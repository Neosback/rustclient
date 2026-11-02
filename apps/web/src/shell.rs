//! Browser platform adapter for the same panel/configuration transaction used by native.
use rustclient_application::{
    builtin_config::{BuiltinConfigurations, availability, value},
    shell_preferences::ShellPreferences,
};
use rustclient_platform_web::LocalStorage;
use rustclient_plugin_host::{
    MemoryLogSink, MemoryStorage, PluginSet, StaticView,
    configuration::{ConfigDocument, ConfigSchema, ConfigValue},
    required::Builtin,
};
use rustclient_renderer::{EguiShellLayer, GpuPreference, GpuSettings, SurfaceRenderer};
use rustclient_shell_ui::{
    EguiPanel, PluginPanel, ShellAction, ShellChannel, SidebarMetrics, SidebarState,
};
use std::collections::BTreeMap;
use winit::{
    event::{ElementState, Ime, MouseButton, MouseScrollDelta, WindowEvent},
    keyboard::{Key, ModifiersState, NamedKey},
    window::Window,
};

pub struct Shell {
    pub panel: EguiPanel,
    channel: ShellChannel,
    config: BuiltinConfigurations,
    storage: Option<LocalStorage>,
    favorites: ShellPreferences,
    notes: BTreeMap<String, String>,
    pointer: Option<(f64, f64)>,
    held: bool,
    composing: bool,
    focused: bool,
    modifiers: ModifiersState,
}
fn gpu(schema: &ConfigSchema, document: &ConfigDocument) -> GpuSettings {
    GpuSettings::from_preferences(|key| match value(schema, document, key) {
        Some(ConfigValue::Boolean(value)) => Some(GpuPreference::Boolean(*value)),
        Some(ConfigValue::Integer(value)) => Some(GpuPreference::Integer(*value)),
        Some(ConfigValue::Choice(value)) => Some(GpuPreference::Choice(value)),
        _ => None,
    })
}
impl Shell {
    pub fn new() -> Result<Self, String> {
        let storage = LocalStorage::new().ok();
        let (config, mut diagnostics) = BuiltinConfigurations::load_recovering(
            storage
                .as_ref()
                .map(|s| s as &dyn rustclient_platform::BlobStorage),
        )?;
        let favorites = storage
            .as_ref()
            .map(|storage| ShellPreferences::load(storage))
            .transpose()
            .unwrap_or_else(|error| {
                diagnostics.push(error);
                None
            })
            .unwrap_or_default();
        let channel = ShellChannel::default();
        let harness = web_sys::window()
            .and_then(|w| w.location().search().ok())
            .is_some_and(|s| {
                s.split('&')
                    .any(|s| s.trim_start_matches('?') == "shell-harness")
            });
        channel.model.borrow_mut().sidebar = if harness {
            SidebarState::Panel
        } else {
            SidebarState::Hidden
        };
        channel.model.borrow_mut().pinned =
            favorites.pinned.iter().map(ToString::to_string).collect();
        if !diagnostics.is_empty() {
            channel.model.borrow_mut().error = Some(diagnostics.join("\n"));
        }
        let mut services = PluginSet::new();
        for service in Builtin::ALL {
            services
                .register_required(service)
                .map_err(|e| e.to_string())?;
        }
        services.init(
            &StaticView::default(),
            &mut MemoryStorage::new(),
            &mut MemoryLogSink::default(),
        );
        channel.model.borrow_mut().runtime = services
            .iter()
            .map(|p| (p.manifest.id.to_string(), (p.status(), None)))
            .collect();
        let panel = EguiPanel::new(
            channel.clone(),
            [rustclient_shell_ui::SidebarMetrics::OPEN, 503],
            1.,
        )?;
        let shell = Self {
            panel,
            channel,
            config,
            storage,
            favorites,
            notes: BTreeMap::new(),
            pointer: None,
            held: false,
            composing: false,
            focused: false,
            modifiers: ModifiersState::empty(),
        };
        shell.project();
        Ok(shell)
    }
    fn project(&self) {
        self.channel.model.borrow_mut().plugins = self
            .config
            .iter()
            .map(|config| {
                let id = config.schema().plugin.as_str();
                PluginPanel::project(config, |key| {
                    if id == "dev.rustclient.gpu" {
                        availability(id, key)
                    } else {
                        Some(
                            "Browser shell harness: game/plugin/window effects are not connected"
                                .into(),
                        )
                    }
                })
                .with_notes(|key| {
                    self.notes.get(key).cloned().or_else(|| {
                        Some(
                            "Shell harness: world rendering/gameplay acceptance remains separate"
                                .into(),
                        )
                    })
                })
            })
            .collect();
    }
    pub fn apply_gpu(&mut self, renderer: &mut SurfaceRenderer) {
        if let Some(config) = self.config.get("dev.rustclient.gpu") {
            self.notes = renderer
                .set_gpu_settings(gpu(config.schema(), config.document()))
                .deviations
                .iter()
                .map(|d| (d.key.to_owned(), d.reason.clone()))
                .collect();
        }
        self.project();
        self.panel.invalidate();
    }
    pub fn width(&self) -> u32 {
        self.channel.model.borrow().sidebar.width()
    }
    pub fn action(&self, action: ShellAction) {
        self.channel.send(action);
    }
    fn prepare_action(&mut self, action: &ShellAction, time: f64) -> Result<(), String> {
        if matches!(
            action,
            ShellAction::Close | ShellAction::TogglePanel | ShellAction::ToggleSidebar
        ) {
            self.panel.finish_editing(time)?;
        }
        Ok(())
    }
    pub fn update(
        &mut self,
        window: &Window,
        renderer: &mut SurfaceRenderer,
        time: f64,
    ) -> Result<(), String> {
        let mut gpu_changed = false;
        for action in self.channel.drain() {
            self.prepare_action(&action, time)?;
            let result = match action {
                ShellAction::PluginUi(_) => {
                    Err("Plugin execution is not connected in this browser shell harness".into())
                }
                ShellAction::ToggleSidebar => {
                    let mut model = self.channel.model.borrow_mut();
                    model.sidebar = if model.sidebar == SidebarState::Hidden {
                        SidebarState::Panel
                    } else {
                        SidebarState::Hidden
                    };
                    Ok(())
                }
                ShellAction::Open => {
                    self.channel.model.borrow_mut().sidebar = SidebarState::Panel;
                    Ok(())
                }
                ShellAction::Close => {
                    self.channel.model.borrow_mut().sidebar = SidebarState::Rail;
                    Ok(())
                }
                ShellAction::TogglePanel => {
                    let mut model = self.channel.model.borrow_mut();
                    model.sidebar = if model.sidebar == SidebarState::Panel {
                        SidebarState::Rail
                    } else {
                        SidebarState::Panel
                    };
                    Ok(())
                }
                ShellAction::ToggleFavorite { plugin } => self
                    .storage
                    .as_ref()
                    .ok_or("browser storage unavailable".into())
                    .and_then(|storage| self.favorites.toggle(&plugin, storage)),
                ShellAction::Configure { plugin, key, value } => self
                    .storage
                    .as_ref()
                    .ok_or("browser storage unavailable".into())
                    .and_then(|storage| {
                        self.config
                            .change(&plugin, &key, value, storage, |_, _| Ok(()), |()| {})
                            .map(|event| {
                                gpu_changed |= event.is_some() && plugin == "dev.rustclient.gpu";
                            })
                    }),
                ShellAction::Reset { plugin } => self
                    .storage
                    .as_ref()
                    .ok_or("browser storage unavailable".into())
                    .and_then(|storage| {
                        self.config
                            .reset(&plugin, storage, |_, _| Ok(()), |()| {})
                            .map(|event| {
                                gpu_changed |= event.is_some() && plugin == "dev.rustclient.gpu";
                            })
                    }),
            };
            self.channel.model.borrow_mut().error = result.err();
            self.channel.model.borrow_mut().pinned = self
                .favorites
                .pinned
                .iter()
                .map(ToString::to_string)
                .collect();
            self.project();
            self.panel.invalidate();
        }
        if gpu_changed {
            self.apply_gpu(renderer);
        }
        let size = window.inner_size();
        let scale = window.scale_factor();
        let chrome = SidebarMetrics::physical(f64::from(self.width()), scale).min(size.width);
        renderer.set_game_width(Some(size.width.saturating_sub(chrome)));
        self.panel.set_visible(chrome > 0 && size.height > 0);
        if chrome == 0 || size.height == 0 {
            renderer.set_shell_layer(None).map_err(|e| e.to_string())?;
            return Ok(());
        }
        #[expect(clippy::cast_possible_truncation, reason = "bounded display scale")]
        let dpi = scale as f32;
        self.panel.resize([chrome, size.height], dpi)?;
        self.panel.render(time)?;
        let frame = self.panel.frame();
        renderer
            .set_egui_shell_layer(EguiShellLayer {
                jobs: &frame.jobs,
                textures: &frame.textures,
                size: rustclient_renderer::SurfaceSize::new(chrome, size.height),
                scale: dpi,
                generation: frame.generation,
                origin: [size.width - chrome, 0],
            })
            .map_err(|e| e.to_string())
    }
    fn point(&self, window: &Window) -> Option<[f32; 2]> {
        let (x, y) = self.pointer?;
        let size = window.inner_size();
        let dpi = window.scale_factor();
        let origin = f64::from(size.width)
            - f64::from(SidebarMetrics::physical(f64::from(self.width()), dpi).min(size.width));
        #[expect(
            clippy::cast_possible_truncation,
            reason = "pointer bounded by current physical canvas"
        )]
        {
            (self.width() > 0
                && x >= origin
                && x < f64::from(size.width)
                && y >= 0.
                && y < f64::from(size.height))
            .then_some([((x - origin) / dpi) as f32, (y / dpi) as f32])
        }
    }
    pub fn input(&mut self, window: &Window, event: &WindowEvent) {
        match event {
            WindowEvent::ModifiersChanged(modifiers) => self.modifiers = modifiers.state(),
            WindowEvent::CursorMoved { position, .. } => {
                self.pointer = Some((position.x, position.y));
                if let Some([x, y]) = self.point(window) {
                    self.panel.pointer(x, y, None, self.held);
                } else if self.held {
                    self.panel.pointer(-1., -1., None, true);
                } else {
                    self.panel.pointer_leave();
                }
            }
            WindowEvent::CursorLeft { .. } => {
                self.pointer = None;
                self.panel.pointer_leave();
            }
            WindowEvent::MouseInput {
                state,
                button: MouseButton::Left,
                ..
            } => {
                let point = self.point(window);
                self.focused = point.is_some();
                if point.is_some() || self.held {
                    self.held = *state == ElementState::Pressed;
                    let [x, y] = point.unwrap_or([-1., -1.]);
                    self.panel.pointer(x, y, Some(self.held), self.held);
                } else {
                    self.panel.clear_focus();
                }
            }
            WindowEvent::MouseWheel { delta, .. } => {
                if let Some([x, y]) = self.point(window) {
                    let delta = match delta {
                        MouseScrollDelta::LineDelta(x, y) => {
                            [f64::from(*x) * 45., f64::from(*y) * 45.]
                        }
                        MouseScrollDelta::PixelDelta(p) => {
                            [p.x / window.scale_factor(), p.y / window.scale_factor()]
                        }
                    };
                    self.panel.scroll(x, y, delta);
                }
            }
            WindowEvent::Focused(false) => {
                self.focused = false;
                self.held = false;
                self.composing = false;
                self.panel.clear_focus();
            }
            WindowEvent::Ime(Ime::Preedit(text, selection)) if self.focused => {
                self.composing = !text.is_empty();
                self.panel.ime_preedit(text.clone(), *selection);
            }
            WindowEvent::Ime(Ime::Commit(text)) if self.focused => {
                self.composing = false;
                self.panel.ime_commit(text.clone());
            }
            WindowEvent::KeyboardInput { event, .. } => {
                if event.state == ElementState::Pressed && !event.repeat {
                    match event.logical_key {
                        Key::Named(NamedKey::F10) => self.action(ShellAction::ToggleSidebar),
                        Key::Named(NamedKey::F9) => self.action(ShellAction::TogglePanel),
                        _ => {}
                    }
                }
                if self.focused {
                    let name = match &event.logical_key {
                        Key::Named(NamedKey::Space) => " ".into(),
                        Key::Named(key) => format!("{key:?}"),
                        Key::Character(text) => text.to_string(),
                        _ => "Unidentified".into(),
                    };
                    let mut mods = keyboard_types::Modifiers::empty();
                    if self.modifiers.shift_key() {
                        mods |= keyboard_types::Modifiers::SHIFT;
                    }
                    if self.modifiers.control_key() {
                        mods |= keyboard_types::Modifiers::CONTROL;
                    }
                    if self.modifiers.alt_key() {
                        mods |= keyboard_types::Modifiers::ALT;
                    }
                    if self.modifiers.super_key() {
                        mods |= keyboard_types::Modifiers::META;
                    }
                    self.panel.key(
                        name.parse().unwrap_or(keyboard_types::Key::Unidentified),
                        keyboard_types::Code::Unidentified,
                        mods,
                        event.state == ElementState::Pressed,
                        (!self.composing)
                            .then(|| event.text.as_ref().map(ToString::to_string))
                            .flatten(),
                    );
                }
            }
            _ => {}
        }
    }
}
