//! Rebuildable movement projection from cache geometry and committed overrides.
use crate::{Result, SceneAsset, SceneryInputs, locations};
use rustclient_cache::world::ObjectCollision;
use rustclient_model::{Plane, SceneDescriptor, WorldTile};
use std::collections::VecDeque;

const BLOCKED: u16 = 1 << 8;
const DIRECTIONS: [(i32, i32); 8] = [
    (-1, 0),
    (0, 1),
    (1, 0),
    (0, -1),
    (-1, 1),
    (1, 1),
    (1, -1),
    (-1, -1),
];

/// Collision is disposable asset/state projection, never a second world owner.
pub struct CollisionMap {
    descriptor: SceneDescriptor,
    flags: Vec<u16>,
}
impl CollisionMap {
    /// Build a single-plane movement map, including bridges and location morphs.
    /// # Errors
    /// Reports missing morph dependencies or invalid scene extents.
    pub fn build(asset: &SceneAsset, inputs: &SceneryInputs) -> Result<Self> {
        let descriptor = asset.descriptor();
        let mut map = Self {
            descriptor,
            flags: vec![0; (descriptor.width() * descriptor.height()) as usize],
        };
        let origin = descriptor.origin();
        for x in origin.x..origin.x + i32::try_from(descriptor.width()).unwrap_or(0) {
            for y in origin.y..origin.y + i32::try_from(descriptor.height()).unwrap_or(0) {
                if !asset.terrain_available(x, y) {
                    map.block(WorldTile::new(x, y, origin.plane));
                    continue;
                }
                for plane in 0..=3 {
                    let Some(plane) = Plane::new(plane) else {
                        continue;
                    };
                    if Self::collision_plane(asset, WorldTile::new(x, y, plane))
                        == Some(origin.plane)
                        && asset
                            .tile(plane, x, y)
                            .is_none_or(|tile| tile.settings & 1 != 0)
                    {
                        map.block(WorldTile::new(x, y, origin.plane));
                    }
                }
            }
        }
        for location in locations::effective_locations(asset, inputs) {
            if Self::collision_plane(asset, location.tile) != Some(origin.plane) {
                continue;
            }
            let Some(base) = asset.sources().objects.get(&location.object) else {
                continue;
            };
            let Some(def) = locations::resolve(asset, base, inputs)? else {
                continue;
            };
            if def.collision == ObjectCollision::None {
                continue;
            }
            let tile = WorldTile::new(location.tile.x, location.tile.y, origin.plane);
            let rotation = usize::from(location.orientation.get());
            match location.shape {
                0 => map.wall(tile, rotation),
                1 | 3 => map.wall(tile, 4 + rotation),
                2 => {
                    map.wall(tile, rotation);
                    map.wall(tile, (rotation + 1) & 3);
                }
                9..=21 => {
                    let size = if rotation & 1 == 0 {
                        def.size
                    } else {
                        [def.size[1], def.size[0]]
                    };
                    for x in 0..i32::from(size[0]) {
                        for y in 0..i32::from(size[1]) {
                            map.block(WorldTile::new(tile.x + x, tile.y + y, tile.plane));
                        }
                    }
                }
                22 if def.collision == ObjectCollision::GeometryAndFloorDecoration => {
                    map.block(tile);
                }
                _ => {}
            }
        }
        Ok(map)
    }
    fn collision_plane(asset: &SceneAsset, tile: WorldTile) -> Option<Plane> {
        if asset
            .tile(Plane::new(1)?, tile.x, tile.y)
            .is_some_and(|t| t.bridge())
        {
            Plane::new(tile.plane.get().checked_sub(1)?)
        } else {
            Some(tile.plane)
        }
    }
    fn index(&self, tile: WorldTile) -> Option<usize> {
        if !self.descriptor.contains(tile) {
            return None;
        }
        let origin = self.descriptor.origin();
        Some(
            usize::try_from(tile.x - origin.x).ok()? * self.descriptor.height() as usize
                + usize::try_from(tile.y - origin.y).ok()?,
        )
    }
    fn block(&mut self, tile: WorldTile) {
        if let Some(i) = self.index(tile) {
            self.flags[i] |= BLOCKED;
        }
    }
    fn wall(&mut self, tile: WorldTile, direction: usize) {
        if let Some(i) = self.index(tile) {
            self.flags[i] |= 1 << direction;
        }
        let (dx, dy) = DIRECTIONS[direction];
        let other = WorldTile::new(tile.x + dx, tile.y + dy, tile.plane);
        let reverse = if direction < 4 {
            (direction + 2) & 3
        } else {
            4 + ((direction - 4 + 2) & 3)
        };
        if let Some(i) = self.index(other) {
            self.flags[i] |= 1 << reverse;
        }
    }
    fn cardinal_step(&self, from: WorldTile, direction: usize) -> bool {
        let (dx, dy) = DIRECTIONS[direction];
        let to = WorldTile::new(from.x + dx, from.y + dy, from.plane);
        match (self.index(from), self.index(to)) {
            (Some(a), Some(b)) => {
                self.flags[a] & (1 << direction) == 0 && self.flags[b] & BLOCKED == 0
            }
            _ => false,
        }
    }
    fn step(&self, from: WorldTile, direction: usize) -> bool {
        if direction < 4 {
            return self.cardinal_step(from, direction);
        }
        let (dx, dy) = DIRECTIONS[direction];
        let horizontal = if dx < 0 { 0 } else { 2 };
        let vertical = if dy > 0 { 1 } else { 3 };
        let to = WorldTile::new(from.x + dx, from.y + dy, from.plane);
        self.index(from)
            .is_some_and(|i| self.flags[i] & (1 << direction) == 0)
            && self.index(to).is_some_and(|i| self.flags[i] & BLOCKED == 0)
            && self.cardinal_step(from, horizontal)
            && self.cardinal_step(from, vertical)
            && self.cardinal_step(WorldTile::new(from.x + dx, from.y, from.plane), vertical)
            && self.cardinal_step(WorldTile::new(from.x, from.y + dy, from.plane), horizontal)
    }
    /// Bounded reachability for a one-tile player. The server computes the final
    /// path; this rejects clicks through unloaded or unreachable geometry.
    #[must_use]
    pub fn reachable(&self, from: WorldTile, destination: WorldTile) -> bool {
        let (Some(start), Some(end)) = (self.index(from), self.index(destination)) else {
            return false;
        };
        if self.flags[end] & BLOCKED != 0 {
            return false;
        }
        let mut visited = vec![false; self.flags.len()];
        let mut queue = VecDeque::new();
        visited[start] = true;
        queue.push_back(from);
        while let Some(tile) = queue.pop_front() {
            if tile == destination {
                return true;
            }
            for (direction, (dx, dy)) in DIRECTIONS.iter().copied().enumerate() {
                let next = WorldTile::new(tile.x + dx, tile.y + dy, tile.plane);
                if let Some(i) = self.index(next)
                    && !visited[i]
                    && self.step(tile, direction)
                {
                    visited[i] = true;
                    queue.push_back(next);
                }
            }
        }
        false
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn routing_contract_respects_bounds_blockers_and_two_sided_walls() {
        let descriptor = SceneDescriptor::new(
            rustclient_model::SceneAssetId::new([0; 32]),
            WorldTile::new(100, 200, Plane::MIN),
            3,
            3,
        )
        .unwrap();
        let mut map = CollisionMap {
            descriptor,
            flags: vec![0; 9],
        };
        let start = WorldTile::new(101, 201, Plane::MIN);
        let west = WorldTile::new(100, 201, Plane::MIN);
        assert!(map.reachable(start, west));
        map.wall(start, 0);
        assert!(!map.cardinal_step(start, 0));
        assert!(!map.cardinal_step(west, 2));
        assert!(map.reachable(start, west)); // a route around the wall remains
        map.block(west);
        assert!(!map.reachable(start, west));
        assert!(!map.reachable(start, WorldTile::new(103, 201, Plane::MIN)));
        assert!(!map.reachable(start, WorldTile::new(101, 201, Plane::MAX)));
    }
}
