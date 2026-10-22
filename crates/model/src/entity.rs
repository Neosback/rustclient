//! Semantic entity types: player appearance, slots, animation slots, and actor states.

use crate::{ItemDefinitionId, KitId, SequenceId};

/// Total appearance body slots in client order.
pub const APPEARANCE_SLOTS: usize = 12;

/// Identity kit parts (hair, jaw, torso, arms, hands, legs, feet).
pub const KIT_PARTS: usize = 7;

/// The body slot each kit part fills in `[hair, jaw, torso, arms, hands, legs, feet]`.
pub const KIT_SLOT: [usize; KIT_PARTS] = [8, 11, 4, 6, 9, 7, 10];

/// Appearance colour slots: hair, torso, legs, feet, skin.
pub const COLOR_SLOTS: usize = 5;

/// Player gender / body type.
#[derive(Debug, Clone, Copy, PartialEq, Eq, Hash, Default)]
pub enum Gender {
    #[default]
    Male,
    Female,
}

/// Content filling one player appearance body slot.
#[derive(Debug, Clone, Copy, PartialEq, Eq, Hash, Default)]
pub enum SlotContent {
    #[default]
    Empty,
    Kit(KitId),
    Item(ItemDefinitionId),
}

/// Movement and idle animations authored by a player appearance or NPC definition.
#[derive(Debug, Clone, Copy, PartialEq, Eq, Hash, Default)]
pub struct MovementSequences {
    pub idle: Option<SequenceId>,
    pub turn: Option<SequenceId>,
    pub walk: Option<SequenceId>,
    pub walk_back: Option<SequenceId>,
    pub walk_left: Option<SequenceId>,
    pub walk_right: Option<SequenceId>,
    pub run: Option<SequenceId>,
}

/// Authoritative player appearance and equipment representation.
#[derive(Debug, Clone, PartialEq, Eq, Hash)]
pub struct PlayerAppearance {
    pub gender: Gender,
    pub slots: [SlotContent; APPEARANCE_SLOTS],
    pub colors: [u8; COLOR_SLOTS],
    pub movement_sequences: MovementSequences,
    pub combat_level: u8,
    pub skill_level: u16,
    pub hidden: bool,
}

impl Default for PlayerAppearance {
    fn default() -> Self {
        Self {
            gender: Gender::Male,
            slots: [SlotContent::Empty; APPEARANCE_SLOTS],
            colors: [0; COLOR_SLOTS],
            movement_sequences: MovementSequences::default(),
            combat_level: 3,
            skill_level: 0,
            hidden: false,
        }
    }
}

/// Runtime playback state for an entity animation sequence slot.
#[derive(Debug, Clone, Copy, PartialEq, Eq, Hash, Default)]
pub struct ActorSequenceSlot {
    pub sequence: Option<SequenceId>,
    pub frame: Option<u32>,
    pub cycle: i32,
    pub delay: i32,
}

impl ActorSequenceSlot {
    #[must_use]
    pub const fn is_active(self) -> bool {
        self.sequence.is_some()
    }

    pub fn set_sequence(&mut self, sequence: Option<SequenceId>, delay: i32) {
        if self.sequence != sequence {
            self.sequence = sequence;
            self.frame = Some(0);
            self.cycle = 0;
            self.delay = delay;
        }
    }

    /// Advance action playback after consuming its explicit start delay.
    pub fn advance_cycles(&mut self, cycles: u32) {
        if self.sequence.is_none() {
            return;
        }
        let delay = self.delay.max(0).cast_unsigned();
        let consumed = cycles.min(delay);
        self.delay -= consumed.cast_signed();
        self.cycle = self.cycle.wrapping_add((cycles - consumed).cast_signed());
    }
    pub fn clear(&mut self) {
        self.sequence = None;
        self.frame = None;
        self.cycle = 0;
        self.delay = 0;
    }
}

/// Player movement update decoded from player info.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum PlayerMovement {
    Step {
        direction: u8,
    },
    Run {
        direction1: u8,
        direction2: u8,
    },
    Teleport {
        delta_x: i32,
        delta_y: i32,
        plane_delta: u8,
    },
}

/// A sequence field can be absent, assigned, or explicitly cleared by the server.
#[derive(Debug, Clone, Copy, Default, PartialEq, Eq)]
pub enum SequenceChange {
    #[default]
    Unchanged,
    Set(Option<SequenceId>),
}
/// Consolidated player info update for one tick.
#[derive(Debug, Clone, PartialEq, Eq, Default)]
pub struct PlayerInfoUpdate {
    pub local_movement: Option<PlayerMovement>,
    pub local_appearance: Option<PlayerAppearance>,
    pub local_sequence: SequenceChange,
    pub local_facing: Option<u16>,
}

/// Newly spawned or high-resolution NPC.
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct NewNpc {
    pub index: crate::NpcIndex,
    pub definition_id: crate::NpcDefinitionId,
    pub delta_x: i32,
    pub delta_y: i32,
    pub orientation: u16,
}

/// NPC movement update.
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct NpcMovement {
    pub index: crate::NpcIndex,
    pub delta_x: i32,
    pub delta_y: i32,
    pub running: bool,
}

/// Consolidated NPC info tick update.
#[derive(Debug, Clone, Default, PartialEq, Eq)]
pub struct NpcInfoUpdate {
    /// Spawn origin relative to the current map anchor. None uses the local player.
    pub origin: Option<crate::TileOffset>,
    pub attributes: Vec<NpcAttributeUpdate>,
    pub new_npcs: Vec<NewNpc>,
    pub movements: Vec<NpcMovement>,
    pub removals: Vec<crate::NpcIndex>,
}

/// Server-authored changes to a visible NPC, applied in packet block order.
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct NpcAttributeUpdate {
    pub index: crate::NpcIndex,
    pub changes: Vec<NpcProperty>,
}
#[derive(Debug, Clone, PartialEq, Eq)]
pub enum NpcProperty {
    Definition(crate::NpcDefinitionId),
    Sequence {
        sequence: Option<SequenceId>,
        delay: u32,
    },
    Facing(u16),
    Name(Option<String>),
    Actions(crate::ActionMask),
}
