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
- Session lifecycle changes pass through explicit validated transitions.
- Starting a session initializes session-owned state; ending it clears session-owned state before another session may begin.
- An invalid lifecycle transition leaves state unchanged.

These constraints apply before the richer operation/event model is implemented and remain invariants afterward.

### Semantic game time

The current semantic game tick is session-owned authoritative state.

- An inactive state has no observable current tick.
- Beginning a session starts at `GameTick::ZERO`.
- Ending a session clears the observable session tick.
- Tick advancement requires an active session.
- Tick advancement uses an explicit `TickSpan`; domain transitions do not read wall time.
- Tick arithmetic is checked. Overflow rejects the transition without mutating state.
- A future runtime scheduler decides *when* to request tick advancement; `GameState` only validates and commits the semantic transition.

CP3B intentionally does not introduce a generic operation enum, event stream, `StateDelta`, or replay envelope. Those build on this transition invariant later.

## Input batches

A logical frame/tick may contain:

1. decoded inbound server operations,
2. local user intents,
3. client-script results,
4. timers/scheduled work,
5. lifecycle operations.

Ordering rules must be specified before protocol correctness tests are ratified. The runtime records the committed ordering when replay capture is enabled.

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

## Acceptance criteria

- Tests can construct a state and apply typed operation batches.
- Identical initial state + inputs produce identical observable state/events.
- Projection rebuild equals incremental projection in reference tests.
- Replay can drive the client without network I/O once implemented.
