//! Reusable static/dynamic geometry buffers; updating poses never uploads static data.
use crate::RenderError;
use glam::Vec3;
use rustclient_model::ModelDepthPolicy;
use rustclient_scene::{ModelView, SceneGeometry, SceneVertex};
use wgpu::util::DeviceExt;
mod compact;
mod sort;
mod validation;
use validation::validate;
#[derive(Clone, Copy)]
struct TransparentTriangle {
    center: Vec3,
    indices: [u32; 3],
    ordinal: usize,
    priority: u8,
    alpha: bool,
    points: [[i16; 3]; 3],
}
struct AlphaModel {
    center: Vec3,
    faces: Vec<TransparentTriangle>,
    depth_policy: ModelDepthPolicy,
    opaque_span: Option<core::ops::Range<usize>>,
    source: Option<std::sync::Arc<rustclient_scene::ModelSortSource>>,
}
struct OpaqueBatch {
    indices: core::ops::Range<u32>,
    policy: ModelDepthPolicy,
}
pub(crate) struct GeometryGpu {
    vertices: wgpu::Buffer,
    opaque: wgpu::Buffer,
    transparent: wgpu::Buffer,
    capacities: [u64; 3],
    opaque_count: u32,
    transparent_count: u32,
    models: Vec<AlphaModel>,
    model_order: Vec<usize>,
    model_groups: Vec<Option<usize>>,
    face_depths: Vec<super::face_priority::FaceDepth>,
    face_order: Vec<usize>,
    sorted: Vec<TransparentTriangle>,
    order: Vec<u32>,
    opaque_order: Vec<u32>,
    opaque_batches: Vec<OpaqueBatch>,
    vertex_remap: Vec<u32>,
    expanded_vertex_bytes: u64,
    last_sort: Option<ModelView>,
    ordering_runs: u64,
    ordering_reuses: u64,
}
impl GeometryGpu {
    pub fn new(device: &wgpu::Device, geometry: &SceneGeometry) -> Result<Self, RenderError> {
        validate(geometry, device.limits().max_buffer_size)?;
        Self::create(device, geometry, None)
    }
    pub fn new_static(
        device: &wgpu::Device,
        geometry: &SceneGeometry,
    ) -> Result<Self, RenderError> {
        validate(geometry, device.limits().max_buffer_size)?;
        Self::create(device, geometry, compact::PackedGeometry::build(geometry)?)
    }
    fn create(
        device: &wgpu::Device,
        geometry: &SceneGeometry,
        packed: Option<compact::PackedGeometry>,
    ) -> Result<Self, RenderError> {
        let (vertices, opaque, transparent) = packed.as_ref().map_or(
            (
                geometry.vertices.as_slice(),
                geometry.opaque.as_slice(),
                geometry.transparent.as_slice(),
            ),
            |packed| {
                (
                    packed.vertices.as_slice(),
                    packed.opaque.as_slice(),
                    packed.transparent.as_slice(),
                )
            },
        );
        let data: [&[u8]; 3] = [
            bytemuck::cast_slice(vertices),
            bytemuck::cast_slice(opaque),
            bytemuck::cast_slice(transparent),
        ];
        let buffers = std::array::from_fn::<_, 3, _>(|i| {
            device.create_buffer_init(&wgpu::util::BufferInitDescriptor {
                label: Some("resident scenery geometry"),
                contents: if data[i].is_empty() { &[0; 4] } else { data[i] },
                usage: if i == 0 {
                    wgpu::BufferUsages::VERTEX | wgpu::BufferUsages::COPY_DST
                } else {
                    wgpu::BufferUsages::INDEX | wgpu::BufferUsages::COPY_DST
                },
            })
        });
        let [vertices, opaque, transparent] = buffers;
        let mut this = Self {
            vertices,
            opaque,
            transparent,
            capacities: data.map(|d| d.len().max(4) as u64),
            opaque_count: 0,
            transparent_count: 0,
            models: Vec::new(),
            model_order: Vec::new(),
            model_groups: Vec::new(),
            face_depths: Vec::new(),
            face_order: Vec::new(),
            sorted: Vec::new(),
            order: Vec::new(),
            opaque_order: Vec::new(),
            opaque_batches: Vec::new(),
            vertex_remap: packed.map_or_else(Vec::new, |packed| packed.remap),
            expanded_vertex_bytes: (geometry.vertices.len() * size_of::<SceneVertex>()) as u64,
            last_sort: None,
            ordering_runs: 0,
            ordering_reuses: 0,
        };
        this.metadata(geometry)?;
        Ok(this)
    }
    #[expect(
        clippy::cast_precision_loss,
        reason = "bounded model face count used for centroid averaging"
    )]
    #[expect(
        clippy::too_many_lines,
        reason = "one ordered derivation of model spans, face groups and opaque pass ranges"
    )]
    fn metadata(&mut self, geometry: &SceneGeometry) -> Result<(), RenderError> {
        self.last_sort = None;
        self.opaque_count = u32::try_from(geometry.opaque.len())
            .map_err(|_| RenderError::World("opaque index count"))?;
        self.transparent_count = u32::try_from(geometry.transparent.len())
            .map_err(|_| RenderError::World("alpha index count"))?;
        for model in &mut self.models {
            model.faces.clear();
            model.opaque_span = None;
        }
        self.model_groups.clear();
        self.model_groups.resize(geometry.models.len(), None);
        let mut used = 0;

        for (alpha, indices) in [(true, &geometry.transparent), (false, &geometry.opaque)] {
            for (offset, triangle) in indices.as_chunks::<3>().0.iter().enumerate() {
                let first = triangle[0] as usize;
                let group = geometry
                    .models
                    .partition_point(|model| model.vertices.end <= first);
                let group = geometry
                    .models
                    .get(group)
                    .filter(|model| model.vertices.contains(&first))
                    .map(|_| group);
                let policy = group.map_or(ModelDepthPolicy::DepthWrite, |group| {
                    geometry.models[group].depth_policy
                });
                if let Some(group) = group
                    && triangle
                        .iter()
                        .any(|&index| !geometry.models[group].vertices.contains(&(index as usize)))
                {
                    return Err(RenderError::World("triangle crosses model spans"));
                }
                let model = if let Some(group) = group {
                    if let Some(model) = self.model_groups[group] {
                        model
                    } else if alpha || policy == ModelDepthPolicy::SortedDeferredWrite {
                        let model = self.reserve_model(used, policy);
                        self.models[model]
                            .source
                            .clone_from(&geometry.models[group].sort_source);
                        used += 1;
                        self.model_groups[group] = Some(model);
                        model
                    } else {
                        continue;
                    }
                } else if alpha {
                    let model = self.reserve_model(used, policy);
                    used += 1;
                    model
                } else {
                    continue;
                };
                if !alpha
                    && (policy == ModelDepthPolicy::SortedDeferredWrite
                        || self.models[model].source.is_some())
                {
                    let span = &mut self.models[model].opaque_span;
                    let start = offset * 3;
                    if let Some(span) = span {
                        if span.end != start {
                            return Err(RenderError::World(
                                "deferred model indices are not contiguous",
                            ));
                        }
                        span.end += 3;
                    } else {
                        *span = Some(start..start + 3);
                    }
                }
                let mut center = Vec3::ZERO;
                for &index in triangle {
                    let vertex = geometry
                        .vertices
                        .get(index as usize)
                        .ok_or(RenderError::World("alpha vertex index"))?;
                    center += point(vertex);
                }
                let priority = u8::try_from(geometry.vertices[first].position[3])
                    .ok()
                    .filter(|p| *p <= 11)
                    .ok_or(RenderError::World("face priority"))?;
                self.models[model].faces.push(TransparentTriangle {
                    center: center / 3.,
                    indices: [triangle[0], triangle[1], triangle[2]].map(|index| {
                        self.vertex_remap
                            .get(index as usize)
                            .copied()
                            .unwrap_or(index)
                    }),
                    ordinal: first / 3,
                    priority,
                    alpha,
                    points: triangle.map(|index| {
                        let point = geometry.vertices[index as usize].position;
                        [point[0], point[1], point[2]]
                    }),
                });
            }
        }
        for model in self.models.iter_mut().take(used) {
            // Emission order, not opacity-pass order, resolves equal face depths.
            model.faces.sort_unstable_by_key(|face| face.ordinal);
            model.center =
                model.faces.iter().map(|face| face.center).sum::<Vec3>() / model.faces.len() as f32;
        }
        self.opaque_order.clear();
        if self
            .models
            .iter()
            .take(used)
            .any(|model| model.opaque_span.is_some())
        {
            self.opaque_order
                .extend(geometry.opaque.iter().map(|&index| {
                    self.vertex_remap
                        .get(index as usize)
                        .copied()
                        .unwrap_or(index)
                }));
        }
        self.opaque_batches.clear();
        for (index, triangle) in geometry.opaque.as_chunks::<3>().0.iter().enumerate() {
            let first = triangle[0] as usize;
            let group = geometry
                .models
                .partition_point(|model| model.vertices.end <= first);
            let policy = geometry
                .models
                .get(group)
                .filter(|model| model.vertices.contains(&first))
                .map_or(ModelDepthPolicy::DepthWrite, |model| model.depth_policy);
            let start =
                u32::try_from(index * 3).map_err(|_| RenderError::World("opaque batch index"))?;
            if let Some(batch) = self.opaque_batches.last_mut()
                && batch.policy == policy
                && batch.indices.end == start
            {
                batch.indices.end += 3;
            } else {
                self.opaque_batches.push(OpaqueBatch {
                    indices: start..start + 3,
                    policy,
                });
            }
        }
        Ok(())
    }
    fn reserve_model(&mut self, index: usize, policy: ModelDepthPolicy) -> usize {
        if index == self.models.len() {
            self.models.push(AlphaModel {
                center: Vec3::ZERO,
                faces: Vec::new(),
                depth_policy: policy,
                opaque_span: None,
                source: None,
            });
        }
        self.models[index].depth_policy = policy;
        self.models[index].source = None;
        index
    }
    pub fn update(
        &mut self,
        device: &wgpu::Device,
        queue: &wgpu::Queue,
        geometry: &SceneGeometry,
    ) -> Result<(), RenderError> {
        if !self.vertex_remap.is_empty() {
            return Err(RenderError::World(
                "immutable packed geometry cannot receive dynamic updates",
            ));
        }
        self.expanded_vertex_bytes = (geometry.vertices.len() * size_of::<SceneVertex>()) as u64;
        validate(geometry, device.limits().max_buffer_size)?;
        let data: [&[u8]; 3] = [
            bytemuck::cast_slice(&geometry.vertices),
            bytemuck::cast_slice(&geometry.opaque),
            bytemuck::cast_slice(&geometry.transparent),
        ];
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
        self.metadata(geometry)
    }
    pub fn stats(&self) -> super::WorldGeometryStats {
        super::WorldGeometryStats {
            gpu_buffer_bytes: self.capacities.iter().sum(),
            expanded_vertex_bytes: self.expanded_vertex_bytes,
            resident_vertex_bytes: self.capacities[0],
            cpu_remap_bytes: (self.vertex_remap.capacity() * size_of::<u32>()) as u64,
            ordering_runs: self.ordering_runs,
            ordering_reuses: self.ordering_reuses,
        }
    }
    pub fn draw_opaque(
        &self,
        pass: &mut wgpu::RenderPass<'_>,
        pipelines: &super::world_pipeline::WorldPipelines,
    ) {
        if self.opaque_count != 0 {
            pass.set_vertex_buffer(0, self.vertices.slice(..));
            pass.set_index_buffer(self.opaque.slice(..), wgpu::IndexFormat::Uint32);
            for batch in &self.opaque_batches {
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
    pub fn alpha_depth(&self, index: u32, eye: Vec3) -> Option<f32> {
        let triangle = self.sorted.get(usize::try_from(index).ok()?)?;
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

#[cfg(test)]
mod trace;
