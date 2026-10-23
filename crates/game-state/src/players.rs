//! Out-of-view tracking and visible remote players share the entity state owner.
use crate::{EntityUpdateError as Error, Locomotion, PlayerEntity, WorldEntitiesState};
use rustclient_model::{
    ActorSequenceSlot, Plane, PlayerAppearance, PlayerIndex, PlayerInstanceId, PlayerMovement,
    PlayerTrackingGrid, RemotePlayerUpdate as Update, SequenceChange, TileOffset, WorldTile,
};
use std::collections::BTreeMap;

#[derive(Debug, Clone, PartialEq, Eq)]
pub struct TrackedPlayer {
    pub grid: PlayerTrackingGrid,
    pub region: WorldTile,
    pub appearance: Option<PlayerAppearance>,
    pub orientation: u16,
}
#[derive(Clone, Copy)]
enum Position {
    Outside(PlayerTrackingGrid, WorldTile),
    Visible(PlayerTrackingGrid, WorldTile),
}
impl WorldEntitiesState {
    #[must_use]
    pub fn tracked_player(&self, index: PlayerIndex) -> Option<&TrackedPlayer> {
        self.outside_players.get(&index)
    }
    fn player_position(&self, index: PlayerIndex) -> Option<Position> {
        if let Some(player) = self.players.get(&index) {
            return player
                .tracking_grid
                .map(|grid| Position::Visible(grid, player.tile));
        }
        self.outside_players
            .get(&index)
            .map(|p| Position::Outside(p.grid, p.region))
    }
    /// Plan positions for only the touched indexes. No appearance copies or live mutations.
    pub(crate) fn validate_remote_players(&self, updates: &[Update]) -> Result<(), Error> {
        if updates.len() > 16_384 {
            return Err(Error::InvalidPlayerBatch);
        }
        let mut planned = BTreeMap::new();
        let mut count = self.players.len() + self.outside_players.len();
        let mut promotions = 0_u64;
        for update in updates {
            let index = update.index();
            if Some(index) == self.local_player_index {
                return Err(Error::InvalidPlayerBatch);
            }
            let current = planned
                .get(&index)
                .copied()
                .or_else(|| self.player_position(index));
            let next = match (update, current) {
                (Update::Track { grid, region, .. }, None) => {
                    let size = i64::from(grid.region_size());
                    let valid = [region.x, region.y].into_iter().all(|n| {
                        n >= 0
                            && i64::from(n) < i64::from(grid.extent())
                            && i64::from(n) % size == 0
                    });
                    if !valid {
                        return Err(Error::InvalidPlayerBatch);
                    }
                    count += 1;
                    Position::Outside(*grid, *region)
                }
                (
                    Update::Shift {
                        regions,
                        plane_delta,
                        ..
                    },
                    Some(Position::Outside(grid, region)),
                ) if *plane_delta <= 3 => {
                    Position::Outside(grid, shift(grid, region, *regions, *plane_delta))
                }
                (Update::Enter { offset, .. }, Some(Position::Outside(grid, region))) => {
                    let size = i64::from(grid.region_size());
                    if [offset.x, offset.y]
                        .into_iter()
                        .any(|v| v < 0 || i64::from(v) >= size)
                    {
                        return Err(Error::InvalidPlayerBatch);
                    }
                    promotions += 1;
                    Position::Visible(
                        grid,
                        WorldTile::new(region.x + offset.x, region.y + offset.y, region.plane),
                    )
                }
                (Update::Leave { .. }, Some(Position::Visible(grid, tile))) => {
                    Position::Outside(grid, coarse(grid, tile))
                }
                (Update::Move { movement, .. }, Some(Position::Visible(grid, tile))) => {
                    Position::Visible(grid, move_tile(tile, *movement)?)
                }
                (
                    Update::Attributes {
                        appearance, facing, ..
                    },
                    Some(position @ Position::Visible(_, _)),
                ) => {
                    if facing.is_some_and(|v| v >= 2048)
                        || appearance
                            .as_ref()
                            .is_some_and(|a| a.display_name.len() > 320)
                    {
                        return Err(Error::InvalidPlayerBatch);
                    }
                    position
                }
                _ => return Err(Error::InvalidPlayerBatch),
            };
            if count > 4096 {
                return Err(Error::InvalidPlayerBatch);
            }
            planned.insert(index, next);
        }
        self.player_serial
            .checked_add(promotions)
            .ok_or(Error::InvalidPlayerBatch)?;
        Ok(())
    }
    pub(crate) fn apply_remote_players(&mut self, updates: &[Update]) -> bool {
        let mut changed = false;
        for update in updates {
            let index = update.index();
            match update {
                Update::Track { grid, region, .. } => {
                    self.outside_players.insert(
                        index,
                        TrackedPlayer {
                            grid: *grid,
                            region: *region,
                            appearance: None,
                            orientation: 0,
                        },
                    );
                    changed = true;
                }
                Update::Shift {
                    regions,
                    plane_delta,
                    ..
                } => {
                    if let Some(player) = self.outside_players.get_mut(&index) {
                        let next = shift(player.grid, player.region, *regions, *plane_delta);
                        changed |= next != player.region;
                        player.region = next;
                    }
                }
                Update::Enter { offset, .. } => {
                    if let Some(player) = self.outside_players.remove(&index) {
                        self.player_serial += 1;
                        self.players.insert(
                            index,
                            PlayerEntity {
                                index,
                                instance: PlayerInstanceId::new(self.player_serial),
                                tracking_grid: Some(player.grid),
                                tile: WorldTile::new(
                                    player.region.x + offset.x,
                                    player.region.y + offset.y,
                                    player.region.plane,
                                ),
                                orientation: player.orientation,
                                appearance: player.appearance,
                                sequence: ActorSequenceSlot::default(),
                                animation_cycle: 0,
                                locomotion: Locomotion::Idle,
                            },
                        );
                        changed = true;
                    }
                }
                Update::Leave { .. } => {
                    if let Some(player) = self.players.remove(&index)
                        && let Some(grid) = player.tracking_grid
                    {
                        self.outside_players.insert(
                            index,
                            TrackedPlayer {
                                grid,
                                region: coarse(grid, player.tile),
                                appearance: player.appearance,
                                orientation: player.orientation,
                            },
                        );
                        changed = true;
                    }
                }
                Update::Move { movement, .. } => {
                    if let Some(player) = self.players.get_mut(&index) {
                        changed |= apply_movement(player, *movement);
                    }
                }
                Update::Attributes {
                    appearance,
                    sequence,
                    delay,
                    facing,
                    ..
                } => {
                    if let Some(player) = self.players.get_mut(&index) {
                        if let Some(appearance) = appearance {
                            changed |= player.appearance.as_ref() != Some(appearance);
                            player.appearance = Some(appearance.clone());
                        }
                        if let Some(facing) = facing {
                            changed |= player.orientation != *facing;
                            player.orientation = *facing;
                        }
                        changed |= set_sequence(player, *sequence, *delay);
                    }
                }
            }
        }
        changed
    }
}
fn set_sequence(player: &mut PlayerEntity, sequence: SequenceChange, delay: u8) -> bool {
    let previous = player.sequence;
    if let SequenceChange::Set(sequence) = sequence {
        if sequence.is_none() {
            player.sequence.clear();
        } else {
            player.sequence.set_sequence(sequence, i32::from(delay));
        }
    }
    previous != player.sequence
}

fn apply_movement(player: &mut PlayerEntity, movement: Option<PlayerMovement>) -> bool {
    let locomotion = match movement {
        Some(PlayerMovement::Step { .. }) => Locomotion::Walk,
        Some(PlayerMovement::Run { .. }) => Locomotion::Run,
        _ => Locomotion::Idle,
    };
    let tile = move_tile(player.tile, movement).expect("validated remote movement");
    let previous = (player.tile, player.locomotion, player.orientation);
    if player.locomotion != locomotion {
        player.animation_cycle = 0;
    }
    player.tile = tile;
    player.locomotion = locomotion;
    if let Some(direction) = match movement {
        Some(PlayerMovement::Step { direction }) => Some(direction),
        Some(PlayerMovement::Run { direction2, .. }) => Some(direction2),
        _ => None,
    } {
        player.orientation = crate::entities::direction_to_orientation(direction);
    }
    previous != (player.tile, player.locomotion, player.orientation)
}

fn shift(
    grid: PlayerTrackingGrid,
    region: WorldTile,
    delta: TileOffset,
    plane_delta: u8,
) -> WorldTile {
    let size = i64::from(grid.region_size());
    let extent = i64::from(grid.extent());
    let axis = |origin: i32, delta: i32| {
        i32::try_from((i64::from(origin) + i64::from(delta) * size).rem_euclid(extent)).unwrap_or(0)
    };
    WorldTile::new(
        axis(region.x, delta.x),
        axis(region.y, delta.y),
        Plane::new((region.plane.get() + plane_delta) & 3).unwrap_or(Plane::MIN),
    )
}
fn coarse(grid: PlayerTrackingGrid, tile: WorldTile) -> WorldTile {
    let size = i32::try_from(grid.region_size()).unwrap_or(i32::MAX);
    let extent = i32::try_from(grid.extent()).unwrap_or(i32::MAX);
    WorldTile::new(
        tile.x.rem_euclid(extent) / size * size,
        tile.y.rem_euclid(extent) / size * size,
        tile.plane,
    )
}
fn move_tile(tile: WorldTile, movement: Option<PlayerMovement>) -> Result<WorldTile, Error> {
    let (x, y, plane) = match movement {
        None => (0, 0, 0),
        Some(PlayerMovement::Step { direction }) if direction < 8 => {
            let (x, y) = crate::entities::direction_to_delta(direction);
            (x, y, 0)
        }
        Some(PlayerMovement::Run {
            direction1,
            direction2,
        }) if direction1 < 8 && direction2 < 8 => {
            let a = crate::entities::direction_to_delta(direction1);
            let b = crate::entities::direction_to_delta(direction2);
            (a.0 + b.0, a.1 + b.1, 0)
        }
        Some(PlayerMovement::Teleport {
            delta_x,
            delta_y,
            plane_delta,
        }) if plane_delta <= 3 => (delta_x, delta_y, plane_delta),
        _ => return Err(Error::InvalidMovement),
    };
    Ok(WorldTile::new(
        tile.x.checked_add(x).ok_or(Error::PositionOverflow)?,
        tile.y.checked_add(y).ok_or(Error::PositionOverflow)?,
        Plane::new((tile.plane.get() + plane) & 3).unwrap_or(Plane::MIN),
    ))
}
