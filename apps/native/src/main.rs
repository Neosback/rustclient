#![forbid(unsafe_code)]
mod actor_overlays;
mod camera_input;
mod capture;
mod frame_limit;
mod input_activity;
mod interaction;
mod network;
mod notifications;
mod options;
mod performance;
mod plugins;
mod scene;
mod shell;
mod shell_input;
mod widget_pointer;
mod widgets;
mod world_overlays;

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
    world_map::{compose_world_map_overview, compose_world_map_viewport},
};
use std::{
    collections::BTreeMap,
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
    /// Scene-local tile under the pointer, tracked while a plugin asks for hovered-roof removal.
    roof_hover: Option<[i32; 2]>,
    hover_dirty: bool,
    /// Set once the game interface has been drawn this session; cleared on disconnect.
    entered_world: bool,
    widget_menu: rustclient_game_ui::ContextMenu<rustclient_model::WidgetAction>,
    reported_script_errors: std::collections::BTreeSet<String>,
    entity_report: (Instant, String),
    /// Hidden or fully covered windows are not drawn: the OS blocks `get_current_texture` there.
    occluded: bool,
    window_focused: bool,
    input_activity: input_activity::InputActivity,
    frame_limit: frame_limit::FrameLimit,
    /// The plugin sprite replacements currently standing in the widget sprites.
    applied_sprite_overrides: Vec<rustclient_plugin_api::SpriteOverride>,
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
    world_map_pixels: Vec<u8>,
    world_map_size: [u32; 2],
    world_map_overview_pixels: Vec<u8>,
    world_map_overview_size: [u32; 2],
    world_map_sprites: BTreeMap<u32, rustclient_cache::Sprite>,
    world_map_drag: Option<([f32; 2], [i32; 2])>,
    /// Widget-authored mask sprites expanded onto their canvas; replaced with the widget assets.
    minimap_mask: Option<(SpriteId, CanvasImage)>,
    compass_mask: Option<(SpriteId, CanvasImage)>,
    flames: Option<Flames>,
    random: SplitMix64Random,
    frame: UiFrame,
    plugins: plugins::Plugins,
    shell: shell::Shell,
    storage: Option<DirectoryStorage>,
    saved_preferences: TitlePreferences,
    modifiers: ModifiersState,
    cursor: Option<(f64, f64)>,
    ime_preedit: bool,
    next_step: Instant,
    cycle_origin: Instant,
    session_counters: rustclient_application::cycle::SessionCounters,
    keepalive_pending: bool,
    pending_authentication: Option<SemanticOperation>,
    pending_network_failure: Option<(rustclient_game_state::LoginFailure, String)>,
    cycle_clock: rustclient_application::cycle::CycleClock,
    ingress: rustclient_application::cycle::CycleIngress<Vec<network::NetworkEvent>>,
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
    /// Hand plugins the projected state: shell-owned facts plus the cache definitions in use.
    fn update_plugins(&mut self) {
        let window = self.state.interfaces().window();
        let camera = self.scene.camera.map(|camera| {
            let viewport = rustclient_renderer::CameraViewport::new(
                i32::try_from(window.canvas.width).unwrap_or(i32::MAX),
                i32::try_from(window.canvas.height).unwrap_or(i32::MAX),
            )
            .with_settings(self.state.interfaces().viewport());
            let pose = camera.pose(viewport);
            rustclient_plugin_api::CameraSnapshot {
                x: pose.position[0],
                y: pose.position[1],
                z: pose.position[2],
                pitch: i32::from(pose.pitch),
                yaw: i32::from(pose.yaw),
                zoom: pose.zoom,
            }
        });
        let asset = self.scene.loaded.as_ref().map(|loaded| &loaded.asset);
        let variables = self.state.scenery().variables();
        let catalog = &self.scene.entity_catalog;
        let resolve = |id| asset.and_then(|asset| catalog.resolve_npc(asset, id, variables));
        let inputs = plugins::Inputs {
            frame: rustclient_plugin_bridge::Frame {
                fps: self.performance.fps(),
                focused: self.window_focused,
                loading: self.scene.loading,
                camera: camera.unwrap_or_default(),
                input_idle: Some(self.input_activity.idle(Instant::now())),
            },
            sources: rustclient_plugin_bridge::Sources {
                npcs: Some(&resolve),
                varbits: asset.map(|asset| &asset.sources().varbits),
                interfaces: self.scripts.as_ref().map(|scripts| &scripts.interfaces),
            },
        };
        self.plugins.update(&self.state, &inputs);
        // Sprites a plugin draws are decoded by the same worker as widget sprites.
        if self.plugins.requested_sprites().any(|sprite| {
            !self
                .widget_requested
                .sprites
                .contains(&rustclient_model::SpriteId::new(sprite))
        }) {
            self.request_widget_assets();
        }
        let raised = self.plugins.take_notifications();
        if let (false, Some(window)) = (raised.is_empty(), &self.window) {
            notifications::deliver(window, self.window_focused, &raised);
        }
    }
    /// The stretched-mode layout for this window, while a plugin asks for stretching.
    #[expect(
        clippy::cast_possible_truncation,
        clippy::cast_sign_loss,
        reason = "window extents are validated platform values"
    )]
    fn stretch_layout(
        &self,
        window: &Window,
    ) -> Option<rustclient_game_ui::stretch::StretchLayout> {
        let stretch = self.plugins.output().stretch?;
        let size = self.shell.game_size(window);
        let scale = window.scale_factor();
        let parent = (
            (f64::from(size.width) / scale).floor() as u32,
            (f64::from(size.height) / scale).floor() as u32,
        );
        let resizable = self.state.authenticated_player().is_some()
            && self.state.interfaces().window().mode == rustclient_model::WindowMode::Resizable;
        Some(rustclient_game_ui::stretch::StretchLayout::compute(
            rustclient_game_ui::stretch::StretchSettings {
                keep_aspect_ratio: stretch.keep_aspect_ratio,
                integer_scaling: stretch.integer_scaling,
                scaling_percent: i32::from(stretch.scale_percent),
            },
            resizable,
            parent,
        ))
    }
    /// Swap the interface sprites plugins replace into the widget assets and re-upload them.
    fn sync_sprite_overrides(&mut self) {
        let wanted = &self.plugins.output().sprite_overrides;
        if *wanted == self.applied_sprite_overrides {
            return;
        }
        let source = |id| {
            rustclient_cache::Source::group(
                rustclient_cache::IndexId(8),
                rustclient_cache::GroupId(id),
            )
        };
        let images = wanted
            .iter()
            .filter_map(|sprite| {
                rustclient_cache::Image::new(
                    u32::from(sprite.width),
                    u32::from(sprite.height),
                    sprite.rgba.to_vec(),
                    source(sprite.sprite),
                )
                .ok()
                .map(|image| (sprite.sprite, image))
            })
            .collect();
        self.applied_sprite_overrides.clone_from(wanted);
        if !self.widget_assets.set_sprite_overrides(images) {
            return;
        }
        if let (Some(renderer), Some(title)) = (&mut self.renderer, &self.assets)
            && let Err(error) = renderer.set_widget_images(title, &self.widget_assets.images)
        {
            eprintln!("sprite override GPU upload: {error}");
        }
        self.minimap_mask = None;
        self.compass_mask = None;
    }
    /// Follow the camera preferences plugins ask for; no request means the unmodified client.
    fn sync_camera_tuning(&mut self) {
        use rustclient_plugin_api::CameraCommand;
        let mut tuning = rustclient_renderer::CameraTuning::default();
        for command in &self.plugins.output().camera {
            match *command {
                CameraCommand::Speed(speed) => tuning.speed = speed,
                CameraCommand::InvertYaw(on) => tuning.invert_yaw = on,
                CameraCommand::InvertPitch(on) => tuning.invert_pitch = on,
                CameraCommand::RelaxPitch(on) => tuning.relax_pitch = on,
                _ => {}
            }
        }
        if let Some(camera) = &mut self.scene.camera {
            camera.set_tuning(tuning);
        }
    }
    /// Follow the actors plugins ask to hide.
    fn sync_hidden_actors(&mut self) {
        use rustclient_plugin_api::ActorRef;
        let entities = &self.plugins.output().entities;
        let split = |actors: &[ActorRef]| {
            let (mut players, mut npcs) = (Vec::new(), Vec::new());
            for actor in actors {
                match actor {
                    ActorRef::Player(index) => players.push(u32::from(*index)),
                    ActorRef::Npc(index) => npcs.push(u32::from(*index)),
                }
            }
            rustclient_scene::entity_mesh::HiddenActors::new(players, npcs)
        };
        let (models, overlays) = (split(&entities.hidden), split(&entities.hidden_overlays));
        if models != self.scene.hidden_models {
            self.scene.hidden_models = models;
        }
        if overlays != self.scene.hidden_overlays {
            self.scene.hidden_overlays = overlays;
        }
    }
    /// Follow the animation smoothing plugins ask for; poses are rebuilt every cycle anyway.
    fn sync_interpolation(&mut self) {
        let output = self.plugins.output();
        let smoothing = output.scene.smoothing;
        let wanted = rustclient_scene::AnimationInterpolation::new(
            smoothing.contains(rustclient_plugin_api::AnimationSmoothing::PLAYERS),
            smoothing.contains(rustclient_plugin_api::AnimationSmoothing::NPCS),
            smoothing.contains(rustclient_plugin_api::AnimationSmoothing::OBJECTS),
            output.smoothing_denied.clone(),
        );
        if wanted != self.scene.interpolation {
            self.scene.interpolation = wanted;
        }
    }
    /// Follow the roof areas plugins ask for, rebuilding the scene's roofs when they change.
    fn sync_roof_areas(&mut self) {
        let wanted: std::sync::Arc<[rustclient_scene::RoofArea]> = self
            .plugins
            .output()
            .roof_areas
            .iter()
            .map(|area| rustclient_scene::RoofArea {
                min: [area.min.0, area.min.1],
                max: [area.max.0, area.max.1],
                planes: [area.planes.0, area.planes.1],
            })
            .collect();
        if wanted != self.scene.roof_areas {
            self.scene.roof_areas = wanted;
        }
        if self.scene.loading {
            return;
        }
        let Some(loaded) = &self.scene.loaded else {
            return;
        };
        if loaded.projection_inputs.roof_areas == self.scene.roof_areas {
            return;
        }
        let (Some(placement), asset) = (self.state.initial_placement(), loaded.asset.clone())
        else {
            return;
        };
        if let Err(error) = self.scene.reproject(
            asset,
            placement,
            self.scene.scenery_inputs(self.state.scenery()),
            self.scene.generation,
            self.proxy.clone(),
        ) {
            eprintln!("roof projection: {error}");
        }
    }
    fn new(
        options: &options::Options,
        proxy: winit::event_loop::EventLoopProxy<LoadEvent>,
    ) -> Result<Self, String> {
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
        let mut plugins = plugins::Plugins::load(
            &options.plugins_dir,
            &options.preferences_dir.join("plugin-data"),
        );
        let shell = shell::Shell::new(
            storage
                .as_ref()
                .map(|store| store as &dyn rustclient_platform::BlobStorage),
        )?;
        plugins.sync(shell.configurations());
        Ok(Self {
            shell,
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
            roof_hover: None,
            hover_dirty: false,
            entered_world: false,
            widget_menu: rustclient_game_ui::ContextMenu::default(),
            reported_script_errors: std::collections::BTreeSet::default(),
            entity_report: (Instant::now(), String::new()),
            occluded: false,
            window_focused: true,
            input_activity: input_activity::InputActivity::new(),
            frame_limit: frame_limit::FrameLimit::default(),
            applied_sprite_overrides: Vec::new(),
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
            world_map_pixels: Vec::new(),
            world_map_size: [0, 0],
            world_map_overview_pixels: Vec::new(),
            world_map_overview_size: [0, 0],
            world_map_sprites: BTreeMap::new(),
            world_map_drag: None,
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
            cycle_origin: Instant::now(),
            session_counters: rustclient_application::cycle::SessionCounters::default(),
            keepalive_pending: false,
            pending_authentication: None,
            pending_network_failure: None,
            cycle_clock: rustclient_application::cycle::CycleClock::default(),
            ingress: rustclient_application::cycle::CycleIngress::default(),
            cycle: 0,
            failed: false,
            load_status: "Opening cache…".into(),
        })
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
                self.shell.apply_current(&window, Some(&mut renderer));
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
    fn validate_outbound(&self, outbound: &network::Outbound) -> bool {
        if let Some(action) = outbound.world_action {
            return self.scene.loaded.as_ref().is_some_and(|loaded| {
                rustclient_application::validate_world_action(
                    &self.state,
                    &loaded.asset,
                    &self.scene.entity_catalog,
                    action,
                    false,
                )
                .is_ok()
            });
        }
        match &outbound.intent {
            rustclient_protocol::ClientIntent::Walk {
                destination,
                invert_run,
            }
            | rustclient_protocol::ClientIntent::MinimapWalk {
                destination,
                invert_run,
                ..
            } => self
                .scene
                .loaded
                .as_ref()
                .zip(self.state.loaded_scene())
                .is_some_and(|(loaded, scene)| {
                    rustclient_application::validate_walk(
                        &self.state,
                        &loaded.asset,
                        scene,
                        *destination,
                        *invert_run,
                    )
                    .is_ok()
                }),
            rustclient_protocol::ClientIntent::WidgetAction(action) => {
                self.scripts.as_ref().is_some_and(|scripts| {
                    rustclient_application::validate_widget_action(
                        &self.state,
                        &scripts.interfaces,
                        *action,
                    )
                    .is_ok()
                })
            }
            _ => true,
        }
    }
    fn apply_session_control(&mut self, control: rustclient_protocol::SessionControl) {
        match control {
            rustclient_protocol::SessionControl::Ping { first, second } => {
                if let Some(connection) = &self.connection {
                    let fps = self.performance.fps();
                    if let Err(error) = connection.send(network::Outbound {
                        world_action: None,
                        map_epoch: self.map_epoch,
                        interface_epoch: None,
                        intent: rustclient_protocol::ClientIntent::PingReply { first, second, fps },
                    }) {
                        eprintln!("ping reply: {error}");
                    }
                }
            }
            end => {
                self.disconnect();
                self.apply(Some(SemanticOperation::SetTitleScreen(
                    rustclient_game_state::TitleScreen::Welcome,
                )));
                let message = match end {
                    rustclient_protocol::SessionControl::LogoutWithReason(reason) => {
                        format!("Server logout (reason {reason})")
                    }
                    rustclient_protocol::SessionControl::Transfer { world, .. } => {
                        format!("World transfer requested: {world}; world transfer unavailable")
                    }
                    _ => "Logged out".to_owned(),
                };
                if let Some(window) = &self.window {
                    window.set_title(&format!("RustClient — {message}"));
                }
            }
        }
    }
    fn apply_server_script(&mut self, epoch: u64, request: rustclient_model::ScriptRequest) {
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
    fn apply_network_event(&mut self, event: network::NetworkEvent) {
        match event {
            network::NetworkEvent::Control(control) => self.apply_session_control(control),
            network::NetworkEvent::Ready => {}
            network::NetworkEvent::Received => self.session_counters.received(),
            network::NetworkEvent::Written => {
                self.session_counters.written();
                self.keepalive_pending = false;
            }
            network::NetworkEvent::Batch(batch) => {
                for event in batch.payload {
                    self.apply_network_event(event);
                }
            }
            network::NetworkEvent::Progress(message) => {
                if let Some(window) = &self.window {
                    window.set_title(&format!("RustClient - {message}"));
                }
            }
            network::NetworkEvent::Script(epoch, request) => {
                self.apply_server_script(epoch, request);
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
                if previous_scene != self.state.loaded_scene() {
                    self.context_menu.close();
                }
                if self.state.loaded_scene().is_none() && (rebuild || previous_scene.is_some()) {
                    self.context_menu.close();
                    self.scene.invalidate_map();
                    self.minimap = None;
                } else if rebuild && self.state.loaded_scene().is_some() {
                    self.acknowledge_map();
                }
                if let Some(loaded) = &mut self.scene.loaded {
                    let mut current: rustclient_scene::SceneryInputs = self.state.scenery().into();
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
                            self.scene.scenery_inputs(self.state.scenery()),
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
    fn enqueue_network_event(&mut self, event: network::NetworkEvent) {
        let batch = match event {
            network::NetworkEvent::Batch(batch) => batch,
            network::NetworkEvent::Operation(
                _,
                _,
                op @ SemanticOperation::AuthenticateSession { .. },
            ) => {
                self.pending_authentication = Some(op);
                return;
            }
            network::NetworkEvent::Failed(category, diagnostic) => {
                self.pending_network_failure = Some((category, diagnostic));
                return;
            }
            event @ network::NetworkEvent::Operation(..) => {
                rustclient_application::cycle::InboundBatch {
                    packets: 1,
                    wire_bytes: 1,
                    payload: vec![event],
                }
            }
            event => {
                self.apply_network_event(event);
                return;
            }
        };
        if let Err(error) = self.ingress.enqueue(batch) {
            self.pending_network_failure = Some((
                rustclient_game_state::LoginFailure::Protocol,
                error.to_string(),
            ));
        }
    }
    fn disconnect(&mut self) {
        self.ingress.clear();
        self.pending_authentication = None;
        self.pending_network_failure = None;
        self.session_counters = rustclient_application::cycle::SessionCounters::default();
        self.keepalive_pending = false;
        self.cycle_origin = Instant::now();
        self.cycle_clock = rustclient_application::cycle::CycleClock::new(
            rustclient_platform::MonotonicTime::default(),
        );
        self.entered_world = false;
        self.widget_pointer = widget_pointer::WidgetPointer::default();
        self.hover = None;
        self.roof_hover = None;
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
        // GPU pacing and an optional FPS plugin share the existing presentation clock.
        if !self
            .frame_limit
            .allow(self.presentation_limit(), Instant::now())
        {
            return;
        }
        let frame_start = Instant::now();
        if let Err(error) = self.shell.update_panel(&window) {
            eprintln!("shell input: {error}");
        }
        if self.shell.actions(
            &window,
            self.renderer.as_mut(),
            self.storage
                .as_ref()
                .map(|store| store as &dyn rustclient_platform::BlobStorage),
        ) {
            self.plugins.sync(self.shell.configurations());
        }
        self.shell.settle(&window);
        // All projections use the same current window extent, including platforms which return
        // an immediate size without delivering a separate resize event.
        self.synchronize_window();
        if let Some(renderer) = &mut self.renderer {
            let size = window.inner_size();
            if let Err(error) = renderer.resize(SurfaceSize::new(size.width, size.height)) {
                eprintln!("presentation resize: {error}");
            }
        }
        let stretch = self
            .stretch_layout(&window)
            .map(|layout| layout.canvas(window.scale_factor()));
        if let Some(renderer) = &mut self.renderer {
            renderer.set_stretch(stretch);
            if let Err(error) = self.shell.stage(&window, renderer) {
                eprintln!("retained shell: {error}");
            }
        }
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
                let visibility = self.scene_visibility(&window);
                actor_overlays::project(
                    &self.scene,
                    &self.state,
                    assets,
                    &mut self.frame,
                    &visibility,
                );
                interaction::project_pointer_overlays(
                    &mut self.frame,
                    assets,
                    &self.widget_assets,
                    self.hover.as_ref(),
                    &mut self.click_cross,
                    &self.state,
                );
                self.context_menu
                    .project(assets, &self.widget_assets.font_icons, &mut self.frame);
                self.widget_menu
                    .project(assets, &self.widget_assets.font_icons, &mut self.frame);
                world_overlays::project(
                    &self.scene,
                    assets,
                    &self.plugins.output().overlay,
                    &mut self.frame,
                    &visibility,
                );
                self.plugins
                    .overlay(&self.state, assets, &self.widget_assets, &mut self.frame);
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
                    if let Some(quad) = self
                        .frame
                        .quads
                        .iter()
                        .find(|q| q.image == rustclient_game_ui::UiImageId::WorldMap)
                    {
                        #[expect(clippy::cast_possible_truncation, clippy::cast_sign_loss)]
                        let width = quad.rect.width.round().max(1.0) as u32;
                        #[expect(clippy::cast_possible_truncation, clippy::cast_sign_loss)]
                        let height = quad.rect.height.round().max(1.0) as u32;
                        self.world_map_size = [width, height];
                        if let Some(scripts) = &mut self.scripts {
                            scripts.world_map.display_size = [
                                i32::try_from(width).unwrap_or(0),
                                i32::try_from(height).unwrap_or(0),
                            ];
                            scripts.world_map.flash_cycle =
                                scripts.world_map.flash_cycle.wrapping_add(1);
                            let player_coord = self.state.entities().local_player().map(|p| {
                                rustclient_cache::osrs240::world_map::MapCoord::new(
                                    i32::from(p.tile.plane.get()),
                                    p.tile.x,
                                    p.tile.y,
                                )
                            });
                            if let Some(composite) = &scripts.world_map.active_composite {
                                for icon in &composite.icons {
                                    if let Some(elem_id) = icon.element
                                        && let Some(elem) = scripts.world_map.elements.get(&elem_id)
                                        && let Some(sprite_id) = elem.sprite
                                        && !self.world_map_sprites.contains_key(&sprite_id)
                                        && let Ok(cache) =
                                            crate::scene::SceneView::open_shared_cache(
                                                &self.widget_cache_dir,
                                                &self.scene.shared_cache,
                                            )
                                        && let Ok(Some(sprite)) = cache.sprite(sprite_id)
                                    {
                                        self.world_map_sprites.insert(sprite_id, sprite);
                                    }
                                }
                            }
                            compose_world_map_viewport(
                                width,
                                height,
                                scripts.world_map.center_tile,
                                scripts.world_map.zoom,
                                scripts.world_map.active_area(),
                                scripts.world_map.active_composite.as_ref(),
                                &scripts.world_map.elements,
                                &self.world_map_sprites,
                                &scripts.world_map.disabled_elements,
                                &scripts.world_map.disabled_categories,
                                scripts.world_map.elements_disabled,
                                &scripts.world_map.flashing_elements,
                                &scripts.world_map.flashing_categories,
                                scripts.world_map.flash_cycle,
                                player_coord,
                                &mut self.world_map_pixels,
                            );
                        }
                    } else {
                        self.world_map_size = [0, 0];
                        self.world_map_pixels.clear();
                    }

                    if let Some(quad) = self
                        .frame
                        .quads
                        .iter()
                        .find(|q| q.image == rustclient_game_ui::UiImageId::WorldMapOverview)
                    {
                        #[expect(clippy::cast_possible_truncation, clippy::cast_sign_loss)]
                        let width = quad.rect.width.round().max(1.0) as u32;
                        #[expect(clippy::cast_possible_truncation, clippy::cast_sign_loss)]
                        let height = quad.rect.height.round().max(1.0) as u32;
                        self.world_map_overview_size = [width, height];
                        if let Some(scripts) = &self.scripts {
                            compose_world_map_overview(
                                width,
                                height,
                                scripts.world_map.center_tile,
                                scripts.world_map.display_size,
                                scripts.world_map.active_area(),
                                scripts.world_map.active_composite.as_ref(),
                                &mut self.world_map_overview_pixels,
                            );
                        }
                    } else {
                        self.world_map_overview_size = [0, 0];
                        self.world_map_overview_pixels.clear();
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
            let pointer = self.cursor.map(|(x, y)| self.ui_pointer(&window, x, y));
            self.shell
                .project_overlay(&mut self.frame, &self.widget_tree, pointer);
            let visibility = self.scene_visibility(&window);
            self.renderer.as_mut().map(|renderer| {
                if let (Some(descriptor), Some(camera)) =
                    (self.state.loaded_scene(), self.scene.camera)
                {
                    return renderer.render_world(
                        descriptor.id(),
                        camera,
                        &visibility,
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
                            world_map: rustclient_renderer::DynamicImage {
                                pixels: &self.world_map_pixels,
                                width: self.world_map_size[0],
                                height: self.world_map_size[1],
                            },
                            world_map_overview: rustclient_renderer::DynamicImage {
                                pixels: &self.world_map_overview_pixels,
                                width: self.world_map_overview_size[0],
                                height: self.world_map_overview_size[1],
                            },
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
                "quads {} world_viewport {:?} nodes {} root {:?} mounts {:?} entered {} loaded {} result {:?} shell {:?} geometry {:?} picking {:?}",
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
                self.shell.panel.stats(),
                self.renderer.as_ref().map(rustclient_renderer::SurfaceRenderer::world_geometry_stats),
                self.scene.loaded.as_ref().map(|loaded| loaded.picking.stats()),
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
                let stale = scene.projection_generation != self.state.scenery().generation();
                if stale {
                    // Install the already-built projection immediately; reprojecting
                    // with newer scenery inputs continues in the background.
                    eprintln!("scenery projection is stale; installing and reprojecting");
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
                if (first || self.scene.entity_loader.is_none())
                    && let Err(e) = self.scene.start_entity_loader(
                        scene.asset.clone(),
                        self.scene.generation,
                        self.proxy.clone(),
                    )
                {
                    eprintln!("entity asset worker: {e}");
                }
                if (first || self.scene.animator.is_none())
                    && let Err(e) = self.scene.start_animator(
                        scene.asset.clone(),
                        self.scene.generation,
                        self.proxy.clone(),
                    )
                {
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
                if stale {
                    let asset = self
                        .scene
                        .loaded
                        .as_ref()
                        .map(|loaded| loaded.asset.clone());
                    if let (Some(placement), Some(asset)) = (self.state.initial_placement(), asset)
                    {
                        let inputs = self.scene.scenery_inputs(self.state.scenery());
                        let generation = self.scene.generation;
                        if let Err(e) = self.scene.reproject(
                            asset,
                            placement,
                            inputs,
                            generation,
                            self.proxy.clone(),
                        ) {
                            eprintln!("scenery projection: {e}");
                        }
                    }
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
            .with_inner_size(LogicalSize::new(765. + f64::from(self.shell.width()), 503.))
            .with_min_inner_size(LogicalSize::new(765. + f64::from(self.shell.width()), 503.));
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
                if matches!(event, network::NetworkEvent::Ready) {
                    let events = self
                        .connection
                        .as_ref()
                        .map(network::ConnectionHandle::drain_events)
                        .unwrap_or_default();
                    for event in events {
                        self.enqueue_network_event(event);
                    }
                } else {
                    self.enqueue_network_event(event);
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
        self.input_activity.note(&event);
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
        if self.route_shell(&window, &event) {
            // Input updates the retained DOM and queues actions. Painting and publication are
            // batched at presentation, rather than once for every high-frequency pointer event.
            window.request_redraw();
            return;
        }
        match event {
            WindowEvent::CloseRequested => {
                self.disconnect();
                event_loop.exit();
            }
            WindowEvent::Resized(size) => {
                self.shell.on_window_resized(&window);
                self.synchronize_window();
                if let Some(renderer) = &mut self.renderer
                    && let Err(error) = renderer.resize(SurfaceSize::new(size.width, size.height))
                {
                    eprintln!("window resize: {error}");
                }
                window.request_redraw();
            }
            WindowEvent::ScaleFactorChanged { .. } => {
                self.shell.on_window_resized(&window);
                self.synchronize_window();
                let size = window.inner_size();
                if let Some(renderer) = &mut self.renderer
                    && let Err(error) = renderer.resize(SurfaceSize::new(size.width, size.height))
                {
                    eprintln!("window resize: {error}");
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
                    let over_world_map = self.frame.quads.iter().any(|q| {
                        q.image == rustclient_game_ui::UiImageId::WorldMap
                            && x >= q.rect.x
                            && x <= q.rect.x + q.rect.width
                            && y >= q.rect.y
                            && y <= q.rect.y + q.rect.height
                    });
                    if over_world_map {
                        if let Some(scripts) = &mut self.scripts {
                            let factor = if amount > 0 { 0.85 } else { 1.15 };
                            let new_zoom = (scripts.world_map.zoom * factor).clamp(25.0, 200.0);
                            scripts.world_map.zoom = new_zoom;
                            scripts.world_map.target_zoom = new_zoom;
                            window.request_redraw();
                        }
                    } else {
                        match self.widget_scroll(x, y, amount) {
                            Ok(true) => {
                                window.request_redraw();
                            }
                            Err(e) => eprintln!("widget scroll: {e}"),
                            Ok(false) => {}
                        }
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
                self.scene.input.pointer = if self.stretch_layout(&window).is_some() {
                    // Camera drags are measured in canvas pixels, not window pixels.
                    let (x, y) = self.ui_pointer(&window, position.x, position.y);
                    scene::canvas_pointer(x, y)
                } else {
                    scene::pointer(position.x, position.y, window.scale_factor())
                };
                self.cursor = Some((position.x, position.y));
                let (x, y) = self.ui_pointer(&window, position.x, position.y);
                if let Some(([sx, sy], start_center)) = self.world_map_drag
                    && let Some(scripts) = &mut self.scripts
                {
                    let pixels_per_tile = (scripts.world_map.zoom.max(10.0) / 100.0) * 4.0;
                    let dx = x - sx;
                    let dy = y - sy;
                    #[expect(clippy::cast_possible_truncation)]
                    {
                        scripts.world_map.center_tile[0] =
                            start_center[0] - (dx / pixels_per_tile).round() as i32;
                        scripts.world_map.center_tile[1] =
                            start_center[1] + (dy / pixels_per_tile).round() as i32;
                    }
                }
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
                self.roof_hover = None;
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
                self.world_map_drag = None;
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
                self.window_focused = false;
                self.scene.input = rustclient_renderer::CameraInput::default();
                self.widget_pointer = widget_pointer::WidgetPointer::default();
                self.modifiers = ModifiersState::default();
                if let Some(scripts) = &mut self.scripts {
                    scripts.modifiers = [false; 3];
                }
            }
            WindowEvent::Focused(true) => {
                self.window_focused = true;
                window.request_redraw();
            }
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
        if self.connection.is_some() || self.state.authenticated_player().is_some() {
            if self.state.authenticated_player().is_some()
                && let Some(window) = self.window.clone()
            {
                let hover_start = Instant::now();
                self.update_hover(&window);
                self.performance
                    .stage(performance::Stage::Hover, hover_start);
                if self.click_cross.is_some() {
                    window.request_redraw();
                }
            }
            let now = Instant::now();
            if now > self.next_step {
                self.performance
                    .cycle_debt(now.duration_since(self.next_step));
            }
            let due = match self
                .cycle_clock
                .due(rustclient_platform::MonotonicTime::from_duration(
                    now.duration_since(self.cycle_origin),
                )) {
                Ok(due) => due,
                Err(error) => {
                    self.apply_network_event(network::NetworkEvent::Failed(
                        rustclient_game_state::LoginFailure::Protocol,
                        error.to_string(),
                    ));
                    0
                }
            };
            let mut advanced = false;
            for _ in 0..due {
                if let Some((category, diagnostic)) = self.pending_network_failure.take() {
                    self.apply_network_event(network::NetworkEvent::Failed(category, diagnostic));
                    break;
                }
                if let Some(authentication) = self.pending_authentication.take() {
                    self.apply_network_event(network::NetworkEvent::Operation(
                        0,
                        0,
                        authentication,
                    ));
                }
                self.ingress.begin_cycle();
                while let Some(events) = self.ingress.next_batch() {
                    for event in events {
                        self.apply_network_event(event);
                    }
                }
                if self.state.authenticated_player().is_none() {
                    if let (Some(assets), Some(flames)) = (&self.assets, &mut self.flames) {
                        let random = &mut self.random;
                        flames.step(&assets.runes, || random.next_sample().get());
                        self.cycle = self.cycle.wrapping_add(1);
                        if let Some(window) = &self.window {
                            window.request_redraw();
                        }
                    }
                    continue;
                }
                self.synchronize_window();
                self.plugins.start(&self.state);
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

                self.session_counters.advance();
                if let Some(connection) = &self.connection {
                    if self.session_counters.keepalive_due() && !self.keepalive_pending {
                        match connection.send(network::Outbound {
                            world_action: None,
                            map_epoch: self.map_epoch,
                            interface_epoch: None,
                            intent: rustclient_protocol::ClientIntent::KeepAlive,
                        }) {
                            Ok(()) => self.keepalive_pending = true,
                            Err(error) => eprintln!("keepalive queue: {error}"),
                        }
                    }
                    if let Err(error) =
                        connection.flush(|outbound| self.validate_outbound(outbound))
                    {
                        eprintln!("outbound flush: {error}");
                    }
                }
                self.plugins.advance(&self.state, 1);
                self.update_plugins();
            }
            self.next_step = self.cycle_origin + self.cycle_clock.deadline().as_duration();
            if advanced {
                self.sync_roof_areas();
                self.sync_interpolation();
                self.sync_camera_tuning();
                self.sync_sprite_overrides();
                self.sync_hidden_actors();
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
                        if self.scene.diagnostics
                            || !report.ends_with("undrawable npc model ids []")
                        {
                            eprintln!("entities: {report}");
                        }
                        self.entity_report.1 = report;
                    }
                }
                let entity_start = Instant::now();
                self.scene.entity_geometry.clear();
                if let Err(e) = build_entities_geometry(
                    &loaded.asset,
                    &self.scene.entity_catalog,
                    self.state.entities(),
                    &self.scene.motion,
                    self.state.scenery().variables(),
                    rustclient_scene::entity_mesh::EntityPresentation {
                        interpolation: &self.scene.interpolation,
                        hidden: &self.scene.hidden_models,
                    },
                    &mut self.scene.entity_geometry,
                ) {
                    eprintln!("entity geometry: {e}");
                    self.scene.entity_geometry.clear();
                }
                self.performance
                    .stage(performance::Stage::EntityBuild, entity_start);
                let upload_start = Instant::now();
                if let Some(renderer) = &mut self.renderer
                    && let Err(e) = renderer.set_world_entities(
                        loaded.asset.descriptor().id(),
                        &self.scene.entity_geometry,
                    )
                {
                    eprintln!("entity geometry upload: {e}");
                }
                self.performance
                    .stage(performance::Stage::EntityUpload, upload_start);
            }
            if advanced && let Some(window) = &self.window {
                self.hover_dirty = true;
                window.request_redraw();
            }
            self.performance.tick(tick_start);
            self.schedule_presentation(event_loop);
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
            self.schedule_presentation(event_loop);
        } else {
            event_loop.set_control_flow(ControlFlow::Wait);
        }
    }
}
impl NativeApp {
    fn presentation_limit(&self) -> Option<u32> {
        frame_limit::combined_limit(
            self.renderer
                .as_ref()
                .map_or(50, |renderer| renderer.effective_gpu().frame_cap),
            self.plugins.output().frame_limit,
        )
    }

    /// Presentation deadlines are independent of the fixed semantic cycle. No catch-up frames.
    fn schedule_presentation(&self, event_loop: &ActiveEventLoop) {
        let Some(window) = &self.window else {
            return;
        };
        if self.occluded || self.renderer.is_none() {
            event_loop.set_control_flow(ControlFlow::WaitUntil(self.next_step));
            return;
        }
        let now = Instant::now();
        match self.frame_limit.deadline(self.presentation_limit()) {
            Some(deadline) if deadline > now => {
                event_loop.set_control_flow(ControlFlow::WaitUntil(self.next_step.min(deadline)));
            }
            Some(_) | None => {
                window.request_redraw();
                // Uncapped/FIFO rendering is paced by presentation; capped frames establish their
                // next deadline in redraw. Semantic catch-up remains bounded by about_to_wait.
                event_loop.set_control_flow(ControlFlow::Poll);
            }
        }
    }

    fn ui_pointer(&self, window: &Window, x: f64, y: f64) -> (f32, f32) {
        let size = self.shell.game_size(window);
        TitleViewport {
            canvas_width: if self.state.authenticated_player().is_some() {
                self.state.interfaces().window().canvas.width
            } else {
                765
            },
            physical_width: size.width,
            physical_height: size.height,
            scale: window.scale_factor(),
            stretch: self
                .stretch_layout(window)
                .map(|layout| layout.canvas(window.scale_factor())),
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
    let mut app = NativeApp::new(&options, proxy.clone())?;
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
                    let mut scripts =
                        rustclient_application::scripts::ClientScripts::new(cache, options.profile);
                    if let Err(error) = scripts.initialize_world_map() {
                        eprintln!("world map startup: {error}");
                    }
                    scripts
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
