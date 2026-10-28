//! Actor triangle extraction plus immutable inputs needed by sorted-model admission.
use super::rotate_y;
use crate::{Result, SceneAsset, SceneGeometry, lighting::Light};
use rustclient_cache::world::StaticModel;
use rustclient_model::ModelPoint;

#[derive(Clone, Copy)]
pub(crate) struct Placement {
    pub(crate) depth_policy: rustclient_model::ModelDepthPolicy,
    /// South-west corner of the footprint in world units (tile centres are `tile * 128`).
    pub(crate) position: [i32; 2],
    pub(crate) plane: rustclient_model::Plane,
    /// Footprint edge in tiles; the model is centred on the whole footprint.
    pub(crate) size: u8,
    /// Fine-unit square the actor stands on; see [`SceneAsset::footprint_height`].
    pub(crate) footprint: i32,
    pub(crate) orientation: u16,
    /// Animation height offset in fine units; the model is drawn this much higher.
    pub(crate) height_offset: i32,
}

pub(crate) fn emit_model(
    asset: &SceneAsset,
    model: &StaticModel,
    placement: Placement,
    light: Light,
    geometry: &mut SceneGeometry,
) -> Result<()> {
    let Placement {
        depth_policy,
        position,
        plane,
        size,
        footprint,
        orientation,
        height_offset,
    } = placement;
    let origin = asset.descriptor().origin();
    let half_footprint = i32::from(size) * 64;
    let center_fine_x = position[0] - origin.x * 128 + half_footprint;
    let center_fine_z = position[1] - origin.y * 128 + half_footprint;
    let ground_height = asset
        .footprint_height(plane, [center_fine_x, center_fine_z], footprint)
        .unwrap_or(0)
        - height_offset;
    let level = crate::mesh::render_level(
        asset,
        plane,
        origin.x + center_fine_x.div_euclid(128),
        origin.y + center_fine_z.div_euclid(128),
    );

    let normals = crate::lighting::calculate(&model.vertices, &model.faces);
    let merged = vec![None; model.vertices.len()];
    let colors: Vec<Option<[i32; 3]>> = model
        .faces
        .iter()
        .enumerate()
        .map(|(i, &f)| crate::lighting::bake_lit(f, i, &normals, &merged, light))
        .collect();

    let first = geometry.vertices.len();
    // The GPU uploader caps original face indices, before hidden/back-face filtering.
    // Authored zero-only transparency-array presence is not yet retained by the cache model.
    let sorted = depth_policy == rustclient_model::ModelDepthPolicy::SortedDeferredWrite
        || model.faces.iter().any(|face| face.alpha != 0);
    let face_limit = if sorted { 8192 } else { usize::MAX };
    for (index, &face) in model.faces.iter().enumerate().take(face_limit) {
        let Some(face_colors) = colors[index] else {
            continue;
        };

        let uv = if face.texture.is_some() {
            face.texture_mapping
                .and_then(|s| model.texture_triangles.get(usize::from(s)))
                .map_or([[0., 0.], [1., 0.], [0., 1.]], |t| {
                    crate::uv::authored(
                        face.vertices.map(|v| model.vertices[v as usize]),
                        t.map(|v| model.vertices[v as usize]),
                    )
                })
        } else {
            [[0.; 2]; 3]
        };

        let points = face.vertices.map(|v| {
            let orig = model.vertices[v as usize];
            let rot = rotate_y(orig, orientation);
            [
                center_fine_x + rot.x,
                ground_height + rot.y,
                center_fine_z + rot.z,
            ]
        });

        geometry.model_triangle(points, face_colors, uv, face, u16::from(level))?;
    }

    let draw_tile = crate::DrawTile::actor([center_fine_x >> 7, center_fine_z >> 7]);
    for vertex in &mut geometry.vertices[first..] {
        vertex.draw_tile = draw_tile;
    }
    if geometry.vertices.len() > first {
        let sort_source = if sorted {
            let placed = model
                .vertices
                .iter()
                .map(|&point| {
                    let point = rotate_y(point, orientation);
                    ModelPoint::new(
                        center_fine_x + point.x,
                        ground_height + point.y,
                        center_fine_z + point.z,
                    )
                })
                .collect();
            Some(std::sync::Arc::new(crate::ModelSortSource::new(
                ModelPoint::new(center_fine_x, ground_height, center_fine_z),
                &model.vertices,
                placed,
            )?))
        } else {
            None
        };
        geometry.models.push(crate::mesh::SceneModel {
            vertices: first..geometry.vertices.len(),
            depth_policy,
            sort_source,
        });
    }

    Ok(())
}
