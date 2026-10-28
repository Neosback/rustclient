//! Cache-backed whole-scene visibility diagnostics, outside the ordinary unit suite.
use rustclient_cache::{Cache, CacheProfile};
use rustclient_model::{ClientCycle, Plane, WorldTile};
use rustclient_platform_native::DirectoryBlobs;
use rustclient_renderer::{
    CameraInput, CameraTarget, GameplayCameraController, GpuSettings, SurfaceSize,
};
use rustclient_scene::{SceneAsset, WorldMesh};
use std::path::Path;

type Result<T> = std::result::Result<T, Box<dyn std::error::Error>>;
fn render(
    mesh: &WorldMesh,
    camera: GameplayCameraController,
    settings: GpuSettings,
) -> Result<Vec<u8>> {
    Ok(pollster::block_on(
        rustclient_renderer::capture_world_with(
            mesh,
            camera,
            SurfaceSize::new(1024, 768),
            ClientCycle::ZERO,
            settings,
            |device| {
                let _ = device.poll(wgpu::PollType::wait_indefinitely());
            },
        ),
    )?)
}
fn save(path: &Path, name: &str, pixels: &[u8]) -> Result<()> {
    image::save_buffer(
        path.join(format!("{name}.png")),
        pixels,
        1024,
        768,
        image::ColorType::Rgba8,
    )?;
    Ok(())
}
pub fn capture(cache: &str, path: &Path) -> Result<()> {
    let cache = Cache::open(DirectoryBlobs::open(cache)?)?;
    let (asset, mesh, origin, spawn) = fixture(&cache)?;
    let unrelated = mesh
        .geometry
        .vertices
        .iter()
        .filter(|v| v.draw_tile.unrelated())
        .count();
    let target = CameraTarget {
        x: (spawn.x - origin.x) * 128 + 64,
        height: asset.ground_height(
            spawn.plane,
            [
                (spawn.x - origin.x) * 128 + 64,
                (spawn.y - origin.y) * 128 + 64,
            ],
        )?,
        z: (spawn.y - origin.y) * 128 + 64,
        terrain_drop: 0,
    };
    let mut camera = GameplayCameraController::new(target);
    for _ in 0..200 {
        camera.step(target, CameraInput::default());
    }
    let defaults = GpuSettings::default();
    let hidden = render(&mesh, camera, defaults)?;
    let shown = render(
        &mesh,
        camera,
        GpuSettings {
            hide_unrelated_maps: false,
            ..defaults
        },
    )?;
    let region_delta = hidden.iter().zip(&shown).filter(|(a, b)| a != b).count();
    if region_delta == 0 {
        return Err("region toggle changed no GPU pixels".into());
    }
    save(path, "regions-hidden", &hidden)?;
    save(path, "regions-shown", &shown)?;
    let short = render(
        &mesh,
        camera,
        GpuSettings {
            draw_distance: 0,
            hide_unrelated_maps: false,
            ..defaults
        },
    )?;
    let distance_delta = shown.iter().zip(&short).filter(|(a, b)| a != b).count();
    save(path, "zones-zero", &short)?;
    assert_eq!(
        shown,
        render(
            &mesh,
            camera,
            GpuSettings {
                hide_unrelated_maps: false,
                ..defaults
            }
        )?,
        "visibility GPU reconstruction changed pixels"
    );
    println!(
        "visibility: {unrelated}/{} unrelated vertices; region toggle {region_delta} changed channels; zero-distance {distance_delta}; fresh reconstruction byte-identical",
        mesh.geometry.vertices.len()
    );
    Ok(())
}

fn fixture(cache: &Cache<DirectoryBlobs>) -> Result<(SceneAsset, WorldMesh, WorldTile, WorldTile)> {
    // Sparse world squares use the adapter's source-defined void initialization. A corrupt
    // declared archive still fails this diagnostic; no error is swallowed to choose another scene.
    for (x, y) in [
        (2832, 4352),
        (2880, 4352),
        (2856, 4368),
        (2256, 4400),
        (2304, 4400),
        (2368, 4400),
        (2400, 4352),
        (2400, 4360),
        (2368, 4352),
        (2256, 4352),
        (2304, 4304),
        (2304, 4448),
    ] {
        let origin = WorldTile::new(x, y, Plane::MIN);
        let spawn = WorldTile::new(x + 52, y + 52, Plane::MIN);
        let sources = CacheProfile::OpenRune240.load_world(cache, origin, 104, 104, |_| {})?;
        let asset = SceneAsset::new(sources, origin, 104, 104)?;
        let mesh = rustclient_scene::build_mesh(&asset, asset.descriptor(), spawn)?;
        let unrelated = mesh
            .geometry
            .vertices
            .iter()
            .filter(|v| v.draw_tile.unrelated())
            .count();
        let absent = asset
            .sources()
            .squares
            .iter()
            .filter(|square| square.source == rustclient_cache::world::MapSquareSource::Absent)
            .count();
        println!(
            "visibility fixture {x},{y}: {absent} absent regions, {unrelated}/{} unrelated vertices",
            mesh.geometry.vertices.len()
        );
        if unrelated > 0 && unrelated < mesh.geometry.vertices.len() {
            println!(
                "visibility fixture selected: origin {x},{y}; spawn {},{}",
                spawn.x, spawn.y
            );
            return Ok((asset, mesh, origin, spawn));
        }
    }
    Err("no mixed-group visible geometry among the bounded visibility fixtures".into())
}

/// An explicitly synthetic quad tests the real vertex-input, uniform and draw pipeline without
/// requiring sparse underground cache archives. It is not a Java/cache visual-parity fixture.
pub fn shader_contract(path: &Path) -> Result<()> {
    use rustclient_scene::{DrawTile, SceneGeometry, SceneStats, SceneVertex};
    let draw_tile = DrawTile::scenery([4, 4], false);
    let geometry = SceneGeometry {
        vertices: [
            [0, 0, 0, 0],
            [1024, 0, 0, 0],
            [0, 0, 1024, 0],
            [1024, 0, 0, 0],
            [1024, 0, 1024, 0],
            [0, 0, 1024, 0],
        ]
        .map(|position| SceneVertex {
            position,
            abhsl: 20000,
            texture_uv: [0; 4],
            draw_tile,
        })
        .to_vec(),
        opaque: (0..6).collect(),
        models: vec![rustclient_scene::SceneModel {
            vertices: 0..6,
            depth_policy: rustclient_model::ModelDepthPolicy::DepthWrite,
            sort_source: None,
        }],
        ..SceneGeometry::default()
    };
    let mesh = WorldMesh {
        asset: rustclient_model::SceneAssetId::new([9; 32]),
        geometry,
        dynamic: SceneGeometry::default(),
        focus: [4., 0., 4.],
        stats: SceneStats {
            terrain_triangles: 2,
            ..SceneStats::default()
        },
        textures: std::collections::BTreeMap::default(),
        roofs: std::sync::Arc::default(),
    };
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
    let defaults = GpuSettings::default();
    let hidden = render(&mesh, camera, defaults)?;
    let shown = render(
        &mesh,
        camera,
        GpuSettings {
            hide_unrelated_maps: false,
            ..defaults
        },
    )?;
    assert!(
        hidden.as_chunks::<4>().0.iter().all(|p| p[..3] == [0; 3]),
        "unrelated quad survived admission"
    );
    assert!(
        shown.as_chunks::<4>().0.iter().any(|p| p[..3] != [0; 3]),
        "synthetic admission quad never rendered"
    );
    save(path, "admission-hidden", &hidden)?;
    save(path, "admission-shown", &shown)?;
    println!(
        "native vertex admission: synthetic unrelated quad hidden/restored through the actual world pipeline"
    );
    Ok(())
}
