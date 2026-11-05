use super::*;
use rustclient_plugin_api::{GroundItemSnapshot, OverlayCommand, PlayerSnapshot, WorldPoint};

fn at(x: i32, y: i32) -> WorldPoint {
    WorldPoint { x, y, plane: 0 }
}
fn item(tile: WorldPoint, id: u32, name: &str, quantity: u32, cost: i32) -> GroundItemSnapshot {
    GroundItemSnapshot {
        tile,
        id,
        quantity,
        name: Some(name.into()),
        cost,
        stackable: true,
        noted: false,
        ownership: 0,
        public_tick: 0,
        despawn_tick: 100,
    }
}
fn snapshot(items: Vec<GroundItemSnapshot>) -> GameSnapshot {
    let mut local = actor(1, None);
    local.world = at(3220, 3218);
    GameSnapshot {
        local_player: Some(1),
        players: vec![PlayerSnapshot {
            actor: local,
            name: "Me".into(),
            combat_level: 3,
            skill_level: 0,
            is_local: true,
            hidden: false,
        }],
        ground_items: items,
        ..GameSnapshot::default()
    }
}
fn labels(output: &HostOutput) -> Vec<(WorldPoint, String, i32, u32)> {
    output
        .overlay
        .iter()
        .map(|item| match &item.command {
            OverlayCommand::TileText {
                tile,
                text,
                y_offset,
                argb,
                ..
            } => (*tile, text.clone(), *y_offset, *argb),
            other => panic!("not a tile label: {other:?}"),
        })
        .collect()
}

#[test]
fn labels_merge_stacks_show_high_alchemy_values_and_stack_upwards() {
    let mut harness = Harness::new();
    harness.enable("grounditems", &[]);
    let tile = at(3221, 3218);
    let output = harness.output(&snapshot(vec![
        item(tile, 314, "Feather", 30, 2),
        item(tile, 314, "Feather", 20, 2),
        item(tile, 1_215, "Dragon dagger", 1, 30_000),
        // "Coins" and "Bones" are on the default hidden list.
        item(tile, 995, "Coins", 500, 1),
        item(tile, 526, "Bones", 1, 1),
        // Further than 2500 local units away.
        item(at(3260, 3218), 1_215, "Dragon dagger", 1, 30_000),
    ]));
    assert_eq!(
        labels(&output),
        [
            // (int) (2 * 0.6f) = 1 gp each.
            (tile, "Feather (50) (HA: 50 gp)".to_owned(), 0, 0xFFFF_FFFF),
            // 30000 * 0.6 = 18000 gp, under the 20000 low tier: default colour.
            (
                tile,
                "Dragon dagger (HA: 18K gp)".to_owned(),
                -15,
                0xFFFF_FFFF
            ),
        ]
    );
}

#[test]
fn highlight_list_and_value_tiers_colour_labels() {
    let mut harness = Harness::new();
    harness.enable(
        "grounditems",
        &[("highlightedItems", ConfigValue::Text("Bones, Rune*".into()))],
    );
    let output = harness.output(&snapshot(vec![
        item(at(3221, 3218), 526, "Bones", 1, 1),
        item(at(3222, 3218), 1_319, "Rune 2h sword", 1, 64_000),
        item(at(3223, 3218), 4_151, "Abyssal whip", 1, 120_001),
    ]));
    let colours: Vec<u32> = labels(&output)
        .into_iter()
        .map(|(_, _, _, argb)| argb)
        .collect();
    let config = |key: &str| {
        rustclient_plugin_host::required::first_party_schemas()
            .unwrap()
            .into_iter()
            .find(|schema| schema.plugin.as_str() == "dev.rustclient.grounditems")
            .and_then(|schema| {
                PluginConfig::of(&Configuration::new(schema, None).unwrap())
                    .color(key)
                    .into()
            })
            .unwrap()
    };
    // An exact entry beats the hidden list; a wildcard highlights; 72000 HA passes the 20000 tier.
    assert_eq!(
        colours,
        [
            config("highlightedColor"),
            config("highlightedColor"),
            config("lowValueColor")
        ]
    );
}
