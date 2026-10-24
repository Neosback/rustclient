# Revision 240 actor path queue implementation checkpoint

Status: bounded queue retention implemented; per-cycle consumption and movement scheduling remain incomplete.

This checkpoint updates the implementation status described in `MOVEMENT-FACING.md` without changing that document's exact revision-240 source findings. In particular, the older "Current Rust mismatches" notes that players had no bounded queue and NPCs retained only the latest request are now superseded by the implementation described here.

## Implemented semantic state

`game-state::ActorPathQueue` is authoritative live actor state shared by players and NPCs.

- Capacity is 9 active entries, matching the observable active depth of the revision-240 `Actor` queue.
- New requests are inserted at the front.
- The oldest surviving request is at the tail and is the next request the future cycle scheduler must consume.
- Saturation drops the previous oldest request when a tenth request is inserted.
- Each entry retains a target `WorldTile` and semantic `MoveSpeed`.
- The complete ordered queue participates in semantic fingerprint schema v18.

The existing player/NPC `tile` fields still retain the server-confirmed/network endpoint. The new queue is separate scheduler state and is not yet consumed to move an actor per client cycle.

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

When a different action sequence is accepted through the resolved `SequencePolicy` path, `ActorSequenceSlot::assign_sequence` now receives the actor's real current queue length instead of a temporary zero. That value corresponds to the initial revision-240 `field1476` behavior.

Same-ID restart modes do not replace that remainder. Stationary/reset and exact-movement paths reset it to zero.

The next scheduler checkpoint must decrement the remainder when pre-action queued entries are consumed. Until then, the stored field is the correct assignment/reset value but does not yet progress per cycle.

## Deliberately not implemented here

This checkpoint does **not** claim parity for:

- per-client-cycle oldest-entry consumption;
- action movement gates from sequence opcodes 9 and 10;
- `field1476` decrement as queued entries finish;
- movement blocking/catch-up counter behavior;
- large-gap snapping;
- crawl/walk/run step scaling and backlog acceleration;
- desired/current orientation and directional locomotion selection;
- ordinary scene-bound safety reset behavior beyond the already-modeled explicit reset paths.

Those behaviors remain specified by `MOVEMENT-FACING.md` and `SEQUENCE-SCHEDULER.md` and are the next implementation boundary.
