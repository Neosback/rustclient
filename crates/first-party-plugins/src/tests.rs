use super::*;
use rustclient_plugin_api::{GameSnapshot, RoofModes};
use rustclient_plugin_host::{
    HostOutput, MemoryLogSink, MemoryStorage, PluginConfig, SnapshotView, StaticView,
    configuration::{ConfigValue, Configuration},
};

/// A set holding every first-party plugin plus the helpers to configure and run it.
pub(crate) struct Harness {
    pub set: PluginSet,
    storage: MemoryStorage,
    logs: MemoryLogSink,
}

impl Harness {
    pub(crate) fn new() -> Self {
        let mut set = PluginSet::new();
        register_all(&mut set).expect("first-party plugins register");
        Self {
            set,
            storage: MemoryStorage::new(),
            logs: MemoryLogSink::default(),
        }
    }

    /// Configure `plugin` from its schema defaults overridden by `values`, and switch it on.
    pub(crate) fn enable(&mut self, plugin: &str, values: &[(&str, ConfigValue)]) {
        let id = PluginId::parse(&format!("dev.rustclient.{plugin}")).unwrap();
        let schema = first_party_schemas()
            .unwrap()
            .into_iter()
            .find(|schema| schema.plugin == id)
            .unwrap();
        let mut configuration = Configuration::new(schema, None).unwrap();
        for (key, value) in values {
            configuration
                .change(key, value.clone(), |_, _| Ok(()))
                .unwrap_or_else(|error| panic!("{key}: {error}"));
        }
        self.set
            .configure(
                &id,
                PluginConfig::of(&configuration),
                true,
                &StaticView::default(),
                &mut self.storage,
                &mut self.logs,
            )
            .unwrap();
    }

    pub(crate) fn output(&mut self, snapshot: &GameSnapshot) -> HostOutput {
        self.set
            .collect_output(&SnapshotView(snapshot), &mut self.storage, &mut self.logs)
    }
}

#[test]
fn every_plugin_is_registered_off_until_configured() {
    let harness = Harness::new();
    for entry in ENTRIES {
        let id = format!("dev.rustclient.{}", entry.name);
        let plugin = harness
            .set
            .iter()
            .find(|plugin| plugin.manifest.id.as_str() == id)
            .unwrap_or_else(|| panic!("{id} is not registered"));
        assert!(!plugin.enabled(), "{id}");
    }
}

#[test]
fn roofremoval_requests_the_source_default_modes_once_enabled() {
    let mut harness = Harness::new();
    // A plugin nobody has switched on produces nothing.
    let snapshot = GameSnapshot::default();
    assert_eq!(harness.output(&snapshot).scene.roofs, RoofModes::default());
    harness.enable("roofremoval", &[]);
    let all =
        RoofModes::POSITION | RoofModes::HOVERED | RoofModes::DESTINATION | RoofModes::BETWEEN;
    assert_eq!(harness.output(&snapshot).scene.roofs, RoofModes::new(all));
}

#[test]
fn roofremoval_follows_each_mode_switch() {
    let mut harness = Harness::new();
    harness.enable(
        "roofremoval",
        &[
            ("removeHovered", ConfigValue::Boolean(false)),
            ("removeBetween", ConfigValue::Boolean(false)),
        ],
    );
    assert_eq!(
        harness.output(&GameSnapshot::default()).scene.roofs,
        RoofModes::new(RoofModes::POSITION | RoofModes::DESTINATION)
    );
}

#[test]
fn roofremoval_reports_override_areas_only_for_a_loaded_scene() {
    use rustclient_plugin_api::{ClientSnapshot, GameStateKind};
    let mut harness = Harness::new();
    harness.enable("roofremoval", &[]);
    let smoke_dungeon = ((12946 / 256) * 64, (12946 % 256) * 64);
    let snapshot = |state| GameSnapshot {
        client: ClientSnapshot {
            state,
            scene_base: smoke_dungeon,
            scene_size: (64, 64),
            ..ClientSnapshot::default()
        },
        ..GameSnapshot::default()
    };
    // At the login screen no scene exists, so there is nothing to override.
    assert!(
        harness
            .output(&snapshot(GameStateKind::LoginScreen))
            .roof_areas
            .is_empty()
    );
    let loaded = harness.output(&snapshot(GameStateKind::LoggedIn));
    assert_eq!(loaded.roof_areas.len(), 1);
    assert_eq!(loaded.roof_areas[0].min, smoke_dungeon);
}
