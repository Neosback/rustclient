//! Demand-driven native/WebGPU presentation. Accepted configuration stays in plugin-host.
mod controls;
mod input;
use crate::{ShellAction, ShellChannel, ShellModel, SidebarState};
use egui::{
    Color32, Pos2, Rect, Vec2,
    epaint::{ClippedPrimitive, ClippedShape, ImageData, ImageDelta, TextureId},
};
use rustclient_plugin_host::configuration::ConfigValue;
use std::collections::BTreeMap;

#[derive(Default, Debug, Clone, Copy)]
pub struct EguiPanelStats {
    pub passes: u64,
    pub tessellations: u64,
    pub reused_inputs: u64,
}
/// Full, bounded texture snapshots make device reconstruction independent of delta history.
#[derive(Default)]
pub struct EguiPanelFrame {
    pub jobs: Vec<ClippedPrimitive>,
    pub textures: BTreeMap<TextureId, (ImageDelta, u64)>,
    pub generation: u64,
}
pub struct EguiPanel {
    ctx: egui::Context,
    channel: ShellChannel,
    input: egui::RawInput,
    modifiers: egui::Modifiers,
    size: [u32; 2],
    scale: f32,
    visible: bool,
    dirty: bool,
    deadline: Option<f64>,
    selected: Option<String>,
    query: String,
    drafts: BTreeMap<(String, String), ConfigValue>,
    text: BTreeMap<(String, String), String>,
    capture: Option<(String, String)>,
    hits: Vec<(Rect, egui::Id)>,
    pointer: Pos2,
    targets: BTreeMap<String, Rect>,
    shapes: Vec<ClippedShape>,
    frame: EguiPanelFrame,
    stats: EguiPanelStats,
    clipboard: Option<Box<dyn rustclient_platform::Clipboard>>,
    ime_cursor: Option<Rect>,
}
impl EguiPanel {
    /// # Errors
    /// Rejects invalid bounded canvas dimensions or scale.
    pub fn new(channel: ShellChannel, size: [u32; 2], scale: f32) -> Result<Self, String> {
        validate(size, scale)?;
        let ctx = egui::Context::default();
        let mut style = (*ctx.style_of(egui::Theme::Dark)).clone();
        style.visuals = egui::Visuals::dark();
        style.visuals.panel_fill = Color32::from_rgb(39, 39, 39);
        style.visuals.window_fill = style.visuals.panel_fill;
        style.visuals.selection.bg_fill = Color32::from_rgb(167, 94, 0);
        style.visuals.widgets.active.bg_fill = Color32::from_rgb(206, 123, 0);
        style.visuals.widgets.hovered.bg_fill = Color32::from_gray(58);
        style.visuals.widgets.noninteractive.bg_stroke = egui::Stroke::NONE;
        style.animation_time = 0.;
        style.spacing.item_spacing = Vec2::new(6., 5.);
        style.spacing.button_padding = Vec2::new(6., 5.);
        style.visuals.override_text_color = Some(Color32::from_gray(218));
        ctx.set_style_of(egui::Theme::Dark, style);
        ctx.set_theme(egui::ThemePreference::Dark);
        ctx.options_mut(|options| options.zoom_with_keyboard = false);
        Ok(Self {
            ctx,
            channel,
            input: egui::RawInput::default(),
            modifiers: egui::Modifiers::default(),
            size,
            scale,
            visible: false,
            dirty: true,
            deadline: None,
            selected: None,
            query: String::new(),
            drafts: BTreeMap::default(),
            text: BTreeMap::default(),
            capture: None,
            hits: Vec::new(),
            pointer: Pos2::ZERO,
            targets: BTreeMap::new(),
            shapes: Vec::new(),
            frame: EguiPanelFrame::default(),
            stats: EguiPanelStats::default(),
            clipboard: None,
            ime_cursor: None,
        })
    }
    pub fn set_visible(&mut self, visible: bool) {
        if self.visible && !visible {
            self.input.events.clear();
            self.clear_focus();
        }
        if visible && !self.visible {
            self.dirty = true;
        }
        self.visible = visible;
    }
    pub fn invalidate(&mut self) {
        self.dirty = true;
        self.drafts.clear();
        self.text.clear();
    }
    /// # Errors
    /// Rejects invalid extents before allocating or painting.
    pub fn resize(&mut self, size: [u32; 2], scale: f32) -> Result<(), String> {
        validate(size, scale)?;
        if self.size != size || self.scale.to_bits() != scale.to_bits() {
            self.size = size;
            self.scale = scale;
            self.dirty = true;
        }
        Ok(())
    }
    #[must_use]
    pub fn stats(&self) -> EguiPanelStats {
        self.stats
    }
    #[must_use]
    pub fn frame(&self) -> &EguiPanelFrame {
        &self.frame
    }
    #[must_use]
    pub fn ime_cursor(&self) -> Option<[f32; 4]> {
        self.ime_cursor
            .map(|rect| [rect.min.x, rect.min.y, rect.width(), rect.height()])
    }
    pub fn set_clipboard(&mut self, value: Box<dyn rustclient_platform::Clipboard>) {
        self.clipboard = Some(value);
    }
    /// One component/layout pass only when input, content or a requested appearance deadline changes.
    /// # Errors
    /// Rejects malformed or excessive toolkit image deltas before retaining/uploading them.
    #[expect(clippy::cast_precision_loss, reason = "bounded panel pixel dimensions")]
    pub fn render(&mut self, time: f64) -> Result<bool, String> {
        if !self.visible || (!self.dirty && !self.deadline.is_some_and(|deadline| time >= deadline))
        {
            return Ok(false);
        }
        self.input.time = Some(time);
        self.input.screen_rect = Some(Rect::from_min_size(
            Pos2::ZERO,
            Vec2::new(
                self.size[0] as f32 / self.scale,
                self.size[1] as f32 / self.scale,
            ),
        ));
        self.input.max_texture_side = Some(4096);
        self.input
            .viewports
            .entry(egui::ViewportId::ROOT)
            .or_default()
            .native_pixels_per_point = Some(self.scale);
        let ctx = self.ctx.clone();
        let channel = self.channel.clone();
        let model = channel.model.borrow();
        self.hits.clear();
        self.targets.clear();
        let previous = (
            self.selected.clone(),
            self.query.clone(),
            self.capture.clone(),
        );
        let output = ctx.run_ui(self.input.take(), |ui| self.content(ui, &model));
        self.stats.passes += output.platform_output.num_completed_passes as u64;
        self.ime_cursor = output
            .platform_output
            .ime
            .as_ref()
            .map(|ime| ime.cursor_rect);
        let delay = output
            .viewport_output
            .get(&egui::ViewportId::ROOT)
            .map(|view| view.repaint_delay);
        self.deadline = delay
            .filter(|delay| *delay != std::time::Duration::MAX)
            .map(|delay| time + delay.as_secs_f64());
        for command in &output.platform_output.commands {
            if let egui::OutputCommand::CopyText(text) = command
                && let Some(clipboard) = &mut self.clipboard
                && let Err(error) = clipboard.write_text(text.clone())
            {
                eprintln!("shell clipboard: {error}");
            }
        }
        let textures_changed = !output.textures_delta.is_empty();
        self.apply_textures(output.textures_delta)?;
        let changed = textures_changed || self.shapes != output.shapes;
        if changed {
            self.shapes = output.shapes;
            self.frame.jobs = ctx.tessellate(self.shapes.clone(), self.scale);
            self.frame.generation += 1;
            self.stats.tessellations += 1;
        }
        self.dirty = previous
            != (
                self.selected.clone(),
                self.query.clone(),
                self.capture.clone(),
            );
        Ok(changed)
    }
    fn content(&mut self, ui: &mut egui::Ui, model: &ShellModel) {
        ui.painter()
            .rect_filled(ui.max_rect(), 0., Color32::from_gray(39));
        let available = ui.available_size();
        ui.horizontal_top(|ui| {
            if model.sidebar == SidebarState::Panel {
                ui.allocate_ui_with_layout(
                    Vec2::new((available.x - 36.).max(1.), available.y),
                    egui::Layout::top_down(egui::Align::Min),
                    |ui| {
                        ui.set_width((available.x - 36.).max(1.));
                        self.panel(ui, model);
                    },
                );
            }
            ui.separator();
            ui.vertical(|ui| {
                ui.set_width(26.);
                let response = ui
                    .add_sized([26., 30.], egui::Button::new("⚙"))
                    .on_hover_text("Configuration");
                self.hit(&response);
                if response.clicked() {
                    self.channel.send(ShellAction::TogglePanel);
                }
            });
        });
    }
    #[expect(
        clippy::too_many_lines,
        reason = "one panel pass lays out navigation and schema-generated settings"
    )]
    fn panel(&mut self, ui: &mut egui::Ui, model: &ShellModel) {
        if let Some(id) = self.selected.clone()
            && let Some(plugin) = model.plugins.iter().find(|plugin| plugin.id == id)
        {
            ui.horizontal(|ui| {
                let back = ui.button("‹").on_hover_text("Back to plugins");
                self.hit(&back);
                if back.clicked() {
                    self.selected = None;
                }
                ui.strong(&plugin.name);
                if plugin.required {
                    ui.colored_label(Color32::from_rgb(242, 154, 35), "Always on");
                }
            });
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
                        egui::CollapsingHeader::new(section)
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
                    self.hit(&reset);
                    if reset.clicked() {
                        self.channel.send(ShellAction::Reset { plugin: id.clone() });
                    }
                });
        } else {
            ui.strong("Configuration");
            ui.separator();
            let search = ui.add_sized(
                [ui.available_width(), 26.],
                egui::TextEdit::singleline(&mut self.query).hint_text("Search plugins"),
            );
            self.hit(&search);
            self.targets.insert("search".into(), search.rect);
            let query = self.query.to_lowercase();
            let mut plugins: Vec<_> = model.plugins.iter().collect();
            plugins.sort_by(|a, b| {
                model
                    .pinned
                    .contains(&b.id)
                    .cmp(&model.pinned.contains(&a.id))
                    .then_with(|| a.name.to_lowercase().cmp(&b.name.to_lowercase()))
            });
            egui::ScrollArea::vertical()
                .id_salt("plugins")
                .show(ui, |ui| {
                    for plugin in plugins {
                        if !query.is_empty()
                            && !plugin.name.to_lowercase().contains(&query)
                            && !plugin
                                .tags
                                .iter()
                                .any(|tag| tag.to_lowercase().contains(&query))
                        {
                            continue;
                        }
                        ui.horizontal(|ui| {
                            let pinned = model.pinned.contains(&plugin.id);
                            let pin = ui
                                .small_button(if pinned { "★" } else { "☆" })
                                .on_hover_text("Favorite plugin");
                            self.hit(&pin);
                            if pin.clicked() {
                                self.channel.send(ShellAction::ToggleFavorite {
                                    plugin: plugin.id.clone(),
                                });
                            }
                            let response = ui
                                .add_sized(
                                    [(ui.available_width() - 35.).max(1.), 25.],
                                    egui::Button::new(&plugin.name).frame(false),
                                )
                                .on_hover_text(&plugin.description);
                            self.hit(&response);
                            self.targets
                                .insert(format!("plugin:{}", plugin.id), response.rect);
                            if response.clicked() {
                                self.selected = Some(plugin.id.clone());
                            }
                            if plugin.required {
                                ui.colored_label(Color32::from_rgb(239, 147, 20), "●")
                                    .on_hover_text("Required plugin: always on");
                            } else {
                                let mut enabled = plugin.enabled;
                                let response = ui.checkbox(&mut enabled, "");
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
                    }
                });
        }
        if let Some(error) = &model.error {
            ui.colored_label(Color32::LIGHT_RED, error);
        }
    }
    /// Logical control bounds for accessibility/diagnostics; never semantic geometry.
    #[must_use]
    pub fn target_bounds(&self, target: &str) -> Option<[f32; 4]> {
        self.targets
            .get(target)
            .map(|rect| [rect.min.x, rect.min.y, rect.width(), rect.height()])
    }
    fn editor(&mut self, key: &(String, String), response: &egui::Response) {
        self.targets
            .entry(format!("setting:{}:{}", key.0, key.1))
            .or_insert(response.rect);
        self.hit(response);
    }
    fn hit(&mut self, response: &egui::Response) {
        self.hits.push((response.rect, response.id));
    }
    fn apply_textures(&mut self, mut delta: egui::TexturesDelta) -> Result<(), String> {
        for (id, deltas) in delta.set.drain() {
            for delta in deltas {
                let ImageData::Color(image) = &delta.image;
                if image.size.contains(&0)
                    || image.size.iter().any(|side| *side > 4096)
                    || image.pixels.len() != image.size[0] * image.size[1]
                {
                    return Err("invalid shell image delta".into());
                }
                let generation = self
                    .frame
                    .textures
                    .get(&id)
                    .map_or(1, |(_, generation)| generation + 1);
                let image = if let Some([x, y]) = delta.pos {
                    let previous = self
                        .frame
                        .textures
                        .get(&id)
                        .ok_or("shell image patch has no base")?;
                    let ImageData::Color(base) = &previous.0.image;
                    let mut base = (**base).clone();
                    if x + image.size[0] > base.size[0] || y + image.size[1] > base.size[1] {
                        return Err("shell image patch out of bounds".into());
                    }
                    for row in 0..image.size[1] {
                        let start = (y + row) * base.size[0] + x;
                        base.pixels[start..start + image.size[0]].copy_from_slice(
                            &image.pixels[row * image.size[0]..(row + 1) * image.size[0]],
                        );
                    }
                    ImageData::Color(std::sync::Arc::new(base))
                } else {
                    delta.image
                };
                self.frame
                    .textures
                    .insert(id, (ImageDelta::full(image, delta.options), generation));
            }
        }
        for id in delta.free.drain() {
            self.frame.textures.remove(&id);
        }
        if self.frame.textures.len() > 16
            || self
                .frame
                .textures
                .values()
                .map(|(image, _)| image.image.width() * image.image.height() * 4)
                .sum::<usize>()
                > 64 * 1024 * 1024
        {
            return Err("shell texture budget exceeded".into());
        }
        Ok(())
    }
}
fn validate(size: [u32; 2], scale: f32) -> Result<(), String> {
    if size.contains(&0)
        || size.iter().any(|side| *side > 4096)
        || !scale.is_finite()
        || scale <= 0.
        || scale > 8.
    {
        return Err("invalid GPU shell extent".into());
    }
    Ok(())
}
