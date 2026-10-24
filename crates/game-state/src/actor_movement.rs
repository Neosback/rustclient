//! Authoritative per-client-cycle actor fine-position scheduling.
//!
//! This checkpoint moves ordinary queued player movement into `GameState`. Forced/exact movement
//! still has a legacy action-frame gate that is deliberately left in the scene projection until
//! that remaining revision-240 dependency is implemented here too.

use crate::{ActorFinePosition, ActorPathQueue, WorldEntitiesState};
use rustclient_model::{ActorSequenceSlot, MoveSpeed, WorldTile};

const SNAP_FINE_UNITS: i64 = 288;

#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub(crate) enum ActorMovementCycle {
    Idle,
    Blocked,
    Moving,
    Consumed,
    Snapped,
}

/// Advance all visible players by one authoritative client cycle.
///
/// Movement is evaluated before the action-sequence delay is decremented for the same cycle. This
/// preserves the revision-240 predicate that an action with a non-zero explicit delay does not yet
/// block normal queued movement.
pub(crate) fn advance_players(entities: &mut WorldEntitiesState) {
    for player in entities.players.values_mut() {
        if player.exact_move.is_some() {
            // Forced movement owns position while active. Keeping ordinary queue progression frozen
            // here avoids conflicting with its still-unported legacy frame-boundary gate.
            continue;
        }
        advance_actor_cycle(
            &mut player.path_queue,
            &mut player.sequence,
            player.tile,
        );
    }
}

fn advance_actor_cycle(
    path: &mut ActorPathQueue,
    sequence: &mut ActorSequenceSlot,
    fallback_tile: WorldTile,
) -> ActorMovementCycle {
    if path.is_empty() {
        path.ensure_position(fallback_tile);
        return ActorMovementCycle::Idle;
    }
    if crate::sequence_scheduler::movement_blocked(*sequence) {
        path.ensure_position(fallback_tile);
        return ActorMovementCycle::Blocked;
    }

    path.ensure_position(fallback_tile);
    let entry = path.oldest().expect("non-empty actor path has oldest entry");
    let target = ActorFinePosition::from_tile(entry.target);
    let mut position = path.fine_position(fallback_tile);
    let gap = (target.x - position.x)
        .abs()
        .max((target.y - position.y).abs());
    if gap > SNAP_FINE_UNITS {
        path.set_fine_position(target);
        debug_assert!(matches!(
            crate::sequence_scheduler::consume_oldest_path_entry(path, sequence),
            crate::sequence_scheduler::PathConsumption::Consumed(_)
        ));
        return ActorMovementCycle::Snapped;
    }

    // This is the stock normal acceleration branch without turn-slowing, the blocked-cycle
    // catch-up counter, or the alternate NPC-definition thresholds. Those are explicit follow-ups.
    let mut step = if path.len() > 3 {
        8_i64
    } else if path.len() > 2 {
        6_i64
    } else {
        4_i64
    };
    match entry.speed {
        MoveSpeed::Run => step *= 2,
        MoveSpeed::Crawl => step /= 2,
        MoveSpeed::Walk | MoveSpeed::Stationary => {}
    }
    step = step.max(1);

    position.x += (target.x - position.x).clamp(-step, step);
    position.y += (target.y - position.y).clamp(-step, step);
    path.set_fine_position(position);

    if position == target {
        debug_assert!(matches!(
            crate::sequence_scheduler::consume_oldest_path_entry(path, sequence),
            crate::sequence_scheduler::PathConsumption::Consumed(_)
        ));
        ActorMovementCycle::Consumed
    } else {
        ActorMovementCycle::Moving
    }
}

#[cfg(test)]
mod tests {
    use super::{ActorMovementCycle, advance_actor_cycle};
    use crate::{ActorFinePosition, ActorPathQueue};
    use rustclient_model::{
        ActorSequenceSlot, MoveSpeed, Plane, SequenceId, SequencePolicy, WorldTile,
    };

    fn tile(x: i32, y: i32) -> WorldTile {
        WorldTile::new(x, y, Plane::MIN)
    }

    fn policy(with_backlog: u8, without_backlog: u8) -> SequencePolicy {
        SequencePolicy {
            restart_mode: 2,
            replacement_priority: 5,
            movement_gate_with_backlog: with_backlog,
            movement_gate_without_backlog: without_backlog,
            max_loops: 99,
        }
    }

    #[test]
    fn walk_advances_four_fine_units_without_consuming_early() {
        let mut path = ActorPathQueue::default();
        path.ensure_position(tile(0, 0));
        path.enqueue(tile(1, 0), MoveSpeed::Walk);
        let mut sequence = ActorSequenceSlot::default();
        assert_eq!(
            advance_actor_cycle(&mut path, &mut sequence, tile(1, 0)),
            ActorMovementCycle::Moving
        );
        assert_eq!(
            path.fine_position(tile(99, 99)),
            ActorFinePosition { x: 4, y: 0 }
        );
        assert_eq!(path.len(), 1);
    }

    #[test]
    fn large_gap_snaps_and_consumes_at_the_authoritative_boundary() {
        let mut path = ActorPathQueue::default();
        path.ensure_position(tile(0, 0));
        path.enqueue(tile(4, 0), MoveSpeed::Walk);
        let mut sequence = ActorSequenceSlot::default();
        assert_eq!(
            advance_actor_cycle(&mut path, &mut sequence, tile(4, 0)),
            ActorMovementCycle::Snapped
        );
        assert_eq!(
            path.fine_position(tile(0, 0)),
            ActorFinePosition::from_tile(tile(4, 0))
        );
        assert!(path.is_empty());
    }

    #[test]
    fn action_gate_blocks_position_and_queue_progress() {
        let mut path = ActorPathQueue::default();
        path.ensure_position(tile(0, 0));
        path.enqueue(tile(1, 0), MoveSpeed::Walk);
        let mut sequence = ActorSequenceSlot::default();
        sequence.assign_sequence(Some((SequenceId::new(10), policy(0, 1))), 0, 1);
        assert_eq!(
            advance_actor_cycle(&mut path, &mut sequence, tile(1, 0)),
            ActorMovementCycle::Blocked
        );
        assert_eq!(
            path.fine_position(tile(9, 9)),
            ActorFinePosition::from_tile(tile(0, 0))
        );
        assert_eq!(sequence.path_backlog_at_assignment, 1);
    }

    #[test]
    fn run_scales_the_normal_step() {
        let mut path = ActorPathQueue::default();
        path.ensure_position(tile(0, 0));
        path.enqueue(tile(1, 0), MoveSpeed::Run);
        let mut sequence = ActorSequenceSlot::default();
        advance_actor_cycle(&mut path, &mut sequence, tile(1, 0));
        assert_eq!(path.fine_position(tile(0, 0)).x, 8);
    }

    #[test]
    fn backlog_acceleration_uses_stock_normal_thresholds() {
        let mut path = ActorPathQueue::default();
        path.ensure_position(tile(0, 0));
        for x in 1..=4 {
            path.enqueue(tile(x, 0), MoveSpeed::Walk);
        }
        let mut sequence = ActorSequenceSlot::default();
        advance_actor_cycle(&mut path, &mut sequence, tile(4, 0));
        assert_eq!(path.fine_position(tile(0, 0)).x, 8);
    }
}
