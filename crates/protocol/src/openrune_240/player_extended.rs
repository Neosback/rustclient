//! Ordered `RSProt` desktop extended-info blocks; non-modeled effects are consumed
//! exactly so later semantic fields remain aligned.
use super::info_fields::{face, smart};
use super::player_appearance::{decode_appearance, sequence_id};
use crate::{ProtocolError, Result, wire::Reader};
use rustclient_model::{PlayerAppearance, SequenceChange};
const KNOWN: u32 = 0x10
    | 4
    | 0x40
    | 8
    | 1
    | 0x1000
    | 0x4000
    | 0x400
    | 0x8000
    | 0x800
    | 0x20
    | 0x20000
    | 0x0010_0000
    | 0x40000
    | 0x10000
    | 0x80
    | 0x2000;
#[derive(Default)]
pub(super) struct Extended {
    pub appearance: Option<PlayerAppearance>,
    pub sequence: SequenceChange,
    pub facing: Option<u16>,
}
pub(super) fn decode_extended(r: &mut Reader<'_>) -> Result<Extended> {
    let mut out = Extended::default();
    let mut mask = u32::from(r.u8()?);
    if mask & 0x80 != 0 {
        mask |= u32::from(r.u8()?) << 8;
    }
    if mask & 0x2000 != 0 {
        mask |= u32::from(r.u8()?) << 16;
    }

    if mask & !KNOWN != 0 {
        return Err(ProtocolError::new("player extended", "unknown mask bits"));
    }
    if mask & 0x20 != 0 {
        let count = r.u8()?.wrapping_sub(128);
        r.take(usize::from(count) * 8)?;
    }
    if mask & 0x1000 != 0 {
        let colour = r.u8()?;
        r.take(3)?;
        let length = r.u8()?.wrapping_sub(128);
        r.take(usize::from(length))?;
        if (13..=20).contains(&colour) {
            r.take(usize::from(colour - 12))?;
        }
    }
    if mask & 0x20000 != 0 {
        let count = 128_u8.wrapping_sub(r.u8()?);
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
    if mask & 4 != 0 {
        let length = usize::from(128_u8.wrapping_sub(r.u8()?));
        let mut bytes = r.take(length)?.to_vec();
        bytes.reverse();
        out.appearance = Some(decode_appearance(&bytes)?);
    }
    if mask & 0x40000 != 0 {
        r.take(5)?;
    }
    if mask & 0x40 != 0 {
        let flag = r.u8()?;
        out.facing = face(r, flag)?;
    }
    if mask & 0x4000 != 0 {
        r.u8()?;
    }
    if mask & 0x800 != 0 {
        r.u8()?;
    }
    if mask & 0x10000 != 0 {
        r.take(7)?;
    }
    if mask & 0x0010_0000 != 0 {
        let count = r.u8()?;
        for _ in 0..count {
            for _ in 0..4 {
                smart(r)?;
            }
        }
    }
    if mask & 0x8000 != 0 {
        r.take(10)?;
    }
    if mask & 0x10 != 0 {
        let lo = r.u8()?.wrapping_sub(128);
        let hi = r.u8()?;
        out.sequence = SequenceChange::Set(sequence_id(u16::from_le_bytes([lo, hi])));
        r.u8()?;
    }
    if mask & 0x400 != 0 {
        r.take(8)?;
    }
    if mask & 8 != 0 {
        r.u8()?;
    }
    if mask & 1 != 0 {
        while r.u8()? != 0 {}
    }
    Ok(out)
}
