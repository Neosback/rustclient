//! Native scene worker and disposable camera state.
use crate::LoadEvent;
use rustclient_application::{LoadedScene, load_scene_from_cache};
use rustclient_cache::{Cache, CacheProfile, osrs240::entity_assets::EntityRequest};
use rustclient_game_state::InitialPlacement;
use rustclient_platform::CpuJobExecutor;
use rustclient_platform_native::DirectoryBlobs;
use rustclient_renderer::{CameraInput, CameraTarget, GameplayCameraController};
use std::{
    path::{Path, PathBuf},
    sync::{Arc, OnceLock},
};
use winit::event_loop::EventLoopProxy;
pub type SharedCacheSlot = Arc<OnceLock<Result<Arc<Cache<DirectoryBlobs>>, String>>>;
pub type PreparedScene = LoadedScene<rustclient_renderer::PreparedWorldMesh>;
fn prepare_scene(scene: LoadedScene) -> Result<PreparedScene, String> {
    scene
        .try_map_mesh(rustclient_renderer::PreparedWorldMesh::new)
        .map_err(|e| format!("scene upload preparation: {e}"))
}
pub struct SceneView {
    pub generation: u64,
    /// Reused every client cycle; never aliases the animated-scenery geometry.
    pub entity_geometry: rustclient_scene::SceneGeometry,
    pub motion: rustclient_scene::ActorMotion,
    /// At most one scenery pose request is outstanding, bounding queued geometry.
    pub animator_in_flight: Option<rustclient_model::ClientCycle>,
    /// Distinguishes worker lifetimes even when decoded assets change inside one session.
    pub animator_generation: u64,
    pub cache_dir: PathBuf,
    pub profile: CacheProfile,
    pub shared_cache: SharedCacheSlot,
    pub loading: bool,
    pub attempted: bool,
    /// Set while a window-changing rebuild's replacement loads and `loaded` is still the scene
    /// being drawn (SPEC-0023); the instant it began delays the loading box.
    pub replacing: Option<std::time::Instant>,
    /// The renderer was created after the static triangles of `loaded` were released, so the
    /// GPU has no world until the scene is projected again from its cached asset (since when).
    pub awaiting_world: Option<std::time::Instant>,
    pub loaded: Option<LoadedScene>,
    pub camera: Option<GameplayCameraController>,
    pub input: CameraInput,
    pub diagnostics: bool,
    pub animator: Option<crate::scene_workers::SceneryWorker>,
    pub entity_catalog: rustclient_scene::entity_mesh::EntityCatalog,
    pub entity_loader: Option<crate::scene_workers::EntityWorker>,
    pub jobs: Option<Arc<rustclient_platform_native::NativeJobPool>>,
    structural_scope: rustclient_platform::JobCancellation,
    pub entity_request: Option<EntityRequest>,
    pub entity_loader_generation: u64,
    entity_scope: rustclient_platform::JobCancellation,
    pub dynamic_cycle: rustclient_model::ClientCycle,
    /// Areas plugins force roofed; scenes are projected with these and rebuilt when they change.
    pub roof_areas: std::sync::Arc<[rustclient_scene::RoofArea]>,
    /// Which animations plugins ask to be blended between frames.
    pub interpolation: rustclient_scene::AnimationInterpolation,
    /// Actors plugins remove from the scene (model) and from their 2D overlays.
    pub hidden_models: rustclient_scene::entity_mesh::HiddenActors,
    pub hidden_overlays: rustclient_scene::entity_mesh::HiddenActors,
}
impl Drop for SceneView {
    fn drop(&mut self) {
        self.structural_scope.cancel();
        self.entity_scope.cancel();
    }
}
impl SceneView {
    pub fn new(options: &crate::options::Options) -> Self {
        Self {
            generation: 0,
            entity_geometry: rustclient_scene::SceneGeometry::default(),
            motion: rustclient_scene::ActorMotion::default(),
            animator_in_flight: None,
            animator_generation: 0,
            cache_dir: options.cache_dir.clone(),
            profile: options.profile,
            shared_cache: Arc::new(OnceLock::new()),
            loading: false,
            attempted: false,
            replacing: None,
            awaiting_world: None,
            loaded: None,
            camera: None,
            input: CameraInput::default(),
            diagnostics: false,
            animator: None,
            entity_catalog: rustclient_scene::entity_mesh::EntityCatalog::default(),
            entity_loader: None,
            jobs: None,
            structural_scope: rustclient_platform::JobCancellation::default(),
            entity_request: None,
            entity_loader_generation: 0,
            entity_scope: rustclient_platform::JobCancellation::default(),
            dynamic_cycle: rustclient_model::ClientCycle::ZERO,
            roof_areas: std::sync::Arc::default(),
            interpolation: rustclient_scene::AnimationInterpolation::default(),
            hidden_models: rustclient_scene::entity_mesh::HiddenActors::default(),
            hidden_overlays: rustclient_scene::entity_mesh::HiddenActors::default(),
        }
    }
    pub fn scenery_inputs(
        &self,
        state: &rustclient_game_state::SceneryState,
    ) -> rustclient_scene::SceneryInputs {
        let mut inputs = rustclient_scene::SceneryInputs::from(state);
        inputs.animations = self.entity_catalog.animation_assets();
        inputs.roof_areas = std::sync::Arc::clone(&self.roof_areas);
        inputs.interpolation = self.interpolation.clone();
        inputs
    }
    /// The inputs for one animation pass, carrying the baseline the static scene was baked with
    /// so the worker can publish scenery changes as a patch over it.
    pub fn animator_inputs(
        &self,
        state: &rustclient_game_state::SceneryState,
    ) -> rustclient_scene::SceneryInputs {
        let mut inputs = self.scenery_inputs(state);
        inputs.base_locations = self
            .loaded
            .as_ref()
            .and_then(|loaded| loaded.projection_inputs.base_locations.clone());
        inputs
    }
    pub(super) fn cancel_entity_request(&mut self) {
        self.entity_scope.cancel();
        if let Some(request) = self.entity_request.take() {
            self.entity_catalog.retry_request(&request);
        }
    }
    pub fn clear(&mut self) {
        self.structural_scope.cancel();
        self.structural_scope = rustclient_platform::JobCancellation::default();
        self.cancel_entity_request();
        self.entity_geometry = rustclient_scene::SceneGeometry::default();
        self.motion = rustclient_scene::ActorMotion::default();
        self.animator_in_flight = None;
        self.generation = self.generation.wrapping_add(1);
        self.entity_catalog = rustclient_scene::entity_mesh::EntityCatalog::default();
        self.entity_loader = None;
        self.loading = false;
        self.attempted = false;
        self.replacing = None;
        self.loaded = None;
        self.animator = None;
        self.dynamic_cycle = rustclient_model::ClientCycle::ZERO;
        self.camera = None;
        self.input = CameraInput::default();
    }
    /// Drop the loaded region but keep what is not tied to it: the camera, the actors' drawn
    /// positions, rotations and poses, and already-decoded entity definitions/models/animations.
    pub fn invalidate_map(&mut self) {
        self.cancel_entity_request();
        let camera = self.camera;
        let motion = std::mem::take(&mut self.motion);
        let entity_catalog = std::mem::take(&mut self.entity_catalog);
        self.clear();
        self.camera = camera;
        self.motion = motion;
        self.entity_catalog = entity_catalog;
    }
    /// A rebuild to a different window: keep drawing the loaded scene while the replacement
    /// loads. Workers tied to the old asset stop and restart once the replacement installs, so
    /// nothing publishes scenery or actor models against a scene about to be dropped.
    pub fn retire_map(&mut self) {
        let loaded = self.loaded.take();
        let replacing = self.replacing.unwrap_or_else(std::time::Instant::now);
        self.invalidate_map();
        self.loaded = loaded;
        self.replacing = Some(replacing);
    }
    pub fn open_shared_cache(
        cache_dir: &Path,
        slot: &OnceLock<Result<Arc<Cache<DirectoryBlobs>>, String>>,
    ) -> Result<Arc<Cache<DirectoryBlobs>>, String> {
        slot.get_or_init(|| {
            DirectoryBlobs::open(cache_dir)
                .map_err(|e| e.to_string())
                .and_then(|source| Cache::open(source).map(Arc::new).map_err(|e| e.to_string()))
        })
        .clone()
    }
    pub fn start(
        &mut self,
        placement: InitialPlacement,
        inputs: rustclient_scene::SceneryInputs,
        generation: u64,
        proxy: EventLoopProxy<LoadEvent>,
    ) -> Result<(), String> {
        if self.attempted || self.loading || (self.loaded.is_some() && self.replacing.is_none()) {
            return Ok(());
        }
        let path = self.cache_dir.clone();
        let profile = self.profile;
        let shared_cache = Arc::clone(&self.shared_cache);
        let pool = self.cpu_jobs()?;
        let fault_proxy = proxy.clone();
        let job = rustclient_platform::CpuJob::new(
            rustclient_platform::JobIdentity {
                session: generation,
                ..Default::default()
            },
            rustclient_platform::JobPriority::Visible,
            self.structural_scope.clone(),
            move |cancellation| {
                let result = Self::open_shared_cache(&path, &shared_cache).and_then(|cache| {
                    load_scene_from_cache(&cache, profile, placement, &inputs, |message| {
                        if !cancellation.is_cancelled() {
                            let _ = proxy.send_event(LoadEvent::SceneProgress(
                                generation,
                                message.to_owned(),
                            ));
                        }
                    })
                });
                if cancellation.is_cancelled() {
                    return;
                }
                let result = result.and_then(prepare_scene);
                if cancellation.is_cancelled() {
                    return;
                }
                let _ = proxy.send_event(LoadEvent::SceneLoaded(generation, Box::new(result)));
            },
        )
        .with_fault_handler(move || {
            let _ = fault_proxy.send_event(LoadEvent::SceneLoaded(
                generation,
                Box::new(Err("scene worker panicked".into())),
            ));
        });
        pool.try_submit(job)
            .map_err(|_| "projection queue full; scene load will retry".to_owned())?;
        self.loading = true;
        self.attempted = true;
        Ok(())
    }
    pub fn reproject(
        &mut self,
        asset: std::sync::Arc<rustclient_scene::SceneAsset>,
        placement: InitialPlacement,
        inputs: rustclient_scene::SceneryInputs,
        generation: u64,
        proxy: EventLoopProxy<LoadEvent>,
    ) -> Result<(), String> {
        let cache_dir = self.cache_dir.clone();
        let shared_cache = std::sync::Arc::clone(&self.shared_cache);
        let profile = self.profile;
        let pool = self.cpu_jobs()?;
        let fault_proxy = proxy.clone();
        let job = rustclient_platform::CpuJob::new(
            rustclient_platform::JobIdentity {
                session: generation,
                ..Default::default()
            },
            rustclient_platform::JobPriority::Visible,
            self.structural_scope.clone(),
            move |cancellation| {
                let result = Self::open_shared_cache(&cache_dir, &shared_cache).and_then(|cache| {
                    rustclient_application::reproject_scene_from_cache(
                        &cache, profile, asset, placement, &inputs,
                    )
                });
                if cancellation.is_cancelled() {
                    return;
                }
                let result = result.and_then(prepare_scene);
                if cancellation.is_cancelled() {
                    return;
                }
                let _ = proxy.send_event(LoadEvent::SceneLoaded(generation, Box::new(result)));
            },
        )
        .with_fault_handler(move || {
            let _ = fault_proxy.send_event(LoadEvent::SceneLoaded(
                generation,
                Box::new(Err("scenery worker panicked".into())),
            ));
        });
        pool.try_submit(job)
            .map_err(|_| "projection queue full; scene update will retry".to_owned())?;
        self.loading = true;
        Ok(())
    }
    /// Keep both projection workers on the current immutable resource closure.
    pub fn ensure_workers(
        &mut self,
        asset: Arc<rustclient_scene::SceneAsset>,
        resources_changed: bool,
        proxy: EventLoopProxy<LoadEvent>,
    ) {
        if (resources_changed || self.entity_loader.is_none())
            && let Err(error) =
                self.start_entity_loader(asset.clone(), self.generation, proxy.clone())
        {
            eprintln!("entity asset worker: {error}");
        }
        if (resources_changed || self.animator.is_none())
            && let Err(error) = self.start_animator(asset, self.generation, proxy)
        {
            eprintln!("animation worker: {error}");
        }
    }
    pub fn cpu_jobs(&mut self) -> Result<Arc<rustclient_platform_native::NativeJobPool>, String> {
        if self.jobs.is_none() {
            self.jobs = Some(Arc::new(
                rustclient_platform_native::NativeJobPool::new()
                    .map_err(|error| format!("projection workers: {error}"))?,
            ));
        }
        self.jobs
            .clone()
            .ok_or_else(|| "projection workers unavailable".into())
    }
    pub fn start_animator(
        &mut self,
        asset: Arc<rustclient_scene::SceneAsset>,
        session: u64,
        proxy: EventLoopProxy<LoadEvent>,
    ) -> Result<(), String> {
        let worker = self.animator_generation.wrapping_add(1);
        self.animator = Some(crate::scene_workers::SceneryWorker::new(
            crate::scene_workers::WorkerContext {
                pool: self.cpu_jobs()?,
                cancellation: rustclient_platform::JobCancellation::default(),
                identity: rustclient_platform::JobIdentity {
                    session,
                    scene: Some(asset.descriptor().id().bytes()),
                    resources: worker,
                    ..Default::default()
                },
                proxy,
                asset,
            },
        ));
        self.animator_generation = worker;
        self.animator_in_flight = None;
        Ok(())
    }
    pub fn start_entity_loader(
        &mut self,
        asset: Arc<rustclient_scene::SceneAsset>,
        session: u64,
        proxy: EventLoopProxy<LoadEvent>,
    ) -> Result<(), String> {
        let pool = self.cpu_jobs()?;
        self.cancel_entity_request();
        self.entity_scope = rustclient_platform::JobCancellation::default();
        self.entity_loader_generation = self.entity_loader_generation.wrapping_add(1);
        self.entity_loader = Some(crate::scene_workers::EntityWorker::new(
            crate::scene_workers::WorkerContext {
                pool,
                cancellation: self.entity_scope.clone(),
                identity: rustclient_platform::JobIdentity {
                    session,
                    scene: Some(asset.descriptor().id().bytes()),
                    resources: self.entity_loader_generation,
                    ..Default::default()
                },
                proxy,
                asset,
            },
            self.cache_dir.clone(),
            self.profile,
            Arc::clone(&self.shared_cache),
        ));
        Ok(())
    }
    pub fn reset_camera(&mut self) {
        if let Some(scene) = &self.loaded {
            if let Some(camera) = &mut self.camera {
                camera.recenter(target(scene));
            } else {
                self.camera = Some(GameplayCameraController::new(target(scene)));
            }
        }
    }
}

#[expect(
    clippy::cast_possible_truncation,
    reason = "validated scene fine coordinates"
)]
pub fn target(scene: &LoadedScene) -> CameraTarget {
    let mesh = &scene.mesh;
    let x = (mesh.focus[0] * 128.) as i32;
    let z = (mesh.focus[2] * 128.) as i32;
    let plane = scene.asset.descriptor().origin().plane;
    let height = scene
        .asset
        .ground_height(plane, [x, z])
        .expect("validated spawn terrain");
    let mut drop = 0;
    let tx = x / 128;
    let ty = z / 128;
    if tx > 3 && ty > 3 && tx < 100 && ty < 100 {
        for dx in -4..=4 {
            for dy in -4..=4 {
                if let Ok(h) = scene
                    .asset
                    .ground_height(plane, [(tx + dx) * 128, (ty + dy) * 128])
                {
                    drop = drop.max(height - h);
                }
            }
        }
    }
    CameraTarget {
        x,
        height,
        z,
        terrain_drop: drop,
    }
}
/// Camera focus on a one-tile actor whose footprint corner is `position` (world units).
pub fn player_target(
    scene: &LoadedScene,
    position: [i32; 2],
    plane: rustclient_model::Plane,
) -> CameraTarget {
    let origin = scene.asset.descriptor().origin();
    let x = position[0] - origin.x * 128 + 64;
    let z = position[1] - origin.y * 128 + 64;
    let height = scene.asset.ground_height(plane, [x, z]).unwrap_or(0);
    let mut drop = 0;
    let tx = x / 128;
    let ty = z / 128;
    if tx > 3 && ty > 3 && tx < 100 && ty < 100 {
        for dx in -4..=4 {
            for dy in -4..=4 {
                if let Ok(h) = scene
                    .asset
                    .ground_height(plane, [(tx + dx) * 128, (ty + dy) * 128])
                {
                    drop = drop.max(height - h);
                }
            }
        }
    }
    CameraTarget {
        x,
        height,
        z,
        terrain_drop: drop,
    }
}
#[expect(
    clippy::cast_possible_truncation,
    reason = "bounded logical pointer coordinates"
)]
pub fn pointer(x: f64, y: f64, scale: f64) -> [i32; 2] {
    [
        (x / scale).clamp(-16384., 16384.) as i32,
        (y / scale).clamp(-16384., 16384.) as i32,
    ]
}

/// A pointer already in logical canvas pixels (stretched mode), as the camera input expects.
#[expect(
    clippy::cast_possible_truncation,
    reason = "bounded logical pointer coordinates"
)]
pub fn canvas_pointer(x: f32, y: f32) -> [i32; 2] {
    [
        x.clamp(-16384., 16384.) as i32,
        y.clamp(-16384., 16384.) as i32,
    ]
}

/// How long a replacement scene may load before the loading box covers the frozen frame.
pub const LOADING_BOX_DELAY: std::time::Duration = std::time::Duration::from_millis(250);

/// Whether the stock "Loading - please wait." box is drawn (SPEC-0023): always when no scene is
/// displayed (login, first load); over a retired scene only once its replacement has been
/// loading longer than [`LOADING_BOX_DELAY`]. Reprojections never show it.
pub fn loading_box_due(displayed: bool, replacing_for: Option<std::time::Duration>) -> bool {
    !displayed || replacing_for.is_some_and(|elapsed| elapsed >= LOADING_BOX_DELAY)
}

#[cfg(test)]
mod tests {
    use super::*;
    use std::time::Duration;

    #[test]
    fn loading_box_covers_an_empty_viewport_and_a_slow_replacement_only() {
        assert!(loading_box_due(false, None));
        assert!(!loading_box_due(true, None));
        assert!(!loading_box_due(true, Some(Duration::from_millis(100))));
        assert!(loading_box_due(true, Some(LOADING_BOX_DELAY)));
    }
}
