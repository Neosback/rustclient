use super::*;
use rustclient_cache::{
    GroupId, IndexId, Source,
    osrs240::widgets::decode,
    script::{Instruction, Opcode, Script},
};
use rustclient_model::*;

fn component(id: WidgetId) -> WidgetDefinition {
    let mut widget = decode(
        include_bytes!("../../../cache/tests/fixtures/widgets/container.bin"),
        id,
        Source::group(IndexId(3), GroupId(id.interface.get())),
    )
    .unwrap();
    widget.parent = None;
    widget.hidden = false;
    widget.layout.size = [765, 503];
    widget.layout.size_mode = [SizeMode::Absolute; 2];
    widget.listeners.clear();
    widget
}

#[test]
#[expect(clippy::too_many_lines, reason = "one hidden reflow/scroll lifecycle")]
fn resize_runs_after_load_on_first_layout_and_only_again_when_its_extent_changes() {
    let mut scripts = test_support::scripts();
    let mut state = GameState::new();
    let id = WidgetId::new(InterfaceId::new(1), 0);
    let mut widget = component(id);
    widget.layout.size = [0, 0];
    widget.layout.size_mode = [SizeMode::ParentMinus; 2];
    widget.hidden = true;
    widget.body = WidgetBody::Container {
        scroll_size: [1000, 800],
        no_click_through: false,
    };
    widget
        .listeners
        .insert(WidgetListener::Resize, vec![ScriptValue::Int(90001)]);
    scripts.interfaces.insert(
        id.interface,
        InterfaceDefinition::from_widgets(id.interface, vec![widget]).unwrap(),
    );
    scripts.scripts.insert(
        ScriptId::new(90001),
        Arc::new(Script {
            id: ScriptId::new(90001),
            name: String::new(),
            locals: [0; 3],
            arguments: [0; 3],
            switches: vec![],
            instructions: [
                (Opcode::GetVarcInt, 1),
                (Opcode::Iconst, 1),
                (Opcode::Add, 0),
                (Opcode::SetVarcInt, 1),
                (Opcode::Return, 0),
            ]
            .into_iter()
            .map(|(opcode, integer)| Instruction {
                opcode,
                integer,
                text: None,
                long: 0,
                array_default: None,
            })
            .collect(),
        }),
    );
    state
        .apply_operation(SemanticOperation::BeginSession {
            revision: Revision::with_subrevision(240, 2),
        })
        .unwrap();
    state
        .apply_operation(SemanticOperation::Interface(
            InterfaceUpdate::ClientVariable {
                id: 1,
                value: ScriptValue::Int(0),
            },
        ))
        .unwrap();
    scripts
        .commit(
            &mut state,
            SemanticOperation::Interface(InterfaceUpdate::SetRoot(Some(id.interface))),
        )
        .unwrap();
    assert_eq!(
        state.interfaces().client_variables().get(&1),
        Some(&ScriptValue::Int(0))
    );
    scripts.resize_widgets(&mut state).unwrap();
    assert_eq!(
        state.interfaces().client_variables().get(&1),
        Some(&ScriptValue::Int(1))
    );
    let address = WidgetAddress::component(id);
    scripts
        .commit(
            &mut state,
            SemanticOperation::Interface(InterfaceUpdate::SetProperty {
                widget: id,
                property: WidgetProperty::Runtime {
                    address,
                    value: Box::new(WidgetRuntime {
                        scroll: Some([999, 999]),
                        hidden: Some(false),
                        ..Default::default()
                    }),
                },
            }),
        )
        .unwrap();
    scripts.resize_widgets(&mut state).unwrap();
    assert_eq!(
        state.interfaces().runtime()[&address].scroll,
        Some([235, 297])
    );
    assert_eq!(
        state.interfaces().client_variables().get(&1),
        Some(&ScriptValue::Int(1))
    );
    scripts
        .commit(
            &mut state,
            SemanticOperation::Interface(InterfaceUpdate::Window(WindowMetrics {
                mode: WindowMode::Resizable,
                available: CanvasSize {
                    width: 800,
                    height: 600,
                },
                canvas: CanvasSize {
                    width: 800,
                    height: 600,
                },
            })),
        )
        .unwrap();
    assert_eq!(
        state.interfaces().client_variables().get(&1),
        Some(&ScriptValue::Int(1)),
        "resize is deferred until after network/load scripts"
    );
    scripts.resize_widgets(&mut state).unwrap();
    assert_eq!(
        state.interfaces().runtime()[&address].scroll,
        Some([200, 200])
    );
    assert_eq!(
        state.interfaces().client_variables().get(&1),
        Some(&ScriptValue::Int(2))
    );
}

#[test]
#[expect(
    clippy::too_many_lines,
    reason = "one dynamic-selection lifecycle with its self-removing callback"
)]
fn a_dropdown_callback_can_delete_its_rows_without_cancelling_the_selected_button() {
    let mut scripts = test_support::scripts();
    let mut state = GameState::new();
    let id = WidgetId::new(InterfaceId::new(1), 0);
    scripts.interfaces.insert(
        id.interface,
        InterfaceDefinition::from_widgets(id.interface, vec![component(id)]).unwrap(),
    );
    scripts.scripts.insert(
        ScriptId::new(90003),
        Arc::new(Script {
            id: ScriptId::new(90003),
            name: String::new(),
            locals: [0; 3],
            arguments: [0; 3],
            switches: vec![],
            instructions: [
                (Opcode::Iconst, 65536),
                (Opcode::CcDeleteall, 0),
                (Opcode::Return, 0),
            ]
            .into_iter()
            .map(|(opcode, integer)| Instruction {
                opcode,
                integer,
                text: None,
                long: 0,
                array_default: None,
            })
            .collect(),
        }),
    );
    state
        .apply_operation(SemanticOperation::AuthenticateSession {
            revision: Revision::with_subrevision(240, 2),
            player: rustclient_game_state::AuthenticatedPlayer {
                profile: ServerProfileId::new(1),
                player: PlayerIndex::new(1),
                member: false,
                staff_level: 0,
            },
        })
        .unwrap();
    for update in [
        InterfaceUpdate::SetRoot(Some(id.interface)),
        InterfaceUpdate::CreateChild {
            parent: id,
            parent_child: None,
            index: 0,
            reject_replace: true,
            body: WidgetBody::Container {
                scroll_size: [0; 2],
                no_click_through: false,
            },
        },
    ] {
        state
            .apply_operation(SemanticOperation::Interface(update))
            .unwrap();
    }
    let address = WidgetAddress {
        widget: id,
        child: Some(0),
    };
    let mut runtime = state.interfaces().runtime()[&address].clone();
    runtime.layout = Some(WidgetLayout {
        offset: [0; 2],
        size: [20, 20],
        size_mode: [SizeMode::Absolute; 2],
        position_mode: [PositionMode::Start; 2],
    });
    runtime.actions = Some(vec!["Select".into()]);
    runtime.listeners.insert(
        WidgetListener::Operation,
        Some(vec![ScriptValue::Int(90003)]),
    );
    for update in [
        InterfaceUpdate::SetProperty {
            widget: id,
            property: WidgetProperty::Runtime {
                address,
                value: Box::new(runtime),
            },
        },
        InterfaceUpdate::SetEvents(WidgetEventRange {
            widget: id,
            first: 0,
            last: 0,
            permissions: WidgetPermissions {
                enabled_operations: 1,
                ..Default::default()
            },
        }),
    ] {
        state
            .apply_operation(SemanticOperation::Interface(update))
            .unwrap();
    }
    let (_, action) = crate::widget_actions(&state, &scripts.interfaces, address)
        .unwrap()
        .remove(0);
    let (intents, errors) = scripts.perform_action(&mut state, action).unwrap();
    assert!(errors.is_empty());
    assert!(!state.interfaces().runtime().contains_key(&address));
    assert!(
        matches!(intents.as_slice(), [rustclient_protocol::ClientIntent::WidgetAction(sent)] if *sent == action)
    );
    assert!(
        scripts.perform_action(&mut state, action).is_err(),
        "a second stale selection is still rejected"
    );
}

#[test]
fn window_modes_and_individual_canvas_queries_share_the_profile_caps() {
    let mut scripts = test_support::scripts();
    let mut state = GameState::new();
    state
        .apply_operation(SemanticOperation::BeginSession {
            revision: Revision::with_subrevision(240, 2),
        })
        .unwrap();
    for (mode, host, expected) in [
        (1, [1280, 800], [765, 503]),
        (2, [1280, 800], [1280, 800]),
        (2, [16000, 4000], [7680, 2160]),
    ] {
        let metrics = scripts.profile.window_metrics(
            WindowMode::Resizable,
            CanvasSize {
                width: host[0],
                height: host[1],
            },
        );
        state
            .apply_operation(SemanticOperation::Interface(InterfaceUpdate::Window(
                metrics,
            )))
            .unwrap();
        let mut stacks = Stacks::default();
        stacks.integers.push(mode);
        queries::instruction(
            &mut ScriptHost {
                resources: &mut scripts,
                state: &mut state,
            },
            Opcode::Setwindowmode,
            0,
            &mut stacks,
        )
        .unwrap();
        for op in [
            Opcode::Getcanvaswidth,
            Opcode::Getcanvasheight,
            Opcode::ViewportGetx,
            Opcode::ViewportGety,
        ] {
            queries::instruction(
                &mut ScriptHost {
                    resources: &mut scripts,
                    state: &mut state,
                },
                op,
                0,
                &mut stacks,
            )
            .unwrap();
        }
        assert_eq!(stacks.integers, [expected[0], expected[1], 0, 0]);
    }
}
