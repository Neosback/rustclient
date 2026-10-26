//! Committed body turning, after movement and before action playback.
use rustclient_model::JauTurnRate;

/// Direction of an idle turning-animation request. Cache projection selects sequence/fallback.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum TurnDirection {
    Left,
    Right,
}

/// Actor-owned turn policy/counter and the last cycle's pose inputs.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct ActorTurning {
    rate: Option<JauTurnRate>,
    cycles: i32,
    movement_orientation: u16,
    idle_direction: Option<TurnDirection>,
}

impl Default for ActorTurning {
    fn default() -> Self {
        Self::new(None, 0)
    }
}

impl ActorTurning {
    #[must_use]
    pub const fn new(rate: Option<JauTurnRate>, orientation: u16) -> Self {
        Self {
            rate,
            cycles: 0,
            movement_orientation: orientation,
            idle_direction: None,
        }
    }
    #[must_use]
    pub const fn rate(self) -> Option<JauTurnRate> {
        self.rate
    }
    #[must_use]
    pub const fn cycles(self) -> i32 {
        self.cycles
    }
    /// Body orientation seen by movement before this cycle's ordinary turn.
    #[must_use]
    pub const fn movement_orientation(self) -> u16 {
        self.movement_orientation
    }
    #[must_use]
    pub const fn idle_direction(self) -> Option<TurnDirection> {
        self.idle_direction
    }
    pub(crate) fn set_rate(&mut self, rate: Option<JauTurnRate>) {
        self.rate = rate;
    }

    pub(crate) fn advance(&mut self, desired: u16, current: &mut u16) {
        self.movement_orientation = *current;
        self.idle_direction = None;
        let Some(rate) = self.rate else {
            return;
        };
        let delta = (i32::from(desired) - i32::from(*current)) & 2047;
        if delta == 0 {
            self.cycles = 0;
            return;
        }
        self.cycles = self.cycles.wrapping_add(1);
        let direction = if delta > 1024 { -1 } else { 1 };
        let rate = i32::from(rate.get());
        let partial = delta >= rate && delta <= 2048 - rate;
        let rotation = if partial {
            i32::from(*current) + rate * direction
        } else {
            i32::from(desired)
        };
        *current = u16::try_from(rotation & 2047).expect("wrapped orientation");
        if rate > 0 && (self.cycles > 25 || partial) {
            self.idle_direction = Some(if direction < 0 {
                TurnDirection::Left
            } else {
                TurnDirection::Right
            });
        }
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn turn_boundaries_match_exact_240_strict_snap_and_half_turn() {
        for (start, desired, rate, expected, pose) in [
            (0, 1024, 32, 32, Some(TurnDirection::Right)),
            (0, 1025, 32, 2016, Some(TurnDirection::Left)),
            (2030, 14, 32, 14, Some(TurnDirection::Right)),
            (0, 31, 32, 31, None),
            (0, 32, 32, 32, Some(TurnDirection::Right)),
            (0, 2016, 32, 2016, Some(TurnDirection::Left)),
            (0, 2017, 32, 2017, None),
            (0, 1024, 65535, 1024, None),
            (0, 1024, 0, 0, None),
        ] {
            let mut state = ActorTurning::new(Some(JauTurnRate::new(rate)), start);
            let mut current = start;
            state.advance(desired, &mut current);
            assert_eq!(
                (current, state.idle_direction),
                (expected, pose),
                "{start}/{desired}/{rate}"
            );
            assert_eq!(state.cycles(), 1);
            assert_eq!(state.movement_orientation(), start);
        }
    }

    #[test]
    fn idle_request_delay_counter_wrap_and_alignment_reset() {
        let mut state = ActorTurning::new(Some(JauTurnRate::new(32)), 0);
        let mut current = 0;
        state.cycles = 25;
        state.advance(1, &mut current);
        assert_eq!(state.idle_direction(), Some(TurnDirection::Right));
        state.advance(1, &mut current);
        assert_eq!((state.cycles(), state.idle_direction()), (0, None));
        state.cycles = i32::MAX;
        state.advance(1024, &mut current);
        assert_eq!(state.cycles(), i32::MIN);
        state.set_rate(Some(JauTurnRate::new(0)));
        state.advance(1024, &mut current);
        assert_eq!(current, 33);
        assert_eq!(state.idle_direction(), None);
    }
}
