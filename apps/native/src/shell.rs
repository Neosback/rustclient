//! Composition of application preferences, retained DOM pixels and engine-owned effects.
use rustclient_application::builtin_config::{BuiltinConfigurations, value};
use rustclient_platform::BlobStorage;
use rustclient_plugin_host::configuration::{ConfigDocument, ConfigSchema, ConfigValue};
use rustclient_renderer::{
    AntiAliasing, ColorBlind, EffectiveGpu, GpuSettings, MaterialOptions, ShellLayer,
    SurfaceRenderer, SurfaceSize, VsyncMode,
};
use rustclient_shell_ui::{PluginPanel, RetainedPanel, ShellAction, ShellChannel};
use std::collections::BTreeMap;
use winit::{
    dpi::{LogicalSize, PhysicalSize},
    window::{Window, WindowLevel},
};

#[expect(
    clippy::struct_excessive_bools,
    reason = "independent input-routing and visibility flags"
)]
pub(super) struct Shell {
    pub panel: RetainedPanel,
    channel: ShellChannel,
    configuration: BuiltinConfigurations,
    pub focused: bool,
    pub held: bool,
    pub composing: bool,
    visible: bool,
    expanded: bool,
    /// Why an applied GPU option differs from the request on this adapter.
    gpu_notes: BTreeMap<String, String>,
}
impl Shell {
    pub fn new(storage: Option<&dyn BlobStorage>) -> Result<Self, String> {
        let (configuration, diagnostics) = BuiltinConfigurations::load_recovering(storage)?;
        for diagnostic in &diagnostics {
            eprintln!("required plugin configuration: {diagnostic}");
        }
        let channel = ShellChannel::default();
        channel.model.borrow_mut().panel_visible = false;
        channel.model.borrow_mut().error =
            (!diagnostics.is_empty()).then(|| diagnostics.join("\n"));
        let mut panel = RetainedPanel::new(channel.clone(), [48, 503], 1.)?;
        match rustclient_platform_native::NativeClipboard::new() {
            Ok(clipboard) => panel.set_clipboard(Box::new(clipboard)),
            Err(error) => eprintln!("shell clipboard unavailable: {error}"),
        }
        panel.set_visible(true);
        let shell = Self {
            panel,
            channel,
            configuration,
            focused: false,
            held: false,
            composing: false,
            visible: true,
            expanded: false,
            gpu_notes: BTreeMap::new(),
        };
        shell.project();
        Ok(shell)
    }
    /// Every accepted plugin configuration, required services first.
    pub fn configurations(
        &self,
    ) -> impl Iterator<Item = &rustclient_plugin_host::configuration::Configuration> {
        self.configuration.iter()
    }
    pub fn width(&self) -> u32 {
        if !self.visible {
            0
        } else if self.expanded {
            370
        } else {
            48
        }
    }
    #[expect(
        clippy::cast_possible_truncation,
        clippy::cast_sign_loss,
        reason = "scale and window extents are validated platform values"
    )]
    pub fn game_size(&self, window: &Window) -> PhysicalSize<u32> {
        let size = window.inner_size();
        let sidebar = (f64::from(self.width()) * window.scale_factor()).round() as u32;
        PhysicalSize::new(size.width.saturating_sub(sidebar).max(1), size.height)
    }
    /// Physical window coordinates use the exact same split as surface composition and game picking.
    #[expect(
        clippy::cast_possible_truncation,
        reason = "physical coordinates converted to bounded logical DOM coordinates"
    )]
    pub fn point(&self, window: &Window, x: f64, y: f64) -> Option<[f32; 2]> {
        if !self.visible {
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
    #[expect(
        clippy::cast_possible_truncation,
        reason = "validated bounded shell extent and platform scale"
    )]
    pub fn stage(&mut self, window: &Window, renderer: &mut SurfaceRenderer) -> Result<(), String> {
        let game = self.game_size(window);
        renderer.set_game_width(Some(game.width));
        self.panel.set_visible(self.visible);
        if !self.visible {
            return renderer
                .set_shell_layer(None)
                .map_err(|error| error.to_string());
        }
        let size = [
            window.inner_size().width.saturating_sub(game.width).max(1),
            window.inner_size().height,
        ];
        if size[1] == 0 {
            return Ok(());
        }
        self.panel.resize(size, window.scale_factor() as f32)?;
        self.panel.render();
        renderer
            .set_shell_layer(Some(ShellLayer {
                pixels: self.panel.pixels(),
                size: SurfaceSize::new(size[0], size[1]),
                generation: self.panel.stats().rasterizations,
                origin: [game.width, 0],
            }))
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
    pub fn actions(
        &mut self,
        window: &Window,
        renderer: Option<&mut SurfaceRenderer>,
        storage: Option<&dyn BlobStorage>,
    ) {
        let mut renderer = renderer;
        for action in self.channel.drain() {
            let mut new_notes = None;
            let result =
                match action {
                    ShellAction::Open => {
                        self.resize(window, true, true);
                        Ok(())
                    }
                    ShellAction::Close => {
                        self.resize(window, true, false);
                        Ok(())
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
                                .map(|_| ())
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
                                .map(|_| ())
                        } else {
                            Err("Settings storage is unavailable".into())
                        }
                    }
                };
            if let Some(new_notes) = new_notes {
                self.gpu_notes = new_notes;
            }
            self.channel.model.borrow_mut().error = result.err();
            self.project();
            self.panel.invalidate();
            window.request_redraw();
        }
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
        self.resize(window, !self.visible, self.expanded);
    }
    pub fn toggle_panel(&mut self, window: &Window) {
        self.resize(window, true, !self.expanded);
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
    fn resize(&mut self, window: &Window, visible: bool, expanded: bool) {
        let before = self.width();
        self.visible = visible;
        self.expanded = expanded;
        self.focused = false;
        self.held = false;
        self.panel.clear_focus();
        self.channel.model.borrow_mut().panel_visible = expanded;
        self.panel.invalidate();
        let after = self.width();
        let config = self
            .configuration
            .get("dev.rustclient.runelite")
            .expect("embedded RuneLite schema");
        let keep_game = matches!(config.value("automaticResizeType"),Some(ConfigValue::Choice(value)) if value=="KEEP_GAME_SIZE");
        window.set_min_inner_size(Some(LogicalSize::new(765. + f64::from(after), 503.)));
        if keep_game && window.fullscreen().is_none() && !window.is_maximized() {
            let size = window.inner_size().to_logical::<f64>(window.scale_factor());
            let _ = window.request_inner_size(LogicalSize::new(
                (size.width + f64::from(after) - f64::from(before)).max(765. + f64::from(after)),
                size.height,
            ));
        }
        window.request_redraw();
    }
}
fn availability(plugin: &str, key: &str) -> Option<String> {
    if key == rustclient_plugin_host::configuration::ENABLED_KEY {
        return None;
    }
    let implemented = match plugin {
        "dev.rustclient.roofremoval" => matches!(
            key,
            "removePosition" | "removeHovered" | "removeDestination" | "removeBetween"
        ),
        "dev.rustclient.gpu" => matches!(
            key,
            "drawDistance"
                | "smoothBanding"
                | "brightTextures"
                | "antiAliasingMode"
                | "fogDepth"
                | "anisotropicFilteringLevel"
                | "colorBlindMode"
                | "colorBlindIntensity"
                | "vsyncMode"
        ),
        "dev.rustclient.runelite" => matches!(
            key,
            "automaticResizeType"
                | "lockWindowSize"
                | "gameAlwaysOnTop"
                | "sidebarToggleKey"
                | "panelToggleKey"
                | "blockExtraMouseButtons"
        ),
        _ => false,
    };
    (!implemented).then(|| pending_reason(plugin, key))
}
fn pending_reason(plugin: &str, key: &str) -> String {
    match (plugin, key) {
        ("dev.rustclient.gpu", "hideUnrelatedMaps" | "expandedMapLoadingChunks") => {
            "Needs neighbouring map regions to be loaded; not ported yet.".into()
        }
        ("dev.rustclient.roofremoval", "overridePOH") => {
            "Needs the player-owned-house roof override data, which is not ported yet.".into()
        }
        ("dev.rustclient.gpu", "uiScalingMode") => {
            "Applies to stretched-mode scaling, which is not ported yet.".into()
        }
        ("dev.rustclient.gpu", "unlockFps" | "fpsTarget") => {
            "Frames still render once per 20 ms game cycle; unlocked-frame interpolation is not ported yet."
                .into()
        }
        ("dev.rustclient.gpu", "removeVertexSnapping" | "numThreads") => {
            "Belongs to RuneLite's zone uploader, which this renderer does not have yet.".into()
        }
        _ => "Effect port pending; source default shown".into(),
    }
}
/// Typed projection of the accepted `dev.rustclient.gpu` document; unknown or mistyped values keep defaults.
fn gpu_settings(schema: &ConfigSchema, document: &ConfigDocument) -> GpuSettings {
    let mut settings = GpuSettings::default();
    let boolean = |key| match value(schema, document, key) {
        Some(ConfigValue::Boolean(value)) => Some(*value),
        _ => None,
    };
    let integer = |key| match value(schema, document, key) {
        Some(ConfigValue::Integer(value)) => u32::try_from(*value).ok(),
        _ => None,
    };
    let choice = |key| match value(schema, document, key) {
        Some(ConfigValue::Choice(value)) => Some(value.as_str()),
        _ => None,
    };
    settings.material = MaterialOptions {
        smooth_banding: boolean("smoothBanding").unwrap_or(true),
        bright_textures: boolean("brightTextures").unwrap_or(false),
    };
    settings.draw_distance = integer("drawDistance").unwrap_or(settings.draw_distance);
    settings.fog_depth = integer("fogDepth").unwrap_or(settings.fog_depth);
    settings.anisotropy = integer("anisotropicFilteringLevel").unwrap_or(settings.anisotropy);
    settings.color_blind_intensity =
        integer("colorBlindIntensity").unwrap_or(settings.color_blind_intensity);
    settings.unlock_fps = boolean("unlockFps").unwrap_or(settings.unlock_fps);
    settings.fps_target = integer("fpsTarget").unwrap_or(settings.fps_target);
    settings.anti_aliasing = match choice("antiAliasingMode") {
        Some("DISABLED") => AntiAliasing::Disabled,
        Some("MSAA_4") => AntiAliasing::Msaa4,
        Some("MSAA_8") => AntiAliasing::Msaa8,
        Some("MSAA_16") => AntiAliasing::Msaa16,
        _ => AntiAliasing::Msaa2,
    };
    settings.color_blind = match choice("colorBlindMode") {
        Some("PROTANOPIA") => ColorBlind::Protanopia,
        Some("DEUTERANOPIA") => ColorBlind::Deuteranopia,
        Some("TRITANOPIA") => ColorBlind::Tritanopia,
        _ => ColorBlind::None,
    };
    settings.vsync = match choice("vsyncMode") {
        Some("ON") => VsyncMode::On,
        Some("ADAPTIVE") => VsyncMode::Adaptive,
        _ => VsyncMode::Off,
    };
    settings
}
fn notes(effective: &EffectiveGpu) -> BTreeMap<String, String> {
    effective
        .deviations
        .iter()
        .map(|deviation| (deviation.key.to_owned(), deviation.reason.clone()))
        .collect()
}
/// Behavior that holds on every adapter but that the option's name does not promise.
fn static_note(key: &str) -> Option<String> {
    (key == "drawDistance").then(|| {
        "Sets the fog boundary; geometry beyond it is still drawn until zone culling is ported."
            .into()
    })
}
fn apply_effect(schema: &ConfigSchema, document: &ConfigDocument, window: &Window) {
    if schema.plugin.as_str() == "dev.rustclient.runelite" {
        window.set_resizable(
            value(schema, document, "lockWindowSize") != Some(&ConfigValue::Boolean(true)),
        );
        window.set_window_level(
            if value(schema, document, "gameAlwaysOnTop") == Some(&ConfigValue::Boolean(true)) {
                WindowLevel::AlwaysOnTop
            } else {
                WindowLevel::Normal
            },
        );
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use rustclient_plugin_host::configuration::Configuration;

    fn gpu_configuration() -> Configuration {
        let schema = rustclient_plugin_host::required::schemas()
            .expect("embedded schemas")
            .into_iter()
            .find(|schema| schema.plugin.as_str() == "dev.rustclient.gpu")
            .expect("embedded GPU schema");
        Configuration::new(schema, None).expect("defaults validate")
    }

    #[test]
    fn schema_defaults_equal_the_renderer_source_defaults() {
        let config = gpu_configuration();
        assert_eq!(
            gpu_settings(config.schema(), config.document()),
            GpuSettings::default()
        );
    }

    #[test]
    fn every_gpu_option_is_either_applied_or_explains_why_not() {
        let config = gpu_configuration();
        for setting in &config.schema().settings {
            let reason = availability("dev.rustclient.gpu", &setting.key);
            assert!(
                reason.as_deref() != Some("Effect port pending; source default shown"),
                "{} has no specific pending reason",
                setting.key
            );
        }
    }
}
