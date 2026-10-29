//! Non-secret session-control payloads for the selected profile.
use crate::{ProtocolError, Result, wire::Reader};
#[derive(Debug, Clone, PartialEq, Eq)]
pub enum SessionControl {
    Logout,
    LogoutWithReason(u8),
    Transfer {
        host: String,
        world: u16,
        properties: u32,
    },
    Ping {
        first: i32,
        second: i32,
    },
}
impl SessionControl {
    #[must_use]
    pub const fn terminates_session(&self) -> bool {
        !matches!(self, Self::Ping { .. })
    }
}
pub(super) fn decode(opcode: u16, payload: &[u8]) -> Result<SessionControl> {
    let mut r = Reader::new(payload, "session control");
    let control = match opcode {
        37 => SessionControl::Logout,
        56 => SessionControl::LogoutWithReason(r.u8()?),
        26 => {
            let mut host = String::new();
            loop {
                let byte = r.u8()?;
                if byte == 0 {
                    break;
                }
                if host.len() >= 255 || !byte.is_ascii() || byte.is_ascii_control() {
                    return Err(ProtocolError::new("world transfer", "invalid host"));
                }
                host.push(char::from(byte));
            }
            if host.is_empty() {
                return Err(ProtocolError::new("world transfer", "empty host"));
            }
            let world = r.u16()?;
            let bytes = r.take(4)?;
            let properties = u32::from_be_bytes([bytes[0], bytes[1], bytes[2], bytes[3]]);
            SessionControl::Transfer {
                host,
                world,
                properties,
            }
        }
        115 => {
            let first = r.take(4)?;
            let first = i32::from_be_bytes([first[0], first[1], first[2], first[3]]);
            let second = r.take(4)?;
            let second = i32::from_be_bytes([second[0], second[1], second[2], second[3]]);
            SessionControl::Ping { first, second }
        }
        _ => return Err(ProtocolError::new("session control", "unsupported control")),
    };
    if r.offset != payload.len() {
        return Err(ProtocolError::new("session control", "trailing bytes"));
    }
    Ok(control)
}
/// Selected `RSProt` `SendPingReplyDecoder`: g1Alt1, g1Alt2, g4Alt1, g4Alt3.
pub(super) fn ping_reply(first: i32, second: i32, fps: u32) -> Vec<u8> {
    let mut out = Vec::with_capacity(10);
    out.extend_from_slice(&[fps.to_le_bytes()[0].wrapping_add(128), 0]); // Rust has no JVM GC; measured GC time is zero.
    out.extend_from_slice(&first.to_le_bytes());
    let [a, b, c, d] = second.to_be_bytes();
    out.extend_from_slice(&[b, a, d, c]);
    out
}
