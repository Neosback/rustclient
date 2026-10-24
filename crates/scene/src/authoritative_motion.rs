//! Projection facade that keeps ordinary player position sourced from authoritative `GameState`.
//!
//! The legacy motion projector still owns disposable pose/turn state and NPC interpolation. For
//! ordinary players, however, the drawn footprint corner is copied from
//! `ActorPathQueue::fine_position`, so rendering, camera and minimap consumers no longer run a
//! second positional scheduler. Exact/forced player movement remains on the legacy projection until
//! its frame-boundary gate is moved into `GameState`.

use crate::motion::{ActorMotion as LegacyActorMotion, ActorPose};
use rustclient_game_state::{ActorFinePosition, WorldEntitiesState};
use rustclient_model::{ClientCycle, NpcIndex, PlayerIndex};
use std::collections::{BTreeMap, BTreeSet};

/// Disposable draw projection with authoritative ordinary-player positions.
#[derive(Debug, Default)]
pub struct ActorMotion {
    legacy: LegacyActorMotion,
    player_positions: BTreeMap<PlayerIndex, [i32; 2]>,
    exact_players: BTreeSet<PlayerIndex>,
}

impl ActorMotion {
    /// Reconcile disposable pose/NPC state and snapshot authoritative player fine positions.
    pub fn observe(&mut self, entities: &WorldEntitiesState, cycle: ClientCycle) {
        self.legacy.observe(entities, cycle);
        self.player_positions
            .retain(|index, _| entities.players().contains_key(index));
        self.exact_players.clear();

        for (index, player) in entities.players() {
            if player.exact_move.is_some() {
                // Exact movement has not yet moved into authoritative fine scheduling. Preserve the
                // existing projection only for that explicitly documented boundary.
                self.exact_players.insert(*index);
                continue;
            }
            self.player_positions.insert(
                *index,
                scene_position(player.path_queue.fine_position(player.tile)),
            );
        }
    }

    /// Advance disposable pose/turn/NPC projection state.
    ///
    /// Ordinary player position returned by [`Self::player`] is never read from this legacy
    /// advancement path, so this cannot become a competing player-position authority.
    pub fn advance(&mut self, cycles: u32) {
        self.legacy.advance(cycles);
    }

    /// Footprint corner of a player in world fine units.
    ///
    /// Ordinary movement is sourced from authoritative GameState fine position. Exact movement is
    /// temporarily delegated to the legacy projector until its scheduler is ported.
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
    #[must_use]
    pub fn npc(&self, index: NpcIndex) -> Option<[i32; 2]> {
        self.legacy.npc(index)
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
