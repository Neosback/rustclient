//! ABI 0.1 vocabulary retained without shape or serialization changes.

/// Logical reference size of the overlay coordinate space in interface pixels.
pub const OVERLAY_WIDTH: u16 = 765;
/// Logical reference height of the overlay coordinate space in interface pixels.
pub const OVERLAY_HEIGHT: u16 = 503;

/// Committed local player position exposed to granted plugins.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct PlayerPosition {
    /// World tile x.
    pub x: i32,
    /// World tile y.
    pub y: i32,
    /// World plane.
    pub plane: u8,
}

/// One overlay text command in the [`OVERLAY_WIDTH`] x [`OVERLAY_HEIGHT`] logical space.
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct OverlayLine {
    /// Text drawn with the client's interface font.
    pub text: String,
    /// Logical x in interface pixels.
    pub x: u16,
    /// Logical y in interface pixels.
    pub y: u16,
    /// Packed `AARRGGBB`.
    pub argb: u32,
}

/// One committed-gameplay event delivered in batches.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum PluginEvent {
    /// A session began and state is available.
    Started,
    /// One committed gameplay tick.
    Tick(u64),
    /// The session ended; the plugin instance is stopped.
    Stopped,
}

/// Diagnostic severity.
#[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord, Hash)]
pub enum LogLevel {
    /// Fine-grained tracing.
    Trace,
    /// Developer diagnostics.
    Debug,
    /// Normal lifecycle information.
    Info,
    /// Recoverable problems.
    Warn,
    /// Failures.
    Error,
}

impl LogLevel {
    /// Short lowercase name for logs.
    #[must_use]
    pub const fn as_str(self) -> &'static str {
        match self {
            Self::Trace => "trace",
            Self::Debug => "debug",
            Self::Info => "info",
            Self::Warn => "warn",
            Self::Error => "error",
        }
    }
}
