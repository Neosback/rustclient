use rustclient_plugin_host::configuration::{
    ConfigValue, Configuration, ENABLED_KEY, PluginConfig,
};
use rustclient_plugin_host::required::{all_schemas, first_party_schemas};
use std::collections::BTreeSet;

#[test]
fn every_first_party_schema_validates_with_unique_keys_and_a_source() {
    let schemas = first_party_schemas().expect("embedded first-party schemas parse");
    assert_eq!(schemas.len(), 13);
    let mut plugins = BTreeSet::new();
    for schema in schemas {
        assert!(!schema.required, "{} must be optional", schema.plugin);
        assert!(
            plugins.insert(schema.plugin.clone()),
            "duplicate {}",
            schema.plugin
        );
        assert_eq!(schema.settings[0].key, ENABLED_KEY, "{}", schema.plugin);
        for setting in &schema.settings {
            assert!(
                setting
                    .source
                    .starts_with("references/open-runelite-rev-240/"),
                "{}.{} has no RuneLite source",
                schema.plugin,
                setting.key
            );
        }
        let configuration = Configuration::new(schema.clone(), None).expect("defaults validate");
        assert_eq!(
            configuration.enabled(),
            matches!(schema.settings[0].default, ConfigValue::Boolean(true))
        );
    }
}

#[test]
fn required_and_first_party_schemas_do_not_collide() {
    let all = all_schemas().expect("embedded schemas parse");
    let ids: BTreeSet<_> = all.iter().map(|schema| schema.plugin.clone()).collect();
    assert_eq!(ids.len(), all.len());
    assert!(all.iter().take(2).all(|schema| schema.required));
}

#[test]
fn plugin_config_reads_source_defaults_and_ignores_mistyped_access() {
    let roof = first_party_schemas()
        .unwrap()
        .into_iter()
        .find(|schema| schema.plugin.as_str() == "dev.rustclient.roofremoval")
        .unwrap();
    let config = PluginConfig::of(&Configuration::new(roof, None).unwrap());
    // Source defaults: every mode on, the POH override off.
    assert!(config.boolean("removePosition"));
    assert!(config.boolean("removeBetween"));
    assert!(!config.boolean("overridePOH"));
    assert_eq!(config.integer("removePosition"), 0);
    assert_eq!(config.choice("missing"), "");
    assert!(config.hotkey("removePosition").is_none());
}
