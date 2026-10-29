//! Shared immutable cache loading; filesystem and event-loop ownership stay in adapters.
use rustclient_cache::{Cache, CacheProfile};
use rustclient_game_state::InitialPlacement;
use rustclient_platform::ReadOnlyBlobs;
use rustclient_scene::{SceneAsset, WorldMesh};
/// CPU resources retained across GPU recreation. `GameState` selects their descriptor.
pub struct LoadedScene {
    pub asset: std::sync::Arc<SceneAsset>,
    pub projection_generation: u64,
    pub projection_inputs: rustclient_scene::SceneryInputs,
    pub mesh: WorldMesh,
    pub picking: rustclient_scene::ScenePickIndex,
}
/// Load the normal spawn window using the explicit revision adapter.
/// # Errors
/// Returns contextual cache/geometry failures; instanced areas are not implemented.
pub fn load_scene(
    source: impl ReadOnlyBlobs,
    profile: CacheProfile,
    placement: InitialPlacement,
    progress: impl FnMut(&str),
) -> Result<LoadedScene, String> {
    load_scene_with_inputs(
        source,
        profile,
        placement,
        &rustclient_scene::SceneryInputs::default(),
        progress,
    )
}

/// Load the normal spawn window and project it immediately with the current scenery inputs.
/// # Errors
/// Returns contextual cache/geometry failures; instanced areas are not implemented.
pub fn load_scene_with_inputs(
    source: impl ReadOnlyBlobs,
    profile: CacheProfile,
    placement: InitialPlacement,
    inputs: &rustclient_scene::SceneryInputs,
    progress: impl FnMut(&str),
) -> Result<LoadedScene, String> {
    let cache = Cache::open(source).map_err(|e| e.to_string())?;
    load_scene_from_cache(&cache, profile, placement, inputs, progress)
}

/// Load the normal spawn window from an already-opened [`Cache`] and project it immediately.
/// # Errors
/// Returns contextual cache/geometry failures; instanced areas are not implemented.
pub fn load_scene_from_cache(
    cache: &Cache<impl ReadOnlyBlobs>,
    profile: CacheProfile,
    placement: InitialPlacement,
    inputs: &rustclient_scene::SceneryInputs,
    mut progress: impl FnMut(&str),
) -> Result<LoadedScene, String> {
    if placement.area.is_some() {
        return Err("instanced scene loading is not implemented".into());
    }
    let (origin, width, height) = profile
        .scene_window(placement.map_anchor)
        .map_err(|e| e.to_string())?;
    let mut assets = profile
        .load_world(cache, origin, width, height, &mut progress)
        .map_err(|e| e.to_string())?;
    let wanted = inputs
        .locations
        .values()
        .filter_map(|value| match value.change {
            rustclient_model::LocationChange::Replace(def) => Some(def.object),
            _ => None,
        })
        .collect();
    profile
        .extend_world_locations(cache, &mut assets, &wanted)
        .map_err(|e| e.to_string())?;
    let asset = SceneAsset::new(assets, origin, width, height).map_err(|e| e.to_string())?;
    progress("building terrain and location mesh");
    let descriptor = asset.descriptor().with_plane(placement.map_anchor.plane);
    let mesh = rustclient_scene::build_mesh_with_inputs(
        &asset,
        descriptor,
        if descriptor.contains(placement.position) {
            placement.position
        } else {
            placement.map_anchor
        },
        inputs,
    )
    .map_err(|e| e.to_string())?;
    let picking =
        rustclient_scene::ScenePickIndex::new(&mesh.geometry).map_err(|e| e.to_string())?;
    let mut projection_inputs = inputs.clone();
    projection_inputs.retain_scene_dependencies(&asset);
    Ok(LoadedScene {
        asset: std::sync::Arc::new(asset),
        mesh,
        picking,
        projection_generation: inputs.generation,
        projection_inputs,
    })
}

/// Resolve any new server-created location assets on the CPU worker, then project.
/// # Errors
/// Reports corrupt/missing cache dependencies and projection failures.
pub fn reproject_scene_from_cache(
    cache: &Cache<impl ReadOnlyBlobs>,
    profile: CacheProfile,
    mut asset: std::sync::Arc<SceneAsset>,
    placement: InitialPlacement,
    inputs: &rustclient_scene::SceneryInputs,
) -> Result<LoadedScene, String> {
    let wanted: std::collections::BTreeSet<_> = inputs
        .locations
        .values()
        .filter_map(|value| match value.change {
            rustclient_model::LocationChange::Replace(def)
                if !asset.sources().objects.contains_key(&def.object) =>
            {
                Some(def.object)
            }
            _ => None,
        })
        .collect();
    if !wanted.is_empty() {
        let descriptor = asset.descriptor();
        let mut sources = asset.sources().clone();
        profile
            .extend_world_locations(cache, &mut sources, &wanted)
            .map_err(|e| e.to_string())?;
        asset = std::sync::Arc::new(
            SceneAsset::new(
                sources,
                descriptor.origin(),
                descriptor.width(),
                descriptor.height(),
            )
            .map_err(|e| e.to_string())?,
        );
    }
    reproject_scene(asset, placement, inputs)
}

/// Rebuild an immutable projection from committed inputs without cache I/O.
/// # Errors
/// Reports missing/invalid scenery dependencies.
pub fn reproject_scene(
    asset: std::sync::Arc<SceneAsset>,
    placement: InitialPlacement,
    inputs: &rustclient_scene::SceneryInputs,
) -> Result<LoadedScene, String> {
    let descriptor = asset.descriptor().with_plane(placement.map_anchor.plane);
    let mesh = rustclient_scene::build_mesh_with_inputs(
        &asset,
        descriptor,
        if descriptor.contains(placement.position) {
            placement.position
        } else {
            placement.map_anchor
        },
        inputs,
    )
    .map_err(|e| e.to_string())?;
    let picking =
        rustclient_scene::ScenePickIndex::new(&mesh.geometry).map_err(|e| e.to_string())?;
    let mut projection_inputs = inputs.clone();
    projection_inputs.retain_scene_dependencies(&asset);
    Ok(LoadedScene {
        asset,
        mesh,
        picking,
        projection_generation: inputs.generation,
        projection_inputs,
    })
}
