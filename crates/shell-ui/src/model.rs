//! Presentation snapshots and typed user actions. Accepted settings stay in plugin-host.
use rustclient_plugin_host::configuration::{ConfigValue, Configuration, Setting};
use std::{
    cell::{Cell, RefCell},
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
    pub required: bool,
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
            required: true,
            settings: schema
                .settings
                .iter()
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
}
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct ShellModel {
    pub plugins: Vec<PluginPanel>,
    pub error: Option<String>,
    pub panel_visible: bool,
}
impl Default for ShellModel {
    fn default() -> Self {
        Self {
            plugins: Vec::new(),
            error: None,
            panel_visible: true,
        }
    }
}

#[derive(Clone, Default)]
pub struct ShellChannel {
    pub model: Rc<RefCell<ShellModel>>,
    actions: Rc<RefCell<Vec<ShellAction>>>,
    /// Native toolkit lacks element-relative mouse coordinates; the retained DOM adapter supplies them.
    pub(crate) pointer_origin_x: Rc<Cell<Option<f64>>>,
}
impl PartialEq for ShellChannel {
    fn eq(&self, other: &Self) -> bool {
        Rc::ptr_eq(&self.model, &other.model)
    }
}
impl ShellChannel {
    pub fn send(&self, action: ShellAction) {
        self.actions.borrow_mut().push(action);
    }
    #[must_use]
    pub fn drain(&self) -> Vec<ShellAction> {
        std::mem::take(&mut *self.actions.borrow_mut())
    }
}
