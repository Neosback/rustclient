//! The facing block of an actor: what it turns toward and whether that makes it "interacting".
//!
//! Stock keeps one facing state per actor (`classBZ.method1384`): a walk policy (bits 0-2), a
//! primary payload (bits 3-5) and a control flag (bit 6). The payload names an entity, an area or
//! an angle, or nothing. `Actor.isInteracting()` is true exactly while it names a player or NPC.
use crate::{NpcIndex, PlayerIndex};

/// The collection an entity target lives in (`classLJ`).
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum FacingEntityKind {
    /// Kind 0: no entity.
    None,
    Npc,
    Player,
    WorldEntity,
}
/// What a facing block points at.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum FacingTarget {
    /// A live entity, tracked every cycle. `index` is `None` when the server sent the null index.
    Entity {
        kind: FacingEntityKind,
        index: Option<u32>,
        /// Angle used when the entity cannot be resolved.
        fallback_angle: u16,
    },
    /// A scenery area: south-west tile within the window and its extents in tiles.
    Area {
        x: u16,
        z: u16,
        size_x: u8,
        size_z: u8,
    },
    /// An explicit orientation in 0..2048 angle units.
    Angle(u16),
    /// Facing cleared.
    Cleared,
}
/// What an in-progress walk does to the facing (`classLV`).
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum FacingWalkPolicy {
    CancelOnWalk,
    TurnOnWalk,
}
/// One decoded facing block.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct FacingBlock {
    pub target: FacingTarget,
    pub walk: FacingWalkPolicy,
    /// Control flag (bit 6): turn to the target immediately instead of smoothly.
    pub instant: bool,
}
/// The actor a facing block makes its owner interact with.
#[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord)]
pub enum Interaction {
    Npc(NpcIndex),
    Player(PlayerIndex),
}
impl FacingBlock {
    /// The player or NPC this block targets, as `Actor.isInteracting()` defines it.
    #[must_use]
    pub fn interaction(&self) -> Option<Interaction> {
        match self.target {
            FacingTarget::Entity {
                kind: FacingEntityKind::Npc,
                index: Some(index),
                ..
            } => Some(Interaction::Npc(NpcIndex::new(index))),
            FacingTarget::Entity {
                kind: FacingEntityKind::Player,
                index: Some(index),
                ..
            } => Some(Interaction::Player(PlayerIndex::new(index))),
            _ => None,
        }
    }
}
/// The facing block of one visible player (local or remote).
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct PlayerFacing {
    pub index: PlayerIndex,
    pub block: FacingBlock,
}

#[cfg(test)]
mod tests {
    use super::*;

    fn entity(kind: FacingEntityKind, index: Option<u32>) -> FacingBlock {
        FacingBlock {
            target: FacingTarget::Entity {
                kind,
                index,
                fallback_angle: 0,
            },
            walk: FacingWalkPolicy::CancelOnWalk,
            instant: false,
        }
    }

    #[test]
    fn only_player_and_npc_targets_with_an_index_are_interactions() {
        assert_eq!(
            entity(FacingEntityKind::Npc, Some(7)).interaction(),
            Some(Interaction::Npc(NpcIndex::new(7)))
        );
        assert_eq!(
            entity(FacingEntityKind::Player, Some(3)).interaction(),
            Some(Interaction::Player(PlayerIndex::new(3)))
        );
        assert_eq!(entity(FacingEntityKind::Npc, None).interaction(), None);
        assert_eq!(
            entity(FacingEntityKind::WorldEntity, Some(1)).interaction(),
            None
        );
        assert_eq!(entity(FacingEntityKind::None, Some(1)).interaction(), None);
        let angle = FacingBlock {
            target: FacingTarget::Angle(512),
            ..entity(FacingEntityKind::None, None)
        };
        assert_eq!(angle.interaction(), None);
    }
}
