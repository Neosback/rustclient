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
    // Plain numbers are NPC definitions; `i<number>` selects an item definition.
    for arg in args {
        match arg.strip_prefix('i') {
            Some(item) => item_ids.push(item.parse()?),
            None => npc_ids.push(arg.parse()?),
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
    for failure in &decoded.failures {
        println!("  failure: {failure}");
    }
    let mut catalog = entity_mesh::EntityCatalog::default();
    catalog.merge(decoded);

    let mut entities = WorldEntitiesState::default();
    let tile = WorldTile::new(3222, 3218, Plane::MIN);
    let update = NpcInfoUpdate {
        new_npcs: npc_ids
            .iter()
            .enumerate()
            .map(|(i, id)| NewNpc {
                index: NpcIndex::new(u32::try_from(i).unwrap_or(0)),
                definition_id: NpcDefinitionId::new(*id),
                delta_x: i32::try_from(i).unwrap_or(0),
                delta_y: 0,
                orientation: 0,
            })
            .collect(),
        ..NpcInfoUpdate::default()
    };
    entities.apply_npc_info(&update, tile);
    let mut geometry = SceneGeometry::default();
    entity_mesh::build_entities_geometry(&scene, &catalog, &entities, &mut geometry)?;
    println!(
        "entity triangles: {}",
        (geometry.opaque.len() + geometry.transparent.len()) / 3
    );
    Ok(())
}
