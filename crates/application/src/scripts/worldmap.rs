//! `worldmap_*` and `mec_*` script opcodes over the cache's world map definitions.
#![allow(
    clippy::too_many_lines,
    clippy::collapsible_if,
    clippy::cast_precision_loss,
    clippy::redundant_closure_for_method_calls
)]
use super::{ClientScripts, Result, ScriptHost};
use rustclient_cache::{
    osrs240::world_map::{MapCoord, WorldMapArea, WorldMapComposite, WorldMapElement},
    script::Opcode,
};
use rustclient_client_script::Stacks;
use rustclient_model::ScriptValue;
use rustclient_platform::ReadOnlyBlobs;
use std::collections::{BTreeMap, BTreeSet};

/// State of the world map subsystem, owned by `ClientScripts`.
#[derive(Default)]
pub struct WorldMapState {
    pub areas: BTreeMap<u32, WorldMapArea>,
    pub elements: BTreeMap<u32, WorldMapElement>,
    pub active_composite: Option<WorldMapComposite>,
    pub current_map_area_id: i32,
    pub center_tile: [i32; 2],
    pub target_tile: Option<[i32; 2]>,
    pub zoom: f32,
    pub target_zoom: f32,
    pub display_size: [i32; 2],
    pub flashing_elements: BTreeSet<u32>,
    pub flashing_categories: BTreeSet<u32>,
    pub perpetual_flash: bool,
    pub max_flash_count: i32,
    pub cycles_per_flash: i32,
    pub flash_cycle: i32,
    pub disabled_elements: BTreeSet<u32>,
    pub disabled_categories: BTreeSet<u32>,
    pub elements_disabled: bool,
    pub list_iterator_index: usize,
    pub last_interacted_coord: Option<MapCoord>,
    pub is_loaded: bool,
    pub load_error: Option<String>,
}

impl WorldMapState {
    #[must_use]
    pub fn new() -> Self {
        Self {
            areas: BTreeMap::new(),
            elements: BTreeMap::new(),
            active_composite: None,
            current_map_area_id: -1,
            center_tile: [0, 0],
            target_tile: None,
            zoom: 100.0,
            target_zoom: 100.0,
            display_size: [800, 600],
            flashing_elements: BTreeSet::new(),
            flashing_categories: BTreeSet::new(),
            perpetual_flash: false,
            max_flash_count: 3,
            cycles_per_flash: 20,
            flash_cycle: 0,
            disabled_elements: BTreeSet::new(),
            disabled_categories: BTreeSet::new(),
            elements_disabled: false,
            list_iterator_index: 0,
            last_interacted_coord: None,
            is_loaded: false,
            load_error: None,
        }
    }

    /// Display size queried by scripts is in tiles, rather than canvas pixels.
    #[expect(
        clippy::cast_possible_truncation,
        reason = "bounded canvas pixels divided by a source zoom of at least one"
    )]
    pub fn set_display_pixels(&mut self, size: [u32; 2]) {
        let zoom = f64::from(rustclient_scene::world_map::pixels_per_tile(self.zoom));
        self.display_size = size.map(|pixels| (f64::from(pixels.min(16384)) / zoom).ceil() as i32);
    }

    pub fn reset(&mut self) {
        // Cache assets survive logout; account-dependent interaction/position does not.
        self.center_tile = self
            .active_area()
            .and_then(|area| area.position(area.origin.plane, area.origin.x, area.origin.y))
            .unwrap_or([0, 0]);
        self.target_tile = None;
        self.flashing_elements.clear();
        self.flashing_categories.clear();
        self.list_iterator_index = 0;
        self.last_interacted_coord = None;
        self.flash_cycle = 0;
    }

    fn select_map<S: ReadOnlyBlobs>(
        &mut self,
        cache: &rustclient_cache::Cache<S>,
        profile: rustclient_cache::CacheProfile,
        map_id: i32,
    ) {
        self.current_map_area_id = map_id;
        self.active_composite = None;
        self.is_loaded = false;
        self.load_error = None;
        let Some(area) = u32::try_from(map_id)
            .ok()
            .and_then(|id| self.areas.get(&id))
        else {
            return;
        };
        self.center_tile = area
            .position(area.origin.plane, area.origin.x, area.origin.y)
            .unwrap_or([area.region_low[0] * 64, area.region_low[1] * 64]);
        self.target_tile = None;
        self.zoom = f32::from(area.default_zoom.max(1));
        self.target_zoom = self.zoom;
        match profile.load_world_map_composite(cache, area) {
            Ok(composite) => {
                self.active_composite = Some(composite);
                self.is_loaded = true;
            }
            Err(error) => self.load_error = Some(error.to_string()),
        }
    }

    #[must_use]
    pub fn active_area(&self) -> Option<&WorldMapArea> {
        u32::try_from(self.current_map_area_id)
            .ok()
            .and_then(|id| self.areas.get(&id))
    }
}

impl<S: ReadOnlyBlobs> ClientScripts<S> {
    /// Prepare the default map on the same cache worker as the other startup assets.
    /// # Errors
    /// Missing/malformed assets remain diagnostics; the game interfaces can still be used.
    pub fn initialize_world_map(&mut self) -> Result<()> {
        self.world_map.areas = self
            .profile
            .load_world_map_areas(&self.cache)
            .map_err(|error| error.to_string())?
            .into_iter()
            .map(|area| (area.id, area))
            .collect();
        self.world_map.elements = self
            .profile
            .load_world_map_elements(&self.cache, &mut Vec::new())
            .map_err(|e| e.to_string())?;
        let main = self
            .world_map
            .areas
            .values()
            .find(|area| area.is_main)
            .map(|area| area.id)
            .ok_or("world map has no main area")?;
        let main = i32::try_from(main).map_err(|error| error.to_string())?;
        self.world_map.select_map(&self.cache, self.profile, main);
        if let Some(error) = &self.world_map.load_error {
            return Err(error.clone());
        }
        Ok(())
    }
}

pub(super) fn instruction<S: ReadOnlyBlobs>(
    h: &mut ScriptHost<'_, S>,
    op: Opcode,
    s: &mut Stacks,
) -> Result<bool> {
    if !is_worldmap_opcode(op) {
        return Ok(false);
    }
    let ClientScripts {
        cache,
        profile,
        world_map,
        ..
    } = &mut *h.resources;

    if world_map.areas.is_empty() {
        if let Ok(areas) = profile.load_world_map_areas(cache) {
            world_map.areas = areas.into_iter().map(|a| (a.id, a)).collect();
        }
    }
    if matches!(
        op,
        Opcode::MecText | Opcode::MecTextsize | Opcode::MecCategory | Opcode::MecSprite
    ) && world_map.elements.is_empty()
    {
        let mut warnings = Vec::new();
        if let Ok(elems) = profile.load_world_map_elements(cache, &mut warnings) {
            world_map.elements = elems;
        }
    }

    match op {
        Opcode::WorldmapGetmapname => {
            let map_id = s.int()?;
            let name = if let Ok(id) = u32::try_from(map_id) {
                world_map
                    .areas
                    .get(&id)
                    .map_or_else(String::new, |a| a.external_name.clone())
            } else {
                String::new()
            };
            s.push_object(ScriptValue::Text(name));
        }
        Opcode::WorldmapSetmap => {
            let map_id = s.int()?;
            world_map.select_map(cache, *profile, map_id);
        }
        Opcode::WorldmapGetzoom => {
            #[expect(
                clippy::cast_possible_truncation,
                reason = "zoom percentage fits in i32"
            )]
            s.integers.push(world_map.zoom.round() as i32);
        }
        Opcode::WorldmapSetzoom => {
            let zoom = s.int()?;
            world_map.target_zoom = zoom as f32;
            world_map.zoom = zoom as f32;
        }
        Opcode::WorldmapIsloaded => {
            s.integers.push(i32::from(world_map.is_loaded));
        }
        Opcode::WorldmapJumptodisplaycoord => {
            let packed = s.int()?;
            let coord = MapCoord::from_packed(packed);
            world_map.target_tile = Some([coord.x, coord.y]);
        }
        Opcode::WorldmapJumptodisplaycoordInstant => {
            let packed = s.int()?;
            let coord = MapCoord::from_packed(packed);
            world_map.center_tile = [coord.x, coord.y];
            world_map.target_tile = None;
        }
        Opcode::WorldmapJumptosourcecoord => {
            let packed = s.int()?;
            let coord = MapCoord::from_packed(packed);
            if let Some(area) = world_map.active_area() {
                if let Some([dx, dy]) = area.position(coord.plane, coord.x, coord.y) {
                    world_map.target_tile = Some([dx, dy]);
                }
            }
        }
        Opcode::WorldmapJumptosourcecoordInstant => {
            let packed = s.int()?;
            let coord = MapCoord::from_packed(packed);
            if let Some(area) = world_map.active_area() {
                if let Some([dx, dy]) = area.position(coord.plane, coord.x, coord.y) {
                    world_map.center_tile = [dx, dy];
                    world_map.target_tile = None;
                }
            }
        }
        Opcode::WorldmapGetdisplayposition => {
            s.integers.push(world_map.center_tile[0]);
            s.integers.push(world_map.center_tile[1]);
        }
        Opcode::WorldmapGetconfigorigin => {
            let map_id = s.int()?;
            let origin = u32::try_from(map_id)
                .ok()
                .and_then(|id| world_map.areas.get(&id))
                .map_or(0, |a| a.origin.packed());
            s.integers.push(origin);
        }
        Opcode::WorldmapGetconfigsize => {
            let map_id = s.int()?;
            let (w, h) = u32::try_from(map_id)
                .ok()
                .and_then(|id| world_map.areas.get(&id))
                .map_or((0, 0), |a| {
                    (
                        (a.region_high[0] - a.region_low[0] + 1) * 64,
                        (a.region_high[1] - a.region_low[1] + 1) * 64,
                    )
                });
            s.integers.push(w);
            s.integers.push(h);
        }
        Opcode::WorldmapGetconfigbounds => {
            let map_id = s.int()?;
            let (min_x, min_y, max_x, max_y) = u32::try_from(map_id)
                .ok()
                .and_then(|id| world_map.areas.get(&id))
                .map_or((0, 0, 0, 0), |a| {
                    (
                        a.region_low[0] * 64,
                        a.region_low[1] * 64,
                        a.region_high[0] * 64 + 63,
                        a.region_high[1] * 64 + 63,
                    )
                });
            s.integers.push(min_x);
            s.integers.push(min_y);
            s.integers.push(max_x);
            s.integers.push(max_y);
        }
        Opcode::WorldmapGetconfigzoom => {
            let map_id = s.int()?;
            let zoom = u32::try_from(map_id)
                .ok()
                .and_then(|id| world_map.areas.get(&id))
                .map_or(-1, |a| i32::from(a.default_zoom));
            s.integers.push(zoom);
        }
        Opcode::WorldmapGetdisplaycentercoord => {
            let (x, y) = world_map
                .active_area()
                .and_then(|a| a.coord(world_map.center_tile[0], world_map.center_tile[1]))
                .map_or((-1, -1), |c| (c.x, c.y));
            s.integers.push(x);
            s.integers.push(y);
        }
        Opcode::WorldmapGetcurrentmap => {
            s.integers.push(world_map.current_map_area_id);
        }
        Opcode::WorldmapGetdisplaycoord => {
            let packed = s.int()?;
            let coord = MapCoord::from_packed(packed);
            let (dx, dy) = world_map
                .active_area()
                .and_then(|a| a.position(coord.plane, coord.x, coord.y))
                .map_or((-1, -1), |[x, y]| (x, y));
            s.integers.push(dx);
            s.integers.push(dy);
        }
        Opcode::WorldmapCoordinmap => {
            let [map_id, coord_packed] = s.ints()?;
            let coord = MapCoord::from_packed(coord_packed);
            let contains = u32::try_from(map_id)
                .ok()
                .and_then(|id| world_map.areas.get(&id))
                .is_some_and(|a| a.contains_coord(coord.plane, coord.x, coord.y));
            s.integers.push(i32::from(contains));
        }
        Opcode::WorldmapGetsize => {
            s.integers.push(world_map.display_size[0]);
            s.integers.push(world_map.display_size[1]);
        }
        Opcode::WorldmapPerpetualflash => {
            let enable = s.int()?;
            world_map.perpetual_flash = enable == 1;
        }
        Opcode::WorldmapFlashelement => {
            let elem = s.int()?;
            if let Ok(elem) = u32::try_from(elem) {
                world_map.flashing_elements.insert(elem);
            }
        }
        Opcode::WorldmapFlashelementcategory => {
            let cat = s.int()?;
            if let Ok(cat) = u32::try_from(cat) {
                world_map.flashing_categories.insert(cat);
            }
        }
        Opcode::WorldmapStopcurrentflashes => {
            world_map.flashing_elements.clear();
            world_map.flashing_categories.clear();
        }
        Opcode::WorldmapDisableelements => {
            let disable = s.int()?;
            // The selected source's misleadingly named disable opcodes take an enabled flag.
            world_map.elements_disabled = disable != 1;
        }
        Opcode::WorldmapDisableelement => {
            let [elem, disable] = s.ints()?;
            if let Ok(elem) = u32::try_from(elem) {
                if disable == 1 {
                    world_map.disabled_elements.remove(&elem);
                } else {
                    world_map.disabled_elements.insert(elem);
                }
            }
        }
        Opcode::WorldmapDisableelementcategory => {
            let [cat, disable] = s.ints()?;
            if let Ok(cat) = u32::try_from(cat) {
                if disable == 1 {
                    world_map.disabled_categories.remove(&cat);
                } else {
                    world_map.disabled_categories.insert(cat);
                }
            }
        }
        Opcode::WorldmapGetdisableelements => {
            s.integers.push(i32::from(!world_map.elements_disabled));
        }
        Opcode::WorldmapGetdisableelement => {
            let elem = s.int()?;
            let disabled =
                u32::try_from(elem).is_ok_and(|id| world_map.disabled_elements.contains(&id));
            s.integers.push(i32::from(!disabled));
        }
        Opcode::WorldmapGetdisableelementcategory => {
            let cat = s.int()?;
            let disabled =
                u32::try_from(cat).is_ok_and(|id| world_map.disabled_categories.contains(&id));
            s.integers.push(i32::from(!disabled));
        }
        Opcode::WorldmapListelementStart => {
            world_map.list_iterator_index = 0;
            let (id, coord) = world_map
                .active_composite
                .as_ref()
                .and_then(|c| c.icons.first())
                .map_or((-1, -1), |icon| {
                    (
                        icon.element.map_or(-1, |e| i32::try_from(e).unwrap_or(-1)),
                        icon.coord.packed(),
                    )
                });
            s.integers.push(id);
            s.integers.push(coord);
        }
        Opcode::WorldmapListelementNext => {
            world_map.list_iterator_index += 1;
            let idx = world_map.list_iterator_index;
            let (id, coord) = world_map
                .active_composite
                .as_ref()
                .and_then(|c| c.icons.get(idx))
                .map_or((-1, -1), |icon| {
                    (
                        icon.element.map_or(-1, |e| i32::try_from(e).unwrap_or(-1)),
                        icon.coord.packed(),
                    )
                });
            s.integers.push(id);
            s.integers.push(coord);
        }
        Opcode::MecText => {
            let elem = s.int()?;
            let text = u32::try_from(elem)
                .ok()
                .and_then(|id| world_map.elements.get(&id))
                .and_then(|e| e.name.clone())
                .unwrap_or_default();
            s.push_object(ScriptValue::Text(text));
        }
        Opcode::MecTextsize => {
            let elem = s.int()?;
            let size = u32::try_from(elem)
                .ok()
                .and_then(|id| world_map.elements.get(&id))
                .map_or(0, |e| i32::from(e.text_size));
            s.integers.push(size);
        }
        Opcode::MecCategory => {
            let elem = s.int()?;
            let cat = u32::try_from(elem)
                .ok()
                .and_then(|id| world_map.elements.get(&id))
                .and_then(|e| e.category)
                .map_or(-1, |c| i32::try_from(c).unwrap_or(-1));
            s.integers.push(cat);
        }
        Opcode::MecSprite => {
            let elem = s.int()?;
            let sprite = u32::try_from(elem)
                .ok()
                .and_then(|id| world_map.elements.get(&id))
                .and_then(|e| e.sprite)
                .map_or(-1, |s| i32::try_from(s).unwrap_or(-1));
            s.integers.push(sprite);
        }
        Opcode::WorldmapElement => {
            s.integers.push(-1);
        }
        Opcode::WorldmapElementcoord => {
            let coord = world_map.last_interacted_coord.map_or(0, |c| c.packed());
            s.integers.push(coord);
        }
        _ => return Err(format!("unhandled worldmap opcode {op:?}")),
    }
    Ok(true)
}

const fn is_worldmap_opcode(op: Opcode) -> bool {
    matches!(
        op,
        Opcode::WorldmapGetmapname
            | Opcode::WorldmapSetmap
            | Opcode::WorldmapGetzoom
            | Opcode::WorldmapSetzoom
            | Opcode::WorldmapIsloaded
            | Opcode::WorldmapJumptodisplaycoord
            | Opcode::WorldmapJumptodisplaycoordInstant
            | Opcode::WorldmapJumptosourcecoord
            | Opcode::WorldmapJumptosourcecoordInstant
            | Opcode::WorldmapGetdisplayposition
            | Opcode::WorldmapGetconfigorigin
            | Opcode::WorldmapGetconfigsize
            | Opcode::WorldmapGetconfigbounds
            | Opcode::WorldmapGetconfigzoom
            | Opcode::WorldmapGetdisplaycentercoord
            | Opcode::WorldmapGetcurrentmap
            | Opcode::WorldmapGetdisplaycoord
            | Opcode::WorldmapCoordinmap
            | Opcode::WorldmapGetsize
            | Opcode::WorldmapPerpetualflash
            | Opcode::WorldmapFlashelement
            | Opcode::WorldmapFlashelementcategory
            | Opcode::WorldmapStopcurrentflashes
            | Opcode::WorldmapDisableelements
            | Opcode::WorldmapDisableelement
            | Opcode::WorldmapDisableelementcategory
            | Opcode::WorldmapGetdisableelements
            | Opcode::WorldmapGetdisableelement
            | Opcode::WorldmapGetdisableelementcategory
            | Opcode::WorldmapListelementStart
            | Opcode::WorldmapListelementNext
            | Opcode::MecText
            | Opcode::MecTextsize
            | Opcode::MecCategory
            | Opcode::MecSprite
            | Opcode::WorldmapElement
            | Opcode::WorldmapElementcoord
    )
}

#[cfg(test)]
#[allow(clippy::float_cmp)]
mod tests {
    use super::*;
    use crate::scripts::test_support::scripts;
    use rustclient_cache::osrs240::world_map::{
        MapCoord, TileRect, WorldMapArea, WorldMapElement, WorldMapSection,
    };
    use rustclient_client_script::Stacks;
    use rustclient_game_state::GameState;

    #[test]
    fn worldmap_instructions_operate_correctly() {
        let mut resources = scripts();
        let mut state = GameState::default();

        let area = WorldMapArea {
            id: 1,
            internal_name: "main".into(),
            external_name: "Gielinor Surface".into(),
            origin: MapCoord::new(0, 3200, 3200),
            region_background: 0,
            viewport_background: 0,
            default_zoom: 100,
            is_main: true,
            sections: vec![WorldMapSection::new(
                0,
                1,
                TileRect {
                    x0: 3000,
                    y0: 3000,
                    x1: 3400,
                    y1: 3400,
                },
                TileRect {
                    x0: 3000,
                    y0: 3000,
                    x1: 3400,
                    y1: 3400,
                },
            )],
            region_low: [46, 46],
            region_high: [53, 53],
        };
        resources.world_map.areas.insert(1, area);

        let element = WorldMapElement {
            id: 10,
            sprite: Some(500),
            hover_sprite: None,
            name: Some("General Store".into()),
            label_color: 0x00FF_FFFF,
            text_size: 1,
            world_map_visible: true,
            minimap_visible: true,
            menu_actions: [None, None, None, None, None],
            menu_target: None,
            category: Some(3),
            horizontal_alignment: 1,
            vertical_alignment: 1,
        };
        resources.world_map.elements.insert(10, element);

        let mut host = super::ScriptHost {
            resources: &mut resources,
            state: &mut state,
        };
        let mut stacks = Stacks::default();

        // 1. Get map name
        stacks.integers.push(1);
        instruction(&mut host, Opcode::WorldmapGetmapname, &mut stacks).unwrap();
        assert_eq!(stacks.text().unwrap(), "Gielinor Surface");

        // 2. Set map
        stacks.integers.push(1);
        instruction(&mut host, Opcode::WorldmapSetmap, &mut stacks).unwrap();
        assert_eq!(host.resources.world_map.current_map_area_id, 1);
        assert!(!host.resources.world_map.is_loaded);
        assert!(host.resources.world_map.load_error.is_some()); // Empty fixture cache must not report a successful load.

        // 3. Zoom
        instruction(&mut host, Opcode::WorldmapGetzoom, &mut stacks).unwrap();
        assert_eq!(stacks.int().unwrap(), 100);

        stacks.integers.push(50);
        instruction(&mut host, Opcode::WorldmapSetzoom, &mut stacks).unwrap();
        assert_eq!(host.resources.world_map.zoom, 50.0);
        host.resources.world_map.set_display_pixels([800, 600]);
        assert_eq!(host.resources.world_map.display_size, [400, 300]);

        // 4. Config bounds and size
        stacks.integers.push(1);
        instruction(&mut host, Opcode::WorldmapGetconfigsize, &mut stacks).unwrap();
        let [w, h] = stacks.ints().unwrap();
        assert_eq!(w, (53 - 46 + 1) * 64);
        assert_eq!(h, (53 - 46 + 1) * 64);

        // 5. Flashing and disabling
        stacks.integers.push(1); // enable
        instruction(&mut host, Opcode::WorldmapDisableelements, &mut stacks).unwrap();
        instruction(&mut host, Opcode::WorldmapGetdisableelements, &mut stacks).unwrap();
        assert_eq!(stacks.int().unwrap(), 1);
        assert!(!host.resources.world_map.elements_disabled);
        stacks.integers.extend([4, 0]);
        instruction(
            &mut host,
            Opcode::WorldmapDisableelementcategory,
            &mut stacks,
        )
        .unwrap();
        assert!(host.resources.world_map.disabled_categories.contains(&4));
        stacks.integers.push(4);
        instruction(
            &mut host,
            Opcode::WorldmapGetdisableelementcategory,
            &mut stacks,
        )
        .unwrap();
        assert_eq!(stacks.int().unwrap(), 0);

        // 6. MEC metadata
        stacks.integers.push(10);
        instruction(&mut host, Opcode::MecText, &mut stacks).unwrap();
        assert_eq!(stacks.text().unwrap(), "General Store");

        stacks.integers.push(10);
        instruction(&mut host, Opcode::MecSprite, &mut stacks).unwrap();
        assert_eq!(stacks.int().unwrap(), 500);

        stacks.integers.push(10);
        instruction(&mut host, Opcode::MecCategory, &mut stacks).unwrap();
        assert_eq!(stacks.int().unwrap(), 3);
    }
}
