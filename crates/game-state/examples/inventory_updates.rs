//! Explicit optimized diagnostic: sparse updates to a large, unchanged-size container.
//! Run the same source against the frozen baseline and current crate; no FPS/RSS claim.
use rustclient_game_state::{GameState, SemanticOperation};
use rustclient_model::{
    InventoryId, InventorySlot, InventorySlotUpdate, InventoryUpdate, ItemDefinitionId, ItemStack,
    REVISION_240,
};
use std::{hint::black_box, time::Instant};

fn main() -> Result<(), Box<dyn std::error::Error>> {
    let capacity = std::env::args()
        .nth(1)
        .map_or(Ok(16_384), |s| s.parse::<usize>())?;
    if ![28, 16_384].contains(&capacity) {
        return Err("capacity must be 28 or 16384".into());
    }
    let updates = if capacity == 28 {
        200_000_u32
    } else {
        20_000_u32
    };
    let id = InventoryId::new(93);
    let item = ItemDefinitionId::new(995);
    for sample in 0..11 {
        let mut state = GameState::new();
        state.apply_operation(SemanticOperation::BeginSession {
            revision: REVISION_240,
        })?;
        state.apply_operation(SemanticOperation::Inventory(InventoryUpdate::Replace {
            id,
            slots: vec![Some(ItemStack { item, amount: 1 }); capacity],
        }))?;
        let started = Instant::now();
        for iteration in 0..updates {
            let operation = SemanticOperation::Inventory(InventoryUpdate::Patch {
                id,
                slots: vec![InventorySlotUpdate {
                    slot: InventorySlot::new(iteration % 28),
                    stack: Some(ItemStack {
                        item,
                        amount: iteration + 2,
                    }),
                }],
            });
            black_box(state.apply_operation(black_box(operation))?);
        }
        let elapsed = started.elapsed().as_secs_f64() * 1000.;
        let slots = state.inventories().get(id).ok_or("missing inventory")?;
        if slots.len() != capacity
            || slots[usize::try_from((updates - 1) % 28)?]
                != Some(ItemStack {
                    item,
                    amount: updates + 1,
                })
        {
            return Err("inventory diagnostic final state mismatch".into());
        }
        println!(
            "{{\"sample\":{sample},\"warmup\":{},\"updates\":{updates},\"slots\":{capacity},\"elapsed_ms\":{elapsed:.6},\"state\":\"{}\"}}",
            sample < 2,
            state.semantic_fingerprint()
        );
    }
    Ok(())
}
