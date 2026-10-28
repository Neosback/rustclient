//! Semantic entity types: player appearance, slots, animation slots, and actor states.

use crate::{ItemDefinitionId, KitId, SequenceId, SequencePolicy};

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

/// Result of applying one action-sequence assignment to an actor slot.
#[derive(Debug, Clone, Copy, PartialEq, Eq, Hash)]
pub enum SequenceAssignmentOutcome {
    /// The action slot was cleared.
    Cleared,
    /// A different sequence was accepted and became active.
    Replaced,
    /// Same sequence, restart mode 1: playback and supplied delay were reset.
    Restarted,
    /// Same sequence, restart mode 2: only the iteration counter was reset.
    IterationReset,
    /// Same sequence with a restart mode that does not mutate playback.
    Unchanged,
    /// A lower-priority replacement was rejected.
    RejectedPriority,
    /// The active legacy slot has no policy, so replacement priority cannot be decided safely.
    MissingCurrentPolicy,
}

/// Runtime playback state for an entity action-animation sequence slot.
#[derive(Debug, Clone, Copy, PartialEq, Eq, Hash, Default)]
pub struct ActorSequenceSlot {
    pub sequence: Option<SequenceId>,
    /// Resolved definition policy for the active action sequence.
    pub policy: Option<SequencePolicy>,
    /// Legacy frame index or cached-model time position for resolved playback.
    pub frame: Option<u32>,
    /// Legacy within-frame counter; unresolved fixtures retain an aggregate compatibility timer.
    pub cycle: i32,
    pub delay: i32,
    /// Playback iteration counter. Restart mode 2 resets only this value.
    pub iterations: u32,
    /// Number of path entries that predated the currently accepted action assignment.
    pub path_backlog_at_assignment: u32,
}

impl ActorSequenceSlot {
    #[must_use]
    pub const fn is_active(self) -> bool {
        self.sequence.is_some()
    }

    /// Apply an action-sequence assignment using already-resolved semantic policy.
    ///
    /// The behavior matches the revision-240 action assignment control flow without embedding
    /// revision/cache knowledge here:
    ///
    /// - same id consults the active definition's restart mode and returns before priority checks,
    /// - restart mode 1 fully resets playback and reapplies the supplied delay,
    /// - restart mode 2 resets only the iteration counter,
    /// - a different lower-priority sequence is rejected,
    /// - accepted replacement captures the path backlog that existed at assignment time.
    ///
    /// `MissingCurrentPolicy` is intentionally non-mutating. A caller must resolve the active
    /// sequence definition before it can make an exact different-id priority decision.
    pub fn assign_sequence(
        &mut self,
        assignment: Option<(SequenceId, SequencePolicy)>,
        delay: i32,
        path_backlog: u32,
    ) -> SequenceAssignmentOutcome {
        let Some((sequence, incoming_policy)) = assignment else {
            self.clear();
            return SequenceAssignmentOutcome::Cleared;
        };

        if self.sequence == Some(sequence) {
            // Revision 240 reads the current sequence definition for same-id restart behavior.
            // A legacy slot may not have retained it, but the newly resolved definition is the
            // same sequence and can safely repair that missing policy before applying the mode.
            let active_policy = self.policy.unwrap_or(incoming_policy);
            self.policy = Some(active_policy);
            return match active_policy.restart_mode {
                1 => {
                    self.reset_playback(delay);
                    SequenceAssignmentOutcome::Restarted
                }
                2 => {
                    self.iterations = 0;
                    SequenceAssignmentOutcome::IterationReset
                }
                _ => SequenceAssignmentOutcome::Unchanged,
            };
        }

        if self.sequence.is_some() {
            let Some(active_policy) = self.policy else {
                return SequenceAssignmentOutcome::MissingCurrentPolicy;
            };
            if incoming_policy.replacement_priority < active_policy.replacement_priority {
                return SequenceAssignmentOutcome::RejectedPriority;
            }
        }

        self.sequence = Some(sequence);
        self.policy = Some(incoming_policy);
        self.reset_playback(delay);
        self.path_backlog_at_assignment = path_backlog;
        SequenceAssignmentOutcome::Replaced
    }

    /// Transitional unpolicy assignment used by packet paths that have not yet been connected to
    /// the sequence-policy resolver. It deliberately clears retained policy so a later exact
    /// replacement cannot accidentally use stale definition metadata.
    pub fn set_sequence(&mut self, sequence: Option<SequenceId>, delay: i32) {
        if self.sequence != sequence {
            self.sequence = sequence;
            self.policy = None;
            self.frame = sequence.map(|_| 0);
            self.cycle = 0;
            self.delay = delay;
            self.iterations = 0;
            self.path_backlog_at_assignment = 0;
        }
    }

    fn reset_playback(&mut self, delay: i32) {
        self.frame = Some(0);
        self.cycle = 0;
        self.delay = delay;
        self.iterations = 0;
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
        self.policy = None;
        self.frame = None;
        self.cycle = 0;
        self.delay = 0;
        self.iterations = 0;
        self.path_backlog_at_assignment = 0;
    }
}

/// Semantic traversal behavior for an actor path entry.
#[derive(Debug, Clone, Copy, PartialEq, Eq, Hash, Default)]
pub enum MoveSpeed {
    /// Clear queued steps and place the actor immediately.
    Stationary,
    Crawl,
    #[default]
    Walk,
    Run,
}
/// Relative server-authored trajectory. Delays count client cycles.
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
    /// Relative relocation on a periodic world grid whose extent is supplied by the profile.
    WrappedTeleport {
        offset: crate::TileOffset,
        plane_delta: u8,
        extent: u32,
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
    /// Profile-derived actor turn rate attached by orchestration, not by the wire decoder.
    pub turn_rate: Option<crate::JauTurnRate>,
    /// Speech blocks in source order; actors/appearances are installed before applying them.
    pub speech: Vec<crate::PlayerSpeech>,
    pub local_movement: Option<PlayerMovement>,
    /// Effective traversal speed for this tick, or the persistent speed when it changed.
    pub local_movement_speed: Option<MoveSpeed>,
    /// New default traversal; temporary movement speed does not replace this value.
    pub local_path_speed: Option<MoveSpeed>,
    /// Forced movement authored by the server for the local player.
    pub local_exact_move: Option<ExactMove>,
    pub local_appearance: Option<PlayerAppearance>,
    pub local_sequence: SequenceChange,
    pub local_sequence_delay: u8,
    /// Resolved scheduling definitions attached by application orchestration after protocol decode.
    /// An empty table preserves compatibility with direct semantic fixtures that have no cache.
    pub sequence_policies: Vec<(SequenceId, SequencePolicy)>,
    /// Resolved playback timing attached with the same sequence definitions. This is immutable
    /// cache-derived input for client-cycle scheduling such as forced-movement frame gating.
    pub sequence_timings: Vec<(SequenceId, crate::SequenceTiming)>,
    pub local_facing: Option<u16>,
    pub remote: Vec<RemotePlayerUpdate>,
}

/// Dimensions of the server's coarse out-of-view player tracking grid, in tiles.
/// Profiles choose dimensions; semantic state owns positions within the grid.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct PlayerTrackingGrid {
    region_size: u32,
    region_count: u32,
}
impl PlayerTrackingGrid {
    #[must_use]
    pub fn new(region_size: u32, region_count: u32) -> Option<Self> {
        let extent = region_size.checked_mul(region_count)?;
        (region_size > 0 && region_count > 0 && extent <= i32::MAX.cast_unsigned()).then_some(
            Self {
                region_size,
                region_count,
            },
        )
    }
    #[must_use]
    pub const fn region_size(self) -> u32 {
        self.region_size
    }
    #[must_use]
    pub const fn region_count(self) -> u32 {
        self.region_count
    }
    #[must_use]
    pub const fn extent(self) -> u32 {
        self.region_size * self.region_count
    }
}

/// Ordered changes to non-local player visibility, tracking and appearance.
#[derive(Debug, Clone, PartialEq, Eq)]
pub enum RemotePlayerUpdate {
    Track {
        index: crate::PlayerIndex,
        grid: PlayerTrackingGrid,
        region: crate::WorldTile,
    },
    Shift {
        index: crate::PlayerIndex,
        regions: crate::TileOffset,
        plane_delta: u8,
    },
    Enter {
        index: crate::PlayerIndex,
        offset: crate::TileOffset,
    },
    Leave {
        index: crate::PlayerIndex,
    },
    Move {
        index: crate::PlayerIndex,
        movement: Option<PlayerMovement>,
        /// Override for this path entry; omission uses the player's persistent speed.
        speed: Option<MoveSpeed>,
    },
    Attributes {
        index: crate::PlayerIndex,
        appearance: Option<PlayerAppearance>,
        sequence: SequenceChange,
        delay: u8,
        facing: Option<u16>,
        /// Persistent traversal speed for future path entries.
        path_speed: Option<MoveSpeed>,
        /// Forced movement block, when present.
        exact_move: Option<ExactMove>,
    },
}
impl RemotePlayerUpdate {
    #[must_use]
    pub const fn index(&self) -> crate::PlayerIndex {
        match self {
            Self::Track { index, .. }
            | Self::Shift { index, .. }
            | Self::Enter { index, .. }
            | Self::Leave { index }
            | Self::Move { index, .. }
            | Self::Attributes { index, .. } => *index,
        }
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
    /// First adjacent step, followed by an optional second step in wire order.
    pub first: crate::TileOffset,
    pub second: Option<crate::TileOffset>,
    pub speed: MoveSpeed,
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
    /// Resolved scheduling definitions attached by application orchestration after protocol decode.
    /// An empty table preserves compatibility with direct semantic fixtures that have no cache.
    pub sequence_policies: Vec<(SequenceId, SequencePolicy)>,
    /// Resolved playback timing attached with the same sequence definitions.
    pub sequence_timings: Vec<(SequenceId, crate::SequenceTiming)>,
    /// Resolved definition-derived path acceleration and turning attached after protocol decode.
    /// An empty table leaves direct semantic fixtures unresolved instead of guessing cache policy.
    pub movement_policies: Vec<(crate::NpcDefinitionId, crate::NpcMovementPolicy)>,
}

/// Server-authored changes to a visible NPC, applied in packet block order.
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct NpcAttributeUpdate {
    pub index: crate::NpcIndex,
    pub changes: Vec<NpcProperty>,
}
#[derive(Debug, Clone, PartialEq, Eq)]
pub enum NpcProperty {
    Overhead(String),
    ExactMove(ExactMove),
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
    use super::PlayerTrackingGrid;
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
}

#[cfg(test)]
mod sequence_assignment_tests {
    use super::{ActorSequenceSlot, SequenceAssignmentOutcome};
    use crate::{SequenceId, SequencePolicy};

    const fn policy(restart_mode: u8, priority: u8) -> SequencePolicy {
        SequencePolicy {
            restart_mode,
            replacement_priority: priority,
            movement_gate_with_backlog: 0,
            movement_gate_without_backlog: 0,
            max_loops: 99,
        }
    }

    #[test]
    fn lower_priority_replacement_is_rejected_without_mutation() {
        let mut slot = ActorSequenceSlot::default();
        assert_eq!(
            slot.assign_sequence(Some((SequenceId::new(10), policy(2, 5))), 3, 4),
            SequenceAssignmentOutcome::Replaced
        );
        slot.frame = Some(7);
        slot.cycle = 11;
        slot.iterations = 2;
        let before = slot;
        assert_eq!(
            slot.assign_sequence(Some((SequenceId::new(20), policy(2, 4))), 9, 8),
            SequenceAssignmentOutcome::RejectedPriority
        );
        assert_eq!(slot, before);
    }

    #[test]
    fn equal_priority_replacement_resets_playback_and_captures_backlog() {
        let mut slot = ActorSequenceSlot::default();
        slot.assign_sequence(Some((SequenceId::new(10), policy(2, 5))), 0, 2);
        slot.frame = Some(8);
        slot.cycle = 15;
        slot.iterations = 4;
        assert_eq!(
            slot.assign_sequence(Some((SequenceId::new(20), policy(1, 5))), 6, 7),
            SequenceAssignmentOutcome::Replaced
        );
        assert_eq!(slot.sequence, Some(SequenceId::new(20)));
        assert_eq!(slot.frame, Some(0));
        assert_eq!(slot.cycle, 0);
        assert_eq!(slot.delay, 6);
        assert_eq!(slot.iterations, 0);
        assert_eq!(slot.path_backlog_at_assignment, 7);
    }

    #[test]
    fn same_id_restart_mode_one_resets_playback_and_reapplies_delay_only() {
        let mut slot = ActorSequenceSlot::default();
        slot.assign_sequence(Some((SequenceId::new(10), policy(1, 5))), 2, 6);
        slot.frame = Some(9);
        slot.cycle = 21;
        slot.iterations = 3;
        assert_eq!(
            slot.assign_sequence(Some((SequenceId::new(10), policy(1, 99))), 8, 1),
            SequenceAssignmentOutcome::Restarted
        );
        assert_eq!(slot.frame, Some(0));
        assert_eq!(slot.cycle, 0);
        assert_eq!(slot.delay, 8);
        assert_eq!(slot.iterations, 0);
        assert_eq!(slot.path_backlog_at_assignment, 6);
        assert_eq!(slot.policy, Some(policy(1, 5)));
    }

    #[test]
    fn same_id_restart_mode_two_only_resets_iteration_counter() {
        let mut slot = ActorSequenceSlot::default();
        slot.assign_sequence(Some((SequenceId::new(10), policy(2, 5))), 4, 6);
        slot.frame = Some(9);
        slot.cycle = 21;
        slot.iterations = 3;
        let policy_before = slot.policy;
        assert_eq!(
            slot.assign_sequence(Some((SequenceId::new(10), policy(1, 99))), 8, 1),
            SequenceAssignmentOutcome::IterationReset
        );
        assert_eq!(slot.frame, Some(9));
        assert_eq!(slot.cycle, 21);
        assert_eq!(slot.delay, 4);
        assert_eq!(slot.iterations, 0);
        assert_eq!(slot.path_backlog_at_assignment, 6);
        assert_eq!(slot.policy, policy_before);
    }

    #[test]
    fn different_id_requires_current_policy_instead_of_guessing_priority() {
        let mut slot = ActorSequenceSlot::default();
        slot.set_sequence(Some(SequenceId::new(10)), 0);
        let before = slot;
        assert_eq!(
            slot.assign_sequence(Some((SequenceId::new(20), policy(2, 5))), 0, 3),
            SequenceAssignmentOutcome::MissingCurrentPolicy
        );
        assert_eq!(slot, before);
    }

    #[test]
    fn clear_removes_policy_and_assignment_backlog() {
        let mut slot = ActorSequenceSlot::default();
        slot.assign_sequence(Some((SequenceId::new(10), policy(2, 5))), 3, 7);
        assert_eq!(
            slot.assign_sequence(None, 99, 99),
            SequenceAssignmentOutcome::Cleared
        );
        assert_eq!(slot, ActorSequenceSlot::default());
    }
}
