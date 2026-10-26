//! Port of `net.runelite.client.plugins.playerindicators` (BSD-2, see `NOTICE`): the service that picks
//! a colour for each player, the name overlay and the tile overlay.
//!
//! Players are coloured by relationship. The client does not keep the friends list, friends chat, clan
//! channel, party or team membership, so the party, friends, friends chat, team and clan settings never
//! match and every other player is treated as "others" (which, in the source, is exactly a player that
//! is in no friends chat and no clan). Rank icons, the coloured right-click menu and the minimap names
//! also need those lists, the menu model and a minimap overlay, and are not ported.

use crate::NativePlugin;
use crate::text::sanitize;
use rustclient_plugin_api::{
    ActorAnchor, ActorRef, HorizontalAlign, LayeredOverlay, OverlayCommand, OverlayLayer,
    PluginOutput,
};
use rustclient_plugin_host::{PluginCall, PluginFault};

/// `VarbitID.INSIDE_WILDERNESS` and `VarbitID.PVP_AREA_CLIENT`.
const INSIDE_WILDERNESS: u32 = 5963;
const PVP_AREA_CLIENT: u32 = 8121;
const OVERHEAD_TEXT_MARGIN: i32 = 40;
const HORIZONTAL_TEXT_MARGIN: i32 = 10;
/// `OverlayUtil.renderPolygon`'s default fill, `new Color(0, 0, 0, 50)`.
const TILE_FILL: u32 = 0x3200_0000;

pub(crate) struct PlayerIndicators;

/// `HighlightSetting.ENABLED`, or `PVP` while inside the wilderness or a `PvP` area.
fn enabled(setting: &str, in_pvp: bool) -> bool {
    setting == "ENABLED" || (setting == "PVP" && in_pvp)
}

impl NativePlugin for PlayerIndicators {
    fn output(&mut self, call: &mut PluginCall<'_>) -> Result<PluginOutput, PluginFault> {
        let config = call.config;
        let in_pvp = [INSIDE_WILDERNESS, PVP_AREA_CLIENT]
            .into_iter()
            .any(|id| call.varbit(id).ok().flatten() == Some(1));
        let position = config.choice("playerNamePosition");
        let draw_tiles = config.boolean("drawPlayerTiles");
        let mut output = PluginOutput::default();
        let Ok(players) = call.players() else {
            return Ok(output);
        };
        for player in players {
            if player.name.is_empty() {
                continue;
            }
            let color = if player.is_local {
                enabled(config.choice("highlightSelf"), in_pvp)
                    .then(|| config.color("ownNameColor"))
            } else {
                enabled(config.choice("highlightOthers"), in_pvp)
                    .then(|| config.color("nonClanMemberColor"))
            };
            let Some(color) = color else {
                continue;
            };
            let actor = ActorRef::Player(player.actor.index);
            if draw_tiles {
                output.overlay.push(LayeredOverlay {
                    layer: OverlayLayer::AboveScene,
                    command: OverlayCommand::Tile {
                        tile: player.actor.world,
                        size: 1,
                        argb: color,
                        fill_argb: TILE_FILL,
                        width: 2,
                    },
                });
            }
            let (anchor, z_offset, align, x_offset) = match position {
                "DISABLED" => continue,
                "MODEL_CENTER" => (ActorAnchor::Middle, 0, HorizontalAlign::Center, 0),
                "MODEL_RIGHT" => (
                    ActorAnchor::Middle,
                    0,
                    HorizontalAlign::Left,
                    HORIZONTAL_TEXT_MARGIN,
                ),
                _ => (
                    ActorAnchor::Top,
                    OVERHEAD_TEXT_MARGIN,
                    HorizontalAlign::Center,
                    0,
                ),
            };
            output.overlay.push(LayeredOverlay {
                layer: OverlayLayer::AboveScene,
                command: OverlayCommand::ActorText {
                    actor,
                    text: sanitize(&player.name),
                    z_offset,
                    // `OverlayUtil.renderTextLocation` draws the text opaque.
                    argb: color | 0xFF00_0000,
                    anchor,
                    align,
                    x_offset,
                },
            });
        }
        Ok(output)
    }
}

#[cfg(test)]
mod tests {
    use super::enabled;

    #[test]
    fn pvp_settings_only_apply_in_pvp_areas() {
        assert!(enabled("ENABLED", false));
        assert!(!enabled("PVP", false));
        assert!(enabled("PVP", true));
        assert!(!enabled("DISABLED", true));
    }
}
