//! Appearance adapter for the selected desktop profile.
use crate::{Result, wire::Reader};
use rustclient_model::{
    Gender, ItemDefinitionId, KitId, MovementSequences, PlayerAppearance, SequenceId, SlotContent,
};
pub(super) fn sequence_id(value: u16) -> Option<SequenceId> {
    (value != u16::MAX).then_some(SequenceId::new(u32::from(value)))
}
pub(super) fn decode_appearance(bytes: &[u8]) -> Result<PlayerAppearance> {
    let mut r = Reader::new(bytes, "appearance payload");
    let gender = if r.u8()? == 0 {
        Gender::Male
    } else {
        Gender::Female
    };
    let _skull = r.u8()?;
    let _overhead = r.u8()?;

    let mut slots = [SlotContent::Empty; 12];
    for (slot_index, slot) in slots.iter_mut().enumerate() {
        let first = r.u8()?;
        if first == 0 {
            continue;
        }
        let second = r.u8()?;
        let value = (u16::from(first) << 8) | u16::from(second);
        if slot_index == 0 && value == u16::MAX {
            // NPC-transformed player appearance. The semantic model does not expose the transform
            // yet, but consuming the definition id is required to keep the rest of the block aligned.
            let _npc_definition = r.u16()?;
            break;
        }
        if value >= 0x800 {
            *slot = SlotContent::Item(ItemDefinitionId::new(u32::from(value - 0x800)));
        } else if value >= 0x100 {
            *slot = SlotContent::Kit(KitId::new(u32::from(value - 0x100)));
        }
    }

    // Skip the 12 kit duplicate/customisation slots in wire appearance.
    for _ in 0..12 {
        let first = r.u8()?;
        if first != 0 {
            let _ = r.u8()?;
        }
    }

    let mut colors = [0u8; 5];
    for color in &mut colors {
        *color = r.u8()?;
    }

    let movement_sequences = MovementSequences {
        idle: sequence_id(r.u16()?),
        turn: sequence_id(r.u16()?),
        walk: sequence_id(r.u16()?),
        walk_back: sequence_id(r.u16()?),
        walk_left: sequence_id(r.u16()?),
        walk_right: sequence_id(r.u16()?),
        run: sequence_id(r.u16()?),
    };

    let mut display_name = String::new();
    loop {
        let byte = r.u8()?;
        if byte == 0 {
            break;
        }
        if display_name.len() >= 320 {
            return Err(crate::ProtocolError::new(
                "appearance",
                "display name limit",
            ));
        }
        display_name.push(crate::wire::decode_cp1252(byte));
    }

    let combat_level = r.u8()?;
    let skill_level = r.u16()?;
    let hidden = r.u8()? == 1;

    Ok(PlayerAppearance {
        display_name,
        gender,
        slots,
        colors,
        movement_sequences,
        combat_level,
        skill_level,
        hidden,
    })
}
