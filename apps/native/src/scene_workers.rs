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

pub(super) struct SceneryWorker {
    context: WorkerContext,
}
impl SceneryWorker {
    pub fn new(context: WorkerContext) -> Self {
        Self { context }
    }
    pub fn try_send(&self, mut inputs: SceneryInputs) -> Result<(), JobRejected> {
        let asset = Arc::clone(&self.context.asset);
        let proxy = self.context.proxy.clone();
        let fault_proxy = proxy.clone();
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
                let result =
                    rustclient_scene::build_dynamic(&asset, &inputs).map_err(|e| e.to_string());
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
