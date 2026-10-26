use rustclient_game_state::{
    ActorTurning, AuthenticatedPlayer, GameState, InitialPlacement, ReplayEnvelope,
    SceneryOperation, SemanticOperation, TurnDirection,
};
use rustclient_model::*;

fn initial() -> GameState {
    let mut state = GameState::new();
    state
        .apply_operation(SemanticOperation::AuthenticateSession {
            revision: REVISION_240,
            player: AuthenticatedPlayer {
                profile: ServerProfileId::new(1),
                player: PlayerIndex::new(1),
                member: false,
                staff_level: 0,
            },
        })
        .unwrap();
    state
        .apply_operation(SemanticOperation::EstablishInitialPlacement(
            InitialPlacement {
                position: WorldTile::new(3220, 3220, Plane::MIN),
                map_anchor: WorldTile::new(3216, 3216, Plane::MIN),
                area: None,
            },
        ))
        .unwrap();
    state
        .apply_operation(SemanticOperation::UpdatePlayers(PlayerInfoUpdate {
            turn_rate: Some(JauTurnRate::new(32)),
            ..Default::default()
        }))
        .unwrap();
    state
}
fn policy(rate: u16) -> NpcMovementPolicy {
    NpcMovementPolicy {
        path_acceleration: PathAccelerationPolicy::STANDARD,
        turn_rate: JauTurnRate::new(rate),
    }
}
fn spawn(rate: u16, orientation: u16) -> SemanticOperation {
    SemanticOperation::UpdateNpcs(NpcInfoUpdate {
        new_npcs: vec![NewNpc {
            index: NpcIndex::new(4),
            definition_id: NpcDefinitionId::new(12),
            delta_x: 0,
            delta_y: 0,
            orientation,
        }],
        movement_policies: vec![(NpcDefinitionId::new(12), policy(rate))],
        ..Default::default()
    })
}
fn face(angle: u16) -> SemanticOperation {
    SemanticOperation::UpdateNpcs(NpcInfoUpdate {
        attributes: vec![NpcAttributeUpdate {
            index: NpcIndex::new(4),
            changes: vec![NpcProperty::Facing(angle)],
        }],
        ..Default::default()
    })
}
fn cycle(cycles: u32) -> SemanticOperation {
    SemanticOperation::Scenery(SceneryOperation::AdvanceCycles(cycles))
}

#[test]
fn definition_replacement_preserves_rotation_but_zero_rate_spawn_resets_it() {
    let mut state = initial();
    state.apply_operation(spawn(0, 512)).unwrap();
    let npc = &state.entities().npcs()[&NpcIndex::new(4)];
    assert_eq!((npc.orientation, npc.current_orientation), (512, 0));
    state.apply_operation(cycle(2)).unwrap();
    assert_eq!(
        state.entities().npcs()[&NpcIndex::new(4)].turning.cycles(),
        2
    );
    let replace = |rate| {
        SemanticOperation::UpdateNpcs(NpcInfoUpdate {
            attributes: vec![NpcAttributeUpdate {
                index: NpcIndex::new(4),
                changes: vec![NpcProperty::Definition(NpcDefinitionId::new(13))],
            }],
            movement_policies: vec![(NpcDefinitionId::new(13), policy(rate))],
            ..Default::default()
        })
    };
    state.apply_operation(replace(64)).unwrap();
    state.apply_operation(cycle(1)).unwrap();
    assert_eq!(
        state.entities().npcs()[&NpcIndex::new(4)].current_orientation,
        64
    );
    state.apply_operation(replace(0)).unwrap();
    state.apply_operation(cycle(1)).unwrap();
    let npc = &state.entities().npcs()[&NpcIndex::new(4)];
    assert_eq!((npc.current_orientation, npc.turning.cycles()), (64, 4));
    assert_eq!(npc.turning.idle_direction(), None);
}

#[test]
fn committed_turns_replay_partition_and_index_reuse() {
    let mut state = initial();
    let operations = [spawn(64, 0), face(1024), cycle(4), face(2030), cycle(2)];
    let mut recording = Vec::new();
    for operation in operations {
        let commit = state.apply_operation(operation.clone()).unwrap();
        recording.push(ReplayEnvelope::new(operation, commit));
    }
    let npc = &state.entities().npcs()[&NpcIndex::new(4)];
    assert_eq!(npc.current_orientation, 128);
    assert_eq!(npc.turning.cycles(), 6);
    assert_eq!(npc.turning.idle_direction(), Some(TurnDirection::Left));
    let mut replay = initial();
    replay.replay_envelopes(&recording).unwrap();
    assert_eq!(replay.semantic_fingerprint(), state.semantic_fingerprint());
    let mut split = initial();
    split.apply_operation(spawn(64, 0)).unwrap();
    split.apply_operation(face(1024)).unwrap();
    for _ in 0..4 {
        split.apply_operation(cycle(1)).unwrap();
    }
    split.apply_operation(face(2030)).unwrap();
    for _ in 0..2 {
        split.apply_operation(cycle(1)).unwrap();
    }
    assert_eq!(split.semantic_fingerprint(), state.semantic_fingerprint());
    let old_instance = npc.instance;
    state
        .apply_operation(SemanticOperation::UpdateNpcs(NpcInfoUpdate {
            removals: vec![NpcIndex::new(4)],
            ..Default::default()
        }))
        .unwrap();
    state.apply_operation(spawn(32, 768)).unwrap();
    let npc = &state.entities().npcs()[&NpcIndex::new(4)];
    assert_ne!(npc.instance, old_instance);
    assert_eq!(
        npc.turning,
        ActorTurning::new(Some(JauTurnRate::new(32)), 768)
    );
    state
        .apply_operation(SemanticOperation::EndSession)
        .unwrap();
    assert!(state.entities().npcs().is_empty());
}

#[test]
fn forced_approach_turns_after_movement_interpolation_aligns_before_turning() {
    let mut state = initial();
    state
        .apply_operation(SemanticOperation::UpdatePlayers(PlayerInfoUpdate {
            local_exact_move: Some(ExactMove {
                start_delta_x: 0,
                start_delta_y: 0,
                end_delta_x: 1,
                end_delta_y: 0,
                start_delay: 2,
                end_delay: 4,
                direction: 512,
            }),
            ..Default::default()
        }))
        .unwrap();
    state.apply_operation(cycle(1)).unwrap();
    let player = state.entities().local_player().unwrap();
    assert_eq!(
        (
            player.orientation,
            player.current_orientation,
            player.turning.cycles()
        ),
        (512, 32, 1)
    );
    state.apply_operation(cycle(1)).unwrap();
    assert_eq!(
        state.entities().local_player().unwrap().current_orientation,
        64
    );
    state.apply_operation(cycle(1)).unwrap();
    let player = state.entities().local_player().unwrap();
    assert_eq!(
        (
            player.current_orientation,
            player.turning.cycles(),
            player.turning.movement_orientation()
        ),
        (512, 0, 512)
    );
}

#[test]
fn duplicate_policy_failure_is_atomic_and_unresolved_replacement_drops_stale_rate() {
    let mut state = initial();
    state.apply_operation(spawn(64, 0)).unwrap();
    let before = state.semantic_fingerprint();
    let invalid = SemanticOperation::UpdateNpcs(NpcInfoUpdate {
        movement_policies: vec![
            (NpcDefinitionId::new(12), policy(1)),
            (NpcDefinitionId::new(12), policy(2)),
        ],
        ..Default::default()
    });
    assert!(state.apply_operation(invalid).is_err());
    assert_eq!(state.semantic_fingerprint(), before);
    state
        .apply_operation(SemanticOperation::UpdateNpcs(NpcInfoUpdate {
            attributes: vec![NpcAttributeUpdate {
                index: NpcIndex::new(4),
                changes: vec![
                    NpcProperty::Definition(NpcDefinitionId::new(13)),
                    NpcProperty::Facing(512),
                ],
            }],
            ..Default::default()
        }))
        .unwrap();
    state.apply_operation(cycle(1)).unwrap();
    let npc = &state.entities().npcs()[&NpcIndex::new(4)];
    assert_eq!(npc.turning.rate(), None);
    assert_eq!(npc.current_orientation, 0);
}

#[test]
fn player_profile_rate_reaches_local_and_same_batch_remote_entries_with_entity_delta() {
    let mut state = initial();
    let index = PlayerIndex::new(2);
    let update = PlayerInfoUpdate {
        turn_rate: Some(JauTurnRate::new(64)),
        local_facing: Some(512),
        remote: vec![
            RemotePlayerUpdate::Track {
                index,
                grid: PlayerTrackingGrid::new(8192, 256).unwrap(),
                region: WorldTile::new(0, 0, Plane::MIN),
            },
            RemotePlayerUpdate::Enter {
                index,
                offset: TileOffset { x: 3220, y: 3221 },
            },
            RemotePlayerUpdate::Attributes {
                index,
                appearance: None,
                sequence: SequenceChange::Unchanged,
                delay: 0,
                facing: Some(1024),
                path_speed: None,
                exact_move: None,
            },
        ],
        ..Default::default()
    };
    let commit = state
        .apply_operation(SemanticOperation::UpdatePlayers(update))
        .unwrap();
    assert!(commit.delta().entities_changed());
    state.apply_operation(cycle(1)).unwrap();
    for index in [PlayerIndex::new(1), index] {
        let player = &state.entities().players()[&index];
        assert_eq!(player.turning.rate(), Some(JauTurnRate::new(64)));
        assert_eq!(player.current_orientation, 64);
    }
    state
        .apply_operation(SemanticOperation::UpdatePlayers(PlayerInfoUpdate::default()))
        .unwrap();
    assert_eq!(
        state.entities().players()[&index].turning.rate(),
        Some(JauTurnRate::new(64))
    );
}
