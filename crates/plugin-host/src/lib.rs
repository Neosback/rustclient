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
mod lifecycle;
pub mod menu;
mod panels;
pub mod required;
mod subscriptions;
pub use lifecycle::{ActivationId, LifecycleStatus};
pub use menu::{EditedMenu, apply_menu_edits};
pub use panels::{HostedPanel, PanelAction, PanelLease};
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
///
/// Saves stage a sibling file, sync it and rename it over the target, so a crash or a full disk
/// never truncates the last accepted document. A file that cannot be parsed is renamed to
/// `config.json.corrupt` instead of being silently replaced by defaults, and every failure is
/// queued for [`DirectoryStorage::take_errors`].
#[cfg(not(target_arch = "wasm32"))]
#[derive(Debug)]
pub struct DirectoryStorage {
    root: std::path::PathBuf,
    errors: std::sync::Mutex<Vec<String>>,
}

#[cfg(not(target_arch = "wasm32"))]
impl DirectoryStorage {
    /// Use `root` as the plugin configuration root.
    #[must_use]
    pub fn new(root: std::path::PathBuf) -> Self {
        Self {
            root,
            errors: std::sync::Mutex::new(Vec::new()),
        }
    }

    /// Storage failures since the last call, for the caller to log.
    pub fn take_errors(&self) -> Vec<String> {
        self.errors
            .lock()
            .map(|mut errors| std::mem::take(&mut *errors))
            .unwrap_or_default()
    }

    fn path_for(&self, plugin: &PluginId) -> std::path::PathBuf {
        self.root.join(plugin.as_str()).join("config.json")
    }

    fn record(&self, plugin: &PluginId, message: &str) {
        if let Ok(mut errors) = self.errors.lock() {
            // Bounded so a failing disk cannot grow memory without limit.
            if errors.len() < 32 {
                errors.push(format!("plugin {plugin}: {message}"));
            }
        }
    }

    fn write_atomically(path: &std::path::Path, text: &str) -> std::io::Result<()> {
        use std::io::Write;
        let staged = path.with_extension("json.pending");
        let result = (|| {
            let mut file = std::fs::File::create(&staged)?;
            file.write_all(text.as_bytes())?;
            file.sync_all()?;
            std::fs::rename(&staged, path)
        })();
        if result.is_err() {
            let _ = std::fs::remove_file(&staged);
        }
        result
    }
}

#[cfg(not(target_arch = "wasm32"))]
impl PluginStorage for DirectoryStorage {
    fn load(&self, plugin: &PluginId) -> BTreeMap<String, String> {
        let path = self.path_for(plugin);
        let text = match std::fs::read_to_string(&path) {
            Ok(text) => text,
            Err(error) if error.kind() == std::io::ErrorKind::NotFound => return BTreeMap::new(),
            Err(error) => {
                self.record(
                    plugin,
                    &format!("could not read its configuration: {error}"),
                );
                return BTreeMap::new();
            }
        };
        serde_json::from_str(&text).unwrap_or_else(|error| {
            let kept = path.with_extension("json.corrupt");
            let outcome = match std::fs::rename(&path, &kept) {
                Ok(()) => format!("kept as {}", kept.display()),
                Err(rename) => format!("could not be kept ({rename})"),
            };
            self.record(
                plugin,
                &format!("configuration is not valid ({error}); defaults used, file {outcome}"),
            );
            BTreeMap::new()
        })
    }

    fn save(&mut self, plugin: &PluginId, values: &BTreeMap<String, String>) {
        let path = self.path_for(plugin);
        let Some(parent) = path.parent() else {
            return;
        };
        if let Err(error) = std::fs::create_dir_all(parent) {
            self.record(plugin, &format!("could not create its folder: {error}"));
            return;
        }
        match serde_json::to_string_pretty(values) {
            Ok(text) => {
                if let Err(error) = Self::write_atomically(&path, &text) {
                    self.record(
                        plugin,
                        &format!("could not save its configuration: {error}"),
                    );
                }
            }
            Err(error) => self.record(
                plugin,
                &format!("could not encode its configuration: {error}"),
            ),
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
/// Authority references are host-owned and cannot be rebound by plugins.
/// ```compile_fail,E0616
/// use rustclient_plugin_host::PluginCall;
/// fn rebind(call: &mut PluginCall<'_>) { call.plugin = call.plugin_id(); }
/// ```
/// ```compile_fail,E0616
/// use rustclient_plugin_host::PluginCall;
/// fn rebind(call: &mut PluginCall<'_>) { call.capabilities = call.capabilities(); }
/// ```
/// ```compile_fail,E0616
/// use rustclient_plugin_host::PluginCall;
/// fn rebind(call: &mut PluginCall<'_>) { call.config = call.config(); }
/// ```
pub struct PluginCall<'a> {
    /// Executing plugin identity.
    plugin: &'a PluginId,
    /// Operator grants for this package.
    capabilities: &'a CapabilitySet,
    /// Approved state projection.
    view: &'a dyn HostView,
    /// Plugin-local storage.
    storage: &'a mut dyn PluginStorage,
    /// Diagnostic sink.
    logs: &'a mut dyn LogSink,
    /// Effective settings of this plugin, defaults applied.
    config: &'a PluginConfig,
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
    /// Revoke instance-owned resources. Host scheduling/output is already revoked.
    /// # Errors
    /// Cleanup faults remain diagnostic; the instance is never rescheduled implicitly.
    fn shutdown(&mut self, _call: &mut PluginCall<'_>) -> Result<(), PluginFault> {
        Ok(())
    }
    /// Host-side interest hint, intersected with capability grants. Old instances receive all events.
    fn subscribes_game_events(&self) -> bool {
        true
    }
    /// Static event declaration; changes require a new registration.
    fn game_event_interests(&self) -> rustclient_plugin_api::EventInterest {
        rustclient_plugin_api::EventInterest::ALL
    }
    /// Source subscriber class for RuneLite-compatible tie ordering.
    fn game_event_source(&self) -> &str {
        core::any::type_name::<Self>()
    }
    /// Source annotation priority for one event class. Must be finite.
    fn game_event_priority(&self, _kind: rustclient_plugin_api::GameEventKind) -> f32 {
        0.
    }

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

    /// User action checked against this activation's last validated panel document.
    /// # Errors
    /// Runtime or contract failures described by [`PluginFault`].
    fn on_panel_event(
        &mut self,
        _call: &mut PluginCall<'_>,
        _event: &rustclient_plugin_api::PanelEvent,
    ) -> Result<(), PluginFault> {
        Ok(())
    }

    /// Receive typed gameplay events (API 0.2), already filtered to the plugin's grants.
    /// The default ignores them, so 0.1 plugins are unaffected.
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
    lifecycle: lifecycle::Lifecycle,
    enabled: bool,
    required: bool,
    config: PluginConfig,
    subscription: subscriptions::Subscription,
    denied: BTreeSet<Capability>,
}

impl LoadedPlugin {
    /// Required application services cannot be disabled or unregistered.
    #[must_use]
    pub fn required(&self) -> bool {
        self.required
    }
    /// Legacy configured-enable view, with isolated optional faults disabled.
    /// Use `running()` for scheduling and `configured_enabled()` for persisted intent.
    #[must_use]
    pub fn enabled(&self) -> bool {
        self.enabled && (self.required || self.status() != LifecycleStatus::Faulted)
    }

    /// Largest consecutive failure streak across the separate callback phases.
    #[must_use]
    pub fn faults(&self) -> u32 {
        self.lifecycle.streaks.iter().copied().max().unwrap_or(0)
    }
}

/// A registered set of plugins with mediation and fault isolation.
pub struct PluginSet {
    registration_sequence: u64,
    plugins: Vec<LoadedPlugin>,
    fault_limit: u32,
    event_routes: subscriptions::Routes,
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
            registration_sequence: 0,
            fault_limit: 3,
            event_routes: subscriptions::Routes::default(),
        }
    }

    /// Empty set with an explicit fault limit.
    #[must_use]
    pub fn with_fault_limit(fault_limit: u32) -> Self {
        Self {
            plugins: Vec::new(),
            registration_sequence: 0,
            fault_limit,
            event_routes: subscriptions::Routes::default(),
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
        let registration = self.registration_sequence.checked_add(1).ok_or_else(|| {
            PluginFault::new(
                FaultKind::Contract,
                "plugin registration identity exhausted",
            )
        })?;
        self.registration_sequence = registration;
        let subscription =
            subscriptions::Subscription::new(instance.as_ref(), &manifest.capabilities)?;
        self.plugins.push(LoadedPlugin {
            subscription,
            manifest,
            instance,
            lifecycle: lifecycle::Lifecycle {
                activation: ActivationId {
                    registration,
                    generation: 0,
                },
                ..Default::default()
            },
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
        let plugin = &mut self.plugins[index];
        if plugin.required && !enabled {
            return Err(PluginFault::new(
                FaultKind::Contract,
                format!("{id} is required"),
            ));
        }
        let turned_on = enabled && plugin.status() != LifecycleStatus::Running;
        plugin.enabled = enabled;
        plugin.config = config;
        if !enabled {
            lifecycle::stop(plugin, view, storage, logs)?;
        }
        if turned_on {
            lifecycle::start(plugin, view, storage, logs)?;
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
        for plugin in &mut self.plugins {
            if plugin.status() == LifecycleStatus::Stopped {
                let _ = lifecycle::start(plugin, view, storage, logs);
            }
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
        if events
            .iter()
            .any(|event| matches!(event, PluginEvent::Stopped))
        {
            for plugin in &mut self.plugins {
                // Session-derived world output expires; local panels belong to the activation.
                plugin.lifecycle.output =
                    plugin
                        .lifecycle
                        .output
                        .take()
                        .and_then(|(activation, output)| {
                            (!output.panels.is_empty()).then_some((
                                activation,
                                PluginOutput {
                                    panels: output.panels,
                                    ..PluginOutput::default()
                                },
                            ))
                        });
            }
        }
        for index in 0..self.plugins.len() {
            let fault_limit = self.fault_limit;
            let plugin = &mut self.plugins[index];
            let _ = Self::invoke(
                plugin,
                lifecycle::Phase::Session,
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
                lifecycle::Phase::Overlay,
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
        phase: lifecycle::Phase,
        view: &dyn HostView,
        storage: &mut dyn PluginStorage,
        logs: &mut dyn LogSink,
        fault_limit: u32,
        action: impl FnOnce(&mut dyn PluginInstance, &mut PluginCall<'_>) -> Result<T, PluginFault>,
    ) -> Option<T> {
        lifecycle::invoke(plugin, phase, view, storage, logs, fault_limit, action)
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
