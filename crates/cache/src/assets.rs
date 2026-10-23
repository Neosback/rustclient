pub use crate::sprite::Sprite;
use crate::{Source, error::Result};

/// Decoded straight-alpha RGBA8 image, validated at construction.
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct Image {
    width: u32,
    height: u32,
    rgba: Vec<u8>,
}
impl Image {
    /// Construct a bounded RGBA image. Zero-area glyphs are permitted.
    ///
    /// # Errors
    /// Rejects inconsistent dimensions or more than 16 million pixels.
    pub fn new(width: u32, height: u32, rgba: Vec<u8>, source: Source) -> Result<Self> {
        let area = u64::from(width) * u64::from(height);
        if area > 16 * 1024 * 1024 || area * 4 != rgba.len() as u64 {
            return Err(source.error("image", "invalid dimensions or pixel length"));
        }
        Ok(Self {
            width,
            height,
            rgba,
        })
    }
    /// Pixel width.
    #[must_use]
    pub const fn width(&self) -> u32 {
        self.width
    }
    /// Pixel height.
    #[must_use]
    pub const fn height(&self) -> u32 {
        self.height
    }
    /// Straight-alpha row-major pixels.
    #[must_use]
    pub fn pixels(&self) -> &[u8] {
        &self.rgba
    }

    pub(crate) fn jpeg(bytes: &[u8], source: Source) -> Result<Self> {
        use image::ImageDecoder;
        let mut decoder = image::codecs::jpeg::JpegDecoder::new(std::io::Cursor::new(bytes))
            .map_err(|e| source.error("JPEG", e.to_string()))?;
        let mut limits = image::Limits::default();
        limits.max_image_width = Some(4096);
        limits.max_image_height = Some(4096);
        limits.max_alloc = Some(64 * 1024 * 1024);
        decoder
            .set_limits(limits)
            .map_err(|e| source.error("JPEG", e.to_string()))?;
        let bitmap = image::DynamicImage::from_decoder(decoder)
            .map_err(|e| source.error("JPEG", e.to_string()))?
            .to_rgba8();
        Self::new(bitmap.width(), bitmap.height(), bitmap.into_raw(), source)
    }
}

/// Cache bitmap font combining index-8 masks with index-13 metrics.
#[derive(Debug, Clone)]
pub struct BitmapFont {
    /// Exact glyph frames in CP1252 order. Nonzero sprite pixels are opaque masks.
    pub glyphs: Vec<Sprite>,
    /// Horizontal advance for each character.
    pub advances: [u8; 256],
    /// Distance from drawing origin to baseline.
    pub ascent: u16,
}
impl BitmapFont {
    /// Decode the simple metrics layout used by the three LIVE title fonts.
    ///
    /// # Errors
    /// Rejects extended metrics (not yet supported) and incomplete glyph tables.
    pub fn decode(metrics: &[u8], mut glyphs: Vec<Sprite>, source: Source) -> Result<Self> {
        if metrics.len() != 257 || glyphs.len() != 256 {
            return Err(source.error(
                "font",
                "title font requires 257-byte metrics and 256 glyphs",
            ));
        }
        let mut advances = [0; 256];
        advances.copy_from_slice(&metrics[..256]);
        for glyph in &mut glyphs {
            let mut pixels = glyph.image.pixels().to_vec();
            for pixel in pixels.as_chunks_mut::<4>().0 {
                if pixel[3] != 0 {
                    pixel.copy_from_slice(&[255; 4]);
                }
            }
            glyph.image = Image::new(glyph.image.width(), glyph.image.height(), pixels, source)?;
        }
        Ok(Self {
            glyphs,
            advances,
            ascent: u16::from(metrics[256]),
        })
    }

    /// Measure literal text, without interpreting angle brackets as markup.
    #[must_use]
    pub fn width(&self, text: &str) -> i32 {
        text.chars()
            .map(|ch| {
                let byte = if ch == '\u{a0}' {
                    b' '
                } else {
                    crate::cp1252(ch).unwrap_or(b'?')
                };
                i32::from(self.advances[usize::from(byte)])
            })
            .sum()
    }
}
