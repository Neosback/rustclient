use rustclient_model::{GameTick, REVISION_240, Revision, TickSpan};

use super::*;
use crate::{ReplayEnvelope, SemanticOperation};

const BEGIN_240: SemanticOperation = SemanticOperation::BeginSession {
    revision: REVISION_240,
};

fn run_operations(operations: &[SemanticOperation]) -> (GameState, CommitStreamFingerprint) {
    let mut state = GameState::new();
    let mut commits = CommitStreamFingerprintBuilder::new();

    state
        .apply_operation_batch_with_commits(operations, |_, commit| commits.push(commit))
        .expect("reference semantic operation sequence must be valid");

    (state, commits.finish())
}

#[test]
fn state_fingerprint_v22_has_exact_regression_vectors() {
    let mut state = GameState::new();
    assert_eq!(
        state.semantic_fingerprint().get(),
        14_004_924_912_801_453_135
    );

    state
        .apply_operation(BEGIN_240)
        .expect("inactive state can begin a session");
    state
        .apply_operation(SemanticOperation::AdvanceTick(TickSpan::new(5)))
        .expect("small tick advance cannot overflow");

    assert_eq!(
        state.semantic_fingerprint().get(),
        18_310_109_310_675_007_821
    );
}

#[test]
fn title_panels_and_feedback_participate_in_canonical_state_and_commits() {
    let screens = [
        TitleScreen::Welcome,
        TitleScreen::Credentials(LoginStatus::Prompt),
        TitleScreen::Credentials(LoginStatus::UsernameRequired),
        TitleScreen::Credentials(LoginStatus::PasswordRequired),
        TitleScreen::Credentials(LoginStatus::Offline),
        TitleScreen::Information(TitleInformation::NewUser),
        TitleScreen::Information(TitleInformation::Help),
    ];
    let mut states = std::collections::BTreeSet::new();
    let mut streams = std::collections::BTreeSet::new();
    for screen in screens {
        let mut state = GameState::new();
        let commit = state
            .apply_operation(SemanticOperation::SetTitleScreen(screen))
            .unwrap();
        let mut stream = CommitStreamFingerprintBuilder::new();
        stream.push(commit);
        assert!(states.insert(state.semantic_fingerprint()));
        assert!(streams.insert(stream.finish()));
    }
}

#[test]
fn state_fingerprint_distinguishes_revision_identity() {
    let mut major_only = GameState::new();
    major_only
        .apply_operation(BEGIN_240)
        .expect("inactive state can begin a session");
    major_only
        .apply_operation(SemanticOperation::AdvanceTick(TickSpan::new(5)))
        .expect("small tick advance cannot overflow");

    let mut subrevision = GameState::new();
    subrevision
        .apply_operation(SemanticOperation::BeginSession {
            revision: Revision::with_subrevision(240, 2),
        })
        .expect("inactive state can begin a session");
    subrevision
        .apply_operation(SemanticOperation::AdvanceTick(TickSpan::new(5)))
        .expect("small tick advance cannot overflow");

    assert_ne!(
        major_only.semantic_fingerprint(),
        subrevision.semantic_fingerprint()
    );
    assert_eq!(
        subrevision.semantic_fingerprint().get(),
        4_132_266_560_656_612_196
    );
}

#[test]
fn npc_path_policy_and_catch_up_participate_in_canonical_state() {
    use rustclient_model::{
        ActionMask, ActorSequenceSlot, NpcDefinitionId, NpcIndex, NpcInstanceId,
        PathAccelerationPolicy, Plane, WorldTile,
    };

    fn state_with_policy(policy: PathAccelerationPolicy) -> GameState {
        let mut state = GameState::new();
        state
            .apply_operation(BEGIN_240)
            .expect("inactive state can begin a session");
        let tile = WorldTile::new(3200, 3200, Plane::MIN);
        let mut path_queue = crate::ActorPathQueue::default();
        path_queue.set_acceleration_policy(Some(policy));
        state.entities.npcs.insert(
            NpcIndex::new(1),
            crate::NpcEntity {
                index: NpcIndex::new(1),
                instance: NpcInstanceId::new(1),
                definition_id: NpcDefinitionId::new(1),
                tile,
                path: None,
                path_queue,
                exact_move: None,
                orientation: 0,
                sequence: ActorSequenceSlot::default(),
                sequence_timing_resolved: false,
                name: None,
                actions: ActionMask::ALL,
                locomotion: crate::Locomotion::Idle,
                animation_cycle: 0,
            },
        );
        state.entities.npc_serial = 1;
        state
    }

    let standard = state_with_policy(PathAccelerationPolicy::STANDARD);
    let early = state_with_policy(PathAccelerationPolicy::EARLY);
    assert_ne!(standard.semantic_fingerprint(), early.semantic_fingerprint());

    let mut with_debt = state_with_policy(PathAccelerationPolicy::STANDARD);
    with_debt
        .entities
        .npcs
        .get_mut(&rustclient_model::NpcIndex::new(1))
        .expect("fixture NPC exists")
        .path_queue
        .increment_catch_up();
    assert_ne!(standard.semantic_fingerprint(), with_debt.semantic_fingerprint());
}

#[test]
fn active_sequence_timing_resolution_participates_in_canonical_state() {
    use rustclient_model::{
        ActionMask, ActorSequenceSlot, NpcDefinitionId, NpcIndex, NpcInstanceId,
        PathAccelerationPolicy, Plane, SequenceId, SequencePolicy, SequenceTiming, WorldTile,
    };

    fn state_with_timing(timing: Option<SequenceTiming>) -> GameState {
        let mut state = GameState::new();
        state
            .apply_operation(BEGIN_240)
            .expect("inactive state can begin a session");
        let sequence_id = SequenceId::new(7);
        let policy = SequencePolicy {
            restart_mode: 2,
            replacement_priority: 5,
            movement_gate_with_backlog: 1,
            movement_gate_without_backlog: 1,
            max_loops: 99,
        };
        let mut sequence = ActorSequenceSlot::default();
        sequence.assign_sequence(Some((sequence_id, policy)), 0, 0);
        let mut path_queue = crate::ActorPathQueue::default();
        path_queue.set_acceleration_policy(Some(PathAccelerationPolicy::STANDARD));
        state.entities.npcs.insert(
            NpcIndex::new(1),
            crate::NpcEntity {
                index: NpcIndex::new(1),
                instance: NpcInstanceId::new(1),
                definition_id: NpcDefinitionId::new(1),
                tile: WorldTile::new(3200, 3200, Plane::MIN),
                path: None,
                path_queue,
                exact_move: None,
                orientation: 0,
                sequence,
                sequence_timing_resolved: timing.is_some(),
                name: None,
                actions: ActionMask::ALL,
                locomotion: crate::Locomotion::Idle,
                animation_cycle: 0,
            },
        );
        state.entities.npc_serial = 1;
        if let Some(timing) = timing {
            state.entities.sequence_timings.insert(sequence_id, timing);
        }
        state
    }

    let unresolved = state_with_timing(None);
    let legacy = state_with_timing(Some(SequenceTiming::Legacy {
        frame_delays: vec![2, 3],
        frame_step: Some(2),
    }));
    let cached = state_with_timing(Some(SequenceTiming::CachedModel {
        range: Some((0, 5)),
    }));
    assert_ne!(unresolved.semantic_fingerprint(), legacy.semantic_fingerprint());
    assert_ne!(legacy.semantic_fingerprint(), cached.semantic_fingerprint());
}

#[test]
fn equivalent_observable_state_has_equivalent_fingerprint() {
    let initial = GameState::new().semantic_fingerprint();
    let mut state = GameState::new();

    state
        .apply_operation(BEGIN_240)
        .expect("inactive state can begin a session");
    state
        .apply_operation(SemanticOperation::AdvanceTick(TickSpan::new(9)))
        .expect("small tick advance cannot overflow");
    state
        .apply_operation(SemanticOperation::EndSession)
        .expect("active state can end a session");

    assert_eq!(state.semantic_fingerprint(), initial);
}

#[test]
fn commit_stream_fingerprint_v22_has_exact_regression_vector() {
    let operations = [
        BEGIN_240,
        SemanticOperation::AdvanceTick(TickSpan::new(2)),
        SemanticOperation::AdvanceTick(TickSpan::ZERO),
        SemanticOperation::EndSession,
    ];
    let (_, fingerprint) = run_operations(&operations);

    assert_eq!(fingerprint.get(), 5_559_884_698_701_515_121);
    assert_eq!(
        CommitStreamFingerprintBuilder::new().finish().get(),
        11_814_434_035_812_821_324
    );
}

#[test]
fn commit_stream_fingerprint_is_order_sensitive() {
    let mut state = GameState::new();
    let begin = state
        .apply_operation(BEGIN_240)
        .expect("inactive state can begin a session");
    let advance = state
        .apply_operation(SemanticOperation::AdvanceTick(TickSpan::ONE))
        .expect("small tick advance cannot overflow");

    let mut forward = CommitStreamFingerprintBuilder::new();
    forward.push(begin);
    forward.push(advance);

    let mut reversed = CommitStreamFingerprintBuilder::new();
    reversed.push(advance);
    reversed.push(begin);

    assert_ne!(forward.finish(), reversed.finish());
}

#[test]
fn identical_semantic_runs_have_identical_canonical_fingerprints() {
    let operations = [
        BEGIN_240,
        SemanticOperation::AdvanceTick(TickSpan::new(2)),
        SemanticOperation::AdvanceTick(TickSpan::ZERO),
        SemanticOperation::AdvanceTick(TickSpan::new(3)),
        SemanticOperation::EndSession,
        BEGIN_240,
        SemanticOperation::AdvanceTick(TickSpan::ONE),
    ];

    let (left_state, left_commits) = run_operations(&operations);
    let (right_state, right_commits) = run_operations(&operations);

    assert_eq!(
        left_state.semantic_fingerprint(),
        right_state.semantic_fingerprint()
    );
    assert_eq!(left_commits, right_commits);
    assert_eq!(left_state.current_tick(), Some(GameTick::new(1)));
}

#[test]
fn replay_reconstructs_state_and_commit_stream_fingerprints() {
    let operations = [
        BEGIN_240,
        SemanticOperation::AdvanceTick(TickSpan::new(2)),
        SemanticOperation::AdvanceTick(TickSpan::ZERO),
        SemanticOperation::AdvanceTick(TickSpan::new(3)),
    ];
    let mut recorded_state = GameState::new();
    let mut recorded_stream = CommitStreamFingerprintBuilder::new();
    let mut envelopes = Vec::with_capacity(operations.len());

    for operation in operations {
        let commit = recorded_state
            .apply_operation(operation.clone())
            .expect("reference operation sequence must be valid");
        recorded_stream.push(commit);
        envelopes.push(ReplayEnvelope::new(operation, commit));
    }

    let mut replayed_state = GameState::new();
    let mut replayed_stream = CommitStreamFingerprintBuilder::new();
    replayed_state
        .replay_envelopes_with_commits(&envelopes, |_, commit| {
            replayed_stream.push(commit);
        })
        .expect("recorded semantic sequence must replay exactly");

    assert_eq!(
        recorded_state.semantic_fingerprint(),
        replayed_state.semantic_fingerprint()
    );
    assert_eq!(recorded_stream.finish(), replayed_stream.finish());
}