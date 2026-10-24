# Revision 240 actor path queue implementation checkpoint

Status: bounded queue retention, action movement gates, ordinary player and cache-resolved NPC fine-position scheduling, ordinary blocked-cycle catch-up, and ordinary actor position projection are authoritative; forced movement and facing-dependent speed remain incomplete.

This checkpoint updates the implementation status described in `MOVEMENT-FACING.md` without changing that document's exact revision-240 source findings. Older mismatch notes that players had no bounded queue, NPCs retained only the latest request, NPC fine scheduling was disconnected, or `field1452` catch-up was absent are superseded here and by `NPC-PATH-ACCELERATION.md` / `ACTOR-CATCH-UP.md`.

## Implemented semantic state

`game-state::ActorPathQueue` is authoritative live actor state shared by players and NPCs.

- Capacity is 9 active entries, matching the observable active depth of the revision-240 `Actor` queue.
- New requests are inserted at the front.
- The oldest surviving request is at the tail.
- `pop_oldest` removes that tail entry, matching the stock scheduler's `field1471 - 1` consumption order.
- Saturation drops the previous oldest request when a tenth request is inserted.
- Each entry retains a target `WorldTile` and semantic `MoveSpeed`.
- The queue owns a lazily initialized authoritative world fine-position cursor.
- For NPCs, it also retains the definition-resolved `PathAccelerationPolicy` used by future ordinary path cycles.
- It retains signed `catch_up_cycles`, the authoritative revision-240 `field1452` equivalent.
- The complete ordered queue, effective fine position, retained acceleration policy and catch-up debt participate in semantic fingerprint schema v21.

The existing player/NPC `tile` fields remain the server-confirmed/network endpoint. Fine position is separate scheduler state: a packet can move the confirmed endpoint immediately while the actor cursor advances toward queued targets over client cycles.

The fine cursor is normalized to the south-west footprint corner (`tile * 128`) instead of storing the stock actor-size centre offset. For a fixed actor footprint that omitted offset is constant on both current and target coordinates, so movement deltas, target equality and the 288-unit snap threshold are unchanged while the shared scheduler avoids depending on actor definition size.

## Player update behavior

For visible players:

- ordinary non-stationary movement first initializes the fine cursor from the **pre-packet** actor tile, then appends the newly confirmed endpoint with its effective traversal speed;
- `MoveSpeed::Stationary` clears queued requests, resets the fine cursor directly to the confirmed tile, resets action/path remainder and hard-resets catch-up debt;
- exact/forced movement clears queued requests and resets the action/path remainder while preserving the current fine cursor and catch-up debt until the exact scheduler owns the real active-cycle reset point;
- player visibility index reuse creates fresh empty queue/fine-position/catch-up state with the new actor instance.

This ordering matters. Initializing the cursor after overwriting `player.tile` would make a newly queued move begin at its destination and disappear instantly.

## NPC definition-derived acceleration

Revision 240 does not always use the player backlog thresholds for NPCs. Exact source evidence is recorded in `NPC-PATH-ACCELERATION.md`.

`NPCComposition.field5325` defaults true. Definition opcode 109 sets it false. `Actor.method3284` uses that value to choose between:

- standard: backlog `> 2` -> step 6 and `> 3` -> step 8;
- early: backlog `> 1` -> step 6 and `> 2` -> step 8.

The cache adapter resolves that source-specific selector into revision-neutral `PathAccelerationPolicy`. Application orchestration attaches policies for spawned NPC definitions and definition-change masks before `GameState` mutation. Protocol decoding remains cache-free.

NPC lifecycle rules are explicit:

- spawn stores the resolved definition policy when available;
- normal path enqueue, clear and reset retain it;
- a definition-change mask replaces the retained policy along with `definition_id`;
- despawn/reuse cannot inherit another NPC's policy;
- direct semantic fixtures with no resolved policy remain unresolved rather than silently assuming player thresholds.

## Authoritative ordinary actor cycle scheduler

`game-state::actor_movement` advances ordinary queued players and cache-resolved NPCs from the same client-cycle transition that advances action delays.

For each client cycle, movement is evaluated before that cycle decrements the action's explicit delay. That preserves the revision-240 ordering where an action with a non-zero delay does not yet apply its normal path gate.

For an ordinary queued path:

1. the opcode-9/opcode-10 action gate is checked;
2. if that gate blocks, fine position and queue remain unchanged and `catch_up_cycles` increments by one;
3. the oldest queued target is converted to fine coordinates;
4. if the maximum axis gap is greater than 288 fine units, the actor snaps to the target and the oldest entry is consumed **without spending catch-up debt**;
5. otherwise the base step is 4 fine units;
6. backlog thresholds from the actor's resolved acceleration policy can raise that step to 6 or 8;
7. after ordinary backlog/turn-speed choices, positive catch-up debt with backlog greater than one forces base step 8 and spends exactly one debt cycle;
8. authored `Run` then doubles the step and `Crawl` halves it;
9. each axis approaches the target by at most that final step;
10. target equality invokes the shared authoritative consumption transition.

Players use `PathAccelerationPolicy::STANDARD`. NPCs use their resolved definition policy. A cache-unresolved NPC does not advance through the authoritative ordinary scheduler because guessing would make future state definition-dependent without the definition evidence.

An empty ordinary path resets catch-up debt to zero. Immediate/hard placement through `ActorPathQueue::reset_to` also resets it. Generic `ActorPathQueue::clear` deliberately preserves it because exact revision-240 queue-clear helpers do not universally reset `field1452`.

The shared consumption transition removes the oldest entry and decrements the positive `field1476`-style pre-action remainder. The opcode-9/opcode-10 gate/decrement behavior is therefore reached by real player and NPC target completion.

`ACTOR-CATCH-UP.md` records the exact increment/decrement/reset evidence and the distinction between ordinary catch-up and the still-deferred forced-movement reset timing.

## Ordinary actor projection migration

`crates/scene/src/authoritative_motion.rs` fronts the older disposable motion projector.

For ordinary players and cache-resolved ordinary NPCs, `ActorMotion` copies position from `ActorPathQueue::fine_position(...)` during scene observation. Existing geometry/camera/minimap consumers therefore receive GameState position without owning a second ordinary path scheduler.

The legacy `scene::motion` implementation is still used for disposable pose/turn state and forced/exact movement. It also remains a fallback for deliberately unresolved direct NPC fixtures. Its independently walked ordinary coordinate is no longer the connected cache-resolved actor-position authority.

Exact/forced movement is the deliberate exception: until its frame-boundary gate and interpolation move into GameState, the facade delegates an actively forced actor's drawn position to the legacy projector.

## Action-sequence interaction

When a different action sequence is accepted through the resolved `SequencePolicy` path, `ActorSequenceSlot::assign_sequence` receives the actor's real current queue length. That initializes the revision-240 `field1476`-style pre-action remainder.

Same-ID restart modes do not replace that remainder. Stationary/reset and exact-movement paths reset it to zero.

`game-state::sequence_scheduler` implements the exact movement-gate choice used by revision 240:

- no active action: path progress is allowed;
- an active action with non-zero explicit delay: path progress is allowed;
- active action, delay zero, positive pre-action remainder: use `movement_gate_with_backlog` (sequence opcode 9);
- active action, delay zero, zero remainder: use `movement_gate_without_backlog` (sequence opcode 10);
- gate value `0` blocks normal path progress;
- unresolved legacy sequence policy does not invent a blocking decision.

A blocked ordinary actor cycle now increments catch-up debt while leaving fine position, queue and `field1476` remainder unchanged. Once movement becomes allowed, debt can force base step 8 while backlog remains greater than one. Reaching/snapping a target independently decrements the positive `field1476` remainder; catch-up debt and pre-action remainder are different live counters.

The Rust field is still named `path_backlog_at_assignment` from the earlier assignment checkpoint, but its runtime meaning now matches the mutable remaining `field1476` counter. A later model-cleanup checkpoint can rename it without changing behavior.

## Forced-movement boundary

Exact revision-240 evidence shows both forced/exact phases reset `field1452` on every active forced cycle. RustClient intentionally does **not** reset catch-up merely because `exact_move.is_some()`.

The current trajectory marker can remain stored beyond its active interval, while `WorldEntitiesState::advance_cycles` does not yet receive the exact per-inner-cycle forced phase. Resetting from marker presence would therefore erase debt for too long and create incorrect future ordinary movement.

The authoritative forced-movement checkpoint must port interpolation, the legacy action-frame boundary gate, exact client-cycle phase ownership, and the matching catch-up reset together.

## Deliberately not implemented here

This checkpoint does **not** claim parity for:

- authoritative exact/forced-movement interpolation or its active-cycle catch-up reset, because the legacy action-frame boundary gate is still outstanding;
- turn-slowing from desired/current orientation mismatch;
- directional walk/run/crawl sequence selection;
- desired/current orientation and moving facing targets;
- ordinary scene-bound safety reset behavior beyond already modeled explicit reset paths;
- removal of legacy pose/turn and forced-movement projection.

Those behaviors remain specified by `MOVEMENT-FACING.md`, `SEQUENCE-SCHEDULER.md`, and `ACTOR-CATCH-UP.md`. The next smallest movement dependency is authoritative exact/forced movement because it closes the remaining positional projection exception and the remaining `field1452` reset site.
