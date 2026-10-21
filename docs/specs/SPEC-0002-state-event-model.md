# SPEC-0002: Authoritative State, Events, Commands, and Replay

**Status:** Draft

## Purpose

Make runtime behavior deterministic enough to debug, replay, test plugins, and support both native/browser front ends.

## State model

`GameState` owns semantic live state such as players/NPCs, scene occupancy, inventories, varps/varbits, skills, widgets, camera intent where semantic, chat/message state, timers, and connection/session metadata that affects behavior.

State is changed through explicit operations. Systems must not retain mutable aliases into state across a transition boundary.

### Root ownership constraints

The authoritative root is deliberately difficult to duplicate or bypass:

- `GameState` does not implement `Clone`.
- Domain fields are private to `game-state`.
- External crates receive immutable queries/snapshots rather than mutable field references.
- External semantic mutation enters through typed `SemanticOperation` values and `GameState::apply_operation` / `apply_operation_batch`.
- Session lifecycle and game-time validation remain crate-owned transition primitives behind that public operation gateway.
- Starting a session initializes session-owned state; ending it clears session-owned state before another session may begin.
- An invalid operation leaves state unchanged.

These constraints remain invariants as richer domains are added.

### Semantic game time

The current semantic game tick is session-owned authoritative state.

- An inactive state has no observable current tick.
- Beginning a session starts at `GameTick::ZERO`.
- Ending a session clears the observable session tick.
- Tick advancement requires an active session.
- Tick advancement uses an explicit `TickSpan`; domain transitions do not read wall time.
- Tick arithmetic is checked. Overflow rejects the transition without mutating state.
- A zero-span tick advance is valid but causes no observable state change.
- A future runtime scheduler decides *when* to request tick advancement; `GameState` only validates and commits the semantic transition.

### Semantic operation application boundary

`SemanticOperation` is the source-neutral request type accepted by the authoritative state mutation gateway.

Current operations are deliberately minimal:

- begin session,
- end session,
- advance semantic game tick by `TickSpan`.

The operation boundary follows these rules:

- operations contain semantic intent only; packet opcodes, byte encodings, transport state, UI toolkit types, renderer types and platform handles are forbidden,
- `GameState::apply_operation` applies exactly one operation at a time,
- dispatch delegates to existing validated transition primitives rather than reimplementing lifecycle/time rules,
- validation happens before committed mutation,
- failure preserves authoritative observable state for the failing operation,
- operation errors preserve the typed subsystem error category,
- successful operations return commit metadata only after authoritative mutation has completed.

### Commit metadata

`OperationCommit` is the result metadata for one successfully applied semantic operation. It contains:

- an optional `SemanticEvent`, and
- a `StateDelta`.

`SemanticEvent` describes a committed fact, never a request. The currently modeled event facts are:

- `SessionBegan`,
- `SessionEnded`,
- `GameTickAdvanced { previous, current }`.

Events are emitted only when observable semantic state actually changed. For example, `AdvanceTick(TickSpan::ZERO)` succeeds but returns no semantic event because the authoritative tick ordinal is unchanged.

`StateDelta` is projection-oriented metadata describing which modeled state domains changed. It is not authoritative state and must be discardable. CP3E tracks only:

- session lifecycle changed,
- observable semantic game tick changed.

A successful operation that changes no observable state returns an empty delta. Consumers query the authoritative `GameState` for current values rather than treating the delta as a second state store.

### Ordered operation batches

`GameState::apply_operation_batch` applies a borrowed slice of `SemanticOperation` values in exact slice order.

Batch behavior is deliberately simple and deterministic:

1. operations are attempted from index `0` upward,
2. each successful operation commits before the next operation begins,
3. processing stops immediately at the first failed operation,
4. the failed operation itself leaves authoritative state unchanged,
5. operations after the failure are not attempted,
6. successful prefix mutations remain committed,
7. an empty batch is a valid no-op,
8. the returned batch error identifies the first failing zero-based index and preserves its typed `OperationError`.

Operation batches are therefore **not transactional** and are not rolled back on later failure. This avoids full-state cloning or rollback authority.

`GameState::apply_operation_batch_with_commits` preserves the same mutation/failure semantics but additionally invokes a caller-provided callback immediately after each successful commit. The callback receives the operation's zero-based batch index and its `OperationCommit` in exact input order. No callback occurs for the failed operation or skipped suffix. This exposes deterministic commit order without requiring `game-state` to allocate a `Vec` per batch.

## Input batches

A logical frame/tick may eventually combine operations translated from multiple sources such as:

1. decoded inbound server operations,
2. local user intents,
3. client-script results,
4. timers/scheduled work,
5. lifecycle operations.

CP3D through CP3F define ordering, commit, and semantic replay behavior *after* a concrete ordered operation sequence has been assembled. Policy for merging multiple source queues into that ordered sequence remains an application/runtime concern to specify when those producers exist. Parallel preparation may never alter the final committed order.

## Events

Events describe committed facts, not requests. Example distinction:

- command/input: `RequestMenuAction`
- operation: validated semantic interaction
- event: `InventorySlotChanged`

Events should carry stable semantic identifiers and the minimum payload consumers require. Event generation must occur only after the corresponding mutation successfully commits.

## StateDelta

A `StateDelta` is an efficient description of what changed. Renderer/UI may use it to incrementally update projections. It is not independently authoritative and can be discarded/rebuilt from state.

## Replay

CP3F defines the first executable semantic replay boundary.

`ReplayEnvelope` pairs:

- one source-neutral `SemanticOperation`, and
- the exact `OperationCommit` that was recorded when that operation originally succeeded.

The envelope is currently an **in-memory semantic contract**, not a stable on-disk or wire format. It deliberately contains no byte serialization, version header, compression, packet capture, wall-clock timestamp, protocol opcode, platform handle, or renderer/plugin data.

`GameState::replay_envelopes` and `replay_envelopes_with_commits` replay envelopes in exact slice order. Replay always calls the normal `GameState::apply_operation` mutation gateway; there is no replay-only state mutation path.

For each envelope:

1. replay applies the recorded semantic operation,
2. a rejected operation stops replay at that exact zero-based index with the original typed `OperationError`,
3. if the operation succeeds, replay compares the newly produced `OperationCommit` with the recorded commit using exact equality,
4. a commit mismatch stops replay at that exact index and reports both expected and actual commit metadata,
5. verified-commit callbacks run only after the actual commit matches the recording exactly,
6. later envelopes are never attempted after a rejection or mismatch,
7. an empty replay sequence is a valid no-op.

A replay rejection leaves the rejected operation uncommitted, following normal operation semantics. A commit mismatch is different: the mismatch can only be known after the operation has successfully committed and produced its actual metadata. Therefore replay state after a commit mismatch is invalid for continued use and must be discarded rather than rolled back or resumed.

This design intentionally avoids cloning the authoritative state tree or introducing rollback authority solely for replay verification.

Current CP3F replay is sufficient to prove that the modeled semantic operation stream can reconstruct equivalent modeled semantic state without network or GPU access. It does **not** yet define the persistent replay-file format required for full client sessions. Future replay checkpoints must add, as separate concerns, the initial profile/revision identity, deterministic random inputs/seeds when those exist, source/provenance metadata where needed, persistent encoding/versioning, and integration with additional semantic domains.

Raw packet capture may be an optional diagnostic stream later, but it is not the canonical semantic replay contract.

## Determinism

- No wall-clock reads inside domain transitions.
- Randomness enters through an injected deterministic source.
- HashMap iteration order must not define visible behavior.
- Parallel preparation may not change committed operation order.
- Identical initial state plus an identical valid operation sequence must produce identical observable state and commit metadata.
- Identical initial state plus an identical operation batch must produce the same successful prefix, commit metadata order, first failure index/error, and final observable state.
- Replaying an identical `ReplayEnvelope` sequence from equivalent initial semantic state must reproduce the recorded `OperationCommit` sequence exactly.
- Replay divergence must stop at the first exact envelope index where an operation is rejected or commit metadata differs.

## Acceptance criteria

- Tests can construct a state and apply typed semantic operations and ordered operation batches.
- Identical initial state + inputs produce identical observable state/events.
- Batch tests prove exact ordering, partial-commit semantics, first-failure reporting, suffix suppression, and successful-prefix commit delivery.
- Successful mutations expose committed event/delta metadata; failed operations expose none.
- A successful semantic no-op produces no event and an empty `StateDelta`.
- Replay tests can record semantic operation/commit envelopes, replay them into equivalent initial state without network/GPU access, and reconstruct equivalent modeled semantic state.
- Replay tests detect the first rejected operation and first commit mismatch at the exact envelope index.
- Replay consumers receive callbacks only for commits that exactly match their recorded envelope.
- Projection rebuild equals incremental projection in reference tests once projections exist.
