//! Script-owned minimap preferences: whether the player may zoom it, and how far.
//!
//! The 240 cache's settings scripts read and write these through `MINIMAP_SETZOOMABLE`,
//! `MINIMAP_SETZOOM`, `MINIMAP_GETZOOM` and `MINIMAP_SETICONZOOMLIMIT` (7250-7254). Those ops are
//! not in the pinned 240 deob jar; the semantics follow the 241 reference client (TSPS) and are
//! recorded in `PARITY-DEVIATIONS.tsv`.

/// Zoom is stored in quarter-steps, the resolution of the 241 mouse-wheel zoom.
pub const MINIMAP_ZOOM_QUARTERS_MIN: i32 = 8;
pub const MINIMAP_ZOOM_QUARTERS_MAX: i32 = 32;
pub const MINIMAP_ZOOM_QUARTERS_DEFAULT: i32 = 16;

/// Committed minimap preferences.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct MinimapSettings {
    pub zoomable: bool,
    /// Tile scale in quarter-steps: 16 is the default 4x, the range is 2x to 8x.
    pub zoom_quarters: i32,
    pub icon_zoom_limit: i32,
}
impl Default for MinimapSettings {
    fn default() -> Self {
        Self {
            zoomable: false,
            zoom_quarters: MINIMAP_ZOOM_QUARTERS_DEFAULT,
            icon_zoom_limit: 0,
        }
    }
}
impl MinimapSettings {
    /// What `MINIMAP_GETZOOM` returns: the whole-step zoom.
    #[must_use]
    pub const fn zoom(self) -> i32 {
        self.zoom_quarters / 4
    }
    /// Apply one script change; `true` when anything changed.
    pub fn apply(&mut self, update: MinimapUpdate) -> bool {
        let before = *self;
        match update {
            // Making the minimap zoomable (or not) always restores the default scale.
            MinimapUpdate::Zoomable(zoomable) => {
                self.zoomable = zoomable;
                self.zoom_quarters = MINIMAP_ZOOM_QUARTERS_DEFAULT;
            }
            // A script cannot change the zoom of a minimap it did not make zoomable.
            MinimapUpdate::ZoomQuarters(quarters) if self.zoomable => {
                self.zoom_quarters =
                    quarters.clamp(MINIMAP_ZOOM_QUARTERS_MIN, MINIMAP_ZOOM_QUARTERS_MAX);
            }
            MinimapUpdate::ZoomQuarters(_) => {}
            MinimapUpdate::IconZoomLimit(limit) => self.icon_zoom_limit = limit,
        }
        *self != before
    }
}

/// One change to [`MinimapSettings`].
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum MinimapUpdate {
    Zoomable(bool),
    ZoomQuarters(i32),
    IconZoomLimit(i32),
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn zoom_needs_zoomable_clamps_and_resets_when_zoomable_changes() {
        let mut minimap = MinimapSettings::default();
        assert_eq!(minimap.zoom(), 4);
        assert!(
            !minimap.apply(MinimapUpdate::ZoomQuarters(24)),
            "not zoomable yet"
        );
        assert!(minimap.apply(MinimapUpdate::Zoomable(true)));
        assert!(minimap.apply(MinimapUpdate::ZoomQuarters(100)));
        assert_eq!(minimap.zoom(), 8);
        assert!(minimap.apply(MinimapUpdate::ZoomQuarters(-3)));
        assert_eq!(minimap.zoom(), 2);
        assert!(minimap.apply(MinimapUpdate::ZoomQuarters(22)));
        assert_eq!(
            minimap.zoom(),
            5,
            "wheel steps of a quarter keep the whole-step reading"
        );
        minimap.apply(MinimapUpdate::Zoomable(false));
        assert_eq!(minimap.zoom_quarters, MINIMAP_ZOOM_QUARTERS_DEFAULT);
        assert!(minimap.apply(MinimapUpdate::IconZoomLimit(5)));
        assert!(!minimap.apply(MinimapUpdate::IconZoomLimit(5)));
    }
}
