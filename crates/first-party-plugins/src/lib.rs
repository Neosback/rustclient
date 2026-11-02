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
mod minimap;
mod notes;
mod npchighlight;
mod playerindicators;
mod roofremoval;
mod statusbars;
mod stretchedmode;
mod text;
mod tileindicators;
pub mod xptracker;

use rustclient_plugin_api::{
    Capability, CapabilitySet, GameEvent, OverlayLine, PluginApiVersion, PluginEvent, PluginId,
    PluginManifest, PluginOutput,
};
use rustclient_plugin_host::{
    FaultKind, PluginCall, PluginFault, PluginInstance, PluginSet, required::first_party_schemas,
};

/// A plugin written against the 0.2 surface only.
pub trait NativePlugin {
    /// Release per-activation state after host output/subscription revocation.
    /// # Errors
    /// Cleanup errors remain diagnostic and do not reactivate the plugin.
    fn shutdown(&mut self, _call: &mut PluginCall<'_>) -> Result<(), PluginFault> {
        Ok(())
    }
    /// Passive event interests, never a substitute for capability grants.
    fn subscribes_game_events(&self) -> bool {
        true
    }
    /// Static subset actually consumed by this implementation.
    fn game_event_interests(&self) -> rustclient_plugin_api::EventInterest {
        rustclient_plugin_api::EventInterest::ALL
    }
    /// Source annotation priority for a class.
    fn game_event_priority(&self, _kind: rustclient_plugin_api::GameEventKind) -> f32 {
        0.
    }

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

    /// A host-validated interaction with a panel from this activation.
    /// # Errors
    /// Callback failures use the host's phase-specific fault policy.
    fn panel_event(
        &mut self,
        _call: &mut PluginCall<'_>,
        _event: &rustclient_plugin_api::PanelEvent,
    ) -> Result<(), PluginFault> {
        Ok(())
    }

    /// Declarative output for this update.
    ///
    /// # Errors
    /// Contract failures described by [`PluginFault`].
    fn output(&mut self, call: &mut PluginCall<'_>) -> Result<PluginOutput, PluginFault>;
}

struct Adapter<T>(T, &'static str);

impl<T: NativePlugin> PluginInstance for Adapter<T> {
    fn shutdown(&mut self, call: &mut PluginCall<'_>) -> Result<(), PluginFault> {
        self.0.shutdown(call)
    }
    fn subscribes_game_events(&self) -> bool {
        self.0.subscribes_game_events()
    }

    fn game_event_interests(&self) -> rustclient_plugin_api::EventInterest {
        self.0.game_event_interests()
    }
    fn game_event_source(&self) -> &str {
        self.1
    }
    fn game_event_priority(&self, kind: rustclient_plugin_api::GameEventKind) -> f32 {
        self.0.game_event_priority(kind)
    }

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

    fn on_panel_event(
        &mut self,
        call: &mut PluginCall<'_>,
        event: &rustclient_plugin_api::PanelEvent,
    ) -> Result<(), PluginFault> {
        self.0.panel_event(call, event)
    }

    fn output(&mut self, call: &mut PluginCall<'_>) -> Result<PluginOutput, PluginFault> {
        self.0.output(call)
    }
}

struct Entry {
    /// Plugin id suffix after `dev.rustclient.`.
    name: &'static str,
    capabilities: &'static [Capability],
    build: fn(&'static str) -> Box<dyn PluginInstance>,
    source_class: &'static str,
}

fn boxed<T: NativePlugin + 'static>(plugin: T, source: &'static str) -> Box<dyn PluginInstance> {
    Box::new(Adapter(plugin, source))
}

const ENTRIES: &[Entry] = &[
    Entry {
        name: "notes",
        source_class: "net.runelite.client.plugins.notes.NotesPlugin",
        capabilities: &[Capability::UiPanel, Capability::StoragePlugin],
        build: |source| boxed(notes::Notes::default(), source),
    },
    Entry {
        name: "xptracker",
        source_class: "net.runelite.client.plugins.xptracker.XpTrackerPlugin",
        capabilities: &[
            Capability::GameReadSkills,
            Capability::GameReadClient,
            Capability::GameReadPlayers,
            Capability::UiOverlay,
            Capability::UiPanel,
        ],
        build: |source| boxed(xptracker::XpTracker::default(), source),
    },
    Entry {
        name: "roofremoval",
        source_class: "net.runelite.client.plugins.roofremoval.RoofRemovalPlugin",
        capabilities: &[Capability::SceneControl, Capability::GameReadClient],
        build: |source| boxed(roofremoval::RoofRemoval::new(), source),
    },
    Entry {
        name: "animsmoothing",
        source_class: "net.runelite.client.plugins.animsmoothing.AnimationSmoothingPlugin",
        capabilities: &[Capability::SceneControl],
        build: |source| boxed(animsmoothing::AnimationSmoothingPlugin, source),
    },
    Entry {
        name: "stretchedmode",
        source_class: "net.runelite.client.plugins.stretchedmode.StretchedModePlugin",
        capabilities: &[Capability::UiViewport],
        build: |source| boxed(stretchedmode::StretchedMode, source),
    },
    Entry {
        name: "entityhider",
        source_class: "net.runelite.client.plugins.entityhider.EntityHiderPlugin",
        capabilities: &[
            Capability::UiEntitiesHide,
            Capability::GameReadPlayers,
            Capability::GameReadNpcs,
        ],
        build: |source| boxed(entityhider::EntityHider, source),
    },
    Entry {
        name: "fps",
        source_class: "net.runelite.client.plugins.fps.FpsPlugin",
        capabilities: &[
            Capability::UiOverlay,
            Capability::ClientFrameLimit,
            Capability::GameReadClient,
            Capability::GameReadWidgets,
        ],
        build: |source| boxed(fps::Fps, source),
    },
    Entry {
        name: "statusbars",
        source_class: "net.runelite.client.plugins.statusbars.StatusBarsPlugin",
        capabilities: &[
            Capability::UiOverlay,
            Capability::GameReadWidgets,
            Capability::GameReadPlayers,
            Capability::GameReadNpcs,
            Capability::GameReadSkills,
            Capability::GameReadVars,
            Capability::GameReadClient,
        ],
        build: |source| boxed(statusbars::StatusBars::default(), source),
    },
    Entry {
        name: "idlenotifier",
        source_class: "net.runelite.client.plugins.idlenotifier.IdleNotifierPlugin",
        capabilities: &[
            Capability::ClientNotify,
            Capability::GameReadClient,
            Capability::GameReadPlayers,
            Capability::GameReadNpcs,
            Capability::GameReadSkills,
            Capability::GameReadVars,
        ],
        build: |source| boxed(idlenotifier::IdleNotifier::new(), source),
    },
    Entry {
        name: "interfacestyles",
        source_class: "net.runelite.client.plugins.interfacestyles.InterfaceStylesPlugin",
        capabilities: &[Capability::UiSpriteOverride],
        build: |source| boxed(interfacestyles::InterfaceStyles::default(), source),
    },
    Entry {
        name: "npchighlight",
        source_class: "net.runelite.client.plugins.npchighlight.NpcIndicatorsPlugin",
        capabilities: &[
            Capability::UiOverlay,
            Capability::GameReadNpcs,
            Capability::GameReadVars,
        ],
        build: |source| boxed(npchighlight::NpcHighlight, source),
    },
    Entry {
        name: "playerindicators",
        source_class: "net.runelite.client.plugins.playerindicators.PlayerIndicatorsPlugin",
        capabilities: &[
            Capability::UiOverlay,
            Capability::GameReadPlayers,
            Capability::GameReadVars,
        ],
        build: |source| boxed(playerindicators::PlayerIndicators, source),
    },
    Entry {
        name: "tileindicators",
        source_class: "net.runelite.client.plugins.tileindicators.TileIndicatorsPlugin",
        capabilities: &[
            Capability::UiOverlay,
            Capability::GameReadClient,
            Capability::GameReadPlayers,
            Capability::GameReadVars,
        ],
        build: |source| boxed(tileindicators::TileIndicators, source),
    },
    Entry {
        name: "minimap",
        source_class: "net.runelite.client.plugins.minimap.MinimapPlugin",
        capabilities: &[
            Capability::UiMinimap,
            Capability::GameReadClient,
            Capability::StoragePlugin,
        ],
        build: |source| boxed(minimap::Minimap::default(), source),
    },
    Entry {
        name: "camera",
        source_class: "net.runelite.client.plugins.camera.CameraPlugin",
        capabilities: &[Capability::CameraControl],
        build: |source| boxed(camera::Camera, source),
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
        set.register(manifest, (entry.build)(entry.source_class))?;
        // Off until the configuration owner reports the plugin's saved state.
        set.set_enabled(&id, false)?;
    }
    Ok(())
}

#[cfg(test)]
mod tests;
