//! Shared bitmap-font text projection.
//!
//! Draws interface text into the ordered quad stream. Used by the gameframe chatbox and by
//! plugin overlay projection so both share one glyph layout path.
#![allow(
    clippy::cast_precision_loss,
    clippy::cast_possible_truncation,
    clippy::cast_lossless,
    reason = "glyph offsets and advances are bounded by the bitmap font asset"
)]

use crate::{Rect, UiFrame, UiImageId};
use rustclient_cache::TitleAssets;

/// Draw one line of CP1252 interface text at logical `(x, y)`.
pub fn draw_text(
    frame: &mut UiFrame,
    assets: &TitleAssets,
    text: &str,
    x: f32,
    y: f32,
    tint: [u8; 4],
    clip: Rect,
) {
    if assets.fonts.is_empty() {
        return;
    }
    let font_idx = 1.min(assets.fonts.len() - 1);
    let font = &assets.fonts[font_idx];
    let mut cursor_x = x;
    for ch in text.chars() {
        let byte = rustclient_cache::cp1252(ch).unwrap_or(b'?');
        if let Some(sprite) = font.glyphs.get(usize::from(byte)) {
            if sprite.image.width() > 0 && sprite.image.height() > 0 {
                let px = cursor_x + sprite.offset[0] as f32;
                let py = y + sprite.offset[1] as f32;
                frame.image_tinted(
                    UiImageId::Glyph(font_idx as u8, byte),
                    Rect::new(
                        px,
                        py,
                        sprite.image.width() as f32,
                        sprite.image.height() as f32,
                    ),
                    clip,
                    tint,
                );
            }
            let advance = font.advances.get(usize::from(byte)).copied().unwrap_or(7);
            cursor_x += advance as f32;
        } else {
            cursor_x += 7.0;
        }
    }
}
