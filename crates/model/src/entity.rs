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
    pub display_name: String,
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
            display_name: String::new(),
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

/// Forced movement block decoded from the revision-240 player update mask.
#[derive(Debug, Clone, Copy, PartialEq, Eq, Hash)]
pub struct ExactMove {
    pub start_delta_x: i8,
    pub start_delta_y: i8,
    pub end_delta_x: i8,
    pub end_delta_y: i8,
    pub start_delay: u16,
    pub end_delay: u16,
    pub direction: u16,
}

/// One high-resolution player change emitted by the revision-specific decoder.
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct PlayerUpdate {
    pub index: PlayerIndex,
    /// Resulting authoritative network tile after this packet's movement bits.
    pub tile: WorldTile,
    /// Present only when ordinary path movement survives all pending update masks.
    pub movement: Option<PlayerMovement>,
    /// Effective traversal speed for this movement, or the persistent speed when it changed.
    pub movement_speed: Option<MoveSpeed>,
    /// True when a low-resolution player became high-resolution in this packet.
    pub spawned: bool,
    pub exact_move: Option<ExactMove>,
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
            exact_move: None,
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

#[cfg(test)]
mod tracking_tests {
    use super::{MoveSpeed, PlayerTrackingGrid};
    #[test]
    fn tracking_grid_requires_nonzero_bounded_tile_extent() {
        assert!(PlayerTrackingGrid::new(0, 1).is_none());
        assert!(PlayerTrackingGrid::new(1, 0).is_none());
        assert!(PlayerTrackingGrid::new(u32::MAX, 2).is_none());
        assert!(PlayerTrackingGrid::new(i32::MAX.cast_unsigned(), 2).is_none());
        assert_eq!(
            PlayerTrackingGrid::new(8192, 256).unwrap().extent(),
            2_097_152
        );
    }

    #[test]
    fn movement_speed_wire_ids_match_revision_240() {
        assert_eq!(MoveSpeed::from_wire(-1), Some(MoveSpeed::Stationary));
        assert_eq!(MoveSpeed::from_wire(0), Some(MoveSpeed::Crawl));
        assert_eq!(MoveSpeed::from_wire(1), Some(MoveSpeed::Walk));
        assert_eq!(MoveSpeed::from_wire(2), Some(MoveSpeed::Run));
        assert_eq!(MoveSpeed::from_wire(127), None);
    }
}
