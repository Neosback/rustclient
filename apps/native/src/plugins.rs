//! Native composition of wasm component plugins.
//!
//! The client hands plugins only a read-only session view and mediated storage; committed game
//! state, renderer resources and transport never cross the component boundary.

use rustclient_cache::TitleAssets;
use rustclient_game_state::GameState;
use rustclient_game_ui::{Rect, UiFrame, draw_text};
use rustclient_plugin_api::{PlayerPosition, PluginEvent};
use rustclient_plugin_host::{DirectoryStorage, HostView, PluginSet, StderrLogSink};
use rustclient_plugin_runtime_native::NativeRuntime;
use std::path::Path;

pub(super) struct Plugins {
    set: PluginSet,
    storage: DirectoryStorage,
    logs: StderrLogSink,
    started: bool,
    tick: u64,
}

impl Plugins {
    /// Load every package directory under `packages`, skipping failures.
    ///
    /// Plugin-local persisted data is rooted at `storage_root`; the package tree itself is
    /// treated as read-only input.
    pub(super) fn load(packages: &Path, storage_root: &Path) -> Self {
        let storage = DirectoryStorage::new(storage_root.to_path_buf());
        let logs = StderrLogSink;
        let mut set = PluginSet::new();
        if !packages.is_dir() {
            return Self {
                set,
                storage,
                logs,
                started: false,
                tick: 0,
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
        Self {
            set,
            storage,
            logs,
            started: false,
            tick: 0,
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

    /// Dispatch `Stopped` once when the session ends.
    pub(super) fn stop(&mut self, state: &GameState) {
        if !self.started {
            return;
        }
        self.started = false;
        let view = SessionView::from_state(state, self.tick);
        self.set.dispatch(
            &[PluginEvent::Stopped],
            &view,
            &mut self.storage,
            &mut self.logs,
        );
    }

    /// Draw plugin overlay lines topmost in the `765x503` interface space.
    pub(super) fn overlay(&mut self, state: &GameState, assets: &TitleAssets, frame: &mut UiFrame) {
        if self.set.is_empty() {
            return;
        }
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
