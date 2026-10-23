//! Authoritative semantic entities: live players and NPCs in the active session.

use rustclient_model::{
    NpcDefinitionId, NpcIndex, NpcInfoUpdate, Plane, PlayerAppearance, PlayerIndex,
    PlayerInfoUpdate, PlayerMovement, WorldTile,
};
use std::collections::BTreeMap;

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
    pub tile: WorldTile,
    pub orientation: u16,
    pub appearance: Option<PlayerAppearance>,
    pub sequence: rustclient_model::ActorSequenceSlot,
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
    pub orientation: u16,
    pub sequence: rustclient_model::ActorSequenceSlot,
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

    pub(crate) fn advance_cycles(&mut self, cycles: u32) {
        self.reboot_cycles = self.reboot_cycles.saturating_sub(cycles);
        for player in self.players.values_mut() {
            player.animation_cycle = player.animation_cycle.wrapping_add(cycles);
            player.sequence.advance_cycles(cycles);
        }
        for npc in self.npcs.values_mut() {
            npc.animation_cycle = npc.animation_cycle.wrapping_add(cycles);
            npc.sequence.advance_cycles(cycles);
        }
    }

    pub(crate) fn validate_player_info(
        &self,
        update: &PlayerInfoUpdate,
    ) -> Result<(), EntityUpdateError> {
        self.validate_remote_players(&update.remote)?;
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
        let (x, y) = match update.local_movement {
            None => (0, 0),
            Some(PlayerMovement::Step { direction }) if direction < 8 => {
                direction_to_delta(direction)
            }
            Some(PlayerMovement::Run {
                direction1,
                direction2,
            }) if direction1 < 8 && direction2 < 8 => {
                let a = direction_to_delta(direction1);
                let b = direction_to_delta(direction2);
                (a.0 + b.0, a.1 + b.1)
            }
            Some(PlayerMovement::Teleport {
                delta_x,
                delta_y,
                plane_delta,
            }) if plane_delta <= 3 => (delta_x, delta_y),
            _ => return Err(EntityUpdateError::InvalidMovement),
        };
        player
            .tile
            .x
            .checked_add(x)
            .ok_or(EntityUpdateError::PositionOverflow)?;
        player
            .tile
            .y
            .checked_add(y)
            .ok_or(EntityUpdateError::PositionOverflow)?;
        Ok(())
    }
    pub(crate) fn apply_player_info(&mut self, update: &PlayerInfoUpdate) -> bool {
        let Some(local_idx) = self.local_player_index else {
            return false;
        };
        let player = self
            .players
            .entry(local_idx)
            .or_insert_with(|| PlayerEntity {
                index: local_idx,
                instance: rustclient_model::PlayerInstanceId::new(0),
                tracking_grid: None,
                tile: WorldTile::new(0, 0, Plane::MIN),
                orientation: 0,
                appearance: None,
                sequence: rustclient_model::ActorSequenceSlot::default(),
                animation_cycle: 0,
                locomotion: Locomotion::Idle,
            });

        let previous = player.clone();
        let locomotion = match update.local_movement {
            Some(PlayerMovement::Step { .. }) => Locomotion::Walk,
            Some(PlayerMovement::Run { .. }) => Locomotion::Run,
            _ if update.local_facing.is_some_and(|f| f != player.orientation) => Locomotion::Turn,
            _ => Locomotion::Idle,
        };
        if player.locomotion != locomotion {
            player.animation_cycle = 0;
        }
        player.locomotion = locomotion;
        if let Some(movement) = &update.local_movement {
            match movement {
                PlayerMovement::Step { direction } => {
                    let (dx, dy) = direction_to_delta(*direction);
                    player.tile =
                        WorldTile::new(player.tile.x + dx, player.tile.y + dy, player.tile.plane);
                    player.orientation = direction_to_orientation(*direction);
                }
                PlayerMovement::Run {
                    direction1,
                    direction2,
                } => {
                    let (dx1, dy1) = direction_to_delta(*direction1);
                    let (dx2, dy2) = direction_to_delta(*direction2);
                    player.tile = WorldTile::new(
                        player.tile.x + dx1 + dx2,
                        player.tile.y + dy1 + dy2,
                        player.tile.plane,
                    );
                    player.orientation = direction_to_orientation(*direction2);
                }
                PlayerMovement::Teleport {
                    delta_x,
                    delta_y,
                    plane_delta,
                } => {
                    player.tile = WorldTile::new(
                        player.tile.x + delta_x,
                        player.tile.y + delta_y,
                        Plane::new((player.tile.plane.get() + plane_delta) & 3)
                            .unwrap_or(Plane::MIN),
                    );
                }
            }
        }
        if let Some(appearance) = &update.local_appearance {
            player.appearance = Some(appearance.clone());
        }
        if let rustclient_model::SequenceChange::Set(seq) = update.local_sequence {
            if seq.is_none() {
                player.sequence.clear();
            } else {
                player
                    .sequence
                    .set_sequence(seq, i32::from(update.local_sequence_delay));
            }
        }
        if let Some(facing) = update.local_facing {
            player.orientation = facing;
        }
        self.generation = self.generation.wrapping_add(1);
        let local_changed = *player != previous;
        self.apply_remote_players(&update.remote) || local_changed
    }

    pub(crate) fn reset_animations(&mut self) -> bool {
        let mut changed = false;
        for slot in self
            .players
            .values_mut()
            .map(|p| &mut p.sequence)
            .chain(self.npcs.values_mut().map(|n| &mut n.sequence))
        {
            changed |= *slot != rustclient_model::ActorSequenceSlot::default();
            slot.clear();
        }
        if changed {
            self.generation = self.generation.wrapping_add(1);
        }
        changed
    }

    pub(crate) fn validate_npc_info(
        &self,
        update: &NpcInfoUpdate,
        origin: WorldTile,
    ) -> Result<(), EntityUpdateError> {
        use rustclient_model::NpcProperty;
        use std::collections::BTreeSet;
        if update.new_npcs.len()
            + update.movements.len()
            + update.attributes.len()
            + update.removals.len()
            > 4096
        {
            return Err(EntityUpdateError::InvalidNpcBatch);
        }
        let removed: BTreeSet<_> = update.removals.iter().copied().collect();
        if removed.len() != update.removals.len()
            || removed.iter().any(|id| !self.npcs.contains_key(id))
        {
            return Err(EntityUpdateError::InvalidNpcBatch);
        }
        self.npc_serial
            .checked_add(
                u64::try_from(update.new_npcs.len())
                    .map_err(|_| EntityUpdateError::InvalidNpcBatch)?,
            )
            .ok_or(EntityUpdateError::InvalidNpcBatch)?;
        let mut added = BTreeSet::new();
        for npc in &update.new_npcs {
            if npc.orientation >= rustclient_model::JAU_FULL_TURN {
                return Err(EntityUpdateError::InvalidNpcBatch);
            }
            if !added.insert(npc.index)
                || self.npcs.contains_key(&npc.index) && !removed.contains(&npc.index)
            {
                return Err(EntityUpdateError::InvalidNpcBatch);
            }
            origin
                .x
                .checked_add(npc.delta_x)
                .ok_or(EntityUpdateError::PositionOverflow)?;
            origin
                .y
                .checked_add(npc.delta_y)
                .ok_or(EntityUpdateError::PositionOverflow)?;
        }
        if self.npcs.len() - removed.len() + added.len() > 16384 {
            return Err(EntityUpdateError::InvalidNpcBatch);
        }
        let mut moving = BTreeSet::new();
        for movement in &update.movements {
            let npc = self
                .npcs
                .get(&movement.index)
                .filter(|_| !removed.contains(&movement.index))
                .ok_or(EntityUpdateError::InvalidNpcBatch)?;
            if !moving.insert(movement.index) {
                return Err(EntityUpdateError::InvalidNpcBatch);
            }
            npc.tile
                .x
                .checked_add(movement.delta_x)
                .ok_or(EntityUpdateError::PositionOverflow)?;
            npc.tile
                .y
                .checked_add(movement.delta_y)
                .ok_or(EntityUpdateError::PositionOverflow)?;
        }
        for attributes in &update.attributes {
            if !added.contains(&attributes.index)
                && (removed.contains(&attributes.index)
                    || !self.npcs.contains_key(&attributes.index))
                || attributes.changes.len() > 32
            {
                return Err(EntityUpdateError::InvalidNpcBatch);
            }
            for change in &attributes.changes {
                match change {
                    NpcProperty::Name(Some(name)) if name.len() > 1024 => {
                        return Err(EntityUpdateError::InvalidNpcBatch);
                    }
                    NpcProperty::Sequence { delay, .. } if *delay > i32::MAX.cast_unsigned() => {
                        return Err(EntityUpdateError::InvalidNpcBatch);
                    }
                    NpcProperty::Facing(angle) if *angle >= rustclient_model::JAU_FULL_TURN => {
                        return Err(EntityUpdateError::InvalidNpcBatch);
                    }
                    _ => {}
                }
            }
        }
        Ok(())
    }
    pub(crate) fn apply_npc_info(&mut self, update: &NpcInfoUpdate, origin: WorldTile) -> bool {
        use rustclient_model::NpcProperty;
        let mut changed = !update.removals.is_empty() || !update.new_npcs.is_empty();
        for removal in &update.removals {
            self.npcs.remove(removal);
        }
        for npc in &update.new_npcs {
            self.npc_serial += 1;
            self.npcs.insert(
                npc.index,
                NpcEntity {
                    index: npc.index,
                    instance: rustclient_model::NpcInstanceId::new(self.npc_serial),
                    definition_id: npc.definition_id,
                    tile: WorldTile::new(
                        origin.x + npc.delta_x,
                        origin.y + npc.delta_y,
                        origin.plane,
                    ),
                    orientation: npc.orientation,
                    sequence: rustclient_model::ActorSequenceSlot::default(),
                    animation_cycle: 0,
                    name: None,
                    actions: rustclient_model::ActionMask::ALL,
                    locomotion: Locomotion::Idle,
                },
            );
        }
        for npc in self.npcs.values_mut() {
            let movement = update.movements.iter().find(|m| m.index == npc.index);
            let locomotion = match movement {
                Some(m) if m.running => Locomotion::Run,
                Some(m) if m.delta_x != 0 || m.delta_y != 0 => Locomotion::Walk,
                _ => Locomotion::Idle,
            };
            if npc.locomotion != locomotion {
                changed = true;
                npc.animation_cycle = 0;
            }
            npc.locomotion = locomotion;
            if let Some(movement) = movement {
                npc.tile = WorldTile::new(
                    npc.tile.x + movement.delta_x,
                    npc.tile.y + movement.delta_y,
                    npc.tile.plane,
                );
                let direction = match (movement.delta_x.signum(), movement.delta_y.signum()) {
                    (-1, -1) => 0,
                    (0, -1) => 1,
                    (1, -1) => 2,
                    (-1, 0) => 3,
                    (1, 0) => 4,
                    (-1, 1) => 5,
                    (0, 1) => 6,
                    _ => 7,
                };
                npc.orientation = direction_to_orientation(direction);
                changed = true;
            }
        }
        for attributes in &update.attributes {
            let Some(npc) = self.npcs.get_mut(&attributes.index) else {
                continue;
            };
            for change in &attributes.changes {
                match change {
                    NpcProperty::Definition(id) => {
                        changed |= npc.definition_id != *id;
                        npc.definition_id = *id;
                    }
                    NpcProperty::Sequence { sequence, delay } => {
                        let previous = npc.sequence;
                        if sequence.is_none() {
                            npc.sequence.clear();
                        } else {
                            npc.sequence.set_sequence(*sequence, delay.cast_signed());
                        }
                        changed |= npc.sequence != previous;
                    }
                    NpcProperty::Facing(angle) => {
                        changed |= npc.orientation != *angle;
                        npc.orientation = *angle;
                    }
                    NpcProperty::Name(name) => {
                        if npc.name != *name {
                            changed = true;
                            npc.name.clone_from(name);
                        }
                    }
                    NpcProperty::Actions(actions) => {
                        changed |= npc.actions != *actions;
                        npc.actions = *actions;
                    }
                }
            }
        }
        if changed {
            self.generation = self.generation.wrapping_add(1);
        }
        changed
    }
}
