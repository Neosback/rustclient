//! Disposable actor speech projection (240 classCE; readable 235 Varcs draw pass).
#![allow(
    clippy::cast_precision_loss,
    clippy::cast_possible_truncation,
    reason = "bounded glyph and viewport coordinates use the reference truncated float waves"
)]
use crate::{Rect, UiFrame, UiImageId, widget_text};
use rustclient_cache::TitleAssets;
use rustclient_game_state::ActorOverhead;

pub struct SpeechLabel<'a> {
    pub speech: &'a ActorOverhead,
    pub point: [f32; 2],
    /// NPC speech does not use the public-player chat-mode filter.
    pub player: bool,
    pub local: bool,
}

/// Draw the bounded reference overhead list, moving later baselines above previous text.
pub fn project<'a>(
    frame: &mut UiFrame,
    assets: &TitleAssets,
    labels: impl IntoIterator<Item = SpeechLabel<'a>>,
    cycle: u64,
    mode: u8,
    clip: Rect,
) {
    let overlay_start = frame.quads.len();
    let Some(font) = assets.fonts.get(2) else {
        return;
    };
    let mut placed = [([0_f32; 2], 0_f32); 50];
    let mut placed_count = 0;
    for label in labels {
        let speech = label.speech;
        let visible = !label.player
            || (!speech.filtered
                && match mode {
                    4 => true,
                    0 | 3 => !speech.autotyper,
                    // The social-list owner is not implemented yet; only self is known here.
                    1 => label.local && !speech.autotyper,
                    _ => false,
                });
        if !visible || placed_count >= placed.len() || speech.text.text.is_empty() {
            continue;
        }
        let width = widget_text::width(&speech.text.text, font) as f32;
        let half = width / 2.;
        let [x, mut baseline] = label.point;
        baseline += 2.;
        loop {
            let mut moved = false;
            for (point, other_half) in &placed[..placed_count] {
                if baseline + 2. > point[1] - f32::from(font.ascent)
                    && baseline - f32::from(font.ascent) < point[1] + 2.
                    && x - half < point[0] + other_half
                    && x + half > point[0] - other_half
                    && point[1] - f32::from(font.ascent) < baseline
                {
                    baseline = point[1] - f32::from(font.ascent);
                    moved = true;
                }
            }
            if !moved {
                break;
            }
        }
        placed[placed_count] = ([x, baseline], half);
        placed_count += 1;
        draw_label(frame, font, speech, [x, baseline], cycle, clip);
    }
    // The world widget owns this paint layer. Mounted interfaces cover world speech just as
    // they cover the 3D viewport; popups and pointer feedback are painted later still.
    let added = frame.quads.len() - overlay_start;
    let insertion = frame.world_overlay_index.unwrap_or(0).min(overlay_start);
    frame.quads[insertion..].rotate_right(added);
}

fn draw_label(
    frame: &mut UiFrame,
    font: &rustclient_cache::BitmapFont,
    speech: &ActorOverhead,
    point: [f32; 2],
    cycle: u64,
    clip: Rect,
) {
    let [x, baseline] = point;
    let width = widget_text::width(&speech.text.text, font) as f32;
    let half = width / 2.;
    let age = 150_u32.saturating_sub(speech.remaining);
    let mut text_clip = clip;
    let mut cursor = x - half;
    let mut y = baseline;
    if speech.text.effect == 4 {
        text_clip = intersect(clip, Rect::new(x - 50., clip.y, 100., clip.height));
        cursor = x + 50. - age as f32 * (width + 100.) / 150.;
    } else if speech.text.effect == 5 {
        let slide = if age < 25 {
            i32::try_from(age).unwrap_or(150) - 25
        } else if age > 125 {
            i32::try_from(age).unwrap_or(150) - 125
        } else {
            0
        };
        y += slide as f32;
        text_clip = intersect(
            clip,
            Rect::new(
                clip.x,
                baseline - f32::from(font.ascent) - 1.,
                clip.width,
                f32::from(font.ascent) + 6.,
            ),
        );
    }
    let count = speech.text.text.chars().count();
    let mut previous = None;
    for (index, byte) in widget_text::glyphs(&speech.text.text).enumerate() {
        let advance = font.advance(previous, byte);
        cursor += (advance - i32::from(font.advances[usize::from(byte)])) as f32;
        let i = index as f64;
        let time = f64::from(
            u32::try_from(cycle & u64::from(u32::MAX))
                .unwrap_or(0)
                .cast_signed(),
        );
        let (dx, dy) = match speech.text.effect {
            1 => (0., (i / 2. + time / 5.).sin() * 5.),
            2 => (
                (i / 5. + time / 5.).sin() * 5.,
                (i / 3. + time / 5.).sin() * 5.,
            ),
            3 => (
                0.,
                (i / 1.5 + time).sin() * (7. - f64::from(age) / 8.).max(0.),
            ),
            _ => (0., 0.),
        };
        let rgb = colour(speech, cycle, age, index, count);
        if let Some(glyph) = font.glyphs.get(usize::from(byte)) {
            let rect = Rect::new(
                cursor + f32::from(glyph.offset[0]) + dx.trunc() as f32,
                y - f32::from(font.ascent) + f32::from(glyph.offset[1]) + dy.trunc() as f32,
                glyph.image.width() as f32,
                glyph.image.height() as f32,
            );
            if rect.width > 0. && rect.height > 0. {
                frame.image_tinted(
                    UiImageId::Glyph(2, byte),
                    Rect::new(rect.x + 1., rect.y + 1., rect.width, rect.height),
                    text_clip,
                    [0, 0, 0, 255],
                );
                let bytes = rgb.to_be_bytes();
                frame.image_tinted(
                    UiImageId::Glyph(2, byte),
                    rect,
                    text_clip,
                    [bytes[1], bytes[2], bytes[3], 255],
                );
            }
        }
        cursor += f32::from(font.advances[usize::from(byte)]);
        previous = Some(byte);
    }
}

fn intersect(a: Rect, b: Rect) -> Rect {
    let x = a.x.max(b.x);
    let y = a.y.max(b.y);
    Rect::new(
        x,
        y,
        (a.x + a.width).min(b.x + b.width).max(x) - x,
        (a.y + a.height).min(b.y + b.height).max(y) - y,
    )
}
fn colour(speech: &ActorOverhead, cycle: u64, age: u32, index: usize, count: usize) -> u32 {
    let blink = cycle % 20 < 10;
    match speech.text.colour {
        1 => 0xff_0000,
        2 => 0x00_ff00,
        3 => 0x00_ffff,
        4 => 0xff_00ff,
        5 => 0xff_ffff,
        6 => {
            if blink {
                0xff_0000
            } else {
                0xff_ff00
            }
        }
        7 => {
            if blink {
                0x00_00ff
            } else {
                0x00_ffff
            }
        }
        8 => {
            if blink {
                0x00_b000
            } else {
                0x80_ff80
            }
        }
        9 => match age {
            0..50 => 0xff_0000 + age * 1280,
            50..100 => 0xff_ff00 - (age - 50) * 327_680,
            _ => 0x00_ff00 + (age - 100) * 5,
        },
        10 => match age {
            0..50 => 0xff_0000 + age * 5,
            50..100 => 0xff_00ff - (age - 50) * 327_680,
            _ => 0x00_00ff + (age - 100) * 327_680 - (age - 100) * 5,
        },
        11 => match age {
            0..50 => 0xff_ffff - age * 327_685,
            50..100 => 0x00_ff00 + (age - 50) * 327_685,
            _ => 0xff_ffff - (age - 100) * 327_680,
        },
        12 => rainbow(index, count),
        13..=20 => {
            // 240 classZM pattern palette; invalid metadata is ignored by classKV.
            const PALETTE: [u32; 36] = [
                16_777_215, 14_942_979, 16_747_520, 16_772_352, 32_806, 2_375_822, 7_547_266,
                16_720_268, 11_884_176, 5_261_772, 10_724_259, 13_970_688, 15_693_351, 16_577_588,
                494_960, 2_208_255, 10_178_454, 16_756_679, 13_722_276, 8_105_443, 16_751_190,
                2_543_274, 7_591_918, 10_246_609, 10_021_057, 11_894_492, 2_894_892, 9_699_842,
                6_371_605, 13_680_896, 4_882_723, 14_504, 8_388_736, 14_025_328, 10_682_978,
                4_004_472,
            ];
            let p = &speech.text.pattern;
            if p.is_empty() || p.iter().any(|b| usize::from(*b) >= PALETTE.len()) {
                return 0xff_ff00;
            }
            // AbstractFont expands the supplied pattern to the visible glyph count.
            PALETTE[usize::from(p[index * p.len() / count.max(1)])]
        }
        _ => 0xff_ff00,
    }
}
#[expect(
    clippy::cast_sign_loss,
    reason = "palette channels are clamped to nonnegative bytes before conversion"
)]
fn rainbow(index: usize, count: usize) -> u32 {
    // Rasterizer palette entry ((64 * i/n) << 10) | 896 | 64, brightness 0.8.
    let hue = (64 * index / count.max(1)) as f32 / 64. + 0.007_812_5;
    let saturation = 0.9375;
    let luminance = 0.5;
    let upper = luminance * (1. + saturation);
    let lower = 2. * luminance - upper;
    let channel = |offset: f32| {
        let t = (hue + offset).rem_euclid(1.);
        let v = if t * 6. < 1. {
            lower + (upper - lower) * 6. * t
        } else if t * 2. < 1. {
            upper
        } else if t * 3. < 2. {
            lower + (upper - lower) * (2. / 3. - t) * 6.
        } else {
            lower
        };
        let quantized = (v * 256.).clamp(0., 255.) as u8;
        ((f32::from(quantized) / 256.).powf(0.8) * 256.).clamp(0., 255.) as u8
    };
    u32::from_be_bytes([0, channel(1. / 3.), channel(0.), channel(-1. / 3.)])
}
