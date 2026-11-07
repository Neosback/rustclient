//! Ground item projection events derived from consecutive snapshots.
use rustclient_plugin_api::{GameEvent, GameSnapshot, GroundItemSnapshot, WorldPoint};
use rustclient_plugin_bridge::{Timing, VarbitIndex, derive};

fn item(x: i32, id: u32, quantity: u32) -> GroundItemSnapshot {
    GroundItemSnapshot {
        tile: WorldPoint::new(x, 3200, 0),
        id,
        quantity,
        name: Some("Coins".into()),
        cost: 1,
        stackable: true,
        noted: false,
        ownership: 1,
        public_tick: 100,
        despawn_tick: 200,
    }
}
fn snapshot(items: Vec<GroundItemSnapshot>) -> GameSnapshot {
    GameSnapshot {
        ground_items: items,
        ..GameSnapshot::default()
    }
}
fn events(prev: Vec<GroundItemSnapshot>, next: Vec<GroundItemSnapshot>) -> Vec<String> {
    derive(
        &snapshot(prev),
        &snapshot(next),
        &VarbitIndex::default(),
        Timing::default(),
    )
    .into_iter()
    .map(|event| match event {
        GameEvent::ItemSpawned(item) => {
            format!("spawn {}x{} @{}", item.id, item.quantity, item.tile.x)
        }
        GameEvent::ItemDespawned(item) => {
            format!("despawn {}x{} @{}", item.id, item.quantity, item.tile.x)
        }
        GameEvent::ItemQuantityChanged { item, old } => {
            format!(
                "count {} {old}->{} @{}",
                item.id, item.quantity, item.tile.x
            )
        }
        other => format!("{other:?}"),
    })
    .collect()
}

#[test]
fn in_place_counts_are_quantity_changes_and_other_differences_spawn_or_despawn() {
    assert!(events(vec![item(1, 995, 5)], vec![item(1, 995, 5)]).is_empty());
    assert_eq!(
        events(
            vec![item(1, 995, 5), item(1, 526, 1)],
            vec![item(1, 995, 9), item(1, 526, 1)]
        ),
        ["count 995 5->9 @1"]
    );
    assert_eq!(
        events(
            vec![item(1, 995, 5), item(2, 526, 1)],
            vec![item(1, 995, 5), item(1, 314, 3), item(3, 1, 1)]
        ),
        ["spawn 314x3 @1", "despawn 526x1 @2", "spawn 1x1 @3"]
    );
    // A duplicate stack leaving is one despawn of the matching stack.
    assert_eq!(
        events(
            vec![item(4, 995, 1), item(4, 995, 1)],
            vec![item(4, 995, 1)]
        ),
        ["despawn 995x1 @4"]
    );
}
