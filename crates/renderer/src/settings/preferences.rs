//! Typed adapter from accepted scalar preferences to renderer-owned settings.
use super::{AntiAliasing, ColorBlind, GpuSettings, VsyncMode};
use crate::MaterialOptions;
/// Borrowed accepted preference; no plugin host or UI dependency enters the renderer.
pub enum GpuPreference<'a> {
    Boolean(bool),
    Integer(i32),
    Choice(&'a str),
}
impl GpuSettings {
    /// Project source-keyed accepted preferences. Unknown/mistyped fields retain source defaults.
    #[must_use]
    pub fn from_preferences<'a>(value: impl Fn(&str) -> Option<GpuPreference<'a>>) -> Self {
        let mut settings = GpuSettings::default();
        let boolean = |key| match value(key) {
            Some(GpuPreference::Boolean(value)) => Some(value),
            _ => None,
        };
        let integer = |key| match value(key) {
            Some(GpuPreference::Integer(value)) => u32::try_from(value).ok(),
            _ => None,
        };
        let choice = |key| match value(key) {
            Some(GpuPreference::Choice(value)) => Some(value),
            _ => None,
        };
        settings.material = MaterialOptions {
            smooth_banding: boolean("smoothBanding").unwrap_or(true),
            bright_textures: boolean("brightTextures").unwrap_or(false),
        };
        settings.draw_distance = integer("drawDistance").unwrap_or(settings.draw_distance);
        settings.hide_unrelated_maps =
            boolean("hideUnrelatedMaps").unwrap_or(settings.hide_unrelated_maps);
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
        settings.ui_scaling = match choice("uiScalingMode") {
            Some("NEAREST") => crate::UiScaling::Nearest,
            Some("LINEAR") => crate::UiScaling::Linear,
            Some("MITCHELL") => crate::UiScaling::Mitchell,
            Some("CATMULL_ROM") => crate::UiScaling::CatmullRom,
            Some("XBR") => crate::UiScaling::Xbr,
            _ => crate::UiScaling::Hybrid,
        };
        settings.vsync = match choice("vsyncMode") {
            Some("ON") => VsyncMode::On,
            Some("ADAPTIVE") => VsyncMode::Adaptive,
            _ => VsyncMode::Off,
        };
        settings
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn shared_adapter_preserves_source_defaults_and_typed_preferences() {
        assert_eq!(
            GpuSettings::from_preferences(|_| None),
            GpuSettings::default()
        );
        let settings = GpuSettings::from_preferences(|key| match key {
            "drawDistance" => Some(GpuPreference::Integer(64)),
            "antiAliasingMode" => Some(GpuPreference::Choice("MSAA_8")),
            "unlockFps" => Some(GpuPreference::Boolean(false)),
            "fogDepth" => Some(GpuPreference::Choice("mistyped")),
            _ => None,
        });
        assert_eq!(settings.draw_distance, 64);
        assert_eq!(settings.anti_aliasing, AntiAliasing::Msaa8);
        assert!(!settings.unlock_fps);
        assert_eq!(settings.fog_depth, GpuSettings::default().fog_depth);
        let effective = settings.resolve(&super::super::GpuCapabilities::default());
        assert_eq!(effective.frame_cap, 50);
        assert!(
            effective
                .deviations
                .iter()
                .any(|d| d.key == "antiAliasingMode")
        );
    }
}
