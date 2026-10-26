//! Frame pacing for a plugin's frame limit (`FpsPlugin`'s draw listener).
//!
//! The source sleeps after each paint to hold a target rate. The shell instead declines to present a
//! frame that would come sooner than one interval after the last presented one; the redraw requests
//! that follow every 20 ms game cycle (and input) pick the schedule back up.

use std::time::{Duration, Instant};

#[derive(Debug, Default)]
pub(super) struct FrameLimit {
    last: Option<Instant>,
}

impl FrameLimit {
    /// Whether a frame may be presented at `now` when at most `limit` frames per second are allowed.
    pub(super) fn allow(&mut self, limit: Option<u32>, now: Instant) -> bool {
        let Some(fps) = limit else {
            self.last = Some(now);
            return true;
        };
        let interval = Duration::from_micros(1_000_000 / u64::from(fps.max(1)));
        if self
            .last
            .is_some_and(|last| now.saturating_duration_since(last) < interval)
        {
            return false;
        }
        self.last = Some(now);
        true
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn without_a_limit_every_frame_is_presented() {
        let mut pacer = FrameLimit::default();
        let start = Instant::now();
        assert!(pacer.allow(None, start));
        assert!(pacer.allow(None, start));
    }

    #[test]
    fn a_limit_spaces_frames_by_at_least_one_interval() {
        let mut pacer = FrameLimit::default();
        let start = Instant::now();
        // 10 fps is a 100 ms interval.
        assert!(pacer.allow(Some(10), start));
        assert!(!pacer.allow(Some(10), start + Duration::from_millis(60)));
        assert!(!pacer.allow(Some(10), start + Duration::from_millis(99)));
        assert!(pacer.allow(Some(10), start + Duration::from_millis(100)));
        assert!(!pacer.allow(Some(10), start + Duration::from_millis(150)));
    }

    #[test]
    fn a_skipped_frame_does_not_move_the_schedule() {
        let mut pacer = FrameLimit::default();
        let start = Instant::now();
        assert!(pacer.allow(Some(5), start));
        for step in 1..10 {
            assert!(!pacer.allow(Some(5), start + Duration::from_millis(step * 20)));
        }
        assert!(pacer.allow(Some(5), start + Duration::from_millis(200)));
    }

    #[test]
    fn lifting_the_limit_resumes_at_once() {
        let mut pacer = FrameLimit::default();
        let start = Instant::now();
        assert!(pacer.allow(Some(1), start));
        assert!(pacer.allow(None, start + Duration::from_millis(1)));
    }
}
