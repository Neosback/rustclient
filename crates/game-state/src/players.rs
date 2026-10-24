//! Out-of-view tracking and visible remote players share the entity state owner.
use crate::player_movement::{apply_movement, apply_traversal, exact_movement, move_tile};
use crate::{EntityUpdateError as Error, Locomotion, PlayerEntity, WorldEntitiesState};
use rustclient_model::{
    ActorSequenceSlot, Plane, PlayerAppearance, PlayerIndex, PlayerInstanceId, PlayerTrackingGrid,
    RemotePlayerUpdate as Update, SequenceId, SequencePolicy, TileOffset, WorldTile,
};
use std::collections::BTreeMap;

#[derive(Debug, Clone, PartialEq, Eq)]
pub struct TrackedPlayer {
    pub grid: PlayerTrackingGrid,
    pub region: WorldTile,
    pub appearance: Option<PlayerAppearance>,
    pub orientation: u16,
    pub path_speed: rustclient_model::MoveSpeed,
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
    pub(crate) fn validate_remote_players(
        &self,
        updates: &[Update],
        cycle: rustclient_model::ClientCycle,
    ) -> Result<(), Error> {
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
                        appearance,
                        facing,
                        exact_move,
                        ..
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
                    if let (Some(exact), Position::Visible(_, tile)) = (exact_move, position) {
                        exact_movement(tile, cycle, *exact)?;
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
    #[expect(
        clippy::too_many_lines,
        reason = "ordered validated visibility and attribute transitions"
    )]
    pub(crate) fn apply_remote_players(
        &mut self,
        updates: &[Update],
        cycle: rustclient_model::ClientCycle,
        sequence_policies: &[(SequenceId, SequencePolicy)],
    ) -> bool {
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
                            path_speed: rustclient_model::MoveSpeed::Walk,
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
                                path_speed: player.path_speed,
                                movement_speed: player.path_speed,
                                path_queue: crate::ActorPathQueue::default(),
                                exact_move: None,
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
                                path_speed: player.path_speed,
                            },
                        );
                        changed = true;
                    }
                }
                Update::Move {
                    movement, speed, ..
                } => {
                    if let Some(player) = self.players.get_mut(&index) {
                        changed |= apply_movement(player, *movement, *speed);
                    }
                }
                Update::Attributes {
                    appearance,
                    sequence,
                    delay,
                    facing,
                    path_speed,
                    exact_move,
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
                        changed |= apply_traversal(player, *path_speed);
                        if let Some(exact) = exact_move {
                            let next = exact_movement(player.tile, cycle, *exact)
                                .expect("validated exact movement");
                            changed |= !player.path_queue.is_empty()
                                || player.sequence.path_backlog_at_assignment != 0
                                || player.exact_move != Some(next);
                            player.path_queue.clear();
                            player.sequence.path_backlog_at_assignment = 0;
                            player.exact_move = Some(next);
                        }
                        changed |= crate::sequence_scheduler::apply_change(
                            &mut player.sequence,
                            *sequence,
                            i32::from(*delay),
                            player.path_queue.backlog(),
                            sequence_policies,
                        );
                    }
                }
            }
        }
        changed
    }
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
