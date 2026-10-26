//! Projection facade that sources actor translation from authoritative `GameState`.
//!
//! The legacy motion projector still owns disposable pose playback state. Ordinary players, exact
//! players, exact NPCs, and NPCs with resolved ordinary path policy copy their drawn footprint
//! corner from `ActorPathQueue::fine_position`, so rendering consumers never run a second
//! authoritative positional scheduler.

use crate::motion::{ActorMotion as LegacyActorMotion, ActorPose, CommittedTurning};
use rustclient_game_state::{ActorFinePosition, WorldEntitiesState};
use rustclient_model::{ClientCycle, NpcIndex, NpcInstanceId, PlayerIndex, PlayerInstanceId};
use std::collections::BTreeMap;

/// Disposable draw projection with authoritative actor positions.
#[derive(Debug, Default)]
pub struct ActorMotion {
    legacy: LegacyActorMotion,
    player_positions: BTreeMap<PlayerIndex, (PlayerInstanceId, [i32; 2])>,
    npc_positions: BTreeMap<NpcIndex, (NpcInstanceId, [i32; 2])>,
}

impl ActorMotion {
    /// Reconcile disposable pose state and snapshot authoritative fine positions.
    pub fn observe(&mut self, entities: &WorldEntitiesState, cycle: ClientCycle) {
        self.legacy.observe(entities, cycle);
        self.player_positions
            .retain(|index, _| entities.players().contains_key(index));
        self.npc_positions
            .retain(|index, _| entities.npcs().contains_key(index));

        for (index, player) in entities.players() {
            let position = scene_position(player.path_queue.fine_position(player.tile));
            let previous = self
                .player_positions
                .insert(*index, (player.instance, position))
                .filter(|(instance, _)| *instance == player.instance)
                .map(|(_, position)| position);
            self.legacy.committed_player(
                *index,
                position,
                previous,
                Some(CommittedTurning {
                    rotation: player.current_orientation,
                    state: player.turning,
                }),
            );
        }

        for (index, npc) in entities.npcs() {
            if npc.exact_move.is_none() && npc.path_queue.acceleration_policy().is_none() {
                // Deliberately unresolved direct ordinary-NPC fixtures retain the legacy fallback;
                // exact movement itself no longer depends on NPC path-definition policy.
                self.npc_positions.remove(index);
                continue;
            }
            let position = scene_position(npc.path_queue.fine_position(npc.tile));
            let previous = self
                .npc_positions
                .insert(*index, (npc.instance, position))
                .filter(|(instance, _)| *instance == npc.instance)
                .map(|(_, position)| position);
            self.legacy.committed_npc(
                *index,
                position,
                previous,
                Some(CommittedTurning {
                    rotation: npc.current_orientation,
                    state: npc.turning,
                }),
            );
        }
    }

    /// Advance disposable pose playback; committed actors never run the fallback turn scheduler.
    ///
    /// Actor positions returned by [`Self::player`] and resolved [`Self::npc`] paths are never read
    /// from this legacy advancement path.
    pub fn advance(&mut self, cycles: u32) {
        self.legacy.advance(cycles);
    }

    /// Footprint corner of a player in world fine units.
    #[must_use]
    pub fn player(&self, index: PlayerIndex) -> Option<[i32; 2]> {
        self.player_positions
            .get(&index)
            .map(|(_, position)| *position)
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
    /// Exact movement and cache-resolved ordinary movement come from `GameState`. Only deliberately
    /// unresolved direct ordinary fixtures can fall back to the legacy projector.
    #[must_use]
    pub fn npc(&self, index: NpcIndex) -> Option<[i32; 2]> {
        self.npc_positions
            .get(&index)
            .map(|(_, position)| *position)
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

    use super::ActorMotion;
    use rustclient_game_state::{
        AuthenticatedPlayer, GameState, InitialPlacement, SceneryOperation, SemanticOperation,
    };
    use rustclient_model::*;

    fn initial() -> GameState {
        let mut state = GameState::new();
        state
            .apply_operation(SemanticOperation::AuthenticateSession {
                revision: REVISION_240,
                player: AuthenticatedPlayer {
                    profile: ServerProfileId::new(1),
                    player: PlayerIndex::new(1),
                    member: false,
                    staff_level: 0,
                },
            })
            .unwrap();
        state
            .apply_operation(SemanticOperation::EstablishInitialPlacement(
                InitialPlacement {
                    position: WorldTile::new(3220, 3220, Plane::MIN),
                    map_anchor: WorldTile::new(3216, 3216, Plane::MIN),
                    area: None,
                },
            ))
            .unwrap();
        state
            .apply_operation(SemanticOperation::UpdatePlayers(PlayerInfoUpdate {
                turn_rate: Some(JauTurnRate::new(32)),
                ..Default::default()
            }))
            .unwrap();
        state
    }

    #[test]
    fn committed_rotations_survive_projection_rebuild_without_independent_turns() {
        let mut state = initial();
        state
            .apply_operation(SemanticOperation::UpdateNpcs(NpcInfoUpdate {
                new_npcs: vec![NewNpc {
                    index: NpcIndex::new(4),
                    definition_id: NpcDefinitionId::new(12),
                    delta_x: 0,
                    delta_y: 0,
                    orientation: 0,
                }],
                movement_policies: vec![(
                    NpcDefinitionId::new(12),
                    NpcMovementPolicy {
                        path_acceleration: PathAccelerationPolicy::STANDARD,
                        turn_rate: JauTurnRate::new(64),
                    },
                )],
                attributes: vec![NpcAttributeUpdate {
                    index: NpcIndex::new(4),
                    changes: vec![NpcProperty::Facing(512)],
                }],
                ..Default::default()
            }))
            .unwrap();
        state
            .apply_operation(SemanticOperation::UpdatePlayers(PlayerInfoUpdate {
                local_facing: Some(1024),
                ..Default::default()
            }))
            .unwrap();
        state
            .apply_operation(SemanticOperation::Scenery(SceneryOperation::AdvanceCycles(
                3,
            )))
            .unwrap();
        let mut motion = ActorMotion::default();
        motion.observe(state.entities(), state.scenery().cycle());
        assert_eq!(motion.npc_pose(NpcIndex::new(4)).unwrap().rotation, 192);
        assert_eq!(
            motion.player_pose(PlayerIndex::new(1)).unwrap().rotation,
            96
        );
        motion.advance(20);
        assert_eq!(motion.npc_pose(NpcIndex::new(4)).unwrap().rotation, 192);
        assert_eq!(
            motion.player_pose(PlayerIndex::new(1)).unwrap().rotation,
            96
        );
        assert_eq!(
            motion.npc_pose(NpcIndex::new(4)).unwrap().pose,
            crate::motion::Pose::TurnRight
        );
        let mut rebuilt = ActorMotion::default();
        rebuilt.observe(state.entities(), state.scenery().cycle());
        rebuilt.advance(1);
        assert_eq!(
            rebuilt.npc_pose(NpcIndex::new(4)).unwrap().rotation,
            motion.npc_pose(NpcIndex::new(4)).unwrap().rotation
        );
        state
            .apply_operation(SemanticOperation::UpdateNpcs(NpcInfoUpdate {
                removals: vec![NpcIndex::new(4)],
                new_npcs: vec![NewNpc {
                    index: NpcIndex::new(4),
                    definition_id: NpcDefinitionId::new(12),
                    delta_x: 20,
                    delta_y: 20,
                    orientation: 1024,
                }],
                movement_policies: vec![(
                    NpcDefinitionId::new(12),
                    NpcMovementPolicy {
                        path_acceleration: PathAccelerationPolicy::STANDARD,
                        turn_rate: JauTurnRate::new(0),
                    },
                )],
                ..Default::default()
            }))
            .unwrap();
        motion.observe(state.entities(), state.scenery().cycle());
        motion.advance(1);
        let pose = motion.npc_pose(NpcIndex::new(4)).unwrap();
        assert_eq!(pose.rotation, 0);
        assert_eq!(
            pose.pose,
            crate::motion::Pose::Idle,
            "index reuse must not become a synthetic movement delta"
        );
    }

    #[test]
    fn scene_position_preserves_normal_fine_coordinates_and_bounds_extremes() {
        assert_eq!(
            scene_position(ActorFinePosition { x: 128, y: -256 }),
            [128, -256]
        );
        assert_eq!(
            scene_position(ActorFinePosition {
                x: i64::MAX,
                y: i64::MIN,
            }),
            [i32::MAX, i32::MIN]
        );
    }
}
