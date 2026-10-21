//! Semantic coordinate spaces used by the client core.
//!
//! World tiles, region-local tiles, scene tiles, world-fine coordinates, and
//! model-local points are intentionally different types. Renderer camera,
//! viewport, and clip-space coordinates do not belong here.

use core::fmt;

/// Number of fine/local units in one world tile.
pub const FINE_UNITS_PER_TILE: i32 = 128;
/// Number of tiles along one OSRS map-region axis.
pub const REGION_SIZE_TILES: i32 = 64;
const REGION_SIZE_TILES_U8: u8 = 64;
/// Highest ordinary world plane index.
pub const MAX_PLANE: u8 = 3;

/// Validated ordinary world plane (`0..=3`).
#[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord, Hash)]
pub struct Plane(u8);

impl Plane {
    /// Lowest ordinary plane.
    pub const MIN: Self = Self(0);
    /// Highest ordinary plane.
    pub const MAX: Self = Self(MAX_PLANE);

    /// Construct a validated plane.
    #[must_use]
    pub const fn new(value: u8) -> Option<Self> {
        if value <= MAX_PLANE {
            Some(Self(value))
        } else {
            None
        }
    }

    /// Return the numeric plane index.
    #[must_use]
    pub const fn get(self) -> u8 {
        self.0
    }
}

/// Error returned when converting an out-of-range primitive to [`Plane`].
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct PlaneError {
    value: u8,
}

impl PlaneError {
    /// Return the rejected primitive value.
    #[must_use]
    pub const fn value(self) -> u8 {
        self.value
    }
}

impl fmt::Display for PlaneError {
    fn fmt(&self, formatter: &mut fmt::Formatter<'_>) -> fmt::Result {
        write!(formatter, "plane {} is outside 0..={MAX_PLANE}", self.value)
    }
}

impl std::error::Error for PlaneError {}

impl TryFrom<u8> for Plane {
    type Error = PlaneError;

    fn try_from(value: u8) -> Result<Self, Self::Error> {
        Self::new(value).ok_or(PlaneError { value })
    }
}

impl From<Plane> for u8 {
    fn from(value: Plane) -> Self {
        value.get()
    }
}

/// Global tile coordinate in the game world.
#[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord, Hash)]
pub struct WorldTile {
    /// World X tile.
    pub x: i32,
    /// World Y tile.
    pub y: i32,
    /// World plane.
    pub plane: Plane,
}

impl WorldTile {
    /// Construct a world tile.
    #[must_use]
    pub const fn new(x: i32, y: i32, plane: Plane) -> Self {
        Self { x, y, plane }
    }

    /// Split into a global region coordinate and a validated tile inside it.
    #[must_use]
    #[allow(clippy::cast_possible_truncation)]
    pub fn split_region(self) -> (RegionCoord, RegionTile) {
        let region = RegionCoord::new(
            self.x.div_euclid(REGION_SIZE_TILES),
            self.y.div_euclid(REGION_SIZE_TILES),
        );
        let tile = RegionTile {
            x: self.x.rem_euclid(REGION_SIZE_TILES) as u8,
            y: self.y.rem_euclid(REGION_SIZE_TILES) as u8,
        };
        (region, tile)
    }

    /// Reconstruct a world tile from a region and in-region tile.
    #[must_use]
    pub fn from_region(region: RegionCoord, tile: RegionTile, plane: Plane) -> Option<Self> {
        let x = region
            .x
            .checked_mul(REGION_SIZE_TILES)?
            .checked_add(i32::from(tile.x))?;
        let y = region
            .y
            .checked_mul(REGION_SIZE_TILES)?
            .checked_add(i32::from(tile.y))?;
        Some(Self::new(x, y, plane))
    }

    /// Convert to scene coordinates relative to `scene_origin`.
    ///
    /// Returns `None` for another plane, coordinates before the origin, or
    /// values that do not fit the scene-coordinate representation.
    #[must_use]
    pub fn to_scene(self, scene_origin: Self) -> Option<SceneTile> {
        if self.plane != scene_origin.plane {
            return None;
        }

        let x = self.x.checked_sub(scene_origin.x)?;
        let y = self.y.checked_sub(scene_origin.y)?;
        Some(SceneTile::new(
            u32::try_from(x).ok()?,
            u32::try_from(y).ok()?,
            self.plane,
        ))
    }

    /// Convert the tile origin to exact absolute world-fine coordinates.
    #[must_use]
    pub fn world_fine_origin(self) -> Option<WorldFinePoint> {
        Some(WorldFinePoint::new(
            FineCoord::from_tiles(self.x)?,
            FineCoord::from_tiles(self.y)?,
            self.plane,
        ))
    }
}

/// Global region coordinate. One region is 64×64 tiles.
#[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord, Hash)]
pub struct RegionCoord {
    /// Region X.
    pub x: i32,
    /// Region Y.
    pub y: i32,
}

impl RegionCoord {
    /// Construct a region coordinate.
    #[must_use]
    pub const fn new(x: i32, y: i32) -> Self {
        Self { x, y }
    }
}

/// Validated tile inside a 64×64 region.
#[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord, Hash)]
pub struct RegionTile {
    x: u8,
    y: u8,
}

impl RegionTile {
    /// Construct an in-region tile.
    #[must_use]
    pub const fn new(x: u8, y: u8) -> Option<Self> {
        if x < REGION_SIZE_TILES_U8 && y < REGION_SIZE_TILES_U8 {
            Some(Self { x, y })
        } else {
            None
        }
    }

    /// Region-local X (`0..=63`).
    #[must_use]
    pub const fn x(self) -> u8 {
        self.x
    }

    /// Region-local Y (`0..=63`).
    #[must_use]
    pub const fn y(self) -> u8 {
        self.y
    }
}

/// Tile inside a currently constructed scene.
///
/// No universal scene width or height is encoded in this type. A profile or
/// scene owner validates actual scene bounds.
#[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord, Hash)]
pub struct SceneTile {
    /// Scene-local X tile.
    pub x: u32,
    /// Scene-local Y tile.
    pub y: u32,
    /// Scene plane.
    pub plane: Plane,
}

impl SceneTile {
    /// Construct a scene tile.
    #[must_use]
    pub const fn new(x: u32, y: u32, plane: Plane) -> Self {
        Self { x, y, plane }
    }

    /// Convert back to a world tile relative to `scene_origin`.
    #[must_use]
    pub fn to_world(self, scene_origin: WorldTile) -> Option<WorldTile> {
        if self.plane != scene_origin.plane {
            return None;
        }

        let x = scene_origin.x.checked_add(i32::try_from(self.x).ok()?)?;
        let y = scene_origin.y.checked_add(i32::try_from(self.y).ok()?)?;
        Some(WorldTile::new(x, y, self.plane))
    }
}

/// One signed coordinate measured in exact 128-unit tile space.
#[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord, Hash)]
pub struct FineCoord(i32);

impl FineCoord {
    /// Construct directly from fine units.
    #[must_use]
    pub const fn from_units(units: i32) -> Self {
        Self(units)
    }

    /// Construct the exact origin of a tile coordinate.
    #[must_use]
    pub fn from_tiles(tiles: i32) -> Option<Self> {
        tiles.checked_mul(FINE_UNITS_PER_TILE).map(Self)
    }

    /// Return raw fine units.
    #[must_use]
    pub const fn units(self) -> i32 {
        self.0
    }

    /// Tile containing this coordinate using Euclidean floor semantics.
    #[must_use]
    pub const fn tile_floor(self) -> i32 {
        self.0.div_euclid(FINE_UNITS_PER_TILE)
    }

    /// Non-negative offset inside the containing tile (`0..=127`).
    #[must_use]
    pub const fn offset_in_tile(self) -> i32 {
        self.0.rem_euclid(FINE_UNITS_PER_TILE)
    }

    /// Checked fine-coordinate addition.
    #[must_use]
    pub const fn checked_add(self, other: Self) -> Option<Self> {
        match self.0.checked_add(other.0) {
            Some(value) => Some(Self(value)),
            None => None,
        }
    }
}

/// Plane-aware absolute world position in exact fine units.
///
/// This is not RuneLite-style scene-local `LocalPoint`; scene-relative fine
/// coordinates, if needed, must use a separately named type.
#[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord, Hash)]
pub struct WorldFinePoint {
    /// Absolute fine X.
    pub x: FineCoord,
    /// Absolute fine Y.
    pub y: FineCoord,
    /// World plane.
    pub plane: Plane,
}

impl WorldFinePoint {
    /// Construct an absolute world-fine point.
    #[must_use]
    pub const fn new(x: FineCoord, y: FineCoord, plane: Plane) -> Self {
        Self { x, y, plane }
    }
}

/// Asset-relative model vertex before scene placement.
///
/// These axes are model-space axes and must not be mixed with world/scene/fine
/// coordinates without an explicit placement transform.
#[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord, Hash)]
pub struct ModelPoint {
    /// Model X.
    pub x: i32,
    /// Model vertical Y.
    pub y: i32,
    /// Model Z.
    pub z: i32,
}

impl ModelPoint {
    /// Construct a model-space point.
    #[must_use]
    pub const fn new(x: i32, y: i32, z: i32) -> Self {
        Self { x, y, z }
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn plane_range_is_explicit() {
        assert_eq!(Plane::new(0), Some(Plane::MIN));
        assert_eq!(Plane::new(3), Some(Plane::MAX));
        assert_eq!(Plane::new(4), None);
    }

    #[test]
    fn region_split_uses_euclidean_coordinates() {
        let tile = WorldTile::new(-1, 64, Plane::MIN);
        let (region, local) = tile.split_region();

        assert_eq!(region, RegionCoord::new(-1, 1));
        assert_eq!(local.x(), 63);
        assert_eq!(local.y(), 0);
        assert_eq!(WorldTile::from_region(region, local, Plane::MIN), Some(tile));
    }

    #[test]
    fn invalid_region_tile_is_rejected() {
        assert!(RegionTile::new(63, 63).is_some());
        assert!(RegionTile::new(64, 0).is_none());
        assert!(RegionTile::new(0, 64).is_none());
    }

    #[test]
    fn scene_conversion_is_plane_aware_and_reversible() {
        let origin = WorldTile::new(3200, 3200, Plane::MIN);
        let world = WorldTile::new(3250, 3260, Plane::MIN);
        let scene = world.to_scene(origin).expect("tile is inside positive scene space");

        assert_eq!(scene, SceneTile::new(50, 60, Plane::MIN));
        assert_eq!(scene.to_world(origin), Some(world));
        assert_eq!(
            WorldTile::new(3250, 3260, Plane::MAX).to_scene(origin),
            None
        );
    }

    #[test]
    fn fine_coordinates_floor_negative_values_correctly() {
        let coordinate = FineCoord::from_units(-1);
        assert_eq!(coordinate.tile_floor(), -1);
        assert_eq!(coordinate.offset_in_tile(), 127);
    }

    #[test]
    fn tile_to_world_fine_conversion_is_exact() {
        let tile = WorldTile::new(10, 20, Plane::MAX);
        let fine = tile
            .world_fine_origin()
            .expect("small tile values fit fine coordinates");

        assert_eq!(fine.x.units(), 1_280);
        assert_eq!(fine.y.units(), 2_560);
        assert_eq!(fine.plane, Plane::MAX);
    }

    #[test]
    fn overflowing_coordinate_conversions_fail_instead_of_wrapping() {
        assert!(FineCoord::from_tiles(i32::MAX).is_none());
        let region = RegionCoord::new(i32::MAX, 0);
        let tile = RegionTile::new(63, 0).expect("63 is a valid in-region tile");
        assert!(WorldTile::from_region(region, tile, Plane::MIN).is_none());
    }
}
