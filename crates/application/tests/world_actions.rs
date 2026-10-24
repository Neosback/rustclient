//! Semantic interaction identity/permission contracts, independent of GPU and live networking.
use rustclient_application::{validate_world_action, world_actions};
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
    NpcMovement, NpcProperty, Plane, PlayerIndex, RegionCoord, SequenceId, TileOffset, WorldTarget,
    WorldTile,
};
use rustclient_protocol::{ClientIntent, ServerProfile};
use rustclient_scene::{SceneAsset, entity_mesh::EntityCatalog};
use std::collections::BTreeMap;
fn asset() -> SceneAsset {
    SceneAsset::new(
        WorldAssets {
            squares: vec![MapSquare {
                coord: RegionCoord::new(0, 0),
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
            textures: BTreeMap::new(),
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
    assert_eq!(actions.len(), 1);
    assert_eq!(actions[0].0, "Bank Banker");
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
            WorldTarget::Location { .. } => unreachable!(),
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
