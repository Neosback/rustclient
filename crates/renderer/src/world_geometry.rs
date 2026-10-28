//! Reusable static/dynamic geometry buffers; updating poses never uploads static data.
use crate::RenderError;
use glam::Vec3;
use rustclient_scene::{SceneGeometry, SceneVertex};
use wgpu::util::DeviceExt;
#[derive(Clone, Copy)]
struct TransparentTriangle {
    center: Vec3,
    indices: [u32; 3],
    ordinal: usize,
    priority: u8,
    alpha: bool,
}
struct AlphaModel {
    center: Vec3,
    faces: Vec<TransparentTriangle>,
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
}
impl GeometryGpu {
    pub fn new(device: &wgpu::Device, geometry: &SceneGeometry) -> Result<Self, RenderError> {
        validate(geometry, device.limits().max_buffer_size)?;
        let data: [&[u8]; 3] = [
            bytemuck::cast_slice(&geometry.vertices),
            bytemuck::cast_slice(&geometry.opaque),
            bytemuck::cast_slice(&geometry.transparent),
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
        };
        this.metadata(geometry)?;
        Ok(this)
    }
    #[expect(
        clippy::cast_precision_loss,
        reason = "bounded model face count used for centroid averaging"
    )]
    fn metadata(&mut self, geometry: &SceneGeometry) -> Result<(), RenderError> {
        self.opaque_count = u32::try_from(geometry.opaque.len())
            .map_err(|_| RenderError::World("opaque index count"))?;
        self.transparent_count = u32::try_from(geometry.transparent.len())
            .map_err(|_| RenderError::World("alpha index count"))?;
        for model in &mut self.models {
            model.faces.clear();
        }
        self.model_groups.clear();
        self.model_groups.resize(geometry.models.len(), None);
        let mut used = 0;

        for (alpha, indices) in [(true, &geometry.transparent), (false, &geometry.opaque)] {
            for triangle in indices.as_chunks::<3>().0 {
                let first = triangle[0] as usize;
                let group = geometry.models.partition_point(|span| span.end <= first);
                let group = geometry
                    .models
                    .get(group)
                    .filter(|span| span.contains(&first))
                    .map(|_| group);
                let model = if let Some(group) = group {
                    if let Some(model) = self.model_groups[group] {
                        model
                    } else if alpha {
                        let model = self.reserve_model(used);
                        used += 1;
                        self.model_groups[group] = Some(model);
                        model
                    } else {
                        continue;
                    }
                } else if alpha {
                    let model = self.reserve_model(used);
                    used += 1;
                    model
                } else {
                    continue;
                };
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
                    indices: [triangle[0], triangle[1], triangle[2]],
                    ordinal: first / 3,
                    priority,
                    alpha,
                });
            }
        }
        for model in self.models.iter_mut().take(used) {
            // Emission order, not opacity-pass order, resolves equal face depths.
            model.faces.sort_unstable_by_key(|face| face.ordinal);
            model.center =
                model.faces.iter().map(|face| face.center).sum::<Vec3>() / model.faces.len() as f32;
        }
        Ok(())
    }
    fn reserve_model(&mut self, index: usize) -> usize {
        if index == self.models.len() {
            self.models.push(AlphaModel {
                center: Vec3::ZERO,
                faces: Vec::new(),
            });
        }
        index
    }
    pub fn update(
        &mut self,
        device: &wgpu::Device,
        queue: &wgpu::Queue,
        geometry: &SceneGeometry,
    ) -> Result<(), RenderError> {
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
    #[expect(
        clippy::cast_possible_truncation,
        reason = "reference face preparation truncates projected depth to integer buckets"
    )]
    pub fn sort(&mut self, queue: &wgpu::Queue, eye: Vec3, direction: Vec3) {
        self.model_order.clear();
        self.model_order.extend(
            self.models
                .iter()
                .enumerate()
                .filter(|(_, model)| !model.faces.is_empty())
                .map(|(index, _)| index),
        );
        self.model_order.sort_unstable_by(|&a, &b| {
            self.models[b]
                .center
                .distance_squared(eye)
                .total_cmp(&self.models[a].center.distance_squared(eye))
                .then(a.cmp(&b))
        });
        self.sorted.clear();
        for &model in &self.model_order {
            let model = &self.models[model];
            self.face_depths.clear();
            for (index, face) in model.faces.iter().enumerate() {
                self.face_depths.push(super::face_priority::FaceDepth {
                    index,
                    priority: face.priority,
                    depth: (face.center - eye).dot(direction) as i32,
                });
            }
            super::face_priority::order(&mut self.face_depths, &mut self.face_order);
            for &index in &self.face_order {
                let face = model.faces[index];
                if face.alpha {
                    // Models remain contiguous in the shared stream merge, preserving
                    // the priority order inside each model.
                    self.sorted.push(TransparentTriangle {
                        center: model.center,
                        ..face
                    });
                }
            }
        }
        self.order.clear();
        for t in &self.sorted {
            self.order.extend_from_slice(&t.indices);
        }
        if !self.order.is_empty() {
            queue.write_buffer(&self.transparent, 0, bytemuck::cast_slice(&self.order));
        }
    }
    pub fn draw(&self, pass: &mut wgpu::RenderPass<'_>, transparent: bool) {
        let (buffer, count) = if transparent {
            (&self.transparent, self.transparent_count)
        } else {
            (&self.opaque, self.opaque_count)
        };
        if count != 0 {
            pass.set_vertex_buffer(0, self.vertices.slice(..));
            pass.set_index_buffer(buffer.slice(..), wgpu::IndexFormat::Uint32);
            pass.draw_indexed(0..count, 0, 0..1);
        }
    }
    pub fn alpha_depth(&self, index: u32, eye: Vec3, _direction: Vec3) -> Option<f32> {
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

fn validate(geometry: &SceneGeometry, maximum: u64) -> Result<(), RenderError> {
    for bytes in [
        bytemuck::cast_slice::<_, u8>(&geometry.vertices),
        bytemuck::cast_slice(&geometry.opaque),
        bytemuck::cast_slice(&geometry.transparent),
    ] {
        if bytes.len().max(4) as u64 > maximum {
            return Err(RenderError::World("geometry buffer exceeds device limits"));
        }
    }
    if !geometry.opaque.len().is_multiple_of(3)
        || !geometry.transparent.len().is_multiple_of(3)
        || geometry
            .opaque
            .iter()
            .chain(&geometry.transparent)
            .any(|&index| index as usize >= geometry.vertices.len())
    {
        return Err(RenderError::World("invalid triangle index stream"));
    }
    if geometry
        .vertices
        .iter()
        .any(|vertex| vertex.texture_uv[0] > 256 || !(0..=11).contains(&vertex.position[3]))
    {
        return Err(RenderError::World(
            "invalid classic material or face priority",
        ));
    }
    if geometry.vertices.as_chunks::<3>().0.iter().any(|triangle| {
        triangle
            .iter()
            .any(|vertex| vertex.draw_tile != triangle[0].draw_tile)
    }) {
        return Err(RenderError::World(
            "triangle has inconsistent draw-tile anchors",
        ));
    }
    let mut end = 0;
    for span in &geometry.models {
        if span.start < end || span.start > span.end || span.end > geometry.vertices.len() {
            return Err(RenderError::World("invalid model span ordering"));
        }
        if let Some(first) = geometry
            .vertices
            .get(span.start)
            .filter(|_| !span.is_empty())
            && geometry.vertices[span.clone()]
                .iter()
                .any(|vertex| vertex.draw_tile != first.draw_tile)
        {
            return Err(RenderError::World(
                "model has inconsistent draw-tile anchors",
            ));
        }
        end = span.end;
    }
    Ok(())
}

#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn malformed_or_oversized_geometry_is_rejected_before_allocating_or_writing_gpu_buffers() {
        let vertex = SceneVertex {
            position: [0; 4],
            abhsl: 0,
            texture_uv: [0; 4],
            draw_tile: rustclient_scene::DrawTile::default(),
        };
        let mut geometry = SceneGeometry {
            vertices: vec![vertex; 3],
            opaque: vec![0, 1, 2],
            ..Default::default()
        };
        assert!(validate(&geometry, 1024).is_ok());
        assert!(validate(&geometry, 4).is_err());
        geometry.opaque[2] = 3;
        assert!(validate(&geometry, 1024).is_err());
        geometry.opaque[2] = 2;
        geometry.vertices[0].texture_uv[0] = 257;
        assert!(validate(&geometry, 1024).is_err());
        geometry.vertices[0].texture_uv[0] = 0;
        geometry.vertices[0].draw_tile = rustclient_scene::DrawTile::actor([8, 0]);
        assert!(validate(&geometry, 1024).is_err());
        geometry.vertices[0].draw_tile = rustclient_scene::DrawTile::default();
        geometry.models = vec![0..2, 1..3];
        assert!(validate(&geometry, 1024).is_err());
    }
}
