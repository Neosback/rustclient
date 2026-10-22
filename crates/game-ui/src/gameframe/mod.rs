//! Authentic OSRS resizable-mode gameframe HUD: Minimap aperture, rotating compass,
//! status orbs, stone side-panel tabs, chatbox, and transient world context menu.
#![allow(
    clippy::cast_precision_loss,
    clippy::cast_possible_truncation,
    clippy::cast_possible_wrap,
    clippy::cast_sign_loss,
    clippy::cast_lossless,
    reason = "pixel and tile coordinate math is bounded by 104x104 scenes and viewport sizes"
)]

mod chatbox;
mod context_menu;
mod minimap;
mod tabs;

pub use chatbox::CHANNELS;
pub use context_menu::ContextMenuEntry;
pub use tabs::TAB_ICONS;

use crate::{Rect, UiFrame};
use rustclient_cache::TitleAssets;
use rustclient_game_state::{GameState, SemanticOperation};
use rustclient_input::GameIntent;
use rustclient_model::WorldTile;

/// Live gameframe HUD state and click dispatcher.
#[derive(Debug, Clone, Default)]
pub struct GameFrameUi {
    pub compass_clicked: bool,
    pub chat_channel: u8,
    context_menu: Option<context_menu::ContextMenu>,
    queued_intent: Option<GameIntent>,
    pointer_consumed: bool,
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

    /// Take one semantic gameplay intent produced by a context-menu selection.
    pub fn take_intent(&mut self) -> Option<GameIntent> {
        self.queued_intent.take()
    }

    /// Whether the most recent left pointer press belonged to HUD/menu UI rather than the world.
    #[must_use]
    pub const fn pointer_consumed(&self) -> bool {
        self.pointer_consumed
    }

    /// Build the canonical first terrain context-menu entry.
    #[must_use]
    pub const fn walk_here_entry(tile: WorldTile) -> ContextMenuEntry {
        context_menu::walk_here_entry(tile)
    }

    /// Open the one-entry terrain context menu at a logical gameframe coordinate.
    pub fn open_walk_menu(&mut self, x: f32, y: f32, view_w: f32, view_h: f32, tile: WorldTile) {
        self.context_menu = Some(context_menu::ContextMenu::walk_here(
            x, y, view_w, view_h, tile,
        ));
    }

    /// Return true when a pointer is over retained HUD chrome that must not click through to terrain.
    #[must_use]
    pub fn world_pointer_blocked(x: f32, y: f32, view_w: f32, view_h: f32) -> bool {
        let map_x = view_w - 210.0 - 10.0;
        let minimap = Rect::new(map_x - 24.0, 10.0, view_w - (map_x - 24.0), 170.0);
        let tabs = Rect::new(
            view_w - 32.0 * 7.0 - 8.0,
            view_h - 32.0 * 2.0 - 8.0,
            224.0,
            64.0,
        );
        let chatbox = Rect::new(8.0, view_h - 130.0 - 8.0, 450.0, 130.0);
        minimap.contains(x, y) || tabs.contains(x, y) || chatbox.contains(x, y)
    }

    /// Handle click in game view. Returns a semantic operation if an interactive HUD
    /// element was clicked. Gameplay intents are queued separately via [`Self::take_intent`].
    pub fn handle_pointer_down(
        &mut self,
        x: f32,
        y: f32,
        view_w: f32,
        view_h: f32,
        current_tab: Option<usize>,
    ) -> Option<SemanticOperation> {
        self.pointer_consumed = false;
        if let Some(menu) = self.context_menu.take() {
            self.pointer_consumed = true;
            if menu.contains(x, y) {
                self.queued_intent = Some(menu.entry().intent);
            }
            return None;
        }

        // 1. Compass click
        let frame_w = 210.0;
        let map_x = view_w - frame_w - 10.0;
        let map_y = 10.0;
        let compass_rect = Rect::new(map_x + 5.0, map_y + 5.0, 33.0, 33.0);
        if compass_rect.contains(x, y) {
            self.pointer_consumed = true;
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
            self.pointer_consumed = true;
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
            return None;
        }

        // 3. Chatbox. The whole retained surface blocks terrain click-through.
        let box_h = 130.0;
        let box_y = view_h - box_h - 8.0;
        let chatbox = Rect::new(8.0, box_y, 450.0, box_h);
        if chatbox.contains(x, y) {
            self.pointer_consumed = true;
            let btn_w = 58.0;
            let btn_h = 20.0;
            let btn_y = box_y + box_h - btn_h - 4.0;
            for (i, _) in CHANNELS.iter().enumerate() {
                let btn_x = 16.0 + (i as f32) * (btn_w + 4.0);
                if Rect::new(btn_x, btn_y, btn_w, btn_h).contains(x, y) {
                    self.chat_channel = i as u8;
                    break;
                }
            }
            return None;
        }

        // Non-interactive minimap/orb chrome also blocks world picking.
        if Self::world_pointer_blocked(x, y, view_w, view_h) {
            self.pointer_consumed = true;
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
        if let Some(menu) = self.context_menu {
            menu.project(assets, view_w, view_h, frame);
        }
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use rustclient_model::Plane;

    #[test]
    fn walk_here_entry_is_typed_and_exact() {
        let tile = WorldTile::new(3200, 3201, Plane::MIN);
        let entry = GameFrameUi::walk_here_entry(tile);
        assert_eq!(entry.label, "Walk here");
        assert_eq!(entry.intent, GameIntent::WalkTo { destination: tile });
    }

    #[test]
    fn retained_hud_blocks_terrain_click_through() {
        assert!(GameFrameUi::world_pointer_blocked(
            600.0, 30.0, 765.0, 503.0
        ));
        assert!(GameFrameUi::world_pointer_blocked(
            50.0, 450.0, 765.0, 503.0
        ));
        assert!(!GameFrameUi::world_pointer_blocked(
            350.0, 250.0, 765.0, 503.0
        ));
    }
}
