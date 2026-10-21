//! Application runtime orchestration shared by native and web shells.
//!
//! Runtime services in this crate coordinate inward-facing semantic systems
//! without becoming alternate authoritative game state.

#![forbid(unsafe_code)]

mod random;

pub use random::{DeterministicRandomSource, RandomSample, RandomSeed, SplitMix64Random};
