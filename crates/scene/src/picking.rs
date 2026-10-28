//! Pointer queries over the exact retained CPU terrain triangles.
#[cfg(test)]
mod model_tests;
mod spatial;
#[cfg(test)]
mod spatial_tests;
pub use spatial::{PickIndexStats, ScenePickIndex};

use crate::{SceneVisibility, WorldMesh};
use rustclient_model::{Plane, SceneDescriptor, WorldTile};

/// Pick the closest terrain triangle using the ray supplied by the rendering
/// camera. Coordinates are scene-local fine units, matching packed vertices. Triangles above
/// `visibility` (hidden roofs and upper floors) are not pickable.
#[must_use]
pub fn pick_terrain(
    mesh: &WorldMesh,
    descriptor: SceneDescriptor,
    plane: Plane,
    origin: [f32; 3],
    direction: [f32; 3],
    visibility: &SceneVisibility,
) -> Option<WorldTile> {
    if mesh.asset != descriptor.id() || origin.iter().chain(&direction).any(|n| !n.is_finite()) {
        return None;
    }
    let triangle_count = usize::try_from(mesh.stats.terrain_triangles).ok()?;
    let mut nearest = f32::INFINITY;
    for triangle in mesh.geometry.vertices.chunks_exact(3).take(triangle_count) {
        if !visibility.shows_vertex(&triangle[0]) {
            continue;
        }
        let points = std::array::from_fn(|i| {
            std::array::from_fn(|axis| f32::from(triangle[i].position[axis]))
        });
        if let Some(distance) = intersection(points, origin, direction)
            && distance < nearest
        {
            nearest = distance;
        }
    }
    if !nearest.is_finite() {
        return None;
    }
    terrain_tile(descriptor, plane, origin, direction, nearest)
}

#[expect(
    clippy::cast_possible_truncation,
    reason = "intersection is checked against bounded scene extents"
)]
fn terrain_tile(
    descriptor: SceneDescriptor,
    plane: Plane,
    origin: [f32; 3],
    direction: [f32; 3],
    nearest: f32,
) -> Option<WorldTile> {
    let x = (origin[0] + direction[0] * nearest) / 128.;
    let y = (origin[2] + direction[2] * nearest) / 128.;
    let base = descriptor.origin();
    let tile = WorldTile::new(
        base.x.checked_add(x.floor() as i32)?,
        base.y.checked_add(y.floor() as i32)?,
        plane,
    );
    descriptor.contains_footprint(tile).then_some(tile)
}

#[expect(
    clippy::many_single_char_names,
    reason = "standard Moller-Trumbore barycentric notation"
)]
fn intersection(points: [[f32; 3]; 3], origin: [f32; 3], direction: [f32; 3]) -> Option<f32> {
    let subtract = |a: [f32; 3], b: [f32; 3]| std::array::from_fn::<_, 3, _>(|i| a[i] - b[i]);
    let cross = |a: [f32; 3], b: [f32; 3]| {
        [
            a[1] * b[2] - a[2] * b[1],
            a[2] * b[0] - a[0] * b[2],
            a[0] * b[1] - a[1] * b[0],
        ]
    };
    let dot = |a: [f32; 3], b: [f32; 3]| a[0] * b[0] + a[1] * b[1] + a[2] * b[2];
    let a = subtract(points[1], points[0]);
    let b = subtract(points[2], points[0]);
    let p = cross(direction, b);
    let determinant = dot(a, p);
    if determinant.abs() < 0.0001 {
        return None;
    }
    let t = subtract(origin, points[0]);
    let u = dot(t, p) / determinant;
    if !(0. ..=1.).contains(&u) {
        return None;
    }
    let q = cross(t, a);
    let v = dot(direction, q) / determinant;
    if v < 0. || u + v > 1. {
        return None;
    }
    let distance = dot(b, q) / determinant;
    (distance >= 0.).then_some(distance)
}

/// Closest surface and its captured semantic target, including untagged occluders.
#[derive(Debug, Clone, Copy)]
pub struct SurfaceHit {
    pub distance: f32,
    pub target: Option<rustclient_model::WorldTarget>,
}
#[must_use]
pub fn pick_surface(
    geometry: &crate::SceneGeometry,
    origin: [f32; 3],
    direction: [f32; 3],
    visibility: &SceneVisibility,
) -> Option<SurfaceHit> {
    if origin.iter().chain(&direction).any(|v| !v.is_finite()) {
        return None;
    }
    let mut hit = None;
    let mut model_index = 0;
    let mut admission = (usize::MAX, true);
    for (i, triangle) in geometry.vertices.chunks_exact(3).enumerate() {
        if triangle.iter().all(|v| v.abhsl >> 24 == 255) || !visibility.shows_vertex(&triangle[0]) {
            continue;
        }
        let vertex = i * 3;
        while geometry
            .models
            .get(model_index)
            .is_some_and(|model| model.vertices.end <= vertex)
        {
            model_index += 1;
        }
        let model = geometry
            .models
            .get(model_index)
            .filter(|model| model.vertices.contains(&vertex));
        if let Some(model) = model {
            if admission.0 != model_index {
                admission = (model_index, visibility.shows_model(model));
            }
            if !admission.1 {
                continue;
            }
        }
        let points = [triangle[0], triangle[1], triangle[2]].map(|v| {
            rustclient_model::ModelPoint::new(
                i32::from(v.position[0]),
                i32::from(v.position[1]),
                i32::from(v.position[2]),
            )
        });
        if model.is_some_and(|model| model.sort_source.is_some())
            && visibility
                .model_view
                .is_some_and(|view| !view.front_face(points))
        {
            continue;
        }
        let points = std::array::from_fn(|i| {
            std::array::from_fn(|axis| f32::from(triangle[i].position[axis]))
        });
        if let Some(distance) = intersection(points, origin, direction)
            && hit.is_none_or(|old: SurfaceHit| distance < old.distance)
        {
            hit = Some(surface_hit(geometry, vertex, distance));
        }
    }
    hit
}

fn surface_hit(geometry: &crate::SceneGeometry, vertex: usize, distance: f32) -> SurfaceHit {
    let range = geometry
        .picks
        .partition_point(|range| range.vertices.end <= vertex);
    let target = geometry
        .picks
        .get(range)
        .filter(|r| r.vertices.contains(&vertex))
        .map(|r| r.target);
    SurfaceHit { distance, target }
}

#[cfg(test)]
mod tests {
    use super::*;
    use crate::{DrawTile, RoofVolumes, SceneGeometry, SceneStats, SceneVertex};
    use rustclient_model::{SceneAssetId, WorldTile};
    use std::collections::BTreeMap;
    use std::sync::Arc;

    pub(super) fn mesh(tag: u16) -> WorldMesh {
        let vertex = SceneVertex {
            position: [0; 4],
            abhsl: 0,
            texture_uv: [0, 0, 0, tag],
            draw_tile: DrawTile::scenery([1, 1], true),
        };
        WorldMesh {
            asset: SceneAssetId::new([9; 32]),
            geometry: SceneGeometry {
                vertices: vec![
                    SceneVertex {
                        position: [128, 0, 128, 0],
                        ..vertex
                    },
                    SceneVertex {
                        position: [256, 0, 128, 0],
                        ..vertex
                    },
                    SceneVertex {
                        position: [128, 0, 256, 0],
                        ..vertex
                    },
                ],
                ..SceneGeometry::default()
            },
            dynamic: SceneGeometry::default(),
            focus: [0.; 3],
            stats: SceneStats {
                terrain_triangles: 1,
                ..SceneStats::default()
            },
            textures: BTreeMap::new(),
            roofs: Arc::new(RoofVolumes::default()),
        }
    }

    pub(super) fn descriptor() -> SceneDescriptor {
        SceneDescriptor::new(
            SceneAssetId::new([9; 32]),
            WorldTile::new(10, 20, Plane::MIN),
            4,
            4,
        )
        .unwrap()
    }

    #[test]
    fn picking_returns_the_active_plane_not_the_descriptor_plane() {
        let hit = pick_terrain(
            &mesh(0),
            descriptor(),
            Plane::MAX,
            [192., 1000., 192.],
            [0., -1., 0.],
            &SceneVisibility::everything(),
        );
        assert_eq!(hit, Some(WorldTile::new(11, 21, Plane::MAX)));
    }

    #[test]
    fn picking_respects_hidden_upper_geometry() {
        let mut visibility = SceneVisibility::everything();
        visibility.top_level = 0;
        assert_eq!(
            pick_terrain(
                &mesh(1),
                descriptor(),
                Plane::MIN,
                [192., 1000., 192.],
                [0., -1., 0.],
                &visibility,
            ),
            None
        );
    }
}
