//! Side-panel stone tab bar with authentic OSRS icons and selection state.
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

pub const TAB_ICONS: [u32; 14] = [
    774, // Combat Options
    775, // Skills
    776, // Quest List
    777, // Inventory
    778, // Worn Equipment
    779, // Prayer
    780, // Magic
    781, // Chat-channel
    782, // Account Management
    783, // Friends List
    784, // Logout
    785, // Settings / Character
    786, // Emotes
    787, // Music Player
];

pub fn project_tabs(
    _assets: &TitleAssets,
    state: &GameState,
    view_w: f32,
    view_h: f32,
    frame: &mut UiFrame,
) {
    let clip = Rect::new(0., 0., view_w, view_h);
    let tab_w = 32.0;
    let tab_h = 32.0;
    let bar_w = tab_w * 7.0;
    let bar_h = tab_h * 2.0;
    let bar_x = view_w - bar_w - 8.0;
    let bar_y = view_h - bar_h - 8.0;

    // 1. Top and bottom stone background frames (1173 and 1174)
    frame.image(
        UiImageId::Sprite(1173),
        Rect::new(bar_x, bar_y, bar_w, tab_h),
        clip,
    );
    frame.image(
        UiImageId::Sprite(1174),
        Rect::new(bar_x, bar_y + tab_h, bar_w, tab_h),
        clip,
    );

    let active_tab = state.selected_tab();

    // 2. Tab Icons and active selection highlight
    for (idx, &sprite_id) in TAB_ICONS.iter().enumerate() {
        let col = (idx % 7) as f32;
        let row = (idx / 7) as f32;
        let cell_x = bar_x + col * tab_w;
        let cell_y = bar_y + row * tab_h;

        // Selection highlight if active
        if active_tab == Some(idx) {
            frame.fill_rect(
                Rect::new(cell_x + 1.0, cell_y + 1.0, tab_w - 2.0, tab_h - 2.0),
                [160, 60, 30, 90],
                clip,
            );
            // Gold border
            frame.fill_rect(
                Rect::new(cell_x + 1.0, cell_y + 1.0, tab_w - 2.0, 1.5),
                [255, 200, 50, 255],
                clip,
            );
            frame.fill_rect(
                Rect::new(cell_x + 1.0, cell_y + tab_h - 2.5, tab_w - 2.0, 1.5),
                [255, 200, 50, 255],
                clip,
            );
            frame.fill_rect(
                Rect::new(cell_x + 1.0, cell_y + 1.0, 1.5, tab_h - 2.0),
                [255, 200, 50, 255],
                clip,
            );
            frame.fill_rect(
                Rect::new(cell_x + tab_w - 2.5, cell_y + 1.0, 1.5, tab_h - 2.0),
                [255, 200, 50, 255],
                clip,
            );
        }

        // Tab icon centered in cell
        let icon_size = 22.0;
        let icon_x = cell_x + (tab_w - icon_size) / 2.0;
        let icon_y = cell_y + (tab_h - icon_size) / 2.0;
        frame.image(
            UiImageId::Sprite(sprite_id),
            Rect::new(icon_x, icon_y, icon_size, icon_size),
            clip,
        );
    }
}
