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

mod fps {
    use super::*;
    use rustclient_plugin_api::{
        ClientSnapshot, HorizontalAlign, OverlayCommand, Rect, WidgetSnapshot,
    };

    fn widget(id: u32, bounds: Rect, hidden: bool) -> WidgetSnapshot {
        WidgetSnapshot {
            id,
            child_index: -1,
            bounds,
            clip: bounds,
            text: None,
            sprite: None,
            item: None,
            hidden,
            actions: Vec::new(),
        }
    }

    fn snapshot(focused: bool, widgets: Vec<WidgetSnapshot>) -> GameSnapshot {
        GameSnapshot {
            client: ClientSnapshot {
                fps: 48,
                focused,
                canvas: (765, 503),
                ..ClientSnapshot::default()
            },
            widgets: widgets.into(),
            ..GameSnapshot::default()
        }
    }

    fn texts(output: &HostOutput) -> Vec<(String, i32, i32, u32, HorizontalAlign)> {
        output
            .overlay
            .iter()
            .filter_map(|item| match &item.command {
                OverlayCommand::Text {
                    text,
                    x,
                    y,
                    argb,
                    align,
                } => Some((text.clone(), *x, *y, *argb, *align)),
                _ => None,
            })
            .collect()
    }

    #[test]
    fn the_indicator_is_yellow_without_a_limit_with_a_shadow_in_the_top_right() {
        let mut harness = Harness::new();
        harness.enable("fps", &[]);
        let output = harness.output(&snapshot(true, Vec::new()));
        assert_eq!(output.frame_limit, None);
        assert_eq!(
            texts(&output),
            [
                (
                    "48 FPS".to_owned(),
                    765,
                    12,
                    0xFF00_0000,
                    HorizontalAlign::Right
                ),
                (
                    "48 FPS".to_owned(),
                    764,
                    11,
                    0xFFFF_FF00,
                    HorizontalAlign::Right
                ),
            ]
        );
    }

    #[test]
    fn an_enforced_limit_turns_the_indicator_red_and_reaches_the_host() {
        let mut harness = Harness::new();
        harness.enable(
            "fps",
            &[
                ("limitFps", ConfigValue::Boolean(true)),
                ("maxFps", ConfigValue::Integer(30)),
            ],
        );
        let output = harness.output(&snapshot(true, Vec::new()));
        assert_eq!(output.frame_limit, Some(30));
        assert_eq!(texts(&output)[1].3, 0xFFFF_0000);
    }

    #[test]
    fn the_unfocused_limit_only_applies_while_the_window_is_unfocused() {
        let mut harness = Harness::new();
        harness.enable(
            "fps",
            &[
                ("limitFpsUnfocused", ConfigValue::Boolean(true)),
                ("maxFpsUnfocused", ConfigValue::Integer(5)),
            ],
        );
        assert_eq!(
            harness.output(&snapshot(true, Vec::new())).frame_limit,
            None
        );
        assert_eq!(
            harness.output(&snapshot(false, Vec::new())).frame_limit,
            Some(5)
        );
    }

    #[test]
    fn the_indicator_moves_left_of_a_shown_logout_button_only() {
        let button = Rect {
            x: 700,
            y: 0,
            width: 30,
            height: 30,
        };
        let mut harness = Harness::new();
        harness.enable("fps", &[]);
        let shown = harness.output(&snapshot(true, vec![widget(0x00A4_0023, button, false)]));
        assert_eq!(texts(&shown)[1].1, 765 - 1 - 30);
        let hidden = harness.output(&snapshot(true, vec![widget(0x00A4_0023, button, true)]));
        assert_eq!(texts(&hidden)[1].1, 764);
    }

    #[test]
    fn hiding_the_indicator_keeps_the_limit() {
        let mut harness = Harness::new();
        harness.enable(
            "fps",
            &[
                ("drawFps", ConfigValue::Boolean(false)),
                ("limitFps", ConfigValue::Boolean(true)),
            ],
        );
        let output = harness.output(&snapshot(true, Vec::new()));
        assert!(output.overlay.is_empty());
        assert_eq!(output.frame_limit, Some(50));
    }
}

mod statusbars {
    use super::*;
    use rustclient_plugin_api::{
        ClientSnapshot, OverlayCommand, Rect, Skill, SkillLevel, VarbitDef, Vars, WidgetSnapshot,
    };
    use std::collections::BTreeMap;
    use std::sync::Arc;

    const FIXED_SIDE_PANELS: u32 = 0x0224_0050;
    const RESIZED_BOTTOM_SIDE_BACKGROUND: u32 = 0x00A4_0046;

    fn panel(id: u32, x: i32, y: i32, width: i32) -> WidgetSnapshot {
        let bounds = Rect {
            x,
            y,
            width,
            height: 261,
        };
        WidgetSnapshot {
            id,
            child_index: -1,
            bounds,
            clip: bounds,
            text: None,
            sprite: None,
            item: None,
            hidden: false,
            actions: Vec::new(),
        }
    }

    fn snapshot(widgets: Vec<WidgetSnapshot>) -> GameSnapshot {
        let mut skills = vec![SkillLevel::default(); 23];
        skills[Skill::Hitpoints.index()] = SkillLevel {
            real: 99,
            boosted: 50,
            experience: 0,
        };
        skills[Skill::Prayer.index()] = SkillLevel {
            real: 70,
            boosted: 70,
            experience: 0,
        };
        GameSnapshot {
            client: ClientSnapshot {
                run_energy: 4200,
                ..ClientSnapshot::default()
            },
            local_player: Some(1),
            players: vec![rustclient_plugin_api::PlayerSnapshot {
                actor: actor(1, None),
                name: "Me".into(),
                combat_level: 3,
                skill_level: 0,
                is_local: true,
                hidden: false,
            }],
            skills,
            widgets: widgets.into(),
            ..GameSnapshot::default()
        }
    }

    fn rects(output: &HostOutput) -> Vec<(i32, i32, i32, i32, u32, bool)> {
        output
            .overlay
            .iter()
            .filter_map(|item| match &item.command {
                OverlayCommand::Rect {
                    x,
                    y,
                    width,
                    height,
                    argb,
                    filled,
                } => Some((*x, *y, *width, *height, *argb, *filled)),
                _ => None,
            })
            .collect()
    }

    #[test]
    fn nothing_is_drawn_without_a_side_panel() {
        let mut harness = Harness::new();
        harness.enable("statusbars", &[]);
        assert!(harness.output(&snapshot(Vec::new())).overlay.is_empty());
    }

    #[test]
    fn fixed_layout_bars_sit_left_and_right_of_the_side_panel() {
        let mut harness = Harness::new();
        harness.enable(
            "statusbars",
            &[("enableSkillIcon", ConfigValue::Boolean(false))],
        );
        let output = harness.output(&snapshot(vec![panel(FIXED_SIDE_PANELS, 549, 205, 190)]));
        let rects = rects(&output);
        // Left bar: x = 549 - 20, y = 205 + 4; right bar: x = 549 - 0 + 190.
        assert_eq!((rects[0].0, rects[0].1), (529, 209));
        assert_eq!(rects[1], (529, 209, 20, 252, 0x9600_0000, true));
        // Hitpoints 50/99 of 252 rows is 127, drawn inside the border.
        assert_eq!(
            rects[2],
            (530, 209 + 1 + (252 - 127), 18, 127 - 2, 0x7DE1_2300, true)
        );
        assert_eq!(rects[3], (739, 209, 20, 252, 0x9600_0000, false));
        // Prayer is full and not active: the full height, in the idle prayer colour.
        assert_eq!(rects[5].3, 250);
        assert_eq!(rects[5].4, 0xAF32_C8C8);
    }

    #[test]
    fn the_modern_layout_uses_the_configured_width_and_its_own_offsets() {
        let mut harness = Harness::new();
        harness.enable(
            "statusbars",
            &[
                ("barWidth", ConfigValue::Integer(10)),
                ("enableSkillIcon", ConfigValue::Boolean(false)),
            ],
        );
        let output = harness.output(&snapshot(vec![panel(
            RESIZED_BOTTOM_SIDE_BACKGROUND,
            500,
            300,
            250,
        )]));
        let rects = rects(&output);
        // extra = 10 - 20 = -10. Left x = 500 + 10 - 61 + 20 = 469, y = 300 - 12 + 12 = 300.
        assert_eq!(
            (rects[0].0, rects[0].1, rects[0].2, rects[0].3),
            (469, 300, 10, 272)
        );
        // Right x = 500 + 10 - 35 + 10 = 485.
        assert_eq!(rects[3].0, 485);
    }

    #[test]
    fn icons_and_counters_follow_their_switches() {
        let mut harness = Harness::new();
        harness.enable(
            "statusbars",
            &[("enableCounter", ConfigValue::Boolean(true))],
        );
        let output = harness.output(&snapshot(vec![panel(FIXED_SIDE_PANELS, 549, 205, 190)]));
        let sprites: Vec<_> = output
            .overlay
            .iter()
            .filter_map(|item| match item.command {
                OverlayCommand::Sprite { sprite, x, y, .. } => Some((sprite, x, y)),
                _ => None,
            })
            .collect();
        // Hitpoints orb on the left bar, prayer orb on the right, centred in each bar, 4 below the top.
        assert_eq!(sprites, [(1067, 539, 213), (1068, 749, 213)]);
        let counters: Vec<_> = output
            .overlay
            .iter()
            .filter_map(|item| match &item.command {
                OverlayCommand::Text {
                    text,
                    argb: 0xFFFF_FFFF,
                    ..
                } => Some(text.clone()),
                _ => None,
            })
            .collect();
        assert_eq!(counters, ["50", "70"]);
    }

    #[test]
    fn run_energy_and_special_attack_read_their_sources() {
        let mut harness = Harness::new();
        harness.enable(
            "statusbars",
            &[
                ("leftBarMode", ConfigValue::Choice("RUN_ENERGY".into())),
                ("rightBarMode", ConfigValue::Choice("SPECIAL_ATTACK".into())),
                ("enableCounter", ConfigValue::Boolean(true)),
                ("enableSkillIcon", ConfigValue::Boolean(false)),
            ],
        );
        let mut game = snapshot(vec![panel(FIXED_SIDE_PANELS, 549, 205, 190)]);
        game.vars.varps.insert(300, 735);
        let output = harness.output(&game);
        let counters: Vec<_> = output
            .overlay
            .iter()
            .filter_map(|item| match &item.command {
                OverlayCommand::Text {
                    text,
                    argb: 0xFFFF_FFFF,
                    ..
                } => Some(text.clone()),
                _ => None,
            })
            .collect();
        // 4200 / 100 and 735 / 10, in whole percent.
        assert_eq!(counters, ["42", "73"]);
    }

    #[test]
    fn poison_and_venom_recolour_the_hitpoints_bar_and_an_active_prayer_the_prayer_bar() {
        let mut harness = Harness::new();
        harness.enable(
            "statusbars",
            &[("enableSkillIcon", ConfigValue::Boolean(false))],
        );
        let mut game = snapshot(vec![panel(FIXED_SIDE_PANELS, 549, 205, 190)]);
        game.vars.varps.insert(102, 10);
        let poisoned = harness.output(&game);
        assert_eq!(rects(&poisoned)[2].4, 0x9600_9100);
        game.vars.varps.insert(102, 1_000_000);
        assert_eq!(rects(&harness.output(&game))[2].4, 0x9600_4100);
        // Protect from Melee (varbit 4129) lights the prayer bar.
        game.vars = Vars {
            varps: BTreeMap::from([(7, 1)]),
            varbits: Arc::new(BTreeMap::from([(
                4129,
                VarbitDef {
                    varp: 7,
                    lsb: 0,
                    msb: 0,
                },
            )])),
        };
        assert_eq!(rects(&harness.output(&game))[5].4, 0xE139_FFBA);
    }

    #[test]
    fn bars_hide_after_the_delay_once_combat_ends() {
        use rustclient_plugin_api::{ActorRef, NpcSnapshot};
        let mut harness = Harness::new();
        harness.enable(
            "statusbars",
            &[
                ("hideAfterCombatDelay", ConfigValue::Integer(10)),
                ("enableSkillIcon", ConfigValue::Boolean(false)),
            ],
        );
        let panel = vec![panel(FIXED_SIDE_PANELS, 549, 205, 190)];
        let mut fight = snapshot(panel.clone());
        fight.client.tick = 100;
        // Never fought: evaluated at once on enabling, 100 ticks since tick 0 is past the delay.
        assert!(harness.output(&fight).overlay.is_empty());
        // Fighting an attackable NPC shows the bars on the tick it starts.
        fight.npcs = vec![NpcSnapshot {
            actor: actor(7, None),
            id: 2,
            name: Some("Giant rat".into()),
            combat_level: 3,
            size: (1, 1),
            actions: vec![Some("Attack".into())],
        }];
        fight.players[0].actor.interacting = Some(ActorRef::Npc(7));
        let shown = harness.run(&fight, &[GameEvent::GameTick(100)]);
        assert!(!shown.overlay.is_empty());
        // Out of combat, they stay for the delay and go on the tick that reaches it.
        let mut over = snapshot(panel);
        over.client.tick = 109;
        assert!(
            !harness
                .run(&over, &[GameEvent::GameTick(109)])
                .overlay
                .is_empty()
        );
        over.client.tick = 110;
        assert!(
            harness
                .run(&over, &[GameEvent::GameTick(110)])
                .overlay
                .is_empty()
        );
    }
}
