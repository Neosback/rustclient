//! Typed action slots shared by definitions, semantic entities and validated intents.
/// One of the five definition-authored world actions, in cache/menu order.
#[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord, Hash)]
pub struct ActionSlot(u8);
impl ActionSlot {
    #[must_use]
    pub const fn new(index: u8) -> Option<Self> {
        if index < 5 { Some(Self(index)) } else { None }
    }
    #[must_use]
    pub const fn get(self) -> u8 {
        self.0
    }
    #[must_use]
    pub const fn index(self) -> usize {
        self.0 as usize
    }
}
/// Server-controlled visibility of definition-authored world actions.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct ActionMask([bool; 5]);
impl ActionMask {
    pub const ALL: Self = Self([true; 5]);
    #[must_use]
    pub const fn new(enabled: [bool; 5]) -> Self {
        Self(enabled)
    }
    #[must_use]
    pub const fn allows(self, slot: ActionSlot) -> bool {
        self.0[slot.index()]
    }
    #[must_use]
    pub const fn enabled(self) -> [bool; 5] {
        self.0
    }
}
/// Signed displacement in world tile units, independent of plane and wire layout.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct TileOffset {
    pub x: i32,
    pub y: i32,
}

/// Session-local lifetime identity, distinct from a reusable NPC protocol index.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct NpcInstanceId(u64);
/// One visible incarnation of a reusable player index.
#[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord, Hash)]
pub struct PlayerInstanceId(u64);
impl PlayerInstanceId {
    #[must_use]
    pub const fn new(value: u64) -> Self {
        Self(value)
    }
    #[must_use]
    pub const fn get(self) -> u64 {
        self.0
    }
}
impl NpcInstanceId {
    #[must_use]
    pub const fn new(value: u64) -> Self {
        Self(value)
    }
    #[must_use]
    pub const fn get(self) -> u64 {
        self.0
    }
}
/// Captured identity of an interactable projection, revalidated when selected.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum WorldTarget {
    Npc {
        index: crate::NpcIndex,
        instance: NpcInstanceId,
        definition: crate::NpcDefinitionId,
        resolved: crate::NpcDefinitionId,
    },
    Location {
        key: crate::LocationKey,
        definition: crate::ObjectDefinitionId,
        resolved: crate::ObjectDefinitionId,
    },
}
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct WorldAction {
    pub scene: crate::SceneDescriptor,
    pub target: WorldTarget,
    pub slot: ActionSlot,
}

#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn action_slots_are_checked_and_masks_preserve_independent_visibility() {
        let mask = ActionMask::new([true, false, true, false, true]);
        for index in 0..5 {
            let slot = ActionSlot::new(index).unwrap();
            assert_eq!(slot.get(), index);
            assert_eq!(slot.index(), usize::from(index));
            assert_eq!(mask.allows(slot), index % 2 == 0);
            assert!(ActionMask::ALL.allows(slot));
        }
        assert_eq!(ActionSlot::new(5), None);
        assert_eq!(ActionSlot::new(u8::MAX), None);
        assert_eq!(NpcInstanceId::new(u64::MAX).get(), u64::MAX);
    }
}
