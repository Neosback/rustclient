//! Revision-240 specific location animation coordinates are relative to the map anchor.
use crate::{ProtocolError, Result, wire::Reader};
use rustclient_model::{LocOrientation, LocationAnimationPlacement, SequenceId, TileOffset};

#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct LocAnimUpdate {
    pub plane: Option<rustclient_model::Plane>,
    pub offset: TileOffset,
    pub placement: LocationAnimationPlacement,
    pub sequence: Option<SequenceId>,
}
/// Decode `LOC_ANIM_SPECIFIC` (opcode 84).
/// # Errors
/// Rejects truncated/trailing payloads and invalid shapes. Off-map updates are ignored.
pub fn decode_loc_anim_specific(payload: &[u8]) -> Result<Option<LocAnimUpdate>> {
    let mut r = Reader::new(payload, "loc anim specific");
    let sequence = r.u16_add_le()?;
    let packed = r.take(3)?;
    let x = i32::from(packed[0]) + i32::from((packed[2] >> 4) & 7);
    let y = i32::from(packed[1]) + i32::from(packed[2] & 7);
    let properties = r.u8_sub()?;
    let shape = properties >> 2;
    if r.offset != payload.len() || shape > 22 {
        return Err(ProtocolError::new(
            "loc anim specific",
            "invalid coordinate, shape or length",
        ));
    }
    if x >= 103 || y >= 103 {
        return Ok(None);
    }
    Ok(Some(LocAnimUpdate {
        plane: None,
        offset: TileOffset {
            x: x - 48,
            y: y - 48,
        },
        placement: LocationAnimationPlacement {
            shape,
            orientation: LocOrientation::new(properties & 3).expect("masked orientation"),
        },
        sequence: (sequence != 65535).then_some(SequenceId::new(u32::from(sequence))),
    }))
}

#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn specific_animation_uses_zone_offsets_sub_properties_and_stop_sentinel() {
        // RSProt p2Alt3(808), p3(zoneX=48, zoneZ=40, x=2, z=7), p1Alt3(22<<2|3).
        let packet = [0xa8, 3, 48, 40, 0x27, 37];
        let update = decode_loc_anim_specific(&packet).unwrap().unwrap();
        assert_eq!(update.offset, TileOffset { x: 2, y: -1 });
        assert_eq!(update.placement.shape, 22);
        assert_eq!(update.placement.orientation.get(), 3);
        assert_eq!(update.sequence, Some(SequenceId::new(808)));
        let mut stop = packet;
        stop[..2].copy_from_slice(&[0x7f, 0xff]);
        assert_eq!(
            decode_loc_anim_specific(&stop).unwrap().unwrap().sequence,
            None
        );
        for length in 0..packet.len() {
            assert!(decode_loc_anim_specific(&packet[..length]).is_err());
        }
        assert_eq!(
            decode_loc_anim_specific(&[0, 0, 104, 0, 0, 128]).unwrap(),
            None
        );
        assert!(decode_loc_anim_specific(&[0, 0, 0, 0, 0, 36]).is_err());
        assert!(decode_loc_anim_specific(&[0, 0, 0, 0, 0, 128, 0]).is_err());
    }
}
