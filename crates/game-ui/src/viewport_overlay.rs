//! Disposable full-viewport widget color, matching injected-240 classGP.method5624.
use crate::Rect;

/// Premultiplied encoded RGBA accumulated beneath the sampled game UI.
#[derive(Debug, Default, Clone, Copy, PartialEq, Eq)]
pub struct ViewportOverlay(u32);
impl ViewportOverlay {
    /// Source uses 256-based color weights and the source 255-based alpha product.
    /// `transparency == 0` is painted normally; only translucent rectangles use this path.
    /// Returns whether this filled rectangle was absorbed and should skip ordinary painting.
    pub fn absorb(
        &mut self,
        viewport: Option<Rect>,
        bounds: Rect,
        filled: bool,
        color: u32,
        transparency: u8,
    ) -> bool {
        if viewport != Some(bounds) || !filled || transparency == 0 {
            return false;
        }
        let inverse = u32::from(transparency);
        let alpha = 256 - inverse;
        let weighted = |rgb: u32, weight: u32| {
            (((rgb & 0x00ff_00ff) * weight) >> 8 & 0x00ff_00ff)
                + (((rgb & 0x0000_ff00) * weight) >> 8 & 0x0000_ff00)
        };
        let rgb = weighted(color, alpha) + weighted(self.0, inverse);
        let previous_alpha = self.0 >> 24;
        // Java's unsigned shift consumes the low 32 bits of this wrapping product.
        let alpha = alpha
            + (previous_alpha
                .wrapping_mul(255 - alpha)
                .wrapping_mul(32_897)
                >> 23);
        self.0 = (alpha << 24) | rgb;
        true
    }

    /// Premultiplied bytes; these must not be multiplied by alpha again by the renderer.
    #[must_use]
    pub fn rgba(self) -> [u8; 4] {
        let [alpha, red, green, blue] = self.0.to_be_bytes();
        [red, green, blue, alpha]
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn source_widget_overlay_selection_accumulation_and_reset() {
        let viewport = Rect::new(10., 20., 512., 334.);
        let mut overlay = ViewportOverlay::default();
        assert!(!overlay.absorb(None, viewport, true, 0x00ff_0000, 128));
        assert!(!overlay.absorb(Some(viewport), viewport, false, 0x00ff_0000, 128));
        assert!(!overlay.absorb(Some(viewport), viewport, true, 0x00ff_0000, 0));
        assert!(!overlay.absorb(
            Some(viewport),
            Rect::new(11., 20., 512., 334.),
            true,
            0x00ff_0000,
            128
        ));
        assert_eq!(overlay.rgba(), [0; 4]);
        assert!(overlay.absorb(Some(viewport), viewport, true, 0x00ff_0000, 128));
        assert_eq!(overlay.rgba(), [127, 0, 0, 128]);
        assert!(overlay.absorb(Some(viewport), viewport, true, 0x0000_00ff, 128));
        assert_eq!(overlay.rgba(), [63, 0, 127, 191]);
        overlay = ViewportOverlay::default();
        assert_eq!(overlay.rgba(), [0; 4]);
        assert!(overlay.absorb(Some(viewport), viewport, true, 0x00ff_ffff, 1));
        assert_eq!(overlay.rgba(), [254, 254, 254, 255]);
    }
}
