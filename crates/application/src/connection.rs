//! Shared interpretation of authenticated profile updates as semantic operations.
use rustclient_game_state::{AuthenticatedPlayer, InitialPlacement, SemanticOperation};
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
}
pub struct ConnectionDecoder {
    codec: SessionCodec,
}
impl ConnectionDecoder {
    /// Transfer cipher ownership and return the authoritative authentication operation.
    #[must_use]
    pub fn new(profile: ServerProfile, login: LoginSession) -> (Self, SemanticOperation) {
        let operation = SemanticOperation::AuthenticateSession {
            revision: profile.revision(),
            player: AuthenticatedPlayer {
                profile: profile.id(),
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
    /// Profile packet count for diagnostics; not semantic time.
    #[must_use]
    pub const fn framed_packets(&self) -> u64 {
        self.codec.framed_packets()
    }
    #[must_use]
    pub const fn awaiting_initial_rebuild(&self) -> bool {
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
            ConnectionInput::Script(_) => script = true,
        })?;
        if script {
            return Err(rustclient_protocol::ProtocolError {
                stage: "connection consumer",
                detail: "script execution consumer required".into(),
                transport: None,
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
        let mut connected = true;
        self.codec.receive(bytes, |update| match update {
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
            ServerUpdate::ResetActorAnimations => deliver(ConnectionInput::Operation(
                SemanticOperation::ResetActorAnimations,
            )),
            ServerUpdate::LocationZoneReset(origin) => {
                deliver(ConnectionInput::Operation(SemanticOperation::Scenery(
                    rustclient_game_state::SceneryOperation::ResetLocationArea {
                        offset: origin.offset,
                        plane: origin.plane,
                        size: [8, 8],
                    },
                )));
            }
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
            ServerUpdate::Tick => deliver(ConnectionInput::Operation(
                SemanticOperation::AdvanceTick(TickSpan::ONE),
            )),
            ServerUpdate::LoggedOut => connected = false,
            ServerUpdate::PlayerStatus(update) => deliver(ConnectionInput::Operation(
                SemanticOperation::PlayerStatus(update),
            )),
            ServerUpdate::Script(request) => deliver(ConnectionInput::Script(request)),
            ServerUpdate::Deferred => {}
        })?;
        Ok(connected)
    }
}
