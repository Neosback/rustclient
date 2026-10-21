use core::fmt;
use core::time::Duration;

use rustclient_platform::{Clock, MonotonicTime, WallTime};

/// Browser clock backed by `Performance.now()` and `Date.now()`.
#[derive(Debug, Clone)]
pub struct BrowserClock {
    performance: web_sys::Performance,
}

impl BrowserClock {
    /// Acquire browser clock capabilities from the active window.
    pub fn new() -> Result<Self, BrowserClockError> {
        let window = web_sys::window().ok_or(BrowserClockError::WindowUnavailable)?;
        let performance = window
            .performance()
            .ok_or(BrowserClockError::PerformanceUnavailable)?;
        Ok(Self { performance })
    }
}

impl Clock for BrowserClock {
    fn monotonic_now(&self) -> MonotonicTime {
        let milliseconds = self.performance.now().max(0.0);
        MonotonicTime::from_duration(Duration::from_secs_f64(milliseconds / 1_000.0))
    }

    fn wall_now(&self) -> WallTime {
        WallTime::from_unix_millis(date_now_millis())
    }
}

#[allow(clippy::cast_possible_truncation)]
fn date_now_millis() -> i64 {
    // ECMAScript Date values are integral millisecond timestamps and their
    // specified range is far inside i64. Rounding handles the f64 carrier
    // without exposing floating-point wall time to shared code.
    js_sys::Date::now().round() as i64
}

/// Failure to acquire required browser clock globals.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum BrowserClockError {
    /// Code is not running with a browser `Window` global.
    WindowUnavailable,
    /// The browser did not expose the Performance API.
    PerformanceUnavailable,
}

impl fmt::Display for BrowserClockError {
    fn fmt(&self, formatter: &mut fmt::Formatter<'_>) -> fmt::Result {
        formatter.write_str(match self {
            Self::WindowUnavailable => "browser window is unavailable",
            Self::PerformanceUnavailable => "browser performance clock is unavailable",
        })
    }
}

impl std::error::Error for BrowserClockError {}
