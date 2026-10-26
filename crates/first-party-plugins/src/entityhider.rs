//! Port of `net.runelite.client.plugins.entityhider.EntityHiderPlugin` (BSD-2, see `NOTICE`).
//!
//! The source registers a `RenderableDrawListener` that the injected client asks before drawing every
//! player, NPC, projectile, graphics object and boat, once for the model (`drawingUI == false`) and once
//! for its 2D overlays (`drawingUI == true`). Here the same decision is made once per update from the
//! snapshot and returned as `EntityVisibility::{hidden, hidden_overlays}`; the scene skips hidden models
//! (so they are not clickable either) and the overlay projector skips hidden overlays.
//!
//! Not ported, because the facts are not in the snapshot: party, friend, friends-chat, clan and ignore
//! membership, other players' pets (`NPCComposition.isFollower`), dying NPCs (`NpcUtil.isDying`) and boats.
//! Their switches are accepted and stored but change nothing; a player who would have been shown by one of
//! them is treated as an ordinary other player.

use crate::NativePlugin;
use rustclient_plugin_api::{ActorRef, EntityVisibility, PluginOutput};
use rustclient_plugin_host::{PluginCall, PluginFault};

/// `THRALL_IDS`: lesser, superior and greater ghost/skeleton/zombie thralls and the Leagues imp overrides.
const THRALLS: [u32; 18] = [
    10878, 10881, 10884, 10879, 10882, 10885, 10880, 10883, 10886, 15701, 15704, 15707, 15702,
    15705, 15708, 15703, 15706, 15709,
];

/// `RANDOM_EVENT_NPC_IDS`.
const RANDOM_EVENTS: [u32; 41] = [
    6747, 5426, 307, 314, 322, 6749, 390, 6754, 6744, 6748, 5429, 5430, 5431, 5432, 312, 13445,
    13446, 13443, 13444, 326, 327, 5438, 5441, 6746, 5437, 5440, 6750, 6751, 6752, 6753, 5436,
    5439, 380, 6738, 6755, 375, 376, 5510, 6743, 12551, 12552,
];

/// Which of an actor's draws the listener is asked about (`drawingUI`).
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub(crate) enum Layer {
    /// The 3D model.
    Model,
    /// The 2D overlays: overhead text, health bars, hitsplats.
    Overlay,
}

#[expect(
    clippy::struct_excessive_bools,
    reason = "one field per source setting"
)]
#[derive(Debug, Clone, Copy, Default)]
pub(crate) struct Switches {
    pub others: bool,
    pub others_2d: bool,
    pub local: bool,
    pub local_2d: bool,
    pub npcs: bool,
    pub npcs_2d: bool,
    pub attackers: bool,
    pub thralls: bool,
    pub random_events: bool,
}

/// The plugin's draw decision for a player; `ui` is `drawingUI`.
pub(crate) fn draws_player(
    switches: Switches,
    is_local: bool,
    interacting_local: bool,
    named: bool,
    layer: Layer,
) -> bool {
    if !named {
        return true;
    }
    let ui = layer == Layer::Overlay;
    if is_local {
        return !(if ui {
            switches.local_2d
        } else {
            switches.local
        });
    }
    if switches.attackers && interacting_local {
        return false;
    }
    !(if ui {
        switches.others_2d
    } else {
        switches.others
    })
}

/// The plugin's draw decision for an NPC.
pub(crate) fn draws_npc(
    switches: Switches,
    id: u32,
    interacting_local: bool,
    layer: Layer,
) -> bool {
    let ui = layer == Layer::Overlay;
    if interacting_local {
        let mut hide = switches.attackers;
        // Attackers only follow the 2D or 3D switch that is itself on.
        if switches.npcs_2d || switches.npcs {
            hide &= if ui { switches.npcs_2d } else { switches.npcs };
        }
        return !hide;
    }
    if THRALLS.contains(&id) {
        return !switches.thralls;
    }
    if RANDOM_EVENTS.contains(&id) {
        return !switches.random_events;
    }
    !(if ui { switches.npcs_2d } else { switches.npcs })
}

#[derive(Default)]
pub(crate) struct EntityHider;

impl NativePlugin for EntityHider {
    fn output(&mut self, call: &mut PluginCall<'_>) -> Result<PluginOutput, PluginFault> {
        let config = call.config;
        let switches = Switches {
            others: config.boolean("hidePlayers"),
            others_2d: config.boolean("hidePlayers2D"),
            local: config.boolean("hideLocalPlayer"),
            local_2d: config.boolean("hideLocalPlayer2D"),
            npcs: config.boolean("hideNPCs"),
            npcs_2d: config.boolean("hideNPCs2D"),
            attackers: config.boolean("hideAttackers"),
            thralls: config.boolean("hideThralls"),
            random_events: config.boolean("hideRandomEvents"),
        };
        let mut visibility = EntityVisibility {
            hide_projectiles: config.boolean("hideProjectiles"),
            ..EntityVisibility::default()
        };
        let local = call
            .local_player()
            .ok()
            .flatten()
            .map(|player| player.actor.index);
        if let Ok(players) = call.players() {
            for player in players {
                let interacting = local
                    .is_some_and(|local| player.actor.interacting == Some(ActorRef::Player(local)));
                let actor = ActorRef::Player(player.actor.index);
                let named = !player.name.is_empty();
                if !draws_player(switches, player.is_local, interacting, named, Layer::Model) {
                    visibility.hidden.push(actor);
                }
                if !draws_player(
                    switches,
                    player.is_local,
                    interacting,
                    named,
                    Layer::Overlay,
                ) {
                    visibility.hidden_overlays.push(actor);
                }
            }
        }
        if let Ok(npcs) = call.npcs() {
            for npc in npcs {
                let interacting = local
                    .is_some_and(|local| npc.actor.interacting == Some(ActorRef::Player(local)));
                let actor = ActorRef::Npc(npc.actor.index);
                if !draws_npc(switches, npc.id, interacting, Layer::Model) {
                    visibility.hidden.push(actor);
                }
                if !draws_npc(switches, npc.id, interacting, Layer::Overlay) {
                    visibility.hidden_overlays.push(actor);
                }
            }
        }
        Ok(PluginOutput {
            entities: Some(visibility),
            ..PluginOutput::default()
        })
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    const ALL_OFF: Switches = Switches {
        others: false,
        others_2d: false,
        local: false,
        local_2d: false,
        npcs: false,
        npcs_2d: false,
        attackers: false,
        thralls: false,
        random_events: false,
    };

    #[test]
    fn the_local_player_follows_only_its_own_switches() {
        let hide_others = Switches {
            others: true,
            others_2d: true,
            ..ALL_OFF
        };
        assert!(draws_player(hide_others, true, false, true, Layer::Model));
        let hide_self = Switches {
            local: true,
            ..ALL_OFF
        };
        assert!(!draws_player(hide_self, true, false, true, Layer::Model));
        // The 2D switch is separate: self can be hidden in 3D yet keep overhead text.
        assert!(draws_player(hide_self, true, false, true, Layer::Overlay));
    }

    #[test]
    fn other_players_split_three_and_two_dimensions_and_attackers_win() {
        let switches = Switches {
            others: true,
            ..ALL_OFF
        };
        assert!(!draws_player(switches, false, false, true, Layer::Model));
        assert!(draws_player(switches, false, false, true, Layer::Overlay));
        let attackers = Switches {
            attackers: true,
            ..ALL_OFF
        };
        assert!(!draws_player(attackers, false, true, true, Layer::Model));
        assert!(!draws_player(attackers, false, true, true, Layer::Overlay));
        assert!(draws_player(attackers, false, false, true, Layer::Model));
        // A player without a name is always drawn.
        assert!(draws_player(switches, false, true, false, Layer::Model));
    }

    #[test]
    fn npc_attackers_only_follow_the_dimension_whose_switch_is_on() {
        let attackers_and_3d = Switches {
            attackers: true,
            npcs: true,
            ..ALL_OFF
        };
        assert!(!draws_npc(attackers_and_3d, 1, true, Layer::Model));
        // The 2D hide is off, so an attacker keeps its 2D overlays.
        assert!(draws_npc(attackers_and_3d, 1, true, Layer::Overlay));
        // With no NPC hide switch at all, attackers hide in both.
        let only_attackers = Switches {
            attackers: true,
            ..ALL_OFF
        };
        assert!(!draws_npc(only_attackers, 1, true, Layer::Overlay));
        assert!(!draws_npc(only_attackers, 1, true, Layer::Model));
    }

    #[test]
    fn thralls_and_random_events_have_their_own_switches() {
        let thralls = Switches {
            thralls: true,
            ..ALL_OFF
        };
        assert!(!draws_npc(thralls, 10878, false, Layer::Model));
        assert!(draws_npc(thralls, 307, false, Layer::Model));
        let events = Switches {
            random_events: true,
            npcs: true,
            ..ALL_OFF
        };
        assert!(!draws_npc(events, 307, false, Layer::Model));
        // Thralls ignore the general NPC switch.
        assert!(draws_npc(events, 10878, false, Layer::Model));
        assert!(!draws_npc(events, 1, false, Layer::Model));
    }
}
