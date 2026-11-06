use rustclient_game_state::{
    AuthenticatedPlayer, GameState, InitialPlacement, ReplayEnvelope, SemanticOperation,
};
use rustclient_model::*;

fn session() -> GameState {
    let mut state = GameState::new();
    for op in [
        SemanticOperation::AuthenticateSession {
            revision: REVISION_240,
            player: AuthenticatedPlayer {
                profile: ServerProfileId::new(1),
                player: PlayerIndex::new(1),
                member: false,
                staff_level: 0,
            },
        },
        SemanticOperation::EstablishInitialPlacement(InitialPlacement {
            position: WorldTile::new(3220, 3220, Plane::MIN),
            map_anchor: WorldTile::new(3216, 3216, Plane::MIN),
            area: None,
        }),
    ] {
        state.apply_operation(op).unwrap();
    }
    state
}
fn option(slot: u8, text: Option<&str>) -> SemanticOperation {
    SemanticOperation::PlayerStatus(PlayerStatusUpdate::PlayerOption {
        slot,
        text: text.map(str::to_owned),
        priority: true,
    })
}

#[test]
fn player_options_are_retained_replaced_removed_validated_and_cleared_with_the_session() {
    let mut state = session();
    let mut recording = Vec::new();
    let mut apply = |state: &mut GameState, op: SemanticOperation| {
        let commit = state.apply_operation(op.clone()).unwrap();
        recording.push(ReplayEnvelope::new(op, commit));
        commit
    };
    assert!(
        apply(&mut state, option(2, Some("Follow")))
            .delta()
            .entities_changed()
    );
    assert_eq!(state.entities().player_option(2).unwrap().text, "Follow");
    assert!(state.entities().player_option(1).is_none());
    // The same option again changes nothing; a different text replaces it.
    assert!(
        apply(&mut state, option(2, Some("Follow")))
            .delta()
            .is_empty()
    );
    apply(&mut state, option(2, Some("Trade with")));
    assert_eq!(
        state.entities().player_option(2).unwrap().text,
        "Trade with"
    );
    apply(&mut state, option(2, None));
    assert!(state.entities().player_option(2).is_none());
    apply(&mut state, option(8, Some("Report")));

    // Slots outside 1..=8 are refused without touching state.
    assert!(state.apply_operation(option(0, Some("x"))).is_err());
    assert!(state.apply_operation(option(9, Some("x"))).is_err());

    let mut replay = session();
    replay.replay_envelopes(&recording).unwrap();
    assert_eq!(replay.semantic_fingerprint(), state.semantic_fingerprint());

    state
        .apply_operation(SemanticOperation::EndSession)
        .unwrap();
    assert!(state.entities().player_option(8).is_none());
}
