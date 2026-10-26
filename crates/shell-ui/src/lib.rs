//! `RustClient` application chrome and developer tooling UI.

#![forbid(unsafe_code)]

mod components;
mod model;
pub use components::Shell;
pub use model::{PluginPanel, SettingView, ShellAction, ShellChannel, ShellModel};

#[cfg(not(target_arch = "wasm32"))]
mod native;
#[cfg(not(target_arch = "wasm32"))]
pub use native::{PanelStats, RetainedPanel};
