//! Revision-neutral action-sequence timing needed by semantic actor scheduling.

/// Immutable playback timing resolved from a sequence definition.
///
/// Cache adapters translate revision-specific sequence encodings into this vocabulary before
/// `GameState` consumes them. The live actor scheduler remains cache-free.
#[derive(Debug, Clone, PartialEq, Eq, Hash)]
pub enum SequenceTiming {
    /// Legacy frame sequence. `frame_delays` is indexed by the semantic action frame and
    /// `frame_step` is the loop rewind count used after the final frame.
    Legacy {
        frame_delays: Vec<u16>,
        frame_step: Option<u16>,
    },
    /// Cached-model/skeletal sequence. Revision 240 does not apply the legacy frame-boundary gate
    /// to forced-movement interpolation for this sequence family.
    CachedModel {
        range: Option<(u16, u16)>,
        frame_step: Option<u16>,
    },
}

impl SequenceTiming {
    #[must_use]
    pub const fn is_cached_model(&self) -> bool {
        matches!(self, Self::CachedModel { .. })
    }

    #[must_use]
    pub fn legacy_frame_delay(&self, frame: u32) -> Option<u16> {
        match self {
            Self::Legacy { frame_delays, .. } => {
                frame_delays.get(usize::try_from(frame).ok()?).copied()
            }
            Self::CachedModel { .. } => None,
        }
    }

    #[must_use]
    pub const fn frame_step(&self) -> Option<u16> {
        match self {
            Self::Legacy { frame_step, .. } | Self::CachedModel { frame_step, .. } => *frame_step,
        }
    }
}
