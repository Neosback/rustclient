//! Validated scene brightness preference, independent of renderer gamma arithmetic.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct ScreenBrightness(u8);
impl ScreenBrightness {
    #[must_use]
    pub const fn new(percent: u8) -> Option<Self> {
        if percent <= 100 {
            Some(Self(percent))
        } else {
            None
        }
    }
    #[must_use]
    pub const fn percent(self) -> u8 {
        self.0
    }
}
impl Default for ScreenBrightness {
    fn default() -> Self {
        Self(40)
    }
}
