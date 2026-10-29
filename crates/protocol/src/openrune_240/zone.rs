//! Zone header context and location animation extraction for the selected desktop profile.
use super::loc_anim::LocAnimUpdate;
use crate::{ProtocolError, Result, wire::Reader};
use rustclient_model::{LocOrientation, LocationAnimationPlacement, Plane, SequenceId, TileOffset};
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct ZoneOrigin {
    pub offset: TileOffset,
    pub plane: Plane,
}
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct LocationUpdate {
    pub offset: TileOffset,
    pub plane: Plane,
    pub placement: LocationAnimationPlacement,
    pub object: Option<rustclient_model::ObjectDefinitionId>,
    pub options: rustclient_model::LocationOptions,
}
#[derive(Debug, Clone, PartialEq, Eq)]
pub(super) enum ZoneUpdate {
    Animation(LocAnimUpdate),
    Location(LocationUpdate),
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
    pub fn location(&self, payload: &[u8], add: bool) -> Result<Option<LocationUpdate>> {
        let origin = self
            .origin
            .ok_or_else(|| error("location without zone header"))?;
        let mut r = Reader::new(payload, "zone location");
        let update = Self::read_location(&mut r, origin, add)?;
        if r.offset != payload.len() {
            return Err(error("trailing location bytes"));
        }
        Ok(update)
    }
    fn read_location(
        r: &mut Reader<'_>,
        origin: ZoneOrigin,
        add: bool,
    ) -> Result<Option<LocationUpdate>> {
        let mut options = rustclient_model::LocationOptions::default();
        if add {
            let flags = r.u8_sub()?;
            options.enabled =
                rustclient_model::ActionMask::new(std::array::from_fn(|i| flags & (1 << i) != 0));
            let count = r.u8()?;
            if count > 5 {
                return Err(error("location action count"));
            }
            for _ in 0..count {
                let slot = usize::from(r.u8_add()?);
                if slot >= 5 {
                    return Err(error("location action slot"));
                }
                let mut label = String::new();
                loop {
                    let byte = r.u8()?;
                    if byte == 0 {
                        break;
                    }
                    if label.len() >= 1024 {
                        return Err(error("location action label limit"));
                    }
                    label.push(crate::wire::decode_cp1252(byte));
                }
                options.labels[slot] = Some(label);
            }
        }
        let coord = r.u8()?;
        let object = if add {
            Some(rustclient_model::ObjectDefinitionId::new(u32::from(
                r.u16_add()?,
            )))
        } else {
            None
        };
        let properties = if add { r.u8()? } else { r.u8_sub()? };
        if properties >> 2 > 22 {
            return Err(error("invalid location shape"));
        }
        let offset = TileOffset {
            x: origin.offset.x + i32::from((coord >> 4) & 7),
            y: origin.offset.y + i32::from(coord & 7),
        };
        if !(-48..55).contains(&offset.x) || !(-48..55).contains(&offset.y) {
            return Ok(None);
        }
        Ok(Some(LocationUpdate {
            offset,
            plane: origin.plane,
            placement: LocationAnimationPlacement {
                shape: properties >> 2,
                orientation: LocOrientation::new(properties & 3).expect("masked orientation"),
            },
            object,
            options,
        }))
    }
    pub fn enclosed(&mut self, payload: &[u8]) -> Result<Vec<ZoneUpdate>> {
        // Parse completely before retaining a new header or publishing any prefix.
        let mut decoder = Self::default();
        decoder.header(49, payload)?;
        let mut r = Reader::new(payload, "enclosed zone");
        r.take(3)?;
        let mut updates = Vec::new();
        while r.offset < payload.len() {
            let opcode = r.u8()?;
            if opcode == 0 || opcode == 9 {
                if let Some(update) = Self::read_location(
                    &mut r,
                    decoder.origin.expect("parsed header"),
                    opcode == 0,
                )? {
                    updates.push(ZoneUpdate::Location(update));
                }
            } else {
                // Exact fixed payload widths, in the selected indexed encoder's order.
                let length = *[0, 18, 37, 4, 24, 14, 14, 17, 11, 2, 4, 7, 7, 7, 6]
                    .get(usize::from(opcode))
                    .ok_or_else(|| error("unknown enclosed payload"))?;
                let bytes = r.take(length)?;
                if opcode == 3
                    && let Some(update) = decoder.animation(bytes)?
                {
                    updates.push(ZoneUpdate::Animation(update));
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
    fn replacements_deletions_options_and_truncated_groups_are_atomic() {
        let mut decoder = ZoneDecoder::default();
        decoder.header(118, &[0, 80, 80]).unwrap();
        // Selected LocAddChangeV2Encoder: flags-sub, count, key-add, string, coord, id-add, properties.
        let add = [97, 1, 128, b'O', b'p', b'e', b'n', 0, 0x23, 0x12, 0xb4, 2];
        let update = decoder.location(&add, true).unwrap().unwrap();
        assert_eq!(update.object.unwrap().get(), 0x1234);
        assert_eq!(update.offset, TileOffset { x: 2, y: 3 });
        assert_eq!(update.options.labels[0].as_deref(), Some("Open"));
        assert_eq!(update.placement.orientation.get(), 2);
        assert!(
            decoder
                .location(&[0x23, 126], false)
                .unwrap()
                .unwrap()
                .object
                .is_none()
        );
        let mut group = vec![80, 176, 0, 0];
        group.extend(add);
        group.extend([9, 0x23, 126, 3, 0xa3, 2, 0xff, 0x7f]);
        assert_eq!(decoder.enclosed(&group).unwrap().len(), 3);
        for length in 4..group.len() {
            if [4 + add.len(), 4 + add.len() + 3].contains(&length) {
                continue;
            }
            assert!(decoder.enclosed(&group[..length]).is_err());
        }
        assert_eq!(decoder.location(&add, true).unwrap(), Some(update));
    }
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
        let enclosed = [80, 168, 254, 9, 0, 128, 3, 0xa7, 91, 0xff, 0x7f];
        let batch = d.enclosed(&enclosed).unwrap();
        assert_eq!(batch.len(), 2);
        let ZoneUpdate::Animation(enclosed_animation) = &batch[1] else {
            panic!("animation order")
        };
        assert_eq!(enclosed_animation.sequence, None);
        assert_eq!(enclosed_animation.offset, direct.offset);
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
