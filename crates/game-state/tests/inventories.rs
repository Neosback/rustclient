use rustclient_game_state::{
    GameState, InventoryError, OperationError, ReplayEnvelope, SemanticEvent, SemanticOperation,
};
use rustclient_model::{
    InventoryId, InventorySlot, InventorySlotUpdate, InventoryUpdate, ItemDefinitionId, ItemStack,
    REVISION_240,
};
fn coins(amount: u32) -> ItemStack {
    ItemStack {
        item: ItemDefinitionId::new(995),
        amount,
    }
}
fn record(state: &mut GameState, recording: &mut Vec<ReplayEnvelope>, update: InventoryUpdate) {
    let operation = SemanticOperation::Inventory(update);
    let commit = state.apply_operation(operation.clone()).unwrap();
    recording.push(ReplayEnvelope::new(operation, commit));
}
#[test]
#[expect(
    clippy::too_many_lines,
    reason = "single inventory lifecycle, atomicity and replay contract"
)]
fn inventories_validate_atomically_replay_and_clear_with_session() {
    let mut state = GameState::new();
    let before = state.semantic_fingerprint();
    let id = InventoryId::new(93);
    assert!(
        state
            .apply_operation(SemanticOperation::Inventory(InventoryUpdate::Remove(id)))
            .is_err()
    );
    let begin = SemanticOperation::BeginSession {
        revision: REVISION_240,
    };
    let commit = state.apply_operation(begin.clone()).unwrap();
    let mut recording = vec![ReplayEnvelope::new(begin, commit)];
    record(
        &mut state,
        &mut recording,
        InventoryUpdate::Replace {
            id,
            slots: vec![Some(coins(300)), None, Some(coins(0))],
        },
    );
    assert_eq!(
        recording.last().unwrap().commit().event(),
        Some(SemanticEvent::InventoryChanged(id))
    );
    assert!(
        recording
            .last()
            .unwrap()
            .commit()
            .delta()
            .inventory_changed()
    );
    let original = state.semantic_fingerprint();
    let same = state
        .apply_operation(SemanticOperation::Inventory(InventoryUpdate::Patch {
            id,
            slots: vec![
                InventorySlotUpdate {
                    slot: InventorySlot::new(0),
                    stack: Some(coins(1)),
                },
                InventorySlotUpdate {
                    slot: InventorySlot::new(0),
                    stack: Some(coins(300)),
                },
            ],
        }))
        .unwrap();
    assert!(same.delta().is_empty());
    assert_eq!(same.event(), None);
    assert_eq!(state.semantic_fingerprint(), original);
    let invalid = state.apply_operation(SemanticOperation::Inventory(InventoryUpdate::Patch {
        id,
        slots: vec![
            InventorySlotUpdate {
                slot: InventorySlot::new(0),
                stack: None,
            },
            InventorySlotUpdate {
                slot: InventorySlot::new(16_384),
                stack: Some(coins(1)),
            },
        ],
    }));
    assert_eq!(
        invalid,
        Err(OperationError::Inventory(InventoryError::LimitExceeded {
            id
        }))
    );
    assert_eq!(state.semantic_fingerprint(), original);
    record(
        &mut state,
        &mut recording,
        InventoryUpdate::Patch {
            id,
            slots: vec![InventorySlotUpdate {
                slot: InventorySlot::new(5),
                stack: Some(coins(1_000)),
            }],
        },
    );
    assert_eq!(
        state.inventories().get(id).unwrap(),
        &[
            Some(coins(300)),
            None,
            Some(coins(0)),
            None,
            None,
            Some(coins(1_000))
        ]
    );
    record(
        &mut state,
        &mut recording,
        InventoryUpdate::Replace {
            id,
            slots: vec![Some(coins(42))],
        },
    );
    assert_eq!(state.inventories().get(id).unwrap(), &[Some(coins(42))]);
    let mut replay = GameState::new();
    replay
        .replay_envelopes_with_commits(&recording, |_, _| {})
        .unwrap();
    assert_eq!(replay.semantic_fingerprint(), state.semantic_fingerprint());
    record(&mut state, &mut recording, InventoryUpdate::Remove(id));
    assert_eq!(state.inventories().get(id), None);
    record(
        &mut state,
        &mut recording,
        InventoryUpdate::Patch { id, slots: vec![] },
    );
    assert_eq!(state.inventories().get(id), Some(&[][..]));
    // Container-count bounds and a failed allocation request leave the prefix intact.
    for value in 0..256 {
        if value != 93 {
            state
                .apply_operation(SemanticOperation::Inventory(InventoryUpdate::Replace {
                    id: InventoryId::new(value),
                    slots: vec![],
                }))
                .unwrap();
        }
    }
    let full = state.semantic_fingerprint();
    assert!(
        state
            .apply_operation(SemanticOperation::Inventory(InventoryUpdate::Replace {
                id: InventoryId::new(256),
                slots: vec![]
            }))
            .is_err()
    );
    assert_eq!(state.semantic_fingerprint(), full);
    state
        .apply_operation(SemanticOperation::EndSession)
        .unwrap();
    assert_eq!(state.inventories().iter().len(), 0);
    assert_eq!(state.semantic_fingerprint(), before);
}
