# Game State Scope

This crate owns authoritative live semantic state and validated transitions.

- No socket, filesystem, browser, window, GPU, `egui`, Wasm runtime, or packet opcode dependencies.
- No revision-specific byte encodings.
- Time/randomness enter explicitly.
- External semantic mutation enters through typed `SemanticOperation` values and `GameState::apply_operation` / `apply_operation_batch`.
- Operation types remain source-neutral: no packet opcode, transport, renderer, UI-toolkit, or platform details.
- The operation dispatcher delegates to validated subsystem transition primitives; do not duplicate mutation rules in dispatch code.
- An active semantic session owns an explicit `Revision`; never infer an implicit process-wide/default revision inside `game-state`.
- `BeginSession { revision }` establishes session revision identity and `EndSession` clears it.
- `Revision` is compatibility identity, not `ServerProfile` identity. Do not invent a profile surrogate in this crate; use the canonical profile abstraction once its owning subsystem defines it.
- Replay reproduces session revision through the same `BeginSession` operation and verifies it again through `SessionBegan { revision }` commit metadata.
- Successful operations return `OperationCommit`; failed operations return no event/delta metadata.
- `SemanticEvent` values describe committed facts only. Do not use events as commands or acknowledgements of requests that caused no observable state change.
- `StateDelta` is projection metadata only; it must never become an independent source of truth.
- A successful no-op transition may return no event and an empty delta.
- Operation batches execute exactly in slice order.
- Batches are not transactional: successful prefix operations remain committed when a later operation fails.
- Batch processing stops at the first failure; the failing operation leaves state unchanged and the remaining suffix is not attempted.
- Batch errors must identify the first failing zero-based operation index and preserve the typed `OperationError`.
- `apply_operation_batch_with_commits` streams successful commit metadata in exact input order without requiring a per-batch allocation.
- Replay envelopes pair the semantic operation with the exact `OperationCommit` originally observed.
- Replay must use `GameState::apply_operation`; never create a replay-only mutation implementation.
- Replay commit mismatches are detected after the operation commits; the replay state is disposable after mismatch and must not be resumed.
- Verified replay callbacks run only after actual commit metadata exactly matches the recording.
- Semantic game ticks are session-owned state and advance only through explicit checked transitions.
- Failed transitions and failed operations must leave authoritative state unchanged.
- Events describe committed facts; commands/operations request changes.
- Avoid mutable references escaping transition boundaries.
- `GameState` must not implement `Clone`; do not create a second mutable semantic authority by copying the full state tree.
- Domain fields stay private. Expose immutable queries and explicit transitions rather than `&mut` field accessors.
- Every new state field needs a clear owner, mutation path, reset/session lifecycle, event/delta implication, and replay implication.
- Prefer deterministic containers/order where ordering is observable.
