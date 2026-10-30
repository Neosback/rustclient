//! Explicit GPU lifetime check; no render/pixel parity assertions.
use super::*;
use rustclient_scene::{DrawTile, SceneGeometry, SceneStats, SceneVertex, WorldMesh};

#[test]
#[ignore = "manual GPU resource-lifetime diagnostic, requires an adapter"]
fn scenery_replacement_retains_actors_until_the_scene_asset_changes() {
    let (device, queue, _) = pollster::block_on(crate::capture::capture_device()).unwrap();
    let mut mesh = WorldMesh {
        asset: rustclient_model::SceneAssetId::new([1; 32]),
        geometry: SceneGeometry::default(),
        dynamic: SceneGeometry::default(),
        focus: [0.; 3],
        stats: SceneStats::default(),
        textures: std::collections::BTreeMap::new(),
        roofs: std::sync::Arc::default(),
    };
    let mut world = WorldGpu::new(
        &device,
        &queue,
        crate::world_target::FORMAT,
        SurfaceSize::new(1, 1),
        &mesh,
        1,
    )
    .unwrap();
    let actors = SceneGeometry {
        vertices: vec![
            SceneVertex {
                position: [0; 4],
                abhsl: 0,
                texture_uv: [0; 4],
                draw_tile: DrawTile::scenery([0; 2], true),
            };
            3
        ],
        opaque: vec![0, 1, 2],
        ..SceneGeometry::default()
    };
    world.update_entities(&device, &queue, &actors).unwrap();
    let actor_bytes = world.entities.stats().expanded_vertex_bytes;
    assert_ne!(actor_bytes, 0);
    world.replace_scenery(&device, &queue, &mesh).unwrap();
    assert_eq!(world.entities.stats().expanded_vertex_bytes, actor_bytes);
    mesh.geometry.opaque = vec![99, 99, 99];
    assert!(world.replace_scenery(&device, &queue, &mesh).is_err());
    assert_eq!(world.entities.stats().expanded_vertex_bytes, actor_bytes);
    mesh.geometry.opaque.clear();
    // A reconnect can reuse identical cache identity, but must not inherit old actors.
    world
        .replace_prepared(
            &device,
            &queue,
            &mesh,
            crate::world_upload::WorldUpload::prepare(&mesh).unwrap(),
            crate::WorldUpdate::Scene,
        )
        .unwrap();
    assert_eq!(world.entities.stats().expanded_vertex_bytes, 0);
    world.update_entities(&device, &queue, &actors).unwrap();
    world.replace_mesh(&device, &queue, &mesh).unwrap();
    assert_eq!(world.entities.stats().expanded_vertex_bytes, 0);
    world.update_entities(&device, &queue, &actors).unwrap();
    mesh.asset = rustclient_model::SceneAssetId::new([2; 32]);
    world.replace_scenery(&device, &queue, &mesh).unwrap();
    assert_eq!(world.entities.stats().expanded_vertex_bytes, 0);
}
