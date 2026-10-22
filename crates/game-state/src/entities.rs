//! Authoritative semantic entities: live players and NPCs in the active session.

use rustclient_model::{
    NpcDefinitionId, NpcIndex, NpcInfoUpdate, Plane, PlayerAppearance, PlayerIndex,
    PlayerInfoUpdate, PlayerMovement, SequenceId, WorldTile,
};
use std::collections::BTreeMap;

/// Authoritative live player entity.
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct PlayerEntity {
    pub index: PlayerIndex,
    pub tile: WorldTile,
    pub orientation: u16,
    pub appearance: Option<PlayerAppearance>,
    pub sequence: Option<SequenceId>,
    pub animation_cycle: u32,
}

/// Authoritative live NPC entity.
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct NpcEntity {
    pub index: NpcIndex,
    pub definition_id: NpcDefinitionId,
    pub tile: WorldTile,
    pub orientation: u16,
    pub sequence: Option<SequenceId>,
    pub animation_cycle: u32,
}

/// Authoritative collection of active entities in the current session.
#[derive(Debug, Clone, Default, PartialEq, Eq)]
pub struct WorldEntitiesState {
    pub local_player_index: Option<PlayerIndex>,
    pub players: BTreeMap<PlayerIndex, PlayerEntity>,
    pub npcs: BTreeMap<NpcIndex, NpcEntity>,
    pub generation: u64,
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
        0 => 768,
        1 => 1024,
        2 => 1280,
        3 => 512,
        4 => 1536,
        5 => 256,
        7 => 1792,
        _ => 0,
    }
}

impl WorldEntitiesState {
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

    pub fn advance_cycles(&mut self, cycles: u32) {
        for player in self.players.values_mut() {
            player.animation_cycle = player.animation_cycle.wrapping_add(cycles);
        }
        for npc in self.npcs.values_mut() {
            npc.animation_cycle = npc.animation_cycle.wrapping_add(cycles);
        }
    }

    pub fn clear(&mut self) {
        self.local_player_index = None;
        self.players.clear();
        self.npcs.clear();
        self.generation = self.generation.wrapping_add(1);
    }

    pub fn apply_player_info(&mut self, update: &PlayerInfoUpdate) {
        let Some(local_idx) = self.local_player_index else {
            return;
        };
        let player = self
            .players
            .entry(local_idx)
            .or_insert_with(|| PlayerEntity {
                index: local_idx,
                tile: WorldTile::new(0, 0, Plane::MIN),
                orientation: 0,
                appearance: None,
                sequence: None,
                animation_cycle: 0,
            });

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
                PlayerMovement::Teleport { delta_x, delta_y } => {
                    player.tile = WorldTile::new(
                        player.tile.x + delta_x,
                        player.tile.y + delta_y,
                        player.tile.plane,
                    );
                }
            }
        }
        if let Some(appearance) = &update.local_appearance {
            player.appearance = Some(appearance.clone());
        }
        if let Some(seq) = update.local_sequence {
            player.sequence = Some(seq);
        }
        if let Some(facing) = update.local_facing {
            player.orientation = facing;
        }
        self.generation = self.generation.wrapping_add(1);
    }

    pub fn apply_npc_info(&mut self, update: &NpcInfoUpdate, local_tile: WorldTile) {
        for removal in &update.removals {
            self.npcs.remove(removal);
        }
        for new_npc in &update.new_npcs {
            let tile = WorldTile::new(
                local_tile.x + new_npc.delta_x,
                local_tile.y + new_npc.delta_y,
                local_tile.plane,
            );
            self.npcs.insert(
                new_npc.index,
                NpcEntity {
                    index: new_npc.index,
                    definition_id: new_npc.definition_id,
                    tile,
                    orientation: new_npc.orientation,
                    sequence: None,
                    animation_cycle: 0,
                },
            );
        }
        for mov in &update.movements {
            if let Some(npc) = self.npcs.get_mut(&mov.index) {
                npc.tile = WorldTile::new(
                    npc.tile.x + mov.delta_x,
                    npc.tile.y + mov.delta_y,
                    npc.tile.plane,
                );
            }
        }
        self.generation = self.generation.wrapping_add(1);
    }
}
