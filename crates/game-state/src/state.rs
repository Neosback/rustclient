use core::fmt;

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

/// Single authoritative mutable root for live semantic client state.
///
/// Domain fields remain private and are exposed through immutable queries plus
/// explicit transition APIs. This type intentionally does not implement
/// [`Clone`]; projections and tests must not create a second mutable semantic
/// authority by cloning the whole state tree.
#[derive(Debug, Default)]
pub struct GameState {
    lifecycle: SessionLifecycle,
}

impl GameState {
    /// Construct an inactive authoritative state root.
    #[must_use]
    pub const fn new() -> Self {
        Self {
            lifecycle: SessionLifecycle::Inactive,
        }
    }

    /// Return the current session lifecycle.
    #[must_use]
    pub const fn lifecycle(&self) -> SessionLifecycle {
        self.lifecycle
    }

    /// Begin a new semantic game session.
    ///
    /// Future session-owned fields must be initialized/reset through this same
    /// lifecycle boundary rather than by exposing mutable field access.
    ///
    /// # Errors
    ///
    /// Returns [`LifecycleError::AlreadyActive`] when a session is already
    /// active. Failed transitions leave the state unchanged.
    pub fn begin_session(&mut self) -> Result<(), LifecycleError> {
        if self.lifecycle == SessionLifecycle::Active {
            return Err(LifecycleError::AlreadyActive);
        }

        self.lifecycle = SessionLifecycle::Active;
        Ok(())
    }

    /// End the active semantic game session.
    ///
    /// Future session-owned fields must be cleared through this same lifecycle
    /// boundary so stale state cannot leak into the next session.
    ///
    /// # Errors
    ///
    /// Returns [`LifecycleError::NotActive`] when no session is active. Failed
    /// transitions leave the state unchanged.
    pub fn end_session(&mut self) -> Result<(), LifecycleError> {
        if self.lifecycle == SessionLifecycle::Inactive {
            return Err(LifecycleError::NotActive);
        }

        self.lifecycle = SessionLifecycle::Inactive;
        Ok(())
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn state_starts_without_an_active_session() {
        let state = GameState::new();
        assert_eq!(state.lifecycle(), SessionLifecycle::Inactive);
    }

    #[test]
    fn lifecycle_transitions_are_explicit_and_strict() {
        let mut state = GameState::new();

        state
            .begin_session()
            .expect("inactive state can begin a session");
        assert_eq!(state.lifecycle(), SessionLifecycle::Active);
        assert_eq!(state.begin_session(), Err(LifecycleError::AlreadyActive));
        assert_eq!(state.lifecycle(), SessionLifecycle::Active);

        state.end_session().expect("active state can end a session");
        assert_eq!(state.lifecycle(), SessionLifecycle::Inactive);
        assert_eq!(state.end_session(), Err(LifecycleError::NotActive));
        assert_eq!(state.lifecycle(), SessionLifecycle::Inactive);
    }
}
