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

    /// Deliver `events` over `snapshot`, then collect the merged output of the same update.
    pub(crate) fn run(&mut self, snapshot: &GameSnapshot, events: &[GameEvent]) -> HostOutput {
        let view = SnapshotView(snapshot);
        self.set
            .dispatch_game(events, &view, &mut self.storage, &mut self.logs);
        self.set
            .collect_output(&view, &mut self.storage, &mut self.logs)
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

mod idlenotifier {
    use super::*;
    use rustclient_plugin_api::{
        ActorRef, ClientSnapshot, GameStateKind, Hitsplat, InputIdle, NpcSnapshot, PlayerSnapshot,
        Skill, SkillLevel, VarKind, Vars, WorldPoint,
    };
    use std::collections::BTreeMap;

    const ME: ActorRef = ActorRef::Player(1);

    /// A logged-in snapshot `ms` milliseconds into the session with the local player idle.
    fn at(ms: u64) -> GameSnapshot {
        let mut local = actor(1, None);
        local.world = WorldPoint::new(3200, 3200, 0);
        GameSnapshot {
            client: ClientSnapshot {
                state: GameStateKind::LoggedIn,
                cycle: ms / 20,
                run_energy: 10_000,
                input_idle: Some(InputIdle {
                    keyboard: 100,
                    mouse: 100,
                    mouse_press: 100,
                }),
                ..ClientSnapshot::default()
            },
            local_player: Some(1),
            players: vec![PlayerSnapshot {
                actor: local,
                name: "Me".into(),
                combat_level: 3,
                skill_level: 0,
                is_local: true,
                hidden: false,
            }],
            skills: vec![SkillLevel::default(); 23],
            ..GameSnapshot::default()
        }
    }

    fn bodies(output: &HostOutput) -> Vec<&str> {
        output
            .notifications
            .iter()
            .map(|(_, notification)| notification.body.as_str())
            .collect()
    }

    fn start(harness: &mut Harness, values: &[(&str, ConfigValue)]) {
        harness.enable("idlenotifier", values);
        harness.run(
            &at(0),
            &[GameEvent::GameStateChanged(GameStateKind::LoggedIn)],
        );
    }

    fn tick(harness: &mut Harness, snapshot: &GameSnapshot) -> HostOutput {
        harness.run(snapshot, &[GameEvent::GameTick(1)])
    }

    #[test]
    fn a_disabled_plugin_notifies_nothing() {
        let mut harness = Harness::new();
        let output = harness.run(&at(0), &[GameEvent::GameTick(1)]);
        assert!(output.notifications.is_empty());
    }

    #[test]
    fn going_idle_after_a_skilling_animation_notifies_once_after_the_delay() {
        let mut harness = Harness::new();
        start(&mut harness, &[]);
        // Rune axe woodcutting starts the idle timer.
        let mut chopping = at(1000);
        chopping.players[0].actor.animation = Some(867);
        harness.run(
            &chopping,
            &[GameEvent::AnimationChanged {
                actor: ME,
                animation: Some(867),
            }],
        );
        assert!(tick(&mut harness, &chopping).notifications.is_empty());
        // The animation ends at 1000 ms; the default delay is 5000 ms.
        assert!(tick(&mut harness, &at(2000)).notifications.is_empty());
        assert!(tick(&mut harness, &at(5900)).notifications.is_empty());
        let output = tick(&mut harness, &at(6100));
        assert_eq!(bodies(&output), ["You are now idle!"]);
        assert_eq!(output.notifications[0].1.title, "RustClient - Me");
        // Edge triggered: staying idle does not repeat it.
        assert!(tick(&mut harness, &at(9000)).notifications.is_empty());
    }

    #[test]
    fn an_unknown_animation_never_arms_the_idle_timer() {
        let mut harness = Harness::new();
        start(&mut harness, &[]);
        let mut busy = at(1000);
        busy.players[0].actor.animation = Some(1);
        harness.run(
            &busy,
            &[GameEvent::AnimationChanged {
                actor: ME,
                animation: Some(1),
            }],
        );
        assert!(tick(&mut harness, &at(20_000)).notifications.is_empty());
    }

    #[test]
    fn the_idle_delay_setting_is_honoured_and_clicks_postpone_the_check() {
        let mut harness = Harness::new();
        start(&mut harness, &[("timeout", ConfigValue::Integer(1000))]);
        let mut chopping = at(0);
        chopping.players[0].actor.animation = Some(867);
        harness.run(
            &chopping,
            &[GameEvent::AnimationChanged {
                actor: ME,
                animation: Some(867),
            }],
        );
        // A click in the last second means the player is there: no notification and the timer resets.
        let mut clicked = at(1500);
        clicked.client.input_idle = Some(InputIdle {
            keyboard: 100,
            mouse: 5,
            mouse_press: 10,
        });
        assert!(tick(&mut harness, &clicked).notifications.is_empty());
        assert!(tick(&mut harness, &at(1600)).notifications.is_empty());
    }

    #[test]
    fn movement_idle_notifies_only_when_the_setting_is_on() {
        let mut harness = Harness::new();
        start(&mut harness, &[]);
        let moved = |ms, x| {
            let mut snapshot = at(ms);
            snapshot.players[0].actor.world = WorldPoint::new(x, 3200, 0);
            snapshot
        };
        for (ms, x) in [(0, 3200), (1000, 3201), (8000, 3201)] {
            assert!(tick(&mut harness, &moved(ms, x)).notifications.is_empty());
        }

        let mut harness = Harness::new();
        start(
            &mut harness,
            &[("movementidle", ConfigValue::Boolean(true))],
        );
        assert!(tick(&mut harness, &moved(0, 3200)).notifications.is_empty());
        assert!(
            tick(&mut harness, &moved(1000, 3201))
                .notifications
                .is_empty()
        );
        assert!(
            tick(&mut harness, &moved(3000, 3201))
                .notifications
                .is_empty()
        );
        assert_eq!(
            bodies(&tick(&mut harness, &moved(6100, 3201))),
            ["You have stopped moving!"]
        );
    }

    #[test]
    fn low_hitpoints_notify_on_the_drop_not_while_staying_low() {
        let mut harness = Harness::new();
        start(
            &mut harness,
            &[
                ("hitpointsNotification", ConfigValue::Boolean(true)),
                ("hitpoints", ConfigValue::Integer(10)),
            ],
        );
        let health = |ms, boosted| {
            let mut snapshot = at(ms);
            snapshot.skills[Skill::Hitpoints.index()] = SkillLevel {
                real: 50,
                boosted,
                experience: 0,
            };
            snapshot
        };
        assert!(tick(&mut harness, &health(0, 50)).notifications.is_empty());
        assert_eq!(
            bodies(&tick(&mut harness, &health(600, 8))),
            ["You have low hitpoints!"]
        );
        assert!(
            tick(&mut harness, &health(1200, 5))
                .notifications
                .is_empty()
        );
        // Recovering above the threshold re-arms it.
        assert!(
            tick(&mut harness, &health(1800, 40))
                .notifications
                .is_empty()
        );
        assert_eq!(
            bodies(&tick(&mut harness, &health(2400, 3))),
            ["You have low hitpoints!"]
        );
    }

    #[test]
    fn absorption_points_count_as_hitpoints() {
        let mut harness = Harness::new();
        start(
            &mut harness,
            &[
                ("hitpointsNotification", ConfigValue::Boolean(true)),
                ("hitpoints", ConfigValue::Integer(10)),
            ],
        );
        let health = |ms, boosted| {
            let mut snapshot = at(ms);
            snapshot.skills[Skill::Hitpoints.index()] = SkillLevel {
                real: 50,
                boosted,
                experience: 0,
            };
            snapshot.vars = Vars {
                varps: BTreeMap::from([(
                    rustclient_plugin_api::VarbitDef {
                        varp: 9,
                        lsb: 0,
                        msb: 15,
                    }
                    .varp,
                    100,
                )]),
                varbits: std::sync::Arc::new(BTreeMap::from([(
                    3956,
                    rustclient_plugin_api::VarbitDef {
                        varp: 9,
                        lsb: 0,
                        msb: 15,
                    },
                )])),
            };
            snapshot
        };
        assert!(tick(&mut harness, &health(0, 50)).notifications.is_empty());
        // 5 hitpoints left but 100 absorption points still shield them.
        assert!(tick(&mut harness, &health(600, 5)).notifications.is_empty());
    }

    #[test]
    fn run_energy_thresholds_use_whole_percent() {
        let mut harness = Harness::new();
        start(
            &mut harness,
            &[
                ("lowEnergyNotification", ConfigValue::Boolean(true)),
                ("lowEnergy", ConfigValue::Integer(20)),
            ],
        );
        let energy = |ms, raw| {
            let mut snapshot = at(ms);
            snapshot.client.run_energy = raw;
            snapshot
        };
        assert!(
            tick(&mut harness, &energy(0, 5000))
                .notifications
                .is_empty()
        );
        // 20.99% is still 20 in the source's integer division.
        assert_eq!(
            bodies(&tick(&mut harness, &energy(600, 2099))),
            ["You have low run energy!"]
        );
        assert!(
            tick(&mut harness, &energy(1200, 1000))
                .notifications
                .is_empty()
        );
    }

    #[test]
    fn spec_energy_notifies_only_on_a_regeneration_sized_step_over_the_threshold() {
        let mut harness = Harness::new();
        start(
            &mut harness,
            &[
                ("specNotification", ConfigValue::Boolean(true)),
                ("spec", ConfigValue::Integer(50)),
            ],
        );
        let spec = |ms, value| {
            let mut snapshot = at(ms);
            snapshot.vars.varps.insert(300, value);
            snapshot
        };
        assert!(tick(&mut harness, &spec(0, 400)).notifications.is_empty());
        // Jumping 400 -> 600 is a drink or a weapon swap, not regeneration.
        assert!(tick(&mut harness, &spec(600, 600)).notifications.is_empty());
        assert!(
            tick(&mut harness, &spec(1200, 400))
                .notifications
                .is_empty()
        );
        assert_eq!(
            bodies(&tick(&mut harness, &spec(1800, 500))),
            ["You have restored spec energy!"]
        );
    }

    #[test]
    fn the_idle_logout_warning_fires_once_twenty_seconds_before_the_timeout() {
        let mut harness = Harness::new();
        start(&mut harness, &[]);
        let idle = |ms, ticks| {
            let mut snapshot = at(ms);
            snapshot.client.input_idle = Some(InputIdle {
                keyboard: ticks,
                mouse: ticks,
                mouse_press: ticks,
            });
            snapshot
        };
        assert!(
            tick(&mut harness, &idle(0, 13_999))
                .notifications
                .is_empty()
        );
        assert_eq!(
            bodies(&tick(&mut harness, &idle(600, 14_000))),
            ["You are about to log out from idling too long!"]
        );
        assert!(
            tick(&mut harness, &idle(1200, 14_030))
                .notifications
                .is_empty()
        );
    }

    #[test]
    fn a_host_that_cannot_report_input_never_sends_logout_warnings() {
        let mut harness = Harness::new();
        start(&mut harness, &[]);
        let mut snapshot = at(0);
        snapshot.client.input_idle = None;
        assert!(tick(&mut harness, &snapshot).notifications.is_empty());
    }

    #[test]
    fn the_six_hour_warning_starts_at_login_and_fires_after_340_minutes() {
        let mut harness = Harness::new();
        harness.enable("idlenotifier", &[]);
        let minute = 60_000;
        // Title screen -> loading -> in game, as one login.
        harness.run(
            &at(0),
            &[GameEvent::GameStateChanged(GameStateKind::Loading)],
        );
        harness.run(
            &at(minute),
            &[GameEvent::GameStateChanged(GameStateKind::LoggedIn)],
        );
        assert!(
            tick(&mut harness, &at(339 * minute))
                .notifications
                .is_empty()
        );
        assert_eq!(
            bodies(&tick(&mut harness, &at(341 * minute + 1000))),
            ["You are about to log out from being online for 6 hours!"]
        );
        assert!(
            tick(&mut harness, &at(342 * minute))
                .notifications
                .is_empty()
        );
    }

    #[test]
    fn enabling_while_logged_in_cannot_know_the_six_hour_mark() {
        let mut harness = Harness::new();
        start(&mut harness, &[]);
        assert!(
            tick(&mut harness, &at(400 * 60_000))
                .notifications
                .is_empty()
        );
    }

    fn rat() -> NpcSnapshot {
        NpcSnapshot {
            actor: actor(7, None),
            id: 2,
            name: Some("Giant rat".into()),
            combat_level: 3,
            size: (1, 1),
            actions: vec![Some("Attack".into()), None],
        }
    }

    #[test]
    fn leaving_combat_notifies_after_the_delay_and_the_last_hit_window() {
        let mut harness = Harness::new();
        start(&mut harness, &[]);
        let mut fighting = at(1000);
        fighting.npcs = vec![rat()];
        fighting.players[0].actor.interacting = Some(ActorRef::Npc(7));
        harness.run(
            &fighting,
            &[
                GameEvent::InteractingChanged {
                    source: ME,
                    target: Some(ActorRef::Npc(7)),
                },
                GameEvent::HitsplatApplied {
                    actor: ME,
                    hitsplat: Hitsplat {
                        kind: 16,
                        amount: 2,
                        cycle: 50,
                    },
                },
            ],
        );
        // The fight ends at 2000 ms; the last hit's 8 tick countdown also has to run out, and each
        // tick below counts one.
        let mut over = at(2000);
        over.npcs = vec![rat()];
        harness.run(
            &over,
            &[GameEvent::InteractingChanged {
                source: ME,
                target: None,
            }],
        );
        for ms in [2600, 3200, 3800, 4400, 5000, 5600, 6200, 6800] {
            assert!(tick(&mut harness, &at(ms)).notifications.is_empty(), "{ms}");
        }
        let output = tick(&mut harness, &at(7100));
        assert_eq!(bodies(&output), ["You are now out of combat!"]);
    }

    #[test]
    fn fishing_spots_count_as_interaction_even_without_an_attack_option() {
        let mut harness = Harness::new();
        start(&mut harness, &[]);
        let spot = NpcSnapshot {
            name: Some("Fishing spot".into()),
            actions: vec![Some("Net".into())],
            ..rat()
        };
        let mut fishing = at(1000);
        fishing.npcs = vec![spot.clone()];
        fishing.players[0].actor.interacting = Some(ActorRef::Npc(7));
        harness.run(
            &fishing,
            &[GameEvent::InteractingChanged {
                source: ME,
                target: Some(ActorRef::Npc(7)),
            }],
        );
        let mut over = at(2000);
        over.npcs = vec![spot];
        harness.run(
            &over,
            &[GameEvent::InteractingChanged {
                source: ME,
                target: None,
            }],
        );
        let output = tick(&mut harness, &at(7100));
        assert_eq!(bodies(&output), ["You are now idle!"]);
    }

    #[test]
    fn other_actors_events_are_ignored() {
        let mut harness = Harness::new();
        start(&mut harness, &[]);
        let mut snapshot = at(1000);
        snapshot.players[0].actor.animation = None;
        harness.run(
            &snapshot,
            &[GameEvent::AnimationChanged {
                actor: ActorRef::Player(9),
                animation: Some(867),
            }],
        );
        assert!(tick(&mut harness, &at(20_000)).notifications.is_empty());
    }

    #[test]
    fn a_group_gathering_buff_ending_assumes_woodcutting() {
        let mut harness = Harness::new();
        start(&mut harness, &[]);
        harness.run(
            &at(1000),
            &[GameEvent::VarbitChanged {
                kind: VarKind::Varp,
                id: 4007,
                value: -1,
            }],
        );
        assert_eq!(
            bodies(&tick(&mut harness, &at(7100))),
            ["You are now idle!"]
        );
    }
}
