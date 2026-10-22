//! CPU images and fonts retained independently of live widget state and GPU resources.
use crate::{Rect, UiFrame, UiImageId};
use rustclient_cache::{BitmapFont, Image, widget::InterfaceDefinition};
use rustclient_game_state::GameState;
use rustclient_model::{FontId, InterfaceId, SpriteId, WidgetBody, WidgetContent};
use std::collections::BTreeMap;
#[derive(Default)]
pub struct WidgetVisualAssets {
    pub images: BTreeMap<UiImageId, Image>,
    pub fonts: BTreeMap<FontId, BitmapFont>,
    pub sprite_offsets: BTreeMap<SpriteId, [u16; 2]>,
    pub failures: Vec<String>,
}
#[expect(
    clippy::cast_precision_loss,
    reason = "layout values are bounded to f32 integer precision"
)]
fn f(n: i32) -> f32 {
    n as f32
}
/// Project only cache/script-authored widgets in painter order.
/// # Errors
/// Reports incomplete assets/layout; never substitutes a bank-specific layout.
#[expect(
    clippy::too_many_lines,
    reason = "painter-ordered widget grammar keeps each primitive together"
)]
pub fn project_widgets(
    state: &GameState,
    definitions: &BTreeMap<InterfaceId, InterfaceDefinition>,
    assets: &WidgetVisualAssets,
    frame: &mut UiFrame,
) -> Result<crate::widgets::WidgetTree, String> {
    use crate::widgets::{WidgetBounds, WidgetTree, resolve_widget};
    let tree = WidgetTree::build(
        state.interfaces(),
        definitions,
        WidgetBounds {
            x: 0,
            y: 0,
            width: 765,
            height: 503,
        },
    )
    .map_err(|e| e.to_string())?;
    for node in &tree.nodes {
        let widget = resolve_widget(state.interfaces(), definitions, node.address)
            .map_err(|e| e.to_string())?;
        let rect = node.bounds.rect();
        let clip = node.clip.rect();
        if clip.width <= 0. || clip.height <= 0. {
            continue;
        }
        if widget.content == WidgetContent::Minimap {
            frame.image(UiImageId::Minimap, rect, clip);
            continue;
        }
        if widget.content == WidgetContent::Compass {
            frame.image(UiImageId::Compass, rect, clip);
            continue;
        }
        if let Some((item, count)) = state
            .interfaces()
            .runtime()
            .get(&node.address)
            .and_then(|r| r.item)
        {
            let image = UiImageId::ItemIcon(item);
            if assets.images.contains_key(&image) {
                frame.image(image, rect, clip);
            }
            if count > 1 {
                let font = assets.fonts.iter().next();
                if let Some((id, font)) = font {
                    draw_text(
                        frame,
                        font,
                        *id,
                        &count.to_string(),
                        node.bounds.x,
                        node.bounds.y + i32::from(font.ascent),
                        [255, 255, 0, 255],
                        clip,
                    );
                }
            }
            continue;
        }
        match widget.body {
            WidgetBody::Rectangle {
                color,
                filled,
                transparency,
            } => {
                let color = rgba(color, 255 - transparency);
                if filled {
                    frame.image_tinted(UiImageId::White, rect, clip, color);
                } else {
                    for r in [
                        Rect::new(rect.x, rect.y, rect.width, 1.),
                        Rect::new(rect.x, rect.y + rect.height - 1., rect.width, 1.),
                        Rect::new(rect.x, rect.y, 1., rect.height),
                        Rect::new(rect.x + rect.width - 1., rect.y, 1., rect.height),
                    ] {
                        frame.image_tinted(UiImageId::White, r, clip, color);
                    }
                }
            }
            WidgetBody::Sprite {
                sprite: Some(sprite),
                transparency,
                flip,
                tiled,
                angle,
                ..
            } => {
                if let Some(image) = assets.images.get(&UiImageId::Sprite(sprite.get())) {
                    let first = frame.quads.len();
                    if tiled {
                        let w = i32::try_from(image.width())
                            .map_err(|_| "sprite width")?
                            .max(1);
                        let h = i32::try_from(image.height())
                            .map_err(|_| "sprite height")?
                            .max(1);
                        let mut count = 0;
                        for y in (0..node.bounds.height.max(0))
                            .step_by(usize::try_from(h).map_err(|_| "sprite height")?)
                        {
                            for x in (0..node.bounds.width.max(0))
                                .step_by(usize::try_from(w).map_err(|_| "sprite width")?)
                            {
                                count += 1;
                                if count > 16_384 {
                                    return Err("tiled sprite limit".into());
                                }
                                frame.image_tinted(
                                    UiImageId::Sprite(sprite.get()),
                                    Rect::new(rect.x + f(x), rect.y + f(y), f(w), f(h)),
                                    clip,
                                    [255, 255, 255, 255 - transparency],
                                );
                            }
                        }
                    } else {
                        frame.image_tinted(
                            UiImageId::Sprite(sprite.get()),
                            rect,
                            clip,
                            [255, 255, 255, 255 - transparency],
                        );
                    }
                    for q in &mut frame.quads[first..] {
                        q.rotation = angle;
                        if flip[0] {
                            q.uv.x = 1.;
                            q.uv.width = -1.;
                        }
                        if flip[1] {
                            q.uv.y = 1.;
                            q.uv.height = -1.;
                        }
                    }
                }
            }
            WidgetBody::Text {
                font: Some(id),
                text,
                line_height,
                alignment,
                color,
                ..
            } => {
                if let Some(font) = assets.fonts.get(&id) {
                    let lines = wrap(&text, font, node.bounds.width);
                    let line_height = if line_height == 0 {
                        i32::from(font.ascent)
                    } else {
                        i32::from(line_height)
                    };
                    let block = i32::try_from(lines.len()).map_err(|_| "text lines")? * line_height;
                    let top = node.bounds.y
                        + match alignment[1] {
                            1 => (node.bounds.height - block) / 2,
                            2 => node.bounds.height - block,
                            _ => 0,
                        };
                    for (index, line) in lines.iter().enumerate() {
                        let width = font.width(line);
                        let x = node.bounds.x
                            + match alignment[0] {
                                1 => (node.bounds.width - width) / 2,
                                2 => node.bounds.width - width,
                                _ => 0,
                            };
                        draw_text(
                            frame,
                            font,
                            id,
                            line,
                            x,
                            top + i32::from(font.ascent)
                                + i32::try_from(index).map_err(|_| "text lines")? * line_height,
                            rgba(color, 255),
                            clip,
                        );
                    }
                }
            }
            WidgetBody::Line {
                width,
                color,
                reversed,
            } => {
                let segments = node
                    .bounds
                    .width
                    .abs()
                    .max(node.bounds.height.abs())
                    .clamp(1, 16_384);
                for i in 0..segments {
                    let t = f(i) / f(segments);
                    let y = if reversed { 1. - t } else { t };
                    frame.image_tinted(
                        UiImageId::White,
                        Rect::new(
                            rect.x + rect.width * t,
                            rect.y + rect.height * y,
                            f32::from(width),
                            f32::from(width),
                        ),
                        clip,
                        rgba(color, 255),
                    );
                }
            }
            // Containers, models and missing sprite/font bodies draw no primitives.
            _ => {}
        }
    }
    Ok(tree)
}
fn rgba(color: u32, alpha: u8) -> [u8; 4] {
    let [_, r, g, b] = color.to_be_bytes();
    [r, g, b, alpha]
}
/// Bounded markup-aware wrapping used for both widget display and script text measurement.
#[must_use]
pub fn wrap(text: &str, font: &BitmapFont, width: i32) -> Vec<String> {
    let mut lines = Vec::new();
    let mut line = String::new();
    let text = text.replace("<br>", "\n");
    let mut tag = false;
    for word in text.split_inclusive([' ', '\n']) {
        let plain: String = word
            .chars()
            .filter(|c| {
                if *c == '<' {
                    tag = true;
                    false
                } else if *c == '>' {
                    tag = false;
                    false
                } else {
                    !tag && *c != '\n'
                }
            })
            .collect();
        if !line.is_empty() && font.width(&line) + font.width(&plain) > width {
            lines.push(std::mem::take(&mut line));
        }
        line.push_str(&plain);
        if word.ends_with('\n') {
            lines.push(std::mem::take(&mut line));
        }
        if lines.len() >= 4096 {
            break;
        }
    }
    lines.push(line);
    lines
}
#[expect(clippy::too_many_arguments, reason = "explicit text projection inputs")]
fn draw_text(
    frame: &mut UiFrame,
    font: &BitmapFont,
    id: FontId,
    text: &str,
    mut x: i32,
    baseline: i32,
    color: [u8; 4],
    clip: Rect,
) {
    for ch in text.chars() {
        let byte = rustclient_cache::cp1252(ch).unwrap_or(b'?');
        let glyph = &font.glyphs[usize::from(byte)];
        if glyph.image.width() == 0 || glyph.image.height() == 0 {
            x += i32::from(font.advances[usize::from(byte)]);
            continue;
        }
        let rect = Rect::new(
            f(x + i32::from(glyph.offset[0])),
            f(baseline - i32::from(font.ascent) + i32::from(glyph.offset[1])),
            f(i32::try_from(glyph.image.width()).unwrap_or(0)),
            f(i32::try_from(glyph.image.height()).unwrap_or(0)),
        );
        frame.image_tinted(UiImageId::WidgetGlyph(id, byte), rect, clip, color);
        x += i32::from(font.advances[usize::from(byte)]);
    }
}
