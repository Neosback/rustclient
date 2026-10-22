use rustclient_application::{validate_widget_action, widget_actions};
use rustclient_cache::{
    GroupId, IndexId, Source, osrs240::widgets::decode, widget::InterfaceDefinition,
};
use rustclient_game_state::{AuthenticatedPlayer, GameState, SemanticOperation};
use rustclient_model::*;
use std::collections::BTreeMap;
fn apply(state: &mut GameState, update: InterfaceUpdate) {
    state
        .apply_operation(SemanticOperation::Interface(update))
        .unwrap();
}
#[test]
#[expect(
    clippy::too_many_lines,
    reason = "one focused semantic/codec family contract"
)]
fn widget_actions_reject_reused_children_changed_items_and_revoked_permissions() {
    let group = InterfaceId::new(12);
    let widget = WidgetId::new(group, 0);
    let address = WidgetAddress {
        widget,
        child: Some(0),
    };
    let mut root = decode(
        include_bytes!("../../cache/tests/fixtures/widgets/container.bin"),
        widget,
        Source::group(IndexId(3), GroupId(12)),
    )
    .unwrap();
    root.parent = None;
    root.hidden = false;
    root.layout = WidgetLayout {
        offset: [0; 2],
        size: [765, 503],
        position_mode: [PositionMode::Start; 2],
        size_mode: [SizeMode::Absolute; 2],
    };
    let assets = BTreeMap::from([(
        group,
        InterfaceDefinition::from_widgets(group, vec![root]).unwrap(),
    )]);
    let mut state = GameState::new();
    state
        .apply_operation(SemanticOperation::AuthenticateSession {
            revision: Revision::with_subrevision(240, 2),
            player: AuthenticatedPlayer {
                profile: ServerProfileId::new(1),
                player: PlayerIndex::new(1),
                member: true,
                staff_level: 0,
            },
        })
        .unwrap();
    apply(&mut state, InterfaceUpdate::SetRoot(Some(group)));
    apply(
        &mut state,
        InterfaceUpdate::CreateChild {
            parent: widget,
            parent_child: None,
            index: 0,
            allow_sparse: false,
            body: WidgetBody::Rectangle {
                color: 0,
                filled: true,
                transparency: 0,
            },
        },
    );
    let mut runtime = state.interfaces().runtime()[&address].clone();
    runtime.layout.as_mut().unwrap().size = [36, 32];
    runtime.actions = Some(vec!["Deposit-1".into(), "Deposit-All".into()]);
    runtime.item = Some((ItemDefinitionId::new(995), 10));
    apply(
        &mut state,
        InterfaceUpdate::SetProperty {
            widget,
            property: WidgetProperty::Runtime {
                address,
                value: Box::new(runtime),
            },
        },
    );
    let permissions = WidgetPermissions {
        enabled_operations: 3,
        ..Default::default()
    };
    apply(
        &mut state,
        InterfaceUpdate::SetEvents(WidgetEventRange {
            widget,
            first: 0,
            last: 0,
            permissions,
        }),
    );
    let actions = widget_actions(&state, &assets, address).unwrap();
    assert_eq!(actions.len(), 2);
    let selected = actions[0].1;
    assert!(
        validate_widget_action(&state, &assets, selected)
            .unwrap()
            .is_some()
    );
    let mut runtime = state.interfaces().runtime()[&address].clone();
    runtime.item = Some((ItemDefinitionId::new(996), 10));
    apply(
        &mut state,
        InterfaceUpdate::SetProperty {
            widget,
            property: WidgetProperty::Runtime {
                address,
                value: Box::new(runtime),
            },
        },
    );
    assert!(validate_widget_action(&state, &assets, selected).is_err());
    let selected = widget_actions(&state, &assets, address).unwrap()[0].1;
    apply(
        &mut state,
        InterfaceUpdate::SetEvents(WidgetEventRange {
            widget,
            first: 0,
            last: 0,
            permissions: WidgetPermissions::default(),
        }),
    );
    assert!(validate_widget_action(&state, &assets, selected).is_err());
    apply(&mut state, InterfaceUpdate::SetRoot(None));
    apply(&mut state, InterfaceUpdate::SetRoot(Some(group)));
    assert!(validate_widget_action(&state, &assets, selected).is_err());
}
