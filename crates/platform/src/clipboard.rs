//! Explicit clipboard capability supplied to trusted UI composition, never to plugins by default.
use std::fmt;

#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum ClipboardErrorKind {
    Unavailable,
    PermissionDenied,
    Backend,
}
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct ClipboardError {
    kind: ClipboardErrorKind,
    message: String,
}
impl ClipboardError {
    #[must_use]
    pub fn new(kind: ClipboardErrorKind, message: impl Into<String>) -> Self {
        Self {
            kind,
            message: message.into(),
        }
    }
    #[must_use]
    pub fn kind(&self) -> ClipboardErrorKind {
        self.kind
    }
}
impl fmt::Display for ClipboardError {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        f.write_str(&self.message)
    }
}
impl std::error::Error for ClipboardError {}
pub trait Clipboard {
    /// # Errors
    /// Reports unavailable/denied native clipboard access without exposing its contents.
    fn read_text(&mut self) -> Result<String, ClipboardError>;
    /// # Errors
    /// Reports unavailable/denied native clipboard access.
    fn write_text(&mut self, text: String) -> Result<(), ClipboardError>;
}
