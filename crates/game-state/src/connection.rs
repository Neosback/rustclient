//! Authoritative non-secret authenticated session bootstrap.
use core::fmt;
use rustclient_model::{PlayerIndex, ServerProfileId, WorldAreaId, WorldTile};
/// Authenticated identity; passwords, account hashes and cipher keys are excluded.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct AuthenticatedPlayer {
    pub profile: ServerProfileId,
    pub player: PlayerIndex,
    pub member: bool,
    pub staff_level: u8,
}
/// Initial player placement and the anchor of the loaded map area.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct InitialPlacement {
    pub position: WorldTile,
    pub map_anchor: WorldTile,
    pub area: Option<WorldAreaId>,
}
/// Invalid authenticated-session transition.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum ConnectionStateError {
    NotAuthenticated,
    AlreadyPlaced,
    InconsistentPlane,
    SceneRequiresPlacement,
    SceneOutsidePlacement,
    SceneAlreadyInstalled,
}
impl fmt::Display for ConnectionStateError {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        f.write_str(match self {
            Self::SceneRequiresPlacement => "scene requires initial placement",
            Self::SceneOutsidePlacement => "scene footprint does not contain initial placement",
            Self::SceneAlreadyInstalled => "scene already installed for this session",
            Self::NotAuthenticated => "placement requires an authenticated session",
            Self::AlreadyPlaced => "initial placement already established",
            Self::InconsistentPlane => "placement and map anchor planes differ",
        })
    }
}
impl std::error::Error for ConnectionStateError {}
