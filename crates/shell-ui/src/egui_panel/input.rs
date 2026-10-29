use super::{ConfigValue, EguiPanel, Pos2, ShellAction, Vec2};
use keyboard_types::{Code, Key, Modifiers};
impl EguiPanel {
    pub fn pointer(&mut self, x: f32, y: f32, pressed: Option<bool>, held: bool) {
        if !self.visible {
            return;
        }
        let point = Pos2::new(x, y);
        let target = |point: Pos2| {
            self.hits
                .iter()
                .rev()
                .find(|(rect, _)| rect.contains(point))
                .map(|(_, id)| *id)
        };
        let unchanged = pressed.is_none() && !held && target(point) == target(self.pointer);
        self.pointer = point;
        let event = egui::Event::PointerMoved(point);
        if let Some(previous @ egui::Event::PointerMoved(_)) = self.input.events.last_mut() {
            *previous = event;
        } else {
            self.input.events.push(event);
        }
        if let Some(pressed) = pressed {
            self.input.focused = true;
            self.input.events.push(egui::Event::PointerButton {
                pos: point,
                button: egui::PointerButton::Primary,
                pressed,
                modifiers: self.modifiers,
            });
        }
        if unchanged {
            self.stats.reused_inputs += 1;
        } else {
            self.dirty = true;
        }
    }
    pub fn pointer_leave(&mut self) {
        if !self.visible {
            return;
        }
        if self.pointer != Pos2::new(-1., -1.) {
            self.input.events.push(egui::Event::PointerGone);
            self.pointer = Pos2::new(-1., -1.);
            self.dirty = true;
        }
    }
    #[expect(
        clippy::cast_possible_truncation,
        reason = "bounded platform wheel deltas"
    )]
    pub fn scroll(&mut self, x: f32, y: f32, delta: [f64; 2]) {
        if !self.visible {
            return;
        }
        self.pointer(x, y, None, false);
        self.input.events.push(egui::Event::MouseWheel {
            unit: egui::MouseWheelUnit::Point,
            phase: egui::TouchPhase::Move,
            source: egui::MouseWheelSource::Unknown,
            delta: Vec2::new(delta[0] as f32, delta[1] as f32),
            modifiers: self.modifiers,
        });
        self.dirty = true;
    }
    #[expect(
        clippy::needless_pass_by_value,
        reason = "owned key matches the platform input adapter contract"
    )]
    pub fn key(
        &mut self,
        key: Key,
        _code: Code,
        modifiers: Modifiers,
        pressed: bool,
        text: Option<String>,
    ) {
        if !self.visible {
            return;
        }
        self.input.focused = true;
        self.modifiers = egui::Modifiers {
            alt: modifiers.contains(Modifiers::ALT),
            ctrl: modifiers.contains(Modifiers::CONTROL),
            shift: modifiers.contains(Modifiers::SHIFT),
            mac_cmd: modifiers.contains(Modifiers::META),
            command: modifiers.intersects(Modifiers::META | Modifiers::CONTROL),
        };
        self.input
            .events
            .push(egui::Event::ModifiersChanged(self.modifiers));
        if pressed && key == Key::Escape {
            self.ctx.stop_dragging();
            self.drafts.clear();
            self.text.clear();
        }
        let name = key.to_string();
        if pressed && let Some((plugin, setting)) = self.capture.take() {
            if key != Key::Escape
                && name.chars().all(|ch| ch.is_ascii_alphanumeric())
                && name.len() <= 32
            {
                let modifiers = u8::from(self.modifiers.shift)
                    | u8::from(self.modifiers.ctrl) << 1
                    | u8::from(self.modifiers.alt) << 2
                    | u8::from(self.modifiers.mac_cmd) << 3;
                self.channel.send(ShellAction::Configure {
                    plugin,
                    key: setting,
                    value: ConfigValue::Hotkey(rustclient_plugin_host::configuration::Hotkey {
                        key: name,
                        modifiers,
                    }),
                });
            }
            self.dirty = true;
            return;
        }
        if pressed && self.modifiers.command {
            match name.to_ascii_lowercase().as_str() {
                "c" => self.input.events.push(egui::Event::Copy),
                "x" => self.input.events.push(egui::Event::Cut),
                "v" => {
                    if let Some(clipboard) = &mut self.clipboard {
                        match clipboard.read_text() {
                            Ok(text) => self.input.events.push(egui::Event::Paste(text)),
                            Err(error) => eprintln!("shell clipboard: {error}"),
                        }
                    }
                }
                _ => {}
            }
        }
        if let Some(key) = egui::Key::from_name(&name) {
            self.input.events.push(egui::Event::Key {
                key,
                physical_key: None,
                pressed,
                repeat: false,
                modifiers: self.modifiers,
            });
        }
        if pressed
            && !self.modifiers.command
            && let Some(text) = text.filter(|text| !text.chars().any(char::is_control))
        {
            self.input.events.push(egui::Event::Text(text));
        }
        self.dirty = true;
    }
    pub fn ime_commit(&mut self, text: String) {
        if self.visible {
            self.input
                .events
                .push(egui::Event::Ime(egui::ImeEvent::Commit(text)));
            self.dirty = true;
        }
    }
    pub fn ime_preedit(&mut self, text: String, selection: Option<(usize, usize)>) {
        if self.visible {
            let active_range_chars = selection.and_then(|(start, end)| {
                Some(text.get(..start)?.chars().count()..text.get(..end)?.chars().count())
            });
            self.input
                .events
                .push(egui::Event::Ime(egui::ImeEvent::Preedit {
                    text,
                    active_range_chars,
                }));
            self.dirty = true;
        }
    }
    pub fn clear_focus(&mut self) {
        self.input.events.push(egui::Event::WindowFocused(false));
        self.input.focused = false;
        self.capture = None;
        self.ime_cursor = None;
        self.modifiers = egui::Modifiers::default();
        self.drafts.clear();
        self.text.clear();
        self.dirty = true;
    }
}
