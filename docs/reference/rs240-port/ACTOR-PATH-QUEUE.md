# Revision 240 actor path queue implementation checkpoint

Status: bounded queue retention, action movement gates, ordinary player and cache-resolved NPC fine-position scheduling, and ordinary actor position projection are authoritative; forced movement, blocked-cycle catch-up and facing-dependent speed remain incomplete.

This checkpoint updates the implementation status described in `MOVEMENT-FACING.md` without changing that document's exact revision-240 source findings. Older mismatch notes that players had no bounded queue, NPCs retained only the latest request, or NPC fine scheduling was disconnected are superseded here and by `NPC-PATH-ACCELERATION.md`.

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
- The complete ordered queue, effective fine position and retained acceleration policy participate in semantic fingerprint schema v20.

The existing player/NPC `tile` fields remain the server-confirmed/network endpoint. Fine position is separate scheduler state: a packet can move the confirmed endpoint immediately while the actor cursor advances toward queued targets over client cycles.

The fine cursor is normalized to the south-west footprint corner (`tile * 128`) instead of storing the stock actor-size centre offset. For a fixed actor footprint that omitted offset is constant on both current and target coordinates, so movement deltas, target equality and the 288-unit snap threshold are unchanged while the shared scheduler avoids depending on actor definition size.

## Player update behavior

For visible players:

- ordinary non-stationary movement first initializes the fine cursor from the **pre-packet** actor tile, then appends the newly confirmed endpoint with its effective traversal speed;
- `MoveSpeed::Stationary` clears queued requests, resets the fine cursor directly to the confirmed tile, and resets the action/path remainder;
- exact/forced movement clears queued requests and resets the action/path remainder while preserving the current fine cursor for the future exact-movement approach phase;
- player visibility index reuse creates fresh empty queue/fine-position state with the new actor instance.

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

1. the existing opcode-9/opcode-10 action gate is checked;
2. the oldest queued target is converted to fine coordinates;
3. if the maximum axis gap is greater than 288 fine units, the actor snaps to the target and the oldest entry is consumed;
4. otherwise the base step is 4 fine units;
5. backlog thresholds from the actor's resolved acceleration policy can raise that step to 6 or 8;
6. authored `Run` doubles the step and `Crawl` halves it;
7. each axis approaches the target by at most that step;
8. target equality invokes the shared authoritative consumption transition.

Players use `PathAccelerationPolicy::STANDARD`. NPCs use their resolved definition policy. A cache-unresolved NPC does not advance through the authoritative ordinary scheduler because guessing would make future state definition-dependent without the definition evidence.

The shared consumption transition removes the oldest entry and decrements the positive `field1476`-style pre-action remainder. The opcode-9/opcode-10 gate/decrement behavior is therefore reached by real player and NPC target completion.

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

A blocked ordinary actor cycle changes neither fine position, queue nor remainder. Once movement is allowed and a target is reached/snapped, the remainder decrements while positive and the scheduler naturally switches to the opcode-10 gate after it reaches zero.

The Rust field is still named `path_backlog_at_assignment` from the earlier assignment checkpoint, but its runtime meaning now matches the mutable remaining `field1476` counter. A later model-cleanup checkpoint can rename it without changing behavior.

## Deliberately not implemented here

This checkpoint does **not** claim parity for:

- blocked-cycle catch-up counter (`field1452`) behavior and its forced recovery step;
- authoritative exact/forced-movement interpolation, because the legacy action-frame boundary gate is still outstanding;
- turn-slowing from desired/current orientation mismatch;
- directional walk/run/crawl sequence selection;
- desired/current orientation and moving facing targets;
- ordinary scene-bound safety reset behavior beyond already modeled explicit reset paths;
- removal of legacy pose/turn and forced-movement projection.

Those behaviors remain specified by `MOVEMENT-FACING.md` and `SEQUENCE-SCHEDULER.md`. The next smallest scheduler dependency is `field1452` blocked-cycle catch-up because both player and NPC ordinary fine scheduling now share one authoritative cycle path.
