#[path = "scene_probe/performance.rs"]
mod performance;
#[path = "scene_probe/updates.rs"]
mod updates;
use rustclient_cache::{Cache, CacheProfile};
use rustclient_game_state::{AuthenticatedPlayer, GameState, InitialPlacement, SemanticOperation};
use rustclient_model::{Plane, WorldTile};
use rustclient_model::{PlayerIndex, Revision, ServerProfileId};
use rustclient_platform_native::DirectoryBlobs;
#[expect(
    clippy::too_many_lines,
    clippy::cast_possible_truncation,
    reason = "explicit development capture workflow with bounded scene fine coordinates"
)]
fn main() -> Result<(), Box<dyn std::error::Error>> {
    let path = std::env::args().nth(1).ok_or("cache directory required")?;
    let cache = Cache::open(DirectoryBlobs::open(path)?)?;
    let started = std::time::Instant::now();
    let assets = CacheProfile::OpenRune240.load_world(
        &cache,
        WorldTile::new(3168, 3168, Plane::MIN),
        104,
        104,
        |s| {
            println!(
                "{:>7.0} ms loading {s}",
                started.elapsed().as_secs_f64() * 1000.
            );
        },
    )?;
    println!(
        "{:>7.0} ms world assets",
        started.elapsed().as_secs_f64() * 1000.
    );
    if std::env::var_os("RUSTCLIENT_PROBE_NEIGHBOUR").is_some() {
        // A second window on the same open cache: what a region change costs once warm.
        let neighbour = std::time::Instant::now();
        let next = CacheProfile::OpenRune240.load_world(
            &cache,
            WorldTile::new(3232, 3168, Plane::MIN),
            104,
            104,
            |_| {},
        )?;
        println!(
            "{:>7.0} ms neighbouring window on the warm cache (models {})",
            neighbour.elapsed().as_secs_f64() * 1000.,
            next.models.len()
        );
        let again = CacheProfile::OpenRune240.load_world(
            &cache,
            WorldTile::new(3168, 3168, Plane::MIN),
            104,
            104,
            |_| {},
        )?;
        assert_eq!(
            again.content_hash, assets.content_hash,
            "a warm reload must reproduce the cold content hash"
        );
        println!("warm reload of the first window reproduces its content hash");
    }
    println!(
        "squares {} ({} absent), locations {}, objects {}, models {}",
        assets.squares.len(),
        assets
            .squares
            .iter()
            .filter(|s| s.source == rustclient_cache::world::MapSquareSource::Absent)
            .count(),
        assets
            .squares
            .iter()
            .map(|s| s.locations.len())
            .sum::<usize>(),
        assets.objects.len(),
        assets.models.len()
    );
    for warning in &assets.size_hint_warnings {
        println!("{warning}");
    }
    let scene = rustclient_scene::SceneAsset::new(
        assets,
        WorldTile::new(3168, 3168, Plane::MIN),
        104,
        104,
    )?;
    println!(
        "{:>7.0} ms scene asset",
        started.elapsed().as_secs_f64() * 1000.
    );
    let state = placed_state(scene.descriptor())?;
    let mesh = rustclient_scene::build_mesh(
        &scene,
        state.loaded_scene().ok_or("scene commit missing")?,
        state
            .initial_placement()
            .ok_or("placement missing")?
            .position,
    )?;
    println!(
        "{:>7.0} ms mesh built",
        started.elapsed().as_secs_f64() * 1000.
    );
    let fingerprint = state.semantic_fingerprint();
    println!(
        "mesh: {:?}; {} vertices, {} bytes",
        mesh.stats,
        mesh.vertex_count(),
        mesh.vertex_count() * std::mem::size_of::<rustclient_scene::SceneVertex>()
    );
    if std::env::args().nth(2).as_deref() == Some("--updates-staging") {
        for sample in rustclient_renderer::measure_world_staging(&mesh.dynamic) {
            println!(
                "staging retained={} repeats=256 vertices={} cpu_ms={:.4} upload_requests={}",
                sample.retained,
                mesh.dynamic.vertices.len(),
                sample.cpu.as_secs_f64() * 1000.,
                sample.upload_requests
            );
        }
        return Ok(());
    }
    if std::env::args().nth(2).as_deref() == Some("--updates-prepared") {
        for (index, sample) in
            pollster::block_on(rustclient_renderer::measure_prepared_world_updates(&mesh))?
                .into_iter()
                .enumerate()
        {
            println!(
                "prepared sample={index} inline_enqueue_ms={:.4} prepare_ms={:.4} enqueue_ms={:.4} geometry_buffer_bytes={}",
                sample.inline_enqueue.as_secs_f64() * 1000.,
                sample.prepare.as_secs_f64() * 1000.,
                sample.enqueue.as_secs_f64() * 1000.,
                sample.geometry_buffer_bytes
            );
        }
        return Ok(());
    }
    if std::env::args().nth(2).as_deref() == Some("--updates-gpu") {
        let samples = pollster::block_on(rustclient_renderer::measure_world_replacements(&mesh))?;
        for (index, elapsed) in samples.into_iter().enumerate() {
            println!(
                "GPU install sample={index} cpu_enqueue_ms={:.4}",
                elapsed.as_secs_f64() * 1000.
            );
        }
        return Ok(());
    }
    if std::env::args().nth(2).as_deref() == Some("--updates") {
        updates::run(
            &scene,
            state.loaded_scene().ok_or("scene commit missing")?,
            &state,
        )?;
        return Ok(());
    }
    if std::env::args().nth(2).as_deref() == Some("--performance") {
        performance::run(&mesh)?;
        return Ok(());
    }
    if let Some(path) = std::env::args().nth(2) {
        let path = std::path::PathBuf::from(path);
        std::fs::create_dir_all(&path)?;
        let textures = path.join("textures");
        std::fs::create_dir_all(&textures)?;
        for (id, texture) in mesh.textures.iter() {
            std::fs::write(
                textures.join(format!("{}.rgba", id.get())),
                texture.image.pixels(),
            )?;
        }
        write_minimap_captures(&cache, &scene, &path)?;
        let target = rustclient_renderer::CameraTarget {
            x: (mesh.focus[0] * 128.) as i32,
            height: -(mesh.focus[1] * 128.) as i32,
            z: (mesh.focus[2] * 128.) as i32,
            terrain_drop: 0,
        };
        let mut camera = rustclient_renderer::GameplayCameraController::new(target);
        for _ in 0..200 {
            camera.step(target, rustclient_renderer::CameraInput::default());
        }
        let size = rustclient_renderer::SurfaceSize::new(1280, 720);
        println!(
            "reference-camera-pose {:?}",
            camera.pose(rustclient_renderer::CameraViewport::new(1280, 720))
        );
        let capture = |camera, size| {
            pollster::block_on(rustclient_renderer::capture_world(
                &mesh,
                camera,
                size,
                |device| {
                    let _ = device.poll(wgpu::PollType::wait_indefinitely());
                },
            ))
        };
        let pixels = capture(camera, size)?;
        image::save_buffer(
            path.join("spawn.png"),
            &pixels,
            size.width,
            size.height,
            image::ColorType::Rgba8,
        )?;
        let rebuilt = capture(camera, size)?;
        if pixels != rebuilt {
            return Err("fresh GPU reconstruction changed world pixels".into());
        }
        for _ in 0..100 {
            camera.step(
                target,
                rustclient_renderer::CameraInput {
                    right: true,
                    up: true,
                    ..Default::default()
                },
            );
        }
        let size = rustclient_renderer::SurfaceSize::new(1530, 1006);
        let turned = capture(camera, size)?;
        image::save_buffer(
            path.join("orbit-hidpi.png"),
            &turned,
            size.width,
            size.height,
            image::ColorType::Rgba8,
        )?;
        assert_eq!(fingerprint, state.semantic_fingerprint());
        println!("fresh GPU reconstruction: byte-identical; second camera/size captured");
        // GPU plugin options: each capture changes one setting from the source defaults.
        let defaults = rustclient_renderer::GpuSettings::default();
        let size = rustclient_renderer::SurfaceSize::new(1280, 720);
        let render = |settings| {
            pollster::block_on(rustclient_renderer::capture_world_with(
                &mesh,
                camera,
                size,
                rustclient_model::ClientCycle::ZERO,
                settings,
                |device| {
                    let _ = device.poll(wgpu::PollType::wait_indefinitely());
                },
            ))
        };
        let baseline = render(defaults)?;
        let neutral = render(rustclient_renderer::GpuSettings {
            color_blind: rustclient_renderer::ColorBlind::Deuteranopia,
            color_blind_intensity: 0,
            ..defaults
        })?;
        if neutral != baseline {
            return Err("zero-intensity color-blind correction changed pixels".into());
        }
        let corrected = render(rustclient_renderer::GpuSettings {
            color_blind: rustclient_renderer::ColorBlind::Deuteranopia,
            ..defaults
        })?;
        let changed = corrected
            .iter()
            .zip(&baseline)
            .filter(|(a, b)| a != b)
            .count();
        if changed == 0 {
            return Err("color-blind correction left every pixel unchanged".into());
        }
        println!(
            "deuteranopia changed {changed} of {} channel bytes",
            baseline.len()
        );
        for (name, settings) in [
            (
                "distance-zero",
                rustclient_renderer::GpuSettings {
                    draw_distance: 0,
                    ..defaults
                },
            ),
            (
                "fog",
                rustclient_renderer::GpuSettings {
                    fog_depth: 12,
                    draw_distance: 20,
                    ..defaults
                },
            ),
            (
                "deuteranopia",
                rustclient_renderer::GpuSettings {
                    color_blind: rustclient_renderer::ColorBlind::Deuteranopia,
                    ..defaults
                },
            ),
            (
                "msaa-off-aniso-16",
                rustclient_renderer::GpuSettings {
                    anti_aliasing: rustclient_renderer::AntiAliasing::Disabled,
                    anisotropy: 16,
                    ..defaults
                },
            ),
        ] {
            let pixels = pollster::block_on(rustclient_renderer::capture_world_with(
                &mesh,
                camera,
                size,
                rustclient_model::ClientCycle::ZERO,
                settings,
                |device| {
                    let _ = device.poll(wgpu::PollType::wait_indefinitely());
                },
            ))?;
            if name == "distance-zero" && pixels == baseline {
                return Err("zero draw distance did not change GPU pixels".into());
            }
            image::save_buffer(
                path.join(format!("gpu-{name}.png")),
                &pixels,
                size.width,
                size.height,
                image::ColorType::Rgba8,
            )?;
            println!("captured gpu-{name}.png");
        }
    }
    println!(
        "scene asset {:?}; state fingerprint {}",
        scene.descriptor().id(),
        fingerprint
    );
    Ok(())
}

fn placed_state(
    descriptor: rustclient_model::SceneDescriptor,
) -> Result<GameState, Box<dyn std::error::Error>> {
    let mut state = GameState::new();
    state.apply_operation(SemanticOperation::AuthenticateSession {
        revision: Revision::with_subrevision(240, 2),
        player: AuthenticatedPlayer {
            profile: ServerProfileId::new(1),
            player: PlayerIndex::new(1),
            member: false,
            staff_level: 0,
        },
    })?;
    state.apply_operation(SemanticOperation::EstablishInitialPlacement(
        InitialPlacement {
            position: WorldTile::new(3221, 3218, Plane::MIN),
            map_anchor: WorldTile::new(3216, 3216, Plane::MIN),
            area: None,
        },
    ))?;
    state.apply_operation(SemanticOperation::InstallScene(descriptor))?;

    Ok(state)
}

/// Compose the minimap and compass through both toplevel masks (fixed 1183/1184, resizable
/// 1178/1179) at several yaws, so mask size and rotation handedness can be inspected as PNGs.
fn write_minimap_captures(
    cache: &Cache<DirectoryBlobs>,
    scene: &rustclient_scene::SceneAsset,
    directory: &std::path::Path,
) -> Result<(), Box<dyn std::error::Error>> {
    use rustclient_scene::minimap::{build_scene_minimap, compose_compass, compose_minimap};
    let minimap = build_scene_minimap(scene, Plane::MIN);
    println!(
        "minimap: {}x{} px, {} icons ({} icon sprites loaded)",
        minimap.width,
        minimap.height,
        minimap.icons.len(),
        scene.sources().map_icons.len()
    );
    let compass = cache.sprite(169)?.ok_or("compass sprite 169")?;
    let focus = (3221.5, 3218.5);
    for (name, mask_id, compass_mask_id) in [("fixed", 1183, 1184), ("resizable", 1178, 1179)] {
        let mask = cache.sprite(mask_id)?.ok_or("minimap mask")?;
        let compass_mask = cache.sprite(compass_mask_id)?.ok_or("compass mask")?;
        for (angle, zoom) in [(0_u16, 16), (512, 16), (1024, 16), (0, 8), (0, 32)] {
            let size = [mask.image.width() as usize, mask.image.height() as usize];
            let mut pixels = compose_minimap(
                mask.image.pixels(),
                size[0],
                size[1],
                &minimap,
                focus,
                angle,
                zoom,
            );
            rustclient_scene::minimap::draw_icons(
                &mut pixels,
                mask.image.pixels(),
                size,
                &scene.sources().map_icons,
                &minimap.icons,
                rustclient_scene::minimap::MinimapView {
                    camera: focus,
                    angle,
                    zoom_quarters: zoom,
                },
                0,
            );
            image::save_buffer(
                directory.join(format!("minimap-{name}-{angle}-z{zoom}.png")),
                &pixels,
                mask.image.width(),
                mask.image.height(),
                image::ColorType::Rgba8,
            )?;
            let pixels = compose_compass(
                compass.image.pixels(),
                compass.image.width() as usize,
                compass.image.height() as usize,
                compass_mask.image.pixels(),
                compass_mask.image.width() as usize,
                compass_mask.image.height() as usize,
                angle,
            );
            image::save_buffer(
                directory.join(format!("compass-{name}-{angle}.png")),
                &pixels,
                compass_mask.image.width(),
                compass_mask.image.height(),
                image::ColorType::Rgba8,
            )?;
        }
    }
    Ok(())
}
