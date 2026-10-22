//! Shared immutable cache loading; filesystem and event-loop ownership stay in adapters.
use rustclient_cache::{Cache, CacheProfile};
use rustclient_game_state::InitialPlacement;
use rustclient_platform::ReadOnlyBlobs;
use rustclient_scene::{SceneAsset, WorldMesh, build_mesh};
/// CPU resources retained across GPU recreation. `GameState` selects their descriptor.
pub struct LoadedScene {
    pub asset: std::sync::Arc<SceneAsset>,
    pub projection_generation: u64,
    pub projection_inputs: rustclient_scene::SceneryInputs,
    pub mesh: WorldMesh,
}
/// Load the normal spawn window using the explicit revision adapter.
/// # Errors
/// Returns contextual cache/geometry failures; instanced areas are not implemented.
pub fn load_scene(
    source: impl ReadOnlyBlobs,
    profile: CacheProfile,
    placement: InitialPlacement,
    mut progress: impl FnMut(&str),
) -> Result<LoadedScene, String> {
    if placement.area.is_some() {
        return Err("instanced scene loading is not implemented".into());
    }
    let (origin, width, height) = profile
        .scene_window(placement.map_anchor)
        .map_err(|e| e.to_string())?;
    let cache = Cache::open(source).map_err(|e| e.to_string())?;
    let assets = profile
        .load_world(&cache, origin, width, height, &mut progress)
        .map_err(|e| e.to_string())?;
    let asset = SceneAsset::new(assets, origin, width, height).map_err(|e| e.to_string())?;
    progress("building terrain and location mesh");
    let mesh =
        build_mesh(&asset, asset.descriptor(), placement.position).map_err(|e| e.to_string())?;
    Ok(LoadedScene {
        asset: std::sync::Arc::new(asset),
        mesh,
        projection_generation: 0,
        projection_inputs: rustclient_scene::SceneryInputs::default(),
    })
}

/// Rebuild an immutable projection from committed inputs without cache I/O.
/// # Errors
/// Reports missing/invalid scenery dependencies.
pub fn reproject_scene(
    asset: std::sync::Arc<SceneAsset>,
    placement: InitialPlacement,
    inputs: &rustclient_scene::SceneryInputs,
) -> Result<LoadedScene, String> {
    let mesh = rustclient_scene::build_mesh_with_inputs(
        &asset,
        asset.descriptor(),
        placement.position,
        inputs,
    )
    .map_err(|e| e.to_string())?;
    let mut projection_inputs = inputs.clone();
    projection_inputs.retain_scene_dependencies(&asset);
    Ok(LoadedScene {
        asset,
        mesh,
        projection_generation: inputs.generation,
        projection_inputs,
    })
}
