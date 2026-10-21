//! Deterministic game-time value types.
//!
//! A game tick is an ordinal semantic step, not a wall-clock timestamp. The
//! nominal real-world duration of a tick is profile/runtime policy and is not
//! encoded here.

/// Monotonic semantic game-tick ordinal.
#[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord, Hash)]
pub struct GameTick(u64);

impl GameTick {
    /// First tick ordinal.
    pub const ZERO: Self = Self(0);

    /// Construct a tick ordinal.
    #[must_use]
    pub const fn new(value: u64) -> Self {
        Self(value)
    }

    /// Return the numeric ordinal.
    #[must_use]
    pub const fn get(self) -> u64 {
        self.0
    }

    /// Advance by a semantic tick span without wrapping.
    #[must_use]
    pub const fn checked_advance(self, span: TickSpan) -> Option<Self> {
        match self.0.checked_add(span.0) {
            Some(value) => Some(Self(value)),
            None => None,
        }
    }

    /// Return elapsed ticks since `earlier`, or `None` when ordering is reversed.
    #[must_use]
    pub const fn checked_since(self, earlier: Self) -> Option<TickSpan> {
        match self.0.checked_sub(earlier.0) {
            Some(value) => Some(TickSpan(value)),
            None => None,
        }
    }
}

/// Count of semantic game ticks.
#[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord, Hash)]
pub struct TickSpan(u64);

impl TickSpan {
    /// Zero ticks.
    pub const ZERO: Self = Self(0);
    /// One tick.
    pub const ONE: Self = Self(1);

    /// Construct a tick span.
    #[must_use]
    pub const fn new(value: u64) -> Self {
        Self(value)
    }

    /// Return the number of ticks.
    #[must_use]
    pub const fn get(self) -> u64 {
        self.0
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn tick_arithmetic_is_checked() {
        let start = GameTick::new(10);
        let end = start
            .checked_advance(TickSpan::new(5))
            .expect("small tick span cannot overflow");

        assert_eq!(end, GameTick::new(15));
        assert_eq!(end.checked_since(start), Some(TickSpan::new(5)));
        assert_eq!(start.checked_since(end), None);
        assert_eq!(GameTick::new(u64::MAX).checked_advance(TickSpan::ONE), None);
    }
}
