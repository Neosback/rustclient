//! Native scene worker and disposable camera state.
use crate::LoadEvent;
use rustclient_application::{LoadedScene, load_scene};
use rustclient_cache::{
    Cache, CacheProfile,
    osrs240::entity_assets::{EntityLoader, EntityRequest},
};
use rustclient_game_state::InitialPlacement;
use rustclient_platform_native::DirectoryBlobs;
use rustclient_renderer::{CameraInput, CameraTarget, GameplayCameraController};
use std::path::PathBuf;
use winit::event_loop::EventLoopProxy;
pub struct SceneView {
    pub cache_dir: PathBuf,
    pub profile: CacheProfile,
    pub loading: bool,
    pub attempted: bool,
    pub loaded: Option<LoadedScene>,
    pub camera: Option<GameplayCameraController>,
    pub input: CameraInput,
    pub diagnostics: bool,
    pub animator: Option<std::sync::mpsc::SyncSender<rustclient_scene::SceneryInputs>>,
    pub entity_catalog: rustclient_scene::entity_mesh::EntityCatalog,
    pub entity_loader: Option<std::sync::mpsc::Sender<EntityRequest>>,
    pub dynamic_cycle: rustclient_model::ClientCycle,
}
impl SceneView {
    pub fn new(options: &crate::options::Options) -> Self {
        Self {
            cache_dir: options.cache_dir.clone(),
            profile: options.profile,
            loading: false,
            attempted: false,
            loaded: None,
            camera: None,
            input: CameraInput::default(),
            diagnostics: false,
            animator: None,
            entity_catalog: rustclient_scene::entity_mesh::EntityCatalog::default(),
            entity_loader: None,
            dynamic_cycle: rustclient_model::ClientCycle::ZERO,
        }
    }
    pub fn clear(&mut self) {
        self.entity_catalog = rustclient_scene::entity_mesh::EntityCatalog::default();
        self.entity_loader = None;
        self.loading = false;
        self.attempted = false;
        self.loaded = None;
        self.animator = None;
        self.dynamic_cycle = rustclient_model::ClientCycle::ZERO;
        self.camera = None;
        self.input = CameraInput::default();
    }
    pub fn start(
        &mut self,
        placement: InitialPlacement,
        generation: u64,
        proxy: EventLoopProxy<LoadEvent>,
    ) -> Result<(), String> {
        if self.attempted || self.loading || self.loaded.is_some() {
            return Ok(());
        }
        let path = self.cache_dir.clone();
        let profile = self.profile;
        std::thread::Builder::new()
            .name("spawn-scene-loader".into())
            .spawn(move || {
                let result = DirectoryBlobs::open(path)
                    .map_err(|e| e.to_string())
                    .and_then(|source| {
                        load_scene(source, profile, placement, |message| {
                            let _ = proxy.send_event(LoadEvent::SceneProgress(
                                generation,
                                message.to_owned(),
                            ));
                        })
                    });
                let _ = proxy.send_event(LoadEvent::SceneLoaded(generation, Box::new(result)));
            })
            .map_err(|e| format!("scene worker: {e}"))?;
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
        std::thread::Builder::new()
            .name("scenery-projector".into())
            .spawn(move || {
                let result = rustclient_application::reproject_scene(asset, placement, &inputs);
                let _ = proxy.send_event(LoadEvent::SceneLoaded(generation, Box::new(result)));
            })
            .map_err(|e| e.to_string())?;
        self.loading = true;
        Ok(())
    }
    pub fn start_animator(
        &mut self,
        asset: std::sync::Arc<rustclient_scene::SceneAsset>,
        placement: InitialPlacement,
        session: u64,
        proxy: EventLoopProxy<LoadEvent>,
    ) -> Result<(), String> {
        let (sender, receiver) =
            std::sync::mpsc::sync_channel::<rustclient_scene::SceneryInputs>(2);
        std::thread::Builder::new()
            .name("scenery-animation".into())
            .spawn(move || {
                while let Ok(mut inputs) = receiver.recv() {
                    while let Ok(next) = receiver.try_recv() {
                        inputs = next;
                    }
                    let result =
                        rustclient_scene::build_dynamic(&asset, placement.position, &inputs)
                            .map_err(|e| e.to_string());
                    if proxy
                        .send_event(LoadEvent::DynamicReady(
                            session,
                            inputs.generation,
                            inputs.cycle,
                            Box::new(result),
                        ))
                        .is_err()
                    {
                        break;
                    }
                }
            })
            .map_err(|e| e.to_string())?;
        self.animator = Some(sender);
        Ok(())
    }
    /// Start the worker that decodes NPC, worn-item and animation dependencies on demand.
    pub fn start_entity_loader(
        &mut self,
        asset: std::sync::Arc<rustclient_scene::SceneAsset>,
        session: u64,
        proxy: EventLoopProxy<LoadEvent>,
    ) -> Result<(), String> {
        let (sender, receiver) = std::sync::mpsc::channel::<EntityRequest>();
        let path = self.cache_dir.clone();
        let profile = self.profile;
        std::thread::Builder::new()
            .name("entity-assets".into())
            .spawn(move || {
                let cache = match DirectoryBlobs::open(path)
                    .map_err(|e| e.to_string())
                    .and_then(|source| Cache::open(source).map_err(|e| e.to_string()))
                {
                    Ok(cache) => cache,
                    Err(error) => {
                        eprintln!("entity assets: {error}");
                        return;
                    }
                };
                let mut loader = EntityLoader::new(profile, asset.sources());
                while let Ok(request) = receiver.recv() {
                    let assets = loader.load(&cache, &request);
                    if proxy
                        .send_event(LoadEvent::EntityAssets(session, Box::new(assets)))
                        .is_err()
                    {
                        break;
                    }
                }
            })
            .map_err(|e| e.to_string())?;
        self.entity_loader = Some(sender);
        Ok(())
    }
    pub fn reset_camera(&mut self) {
        self.camera = self
            .loaded
            .as_ref()
            .map(|s| GameplayCameraController::new(target(s)));
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
pub fn player_target(scene: &LoadedScene, tile: rustclient_model::WorldTile) -> CameraTarget {
    let origin = scene.asset.descriptor().origin();
    let x = (tile.x - origin.x) * 128 + 64;
    let z = (tile.y - origin.y) * 128 + 64;
    let plane = tile.plane;
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
