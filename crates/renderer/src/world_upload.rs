//! Portable CPU preparation. The public payload owns the exact mesh it prepares.
use crate::{
    RenderError,
    world_geometry::{PreparedGeometry, PreparedScenery},
};
use rustclient_scene::WorldMesh;

/// How a publication relates to the current live scene, independently of repeated cache IDs.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum WorldUpdate {
    Scene,
    Scenery,
}
/// CPU-only, device-independent upload work for one immutable mesh.
/// Fields stay private so prepared data cannot be attached to a different mesh.
pub struct PreparedWorldMesh {
    mesh: WorldMesh,
    upload: WorldUpload,
}
impl PreparedWorldMesh {
    /// Validate and prepare lossless packing, draw metadata and texture identity.
    /// # Errors
    /// Rejects invalid geometry, material indices, spans and preparation budgets.
    pub fn new(mesh: WorldMesh) -> Result<Self, RenderError> {
        let upload = WorldUpload::prepare(&mesh)?;
        Ok(Self { mesh, upload })
    }
    #[must_use]
    pub fn mesh(&self) -> &WorldMesh {
        &self.mesh
    }
    /// Recover the source CPU projection when a renderer is unavailable.
    #[must_use]
    pub fn into_mesh(self) -> WorldMesh {
        self.mesh
    }
    pub(crate) fn into_parts(self) -> (WorldMesh, WorldUpload) {
        (self.mesh, self.upload)
    }
}
pub(crate) struct WorldUpload {
    pub geometry: PreparedScenery,
    pub dynamic: PreparedGeometry,
    pub textures_fingerprint: u64,
}
impl WorldUpload {
    pub fn prepare(mesh: &WorldMesh) -> Result<Self, RenderError> {
        Ok(Self {
            geometry: PreparedScenery::new(&mesh.geometry)?,
            dynamic: PreparedGeometry::new(&mesh.dynamic, false)?,
            textures_fingerprint: crate::world_textures::fingerprint(&mesh.textures),
        })
    }
    pub fn validate_for(&self, mesh: &WorldMesh, limits: &wgpu::Limits) -> Result<(), RenderError> {
        self.geometry
            .validate_for(&mesh.geometry, limits.max_buffer_size)?;
        self.dynamic
            .validate_for(&mesh.dynamic, limits.max_buffer_size)?;
        crate::world_textures::validate(mesh, limits)
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn preparation_owns_source_and_rejects_invalid_static_or_dynamic_indices() {
        fn send_sync<T: Send + Sync>() {}
        send_sync::<PreparedWorldMesh>();
        let mesh = WorldMesh {
            asset: rustclient_model::SceneAssetId::new([1; 32]),
            geometry: rustclient_scene::SceneGeometry::default(),
            dynamic: rustclient_scene::SceneGeometry::default(),
            focus: [1., 2., 3.],
            stats: rustclient_scene::SceneStats::default(),
            textures: std::sync::Arc::default(),
            roofs: std::sync::Arc::default(),
        };
        let prepared = PreparedWorldMesh::new(mesh).unwrap();
        assert_eq!(
            prepared.mesh().focus.map(f32::to_bits),
            [1_f32, 2., 3.].map(f32::to_bits)
        );
        let mut mesh = prepared.into_mesh();
        mesh.geometry.opaque = vec![99; 3];
        assert!(WorldUpload::prepare(&mesh).is_err());
        mesh.geometry.opaque.clear();
        mesh.dynamic.transparent = vec![99; 3];
        assert!(PreparedWorldMesh::new(mesh).is_err());
    }
}
