//! Explicitly synthetic face-priority and deferred-depth diagnostics, outside ordinary unit tests.
use rustclient_model::{ModelDepthPolicy, ModelPoint, SceneAssetId};
use rustclient_renderer::{
    CameraInput, CameraTarget, CameraViewport, GameplayCameraController, SurfaceSize,
};
use rustclient_scene::{
    DrawTile, ModelSortSource, SceneGeometry, SceneModel, SceneStats, SceneVertex, WorldMesh,
};
use std::path::Path;
type Result<T> = std::result::Result<T, Box<dyn std::error::Error>>;
const SIZE: u32 = 256;

#[allow(clippy::cast_precision_loss, clippy::cast_possible_truncation)]
fn triangle(
    camera: GameplayCameraController,
    distance: i32,
    color: u32,
    priority: i16,
) -> [SceneVertex; 3] {
    let pose = camera.pose(CameraViewport::new(256, 256));
    let pitch = f64::from(pose.pitch) * std::f64::consts::TAU / 2048.;
    let (sin, cos) = pitch.sin_cos();
    // Invert the view rotation, keeping all three triangles aligned in screen space.
    let extent = f64::from(distance) * 0.3;
    [(-extent, extent), (extent, extent), (0., -extent)].map(|(x, y)| SceneVertex {
        position: [
            (f64::from(pose.position[0]) + x).round() as i16,
            (f64::from(pose.position[1]) + cos * y + sin * f64::from(distance)).round() as i16,
            (f64::from(pose.position[2]) - sin * y + cos * f64::from(distance)).round() as i16,
            priority,
        ],
        abhsl: color,
        texture_uv: [0; 4],
        draw_tile: DrawTile::actor([4, 4]),
    })
}
fn render(mesh: &WorldMesh, camera: GameplayCameraController) -> Result<Vec<u8>> {
    Ok(pollster::block_on(rustclient_renderer::capture_world(
        mesh,
        camera,
        SurfaceSize::new(SIZE, SIZE),
        |device| {
            let _ = device.poll(wgpu::PollType::wait_indefinitely());
        },
    ))?)
}
fn center(pixels: &[u8]) -> [u8; 4] {
    pixels[((SIZE / 2 * SIZE + SIZE / 2) * 4) as usize..][..4]
        .try_into()
        .expect("bounded capture")
}
fn save(path: &Path, name: &str, pixels: &[u8]) -> Result<()> {
    image::save_buffer(
        path.join(format!("{name}.png")),
        pixels,
        SIZE,
        SIZE,
        image::ColorType::Rgba8,
    )?;
    Ok(())
}
#[expect(
    clippy::too_many_lines,
    reason = "single diagnostic scene setup, render and capture lifecycle"
)]
pub fn capture(path: &Path) -> Result<()> {
    let target = CameraTarget {
        x: 512,
        height: 0,
        z: 512,
        terrain_drop: 0,
    };
    let mut camera = GameplayCameraController::new(target);
    for _ in 0..200 {
        camera.step(target, CameraInput::default());
    }
    let blue = (42 << 10) | (7 << 7) | 64;
    let red = (7 << 7) | 64;
    let green = (21 << 10) | (7 << 7) | 64;
    let mut geometry = SceneGeometry::default();
    geometry.vertices.extend(triangle(camera, 600, blue, 9));
    geometry.vertices.extend(triangle(camera, 400, red, 0));
    geometry.opaque.extend(0..6);
    geometry.models.push(SceneModel {
        vertices: 0..6,
        depth_policy: ModelDepthPolicy::DepthWrite,
        sort_source: None,
    });
    let origin = ModelPoint::new(512, 0, 512);
    let placed: Vec<_> = geometry
        .vertices
        .iter()
        .map(|v| {
            ModelPoint::new(
                i32::from(v.position[0]),
                i32::from(v.position[1]),
                i32::from(v.position[2]),
            )
        })
        .collect();
    let local: Vec<_> = placed
        .iter()
        .map(|p| ModelPoint::new(p.x - origin.x, p.y - origin.y, p.z - origin.z))
        .collect();
    geometry.models[0].sort_source = Some(std::sync::Arc::new(ModelSortSource::new(
        origin, &local, placed,
    )?));
    let mut mesh = WorldMesh {
        asset: SceneAssetId::new([7; 32]),
        geometry,
        dynamic: SceneGeometry::default(),
        focus: [4., 0., 4.],
        stats: SceneStats::default(),
        textures: std::collections::BTreeMap::default(),
        roofs: std::sync::Arc::default(),
    };
    let conventional = render(&mesh, camera)?;
    mesh.geometry.models[0].depth_policy = ModelDepthPolicy::SortedDeferredWrite;
    let sorted = render(&mesh, camera)?;
    let conventional_pixel = center(&conventional);
    let sorted_pixel = center(&sorted);
    assert!(
        conventional_pixel[0] > conventional_pixel[2],
        "nearest opaque red: {conventional_pixel:?}"
    );
    assert!(
        sorted_pixel[2] > sorted_pixel[0],
        "source priority-9 blue paints after priority-0 red: {sorted_pixel:?}"
    );
    assert_eq!(
        sorted,
        render(&mesh, camera)?,
        "deferred-depth reconstruction changed pixels"
    );
    save(path, "depth-conventional", &conventional)?;
    save(path, "depth-deferred", &sorted)?;
    // Reversing the submitted faces excludes them before priority thresholds, not just in raster.
    let original = mesh.geometry.clone();
    for triangle in mesh.geometry.vertices.chunks_exact_mut(3) {
        triangle.swap(1, 2);
    }
    let back = render(&mesh, camera)?;
    assert_eq!(center(&back), [0, 0, 0, 255], "reversed faces still draw");
    save(path, "depth-back-face", &back)?;
    mesh.geometry = original.clone();
    // An unreferenced vertex crossing 50 rejects the entire source model.
    let source = std::sync::Arc::make_mut(mesh.geometry.models[0].sort_source.as_mut().unwrap());
    let mut guards = source.vertices.to_vec();
    let eye = camera.pose(CameraViewport::new(256, 256)).position;
    guards.push(ModelPoint::new(eye[0], eye[1], eye[2]));
    source.vertices = guards.into();
    let near = render(&mesh, camera)?;
    assert_eq!(
        center(&near),
        [0, 0, 0, 255],
        "unused near vertex did not reject whole model"
    );
    save(path, "depth-near-model", &near)?;
    mesh.geometry = original;
    // A later green surface between red and blue must see the nearest RED depth, not the
    // blue color's distance or the unchanged clear depth from the initial color-only pass.
    mesh.geometry
        .vertices
        .extend(triangle(camera, 500, green, 0));
    mesh.geometry.opaque.extend(6..9);
    mesh.geometry.models.push(SceneModel {
        vertices: 6..9,
        depth_policy: ModelDepthPolicy::DepthWrite,
        sort_source: None,
    });
    let later = render(&mesh, camera)?;
    assert_eq!(
        center(&later),
        sorted_pixel,
        "later geometry ignored the second depth-only draw"
    );
    save(path, "depth-later-occluded", &later)?;
    // An existing wall must still block both deferred passes: the policy changes depth writes,
    // never the depth test. Put the wall in the earlier resident stream and the actor in dynamic.
    mesh.dynamic = mesh.geometry.clone();
    mesh.dynamic.vertices.truncate(6);
    mesh.dynamic.opaque.truncate(6);
    mesh.dynamic.models.truncate(1);
    mesh.geometry = SceneGeometry {
        vertices: triangle(camera, 300, green, 0).to_vec(),
        opaque: vec![0, 1, 2],
        ..Default::default()
    };
    let wall = render(&mesh, camera)?;
    let wall_pixel = center(&wall);
    assert!(
        wall_pixel[1] > wall_pixel[0] && wall_pixel[1] > wall_pixel[2],
        "existing wall failed to occlude actor: {wall_pixel:?}"
    );
    save(path, "depth-existing-wall", &wall)?;
    println!(
        "deferred actor depth: priority color {conventional_pixel:?} -> {sorted_pixel:?}; back faces excluded; unused near vertex rejects whole model; later surface occluded; existing wall occludes; reconstruction byte-identical"
    );
    Ok(())
}
