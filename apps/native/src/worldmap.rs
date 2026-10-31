//! Off-thread region/resource loading for the map widget. No transport or semantic state.
mod composition;
use crate::{
    LoadEvent,
    scene::{SceneView, SharedCacheSlot},
};
pub(super) use composition::{MapComposer, MapPixels};
use rustclient_application::scripts::worldmap::WorldMapState;
use rustclient_cache::{
    BitmapFont, CacheProfile, Sprite,
    osrs240::world_map::{WorldMapLabelSize, WorldMapObject},
};
use rustclient_scene::world_map::{RegionImage, RegionPainter, pixels_per_tile};
use std::{
    collections::{BTreeMap, BTreeSet},
    path::Path,
    sync::Arc,
};
use winit::event_loop::EventLoopProxy;
#[derive(Default)]
pub(super) struct MapView {
    map: Option<u32>,
    ppt: u8,
    pending: bool,
    request_id: u64,
    cancellation: rustclient_platform::JobCancellation,
    pending_context: Option<(u32, u8)>,
    pending_regions: Vec<[i32; 2]>,
    failed: BTreeSet<[i32; 2]>,
    settled: Option<(u32, u32, [u32; 2], [i32; 2])>,
    pub regions: Arc<BTreeMap<[i32; 2], Arc<RegionImage>>>,
    pub fonts: Arc<BTreeMap<WorldMapLabelSize, Arc<BitmapFont>>>,
    pub sprites: Arc<BTreeMap<u32, Arc<Sprite>>>,
    pub resource_revision: u64,
}
pub(super) struct MapBatch {
    pub map: u32,
    pub ppt: u8,
    pub regions: BTreeMap<[i32; 2], Arc<RegionImage>>,
    pub sprites: BTreeMap<u32, Arc<Sprite>>,
    pub fonts: BTreeMap<WorldMapLabelSize, Arc<BitmapFont>>,
}
impl MapView {
    fn select_raster_scale(&mut self, map: u32, ppt: u8) {
        if self.map != Some(map) || self.ppt != ppt {
            self.cancellation.cancel();
            self.cancellation = rustclient_platform::JobCancellation::default();
            self.pending = false;
            Arc::make_mut(&mut self.regions).clear();
            self.resource_revision = self.resource_revision.wrapping_add(1);
            self.failed.clear();
            self.map = Some(map);
            self.ppt = ppt;
        }
    }

    fn clear_missing_area(&mut self) {
        if self.map.is_some() || !self.regions.is_empty() || self.pending {
            self.cancellation.cancel();
            self.cancellation = rustclient_platform::JobCancellation::default();
            self.pending = false;
            self.map = None;
            self.settled = None;
            Arc::make_mut(&mut self.regions).clear();
            self.failed.clear();
            self.resource_revision = self.resource_revision.wrapping_add(1);
        }
    }

    /// At most one bounded batch is in flight. Old-area/zoom results cannot replace current data.
    #[expect(
        clippy::too_many_arguments,
        clippy::cast_possible_truncation,
        clippy::cast_precision_loss,
        clippy::cast_sign_loss,
        reason = "bounded world-map viewport and integer reference raster scale"
    )]
    pub fn request(
        &mut self,
        state: &WorldMapState,
        size: [u32; 2],
        path: &Path,
        profile: CacheProfile,
        shared: &SharedCacheSlot,
        proxy: EventLoopProxy<LoadEvent>,
        executor: &dyn rustclient_platform::CpuJobExecutor,
        session: u64,
    ) {
        let Some(composite) = &state.active_composite else {
            self.clear_missing_area();
            return;
        };
        let map = composite.area.id;
        let request_key = (map, state.zoom.to_bits(), size, state.center_tile);
        if self.settled == Some(request_key) {
            return;
        }
        self.settled = None;
        let zoom = pixels_per_tile(state.zoom);
        let ppt = zoom.ceil().clamp(1., 8.) as u8;
        self.select_raster_scale(map, ppt);
        if self.pending {
            return;
        }
        let radius = [
            (size[0] as f32 / (zoom * 128.)).ceil() as i32 + 1,
            (size[1] as f32 / (zoom * 128.)).ceil() as i32 + 1,
        ];
        let center = state.center_tile.map(|n| n.div_euclid(64));
        let mut visible = Vec::new();
        for x in (center[0] - radius[0]).max(composite.area.region_low[0])
            ..=(center[0] + radius[0]).min(composite.area.region_high[0])
        {
            for y in (center[1] - radius[1]).max(composite.area.region_low[1])
                ..=(center[1] + radius[1]).min(composite.area.region_high[1])
            {
                visible.push([x, y]);
            }
        }
        visible.sort_by_key(|p| (p[0] - center[0]).abs() + (p[1] - center[1]).abs());
        let bytes_per_region = (64 * usize::from(ppt)).pow(2) * 4;
        visible.truncate((32 * 1024 * 1024 / bytes_per_region).max(16));
        // Retain only nearby rasters, within a 32 MiB raster budget.
        let previous = self.regions.len();
        Arc::make_mut(&mut self.regions).retain(|region, _| visible.contains(region));
        if previous != self.regions.len() {
            self.resource_revision = self.resource_revision.wrapping_add(1);
        }
        visible
            .retain(|region| !self.regions.contains_key(region) && !self.failed.contains(region));
        visible.truncate(16);
        if visible.is_empty() {
            self.settled = Some(request_key);
            return;
        }
        // The overview can be tens of MB and is not a geography dependency.
        let composite = rustclient_cache::osrs240::world_map::WorldMapComposite {
            area: composite.area.clone(),
            whole: composite.whole.clone(),
            chunks: composite.chunks.clone(),
            icons: composite.icons.clone(),
            overview: None,
        };
        let elements = state.elements.clone();
        self.pending_regions.clone_from(&visible);
        self.pending_context = Some((map, ppt));
        let known: BTreeSet<_> = self.sprites.keys().copied().collect();
        let path = path.to_path_buf();
        let shared = shared.clone();
        self.request_id = self.request_id.wrapping_add(1);
        let identity = rustclient_platform::JobIdentity {
            session,
            request: self.request_id,
            ..Default::default()
        };
        let fault_proxy = proxy.clone();
        let job = rustclient_platform::CpuJob::new(
            identity,
            rustclient_platform::JobPriority::Visible,
            self.cancellation.clone(),
            move |cancellation| {
                let result = SceneView::open_shared_cache(&path, &shared).and_then(|cache| {
                    load_batch(&cache, profile, &composite, visible, ppt, &elements, &known)
                });
                if cancellation.is_cancelled() {
                    return;
                }
                let _ = proxy.send_event(LoadEvent::WorldMapAssets(identity, Box::new(result)));
            },
        )
        .with_fault_handler(move || {
            let _ = fault_proxy.send_event(LoadEvent::WorldMapAssets(
                identity,
                Box::new(Err(
                    "world map asset worker panicked; reopen the map to retry".into(),
                )),
            ));
        });
        self.pending = executor.try_submit(job).is_ok();
        // Rejected admission is retried on the next projection, not recorded as a decode failure.
        if !self.pending {
            self.pending_context = None;
            self.pending_regions.clear();
        }
    }
    pub fn receive(
        &mut self,
        identity: rustclient_platform::JobIdentity,
        result: Result<MapBatch, String>,
    ) {
        if identity.request != self.request_id {
            return;
        }
        self.pending = false;
        match result {
            Ok(batch) if self.map == Some(batch.map) && self.ppt == batch.ppt => {
                Arc::make_mut(&mut self.regions).extend(batch.regions);
                self.fonts = Arc::new(batch.fonts);
                Arc::make_mut(&mut self.sprites).extend(batch.sprites);
                self.resource_revision = self.resource_revision.wrapping_add(1);
            }
            Ok(_) => {}
            Err(error) => {
                if self.pending_context == self.map.map(|map| (map, self.ppt)) {
                    self.failed.extend(self.pending_regions.iter().copied());
                }
                eprintln!("world map assets: {error}");
            }
        }
    }
}

fn load_batch(
    cache: &rustclient_cache::Cache<impl rustclient_platform::ReadOnlyBlobs>,
    profile: CacheProfile,
    composite: &rustclient_cache::osrs240::world_map::WorldMapComposite,
    visible: Vec<[i32; 2]>,
    ppt: u8,
    elements: &BTreeMap<u32, rustclient_cache::osrs240::world_map::WorldMapElement>,
    known: &BTreeSet<u32>,
) -> Result<MapBatch, String> {
    let map = composite.area.id;
    let overlays = profile
        .load_world_map_overlays(cache)
        .map_err(|e| e.to_string())?;
    let textures = profile
        .load_world_map_texture_colors(cache)
        .map_err(|e| e.to_string())?;
    let scenes = cache.named_sprites("mapscene").map_err(|e| e.to_string())?;
    let mut geography = Vec::new();
    let mut wanted = BTreeSet::new();
    for region in visible {
        let data = profile
            .load_world_map_region(cache, composite, region)
            .map_err(|e| e.to_string())?;
        wanted.extend(
            data.tiles
                .iter()
                .flat_map(|t| t.decorations.iter().flatten().filter_map(|d| d.object)),
        );
        geography.push(data);
    }
    let mut objects: BTreeMap<u32, WorldMapObject> = BTreeMap::new();
    profile
        .load_world_map_objects(cache, &wanted, &mut objects)
        .map_err(|e| e.to_string())?;
    let painter = RegionPainter::default();
    let regions: BTreeMap<_, _> = geography
        .iter()
        .map(|data| {
            (
                data.region,
                Arc::new(painter.render(
                    data,
                    ppt,
                    composite.area.region_background,
                    &overlays,
                    &textures,
                    &objects,
                    &scenes,
                )),
            )
        })
        .collect();
    let icon_ids: BTreeSet<_> = regions
        .values()
        .flat_map(|r| r.icons.iter())
        .chain(composite.icons.iter().filter(|icon| {
            regions.contains_key(&[icon.coord.x.div_euclid(64), icon.coord.y.div_euclid(64)])
        }))
        .filter_map(|icon| icon.element)
        .collect();
    let mut sprites = BTreeMap::new();
    for sprite in icon_ids
        .into_iter()
        .filter_map(|id| elements.get(&id).and_then(|e| e.sprite))
    {
        if !known.contains(&sprite)
            && !sprites.contains_key(&sprite)
            && let Some(image) = cache.sprite(sprite).map_err(|e| e.to_string())?
        {
            sprites.insert(sprite, Arc::new(image));
        }
    }
    let mut fonts = BTreeMap::new();
    for size in [
        WorldMapLabelSize::Small,
        WorldMapLabelSize::Medium,
        WorldMapLabelSize::Large,
    ] {
        fonts.insert(
            size,
            Arc::new(
                profile
                    .load_world_map_font(cache, size)
                    .map_err(|e| e.to_string())?,
            ),
        );
    }
    Ok(MapBatch {
        map,
        ppt,
        regions,
        sprites,
        fonts,
    })
}

impl Drop for MapView {
    fn drop(&mut self) {
        self.cancellation.cancel();
    }
}

impl crate::NativeApp {
    pub(super) fn cancel_map_input(&mut self) {
        self.world_map_input_bounds = None;
        if let Some(scripts) = &mut self.scripts {
            scripts.world_map.cancel_input();
        }
    }
    pub(super) fn map_pointer(&mut self, position: [f32; 2], pressed: bool, held: bool) {
        let Some(viewport) = self.world_map_input_bounds else {
            return;
        };
        let Some(scripts) = &mut self.scripts else {
            return;
        };
        let staff_shortcut = self
            .state
            .authenticated_player()
            .is_some_and(|p| p.staff_level >= 2)
            && self.modifiers.control_key()
            && self.modifiers.shift_key();
        if let Some(tile) =
            scripts
                .world_map
                .pointer(rustclient_application::scripts::worldmap::WorldMapPointer {
                    viewport,
                    position,
                    pressed,
                    held,
                    at: self.cycle_origin.elapsed(),
                    staff_shortcut,
                })
        {
            self.send_script_intents(vec![rustclient_protocol::ClientIntent::WorldMapClick(tile)]);
        }
    }
}

impl crate::NativeApp {
    pub(super) fn clear_map_projection(&mut self) {
        self.map_composer.clear();
        self.cancel_map_input();
        self.world_map_pixels = Vec::new();
        self.world_map_overview_pixels = Vec::new();
        self.world_map_size = [0, 0];
        self.world_map_overview_size = [0, 0];
    }
    pub(super) fn update_map_projection(&mut self) {
        let size = |image| {
            self.frame
                .quads
                .iter()
                .find(|quad| quad.image == image)
                .map_or([0, 0], |quad| {
                    #[expect(
                        clippy::cast_possible_truncation,
                        clippy::cast_sign_loss,
                        reason = "validated widget bounds"
                    )]
                    [
                        quad.rect.width.round().max(1.) as u32,
                        quad.rect.height.round().max(1.) as u32,
                    ]
                })
        };
        let viewport = size(rustclient_game_ui::UiImageId::WorldMap);
        let overview = size(rustclient_game_ui::UiImageId::WorldMapOverview);
        if viewport.contains(&0) && overview.contains(&0) {
            self.clear_map_projection();
            return;
        }
        let Ok(pool) = self.scene.cpu_jobs() else {
            return;
        };
        let Some(scripts) = &mut self.scripts else {
            return;
        };
        if !viewport.contains(&0) {
            self.map_view.request(
                &scripts.world_map,
                viewport,
                &self.widget_cache_dir,
                self.widget_cache_profile,
                &self.scene.shared_cache,
                self.proxy.clone(),
                pool.as_ref(),
                self.connection_generation,
            );
            scripts.world_map.set_display_pixels(viewport);
        }
        let player = self.state.entities().local_player().map(|player| {
            rustclient_cache::osrs240::world_map::MapCoord::new(
                i32::from(player.tile.plane.get()),
                player.tile.x,
                player.tile.y,
            )
        });
        let proxy = self.proxy.clone();
        if let Some(pixels) = self.map_composer.request(
            &scripts.world_map,
            &self.map_view,
            viewport,
            overview,
            player,
            self.connection_generation,
            pool.as_ref(),
            move |identity, result| {
                let _ = proxy.send_event(LoadEvent::MapComposed(identity, Box::new(result)));
            },
        ) {
            self.world_map_revision = pixels.revision;
            self.world_map_size = pixels.viewport_size();
            self.world_map_overview_size = pixels.overview_size();
            let viewport = std::mem::replace(&mut self.world_map_pixels, pixels.viewport);
            let overview = std::mem::replace(&mut self.world_map_overview_pixels, pixels.overview);
            self.map_composer.recycle(viewport, overview);
            self.performance.event(|| {
                format!(
                    "map pixels published revision={} composition_ms={:.3}",
                    self.world_map_revision,
                    pixels.cpu.as_secs_f64() * 1000.
                )
            });
        }
    }
}
