use rustclient_game_state::{
    AuthenticatedPlayer, GameState, GroundItemOperation, InitialPlacement, OperationError,
    ProjectionChange, ReplayEnvelope, SemanticEvent, SemanticOperation,
};
use rustclient_model::{
    ActionMask, GroundItemCustomisation, GroundItemOwnership, GroundItemPosition, GroundItemUpdate,
    ItemDefinitionId, ModelId, Plane, PlayerIndex, Revision, ServerProfileId, TickSpan, TileOffset,
    WorldTile,
};

const ANCHOR: WorldTile = WorldTile::new(3216, 3216, Plane::MIN);

fn session() -> GameState {
    let mut state = GameState::new();
    for op in [
        SemanticOperation::AuthenticateSession {
            revision: Revision::with_subrevision(240, 2),
            player: AuthenticatedPlayer {
                profile: ServerProfileId::new(1),
                player: PlayerIndex::new(1),
                member: false,
                staff_level: 0,
            },
        },
        SemanticOperation::EstablishInitialPlacement(InitialPlacement {
            position: WorldTile::new(3221, 3218, Plane::MIN),
            map_anchor: ANCHOR,
            area: None,
        }),
    ] {
        state.apply_operation(op).unwrap();
    }
    state
}
fn at(x: i32, y: i32) -> GroundItemPosition {
    GroundItemPosition::Zone {
        offset: TileOffset { x, y },
        plane: Plane::MIN,
    }
}
fn add(position: GroundItemPosition, item: u32, quantity: u32) -> SemanticOperation {
    SemanticOperation::GroundItems(GroundItemOperation::Update(GroundItemUpdate::Add {
        position,
        item: ItemDefinitionId::new(item),
        quantity,
        ops: ActionMask::ALL,
        ownership: GroundItemOwnership::SELF,
        ticks_until_public: 100,
        ticks_until_despawn: 200,
        never_public: false,
    }))
}
fn update(update: GroundItemUpdate) -> SemanticOperation {
    SemanticOperation::GroundItems(GroundItemOperation::Update(update))
}
fn ids(state: &GameState, tile: WorldTile) -> Vec<(u32, u32)> {
    state
        .ground_items()
        .pile(tile)
        .iter()
        .map(|item| (item.item.get(), item.quantity))
        .collect()
}

#[test]
fn piles_keep_arrival_order_and_match_the_first_item_like_the_reference() {
    let mut state = session();
    state
        .apply_operation(SemanticOperation::AdvanceTick(TickSpan::new(7)))
        .unwrap();
    let tile = WorldTile::new(3218, 3219, Plane::MIN);
    for op in [
        add(at(2, 3), 995, 10),
        add(at(2, 3), 526, 1),
        add(at(2, 3), 995, 10),
    ] {
        let commit = state.apply_operation(op).unwrap();
        assert_eq!(
            commit.event(),
            Some(SemanticEvent::GroundItemsChanged { tile })
        );
        assert!(commit.delta().ground_items_changed());
    }
    assert_eq!(ids(&state, tile), [(995, 10), (526, 1), (995, 10)]);
    let first = state.ground_items().pile(tile)[0];
    assert_eq!(first.public_at.get(), 107);
    assert_eq!(first.despawn_at.get(), 207);

    // Count and delete act on the first item with the same id and quantity.
    state
        .apply_operation(update(GroundItemUpdate::Count {
            position: at(2, 3),
            item: ItemDefinitionId::new(995),
            old: 10,
            new: 25,
        }))
        .unwrap();
    assert_eq!(ids(&state, tile), [(995, 25), (526, 1), (995, 10)]);
    state
        .apply_operation(update(GroundItemUpdate::Delete {
            position: GroundItemPosition::Absolute(tile),
            item: ItemDefinitionId::new(995),
            quantity: 10,
        }))
        .unwrap();
    assert_eq!(ids(&state, tile), [(995, 25), (526, 1)]);

    // Ops act on the first item with the id; customisation needs the quantity too.
    let ops = ActionMask::new([false, false, true, false, false]);
    state
        .apply_operation(update(GroundItemUpdate::EnabledOps {
            position: at(2, 3),
            item: ItemDefinitionId::new(526),
            ops,
        }))
        .unwrap();
    assert_eq!(state.ground_items().pile(tile)[1].ops, ops);
    let custom = GroundItemCustomisation {
        model: Some(ModelId::new(7)),
        recolour: None,
        retexture: Some((0, 3)),
    };
    let mismatch = state
        .apply_operation(update(GroundItemUpdate::Customise {
            position: at(2, 3),
            item: ItemDefinitionId::new(526),
            quantity: 2,
            customisation: custom,
        }))
        .unwrap();
    assert!(mismatch.event().is_none() && mismatch.delta().is_empty());
    state
        .apply_operation(update(GroundItemUpdate::Customise {
            position: at(2, 3),
            item: ItemDefinitionId::new(526),
            quantity: 1,
            customisation: custom,
        }))
        .unwrap();
    assert_eq!(
        state.ground_items().pile(tile)[1].customisation,
        Some(custom)
    );
    state
        .apply_operation(update(GroundItemUpdate::Uncustomise {
            position: at(2, 3),
            item: ItemDefinitionId::new(526),
            quantity: 1,
        }))
        .unwrap();
    assert_eq!(state.ground_items().pile(tile)[1].customisation, None);

    // Deleting the last item drops the tile entirely.
    for (item, quantity) in [(995, 25), (526, 1)] {
        state
            .apply_operation(update(GroundItemUpdate::Delete {
                position: at(2, 3),
                item: ItemDefinitionId::new(item),
                quantity,
            }))
            .unwrap();
    }
    assert!(state.ground_items().is_empty());
    assert_eq!(state.ground_items().piles().len(), 0);
}

#[test]
fn updates_outside_the_window_or_for_missing_items_change_nothing() {
    let mut state = session();
    let before = state.semantic_fingerprint();
    let far = GroundItemPosition::Absolute(WorldTile::new(3216 + 60, 3216, Plane::MIN));
    let commit = state.apply_operation(add(far, 995, 1)).unwrap();
    assert!(commit.event().is_none());
    let missing = state
        .apply_operation(update(GroundItemUpdate::Delete {
            position: at(0, 0),
            item: ItemDefinitionId::new(1),
            quantity: 1,
        }))
        .unwrap();
    assert!(missing.delta().is_empty());
    assert_eq!(state.semantic_fingerprint(), before);
    assert!(matches!(
        GameState::new().apply_operation(add(at(0, 0), 1, 1)),
        Err(OperationError::GroundItems(_))
    ));
}

#[test]
fn zone_reset_rebuild_and_session_end_clear_items_and_replay_reproduces_state() {
    let mut state = session();
    let mut recording = Vec::new();
    let mut record = |state: &mut GameState, op: SemanticOperation| {
        let commit = state.apply_operation(op.clone()).unwrap();
        recording.push(ReplayEnvelope::new(op, commit));
        commit
    };
    for op in [
        add(at(0, 0), 1, 1),
        add(at(7, 7), 2, 1),
        add(at(8, 0), 3, 1),
    ] {
        record(&mut state, op);
    }
    let cursor = state.projection_cursor();
    let reset = record(
        &mut state,
        SemanticOperation::GroundItems(GroundItemOperation::ResetArea {
            offset: TileOffset { x: 0, y: 0 },
            plane: Plane::MIN,
            size: [8, 8],
        }),
    );
    assert_eq!(
        reset.event(),
        Some(SemanticEvent::GroundItemAreaReset {
            origin: ANCHOR,
            size: [8, 8]
        })
    );
    assert_eq!(state.ground_items().len(), 1);
    assert!(state.projection_changes(cursor).missed_domains().is_empty());
    let precise = record(&mut state, add(at(1, 1), 4, 1));
    assert!(precise.delta().ground_items_changed());
    assert!(state.projection_changes(cursor).iter().any(
        |change| change == ProjectionChange::GroundItem(WorldTile::new(3217, 3217, Plane::MIN))
    ));

    let mut replay = session();
    replay.replay_envelopes(&recording).unwrap();
    assert_eq!(replay.semantic_fingerprint(), state.semantic_fingerprint());

    // A rebuild to another window empties every pile, like the stock deque reset.
    state
        .apply_operation(SemanticOperation::RebuildMap(rustclient_model::MapAnchor {
            x: ANCHOR.x + 64,
            y: ANCHOR.y,
        }))
        .unwrap();
    assert!(state.ground_items().is_empty());
    replay.apply_operation(add(at(0, 0), 1, 1)).unwrap();
    replay
        .apply_operation(SemanticOperation::EndSession)
        .unwrap();
    assert!(replay.ground_items().is_empty());
}
