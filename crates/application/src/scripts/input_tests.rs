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
fn key_listener_receives_code_and_character_and_hidden_widgets_do_not_capture_text() {
    let mut scripts = test_support::scripts();
    let mut state = GameState::new();
    let id = WidgetId::new(InterfaceId::new(1), 0);
    let mut widget = component(id);
    widget.listeners.insert(
        WidgetListener::Key,
        vec![
            ScriptValue::Int(90000),
            ScriptValue::Int(-2_147_483_640),
            ScriptValue::Int(-2_147_483_639),
        ],
    );
    scripts.interfaces.insert(
        id.interface,
        InterfaceDefinition::from_widgets(id.interface, vec![widget]).unwrap(),
    );
    scripts.scripts.insert(
        ScriptId::new(90000),
        Arc::new(Script {
            id: ScriptId::new(90000),
            name: String::new(),
            locals: [2, 0, 0],
            arguments: [2, 0, 0],
            switches: vec![],
            instructions: [
                (Opcode::Iload, 0),
                (Opcode::SetVarcInt, 1),
                (Opcode::Iload, 1),
                (Opcode::SetVarcInt, 2),
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
        .apply_operation(SemanticOperation::Interface(InterfaceUpdate::SetRoot(
            Some(id.interface),
        )))
        .unwrap();
    assert!(
        scripts
            .keyboard(&mut state, None, Some('h'), true)
            .unwrap()
            .errors
            .is_empty()
    );
    assert_eq!(
        state.interfaces().client_variables().get(&1),
        Some(&ScriptValue::Int(-1))
    );
    assert_eq!(
        state.interfaces().client_variables().get(&2),
        Some(&ScriptValue::Int(104))
    );
    assert!(
        scripts
            .keyboard(&mut state, Some(WidgetKey::Backspace), None, true)
            .unwrap()
            .errors
            .is_empty()
    );
    assert_eq!(
        state.interfaces().client_variables().get(&1),
        Some(&ScriptValue::Int(85))
    );
    state
        .apply_operation(SemanticOperation::Interface(InterfaceUpdate::SetProperty {
            widget: id,
            property: WidgetProperty::Hidden(true),
        }))
        .unwrap();
    let before = state.semantic_fingerprint();
    scripts.keyboard(&mut state, None, Some('x'), true).unwrap();
    assert_eq!(state.semantic_fingerprint(), before);
}

#[test]
fn script_drag_configuration_is_owned_by_interface_state_and_resets_with_the_group() {
    let mut scripts = test_support::scripts();
    let mut state = GameState::new();
    let id = WidgetId::new(InterfaceId::new(1), 0);
    scripts.interfaces.insert(
        id.interface,
        InterfaceDefinition::from_widgets(id.interface, vec![component(id)]).unwrap(),
    );
    state
        .apply_operation(SemanticOperation::BeginSession {
            revision: Revision::with_subrevision(240, 2),
        })
        .unwrap();
    state
        .apply_operation(SemanticOperation::Interface(InterfaceUpdate::SetRoot(
            Some(id.interface),
        )))
        .unwrap();
    let address = WidgetAddress::component(id);
    let mut context = Context {
        active: [Some(address); 2],
        ..Context::default()
    };
    for (op, ints) in [
        (Opcode::CcSetdraggable, vec![65536, -1]),
        (Opcode::CcSetdragdeadzone, vec![3]),
        (Opcode::CcSetdragdeadtime, vec![2]),
        (Opcode::CcSetnoscrollthrough, vec![1]),
    ] {
        let mut stacks = Stacks::default();
        stacks.integers = ints;
        widgets::instruction(
            &mut ScriptHost {
                resources: &mut scripts,
                state: &mut state,
            },
            op,
            0,
            &mut stacks,
            &mut context,
        )
        .unwrap();
    }
    let drag = state.interfaces().runtime()[&address].drag.unwrap();
    assert_eq!(
        state.interfaces().runtime()[&address].no_scroll_through,
        Some(true)
    );
    assert_eq!(
        (drag.parent, drag.zone, drag.threshold),
        (Some(address), 3, 2)
    );
    let before = state.semantic_fingerprint();
    let mut runtime = state.interfaces().runtime()[&address].clone();
    runtime.drag.as_mut().unwrap().zone += 1;
    state
        .apply_operation(SemanticOperation::Interface(InterfaceUpdate::SetProperty {
            widget: id,
            property: WidgetProperty::Runtime {
                address,
                value: Box::new(runtime),
            },
        }))
        .unwrap();
    assert_ne!(state.semantic_fingerprint(), before);
    state
        .apply_operation(SemanticOperation::Interface(InterfaceUpdate::SetRoot(None)))
        .unwrap();
    assert!(!state.interfaces().runtime().contains_key(&address));
}

#[test]
fn closing_settings_runs_the_profile_restore_hook_in_the_surviving_chat_context() {
    let mut scripts = test_support::scripts();
    let mut state = GameState::new();
    let chat = WidgetId::new(InterfaceId::new(162), 0);
    let settings = WidgetId::new(InterfaceId::new(134), 0);
    for id in [chat, settings] {
        scripts.interfaces.insert(
            id.interface,
            InterfaceDefinition::from_widgets(id.interface, vec![component(id)]).unwrap(),
        );
    }
    scripts.scripts.insert(
        ScriptId::new(2158),
        Arc::new(Script {
            id: ScriptId::new(2158),
            name: String::new(),
            locals: [0; 3],
            arguments: [0; 3],
            switches: vec![],
            instructions: [
                (Opcode::Iconst, 0),
                (Opcode::SetVarcInt, 11),
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
    for update in [
        InterfaceUpdate::SetRoot(Some(chat.interface)),
        InterfaceUpdate::Open(InterfaceMount {
            parent: chat,
            interface: settings.interface,
            kind: InterfaceMountKind::Modal,
        }),
        InterfaceUpdate::ClientVariable {
            id: 11,
            value: ScriptValue::Int(1),
        },
    ] {
        scripts
            .commit(&mut state, SemanticOperation::Interface(update))
            .unwrap();
    }
    assert_eq!(
        state.interfaces().client_variables().get(&11),
        Some(&ScriptValue::Int(1))
    );
    scripts
        .commit(
            &mut state,
            SemanticOperation::Interface(InterfaceUpdate::Close(chat)),
        )
        .unwrap();
    assert_eq!(
        state.interfaces().client_variables().get(&11),
        Some(&ScriptValue::Int(0))
    );
    assert!(!state.interfaces().groups().contains(&settings.interface));
}

#[test]
fn logout_listener_allows_the_validated_server_button_before_session_shutdown() {
    let mut scripts = test_support::scripts();
    let mut state = GameState::new();
    let id = WidgetId::new(InterfaceId::new(182), 8);
    let mut widget = component(id);
    widget.actions = vec!["Logout".into()];
    widget.permissions.enabled_operations = 1;
    widget
        .listeners
        .insert(WidgetListener::Operation, vec![ScriptValue::Int(90002)]);
    scripts.interfaces.insert(
        id.interface,
        InterfaceDefinition::from_widgets(id.interface, vec![widget]).unwrap(),
    );
    scripts.scripts.insert(
        ScriptId::new(90002),
        Arc::new(Script {
            id: ScriptId::new(90002),
            name: String::new(),
            locals: [0; 3],
            arguments: [0; 3],
            switches: vec![],
            instructions: [Opcode::Logout, Opcode::Return]
                .into_iter()
                .map(|opcode| Instruction {
                    opcode,
                    integer: 0,
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
    state
        .apply_operation(SemanticOperation::Interface(InterfaceUpdate::SetRoot(
            Some(id.interface),
        )))
        .unwrap();
    let (_, action) =
        crate::widget_actions(&state, &scripts.interfaces, WidgetAddress::component(id))
            .unwrap()
            .remove(0);
    let (intents, errors) = scripts.perform_action(&mut state, action).unwrap();
    assert!(errors.is_empty());
    assert!(
        matches!(intents.as_slice(),[rustclient_protocol::ClientIntent::WidgetAction(sent)] if *sent==action)
    );
    assert!(
        state.authenticated_player().is_some(),
        "server confirmation ends the authenticated session"
    );
}

#[test]
fn cache_preference_initialization_runs_once_and_resets_with_the_session() {
    let mut scripts = test_support::scripts();
    // Semantic expansion of selected cache helper 626 -> 605.
    scripts.scripts.insert(
        ScriptId::new(626),
        Arc::new(Script {
            id: ScriptId::new(626),
            name: String::new(),
            locals: [0; 3],
            arguments: [0; 3],
            switches: vec![],
            instructions: [
                (Opcode::Iconst, 128),
                (Opcode::SetVarcInt, 1338),
                (Opcode::Iconst, 896),
                (Opcode::SetVarcInt, 1339),
                (Opcode::Iconst, 128),
                (Opcode::SetVarcInt, 1340),
                (Opcode::Iconst, 896),
                (Opcode::SetVarcInt, 1341),
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
    let mut state = GameState::new();
    state
        .apply_operation(SemanticOperation::BeginSession {
            revision: REVISION_240,
        })
        .unwrap();
    assert!(scripts.initialize_session(&mut state).unwrap().is_empty());
    assert_eq!(
        state.interfaces().client_variables()[&1338],
        ScriptValue::Int(128)
    );
    state
        .apply_operation(SemanticOperation::Interface(
            InterfaceUpdate::ClientVariable {
                id: 1338,
                value: ScriptValue::Int(256),
            },
        ))
        .unwrap();
    assert!(scripts.initialize_session(&mut state).unwrap().is_empty());
    assert_eq!(
        state.interfaces().client_variables()[&1338],
        ScriptValue::Int(256)
    );
    scripts.reset();
    state
        .apply_operation(SemanticOperation::EndSession)
        .unwrap();
    state
        .apply_operation(SemanticOperation::BeginSession {
            revision: REVISION_240,
        })
        .unwrap();
    assert!(scripts.initialize_session(&mut state).unwrap().is_empty());
    assert_eq!(
        state.interfaces().client_variables()[&1338],
        ScriptValue::Int(128)
    );
}

#[test]
fn model_setup_key_scope_and_script_continuation_reach_existing_semantic_owners() {
    let mut scripts = test_support::scripts();
    let mut state = GameState::new();
    let id = WidgetId::new(InterfaceId::new(1), 0);
    let mut widget = component(id);
    widget.body = WidgetBody::Model {
        model: None,
        offset: [0; 2],
        angles: [0; 3],
        zoom: 100,
        sequence: None,
        orthographic: false,
        sizing: [0; 3],
    };
    widget.permissions.continue_enabled = true;
    scripts.interfaces.insert(
        id.interface,
        InterfaceDefinition::from_widgets(id.interface, vec![widget]).unwrap(),
    );
    scripts.scripts.insert(
        ScriptId::new(90007),
        Arc::new(Script {
            id: ScriptId::new(90007),
            name: String::new(),
            locals: [0; 3],
            arguments: [0; 3],
            switches: vec![],
            instructions: [
                (Opcode::Iconst, 3374),
                (Opcode::Iconst, 65536),
                (Opcode::IfSetmodel, 0),
                (Opcode::Iconst, 0),
                (Opcode::Iconst, 0),
                (Opcode::Iconst, 512),
                (Opcode::Iconst, 1536),
                (Opcode::Iconst, 0),
                (Opcode::Iconst, 950),
                (Opcode::Iconst, 65536),
                (Opcode::IfSetmodelangle, 0),
                (Opcode::Iconst, 65536),
                (Opcode::SetkeyscopeWidget, 0),
                (Opcode::Iconst, 65536),
                (Opcode::IfResumePausebutton, 0),
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
    scripts
        .enqueue(
            ScriptRequest {
                id: ScriptId::new(90007),
                arguments: vec![],
            },
            None,
        )
        .unwrap();
    assert!(scripts.drain(&mut state).is_empty());
    assert_eq!(state.interfaces().key_scope(), WidgetKeyScope::Widget(id));
    assert!(
        matches!(state.interfaces().runtime()[&WidgetAddress::component(id)].body,
        Some(WidgetBody::Model { model: Some(model), angles: [512,1536,0], zoom: 950, .. }) if model.get()==3374)
    );
    let intents = scripts.take_intents();
    let [rustclient_protocol::ClientIntent::WidgetAction(action)] = intents.as_slice() else {
        panic!("one resume intent")
    };
    assert_eq!(action.address, WidgetAddress::component(id));
    assert!(action.operation.is_continue());
    assert!(
        crate::validate_widget_action(&state, &scripts.interfaces, *action)
            .unwrap()
            .is_some()
    );
}
