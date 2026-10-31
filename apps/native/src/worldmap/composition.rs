//! One bounded map-composition job and one coalesced latest request.
#[cfg(test)]
mod tests;
use super::MapView;
use rustclient_application::scripts::worldmap::WorldMapState;
use rustclient_cache::osrs240::world_map::{
    MapCoord, WorldMapArea, WorldMapComposite, WorldMapElement,
};
use rustclient_platform::{CpuJob, CpuJobExecutor, JobCancellation, JobIdentity, JobPriority};
use std::{
    collections::{BTreeMap, BTreeSet},
    sync::Arc,
    time::{Duration, Instant},
};

pub(super) struct MapInput {
    session: u64,
    map: i32,
    resources: u64,
    viewport: [u32; 2],
    overview: [u32; 2],
    center: [i32; 2],
    zoom: u32,
    display: [i32; 2],
    player: Option<MapCoord>,
    fallback_area: Option<WorldMapArea>,
    composite: Option<Arc<WorldMapComposite>>,
    elements: Arc<BTreeMap<u32, WorldMapElement>>,
    disabled_elements: BTreeSet<u32>,
    disabled_categories: BTreeSet<u32>,
    disabled: bool,
    flashing_elements: BTreeSet<u32>,
    flashing_categories: BTreeSet<u32>,
    flash: bool,
    flash_visible: bool,
    regions: Arc<BTreeMap<[i32; 2], Arc<rustclient_scene::world_map::RegionImage>>>,
    fonts: Arc<
        BTreeMap<
            rustclient_cache::osrs240::world_map::WorldMapLabelSize,
            Arc<rustclient_cache::BitmapFont>,
        >,
    >,
    sprites: Arc<BTreeMap<u32, Arc<rustclient_cache::Sprite>>>,
}
struct View<'a> {
    state: &'a WorldMapState,
    assets: &'a MapView,
    session: u64,
    viewport: [u32; 2],
    overview: [u32; 2],
    player: Option<MapCoord>,
}
impl MapInput {
    fn matches(&self, view: &View<'_>) -> bool {
        let state = view.state;
        self.session == view.session
            && self.map == state.current_map_area_id
            && self.resources == view.assets.resource_revision
            && self.viewport == view.viewport
            && self.overview == view.overview
            && self.center == state.center_tile
            && self.zoom == state.zoom.to_bits()
            && self.display == state.display_size
            && self.player == view.player
            && self.disabled_elements == state.disabled_elements
            && self.disabled_categories == state.disabled_categories
            && self.disabled == state.elements_disabled
            && self.flashing_elements == state.flashing_elements
            && self.flashing_categories == state.flashing_categories
            && self.flash == (self.flash_visible && state.flash_cycle % 20 < 10)
            && Arc::ptr_eq(&self.elements, &state.elements)
            && match (&self.composite, &state.active_composite) {
                (Some(a), Some(b)) => Arc::ptr_eq(a, b),
                (None, None) => self.fallback_area.as_ref() == state.active_area(),
                _ => false,
            }
    }
    fn capture(view: &View<'_>) -> Self {
        let state = view.state;
        let flash_visible = rustclient_scene::world_map::WorldMapFlashInputs {
            size: view.viewport,
            center: state.center_tile,
            zoom: state.zoom,
            composite: state.active_composite.as_deref(),
            regions: &view.assets.regions,
            elements: &state.elements,
            disabled_elements: &state.disabled_elements,
            disabled_categories: &state.disabled_categories,
            flashing_elements: &state.flashing_elements,
            flashing_categories: &state.flashing_categories,
            disabled: state.elements_disabled,
        }
        .visible();
        Self {
            session: view.session,
            map: state.current_map_area_id,
            resources: view.assets.resource_revision,
            viewport: view.viewport,
            overview: view.overview,
            center: state.center_tile,
            zoom: state.zoom.to_bits(),
            display: state.display_size,
            player: view.player,
            fallback_area: state
                .active_area()
                .filter(|_| state.active_composite.is_none())
                .cloned(),
            composite: state.active_composite.clone(),
            elements: state.elements.clone(),
            disabled_elements: state.disabled_elements.clone(),
            disabled_categories: state.disabled_categories.clone(),
            disabled: state.elements_disabled,
            flashing_elements: state.flashing_elements.clone(),
            flashing_categories: state.flashing_categories.clone(),
            flash: flash_visible && state.flash_cycle % 20 < 10,
            flash_visible,
            regions: view.assets.regions.clone(),
            fonts: view.assets.fonts.clone(),
            sprites: view.assets.sprites.clone(),
        }
    }
    fn compose(
        self: &Arc<Self>,
        cancellation: &JobCancellation,
        buffers: MapBuffers,
    ) -> Result<MapPixels, String> {
        for size in [self.viewport, self.overview] {
            if size.into_iter().any(|side| side > 2048) {
                return Err(
                    "world-map image exceeds the existing 2048-pixel dynamic-image limit".into(),
                );
            }
        }
        let start = Instant::now();
        let MapBuffers {
            mut viewport,
            mut overview,
        } = buffers;
        let area = self
            .composite
            .as_deref()
            .map(|c| &c.area)
            .or(self.fallback_area.as_ref());
        if self.viewport.contains(&0) {
            viewport.clear();
        } else {
            rustclient_scene::world_map::compose_world_map_viewport(
                self.viewport[0],
                self.viewport[1],
                self.center,
                f32::from_bits(self.zoom),
                area,
                self.composite.as_deref(),
                &self.elements,
                &self.sprites,
                &self.disabled_elements,
                &self.disabled_categories,
                self.disabled,
                &self.flashing_elements,
                &self.flashing_categories,
                if self.flash { 0 } else { 10 },
                self.player,
                &self.regions,
                &self.fonts,
                &mut viewport,
            );
        }
        if cancellation.is_cancelled() {
            return Err("map composition revoked".into());
        }
        if self.overview.contains(&0) {
            overview.clear();
        } else {
            rustclient_scene::world_map::compose_world_map_overview(
                self.overview[0],
                self.overview[1],
                self.center,
                self.display,
                area,
                self.composite.as_deref(),
                &mut overview,
            );
        }
        Ok(MapPixels {
            input: self.clone(),
            viewport,
            overview,
            cpu: start.elapsed(),
            revision: 0,
        })
    }
}
#[derive(Default)]
struct MapBuffers {
    viewport: Vec<u8>,
    overview: Vec<u8>,
}
pub(crate) struct MapPixels {
    input: Arc<MapInput>,
    pub viewport: Vec<u8>,
    pub overview: Vec<u8>,
    pub cpu: Duration,
    pub revision: u64,
}
impl MapPixels {
    pub fn viewport_size(&self) -> [u32; 2] {
        self.input.viewport
    }
    pub fn overview_size(&self) -> [u32; 2] {
        self.input.overview
    }
}

#[derive(Default)]
pub(crate) struct MapComposer {
    desired: Option<Arc<MapInput>>,
    accepted: Option<Arc<MapInput>>,
    running: Option<JobIdentity>,
    completed: Option<MapPixels>,
    cancellation: JobCancellation,
    next_request: u64,
    retry_at: Option<Instant>,
    pub submitted: u64,
    pub published: u64,
    pub stale: u64,
    exhausted: bool,
    spare: MapBuffers,
}
impl MapComposer {
    pub fn clear(&mut self) {
        if self.desired.is_none()
            && self.accepted.is_none()
            && self.running.is_none()
            && self.completed.is_none()
            && self.spare.viewport.capacity() == 0
            && self.spare.overview.capacity() == 0
        {
            return;
        }
        self.cancellation.cancel();
        self.cancellation = JobCancellation::default();
        self.desired = None;
        self.accepted = None;
        self.running = None;
        self.completed = None;
        self.retry_at = None;
        self.spare = MapBuffers::default();
    }
    pub fn recycle(&mut self, viewport: Vec<u8>, overview: Vec<u8>) {
        self.spare = MapBuffers { viewport, overview };
    }
    pub fn receive(&mut self, identity: JobIdentity, result: Result<MapPixels, String>) {
        if self.running != Some(identity) {
            self.stale += 1;
            return;
        }
        self.running = None;
        match result {
            Ok(mut pixels) => {
                pixels.revision = identity.request;
                self.completed = Some(pixels);
            }
            Err(error) => {
                eprintln!("world map composition: {error}");
                self.retry_at = Some(Instant::now() + Duration::from_millis(250));
            }
        }
    }
    #[expect(
        clippy::too_many_arguments,
        reason = "native composition wiring; one owned input snapshot"
    )]
    pub fn request(
        &mut self,
        state: &WorldMapState,
        assets: &MapView,
        viewport: [u32; 2],
        overview: [u32; 2],
        player: Option<MapCoord>,
        session: u64,
        executor: &dyn CpuJobExecutor,
        complete: impl Fn(JobIdentity, Result<MapPixels, String>) + Send + Sync + Clone + 'static,
    ) -> Option<MapPixels> {
        if self.exhausted {
            return None;
        }
        let view = View {
            state,
            assets,
            session,
            viewport,
            overview,
            player,
        };
        if !self
            .desired
            .as_ref()
            .is_some_and(|input| input.matches(&view))
        {
            self.desired = Some(Arc::new(MapInput::capture(&view)));
            self.retry_at = None;
        }
        let mut published = None;
        if let Some(pixels) = self.completed.take() {
            if pixels.input.matches(&view) {
                self.accepted = Some(pixels.input.clone());
                self.published += 1;
                published = Some(pixels);
            } else {
                self.stale += 1;
                self.recycle(pixels.viewport, pixels.overview);
            }
        }
        if self.running.is_some()
            || self
                .accepted
                .as_ref()
                .is_some_and(|input| input.matches(&view))
            || self.retry_at.is_some_and(|at| at > Instant::now())
        {
            return published;
        }
        let Some(next) = self.next_request.checked_add(1) else {
            self.exhausted = true;
            eprintln!("map projection request counter exhausted");
            return published;
        };
        self.next_request = next;
        let identity = JobIdentity {
            session,
            scene: None,
            resources: assets.resource_revision,
            request: self.next_request,
        };
        let input = self.desired.as_ref()?.clone();
        let fault_complete = complete.clone();
        let buffers = std::mem::take(&mut self.spare);
        let job = CpuJob::new(
            identity,
            JobPriority::Visible,
            self.cancellation.clone(),
            move |cancel| {
                let result = input.compose(cancel, buffers);
                if !cancel.is_cancelled() {
                    complete(identity, result);
                }
            },
        )
        .with_fault_handler(move || {
            fault_complete(identity, Err("map composition worker fault".into()));
        });
        if executor.try_submit(job).is_ok() {
            self.running = Some(identity);
            self.submitted += 1;
        }
        published
    }
}
impl Drop for MapComposer {
    fn drop(&mut self) {
        self.cancellation.cancel();
    }
}
