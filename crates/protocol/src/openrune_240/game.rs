use crate::{Isaac, ProtocolError, Result, openrune_240::lengths::LENGTHS};
use rustclient_model::{Plane, PlayerIndex, WorldAreaId, WorldTile};
use std::collections::VecDeque;

/// A complete bounded desktop game packet. Payload decoding remains profile-owned.
pub struct GamePacket {
    pub opcode: u16,
    pub payload: Vec<u8>,
}
/// Incremental frame reader: cipher state advances only when a byte is available.
pub struct GameFramer {
    cipher: Isaac,
    pending: VecDeque<u8>,
    first: Option<u8>,
    opcode: Option<u16>,
    length: Option<usize>,
}
impl GameFramer {
    #[must_use]
    pub fn new(cipher: Isaac) -> Self {
        Self {
            cipher,
            pending: VecDeque::new(),
            first: None,
            opcode: None,
            length: None,
        }
    }
    /// # Errors
    /// Rejects excessive unread bytes before allocating.
    pub fn feed(&mut self, bytes: &[u8]) -> Result<()> {
        if self.pending.len() + bytes.len() > 131_072 {
            return Err(ProtocolError::new("game framing", "receive buffer limit"));
        }
        self.pending.extend(bytes);
        Ok(())
    }
    fn decrypted(&mut self) -> Option<u8> {
        self.pending
            .pop_front()
            .map(|b| b.wrapping_sub(self.cipher.next_word().to_le_bytes()[0]))
    }
    /// # Errors
    /// Rejects unknown opcodes and oversized frames; incomplete frames return None.
    pub fn next_packet(&mut self) -> Result<Option<GamePacket>> {
        if self.opcode.is_none() {
            let first = if let Some(v) = self.first {
                v
            } else if let Some(v) = self.decrypted() {
                self.first = Some(v);
                v
            } else {
                return Ok(None);
            };
            let opcode = if first < 128 {
                u16::from(first)
            } else if let Some(second) = self.decrypted() {
                (u16::from(first - 128) << 8) | u16::from(second)
            } else {
                return Ok(None);
            };
            self.first = None;
            self.opcode = Some(opcode);
        }
        let opcode = self
            .opcode
            .ok_or_else(|| ProtocolError::new("game framing", "missing opcode"))?;
        let size = *LENGTHS
            .get(usize::from(opcode))
            .ok_or_else(|| ProtocolError::new("game framing", "unknown opcode"))?;
        if self.length.is_none() {
            let length = match size {
                -1 => {
                    let Some(byte) = self.pending.pop_front() else {
                        return Ok(None);
                    };
                    usize::from(byte)
                }
                -2 => {
                    if self.pending.len() < 2 {
                        return Ok(None);
                    }
                    let hi = self.pending[0];
                    let lo = self.pending[1];
                    self.pending.drain(..2);
                    usize::from(u16::from_be_bytes([hi, lo]))
                }
                n if n >= 0 => usize::try_from(n)
                    .map_err(|_| ProtocolError::new("game framing", "invalid fixed size"))?,
                _ => {
                    return Err(ProtocolError::new(
                        "game framing",
                        format!("unassigned opcode {opcode}"),
                    ));
                }
            };
            if length > 40_000 {
                return Err(ProtocolError::new("game framing", "packet length limit"));
            }
            self.length = Some(length);
        }
        let n = self
            .length
            .ok_or_else(|| ProtocolError::new("game framing", "missing length"))?;
        if self.pending.len() < n {
            return Ok(None);
        }
        let payload = self.pending.drain(..n).collect();
        self.opcode = None;
        self.length = None;
        Ok(Some(GamePacket { opcode, payload }))
    }
}
/// Source-neutral initial placement decoded from the login rebuild.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct Bootstrap {
    pub position: WorldTile,
    pub map_anchor: WorldTile,
    pub area: Option<WorldAreaId>,
}
/// Decode the very first non-instanced login rebuild, including the GPI prefix.
/// # Errors
/// Rejects missing/extra bytes, invalid packed placement and unsupported instancing.
pub fn decode_bootstrap(packet: &GamePacket) -> Result<Bootstrap> {
    const GPI: usize = (30 + 2046 * 18_usize).div_ceil(8);
    if packet.opcode != 39 || packet.payload.len() != GPI + 6 {
        return Err(ProtocolError::new(
            "initial rebuild",
            "expected non-instanced login rebuild",
        ));
    }
    let b = &packet.payload;
    let position_bits = u32::from_be_bytes([b[0], b[1], b[2], b[3]]) >> 2;
    let plane = Plane::new((position_bits >> 28).to_le_bytes()[0])
        .ok_or_else(|| ProtocolError::new("initial rebuild", "invalid plane"))?;
    let position = WorldTile::new(
        i32::try_from((position_bits >> 14) & 16383)
            .map_err(|_| ProtocolError::new("initial rebuild", "invalid coordinate"))?,
        i32::try_from(position_bits & 16383)
            .map_err(|_| ProtocolError::new("initial rebuild", "invalid coordinate"))?,
        plane,
    );
    if b[GPI - 1] & 63 != 0 {
        return Err(ProtocolError::new("initial rebuild", "nonzero GPI padding"));
    }
    let short = |offset: usize| u16::from_le_bytes([b[offset].wrapping_sub(128), b[offset + 1]]);
    let y = short(GPI);
    let area = match short(GPI + 2) {
        65535 => None,
        value @ 0..=32767 => Some(WorldAreaId::new(u32::from(value))),
        _ => {
            return Err(ProtocolError::new(
                "initial rebuild",
                "invalid map-area identity",
            ));
        }
    };
    let x = short(GPI + 4);
    if x > 2047
        || y > 2047
        || (position.x / 8 - i32::from(x)).abs() > 6
        || (position.y / 8 - i32::from(y)).abs() > 6
    {
        return Err(ProtocolError::new(
            "initial rebuild",
            "map anchor inconsistent with player placement",
        ));
    }
    Ok(Bootstrap {
        position,
        map_anchor: WorldTile::new(i32::from(x) * 8, i32::from(y) * 8, plane),
        area,
    })
}
/// Narrow outbound semantic intents supported by this profile.
#[derive(Debug, Clone, Copy)]
pub enum ClientIntent {
    WindowStatus { width: u16, height: u16 },
    KeepAlive,
}
impl ClientIntent {
    #[must_use]
    pub fn encode(self, cipher: &mut Isaac) -> Vec<u8> {
        let (opcode, payload) = match self {
            Self::KeepAlive => (99, vec![]),
            Self::WindowStatus { width, height } => {
                let mut p = vec![2];
                p.extend_from_slice(&width.to_be_bytes());
                p.extend_from_slice(&height.to_be_bytes());
                (44, p)
            }
        };
        let mut bytes = vec![u8::wrapping_add(
            opcode,
            cipher.next_word().to_le_bytes()[0],
        )];
        bytes.extend_from_slice(&payload);
        bytes
    }
}

use super::{
    loc_anim::{LocAnimUpdate, decode_loc_anim_specific},
    npc_info::{NpcInfoUpdate, decode_npc_info},
    player_info::{PlayerInfoDecoder, PlayerInfoUpdate},
};

/// Profile-decoded updates, without exposing wire IDs to application semantics.
#[derive(Debug, Clone, PartialEq, Eq)]
pub enum ServerUpdate {
    Variable(VariableUpdate),
    InitialPlacement(Bootstrap),
    PlayerInfo(PlayerInfoUpdate),
    NpcInfo(NpcInfoUpdate),
    LocationAnim(LocAnimUpdate),
    Tick,
    LoggedOut,
    Deferred,
}
/// Server variable values, defaulting to zero on reset. Profile bytes stay here.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum VariableUpdate {
    Set {
        id: rustclient_model::VarpId,
        value: i32,
    },
    Reset,
    Sync,
}
fn decode_variable(packet: &GamePacket) -> Result<VariableUpdate> {
    use rustclient_model::VarpId;
    match (packet.opcode, packet.payload.as_slice()) {
        (68, []) => Ok(VariableUpdate::Reset),
        (100, []) => Ok(VariableUpdate::Sync),
        (127, [hi, lo, value]) => Ok(VariableUpdate::Set {
            id: VarpId::new(u32::from(u16::from_be_bytes([*hi, lo.wrapping_sub(128)]))),
            value: i32::from(value.wrapping_neg().cast_signed()),
        }),
        (94, [lo, hi, a, b, c, d]) => Ok(VariableUpdate::Set {
            id: VarpId::new(u32::from(u16::from_le_bytes([lo.wrapping_sub(128), *hi]))),
            value: i32::from_le_bytes([*a, *b, *c, *d]),
        }),
        _ => Err(ProtocolError::new(
            "variable update",
            "invalid payload length",
        )),
    }
}
/// Wire-phase/cipher owner. The authoritative session remains in `GameState`.
pub struct SessionCodec {
    framer: GameFramer,
    outgoing: Isaac,
    player_info: PlayerInfoDecoder,
    awaiting_rebuild: bool,
    framed_packets: u64,
}
impl SessionCodec {
    #[must_use]
    pub fn new(incoming: Isaac, outgoing: Isaac, local_player: PlayerIndex) -> Self {
        Self {
            framer: GameFramer::new(incoming),
            outgoing,
            player_info: PlayerInfoDecoder::new(local_player),
            awaiting_rebuild: true,
            framed_packets: 0,
        }
    }
    /// Diagnostic count, never used as semantic game time.
    #[must_use]
    pub const fn framed_packets(&self) -> u64 {
        self.framed_packets
    }
    #[must_use]
    pub const fn awaiting_initial_rebuild(&self) -> bool {
        self.awaiting_rebuild
    }
    #[must_use]
    pub fn encode(&mut self, intent: ClientIntent) -> Vec<u8> {
        intent.encode(&mut self.outgoing)
    }
    /// Deliver complete, profile-decoded updates in stream order.
    /// # Errors
    /// Fails on invalid framing or unsupported map transitions.
    pub fn receive(&mut self, bytes: &[u8], mut emit: impl FnMut(ServerUpdate)) -> Result<()> {
        self.framer.feed(bytes)?;
        while let Some(packet) = self.framer.next_packet()? {
            self.framed_packets = self.framed_packets.saturating_add(1);
            let update = match packet.opcode {
                39 if self.awaiting_rebuild => {
                    let bootstrap = decode_bootstrap(&packet)?;
                    let gpi_position = self.player_info.initialize_from_rebuild(&packet.payload)?;
                    if gpi_position != bootstrap.position {
                        return Err(ProtocolError::new(
                            "initial player info",
                            "GPI local position disagrees with rebuild placement",
                        ));
                    }
                    self.awaiting_rebuild = false;
                    ServerUpdate::InitialPlacement(bootstrap)
                }
                12 | 39 => {
                    return Err(ProtocolError::new(
                        "map rebuild",
                        "subsequent or instanced rebuild is not implemented",
                    ));
                }
                91 => ServerUpdate::PlayerInfo(self.player_info.decode(&packet.payload)?),
                150 => {
                    let update = decode_npc_info(&packet.payload, false)?;
                    ServerUpdate::NpcInfo(update)
                }
                141 => {
                    let update = decode_npc_info(&packet.payload, true)?;
                    ServerUpdate::NpcInfo(update)
                }
                84 => {
                    let update = decode_loc_anim_specific(&packet.payload)?;
                    ServerUpdate::LocationAnim(update)
                }
                68 | 94 | 100 | 127 => ServerUpdate::Variable(decode_variable(&packet)?),
                92 => ServerUpdate::Tick,
                26 | 37 | 56 => ServerUpdate::LoggedOut,
                _ => ServerUpdate::Deferred,
            };
            let is_logout = update == ServerUpdate::LoggedOut;
            emit(update);
            if is_logout {
                break;
            }
        }
        Ok(())
    }
}

#[cfg(test)]
mod variable_tests {
    use super::*;
    #[test]
    fn revision_240_varp_transforms_preserve_sign_and_identifier() {
        assert_eq!(
            decode_variable(&GamePacket {
                opcode: 127,
                payload: vec![0x12, 0xb4, 1]
            })
            .unwrap(),
            VariableUpdate::Set {
                id: rustclient_model::VarpId::new(0x1234),
                value: -1
            }
        );
        assert_eq!(
            decode_variable(&GamePacket {
                opcode: 94,
                payload: vec![0xb4, 0x12, 0xfe, 0xff, 0xff, 0xff]
            })
            .unwrap(),
            VariableUpdate::Set {
                id: rustclient_model::VarpId::new(0x1234),
                value: -2
            }
        );
        assert!(
            decode_variable(&GamePacket {
                opcode: 94,
                payload: vec![0; 5]
            })
            .is_err()
        );
        assert_eq!(
            decode_variable(&GamePacket {
                opcode: 68,
                payload: vec![]
            })
            .unwrap(),
            VariableUpdate::Reset
        );
    }
}
