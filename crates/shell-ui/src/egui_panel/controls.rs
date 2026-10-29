//! Schema-generated controls; only validated final edits enter the existing configuration path.
use super::{Color32, ConfigValue, EguiPanel, ShellAction};
use crate::SettingView;
use rustclient_plugin_host::configuration::Control;
impl EguiPanel {
    #[expect(
        clippy::too_many_lines,
        reason = "exhaustive typed control generation without foreign UI state"
    )]
    pub(super) fn setting(&mut self, ui: &mut egui::Ui, plugin: &str, row: &SettingView) {
        let key = (plugin.to_owned(), row.descriptor.key.clone());
        let mut value = self.drafts.get(&key).unwrap_or(&row.value).clone();
        let mut commit = false;
        ui.push_id(&key, |ui| {
            ui.vertical(|ui| {
                let label = ui
                    .label(&row.descriptor.label)
                    .on_hover_text(row.descriptor.description.replace("<br>", " "));
                self.hit(&label);
                ui.add_enabled_ui(row.unavailable.is_none(), |ui| {
                    match (&row.descriptor.control, &mut value) {
                        (Control::Boolean, ConfigValue::Boolean(value)) => {
                            let r = ui.checkbox(value, "");
                            self.editor(&key, &r);
                            commit = r.changed();
                        }
                        (Control::Integer { min, max }, ConfigValue::Integer(value)) => {
                            let r = ui.add(egui::Slider::new(value, *min..=*max));
                            self.editor(&key, &r);
                            commit = r.drag_stopped()
                                || r.changed() && !ui.input(|input| input.pointer.any_down());
                        }
                        (Control::Decimal { min, max }, ConfigValue::Decimal(value)) => {
                            let r = ui.add(
                                egui::Slider::new(value, *min..=*max)
                                    .custom_formatter(|value, _| format!("{:.3}", value / 1000.)),
                            );
                            self.editor(&key, &r);
                            commit = r.drag_stopped()
                                || r.changed() && !ui.input(|input| input.pointer.any_down());
                        }
                        (Control::Choice { options }, ConfigValue::Choice(value)) => {
                            let response = egui::ComboBox::from_id_salt("choice")
                                .selected_text(value.as_str())
                                .show_ui(ui, |ui| {
                                    for option in options {
                                        let r = ui.selectable_value(value, option.clone(), option);
                                        self.editor(&key, &r);
                                        commit |= r.changed();
                                    }
                                })
                                .response;
                            self.editor(&key, &response);
                        }
                        (Control::Text { max_len }, ConfigValue::Text(value)) => {
                            let r = ui.add(egui::TextEdit::singleline(value).char_limit(*max_len));
                            self.editor(&key, &r);
                            commit = r.lost_focus()
                                || r.has_focus()
                                    && ui.input(|input| input.key_pressed(egui::Key::Enter));
                        }
                        (Control::Color, ConfigValue::Color(value)) => {
                            let text = self
                                .text
                                .entry(key.clone())
                                .or_insert_with(|| format!("{value:08X}"));
                            let r = ui.text_edit_singleline(text);
                            if (r.lost_focus()
                                || r.has_focus()
                                    && ui.input(|input| input.key_pressed(egui::Key::Enter)))
                                && let Ok(color) =
                                    u32::from_str_radix(text.trim().trim_start_matches('#'), 16)
                            {
                                *value = color;
                                commit = true;
                            }
                            self.editor(&key, &r);
                        }
                        (Control::Hotkey, ConfigValue::Hotkey(value)) => {
                            let capturing = self.capture.as_ref() == Some(&key);
                            let label = if capturing {
                                "Press a key…".into()
                            } else {
                                format!("{}  [click to bind]", value.key)
                            };
                            let r = ui.button(label);
                            self.editor(&key, &r);
                            if r.clicked() {
                                self.capture = Some(key.clone());
                            }
                            let r = ui.small_button("Clear binding");
                            self.editor(&key, &r);
                            if r.clicked() {
                                value.key.clear();
                                value.modifiers = 0;
                                commit = true;
                            }
                        }
                        (Control::Size, ConfigValue::Size(size)) => {
                            ui.horizontal(|ui| {
                                for (index, value) in size.iter_mut().enumerate() {
                                    let r =
                                        ui.add(egui::DragValue::new(value).range(if index == 0 {
                                            320..=16384
                                        } else {
                                            200..=16384
                                        }));
                                    self.editor(&key, &r);
                                    commit |= r.drag_stopped() || r.lost_focus();
                                }
                            });
                        }
                        (Control::Font, ConfigValue::Font(font)) => {
                            let r = ui.text_edit_singleline(&mut font.family);
                            self.editor(&key, &r);
                            commit |= r.lost_focus();
                            ui.horizontal(|ui| {
                                let r = ui.add(egui::DragValue::new(&mut font.size).range(6..=96));
                                self.editor(&key, &r);
                                commit |= r.drag_stopped() || r.lost_focus();
                                let r = ui.checkbox(&mut font.bold, "Bold");
                                self.editor(&key, &r);
                                commit |= r.changed();
                                let r = ui.checkbox(&mut font.italic, "Italic");
                                self.editor(&key, &r);
                                commit |= r.changed();
                            });
                        }
                        _ => {
                            ui.colored_label(Color32::LIGHT_RED, "Invalid configuration value");
                        }
                    }
                });
                if let Some(reason) = row.unavailable.as_ref().or(row.note.as_ref()) {
                    ui.small(reason);
                }
                ui.add_space(3.);
            });
        });
        if commit && value != row.value && row.descriptor.accepts(&value) {
            self.channel.send(ShellAction::Configure {
                plugin: plugin.into(),
                key: row.descriptor.key.clone(),
                value: value.clone(),
            });
        }
        self.drafts.insert(key, value);
    }
}
