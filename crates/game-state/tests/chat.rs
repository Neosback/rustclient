use rustclient_game_state::{GameState, ReplayEnvelope, SemanticOperation};
use rustclient_model::{GameMessage, PlayerStatusUpdate, Revision, ids::ChatChannelId};

#[test]
fn chat_status_and_viewport_preserve_bounds_replay_and_session_cleanup() {
    let mut state = GameState::new();
    let mut recording = Vec::new();
    let channel = ChatChannelId::new(0);
    let mut operations = vec![SemanticOperation::BeginSession {
        revision: Revision::with_subrevision(240, 2),
    }];
    operations.extend((0..105).map(|i| {
        SemanticOperation::GameMessage(GameMessage {
            channel,
            sender: "Banker".into(),
            text: format!("message {i}"),
        })
    }));
    operations.extend([
        SemanticOperation::ChatFilterText("coins".into()),
        SemanticOperation::ChatFilters([Some(1), Some(2), Some(0)]),
        SemanticOperation::PlayerStatus(PlayerStatusUpdate::Reboot {
            cycles: 100,
            message: Some("Update".into()),
        }),
        SemanticOperation::Scenery(rustclient_game_state::SceneryOperation::AdvanceCycles(10)),
    ]);
    for op in operations {
        let commit = state.apply_operation(op.clone()).unwrap();
        recording.push(ReplayEnvelope::new(op, commit));
    }
    assert_eq!(state.chat().channel(channel).count(), 100);
    assert_eq!(
        state.chat().channel(channel).next().unwrap().message.text,
        "message 104"
    );
    assert_eq!(state.entities().reboot_cycles(), 90);
    let before = state.semantic_fingerprint();
    assert!(
        state
            .apply_operation(SemanticOperation::ChatFilterText("x".repeat(4097)))
            .is_err()
    );
    assert!(
        state
            .apply_operation(SemanticOperation::ChatFilters([None, Some(3), None]))
            .is_err()
    );
    assert_eq!(state.semantic_fingerprint(), before);
    let mut replay = GameState::new();
    replay.replay_envelopes(&recording).unwrap();
    assert_eq!(replay.semantic_fingerprint(), before);
    state
        .apply_operation(SemanticOperation::EndSession)
        .unwrap();
    assert!(state.chat().entries().is_empty());
    assert!(state.chat().filter_text().is_empty());
    assert_eq!(state.entities().reboot_cycles(), 0);
    assert_eq!(
        state.interfaces().viewport(),
        rustclient_model::ViewportSettings::default()
    );
}
