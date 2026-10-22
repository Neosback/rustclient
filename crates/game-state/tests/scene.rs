use rustclient_game_state::{
    AuthenticatedPlayer, ConnectionStateError, GameState, InitialPlacement, OperationError,
    ReplayEnvelope, SemanticOperation,
};
use rustclient_model::{
    Plane, PlayerIndex, Revision, SceneAssetId, SceneDescriptor, ServerProfileId, WorldTile,
};
fn descriptor() -> SceneDescriptor {
    SceneDescriptor::new(
        SceneAssetId::new([7; 32]),
        WorldTile::new(3168, 3168, Plane::MIN),
        104,
        104,
    )
    .unwrap()
}
fn authenticate() -> SemanticOperation {
    SemanticOperation::AuthenticateSession {
        revision: Revision::with_subrevision(240, 2),
        player: AuthenticatedPlayer {
            profile: ServerProfileId::new(1),
            player: PlayerIndex::new(1),
            member: false,
            staff_level: 0,
        },
    }
}
fn placement() -> SemanticOperation {
    SemanticOperation::EstablishInitialPlacement(InitialPlacement {
        position: WorldTile::new(3221, 3218, Plane::MIN),
        map_anchor: WorldTile::new(3216, 3216, Plane::MIN),
        area: None,
    })
}
#[test]
fn scene_selection_replays_and_disconnect_clears_reference() {
    let mut state = GameState::new();
    let mut envelopes = Vec::new();
    for op in [
        authenticate(),
        placement(),
        SemanticOperation::InstallScene(descriptor()),
    ] {
        let commit = state.apply_operation(op.clone()).unwrap();
        envelopes.push(ReplayEnvelope::new(op, commit));
    }
    assert!(envelopes[2].commit().delta().scene_changed());
    assert_eq!(state.loaded_scene(), Some(descriptor()));
    let mut replay = GameState::new();
    replay.replay_envelopes(&envelopes).unwrap();
    assert_eq!(state.semantic_fingerprint(), replay.semantic_fingerprint());
    let fingerprint = state.semantic_fingerprint();
    assert!(
        state
            .apply_operation(SemanticOperation::InstallScene(descriptor()))
            .is_err()
    );
    assert_eq!(fingerprint, state.semantic_fingerprint());
    let commit = state
        .apply_operation(SemanticOperation::EndSession)
        .unwrap();
    assert!(commit.delta().scene_changed());
    assert_eq!(state.loaded_scene(), None);
    state.apply_operation(authenticate()).unwrap();
    assert_eq!(state.loaded_scene(), None);
}
#[test]
fn invalid_scene_installation_is_atomic() {
    let mut state = GameState::new();
    let before = state.semantic_fingerprint();
    assert_eq!(
        state.apply_operation(SemanticOperation::InstallScene(descriptor())),
        Err(OperationError::Connection(
            ConnectionStateError::SceneRequiresPlacement
        ))
    );
    assert_eq!(before, state.semantic_fingerprint());
    state.apply_operation(authenticate()).unwrap();
    state.apply_operation(placement()).unwrap();
    let before = state.semantic_fingerprint();
    let outside = SceneDescriptor::new(
        descriptor().id(),
        WorldTile::new(0, 0, Plane::MIN),
        104,
        104,
    )
    .unwrap();
    assert_eq!(
        state.apply_operation(SemanticOperation::InstallScene(outside)),
        Err(OperationError::Connection(
            ConnectionStateError::SceneOutsidePlacement
        ))
    );
    assert_eq!(before, state.semantic_fingerprint());
}
#[test]
fn every_scene_descriptor_field_participates_in_fingerprints() {
    let origin = descriptor().origin();
    let mut seen = std::collections::BTreeSet::new();
    for d in [
        descriptor(),
        SceneDescriptor::new(SceneAssetId::new([8; 32]), origin, 104, 104).unwrap(),
        SceneDescriptor::new(
            descriptor().id(),
            WorldTile::new(3167, 3168, Plane::MIN),
            104,
            104,
        )
        .unwrap(),
        SceneDescriptor::new(descriptor().id(), origin, 105, 104).unwrap(),
        SceneDescriptor::new(descriptor().id(), origin, 104, 105).unwrap(),
    ] {
        let mut state = GameState::new();
        state.apply_operation(authenticate()).unwrap();
        state.apply_operation(placement()).unwrap();
        state
            .apply_operation(SemanticOperation::InstallScene(d))
            .unwrap();
        assert!(seen.insert(state.semantic_fingerprint()));
    }
}
