use super::*;
use rustclient_cache::osrs240::world_map::{MapCoord, TileRect, WorldMapArea, WorldMapSection};

fn map() -> WorldMapState {
    let mut map = WorldMapState::new();
    map.areas.insert(
        1,
        WorldMapArea {
            id: 1,
            internal_name: "test".into(),
            external_name: "test".into(),
            origin: MapCoord::new(2, 3200, 3200),
            region_background: 0,
            viewport_background: 0,
            default_zoom: 100,
            is_main: true,
            sections: vec![WorldMapSection::new(
                2,
                1,
                TileRect {
                    x0: 3100,
                    y0: 3100,
                    x1: 3300,
                    y1: 3300,
                },
                TileRect {
                    x0: 6100,
                    y0: 6100,
                    x1: 6300,
                    y1: 6300,
                },
            )],
            region_low: [95, 95],
            region_high: [98, 98],
        },
    );
    map.current_map_area_id = 1;
    map.is_loaded = true;
    map.center_tile = [6200, 6200];
    map.set_display_pixels([800, 600]);
    map
}
fn event(ms: u64, point: [f32; 2], pressed: bool, held: bool) -> WorldMapPointer {
    WorldMapPointer {
        viewport: Rect::new(10., 20., 800., 600.),
        position: point,
        pressed,
        held,
        at: Duration::from_millis(ms),
        staff_shortcut: false,
    }
}
#[test]
fn stock_confirmation_precedes_drag_and_maps_display_sections_back_to_source() {
    let mut map = map();
    assert_eq!(map.pointer(event(1000, [410., 320.], true, true)), None);
    assert_eq!(map.pointer(event(1010, [410., 320.], false, false)), None);
    assert_eq!(
        map.pointer(event(1500, [414., 316.], true, true)),
        Some(WorldTile::new(3201, 3201, Plane::new(2).unwrap()))
    );
    assert_eq!(map.pointer(event(1510, [421., 316.], false, true)), None);
    assert_eq!(map.center_tile, [6199, 6200]); // truncates 7/4, not rounded.
    map.cancel_input();
    assert_eq!(map.pointer(event(1520, [414., 316.], true, true)), None);
    assert_eq!(map.pointer(event(2021, [414., 316.], true, true)), None);
    assert_eq!(map.pointer(event(2030, [450., 316.], true, true)), None);
}
#[test]
fn outside_loading_area_changes_and_staff_shortcuts_cannot_emit_normal_clicks() {
    let mut map = map();
    let press = event(1000, [410., 320.], true, true);
    assert_eq!(map.pointer(press), None);
    let mut shortcut = event(1100, [410., 320.], true, true);
    shortcut.staff_shortcut = true;
    assert_eq!(map.pointer(shortcut), None);
    assert_eq!(map.pointer(event(1200, [0., 0.], true, true)), None);
    map.is_loaded = false;
    assert_eq!(map.pointer(event(1300, [410., 320.], true, true)), None);
    map.is_loaded = true;
    map.current_map_area_id = 2;
    assert_eq!(map.pointer(event(1400, [410., 320.], true, true)), None);
}
