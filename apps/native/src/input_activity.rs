//! Time since the player last touched the keyboard or pointer, for plugins that watch idleness.
//!
//! `RuneLite` reads the client's own key and mouse handlers (`getKeyboardIdleTicks`,
//! `getMouseIdleTicks`, `getMouseLastPressedMillis`); here the shell stamps the same three events.

use rustclient_plugin_api::InputIdle;
use std::time::{Duration, Instant};
use winit::event::WindowEvent;

/// Length of one client tick.
const CLIENT_TICK: Duration = Duration::from_millis(20);

#[derive(Debug, Clone, Copy)]
pub(super) struct InputActivity {
    keyboard: Instant,
    mouse: Instant,
    mouse_press: Instant,
}

impl InputActivity {
    pub(super) fn new() -> Self {
        let now = Instant::now();
        Self {
            keyboard: now,
            mouse: now,
            mouse_press: now,
        }
    }

    /// Stamp the input kinds `event` belongs to.
    pub(super) fn note(&mut self, event: &WindowEvent) {
        let now = Instant::now();
        match event {
            WindowEvent::KeyboardInput { .. } => self.keyboard = now,
            WindowEvent::CursorMoved { .. } | WindowEvent::MouseWheel { .. } => self.mouse = now,
            WindowEvent::MouseInput { state, .. } => {
                self.mouse = now;
                if state.is_pressed() {
                    self.mouse_press = now;
                }
            }
            _ => {}
        }
    }

    /// Idle time at `now`, in client ticks.
    pub(super) fn idle(&self, now: Instant) -> InputIdle {
        let ticks = |since: Instant| {
            let elapsed = now.saturating_duration_since(since);
            u32::try_from(elapsed.as_millis() / CLIENT_TICK.as_millis()).unwrap_or(u32::MAX)
        };
        InputIdle {
            keyboard: ticks(self.keyboard),
            mouse: ticks(self.mouse),
            mouse_press: ticks(self.mouse_press),
        }
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn idle_time_counts_client_ticks_since_each_stamp() {
        let start = Instant::now();
        let activity = InputActivity {
            keyboard: start,
            mouse: start + Duration::from_millis(500),
            mouse_press: start + Duration::from_millis(900),
        };
        let idle = activity.idle(start + Duration::from_secs(1));
        assert_eq!(idle.keyboard, 50);
        assert_eq!(idle.mouse, 25);
        assert_eq!(idle.mouse_press, 5);
    }

    #[test]
    fn a_stamp_in_the_future_reads_as_zero() {
        let now = Instant::now();
        let activity = InputActivity {
            keyboard: now + Duration::from_secs(1),
            mouse: now,
            mouse_press: now,
        };
        assert_eq!(activity.idle(now).keyboard, 0);
    }
}
