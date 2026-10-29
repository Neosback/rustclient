//! Projection of accepted configuration into native effects and availability notes.
use rustclient_application::builtin_config::value;
use rustclient_plugin_host::configuration::{ConfigDocument, ConfigSchema, ConfigValue};
use rustclient_renderer::{EffectiveGpu, GpuSettings};
use std::collections::BTreeMap;
use winit::window::{Window, WindowLevel};

pub(super) use rustclient_application::builtin_config::availability;
/// Typed projection of the accepted `dev.rustclient.gpu` document; unknown or mistyped values keep defaults.
pub(super) fn gpu_settings(schema: &ConfigSchema, document: &ConfigDocument) -> GpuSettings {
    GpuSettings::from_preferences(|key| match value(schema, document, key) {
        Some(ConfigValue::Boolean(value)) => {
            Some(rustclient_renderer::GpuPreference::Boolean(*value))
        }
        Some(ConfigValue::Integer(value)) => {
            Some(rustclient_renderer::GpuPreference::Integer(*value))
        }
        Some(ConfigValue::Choice(value)) => Some(rustclient_renderer::GpuPreference::Choice(value)),
        _ => None,
    })
}

pub(super) fn notes(effective: &EffectiveGpu) -> BTreeMap<String, String> {
    effective
        .deviations
        .iter()
        .map(|deviation| (deviation.key.to_owned(), deviation.reason.clone()))
        .collect()
}
/// Behavior that holds on every adapter but that the option's name does not promise.
pub(super) fn static_note(key: &str) -> Option<String> {
    (key == "drawDistance").then(|| {
        "Uses the revision-240 inclusive eight-tile zone square; fog still uses the requested tile distance."
            .into()
    })
}
pub(super) fn apply_effect(schema: &ConfigSchema, document: &ConfigDocument, window: &Window) {
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
