//! Toolkit adapter for immutable, activation-scoped plugin panel documents.
use super::{EguiPanel, ShellAction, ShellModel, SidebarState, Vec2, theme};
use rustclient_plugin_api::{PanelBlock, PanelEvent, PanelIcon, PanelValue, PluginId};
use rustclient_plugin_host::{HostedPanel, PanelAction};

pub(super) struct EditorDraft {
    owner: HostedPanel,
    state_id: egui::Id,
    element: String,
    text: String,
    sent: String,
}
impl EditorDraft {
    fn action(&mut self) -> Option<PanelAction> {
        if self.text == self.sent {
            return None;
        }
        self.sent.clone_from(&self.text);
        Some(PanelAction {
            plugin: self.owner.plugin.clone(),
            activation: self.owner.activation,
            lease: self.owner.lease,
            event: PanelEvent {
                panel: self.owner.document.id.clone(),
                revision: self.owner.document.revision,
                element: self.element.clone(),
                value: PanelValue::Text(self.text.clone()),
            },
        })
    }
}
impl EguiPanel {
    pub(super) fn prune_editors(&mut self, model: &ShellModel) {
        let ctx = self.ctx.clone();
        self.editors.retain(|(plugin, panel, _), draft| {
            let live = model.panels.iter().any(|current| {
                &current.plugin == plugin
                    && &current.document.id == panel
                    && current.activation == draft.owner.activation
                    && current.document.blocks.iter().any(|block| {
                        matches!(block,
                        PanelBlock::Editor { id, .. } if id == &draft.element)
                    })
            });
            if !live {
                ctx.data_mut(|data| data.remove::<egui::text_edit::TextEditState>(draft.state_id));
                ctx.memory_mut(|memory| memory.surrender_focus(draft.state_id));
            }
            live
        });
    }

    pub(super) fn flush_editors(&mut self) {
        for editor in self.editors.values_mut() {
            if let Some(action) = editor.action() {
                self.channel.send(ShellAction::PluginUi(action));
            }
        }
    }

    pub(super) fn plugin_navigation(&mut self, ui: &mut egui::Ui, model: &ShellModel) {
        let mut panels: Vec<_> = model.panels.iter().collect();
        panels.sort_by(|a, b| {
            a.document
                .priority
                .cmp(&b.document.priority)
                .then_with(|| a.document.title.cmp(&b.document.title))
                .then_with(|| a.plugin.cmp(&b.plugin))
        });
        for panel in panels {
            let key = (panel.plugin.clone(), panel.document.id.clone());
            let selected =
                self.plugin_selected.as_ref() == Some(&key) && model.sidebar == SidebarState::Panel;
            let icon = match panel.document.icon {
                PanelIcon::Notes => "notes",
                PanelIcon::XpTracker => "xp_tracker",
            };
            let response = ui
                .add_sized(
                    [30., crate::SidebarMetrics::TAB_HEIGHT],
                    egui::Button::image(
                        self.assets
                            .image(icon, Vec2::splat(crate::SidebarMetrics::ICON)),
                    )
                    .frame(selected),
                )
                .on_hover_text(&panel.document.title);
            self.targets.insert(
                format!("panel:{}:{}", panel.plugin, panel.document.id),
                response.rect,
            );
            self.hit(&response);
            if selected {
                ui.painter().rect_filled(
                    egui::Rect::from_min_size(
                        response.rect.min,
                        Vec2::new(3., crate::SidebarMetrics::TAB_HEIGHT),
                    ),
                    0.,
                    theme::ORANGE,
                );
            }
            if response.clicked() {
                self.flush_editors();
                self.plugin_selected = Some(key);
                self.channel.send(if selected {
                    ShellAction::Close
                } else {
                    ShellAction::Open
                });
            }
        }
    }

    /// Returns false to draw configuration navigation instead.
    pub(super) fn plugin_panel(&mut self, ui: &mut egui::Ui, model: &ShellModel) -> bool {
        self.prune_editors(model);
        let Some((plugin, id)) = &self.plugin_selected else {
            return false;
        };
        let Some(panel) = model
            .panels
            .iter()
            .find(|p| &p.plugin == plugin && &p.document.id == id)
        else {
            self.plugin_selected = None;
            return false;
        };
        if let Some((_, Some(error))) = model.runtime.get(panel.plugin.as_str()) {
            ui.colored_label(egui::Color32::LIGHT_RED, error);
        }
        egui::ScrollArea::vertical()
            .id_salt(("plugin-panel", &panel.plugin, &panel.document.id))
            .show(ui, |ui| {
                for block in panel.document.blocks.iter() {
                    match block {
                        PanelBlock::Text(text) => {
                            ui.add(egui::Label::new(text).wrap().selectable(true));
                        }
                        PanelBlock::Metric { label, value } => {
                            ui.horizontal(|ui| {
                                ui.label(label);
                                ui.label(value);
                            });
                        }
                        PanelBlock::Button { id, label } => {
                            let response = ui.button(label);
                            self.hit(&response);
                            if response.clicked() {
                                self.channel.send(ShellAction::PluginUi(PanelAction {
                                    plugin: panel.plugin.clone(),
                                    activation: panel.activation,
                                    lease: panel.lease,
                                    event: PanelEvent {
                                        panel: panel.document.id.clone(),
                                        revision: panel.document.revision,
                                        element: id.clone(),
                                        value: PanelValue::Activate,
                                    },
                                }));
                            }
                        }
                        PanelBlock::Editor {
                            id,
                            text,
                            max_bytes,
                        } => self.panel_editor(ui, panel, id, text, *max_bytes),
                    }
                }
            });
        true
    }

    fn panel_editor(
        &mut self,
        ui: &mut egui::Ui,
        panel: &HostedPanel,
        element: &str,
        text: &str,
        max_bytes: u32,
    ) {
        let key: (PluginId, String, String) = (
            panel.plugin.clone(),
            panel.document.id.clone(),
            element.into(),
        );
        let editor_id = ui.make_persistent_id((&key, panel.activation));
        let is_new = !self.editors.contains_key(&key);
        let draft = self.editors.entry(key).or_insert_with(|| EditorDraft {
            owner: panel.clone(),
            state_id: editor_id,
            element: element.into(),
            text: text.into(),
            sent: text.into(),
        });
        if draft.owner.lease != panel.lease {
            // A new authoritative document wins over a stale uncommitted draft.
            if text != draft.sent {
                draft.text = text.into();
            }
            draft.sent = text.into();
            draft.owner = panel.clone();
        }
        if is_new {
            let mut state = egui::TextEdit::load_state(ui.ctx(), editor_id).unwrap_or_default();
            state.set_undoer(egui::util::undoer::Undoer::with_settings(
                egui::util::undoer::Settings {
                    max_undos: 500,
                    ..egui::util::undoer::Settings::default()
                },
            ));
            state.store(ui.ctx(), editor_id);
        }
        // Scroll content has unbounded available height; use the finite visible viewport.
        let editor_height = (ui.clip_rect().bottom() - ui.cursor().top() - 20.).max(120.);
        let response = egui::Frame::new()
            .fill(theme::RAIL)
            .inner_margin(10)
            .show(ui, |ui| {
                ui.add_sized(
                    [ui.available_width(), editor_height],
                    egui::TextEdit::multiline(&mut draft.text)
                        .id(editor_id)
                        .font(egui::TextStyle::Body)
                        .frame(egui::Frame::NONE)
                        .desired_width(f32::INFINITY)
                        .char_limit(max_bytes as usize),
                )
            })
            .inner;
        // egui's char limit counts Unicode scalars; the API bound counts UTF-8 bytes.
        if draft.text.len() > max_bytes as usize {
            let mut end = max_bytes as usize;
            while !draft.text.is_char_boundary(end) {
                end -= 1;
            }
            draft.text.truncate(end);
        }
        let action = response.lost_focus().then(|| draft.action()).flatten();
        self.targets
            .insert(format!("editor:{}:{element}", panel.plugin), response.rect);
        self.hit(&response);
        if let Some(action) = action {
            self.channel.send(ShellAction::PluginUi(action));
        }
    }
}
