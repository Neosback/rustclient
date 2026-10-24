//! Shared action-sequence assignment and movement gating for authoritative actor state.

use crate::{ActorPathEntry, ActorPathQueue};
use rustclient_model::{
    ActorSequenceSlot, SequenceChange, SequenceId, SequencePolicy, SequenceTiming,
};

fn policy(
    policies: &[(SequenceId, SequencePolicy)],
    id: SequenceId,
) -> Option<SequencePolicy> {
    policies
        .iter()
        .find_map(|(candidate, policy)| (*candidate == id).then_some(*policy))
}

fn has_timing(timings: &[(SequenceId, SequenceTiming)], id: SequenceId) -> bool {
    timings.iter().any(|(candidate, _)| *candidate == id)
}

/// Result of attempting to consume the oldest authoritative actor path request.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub(crate) enum PathConsumption {
    Empty,
    Blocked,
    Consumed(ActorPathEntry),
}

/// Whether the current action sequence blocks normal queued-path progress this cycle.
///
/// Revision 240 applies these gates only while an action is active with zero explicit delay. The
/// opcode-9 policy is used while the pre-action path remainder is positive; opcode 10 takes over
/// once that remainder reaches zero. Legacy/unresolved slots have no policy and therefore cannot
/// make an exact blocking claim.
#[must_use]
pub(crate) fn movement_blocked(slot: ActorSequenceSlot) -> bool {
    if slot.sequence.is_none() || slot.delay != 0 {
        return false;
    }
    let Some(policy) = slot.policy else {
        return false;
    };
    if slot.path_backlog_at_assignment > 0 {
        policy.movement_gate_with_backlog == 0
    } else {
        policy.movement_gate_without_backlog == 0
    }
}

/// Consume one already-reached oldest path request if the active action policy allows progress.
///
/// This is deliberately the authoritative *consumption* transition, not a timer that assumes one
/// tile is reached per client cycle. The fine-position scheduler calls this only after it actually
/// reaches (or stock-snaps to) the oldest target. On consumption, revision-240 `field1476`
/// semantics decrement the pre-action remainder while it is still positive.
pub(crate) fn consume_oldest_path_entry(
    queue: &mut ActorPathQueue,
    slot: &mut ActorSequenceSlot,
) -> PathConsumption {
    if queue.is_empty() {
        return PathConsumption::Empty;
    }
    if movement_blocked(*slot) {
        return PathConsumption::Blocked;
    }
    let entry = queue
        .pop_oldest()
        .expect("non-empty actor path has an oldest entry");
    if slot.path_backlog_at_assignment > 0 {
        slot.path_backlog_at_assignment -= 1;
    }
    PathConsumption::Consumed(entry)
}

/// Apply a player sequence field, using resolved definition policy/timing when application supplied
/// them. Timing resolution belongs to the accepted active action, not merely to a sequence id that
/// happened to exist in the shared timing registry earlier in the session.
pub(crate) fn apply_change(
    slot: &mut ActorSequenceSlot,
    timing_resolved: &mut bool,
    change: SequenceChange,
    delay: i32,
    path_backlog: u32,
    policies: &[(SequenceId, SequencePolicy)],
    timings: &[(SequenceId, SequenceTiming)],
) -> bool {
    let previous = *slot;
    let previous_timing = *timing_resolved;
    match change {
        SequenceChange::Unchanged => {}
        SequenceChange::Set(None) => {
            slot.clear();
            *timing_resolved = false;
        }
        SequenceChange::Set(Some(id)) => {
            let before_id = slot.sequence;
            if let Some(policy) = policy(policies, id) {
                slot.assign_sequence(Some((id, policy)), delay, path_backlog);
            } else {
                slot.set_sequence(Some(id), delay);
            }
            if slot.sequence == Some(id) {
                if has_timing(timings, id) {
                    *timing_resolved = true;
                } else if before_id != Some(id) {
                    *timing_resolved = false;
                }
            }
        }
    }
    *slot != previous || *timing_resolved != previous_timing
}

/// Apply an NPC sequence property, sharing the exact same arbitration as players.
pub(crate) fn apply_sequence(
    slot: &mut ActorSequenceSlot,
    timing_resolved: &mut bool,
    sequence: Option<SequenceId>,
    delay: i32,
    path_backlog: u32,
    policies: &[(SequenceId, SequencePolicy)],
    timings: &[(SequenceId, SequenceTiming)],
) -> bool {
    let previous = *slot;
    let previous_timing = *timing_resolved;
    match sequence {
        None => {
            slot.clear();
            *timing_resolved = false;
        }
        Some(id) => {
            let before_id = slot.sequence;
            if let Some(policy) = policy(policies, id) {
                slot.assign_sequence(Some((id, policy)), delay, path_backlog);
            } else {
                slot.set_sequence(Some(id), delay);
            }
            if slot.sequence == Some(id) {
                if has_timing(timings, id) {
                    *timing_resolved = true;
                } else if before_id != Some(id) {
                    *timing_resolved = false;
                }
            }
        }
    }
    *slot != previous || *timing_resolved != previous_timing
}

#[cfg(test)]
mod tests {
    use super::{
        PathConsumption, apply_change, apply_sequence, consume_oldest_path_entry, movement_blocked,
    };
    use crate::ActorPathQueue;
    use rustclient_model::{
        ActorSequenceSlot, MoveSpeed, Plane, SequenceChange, SequenceId, SequencePolicy,
        SequenceTiming, WorldTile,
    };

    const fn policy(restart_mode: u8, priority: u8) -> SequencePolicy {
        SequencePolicy {
            restart_mode,
            replacement_priority: priority,
            movement_gate_with_backlog: 0,
            movement_gate_without_backlog: 0,
            max_loops: 99,
        }
    }

    const fn movement_policy(with_backlog: u8, without_backlog: u8) -> SequencePolicy {
        SequencePolicy {
            restart_mode: 2,
            replacement_priority: 5,
            movement_gate_with_backlog: with_backlog,
            movement_gate_without_backlog: without_backlog,
            max_loops: 99,
        }
    }

    fn tile(x: i32) -> WorldTile {
        WorldTile::new(x, 0, Plane::MIN)
    }

    #[test]
    fn resolved_player_assignment_uses_priority_and_captures_backlog() {
        let high = SequenceId::new(1);
        let low = SequenceId::new(2);
        let policies = [(high, policy(2, 8)), (low, policy(2, 3))];
        let mut slot = ActorSequenceSlot::default();
        let mut timing_resolved = false;
        assert!(apply_change(
            &mut slot,
            &mut timing_resolved,
            SequenceChange::Set(Some(high)),
            0,
            4,
            &policies,
            &[]
        ));
        assert_eq!(slot.path_backlog_at_assignment, 4);
        assert!(!apply_change(
            &mut slot,
            &mut timing_resolved,
            SequenceChange::Set(Some(low)),
            0,
            7,
            &policies,
            &[]
        ));
        assert_eq!(slot.sequence, Some(high));
        assert_eq!(slot.path_backlog_at_assignment, 4);
    }

    #[test]
    fn accepted_assignment_binds_supplied_timing_but_rejected_replacement_preserves_it() {
        let high = SequenceId::new(1);
        let low = SequenceId::new(2);
        let policies = [(high, policy(2, 8)), (low, policy(2, 3))];
        let timings = [(
            high,
            SequenceTiming::Legacy {
                frame_delays: vec![2],
                frame_step: None,
            },
        )];
        let mut slot = ActorSequenceSlot::default();
        let mut timing_resolved = false;
        assert!(apply_change(
            &mut slot,
            &mut timing_resolved,
            SequenceChange::Set(Some(high)),
            0,
            0,
            &policies,
            &timings,
        ));
        assert!(timing_resolved);
        assert!(!apply_change(
            &mut slot,
            &mut timing_resolved,
            SequenceChange::Set(Some(low)),
            0,
            0,
            &policies,
            &[],
        ));
        assert!(timing_resolved);
    }

    #[test]
    fn resolved_npc_same_id_restart_reapplies_delay_without_replacing_backlog() {
        let id = SequenceId::new(3);
        let policies = [(id, policy(1, 5))];
        let mut slot = ActorSequenceSlot::default();
        let mut timing_resolved = false;
        assert!(apply_sequence(
            &mut slot,
            &mut timing_resolved,
            Some(id),
            2,
            5,
            &policies,
            &[]
        ));
        slot.cycle = 7;
        slot.frame = Some(4);
        assert!(apply_sequence(
            &mut slot,
            &mut timing_resolved,
            Some(id),
            6,
            1,
            &policies,
            &[]
        ));
        assert_eq!(slot.cycle, 0);
        assert_eq!(slot.frame, Some(0));
        assert_eq!(slot.delay, 6);
        assert_eq!(slot.path_backlog_at_assignment, 5);
    }

    #[test]
    fn backlog_gate_zero_blocks_without_consuming_or_decrementing() {
        let mut queue = ActorPathQueue::default();
        queue.enqueue(tile(1), MoveSpeed::Walk);
        let mut slot = ActorSequenceSlot::default();
        slot.assign_sequence(
            Some((SequenceId::new(10), movement_policy(0, 1))),
            0,
            queue.backlog(),
        );

        assert!(movement_blocked(slot));
        assert_eq!(
            consume_oldest_path_entry(&mut queue, &mut slot),
            PathConsumption::Blocked
        );
        assert_eq!(queue.oldest().unwrap().target, tile(1));
        assert_eq!(slot.path_backlog_at_assignment, 1);
    }

    #[test]
    fn consuming_pre_action_entry_decrements_remainder_then_switches_gate() {
        let mut queue = ActorPathQueue::default();
        queue.enqueue(tile(1), MoveSpeed::Walk);
        queue.enqueue(tile(2), MoveSpeed::Walk);
        let mut slot = ActorSequenceSlot::default();
        slot.assign_sequence(
            Some((SequenceId::new(10), movement_policy(1, 0))),
            0,
            1,
        );

        assert_eq!(
            consume_oldest_path_entry(&mut queue, &mut slot),
            PathConsumption::Consumed(crate::ActorPathEntry {
                target: tile(1),
                speed: MoveSpeed::Walk,
            })
        );
        assert_eq!(slot.path_backlog_at_assignment, 0);
        assert_eq!(queue.oldest().unwrap().target, tile(2));
        assert!(movement_blocked(slot));
        assert_eq!(
            consume_oldest_path_entry(&mut queue, &mut slot),
            PathConsumption::Blocked
        );
    }

    #[test]
    fn explicit_action_delay_defers_gate_until_delay_reaches_zero() {
        let mut queue = ActorPathQueue::default();
        queue.enqueue(tile(1), MoveSpeed::Run);
        let mut slot = ActorSequenceSlot::default();
        slot.assign_sequence(
            Some((SequenceId::new(10), movement_policy(0, 0))),
            2,
            1,
        );

        assert!(!movement_blocked(slot));
        assert!(matches!(
            consume_oldest_path_entry(&mut queue, &mut slot),
            PathConsumption::Consumed(_)
        ));
        assert_eq!(slot.path_backlog_at_assignment, 0);
    }

    #[test]
    fn unresolved_or_absent_action_does_not_invent_a_gate() {
        let mut slot = ActorSequenceSlot::default();
        assert!(!movement_blocked(slot));
        slot.set_sequence(Some(SequenceId::new(10)), 0);
        assert!(!movement_blocked(slot));
    }
}
