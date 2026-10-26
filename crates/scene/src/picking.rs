//! Pointer queries over the exact retained CPU terrain triangles.
use crate::{RoofVisibility, WorldMesh};
use rustclient_model::{SceneDescriptor, WorldTile};

/// Pick the closest terrain triangle using the ray supplied by the rendering
/// camera. Coordinates are scene-local fine units, matching packed vertices. Triangles above
/// `visibility` (hidden roofs and upper floors) are not pickable.
#[must_use]
#[expect(
    clippy::cast_possible_truncation,
    reason = "intersection is checked against bounded scene extents"
)]
pub fn pick_terrain(
    mesh: &WorldMesh,
    descriptor: SceneDescriptor,
    origin: [f32; 3],
    direction: [f32; 3],
    visibility: &RoofVisibility,
) -> Option<WorldTile> {
    if mesh.asset != descriptor.id() || origin.iter().chain(&direction).any(|n| !n.is_finite()) {
        return None;
    }
    let triangle_count = usize::try_from(mesh.stats.terrain_triangles).ok()?;
    let mut nearest = f32::INFINITY;
    for triangle in mesh.geometry.vertices.chunks_exact(3).take(triangle_count) {
        if !visibility.shows(triangle[0].render_tag()) {
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
    let x = (origin[0] + direction[0] * nearest) / 128.;
    let y = (origin[2] + direction[2] * nearest) / 128.;
    let base = descriptor.origin();
    let tile = WorldTile::new(
        base.x.checked_add(x.floor() as i32)?,
        base.y.checked_add(y.floor() as i32)?,
        base.plane,
    );
    descriptor.contains(tile).then_some(tile)
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
    visibility: &RoofVisibility,
) -> Option<SurfaceHit> {
    if origin.iter().chain(&direction).any(|v| !v.is_finite()) {
        return None;
    }
    let mut hit = None;
    for (i, triangle) in geometry.vertices.chunks_exact(3).enumerate() {
        if triangle.iter().all(|v| v.abhsl >> 24 == 255)
            || !visibility.shows(triangle[0].render_tag())
        {
            continue;
        }
        let points = std::array::from_fn(|i| {
            std::array::from_fn(|axis| f32::from(triangle[i].position[axis]))
        });
        if let Some(distance) = intersection(points, origin, direction)
            && hit.is_none_or(|old: SurfaceHit| distance < old.distance)
        {
            let vertex = i * 3;
            let range = geometry
                .picks
                .partition_point(|range| range.vertices.end <= vertex);
            let target = geometry
                .picks
                .get(range)
                .filter(|r| r.vertices.contains(&vertex))
                .map(|r| r.target);
            hit = Some(SurfaceHit { distance, target });
        }
    }
    hit
}
