//! Sub-tile actor motion.
//!
//! `GameState` keeps each actor on its server tile, which only changes once per game tick. The
//! reference client walks the drawn position toward each new tile a few units per 20 ms cycle,
//! so motion reads as continuous. This module is that walk as a disposable projection: it is
//! derived from entity tiles, never feeds back into `GameState`, and can be rebuilt at any time.
//!
//! Ported from the reference actor movement step (`Actor.method3284`) and turn step
//! (`classKN.method6539`): the drawn rotation turns toward the network heading a fixed amount per
//! cycle, walking slows while it does, and the movement pose (stand, turn in place, walk, strafe,
//! back-pedal, run, crawl) follows from the angle between heading and rotation and the step speed.
//! Omitted: the catch-up speed-up after an animation blocks movement, because movement-blocking
//! animations are not modelled yet.
//! Forced trajectories currently advance every cycle; legacy action-frame gating remains open.
use rustclient_game_state::{ExactMovement, WorldEntitiesState};
use rustclient_model::{ClientCycle, MoveSpeed, NpcIndex, NpcInstanceId, PlayerIndex, WorldTile};
use std::collections::{BTreeMap, VecDeque};

/// Local units per tile.
const TILE: i32 = 128;
/// The reference keeps at most nine queued steps, dropping the oldest.
const MAX_QUEUE: usize = 9;
/// A step whose target is farther than this is a teleport and snaps (`Actor.method3284`).
const SNAP_UNITS: i32 = 288;
/// One full turn in the reference's 2048-unit angle.
const FULL_TURN: i32 = 2048;
/// Rotation applied per cycle; the reference actor default (an NPC definition may override it).
const TURN_RATE: i32 = 32;
/// A turn in place plays its animation once the actor has turned this many cycles.
const TURN_ANIMATION_DELAY: u32 = 25;

/// Direction of travel relative to the way the actor is facing.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum Facing {
    Forward,
    Back,
    Left,
    Right,
}

/// How fast the actor is covering ground this cycle.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum Pace {
    Crawl,
    Walk,
    Run,
}

/// The movement pose the reference client selects for an actor.
#[derive(Debug, Clone, Copy, PartialEq, Eq, Default)]
pub enum Pose {
    #[default]
    Idle,
    TurnLeft,
    TurnRight,
    Move {
        facing: Facing,
        pace: Pace,
    },
}

/// Drawn state of one actor: where it faces and which movement pose has played for how long.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct ActorPose {
    pub pose: Pose,
    /// Client cycles since the pose last changed.
    pub cycle: u32,
    /// Drawn rotation in 2048-unit turns.
    pub rotation: u16,
}

#[derive(Debug, Clone, Copy, PartialEq, Eq)]
struct Step {
    tile: [i32; 2],
    speed: MoveSpeed,
}

#[derive(Debug, Clone, PartialEq, Eq)]
struct Actor {
    tile: WorldTile,
    /// South-west corner of the actor's footprint in world units.
    position: [i32; 2],
    /// Oldest step first.
    queue: VecDeque<Step>,
    exact: Option<ExactMovement>,
    cycle: u64,
    /// Network heading the rotation turns toward.
    heading: i32,
    /// Drawn rotation.
    rotation: i32,
    /// Consecutive cycles spent turning.
    turning: u32,
    pose: Pose,
    pose_cycle: u32,
    committed_delta: Option<[i32; 2]>,
    committed_rotation: Option<u16>,
}

impl Actor {
    fn at(tile: WorldTile) -> Self {
        Self::facing(tile, 0)
    }

    fn facing(tile: WorldTile, orientation: u16) -> Self {
        let heading = i32::from(orientation) & (FULL_TURN - 1);
        Self {
            tile,
            position: [tile.x * TILE, tile.y * TILE],
            queue: VecDeque::new(),
            exact: None,
            cycle: 0,
            heading,
            rotation: heading,
            turning: 0,
            pose: Pose::Idle,
            pose_cycle: 0,
            committed_delta: None,
            committed_rotation: None,
        }
    }

    fn snap(&mut self, tile: WorldTile) {
        let cycle = self.cycle;
        let (heading, rotation, turning, pose, pose_cycle) = (
            self.heading,
            self.rotation,
            self.turning,
            self.pose,
            self.pose_cycle,
        );
        *self = Self::at(tile);
        self.cycle = cycle;
        self.heading = heading;
        self.rotation = rotation;
        self.turning = turning;
        self.pose = pose;
        self.pose_cycle = pose_cycle;
    }

    /// A new network heading; the drawn rotation turns toward it over the next cycles.
    fn face(&mut self, orientation: u16) {
        self.heading = i32::from(orientation) & (FULL_TURN - 1);
    }

    fn drawn(&self) -> ActorPose {
        ActorPose {
            pose: self.pose,
            cycle: self.pose_cycle,
            rotation: u16::try_from(self.rotation & (FULL_TURN - 1)).unwrap_or(0),
        }
    }

    fn set_pose(&mut self, pose: Pose) {
        if self.pose != pose {
            self.pose = pose;
            self.pose_cycle = 0;
        }
    }

    fn observe(&mut self, tile: WorldTile, speed: MoveSpeed) {
        self.exact = None;
        if speed == MoveSpeed::Stationary {
            self.snap(tile);
            return;
        }
        if tile == self.tile {
            return;
        }
        let delta = [tile.x - self.tile.x, tile.y - self.tile.y];
        if tile.plane != self.tile.plane || delta.iter().any(|d| d.abs() > 2) {
            self.snap(tile);
            return;
        }
        // A two-tile run passes through the tile one step along the same heading.
        if delta.iter().any(|d| d.abs() == 2) {
            let mid = [
                self.tile.x + delta[0].signum(),
                self.tile.y + delta[1].signum(),
            ];
            self.push(Step { tile: mid, speed });
        }
        self.push(Step {
            tile: [tile.x, tile.y],
            speed,
        });
        self.tile = tile;
    }

    fn force(&mut self, tile: WorldTile, exact: ExactMovement) {
        self.tile = tile;
        self.queue.clear();
        self.exact = Some(exact);
        if self.cycle >= exact.starts_at.get() {
            self.force_position(exact);
        }
    }
    fn force_position(&mut self, exact: ExactMovement) {
        let start = [exact.start.x * TILE, exact.start.y * TILE];
        let end = [exact.end.x * TILE, exact.end.y * TILE];
        if self.cycle <= exact.starts_at.get() {
            let remaining =
                i64::try_from((exact.starts_at.get() - self.cycle).max(1)).unwrap_or(i64::MAX);
            for (position, start) in self.position.iter_mut().zip(start) {
                *position += i32::try_from((i64::from(start) - i64::from(*position)) / remaining)
                    .expect("bounded approach delta");
            }
        } else if exact.starts_at == exact.ends_at {
            // Actor.method3284's inclusive approach phase wins a zero-duration interval.
            self.position = start;
        } else if self.cycle >= exact.ends_at.get() {
            self.position = end;
        } else {
            let duration = exact.ends_at.get() - exact.starts_at.get();
            let elapsed = self.cycle - exact.starts_at.get();
            // Delays are bounded by the entity owner before this projection is built.
            let elapsed = i64::try_from(elapsed).expect("bounded exact duration");
            let duration = i64::try_from(duration).expect("bounded exact duration");
            for axis in 0..2 {
                self.position[axis] = i32::try_from(
                    (i64::from(start[axis]) * (duration - elapsed)
                        + i64::from(end[axis]) * elapsed)
                        / duration,
                )
                .expect("convex coordinate interpolation");
            }
        }
    }
    fn push(&mut self, step: Step) {
        if self.queue.len() >= MAX_QUEUE {
            self.queue.pop_front();
        }
        self.queue.push_back(step);
    }

    /// One 20 ms client cycle of the reference movement and turn steps.
    fn advance(&mut self) {
        self.cycle = self.cycle.saturating_add(1);
        self.pose_cycle = self.pose_cycle.saturating_add(1);
        if let Some(delta) = self.committed_delta.take() {
            let speed = delta[0].abs().max(delta[1].abs());
            let moving = if speed == 0 {
                Pose::Idle
            } else {
                self.movement_pose(speed)
            };
            let pose = if let Some(rotation) = self.committed_rotation.take() {
                self.rotation = i32::from(rotation);
                self.turning = 0;
                moving
            } else {
                self.turn(moving)
            };
            self.set_pose(pose);
            return;
        }
        if let Some(exact) = self.exact {
            self.force_position(exact);
            if self.cycle <= exact.ends_at.get() {
                // Forced movement faces its authored direction immediately.
                self.heading = i32::from(exact.direction) & (FULL_TURN - 1);
                self.rotation = self.heading;
                self.turning = 0;
                self.set_pose(Pose::Idle);
            } else {
                // The finished trajectory holds its end; the actor turns back to its heading.
                let pose = self.turn(Pose::Idle);
                self.set_pose(pose);
            }
            return;
        }
        let moving = self.step();
        let pose = self.turn(moving);
        self.set_pose(pose);
    }

    /// `Actor.method3284`: walk toward the oldest queued tile; returns the movement pose.
    fn step(&mut self) -> Pose {
        let Some(&Step {
            tile,
            speed: traversal,
        }) = self.queue.front()
        else {
            return Pose::Idle;
        };
        let target = [tile[0] * TILE, tile[1] * TILE];
        if (target[0] - self.position[0])
            .abs()
            .max((target[1] - self.position[1]).abs())
            > SNAP_UNITS
        {
            self.position = target;
            self.queue.pop_front();
            return Pose::Idle;
        }
        let backlog = self.queue.len();
        let mut speed = 4;
        if self.heading != self.rotation {
            speed = 2;
        }
        if backlog > 2 {
            speed = 6;
        }
        if backlog > 3 {
            speed = 8;
        }
        match traversal {
            MoveSpeed::Run => speed <<= 1,
            MoveSpeed::Crawl => speed >>= 1,
            MoveSpeed::Walk | MoveSpeed::Stationary => {}
        }
        let speed = speed.max(1);
        for (position, target) in self.position.iter_mut().zip(target) {
            *position += (target - *position).clamp(-speed, speed);
        }
        if self.position == target {
            self.queue.pop_front();
        }
        self.movement_pose(speed)
    }

    fn movement_pose(&self, speed: i32) -> Pose {
        let relative = (self.heading - self.rotation) & (FULL_TURN - 1);
        let relative = if relative > FULL_TURN / 2 {
            relative - FULL_TURN
        } else {
            relative
        };
        let facing = match relative {
            -256..=256 => Facing::Forward,
            257..768 => Facing::Right,
            -768..=-257 => Facing::Left,
            _ => Facing::Back,
        };
        let pace = if speed >= 8 {
            Pace::Run
        } else if speed <= 2 {
            Pace::Crawl
        } else {
            Pace::Walk
        };
        Pose::Move { facing, pace }
    }
    fn committed_position(
        &mut self,
        position: [i32; 2],
        previous: Option<[i32; 2]>,
        rotation: Option<u16>,
    ) {
        let delta = std::array::from_fn(|axis| {
            position[axis].saturating_sub(previous.unwrap_or(position)[axis])
        });
        self.position = position;
        self.queue.clear();
        self.exact = None;
        self.committed_delta = Some(delta);
        self.committed_rotation = rotation;
    }

    /// `classKN.method6539`: turn toward the heading, replacing a standing pose with the turn
    /// pose; returns the pose to draw this cycle.
    fn turn(&mut self, moving: Pose) -> Pose {
        let difference = (self.heading - self.rotation) & (FULL_TURN - 1);
        if difference == 0 {
            self.turning = 0;
            return moving;
        }
        self.turning += 1;
        let direction = if difference > FULL_TURN / 2 { -1 } else { 1 };
        self.rotation += TURN_RATE * direction;
        let partial = (TURN_RATE..=FULL_TURN - TURN_RATE).contains(&difference);
        if !partial {
            self.rotation = self.heading;
        }
        self.rotation &= FULL_TURN - 1;
        if moving == Pose::Idle && (self.turning > TURN_ANIMATION_DELAY || partial) {
            return if direction < 0 {
                Pose::TurnLeft
            } else {
                Pose::TurnRight
            };
        }
        moving
    }
}

/// Drawn positions for every live player and NPC.
#[derive(Debug, Default)]
pub struct ActorMotion {
    players: BTreeMap<PlayerIndex, (rustclient_model::PlayerInstanceId, Actor)>,
    npcs: BTreeMap<NpcIndex, (NpcInstanceId, u64, Actor)>,
}

impl ActorMotion {
    /// Reconcile with the authoritative entity tiles, queueing a step for each actor that moved.
    pub fn observe(&mut self, entities: &WorldEntitiesState, cycle: ClientCycle) {
        self.players
            .retain(|index, _| entities.players().contains_key(index));
        self.npcs
            .retain(|index, _| entities.npcs().contains_key(index));
        for (index, player) in entities.players() {
            let entry = self.players.entry(*index).or_insert_with(|| {
                (
                    player.instance,
                    Actor::facing(player.tile, player.orientation),
                )
            });
            if entry.0 != player.instance {
                *entry = (
                    player.instance,
                    Actor::facing(player.tile, player.orientation),
                );
            }
            entry.1.cycle = cycle.get();
            entry.1.face(player.orientation);
            if let Some(exact) = player.exact_move {
                entry.1.force(player.tile, exact);
            } else {
                entry.1.observe(player.tile, player.movement_speed);
            }
        }
        for (index, npc) in entities.npcs() {
            let serial = npc.path.map_or(0, |p| p.serial);
            let entry = self.npcs.entry(*index).or_insert_with(|| {
                (
                    npc.instance,
                    serial,
                    Actor::facing(npc.tile, npc.orientation),
                )
            });
            if entry.0 != npc.instance {
                *entry = (
                    npc.instance,
                    serial,
                    Actor::facing(npc.tile, npc.orientation),
                );
            }
            entry.2.cycle = cycle.get();
            entry.2.face(npc.orientation);
            if let Some(exact) = npc.exact_move {
                entry.2.force(npc.tile, exact);
            } else if entry.1 != serial
                && let Some(path) = npc.path
            {
                entry.2.observe(path.first, path.speed);
                if let Some(second) = path.second {
                    entry.2.observe(second, path.speed);
                }
            }
            entry.1 = serial;
        }
    }

    pub(crate) fn committed_player(
        &mut self,
        index: PlayerIndex,
        position: [i32; 2],
        previous: Option<[i32; 2]>,
        rotation: Option<u16>,
    ) {
        if let Some((_, actor)) = self.players.get_mut(&index) {
            actor.committed_position(position, previous, rotation);
        }
    }
    pub(crate) fn committed_npc(
        &mut self,
        index: NpcIndex,
        position: [i32; 2],
        previous: Option<[i32; 2]>,
        rotation: Option<u16>,
    ) {
        if let Some((_, _, actor)) = self.npcs.get_mut(&index) {
            actor.committed_position(position, previous, rotation);
        }
    }
    /// Advance every actor by `cycles` client cycles.
    pub fn advance(&mut self, cycles: u32) {
        for _ in 0..cycles {
            for actor in self
                .players
                .values_mut()
                .map(|(_, actor)| actor)
                .chain(self.npcs.values_mut().map(|(_, _, actor)| actor))
            {
                actor.advance();
            }
        }
    }

    /// Footprint corner of a player in world units, or `None` before it is observed.
    #[must_use]
    pub fn player(&self, index: PlayerIndex) -> Option<[i32; 2]> {
        self.players.get(&index).map(|(_, actor)| actor.position)
    }

    /// Drawn rotation and movement pose of a player, or `None` before it is observed.
    #[must_use]
    pub fn player_pose(&self, index: PlayerIndex) -> Option<ActorPose> {
        self.players.get(&index).map(|(_, actor)| actor.drawn())
    }

    /// Drawn rotation and movement pose of an NPC, or `None` before it is observed.
    #[must_use]
    pub fn npc_pose(&self, index: NpcIndex) -> Option<ActorPose> {
        self.npcs.get(&index).map(|(_, _, actor)| actor.drawn())
    }

    /// Footprint corner of an NPC in world units, or `None` before it is observed.
    #[must_use]
    pub fn npc(&self, index: NpcIndex) -> Option<[i32; 2]> {
        self.npcs.get(&index).map(|(_, _, actor)| actor.position)
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use rustclient_model::Plane;

    fn tile(x: i32, y: i32) -> WorldTile {
        WorldTile::new(x, y, Plane::MIN)
    }
    fn run_cycles(actor: &mut Actor, cycles: u32) {
        for _ in 0..cycles {
            actor.advance();
        }
    }

    #[test]
    fn a_walked_tile_is_crossed_gradually_and_arrives_within_one_tick() {
        let mut actor = Actor::at(tile(10, 10));
        actor.observe(tile(11, 10), MoveSpeed::Walk);
        assert_eq!(
            actor.position,
            [1280, 1280],
            "no movement before a cycle runs"
        );
        actor.advance();
        assert_eq!(actor.position, [1284, 1280]);
        // 128 units at 4 per cycle: 32 cycles, just over one 30-cycle game tick.
        run_cycles(&mut actor, 31);
        assert_eq!(actor.position, [1408, 1280]);
        assert!(actor.queue.is_empty());
    }

    #[test]
    fn running_covers_two_tiles_through_the_intermediate_tile() {
        let mut actor = Actor::at(tile(0, 0));
        actor.observe(tile(2, 0), MoveSpeed::Run);
        assert_eq!(actor.queue.len(), 2);
        // Two queued steps keep the base speed of 4, doubled for running.
        actor.advance();
        assert_eq!(actor.position, [8, 0]);
        run_cycles(&mut actor, 15);
        assert_eq!(actor.position, [128, 0], "first tile after 16 cycles");
        run_cycles(&mut actor, 16);
        assert_eq!(actor.position, [256, 0]);
    }

    #[test]
    fn a_longer_backlog_speeds_the_walk_up() {
        let mut actor = Actor::at(tile(0, 0));
        for x in 1..=4 {
            actor.observe(tile(x, 0), MoveSpeed::Walk);
        }
        assert_eq!(actor.queue.len(), 4);
        actor.advance();
        assert_eq!(
            actor.position,
            [8, 0],
            "more than three queued steps move 8 per cycle"
        );
    }

    #[test]
    fn diagonal_steps_move_both_axes_together() {
        let mut actor = Actor::at(tile(5, 5));
        actor.observe(tile(6, 4), MoveSpeed::Walk);
        run_cycles(&mut actor, 10);
        assert_eq!(actor.position, [5 * 128 + 40, 5 * 128 - 40]);
    }

    #[test]
    fn teleports_and_plane_changes_snap_and_drop_the_queue() {
        let mut actor = Actor::at(tile(0, 0));
        actor.observe(tile(1, 0), MoveSpeed::Walk);
        actor.observe(tile(40, 40), MoveSpeed::Walk);
        assert_eq!(actor.position, [40 * 128, 40 * 128]);
        assert!(actor.queue.is_empty());
        let upstairs = WorldTile::new(40, 40, Plane::new(1).unwrap());
        actor.observe(upstairs, MoveSpeed::Walk);
        assert_eq!(actor.tile, upstairs);
        assert!(actor.queue.is_empty());
    }

    #[test]
    fn the_queue_never_exceeds_the_reference_depth() {
        let mut actor = Actor::at(tile(0, 0));
        for x in 1..=20 {
            actor.observe(tile(x % 2, 0), MoveSpeed::Walk);
        }
        assert!(actor.queue.len() <= MAX_QUEUE);
    }
    #[test]
    fn traversal_override_and_exact_motion_follow_the_authored_cycle_interval() {
        let mut actor = Actor::at(tile(10, 10));
        actor.observe(tile(11, 10), MoveSpeed::Crawl);
        run_cycles(&mut actor, 4);
        assert_eq!(actor.position, [1288, 1280]);
        actor.observe(tile(10, 10), MoveSpeed::Stationary);
        assert_eq!(actor.position, [1280, 1280]);
        let exact = ExactMovement {
            start: tile(12, 10),
            end: tile(14, 10),
            starts_at: ClientCycle::new(9),
            ends_at: ClientCycle::new(13),
            direction: 1024,
        };
        actor.force(tile(10, 10), exact);
        run_cycles(&mut actor, 4);
        assert_eq!(actor.position, [1536, 1280]);
        run_cycles(&mut actor, 3);
        assert_eq!(actor.position, [1664, 1280]);
        run_cycles(&mut actor, 2);
        assert_eq!(actor.position, [1792, 1280]);
        assert_eq!(
            actor.tile,
            tile(10, 10),
            "a draw projection cannot advance the network coordinate"
        );
        let mut zero_duration = Actor::at(tile(10, 10));
        zero_duration.force(
            tile(10, 10),
            ExactMovement {
                starts_at: ClientCycle::new(1),
                ends_at: ClientCycle::new(1),
                ..exact
            },
        );
        run_cycles(&mut zero_duration, 3);
        assert_eq!(zero_duration.position, [1536, 1280]);
    }
}

#[cfg(test)]
mod pose_tests {
    use super::*;
    use rustclient_model::Plane;

    fn tile(x: i32, y: i32) -> WorldTile {
        WorldTile::new(x, y, Plane::MIN)
    }
    fn run_cycles(actor: &mut Actor, cycles: u32) {
        for _ in 0..cycles {
            actor.advance();
        }
    }

    #[test]
    fn rotation_turns_a_fixed_amount_per_cycle_and_the_walk_is_slow_until_it_arrives() {
        let mut actor = Actor::facing(tile(0, 0), 0);
        actor.observe(tile(1, 0), MoveSpeed::Walk);
        actor.face(512);
        actor.advance();
        assert_eq!(actor.drawn().rotation, 32);
        assert_eq!(actor.position, [2, 0], "half speed while turning");
        run_cycles(&mut actor, 15);
        assert_eq!(actor.drawn().rotation, 512);
        actor.advance();
        assert_eq!(
            actor.position,
            [2 * 16 + 4, 0],
            "full speed once facing the heading"
        );
    }

    #[test]
    fn turning_takes_the_short_way_round() {
        let mut actor = Actor::facing(tile(0, 0), 100);
        actor.face(2000);
        actor.advance();
        assert_eq!(
            actor.drawn().rotation,
            68,
            "100 - 32 wraps the short way to 2000"
        );
        run_cycles(&mut actor, 2);
        assert_eq!(actor.drawn().rotation, 4);
        run_cycles(&mut actor, 1);
        assert_eq!(actor.drawn().rotation, 2020, "wrapped below zero");
    }

    #[test]
    fn standing_turns_play_the_turn_pose_in_their_direction() {
        let mut actor = Actor::facing(tile(0, 0), 0);
        actor.face(512);
        actor.advance();
        assert_eq!(actor.drawn().pose, Pose::TurnRight);
        let mut actor = Actor::facing(tile(0, 0), 0);
        actor.face(1900);
        actor.advance();
        assert_eq!(actor.drawn().pose, Pose::TurnLeft);
        let mut settled = Actor::facing(tile(0, 0), 0);
        settled.advance();
        assert_eq!(settled.drawn().pose, Pose::Idle);
    }

    #[test]
    fn a_turn_pose_keeps_its_frame_counter_while_the_turn_lasts() {
        let mut actor = Actor::facing(tile(0, 0), 0);
        actor.face(1024);
        run_cycles(&mut actor, 5);
        assert_eq!(actor.drawn().pose, Pose::TurnRight);
        assert_eq!(
            actor.drawn().cycle,
            4,
            "restarted once, when the turn began"
        );
    }

    #[test]
    fn walking_picks_the_variant_from_the_angle_between_heading_and_rotation() {
        let at = |heading: u16| {
            let mut actor = Actor::facing(tile(0, 0), 0);
            actor.observe(tile(1, 0), MoveSpeed::Walk);
            actor.face(heading);
            actor.advance();
            actor.drawn().pose
        };
        let walk = |facing| Pose::Move {
            facing,
            pace: Pace::Crawl,
        };
        // The first cycle after a heading change is slow, so it reads as a crawl pace.
        assert_eq!(
            at(0),
            Pose::Move {
                facing: Facing::Forward,
                pace: Pace::Walk
            }
        );
        assert_eq!(at(512), walk(Facing::Right));
        assert_eq!(at(1536), walk(Facing::Left));
        assert_eq!(at(1024), walk(Facing::Back));
    }

    #[test]
    fn a_backlog_or_running_selects_the_run_pace() {
        let mut actor = Actor::facing(tile(0, 0), 0);
        actor.observe(tile(2, 0), MoveSpeed::Run);
        actor.advance();
        assert_eq!(
            actor.drawn().pose,
            Pose::Move {
                facing: Facing::Forward,
                pace: Pace::Run
            }
        );
        let mut catching_up = Actor::facing(tile(0, 0), 0);
        for x in 1..=4 {
            catching_up.observe(tile(x, 0), MoveSpeed::Walk);
        }
        catching_up.advance();
        assert_eq!(
            catching_up.drawn().pose,
            Pose::Move {
                facing: Facing::Forward,
                pace: Pace::Run
            },
            "four queued steps move at the running speed of 8"
        );
    }

    #[test]
    fn stopping_returns_to_idle() {
        let mut actor = Actor::facing(tile(0, 0), 0);
        actor.observe(tile(1, 0), MoveSpeed::Walk);
        run_cycles(&mut actor, 40);
        assert_eq!(actor.drawn().pose, Pose::Idle);
    }
}
