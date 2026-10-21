use core::fmt;

use rustclient_model::{Revision, TickSpan};

use crate::commit::{OperationCommit, SemanticEvent, StateDelta};
use crate::state::{GameState, LifecycleError, TickAdvanceError};

/// Source-neutral semantic operation applied to the authoritative [`GameState`].
///
/// Operations describe requested domain changes after transport/UI-specific
/// details have already been translated away. They are not committed events.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum SemanticOperation {
    /// Begin a new semantic game session for an explicit compatibility revision.
    BeginSession {
        /// Protocol/cache compatibility revision selected by the caller.
        revision: Revision,
    },
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

/// Failure while applying an ordered semantic-operation batch.
///
/// Every operation before `failed_index` has already committed successfully.
/// The failing operation does not mutate authoritative state, and later
/// operations in the slice are not attempted.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct OperationBatchError {
    failed_index: usize,
    error: OperationError,
}

impl OperationBatchError {
    /// Return the zero-based index of the first operation that failed.
    #[must_use]
    pub const fn failed_index(self) -> usize {
        self.failed_index
    }

    /// Return the typed error produced by the failing operation.
    #[must_use]
    pub const fn operation_error(self) -> OperationError {
        self.error
    }
}

impl fmt::Display for OperationBatchError {
    fn fmt(&self, formatter: &mut fmt::Formatter<'_>) -> fmt::Result {
        write!(
            formatter,
            "semantic operation batch failed at index {}: {}",
            self.failed_index, self.error
        )
    }
}

impl std::error::Error for OperationBatchError {
    fn source(&self) -> Option<&(dyn std::error::Error + 'static)> {
        Some(&self.error)
    }
}

impl GameState {
    /// Validate and apply exactly one source-neutral semantic operation.
    ///
    /// This is the public mutation gateway for semantic inputs. The dispatcher
    /// deliberately delegates to the crate's validated transition primitives so
    /// lifecycle/time rules have one implementation rather than being repeated.
    /// A successful return describes only facts and state domains that actually
    /// changed as a consequence of the committed mutation.
    ///
    /// # Errors
    ///
    /// Returns [`OperationError`] when the requested operation is invalid for
    /// the current state. Failed operations leave authoritative state unchanged
    /// and therefore produce no [`OperationCommit`].
    pub fn apply_operation(
        &mut self,
        operation: SemanticOperation,
    ) -> Result<OperationCommit, OperationError> {
        match operation {
            SemanticOperation::BeginSession { revision } => {
                self.begin_session(revision)
                    .map_err(OperationError::Lifecycle)?;
                Ok(OperationCommit::new(
                    Some(SemanticEvent::SessionBegan { revision }),
                    StateDelta::SESSION_BOUNDARY,
                ))
            }
            SemanticOperation::EndSession => {
                self.end_session().map_err(OperationError::Lifecycle)?;
                Ok(OperationCommit::new(
                    Some(SemanticEvent::SessionEnded),
                    StateDelta::SESSION_BOUNDARY,
                ))
            }
            SemanticOperation::AdvanceTick(span) => {
                let (previous, current) = self
                    .advance_tick(span)
                    .map_err(OperationError::TickAdvance)?;

                if previous == current {
                    Ok(OperationCommit::new(None, StateDelta::default()))
                } else {
                    Ok(OperationCommit::new(
                        Some(SemanticEvent::GameTickAdvanced { previous, current }),
                        StateDelta::GAME_TICK,
                    ))
                }
            }
        }
    }

    /// Apply an ordered batch of semantic operations from first to last.
    ///
    /// This convenience path preserves CP3D batch semantics while intentionally
    /// discarding commit metadata. Consumers that need committed events/deltas
    /// should call [`Self::apply_operation_batch_with_commits`].
    ///
    /// # Errors
    ///
    /// Returns [`OperationBatchError`] for the first failing operation. Earlier
    /// successful prefix mutations remain committed and later operations are not
    /// attempted.
    pub fn apply_operation_batch(
        &mut self,
        operations: &[SemanticOperation],
    ) -> Result<(), OperationBatchError> {
        self.apply_operation_batch_with_commits(operations, |_, _| {})
    }

    /// Apply an ordered batch and stream each successful commit in input order.
    ///
    /// `on_commit` runs immediately after each operation commits and receives
    /// that operation's zero-based input index plus its [`OperationCommit`]. No
    /// callback occurs for a failing operation or for any skipped suffix.
    /// This keeps batch application allocation-free while exposing exact commit
    /// order to future projection/replay consumers.
    ///
    /// # Errors
    ///
    /// Returns [`OperationBatchError`] containing the first failing operation's
    /// zero-based index and typed [`OperationError`]. Successful prefix commits
    /// have already been delivered to `on_commit` and remain authoritative.
    pub fn apply_operation_batch_with_commits<F>(
        &mut self,
        operations: &[SemanticOperation],
        mut on_commit: F,
    ) -> Result<(), OperationBatchError>
    where
        F: FnMut(usize, OperationCommit),
    {
        for (index, operation) in operations.iter().copied().enumerate() {
            match self.apply_operation(operation) {
                Ok(commit) => on_commit(index, commit),
                Err(error) => {
                    return Err(OperationBatchError {
                        failed_index: index,
                        error,
                    });
                }
            }
        }

        Ok(())
    }
}

#[cfg(test)]
mod tests {
    use rustclient_model::{GameTick, REVISION_240, TickSpan};

    use super::*;
    use crate::SessionLifecycle;

    const BEGIN_240: SemanticOperation = SemanticOperation::BeginSession {
        revision: REVISION_240,
    };

    #[test]
    fn lifecycle_and_tick_changes_apply_through_one_public_gateway() {
        let mut state = GameState::new();

        state
            .apply_operation(BEGIN_240)
            .expect("inactive state can begin a session");
        state
            .apply_operation(SemanticOperation::AdvanceTick(TickSpan::new(5)))
            .expect("small tick advance cannot overflow");

        assert_eq!(state.lifecycle(), SessionLifecycle::Active);
        assert_eq!(state.session_revision(), Some(REVISION_240));
        assert_eq!(state.current_tick(), Some(GameTick::new(5)));

        state
            .apply_operation(SemanticOperation::EndSession)
            .expect("active state can end a session");
        assert_eq!(state.lifecycle(), SessionLifecycle::Inactive);
        assert_eq!(state.session_revision(), None);
        assert_eq!(state.current_tick(), None);
    }

    #[test]
    fn operation_commit_describes_only_committed_changes() {
        let mut state = GameState::new();

        let begin = state
            .apply_operation(BEGIN_240)
            .expect("inactive state can begin a session");
        assert_eq!(
            begin.event(),
            Some(SemanticEvent::SessionBegan {
                revision: REVISION_240,
            })
        );
        assert!(begin.delta().lifecycle_changed());
        assert!(begin.delta().game_tick_changed());

        let advance = state
            .apply_operation(SemanticOperation::AdvanceTick(TickSpan::new(3)))
            .expect("small tick advance cannot overflow");
        assert_eq!(
            advance.event(),
            Some(SemanticEvent::GameTickAdvanced {
                previous: GameTick::ZERO,
                current: GameTick::new(3),
            })
        );
        assert!(!advance.delta().lifecycle_changed());
        assert!(advance.delta().game_tick_changed());

        let end = state
            .apply_operation(SemanticOperation::EndSession)
            .expect("active state can end a session");
        assert_eq!(end.event(), Some(SemanticEvent::SessionEnded));
        assert!(end.delta().lifecycle_changed());
        assert!(end.delta().game_tick_changed());
    }

    #[test]
    fn zero_tick_span_commits_without_claiming_an_observable_change() {
        let mut state = GameState::new();
        state
            .apply_operation(BEGIN_240)
            .expect("inactive state can begin a session");

        let commit = state
            .apply_operation(SemanticOperation::AdvanceTick(TickSpan::ZERO))
            .expect("zero tick span is valid");

        assert_eq!(commit.event(), None);
        assert!(commit.delta().is_empty());
        assert_eq!(state.current_tick(), Some(GameTick::ZERO));
    }

    #[test]
    fn invalid_operation_preserves_authoritative_state_and_typed_error() {
        let mut state = GameState::new();
        let before_lifecycle = state.lifecycle();
        let before_revision = state.session_revision();
        let before_tick = state.current_tick();

        assert_eq!(
            state.apply_operation(SemanticOperation::AdvanceTick(TickSpan::ONE)),
            Err(OperationError::TickAdvance(
                TickAdvanceError::InactiveSession
            ))
        );
        assert_eq!(state.lifecycle(), before_lifecycle);
        assert_eq!(state.session_revision(), before_revision);
        assert_eq!(state.current_tick(), before_tick);
    }

    #[test]
    fn operation_gateway_preserves_checked_tick_overflow() {
        let mut state = GameState::new();
        state
            .apply_operation(BEGIN_240)
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
            BEGIN_240,
            SemanticOperation::AdvanceTick(TickSpan::new(2)),
            SemanticOperation::AdvanceTick(TickSpan::new(3)),
            SemanticOperation::EndSession,
            BEGIN_240,
            SemanticOperation::AdvanceTick(TickSpan::ONE),
        ];

        let mut left = GameState::new();
        let mut right = GameState::new();

        for operation in operations {
            assert_eq!(
                left.apply_operation(operation),
                right.apply_operation(operation)
            );
        }

        assert_eq!(left.lifecycle(), right.lifecycle());
        assert_eq!(left.session_revision(), right.session_revision());
        assert_eq!(left.current_tick(), right.current_tick());
        assert_eq!(left.session_revision(), Some(REVISION_240));
        assert_eq!(left.current_tick(), Some(GameTick::new(1)));
    }

    #[test]
    fn operation_batch_applies_in_exact_slice_order() {
        let operations = [
            BEGIN_240,
            SemanticOperation::AdvanceTick(TickSpan::new(2)),
            SemanticOperation::EndSession,
            BEGIN_240,
            SemanticOperation::AdvanceTick(TickSpan::new(3)),
        ];
        let mut state = GameState::new();

        state
            .apply_operation_batch(&operations)
            .expect("ordered reference batch is valid");

        assert_eq!(state.lifecycle(), SessionLifecycle::Active);
        assert_eq!(state.session_revision(), Some(REVISION_240));
        assert_eq!(state.current_tick(), Some(GameTick::new(3)));
    }

    #[test]
    fn operation_batch_stops_at_first_failure_and_preserves_committed_prefix() {
        let operations = [
            BEGIN_240,
            SemanticOperation::AdvanceTick(TickSpan::new(2)),
            BEGIN_240,
            SemanticOperation::AdvanceTick(TickSpan::new(5)),
        ];
        let mut state = GameState::new();

        let error = state
            .apply_operation_batch(&operations)
            .expect_err("second begin-session must fail");

        assert_eq!(error.failed_index(), 2);
        assert_eq!(
            error.operation_error(),
            OperationError::Lifecycle(LifecycleError::AlreadyActive)
        );
        assert_eq!(state.lifecycle(), SessionLifecycle::Active);
        assert_eq!(state.session_revision(), Some(REVISION_240));
        assert_eq!(state.current_tick(), Some(GameTick::new(2)));
    }

    #[test]
    fn batch_commit_stream_matches_successful_prefix_only() {
        let operations = [
            BEGIN_240,
            SemanticOperation::AdvanceTick(TickSpan::new(2)),
            BEGIN_240,
            SemanticOperation::EndSession,
        ];
        let mut state = GameState::new();
        let mut commits = Vec::new();

        let error = state
            .apply_operation_batch_with_commits(&operations, |index, commit| {
                commits.push((index, commit));
            })
            .expect_err("second begin-session must fail");

        assert_eq!(error.failed_index(), 2);
        assert_eq!(commits.len(), 2);
        assert_eq!(commits[0].0, 0);
        assert_eq!(
            commits[0].1.event(),
            Some(SemanticEvent::SessionBegan {
                revision: REVISION_240,
            })
        );
        assert_eq!(commits[1].0, 1);
        assert_eq!(
            commits[1].1.event(),
            Some(SemanticEvent::GameTickAdvanced {
                previous: GameTick::ZERO,
                current: GameTick::new(2),
            })
        );
        assert_eq!(state.lifecycle(), SessionLifecycle::Active);
        assert_eq!(state.session_revision(), Some(REVISION_240));
        assert_eq!(state.current_tick(), Some(GameTick::new(2)));
    }

    #[test]
    fn empty_operation_batch_is_a_no_op() {
        let mut state = GameState::new();
        let before_lifecycle = state.lifecycle();
        let before_revision = state.session_revision();
        let before_tick = state.current_tick();

        state
            .apply_operation_batch(&[])
            .expect("empty batch is valid");

        assert_eq!(state.lifecycle(), before_lifecycle);
        assert_eq!(state.session_revision(), before_revision);
        assert_eq!(state.current_tick(), before_tick);
    }
}
