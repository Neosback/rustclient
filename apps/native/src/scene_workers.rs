//! Disposable projection services sharing the composition root's bounded CPU pool.
use crate::{
    LoadEvent,
    scene::{SceneView, SharedCacheSlot},
};
use rustclient_cache::{
    CacheProfile,
    osrs240::entity_assets::{EntityLoader, EntityRequest},
};
use rustclient_platform::{
    CpuJob, CpuJobExecutor, JobCancellation, JobIdentity, JobPriority, JobRejected,
};
use rustclient_platform_native::NativeJobPool;
use rustclient_scene::{SceneAsset, SceneryInputs};
use std::{
    path::PathBuf,
    sync::{Arc, Mutex},
};
use winit::event_loop::EventLoopProxy;

pub(super) struct WorkerContext {
    pub pool: Arc<NativeJobPool>,
    pub cancellation: JobCancellation,
    pub identity: JobIdentity,
    pub proxy: EventLoopProxy<LoadEvent>,
    pub asset: Arc<SceneAsset>,
}
impl WorkerContext {
    fn submit(
        &self,
        identity: JobIdentity,
        work: impl FnOnce(&JobCancellation) + Send + 'static,
        fault: impl FnOnce() + Send + 'static,
    ) -> Result<(), JobRejected> {
        self.pool.try_submit(
            CpuJob::new(
                identity,
                JobPriority::Visible,
                self.cancellation.clone(),
                work,
            )
            .with_fault_handler(fault),
        )
    }
}

pub(super) struct DynamicProjection {
    pub geometry: rustclient_scene::SceneGeometry,
    pub prepared: rustclient_renderer::PreparedLocationUpdate,
    pub minimap: Arc<rustclient_scene::minimap::MinimapImage>,
    pub plane: rustclient_model::Plane,
    pub queue_wait: std::time::Duration,
    pub preparation: std::time::Duration,
    pub rebuilt_zones: u64,
}
struct SceneryPreparation {
    projection: rustclient_scene::LocationProjection,
    prepared: Option<rustclient_renderer::PreparedLocationUpdate>,
    minimap: Option<(
        rustclient_model::Plane,
        Arc<rustclient_scene::minimap::MinimapImage>,
    )>,
}
impl SceneryPreparation {
    fn extract(
        &mut self,
        asset: &SceneAsset,
        inputs: &SceneryInputs,
        plane: rustclient_model::Plane,
    ) -> Result<DynamicProjection, String> {
        let start = std::time::Instant::now();
        let builds = self.projection.zone_builds();
        let geometry = self
            .projection
            .extract(asset, inputs)
            .map_err(|e| e.to_string())?;
        let prepared =
            rustclient_renderer::PreparedLocationUpdate::prepare(&geometry, self.prepared.as_ref())
                .map_err(|e| e.to_string())?;
        self.prepared = Some(prepared.clone());
        if self.projection.zone_builds() != builds
            || self.minimap.as_ref().is_none_or(|(old, _)| *old != plane)
        {
            self.minimap = None;
            self.minimap = Some((
                plane,
                Arc::new(
                    rustclient_scene::minimap::build_scene_minimap_with_inputs(
                        asset, plane, inputs,
                    )
                    .map_err(|e| e.to_string())?,
                ),
            ));
        }
        let minimap = Arc::clone(&self.minimap.as_ref().ok_or("missing minimap projection")?.1);
        Ok(DynamicProjection {
            geometry,
            prepared,
            minimap,
            plane,
            queue_wait: std::time::Duration::ZERO,
            preparation: start.elapsed(),
            rebuilt_zones: self.projection.zone_builds() - builds,
        })
    }
}
pub(super) struct SceneryWorker {
    context: WorkerContext,
    projection: Arc<Mutex<SceneryPreparation>>,
}
impl SceneryWorker {
    pub fn new(context: WorkerContext, inputs: SceneryInputs) -> Self {
        let projection = Arc::new(Mutex::new(SceneryPreparation {
            projection: rustclient_scene::LocationProjection::new(&context.asset, inputs),
            prepared: None,
            minimap: None,
        }));
        Self {
            context,
            projection,
        }
    }
    pub fn replace_resources(&mut self, asset: Arc<SceneAsset>, resources: u64) {
        self.context.cancellation.cancel();
        self.context.cancellation = JobCancellation::default();
        self.context.asset = asset;
        self.context.identity.resources = resources;
    }
    pub fn try_send(
        &self,
        mut inputs: SceneryInputs,
        plane: rustclient_model::Plane,
        invalidate: bool,
    ) -> Result<(), JobRejected> {
        let projection = Arc::clone(&self.projection);
        let asset = Arc::clone(&self.context.asset);
        let proxy = self.context.proxy.clone();
        let fault_proxy = proxy.clone();
        let requested = std::time::Instant::now();
        let cycle = inputs.cycle;
        let id = JobIdentity {
            request: cycle.get(),
            ..self.context.identity
        };
        self.context.submit(
            id,
            move |cancellation| {
                inputs.retain_scene_dependencies(&asset);
                if cancellation.is_cancelled() {
                    return;
                }
                let queued = requested.elapsed();
                let mut retained = projection
                    .lock()
                    .unwrap_or_else(std::sync::PoisonError::into_inner);
                if invalidate {
                    retained.projection.invalidate();
                }
                let result = retained.extract(&asset, &inputs, plane).map(|mut output| {
                    output.queue_wait = queued;
                    output
                });
                drop(retained);
                if cancellation.is_cancelled() {
                    return;
                }
                let _ = proxy.send_event(LoadEvent::DynamicReady(
                    id.session,
                    id.resources,
                    Box::new(inputs),
                    Box::new(result),
                ));
            },
            move || {
                let _ = fault_proxy.send_event(LoadEvent::DynamicFault(
                    id.session,
                    id.resources,
                    cycle,
                ));
            },
        )
    }
}
impl Drop for SceneryWorker {
    fn drop(&mut self) {
        self.context.cancellation.cancel();
    }
}

pub(super) struct EntityWorker {
    context: WorkerContext,
    path: PathBuf,
    profile: CacheProfile,
    cache: SharedCacheSlot,
    loader: Arc<Mutex<Option<EntityLoader>>>,
    next_request: std::sync::atomic::AtomicU64,
}
impl EntityWorker {
    pub fn new(
        context: WorkerContext,
        path: PathBuf,
        profile: CacheProfile,
        cache: SharedCacheSlot,
    ) -> Self {
        Self {
            context,
            path,
            profile,
            cache,
            loader: Arc::default(),
            next_request: 0.into(),
        }
    }
    pub fn try_send(&self, request: EntityRequest) -> Result<(), JobRejected> {
        let path = self.path.clone();
        let cache = Arc::clone(&self.cache);
        let profile = self.profile;
        let asset = Arc::clone(&self.context.asset);
        let loader = Arc::clone(&self.loader);
        let proxy = self.context.proxy.clone();
        let fault_proxy = proxy.clone();
        let id = JobIdentity {
            request: self
                .next_request
                .fetch_add(1, std::sync::atomic::Ordering::Relaxed),
            ..self.context.identity
        };
        self.context.submit(
            id,
            move |cancellation| {
                let result = SceneView::open_shared_cache(&path, &cache).map(|cache| {
                    let mut loader = loader
                        .lock()
                        .unwrap_or_else(std::sync::PoisonError::into_inner);
                    loader
                        .get_or_insert_with(|| EntityLoader::new(profile, asset.sources()))
                        .load(&cache, &request)
                });
                if cancellation.is_cancelled() {
                    return;
                }
                let event = match result {
                    Ok(assets) => {
                        LoadEvent::EntityAssets(id.session, id.resources, Box::new(assets))
                    }
                    Err(error) => LoadEvent::EntityFault(id.session, id.resources, error),
                };
                let _ = proxy.send_event(event);
            },
            move || {
                let _ = fault_proxy.send_event(LoadEvent::EntityFault(
                    id.session,
                    id.resources,
                    "entity asset worker panicked; reconnect to retry".into(),
                ));
            },
        )
    }
}
impl Drop for EntityWorker {
    fn drop(&mut self) {
        self.context.cancellation.cancel();
    }
}
