//! RUNCLIENTSCRIPT 240 arguments are transmitted in reverse descriptor order.
use crate::{ProtocolError, Result, wire::Reader};
use rustclient_model::{ScriptId, ScriptRequest, ScriptValue};
fn text(r: &mut Reader<'_>) -> Result<String> {
    let mut s = String::new();
    loop {
        let b = r.u8()?;
        if b == 0 {
            return Ok(s);
        }
        s.push(crate::wire::decode_cp1252(b));
        if s.len() > 16_384 {
            return Err(ProtocolError::new("client script", "text limit"));
        }
    }
}
fn varint(r: &mut Reader<'_>) -> Result<u32> {
    let mut n = 0_u32;
    for shift in (0..35).step_by(7) {
        let b = r.u8()?;
        if shift == 28 && b & 0xf0 != 0 {
            return Err(ProtocolError::new("client script", "varint overflow"));
        }
        n |= u32::from(b & 127) << shift;
        if b & 128 == 0 {
            return Ok(n);
        }
    }
    Err(ProtocolError::new("client script", "invalid varint"))
}
pub(super) fn decode(bytes: &[u8]) -> Result<ScriptRequest> {
    let mut r = Reader::new(bytes, "client script");
    let types = text(&mut r)?;
    if types.chars().count() > 128 {
        return Err(ProtocolError::new("client script", "argument limit"));
    }
    let mut arguments = Vec::new();
    for kind in types.chars().rev() {
        arguments.push(match kind {
            's' => ScriptValue::Text(text(&mut r)?),
            'Ï' => ScriptValue::Long(i64::from_be_bytes(
                r.take(8)?
                    .try_into()
                    .map_err(|_| ProtocolError::new("client script", "long"))?,
            )),
            'W' | 'X' => {
                let count = usize::try_from(varint(&mut r)?)
                    .map_err(|_| ProtocolError::new("client script", "array size"))?;
                if count > 4096 {
                    return Err(ProtocolError::new("client script", "array limit"));
                }
                if kind == 'W' {
                    let mut a = Vec::with_capacity(count);
                    for _ in 0..count {
                        let n = varint(&mut r)?;
                        a.push((n >> 1).cast_signed() ^ -i32::from(n & 1 != 0));
                    }
                    ScriptValue::Integers(a)
                } else {
                    let mut a = Vec::with_capacity(count);
                    for _ in 0..count {
                        a.push(text(&mut r)?);
                    }
                    ScriptValue::Strings(a)
                }
            }
            _ => ScriptValue::Int(
                u32::from_be_bytes(
                    r.take(4)?
                        .try_into()
                        .map_err(|_| ProtocolError::new("client script", "integer"))?,
                )
                .cast_signed(),
            ),
        });
    }
    arguments.reverse();
    let id = ScriptId::new(u32::from_be_bytes(
        r.take(4)?
            .try_into()
            .map_err(|_| ProtocolError::new("client script", "integer"))?,
    ));
    if r.offset != bytes.len() {
        return Err(ProtocolError::new("client script", "trailing bytes"));
    }
    Ok(ScriptRequest { id, arguments })
}

pub(super) fn status(op: u16, bytes: &[u8]) -> Result<rustclient_model::PlayerStatusUpdate> {
    use rustclient_model::{PlayerStatusUpdate, SkillId, SkillValues};
    if op == 66 {
        let mut r = Reader::new(bytes, "reboot timer");
        let message = text(&mut r)?;
        let a = r.u8()?;
        let b = r.u8()?;
        if r.offset != bytes.len() {
            return Err(ProtocolError::new("reboot timer", "trailing bytes"));
        }
        return Ok(PlayerStatusUpdate::Reboot {
            cycles: u32::from(u16::from_be_bytes([a, b.wrapping_sub(128)])),
            message: if message.is_empty() {
                None
            } else {
                Some(if message == "\u{18}" {
                    String::new()
                } else {
                    message
                })
            },
        });
    }
    match (op, bytes) {
        (19, [a, b]) => Ok(PlayerStatusUpdate::RunWeight(i16::from_be_bytes([*a, *b]))),
        (120, [a, b]) => {
            let n = u16::from_be_bytes([*a, *b]);
            if n > 10000 {
                return Err(ProtocolError::new("player status", "run energy range"));
            }
            Ok(PlayerStatusUpdate::RunEnergy(n))
        }
        (63, [skill, a, b, c, d, invisible, current]) => Ok(PlayerStatusUpdate::Skill {
            id: SkillId::new(u32::from(128_u8.wrapping_sub(*skill))),
            values: SkillValues {
                experience: u32::from_le_bytes([*a, *b, *c, *d]),
                current: u16::from(128_u8.wrapping_sub(*current)),
                invisible: u16::from(*invisible),
            },
        }),
        _ => Err(ProtocolError::new(
            "player status",
            "invalid payload length",
        )),
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn script_call_family_reverses_typed_arguments_and_rejects_truncation_and_large_arrays() {
        let bytes = [
            b'i', b's', b'W', 0, 2, 2, 5, b'h', b'i', 0, 0, 0, 0, 42, 0, 0, 0, 9,
        ];
        let result = decode(&bytes).unwrap();
        assert_eq!(
            result,
            ScriptRequest {
                id: ScriptId::new(9),
                arguments: vec![
                    ScriptValue::Int(42),
                    ScriptValue::Text("hi".into()),
                    ScriptValue::Integers(vec![1, -3])
                ]
            }
        );
        for end in 0..bytes.len() {
            assert!(decode(&bytes[..end]).is_err());
        }
        let mut long = vec![0xcf, 0];
        long.extend((-7_i64).to_be_bytes());
        long.extend(1_u32.to_be_bytes());
        assert_eq!(decode(&long).unwrap().arguments, [ScriptValue::Long(-7)]);
        assert!(decode(&[b'W', 0, 0xff, 0xff, 0xff, 0xff, 0x7f]).is_err());
        assert_eq!(
            status(120, &[0x27, 0x10]).unwrap(),
            rustclient_model::PlayerStatusUpdate::RunEnergy(10000)
        );
        assert!(status(120, &[0x27, 0x11]).is_err());
        assert!(status(63, &[0; 6]).is_err());
    }
}
