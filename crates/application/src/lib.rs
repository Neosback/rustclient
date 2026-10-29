//! Application runtime orchestration shared by native and web shells.
//!
//! Runtime services in this crate coordinate inward-facing semantic systems
//! without becoming alternate authoritative game state.

#![forbid(unsafe_code)]

pub mod builtin_config;
mod interaction;
pub mod shell_preferences;
pub use interaction::{validate_walk, validate_world_action, world_actions};

mod random;

pub use random::{DeterministicRandomSource, RandomSample, RandomSeed, SplitMix64Random};

mod title;
pub use title::{load_title, load_title_preferences, save_title_preferences};

pub mod cycle;

mod connection;
pub use connection::{ConnectionDecoder, ConnectionInput};

mod sequence_policy;
pub use sequence_policy::SequencePolicyResolver;

mod scene;
pub use scene::{
    LoadedScene, load_scene, load_scene_from_cache, load_scene_with_inputs, reproject_scene,
    reproject_scene_from_cache,
};

pub mod scripts;

mod widget_actions;
pub use widget_actions::{validate_widget_action, widget_actions};
