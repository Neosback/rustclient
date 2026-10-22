use super::*;
use rustclient_cache::world::*;
use rustclient_model::{
    FloorOverlayId, FloorUnderlayId, LocOrientation, ModelId, ModelPoint, ObjectDefinitionId,
    RegionCoord,
};
use std::collections::BTreeMap;
fn assets() -> WorldAssets {
    let mut tiles = vec![TerrainTile::default(); 16384];
    for p in 0..4 {
        for x in 0..64 {
            for y in 0..64 {
                tiles[p * 4096 + x * 64 + y] = TerrainTile {
                    elevation: i32::try_from(x * 128 + p * 240).unwrap(),
                    underlay: Some(FloorUnderlayId::new(0)),
                    ..TerrainTile::default()
                };
            }
        }
    }
    let material = FloorMaterial {
        rgb: 0x0044_8822,
        texture: None,
        secondary: None,
        visible: true,
        hide_underlay: true,
    };
    WorldAssets {
        squares: vec![MapSquare {
            coord: RegionCoord::new(0, 0),
            tiles,
            locations: Vec::new(),
        }],
        underlays: BTreeMap::from([(FloorUnderlayId::new(0), material)]),
        overlays: BTreeMap::from([(FloorOverlayId::new(0), material)]),
        objects: BTreeMap::new(),
        models: BTreeMap::new(),
        texture_colors: BTreeMap::new(),
        textures: BTreeMap::new(),
        sequences: BTreeMap::new(),
        frames: BTreeMap::new(),
        skeletons: BTreeMap::new(),
        varbits: BTreeMap::new(),
        kits: BTreeMap::new(),
        metadata_fingerprint: "synthetic".into(),
        content_hash: [7; 32],
        auxiliary_map_files: 0,
        size_hint_warnings: Vec::new(),
    }
}
fn scene(assets: WorldAssets) -> SceneAsset {
    SceneAsset::new(assets, WorldTile::new(0, 0, Plane::MIN), 2, 2).unwrap()
}
fn definition() -> ObjectDefinition {
    ObjectDefinition {
        id: ObjectDefinitionId::new(1),
        models: vec![ModelReference {
            id: ModelId::new(1),
            shape: None,
        }],
        size: [1, 1],
        scale: [128; 3],
        translation: [0; 3],
        mirrored: false,
        contour: None,
        recolors: vec![(1, 2), (2, 3)],
        retextures: vec![],
        animation: None,
        morphs: None,
        non_flat_shading: false,
        clipped: true,
        model_clipped: false,
        random_start: true,
        int1: 1,
        ambient: 0,
        contrast: 0,
        decoration_displacement: 16,
    }
}
fn with_model() -> WorldAssets {
    let mut a = assets();
    a.objects.insert(ObjectDefinitionId::new(1), definition());
    a.models.insert(
        ModelId::new(1),
        StaticModel {
            vertices: vec![
                ModelPoint::new(-64, 0, -64),
                ModelPoint::new(64, 0, -64),
                ModelPoint::new(0, -128, 64),
            ],
            vertex_skins: vec![None; 3],
            skeletal_weights: vec![Vec::new(); 3],
            texture_triangles: vec![],
            texture_types: vec![],
            faces: vec![ModelFace {
                vertices: [0, 1, 2],
                color: 1,
                texture: None,
                render_type: 0,
                alpha: 0,
                skin: None,
                texture_mapping: None,
                bias: 0,
                priority: 0,
            }],
        },
    );
    a.squares[0].locations.push(StaticLocation {
        object: ObjectDefinitionId::new(1),
        tile: WorldTile::new(0, 0, Plane::MIN),
        shape: 10,
        orientation: LocOrientation::new(0).unwrap(),
    });
    a
}
#[test]
fn instance_transform_order_matches_editor_vectors() {
    let mut def = definition();
    def.scale = [256, 64, 128];
    def.translation = [-7, 3, 5];
    let point = locations::transform(ModelPoint::new(10, -20, 30), &def, 10, 1, false).unwrap();
    assert_eq!(point, ModelPoint::new(53, -7, -5));
    let point = locations::transform(ModelPoint::new(10, -20, 30), &def, 10, 1, true).unwrap();
    assert_eq!(point, ModelPoint::new(-67, -7, -5));
    let p = locations::rotate_diagonal(ModelPoint::new(128, 0, 0));
    assert_eq!(p, ModelPoint::new(90, 0, -91));
    def.scale = [128; 3];
    def.translation = [0; 3];
    assert_eq!(
        locations::transform(ModelPoint::new(128, 0, 0), &def, 4, 4, false).unwrap(),
        ModelPoint::new(135, 0, -136)
    );
}
#[test]
fn contour_recolor_and_static_morph_policy_are_visible_contracts() {
    let mut a = with_model();
    a.objects
        .get_mut(&ObjectDefinitionId::new(1))
        .unwrap()
        .contour = Some(0);
    let a = scene(a);
    let m = build_mesh(&a, a.descriptor(), WorldTile::new(0, 0, Plane::MIN)).unwrap();
    assert_eq!(m.stats.model_triangles, 1);
    let v = &m.geometry.vertices[0..3];
    assert_eq!(
        [v[0].position[1], v[1].position[1], v[2].position[1]],
        [0, -128, -192]
    );
    assert_eq!(v[0].abhsl & 0xffff, 2);
    let mut a = with_model();
    a.objects
        .get_mut(&ObjectDefinitionId::new(1))
        .unwrap()
        .morphs = Some(ObjectMorphs {
        varbit: None,
        varp: None,
        targets: vec![],
        fallback: None,
    });
    let a = scene(a);
    let m = build_mesh(&a, a.descriptor(), WorldTile::new(0, 0, Plane::MIN)).unwrap();
    assert_eq!(m.stats.morphs_deferred, 0);
    assert_eq!(m.stats.model_triangles, 0);
}
#[test]
fn all_floor_shapes_and_rotations_cover_exactly_the_same_tile_area() {
    for shape in 0..12 {
        for rotation in 0..4 {
            let mut a = assets();
            a.squares[0].tiles[65].overlay = Some(TerrainOverlay {
                id: FloorOverlayId::new(0),
                shape,
                rotation,
            });
            let a = SceneAsset::new(a, WorldTile::new(0, 0, Plane::MIN), 3, 3).unwrap();
            let m = build_mesh(&a, a.descriptor(), WorldTile::new(0, 0, Plane::MIN)).unwrap();
            let mut area = 0_f32;
            for triangle in m.geometry.vertices.chunks_exact(3) {
                let a = triangle[0].position.map(f32::from);
                let b = triangle[1].position.map(f32::from);
                let c = triangle[2].position.map(f32::from);
                area += ((b[0] - a[0]) * (c[2] - a[2]) - (b[2] - a[2]) * (c[0] - a[0])).abs() / 2.;
            }
            assert!(
                (area / 16384. - 1.).abs() < 0.001,
                "shape {shape}, rotation {rotation}, area {area}"
            );
        }
    }
}
#[test]
fn bridges_retain_upper_surface_and_asset_identity_is_enforced() {
    let mut a = assets();
    a.squares[0].tiles[4096].settings = 2;
    let a = scene(a);
    assert!(mesh::visible(&a, Plane::new(1).unwrap(), 0, 0, Plane::MIN));
    assert!(!mesh::visible(&a, Plane::new(1).unwrap(), 1, 1, Plane::MIN));
    assert!(!mesh::visible(&a, Plane::new(2).unwrap(), 0, 0, Plane::MIN));
    let wrong =
        SceneDescriptor::new(SceneAssetId::new([8; 32]), a.descriptor().origin(), 2, 2).unwrap();
    assert!(build_mesh(&a, wrong, WorldTile::new(0, 0, Plane::MIN)).is_err());
    assert!(build_mesh(&a, a.descriptor(), WorldTile::new(2, 0, Plane::MIN)).is_err());
}

#[test]
fn boundary_pairs_and_wall_decoration_offsets_match_editor_rules() {
    assert_eq!(
        locations::placement_requests(2, 3, 34),
        [(2, 7, [0, 0]), (2, 0, [0, 0])]
    );
    assert_eq!(locations::placement_requests(5, 1, 34), [(4, 1, [0, -34])]);
    assert_eq!(locations::placement_requests(6, 2, 35), [(4, 6, [-17, 17])]);
    assert_eq!(locations::placement_requests(7, 1, 34), [(4, 7, [0, 0])]);
    assert_eq!(
        locations::placement_requests(8, 0, 34),
        [(4, 4, [17, -17]), (4, 6, [17, -17])]
    );
}

#[test]
fn committed_variables_and_location_removal_control_morph_projection() {
    let mut a = with_model();
    let mut morph = definition();
    morph.id = ObjectDefinitionId::new(2);
    morph.models.clear();
    morph.morphs = Some(ObjectMorphs {
        varbit: None,
        varp: Some(rustclient_model::VarpId::new(7)),
        targets: vec![None, Some(ObjectDefinitionId::new(1))],
        fallback: None,
    });
    a.objects.insert(morph.id, morph);
    a.squares[0].locations[0].object = ObjectDefinitionId::new(2);
    let a = scene(a);
    let spawn = WorldTile::new(0, 0, Plane::MIN);
    let mut state = rustclient_game_state::GameState::new();
    state
        .apply_operation(rustclient_game_state::SemanticOperation::BeginSession {
            revision: rustclient_model::REVISION_240,
        })
        .unwrap();
    let hidden =
        build_mesh_with_inputs(&a, a.descriptor(), spawn, &state.scenery().into()).unwrap();
    assert!(hidden.dynamic.vertices.is_empty());
    state
        .apply_operation(rustclient_game_state::SemanticOperation::Scenery(
            rustclient_game_state::SceneryOperation::SetVariable {
                id: rustclient_model::VarpId::new(7),
                value: 1,
            },
        ))
        .unwrap();
    let shown = build_mesh_with_inputs(&a, a.descriptor(), spawn, &state.scenery().into()).unwrap();
    assert_eq!(shown.dynamic.vertices.len(), 3);
    let key = rustclient_model::LocationKey {
        tile: spawn,
        layer: rustclient_model::LocationLayer::GameObject,
    };
    state
        .apply_operation(rustclient_game_state::SemanticOperation::Scenery(
            rustclient_game_state::SceneryOperation::ChangeLocation {
                key,
                change: rustclient_model::LocationChange::Remove,
            },
        ))
        .unwrap();
    let removed =
        build_mesh_with_inputs(&a, a.descriptor(), spawn, &state.scenery().into()).unwrap();
    assert!(removed.dynamic.vertices.is_empty());
}
