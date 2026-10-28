use super::*;
use crate::{DrawTile, PickRange, SceneGeometry, SceneVertex};
use rustclient_model::{NpcDefinitionId, NpcIndex, NpcInstanceId, WorldTarget};
use std::time::Instant;

fn geometry(side: i16) -> SceneGeometry {
    let mut vertices = Vec::new();
    for x in 0..side {
        for z in 0..side {
            for point in [
                [x * 64, 0, z * 64],
                [x * 64 + 60, 0, z * 64],
                [x * 64, 0, z * 64 + 60],
            ] {
                vertices.push(SceneVertex {
                    position: [point[0], point[1], point[2], 0],
                    abhsl: if (x + z) % 7 == 0 { 255 << 24 } else { 0 },
                    texture_uv: [0, 0, 0, u16::from((x + z) % 3 != 0)],
                    draw_tile: DrawTile::scenery([i32::from(x / 2), i32::from(z / 2)], true),
                });
            }
        }
    }
    SceneGeometry {
        vertices,
        ..SceneGeometry::default()
    }
}
fn compare(
    geometry: &SceneGeometry,
    index: &ScenePickIndex,
    origin: [f32; 3],
    direction: [f32; 3],
    visibility: &SceneVisibility,
) {
    let expected = pick_surface(geometry, origin, direction, visibility);
    let actual = index.pick_surface(geometry, origin, direction, visibility);
    assert_eq!(
        actual.map(|h| (h.distance, h.target)),
        expected.map(|h| (h.distance, h.target)),
        "origin={origin:?}, direction={direction:?}"
    );
}
#[test]
fn indexed_hits_match_original_queries_including_edges_occluders_and_visibility() {
    let mut geometry = self::geometry(24);
    // Equal-distance overlapping triangle: original source order wins, including an untagged occluder.
    geometry.vertices.extend_from_within(3..6);
    let target = WorldTarget::Npc {
        index: NpcIndex::new(1),
        instance: NpcInstanceId::new(1),
        definition: NpcDefinitionId::new(1),
        resolved: NpcDefinitionId::new(1),
    };
    geometry.picks.push(PickRange {
        vertices: geometry.vertices.len() - 3..geometry.vertices.len(),
        target,
    });
    let index = ScenePickIndex::new(&geometry).unwrap();
    assert!(index.stats().resident_bytes < geometry.vertices.len() * 2);
    for top_level in [0, 3] {
        let mut visibility = SceneVisibility::everything();
        visibility.top_level = top_level;
        for x in -1_i16..50 {
            for z in -1_i16..50 {
                let origin = [f32::from(x) * 32., 1500., f32::from(z) * 32.];
                for direction in [[0., -1., 0.], [0.03125, -1., -0.0625], [0., 1., 0.]] {
                    compare(&geometry, &index, origin, direction, &visibility);
                }
            }
        }
    }
    // Mismatched allocations and invalid/large rays use the established fallback semantics.
    let other = self::geometry(2);
    compare(
        &other,
        &index,
        [20., 10., 20.],
        [0., -1., 0.],
        &SceneVisibility::everything(),
    );
    for origin in [[f32::NAN, 1., 1.], [20_000_000., 100., 10.]] {
        compare(
            &geometry,
            &index,
            origin,
            [0., -1., 0.],
            &SceneVisibility::everything(),
        );
    }
}
#[test]
fn indexed_terrain_matches_prefix_plane_and_roof_contracts() {
    let mesh = super::tests::mesh(0);
    let descriptor = super::tests::descriptor();
    let index = ScenePickIndex::new(&mesh.geometry).unwrap();
    let mut visibility = SceneVisibility::everything();
    for level in [0, 3] {
        visibility.top_level = level;
        for x in 100_i16..280 {
            let origin = [f32::from(x), 500., 192.];
            let direction = [0., -1., 0.];
            assert_eq!(
                index.pick_terrain(
                    &mesh,
                    descriptor,
                    Plane::MAX,
                    origin,
                    direction,
                    &visibility
                ),
                pick_terrain(
                    &mesh,
                    descriptor,
                    Plane::MAX,
                    origin,
                    direction,
                    &visibility
                )
            );
        }
    }
    let mut mesh = mesh;
    mesh.stats.terrain_triangles = 0;
    assert_eq!(
        index.pick_terrain(
            &mesh,
            descriptor,
            Plane::MIN,
            [192., 500., 192.],
            [0., -1., 0.],
            &visibility
        ),
        None
    );
}
#[test]
#[ignore = "optimized CPU trace; run explicitly on the comparison machine"]
fn static_picking_trace() {
    let geometry = geometry(450); // 202,500 dispersed triangles; no copied triangle data in the index.
    let started = Instant::now();
    let index = ScenePickIndex::new(&geometry).unwrap();
    eprintln!(
        "index build={:?}, stats={:?}",
        started.elapsed(),
        index.stats()
    );
    let visibility = SceneVisibility::everything();
    for indexed in [false, true] {
        let mut samples = Vec::new();
        for step in 0..600 {
            let origin = [
                f32::from(u16::try_from((step * 173) % 28000).unwrap()),
                1200.,
                f32::from(u16::try_from((step * 91) % 28000).unwrap()),
            ];
            let now = Instant::now();
            let hit = if indexed {
                index.pick_surface(&geometry, origin, [0., -1., 0.], &visibility)
            } else {
                pick_surface(&geometry, origin, [0., -1., 0.], &visibility)
            };
            std::hint::black_box(hit);
            samples.push(now.elapsed().as_secs_f64() * 1000.);
        }
        samples.sort_by(f64::total_cmp);
        eprintln!(
            "indexed={indexed} picking_ms p50={:.4}, p95={:.4}, max={:.4}",
            samples[300], samples[570], samples[599]
        );
    }
}
