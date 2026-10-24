//! Revision-neutral path acceleration inputs shared by cache adapters and live actor scheduling.

/// Backlog thresholds that raise ordinary queued movement from the base step of four fine units.
///
/// The scheduler uses strict `>` comparisons: a backlog above `step_six_after` selects six fine
/// units, and a backlog above `step_eight_after` selects eight. Traversal scaling (run/crawl),
/// turning and catch-up remain separate concerns.
#[derive(Debug, Clone, Copy, PartialEq, Eq, Hash)]
pub struct PathAccelerationPolicy {
    pub step_six_after: u8,
    pub step_eight_after: u8,
}

impl PathAccelerationPolicy {
    /// Normal revision-240 actor thresholds: backlog > 2 => 6, backlog > 3 => 8.
    pub const STANDARD: Self = Self {
        step_six_after: 2,
        step_eight_after: 3,
    };

    /// Revision-240 NPC opcode-109 alternate thresholds: backlog > 1 => 6, backlog > 2 => 8.
    pub const EARLY: Self = Self {
        step_six_after: 1,
        step_eight_after: 2,
    };
}
