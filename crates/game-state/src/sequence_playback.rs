//! Definition-informed action playback needed by authoritative actor scheduling.

use rustclient_model::{ActorSequenceSlot, SequenceTiming};

/// Whether revision-240 forced interpolation may update position this cycle.
///
/// The exact end cycle always interpolates. Otherwise an absent action, a non-zero explicit start
/// delay, or a cached-model action permits interpolation. A legacy action at zero delay must be on
/// the frame boundary that its playback step will cross later in this same client cycle.
#[must_use]
pub(crate) fn forced_interpolation_allowed(
    slot: ActorSequenceSlot,
    timing: Option<&SequenceTiming>,
    at_exact_end: bool,
) -> bool {
    if at_exact_end || slot.sequence.is_none() || slot.delay != 0 {
        return true;
    }
    match timing {
        Some(SequenceTiming::CachedModel { .. }) => true,
        Some(SequenceTiming::Legacy { .. }) => {
            let Some(frame) = slot.frame else {
                return false;
            };
            let Some(frame_delay) = timing.and_then(|value| value.legacy_frame_delay(frame)) else {
                return false;
            };
            slot.cycle.wrapping_add(1) > i32::from(frame_delay)
        }
        None => false,
    }
}

/// Hold an action at delay one until its pre-assignment path remainder has been consumed.
/// Called after movement, using the remaining backlog and this cycle's forced-movement bounds.
pub(crate) fn hold_for_path(
    slot: &mut ActorSequenceSlot,
    exact: Option<crate::ExactMovement>,
    cycle: rustclient_model::ClientCycle,
) -> bool {
    let forced_ended =
        exact.is_none_or(|movement| movement.starts_at <= cycle && movement.ends_at < cycle);
    let hold = slot.sequence.is_some()
        && slot.delay <= 1
        && slot
            .policy
            .is_some_and(|policy| policy.movement_gate_with_backlog == 1)
        && slot.path_backlog_at_assignment > 0
        && forced_ended;
    if hold {
        slot.delay = 1;
    }
    hold
}

/// Advance the action after movement has consumed this cycle's pre-playback state.
/// Unresolved timing retains the explicit aggregate-counter compatibility fallback.
pub(crate) fn advance_one(slot: &mut ActorSequenceSlot, timing: Option<&SequenceTiming>) {
    if slot.sequence.is_none() {
        return;
    }
    if slot.delay > 0 {
        slot.delay -= 1;
        return;
    }
    match timing {
        Some(SequenceTiming::Legacy {
            frame_delays,
            frame_step,
        }) => advance_legacy(slot, frame_delays, *frame_step),
        Some(SequenceTiming::CachedModel { range, frame_step }) => {
            advance_cached(slot, *range, *frame_step);
        }
        None => slot.advance_cycles(1),
    }
}

fn reached_loop_limit(slot: &mut ActorSequenceSlot) -> bool {
    slot.iterations = slot.iterations.wrapping_add(1);
    slot.policy
        .is_some_and(|policy| slot.iterations >= u32::from(policy.max_loops))
}

fn advance_cached(slot: &mut ActorSequenceSlot, range: Option<(u16, u16)>, step: Option<u16>) {
    let duration = range.map_or(0, |(start, end)| i64::from(end) - i64::from(start));
    let mut frame = i64::from(slot.frame.unwrap_or(0)) + 1;
    if frame >= duration {
        let terminal = reached_loop_limit(slot);
        frame -= step.map_or(-1, i64::from);
        if terminal || frame < 0 || frame >= duration {
            slot.clear();
            return;
        }
    }
    // The valid cached range is bounded by its semantic u16 endpoints.
    slot.frame = u32::try_from(frame).ok();
    // Cached playback deliberately leaves the legacy within-frame counter unchanged.
}

fn advance_legacy(slot: &mut ActorSequenceSlot, delays: &[u16], step: Option<u16>) {
    if delays.is_empty() {
        slot.clear();
        return;
    }
    let mut frame = slot
        .frame
        .and_then(|frame| usize::try_from(frame).ok())
        .unwrap_or(0);
    if frame >= delays.len() {
        frame = 0;
        slot.cycle = 0;
    }
    // classRD.method9980 reduces the advance modulo total duration for positive rewinds.
    // With a one-cycle advance this only changes the duration-one case (advance becomes zero).
    // Only duration == 1 matters for this one-cycle entry point. Two nonzero delays
    // already prove otherwise, avoiding a full frame-table sum on ordinary hot paths.
    let duration: u32 = delays
        .iter()
        .copied()
        .filter(|&delay| delay != 0)
        .take(2)
        .map(u32::from)
        .sum();
    let advance = i32::from(!(step.is_some_and(|step| step > 0) && duration == 1));
    slot.cycle = slot.cycle.wrapping_add(advance);
    while slot.cycle > i32::from(delays[frame]) {
        slot.cycle -= i32::from(delays[frame]);
        frame += 1;
        if frame == delays.len() {
            let terminal = reached_loop_limit(slot);
            let looped = step
                .and_then(|step| frame.checked_sub(usize::from(step)))
                .filter(|&frame| frame < delays.len());
            let Some(looped) = looped.filter(|_| !terminal) else {
                slot.clear();
                return;
            };
            // A zero-duration repeating tail cannot spend the remainder. The action is terminal
            // rather than allowing malformed timing to spin forever, including unresolved policy.
            if delays[looped..].iter().all(|&delay| delay == 0) {
                slot.clear();
                return;
            }
            frame = looped;
        }
    }
    slot.frame = u32::try_from(frame).ok();
    if slot.frame.is_none() {
        slot.clear();
    }
}

#[cfg(test)]
mod tests {
    use super::{advance_one, forced_interpolation_allowed};
    use rustclient_model::{ActorSequenceSlot, SequenceId, SequencePolicy, SequenceTiming};

    const POLICY: SequencePolicy = SequencePolicy {
        restart_mode: 2,
        replacement_priority: 5,
        movement_gate_with_backlog: 1,
        movement_gate_without_backlog: 1,
        max_loops: 99,
    };

    fn legacy() -> SequenceTiming {
        SequenceTiming::Legacy {
            frame_delays: vec![2, 3],
            frame_step: Some(2),
        }
    }

    fn action(max_loops: u16) -> ActorSequenceSlot {
        let mut slot = ActorSequenceSlot::default();
        slot.assign_sequence(
            Some((
                SequenceId::new(1),
                SequencePolicy {
                    max_loops,
                    ..POLICY
                },
            )),
            0,
            0,
        );
        slot
    }

    #[test]
    fn legacy_carries_remainder_skips_zero_frames_and_rewinds_without_losing_time() {
        let timing = SequenceTiming::Legacy {
            frame_delays: vec![2, 0, 3],
            frame_step: Some(1),
        };
        let mut slot = action(2);
        for _ in 0..3 {
            advance_one(&mut slot, Some(&timing));
        }
        assert_eq!((slot.frame, slot.cycle, slot.iterations), (Some(2), 1, 0));
        for _ in 0..3 {
            advance_one(&mut slot, Some(&timing));
        }
        assert_eq!((slot.frame, slot.cycle, slot.iterations), (Some(2), 1, 1));
        for _ in 0..3 {
            advance_one(&mut slot, Some(&timing));
        }
        assert_eq!(slot, ActorSequenceSlot::default());
    }

    #[test]
    fn legacy_normalizes_stale_frame_and_preserves_duration_one_modulo() {
        let timing = legacy();
        let mut slot = action(3);
        slot.frame = Some(200);
        slot.cycle = 800;
        advance_one(&mut slot, Some(&timing));
        assert_eq!((slot.frame, slot.cycle), (Some(0), 1));
        let singleton = SequenceTiming::Legacy {
            frame_delays: vec![1],
            frame_step: Some(1),
        };
        let mut slot = action(1);
        for _ in 0..50 {
            advance_one(&mut slot, Some(&singleton));
        }
        assert_eq!((slot.frame, slot.cycle, slot.iterations), (Some(0), 0, 0));
    }

    #[test]
    fn cached_cursor_uses_range_length_and_keeps_legacy_counter_until_terminal_clear() {
        let timing = SequenceTiming::CachedModel {
            range: Some((10, 13)),
            frame_step: Some(2),
        };
        let mut slot = action(2);
        slot.cycle = 7;
        for (frame, iterations) in [(1, 0), (2, 0), (1, 1), (2, 1)] {
            advance_one(&mut slot, Some(&timing));
            assert_eq!(
                (slot.frame, slot.cycle, slot.iterations),
                (Some(frame), 7, iterations)
            );
        }
        advance_one(&mut slot, Some(&timing));
        assert_eq!(slot, ActorSequenceSlot::default());
    }

    #[test]
    fn invalid_rewinds_and_degenerate_timing_finish_in_bounded_work() {
        for timing in [
            SequenceTiming::CachedModel {
                range: None,
                frame_step: None,
            },
            SequenceTiming::CachedModel {
                range: Some((4, 4)),
                frame_step: Some(1),
            },
            SequenceTiming::CachedModel {
                range: Some((9, 3)),
                frame_step: Some(2),
            },
            SequenceTiming::CachedModel {
                range: Some((0, 2)),
                frame_step: None,
            },
            SequenceTiming::CachedModel {
                range: Some((0, 2)),
                frame_step: Some(0),
            },
            SequenceTiming::CachedModel {
                range: Some((0, 2)),
                frame_step: Some(3),
            },
            SequenceTiming::Legacy {
                frame_delays: vec![],
                frame_step: None,
            },
            SequenceTiming::Legacy {
                frame_delays: vec![0, 0],
                frame_step: Some(2),
            },
            SequenceTiming::Legacy {
                frame_delays: vec![2, 0],
                frame_step: Some(1),
            },
            SequenceTiming::Legacy {
                frame_delays: vec![2],
                frame_step: None,
            },
            SequenceTiming::Legacy {
                frame_delays: vec![2],
                frame_step: Some(0),
            },
            SequenceTiming::Legacy {
                frame_delays: vec![2],
                frame_step: Some(3),
            },
        ] {
            let mut slot = action(99);
            for _ in 0..4 {
                advance_one(&mut slot, Some(&timing));
            }
            assert_eq!(slot, ActorSequenceSlot::default(), "{timing:?}");
        }
    }

    #[test]
    fn delay_and_path_hold_never_advance_on_the_cycle_delay_reaches_zero() {
        use rustclient_model::{ClientCycle, Plane, WorldTile};
        let timing = SequenceTiming::CachedModel {
            range: Some((0, 10)),
            frame_step: None,
        };
        let mut slot = action(99);
        slot.delay = 2;
        advance_one(&mut slot, Some(&timing));
        advance_one(&mut slot, Some(&timing));
        assert_eq!((slot.delay, slot.frame), (0, Some(0)));
        slot.path_backlog_at_assignment = 1;
        assert!(super::hold_for_path(&mut slot, None, ClientCycle::new(1)));
        assert_eq!((slot.delay, slot.frame), (1, Some(0)));
        let exact = crate::ExactMovement {
            start: WorldTile::new(0, 0, Plane::MIN),
            end: WorldTile::new(1, 0, Plane::MIN),
            starts_at: ClientCycle::new(2),
            ends_at: ClientCycle::new(4),
            direction: 0,
        };
        assert!(!super::hold_for_path(
            &mut slot,
            Some(exact),
            ClientCycle::new(1)
        ));
        assert!(!super::hold_for_path(
            &mut slot,
            Some(exact),
            ClientCycle::new(4)
        ));
        assert!(super::hold_for_path(
            &mut slot,
            Some(exact),
            ClientCycle::new(5)
        ));
        slot.path_backlog_at_assignment = 0;
        advance_one(&mut slot, Some(&timing));
        assert_eq!((slot.delay, slot.frame), (0, Some(0)));
        advance_one(&mut slot, Some(&timing));
        assert_eq!(slot.frame, Some(1));
    }

    #[test]
    fn legacy_forced_gate_uses_the_boundary_before_playback_advances() {
        let timing = legacy();
        let mut slot = ActorSequenceSlot::default();
        slot.assign_sequence(Some((SequenceId::new(1), POLICY)), 0, 0);
        slot.frame = Some(0);
        slot.cycle = 2;
        assert!(forced_interpolation_allowed(slot, Some(&timing), false));
        advance_one(&mut slot, Some(&timing));
        assert_eq!(slot.frame, Some(1));
        assert_eq!(slot.cycle, 1);
    }

    #[test]
    fn cached_model_bypasses_legacy_boundary_gate() {
        let mut slot = ActorSequenceSlot::default();
        slot.assign_sequence(Some((SequenceId::new(1), POLICY)), 0, 0);
        let timing = SequenceTiming::CachedModel {
            range: Some((0, 10)),
            frame_step: None,
        };
        assert!(forced_interpolation_allowed(slot, Some(&timing), false));
    }

    #[test]
    fn unresolved_zero_delay_action_does_not_guess_forced_boundary() {
        let mut slot = ActorSequenceSlot::default();
        slot.assign_sequence(Some((SequenceId::new(1), POLICY)), 0, 0);
        assert!(!forced_interpolation_allowed(slot, None, false));
        assert!(forced_interpolation_allowed(slot, None, true));
    }
}
