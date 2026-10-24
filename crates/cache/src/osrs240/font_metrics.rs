//! Revision-240 `AbstractFont` simple/contour metrics; no renderer or live state.
use crate::{Source, error::Result, reader::Reader};
pub(crate) struct Metrics {
    pub advances: [u8; 256],
    pub ascent: u16,
    pub kerning: Option<Vec<i8>>,
}
pub(crate) fn decode(bytes: &[u8], source: Source) -> Result<Metrics> {
    let mut r = Reader::new(bytes, source);
    let mut advances = [0; 256];
    advances.copy_from_slice(r.take(256)?);
    if bytes.len() == 257 {
        return Ok(Metrics {
            advances,
            ascent: u16::from(r.u8()?),
            kerning: None,
        });
    }
    let lengths = r.take(256)?.to_vec();
    let offsets = r.take(256)?.to_vec();
    let mut contours = Vec::with_capacity(512);
    for _ in 0..2 {
        for &length in &lengths {
            let mut previous = 0_i8;
            let mut contour = Vec::with_capacity(usize::from(length));
            for &delta in r.take(usize::from(length))? {
                previous = previous.wrapping_add(delta.cast_signed());
                contour.push(previous);
            }
            contours.push(contour);
        }
    }
    r.finish()?;
    let mut kerning = vec![0_i8; 65_536];
    for first in (0..256).filter(|n| ![32, 160].contains(n)) {
        for second in (0..256).filter(|n| ![32, 160].contains(n)) {
            let start = usize::from(offsets[first].max(offsets[second]));
            let end = (usize::from(offsets[first]) + usize::from(lengths[first]))
                .min(usize::from(offsets[second]) + usize::from(lengths[second]));
            let mut gap = i32::from(advances[first].min(advances[second]));
            for y in start..end {
                gap = gap.min(
                    i32::from(contours[256 + first][y - usize::from(offsets[first])])
                        + i32::from(contours[second][y - usize::from(offsets[second])]),
                );
            }
            // The client stores this exact signed byte, including Java's narrowing wrap.
            kerning[first * 256 + second] = (-gap).to_le_bytes()[0].cast_signed();
        }
    }
    Ok(Metrics {
        advances,
        ascent: u16::from(offsets[32]) + u16::from(lengths[32]),
        kerning: Some(kerning),
    })
}
