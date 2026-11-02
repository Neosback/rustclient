//! A baseline plus a scenery patch must show exactly what a full rebuild with the same committed
//! overrides shows (SPEC-0016, scenery patch publication).
use super::*;
use rustclient_game_state::LocationOverride;
use rustclient_model::{LocationChange, LocationDefinition};

fn quad(a: (i32, i32), b: (i32, i32)) -> StaticModel {
    // A vertical wall segment between two floor points, two smooth-shaded faces.
    let mut model = triangle([(a.0, 0, a.1), (b.0, 0, b.1), (b.0, -128, b.1)]);
    model.vertices.push(ModelPoint::new(a.0, -128, a.1));
    model.vertex_skins.push(None);
    model.skeletal_weights = rustclient_cache::world::VertexWeights::empty(4);
    let mut second = model.faces[0];
    second.vertices = [0, 2, 3];
    model.faces.push(second);
    model
}
fn wall_definition(id: u32, model: u32, shape: u8) -> ObjectDefinition {
    let mut def = definition();
    def.id = ObjectDefinitionId::new(id);
    def.models = vec![ModelReference {
        id: ModelId::new(model),
        shape: Some(shape),
    }];
    def.recolors.clear();
    def.non_flat_shading = true;
    def
}
fn row_scene() -> SceneAsset {
    let mut a = assets();
    // 1: closed door segment along the tile's south edge; 3: the open door along its west edge;
    // 2: a wall decoration; 4: a plain object standing nearby.
    for (id, model, shape) in [(1, 1, 0), (2, 2, 4), (3, 3, 0), (4, 4, 10)] {
        a.objects.insert(
            ObjectDefinitionId::new(id),
            wall_definition(id, model, shape),
        );
    }
    a.models
        .insert(ModelId::new(1), quad((-64, -64), (64, -64)));
    a.models.insert(
        ModelId::new(2),
        triangle([(-10, 0, -5), (10, 0, -5), (0, -20, 5)]),
    );
    a.models
        .insert(ModelId::new(3), quad((-64, -64), (-64, 64)));
    a.models.insert(ModelId::new(4), quad((-30, -30), (30, 30)));
    let at = |object: u32, shape: u8, x: i32, y: i32| StaticLocation {
        object: ObjectDefinitionId::new(object),
        tile: WorldTile::new(x, y, Plane::MIN),
        shape,
        orientation: LocOrientation::new(0).unwrap(),
    };
    a.squares[0].locations.extend([
        at(1, 0, 2, 3),
        at(1, 0, 3, 3),
        at(1, 0, 4, 3),
        at(2, 4, 3, 3),
        at(1, 0, 5, 3),
        at(1, 0, 6, 3),
        at(4, 10, 3, 4),
        // Far from every change below.
        at(1, 0, 10, 10),
        at(4, 10, 9, 10),
    ]);
    SceneAsset::new(a, WorldTile::new(0, 0, Plane::MIN), 12, 12).unwrap()
}
fn key(x: i32, y: i32) -> rustclient_model::LocationKey {
    rustclient_model::LocationKey {
        tile: WorldTile::new(x, y, Plane::MIN),
        layer: rustclient_model::LocationLayer::Boundary,
    }
}
fn replace(object: u32, orientation: u8) -> LocationOverride {
    LocationOverride {
        change: LocationChange::Replace(LocationDefinition {
            object: ObjectDefinitionId::new(object),
            shape: 0,
            orientation: LocOrientation::new(orientation).unwrap(),
        }),
        animation: None,
    }
}
fn removal() -> LocationOverride {
    LocationOverride {
        change: LocationChange::Remove,
        animation: None,
    }
}
fn full(asset: &SceneAsset, inputs: &SceneryInputs) -> WorldMesh {
    let descriptor = asset.descriptor().with_plane(Plane::MIN);
    build_mesh_with_inputs(asset, descriptor, WorldTile::new(2, 2, Plane::MIN), inputs).unwrap()
}
/// Every triangle of `geometry` as sorted raw vertex bytes, without the hidden static models.
fn triangles(geometry: &SceneGeometry, hidden: &[u32]) -> Vec<Vec<u8>> {
    let spans: Vec<_> = geometry
        .models
        .iter()
        .zip(&geometry.model_sources)
        .filter(|(_, source)| hidden.binary_search(source).is_ok())
        .map(|(model, _)| model.vertices.clone())
        .collect();
    let mut out: Vec<_> = geometry
        .vertices
        .chunks_exact(3)
        .enumerate()
        .filter(|(i, _)| !spans.iter().any(|span| span.contains(&(i * 3))))
        .map(|(_, chunk)| bytemuck::cast_slice::<SceneVertex, u8>(chunk).to_vec())
        .collect();
    out.sort();
    out
}
/// What the baseline plus the patch shows, against a full rebuild of the same overrides.
fn assert_patch_equals_full(
    changes: &[(rustclient_model::LocationKey, LocationOverride)],
) -> usize {
    let asset = row_scene();
    let base = full(&asset, &SceneryInputs::default());
    assert_eq!(
        base.geometry.models.len(),
        base.geometry.model_sources.len()
    );
    let mut current = SceneryInputs::default();
    for (key, change) in changes {
        current.locations.insert(*key, *change);
    }
    let expected = full(&asset, &current);
    let mut patched = current.clone();
    patched.base_locations = Some(std::sync::Arc::new(BTreeMap::new()));
    let overlay = build_dynamic(&asset, &patched).unwrap();
    let mut shown = triangles(&base.geometry, &overlay.hidden_static);
    shown.extend(triangles(&overlay, &[]));
    shown.sort();
    let mut wanted = triangles(&expected.geometry, &[]);
    wanted.extend(triangles(&expected.dynamic, &[]));
    wanted.sort();
    assert_eq!(shown.len(), wanted.len(), "triangle count");
    assert!(
        shown == wanted,
        "baseline plus patch differs from a full rebuild"
    );
    overlay.hidden_static.len()
}

#[test]
fn opening_a_door_between_walls_matches_a_full_rebuild() {
    let hidden = assert_patch_equals_full(&[(key(4, 3), replace(3, 0))]);
    assert!(hidden > 0 && hidden < 9, "only the door region is replaced");
}
#[test]
fn removing_a_wall_next_to_a_decoration_matches_a_full_rebuild() {
    assert_patch_equals_full(&[(key(3, 3), removal())]);
}
#[test]
fn rotating_and_replacing_separate_doors_matches_a_full_rebuild() {
    assert_patch_equals_full(&[(key(2, 3), replace(3, 1)), (key(6, 3), replace(3, 0))]);
}
#[test]
fn a_patch_leaves_far_locations_to_the_baseline() {
    let asset = row_scene();
    let mut current = SceneryInputs::default();
    current.locations.insert(key(4, 3), replace(3, 0));
    current.base_locations = Some(std::sync::Arc::new(BTreeMap::new()));
    let overlay = build_dynamic(&asset, &current).unwrap();
    let base = full(&asset, &SceneryInputs::default());
    let far: Vec<u32> = base
        .geometry
        .model_sources
        .iter()
        .copied()
        .filter(|source| {
            let loc = asset.sources().squares[0].locations[*source as usize];
            loc.tile.x >= 9
        })
        .collect();
    assert!(!far.is_empty());
    assert!(
        far.iter()
            .all(|source| overlay.hidden_static.binary_search(source).is_err()),
        "locations outside the neighbourhood stay in the baseline"
    );
}
#[test]
fn an_unchanged_or_reverted_override_set_needs_no_patch() {
    let asset = row_scene();
    let current = SceneryInputs {
        base_locations: Some(std::sync::Arc::new(BTreeMap::new())),
        ..SceneryInputs::default()
    };
    let overlay = build_dynamic(&asset, &current).unwrap();
    assert!(overlay.hidden_static.is_empty() && overlay.vertices.is_empty());
}

#[test]
fn picking_skips_the_static_models_a_patch_replaces() {
    let asset = row_scene();
    let base = full(&asset, &SceneryInputs::default());
    let mut current = SceneryInputs::default();
    current.locations.insert(key(4, 3), replace(3, 0));
    current.base_locations = Some(std::sync::Arc::new(BTreeMap::new()));
    let hidden = build_dynamic(&asset, &current).unwrap().hidden_static;
    let mut index = ScenePickIndex::new(&base.geometry).unwrap();
    let everything = SceneVisibility::everything();
    // Aim at a hidden model's first triangle, along its own normal.
    let model = base
        .geometry
        .models
        .iter()
        .zip(&base.geometry.model_sources)
        .find(|(_, source)| hidden.binary_search(source).is_ok())
        .map(|(model, _)| model.vertices.clone())
        .unwrap();
    let corner = |n: usize| -> [f32; 3] {
        std::array::from_fn(|axis| {
            f32::from(base.geometry.vertices[model.start + n].position[axis])
        })
    };
    let (first, second, third) = (corner(0), corner(1), corner(2));
    let centroid: [f32; 3] = std::array::from_fn(|i| (first[i] + second[i] + third[i]) / 3.);
    let edge_a: [f32; 3] = std::array::from_fn(|i| second[i] - first[i]);
    let edge_b: [f32; 3] = std::array::from_fn(|i| third[i] - first[i]);
    let normal = [
        edge_a[1] * edge_b[2] - edge_a[2] * edge_b[1],
        edge_a[2] * edge_b[0] - edge_a[0] * edge_b[2],
        edge_a[0] * edge_b[1] - edge_a[1] * edge_b[0],
    ];
    let length = normal.iter().map(|n| n * n).sum::<f32>().sqrt();
    let direction = normal.map(|n| -n / length);
    let origin: [f32; 3] = std::array::from_fn(|i| centroid[i] - direction[i] * 40.);
    let before = index
        .pick_surface(&base.geometry, origin, direction, &everything)
        .expect("the triangle is pickable before the patch");
    index.set_hidden(&base.geometry, &hidden);
    let after = index.pick_surface(&base.geometry, origin, direction, &everything);
    assert!(after.is_none_or(|hit| hit.distance > before.distance + 1.));
    index.set_hidden(&base.geometry, &[]);
    let restored = index
        .pick_surface(&base.geometry, origin, direction, &everything)
        .unwrap();
    assert!((restored.distance - before.distance).abs() < 1e-3);
}

#[test]
fn zone_projection_retains_structural_identity_across_pose_cycles_and_retry() {
    let asset = row_scene();
    let baseline = SceneryInputs::default();
    let mut projection = crate::LocationProjection::new(&asset, baseline.clone());
    let mut inputs = baseline;
    inputs.locations.insert(key(3, 3), removal());
    let first = projection.extract(&asset, &inputs).unwrap();
    assert!(!first.location_zones.is_empty());
    let builds = projection.zone_builds();
    inputs.cycle = rustclient_model::ClientCycle::new(10);
    let second = projection.extract(&asset, &inputs).unwrap();
    assert!(std::sync::Arc::ptr_eq(
        &first.location_zones,
        &second.location_zones
    ));
    assert_eq!(projection.zone_builds(), builds);
    inputs.locations.insert(key(3, 3), replace(999_999, 0));
    assert!(projection.extract(&asset, &inputs).is_err());
    inputs.locations.insert(key(3, 3), removal());
    let recovered = projection.extract(&asset, &inputs).unwrap();
    assert!(std::sync::Arc::ptr_eq(
        &second.location_zones,
        &recovered.location_zones
    ));
}

#[test]
fn zone_dependencies_cross_boundaries_without_duplicate_model_ownership() {
    let mut sources = row_scene().sources().clone();
    sources.squares[0].locations.push(StaticLocation {
        object: ObjectDefinitionId::new(1),
        tile: WorldTile::new(8, 3, Plane::MIN),
        shape: 0,
        orientation: LocOrientation::new(0).unwrap(),
    });
    let asset = SceneAsset::new(sources, WorldTile::new(0, 0, Plane::MIN), 12, 12).unwrap();
    let mut after = SceneryInputs::default();
    after.locations.insert(key(7, 3), replace(3, 1));
    let dirty = crate::changed_location_zones(&asset, &SceneryInputs::default(), &after);
    let zones = crate::build_location_zones(&asset, &after, &dirty).unwrap();
    for zone in &zones {
        assert!(
            zone.geometry
                .model_locations
                .iter()
                .all(|model| crate::SceneZone::of(model.key) == zone.zone)
        );
    }
    assert!(dirty.contains(&crate::SceneZone::of(key(8, 3))));
    // A far-away owning zone was not emitted just because its source shares a cache square.
    assert!(!dirty.contains(&crate::SceneZone::of(key(10, 10))));
}

#[test]
fn location_count_no_longer_forces_a_full_scene_fallback() {
    let asset = row_scene();
    let base = std::collections::BTreeMap::new();
    let mut after = base.clone();
    for x in 1..11 {
        for y in 1..11 {
            after.insert(key(x, y), removal());
        }
    }
    assert!(after.len() > crate::PATCH_KEY_BUDGET);
    assert!(crate::patch_supported(&asset, &base, &after));
    let mut projection = crate::LocationProjection::new(&asset, SceneryInputs::default());
    let inputs = SceneryInputs {
        locations: after,
        ..SceneryInputs::default()
    };
    let frame = projection.extract(&asset, &inputs).unwrap();
    assert!(!frame.location_zones.is_empty());
    let next = projection.extract(&asset, &inputs).unwrap();
    assert!(std::sync::Arc::ptr_eq(
        &frame.location_zones,
        &next.location_zones
    ));
}

#[test]
fn dependency_index_finds_cross_zone_footprints_and_adjacent_planes() {
    let mut sources = row_scene().sources().clone();
    sources
        .objects
        .get_mut(&ObjectDefinitionId::new(4))
        .unwrap()
        .size = [4, 2];
    let large = rustclient_model::LocationKey {
        tile: WorldTile::new(7, 7, Plane::MIN),
        layer: rustclient_model::LocationLayer::GameObject,
    };
    sources.squares[0].locations.push(StaticLocation {
        object: ObjectDefinitionId::new(4),
        tile: large.tile,
        shape: 10,
        orientation: LocOrientation::new(0).unwrap(),
    });
    for plane in [Plane::MIN, Plane::new(1).unwrap()] {
        sources.squares[0].locations.push(StaticLocation {
            object: ObjectDefinitionId::new(1),
            tile: WorldTile::new(10, 7, plane),
            shape: 0,
            orientation: LocOrientation::new(0).unwrap(),
        });
    }
    let asset = SceneAsset::new(sources, WorldTile::new(0, 0, Plane::MIN), 12, 12).unwrap();
    let mut current = SceneryInputs::default();
    current.locations.insert(key(10, 7), removal());
    let dirty = crate::changed_location_zones(&asset, &SceneryInputs::default(), &current);
    assert!(
        dirty.contains(&crate::SceneZone::of(large)),
        "an anchor-only lookup would miss the crossing footprint"
    );
    assert!(
        dirty.contains(&crate::SceneZone::of(rustclient_model::LocationKey {
            tile: WorldTile::new(10, 7, Plane::new(1).unwrap()),
            ..key(10, 7)
        }))
    );
    let built = crate::build_location_zones(&asset, &current, &dirty).unwrap();
    assert_eq!(
        built
            .iter()
            .flat_map(|z| &z.geometry.model_locations)
            .filter(|model| model.key == large)
            .count(),
        1
    );
}

#[test]
fn explicit_journal_invalidation_refreshes_the_domain_without_reusing_stale_resources() {
    let asset = row_scene();
    let mut projection = crate::LocationProjection::new(&asset, SceneryInputs::default());
    let mut inputs = SceneryInputs::default();
    inputs.locations.insert(key(3, 3), removal());
    let first = projection.extract(&asset, &inputs).unwrap();
    projection.invalidate();
    let refreshed = projection.extract(&asset, &inputs).unwrap();
    assert!(!std::sync::Arc::ptr_eq(
        &first.location_zones,
        &refreshed.location_zones
    ));
    let retained = projection.extract(&asset, &inputs).unwrap();
    assert!(std::sync::Arc::ptr_eq(
        &refreshed.location_zones,
        &retained.location_zones
    ));
}
