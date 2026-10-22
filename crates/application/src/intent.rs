use core::fmt;
use rustclient_game_state::{GameState, SessionLifecycle};
use rustclient_input::GameIntent;
use rustclient_protocol::{ClientIntent, ProtocolError};

/// Failure while preparing a local gameplay intent for the active server profile.
#[derive(Debug, Clone, PartialEq, Eq)]
pub enum GameIntentError {
    /// No authoritative game session is active.
    InactiveSession,
    /// The active session has not completed authentication.
    NotAuthenticated,
    /// No authoritative scene descriptor is installed yet.
    SceneUnavailable,
    /// The requested world tile is not inside the committed scene footprint.
    OutsideScene,
    /// The semantic intent exists but is not implemented by the current delivery slice.
    Unsupported,
    /// The selected protocol profile cannot represent the validated semantic request.
    Protocol(ProtocolError),
}

impl fmt::Display for GameIntentError {
    fn fmt(&self, formatter: &mut fmt::Formatter<'_>) -> fmt::Result {
        formatter.write_str(match self {
            Self::InactiveSession => "gameplay intent requires an active session",
            Self::NotAuthenticated => "gameplay intent requires an authenticated session",
            Self::SceneUnavailable => "gameplay intent requires a committed scene",
            Self::OutsideScene => "gameplay destination is outside the committed scene",
            Self::Unsupported => "gameplay intent is not implemented yet",
            Self::Protocol(error) => return error.fmt(formatter),
        })
    }
}

impl std::error::Error for GameIntentError {
    fn source(&self) -> Option<&(dyn std::error::Error + 'static)> {
        match self {
            Self::Protocol(error) => Some(error),
            _ => None,
        }
    }
}

impl From<ProtocolError> for GameIntentError {
    fn from(error: ProtocolError) -> Self {
        Self::Protocol(error)
    }
}

/// Validate a revision-neutral user intent against committed semantic state, then translate it
/// into the selected profile's outbound semantic packet request.
///
/// This function does not mutate [`GameState`] and does not encode bytes. Scene/session checks
/// stay target-neutral here; opcode/endian/transform knowledge remains in `rustclient-protocol`.
///
/// # Errors
/// Rejects inactive, unauthenticated, scene-less, out-of-scene, unsupported, or profile-invalid
/// requests before the native/browser transport can send them.
pub fn prepare_game_intent(
    state: &GameState,
    intent: GameIntent,
) -> Result<ClientIntent, GameIntentError> {
    if state.lifecycle() != SessionLifecycle::Active {
        return Err(GameIntentError::InactiveSession);
    }
    if state.authenticated_player().is_none() {
        return Err(GameIntentError::NotAuthenticated);
    }
    let scene = state
        .loaded_scene()
        .ok_or(GameIntentError::SceneUnavailable)?;

    match intent {
        GameIntent::WalkTo { destination } => {
            if !scene.contains(destination) {
                return Err(GameIntentError::OutsideScene);
            }
            Ok(ClientIntent::walk_to(destination)?)
        }
        GameIntent::Interact { .. } => Err(GameIntentError::Unsupported),
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use rustclient_game_state::{AuthenticatedPlayer, InitialPlacement, SemanticOperation};
    use rustclient_model::{
        Plane, PlayerIndex, REVISION_240, SceneAssetId, SceneDescriptor, ServerProfileId, WorldTile,
    };

    fn state_with_scene() -> GameState {
        let mut state = GameState::new();
        state
            .apply_operation(SemanticOperation::AuthenticateSession {
                revision: REVISION_240,
                player: AuthenticatedPlayer {
                    profile: ServerProfileId::new(1),
                    player: PlayerIndex::new(7),
                    member: true,
                    staff_level: 0,
                },
            })
            .expect("test session should authenticate");
        let position = WorldTile::new(3_200, 3_200, Plane::MIN);
        state
            .apply_operation(SemanticOperation::EstablishInitialPlacement(
                InitialPlacement {
                    position,
                    map_anchor: position,
                    area: None,
                },
            ))
            .expect("test placement should commit");
        let scene = SceneDescriptor::new(
            SceneAssetId::new([1; 32]),
            WorldTile::new(3_190, 3_190, Plane::MIN),
            20,
            20,
        )
        .expect("test scene should be valid");
        state
            .apply_operation(SemanticOperation::InstallScene(scene))
            .expect("test scene should install");
        state
    }

    #[test]
    fn walk_requires_an_active_authenticated_committed_scene() {
        let destination = WorldTile::new(3_201, 3_202, Plane::MIN);
        let intent = GameIntent::WalkTo { destination };

        assert_eq!(
            prepare_game_intent(&GameState::new(), intent),
            Err(GameIntentError::InactiveSession)
        );

        let mut active = GameState::new();
        active
            .apply_operation(SemanticOperation::BeginSession {
                revision: REVISION_240,
            })
            .expect("test session should begin");
        assert_eq!(
            prepare_game_intent(&active, intent),
            Err(GameIntentError::NotAuthenticated)
        );
    }

    #[test]
    fn walk_rejects_destinations_outside_the_authoritative_scene() {
        let state = state_with_scene();
        let outside = WorldTile::new(3_210, 3_200, Plane::MIN);

        assert_eq!(
            prepare_game_intent(
                &state,
                GameIntent::WalkTo {
                    destination: outside,
                },
            ),
            Err(GameIntentError::OutsideScene)
        );
    }

    #[test]
    fn walk_inside_scene_becomes_profile_owned_client_intent() {
        let state = state_with_scene();
        let destination = WorldTile::new(3_201, 3_202, Plane::MIN);

        let prepared = prepare_game_intent(&state, GameIntent::WalkTo { destination })
            .expect("in-scene walk should validate");
        let expected =
            ClientIntent::walk_to(destination).expect("profile should encode coordinate");

        assert_eq!(prepared, expected);
    }
}
