//! Stock 240 RenderOverview.method12459 / method12464 input ordering.
#[cfg(test)]
mod tests;
use super::WorldMapState;
use rustclient_game_ui::Rect;
use rustclient_model::{Plane, WorldTile};
use std::time::Duration;

/// Input already transformed to the active game canvas and arbitrated by the UI.
#[derive(Clone, Copy)]
pub struct WorldMapPointer {
    pub viewport: Rect,
    pub position: [f32; 2],
    pub pressed: bool,
    pub held: bool,
    pub at: Duration,
    /// The separate staff modifier shortcut is not a normal map-click packet.
    pub staff_shortcut: bool,
}

#[derive(Default)]
pub(super) struct MapGesture {
    area: Option<i32>,
    previous_press: Option<(Duration, [f32; 2])>,
    drag: Option<([f32; 2], [i32; 2])>,
}
impl WorldMapState {
    /// Revoke unfinished gestures on map/session/focus replacement.
    pub fn cancel_input(&mut self) {
        self.input = MapGesture::default();
    }

    /// Resolve a source coordinate before updating drag state, as stock does on press.
    /// No local gameplay state or player position is changed.
    #[must_use]
    #[expect(
        clippy::cast_precision_loss,
        clippy::cast_possible_truncation,
        reason = "bounded stock float-coordinate arithmetic truncates toward zero"
    )]
    pub fn pointer(&mut self, input: WorldMapPointer) -> Option<WorldTile> {
        if self.input.area != Some(self.current_map_area_id) {
            self.cancel_input();
            self.input.area = Some(self.current_map_area_id);
        }
        let [x, y] = input.position;
        if !x.is_finite() || !y.is_finite() || !input.viewport.contains(x, y) {
            self.input.drag = None;
            return None;
        }
        let mut selected = None;
        if input.pressed {
            if self.is_loaded
                && !input.staff_shortcut
                && self.input.previous_press.is_some_and(|(previous, point)| {
                    input
                        .at
                        .checked_sub(previous)
                        .is_some_and(|elapsed| elapsed <= Duration::from_millis(500))
                        && (x - point[0]).abs() <= 25.
                        && (y - point[1]).abs() <= 25.
                })
            {
                let zoom = rustclient_scene::world_map::pixels_per_tile(self.zoom);
                let display = [
                    (self.center_tile[0] as f32
                        + (x - input.viewport.x - self.display_size[0] as f32 * zoom / 2.) / zoom)
                        as i32,
                    (self.center_tile[1] as f32
                        - (y - input.viewport.y - self.display_size[1] as f32 * zoom / 2.) / zoom)
                        as i32,
                ];
                selected = self
                    .active_area()
                    .and_then(|area| area.coord(display[0], display[1]))
                    .and_then(|coord| {
                        u8::try_from(coord.plane)
                            .ok()
                            .and_then(Plane::new)
                            .map(|plane| WorldTile::new(coord.x, coord.y, plane))
                    });
            }
            self.input.previous_press = Some((input.at, input.position));
        }
        if self.target_tile.is_none() && (input.pressed || input.held) {
            if input.pressed {
                self.input.drag = Some((input.position, self.center_tile));
            }
            if let Some((start, center)) = self.input.drag {
                let zoom = rustclient_scene::world_map::pixels_per_tile(self.target_zoom);
                self.center_tile = [
                    center[0] - ((x - start[0]) / zoom) as i32,
                    center[1] + ((y - start[1]) / zoom) as i32,
                ];
            }
        } else {
            self.input.drag = None;
        }
        selected
    }
}
