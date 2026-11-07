//! Native composition of first-party and ABI 0.1 component plugins.
//!
//! The client hands plugins only a read-only session view and mediated storage; committed game
//! state, renderer resources and transport never cross the component boundary.

use rustclient_cache::TitleAssets;
use rustclient_game_state::GameState;
use rustclient_game_ui::{
    Rect, UiFrame, UiImageId, draw_text, text_width, widget_visuals::WidgetVisualAssets,
};
use rustclient_plugin_api::{
    HorizontalAlign, Notification, OverlayCommand, PlayerPosition, PluginEvent,
};
use rustclient_plugin_bridge::{Frame, Projector, Sources};
use rustclient_plugin_host::{
    DirectoryStorage, HostOutput, HostView, PluginConfig, PluginSet, SnapshotView, StaticView,
    StderrLogSink, configuration::Configuration, required::Builtin,
};
use rustclient_plugin_runtime_native::NativeRuntime;
use std::{collections::BTreeMap, path::Path};

pub(super) struct Plugins {
    set: PluginSet,
    storage: DirectoryStorage,
    logs: StderrLogSink,
    started: bool,
    tick: u64,
    last_frame: Frame,
    projector: Projector,
    output: HostOutput,
    /// Last configuration pushed to each first-party plugin, so unchanged ones are not re-sent.
    applied: BTreeMap<String, (PluginConfig, bool)>,
    pending: BTreeMap<String, (rustclient_plugin_api::PluginId, PluginConfig, bool)>,
    /// Plugin menu entries chosen since the last update, delivered as `MenuOptionClicked`.
    menu_clicks: Vec<rustclient_plugin_api::MenuEntry>,
    /// Scripts finished since the last update, delivered as `ScriptPostFired`.
    scripts_finished: Vec<rustclient_model::ScriptId>,
}

/// Shell-owned facts and cache definitions the projector consults.
pub(super) struct Inputs<'a> {
    pub frame: Frame,
    pub sources: Sources<'a>,
}

impl Plugins {
    /// Script invocations that finished, in run order; the next update delivers them as
    /// `ScriptPostFired`.
    pub(super) fn scripts_finished(&mut self, ids: Vec<rustclient_model::ScriptId>) {
        self.scripts_finished.extend(ids);
    }
    /// A world menu entry was chosen; the next update delivers it as `MenuOptionClicked`.
    pub(super) fn menu_chosen(&mut self, entry: rustclient_plugin_api::MenuEntry) {
        self.menu_clicks.push(entry);
    }
    /// Whether any running plugin subscribes to a menu hook.
    pub(super) fn wants_menu_stage(&self) -> bool {
        self.set.wants_menu_stage()
    }
    /// Run the synchronous menu stage over `entries` against the latest projection.
    pub(super) fn menu_stage(
        &mut self,
        entries: &[rustclient_plugin_api::MenuEntry],
        opened: bool,
    ) -> rustclient_plugin_host::EditedMenu {
        let Some(snapshot) = self.projector.latest() else {
            return rustclient_plugin_host::EditedMenu::identity(entries.len());
        };
        self.set.menu_stage(
            entries,
            opened,
            &SnapshotView(&snapshot),
            &mut self.storage,
            &mut self.logs,
        )
    }
    /// Whether a running plugin may read client facts, which include the tile under the pointer.
    pub(super) fn reads_client(&self) -> bool {
        self.set
            .granted()
            .contains(rustclient_plugin_api::Capability::GameReadClient)
    }
    /// Load every package directory under `packages`, skipping failures.
    ///
    /// Plugin-local persisted data is rooted at `storage_root`; the package tree itself is
    /// treated as read-only input.
    pub(super) fn load(packages: &Path, storage_root: &Path) -> Self {
        let mut storage = DirectoryStorage::new(storage_root.to_path_buf());
        let mut logs = StderrLogSink;
        let mut set = PluginSet::new();
        for service in Builtin::ALL {
            if let Err(error) = set.register_required(service) {
                eprintln!("required plugin registration: {error}");
            }
        }
        if let Err(error) = rustclient_first_party_plugins::register_all(&mut set) {
            eprintln!("first-party plugin registration: {error}");
        }
        set.init(&StaticView::default(), &mut storage, &mut logs);
        if !packages.is_dir() {
            return Self {
                set,
                storage,
                logs,
                started: false,
                tick: 0,
                last_frame: Frame::default(),
                projector: Projector::new(),
                output: HostOutput::default(),
                applied: BTreeMap::new(),
                pending: BTreeMap::new(),
                menu_clicks: Vec::new(),
                scripts_finished: Vec::new(),
            };
        }
        match NativeRuntime::new() {
            Ok(runtime) => {
                let mut entries = std::fs::read_dir(packages)
                    .map(|entries| {
                        entries
                            .flatten()
                            .map(|entry| entry.path())
                            .collect::<Vec<_>>()
                    })
                    .unwrap_or_default();
                entries.sort();
                for package in entries.into_iter().take(32) {
                    if !package.is_dir() {
                        continue;
                    }
                    match runtime.load(&package) {
                        Ok((manifest, instance)) => {
                            let id = manifest.id.clone();
                            match set.register(manifest, instance) {
                                Ok(()) => eprintln!("plugin loaded: {id}"),
                                Err(fault) => eprintln!("plugin {id} rejected: {fault}"),
                            }
                        }
                        Err(fault) => {
                            eprintln!("plugin {} unavailable: {fault}", package.display());
                        }
                    }
                }
            }
            Err(fault) => eprintln!("plugin runtime unavailable: {fault}"),
        }
        // Optional components are discovered after required services and initialize before login.
        set.init(&StaticView::default(), &mut storage, &mut logs);
        Self {
            set,
            storage,
            logs,
            started: false,
            tick: 0,
            last_frame: Frame::default(),
            projector: Projector::new(),
            output: HostOutput::default(),
            applied: BTreeMap::new(),
            pending: BTreeMap::new(),
            menu_clicks: Vec::new(),
            scripts_finished: Vec::new(),
        }
    }

    /// Push the accepted configuration of every optional first-party plugin to its instance.
    ///
    /// Only plugins whose settings or switch changed since the last call are reconfigured, and a
    /// plugin that becomes enabled starts from a clean state.
    pub(super) fn sync<'a>(&mut self, configurations: impl Iterator<Item = &'a Configuration>) {
        for configuration in configurations {
            let schema = configuration.schema();
            if schema.required {
                continue;
            }
            let (config, enabled) = (PluginConfig::of(configuration), configuration.enabled());
            let key = schema.plugin.as_str();
            if self
                .applied
                .get(key)
                .is_some_and(|(have, on)| *have == config && *on == enabled)
            {
                continue;
            }
            let registered = self
                .set
                .iter()
                .any(|plugin| plugin.manifest.id == schema.plugin);
            if !registered {
                continue;
            }
            if enabled {
                self.pending
                    .insert(key.to_owned(), (schema.plugin.clone(), config, enabled));
            } else {
                if let Err(fault) = self.set.configure(
                    &schema.plugin,
                    config.clone(),
                    false,
                    &StaticView::default(),
                    &mut self.storage,
                    &mut self.logs,
                ) {
                    eprintln!("plugin {key} cleanup: {fault}");
                }
                self.pending.remove(key);
                self.applied.insert(key.to_owned(), (config, enabled));
                self.output = self.set.retained_output();
            }
        }
    }

    /// Dispatch `Started` once per authenticated session.
    pub(super) fn start(&mut self, state: &GameState) {
        if self.started {
            return;
        }
        self.started = true;
        let view = SessionView::from_state(state, self.tick);
        self.set.dispatch(
            &[PluginEvent::Started],
            &view,
            &mut self.storage,
            &mut self.logs,
        );
    }

    /// Advance the session tick and dispatch one batched event per rendered frame.
    pub(super) fn advance(&mut self, state: &GameState, steps: u32) {
        self.tick = self.tick.wrapping_add(u64::from(steps));
        if self.set.is_empty() {
            return;
        }
        let view = SessionView::from_state(state, self.tick);
        self.set.dispatch(
            &[PluginEvent::Tick(self.tick)],
            &view,
            &mut self.storage,
            &mut self.logs,
        );
    }

    /// Project committed state, deliver typed events and collect validated plugin output.
    ///
    /// Only granted query categories are projected. Minimal client/timing history advances even
    /// without grants so later enablement cannot replay a synthetic session transition.
    pub(super) fn update(
        &mut self,
        state: &GameState,
        inputs: &Inputs<'_>,
        actions: &[rustclient_plugin_host::PanelAction],
    ) {
        self.last_frame = inputs.frame;
        for (key, (id, config, enabled)) in std::mem::take(&mut self.pending) {
            let grants = self
                .set
                .iter()
                .find(|plugin| plugin.manifest.id == id)
                .map(|plugin| plugin.manifest.capabilities.clone())
                .unwrap_or_default();
            let bootstrap = self
                .projector
                .snapshot(state, &inputs.frame, &inputs.sources, &grants);
            if let Err(fault) = self.set.configure(
                &id,
                config.clone(),
                enabled,
                &SnapshotView(&bootstrap),
                &mut self.storage,
                &mut self.logs,
            ) {
                eprintln!("plugin {key} startup: {fault}");
            }
            self.applied.insert(key, (config, enabled));
        }
        let wanted = self.set.granted();
        let interest = self.set.game_event_interest();
        let (snapshot, mut events) = self.projector.project_filtered(
            state,
            &inputs.frame,
            &inputs.sources,
            &wanted,
            interest.as_ref(),
            self.set.game_event_kinds(),
        );
        if wanted.is_empty() && interest.is_none() {
            self.output = HostOutput::default();
            self.menu_clicks.clear();
            self.scripts_finished.clear();
            return;
        }
        // Scripts ran inside the cycle's packet and input stages, before this end-of-cycle
        // projection; clicks were chosen after the previous one.
        events.extend(
            std::mem::take(&mut self.scripts_finished)
                .into_iter()
                .map(|id| rustclient_plugin_api::GameEvent::ScriptPostFired(id.get())),
        );
        events.extend(
            std::mem::take(&mut self.menu_clicks)
                .into_iter()
                .map(rustclient_plugin_api::GameEvent::MenuOptionClicked),
        );
        let view = SnapshotView(&snapshot);
        for action in actions {
            self.set
                .dispatch_panel_action(action, &view, &mut self.storage, &mut self.logs);
        }
        self.set
            .dispatch_game(&events, &view, &mut self.storage, &mut self.logs);
        self.output = self
            .set
            .collect_output(&view, &mut self.storage, &mut self.logs);
        self.report_storage_errors();
    }

    /// Log plugin configuration files that could not be read or saved.
    fn report_storage_errors(&self) {
        for error in self.storage.take_errors() {
            eprintln!("{error}");
        }
    }

    /// Dispatch `Stopped` once when the session ends.
    pub(super) fn stop(&mut self, state: &GameState) {
        if !self.started {
            return;
        }
        self.started = false;
        let (snapshot, events) = self.projector.project(
            state,
            &Frame {
                loading: false,
                ..self.last_frame
            },
            &Sources::default(),
            &self.set.granted(),
        );
        self.set.dispatch_game(
            &events,
            &SnapshotView(&snapshot),
            &mut self.storage,
            &mut self.logs,
        );
        self.projector.reset();
        let view = SessionView::from_state(state, self.tick);
        self.set.dispatch(
            &[PluginEvent::Stopped],
            &view,
            &mut self.storage,
            &mut self.logs,
        );
        self.output = self.set.retained_output();
        self.report_storage_errors();
    }

    pub(super) fn statuses(
        &self,
    ) -> BTreeMap<String, (rustclient_plugin_host::LifecycleStatus, Option<String>)> {
        self.set
            .iter()
            .map(|plugin| {
                (
                    plugin.manifest.id.to_string(),
                    (
                        plugin.status(),
                        plugin.last_fault().map(ToString::to_string),
                    ),
                )
            })
            .collect()
    }
    /// Notifications raised by the last update, each reported once.
    pub(super) fn take_notifications(&mut self) -> Vec<Notification> {
        std::mem::take(&mut self.output.notifications)
            .into_iter()
            .map(|(_, notification)| notification)
            .collect()
    }

    /// The saved minimap zoom a plugin asked for in the last update, reported once.
    pub(super) fn take_minimap_zoom(&mut self) -> Option<f64> {
        self.output.minimap.zoom.take()
    }

    /// The merged declarative output of the last update.
    pub(super) fn output(&self) -> &HostOutput {
        &self.output
    }

    /// Cache sprites the last update's overlay wants drawn.
    pub(super) fn requested_sprites(&self) -> impl Iterator<Item = u32> + '_ {
        self.output
            .overlay
            .iter()
            .filter_map(|item| match item.command {
                OverlayCommand::Sprite { sprite, .. } => Some(sprite),
                _ => None,
            })
    }

    /// Draw plugin overlay lines topmost in the `765x503` interface space.
    pub(super) fn overlay(
        &mut self,
        state: &GameState,
        assets: &TitleAssets,
        sprites: &WidgetVisualAssets,
        frame: &mut UiFrame,
    ) {
        if self.set.is_empty() {
            return;
        }
        self.draw_output(assets, sprites, frame);
        let view = SessionView::from_state(state, self.tick);
        let lines = self.set.overlay(&view, &mut self.storage, &mut self.logs);
        let clip = Rect::new(0., 0., 765., 503.);
        for line in lines {
            let [alpha, red, green, blue] = line.argb.to_be_bytes();
            draw_text(
                frame,
                assets,
                &line.text,
                f32::from(line.x),
                f32::from(line.y),
                [red, green, blue, alpha],
                clip,
            );
        }
    }
}

impl Plugins {
    /// Draw the 0.2 overlay primitives that need no world projection; the rest are reported once.
    fn draw_output(
        &mut self,
        assets: &TitleAssets,
        sprites: &WidgetVisualAssets,
        frame: &mut UiFrame,
    ) {
        let clip = Rect::new(0., 0., 765., 503.);
        let canvas = frame.canvas.map_or(clip, |size| {
            Rect::new(
                0.,
                0.,
                px(i32::try_from(size.width).unwrap_or(i32::MAX)),
                px(i32::try_from(size.height).unwrap_or(i32::MAX)),
            )
        });
        for item in &self.output.overlay {
            match &item.command {
                OverlayCommand::Text {
                    text,
                    x,
                    y,
                    argb,
                    align,
                } => {
                    let [alpha, red, green, blue] = argb.to_be_bytes();
                    let left = aligned(px(*x), text_width(assets, text), *align);
                    draw_text(
                        frame,
                        assets,
                        text,
                        left,
                        px(*y),
                        [red, green, blue, alpha],
                        canvas,
                    );
                }
                OverlayCommand::Sprite {
                    sprite,
                    x,
                    y,
                    align,
                } => {
                    let id = UiImageId::Sprite(*sprite);
                    if let Some(image) = sprites.images.get(&id) {
                        #[expect(
                            clippy::cast_precision_loss,
                            reason = "sprite edges are far below f32 integer precision"
                        )]
                        let (width, height) = (image.width() as f32, image.height() as f32);
                        let left = aligned(px(*x), width, *align);
                        frame.image(id, Rect::new(left, px(*y), width, height), canvas);
                    }
                }
                OverlayCommand::Rect {
                    x,
                    y,
                    width,
                    height,
                    argb,
                    filled,
                } => draw_rect(frame, canvas, [*x, *y, *width, *height], *argb, *filled),
                OverlayCommand::Line { from, to, argb } => {
                    draw_line(frame, canvas, *from, *to, *argb);
                }
                // World-anchored commands are projected by `world_overlays`; minimap dots are
                // drawn into the minimap image.
                OverlayCommand::Tile { .. }
                | OverlayCommand::ActorHull { .. }
                | OverlayCommand::ActorText { .. }
                | OverlayCommand::TileText { .. }
                | OverlayCommand::MinimapDot { .. } => {}
            }
        }
        self.draw_info_boxes(assets, sprites, frame, canvas);
    }

    /// `RuneLite` `InfoBoxOverlay`: 35-pixel boxes, one pixel apart, four per row, from the
    /// world view's top-left snap corner (5 pixels in, 20 down to clear the mouse-over text).
    /// Each box is `InfoBoxComponent`: the standard translucent background with its darker outer
    /// and lighter inner stroke, the image centred, the caption centred 3 pixels above the bottom.
    fn draw_info_boxes(
        &self,
        assets: &TitleAssets,
        sprites: &WidgetVisualAssets,
        frame: &mut UiFrame,
        canvas: Rect,
    ) {
        const SIZE: f32 = 35.;
        const GAP: f32 = 1.;
        const WRAP: usize = 4;
        // `ComponentConstants.STANDARD_BACKGROUND_COLOR` and `BackgroundComponent`'s strokes.
        const BACKGROUND: [u8; 4] = [70, 61, 50, 156];
        const OUTER: [u8; 4] = [56, 48, 40, 218];
        const INNER: [u8; 4] = [84, 73, 60, 218];
        let Some(viewport) = frame.world_viewport else {
            return;
        };
        let (left, top) = (viewport.x + 5., viewport.y + 20.);
        for (slot, (_, info)) in self.output.info_boxes.iter().enumerate() {
            #[expect(clippy::cast_precision_loss, reason = "at most a few dozen boxes")]
            let (column, row) = ((slot % WRAP) as f32, (slot / WRAP) as f32);
            let (x, y) = (left + column * (SIZE + GAP), top + row * (SIZE + GAP));
            frame.fill_rect(Rect::new(x, y, SIZE, SIZE), BACKGROUND, canvas);
            for (inset, stroke) in [(0., OUTER), (1., INNER)] {
                let (sx, sy, side) = (x + inset, y + inset, SIZE - 2. * inset);
                for edge in [
                    Rect::new(sx, sy, side, 1.),
                    Rect::new(sx, sy + side - 1., side, 1.),
                    Rect::new(sx, sy, 1., side),
                    Rect::new(sx + side - 1., sy, 1., side),
                ] {
                    frame.fill_rect(edge, stroke, canvas);
                }
            }
            if let Some(id) = info.sprite.map(UiImageId::Sprite)
                && let Some(image) = sprites.images.get(&id)
            {
                #[expect(clippy::cast_precision_loss, reason = "small sprite extents")]
                let (width, height) = (image.width() as f32, image.height() as f32);
                frame.image(
                    id,
                    Rect::new(
                        x + ((SIZE - width) / 2.).floor(),
                        y + ((SIZE - height) / 2.).floor(),
                        width,
                        height,
                    ),
                    canvas,
                );
            }
            if !info.text.is_empty() {
                let text_x = x + ((SIZE - text_width(assets, &info.text)) / 2.).floor();
                let baseline = y + SIZE - 3.;
                draw_text(
                    frame,
                    assets,
                    &info.text,
                    text_x + 1.,
                    baseline + 1.,
                    [0, 0, 0, 255],
                    canvas,
                );
                draw_text(
                    frame,
                    assets,
                    &info.text,
                    text_x,
                    baseline,
                    color(info.argb),
                    canvas,
                );
            }
        }
    }
}

/// The left edge of something `width` wide whose `align` edge is at `x`; integer halves as in the source.
fn aligned(x: f32, width: f32, align: HorizontalAlign) -> f32 {
    match align {
        HorizontalAlign::Left => x,
        HorizontalAlign::Center => x - (width / 2.).floor(),
        HorizontalAlign::Right => x - width,
    }
}

/// Canvas pixels fit `i16`; clamping keeps hostile coordinates harmless and the cast lossless.
fn px(value: i32) -> f32 {
    f32::from(i16::try_from(value.clamp(-32768, 32767)).unwrap_or(0))
}

pub(super) fn color(argb: u32) -> [u8; 4] {
    let [alpha, red, green, blue] = argb.to_be_bytes();
    [red, green, blue, alpha]
}

fn draw_rect(
    frame: &mut UiFrame,
    clip: Rect,
    [x, y, width, height]: [i32; 4],
    argb: u32,
    filled: bool,
) {
    let (x, y, w, h) = (px(x), px(y), px(width), px(height));
    if filled {
        frame.fill_rect(Rect::new(x, y, w, h), color(argb), clip);
        return;
    }
    for edge in [
        Rect::new(x, y, w, 1.),
        Rect::new(x, y + h - 1., w, 1.),
        Rect::new(x, y, 1., h),
        Rect::new(x + w - 1., y, 1., h),
    ] {
        frame.fill_rect(edge, color(argb), clip);
    }
}

/// One-pixel Bresenham line, bounded so a hostile plugin cannot stall the frame.
pub(super) fn draw_line(
    frame: &mut UiFrame,
    clip: Rect,
    from: (i32, i32),
    to: (i32, i32),
    argb: u32,
) {
    let (mut x, mut y) = from;
    let (dx, dy) = ((to.0 - x).abs(), -(to.1 - y).abs());
    let (sx, sy) = (if x < to.0 { 1 } else { -1 }, if y < to.1 { 1 } else { -1 });
    let mut error = dx + dy;
    for _ in 0..4096 {
        frame.fill_rect(Rect::new(px(x), px(y), 1., 1.), color(argb), clip);
        if (x, y) == to {
            break;
        }
        let doubled = 2 * error;
        if doubled >= dy {
            error += dy;
            x += sx;
        }
        if doubled <= dx {
            error += dx;
            y += sy;
        }
    }
}

/// Read-only projection of committed session state handed to plugins.
struct SessionView {
    position: Option<PlayerPosition>,
    tick: u64,
}

impl SessionView {
    fn from_state(state: &GameState, tick: u64) -> Self {
        Self {
            position: state
                .entities()
                .local_player()
                .map(|player| PlayerPosition {
                    x: player.tile.x,
                    y: player.tile.y,
                    plane: player.tile.plane.get(),
                }),
            tick,
        }
    }
}

impl HostView for SessionView {
    fn player_position(&self) -> Option<PlayerPosition> {
        self.position
    }

    fn tick(&self) -> u64 {
        self.tick
    }
}
