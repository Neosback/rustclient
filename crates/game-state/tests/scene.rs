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

#[test]
#[expect(
    clippy::too_many_lines,
    reason = "one semantic replay contract with malformed boundary cases"
)]
fn movement_map_rebuild_cycles_and_appearance_replay_through_the_same_gateway() {
    use rustclient_game_state::{Locomotion, SceneryOperation};
    use rustclient_model::{
        MapAnchor, PlayerAppearance, PlayerInfoUpdate, PlayerMovement, SequenceChange, SequenceId,
    };
    let mut state = GameState::new();
    let mut recorded = Vec::new();
    let mut appearance = PlayerAppearance::default();
    appearance.movement_sequences.walk = Some(SequenceId::new(819));
    let next = SceneDescriptor::new(
        descriptor().id(),
        WorldTile::new(3176, 3168, Plane::MIN),
        104,
        104,
    )
    .unwrap();
    for operation in [
        authenticate(),
        placement(),
        SemanticOperation::InstallScene(descriptor()),
        SemanticOperation::UpdatePlayers(PlayerInfoUpdate {
            local_movement: Some(PlayerMovement::Step { direction: 4 }),
            local_appearance: Some(appearance),
            ..Default::default()
        }),
        SemanticOperation::Scenery(SceneryOperation::AdvanceCycles(3)),
        SemanticOperation::RebuildMap(MapAnchor { x: 3224, y: 3216 }),
        SemanticOperation::InstallScene(next),
        SemanticOperation::UpdatePlayers(PlayerInfoUpdate {
            local_sequence: SequenceChange::Set(None),
            ..Default::default()
        }),
    ] {
        let commit = state.apply_operation(operation.clone()).unwrap();
        recorded.push(ReplayEnvelope::new(operation, commit));
    }
    let player = state.entities().local_player().unwrap();
    assert_eq!(player.tile, WorldTile::new(3222, 3218, Plane::MIN));
    assert_eq!(player.locomotion, Locomotion::Idle);
    assert_eq!(state.initial_placement().unwrap().map_anchor.x, 3224);
    assert_eq!(state.loaded_scene(), Some(next));
    assert_eq!(player.orientation, 1536);
    let plane_update = SemanticOperation::UpdatePlayers(PlayerInfoUpdate {
        local_movement: Some(PlayerMovement::Teleport {
            delta_x: 0,
            delta_y: 0,
            plane_delta: 1,
        }),
        ..Default::default()
    });
    let plane_commit = state.apply_operation(plane_update.clone()).unwrap();
    assert!(plane_commit.delta().scene_changed());
    assert_eq!(state.loaded_scene(), None);
    assert_eq!(
        state.initial_placement().unwrap().map_anchor.plane,
        Plane::new(1).unwrap()
    );
    recorded.push(ReplayEnvelope::new(plane_update, plane_commit));
    let mut replay = GameState::new();
    replay.replay_envelopes(&recorded).unwrap();
    assert_eq!(replay.semantic_fingerprint(), state.semantic_fingerprint());
    let before = state.semantic_fingerprint();
    assert!(
        state
            .apply_operation(SemanticOperation::UpdatePlayers(PlayerInfoUpdate {
                local_movement: Some(PlayerMovement::Step { direction: 8 }),
                ..Default::default()
            }))
            .is_err()
    );
    assert_eq!(before, state.semantic_fingerprint());
    assert!(
        state
            .apply_operation(SemanticOperation::UpdatePlayers(PlayerInfoUpdate {
                local_movement: Some(PlayerMovement::Teleport {
                    delta_x: i32::MAX,
                    delta_y: 0,
                    plane_delta: 0
                }),
                ..Default::default()
            }))
            .is_err()
    );
    assert_eq!(before, state.semantic_fingerprint());
    let mut changed = state
        .entities()
        .local_player()
        .unwrap()
        .appearance
        .clone()
        .unwrap();
    changed.colors[0] = 1;
    state
        .apply_operation(SemanticOperation::UpdatePlayers(PlayerInfoUpdate {
            local_appearance: Some(changed),
            ..Default::default()
        }))
        .unwrap();
    assert_ne!(before, state.semantic_fingerprint());
    state
        .apply_operation(SemanticOperation::EndSession)
        .unwrap();
    assert_eq!(
        state.semantic_fingerprint(),
        GameState::new().semantic_fingerprint()
    );
}
