//! End-to-end projection: committed `GameState` operations become snapshots and ordered events.
use rustclient_cache::{
    GroupId, IndexId, Source,
    osrs240::definitions,
    world::{NpcDefinition, VarbitDefinition},
};
use rustclient_game_state::{
    AuthenticatedPlayer, GameState, InitialPlacement, SceneryOperation, SemanticOperation,
};
use rustclient_model::{
    GameMessage, InventoryUpdate, NewNpc, NpcAttributeUpdate, NpcDefinitionId, NpcIndex,
    NpcInfoUpdate, NpcProperty, Plane, PlayerIndex, PlayerStatusUpdate, SceneAssetId,
    SceneDescriptor, SequenceId, SkillId, SkillValues, TickSpan, TileOffset, VarbitId, VarpId,
    WorldTile,
    ids::{ChatChannelId, InventoryId, ItemDefinitionId},
};
use rustclient_plugin_api::{
    ActorRef, Capability, CapabilitySet, ChatKind, GameEvent, GameStateKind, Skill, VarKind,
};
use rustclient_plugin_bridge::{Frame, Projector, Sources};
use rustclient_protocol::ServerProfile;
use std::collections::BTreeMap;

fn everything() -> CapabilitySet {
    [
        Capability::GameReadPlayers,
        Capability::GameReadNpcs,
        Capability::GameReadSkills,
        Capability::GameReadVars,
        Capability::GameReadInventory,
        Capability::GameReadChat,
        Capability::GameReadWidgets,
        Capability::GameReadClient,
    ]
    .into_iter()
    .collect()
}
#[test]
fn bootstrap_reads_current_facts_without_consuming_another_observers_history() {
    let mut state = session();
    let mut projector = Projector::new();
    let frame = Frame::default();
    let sources = Sources::default();
    let wanted = everything();
    let _ = projector.project(&state, &frame, &sources, &wanted);
    apply(
        &mut state,
        SemanticOperation::Scenery(SceneryOperation::SetVariable {
            id: VarpId::new(42),
            value: 7,
        }),
    );
    let before = state.semantic_fingerprint();
    let bootstrap = projector.snapshot(&state, &frame, &sources, &wanted);
    assert_eq!(bootstrap.vars.varp(42), 7);
    assert_eq!(before, state.semantic_fingerprint());
    let (_, events) = projector.project(&state, &frame, &sources, &wanted);
    assert!(events.iter().any(|event| matches!(
        event,
        GameEvent::VarbitChanged {
            kind: VarKind::Varp,
            id: 42,
            value: 7
        }
    )));
    assert!(
        projector
            .project(&state, &frame, &sources, &wanted)
            .1
            .is_empty()
    );
}

fn apply(state: &mut GameState, operation: SemanticOperation) {
    state.apply_operation(operation).expect("operation commits");
}

fn session() -> GameState {
    let mut state = GameState::new();
    apply(
        &mut state,
        SemanticOperation::AuthenticateSession {
            revision: ServerProfile::OPENRUNE_240_2.revision(),
            player: AuthenticatedPlayer {
                profile: ServerProfile::OPENRUNE_240_2.id(),
                player: PlayerIndex::new(1),
                member: false,
                staff_level: 0,
            },
        },
    );
    apply(
        &mut state,
        SemanticOperation::EstablishInitialPlacement(InitialPlacement {
            position: WorldTile::new(3, 3, Plane::MIN),
            map_anchor: WorldTile::new(3, 3, Plane::MIN),
            area: None,
        }),
    );
    apply(
        &mut state,
        SemanticOperation::InstallScene(
            SceneDescriptor::new(
                SceneAssetId::new([7; 32]),
                WorldTile::new(0, 0, Plane::MIN),
                8,
                8,
            )
            .expect("scene"),
        ),
    );
    state
}

#[test]
#[expect(
    clippy::too_many_lines,
    reason = "one ordered session scenario shares a single projector history"
)]
fn state_operations_project_to_snapshots_and_ordered_events() {
    let npc_definition = definitions::npc(
        b"\x02Banker\0\x20Bank\0\0",
        NpcDefinitionId::new(12),
        Source::group(IndexId(2), GroupId(9)),
    )
    .expect("definition");
    let npcs: BTreeMap<NpcDefinitionId, NpcDefinition> =
        BTreeMap::from([(npc_definition.id, npc_definition)]);
    let varbits: BTreeMap<VarbitId, VarbitDefinition> = BTreeMap::from([(
        VarbitId::new(9),
        VarbitDefinition {
            varp: VarpId::new(172),
            low: 1,
            high: 2,
        },
    )]);
    let resolve = |id: NpcDefinitionId| npcs.get(&id);
    let sources = Sources {
        npcs: Some(&resolve),
        varbits: Some(&varbits),
        interfaces: None,
    };
    let frame = Frame {
        fps: 50,
        focused: true,
        ..Frame::default()
    };
    let wanted = everything();
    let mut projector = Projector::new();

    // A logged-out client projects an empty world and reports nothing.
    let mut state = GameState::new();
    let (snapshot, events) = projector.project(&state, &frame, &sources, &wanted);
    assert_eq!(snapshot.client.state, GameStateKind::LoginScreen);
    assert!(events.is_empty(), "{events:?}");

    state = session();
    apply(
        &mut state,
        SemanticOperation::UpdateNpcs(NpcInfoUpdate {
            origin: Some(TileOffset { x: -1, y: -1 }),
            new_npcs: vec![NewNpc {
                index: NpcIndex::new(42),
                definition_id: NpcDefinitionId::new(12),
                delta_x: 1,
                delta_y: 1,
                orientation: 0,
            }],
            ..Default::default()
        }),
    );
    let (snapshot, events) = projector.project(&state, &frame, &sources, &wanted);
    assert_eq!(snapshot.client.state, GameStateKind::LoggedIn);
    assert_eq!(snapshot.client.fps, 50);
    assert_eq!(snapshot.local_player, Some(1));
    let npc = snapshot.npc(42).expect("npc projected");
    assert_eq!(npc.name.as_deref(), Some("Banker"));
    assert_eq!((npc.id, npc.actor.world.x, npc.actor.world.y), (12, 3, 3));
    assert_eq!(npc.actions[2].as_deref(), Some("Bank"));
    assert!(matches!(
        events.first(),
        Some(GameEvent::GameStateChanged(GameStateKind::LoggedIn))
    ));
    assert!(
        events
            .iter()
            .any(|e| matches!(e, GameEvent::NpcSpawned(n) if n.actor.index == 42))
    );

    // Nothing changed: no events, and the projection is stable.
    let (_, quiet) = projector.project(&state, &frame, &sources, &wanted);
    assert!(quiet.is_empty(), "{quiet:?}");

    apply(
        &mut state,
        SemanticOperation::UpdateNpcs(NpcInfoUpdate {
            attributes: vec![NpcAttributeUpdate {
                index: NpcIndex::new(42),
                changes: vec![NpcProperty::Sequence {
                    sequence: Some(SequenceId::new(808)),
                    delay: 0,
                }],
            }],
            ..Default::default()
        }),
    );
    apply(
        &mut state,
        SemanticOperation::PlayerStatus(PlayerStatusUpdate::Skill {
            id: SkillId::new(3),
            values: SkillValues {
                experience: 1_154,
                current: 12,
                invisible: 0,
            },
        }),
    );
    apply(
        &mut state,
        SemanticOperation::Scenery(SceneryOperation::SetVariable {
            id: VarpId::new(172),
            value: 0b100,
        }),
    );
    apply(
        &mut state,
        SemanticOperation::Inventory(InventoryUpdate::Replace {
            id: InventoryId::new(93),
            slots: vec![Some(rustclient_model::ItemStack {
                item: ItemDefinitionId::new(995),
                amount: 10,
            })],
        }),
    );
    apply(
        &mut state,
        SemanticOperation::GameMessage(GameMessage {
            channel: ChatChannelId::new(0),
            sender: String::new(),
            text: "Welcome".into(),
        }),
    );
    apply(&mut state, SemanticOperation::AdvanceTick(TickSpan::new(1)));
    let (snapshot, events) = projector.project(&state, &frame, &sources, &wanted);

    assert_eq!(snapshot.npc(42).unwrap().actor.animation, Some(808));
    let hitpoints = snapshot.skills[Skill::Hitpoints.index()];
    assert_eq!(
        (hitpoints.real, hitpoints.boosted, hitpoints.experience),
        (10, 12, 1_154)
    );
    assert_eq!(snapshot.vars.varp(172), 0b100);
    assert_eq!(snapshot.vars.varbit(9), Some(0b10));
    assert_eq!(snapshot.containers[&93][0].map(|s| s.quantity), Some(10));
    assert_eq!(
        snapshot.chat.last().map(|l| (l.kind, l.text.as_str())),
        Some((ChatKind::Game, "Welcome"))
    );

    let position = |predicate: &dyn Fn(&GameEvent) -> bool| {
        events
            .iter()
            .position(predicate)
            .unwrap_or_else(|| panic!("missing event in {events:?}"))
    };
    let tick = position(&|e| matches!(e, GameEvent::GameTick(_)));
    let animation = position(&|e| {
        matches!(
            e,
            GameEvent::AnimationChanged {
                actor: ActorRef::Npc(42),
                animation: Some(808)
            }
        )
    });
    let varp = position(&|e| {
        matches!(
            e,
            GameEvent::VarbitChanged {
                kind: VarKind::Varp,
                id: 172,
                value: 4
            }
        )
    });
    let varbit = position(&|e| {
        matches!(
            e,
            GameEvent::VarbitChanged {
                kind: VarKind::Varbit,
                id: 9,
                value: 2
            }
        )
    });
    let stat = position(&|e| {
        matches!(
            e,
            GameEvent::StatChanged {
                skill: Skill::Hitpoints,
                ..
            }
        )
    });
    let container = position(&|e| matches!(e, GameEvent::ItemContainerChanged { container: 93 }));
    let chat = position(&|e| matches!(e, GameEvent::ChatMessage(line) if line.text == "Welcome"));
    assert!(
        tick < animation
            && animation < varp
            && varp < varbit
            && varbit < stat
            && stat < container
            && container < chat,
        "event order: {events:?}"
    );

    // Ending the session despawns everything and reports the lifecycle change.
    apply(&mut state, SemanticOperation::EndSession);
    let (snapshot, events) = projector.project(&state, &frame, &sources, &wanted);
    assert_eq!(snapshot.client.state, GameStateKind::LoginScreen);
    assert!(events.iter().any(|e| matches!(e, GameEvent::NpcDespawned(n) if n.actor.index == 42 && n.name.as_deref() == Some("Banker"))));
    assert!(matches!(
        events.first(),
        Some(GameEvent::GameStateChanged(GameStateKind::LoginScreen))
    ));
}

#[test]
fn only_granted_categories_are_projected() {
    let state = session();
    let mut projector = Projector::new();
    let wanted: CapabilitySet = [Capability::GameReadClient].into_iter().collect();
    let (snapshot, events) =
        projector.project(&state, &Frame::default(), &Sources::default(), &wanted);
    assert!(snapshot.players.is_empty() && snapshot.npcs.is_empty());
    assert_eq!(snapshot.local_player, None);
    assert!(snapshot.containers.is_empty() && snapshot.chat.is_empty());
    assert!(
        events
            .iter()
            .all(|e| !matches!(e, GameEvent::PlayerSpawned(_)))
    );
}

#[test]
fn no_subscriber_advances_history_without_replaying_events_when_one_appears() {
    let mut state = session();
    let mut projector = Projector::new();
    let wanted: CapabilitySet = [Capability::GameReadVars].into_iter().collect();
    let frame = Frame::default();
    let sources = Sources::default();
    let fingerprint = state.semantic_fingerprint();
    let (_, events) = projector.project_with_events(&state, &frame, &sources, &wanted, false);
    assert!(events.is_empty());
    assert_eq!(fingerprint, state.semantic_fingerprint());
    apply(
        &mut state,
        SemanticOperation::Scenery(SceneryOperation::SetVariable {
            id: VarpId::new(42),
            value: 7,
        }),
    );
    let (_, events) = projector.project_with_events(&state, &frame, &sources, &wanted, false);
    assert!(events.is_empty());
    let (_, events) = projector.project_with_events(&state, &frame, &sources, &wanted, true);
    assert!(
        events.is_empty(),
        "subscribing replayed an already committed change: {events:?}"
    );
}

#[test]
fn observing_with_no_grants_prevents_a_synthetic_login_when_a_plugin_enables_later() {
    let state = session();
    let mut projector = Projector::new();
    let frame = Frame::default();
    let sources = Sources::default();
    let _ =
        projector.project_with_events(&state, &frame, &sources, &CapabilitySet::default(), false);
    let wanted = [Capability::GameReadClient].into_iter().collect();
    let bootstrap = projector.snapshot(&state, &frame, &sources, &wanted);
    assert_ne!(bootstrap.client.state, GameStateKind::LoginScreen);
    let (_, events) = projector.project_with_events(&state, &frame, &sources, &wanted, true);
    assert!(
        events.is_empty(),
        "enabling a plugin manufactured a session transition: {events:?}"
    );
}

#[test]
fn query_only_categories_keep_current_snapshots_without_events_or_replay_on_subscription() {
    let mut state = session();
    let mut projector = Projector::new();
    let frame = Frame::default();
    let sources = Sources::default();
    let query = everything();
    let interest = [Capability::GameReadClient].into_iter().collect();
    let _ = projector.project_interested(&state, &frame, &sources, &query, Some(&interest));
    apply(
        &mut state,
        SemanticOperation::Scenery(SceneryOperation::SetVariable {
            id: VarpId::new(42),
            value: 7,
        }),
    );
    let (snapshot, events) =
        projector.project_interested(&state, &frame, &sources, &query, Some(&interest));
    assert_eq!(snapshot.vars.varp(42), 7);
    assert!(events.is_empty());
    let (_, events) = projector.project_interested(&state, &frame, &sources, &query, Some(&query));
    assert!(
        events.is_empty(),
        "subscribing replayed query-only history: {events:?}"
    );
}

#[test]
fn the_pointer_tile_and_the_walk_destination_reach_the_client_snapshot_on_the_players_plane() {
    let mut state = session();
    let mut projector = Projector::new();
    let sources = Sources::default();
    let wanted = everything();
    let quiet = projector.snapshot(&state, &Frame::default(), &sources, &wanted);
    assert_eq!(quiet.client.hovered_tile, None);
    assert_eq!(quiet.client.destination, None);

    apply(
        &mut state,
        SemanticOperation::SetDestination(Some(rustclient_model::MapDestination { x: 5, y: 6 })),
    );
    let frame = Frame {
        hovered_tile: Some(WorldTile::new(4, 2, Plane::MIN)),
        ..Frame::default()
    };
    let client = projector.snapshot(&state, &frame, &sources, &wanted).client;
    assert_eq!(
        client.hovered_tile,
        Some(rustclient_plugin_api::WorldPoint::new(4, 2, 0))
    );
    assert_eq!(
        client.destination,
        Some(rustclient_plugin_api::WorldPoint::new(5, 6, 0))
    );
}

#[test]
fn the_minimap_zoom_reaches_the_client_snapshot_in_pixels_per_tile() {
    let mut state = session();
    let mut projector = Projector::new();
    let sources = Sources::default();
    let wanted = everything();
    let zoom = |state: &rustclient_game_state::GameState, projector: &mut Projector| {
        projector
            .snapshot(state, &Frame::default(), &sources, &wanted)
            .client
            .minimap_zoom
    };
    assert!((zoom(&state, &mut projector) - 4.).abs() < f64::EPSILON);
    for update in [
        rustclient_model::MinimapUpdate::Zoomable(true),
        rustclient_model::MinimapUpdate::ZoomQuarters(26),
    ] {
        apply(
            &mut state,
            SemanticOperation::Interface(rustclient_model::InterfaceUpdate::Minimap(update)),
        );
    }
    assert!((zoom(&state, &mut projector) - 6.5).abs() < f64::EPSILON);
}
