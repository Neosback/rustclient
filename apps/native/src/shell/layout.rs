//! Window-system independent canvas sizing. Shell requests never become user game resizes.
use rustclient_shell_ui::SidebarMetrics;
use std::time::{Duration, Instant};

const SETTLE: Duration = Duration::from_millis(150);
const MAX_WAIT: Duration = Duration::from_millis(750);

#[derive(Clone, Copy, Debug)]
struct Request {
    target: u32,
    observed: u32,
    changed: Instant,
    deadline: Instant,
    scale: f64,
}

#[derive(Debug)]
pub(super) struct CanvasLayout {
    logical_game: f64,
    request: Option<Request>,
}
impl Default for CanvasLayout {
    fn default() -> Self {
        Self {
            logical_game: 765.,
            request: None,
        }
    }
}
impl CanvasLayout {
    pub fn target(&mut self, actual: u32, scale: f64, chrome: u32, now: Instant) -> u32 {
        let target = SidebarMetrics::physical(self.logical_game, scale)
            + SidebarMetrics::physical(f64::from(chrome), scale);
        self.request = Some(Request {
            target,
            observed: actual,
            changed: now,
            deadline: now + MAX_WAIT,
            scale,
        });
        target
    }
    pub fn cancel(&mut self) {
        self.request = None;
    }
    pub fn pending(&self) -> bool {
        self.request.is_some()
    }
    pub fn observe(&mut self, actual: u32, scale: f64, chrome: u32, now: Instant) {
        if actual == 0 {
            return;
        }
        if let Some(request) = &mut self.request {
            if request.scale.to_bits() != scale.to_bits() {
                // The OS scales the window during a monitor move. Keep the logical canvas target.
                {
                    request.target = SidebarMetrics::physical(self.logical_game, scale)
                        + SidebarMetrics::physical(f64::from(chrome), scale);
                }
                request.scale = scale;
            }
            if actual != request.observed {
                request.observed = actual;
                request.changed = now;
            }
            // Keep a short quiet period even after the target arrives: platforms may queue older
            // intermediate events behind it. The hard deadline also settles screen-clamped requests.
            if now < request.deadline
                && (actual.abs_diff(request.target) > 2
                    || now.duration_since(request.changed) < SETTLE)
            {
                return;
            }
            self.request = None;
        }
        let chrome = SidebarMetrics::physical(f64::from(chrome), scale);
        self.logical_game = f64::from(actual.saturating_sub(chrome).max(1)) / scale;
    }
    pub fn game_width(&self, actual: u32, scale: f64, chrome: u32, preserve: bool) -> u32 {
        if preserve && self.pending() {
            // During opening, clip chrome beyond the surface; never grow the canvas into the
            // newly available sidebar space before the window resize has settled.
            (SidebarMetrics::physical(self.logical_game, scale))
                .min(actual)
                .max(1)
        } else {
            actual
                .saturating_sub(SidebarMetrics::physical(f64::from(chrome), scale))
                .max(1)
        }
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn shell_resizes_preserve_canvas_across_scales_and_rapid_toggles() {
        for scale in [1., 1.25, 1.5, 1.75, 2.] {
            let now = Instant::now();
            let mut layout = CanvasLayout::default();
            let old = layout.target(765, scale, 0, now);
            let target = layout.target(old, scale, SidebarMetrics::OPEN, now);
            for actual in [old, old.midpoint(target), target, target - 3, target] {
                layout.observe(actual, scale, SidebarMetrics::OPEN, now);
                assert_eq!(
                    layout.game_width(actual, scale, SidebarMetrics::OPEN, true),
                    old
                );
            }
            let rail = layout.target(target, scale, SidebarMetrics::RAIL, now);
            layout.observe(rail, scale, SidebarMetrics::RAIL, now + SETTLE);
            assert_eq!(
                layout.game_width(rail, scale, SidebarMetrics::RAIL, true),
                old
            );
            let closed = layout.target(rail, scale, 0, now + SETTLE);
            assert_eq!(closed, old);
        }
    }
    #[test]
    fn settled_fractional_scale_toggles_never_accumulate_rounding_drift() {
        for scale in [1., 1.25, 1.5, 1.75, 2.] {
            let mut now = Instant::now();
            let mut layout = CanvasLayout::default();
            let game = layout.target(765, scale, 0, now);
            for _ in 0..25 {
                for chrome in [SidebarMetrics::OPEN, SidebarMetrics::RAIL, 0] {
                    let target = layout.target(game, scale, chrome, now);
                    now += MAX_WAIT;
                    layout.observe(target, scale, chrome, now);
                    assert!(!layout.pending());
                    assert_eq!(layout.game_width(target, scale, chrome, true), game);
                }
            }
        }
    }
    #[test]
    fn clamped_requests_settle_once_and_user_resizes_are_then_observed() {
        let now = Instant::now();
        let mut layout = CanvasLayout::default();
        layout.target(765, 1., SidebarMetrics::OPEN, now);
        layout.observe(1000, 1., SidebarMetrics::OPEN, now + MAX_WAIT);
        assert!(!layout.pending());
        assert_eq!(layout.game_width(1000, 1., SidebarMetrics::OPEN, true), 727);
        layout.observe(1100, 1., SidebarMetrics::OPEN, now + MAX_WAIT);
        assert_eq!(layout.target(1100, 1., 0, now + MAX_WAIT), 827);
    }
    #[test]
    fn fixed_window_and_dpi_changes_share_the_same_split() {
        let now = Instant::now();
        let mut layout = CanvasLayout::default();
        layout.target(765, 1., SidebarMetrics::OPEN, now);
        layout.observe(2076, 2., SidebarMetrics::OPEN, now);
        assert_eq!(
            layout.game_width(2076, 2., SidebarMetrics::OPEN, true),
            1530
        );
        assert_eq!(
            layout.game_width(2076, 2., SidebarMetrics::OPEN, false),
            1530
        );
        layout.cancel();
        assert_eq!(layout.game_width(765, 1., SidebarMetrics::OPEN, false), 492);
    }
}
