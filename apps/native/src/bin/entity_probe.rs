//! Diagnostic: decode NPC dependencies on demand and mesh one NPC of each requested definition.
use rustclient_cache::{
    Cache, CacheProfile,
    osrs240::entity_assets::{EntityLoader, EntityRequest},
};
use rustclient_game_state::WorldEntitiesState;
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
    let request = EntityRequest {
        npcs: npc_ids.iter().map(|id| NpcDefinitionId::new(*id)).collect(),
        items: item_ids
            .iter()
            .map(|id| rustclient_model::ItemDefinitionId::new(*id))
            .collect(),
        ..EntityRequest::default()
    };
    let started = std::time::Instant::now();
    let decoded = asset_loader.load(&cache, &request);
    println!(
        "items {} npcs {} models {} sequences {} frames {} skeletons {} failures {} in {:?}",
        decoded.items.len(),
        decoded.npcs.len(),
        decoded.models.len(),
        decoded.sequences.len(),
        decoded.frames.len(),
        decoded.skeletons.len(),
        decoded.failures.len(),
        started.elapsed()
    );
    println!(
        "morphing npcs: {}, varbits loaded: {}",
        decoded.npcs.values().filter(|n| n.morphs.is_some()).count(),
        decoded.varbits.len()
    );
    let skeletal = decoded
        .sequences
        .values()
        .filter_map(|s| s.skeletal)
        .collect::<std::collections::BTreeSet<_>>();
    println!(
        "skeletal sequences: {} of {}; cached animations decoded {} of {} ids; bone skeletons {}",
        decoded
            .sequences
            .values()
            .filter(|s| s.skeletal.is_some())
            .count(),
        decoded.sequences.len(),
        decoded.cached_animations.len(),
        skeletal.len(),
        decoded
            .skeletons
            .values()
            .filter(|s| s.bones.is_some())
            .count(),
    );
    for failure in decoded.failures.iter().take(40) {
        println!("  failure: {failure}");
    }
    if decoded.failures.len() > 40 {
        println!("  ... and {} more failures", decoded.failures.len() - 40);
    }
    let mut catalog = entity_mesh::EntityCatalog::default();
    for failure in catalog.merge(&scene, decoded) {
        println!("  pose failure: {failure}");
    }

    let mut entities = WorldEntitiesState::default();
    let tile = WorldTile::new(3222, 3218, Plane::MIN);
    let update = NpcInfoUpdate {
        new_npcs: npc_ids
            .iter()
            .enumerate()
            .map(|(i, id)| NewNpc {
                index: NpcIndex::new(u32::try_from(i).unwrap_or(0)),
                definition_id: NpcDefinitionId::new(*id),
                delta_x: i32::try_from(i % 40).unwrap_or(0) - 20,
                delta_y: 0,
                orientation: 0,
            })
            .collect(),
        ..NpcInfoUpdate::default()
    };
    entities.apply_npc_info(&update, tile);
    let mut geometry = SceneGeometry::default();
    entity_mesh::build_entities_geometry(
        &scene,
        &catalog,
        &entities,
        &std::collections::BTreeMap::new(),
        &mut geometry,
    )?;
    println!(
        "entity triangles: {}",
        (geometry.opaque.len() + geometry.transparent.len()) / 3
    );
    Ok(())
}
