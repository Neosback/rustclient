//! Instance lifecycle is independent of session lifecycle and accepted configuration.
use crate::{FaultKind, HostView, LoadedPlugin, LogSink, PluginCall, PluginFault, PluginStorage};
use rustclient_plugin_api::{LogLevel, PluginOutput};

#[derive(Debug, Clone, Copy, PartialEq, Eq, Default)]
pub enum LifecycleStatus {
    #[default]
    Stopped,
    Starting,
    Running,
    Stopping,
    Faulted,
}
/// Host-local incarnation, never a guest ABI or game-state identity.
#[derive(Debug, Clone, Copy, PartialEq, Eq, Hash, Default)]
pub struct ActivationId {
    pub(crate) registration: u64,
    pub(crate) generation: u64,
}
#[derive(Debug, Clone, Copy)]
pub(crate) enum Phase {
    Session,
    Game,
    Overlay,
    Output,
    Ui,
}
impl Phase {
    const fn index(self) -> usize {
        self as usize
    }
}

#[derive(Default)]
pub(crate) struct Lifecycle {
    pub status: LifecycleStatus,
    pub activation: ActivationId,
    pub streaks: [u32; 5],
    pub error: Option<PluginFault>,
    pub output: Option<(ActivationId, PluginOutput)>,
    pub panels: Vec<crate::HostedPanel>,
    pub panel_sequence: u64,
}
impl LoadedPlugin {
    #[must_use]
    pub const fn status(&self) -> LifecycleStatus {
        self.lifecycle.status
    }
    #[must_use]
    pub const fn configured_enabled(&self) -> bool {
        self.enabled
    }
    #[must_use]
    pub fn running(&self) -> bool {
        self.enabled && self.status() == LifecycleStatus::Running
    }
    #[must_use]
    pub const fn activation(&self) -> ActivationId {
        self.lifecycle.activation
    }
    #[must_use]
    pub fn accepts_activation(&self, activation: ActivationId) -> bool {
        self.running() && self.activation() == activation
    }
    #[must_use]
    pub fn last_fault(&self) -> Option<&PluginFault> {
        self.lifecycle.error.as_ref()
    }
    pub(crate) fn wants_game_events(&self) -> bool {
        !self.subscription.interest.is_empty()
    }
}

fn call<T>(
    plugin: &mut LoadedPlugin,
    view: &dyn HostView,
    storage: &mut dyn PluginStorage,
    logs: &mut dyn LogSink,
    action: impl FnOnce(&mut dyn crate::PluginInstance, &mut PluginCall<'_>) -> Result<T, PluginFault>,
) -> Result<T, PluginFault> {
    let LoadedPlugin {
        manifest,
        instance,
        config,
        denied,
        ..
    } = plugin;
    action(
        instance.as_mut(),
        &mut PluginCall {
            plugin: &manifest.id,
            capabilities: &manifest.capabilities,
            view,
            storage,
            logs,
            config,
            denied,
        },
    )
}
pub(crate) fn start(
    plugin: &mut LoadedPlugin,
    view: &dyn HostView,
    storage: &mut dyn PluginStorage,
    logs: &mut dyn LogSink,
) -> Result<(), PluginFault> {
    if !plugin.enabled || plugin.status() == LifecycleStatus::Running {
        return Ok(());
    }
    let next = plugin
        .lifecycle
        .activation
        .generation
        .checked_add(1)
        .ok_or_else(|| {
            PluginFault::new(FaultKind::Contract, "plugin activation identity exhausted")
        })?;
    plugin.lifecycle.activation.generation = next;
    plugin.lifecycle.status = LifecycleStatus::Starting;
    plugin.lifecycle.streaks = [0; 5];
    plugin.lifecycle.output = None;
    plugin.lifecycle.panels.clear();
    match call(plugin, view, storage, logs, |instance, call| {
        instance.init(call)
    }) {
        Ok(()) => {
            plugin.lifecycle.status = LifecycleStatus::Running;
            plugin.lifecycle.error = None;
            Ok(())
        }
        Err(error) => {
            logs.log(&plugin.manifest.id, LogLevel::Error, &error.to_string());
            let _ = stop(plugin, view, storage, logs);
            plugin.lifecycle.status = LifecycleStatus::Faulted;
            plugin.lifecycle.error = Some(error.clone());
            Err(error)
        }
    }
}
pub(crate) fn stop(
    plugin: &mut LoadedPlugin,
    view: &dyn HostView,
    storage: &mut dyn PluginStorage,
    logs: &mut dyn LogSink,
) -> Result<(), PluginFault> {
    plugin.lifecycle.output = None;
    plugin.lifecycle.panels.clear();
    if !matches!(
        plugin.status(),
        LifecycleStatus::Starting | LifecycleStatus::Running
    ) {
        return Ok(());
    }
    // Revoke dispatch/output before executing potentially failing cleanup.
    plugin.lifecycle.status = LifecycleStatus::Stopping;
    let result = call(plugin, view, storage, logs, |instance, call| {
        instance.shutdown(call)
    });
    plugin.lifecycle.status = LifecycleStatus::Stopped;
    if let Err(error) = &result {
        logs.log(
            &plugin.manifest.id,
            LogLevel::Error,
            &format!("plugin cleanup: {error}"),
        );
        plugin.lifecycle.error = Some(error.clone());
        plugin.lifecycle.status = LifecycleStatus::Faulted;
    }
    result
}
pub(crate) fn invoke<T>(
    plugin: &mut LoadedPlugin,
    phase: Phase,
    view: &dyn HostView,
    storage: &mut dyn PluginStorage,
    logs: &mut dyn LogSink,
    fault_limit: u32,
    action: impl FnOnce(&mut dyn crate::PluginInstance, &mut PluginCall<'_>) -> Result<T, PluginFault>,
) -> Option<T> {
    if !plugin.enabled {
        return None;
    }
    // Retain the register-then-dispatch convenience, but never invoke an uninitialized instance.
    if plugin.status() == LifecycleStatus::Stopped && start(plugin, view, storage, logs).is_err() {
        return None;
    }
    if !plugin.running() {
        return None;
    }
    match call(plugin, view, storage, logs, action) {
        Ok(value) => {
            plugin.lifecycle.streaks[phase.index()] = 0;
            Some(value)
        }
        Err(error) => {
            let failures = plugin.lifecycle.streaks[phase.index()].saturating_add(1);
            plugin.lifecycle.streaks[phase.index()] = failures;
            logs.log(&plugin.manifest.id, LogLevel::Error, &error.to_string());
            plugin.lifecycle.error = Some(error.clone());
            plugin.lifecycle.output = None;
            plugin.lifecycle.panels.clear();
            if failures >= fault_limit.max(1) {
                let _ = stop(plugin, view, storage, logs);
                plugin.lifecycle.error = Some(error);
                plugin.lifecycle.status = LifecycleStatus::Faulted;
            }
            None
        }
    }
}
