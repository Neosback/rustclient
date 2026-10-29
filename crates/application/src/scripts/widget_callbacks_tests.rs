use super::*;
use rustclient_cache::{
    GroupId, IndexId, Source,
    osrs240::widgets::decode,
    script::{Instruction, Opcode, Script},
};
use rustclient_model::*;
fn setup() -> (
    ClientScripts<rustclient_platform::MemoryBlobs>,
    GameState,
    WidgetId,
) {
    let mut scripts = test_support::scripts();
    let mut state = GameState::new();
    let id = WidgetId::new(InterfaceId::new(1), 0);
    let mut widget = decode(
        include_bytes!("../../../cache/tests/fixtures/widgets/container.bin"),
        id,
        Source::group(IndexId(3), GroupId(1)),
    )
    .unwrap();
    widget.parent = None;
    widget.hidden = false;
    widget.listeners.clear();
    scripts.interfaces.insert(
        id.interface,
        InterfaceDefinition::from_widgets(id.interface, vec![widget]).unwrap(),
    );
    state
        .apply_operation(SemanticOperation::BeginSession {
            revision: REVISION_240,
        })
        .unwrap();
    state
        .apply_operation(SemanticOperation::Interface(InterfaceUpdate::SetRoot(
            Some(id.interface),
        )))
        .unwrap();
    (scripts, state, id)
}
fn program(
    scripts: &mut ClientScripts<rustclient_platform::MemoryBlobs>,
    id: u32,
    ops: &[(Opcode, i32)],
) {
    scripts.scripts.insert(
        ScriptId::new(id),
        Arc::new(Script {
            id: ScriptId::new(id),
            name: String::new(),
            locals: [0; 3],
            arguments: [0; 3],
            switches: vec![],
            instructions: ops
                .iter()
                .map(|&(opcode, integer)| Instruction {
                    opcode,
                    integer,
                    text: None,
                    long: 0,
                    array_default: None,
                })
                .collect(),
        }),
    );
}
#[test]
fn resize_callbacks_stop_on_absence_and_bound_recursive_event_depth() {
    let (mut scripts, mut state, id) = setup();
    program(
        &mut scripts,
        90010,
        &[
            (Opcode::Iconst, 65536),
            (Opcode::IfCallonresize, 0),
            (Opcode::Iconst, 99),
            (Opcode::SetVarcInt, 999),
            (Opcode::Return, 0),
        ],
    );
    let request = ScriptRequest {
        id: ScriptId::new(90010),
        arguments: vec![],
    };
    scripts.enqueue(request.clone(), None).unwrap();
    assert!(scripts.drain(&mut state).is_empty());
    assert!(!state.interfaces().client_variables().contains_key(&999));
    program(
        &mut scripts,
        90011,
        &[
            (Opcode::GetVarcInt, 998),
            (Opcode::Iconst, 1),
            (Opcode::Add, 0),
            (Opcode::SetVarcInt, 998),
            (Opcode::Iconst, 65536),
            (Opcode::IfCallonresize, 0),
            (Opcode::Return, 0),
        ],
    );
    state
        .apply_operation(SemanticOperation::Interface(InterfaceUpdate::SetProperty {
            widget: id,
            property: WidgetProperty::Runtime {
                address: WidgetAddress::component(id),
                value: Box::new(WidgetRuntime {
                    listeners: BTreeMap::from([(
                        WidgetListener::Resize,
                        Some(vec![ScriptValue::Int(90011)]),
                    )]),
                    ..Default::default()
                }),
            },
        }))
        .unwrap();
    scripts.enqueue(request, None).unwrap();
    let errors = scripts.drain(&mut state);
    assert_eq!(errors.len(), 1);
    assert!(errors[0].contains("resize callback depth limit"));
    assert_eq!(
        state.interfaces().client_variables()[&998],
        ScriptValue::Int(9)
    );
    assert_eq!(
        state.interfaces().client_variables()[&999],
        ScriptValue::Int(99)
    );
}
#[test]
fn widget_queries_preserve_slot_direct_and_depth_first_order_then_reset() {
    let (mut scripts, mut state, id) = setup();
    for (index, parent_child) in [(0, None), (1, Some(0)), (2, None), (3, Some(0))] {
        state
            .apply_operation(SemanticOperation::Interface(InterfaceUpdate::CreateChild {
                parent: id,
                parent_child,
                index,
                reject_replace: false,
                body: WidgetBody::Container {
                    scroll_size: [0; 2],
                    no_click_through: false,
                },
            }))
            .unwrap();
    }
    for (mode, expected) in [
        (0, vec![0, 1, 2, 3]),
        (1, vec![0, 2]),
        (2, vec![0, 1, 3, 2]),
    ] {
        let mut context = Context {
            active: [Some(WidgetAddress::component(id)); 2],
            ..Default::default()
        };
        let mut stacks = Stacks::default();
        stacks.integers.push(mode);
        let mut host = ScriptHost {
            resources: &mut scripts,
            state: &mut state,
        };
        host.instruction(Opcode::CcFindchildrenActive, 0, &mut stacks, &mut context)
            .unwrap();
        assert_eq!(
            stacks.int().unwrap(),
            i32::try_from(expected.len()).unwrap()
        );
        for child in expected {
            host.instruction(Opcode::CcFindchildNext, 0, &mut stacks, &mut context)
                .unwrap();
            assert_eq!(stacks.int().unwrap(), 1);
            assert_eq!(context.active[0].unwrap().child, Some(child));
        }
        let last = context.active;
        host.instruction(Opcode::CcFindchildNext, 0, &mut stacks, &mut context)
            .unwrap();
        assert_eq!(stacks.int().unwrap(), 0);
        assert_eq!(context.active, last);
    }
    scripts.reset();
    let mut host = ScriptHost {
        resources: &mut scripts,
        state: &mut state,
    };
    let mut stacks = Stacks::default();
    host.instruction(
        Opcode::CcFindchildIndexNext,
        0,
        &mut stacks,
        &mut Context::default(),
    )
    .unwrap();
    assert_eq!(stacks.int().unwrap(), -1);
}

#[test]
fn trigger_operations_run_after_the_invocation_and_do_not_drain_other_events_early() {
    let (mut scripts, mut state, id) = setup();
    program(
        &mut scripts,
        90100,
        &[
            (Opcode::GetVarcInt, 900),
            (Opcode::SetVarcInt, 901),
            (Opcode::Return, 0),
        ],
    );
    state
        .apply_operation(SemanticOperation::Interface(InterfaceUpdate::SetProperty {
            widget: id,
            property: WidgetProperty::Runtime {
                address: WidgetAddress::component(id),
                value: Box::new(WidgetRuntime {
                    listeners: BTreeMap::from([(
                        WidgetListener::Operation,
                        Some(vec![ScriptValue::Int(90100)]),
                    )]),
                    ..Default::default()
                }),
            },
        }))
        .unwrap();
    program(
        &mut scripts,
        90101,
        &[
            (Opcode::Iconst, 1),
            (Opcode::CcTriggerop, 0),
            (Opcode::Iconst, 42),
            (Opcode::SetVarcInt, 900),
            (Opcode::Return, 0),
        ],
    );
    program(
        &mut scripts,
        90102,
        &[
            (Opcode::Iconst, 99),
            (Opcode::SetVarcInt, 900),
            (Opcode::Return, 0),
        ],
    );
    for script in [90101, 90102] {
        scripts
            .enqueue(
                ScriptRequest {
                    id: ScriptId::new(script),
                    arguments: vec![],
                },
                Some(WidgetAddress::component(id)),
            )
            .unwrap();
    }
    assert!(scripts.drain(&mut state).is_empty());
    assert_eq!(
        state.interfaces().client_variables()[&901],
        ScriptValue::Int(42),
        "onOp must see the parent script's final state, before the next queued event"
    );
    assert_eq!(
        state.interfaces().client_variables()[&900],
        ScriptValue::Int(99)
    );
    assert!(scripts.deferred_operations.is_empty());
    program(
        &mut scripts,
        90103,
        &[
            (Opcode::Iconst, 11),
            (Opcode::CcTriggerop, 0),
            (Opcode::Return, 0),
        ],
    );
    scripts
        .enqueue(
            ScriptRequest {
                id: ScriptId::new(90103),
                arguments: vec![],
            },
            Some(WidgetAddress::component(id)),
        )
        .unwrap();
    assert!(scripts.drain(&mut state)[0].contains("widget operation must be 1..=10"));
}

#[test]
fn trigger_queue_captures_item_context_and_reset_discards_pending_operations() {
    let (mut scripts, mut state, id) = setup();
    let address = WidgetAddress::component(id);
    state
        .apply_operation(SemanticOperation::Interface(InterfaceUpdate::SetProperty {
            widget: id,
            property: WidgetProperty::Runtime {
                address,
                value: Box::new(WidgetRuntime {
                    item: Some((ItemDefinitionId::new(7), 1)),
                    ..Default::default()
                }),
            },
        }))
        .unwrap();
    let mut stacks = Stacks::default();
    stacks.integers.push(1);
    let mut context = Context {
        active: [Some(address); 2],
        ..Context::default()
    };
    widgets::instruction(
        &mut ScriptHost {
            resources: &mut scripts,
            state: &mut state,
        },
        Opcode::CcTriggerop,
        0,
        &mut stacks,
        &mut context,
    )
    .unwrap();
    assert_eq!(
        scripts.deferred_operations.front().unwrap().item,
        Some(ItemDefinitionId::new(7))
    );
    state
        .apply_operation(SemanticOperation::Interface(InterfaceUpdate::SetProperty {
            widget: id,
            property: WidgetProperty::Runtime {
                address,
                value: Box::new(WidgetRuntime {
                    item: Some((ItemDefinitionId::new(8), 1)),
                    ..Default::default()
                }),
            },
        }))
        .unwrap();
    assert_eq!(
        scripts.deferred_operations.front().unwrap().item,
        Some(ItemDefinitionId::new(7))
    );
    scripts.reset();
    assert!(scripts.deferred_operations.is_empty());
}

#[test]
fn operation_listener_failure_reports_diagnostics_but_preserves_source_permission_gate() {
    let (mut scripts, _, id) = setup();
    let mut state = GameState::new();
    state
        .apply_operation(SemanticOperation::AuthenticateSession {
            revision: REVISION_240,
            player: rustclient_game_state::AuthenticatedPlayer {
                profile: ServerProfileId::new(1),
                player: PlayerIndex::new(1),
                member: false,
                staff_level: 0,
            },
        })
        .unwrap();
    state
        .apply_operation(SemanticOperation::Interface(InterfaceUpdate::SetRoot(
            Some(id.interface),
        )))
        .unwrap();
    program(
        &mut scripts,
        90110,
        &[(Opcode::PopInt, 0), (Opcode::Return, 0)],
    );
    let address = WidgetAddress::component(id);
    state
        .apply_operation(SemanticOperation::Interface(InterfaceUpdate::SetProperty {
            widget: id,
            property: WidgetProperty::Runtime {
                address,
                value: Box::new(WidgetRuntime {
                    listeners: BTreeMap::from([(
                        WidgetListener::Operation,
                        Some(vec![ScriptValue::Int(90110)]),
                    )]),
                    ..Default::default()
                }),
            },
        }))
        .unwrap();
    let action = WidgetAction {
        address,
        operation: WidgetOperation::new(0).unwrap(),
        item: None,
        interface_epoch: state.interfaces().epoch(),
    };
    let (intents, errors) = scripts.perform_operation(&mut state, action).unwrap();
    assert_eq!(errors.len(), 1);
    assert!(errors[0].contains("stack"));
    assert!(
        matches!(intents.as_slice(),[rustclient_protocol::ClientIntent::WidgetAction(selected)] if *selected==action)
    );
}
