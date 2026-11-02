//! Validated CPU preparation; no device/queue or platform access is needed here.
use super::{
    DeviceExt, GeometryData, GeometryGpu, RenderError, SceneGeometry, SceneVertex, compact,
    validate,
};

pub(crate) struct PreparedGeometry {
    packed: Option<compact::PackedGeometry>,
    pub(super) data: GeometryData,
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
        data.immutable = !data.vertex_remap.is_empty();
        data.vertex_remap = Vec::new();
        Ok(Self { packed, data })
    }
    /// Packed streams own every GPU input; release their redundant expanded CPU source.
    pub(super) fn release_packed_source(&self, geometry: &mut SceneGeometry) {
        if self.packed.is_some() {
            *geometry = SceneGeometry::default();
        }
    }
    pub(super) fn buffers<'a>(&'a self, geometry: &'a SceneGeometry) -> [&'a [u8]; 3] {
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
    fn packed_geometry_stays_immutable_without_keeping_the_remap() {
        let vertex = SceneVertex {
            position: [1, 2, 3, 0],
            abhsl: 0x1234,
            texture_uv: [1, 2, 3, 4],
            draw_tile: rustclient_scene::DrawTile::default(),
        };
        let geometry = SceneGeometry {
            vertices: vec![vertex; 60],
            opaque: (0..30).collect(),
            transparent: (30..60).rev().collect(),
            ..SceneGeometry::default()
        };
        let packed = PreparedGeometry::new(&geometry, true).unwrap();
        assert!(packed.packed.is_some(), "the fixture must pack");
        assert!(packed.data.immutable);
        assert_eq!(packed.data.vertex_remap.capacity(), 0);
        let plain = PreparedGeometry::new(&geometry, false).unwrap();
        assert!(!plain.data.immutable);
        let bytes = packed.buffers(&geometry).map(<[u8]>::to_vec);
        let mut released = geometry;
        packed.release_packed_source(&mut released);
        assert_eq!(released.vertices.capacity(), 0);
        assert_eq!(
            packed.buffers(&released),
            bytes.each_ref().map(Vec::as_slice)
        );
        assert!(packed.validate_for(&released, u64::MAX).is_ok());
    }
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
