//! Ordered revision-240 player extended-info blocks.
//!
//! The byte order and transforms intentionally mirror `PlayerUpdateManager.method2946` from the
//! revision-240 deob. Non-modeled effects are still consumed exactly so later semantic fields stay
//! aligned.
use super::player_appearance::{decode_appearance, sequence_id};
use crate::{ProtocolError, Result, wire::Reader};
use rustclient_model::{ExactMove, MoveSpeed, PlayerAppearance, SequenceChange};

const KNOWN: u32 = 0x000001
    | 0x000002
    | 0x000004
    | 0x000008
    | 0x000010
    | 0x000020
    | 0x000040
    | 0x000080
    | 0x000100
    | 0x000200
    | 0x000400
    | 0x000800
    | 0x001000
    | 0x002000
    | 0x004000
    | 0x010000;

#[derive(Default)]
pub(super) struct Extended {
    pub appearance: Option<PlayerAppearance>,
    pub sequence: SequenceChange,
    pub sequence_delay: u8,
    pub facing: Option<u16>,
    pub temporary_speed: Option<MoveSpeed>,
    pub persistent_speed: Option<MoveSpeed>,
    pub exact_move: Option<ExactMove>,
}

pub(super) fn decode_extended(reader: &mut Reader<'_>) -> Result<Extended> {
    let mut out = Extended::default();
    let mask = read_mask(reader)?;
    if mask & !KNOWN != 0 {
        return Err(ProtocolError::new(
            "player extended",
            format!("unknown mask bits {:#x}", mask & !KNOWN),
        ));
    }

    // 0x4: appearance.
    if mask & 4 != 0 {
        let length = usize::from(reader.u8_add()?);
        let wire = reader.take(length)?;
        let bytes: Vec<u8> = wire
            .iter()
            .rev()
            .map(|byte| byte.wrapping_sub(128))
            .collect();
        out.appearance = Some(decode_appearance(&bytes)?);
    }

    // 0x4000: temporary traversal override for this deferred movement only.
    if mask & 16_384 != 0 {
        let raw = reader.i8_neg()?;
        out.temporary_speed = Some(move_speed(if raw == 127 { -1 } else { raw })?);
    }

    // 0x2000: timed actor colour/model override. The renderer does not model it yet.
    if mask & 8192 != 0 {
        let _ = reader.u16_add()?;
        let _ = reader.u16_add()?;
        let _ = reader.i8_sub()?;
        let _ = reader.i8_add()?;
        let _ = reader.i8_sub()?;
        let _ = reader.u8_add()?;
    }

    // 0x80: facing/orientation override.
    if mask & 128 != 0 {
        out.facing = Some(reader.u16()?);
    }

    // 0x10: action sequence and start delay.
    if mask & 16 != 0 {
        out.sequence = SequenceChange::Set(sequence_id(reader.u16_le()?));
        out.sequence_delay = reader.u8_add()?;
    }

    // 0x20: overhead text.
    if mask & 32 != 0 {
        skip_cstring(reader)?;
    }

    // 0x1000: public chat payload and optional icon bytes.
    if mask & 4096 != 0 {
        let style = reader.u16_add()?;
        let _ = reader.u8_neg()?;
        let _ = reader.u8_neg()?;
        let length = usize::from(reader.u8_sub()?);
        let _ = reader.take(length)?;
        let icon_count = match style >> 8 {
            13..=20 => usize::from((style >> 8) - 12),
            _ => 0,
        };
        for _ in 0..icon_count {
            let _ = reader.i8_add()?;
        }
    }

    // 0x40: hitsplats and health bars.
    if mask & 64 != 0 {
        skip_hitsplats_and_healthbars(reader)?;
    }

    // 0x800: persistent path traversal speed.
    if mask & 2048 != 0 {
        out.persistent_speed = Some(move_speed(reader.i8_add()?)?);
    }

    // 0x2: revision-240 auxiliary payload. It is not semantically modeled yet.
    if mask & 2 != 0 {
        let _ = reader.u16_le()?;
        let _ = reader.u8_neg()?;
        let _ = reader.u8()?;
        let length = usize::from(reader.u8_sub()?);
        let _ = reader.take(length)?;
    }

    // 0x10000: spot-animation updates.
    if mask & 65_536 != 0 {
        let count = usize::from(reader.u8()?);
        for _ in 0..count {
            let _ = reader.u8()?;
            let _ = reader.u16_add_le()?;
            let _ = reader.u32_me()?;
        }
    }

    // 0x400: three player interaction/action strings.
    if mask & 1024 != 0 {
        for _ in 0..3 {
            skip_cstring(reader)?;
        }
    }

    // 0x200: exact/forced movement.
    if mask & 512 != 0 {
        out.exact_move = Some(ExactMove {
            start_delta_x: reader.i8_sub()?,
            start_delta_y: reader.u8()?.cast_signed(),
            end_delta_x: reader.u8()?.cast_signed(),
            end_delta_y: reader.i8_add()?,
            start_delay: reader.u16_add()?,
            end_delay: reader.u16()?,
            direction: reader.u16_add_le()?,
        });
    }

    // 0x8: 24-bit actor target. It is consumed now and can be modeled with actor targeting later.
    if mask & 8 != 0 {
        let low = u32::from(reader.u16()?);
        let high = u32::from(reader.u8_sub()?);
        let _target = match low | (high << 16) {
            16_777_215 => None,
            value => Some(value),
        };
    }

    Ok(out)
}

fn read_mask(reader: &mut Reader<'_>) -> Result<u32> {
    let mut mask = u32::from(reader.u8()?);
    if mask & 1 != 0 {
        mask |= u32::from(reader.u8()?) << 8;
    }
    if mask & 256 != 0 {
        mask |= u32::from(reader.u8()?) << 16;
    }
    Ok(mask)
}

fn move_speed(value: i8) -> Result<MoveSpeed> {
    MoveSpeed::from_wire(value).ok_or_else(|| {
        ProtocolError::new(
            "player extended",
            format!("invalid revision-240 movement speed {value}"),
        )
    })
}

fn skip_cstring(reader: &mut Reader<'_>) -> Result<()> {
    loop {
        if reader.u8()? == 0 {
            return Ok(());
        }
    }
}

fn skip_hitsplats_and_healthbars(reader: &mut Reader<'_>) -> Result<()> {
    let hit_count = usize::from(reader.u8_neg()?);
    for _ in 0..hit_count {
        let hit_type = reader.u_short_smart()?;
        if hit_type == 32_767 {
            for _ in 0..4 {
                let _ = reader.u_short_smart()?;
            }
        } else if hit_type != 32_766 {
            let _ = reader.u_short_smart()?;
        }
        let _ = reader.u_short_smart()?;
    }

    let health_count = usize::from(reader.u8_neg()?);
    for _ in 0..health_count {
        let _ = reader.u_short_smart()?;
        let value = reader.u_short_smart()?;
        if value != 32_767 {
            let _ = reader.u_short_smart()?;
            let _ = reader.u8()?;
            if value > 0 {
                let _ = reader.u8_add()?;
            }
        }
    }
    Ok(())
}

#[cfg(test)]
mod tests {
    use super::*;
    use rustclient_model::SequenceId;

    #[test]
    fn mask_extensions_use_revision_240_bits_zero_and_eight() {
        let mut reader = Reader::new(&[0x01, 0x01, 0x01], "test");
        assert_eq!(read_mask(&mut reader).unwrap(), 0x01_0101);
    }

    #[test]
    fn sequence_uses_little_endian_short_and_add_delay() {
        let mut reader = Reader::new(&[0x10, 0x34, 0x12, 0x85], "test");
        let decoded = decode_extended(&mut reader).unwrap();
        assert_eq!(
            decoded.sequence,
            SequenceChange::Set(Some(SequenceId::new(0x1234)))
        );
        assert_eq!(decoded.sequence_delay, 5);
    }

    #[test]
    fn exact_move_uses_revision_240_transforms() {
        let mut reader = Reader::new(
            &[
                0x01, 0x02, // extended mask 0x200
                129, 0, 2, 129, 0, 133, 0, 10, 128, 4,
            ],
            "test",
        );
        let decoded = decode_extended(&mut reader).unwrap();
        assert_eq!(
            decoded.exact_move,
            Some(ExactMove {
                start_delta_x: -1,
                start_delta_y: 0,
                end_delta_x: 2,
                end_delta_y: 1,
                start_delay: 5,
                end_delay: 10,
                direction: 1024,
            })
        );
    }
}
