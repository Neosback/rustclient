# CP3E — Committed Events and StateDelta

<!-- authority-notice -->
> Historical checkpoint and evidence record. Implementation status, “next” tasks, counts
> and validation below apply to the recorded snapshot. Current work follows
> [PORTING-ROADMAP.md](PORTING-ROADMAP.md) and the [domain matrix](../reference/rs240-port/PORT-MATRIX.md).

## Goal

Expose the smallest deterministic commit metadata needed by future projections, plugins, and replay without introducing a persistent event bus or per-batch allocation.

## Scope

CP3E adds:

- `SemanticEvent` for committed lifecycle/tick facts,
- `StateDelta` for currently modeled changed-state domains,
- `OperationCommit` as the post-commit result of one successful semantic operation,
- `GameState::apply_operation` returning `OperationCommit`,
- allocation-free batch commit streaming through `apply_operation_batch_with_commits`,
- exact previous/current tick values from the validated tick transition primitive,
- tests for committed facts, deltas, semantic no-ops, deterministic commit metadata, and successful-prefix batch delivery.

## Invariants

1. Event metadata is created only after authoritative mutation succeeds.
2. Failed operations produce no event or delta result.
3. Events describe committed facts, not input requests.
4. `StateDelta` is disposable projection metadata and never a second source of truth.
5. `AdvanceTick(TickSpan::ZERO)` remains valid but emits no event and an empty delta.
6. Batch commit callbacks run after each successful operation, in exact slice order.
7. No batch callback occurs for a failed operation or skipped suffix.
8. The existing non-transactional successful-prefix semantics remain unchanged.
9. Batch commit delivery requires no `Vec` allocation inside `game-state`.

## Explicitly deferred

- persistent event queues,
- event fan-out/subscription infrastructure,
- replay envelopes or serialization,
- input provenance/source metadata,
- renderer/UI incremental projection integration,
- plugin event delivery,
- player/NPC/inventory/widget operations or events,
- protocol/networking integration.

## Validation

The checkpoint is complete only after the exact final branch head passes:

- `cargo fmt --all --check`
- `cargo check --workspace --all-targets`
- `cargo clippy --workspace --all-targets -- -D warnings`
- `cargo test --workspace`
- `cargo check -p rustclient-web --target wasm32-unknown-unknown`
