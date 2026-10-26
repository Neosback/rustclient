//! Stretched-mode layout: how big the logical game canvas is and where it lands on screen.
//!
//! Ported from the injected client's `StretchedModeMixin` (`getRealDimensions`,
//! `getStretchedDimensions`; BSD-2 `RuneLite`). All sizes here are logical (device independent)
//! pixels, exactly as the source computes them from the canvas parent's size; [`StretchLayout::canvas`]
//! converts the result to framebuffer pixels.

use crate::StretchedCanvas;

/// `Constants.GAME_FIXED_WIDTH` and `GAME_FIXED_HEIGHT`.
pub const FIXED_SIZE: (u32, u32) = (765, 503);

/// What the user asked for (`StretchedModeConfig`).
#[derive(Debug, Clone, Copy, PartialEq)]
pub struct StretchSettings {
    /// Keep the canvas aspect ratio, leaving the remainder empty.
    pub keep_aspect_ratio: bool,
    /// Use whole-number scale factors only.
    pub integer_scaling: bool,
    /// `Client.setScalingFactor(percent)`: the resizable canvas is the window divided by
    /// `1 + percent / 100` before it is stretched back up.
    pub scaling_percent: i32,
}

/// The resolved layout for one window size.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct StretchLayout {
    /// The logical canvas the game lays itself out in (`getRealDimensions`).
    pub real: (u32, u32),
    /// The size that canvas is drawn at (`getStretchedDimensions`).
    pub stretched: (u32, u32),
    /// Left edge of the stretched canvas; it is centered horizontally and top aligned
    /// (`Canvas.setLocation`).
    pub offset_x: u32,
}

/// Truncate like Java's `(int)` cast on a non-negative double.
#[expect(
    clippy::cast_possible_truncation,
    clippy::cast_sign_loss,
    reason = "reference (int) casts of bounded non-negative window extents"
)]
fn int(value: f64) -> u32 {
    value.max(0.) as u32
}

impl StretchLayout {
    /// Lay the game out inside a parent of `parent` logical pixels.
    ///
    /// `resizable` is whether the game is in a resizable interface mode; the fixed interface
    /// always has the fixed canvas size.
    #[must_use]
    pub fn compute(settings: StretchSettings, resizable: bool, parent: (u32, u32)) -> Self {
        let parent = (parent.0.max(1), parent.1.max(1));
        let real = if resizable {
            let factor = 1. + f64::from(settings.scaling_percent) / 100.;
            let (mut width, mut height) = (
                int(f64::from(parent.0) / factor),
                int(f64::from(parent.1) / factor),
            );
            if width < FIXED_SIZE.0 || height < FIXED_SIZE.1 {
                let fit = (f64::from(parent.0) / f64::from(FIXED_SIZE.0))
                    .min(f64::from(parent.1) / f64::from(FIXED_SIZE.1));
                width = int(f64::from(parent.0) / fit);
                height = int(f64::from(parent.1) / fit);
            }
            (width.max(1), height.max(1))
        } else {
            FIXED_SIZE
        };
        let (mut width, mut height) = parent;
        if settings.keep_aspect_ratio {
            let aspect = f64::from(real.0) / f64::from(real.1);
            let fitted_width = int(f64::from(height) * aspect);
            if fitted_width > width {
                height = int(f64::from(width) / aspect);
            } else {
                width = fitted_width;
            }
        }
        if settings.integer_scaling {
            if width > real.0 {
                width -= width % real.0;
            }
            if height > real.1 {
                height -= height % real.1;
            }
        }
        let (width, height) = (width.max(1), height.max(1));
        Self {
            real,
            stretched: (width, height),
            offset_x: parent.0.saturating_sub(width) / 2,
        }
    }

    /// The framebuffer rectangle for a window with `scale` physical pixels per logical pixel.
    #[must_use]
    #[expect(
        clippy::cast_possible_truncation,
        reason = "framebuffer extents are bounded window-system values"
    )]
    pub fn canvas(self, scale: f64) -> StretchedCanvas {
        StretchedCanvas {
            canvas_height: self.real.1,
            x: (f64::from(self.offset_x) * scale).round() as f32,
            width: (f64::from(self.stretched.0) * scale).round() as f32,
            height: (f64::from(self.stretched.1) * scale).round() as f32,
        }
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    const PLAIN: StretchSettings = StretchSettings {
        keep_aspect_ratio: false,
        integer_scaling: false,
        scaling_percent: 50,
    };

    #[test]
    fn the_fixed_interface_keeps_its_canvas_and_fills_the_parent() {
        let layout = StretchLayout::compute(PLAIN, false, (1600, 900));
        assert_eq!(layout.real, FIXED_SIZE);
        assert_eq!(layout.stretched, (1600, 900));
        assert_eq!(layout.offset_x, 0);
    }

    #[test]
    fn keeping_the_aspect_ratio_letterboxes_and_centers() {
        let settings = StretchSettings {
            keep_aspect_ratio: true,
            ..PLAIN
        };
        // 765:503 at 800 high is 1216 wide, centered in 1600.
        let wide = StretchLayout::compute(settings, false, (1600, 800));
        assert_eq!(wide.stretched, (1216, 800));
        assert_eq!(wide.offset_x, 192);
        // A tall window is limited by width instead.
        let tall = StretchLayout::compute(settings, false, (765, 900));
        assert_eq!(tall.stretched, (765, 503));
        assert_eq!(tall.offset_x, 0);
    }

    #[test]
    fn integer_scaling_rounds_each_axis_down_to_a_multiple_of_the_canvas() {
        let settings = StretchSettings {
            integer_scaling: true,
            ..PLAIN
        };
        let layout = StretchLayout::compute(settings, false, (1600, 1006));
        assert_eq!(layout.stretched, (1530, 1006));
        assert_eq!(layout.offset_x, 35);
        // Below one canvas there is nothing to round.
        assert_eq!(
            StretchLayout::compute(settings, false, (700, 400)).stretched,
            (700, 400)
        );
    }

    #[test]
    fn a_resizable_canvas_is_the_parent_over_the_scaling_factor() {
        let layout = StretchLayout::compute(PLAIN, true, (1920, 1080));
        // 50% means a factor of 1.5.
        assert_eq!(layout.real, (1280, 720));
        assert_eq!(layout.stretched, (1920, 1080));
        let none = StretchSettings {
            scaling_percent: 0,
            ..PLAIN
        };
        assert_eq!(
            StretchLayout::compute(none, true, (1920, 1080)).real,
            (1920, 1080)
        );
    }

    #[test]
    fn a_resizable_canvas_never_shrinks_below_the_fixed_size_ratio() {
        let layout = StretchLayout::compute(PLAIN, true, (700, 400));
        // The factor is replaced by whatever fits the fixed canvas: 400 / 503 per axis.
        assert_eq!(layout.real.0, 880);
        assert!(layout.real.1 >= 502);
        assert_eq!(layout.stretched, (700, 400));
    }

    #[test]
    fn the_framebuffer_rectangle_scales_with_the_window() {
        let settings = StretchSettings {
            keep_aspect_ratio: true,
            ..PLAIN
        };
        let canvas = StretchLayout::compute(settings, false, (1600, 800)).canvas(2.);
        assert_eq!(
            (canvas.x, canvas.width, canvas.height, canvas.canvas_height),
            (384., 2432., 1600., 503)
        );
    }

    #[test]
    fn the_viewport_transform_maps_canvas_corners_and_back() {
        let settings = StretchSettings {
            keep_aspect_ratio: true,
            ..PLAIN
        };
        let layout = StretchLayout::compute(settings, false, (1600, 800));
        let viewport = crate::TitleViewport {
            canvas_width: layout.real.0,
            physical_width: 1600,
            physical_height: 800,
            scale: 1.,
            stretch: Some(layout.canvas(1.)),
        };
        // Canvas origin lands on the letterbox edge, the far corner on the stretched edge.
        let [left, top] = viewport.ndc(0., 0.);
        assert!((left - (192. / 1600. * 2. - 1.)).abs() < 1e-5);
        assert!((top - 1.).abs() < 1e-5);
        let [right, bottom] = viewport.ndc(765., 503.);
        assert!((right - ((192. + 1216.) / 1600. * 2. - 1.)).abs() < 1e-5);
        assert!((bottom + 1.).abs() < 1e-5);
        let (x, y) = viewport.pointer(192. + 1216. / 2., 400.);
        assert!((x - 382.5).abs() < 1e-3 && (y - 251.5).abs() < 1e-3);
    }
}
