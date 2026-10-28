//! Presentation snapshots and typed user actions. Accepted settings stay in plugin-host.
use rustclient_plugin_host::configuration::{ConfigValue, Configuration, ENABLED_KEY, Setting};
use std::{
    cell::{Cell, RefCell},
    collections::BTreeSet,
    rc::Rc,
};

#[derive(Debug, Clone, PartialEq, Eq)]
pub struct SettingView {
    pub descriptor: Setting,
    pub value: ConfigValue,
    /// A missing effect is shown explicitly and cannot produce an effective-looking control.
    pub unavailable: Option<String>,
    /// Information about the effective behavior (for example a hardware limit); the control stays usable.
    pub note: Option<String>,
}
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct PluginPanel {
    pub id: String,
    pub name: String,
    pub description: String,
    pub tags: Vec<String>,
    /// Required services are always on and have no switch.
    pub required: bool,
    /// Whether an optional plugin is switched on; always true for required services.
    pub enabled: bool,
    /// Every setting except the plugin's own on/off switch.
    pub settings: Vec<SettingView>,
}
impl PluginPanel {
    pub fn project(
        configuration: &Configuration,
        availability: impl Fn(&str) -> Option<String>,
    ) -> Self {
        let schema = configuration.schema();
        Self {
            id: schema.plugin.to_string(),
            name: schema.name.clone(),
            description: schema.description.clone(),
            tags: schema.tags.clone(),
            required: schema.required,
            enabled: configuration.enabled(),
            settings: schema
                .settings
                .iter()
                .filter(|descriptor| schema.required || descriptor.key != ENABLED_KEY)
                .map(|descriptor| SettingView {
                    value: configuration
                        .value(&descriptor.key)
                        .unwrap_or(&descriptor.default)
                        .clone(),
                    unavailable: availability(&descriptor.key),
                    note: None,
                    descriptor: descriptor.clone(),
                })
                .collect(),
        }
    }
}
impl PluginPanel {
    /// Attach effective-behavior notes (hardware limits, deviations) to settings that have one.
    #[must_use]
    pub fn with_notes(mut self, note: impl Fn(&str) -> Option<String>) -> Self {
        for setting in &mut self.settings {
            setting.note = note(&setting.descriptor.key);
        }
        self
    }
}
#[derive(Debug, Clone, PartialEq, Eq)]
pub enum ShellAction {
    Configure {
        plugin: String,
        key: String,
        value: ConfigValue,
    },
    Reset {
        plugin: String,
    },
    Close,
    Open,
    ToggleSidebar,
    TogglePanel,
    ToggleFavorite {
        plugin: String,
    },
}
/// The one presentation owner of both the rail and panel visibility.
#[derive(Debug, Default, Clone, Copy, PartialEq, Eq)]
pub enum SidebarState {
    #[default]
    Hidden,
    Rail,
    Panel,
}
impl SidebarState {
    #[must_use]
    pub const fn width(self) -> u32 {
        match self {
            Self::Hidden => 0,
            Self::Rail => 36,
            Self::Panel => 278,
        }
    }
}

#[derive(Debug, Clone, PartialEq, Eq)]
pub struct ShellModel {
    pub plugins: Vec<PluginPanel>,
    pub error: Option<String>,
    pub sidebar: SidebarState,
    pub pinned: BTreeSet<String>,
}
impl Default for ShellModel {
    fn default() -> Self {
        Self {
            plugins: Vec::new(),
            error: None,
            sidebar: SidebarState::Hidden,
            pinned: BTreeSet::new(),
        }
    }
}

#[derive(Clone, PartialEq)]
pub(crate) struct Tooltip {
    pub text: String,
    pub left: f32,
    pub top: f32,
}

#[derive(Clone, Default)]
pub struct ShellChannel {
    pub model: Rc<RefCell<ShellModel>>,
    actions: Rc<RefCell<Vec<ShellAction>>>,
    pub(crate) tooltip: Rc<Cell<Option<dioxus::prelude::Signal<Option<Tooltip>>>>>,
    drag_cancel: Rc<RefCell<Option<dioxus::prelude::EventHandler<()>>>>,
    drag_commit: Rc<RefCell<Option<dioxus::prelude::EventHandler<()>>>>,
    pub(crate) sections: Rc<RefCell<std::collections::BTreeMap<String, bool>>>,
    /// Native toolkit lacks element-relative mouse coordinates; the retained DOM adapter supplies them.
    #[cfg(not(target_arch = "wasm32"))]
    pub(crate) pointer_origin_x: Rc<Cell<Option<f64>>>,
}
impl PartialEq for ShellChannel {
    fn eq(&self, other: &Self) -> bool {
        Rc::ptr_eq(&self.model, &other.model)
    }
}
impl ShellChannel {
    pub(crate) fn start_drag(
        &self,
        cancel: dioxus::prelude::EventHandler<()>,
        commit: dioxus::prelude::EventHandler<()>,
    ) {
        self.cancel_drag();
        *self.drag_cancel.borrow_mut() = Some(cancel);
        *self.drag_commit.borrow_mut() = Some(commit);
    }
    pub(crate) fn cancel_drag(&self) {
        let cancel = self.drag_cancel.borrow_mut().take();
        self.drag_commit.borrow_mut().take();
        if let Some(cancel) = cancel {
            cancel.call(());
        }
    }
    pub(crate) fn finish_drag(&self) {
        let commit = self.drag_commit.borrow_mut().take();
        self.drag_cancel.borrow_mut().take();
        if let Some(commit) = commit {
            commit.call(());
        }
    }

    pub fn send(&self, action: ShellAction) {
        self.actions.borrow_mut().push(action);
    }
    #[must_use]
    pub fn drain(&self) -> Vec<ShellAction> {
        std::mem::take(&mut *self.actions.borrow_mut())
    }
}

#[cfg(test)]
mod sidebar_defaults {
    use super::*;
    #[test]
    fn shared_startup_default_is_hidden() {
        assert_eq!(SidebarState::default(), SidebarState::Hidden);
        assert_eq!(ShellModel::default().sidebar, SidebarState::Hidden);
        assert_eq!(SidebarState::Hidden.width(), 0);
        assert_eq!(SidebarState::Rail.width(), 36);
        assert_eq!(SidebarState::Panel.width(), 278);
    }
}
