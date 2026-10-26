//! Required first-party services share registration and diagnostics with optional components.
use crate::{FaultKind, PluginCall, PluginFault, PluginInstance, PluginSet};
use rustclient_plugin_api::{
    CapabilitySet, LogLevel, OverlayLine, PluginApiVersion, PluginEvent, PluginId, PluginManifest,
};

#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum Builtin {
    Gpu,
    RuneLite,
}
impl Builtin {
    pub const ALL: [Self; 2] = [Self::Gpu, Self::RuneLite];
    #[must_use]
    pub const fn id(self) -> &'static str {
        match self {
            Self::Gpu => "dev.rustclient.gpu",
            Self::RuneLite => "dev.rustclient.runelite",
        }
    }
    #[must_use]
    pub const fn name(self) -> &'static str {
        match self {
            Self::Gpu => "GPU",
            Self::RuneLite => "RuneLite",
        }
    }
}

/// Source-keyed configuration catalogs. Presence does not claim the effect is implemented.
/// # Errors
/// Rejects corrupt embedded descriptors before constructing a live preference owner.
pub fn schemas() -> Result<Vec<crate::configuration::ConfigSchema>, String> {
    serde_json::from_str(include_str!("builtin-schemas.json")).map_err(|error| error.to_string())
}

/// Lifecycle adapter. Rendering/configuration effects stay in their typed engine services.
struct ApplicationService {
    kind: Builtin,
    initialized: bool,
    session_active: bool,
}
impl PluginInstance for ApplicationService {
    fn init(&mut self, call: &mut PluginCall<'_>) -> Result<(), PluginFault> {
        if !self.initialized {
            call.logs.log(
                call.plugin,
                LogLevel::Info,
                &format!("{} required application service started", self.kind.name()),
            );
            self.initialized = true;
        }
        Ok(())
    }
    fn on_event(
        &mut self,
        call: &mut PluginCall<'_>,
        events: &[PluginEvent],
    ) -> Result<(), PluginFault> {
        if !self.initialized {
            return Err(PluginFault::new(
                FaultKind::Init,
                "application service not initialized",
            ));
        }
        for event in events {
            let next = match event {
                PluginEvent::Started => true,
                PluginEvent::Stopped => false,
                PluginEvent::Tick(_) => continue,
            };
            if next != self.session_active {
                self.session_active = next;
                call.logs.log(
                    call.plugin,
                    LogLevel::Debug,
                    if next {
                        "game session started"
                    } else {
                        "game session stopped"
                    },
                );
            }
        }
        Ok(())
    }
    fn overlay(&mut self, _call: &mut PluginCall<'_>) -> Result<Vec<OverlayLine>, PluginFault> {
        // These services provide configuration/sidebar controls; they have no world text overlay.
        Ok(Vec::new())
    }
}

impl PluginSet {
    /// Register an engine-owned required service. External manifests cannot request this policy.
    /// # Errors
    /// Rejects duplicate identities or incompatible API metadata.
    pub fn register_required(&mut self, kind: Builtin) -> Result<(), PluginFault> {
        let id = PluginId::parse(kind.id())
            .map_err(|error| PluginFault::new(FaultKind::Contract, error.to_string()))?;
        self.register(
            PluginManifest {
                id: id.clone(),
                name: kind.name().to_owned(),
                version: env!("CARGO_PKG_VERSION").to_owned(),
                plugin_api: PluginApiVersion::current(),
                capabilities: CapabilitySet::default(),
                entry: String::new(),
            },
            Box::new(ApplicationService {
                kind,
                initialized: false,
                session_active: false,
            }),
        )?;
        if let Some(plugin) = self
            .plugins
            .iter_mut()
            .find(|plugin| plugin.manifest.id == id)
        {
            plugin.required = true;
        }
        Ok(())
    }

    /// Host enforcement applies even when no configuration UI is present.
    /// # Errors
    /// Required services cannot be disabled; unknown IDs are rejected.
    pub fn set_enabled(&mut self, id: &PluginId, enabled: bool) -> Result<(), PluginFault> {
        let plugin = self
            .plugins
            .iter_mut()
            .find(|plugin| plugin.manifest.id == *id)
            .ok_or_else(|| PluginFault::new(FaultKind::Contract, format!("unknown plugin {id}")))?;
        if plugin.required && !enabled {
            return Err(PluginFault::new(
                FaultKind::Contract,
                format!("{id} is required"),
            ));
        }
        plugin.enabled = enabled;
        Ok(())
    }

    /// Remove an optional service after the caller has stopped it.
    /// # Errors
    /// Required services and unknown IDs cannot be unregistered.
    pub fn unregister(&mut self, id: &PluginId) -> Result<(), PluginFault> {
        let index = self
            .plugins
            .iter()
            .position(|plugin| plugin.manifest.id == *id)
            .ok_or_else(|| PluginFault::new(FaultKind::Contract, format!("unknown plugin {id}")))?;
        if self.plugins[index].required {
            return Err(PluginFault::new(
                FaultKind::Contract,
                format!("{id} is required"),
            ));
        }
        self.plugins.remove(index);
        Ok(())
    }
}
