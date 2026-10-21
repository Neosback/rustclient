# Game State Scope

This crate owns authoritative live semantic state and validated transitions.

- No socket, filesystem, browser, window, GPU, `egui`, Wasm runtime, or packet opcode dependencies.
- No revision-specific byte encodings.
- Time/randomness enter explicitly.
- External semantic mutation enters through typed `SemanticOperation` values and `GameState::apply_operation` / `apply_operation_batch`.
- Operation types remain source-neutral: no packet opcode, transport, renderer, UI-toolkit, or platform details.
- The operation dispatcher delegates to validated subsystem transition primitives; do not duplicate mutation rules in dispatch code.
- Operation batches execute exactly in slice order.
- Batches are not transactional: successful prefix operations remain committed when a later operation fails.
- Batch processing stops at the first failure; the failing operation leaves state unchanged and the remaining suffix is not attempted.
- Batch errors must identify the first failing zero-based operation index and preserve the typed `OperationError`.
- Semantic game ticks are session-owned state and advance only through explicit checked transitions.
- Failed transitions and failed operations must leave authoritative state unchanged.
- Events describe committed facts; commands/operations request changes.
- Avoid mutable references escaping transition boundaries.
- `GameState` must not implement `Clone`; do not create a second mutable semantic authority by copying the full state tree.
- Domain fields stay private. Expose immutable queries and explicit transitions rather than `&mut` field accessors.
- Every new state field needs a clear owner, mutation path, reset/session lifecycle, and replay implication.
- Prefer deterministic containers/order where ordering is observable.
