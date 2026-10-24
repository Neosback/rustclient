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

/// Advance one action sequence after authoritative movement has observed this cycle's pre-advance
/// delay/frame state.
///
/// Resolved legacy playback follows the revision-240 controller counters used by forced movement:
/// explicit delay is consumed first; the within-frame counter increments; crossing a frame delay
/// moves to the next frame with cycle 1; final-frame playback rewinds by the authored frame step and
/// counts an iteration, clearing when no valid rewind remains or the resolved max-loop limit is
/// reached. Cached-model and unresolved timing retain the previous aggregate-cycle behavior here;
/// cached-model family classification is already sufficient for the forced-movement gate.
pub(crate) fn advance_one(slot: &mut ActorSequenceSlot, timing: Option<&SequenceTiming>) {
    if slot.sequence.is_none() {
        return;
    }
    if slot.delay > 0 {
        slot.delay -= 1;
        return;
    }

    let Some(SequenceTiming::Legacy {
        frame_delays,
        frame_step,
    }) = timing
    else {
        slot.advance_cycles(1);
        return;
    };

    if frame_delays.is_empty() {
        // A resolved definition with no legacy frames cannot supply a frame boundary. Keep the
        // existing aggregate counter deterministic without inventing a duration.
        slot.cycle = slot.cycle.wrapping_add(1);
        return;
    }

    let frame = slot.frame.unwrap_or(0);
    let Ok(frame_index) = usize::try_from(frame) else {
        slot.clear();
        return;
    };
    let Some(&frame_delay) = frame_delays.get(frame_index) else {
        slot.clear();
        return;
    };

    slot.frame = Some(frame);
    slot.cycle = slot.cycle.wrapping_add(1);
    if slot.cycle <= i32::from(frame_delay) {
        return;
    }

    slot.cycle = 1;
    let next = frame.saturating_add(1);
    if usize::try_from(next).ok().is_some_and(|index| index < frame_delays.len()) {
        slot.frame = Some(next);
        return;
    }

    let Some(rewind) = frame_step.filter(|step| *step != 0) else {
        slot.clear();
        return;
    };
    let Some(looped) = next.checked_sub(u32::from(rewind)) else {
        slot.clear();
        return;
    };
    let Ok(looped_index) = usize::try_from(looped) else {
        slot.clear();
        return;
    };
    if looped_index >= frame_delays.len() {
        slot.clear();
        return;
    }

    slot.iterations = slot.iterations.wrapping_add(1);
    if slot
        .policy
        .is_some_and(|policy| slot.iterations >= u32::from(policy.max_loops))
    {
        slot.clear();
        return;
    }
    slot.frame = Some(looped);
    slot.cycle = 0;
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
        let timing = SequenceTiming::CachedModel { range: Some((0, 10)) };
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
