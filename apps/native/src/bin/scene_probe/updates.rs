//! Bounded CPU update diagnostic against an explicitly supplied cache; no account or server.
use rustclient_game_state::GameState;
use rustclient_model::{LocationChange, SceneDescriptor};
use rustclient_scene::{SceneAsset, ScenePickIndex, SceneryInputs};
use std::{hint::black_box, time::Instant};

pub(super) fn run(
    asset: &SceneAsset,
    descriptor: SceneDescriptor,
    state: &GameState,
) -> Result<(), Box<dyn std::error::Error>> {
    let door = asset
        .sources()
        .squares
        .iter()
        .flat_map(|square| &square.locations)
        .find(|location| {
            descriptor.contains(location.tile)
                && asset
                    .sources()
                    .objects
                    .get(&location.object)
                    .is_some_and(|def| def.actions.iter().flatten().any(|action| action == "Open"))
        })
        .ok_or("no openable location in this scene")?;
    let key = rustclient_scene::location_key(door);
    println!(
        "update workload: remove/restore openable object {:?} at {key:?}",
        door.object
    );
    let placement = state.initial_placement().ok_or("placement missing")?;
    location_queries(asset, state, key, door.object)?;
    for iteration in 0..9 {
        let mut inputs = SceneryInputs::from(state.scenery());
        if iteration % 2 == 0 {
            inputs.locations.insert(
                key,
                rustclient_game_state::LocationOverride {
                    change: LocationChange::Remove,
                    animation: None,
                },
            );
        }
        let started = Instant::now();
        let mesh = rustclient_scene::build_mesh_with_inputs(
            asset,
            descriptor,
            placement.position,
            &inputs,
        )?;
        let mesh_ms = started.elapsed().as_secs_f64() * 1000.;
        let started = Instant::now();
        black_box(ScenePickIndex::new(&mesh.geometry)?);
        let picking_ms = started.elapsed().as_secs_f64() * 1000.;
        let started = Instant::now();
        black_box(rustclient_scene::minimap::build_scene_minimap(
            asset,
            placement.position.plane,
        ));
        let minimap_ms = started.elapsed().as_secs_f64() * 1000.;
        println!(
            "sample={iteration} mesh_ms={mesh_ms:.4} picking_ms={picking_ms:.4} minimap_ms={minimap_ms:.4} vertices={}",
            mesh.vertex_count()
        );
    }
    let mut projected = SceneryInputs::from(state.scenery());
    projected.retain_scene_dependencies(asset);
    let started = Instant::now();
    for _ in 0..1000 {
        let mut current = SceneryInputs::from(black_box(state.scenery()));
        current.retain_scene_dependencies(asset);
        black_box(projected.same_geometry(&current));
    }
    println!(
        "1000 packet snapshot comparisons ms={:.4}",
        started.elapsed().as_secs_f64() * 1000.
    );
    let started = Instant::now();
    for _ in 0..1000 {
        black_box(projected.matches_state(black_box(state.scenery()), asset));
    }
    println!(
        "1000 borrowed dependency comparisons ms={:.4}",
        started.elapsed().as_secs_f64() * 1000.
    );
    println!(
        "CPU-only diagnostic after initial mesh warm-up; no frame-time or live door acceptance claim"
    );
    Ok(())
}

fn location_queries(
    asset: &SceneAsset,
    state: &GameState,
    key: rustclient_model::LocationKey,
    object: rustclient_model::ObjectDefinitionId,
) -> Result<(), Box<dyn std::error::Error>> {
    for count in [0, 32] {
        let mut inputs = SceneryInputs::from(state.scenery());
        for location in asset
            .sources()
            .squares
            .iter()
            .flat_map(|square| &square.locations)
        {
            let candidate = rustclient_scene::location_key(location);
            if inputs.locations.len() == count {
                break;
            }
            if candidate != key {
                inputs.locations.insert(
                    candidate,
                    rustclient_game_state::LocationOverride {
                        change: LocationChange::Remove,
                        animation: None,
                    },
                );
            }
        }
        let started = Instant::now();
        for _ in 0..64 {
            black_box(asset.location_definition(key, object, black_box(&inputs))?);
        }
        println!(
            "location queries overrides={count} count=64 total_ms={:.4}",
            started.elapsed().as_secs_f64() * 1000.
        );
    }
    Ok(())
}
