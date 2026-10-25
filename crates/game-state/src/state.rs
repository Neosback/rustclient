use crate::{
    AuthenticatedPlayer, ConnectionStateError, InitialPlacement, TitleScreen, TitleTransitionError,
};
use core::fmt;

use rustclient_model::{GameTick, Revision, SceneDescriptor, TickSpan};

/// Whether the authoritative state currently represents an active game session.
#[derive(Debug, Clone, Copy, PartialEq, Eq, Hash, Default)]
pub enum SessionLifecycle {
    /// No semantic game session is active.
    #[default]
    Inactive,
    /// A semantic game session is active.
    Active,
}

/// Invalid authoritative-state lifecycle transition.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum LifecycleError {
    /// A new session was requested while one is already active.
    AlreadyActive,
    /// Session shutdown was requested while no session is active.
    NotActive,
}

impl fmt::Display for LifecycleError {
    fn fmt(&self, formatter: &mut fmt::Formatter<'_>) -> fmt::Result {
        formatter.write_str(match self {
            Self::AlreadyActive => "game session is already active",
            Self::NotActive => "no game session is active",
        })
    }
}

impl std::error::Error for LifecycleError {}

/// Failure to advance deterministic semantic game time.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum TickAdvanceError {
    /// Tick advancement was requested while no session is active.
    InactiveSession,
    /// The requested advance would overflow the semantic tick ordinal.
    Overflow,
}

impl fmt::Display for TickAdvanceError {
    fn fmt(&self, formatter: &mut fmt::Formatter<'_>) -> fmt::Result {
        formatter.write_str(match self {
            Self::InactiveSession => "cannot advance game tick without an active session",
            Self::Overflow => "game tick advance would overflow",
        })
    }
}

impl std::error::Error for TickAdvanceError {}

/// Single authoritative mutable root for live semantic client state.
///
/// Domain fields remain private and are exposed through immutable queries plus
/// explicit transition APIs. This type intentionally does not implement
/// [`Clone`]; projections and tests must not create a second mutable semantic
/// authority by cloning the whole state tree.
#[derive(Debug)]
pub struct GameState {
    lifecycle: SessionLifecycle,
    revision: Option<Revision>,
    tick: GameTick,
    title: TitleScreen,
    authenticated: Option<AuthenticatedPlayer>,
    placement: Option<InitialPlacement>,
    scene: Option<SceneDescriptor>,
    pub(crate) chat: crate::ChatState,
    pub(crate) scenery: crate::SceneryState,
    pub(crate) interfaces: crate::InterfaceState,
    pub(crate) inventories: crate::InventoryState,
    pub(crate) entities: crate::WorldEntitiesState,
    pub(crate) selected_tab: Option<usize>,
    pub(crate) destination: Option<rustclient_model::MapDestination>,
}

impl GameState {
    #[must_use]
    pub const fn chat(&self) -> &crate::ChatState {
        &self.chat
    }
    /// Construct an inactive authoritative state root.
    #[must_use]
    pub fn new() -> Self {
        Self {
            lifecycle: SessionLifecycle::Inactive,
            revision: None,
            tick: GameTick::ZERO,
            title: TitleScreen::Welcome,
            authenticated: None,
            placement: None,
            scene: None,
            chat: crate::ChatState::default(),
            scenery: crate::SceneryState::default(),
            entities: crate::WorldEntitiesState::default(),
            inventories: crate::InventoryState::default(),
            interfaces: crate::InterfaceState::default(),
            selected_tab: Some(3),
            destination: None,
        }
    }

    #[must_use]
    pub const fn inventories(&self) -> &crate::InventoryState {
        &self.inventories
    }

    #[must_use]
    pub const fn interfaces(&self) -> &crate::InterfaceState {
        &self.interfaces
    }

    /// Authoritative world entities (players and NPCs).
    #[must_use]
    pub const fn entities(&self) -> &crate::WorldEntitiesState {
        &self.entities
    }

    /// Currently active gameframe stone tab index.
    #[must_use]
    pub const fn selected_tab(&self) -> Option<usize> {
        self.selected_tab
    }

    /// Non-secret identity established by server authentication.
    #[must_use]
    pub const fn authenticated_player(&self) -> Option<AuthenticatedPlayer> {
        self.authenticated
    }
    /// Current map selection, initially established by the login rebuild.
    #[must_use]
    pub const fn initial_placement(&self) -> Option<InitialPlacement> {
        self.placement
    }
    /// Immutable asset reference selected by the authoritative session.
    #[must_use]
    pub const fn loaded_scene(&self) -> Option<SceneDescriptor> {
        self.scene
    }
    pub(crate) fn install_scene(
        &mut self,
        scene: SceneDescriptor,
    ) -> Result<(), ConnectionStateError> {
        let placement = self
            .placement
            .ok_or(ConnectionStateError::SceneRequiresPlacement)?;
        if !scene.contains(placement.map_anchor) {
            return Err(ConnectionStateError::SceneOutsidePlacement);
        }
        if self.scene.is_some() {
            return Err(ConnectionStateError::SceneAlreadyInstalled);
        }
        self.scene = Some(scene);
        Ok(())
    }
    pub(crate) fn change_map_plane(&mut self, position: rustclient_model::WorldTile) {
        if let Some(placement) = &mut self.placement {
            placement.position = position;
            placement.map_anchor.plane = position.plane;
        }
        self.scene = None;
    }
    pub(crate) fn rebuild_map(
        &mut self,
        anchor: rustclient_model::MapAnchor,
    ) -> Result<InitialPlacement, ConnectionStateError> {
        let previous = self
            .placement
            .ok_or(ConnectionStateError::SceneRequiresPlacement)?;
        let position = self
            .entities
            .local_player()
            .map_or(previous.position, |p| p.tile);
        let placement = InitialPlacement {
            position,
            map_anchor: anchor.on_plane(position.plane),
            area: None,
        };
        self.placement = Some(placement);
        self.scene = None;
        self.scenery.clear_map_locations();
        Ok(placement)
    }
    pub(crate) fn authenticate_session(
        &mut self,
        revision: Revision,
        player: AuthenticatedPlayer,
    ) -> Result<(), LifecycleError> {
        self.begin_session(revision)?;
        self.authenticated = Some(player);
        Ok(())
    }
    pub(crate) fn establish_placement(
        &mut self,
        placement: InitialPlacement,
    ) -> Result<(), ConnectionStateError> {
        if self.authenticated.is_none() {
            return Err(ConnectionStateError::NotAuthenticated);
        }
        if self.placement.is_some() {
            return Err(ConnectionStateError::AlreadyPlaced);
        }
        if placement.position.plane != placement.map_anchor.plane {
            return Err(ConnectionStateError::InconsistentPlane);
        }
        self.placement = Some(placement);
        if let Some(auth) = self.authenticated {
            self.entities.local_player_index = Some(auth.player);
            self.entities.players.insert(
                auth.player,
                crate::PlayerEntity {
                    overhead: None,
                    index: auth.player,
                    instance: rustclient_model::PlayerInstanceId::new(1),
                    tracking_grid: None,
                    path_speed: rustclient_model::MoveSpeed::Walk,
                    movement_speed: rustclient_model::MoveSpeed::Walk,
                    path_queue: crate::ActorPathQueue::default(),
                    exact_move: None,
                    tile: placement.position,
                    orientation: 0,
                    current_orientation: 0,
                    appearance: None,
                    sequence: rustclient_model::ActorSequenceSlot::default(),
                    sequence_timing_resolved: false,
                    animation_cycle: 0,
                    locomotion: crate::Locomotion::Idle,
                },
            );
            self.entities.player_serial = 1;
        }
        Ok(())
    }

    /// Current non-secret title screen.
    #[must_use]
    pub const fn title_screen(&self) -> TitleScreen {
        self.title
    }

    pub(crate) fn set_title_screen(
        &mut self,
        screen: TitleScreen,
    ) -> Result<TitleScreen, TitleTransitionError> {
        if self.lifecycle == SessionLifecycle::Active {
            return Err(TitleTransitionError);
        }
        let previous = self.title;
        self.title = screen;
        Ok(previous)
    }

    /// Return the current session lifecycle.
    #[must_use]
    pub const fn lifecycle(&self) -> SessionLifecycle {
        self.lifecycle
    }

    /// Return the semantic compatibility revision of the active session.
    ///
    /// Revision identity is session-owned. An inactive state has no current
    /// session revision and callers must not infer an implicit default.
    #[must_use]
    pub const fn session_revision(&self) -> Option<Revision> {
        self.revision
    }

    /// Return the current semantic game tick when a session is active.
    #[must_use]
    pub const fn current_tick(&self) -> Option<GameTick> {
        match self.lifecycle {
            SessionLifecycle::Inactive => None,
            SessionLifecycle::Active => Some(self.tick),
        }
    }

    /// Begin a new semantic game session for an explicit compatibility revision.
    ///
    /// Future session-owned fields must be initialized/reset through this same
    /// lifecycle boundary rather than by exposing mutable field access.
    /// `Revision` identifies semantic/protocol/cache compatibility; it is not a
    /// server-profile identity and does not itself assert that a revision is
    /// implemented by the surrounding application.
    ///
    /// # Errors
    ///
    /// Returns [`LifecycleError::AlreadyActive`] when a session is already
    /// active. Failed transitions leave the state unchanged.
    pub(crate) fn begin_session(&mut self, revision: Revision) -> Result<(), LifecycleError> {
        if self.lifecycle == SessionLifecycle::Active {
            return Err(LifecycleError::AlreadyActive);
        }

        self.title = TitleScreen::Welcome;
        self.tick = GameTick::ZERO;
        self.revision = Some(revision);
        self.authenticated = None;
        self.placement = None;
        self.scene = None;
        self.chat = crate::ChatState::default();
        self.scenery = crate::SceneryState::default();
        self.entities = crate::WorldEntitiesState::default();
        self.inventories = crate::InventoryState::default();
        self.interfaces = crate::InterfaceState::default();
        self.selected_tab = Some(3);
        self.destination = None;
        self.lifecycle = SessionLifecycle::Active;
        Ok(())
    }

    /// End the active semantic game session and clear session-owned state.
    ///
    /// Future session-owned fields must be cleared through this same lifecycle
    /// boundary so stale state cannot leak into the next session.
    ///
    /// # Errors
    ///
    /// Returns [`LifecycleError::NotActive`] when no session is active. Failed
    /// transitions leave authoritative state unchanged.
    pub(crate) fn end_session(&mut self) -> Result<(), LifecycleError> {
        if self.lifecycle == SessionLifecycle::Inactive {
            return Err(LifecycleError::NotActive);
        }

        self.title = TitleScreen::Welcome;
        self.tick = GameTick::ZERO;
        self.revision = None;
        self.authenticated = None;
        self.placement = None;
        self.scene = None;
        self.chat = crate::ChatState::default();
        self.scenery = crate::SceneryState::default();
        self.entities = crate::WorldEntitiesState::default();
        self.inventories = crate::InventoryState::default();
        self.interfaces = crate::InterfaceState::default();
        self.selected_tab = Some(3);
        self.destination = None;
        self.lifecycle = SessionLifecycle::Inactive;
        Ok(())
    }

    /// Advance deterministic semantic time by an explicit tick span.
    ///
    /// This transition primitive performs validation before mutation and returns
    /// both the previous and committed tick so event generation can describe the
    /// exact fact without reimplementing transition rules.
    ///
    /// # Errors
    ///
    /// Returns [`TickAdvanceError::InactiveSession`] when no session is active,
    /// or [`TickAdvanceError::Overflow`] when the tick ordinal would overflow.
    /// Failed transitions leave authoritative state unchanged.
    pub(crate) fn advance_tick(
        &mut self,
        span: TickSpan,
    ) -> Result<(GameTick, GameTick), TickAdvanceError> {
        if self.lifecycle == SessionLifecycle::Inactive {
            return Err(TickAdvanceError::InactiveSession);
        }

        let previous = self.tick;
        let Some(next_tick) = previous.checked_advance(span) else {
            return Err(TickAdvanceError::Overflow);
        };

        self.tick = next_tick;
        Ok((previous, next_tick))
    }
}

impl Default for GameState {
    fn default() -> Self {
        Self::new()
    }
}

#[cfg(test)]
mod tests {
    use rustclient_model::REVISION_240;

    use super::*;

    #[test]
    fn state_starts_without_an_active_session() {
        let state = GameState::new();
        assert_eq!(state.lifecycle(), SessionLifecycle::Inactive);
        assert_eq!(state.session_revision(), None);
        assert_eq!(state.current_tick(), None);
    }

    #[test]
    fn lifecycle_transitions_are_explicit_and_strict() {
        let mut state = GameState::new();

        state
            .begin_session(REVISION_240)
            .expect("inactive state can begin a session");
        assert_eq!(state.lifecycle(), SessionLifecycle::Active);
        assert_eq!(state.session_revision(), Some(REVISION_240));
        assert_eq!(state.current_tick(), Some(GameTick::ZERO));
        assert_eq!(
            state.begin_session(REVISION_240),
            Err(LifecycleError::AlreadyActive)
        );
        assert_eq!(state.lifecycle(), SessionLifecycle::Active);
        assert_eq!(state.session_revision(), Some(REVISION_240));
        assert_eq!(state.current_tick(), Some(GameTick::ZERO));

        state.end_session().expect("active state can end a session");
        assert_eq!(state.lifecycle(), SessionLifecycle::Inactive);
        assert_eq!(state.session_revision(), None);
        assert_eq!(state.current_tick(), None);
        assert_eq!(state.end_session(), Err(LifecycleError::NotActive));
        assert_eq!(state.lifecycle(), SessionLifecycle::Inactive);
        assert_eq!(state.session_revision(), None);
        assert_eq!(state.current_tick(), None);
    }

    #[test]
    fn tick_advance_requires_an_active_session() {
        let mut state = GameState::new();

        assert_eq!(
            state.advance_tick(TickSpan::ONE),
            Err(TickAdvanceError::InactiveSession)
        );
        assert_eq!(state.current_tick(), None);
    }

    #[test]
    fn tick_advance_is_explicit_and_checked() {
        let mut state = GameState::new();
        state
            .begin_session(REVISION_240)
            .expect("inactive state can begin a session");

        assert_eq!(
            state.advance_tick(TickSpan::new(5)),
            Ok((GameTick::ZERO, GameTick::new(5)))
        );
        assert_eq!(state.current_tick(), Some(GameTick::new(5)));

        state.tick = GameTick::new(u64::MAX);
        assert_eq!(
            state.advance_tick(TickSpan::ONE),
            Err(TickAdvanceError::Overflow)
        );
        assert_eq!(state.current_tick(), Some(GameTick::new(u64::MAX)));
    }

    #[test]
    fn new_session_never_inherits_previous_session_state() {
        let mut state = GameState::new();
        state
            .begin_session(REVISION_240)
            .expect("inactive state can begin a session");
        state
            .advance_tick(TickSpan::new(12))
            .expect("small tick advance cannot overflow");
        state.end_session().expect("active state can end a session");

        assert_eq!(state.session_revision(), None);
        assert_eq!(state.current_tick(), None);

        state
            .begin_session(REVISION_240)
            .expect("inactive state can begin another session");

        assert_eq!(state.session_revision(), Some(REVISION_240));
        assert_eq!(state.current_tick(), Some(GameTick::ZERO));
    }
}
