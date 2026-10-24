//! Shared action-sequence assignment for authoritative player and NPC state.

use rustclient_model::{ActorSequenceSlot, SequenceChange, SequenceId, SequencePolicy};

fn policy(
    policies: &[(SequenceId, SequencePolicy)],
    id: SequenceId,
) -> Option<SequencePolicy> {
    policies
        .iter()
        .find_map(|(candidate, policy)| (*candidate == id).then_some(*policy))
}

/// Apply a player sequence field, using resolved definition policy when application supplied it.
///
/// Empty policy tables remain supported for direct semantic fixtures and unfinished adapters; that
/// path deliberately uses the transitional unpolicy setter. The exact path captures zero backlog
/// until the bounded actor path queue becomes authoritative in the movement-scheduler checkpoint.
pub(crate) fn apply_change(
    slot: &mut ActorSequenceSlot,
    change: SequenceChange,
    delay: i32,
    policies: &[(SequenceId, SequencePolicy)],
) -> bool {
    let previous = *slot;
    match change {
        SequenceChange::Unchanged => {}
        SequenceChange::Set(None) => slot.clear(),
        SequenceChange::Set(Some(id)) => {
            if let Some(policy) = policy(policies, id) {
                slot.assign_sequence(Some((id, policy)), delay, 0);
            } else {
                slot.set_sequence(Some(id), delay);
            }
        }
    }
    *slot != previous
}

/// Apply an NPC sequence property, sharing the exact same arbitration as players.
pub(crate) fn apply_sequence(
    slot: &mut ActorSequenceSlot,
    sequence: Option<SequenceId>,
    delay: i32,
    policies: &[(SequenceId, SequencePolicy)],
) -> bool {
    let previous = *slot;
    match sequence {
        None => slot.clear(),
        Some(id) => {
            if let Some(policy) = policy(policies, id) {
                slot.assign_sequence(Some((id, policy)), delay, 0);
            } else {
                slot.set_sequence(Some(id), delay);
            }
        }
    }
    *slot != previous
}

#[cfg(test)]
mod tests {
    use super::{apply_change, apply_sequence};
    use rustclient_model::{ActorSequenceSlot, SequenceChange, SequenceId, SequencePolicy};

    const fn policy(restart_mode: u8, priority: u8) -> SequencePolicy {
        SequencePolicy {
            restart_mode,
            replacement_priority: priority,
            movement_gate_with_backlog: 0,
            movement_gate_without_backlog: 0,
            max_loops: 99,
        }
    }

    #[test]
    fn resolved_player_assignment_uses_priority() {
        let high = SequenceId::new(1);
        let low = SequenceId::new(2);
        let policies = [(high, policy(2, 8)), (low, policy(2, 3))];
        let mut slot = ActorSequenceSlot::default();
        assert!(apply_change(
            &mut slot,
            SequenceChange::Set(Some(high)),
            0,
            &policies
        ));
        assert!(!apply_change(
            &mut slot,
            SequenceChange::Set(Some(low)),
            0,
            &policies
        ));
        assert_eq!(slot.sequence, Some(high));
    }

    #[test]
    fn resolved_npc_same_id_restart_reapplies_delay() {
        let id = SequenceId::new(3);
        let policies = [(id, policy(1, 5))];
        let mut slot = ActorSequenceSlot::default();
        assert!(apply_sequence(&mut slot, Some(id), 2, &policies));
        slot.cycle = 7;
        slot.frame = Some(4);
        assert!(apply_sequence(&mut slot, Some(id), 6, &policies));
        assert_eq!(slot.cycle, 0);
        assert_eq!(slot.frame, Some(0));
        assert_eq!(slot.delay, 6);
    }
}
