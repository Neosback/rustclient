//! Full-viewport selection is a widget projection contract, independent from GPU pixels.
use rustclient_cache::{
    GroupId, IndexId, Source, osrs240::widgets::decode, widget::InterfaceDefinition,
};
use rustclient_game_state::{GameState, SemanticOperation};
use rustclient_game_ui::{
    UiFrame,
    widget_visuals::{WidgetVisualAssets, project_widgets},
};
use rustclient_model::*;
use std::collections::BTreeMap;

#[test]
fn viewport_rectangles_project_once_and_reset_without_mutating_widget_state() {
    let group = InterfaceId::new(1);
    let widgets = (0..4)
        .map(|component| {
            let mut widget = decode(
                include_bytes!("../../cache/tests/fixtures/widgets/container.bin"),
                WidgetId::new(group, component),
                Source::group(IndexId(3), GroupId(1)),
            )
            .unwrap();
            widget.parent = None;
            widget.hidden = false;
            widget.listeners.clear();
            widget.layout = WidgetLayout {
                offset: [0, 0],
                size: [100, 50],
                size_mode: [SizeMode::Absolute; 2],
                position_mode: [PositionMode::Start; 2],
            };
            if component == 0 {
                widget.content = WidgetContent::WorldViewport;
            } else {
                widget.content = WidgetContent::None;
                widget.body = WidgetBody::Rectangle {
                    color: if component == 1 {
                        0x00ff_0000
                    } else {
                        0x0000_00ff
                    },
                    filled: true,
                    transparency: 128,
                };
                if component == 3 {
                    widget.layout.size = [10, 10];
                }
            }
            widget
        })
        .collect();
    let definitions = BTreeMap::from([(
        group,
        InterfaceDefinition::from_widgets(group, widgets).unwrap(),
    )]);
    let mut state = GameState::new();
    state
        .apply_operation(SemanticOperation::BeginSession {
            revision: Revision::with_subrevision(240, 2),
        })
        .unwrap();
    state
        .apply_operation(SemanticOperation::Interface(InterfaceUpdate::SetRoot(
            Some(group),
        )))
        .unwrap();
    let before = state.semantic_fingerprint();
    let mut frame = UiFrame::default();
    let assets = WidgetVisualAssets::default();
    let tree = project_widgets(&state, &definitions, &assets, &mut frame).unwrap();
    assert_eq!(tree.nodes.len(), 4); // Optimization must not remove semantic input targets.
    assert_eq!(frame.quads.len(), 1); // Only the small ordinary rectangle paints into the UI texture.
    assert_eq!(frame.viewport_overlay.rgba(), [63, 0, 127, 191]);
    assert_eq!(state.semantic_fingerprint(), before);
    frame.quads.clear();
    project_widgets(&state, &definitions, &assets, &mut frame).unwrap();
    assert_eq!(frame.viewport_overlay.rgba(), [63, 0, 127, 191]); // No cross-frame accumulation.
    state
        .apply_operation(SemanticOperation::Interface(InterfaceUpdate::Viewport(
            ViewportSettings {
                clamp: [1, 100, 1000, 32767],
                ..Default::default()
            },
        )))
        .unwrap();
    frame.quads.clear();
    project_widgets(&state, &definitions, &assets, &mut frame).unwrap();
    assert_eq!(frame.viewport_overlay.rgba(), [0; 4]); // Full widget bounds no longer match letterboxed world.
    assert_eq!(frame.quads.len(), 3);
    state
        .apply_operation(SemanticOperation::Interface(InterfaceUpdate::SetRoot(None)))
        .unwrap();
    frame.quads.clear();
    project_widgets(&state, &definitions, &assets, &mut frame).unwrap();
    assert_eq!(frame.viewport_overlay.rgba(), [0; 4]);
    assert!(frame.quads.is_empty());
}
