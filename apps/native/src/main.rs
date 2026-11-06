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
mod scene_publication;
mod scene_resources;
mod scene_workers;
mod shell;
mod shell_input;
mod widget_pointer;
mod widgets;
mod world_overlays;
mod worldmap;

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
    hash::{DefaultHasher, Hash, Hasher},
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
    MapComposed(
        rustclient_platform::JobIdentity,
        Box<Result<worldmap::MapPixels, String>>,
    ),
    WorldMapAssets(
        rustclient_platform::JobIdentity,
        Box<Result<worldmap::MapBatch, String>>,
    ),
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
    DynamicFault(u64, u64, rustclient_model::ClientCycle),
    EntityFault(u64, u64, String),
    EntityAssets(
        u64,
        u64,
        Box<rustclient_cache::osrs240::entity_assets::EntityAssets>,
    ),
    DynamicReady(
        u64,
        u64,
        Box<rustclient_scene::SceneryInputs>,
        Box<Result<scene_workers::DynamicProjection, String>>,
    ),
    LocationResources(
        u64,
        u64,
        Box<Result<std::sync::Arc<rustclient_scene::SceneAsset>, String>>,
    ),
    SceneLoaded(u64, Box<Result<scene::PreparedScene, String>>),
}

/// Per-session client state that must not outlive a login. Everything here is cleared by
/// [`SessionScratch::reset`] when the session ends, so a field added later cannot be forgotten.
#[derive(Default)]
struct SessionScratch {
    /// Script error texts already reported, so each is logged once per session.
    reported_script_errors: std::collections::BTreeSet<String>,
    /// Whether the last plugin update asked for mouse-wheel zoom on the minimap, so the client
    /// changes the script-owned flag only when the request changes.
    plugin_wheel_zoom: bool,
    /// Whether the client last hid the minimap and orbs for a plugin, so lifting the request
    /// shows them again.
    minimap_hidden: bool,
    /// Widget assets the session's interfaces have asked for.
    widget_requested: rustclient_application::scripts::WidgetAssetRequest,
}
impl SessionScratch {
    fn reset(&mut self) {
        *self = Self::default();
    }
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
    /// The plugin entries of the world menu that was last opened, by `WorldChoice::Plugin` slot.
    plugin_menu: Vec<rustclient_plugin_api::MenuEntry>,
    /// Plugin records of the stock entries of the last built world menu, for `MenuOptionClicked`.
    menu_records: Vec<(interaction::WorldChoice, rustclient_plugin_api::MenuEntry)>,
    /// Plugin records of the rows of the last widget menu stage, for `MenuOptionClicked`.
    widget_menu_records: Vec<(
        rustclient_model::WidgetAction,
        rustclient_plugin_api::MenuEntry,
    )>,
    click_cross: Option<(Instant, interaction::CrossKind, f32, f32)>,
    hover: Option<interaction::HoverText>,
    /// Scene-local tile under the pointer, tracked while a plugin asks for hovered-roof removal.
    roof_hover: Option<[i32; 2]>,
    hover_dirty: bool,
    /// Set once the game interface has been drawn this session; cleared on disconnect.
    entered_world: bool,
    widget_menu: rustclient_game_ui::ContextMenu<rustclient_model::WidgetAction>,
    scratch: SessionScratch,
    entity_report: (Instant, String),
    /// Hidden or fully covered windows are not drawn: the OS blocks `get_current_texture` there.
    occluded: bool,
    window_focused: bool,
    input_activity: input_activity::InputActivity,
    frame_limit: frame_limit::FrameLimit,
    /// The plugin sprite replacements currently standing in the widget sprites.
    applied_sprite_overrides: Vec<rustclient_plugin_api::SpriteOverride>,
    scripts: Option<rustclient_application::scripts::ClientScripts<DirectoryBlobs>>,
    widget_job_scope: rustclient_platform::JobCancellation,
    widget_assets: rustclient_game_ui::widget_visuals::WidgetVisualAssets,
    widget_asset_key: Option<(u64, u64)>,
    widget_loading: bool,
    reported_window: Option<rustclient_model::WindowMetrics>,
    widget_tree: rustclient_game_ui::widgets::WidgetTree,
    widget_tree_key: Option<widgets::WidgetLayoutKey>,
    widget_paint: Option<rustclient_application::scripts::WidgetPaintDependencies>,
    widget_pointer: widget_pointer::WidgetPointer,
    widget_cache_dir: std::path::PathBuf,
    widget_cache_profile: rustclient_cache::CacheProfile,
    minimap: Option<std::sync::Arc<MinimapImage>>,
    /// Plane the cached minimap image was built for; the client rebuilds it when the player changes plane.
    minimap_plane: Option<rustclient_model::Plane>,
    minimap_pixels: Vec<u8>,
    compass_pixels: Vec<u8>,
    /// Hash of every input the composed minimap/compass pixels depend on, and the revision handed
    /// to the renderer; a frame with an unchanged key neither recomposes nor re-uploads.
    minimap_key: u64,
    minimap_revision: u64,
    minimap_generation: u64,
    compass_key: u64,
    compass_revision: u64,
    map_view: worldmap::MapView,
    map_composer: worldmap::MapComposer,
    world_map_revision: u64,
    world_map_pixels: Vec<u8>,
    world_map_size: [u32; 2],
    world_map_overview_pixels: Vec<u8>,
    world_map_overview_size: [u32; 2],
    world_map_input_bounds: Option<rustclient_game_ui::Rect>,
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
    /// Machine-local preferences: where the window was left.
    client_preferences: rustclient_application::client_preferences::ClientPreferences,
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
        if let Some(scripts) = &mut self.scripts {
            self.plugins
                .scripts_finished(scripts.take_completed_scripts());
        }
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
        // Stock `genCert` gives a noted item its unnoted name and price.
        let items = |id| {
            let def = catalog.item(id)?;
            let unnoted = def
                .note_template
                .and(def.note)
                .and_then(|note| catalog.item(note));
            Some(rustclient_plugin_bridge::ItemFacts {
                name: catalog.item_name(id),
                cost: unnoted.map_or(def.cost, |unnoted| unnoted.cost),
                stackable: def.stackable || def.note_template.is_some(),
                noted: def.note_template.is_some(),
            })
        };
        let inputs = plugins::Inputs {
            frame: rustclient_plugin_bridge::Frame {
                session: self.connection_generation.checked_add(1).unwrap_or(0),
                fps: self.performance.fps(),
                focused: self.window_focused,
                loading: self.scene.loading,
                camera: camera.unwrap_or_default(),
                input_idle: Some(self.input_activity.idle(Instant::now())),
                hovered_tile: self.hovered_world_tile(),
                shift_held: self.modifiers.shift_key(),
                wall_time_ms: std::time::SystemTime::now()
                    .duration_since(std::time::UNIX_EPOCH)
                    .map_or(0, |elapsed| {
                        u64::try_from(elapsed.as_millis()).unwrap_or(u64::MAX)
                    }),
            },
            sources: rustclient_plugin_bridge::Sources {
                npcs: Some(&resolve),
                varbits: asset.map(|asset| &asset.sources().varbits),
                items: Some(&items),
                // NPC names and ground item facts change only when definitions arrive or the
                // scene (and with it the asset NPCs resolve against) is replaced.
                revision: catalog.generation() ^ self.map_epoch.rotate_left(32),
                interfaces: self
                    .scripts
                    .as_ref()
                    .map(rustclient_application::scripts::ClientScripts::interfaces),
            },
        };
        self.plugins
            .update(&self.state, &inputs, &self.shell.take_panel_actions());
        self.shell.set_plugin_panels(&self.plugins.output().panels);
        self.sync_minimap();
        self.shell.set_plugin_statuses(self.plugins.statuses());
        // Sprites a plugin draws are decoded by the same worker as widget sprites.
        if self.widget_sprites_missing() {
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
        self.widget_paint = None;
        if let (Some(renderer), Some(title)) = (&mut self.renderer, &self.assets)
            && let Err(error) = renderer.set_widget_images(title, &self.widget_assets.images)
        {
            eprintln!("sprite override GPU upload: {error}");
        }
        self.minimap_mask = None;
        self.compass_mask = None;
        self.minimap_key = 0;
        self.compass_key = 0;
    }
    /// Follow the minimap preferences plugins ask for: wheel zoom, the saved zoom level and
    /// hiding. No request means the unmodified client.
    #[expect(
        clippy::cast_possible_truncation,
        reason = "the host clamps the requested zoom to 2..=8 pixels per tile"
    )]
    fn sync_minimap(&mut self) {
        use rustclient_model::MinimapUpdate;
        let wheel_zoom = self.plugins.output().minimap.wheel_zoom;
        if wheel_zoom != self.scratch.plugin_wheel_zoom {
            self.scratch.plugin_wheel_zoom = wheel_zoom;
            self.update_minimap(MinimapUpdate::Zoomable(wheel_zoom));
        }
        if let Some(zoom) = self.plugins.take_minimap_zoom() {
            self.update_minimap(MinimapUpdate::ZoomQuarters((zoom * 4.).round() as i32));
        }
        self.sync_minimap_visibility();
    }
    /// The plugin's `hideMinimap` hides the minimap and its orbs in the resizable top-levels
    /// (161 and 164) with `Widget.setHidden`, and again after every redraw script; here the
    /// request is re-asserted each cycle, and lifting it shows both again.
    fn sync_minimap_visibility(&mut self) {
        use rustclient_model::{InterfaceUpdate, WidgetAddress, WidgetId, WidgetProperty};
        const MINIMAP: u32 = 22;
        const ORBS: u32 = 33;
        let hide = self.plugins.output().minimap.hidden;
        if !hide && !self.scratch.minimap_hidden {
            return;
        }
        let Some(root) = self
            .state
            .interfaces()
            .root()
            .filter(|root| matches!(root.get(), 161 | 164))
        else {
            return;
        };
        for component in [MINIMAP, ORBS] {
            let widget = WidgetId::new(root, component);
            let hidden = self
                .state
                .interfaces()
                .runtime()
                .get(&WidgetAddress::component(widget))
                .and_then(|runtime| runtime.hidden);
            if hidden != Some(hide) {
                self.apply(Some(SemanticOperation::Interface(
                    InterfaceUpdate::SetProperty {
                        widget,
                        property: WidgetProperty::Hidden(hide),
                    },
                )));
            }
        }
        self.scratch.minimap_hidden = hide;
    }
    fn update_minimap(&mut self, update: rustclient_model::MinimapUpdate) {
        self.apply(Some(SemanticOperation::Interface(
            rustclient_model::InterfaceUpdate::Minimap(update),
        )));
    }
    /// One wheel notch over the minimap changes its zoom by a quarter pixel per tile (the 241
    /// client's step); `amount` is positive when the wheel turns toward the user.
    fn zoom_minimap_with_wheel(&mut self, amount: i32) -> bool {
        let settings = self.state.interfaces().minimap();
        if !settings.zoomable || amount == 0 {
            return false;
        }
        self.update_minimap(rustclient_model::MinimapUpdate::ZoomQuarters(
            settings.zoom_quarters - amount.signum(),
        ));
        true
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
                CameraCommand::YawTarget(angle) => {
                    tuning.yaw_target = u16::try_from(angle.rem_euclid(2048)).ok();
                }
                CameraCommand::PitchTarget(angle) => tuning.pitch_target = Some(angle),
                CameraCommand::ZoomTarget(zoom) => tuning.zoom_target = Some(zoom),
                CameraCommand::ZoomLimits { min, max } => tuning.zoom_limits = Some((min, max)),
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
    }
    /// Start the load for the committed placement once the packets of this cycle batch are
    /// applied, so the scenery snapshot already holds the batch's location changes.
    fn start_pending_scene(&mut self) {
        if let Some(placement) = self.state.initial_placement()
            && let Some(window) = &self.window
            && !self.scene.attempted
            && (self.scene.loaded.is_none() || self.scene.replacing.is_some())
            && !self.scene.loading
        {
            window.set_title("RustClient - Loading spawn scene");
            if let Err(error) = self.scene.start(
                placement,
                self.scene.scenery_inputs(self.state.scenery()),
                self.scene.generation,
                self.proxy.clone(),
            ) {
                eprintln!("{error}");
            }
        }
    }
    /// Publish rendering work after committed client-cycle work, never from an intermediate
    /// packet in a group. Semantic operations and script listeners still run in wire order.
    fn schedule_scene_projection(&mut self) {
        self.performance.scene_generation(self.scene.generation);
        let generation = self.state.scenery().generation();
        if self.scene.locations.observed != Some(generation) {
            self.performance.location_event("post_cycle_observation",generation,self.state.scenery().cycle().get(),0,||serde_json::json!({"scope":"post-cycle observation; packet arrival and physical visibility are separate"}));
            self.scene.locations.observed = Some(generation);
        }
        let changes = self.state.projection_changes(self.scene.locations.cursor);
        if changes.missed() && changes.missed_domains().scenery_changed() {
            self.scene.locations.invalidate = true;
        }
        self.scene.locations.cursor = changes.cursor();
        self.start_pending_scene();
        if self.scene.loading || self.scene.replacing.is_some() {
            return;
        }
        let Some(loaded) = &mut self.scene.loaded else {
            return;
        };
        let scenery = self.state.scenery();
        let rebuild = self.scene.awaiting_world.is_some();
        if !rebuild
            && loaded.projection_generation == scenery.generation()
            && loaded.projection_inputs.roof_areas == self.scene.roof_areas
        {
            return;
        }
        // A door or location change the baked scene can express as a patch is published by the
        // animation worker; only a change it cannot express rebuilds the static scene. A renderer
        // created after the triangles were released has nothing to patch.
        if !rebuild
            && static_scene_shows(
                loaded,
                scenery,
                &self.scene.roof_areas,
                self.scene.animator.is_some(),
            )
        {
            loaded.projection_generation = scenery.generation();
            return;
        }
        if !rebuild
            && self.scene.animator.is_some()
            && loaded.projection_inputs.roof_areas == self.scene.roof_areas
        {
            let asset = loaded.asset.clone();
            if let Err(error) = self.scene.request_location_resources(
                asset,
                self.scene.scenery_inputs(scenery),
                self.proxy.clone(),
            ) {
                eprintln!("{error}");
            }
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
            eprintln!("scenery projection: {error}");
        }
    }
    #[expect(
        clippy::too_many_lines,
        reason = "composition root explicitly initializes each owned subsystem"
    )]
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
        let client_preferences = storage
            .as_ref()
            .and_then(|store| {
                rustclient_application::client_preferences::ClientPreferences::load(store)
                    .map_err(|error| eprintln!("using default client preferences: {error}"))
                    .ok()
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
            plugin_menu: Vec::new(),
            menu_records: Vec::new(),
            widget_menu_records: Vec::new(),
            click_cross: None,
            hover: None,
            roof_hover: None,
            hover_dirty: false,
            entered_world: false,
            widget_menu: rustclient_game_ui::ContextMenu::default(),
            scratch: SessionScratch::default(),
            entity_report: (Instant::now(), String::new()),
            occluded: false,
            window_focused: true,
            input_activity: input_activity::InputActivity::new(),
            frame_limit: frame_limit::FrameLimit::default(),
            applied_sprite_overrides: Vec::new(),
            scripts: None,
            widget_job_scope: rustclient_platform::JobCancellation::default(),
            widget_assets: rustclient_game_ui::widget_visuals::WidgetVisualAssets::default(),
            widget_asset_key: None,
            widget_loading: false,
            reported_window: None,
            widget_tree: rustclient_game_ui::widgets::WidgetTree::default(),
            widget_tree_key: None,
            widget_paint: None,
            widget_pointer: widget_pointer::WidgetPointer::default(),
            widget_cache_dir: options.cache_dir.clone(),
            widget_cache_profile: options.profile,
            minimap: None,
            minimap_plane: None,
            minimap_pixels: Vec::new(),
            compass_pixels: Vec::new(),
            minimap_key: 0,
            minimap_revision: 0,
            minimap_generation: 0,
            compass_key: 0,
            compass_revision: 0,
            map_view: worldmap::MapView::default(),
            map_composer: worldmap::MapComposer::default(),
            world_map_revision: 0,
            world_map_pixels: Vec::new(),
            world_map_size: [0, 0],
            world_map_overview_pixels: Vec::new(),
            world_map_overview_size: [0, 0],
            world_map_input_bounds: None,
            minimap_mask: None,
            compass_mask: None,
            flames: None,
            random: SplitMix64Random::new(RandomSeed::new(240)),
            frame: UiFrame::default(),
            plugins,
            storage,
            saved_preferences: preferences,
            client_preferences,
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
        match pollster::block_on(SurfaceRenderer::new_with_instrumentation(
            window.clone(),
            SurfaceSize::new(size.width, size.height),
            self.performance.instrumentation(),
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
                // Uploaded triangles are released after the upload (the picking index keeps its
                // own copy), so a renderer created later has to wait for a fresh projection.
                let released = self
                    .scene
                    .loaded
                    .as_ref()
                    .is_some_and(|scene| scene.mesh.geometry.is_released());
                self.scene.awaiting_world = released.then(Instant::now);
                if !released && let Some(scene) = &mut self.scene.loaded {
                    if let Err(error) = renderer.set_world(&scene.mesh) {
                        self.fail(event_loop, &error.to_string());
                        return;
                    }
                    scene.mesh.geometry.release_triangles();
                }
                if !released
                    && let Some(scene) = &self.scene.loaded
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
                self.performance.renderer_created(&window, &renderer);
                self.renderer = Some(renderer);
                // A renderer created after the triangles were released waits for the projection
                // this starts (and the per-cycle scheduler retries).
                self.schedule_scene_projection();
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
                        scripts.interfaces(),
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
                        if self.scratch.reported_script_errors.len() < 256
                            && self.scratch.reported_script_errors.insert(error.clone())
                        {
                            eprintln!("client script: {error}");
                        }
                    }
                }
                Err(error) => eprintln!("client script: {error}"),
            }
        }
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
                // SPEC-0021 lifetime: a scene change or the loss of the interface root ends any menu.
                if previous_scene != self.state.loaded_scene()
                    || self.state.interfaces().root().is_none()
                {
                    self.close_menus();
                }
                if self.state.loaded_scene().is_none() && (rebuild || previous_scene.is_some()) {
                    self.context_menu.close();
                    if self.state.displayed_scene().is_some() && self.scene.loaded.is_some() {
                        // SPEC-0023: the retired scene stays drawn until the replacement installs.
                        self.scene.retire_map();
                    } else {
                        self.scene.invalidate_map();
                        self.minimap = None;
                    }
                } else if rebuild && self.state.loaded_scene().is_some() {
                    self.acknowledge_map();
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
        self.widget_tree_key = None;
        self.widget_paint = None;
        self.world_map_input_bounds = None;
        self.widget_asset_key = None;
        self.widget_loading = false;
        self.scratch.reset();
        self.minimap_key = 0;
        self.map_view = worldmap::MapView::default();
        self.clear_map_projection();
        self.widget_job_scope.cancel();
        self.widget_job_scope = rustclient_platform::JobCancellation::default();
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
            let commit_start = Instant::now();
            let result = if let Some(scripts) = &mut self.scripts {
                let authenticated =
                    matches!(operation, SemanticOperation::AuthenticateSession { .. });
                scripts
                    .commit(&mut self.state, operation)
                    .and_then(|mut errors| {
                        if authenticated {
                            errors.extend(scripts.initialize_session(&mut self.state)?);
                        }
                        Ok(errors)
                    })
            } else {
                self.state
                    .apply_operation(operation)
                    .map(|_| Vec::new())
                    .map_err(|e| e.to_string())
            };
            self.performance
                .stage(performance::Stage::CommitAndScripts, commit_start);
            match result {
                Ok(errors) => {
                    for error in errors {
                        if self.scratch.reported_script_errors.len() < 256
                            && self.scratch.reported_script_errors.insert(error.clone())
                        {
                            eprintln!("client script: {error}");
                        }
                    }
                }
                Err(error) => eprintln!("semantic input rejected: {error}"),
            }
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
        // Stock draws the red item dot (mapdots frame 0) at the centre of every tile on the
        // local player's plane with a pile, before NPC and player dots.
        if let Some(plane) = entities.local_player().map(|player| player.tile.plane) {
            for (tile, _) in self.state.ground_items().piles() {
                if tile.plane == plane {
                    dots.push(MinimapDot {
                        tile: (
                            f64::from(tile.x - origin.x) + 0.5,
                            f64::from(tile.y - origin.y) + 0.5,
                        ),
                        frame: 0,
                    });
                }
            }
        }
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
        self.performance.scene_generation(self.scene.generation);
        if self.performance.run_complete() {
            return;
        }
        let Some(window) = self.window.clone() else {
            return;
        };
        if self.occluded {
            return;
        }
        let mut frame_visibility: Option<rustclient_scene::SceneVisibility> = None;
        // GPU pacing and an optional FPS plugin share the existing presentation clock.
        let policy = self.presentation_limit();
        let deadline = self.frame_limit.deadline(policy);
        if !self.frame_limit.allow(policy, Instant::now()) {
            return;
        }
        self.performance.presentation_deadline(deadline);
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
        if self.assets.is_some()
            && self.flames.is_some()
            && self.state.authenticated_player().is_some()
            && self.state.interfaces().root().is_some()
            && !self.scene.diagnostics
        {
            self.project_game_widgets();
            self.update_map_projection();
        } else {
            self.clear_map_projection();
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
                    self.entered_world = true;
                }
                let visibility = self.scene_visibility(&window);
                // The renderer below draws with the same predicate; compute it once per frame.
                frame_visibility = Some(visibility.clone());
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
                world_overlays::project(
                    &self.scene,
                    assets,
                    &self.plugins.output().overlay,
                    &mut self.frame,
                    &visibility,
                );
                self.plugins
                    .overlay(&self.state, assets, &self.widget_assets, &mut self.frame);
                if scene::loading_box_due(
                    self.state.displayed_scene().is_some(),
                    self.scene.replacing.map(|since| since.elapsed()),
                ) {
                    rustclient_game_ui::loading_box(
                        &mut self.frame,
                        assets,
                        "Loading - please wait.",
                    );
                }
                if let (Some(loaded), Some(player), Some(_)) = (
                    &self.scene.loaded,
                    self.state.entities().local_player(),
                    &self.minimap,
                ) && self.minimap_plane != Some(player.tile.plane)
                {
                    self.minimap = Some(std::sync::Arc::new(build_scene_minimap(
                        &loaded.asset,
                        player.tile.plane,
                    )));
                    self.minimap_plane = Some(player.tile.plane);
                    self.minimap_generation += 1;
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
                        let destination =
                            self.state.destination().filter(|value| value.x != origin.x);
                        let local_visible =
                            self.state.entities().local_player().is_some_and(|player| {
                                player
                                    .appearance
                                    .as_ref()
                                    .is_none_or(|appearance| !appearance.hidden)
                            });
                        let mut hasher = DefaultHasher::new();
                        self.minimap_generation.hash(&mut hasher);
                        self.frame
                            .minimap_sprite
                            .map(SpriteId::get)
                            .hash(&mut hasher);
                        mask.size.hash(&mut hasher);
                        camera_focus_tile.0.to_bits().hash(&mut hasher);
                        camera_focus_tile.1.to_bits().hash(&mut hasher);
                        angle.hash(&mut hasher);
                        let minimap_settings = self.state.interfaces().minimap();
                        (
                            minimap_settings.zoom_quarters,
                            minimap_settings.icon_zoom_limit,
                        )
                            .hash(&mut hasher);
                        self.plugins.output().minimap.dot_colors.hash(&mut hasher);
                        (origin.x, origin.y).hash(&mut hasher);
                        self.scene.loaded.is_some().hash(&mut hasher);
                        destination.map(|tile| (tile.x, tile.y)).hash(&mut hasher);
                        self.widget_assets
                            .destination_flag
                            .is_some()
                            .hash(&mut hasher);
                        local_visible.hash(&mut hasher);
                        for dot in &dots {
                            (dot.tile.0.to_bits(), dot.tile.1.to_bits(), dot.frame)
                                .hash(&mut hasher);
                        }
                        let plugin_dots: Vec<_> = self
                            .plugins
                            .output()
                            .overlay
                            .iter()
                            .filter_map(|item| match item.command {
                                rustclient_plugin_api::OverlayCommand::MinimapDot {
                                    tile,
                                    argb,
                                } if i32::from(tile.plane)
                                    == self
                                        .state
                                        .entities()
                                        .local_player()
                                        .map_or(-1, |p| i32::from(p.tile.plane.get())) =>
                                {
                                    Some(((tile.x, tile.y), argb))
                                }
                                _ => None,
                            })
                            .collect();
                        plugin_dots.hash(&mut hasher);
                        let key = hasher.finish();
                        if key != self.minimap_key || self.minimap_pixels.is_empty() {
                            self.minimap_key = key;
                            self.minimap_revision += 1;
                            self.minimap_pixels = compose_minimap(
                                &mask.pixels,
                                mask.size[0] as usize,
                                mask.size[1] as usize,
                                minimap,
                                camera_focus_tile,
                                angle,
                                minimap_settings.zoom_quarters,
                            );
                            if let Some(loaded) = &self.scene.loaded {
                                rustclient_scene::minimap::draw_icons(
                                    &mut self.minimap_pixels,
                                    &mask.pixels,
                                    [mask.size[0] as usize, mask.size[1] as usize],
                                    &loaded.asset.sources().map_icons,
                                    &minimap.icons,
                                    rustclient_scene::minimap::MinimapView {
                                        camera: camera_focus_tile,
                                        angle,
                                        zoom_quarters: minimap_settings.zoom_quarters,
                                    },
                                    minimap_settings.icon_zoom_limit,
                                );
                                let relative = (
                                    camera_focus_tile.0 - f64::from(origin.x),
                                    camera_focus_tile.1 - f64::from(origin.y),
                                );
                                rustclient_scene::minimap::draw_dots(
                                    &mut self.minimap_pixels,
                                    &mask.pixels,
                                    [mask.size[0] as usize, mask.size[1] as usize],
                                    &rustclient_scene::minimap::recolored_dots(
                                        &loaded.asset.sources().map_dots,
                                        &self.plugins.output().minimap.dot_colors,
                                    ),
                                    &dots,
                                    rustclient_scene::minimap::MinimapView {
                                        camera: relative,
                                        angle,
                                        zoom_quarters: minimap_settings.zoom_quarters,
                                    },
                                );
                                // Plugin dots (`OverlayCommand::MinimapDot`) above the cache's.
                                for ((x, y), argb) in &plugin_dots {
                                    if let Some(sprite) =
                                        rustclient_scene::minimap::plugin_dot(*argb)
                                    {
                                        rustclient_scene::minimap::draw_marker(
                                            &mut self.minimap_pixels,
                                            &mask.pixels,
                                            [mask.size[0] as usize, mask.size[1] as usize],
                                            &sprite,
                                            (
                                                f64::from(x - origin.x) + 0.5,
                                                f64::from(y - origin.y) + 0.5,
                                            ),
                                            rustclient_scene::minimap::MinimapView {
                                                camera: relative,
                                                angle,
                                                zoom_quarters: minimap_settings.zoom_quarters,
                                            },
                                        );
                                    }
                                }
                            }
                            if let (Some(destination), Some(sprite)) =
                                (destination, self.widget_assets.destination_flag.as_ref())
                            {
                                rustclient_scene::minimap::draw_marker(
                                    &mut self.minimap_pixels,
                                    &mask.pixels,
                                    [mask.size[0] as usize, mask.size[1] as usize],
                                    sprite,
                                    (
                                        f64::from(destination.x) + 0.5,
                                        f64::from(destination.y) + 0.5,
                                    ),
                                    rustclient_scene::minimap::MinimapView {
                                        camera: camera_focus_tile,
                                        angle,
                                        zoom_quarters: minimap_settings.zoom_quarters,
                                    },
                                );
                            }
                            if local_visible {
                                rustclient_scene::minimap::draw_local_player(
                                    &mut self.minimap_pixels,
                                    &mask.pixels,
                                    [mask.size[0] as usize, mask.size[1] as usize],
                                );
                            }
                        }
                    }
                    if let Some(mask) = cached_mask(
                        &mut self.compass_mask,
                        &self.widget_assets,
                        self.frame.compass_sprite,
                    ) {
                        let key = {
                            let mut hasher = DefaultHasher::new();
                            (
                                self.frame.compass_sprite.map(SpriteId::get),
                                mask.size,
                                gameframe.compass.image.width(),
                                gameframe.compass.image.height(),
                                angle & 2047,
                            )
                                .hash(&mut hasher);
                            hasher.finish()
                        };
                        if key != self.compass_key || self.compass_pixels.is_empty() {
                            self.compass_key = key;
                            self.compass_revision += 1;
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
                self.ui.project_reusing(
                    assets,
                    self.state.title_screen(),
                    self.cycle,
                    &mut self.frame,
                );
            }
            let pointer = self.cursor.map(|(x, y)| self.ui_pointer(&window, x, y));
            self.shell
                .project_overlay(&mut self.frame, &self.widget_tree, pointer);
            // SPEC-0021 input arbitration: an open menu is the topmost layer, so it paints last.
            if let Some(assets) = &self.assets {
                self.context_menu
                    .project(assets, &self.widget_assets.font_icons, &mut self.frame);
                self.widget_menu
                    .project(assets, &self.widget_assets.font_icons, &mut self.frame);
            }
            let visibility = frame_visibility
                .take()
                .unwrap_or_else(|| self.scene_visibility(&window));
            self.renderer.as_mut().map(|renderer| {
                if let (Some(descriptor), Some(camera)) =
                    (self.state.displayed_scene(), self.scene.camera)
                {
                    return renderer.render_world(
                        descriptor.id(),
                        camera,
                        &visibility,
                        self.state.scenery().cycle(),
                        &self.frame,
                        rustclient_renderer::DynamicImages {
                            flame_revision: 0,
                            minimap: dynamic_image(
                                &self.minimap_pixels,
                                self.minimap_mask.as_ref(),
                                self.minimap_revision,
                            ),
                            compass: dynamic_image(
                                &self.compass_pixels,
                                self.compass_mask.as_ref(),
                                self.compass_revision,
                            ),
                            world_map: rustclient_renderer::DynamicImage {
                                pixels: &self.world_map_pixels,
                                width: self.world_map_size[0],
                                height: self.world_map_size[1],
                                revision: self.world_map_revision,
                            },
                            world_map_overview: rustclient_renderer::DynamicImage {
                                pixels: &self.world_map_overview_pixels,
                                width: self.world_map_overview_size[0],
                                height: self.world_map_overview_size[1],
                                revision: self.world_map_revision,
                            },
                        },
                        window.scale_factor(),
                        || window.pre_present_notify(),
                    );
                }
                renderer.render_ui_with_flame_revision(
                    &self.frame,
                    flames.pixels(),
                    flames.revision(),
                    window.scale_factor(),
                    || {
                        window.pre_present_notify();
                    },
                )
            })
        } else {
            self.renderer.as_mut().map(|renderer| {
                renderer.render_clear(ClearColor::BOOTSTRAP, || window.pre_present_notify())
            })
        };
        self.performance.note(|| {
            format!(
                "quads {} world_viewport {:?} nodes {} root {:?} mounts {:?} entered {} loaded {} result {:?} shell {:?} geometry {:?} picking {:?} ui {:?} listener_rebuilds {:?} map_jobs submitted={} published={} stale={}",
                self.frame.quads.len() + self.frame.world_quads.len(),
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
                self.renderer.as_ref().map(rustclient_renderer::SurfaceRenderer::ui_stats),
                self.scripts.as_ref().map(rustclient_application::scripts::ClientScripts::listener_index_rebuilds),
                self.map_composer.submitted,
                self.map_composer.published,
                self.map_composer.stale,
            )
        });
        if let Some(pool) = &self.scene.jobs {
            self.performance.job_stats(|| pool.stats());
        }
        if matches!(result, Some(Ok(FrameOutcome::Suspended))) {
            self.performance.suspend_presentation();
            self.frame_limit.reset();
        }
        if let Some(renderer) = &mut self.renderer {
            self.performance.render_metrics(
                frame_start,
                matches!(result, Some(Ok(FrameOutcome::Presented))),
                renderer,
            );
        }
        if matches!(result, Some(Ok(FrameOutcome::Presented))) {
            self.publish_dynamic_if_ready();
        }
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
    revision: u64,
) -> rustclient_renderer::DynamicImage<'a> {
    mask.filter(|(_, mask)| {
        u64::from(mask.size[0]) * u64::from(mask.size[1]) * 4 == pixels.len() as u64
    })
    .map_or_else(Default::default, |(_, mask)| {
        rustclient_renderer::DynamicImage {
            pixels,
            width: mask.size[0],
            height: mask.size[1],
            revision,
        }
    })
}
/// Whether the static scene baked into `scene` shows the committed scenery: identical
/// overrides, or a baseline-plus-patch the animation worker publishes (SPEC-0016). Variables
/// never change static geometry; locations that morph on them are animated, not baked.
fn static_scene_shows<M>(
    scene: &rustclient_application::LoadedScene<M>,
    scenery: &rustclient_game_state::SceneryState,
    roof_areas: &std::sync::Arc<[rustclient_scene::RoofArea]>,
    animated: bool,
) -> bool {
    let inputs = &scene.projection_inputs;
    if inputs.roof_areas != *roof_areas {
        return false;
    }
    if inputs.locations == *scenery.locations() {
        return true;
    }
    animated
        && inputs.base_locations.as_deref().is_some_and(|base| {
            rustclient_scene::patch_supported(&scene.asset, base, scenery.locations())
        })
}
impl NativeApp {
    /// Reject obsolete worker output before any GPU, picking or minimap publication.
    fn discard_obsolete_scene<M>(
        &mut self,
        scene: &rustclient_application::LoadedScene<M>,
    ) -> bool {
        if static_scene_shows(scene, self.state.scenery(), &self.scene.roof_areas, true) {
            return false;
        }
        self.performance
            .event(|| "discarded obsolete scenery projection".into());
        // A scene being replaced is never the one this projection stands in for.
        if self.scene.replacing.is_none()
            && let Some(loaded) = &mut self.scene.loaded
            && static_scene_shows(
                loaded,
                self.state.scenery(),
                &self.scene.roof_areas,
                self.scene.animator.is_some(),
            )
        {
            // A -> B -> A while B was building needs no replacement of A.
            loaded.projection_generation = self.state.scenery().generation();
            return true;
        }
        if let Some(placement) = self.state.initial_placement()
            && let Err(error) = self.scene.reproject(
                scene.asset.clone(),
                placement,
                self.scene.scenery_inputs(self.state.scenery()),
                self.scene.generation,
                self.proxy.clone(),
            )
        {
            eprintln!("scenery projection: {error}");
        }
        true
    }
    /// Keep compatible newer poses without exposing mutable static prepared geometry.
    fn preserve_scene_pose(
        &mut self,
        scene: &mut rustclient_application::LoadedScene,
    ) -> Result<(), rustclient_renderer::RenderError> {
        if let Some(old) = &mut self.scene.loaded
            && old
                .projection_inputs
                .same_geometry(&scene.projection_inputs)
            && old.projection_inputs.roof_areas == scene.projection_inputs.roof_areas
            && old.projection_inputs.interpolation == scene.projection_inputs.interpolation
            && self.scene.dynamic_cycle > scene.projection_inputs.cycle
        {
            std::mem::swap(&mut old.mesh.dynamic, &mut scene.mesh.dynamic);
            scene
                .picking
                .set_hidden(&scene.mesh.geometry, &scene.mesh.dynamic.hidden_static);
            scene.projection_inputs.cycle = self.scene.dynamic_cycle;
            if let Some(renderer) = &mut self.renderer {
                renderer.set_world_dynamic(scene.mesh.asset, &scene.mesh.dynamic)?;
            }
        }
        Ok(())
    }
    /// The scene projected again after a renderer was created without its released triangles.
    fn note_world_rebuilt(&mut self) {
        if let Some(since) = self.scene.awaiting_world.take() {
            self.performance.event(|| {
                format!(
                    "world rebuilt {:.1} ms after the renderer was recreated",
                    since.elapsed().as_secs_f64() * 1000.
                )
            });
        }
    }
    /// Apply the window placement saved by the last run, if it still fits the monitors.
    fn restore_window_placement(
        &self,
        event_loop: &ActiveEventLoop,
        attributes: winit::window::WindowAttributes,
    ) -> winit::window::WindowAttributes {
        use rustclient_application::client_preferences::MonitorRect;
        let min = (765 + self.shell.width(), 503);
        let Some(placement) = self
            .client_preferences
            .window
            .and_then(|placement| placement.sanitized(min))
        else {
            return attributes;
        };
        let monitors: Vec<MonitorRect> = event_loop
            .available_monitors()
            .map(|monitor| {
                let position = monitor.position();
                let size = monitor.size();
                MonitorRect {
                    x: position.x,
                    y: position.y,
                    width: size.width,
                    height: size.height,
                }
            })
            .collect();
        let mut attributes = attributes
            .with_inner_size(winit::dpi::PhysicalSize::new(
                placement.width,
                placement.height,
            ))
            .with_maximized(placement.maximized);
        if placement.reachable(&monitors) {
            attributes = attributes
                .with_position(winit::dpi::PhysicalPosition::new(placement.x, placement.y));
        }
        attributes
    }
    /// Remember where the window is. A maximized window keeps the restored size it had before.
    fn save_window_placement(&mut self, window: &Window) {
        use rustclient_application::client_preferences::WindowPlacement;
        let maximized = window.is_maximized();
        let placement = match (maximized, window.outer_position()) {
            (false, Ok(position)) => {
                let size = window.inner_size();
                Some(WindowPlacement {
                    x: position.x,
                    y: position.y,
                    width: size.width,
                    height: size.height,
                    maximized: false,
                })
            }
            (true, _) => self
                .client_preferences
                .window
                .map(|placement| WindowPlacement {
                    maximized: true,
                    ..placement
                })
                .or_else(|| {
                    let size = window.inner_size();
                    window
                        .outer_position()
                        .ok()
                        .map(|position| WindowPlacement {
                            x: position.x,
                            y: position.y,
                            width: size.width,
                            height: size.height,
                            maximized: true,
                        })
                }),
            (false, Err(_)) => None,
        };
        let Some(placement) = placement else {
            return;
        };
        self.client_preferences.window = Some(placement);
        if let Some(storage) = &self.storage
            && let Err(error) = self.client_preferences.save(storage)
        {
            eprintln!("{error}");
        }
    }
    /// A scene load failed: retry once, then end the session at the title with the reason.
    fn scene_load_failed(&mut self, error: &str) {
        eprintln!("spawn scene: {error}");
        match self.scene.note_load_failure() {
            scene::SceneFailure::Retry => {
                eprintln!("spawn scene: retrying the load once");
                // `start_pending_scene` runs again on the next cycle.
                if let Some(window) = &self.window {
                    window.request_redraw();
                }
            }
            scene::SceneFailure::Abort => {
                self.apply_network_event(network::NetworkEvent::Failed(
                    rustclient_game_state::LoginFailure::SceneLoad,
                    format!("scene load failed twice: {error}"),
                ));
            }
        }
    }
    fn finish_scene(
        &mut self,
        event_loop: &ActiveEventLoop,
        result: Result<scene::PreparedScene, String>,
    ) {
        self.scene.loading = false;
        match result {
            Ok(mut scene) => {
                if self.discard_obsolete_scene(&scene) {
                    return;
                }
                scene.projection_generation = self.state.scenery().generation();
                let first = self.state.loaded_scene().is_none();
                eprintln!(
                    "Spawn scene: {:?}; {} vertices; {} auxiliary map files",
                    scene.mesh.mesh().stats,
                    scene.mesh.mesh().vertex_count(),
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
                let resources_changed = self
                    .scene
                    .loaded
                    .as_ref()
                    .is_none_or(|old| !std::sync::Arc::ptr_eq(&old.asset, &scene.asset));
                let upload_started = Instant::now();
                // Extending the cache catalog inside a live scene restarts resource workers,
                // but does not start a new map/session or erase its actors.
                let update = if first || self.scene.loaded.is_none() {
                    rustclient_renderer::WorldUpdate::Scene
                } else {
                    rustclient_renderer::WorldUpdate::Scenery
                };
                let scene = match self.upload_scene_projection(scene, update) {
                    Ok(scene) => scene,
                    Err(error) => {
                        self.fail(event_loop, &error.to_string());
                        return;
                    }
                };
                self.performance
                    .stage(performance::Stage::SceneUpload, upload_started);
                self.scene.locations.cursor = self.state.projection_cursor();
                self.scene.locations.invalidate = false;
                self.scene.ensure_workers(
                    scene.asset.clone(),
                    resources_changed,
                    scene.projection_inputs.clone(),
                    self.proxy.clone(),
                );
                if let Some(placement) = self.state.initial_placement()
                    && (resources_changed || self.minimap_plane != Some(placement.position.plane))
                {
                    let started = Instant::now();
                    self.minimap = Some(std::sync::Arc::new(build_scene_minimap(
                        &scene.asset,
                        placement.position.plane,
                    )));
                    self.minimap_plane = Some(placement.position.plane);
                    self.minimap_generation += 1;
                    self.performance
                        .stage(performance::Stage::MinimapBuild, started);
                }
                self.scene.dynamic_cycle = scene.projection_inputs.cycle;
                self.note_world_rebuilt();
                self.scene.loaded = Some(scene);
                if let Some(since) = self.scene.replacing.take() {
                    self.performance.event(|| {
                        format!(
                            "map replacement published {:.1} ms after the rebuild",
                            since.elapsed().as_secs_f64() * 1000.
                        )
                    });
                }
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
            Err(error) => self.scene_load_failed(&error),
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
    fn new_events(&mut self, _event_loop: &ActiveEventLoop, cause: winit::event::StartCause) {
        self.performance.wake(cause);
    }

    fn resumed(&mut self, event_loop: &ActiveEventLoop) {
        if self.window.is_some() {
            return;
        }
        let attributes = Window::default_attributes()
            .with_title(format!("RustClient — {}", self.load_status))
            .with_inner_size(LogicalSize::new(765. + f64::from(self.shell.width()), 503.))
            .with_min_inner_size(LogicalSize::new(765. + f64::from(self.shell.width()), 503.));
        let attributes = self.restore_window_placement(event_loop, attributes);
        match event_loop.create_window(attributes) {
            Ok(window) => {
                window.set_ime_allowed(true);
                self.window = Some(Arc::new(window));
                self.scene.prewarm();
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
            LoadEvent::MapComposed(identity, result) => {
                if identity.session == self.connection_generation {
                    self.map_composer.receive(identity, *result);
                }
                if let Some(window) = &self.window {
                    window.request_redraw();
                }
            }
            LoadEvent::WorldMapAssets(identity, result) => {
                if identity.session == self.connection_generation {
                    self.map_view.receive(identity, *result);
                }
                if let Some(window) = &self.window {
                    window.request_redraw();
                }
            }
            LoadEvent::ScriptsReady(result) => match *result {
                Ok(mut scripts) => {
                    match scripts.initialize_session(&mut self.state) {
                        Ok(errors) => {
                            for error in errors {
                                eprintln!("session scripts: {error}");
                            }
                        }
                        Err(error) => eprintln!("session scripts: {error}"),
                    }
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
                    self.widget_paint = None;
                    if let (Some(renderer), Some(title)) = (&mut self.renderer, &self.assets)
                        && let Err(e) =
                            renderer.set_widget_images(title, &self.widget_assets.images)
                    {
                        eprintln!("widget GPU upload: {e}");
                        return;
                    }
                    self.minimap_mask = None;
                    self.compass_mask = None;
                    self.minimap_key = 0;
                    self.compass_key = 0;
                    self.scratch.widget_requested.absorb(&request);
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
            LoadEvent::LocationResources(session, resources, result) => {
                self.accept_location_resources(session, resources, *result);
            }
            LoadEvent::DynamicReady(session, worker, inputs, result) => {
                let cycle = inputs.cycle;
                if session == self.scene.generation
                    && worker == self.scene.animator_generation
                    && self.scene.animator_in_flight == Some(cycle)
                {
                    self.scene.animator_in_flight = None;
                }
                if session == self.scene.generation
                    && worker == self.scene.animator_generation
                    && cycle >= self.scene.dynamic_cycle
                    && self.scene.replacing.is_none()
                    && let Some(loaded) = &self.scene.loaded
                    && inputs.matches_state(self.state.scenery(), &loaded.asset)
                    && inputs
                        .base_locations
                        .as_ref()
                        .zip(loaded.projection_inputs.base_locations.as_ref())
                        .is_some_and(|(posed, baked)| std::sync::Arc::ptr_eq(posed, baked))
                    && inputs.roof_areas == loaded.projection_inputs.roof_areas
                    && inputs.interpolation == self.scene.interpolation
                {
                    match *result {
                        Ok(projection) => self.accept_dynamic(*inputs, projection),
                        Err(e) => {
                            eprintln!(
                                "scenery preparation: {e}; retained presentation, retry pending"
                            );
                            self.scene.locations.projection_retry = Some((
                                self.state.scenery().generation(),
                                Instant::now() + Duration::from_millis(500),
                            ));
                        }
                    }
                }
            }
            LoadEvent::DynamicFault(session, worker, cycle) => {
                if session == self.scene.generation && worker == self.scene.animator_generation {
                    if self.scene.animator_in_flight == Some(cycle) {
                        self.scene.animator_in_flight = None;
                    }
                    self.scene.animator = None;
                    eprintln!("scenery animation worker panicked; reconnect to retry");
                }
            }
            LoadEvent::EntityFault(session, worker, error) => {
                if session == self.scene.generation && worker == self.scene.entity_loader_generation
                {
                    self.scene.cancel_entity_request();
                    self.scene.entity_loader = None;
                    eprintln!("entity asset worker: {error}");
                }
            }
            LoadEvent::EntityAssets(session, worker, assets) => {
                if session == self.scene.generation && worker == self.scene.entity_loader_generation
                {
                    self.scene.entity_request = None;
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
                    self.ui.invalidate_projection();
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
                self.save_window_placement(&window);
                self.shell.prepare_close();
                self.update_plugins();
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
                if let Some((x, y)) = self.cursor.filter(|_| {
                    !rustclient_game_ui::arbitration::suppress_gestures(self.menu_open())
                }) {
                    let (x, y) = self.ui_pointer(&window, x, y);
                    let over_world_map = self.frame.quads.iter().any(|q| {
                        q.image == rustclient_game_ui::UiImageId::WorldMap
                            && x >= q.rect.x
                            && x <= q.rect.x + q.rect.width
                            && y >= q.rect.y
                            && y <= q.rect.y + q.rect.height
                    });
                    let over_minimap = self.frame.quads.iter().any(|q| {
                        q.image == rustclient_game_ui::UiImageId::Minimap
                            && x >= q.rect.x
                            && x <= q.rect.x + q.rect.width
                            && y >= q.rect.y
                            && y <= q.rect.y + q.rect.height
                    });
                    if over_minimap && self.zoom_minimap_with_wheel(amount) {
                        window.request_redraw();
                    } else if over_world_map {
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
                // Plugins build the menu with Shift state, so the hovered menu must be rebuilt.
                self.hover_dirty = true;
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
                self.map_pointer([x, y], false, true);
                self.ui.pointer(x, y);
                self.context_menu.pointer(x, y);
                self.widget_menu.pointer(x, y);
                self.hover_dirty = true;
                window.request_redraw();
            }
            WindowEvent::CursorLeft { .. } => {
                self.cancel_map_input();
                self.scene.input.middle = false;
                self.close_menus();
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
                // A press never starts a camera drag under an open menu; a release always ends one.
                self.scene.input.middle = state == ElementState::Pressed
                    && !rustclient_game_ui::arbitration::suppress_gestures(self.menu_open());
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
                        // Popups own input above every underlying gameframe widget.
                        if self.press_open_menu(&window, x, y) {
                            window.request_redraw();
                            return;
                        }
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
                    let point = self.ui_pointer(&window, x, y);
                    self.map_pointer([point.0, point.1], false, false);
                }
                self.world_map_input_bounds = None;
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
                    // An open menu takes the press first: select the row under the pointer, or close.
                    if self.press_open_menu(&window, x, y) {
                        window.request_redraw();
                        return;
                    }
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
                self.cancel_map_input();
                self.window_focused = false;
                self.close_menus();
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
                self.performance.suspend_presentation();
                self.frame_limit.reset();
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
        if self.performance.run_complete() {
            let done = self
                .renderer
                .as_mut()
                .is_none_or(|renderer| self.performance.finish_run(renderer));
            if done {
                event_loop.exit();
            } else {
                event_loop.set_control_flow(ControlFlow::WaitUntil(
                    Instant::now() + Duration::from_millis(2),
                ));
            }
            return;
        }
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
                    // Local plugin activation and panels also run on the title/login screen.
                    self.update_plugins();
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
                            if self.scratch.reported_script_errors.len() < 256
                                && self.scratch.reported_script_errors.insert(error.clone())
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
                                if self.scratch.reported_script_errors.len() < 256
                                    && self.scratch.reported_script_errors.insert(error.clone())
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
                self.schedule_scene_projection();
                // Collect the final widget dependencies once for this cycle batch, rather
                // than walking the same widget tree after each unrelated inbound packet.
                let started = Instant::now();
                self.request_widget_assets();
                self.performance
                    .stage(performance::Stage::WidgetAssets, started);
            }
            // A hidden window presents nothing: posing scenery and entities for it is wasted
            // work, and the next visible cycle rebuilds both from authoritative state.
            let visible = !self.occluded;
            if advanced
                && visible
                && self.scene.replacing.is_none()
                && self.scene.animator_in_flight.is_none()
                && self
                    .scene
                    .locations
                    .projection_retry
                    .is_none_or(|(generation, until)| {
                        generation != self.state.scenery().generation() || Instant::now() >= until
                    })
                && self.scene.location_resources_ready(self.state.scenery())
                && let Some(animator) = &self.scene.animator
                && animator
                    .try_send(
                        self.scene.animator_inputs(self.state.scenery()),
                        self.minimap_plane.unwrap_or(rustclient_model::Plane::MIN),
                        self.scene.locations.invalidate,
                    )
                    .is_ok()
            {
                self.scene.locations.invalidate = false;
                self.scene.animator_in_flight = Some(self.state.scenery().cycle());
            }
            if advanced
                && visible
                && let Some(loaded) = &self.scene.loaded
            {
                if self.scene.entity_request.is_none()
                    && let Some(loader) = &self.scene.entity_loader
                    && let Some(request) = self.scene.entity_catalog.request_missing(
                        &loaded.asset,
                        self.state.entities(),
                        self.state.scenery(),
                        self.state.ground_items(),
                    )
                {
                    let in_flight = request.clone();
                    match loader.try_send(request) {
                        Ok(()) => self.scene.entity_request = Some(in_flight),
                        Err(_) => self.scene.entity_catalog.retry_request(&in_flight),
                    }
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
                    &mut self.scene.entity_catalog,
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
                    && self.scene.awaiting_world.is_none()
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

#[cfg(test)]
mod session_scratch_tests {
    use super::SessionScratch;

    #[test]
    fn reset_returns_every_session_field_to_its_initial_value() {
        let mut scratch = SessionScratch::default();
        scratch.reported_script_errors.insert("boom".into());
        scratch.plugin_wheel_zoom = true;
        scratch.minimap_hidden = true;
        scratch
            .widget_requested
            .sprites
            .insert(rustclient_model::SpriteId::new(1));
        scratch.reset();
        assert!(scratch.reported_script_errors.is_empty());
        assert!(!scratch.plugin_wheel_zoom && !scratch.minimap_hidden);
        assert!(scratch.widget_requested.sprites.is_empty());
    }
}
