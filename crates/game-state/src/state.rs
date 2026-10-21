use core::fmt;

use rustclient_model::{GameTick, Revision, TickSpan};

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
}

impl GameState {
    /// Construct an inactive authoritative state root.
    #[must_use]
    pub const fn new() -> Self {
        Self {
            lifecycle: SessionLifecycle::Inactive,
            revision: None,
            tick: GameTick::ZERO,
        }
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

        self.tick = GameTick::ZERO;
        self.revision = Some(revision);
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
    /// transitions leave the state unchanged.
    pub(crate) fn end_session(&mut self) -> Result<(), LifecycleError> {
        if self.lifecycle == SessionLifecycle::Inactive {
            return Err(LifecycleError::NotActive);
        }

        self.tick = GameTick::ZERO;
        self.revision = None;
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
    /// Failed transitions leave the state unchanged.
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
