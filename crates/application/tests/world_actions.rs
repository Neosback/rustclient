//! Semantic interaction identity/permission contracts, independent of GPU and live networking.
use rustclient_application::{validate_walk, validate_world_action, world_actions};
use rustclient_cache::{
    GroupId, IndexId, Source,
    osrs240::{definitions, entity_assets::EntityAssets},
    world::{MapSquare, TerrainTile, WorldAssets},
};
use rustclient_game_state::{
    AuthenticatedPlayer, GameState, InitialPlacement, ReplayEnvelope, SceneryOperation,
    SemanticOperation,
};
use rustclient_model::{
    ActionMask, ActionSlot, NewNpc, NpcAttributeUpdate, NpcDefinitionId, NpcIndex, NpcInfoUpdate,
    NpcMovement, NpcProperty, Plane, PlayerAppearance, PlayerIndex, PlayerInfoUpdate,
    PlayerInstanceId, PlayerOpSlot, PlayerStatusUpdate, PlayerTrackingGrid, RegionCoord,
    RemotePlayerUpdate, SequenceChange, SequenceId, TileOffset, WorldTarget, WorldTile,
};
use rustclient_protocol::{ClientIntent, ExamineTarget, ServerProfile};
use rustclient_scene::{SceneAsset, entity_mesh::EntityCatalog};
use std::collections::BTreeMap;
fn asset() -> SceneAsset {
    SceneAsset::new(
        WorldAssets {
            squares: vec![MapSquare {
                coord: RegionCoord::new(0, 0),
                source: rustclient_cache::world::MapSquareSource::Archive,
                tiles: vec![TerrainTile::default(); 4 * 64 * 64],
                locations: vec![],
            }],
            underlays: BTreeMap::new(),
            overlays: BTreeMap::new(),
            objects: BTreeMap::new(),
            models: BTreeMap::new(),
            texture_colors: BTreeMap::new(),
            map_scenes: Vec::new(),
            map_dots: Vec::new(),
            map_icons: BTreeMap::new(),
            textures: std::sync::Arc::default(),
            sequences: BTreeMap::new(),
            frames: BTreeMap::new(),
            skeletons: BTreeMap::new(),
            varbits: BTreeMap::new(),
            kits: BTreeMap::new(),
            metadata_fingerprint: "synthetic interaction fixture".into(),
            content_hash: [9; 32],
            auxiliary_map_files: 0,
            size_hint_warnings: vec![],
        },
        WorldTile::new(0, 0, Plane::MIN),
        8,
        8,
    )
    .unwrap()
}
fn new_npc() -> NewNpc {
    NewNpc {
        index: NpcIndex::new(42),
        definition_id: NpcDefinitionId::new(12),
        delta_x: 1,
        delta_y: 1,
        orientation: 0,
    }
}
fn apply(state: &mut GameState, recorded: &mut Vec<ReplayEnvelope>, operation: SemanticOperation) {
    let commit = state.apply_operation(operation.clone()).unwrap();
    recorded.push(ReplayEnvelope::new(operation, commit));
}
#[test]
#[expect(
    clippy::too_many_lines,
    reason = "one NPC identity/permission/replay contract against synthetic assets"
)]
fn npc_batch_identity_permissions_delay_and_replay_reject_stale_actions_atomically() {
    let asset = asset();
    let mut catalog = EntityCatalog::default();
    let def = definitions::npc(
        b"\x02Banker\0\x20Bank\0\0",
        NpcDefinitionId::new(12),
        Source::group(IndexId(2), GroupId(9)),
    )
    .unwrap();
    let mut batch = EntityAssets::default();
    batch.npcs.insert(def.id, def);
    assert!(catalog.merge(&asset, batch).is_empty());
    let mut state = GameState::new();
    let mut recorded = Vec::new();
    for operation in [
        SemanticOperation::AuthenticateSession {
            revision: ServerProfile::OPENRUNE_240_2.revision(),
            player: AuthenticatedPlayer {
                profile: ServerProfile::OPENRUNE_240_2.id(),
                player: PlayerIndex::new(1),
                member: false,
                staff_level: 0,
            },
        },
        SemanticOperation::EstablishInitialPlacement(InitialPlacement {
            position: WorldTile::new(3, 3, Plane::MIN),
            map_anchor: WorldTile::new(3, 3, Plane::MIN),
            area: None,
        }),
        SemanticOperation::InstallScene(asset.descriptor()),
        SemanticOperation::UpdateNpcs(NpcInfoUpdate {
            origin: Some(TileOffset { x: -1, y: -1 }),
            new_npcs: vec![new_npc()],
            ..Default::default()
        }),
    ] {
        apply(&mut state, &mut recorded, operation);
    }
    let npc = &state.entities().npcs()[&NpcIndex::new(42)];
    assert_eq!(npc.tile, WorldTile::new(3, 3, Plane::MIN));
    let target = WorldTarget::Npc {
        index: npc.index,
        instance: npc.instance,
        definition: npc.definition_id,
        resolved: npc.definition_id,
    };
    let actions = world_actions(&state, &asset, &catalog, asset.descriptor(), target).unwrap();
    // The definition's own action, then the client-added Examine.
    assert_eq!(actions.len(), 2);
    assert_eq!(actions[0].0, "Bank <col=ffff00>Banker");
    assert_eq!(actions[1].0, "Examine <col=ffff00>Banker");
    assert!(actions[1].1.slot.is_examine());
    assert!(matches!(
        validate_world_action(&state, &asset, &catalog, actions[1].1, false).unwrap(),
        ClientIntent::Examine(ExamineTarget::Npc(definition)) if definition == npc.definition_id
    ));
    let selected = actions[0].1;
    assert!(
        matches!(validate_world_action(&state,&asset,&catalog,selected,false).unwrap(),ClientIntent::NpcAction {index,slot,..} if index==NpcIndex::new(42) && slot==ActionSlot::new(2).unwrap())
    );
    let change = SemanticOperation::UpdateNpcs(NpcInfoUpdate {
        movements: vec![NpcMovement {
            index: NpcIndex::new(42),
            first: TileOffset { x: 1, y: 0 },
            second: None,
            speed: rustclient_model::MoveSpeed::Walk,
        }],
        attributes: vec![NpcAttributeUpdate {
            index: NpcIndex::new(42),
            changes: vec![
                NpcProperty::Sequence {
                    sequence: Some(SequenceId::new(808)),
                    delay: 2,
                },
                NpcProperty::Name(Some("Alice".into())),
                NpcProperty::Actions(ActionMask::new([false; 5])),
            ],
        }],
        ..Default::default()
    });
    apply(&mut state, &mut recorded, change);
    assert!(validate_world_action(&state, &asset, &catalog, selected, false).is_err());
    apply(
        &mut state,
        &mut recorded,
        SemanticOperation::Scenery(SceneryOperation::AdvanceCycles(5)),
    );
    let npc = &state.entities().npcs()[&NpcIndex::new(42)];
    assert_eq!((npc.sequence.delay, npc.sequence.cycle), (0, 3));
    assert_eq!(npc.name.as_deref(), Some("Alice"));
    let before = state.semantic_fingerprint();
    // A removal must not commit when a later member of that same operation fails.
    assert!(
        state
            .apply_operation(SemanticOperation::UpdateNpcs(NpcInfoUpdate {
                removals: vec![NpcIndex::new(42)],
                new_npcs: vec![new_npc(), new_npc()],
                ..Default::default()
            }))
            .is_err()
    );
    assert_eq!(before, state.semantic_fingerprint());
    apply(
        &mut state,
        &mut recorded,
        SemanticOperation::UpdateNpcs(NpcInfoUpdate {
            removals: vec![NpcIndex::new(42)],
            new_npcs: vec![new_npc()],
            ..Default::default()
        }),
    );
    assert!(validate_world_action(&state, &asset, &catalog, selected, false).is_err());
    assert_ne!(
        state.entities().npcs()[&NpcIndex::new(42)].instance,
        match target {
            WorldTarget::Npc { instance, .. } => instance,
            WorldTarget::Location { .. }
            | WorldTarget::GroundItem { .. }
            | WorldTarget::Player { .. } => unreachable!(),
        }
    );
    let mut replay = GameState::new();
    replay.replay_envelopes(&recorded).unwrap();
    assert_eq!(replay.semantic_fingerprint(), state.semantic_fingerprint());
    state
        .apply_operation(SemanticOperation::EndSession)
        .unwrap();
    assert_eq!(
        state.semantic_fingerprint(),
        GameState::new().semantic_fingerprint()
    );
}

#[test]
fn npc_morph_chains_resolve_nested_defaults_and_stop_cycles() {
    let asset = asset();
    let mut catalog = EntityCatalog::default();
    let mut batch = EntityAssets::default();
    for (id, bytes) in [
        (1, &[106, 255, 255, 0, 7, 0, 0, 2, 0][..]),
        (2, &[106, 255, 255, 0, 8, 0, 0, 3, 0][..]),
        (3, &b"\x02Final form\0\0"[..]),
        (4, &[106, 255, 255, 0, 7, 0, 0, 4, 0][..]),
    ] {
        let def = definitions::npc(
            bytes,
            NpcDefinitionId::new(id),
            Source::group(IndexId(2), GroupId(9)),
        )
        .unwrap();
        batch.npcs.insert(def.id, def);
    }
    assert!(catalog.merge(&asset, batch).is_empty());
    let mut variables = BTreeMap::new();
    assert_eq!(
        catalog
            .resolve_npc(&asset, NpcDefinitionId::new(1), &variables)
            .unwrap()
            .id,
        NpcDefinitionId::new(3)
    );
    variables.insert(rustclient_model::VarpId::new(8), -1);
    assert!(
        catalog
            .resolve_npc(&asset, NpcDefinitionId::new(1), &variables)
            .is_none()
    );
    assert!(
        catalog
            .resolve_npc(&asset, NpcDefinitionId::new(4), &variables)
            .is_none()
    );
}

#[test]
fn plane_transitions_reuse_scene_asset_and_validate_walk_and_actions_on_upper_planes() {
    use rustclient_model::{PlayerInfoUpdate, PlayerMovement};
    let asset = asset();
    let mut catalog = EntityCatalog::default();
    let def = definitions::npc(
        b"\x02Guard\0\x1eAttack\0\0",
        NpcDefinitionId::new(12),
        Source::group(IndexId(2), GroupId(9)),
    )
    .unwrap();
    let mut batch = EntityAssets::default();
    batch.npcs.insert(def.id, def);
    assert!(catalog.merge(&asset, batch).is_empty());

    let mut state = GameState::new();
    let mut recorded = Vec::new();
    for operation in [
        SemanticOperation::AuthenticateSession {
            revision: ServerProfile::OPENRUNE_240_2.revision(),
            player: AuthenticatedPlayer {
                profile: ServerProfile::OPENRUNE_240_2.id(),
                player: PlayerIndex::new(1),
                member: false,
                staff_level: 0,
            },
        },
        SemanticOperation::EstablishInitialPlacement(InitialPlacement {
            position: WorldTile::new(3, 3, Plane::MIN),
            map_anchor: WorldTile::new(3, 3, Plane::MIN),
            area: None,
        }),
        SemanticOperation::InstallScene(asset.descriptor()),
        SemanticOperation::UpdatePlayers(PlayerInfoUpdate {
            local_movement: Some(PlayerMovement::Teleport {
                delta_x: 0,
                delta_y: 0,
                plane_delta: 1,
            }),
            ..Default::default()
        }),
        SemanticOperation::UpdateNpcs(NpcInfoUpdate {
            new_npcs: vec![new_npc()],
            ..Default::default()
        }),
    ] {
        apply(&mut state, &mut recorded, operation);
    }

    let plane_one = Plane::new(1).unwrap();
    let active_scene = state
        .loaded_scene()
        .expect("scene stays installed across plane change");
    assert_eq!(active_scene, asset.descriptor());
    let walk = validate_walk(
        &state,
        &asset,
        active_scene,
        WorldTile::new(4, 4, plane_one),
        false,
    )
    .expect("walk on upper plane validates against shared SceneAsset");
    assert!(matches!(
        walk,
        ClientIntent::Walk {
            destination,
            invert_run: false,
        } if destination == WorldTile::new(4, 4, plane_one)
    ));

    let npc = &state.entities().npcs()[&NpcIndex::new(42)];
    assert_eq!(npc.tile.plane, plane_one);
    let target = WorldTarget::Npc {
        index: npc.index,
        instance: npc.instance,
        definition: npc.definition_id,
        resolved: npc.definition_id,
    };
    let actions = world_actions(&state, &asset, &catalog, active_scene, target)
        .expect("NPC action on upper plane validates against shared SceneAsset");
    assert_eq!(actions.len(), 2);
    assert_eq!(actions[0].0, "Attack <col=ffff00>Guard");
    assert_eq!(actions[1].0, "Examine <col=ffff00>Guard");
}

#[test]
fn ground_item_options_follow_the_definition_mask_and_revalidate_against_the_pile() {
    use rustclient_game_state::GroundItemOperation;
    use rustclient_model::{
        GroundItemOwnership, GroundItemPosition, GroundItemUpdate, ItemDefinitionId,
    };
    let asset = asset();
    let mut catalog = EntityCatalog::default();
    // Name "Coins", ground op 1 "Light", op 3 left at the default "Take".
    let def = definitions::item(
        b"\x02Coins\0\x1eLight\0\0",
        ItemDefinitionId::new(995),
        Source::group(IndexId(2), GroupId(10)),
    )
    .unwrap();
    let mut batch = EntityAssets::default();
    batch.items.insert(def.id, def);
    assert!(catalog.merge(&asset, batch).is_empty());
    let mut state = GameState::new();
    let mut recorded = Vec::new();
    let tile = WorldTile::new(4, 3, Plane::MIN);
    for operation in [
        SemanticOperation::AuthenticateSession {
            revision: ServerProfile::OPENRUNE_240_2.revision(),
            player: AuthenticatedPlayer {
                profile: ServerProfile::OPENRUNE_240_2.id(),
                player: PlayerIndex::new(1),
                member: false,
                staff_level: 0,
            },
        },
        SemanticOperation::EstablishInitialPlacement(InitialPlacement {
            position: WorldTile::new(3, 3, Plane::MIN),
            map_anchor: WorldTile::new(3, 3, Plane::MIN),
            area: None,
        }),
        SemanticOperation::InstallScene(asset.descriptor()),
        SemanticOperation::GroundItems(GroundItemOperation::Update(GroundItemUpdate::Add {
            position: GroundItemPosition::Absolute(tile),
            item: ItemDefinitionId::new(995),
            quantity: 50,
            // The server hides op 1.
            ops: ActionMask::new([false, true, true, true, true]),
            ownership: GroundItemOwnership::NONE,
            ticks_until_public: 0,
            ticks_until_despawn: 100,
            never_public: false,
        })),
    ] {
        apply(&mut state, &mut recorded, operation);
    }
    let target = WorldTarget::GroundItem {
        tile,
        item: ItemDefinitionId::new(995),
    };
    let actions = world_actions(&state, &asset, &catalog, asset.descriptor(), target).unwrap();
    let labels: Vec<_> = actions.iter().map(|(label, _)| label.as_str()).collect();
    assert_eq!(
        labels,
        ["Take <col=ff9040>Coins", "Examine <col=ff9040>Coins"]
    );
    assert!(matches!(
        validate_world_action(&state, &asset, &catalog, actions[1].1, false).unwrap(),
        ClientIntent::Examine(ExamineTarget::GroundItem { tile: t, item })
            if t == tile && item.get() == 995
    ));
    assert!(matches!(
        validate_world_action(&state, &asset, &catalog, actions[0].1, true).unwrap(),
        ClientIntent::GroundItemAction { tile: t, item, slot, control: true }
            if t == tile && item.get() == 995 && slot == ActionSlot::new(2).unwrap()
    ));
    // Once the server deletes the item the selected entry no longer validates.
    apply(
        &mut state,
        &mut recorded,
        SemanticOperation::GroundItems(GroundItemOperation::Update(GroundItemUpdate::Delete {
            position: GroundItemPosition::Absolute(tile),
            item: ItemDefinitionId::new(995),
            quantity: 50,
        })),
    );
    assert!(validate_world_action(&state, &asset, &catalog, actions[0].1, false).is_err());
}

#[test]
#[expect(
    clippy::too_many_lines,
    reason = "one player-option contract from roster to validation against synthetic assets"
)]
fn player_options_list_every_server_option_and_revalidate_against_the_incarnation() {
    let asset = asset();
    let catalog = EntityCatalog::default();
    let mut state = GameState::new();
    let mut recorded = Vec::new();
    let other = PlayerIndex::new(2);
    let grid = PlayerTrackingGrid::new(8192, 256).unwrap();
    let remote = |updates| {
        SemanticOperation::UpdatePlayers(PlayerInfoUpdate {
            remote: updates,
            ..Default::default()
        })
    };
    for operation in [
        SemanticOperation::AuthenticateSession {
            revision: ServerProfile::OPENRUNE_240_2.revision(),
            player: AuthenticatedPlayer {
                profile: ServerProfile::OPENRUNE_240_2.id(),
                player: PlayerIndex::new(1),
                member: false,
                staff_level: 0,
            },
        },
        SemanticOperation::EstablishInitialPlacement(InitialPlacement {
            position: WorldTile::new(3, 3, Plane::MIN),
            map_anchor: WorldTile::new(3, 3, Plane::MIN),
            area: None,
        }),
        SemanticOperation::InstallScene(asset.descriptor()),
        remote(vec![RemotePlayerUpdate::Track {
            index: other,
            grid,
            region: WorldTile::new(0, 0, Plane::MIN),
        }]),
        remote(vec![
            RemotePlayerUpdate::Enter {
                index: other,
                offset: TileOffset { x: 4, y: 3 },
            },
            RemotePlayerUpdate::Attributes {
                index: other,
                appearance: Some(PlayerAppearance {
                    display_name: "Zezima".into(),
                    combat_level: 126,
                    ..Default::default()
                }),
                sequence: SequenceChange::Unchanged,
                delay: 0,
                facing: None,
                path_speed: None,
                exact_move: None,
            },
        ]),
        SemanticOperation::PlayerStatus(PlayerStatusUpdate::PlayerOption {
            slot: 2,
            text: Some("Follow".into()),
            priority: true,
        }),
        SemanticOperation::PlayerStatus(PlayerStatusUpdate::PlayerOption {
            slot: 7,
            text: Some("Trade with".into()),
            priority: true,
        }),
    ] {
        apply(&mut state, &mut recorded, operation);
    }
    let instance = state.entities().players()[&other].instance;
    let target = WorldTarget::Player {
        index: other,
        instance,
        op: PlayerOpSlot::new(1).unwrap(),
    };
    let actions = world_actions(&state, &asset, &catalog, asset.descriptor(), target).unwrap();
    let labels: Vec<_> = actions.iter().map(|(label, _)| label.as_str()).collect();
    assert_eq!(
        labels,
        [
            "Follow <col=ffffff>Zezima (level-126)",
            "Trade with <col=ffffff>Zezima (level-126)"
        ]
    );
    // Each row carries its own option, and choosing it encodes OPPLAYER<n>.
    let intents: Vec<_> = actions
        .iter()
        .map(|(_, action)| validate_world_action(&state, &asset, &catalog, *action, true).unwrap())
        .collect();
    assert!(matches!(
        intents[0],
        ClientIntent::PlayerAction { index, slot, control: true }
            if index == other && slot.get() == 2
    ));
    assert!(matches!(
        intents[1],
        ClientIntent::PlayerAction { slot, .. } if slot.get() == 7
    ));
    // Removing the option withdraws its row, and an entry chosen before that is rejected.
    apply(
        &mut state,
        &mut recorded,
        SemanticOperation::PlayerStatus(PlayerStatusUpdate::PlayerOption {
            slot: 2,
            text: None,
            priority: true,
        }),
    );
    assert!(validate_world_action(&state, &asset, &catalog, actions[0].1, false).is_err());
    assert_eq!(
        world_actions(&state, &asset, &catalog, asset.descriptor(), target)
            .unwrap()
            .len(),
        1
    );
    // The local player has no menu, and a reused index (new incarnation) is stale.
    let own = WorldTarget::Player {
        index: PlayerIndex::new(1),
        instance: state.entities().local_player().unwrap().instance,
        op: PlayerOpSlot::new(1).unwrap(),
    };
    assert!(world_actions(&state, &asset, &catalog, asset.descriptor(), own).is_err());
    let reused = WorldTarget::Player {
        index: other,
        instance: PlayerInstanceId::new(instance.get() + 1000),
        op: PlayerOpSlot::new(1).unwrap(),
    };
    assert!(world_actions(&state, &asset, &catalog, asset.descriptor(), reused).is_err());
}
