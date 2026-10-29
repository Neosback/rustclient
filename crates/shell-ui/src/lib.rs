//! `RustClient` application chrome and developer tooling UI.

#![forbid(unsafe_code)]

mod assets;
mod model;
pub use assets::OPEN_RS_PNG;
pub use model::{PluginPanel, SettingView, ShellAction, ShellChannel, ShellModel, SidebarState};

mod egui_panel;
pub use egui_panel::{EguiPanel, EguiPanelFrame, EguiPanelStats};

mod metrics;
pub use metrics::SidebarMetrics;
