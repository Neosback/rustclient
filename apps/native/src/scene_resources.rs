//! Missing-location asset admission is independent from scene reconstruction.
use crate::{LoadEvent, NativeApp, scene::SceneView};
use rustclient_platform::{CpuJob, CpuJobExecutor, JobIdentity, JobPriority};
use rustclient_scene::{SceneAsset, SceneryInputs};
use std::sync::Arc;
#[derive(Default)]
pub(super) struct LocationRequests {
    pub assets: Option<JobIdentity>,
    pub retry_assets: Option<std::time::Instant>,
    pub projection_retry: Option<(u64, std::time::Instant)>,
    pub cursor: rustclient_game_state::ProjectionCursor,
    pub observed: Option<u64>,
    pub invalidate: bool,
}
impl SceneView {
    pub(super) fn request_location_resources(
        &mut self,
        asset: Arc<SceneAsset>,
        inputs: SceneryInputs,
        proxy: winit::event_loop::EventLoopProxy<LoadEvent>,
    ) -> Result<(), String> {
        if self.locations.assets.is_some()
            || self
                .locations
                .retry_assets
                .is_some_and(|until| std::time::Instant::now() < until)
        {
            return Ok(());
        }
        let pool = self.cpu_jobs()?;
        let path = self.cache_dir.clone();
        let cache = Arc::clone(&self.shared_cache);
        let profile = self.profile;
        let session = self.generation;
        let resources = self.animator_generation;
        let identity = JobIdentity {
            session,
            resources,
            scene: Some(asset.descriptor().id().bytes()),
            request: inputs.generation,
        };
        let fault_proxy = proxy.clone();
        let job = CpuJob::new(
            identity,
            JobPriority::Visible,
            self.structural_scope.clone(),
            move |cancellation| {
                let result = Self::open_shared_cache(&path, &cache).and_then(|cache| {
                    rustclient_application::extend_location_resources_from_cache(
                        &cache, profile, asset, &inputs,
                    )
                });
                if !cancellation.is_cancelled() {
                    let _ = proxy.send_event(LoadEvent::LocationResources(
                        session,
                        resources,
                        Box::new(result),
                    ));
                }
            },
        )
        .with_fault_handler(move || {
            let _ = fault_proxy.send_event(LoadEvent::LocationResources(
                session,
                resources,
                Box::new(Err("location asset worker panicked".into())),
            ));
        });
        pool.try_submit(job)
            .map_err(|_| "location asset queue full; retry pending".to_owned())?;
        self.locations.assets = Some(identity);
        Ok(())
    }
    pub(super) fn location_resources_ready(
        &self,
        state: &rustclient_game_state::SceneryState,
    ) -> bool {
        self.loaded.as_ref().is_some_and(|loaded| {
            state.locations().values().all(|value| match value.change {
                rustclient_model::LocationChange::Replace(def) => {
                    loaded.asset.sources().objects.contains_key(&def.object)
                }
                _ => true,
            })
        })
    }
}
impl NativeApp {
    pub(super) fn accept_location_resources(
        &mut self,
        session: u64,
        resources: u64,
        result: Result<Arc<SceneAsset>, String>,
    ) {
        if session != self.scene.generation || resources != self.scene.animator_generation {
            return;
        }
        self.scene.locations.assets = None;
        match result {
            Err(error) => {
                self.scene.locations.retry_assets =
                    Some(std::time::Instant::now() + std::time::Duration::from_millis(500));
                eprintln!("location assets: {error}; retained scene, retry pending");
            }
            Ok(asset) => {
                self.scene.locations.retry_assets = None;
                let Some(loaded) = &mut self.scene.loaded else {
                    return;
                };
                if loaded.asset.descriptor() != asset.descriptor() {
                    return;
                }
                loaded.asset = Arc::clone(&asset);
                self.scene.animator_generation = self.scene.animator_generation.wrapping_add(1);
                if let Some(worker) = &mut self.scene.animator {
                    worker.replace_resources(Arc::clone(&asset), self.scene.animator_generation);
                }
                self.scene.animator_in_flight = None;
                self.scene.pending_dynamic = None;
                if let Err(error) =
                    self.scene
                        .start_entity_loader(asset, self.scene.generation, self.proxy.clone())
                {
                    eprintln!("location entity dependencies: {error}");
                }
            }
        }
    }
}
