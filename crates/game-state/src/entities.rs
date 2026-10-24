//! Authoritative semantic entities: live players and NPCs in the active session.

use rustclient_model::{
    NpcDefinitionId, NpcIndex, Plane, PlayerAppearance, PlayerIndex, PlayerInfoUpdate, SequenceId,
    SequenceTiming, WorldTile,
};
use std::collections::{BTreeMap, BTreeSet};

/// Invalid entity transition, rejected before any authoritative mutation.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum EntityUpdateError {
    MissingLocalPlayer,
    InvalidMovement,
    PositionOverflow,
    InvalidNpcBatch,
    InvalidPlayerBatch,
}
impl core::fmt::Display for EntityUpdateError {
    fn fmt(&self, f: &mut core::fmt::Formatter<'_>) -> core::fmt::Result {
        f.write_str(match self {
            Self::MissingLocalPlayer => "entity update requires a placed local player",
            Self::InvalidMovement => "invalid entity movement direction or plane change",
            Self::InvalidNpcBatch => "invalid, stale or excessive NPC batch",
            Self::InvalidPlayerBatch => "invalid, stale or excessive player batch",
            Self::PositionOverflow => "entity movement exceeds world coordinate range",
        })
    }
}
impl std::error::Error for EntityUpdateError {}

/// Server-confirmed locomotion used to select the appearance's base sequence.
#[derive(Debug, Clone, Copy, Default, PartialEq, Eq)]
pub enum Locomotion {
    #[default]
    Idle,
    Walk,
    Run,
    Turn,
}
/// Authoritative live player entity.
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct PlayerEntity {
    pub index: PlayerIndex,
    pub instance: rustclient_model::PlayerInstanceId,
    pub tracking_grid: Option<rustclient_model::PlayerTrackingGrid>,
    pub path_speed: rustclient_model::MoveSpeed,
    pub movement_speed: rustclient_model::MoveSpeed,
    pub path_queue: crate::ActorPathQueue,
    pub exact_move: Option<crate::ExactMovement>,
    pub tile: WorldTile,
    /// Desired orientation selected by path/facing/forced-movement intent.
    pub orientation: u16,
    /// Current body rotation. Ordinary smooth-turn advancement is intentionally deferred until
    /// the exact revision-240 turn mutator is pinned.
    pub current_orientation: u16,
    pub appearance: Option<PlayerAppearance>,
    pub sequence: rustclient_model::ActorSequenceSlot,
    /// Whether definition timing for the currently active action was supplied when it became
    /// active. The shared timing registry is immutable evidence; this bit prevents an unresolved
    /// direct fixture from inheriting stale evidence for the same id from an older actor/action.
    pub sequence_timing_resolved: bool,
    pub animation_cycle: u32,
    pub locomotion: Locomotion,
}

/// Authoritative live NPC entity.
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct NpcEntity {
    pub index: NpcIndex,
    pub instance: rustclient_model::NpcInstanceId,
    pub definition_id: NpcDefinitionId,
    pub tile: WorldTile,
    /// Latest ordered server path request; the tile remains its confirmed endpoint.
    pub path: Option<crate::npcs::NpcPath>,
    pub path_queue: crate::ActorPathQueue,
    pub exact_move: Option<crate::ExactMovement>,
    /// Desired orientation selected by path/facing/forced-movement intent.
    pub orientation: u16,
    /// Current body rotation. Ordinary smooth-turn advancement is intentionally deferred until
    /// the exact revision-240 turn mutator is pinned.
    pub current_orientation: u16,
    pub sequence: rustclient_model::ActorSequenceSlot,
    pub sequence_timing_resolved: bool,
    pub name: Option<String>,
    pub actions: rustclient_model::ActionMask,
    pub locomotion: Locomotion,
    pub animation_cycle: u32,
}

/// Authoritative collection of active entities in the current session.
#[derive(Debug, Clone, Default, PartialEq, Eq)]
pub struct WorldEntitiesState {
    pub local_player_index: Option<PlayerIndex>,
    pub players: BTreeMap<PlayerIndex, PlayerEntity>,
    pub npcs: BTreeMap<NpcIndex, NpcEntity>,
    pub generation: u64,
    pub(crate) npc_serial: u64,
    pub(crate) player_serial: u64,
    pub(crate) outside_players: BTreeMap<PlayerIndex, crate::players::TrackedPlayer>,
    /// Immutable sequence timing evidence seen in cache-enriched entity updates. Live actors still
    /// carry an explicit resolved bit, so registry history alone never resolves a new assignment.
    pub(crate) sequence_timings: BTreeMap<SequenceId, SequenceTiming>,
    skills: BTreeMap<rustclient_model::SkillId, rustclient_model::SkillValues>,
    reboot_cycles: u32,
    reboot_message: String,
    energy: u16,
    weight: i16,
}

/// Convert movement direction (0..7) to (dx, dy).
#[must_use]
pub const fn direction_to_delta(direction: u8) -> (i32, i32) {
    match direction {
        0 => (-1, -1),
        1 => (0, -1),
        2 => (1, -1),
        3 => (-1, 0),
        4 => (1, 0),
        5 => (-1, 1),
        6 => (0, 1),
        7 => (1, 1),
        _ => (0, 0),
    }
}

/// Convert movement direction (0..7) to orientation (0..2047).
#[must_use]
pub const fn direction_to_orientation(direction: u8) -> u16 {
    match direction {
        0 => 256,
        1 => 0,
        2 => 1792,
        3 => 512,
        4 => 1536,
        5 => 768,
        7 => 1280,
        _ => 1024,
    }
}

impl WorldEntitiesState {
    #[must_use]
    pub const fn reboot_cycles(&self) -> u32 {
        self.reboot_cycles
    }
    #[must_use]
    pub fn reboot_message(&self) -> &str {
        &self.reboot_message
    }
    #[must_use]
    pub const fn skills(
        &self,
    ) -> &BTreeMap<rustclient_model::SkillId, rustclient_model::SkillValues> {
        &self.skills
    }
    #[must_use]
    pub const fn run_energy(&self) -> u16 {
        self.energy
    }
    #[must_use]
    pub const fn run_weight(&self) -> i16 {
        self.weight
    }
    pub(crate) fn status(
        &mut self,
        update: rustclient_model::PlayerStatusUpdate,
    ) -> Result<bool, EntityUpdateError> {
        use rustclient_model::PlayerStatusUpdate;
        Ok(match update {
            PlayerStatusUpdate::Reboot { cycles, message } => {
                if cycles > 65535 || message.as_ref().is_some_and(|s| s.len() > 4096) {
                    return Err(EntityUpdateError::InvalidMovement);
                }
                let changed = self.reboot_cycles != cycles
                    || message.as_ref().is_some_and(|s| *s != self.reboot_message);
                self.reboot_cycles = cycles;
                if let Some(message) = message {
                    self.reboot_message = message;
                }
                changed
            }
            PlayerStatusUpdate::Skill { id, values } => {
                if id.get() >= 256 {
                    return Err(EntityUpdateError::InvalidMovement);
                }
                if self.skills.get(&id) == Some(&values) {
                    false
                } else {
                    self.skills.insert(id, values);
                    true
                }
            }
            PlayerStatusUpdate::RunEnergy(n) => {
                if n > 10000 {
                    return Err(EntityUpdateError::InvalidMovement);
                }
                let changed = self.energy != n;
                self.energy = n;
                changed
            }
            PlayerStatusUpdate::RunWeight(n) => {
                let changed = self.weight != n;
                self.weight = n;
                changed
            }
        })
    }
    #[must_use]
    pub const fn generation(&self) -> u64 {
        self.generation
    }

    #[must_use]
    pub const fn local_player_index(&self) -> Option<PlayerIndex> {
        self.local_player_index
    }

    #[must_use]
    pub fn local_player(&self) -> Option<&PlayerEntity> {
        match self.local_player_index {
            Some(idx) => self.players.get(&idx),
            None => None,
        }
    }

    #[must_use]
    pub const fn players(&self) -> &BTreeMap<PlayerIndex, PlayerEntity> {
        &self.players
    }

    #[must_use]
    pub const fn npcs(&self) -> &BTreeMap<NpcIndex, NpcEntity> {
        &self.npcs
    }

    pub(crate) fn merge_sequence_timings(&mut self, timings: &[(SequenceId, SequenceTiming)]) {
        for (id, timing) in timings {
            self.sequence_timings.insert(*id, timing.clone());
        }
    }

    pub(crate) fn advance_cycles(
        &mut self,
        previous_cycle: rustclient_model::ClientCycle,
        cycles: u32,
    ) {
        self.reboot_cycles = self.reboot_cycles.saturating_sub(cycles);
        // Movement must observe the action delay/frame boundary before that same cycle advances
        // playback. The scenery clock remains the single ClientCycle authority.
        for offset in 1..=cycles {
            let current_cycle = previous_cycle
                .checked_advance(offset)
                .expect("scenery validated client-cycle advance");
            crate::actor_movement::advance_players(self, current_cycle);

            let timings = &self.sequence_timings;
            for player in self.players.values_mut() {
                player.animation_cycle = player.animation_cycle.wrapping_add(1);
                let timing = player
                    .sequence
                    .sequence
                    .filter(|_| player.sequence_timing_resolved)
                    .and_then(|id| timings.get(&id));
                crate::sequence_playback::advance_one(&mut player.sequence, timing);
                if player.sequence.sequence.is_none() {
                    player.sequence_timing_resolved = false;
                }
            }
            for npc in self.npcs.values_mut() {
                npc.animation_cycle = npc.animation_cycle.wrapping_add(1);
                let timing = npc
                    .sequence
                    .sequence
                    .filter(|_| npc.sequence_timing_resolved)
                    .and_then(|id| timings.get(&id));
                crate::sequence_playback::advance_one(&mut npc.sequence, timing);
                if npc.sequence.sequence.is_none() {
                    npc.sequence_timing_resolved = false;
                }
            }
        }
    }

    pub(crate) fn validate_player_info(
        &self,
        update: &PlayerInfoUpdate,
        cycle: rustclient_model::ClientCycle,
    ) -> Result<(), EntityUpdateError> {
        self.validate_remote_players(&update.remote, cycle)?;
        let timing_ids: BTreeSet<_> = update.sequence_timings.iter().map(|(id, _)| *id).collect();
        if timing_ids.len() != update.sequence_timings.len() {
            return Err(EntityUpdateError::InvalidPlayerBatch);
        }
        if update
            .local_appearance
            .as_ref()
            .is_some_and(|a| a.display_name.len() > 320)
        {
            return Err(EntityUpdateError::InvalidMovement);
        }
        let player = self
            .local_player()
            .ok_or(EntityUpdateError::MissingLocalPlayer)?;
        let tile = crate::player_movement::move_tile(player.tile, update.local_movement)?;
        if update.local_facing.is_some_and(|angle| angle >= 2048) {
            return Err(EntityUpdateError::InvalidMovement);
        }
        if let Some(exact) = update.local_exact_move {
            crate::player_movement::exact_movement(tile, cycle, exact)?;
        }
        Ok(())
    }
    pub(crate) fn apply_player_info(
        &mut self,
        update: &PlayerInfoUpdate,
        cycle: rustclient_model::ClientCycle,
    ) -> bool {
        let Some(local_idx) = self.local_player_index else {
            return false;
        };
        self.merge_sequence_timings(&update.sequence_timings);
        let player = self
            .players
            .entry(local_idx)
            .or_insert_with(|| PlayerEntity {
                index: local_idx,
                instance: rustclient_model::PlayerInstanceId::new(0),
                tracking_grid: None,
                path_speed: rustclient_model::MoveSpeed::Walk,
                movement_speed: rustclient_model::MoveSpeed::Walk,
                path_queue: crate::ActorPathQueue::default(),
                exact_move: None,
                tile: WorldTile::new(0, 0, Plane::MIN),
                orientation: 0,
                current_orientation: 0,
                appearance: None,
                sequence: rustclient_model::ActorSequenceSlot::default(),
                sequence_timing_resolved: false,
                animation_cycle: 0,
                locomotion: Locomotion::Idle,
            });

        let previous = player.clone();
        if let Some(speed) = update.local_path_speed {
            player.path_speed = speed;
        }
        crate::player_movement::apply_movement(
            player,
            update.local_movement,
            update.local_movement_speed,
        );
        if update.local_movement.is_none()
            && update
                .local_facing
                .is_some_and(|f| f != previous.orientation)
        {
            player.locomotion = Locomotion::Turn;
            player.animation_cycle = if previous.locomotion == Locomotion::Turn {
                previous.animation_cycle
            } else {
                0
            };
        }
        if let Some(exact) = update.local_exact_move {
            player.path_queue.clear();
            player.sequence.path_backlog_at_assignment = 0;
            player.exact_move = Some(
                crate::player_movement::exact_movement(player.tile, cycle, exact)
                    .expect("validated exact movement"),
            );
        }
        if let Some(appearance) = &update.local_appearance {
            player.appearance = Some(appearance.clone());
        }
        crate::sequence_scheduler::apply_change(
            &mut player.sequence,
            &mut player.sequence_timing_resolved,
            update.local_sequence,
            i32::from(update.local_sequence_delay),
            player.path_queue.backlog(),
            &update.sequence_policies,
            &update.sequence_timings,
        );
        if let Some(facing) = update.local_facing {
            player.orientation = facing;
        }
        let local_changed = *player != previous;
        let changed = self.apply_remote_players(
            &update.remote,
            cycle,
            &update.sequence_policies,
            &update.sequence_timings,
        ) || local_changed;
        if changed {
            self.generation = self.generation.wrapping_add(1);
        }
        changed
    }

    pub(crate) fn reset_animations(&mut self) -> bool {
        let mut changed = false;
        for player in self.players.values_mut() {
            changed |= player.sequence != rustclient_model::ActorSequenceSlot::default()
                || player.sequence_timing_resolved;
            player.sequence.clear();
            player.sequence_timing_resolved = false;
        }
        for npc in self.npcs.values_mut() {
            changed |= npc.sequence != rustclient_model::ActorSequenceSlot::default()
                || npc.sequence_timing_resolved;
            npc.sequence.clear();
            npc.sequence_timing_resolved = false;
        }
        if changed {
            self.generation = self.generation.wrapping_add(1);
        }
        changed
    }
}
