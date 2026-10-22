//! Diagnostic: decode NPC dependencies on demand and mesh one NPC of each requested definition.
use rustclient_cache::{
    Cache, CacheProfile,
    osrs240::entity_assets::{EntityAssets, EntityLoader, EntityRequest},
};
use rustclient_game_state::{
    AuthenticatedPlayer, GameState, InitialPlacement, SceneryOperation, SemanticOperation,
};
use rustclient_model::{NewNpc, NpcDefinitionId, NpcIndex, NpcInfoUpdate, Plane, WorldTile};
use rustclient_platform_native::DirectoryBlobs;
use rustclient_scene::{SceneAsset, SceneGeometry, entity_mesh};

fn main() -> Result<(), Box<dyn std::error::Error>> {
    let mut args = std::env::args().skip(1);
    let path = args.next().ok_or("cache directory required")?;
    let mut npc_ids: Vec<u32> = Vec::new();
    let mut item_ids: Vec<u32> = Vec::new();
    // Plain numbers are NPC definitions; `i<number>` selects an item definition. `a-b` is an
    // inclusive range of either.
    for arg in args {
        let (ids, text) = match arg.strip_prefix('i') {
            Some(item) => (&mut item_ids, item),
            None => (&mut npc_ids, arg.as_str()),
        };
        match text.split_once('-') {
            Some((low, high)) => ids.extend(low.parse::<u32>()?..=high.parse::<u32>()?),
            None => ids.push(text.parse()?),
        }
    }
    let origin = WorldTile::new(3168, 3168, Plane::MIN);
    let cache = Cache::open(DirectoryBlobs::open(path)?)?;
    let assets = CacheProfile::OpenRune240.load_world(&cache, origin, 104, 104, |_| {})?;
    let scene = SceneAsset::new(assets, origin, 104, 104)?;

    let mut asset_loader = EntityLoader::new(CacheProfile::OpenRune240, scene.sources());
    let mut catalog = entity_mesh::EntityCatalog::default();
    let mut failures = 0;
    let started = std::time::Instant::now();
    // Keep every request below the loader's batch limit; one large request would truncate.
    for offset in (0..npc_ids.len().max(item_ids.len())).step_by(2048) {
        let request = EntityRequest {
            npcs: npc_ids
                .iter()
                .skip(offset)
                .take(2048)
                .map(|id| NpcDefinitionId::new(*id))
                .collect(),
            items: item_ids
                .iter()
                .skip(offset)
                .take(2048)
                .map(|id| rustclient_model::ItemDefinitionId::new(*id))
                .collect(),
            ..EntityRequest::default()
        };
        let decoded = asset_loader.load(&cache, &request);
        report_batch(&decoded);
        failures += decoded.failures.len();
        for failure in &decoded.failures {
            eprintln!("  decode failure: {failure}");
        }
        let pose_failures = catalog.merge(&scene, decoded);
        failures += pose_failures.len();
        for failure in pose_failures {
            eprintln!("  pose failure: {failure}");
        }
    }
    let triangles = mesh_batches(&scene, &catalog, &npc_ids)?;
    println!(
        "requested NPCs {} items {}; entity triangles {triangles}; failures {failures}; elapsed {:?}",
        npc_ids.len(),
        item_ids.len(),
        started.elapsed()
    );
    if failures > 0 {
        return Err(format!("entity probe recorded {failures} asset/pose failures").into());
    }
    Ok(())
}

fn report_batch(decoded: &EntityAssets) {
    println!(
        "decoded items {} npcs {} models {} sequences {} frames {} skeletons {} failures {}",
        decoded.items.len(),
        decoded.npcs.len(),
        decoded.models.len(),
        decoded.sequences.len(),
        decoded.frames.len(),
        decoded.skeletons.len(),
        decoded.failures.len()
    );
    for (id, sequence) in &decoded.sequences {
        if let Some(animation) = sequence.skeletal {
            println!("  skeletal sequence {id}: animation {animation}");
        }
    }
    println!(
        "cached animations decoded {}; morphing npcs {}; varbits {}",
        decoded.cached_animations.len(),
        decoded.npcs.values().filter(|n| n.morphs.is_some()).count(),
        decoded.varbits.len()
    );
}

fn mesh_batches(
    scene: &SceneAsset,
    catalog: &entity_mesh::EntityCatalog,
    npc_ids: &[u32],
) -> Result<usize, Box<dyn std::error::Error>> {
    let tile = WorldTile::new(3222, 3218, Plane::MIN);
    let mut triangles = 0;
    // Reuse a bounded projection rather than putting every cache definition into one world.
    let mut geometry = SceneGeometry::default();
    for ids in npc_ids.chunks(32) {
        let mut state = GameState::new();
        state.apply_operation(SemanticOperation::AuthenticateSession {
            revision: rustclient_model::Revision::with_subrevision(240, 2),
            player: AuthenticatedPlayer {
                profile: rustclient_protocol::ServerProfile::OPENRUNE_240_2.id(),
                player: rustclient_model::PlayerIndex::new(1),
                member: false,
                staff_level: 0,
            },
        })?;
        state.apply_operation(SemanticOperation::EstablishInitialPlacement(
            InitialPlacement {
                position: tile,
                map_anchor: tile,
                area: None,
            },
        ))?;
        let update = NpcInfoUpdate {
            new_npcs: ids
                .iter()
                .enumerate()
                .map(|(i, id)| NewNpc {
                    index: NpcIndex::new(u32::try_from(i).unwrap_or(0)),
                    definition_id: NpcDefinitionId::new(*id),
                    delta_x: i32::try_from(i).unwrap_or(0) - 16,
                    delta_y: 0,
                    orientation: 0,
                })
                .collect(),
            ..NpcInfoUpdate::default()
        };
        state.apply_operation(SemanticOperation::UpdateNpcs(update))?;
        // Exercise more than the first sample of each animation without retaining geometry.
        for cycle in [0, 10] {
            state.apply_operation(SemanticOperation::Scenery(SceneryOperation::AdvanceCycles(
                cycle,
            )))?;
            geometry.vertices.clear();
            geometry.picks.clear();
            geometry.opaque.clear();
            geometry.transparent.clear();
            entity_mesh::build_entities_geometry(
                scene,
                catalog,
                state.entities(),
                &std::collections::BTreeMap::new(),
                &mut geometry,
            )?;
            triangles += (geometry.opaque.len() + geometry.transparent.len()) / 3;
        }
    }
    Ok(triangles)
}
