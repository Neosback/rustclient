//! Port of `net.runelite.client.plugins.npchighlight.NpcIndicatorsPlugin` and the shared
//! `game.npcoverlay.NpcOverlay` it renders through (BSD-2, see `NOTICE`).
//!
//! The source keeps a map of highlighted NPCs (by name pattern or by menu tag) and an overlay that
//! outlines each one. Here the name patterns are matched against the NPC snapshot every update and the
//! styles are returned as `ActorHull`, `Tile` and `ActorText` overlay commands.
//!
//! Not ported: the menu-driven tagging (tag, tag-all, per-NPC colour and style), the menu highlight
//! style, the respawn timer and the minimap names, because the client has no menu model and does not
//! retain minimap or death state; `ignoreDeadNpcs` and `ignorePets` need death and follower state;
//! the model outline needs an outline renderer. The local-position tile styles draw at the NPC's server
//! tile, because the snapshot carries no sub-tile position, so they equal the true-tile styles. NPCs
//! with no interact option (`isInteractible`) are not filtered out.

use crate::NativePlugin;
use crate::text::{from_csv, remove_tags, wildcard_matches};
use rustclient_plugin_api::{
    ActorAnchor, ActorRef, HorizontalAlign, LayeredOverlay, OverlayCommand, OverlayLayer,
    PluginOutput, WorldPoint,
};
use rustclient_plugin_host::{PluginCall, PluginFault};

/// `VarbitID.CUTSCENE_STATUS`.
const CUTSCENE_STATUS: u32 = 542;
/// `getLogicalHeight() + 40` is the name offset; the anchor here is already the model top.
const NAME_OFFSET: i32 = 40;

pub(crate) struct NpcHighlight;

/// Stroke width in whole pixels from the `borderWidth` decimal (thousandths).
fn stroke_width(thousandths: i32) -> u8 {
    u8::try_from(((thousandths + 500) / 1000).clamp(1, 8)).unwrap_or(2)
}

fn layered(command: OverlayCommand) -> LayeredOverlay {
    LayeredOverlay {
        layer: OverlayLayer::AboveScene,
        command,
    }
}

impl NativePlugin for NpcHighlight {
    fn output(&mut self, call: &mut PluginCall<'_>) -> Result<PluginOutput, PluginFault> {
        let config = call.config;
        let mut output = PluginOutput::default();
        if call.varbit(CUTSCENE_STATUS).ok().flatten() == Some(1) {
            return Ok(output);
        }
        let patterns = from_csv(config.text("npcToHighlight"));
        if patterns.is_empty() {
            return Ok(output);
        }
        let (hull, tile, true_tile, sw_tile, sw_true_tile) = (
            config.boolean("highlightHull"),
            config.boolean("highlightTile"),
            config.boolean("highlightTrueTile"),
            config.boolean("highlightSouthWestTile"),
            config.boolean("highlightSouthWestTrueTile"),
        );
        let border = config.color("npcColor");
        let fill = config.color("fillColor");
        let width = stroke_width(config.decimal("borderWidth"));
        let draw_names = config.boolean("drawNames");
        let Ok(npcs) = call.npcs() else {
            return Ok(output);
        };
        for npc in npcs {
            let Some(name) = npc.name.as_deref() else {
                continue;
            };
            if !patterns
                .iter()
                .any(|pattern| wildcard_matches(pattern, name))
            {
                continue;
            }
            let actor = ActorRef::Npc(npc.actor.index);
            let size = npc.size.0.max(npc.size.1).max(1);
            let at: WorldPoint = npc.actor.world;
            if hull {
                output.overlay.push(layered(OverlayCommand::ActorHull {
                    actor,
                    argb: border,
                    fill_argb: fill,
                    width,
                }));
            }
            for (enabled, area) in [
                (tile, size),
                (true_tile, size),
                (sw_tile, 1),
                (sw_true_tile, 1),
            ] {
                if enabled {
                    output.overlay.push(layered(OverlayCommand::Tile {
                        tile: at,
                        size: area,
                        argb: border,
                        fill_argb: fill,
                        width,
                    }));
                }
            }
            if draw_names {
                output.overlay.push(layered(OverlayCommand::ActorText {
                    actor,
                    text: remove_tags(name),
                    z_offset: NAME_OFFSET,
                    // `OverlayUtil.renderTextLocation` draws the text opaque.
                    argb: border | 0xFF00_0000,
                    anchor: ActorAnchor::Top,
                    align: HorizontalAlign::Center,
                    x_offset: 0,
                }));
            }
        }
        Ok(output)
    }
}

#[cfg(test)]
mod tests {
    use super::stroke_width;

    #[test]
    fn the_border_width_rounds_to_whole_pixels_within_the_host_range() {
        assert_eq!(stroke_width(2000), 2);
        assert_eq!(stroke_width(2499), 2);
        assert_eq!(stroke_width(2500), 3);
        assert_eq!(stroke_width(0), 1);
        assert_eq!(stroke_width(50_000), 8);
        assert_eq!(stroke_width(-4000), 1);
    }
}
