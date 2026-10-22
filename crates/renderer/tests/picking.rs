use rustclient_cache::world::{MapSquare, TerrainTile, WorldAssets};
use rustclient_model::{FloorUnderlayId, Plane, RegionCoord, WorldTile};
use rustclient_renderer::{CameraTarget, GameplayCameraController, pick_terrain};
use rustclient_scene::SceneAsset;
use std::collections::BTreeMap;

fn flat_scene() -> SceneAsset {
    let tile = TerrainTile {
        elevation: 0,
        underlay: Some(FloorUnderlayId::new(1)),
        overlay: None,
        settings: 0,
    };
    let assets = WorldAssets {
        squares: vec![MapSquare {
            coord: RegionCoord::new(0, 0),
            tiles: vec![tile; 4 * 64 * 64],
            locations: Vec::new(),
        }],
        underlays: BTreeMap::new(),
        overlays: BTreeMap::new(),
        objects: BTreeMap::new(),
        models: BTreeMap::new(),
        texture_colors: BTreeMap::new(),
        textures: BTreeMap::new(),
        sequences: BTreeMap::new(),
        frames: BTreeMap::new(),
        skeletons: BTreeMap::new(),
        varbits: BTreeMap::new(),
        kits: BTreeMap::new(),
        metadata_fingerprint: "picking-fixture".into(),
        content_hash: [7; 32],
        auxiliary_map_files: 0,
        size_hint_warnings: Vec::new(),
    };
    SceneAsset::new(assets, WorldTile::new(0, 0, Plane::MIN), 6, 6).unwrap()
}

#[test]
fn center_pointer_selects_expected_visible_flat_tile() {
    let scene = flat_scene();
    let camera = GameplayCameraController::new(CameraTarget {
        x: 448,
        height: 0,
        z: 320,
        terrain_drop: 0,
    });
    let picked = pick_terrain(&scene, Plane::MIN, camera, [765, 503], [382.5, 251.5]);
    assert_eq!(picked, Some(WorldTile::new(3, 3, Plane::MIN)));
}
