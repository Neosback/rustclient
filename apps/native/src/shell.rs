//! Composition of application preferences, retained DOM pixels and engine-owned effects.
use rustclient_application::{
    builtin_config::BuiltinConfigurations, shell_preferences::ShellPreferences,
};
mod layout;
mod settings;
use layout::CanvasLayout;
use rustclient_platform::BlobStorage;
use rustclient_plugin_host::configuration::ConfigValue;
use rustclient_renderer::{EguiShellLayer, SurfaceRenderer, SurfaceSize};
use rustclient_shell_ui::{EguiPanel, PluginPanel, ShellAction, ShellChannel, SidebarState};
use settings::{apply_effect, availability, gpu_settings, notes, static_note};
use std::collections::BTreeMap;
use std::time::Instant;
use winit::{
    dpi::{LogicalSize, PhysicalSize},
    window::Window,
};

struct SidebarArrow {
    width: u32,
    height: u32,
    pixels: Vec<[u8; 4]>,
}
impl SidebarArrow {
    fn decode() -> Result<Self, String> {
        let image = image::load_from_memory(rustclient_shell_ui::OPEN_RS_PNG)
            .map_err(|error| format!("sidebar arrow asset: {error}"))?
            .into_rgba8();
        let (width, height) = image.dimensions();
        let pixels = image.as_raw().as_chunks::<4>().0.to_vec();
        Ok(Self {
            width,
            height,
            pixels,
        })
    }
}

#[allow(clippy::struct_excessive_bools)]
pub(super) struct Shell {
    pub panel: EguiPanel,
    ui_clock: Instant,
    channel: ShellChannel,
    configuration: BuiltinConfigurations,
    pub focused: bool,
    pub held: bool,
    pub composing: bool,
    restored: SidebarState,
    layout: CanvasLayout,
    preferences: ShellPreferences,
    arrow: SidebarArrow,
    /// Why an applied GPU option differs from the request on this adapter.
    gpu_notes: BTreeMap<String, String>,
}
impl Shell {
    pub fn new(storage: Option<&dyn BlobStorage>) -> Result<Self, String> {
        let (configuration, mut diagnostics) = BuiltinConfigurations::load_recovering(storage)?;
        for diagnostic in &diagnostics {
            eprintln!("required plugin configuration: {diagnostic}");
        }
        let preferences = storage
            .map(ShellPreferences::load)
            .transpose()
            .unwrap_or_else(|error| {
                diagnostics.push(error);
                None
            })
            .unwrap_or_default();
        let channel = ShellChannel::default();
        channel.model.borrow_mut().sidebar = SidebarState::Hidden;
        channel.model.borrow_mut().pinned =
            preferences.pinned.iter().map(ToString::to_string).collect();
        channel.model.borrow_mut().error =
            (!diagnostics.is_empty()).then(|| diagnostics.join("\n"));
        let mut panel = EguiPanel::new(channel.clone(), [36, 503], 1.)?;
        match rustclient_platform_native::NativeClipboard::new() {
            Ok(clipboard) => panel.set_clipboard(Box::new(clipboard)),
            Err(error) => eprintln!("shell clipboard unavailable: {error}"),
        }
        panel.set_visible(false);
        let arrow = SidebarArrow::decode()?;
        let shell = Self {
            panel,
            ui_clock: Instant::now(),
            channel,
            configuration,
            focused: false,
            held: false,
            composing: false,
            restored: SidebarState::Panel,
            layout: CanvasLayout::default(),
            preferences,
            arrow,
            gpu_notes: BTreeMap::new(),
        };
        shell.project();
        Ok(shell)
    }
    fn keep_game_size(&self) -> bool {
        let config = self
            .configuration
            .get("dev.rustclient.runelite")
            .expect("embedded RuneLite schema");
        matches!(
            config.value("automaticResizeType"),
            Some(ConfigValue::Choice(value)) if value == "KEEP_GAME_SIZE"
        )
    }
    /// Every accepted plugin configuration, required services first.
    pub fn configurations(
        &self,
    ) -> impl Iterator<Item = &rustclient_plugin_host::configuration::Configuration> {
        self.configuration.iter()
    }
    fn sidebar(&self) -> SidebarState {
        self.channel.model.borrow().sidebar
    }
    pub fn width(&self) -> u32 {
        self.sidebar().width()
    }
    fn preserve_game(&self, window: &Window) -> bool {
        self.keep_game_size() && window.fullscreen().is_none() && !window.is_maximized()
    }
    pub fn on_window_resized(&mut self, window: &Window) {
        self.layout.observe(
            window.inner_size().width,
            window.scale_factor(),
            self.width(),
            Instant::now(),
        );
        self.trace_layout(window, "observed");
    }
    pub fn settle(&mut self, window: &Window) {
        if self.layout.pending() {
            self.on_window_resized(window);
        }
    }
    pub fn game_size(&self, window: &Window) -> PhysicalSize<u32> {
        let size = window.inner_size();
        PhysicalSize::new(
            self.layout.game_width(
                size.width,
                window.scale_factor(),
                self.width(),
                self.preserve_game(window),
            ),
            size.height,
        )
    }
    fn trace_layout(&self, window: &Window, event: &str) {
        if std::env::var_os("RUSTCLIENT_FRAME_TIMINGS").is_some() {
            eprintln!(
                "sidebar {event}: state={:?} surface={:?} game={:?} scale={} pending={}",
                self.sidebar(),
                window.inner_size(),
                self.game_size(window),
                window.scale_factor(),
                self.layout.pending()
            );
        }
    }
    /// Logical bounds of the `open_rs.png` sidebar toggle button on the game canvas (`ClientUI.paintOverlays`).
    #[expect(
        clippy::cast_precision_loss,
        reason = "canvas and widget dimensions fit f32"
    )]
    pub fn overlay_button_rect(
        &self,
        canvas_width: u32,
        widget_tree: &rustclient_game_ui::widgets::WidgetTree,
    ) -> rustclient_game_ui::Rect {
        let x = canvas_width.saturating_sub(self.arrow.width + 5) as f32;
        let stone10 = rustclient_model::WidgetAddress::component(rustclient_model::WidgetId::new(
            rustclient_model::InterfaceId::new(161),
            36,
        ));
        let y = widget_tree
            .nodes
            .iter()
            .find(|node| {
                node.address == stone10
                    && node.bounds.width > 0
                    && node.bounds.height > 0
                    && node.clip.width > 0
                    && node.clip.height > 0
            })
            .map_or(5., |node| (node.bounds.y + node.bounds.height) as f32);
        rustclient_game_ui::Rect::new(x, y, self.arrow.width as f32, self.arrow.height as f32)
    }
    /// Paint the `open_rs.png` (or horizontally flipped `open_rs.png` when the sidebar is visible)
    /// overlay on the top-right of the game canvas.
    #[expect(clippy::cast_precision_loss, reason = "small icon dimensions fit f32")]
    pub fn project_overlay(
        &self,
        frame: &mut rustclient_game_ui::UiFrame,
        widget_tree: &rustclient_game_ui::widgets::WidgetTree,
        pointer: Option<(f32, f32)>,
    ) {
        let canvas_width = frame.canvas.map_or(765, |canvas| canvas.width);
        let rect = self.overlay_button_rect(canvas_width, widget_tree);
        let in_range = self.sidebar() == SidebarState::Hidden
            || pointer.is_some_and(|(px, py)| {
                px >= rect.x - 15. && px <= canvas_width as f32 + 36. && py >= 0.
            });
        if !in_range {
            return;
        }
        let clip = rustclient_game_ui::Rect::new(0., 0., 16_384., 16_384.);
        let flip_h = self.sidebar() != SidebarState::Hidden;
        for py in 0..self.arrow.height {
            let mut px = 0;
            while px < self.arrow.width {
                let src_x = if flip_h {
                    self.arrow.width - 1 - px
                } else {
                    px
                };
                let color = self.arrow.pixels[(py * self.arrow.width + src_x) as usize];
                if color[3] == 0 {
                    px += 1;
                    continue;
                }
                let start = px;
                px += 1;
                while px < self.arrow.width {
                    let next_x = if flip_h {
                        self.arrow.width - 1 - px
                    } else {
                        px
                    };
                    if self.arrow.pixels[(py * self.arrow.width + next_x) as usize] != color {
                        break;
                    }
                    px += 1;
                }
                frame.fill_rect(
                    rustclient_game_ui::Rect::new(
                        rect.x + start as f32,
                        rect.y + py as f32,
                        (px - start) as f32,
                        1.,
                    ),
                    color,
                    clip,
                );
            }
        }
    }
    /// Physical window coordinates use the exact same split as surface composition and game picking.
    #[expect(
        clippy::cast_possible_truncation,
        reason = "physical coordinates converted to bounded logical DOM coordinates"
    )]
    pub fn point(&self, window: &Window, x: f64, y: f64) -> Option<[f32; 2]> {
        if self.sidebar() == SidebarState::Hidden {
            return None;
        }
        let origin = f64::from(self.game_size(window).width);
        (x >= origin
            && x < f64::from(window.inner_size().width)
            && y >= 0.
            && y < f64::from(window.inner_size().height))
        .then_some([
            ((x - origin) / window.scale_factor()) as f32,
            (y / window.scale_factor()) as f32,
        ])
    }
    /// Process input before accepted actions are drained; world frames do not invalidate the UI.
    #[expect(
        clippy::cast_possible_truncation,
        clippy::cast_sign_loss,
        reason = "validated bounded panel dimensions and native DPI"
    )]
    pub fn update_panel(&mut self, window: &Window) -> Result<(), String> {
        self.panel
            .set_visible(self.sidebar() != SidebarState::Hidden);
        if self.sidebar() == SidebarState::Hidden {
            return Ok(());
        }
        let scale = window.scale_factor();
        let size = [
            (f64::from(self.width()) * scale).round() as u32,
            window.inner_size().height,
        ];
        if size.contains(&0) {
            return Ok(());
        }
        self.panel.resize(size, scale as f32)?;
        self.panel.render(self.ui_clock.elapsed().as_secs_f64())?;
        if let Some([x, y, width, height]) = self.panel.ime_cursor() {
            window.set_ime_cursor_area(
                winit::dpi::LogicalPosition::new(
                    f64::from(x) + f64::from(self.game_size(window).width) / scale,
                    f64::from(y),
                ),
                winit::dpi::LogicalSize::new(f64::from(width.max(1.)), f64::from(height.max(1.))),
            );
        }
        Ok(())
    }
    #[expect(
        clippy::cast_possible_truncation,
        clippy::cast_sign_loss,
        reason = "validated bounded shell extent and platform scale"
    )]
    pub fn stage(&mut self, window: &Window, renderer: &mut SurfaceRenderer) -> Result<(), String> {
        // A requested size the platform never delivered (clamped to the screen) expires here.
        let game = self.game_size(window);
        renderer.set_game_width(Some(game.width));
        self.panel
            .set_visible(self.sidebar() != SidebarState::Hidden);
        if self.sidebar() == SidebarState::Hidden {
            return renderer
                .set_shell_layer(None)
                .map_err(|error| error.to_string());
        }
        let scale = window.scale_factor();
        let sidebar = (f64::from(self.width()) * scale).round() as u32;
        let size = [sidebar.max(1), window.inner_size().height];
        if size[1] == 0 {
            return Ok(());
        }
        self.panel.resize(size, scale as f32)?;
        // While opening, do not scale a partially available panel texture into the surface.
        if game.width.saturating_add(sidebar) > window.inner_size().width {
            return renderer
                .set_shell_layer(None)
                .map_err(|error| error.to_string());
        }
        self.panel.render(self.ui_clock.elapsed().as_secs_f64())?;
        let frame = self.panel.frame();
        renderer
            .set_egui_shell_layer(EguiShellLayer {
                jobs: &frame.jobs,
                textures: &frame.textures,
                scale: scale as f32,
                size: SurfaceSize::new(size[0], size[1]),
                generation: frame.generation,
                origin: [game.width, 0],
            })
            .map_err(|error| error.to_string())
    }
    pub fn apply_current(&mut self, window: &Window, renderer: Option<&mut SurfaceRenderer>) {
        for config in self.configuration.iter() {
            apply_effect(config.schema(), config.document(), window);
        }
        if let Some(renderer) = renderer {
            let config = self
                .configuration
                .get("dev.rustclient.gpu")
                .expect("embedded GPU schema");
            self.gpu_notes =
                notes(renderer.set_gpu_settings(gpu_settings(config.schema(), config.document())));
            self.project();
        }
    }
    #[allow(clippy::too_many_lines)]
    pub fn actions(
        &mut self,
        window: &Window,
        renderer: Option<&mut SurfaceRenderer>,
        storage: Option<&dyn BlobStorage>,
    ) -> bool {
        let mut changed = false;
        let mut renderer = renderer;
        for action in self.channel.drain() {
            let mut new_notes = None;
            let result =
                match action {
                    ShellAction::ToggleSidebar => {
                        self.resize(
                            window,
                            if self.sidebar() == SidebarState::Hidden {
                                self.restored
                            } else {
                                SidebarState::Hidden
                            },
                        );
                        Ok(())
                    }
                    ShellAction::TogglePanel => {
                        self.resize(
                            window,
                            if self.sidebar() == SidebarState::Panel {
                                SidebarState::Rail
                            } else {
                                SidebarState::Panel
                            },
                        );
                        Ok(())
                    }
                    ShellAction::Open => {
                        self.resize(window, SidebarState::Panel);
                        Ok(())
                    }
                    ShellAction::Close => {
                        self.resize(window, SidebarState::Rail);
                        Ok(())
                    }
                    ShellAction::ToggleFavorite { plugin } => {
                        if !self
                            .channel
                            .model
                            .borrow()
                            .plugins
                            .iter()
                            .any(|entry| entry.id == plugin)
                        {
                            Err(format!("Unknown plugin {plugin}"))
                        } else if let Some(storage) = storage {
                            self.preferences.toggle(&plugin, storage).map(|()| {
                                self.channel.model.borrow_mut().pinned = self
                                    .preferences
                                    .pinned
                                    .iter()
                                    .map(ToString::to_string)
                                    .collect();
                            })
                        } else {
                            Err("Settings storage is unavailable".into())
                        }
                    }
                    ShellAction::Configure {
                        plugin,
                        key,
                        value: setting,
                    } => {
                        if let Some(reason) = availability(&plugin, &key) {
                            Err(reason)
                        } else if let Some(storage) = storage {
                            self.configuration
                                .change(
                                    &plugin,
                                    &key,
                                    setting,
                                    storage,
                                    |schema, document| Ok((schema.clone(), document.clone())),
                                    |(schema, document)| {
                                        apply_effect(&schema, &document, window);
                                        if schema.plugin.as_str() == "dev.rustclient.gpu"
                                            && let Some(renderer) = renderer.as_deref_mut()
                                        {
                                            new_notes = Some(notes(renderer.set_gpu_settings(
                                                gpu_settings(&schema, &document),
                                            )));
                                        }
                                    },
                                )
                                .map(|publication| {
                                    changed |= publication.is_some();
                                })
                        } else {
                            Err(
                            "Settings storage is unavailable; last working configuration retained"
                                .into(),
                        )
                        }
                    }
                    ShellAction::Reset { plugin } => {
                        if let Some(storage) = storage {
                            self.configuration
                                .reset(
                                    &plugin,
                                    storage,
                                    |schema, document| Ok((schema.clone(), document.clone())),
                                    |(schema, document)| {
                                        apply_effect(&schema, &document, window);
                                        if schema.plugin.as_str() == "dev.rustclient.gpu"
                                            && let Some(renderer) = renderer.as_deref_mut()
                                        {
                                            new_notes = Some(notes(renderer.set_gpu_settings(
                                                gpu_settings(&schema, &document),
                                            )));
                                        }
                                    },
                                )
                                .map(|publication| {
                                    changed |= publication.is_some();
                                })
                        } else {
                            Err("Settings storage is unavailable".into())
                        }
                    }
                };
            if let Some(new_notes) = new_notes {
                self.gpu_notes = new_notes;
            }
            self.channel.model.borrow_mut().error = result.err();
            if changed {
                self.project();
            }
            self.panel.invalidate();
            window.request_redraw();
        }
        changed
    }
    fn project(&self) {
        self.channel.model.borrow_mut().plugins = self
            .configuration
            .iter()
            .map(|config| {
                let plugin = config.schema().plugin.as_str();
                PluginPanel::project(config, |key| availability(plugin, key)).with_notes(|key| {
                    if plugin == "dev.rustclient.gpu" {
                        self.gpu_notes
                            .get(key)
                            .cloned()
                            .or_else(|| static_note(key))
                    } else {
                        None
                    }
                })
            })
            .collect();
    }
    pub fn toggle_sidebar(&mut self, window: &Window) {
        self.channel.send(ShellAction::ToggleSidebar);
        window.request_redraw();
    }
    pub fn toggle_panel(&mut self, window: &Window) {
        self.channel.send(ShellAction::TogglePanel);
        window.request_redraw();
    }
    pub fn hotkey(&self, key: &str, modifiers: u8) -> Option<bool> {
        let config = self.configuration.get("dev.rustclient.runelite")?;
        for (setting, panel) in [("sidebarToggleKey", false), ("panelToggleKey", true)] {
            if let Some(ConfigValue::Hotkey(binding)) = config.value(setting)
                && binding.key == key
                && binding.modifiers == modifiers
            {
                return Some(panel);
            }
        }
        None
    }
    pub fn block_extra_mouse(&self) -> bool {
        self.configuration
            .get("dev.rustclient.runelite")
            .and_then(|config| config.value("blockExtraMouseButtons"))
            == Some(&ConfigValue::Boolean(true))
    }
    fn resize(&mut self, window: &Window, sidebar: SidebarState) {
        let previous = self.sidebar();
        if previous == sidebar {
            return;
        }
        let scale = window.scale_factor();
        // Capture a genuine user size before changing how much chrome is reserved. In-flight
        // shell requests retain their original canvas target through subsequent toggles.
        self.layout.observe(
            window.inner_size().width,
            scale,
            previous.width(),
            Instant::now(),
        );
        if previous != SidebarState::Hidden {
            self.restored = previous;
        }
        self.channel.model.borrow_mut().sidebar = sidebar;
        self.focused = sidebar == SidebarState::Panel;
        self.held = false;
        self.composing = false;
        self.panel.clear_focus();
        if self.focused {
            window.set_ime_allowed(true);
        }
        self.panel.set_visible(sidebar != SidebarState::Hidden);
        self.panel.invalidate();
        let chrome = self.width();
        if self.preserve_game(window) {
            let target =
                self.layout
                    .target(window.inner_size().width, scale, chrome, Instant::now());
            // Lower the minimum before requesting a close; raise it only after requesting an
            // open. Raising the minimum first starts a separate OS-driven resize animation.
            if chrome < previous.width() {
                window.set_min_inner_size(Some(LogicalSize::new(765. + f64::from(chrome), 503.)));
            }
            self.trace_layout(window, &format!("request width={target}"));
            let immediate =
                window.request_inner_size(PhysicalSize::new(target, window.inner_size().height));
            if let Some(size) = immediate {
                self.layout
                    .observe(size.width, scale, chrome, Instant::now());
            }
            window.set_min_inner_size(Some(LogicalSize::new(765. + f64::from(chrome), 503.)));
        } else {
            self.layout.cancel();
            window.set_min_inner_size(Some(LogicalSize::new(765., 503.)));
            self.on_window_resized(window);
        }
        window.request_redraw();
    }
}
