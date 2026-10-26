//! Port of `net.runelite.client.plugins.stretchedmode.StretchedModePlugin` (BSD-2, see `NOTICE`).
//!
//! The source switches `Client.setStretchedEnabled` on and forwards four settings to the injected
//! client, which resizes the canvas and scales it to the window; it also installs listeners that
//! translate mouse coordinates. Here the settings travel as one `PluginOutput::stretch`; the
//! layout (`rustclient_game_ui::stretch`) and the pointer transform
//! (`TitleViewport::pointer`) are owned by the client, so there is no listener to port.
//!
//! The source's `ResizeableChanged` and `ConfigChanged` handlers only invalidate cached
//! dimensions; the layout is recomputed from the window every frame, so they have no equivalent.

use crate::NativePlugin;
use rustclient_plugin_api::{PluginOutput, Stretch, StretchScaling};
use rustclient_plugin_host::{PluginCall, PluginFault};

#[derive(Default)]
pub(crate) struct StretchedMode;

impl NativePlugin for StretchedMode {
    fn output(&mut self, call: &mut PluginCall<'_>) -> Result<PluginOutput, PluginFault> {
        let config = call.config;
        let percent = config.integer("scalingFactor").clamp(
            i32::from(Stretch::MIN_SCALE_PERCENT),
            i32::from(Stretch::MAX_SCALE_PERCENT),
        );
        Ok(PluginOutput {
            stretch: Some(Stretch {
                keep_aspect_ratio: config.boolean("keepAspectRatio"),
                integer_scaling: config.boolean("integerScaling"),
                scale_percent: i16::try_from(percent).unwrap_or(0),
                scaling: if config.boolean("increasedPerformance") {
                    StretchScaling::Nearest
                } else {
                    StretchScaling::Linear
                },
            }),
            ..PluginOutput::default()
        })
    }
}
