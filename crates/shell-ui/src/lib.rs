//! `RustClient` application chrome and developer tooling UI.

#![forbid(unsafe_code)]

mod assets;
mod components;
mod model;
pub use assets::OPEN_RS_PNG;
pub use components::Shell;
pub use model::{PluginPanel, SettingView, ShellAction, ShellChannel, ShellModel, SidebarState};

#[cfg(not(target_arch = "wasm32"))]
mod native;
#[cfg(not(target_arch = "wasm32"))]
pub use native::{PanelStats, RetainedPanel};
