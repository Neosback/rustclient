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
use rustclient_model::{MINIMAP_ZOOM_QUARTERS_DEFAULT, Plane, WorldTile};

/// Minimap pixels per world tile.
pub const TILE_PX: usize = 4;
const WALL_COLOR: u32 = 0x00EE_EEEE;

const DOOR_COLOR: u32 = 0x00EE_0000;
/// Tile setting bits: a bridge deck (shown on the plane below) and tiles hidden from the minimap.
const BRIDGE_DECK: u8 = 0x08;
const HIDDEN_BY_BRIDGE_OR_ROOF: u8 = 0x18;

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

/// A `mapfunction` icon the scene asks the minimap to draw over a location.
#[derive(Debug, Clone, Copy, PartialEq)]
pub struct MinimapIcon {
    /// World map element id, the key of the scene's icon sprites.
    pub element: u32,
    /// Centre of the location in absolute world tile units.
    pub tile: (f64, f64),
}

/// Generated 2D minimap image for the scene.
#[derive(Debug, Clone)]
pub struct MinimapImage {
    pub width: usize,
    pub height: usize,
    pub origin: WorldTile,
    pub rgba: Vec<u8>,
    /// Icons of the locations on this plane, drawn over the composed map.
    pub icons: Vec<MinimapIcon>,
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
        icons: Vec::new(),
    };

    // 1. Terrain: each tile of this plane unless a bridge or roof flag hides it, then the tiles
    // of the plane above that are flagged as a bridge deck over this one.
    paint_terrain(asset, &mut minimap, plane, |settings| {
        settings & HIDDEN_BY_BRIDGE_OR_ROOF == 0
    });
    if let Some(above) = Plane::new(plane.get() + 1) {
        paint_terrain(asset, &mut minimap, above, |settings| {
            settings & BRIDGE_DECK != 0
        });
    }

    // 2. Walls, doors and map scenes, with the same plane and bridge rules as the tiles.
    for square in &asset.sources().squares {
        for loc in &square.locations {
            let drawn = if loc.tile.plane == plane {
                |settings: u8| settings & HIDDEN_BY_BRIDGE_OR_ROOF == 0
            } else if plane.get() + 1 == loc.tile.plane.get() {
                |settings: u8| settings & BRIDGE_DECK != 0
            } else {
                continue;
            };
            let tx = loc.tile.x - origin.x;
            let ty = loc.tile.y - origin.y;
            if tx < 0 || ty < 0 || tx >= width_tiles as i32 || ty >= height_tiles as i32 {
                continue;
            }
            if !asset
                .tile(loc.tile.plane, loc.tile.x, loc.tile.y)
                .is_some_and(|tile| drawn(tile.settings))
            {
                continue;
            }
            let Some(definition) = resolved_object(asset, loc.object) else {
                continue;
            };
            if loc.tile.plane == plane
                && let Some(element) = definition.map_icon
                && asset.sources().map_icons.contains_key(&element)
            {
                let [size_x, size_y] = if loc.orientation.get() & 1 == 1 {
                    [definition.size[1], definition.size[0]]
                } else {
                    definition.size
                };
                minimap.icons.push(MinimapIcon {
                    element,
                    tile: (
                        f64::from(loc.tile.x) + f64::from(size_x) / 2.,
                        f64::from(loc.tile.y) + f64::from(size_y) / 2.,
                    ),
                });
            }
            let px = tx as usize * TILE_PX;
            let py = (height_tiles - 1 - ty as usize) * TILE_PX;
            let color = if definition.int1 != 0 {
                DOOR_COLOR
            } else {
                WALL_COLOR
            };
            let rot = loc.orientation.get() & 3;
            // Map scenes stand in for everything except wall decorations (types 4 to 8).
            if !(4..=8).contains(&loc.shape)
                && let Some(sprite) = definition
                    .map_scene
                    .and_then(|id| asset.sources().map_scenes.get(id as usize))
            {
                let [size_x, size_y] = if rot & 1 == 1 {
                    [definition.size[1], definition.size[0]]
                } else {
                    definition.size
                };
                let (size_x, size_y) = (i32::from(size_x), i32::from(size_y));
                let tile_px = TILE_PX as i32;
                let x = tx * tile_px
                    + (size_x * tile_px - i32::from(sprite.canvas[0])) / 2
                    + i32::from(sprite.offset[0]);
                let y = (height_tiles as i32 - ty - size_y) * tile_px
                    + (size_y * tile_px - i32::from(sprite.canvas[1])) / 2
                    + i32::from(sprite.offset[1]);
                blit(&mut minimap, sprite, x, y);
                continue;
            }

            draw_wall(&mut minimap, loc.shape, rot, (px, py), color);
        }
    }

    minimap
}

/// Draw one wall-like location: an edge (types 0 and 2 also fill the adjoining edge), a corner
/// pixel (type 3) or a diagonal (type 9).
fn draw_wall(minimap: &mut MinimapImage, shape: u8, rot: u8, (px, py): (usize, usize), color: u32) {
    match shape {
        0 | 2 => {
            draw_wall_edge(minimap, px, py, rot, color);
            if shape == 2 {
                draw_wall_edge(minimap, px, py, (rot + 1) & 3, color);
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

/// Paint the terrain of `source` over `minimap` for every tile whose settings pass `wanted`.
fn paint_terrain(
    asset: &SceneAsset,
    minimap: &mut MinimapImage,
    source: Plane,
    wanted: impl Fn(u8) -> bool,
) {
    let origin = minimap.origin;
    let width_tiles = minimap.width / TILE_PX;
    let height_tiles = minimap.height / TILE_PX;
    // Reference colours: the blended underlay and the overlay, each lit at the fixed minimap
    // lightness of 96 rather than shaded per vertex.
    let floors = crate::terrain::underlay_floors(asset, source, width_tiles, height_tiles);
    for tx in 0..width_tiles {
        for ty in 0..height_tiles {
            let wx = origin.x + tx as i32;
            let wy = origin.y + ty as i32;
            let Some(tile) = asset.tile(source, wx, wy) else {
                continue;
            };
            if !wanted(tile.settings) {
                continue;
            }
            let blended = crate::terrain::blended_underlay(
                &floors,
                width_tiles,
                height_tiles,
                tx,
                ty,
                tile.underlay.is_some(),
            );
            let underlay_rgb = paint_rgb(blended);
            let px = tx * TILE_PX;
            let py = (height_tiles - 1 - ty) * TILE_PX;
            if let Some(overlay) = tile.overlay {
                let overlay_rgb = asset
                    .sources()
                    .overlays
                    .get(&overlay.id)
                    .map_or(0, |material| overlay_paint(asset, material));
                // Cache overlay shape 0 is the full square, which the reference numbers 1.
                let shape_idx = (usize::from(overlay.shape) + 1).min(12);
                let rot_idx = usize::from(overlay.rotation & 3);
                for sub_y in 0..TILE_PX {
                    for sub_x in 0..TILE_PX {
                        let mapped =
                            usize::from(TILE_ROTATION_2D[rot_idx][sub_y * TILE_PX + sub_x]);
                        let color = if TILE_SHAPE_2D[shape_idx][mapped] == 0 {
                            underlay_rgb
                        } else {
                            overlay_rgb
                        };
                        if color != 0 {
                            minimap.set_pixel(px + sub_x, py + sub_y, color);
                        }
                    }
                }
            } else if underlay_rgb != 0 {
                for sub_y in 0..TILE_PX {
                    for sub_x in 0..TILE_PX {
                        minimap.set_pixel(px + sub_x, py + sub_y, underlay_rgb);
                    }
                }
            }
        }
    }
}

/// Follow an object's variable morph to the definition the minimap draws: its first target, or
/// the fallback when that is absent. `None` hides the object.
fn resolved_object(
    asset: &SceneAsset,
    id: rustclient_model::ObjectDefinitionId,
) -> Option<&rustclient_cache::world::ObjectDefinition> {
    let definition = asset.sources().objects.get(&id)?;
    let Some(morphs) = &definition.morphs else {
        return Some(definition);
    };
    let target = morphs
        .targets
        .first()
        .copied()
        .flatten()
        .or(morphs.fallback)?;
    asset.sources().objects.get(&target)
}

/// Draw an indexed sprite's opaque pixels with its trimmed top-left at `(x, y)`.
fn blit(minimap: &mut MinimapImage, sprite: &rustclient_cache::Sprite, x: i32, y: i32) {
    let (width, height) = (
        sprite.image.width() as usize,
        sprite.image.height() as usize,
    );
    for row in 0..height {
        for column in 0..width {
            let at = (row * width + column) * 4;
            let pixel = &sprite.image.pixels()[at..at + 4];
            if pixel[3] == 0 {
                continue;
            }
            let (Ok(dx), Ok(dy)) = (
                usize::try_from(x + column as i32),
                usize::try_from(y + row as i32),
            ) else {
                continue;
            };
            let rgb =
                (u32::from(pixel[0]) << 16) | (u32::from(pixel[1]) << 8) | u32::from(pixel[2]);
            minimap.set_pixel(dx, dy, rgb);
        }
    }
}

/// Reference overlay minimap colour: a secondary colour wins, then a texture's average HSL,
/// then the primary colour; the placeholder magenta paints nothing.
fn overlay_paint(asset: &SceneAsset, material: &rustclient_cache::world::FloorMaterial) -> u32 {
    if let Some(secondary) = material.secondary {
        return paint_rgb(crate::color::overlay(secondary));
    }
    let hsl = match material.texture {
        Some(texture) => asset
            .sources()
            .texture_colors
            .get(&texture)
            .map_or(-1, |average| i32::from(*average)),
        None if !material.visible => -2,
        None => crate::color::overlay(material.rgb),
    };
    paint_rgb(hsl)
}

/// Reference minimap paint: palette colour at lightness 96, or 0 (unpainted) without a colour.
fn paint_rgb(hsl: i32) -> u32 {
    match crate::color::light(hsl, 96) {
        crate::color::SKIP => 0,
        lit => crate::color::palette_rgb(lit),
    }
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

/// Revision-240 minimap click: integer rotation and footprint-centred world fine coordinates.
#[must_use]
pub fn click_destination(
    offset: [i32; 2],
    fine: [i64; 2],
    yaw: u16,
    zoom_quarters: i32,
) -> Option<rustclient_model::MapDestination> {
    let radians = f64::from(yaw & 2047) * std::f64::consts::TAU / 2048.;
    let sin = (radians.sin() * 65536.) as i64;
    let cos = (radians.cos() * 65536.) as i64;
    let [dx, dy] = offset.map(i64::from);
    // A click covers fewer tiles per pixel the further the map is zoomed in.
    let scale = |value: i64| {
        value * i64::from(MINIMAP_ZOOM_QUARTERS_DEFAULT) / i64::from(zoom_quarters.max(1))
    };
    let x = fine[0].checked_add(scale(cos * dx + sin * dy) >> 11)?;
    let y = fine[1].checked_sub(scale(cos * dy - sin * dx) >> 11)?;
    Some(rustclient_model::MapDestination {
        x: i32::try_from(x >> 7).ok()?,
        y: i32::try_from(y >> 7).ok()?,
    })
}

/// Reference 16.16 sine/cosine for a 0..2047 camera angle. The client divides by `326.11`
/// (not exactly `2048 / TAU`) and truncates toward zero; matching it keeps cardinal rotations
/// pixel-identical instead of drifting by one pixel from float rounding.
fn rotation_16_16(angle: u16) -> (i64, i64) {
    let radians = f64::from(angle & 2047) / 326.11;
    (
        (radians.sin() * 65536.) as i64,
        (radians.cos() * 65536.) as i64,
    )
}

/// Compose the rotating minimap through the minimap widget's own mask sprite.
///
/// Mirrors the reference `SpritePixels.drawRotatedMaskedCenteredAround`: destination pixel
/// `(i, j)` sits at `(i - w/2, j - h/2)` from the mask centre and samples the scene sprite at
/// the integer focus pixel plus that offset rotated by the camera yaw. Transparent mask pixels
/// are the visible span. `zoom_quarters` is the tile scale in quarter-steps: 16 shows the stored
/// 4 pixels per tile one to one, 32 doubles it. Returns `mask_width * mask_height * 4` RGBA bytes.
#[must_use]
pub fn compose_minimap(
    mask_rgba: &[u8],
    mask_width: usize,
    mask_height: usize,
    minimap: &MinimapImage,
    camera: (f64, f64),
    angle: u16,
    zoom_quarters: i32,
) -> Vec<u8> {
    let mut out = vec![0_u8; mask_width * mask_height * 4];
    if mask_rgba.len() != out.len() || minimap.width == 0 || minimap.height == 0 {
        return out;
    }
    let focus_x = ((camera.0 - f64::from(minimap.origin.x)) * TILE_PX as f64).floor() as i32;
    let focus_y = minimap.height as i32
        - 1
        - ((camera.1 - f64::from(minimap.origin.y)) * TILE_PX as f64).floor() as i32;
    let (sin, cos) = rotation_16_16(angle);
    // Zooming in samples fewer source pixels per screen pixel.
    let (sin, cos) = (
        sin * i64::from(MINIMAP_ZOOM_QUARTERS_DEFAULT) / i64::from(zoom_quarters.max(1)),
        cos * i64::from(MINIMAP_ZOOM_QUARTERS_DEFAULT) / i64::from(zoom_quarters.max(1)),
    );
    let (half_w, half_h) = ((mask_width / 2) as i32, (mask_height / 2) as i32);
    for y in 0..mask_height {
        let dy = y as i32 - half_h;
        for x in 0..mask_width {
            let mask_idx = (y * mask_width + x) * 4;
            if mask_rgba[mask_idx + 3] != 0 {
                continue;
            }
            let dx = x as i32 - half_w;
            let source_x =
                ((i64::from(focus_x) << 16) + i64::from(dy) * sin + i64::from(dx) * cos) >> 16;
            let source_y =
                ((i64::from(focus_y) << 16) + i64::from(dy) * cos - i64::from(dx) * sin) >> 16;
            if source_x < 0
                || source_y < 0
                || source_x >= minimap.width as i64
                || source_y >= minimap.height as i64
            {
                continue;
            }
            let src_idx = (source_y as usize * minimap.width + source_x as usize) * 4;
            if minimap.rgba[src_idx + 3] != 0 {
                out[mask_idx..mask_idx + 4].copy_from_slice(&minimap.rgba[src_idx..src_idx + 4]);
            }
        }
    }
    out
}

/// An actor marker on the minimap.
#[derive(Debug, Clone, Copy)]
pub struct MinimapDot {
    /// Position in world tile units (fractional while the actor walks).
    pub tile: (f64, f64),
    /// Frame of the `mapdots` group: 0 item, 1 NPC, 2 player, 3 friend, 4 team.
    pub frame: usize,
}

/// The dot sprites with each replacement color applied, as the `RuneLite` Minimap plugin does.
///
/// The source replaces a map dot by a 4x5 sprite of one color with a dark lower rim; a kind
/// without a color keeps its cache sprite. Borrowed when nothing is replaced.
#[must_use]
pub fn recolored_dots<'a>(
    dots: &'a [rustclient_cache::Sprite],
    colors: &[Option<u32>],
) -> std::borrow::Cow<'a, [rustclient_cache::Sprite]> {
    if colors.iter().all(Option::is_none) {
        return std::borrow::Cow::Borrowed(dots);
    }
    std::borrow::Cow::Owned(
        dots.iter()
            .enumerate()
            .map(|(frame, dot)| {
                colors
                    .get(frame)
                    .copied()
                    .flatten()
                    .and_then(|argb| recolored_dot(dot, argb))
                    .unwrap_or_else(|| dot.clone())
            })
            .collect(),
    )
}

/// Width and height of the sprite `RuneLite` substitutes for a map dot.
const REPLACEMENT_DOT: [usize; 2] = [4, 5];

/// One replacement dot: `1` is the near-black rim pixel, `2` the color, `0` transparent.
const REPLACEMENT_DOT_SHAPE: [u8; 20] =
    [0, 2, 2, 0, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 1, 0, 1, 1, 0];

fn recolored_dot(
    template: &rustclient_cache::Sprite,
    argb: u32,
) -> Option<rustclient_cache::Sprite> {
    let [red, green, blue] = [(argb >> 16) as u8, (argb >> 8) as u8, argb as u8];
    let mut rgba = Vec::with_capacity(REPLACEMENT_DOT_SHAPE.len() * 4);
    for cell in REPLACEMENT_DOT_SHAPE {
        rgba.extend_from_slice(&match cell {
            0 => [0, 0, 0, 0],
            1 => [0, 0, 1, 255],
            _ => [red, green, blue, 255],
        });
    }
    let image = rustclient_cache::Image::new(
        REPLACEMENT_DOT[0] as u32,
        REPLACEMENT_DOT[1] as u32,
        rgba,
        rustclient_cache::Source::group(
            rustclient_cache::IndexId(8),
            rustclient_cache::GroupId(template.id.get()),
        ),
    )
    .ok()?;
    Some(rustclient_cache::Sprite {
        id: template.id,
        frame: template.frame,
        // The replacement sprite is its own canvas, as `createSpritePixels` makes it.
        canvas: [REPLACEMENT_DOT[0] as u16, REPLACEMENT_DOT[1] as u16],
        offset: [0, 0],
        image,
    })
}

/// Draw `dots` over a composed minimap (`mask_width * mask_height` RGBA bytes).
///
/// Each dot is placed by the inverse of the rotation `compose_minimap` samples through, then
/// drawn upright and centred, inside the mask's visible span only.
pub fn draw_dots(
    out: &mut [u8],
    mask_rgba: &[u8],
    mask_size: [usize; 2],
    sprites: &[rustclient_cache::Sprite],
    dots: &[MinimapDot],
    view: MinimapView,
) {
    let [mask_width, mask_height] = mask_size;
    if out.len() != mask_width * mask_height * 4 || mask_rgba.len() != out.len() {
        return;
    }
    for dot in dots {
        if let Some(sprite) = sprites.get(dot.frame) {
            draw_marker(out, mask_rgba, mask_size, sprite, dot.tile, view);
        }
    }
}

/// Draw an upright cache sprite at its rotated minimap position, retaining canvas trim offsets.
pub fn draw_marker(
    out: &mut [u8],
    mask_rgba: &[u8],
    mask_size: [usize; 2],
    sprite: &rustclient_cache::Sprite,
    tile: (f64, f64),
    view: MinimapView,
) {
    let MinimapView {
        camera,
        angle,
        zoom_quarters,
    } = view;
    let [mask_width, mask_height] = mask_size;
    if out.len() != mask_width * mask_height * 4 || mask_rgba.len() != out.len() {
        return;
    }
    let radians = f64::from(angle & 2047) * std::f64::consts::TAU / 2048.;
    let (sin, cos) = (
        (radians.sin() * 65536.) as i64,
        (radians.cos() * 65536.) as i64,
    );
    // Offsets are measured in stored minimap pixels, then scaled to the zoomed screen.
    let zoom = i64::from(zoom_quarters.max(1));
    let default_zoom = i64::from(MINIMAP_ZOOM_QUARTERS_DEFAULT);
    let offset_x =
        ((tile.0 * 4.).floor() as i64 - (camera.0 * 4.).floor() as i64) * zoom / default_zoom;
    let offset_y =
        ((camera.1 * 4.).floor() as i64 - (tile.1 * 4.).floor() as i64) * zoom / default_zoom;
    if offset_x.unsigned_abs() > 80
        || offset_y.unsigned_abs() > 80
        || offset_x * offset_x + offset_y * offset_y > 6400
    {
        return;
    }
    let centre_x = (mask_width / 2) as i64 + ((offset_x * cos - offset_y * sin) >> 16);
    let centre_y = (mask_height / 2) as i64 + ((offset_x * sin + offset_y * cos) >> 16);
    let left = centre_x - i64::from(sprite.canvas[0] / 2) + i64::from(sprite.offset[0]);
    let top = centre_y - i64::from(sprite.canvas[1] / 2) + i64::from(sprite.offset[1]);
    let (width, height) = (
        i64::from(sprite.image.width()),
        i64::from(sprite.image.height()),
    );
    for row in 0..height {
        for column in 0..width {
            let at = ((row * width + column) * 4) as usize;
            let pixel = &sprite.image.pixels()[at..at + 4];
            let (x, y) = (left + column, top + row);
            if pixel[3] == 0 || x < 0 || y < 0 || x >= mask_width as i64 || y >= mask_height as i64
            {
                continue;
            }
            let target = (y as usize * mask_width + x as usize) * 4;
            if mask_rgba[target + 3] == 0 {
                out[target..target + 4].copy_from_slice(pixel);
            }
        }
    }
}

/// Draw the scene's `mapfunction` icons over a composed minimap.
///
/// `sprites` maps element ids to their icon. Icons follow the same rotation and zoom as dots and
/// are skipped below `icon_zoom_limit` whole zoom steps.
pub fn draw_icons(
    out: &mut [u8],
    mask_rgba: &[u8],
    mask_size: [usize; 2],
    sprites: &std::collections::BTreeMap<u32, rustclient_cache::Sprite>,
    icons: &[MinimapIcon],
    view: MinimapView,
    icon_zoom_limit: i32,
) {
    if view.zoom_quarters / 4 < icon_zoom_limit {
        return;
    }
    for icon in icons {
        if let Some(sprite) = sprites.get(&icon.element) {
            draw_marker(out, mask_rgba, mask_size, sprite, icon.tile, view);
        }
    }
}

/// Where the minimap is looking: the camera focus tile, yaw and tile scale.
#[derive(Debug, Clone, Copy)]
pub struct MinimapView {
    pub camera: (f64, f64),
    pub angle: u16,
    pub zoom_quarters: i32,
}

/// The reference local-player marker is a three-pixel white square at the integer mask centre.
pub fn draw_local_player(out: &mut [u8], mask: &[u8], size: [usize; 2]) {
    let [width, height] = size;
    if width < 3 || height < 3 || out.len() != width * height * 4 || mask.len() != out.len() {
        return;
    }
    for y in height / 2 - 1..=height / 2 + 1 {
        for x in width / 2 - 1..=width / 2 + 1 {
            let at = (y * width + x) * 4;
            if mask[at + 3] == 0 {
                out[at..at + 4].fill(255);
            }
        }
    }
}

/// Compose the rotating compass through the compass widget's own mask sprite, pivoting on the
/// compass sprite's integer centre exactly like the minimap (same rotation handedness).
/// Returns `mask_width * mask_height * 4` RGBA bytes.
#[must_use]
pub fn compose_compass(
    compass_rgba: &[u8],
    compass_width: usize,
    compass_height: usize,
    mask_rgba: &[u8],
    mask_width: usize,
    mask_height: usize,
    angle: u16,
) -> Vec<u8> {
    let mut out = vec![0_u8; mask_width * mask_height * 4];
    if mask_rgba.len() != out.len() || compass_rgba.len() != compass_width * compass_height * 4 {
        return out;
    }
    let (sin, cos) = rotation_16_16(angle);
    let (pivot_x, pivot_y) = ((compass_width / 2) as i32, (compass_height / 2) as i32);
    let (half_w, half_h) = ((mask_width / 2) as i32, (mask_height / 2) as i32);
    for y in 0..mask_height {
        let dy = y as i32 - half_h;
        for x in 0..mask_width {
            let mask_idx = (y * mask_width + x) * 4;
            if mask_rgba[mask_idx + 3] != 0 {
                continue;
            }
            let dx = x as i32 - half_w;
            let source_x =
                ((i64::from(pivot_x) << 16) + i64::from(dy) * sin + i64::from(dx) * cos) >> 16;
            let source_y =
                ((i64::from(pivot_y) << 16) + i64::from(dy) * cos - i64::from(dx) * sin) >> 16;
            if source_x < 0
                || source_y < 0
                || source_x >= compass_width as i64
                || source_y >= compass_height as i64
            {
                continue;
            }
            let src_idx = (source_y as usize * compass_width + source_x as usize) * 4;
            if compass_rgba[src_idx + 3] != 0 {
                out[mask_idx..mask_idx + 4].copy_from_slice(&compass_rgba[src_idx..src_idx + 4]);
            }
        }
    }
    out
}

#[cfg(test)]
mod tests {
    use super::*;

    fn opaque_mask(width: usize, height: usize) -> Vec<u8> {
        // Alpha zero is the visible span in the reference mask sprites.
        vec![0; width * height * 4]
    }

    fn marker_compass() -> Vec<u8> {
        let mut pixels = vec![0_u8; 9 * 9 * 4];
        // North marker: top-centre of a 9x9 sprite.
        pixels[(9 + 4) * 4..(9 + 4) * 4 + 4].copy_from_slice(&[255, 0, 0, 255]);
        pixels
    }

    fn marker_position(rgba: &[u8], width: usize) -> Option<(usize, usize)> {
        rgba.chunks_exact(4)
            .position(|p| p[3] != 0)
            .map(|i| (i % width, i / width))
    }

    #[test]
    fn compass_north_stays_up_at_zero_yaw() {
        let out = compose_compass(&marker_compass(), 9, 9, &opaque_mask(9, 9), 9, 9, 0);
        assert_eq!(marker_position(&out, 9), Some((4, 1)));
    }

    #[test]
    fn compass_and_minimap_turn_the_same_way() {
        // At a quarter turn the reference moves world north to the right of the minimap.
        let quarter = 512;
        let out = compose_compass(&marker_compass(), 9, 9, &opaque_mask(9, 9), 9, 9, quarter);
        let (x, y) = marker_position(&out, 9).expect("rotated north marker");
        assert!(x > 4 && y == 4, "north at ({x}, {y})");

        let mut image = MinimapImage {
            width: 64,
            height: 64,
            origin: WorldTile::new(0, 0, Plane::MIN),
            rgba: vec![0; 64 * 64 * 4],
            icons: Vec::new(),
        };
        // One lit tile directly north of the focus (tile 8, 12).
        for sub in 0..16 {
            image.set_pixel(32 + sub % 4, 64 - 1 - 48 - sub / 4, 0x00FF_0000);
        }
        let out = compose_minimap(
            &opaque_mask(33, 33),
            33,
            33,
            &image,
            (8.5, 8.5),
            quarter,
            MINIMAP_ZOOM_QUARTERS_DEFAULT,
        );
        let (x, y) = marker_position(&out, 33).expect("rotated north tile");
        assert!(x > 16 && y.abs_diff(16) <= 2, "north tile at ({x}, {y})");
    }

    fn view(angle: u16, zoom_quarters: i32) -> MinimapView {
        MinimapView {
            camera: (8.5, 8.5),
            angle,
            zoom_quarters,
        }
    }

    fn dot_sprite() -> rustclient_cache::Sprite {
        use rustclient_cache::{GroupId, Image, IndexId, Source};
        rustclient_cache::Sprite {
            id: rustclient_model::SpriteId::new(0),
            frame: 0,
            canvas: [1, 1],
            offset: [0, 0],
            image: Image::new(
                1,
                1,
                vec![255, 255, 0, 255],
                Source::group(IndexId(8), GroupId(0)),
            )
            .unwrap(),
        }
    }

    #[test]
    fn recolored_dots_replace_only_the_kinds_with_a_color_and_keep_the_rim() {
        let stock = [dot_sprite(), dot_sprite()];
        assert!(matches!(
            recolored_dots(&stock, &[None, None]),
            std::borrow::Cow::Borrowed(_)
        ));
        let dots = recolored_dots(&stock, &[None, Some(0xFF11_2233)]);
        assert_eq!(dots[0].image.pixels(), stock[0].image.pixels());
        let dot = &dots[1];
        assert_eq!((dot.image.width(), dot.image.height()), (4, 5));
        assert_eq!(dot.canvas, [4, 5]);
        let pixel = |column: usize, row: usize| {
            let at = (row * 4 + column) * 4;
            dot.image.pixels()[at..at + 4].to_vec()
        };
        assert_eq!(pixel(0, 0), [0, 0, 0, 0], "corners stay transparent");
        assert_eq!(pixel(1, 0), [0x11, 0x22, 0x33, 255]);
        assert_eq!(pixel(0, 3), [0, 0, 1, 255], "the lower rim is near black");
        assert_eq!(pixel(1, 4), [0, 0, 1, 255]);
    }

    #[test]
    fn dots_follow_the_minimap_rotation() {
        let mask = opaque_mask(33, 33);
        let dots = [MinimapDot {
            tile: (8.5, 12.5),
            frame: 0,
        }];
        let place = |angle| {
            let mut out = vec![0_u8; 33 * 33 * 4];
            draw_dots(
                &mut out,
                &mask,
                [33, 33],
                &[dot_sprite()],
                &dots,
                view(angle, MINIMAP_ZOOM_QUARTERS_DEFAULT),
            );
            marker_position(&out, 33).expect("dot drawn")
        };
        // Four tiles north of the focus is sixteen pixels straight up, then to the right at a
        // quarter turn like the compass.
        assert_eq!(place(0), (16, 0));
        let (x, y) = place(512);
        assert!(x >= 31 && y.abs_diff(16) <= 1, "dot at ({x}, {y})");
    }

    #[test]
    fn dots_outside_the_visible_span_are_clipped() {
        let mut mask = opaque_mask(33, 33);
        mask[(16 * 33 + 16) * 4 + 3] = 255;
        let mut out = vec![0_u8; 33 * 33 * 4];
        let dots = [MinimapDot {
            tile: (8.5, 8.5),
            frame: 0,
        }];
        draw_dots(
            &mut out,
            &mask,
            [33, 33],
            &[dot_sprite()],
            &dots,
            view(0, MINIMAP_ZOOM_QUARTERS_DEFAULT),
        );
        assert!(out.iter().all(|byte| *byte == 0));
    }

    #[test]
    fn non_square_masks_compose_at_their_own_size() {
        let mask = opaque_mask(145, 151);
        let image = MinimapImage {
            width: 16,
            height: 16,
            origin: WorldTile::new(0, 0, Plane::MIN),
            rgba: vec![255; 16 * 16 * 4],
            icons: Vec::new(),
        };
        let out = compose_minimap(&mask, 145, 151, &image, (2., 2.), 0, 16);
        assert_eq!(out.len(), 145 * 151 * 4);
        let wrong_size =
            compose_minimap(&mask[..mask.len() - 4], 145, 151, &image, (2., 2.), 0, 16);
        assert!(wrong_size.iter().all(|b| *b == 0));
    }

    #[test]
    fn zoom_changes_the_scale_of_composition_clicks_and_markers() {
        let mut image = MinimapImage {
            width: 64,
            height: 64,
            origin: WorldTile::new(0, 0, Plane::MIN),
            rgba: vec![0; 64 * 64 * 4],
            icons: Vec::new(),
        };
        // One lit tile (4x4 pixels) two tiles east of the focus tile (8, 8).
        for sub in 0..16 {
            image.set_pixel(40 + sub % 4, 64 - 1 - 32 - sub / 4, 0x00FF_0000);
        }
        let mask = opaque_mask(65, 65);
        let lit_columns = |zoom| {
            let out = compose_minimap(&mask, 65, 65, &image, (8.5, 8.5), 0, zoom);
            let columns: Vec<usize> = out
                .chunks_exact(4)
                .enumerate()
                .filter(|(_, p)| p[3] != 0)
                .map(|(i, _)| i % 65)
                .collect();
            (columns.iter().min().copied(), columns.iter().max().copied())
        };
        let (near_start, near_end) = lit_columns(32);
        let (far_start, far_end) = lit_columns(8);
        let width = |a: Option<usize>, b: Option<usize>| b.unwrap() - a.unwrap() + 1;
        assert_eq!(width(far_start, far_end), 2, "a tile is 2 pixels at 2x");
        assert_eq!(width(near_start, near_end), 8, "a tile is 8 pixels at 8x");

        // Sixteen pixels east is four tiles at the default scale and two tiles at 2x.
        let fine = [8 * 128, 8 * 128];
        let east = |zoom| click_destination([16, 0], fine, 0, zoom).unwrap().x;
        assert_eq!(east(16), 12);
        assert_eq!(east(32), 10);
        assert_eq!(east(8), 16);

        // Markers keep their sprite size and move with the scale: four tiles north is 16 pixels at 4x.
        let place = |zoom| {
            let mut out = vec![0_u8; 33 * 33 * 4];
            draw_marker(
                &mut out,
                &opaque_mask(33, 33),
                [33, 33],
                &dot_sprite(),
                (8.5, 12.5),
                view(0, zoom),
            );
            marker_position(&out, 33)
        };
        assert_eq!(place(16), Some((16, 0)));
        assert_eq!(place(8), Some((16, 8)));
        assert_eq!(
            place(32),
            None,
            "eight tiles out is beyond the visible radius"
        );
    }
}
