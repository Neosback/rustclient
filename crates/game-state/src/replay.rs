use core::fmt;

use crate::{GameState, OperationCommit, OperationError, SemanticOperation};

/// One replayable semantic transition plus the commit metadata originally observed.
///
/// This is an in-memory semantic contract, not a stable serialized file format.
/// The operation is replayed through [`GameState::apply_operation`], and the
/// resulting commit must exactly match `commit` for replay to continue.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct ReplayEnvelope {
    operation: SemanticOperation,
    commit: OperationCommit,
}

impl ReplayEnvelope {
    /// Pair one semantic operation with the commit metadata recorded when it succeeded.
    #[must_use]
    pub const fn new(operation: SemanticOperation, commit: OperationCommit) -> Self {
        Self { operation, commit }
    }

    /// Return the semantic operation that must be replayed.
    #[must_use]
    pub const fn operation(self) -> SemanticOperation {
        self.operation
    }

    /// Return the commit metadata expected from deterministic replay.
    #[must_use]
    pub const fn commit(self) -> OperationCommit {
        self.commit
    }
}

/// Why deterministic semantic replay stopped.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum ReplayFailure {
    /// A recorded operation was rejected when replayed against the current state.
    OperationRejected(OperationError),
    /// The operation committed, but its resulting metadata differed from the recording.
    CommitMismatch {
        /// Commit metadata stored in the replay envelope.
        expected: OperationCommit,
        /// Commit metadata produced by replaying the operation.
        actual: OperationCommit,
    },
}

/// Failure while replaying an ordered sequence of semantic commit envelopes.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct ReplayError {
    failed_index: usize,
    failure: ReplayFailure,
}

impl ReplayError {
    /// Return the zero-based replay-envelope index where replay stopped.
    #[must_use]
    pub const fn failed_index(self) -> usize {
        self.failed_index
    }

    /// Return the typed replay failure.
    #[must_use]
    pub const fn failure(self) -> ReplayFailure {
        self.failure
    }
}

impl fmt::Display for ReplayError {
    fn fmt(&self, formatter: &mut fmt::Formatter<'_>) -> fmt::Result {
        match self.failure {
            ReplayFailure::OperationRejected(error) => write!(
                formatter,
                "semantic replay rejected operation at index {}: {}",
                self.failed_index, error
            ),
            ReplayFailure::CommitMismatch { expected, actual } => write!(
                formatter,
                "semantic replay commit mismatch at index {}: expected {expected:?}, actual {actual:?}",
                self.failed_index
            ),
        }
    }
}

impl std::error::Error for ReplayError {
    fn source(&self) -> Option<&(dyn std::error::Error + 'static)> {
        match &self.failure {
            ReplayFailure::OperationRejected(error) => Some(error),
            ReplayFailure::CommitMismatch { .. } => None,
        }
    }
}

impl GameState {
    /// Replay and verify an ordered sequence of semantic commit envelopes.
    ///
    /// Each operation is applied through the normal authoritative mutation
    /// gateway. Replay stops at the first rejected operation or commit mismatch.
    /// Session revision identity is reproduced by the recorded `BeginSession`
    /// operation and verified again by its committed `SessionBegan` event.
    /// An empty sequence is a valid no-op.
    ///
    /// A commit mismatch can only be detected after the operation has committed.
    /// Therefore, callers must treat the replay state as invalid/disposable after
    /// a mismatch rather than attempting to continue from it.
    ///
    /// # Errors
    ///
    /// Returns [`ReplayError`] containing the exact zero-based envelope index and
    /// typed rejection/mismatch reason.
    pub fn replay_envelopes(&mut self, envelopes: &[ReplayEnvelope]) -> Result<(), ReplayError> {
        self.replay_envelopes_with_commits(envelopes, |_, _| {})
    }

    /// Replay envelopes and stream only commits that exactly match their recording.
    ///
    /// `on_verified_commit` runs after the authoritative operation commits *and*
    /// after its [`OperationCommit`] equals the envelope's recorded commit. No
    /// callback occurs for a rejected operation, a mismatching commit, or any
    /// skipped suffix.
    ///
    /// # Errors
    ///
    /// Returns [`ReplayError`] at the first rejection or commit mismatch.
    pub fn replay_envelopes_with_commits<F>(
        &mut self,
        envelopes: &[ReplayEnvelope],
        mut on_verified_commit: F,
    ) -> Result<(), ReplayError>
    where
        F: FnMut(usize, OperationCommit),
    {
        for (index, envelope) in envelopes.iter().copied().enumerate() {
            let actual = self
                .apply_operation(envelope.operation)
                .map_err(|error| ReplayError {
                    failed_index: index,
                    failure: ReplayFailure::OperationRejected(error),
                })?;

            if actual != envelope.commit {
                return Err(ReplayError {
                    failed_index: index,
                    failure: ReplayFailure::CommitMismatch {
                        expected: envelope.commit,
                        actual,
                    },
                });
            }

            on_verified_commit(index, actual);
        }

        Ok(())
    }
}

#[cfg(test)]
mod tests {
    use rustclient_model::{GameTick, REVISION_240, TickSpan};

    use super::*;
    use crate::{SemanticEvent, SessionLifecycle};

    const BEGIN_240: SemanticOperation = SemanticOperation::BeginSession {
        revision: REVISION_240,
    };

    fn record_operations(operations: &[SemanticOperation]) -> (GameState, Vec<ReplayEnvelope>) {
        let mut state = GameState::new();
        let mut envelopes = Vec::with_capacity(operations.len());

        for operation in operations.iter().copied() {
            let commit = state
                .apply_operation(operation)
                .expect("recording operation sequence must be valid");
            envelopes.push(ReplayEnvelope::new(operation, commit));
        }

        (state, envelopes)
    }

    #[test]
    fn replay_reconstructs_equivalent_semantic_state_and_verified_commits() {
        let operations = [
            BEGIN_240,
            SemanticOperation::AdvanceTick(TickSpan::new(2)),
            SemanticOperation::AdvanceTick(TickSpan::ZERO),
            SemanticOperation::AdvanceTick(TickSpan::new(3)),
            SemanticOperation::EndSession,
            BEGIN_240,
            SemanticOperation::AdvanceTick(TickSpan::ONE),
        ];
        let (recorded_state, envelopes) = record_operations(&operations);
        let mut replayed_state = GameState::new();
        let mut verified = Vec::new();

        replayed_state
            .replay_envelopes_with_commits(&envelopes, |index, commit| {
                verified.push((index, commit));
            })
            .expect("recorded semantic sequence must replay exactly");

        assert_eq!(replayed_state.lifecycle(), recorded_state.lifecycle());
        assert_eq!(
            replayed_state.session_revision(),
            recorded_state.session_revision()
        );
        assert_eq!(replayed_state.current_tick(), recorded_state.current_tick());
        assert_eq!(replayed_state.lifecycle(), SessionLifecycle::Active);
        assert_eq!(replayed_state.session_revision(), Some(REVISION_240));
        assert_eq!(replayed_state.current_tick(), Some(GameTick::new(1)));
        assert_eq!(verified.len(), envelopes.len());
        for (index, commit) in verified {
            assert_eq!(commit, envelopes[index].commit());
        }
    }

    #[test]
    fn replay_reports_rejected_operation_at_exact_index_and_stops() {
        let mut source = GameState::new();
        let begin_commit = source
            .apply_operation(BEGIN_240)
            .expect("first begin-session is valid");
        let envelopes = [
            ReplayEnvelope::new(BEGIN_240, begin_commit),
            ReplayEnvelope::new(BEGIN_240, begin_commit),
            ReplayEnvelope::new(SemanticOperation::EndSession, begin_commit),
        ];
        let mut replayed_state = GameState::new();
        let mut verified_indexes = Vec::new();

        let error = replayed_state
            .replay_envelopes_with_commits(&envelopes, |index, _| {
                verified_indexes.push(index);
            })
            .expect_err("second begin-session must be rejected");

        assert_eq!(error.failed_index(), 1);
        assert_eq!(
            error.failure(),
            ReplayFailure::OperationRejected(OperationError::Lifecycle(
                crate::LifecycleError::AlreadyActive
            ))
        );
        assert_eq!(verified_indexes, vec![0]);
        assert_eq!(replayed_state.lifecycle(), SessionLifecycle::Active);
        assert_eq!(replayed_state.session_revision(), Some(REVISION_240));
        assert_eq!(replayed_state.current_tick(), Some(GameTick::ZERO));
    }

    #[test]
    fn replay_detects_commit_mismatch_after_operation_commit() {
        let operations = [
            BEGIN_240,
            SemanticOperation::AdvanceTick(TickSpan::new(2)),
            SemanticOperation::AdvanceTick(TickSpan::new(3)),
        ];
        let (_, recorded) = record_operations(&operations);
        let envelopes = [
            recorded[0],
            ReplayEnvelope::new(recorded[1].operation(), recorded[2].commit()),
            recorded[2],
        ];
        let mut replayed_state = GameState::new();
        let mut verified_indexes = Vec::new();

        let error = replayed_state
            .replay_envelopes_with_commits(&envelopes, |index, _| {
                verified_indexes.push(index);
            })
            .expect_err("tampered expected commit must be detected");

        assert_eq!(error.failed_index(), 1);
        assert_eq!(
            error.failure(),
            ReplayFailure::CommitMismatch {
                expected: recorded[2].commit(),
                actual: recorded[1].commit(),
            }
        );
        assert_eq!(verified_indexes, vec![0]);
        assert_eq!(replayed_state.session_revision(), Some(REVISION_240));
        assert_eq!(replayed_state.current_tick(), Some(GameTick::new(2)));
    }

    #[test]
    fn replay_preserves_semantic_no_op_commit_metadata() {
        let operations = [
            BEGIN_240,
            SemanticOperation::AdvanceTick(TickSpan::ZERO),
        ];
        let (_, envelopes) = record_operations(&operations);
        let mut replayed_state = GameState::new();
        let mut verified = Vec::new();

        replayed_state
            .replay_envelopes_with_commits(&envelopes, |_, commit| {
                verified.push(commit);
            })
            .expect("zero-span semantic no-op must replay exactly");

        assert_eq!(verified[1].event(), None);
        assert!(verified[1].delta().is_empty());
        assert_eq!(replayed_state.session_revision(), Some(REVISION_240));
        assert_eq!(replayed_state.current_tick(), Some(GameTick::ZERO));
    }

    #[test]
    fn empty_replay_sequence_is_a_no_op() {
        let mut state = GameState::new();

        state
            .replay_envelopes(&[])
            .expect("empty replay sequence is valid");

        assert_eq!(state.lifecycle(), SessionLifecycle::Inactive);
        assert_eq!(state.session_revision(), None);
        assert_eq!(state.current_tick(), None);
    }

    #[test]
    fn replay_envelope_preserves_recorded_event_payload() {
        let operations = [
            BEGIN_240,
            SemanticOperation::AdvanceTick(TickSpan::new(4)),
        ];
        let (_, envelopes) = record_operations(&operations);

        assert_eq!(
            envelopes[0].commit().event(),
            Some(SemanticEvent::SessionBegan {
                revision: REVISION_240,
            })
        );
        assert_eq!(
            envelopes[1].commit().event(),
            Some(SemanticEvent::GameTickAdvanced {
                previous: GameTick::ZERO,
                current: GameTick::new(4),
            })
        );
    }
}
