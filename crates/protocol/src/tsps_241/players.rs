//! TSPS extended-player fields. Four-pass GPI framing is shared; masks/transforms are not.
use crate::{ProtocolError, Result, openrune_240::player_extended::Extended, wire::Reader};
use rustclient_model::{
    ActorSpotAnim, ExactMove, FacingBlock, FacingTarget, FacingWalkPolicy, MoveSpeed,
    SequenceChange, SequenceId, SpeechUpdate,
};

const KNOWN: u32 = 1 | 2 | 4 | 8 | 0x40 | 0x80 | 0x400 | 0x1000 | 0x2000 | 0x4000 | 0x10000;

pub(super) fn fields(r: &mut Reader<'_>, _chat: Option<&crate::Huffman>) -> Result<Extended> {
    let mut out = Extended::default();
    let mut mask = u32::from(r.u8()?);
    if mask & 0x80 != 0 {
        mask |= u32::from(r.u8()?) << 8;
    }
    if mask & 0x4000 != 0 {
        mask |= u32::from(r.u8()?) << 16;
    }
    // Unsupported fields fail before publishing this packet; never use the 240 mask table here.

    if mask & !KNOWN != 0 {
        return Err(ProtocolError::new(
            "TSPS player fields",
            format!("unsupported mask {:#x}", mask & !KNOWN),
        ));
    }
    if mask & 1 != 0 {
        let text = string(r)?;
        out.speech.push(SpeechUpdate::Say(text));
    }
    if mask & 2 != 0 {
        out.facing = Some(r.u16_le()? & 2047);
    }
    if mask & 0x40 != 0 {
        let index = u32::from(r.u16()?) | (u32::from(r.u8()?) << 16);
        if index != 0xff_ffff {
            return Err(ProtocolError::new(
                "TSPS player facing",
                "entity-target fallback semantics need authoritative actor support",
            ));
        }
        out.facing_block = Some(FacingBlock {
            target: FacingTarget::Cleared,
            walk: FacingWalkPolicy::TurnOnWalk,
            instant: false,
        });
    }
    if mask & 8 != 0 {
        let id = r.u16_add_le()?;
        out.sequence =
            SequenceChange::Set((id != u16::MAX).then(|| SequenceId::new(u32::from(id))));
        out.sequence_delay = r.u8()?;
    }
    if mask & 0x1000 != 0 {
        out.persistent_speed = Some(speed(r.i8_neg()?)?);
    }
    if mask & 4 != 0 {
        let length = usize::from(r.u8_neg()?);
        let data = r.take(length)?;
        out.appearance = Some(super::appearance::decode(data)?);
    }
    if mask & 0x400 != 0 {
        out.exact_move = Some(exact(r)?);
    }
    if mask & 0x2000 != 0 {
        let value = r.i8_sub()?;
        out.temporary_speed = Some(if value == 127 {
            MoveSpeed::Stationary
        } else {
            speed(value)?
        });
    }
    if mask & 0x10000 != 0 {
        let count = r.u8_add()?;
        for _ in 0..count {
            let slot = r.u8()?;
            let id = r.u16()?;
            if id != u16::MAX {
                return Err(ProtocolError::new(
                    "TSPS spot animation",
                    "positive effects require cache-resolved duration semantics",
                ));
            }
            let b = r.take(4)?;
            let packed = u32::from_be_bytes([b[2], b[3], b[0], b[1]]);
            out.effects.spotanims.push(ActorSpotAnim {
                slot,
                id: None,
                height: u16::try_from(packed >> 16)
                    .map_err(|_| ProtocolError::new("TSPS spot", "height"))?,
                delay: u16::try_from(packed & 65535)
                    .map_err(|_| ProtocolError::new("TSPS spot", "delay"))?,
                looping: false,
            });
        }
    }
    Ok(out)
}
pub(super) fn exact(r: &mut Reader<'_>) -> Result<ExactMove> {
    Ok(ExactMove {
        start_delta_x: r.i8_sub()?,
        start_delta_y: r.u8()?.cast_signed(),
        end_delta_x: r.u8()?.cast_signed(),
        end_delta_y: r.i8_add()?,
        start_delay: r.u16_add()?,
        end_delay: r.u16()?,
        direction: r.u16_add_le()?,
    })
}
fn speed(value: i8) -> Result<MoveSpeed> {
    match value {
        -1 => Ok(MoveSpeed::Stationary),
        0 => Ok(MoveSpeed::Crawl),
        1 => Ok(MoveSpeed::Walk),
        2 => Ok(MoveSpeed::Run),
        _ => Err(ProtocolError::new(
            "TSPS player fields",
            "invalid traversal",
        )),
    }
}
fn string(r: &mut Reader<'_>) -> Result<String> {
    let mut text = String::new();
    loop {
        let byte = r.u8()?;
        if byte == 0 {
            return Ok(text);
        }
        if text.len() >= 16_384 {
            return Err(ProtocolError::new("TSPS player fields", "text limit"));
        }
        text.push(char::from(byte));
    }
}
