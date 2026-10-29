//! `RustClient` application chrome and developer tooling UI.

#![forbid(unsafe_code)]

mod assets;
mod components;
mod model;
pub use assets::OPEN_RS_PNG;
pub use components::Shell;
pub use model::{PluginPanel, SettingView, ShellAction, ShellChannel, ShellModel, SidebarState};

#[cfg(all(not(target_arch = "wasm32"), feature = "legacy-native"))]
mod native;
#[cfg(all(not(target_arch = "wasm32"), feature = "legacy-native"))]
pub use native::{PanelStats, RetainedPanel};
mod egui_panel;
pub use egui_panel::{EguiPanel, EguiPanelFrame, EguiPanelStats};
