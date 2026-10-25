//! Per-frame roof visibility: the "top level" up to which scene tiles are drawn.
//!
//! Ported from the 240 client's `classCZ.method2850` and the call that feeds it to
//! `Scene.method4435`. Every tile carries a render level (`mesh::render_level`); a tile is drawn
//! when its level is at most the frame's top level. The top level is 3 (everything, so roofs
//! show from outside) unless the camera looks at the player from a shallow pitch through a tile
//! flagged `0x4` ("roof") on the player's plane, in which case it drops to the player's plane.
//! The "hide roofs" game option (`ClientSetting::RoofsHidden`) forces the player's plane.
use crate::SceneAsset;
use rustclient_model::Plane;

/// Camera pitch (in the 128..383 gameplay-camera units) below which the roof line-of-sight
/// test runs; the client compares `pitch < 310 << 3` in its finer pitch units.
const SHALLOW_PITCH: u16 = 310;
/// Tile setting bit marking a tile that has a roof over it.
const ROOF: u8 = 4;

/// Everything the top-level decision reads, in scene-local fine units (128 per tile).
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct RoofView {
    /// The local player's plane.
    pub plane: Plane,
    /// The player (camera focus) position.
    pub subject: [i32; 2],
    /// The camera eye position.
    pub camera: [i32; 2],
    /// Camera pitch in gameplay-camera units (128..383).
    pub pitch: u16,
    /// Game option 1: never draw above the player's plane.
    pub roofs_hidden: bool,
}

impl RoofView {
    /// The highest render level drawn this frame.
    #[must_use]
    pub fn top_level(self, asset: &SceneAsset) -> u8 {
        let plane = self.plane.get();
        if self.roofs_hidden {
            return plane;
        }
        let descriptor = asset.descriptor();
        let origin = descriptor.origin();
        let (width, height) = (
            i32::try_from(descriptor.width()).unwrap_or(i32::MAX),
            i32::try_from(descriptor.height()).unwrap_or(i32::MAX),
        );
        let in_scene = |x: i32, y: i32| x >= 0 && y >= 0 && x < width && y < height;
        let roofed = |x: i32, y: i32| {
            asset
                .tile(self.plane, origin.x + x, origin.y + y)
                .is_some_and(|tile| tile.settings & ROOF != 0)
        };
        let [subject_x, subject_y] = self.subject.map(|v| v >> 7);
        let mut top = 3;
        if self.pitch < SHALLOW_PITCH {
            let (mut x, mut y) = (self.camera[0] >> 7, self.camera[1] >> 7);
            if !in_scene(x, y) || !in_scene(subject_x, subject_y) {
                return plane;
            }
            if roofed(x, y) {
                top = plane;
            }
            let dx = (subject_x - x).abs();
            let dy = (subject_y - y).abs();
            // Bresenham-style walk from the camera tile to the player tile, with the client's
            // 16.16 fixed-point error term starting half way.
            if dx > dy {
                let ratio = dy * 65536 / dx;
                let mut error = 32768;
                while x != subject_x {
                    x += (subject_x - x).signum();
                    if roofed(x, y) {
                        top = plane;
                    }
                    error += ratio;
                    if error >= 65536 {
                        error -= 65536;
                        y += (subject_y - y).signum();
                        if roofed(x, y) {
                            top = plane;
                        }
                    }
                }
            } else if dy > 0 {
                let ratio = dx * 65536 / dy;
                let mut error = 32768;
                while y != subject_y {
                    y += (subject_y - y).signum();
                    if roofed(x, y) {
                        top = plane;
                    }
                    error += ratio;
                    if error >= 65536 {
                        error -= 65536;
                        x += (subject_x - x).signum();
                        if roofed(x, y) {
                            top = plane;
                        }
                    }
                }
            }
        }
        if !in_scene(subject_x, subject_y) {
            return plane;
        }
        if roofed(subject_x, subject_y) {
            top = plane;
        }
        top
    }
}
