use rustclient_platform::{Clipboard, ClipboardError, ClipboardErrorKind};

pub struct NativeClipboard(arboard::Clipboard);
impl NativeClipboard {
    /// # Errors
    /// Reports unavailable native clipboard services.
    pub fn new() -> Result<Self, ClipboardError> {
        arboard::Clipboard::new().map(Self).map_err(|error| {
            ClipboardError::new(
                ClipboardErrorKind::Unavailable,
                format!("open clipboard: {error}"),
            )
        })
    }
}
impl Clipboard for NativeClipboard {
    fn read_text(&mut self) -> Result<String, ClipboardError> {
        self.0.get_text().map_err(|error| {
            ClipboardError::new(
                ClipboardErrorKind::Backend,
                format!("read clipboard: {error}"),
            )
        })
    }
    fn write_text(&mut self, text: String) -> Result<(), ClipboardError> {
        self.0.set_text(text).map_err(|error| {
            ClipboardError::new(
                ClipboardErrorKind::Backend,
                format!("write clipboard: {error}"),
            )
        })
    }
}
