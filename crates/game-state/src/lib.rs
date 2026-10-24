//! Authoritative semantic game state and deterministic transitions.
//!
//! `GameState` is the single mutable semantic root. Subsystem projections may
//! cache derived data, but they do not become competing sources of truth.

#![forbid(unsafe_code)]

mod interfaces;
pub use interfaces::{InterfaceError, InterfaceState};
mod inventory;
pub use inventory::{InventoryError, InventoryState};
mod entities;
mod npcs;
pub use npcs::NpcPath;
mod player_movement;
mod players;
mod sequence_scheduler;
pub use entities::{
    EntityUpdateError, Locomotion, NpcEntity, PlayerEntity, WorldEntitiesState, direction_to_delta,
    direction_to_orientation,
};
pub use player_movement::ExactMovement;
pub use players::TrackedPlayer;

mod commit;
mod fingerprint;
mod operation;
mod replay;
mod scenery;
mod state;
pub use scenery::{LocationOverride, SceneryError, SceneryOperation, SceneryState};
mod title;

pub use commit::{OperationCommit, SemanticEvent, StateDelta};
pub use fingerprint::{
    CommitStreamFingerprint, CommitStreamFingerprintBuilder, SEMANTIC_FINGERPRINT_VERSION,
    StateFingerprint,
};
pub use operation::{OperationBatchError, OperationError, SemanticOperation};
pub use replay::{ReplayEnvelope, ReplayError, ReplayFailure};
pub use state::{GameState, LifecycleError, SessionLifecycle, TickAdvanceError};

pub use title::{LoginFailure, LoginStatus, TitleInformation, TitleScreen, TitleTransitionError};

mod connection;
pub use connection::{AuthenticatedPlayer, ConnectionStateError, InitialPlacement};

mod chat;
pub use chat::{ChatEntry, ChatError, ChatState};