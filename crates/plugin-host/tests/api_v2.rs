//! API 0.2 mediation: every gated query, event class and output kind is denied without its grant.
use rustclient_plugin_api::{
    ActorRef, ActorState, CameraCommand, Capability, CapabilitySet, ChatKind, ChatLine,
    ClientSnapshot, EntityVisibility, GameEvent, GameSnapshot, InfoBox, ItemStack, LayeredOverlay,
    MenuEdit, MinimapRequest, Notification, NotificationKind, NpcSnapshot, OverlayCommand,
    OverlayLayer, PlayerSnapshot, PluginApiVersion, PluginEvent, PluginId, PluginManifest,
    PluginOutput, Rect, RoofArea, RoofModes, SceneFlags, SkillLevel, SpriteOverride, Stretch,
    StretchScaling, VarbitDef, Vars, WidgetSnapshot, WorldPoint,
};
use rustclient_plugin_host::{
    CapabilityDenied, FaultKind, MemoryLogSink, MemoryStorage, PluginCall, PluginFault,
    PluginInstance, PluginSet, SnapshotView,
};
use std::cell::RefCell;
use std::collections::BTreeMap;
use std::rc::Rc;
use std::sync::Arc;

#[derive(Default)]
struct Seen {
    events: Vec<GameEvent>,
    queries: Vec<(&'static str, Result<(), CapabilityDenied>)>,
}

struct Probe {
    seen: Rc<RefCell<Seen>>,
    output: PluginOutput,
    query: bool,
    fail: bool,
}

impl PluginInstance for Probe {
    fn init(&mut self, _call: &mut PluginCall<'_>) -> Result<(), PluginFault> {
        Ok(())
    }

    fn on_event(
        &mut self,
        _call: &mut PluginCall<'_>,
        _events: &[PluginEvent],
    ) -> Result<(), PluginFault> {
        Ok(())
    }

    fn overlay(
        &mut self,
        _call: &mut PluginCall<'_>,
    ) -> Result<Vec<rustclient_plugin_api::OverlayLine>, PluginFault> {
        Ok(Vec::new())
    }

    fn on_game_events(
        &mut self,
        _call: &mut PluginCall<'_>,
        events: &[GameEvent],
    ) -> Result<(), PluginFault> {
        self.seen.borrow_mut().events.extend_from_slice(events);
        if self.fail {
            return Err(PluginFault::new(FaultKind::Event, "probe failure"));
        }
        Ok(())
    }

    fn output(&mut self, call: &mut PluginCall<'_>) -> Result<PluginOutput, PluginFault> {
        if self.query {
            let mut seen = self.seen.borrow_mut();
            seen.queries.clear();
            seen.queries.push(("client", call.client().map(|_| ())));
            seen.queries.push((
                "actor",
                call.actor(
                    rustclient_plugin_api::ActorHandle::new(7, ActorRef::Npc(7), 1).unwrap(),
                )
                .map(|_| ()),
            ));
            seen.queries.push(("players", call.players().map(|_| ())));
            seen.queries
                .push(("local_player", call.local_player().map(|_| ())));
            seen.queries.push(("npcs", call.npcs().map(|_| ())));
            seen.queries.push(("skills", call.skills().map(|_| ())));
            seen.queries.push(("varp", call.varp(7).map(|_| ())));
            seen.queries.push(("varbit", call.varbit(1).map(|_| ())));
            seen.queries
                .push(("container", call.container(93).map(|_| ())));
            seen.queries.push(("widgets", call.widgets().map(|_| ())));
            seen.queries.push(("chat", call.chat().map(|_| ())));
        }
        Ok(self.output.clone())
    }
}

fn manifest(id: &str, capabilities: &[Capability]) -> PluginManifest {
    PluginManifest {
        id: PluginId::parse(id).expect("valid id"),
        name: "probe".into(),
        version: "0.2.0".into(),
        plugin_api: PluginApiVersion::current(),
        capabilities: capabilities.iter().copied().collect::<CapabilitySet>(),
        entry: String::new(),
    }
}

fn actor(index: u16) -> ActorState {
    ActorState {
        instance: 1,
        index,
        world: WorldPoint::new(3222, 3218, 0),
        orientation: 0,
        animation: None,
        pose: None,
        graphic: None,
        overhead_text: None,
        interacting: None,
        hitsplats: Vec::new(),
        healthbars: Vec::new(),
    }
}

fn snapshot() -> GameSnapshot {
    GameSnapshot {
        client: ClientSnapshot {
            tick: 12,
            fps: 50,
            ..ClientSnapshot::default()
        },
        local_player: Some(1),
        players: vec![PlayerSnapshot {
            actor: actor(1),
            name: "Zezima".into(),
            combat_level: 126,
            skill_level: 0,
            is_local: true,
            hidden: false,
        }],
        npcs: vec![NpcSnapshot {
            actor: actor(5),
            id: 3,
            name: Some("Goblin".into()),
            combat_level: 2,
            size: (1, 1),
            actions: Vec::new(),
        }],
        skills: vec![SkillLevel {
            real: 10,
            boosted: 12,
            experience: 1154,
        }],
        vars: Vars {
            varps: BTreeMap::from([(7, 0b100)]),
            varbits: Arc::new(BTreeMap::from([(
                1,
                VarbitDef {
                    varp: 7,
                    lsb: 2,
                    msb: 3,
                },
            )])),
        },
        containers: BTreeMap::from([(
            93,
            vec![Some(ItemStack {
                id: 995,
                quantity: 10,
            })],
        )]),
        widget_groups: vec![161],
        widgets: Arc::from(vec![WidgetSnapshot {
            id: 0x00A1_005D,
            child_index: -1,
            bounds: Rect::default(),
            clip: Rect::default(),
            text: Some("hello".into()),
            sprite: None,
            item: None,
            hidden: false,
            actions: Vec::new(),
        }]),
        chat: vec![ChatLine {
            id: 1,
            kind: ChatKind::Game,
            name: String::new(),
            text: "Welcome".into(),
        }],
    }
}

struct Harness {
    set: PluginSet,
    storage: MemoryStorage,
    logs: MemoryLogSink,
}

impl Harness {
    fn new() -> Self {
        Self {
            set: PluginSet::new(),
            storage: MemoryStorage::new(),
            logs: MemoryLogSink::default(),
        }
    }

    fn add(
        &mut self,
        id: &str,
        capabilities: &[Capability],
        output: PluginOutput,
        query: bool,
    ) -> Rc<RefCell<Seen>> {
        let seen = Rc::new(RefCell::new(Seen::default()));
        self.set
            .register(
                manifest(id, capabilities),
                Box::new(Probe {
                    seen: Rc::clone(&seen),
                    output,
                    query,
                    fail: false,
                }),
            )
            .expect("compatible");
        seen
    }

    fn output(&mut self, game: &GameSnapshot) -> rustclient_plugin_host::HostOutput {
        self.set
            .collect_output(&SnapshotView(game), &mut self.storage, &mut self.logs)
    }
}

#[test]
fn every_gated_query_is_denied_without_its_grant_and_allowed_with_it() {
    let game = snapshot();
    let mut harness = Harness::new();
    let denied = harness.add("dev.rustclient.denied", &[], PluginOutput::default(), true);
    let granted = harness.add(
        "dev.rustclient.granted",
        &[
            Capability::GameReadClient,
            Capability::GameReadPlayers,
            Capability::GameReadNpcs,
            Capability::GameReadSkills,
            Capability::GameReadVars,
            Capability::GameReadInventory,
            Capability::GameReadWidgets,
            Capability::GameReadChat,
        ],
        PluginOutput::default(),
        true,
    );
    let _ = harness.output(&game);
    for (name, result) in &denied.borrow().queries {
        assert!(result.is_err(), "{name} was readable without a grant");
    }
    assert_eq!(denied.borrow().queries.len(), 11);
    for (name, result) in &granted.borrow().queries {
        assert!(result.is_ok(), "{name} denied despite grant");
    }
    let warnings = harness
        .logs
        .records
        .iter()
        .filter(|record| record.message.starts_with("denied:"))
        .count();
    assert_eq!(
        warnings, 8,
        "each distinct capability is reported once per plugin"
    );
    let _ = harness.output(&game);
    let again = harness
        .logs
        .records
        .iter()
        .filter(|record| record.message.starts_with("denied:"))
        .count();
    assert_eq!(again, 8, "repeat denials must not spam diagnostics");
}

#[test]
fn granted_queries_return_the_projected_values() {
    struct Reader {
        out: Rc<RefCell<Vec<String>>>,
    }
    impl PluginInstance for Reader {
        fn init(&mut self, _: &mut PluginCall<'_>) -> Result<(), PluginFault> {
            Ok(())
        }
        fn on_event(
            &mut self,
            _: &mut PluginCall<'_>,
            _: &[PluginEvent],
        ) -> Result<(), PluginFault> {
            Ok(())
        }
        fn overlay(
            &mut self,
            _: &mut PluginCall<'_>,
        ) -> Result<Vec<rustclient_plugin_api::OverlayLine>, PluginFault> {
            Ok(Vec::new())
        }
        fn output(&mut self, call: &mut PluginCall<'_>) -> Result<PluginOutput, PluginFault> {
            let mut out = self.out.borrow_mut();
            out.push(format!(
                "{:?}",
                call.local_player().unwrap().map(|p| p.name.clone())
            ));
            out.push(format!("{}", call.varp(7).unwrap()));
            out.push(format!("{:?}", call.varbit(1).unwrap()));
            out.push(format!("{:?}", call.varbit(99).unwrap()));
            out.push(format!("{}", call.npcs().unwrap()[0].actor.index));
            out.push(format!("{}", call.client().unwrap().fps));
            out.push(format!("{:?}", call.container(93).unwrap().map(<[_]>::len)));
            out.push(format!("{}", call.widgets().unwrap()[0].group()));
            out.push(call.chat().unwrap()[0].text.clone());
            Ok(PluginOutput::default())
        }
    }
    let out = Rc::new(RefCell::new(Vec::new()));
    let mut set = PluginSet::new();
    set.register(
        manifest(
            "dev.rustclient.reader",
            &[
                Capability::GameReadPlayers,
                Capability::GameReadNpcs,
                Capability::GameReadVars,
                Capability::GameReadClient,
                Capability::GameReadInventory,
                Capability::GameReadWidgets,
                Capability::GameReadChat,
            ],
        ),
        Box::new(Reader {
            out: Rc::clone(&out),
        }),
    )
    .unwrap();
    let game = snapshot();
    let mut storage = MemoryStorage::new();
    let mut logs = MemoryLogSink::default();
    let _ = set.collect_output(&SnapshotView(&game), &mut storage, &mut logs);
    assert_eq!(
        *out.borrow(),
        [
            "Some(\"Zezima\")",
            "4",
            "Some(1)",
            "None",
            "5",
            "50",
            "Some(1)",
            "161",
            "Welcome"
        ]
    );
    assert!(logs.records.is_empty(), "{:?}", logs.records);
}

#[test]
fn events_are_filtered_to_the_grants_of_each_plugin() {
    let game = snapshot();
    let mut harness = Harness::new();
    let none = harness.add("dev.rustclient.none", &[], PluginOutput::default(), false);
    let npcs = harness.add(
        "dev.rustclient.npcs",
        &[Capability::GameReadNpcs],
        PluginOutput::default(),
        false,
    );
    let menu = harness.add(
        "dev.rustclient.menu",
        &[Capability::UiMenuSwap, Capability::GameReadVars],
        PluginOutput::default(),
        false,
    );
    let npc = game.npcs[0].clone();
    let events = [
        GameEvent::GameTick(12),
        GameEvent::NpcSpawned(Box::new(npc)),
        GameEvent::AnimationChanged {
            actor: ActorRef::Npc(5),
            animation: Some(422),
        },
        GameEvent::AnimationChanged {
            actor: ActorRef::Player(1),
            animation: Some(808),
        },
        GameEvent::VarbitChanged {
            kind: rustclient_plugin_api::VarKind::Varp,
            id: 7,
            value: 4,
        },
        GameEvent::PostMenuSort(Vec::new()),
        GameEvent::ChatMessage(game.chat[0].clone()),
    ];
    harness.set.dispatch_game(
        &events,
        &SnapshotView(&game),
        &mut harness.storage,
        &mut harness.logs,
    );
    assert_eq!(none.borrow().events, [GameEvent::GameTick(12)]);
    assert_eq!(npcs.borrow().events.len(), 3);
    assert!(matches!(
        npcs.borrow().events[2],
        GameEvent::AnimationChanged {
            actor: ActorRef::Npc(5),
            ..
        }
    ));
    assert_eq!(
        menu.borrow()
            .events
            .iter()
            .map(std::mem::discriminant)
            .collect::<Vec<_>>(),
        [
            GameEvent::GameTick(0),
            GameEvent::VarbitChanged {
                kind: rustclient_plugin_api::VarKind::Varp,
                id: 0,
                value: 0
            },
            GameEvent::PostMenuSort(Vec::new())
        ]
        .iter()
        .map(std::mem::discriminant)
        .collect::<Vec<_>>()
    );
}

#[path = "api_v2/lifetimes.rs"]
mod lifetimes;
#[path = "api_v2/outputs.rs"]
mod outputs;
