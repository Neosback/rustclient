# Revision 240 actor path queue implementation checkpoint

<!-- authority-notice -->
> Domain source/ownership research; implementation statements are bounded to the dates
> recorded below. The [current matrix](PORT-MATRIX.md) and [audit](CURRENT-AUDIT.md)
> supersede older gap/status lists; preserve exact source details and historical evidence.

Status: bounded path retention, ordinary player/NPC fine scheduling, action gates, blocked-cycle catch-up, exact/forced movement, and actor position projection are authoritative. The main remaining path-scheduler gap is facing/turning-dependent movement and directional locomotion.

This checkpoint supersedes older mismatch notes in `MOVEMENT-FACING.md` that describe missing bounded queues, disconnected NPC fine scheduling, missing `field1452`, or projection-owned forced movement. Exact source findings in that document remain evidence.

## Authoritative semantic state

`game-state::ActorPathQueue` is live actor movement state shared by players and NPCs.

- Capacity is 9 active entries.
- New requests are inserted at the front; the oldest active request is consumed from the tail.
- Saturation drops the previous oldest request when a tenth request is inserted.
- Each entry retains target `WorldTile` plus semantic `MoveSpeed`.
- The queue owns the authoritative world fine-position cursor used by ordinary and forced movement.
- NPC queues retain the definition-resolved `PathAccelerationPolicy` required by future ordinary cycles.
- The queue retains signed `catch_up_cycles`, the revision-240 `field1452` equivalent.

The player/NPC `tile` field remains the server-confirmed/network endpoint. Fine position is separate client-cycle state. The fine cursor is normalized to the south-west footprint corner (`tile * 128`); omitting the constant fixed-footprint centre offset leaves deltas, equality and the 288-unit snap threshold unchanged.

Semantic fingerprint schema v22 includes effective fine position, ordered queue entries, NPC acceleration policy, catch-up debt and the active action timing that can gate forced interpolation.

## Packet-time path behavior

For visible players:

- ordinary non-stationary movement initializes a missing fine cursor from the pre-packet tile, then queues the confirmed endpoint;
- `MoveSpeed::Stationary` clears the queue, places the fine cursor at the confirmed tile and hard-resets path/action catch-up state;
- exact movement clears queued requests and the `field1476`-style action/path remainder while preserving the current fine cursor until the forced client-cycle phase runs;
- visibility index reuse creates fresh path/fine/catch-up state.

For NPCs, ordered one-step/two-step requests are retained in the same bounded queue. Definition-derived acceleration policy is resolved before GameState and survives normal queue clears/resets for that NPC lifetime.

## Definition-derived NPC acceleration

Revision 240 uses `NPCComposition.field5325` to choose backlog thresholds. Exact evidence is recorded in `NPC-PATH-ACCELERATION.md`.

- default / no opcode 109: backlog `> 2` -> step 6, `> 3` -> step 8;
- opcode 109: backlog `> 1` -> step 6, `> 2` -> step 8.

The cache adapter projects this into revision-neutral `PathAccelerationPolicy`; protocol remains cache-free. A direct NPC fixture without required policy stays unresolved instead of assuming one branch.

## Authoritative ordinary cycle

Movement is evaluated before action playback advances for that same client cycle.

For an ordinary queued target:

1. evaluate opcode-9/opcode-10 action movement gate;
2. if blocked, increment `catch_up_cycles` without moving;
3. compute oldest target and fine-coordinate gap;
4. gap `> 288` snaps and consumes without spending catch-up debt;
5. otherwise choose base step 4 and apply player/NPC backlog thresholds;
6. positive catch-up debt with backlog `> 1` forces base step 8 and spends one debt cycle;
7. run doubles and crawl halves the base step;
8. approach target on each axis;
9. on target equality, consume the oldest request and decrement the positive `field1476`-style remainder.

An empty ordinary queue resets catch-up debt. Generic queue `clear` preserves debt because stock queue-clear helpers do not universally reset `field1452`; `reset_to` is a hard placement and clears it.

## Authoritative forced/exact cycle

`SceneryState` remains the single `ClientCycle` clock. For each inner cycle, `WorldEntitiesState` passes the exact cycle into `game-state::actor_movement` before sequence playback advances.

If an actor has an active forced trajectory:

- while `current <= start`, approach the forced start coordinate using the stock remaining-cycle divisor;
- while `start < current <= end`, interpolate start to end only when the revision-240 forced action gate permits it;
- exact end always permits interpolation;
- cached-model actions bypass the legacy frame-boundary test;
- a zero-delay legacy action requires `current_frame_cycle + 1 > current_frame_delay`;
- unresolved required timing holds position rather than guessing;
- every active forced cycle resets `catch_up_cycles` and applies the authored orientation;
- once `current > end`, clear the trajectory and resume ordinary scheduling in that same cycle.

`FORCED-MOVEMENT.md` records the full evidence and timing boundary.

## Action-sequence interaction

A different accepted action captures the actor's real queue length into the mutable revision-240 `field1476` equivalent (`path_backlog_at_assignment`, retained historical Rust name).

- active action + non-zero delay does not apply the ordinary path gate yet;
- delay zero + positive remainder uses `movement_gate_with_backlog`;
- delay zero + zero remainder uses `movement_gate_without_backlog`;
- gate value 0 blocks ordinary progress and accrues catch-up debt;
- real target consumption decrements the positive remainder.

Resolved `SequenceTiming` is retained separately from `SequencePolicy`. It provides legacy frame delays/rewind or cached-model classification for authoritative forced movement. The active timing resolution and data participate in fingerprint v22 because they change future movement.

## Projection boundary

`crates/scene/src/authoritative_motion.rs` fronts the older disposable pose/turn projector.

- all player translation, ordinary or forced, comes from GameState fine position;
- forced NPC translation comes from GameState fine position even when ordinary NPC acceleration policy is absent;
- cache-resolved ordinary NPC translation also comes from GameState;
- only deliberately unresolved ordinary NPC fixtures can use the legacy position fallback.

The legacy scene implementation may still own disposable pose/turn state. It is no longer the connected actor-position authority.

## Remaining movement gaps

This checkpoint does **not** claim parity for:

- separate desired/current actor orientation and target-following state;
- turn rate and turn-in-place semantics;
- turning slowdown before backlog/catch-up acceleration;
- directional walk/run/crawl sequence selection;
- scene-bound safety reset behavior beyond modeled explicit hard resets;
- complete cached-model action playback/completion semantics.

The next smallest movement checkpoint should move facing/turning into GameState, because position ownership for both ordinary and forced movement is now closed.
