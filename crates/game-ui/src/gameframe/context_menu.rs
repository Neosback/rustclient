//! Small semantic context menu projection for world interactions.
use super::chatbox::draw_text_line;
use crate::{Rect, UiFrame};
use rustclient_cache::TitleAssets;
use rustclient_input::GameIntent;
use rustclient_model::WorldTile;

const WIDTH: f32 = 96.0;
const HEIGHT: f32 = 22.0;

/// One user-visible context menu choice backed by a typed semantic intent.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct ContextMenuEntry {
    pub label: &'static str,
    pub intent: GameIntent,
}

#[derive(Debug, Clone, Copy)]
pub(super) struct ContextMenu {
    x: f32,
    y: f32,
    entry: ContextMenuEntry,
}

impl ContextMenu {
    pub fn walk_here(x: f32, y: f32, view_w: f32, view_h: f32, tile: WorldTile) -> Self {
        Self {
            x: x.clamp(0.0, (view_w - WIDTH).max(0.0)),
            y: y.clamp(0.0, (view_h - HEIGHT).max(0.0)),
            entry: walk_here_entry(tile),
        }
    }

    pub const fn entry(self) -> ContextMenuEntry {
        self.entry
    }

    pub fn contains(self, x: f32, y: f32) -> bool {
        self.rect().contains(x, y)
    }

    fn rect(self) -> Rect {
        Rect::new(self.x, self.y, WIDTH, HEIGHT)
    }

    pub fn project(self, assets: &TitleAssets, view_w: f32, view_h: f32, frame: &mut UiFrame) {
        let clip = Rect::new(0.0, 0.0, view_w, view_h);
        let rect = self.rect();
        frame.fill_rect(rect, [32, 32, 32, 245], clip);
        frame.fill_rect(Rect::new(rect.x, rect.y, rect.width, 1.0), [110, 110, 110, 255], clip);
        frame.fill_rect(
            Rect::new(rect.x, rect.y + rect.height - 1.0, rect.width, 1.0),
            [110, 110, 110, 255],
            clip,
        );
        frame.fill_rect(Rect::new(rect.x, rect.y, 1.0, rect.height), [110, 110, 110, 255], clip);
        frame.fill_rect(
            Rect::new(rect.x + rect.width - 1.0, rect.y, 1.0, rect.height),
            [110, 110, 110, 255],
            clip,
        );
        draw_text_line(
            frame,
            assets,
            self.entry.label,
            rect.x + 6.0,
            rect.y + 4.0,
            [255, 255, 255, 255],
            clip,
        );
    }
}

#[must_use]
pub const fn walk_here_entry(tile: WorldTile) -> ContextMenuEntry {
    ContextMenuEntry {
        label: "Walk here",
        intent: GameIntent::WalkTo { destination: tile },
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use rustclient_model::Plane;

    #[test]
    fn walk_here_entry_preserves_exact_semantic_destination() {
        let tile = WorldTile::new(3222, 3218, Plane::MIN);
        let entry = walk_here_entry(tile);
        assert_eq!(entry.label, "Walk here");
        assert_eq!(entry.intent, GameIntent::WalkTo { destination: tile });
    }
}
