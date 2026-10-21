# CP3D: Deterministic Operation Batches

## Goal

Define the smallest deterministic batch application contract on top of the CP3C semantic operation gateway.

## Scope

- add `GameState::apply_operation_batch(&[SemanticOperation])`
- apply operations exactly in slice order
- stop processing at the first failed operation
- preserve already committed successful prefix operations
- do not attempt the remaining suffix after failure
- add `OperationBatchError` with the first failing zero-based index and typed `OperationError`
- treat an empty batch as a valid no-op
- add focused batch-order/failure tests
- update game-state invariants and SPEC-0002

## Explicitly out of scope

- transactional batch rollback
- full-state cloning/checkpointing
- cross-source queue merge policy
- committed event types
- `StateDelta`
- replay capture/envelopes
- protocol/networking
- player/NPC/inventory/widget/scene operations
- renderer/UI/plugin integration

## Invariants

1. Batch input order is the commit-attempt order.
2. A successful operation commits before the next operation begins.
3. The first failed operation leaves state unchanged.
4. Successful prefix operations remain committed after a later failure.
5. Operations after the first failure are never attempted.
6. Batch failure identifies the exact zero-based failing operation index and preserves its typed operation error.
7. No full-state clone or rollback authority is introduced.
8. An empty batch is a deterministic no-op.

## Validation

The exact final branch head must pass:

- `cargo fmt --all --check`
- `cargo check --workspace --all-targets`
- `cargo clippy --workspace --all-targets -- -D warnings`
- `cargo test --workspace`
- `cargo check -p rustclient-web --target wasm32-unknown-unknown`

## Next checkpoint

CP3E should introduce the smallest committed event / `StateDelta` result for successful semantic mutations. It must not start until CP3D is merged and explicitly approved to continue.
