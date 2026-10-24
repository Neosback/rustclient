//! Player movement normalization belongs to the entity owner, not a decoder cache.
use crate::{EntityUpdateError as Error, Locomotion, PlayerEntity};
use rustclient_model::{ClientCycle, ExactMove, MoveSpeed, Plane, PlayerMovement, WorldTile};
/// A server-authored trajectory anchored to the packet's new network coordinate.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct ExactMovement {
    pub start: WorldTile,
    pub end: WorldTile,
    pub starts_at: ClientCycle,
    pub ends_at: ClientCycle,
    pub direction: u16,
}
pub(crate) fn exact_movement(
    origin: WorldTile,
    cycle: ClientCycle,
    request: ExactMove,
) -> Result<ExactMovement, Error> {
    if request.start_delay > request.end_delay || request.direction >= 2048 {
        return Err(Error::InvalidMovement);
    }
    let offset = |x: i8, y: i8| {
        Ok(WorldTile::new(
            origin
                .x
                .checked_add(i32::from(x))
                .ok_or(Error::PositionOverflow)?,
            origin
                .y
                .checked_add(i32::from(y))
                .ok_or(Error::PositionOverflow)?,
            origin.plane,
        ))
    };
    Ok(ExactMovement {
        start: offset(request.start_delta_x, request.start_delta_y)?,
        end: offset(request.end_delta_x, request.end_delta_y)?,
        starts_at: cycle
            .checked_advance(u32::from(request.start_delay))
            .ok_or(Error::PositionOverflow)?,
        ends_at: cycle
            .checked_advance(u32::from(request.end_delay))
            .ok_or(Error::PositionOverflow)?,
        direction: request.direction,
    })
}
pub(crate) fn move_tile(
    tile: WorldTile,
    movement: Option<PlayerMovement>,
) -> Result<WorldTile, Error> {
    let (x, y, plane, wrap) = match movement {
        None => (0, 0, 0, None),
        Some(PlayerMovement::Step { direction }) if direction < 8 => {
            let (x, y) = crate::direction_to_delta(direction);
            (x, y, 0, None)
        }
        Some(PlayerMovement::Run {
            direction1,
            direction2,
        }) if direction1 < 8 && direction2 < 8 => {
            let a = crate::direction_to_delta(direction1);
            let b = crate::direction_to_delta(direction2);
            (a.0 + b.0, a.1 + b.1, 0, None)
        }
        Some(PlayerMovement::Teleport {
            delta_x,
            delta_y,
            plane_delta,
        }) if plane_delta <= 3 => (delta_x, delta_y, plane_delta, None),
        Some(PlayerMovement::WrappedTeleport {
            offset,
            plane_delta,
            extent,
        }) if plane_delta <= 3 && extent > 0 && i32::try_from(extent).is_ok() => {
            (offset.x, offset.y, plane_delta, Some(extent))
        }
        _ => return Err(Error::InvalidMovement),
    };
    let axis = |origin: i32, delta: i32| {
        if let Some(extent) = wrap {
            i32::try_from((i64::from(origin) + i64::from(delta)).rem_euclid(i64::from(extent)))
                .map_err(|_| Error::PositionOverflow)
        } else {
            origin.checked_add(delta).ok_or(Error::PositionOverflow)
        }
    };
    Ok(WorldTile::new(
        axis(tile.x, x)?,
        axis(tile.y, y)?,
        Plane::new((tile.plane.get() + plane) & 3).unwrap_or(Plane::MIN),
    ))
}
pub(crate) fn apply_movement(
    player: &mut PlayerEntity,
    movement: Option<PlayerMovement>,
    speed: Option<MoveSpeed>,
) -> bool {
    let previous = (
        player.tile,
        player.locomotion,
        player.orientation,
        player.movement_speed,
        player.path_queue,
        player.exact_move,
        player.sequence.path_backlog_at_assignment,
    );
    let origin = player.tile;
    player.movement_speed = speed.unwrap_or(player.path_speed);
    let locomotion = match movement {
        Some(PlayerMovement::Step { .. } | PlayerMovement::Run { .. }) => {
            locomotion(player.movement_speed)
        }
        _ => Locomotion::Idle,
    };
    if player.locomotion != locomotion {
        player.animation_cycle = 0;
    }
    player.locomotion = locomotion;
    player.tile = move_tile(player.tile, movement).expect("validated player movement");
    if movement.is_some() {
        player.exact_move = None;
        if player.movement_speed == MoveSpeed::Stationary {
            player.path_queue.reset_to(player.tile);
            player.sequence.path_backlog_at_assignment = 0;
        } else {
            // The confirmed endpoint changes at packet time, but the authoritative fine cursor
            // must begin from the actor's pre-packet position.
            player.path_queue.ensure_position(origin);
            player
                .path_queue
                .enqueue(player.tile, player.movement_speed);
        }
    }
    let direction = match movement {
        Some(PlayerMovement::Step { direction }) => Some(direction),
        Some(PlayerMovement::Run { direction2, .. }) => Some(direction2),
        _ => None,
    };
    if let Some(direction) = direction
        && player.movement_speed != MoveSpeed::Stationary
    {
        player.orientation = crate::direction_to_orientation(direction);
    }
    previous
        != (
            player.tile,
            player.locomotion,
            player.orientation,
            player.movement_speed,
            player.path_queue,
            player.exact_move,
            player.sequence.path_backlog_at_assignment,
        )
}
pub(crate) fn apply_traversal(player: &mut PlayerEntity, persistent: Option<MoveSpeed>) -> bool {
    let previous = player.path_speed;
    if let Some(speed) = persistent {
        player.path_speed = speed;
    }
    previous != player.path_speed
}
fn locomotion(speed: MoveSpeed) -> Locomotion {
    match speed {
        MoveSpeed::Run => Locomotion::Run,
        MoveSpeed::Walk | MoveSpeed::Crawl => Locomotion::Walk,
        MoveSpeed::Stationary => Locomotion::Idle,
    }
}
