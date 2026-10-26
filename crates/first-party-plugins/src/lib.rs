//! First-party plugins ported from `RuneLite`, running on plugin API 0.2.
//!
//! Each plugin is a [`NativePlugin`]; [`register_all`] adds every one to a [`PluginSet`] with the
//! capabilities its source behaviour needs, and the host enforces those grants like any other
//! plugin's (ADR-0006).

#![forbid(unsafe_code)]

mod animsmoothing;
mod camera;
mod entityhider;
mod fps;
mod idlenotifier;
mod idlenotifier_animations;
mod interfacestyles;
mod interfacestyles_sprites;
mod npchighlight;
mod playerindicators;
mod roofremoval;
mod statusbars;
mod stretchedmode;
mod text;

use rustclient_plugin_api::{
    Capability, CapabilitySet, GameEvent, OverlayLine, PluginApiVersion, PluginEvent, PluginId,
    PluginManifest, PluginOutput,
};
use rustclient_plugin_host::{
    FaultKind, PluginCall, PluginFault, PluginInstance, PluginSet, required::first_party_schemas,
};

/// A plugin written against the 0.2 surface only.
pub trait NativePlugin {
    /// Reset to a clean state; called when the plugin starts and every time it is switched on.
    ///
    /// # Errors
    /// Contract failures described by [`PluginFault`].
    fn init(&mut self, call: &mut PluginCall<'_>) -> Result<(), PluginFault> {
        let _ = call;
        Ok(())
    }

    /// Typed gameplay events, already filtered to the plugin's grants.
    ///
    /// # Errors
    /// Contract failures described by [`PluginFault`].
    fn events(
        &mut self,
        call: &mut PluginCall<'_>,
        events: &[GameEvent],
    ) -> Result<(), PluginFault> {
        let _ = (call, events);
        Ok(())
    }

    /// Declarative output for this update.
    ///
    /// # Errors
    /// Contract failures described by [`PluginFault`].
    fn output(&mut self, call: &mut PluginCall<'_>) -> Result<PluginOutput, PluginFault>;
}

struct Adapter<T>(T);

impl<T: NativePlugin> PluginInstance for Adapter<T> {
    fn init(&mut self, call: &mut PluginCall<'_>) -> Result<(), PluginFault> {
        self.0.init(call)
    }

    fn on_event(
        &mut self,
        _call: &mut PluginCall<'_>,
        _events: &[PluginEvent],
    ) -> Result<(), PluginFault> {
        Ok(())
    }

    fn overlay(&mut self, _call: &mut PluginCall<'_>) -> Result<Vec<OverlayLine>, PluginFault> {
        Ok(Vec::new())
    }

    fn on_game_events(
        &mut self,
        call: &mut PluginCall<'_>,
        events: &[GameEvent],
    ) -> Result<(), PluginFault> {
        self.0.events(call, events)
    }

    fn output(&mut self, call: &mut PluginCall<'_>) -> Result<PluginOutput, PluginFault> {
        self.0.output(call)
    }
}

struct Entry {
    /// Plugin id suffix after `dev.rustclient.`.
    name: &'static str,
    capabilities: &'static [Capability],
    build: fn() -> Box<dyn PluginInstance>,
}

fn boxed<T: NativePlugin + 'static>(plugin: T) -> Box<dyn PluginInstance> {
    Box::new(Adapter(plugin))
}

const ENTRIES: &[Entry] = &[
    Entry {
        name: "roofremoval",
        capabilities: &[Capability::SceneControl, Capability::GameReadClient],
        build: || boxed(roofremoval::RoofRemoval::new()),
    },
    Entry {
        name: "animsmoothing",
        capabilities: &[Capability::SceneControl],
        build: || boxed(animsmoothing::AnimationSmoothingPlugin),
    },
    Entry {
        name: "stretchedmode",
        capabilities: &[Capability::UiViewport],
        build: || boxed(stretchedmode::StretchedMode),
    },
    Entry {
        name: "entityhider",
        capabilities: &[
            Capability::UiEntitiesHide,
            Capability::GameReadPlayers,
            Capability::GameReadNpcs,
        ],
        build: || boxed(entityhider::EntityHider),
    },
    Entry {
        name: "fps",
        capabilities: &[
            Capability::UiOverlay,
            Capability::ClientFrameLimit,
            Capability::GameReadClient,
            Capability::GameReadWidgets,
        ],
        build: || boxed(fps::Fps),
    },
    Entry {
        name: "statusbars",
        capabilities: &[
            Capability::UiOverlay,
            Capability::GameReadWidgets,
            Capability::GameReadPlayers,
            Capability::GameReadNpcs,
            Capability::GameReadSkills,
            Capability::GameReadVars,
            Capability::GameReadClient,
        ],
        build: || boxed(statusbars::StatusBars::default()),
    },
    Entry {
        name: "idlenotifier",
        capabilities: &[
            Capability::ClientNotify,
            Capability::GameReadClient,
            Capability::GameReadPlayers,
            Capability::GameReadNpcs,
            Capability::GameReadSkills,
            Capability::GameReadVars,
        ],
        build: || boxed(idlenotifier::IdleNotifier::new()),
    },
    Entry {
        name: "interfacestyles",
        capabilities: &[Capability::UiSpriteOverride],
        build: || boxed(interfacestyles::InterfaceStyles::default()),
    },
    Entry {
        name: "npchighlight",
        capabilities: &[
            Capability::UiOverlay,
            Capability::GameReadNpcs,
            Capability::GameReadVars,
        ],
        build: || boxed(npchighlight::NpcHighlight),
    },
    Entry {
        name: "playerindicators",
        capabilities: &[
            Capability::UiOverlay,
            Capability::GameReadPlayers,
            Capability::GameReadVars,
        ],
        build: || boxed(playerindicators::PlayerIndicators),
    },
    Entry {
        name: "camera",
        capabilities: &[Capability::CameraControl],
        build: || boxed(camera::Camera),
    },
];

/// Register every first-party plugin, disabled until its configuration switches it on.
///
/// # Errors
/// Reports a plugin whose schema is missing or whose identity is rejected by the host.
pub fn register_all(set: &mut PluginSet) -> Result<(), PluginFault> {
    let schemas =
        first_party_schemas().map_err(|error| PluginFault::new(FaultKind::Contract, error))?;
    for entry in ENTRIES {
        let id = format!("dev.rustclient.{}", entry.name);
        let schema = schemas
            .iter()
            .find(|schema| schema.plugin.as_str() == id)
            .ok_or_else(|| PluginFault::new(FaultKind::Contract, format!("no schema for {id}")))?;
        let manifest = PluginManifest {
            id: PluginId::parse(&id)
                .map_err(|error| PluginFault::new(FaultKind::Contract, error.to_string()))?,
            name: schema.name.clone(),
            version: env!("CARGO_PKG_VERSION").to_owned(),
            plugin_api: PluginApiVersion::current(),
            capabilities: entry
                .capabilities
                .iter()
                .copied()
                .collect::<CapabilitySet>(),
            entry: String::new(),
        };
        let id = manifest.id.clone();
        set.register(manifest, (entry.build)())?;
        // Off until the configuration owner reports the plugin's saved state.
        set.set_enabled(&id, false)?;
    }
    Ok(())
}

#[cfg(test)]
mod tests;
