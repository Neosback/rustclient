use std::time::{Instant, SystemTime, UNIX_EPOCH};

use rustclient_platform::{Clock, MonotonicTime, WallTime};

/// Native clock backed by `std::time`.
#[derive(Debug, Clone)]
pub struct NativeClock {
    origin: Instant,
}

impl NativeClock {
    /// Start a clock with a fresh monotonic origin.
    #[must_use]
    pub fn new() -> Self {
        Self {
            origin: Instant::now(),
        }
    }
}

impl Default for NativeClock {
    fn default() -> Self {
        Self::new()
    }
}

impl Clock for NativeClock {
    fn monotonic_now(&self) -> MonotonicTime {
        MonotonicTime::from_duration(self.origin.elapsed())
    }

    fn wall_now(&self) -> WallTime {
        WallTime::from_unix_millis(system_time_millis(SystemTime::now()))
    }
}

fn system_time_millis(time: SystemTime) -> i64 {
    match time.duration_since(UNIX_EPOCH) {
        Ok(duration) => u128_to_i64_saturating(duration.as_millis()),
        Err(error) => u128_to_i64_saturating(error.duration().as_millis()).saturating_neg(),
    }
}

fn u128_to_i64_saturating(value: u128) -> i64 {
    i64::try_from(value).unwrap_or(i64::MAX)
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn monotonic_clock_does_not_move_backward() {
        let clock = NativeClock::new();
        let first = clock.monotonic_now();
        let second = clock.monotonic_now();
        assert!(second >= first);
    }

    #[test]
    fn epoch_conversion_preserves_before_and_after_epoch() {
        assert_eq!(system_time_millis(UNIX_EPOCH), 0);
        assert_eq!(
            system_time_millis(UNIX_EPOCH + std::time::Duration::from_millis(42)),
            42
        );
        assert_eq!(
            system_time_millis(UNIX_EPOCH - std::time::Duration::from_millis(42)),
            -42
        );
    }
}
