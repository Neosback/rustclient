//! Source uploader admission metadata and original-face cap survive triangle extraction.
use super::*;
use crate::entity_mesh::{Placement, emit_model};
#[test]
fn sorted_actor_emission_caps_original_faces_and_retains_unused_vertices() {
    let mut assets = with_model();
    let mut model = assets.models.remove(&ModelId::new(1)).unwrap();
    model.vertices.push(ModelPoint::new(0, 0, 0)); // No emitted face references this guard.
    model.faces.resize(8193, model.faces[0]);
    let asset = scene(assets);
    let mut placement = Placement {
        depth_policy: rustclient_model::ModelDepthPolicy::SortedDeferredWrite,
        position: [0, 0],
        plane: Plane::MIN,
        size: 1,
        footprint: 51,
        orientation: 0,
        height_offset: 0,
    };
    let mut geometry = SceneGeometry::default();
    emit_model(
        &asset,
        &model,
        placement,
        crate::lighting::Light::entity(64, 850),
        &mut geometry,
    )
    .unwrap();
    assert_eq!(geometry.vertices.len(), 8192 * 3);
    assert_eq!(
        geometry.models[0]
            .sort_source
            .as_ref()
            .unwrap()
            .vertices
            .len(),
        4
    );
    placement.depth_policy = rustclient_model::ModelDepthPolicy::DepthWrite;
    geometry.clear();
    emit_model(
        &asset,
        &model,
        placement,
        crate::lighting::Light::entity(64, 850),
        &mut geometry,
    )
    .unwrap();
    assert_eq!(geometry.vertices.len(), 8193 * 3);
    assert!(geometry.models[0].sort_source.is_none());
    for face in &mut model.faces {
        face.alpha = 1;
    }
    geometry.clear();
    emit_model(
        &asset,
        &model,
        placement,
        crate::lighting::Light::entity(64, 850),
        &mut geometry,
    )
    .unwrap();
    assert_eq!(geometry.vertices.len(), 8192 * 3);
    assert_eq!(geometry.transparent.len(), 8192 * 3);
    assert!(geometry.models[0].sort_source.is_some());
}
