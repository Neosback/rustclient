//! Scenery patches hide baseline models by dropping draw ranges and alpha faces, never by
//! rewriting static buffers.
use super::*;
use rustclient_scene::{DrawTile, SceneModel};

/// Three models, one per effective location 10, 20 and 30. The middle one is translucent.
fn geometry() -> SceneGeometry {
    let mut geometry = SceneGeometry::default();
    for (model, alpha) in [(0_i16, false), (1, true), (2, false)] {
        let first = geometry.vertices.len();
        for corner in 0..3_i16 {
            let index = u32::try_from(geometry.vertices.len()).unwrap();
            if alpha {
                geometry.transparent.push(index);
            } else {
                geometry.opaque.push(index);
            }
            geometry.vertices.push(SceneVertex {
                position: [model * 128 + corner * 16, 0, 0, 0],
                abhsl: if alpha { 128 << 24 } else { 0 },
                texture_uv: [0; 4],
                draw_tile: DrawTile::scenery([i32::from(model), 0], true),
            });
        }
        geometry.models.push(SceneModel {
            vertices: first..geometry.vertices.len(),
            depth_policy: ModelDepthPolicy::DepthWrite,
            sort_source: None,
        });
    }
    geometry.model_sources = vec![10, 20, 30];
    geometry
}
/// Drawn index ranges as `(start, end)` pairs.
fn drawn(data: &GeometryData) -> Vec<(u32, u32)> {
    data.opaque_batches
        .iter()
        .map(|batch| (batch.indices.start, batch.indices.end))
        .collect()
}

#[test]
fn hiding_a_model_splits_the_opaque_draws_and_restoring_joins_them() {
    let mut data = GeometryData::default();
    data.metadata(&geometry()).unwrap();
    assert_eq!(drawn(&data), [(0, 6)]);
    assert!(data.set_hidden(&[10]));
    assert_eq!(drawn(&data), [(3, 6)]);
    assert!(!data.set_hidden(&[10]), "an unchanged list is a no-op");
    assert!(data.set_hidden(&[30]));
    assert_eq!(drawn(&data), [(0, 3)]);
    assert!(data.set_hidden(&[]));
    assert_eq!(drawn(&data), [(0, 6)]);
}
#[test]
fn hiding_a_translucent_model_forces_a_new_ordering_without_it() {
    let mut data = GeometryData::default();
    data.metadata(&geometry()).unwrap();
    let alpha = data.model_groups[1].expect("the translucent model is tracked");
    assert!(!data.models[alpha].hidden);
    data.last_sort = Some(ModelView::new([0; 3], 0, 0));
    assert!(data.set_hidden(&[20]));
    assert!(data.models[alpha].hidden);
    assert!(data.last_sort.is_none(), "the next frame re-sorts");
    assert!(data.set_hidden(&[]));
    assert!(!data.models[alpha].hidden);
}
#[test]
fn a_new_baseline_clears_the_hidden_list() {
    let mut data = GeometryData::default();
    data.metadata(&geometry()).unwrap();
    data.set_hidden(&[10, 20]);
    data.metadata(&geometry()).unwrap();
    assert!(data.hidden.is_empty());
    assert_eq!(drawn(&data), [(0, 6)]);
}
