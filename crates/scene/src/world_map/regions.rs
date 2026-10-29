//! Retained CPU map-region projection. Evidence: 240 `WorldMapRegion` and `WorldMapScaleHandler`.
use rustclient_cache::{
    Sprite,
    osrs240::world_map::{MapCoord, WorldMapObject, WorldMapRegion, WorldMapStaticIcon},
    world::FloorMaterial,
};
use rustclient_model::FloorOverlayId;
use std::collections::BTreeMap;

/// One region raster, north at row zero. Geometry and pixels are disposable projections.
pub struct RegionImage {
    pub region: [i32; 2],
    pub pixels_per_tile: u8,
    pub pixels: Vec<u8>,
    pub icons: Vec<WorldMapStaticIcon>,
}
/// Source palette is independent of display brightness; build it once per worker.
pub struct RegionPainter {
    palette: Vec<u32>,
}
impl Default for RegionPainter {
    fn default() -> Self {
        Self {
            palette: (0..65536).map(crate::color::palette_rgb).collect(),
        }
    }
}
impl RegionPainter {
    /// Render a bounded integer-resolution region. The viewport scales this retained image.
    #[must_use]
    #[expect(
        clippy::too_many_arguments,
        clippy::too_many_lines,
        reason = "source tile ground/decorations passes"
    )]
    pub fn render(
        &self,
        region: &WorldMapRegion,
        ppt: u8,
        background: u32,
        overlays: &BTreeMap<FloorOverlayId, FloorMaterial>,
        textures: &BTreeMap<u32, u16>,
        objects: &BTreeMap<u32, WorldMapObject>,
        scenes: &[Sprite],
    ) -> RegionImage {
        let ppt = i32::from(ppt.clamp(1, 8));
        let mut canvas = Canvas {
            side: 64 * ppt,
            pixels: vec![0; (64 * ppt * 64 * ppt) as usize],
        };
        let background = background | 0xff00_0000;
        for x in 0..64 {
            for y in 0..64 {
                let tile = region.tile(x as usize, y as usize);
                let ground = if tile.underlay == 0 {
                    background
                } else {
                    region.ground_color(x as usize, y as usize) | 0xff00_0000
                };
                for py in 0..ppt {
                    for px in 0..ppt {
                        canvas.put(x * ppt + px, (63 - y) * ppt + py, ground);
                    }
                }
                for (plane, overlay) in tile.overlays.iter().enumerate().filter(|(_, o)| o.id != 0)
                {
                    let color = overlays
                        .get(&FloorOverlayId::new(u32::from(overlay.id) - 1))
                        .map_or(background, |material| {
                            self.overlay_color(material, textures, background)
                        });
                    for py in 0..ppt {
                        for px in 0..ppt {
                            if overlay.shape == 0
                                || mask(ppt, overlay.shape, overlay.rotation, px, py)
                            {
                                canvas.put(x * ppt + px, (63 - y) * ppt + py, color);
                            } else if plane == 0 {
                                canvas.put(x * ppt + px, (63 - y) * ppt + py, ground);
                            }
                        }
                    }
                }
            }
        }
        let mut icons = Vec::new();
        for x in 0..64 {
            for y in 0..64 {
                let tile = region.tile(x as usize, y as usize);
                let mut icon = false;
                for (plane, decorations) in tile.decorations.iter().enumerate() {
                    for decoration in decorations {
                        let Some(object) = decoration.object.and_then(|id| objects.get(&id)) else {
                            continue;
                        };
                        if !icon && let Some(element) = object.map_element {
                            icons.push(WorldMapStaticIcon {
                                element: Some(element),
                                coord: MapCoord::new(
                                    plane as i32,
                                    region.region[0] * 64 + x,
                                    region.region[1] * 64 + y,
                                ),
                                members_only: false,
                            });
                            icon = true;
                        }
                        let left = x * ppt;
                        let top = (63 - y) * ppt;
                        if object.map_scene.is_none() {
                            let color = if object.interactive {
                                0xffcc_0000
                            } else {
                                0xffcc_cccc
                            };
                            match decoration.shape {
                                0 => canvas.wall(left, top, ppt, decoration.rotation, color),
                                2 => {
                                    canvas.wall(left, top, ppt, decoration.rotation, 0xffcc_cccc);
                                    canvas.wall(
                                        left,
                                        top,
                                        ppt,
                                        (decoration.rotation + 1) & 3,
                                        color,
                                    );
                                }
                                3 => {
                                    let [dx, dy] =
                                        [[0, 0], [ppt - 1, 0], [ppt - 1, ppt - 1], [0, ppt - 1]]
                                            [usize::from(decoration.rotation & 3)];
                                    canvas.put(left + dx, top + dy, color);
                                }
                                9 => {
                                    for step in 0..ppt {
                                        canvas.put(
                                            left + step,
                                            top + if decoration.rotation & 1 == 0 {
                                                ppt - 1 - step
                                            } else {
                                                step
                                            },
                                            color,
                                        );
                                    }
                                }
                                _ => {}
                            }
                        } else if !(4..=8).contains(&decoration.shape)
                            && let Some(sprite) =
                                object.map_scene.and_then(|id| scenes.get(id as usize))
                        {
                            let length =
                                i32::from(object.size[usize::from(decoration.rotation & 1 == 0)]);
                            let size =
                                ppt * 2 + i32::from(matches!(object.map_scene, Some(46 | 52)));
                            canvas.sprite(sprite, left, (64 - length - y) * ppt, size);
                        }
                    }
                }
            }
        }
        let pixels = canvas
            .pixels
            .into_iter()
            .flat_map(|pixel| {
                [
                    (pixel >> 16) as u8,
                    (pixel >> 8) as u8,
                    pixel as u8,
                    (pixel >> 24) as u8,
                ]
            })
            .collect();
        RegionImage {
            region: region.region,
            pixels_per_tile: ppt as u8,
            pixels,
            icons,
        }
    }
    fn overlay_color(
        &self,
        material: &FloorMaterial,
        textures: &BTreeMap<u32, u16>,
        background: u32,
    ) -> u32 {
        let packed = if let Some(rgb) = material.secondary {
            crate::color::overlay(rgb)
        } else if let Some(texture) = material.texture {
            i32::from(*textures.get(&texture.get()).unwrap_or(&0))
        } else if material.rgb == 0x00ff_00ff {
            return background;
        } else {
            crate::color::overlay(material.rgb)
        };
        let light = if packed == -1 {
            31
        } else {
            crate::color::light(packed, 96)
        };
        self.palette.get(light as usize).copied().unwrap_or(0) | 0xff00_0000
    }
}
struct Canvas {
    side: i32,
    pixels: Vec<u32>,
}
impl Canvas {
    fn put(&mut self, x: i32, y: i32, color: u32) {
        if (0..self.side).contains(&x) && (0..self.side).contains(&y) {
            self.pixels[(y * self.side + x) as usize] = color;
        }
    }
    fn wall(&mut self, x: i32, y: i32, length: i32, rotation: u8, color: u32) {
        for step in 0..length {
            let (dx, dy) = match rotation & 3 {
                0 => (0, step),
                1 => (step, 0),
                2 => (length - 1, step),
                _ => (step, length - 1),
            };
            self.put(x + dx, y + dy, color);
        }
    }
    fn sprite(&mut self, sprite: &Sprite, x: i32, y: i32, size: i32) {
        if sprite.canvas.contains(&0) {
            return;
        }
        for dy in 0..size {
            for dx in 0..size {
                let sx = dx * i32::from(sprite.canvas[0]) / size - i32::from(sprite.offset[0]);
                let sy = dy * i32::from(sprite.canvas[1]) / size - i32::from(sprite.offset[1]);
                if sx >= 0
                    && sy >= 0
                    && sx < sprite.image.width() as i32
                    && sy < sprite.image.height() as i32
                {
                    let at = (sy as usize * sprite.image.width() as usize + sx as usize) * 4;
                    let p = &sprite.image.pixels()[at..at + 4];
                    if p[3] != 0 {
                        self.put(x + dx, y + dy, u32::from_be_bytes([255, p[0], p[1], p[2]]));
                    }
                }
            }
        }
    }
}
/// Exact template comparisons after the shape-9/10/11 remap in `WorldMapScaleHandler`.
fn mask(size: i32, mut shape: u8, mut rotation: u8, x: i32, y: i32) -> bool {
    if shape == 9 {
        shape = 1;
        rotation = (rotation + 1) & 3;
    }
    if shape == 10 {
        shape = 1;
        rotation = (rotation + 3) & 3;
    }
    if shape == 11 {
        shape = 8;
        rotation = (rotation + 3) & 3;
    }
    let end = size - 1;
    match (shape, rotation & 3) {
        (1, 0) => x <= y,
        (1, 1) => x <= end - y,
        (1, 2) => x >= y,
        (1, 3) => x >= end - y,
        (2, 0) => x <= (end - y) >> 1,
        (2, 1) => x >= y << 1,
        (2, 2) => end - x <= y >> 1,
        (2, 3) => end - x >= (end - y) << 1,
        (3, 0) => end - x <= (end - y) >> 1,
        (3, 1) => x >= (end - y) << 1,
        (3, 2) => x <= y >> 1,
        (3, 3) => end - x >= y << 1,
        (4, 0) => x >= (end - y) >> 1,
        (4, 1) => x <= y << 1,
        (4, 2) => end - x >= y >> 1,
        (4, 3) => end - x <= (end - y) << 1,
        (5, 0) => end - x >= (end - y) >> 1,
        (5, 1) => x <= (end - y) << 1,
        (5, 2) => x >= y >> 1,
        (5, 3) => end - x <= y << 1,
        (6, 0) => x <= size / 2,
        (6, 1) => y <= size / 2,
        (6, 2) => x >= size / 2,
        (6, 3) => y >= size / 2,
        (7, 0) => x <= y - size / 2,
        (7, 1) => x <= end - y - size / 2,
        (7, 2) => end - x <= end - y - size / 2,
        (7, 3) => end - x <= y - size / 2,
        (8, 0) => x >= y - size / 2,
        (8, 1) => x >= end - y - size / 2,
        (8, 2) => end - x >= end - y - size / 2,
        (8, 3) => end - x >= y - size / 2,
        _ => false,
    }
}
