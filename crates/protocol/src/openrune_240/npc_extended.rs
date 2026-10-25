//! Verified stock NPC extended blocks; customization layouts fail explicitly.
use super::{
    info_fields::{face, smart},
    player_appearance::sequence_id,
};
use crate::{ProtocolError, Result, wire::Reader};
use rustclient_model::{ActionMask, NpcAttributeUpdate, NpcIndex, NpcProperty};
const SUPPORTED: u32 = 0x1
    | 0x2
    | 0x4
    | 0x8
    | 0x10
    | 0x20
    | 0x80
    | 0x100
    | 0x200
    | 0x400
    | 0x800
    | 0x1000
    | 0x2000
    | 0x8000
    | 0x0001_0000
    | 0x0002_0000
    | 0x0004_0000
    | 0x0100_0000;
#[expect(
    clippy::too_many_lines,
    reason = "profile-defined ordered block layout"
)]
pub(super) fn decode_attributes(r: &mut Reader<'_>, index: NpcIndex) -> Result<NpcAttributeUpdate> {
    let mut mask = u32::from(r.u8()?);
    if mask & 0x20 != 0 {
        mask |= u32::from(r.u8()?) << 8;
    }
    if mask & 0x1000 != 0 {
        mask |= u32::from(r.u8()?) << 16;
    }
    if mask & 0x0004_0000 != 0 {
        mask |= u32::from(r.u8()?) << 24;
    }
    if mask & !SUPPORTED != 0 {
        return Err(ProtocolError::new(
            "NPC extended",
            "unsupported customization or mask bits",
        ));
    }
    let mut changes = Vec::new();
    if mask & 0x200 != 0 {
        r.take(8)?;
    }
    if mask & 2 != 0 {
        let count = 128_u8.wrapping_sub(r.u8()?);
        r.take(usize::from(count) * 8)?;
    }
    if mask & 0x0001_0000 != 0 {
        let flags = r.u8()?.wrapping_sub(128);
        for bit in 0..8 {
            if flags & (1 << bit) != 0 {
                let width = if r.bytes.get(r.offset).is_some_and(|v| v & 128 != 0) {
                    4
                } else {
                    2
                };
                r.take(width)?;
                smart(r)?;
            }
        }
    }
    if mask & 0x400 != 0 {
        let flags = 128_u8.wrapping_sub(r.u8()?);
        changes.push(NpcProperty::Actions(ActionMask::new(std::array::from_fn(
            |i| flags & (1 << i) != 0,
        ))));
    }
    if mask & 0x10 != 0 {
        changes.push(NpcProperty::Overhead(super::speech::say(r)?));
    }
    if mask & 1 != 0 {
        r.take(5)?;
    }
    if mask & 0x800 != 0 {
        r.take(4)?;
    }
    if mask & 0x2000 != 0 {
        changes.push(NpcProperty::ExactMove(rustclient_model::ExactMove {
            start_delta_x: r.i8_add()?,
            start_delta_y: r.i8_sub()?,
            end_delta_x: r.u8()?.cast_signed(),
            end_delta_y: r.i8_add()?,
            start_delay: r.u16_le()?,
            end_delay: r.u16_add()?,
            direction: r.u16_le()?,
        }));
    }
    if mask & 4 != 0 {
        let lo = r.u8()?;
        let hi = r.u8()?;
        let delay = u32::from(r.u8()?.wrapping_sub(128));
        changes.push(NpcProperty::Sequence {
            sequence: sequence_id(u16::from_le_bytes([lo, hi])),
            delay,
        });
    }
    if mask & 0x0002_0000 != 0 {
        let count = r.u8()?.wrapping_sub(128);
        for _ in 0..count {
            for _ in 0..4 {
                smart(r)?;
            }
        }
    }
    if mask & 0x8000 != 0 {
        r.take(7)?;
    }
    if mask & 0x100 != 0 {
        let name = read_text(r)?;
        changes.push(NpcProperty::Name((!name.is_empty()).then_some(name)));
    }
    if mask & 8 != 0 {
        let hi = r.u8()?;
        let lo = r.u8()?.wrapping_sub(128);
        changes.push(NpcProperty::Definition(
            rustclient_model::NpcDefinitionId::new(u32::from(u16::from_be_bytes([hi, lo]))),
        ));
    }
    if mask & 0x80 != 0 {
        let flag = r.u8()?.wrapping_sub(128);
        if let Some(angle) = face(r, flag)? {
            changes.push(NpcProperty::Facing(angle));
        }
    }
    if mask & 0x0100_0000 != 0 {
        let count = r.u8()?.wrapping_sub(128);
        for _ in 0..count {
            smart(r)?;
            let end = smart(r)?;
            if end != 32767 {
                smart(r)?;
                r.u8()?;
                if end > 0 {
                    r.u8()?;
                }
            }
        }
    }
    Ok(NpcAttributeUpdate { index, changes })
}
fn read_text(r: &mut Reader<'_>) -> Result<String> {
    let mut text = String::new();
    for _ in 0..=256 {
        let byte = r.u8()?;
        if byte == 0 {
            return Ok(text);
        }
        text.push(crate::wire::decode_cp1252(byte));
    }
    Err(ProtocolError::new("NPC text", "text limit exceeded"))
}

#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn forced_move_and_sequence_mixed_mask_uses_npc_transforms_and_checks_every_prefix() {
        let bytes = [0x24, 0x20, 126, 125, 4, 123, 2, 0, 0, 136, 0, 6, 40, 3, 130];
        let update =
            decode_attributes(&mut Reader::new(&bytes, "fixture"), NpcIndex::new(42)).unwrap();
        assert_eq!(
            update.changes,
            vec![
                NpcProperty::ExactMove(rustclient_model::ExactMove {
                    start_delta_x: -2,
                    start_delta_y: 3,
                    end_delta_x: 4,
                    end_delta_y: -5,
                    start_delay: 2,
                    end_delay: 8,
                    direction: 1536,
                }),
                NpcProperty::Sequence {
                    sequence: Some(rustclient_model::SequenceId::new(808)),
                    delay: 2
                }
            ]
        );
        for length in 0..bytes.len() {
            assert!(
                decode_attributes(
                    &mut Reader::new(&bytes[..length], "fixture"),
                    NpcIndex::new(42)
                )
                .is_err()
            );
        }
    }
}
