//! Port of `net.runelite.client.plugins.tileindicators.TileIndicatorsPlugin` and its overlay
//! (BSD-2, see `NOTICE`).
//!
//! Up to three single-tile outlines per update, back to front as the source draws them: the tile
//! under the pointer, the tile the player is walking to and the tile the server has the player on.
//! Nothing is drawn during a cutscene (`CUTSCENE_STATUS` = 1). The projection through the terrain
//! heights is the host's `Tile` overlay command.
//!
//! Not ported: the destination is the whole-tile `MapDestination` the client keeps, so it does not
//! carry the sub-tile local point the source reads; the outline is the same.

use crate::NativePlugin;
use crate::npchighlight::{CUTSCENE_STATUS, layered, stroke_width};
use rustclient_plugin_api::{OverlayCommand, PluginOutput, WorldPoint};
use rustclient_plugin_host::{PluginCall, PluginFault};

pub(crate) struct TileIndicators;

/// The configuration keys of one outline's section.
struct Style {
    border: &'static str,
    fill: &'static str,
    width: &'static str,
}

const HOVERED: Style = Style {
    border: "highlightHoveredColor",
    fill: "hoveredTileFillColor",
    width: "hoveredTileBorderWidth",
};
const DESTINATION: Style = Style {
    border: "highlightDestinationColor",
    fill: "destinationTileFillColor",
    width: "destinationTileBorderWidth",
};
const CURRENT: Style = Style {
    border: "highlightCurrentColor",
    fill: "currentTileFillColor",
    width: "currentTileBorderWidth",
};

impl NativePlugin for TileIndicators {
    fn subscribes_game_events(&self) -> bool {
        false
    }
    fn output(&mut self, call: &mut PluginCall<'_>) -> Result<PluginOutput, PluginFault> {
        let mut output = PluginOutput::default();
        if call.varbit(CUTSCENE_STATUS).ok().flatten() == Some(1) {
            return Ok(output);
        }
        let config = call.config;
        let client = call.client().ok();
        let current = call
            .local_player()
            .ok()
            .flatten()
            .map(|player| player.actor.world);
        for (enabled, tile, style) in [
            (
                config.boolean("highlightHoveredTile"),
                client.and_then(|client| client.hovered_tile),
                HOVERED,
            ),
            (
                config.boolean("highlightDestinationTile"),
                client.and_then(|client| client.destination),
                DESTINATION,
            ),
            (config.boolean("highlightCurrentTile"), current, CURRENT),
        ] {
            let Some(tile) = tile.filter(|_| enabled) else {
                continue;
            };
            output.overlay.push(layered(outline(config, tile, &style)));
        }
        Ok(output)
    }
}

fn outline(
    config: &rustclient_plugin_host::PluginConfig,
    tile: WorldPoint,
    style: &Style,
) -> OverlayCommand {
    OverlayCommand::Tile {
        tile,
        size: 1,
        argb: config.color(style.border),
        fill_argb: config.color(style.fill),
        width: stroke_width(config.decimal(style.width)),
    }
}
