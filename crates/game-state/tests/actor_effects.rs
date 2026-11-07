use rustclient_game_state::{
    AuthenticatedPlayer, GameState, InitialPlacement, ReplayEnvelope, SceneryOperation,
    SemanticOperation,
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
fn npc_info(spawn: bool, effects: Option<ActorEffects>) -> SemanticOperation {
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
        attributes: effects
            .map(|effects| {
                vec![NpcAttributeUpdate {
                    index: NpcIndex::new(42),
                    changes: vec![NpcProperty::Effects(effects)],
                }]
            })
            .unwrap_or_default(),
        ..NpcInfoUpdate::default()
    })
}
fn hit(amount: u16) -> ActorEffects {
    ActorEffects {
        hitmarks: vec![Hitmark {
            kind: 12,
            amount,
            delay: 0,
            limit: 0,
        }],
        ..ActorEffects::default()
    }
}

#[test]
fn npc_and_player_effects_expire_with_the_cycle_clock_reset_on_index_reuse_and_replay() {
    let mut state = session();
    let mut recording = Vec::new();
    let mut apply = |state: &mut GameState, op: SemanticOperation| {
        let commit = state.apply_operation(op.clone()).unwrap();
        recording.push(ReplayEnvelope::new(op, commit));
        commit
    };
    apply(&mut state, npc_info(true, Some(hit(7))));
    let npc = &state.entities().npcs()[&NpcIndex::new(42)];
    assert_eq!(npc.effects.hitmarks()[0].amount, 7);

    // The local player's block rides the player info update.
    let local = SemanticOperation::UpdatePlayers(PlayerInfoUpdate {
        effects: vec![PlayerEffects {
            index: PlayerIndex::new(1),
            effects: hit(3),
        }],
        ..PlayerInfoUpdate::default()
    });
    let commit = apply(&mut state, local);
    assert!(commit.delta().entities_changed());
    assert_eq!(
        state.entities().local_player().unwrap().effects.hitmarks()[0].amount,
        3
    );
    // A block for a player that is not in the scene is dropped.
    let absent = SemanticOperation::UpdatePlayers(PlayerInfoUpdate {
        effects: vec![PlayerEffects {
            index: PlayerIndex::new(900),
            effects: hit(1),
        }],
        ..PlayerInfoUpdate::default()
    });
    assert!(apply(&mut state, absent).delta().is_empty());

    // 69 cycles later the splat is still shown; at 70 it is gone.
    apply(
        &mut state,
        SemanticOperation::Scenery(SceneryOperation::AdvanceCycles(69)),
    );
    assert_eq!(
        state.entities().npcs()[&NpcIndex::new(42)]
            .effects
            .hitmarks()
            .len(),
        1
    );
    apply(
        &mut state,
        SemanticOperation::Scenery(SceneryOperation::AdvanceCycles(1)),
    );
    assert!(
        state.entities().npcs()[&NpcIndex::new(42)]
            .effects
            .is_empty()
    );
    assert!(state.entities().local_player().unwrap().effects.is_empty());

    // A removed and re-added NPC index starts with no effects.
    apply(&mut state, npc_info(false, Some(hit(9))));
    assert!(
        !state.entities().npcs()[&NpcIndex::new(42)]
            .effects
            .is_empty()
    );
    apply(
        &mut state,
        SemanticOperation::UpdateNpcs(NpcInfoUpdate {
            origin: Some(TileOffset { x: 0, y: 0 }),
            removals: vec![NpcIndex::new(42)],
            ..NpcInfoUpdate::default()
        }),
    );
    apply(&mut state, npc_info(true, None));
    assert!(
        state.entities().npcs()[&NpcIndex::new(42)]
            .effects
            .is_empty()
    );

    let mut replay = session();
    replay.replay_envelopes(&recording).unwrap();
    assert_eq!(replay.semantic_fingerprint(), state.semantic_fingerprint());
}
