//! Platform-neutral clock values and capability contract.

use core::time::Duration;

/// Monotonic time elapsed from an implementation-defined process/session origin.
#[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord, Hash, Default)]
pub struct MonotonicTime(Duration);

impl MonotonicTime {
    /// Construct a monotonic timestamp from an elapsed duration.
    #[must_use]
    pub const fn from_duration(duration: Duration) -> Self {
        Self(duration)
    }

    /// Return the elapsed duration from the implementation-defined origin.
    #[must_use]
    pub const fn as_duration(self) -> Duration {
        self.0
    }

    /// Return the checked duration elapsed since an earlier timestamp.
    #[must_use]
    pub fn checked_duration_since(self, earlier: Self) -> Option<Duration> {
        self.0.checked_sub(earlier.0)
    }
}

/// Wall-clock Unix timestamp in milliseconds.
#[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord, Hash, Default)]
pub struct WallTime(i64);

impl WallTime {
    /// Construct a wall timestamp from signed Unix milliseconds.
    #[must_use]
    pub const fn from_unix_millis(milliseconds: i64) -> Self {
        Self(milliseconds)
    }

    /// Return signed Unix milliseconds.
    #[must_use]
    pub const fn unix_millis(self) -> i64 {
        self.0
    }
}

/// Clock capability supplied by the active platform adapter.
///
/// Semantic game ticks remain separate from wall and monotonic platform time.
pub trait Clock {
    /// Return a timestamp that never moves backward for this clock instance.
    fn monotonic_now(&self) -> MonotonicTime;

    /// Return the current wall-clock Unix timestamp.
    fn wall_now(&self) -> WallTime;
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn monotonic_duration_is_checked() {
        let early = MonotonicTime::from_duration(Duration::from_millis(10));
        let late = MonotonicTime::from_duration(Duration::from_millis(25));

        assert_eq!(
            late.checked_duration_since(early),
            Some(Duration::from_millis(15))
        );
        assert_eq!(early.checked_duration_since(late), None);
    }

    #[test]
    fn wall_time_preserves_signed_unix_millis() {
        assert_eq!(WallTime::from_unix_millis(-1).unix_millis(), -1);
        assert_eq!(WallTime::from_unix_millis(0).unix_millis(), 0);
        assert_eq!(WallTime::from_unix_millis(42).unix_millis(), 42);
    }
}
