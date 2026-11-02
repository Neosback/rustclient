use super::*;
use rustclient_plugin_api::{ActorRef, NpcSnapshot, OverlayCommand};

fn npc(index: u16, name: &str, size: u8) -> NpcSnapshot {
    NpcSnapshot {
        actor: actor(index, None),
        id: 1,
        name: Some(name.into()),
        combat_level: 2,
        size: (size, size),
        actions: Vec::new(),
    }
}

fn snapshot() -> GameSnapshot {
    GameSnapshot {
        npcs: vec![
            npc(1, "Goblin", 1),
            npc(2, "Goblin guard", 1),
            npc(3, "Cow", 2),
        ],
        ..GameSnapshot::default()
    }
}

fn commands(output: &HostOutput) -> Vec<&OverlayCommand> {
    output.overlay.iter().map(|item| &item.command).collect()
}

#[test]
fn nothing_is_drawn_without_a_name_list() {
    let mut harness = Harness::new();
    harness.enable("npchighlight", &[]);
    assert!(harness.output(&snapshot()).overlay.is_empty());
}

#[test]
fn listed_names_get_the_default_hull_with_the_source_colours() {
    let mut harness = Harness::new();
    harness.enable(
        "npchighlight",
        &[("npcToHighlight", ConfigValue::Text("goblin, cow".into()))],
    );
    let output = harness.output(&snapshot());
    assert_eq!(
        commands(&output),
        [
            &OverlayCommand::ActorHull {
                actor: ActorRef::Npc(1),
                argb: 0xFF00_FFFF,
                fill_argb: 0x1400_FFFF,
                width: 2,
            },
            &OverlayCommand::ActorHull {
                actor: ActorRef::Npc(3),
                argb: 0xFF00_FFFF,
                fill_argb: 0x1400_FFFF,
                width: 2,
            },
        ]
    );
}

#[test]
fn wildcards_tiles_and_names_follow_the_style_switches() {
    let mut harness = Harness::new();
    harness.enable(
        "npchighlight",
        &[
            ("npcToHighlight", ConfigValue::Text("gob*, cow".into())),
            ("highlightHull", ConfigValue::Boolean(false)),
            ("highlightTile", ConfigValue::Boolean(true)),
            ("highlightSouthWestTile", ConfigValue::Boolean(true)),
            ("drawNames", ConfigValue::Boolean(true)),
        ],
    );
    let output = harness.output(&snapshot());
    let tiles: Vec<u8> = commands(&output)
        .into_iter()
        .filter_map(|command| match command {
            OverlayCommand::Tile { size, .. } => Some(*size),
            _ => None,
        })
        .collect();
    // Goblin, Goblin guard (1x1) and the 2x2 cow: area tile, then the south-west single tile.
    assert_eq!(tiles, [1, 1, 1, 1, 2, 1]);
    let names: Vec<&str> = commands(&output)
        .into_iter()
        .filter_map(|command| match command {
            OverlayCommand::ActorText { text, .. } => Some(text.as_str()),
            _ => None,
        })
        .collect();
    assert_eq!(names, ["Goblin", "Goblin guard", "Cow"]);
}

#[test]
fn cutscenes_hide_the_highlights() {
    let mut harness = Harness::new();
    harness.enable(
        "npchighlight",
        &[("npcToHighlight", ConfigValue::Text("goblin".into()))],
    );
    let mut scene = snapshot();
    let vars = rustclient_plugin_api::Vars {
        varps: std::collections::BTreeMap::from([(1, 1)]),
        varbits: std::sync::Arc::new(std::collections::BTreeMap::from([(
            542,
            rustclient_plugin_api::VarbitDef {
                varp: 1,
                lsb: 0,
                msb: 0,
            },
        )])),
    };
    scene.vars = vars;
    assert!(harness.output(&scene).overlay.is_empty());
}
