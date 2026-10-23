use rustclient_game_state::{
    AuthenticatedPlayer, CommitStreamFingerprintBuilder, ConnectionStateError, GameState,
    InitialPlacement, OperationError, ReplayEnvelope, SemanticOperation, SessionLifecycle,
};
use rustclient_model::{Plane, PlayerIndex, Revision, ServerProfileId, WorldAreaId, WorldTile};
fn player() -> AuthenticatedPlayer {
    AuthenticatedPlayer {
        profile: ServerProfileId::new(1),
        player: PlayerIndex::new(1),
        member: false,
        staff_level: 0,
    }
}
fn placement() -> InitialPlacement {
    InitialPlacement {
        position: WorldTile::new(3221, 3218, Plane::MIN),
        map_anchor: WorldTile::new(3216, 3216, Plane::MIN),
        area: None,
    }
}
fn authenticate(player: AuthenticatedPlayer) -> SemanticOperation {
    SemanticOperation::AuthenticateSession {
        revision: Revision::with_subrevision(240, 2),
        player,
    }
}
#[test]
fn authentication_and_initial_placement_replay_exactly_and_reset_on_disconnect() {
    let mut state = GameState::new();
    let before = state.semantic_fingerprint();
    let mut envelopes = Vec::new();
    let mut stream = CommitStreamFingerprintBuilder::new();
    for operation in [
        authenticate(player()),
        SemanticOperation::EstablishInitialPlacement(placement()),
    ] {
        let commit = state.apply_operation(operation.clone()).unwrap();
        assert!(commit.delta().connection_changed());
        stream.push(commit);
        envelopes.push(ReplayEnvelope::new(operation, commit));
    }
    assert_eq!(
        state.semantic_fingerprint().get(),
        13_763_867_444_201_730_751
    );
    assert_eq!(stream.finish().get(), 2_022_803_856_463_524_580);
    assert_eq!(state.lifecycle(), SessionLifecycle::Active);
    assert_eq!(state.authenticated_player(), Some(player()));
    assert_eq!(state.initial_placement(), Some(placement()));
    let mut replay = GameState::new();
    let mut replay_stream = CommitStreamFingerprintBuilder::new();
    replay
        .replay_envelopes_with_commits(&envelopes, |_, commit| replay_stream.push(commit))
        .unwrap();
    assert_eq!(replay.semantic_fingerprint(), state.semantic_fingerprint());
    assert_eq!(replay_stream.finish(), stream.finish());
    let commit = state
        .apply_operation(SemanticOperation::EndSession)
        .unwrap();
    assert!(commit.delta().connection_changed());
    assert_eq!(state.authenticated_player(), None);
    assert_eq!(state.initial_placement(), None);
    assert_eq!(state.semantic_fingerprint(), before);
}
#[test]
fn invalid_placement_is_atomic_and_requires_authentication() {
    let mut state = GameState::new();
    let before = state.semantic_fingerprint();
    assert_eq!(
        state.apply_operation(SemanticOperation::EstablishInitialPlacement(placement())),
        Err(OperationError::Connection(
            ConnectionStateError::NotAuthenticated
        ))
    );
    assert_eq!(state.semantic_fingerprint(), before);
    state.apply_operation(authenticate(player())).unwrap();
    let before = state.semantic_fingerprint();
    let mut invalid = placement();
    invalid.map_anchor.plane = Plane::MAX;
    assert!(
        state
            .apply_operation(SemanticOperation::EstablishInitialPlacement(invalid))
            .is_err()
    );
    assert_eq!(state.semantic_fingerprint(), before);
    state
        .apply_operation(SemanticOperation::EstablishInitialPlacement(placement()))
        .unwrap();
    let before = state.semantic_fingerprint();
    assert!(
        state
            .apply_operation(SemanticOperation::EstablishInitialPlacement(placement()))
            .is_err()
    );
    assert_eq!(state.semantic_fingerprint(), before);
}
#[test]
fn every_new_observable_field_participates_in_fingerprints() {
    let mut fingerprints = std::collections::BTreeSet::new();
    let p = player();
    for changed in [
        p,
        AuthenticatedPlayer {
            profile: ServerProfileId::new(2),
            ..p
        },
        AuthenticatedPlayer {
            player: PlayerIndex::new(2),
            ..p
        },
        AuthenticatedPlayer { member: true, ..p },
        AuthenticatedPlayer {
            staff_level: 1,
            ..p
        },
    ] {
        let mut state = GameState::new();
        state.apply_operation(authenticate(changed)).unwrap();
        assert!(fingerprints.insert(state.semantic_fingerprint()));
    }
    let p = placement();
    for changed in [
        p,
        InitialPlacement {
            position: WorldTile::new(3222, 3218, Plane::MIN),
            ..p
        },
        InitialPlacement {
            position: WorldTile::new(3221, 3219, Plane::MIN),
            ..p
        },
        InitialPlacement {
            map_anchor: WorldTile::new(3208, 3216, Plane::MIN),
            ..p
        },
        InitialPlacement {
            map_anchor: WorldTile::new(3216, 3208, Plane::MIN),
            ..p
        },
        InitialPlacement {
            area: Some(WorldAreaId::new(0)),
            ..p
        },
        InitialPlacement {
            position: WorldTile::new(3221, 3218, Plane::MAX),
            map_anchor: WorldTile::new(3216, 3216, Plane::MAX),
            ..p
        },
    ] {
        let mut state = GameState::new();
        state.apply_operation(authenticate(player())).unwrap();
        state
            .apply_operation(SemanticOperation::EstablishInitialPlacement(changed))
            .unwrap();
        assert!(fingerprints.insert(state.semantic_fingerprint()));
    }
}
