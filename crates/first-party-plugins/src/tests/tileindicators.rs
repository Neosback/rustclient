use super::*;
use rustclient_plugin_api::{
    ClientSnapshot, OverlayCommand, PlayerSnapshot, VarbitDef, Vars, WorldPoint,
};

fn at(x: i32, y: i32) -> WorldPoint {
    WorldPoint { x, y, plane: 0 }
}

fn snapshot() -> GameSnapshot {
    let mut local = actor(1, None);
    local.world = at(3220, 3218);
    GameSnapshot {
        client: ClientSnapshot {
            hovered_tile: Some(at(3224, 3218)),
            destination: Some(at(3222, 3220)),
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
fn by_default_only_the_destination_tile_is_outlined_in_the_source_colours() {
    let mut harness = Harness::new();
    harness.enable("tileindicators", &[]);
    assert_eq!(
        tiles(&harness.output(&snapshot())),
        [(at(3222, 3220), 0xFF80_8080, 0x3200_0000, 2)]
    );
}

#[test]
fn hovered_destination_and_current_tiles_draw_in_the_source_order_with_their_own_styles() {
    let mut harness = Harness::new();
    harness.enable(
        "tileindicators",
        &[
            ("highlightHoveredTile", ConfigValue::Boolean(true)),
            ("highlightHoveredColor", ConfigValue::Color(0xFFFF_0000)),
            ("hoveredTileBorderWidth", ConfigValue::Decimal(4000)),
            ("highlightCurrentTile", ConfigValue::Boolean(true)),
        ],
    );
    assert_eq!(
        tiles(&harness.output(&snapshot())),
        [
            (at(3224, 3218), 0xFFFF_0000, 0x3200_0000, 4),
            (at(3222, 3220), 0xFF80_8080, 0x3200_0000, 2),
            (at(3220, 3218), 0xFF00_FFFF, 0x3200_0000, 2),
        ]
    );
}

#[test]
fn a_tile_the_client_does_not_report_is_not_drawn_and_a_disabled_style_stays_off() {
    let mut harness = Harness::new();
    harness.enable(
        "tileindicators",
        &[
            ("highlightHoveredTile", ConfigValue::Boolean(true)),
            ("highlightDestinationTile", ConfigValue::Boolean(false)),
        ],
    );
    let mut scene = snapshot();
    scene.client.hovered_tile = None;
    assert!(harness.output(&scene).overlay.is_empty());
}

#[test]
fn cutscenes_hide_every_indicator() {
    let mut harness = Harness::new();
    harness.enable("tileindicators", &[]);
    let mut scene = snapshot();
    scene.vars = Vars {
        varps: std::collections::BTreeMap::from([(1, 1)]),
        varbits: std::sync::Arc::new(std::collections::BTreeMap::from([(
            542,
            VarbitDef {
                varp: 1,
                lsb: 0,
                msb: 0,
            },
        )])),
    };
    assert!(harness.output(&scene).overlay.is_empty());
}
