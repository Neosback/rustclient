//! Authoritative per-client-cycle actor fine-position scheduling.
//!
//! Ordinary queued player and resolved-NPC movement lives in `GameState`. Forced/exact movement
//! still has a legacy action-frame gate that is deliberately left in the scene projection until
//! that remaining revision-240 dependency is implemented here too.

use crate::{ActorFinePosition, ActorPathQueue, WorldEntitiesState};
use rustclient_model::{ActorSequenceSlot, MoveSpeed, PathAccelerationPolicy, WorldTile};

const SNAP_FINE_UNITS: i64 = 288;

#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub(crate) enum ActorMovementCycle {
    Idle,
    Blocked,
    Moving,
    Consumed,
    Snapped,
}

/// Advance all ordinary visible actors by one authoritative client cycle.
///
/// The historical function name is retained to keep the existing entity-cycle call site small;
/// this now advances players plus NPCs whose definition-derived acceleration policy was resolved.
/// Movement is evaluated before the action-sequence delay is decremented for the same cycle.
pub(crate) fn advance_players(entities: &mut WorldEntitiesState) {
    for player in entities.players.values_mut() {
        if player.exact_move.is_some() {
            // Exact-movement phase timing is not authoritative yet. `exact_move` can remain
            // attached after its active interval, so resetting catch-up from presence alone would
            // over-reset field1452. The forced-movement scheduler will own that exact reset point.
            continue;
        }
        advance_actor_cycle(
            &mut player.path_queue,
            &mut player.sequence,
            player.tile,
            PathAccelerationPolicy::STANDARD,
        );
    }

    for npc in entities.npcs.values_mut() {
        if npc.exact_move.is_some() {
            // See the player branch above: preserve debt until exact client-cycle timing is owned
            // by GameState rather than guessing from a potentially stale trajectory marker.
            continue;
        }
        if npc.path_queue.is_empty() {
            // Empty-queue reset is definition-independent and must still happen for unresolved
            // direct fixtures whose acceleration policy is intentionally absent.
            npc.path_queue.ensure_position(npc.tile);
            npc.path_queue.reset_catch_up();
            continue;
        }
        let Some(policy) = npc.path_queue.acceleration_policy() else {
            // Direct semantic fixtures may omit cache enrichment. Do not invent revision policy.
            continue;
        };
        advance_actor_cycle(&mut npc.path_queue, &mut npc.sequence, npc.tile, policy);
    }
}

fn consume_reached_entry(path: &mut ActorPathQueue, sequence: &mut ActorSequenceSlot) {
    let consumption = crate::sequence_scheduler::consume_oldest_path_entry(path, sequence);
    debug_assert!(matches!(
        consumption,
        crate::sequence_scheduler::PathConsumption::Consumed(_)
    ));
}

fn advance_actor_cycle(
    path: &mut ActorPathQueue,
    sequence: &mut ActorSequenceSlot,
    fallback_tile: WorldTile,
    acceleration: PathAccelerationPolicy,
) -> ActorMovementCycle {
    if path.is_empty() {
        path.ensure_position(fallback_tile);
        path.reset_catch_up();
        return ActorMovementCycle::Idle;
    }
    if crate::sequence_scheduler::movement_blocked(*sequence) {
        path.ensure_position(fallback_tile);
        path.increment_catch_up();
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
        // Stock method3284 snaps and consumes without spending field1452 catch-up debt.
        path.set_fine_position(target);
        consume_reached_entry(path, sequence);
        return ActorMovementCycle::Snapped;
    }

    let backlog = path.len();
    let mut step = if backlog > acceleration.step_eight_after {
        8_i64
    } else if backlog > acceleration.step_six_after {
        6_i64
    } else {
        4_i64
    };
    // Revision 240 checks catch-up after normal/definition backlog acceleration (and, in the full
    // client, after turning slowdown). Positive debt with more than one queued target forces base
    // step eight and spends exactly one cycle before traversal scaling.
    if backlog > 1 && path.catch_up_cycles() > 0 {
        step = 8;
        let spent = path.spend_catch_up();
        debug_assert!(spent);
    }
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
        consume_reached_entry(path, sequence);
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
        ActorSequenceSlot, MoveSpeed, PathAccelerationPolicy, Plane, SequenceId, SequencePolicy,
        WorldTile,
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
                tile(1, 0),
                PathAccelerationPolicy::STANDARD,
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
                tile(4, 0),
                PathAccelerationPolicy::STANDARD,
            ),
            ActorMovementCycle::Snapped
        );
        assert_eq!(
            path.fine_position(tile(0, 0)),
            ActorFinePosition::from_tile(tile(4, 0))
        );
        assert!(path.is_empty());
    }

    #[test]
    fn action_gate_blocks_position_and_accrues_catch_up_debt() {
        let mut path = ActorPathQueue::default();
        path.ensure_position(tile(0, 0));
        path.enqueue(tile(1, 0), MoveSpeed::Walk);
        let mut sequence = ActorSequenceSlot::default();
        sequence.assign_sequence(Some((SequenceId::new(10), policy(0, 1))), 0, 1);
        assert_eq!(
            advance_actor_cycle(
                &mut path,
                &mut sequence,
                tile(1, 0),
                PathAccelerationPolicy::STANDARD,
            ),
            ActorMovementCycle::Blocked
        );
        assert_eq!(
            path.fine_position(tile(9, 9)),
            ActorFinePosition::from_tile(tile(0, 0))
        );
        assert_eq!(sequence.path_backlog_at_assignment, 1);
        assert_eq!(path.catch_up_cycles(), 1);
    }

    #[test]
    fn positive_catch_up_forces_step_eight_and_spends_one_cycle() {
        let mut path = ActorPathQueue::default();
        path.ensure_position(tile(0, 0));
        path.enqueue(tile(1, 0), MoveSpeed::Walk);
        path.enqueue(tile(2, 0), MoveSpeed::Walk);
        let mut sequence = ActorSequenceSlot::default();
        sequence.assign_sequence(Some((SequenceId::new(10), policy(0, 1))), 0, 2);
        advance_actor_cycle(
            &mut path,
            &mut sequence,
            tile(2, 0),
            PathAccelerationPolicy::STANDARD,
        );
        advance_actor_cycle(
            &mut path,
            &mut sequence,
            tile(2, 0),
            PathAccelerationPolicy::STANDARD,
        );
        assert_eq!(path.catch_up_cycles(), 2);

        // Non-zero sequence delay bypasses the action path gate for this cycle.
        sequence.delay = 1;
        assert_eq!(
            advance_actor_cycle(
                &mut path,
                &mut sequence,
                tile(2, 0),
                PathAccelerationPolicy::STANDARD,
            ),
            ActorMovementCycle::Moving
        );
        assert_eq!(path.fine_position(tile(0, 0)).x, 8);
        assert_eq!(path.catch_up_cycles(), 1);
    }

    #[test]
    fn snap_does_not_spend_catch_up_and_empty_next_cycle_resets_it() {
        let mut path = ActorPathQueue::default();
        path.ensure_position(tile(0, 0));
        path.enqueue(tile(4, 0), MoveSpeed::Walk);
        let mut sequence = ActorSequenceSlot::default();
        sequence.assign_sequence(Some((SequenceId::new(10), policy(0, 1))), 0, 1);
        advance_actor_cycle(
            &mut path,
            &mut sequence,
            tile(4, 0),
            PathAccelerationPolicy::STANDARD,
        );
        assert_eq!(path.catch_up_cycles(), 1);

        sequence.delay = 1;
        assert_eq!(
            advance_actor_cycle(
                &mut path,
                &mut sequence,
                tile(4, 0),
                PathAccelerationPolicy::STANDARD,
            ),
            ActorMovementCycle::Snapped
        );
        assert_eq!(path.catch_up_cycles(), 1);
        assert!(path.is_empty());

        assert_eq!(
            advance_actor_cycle(
                &mut path,
                &mut sequence,
                tile(4, 0),
                PathAccelerationPolicy::STANDARD,
            ),
            ActorMovementCycle::Idle
        );
        assert_eq!(path.catch_up_cycles(), 0);
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
            tile(1, 0),
            PathAccelerationPolicy::STANDARD,
        );
        assert_eq!(path.fine_position(tile(0, 0)).x, 8);
    }

    #[test]
    fn standard_backlog_acceleration_uses_player_thresholds() {
        let mut path = ActorPathQueue::default();
        path.ensure_position(tile(0, 0));
        for x in 1..=3 {
            path.enqueue(tile(x, 0), MoveSpeed::Walk);
        }
        let mut sequence = ActorSequenceSlot::default();
        advance_actor_cycle(
            &mut path,
            &mut sequence,
            tile(3, 0),
            PathAccelerationPolicy::STANDARD,
        );
        assert_eq!(path.fine_position(tile(0, 0)).x, 6);
    }

    #[test]
    fn early_npc_backlog_acceleration_moves_one_entry_sooner() {
        let mut standard = ActorPathQueue::default();
        let mut early = ActorPathQueue::default();
        standard.ensure_position(tile(0, 0));
        early.ensure_position(tile(0, 0));
        for x in 1..=2 {
            standard.enqueue(tile(x, 0), MoveSpeed::Walk);
            early.enqueue(tile(x, 0), MoveSpeed::Walk);
        }
        let mut standard_sequence = ActorSequenceSlot::default();
        let mut early_sequence = ActorSequenceSlot::default();
        advance_actor_cycle(
            &mut standard,
            &mut standard_sequence,
            tile(2, 0),
            PathAccelerationPolicy::STANDARD,
        );
        advance_actor_cycle(
            &mut early,
            &mut early_sequence,
            tile(2, 0),
            PathAccelerationPolicy::EARLY,
        );
        assert_eq!(standard.fine_position(tile(0, 0)).x, 4);
        assert_eq!(early.fine_position(tile(0, 0)).x, 6);
    }
}
