use super::lengths::{CLIENT_LENGTHS, SERVER_LENGTHS};
use crate::{ProtocolError, Result};
use std::collections::VecDeque;

pub(super) const MAX_BUFFER: usize = 131_072;
pub(super) struct Packet {
    pub opcode: u8,
    pub payload: Vec<u8>,
    pub wire_bytes: usize,
}
#[derive(Default)]
pub(super) struct Framer {
    pending: VecDeque<u8>,
}
impl Framer {
    pub fn feed(&mut self, bytes: &[u8]) -> Result<()> {
        if bytes.len() > MAX_BUFFER.saturating_sub(self.pending.len()) {
            return Err(ProtocolError::new("TSPS framing", "receive buffer limit"));
        }
        self.pending.extend(bytes);
        Ok(())
    }
    pub fn next(&mut self) -> Result<Option<Packet>> {
        let Some(&opcode) = self.pending.front() else {
            return Ok(None);
        };
        let (header, length) = match SERVER_LENGTHS[usize::from(opcode)] {
            -1 if self.pending.len() >= 2 => (2, usize::from(self.pending[1])),
            -2 if self.pending.len() >= 3 => (
                3,
                usize::from(u16::from_be_bytes([self.pending[1], self.pending[2]])),
            ),
            -1 | -2 => return Ok(None),
            n if n >= 0 => (
                1,
                usize::try_from(n)
                    .map_err(|_| ProtocolError::new("TSPS framing", "invalid size"))?,
            ),
            _ => {
                return Err(ProtocolError::new(
                    "TSPS framing",
                    format!("unassigned opcode {opcode}"),
                ));
            }
        };
        let total = header + length;
        if self.pending.len() < total {
            return Ok(None);
        }
        self.pending.drain(..header);
        Ok(Some(Packet {
            opcode,
            payload: self.pending.drain(..length).collect(),
            wire_bytes: total,
        }))
    }
}
pub(super) fn packet(opcode: u8, body: &[u8]) -> Result<Vec<u8>> {
    let mut out = vec![opcode];
    match CLIENT_LENGTHS[usize::from(opcode)] {
        -1 => out.push(
            u8::try_from(body.len())
                .map_err(|_| ProtocolError::new("TSPS encode", "byte length limit"))?,
        ),
        -2 => out.extend(
            u16::try_from(body.len())
                .map_err(|_| ProtocolError::new("TSPS encode", "short length limit"))?
                .to_be_bytes(),
        ),
        n if usize::try_from(n).ok() == Some(body.len()) => {}
        _ => {
            return Err(ProtocolError::new(
                "TSPS encode",
                "unassigned opcode or invalid payload length",
            ));
        }
    }
    out.extend(body);
    Ok(out)
}
