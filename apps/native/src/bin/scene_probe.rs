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
    let assets = CacheProfile::OpenRune240.load_world(
        &cache,
        WorldTile::new(3168, 3168, Plane::MIN),
        104,
        104,
        |s| println!("loading {s}"),
    )?;
    println!(
        "squares {}, locations {}, objects {}, models {}",
        assets.squares.len(),
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
    let state = placed_state(scene.descriptor())?;
    let mesh = rustclient_scene::build_mesh(
        &scene,
        state.loaded_scene().ok_or("scene commit missing")?,
        state
            .initial_placement()
            .ok_or("placement missing")?
            .position,
    )?;
    let fingerprint = state.semantic_fingerprint();
    println!(
        "mesh: {:?}; {} vertices, {} bytes",
        mesh.stats,
        mesh.vertex_count(),
        mesh.vertex_count() * std::mem::size_of::<rustclient_scene::SceneVertex>()
    );
    if let Some(path) = std::env::args().nth(2) {
        let path = std::path::PathBuf::from(path);
        std::fs::create_dir_all(&path)?;
        let textures = path.join("textures");
        std::fs::create_dir_all(&textures)?;
        for (id, texture) in &mesh.textures {
            std::fs::write(
                textures.join(format!("{}.rgba", id.get())),
                texture.image.pixels(),
            )?;
        }
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
