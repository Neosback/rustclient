//! Pure local tracker derived from `RuneLite` XpStateSingle/XpPauseStateSingle (BSD-2, NOTICE).
use serde::{Deserialize, Serialize};
#[derive(Debug, Clone, Copy, Default)]
pub struct PausePolicy {
    pub logged_out: bool,
    pub pause_after_ms: u64,
    pub reset_rate_after_ms: u64,
}
/// Derived state for one skill. Its caller supplies XP truth, goals and elapsed time.
#[derive(Debug, Clone, Serialize, Deserialize)]
pub struct SkillTracker {
    start_xp: i64,
    current_xp: i64,
    rate_xp: i64,
    actions: u64,
    rate_actions: u64,
    history: [i64; 10],
    history_cursor: usize,
    active_ms: u64,
    idle_ms: u64,
    manual_pause: bool,
    rate_reset: bool,
    goal: [i64; 2],
}
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct XpSummary {
    pub gained: i64,
    pub xp_per_hour: u64,
    pub actions: u64,
    pub actions_per_hour: u64,
    pub remaining: i64,
    pub actions_remaining: Option<u64>,
    pub seconds_remaining: Option<u64>,
}
impl SkillTracker {
    #[must_use]
    pub fn new(xp: u32) -> Self {
        let xp = i64::from(xp);
        Self {
            start_xp: xp,
            current_xp: xp,
            rate_xp: 0,
            actions: 0,
            rate_actions: 0,
            history: [0; 10],
            history_cursor: 0,
            active_ms: 0,
            idle_ms: 0,
            manual_pause: false,
            rate_reset: false,
            goal: [xp, xp],
        }
    }
    #[must_use]
    pub fn current_xp(&self) -> u32 {
        u32::try_from(self.current_xp).unwrap_or(0)
    }
    /// Returns true for a gain. Lower XP reinitializes, as negative-XP lamps do in `RuneLite`.
    pub fn update(&mut self, xp: u32) -> bool {
        let value = xp;
        let xp = i64::from(xp);
        if xp < self.current_xp {
            *self = Self::new(value);
            return false;
        }
        let gain = xp - self.current_xp;
        if gain == 0 {
            return false;
        }
        if self.actions == 0 {
            self.history.fill(gain);
        } else {
            self.history[self.history_cursor] = gain;
        }
        self.history_cursor = (self.history_cursor + 1) % 10;
        self.current_xp = xp;
        self.rate_xp += gain;
        self.actions += 1;
        self.rate_actions += 1;
        self.idle_ms = 0;
        self.manual_pause = false;
        self.rate_reset = false;
        true
    }
    pub fn advance(&mut self, elapsed_ms: u64, policy: PausePolicy) {
        if policy.logged_out {
            return;
        }
        let active = if self.manual_pause {
            0
        } else if policy.pause_after_ms == 0 {
            elapsed_ms
        } else {
            elapsed_ms.min(policy.pause_after_ms.saturating_sub(self.idle_ms))
        };
        if self.rate_xp > 0 {
            self.active_ms = self.active_ms.saturating_add(active);
        }
        self.idle_ms = self.idle_ms.saturating_add(elapsed_ms);
        if !self.rate_reset
            && policy.reset_rate_after_ms > 0
            && self.idle_ms >= policy.reset_rate_after_ms
        {
            self.reset_rate();
            self.rate_reset = true;
        }
    }
    pub fn pause(&mut self) {
        self.manual_pause = true;
    }
    pub fn resume(&mut self) {
        self.manual_pause = false;
        self.idle_ms = 0;
    }
    pub fn reset_rate(&mut self) {
        self.rate_xp = 0;
        self.rate_actions = 0;
        self.active_ms = 0;
        self.idle_ms = 0;
    }
    /// Goal selection is supplied explicitly by the host; no guessed varp/level data.
    /// # Errors
    /// Rejects negative, inverted, exceeded or out-of-range goals.
    pub fn set_goal(&mut self, start: i64, end: i64) -> Result<(), String> {
        if start < 0 || end <= start || end < self.current_xp || end > i64::from(u32::MAX) {
            return Err("XP goal must include current XP and have positive width".into());
        }
        self.goal = [start, end];
        Ok(())
    }
    #[must_use]
    pub fn summary(&self) -> XpSummary {
        let seconds = (self.active_ms / 1000).max(60);
        let remaining = (self.goal[1] - self.current_xp).max(0);
        let history_total = self.history.iter().sum::<i64>();
        let positive = |value: i64| u64::try_from(value).unwrap_or(0);
        XpSummary {
            gained: self.current_xp - self.start_xp,
            xp_per_hour: positive(self.rate_xp).saturating_mul(3600) / seconds,
            actions: self.actions,
            actions_per_hour: self.rate_actions.saturating_mul(3600) / seconds,
            remaining,
            actions_remaining: (history_total > 0).then(|| {
                positive(remaining)
                    .saturating_mul(10)
                    .div_ceil(positive(history_total))
            }),
            seconds_remaining: (self.rate_xp > 0)
                .then(|| positive(remaining).saturating_mul(seconds) / positive(self.rate_xp)),
        }
    }
    /// Persist through the caller's mediated plugin storage, explicitly bound to its account key.
    /// # Errors
    /// Rejects an empty identity and serialization errors.
    pub fn save(&self, account: &str) -> Result<String, String> {
        if account.is_empty() {
            return Err("stable account key required".into());
        }
        serde_json::to_string(&(1_u8, account, self)).map_err(|e| e.to_string())
    }
    /// Restore only for the exact account and XP snapshot. Offline gains become a fresh baseline.
    /// # Errors
    /// Rejects wrong account/version, corrupt/unbounded state and stale XP.
    pub fn restore(json: &str, account: &str, xp: u32) -> Result<Self, String> {
        let xp = i64::from(xp);
        if json.len() > 8192 || account.is_empty() {
            return Err("invalid XP save envelope".into());
        }
        let (version, key, state): (u8, String, Self) =
            serde_json::from_str(json).map_err(|e| e.to_string())?;
        if version != 1
            || key != account
            || state.current_xp != xp
            || state.start_xp < 0
            || state.start_xp > xp
            || state.rate_xp < 0
            || state.rate_xp > xp - state.start_xp
            || state.history_cursor >= 10
            || state
                .history
                .iter()
                .any(|v| *v < 0 || *v > i64::from(u32::MAX))
            || state.actions > u64::try_from(xp - state.start_xp).unwrap_or(0)
            || state.rate_actions > u64::try_from(state.rate_xp).unwrap_or(0)
            || state.rate_actions > state.actions
            || state.goal[0] < 0
            || state.goal[1] > i64::from(u32::MAX)
            || state.goal[0] > state.goal[1]
        {
            return Err("incompatible XP save".into());
        }
        Ok(state)
    }
}
