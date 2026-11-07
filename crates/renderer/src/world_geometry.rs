//! Reusable static/dynamic geometry buffers; updating poses never uploads static data.
use crate::RenderError;
use glam::Vec3;
use rustclient_model::ModelDepthPolicy;
use rustclient_scene::{ModelView, SceneGeometry, SceneVertex};
use wgpu::util::DeviceExt;
mod compact;
mod metadata;
mod prepared;
mod scenery;
mod sort;
#[cfg(not(target_arch = "wasm32"))]
mod zone_diagnostic;
mod zone_upload;
pub(crate) use scenery::{PreparedScenery, SceneryGpu};
#[cfg(not(target_arch = "wasm32"))]
pub use zone_diagnostic::{ZoneResidencySample, verify_location_residency};
pub use zone_upload::PreparedLocationUpdate;
mod validation;
pub(crate) use prepared::PreparedGeometry;
use validation::validate;
#[derive(Clone, Copy)]
struct TransparentTriangle {
    center: Vec3,
    indices: [u32; 3],
    ordinal: usize,
    priority: u8,
    location_order: Option<rustclient_scene::LocationOrder>,
    alpha: bool,
    points: [[i16; 3]; 3],
}
#[derive(Clone)]
struct AlphaModel {
    center: Vec3,
    faces: Vec<TransparentTriangle>,
    depth_policy: ModelDepthPolicy,
    opaque_span: Option<core::ops::Range<usize>>,
    source: Option<std::sync::Arc<rustclient_scene::ModelSortSource>>,
    /// Replaced by a scenery patch: contributes no faces to either pass.
    hidden: bool,
}
#[derive(Clone)]
struct OpaqueBatch {
    indices: core::ops::Range<u32>,
    policy: ModelDepthPolicy,
}
/// Consecutive opaque triangles of one source model; the unit a scenery patch can hide.
#[derive(Clone)]
struct OpaqueRun {
    indices: core::ops::Range<u32>,
    policy: ModelDepthPolicy,
    group: Option<usize>,
}
#[derive(Default, Clone)]
struct GeometryData {
    opaque_count: u32,
    transparent_count: u32,
    models: Vec<AlphaModel>,
    model_order: Vec<usize>,
    model_groups: Vec<Option<usize>>,
    location_orders: Vec<Option<rustclient_scene::LocationOrder>>,
    face_depths: Vec<super::face_priority::FaceDepth>,
    face_order: Vec<usize>,
    sorted: Vec<TransparentTriangle>,
    order: Vec<u32>,
    opaque_order: Vec<u32>,
    opaque_batches: Vec<OpaqueBatch>,
    opaque_runs: Vec<OpaqueRun>,
    /// Static geometry only: the effective location each entry of the model spans came from.
    model_sources: Vec<u32>,
    /// Sorted baseline location indices currently hidden by a scenery patch.
    hidden: Vec<u32>,
    /// Source-to-packed vertex indices; only needed while `metadata` builds the draw order, so
    /// it is released afterwards and `immutable` remembers that the geometry was packed.
    vertex_remap: Vec<u32>,
    immutable: bool,
    expanded_vertex_bytes: u64,
    last_sort: Option<ModelView>,
    ordering_runs: u64,
    ordering_reuses: u64,
}
pub(crate) struct GeometryGpu {
    vertices: wgpu::Buffer,
    opaque: wgpu::Buffer,
    transparent: wgpu::Buffer,
    capacities: [u64; 3],
    data: GeometryData,
}
impl GeometryGpu {
    pub fn new(device: &wgpu::Device, geometry: &SceneGeometry) -> Result<Self, RenderError> {
        PreparedGeometry::new(geometry, false)?.upload(device, geometry)
    }
    pub fn update(
        &mut self,
        device: &wgpu::Device,
        queue: &wgpu::Queue,
        geometry: &SceneGeometry,
    ) -> Result<(), RenderError> {
        if self.data.immutable {
            return Err(RenderError::World(
                "immutable packed geometry cannot receive dynamic updates",
            ));
        }
        self.data.expanded_vertex_bytes =
            (geometry.vertices.len() * size_of::<SceneVertex>()) as u64;
        validate(geometry, device.limits().max_buffer_size)?;
        let data: [&[u8]; 3] = [
            bytemuck::cast_slice(&geometry.vertices),
            bytemuck::cast_slice(&geometry.opaque),
            bytemuck::cast_slice(&geometry.transparent),
        ];
        self.write(device, queue, data)?;
        self.data.metadata(geometry)
    }
    fn write(
        &mut self,
        device: &wgpu::Device,
        queue: &wgpu::Queue,
        data: [&[u8]; 3],
    ) -> Result<(), RenderError> {
        for (i, buffer) in [&mut self.vertices, &mut self.opaque, &mut self.transparent]
            .into_iter()
            .enumerate()
        {
            let required = data[i].len() as u64;
            if required > device.limits().max_buffer_size {
                return Err(RenderError::World("dynamic geometry exceeds device limits"));
            }
            if required > self.capacities[i] {
                self.capacities[i] = required
                    .next_power_of_two()
                    .min(device.limits().max_buffer_size);
                *buffer = device.create_buffer(&wgpu::BufferDescriptor {
                    label: Some("grown dynamic scenery buffer"),
                    size: self.capacities[i],
                    usage: if i == 0 {
                        wgpu::BufferUsages::VERTEX | wgpu::BufferUsages::COPY_DST
                    } else {
                        wgpu::BufferUsages::INDEX | wgpu::BufferUsages::COPY_DST
                    },
                    mapped_at_creation: false,
                });
            }
            if required != 0 {
                queue.write_buffer(buffer, 0, data[i]);
            }
        }
        Ok(())
    }
    pub fn stats(&self) -> super::WorldGeometryStats {
        super::WorldGeometryStats {
            gpu_buffer_bytes: self.capacities.iter().sum(),
            expanded_vertex_bytes: self.data.expanded_vertex_bytes,
            resident_vertex_bytes: self.capacities[0],
            cpu_remap_bytes: (self.data.vertex_remap.capacity() * size_of::<u32>()) as u64,
            ordering_runs: self.data.ordering_runs,
            ordering_reuses: self.data.ordering_reuses,
            ..super::WorldGeometryStats::default()
        }
    }
    pub fn draw_opaque(
        &self,
        pass: &mut wgpu::RenderPass<'_>,
        pipelines: &super::world_pipeline::WorldPipelines,
    ) {
        if self.data.opaque_count != 0 {
            pass.set_vertex_buffer(0, self.vertices.slice(..));
            pass.set_index_buffer(self.opaque.slice(..), wgpu::IndexFormat::Uint32);
            for batch in &self.data.opaque_batches {
                match batch.policy {
                    ModelDepthPolicy::DepthWrite => {
                        pass.set_pipeline(&pipelines.opaque);
                        pass.draw_indexed(batch.indices.clone(), 0, 0..1);
                    }
                    ModelDepthPolicy::SortedDeferredWrite => {
                        pass.set_pipeline(&pipelines.deferred_color);
                        pass.draw_indexed(batch.indices.clone(), 0, 0..1);
                        pass.set_pipeline(&pipelines.deferred_depth);
                        pass.draw_indexed(batch.indices.clone(), 0, 0..1);
                    }
                }
            }
        }
    }
    /// Draw one opaque run, skipping buffer and pipeline binds that `bound` says are current.
    /// Consecutive runs of one zone share buffers, and most runs share the opaque pipeline.
    pub fn draw_opaque_range(
        &self,
        pass: &mut wgpu::RenderPass<'_>,
        pipelines: &super::world_pipeline::WorldPipelines,
        indices: core::ops::Range<u32>,
        policy: ModelDepthPolicy,
        bound: &mut OpaqueBindings,
    ) {
        let buffers = std::ptr::from_ref(self) as usize;
        if bound.buffers != Some(buffers) {
            pass.set_vertex_buffer(0, self.vertices.slice(..));
            pass.set_index_buffer(self.opaque.slice(..), wgpu::IndexFormat::Uint32);
            bound.buffers = Some(buffers);
        }
        let mut pipeline = |pass: &mut wgpu::RenderPass<'_>, which: OpaquePipeline| {
            if bound.pipeline != Some(which) {
                pass.set_pipeline(match which {
                    OpaquePipeline::Opaque => &pipelines.opaque,
                    OpaquePipeline::DeferredColor => &pipelines.deferred_color,
                    OpaquePipeline::DeferredDepth => &pipelines.deferred_depth,
                });
                bound.pipeline = Some(which);
            }
        };
        match policy {
            ModelDepthPolicy::DepthWrite => {
                pipeline(pass, OpaquePipeline::Opaque);
                pass.draw_indexed(indices, 0, 0..1);
            }
            ModelDepthPolicy::SortedDeferredWrite => {
                pipeline(pass, OpaquePipeline::DeferredColor);
                pass.draw_indexed(indices.clone(), 0, 0..1);
                pipeline(pass, OpaquePipeline::DeferredDepth);
                pass.draw_indexed(indices, 0, 0..1);
            }
        }
    }
    /// Hide the static models emitted for the baseline locations `sources` (sorted), or show
    /// every model again with an empty list. Buffers are untouched: hidden models leave the
    /// opaque draw ranges and the alpha ordering. Returns whether anything changed.
    pub fn set_hidden(&mut self, sources: &[u32]) -> bool {
        self.data.set_hidden(sources)
    }
    pub fn alpha_depth(&self, index: u32, eye: Vec3) -> Option<f32> {
        let triangle = self.data.sorted.get(usize::try_from(index).ok()?)?;
        Some(triangle.center.distance_squared(eye))
    }
    pub fn draw_alpha(&self, pass: &mut wgpu::RenderPass<'_>, indices: core::ops::Range<u32>) {
        pass.set_vertex_buffer(0, self.vertices.slice(..));
        pass.set_index_buffer(self.transparent.slice(..), wgpu::IndexFormat::Uint32);
        pass.draw_indexed(indices, 0, 0..1);
    }
}
fn point(vertex: &SceneVertex) -> Vec3 {
    Vec3::new(
        f32::from(vertex.position[0]),
        f32::from(vertex.position[1]),
        f32::from(vertex.position[2]),
    )
}

/// Which opaque pipeline a render pass has bound.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum OpaquePipeline {
    Opaque,
    DeferredColor,
    DeferredDepth,
}

/// The buffers and pipeline the opaque pass last bound, so redundant binds are skipped. A new
/// value must be used for every render pass; nothing outside the opaque runs may bind in between.
#[derive(Debug, Clone, Copy, Default)]
pub struct OpaqueBindings {
    buffers: Option<usize>,
    pipeline: Option<OpaquePipeline>,
}

#[cfg(test)]
mod hidden;
#[cfg(test)]
mod trace;

#[cfg(target_arch = "wasm32")]
mod retirement;
