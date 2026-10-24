//! Authoritative per-client-cycle actor fine-position scheduling.
//!
//! This checkpoint moves normal queued player movement and forced-movement coordinates into
//! `GameState`. Directional pose selection, turn-rate slowdown, catch-up counters and the alternate
//! NPC acceleration mode remain separate follow-up work.

use crate::{ActorFinePosition, ActorPathQueue, ExactMovement, WorldEntitiesState};
use rustclient_model::{ActorSequenceSlot, ClientCycle, MoveSpeed, WorldTile};

const SNAP_FINE_UNITS: i64 = 288;

#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub(crate) enum ActorMovementCycle {
    Idle,
    Blocked,
    Moving,
    Consumed,
    Snapped,
    Exact,
}

/// Advance all visible players by one authoritative client cycle.
///
/// Movement is evaluated before the sequence delay is decremented for the same cycle. This matches
/// the revision-240 gate predicate: an action whose explicit delay is still non-zero cannot block
/// queued movement yet.
pub(crate) fn advance_players(entities: &mut WorldEntitiesState, cycle: ClientCycle) {
    for player in entities.players.values_mut() {
        advance_actor_cycle(
            &mut player.path_queue,
            &mut player.sequence,
            player.exact_move,
            cycle,
            player.tile,
        );
    }
}

fn advance_actor_cycle(
    path: &mut ActorPathQueue,
    sequence: &mut ActorSequenceSlot,
    exact: Option<ExactMovement>,
    cycle: ClientCycle,
    fallback_tile: WorldTile,
) -> ActorMovementCycle {
    if let Some(exact) = exact {
        if cycle <= exact.ends_at {
            advance_exact(path, exact, cycle, fallback_tile);
            return ActorMovementCycle::Exact;
        }
        // A zero-duration exact move can be authored for the packet's current cycle and therefore
        // finish before the next `AdvanceCycles` operation. Preserve its terminal position instead
        // of letting a lazily initialized cursor start from the confirmed endpoint.
        path.set_fine_position(ActorFinePosition::from_tile(exact.end));
    }

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

fn advance_exact(
    path: &mut ActorPathQueue,
    exact: ExactMovement,
    cycle: ClientCycle,
    fallback_tile: WorldTile,
) {
    path.ensure_position(fallback_tile);
    let mut position = path.fine_position(fallback_tile);
    let start = ActorFinePosition::from_tile(exact.start);
    let end = ActorFinePosition::from_tile(exact.end);

    if cycle <= exact.starts_at {
        let remaining = exact.starts_at.get().saturating_sub(cycle.get()).max(1);
        let remaining = i64::try_from(remaining).unwrap_or(i64::MAX);
        position.x += (start.x - position.x) / remaining;
        position.y += (start.y - position.y) / remaining;
    } else if exact.starts_at == exact.ends_at {
        position = start;
    } else if cycle >= exact.ends_at {
        position = end;
    } else {
        let duration = i128::from(exact.ends_at.get() - exact.starts_at.get());
        let elapsed = i128::from(cycle.get() - exact.starts_at.get());
        let interpolate = |a: i64, b: i64| {
            i64::try_from(
                (i128::from(a) * (duration - elapsed) + i128::from(b) * elapsed) / duration,
            )
            .expect("convex fine-coordinate interpolation stays in i64 range")
        };
        position.x = interpolate(start.x, end.x);
        position.y = interpolate(start.y, end.y);
    }
    path.set_fine_position(position);
}

#[cfg(test)]
mod tests {
    use super::{ActorMovementCycle, advance_actor_cycle};
    use crate::{ActorFinePosition, ActorPathQueue, ExactMovement};
    use rustclient_model::{
        ActorSequenceSlot, ClientCycle, MoveSpeed, Plane, SequenceId, SequencePolicy, WorldTile,
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
            advance_actor_cycle(
                &mut path,
                &mut sequence,
                None,
                ClientCycle::new(1),
                tile(1, 0),
            ),
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
            advance_actor_cycle(
                &mut path,
                &mut sequence,
                None,
                ClientCycle::new(1),
                tile(4, 0),
            ),
            ActorMovementCycle::Snapped
        );
        assert_eq!(path.fine_position(tile(0, 0)), ActorFinePosition::from_tile(tile(4, 0)));
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
            advance_actor_cycle(
                &mut path,
                &mut sequence,
                None,
                ClientCycle::new(1),
                tile(1, 0),
            ),
            ActorMovementCycle::Blocked
        );
        assert_eq!(path.fine_position(tile(9, 9)), ActorFinePosition::from_tile(tile(0, 0)));
        assert_eq!(sequence.path_backlog_at_assignment, 1);
    }

    #[test]
    fn run_scales_the_normal_step() {
        let mut path = ActorPathQueue::default();
        path.ensure_position(tile(0, 0));
        path.enqueue(tile(1, 0), MoveSpeed::Run);
        let mut sequence = ActorSequenceSlot::default();
        advance_actor_cycle(
            &mut path,
            &mut sequence,
            None,
            ClientCycle::new(1),
            tile(1, 0),
        );
        assert_eq!(path.fine_position(tile(0, 0)).x, 8);
    }

    #[test]
    fn exact_move_interpolates_from_the_authoritative_cursor() {
        let mut path = ActorPathQueue::default();
        path.ensure_position(tile(0, 0));
        let exact = ExactMovement {
            start: tile(1, 0),
            end: tile(3, 0),
            starts_at: ClientCycle::new(2),
            ends_at: ClientCycle::new(4),
            direction: 0,
        };
        let mut sequence = ActorSequenceSlot::default();
        assert_eq!(
            advance_actor_cycle(
                &mut path,
                &mut sequence,
                Some(exact),
                ClientCycle::new(1),
                tile(0, 0),
            ),
            ActorMovementCycle::Exact
        );
        assert_eq!(path.fine_position(tile(0, 0)).x, 128);
    }
}
