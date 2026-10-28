//! One presentation clock for GPU policy and the optional FPS plugin.
//! Game cycles keep their own 20 ms deadline; rendering never advances semantic time.

use std::time::{Duration, Instant};

#[derive(Debug, Default)]
pub(super) struct FrameLimit {
    last: Option<Instant>,
}

impl FrameLimit {
    /// The next allowed presentation, or no deadline when uncapped/not yet presented.
    pub(super) fn deadline(&self, limit: Option<u32>) -> Option<Instant> {
        self.last?.checked_add(interval(limit?))
    }

    /// Whether a frame may be presented at `now` when at most `limit` frames per second are allowed.
    pub(super) fn allow(&mut self, limit: Option<u32>, now: Instant) -> bool {
        let Some(fps) = limit else {
            self.last = Some(now);
            return true;
        };
        let interval = interval(fps);
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

fn interval(fps: u32) -> Duration {
    Duration::from_nanos(1_000_000_000_u64.div_ceil(u64::from(fps.max(1))))
}

/// Zero GPU cap means uncapped; the stricter active policy wins.
pub(super) fn combined_limit(gpu: u32, plugin: Option<u32>) -> Option<u32> {
    match (gpu, plugin) {
        (0, None) => None,
        (0, Some(plugin)) => Some(plugin.max(1)),
        (gpu, None) => Some(gpu),
        (gpu, Some(plugin)) => Some(gpu.min(plugin.max(1))),
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

#[cfg(test)]
mod gpu_tests {
    use super::*;
    #[test]
    fn stricter_gpu_or_plugin_policy_wins() {
        assert_eq!(combined_limit(50, Some(120)), Some(50));
        assert_eq!(combined_limit(120, Some(30)), Some(30));
        assert_eq!(combined_limit(0, Some(30)), Some(30));
        assert_eq!(combined_limit(0, None), None);
    }
    #[test]
    fn deadlines_follow_changes_and_never_catch_up_missed_frames() {
        let mut clock = FrameLimit::default();
        let start = Instant::now();
        assert_eq!(clock.deadline(Some(60)), None);
        assert!(clock.allow(Some(60), start));
        assert_eq!(
            clock.deadline(Some(60)),
            Some(start + Duration::from_nanos(16_666_667))
        );
        assert!(!clock.allow(Some(60), start + Duration::from_micros(16_666)));
        assert!(clock.allow(Some(60), start + Duration::from_secs(2)));
        assert_eq!(
            clock.deadline(Some(50)),
            Some(start + Duration::from_millis(2020))
        );
        assert_eq!(clock.deadline(None), None);
    }
}
