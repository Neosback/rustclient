//! Retained, host-rendered plugin UI. No toolkit, platform or mutable client handles cross here.
use std::sync::Arc;

/// Owned navigation assets supported by the shared shell.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum PanelIcon {
    /// `RuneLite` Notes navigation icon.
    Notes,
    /// `RuneLite` XP Tracker navigation icon.
    XpTracker,
}

/// One flat block in a retained panel; stable ids name interactive blocks.
#[derive(Debug, Clone, PartialEq, Eq)]
pub enum PanelBlock {
    /// Wrapping, selectable text.
    Text(String),
    /// A label and value row.
    Metric { label: String, value: String },
    /// A user-initiated command.
    Button { id: String, label: String },
    /// A word-wrapping editor. Commit on focus loss; drafts belong to the shell.
    Editor {
        id: String,
        text: String,
        max_bytes: u32,
    },
}

/// Immutable content reused until its actual presentation dependencies change.
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct PluginPanel {
    /// Stable within the executing plugin.
    pub id: String,
    /// Nonzero content revision, checked on every returned action.
    pub revision: u64,
    /// Navigation tooltip and accessible title.
    pub title: String,
    /// Source navigation priority; lower values appear first.
    pub priority: i32,
    /// Supported, owned asset.
    pub icon: PanelIcon,
    /// Display order, capped by the host.
    pub blocks: Arc<[PanelBlock]>,
}

/// An interaction with content from a particular immutable panel revision.
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct PanelEvent {
    pub panel: String,
    pub revision: u64,
    pub element: String,
    pub value: PanelValue,
}

/// An action cannot smuggle an arbitrary client command through a UI control.
#[derive(Debug, Clone, PartialEq, Eq)]
pub enum PanelValue {
    Activate,
    Text(String),
}

/// Bounds shared by producers, host validation and shell editors.
pub mod panel_limits {
    pub const PANELS: usize = 4;
    pub const BLOCKS: usize = 128;
    pub const TEXT_BYTES: usize = 65_536;
    pub const ID_BYTES: usize = 64;
}
