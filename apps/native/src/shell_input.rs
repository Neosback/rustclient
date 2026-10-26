//! Native input focus boundary. Shell events are consumed before cache widgets/world input.
use crate::NativeApp;
use keyboard_types::{Code, Key as PanelKey, Modifiers};
use winit::{
    event::{ElementState, Ime, MouseButton, MouseScrollDelta, WindowEvent},
    keyboard::{Key, NamedKey, PhysicalKey},
    window::Window,
};
impl NativeApp {
    #[expect(
        clippy::cast_possible_truncation,
        reason = "window coordinates converted into bounded DOM logical coordinates"
    )]
    #[expect(
        clippy::too_many_lines,
        reason = "one explicit focus boundary for all platform event classes"
    )]
    pub(super) fn route_shell(&mut self, window: &Window, event: &WindowEvent) -> bool {
        match event {
            WindowEvent::KeyboardInput { event, .. } => {
                let key_name = key_name(&event.logical_key);
                let bits = u8::from(self.modifiers.shift_key())
                    | (u8::from(self.modifiers.control_key()) << 1)
                    | (u8::from(self.modifiers.alt_key()) << 2)
                    | (u8::from(self.modifiers.super_key()) << 3);
                if event.state == ElementState::Pressed
                    && !event.repeat
                    && let Some(panel) = self.shell.hotkey(&key_name, bits)
                {
                    if panel {
                        self.shell.toggle_panel(window);
                    } else {
                        self.shell.toggle_sidebar(window);
                    }
                    self.release_game_input();
                    return true;
                }
                if !self.shell.focused {
                    return false;
                }
                let key = if event.logical_key == Key::Named(NamedKey::Space) {
                    PanelKey::Character(" ".into())
                } else {
                    key_name.parse().unwrap_or(PanelKey::Unidentified)
                };
                let code = match event.physical_key {
                    PhysicalKey::Code(code) => {
                        format!("{code:?}").parse().unwrap_or(Code::Unidentified)
                    }
                    PhysicalKey::Unidentified(_) => Code::Unidentified,
                };
                let mut modifiers = Modifiers::empty();
                if self.modifiers.shift_key() {
                    modifiers |= Modifiers::SHIFT;
                }
                if self.modifiers.control_key() {
                    modifiers |= Modifiers::CONTROL;
                }
                if self.modifiers.alt_key() {
                    modifiers |= Modifiers::ALT;
                }
                if self.modifiers.super_key() {
                    modifiers |= Modifiers::SUPER;
                }
                self.shell.panel.key(
                    key,
                    code,
                    modifiers,
                    event.state == ElementState::Pressed,
                    (!self.shell.composing)
                        .then(|| event.text.as_ref().map(ToString::to_string))
                        .flatten(),
                );
                true
            }
            WindowEvent::Ime(ime) if self.shell.focused => {
                match ime {
                    Ime::Preedit(text, selection) => {
                        self.shell.composing = !text.is_empty();
                        self.shell.panel.ime_preedit(text.clone(), *selection);
                    }
                    Ime::Commit(text) => {
                        self.shell.composing = false;
                        self.shell.panel.ime_commit(text.clone());
                    }
                    Ime::Disabled => {
                        self.shell.composing = false;
                        self.shell.panel.ime_preedit(String::new(), None);
                    }
                    Ime::Enabled => {}
                }
                true
            }
            WindowEvent::CursorMoved { position, .. } => {
                self.cursor = Some((position.x, position.y));
                let point = self.shell.point(window, position.x, position.y);
                if let Some([x, y]) = point {
                    self.shell.panel.pointer(x, y, None, self.shell.held);
                    self.ui.clear_hover();
                    self.hover = None;
                    return true;
                }
                if self.shell.held {
                    let origin = self.shell.game_size(window).width;
                    self.shell.panel.pointer(
                        ((position.x - f64::from(origin)) / window.scale_factor()) as f32,
                        (position.y / window.scale_factor()) as f32,
                        None,
                        true,
                    );
                    return true;
                }
                false
            }
            WindowEvent::MouseInput { state, button, .. } => {
                let point = self
                    .cursor
                    .and_then(|(x, y)| self.shell.point(window, x, y));
                if *state == ElementState::Pressed && point.is_none() {
                    self.shell.focused = false;
                    self.shell.composing = false;
                    self.shell.panel.clear_focus();
                    if *button == MouseButton::Left
                        && let Some((cx, cy)) = self.cursor
                    {
                        let (ux, uy) = self.ui_pointer(window, cx, cy);
                        let canvas_width = if self.state.authenticated_player().is_some() {
                            self.state.interfaces().window().canvas.width
                        } else {
                            765
                        };
                        self.ensure_widget_tree();
                        if self
                            .shell
                            .overlay_button_rect(canvas_width, &self.widget_tree)
                            .contains(ux, uy)
                        {
                            self.shell.toggle_sidebar(window);
                            self.release_game_input();
                            return true;
                        }
                    }
                }
                if point.is_some() || self.shell.held {
                    if *button == MouseButton::Left {
                        let [x, y] = point.unwrap_or([-1., -1.]);
                        self.shell.held = *state == ElementState::Pressed;
                        self.shell.focused = true;
                        self.shell
                            .panel
                            .pointer(x, y, Some(self.shell.held), self.shell.held);
                    }
                    self.release_game_input();
                    return true;
                }
                self.shell.block_extra_mouse()
                    && matches!(
                        button,
                        MouseButton::Back | MouseButton::Forward | MouseButton::Other(_)
                    )
            }
            WindowEvent::MouseWheel { delta, .. } => {
                if let Some([x, y]) = self
                    .cursor
                    .and_then(|(x, y)| self.shell.point(window, x, y))
                {
                    let delta = match delta {
                        MouseScrollDelta::LineDelta(x, y) => {
                            [f64::from(*x) * 45., f64::from(*y) * 45.]
                        }
                        MouseScrollDelta::PixelDelta(p) => {
                            [p.x / window.scale_factor(), p.y / window.scale_factor()]
                        }
                    };
                    self.shell.panel.scroll(x, y, delta);
                    return true;
                }
                false
            }
            WindowEvent::Focused(false) => {
                self.shell.focused = false;
                self.shell.held = false;
                self.shell.composing = false;
                self.shell.panel.clear_focus();
                false
            }
            _ => false,
        }
    }
    fn release_game_input(&mut self) {
        self.scene.input = rustclient_renderer::CameraInput::default();
        self.widget_pointer = crate::widget_pointer::WidgetPointer::default();
        self.widget_menu.close();
        self.context_menu.close();
    }
}
fn key_name(key: &Key) -> String {
    match key {
        Key::Named(key) => format!("{key:?}"),
        Key::Character(text) => text.to_string(),
        _ => "Unidentified".into(),
    }
}
