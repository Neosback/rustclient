use super::*;
use rustclient_plugin_api::{
    ClientSnapshot, HorizontalAlign, OverlayCommand, Rect, WidgetSnapshot,
};

fn widget(id: u32, bounds: Rect, hidden: bool) -> WidgetSnapshot {
    WidgetSnapshot {
        id,
        child_index: -1,
        bounds,
        clip: bounds,
        text: None,
        sprite: None,
        item: None,
        hidden,
        actions: Vec::new(),
    }
}

fn snapshot(focused: bool, widgets: Vec<WidgetSnapshot>) -> GameSnapshot {
    GameSnapshot {
        client: ClientSnapshot {
            fps: 48,
            focused,
            canvas: (765, 503),
            ..ClientSnapshot::default()
        },
        widgets: widgets.into(),
        ..GameSnapshot::default()
    }
}

fn texts(output: &HostOutput) -> Vec<(String, i32, i32, u32, HorizontalAlign)> {
    output
        .overlay
        .iter()
        .filter_map(|item| match &item.command {
            OverlayCommand::Text {
                text,
                x,
                y,
                argb,
                align,
            } => Some((text.clone(), *x, *y, *argb, *align)),
            _ => None,
        })
        .collect()
}

#[test]
fn the_indicator_is_yellow_without_a_limit_with_a_shadow_in_the_top_right() {
    let mut harness = Harness::new();
    harness.enable("fps", &[]);
    let output = harness.output(&snapshot(true, Vec::new()));
    assert_eq!(output.frame_limit, None);
    assert_eq!(
        texts(&output),
        [
            (
                "48 FPS".to_owned(),
                765,
                12,
                0xFF00_0000,
                HorizontalAlign::Right
            ),
            (
                "48 FPS".to_owned(),
                764,
                11,
                0xFFFF_FF00,
                HorizontalAlign::Right
            ),
        ]
    );
}

#[test]
fn an_enforced_limit_turns_the_indicator_red_and_reaches_the_host() {
    let mut harness = Harness::new();
    harness.enable(
        "fps",
        &[
            ("limitFps", ConfigValue::Boolean(true)),
            ("maxFps", ConfigValue::Integer(30)),
        ],
    );
    let output = harness.output(&snapshot(true, Vec::new()));
    assert_eq!(output.frame_limit, Some(30));
    assert_eq!(texts(&output)[1].3, 0xFFFF_0000);
}

#[test]
fn the_unfocused_limit_only_applies_while_the_window_is_unfocused() {
    let mut harness = Harness::new();
    harness.enable(
        "fps",
        &[
            ("limitFpsUnfocused", ConfigValue::Boolean(true)),
            ("maxFpsUnfocused", ConfigValue::Integer(5)),
        ],
    );
    assert_eq!(
        harness.output(&snapshot(true, Vec::new())).frame_limit,
        None
    );
    assert_eq!(
        harness.output(&snapshot(false, Vec::new())).frame_limit,
        Some(5)
    );
}

#[test]
fn the_indicator_moves_left_of_a_shown_logout_button_only() {
    let button = Rect {
        x: 700,
        y: 0,
        width: 30,
        height: 30,
    };
    let mut harness = Harness::new();
    harness.enable("fps", &[]);
    let shown = harness.output(&snapshot(true, vec![widget(0x00A4_0023, button, false)]));
    assert_eq!(texts(&shown)[1].1, 765 - 1 - 30);
    let hidden = harness.output(&snapshot(true, vec![widget(0x00A4_0023, button, true)]));
    assert_eq!(texts(&hidden)[1].1, 764);
}

#[test]
fn hiding_the_indicator_keeps_the_limit() {
    let mut harness = Harness::new();
    harness.enable(
        "fps",
        &[
            ("drawFps", ConfigValue::Boolean(false)),
            ("limitFps", ConfigValue::Boolean(true)),
        ],
    );
    let output = harness.output(&snapshot(true, Vec::new()));
    assert!(output.overlay.is_empty());
    assert_eq!(output.frame_limit, Some(50));
}
