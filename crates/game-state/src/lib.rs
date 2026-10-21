//! Authoritative semantic game state and deterministic transitions.
//!
//! `GameState` is the single mutable semantic root. Subsystem projections may
//! cache derived data, but they do not become competing sources of truth.

#![forbid(unsafe_code)]

mod commit;
mod operation;
mod replay;
mod state;

pub use commit::{OperationCommit, SemanticEvent, StateDelta};
pub use operation::{OperationBatchError, OperationError, SemanticOperation};
pub use replay::{ReplayEnvelope, ReplayError, ReplayFailure};
pub use state::{GameState, LifecycleError, SessionLifecycle, TickAdvanceError};
