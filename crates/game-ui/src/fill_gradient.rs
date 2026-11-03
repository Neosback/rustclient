//! Row colours of a gradient-filled widget rectangle.
//!
//! Reference `classYW.method13738` (opaque) and `method13804` (fading): the rectangle is drawn row
//! by row, row `i` of `height` blending `top` into `bottom` with the weights
//! `(65536 - t) >> 8` and `t >> 8` where `t = i * (65536 / height)`, per colour channel.
use rustclient_model::FillGradient;

/// Colour and alpha of one row; `Gradient::rows` yields `None` for a fully transparent row, which
/// the reference skips.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub(crate) struct Row {
    pub color: u32,
    pub alpha: u8,
}

pub(crate) struct Gradient {
    pub kind: FillGradient,
    pub top: u32,
    pub bottom: u32,
    /// The rectangle's own and the second transparency (`0` opaque, `255` clear).
    pub transparency: [u8; 2],
}

impl Gradient {
    /// Rows for a rectangle `height` rows tall; empty for a solid fill or no height.
    pub(crate) fn rows(&self, height: u32) -> impl Iterator<Item = Option<Row>> + '_ {
        let step = 65_536u32.checked_div(height).unwrap_or(0);
        let rows = if self.kind == FillGradient::Solid {
            0
        } else {
            height
        };
        (0..rows).map(move |row| {
            let t = row * step;
            let (w0, w1) = ((65_536 - t) >> 8, t >> 8);
            let channel = |shift: u32| {
                let (a, b) = ((self.top >> shift) & 0xFF, (self.bottom >> shift) & 0xFF);
                (a * w0 + b * w1) >> 8
            };
            let color = channel(16) << 16 | channel(8) << 8 | channel(0);
            let alpha = if self.kind == FillGradient::VerticalFade {
                let [from, to] = self.transparency.map(|t| 255 - u32::from(t));
                let alpha = (from * w0 + to * w1) >> 8;
                if alpha == 0 {
                    return None;
                }
                u8::try_from(alpha).unwrap_or(u8::MAX)
            } else {
                u8::MAX
            };
            Some(Row { color, alpha })
        })
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    fn gradient(kind: FillGradient, transparency: [u8; 2]) -> Gradient {
        Gradient {
            kind,
            top: 0xFF_00_00,
            bottom: 0x00_00_FF,
            transparency,
        }
    }

    #[test]
    fn solid_fills_have_no_rows() {
        assert_eq!(gradient(FillGradient::Solid, [0; 2]).rows(10).count(), 0);
        assert_eq!(gradient(FillGradient::Vertical, [0; 2]).rows(0).count(), 0);
    }

    #[test]
    fn vertical_blends_top_to_bottom_without_fading() {
        let rows: Vec<_> = gradient(FillGradient::Vertical, [200, 0])
            .rows(4)
            .map(Option::unwrap)
            .collect();
        // t = row * 16384: weights (256, 0), (192, 64), (128, 128), (64, 192).
        assert_eq!(
            rows[0],
            Row {
                color: 0xFF_00_00,
                alpha: 255
            }
        );
        assert_eq!(
            rows[1],
            Row {
                color: 0xBF_00_3F,
                alpha: 255
            }
        );
        assert_eq!(
            rows[2],
            Row {
                color: 0x7F_00_7F,
                alpha: 255
            }
        );
        assert_eq!(
            rows[3],
            Row {
                color: 0x3F_00_BF,
                alpha: 255
            }
        );
    }

    #[test]
    fn fade_blends_alpha_and_skips_clear_rows() {
        // Opaque at the top, fully clear at the bottom.
        let rows: Vec<_> = gradient(FillGradient::VerticalFade, [0, 255])
            .rows(4)
            .collect();
        assert_eq!(rows[0].map(|r| r.alpha), Some(255));
        assert_eq!(rows[1].map(|r| r.alpha), Some(191));
        assert_eq!(rows[2].map(|r| r.alpha), Some(127));
        assert_eq!(rows[3].map(|r| r.alpha), Some(63));
        // A single clear row (both transparencies 255) is skipped.
        let clear = gradient(FillGradient::VerticalFade, [255, 255]);
        assert!(clear.rows(3).all(|row| row.is_none()));
    }
}
