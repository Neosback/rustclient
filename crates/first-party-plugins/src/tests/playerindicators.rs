use super::*;
use rustclient_plugin_api::{ActorAnchor, HorizontalAlign, OverlayCommand, PlayerSnapshot};

fn player(index: u16, name: &str, is_local: bool) -> PlayerSnapshot {
    PlayerSnapshot {
        actor: actor(index, None),
        name: name.into(),
        combat_level: 3,
        skill_level: 0,
        is_local,
        hidden: false,
    }
}

fn snapshot() -> GameSnapshot {
    GameSnapshot {
        players: vec![
            player(1, "Me", true),
            player(2, "Them", false),
            player(3, "", false),
        ],
        ..GameSnapshot::default()
    }
}

fn texts(output: &HostOutput) -> Vec<(&str, ActorAnchor, HorizontalAlign, i32, u32)> {
    output
        .overlay
        .iter()
        .filter_map(|item| match &item.command {
            OverlayCommand::ActorText {
                text,
                anchor,
                align,
                x_offset,
                argb,
                ..
            } => Some((text.as_str(), *anchor, *align, *x_offset, *argb)),
            _ => None,
        })
        .collect()
}

#[test]
fn the_defaults_highlight_nobody() {
    // Self and others are both off by default.
    let mut harness = Harness::new();
    harness.enable("playerindicators", &[]);
    assert!(harness.output(&snapshot()).overlay.is_empty());
}

#[test]
fn others_are_named_above_the_head_in_the_source_colour() {
    let mut harness = Harness::new();
    harness.enable(
        "playerindicators",
        &[("highlightOthers", ConfigValue::Choice("ENABLED".into()))],
    );
    let output = harness.output(&snapshot());
    // The unnamed third player is skipped and the local player is not "others".
    assert_eq!(
        texts(&output),
        [(
            "Them",
            ActorAnchor::Top,
            HorizontalAlign::Center,
            0,
            0xFFFF_0000
        )]
    );
}

#[test]
fn name_positions_and_tiles_follow_their_settings() {
    let mut harness = Harness::new();
    harness.enable(
        "playerindicators",
        &[
            ("highlightSelf", ConfigValue::Choice("ENABLED".into())),
            (
                "playerNamePosition",
                ConfigValue::Choice("MODEL_RIGHT".into()),
            ),
            ("drawPlayerTiles", ConfigValue::Boolean(true)),
        ],
    );
    let output = harness.output(&snapshot());
    assert_eq!(
        texts(&output),
        [(
            "Me",
            ActorAnchor::Middle,
            HorizontalAlign::Left,
            10,
            0xFF00_B8D4
        )]
    );
    assert!(output.overlay.iter().any(|item| matches!(
        item.command,
        OverlayCommand::Tile {
            size: 1,
            fill_argb: 0x3200_0000,
            ..
        }
    )));
    let disabled = {
        let mut harness = Harness::new();
        harness.enable(
            "playerindicators",
            &[
                ("highlightSelf", ConfigValue::Choice("ENABLED".into())),
                ("playerNamePosition", ConfigValue::Choice("DISABLED".into())),
            ],
        );
        harness.output(&snapshot())
    };
    assert!(texts(&disabled).is_empty());
}
