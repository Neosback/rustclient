use super::*;
use rustclient_cache::{
    GroupId, IndexId, Source,
    osrs240::widgets::decode_legacy,
    widget::{InterfaceDefinition, LegacyButton},
};
use rustclient_game_state::{AuthenticatedPlayer, SceneryOperation, SemanticOperation};
use rustclient_model::*;

fn setup(
    button: LegacyButton,
) -> (
    ClientScripts<rustclient_platform::MemoryBlobs>,
    GameState,
    WidgetId,
) {
    let mut scripts = super::super::test_support::scripts();
    let id = WidgetId::new(InterfaceId::new(12), 3);
    let (mut widget, mut legacy) = decode_legacy(
        include_bytes!("../../../../cache/tests/fixtures/widgets/if1-3.bin"),
        id,
        Source::group(IndexId(3), GroupId(12)),
    )
    .unwrap();
    widget.parent = None;
    widget.hidden = false;
    widget.content = WidgetContent::None;
    widget.layout.offset = [0; 2];
    widget.layout.size = [100, 100];
    widget.permissions = WidgetPermissions::default();
    // A legacy button must not invoke an ordinary operation listener.
    widget
        .listeners
        .insert(WidgetListener::Operation, vec![ScriptValue::Int(999_999)]);
    legacy.button = button;
    legacy.button_text = "Authored label".into();
    legacy.expressions = vec![vec![5, 42, 0]];
    legacy.comparisons = vec![(1, 7)];
    scripts.interfaces.insert(
        id.interface,
        InterfaceDefinition::from_widgets(id.interface, vec![widget])
            .unwrap()
            .with_legacy(BTreeMap::from([(id, legacy)]))
            .unwrap(),
    );
    let mut state = GameState::new();
    state
        .apply_operation(SemanticOperation::AuthenticateSession {
            revision: REVISION_240,
            player: AuthenticatedPlayer {
                profile: ServerProfileId::new(1),
                player: PlayerIndex::new(1),
                member: true,
                staff_level: 0,
            },
        })
        .unwrap();
    state
        .apply_operation(SemanticOperation::Interface(InterfaceUpdate::SetRoot(
            Some(id.interface),
        )))
        .unwrap();
    (scripts, state, id)
}
fn action(
    scripts: &ClientScripts<rustclient_platform::MemoryBlobs>,
    state: &GameState,
    id: WidgetId,
) -> WidgetAction {
    let entries =
        crate::widget_actions(state, &scripts.interfaces, WidgetAddress::component(id)).unwrap();
    assert_eq!(entries.len(), 1);
    assert_eq!(entries[0].0, "Authored label");
    entries[0].1
}
fn set_server(state: &mut GameState, value: i32) {
    state
        .apply_operation(SemanticOperation::Scenery(SceneryOperation::SetVariable {
            id: VarpId::new(42),
            value,
        }))
        .unwrap();
}
fn replace_legacy(
    scripts: &mut ClientScripts<rustclient_platform::MemoryBlobs>,
    id: WidgetId,
    edit: impl FnOnce(&mut LegacyPresentation),
) {
    let group = scripts.interfaces.remove(&id.interface).unwrap();
    let mut legacy = group.legacy(id).unwrap().clone();
    edit(&mut legacy);
    scripts.interfaces.insert(
        id.interface,
        group.with_legacy(BTreeMap::from([(id, legacy)])).unwrap(),
    );
}
#[test]
fn authored_click_uses_its_label_without_operation_permissions_or_onop() {
    let (mut scripts, mut state, id) = setup(LegacyButton::Click);
    let selected = action(&scripts, &state, id);
    assert_eq!(selected.operation, WidgetOperation::BUTTON_CLICK);
    let before = state.semantic_fingerprint();
    let (intents, errors) = scripts.perform_action(&mut state, selected).unwrap();
    assert!(errors.is_empty(), "{errors:?}");
    assert!(
        matches!(intents.as_slice(), [rustclient_protocol::ClientIntent::WidgetAction(value)] if *value == selected)
    );
    assert_eq!(state.semantic_fingerprint(), before);
    assert!(
        scripts
            .perform_action(
                &mut state,
                WidgetAction {
                    operation: WidgetOperation::new(0).unwrap(),
                    ..selected
                }
            )
            .is_err()
    );
}
#[test]
fn toggles_notify_the_existing_queue_and_preserve_the_server_shadow() {
    use rustclient_cache::script::{Instruction, Opcode, Script};
    let (mut scripts, mut state, id) = setup(LegacyButton::Toggle);
    let mut widget = scripts.interfaces[&id.interface].widgets()[&id].clone();
    widget
        .listeners
        .insert(WidgetListener::Variable, vec![ScriptValue::Int(90100)]);
    widget.variable_triggers = vec![42];
    let legacy = scripts.interfaces[&id.interface]
        .legacy(id)
        .unwrap()
        .clone();
    scripts.interfaces.insert(
        id.interface,
        InterfaceDefinition::from_widgets(id.interface, vec![widget])
            .unwrap()
            .with_legacy(BTreeMap::from([(id, legacy)]))
            .unwrap(),
    );
    scripts.scripts.insert(
        ScriptId::new(90100),
        std::sync::Arc::new(Script {
            id: ScriptId::new(90100),
            name: String::new(),
            locals: [0; 3],
            arguments: [0; 3],
            switches: vec![],
            instructions: [
                (Opcode::GetVarp, 42),
                (Opcode::SetVarcInt, 999),
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
    set_server(&mut state, 7);
    let selected = action(&scripts, &state, id);
    let (intents, errors) = scripts.perform_action(&mut state, selected).unwrap();
    assert_eq!(intents.len(), 1);
    assert_eq!(state.scenery().variable(VarpId::new(42)), -6);
    assert!(
        errors
            .iter()
            .any(|e| e.contains("option-type side effects"))
    );
    assert!(!state.interfaces().client_variables().contains_key(&999));
    assert!(scripts.drain(&mut state).is_empty());
    assert_eq!(
        state.interfaces().client_variables()[&999],
        ScriptValue::Int(-6)
    );
    state
        .apply_operation(SemanticOperation::Scenery(
            SceneryOperation::SynchronizeVariables,
        ))
        .unwrap();
    assert_eq!(state.scenery().variable(VarpId::new(42)), 7);
}
#[test]
fn selecting_an_equal_value_still_sends_the_button_without_a_state_change() {
    let (mut scripts, mut state, id) = setup(LegacyButton::Select);
    set_server(&mut state, 7);
    let selected = action(&scripts, &state, id);
    let before = state.semantic_fingerprint();
    let (intents, errors) = scripts.perform_action(&mut state, selected).unwrap();
    assert_eq!(intents.len(), 1);
    assert!(errors.is_empty(), "{errors:?}");
    assert_eq!(state.semantic_fingerprint(), before);
    set_server(&mut state, 3);
    let (intents, errors) = scripts.perform_action(&mut state, selected).unwrap();
    assert_eq!(intents.len(), 1);
    assert_eq!(state.scenery().variable(VarpId::new(42)), 7);
    assert_eq!(errors.len(), 1);
}
#[test]
fn malformed_direct_references_retain_the_packet_and_diagnose_the_local_failure() {
    let (mut scripts, mut state, id) = setup(LegacyButton::Toggle);
    replace_legacy(&mut scripts, id, |legacy| {
        legacy.expressions = vec![vec![5]];
    });
    let selected = action(&scripts, &state, id);
    let before = state.semantic_fingerprint();
    let (intents, errors) = scripts.perform_action(&mut state, selected).unwrap();
    assert_eq!(intents.len(), 1);
    assert!(errors[0].contains("truncated button varp"));
    assert_eq!(state.semantic_fingerprint(), before);
}
#[test]
fn unsupported_content_and_obsolete_selections_cannot_emit_a_button() {
    let (mut scripts, mut state, id) = setup(LegacyButton::Click);
    let selected = action(&scripts, &state, id);
    let mut widget = scripts.interfaces[&id.interface].widgets()[&id].clone();
    widget.content = WidgetContent::Other(205);
    let legacy = scripts.interfaces[&id.interface]
        .legacy(id)
        .unwrap()
        .clone();
    scripts.interfaces.insert(
        id.interface,
        InterfaceDefinition::from_widgets(id.interface, vec![widget])
            .unwrap()
            .with_legacy(BTreeMap::from([(id, legacy)]))
            .unwrap(),
    );
    assert!(
        scripts
            .perform_action(&mut state, selected)
            .unwrap_err()
            .contains("content handler")
    );
    state
        .apply_operation(SemanticOperation::Interface(InterfaceUpdate::SetRoot(None)))
        .unwrap();
    assert!(scripts.perform_action(&mut state, selected).is_err());
}
#[test]
fn target_and_close_are_not_reinterpreted_as_ordinary_operations() {
    for button in [
        LegacyButton::Target,
        LegacyButton::Close,
        LegacyButton::None,
    ] {
        let (scripts, state, id) = setup(button);
        assert!(
            crate::widget_actions(&state, &scripts.interfaces, WidgetAddress::component(id))
                .unwrap()
                .is_empty()
        );
    }
}

#[test]
fn hidden_unauthenticated_and_dynamic_selections_cannot_borrow_a_base_button() {
    let (mut scripts, mut state, id) = setup(LegacyButton::Click);
    let selected = action(&scripts, &state, id);
    state
        .apply_operation(SemanticOperation::Interface(InterfaceUpdate::CreateChild {
            parent: id,
            parent_child: None,
            reject_replace: false,
            index: 0,
            body: WidgetBody::Rectangle {
                color: 0,
                filled: true,
                transparency: 0,
            },
        }))
        .unwrap();
    let selected = WidgetAction {
        interface_epoch: state.interfaces().epoch(),
        ..selected
    };
    assert!(
        scripts
            .perform_action(
                &mut state,
                WidgetAction {
                    address: WidgetAddress {
                        child: Some(0),
                        ..selected.address
                    },
                    ..selected
                }
            )
            .is_err()
    );
    state
        .apply_operation(SemanticOperation::Interface(InterfaceUpdate::SetProperty {
            widget: id,
            property: WidgetProperty::Hidden(true),
        }))
        .unwrap();
    assert!(scripts.perform_action(&mut state, selected).is_err());
    state
        .apply_operation(SemanticOperation::EndSession)
        .unwrap();
    assert!(
        crate::widget_actions(&state, &scripts.interfaces, WidgetAddress::component(id)).is_err()
            || crate::widget_actions(&state, &scripts.interfaces, WidgetAddress::component(id))
                .unwrap()
                .is_empty()
    );
}
