//! Stable references to immutable scene assets, independent of render representations.
use crate::{Plane, WorldTile};
/// World-space centre of a map window, independent of the player's current plane.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct MapAnchor {
    pub x: i32,
    pub y: i32,
}
impl MapAnchor {
    #[must_use]
    pub const fn on_plane(self, plane: Plane) -> WorldTile {
        WorldTile::new(self.x, self.y, plane)
    }
}
/// SHA-256 content identity allocated by the immutable scene asset catalog.
#[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord, Hash)]
pub struct SceneAssetId([u8; 32]);
impl SceneAssetId {
    #[must_use]
    pub const fn new(bytes: [u8; 32]) -> Self {
        Self(bytes)
    }
    #[must_use]
    pub const fn bytes(self) -> [u8; 32] {
        self.0
    }
}
/// Authoritative asset reference and bounded world footprint for a loaded scene.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct SceneDescriptor {
    id: SceneAssetId,
    origin: WorldTile,
    width: u32,
    height: u32,
}
impl SceneDescriptor {
    /// Validate nonempty extents and checked world-coordinate arithmetic.
    #[must_use]
    pub fn new(id: SceneAssetId, origin: WorldTile, width: u32, height: u32) -> Option<Self> {
        if width == 0 || height == 0 {
            return None;
        }
        origin.x.checked_add(i32::try_from(width).ok()?)?;
        origin.y.checked_add(i32::try_from(height).ok()?)?;
        Some(Self {
            id,
            origin,
            width,
            height,
        })
    }
    #[must_use]
    pub const fn id(self) -> SceneAssetId {
        self.id
    }
    #[must_use]
    pub const fn origin(self) -> WorldTile {
        self.origin
    }
    #[must_use]
    pub const fn width(self) -> u32 {
        self.width
    }
    #[must_use]
    pub const fn height(self) -> u32 {
        self.height
    }
    #[must_use]
    pub const fn with_plane(mut self, plane: Plane) -> Self {
        self.origin.plane = plane;
        self
    }
    #[must_use]
    pub fn contains_footprint(self, tile: WorldTile) -> bool {
        i64::from(tile.x) >= i64::from(self.origin.x)
            && i64::from(tile.y) >= i64::from(self.origin.y)
            && i64::from(tile.x) < i64::from(self.origin.x) + i64::from(self.width)
            && i64::from(tile.y) < i64::from(self.origin.y) + i64::from(self.height)
    }
    /// Whether the tile's 2D footprint lies in this scene window.
    ///
    /// A scene window owns the same `(x, y)` footprint on every ordinary
    /// plane (`0..=3`); the plane in [`Self::origin`] records how the
    /// descriptor was constructed, not which planes the window can display.
    #[must_use]
    pub fn contains(self, tile: WorldTile) -> bool {
        self.contains_footprint(tile)
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use crate::Plane;
    #[test]
    fn descriptor_has_checked_exclusive_bounds_and_all_planes() {
        let id = SceneAssetId::new([0; 32]);
        let origin = WorldTile::new(10, 20, Plane::MIN);
        let d = SceneDescriptor::new(id, origin, 2, 3).unwrap();
        assert!(d.contains(WorldTile::new(11, 22, Plane::MIN)));
        assert!(!d.contains(WorldTile::new(12, 22, Plane::MIN)));
        assert!(!d.contains(WorldTile::new(11, 23, Plane::MIN)));
        for plane in [0, 1, 2, 3] {
            let plane = Plane::new(plane).unwrap();
            assert!(d.contains(WorldTile::new(11, 22, plane)));
            assert!(!d.contains(WorldTile::new(12, 22, plane)));
        }
        assert_eq!(d.with_plane(Plane::MAX).origin().plane, Plane::MAX);
        assert!(SceneDescriptor::new(id, origin, 0, 3).is_none());
        assert!(SceneDescriptor::new(id, WorldTile::new(i32::MAX, 0, Plane::MIN), 1, 1).is_none());
    }
}
