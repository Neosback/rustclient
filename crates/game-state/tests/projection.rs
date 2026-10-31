use rustclient_game_state::{GameState, ProjectionChange, SemanticOperation};
use rustclient_model::{
    InventoryId, InventorySlot, InventorySlotUpdate, InventoryUpdate, REVISION_240,
};

#[test]
fn dynamic_widget_runtime_invalidates_the_actual_child_address() {
    use rustclient_model::{
        InterfaceId, InterfaceUpdate, WidgetAddress, WidgetBody, WidgetId, WidgetProperty,
    };
    let mut state = GameState::new();
    state
        .apply_operation(SemanticOperation::BeginSession {
            revision: REVISION_240,
        })
        .unwrap();
    let widget = WidgetId::new(InterfaceId::new(12), 13);
    state
        .apply_operation(SemanticOperation::Interface(InterfaceUpdate::CreateChild {
            parent: widget,
            parent_child: None,
            reject_replace: false,
            index: 0,
            body: WidgetBody::Container {
                scroll_size: [100, 100],
                no_click_through: false,
            },
        }))
        .unwrap();
    let address = WidgetAddress {
        widget,
        child: Some(0),
    };
    let mut runtime = state.interfaces().runtime()[&address].clone();
    runtime.text = Some("updated".into());
    let cursor = state.projection_cursor();
    state
        .apply_operation(SemanticOperation::Interface(InterfaceUpdate::SetProperty {
            widget,
            property: WidgetProperty::Runtime {
                address,
                value: Box::new(runtime),
            },
        }))
        .unwrap();
    let changes: Vec<_> = state.projection_changes(cursor).iter().collect();
    assert_eq!(changes, vec![ProjectionChange::Widget(address)]);
}

#[test]
fn projection_readers_are_independent_and_expired_history_is_explicit() {
    let mut state = GameState::new();
    let initial = state.projection_cursor();
    state
        .apply_operation(SemanticOperation::BeginSession {
            revision: REVISION_240,
        })
        .unwrap();
    let first = state.projection_changes(initial);
    assert!(!first.missed());
    assert!(
        matches!(first.iter().next(), Some(ProjectionChange::Domains(d)) if d.lifecycle_changed())
    );
    let current = first.cursor();
    let fingerprint = state.semantic_fingerprint();
    assert_eq!(state.projection_changes(initial).iter().count(), 1);
    assert_eq!(state.projection_changes(current).iter().count(), 0);
    assert_eq!(state.semantic_fingerprint(), fingerprint);
    assert!(
        state
            .apply_operation(SemanticOperation::BeginSession {
                revision: REVISION_240
            })
            .is_err()
    );
    assert_eq!(state.projection_cursor(), current);
    // A consumer which did not run cannot silently miss history overwritten by later commits.
    for i in 0..2100 {
        state
            .apply_operation(SemanticOperation::SelectGameFrameTab(Some(i % 14)))
            .unwrap();
    }
    assert!(state.projection_changes(current).missed());
    let expired = state.projection_changes(current).missed_domains();
    assert!(expired.gameframe_changed());
    assert!(!expired.scenery_changed());
    assert!(!expired.inventory_changed());
    assert_eq!(state.projection_changes(current).iter().count(), 0);
    assert!(!state.projection_changes(state.projection_cursor()).missed());
}

#[test]
fn sparse_inventory_changes_report_final_slots_and_preserve_presence_and_atomicity() {
    let mut state = GameState::new();
    state
        .apply_operation(SemanticOperation::BeginSession {
            revision: REVISION_240,
        })
        .unwrap();
    let id = InventoryId::new(93);
    let patch = |slots| SemanticOperation::Inventory(InventoryUpdate::Patch { id, slots });
    let empty = |slot| InventorySlotUpdate {
        slot: InventorySlot::new(slot),
        stack: None,
    };
    let start = state.projection_cursor();
    state.apply_operation(patch(vec![])).unwrap();
    assert!(
        state
            .projection_changes(start)
            .iter()
            .any(|c| c == ProjectionChange::Inventory(id))
    );
    assert_eq!(state.inventories().get(id), Some([].as_slice()));
    let start = state.projection_cursor();
    state.apply_operation(patch(vec![empty(16_383)])).unwrap();
    assert_eq!(state.inventories().get(id).unwrap().len(), 16_384);
    assert!(
        state
            .projection_changes(start)
            .iter()
            .any(|c| c == ProjectionChange::Inventory(id))
    );
    let start = state.projection_cursor();
    let fingerprint = state.semantic_fingerprint();
    let mut populated = empty(3);
    populated.stack = Some(rustclient_model::ItemStack {
        item: rustclient_model::ItemDefinitionId::new(995),
        amount: 5,
    });
    state
        .apply_operation(patch(vec![populated, empty(3)]))
        .unwrap();
    assert_eq!(state.projection_cursor(), start);
    assert_eq!(state.semantic_fingerprint(), fingerprint);
    assert!(
        state
            .apply_operation(patch(vec![populated, empty(16_384)]))
            .is_err()
    );
    assert_eq!(state.projection_cursor(), start);
    state
        .apply_operation(patch(vec![populated, populated]))
        .unwrap();
    assert_eq!(
        state
            .projection_changes(start)
            .iter()
            .filter(|c| matches!(c, ProjectionChange::InventorySlot { .. }))
            .count(),
        1
    );
}
