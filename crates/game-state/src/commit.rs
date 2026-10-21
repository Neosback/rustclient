use rustclient_model::GameTick;

/// A semantic fact that became true because authoritative state committed.
///
/// Events are outputs, never mutation requests. A successful operation that
/// does not change observable state may commit without producing an event.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum SemanticEvent {
    /// A semantic game session became active at its defined initial state.
    SessionBegan,
    /// The active semantic game session ended.
    SessionEnded,
    /// Deterministic semantic game time changed from one tick to another.
    GameTickAdvanced {
        /// Tick ordinal before the committed transition.
        previous: GameTick,
        /// Tick ordinal after the committed transition.
        current: GameTick,
    },
}

/// Efficient description of which currently modeled state domains changed.
///
/// A delta is projection metadata, not authoritative state. Consumers query
/// [`crate::GameState`] for current values after observing changed domains.
#[derive(Debug, Clone, Copy, PartialEq, Eq, Default)]
pub struct StateDelta {
    lifecycle_changed: bool,
    game_tick_changed: bool,
}

impl StateDelta {
    pub(crate) const SESSION_BOUNDARY: Self = Self {
        lifecycle_changed: true,
        game_tick_changed: true,
    };

    pub(crate) const GAME_TICK: Self = Self {
        lifecycle_changed: false,
        game_tick_changed: true,
    };

    /// Return whether session lifecycle changed in this commit.
    #[must_use]
    pub const fn lifecycle_changed(self) -> bool {
        self.lifecycle_changed
    }

    /// Return whether the observable semantic game tick changed in this commit.
    #[must_use]
    pub const fn game_tick_changed(self) -> bool {
        self.game_tick_changed
    }

    /// Return whether no currently modeled state domain changed.
    #[must_use]
    pub const fn is_empty(self) -> bool {
        !self.lifecycle_changed && !self.game_tick_changed
    }
}

/// Result metadata for one successfully applied semantic operation.
///
/// The authoritative mutation has already committed when this value exists.
/// `event` is absent when the successful operation caused no observable state
/// change; `delta` is correspondingly empty in that case.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct OperationCommit {
    event: Option<SemanticEvent>,
    delta: StateDelta,
}

impl OperationCommit {
    pub(crate) const fn new(event: Option<SemanticEvent>, delta: StateDelta) -> Self {
        Self { event, delta }
    }

    /// Return the committed semantic fact, if observable state changed.
    #[must_use]
    pub const fn event(self) -> Option<SemanticEvent> {
        self.event
    }

    /// Return the projection-oriented description of changed state domains.
    #[must_use]
    pub const fn delta(self) -> StateDelta {
        self.delta
    }
}
