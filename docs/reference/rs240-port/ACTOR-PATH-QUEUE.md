# Revision 240 actor path queue implementation checkpoint

Status: bounded queue retention, action movement gates and ordinary player fine-position scheduling are authoritative; NPC and forced-movement fine scheduling remain incomplete.

This checkpoint updates the implementation status described in `MOVEMENT-FACING.md` without changing that document's exact revision-240 source findings. In particular, the older "Current Rust mismatches" notes that players had no bounded queue and NPCs retained only the latest request are superseded by the implementation described here.

## Implemented semantic state

`game-state::ActorPathQueue` is authoritative live actor state shared by players and NPCs.

- Capacity is 9 active entries, matching the observable active depth of the revision-240 `Actor` queue.
- New requests are inserted at the front.
- The oldest surviving request is at the tail.
- `pop_oldest` removes that tail entry, matching the stock scheduler's `field1471 - 1` consumption order.
- Saturation drops the previous oldest request when a tenth request is inserted.
- Each entry retains a target `WorldTile` and semantic `MoveSpeed`.
- The queue also owns a lazily initialized authoritative world fine-position cursor.
- The complete ordered queue and its effective fine position participate in semantic fingerprint schema v19.

The existing player/NPC `tile` fields remain the server-confirmed/network endpoint. Fine position is separate scheduler state: a packet can move the confirmed endpoint immediately while the actor cursor advances toward queued targets over client cycles.

The fine cursor is normalized to the south-west footprint corner (`tile * 128`) instead of storing the stock actor-size centre offset. For a fixed actor footprint that omitted offset is constant on both current and target coordinates, so movement deltas, target equality and the 288-unit snap threshold are unchanged while the shared scheduler avoids depending on NPC/player definition size.

## Player update behavior

For visible players:

- ordinary non-stationary movement first initializes the fine cursor from the **pre-packet** actor tile, then appends the newly confirmed endpoint with its effective traversal speed;
- `MoveSpeed::Stationary` clears queued requests, resets the fine cursor directly to the confirmed tile, and resets the action/path remainder;
- exact/forced movement clears queued requests and resets the action/path remainder while preserving the current fine cursor for the future exact-movement approach phase;
- player visibility index reuse creates a fresh empty queue/fine-position state with the new actor instance.

This ordering matters. Initializing the cursor after overwriting `player.tile` would make a newly queued move begin at its destination and disappear instantly.

## Authoritative ordinary player cycle scheduler

`game-state::actor_movement` now advances ordinary queued **player** movement from the same `SceneryOperation::AdvanceCycles` client-cycle transition that advances action delays.

For each client cycle, movement is evaluated before that cycle decrements the action's explicit delay. That preserves the revision-240 ordering where an action with a non-zero delay does not yet apply its normal path gate.

For an ordinary queued player path:

1. the existing opcode-9/opcode-10 action gate is checked;
2. the oldest queued target is converted to fine coordinates;
3. if the maximum axis gap is greater than 288 fine units, the actor snaps to the target and the oldest entry is consumed;
4. otherwise the normal base step is 4 fine units;
5. normal queued backlog greater than 2 raises the step to 6, and greater than 3 raises it to 8;
6. authored `Run` doubles the step and `Crawl` halves it;
7. each axis approaches the target by at most that step;
8. target equality invokes the shared authoritative consumption transition.

That consumption transition removes the oldest entry and decrements the positive `field1476`-style pre-action remainder. Therefore the Part 6 gate/decrement behavior is now reached by real ordinary player target completion rather than existing only as a helper.

## NPC update behavior

For visible NPCs, packet-time retention remains implemented:

- one-step requests append one target;
- two-step run requests append the first target and then the second target, so oldest-first consumption remains wire order;
- exact/forced movement clears the active queue and resets the action/path remainder;
- despawn/reuse cannot inherit an older actor's queue because the queue lives on the lifecycle-owned `NpcEntity`.

`NpcPath` remains the latest packet request used by existing projection code. `ActorPathQueue` is the accumulated scheduler backlog. NPC fine-position **cycle advancement is not yet connected** because revision 240 has definition-controlled alternate acceleration thresholds that should be preserved rather than silently treating every NPC like a player.

## Action-sequence interaction

When a different action sequence is accepted through the resolved `SequencePolicy` path, `ActorSequenceSlot::assign_sequence` receives the actor's real current queue length. That initializes the revision-240 `field1476`-style pre-action remainder.

Same-ID restart modes do not replace that remainder. Stationary/reset and exact-movement paths reset it to zero.

`game-state::sequence_scheduler` implements the exact movement-gate choice used by revision 240:

- no active action: path progress is allowed;
- an active action with non-zero explicit delay: path progress is allowed;
- active action, delay zero, positive pre-action remainder: use `movement_gate_with_backlog` (sequence opcode 9);
- active action, delay zero, zero remainder: use `movement_gate_without_backlog` (sequence opcode 10);
- gate value `0` blocks normal path progress;
- unresolved legacy policy does not invent a blocking decision.

A blocked ordinary player cycle changes neither fine position, queue nor remainder. Once movement is allowed and a target is reached/snapped, the remainder decrements while positive and the scheduler naturally switches to the opcode-10 gate after it reaches zero.

The Rust field is still named `path_backlog_at_assignment` from the earlier assignment checkpoint, but its runtime meaning now matches the mutable remaining `field1476` counter. A later model-cleanup checkpoint can rename it without changing behavior.

## Projection boundary still to remove

`crates/scene/src/motion.rs` still carries its older disposable interpolation queue for drawing. It must not feed changes back into `GameState` and is no longer the semantic source of truth for ordinary player position.

This checkpoint deliberately does not rewrite the renderer/projection path at the same time as introducing the authoritative scheduler. A following small checkpoint should make player rendering/camera/minimap translation consume `ActorPathQueue::fine_position(...)`, then remove the duplicate ordinary-player positional walk from `scene::motion`.

## Deliberately not implemented here

This checkpoint does **not** claim parity for:

- NPC authoritative per-client-cycle fine-position advancement and its alternate definition-driven acceleration thresholds;
- authoritative exact/forced-movement interpolation, because the legacy action-frame boundary gate is still outstanding;
- blocked-cycle catch-up counter (`field1452`) behavior;
- turn-slowing from desired/current orientation mismatch;
- directional walk/run/crawl sequence selection;
- desired/current orientation and moving facing targets;
- ordinary scene-bound safety reset behavior beyond already modeled explicit reset paths;
- removal of the older duplicate `scene::motion` ordinary-player position projection.

Those behaviors remain specified by `MOVEMENT-FACING.md` and `SEQUENCE-SCHEDULER.md`. The next small dependency is either the NPC ordinary fine scheduler or projection migration; forced movement should remain separate until its frame-gate dependency is resolved.
