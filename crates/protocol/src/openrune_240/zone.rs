//! Zone header context and location animation extraction for the selected desktop profile.
use super::loc_anim::LocAnimUpdate;
use crate::{ProtocolError, Result, wire::Reader};
use rustclient_model::{LocOrientation, LocationAnimationPlacement, Plane, SequenceId, TileOffset};
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct ZoneOrigin {
    pub offset: TileOffset,
    pub plane: Plane,
}
#[derive(Default)]
pub(super) struct ZoneDecoder {
    origin: Option<ZoneOrigin>,
}
impl ZoneDecoder {
    pub fn clear(&mut self) {
        self.origin = None;
    }
    pub fn header(&mut self, opcode: u16, payload: &[u8]) -> Result<ZoneOrigin> {
        let mut r = Reader::new(payload, "zone header");
        let (x, y, plane) = match opcode {
            118 => {
                let p = r.u8()?;
                let y = r.u8_sub()?;
                (r.u8_sub()?, y, p)
            }
            126 => {
                let y = r.u8()?;
                let x = r.u8()?;
                (x, y, r.u8_sub()?)
            }
            49 => {
                let x = r.u8_sub()?;
                let y = r.u8_add()?;
                (x, y, r.u8_neg()?)
            }
            _ => return Err(error("unknown header")),
        };
        let origin = ZoneOrigin {
            offset: TileOffset {
                x: i32::from(x) - 48,
                y: i32::from(y) - 48,
            },
            plane: Plane::new(plane).ok_or_else(|| error("invalid plane"))?,
        };
        self.origin = Some(origin);
        Ok(origin)
    }
    pub fn animation(&self, payload: &[u8]) -> Result<Option<LocAnimUpdate>> {
        let origin = self
            .origin
            .ok_or_else(|| error("animation without zone header"))?;
        let mut r = Reader::new(payload, "zone animation");
        let coord = r.u8_add()?;
        let properties = r.u8()?;
        let sequence = r.u16_add()?;
        if r.offset != payload.len() || properties >> 2 > 22 {
            return Err(error("invalid animation length or shape"));
        }
        let offset = TileOffset {
            x: origin.offset.x + i32::from((coord >> 4) & 7),
            y: origin.offset.y + i32::from(coord & 7),
        };
        if !(-48..55).contains(&offset.x) || !(-48..55).contains(&offset.y) {
            return Ok(None);
        }
        Ok(Some(LocAnimUpdate {
            offset,
            plane: Some(origin.plane),
            placement: LocationAnimationPlacement {
                shape: properties >> 2,
                orientation: LocOrientation::new(properties & 3).expect("masked orientation"),
            },
            sequence: (sequence != 65535).then_some(SequenceId::new(u32::from(sequence))),
        }))
    }
    pub fn enclosed(&mut self, payload: &[u8]) -> Result<Vec<LocAnimUpdate>> {
        // Parse completely before retaining a new header or publishing any prefix.
        let mut decoder = Self::default();
        decoder.header(49, payload)?;
        let mut r = Reader::new(payload, "enclosed zone");
        r.take(3)?;
        let mut updates = Vec::new();
        while r.offset < payload.len() {
            let opcode = r.u8()?;
            if opcode == 0 {
                r.u8()?; // action visibility
                let count = r.u8()?;
                for _ in 0..count {
                    r.u8()?;
                    while r.u8()? != 0 {}
                }
                r.take(4)?;
            } else {
                // Exact fixed payload widths, in the selected indexed encoder's order.
                let length = *[0, 18, 37, 4, 24, 14, 14, 17, 11, 2, 4, 7, 7, 7, 6]
                    .get(usize::from(opcode))
                    .ok_or_else(|| error("unknown enclosed payload"))?;
                let bytes = r.take(length)?;
                if opcode == 3
                    && let Some(update) = decoder.animation(bytes)?
                {
                    updates.push(update);
                }
            }
        }
        self.origin = decoder.origin;
        Ok(updates)
    }
}
fn error(detail: &'static str) -> ProtocolError {
    ProtocolError::new("zone update", detail)
}

#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn zone_headers_direct_and_enclosed_animations_preserve_order_plane_and_sentinels() {
        let mut d = ZoneDecoder::default();
        let animation = [0xa7, 91, 3, 0xa8];
        assert!(d.animation(&animation).is_err());
        d.header(118, &[2, 88, 80]).unwrap();
        let direct = d.animation(&animation).unwrap().unwrap();
        assert_eq!(direct.offset, TileOffset { x: 2, y: -1 });
        assert_eq!(direct.plane, Some(Plane::new(2).unwrap()));
        assert_eq!(direct.sequence, Some(SequenceId::new(808)));
        let enclosed = [80, 168, 254, 9, 0, 0, 3, 0xa7, 91, 0xff, 0x7f];
        let batch = d.enclosed(&enclosed).unwrap();
        assert_eq!(batch.len(), 1);
        assert_eq!(batch[0].sequence, None);
        assert_eq!(batch[0].offset, direct.offset);
        // Malformed enclosed tail does not change the previous zone origin.
        assert!(d.enclosed(&[72, 160, 255, 3, 0]).is_err());
        assert_eq!(d.animation(&animation).unwrap(), Some(direct));
        d.header(126, &[40, 48, 126]).unwrap();
        assert_eq!(d.animation(&animation).unwrap(), Some(direct));
        d.header(118, &[2, 88, 24]).unwrap(); // zone x=104, outside the build area
        assert_eq!(d.animation(&animation).unwrap(), None);
        d.clear();
        assert!(d.animation(&animation).is_err());
    }
}
