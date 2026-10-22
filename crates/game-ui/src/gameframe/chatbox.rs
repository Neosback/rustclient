//! Chatbox frame with messages and stone tab filters.
#![allow(
    clippy::cast_precision_loss,
    clippy::cast_possible_truncation,
    clippy::cast_possible_wrap,
    clippy::cast_sign_loss,
    clippy::cast_lossless,
    reason = "pixel and tile coordinate math is bounded by 104x104 scenes and viewport sizes"
)]

use crate::{Rect, UiFrame, UiImageId};
use rustclient_cache::TitleAssets;
use rustclient_game_state::GameState;

pub const CHANNELS: [&str; 7] = [
    "All", "Game", "Public", "Private", "Channel", "Clan", "Trade",
];

pub fn project_chatbox(
    assets: &TitleAssets,
    _state: &GameState,
    view_w: f32,
    view_h: f32,
    chat_channel: u8,
    frame: &mut UiFrame,
) {
    let clip = Rect::new(0., 0., view_w, view_h);
    let width = 450.0;
    let height = 130.0;
    let box_x = 8.0;
    let box_y = view_h - height - 8.0;

    // 1. Semi-transparent dark background
    frame.fill_rect(
        Rect::new(box_x, box_y, width, height),
        [18, 16, 14, 210],
        clip,
    );

    // 2. Brown stone border
    let border_color = [70, 60, 50, 255];
    frame.fill_rect(Rect::new(box_x, box_y, width, 1.0), border_color, clip);
    frame.fill_rect(
        Rect::new(box_x, box_y + height - 1.0, width, 1.0),
        border_color,
        clip,
    );
    frame.fill_rect(Rect::new(box_x, box_y, 1.0, height), border_color, clip);
    frame.fill_rect(
        Rect::new(box_x + width - 1.0, box_y, 1.0, height),
        border_color,
        clip,
    );

    // 3. Welcome text lines
    draw_text_line(
        frame,
        assets,
        "Welcome to RustClient.",
        box_x + 10.0,
        box_y + 8.0,
        [255, 255, 0, 255],
        clip,
    );
    draw_text_line(
        frame,
        assets,
        "Connected to OpenRune Server on 127.0.0.1:43594.",
        box_x + 10.0,
        box_y + 24.0,
        [120, 200, 255, 255],
        clip,
    );
    draw_text_line(
        frame,
        assets,
        "Use Arrow keys or WASD to rotate camera / walk.",
        box_x + 10.0,
        box_y + 40.0,
        [200, 200, 200, 255],
        clip,
    );

    // 4. Channel filter buttons across bottom
    let btn_w = 58.0;
    let btn_h = 20.0;
    let btn_y = box_y + height - btn_h - 4.0;

    for (i, &channel) in CHANNELS.iter().enumerate() {
        let btn_x = box_x + 8.0 + (i as f32) * (btn_w + 4.0);
        let active = i as u8 == chat_channel;
        let bg_color = if active {
            [160, 60, 30, 160]
        } else {
            [40, 35, 30, 180]
        };
        frame.fill_rect(Rect::new(btn_x, btn_y, btn_w, btn_h), bg_color, clip);
        frame.fill_rect(Rect::new(btn_x, btn_y, btn_w, 1.0), [90, 80, 70, 255], clip);
        let text_color = if active {
            [255, 255, 255, 255]
        } else {
            [180, 180, 180, 255]
        };
        draw_text_line(
            frame,
            assets,
            channel,
            btn_x + 6.0,
            btn_y + 4.0,
            text_color,
            clip,
        );
    }
}

fn draw_text_line(
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
