# CP3C: Typed Semantic Operation Boundary

<!-- authority-notice -->
> Historical checkpoint and evidence record. Implementation status, “next” tasks, counts
> and validation below apply to the recorded snapshot. Current work follows
> [PORTING-ROADMAP.md](PORTING-ROADMAP.md) and the [domain matrix](../reference/rs240-port/PORT-MATRIX.md).

## Goal

Introduce the smallest source-neutral semantic operation application boundary for the authoritative `GameState` without starting event, delta, batching, replay, protocol, or domain-state work.

## Scope

- add `SemanticOperation`
- cover the currently implemented mutation domains only: session begin, session end and deterministic tick advancement
- add `GameState::apply_operation` as the public semantic mutation gateway
- make the existing lifecycle/tick mutation methods crate-internal validated primitives
- preserve typed lifecycle/tick errors through `OperationError`
- ensure the dispatcher delegates rather than duplicating mutation rules
- prove invalid operations leave observable authoritative state unchanged
- prove identical operation sequences produce identical observable state
- update game-state invariants and SPEC-0002

## Explicitly out of scope

- operation batches
- batch ordering rules
- partial-batch failure semantics
- committed events
- `StateDelta`
- replay capture/envelopes
- operation provenance/source metadata
- protocol/networking
- player/NPC/inventory/widget/scene operations
- renderer/UI/plugin integration

## Invariants

1. `SemanticOperation` is source-neutral and contains no packet/UI/renderer/platform details.
2. External semantic mutation enters through one public `GameState::apply_operation` boundary.
3. Dispatch reuses validated transition primitives; it does not duplicate their rules.
4. One operation is validated/applied atomically.
5. A failed operation leaves observable authoritative state unchanged.
6. Error values retain the subsystem failure category.
7. Identical initial state plus identical valid operations produces identical observable state.
8. Operation application does not imply that an event has been committed or emitted.

## Validation

The exact final branch head must pass:

- `cargo fmt --all --check`
- `cargo check --workspace --all-targets`
- `cargo clippy --workspace --all-targets -- -D warnings`
- `cargo test --workspace`
- `cargo check -p rustclient-web --target wasm32-unknown-unknown`

## Next checkpoint

After CP3C is merged and explicitly approved to continue, the next checkpoint should define deterministic operation-batch ordering and failure semantics before introducing committed event/`StateDelta` output.
