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
- operation application does not itself emit committed events yet.

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

Operation batches are therefore **not transactional** and are not rolled back on later failure. This avoids full-state cloning or rollback authority and gives later events/replay a single observable committed order: the successful prefix, in input order.

CP3D intentionally does not define event types, `StateDelta`, replay envelopes, provenance metadata, or domain operations beyond lifecycle/time. Those contracts remain separate checkpoints.

## Input batches

A logical frame/tick may eventually combine operations translated from multiple sources such as:

1. decoded inbound server operations,
2. local user intents,
3. client-script results,
4. timers/scheduled work,
5. lifecycle operations.

CP3D defines only the ordering and failure semantics *after* a concrete ordered slice has been assembled. Policy for merging multiple source queues into that ordered slice remains an application/runtime concern to specify when those producers exist. Parallel preparation may never alter the final committed order.

## Events

Events describe committed facts, not requests. Example distinction:

- command/input: `RequestMenuAction`
- operation: validated semantic interaction
- event: `InventorySlotChanged`

Events should carry stable semantic identifiers and the minimum payload consumers require.

## StateDelta

A `StateDelta` is an efficient description of what changed. Renderer/UI may use it to incrementally update projections. It is not independently authoritative and can be discarded/rebuilt from state.

## Replay

The replay format will record enough information to reproduce semantic state transitions without a live server. Initial target: protocol-decoded operation batches + local input + deterministic clocks/random seeds + profile metadata.

Raw packet capture may be an optional diagnostic stream, not the canonical replay contract.

## Determinism

- No wall-clock reads inside domain transitions.
- Randomness enters through an injected deterministic source.
- HashMap iteration order must not define visible behavior.
- Parallel preparation may not change committed operation order.
- Identical initial state plus an identical valid operation sequence must produce identical observable state.
- Identical initial state plus an identical operation batch must produce the same successful prefix, first failure index/error, and final observable state.

## Acceptance criteria

- Tests can construct a state and apply typed semantic operations and ordered operation batches.
- Identical initial state + inputs produce identical observable state/events.
- Batch tests prove exact ordering, partial-commit semantics, first-failure reporting, and suffix suppression.
- Projection rebuild equals incremental projection in reference tests once projections/events exist.
- Replay can drive the client without network I/O once implemented.
