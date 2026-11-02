use super::*;
use rustclient_plugin_api::{
    ClientSnapshot, MenuEntry, MenuKind, OverlayCommand, PLUGIN_MENU_TYPE, PlayerSnapshot,
    WorldPoint,
};
use rustclient_plugin_host::PluginStorage;
use std::collections::BTreeMap;

fn id() -> PluginId {
    PluginId::parse("dev.rustclient.groundmarkers").unwrap()
}

fn at(x: i32, y: i32, plane: u8) -> WorldPoint {
    WorldPoint { x, y, plane }
}

/// The player stands at (3220, 3218) on plane 0; `hovered` is the tile under the pointer.
fn snapshot(hovered: Option<WorldPoint>) -> GameSnapshot {
    let mut local = actor(1, None);
    local.world = at(3220, 3218, 0);
    GameSnapshot {
        client: ClientSnapshot {
            hovered_tile: hovered,
            ..ClientSnapshot::default()
        },
        local_player: Some(1),
        players: vec![PlayerSnapshot {
            actor: local,
            name: "Me".into(),
            combat_level: 3,
            skill_level: 0,
            is_local: true,
            hidden: false,
        }],
        ..GameSnapshot::default()
    }
}

fn chosen(option: &str, tile: WorldPoint) -> GameEvent {
    GameEvent::MenuOptionClicked(MenuEntry {
        option: option.into(),
        target: "Tile".into(),
        kind: MenuKind::Other(PLUGIN_MENU_TYPE),
        identifier: 0,
        param0: tile.x,
        param1: tile.y,
        deprioritized: false,
    })
}

fn stored(harness: &Harness) -> BTreeMap<String, String> {
    harness.storage.load(&id())
}

fn tiles(output: &HostOutput) -> Vec<(WorldPoint, u32, u32, u8)> {
    output
        .overlay
        .iter()
        .map(|item| match item.command {
            OverlayCommand::Tile {
                tile,
                size: 1,
                argb,
                fill_argb,
                width,
            } => (tile, argb, fill_argb, width),
            ref other => panic!("not a single tile: {other:?}"),
        })
        .collect()
}

#[test]
fn the_hovered_tile_is_offered_for_marking_with_shift_and_nothing_is_drawn() {
    let mut harness = Harness::new();
    harness.enable("groundmarkers", &[]);
    let output = harness.output(&snapshot(Some(at(3222, 3218, 0))));
    assert!(output.overlay.is_empty());
    assert_eq!(output.menu_offers.len(), 1);
    let offer = &output.menu_offers[0].1;
    assert_eq!(
        (offer.option.as_str(), offer.target.as_str(), offer.shift),
        ("Mark", "Tile", true)
    );
    assert_eq!(offer.tile, at(3222, 3218, 0));
    assert!(harness.output(&snapshot(None)).menu_offers.is_empty());
}

#[test]
fn marking_draws_the_tile_in_the_source_style_saves_the_source_json_and_unmarking_removes_it() {
    let mut harness = Harness::new();
    harness.enable("groundmarkers", &[]);
    let tile = at(3222, 3218, 0);
    let game = snapshot(Some(tile));
    harness.output(&game);
    harness.run(&game, &[chosen("Mark", tile)]);
    let output = harness.output(&game);
    // Yellow, fill opacity 50 in black, border width 2.
    assert_eq!(tiles(&output), [(tile, 0xFFFF_FF00, 0x3200_0000, 2)]);
    assert_eq!(output.menu_offers[0].1.option, "Unmark");
    // Region 50, 50 holds the tile at (22, 18); the color is the serialized java.awt.Color.
    assert_eq!(
        stored(&harness).get("region_12850").map(String::as_str),
        Some(
            r#"[{"color":{"falpha":0.0,"value":-256},"regionId":12850,"regionX":22,"regionY":18,"z":0}]"#
        )
    );
    harness.run(&game, &[chosen("Unmark", tile)]);
    assert!(tiles(&harness.output(&game)).is_empty());
    assert!(stored(&harness).is_empty());
}

#[test]
fn only_the_plugin_menu_type_with_the_mark_options_toggles_and_the_menu_may_outlive_the_hover() {
    let mut harness = Harness::new();
    harness.enable("groundmarkers", &[]);
    let game = snapshot(Some(at(3222, 3218, 0)));
    harness.output(&game);
    let mut other_kind = chosen("Mark", at(3222, 3218, 0));
    if let GameEvent::MenuOptionClicked(entry) = &mut other_kind {
        entry.kind = MenuKind::Walk;
    }
    harness.run(&game, &[chosen("Label", at(3222, 3218, 0)), other_kind]);
    assert!(tiles(&harness.output(&game)).is_empty());
    assert!(stored(&harness).is_empty());
    // The pointer moved on while the menu was open: the entry still names the tile it was opened on.
    let moved = snapshot(Some(at(3230, 3218, 0)));
    harness.run(&moved, &[chosen("Mark", at(3222, 3218, 0))]);
    assert_eq!(
        tiles(&harness.output(&moved)),
        [(at(3222, 3218, 0), 0xFFFF_FF00, 0x3200_0000, 2)]
    );
}

#[test]
fn stored_markers_keep_their_color_label_and_region_and_draw_within_range_on_the_players_plane() {
    let mut harness = Harness::new();
    // Gson writes a java.awt.Color as its signed ARGB; region 51, 50 starts at x = 3264.
    harness.storage.save(
        &id(),
        &BTreeMap::from([
            (
                "region_12850".to_owned(),
                r#"[{"regionId":12850,"regionX":22,"regionY":18,"z":0,"color":{"value":-16711936,"falpha":0.0},"label":"bank"},
                    {"regionId":12850,"regionX":22,"regionY":18,"z":1,"color":{"value":-256,"falpha":0.0}},
                    {"regionId":12850,"regionX":1,"regionY":18,"z":0}]"#
                    .to_owned(),
            ),
            (
                "region_13106".to_owned(),
                r#"[{"regionId":13106,"regionX":2,"regionY":18,"z":0,"color":{"value":-65536,"falpha":0.0}}]"#
                    .to_owned(),
            ),
            ("region_bad".to_owned(), "[]".to_owned()),
            ("region_12851".to_owned(), "not json".to_owned()),
        ]),
    );
    harness.enable("groundmarkers", &[]);
    let output = harness.output(&snapshot(None));
    assert_eq!(
        tiles(&output),
        [
            // The green one; the plane 1 marker is hidden and region 51's is 46 tiles away.
            (at(3222, 3218, 0), 0xFF00_FF00, 0x3200_0000, 2),
            // The marker without a color follows markerColor (yellow).
            (at(3201, 3218, 0), 0xFFFF_FF00, 0x3200_0000, 2),
        ]
    );
    // Toggling a tile of the region rewrites it with the label of the other markers intact.
    let game = snapshot(Some(at(3225, 3218, 0)));
    harness.output(&game);
    harness.run(&game, &[chosen("Mark", at(3225, 3218, 0))]);
    let text = stored(&harness)["region_12850"].clone();
    assert!(text.contains(r#""label":"bank""#), "{text}");
    assert!(text.contains(r#""regionX":25"#), "{text}");
    assert!(stored(&harness).contains_key("region_13106"));
}

#[test]
fn markers_past_thirty_one_tiles_or_in_the_next_region_follow_the_distance_rule() {
    let mut harness = Harness::new();
    harness.storage.save(
        &id(),
        &BTreeMap::from([
            (
                "region_12850".to_owned(),
                // x = 3251 is 31 tiles from the player, x = 3252 is 32.
                r#"[{"regionId":12850,"regionX":51,"regionY":18,"z":0,"color":{"value":-1,"falpha":0.0}},
                    {"regionId":12850,"regionX":52,"regionY":18,"z":0,"color":{"value":-1,"falpha":0.0}}]"#
                    .to_owned(),
            ),
            (
                "region_13106".to_owned(),
                // x = 3264: the next region, 44 tiles away.
                r#"[{"regionId":13106,"regionX":0,"regionY":18,"z":0,"color":{"value":-1,"falpha":0.0}}]"#
                    .to_owned(),
            ),
        ]),
    );
    harness.enable(
        "groundmarkers",
        &[
            ("fillOpacity", ConfigValue::Integer(0)),
            ("borderWidth", ConfigValue::Decimal(4000)),
        ],
    );
    let drawn = tiles(&harness.output(&snapshot(None)));
    assert_eq!(drawn, [(at(3251, 3218, 0), 0xFFFF_FFFF, 0, 4)]);

    // Standing at the east edge of region 50 reaches into region 51.
    let mut east = snapshot(None);
    east.players[0].actor.world = at(3260, 3218, 0);
    assert_eq!(
        tiles(&harness.output(&east))
            .into_iter()
            .map(|(tile, ..)| tile.x)
            .collect::<Vec<_>>(),
        [3251, 3252, 3264]
    );
}
