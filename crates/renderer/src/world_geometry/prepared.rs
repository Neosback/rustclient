//! Validated CPU preparation; no device/queue or platform access is needed here.
use super::{
    DeviceExt, GeometryData, GeometryGpu, RenderError, SceneGeometry, SceneVertex, compact,
    validate,
};

pub(crate) struct PreparedGeometry {
    packed: Option<compact::PackedGeometry>,
    data: GeometryData,
}

impl PreparedGeometry {
    pub fn new(geometry: &SceneGeometry, compact: bool) -> Result<Self, RenderError> {
        validate(geometry, u64::MAX)?;
        let mut packed = if compact {
            compact::PackedGeometry::build(geometry)?
        } else {
            None
        };
        let mut data = GeometryData {
            vertex_remap: packed
                .as_mut()
                .map_or_else(Vec::new, |packed| std::mem::take(&mut packed.remap)),
            expanded_vertex_bytes: (geometry.vertices.len() * size_of::<SceneVertex>()) as u64,
            ..GeometryData::default()
        };
        data.metadata(geometry)?;
        Ok(Self { packed, data })
    }
    fn buffers<'a>(&'a self, geometry: &'a SceneGeometry) -> [&'a [u8]; 3] {
        let (vertices, opaque, transparent) = self.packed.as_ref().map_or(
            (
                &geometry.vertices[..],
                &geometry.opaque[..],
                &geometry.transparent[..],
            ),
            |packed| {
                (
                    &packed.vertices[..],
                    &packed.opaque[..],
                    &packed.transparent[..],
                )
            },
        );
        [
            bytemuck::cast_slice(vertices),
            bytemuck::cast_slice(opaque),
            bytemuck::cast_slice(transparent),
        ]
    }
    pub fn validate_for(&self, geometry: &SceneGeometry, maximum: u64) -> Result<(), RenderError> {
        if self
            .buffers(geometry)
            .iter()
            .any(|bytes| bytes.len().max(4) as u64 > maximum)
        {
            return Err(RenderError::World(
                "prepared geometry exceeds device buffer limits",
            ));
        }
        Ok(())
    }
    pub fn upload(
        self,
        device: &wgpu::Device,
        geometry: &SceneGeometry,
    ) -> Result<GeometryGpu, RenderError> {
        self.validate_for(geometry, device.limits().max_buffer_size)?;
        let data = self.buffers(geometry);
        let buffers = std::array::from_fn::<_, 3, _>(|i| {
            device.create_buffer_init(&wgpu::util::BufferInitDescriptor {
                label: Some("resident scenery geometry"),
                contents: if data[i].is_empty() { &[0; 4] } else { data[i] },
                usage: wgpu::BufferUsages::COPY_DST
                    | if i == 0 {
                        wgpu::BufferUsages::VERTEX
                    } else {
                        wgpu::BufferUsages::INDEX
                    },
            })
        });
        let capacities = data.map(|bytes| bytes.len().max(4) as u64);
        let [vertices, opaque, transparent] = buffers;
        Ok(GeometryGpu {
            vertices,
            opaque,
            transparent,
            capacities,
            data: self.data,
        })
    }
    pub fn replace(
        self,
        gpu: &mut GeometryGpu,
        device: &wgpu::Device,
        queue: &wgpu::Queue,
        geometry: &SceneGeometry,
    ) -> Result<(), RenderError> {
        self.validate_for(geometry, device.limits().max_buffer_size)?;
        gpu.write(device, queue, self.buffers(geometry))?;
        gpu.data = self.data;
        Ok(())
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    #[ignore = "manual GPU buffer-lifetime diagnostic, no pixel assertions"]
    fn prepared_replacements_reuse_sufficient_buffers() {
        let (device, queue, _) = pollster::block_on(crate::capture::capture_device()).unwrap();
        let mut geometry = SceneGeometry {
            vertices: vec![
                SceneVertex {
                    position: [0; 4],
                    abhsl: 0,
                    texture_uv: [0; 4],
                    draw_tile: rustclient_scene::DrawTile::default()
                };
                3
            ],
            opaque: vec![0, 1, 2],
            ..SceneGeometry::default()
        };
        let mut gpu = PreparedGeometry::new(&geometry, true)
            .unwrap()
            .upload(&device, &geometry)
            .unwrap();
        let handles = [
            gpu.vertices.clone(),
            gpu.opaque.clone(),
            gpu.transparent.clone(),
        ];
        for vertex in &mut geometry.vertices {
            vertex.position[0] = 16;
        }
        let prepared = PreparedGeometry::new(&geometry, true).unwrap();
        assert!(prepared.validate_for(&geometry, 4).is_err());
        prepared
            .replace(&mut gpu, &device, &queue, &geometry)
            .unwrap();
        assert_eq!([gpu.vertices, gpu.opaque, gpu.transparent], handles);
    }
}
