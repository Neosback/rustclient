//! Demand-driven native/WebGPU presentation. Accepted configuration stays in plugin-host.
mod controls;
mod input;
mod layout;
mod theme;
use crate::{ShellAction, ShellChannel, ShellModel, SidebarMetrics, SidebarState};
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
    assets: theme::Assets,
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
    copied_text: Option<String>,
}
impl EguiPanel {
    /// # Errors
    /// Rejects invalid bounded canvas dimensions or scale.
    pub fn new(channel: ShellChannel, size: [u32; 2], scale: f32) -> Result<Self, String> {
        validate(size, scale)?;
        let ctx = egui::Context::default();
        let assets = theme::Assets::new(&ctx)?;
        Ok(Self {
            ctx,
            assets,
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
            copied_text: None,
        })
    }
    pub fn set_visible(&mut self, visible: bool) {
        if self.visible && !visible {
            self.input.events.clear();
            self.clear_focus();
            self.targets.clear();
            self.hits.clear();
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
            if let egui::OutputCommand::CopyText(text) = command {
                if let Some(clipboard) = &mut self.clipboard {
                    if let Err(error) = clipboard.write_text(text.clone()) {
                        eprintln!("shell clipboard: {error}");
                    }
                } else {
                    self.copied_text = Some(text.clone());
                }
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
    /// Platform adapter deadline. World frames never force a UI pass.
    #[must_use]
    pub fn repaint_in(&self, time: f64) -> Option<std::time::Duration> {
        self.visible
            .then_some(self.deadline)
            .flatten()
            .map(|deadline| std::time::Duration::from_secs_f64((deadline - time).max(0.)))
    }
    #[must_use]
    pub fn wants_keyboard_input(&self) -> bool {
        self.visible && self.ctx.egui_wants_keyboard_input()
    }
    pub fn paste(&mut self, text: String) {
        if self.visible {
            self.input.events.push(egui::Event::Paste(text));
            self.dirty = true;
        }
    }
    pub fn clipboard_event(&mut self, cut: bool) {
        if self.visible {
            self.input.events.push(if cut {
                egui::Event::Cut
            } else {
                egui::Event::Copy
            });
            self.dirty = true;
        }
    }
    pub fn take_copied_text(&mut self) -> Option<String> {
        self.copied_text.take()
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
