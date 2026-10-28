use rustclient_game_state::{
    AuthenticatedPlayer, GameState, InitialPlacement, ReplayEnvelope, SceneryOperation,
    SemanticOperation,
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
}

fn policy() -> SequencePolicy {
    SequencePolicy {
        restart_mode: 1,
        replacement_priority: 5,
        movement_gate_with_backlog: 1,
        movement_gate_without_backlog: 1,
        max_loops: 2,
    }
}
fn timing() -> SequenceTiming {
    SequenceTiming::CachedModel {
        range: Some((5, 8)),
        frame_step: Some(2),
    }
}
fn advance(cycles: u32) -> SemanticOperation {
    SemanticOperation::Scenery(SceneryOperation::AdvanceCycles(cycles))
}
fn player_action(delay: u8, remote: Vec<RemotePlayerUpdate>) -> SemanticOperation {
    SemanticOperation::UpdatePlayers(PlayerInfoUpdate {
        local_sequence: SequenceChange::Set(Some(SequenceId::new(1))),
        local_sequence_delay: delay,
        sequence_policies: vec![(SequenceId::new(1), policy())],
        sequence_timings: vec![(SequenceId::new(1), timing())],
        remote,
        ..Default::default()
    })
}
fn npc() -> NewNpc {
    NewNpc {
        index: NpcIndex::new(4),
        definition_id: NpcDefinitionId::new(12),
        delta_x: 0,
        delta_y: 0,
        orientation: 0,
    }
}
fn slots(state: &GameState) -> [ActorSequenceSlot; 3] {
    [
        state.entities().players()[&PlayerIndex::new(1)].sequence,
        state.entities().players()[&PlayerIndex::new(2)].sequence,
        state.entities().npcs()[&NpcIndex::new(4)].sequence,
    ]
}

#[test]
fn local_remote_and_npc_actions_complete_together_and_replay_without_projection() {
    let mut state = initial();
    let mut recording = Vec::new();
    let mut apply = |state: &mut GameState, operation: SemanticOperation| {
        let commit = state.apply_operation(operation.clone()).unwrap();
        assert!(commit.delta().entities_changed());
        recording.push(ReplayEnvelope::new(operation, commit));
    };
    apply(
        &mut state,
        player_action(
            2,
            vec![
                RemotePlayerUpdate::Track {
                    index: PlayerIndex::new(2),
                    grid: PlayerTrackingGrid::new(8192, 256).unwrap(),
                    region: WorldTile::new(0, 0, Plane::MIN),
                },
                RemotePlayerUpdate::Enter {
                    index: PlayerIndex::new(2),
                    offset: TileOffset { x: 3220, y: 3221 },
                },
                RemotePlayerUpdate::Attributes {
                    index: PlayerIndex::new(2),
                    appearance: None,
                    sequence: SequenceChange::Set(Some(SequenceId::new(1))),
                    delay: 2,
                    facing: None,
                    path_speed: None,
                    exact_move: None,
                },
            ],
        ),
    );
    apply(
        &mut state,
        SemanticOperation::UpdateNpcs(NpcInfoUpdate {
            new_npcs: vec![npc()],
            attributes: vec![NpcAttributeUpdate {
                index: NpcIndex::new(4),
                changes: vec![NpcProperty::Sequence {
                    sequence: Some(SequenceId::new(1)),
                    delay: 2,
                }],
            }],
            sequence_policies: vec![(SequenceId::new(1), policy())],
            sequence_timings: vec![(SequenceId::new(1), timing())],
            ..Default::default()
        }),
    );
    apply(&mut state, advance(2));
    for slot in slots(&state) {
        assert_eq!(
            (slot.frame, slot.cycle, slot.delay, slot.iterations),
            (Some(0), 0, 0, 0)
        );
    }
    apply(&mut state, advance(4));
    for slot in slots(&state) {
        assert_eq!((slot.frame, slot.cycle, slot.iterations), (Some(2), 0, 1));
    }
    apply(&mut state, advance(1));
    let mut replay_prefix = initial();
    replay_prefix
        .replay_envelopes(&recording[..recording.len() - 1])
        .unwrap();
    replay_prefix.apply_operation(advance(1)).unwrap();
    assert_eq!(slots(&state), [ActorSequenceSlot::default(); 3]);
    assert!(
        !state
            .entities()
            .local_player()
            .unwrap()
            .sequence_timing_resolved
    );
    assert!(!state.entities().npcs()[&NpcIndex::new(4)].sequence_timing_resolved);
    assert_eq!(
        state.semantic_fingerprint(),
        replay_prefix.semantic_fingerprint()
    );
    let mut replay = initial();
    replay.replay_envelopes(&recording).unwrap();
    assert_eq!(state.semantic_fingerprint(), replay.semantic_fingerprint());
    let mut split = initial();
    for envelope in &recording {
        match envelope.operation() {
            SemanticOperation::Scenery(SceneryOperation::AdvanceCycles(cycles)) => {
                for _ in 0..cycles {
                    split.apply_operation(advance(1)).unwrap();
                }
            }
            operation => {
                split.apply_operation(operation).unwrap();
            }
        }
    }
    assert_eq!(state.semantic_fingerprint(), split.semantic_fingerprint());
}

#[test]
fn preexisting_walk_holds_action_until_consumption_then_consumes_delay_before_advancing() {
    let mut state = initial();
    state
        .apply_operation(SemanticOperation::UpdatePlayers(PlayerInfoUpdate {
            local_movement: Some(PlayerMovement::Step { direction: 4 }),
            ..Default::default()
        }))
        .unwrap();
    state.apply_operation(player_action(0, vec![])).unwrap();
    state.apply_operation(advance(1)).unwrap();
    let player = state.entities().local_player().unwrap();
    assert_eq!(
        (
            player.sequence.delay,
            player.sequence.frame,
            player.sequence.path_backlog_at_assignment
        ),
        (1, Some(0), 1)
    );
    for _ in 0..64 {
        if state
            .entities()
            .local_player()
            .unwrap()
            .path_queue
            .is_empty()
        {
            break;
        }
        state.apply_operation(advance(1)).unwrap();
    }
    let player = state.entities().local_player().unwrap();
    assert!(player.path_queue.is_empty());
    assert_eq!((player.sequence.delay, player.sequence.frame), (0, Some(0)));
    state.apply_operation(advance(1)).unwrap();
    assert_eq!(
        state.entities().local_player().unwrap().sequence.frame,
        Some(1)
    );
}

#[test]
fn index_reuse_does_not_inherit_resolved_timing_or_playback_and_session_reset_clears_actors() {
    let mut state = initial();
    let spawn = || {
        SemanticOperation::UpdateNpcs(NpcInfoUpdate {
            new_npcs: vec![npc()],
            attributes: vec![NpcAttributeUpdate {
                index: NpcIndex::new(4),
                changes: vec![NpcProperty::Sequence {
                    sequence: Some(SequenceId::new(1)),
                    delay: 0,
                }],
            }],
            sequence_policies: vec![(SequenceId::new(1), policy())],
            sequence_timings: vec![(SequenceId::new(1), timing())],
            ..Default::default()
        })
    };
    state.apply_operation(spawn()).unwrap();
    state.apply_operation(advance(3)).unwrap();
    let old_instance = state.entities().npcs()[&NpcIndex::new(4)].instance;
    state
        .apply_operation(SemanticOperation::UpdateNpcs(NpcInfoUpdate {
            removals: vec![NpcIndex::new(4)],
            new_npcs: vec![npc()],
            attributes: vec![NpcAttributeUpdate {
                index: NpcIndex::new(4),
                changes: vec![NpcProperty::Sequence {
                    sequence: Some(SequenceId::new(1)),
                    delay: 0,
                }],
            }],
            ..Default::default()
        }))
        .unwrap();
    let npc = &state.entities().npcs()[&NpcIndex::new(4)];
    assert_ne!(old_instance, npc.instance);
    assert!(!npc.sequence_timing_resolved);
    assert_eq!(
        (
            npc.sequence.frame,
            npc.sequence.cycle,
            npc.sequence.iterations
        ),
        (Some(0), 0, 0)
    );
    state.apply_operation(advance(20)).unwrap();
    let npc = &state.entities().npcs()[&NpcIndex::new(4)];
    assert_eq!((npc.sequence.frame, npc.sequence.cycle), (Some(0), 20));
    state
        .apply_operation(SemanticOperation::EndSession)
        .unwrap();
    assert!(state.entities().npcs().is_empty() && state.entities().players().is_empty());
}
