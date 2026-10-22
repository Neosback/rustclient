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
}
pub(crate) struct GeometryGpu {
    vertices: wgpu::Buffer,
    opaque: wgpu::Buffer,
    transparent: wgpu::Buffer,
    capacities: [u64; 3],
    opaque_count: u32,
    transparent_count: u32,
    triangles: Vec<TransparentTriangle>,
    sorted: Vec<TransparentTriangle>,
    order: Vec<u32>,
}
impl GeometryGpu {
    pub fn new(device: &wgpu::Device, geometry: &SceneGeometry) -> Result<Self, RenderError> {
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
            triangles: Vec::new(),
            sorted: Vec::new(),
            order: Vec::new(),
        };
        this.metadata(geometry)?;
        Ok(this)
    }
    fn metadata(&mut self, geometry: &SceneGeometry) -> Result<(), RenderError> {
        self.opaque_count = u32::try_from(geometry.opaque.len())
            .map_err(|_| RenderError::World("opaque index count"))?;
        self.transparent_count = u32::try_from(geometry.transparent.len())
            .map_err(|_| RenderError::World("alpha index count"))?;
        self.triangles.clear();
        for (ordinal, triangle) in geometry.transparent.chunks_exact(3).enumerate() {
            let mut center = Vec3::ZERO;
            for &index in triangle {
                let vertex = geometry
                    .vertices
                    .get(index as usize)
                    .ok_or(RenderError::World("alpha vertex index"))?;
                center += point(vertex);
            }
            self.triangles.push(TransparentTriangle {
                center: center / 3.,
                indices: [triangle[0], triangle[1], triangle[2]],
                ordinal,
            });
        }
        Ok(())
    }
    pub fn update(
        &mut self,
        device: &wgpu::Device,
        queue: &wgpu::Queue,
        geometry: &SceneGeometry,
    ) -> Result<(), RenderError> {
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
    pub fn sort(&mut self, queue: &wgpu::Queue, eye: Vec3, direction: Vec3) {
        self.sorted.clear();
        self.sorted.extend_from_slice(&self.triangles);
        self.sorted.sort_unstable_by(|a, b| {
            let a_depth = (a.center - eye).dot(direction);
            let b_depth = (b.center - eye).dot(direction);
            b_depth.total_cmp(&a_depth).then(a.ordinal.cmp(&b.ordinal))
        });
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
}
fn point(vertex: &SceneVertex) -> Vec3 {
    Vec3::new(
        f32::from(vertex.position[0]),
        f32::from(vertex.position[1]),
        f32::from(vertex.position[2]),
    )
}
