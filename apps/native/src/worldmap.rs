//! Off-thread region/resource loading for the map widget. No transport or semantic state.
use crate::{
    LoadEvent,
    scene::{SceneView, SharedCacheSlot},
};
use rustclient_application::scripts::worldmap::WorldMapState;
use rustclient_cache::{
    BitmapFont, CacheProfile, Sprite,
    osrs240::world_map::{WorldMapLabelSize, WorldMapObject},
};
use rustclient_scene::world_map::{RegionImage, RegionPainter, pixels_per_tile};
use std::{
    collections::{BTreeMap, BTreeSet},
    path::Path,
};
use winit::event_loop::EventLoopProxy;
#[derive(Default)]
pub(super) struct MapView {
    map: Option<u32>,
    ppt: u8,
    pending: bool,
    pending_context: Option<(u32, u8)>,
    pending_regions: Vec<[i32; 2]>,
    failed: BTreeSet<[i32; 2]>,
    settled: Option<(u32, u32, [u32; 2], [i32; 2])>,
    pub regions: BTreeMap<[i32; 2], RegionImage>,
    pub fonts: BTreeMap<WorldMapLabelSize, BitmapFont>,
}
pub(super) struct MapBatch {
    pub map: u32,
    pub ppt: u8,
    pub regions: BTreeMap<[i32; 2], RegionImage>,
    pub sprites: BTreeMap<u32, Sprite>,
    pub fonts: BTreeMap<WorldMapLabelSize, BitmapFont>,
}
impl MapView {
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
        known_sprites: &BTreeMap<u32, Sprite>,
        proxy: EventLoopProxy<LoadEvent>,
    ) {
        let Some(composite) = &state.active_composite else {
            self.map = None;
            self.settled = None;
            self.regions.clear();
            self.failed.clear();
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
        if self.map != Some(map) || self.ppt != ppt {
            self.regions.clear();
            self.failed.clear();
            self.map = Some(map);
            self.ppt = ppt;
        }
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
        self.regions.retain(|region, _| visible.contains(region));
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
        let known: BTreeSet<_> = known_sprites.keys().copied().collect();
        let path = path.to_path_buf();
        let shared = shared.clone();
        let result = std::thread::Builder::new()
            .name("world-map-assets".into())
            .spawn(move || {
                let result = SceneView::open_shared_cache(&path, &shared).and_then(|cache| {
                    load_batch(&cache, profile, &composite, visible, ppt, &elements, &known)
                });
                let _ = proxy.send_event(LoadEvent::WorldMapAssets(Box::new(result)));
            });
        match result {
            Ok(_) => self.pending = true,
            Err(error) => {
                self.failed.extend(self.pending_regions.iter().copied());
                eprintln!("world map worker: {error}");
            }
        }
    }
    pub fn receive(
        &mut self,
        result: Result<MapBatch, String>,
        sprites: &mut BTreeMap<u32, Sprite>,
    ) {
        self.pending = false;
        match result {
            Ok(batch) if self.map == Some(batch.map) && self.ppt == batch.ppt => {
                self.regions.extend(batch.regions);
                self.fonts = batch.fonts;
                sprites.extend(batch.sprites);
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
                painter.render(
                    data,
                    ppt,
                    composite.area.region_background,
                    &overlays,
                    &textures,
                    &objects,
                    &scenes,
                ),
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
            sprites.insert(sprite, image);
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
            profile
                .load_world_map_font(cache, size)
                .map_err(|e| e.to_string())?,
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
