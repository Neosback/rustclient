use rustclient_game_state::{
    AuthenticatedPlayer, GameState, InitialPlacement, ReplayEnvelope, SceneryOperation,
    SemanticOperation,
};
use rustclient_model::*;
fn initial() -> GameState {
    let mut state = GameState::new();
    state
        .apply_operation(SemanticOperation::AuthenticateSession {
            revision: REVISION_240,
            player: AuthenticatedPlayer {
                profile: ServerProfileId::new(1),
                player: PlayerIndex::new(1),
                member: false,
                staff_level: 0,
            },
        })
        .unwrap();
    state
        .apply_operation(SemanticOperation::EstablishInitialPlacement(
            InitialPlacement {
                position: WorldTile::new(3220, 3220, Plane::MIN),
                map_anchor: WorldTile::new(3216, 3216, Plane::MIN),
                area: None,
            },
        ))
        .unwrap();
    state
}
fn spawn(index: NpcIndex) -> NewNpc {
    NewNpc {
        index,
        definition_id: NpcDefinitionId::new(12),
        delta_x: 0,
        delta_y: 0,
        orientation: 0,
    }
}
#[test]
#[expect(
    clippy::too_many_lines,
    reason = "one recorded NPC movement, forced trajectory, rejection and lifetime contract"
)]
fn ordered_paths_forced_anchors_replay_removal_and_invalid_tails_are_authoritative() {
    let mut state = initial();
    let index = NpcIndex::new(42);
    let mut recording = Vec::new();
    let mut apply = |state: &mut GameState, operation: SemanticOperation| {
        let commit = state.apply_operation(operation.clone()).unwrap();
        recording.push(ReplayEnvelope::new(operation, commit));
    };
    apply(
        &mut state,
        SemanticOperation::UpdateNpcs(NpcInfoUpdate {
            new_npcs: vec![spawn(index)],
            ..Default::default()
        }),
    );
    let first_instance = state.entities().npcs()[&index].instance;
    apply(
        &mut state,
        SemanticOperation::Scenery(SceneryOperation::AdvanceCycles(10)),
    );
    let run = NpcMovement {
        index,
        first: TileOffset { x: 1, y: 0 },
        second: Some(TileOffset { x: -1, y: 0 }),
        speed: MoveSpeed::Run,
    };
    apply(
        &mut state,
        SemanticOperation::UpdateNpcs(NpcInfoUpdate {
            movements: vec![run.clone()],
            ..Default::default()
        }),
    );
    let npc = &state.entities().npcs()[&index];
    let path = npc.path.unwrap();
    assert_eq!(path.first, WorldTile::new(3221, 3220, Plane::MIN));
    assert_eq!(path.second, Some(npc.tile));
    assert_eq!(
        npc.orientation, 512,
        "last step decides heading even when the endpoint is unchanged"
    );
    apply(
        &mut state,
        SemanticOperation::UpdateNpcs(NpcInfoUpdate {
            movements: vec![run],
            ..Default::default()
        }),
    );
    assert_eq!(
        state.entities().npcs()[&index].path.unwrap().serial,
        path.serial + 1
    );
    let crawl = NpcMovement {
        index,
        first: TileOffset { x: 0, y: 1 },
        second: None,
        speed: MoveSpeed::Crawl,
    };
    let exact = ExactMove {
        start_delta_x: 1,
        start_delta_y: 0,
        end_delta_x: 2,
        end_delta_y: 0,
        start_delay: 2,
        end_delay: 8,
        direction: 1024,
    };
    apply(
        &mut state,
        SemanticOperation::UpdateNpcs(NpcInfoUpdate {
            movements: vec![crawl.clone()],
            attributes: vec![NpcAttributeUpdate {
                index,
                changes: vec![NpcProperty::ExactMove(exact)],
            }],
            ..Default::default()
        }),
    );
    let npc = &state.entities().npcs()[&index];
    assert_eq!(npc.path.unwrap().speed, MoveSpeed::Crawl);
    let forced = npc.exact_move.unwrap();
    assert_eq!(forced.start, WorldTile::new(3221, 3221, Plane::MIN));
    assert_eq!(forced.end, WorldTile::new(3222, 3221, Plane::MIN));
    assert_eq!((forced.starts_at.get(), forced.ends_at.get()), (12, 18));
    apply(
        &mut state,
        SemanticOperation::UpdateNpcs(NpcInfoUpdate::default()),
    );
    assert_eq!(state.entities().npcs()[&index].exact_move, Some(forced));
    let before = state.semantic_fingerprint();
    let bad = ExactMove {
        start_delay: 9,
        end_delay: 8,
        ..exact
    };
    assert!(
        state
            .apply_operation(SemanticOperation::UpdateNpcs(NpcInfoUpdate {
                movements: vec![crawl.clone()],
                attributes: vec![NpcAttributeUpdate {
                    index,
                    changes: vec![
                        NpcProperty::Name(Some("bad prefix".into())),
                        NpcProperty::ExactMove(bad)
                    ]
                }],
                ..Default::default()
            }))
            .is_err()
    );
    assert_eq!(state.semantic_fingerprint(), before);
    apply(
        &mut state,
        SemanticOperation::UpdateNpcs(NpcInfoUpdate {
            movements: vec![crawl],
            ..Default::default()
        }),
    );
    assert!(state.entities().npcs()[&index].exact_move.is_none());
    apply(
        &mut state,
        SemanticOperation::UpdateNpcs(NpcInfoUpdate {
            removals: vec![index],
            new_npcs: vec![spawn(index)],
            ..Default::default()
        }),
    );
    let npc = &state.entities().npcs()[&index];
    assert_ne!(npc.instance, first_instance);
    assert!(npc.path.is_none() && npc.exact_move.is_none());
    let mut replay = initial();
    replay.replay_envelopes(&recording).unwrap();
    assert_eq!(replay.semantic_fingerprint(), state.semantic_fingerprint());
    state
        .apply_operation(SemanticOperation::EndSession)
        .unwrap();
    assert!(state.entities().npcs().is_empty());
}
