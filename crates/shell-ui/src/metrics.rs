//! Logical pixels resolved by the pinned `RuneLite`/`FlatLaf` shell oracle.
pub struct SidebarMetrics;
impl SidebarMetrics {
    pub const INNER_PANEL: u32 = 225;
    pub const SCROLL_ALLOWANCE: u32 = 17;
    pub const PANEL: u32 = Self::INNER_PANEL + Self::SCROLL_ALLOWANCE;
    /// 30px resolved tab plus the 1px content separator, not just its 16+5+5 icon inset.
    pub const RAIL: u32 = 31;
    pub const OPEN: u32 = Self::PANEL + Self::RAIL;
    pub const TAB_HEIGHT: f32 = 26.;
    pub const ICON: f32 = 16.;
    pub const PADDING: f32 = 10.;
    pub const HEADER_TAB_HEIGHT: f32 = 34.;
    pub const HEADER_GAP: f32 = 7.;
    pub const FONT: f32 = 16.;
    /// Round each logical extent once; callers use this split for clipping and pointer origins.
    #[must_use]
    #[expect(
        clippy::cast_possible_truncation,
        clippy::cast_sign_loss,
        reason = "platform-validated nonnegative extents and display scale"
    )]
    pub fn physical(logical: f64, scale: f64) -> u32 {
        (logical * scale).round() as u32
    }
}
