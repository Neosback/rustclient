//! Port of `net.runelite.client.plugins.minimap.MinimapPlugin` (BSD-2, see `NOTICE`).
//!
//! The source switches the injected client's minimap zoom on, replaces the seven map dot sprites
//! with single-colored ones, hides the minimap and its orbs on request, and saves the zoom level
//! every eleven seconds. Here the settings travel as one `PluginOutput::minimap`; the client
//! owns the zoom value, the wheel handling, the sprite replacement and the widget visibility.
//!
//! Not ported: the saved zoom level is applied only while `zoom` is on (the source also applies
//! it with the wheel off, where the level then cannot change), and the source's re-hide after the
//! top-level redraw scripts is the client re-applying the request every cycle.

use crate::NativePlugin;
use rustclient_plugin_api::{Capability, MinimapRequest, PluginOutput};
use rustclient_plugin_host::{PluginCall, PluginFault};

/// The key the zoom level is saved under (`MinimapConfig.GROUP`'s `zoomLevel`).
const ZOOM_KEY: &str = "zoomLevel";
/// `@Schedule(period = 11, unit = SECONDS)` in 20 ms client cycles.
const SAVE_PERIOD_CYCLES: u64 = 550;
/// Configuration keys of the dot colors, in map dot frame order: item, NPC, player, friend, team,
/// friends chat and clan chat.
const DOT_KEYS: [&str; MinimapRequest::DOT_KINDS] = [
    "item", "npc", "player", "friend", "team", "clan", "clanchat",
];

#[derive(Default)]
pub(crate) struct Minimap {
    /// The saved zoom the client has not been told about yet.
    pending_zoom: Option<f64>,
    /// The zoom level last written to storage.
    saved: f64,
    /// Client cycle of the last save, or of the first update after switching on.
    saved_at: Option<u64>,
}

impl Minimap {
    fn stored_zoom(call: &PluginCall<'_>) -> Option<f64> {
        call.storage
            .load(call.plugin)
            .get(ZOOM_KEY)
            .and_then(|text| text.parse::<f64>().ok())
            .filter(|zoom| zoom.is_finite() && *zoom > 0.)
    }
}

impl NativePlugin for Minimap {
    fn subscribes_game_events(&self) -> bool {
        false
    }
    fn init(&mut self, call: &mut PluginCall<'_>) -> Result<(), PluginFault> {
        let stored = call
            .granted(Capability::StoragePlugin)
            .then(|| Self::stored_zoom(call))
            .flatten();
        *self = Self {
            pending_zoom: stored.filter(|_| call.config.boolean("zoom")),
            saved: stored.unwrap_or(0.),
            saved_at: None,
        };
        Ok(())
    }
    fn output(&mut self, call: &mut PluginCall<'_>) -> Result<PluginOutput, PluginFault> {
        let config = call.config;
        let wheel_zoom = config.boolean("zoom");
        if wheel_zoom {
            self.save_zoom(call);
        }
        let mut dot_colors = [None; MinimapRequest::DOT_KINDS];
        for (slot, key) in dot_colors.iter_mut().zip(DOT_KEYS) {
            // Alpha zero is the source's null color: the stock sprite stays.
            *slot = Some(config.color(key)).filter(|argb| argb >> 24 != 0);
        }
        Ok(PluginOutput {
            minimap: Some(MinimapRequest {
                wheel_zoom,
                zoom: self.pending_zoom.take(),
                hidden: config.boolean("hideMinimap"),
                dot_colors,
            }),
            ..PluginOutput::default()
        })
    }
}

impl Minimap {
    /// `saveZoom`: write the client's zoom level at most once per period, and only when it moved.
    fn save_zoom(&mut self, call: &mut PluginCall<'_>) {
        if !call.granted(Capability::StoragePlugin) {
            return;
        }
        let Ok(client) = call.client() else {
            return;
        };
        let since = *self.saved_at.get_or_insert(client.cycle);
        // The first update may still hold the zoom from before the saved level was applied.
        if self.pending_zoom.is_some()
            || client.cycle.saturating_sub(since) < SAVE_PERIOD_CYCLES
            || client.minimap_zoom <= 0.
            || (client.minimap_zoom - self.saved).abs() < f64::EPSILON
        {
            return;
        }
        let mut values = call.storage.load(call.plugin);
        values.insert(ZOOM_KEY.to_owned(), client.minimap_zoom.to_string());
        call.storage.save(call.plugin, &values);
        self.saved = client.minimap_zoom;
        self.saved_at = Some(client.cycle);
    }
}
