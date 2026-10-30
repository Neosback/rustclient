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
    let baseline_inputs = SceneryInputs::from(state.scenery());
    let baseline = rustclient_scene::build_mesh_with_inputs(
        asset,
        descriptor,
        placement.position,
        &baseline_inputs,
    )?;
    let baked = std::sync::Arc::new(baseline_inputs.locations.clone());
    let mut index = ScenePickIndex::new(&baseline.geometry)?;
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
        patch_sample(asset, &baseline, &mut index, &baked, &inputs, &mesh)?;
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

/// The same change published as baseline plus patch: what the animation worker builds and what the
/// renderer and picking index apply, against the full rebuild's cost and replaced static bytes.
fn patch_sample(
    asset: &SceneAsset,
    baseline: &rustclient_scene::WorldMesh,
    index: &mut ScenePickIndex,
    baked: &std::sync::Arc<
        std::collections::BTreeMap<
            rustclient_model::LocationKey,
            rustclient_game_state::LocationOverride,
        >,
    >,
    inputs: &SceneryInputs,
    full: &rustclient_scene::WorldMesh,
) -> Result<(), Box<dyn std::error::Error>> {
    let mut patched = inputs.clone();
    patched.base_locations = Some(std::sync::Arc::clone(baked));
    let started = Instant::now();
    let overlay = rustclient_scene::build_dynamic(asset, &patched)?;
    let patch_ms = started.elapsed().as_secs_f64() * 1000.;
    let started = Instant::now();
    index.set_hidden(&baseline.geometry, &overlay.hidden_static);
    let hide_ms = started.elapsed().as_secs_f64() * 1000.;
    let hidden_vertices: usize = baseline
        .geometry
        .models
        .iter()
        .zip(&baseline.geometry.model_sources)
        .filter(|(_, source)| overlay.hidden_static.binary_search(source).is_ok())
        .map(|(model, _)| model.vertices.len())
        .sum();
    let shown = baseline.geometry.vertices.len() - hidden_vertices + overlay.vertices.len();
    let wanted = full.geometry.vertices.len() + full.dynamic.vertices.len();
    println!(
        "  patch_ms={patch_ms:.4} pick_hide_ms={hide_ms:.4} hidden_locations={} hidden_vertices={hidden_vertices} patch_vertices={} patch_bytes={} full_static_bytes={} vertex_total_matches_full={}",
        overlay.hidden_static.len(),
        overlay.vertices.len(),
        overlay.vertices.len() * size_of::<rustclient_scene::SceneVertex>(),
        full.geometry.vertices.len() * size_of::<rustclient_scene::SceneVertex>(),
        shown == wanted,
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
