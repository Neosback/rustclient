# SPEC-0002: Authoritative State, Events, Commands, and Replay

**Status:** Draft

## Purpose

Make runtime behavior deterministic enough to debug, replay, test plugins, and support both native/browser front ends.

## State model

`GameState` owns semantic live state such as players/NPCs, scene occupancy, inventories, varps/varbits, skills, widgets, camera intent where semantic, chat/message state, timers, and connection/session metadata that affects behavior.

State is changed through explicit operations. Systems must not retain mutable aliases into state across a transition boundary.

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
