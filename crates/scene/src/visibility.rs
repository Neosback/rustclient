//! Shared whole-primitive admission for CPU picking and GPU rasterization.
use crate::{RoofMask, SceneVertex};

/// One scene-local owning tile, not an individual vertex's position. Signed ten-bit axes cover
/// the supported 192-tile scene plus off-scene temporary actors. Bits 20/21 mark unrelated/outside.
#[repr(transparent)]
#[derive(Debug, Default, Clone, Copy, PartialEq, Eq, bytemuck::Pod, bytemuck::Zeroable)]
pub struct DrawTile(u32);
const AXIS_MASK: u32 = 1023;
const UNRELATED: u32 = 1 << 20;
const OUTSIDE: u32 = 1 << 21;
impl DrawTile {
    #[must_use]
    pub fn scenery(tile: [i32; 2], related: bool) -> Self {
        if tile.iter().any(|v| !(-512..512).contains(v)) {
            return Self(OUTSIDE);
        }
        Self(
            (tile[0].cast_unsigned() & AXIS_MASK)
                | ((tile[1].cast_unsigned() & AXIS_MASK) << 10)
                | if related { 0 } else { UNRELATED },
        )
    }
    /// Temporary actors are independent from `RegionManager`'s removal of scenery tiles.
    #[must_use]
    pub fn actor(tile: [i32; 2]) -> Self {
        Self::scenery(tile, true)
    }
    #[must_use]
    pub fn tile(self) -> [i32; 2] {
        [
            (self.0 << 22).cast_signed() >> 22,
            (self.0 << 12).cast_signed() >> 22,
        ]
    }
    #[must_use]
    pub const fn unrelated(self) -> bool {
        self.0 & UNRELATED != 0
    }
    #[must_use]
    pub const fn outside(self) -> bool {
        self.0 & OUTSIDE != 0
    }
}

/// Inclusive zone square from revision-240 Scene.method4474. Scene offset is a multiple of eight,
/// so working in core-scene coordinates gives the same zone differences without baking that offset.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct ZoneWindow {
    /// Minimum X/Z zone, maximum X/Z zone, inclusive.
    pub bounds: [i32; 4],
}
impl ZoneWindow {
    #[must_use]
    pub fn new(camera_fine: [i32; 2], tiles: u32) -> Self {
        let [x, z] = camera_fine.map(|v| v >> 10);
        // Widening from the source's bounded u8 radius cannot overflow.
        let radius = i32::from(u8::try_from(tiles.min(184) >> 3).unwrap_or_default());
        Self {
            bounds: [x - radius, z - radius, x + radius, z + radius],
        }
    }
    #[must_use]
    pub fn contains(self, tile: [i32; 2]) -> bool {
        let [x, z] = tile.map(|v| v >> 3);
        (self.bounds[0]..=self.bounds[2]).contains(&x)
            && (self.bounds[1]..=self.bounds[3]).contains(&z)
    }
}

/// Disposable frame visibility. This is the single predicate used for rendering and picking.
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct SceneVisibility {
    pub top_level: u8,
    pub hidden: RoofMask,
    pub zones: Option<ZoneWindow>,
    pub hide_unrelated_maps: bool,
}
impl SceneVisibility {
    #[must_use]
    pub fn everything() -> Self {
        Self {
            top_level: 3,
            hidden: RoofMask::default(),
            zones: None,
            hide_unrelated_maps: false,
        }
    }
    #[must_use]
    pub fn with_policy(
        mut self,
        camera_fine: [i32; 2],
        draw_distance: u32,
        hide_unrelated_maps: bool,
    ) -> Self {
        self.zones = Some(ZoneWindow::new(camera_fine, draw_distance));
        self.hide_unrelated_maps = hide_unrelated_maps;
        self
    }
    #[must_use]
    pub fn shows(&self, tag: u16) -> bool {
        usize::from(tag & 3) <= usize::from(self.top_level)
            && !self.hidden.hides(usize::from(tag >> 2))
    }
    #[must_use]
    pub fn shows_vertex(&self, vertex: &SceneVertex) -> bool {
        self.shows(vertex.render_tag()) && self.shows_tile(vertex.draw_tile)
    }
    #[must_use]
    pub fn shows_tile(&self, tile: DrawTile) -> bool {
        !(tile.outside() || self.hide_unrelated_maps && tile.unrelated())
            && self.zones.is_none_or(|zones| zones.contains(tile.tile()))
    }
    /// vec4/ivec4 blocks mirrored by the WGSL camera uniform.
    #[must_use]
    pub fn gpu_admission(&self) -> ([i32; 4], [u32; 4]) {
        (
            self.zones.map_or([0; 4], |zones| zones.bounds),
            [
                u32::from(self.zones.is_some()),
                u32::from(self.hide_unrelated_maps),
                0,
                0,
            ],
        )
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn source_zone_loop_and_shared_admission_contract() {
        // Independent reference loop: enumerate source camera zone +/- (distance >> 3).
        for eye in [-1025, -1, 0, 1023, 1024, 6600] {
            for distance in [0, 7, 8, 50, 184] {
                let window = ZoneWindow::new([eye, eye], distance);
                let center = eye >> 10;
                let radius = i32::try_from(distance >> 3).unwrap();
                for x in -24..26 {
                    for z in -24..26 {
                        let source = (-radius..=radius).any(|dx| {
                            (-radius..=radius).any(|dz| x == center + dx && z == center + dz)
                        });
                        assert_eq!(window.contains([x * 8, z * 8]), source);
                    }
                }
            }
        }
        let policy = SceneVisibility::everything().with_policy([0, 0], 0, true);
        assert!(policy.shows_tile(DrawTile::scenery([7, 7], true)));
        assert!(!policy.shows_tile(DrawTile::scenery([8, 0], true)));
        assert!(!policy.shows_tile(DrawTile::scenery([0, 0], false)));
        assert!(policy.shows_tile(DrawTile::actor([0, 0])));
        assert_eq!(DrawTile::actor([-1, 191]).tile(), [-1, 191]);
        assert!(DrawTile::actor([512, 0]).outside());
        assert_eq!(size_of::<DrawTile>(), 4);
        let points = [[0, 0, 0, 0], [128, 0, 0, 0], [0, 0, 128, 0]];
        let mut geometry = crate::SceneGeometry {
            vertices: points
                .map(|position| crate::SceneVertex {
                    position,
                    abhsl: 0,
                    texture_uv: [0; 4],
                    draw_tile: DrawTile::scenery([0, 0], false),
                })
                .to_vec(),
            ..crate::SceneGeometry::default()
        };
        let ray = ([32., 100., 32.], [0., -1., 0.]);
        assert!(crate::pick_surface(&geometry, ray.0, ray.1, &policy).is_none());
        assert!(
            crate::pick_surface(&geometry, ray.0, ray.1, &SceneVisibility::everything()).is_some()
        );
        // Geometry near the ray belongs to a different whole-model tile: both CPU and GPU
        // reject the model by its anchor, rather than cutting it at a vertex position.
        for vertex in &mut geometry.vertices {
            vertex.draw_tile = DrawTile::actor([8, 0]);
        }
        assert!(crate::pick_surface(&geometry, ray.0, ray.1, &policy).is_none());
    }
}
