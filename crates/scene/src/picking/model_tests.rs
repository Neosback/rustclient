//! Picking and sorted-actor admission must consume the same immutable projection inputs.
use crate::{
    DrawTile, ModelSortSource, ModelView, SceneGeometry, SceneModel, SceneVertex, SceneVisibility,
    pick_surface,
};
use rustclient_model::{ModelDepthPolicy, ModelPoint};
use std::sync::Arc;
#[test]
fn model_near_guard_and_front_faces_are_shared_with_picking() {
    let positions = [[-10_i16, 10, 100], [10, 10, 100], [0, -10, 100]];
    let placed =
        positions.map(|[x, y, z]| ModelPoint::new(i32::from(x), i32::from(y), i32::from(z)));
    let mut geometry = SceneGeometry {
        vertices: positions
            .map(|[x, y, z]| SceneVertex {
                position: [x, y, z, 0],
                abhsl: 0,
                texture_uv: [0; 4],
                draw_tile: DrawTile::actor([0, 0]),
            })
            .to_vec(),
        models: vec![SceneModel {
            vertices: 0..3,
            depth_policy: ModelDepthPolicy::SortedDeferredWrite,
            sort_source: Some(Arc::new(
                ModelSortSource::new(ModelPoint::new(0, 0, 0), &placed, placed.to_vec()).unwrap(),
            )),
        }],
        ..Default::default()
    };
    let visibility = SceneVisibility::everything().with_model_view(ModelView::new([0; 3], 0, 0));
    let pick =
        |geometry: &SceneGeometry| pick_surface(geometry, [0.; 3], [0., 0., 1.], &visibility);
    assert!(pick(&geometry).is_some());
    geometry.vertices.swap(1, 2);
    assert!(pick(&geometry).is_none());
    geometry.vertices.swap(1, 2);
    let source = Arc::make_mut(geometry.models[0].sort_source.as_mut().unwrap());
    let mut vertices = source.vertices.to_vec();
    vertices.push(ModelPoint::new(0, 0, 49));
    source.vertices = vertices.into();
    assert!(pick(&geometry).is_none());
    assert!(
        pick_surface(
            &geometry,
            [0.; 3],
            [0., 0., 1.],
            &SceneVisibility::everything()
        )
        .is_some()
    );
    assert_eq!(geometry.vertices.len(), 3); // Disposable visibility never edits retained geometry.
}
