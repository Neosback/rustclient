//! Plugin lifecycle, capabilities, mediation, budgets, and fault isolation.
//!
//! Platform-neutral: runtime adapters (`rustclient-plugin-runtime-native`,
//! `rustclient-plugin-runtime-web`) implement [`PluginInstance`]; this crate owns the contract
//! between committed game state, grants, plugin-local storage and plugin executions.
//!
//! Plugins never receive `GameState`, platform handles or mutable internals. They see an
//! approved [`HostView`] projection, own [`PluginStorage`], and produce overlay commands.

#![forbid(unsafe_code)]

pub mod configuration;
pub mod required;
mod v2;

pub use configuration::PluginConfig;
pub use v2::{CapabilityDenied, HostOutput, SnapshotView};

use rustclient_plugin_api::{
    Capability, CapabilitySet, GameEvent, GameSnapshot, LogLevel, OverlayLine, PlayerPosition,
    PluginApiVersion, PluginEvent, PluginId, PluginManifest, PluginOutput,
};
use std::collections::{BTreeMap, BTreeSet};

/// Plugin-local persisted key/value storage, namespaced by plugin identity.
///
/// Values are loaded as a snapshot before a plugin call and saved after it, so runtimes can
/// keep all per-call state owned.
pub trait PluginStorage {
    /// Load the full current namespace for `plugin`.
    fn load(&self, plugin: &PluginId) -> BTreeMap<String, String>;
    /// Replace the full namespace for `plugin`.
    fn save(&mut self, plugin: &PluginId, values: &BTreeMap<String, String>);
}

/// In-memory storage for tests, probes and browser fallbacks.
#[derive(Debug, Default)]
pub struct MemoryStorage(BTreeMap<PluginId, BTreeMap<String, String>>);

impl MemoryStorage {
    /// Empty storage.
    #[must_use]
    pub fn new() -> Self {
        Self::default()
    }
}

impl PluginStorage for MemoryStorage {
    fn load(&self, plugin: &PluginId) -> BTreeMap<String, String> {
        self.0.get(plugin).cloned().unwrap_or_default()
    }

    fn save(&mut self, plugin: &PluginId, values: &BTreeMap<String, String>) {
        self.0.insert(plugin.clone(), values.clone());
    }
}

/// JSON-file storage rooted at a directory, one folder per plugin.
#[cfg(not(target_arch = "wasm32"))]
#[derive(Debug)]
pub struct DirectoryStorage {
    root: std::path::PathBuf,
}

#[cfg(not(target_arch = "wasm32"))]
impl DirectoryStorage {
    /// Use `root` as the plugin configuration root.
    #[must_use]
    pub fn new(root: std::path::PathBuf) -> Self {
        Self { root }
    }

    fn path_for(&self, plugin: &PluginId) -> std::path::PathBuf {
        self.root.join(plugin.as_str()).join("config.json")
    }
}

#[cfg(not(target_arch = "wasm32"))]
impl PluginStorage for DirectoryStorage {
    fn load(&self, plugin: &PluginId) -> BTreeMap<String, String> {
        let path = self.path_for(plugin);
        let Ok(text) = std::fs::read_to_string(&path) else {
            return BTreeMap::new();
        };
        serde_json::from_str(&text).unwrap_or_default()
    }

    fn save(&mut self, plugin: &PluginId, values: &BTreeMap<String, String>) {
        let path = self.path_for(plugin);
        let Some(parent) = path.parent() else {
            return;
        };
        if std::fs::create_dir_all(parent).is_err() {
            return;
        }
        if let Ok(text) = serde_json::to_string_pretty(values) {
            let _ = std::fs::write(path, text);
        }
    }
}

/// Approved projection of committed game state visible during one plugin call.
pub trait HostView {
    /// Committed local player position, absent before a scene is loaded.
    fn player_position(&self) -> Option<PlayerPosition>;
    /// Current committed gameplay tick.
    fn tick(&self) -> u64;
    /// Consistent snapshot of the committed game (API 0.2); `None` for 0.1-only views.
    ///
    /// Reads through this must go via [`PluginCall`] accessors, which enforce grants.
    fn game(&self) -> Option<&GameSnapshot> {
        None
    }
}

/// A fixed view snapshot, used by probes and tests.
#[derive(Debug, Default, Clone, Copy)]
pub struct StaticView {
    /// Value returned by [`HostView::player_position`].
    pub position: Option<PlayerPosition>,
    /// Value returned by [`HostView::tick`].
    pub tick: u64,
}

impl HostView for StaticView {
    fn player_position(&self) -> Option<PlayerPosition> {
        self.position
    }

    fn tick(&self) -> u64 {
        self.tick
    }
}

/// Destination for plugin diagnostics.
pub trait LogSink {
    /// Record one diagnostic line attributed to `plugin`.
    fn log(&mut self, plugin: &PluginId, level: LogLevel, message: &str);
}

/// One captured diagnostic line.
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct LogRecord {
    /// Originating plugin.
    pub plugin: PluginId,
    /// Severity.
    pub level: LogLevel,
    /// Message text.
    pub message: String,
}

/// In-memory log sink.
#[derive(Debug, Default)]
pub struct MemoryLogSink {
    /// Captured lines in arrival order.
    pub records: Vec<LogRecord>,
}

impl LogSink for MemoryLogSink {
    fn log(&mut self, plugin: &PluginId, level: LogLevel, message: &str) {
        self.records.push(LogRecord {
            plugin: plugin.clone(),
            level,
            message: message.to_owned(),
        });
    }
}

/// Diagnostic sink that writes attributed lines to stderr.
#[cfg(not(target_arch = "wasm32"))]
#[derive(Debug, Default)]
pub struct StderrLogSink;

#[cfg(not(target_arch = "wasm32"))]
impl LogSink for StderrLogSink {
    fn log(&mut self, plugin: &PluginId, level: LogLevel, message: &str) {
        eprintln!("plugin {plugin} [{}]: {message}", level.as_str());
    }
}

/// Everything a plugin instance may legally observe or change during one call.
pub struct PluginCall<'a> {
    /// Executing plugin identity.
    pub plugin: &'a PluginId,
    /// Operator grants for this package.
    pub capabilities: &'a CapabilitySet,
    /// Approved state projection.
    pub view: &'a dyn HostView,
    /// Plugin-local storage.
    pub storage: &'a mut dyn PluginStorage,
    /// Diagnostic sink.
    pub logs: &'a mut dyn LogSink,
    /// Effective settings of this plugin, defaults applied.
    pub config: &'a PluginConfig,
    /// Capabilities already reported as denied, so diagnostics are not repeated every call.
    denied: &'a mut BTreeSet<Capability>,
}

impl PluginCall<'_> {
    /// Whether `capability` is granted for this plugin.
    #[must_use]
    pub fn granted(&self, capability: Capability) -> bool {
        self.capabilities.contains(capability)
    }
}

/// One loaded plugin execution.
pub trait PluginInstance {
    /// Initialize after grants, before any event.
    ///
    /// # Errors
    /// Runtime or contract failures described by [`PluginFault`].
    fn init(&mut self, call: &mut PluginCall<'_>) -> Result<(), PluginFault>;

    /// Receive one committed event batch.
    ///
    /// # Errors
    /// Runtime or contract failures described by [`PluginFault`].
    fn on_event(
        &mut self,
        call: &mut PluginCall<'_>,
        events: &[PluginEvent],
    ) -> Result<(), PluginFault>;

    /// Overlay commands valid until the next event batch.
    ///
    /// # Errors
    /// Runtime or contract failures described by [`PluginFault`].
    fn overlay(&mut self, call: &mut PluginCall<'_>) -> Result<Vec<OverlayLine>, PluginFault>;

    /// Receive typed gameplay events (API 0.2), already filtered to the plugin's grants.
    ///
    /// The default ignores them, so 0.1 plugins are unaffected.
    ///
    /// # Errors
    /// Runtime or contract failures described by [`PluginFault`].
    fn on_game_events(
        &mut self,
        call: &mut PluginCall<'_>,
        events: &[GameEvent],
    ) -> Result<(), PluginFault> {
        let _ = (call, events);
        Ok(())
    }

    /// Declarative output for this update (API 0.2). The default asks for nothing.
    ///
    /// # Errors
    /// Runtime or contract failures described by [`PluginFault`].
    fn output(&mut self, call: &mut PluginCall<'_>) -> Result<PluginOutput, PluginFault> {
        let _ = call;
        Ok(PluginOutput::default())
    }
}

/// Why a plugin call failed.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum FaultKind {
    /// The component could not be compiled or instantiated.
    Instantiate,
    /// Initialization failed.
    Init,
    /// Event handling failed.
    Event,
    /// Overlay production failed.
    Overlay,
    /// The plugin exceeded its CPU budget.
    Budget,
    /// The package contract was violated.
    Contract,
}

/// A diagnosable plugin failure.
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct PluginFault {
    /// Failure category.
    pub kind: FaultKind,
    /// Human-readable context; never contains credentials.
    pub message: String,
}

impl PluginFault {
    /// Construct a fault.
    #[must_use]
    pub fn new(kind: FaultKind, message: impl Into<String>) -> Self {
        Self {
            kind,
            message: message.into(),
        }
    }
}

impl std::fmt::Display for PluginFault {
    fn fmt(&self, formatter: &mut std::fmt::Formatter<'_>) -> std::fmt::Result {
        write!(formatter, "{:?}: {}", self.kind, self.message)
    }
}

impl std::error::Error for PluginFault {}

/// One plugin package together with its live execution.
pub struct LoadedPlugin {
    /// Validated package manifest.
    pub manifest: PluginManifest,
    instance: Box<dyn PluginInstance>,
    faults: u32,
    enabled: bool,
    required: bool,
    config: PluginConfig,
    denied: BTreeSet<Capability>,
}

impl LoadedPlugin {
    /// Required application services cannot be disabled or unregistered.
    #[must_use]
    pub fn required(&self) -> bool {
        self.required
    }
    /// Whether the plugin is still scheduled.
    #[must_use]
    pub fn enabled(&self) -> bool {
        self.enabled
    }

    /// Consecutive faults recorded for this plugin.
    #[must_use]
    pub fn faults(&self) -> u32 {
        self.faults
    }
}

/// A registered set of plugins with mediation and fault isolation.
pub struct PluginSet {
    plugins: Vec<LoadedPlugin>,
    fault_limit: u32,
}

impl Default for PluginSet {
    fn default() -> Self {
        Self::new()
    }
}

impl PluginSet {
    /// Empty set; a plugin is disabled after three consecutive faults.
    #[must_use]
    pub fn new() -> Self {
        Self {
            plugins: Vec::new(),
            fault_limit: 3,
        }
    }

    /// Empty set with an explicit fault limit.
    #[must_use]
    pub fn with_fault_limit(fault_limit: u32) -> Self {
        Self {
            plugins: Vec::new(),
            fault_limit,
        }
    }

    /// Register one loaded plugin after API compatibility checks.
    ///
    /// # Errors
    /// Rejects packages that do not declare a compatible plugin API version.
    pub fn register(
        &mut self,
        manifest: PluginManifest,
        instance: Box<dyn PluginInstance>,
    ) -> Result<(), PluginFault> {
        if self
            .plugins
            .iter()
            .any(|plugin| plugin.manifest.id == manifest.id)
        {
            return Err(PluginFault::new(
                FaultKind::Contract,
                format!("duplicate plugin {}", manifest.id),
            ));
        }
        manifest
            .check_api(PluginApiVersion::current())
            .map_err(|error| PluginFault::new(FaultKind::Contract, error.to_string()))?;
        self.plugins.push(LoadedPlugin {
            manifest,
            instance,
            faults: 0,
            enabled: true,
            required: false,
            config: PluginConfig::default(),
            denied: BTreeSet::new(),
        });
        Ok(())
    }

    /// Replace a plugin's effective settings and switch it on or off.
    ///
    /// A plugin that becomes enabled is initialized again so it starts from a clean state. A
    /// plugin the host disabled after repeated faults is only re-enabled by this call.
    ///
    /// # Errors
    /// Unknown plugins, and attempts to disable a required service.
    pub fn configure(
        &mut self,
        id: &PluginId,
        config: PluginConfig,
        enabled: bool,
        view: &dyn HostView,
        storage: &mut dyn PluginStorage,
        logs: &mut dyn LogSink,
    ) -> Result<(), PluginFault> {
        let index = self
            .plugins
            .iter()
            .position(|plugin| plugin.manifest.id == *id)
            .ok_or_else(|| PluginFault::new(FaultKind::Contract, format!("unknown plugin {id}")))?;
        let fault_limit = self.fault_limit;
        let plugin = &mut self.plugins[index];
        if plugin.required && !enabled {
            return Err(PluginFault::new(
                FaultKind::Contract,
                format!("{id} is required"),
            ));
        }
        let turned_on = enabled && !plugin.enabled;
        plugin.config = config;
        plugin.enabled = enabled;
        if turned_on {
            plugin.faults = 0;
            let _ = Self::invoke(
                plugin,
                view,
                storage,
                logs,
                fault_limit,
                |instance, call| instance.init(call),
            );
        }
        Ok(())
    }

    /// Number of registered plugins.
    #[must_use]
    pub fn len(&self) -> usize {
        self.plugins.len()
    }

    /// Whether no plugin is registered.
    #[must_use]
    pub fn is_empty(&self) -> bool {
        self.plugins.is_empty()
    }

    /// Registered plugins in load order.
    pub fn iter(&self) -> impl Iterator<Item = &LoadedPlugin> {
        self.plugins.iter()
    }

    /// Initialize every enabled plugin.
    pub fn init(
        &mut self,
        view: &dyn HostView,
        storage: &mut dyn PluginStorage,
        logs: &mut dyn LogSink,
    ) {
        for index in 0..self.plugins.len() {
            let fault_limit = self.fault_limit;
            let plugin = &mut self.plugins[index];
            let _ = Self::invoke(
                plugin,
                view,
                storage,
                logs,
                fault_limit,
                |instance, call| instance.init(call),
            );
        }
    }

    /// Deliver one event batch to every enabled plugin.
    pub fn dispatch(
        &mut self,
        events: &[PluginEvent],
        view: &dyn HostView,
        storage: &mut dyn PluginStorage,
        logs: &mut dyn LogSink,
    ) {
        for index in 0..self.plugins.len() {
            let fault_limit = self.fault_limit;
            let plugin = &mut self.plugins[index];
            let _ = Self::invoke(
                plugin,
                view,
                storage,
                logs,
                fault_limit,
                |instance, call| instance.on_event(call, events),
            );
        }
    }

    /// Collect overlay commands from every enabled plugin.
    pub fn overlay(
        &mut self,
        view: &dyn HostView,
        storage: &mut dyn PluginStorage,
        logs: &mut dyn LogSink,
    ) -> Vec<OverlayLine> {
        let mut lines = Vec::new();
        for index in 0..self.plugins.len() {
            let fault_limit = self.fault_limit;
            let plugin = &mut self.plugins[index];
            if let Some(mut produced) = Self::invoke(
                plugin,
                view,
                storage,
                logs,
                fault_limit,
                |instance, call| instance.overlay(call),
            ) {
                lines.append(&mut produced);
            }
        }
        lines
    }

    /// Invoke `action` on one plugin, isolating and recording failures.
    ///
    /// `view`, `storage` and `logs` may be touched only through the mediated [`PluginCall`].
    fn invoke<T>(
        plugin: &mut LoadedPlugin,
        view: &dyn HostView,
        storage: &mut dyn PluginStorage,
        logs: &mut dyn LogSink,
        fault_limit: u32,
        action: impl FnOnce(&mut dyn PluginInstance, &mut PluginCall<'_>) -> Result<T, PluginFault>,
    ) -> Option<T> {
        if !plugin.enabled {
            return None;
        }
        let result = {
            let LoadedPlugin {
                manifest,
                instance,
                denied,
                config,
                ..
            } = plugin;
            let mut call = PluginCall {
                plugin: &manifest.id,
                capabilities: &manifest.capabilities,
                view,
                storage,
                logs,
                config,
                denied,
            };
            action(instance.as_mut(), &mut call)
        };
        match result {
            Ok(value) => {
                plugin.faults = 0;
                Some(value)
            }
            Err(fault) => {
                plugin.faults = plugin.faults.saturating_add(1);
                let id = &plugin.manifest.id;
                let level = if fault.kind == FaultKind::Budget {
                    LogLevel::Warn
                } else {
                    LogLevel::Error
                };
                logs.log(id, level, &fault.to_string());
                if plugin.faults >= fault_limit && plugin.enabled && !plugin.required {
                    plugin.enabled = false;
                    logs.log(id, LogLevel::Warn, "plugin disabled after repeated faults");
                }
                None
            }
        }
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use rustclient_plugin_api::{Capability, PluginApiVersion, PluginId};

    struct Stub {
        seen_ticks: u64,
        fail: bool,
    }

    impl PluginInstance for Stub {
        fn init(&mut self, _call: &mut PluginCall<'_>) -> Result<(), PluginFault> {
            Ok(())
        }

        fn on_event(
            &mut self,
            _call: &mut PluginCall<'_>,
            events: &[PluginEvent],
        ) -> Result<(), PluginFault> {
            for event in events {
                if let PluginEvent::Tick(tick) = event {
                    self.seen_ticks = *tick;
                }
            }
            if self.fail {
                return Err(PluginFault::new(FaultKind::Event, "stub failure"));
            }
            Ok(())
        }

        fn overlay(&mut self, call: &mut PluginCall<'_>) -> Result<Vec<OverlayLine>, PluginFault> {
            if !call.granted(Capability::UiOverlay) {
                return Ok(Vec::new());
            }
            Ok(vec![OverlayLine {
                text: format!("tick {}", self.seen_ticks),
                x: 1,
                y: 2,
                argb: 0xFFFF_FFFF,
            }])
        }
    }

    fn manifest(id: &str, capabilities: &[Capability]) -> PluginManifest {
        PluginManifest {
            id: PluginId::parse(id).expect("valid id"),
            name: "stub".into(),
            version: "0.1.0".into(),
            plugin_api: PluginApiVersion::current(),
            capabilities: capabilities.iter().copied().collect(),
            entry: "stub.wasm".into(),
        }
    }

    #[test]
    fn overlay_requires_the_declared_capability() {
        let mut set = PluginSet::new();
        set.register(
            manifest("dev.rustclient.stub", &[]),
            Box::new(Stub {
                seen_ticks: 0,
                fail: false,
            }),
        )
        .expect("compatible");
        let view = StaticView::default();
        let mut storage = MemoryStorage::new();
        let mut logs = MemoryLogSink::default();
        set.init(&view, &mut storage, &mut logs);
        set.dispatch(&[PluginEvent::Tick(7)], &view, &mut storage, &mut logs);
        assert!(set.overlay(&view, &mut storage, &mut logs).is_empty());
    }

    #[test]
    fn granted_plugins_receive_ticks_and_emit_overlays() {
        let mut set = PluginSet::new();
        set.register(
            manifest("dev.rustclient.stub", &[Capability::UiOverlay]),
            Box::new(Stub {
                seen_ticks: 0,
                fail: false,
            }),
        )
        .expect("compatible");
        let view = StaticView::default();
        let mut storage = MemoryStorage::new();
        let mut logs = MemoryLogSink::default();
        set.init(&view, &mut storage, &mut logs);
        set.dispatch(&[PluginEvent::Tick(7)], &view, &mut storage, &mut logs);
        let lines = set.overlay(&view, &mut storage, &mut logs);
        assert_eq!(lines.len(), 1);
        assert_eq!(lines[0].text, "tick 7");
    }

    #[test]
    fn repeated_faults_disable_a_plugin() {
        let mut set = PluginSet::with_fault_limit(2);
        set.register(
            manifest("dev.rustclient.stub", &[Capability::UiOverlay]),
            Box::new(Stub {
                seen_ticks: 0,
                fail: true,
            }),
        )
        .expect("compatible");
        let view = StaticView::default();
        let mut storage = MemoryStorage::new();
        let mut logs = MemoryLogSink::default();
        set.init(&view, &mut storage, &mut logs);
        for _ in 0..3 {
            set.dispatch(&[PluginEvent::Tick(1)], &view, &mut storage, &mut logs);
        }
        let plugin = set.iter().next().expect("registered");
        assert!(!plugin.enabled());
        assert_eq!(plugin.faults(), 2);
    }
}
