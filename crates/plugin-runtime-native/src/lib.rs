//! Native plugin execution adapter over Wasmtime's component model.
//!
//! Each plugin is one component instance with its own `Store`, fuel budget and host state.
//! Host imports are mediated by [`HostState`]: they copy from the approved [`PluginCall`]
//! projection into owned per-call state and flush logs/config back after the call, so a
//! plugin can never observe or retain a client reference.

#![forbid(unsafe_code)]

use rustclient_plugin_api::{
    Capability, LogLevel, OverlayLine, PlayerPosition, PluginEvent, PluginId, PluginManifest,
};
use rustclient_plugin_host::{FaultKind, PluginCall, PluginFault, PluginInstance};
use std::collections::BTreeMap;
use std::path::Path;
use wasmtime::component::{Component, HasSelf, Linker};
use wasmtime::{Config, Engine, Store};

mod bindings {
    wasmtime::component::bindgen!({
        path: "../../wit/rustclient/plugin/world.wit",
        world: "plugin",
    });
}

use bindings::rustclient::plugin::host as wit_host;

/// The 0.2.0 contract is checked at compile time but not instantiated yet: the runtime still binds the
/// 0.1.0 world until the host import and export adapters for 0.2 land (ADR-0006).
#[cfg(test)]
mod bindings_v2 {
    wasmtime::component::bindgen!({
        path: "../../wit/rustclient/plugin-0.2/world.wit",
        world: "plugin",
    });

    #[test]
    fn wit_0_2_generates_records_that_mirror_the_api_crate() {
        use rustclient::plugin::types::{ActorRef, GameEvent, PluginOutput, WorldPoint};
        let point = WorldPoint {
            x: 3222,
            y: 3218,
            plane: 0,
        };
        let event = GameEvent::AnimationChanged((ActorRef::Npc(5), Some(422)));
        let output = PluginOutput {
            overlay: Vec::new(),
            notifications: Vec::new(),
            info_boxes: Vec::new(),
            menu_edits: Vec::new(),
            entities: None,
            sprite_overrides: Vec::new(),
            camera: Vec::new(),
            scene: None,
            roof_areas: Vec::new(),
            stretch: None,
            smoothing_denied: Vec::new(),
        };
        assert_eq!((point.x, point.plane), (3222, 0));
        assert!(matches!(event, GameEvent::AnimationChanged(_)));
        assert!(output.entities.is_none());
    }
}

/// Fuel granted to one plugin entry point; a runaway plugin traps instead of hanging the client.
const CALL_FUEL: u64 = 20_000_000;

/// Native runtime owning one engine and one host linker.
pub struct NativeRuntime {
    engine: Engine,
    linker: Linker<HostState>,
}

impl NativeRuntime {
    /// Build a component-model engine with fuel metering.
    ///
    /// # Errors
    /// Fails if the Wasmtime engine or host linker cannot be constructed.
    pub fn new() -> Result<Self, PluginFault> {
        let mut config = Config::new();
        config.wasm_component_model(true);
        config.consume_fuel(true);
        let engine = Engine::new(&config)
            .map_err(|error| PluginFault::new(FaultKind::Instantiate, error.to_string()))?;
        let mut linker = Linker::new(&engine);
        wit_host::add_to_linker::<_, HasSelf<_>>(&mut linker, |state: &mut HostState| state)
            .map_err(|error| PluginFault::new(FaultKind::Instantiate, error.to_string()))?;
        Ok(Self { engine, linker })
    }

    /// Load one plugin package directory.
    ///
    /// Reads `plugin.json`, validates entry containment, compiles the component and
    /// instantiates one isolated store.
    ///
    /// # Errors
    /// Fails on manifest, compile or instantiation problems.
    pub fn load(
        &self,
        package_dir: &Path,
    ) -> Result<(PluginManifest, Box<dyn PluginInstance>), PluginFault> {
        let manifest = PluginManifest::from_dir(package_dir)
            .map_err(|error| PluginFault::new(FaultKind::Contract, error.to_string()))?;
        manifest
            .check_api(rustclient_plugin_api::PluginApiVersion::current())
            .map_err(|error| PluginFault::new(FaultKind::Contract, error.to_string()))?;
        let entry = manifest
            .entry_path(package_dir)
            .map_err(|error| PluginFault::new(FaultKind::Contract, error.to_string()))?;
        let component = Component::from_file(&self.engine, &entry)
            .map_err(|error| PluginFault::new(FaultKind::Instantiate, error.to_string()))?;
        let mut store = Store::new(&self.engine, HostState::default());
        store
            .set_fuel(CALL_FUEL)
            .map_err(|error| PluginFault::new(FaultKind::Instantiate, error.to_string()))?;
        let bindings = bindings::Plugin::instantiate(&mut store, &component, &self.linker)
            .map_err(|error| PluginFault::new(FaultKind::Instantiate, error.to_string()))?;
        Ok((manifest, Box::new(NativeInstance { store, bindings })))
    }
}

/// One instantiated native plugin.
struct NativeInstance {
    store: Store<HostState>,
    bindings: bindings::Plugin,
}

impl NativeInstance {
    /// Copy the mediated call projection into owned store state.
    fn prepare(&mut self, call: &mut PluginCall<'_>) {
        let state = self.store.data_mut();
        state.plugin = Some(call.plugin.clone());
        state.capabilities = call.capabilities.clone();
        state.config = call.storage.load(call.plugin);
        state.config_dirty = false;
        state.position = call.view.player_position();
        state.tick = call.view.tick();
        state.logs.clear();
        state.denials.clear();
    }

    /// Flush buffered logs, denials and storage writes after a call.
    fn finish(&mut self, call: &mut PluginCall<'_>) {
        let state = self.store.data_mut();
        for (level, message) in state.logs.drain(..) {
            call.logs.log(call.plugin, level, &message);
        }
        for denial in state.denials.drain(..) {
            call.logs.log(
                call.plugin,
                LogLevel::Warn,
                &format!("host service denied: {denial}"),
            );
        }
        if state.config_dirty {
            call.storage.save(call.plugin, &state.config);
            state.config_dirty = false;
        }
    }

    /// Refill fuel before one entry point.
    fn refuel(&mut self, kind: FaultKind) -> Result<(), PluginFault> {
        self.store
            .set_fuel(CALL_FUEL)
            .map_err(|error| PluginFault::new(kind, error.to_string()))
    }
}

fn trap(kind: FaultKind, error: &wasmtime::Error) -> PluginFault {
    let message = error.to_string();
    let kind = if message.contains("fuel") {
        FaultKind::Budget
    } else {
        kind
    };
    PluginFault::new(kind, message)
}

impl PluginInstance for NativeInstance {
    fn init(&mut self, call: &mut PluginCall<'_>) -> Result<(), PluginFault> {
        self.prepare(call);
        self.refuel(FaultKind::Init)?;
        let result = self
            .bindings
            .rustclient_plugin_guest()
            .call_init(&mut self.store)
            .map_err(|error| trap(FaultKind::Init, &error));
        self.finish(call);
        result
    }

    fn on_event(
        &mut self,
        call: &mut PluginCall<'_>,
        events: &[PluginEvent],
    ) -> Result<(), PluginFault> {
        let events: Vec<bindings::exports::rustclient::plugin::guest::Event> =
            events.iter().map(convert_event).collect();
        self.prepare(call);
        self.refuel(FaultKind::Event)?;
        let result = self
            .bindings
            .rustclient_plugin_guest()
            .call_on_event(&mut self.store, &events)
            .map_err(|error| trap(FaultKind::Event, &error));
        self.finish(call);
        result
    }

    fn overlay(&mut self, call: &mut PluginCall<'_>) -> Result<Vec<OverlayLine>, PluginFault> {
        self.prepare(call);
        self.refuel(FaultKind::Overlay)?;
        let lines = self
            .bindings
            .rustclient_plugin_guest()
            .call_overlay(&mut self.store)
            .map_err(|error| trap(FaultKind::Overlay, &error))?;
        self.finish(call);
        Ok(lines
            .into_iter()
            .map(|line| OverlayLine {
                text: line.text,
                x: line.x,
                y: line.y,
                argb: line.argb,
            })
            .collect())
    }
}

fn convert_event(event: &PluginEvent) -> bindings::exports::rustclient::plugin::guest::Event {
    use bindings::exports::rustclient::plugin::guest::Event;
    match event {
        PluginEvent::Started => Event::Started,
        PluginEvent::Tick(tick) => Event::Tick(*tick),
        PluginEvent::Stopped => Event::Stopped,
    }
}

fn convert_level(level: wit_host::LogLevel) -> LogLevel {
    match level {
        wit_host::LogLevel::Trace => LogLevel::Trace,
        wit_host::LogLevel::Debug => LogLevel::Debug,
        wit_host::LogLevel::Info => LogLevel::Info,
        wit_host::LogLevel::Warn => LogLevel::Warn,
        wit_host::LogLevel::Error => LogLevel::Error,
    }
}

/// Owned per-call host state. Contains nothing a plugin can retain.
#[derive(Default)]
struct HostState {
    plugin: Option<PluginId>,
    capabilities: rustclient_plugin_api::CapabilitySet,
    config: BTreeMap<String, String>,
    config_dirty: bool,
    position: Option<PlayerPosition>,
    tick: u64,
    logs: Vec<(LogLevel, String)>,
    denials: Vec<String>,
}

impl wit_host::Host for HostState {
    fn log(&mut self, level: wit_host::LogLevel, message: String) {
        self.logs.push((convert_level(level), message));
    }

    fn player_position(&mut self) -> Option<wit_host::Player> {
        if !self.capabilities.contains(Capability::GameReadPlayers) {
            self.denials.push(Capability::GameReadPlayers.to_string());
            return None;
        }
        self.position.map(|position| wit_host::Player {
            x: position.x,
            y: position.y,
            plane: position.plane,
        })
    }

    fn tick(&mut self) -> u64 {
        self.tick
    }

    fn config_get(&mut self, key: String) -> Option<String> {
        if !self.capabilities.contains(Capability::StoragePlugin) {
            self.denials.push(Capability::StoragePlugin.to_string());
            return None;
        }
        self.config.get(&key).cloned()
    }

    fn config_set(&mut self, key: String, value: String) {
        if !self.capabilities.contains(Capability::StoragePlugin) {
            self.denials.push(Capability::StoragePlugin.to_string());
            return;
        }
        self.config.insert(key, value);
        self.config_dirty = true;
    }
}
