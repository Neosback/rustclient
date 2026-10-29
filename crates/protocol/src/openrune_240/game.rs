use crate::{Isaac, ProtocolError, Result, openrune_240::lengths::LENGTHS};
use rustclient_model::{Plane, WorldAreaId, WorldTile};
use std::collections::VecDeque;

/// A complete bounded desktop game packet. Payload decoding remains profile-owned.
pub struct GamePacket {
    pub opcode: u16,
    pub payload: Vec<u8>,
}
impl GamePacket {
    fn wire_size(&self) -> usize {
        let prefix = match LENGTHS[usize::from(self.opcode)] {
            -1 => 1,
            -2 => 2,
            _ => 0,
        };
        usize::from(self.opcode >= 128) + 1 + prefix + self.payload.len()
    }
}
/// Ordered decoded output for one ordinary packet or one complete packet group.
#[derive(Debug)]
pub struct PacketBatch {
    /// Includes the group marker, matching the stock packet-loop counter.
    pub packets: usize,
    pub wire_bytes: usize,
    pub updates: Vec<ServerUpdate>,
}
struct PacketGroup {
    remaining: usize,
    wire_bytes: usize,
    packets: Vec<GamePacket>,
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
/// Ordinary rebuild header (rsprot `RebuildNormalV2Encoder`). Plane is supplied
/// by committed entity state above the wire boundary.
fn decode_map_anchor(payload: &[u8]) -> Result<rustclient_model::MapAnchor> {
    let [ylo, yhi, alo, ahi, xlo, xhi] = payload else {
        return Err(ProtocolError::new(
            "map rebuild",
            "expected six-byte normal rebuild",
        ));
    };
    let short = |lo: u8, hi: u8| u16::from_le_bytes([lo.wrapping_sub(128), hi]);
    if short(*alo, *ahi) != u16::MAX {
        return Err(ProtocolError::new(
            "map rebuild",
            "world-area rebuild is not supported",
        ));
    }
    let (x, y) = (short(*xlo, *xhi), short(*ylo, *yhi));
    if x > 2047 || y > 2047 {
        return Err(ProtocolError::new(
            "map rebuild",
            "map anchor outside profile range",
        ));
    }
    Ok(rustclient_model::MapAnchor {
        x: i32::from(x) * 8,
        y: i32::from(y) * 8,
    })
}
/// A message entered in the chatbox: a public message or a `::` command.
#[derive(Debug, Clone, PartialEq, Eq)]
pub enum ChatIntent {
    /// Public message with parsed style and Huffman text from the selected cache codec.
    Public {
        chat_type: u8,
        message: super::outgoing_chat::PublicChatMessage,
    },
    /// Developer/console command without its leading `::`.
    Command(String),
}

/// Narrow outbound semantic intents supported by this profile.
#[derive(Debug, Clone)]
pub enum ClientIntent {
    WindowStatus {
        mode: rustclient_model::WindowMode,
        width: u16,
        height: u16,
    },
    KeepAlive,
    PingReply {
        first: i32,
        second: i32,
        fps: u32,
    },
    /// User-selected destination. Confirmed player position does not change.
    Walk {
        destination: WorldTile,
        invert_run: bool,
    },
    MinimapWalk {
        destination: WorldTile,
        invert_run: bool,
        click: super::MinimapClick,
    },
    /// The selected map has finished loading.
    MapBuildComplete,
    WidgetAction(rustclient_model::WidgetAction),
    CloseModal,
    /// Chat typed in the main chatbox. `command` carries text after `::`.
    Chat(ChatIntent),
    NpcAction {
        index: rustclient_model::NpcIndex,
        slot: rustclient_model::ActionSlot,
        control: bool,
    },
    LocationAction {
        tile: WorldTile,
        object: rustclient_model::ObjectDefinitionId,
        slot: rustclient_model::ActionSlot,
        control: bool,
    },
}
impl ClientIntent {
    /// Encode only after validating all fields, before advancing the cipher.
    /// # Errors
    /// Rejects destinations outside this profile's coordinate range.
    pub fn encode(self, cipher: &mut Isaac) -> Result<Vec<u8>> {
        let (opcode, payload) = match self {
            Self::KeepAlive => (99, vec![]),
            Self::PingReply { first, second, fps } => {
                (32, super::control::ping_reply(first, second, fps))
            }
            Self::CloseModal => (98, vec![]),
            Self::Chat(chat) => {
                // MESSAGE_PUBLIC and CLIENT_CHEAT are both VAR_BYTE.
                let (opcode, body) = match chat {
                    ChatIntent::Public { chat_type, message } => {
                        let mut body = vec![chat_type];
                        message.append(chat_type, &mut body);
                        (56, body)
                    }
                    ChatIntent::Command(text) => {
                        let mut body: Vec<u8> = text
                            .chars()
                            .map(|ch| crate::wire::cp1252(ch).unwrap_or(b'?'))
                            .collect();
                        body.push(0);
                        (79, body)
                    }
                };
                let length = u8::try_from(body.len()).map_err(|_| {
                    ProtocolError::new("chat encode", "message exceeds one length byte")
                })?;
                let mut payload = vec![length];
                payload.extend(body);
                (opcode, payload)
            }
            Self::WidgetAction(action) => super::actions::widget(action)?,
            Self::NpcAction {
                index,
                slot,
                control,
            } => super::actions::npc(index, slot, control)?,
            Self::LocationAction {
                tile,
                object,
                slot,
                control,
            } => super::actions::location(tile, object, slot, control)?,
            Self::MinimapWalk {
                destination,
                invert_run,
                click,
            } => super::minimap::movement(destination, invert_run, click)?,
            Self::MapBuildComplete => (27, vec![]),
            Self::Walk {
                destination,
                invert_run,
            } => {
                let coordinate = |value| {
                    u16::try_from(value).map_err(|_| {
                        ProtocolError::new(
                            "movement encode",
                            "destination outside profile coordinate range",
                        )
                    })
                };
                let x = coordinate(destination.x)?.to_le_bytes();
                let y = coordinate(destination.y)?.to_le_bytes();
                // rsprot 240: g2Alt3(z), g1Alt2(keys), g2Alt3(x); VAR_BYTE.
                (
                    102,
                    vec![
                        5,
                        y[0].wrapping_add(128),
                        y[1],
                        u8::from(invert_run).wrapping_neg(),
                        x[0].wrapping_add(128),
                        x[1],
                    ],
                )
            }
            Self::WindowStatus {
                mode,
                width,
                height,
            } => {
                if width == 0 || height == 0 || width > 7680 || height > 2160 {
                    return Err(ProtocolError::new(
                        "window status",
                        "canvas dimensions outside profile bounds",
                    ));
                }
                let mut p = vec![match mode {
                    rustclient_model::WindowMode::Fixed => 1,
                    rustclient_model::WindowMode::Resizable => 2,
                }];
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
        Ok(bytes)
    }
}

use super::{
    loc_anim::{LocAnimUpdate, decode_loc_anim_specific},
    npc_info::{NpcInfoDecoder, NpcInfoUpdate},
    player_info::{PlayerInfoDecoder, PlayerInfoUpdate},
};

/// Profile-decoded updates, without exposing wire IDs to application semantics.
#[derive(Debug, Clone, PartialEq, Eq)]
pub enum ServerUpdate {
    GameMessage(rustclient_model::GameMessage),
    ChatFilters([Option<u8>; 3]),
    Interface(rustclient_model::InterfaceUpdate),
    Script(rustclient_model::ScriptRequest),
    PlayerStatus(rustclient_model::PlayerStatusUpdate),
    Inventory(rustclient_model::InventoryUpdate),
    Variable(VariableUpdate),
    InitialPlacement(Bootstrap),
    /// New ordinary map anchor; the profile carries no player position here.
    RebuildMap {
        map_anchor: rustclient_model::MapAnchor,
    },
    PlayerInfo(PlayerInfoUpdate),
    NpcInfo(NpcInfoUpdate),
    MapFlag(Option<rustclient_model::MapDestination>),
    ResetActorAnimations,
    LocationAnim(LocAnimUpdate),
    LocationZoneReset(super::zone::ZoneOrigin),
    Tick,
    LoggedOut,
    Control(super::control::SessionControl),
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
    awaiting_rebuild: bool,
    framed_packets: u64,
    players: PlayerInfoDecoder,
    chat_codec: Option<super::huffman::Huffman>,
    npcs: NpcInfoDecoder,
    zones: super::zone::ZoneDecoder,
    group: Option<PacketGroup>,
}
impl SessionCodec {
    #[must_use]
    pub fn new(
        incoming: Isaac,
        outgoing: Isaac,
        local_player: rustclient_model::PlayerIndex,
    ) -> Self {
        Self {
            framer: GameFramer::new(incoming),
            outgoing,
            awaiting_rebuild: true,
            framed_packets: 0,
            players: PlayerInfoDecoder::new(local_player),
            chat_codec: None,
            npcs: NpcInfoDecoder::default(),
            zones: super::zone::ZoneDecoder::default(),
            group: None,
        }
    }
    /// Supply the selected cache's immutable Huffman codec before receiving player chat.
    pub fn set_chat_codec(&mut self, codec: super::huffman::Huffman) {
        self.chat_codec = Some(codec);
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
    /// # Errors
    /// Rejects invalid outbound fields without advancing the stream cipher.
    pub fn encode(&mut self, intent: ClientIntent) -> Result<Vec<u8>> {
        intent.encode(&mut self.outgoing)
    }
    /// Deliver complete, profile-decoded updates in stream order.
    /// # Errors
    /// Rejects malformed packets and incomplete or invalid group boundaries.
    pub fn receive(&mut self, bytes: &[u8], mut emit: impl FnMut(ServerUpdate)) -> Result<()> {
        self.receive_batches(bytes, |batch| {
            for update in batch.updates {
                emit(update);
            }
        })
    }

    /// Deliver an ordinary packet or a complete atomic packet group.
    /// No group member is decoded or published until every declared wire byte is available.
    /// # Errors
    /// Rejects invalid framing, nested groups and groups ending inside a packet.
    pub fn receive_batches(
        &mut self,
        bytes: &[u8],
        mut emit: impl FnMut(PacketBatch),
    ) -> Result<()> {
        self.framer.feed(bytes)?;
        while let Some(packet) = self.framer.next_packet()? {
            self.framed_packets = self.framed_packets.saturating_add(1);
            let size = packet.wire_size();
            if packet.opcode == 57 {
                if self.group.is_some() {
                    return Err(ProtocolError::new("packet group", "nested group"));
                }
                let [hi, lo] = packet.payload.as_slice() else {
                    return Err(ProtocolError::new("packet group", "invalid header"));
                };
                let length = i16::from_be_bytes([*hi, *lo]);
                let remaining = usize::try_from(length)
                    .map_err(|_| ProtocolError::new("packet group", "negative byte count"))?;
                if remaining == 0 {
                    emit(PacketBatch {
                        packets: 1,
                        wire_bytes: size,
                        updates: Vec::new(),
                    });
                } else {
                    self.group = Some(PacketGroup {
                        remaining,
                        wire_bytes: size,
                        packets: Vec::new(),
                    });
                }
                continue;
            }
            let (packets, wire_bytes, count) = if let Some(group) = &mut self.group {
                group.remaining = group
                    .remaining
                    .checked_sub(size)
                    .ok_or_else(|| ProtocolError::new("packet group", "boundary inside packet"))?;
                group.wire_bytes += size;
                group.packets.push(packet);
                if group.remaining != 0 {
                    continue;
                }
                let group = self
                    .group
                    .take()
                    .ok_or_else(|| ProtocolError::new("packet group", "missing group"))?;
                let count = group.packets.len() + 1;
                (group.packets, group.wire_bytes, count)
            } else {
                (vec![packet], size, 1)
            };
            let mut updates = Vec::new();
            let mut logged_out = false;
            for packet in packets {
                logged_out = self.decode_packet(&packet, |update| updates.push(update))?;
                if logged_out {
                    break;
                }
            }
            emit(PacketBatch {
                packets: count,
                wire_bytes,
                updates,
            });
            if logged_out {
                break;
            }
        }
        Ok(())
    }

    fn decode_packet(
        &mut self,
        packet: &GamePacket,
        mut emit: impl FnMut(ServerUpdate),
    ) -> Result<bool> {
        let update = match packet.opcode {
            18 | 76 => {
                let values = match (packet.opcode, packet.payload.as_slice()) {
                    (18, [trade, public]) if *trade <= 2 && *public <= 4 => {
                        [Some(*public), None, Some(*trade)]
                    }
                    (76, [private]) if *private <= 2 => [None, Some(*private), None],
                    _ => {
                        return Err(ProtocolError::new(
                            "chat settings",
                            "invalid filter payload",
                        ));
                    }
                };
                ServerUpdate::ChatFilters(values)
            }
            73 => ServerUpdate::GameMessage(super::chat::decode(&packet.payload)?),
            19 | 63 | 66 | 120 => {
                ServerUpdate::PlayerStatus(super::scripts::status(packet.opcode, &packet.payload)?)
            }
            10 => ServerUpdate::Script(super::scripts::decode(&packet.payload)?),
            39 if self.awaiting_rebuild => {
                let bootstrap = decode_bootstrap(packet)?;
                let tracking = self.players.bootstrap(&packet.payload)?;
                self.awaiting_rebuild = false;
                emit(ServerUpdate::InitialPlacement(bootstrap));
                ServerUpdate::PlayerInfo(tracking)
            }
            39 => {
                let map_anchor = decode_map_anchor(&packet.payload)?;
                self.npcs.clear_origin();
                self.zones.clear();
                ServerUpdate::RebuildMap { map_anchor }
            }
            12 => {
                return Err(ProtocolError::new(
                    "map rebuild",
                    "instanced rebuild is not supported",
                ));
            }
            91 => {
                let update = self
                    .players
                    .decode_with_chat(&packet.payload, self.chat_codec.as_ref())?;
                ServerUpdate::PlayerInfo(update)
            }
            58 => ServerUpdate::ResetActorAnimations,
            111 => {
                self.npcs.set_origin(&packet.payload)?;
                ServerUpdate::Deferred
            }
            150 => {
                let update = self.npcs.decode(&packet.payload, false)?;
                ServerUpdate::NpcInfo(update)
            }
            141 => {
                let update = self.npcs.decode(&packet.payload, true)?;
                ServerUpdate::NpcInfo(update)
            }
            118 | 126 => {
                let origin = self.zones.header(packet.opcode, &packet.payload)?;
                if packet.opcode == 126 {
                    ServerUpdate::LocationZoneReset(origin)
                } else {
                    ServerUpdate::Deferred
                }
            }
            49 => {
                for animation in self.zones.enclosed(&packet.payload)? {
                    emit(ServerUpdate::LocationAnim(animation));
                }
                ServerUpdate::Deferred
            }
            123 => self
                .zones
                .animation(&packet.payload)?
                .map_or(ServerUpdate::Deferred, ServerUpdate::LocationAnim),
            84 => {
                let update = decode_loc_anim_specific(&packet.payload)?;
                update.map_or(ServerUpdate::Deferred, ServerUpdate::LocationAnim)
            }
            11 | 30 | 35 | 45 | 51 | 54 | 78 | 106 | 116 => {
                ServerUpdate::Interface(super::interfaces::decode(packet.opcode, &packet.payload)?)
            }
            14 | 23 | 74 => {
                ServerUpdate::Inventory(super::inventory::decode(packet.opcode, &packet.payload)?)
            }
            68 | 94 | 100 | 127 => ServerUpdate::Variable(decode_variable(packet)?),
            136 => ServerUpdate::MapFlag(super::minimap::flag(&packet.payload)?),
            92 => ServerUpdate::Tick,
            26 | 37 | 56 | 115 => {
                ServerUpdate::Control(super::control::decode(packet.opcode, &packet.payload)?)
            }
            _ => ServerUpdate::Deferred,
        };
        let is_logout = matches!(&update, ServerUpdate::LoggedOut)
            || matches!(&update, ServerUpdate::Control(control) if control.terminates_session());
        emit(update);
        Ok(is_logout)
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

#[cfg(test)]
mod action_tests {
    use super::*;
    #[test]
    fn movement_family_has_reference_transforms_lengths_and_atomic_cipher_validation() {
        let mut cipher = Isaac::new([1, 2, 3, 4]);
        let mut expected_cipher = Isaac::new([1, 2, 3, 4]);
        for destination in [
            WorldTile::new(-1, 3200, Plane::MIN),
            WorldTile::new(3200, 65536, Plane::MIN),
        ] {
            assert!(
                ClientIntent::Walk {
                    destination,
                    invert_run: false
                }
                .encode(&mut cipher)
                .is_err()
            );
        }
        let bytes = ClientIntent::Walk {
            destination: WorldTile::new(3221, 3218, Plane::MIN),
            invert_run: true,
        }
        .encode(&mut cipher)
        .unwrap();
        assert_eq!(
            bytes[0],
            102_u8.wrapping_add(expected_cipher.next_word().to_le_bytes()[0])
        );
        assert_eq!(&bytes[1..], &[5, 0x12, 0x0c, 0xff, 0x15, 0x0c]);
        assert_eq!(
            ClientIntent::MapBuildComplete.encode(&mut cipher).unwrap(),
            vec![27_u8.wrapping_add(expected_cipher.next_word().to_le_bytes()[0])]
        );
        assert_eq!(
            decode_map_anchor(&[0x13, 1, 0x7f, 0xff, 0x14, 1]).unwrap(),
            rustclient_model::MapAnchor { x: 3232, y: 3224 }
        );
        for payload in [&[][..], &[0; 5], &[0; 6], &[0x13, 1, 0x7f, 0xff, 0x14, 8]] {
            assert!(decode_map_anchor(payload).is_err());
        }
    }
}
