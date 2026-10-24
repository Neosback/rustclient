//! Projection facade that sources ordinary actor translation from authoritative `GameState`.
//!
//! The legacy motion projector still owns disposable pose/turn state and forced-movement
//! interpolation. Ordinary players and NPCs with resolved path policy copy their drawn footprint
//! corner from `ActorPathQueue::fine_position`, so rendering consumers do not run a second ordinary
//! positional scheduler.

use crate::motion::{ActorMotion as LegacyActorMotion, ActorPose};
use rustclient_game_state::{ActorFinePosition, WorldEntitiesState};
use rustclient_model::{ClientCycle, NpcIndex, PlayerIndex};
use std::collections::{BTreeMap, BTreeSet};

/// Disposable draw projection with authoritative ordinary actor positions.
#[derive(Debug, Default)]
pub struct ActorMotion {
    legacy: LegacyActorMotion,
    player_positions: BTreeMap<PlayerIndex, [i32; 2]>,
    npc_positions: BTreeMap<NpcIndex, [i32; 2]>,
    exact_players: BTreeSet<PlayerIndex>,
}

impl ActorMotion {
    /// Reconcile disposable pose state and snapshot authoritative ordinary fine positions.
    pub fn observe(&mut self, entities: &WorldEntitiesState, cycle: ClientCycle) {
        self.legacy.observe(entities, cycle);
        self.player_positions
            .retain(|index, _| entities.players().contains_key(index));
        self.npc_positions
            .retain(|index, _| entities.npcs().contains_key(index));
        self.exact_players.clear();

        for (index, player) in entities.players() {
            if player.exact_move.is_some() {
                self.exact_players.insert(*index);
                continue;
            }
            self.player_positions.insert(
                *index,
                scene_position(player.path_queue.fine_position(player.tile)),
            );
        }

        for (index, npc) in entities.npcs() {
            if npc.exact_move.is_some() || npc.path_queue.acceleration_policy().is_none() {
                // Forced movement is still projection-owned. An unresolved direct fixture also
                // stays on the legacy path instead of pretending a cache policy was known.
                self.npc_positions.remove(index);
                continue;
            }
            self.npc_positions.insert(
                *index,
                scene_position(npc.path_queue.fine_position(npc.tile)),
            );
        }
    }

    /// Advance disposable pose/turn and forced-movement projection state.
    ///
    /// Ordinary positions returned by [`Self::player`] and [`Self::npc`] are not read from this
    /// legacy advancement path once their authoritative scheduler inputs are available.
    pub fn advance(&mut self, cycles: u32) {
        self.legacy.advance(cycles);
    }

    /// Footprint corner of a player in world fine units.
    #[must_use]
    pub fn player(&self, index: PlayerIndex) -> Option<[i32; 2]> {
        if self.exact_players.contains(&index) {
            return self.legacy.player(index);
        }
        self.player_positions
            .get(&index)
            .copied()
            .or_else(|| self.legacy.player(index))
    }

    /// Drawn rotation and movement pose of a player.
    #[must_use]
    pub(crate) fn player_pose(&self, index: PlayerIndex) -> Option<ActorPose> {
        self.legacy.player_pose(index)
    }

    /// Drawn rotation and movement pose of an NPC.
    #[must_use]
    pub(crate) fn npc_pose(&self, index: NpcIndex) -> Option<ActorPose> {
        self.legacy.npc_pose(index)
    }

    /// Footprint corner of an NPC in world fine units.
    ///
    /// Resolved ordinary movement comes from GameState. Forced movement and unresolved direct
    /// fixtures temporarily fall back to the legacy projector.
    #[must_use]
    pub fn npc(&self, index: NpcIndex) -> Option<[i32; 2]> {
        self.npc_positions
            .get(&index)
            .copied()
            .or_else(|| self.legacy.npc(index))
    }
}

fn scene_position(position: ActorFinePosition) -> [i32; 2] {
    let bounded = |value: i64| {
        i32::try_from(value.clamp(i64::from(i32::MIN), i64::from(i32::MAX)))
            .expect("clamped fine position fits i32")
    };
    [bounded(position.x), bounded(position.y)]
}

#[cfg(test)]
mod tests {
    use super::scene_position;
    use rustclient_game_state::ActorFinePosition;

    #[test]
    fn scene_position_preserves_normal_fine_coordinates_and_bounds_extremes() {
        assert_eq!(scene_position(ActorFinePosition { x: 128, y: -256 }), [128, -256]);
        assert_eq!(
            scene_position(ActorFinePosition {
                x: i64::MAX,
                y: i64::MIN,
            }),
            [i32::MAX, i32::MIN]
        );
    }
}
