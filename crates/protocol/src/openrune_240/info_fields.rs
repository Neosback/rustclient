//! Shared verified actor-info field encodings.
use crate::{ProtocolError, Result, wire::Reader};
use rustclient_model::{FacingBlock, FacingEntityKind, FacingTarget, FacingWalkPolicy};
pub(super) fn smart(reader: &mut Reader<'_>) -> Result<u16> {
    reader.u_short_smart()
}
/// The facing block (`classBZ.method1384`; rsprox `decodeFacing`): `flag` carries the walk policy
/// (bits 0-2), the payload kind (bits 3-5) and the control flag (bit 6). Entity payloads are the
/// target collection (`gSmart1or2`), its index (`gSmart2or4null`) and a fallback angle (`gSmart1or2`).
pub(super) fn face(reader: &mut Reader<'_>, flag: u8) -> Result<FacingBlock> {
    let walk = match flag & 7 {
        0 => FacingWalkPolicy::CancelOnWalk,
        1 => FacingWalkPolicy::TurnOnWalk,
        _ => return Err(ProtocolError::new("actor facing", "unknown walk policy")),
    };
    let target = match (flag >> 3) & 7 {
        0 => {
            let kind = match smart(reader)? {
                0 => FacingEntityKind::None,
                1 => FacingEntityKind::Npc,
                2 => FacingEntityKind::Player,
                3 => FacingEntityKind::WorldEntity,
                _ => return Err(ProtocolError::new("actor facing", "unknown entity type")),
            };
            let index = smart_2_or_4_null(reader)?;
            FacingTarget::Entity {
                kind,
                index,
                fallback_angle: smart(reader)?,
            }
        }
        1 => {
            let x = smart(reader)?;
            let z = smart(reader)?;
            let packed = smart(reader)?;
            FacingTarget::Area {
                x,
                z,
                size_x: u8::try_from(packed & 15).unwrap_or(0),
                size_z: u8::try_from((packed >> 4) & 15).unwrap_or(0),
            }
        }
        2 => FacingTarget::Angle(smart(reader)?),
        3 => FacingTarget::Cleared,
        _ => return Err(ProtocolError::new("actor facing", "unknown facing kind")),
    };
    Ok(FacingBlock {
        target,
        walk,
        instant: (flag >> 6) & 1 == 1,
    })
}
/// `classXY.method13169`: two bytes (`32767` is null), or four with the top bit set.
fn smart_2_or_4_null(reader: &mut Reader<'_>) -> Result<Option<u32>> {
    let wide = reader
        .bytes
        .get(reader.offset)
        .is_some_and(|byte| byte & 128 != 0);
    if wide {
        let bytes = reader.take(4)?;
        Ok(Some(
            u32::from_be_bytes([bytes[0], bytes[1], bytes[2], bytes[3]]) & 0x7fff_ffff,
        ))
    } else {
        let value = reader.u16()?;
        Ok((value != 32767).then_some(u32::from(value)))
    }
}
/// The angle of an explicit-angle block: the only kind that sets the desired orientation here.
pub(super) fn angle_of(block: &FacingBlock) -> Option<u16> {
    match block.target {
        FacingTarget::Angle(angle) => Some(angle),
        _ => None,
    }
}
