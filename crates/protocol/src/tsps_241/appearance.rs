//! Strict TSPS appearance subset. Unknown effects are errors, never guessed byte skips.
use super::reader::Reader;
use crate::{ProtocolError, Result};
use rustclient_model::{
    Gender, ItemDefinitionId, KitId, MovementSequences, PlayerAppearance, SequenceId, SlotContent,
};

fn unsupported() -> ProtocolError {
    ProtocolError::new(
        "TSPS appearance",
        "appearance extension needs semantic support",
    )
}
pub(super) fn decode(bytes: &[u8]) -> Result<PlayerAppearance> {
    let mut r = Reader::new(bytes);
    let gender_byte = r.u8()?;
    let gender = match gender_byte {
        0 => Gender::Male,
        1 => Gender::Female,
        _ => return Err(unsupported()),
    };
    // The current semantic appearance does not retain head icons, transforms, customisation,
    // name extras or worn-container extension facts. Reject them rather than lose their effects.
    if r.u8()? != 255 || r.u8()? != 255 {
        return Err(unsupported());
    }
    let mut raw = [0; 12];
    for slot in &mut raw {
        *slot = equipment(&mut r)?;
    }
    for expected in raw {
        if equipment(&mut r)? != expected {
            return Err(unsupported());
        }
    }
    let mut slots = [SlotContent::Empty; 12];
    for (target, value) in slots.iter_mut().zip(raw) {
        *target = match value {
            0 => SlotContent::Empty,
            256..=2047 => SlotContent::Kit(KitId::new(u32::from(value - 256))),
            2048..=65534 => SlotContent::Item(ItemDefinitionId::new(u32::from(value - 2048))),
            _ => return Err(unsupported()),
        };
    }
    let mut colors = [0; 5];
    for color in &mut colors {
        *color = r.u8()?;
    }
    let sequence = |value| (value != u16::MAX).then(|| SequenceId::new(u32::from(value)));
    let movement_sequences = MovementSequences {
        idle: sequence(r.u16()?),
        turn: sequence(r.u16()?),
        walk: sequence(r.u16()?),
        walk_back: sequence(r.u16()?),
        walk_left: sequence(r.u16()?),
        walk_right: sequence(r.u16()?),
        run: sequence(r.u16()?),
    };
    let display_name = r.string()?;
    if display_name.len() > 320 {
        return Err(ProtocolError::new("TSPS appearance", "display name limit"));
    }
    let combat_level = r.u8()?;
    let skill_level = r.u16()?;
    let hidden = r.boolean()?;
    if r.u16()? != 0 {
        return Err(unsupported());
    }
    for _ in 0..3 {
        if !r.string()?.is_empty() {
            return Err(unsupported());
        }
    }
    if r.u8()? != gender_byte {
        return Err(unsupported());
    }
    let ammo_quantity = r.u32()?;
    let ammo_item = r.i32()?;
    let ring_item = r.i32()?;
    let weapon_quantity = r.u32()?;
    if ammo_quantity != 0 || ammo_item != -1 || ring_item != -1 || weapon_quantity != 0 {
        return Err(unsupported());
    }
    r.finish()?;
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
fn equipment(r: &mut Reader<'_>) -> Result<u16> {
    let first = r.u8()?;
    let value = if first == 0 {
        0
    } else {
        u16::from(first) << 8 | u16::from(r.u8()?)
    };
    if value == u16::MAX {
        return Err(unsupported());
    }
    Ok(value)
}

#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn source_encoded_appearance_round_trips_and_unowned_extensions_are_rejected() {
        let appearance = decode(include_bytes!(
            "../../tests/fixtures/tsps-241/appearance-naked.bin"
        ))
        .unwrap();
        assert_eq!(appearance.display_name, "Test");
        assert_eq!(appearance.combat_level, 3);
        assert_eq!(
            appearance.movement_sequences.walk,
            Some(SequenceId::new(819))
        );
        assert!(
            decode(include_bytes!(
                "../../tests/fixtures/tsps-241/appearance-ring.bin"
            ))
            .is_err()
        );
    }
}
