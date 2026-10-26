use super::*;
use rustclient_plugin_api::{AnimationSmoothing, GameSnapshot, RoofModes, Stretch, StretchScaling};
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

#[test]
fn animsmoothing_blends_every_class_except_the_rejected_animations_once_enabled() {
    let mut harness = Harness::new();
    let snapshot = GameSnapshot::default();
    let off = harness.output(&snapshot);
    assert_eq!(off.scene.smoothing, AnimationSmoothing::default());
    assert!(off.smoothing_denied.is_empty());
    harness.enable("animsmoothing", &[]);
    let on = harness.output(&snapshot);
    assert!(on.scene.smoothing.contains(
        AnimationSmoothing::PLAYERS
            | AnimationSmoothing::NPCS
            | AnimationSmoothing::OBJECTS
            | AnimationSmoothing::WIDGETS
    ));
    assert_eq!(on.smoothing_denied.len(), 23);
    // Wyrm melee attack and the Varrock mill sail are rejected by the source predicate.
    assert!(on.smoothing_denied.contains(&8270));
    assert!(on.smoothing_denied.contains(&6495));
    assert!(!on.smoothing_denied.contains(&422));
}

#[test]
fn stretchedmode_forwards_its_four_settings_once_enabled() {
    let mut harness = Harness::new();
    let snapshot = GameSnapshot::default();
    assert_eq!(harness.output(&snapshot).stretch, None);
    harness.enable("stretchedmode", &[]);
    assert_eq!(
        harness.output(&snapshot).stretch,
        Some(Stretch {
            keep_aspect_ratio: false,
            integer_scaling: false,
            scale_percent: 50,
            scaling: StretchScaling::Linear,
        })
    );
    harness.enable(
        "stretchedmode",
        &[
            ("keepAspectRatio", ConfigValue::Boolean(true)),
            ("integerScaling", ConfigValue::Boolean(true)),
            ("increasedPerformance", ConfigValue::Boolean(true)),
            ("scalingFactor", ConfigValue::Integer(-30)),
        ],
    );
    assert_eq!(
        harness.output(&snapshot).stretch,
        Some(Stretch {
            keep_aspect_ratio: true,
            integer_scaling: true,
            scale_percent: -30,
            scaling: StretchScaling::Nearest,
        })
    );
}

#[test]
fn stretchedmode_bounds_an_absurd_scaling_factor() {
    let mut harness = Harness::new();
    harness.enable(
        "stretchedmode",
        &[("scalingFactor", ConfigValue::Integer(i32::MAX))],
    );
    assert_eq!(
        harness
            .output(&GameSnapshot::default())
            .stretch
            .map(|stretch| stretch.scale_percent),
        Some(Stretch::MAX_SCALE_PERCENT)
    );
}

fn actor(
    index: u16,
    interacting: Option<rustclient_plugin_api::ActorRef>,
) -> rustclient_plugin_api::ActorState {
    rustclient_plugin_api::ActorState {
        index,
        world: rustclient_plugin_api::WorldPoint {
            x: 0,
            y: 0,
            plane: 0,
        },
        orientation: 0,
        animation: None,
        pose: None,
        graphic: None,
        overhead_text: None,
        interacting,
        hitsplats: Vec::new(),
        healthbars: Vec::new(),
    }
}

#[test]
fn entityhider_hides_other_players_by_default_but_never_the_local_player() {
    use rustclient_plugin_api::{ActorRef, NpcSnapshot, PlayerSnapshot};
    let player = |index, name: &str, is_local| PlayerSnapshot {
        actor: actor(index, None),
        name: name.into(),
        combat_level: 3,
        skill_level: 0,
        is_local,
        hidden: false,
    };
    let snapshot = GameSnapshot {
        local_player: Some(1),
        players: vec![player(1, "Me", true), player(2, "Them", false)],
        npcs: vec![NpcSnapshot {
            actor: actor(7, Some(ActorRef::Player(1))),
            id: 3,
            name: None,
            combat_level: 0,
            size: (1, 1),
            actions: Vec::new(),
        }],
        ..GameSnapshot::default()
    };
    let mut harness = Harness::new();
    assert!(harness.output(&snapshot).entities.hidden.is_empty());
    harness.enable("entityhider", &[]);
    let output = harness.output(&snapshot).entities;
    // Source defaults: `hidePlayers` and `hidePlayers2D` on, everything else off.
    assert_eq!(output.hidden, [ActorRef::Player(2)]);
    assert_eq!(output.hidden_overlays, [ActorRef::Player(2)]);
    assert!(!output.hide_projectiles);
    harness.enable(
        "entityhider",
        &[
            ("hideAttackers", ConfigValue::Boolean(true)),
            ("hideProjectiles", ConfigValue::Boolean(true)),
        ],
    );
    let output = harness.output(&snapshot).entities;
    assert!(output.hidden.contains(&ActorRef::Npc(7)));
    assert!(output.hidden_overlays.contains(&ActorRef::Npc(7)));
    assert!(output.hide_projectiles);
}
