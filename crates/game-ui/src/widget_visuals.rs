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
    pub quantity_font: Option<FontId>,
    pub font_icons: Vec<rustclient_cache::Sprite>,
    pub destination_flag: Option<rustclient_cache::Sprite>,
    pub cross_frames: BTreeMap<u8, SpriteFrame>,
    /// Untrimmed canvas and trim offset; widget geometry scales the canvas, not the trimmed image.
    pub sprite_frames: BTreeMap<SpriteId, SpriteFrame>,
    pub failures: Vec<String>,
    /// Replacement sprites a plugin asked for, by sprite id; they stand in `images` and `sprite_frames`.
    overrides: BTreeMap<u32, Image>,
    /// What each overridden sprite held before (absent when it was not loaded), restored on removal.
    originals: BTreeMap<u32, (Option<Image>, Option<SpriteFrame>)>,
}
impl WidgetVisualAssets {
    /// Replace interface sprites with `wanted` and restore the ones no longer listed.
    ///
    /// A replacement is shown at its own size with no trim offset, as the reference client draws a
    /// `SpritePixels` override. Returns whether anything changed (the GPU images then need re-uploading).
    pub fn set_sprite_overrides(&mut self, wanted: BTreeMap<u32, Image>) -> bool {
        if wanted == self.overrides {
            return false;
        }
        for (id, (image, frame)) in std::mem::take(&mut self.originals) {
            match image {
                Some(image) => self.images.insert(UiImageId::Sprite(id), image),
                None => self.images.remove(&UiImageId::Sprite(id)),
            };
            match frame {
                Some(frame) => self.sprite_frames.insert(SpriteId::new(id), frame),
                None => self.sprite_frames.remove(&SpriteId::new(id)),
            };
        }
        for (id, image) in &wanted {
            self.originals.insert(
                *id,
                (
                    self.images.get(&UiImageId::Sprite(*id)).cloned(),
                    self.sprite_frames.get(&SpriteId::new(*id)).copied(),
                ),
            );
            self.show_override(*id, image.clone());
        }
        self.overrides = wanted;
        true
    }
    fn show_override(&mut self, id: u32, image: Image) {
        self.sprite_frames.insert(
            SpriteId::new(id),
            SpriteFrame {
                canvas: [image.width(), image.height()],
                offset: [0; 2],
            },
        );
        self.images.insert(UiImageId::Sprite(id), image);
    }
    /// Fold a later load into this set; the cache is immutable, so equal keys are equal values.
    pub fn merge(&mut self, later: Self) {
        // A sprite loaded after it was overridden updates what is restored, never what is shown.
        for (id, original) in &mut self.originals {
            original.0 = later
                .images
                .get(&UiImageId::Sprite(*id))
                .cloned()
                .or(original.0.take());
            original.1 = later
                .sprite_frames
                .get(&SpriteId::new(*id))
                .copied()
                .or(original.1);
        }
        let shown: Vec<(u32, Image)> = self
            .overrides
            .iter()
            .map(|(id, image)| (*id, image.clone()))
            .collect();
        self.images.extend(later.images);
        self.fonts.extend(later.fonts);
        self.quantity_font = later.quantity_font.or(self.quantity_font);
        if !later.font_icons.is_empty() {
            self.font_icons = later.font_icons;
        }
        if later.destination_flag.is_some() {
            self.destination_flag = later.destination_flag;
        }
        self.cross_frames.extend(later.cross_frames);
        self.sprite_frames.extend(later.sprite_frames);
        for (id, image) in shown {
            self.show_override(id, image);
        }
        self.failures.extend(later.failures);
    }
}
/// Cache frame geometry: the reference client scales and tiles the full canvas.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct SpriteFrame {
    pub canvas: [u32; 2],
    pub offset: [u32; 2],
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
pub fn project_widgets(
    state: &GameState,
    definitions: &BTreeMap<InterfaceId, InterfaceDefinition>,
    assets: &WidgetVisualAssets,
    frame: &mut UiFrame,
) -> Result<crate::widgets::WidgetTree, String> {
    project_widgets_with_drag(state, definitions, assets, frame, None)
}
/// Project a drag through the same layout used by pointer hit testing.
/// # Errors
/// Reports incomplete assets or an invalid layout.
#[expect(clippy::too_many_lines, reason = "painter-ordered widget grammar")]
pub fn project_widgets_with_drag(
    state: &GameState,
    definitions: &BTreeMap<InterfaceId, InterfaceDefinition>,
    assets: &WidgetVisualAssets,
    frame: &mut UiFrame,
    dragged: Option<crate::pointer::DraggedWidget>,
) -> Result<crate::widgets::WidgetTree, String> {
    use crate::widgets::{WidgetBounds, WidgetTree, resolve_widget};
    frame.world_viewport = None;
    frame.world_overlay_index = None;
    frame.viewport_overlay = crate::ViewportOverlay::default();
    frame.minimap_sprite = None;
    frame.compass_sprite = None;
    frame.canvas = Some(state.interfaces().window().canvas);
    frame.viewport_settings = state.interfaces().viewport();
    frame.scene_brightness = state.interfaces().brightness();
    let tree = WidgetTree::build_with_drag(
        state.interfaces(),
        definitions,
        WidgetBounds::canvas(state.interfaces()),
        dragged,
    )
    .map_err(|e| e.to_string())?;
    let mut overlay_viewport = None;
    for node in &tree.nodes {
        let widget = resolve_widget(state.interfaces(), definitions, node.address)
            .map_err(|e| e.to_string())?;
        let rect = node.bounds.rect();
        let clip = node.clip.rect();
        if clip.width <= 0. || clip.height <= 0. {
            continue;
        }
        if widget.content == WidgetContent::WorldViewport {
            frame.world_viewport = Some(rect);
            frame.world_overlay_index = Some(frame.quads.len());
            frame.viewport_overlay = crate::ViewportOverlay::default();
            let shape = crate::viewport::viewport_shape(
                node.bounds.width,
                node.bounds.height,
                frame.viewport_settings,
            );
            overlay_viewport = Some(Rect::new(
                rect.x + f(shape.inset[0]),
                rect.y + f(shape.inset[1]),
                f(shape.size[0]),
                f(shape.size[1]),
            ));
            continue;
        }
        if widget.content == WidgetContent::Minimap {
            frame.minimap_sprite = mask_sprite(&widget.body);
            frame.image(UiImageId::Minimap, rect, clip);
            continue;
        }
        if widget.content == WidgetContent::Compass {
            frame.compass_sprite = mask_sprite(&widget.body);
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
                let font = assets
                    .quantity_font
                    .and_then(|id| assets.fonts.get(&id).map(|font| (id, font)));
                if let Some((id, font)) = font {
                    let (label, color) = crate::widget_text::quantity_label(count);
                    draw_text(
                        frame,
                        font,
                        id,
                        &label,
                        node.bounds.x + 1,
                        node.bounds.y + 10,
                        [0, 0, 1, 255],
                        clip,
                    );
                    draw_text(
                        frame,
                        font,
                        id,
                        &label,
                        node.bounds.x,
                        node.bounds.y + 9,
                        rgba(color, 255),
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
                if frame.viewport_overlay.absorb(
                    overlay_viewport,
                    rect,
                    filled,
                    color,
                    transparency,
                ) {
                    continue;
                }
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
                    let image_size = [image.width(), image.height()];
                    let sprite_frame =
                        assets
                            .sprite_frames
                            .get(&sprite)
                            .copied()
                            .unwrap_or(SpriteFrame {
                                canvas: image_size,
                                offset: [0; 2],
                            });
                    let placement = sprite_placement(sprite_frame, image_size, rect, flip, tiled)?;
                    let clip = placement
                        .clip
                        .map_or(clip, |bounds| intersect(clip, bounds));
                    for placed in &placement.sprites {
                        frame.image_tinted(
                            UiImageId::Sprite(sprite.get()),
                            placed.rect,
                            clip,
                            [255, 255, 255, 255 - transparency],
                        );
                        if let Some(q) = frame.quads.last_mut() {
                            q.rotation = angle;
                            q.rotation_center = Some(placed.pivot);
                        }
                    }
                    for q in &mut frame.quads[first..] {
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
                shadowed,
            } => {
                if let Some(font) = assets.fonts.get(&id) {
                    crate::widget_text::paint(
                        frame,
                        font,
                        id,
                        &assets.font_icons,
                        crate::widget_text::TextBox {
                            text: &text,
                            bounds: node.bounds,
                            alignment,
                            line_height,
                            color,
                            shadowed,
                        },
                        clip,
                    );
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
fn mask_sprite(body: &WidgetBody) -> Option<SpriteId> {
    match body {
        WidgetBody::Sprite { sprite, .. } => *sprite,
        _ => None,
    }
}
/// A sprite expanded onto its untrimmed canvas; pixels outside the trimmed image are transparent.
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct CanvasImage {
    pub pixels: Vec<u8>,
    pub size: [u32; 2],
}
impl WidgetVisualAssets {
    /// The reference `copyNormalized`: minimap and compass masks are read on the full canvas.
    #[must_use]
    pub fn canvas_image(&self, id: SpriteId) -> Option<CanvasImage> {
        let image = self.images.get(&UiImageId::Sprite(id.get()))?;
        let frame = self.sprite_frames.get(&id).copied().unwrap_or(SpriteFrame {
            canvas: [image.width(), image.height()],
            offset: [0; 2],
        });
        let size = [
            frame.canvas[0].max(image.width() + frame.offset[0]),
            frame.canvas[1].max(image.height() + frame.offset[1]),
        ];
        let stride = usize::try_from(size[0]).ok()? * 4;
        let mut pixels = vec![0_u8; stride * usize::try_from(size[1]).ok()?];
        let row_len = usize::try_from(image.width()).ok()? * 4;
        for row in 0..usize::try_from(image.height()).ok()? {
            let destination = (usize::try_from(frame.offset[1]).ok()? + row) * stride
                + usize::try_from(frame.offset[0]).ok()? * 4;
            pixels[destination..destination + row_len]
                .copy_from_slice(&image.pixels()[row * row_len..(row + 1) * row_len]);
        }
        Some(CanvasImage { pixels, size })
    }
}
/// One trimmed sprite image destination and the canvas centre it rotates about.
#[derive(Debug, Clone, Copy, PartialEq)]
struct PlacedSprite {
    rect: Rect,
    pivot: [f32; 2],
}
#[derive(Debug, PartialEq)]
struct SpritePlacement {
    sprites: Vec<PlacedSprite>,
    /// Tiling never draws outside the widget; scaled sprites are clipped by the node alone.
    clip: Option<Rect>,
}
fn intersect(a: Rect, b: Rect) -> Rect {
    let x = a.x.max(b.x);
    let y = a.y.max(b.y);
    Rect::new(
        x,
        y,
        (a.x + a.width).min(b.x + b.width) - x,
        (a.y + a.height).min(b.y + b.height) - y,
    )
}
/// Reference `Widget` type-5 placement: a sprite is its untrimmed canvas. Without tiling the
/// canvas is scaled to the widget; with tiling it repeats unscaled. The trimmed image keeps its
/// canvas offset, mirrored when the sprite is flipped.
#[expect(
    clippy::cast_possible_truncation,
    clippy::cast_precision_loss,
    clippy::cast_sign_loss,
    reason = "sprite dimensions are bounded by the cache image limit; tile counts are checked first"
)]
fn sprite_placement(
    sprite: SpriteFrame,
    image: [u32; 2],
    bounds: Rect,
    flip: [bool; 2],
    tiled: bool,
) -> Result<SpritePlacement, String> {
    let canvas = [
        sprite.canvas[0].max(image[0]).max(1),
        sprite.canvas[1].max(image[1]).max(1),
    ];
    let offset = [
        if flip[0] {
            canvas[0].saturating_sub(sprite.offset[0] + image[0])
        } else {
            sprite.offset[0]
        },
        if flip[1] {
            canvas[1].saturating_sub(sprite.offset[1] + image[1])
        } else {
            sprite.offset[1]
        },
    ];
    let canvas_f = [canvas[0] as f32, canvas[1] as f32];
    let place = |origin: [f32; 2], scale: [f32; 2]| PlacedSprite {
        rect: Rect::new(
            origin[0] + offset[0] as f32 * scale[0],
            origin[1] + offset[1] as f32 * scale[1],
            image[0] as f32 * scale[0],
            image[1] as f32 * scale[1],
        ),
        pivot: [
            origin[0] + canvas_f[0] * scale[0] / 2.,
            origin[1] + canvas_f[1] * scale[1] / 2.,
        ],
    };
    if !tiled {
        let scale = [bounds.width / canvas_f[0], bounds.height / canvas_f[1]];
        return Ok(SpritePlacement {
            sprites: vec![place([bounds.x, bounds.y], scale)],
            clip: None,
        });
    }
    let columns = (bounds.width.max(0.) / canvas_f[0]).ceil();
    let rows = (bounds.height.max(0.) / canvas_f[1]).ceil();
    if columns * rows > 16_384. {
        return Err("tiled sprite limit".into());
    }
    let mut sprites = Vec::with_capacity((columns * rows) as usize);
    for row in 0..rows as u32 {
        for column in 0..columns as u32 {
            sprites.push(place(
                [
                    bounds.x + column as f32 * canvas_f[0],
                    bounds.y + row as f32 * canvas_f[1],
                ],
                [1.; 2],
            ));
        }
    }
    Ok(SpritePlacement {
        sprites,
        clip: Some(bounds),
    })
}
fn rgba(color: u32, alpha: u8) -> [u8; 4] {
    let [_, r, g, b] = color.to_be_bytes();
    [r, g, b, alpha]
}
pub use crate::widget_text::wrap;
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

#[cfg(test)]
mod tests {
    use super::*;

    const FRAME: SpriteFrame = SpriteFrame {
        canvas: [10, 8],
        offset: [2, 1],
    };

    #[test]
    fn scaling_applies_to_the_canvas_and_keeps_the_trim_offset_proportional() {
        let placement = sprite_placement(
            FRAME,
            [6, 4],
            Rect::new(100., 50., 20., 16.),
            [false; 2],
            false,
        )
        .unwrap();
        // The canvas is stretched 2x, so the 2,1 offset and 6x4 image double with it.
        assert_eq!(placement.sprites[0].rect, Rect::new(104., 52., 12., 8.));
        assert!(
            placement.sprites[0]
                .pivot
                .iter()
                .zip([110., 58.])
                .all(|(a, b)| (a - b).abs() < 1e-4)
        );
        assert_eq!(placement.clip, None);
    }

    #[test]
    fn unscaled_sprite_lands_on_its_offset() {
        let placement =
            sprite_placement(FRAME, [6, 4], Rect::new(0., 0., 10., 8.), [false; 2], false).unwrap();
        assert_eq!(placement.sprites[0].rect, Rect::new(2., 1., 6., 4.));
    }

    #[test]
    fn flipping_mirrors_the_offset_inside_the_canvas() {
        let placement = sprite_placement(
            FRAME,
            [6, 4],
            Rect::new(0., 0., 10., 8.),
            [true, true],
            false,
        )
        .unwrap();
        assert_eq!(placement.sprites[0].rect, Rect::new(2., 3., 6., 4.));
    }

    #[test]
    fn tiling_repeats_unscaled_canvases_and_clips_to_the_widget() {
        let bounds = Rect::new(0., 0., 25., 8.);
        let placement = sprite_placement(FRAME, [6, 4], bounds, [false; 2], true).unwrap();
        let origins: Vec<_> = placement.sprites.iter().map(|s| s.rect.x).collect();
        assert_eq!(origins, [2., 12., 22.]);
        assert_eq!(placement.clip, Some(bounds));
    }

    #[test]
    fn canvas_image_pads_the_trimmed_pixels_with_transparency() {
        let mut assets = WidgetVisualAssets::default();
        let id = SpriteId::new(7);
        let image = rustclient_cache::Image::new(
            1,
            1,
            vec![9, 8, 7, 255],
            rustclient_cache::Source::group(
                rustclient_cache::IndexId(8),
                rustclient_cache::GroupId(7),
            ),
        )
        .unwrap();
        assets.images.insert(UiImageId::Sprite(7), image);
        assets.sprite_frames.insert(
            id,
            SpriteFrame {
                canvas: [3, 2],
                offset: [1, 1],
            },
        );
        let canvas = assets.canvas_image(id).unwrap();
        assert_eq!(canvas.size, [3, 2]);
        assert_eq!(canvas.pixels.iter().filter(|b| **b != 0).count(), 4);
        assert_eq!(&canvas.pixels[(3 + 1) * 4..(3 + 1) * 4 + 4], [9, 8, 7, 255]);
    }

    #[test]
    fn missing_frame_metadata_degrades_to_the_image() {
        let placement = sprite_placement(
            SpriteFrame {
                canvas: [0, 0],
                offset: [0, 0],
            },
            [6, 4],
            Rect::new(0., 0., 12., 8.),
            [false; 2],
            false,
        )
        .unwrap();
        assert_eq!(placement.sprites[0].rect, Rect::new(0., 0., 12., 8.));
    }

    #[test]
    fn sprite_overrides_stand_in_and_restore_the_cache_sprites() {
        let source = rustclient_cache::Source::group(
            rustclient_cache::IndexId(8),
            rustclient_cache::GroupId(7),
        );
        let image = |value: u8, size: u32| {
            rustclient_cache::Image::new(size, 1, vec![value; size as usize * 4], source).unwrap()
        };
        let mut assets = WidgetVisualAssets::default();
        assets.images.insert(UiImageId::Sprite(7), image(1, 1));
        let original_frame = SpriteFrame {
            canvas: [5, 5],
            offset: [2, 2],
        };
        assets
            .sprite_frames
            .insert(SpriteId::new(7), original_frame);

        // An override of a loaded sprite and one of a sprite that was never loaded.
        let wanted = BTreeMap::from([(7, image(9, 3)), (8, image(8, 2))]);
        assert!(assets.set_sprite_overrides(wanted.clone()));
        assert!(!assets.set_sprite_overrides(wanted));
        assert_eq!(assets.images[&UiImageId::Sprite(7)].width(), 3);
        assert_eq!(
            assets.sprite_frames[&SpriteId::new(7)],
            SpriteFrame {
                canvas: [3, 1],
                offset: [0; 2]
            }
        );
        assert!(assets.images.contains_key(&UiImageId::Sprite(8)));

        // A later cache load of the overridden sprite must not replace what is shown.
        let mut later = WidgetVisualAssets::default();
        later.images.insert(UiImageId::Sprite(7), image(2, 4));
        later.sprite_frames.insert(
            SpriteId::new(7),
            SpriteFrame {
                canvas: [4, 4],
                offset: [0; 2],
            },
        );
        assets.merge(later);
        assert_eq!(assets.images[&UiImageId::Sprite(7)].width(), 3);

        // Removing the overrides restores the newest cache values and drops the unloaded sprite.
        assert!(assets.set_sprite_overrides(BTreeMap::new()));
        assert_eq!(assets.images[&UiImageId::Sprite(7)].width(), 4);
        assert_eq!(assets.sprite_frames[&SpriteId::new(7)].canvas, [4, 4]);
        assert!(!assets.images.contains_key(&UiImageId::Sprite(8)));
        assert!(!assets.sprite_frames.contains_key(&SpriteId::new(8)));
    }
}
