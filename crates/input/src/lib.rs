//! Platform-neutral title input. Adapters supply text and logical key intents.
#![forbid(unsafe_code)]

/// Editing/navigation keys accepted by the local credential form.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum EditKey {
    /// Delete the last character.
    Backspace,
    /// Move focus; reverse when Shift is held.
    Tab {
        /// Whether Shift is held.
        reverse: bool,
    },
    /// Activate the default action.
    Enter,
    /// Return to welcome.
    Escape,
}
