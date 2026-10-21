//! Authoritative semantic game state and deterministic transitions.
//!
//! `GameState` is the single mutable semantic root. Subsystem projections may
//! cache derived data, but they do not become competing sources of truth.

#![forbid(unsafe_code)]

mod state;

pub use state::{GameState, LifecycleError, SessionLifecycle};
