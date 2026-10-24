//! Cache-font rich text layout shared by widget painting and script measurements.
#![expect(
    clippy::cast_precision_loss,
    reason = "bounded logical glyph coordinates are projected to UI floats"
)]
use crate::{Rect, UiFrame, UiImageId, widgets::WidgetBounds};
use rustclient_cache::BitmapFont;
use rustclient_model::FontId;

#[derive(Clone, Copy)]
enum Token<'a> {
    Glyph(u8),
    Tag(&'a str),
    Break,
}
fn tokens(text: &str) -> impl Iterator<Item = (usize, usize, Token<'_>)> {
    let mut offset = 0;
    std::iter::from_fn(move || {
        while offset < text.len() {
            let start = offset;
            let ch = text[offset..].chars().next()?;
            offset += ch.len_utf8();
            if ch == '<' {
                let Some(end) = text[offset..].find('>') else {
                    offset = text.len();
                    return None;
                };
                let tag = &text[offset..offset + end];
                offset += end + 1;
                let token = match tag {
                    "lt" => Token::Glyph(b'<'),
                    "gt" => Token::Glyph(b'>'),
                    "at" => Token::Glyph(b'@'),
                    "nbh" => Token::Glyph(b'-'),
                    "br" | "n" => Token::Break,
                    _ => Token::Tag(tag),
                };
                return Some((start, offset, token));
            }
            if ch == '\0' {
                continue;
            }
            let token = if ch == '\n' {
                Token::Break
            } else {
                Token::Glyph(if ch == '\u{a0}' {
                    b' '
                } else {
                    rustclient_cache::cp1252(ch).unwrap_or(b'?')
                })
            };
            return Some((start, offset, token));
        }
        None
    })
}
/// Measure a formatted line; style tags have no advance.
#[must_use]
pub fn width(text: &str, font: &BitmapFont) -> i32 {
    let mut previous = None;
    tokens(text)
        .map(|(_, _, token)| match token {
            Token::Glyph(byte) => {
                let n = font.advance(previous, byte);
                previous = Some(byte);
                n
            }
            Token::Break | Token::Tag(_) => 0,
        })
        .sum()
}
/// Preserve tags while wrapping at spaces/hyphens; explicit breaks reset formatting at paint.
#[must_use]
pub fn wrap(text: &str, font: &BitmapFont, maximum: i32) -> Vec<String> {
    let mut lines = Vec::new();
    let (mut start, mut length, mut previous, mut split) = (0, 0, None, None);
    for (from, end, token) in tokens(text) {
        match token {
            Token::Break => {
                lines.push(text[start..end].to_owned());
                start = end;
                length = 0;
                previous = None;
                split = None;
            }
            Token::Glyph(byte) => {
                length += font.advance(previous, byte);
                previous = Some(byte);
                // A nonbreaking space/hyphen is displayed but never creates a wrap point.
                if &text[from..end] == " " {
                    split = Some((from, end));
                } else if &text[from..end] == "-" {
                    split = Some((end, end));
                }
                if length > maximum
                    && let Some((visible, after)) = split
                {
                    lines.push(text[start..visible].to_owned());
                    start = after;
                    length = width(&text[start..end], font);
                    previous = tokens(&text[start..end])
                        .filter_map(|(_, _, t)| {
                            if let Token::Glyph(b) = t {
                                Some(b)
                            } else {
                                None
                            }
                        })
                        .last();
                    split = None;
                }
            }
            Token::Tag(_) => {}
        }
        if lines.len() >= 4096 {
            return lines;
        }
    }
    if start < text.len() {
        lines.push(text[start..].to_owned());
    }
    lines
}
#[derive(Clone, Copy)]
struct Style {
    color: [u8; 4],
    shadow: Option<[u8; 4]>,
    underline: Option<[u8; 4]>,
    strike: Option<[u8; 4]>,
}
impl Style {
    fn tag(&mut self, tag: &str, base: Self) {
        let color = |s: &str| u32::from_str_radix(s, 16).ok().map(rgba);
        match tag {
            "/col" => self.color = base.color,
            "/shad" => self.shadow = base.shadow,
            "shad" => self.shadow = Some(rgba(0)),
            "u" => self.underline = Some(rgba(0)),
            "/u" => self.underline = None,
            "str" => self.strike = Some(rgba(0x0080_0000)),
            "/str" => self.strike = None,
            _ => {
                if let Some(c) = tag.strip_prefix("col=").and_then(color) {
                    self.color = c;
                } else if let Some(c) = tag.strip_prefix("shad=").and_then(color) {
                    self.shadow = Some(c);
                } else if let Some(c) = tag.strip_prefix("u=").and_then(color) {
                    self.underline = Some(c);
                } else if let Some(c) = tag.strip_prefix("str=").and_then(color) {
                    self.strike = Some(c);
                }
            }
        }
    }
}
fn rgba(n: u32) -> [u8; 4] {
    let [_, r, g, b] = n.to_be_bytes();
    [r, g, b, 255]
}

/// Authoritative widget properties are inputs; all layout/style state is disposable.
#[derive(Clone, Copy)]
pub(crate) struct TextBox<'a> {
    pub text: &'a str,
    pub bounds: WidgetBounds,
    pub alignment: [u8; 2],
    pub line_height: u8,
    pub color: u32,
    pub shadowed: bool,
}
/// Paint using `AbstractFont`'s ink extents, line alignment and style lifetime.
pub(crate) fn paint(
    frame: &mut UiFrame,
    font: &BitmapFont,
    id: FontId,
    text: TextBox<'_>,
    clip: Rect,
) {
    let b = text.bounds;
    let mut spacing = if text.line_height == 0 {
        i32::from(font.ascent)
    } else {
        i32::from(text.line_height)
    };
    let maximum =
        if b.height < font.max_ascent + font.max_descent + spacing && b.height < spacing * 2 {
            i32::MAX
        } else {
            b.width
        };
    let lines = wrap(text.text, font, maximum);
    let count = i32::try_from(lines.len()).unwrap_or(4096);
    if count == 0 {
        return;
    }
    let mut vertical = text.alignment[1];
    if vertical == 3 && count == 1 {
        vertical = 1;
    }
    let spare = b.height - font.max_ascent - font.max_descent - (count - 1) * spacing;
    let extra = if vertical == 3 {
        (spare / (count + 1)).max(0)
    } else {
        0
    };
    let mut baseline = b.y
        + font.max_ascent
        + match vertical {
            1 => spare / 2,
            2 => spare,
            3 => extra,
            _ => 0,
        };
    spacing += extra;
    let base = Style {
        color: rgba(text.color),
        shadow: text.shadowed.then(|| rgba(0)),
        underline: None,
        strike: None,
    };
    let mut style = base;
    for (index, line) in lines.iter().enumerate() {
        let line_width = width(line, font);
        let x = b.x
            + match text.alignment[0] {
                1 => (b.width - line_width) / 2,
                2 => b.width - line_width,
                _ => 0,
            };
        let spaces = tokens(line)
            .filter(|(from, end, t)| matches!(t, Token::Glyph(b' ')) && &line[*from..*end] == " ")
            .count();
        let justify = if text.alignment[0] == 3 && index + 1 < lines.len() && spaces > 0 {
            ((i64::from(b.width) - i64::from(line_width)) << 8) / i64::try_from(spaces).unwrap_or(1)
        } else {
            0
        };
        paint_line(
            frame,
            font,
            id,
            line,
            [x, baseline],
            clip,
            &mut style,
            base,
            justify,
        );
        baseline += spacing;
    }
}
#[expect(
    clippy::too_many_arguments,
    reason = "explicit immutable font/style/layout inputs"
)]
fn paint_line(
    frame: &mut UiFrame,
    font: &BitmapFont,
    id: FontId,
    line: &str,
    origin: [i32; 2],
    clip: Rect,
    style: &mut Style,
    base: Style,
    justify: i64,
) {
    let [mut x, baseline] = origin;
    let (mut previous, mut fraction) = (None, 0);
    for (from, end, token) in tokens(line) {
        match token {
            Token::Tag(tag) => style.tag(tag, base),
            Token::Break => *style = base,
            Token::Glyph(byte) => {
                let advance = i32::from(font.advances[usize::from(byte)]);
                x += font.advance(previous, byte) - advance;
                let glyph = &font.glyphs[usize::from(byte)];
                if glyph.image.width() > 0 && glyph.image.height() > 0 && byte != b' ' {
                    let rect = Rect::new(
                        (x + i32::from(glyph.offset[0])) as f32,
                        (baseline - i32::from(font.ascent) + i32::from(glyph.offset[1])) as f32,
                        glyph.image.width() as f32,
                        glyph.image.height() as f32,
                    );
                    if let Some(color) = style.shadow {
                        frame.image_tinted(
                            UiImageId::WidgetGlyph(id, byte),
                            Rect::new(rect.x + 1., rect.y + 1., rect.width, rect.height),
                            clip,
                            color,
                        );
                    }
                    frame.image_tinted(UiImageId::WidgetGlyph(id, byte), rect, clip, style.color);
                }
                for (color, y) in [
                    (
                        style.strike,
                        baseline - i32::from(font.ascent) + i32::from(font.ascent) * 7 / 10,
                    ),
                    (style.underline, baseline + 1),
                ] {
                    if let Some(color) = color {
                        frame.fill_rect(
                            Rect::new(x as f32, y as f32, advance as f32, 1.),
                            color,
                            clip,
                        );
                    }
                }
                if byte == b' ' && &line[from..end] == " " {
                    fraction += justify;
                    x += i32::try_from(fraction >> 8).unwrap_or(0);
                    fraction &= 255;
                }
                x += advance;
                previous = Some(byte);
            }
        }
    }
}

/// Stock item-icon quantity label and RGB (distinct from long-form examine quantities).
#[must_use]
pub fn quantity_label(count: u32) -> (String, u32) {
    if count < 100_000 {
        (count.to_string(), 0x00ff_ff00)
    } else if count < 10_000_000 {
        (format!("{}K", count / 1000), 0x00ff_ffff)
    } else {
        (format!("{}M", count / 1_000_000), 0x0000_ff80)
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use rustclient_cache::{GroupId, Image, IndexId, Source, Sprite};
    use rustclient_model::SpriteId;
    #[test]
    fn markup_wraps_without_losing_style_and_distinguishes_literal_escape_and_nonbreaking_spaces() {
        let source = Source::group(IndexId(13), GroupId(1));
        let glyph = Sprite {
            id: SpriteId::new(1),
            frame: 0,
            canvas: [1, 1],
            offset: [0, 0],
            image: Image::new(1, 1, vec![255; 4], source).unwrap(),
        };
        let mut metrics = vec![5; 256];
        metrics.push(8);
        let font = BitmapFont::decode(&metrics, vec![glyph; 256], source).unwrap();
        assert_eq!(width("<col=ff0000>A</col><lt><gt>\u{a0}", &font), 20);
        assert_eq!(
            wrap("<col=ff0000>A B</col> C", &font, 6),
            vec!["<col=ff0000>A", "B</col>", "C"]
        );
        assert_eq!(wrap("A\u{a0}B", &font, 6), vec!["A\u{a0}B"]);
        assert_eq!(wrap("A<br>B", &font, 100), vec!["A<br>", "B"]);
        assert!(wrap("", &font, 100).is_empty());
        assert_eq!(quantity_label(99_999), ("99999".into(), 0x00ff_ff00));
        assert_eq!(quantity_label(100_000), ("100K".into(), 0x00ff_ffff));
        assert_eq!(quantity_label(10_000_000), ("10M".into(), 0x0000_ff80));
        let base = Style {
            color: rgba(0x00ff_ff00),
            shadow: Some(rgba(0)),
            underline: None,
            strike: None,
        };
        let mut style = base;
        style.tag("col=ff0000", base);
        assert_eq!(style.color, rgba(0x00ff_0000));
        style.tag("/col", base);
        assert_eq!(style.color, base.color);
        style.tag("shad=00ff00", base);
        assert_eq!(style.shadow, Some(rgba(0x0000_ff00)));
        style.tag("/shad", base);
        assert_eq!(style.shadow, base.shadow);
        style.tag("col=oops", base);
        assert_eq!(style.color, base.color);
    }
}
