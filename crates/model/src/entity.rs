//! Semantic entity types: player appearance, slots, animation slots, and actor states.

use crate::{ItemDefinitionId, KitId, PlayerIndex, SequenceId, WorldTile};

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

    pub fn clear(&mut self) {
        self.sequence = None;
        self.frame = None;
        self.cycle = 0;
        self.delay = 0;
    }
}

/// Traversal speed carried by revision-240 player path entries.
#[derive(Debug, Clone, Copy, PartialEq, Eq, Hash, Default)]
pub enum MoveSpeed {
    Stationary,
    Crawl,
    #[default]
    Walk,
    Run,
}

impl MoveSpeed {
    #[must_use]
    pub const fn from_wire(value: i8) -> Option<Self> {
        match value {
            -1 => Some(Self::Stationary),
            0 => Some(Self::Crawl),
            1 => Some(Self::Walk),
            2 => Some(Self::Run),
            _ => None,
        }
    }
}

/// Player movement update decoded from player info.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum PlayerMovement {
    /// One-tile movement using the revision's canonical 0..=7 compass direction.
    Step { direction: u8 },
    /// Two-tile movement. Revision 240 encodes this as one 4-bit vector, not two steps.
    Run { delta_x: i8, delta_y: i8 },
    /// Plane-relative movement. X/Y are added with the client's 14-bit world wrapping rules.
    Teleport {
        plane_delta: u8,
        delta_x: i32,
        delta_y: i32,
    },
}

/// One high-resolution player change emitted by the revision-specific decoder.
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct PlayerUpdate {
    pub index: PlayerIndex,
    /// Resulting authoritative network tile after this packet's movement bits.
    pub tile: WorldTile,
    /// Present only when ordinary path movement survives all pending update masks.
    pub movement: Option<PlayerMovement>,
    pub movement_speed: Option<MoveSpeed>,
    /// True when a low-resolution player became high-resolution in this packet.
    pub spawned: bool,
    pub appearance: Option<PlayerAppearance>,
    /// `None` means no sequence mask; `Some(None)` explicitly clears the action sequence.
    pub sequence: Option<Option<SequenceId>>,
    pub sequence_delay: Option<u8>,
    pub facing: Option<u16>,
}

impl PlayerUpdate {
    #[must_use]
    pub const fn new(index: PlayerIndex, tile: WorldTile) -> Self {
        Self {
            index,
            tile,
            movement: None,
            movement_speed: None,
            spawned: false,
            appearance: None,
            sequence: None,
            sequence_delay: None,
            facing: None,
        }
    }
}

/// Consolidated revision-240 player info update for one tick.
#[derive(Debug, Clone, PartialEq, Eq, Default)]
pub struct PlayerInfoUpdate {
    pub players: Vec<PlayerUpdate>,
    pub removals: Vec<PlayerIndex>,
}

impl PlayerInfoUpdate {
    #[must_use]
    pub fn player(&self, index: PlayerIndex) -> Option<&PlayerUpdate> {
        self.players.iter().find(|update| update.index == index)
    }
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
}

/// Consolidated NPC info tick update.
#[derive(Debug, Clone, Default, PartialEq, Eq)]
pub struct NpcInfoUpdate {
    pub new_npcs: Vec<NewNpc>,
    pub movements: Vec<NpcMovement>,
    pub removals: Vec<crate::NpcIndex>,
}
