//! Shared `RuneLite` chrome; navigation is session presentation, never accepted settings.
use super::{
    Color32, ConfigValue, EguiPanel, Pos2, Rect, ShellAction, ShellModel, SidebarMetrics,
    SidebarState, Vec2, theme,
};
impl EguiPanel {
    #[expect(
        clippy::cast_precision_loss,
        reason = "fixed 31-pixel source rail fits f32"
    )]
    pub(super) fn content(&mut self, ui: &mut egui::Ui, model: &ShellModel) {
        ui.painter().rect_filled(ui.max_rect(), 0., theme::PANEL);
        let bounds = ui.available_rect_before_wrap();
        let rail_width = SidebarMetrics::RAIL as f32;
        let rail_left = (bounds.right() - rail_width).max(bounds.left());
        if model.sidebar == SidebarState::Panel {
            let inset = SidebarMetrics::PADDING;
            let panel = Rect::from_min_max(
                bounds.min + Vec2::splat(inset),
                Pos2::new(rail_left - inset, bounds.bottom() - SidebarMetrics::PADDING),
            );
            ui.scope_builder(
                egui::UiBuilder::new()
                    .max_rect(panel)
                    .layout(egui::Layout::top_down(egui::Align::Min)),
                |ui| {
                    ui.set_clip_rect(panel);
                    self.panel(ui, model);
                },
            );
        }
        ui.painter().rect_filled(
            Rect::from_min_max(Pos2::new(rail_left, bounds.top()), bounds.max),
            0.,
            theme::RAIL,
        );
        ui.painter().vline(
            rail_left,
            bounds.y_range(),
            egui::Stroke::new(1., Color32::from_gray(23)),
        );
        let rail = Rect::from_min_max(Pos2::new(rail_left + 1., bounds.top()), bounds.max);
        ui.scope_builder(
            egui::UiBuilder::new()
                .max_rect(rail)
                .layout(egui::Layout::top_down(egui::Align::Center)),
            |ui| {
                let response = ui
                    .add_sized(
                        [30., SidebarMetrics::TAB_HEIGHT],
                        egui::Button::image(
                            self.assets
                                .image("config_icon", Vec2::splat(SidebarMetrics::ICON)),
                        )
                        .frame(model.sidebar == SidebarState::Panel),
                    )
                    .on_hover_text("Configuration");
                if model.sidebar == SidebarState::Panel {
                    ui.painter().rect_filled(
                        Rect::from_min_size(
                            response.rect.min,
                            Vec2::new(3., SidebarMetrics::TAB_HEIGHT),
                        ),
                        0.,
                        theme::ORANGE,
                    );
                }
                self.targets.insert("toggle-panel".into(), response.rect);
                self.hit(&response);
                if response.clicked() {
                    self.channel.send(ShellAction::TogglePanel);
                }
            },
        );
    }
    #[expect(
        clippy::too_many_lines,
        reason = "one panel pass lays out navigation and schema-generated settings"
    )]
    fn panel(&mut self, ui: &mut egui::Ui, model: &ShellModel) {
        self.configuration_header(ui);
        ui.add_space(10.);
        if let Some(id) = self.selected.clone()
            && let Some(plugin) = model.plugins.iter().find(|plugin| plugin.id == id)
        {
            ui.horizontal(|ui| {
                let back = self
                    .assets
                    .button(ui, "config_back_icon", "Back to plugins");
                self.targets.insert("back".into(), back.rect);
                self.hit(&back);
                if back.clicked() {
                    self.selected = None;
                }
                ui.label(egui::RichText::new(&plugin.name).font(egui::FontId::new(
                    16.,
                    egui::FontFamily::Name("runelite-bold".into()),
                )));
                if plugin.required {
                    self.badge(ui, &plugin.id);
                }
            });
            if let Some((_, Some(error))) = model.runtime.get(&id) {
                ui.colored_label(Color32::LIGHT_RED, error);
            }
            ui.separator();
            egui::ScrollArea::vertical()
                .id_salt(("settings", &id))
                .show(ui, |ui| {
                    let mut sections = Vec::<&str>::new();
                    for setting in &plugin.settings {
                        if !sections.contains(&setting.descriptor.section.as_str()) {
                            sections.push(&setting.descriptor.section);
                        }
                    }
                    for section in sections {
                        if section.is_empty() {
                            for setting in plugin
                                .settings
                                .iter()
                                .filter(|setting| setting.descriptor.section.is_empty())
                            {
                                self.setting(ui, &id, setting);
                            }
                            continue;
                        }
                        egui::CollapsingHeader::new(
                            egui::RichText::new(section_title(section))
                                .color(theme::ORANGE)
                                .font(egui::FontId::new(
                                    16.,
                                    egui::FontFamily::Name("runelite-bold".into()),
                                )),
                        )
                        .id_salt((&id, section))
                        .default_open(true)
                        .show(ui, |ui| {
                            for setting in plugin
                                .settings
                                .iter()
                                .filter(|setting| setting.descriptor.section == section)
                            {
                                self.setting(ui, &id, setting);
                            }
                        });
                    }
                    ui.separator();
                    let reset = ui
                        .button("Reset settings")
                        .on_hover_text("Restore source defaults");
                    self.targets.insert("reset".into(), reset.rect);
                    self.hit(&reset);
                    if reset.clicked() {
                        self.channel.send(ShellAction::Reset { plugin: id.clone() });
                    }
                });
        } else {
            egui::Frame::new()
                .fill(theme::RAIL)
                .inner_margin(egui::Margin::symmetric(4, 4))
                .show(ui, |ui| {
                    ui.horizontal(|ui| {
                        ui.add(self.assets.image("search", Vec2::splat(16.)));
                        let has_query = !self.query.is_empty();
                        let reserve = if has_query { 22. } else { 0. };
                        let search = ui.add_sized(
                            [(ui.available_width() - reserve).max(1.), 22.],
                            egui::TextEdit::singleline(&mut self.query)
                                .hint_text("Search plugins")
                                .id_salt("plugin-search")
                                .frame(egui::Frame::NONE),
                        );
                        self.hit(&search);
                        self.targets.insert("search".into(), search.rect);
                        if has_query {
                            let clear = ui.add(
                                egui::Button::new(
                                    egui::RichText::new("×").color(Color32::LIGHT_RED).font(
                                        egui::FontId::new(
                                            16.,
                                            egui::FontFamily::Name("runelite-bold".into()),
                                        ),
                                    ),
                                )
                                .frame(false),
                            );
                            self.targets.insert("clear-search".into(), clear.rect);
                            self.hit(&clear);
                            if clear.clicked() {
                                self.query.clear();
                            }
                        }
                    });
                });
            ui.add_space(8.);
            let query = self.query.to_lowercase();
            let mut plugins: Vec<_> = model.plugins.iter().collect();
            plugins.sort_by(|a, b| {
                model
                    .pinned
                    .contains(&b.id)
                    .cmp(&model.pinned.contains(&a.id))
                    .then_with(|| a.name.to_lowercase().cmp(&b.name.to_lowercase()))
            });
            ui.spacing_mut().item_spacing = Vec2::new(3., 5.);
            ui.spacing_mut().interact_size.y = 20.;
            ui.spacing_mut().button_padding = Vec2::ZERO;
            egui::ScrollArea::vertical()
                .id_salt("plugins")
                .show(ui, |ui| {
                    for plugin in plugins {
                        if !query.is_empty()
                            && !plugin.name.to_lowercase().contains(&query)
                            && !plugin.description.to_lowercase().contains(&query)
                            && !plugin
                                .tags
                                .iter()
                                .any(|tag| tag.to_lowercase().contains(&query))
                        {
                            continue;
                        }
                        ui.scope_builder(
                            egui::UiBuilder::new()
                                .scope_id(egui::Id::unique(("plugin-row", &plugin.id))),
                            |ui| {
                                ui.horizontal(|ui| {
                                    let pinned = model.pinned.contains(&plugin.id);
                                    let pin = self.assets.button(
                                        ui,
                                        if pinned { "star_on" } else { "star_off" },
                                        "Favorite plugin",
                                    );
                                    self.targets
                                        .insert(format!("favorite:{}", plugin.id), pin.rect);
                                    self.hit(&pin);
                                    if pin.clicked() {
                                        self.channel.send(ShellAction::ToggleFavorite {
                                            plugin: plugin.id.clone(),
                                        });
                                    }
                                    let width = (ui.available_width()
                                        - if plugin.required { 100. } else { 58. })
                                    .max(1.);
                                    let response = name_button(ui, &plugin.name, width, pinned)
                                        .on_hover_text(&plugin.description);
                                    self.hit(&response);
                                    self.targets
                                        .insert(format!("plugin:{}", plugin.id), response.rect);
                                    if response.clicked() {
                                        self.selected = Some(plugin.id.clone());
                                    }
                                    let edit = self.assets.button(
                                        ui,
                                        "config_edit_icon",
                                        "Configure plugin",
                                    );
                                    self.targets
                                        .insert(format!("configure:{}", plugin.id), edit.rect);
                                    self.hit(&edit);
                                    if edit.clicked() {
                                        self.selected = Some(plugin.id.clone());
                                    }
                                    if plugin.required {
                                        self.badge(ui, &plugin.id);
                                    } else {
                                        let mut enabled = plugin.enabled;
                                        let response = ui
                                            .add_enabled_ui(
                                                plugin.enablement_unavailable.is_none(),
                                                |ui| self.assets.toggle(ui, &mut enabled),
                                            )
                                            .inner;
                                        let response =
                                            if let Some(reason) = &plugin.enablement_unavailable {
                                                response.on_disabled_hover_text(reason)
                                            } else {
                                                response
                                            };
                                        self.targets.insert(
                                            format!("enabled:{}", plugin.id),
                                            response.rect,
                                        );
                                        self.hit(&response);
                                        if response.changed() {
                                            self.channel.send(ShellAction::Configure {
                                                plugin: plugin.id.clone(),
                                                key: "enabled".into(),
                                                value: ConfigValue::Boolean(enabled),
                                            });
                                        }
                                    }
                                });
                            },
                        );
                        if let Some((status, error)) = model.runtime.get(&plugin.id)
                            && ((plugin.enabled
                                && *status != rustclient_plugin_host::LifecycleStatus::Running)
                                || error.is_some())
                        {
                            ui.small(format!(
                                "{status:?}{}",
                                error.as_ref().map_or(String::new(), |s| format!(": {s}"))
                            ));
                        }
                    }
                });
        }
        if let Some(error) = &model.error {
            ui.colored_label(Color32::LIGHT_RED, error);
        }
    }
    fn configuration_header(&mut self, ui: &mut egui::Ui) {
        ui.horizontal(|ui| {
            ui.spacing_mut().item_spacing.x = SidebarMetrics::HEADER_GAP;
            let width = ((ui.available_width() - 2. * SidebarMetrics::HEADER_GAP) / 3.)
                .floor()
                .max(1.);
            for (icon, label, active) in [
                ("config_icon_lg", "Configuration", true),
                (
                    "profile_icon",
                    "Profiles — application profile support is not implemented",
                    false,
                ),
                (
                    "plugin_hub_icon",
                    "Plugin Hub — package browsing/install support is not implemented",
                    false,
                ),
            ] {
                let (rect, response) = ui.allocate_exact_size(
                    Vec2::new(width, SidebarMetrics::HEADER_TAB_HEIGHT),
                    egui::Sense::hover(),
                );
                ui.painter().rect_filled(rect, 0., theme::RAIL);
                let icon_size = self.assets.size(icon);
                self.assets
                    .image(icon, icon_size)
                    .tint(if active {
                        Color32::WHITE
                    } else {
                        Color32::from_white_alpha(120)
                    })
                    .paint_at(ui, Rect::from_center_size(rect.center(), icon_size));
                if active {
                    ui.painter().hline(
                        rect.x_range(),
                        rect.bottom() - 0.5,
                        egui::Stroke::new(1., theme::ORANGE),
                    );
                }
                self.targets.insert(format!("header:{icon}"), rect);
                self.hit(&response);
                response.on_hover_text(label);
            }
        });
    }

    /// Logical control bounds for accessibility/diagnostics; never semantic geometry.
    #[must_use]
    pub fn target_bounds(&self, target: &str) -> Option<[f32; 4]> {
        self.targets
            .get(target)
            .map(|rect| [rect.min.x, rect.min.y, rect.width(), rect.height()])
    }
    pub(super) fn editor(&mut self, key: &(String, String), response: &egui::Response) {
        self.targets
            .entry(format!("setting:{}:{}", key.0, key.1))
            .or_insert(response.rect);
        self.hit(response);
    }
    fn badge(&mut self, ui: &mut egui::Ui, id: &str) {
        let response = ui
            .label(egui::RichText::new("Always on").color(theme::ORANGE))
            .on_hover_text("Required application service");
        self.targets
            .insert(format!("always-on:{id}"), response.rect);
        self.hit(&response);
    }
}

fn section_title(section: &str) -> String {
    if section.contains(' ') {
        return section.into();
    }
    let mut title = String::new();
    for (index, c) in section.chars().enumerate() {
        if index == 0 {
            title.extend(c.to_uppercase());
        } else if c.is_uppercase() {
            title.push(' ');
            title.extend(c.to_lowercase());
        } else {
            title.push(c);
        }
    }
    title
}

// Fixed slot and left-aligned label: add_sized centers short labels inside the slot.
fn name_button(ui: &mut egui::Ui, name: &str, width: f32, pinned: bool) -> egui::Response {
    let (rect, response) = ui.allocate_exact_size(Vec2::new(width, 20.), egui::Sense::click());
    let color = if pinned {
        Color32::from_rgba_unmultiplied(0, 255, 13, 120)
    } else {
        Color32::WHITE
    };
    let text = egui::WidgetText::from(egui::RichText::new(name).color(color));
    let galley = text.into_galley(
        ui,
        Some(egui::TextWrapMode::Truncate),
        width,
        egui::TextStyle::Body,
    );
    let position = Pos2::new(rect.left(), rect.center().y - galley.size().y / 2.);
    ui.painter()
        .with_clip_rect(rect.intersect(ui.clip_rect()))
        .galley(position, galley, color);
    if response.has_focus() {
        ui.painter().rect_stroke(
            rect,
            0.,
            ui.visuals().selection.stroke,
            egui::StrokeKind::Inside,
        );
    }
    if response.clicked() {
        response.request_focus();
    }
    response.widget_info(|| egui::WidgetInfo::labeled(egui::Role::Button, ui.is_enabled(), name));
    response
}
