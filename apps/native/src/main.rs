#![forbid(unsafe_code)]
mod capture;
mod interaction;
mod network;
mod options;
mod plugins;
mod scene;
mod widgets;

use rustclient_application::{
    DeterministicRandomSource, RandomSeed, SplitMix64Random, load_title, load_title_preferences,
    save_title_preferences,
};
use rustclient_cache::TitleAssets;
use rustclient_game_state::{GameState, SemanticOperation};
use rustclient_game_ui::{Flames, GameFrameUi, TitlePreferences, TitleUi, TitleViewport, UiFrame};
use rustclient_input::EditKey;
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

struct NativeApp {
    scene: scene::SceneView,
    window: Option<Arc<Window>>,
    renderer: Option<SurfaceRenderer>,
    assets: Option<TitleAssets>,
    state: GameState,
    ui: TitleUi,
    gameframe_ui: GameFrameUi,
    context_menu: rustclient_game_ui::ContextMenu,
    widget_menu: rustclient_game_ui::ContextMenu<rustclient_model::WidgetAction>,
    reported_script_errors: std::collections::BTreeSet<String>,
    scripts: Option<rustclient_application::scripts::ClientScripts<DirectoryBlobs>>,
    widget_assets: rustclient_game_ui::widget_visuals::WidgetVisualAssets,
    widget_requested: rustclient_application::scripts::WidgetAssetRequest,
    widget_loading: bool,
    widget_tree: rustclient_game_ui::widgets::WidgetTree,
    widget_cache_dir: std::path::PathBuf,
    widget_cache_profile: rustclient_cache::CacheProfile,
    minimap: Option<MinimapImage>,
    minimap_pixels: Vec<u8>,
    compass_pixels: Vec<u8>,
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
            gameframe_ui: GameFrameUi::new(),
            context_menu: rustclient_game_ui::ContextMenu::default(),
            widget_menu: rustclient_game_ui::ContextMenu::default(),
            reported_script_errors: std::collections::BTreeSet::default(),
            scripts: None,
            widget_assets: rustclient_game_ui::widget_visuals::WidgetVisualAssets::default(),
            widget_requested: rustclient_application::scripts::WidgetAssetRequest::default(),
            widget_loading: false,
            widget_tree: rustclient_game_ui::widgets::WidgetTree::default(),
            widget_cache_dir: options.cache_dir.clone(),
            widget_cache_profile: options.profile,
            minimap: None,
            minimap_pixels: vec![0; 156 * 156 * 4],
            compass_pixels: vec![0; 33 * 33 * 4],
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
                if let Some(scene) = &self.scene.loaded
                    && let Err(error) = renderer.set_world(&scene.mesh)
                {
                    self.fail(event_loop, &error.to_string());
                    return;
                }
                if let Some(scene) = &self.scene.loaded
                    && let Err(error) = renderer.set_world_dynamic(
                        scene.asset.descriptor().id(),
                        &self.scene.displayed_dynamic,
                    )
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
        self.widget_menu.close();
        self.widget_tree.nodes.clear();
        self.widget_requested = rustclient_application::scripts::WidgetAssetRequest::default();
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
        }
        if let Some(credentials) = self.ui.take_submission() {
            self.disconnect();
            if let Some(options) = self.connection_options.clone() {
                match network::spawn(
                    options,
                    credentials,
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
    #[expect(
        clippy::too_many_lines,
        reason = "one frame projects every interface layer in paint order"
    )]
    fn redraw(&mut self, event_loop: &ActiveEventLoop) {
        let Some(window) = self.window.clone() else {
            return;
        };
        let result = if let (Some(assets), Some(flames)) = (&self.assets, &self.flames) {
            if self.state.loaded_scene().is_some() && self.scene.camera.is_some() {
                if self.scene.diagnostics {
                    self.ui.project_world(assets, &self.state, &mut self.frame);
                } else {
                    self.frame.quads.clear();
                    self.gameframe_ui
                        .project(assets, &self.state, 765., 503., &mut self.frame);
                    if let Some(scripts) = &self.scripts {
                        match rustclient_game_ui::widget_visuals::project_widgets(
                            &self.state,
                            &scripts.interfaces,
                            &self.widget_assets,
                            &mut self.frame,
                        ) {
                            Ok(tree) => self.widget_tree = tree,
                            Err(error) => {
                                eprintln!("widget projection: {error}");
                                self.widget_tree.nodes.clear();
                            }
                        }
                    }
                }
                self.context_menu.project(assets, &mut self.frame);
                self.widget_menu.project(assets, &mut self.frame);
                self.plugins.overlay(&self.state, assets, &mut self.frame);
                if let (Some(gameframe), Some(minimap), Some(camera)) =
                    (&assets.gameframe, &self.minimap, self.scene.camera)
                {
                    let yaw_radians = f32::from(camera.yaw()) * std::f32::consts::TAU / 2048.0;
                    let origin = self.scene.loaded.as_ref().map_or_else(
                        || rustclient_model::WorldTile::new(0, 0, rustclient_model::Plane::MIN),
                        |scene| scene.asset.descriptor().origin(),
                    );
                    let camera_focus_tile =
                        if let Some(player) = self.state.entities().local_player() {
                            (
                                f64::from(player.tile.x) + 0.5,
                                f64::from(player.tile.y) + 0.5,
                            )
                        } else {
                            (
                                f64::from(origin.x) + (f64::from(camera.focal()[0]) / 128.0),
                                f64::from(origin.y) + (f64::from(camera.focal()[2]) / 128.0),
                            )
                        };
                    self.minimap_pixels = compose_minimap(
                        gameframe.minimap_mask.image.pixels(),
                        gameframe.minimap_mask.image.width() as usize,
                        gameframe.minimap_mask.image.height() as usize,
                        minimap,
                        camera_focus_tile,
                        yaw_radians,
                    );
                    self.compass_pixels = compose_compass(
                        gameframe.compass.image.pixels(),
                        gameframe.compass.image.width() as usize,
                        gameframe.compass.image.height() as usize,
                        gameframe.compass_mask.image.pixels(),
                        gameframe.compass_mask.image.width() as usize,
                        gameframe.compass_mask.image.height() as usize,
                        yaw_radians,
                    );
                }
            } else if self.state.authenticated_player().is_some() {
                self.ui
                    .project_session(assets, &self.state, &mut self.frame);
            } else {
                self.ui.project(
                    assets,
                    self.state.title_screen(),
                    self.cycle,
                    &mut self.frame,
                );
            }
            self.renderer.as_mut().map(|renderer| {
                if let (Some(descriptor), Some(camera)) =
                    (self.state.loaded_scene(), self.scene.camera)
                {
                    return renderer.render_world(
                        descriptor.id(),
                        camera,
                        self.state.scenery().cycle(),
                        &self.frame,
                        &self.minimap_pixels,
                        &self.compass_pixels,
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
        match result {
            Some(Ok(FrameOutcome::RecreateRequired)) => self.create_renderer(event_loop),
            Some(Ok(FrameOutcome::Reconfigured)) => window.request_redraw(),
            Some(Err(error)) => self.fail(event_loop, &format!("rendering: {error}")),
            _ => {}
        }
    }
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
                            self.state.scenery().into(),
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
                if let Some(placement) = self.state.initial_placement()
                    && let Err(e) = self.scene.start_animator(
                        scene.asset.clone(),
                        placement,
                        self.scene.generation,
                        self.proxy.clone(),
                    )
                {
                    eprintln!("animation worker: {e}");
                }
                if let Some(placement) = self.state.initial_placement() {
                    self.minimap =
                        Some(build_scene_minimap(&scene.asset, placement.position.plane));
                }
                self.scene.displayed_dynamic = scene.mesh.dynamic.clone();
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
                    self.widget_loading = false;
                    for error in &assets.failures {
                        eprintln!("widget asset: {error}");
                    }
                    if let (Some(renderer), Some(title)) = (&mut self.renderer, &self.assets)
                        && let Err(e) = renderer.set_widget_images(title, &assets.images)
                    {
                        eprintln!("widget GPU upload: {e}");
                        return;
                    }
                    self.widget_assets = *assets;
                    self.widget_requested = request;
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
                        self.request_widget_assets();
                        if let Some(window) = &self.window {
                            window.request_redraw();
                        }
                    }
                    network::NetworkEvent::Operation(map_epoch, interface_epoch, operation) => {
                        self.interface_epoch = interface_epoch;
                        self.map_epoch = map_epoch;
                        let rebuild = matches!(operation, SemanticOperation::RebuildMap(_));
                        let previous_scene = self.state.loaded_scene();
                        self.apply(Some(operation));
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
                                self.state.scenery().into(),
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
                                self.scene.displayed_dynamic = geometry.clone();
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
                let amount = match delta {
                    winit::event::MouseScrollDelta::LineDelta(_, y) => {
                        if y > 0. {
                            -45
                        } else {
                            45
                        }
                    }
                    winit::event::MouseScrollDelta::PixelDelta(p) => {
                        if p.y > 0. {
                            -45
                        } else {
                            45
                        }
                    }
                };
                if let Some((x, y)) = self.cursor {
                    let (x, y) = title_pointer(&window, x, y);
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
                let (x, y) = title_pointer(&window, position.x, position.y);
                self.ui.pointer(x, y);
                self.context_menu.pointer(x, y);
                self.widget_menu.pointer(x, y);
                window.request_redraw();
            }
            WindowEvent::CursorLeft { .. } => {
                self.scene.input.middle = false;
                self.cursor = None;
                self.ui.clear_hover();
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
                    let (x, y) = title_pointer(&window, x, y);
                    if self.state.loaded_scene().is_some() {
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
                            let op = self.gameframe_ui.handle_pointer_down(
                                x,
                                y,
                                765.0,
                                503.0,
                                self.state.selected_tab(),
                            );
                            if self.gameframe_ui.take_compass_click()
                                && let Some(camera) = &mut self.scene.camera
                            {
                                camera.set_yaw(0);
                            }
                            op
                        }
                    } else if self.state.authenticated_player().is_some() {
                        TitleUi::disconnect_hit(x, y).then_some(SemanticOperation::SetTitleScreen(
                            rustclient_game_state::TitleScreen::Welcome,
                        ))
                    } else {
                        self.ui.click(self.state.title_screen(), x, y)
                    };
                    self.apply(operation);
                    if let Err(error) = self.walk_click(&window, x, y) {
                        eprintln!("movement: {error}");
                        window.set_title(&format!("RustClient - {error}"));
                    }
                }
            }
            WindowEvent::MouseInput {
                state: ElementState::Pressed,
                button: MouseButton::Right,
                ..
            } if self.state.loaded_scene().is_some() => {
                if let Some((x, y)) = self.cursor {
                    let (x, y) = title_pointer(&window, x, y);
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
            }
            WindowEvent::KeyboardInput { event, .. } if self.assets.is_some() => {
                if self.widget_menu.is_open() {
                    if event.state == ElementState::Pressed {
                        match &event.logical_key {
                            Key::Named(NamedKey::ArrowUp) => self.widget_menu.navigate(-1),
                            Key::Named(NamedKey::ArrowDown) => self.widget_menu.navigate(1),
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
                            Key::Named(NamedKey::ArrowUp) => self.context_menu.navigate(-1),
                            Key::Named(NamedKey::ArrowDown) => self.context_menu.navigate(1),
                            Key::Named(NamedKey::Escape) => self.context_menu.close(),
                            Key::Named(NamedKey::Enter) => {
                                if let Some(action) = self.context_menu.activate()
                                    && let Err(error) = self.send_world_action(action)
                                {
                                    eprintln!("world action: {error}");
                                }
                            }
                            _ => {}
                        }
                        window.request_redraw();
                    }
                    return;
                }
                let held = event.state == ElementState::Pressed;
                match &event.logical_key {
                    Key::Named(NamedKey::ArrowLeft) => self.scene.input.left = held,
                    Key::Named(NamedKey::ArrowRight) => self.scene.input.right = held,
                    Key::Named(NamedKey::ArrowUp) => self.scene.input.up = held,
                    Key::Named(NamedKey::ArrowDown) => self.scene.input.down = held,
                    _ => {}
                }
                if held {
                    self.keyboard(event_loop, &window, event);
                }
            }
            WindowEvent::Ime(Ime::Preedit(value, _)) => self.ime_preedit = !value.is_empty(),
            WindowEvent::Ime(Ime::Commit(text)) if self.assets.is_some() => {
                self.ime_preedit = false;
                self.ui.text(self.state.title_screen(), &text);
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
        if self.state.authenticated_player().is_some() {
            self.plugins.start(&self.state);
            let now = Instant::now();
            let mut advanced = false;
            let mut steps: u32 = 0;
            while now >= self.next_step && steps < 5 {
                steps += 1;
                advanced = true;
                if let (Some(camera), Some(loaded)) = (&mut self.scene.camera, &self.scene.loaded) {
                    let target = if let Some(player) = self.state.entities().local_player() {
                        scene::player_target(loaded, player.tile)
                    } else {
                        scene::target(loaded)
                    };
                    camera.step(target, self.scene.input);
                }
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
                self.next_step += STEP;
            }
            if advanced {
                self.plugins.advance(&self.state, steps);
            }
            if now >= self.next_step {
                self.next_step = now + STEP;
            }
            if advanced && let Some(animator) = &self.scene.animator {
                let _ = animator.try_send(self.state.scenery().into());
            }
            if advanced && let Some(loaded) = &self.scene.loaded {
                if let Some(request) = self
                    .scene
                    .entity_catalog
                    .request_missing(&loaded.asset, self.state.entities())
                    && let Some(loader) = &self.scene.entity_loader
                {
                    let _ = loader.send(request);
                }
                let mut combined = loaded.mesh.dynamic.clone();
                if let Err(e) = build_entities_geometry(
                    &loaded.asset,
                    &self.scene.entity_catalog,
                    self.state.entities(),
                    self.state.scenery().variables(),
                    &mut combined,
                ) {
                    eprintln!("entity geometry: {e}");
                } else if let Some(renderer) = &mut self.renderer {
                    match renderer.set_world_dynamic(loaded.asset.descriptor().id(), &combined) {
                        Ok(()) => self.scene.displayed_dynamic = combined,
                        Err(e) => eprintln!("dynamic entities upload: {e}"),
                    }
                }
            }
            if advanced && let Some(window) = &self.window {
                window.request_redraw();
            }
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
fn title_pointer(window: &Window, x: f64, y: f64) -> (f32, f32) {
    let size = window.inner_size();
    TitleViewport {
        physical_width: size.width,
        physical_height: size.height,
        scale: window.scale_factor(),
    }
    .pointer(x, y)
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
