//! Sub-tile actor motion.
//!
//! `GameState` keeps each actor on its server tile, which only changes once per game tick. The
//! reference client walks the drawn position toward each new tile a few units per 20 ms cycle,
//! so motion reads as continuous. This module is that walk as a disposable projection: it is
//! derived from entity tiles, never feeds back into `GameState`, and can be rebuilt at any time.
//!
//! Ported from the reference actor movement step. Omitted: the slow-down while an actor turns
//! toward its heading and the catch-up speed-up after an animation blocks movement, because
//! actor rotation and movement-blocking animations are not modelled yet.
use rustclient_game_state::{Locomotion, WorldEntitiesState};
use rustclient_model::{NpcIndex, NpcInstanceId, PlayerIndex, WorldTile};
use std::collections::{BTreeMap, VecDeque};

/// Local units per tile.
const TILE: i32 = 128;
/// The reference keeps at most nine queued steps, dropping the oldest.
const MAX_QUEUE: usize = 9;
/// A step whose target is farther than two tiles away is a teleport and snaps.
const SNAP_UNITS: i32 = 2 * TILE;

#[derive(Debug, Clone, Copy, PartialEq, Eq)]
struct Step {
    tile: [i32; 2],
    run: bool,
}

#[derive(Debug, Clone, PartialEq, Eq)]
struct Actor {
    tile: WorldTile,
    /// South-west corner of the actor's footprint in world units.
    position: [i32; 2],
    /// Oldest step first.
    queue: VecDeque<Step>,
}

impl Actor {
    fn at(tile: WorldTile) -> Self {
        Self {
            tile,
            position: [tile.x * TILE, tile.y * TILE],
            queue: VecDeque::new(),
        }
    }

    fn snap(&mut self, tile: WorldTile) {
        *self = Self::at(tile);
    }

    fn observe(&mut self, tile: WorldTile, run: bool) {
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
            self.push(Step { tile: mid, run });
        }
        self.push(Step {
            tile: [tile.x, tile.y],
            run,
        });
        self.tile = tile;
    }

    fn push(&mut self, step: Step) {
        if self.queue.len() >= MAX_QUEUE {
            self.queue.pop_front();
        }
        self.queue.push_back(step);
    }

    /// One 20 ms client cycle of the reference movement step.
    fn advance(&mut self) {
        let Some(&Step { tile, run }) = self.queue.front() else {
            return;
        };
        let target = [tile[0] * TILE, tile[1] * TILE];
        if (target[0] - self.position[0]).abs() > SNAP_UNITS
            || (target[1] - self.position[1]).abs() > SNAP_UNITS
        {
            self.position = target;
            self.queue.pop_front();
            return;
        }
        let mut speed = match self.queue.len() {
            0..=2 => 4,
            3 => 6,
            _ => 8,
        };
        if run {
            speed <<= 1;
        }
        for (position, target) in self.position.iter_mut().zip(target) {
            *position += (target - *position).clamp(-speed, speed);
        }
        if self.position == target {
            self.queue.pop_front();
        }
    }
}

/// Drawn positions for every live player and NPC.
#[derive(Debug, Default)]
pub struct ActorMotion {
    players: BTreeMap<PlayerIndex, (rustclient_model::PlayerInstanceId, Actor)>,
    npcs: BTreeMap<NpcIndex, (NpcInstanceId, Actor)>,
}

impl ActorMotion {
    /// Reconcile with the authoritative entity tiles, queueing a step for each actor that moved.
    pub fn observe(&mut self, entities: &WorldEntitiesState) {
        self.players
            .retain(|index, _| entities.players().contains_key(index));
        self.npcs
            .retain(|index, _| entities.npcs().contains_key(index));
        for (index, player) in entities.players() {
            let entry = self
                .players
                .entry(*index)
                .or_insert_with(|| (player.instance, Actor::at(player.tile)));
            if entry.0 != player.instance {
                *entry = (player.instance, Actor::at(player.tile));
            }
            entry
                .1
                .observe(player.tile, player.locomotion == Locomotion::Run);
        }
        for (index, npc) in entities.npcs() {
            let entry = self
                .npcs
                .entry(*index)
                .or_insert_with(|| (npc.instance, Actor::at(npc.tile)));
            if entry.0 != npc.instance {
                *entry = (npc.instance, Actor::at(npc.tile));
            }
            entry.1.observe(npc.tile, npc.locomotion == Locomotion::Run);
        }
    }

    /// Advance every actor by `cycles` client cycles.
    pub fn advance(&mut self, cycles: u32) {
        for _ in 0..cycles {
            for actor in self
                .players
                .values_mut()
                .map(|(_, actor)| actor)
                .chain(self.npcs.values_mut().map(|(_, actor)| actor))
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

    /// Footprint corner of an NPC in world units, or `None` before it is observed.
    #[must_use]
    pub fn npc(&self, index: NpcIndex) -> Option<[i32; 2]> {
        self.npcs.get(&index).map(|(_, actor)| actor.position)
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
        actor.observe(tile(11, 10), false);
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
        actor.observe(tile(2, 0), true);
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
            actor.observe(tile(x, 0), false);
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
        actor.observe(tile(6, 4), false);
        run_cycles(&mut actor, 10);
        assert_eq!(actor.position, [5 * 128 + 40, 5 * 128 - 40]);
    }

    #[test]
    fn teleports_and_plane_changes_snap_and_drop_the_queue() {
        let mut actor = Actor::at(tile(0, 0));
        actor.observe(tile(1, 0), false);
        actor.observe(tile(40, 40), false);
        assert_eq!(actor.position, [40 * 128, 40 * 128]);
        assert!(actor.queue.is_empty());
        let upstairs = WorldTile::new(40, 40, Plane::new(1).unwrap());
        actor.observe(upstairs, false);
        assert_eq!(actor.tile, upstairs);
        assert!(actor.queue.is_empty());
    }

    #[test]
    fn the_queue_never_exceeds_the_reference_depth() {
        let mut actor = Actor::at(tile(0, 0));
        for x in 1..=20 {
            actor.observe(tile(x % 2, 0), false);
        }
        assert!(actor.queue.len() <= MAX_QUEUE);
    }
}
