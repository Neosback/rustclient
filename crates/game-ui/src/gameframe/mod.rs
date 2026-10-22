//! Authentic OSRS resizable-mode gameframe HUD: Minimap aperture, rotating compass,
//! status orbs, stone side-panel tabs, and chatbox.
#![allow(
    clippy::cast_precision_loss,
    clippy::cast_possible_truncation,
    clippy::cast_possible_wrap,
    clippy::cast_sign_loss,
    clippy::cast_lossless,
    reason = "pixel and tile coordinate math is bounded by 104x104 scenes and viewport sizes"
)]

mod chatbox;
mod minimap;
mod tabs;

pub use chatbox::CHANNELS;
pub use tabs::TAB_ICONS;

use crate::{Rect, UiFrame};
use rustclient_cache::TitleAssets;
use rustclient_game_state::{GameState, SemanticOperation};

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
pub fn gameplay_pointer(frame: &UiFrame, assets: &TitleAssets, x: f32, y: f32) -> GameplayPointer {
    use crate::UiImageId;
    if !x.is_finite() || !y.is_finite() {
        return GameplayPointer::Blocked;
    }
    let hit = |quad: &&crate::Quad| quad.rect.contains(x, y) && quad.clip.contains(x, y);
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
        && let Some(gameframe) = &assets.gameframe
    {
        let mask = &gameframe.minimap_mask.image;
        let width = mask.width() as f32;
        let height = mask.height() as f32;
        let px = (x - quad.rect.x) * width / quad.rect.width;
        let py = (y - quad.rect.y) * height / quad.rect.height;
        let index = ((py.floor() as usize) * mask.width() as usize + px.floor() as usize) * 4 + 3;
        return if mask.pixels().get(index) == Some(&0) {
            GameplayPointer::Minimap {
                dx: px - width / 2.,
                dy: py - height / 2.,
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

/// Live gameframe HUD state and click dispatcher.
#[derive(Debug, Clone, Default)]
pub struct GameFrameUi {
    pub compass_clicked: bool,
    pub chat_channel: u8,
}

impl GameFrameUi {
    #[must_use]
    pub fn new() -> Self {
        Self::default()
    }

    /// Check if compass was clicked (e.g. to face camera north). Clears the flag.
    pub fn take_compass_click(&mut self) -> bool {
        std::mem::take(&mut self.compass_clicked)
    }

    /// Handle click in game view. Returns a semantic operation if an interactive HUD
    /// element was clicked (e.g. stone tab selection).
    pub fn handle_pointer_down(
        &mut self,
        x: f32,
        y: f32,
        view_w: f32,
        view_h: f32,
        current_tab: Option<usize>,
    ) -> Option<SemanticOperation> {
        // 1. Compass click
        let frame_w = 210.0;
        let map_x = view_w - frame_w - 10.0;
        let map_y = 10.0;
        let compass_rect = Rect::new(map_x + 5.0, map_y + 5.0, 33.0, 33.0);
        if compass_rect.contains(x, y) {
            self.compass_clicked = true;
            return None;
        }

        // 2. Stone tabs click
        let tab_w = 32.0;
        let tab_h = 32.0;
        let bar_w = tab_w * 7.0;
        let bar_h = tab_h * 2.0;
        let bar_x = view_w - bar_w - 8.0;
        let bar_y = view_h - bar_h - 8.0;
        let tabs_rect = Rect::new(bar_x, bar_y, bar_w, bar_h);

        if tabs_rect.contains(x, y) {
            let col = ((x - bar_x) / tab_w).floor() as usize;
            let row = ((y - bar_y) / tab_h).floor() as usize;
            let idx = row * 7 + col;
            if idx < 14 {
                let next_tab = if current_tab == Some(idx) {
                    None
                } else {
                    Some(idx)
                };
                return Some(SemanticOperation::SelectGameFrameTab(next_tab));
            }
        }

        // 3. Chatbox channels click
        let box_h = 130.0;
        let box_y = view_h - box_h - 8.0;
        let btn_w = 58.0;
        let btn_h = 20.0;
        let btn_y = box_y + box_h - btn_h - 4.0;
        for (i, _) in CHANNELS.iter().enumerate() {
            let btn_x = 8.0 + 8.0 + (i as f32) * (btn_w + 4.0);
            if Rect::new(btn_x, btn_y, btn_w, btn_h).contains(x, y) {
                self.chat_channel = i as u8;
                return None;
            }
        }

        None
    }

    /// Project gameframe quads into `frame`.
    pub fn project(
        &self,
        assets: &TitleAssets,
        state: &GameState,
        view_w: f32,
        view_h: f32,
        frame: &mut UiFrame,
    ) {
        minimap::project_minimap(assets, state, view_w, view_h, frame);
        tabs::project_tabs(assets, state, view_w, view_h, frame);
        chatbox::project_chatbox(assets, state, view_w, view_h, self.chat_channel, frame);
    }
}
