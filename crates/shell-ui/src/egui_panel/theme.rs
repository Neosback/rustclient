//! Owned reference fonts/assets and shared native/browser chrome.
use crate::SidebarMetrics;
use egui::{Color32, FontData, FontFamily, FontId, TextureHandle, Vec2};
use std::{collections::BTreeMap, sync::Arc};
pub(super) const PANEL: Color32 = Color32::from_rgb(40, 40, 40);
pub(super) const RAIL: Color32 = Color32::from_rgb(30, 30, 30);
pub(super) const ORANGE: Color32 = Color32::from_rgb(220, 138, 0);
pub(super) struct Assets {
    images: BTreeMap<&'static str, TextureHandle>,
}
impl Assets {
    pub fn new(ctx: &egui::Context) -> Result<Self, String> {
        let mut fonts = egui::FontDefinitions::default();
        for (name, bytes) in [
            (
                "runelite",
                include_bytes!("../../assets/runelite/runescape.ttf").as_slice(),
            ),
            (
                "runelite-bold",
                include_bytes!("../../assets/runelite/runescape_bold.ttf").as_slice(),
            ),
            (
                "runelite-small",
                include_bytes!("../../assets/runelite/runescape_small.ttf").as_slice(),
            ),
        ] {
            fonts
                .font_data
                .insert(name.into(), Arc::new(FontData::from_static(bytes)));
            fonts
                .families
                .insert(FontFamily::Name(name.into()), vec![name.into()]);
        }
        fonts
            .families
            .entry(FontFamily::Proportional)
            .or_default()
            .insert(0, "runelite".into());
        ctx.set_fonts(fonts);
        let mut style = egui::Style {
            visuals: egui::Visuals::dark(),
            ..Default::default()
        };
        style.visuals.panel_fill = PANEL;
        style.visuals.window_fill = PANEL;
        style.visuals.override_text_color = Some(Color32::WHITE);
        style.visuals.selection.bg_fill = ORANGE;
        style.visuals.widgets.active.bg_fill = ORANGE;
        style.visuals.widgets.hovered.bg_fill = Color32::from_gray(60);
        style.visuals.widgets.inactive.bg_fill = RAIL;
        style.visuals.widgets.inactive.weak_bg_fill = RAIL;
        style.visuals.widgets.noninteractive.bg_stroke = egui::Stroke::NONE;
        style.visuals.widgets.inactive.corner_radius = egui::CornerRadius::ZERO;
        style.visuals.widgets.hovered.corner_radius = egui::CornerRadius::ZERO;
        style.visuals.widgets.active.corner_radius = egui::CornerRadius::ZERO;
        style.spacing.item_spacing = Vec2::new(4., 3.);
        style.spacing.button_padding = Vec2::new(3., 3.);
        style.spacing.interact_size = Vec2::new(25., 24.);
        style.spacing.scroll.bar_width = 7.;
        style.spacing.scroll.floating = false;
        style.spacing.slider_width = 132.;
        style.animation_time = 0.;
        for text in [
            egui::TextStyle::Body,
            egui::TextStyle::Button,
            egui::TextStyle::Monospace,
            egui::TextStyle::Small,
        ] {
            style.text_styles.insert(
                text,
                FontId::new(SidebarMetrics::FONT, FontFamily::Proportional),
            );
        }
        style.text_styles.insert(
            egui::TextStyle::Heading,
            FontId::new(16., FontFamily::Name("runelite-bold".into())),
        );
        ctx.set_style_of(egui::Theme::Dark, style);
        ctx.set_theme(egui::ThemePreference::Dark);
        ctx.options_mut(|options| options.zoom_with_keyboard = false);
        let mut images = BTreeMap::new();
        for (name, bytes) in crate::assets::ICONS {
            let image = image::load_from_memory(bytes)
                .map_err(|e| format!("sidebar icon {name}: {e}"))?
                .into_rgba8();
            let size = [image.width() as usize, image.height() as usize];
            let handle = ctx.load_texture(
                *name,
                egui::ColorImage::from_rgba_unmultiplied(size, image.as_raw()),
                egui::TextureOptions::NEAREST,
            );
            images.insert(*name, handle);
        }
        Ok(Self { images })
    }
    pub fn size(&self, name: &str) -> Vec2 {
        self.images[name].size_vec2()
    }
    pub fn image(&self, name: &str, size: Vec2) -> egui::Image<'static> {
        egui::Image::new((self.images[name].id(), size))
    }
    pub fn button(&self, ui: &mut egui::Ui, name: &str, label: &str) -> egui::Response {
        let size = match name {
            "star_on" | "star_off" | "config_edit_icon" => Vec2::splat(18.),
            "config_back_icon" => Vec2::new(12., 18.),
            _ => Vec2::splat(SidebarMetrics::ICON),
        };
        let width = if name.starts_with("star_") { 21. } else { 25. };
        let response = ui
            .add_sized(
                [width, 20.],
                egui::Button::new(self.image(name, size)).frame(false),
            )
            .on_hover_text(label);
        if response.clicked() {
            response.request_focus();
        }
        response
    }
    pub fn toggle(&self, ui: &mut egui::Ui, value: &mut bool) -> egui::Response {
        let mut response = ui.add_sized(
            [25., 20.],
            egui::Button::new(self.image(
                if *value { "switch_on" } else { "switch_off" },
                Vec2::new(25., 15.),
            ))
            .frame(false),
        );
        if response.clicked() {
            response.request_focus();
            *value = !*value;
            response.mark_changed();
        }
        response
    }
}
