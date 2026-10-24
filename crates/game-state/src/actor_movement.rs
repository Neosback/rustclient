//! Authoritative per-client-cycle actor fine-position scheduling.
//!
//! Ordinary queued and exact/forced player/NPC translation lives in `GameState`. Scene motion is
//! a disposable pose/turn projection and must not own a second positional scheduler.

use crate::{ActorFinePosition, ActorPathQueue, ExactMovement, WorldEntitiesState};
use rustclient_model::{
    ActorSequenceSlot, ClientCycle, MoveSpeed, PathAccelerationPolicy, SequenceTiming, WorldTile,
};

const SNAP_FINE_UNITS: i64 = 288;

#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub(crate) enum ActorMovementCycle {
    Idle,
    Blocked,
    Moving,
    Consumed,
    Snapped,
    ForcedApproach,
    ForcedInterpolated,
    ForcedHeld,
}

/// Advance all visible actors by one authoritative client cycle.
///
/// Exact movement owns position before ordinary queued movement. Movement observes action delay and
/// legacy frame-boundary state before playback advances later in the same cycle.
pub(crate) fn advance_players(entities: &mut WorldEntitiesState, current_cycle: ClientCycle) {
    let timings = &entities.sequence_timings;
    for player in entities.players.values_mut() {
        let timing = player
            .sequence
            .sequence
            .filter(|_| player.sequence_timing_resolved)
            .and_then(|id| timings.get(&id));
        if advance_forced_cycle(
            &mut player.path_queue,
            &player.sequence,
            timing,
            &mut player.exact_move,
            &mut player.orientation,
            &mut player.current_orientation,
            player.tile,
            current_cycle,
        )
        .is_some()
        {
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
        let timing = npc
            .sequence
            .sequence
            .filter(|_| npc.sequence_timing_resolved)
            .and_then(|id| timings.get(&id));
        if advance_forced_cycle(
            &mut npc.path_queue,
            &npc.sequence,
            timing,
            &mut npc.exact_move,
            &mut npc.orientation,
            &mut npc.current_orientation,
            npc.tile,
            current_cycle,
        )
        .is_some()
        {
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

/// Advance the active revision-240 forced trajectory, returning `None` only when no forced phase
/// owns this cycle. An expired trajectory is cleared and ordinary scheduling resumes immediately.
fn advance_forced_cycle(
    path: &mut ActorPathQueue,
    sequence: &ActorSequenceSlot,
    timing: Option<&SequenceTiming>,
    exact_move: &mut Option<ExactMovement>,
    orientation: &mut u16,
    current_orientation: &mut u16,
    fallback_tile: WorldTile,
    current_cycle: ClientCycle,
) -> Option<ActorMovementCycle> {
    let exact = (*exact_move)?;
    let current = current_cycle.get();
    let start_cycle = exact.starts_at.get();
    let end_cycle = exact.ends_at.get();

    if current > end_cycle {
        *exact_move = None;
        return None;
    }

    path.ensure_position(fallback_tile);
    path.reset_catch_up();
    *orientation = exact.direction;

    let start = ActorFinePosition::from_tile(exact.start);
    if current <= start_cycle {
        let remaining = start_cycle.saturating_sub(current).max(1);
        let divisor = i64::try_from(remaining).unwrap_or(i64::MAX);
        let mut position = path.fine_position(fallback_tile);
        position.x += (start.x - position.x) / divisor;
        position.y += (start.y - position.y) / divisor;
        path.set_fine_position(position);
        return Some(ActorMovementCycle::ForcedApproach);
    }

    // Revision 240 snaps current body rotation to the authored forced direction throughout the
    // interpolation phase, even on a cycle where the legacy action-frame gate holds position.
    *current_orientation = *orientation;

    let at_exact_end = current == end_cycle;
    if !crate::sequence_playback::forced_interpolation_allowed(
        *sequence,
        timing,
        at_exact_end,
    ) {
        return Some(ActorMovementCycle::ForcedHeld);
    }

    let end = ActorFinePosition::from_tile(exact.end);
    let duration = end_cycle - start_cycle;
    debug_assert!(duration > 0, "approach phase owns zero-duration exact movement");
    let elapsed = current - start_cycle;
    let interpolate = |from: i64, to: i64| {
        let duration = i128::from(duration);
        let elapsed = i128::from(elapsed);
        i64::try_from(
            (i128::from(from) * (duration - elapsed) + i128::from(to) * elapsed) / duration,
        )
        .expect("convex exact-movement interpolation fits fine coordinate")
    };
    path.set_fine_position(ActorFinePosition {
        x: interpolate(start.x, end.x),
        y: interpolate(start.y, end.y),
    });
    Some(ActorMovementCycle::ForcedInterpolated)
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
    use super::{ActorMovementCycle, advance_actor_cycle, advance_forced_cycle};
    use crate::{ActorFinePosition, ActorPathQueue, ExactMovement};
    use rustclient_model::{
        ActorSequenceSlot, ClientCycle, MoveSpeed, PathAccelerationPolicy, Plane, SequenceId,
        SequencePolicy, SequenceTiming, WorldTile,
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

    fn exact() -> ExactMovement {
        ExactMovement {
            start: tile(1, 0),
            end: tile(3, 0),
            starts_at: ClientCycle::new(2),
            ends_at: ClientCycle::new(4),
            direction: 512,
        }
    }

    #[test]
    fn forced_approach_preserves_current_orientation_then_interpolation_snaps_it() {
        let mut path = ActorPathQueue::default();
        path.ensure_position(tile(0, 0));
        path.increment_catch_up();
        let sequence = ActorSequenceSlot::default();
        let mut movement = Some(exact());
        let mut orientation = 0;
        let mut current_orientation = 1024;

        assert_eq!(
            advance_forced_cycle(
                &mut path,
                &sequence,
                None,
                &mut movement,
                &mut orientation,
                &mut current_orientation,
                tile(0, 0),
                ClientCycle::new(2),
            ),
            Some(ActorMovementCycle::ForcedApproach)
        );
        assert_eq!(path.fine_position(tile(0, 0)).x, 128);
        assert_eq!(path.catch_up_cycles(), 0);
        assert_eq!(orientation, 512);
        assert_eq!(current_orientation, 1024);

        assert_eq!(
            advance_forced_cycle(
                &mut path,
                &sequence,
                None,
                &mut movement,
                &mut orientation,
                &mut current_orientation,
                tile(0, 0),
                ClientCycle::new(3),
            ),
            Some(ActorMovementCycle::ForcedInterpolated)
        );
        assert_eq!(path.fine_position(tile(0, 0)).x, 256);
        assert_eq!(current_orientation, 512);
    }

    #[test]
    fn legacy_zero_delay_action_holds_until_frame_boundary_but_end_cycle_wins() {
        let mut path = ActorPathQueue::default();
        path.reset_to(tile(1, 0));
        let mut sequence = ActorSequenceSlot::default();
        sequence.assign_sequence(Some((SequenceId::new(1), policy(1, 1))), 0, 0);
        sequence.frame = Some(0);
        sequence.cycle = 0;
        let timing = SequenceTiming::Legacy {
            frame_delays: vec![5],
            frame_step: None,
        };
        let mut movement = Some(exact());
        let mut orientation = 0;
        let mut current_orientation = 1024;

        assert_eq!(
            advance_forced_cycle(
                &mut path,
                &sequence,
                Some(&timing),
                &mut movement,
                &mut orientation,
                &mut current_orientation,
                tile(0, 0),
                ClientCycle::new(3),
            ),
            Some(ActorMovementCycle::ForcedHeld)
        );
        assert_eq!(path.fine_position(tile(0, 0)).x, 128);
        assert_eq!(current_orientation, 512);

        assert_eq!(
            advance_forced_cycle(
                &mut path,
                &sequence,
                Some(&timing),
                &mut movement,
                &mut orientation,
                &mut current_orientation,
                tile(0, 0),
                ClientCycle::new(4),
            ),
            Some(ActorMovementCycle::ForcedInterpolated)
        );
        assert_eq!(path.fine_position(tile(0, 0)).x, 384);
        assert_eq!(current_orientation, 512);
    }

    #[test]
    fn expired_forced_marker_is_removed_and_releases_ordinary_scheduler() {
        let mut path = ActorPathQueue::default();
        let sequence = ActorSequenceSlot::default();
        let mut movement = Some(exact());
        let mut orientation = 0;
        let mut current_orientation = 1024;
        assert_eq!(
            advance_forced_cycle(
                &mut path,
                &sequence,
                None,
                &mut movement,
                &mut orientation,
                &mut current_orientation,
                tile(0, 0),
                ClientCycle::new(5),
            ),
            None
        );
        assert_eq!(movement, None);
        assert_eq!(orientation, 0);
        assert_eq!(current_orientation, 1024);
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
