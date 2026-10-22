use crate::{Result, SceneAsset, SceneError};
use rustclient_model::{Plane, SceneAssetId, TextureId, WorldTile};
/// Reference GPU vertex: signed fine units, alpha/bias/HSL, texture+1 and signed 8.8 UVs.
#[repr(C)]
#[derive(Debug, Clone, Copy, PartialEq, Eq, bytemuck::Pod, bytemuck::Zeroable)]
pub struct SceneVertex {
    pub position: [i16; 4],
    pub abhsl: u32,
    pub texture_uv: [u16; 4],
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
}
pub struct WorldMesh {
    pub asset: SceneAssetId,
    pub geometry: SceneGeometry,
    pub dynamic: SceneGeometry,
    pub focus: [f32; 3],
    pub stats: SceneStats,
    pub textures: std::collections::BTreeMap<TextureId, rustclient_cache::world::TextureAsset>,
}
impl SceneGeometry {
    #[expect(
        clippy::cast_possible_truncation,
        reason = "UVs use reference truncated and clamped signed 8.8"
    )]
    pub(crate) fn triangle(
        &mut self,
        points: [[i32; 3]; 3],
        colors: [i32; 3],
        texture: Option<TextureId>,
        uv: [[f32; 2]; 3],
        alpha: u8,
        bias: i8,
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
            vertices[i].texture_uv = [texture, u.cast_unsigned(), v.cast_unsigned(), 0];
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
    if asset.descriptor() != descriptor || !descriptor.contains(spawn) {
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
    };
    super::terrain::build(asset, &mut mesh, spawn.plane, inputs)?;
    super::locations::build(asset, &mut mesh, spawn.plane, inputs, false)?;
    let mut dynamic = empty_mesh(asset, mesh.focus);
    super::locations::build(asset, &mut dynamic, spawn.plane, inputs, true)?;
    mesh.stats.model_triangles += dynamic.stats.model_triangles;
    mesh.stats.locations += dynamic.stats.locations;
    mesh.stats.no_model += dynamic.stats.no_model;
    mesh.stats.animated_static += dynamic.stats.animated_static;
    mesh.stats.hidden_faces += dynamic.stats.hidden_faces;
    mesh.dynamic = dynamic.geometry;
    Ok(mesh)
}
pub(crate) fn visible(asset: &SceneAsset, source: Plane, x: i32, y: i32, selected: Plane) -> bool {
    let forced = asset
        .tile(source, x, y)
        .is_some_and(|t| t.settings & 8 != 0);
    let bridged = asset
        .tile(Plane::new(1).expect("plane"), x, y)
        .is_some_and(|t| t.bridge());
    let minimum = if forced {
        0
    } else if bridged {
        source.get().saturating_sub(1)
    } else {
        source.get()
    };
    minimum <= selected.get()
}

fn empty_mesh(asset: &SceneAsset, focus: [f32; 3]) -> WorldMesh {
    WorldMesh {
        asset: asset.descriptor.id(),
        geometry: SceneGeometry::default(),
        dynamic: SceneGeometry::default(),
        focus,
        stats: SceneStats::default(),
        textures: std::collections::BTreeMap::default(),
    }
}
/// Extract only posed/morphing locations for the explicit committed cycle.
/// # Errors
/// Returns missing pose/model dependencies and extraction errors.
pub fn build_dynamic(
    asset: &SceneAsset,
    spawn: WorldTile,
    inputs: &crate::SceneryInputs,
) -> Result<SceneGeometry> {
    let mut mesh = empty_mesh(asset, [0.; 3]);
    super::locations::build(asset, &mut mesh, spawn.plane, inputs, true)?;
    Ok(mesh.geometry)
}
impl WorldMesh {
    #[must_use]
    pub fn vertex_count(&self) -> usize {
        self.geometry.vertices.len() + self.dynamic.vertices.len()
    }
}
