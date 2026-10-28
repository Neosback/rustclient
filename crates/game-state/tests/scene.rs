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

#[test]
fn action_animation_delay_is_independent_of_locomotion_and_server_reset_is_replayable() {
    use rustclient_model::{
        NewNpc, NpcAttributeUpdate, NpcDefinitionId, NpcIndex, NpcInfoUpdate, NpcProperty,
        PlayerInfoUpdate, PlayerMovement, SequenceChange, SequenceId,
    };
    let mut state = GameState::new();
    let mut replay = Vec::new();
    for operation in [
        authenticate(),
        placement(),
        SemanticOperation::UpdatePlayers(PlayerInfoUpdate {
            local_sequence: SequenceChange::Set(Some(SequenceId::new(10))),
            local_sequence_delay: 3,
            ..Default::default()
        }),
        SemanticOperation::UpdateNpcs(NpcInfoUpdate {
            new_npcs: vec![NewNpc {
                index: NpcIndex::new(1),
                definition_id: NpcDefinitionId::new(1),
                delta_x: 0,
                delta_y: 0,
                orientation: 0,
            }],
            attributes: vec![NpcAttributeUpdate {
                index: NpcIndex::new(1),
                changes: vec![NpcProperty::Sequence {
                    sequence: Some(SequenceId::new(10)),
                    delay: 3,
                }],
            }],
            ..Default::default()
        }),
        SemanticOperation::Scenery(rustclient_game_state::SceneryOperation::AdvanceCycles(5)),
        SemanticOperation::UpdatePlayers(PlayerInfoUpdate {
            local_movement: Some(PlayerMovement::Step { direction: 4 }),
            ..Default::default()
        }),
    ] {
        let commit = state.apply_operation(operation.clone()).unwrap();
        replay.push(ReplayEnvelope::new(operation, commit));
    }
    let player = state.entities().local_player().unwrap();
    assert_eq!(
        (
            player.sequence.delay,
            player.sequence.cycle,
            player.animation_cycle
        ),
        (0, 2, 0)
    );
    let tile = player.tile;
    let operation = SemanticOperation::ResetActorAnimations;
    let commit = state.apply_operation(operation.clone()).unwrap();
    assert!(commit.delta().entities_changed());
    replay.push(ReplayEnvelope::new(operation, commit));
    assert!(
        state
            .entities()
            .players()
            .values()
            .all(|p| !p.sequence.is_active())
    );
    assert!(
        state
            .entities()
            .npcs()
            .values()
            .all(|n| !n.sequence.is_active())
    );
    assert_eq!(state.entities().local_player().unwrap().tile, tile);
    assert!(
        state
            .apply_operation(SemanticOperation::ResetActorAnimations)
            .unwrap()
            .delta()
            .is_empty()
    );
    let mut replayed = GameState::new();
    for entry in replay {
        replayed.apply_operation(entry.operation().clone()).unwrap();
    }
    assert_eq!(
        state.semantic_fingerprint(),
        replayed.semantic_fingerprint()
    );
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
    assert_eq!(state.loaded_scene(), Some(next));
    assert_eq!(
        state.initial_placement().unwrap().map_anchor.plane,
        Plane::new(1).unwrap()
    );
    recorded.push(ReplayEnvelope::new(plane_update, plane_commit));
    let same_anchor_rebuild = SemanticOperation::RebuildMap(MapAnchor { x: 3224, y: 3216 });
    let same_anchor_commit = state.apply_operation(same_anchor_rebuild.clone()).unwrap();
    assert!(same_anchor_commit.event().is_none());
    assert!(same_anchor_commit.delta().is_empty());
    assert_eq!(state.loaded_scene(), Some(next));
    recorded.push(ReplayEnvelope::new(same_anchor_rebuild, same_anchor_commit));
    let out_of_bounds_plane = SemanticOperation::UpdatePlayers(PlayerInfoUpdate {
        local_movement: Some(PlayerMovement::Teleport {
            delta_x: 200,
            delta_y: 0,
            plane_delta: 1,
        }),
        ..Default::default()
    });
    let out_of_bounds_commit = state.apply_operation(out_of_bounds_plane.clone()).unwrap();
    assert!(out_of_bounds_commit.delta().scene_changed());
    assert_eq!(state.loaded_scene(), None);
    recorded.push(ReplayEnvelope::new(
        out_of_bounds_plane,
        out_of_bounds_commit,
    ));
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
