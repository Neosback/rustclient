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
    for slot in &mut slots {
        let first = r.u8()?;
        if first == 0 {
            *slot = SlotContent::Empty;
        } else {
            let second = r.u8()?;
            let val = (u16::from(first) << 8) | u16::from(second);
            if val >= 0x800 {
                *slot = SlotContent::Item(ItemDefinitionId::new(u32::from(val - 0x800)));
            } else if val >= 0x100 {
                *slot = SlotContent::Kit(KitId::new(u32::from(val - 0x100)));
            }
        }
    }

    // Skip the 12 kit duplicate slots in wire appearance
    for _ in 0..12 {
        let first = r.u8()?;
        if first != 0 {
            r.u8()?;
        }
    }

    let mut colors = [0u8; 5];
    for c in &mut colors {
        *c = r.u8()?;
    }

    let ready_anim = sequence_id(r.u16()?);
    let turn_anim = sequence_id(r.u16()?);
    let walk_anim = sequence_id(r.u16()?);
    let walk_anim_back = sequence_id(r.u16()?);
    let walk_anim_left = sequence_id(r.u16()?);
    let walk_anim_right = sequence_id(r.u16()?);
    let run_anim = sequence_id(r.u16()?);

    let movement_sequences = MovementSequences {
        idle: ready_anim,
        turn: turn_anim,
        walk: walk_anim,
        walk_back: walk_anim_back,
        walk_left: walk_anim_left,
        walk_right: walk_anim_right,
        run: run_anim,
    };

    // Name string (terminated with 0)
    while r.u8()? != 0 {}

    let combat_level = r.u8()?;
    let skill_level = r.u16()?;
    let hidden = r.u8()? == 1;

    Ok(PlayerAppearance {
        gender,
        slots,
        colors,
        movement_sequences,
        combat_level,
        skill_level,
        hidden,
    })
}
