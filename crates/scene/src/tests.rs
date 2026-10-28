mod sorted_models;
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
            source: rustclient_cache::world::MapSquareSource::Archive,
            tiles,
            locations: Vec::new(),
        }],
        underlays: BTreeMap::from([(FloorUnderlayId::new(0), material)]),
        overlays: BTreeMap::from([(FloorOverlayId::new(0), material)]),
        objects: BTreeMap::new(),
        models: BTreeMap::new(),
        texture_colors: BTreeMap::new(),
        map_scenes: Vec::new(),
        map_dots: Vec::new(),
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
        name: None,
        actions: Default::default(),
        collision: ObjectCollision::Geometry,
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
        map_scene: None,
        map_icon: None,
        ambient: 0,
        contrast: 0,
        decoration_displacement: 16,
        floor_lift: 0,
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
                if triangle[0].render_level() != 0 {
                    continue;
                }
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
    assert_eq!(mesh::render_level(&a, Plane::new(1).unwrap(), 0, 0), 0);
    assert_eq!(mesh::render_level(&a, Plane::new(1).unwrap(), 1, 1), 1);
    assert_eq!(mesh::render_level(&a, Plane::new(2).unwrap(), 0, 0), 1);
    let wrong =
        SceneDescriptor::new(SceneAssetId::new([8; 32]), a.descriptor().origin(), 2, 2).unwrap();
    assert!(build_mesh(&a, wrong, WorldTile::new(0, 0, Plane::MIN)).is_err());
    assert!(build_mesh(&a, a.descriptor(), WorldTile::new(2, 0, Plane::MIN)).is_err());
}

#[test]
fn boundary_pairs_and_wall_decoration_offsets_match_client_rules() {
    assert_eq!(
        locations::placement_requests(2, 3, [0, 0]),
        [(2, 7, [0, 0]), (2, 0, [0, 0])]
    );
    // Type 5 takes the wall's full displacement plus the client's one-unit nudge.
    assert_eq!(
        locations::placement_requests(5, 1, locations::decoration_offset(5, 1, Some(34))),
        [(4, 1, [0, -35])]
    );
    assert_eq!(
        locations::placement_requests(6, 2, locations::decoration_offset(6, 2, Some(35))),
        [(4, 6, [-17, 17])]
    );
    assert_eq!(
        locations::placement_requests(7, 1, [0, 0]),
        [(4, 7, [0, 0])]
    );
    // The second plate of a type-8 decoration is drawn at the plain tile centre.
    assert_eq!(
        locations::placement_requests(8, 0, locations::decoration_offset(8, 0, Some(34))),
        [(4, 4, [17, -17]), (4, 6, [0, 0])]
    );
}

fn triangle(points: [(i32, i32, i32); 3]) -> StaticModel {
    StaticModel {
        vertices: points.map(|(x, y, z)| ModelPoint::new(x, y, z)).to_vec(),
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
    }
}
fn located(object: u32, shape: u8) -> StaticLocation {
    StaticLocation {
        object: ObjectDefinitionId::new(object),
        tile: WorldTile::new(1, 1, Plane::MIN),
        shape,
        orientation: LocOrientation::new(0).unwrap(),
    }
}

#[test]
fn wall_decoration_lands_in_the_same_place_whichever_is_authored_first() {
    let build = |decoration_first: bool| {
        let mut a = assets();
        let mut wall = definition();
        wall.models = vec![ModelReference {
            id: ModelId::new(1),
            shape: Some(0),
        }];
        wall.recolors.clear();
        wall.decoration_displacement = 32;
        let mut decoration = definition();
        decoration.id = ObjectDefinitionId::new(2);
        decoration.models = vec![ModelReference {
            id: ModelId::new(2),
            shape: Some(4),
        }];
        decoration.recolors.clear();
        a.objects.insert(ObjectDefinitionId::new(1), wall);
        a.objects.insert(ObjectDefinitionId::new(2), decoration);
        a.models.insert(
            ModelId::new(1),
            triangle([(-64, 0, -64), (64, 0, -64), (0, -128, 64)]),
        );
        a.models.insert(
            ModelId::new(2),
            triangle([(-10, 0, -5), (10, 0, -5), (0, -20, 5)]),
        );
        let (wall, decoration) = (located(1, 0), located(2, 5));
        a.squares[0].locations = if decoration_first {
            vec![decoration, wall]
        } else {
            vec![wall, decoration]
        };
        let a = scene(a);
        let mesh = build_mesh(&a, a.descriptor(), WorldTile::new(0, 0, Plane::MIN)).unwrap();
        let mut vertices: Vec<_> = mesh.geometry.vertices.iter().map(|v| v.position).collect();
        vertices.sort_unstable();
        vertices
    };
    let after = build(false);
    assert_eq!(after, build(true));
    // Tile (1, 1) centre 192, plus the wall's 32 displacement and the one-unit nudge, plus model x.
    assert!(after.iter().any(|p| p[0] == 192 + 33 - 10), "{after:?}");
}

#[test]
fn a_later_wall_decoration_replaces_the_earlier_one_on_its_tile() {
    let mut a = assets();
    for id in [1, 2] {
        let mut decoration = definition();
        decoration.id = ObjectDefinitionId::new(id);
        decoration.models = vec![ModelReference {
            id: ModelId::new(id),
            shape: Some(4),
        }];
        decoration.recolors.clear();
        a.objects.insert(ObjectDefinitionId::new(id), decoration);
        a.models.insert(
            ModelId::new(id),
            triangle([(-10, 0, -5), (10, 0, -5), (0, -20, 5)]),
        );
    }
    a.squares[0].locations = vec![located(1, 4), located(2, 4)];
    let a = scene(a);
    let mesh = build_mesh(&a, a.descriptor(), WorldTile::new(0, 0, Plane::MIN)).unwrap();
    assert_eq!(mesh.stats.model_triangles, 1);
}

#[test]
fn diagonal_decoration_plates_are_snapped_flush_against_their_type_nine_slab() {
    let mut a = assets();
    let mut wall = definition();
    wall.models = vec![ModelReference {
        id: ModelId::new(1),
        shape: Some(9),
    }];
    wall.recolors.clear();
    let mut decoration = definition();
    decoration.id = ObjectDefinitionId::new(2);
    decoration.models = vec![ModelReference {
        id: ModelId::new(2),
        shape: Some(4),
    }];
    decoration.recolors.clear();
    a.objects.insert(ObjectDefinitionId::new(1), wall);
    a.objects.insert(ObjectDefinitionId::new(2), decoration);
    // A thin diagonal slab: its flat face runs through the tile centre, its back 15.6 units behind.
    a.models.insert(
        ModelId::new(1),
        triangle([(-64, 0, 64), (64, 0, -64), (-11, 0, -11)]),
    );
    a.models.insert(
        ModelId::new(2),
        triangle([(-20, 0, 0), (20, 0, 0), (0, -30, 10)]),
    );
    let locations = [located(1, 9), located(2, 8)];
    a.squares[0].locations = locations.to_vec();
    let a = scene(a);
    let plan = decorations::plan(&a, &SceneryInputs::default(), &locations, None).unwrap();
    // The offset plate (orientation + 4) sits on the slab's inner face; the plain plate lifts one
    // unit off the outer face, as `RustOSRS` measured with the client's own models.
    assert_eq!(plan.requests(1, 8, 0), [(4, 4, [-12, -12]), (4, 6, [1, 1])]);
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
fn minimap_tile_colors(shape: u8) -> Vec<[u8; 3]> {
    let mut a = assets();
    a.overlays.insert(
        FloorOverlayId::new(0),
        FloorMaterial {
            rgb: 0x00dd_2222,
            texture: None,
            secondary: None,
            visible: true,
            hide_underlay: false,
        },
    );
    a.squares[0].tiles[65].overlay = Some(TerrainOverlay {
        id: FloorOverlayId::new(0),
        shape,
        rotation: 0,
    });
    let scene = SceneAsset::new(a, WorldTile::new(0, 0, Plane::MIN), 3, 3).unwrap();
    let image = minimap::build_scene_minimap(&scene, Plane::MIN);
    // Tile (1, 1) occupies pixel columns 4..8 and rows 4..8 of the 12x12 image.
    (4..8)
        .flat_map(|y| (4..8).map(move |x| (x, y)))
        .map(|(x, y)| {
            let i = (y * image.width + x) * 4;
            [image.rgba[i], image.rgba[i + 1], image.rgba[i + 2]]
        })
        .collect()
}
#[test]
fn minimap_overlay_shapes_follow_the_reference_numbering() {
    // Cache shape 0 is the full-square overlay: every pixel is the overlay colour.
    let full = minimap_tile_colors(0);
    assert!(full.iter().all(|c| *c == full[0]));
    assert!(
        full[0][0] > full[0][1],
        "overlay is red-dominant: {:?}",
        full[0]
    );
    // Cache shape 1 splits the tile between overlay and underlay.
    let split = minimap_tile_colors(1);
    assert!(split.iter().any(|c| *c == full[0]));
    assert!(split.iter().any(|c| *c != full[0]));
}
#[test]
fn minimap_palette_matches_the_reference_default_brightness() {
    assert_eq!(
        color::palette_rgb(0),
        1,
        "black is nudged off the transparent pixel"
    );
    let grass = color::palette_rgb(color::light(color::pack(88, 120, 100), 96));
    let [r, g, b] = [(grass >> 16) & 255, (grass >> 8) & 255, grass & 255];
    assert!(g > r && g > b, "green hue stays green: {grass:06x}");
}
fn minimap_pixel(image: &minimap::MinimapImage, x: usize, y: usize) -> [u8; 4] {
    let at = (y * image.width + x) * 4;
    image.rgba[at..at + 4].try_into().unwrap()
}
fn wall(assets: &mut WorldAssets, interactive: bool) {
    let mut object = definition();
    object.int1 = i32::from(interactive);
    assets.objects.insert(ObjectDefinitionId::new(1), object);
    assets.squares[0].locations.push(StaticLocation {
        object: ObjectDefinitionId::new(1),
        tile: WorldTile::new(0, 0, Plane::MIN),
        shape: 0,
        orientation: LocOrientation::new(0).unwrap(),
    });
}
#[test]
fn minimap_walls_are_pale_and_interactive_objects_red() {
    for (interactive, expected) in [(false, [0xEE; 3]), (true, [0xEE, 0, 0])] {
        let mut assets = assets();
        wall(&mut assets, interactive);
        let image = minimap::build_scene_minimap(&scene(assets), Plane::MIN);
        // Tile (0, 0) is the bottom-left tile of the 2x2 scene; rotation 0 is its west column.
        for y in 4..8 {
            let [r, g, b, a] = minimap_pixel(&image, 0, y);
            assert_eq!([r, g, b, a], [expected[0], expected[1], expected[2], 255]);
        }
    }
}
#[test]
fn minimap_hides_bridged_tiles_and_shows_the_bridge_deck_instead() {
    let tile = |plane: usize| plane * 4096 + 64 + 1;
    let mut assets = assets();
    assets.squares[0].tiles[tile(0)].settings = 24;
    let hidden = minimap::build_scene_minimap(&scene(assets), Plane::MIN);
    assert_eq!(minimap_pixel(&hidden, 4, 0)[3], 0, "bridge-covered tile");
    let mut assets = self::assets();
    assets.squares[0].tiles[tile(0)].settings = 24;
    assets.squares[0].tiles[tile(1)].settings = 8;
    let shown = minimap::build_scene_minimap(&scene(assets), Plane::MIN);
    assert_eq!(minimap_pixel(&shown, 4, 0)[3], 255, "bridge deck above");
}
#[test]
fn minimap_map_scene_sprites_replace_the_object_and_honour_the_canvas_offset() {
    use rustclient_cache::{Image, Source, Sprite};
    let mut assets = assets();
    let mut object = definition();
    object.map_scene = Some(0);
    assets.objects.insert(ObjectDefinitionId::new(1), object);
    assets.squares[0].locations.push(StaticLocation {
        object: ObjectDefinitionId::new(1),
        tile: WorldTile::new(0, 0, Plane::MIN),
        shape: 10,
        orientation: LocOrientation::new(0).unwrap(),
    });
    let red = [255, 0, 0, 255].repeat(4);
    assets.map_scenes.push(Sprite {
        id: rustclient_model::SpriteId::new(0),
        frame: 0,
        canvas: [4, 4],
        offset: [1, 1],
        image: Image::new(
            2,
            2,
            red,
            Source::group(rustclient_cache::IndexId(8), rustclient_cache::GroupId(0)),
        )
        .unwrap(),
    });
    let image = minimap::build_scene_minimap(&scene(assets), Plane::MIN);
    assert_eq!(minimap_pixel(&image, 1, 5), [255, 0, 0, 255]);
    assert_eq!(minimap_pixel(&image, 2, 6), [255, 0, 0, 255]);
    assert_ne!(minimap_pixel(&image, 0, 4), [255, 0, 0, 255]);
    assert_ne!(minimap_pixel(&image, 3, 7), [255, 0, 0, 255]);
}
#[test]
fn entity_models_stand_on_the_ground_with_their_tops_above_it() {
    use crate::entity_mesh::{Placement, emit_model};
    let scene = scene(with_model());
    let model = &scene.sources().models[&ModelId::new(1)];
    let mut geometry = SceneGeometry::default();
    emit_model(
        &scene,
        model,
        Placement {
            position: [0, 0],
            depth_policy: rustclient_model::ModelDepthPolicy::DepthWrite,
            plane: Plane::MIN,
            size: 1,
            footprint: 0,
            orientation: 0,
            height_offset: 0,
        },
        lighting::Light::entity(64, 850),
        &mut geometry,
    )
    .unwrap();
    let ground = scene.ground_height(Plane::MIN, [64, 64]).unwrap();
    let heights: Vec<i32> = geometry
        .vertices
        .iter()
        .map(|vertex| i32::from(vertex.position[1]))
        .collect();
    // The model's apex is 128 units above its base and the scene's y axis points down.
    assert_eq!(heights.iter().copied().max(), Some(ground));
    assert_eq!(heights.iter().copied().min(), Some(ground - 128));
}
#[test]
fn ground_height_follows_the_drawn_floor_triangle_not_a_corner_blend() {
    let mut assets = assets();
    // Only the north-east corner of tile (1, 1) is raised, so the tile is not planar.
    let tiles = &mut assets.squares[0].tiles;
    for (x, y, elevation) in [(1, 1, 0), (2, 1, 0), (1, 2, 0), (2, 2, 100)] {
        tiles[x * 64 + y].elevation = elevation;
    }
    let scene = scene(assets);
    let at = |x, y| scene.ground_height(Plane::MIN, [128 + x, 128 + y]).unwrap();
    // The split runs south-east to north-west: the south-west half stays flat and the
    // diagonal itself is level, where a bilinear blend would already rise to -25.
    assert_eq!(at(32, 32), 0);
    assert_eq!(at(64, 64), 0);
    // Halfway up the north-east half the plane reaches half of the corner's height; the
    // bilinear blend would give about -56.
    assert!((at(96, 96) + 50).abs() <= 1, "{}", at(96, 96));
}
fn roof_scene(roofs: &[(usize, usize)]) -> SceneAsset {
    let mut a = assets();
    for &(x, y) in roofs {
        a.squares[0].tiles[x * 64 + y].settings = 4;
    }
    SceneAsset::new(a, WorldTile::new(0, 0, Plane::MIN), 12, 12).unwrap()
}
fn roof_view(subject: (i32, i32), camera: (i32, i32), pitch: u16) -> RoofView {
    RoofView {
        plane: Plane::MIN,
        subject: [subject.0 * 128 + 64, subject.1 * 128 + 64],
        camera: [camera.0 * 128 + 64, camera.1 * 128 + 64],
        pitch,
        roofs_hidden: false,
    }
}
#[test]
fn roofs_show_unless_a_roofed_tile_sits_on_the_camera_line_or_under_the_player() {
    let scene = roof_scene(&[(5, 5)]);
    // Looking down a clear line from a steep pitch shows everything, roofs included.
    assert_eq!(
        roof_view((8, 5), (2, 5), 320).top_level(&scene.roofs_with(&std::sync::Arc::default())),
        3
    );
    // Standing under the roof cuts the scene at the player's plane at any pitch.
    assert_eq!(
        roof_view((5, 5), (5, 2), 383).top_level(&scene.roofs_with(&std::sync::Arc::default())),
        0
    );
    // A roofed tile between the camera and the player cuts it, but only at a shallow pitch.
    assert_eq!(
        roof_view((8, 5), (2, 5), 200).top_level(&scene.roofs_with(&std::sync::Arc::default())),
        0
    );
    assert_eq!(
        roof_view((8, 5), (2, 5), 310).top_level(&scene.roofs_with(&std::sync::Arc::default())),
        3
    );
    // The camera tile itself counts, and so do tiles the diagonal walk crosses.
    assert_eq!(
        roof_view((9, 9), (5, 5), 200).top_level(&scene.roofs_with(&std::sync::Arc::default())),
        0
    );
    assert_eq!(
        roof_view((9, 9), (3, 3), 200).top_level(&scene.roofs_with(&std::sync::Arc::default())),
        0
    );
    assert_eq!(
        roof_view((9, 2), (2, 2), 200).top_level(&scene.roofs_with(&std::sync::Arc::default())),
        3
    );
    // A camera outside the scene falls back to the player's plane.
    assert_eq!(
        roof_view((8, 5), (-3, 5), 200).top_level(&scene.roofs_with(&std::sync::Arc::default())),
        0
    );
}
#[test]
fn hide_roofs_option_always_caps_the_scene_at_the_player_plane() {
    let scene = roof_scene(&[]);
    let mut view = roof_view((8, 5), (2, 5), 383);
    assert_eq!(
        view.top_level(&scene.roofs_with(&std::sync::Arc::default())),
        3
    );
    view.roofs_hidden = true;
    assert_eq!(
        view.top_level(&scene.roofs_with(&std::sync::Arc::default())),
        0
    );
    view.plane = Plane::new(2).unwrap();
    assert_eq!(
        view.top_level(&scene.roofs_with(&std::sync::Arc::default())),
        2
    );
}
#[test]
fn every_plane_is_meshed_and_tagged_with_the_level_that_reveals_it() {
    let mut a = assets();
    // A forced-visible upper tile and a bridge deck over plane 0 at (1, 1).
    a.squares[0].tiles[4096 + 2 * 64 + 2].settings = 8;
    a.squares[0].tiles[4096 + 64 + 1].settings = 2;
    let a = SceneAsset::new(a, WorldTile::new(0, 0, Plane::MIN), 4, 4).unwrap();
    let mesh = build_mesh(&a, a.descriptor(), WorldTile::new(1, 1, Plane::MIN)).unwrap();
    let mut by_level = [0; 4];
    for triangle in mesh.geometry.vertices.chunks_exact(3) {
        by_level[usize::from(triangle[0].render_level())] += 1;
    }
    // Level 0: plane 0, the forced-visible plane-1 tile and the bridge deck; the rest of
    // planes 1..3 draws at its own plane, except plane 2 above the bridge which drops to 1.
    assert!(by_level.iter().all(|&count| count > 0), "{by_level:?}");
    assert!(mesh.geometry.vertices.iter().all(|v| v.render_level() <= 3));
}
#[test]
fn actors_stand_on_the_highest_floor_inside_their_footprint() {
    // Elevation rises by 128 per tile eastwards, so the uphill edge of a footprint is higher.
    let scene = scene(assets());
    let center = [192, 192];
    let at = |x| scene.ground_height(Plane::MIN, [x, 192]).unwrap();
    assert_eq!(
        scene.footprint_height(Plane::MIN, center, 0).unwrap(),
        at(192)
    );
    // A 51-unit footprint reaches 25 units each way: the east side wins (negative is up).
    let stood = scene.footprint_height(Plane::MIN, center, 51).unwrap();
    assert_eq!(stood, at(217));
    assert!(stood < at(192));
    // A footprint spanning a tile corner also samples that corner.
    let wide = scene.footprint_height(Plane::MIN, [128, 192], 153).unwrap();
    assert_eq!(wide, at(128 + 76));
}
fn decorated_scene(lift: u8, plane: Plane, bridge: bool) -> SceneAsset {
    let mut a = with_model();
    a.objects
        .get_mut(&ObjectDefinitionId::new(1))
        .unwrap()
        .floor_lift = lift;
    a.squares[0].locations[0].shape = 22;
    a.squares[0].locations[0].tile = WorldTile::new(0, 0, plane);
    if bridge {
        a.squares[0].tiles[4096].settings = 2;
    }
    scene(a)
}
#[test]
fn floor_decoration_lift_raises_the_tile_and_bridge_decks_shift_it_down_a_plane() {
    let height = |s: &SceneAsset| s.ground_height(Plane::MIN, [64, 64]).unwrap();
    let plain = height(&decorated_scene(0, Plane::MIN, false));
    assert_eq!(height(&decorated_scene(12, Plane::MIN, false)), plain - 12);
    // A decoration on plane 1 belongs to the deck of a bridge, which ground-level actors cross.
    let upper = Plane::new(1).unwrap();
    let flat_bridge = height(&decorated_scene(0, upper, true));
    assert_eq!(height(&decorated_scene(12, upper, true)), flat_bridge - 12);
    // The decoration under the bridge is on the lower link, not the deck.
    assert_eq!(height(&decorated_scene(12, Plane::MIN, true)), flat_bridge);
}
#[test]
fn roofs_are_eight_connected_regions_numbered_across_planes() {
    let flagged: [(usize, usize, usize); 5] =
        [(0, 1, 1), (0, 2, 2), (0, 5, 5), (1, 1, 1), (2, 7, 7)];
    let volumes = roofs::RoofVolumes::build(12, 12, |plane, x, y| flagged.contains(&(plane, x, y)));
    let slot = |plane, x, y| volumes.slot(plane, x, y);
    // Diagonal neighbours form one roof; a distant tile is another; each plane numbers its own.
    assert_eq!(slot(0, 1, 1), slot(0, 2, 2));
    assert_ne!(slot(0, 1, 1), 0);
    assert_ne!(slot(0, 5, 5), slot(0, 1, 1));
    assert_ne!(slot(1, 1, 1), slot(0, 1, 1));
    assert_ne!(slot(2, 7, 7), 0);
    assert_eq!(slot(0, 3, 3), 0);
    assert_eq!(slot(0, -1, 0), 0);
    assert_eq!(slot(0, 12, 0), 0);
    // Geometry on plane n + 1 takes the roof of plane n; the ground has no roof over it.
    let at = |source: u8, x, y| volumes.covering(Plane::new(source).unwrap(), x, y);
    assert_eq!(at(1, 1, 1), slot(0, 1, 1));
    assert_eq!(at(2, 1, 1), slot(1, 1, 1));
    assert_eq!(at(0, 1, 1), 0);
}
#[test]
fn exhausted_roof_slots_stay_unnumbered_instead_of_aliasing() {
    // 3 x 3 isolated tiles per 4 columns stays small; flag more regions than slots exist.
    let (width, height) = (140, 140);
    let volumes = roofs::RoofVolumes::build(width, height, |plane, x, y| {
        plane == 0 && x % 2 == 0 && y % 2 == 0
    });
    let mut seen = std::collections::BTreeSet::new();
    for x in (0..width).step_by(2) {
        for y in (0..height).step_by(2) {
            let slot = volumes.slot(0, i32::try_from(x).unwrap(), i32::try_from(y).unwrap());
            if slot != 0 {
                assert!(usize::from(slot) < ROOF_SLOTS);
                assert!(seen.insert(slot), "slot {slot} reused for a second roof");
            }
        }
    }
    assert_eq!(seen.len(), ROOF_SLOTS - 1);
}
fn roof_tag_scene() -> SceneAsset {
    // Two roofs on plane 0: tiles (5,5)-(6,5) and (9,9).
    let scene = roof_scene(&[(5, 5), (6, 5), (9, 9)]);
    let mesh = build_mesh(&scene, scene.descriptor(), WorldTile::new(1, 1, Plane::MIN)).unwrap();
    let mut by_tile = BTreeMap::<(u8, i32, i32), u16>::new();
    for triangle in mesh.geometry.vertices.chunks_exact(3) {
        let centre = |axis: usize| {
            triangle
                .iter()
                .map(|v| i32::from(v.position[axis]))
                .sum::<i32>()
                / 3
        };
        let key = (
            triangle[0].render_level(),
            centre(0).div_euclid(128),
            centre(2).div_euclid(128),
        );
        let old = by_tile.insert(key, triangle[0].roof_slot());
        assert!(
            old.is_none_or(|old| old == triangle[0].roof_slot()),
            "{key:?}"
        );
    }
    let slot = |level, x, y| by_tile[&(level, x, y)];
    // Plane 1 geometry (render level 1) over the roofed tiles carries the roof; ground does not.
    assert_ne!(slot(1, 5, 5), 0);
    assert_eq!(slot(1, 5, 5), slot(1, 6, 5));
    assert_ne!(slot(1, 9, 9), 0);
    assert_ne!(slot(1, 9, 9), slot(1, 5, 5));
    assert_eq!(slot(1, 3, 3), 0);
    assert_eq!(slot(0, 5, 5), 0);
    assert_eq!(slot(0, 9, 9), 0);
    // Plane 2 geometry sits over plane 1, which has no roof flags here.
    assert_eq!(slot(2, 5, 5), 0);
    scene
}
#[test]
fn geometry_over_a_roofed_tile_carries_that_roofs_slot() {
    roof_tag_scene();
}
#[test]
fn roof_modes_hide_the_roofs_over_their_target_tiles() {
    let scene = roof_tag_scene();
    let volumes = scene.roofs_with(&std::sync::Arc::default());
    let slot = |x, y| volumes.covering(Plane::new(1).unwrap(), x, y);
    let (near, far) = (slot(5, 5), slot(9, 9));
    let tiles = |x, y| [x, y].map(|v: i32| v * 128 + 64);
    let view = |subject: [i32; 2], camera: [i32; 2], plane| RoofView {
        plane: Plane::new(plane).unwrap(),
        subject,
        camera,
        pitch: 383,
        roofs_hidden: false,
    };
    let none = RoofModes(0);
    let hidden = |mask: &RoofMask| (1..64).filter(|s| mask.hides(*s)).collect::<Vec<_>>();
    let position = view(tiles(5, 5), tiles(2, 2), 0);
    assert!(
        position
            .hidden(&volumes, none, RoofTargets::default())
            .is_empty()
    );
    assert_eq!(
        hidden(&position.hidden(
            &volumes,
            RoofModes(RoofModes::POSITION),
            RoofTargets::default()
        )),
        [usize::from(near)]
    );
    // Only the focus tile counts: the player outside any roof hides nothing under POSITION.
    let outside = view(tiles(3, 3), tiles(2, 2), 0);
    assert!(
        outside
            .hidden(
                &volumes,
                RoofModes(RoofModes::POSITION),
                RoofTargets::default()
            )
            .is_empty()
    );
    let targets = RoofTargets {
        hovered: Some([9, 9]),
        destination: Some([6, 5]),
    };
    assert_eq!(
        hidden(&outside.hidden(&volumes, RoofModes(RoofModes::HOVERED), targets)),
        [usize::from(far)]
    );
    assert_eq!(
        hidden(&outside.hidden(&volumes, RoofModes(RoofModes::DESTINATION), targets)),
        [usize::from(near)]
    );
    // The camera-to-player walk crosses (5,5) and (6,5) but never (9,9).
    let between = view(tiles(8, 5), tiles(2, 5), 0);
    assert_eq!(
        hidden(&between.hidden(
            &volumes,
            RoofModes(RoofModes::BETWEEN),
            RoofTargets::default()
        )),
        [usize::from(near)]
    );
    // Viewing from plane 1 leaves the plane-0 roofs alone: that geometry is the floor upstairs.
    let upstairs = view(tiles(5, 5), tiles(2, 2), 1);
    assert!(
        upstairs
            .hidden(&volumes, RoofModes(0xF), targets)
            .is_empty()
    );
}
#[test]
fn roof_visibility_combines_the_level_cut_with_hidden_roofs() {
    let mut visibility = SceneVisibility::everything();
    let tag = |level: u16, slot: u16| level | (slot << 2);
    assert!(visibility.shows(tag(3, 7)));
    visibility.hidden.hide(7);
    visibility.hidden.hide(0);
    assert!(!visibility.shows(tag(3, 7)));
    assert!(visibility.shows(tag(3, 8)));
    assert!(visibility.shows(tag(0, 0)));
    visibility.top_level = 1;
    assert!(!visibility.shows(tag(2, 0)));
    assert!(visibility.shows(tag(1, 0)));
    assert!(visibility.hidden.words().iter().any(|w| *w != 0));
}
#[test]
fn area_overrides_force_tiles_roofed_for_slots_and_the_line_of_sight_cut() {
    let scene = roof_scene(&[]);
    let areas: std::sync::Arc<[RoofArea]> = std::sync::Arc::from([RoofArea {
        min: [4, 4],
        max: [5, 5],
        planes: [0, 0],
    }]);
    let base = scene.roofs_with(&std::sync::Arc::default());
    let forced = scene.roofs_with(&areas);
    let ground = Plane::MIN;
    assert!(!base.roofed(ground, 4, 5));
    assert!(forced.roofed(ground, 4, 5) && forced.roofed(ground, 5, 4));
    assert!(!forced.roofed(ground, 6, 6));
    // The override only covers the planes it names.
    assert!(!forced.roofed(Plane::new(1).unwrap(), 4, 4));
    // Repeated requests for the same areas share one flood fill.
    assert!(std::sync::Arc::ptr_eq(&forced, &scene.roofs_with(&areas)));
    // Standing under an override cuts the scene exactly like a flagged roof does.
    let view = roof_view((4, 4), (4, 1), 383);
    assert_eq!(view.top_level(&base), 3);
    assert_eq!(view.top_level(&forced), 0);
    // Meshes built with the areas tag their geometry with the forced roof.
    let inputs = SceneryInputs {
        roof_areas: areas,
        ..SceneryInputs::default()
    };
    let mesh = build_mesh_with_inputs(
        &scene,
        scene.descriptor(),
        WorldTile::new(1, 1, ground),
        &inputs,
    )
    .unwrap();
    assert!(std::sync::Arc::ptr_eq(&mesh.roofs, &forced));
    let tagged = mesh
        .geometry
        .vertices
        .iter()
        .filter(|vertex| vertex.roof_slot() != 0)
        .count();
    assert!(tagged > 0, "no geometry carries the overridden roof");
    let hidden = view.hidden(
        &forced,
        RoofModes(RoofModes::POSITION),
        RoofTargets::default(),
    );
    assert!(!hidden.is_empty());
}

#[test]
fn absent_map_catalog_tiles_remain_unwalkable_without_inventing_visible_floors() {
    let mut catalog = assets();
    for tile in &mut catalog.squares[0].tiles {
        tile.underlay = None;
        tile.overlay = None;
    }
    let normal = SceneAsset::new(catalog, WorldTile::new(0, 0, Plane::MIN), 8, 8).unwrap();
    let from = WorldTile::new(2, 2, Plane::MIN);
    let to = WorldTile::new(3, 3, Plane::MIN);
    assert!(
        CollisionMap::build(&normal, &SceneryInputs::default())
            .unwrap()
            .reachable(from, to)
    );
    let mut catalog = assets();
    catalog.squares[0].source = MapSquareSource::Absent;
    for tile in &mut catalog.squares[0].tiles {
        *tile = TerrainTile::default();
    }
    let empty = SceneAsset::new(catalog, WorldTile::new(0, 0, Plane::MIN), 8, 8).unwrap();
    assert!(
        !CollisionMap::build(&empty, &SceneryInputs::default())
            .unwrap()
            .reachable(from, to)
    );
    let mesh = build_mesh(&empty, empty.descriptor(), from).unwrap();
    assert_eq!(mesh.stats.terrain_triangles, 0);
    assert_eq!(empty.ground_height(Plane::MIN, [256, 256]).unwrap(), 0);
}

#[test]
fn npc_projection_keeps_base_depth_policy_when_appearance_morphs() {
    use crate::entity_mesh::{
        EntityCatalog, EntityPresentation, HiddenActors, build_entities_geometry,
    };
    use rustclient_cache::{
        GroupId, IndexId, Source,
        osrs240::{definitions, entity_assets::EntityAssets},
    };
    use rustclient_game_state::{
        AuthenticatedPlayer, GameState, InitialPlacement, SemanticOperation,
    };
    use rustclient_model::*;
    let asset = scene(with_model());
    let mut state = GameState::new();
    state
        .apply_operation(SemanticOperation::AuthenticateSession {
            revision: REVISION_240,
            player: AuthenticatedPlayer {
                profile: ServerProfileId::new(1),
                player: PlayerIndex::new(1),
                member: false,
                staff_level: 0,
            },
        })
        .unwrap();
    state
        .apply_operation(SemanticOperation::EstablishInitialPlacement(
            InitialPlacement {
                position: WorldTile::new(0, 0, Plane::MIN),
                map_anchor: WorldTile::new(0, 0, Plane::MIN),
                area: None,
            },
        ))
        .unwrap();
    state
        .apply_operation(SemanticOperation::UpdateNpcs(NpcInfoUpdate {
            new_npcs: vec![NewNpc {
                index: NpcIndex::new(1),
                definition_id: NpcDefinitionId::new(1),
                delta_x: 0,
                delta_y: 0,
                orientation: 0,
            }],
            ..Default::default()
        }))
        .unwrap();
    let before = state.semantic_fingerprint();
    for (base_deferred, morph_deferred) in [(false, false), (true, false), (false, true)] {
        let mut batch = EntityAssets::default();
        for (id, deferred) in [(1, base_deferred), (2, morph_deferred)] {
            let bytes: &[u8] = if deferred { &[147, 0] } else { &[0] };
            let mut def = definitions::npc(
                bytes,
                NpcDefinitionId::new(id),
                Source::group(IndexId(2), GroupId(9)),
            )
            .unwrap();
            def.models.push(ModelId::new(1));
            if id == 1 {
                def.morphs = Some(NpcMorphs {
                    varbit: None,
                    varp: Some(VarpId::new(1)),
                    transforms: vec![Some(NpcDefinitionId::new(2)); 2],
                });
            }
            batch.npcs.insert(def.id, def);
        }
        let mut catalog = EntityCatalog::default();
        assert!(catalog.merge(&asset, batch).is_empty());
        let mut geometry = SceneGeometry::default();
        build_entities_geometry(
            &asset,
            &catalog,
            state.entities(),
            &ActorMotion::default(),
            &BTreeMap::new(),
            EntityPresentation {
                interpolation: &AnimationInterpolation::default(),
                hidden: &HiddenActors::default(),
            },
            &mut geometry,
        )
        .unwrap();
        assert_eq!(geometry.models.len(), 1);
        assert_eq!(
            geometry.models[0].depth_policy,
            if base_deferred {
                ModelDepthPolicy::SortedDeferredWrite
            } else {
                ModelDepthPolicy::DepthWrite
            }
        );
        assert_eq!(state.semantic_fingerprint(), before);
    }
}
