//! Geometry contracts checked before GPU allocation or queued writes.
use crate::RenderError;
use rustclient_model::ModelDepthPolicy;
use rustclient_scene::SceneGeometry;
pub(super) fn validate(geometry: &SceneGeometry, maximum: u64) -> Result<(), RenderError> {
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
    let mut sort_vertices = 0_usize;
    for model in &geometry.models {
        if let Some(source) = &model.sort_source {
            sort_vertices = sort_vertices.saturating_add(source.vertices.len());
            if source.vertices.is_empty()
                || source.radius < 0
                || source.diameter < source.radius
                || sort_vertices > 3_000_000
            {
                return Err(RenderError::World(
                    "invalid or oversized sorted-model inputs",
                ));
            }
        }
        let span = &model.vertices;
        if span.start < end
            || span.start > span.end
            || span.end > geometry.vertices.len()
            || !span.start.is_multiple_of(3)
            || !span.end.is_multiple_of(3)
        {
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
    validate_model_indices(geometry)
}

fn validate_model_indices(geometry: &SceneGeometry) -> Result<(), RenderError> {
    for (opaque, indices) in [(true, &geometry.opaque), (false, &geometry.transparent)] {
        let mut previous_deferred = None;
        for (offset, triangle) in indices.as_chunks::<3>().0.iter().enumerate() {
            let first = triangle[0] as usize;
            let group = geometry
                .models
                .partition_point(|model| model.vertices.end <= first);
            if let Some(model) = geometry
                .models
                .get(group)
                .filter(|model| model.vertices.contains(&first))
            {
                if triangle
                    .iter()
                    .any(|&index| !model.vertices.contains(&(index as usize)))
                {
                    return Err(RenderError::World("triangle crosses model spans"));
                }
                if opaque
                    && (model.depth_policy == ModelDepthPolicy::SortedDeferredWrite
                        || model.sort_source.is_some())
                {
                    if let Some((prior, next_offset)) = previous_deferred
                        && (group < prior || group == prior && offset != next_offset)
                    {
                        return Err(RenderError::World(
                            "deferred model indices are not contiguous and ordered",
                        ));
                    }
                    previous_deferred = Some((group, offset + 1));
                }
            } else {
                let low = group
                    .checked_sub(1)
                    .map_or(0, |prior| geometry.models[prior].vertices.end);
                let high = geometry
                    .models
                    .get(group)
                    .map_or(geometry.vertices.len(), |model| model.vertices.start);
                if triangle
                    .iter()
                    .any(|&index| !(low..high).contains(&(index as usize)))
                {
                    return Err(RenderError::World("triangle crosses model span boundary"));
                }
            }
        }
    }
    Ok(())
}

#[cfg(test)]
mod tests {
    use super::*;
    use rustclient_scene::SceneVertex;
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
        geometry.models = [0..2, 1..3]
            .map(|vertices| rustclient_scene::SceneModel {
                vertices,
                depth_policy: ModelDepthPolicy::DepthWrite,
                sort_source: None,
            })
            .into();
        assert!(validate(&geometry, 1024).is_err());
    }
    #[test]
    fn deferred_model_order_is_validated_before_any_upload() {
        let mut geometry = SceneGeometry {
            vertices: vec![
                SceneVertex {
                    position: [0; 4],
                    abhsl: 0,
                    texture_uv: [0; 4],
                    draw_tile: rustclient_scene::DrawTile::default()
                };
                9
            ],
            opaque: (0..9).collect(),
            models: vec![
                rustclient_scene::SceneModel {
                    vertices: 0..6,
                    depth_policy: ModelDepthPolicy::SortedDeferredWrite,
                    sort_source: None,
                },
                rustclient_scene::SceneModel {
                    vertices: 6..9,
                    depth_policy: ModelDepthPolicy::DepthWrite,
                    sort_source: None,
                },
            ],
            ..Default::default()
        };
        assert!(validate(&geometry, 1024).is_ok());
        geometry.opaque = vec![0, 1, 2, 6, 7, 8, 3, 4, 5];
        assert!(validate(&geometry, 1024).is_err());
        geometry.opaque = vec![0, 1, 6];
        assert!(validate(&geometry, 1024).is_err());
        geometry.models.remove(0);
        assert!(validate(&geometry, 1024).is_err()); // A triangle from ungrouped terrain cannot enter a model.
    }
}
