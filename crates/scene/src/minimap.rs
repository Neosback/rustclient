//! Minimap generator and rotator for OSRS scenes.
//!
//! Generates a 4px/tile minimap surface from scene terrain (underlays, overlays, tile shapes)
//! and static wall locations, then composites it through the circular mask (sprite 1178) rotated
//! by camera yaw, and rotates the compass (sprite 169 through mask 1179).
#![allow(
    clippy::cast_precision_loss,
    clippy::cast_possible_truncation,
    clippy::cast_possible_wrap,
    clippy::cast_sign_loss,
    clippy::cast_lossless,
    reason = "pixel and tile coordinate math is bounded by 104x104 scenes and viewport sizes"
)]

use crate::SceneAsset;
use rustclient_model::{Plane, WorldTile};

/// Minimap pixels per world tile.
pub const TILE_PX: usize = 4;
const WALL_COLOR: u32 = 0x00EE_EEEE;

const DOOR_COLOR: u32 = 0x00EE_0000;

/// `Scene.tileShape2D`: 16 template entries (a 4x4 pixel mask) per tile shape.
const TILE_SHAPE_2D: [[u8; 16]; 13] = [
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
    [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1],
    [1, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1],
    [1, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0],
    [0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1],
    [0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1],
    [1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1],
    [1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0],
    [1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1],
    [1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1],
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1],
];

/// `Scene.tileRotation2D`.
const TILE_ROTATION_2D: [[u8; 16]; 4] = [
    [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15],
    [12, 8, 4, 0, 13, 9, 5, 1, 14, 10, 6, 2, 15, 11, 7, 3],
    [15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0],
    [3, 7, 11, 15, 2, 6, 10, 14, 1, 5, 9, 13, 0, 4, 8, 12],
];

/// Generated 2D minimap image for the scene.
#[derive(Debug, Clone)]
pub struct MinimapImage {
    pub width: usize,
    pub height: usize,
    pub origin: WorldTile,
    pub rgba: Vec<u8>,
}

impl MinimapImage {
    fn set_pixel(&mut self, x: usize, y: usize, rgb: u32) {
        if x < self.width && y < self.height {
            let idx = (y * self.width + x) * 4;
            self.rgba[idx] = (rgb >> 16) as u8;
            self.rgba[idx + 1] = (rgb >> 8) as u8;
            self.rgba[idx + 2] = rgb as u8;
            self.rgba[idx + 3] = 255;
        }
    }
}

/// Build the full 4px/tile minimap image for the given `SceneAsset` at `plane`.
#[must_use]
#[expect(
    clippy::too_many_lines,
    reason = "single-pass raster of terrain, overlays and walls"
)]
pub fn build_scene_minimap(asset: &SceneAsset, plane: Plane) -> MinimapImage {
    let descriptor = asset.descriptor();
    let origin = descriptor.origin();
    let width_tiles = descriptor.width() as usize;
    let height_tiles = descriptor.height() as usize;
    let img_w = width_tiles * TILE_PX;
    let img_h = height_tiles * TILE_PX;

    let mut minimap = MinimapImage {
        width: img_w,
        height: img_h,
        origin,
        rgba: vec![0; img_w * img_h * 4],
    };

    // 1. Render terrain tiles
    for tx in 0..width_tiles {
        for ty in 0..height_tiles {
            let wx = origin.x + tx as i32;
            let wy = origin.y + ty as i32;
            let Some(tile) = asset.tile(plane, wx, wy) else {
                continue;
            };

            let px = tx * TILE_PX;
            let py = (height_tiles - 1 - ty) * TILE_PX;

            let underlay_rgb = tile
                .underlay
                .and_then(|id| asset.sources().underlays.get(&id))
                .map_or(0x0033_3333, |mat| mat.rgb);

            if let Some(overlay) = tile.overlay {
                let overlay_rgb = asset
                    .sources()
                    .overlays
                    .get(&overlay.id)
                    .map_or(underlay_rgb, |mat| mat.rgb);

                let shape_idx = usize::from(overlay.shape).min(12);
                let rot_idx = usize::from(overlay.rotation & 3);

                for sub_y in 0..TILE_PX {
                    for sub_x in 0..TILE_PX {
                        let sub_idx = sub_y * TILE_PX + sub_x;
                        let mapped_idx = usize::from(TILE_ROTATION_2D[rot_idx][sub_idx]);
                        let is_overlay = TILE_SHAPE_2D[shape_idx][mapped_idx] != 0;
                        let color = if is_overlay {
                            overlay_rgb
                        } else {
                            underlay_rgb
                        };
                        minimap.set_pixel(px + sub_x, py + sub_y, color);
                    }
                }
            } else if tile.underlay.is_some() {
                for sub_y in 0..TILE_PX {
                    for sub_x in 0..TILE_PX {
                        minimap.set_pixel(px + sub_x, py + sub_y, underlay_rgb);
                    }
                }
            }
        }
    }

    // 2. Render static walls / doors
    for square in &asset.sources().squares {
        for loc in &square.locations {
            if loc.tile.plane != plane {
                continue;
            }
            let tx = loc.tile.x - origin.x;
            let ty = loc.tile.y - origin.y;
            if tx < 0 || ty < 0 || tx >= width_tiles as i32 || ty >= height_tiles as i32 {
                continue;
            }
            let px = tx as usize * TILE_PX;
            let py = (height_tiles - 1 - ty as usize) * TILE_PX;

            let is_interactive = asset
                .sources()
                .objects
                .get(&loc.object)
                .is_some_and(|def| def.animation.is_some());
            let color = if is_interactive {
                DOOR_COLOR
            } else {
                WALL_COLOR
            };
            let rot = loc.orientation.get() & 3;

            match loc.shape {
                0 | 2 => {
                    draw_wall_edge(&mut minimap, px, py, rot, color);
                    if loc.shape == 2 {
                        draw_wall_edge(&mut minimap, px, py, (rot + 1) & 3, color);
                    }
                }
                3 => {
                    let (cx, cy) = match rot {
                        0 => (px, py),
                        1 => (px + 3, py),
                        2 => (px + 3, py + 3),
                        _ => (px, py + 3),
                    };
                    minimap.set_pixel(cx, cy, color);
                }
                9 => {
                    for step in 0..TILE_PX {
                        let y = if rot == 0 || rot == 2 {
                            py + 3 - step
                        } else {
                            py + step
                        };
                        minimap.set_pixel(px + step, y, color);
                    }
                }
                _ => {}
            }
        }
    }

    minimap
}

fn draw_wall_edge(minimap: &mut MinimapImage, px: usize, py: usize, rot: u8, color: u32) {
    for step in 0..TILE_PX {
        let (x, y) = match rot {
            0 => (px, py + step),
            1 => (px + step, py),
            2 => (px + 3, py + step),
            _ => (px + step, py + 3),
        };
        minimap.set_pixel(x, y, color);
    }
}

/// Convert screen minimap offset (dx, dy) into target world coordinates (tx, ty).
#[must_use]
pub fn screen_to_tile(dx: f32, dy: f32, camera: (f64, f64), yaw: f32) -> (f64, f64) {
    let (sin, cos) = yaw.sin_cos();
    let (right, forward) = (dx / (TILE_PX as f32), -dy / (TILE_PX as f32));
    (
        camera.0 + f64::from(right * cos - forward * sin),
        camera.1 + f64::from(right * sin + forward * cos),
    )
}

/// Compose the rotating minimap image through mask (sprite 1178).
/// Returns raw RGBA bytes of size `mask_width * mask_height * 4`.
#[must_use]
pub fn compose_minimap(
    mask_rgba: &[u8],
    mask_width: usize,
    mask_height: usize,
    minimap: &MinimapImage,
    camera: (f64, f64),
    yaw: f32,
) -> Vec<u8> {
    let mut out = vec![0_u8; mask_width * mask_height * 4];
    let origin_x = f64::from(minimap.origin.x);
    let origin_y = f64::from(minimap.origin.y);
    let half_w = mask_width as f32 / 2.0;
    let half_h = mask_height as f32 / 2.0;

    for y in 0..mask_height {
        for x in 0..mask_width {
            let mask_idx = (y * mask_width + x) * 4;
            // Transparent part of the mask (alpha == 0) is the open viewport
            if mask_rgba[mask_idx + 3] != 0 {
                continue;
            }

            let dx = x as f32 + 0.5 - half_w;
            let dy = y as f32 + 0.5 - half_h;
            let (tx, ty) = screen_to_tile(dx, dy, camera, yaw);

            let local_tile_x = tx - origin_x;
            let local_tile_y = ty - origin_y;

            if local_tile_x >= 0.0 && local_tile_y >= 0.0 {
                let pixel_x = (local_tile_x * (TILE_PX as f64)).floor() as usize;
                let tile_py = (local_tile_y * (TILE_PX as f64)).floor() as usize;
                let img_py = (minimap.height.saturating_sub(1)).saturating_sub(tile_py);

                if pixel_x < minimap.width && img_py < minimap.height {
                    let src_idx = (img_py * minimap.width + pixel_x) * 4;
                    if minimap.rgba[src_idx + 3] != 0 {
                        out[mask_idx] = minimap.rgba[src_idx];
                        out[mask_idx + 1] = minimap.rgba[src_idx + 1];
                        out[mask_idx + 2] = minimap.rgba[src_idx + 2];
                        out[mask_idx + 3] = 255;
                    }
                }
            }
        }
    }

    out
}

/// Compose the rotating compass image (sprite 169) through compass mask (sprite 1179).
/// Returns raw RGBA bytes of size `mask_width * mask_height * 4`.
#[must_use]
pub fn compose_compass(
    compass_rgba: &[u8],
    compass_width: usize,
    compass_height: usize,
    mask_rgba: &[u8],
    mask_width: usize,
    mask_height: usize,
    yaw: f32,
) -> Vec<u8> {
    let mut out = vec![0_u8; mask_width * mask_height * 4];
    let (sin, cos) = yaw.sin_cos();
    let src_w = compass_width as f32;
    let src_h = compass_height as f32;
    let half_w = mask_width as f32 / 2.0;
    let half_h = mask_height as f32 / 2.0;

    for y in 0..mask_height {
        for x in 0..mask_width {
            let mask_idx = (y * mask_width + x) * 4;
            if mask_rgba[mask_idx + 3] != 0 {
                continue;
            }

            let dx = x as f32 + 0.5 - half_w;
            let dy = y as f32 + 0.5 - half_h;

            let sx = dx * cos - dy * sin + src_w / 2.0;
            let sy = dx * sin + dy * cos + src_h / 2.0;

            if sx >= 0.0 && sy >= 0.0 && sx < src_w && sy < src_h {
                let src_idx = (sy as usize * compass_width + sx as usize) * 4;
                if compass_rgba[src_idx + 3] != 0 {
                    out[mask_idx] = compass_rgba[src_idx];
                    out[mask_idx + 1] = compass_rgba[src_idx + 1];
                    out[mask_idx + 2] = compass_rgba[src_idx + 2];
                    out[mask_idx + 3] = compass_rgba[src_idx + 3];
                }
            }
        }
    }

    out
}
