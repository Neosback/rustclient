#![forbid(unsafe_code)]
mod actor_overlays;
mod camera_input;
mod capture;
mod interaction;
mod network;
mod options;
mod performance;
mod plugins;
mod scene;
mod widget_pointer;
mod widgets;

use rustclient_application::{
    DeterministicRandomSource, RandomSeed, SplitMix64Random, load_title, load_title_preferences,
    save_title_preferences,
};
use rustclient_cache::TitleAssets;
use rustclient_game_state::{GameState, SemanticOperation};
use rustclient_game_ui::{
    Flames, TitlePreferences, TitleUi, TitleViewport, UiFrame,
    widget_visuals::{CanvasImage, WidgetVisualAssets},
};
use rustclient_input::EditKey;
use rustclient_model::SpriteId;
use rustclient_platform_native::{DirectoryBlobs, DirectoryStorage};
use rustclient_renderer::{ClearColor, FrameOutcome, SurfaceRenderer, SurfaceSize};
use rustclient_scene::{
    entity_mesh::build_entities_geometry,
    minimap::{MinimapImage, build_scene_minimap, compose_compass, compose_minimap},
};
use std::{
    process::ExitCode,
    sync::Arc,
    time::{Duration, Instant},
};
use winit::{
    application::ApplicationHandler,
    dpi::LogicalSize,
    event::{ElementState, Ime, MouseButton, WindowEvent},
    event_loop::{ActiveEventLoop, ControlFlow, EventLoop},
    keyboard::{Key, ModifiersState, NamedKey},
    window::{Window, WindowId},
};

const STEP: Duration = Duration::from_millis(20);
#[expect(
    clippy::large_enum_variant,
    reason = "keep bounded network operations inline without a heap allocation per packet"
)]
enum LoadEvent {
    Progress(String),
    Loaded(Box<Result<TitleAssets, String>>),
    Network(u64, network::NetworkEvent),
    ScriptsReady(
        Box<Result<rustclient_application::scripts::ClientScripts<DirectoryBlobs>, String>>,
    ),
    WidgetAssets(
        u64,
        rustclient_application::scripts::WidgetAssetRequest,
        Box<rustclient_game_ui::widget_visuals::WidgetVisualAssets>,
    ),
    SceneProgress(u64, String),
    EntityAssets(
        u64,
        Box<rustclient_cache::osrs240::entity_assets::EntityAssets>,
    ),
    DynamicReady(
        u64,
        u64,
        rustclient_model::ClientCycle,
        Box<Result<rustclient_scene::SceneGeometry, String>>,
    ),
    SceneLoaded(
        u64,
        Box<Result<rustclient_application::LoadedScene, String>>,
    ),
}

#[expect(
    clippy::struct_excessive_bools,
    reason = "independent window and loading flags, not a state machine"
)]
struct NativeApp {
    performance: performance::Performance,
    scene: scene::SceneView,
    window: Option<Arc<Window>>,
    renderer: Option<SurfaceRenderer>,
    assets: Option<TitleAssets>,
    state: GameState,
    ui: TitleUi,
    context_menu: rustclient_game_ui::ContextMenu<interaction::WorldChoice>,
    click_cross: Option<(Instant, interaction::CrossKind, f32, f32)>,
    hover: Option<interaction::HoverText>,
    hover_dirty: bool,
    /// Set once the game interface has been drawn this session; cleared on disconnect.
    entered_world: bool,
    widget_menu: rustclient_game_ui::ContextMenu<rustclient_model::WidgetAction>,
    reported_script_errors: std::collections::BTreeSet<String>,
    entity_report: (Instant, String),
    /// Hidden or fully covered windows are not drawn: the OS blocks `get_current_texture` there.
    occluded: bool,
    scripts: Option<rustclient_application::scripts::ClientScripts<DirectoryBlobs>>,
    widget_assets: rustclient_game_ui::widget_visuals::WidgetVisualAssets,
    widget_requested: rustclient_application::scripts::WidgetAssetRequest,
    widget_loading: bool,
    reported_window: Option<rustclient_model::WindowMetrics>,
    widget_tree: rustclient_game_ui::widgets::WidgetTree,
    widget_pointer: widget_pointer::WidgetPointer,
    widget_cache_dir: std::path::PathBuf,
    widget_cache_profile: rustclient_cache::CacheProfile,
    minimap: Option<MinimapImage>,
    /// Plane the cached minimap image was built for; the client rebuilds it when the player changes plane.
    minimap_plane: Option<rustclient_model::Plane>,
    minimap_pixels: Vec<u8>,
    compass_pixels: Vec<u8>,
    /// Widget-authored mask sprites expanded onto their canvas; replaced with the widget assets.
    minimap_mask: Option<(SpriteId, CanvasImage)>,
    compass_mask: Option<(SpriteId, CanvasImage)>,
    flames: Option<Flames>,
    random: SplitMix64Random,
    frame: UiFrame,
    plugins: plugins::Plugins,
    storage: Option<DirectoryStorage>,
    saved_preferences: TitlePreferences,
    modifiers: ModifiersState,
    cursor: Option<(f64, f64)>,
    ime_preedit: bool,
    next_step: Instant,
    cycle: u64,
    failed: bool,
    load_status: String,
    connection_options: Option<network::ConnectionOptions>,
    connection: Option<network::ConnectionHandle>,
    connection_generation: u64,
    map_epoch: u64,
    interface_epoch: u64,
    proxy: winit::event_loop::EventLoopProxy<LoadEvent>,
}
impl NativeApp {
    fn new(
        options: &options::Options,
        proxy: winit::event_loop::EventLoopProxy<LoadEvent>,
    ) -> Self {
        let storage = match DirectoryStorage::new(&options.preferences_dir) {
            Ok(store) => Some(store),
            Err(error) => {
                eprintln!("preferences unavailable: {error}");
                None
            }
        };
        let preferences = storage
            .as_ref()
            .and_then(|store| match load_title_preferences(store) {
                Ok(prefs) => Some(prefs),
                Err(error) => {
                    eprintln!("using default preferences: {error}");
                    None
                }
            })
            .unwrap_or_default();
        let mut ui = TitleUi::new(preferences.clone());
        ui.set_online(options.connection.is_some());
        let plugins = plugins::Plugins::load(
            &options.plugins_dir,
            &options.preferences_dir.join("plugin-data"),
        );
        Self {
            performance: performance::Performance::new(),
            scene: scene::SceneView::new(options),
            connection_options: options.connection.clone(),
            connection: None,
            connection_generation: 0,
            map_epoch: 0,
            interface_epoch: 0,
            proxy,
            window: None,
            renderer: None,
            assets: None,
            state: GameState::new(),
            ui,
            context_menu: rustclient_game_ui::ContextMenu::default(),
            click_cross: None,
            hover: None,
            hover_dirty: false,
            entered_world: false,
            widget_menu: rustclient_game_ui::ContextMenu::default(),
            reported_script_errors: std::collections::BTreeSet::default(),
            entity_report: (Instant::now(), String::new()),
            occluded: false,
            scripts: None,
            widget_assets: rustclient_game_ui::widget_visuals::WidgetVisualAssets::default(),
            widget_requested: rustclient_application::scripts::WidgetAssetRequest::default(),
            widget_loading: false,
            reported_window: None,
            widget_tree: rustclient_game_ui::widgets::WidgetTree::default(),
            widget_pointer: widget_pointer::WidgetPointer::default(),
            widget_cache_dir: options.cache_dir.clone(),
            widget_cache_profile: options.profile,
            minimap: None,
            minimap_plane: None,
            minimap_pixels: Vec::new(),
            compass_pixels: Vec::new(),
            minimap_mask: None,
            compass_mask: None,
            flames: None,
            random: SplitMix64Random::new(RandomSeed::new(240)),
            frame: UiFrame::default(),
            plugins,
            storage,
            saved_preferences: preferences,
            modifiers: ModifiersState::default(),
            cursor: None,
            ime_preedit: false,
            next_step: Instant::now(),
            cycle: 0,
            failed: false,
            load_status: "Opening cache…".into(),
        }
    }
    fn create_renderer(&mut self, event_loop: &ActiveEventLoop) {
        let fingerprint = self.state.semantic_fingerprint();
        let Some(window) = self.window.clone() else {
            return;
        };
        let size = window.inner_size();
        match pollster::block_on(SurfaceRenderer::new(
            window.clone(),
            SurfaceSize::new(size.width, size.height),
        )) {
            Ok(mut renderer) => {
                if let Some(assets) = &self.assets
                    && let Err(error) = renderer.set_title_assets(assets)
                {
                    self.fail(event_loop, &error.to_string());
                    return;
                }
                if !self.widget_assets.images.is_empty()
                    && let Some(assets) = &self.assets
                    && let Err(error) =
                        renderer.set_widget_images(assets, &self.widget_assets.images)
                {
                    self.fail(event_loop, &error.to_string());
                    return;
                }
                if let Some(scene) = &self.scene.loaded
                    && let Err(error) = renderer.set_world(&scene.mesh)
                {
                    self.fail(event_loop, &error.to_string());
                    return;
                }
                if let Some(scene) = &self.scene.loaded
                    && let Err(error) = renderer
                        .set_world_dynamic(scene.asset.descriptor().id(), &scene.mesh.dynamic)
                        .and_then(|()| {
                            renderer.set_world_entities(
                                scene.asset.descriptor().id(),
                                &self.scene.entity_geometry,
                            )
                        })
                {
                    self.fail(event_loop, &error.to_string());
                    return;
                }
                if self.scene.loaded.is_some() {
                    eprintln!(
                        "World GPU recreated; semantic fingerprint preserved: {}",
                        self.state.semantic_fingerprint() == fingerprint
                    );
                }
                self.renderer = Some(renderer);
                window.request_redraw();
            }
            Err(error) => self.fail(event_loop, &format!("renderer initialization: {error}")),
        }
    }
    fn fail(&mut self, event_loop: &ActiveEventLoop, message: &str) {
        eprintln!("RustClient: {message}");
        self.failed = true;
        event_loop.exit();
    }
    fn disconnect(&mut self) {
        self.entered_world = false;
        self.widget_pointer = widget_pointer::WidgetPointer::default();
        self.hover = None;
        self.hover_dirty = true;
        self.click_cross = None;
        self.reported_window = None;
        self.widget_menu.close();
        self.widget_tree.nodes.clear();
        self.widget_loading = false;
        if let Some(scripts) = &mut self.scripts {
            scripts.reset();
        }
        self.context_menu.close();
        self.minimap = None;
        self.scene.clear();
        if let Some(cancel) = self.connection.take() {
            cancel.cancel();
        }
        self.connection_generation = self.connection_generation.wrapping_add(1);
        self.ui.clear_password();
        if let Some(window) = &self.window {
            window.set_title(if self.connection_options.is_some() {
                "RustClient - OpenRune dev server login"
            } else {
                "RustClient - Offline title preview"
            });
        }
        if self.state.lifecycle() == rustclient_game_state::SessionLifecycle::Active {
            let _ = self.state.apply_operation(SemanticOperation::EndSession);
        }
        self.plugins.stop(&self.state);
    }
    fn apply(&mut self, operation: Option<SemanticOperation>) {
        if operation
            == Some(SemanticOperation::SetTitleScreen(
                rustclient_game_state::TitleScreen::Welcome,
            ))
        {
            self.disconnect();
        }

        if let Some(operation) = operation {
            let result = if let Some(scripts) = &mut self.scripts {
                scripts.commit(&mut self.state, operation)
            } else {
                self.state
                    .apply_operation(operation)
                    .map(|_| Vec::new())
                    .map_err(|e| e.to_string())
            };
            match result {
                Ok(errors) => {
                    for error in errors {
                        if self.reported_script_errors.len() < 256
                            && self.reported_script_errors.insert(error.clone())
                        {
                            eprintln!("client script: {error}");
                        }
                    }
                }
                Err(error) => eprintln!("semantic input rejected: {error}"),
            }
            self.widget_tree.nodes.clear();
            self.flush_script_intents();
        }
        if let Some(credentials) = self.ui.take_submission() {
            self.disconnect();
            if let Some(options) = self.connection_options.clone() {
                match network::spawn(
                    options,
                    credentials,
                    self.state.interfaces().window(),
                    self.connection_generation,
                    self.proxy.clone(),
                ) {
                    Ok(cancel) => self.connection = Some(cancel),
                    Err(error) => {
                        eprintln!("connection worker: {error}");
                        let _ = self
                            .state
                            .apply_operation(SemanticOperation::SetTitleScreen(
                                rustclient_game_state::TitleScreen::Credentials(
                                    rustclient_game_state::LoginStatus::Failed(
                                        rustclient_game_state::LoginFailure::Connection,
                                    ),
                                ),
                            ));
                    }
                }
            }
        }
        let preferences = self.ui.preferences();
        if preferences != self.saved_preferences {
            if let Some(store) = &self.storage
                && let Err(error) = save_title_preferences(store, &preferences)
            {
                eprintln!("could not save title preferences: {error}");
            }
            self.saved_preferences = preferences;
        }
        if let Some(window) = &self.window {
            window.request_redraw();
        }
    }
    /// Actor markers in scene-relative tile units, drawn from the animated positions.
    #[expect(
        clippy::cast_precision_loss,
        reason = "world fine positions originate from i32 tiles and fit the f64 mantissa"
    )]
    fn minimap_dots(
        &self,
        origin: rustclient_model::WorldTile,
    ) -> Vec<rustclient_scene::minimap::MinimapDot> {
        use rustclient_scene::minimap::MinimapDot;
        let Some(loaded) = &self.scene.loaded else {
            return Vec::new();
        };
        let entities = self.state.entities();
        let local = entities.local_player().map(|player| player.index);
        let centre = |position: [i64; 2], size: u8| {
            let half = f64::from(size) / 2.;
            (
                position[0] as f64 / 128. + half - f64::from(origin.x),
                position[1] as f64 / 128. + half - f64::from(origin.y),
            )
        };
        let mut dots = Vec::new();
        for player in entities.players().values() {
            if Some(player.index) == local {
                continue;
            }
            let position = player.path_queue.fine_position(player.tile).units();
            dots.push(MinimapDot {
                tile: centre(position, 1),
                frame: 2,
            });
        }
        for npc in entities.npcs().values() {
            let Some(definition) = self.scene.entity_catalog.resolve_npc(
                &loaded.asset,
                npc.definition_id,
                self.state.scenery().variables(),
            ) else {
                continue;
            };
            if !definition.draw_minimap_dot {
                continue;
            }
            let position = npc.path_queue.fine_position(npc.tile).units();
            dots.push(MinimapDot {
                tile: centre(position, definition.size.max(1)),
                frame: 1,
            });
        }
        dots
    }

    #[expect(
        clippy::too_many_lines,
        reason = "one frame projects every interface layer in paint order"
    )]
    #[expect(
        clippy::cast_precision_loss,
        reason = "validated world fine positions fit the f64 minimap projection"
    )]
    fn redraw(&mut self, event_loop: &ActiveEventLoop) {
        let Some(window) = self.window.clone() else {
            return;
        };
        if self.occluded {
            return;
        }
        let frame_start = Instant::now();
        let result = if let (Some(assets), Some(flames)) = (&self.assets, &self.flames) {
            if self.state.authenticated_player().is_some()
                && self.state.interfaces().root().is_some()
            {
                if self.scene.diagnostics {
                    self.frame.world_viewport = None;
                    self.frame.viewport_settings = rustclient_model::ViewportSettings::default();
                    self.ui.project_world(assets, &self.state, &mut self.frame);
                } else {
                    self.frame.quads.clear();
                    self.entered_world = true;

                    if let Some(scripts) = &self.scripts {
                        let dragged = self.dragged_widget();
                        match rustclient_game_ui::widget_visuals::project_widgets_with_drag(
                            &self.state,
                            &scripts.interfaces,
                            &self.widget_assets,
                            &mut self.frame,
                            dragged,
                        ) {
                            Ok(tree) => self.widget_tree = tree,
                            Err(error) => {
                                eprintln!("widget projection: {error}");
                                self.widget_tree.nodes.clear();
                            }
                        }
                    }
                }
                let top_level = self.roof_top_level(&window);
                actor_overlays::project(
                    &self.scene,
                    &self.state,
                    assets,
                    &mut self.frame,
                    top_level,
                );
                interaction::project_pointer_overlays(
                    &mut self.frame,
                    assets,
                    &self.widget_assets,
                    self.hover.as_ref(),
                    &mut self.click_cross,
                    &self.state,
                );
                self.context_menu.project(assets, &mut self.frame);
                self.widget_menu.project(assets, &mut self.frame);
                self.plugins.overlay(&self.state, assets, &mut self.frame);
                if self.state.loaded_scene().is_none() || self.scene.loading {
                    rustclient_game_ui::shadowed_text(
                        &mut self.frame,
                        assets,
                        "Loading - please wait.",
                        6,
                        20,
                        [255; 4],
                        rustclient_game_ui::Rect::new(0., 0., 16384., 16384.),
                    );
                }
                if let (Some(loaded), Some(player), Some(_)) = (
                    &self.scene.loaded,
                    self.state.entities().local_player(),
                    &self.minimap,
                ) && self.minimap_plane != Some(player.tile.plane)
                {
                    self.minimap = Some(build_scene_minimap(&loaded.asset, player.tile.plane));
                    self.minimap_plane = Some(player.tile.plane);
                }
                if let (Some(gameframe), Some(minimap), Some(camera)) =
                    (&assets.gameframe, &self.minimap, self.scene.camera)
                {
                    let origin = self.scene.loaded.as_ref().map_or_else(
                        || rustclient_model::WorldTile::new(0, 0, rustclient_model::Plane::MIN),
                        |scene| scene.asset.descriptor().origin(),
                    );
                    let camera_focus_tile =
                        if let Some(player) = self.state.entities().local_player() {
                            let position = player.path_queue.fine_position(player.tile);
                            (
                                (position.x as f64 + 64.) / 128.,
                                (position.y as f64 + 64.) / 128.,
                            )
                        } else {
                            (
                                f64::from(origin.x) + f64::from(camera.focal()[0]) / 128.,
                                f64::from(origin.y) + f64::from(camera.focal()[2]) / 128.,
                            )
                        };
                    let angle = camera.yaw();
                    let dots = self.minimap_dots(origin);
                    if let Some(mask) = cached_mask(
                        &mut self.minimap_mask,
                        &self.widget_assets,
                        self.frame.minimap_sprite,
                    ) {
                        self.minimap_pixels = compose_minimap(
                            &mask.pixels,
                            mask.size[0] as usize,
                            mask.size[1] as usize,
                            minimap,
                            camera_focus_tile,
                            angle,
                        );
                        if let Some(loaded) = &self.scene.loaded {
                            let relative = (
                                camera_focus_tile.0 - f64::from(origin.x),
                                camera_focus_tile.1 - f64::from(origin.y),
                            );
                            rustclient_scene::minimap::draw_dots(
                                &mut self.minimap_pixels,
                                &mask.pixels,
                                [mask.size[0] as usize, mask.size[1] as usize],
                                &loaded.asset.sources().map_dots,
                                &dots,
                                relative,
                                angle,
                            );
                        }
                        if let (Some(destination), Some(sprite)) = (
                            self.state.destination().filter(|value| value.x != origin.x),
                            self.widget_assets.destination_flag.as_ref(),
                        ) {
                            rustclient_scene::minimap::draw_marker(
                                &mut self.minimap_pixels,
                                &mask.pixels,
                                [mask.size[0] as usize, mask.size[1] as usize],
                                sprite,
                                (
                                    f64::from(destination.x) + 0.5,
                                    f64::from(destination.y) + 0.5,
                                ),
                                camera_focus_tile,
                                angle,
                            );
                        }
                        if self.state.entities().local_player().is_some_and(|player| {
                            player
                                .appearance
                                .as_ref()
                                .is_none_or(|appearance| !appearance.hidden)
                        }) {
                            rustclient_scene::minimap::draw_local_player(
                                &mut self.minimap_pixels,
                                &mask.pixels,
                                [mask.size[0] as usize, mask.size[1] as usize],
                            );
                        }
                    }
                    if let Some(mask) = cached_mask(
                        &mut self.compass_mask,
                        &self.widget_assets,
                        self.frame.compass_sprite,
                    ) {
                        self.compass_pixels = compose_compass(
                            gameframe.compass.image.pixels(),
                            gameframe.compass.image.width() as usize,
                            gameframe.compass.image.height() as usize,
                            &mask.pixels,
                            mask.size[0] as usize,
                            mask.size[1] as usize,
                            angle,
                        );
                    }
                }
            } else if self.state.authenticated_player().is_some() {
                if self.entered_world {
                    self.ui
                        .project_loading(assets, &self.state, &mut self.frame);
                } else {
                    self.ui
                        .project_session(assets, &self.state, &mut self.frame);
                }
            } else {
                self.ui.project(
                    assets,
                    self.state.title_screen(),
                    self.cycle,
                    &mut self.frame,
                );
            }
            let top_level = self.roof_top_level(&window);
            self.renderer.as_mut().map(|renderer| {
                if let (Some(descriptor), Some(camera)) =
                    (self.state.loaded_scene(), self.scene.camera)
                {
                    return renderer.render_world(
                        descriptor.id(),
                        camera,
                        top_level,
                        self.state.scenery().cycle(),
                        &self.frame,
                        rustclient_renderer::DynamicImages {
                            minimap: dynamic_image(
                                &self.minimap_pixels,
                                self.minimap_mask.as_ref(),
                            ),
                            compass: dynamic_image(
                                &self.compass_pixels,
                                self.compass_mask.as_ref(),
                            ),
                        },
                        window.scale_factor(),
                        || window.pre_present_notify(),
                    );
                }
                renderer.render_ui(&self.frame, flames.pixels(), window.scale_factor(), || {
                    window.pre_present_notify();
                })
            })
        } else {
            self.renderer.as_mut().map(|renderer| {
                renderer.render_clear(ClearColor::BOOTSTRAP, || window.pre_present_notify())
            })
        };
        self.performance.note(|| {
            format!(
                "quads {} world_viewport {:?} nodes {} root {:?} mounts {:?} entered {} loaded {} result {:?}",
                self.frame.quads.len(),
                self.frame.world_viewport,
                self.widget_tree.nodes.len(),
                self.state.interfaces().root(),
                self.state
                    .interfaces()
                    .mounts()
                    .iter()
                    .map(|(at, mount)| (at.interface.get(), at.component, mount))
                    .collect::<Vec<_>>(),
                self.entered_world,
                self.scene.loaded.is_some(),
                result.as_ref().map(Result::as_ref),
            )
        });
        self.performance.frame(
            &window,
            frame_start,
            matches!(result, Some(Ok(FrameOutcome::Presented))),
        );
        if !matches!(result, Some(Ok(FrameOutcome::Presented))) {
            self.performance.event(|| {
                format!(
                    "frame not presented: {:?}",
                    result.as_ref().map(Result::as_ref)
                )
            });
        }
        match result {
            Some(Ok(FrameOutcome::RecreateRequired)) => self.create_renderer(event_loop),
            Some(Ok(FrameOutcome::Reconfigured)) => window.request_redraw(),
            Some(Err(error)) => self.fail(event_loop, &format!("rendering: {error}")),
            _ => {}
        }
    }
}
/// The widget mask for `sprite`, expanded once and reused until the sprite or assets change.
fn cached_mask<'a>(
    cache: &'a mut Option<(SpriteId, CanvasImage)>,
    assets: &WidgetVisualAssets,
    sprite: Option<SpriteId>,
) -> Option<&'a CanvasImage> {
    let sprite = sprite?;
    if cache.as_ref().is_none_or(|(cached, _)| *cached != sprite) {
        *cache = assets.canvas_image(sprite).map(|image| (sprite, image));
    }
    cache.as_ref().map(|(_, image)| image)
}
fn dynamic_image<'a>(
    pixels: &'a [u8],
    mask: Option<&(SpriteId, CanvasImage)>,
) -> rustclient_renderer::DynamicImage<'a> {
    mask.filter(|(_, mask)| {
        u64::from(mask.size[0]) * u64::from(mask.size[1]) * 4 == pixels.len() as u64
    })
    .map_or_else(Default::default, |(_, mask)| {
        rustclient_renderer::DynamicImage {
            pixels,
            width: mask.size[0],
            height: mask.size[1],
        }
    })
}
impl NativeApp {
    fn finish_scene(
        &mut self,
        event_loop: &ActiveEventLoop,
        result: Result<rustclient_application::LoadedScene, String>,
    ) {
        self.scene.loading = false;
        match result {
            Ok(mut scene) => {
                let mut current: rustclient_scene::SceneryInputs = self.state.scenery().into();
                current.retain_scene_dependencies(&scene.asset);
                if scene.projection_inputs.same_geometry(&current) {
                    scene.projection_generation = current.generation;
                }
                if scene.projection_generation != self.state.scenery().generation() {
                    if let Some(placement) = self.state.initial_placement()
                        && let Err(e) = self.scene.reproject(
                            scene.asset,
                            placement,
                            self.scene.scenery_inputs(self.state.scenery()),
                            self.scene.generation,
                            self.proxy.clone(),
                        )
                    {
                        eprintln!("scenery projection: {e}");
                    }
                    return;
                }
                let first = self.state.loaded_scene().is_none();
                eprintln!(
                    "Spawn scene: {:?}; {} vertices; {} auxiliary map files",
                    scene.mesh.stats,
                    scene.mesh.vertex_count(),
                    scene.asset.sources().auxiliary_map_files
                );
                for warning in &scene.asset.sources().size_hint_warnings {
                    eprintln!("cache profile advisory: {warning}");
                }
                if first
                    && let Err(error) = self
                        .state
                        .apply_operation(SemanticOperation::InstallScene(scene.asset.descriptor()))
                {
                    eprintln!("scene commit: {error}");
                    return;
                }
                if let Some(renderer) = &mut self.renderer
                    && let Err(error) = renderer.set_world(&scene.mesh)
                {
                    self.fail(event_loop, &error.to_string());
                    return;
                }
                if let Err(e) = self.scene.start_entity_loader(
                    scene.asset.clone(),
                    self.scene.generation,
                    self.proxy.clone(),
                ) {
                    eprintln!("entity asset worker: {e}");
                }
                if let Err(e) = self.scene.start_animator(
                    scene.asset.clone(),
                    self.scene.generation,
                    self.proxy.clone(),
                ) {
                    eprintln!("animation worker: {e}");
                }
                if let Some(placement) = self.state.initial_placement() {
                    self.minimap =
                        Some(build_scene_minimap(&scene.asset, placement.position.plane));
                    self.minimap_plane = Some(placement.position.plane);
                }
                self.scene.loaded = Some(scene);
                if first {
                    self.acknowledge_map();
                    self.scene.reset_camera();
                    self.next_step = Instant::now() + STEP;
                }
                if let Some(window) = &self.window {
                    window.set_title("RustClient - OpenRune spawn scene");
                    window.request_redraw();
                }
            }
            Err(error) => {
                eprintln!("spawn scene: {error}");
                if let Some(window) = &self.window {
                    window.set_title(&format!("RustClient - scene load failed: {error}"));
                }
            }
        }
    }

    fn keyboard(
        &mut self,
        event_loop: &ActiveEventLoop,
        window: &Window,
        event: winit::event::KeyEvent,
    ) {
        if self.scene.camera.is_some() {
            match &event.logical_key {
                Key::Named(NamedKey::Home) if !event.repeat => self.scene.reset_camera(),
                Key::Named(NamedKey::F5) if !event.repeat => self.create_renderer(event_loop),
                Key::Named(NamedKey::F3) if !event.repeat => {
                    self.scene.diagnostics = !self.scene.diagnostics;
                }
                _ => {}
            }
            window.request_redraw();
        }
        if self.state.authenticated_player().is_some() {
            if let Some(key) = widget_key(&event.logical_key) {
                self.game_key(Some(key), None, true);
            } else if !self.modifiers.control_key()
                && !self.modifiers.super_key()
                && !self.ime_preedit
                && let Some(text) = event.text
            {
                for ch in text.chars().filter(|ch| !ch.is_control()) {
                    self.game_key(None, Some(ch), true);
                }
            }
            window.request_redraw();
            return;
        }
        let key = match event.logical_key {
            Key::Named(NamedKey::Backspace) => Some(EditKey::Backspace),
            Key::Named(NamedKey::Tab) => Some(EditKey::Tab {
                reverse: self.modifiers.shift_key(),
            }),
            Key::Named(NamedKey::Enter) => Some(EditKey::Enter),
            Key::Named(NamedKey::Escape) => Some(EditKey::Escape),
            _ => None,
        };
        if let Some(key) = key {
            let operation = if self.state.authenticated_player().is_some() {
                (key == EditKey::Escape).then_some(SemanticOperation::SetTitleScreen(
                    rustclient_game_state::TitleScreen::Welcome,
                ))
            } else {
                self.ui.key(self.state.title_screen(), key)
            };
            self.apply(operation);
        } else if !self.modifiers.control_key()
            && !self.modifiers.super_key()
            && !self.ime_preedit
            && let Some(text) = event.text
        {
            self.ui.text(self.state.title_screen(), &text);
            self.apply(None);
        }
    }
}

impl ApplicationHandler<LoadEvent> for NativeApp {
    fn resumed(&mut self, event_loop: &ActiveEventLoop) {
        if self.window.is_some() {
            return;
        }
        let attributes = Window::default_attributes()
            .with_title(format!("RustClient — {}", self.load_status))
            .with_inner_size(LogicalSize::new(765., 503.))
            .with_min_inner_size(LogicalSize::new(765., 503.));
        match event_loop.create_window(attributes) {
            Ok(window) => {
                window.set_ime_allowed(true);
                self.window = Some(Arc::new(window));
                self.create_renderer(event_loop);
            }
            Err(error) => self.fail(event_loop, &format!("window creation: {error}")),
        }
    }
    #[expect(
        clippy::too_many_lines,
        reason = "native event routing; each event delegates to its subsystem owner"
    )]
    fn user_event(&mut self, event_loop: &ActiveEventLoop, event: LoadEvent) {
        match event {
            LoadEvent::ScriptsReady(result) => match *result {
                Ok(mut scripts) => {
                    if let Err(e) = scripts.synchronize(&self.state) {
                        eprintln!("script definitions: {e}");
                    }
                    self.scripts = Some(scripts);
                }
                Err(e) => self.fail(event_loop, &format!("script cache: {e}")),
            },
            LoadEvent::WidgetAssets(generation, request, assets) => {
                if generation == self.connection_generation {
                    self.performance.event(|| {
                        format!(
                            "widget assets loaded: {} images, {} failures",
                            assets.images.len(),
                            assets.failures.len()
                        )
                    });
                    self.widget_loading = false;
                    for error in &assets.failures {
                        eprintln!("widget asset: {error}");
                    }
                    self.widget_assets.merge(*assets);
                    if let (Some(renderer), Some(title)) = (&mut self.renderer, &self.assets)
                        && let Err(e) =
                            renderer.set_widget_images(title, &self.widget_assets.images)
                    {
                        eprintln!("widget GPU upload: {e}");
                        return;
                    }
                    self.minimap_mask = None;
                    self.compass_mask = None;
                    self.widget_requested.absorb(&request);
                    self.request_widget_assets();
                    if let Some(window) = &self.window {
                        window.request_redraw();
                    }
                }
            }
            LoadEvent::SceneProgress(generation, message)
                if generation == self.scene.generation =>
            {
                if let Some(window) = &self.window {
                    window.set_title(&format!("RustClient - {message}"));
                }
            }
            LoadEvent::SceneLoaded(generation, result) if generation == self.scene.generation => {
                self.finish_scene(event_loop, *result);
            }

            LoadEvent::Network(generation, event) if generation == self.connection_generation => {
                match event {
                    network::NetworkEvent::Progress(message) => {
                        if let Some(window) = &self.window {
                            window.set_title(&format!("RustClient - {message}"));
                        }
                    }
                    network::NetworkEvent::Script(epoch, request) => {
                        self.interface_epoch = epoch;
                        if let Some(scripts) = &mut self.scripts {
                            match scripts.enqueue(request, None) {
                                Ok(()) => {
                                    for error in scripts.drain(&mut self.state) {
                                        if self.reported_script_errors.len() < 256
                                            && self.reported_script_errors.insert(error.clone())
                                        {
                                            eprintln!("client script: {error}");
                                        }
                                    }
                                }
                                Err(error) => eprintln!("client script: {error}"),
                            }
                        }
                        self.widget_tree.nodes.clear();
                        self.flush_script_intents();
                        self.request_widget_assets();
                        if let Some(window) = &self.window {
                            window.request_redraw();
                        }
                    }
                    network::NetworkEvent::Operation(map_epoch, interface_epoch, operation) => {
                        self.hover_dirty = true;
                        self.interface_epoch = interface_epoch;
                        self.map_epoch = map_epoch;
                        let rebuild = matches!(operation, SemanticOperation::RebuildMap(_));
                        let previous_scene = self.state.loaded_scene();
                        self.apply(Some(operation));
                        self.scene
                            .motion
                            .observe(self.state.entities(), self.state.scenery().cycle());
                        self.request_widget_assets();
                        if rebuild
                            || previous_scene.is_some() && self.state.loaded_scene().is_none()
                        {
                            self.context_menu.close();
                            self.scene.invalidate_map();
                            self.minimap = None;
                        }
                        if let Some(loaded) = &mut self.scene.loaded {
                            let mut current: rustclient_scene::SceneryInputs =
                                self.state.scenery().into();
                            current.retain_scene_dependencies(&loaded.asset);
                            if loaded.projection_inputs.same_geometry(&current) {
                                loaded.projection_generation = current.generation;
                            }
                        }
                        if !self.scene.loading
                            && let Some(loaded) = &self.scene.loaded
                            && loaded.projection_generation != self.state.scenery().generation()
                            && let Some(placement) = self.state.initial_placement()
                        {
                            let asset = loaded.asset.clone();
                            if let Err(e) = self.scene.reproject(
                                asset,
                                placement,
                                self.scene.scenery_inputs(self.state.scenery()),
                                self.scene.generation,
                                self.proxy.clone(),
                            ) {
                                eprintln!("scenery projection: {e}");
                            }
                        }
                        if self.state.initial_placement().is_some()
                            && let Some(window) = &self.window
                            && !self.scene.attempted
                            && self.scene.loaded.is_none()
                            && !self.scene.loading
                        {
                            window.set_title("RustClient - Loading spawn scene");
                            if let Some(placement) = self.state.initial_placement()
                                && let Err(error) = self.scene.start(
                                    placement,
                                    self.scene.generation,
                                    self.proxy.clone(),
                                )
                            {
                                eprintln!("{error}");
                            }
                        }
                    }
                    network::NetworkEvent::Failed(category, diagnostic) => {
                        eprintln!("connection: {diagnostic}");
                        self.disconnect();
                        self.apply(Some(SemanticOperation::SetTitleScreen(
                            rustclient_game_state::TitleScreen::Credentials(
                                rustclient_game_state::LoginStatus::Failed(category),
                            ),
                        )));
                    }
                }
            }
            LoadEvent::DynamicReady(session, generation, cycle, result) => {
                if session == self.scene.generation {
                    self.scene.animator_in_flight = None;
                }
                if session == self.scene.generation
                    && generation == self.state.scenery().generation()
                    && cycle >= self.scene.dynamic_cycle
                    && !self.scene.loading
                {
                    match *result {
                        Ok(geometry) => {
                            if let Some(loaded) = &mut self.scene.loaded {
                                if let Some(renderer) = &mut self.renderer
                                    && let Err(e) = renderer.set_world_dynamic(
                                        loaded.asset.descriptor().id(),
                                        &geometry,
                                    )
                                {
                                    eprintln!("dynamic GPU upload: {e}");
                                    return;
                                }
                                loaded.mesh.dynamic = geometry;
                                self.scene.dynamic_cycle = cycle;
                                if let Some(window) = &self.window {
                                    window.request_redraw();
                                }
                            }
                        }
                        Err(e) => {
                            eprintln!("scenery animation: {e}");
                            self.scene.animator = None;
                        }
                    }
                }
            }
            LoadEvent::EntityAssets(session, assets) => {
                if session == self.scene.generation {
                    for failure in &assets.failures {
                        eprintln!("entity asset advisory: {failure}");
                    }
                    if let Some(loaded) = &self.scene.loaded {
                        for failure in self.scene.entity_catalog.merge(&loaded.asset, *assets) {
                            eprintln!("entity asset advisory: {failure}");
                        }
                    }
                }
            }
            LoadEvent::SceneLoaded(_, _)
            | LoadEvent::SceneProgress(_, _)
            | LoadEvent::Network(_, _) => {}
            LoadEvent::Progress(message) => {
                self.load_status = message;
                if let Some(window) = &self.window {
                    window.set_title(&format!("RustClient — loading {}", self.load_status));
                }
            }
            LoadEvent::Loaded(result) => match *result {
                Ok(assets) => {
                    eprintln!(
                        "Title loaded; reference-metadata-sha256: {}",
                        assets.metadata_fingerprint
                    );
                    let random = &mut self.random;
                    self.flames = Some(Flames::new(assets.flame_colors, || {
                        random.next_sample().get()
                    }));
                    if let Some(renderer) = &mut self.renderer
                        && let Err(error) = renderer.set_title_assets(&assets)
                    {
                        self.fail(event_loop, &error.to_string());
                        return;
                    }
                    self.assets = Some(assets);
                    self.next_step = Instant::now();
                    if let Some(window) = &self.window {
                        window.set_title(if self.connection_options.is_some() {
                            "RustClient - OpenRune dev server login"
                        } else {
                            "RustClient - Offline title preview"
                        });
                        window.request_redraw();
                    }
                }
                Err(error) => self.fail(event_loop, &format!("cache load: {error}")),
            },
        }
    }
    #[expect(clippy::too_many_lines, reason = "winit event dispatch table")]
    fn window_event(
        &mut self,
        event_loop: &ActiveEventLoop,
        window_id: WindowId,
        event: WindowEvent,
    ) {
        let Some(window) = self.window.clone() else {
            return;
        };
        if window.id() != window_id {
            return;
        }
        if matches!(
            event,
            WindowEvent::Focused(_)
                | WindowEvent::Occluded(_)
                | WindowEvent::Resized(_)
                | WindowEvent::ScaleFactorChanged { .. }
                | WindowEvent::Moved(_)
                | WindowEvent::ThemeChanged(_)
        ) {
            self.performance.event(|| format!("{event:?}"));
        }
        match event {
            WindowEvent::CloseRequested => {
                self.disconnect();
                event_loop.exit();
            }
            WindowEvent::Resized(size) => {
                if let Some(renderer) = &mut self.renderer {
                    renderer.resize(SurfaceSize::new(size.width, size.height));
                }
                window.request_redraw();
            }
            WindowEvent::ScaleFactorChanged { .. } => {
                let size = window.inner_size();
                if let Some(renderer) = &mut self.renderer {
                    renderer.resize(SurfaceSize::new(size.width, size.height));
                }
                window.request_redraw();
            }
            WindowEvent::RedrawRequested => self.redraw(event_loop),
            WindowEvent::MouseWheel { delta, .. } => {
                #[expect(
                    clippy::cast_possible_truncation,
                    reason = "finite bounded platform wheel rotation"
                )]
                let amount = match delta {
                    winit::event::MouseScrollDelta::LineDelta(_, y) => {
                        (-f64::from(y)).clamp(-128., 128.).round() as i32
                    }
                    winit::event::MouseScrollDelta::PixelDelta(p) => {
                        let lines = -p.y / 45.;
                        if lines == 0. || !lines.is_finite() {
                            0
                        } else if lines.abs() < 1. {
                            lines.signum() as i32
                        } else {
                            lines.clamp(-128., 128.).round() as i32
                        }
                    }
                };
                if let Some((x, y)) = self.cursor {
                    let (x, y) = self.ui_pointer(&window, x, y);
                    match self.widget_scroll(x, y, amount) {
                        Ok(true) => {
                            window.request_redraw();
                        }
                        Err(e) => eprintln!("widget scroll: {e}"),
                        Ok(false) => {}
                    }
                }
            }
            WindowEvent::ModifiersChanged(modifiers) => {
                self.modifiers = modifiers.state();
                if let Some(scripts) = &mut self.scripts {
                    scripts.modifiers = [
                        self.modifiers.control_key(),
                        self.modifiers.shift_key(),
                        self.modifiers.alt_key(),
                    ];
                }
            }
            WindowEvent::CursorMoved { position, .. } => {
                self.scene.input.pointer =
                    scene::pointer(position.x, position.y, window.scale_factor());
                self.cursor = Some((position.x, position.y));
                let (x, y) = self.ui_pointer(&window, position.x, position.y);
                self.ui.pointer(x, y);
                self.context_menu.pointer(x, y);
                self.widget_menu.pointer(x, y);
                self.hover_dirty = true;
                window.request_redraw();
            }
            WindowEvent::CursorLeft { .. } => {
                self.scene.input.middle = false;
                self.cursor = None;
                self.ui.clear_hover();
                self.hover = None;
                self.hover_dirty = true;
                window.request_redraw();
            }
            WindowEvent::MouseInput {
                state,
                button: MouseButton::Middle,
                ..
            } if self.scene.camera.is_some() => {
                self.scene.input.middle = state == ElementState::Pressed;
            }
            WindowEvent::MouseInput {
                state: ElementState::Pressed,
                button: MouseButton::Left,
                ..
            } if self.assets.is_some() => {
                if let Some((x, y)) = self.cursor {
                    let (x, y) = self.ui_pointer(&window, x, y);
                    if self.state.authenticated_player().is_some()
                        && self.state.interfaces().root().is_some()
                    {
                        if self.widget_pointer_press(x, y) {
                            window.request_redraw();
                            return;
                        }
                        match self.widget_click(x, y, false) {
                            Ok(true) => {
                                window.request_redraw();
                                return;
                            }
                            Err(e) => {
                                eprintln!("widget action: {e}");
                                return;
                            }
                            Ok(false) => {}
                        }
                        match self.world_click(&window, x, y, false) {
                            Ok(true) => {
                                window.request_redraw();
                                return;
                            }
                            Err(error) => {
                                eprintln!("world action: {error}");
                                window.set_title(&format!("RustClient - {error}"));
                                window.request_redraw();
                                return;
                            }
                            Ok(false) => {}
                        }
                    }
                    let operation = if self.state.loaded_scene().is_some() {
                        if self.scene.diagnostics {
                            (TitleUi::world_disconnect_hit(x, y)).then_some(
                                SemanticOperation::SetTitleScreen(
                                    rustclient_game_state::TitleScreen::Welcome,
                                ),
                            )
                        } else {
                            None
                        }
                    } else if self.state.authenticated_player().is_some() {
                        TitleUi::disconnect_hit(x, y).then_some(SemanticOperation::SetTitleScreen(
                            rustclient_game_state::TitleScreen::Welcome,
                        ))
                    } else {
                        self.ui.click(self.state.title_screen(), x, y)
                    };
                    self.apply(operation);
                    match self.walk_click(&window, x, y) {
                        Ok(_) => {}
                        Err(error) => {
                            eprintln!("movement: {error}");
                            window.set_title(&format!("RustClient - {error}"));
                        }
                    }
                }
            }
            WindowEvent::MouseInput {
                state: ElementState::Released,
                button: MouseButton::Left,
                ..
            } => {
                if let Some((x, y)) = self.cursor {
                    let (x, y) = self.ui_pointer(&window, x, y);
                    self.widget_pointer_release(x, y);
                }
            }
            WindowEvent::MouseInput {
                state: ElementState::Pressed,
                button: MouseButton::Right,
                ..
            } if self.state.authenticated_player().is_some()
                && self.state.interfaces().root().is_some() =>
            {
                if let Some((x, y)) = self.cursor {
                    let (x, y) = self.ui_pointer(&window, x, y);
                    match self.widget_click(x, y, true) {
                        Ok(true) => {
                            window.request_redraw();
                            return;
                        }
                        Err(e) => {
                            eprintln!("widget action: {e}");
                            return;
                        }
                        Ok(false) => {}
                    }
                    if let Err(error) = self.world_click(&window, x, y, true) {
                        eprintln!("world action: {error}");
                    }
                    window.request_redraw();
                }
            }
            WindowEvent::Focused(false) => {
                self.scene.input = rustclient_renderer::CameraInput::default();
                self.widget_pointer = widget_pointer::WidgetPointer::default();
                self.modifiers = ModifiersState::default();
                if let Some(scripts) = &mut self.scripts {
                    scripts.modifiers = [false; 3];
                }
            }
            WindowEvent::Focused(true) => window.request_redraw(),
            WindowEvent::Occluded(occluded) => {
                self.occluded = occluded;
                if !occluded {
                    window.request_redraw();
                }
            }
            WindowEvent::KeyboardInput { event, .. } if self.assets.is_some() => {
                if event.state == ElementState::Pressed
                    && !event.repeat
                    && event.logical_key == Key::Named(NamedKey::F11)
                {
                    window.set_fullscreen(if window.fullscreen().is_some() {
                        None
                    } else {
                        Some(winit::window::Fullscreen::Borderless(None))
                    });
                    return;
                }
                let held = event.state == ElementState::Pressed;
                if camera_input::route(
                    &mut self.scene.input,
                    &event.logical_key,
                    held,
                    self.widget_menu.is_open() || self.context_menu.is_open(),
                ) {
                    window.request_redraw();
                    return;
                }
                if self.widget_menu.is_open() {
                    if event.state == ElementState::Pressed {
                        match &event.logical_key {
                            Key::Named(NamedKey::Escape) => self.widget_menu.close(),
                            Key::Named(NamedKey::Enter) => {
                                if let Some(action) = self.widget_menu.activate()
                                    && let Err(e) = self.send_widget_action(action)
                                {
                                    eprintln!("widget action: {e}");
                                }
                            }
                            _ => {}
                        }
                        window.request_redraw();
                    }
                    return;
                }
                if self.context_menu.is_open() {
                    if event.state == ElementState::Pressed {
                        match &event.logical_key {
                            Key::Named(NamedKey::Escape) => self.context_menu.close(),
                            Key::Named(NamedKey::Enter) => {
                                if let Some(choice) = self.context_menu.activate() {
                                    let (x, y) = self
                                        .cursor
                                        .map_or((0., 0.), |(x, y)| self.ui_pointer(&window, x, y));
                                    if let Err(error) = self.run_world_choice(&window, choice, x, y)
                                    {
                                        eprintln!("world action: {error}");
                                    }
                                }
                            }
                            _ => {}
                        }
                        window.request_redraw();
                    }
                    return;
                }
                if held {
                    self.keyboard(event_loop, &window, event);
                } else if self.state.authenticated_player().is_some()
                    && let Some(key) = widget_key(&event.logical_key)
                {
                    self.game_key(Some(key), None, false);
                }
            }
            WindowEvent::Ime(Ime::Preedit(value, _)) => self.ime_preedit = !value.is_empty(),
            WindowEvent::Ime(Ime::Commit(text)) if self.assets.is_some() => {
                self.ime_preedit = false;
                if self.state.authenticated_player().is_some() {
                    for ch in text.chars().filter(|ch| !ch.is_control()) {
                        self.game_key(None, Some(ch), true);
                    }
                } else {
                    self.ui.text(self.state.title_screen(), &text);
                }
                self.apply(None);
            }
            _ => {}
        }
    }
    #[expect(
        clippy::too_many_lines,
        reason = "frame scheduling, timer commits, plugin dispatch and asset upload in one loop"
    )]
    fn about_to_wait(&mut self, event_loop: &ActiveEventLoop) {
        let tick_start = Instant::now();
        if self.state.authenticated_player().is_some() {
            if let Some(window) = self.window.clone() {
                self.update_hover(&window);
                if self.click_cross.is_some() {
                    window.request_redraw();
                }
            }
            self.synchronize_window();
            self.plugins.start(&self.state);
            let now = Instant::now();
            let mut advanced = false;
            let mut steps: u32 = 0;
            while now >= self.next_step && steps < 5 {
                steps += 1;
                advanced = true;
                let operation = SemanticOperation::Scenery(
                    rustclient_game_state::SceneryOperation::AdvanceCycles(1),
                );
                let result = if let Some(scripts) = &mut self.scripts {
                    scripts.commit(&mut self.state, operation)
                } else {
                    self.state
                        .apply_operation(operation)
                        .map(|_| Vec::new())
                        .map_err(|e| e.to_string())
                };
                match result {
                    Ok(errors) => {
                        for error in errors {
                            if self.reported_script_errors.len() < 256
                                && self.reported_script_errors.insert(error.clone())
                            {
                                eprintln!("client timer: {error}");
                            }
                        }
                    }
                    Err(error) => eprintln!("client cycle: {error}"),
                }
                self.scene
                    .motion
                    .observe(self.state.entities(), self.state.scenery().cycle());
                self.scene.motion.advance(1);
                if let (Some(camera), Some(loaded)) = (&mut self.scene.camera, &self.scene.loaded) {
                    let target = if let Some(player) = self.state.entities().local_player() {
                        let position = self
                            .scene
                            .motion
                            .player(player.index)
                            .unwrap_or([player.tile.x * 128, player.tile.y * 128]);
                        scene::player_target(loaded, position, player.tile.plane)
                    } else {
                        scene::target(loaded)
                    };
                    camera.set_follow_height(self.state.interfaces().viewport().follow_height);
                    camera.step(target, self.scene.input);
                    if let Some(scripts) = &mut self.scripts {
                        scripts
                            .set_camera_angles(i32::from(camera.pitch()), i32::from(camera.yaw()));
                    }
                }
                self.widget_pointer_cycle();
                self.flush_script_intents();
                self.next_step += STEP;
            }
            if advanced {
                if let Some(scripts) = &mut self.scripts {
                    match scripts.resize_widgets(&mut self.state) {
                        Ok(errors) => {
                            for error in errors {
                                if self.reported_script_errors.len() < 256
                                    && self.reported_script_errors.insert(error.clone())
                                {
                                    eprintln!("widget resize: {error}");
                                }
                            }
                        }
                        Err(error) => eprintln!("widget layout: {error}"),
                    }
                }
                self.flush_script_intents();
                self.plugins.advance(&self.state, steps);
            }
            if now >= self.next_step {
                self.next_step = now + STEP;
            }
            // A hidden window presents nothing: posing scenery and entities for it is wasted
            // work, and the next visible cycle rebuilds both from authoritative state.
            let visible = !self.occluded;
            if advanced
                && visible
                && self.scene.animator_in_flight.is_none()
                && let Some(animator) = &self.scene.animator
                && animator
                    .try_send(self.scene.scenery_inputs(self.state.scenery()))
                    .is_ok()
            {
                self.scene.animator_in_flight = Some(self.state.scenery().cycle());
            }
            if advanced
                && visible
                && let Some(loaded) = &self.scene.loaded
            {
                if let Some(request) = self.scene.entity_catalog.request_missing(
                    &loaded.asset,
                    self.state.entities(),
                    self.state.scenery(),
                ) && let Some(loader) = &self.scene.entity_loader
                {
                    let _ = loader.send(request);
                }
                if self.entity_report.0.elapsed() >= Duration::from_secs(2) {
                    let report = self.scene.entity_catalog.report(
                        &loaded.asset,
                        self.state.entities(),
                        self.state.scenery().variables(),
                    );
                    self.entity_report.0 = Instant::now();
                    if report != self.entity_report.1 {
                        eprintln!("entities: {report}");
                        self.entity_report.1 = report;
                    }
                }
                self.scene.entity_geometry.clear();
                if let Err(e) = build_entities_geometry(
                    &loaded.asset,
                    &self.scene.entity_catalog,
                    self.state.entities(),
                    &self.scene.motion,
                    self.state.scenery().variables(),
                    &mut self.scene.entity_geometry,
                ) {
                    eprintln!("entity geometry: {e}");
                    self.scene.entity_geometry.clear();
                }
                if let Some(renderer) = &mut self.renderer
                    && let Err(e) = renderer.set_world_entities(
                        loaded.asset.descriptor().id(),
                        &self.scene.entity_geometry,
                    )
                {
                    eprintln!("entity geometry upload: {e}");
                }
            }
            if advanced && let Some(window) = &self.window {
                self.hover_dirty = true;
                window.request_redraw();
            }
            self.performance.tick(tick_start);
            event_loop.set_control_flow(ControlFlow::WaitUntil(self.next_step));
            return;
        }
        if let (Some(assets), Some(flames)) = (&self.assets, &mut self.flames) {
            let now = Instant::now();
            let mut steps = 0;
            while now >= self.next_step && steps < 5 {
                let random = &mut self.random;
                flames.step(&assets.runes, || random.next_sample().get());
                self.cycle = self.cycle.wrapping_add(1);
                self.next_step += STEP;
                steps += 1;
            }
            if steps > 0
                && let Some(window) = &self.window
            {
                window.request_redraw();
            }
            if now >= self.next_step {
                self.next_step = now + STEP;
            }
            event_loop.set_control_flow(ControlFlow::WaitUntil(self.next_step));
        } else {
            event_loop.set_control_flow(ControlFlow::Wait);
        }
    }
}
impl NativeApp {
    fn ui_pointer(&self, window: &Window, x: f64, y: f64) -> (f32, f32) {
        let size = window.inner_size();
        TitleViewport {
            canvas_width: if self.state.authenticated_player().is_some() {
                self.state.interfaces().window().canvas.width
            } else {
                765
            },
            physical_width: size.width,
            physical_height: size.height,
            scale: window.scale_factor(),
        }
        .pointer(x, y)
    }
}

fn run() -> Result<(), String> {
    let Some(options) = options::Options::parse()? else {
        return Ok(());
    };
    if options.capture_dir.is_some() {
        return capture::run(&options);
    }
    let event_loop = EventLoop::<LoadEvent>::with_user_event()
        .build()
        .map_err(|e| format!("event loop: {e}"))?;
    let proxy = event_loop.create_proxy();
    let mut app = NativeApp::new(&options, proxy.clone());
    std::thread::Builder::new()
        .name("title-cache-loader".into())
        .spawn(move || {
            let result = DirectoryBlobs::open(&options.cache_dir)
                .map_err(|e| e.to_string())
                .and_then(|source| {
                    load_title(source, options.profile, |name| {
                        let _ = proxy.send_event(LoadEvent::Progress(name.to_owned()));
                    })
                    .map_err(|e| e.to_string())
                });
            let scripts = DirectoryBlobs::open(&options.cache_dir)
                .map_err(|e| e.to_string())
                .and_then(|source| rustclient_cache::Cache::open(source).map_err(|e| e.to_string()))
                .map(|cache| {
                    rustclient_application::scripts::ClientScripts::new(cache, options.profile)
                });
            let _ = proxy.send_event(LoadEvent::ScriptsReady(Box::new(scripts)));
            let _ = proxy.send_event(LoadEvent::Loaded(Box::new(result)));
        })
        .map_err(|e| format!("cache worker: {e}"))?;
    event_loop
        .run_app(&mut app)
        .map_err(|e| format!("event loop: {e}"))?;
    if app.failed {
        Err("client startup or rendering failed (see diagnostic above)".into())
    } else {
        Ok(())
    }
}
fn main() -> ExitCode {
    match run() {
        Ok(()) => ExitCode::SUCCESS,
        Err(error) => {
            eprintln!("{error}");
            ExitCode::FAILURE
        }
    }
}

fn widget_key(key: &Key) -> Option<rustclient_model::WidgetKey> {
    use rustclient_model::WidgetKey as W;
    Some(match key {
        Key::Named(NamedKey::Enter) => W::Enter,
        Key::Named(NamedKey::Backspace) => W::Backspace,
        Key::Named(NamedKey::Tab) => W::Tab,
        Key::Named(NamedKey::Escape) => W::Escape,
        Key::Named(NamedKey::ArrowLeft) => W::Left,
        Key::Named(NamedKey::ArrowRight) => W::Right,
        Key::Named(NamedKey::ArrowUp) => W::Up,
        Key::Named(NamedKey::ArrowDown) => W::Down,
        Key::Named(NamedKey::Delete) => W::Delete,
        Key::Named(NamedKey::Home) => W::Home,
        Key::Named(NamedKey::End) => W::End,
        Key::Named(NamedKey::PageUp) => W::PageUp,
        Key::Named(NamedKey::PageDown) => W::PageDown,
        _ => return None,
    })
}
