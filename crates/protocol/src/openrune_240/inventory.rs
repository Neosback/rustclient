//! Selected `RSProt` 240 inventory family; no widget or state mutation here.
use super::info_fields::smart;
use crate::{ProtocolError, Result, wire::Reader};
use rustclient_model::{
    InventoryId, InventorySlot, InventorySlotUpdate, InventoryUpdate, ItemDefinitionId, ItemStack,
};
const MAX_SLOTS: usize = 16_384;
fn invalid(detail: &str) -> ProtocolError {
    ProtocolError::new("inventory update", detail)
}
fn integer(reader: &mut Reader<'_>) -> Result<u32> {
    let b = reader.take(4)?;
    Ok(u32::from_be_bytes([b[0], b[1], b[2], b[3]]))
}
fn quantity(reader: &mut Reader<'_>, short: u8) -> Result<u32> {
    let amount = if short == 255 {
        integer(reader)?
    } else {
        u32::from(short)
    };
    if amount > i32::MAX.cast_unsigned() {
        return Err(invalid("negative item quantity"));
    }
    Ok(amount)
}
fn stack(item: u16, amount: u32) -> Option<ItemStack> {
    item.checked_sub(1).map(|id| ItemStack {
        item: ItemDefinitionId::new(u32::from(id)),
        amount,
    })
}
pub(super) fn decode(opcode: u16, payload: &[u8]) -> Result<InventoryUpdate> {
    let mut reader = Reader::new(payload, "inventory update");
    if opcode == 14 {
        let lo = reader.u8()?;
        let hi = reader.u8()?;
        if reader.offset != payload.len() {
            return Err(invalid("trailing stop-transmit bytes"));
        }
        return Ok(InventoryUpdate::Remove(InventoryId::new(u32::from(
            u16::from_le_bytes([lo, hi]),
        ))));
    }
    let address = integer(&mut reader)?.cast_signed();
    if address >= 0 {
        return Err(invalid("obsolete IF1 inventory address is unsupported"));
    }
    let id =
        InventoryId::new(u32::from(reader.u16()?) + if address < -70_000 { 32_768 } else { 0 });
    let update = match opcode {
        74 => {
            let count = usize::from(reader.u16()?);
            if count > MAX_SLOTS {
                return Err(invalid("slot limit exceeded"));
            }
            let mut slots = Vec::with_capacity(count);
            for _ in 0..count {
                let short = 128_u8.wrapping_sub(reader.u8()?);
                let amount = quantity(&mut reader, short)?;
                let item = reader.u16()?;
                slots.push(stack(item, amount));
            }
            InventoryUpdate::Replace { id, slots }
        }
        23 => {
            let mut slots = Vec::new();
            while reader.offset < payload.len() {
                let slot = smart(&mut reader)?;
                if usize::from(slot) >= MAX_SLOTS || slots.len() >= MAX_SLOTS {
                    return Err(invalid("slot limit exceeded"));
                }
                let item = reader.u16()?;
                let amount = if item == 0 {
                    0
                } else {
                    let short = reader.u8()?;
                    quantity(&mut reader, short)?
                };
                slots.push(InventorySlotUpdate {
                    slot: InventorySlot::new(u32::from(slot)),
                    stack: stack(item, amount),
                });
            }
            InventoryUpdate::Patch { id, slots }
        }
        _ => return Err(invalid("unsupported inventory opcode")),
    };
    if reader.offset != payload.len() {
        return Err(invalid("trailing inventory bytes"));
    }
    Ok(update)
}

#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn inventory_family_preserves_slots_counts_mirrors_and_bounds() {
        // Independent hand-authored RSProt fields: inventory 93, empty, item 0
        // with zero quantity, coins with extended quantity 300.
        let full = [
            255, 255, 255, 255, 0, 93, 0, 3, 128, 0, 0, 128, 0, 1, 129, 0, 0, 1, 44, 3, 228,
        ];
        let decoded = decode(74, &full).unwrap();
        assert_eq!(
            decoded,
            InventoryUpdate::Replace {
                id: InventoryId::new(93),
                slots: vec![
                    None,
                    Some(ItemStack {
                        item: ItemDefinitionId::new(0),
                        amount: 0
                    }),
                    Some(ItemStack {
                        item: ItemDefinitionId::new(995),
                        amount: 300
                    })
                ]
            }
        );
        for length in 0..full.len() {
            assert!(decode(74, &full[..length]).is_err());
        }
        let mut extra = full.to_vec();
        extra.push(0);
        assert!(decode(74, &extra).is_err());
        let patch = [
            255, 255, 255, 255, 0, 93, 128, 128, 3, 228, 255, 0, 0, 1, 44, 0, 0, 0,
        ];
        assert_eq!(
            decode(23, &patch).unwrap(),
            InventoryUpdate::Patch {
                id: InventoryId::new(93),
                slots: vec![
                    InventorySlotUpdate {
                        slot: InventorySlot::new(128),
                        stack: Some(ItemStack {
                            item: ItemDefinitionId::new(995),
                            amount: 300
                        })
                    },
                    InventorySlotUpdate {
                        slot: InventorySlot::new(0),
                        stack: None
                    }
                ]
            }
        );
        assert_eq!(
            decode(14, &[93, 128]).unwrap(),
            InventoryUpdate::Remove(InventoryId::new(32861))
        );
        assert!(decode(14, &[93]).is_err());
        assert!(decode(14, &[93, 0, 0]).is_err());
        let mut mirrored = full;
        mirrored[..4].copy_from_slice(&(-70_001_i32).to_be_bytes());
        assert_eq!(decode(74, &mirrored).unwrap().id(), InventoryId::new(32861));
        let mut obsolete = full;
        obsolete[..4].fill(0);
        assert!(decode(74, &obsolete).is_err());
        let mut negative = full;
        negative[15] = 128;
        assert!(decode(74, &negative).is_err());
        assert!(decode(74, &[255, 255, 255, 255, 0, 93, 255, 255]).is_err());
        assert!(decode(23, &[255, 255, 255, 255, 0, 93, 192, 0, 0, 0]).is_err());
    }
}
