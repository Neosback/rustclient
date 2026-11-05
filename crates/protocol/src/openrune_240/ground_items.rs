//! Revision-240 ground item ("obj") payloads: the six enclosed zone bodies and the six
//! `*_SPECIFIC` packets.
//!
//! Field order and byte transforms follow the stock 240 handlers (`client` `OBJ_*_SPECIFIC`,
//! `classTI.method10892` for zone bodies), cross-checked against the rsprox 240 decoders
//! (`zone/payload/Obj*Decoder.kt`, `specific/Obj*SpecificDecoder.kt`).
use super::zone::ZoneOrigin;
use crate::{ProtocolError, Result, wire::Reader};
use rustclient_model::{
    ActionMask, GroundItemCustomisation, GroundItemOwnership, GroundItemPosition, GroundItemUpdate,
    ItemDefinitionId, ModelId, Plane, TileOffset, WorldTile,
};

fn error(detail: &'static str) -> ProtocolError {
    ProtocolError::new("ground item", detail)
}

/// Four bytes assembled most-significant first from the read positions in `order`.
fn int(r: &mut Reader<'_>, order: [usize; 4]) -> Result<u32> {
    let b = r.take(4)?;
    Ok(u32::from_be_bytes(order.map(|i| b[i])))
}
/// `g4`: big-endian.
const G4: [usize; 4] = [0, 1, 2, 3];
/// `g4Alt1` (stock `method13295`): little-endian.
const G4_ALT1: [usize; 4] = [3, 2, 1, 0];
/// `g4Alt2` (stock `method13300`).
const G4_ALT2: [usize; 4] = [2, 3, 0, 1];
/// `g4Alt3` (stock `method13303`).
const G4_ALT3: [usize; 4] = [1, 0, 3, 2];

/// `g2sAlt1`: little-endian signed.
fn i16_le(r: &mut Reader<'_>) -> Result<i16> {
    Ok(r.u16_le()?.cast_signed())
}
/// `g2sAlt3`: little-endian signed, low byte offset by 128.
fn i16_add_le(r: &mut Reader<'_>) -> Result<i16> {
    Ok(r.u16_add_le()?.cast_signed())
}

fn quantity(value: u32) -> Result<u32> {
    if value > i32::MAX.cast_unsigned() {
        return Err(error("negative quantity"));
    }
    Ok(value)
}
fn item(value: u16) -> ItemDefinitionId {
    ItemDefinitionId::new(u32::from(value))
}
fn ops(flags: u8) -> ActionMask {
    ActionMask::new(std::array::from_fn(|i| flags & (1 << i) != 0))
}
/// A recolour or retexture pair; a negative index means none.
fn replacement(index: i16, value: i16) -> Option<(u16, u16)> {
    u16::try_from(index)
        .ok()
        .map(|index| (index, value.cast_unsigned()))
}
fn model(value: u16) -> Option<ModelId> {
    (value != u16::MAX).then(|| ModelId::new(u32::from(value)))
}
/// Packed `CoordGrid`: plane in bits 28-29, x in 14-27, y in 0-13.
fn coord_grid(packed: u32) -> Result<WorldTile> {
    let plane = u8::try_from((packed >> 28) & 3).expect("two bits");
    let x = i32::try_from((packed >> 14) & 0x3fff).expect("fourteen bits");
    let y = i32::try_from(packed & 0x3fff).expect("fourteen bits");
    if packed >> 30 != 0 {
        return Err(error("coordinate plane out of range"));
    }
    Ok(WorldTile::new(
        x,
        y,
        Plane::new(plane).ok_or_else(|| error("invalid plane"))?,
    ))
}

#[expect(
    clippy::too_many_lines,
    reason = "one arm per indexed zone body keeps each wire layout beside its index"
)]
/// Decode one enclosed zone body. Returns `None` for a non-ground-item index or a tile outside
/// the map window (the reference's `method3747` bounds check).
pub(super) fn zone(
    index: u8,
    bytes: &[u8],
    origin: ZoneOrigin,
) -> Result<Option<GroundItemUpdate>> {
    let mut r = Reader::new(bytes, "zone ground item");
    let position = |coord: u8| {
        let offset = TileOffset {
            x: origin.offset.x + i32::from((coord >> 4) & 7),
            y: origin.offset.y + i32::from(coord & 7),
        };
        ((-48..55).contains(&offset.x) && (-48..55).contains(&offset.y)).then_some(
            GroundItemPosition::Zone {
                offset,
                plane: origin.plane,
            },
        )
    };
    let update = match index {
        // OBJ_ADD
        6 => {
            let ticks_until_despawn = r.u16()?;
            let ticks_until_public = r.u16()?;
            let amount = quantity(int(&mut r, G4_ALT3)?)?;
            let never_public = r.u8()? == 1;
            let id = r.u16_le()?;
            let coord = r.u8_add()?;
            let flags = r.u8_neg()?;
            let ownership = r.u8_neg()?;
            position(coord).map(|position| GroundItemUpdate::Add {
                position,
                item: item(id),
                quantity: amount,
                ops: ops(flags),
                ownership: GroundItemOwnership(ownership),
                ticks_until_public,
                ticks_until_despawn,
                never_public,
            })
        }
        // OBJ_CUSTOMISE
        7 => {
            let recolour_index = i16_le(&mut r)?;
            let id = r.u16_add()?;
            let coord = r.u8_add()?;
            let recolour = i16_le(&mut r)?;
            let retexture_index = i16_le(&mut r)?;
            let retexture = i16_add_le(&mut r)?;
            let amount = quantity(int(&mut r, G4_ALT3)?)?;
            let replacement_model = r.u16_add_le()?;
            position(coord).map(|position| GroundItemUpdate::Customise {
                position,
                item: item(id),
                quantity: amount,
                customisation: GroundItemCustomisation {
                    model: model(replacement_model),
                    recolour: replacement(recolour_index, recolour),
                    retexture: replacement(retexture_index, retexture),
                },
            })
        }
        // OBJ_COUNT
        8 => {
            let coord = r.u8_sub()?;
            let old = quantity(int(&mut r, G4_ALT3)?)?;
            let new = quantity(int(&mut r, G4)?)?;
            let id = r.u16_add_le()?;
            position(coord).map(|position| GroundItemUpdate::Count {
                position,
                item: item(id),
                old,
                new,
            })
        }
        // OBJ_ENABLED_OPS
        10 => {
            let id = r.u16_add()?;
            let flags = r.u8_neg()?;
            let coord = r.u8_sub()?;
            position(coord).map(|position| GroundItemUpdate::EnabledOps {
                position,
                item: item(id),
                ops: ops(flags),
            })
        }
        // OBJ_DEL
        12 => {
            let id = r.u16_le()?;
            let coord = r.u8_sub()?;
            let amount = quantity(int(&mut r, G4)?)?;
            position(coord).map(|position| GroundItemUpdate::Delete {
                position,
                item: item(id),
                quantity: amount,
            })
        }
        // OBJ_UNCUSTOMISE
        13 => {
            let amount = quantity(int(&mut r, G4_ALT3)?)?;
            let coord = r.u8_add()?;
            let id = r.u16_le()?;
            position(coord).map(|position| GroundItemUpdate::Uncustomise {
                position,
                item: item(id),
                quantity: amount,
            })
        }
        _ => return Ok(None),
    };
    if r.offset != bytes.len() {
        return Err(error("zone ground item length"));
    }
    Ok(update)
}

/// Decode an `OBJ_*_SPECIFIC` packet, or `None` when `opcode` is not one.
pub(super) fn specific(opcode: u16, payload: &[u8]) -> Result<Option<GroundItemUpdate>> {
    let mut r = Reader::new(payload, "ground item specific");
    let update = match opcode {
        // OBJ_ADD_SPECIFIC
        114 => {
            let amount = quantity(int(&mut r, G4_ALT1)?)?;
            let flags = r.u8_neg()?;
            let tile = coord_grid(int(&mut r, G4_ALT3)?)?;
            let never_public = r.u8_neg()? == 1;
            let id = r.u16_add()?;
            let ticks_until_despawn = r.u16()?;
            let ownership = r.u8()?;
            let ticks_until_public = r.u16_le()?;
            GroundItemUpdate::Add {
                position: GroundItemPosition::Absolute(tile),
                item: item(id),
                quantity: amount,
                ops: ops(flags),
                ownership: GroundItemOwnership(ownership),
                ticks_until_public,
                ticks_until_despawn,
                never_public,
            }
        }
        // OBJ_DEL_SPECIFIC
        86 => {
            let amount = quantity(int(&mut r, G4_ALT3)?)?;
            let tile = coord_grid(int(&mut r, G4_ALT1)?)?;
            let id = r.u16_add_le()?;
            GroundItemUpdate::Delete {
                position: GroundItemPosition::Absolute(tile),
                item: item(id),
                quantity: amount,
            }
        }
        // OBJ_COUNT_SPECIFIC
        124 => {
            let id = r.u16_le()?;
            let tile = coord_grid(int(&mut r, G4_ALT2)?)?;
            let old = quantity(int(&mut r, G4_ALT1)?)?;
            let new = quantity(int(&mut r, G4_ALT2)?)?;
            GroundItemUpdate::Count {
                position: GroundItemPosition::Absolute(tile),
                item: item(id),
                old,
                new,
            }
        }
        // OBJ_ENABLED_OPS_SPECIFIC
        87 => {
            let id = r.u16_add_le()?;
            let tile = coord_grid(int(&mut r, G4_ALT2)?)?;
            let flags = r.u8_sub()?;
            GroundItemUpdate::EnabledOps {
                position: GroundItemPosition::Absolute(tile),
                item: item(id),
                ops: ops(flags),
            }
        }
        // OBJ_CUSTOMISE_SPECIFIC (V1)
        52 => {
            let tile = coord_grid(int(&mut r, G4_ALT3)?)?;
            let recolour_index = i16_le(&mut r)?;
            let replacement_model = r.u16_add_le()?;
            let id = r.u16_add_le()?;
            let retexture_index = i16_le(&mut r)?;
            let amount = quantity(int(&mut r, G4)?)?;
            let recolour = i16_add_le(&mut r)?;
            let retexture = i16_add_le(&mut r)?;
            GroundItemUpdate::Customise {
                position: GroundItemPosition::Absolute(tile),
                item: item(id),
                quantity: amount,
                customisation: GroundItemCustomisation {
                    model: model(replacement_model),
                    recolour: replacement(recolour_index, recolour),
                    retexture: replacement(retexture_index, retexture),
                },
            }
        }
        // OBJ_UNCUSTOMISE_SPECIFIC
        89 => {
            let id = r.u16_add()?;
            let tile = coord_grid(int(&mut r, G4_ALT2)?)?;
            let amount = quantity(int(&mut r, G4_ALT2)?)?;
            GroundItemUpdate::Uncustomise {
                position: GroundItemPosition::Absolute(tile),
                item: item(id),
                quantity: amount,
            }
        }
        _ => return Ok(None),
    };
    if r.offset != payload.len() {
        return Err(error("ground item specific length"));
    }
    Ok(Some(update))
}

#[cfg(test)]
mod tests {
    use super::*;

    fn origin() -> ZoneOrigin {
        ZoneOrigin {
            offset: TileOffset { x: 8, y: -16 },
            plane: Plane::new(1).unwrap(),
        }
    }
    /// Encode `value` so that `int(order)` reads it back.
    fn put_int(out: &mut Vec<u8>, value: u32, order: [usize; 4]) {
        let be = value.to_be_bytes();
        let mut wire = [0_u8; 4];
        for (significance, position) in order.iter().enumerate() {
            wire[*position] = be[significance];
        }
        out.extend_from_slice(&wire);
    }
    fn grid(x: u32, y: u32, plane: u32) -> u32 {
        (plane << 28) | (x << 14) | y
    }

    #[test]
    fn zone_obj_add_reads_every_field_in_stock_order() {
        let mut bytes = vec![0x01, 0x2c, 0x00, 0x64]; // despawn 300, public 100
        put_int(&mut bytes, 1_000_000, G4_ALT3);
        bytes.push(1); // never public
        bytes.extend_from_slice(&995_u16.to_le_bytes());
        bytes.push(0x35_u8.wrapping_add(128)); // tile (3, 5) in zone, add
        bytes.push(0b0_0100_u8.wrapping_neg()); // op 3 enabled, neg
        bytes.push(1_u8.wrapping_neg()); // ownership: self, neg
        assert_eq!(bytes.len(), 14);
        let update = zone(6, &bytes, origin()).unwrap().unwrap();
        assert_eq!(
            update,
            GroundItemUpdate::Add {
                position: GroundItemPosition::Zone {
                    offset: TileOffset { x: 11, y: -11 },
                    plane: Plane::new(1).unwrap(),
                },
                item: ItemDefinitionId::new(995),
                quantity: 1_000_000,
                ops: ActionMask::new([false, false, true, false, false]),
                ownership: GroundItemOwnership::SELF,
                ticks_until_public: 100,
                ticks_until_despawn: 300,
                never_public: true,
            }
        );
    }

    #[test]
    fn zone_count_delete_and_ops_bodies_match_their_fixed_widths() {
        let mut count = vec![128_u8.wrapping_sub(0x12)];
        put_int(&mut count, 5, G4_ALT3);
        put_int(&mut count, 9, G4);
        count.extend_from_slice(&[0x26_u8.wrapping_add(128), 0x01]); // 0x0126 = 294, LE add
        assert_eq!(count.len(), 11);
        assert_eq!(
            zone(8, &count, origin()).unwrap(),
            Some(GroundItemUpdate::Count {
                position: GroundItemPosition::Zone {
                    offset: TileOffset { x: 9, y: -14 },
                    plane: Plane::new(1).unwrap(),
                },
                item: ItemDefinitionId::new(294),
                old: 5,
                new: 9,
            })
        );
        let mut delete = 526_u16.to_le_bytes().to_vec();
        delete.push(128_u8.wrapping_sub(0x00));
        put_int(&mut delete, 1, G4);
        assert_eq!(delete.len(), 7);
        assert!(matches!(
            zone(12, &delete, origin()).unwrap(),
            Some(GroundItemUpdate::Delete { quantity: 1, item, .. }) if item.get() == 526
        ));
        let ops_body = [
            0x02,
            0x0b_u8.wrapping_add(128),
            0b1_0001_u8.wrapping_neg(),
            128,
        ];
        assert!(matches!(
            zone(10, &ops_body, origin()).unwrap(),
            Some(GroundItemUpdate::EnabledOps { ops, item, .. })
                if item.get() == 0x020b && ops.enabled() == [true, false, false, false, true]
        ));
    }

    #[test]
    fn zone_bodies_outside_the_window_are_dropped_and_bad_lengths_fail() {
        let far = ZoneOrigin {
            offset: TileOffset { x: 48, y: 0 },
            plane: Plane::new(0).unwrap(),
        };
        let mut delete = 1_u16.to_le_bytes().to_vec();
        delete.push(128_u8.wrapping_sub(0x70)); // x + 7 = 55, outside -48..55
        put_int(&mut delete, 1, G4);
        assert_eq!(zone(12, &delete, far).unwrap(), None);
        assert!(zone(12, &delete[..6], origin()).is_err());
        let mut negative = 1_u16.to_le_bytes().to_vec();
        negative.push(128);
        put_int(&mut negative, u32::MAX, G4);
        assert!(zone(12, &negative, origin()).is_err());
        assert_eq!(zone(3, &[], origin()).unwrap(), None);
    }

    #[test]
    fn specific_add_and_count_read_absolute_coordinates() {
        let mut add = Vec::new();
        put_int(&mut add, 25, G4_ALT1);
        add.push(0b0_0101_u8.wrapping_neg());
        put_int(&mut add, grid(3222, 3218, 0), G4_ALT3);
        add.push(0_u8.wrapping_neg());
        add.extend_from_slice(&[0x03, 0xe3_u8.wrapping_add(128)]); // 995, BE add
        add.extend_from_slice(&[0x00, 0xc8]); // despawn 200
        add.push(2); // ownership: other
        add.extend_from_slice(&60_u16.to_le_bytes());
        assert_eq!(add.len(), 17);
        assert_eq!(
            specific(114, &add).unwrap(),
            Some(GroundItemUpdate::Add {
                position: GroundItemPosition::Absolute(WorldTile::new(
                    3222,
                    3218,
                    Plane::new(0).unwrap()
                )),
                item: ItemDefinitionId::new(995),
                quantity: 25,
                ops: ActionMask::new([true, false, true, false, false]),
                ownership: GroundItemOwnership::OTHER,
                ticks_until_public: 60,
                ticks_until_despawn: 200,
                never_public: false,
            })
        );
        let mut count = 995_u16.to_le_bytes().to_vec();
        put_int(&mut count, grid(3222, 3218, 2), G4_ALT2);
        put_int(&mut count, 25, G4_ALT1);
        put_int(&mut count, 40, G4_ALT2);
        assert_eq!(count.len(), 14);
        assert!(matches!(
            specific(124, &count).unwrap(),
            Some(GroundItemUpdate::Count { old: 25, new: 40, position: GroundItemPosition::Absolute(tile), .. })
                if tile.plane.get() == 2 && tile.x == 3222
        ));
        assert!(specific(124, &count[..13]).is_err());
        assert_eq!(specific(1, &[]).unwrap(), None);
    }

    #[test]
    fn customise_keeps_present_overrides_and_drops_negative_indices() {
        let mut body = Vec::new();
        put_int(&mut body, grid(10, 20, 0), G4_ALT3);
        body.extend_from_slice(&2_i16.to_le_bytes()); // recolour index
        body.extend_from_slice(&[0xff_u8.wrapping_add(128), 0xff]); // model 65535 = none
        body.extend_from_slice(&[0x01_u8.wrapping_add(128), 0x00]); // id 1
        body.extend_from_slice(&(-1_i16).to_le_bytes()); // no retexture
        put_int(&mut body, 3, G4);
        body.extend_from_slice(&[0x34_u8.wrapping_add(128), 0x12]); // recolour 0x1234
        body.extend_from_slice(&[0x00_u8.wrapping_add(128), 0x00]);
        assert_eq!(body.len(), 20);
        assert_eq!(
            specific(52, &body).unwrap(),
            Some(GroundItemUpdate::Customise {
                position: GroundItemPosition::Absolute(WorldTile::new(
                    10,
                    20,
                    Plane::new(0).unwrap()
                )),
                item: ItemDefinitionId::new(1),
                quantity: 3,
                customisation: GroundItemCustomisation {
                    model: None,
                    recolour: Some((2, 0x1234)),
                    retexture: None,
                },
            })
        );
    }
}
