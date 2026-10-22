//! Platform-neutral user input and game interaction intents.
//!
//! Platform adapters translate native/browser events into these values. Revision-specific
//! packet opcodes and renderer/GPU identities do not belong in this crate.

#![forbid(unsafe_code)]

use rustclient_model::{NpcIndex, ObjectDefinitionId, PlayerIndex, WorldTile};

/// Editing/navigation keys accepted by the local credential form.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum EditKey {
    /// Delete the last character.
    Backspace,
    /// Move focus; reverse when Shift is held.
    Tab {
        /// Whether Shift is held.
        reverse: bool,
    },
    /// Activate the default action.
    Enter,
    /// Return to welcome.
    Escape,
}

/// Zero-based action slot from an entity/location definition.
///
/// This is semantic menu ordering, not a protocol opcode. Revision adapters are responsible
/// for translating an accepted intent into the correct outbound packet family.
#[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord, Hash)]
pub struct ActionSlot(u8);

impl ActionSlot {
    /// Maximum number of ordinary interaction options exposed by classic definitions.
    pub const COUNT: u8 = 5;

    /// Construct an action slot in `0..5`.
    #[must_use]
    pub const fn new(index: u8) -> Option<Self> {
        if index < Self::COUNT {
            Some(Self(index))
        } else {
            None
        }
    }

    /// Return the zero-based definition action index.
    #[must_use]
    pub const fn index(self) -> u8 {
        self.0
    }
}

/// Stable semantic identity selected for a gameplay interaction.
///
/// Picking may originate from disposable render data, but the selected target crossing into
/// application/domain code must be expressed with semantic IDs and world coordinates.
#[derive(Debug, Clone, Copy, PartialEq, Eq, Hash)]
pub enum InteractionTarget {
    /// A world location/object definition at a world tile.
    Location {
        /// Object/location definition identity.
        object: ObjectDefinitionId,
        /// World tile used by the interaction packet semantics.
        tile: WorldTile,
    },
    /// Runtime NPC slot/index.
    Npc(NpcIndex),
    /// Runtime player slot/index.
    Player(PlayerIndex),
}

/// Platform- and revision-neutral gameplay intent produced by local user input.
///
/// These values describe what the user asked to do. They do not mutate `GameState` directly and
/// do not contain revision-specific packet details. The application layer validates an intent
/// against committed semantic state before a protocol profile is allowed to encode it.
#[derive(Debug, Clone, Copy, PartialEq, Eq, Hash)]
pub enum GameIntent {
    /// Request movement toward an exact world tile.
    WalkTo {
        /// Requested destination tile.
        destination: WorldTile,
    },
    /// Invoke one definition action slot on a selected semantic target.
    Interact {
        /// Selected world/entity target.
        target: InteractionTarget,
        /// Zero-based action slot from the target definition.
        action: ActionSlot,
    },
}

#[cfg(test)]
mod tests {
    use super::*;
    use rustclient_model::Plane;

    #[test]
    fn action_slots_are_bounded_to_definition_options() {
        for index in 0..ActionSlot::COUNT {
            let slot = ActionSlot::new(index).expect("ordinary action slot should be valid");
            assert_eq!(slot.index(), index);
        }

        assert_eq!(ActionSlot::new(ActionSlot::COUNT), None);
        assert_eq!(ActionSlot::new(u8::MAX), None);
    }

    #[test]
    fn walk_intent_preserves_typed_world_destination() {
        let destination = WorldTile::new(3_209, 3_218, Plane::MIN);
        let intent = GameIntent::WalkTo { destination };

        assert_eq!(intent, GameIntent::WalkTo { destination });
    }

    #[test]
    fn interaction_target_keeps_runtime_and_definition_identity_distinct() {
        let tile = WorldTile::new(3_210, 3_220, Plane::MIN);
        let location = InteractionTarget::Location {
            object: ObjectDefinitionId::new(1_234),
            tile,
        };
        let npc = InteractionTarget::Npc(NpcIndex::new(1_234));
        let player = InteractionTarget::Player(PlayerIndex::new(1_234));

        assert_ne!(location, npc);
        assert_ne!(location, player);
        assert_ne!(npc, player);
    }
}
