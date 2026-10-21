use core::fmt;

use rustclient_model::TickSpan;

use crate::state::{GameState, LifecycleError, TickAdvanceError};

/// Source-neutral semantic operation applied to the authoritative [`GameState`].
///
/// Operations describe requested domain changes after transport/UI-specific
/// details have already been translated away. They are not committed events.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum SemanticOperation {
    /// Begin a new semantic game session.
    BeginSession,
    /// End the active semantic game session.
    EndSession,
    /// Advance deterministic semantic game time by the supplied span.
    AdvanceTick(TickSpan),
}

/// Failure while validating or applying one semantic operation.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum OperationError {
    /// Session lifecycle validation failed.
    Lifecycle(LifecycleError),
    /// Deterministic game-time validation failed.
    TickAdvance(TickAdvanceError),
}

impl fmt::Display for OperationError {
    fn fmt(&self, formatter: &mut fmt::Formatter<'_>) -> fmt::Result {
        match self {
            Self::Lifecycle(error) => error.fmt(formatter),
            Self::TickAdvance(error) => error.fmt(formatter),
        }
    }
}

impl std::error::Error for OperationError {
    fn source(&self) -> Option<&(dyn std::error::Error + 'static)> {
        match self {
            Self::Lifecycle(error) => Some(error),
            Self::TickAdvance(error) => Some(error),
        }
    }
}

impl GameState {
    /// Validate and apply exactly one source-neutral semantic operation.
    ///
    /// This is the public mutation gateway for semantic inputs. The dispatcher
    /// deliberately delegates to the crate's validated transition primitives so
    /// lifecycle/time rules have one implementation rather than being repeated.
    ///
    /// # Errors
    ///
    /// Returns [`OperationError`] when the requested operation is invalid for
    /// the current state. Failed operations leave authoritative state unchanged.
    pub fn apply_operation(&mut self, operation: SemanticOperation) -> Result<(), OperationError> {
        match operation {
            SemanticOperation::BeginSession => self
                .begin_session()
                .map_err(OperationError::Lifecycle),
            SemanticOperation::EndSession => {
                self.end_session().map_err(OperationError::Lifecycle)
            }
            SemanticOperation::AdvanceTick(span) => self
                .advance_tick(span)
                .map(|_| ())
                .map_err(OperationError::TickAdvance),
        }
    }
}

#[cfg(test)]
mod tests {
    use rustclient_model::{GameTick, TickSpan};

    use super::*;
    use crate::SessionLifecycle;

    #[test]
    fn lifecycle_and_tick_changes_apply_through_one_public_gateway() {
        let mut state = GameState::new();

        state
            .apply_operation(SemanticOperation::BeginSession)
            .expect("inactive state can begin a session");
        state
            .apply_operation(SemanticOperation::AdvanceTick(TickSpan::new(5)))
            .expect("small tick advance cannot overflow");

        assert_eq!(state.lifecycle(), SessionLifecycle::Active);
        assert_eq!(state.current_tick(), Some(GameTick::new(5)));

        state
            .apply_operation(SemanticOperation::EndSession)
            .expect("active state can end a session");
        assert_eq!(state.lifecycle(), SessionLifecycle::Inactive);
        assert_eq!(state.current_tick(), None);
    }

    #[test]
    fn invalid_operation_preserves_authoritative_state_and_typed_error() {
        let mut state = GameState::new();
        let before_lifecycle = state.lifecycle();
        let before_tick = state.current_tick();

        assert_eq!(
            state.apply_operation(SemanticOperation::AdvanceTick(TickSpan::ONE)),
            Err(OperationError::TickAdvance(
                TickAdvanceError::InactiveSession
            ))
        );
        assert_eq!(state.lifecycle(), before_lifecycle);
        assert_eq!(state.current_tick(), before_tick);
    }

    #[test]
    fn operation_gateway_preserves_checked_tick_overflow() {
        let mut state = GameState::new();
        state
            .apply_operation(SemanticOperation::BeginSession)
            .expect("inactive state can begin a session");
        state
            .apply_operation(SemanticOperation::AdvanceTick(TickSpan::new(u64::MAX)))
            .expect("zero plus max is representable");

        let before_tick = state.current_tick();
        assert_eq!(
            state.apply_operation(SemanticOperation::AdvanceTick(TickSpan::ONE)),
            Err(OperationError::TickAdvance(TickAdvanceError::Overflow))
        );
        assert_eq!(state.current_tick(), before_tick);
    }

    #[test]
    fn identical_operation_sequences_produce_identical_observable_state() {
        let operations = [
            SemanticOperation::BeginSession,
            SemanticOperation::AdvanceTick(TickSpan::new(2)),
            SemanticOperation::AdvanceTick(TickSpan::new(3)),
            SemanticOperation::EndSession,
            SemanticOperation::BeginSession,
            SemanticOperation::AdvanceTick(TickSpan::ONE),
        ];

        let mut left = GameState::new();
        let mut right = GameState::new();

        for operation in operations {
            left.apply_operation(operation)
                .expect("reference operation sequence is valid");
            right
                .apply_operation(operation)
                .expect("same operation sequence is valid");
        }

        assert_eq!(left.lifecycle(), right.lifecycle());
        assert_eq!(left.current_tick(), right.current_tick());
        assert_eq!(left.current_tick(), Some(GameTick::new(1)));
    }
}
