//! World map viewport and overview composition.
#![allow(
    clippy::too_many_lines,
    clippy::too_many_arguments,
    clippy::similar_names,
    clippy::cast_lossless,
    clippy::cast_possible_truncation,
    clippy::cast_sign_loss,
    clippy::cast_precision_loss,
    clippy::cast_possible_wrap,
    clippy::manual_range_contains,
    clippy::suboptimal_flops,
    clippy::collapsible_if
)]
mod regions;
pub use regions::{RegionImage, RegionPainter};
/// Exact discrete zoom conversion used by the selected client's world map.
#[must_use]
#[expect(
    clippy::float_cmp,
    reason = "authored percentage 37 has the exact source scale 1.5"
)]
pub fn pixels_per_tile(percent: f32) -> f32 {
    if percent == 37. {
        1.5
    } else {
        (percent / 25.).clamp(1., 8.)
    }
}
use rustclient_cache::Sprite;
use rustclient_cache::osrs240::world_map::{
    MapCoord, WorldMapArea, WorldMapComposite, WorldMapElement,
};
use std::collections::{BTreeMap, BTreeSet};

/// Composes the main interactive world map viewport (contentType 1400 / component 595:8).
pub fn compose_world_map_viewport(
    width: u32,
    height: u32,
    center_tile: [i32; 2],
    zoom_pct: f32,
    area: Option<&WorldMapArea>,
    composite: Option<&WorldMapComposite>,
    elements: &BTreeMap<u32, WorldMapElement>,
    element_sprites: &BTreeMap<u32, Sprite>,
    disabled_elements: &BTreeSet<u32>,
    disabled_categories: &BTreeSet<u32>,
    elements_disabled: bool,
    flashing_elements: &BTreeSet<u32>,
    flashing_categories: &BTreeSet<u32>,
    flash_cycle: i32,
    player_coord: Option<MapCoord>,
    regions: &BTreeMap<[i32; 2], RegionImage>,
    fonts: &BTreeMap<
        rustclient_cache::osrs240::world_map::WorldMapLabelSize,
        rustclient_cache::BitmapFont,
    >,
    target_buffer: &mut Vec<u8>,
) {
    let size = (width * height * 4) as usize;
    target_buffer.resize(size, 0);

    // 1. Fill background with area viewport background or dark navy default
    let bg_color = area.map_or(0xFF16_2332, |a| {
        if a.viewport_background != 0 {
            0xFF00_0000 | (a.viewport_background & 0x00FF_FFFF)
        } else {
            0xFF16_2332
        }
    });
    let bg_rgba = [
        ((bg_color >> 16) & 0xFF) as u8,
        ((bg_color >> 8) & 0xFF) as u8,
        (bg_color & 0xFF) as u8,
        ((bg_color >> 24) & 0xFF) as u8,
    ];
    for chunk in target_buffer.chunks_exact_mut(4) {
        chunk.copy_from_slice(&bg_rgba);
    }

    if width == 0 || height == 0 {
        return;
    }

    let pixels_per_tile = pixels_per_tile(zoom_pct);

    // Region rasters use geography, floor shapes, walls and map-scene sprites.
    // The overview is exclusively used by the overview widget.
    for image in regions.values() {
        let left =
            width as f32 / 2. + ((image.region[0] * 64 - center_tile[0]) as f32) * pixels_per_tile;
        let top = height as f32 / 2.
            - ((image.region[1] * 64 + 64 - center_tile[1]) as f32) * pixels_per_tile;
        let span = 64. * pixels_per_tile;
        let x0 = left.ceil().clamp(0., width as f32) as u32;
        let y0 = top.ceil().clamp(0., height as f32) as u32;
        let x1 = (left + span).ceil().clamp(0., width as f32) as u32;
        let y1 = (top + span).ceil().clamp(0., height as f32) as u32;
        let side = usize::from(image.pixels_per_tile) * 64;
        let scale = f32::from(image.pixels_per_tile) / pixels_per_tile;
        for sy in y0..y1 {
            let py = ((sy as f32 - top) * scale) as usize;
            if py >= side {
                continue;
            }
            for sx in x0..x1 {
                let px = ((sx as f32 - left) * scale) as usize;
                if px < side {
                    let from = (py * side + px) * 4;
                    let to = (sy * width + sx) as usize * 4;
                    target_buffer[to..to + 4].copy_from_slice(&image.pixels[from..from + 4]);
                }
            }
        }
    }
    if let (Some(area), Some(composite)) = (area, composite) {
        // 3. Draw static icons
        if !elements_disabled {
            let half_w = width as f32 / 2.0;
            let half_h = height as f32 / 2.0;

            for icon in composite
                .icons
                .iter()
                .chain(regions.values().flat_map(|r| r.icons.iter()))
            {
                let Some(elem_id) = icon.element else {
                    continue;
                };
                if disabled_elements.contains(&elem_id) {
                    continue;
                }
                let elem = elements.get(&elem_id);
                if elem.is_none_or(|e| !e.world_map_visible) {
                    continue;
                }
                if let Some(elem) = elem {
                    if let Some(cat) = elem.category {
                        if disabled_categories.contains(&cat) {
                            continue;
                        }
                    }
                }

                let screen_x = (half_w
                    + (icon.coord.x as f32 - center_tile[0] as f32) * pixels_per_tile)
                    as i32;
                let screen_y = (half_h
                    - (icon.coord.y as f32 - center_tile[1] as f32) * pixels_per_tile)
                    as i32;

                if screen_x < -24
                    || screen_x > width as i32 + 24
                    || screen_y < -24
                    || screen_y > height as i32 + 24
                {
                    continue;
                }

                let is_flashing = flashing_elements.contains(&elem_id)
                    || elem
                        .and_then(|e| e.category)
                        .is_some_and(|c| flashing_categories.contains(&c));

                if is_flashing && (flash_cycle % 20 < 10) {
                    draw_circle(
                        target_buffer,
                        width,
                        height,
                        screen_x,
                        screen_y,
                        10,
                        [255, 230, 0, 220],
                    );
                }

                if let Some(element) = elem
                    && let Some(name) = &element.name
                    && let Some(size) =
                        rustclient_cache::osrs240::world_map::WorldMapLabelSize::from_text_size(
                            element.text_size,
                        )
                    && pixels_per_tile >= size.min_zoom()
                    && let Some(font) = fonts.get(&size)
                {
                    draw_label(
                        target_buffer,
                        [width, height],
                        [screen_x, screen_y],
                        name,
                        font,
                        element.label_color,
                    );
                }
                if let Some(sprite_id) = elem.and_then(|e| e.sprite) {
                    if let Some(sprite) = element_sprites.get(&sprite_id) {
                        draw_sprite_centered(
                            target_buffer,
                            width,
                            height,
                            sprite,
                            screen_x,
                            screen_y,
                        );
                    }
                }
            }
        }

        // 4. Draw player position marker
        if let Some(player) = player_coord {
            if let Some([dx, dy]) = area.position(player.plane, player.x, player.y) {
                let half_w = width as f32 / 2.0;
                let half_h = height as f32 / 2.0;
                let px = (half_w + (dx as f32 - center_tile[0] as f32) * pixels_per_tile) as i32;
                let py = (half_h - (dy as f32 - center_tile[1] as f32) * pixels_per_tile) as i32;

                if px >= 0 && px < width as i32 && py >= 0 && py < height as i32 {
                    // Pulsing outer ring
                    draw_circle(target_buffer, width, height, px, py, 7, [255, 50, 50, 200]);
                    draw_circle(
                        target_buffer,
                        width,
                        height,
                        px,
                        py,
                        4,
                        [255, 255, 255, 255],
                    );
                    draw_circle(target_buffer, width, height, px, py, 2, [255, 0, 0, 255]);
                }
            }
        }
    }
}

/// Composes the small overview map widget (contentType 1401 / component 595:12).
pub fn compose_world_map_overview(
    width: u32,
    height: u32,
    center_tile: [i32; 2],
    viewport_size_tiles: [i32; 2],
    area: Option<&WorldMapArea>,
    composite: Option<&WorldMapComposite>,
    target_buffer: &mut Vec<u8>,
) {
    let size = (width * height * 4) as usize;
    target_buffer.resize(size, 0);

    // Dark background
    for chunk in target_buffer.chunks_exact_mut(4) {
        chunk.copy_from_slice(&[15, 22, 30, 255]);
    }

    if width == 0 || height == 0 {
        return;
    }

    if let (Some(area), Some(composite)) = (area, composite) {
        if let Some(ref overview) = composite.overview {
            let spr_w = overview.image.width();
            let spr_h = overview.image.height();
            let spr_pixels = overview.image.pixels();

            if spr_w > 0 && spr_h > 0 {
                for dy in 0..height {
                    let sy = (dy * spr_h / height).min(spr_h - 1);
                    let dst_row = (dy * width * 4) as usize;
                    let src_row = (sy * spr_w * 4) as usize;
                    for dx in 0..width {
                        let sx = (dx * spr_w / width).min(spr_w - 1);
                        let src_idx = src_row + (sx * 4) as usize;
                        let dst_idx = dst_row + (dx * 4) as usize;
                        target_buffer[dst_idx..dst_idx + 4]
                            .copy_from_slice(&spr_pixels[src_idx..src_idx + 4]);
                    }
                }
            }
        }

        // Draw view indicator box
        let total_w = ((area.region_high[0] - area.region_low[0] + 1) * 64) as f32;
        let total_h = ((area.region_high[1] - area.region_low[1] + 1) * 64) as f32;
        let min_x = (area.region_low[0] * 64) as f32;
        let min_y = (area.region_low[1] * 64) as f32;

        if total_w > 0.0 && total_h > 0.0 {
            let norm_x = (center_tile[0] as f32 - min_x) / total_w;
            let norm_y = 1.0 - (center_tile[1] as f32 - min_y) / total_h;

            let bx = (norm_x * width as f32) as i32;
            let by = (norm_y * height as f32) as i32;

            let bw = ((viewport_size_tiles[0] as f32 / total_w) * width as f32).max(10.0) as i32;
            let bh = ((viewport_size_tiles[1] as f32 / total_h) * height as f32).max(8.0) as i32;

            let x0 = bx - bw / 2;
            let y0 = by - bh / 2;
            draw_rect_outline(
                target_buffer,
                width,
                height,
                x0,
                y0,
                bw,
                bh,
                [255, 255, 255, 255],
            );
        }
    }
}

fn draw_label(
    buf: &mut [u8],
    size: [u32; 2],
    center: [i32; 2],
    text: &str,
    font: &rustclient_cache::BitmapFont,
    color: u32,
) {
    for (line, text) in text.split("<br>").enumerate() {
        let mut x = center[0] - font.width(text) / 2;
        let y = center[1] + line as i32 * i32::from(font.ascent) / 2;
        let mut previous = None;
        for ch in text.chars() {
            let byte = rustclient_cache::cp1252(ch).unwrap_or(b'?');
            let advance = font.advance(previous, byte);
            x += advance - i32::from(font.advances[usize::from(byte)]);
            if let Some(glyph) = font.glyphs.get(usize::from(byte)) {
                for gy in 0..glyph.image.height() {
                    for gx in 0..glyph.image.width() {
                        let px = x + i32::from(glyph.offset[0]) + gx as i32;
                        let py = y + i32::from(glyph.offset[1]) + gy as i32;
                        if px >= 0
                            && py >= 0
                            && px < size[0] as i32
                            && py < size[1] as i32
                            && glyph.image.pixels()
                                [(gy * glyph.image.width() + gx) as usize * 4 + 3]
                                != 0
                        {
                            let at = (py as u32 * size[0] + px as u32) as usize * 4;
                            buf[at..at + 4].copy_from_slice(&[
                                (color >> 16) as u8,
                                (color >> 8) as u8,
                                color as u8,
                                255,
                            ]);
                        }
                    }
                }
            }
            x += i32::from(font.advances[usize::from(byte)]);
            previous = Some(byte);
        }
    }
}

fn draw_circle(buf: &mut [u8], w: u32, h: u32, cx: i32, cy: i32, r: i32, rgba: [u8; 4]) {
    let r2 = r * r;
    for dy in -r..=r {
        let py = cy + dy;
        if py < 0 || py >= h as i32 {
            continue;
        }
        for dx in -r..=r {
            if dx * dx + dy * dy <= r2 {
                let px = cx + dx;
                if px >= 0 && px < w as i32 {
                    let idx = ((py as u32 * w + px as u32) * 4) as usize;
                    buf[idx..idx + 4].copy_from_slice(&rgba);
                }
            }
        }
    }
}

fn draw_rect_outline(
    buf: &mut [u8],
    w: u32,
    h: u32,
    x0: i32,
    y0: i32,
    rw: i32,
    rh: i32,
    rgba: [u8; 4],
) {
    for dx in 0..rw {
        let px = x0 + dx;
        if px >= 0 && px < w as i32 {
            if y0 >= 0 && y0 < h as i32 {
                let idx = ((y0 as u32 * w + px as u32) * 4) as usize;
                buf[idx..idx + 4].copy_from_slice(&rgba);
            }
            let y1 = y0 + rh - 1;
            if y1 >= 0 && y1 < h as i32 {
                let idx = ((y1 as u32 * w + px as u32) * 4) as usize;
                buf[idx..idx + 4].copy_from_slice(&rgba);
            }
        }
    }
    for dy in 0..rh {
        let py = y0 + dy;
        if py >= 0 && py < h as i32 {
            if x0 >= 0 && x0 < w as i32 {
                let idx = ((py as u32 * w + x0 as u32) * 4) as usize;
                buf[idx..idx + 4].copy_from_slice(&rgba);
            }
            let x1 = x0 + rw - 1;
            if x1 >= 0 && x1 < w as i32 {
                let idx = ((py as u32 * w + x1 as u32) * 4) as usize;
                buf[idx..idx + 4].copy_from_slice(&rgba);
            }
        }
    }
}

fn draw_sprite_centered(buf: &mut [u8], w: u32, h: u32, sprite: &Sprite, cx: i32, cy: i32) {
    let sw = sprite.image.width() as i32;
    let sh = sprite.image.height() as i32;
    let ox = cx - sw / 2 + i32::from(sprite.offset[0]);
    let oy = cy - sh / 2 + i32::from(sprite.offset[1]);
    let spr_pixels = sprite.image.pixels();

    for sy in 0..sh {
        let py = oy + sy;
        if py < 0 || py >= h as i32 {
            continue;
        }
        let src_row = (sy as u32 * sprite.image.width() * 4) as usize;
        let dst_row = (py as u32 * w * 4) as usize;

        for sx in 0..sw {
            let px = ox + sx;
            if px < 0 || px >= w as i32 {
                continue;
            }
            let src_idx = src_row + (sx as u32 * 4) as usize;
            let dst_idx = dst_row + (px as u32 * 4) as usize;
            let sa = spr_pixels[src_idx + 3];
            if sa == 255 {
                buf[dst_idx..dst_idx + 4].copy_from_slice(&spr_pixels[src_idx..src_idx + 4]);
            } else if sa > 0 {
                let a = sa as u32;
                let inv_a = 255 - a;
                buf[dst_idx] =
                    ((spr_pixels[src_idx] as u32 * a + buf[dst_idx] as u32 * inv_a) / 255) as u8;
                buf[dst_idx + 1] = ((spr_pixels[src_idx + 1] as u32 * a
                    + buf[dst_idx + 1] as u32 * inv_a)
                    / 255) as u8;
                buf[dst_idx + 2] = ((spr_pixels[src_idx + 2] as u32 * a
                    + buf[dst_idx + 2] as u32 * inv_a)
                    / 255) as u8;
                buf[dst_idx + 3] = 255;
            }
        }
    }
}
