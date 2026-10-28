//! Lossless static GPU packing. CPU triangles, model spans and picking remain unchanged.
use crate::RenderError;
use rustclient_scene::{SceneGeometry, SceneVertex};
use std::collections::{HashMap, hash_map::Entry};

pub(super) struct PackedGeometry {
    pub vertices: Vec<SceneVertex>,
    pub opaque: Vec<u32>,
    pub transparent: Vec<u32>,
    pub remap: Vec<u32>,
}
impl PackedGeometry {
    pub fn build(geometry: &SceneGeometry) -> Result<Option<Self>, RenderError> {
        type Key = [u8; size_of::<SceneVertex>()];
        let mut seen = HashMap::<Key, u32>::new();
        let mut vertices = Vec::new();
        let mut remap = Vec::with_capacity(geometry.vertices.len());
        for vertex in &geometry.vertices {
            let index = match seen.entry(bytemuck::cast(*vertex)) {
                Entry::Occupied(entry) => *entry.get(),
                Entry::Vacant(entry) => {
                    let index = u32::try_from(vertices.len())
                        .map_err(|_| RenderError::World("packed vertex count"))?;
                    vertices.push(*vertex);
                    entry.insert(index);
                    index
                }
            };
            remap.push(index);
        }
        // Retain a CPU remap only when GPU savings exceed its own resident allocation.
        let saved = (geometry.vertices.len() - vertices.len()) * size_of::<SceneVertex>();
        if saved <= remap.capacity() * size_of::<u32>() {
            return Ok(None);
        }
        let indices = |source: &[u32]| -> Result<Vec<u32>, RenderError> {
            source
                .iter()
                .map(|&index| {
                    remap
                        .get(index as usize)
                        .copied()
                        .ok_or(RenderError::World("packed vertex index"))
                })
                .collect()
        };
        Ok(Some(Self {
            opaque: indices(&geometry.opaque)?,
            transparent: indices(&geometry.transparent)?,
            vertices,
            remap,
        }))
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use rustclient_scene::DrawTile;

    #[test]
    fn packing_preserves_every_vertex_byte_and_index_order_and_requires_a_net_saving() {
        let vertex = SceneVertex {
            position: [1, 2, 3, 4],
            abhsl: 0x1234,
            texture_uv: [1, 2, 3, 4],
            draw_tile: DrawTile::scenery([1, 1], true),
        };
        let geometry = SceneGeometry {
            vertices: vec![vertex; 60],
            opaque: (0..30).collect(),
            transparent: (30..60).rev().collect(),
            ..SceneGeometry::default()
        };
        let packed = PackedGeometry::build(&geometry).unwrap().unwrap();
        for (index, &mapped) in packed.remap.iter().enumerate() {
            assert_eq!(packed.vertices[mapped as usize], geometry.vertices[index]);
        }
        for (original, mapped) in [
            (&geometry.opaque, &packed.opaque),
            (&geometry.transparent, &packed.transparent),
        ] {
            for (&original, &mapped) in original.iter().zip(mapped) {
                assert_eq!(
                    geometry.vertices[original as usize],
                    packed.vertices[mapped as usize]
                );
            }
        }
        let mut unique = geometry;
        for (index, vertex) in unique.vertices.iter_mut().enumerate() {
            vertex.abhsl = u32::try_from(index).unwrap();
        }
        assert!(PackedGeometry::build(&unique).unwrap().is_none());
    }

    #[test]
    #[ignore = "manual optimized static-memory trace"]
    fn packing_memory_trace() {
        let vertex = SceneVertex {
            position: [1, 2, 3, 0],
            abhsl: 100,
            texture_uv: [0; 4],
            draw_tile: DrawTile::scenery([1, 1], true),
        };
        let geometry = SceneGeometry {
            vertices: (0..300_000)
                .map(|index| SceneVertex {
                    abhsl: index % 60_000,
                    ..vertex
                })
                .collect(),
            opaque: (0..300_000).collect(),
            ..SceneGeometry::default()
        };
        let start = std::time::Instant::now();
        let packed = PackedGeometry::build(&geometry).unwrap().unwrap();
        println!(
            "static packing: {:?}; expanded vertex bytes={} packed vertex bytes={} retained remap bytes={}",
            start.elapsed(),
            geometry.vertices.len() * size_of::<SceneVertex>(),
            packed.vertices.len() * size_of::<SceneVertex>(),
            packed.remap.capacity() * size_of::<u32>()
        );
    }
}
