use crate::{Result, SceneAsset, SceneError};
use rustclient_model::{Plane, SceneAssetId, TextureId, WorldTile};
/// Reference GPU vertex: signed fine units, alpha/bias/HSL, texture+1 and signed 8.8 UVs.
#[repr(C)]
#[derive(Debug, Clone, Copy, PartialEq, Eq, bytemuck::Pod, bytemuck::Zeroable)]
pub struct SceneVertex {
    /// XYZ fine units; W retains the authored face priority for CPU ordering.
    pub position: [i16; 4],
    pub abhsl: u32,
    /// Texture+1, signed 8.8 U and V, and the render tag: the tile render level in the low two
    /// bits and the roof slot above them (see [`SceneVertex::render_level`]).
    pub texture_uv: [u16; 4],
    /// One owning tile for whole-model/terrain admission, shared with picking.
    pub draw_tile: crate::DrawTile,
}
impl SceneVertex {
    /// Lowest "top level" at which this vertex is drawn: tiles, locations and actors
    /// are culled when the frame's top level (see `roofs::top_level`) is below it.
    #[must_use]
    pub fn render_level(&self) -> u8 {
        u8::try_from(self.texture_uv[3] & 3).unwrap_or(u8::MAX)
    }

    /// The roof this vertex belongs to (`roofs::RoofVolumes`), 0 when it is not part of one.
    #[must_use]
    pub fn roof_slot(&self) -> u16 {
        self.texture_uv[3] >> 2
    }

    /// Packed render tag, see [`SceneVertex::texture_uv`].
    #[must_use]
    pub fn render_tag(&self) -> u16 {
        self.texture_uv[3]
    }
}
#[derive(Debug, Clone, Copy, Default)]
pub struct SceneStats {
    pub terrain_triangles: u64,
    pub model_triangles: u64,
    pub locations: u64,
    pub morphs_deferred: u64,
    pub animated_static: u64,
    pub no_model: u64,
    pub texture_average_faces: u64,
    pub hidden_faces: u64,
}
/// Disposable extraction from one immutable asset and authoritative placement.
#[derive(Default, Clone)]
pub struct SceneGeometry {
    pub vertices: Vec<SceneVertex>,
    pub opaque: Vec<u32>,
    pub transparent: Vec<u32>,
    /// Non-overlapping vertex spans, one per emitted model, used for face ordering.
    pub models: Vec<SceneModel>,
    /// Ordered vertex spans emitted for the same posed models submitted to the GPU.
    pub picks: Vec<PickRange>,
    /// Disposable overhead anchors extracted from the posed actor geometry.
    pub actor_labels: Vec<ActorLabelAnchor>,
}
/// One whole emitted model, retaining its source raster policy alongside its existing vertex span.
#[derive(Debug, Clone)]
pub struct SceneModel {
    pub vertices: std::ops::Range<usize>,
    pub depth_policy: rustclient_model::ModelDepthPolicy,
    pub sort_source: Option<std::sync::Arc<crate::ModelSortSource>>,
}
#[derive(Debug, Clone, Copy)]
pub enum ActorLabelId {
    Player(
        rustclient_model::PlayerIndex,
        rustclient_model::PlayerInstanceId,
    ),
    Npc(rustclient_model::NpcIndex, rustclient_model::NpcInstanceId),
}
#[derive(Debug, Clone)]
pub struct ActorLabelAnchor {
    pub actor: ActorLabelId,
    pub position: [i32; 3],
    pub level: u8,
    /// The actor's posed model vertices in [`SceneGeometry::vertices`].
    pub vertices: std::ops::Range<usize>,
}
#[derive(Debug, Clone)]
pub struct PickRange {
    pub vertices: std::ops::Range<usize>,
    pub target: rustclient_model::WorldTarget,
    pub bounds: Option<crate::PickBounds>,
    pub precision:crate::PickPrecision,
}
pub struct WorldMesh {
    pub asset: SceneAssetId,
    pub geometry: SceneGeometry,
    pub dynamic: SceneGeometry,
    pub focus: [f32; 3],
    pub stats: SceneStats,
    pub textures: std::collections::BTreeMap<TextureId, rustclient_cache::world::TextureAsset>,
    /// The roof regions this mesh's vertices are tagged with; the line-of-sight cut and the
    /// per-roof hiding must read the same regions.
    pub roofs: std::sync::Arc<crate::RoofVolumes>,
}
impl SceneGeometry {
    /// Empty every list but keep allocations for per-cycle rebuilds.
    pub fn clear(&mut self) {
        self.vertices.clear();
        self.opaque.clear();
        self.transparent.clear();
        self.picks.clear();
        self.actor_labels.clear();
        self.models.clear();
    }
    pub(crate) fn model_triangle(
        &mut self,
        points: [[i32; 3]; 3],
        colors: [i32; 3],
        uv: [[f32; 2]; 3],
        face: rustclient_cache::world::ModelFace,
        tag: u16,
    ) -> Result<()> {
        if face.priority > 11 {
            return Err(SceneError("face priority outside 0..11".into()));
        }
        let first = self.vertices.len();
        let alpha = if face.alpha == -2 {
            0
        } else {
            face.alpha.cast_unsigned()
        };
        self.triangle(points, colors, face.texture, uv, alpha, face.bias, tag)?;
        for vertex in &mut self.vertices[first..] {
            vertex.position[3] = i16::from(face.priority);
        }
        Ok(())
    }
    #[expect(
        clippy::cast_possible_truncation,
        reason = "UVs use reference truncated and clamped signed 8.8"
    )]
    #[expect(
        clippy::too_many_arguments,
        reason = "one packed reference vertex record is assembled from its independent fields"
    )]
    pub(crate) fn triangle(
        &mut self,
        points: [[i32; 3]; 3],
        colors: [i32; 3],
        texture: Option<TextureId>,
        uv: [[f32; 2]; 3],
        alpha: u8,
        bias: i8,
        tag: u16,
    ) -> Result<bool> {
        if self.vertices.len() + 3 > 3_000_000 {
            return Err(SceneError("scene vertex allocation limit exceeded".into()));
        }
        let first = u32::try_from(self.vertices.len())
            .map_err(|_| SceneError("vertex index overflow".into()))?;
        let texture = texture.map_or(Ok(0), |t| {
            u16::try_from(t.get() + 1).map_err(|_| SceneError("texture id overflow".into()))
        })?;
        let mut vertices = [SceneVertex {
            position: [0; 4],
            abhsl: 0,
            texture_uv: [0; 4],
            draw_tile: crate::DrawTile::default(),
        }; 3];
        for i in 0..3 {
            let p = points[i];
            vertices[i].position = [
                i16::try_from(p[0])
                    .map_err(|_| SceneError("x exceeds packed scene range".into()))?,
                i16::try_from(p[1])
                    .map_err(|_| SceneError("height exceeds packed scene range".into()))?,
                i16::try_from(p[2])
                    .map_err(|_| SceneError("z exceeds packed scene range".into()))?,
                0,
            ];
            vertices[i].abhsl = (u32::from(alpha) << 24)
                | (u32::from(bias.cast_unsigned()) << 16)
                | (colors[i].cast_unsigned() & 65535);
            let [u, v] = uv[i].map(|v| ((v * 256.) as i32).clamp(-32768, 32767) as i16);
            vertices[i].texture_uv = [texture, u.cast_unsigned(), v.cast_unsigned(), tag];
        }
        let dest = if alpha != 0 {
            &mut self.transparent
        } else {
            &mut self.opaque
        };
        dest.extend_from_slice(&[first, first + 1, first + 2]);
        self.vertices.extend_from_slice(&vertices);
        Ok(true)
    }
}
/// # Errors
/// Rejects mismatched identities/placement and bounded geometry extraction failures.
pub fn build_mesh(
    asset: &SceneAsset,
    descriptor: rustclient_model::SceneDescriptor,
    spawn: WorldTile,
) -> Result<WorldMesh> {
    build_mesh_with_inputs(asset, descriptor, spawn, &crate::SceneryInputs::default())
}
/// Extract scenery from an immutable snapshot of committed variable/location/time inputs.
/// # Errors
/// Rejects missing dependencies, invalid morphs and invalid projection bounds.
#[expect(
    clippy::cast_precision_loss,
    reason = "bounded scene focus converted to camera composition units"
)]
pub fn build_mesh_with_inputs(
    asset: &SceneAsset,
    descriptor: rustclient_model::SceneDescriptor,
    spawn: WorldTile,
    inputs: &crate::SceneryInputs,
) -> Result<WorldMesh> {
    if asset.descriptor().with_plane(descriptor.origin().plane) != descriptor
        || !descriptor.contains(spawn)
    {
        return Err(SceneError(
            "mesh extraction does not match authoritative scene reference".into(),
        ));
    }
    let origin = descriptor.origin();
    let mut mesh = WorldMesh {
        asset: descriptor.id(),
        geometry: SceneGeometry::default(),
        dynamic: SceneGeometry::default(),
        focus: [
            (spawn.x - origin.x) as f32 + 0.5,
            asset.elevation(spawn.plane, spawn.x, spawn.y)? as f32 / 128.,
            (spawn.y - origin.y) as f32 + 0.5,
        ],
        stats: SceneStats::default(),
        textures: asset.assets.textures.clone(),
        roofs: asset.roofs_with(&inputs.roof_areas),
    };
    super::terrain::build(asset, &mut mesh, inputs)?;
    super::locations::build(asset, &mut mesh, inputs, false)?;
    let mut dynamic = empty_mesh(asset, mesh.focus, std::sync::Arc::clone(&mesh.roofs));
    super::locations::build(asset, &mut dynamic, inputs, true)?;
    mesh.stats.model_triangles += dynamic.stats.model_triangles;
    mesh.stats.locations += dynamic.stats.locations;
    mesh.stats.no_model += dynamic.stats.no_model;
    mesh.stats.animated_static += dynamic.stats.animated_static;
    mesh.stats.hidden_faces += dynamic.stats.hidden_faces;
    mesh.dynamic = dynamic.geometry;
    Ok(mesh)
}
/// The lowest frame "top level" at which a tile (and everything standing on it) is drawn.
///
/// The 240 client assigns each scene tile this level while loading (`classDI` tile pass): a
/// tile flagged `0x8` always draws (level 0); a tile above a bridge deck (`0x2` on plane 1)
/// drops one level, so the deck itself draws with the ground floor; every other tile draws at
/// its own plane. A tile is rendered when its level is at most the frame's top level.
pub(crate) fn render_level(asset: &SceneAsset, source: Plane, x: i32, y: i32) -> u8 {
    let forced = asset
        .tile(source, x, y)
        .is_some_and(|t| t.settings & 8 != 0);
    let bridged = asset
        .tile(Plane::new(1).expect("plane"), x, y)
        .is_some_and(|t| t.bridge());
    if forced {
        0
    } else if bridged {
        source.get().saturating_sub(1)
    } else {
        source.get()
    }
}

/// The packed vertex render tag of geometry standing on `source` at world tile `(x, y)`: the
/// render level of [`render_level`] in the low two bits and the covering roof slot above them.
pub(crate) fn render_tag(
    asset: &SceneAsset,
    roofs: &crate::RoofVolumes,
    source: Plane,
    x: i32,
    y: i32,
) -> u16 {
    let origin = asset.descriptor().origin();
    let slot = roofs.covering(source, x - origin.x, y - origin.y);
    u16::from(render_level(asset, source, x, y)) | (slot << 2)
}

fn empty_mesh(
    asset: &SceneAsset,
    focus: [f32; 3],
    roofs: std::sync::Arc<crate::RoofVolumes>,
) -> WorldMesh {
    WorldMesh {
        asset: asset.descriptor.id(),
        geometry: SceneGeometry::default(),
        dynamic: SceneGeometry::default(),
        focus,
        stats: SceneStats::default(),
        textures: std::collections::BTreeMap::default(),
        roofs,
    }
}
/// Extract only posed/morphing locations for the explicit committed cycle.
/// # Errors
/// Returns missing pose/model dependencies and extraction errors.
pub fn build_dynamic(asset: &SceneAsset, inputs: &crate::SceneryInputs) -> Result<SceneGeometry> {
    let mut mesh = empty_mesh(asset, [0.; 3], asset.roofs_with(&inputs.roof_areas));
    super::locations::build(asset, &mut mesh, inputs, true)?;
    Ok(mesh.geometry)
}
impl WorldMesh {
    #[must_use]
    pub fn vertex_count(&self) -> usize {
        self.geometry.vertices.len() + self.dynamic.vertices.len()
    }
}
