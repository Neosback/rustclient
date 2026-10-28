//! Conservative broad phase over blocks of the immutable static projection.
//! Exact intersections and original triangle order remain the picking contract.
use super::{intersection, surface_hit, terrain_tile};
use crate::{SceneError, SceneGeometry, SceneVisibility, SurfaceHit, WorldMesh};
use rustclient_model::{Plane, SceneDescriptor, WorldTile};

const BLOCK_VERTICES: usize = 96;

#[derive(Clone, Copy)]
struct Bounds {
    min: [i16; 3],
    max: [i16; 3],
}
impl Bounds {
    fn union(self, other: Self) -> Self {
        Self {
            min: std::array::from_fn(|i| self.min[i].min(other.min[i])),
            max: std::array::from_fn(|i| self.max[i].max(other.max[i])),
        }
    }
    fn intersects(self, origin: [f32; 3], direction: [f32; 3]) -> bool {
        let mut near = 0.0_f64;
        let mut far = f64::INFINITY;
        for axis in 0..3 {
            // Pad integer bounds for the exact f32 triangle calculation's edge rounding.
            let low = f64::from(self.min[axis]) - 1.;
            let high = f64::from(self.max[axis]) + 1.;
            let start = f64::from(origin[axis]);
            let delta = f64::from(direction[axis]);
            if delta == 0. {
                if start < low || start > high {
                    return false;
                }
            } else {
                let a = (low - start) / delta;
                let b = (high - start) / delta;
                near = near.max(a.min(b));
                far = far.min(a.max(b));
                if far < near {
                    return false;
                }
            }
        }
        true
    }
}
#[derive(Clone, Copy)]
struct Node {
    bounds: Bounds,
    first: u32,
    end: u32,
    leaf: bool,
}
#[derive(Clone, Copy)]
struct Block {
    bounds: Bounds,
    first: u32,
    end: u32,
}
/// Retained resource costs; triangle storage remains owned by `SceneGeometry`.
#[derive(Debug, Clone, Copy)]
pub struct PickIndexStats {
    pub blocks: usize,
    pub resident_bytes: usize,
}
/// Immutable acceleration projection. Rebuild when the source geometry changes.
/// Mismatched allocations fall back to the original linear query.
pub struct ScenePickIndex {
    source: usize,
    vertices: usize,
    blocks: usize,
    nodes: Vec<Node>,
}
impl ScenePickIndex {
    /// Build a balanced index with one leaf per 32 original triangles.
    /// # Errors
    /// Rejects source vertex counts beyond the index's u32 address space.
    pub fn new(geometry: &SceneGeometry) -> Result<Self, SceneError> {
        let count = geometry.vertices.len() / 3 * 3;
        u32::try_from(count).map_err(|_| SceneError("picking vertex count exceeds u32".into()))?;
        let mut blocks = Vec::with_capacity(count.div_ceil(BLOCK_VERTICES));
        for first in (0..count).step_by(BLOCK_VERTICES) {
            let end = (first + BLOCK_VERTICES).min(count);
            let mut bounds = Bounds {
                min: [i16::MAX; 3],
                max: [i16::MIN; 3],
            };
            for vertex in &geometry.vertices[first..end] {
                for axis in 0..3 {
                    bounds.min[axis] = bounds.min[axis].min(vertex.position[axis]);
                    bounds.max[axis] = bounds.max[axis].max(vertex.position[axis]);
                }
            }
            blocks.push(Block {
                bounds,
                first: u32::try_from(first)
                    .map_err(|_| SceneError("picking offset exceeds u32".into()))?,
                end: u32::try_from(end)
                    .map_err(|_| SceneError("picking offset exceeds u32".into()))?,
            });
        }
        let mut index = Self {
            source: geometry.vertices.as_ptr() as usize,
            vertices: geometry.vertices.len(),
            blocks: blocks.len(),
            nodes: Vec::with_capacity(blocks.len().saturating_mul(2).saturating_sub(1)),
        };
        if !blocks.is_empty() {
            index.build(&mut blocks);
        }
        Ok(index)
    }
    fn build(&mut self, blocks: &mut [Block]) -> u32 {
        // At most 2 * ceil(u32::MAX / 96) nodes fit in u32.
        let id = u32::try_from(self.nodes.len()).expect("bounded picking nodes");
        let bounds = blocks
            .iter()
            .skip(1)
            .fold(blocks[0].bounds, |a, b| a.union(b.bounds));
        self.nodes.push(Node {
            bounds,
            first: blocks[0].first,
            end: blocks[0].end,
            leaf: blocks.len() == 1,
        });
        if blocks.len() > 1 {
            let axis = (0..3)
                .max_by_key(|&i| i32::from(bounds.max[i]) - i32::from(bounds.min[i]))
                .expect("three axes");
            let middle = blocks.len() / 2;
            blocks.select_nth_unstable_by_key(middle, |block| {
                (
                    i32::from(block.bounds.min[axis]) + i32::from(block.bounds.max[axis]),
                    block.first,
                )
            });
            let (left, right) = blocks.split_at_mut(middle);
            let first = self.build(left);
            let end = self.build(right);
            self.nodes[id as usize].first = first;
            self.nodes[id as usize].end = end;
        }
        id
    }
    #[must_use]
    pub fn stats(&self) -> PickIndexStats {
        PickIndexStats {
            blocks: self.blocks,
            resident_bytes: self.nodes.capacity() * std::mem::size_of::<Node>(),
        }
    }
    fn matches(&self, geometry: &SceneGeometry, origin: [f32; 3]) -> bool {
        self.source == geometry.vertices.as_ptr() as usize
            && self.vertices == geometry.vertices.len()
            && origin.iter().all(|v| v.abs() <= 16_777_216.)
    }
    #[expect(
        clippy::float_cmp,
        reason = "exact distance ties must retain original source triangle order"
    )]
    fn nearest(
        &self,
        geometry: &SceneGeometry,
        limit: usize,
        origin: [f32; 3],
        direction: [f32; 3],
        visibility: &SceneVisibility,
        surface: bool,
    ) -> Option<(usize, f32)> {
        if self.nodes.is_empty() || origin.iter().chain(&direction).any(|v| !v.is_finite()) {
            return None;
        }
        // Balanced tree depth is below 32 for the supported source size.
        let mut stack = [0_u32; 64];
        let mut depth = 1;
        let mut hit = None;
        while depth > 0 {
            depth -= 1;
            let node = self.nodes[stack[depth] as usize];
            if !node.bounds.intersects(origin, direction) {
                continue;
            }
            if !node.leaf {
                stack[depth] = node.first;
                stack[depth + 1] = node.end;
                depth += 2;
                continue;
            }
            for vertex in (node.first as usize..(node.end as usize).min(limit)).step_by(3) {
                let triangle = &geometry.vertices[vertex..vertex + 3];
                if !visibility.shows_vertex(&triangle[0])
                    || (surface && triangle.iter().all(|v| v.abhsl >> 24 == 255))
                {
                    continue;
                }
                let points = std::array::from_fn(|i| {
                    std::array::from_fn(|axis| f32::from(triangle[i].position[axis]))
                });
                if let Some(distance) = intersection(points, origin, direction)
                    && distance.is_finite()
                    && hit.is_none_or(|(old_vertex, old_distance)| {
                        distance < old_distance || (distance == old_distance && vertex < old_vertex)
                    })
                {
                    hit = Some((vertex, distance));
                }
            }
        }
        hit
    }
    #[must_use]
    pub fn pick_surface(
        &self,
        geometry: &SceneGeometry,
        origin: [f32; 3],
        direction: [f32; 3],
        visibility: &SceneVisibility,
    ) -> Option<SurfaceHit> {
        if !self.matches(geometry, origin) {
            return super::pick_surface(geometry, origin, direction, visibility);
        }
        self.nearest(
            geometry,
            geometry.vertices.len() / 3 * 3,
            origin,
            direction,
            visibility,
            true,
        )
        .map(|(vertex, distance)| surface_hit(geometry, vertex, distance))
    }
    #[must_use]
    pub fn pick_terrain(
        &self,
        mesh: &WorldMesh,
        descriptor: SceneDescriptor,
        plane: Plane,
        origin: [f32; 3],
        direction: [f32; 3],
        visibility: &SceneVisibility,
    ) -> Option<WorldTile> {
        if !self.matches(&mesh.geometry, origin) {
            return super::pick_terrain(mesh, descriptor, plane, origin, direction, visibility);
        }
        if mesh.asset != descriptor.id() {
            return None;
        }
        let count = usize::try_from(mesh.stats.terrain_triangles)
            .ok()?
            .saturating_mul(3);
        let (_, distance) =
            self.nearest(&mesh.geometry, count, origin, direction, visibility, false)?;
        terrain_tile(descriptor, plane, origin, direction, distance)
    }
}
