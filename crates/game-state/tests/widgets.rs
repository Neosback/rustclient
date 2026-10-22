use rustclient_game_state::{GameState, ReplayEnvelope, SemanticOperation};
use rustclient_model::{
    InterfaceId, InterfaceUpdate, Revision, ScriptValue, WidgetAddress, WidgetBody, WidgetId,
    WidgetProperty,
};
fn update(state: &mut GameState, recording: &mut Vec<ReplayEnvelope>, value: InterfaceUpdate) {
    let op = SemanticOperation::Interface(value);
    let commit = state.apply_operation(op.clone()).unwrap();
    recording.push(ReplayEnvelope::new(op, commit));
}
fn container() -> WidgetBody {
    WidgetBody::Container {
        scroll_size: [100, 500],
        no_click_through: false,
    }
}
#[test]
#[expect(
    clippy::too_many_lines,
    reason = "one focused semantic/codec family contract"
)]
fn dynamic_widget_lifecycle_has_one_property_owner_atomic_failures_and_exact_replay() {
    let mut state = GameState::new();
    let begin = SemanticOperation::BeginSession {
        revision: Revision::with_subrevision(240, 2),
    };
    let commit = state.apply_operation(begin.clone()).unwrap();
    let mut recording = vec![ReplayEnvelope::new(begin, commit)];
    let group = InterfaceId::new(12);
    let widget = WidgetId::new(group, 13);
    let root = WidgetAddress::component(widget);
    update(
        &mut state,
        &mut recording,
        InterfaceUpdate::SetRoot(Some(group)),
    );
    update(
        &mut state,
        &mut recording,
        InterfaceUpdate::SetProperty {
            widget,
            property: WidgetProperty::Text("server".into()),
        },
    );
    let mut runtime = state.interfaces().runtime()[&root].clone();
    runtime.text = Some("script".into());
    update(
        &mut state,
        &mut recording,
        InterfaceUpdate::SetProperty {
            widget,
            property: WidgetProperty::Runtime {
                address: root,
                value: Box::new(runtime),
            },
        },
    );
    update(
        &mut state,
        &mut recording,
        InterfaceUpdate::SetProperty {
            widget,
            property: WidgetProperty::Text("later server".into()),
        },
    );
    assert_eq!(
        state.interfaces().runtime()[&root].text.as_deref(),
        Some("later server")
    );
    for (index, parent_child) in [(0, None), (1, Some(0))] {
        update(
            &mut state,
            &mut recording,
            InterfaceUpdate::CreateChild {
                parent: widget,
                parent_child,
                allow_sparse: false,
                index,
                body: container(),
            },
        );
    }
    let first = WidgetAddress {
        widget,
        child: Some(0),
    };
    let mut cycle = state.interfaces().runtime()[&first].clone();
    cycle.parent_child = Some(1);
    let before = state.semantic_fingerprint();
    assert!(
        state
            .apply_operation(SemanticOperation::Interface(InterfaceUpdate::SetProperty {
                widget,
                property: WidgetProperty::Runtime {
                    address: first,
                    value: Box::new(cycle)
                }
            }))
            .is_err()
    );
    assert_eq!(state.semantic_fingerprint(), before);
    update(
        &mut state,
        &mut recording,
        InterfaceUpdate::ClientVariable {
            id: 7,
            value: ScriptValue::Long(99),
        },
    );
    let mut replay = GameState::new();
    replay.replay_envelopes(&recording).unwrap();
    assert_eq!(replay.semantic_fingerprint(), state.semantic_fingerprint());
    update(
        &mut state,
        &mut recording,
        InterfaceUpdate::DeleteChild(first),
    );
    assert!(
        !state
            .interfaces()
            .runtime()
            .keys()
            .any(|a| a.child.is_some())
    );
    update(&mut state, &mut recording, InterfaceUpdate::SetRoot(None));
    assert!(state.interfaces().runtime().is_empty());
    assert_eq!(state.interfaces().client_variables().len(), 1);
    state
        .apply_operation(SemanticOperation::EndSession)
        .unwrap();
    assert!(state.interfaces().client_variables().is_empty());
    assert_eq!(
        state.semantic_fingerprint(),
        GameState::new().semantic_fingerprint()
    );
}
