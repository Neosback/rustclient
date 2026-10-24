# Revision 240 actor path queue implementation checkpoint

Status: bounded queue retention, oldest-entry consumption transition, action movement gates and backlog decrement implemented; authoritative fine-position scheduling remains incomplete.

This checkpoint updates the implementation status described in `MOVEMENT-FACING.md` without changing that document's exact revision-240 source findings. In particular, the older "Current Rust mismatches" notes that players had no bounded queue and NPCs retained only the latest request are now superseded by the implementation described here.

## Implemented semantic state

`game-state::ActorPathQueue` is authoritative live actor state shared by players and NPCs.

- Capacity is 9 active entries, matching the observable active depth of the revision-240 `Actor` queue.
- New requests are inserted at the front.
- The oldest surviving request is at the tail.
- `pop_oldest` removes that tail entry, matching the stock scheduler's `field1471 - 1` consumption order.
- Saturation drops the previous oldest request when a tenth request is inserted.
- Each entry retains a target `WorldTile` and semantic `MoveSpeed`.
- The complete ordered queue participates in semantic fingerprint schema v18.

The existing player/NPC `tile` fields still retain the server-confirmed/network endpoint. The queue is separate scheduler state. RustClient does not yet own the actor's current fine position in `GameState`, so queue consumption is an explicit authoritative transition rather than an incorrect assumption that one tile completes every client cycle.

## Player update behavior

For visible players:

- ordinary non-stationary movement appends the newly confirmed endpoint to the actor queue with its effective traversal speed;
- `MoveSpeed::Stationary` clears the active queue and resets the action/path remainder;
- exact/forced movement clears the active queue and resets the action/path remainder before retaining the normalized exact trajectory;
- player visibility index reuse creates a fresh empty queue with the new actor instance.

This preserves the existing confirmed endpoint contract while retaining the backlog needed by the stock scheduler.

## NPC update behavior

For visible NPCs:

- one-step requests append one target;
- two-step run requests append the first target and then the second target, so oldest-first consumption remains wire order;
- exact/forced movement clears the active queue and resets the action/path remainder;
- despawn/reuse cannot inherit an older actor's queue because the queue lives on the lifecycle-owned `NpcEntity`.

`NpcPath` remains the latest packet request used by existing projection code. `ActorPathQueue` is the accumulated scheduler backlog.

## Action-sequence interaction

When a different action sequence is accepted through the resolved `SequencePolicy` path, `ActorSequenceSlot::assign_sequence` receives the actor's real current queue length. That initializes the revision-240 `field1476`-style pre-action remainder.

Same-ID restart modes do not replace that remainder. Stationary/reset and exact-movement paths reset it to zero.

`game-state::sequence_scheduler` now implements the exact movement-gate choice used by revision 240:

- no active action: path progress is allowed;
- an active action with non-zero explicit delay: path progress is allowed;
- active action, delay zero, positive pre-action remainder: use `movement_gate_with_backlog` (sequence opcode 9);
- active action, delay zero, zero remainder: use `movement_gate_without_backlog` (sequence opcode 10);
- gate value `0` blocks normal path progress;
- unresolved legacy policy does not invent a blocking decision.

When an oldest queued entry has actually been reached and the gate allows progress, the shared consumption transition removes that oldest entry and decrements the pre-action remainder by one while it remains positive. A blocked transition mutates neither queue nor remainder.

The Rust field is still named `path_backlog_at_assignment` from the earlier assignment checkpoint, but after this checkpoint its runtime meaning matches the mutable remaining `field1476` counter. A later model-cleanup checkpoint can rename it without changing behavior.

## Why consumption is not called once per client cycle yet

`crates/scene/src/motion.rs` already owns a disposable drawn-position interpolation queue, but architecture rules forbid scene projection from mutating authoritative `GameState`.

Conversely, `GameState` currently retains confirmed tile endpoints but not the actor's current fine-coordinate position. Without that position, it cannot truthfully know whether the oldest target was reached on a given client cycle. Calling `pop_oldest` once per cycle would therefore be a fabricated rule and would consume walk/run/crawl paths too quickly.

The next movement checkpoint must move enough fine-position scheduling state into the authoritative actor domain to decide target completion. That scheduler can then call the already-implemented gate/consumption transition at the exact reach/snap boundary.

## Deliberately not implemented here

This checkpoint does **not** claim parity for:

- authoritative per-client-cycle fine-position advancement;
- automatic invocation of path consumption on target reach;
- movement blocking/catch-up counter (`field1452`) behavior;
- large-gap snapping in authoritative state;
- crawl/walk/run step scaling and backlog acceleration in authoritative state;
- turn-slowing, desired/current orientation and directional locomotion selection;
- ordinary scene-bound safety reset behavior beyond the already-modeled explicit reset paths.

Those behaviors remain specified by `MOVEMENT-FACING.md` and `SEQUENCE-SCHEDULER.md`. The next dependency is the authoritative fine-position/path-step scheduler, not another packet or cache decoder.
