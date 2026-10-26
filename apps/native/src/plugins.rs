//! Native composition of wasm component plugins.
//!
//! The client hands plugins only a read-only session view and mediated storage; committed game
//! state, renderer resources and transport never cross the component boundary.

use rustclient_cache::TitleAssets;
use rustclient_game_state::GameState;
use rustclient_game_ui::{Rect, UiFrame, draw_text};
use rustclient_plugin_api::{Notification, OverlayCommand, PlayerPosition, PluginEvent};
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
    projector: Projector,
    output: HostOutput,
    unsupported_reported: bool,
    /// Last configuration pushed to each first-party plugin, so unchanged ones are not re-sent.
    applied: BTreeMap<String, (PluginConfig, bool)>,
}

/// Shell-owned facts and cache definitions the projector consults.
pub(super) struct Inputs<'a> {
    pub frame: Frame,
    pub sources: Sources<'a>,
}

impl Plugins {
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
                projector: Projector::new(),
                output: HostOutput::default(),
                unsupported_reported: false,
                applied: BTreeMap::new(),
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
            projector: Projector::new(),
            output: HostOutput::default(),
            unsupported_reported: false,
            applied: BTreeMap::new(),
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
            // Not every catalogued plugin is ported yet; those have no instance to configure.
            let registered = self
                .set
                .iter()
                .any(|plugin| plugin.manifest.id == schema.plugin);
            if registered
                && let Err(fault) = self.set.configure(
                    &schema.plugin,
                    config.clone(),
                    enabled,
                    &StaticView::default(),
                    &mut self.storage,
                    &mut self.logs,
                )
            {
                eprintln!("plugin {key} configuration: {fault}");
                continue;
            }
            self.applied.insert(key.to_owned(), (config, enabled));
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
    /// Nothing is projected unless an enabled plugin holds a grant, so an idle plugin set costs
    /// only the capability union.
    pub(super) fn update(&mut self, state: &GameState, inputs: &Inputs<'_>) {
        let wanted = self.set.granted();
        if wanted.is_empty() {
            self.output = HostOutput::default();
            return;
        }
        let (snapshot, events) =
            self.projector
                .project(state, &inputs.frame, &inputs.sources, &wanted);
        let view = SnapshotView(&snapshot);
        self.set
            .dispatch_game(&events, &view, &mut self.storage, &mut self.logs);
        self.output = self
            .set
            .collect_output(&view, &mut self.storage, &mut self.logs);
    }

    /// Dispatch `Stopped` once when the session ends.
    pub(super) fn stop(&mut self, state: &GameState) {
        if !self.started {
            return;
        }
        self.started = false;
        self.projector.reset();
        self.output = HostOutput::default();
        let view = SessionView::from_state(state, self.tick);
        self.set.dispatch(
            &[PluginEvent::Stopped],
            &view,
            &mut self.storage,
            &mut self.logs,
        );
    }

    /// Notifications raised by the last update, each reported once.
    pub(super) fn take_notifications(&mut self) -> Vec<Notification> {
        std::mem::take(&mut self.output.notifications)
            .into_iter()
            .map(|(_, notification)| notification)
            .collect()
    }

    /// The merged declarative output of the last update.
    pub(super) fn output(&self) -> &HostOutput {
        &self.output
    }

    /// Draw plugin overlay lines topmost in the `765x503` interface space.
    pub(super) fn overlay(&mut self, state: &GameState, assets: &TitleAssets, frame: &mut UiFrame) {
        if self.set.is_empty() {
            return;
        }
        self.draw_output(assets, frame);
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
    fn draw_output(&mut self, assets: &TitleAssets, frame: &mut UiFrame) {
        let clip = Rect::new(0., 0., 765., 503.);
        let canvas = frame.canvas.map_or(clip, |size| {
            Rect::new(
                0.,
                0.,
                px(i32::try_from(size.width).unwrap_or(i32::MAX)),
                px(i32::try_from(size.height).unwrap_or(i32::MAX)),
            )
        });
        let mut skipped = false;
        for item in &self.output.overlay {
            match &item.command {
                OverlayCommand::Text { text, x, y, argb } => {
                    let [alpha, red, green, blue] = argb.to_be_bytes();
                    draw_text(
                        frame,
                        assets,
                        text,
                        px(*x),
                        px(*y),
                        [red, green, blue, alpha],
                        canvas,
                    );
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
                _ => skipped = true,
            }
        }
        if skipped && !self.unsupported_reported {
            self.unsupported_reported = true;
            eprintln!("plugin overlay: world-anchored primitives are not drawn by this client yet");
        }
    }
}

/// Canvas pixels fit `i16`; clamping keeps hostile coordinates harmless and the cast lossless.
fn px(value: i32) -> f32 {
    f32::from(i16::try_from(value.clamp(-32768, 32767)).unwrap_or(0))
}

fn color(argb: u32) -> [u8; 4] {
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
fn draw_line(frame: &mut UiFrame, clip: Rect, from: (i32, i32), to: (i32, i32), argb: u32) {
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
