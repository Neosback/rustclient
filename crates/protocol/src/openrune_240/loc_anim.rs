//! Decoders for location animation packets (opcodes 123 and 84).

use crate::{Result, wire::Reader};
use rustclient_model::{LocationKey, Plane, SequenceId, WorldTile};

/// Location animation trigger from server.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct LocAnimUpdate {
    pub key: LocationKey,
    pub sequence: SequenceId,
}

/// Decode `LOC_ANIM_SPECIFIC` (opcode 84).
/// # Errors
/// Fails on truncated payload.
pub fn decode_loc_anim_specific(payload: &[u8]) -> Result<LocAnimUpdate> {
    let mut r = Reader::new(payload, "loc anim specific");
    let lo = r.u8()?.wrapping_sub(128);
    let hi = r.u8()?;
    let seq = u16::from_le_bytes([lo, hi]);

    let packed_coord = r.take(3)?;
    let plane_val = packed_coord[0] >> 6;
    let x = (u16::from(packed_coord[0] & 0x3f) << 8) | u16::from(packed_coord[1]);
    let y = (u16::from(packed_coord[2]) << 6) | u16::from(packed_coord[0] >> 2 & 0x3f);

    let prop = r.u8()?.wrapping_sub(128);
    let shape = prop >> 2;

    let plane = Plane::new(plane_val).unwrap_or(Plane::MIN);
    let tile = WorldTile::new(i32::from(x), i32::from(y), plane);
    let layer = rustclient_model::LocationLayer::from_shape(shape);

    Ok(LocAnimUpdate {
        key: LocationKey::new(tile, layer),
        sequence: SequenceId::new(u32::from(seq)),
    })
}
