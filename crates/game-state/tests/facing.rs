use rustclient_game_state::{
    AuthenticatedPlayer, GameState, InitialPlacement, ReplayEnvelope, SemanticOperation,
};
use rustclient_model::*;

fn session() -> GameState {
    let mut state = GameState::new();
    for op in [
        SemanticOperation::AuthenticateSession {
            revision: REVISION_240,
            player: AuthenticatedPlayer {
                profile: ServerProfileId::new(1),
                player: PlayerIndex::new(1),
                member: false,
                staff_level: 0,
            },
        },
        SemanticOperation::EstablishInitialPlacement(InitialPlacement {
            position: WorldTile::new(3220, 3220, Plane::MIN),
            map_anchor: WorldTile::new(3216, 3216, Plane::MIN),
            area: None,
        }),
    ] {
        state.apply_operation(op).unwrap();
    }
    state
}
fn block(target: FacingTarget) -> FacingBlock {
    FacingBlock {
        target,
        walk: FacingWalkPolicy::CancelOnWalk,
        instant: false,
    }
}
fn at_player(index: u32) -> FacingBlock {
    block(FacingTarget::Entity {
        kind: FacingEntityKind::Player,
        index: Some(index),
        fallback_angle: 0,
    })
}
fn npc_facing(spawn: bool, block: Option<FacingBlock>) -> SemanticOperation {
    SemanticOperation::UpdateNpcs(NpcInfoUpdate {
        origin: Some(TileOffset { x: 0, y: 0 }),
        new_npcs: if spawn {
            vec![NewNpc {
                index: NpcIndex::new(42),
                definition_id: NpcDefinitionId::new(12),
                delta_x: 4,
                delta_y: 4,
                orientation: 0,
            }]
        } else {
            vec![]
        },
        attributes: block
            .map(|block| {
                vec![NpcAttributeUpdate {
                    index: NpcIndex::new(42),
                    changes: vec![NpcProperty::FacingBlock(block)],
                }]
            })
            .unwrap_or_default(),
        ..NpcInfoUpdate::default()
    })
}

#[test]
fn facing_blocks_are_retained_replaced_by_later_blocks_and_reset_on_index_reuse() {
    let mut state = session();
    let mut recording = Vec::new();
    let mut apply = |state: &mut GameState, op: SemanticOperation| {
        let commit = state.apply_operation(op.clone()).unwrap();
        recording.push(ReplayEnvelope::new(op, commit));
        commit
    };
    // The NPC starts attacking the local player.
    let commit = apply(&mut state, npc_facing(true, Some(at_player(1))));
    assert!(commit.delta().entities_changed());
    let npc = &state.entities().npcs()[&NpcIndex::new(42)];
    assert_eq!(
        npc.facing.and_then(|b| b.interaction()),
        Some(Interaction::Player(PlayerIndex::new(1)))
    );
    // The same block again changes nothing.
    assert!(
        apply(&mut state, npc_facing(false, Some(at_player(1))))
            .delta()
            .is_empty()
    );
    // An angle block replaces the entity target: the NPC stops interacting.
    apply(
        &mut state,
        npc_facing(false, Some(block(FacingTarget::Angle(512)))),
    );
    let npc = &state.entities().npcs()[&NpcIndex::new(42)];
    assert_eq!(npc.facing.and_then(|b| b.interaction()), None);
    assert!(npc.facing.is_some());

    // The local player's block rides the player info update; one for an absent player is dropped.
    let local = SemanticOperation::UpdatePlayers(PlayerInfoUpdate {
        facings: vec![PlayerFacing {
            index: PlayerIndex::new(1),
            block: block(FacingTarget::Entity {
                kind: FacingEntityKind::Npc,
                index: Some(42),
                fallback_angle: 0,
            }),
        }],
        ..PlayerInfoUpdate::default()
    });
    apply(&mut state, local);
    assert_eq!(
        state
            .entities()
            .local_player()
            .unwrap()
            .facing
            .and_then(|b| b.interaction()),
        Some(Interaction::Npc(NpcIndex::new(42)))
    );
    let absent = SemanticOperation::UpdatePlayers(PlayerInfoUpdate {
        facings: vec![PlayerFacing {
            index: PlayerIndex::new(900),
            block: at_player(1),
        }],
        ..PlayerInfoUpdate::default()
    });
    assert!(apply(&mut state, absent).delta().is_empty());

    // A removed and re-added index starts with no facing.
    apply(
        &mut state,
        SemanticOperation::UpdateNpcs(NpcInfoUpdate {
            origin: Some(TileOffset { x: 0, y: 0 }),
            removals: vec![NpcIndex::new(42)],
            ..NpcInfoUpdate::default()
        }),
    );
    apply(&mut state, npc_facing(true, None));
    assert!(state.entities().npcs()[&NpcIndex::new(42)].facing.is_none());

    let mut replay = session();
    replay.replay_envelopes(&recording).unwrap();
    assert_eq!(replay.semantic_fingerprint(), state.semantic_fingerprint());
}
