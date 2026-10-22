//! Shared interpretation of authenticated profile updates as semantic operations.
use rustclient_game_state::{AuthenticatedPlayer, InitialPlacement, SemanticOperation};
use rustclient_model::TickSpan;
use rustclient_protocol::{LoginSession, Result, ServerProfile, ServerUpdate, SessionCodec};
/// Narrow connection decoder, storing only wire phase/ciphers rather than game truth.
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
                codec: SessionCodec::new(login.incoming, login.outgoing),
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
    #[must_use]
    pub fn encode(&mut self, intent: rustclient_protocol::ClientIntent) -> Vec<u8> {
        self.codec.encode(intent)
    }
    /// Decode updates in order; false means the server ended the session.
    /// # Errors
    /// Forwards bounded profile decode errors; no authoritative mutation occurs here.
    pub fn receive(
        &mut self,
        bytes: &[u8],
        mut emit: impl FnMut(SemanticOperation),
    ) -> Result<bool> {
        let mut connected = true;
        self.codec.receive(bytes, |update| match update {
            ServerUpdate::InitialPlacement(p) => emit(
                SemanticOperation::EstablishInitialPlacement(InitialPlacement {
                    position: p.position,
                    map_anchor: p.map_anchor,
                    area: p.area,
                }),
            ),
            ServerUpdate::PlayerInfo(info) => emit(SemanticOperation::UpdatePlayers(info)),
            ServerUpdate::NpcInfo(info) => emit(SemanticOperation::UpdateNpcs(info)),
            ServerUpdate::LocationAnim(anim) => {
                emit(SemanticOperation::Scenery(
                    rustclient_game_state::SceneryOperation::AnimateLocation {
                        key: anim.key,
                        start: Some(rustclient_model::AnimationStart {
                            sequence: Some(anim.sequence),
                            cycle: rustclient_model::ClientCycle::new(0),
                            random_seed: 0,
                        }),
                    },
                ));
            }
            ServerUpdate::Variable(update) => emit(SemanticOperation::Scenery(match update {
                rustclient_protocol::VariableUpdate::Set { id, value } => {
                    rustclient_game_state::SceneryOperation::SetVariable { id, value }
                }
                rustclient_protocol::VariableUpdate::Reset => {
                    rustclient_game_state::SceneryOperation::ResetVariables
                }
                rustclient_protocol::VariableUpdate::Sync => {
                    rustclient_game_state::SceneryOperation::SynchronizeVariables
                }
            })),
            ServerUpdate::Tick => emit(SemanticOperation::AdvanceTick(TickSpan::ONE)),
            ServerUpdate::LoggedOut => connected = false,
            ServerUpdate::Deferred => {}
        })?;
        Ok(connected)
    }
}
