//! Immutable scene asset assembly and disposable CPU mesh projection.
#![forbid(unsafe_code)]
#![allow(
    clippy::manual_midpoint,
    reason = "deob-derived curve solving preserves the reference floating-point operation order"
)]
#![allow(
    clippy::chunks_exact_to_as_chunks,
    reason = "scene topology is expressed as fixed-width complete records and intentionally keeps reference chunk iteration"
)]
mod animation;
mod animation_assets;
pub use animation_assets::AnimationAssets;
mod collision;
mod color;
pub mod item_icon;
pub use collision::CollisionMap;
pub mod entity_mesh;
mod motion;
pub use motion::{ActorPose, Facing, Pace, Pose};
mod authoritative_motion;
pub use authoritative_motion::ActorMotion;
mod inputs;
mod lighting;
pub mod minimap;
pub use inputs::SceneryInputs;
mod locations;
pub use locations::location_key;
mod mesh;
mod picking;
mod roofs;
pub use picking::{SurfaceHit, pick_surface, pick_terrain};
pub use roofs::RoofView;
mod shapes;
mod terrain;
mod uv;
use core::fmt;
pub use mesh::{
    ActorLabelAnchor, ActorLabelId, PickRange, SceneGeometry, SceneStats, SceneVertex, WorldMesh, build_dynamic, build_mesh,
    build_mesh_with_inputs,
};
use rustclient_cache::world::{TerrainTile, WorldAssets};
use rustclient_model::{Plane, SceneAssetId, SceneDescriptor, WorldTile};
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct SceneError(pub String);
impl fmt::Display for SceneError {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        f.write_str(&self.0)
    }
}
impl std::error::Error for SceneError {}
type Result<T> = std::result::Result<T, SceneError>;
/// Immutable catalog asset. Only `GameState`'s descriptor selects the current scene.
pub struct SceneAsset {
    descriptor: SceneDescriptor,
    assets: WorldAssets,
    /// `ObjectComposition.floor_lift` of each tile's floor decoration, keyed by the plane the
    /// client files the decoration under (bridge decks sit one plane lower) and world tile.
    floor_lifts: std::collections::HashMap<(u8, i32, i32), u8>,
}
/// Shape code of a floor decoration, the client's "ground object".
const FLOOR_DECORATION: u8 = 22;
impl SceneAsset {
    /// # Errors
    /// Rejects inconsistent extents, tile counts or missing height borders.
    pub fn new(assets: WorldAssets, origin: WorldTile, width: u32, height: u32) -> Result<Self> {
        if width > 192
            || height > 192
            || assets.squares.iter().any(|s| s.tiles.len() != 4 * 64 * 64)
        {
            return Err(SceneError("invalid scene asset dimensions".into()));
        }
        let descriptor = SceneDescriptor::new(
            SceneAssetId::new(assets.content_hash),
            origin,
            width,
            height,
        )
        .ok_or_else(|| SceneError("invalid scene footprint".into()))?;
        let mut this = Self {
            descriptor,
            assets,
            floor_lifts: std::collections::HashMap::new(),
        };
        for x in 0..=width {
            for y in 0..=height {
                if this
                    .tile(
                        origin.plane,
                        origin.x + i32::try_from(x).map_err(|_| SceneError("x overflow".into()))?,
                        origin.y + i32::try_from(y).map_err(|_| SceneError("y overflow".into()))?,
                    )
                    .is_none()
                {
                    return Err(SceneError(
                        "scene is missing required terrain border".into(),
                    ));
                }
            }
        }
        this.index_floor_lifts();
        Ok(this)
    }
    fn index_floor_lifts(&mut self) {
        let mut lifts = std::collections::HashMap::new();
        for loc in self.assets.squares.iter().flat_map(|s| &s.locations) {
            let lift = self
                .assets
                .objects
                .get(&loc.object)
                .map_or(0, |def| def.floor_lift);
            if loc.shape != FLOOR_DECORATION {
                continue;
            }
            let tile = loc.tile;
            let deck = self
                .tile(Plane::new(1).expect("plane"), tile.x, tile.y)
                .is_some_and(|t| t.bridge());
            // Under a bridge the ground-floor tile is the deck's lower link, not the tile an
            // actor on the bridge stands on.
            if deck && tile.plane.get() == 0 {
                continue;
            }
            let plane = tile.plane.get() - u8::from(deck);
            // One decoration per tile: a later one replaces the earlier, as in the client.
            lifts.insert((plane, tile.x, tile.y), lift);
        }
        lifts.retain(|_, lift| *lift != 0);
        self.floor_lifts = lifts;
    }
    #[must_use]
    pub const fn descriptor(&self) -> SceneDescriptor {
        self.descriptor
    }
    #[must_use]
    pub const fn sources(&self) -> &WorldAssets {
        &self.assets
    }
    #[must_use]
    pub fn tile(&self, plane: Plane, x: i32, y: i32) -> Option<&TerrainTile> {
        let square = self
            .assets
            .squares
            .iter()
            .find(|s| s.coord.x == x.div_euclid(64) && s.coord.y == y.div_euclid(64))?;
        let index = usize::from(plane.get()) * 4096
            + usize::try_from(x.rem_euclid(64)).ok()? * 64
            + usize::try_from(y.rem_euclid(64)).ok()?;
        square.tiles.get(index)
    }
    /// Resolve a currently present location through the same overrides/morphs used to render it.
    /// # Errors
    /// Reports missing definition/morph dependencies.
    pub fn location_definition(
        &self,
        key: rustclient_model::LocationKey,
        base: rustclient_model::ObjectDefinitionId,
        inputs: &SceneryInputs,
    ) -> Result<Option<&rustclient_cache::world::ObjectDefinition>> {
        let present = locations::effective_locations(self, inputs)
            .iter()
            .any(|loc| locations::location_key(loc) == key && loc.object == base);
        if !present {
            return Ok(None);
        }
        let definition = self
            .sources()
            .objects
            .get(&base)
            .ok_or_else(|| SceneError("location definition unavailable".into()))?;
        locations::resolve(self, definition, inputs)
    }
    /// Bridge-aware scene-local floor height, in reference negative-up fine units, as the 240
    /// client reports it (`WorldView.method3794`): the drawn floor triangle where the tile has
    /// one, otherwise a bilinear corner blend, raised by the tile's floor-decoration lift.
    /// # Errors
    /// Rejects points outside the retained terrain height border.
    #[expect(
        clippy::missing_panics_doc,
        reason = "validated Plane values make internal plane construction infallible"
    )]
    pub fn ground_height(&self, plane: Plane, fine: [i32; 2]) -> Result<i32> {
        let origin = self.descriptor.origin();
        let x = origin.x + fine[0].div_euclid(128);
        let y = origin.y + fine[1].div_euclid(128);
        let floor_plane = if plane.get() < 3
            && self
                .tile(Plane::new(1).expect("plane"), x, y)
                .is_some_and(|t| t.bridge())
        {
            Plane::new(plane.get() + 1).expect("bounded plane")
        } else {
            plane
        };
        let fx = fine[0].rem_euclid(128);
        let fy = fine[1].rem_euclid(128);
        let lift = i32::from(
            self.floor_lifts
                .get(&(plane.get(), x, y))
                .copied()
                .unwrap_or(0),
        );
        if let Some(height) = terrain::floor_height(self, floor_plane, x, y, fx, fy)? {
            return Ok(height - lift);
        }
        let a = (-self.elevation(floor_plane, x, y)? * (128 - fx)
            - self.elevation(floor_plane, x + 1, y)? * fx)
            >> 7;
        let b = (-self.elevation(floor_plane, x, y + 1)? * (128 - fx)
            - self.elevation(floor_plane, x + 1, y + 1)? * fx)
            >> 7;
        Ok(((a * (128 - fy) + b * fy) >> 7) - lift)
    }
    /// Height an actor of `footprint` fine units stands at (`classKC.method6499`): the highest
    /// floor among the centre, the four corners of the footprint square and every tile corner
    /// inside it, so a foot on the uphill side of a slope is never buried. A zero footprint (forced
    /// movement) is the centre alone.
    /// # Errors
    /// Rejects a centre outside the retained terrain height border; samples that fall outside
    /// it are skipped.
    pub fn footprint_height(&self, plane: Plane, center: [i32; 2], footprint: i32) -> Result<i32> {
        let mut highest = self.ground_height(plane, center)?;
        if footprint == 0 {
            return Ok(highest);
        }
        let half = footprint / 2;
        let [x, y] = center;
        let mut sample = |point: [i32; 2]| {
            if let Ok(height) = self.ground_height(plane, point) {
                highest = highest.min(height);
            }
        };
        for tile_x in ((x - half) >> 7) + 1..=(x + half) >> 7 {
            for tile_y in ((y - half) >> 7) + 1..=(y + half) >> 7 {
                sample([tile_x << 7, tile_y << 7]);
            }
        }
        for (dx, dy) in [(-half, -half), (-half, half), (half, -half), (half, half)] {
            sample([x + dx, y + dy]);
        }
        Ok(highest)
    }
    pub(crate) fn elevation(&self, plane: Plane, x: i32, y: i32) -> Result<i32> {
        self.tile(plane, x, y)
            .map(|t| t.elevation)
            .ok_or_else(|| SceneError(format!("missing terrain height at {x},{y},{}", plane.get())))
    }
}

#[cfg(test)]
mod tests;

mod seams;
