//! Input routing through the displayed, cache-authored gameframe.
#![allow(
    clippy::cast_precision_loss,
    clippy::cast_possible_truncation,
    clippy::cast_possible_wrap,
    clippy::cast_sign_loss,
    clippy::cast_lossless,
    reason = "pixel and tile coordinate math is bounded by 104x104 scenes and viewport sizes"
)]

use crate::UiFrame;
use crate::widget_visuals::CanvasImage;

/// Pointer routing derived from the actual HUD projection.
pub enum GameplayPointer {
    World,
    Blocked,
    /// Offset in minimap source pixels, ready for the displayed rotation.
    Minimap {
        dx: f32,
        dy: f32,
    },
}

/// Route world input through the same quads and aperture mask used to draw HUD.
#[must_use]
/// `minimap_mask` is the minimap widget's own mask sprite on its canvas; clicks outside its
/// transparent span are blocked, exactly like the displayed minimap.
pub fn gameplay_pointer(
    frame: &UiFrame,
    minimap_mask: Option<&CanvasImage>,
    x: f32,
    y: f32,
) -> GameplayPointer {
    use crate::UiImageId;
    if !x.is_finite() || !y.is_finite() {
        return GameplayPointer::Blocked;
    }
    // Pointer feedback is drawn last, but never captures input itself.
    let hit = |quad: &&crate::Quad| {
        !matches!(quad.image, UiImageId::Cross(_) | UiImageId::Glyph(_, _))
            && quad.rect.contains(x, y)
            && quad.clip.contains(x, y)
    };
    if frame
        .quads
        .iter()
        .filter(hit)
        .any(|q| q.image == UiImageId::Compass)
    {
        return GameplayPointer::Blocked;
    }
    if let Some(quad) = frame
        .quads
        .iter()
        .filter(hit)
        .find(|q| q.image == UiImageId::Minimap)
        && let Some(mask) = minimap_mask
    {
        let width = mask.size[0] as f32;
        let height = mask.size[1] as f32;
        let px = ((x - quad.rect.x) * width / quad.rect.width).floor();
        let py = ((y - quad.rect.y) * height / quad.rect.height).floor();
        let index = ((py.floor() as usize) * mask.size[0] as usize + px.floor() as usize) * 4 + 3;
        return if mask.pixels.get(index) == Some(&0) {
            GameplayPointer::Minimap {
                dx: px - (mask.size[0] / 2) as f32,
                dy: py - (mask.size[1] / 2) as f32,
            }
        } else {
            GameplayPointer::Blocked
        };
    }
    if frame.quads.iter().any(|q| hit(&q)) {
        GameplayPointer::Blocked
    } else {
        GameplayPointer::World
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use crate::{Rect, UiImageId};

    #[test]
    fn click_cross_and_mouseover_text_never_block_world_input() {
        let mut frame = UiFrame::default();
        let rect = Rect::new(20., 30., 16., 16.);
        frame.image(UiImageId::Cross(4), rect, rect);
        frame.image(UiImageId::Glyph(1, b'A'), rect, rect);
        assert!(matches!(
            gameplay_pointer(&frame, None, 25., 35.),
            GameplayPointer::World
        ));
        frame.fill_rect(rect, [255; 4], rect);
        assert!(matches!(
            gameplay_pointer(&frame, None, 25., 35.),
            GameplayPointer::Blocked
        ));
    }
}
