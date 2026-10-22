//! Deterministic CPU world-terrain picking using the same camera projection as the GPU.
//!
//! Picking is projection-derived evidence only. The returned [`WorldTile`] is a semantic
//! coordinate; this module owns no gameplay authority and performs no protocol work.
use crate::{CameraViewport, GameplayCameraController};
use glam::{Mat4, Vec3};
use rustclient_model::{Plane, WorldTile};
use rustclient_scene::SceneAsset;

const FINE_PER_TILE: i32 = 128;
const HIT_EPSILON: f32 = 0.000_1;

/// Pick the nearest visible terrain surface for one logical viewport pointer.
///
/// `viewport_size` and `pointer` are logical pixels. Platform adapters must divide physical
/// coordinates by their scale factor before calling this function.
#[must_use]
#[expect(
    clippy::cast_precision_loss,
    reason = "scene-local fine coordinates and bounded viewport dimensions are exact enough in f32"
)]
pub fn pick_terrain(
    asset: &SceneAsset,
    plane: Plane,
    camera: GameplayCameraController,
    viewport_size: [i32; 2],
    pointer: [f32; 2],
) -> Option<WorldTile> {
    let (ray_origin, ray_direction) = pointer_ray(camera, viewport_size, pointer)?;
    let descriptor = asset.descriptor();
    let width = i32::try_from(descriptor.width()).ok()?;
    let height = i32::try_from(descriptor.height()).ok()?;
    let origin = descriptor.origin();
    let mut nearest: Option<(f32, WorldTile)> = None;

    // The scene mesh intentionally leaves its one-tile height border undrawn. Match that
    // footprint so pointer evidence never selects an invisible border tile.
    for local_x in 1..width.saturating_sub(1) {
        for local_y in 1..height.saturating_sub(1) {
            let world_x = origin.x.checked_add(local_x)?;
            let world_y = origin.y.checked_add(local_y)?;
            let tile = asset.tile(plane, world_x, world_y)?;
            if tile.underlay.is_none() && tile.overlay.is_none() {
                continue;
            }

            let x0 = local_x.checked_mul(FINE_PER_TILE)?;
            let z0 = local_y.checked_mul(FINE_PER_TILE)?;
            let x1 = x0.checked_add(FINE_PER_TILE)?;
            let z1 = z0.checked_add(FINE_PER_TILE)?;
            let h00 = asset.ground_height(plane, [x0, z0]).ok()?;
            let h10 = asset.ground_height(plane, [x1, z0]).ok()?;
            let h11 = asset.ground_height(plane, [x1, z1]).ok()?;
            let h01 = asset.ground_height(plane, [x0, z1]).ok()?;

            let p00 = Vec3::new(x0 as f32, h00 as f32, z0 as f32);
            let p10 = Vec3::new(x1 as f32, h10 as f32, z0 as f32);
            let p11 = Vec3::new(x1 as f32, h11 as f32, z1 as f32);
            let p01 = Vec3::new(x0 as f32, h01 as f32, z1 as f32);
            let candidate = ray_triangle(ray_origin, ray_direction, p11, p01, p10)
                .into_iter()
                .chain(ray_triangle(ray_origin, ray_direction, p00, p10, p01))
                .min_by(f32::total_cmp);

            if let Some(distance) = candidate
                && nearest.is_none_or(|(best, _)| distance < best)
            {
                nearest = Some((distance, WorldTile::new(world_x, world_y, plane)));
            }
        }
    }

    nearest.map(|(_, tile)| tile)
}

#[expect(
    clippy::cast_precision_loss,
    reason = "bounded camera and viewport values are intentionally projected to f32"
)]
fn pointer_ray(
    camera: GameplayCameraController,
    viewport_size: [i32; 2],
    pointer: [f32; 2],
) -> Option<(Vec3, Vec3)> {
    let viewport = CameraViewport::new(viewport_size[0], viewport_size[1]);
    let width = viewport_size[0].clamp(1, 16_384) as f32;
    let height = viewport_size[1].clamp(1, 16_384) as f32;
    if pointer[0] < 0.0 || pointer[1] < 0.0 || pointer[0] >= width || pointer[1] >= height {
        return None;
    }

    let pose = camera.pose(viewport);
    let zoom = pose.zoom as f32;
    if zoom <= 0.0 {
        return None;
    }
    let ndc_x = pointer[0].mul_add(2.0 / width, -1.0);
    let ndc_y = 1.0 - pointer[1] * 2.0 / height;
    let camera_direction = Vec3::new(
        ndc_x * width / (2.0 * zoom),
        -ndc_y * height / (2.0 * zoom),
        1.0,
    );
    let pitch = f32::from(pose.pitch) * std::f32::consts::TAU / 2048.0;
    let yaw = f32::from(pose.yaw) * std::f32::consts::TAU / 2048.0;
    let inverse_view = Mat4::from_rotation_y(-yaw) * Mat4::from_rotation_x(-pitch);
    let direction = inverse_view
        .transform_vector3(camera_direction)
        .normalize_or_zero();
    if direction == Vec3::ZERO {
        return None;
    }
    let origin = Vec3::from_array(pose.position.map(|value| value as f32));
    Some((origin, direction))
}

fn ray_triangle(
    origin: Vec3,
    direction: Vec3,
    vertex_a: Vec3,
    vertex_b: Vec3,
    vertex_c: Vec3,
) -> Option<f32> {
    let first_edge = vertex_b - vertex_a;
    let second_edge = vertex_c - vertex_a;
    let direction_cross_second = direction.cross(second_edge);
    let determinant = first_edge.dot(direction_cross_second);
    if determinant.abs() <= HIT_EPSILON {
        return None;
    }
    let inverse_determinant = determinant.recip();
    let origin_offset = origin - vertex_a;
    let barycentric_u = origin_offset.dot(direction_cross_second) * inverse_determinant;
    if !(0.0..=1.0).contains(&barycentric_u) {
        return None;
    }
    let offset_cross_first = origin_offset.cross(first_edge);
    let barycentric_v = direction.dot(offset_cross_first) * inverse_determinant;
    if barycentric_v < 0.0 || barycentric_u + barycentric_v > 1.0 {
        return None;
    }
    let distance = second_edge.dot(offset_cross_first) * inverse_determinant;
    (distance > HIT_EPSILON).then_some(distance)
}

#[cfg(test)]
mod tests {
    use super::*;
    use crate::CameraTarget;

    #[test]
    fn triangle_hit_is_two_sided_and_rejects_points_behind_ray() {
        let vertex_a = Vec3::new(-1.0, 0.0, 2.0);
        let vertex_b = Vec3::new(1.0, 0.0, 2.0);
        let vertex_c = Vec3::new(0.0, 1.0, 2.0);
        assert_eq!(
            ray_triangle(Vec3::ZERO, Vec3::Z, vertex_a, vertex_b, vertex_c),
            Some(2.0)
        );
        assert_eq!(
            ray_triangle(Vec3::ZERO, -Vec3::Z, vertex_a, vertex_b, vertex_c),
            None
        );
    }

    #[test]
    fn pointer_ray_rejects_coordinates_outside_logical_viewport() {
        let camera = GameplayCameraController::new(CameraTarget {
            x: 1024,
            height: 0,
            z: 1024,
            terrain_drop: 0,
        });
        assert!(pointer_ray(camera, [765, 503], [-1.0, 100.0]).is_none());
        assert!(pointer_ray(camera, [765, 503], [765.0, 100.0]).is_none());
        assert!(pointer_ray(camera, [765, 503], [382.0, 251.0]).is_some());
    }
}
