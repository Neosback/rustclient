use crate::{Image, Source, error::Result, reader::Reader};
use rustclient_model::SpriteId;

/// One indexed sprite frame, expanded to RGBA but retaining canvas/trim offsets.
#[derive(Debug, Clone)]
pub struct Sprite {
    /// Semantic sprite group resource ID.
    pub id: SpriteId,
    /// Frame in the source group.
    pub frame: u16,
    /// Untrimmed canvas dimensions.
    pub canvas: [u16; 2],
    /// Trimmed image's canvas offset.
    pub offset: [u16; 2],
    /// Trimmed RGBA pixels.
    pub image: Image,
}

/// Decode a bounded indexed-sprite file, including column-major and alpha flags.
///
/// # Errors
/// Rejects malformed metadata, palettes, flags, bounds or pixel streams.
pub fn decode_sprites(bytes: &[u8], source: Source) -> Result<Vec<Sprite>> {
    if bytes.len() < 2 {
        return Err(source.error("sprite", "missing frame count"));
    }
    let count = usize::from(u16::from_be_bytes([
        bytes[bytes.len() - 2],
        bytes[bytes.len() - 1],
    ]));
    if count == 0 || count > 4096 {
        return Err(source.error("sprite", "invalid frame count"));
    }
    let metadata = bytes
        .len()
        .checked_sub(7 + count * 8)
        .ok_or_else(|| source.error("sprite", "truncated metadata"))?;
    let mut r = Reader::new(&bytes[metadata..bytes.len() - 2], source);
    let canvas = [r.u16()?, r.u16()?];
    let palette_len = usize::from(r.u8()?) + 1;
    let offsets_x = (0..count).map(|_| r.u16()).collect::<Result<Vec<_>>>()?;
    let offsets_y = (0..count).map(|_| r.u16()).collect::<Result<Vec<_>>>()?;
    let widths = (0..count).map(|_| r.u16()).collect::<Result<Vec<_>>>()?;
    let heights = (0..count).map(|_| r.u16()).collect::<Result<Vec<_>>>()?;
    r.finish()?;
    let palette_start = metadata
        .checked_sub((palette_len - 1) * 3)
        .ok_or_else(|| source.error("sprite", "truncated palette"))?;
    let mut palette = vec![[0; 4]; palette_len];
    let mut r = Reader::new(&bytes[palette_start..metadata], source);
    for color in palette.iter_mut().skip(1) {
        let rgb = r.u24()?.max(1).to_be_bytes();
        *color = [rgb[1], rgb[2], rgb[3], 255];
    }
    let mut r = Reader::new(&bytes[..palette_start], source);
    let mut frames = Vec::with_capacity(count);
    let mut total_area = 0_usize;
    for i in 0..count {
        let width = usize::from(widths[i]);
        let height = usize::from(heights[i]);
        let area = width * height;
        total_area += area;
        if total_area > 16 * 1024 * 1024
            || u32::from(offsets_x[i]) + u32::from(widths[i]) > u32::from(canvas[0])
            || u32::from(offsets_y[i]) + u32::from(heights[i]) > u32::from(canvas[1])
        {
            return Err(source.error("sprite", "frame outside canvas or pixel limit"));
        }
        let flags = r.u8()?;
        if flags & !3 != 0 {
            return Err(source.error("sprite", "unsupported pixel flags"));
        }
        let indices = plane(&mut r, width, height, flags & 1 != 0)?;
        let alpha = if flags & 2 != 0 {
            Some(plane(&mut r, width, height, flags & 1 != 0)?)
        } else {
            None
        };
        let mut rgba = Vec::with_capacity(area * 4);
        for (p, &index) in indices.iter().enumerate() {
            let mut color = *palette
                .get(usize::from(index))
                .ok_or_else(|| source.error("sprite", "palette index out of range"))?;
            if let Some(alpha) = &alpha {
                color[3] = alpha[p];
            }
            rgba.extend_from_slice(&color);
        }
        frames.push(Sprite {
            id: SpriteId::new(source.group.map_or(0, |id| id.0)),
            frame: u16::try_from(i).map_err(|_| source.error("sprite", "frame overflow"))?,
            canvas,
            offset: [offsets_x[i], offsets_y[i]],
            image: Image::new(u32::from(widths[i]), u32::from(heights[i]), rgba, source)?,
        });
    }
    r.finish()?;
    Ok(frames)
}
fn plane(r: &mut Reader<'_>, width: usize, height: usize, vertical: bool) -> Result<Vec<u8>> {
    let bytes = r.take(width * height)?;
    if !vertical {
        return Ok(bytes.to_vec());
    }
    let mut result = vec![0; bytes.len()];
    for x in 0..width {
        for y in 0..height {
            result[y * width + x] = bytes[x * height + y];
        }
    }
    Ok(result)
}
