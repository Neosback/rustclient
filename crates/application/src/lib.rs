//! Application runtime orchestration shared by native and web shells.
//!
//! Runtime services in this crate coordinate inward-facing semantic systems
//! without becoming alternate authoritative game state.

#![forbid(unsafe_code)]

mod random;

pub use random::{DeterministicRandomSource, RandomSample, RandomSeed, SplitMix64Random};

mod title;
pub use title::{load_title, load_title_preferences, save_title_preferences};

mod connection;
pub use connection::ConnectionDecoder;

mod scene;
pub use scene::{LoadedScene, load_scene, reproject_scene};
