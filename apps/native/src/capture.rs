use rustclient_application::{DeterministicRandomSource, RandomSeed, SplitMix64Random, load_title};
use rustclient_game_state::{GameState, SemanticOperation};
use rustclient_game_ui::{Flames, TitlePreferences, TitleUi, TitleViewport, UiFrame};
use rustclient_input::EditKey;
use rustclient_platform_native::DirectoryBlobs;
use rustclient_renderer::capture_ui;
use std::path::Path;

pub(super) fn run(options: &super::options::Options) -> Result<(), String> {
    let directory = options
        .capture_dir
        .as_ref()
        .ok_or("capture directory missing")?;
    std::fs::create_dir_all(directory).map_err(|error| error.to_string())?;
    let assets = load_title(
        DirectoryBlobs::open(&options.cache_dir).map_err(|e| e.to_string())?,
        options.profile,
        |name| eprintln!("loading {name}"),
    )
    .map_err(|e| e.to_string())?;
    let mut random = SplitMix64Random::new(RandomSeed::new(240));
    let mut flames = Flames::new(assets.flame_colors, || random.next_sample().get());
    for _ in 0..150 {
        flames.step(&assets.runes, || random.next_sample().get());
    }
    let mut state = GameState::new();
    let mut ui = TitleUi::new(TitlePreferences::default());
    let mut frame = UiFrame::default();
    let viewport = TitleViewport {
        physical_width: 765,
        physical_height: 503,
        scale: 1.,
    };
    ui.project(&assets, state.title_screen(), 150, &mut frame);
    let welcome = capture(&assets, &frame, &flames, viewport)?;
    save(directory, "welcome.png", &welcome, viewport)?;
    let before = state.semantic_fingerprint();
    // A second capture constructs every GPU resource anew from the same CPU assets.
    let rebuilt = capture(&assets, &frame, &flames, viewport)?;
    if rebuilt != welcome || state.semantic_fingerprint() != before {
        return Err("GPU rebuild changed title pixels or authoritative state".into());
    }
    let operation = ui.key(state.title_screen(), EditKey::Enter);
    apply(&mut state, operation)?;
    ui.project(&assets, state.title_screen(), 150, &mut frame);
    save(
        directory,
        "credentials.png",
        &capture(&assets, &frame, &flames, viewport)?,
        viewport,
    )?;
    ui.text(state.title_screen(), "Local Preview");
    let operation = ui.key(state.title_screen(), EditKey::Tab { reverse: false });
    apply(&mut state, operation)?;
    ui.text(state.title_screen(), "example");
    let operation = ui.key(state.title_screen(), EditKey::Enter);
    apply(&mut state, operation)?;
    ui.project(&assets, state.title_screen(), 150, &mut frame);
    save(
        directory,
        "offline.png",
        &capture(&assets, &frame, &flames, viewport)?,
        viewport,
    )?;
    let hidpi = TitleViewport {
        physical_width: 1800,
        physical_height: 1100,
        scale: 2.,
    };
    save(
        directory,
        "hidpi.png",
        &capture(&assets, &frame, &flames, hidpi)?,
        hidpi,
    )?;
    verify_authenticated_rebuild(&assets, &flames, &mut ui, &mut state, viewport, directory)?;
    let evidence = format!(
        "profile: {}\nmetadata-sha256: {}\nflame seed: 240\nflame step: 150 (20ms steps)\nGPU rebuild: byte-identical welcome pixels\nstate preserved across rebuild: true\nsemantic session: inactive\nauthenticated fixture rebuild: byte-identical pixels and preserved semantic fingerprint\n",
        options.profile.name(),
        assets.metadata_fingerprint
    );
    std::fs::write(directory.join("evidence.txt"), evidence).map_err(|e| e.to_string())?;
    eprintln!(
        "GPU captures and rebuild verification passed: {}",
        directory.display()
    );
    Ok(())
}
fn verify_authenticated_rebuild(
    assets: &rustclient_cache::TitleAssets,
    flames: &Flames,
    ui: &mut TitleUi,
    state: &mut GameState,
    viewport: TitleViewport,
    directory: &Path,
) -> Result<(), String> {
    use rustclient_game_state::{AuthenticatedPlayer, InitialPlacement};
    use rustclient_model::{Plane, PlayerIndex, WorldTile};
    let profile = rustclient_protocol::ServerProfile::OPENRUNE_240_2;
    ui.clear_password();
    ui.set_online(true);
    apply(
        state,
        Some(SemanticOperation::AuthenticateSession {
            revision: profile.revision(),
            player: AuthenticatedPlayer {
                profile: profile.id(),
                player: PlayerIndex::new(1),
                member: false,
                staff_level: 0,
            },
        }),
    )?;
    apply(
        state,
        Some(SemanticOperation::EstablishInitialPlacement(
            InitialPlacement {
                position: WorldTile::new(3221, 3218, Plane::MIN),
                map_anchor: WorldTile::new(3216, 3216, Plane::MIN),
                area: None,
            },
        )),
    )?;
    let fingerprint = state.semantic_fingerprint();
    let mut frame = UiFrame::default();
    ui.project_session(assets, state, &mut frame);
    let pixels = capture(assets, &frame, flames, viewport)?;
    save(directory, "connected-fixture.png", &pixels, viewport)?;
    let rebuilt = capture(assets, &frame, flames, viewport)?;
    if pixels != rebuilt || state.semantic_fingerprint() != fingerprint {
        return Err("GPU rebuild changed authenticated fixture pixels or state".into());
    }
    apply(state, Some(SemanticOperation::EndSession))
}
fn apply(state: &mut GameState, operation: Option<SemanticOperation>) -> Result<(), String> {
    if let Some(operation) = operation {
        state
            .apply_operation(operation)
            .map_err(|e| e.to_string())?;
    }
    Ok(())
}
fn capture(
    assets: &rustclient_cache::TitleAssets,
    frame: &UiFrame,
    flames: &Flames,
    viewport: TitleViewport,
) -> Result<Vec<u8>, String> {
    pollster::block_on(capture_ui(
        assets,
        frame,
        flames.pixels(),
        viewport,
        |device| {
            let _ = device.poll(wgpu::PollType::wait_indefinitely());
        },
    ))
    .map_err(|e| e.to_string())
}
fn save(
    directory: &Path,
    name: &str,
    pixels: &[u8],
    viewport: TitleViewport,
) -> Result<(), String> {
    image::save_buffer(
        directory.join(name),
        pixels,
        viewport.physical_width,
        viewport.physical_height,
        image::ColorType::Rgba8,
    )
    .map_err(|e| e.to_string())
}
