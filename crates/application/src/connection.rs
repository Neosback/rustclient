//! Shared interpretation of authenticated profile updates as semantic operations.
use rustclient_game_state::{
    AuthenticatedPlayer, GroundItemOperation, InitialPlacement, SemanticOperation,
};
use rustclient_model::TickSpan;
use rustclient_protocol::{LoginSession, Result, ServerProfile, ServerUpdate, SessionCodec};
/// Narrow connection decoder, storing only wire phase/ciphers rather than game truth.
#[expect(
    clippy::large_enum_variant,
    reason = "frequent semantic packets stay inline instead of allocating one box per packet"
)]
pub enum ConnectionInput {
    Operation(SemanticOperation),
    Script(rustclient_model::ScriptRequest),
    Control(rustclient_protocol::SessionControl),
}
pub struct ConnectionDecoder {
    codec: SessionCodec,
}
impl ConnectionDecoder {
    /// Transfer cipher ownership and return the authoritative authentication operation.
    #[must_use]
    pub fn openrune(login: LoginSession) -> (Self, SemanticOperation) {
        let operation = SemanticOperation::AuthenticateSession {
            revision: ServerProfile::OPENRUNE_240_2.revision(),
            player: AuthenticatedPlayer {
                profile: ServerProfile::OPENRUNE_240_2.id(),
                player: login.player,
                member: login.member,
                staff_level: login.staff_level,
            },
        };
        (
            Self {
                codec: SessionCodec::new(login.incoming, login.outgoing, login.player),
            },
            operation,
        )
    }
    /// Construct a TSPS session without manufacturing `OpenRune` cipher state.
    #[must_use]
    pub fn tsps(login: rustclient_protocol::tsps_241::LoginSession) -> (Self, SemanticOperation) {
        let operation = SemanticOperation::AuthenticateSession {
            revision: ServerProfile::TSPS_241.revision(),
            player: AuthenticatedPlayer {
                profile: ServerProfile::TSPS_241.id(),
                player: login.player,
                member: login.member,
                staff_level: login.staff_level,
            },
        };
        (
            Self {
                codec: SessionCodec::tsps(login),
            },
            operation,
        )
    }
    /// Supply the selected cache's chat codec before authenticated packet consumption.
    pub fn set_chat_codec(&mut self, codec: rustclient_protocol::Huffman) {
        self.codec.set_chat_codec(codec);
    }
    /// Supply immutable varbit definitions without giving the wire adapter live variable values.
    pub fn set_varbit_lookup(&mut self, lookup: rustclient_protocol::VarbitLookup) {
        self.codec.set_varbit_lookup(lookup);
    }
    /// Supply the cache-backed NPC recolour and retexture counts that size customisation blocks.
    pub fn set_npc_shapes(&mut self, shapes: rustclient_protocol::NpcShapeLookup) {
        self.codec.set_npc_shapes(shapes);
    }
    /// Profile packet count for diagnostics; not semantic time.
    #[must_use]
    pub fn framed_packets(&self) -> u64 {
        self.codec.framed_packets()
    }
    /// Per-opcode counts of server packets consumed without decoding (diagnostic).
    #[must_use]
    pub fn unhandled_opcodes(&self) -> &std::collections::BTreeMap<u16, u64> {
        self.codec.unhandled_opcodes()
    }
    /// Opcodes first seen since the last call, so each can be logged once.
    pub fn take_new_unhandled(&mut self) -> Vec<u16> {
        self.codec.take_new_unhandled()
    }
    #[must_use]
    pub fn awaiting_initial_rebuild(&self) -> bool {
        self.codec.awaiting_initial_rebuild()
    }
    /// # Errors
    /// Rejects invalid fields before cipher advancement.
    pub fn encode(&mut self, intent: rustclient_protocol::ClientIntent) -> Result<Vec<u8>> {
        self.codec.encode(intent)
    }
    /// Decode semantic-only streams. Script-bearing callers must use `receive_inputs`.
    /// # Errors
    /// Rejects a script request rather than silently discarding it.
    pub fn receive(
        &mut self,
        bytes: &[u8],
        mut emit: impl FnMut(SemanticOperation),
    ) -> Result<bool> {
        let mut script = false;
        let connected = self.receive_inputs(bytes, |input| match input {
            ConnectionInput::Operation(op) => emit(op),
            ConnectionInput::Script(_) | ConnectionInput::Control(_) => script = true,
        })?;
        if script {
            return Err(rustclient_protocol::ProtocolError {
                stage: "connection consumer",
                detail: "script or session-control consumer required".into(),
                transport: None,
                rejection: None,
            });
        }
        Ok(connected)
    }
    /// Decode updates in order; false means the server ended the session.
    /// # Errors
    /// Forwards bounded profile decode errors; no authoritative mutation occurs here.
    pub fn receive_inputs(
        &mut self,
        bytes: &[u8],
        mut deliver: impl FnMut(ConnectionInput),
    ) -> Result<bool> {
        self.receive_batches(bytes, |batch| {
            for input in batch.payload {
                deliver(input);
            }
        })
    }
    /// Preserve packet boundaries and atomic groups for application cycle ingress.
    /// # Errors
    /// Forwards bounded protocol failures without mutating authoritative state.
    pub fn receive_batches(
        &mut self,
        bytes: &[u8],
        mut deliver_batch: impl FnMut(crate::cycle::InboundBatch<Vec<ConnectionInput>>),
    ) -> Result<bool> {
        let mut connected = true;
        self.codec.receive_batches(bytes, |batch| {
            let mut inputs = Vec::new();
            for update in batch.updates {
                translate(update, |input| inputs.push(input), &mut connected);
            }
            deliver_batch(crate::cycle::InboundBatch {
                packets: batch.packets,
                wire_bytes: batch.wire_bytes,
                payload: inputs,
            });
        })?;
        Ok(connected)
    }
}
/// `UPDATE_ZONE_FULL_FOLLOWS`: reset the zone's location changes and empty its ground item
/// deques, as the stock handler does.
fn zone_reset(
    offset: rustclient_model::TileOffset,
    plane: rustclient_model::Plane,
) -> [SemanticOperation; 2] {
    [
        SemanticOperation::Scenery(rustclient_game_state::SceneryOperation::ResetLocationArea {
            offset,
            plane,
            size: [8, 8],
        }),
        SemanticOperation::GroundItems(GroundItemOperation::ResetArea {
            offset,
            plane,
            size: [8, 8],
        }),
    ]
}
fn translate(update: ServerUpdate, mut deliver: impl FnMut(ConnectionInput), connected: &mut bool) {
    match update {
        ServerUpdate::ChatFilters(values) => deliver(ConnectionInput::Operation(
            SemanticOperation::ChatFilters(values),
        )),
        ServerUpdate::GameMessage(message) => deliver(ConnectionInput::Operation(
            SemanticOperation::GameMessage(message),
        )),
        ServerUpdate::InitialPlacement(p) => deliver(ConnectionInput::Operation(
            SemanticOperation::EstablishInitialPlacement(InitialPlacement {
                position: p.position,
                map_anchor: p.map_anchor,
                area: p.area,
            }),
        )),
        ServerUpdate::RebuildMap { map_anchor } => {
            deliver(ConnectionInput::Operation(SemanticOperation::RebuildMap(
                map_anchor,
            )));
        }
        ServerUpdate::SetMapAnchor(anchor) => deliver(ConnectionInput::Operation(
            SemanticOperation::SetMapAnchor(anchor),
        )),
        ServerUpdate::Interface(update) => deliver(ConnectionInput::Operation(
            SemanticOperation::Interface(update),
        )),
        ServerUpdate::Inventory(update) => deliver(ConnectionInput::Operation(
            SemanticOperation::Inventory(update),
        )),
        ServerUpdate::PlayerInfo(info) => deliver(ConnectionInput::Operation(
            SemanticOperation::UpdatePlayers(info),
        )),
        ServerUpdate::NpcInfo(info) => deliver(ConnectionInput::Operation(
            SemanticOperation::UpdateNpcs(info),
        )),
        ServerUpdate::MapFlag(destination) => deliver(ConnectionInput::Operation(
            SemanticOperation::SetDestination(destination),
        )),
        ServerUpdate::ResetActorAnimations => deliver(ConnectionInput::Operation(
            SemanticOperation::ResetActorAnimations,
        )),
        ServerUpdate::LocationZoneReset(origin) => {
            for operation in zone_reset(origin.offset, origin.plane) {
                deliver(ConnectionInput::Operation(operation));
            }
        }
        ServerUpdate::GroundItem(update) => deliver(ConnectionInput::Operation(
            SemanticOperation::GroundItems(GroundItemOperation::Update(update)),
        )),
        ServerUpdate::Location(location) => deliver(ConnectionInput::Operation(
            SemanticOperation::Scenery(rustclient_game_state::SceneryOperation::UpdateLocation {
                offset: location.offset,
                plane: location.plane,
                placement: location.placement,
                object: location.object,
                options: location.options,
            }),
        )),
        ServerUpdate::LocationAnim(anim) => {
            deliver(ConnectionInput::Operation(SemanticOperation::Scenery(
                rustclient_game_state::SceneryOperation::TriggerLocationAnimation {
                    offset: anim.offset,
                    plane: anim.plane,
                    placement: anim.placement,
                    sequence: anim.sequence,
                },
            )));
        }
        ServerUpdate::Variable(update) => deliver(ConnectionInput::Operation(
            SemanticOperation::Scenery(match update {
                rustclient_protocol::VariableUpdate::SetBits { range, value } => {
                    rustclient_game_state::SceneryOperation::SetServerVariableBits { range, value }
                }
                rustclient_protocol::VariableUpdate::Set { id, value } => {
                    rustclient_game_state::SceneryOperation::SetVariable { id, value }
                }
                rustclient_protocol::VariableUpdate::Reset => {
                    rustclient_game_state::SceneryOperation::ResetVariables
                }
                rustclient_protocol::VariableUpdate::Sync => {
                    rustclient_game_state::SceneryOperation::SynchronizeVariables
                }
            }),
        )),
        ServerUpdate::Tick => deliver(ConnectionInput::Operation(SemanticOperation::AdvanceTick(
            TickSpan::ONE,
        ))),
        ServerUpdate::LoggedOut => *connected = false,
        ServerUpdate::PlayerStatus(update) => deliver(ConnectionInput::Operation(
            SemanticOperation::PlayerStatus(update),
        )),
        ServerUpdate::Script(request) => deliver(ConnectionInput::Script(request)),
        ServerUpdate::Control(control) => {
            if control.terminates_session() {
                *connected = false;
            }
            deliver(ConnectionInput::Control(control));
        }
        ServerUpdate::Deferred => {}
    }
}
