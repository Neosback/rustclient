# Movement, path scheduling and facing in revision 240

Status: exact core source flow identified; smooth-turn advancement and some auxiliary
facing subfields still need a final method trace before implementation.

This document follows `SEQUENCE-SCHEDULER.md`. Together they describe why a
byte-correct player/NPC update can still produce visibly incorrect movement or
animation in RustClient.

## Primary exact-240 sources

- `Actor.java`
  - `Actor.method3122`: enqueue path entry
  - `Actor.method3284`: exact movement and normal path scheduler
  - `Actor.method3280`: scene-bound reset/safety behavior
  - `Actor.method3208`: direction to oldest queued path target
- `classGT.java`: traversal mode enum carried by path entries
- `classDF.java::method3050`: player extended block invokes facing decoder
- `classDC.java::method2933`: applies the encoded facing block to tracked players
- `classBZ.java::method1384`: facing block state decoder
- `classLU.java`: encoded facing-mode mapping
- `classLJ.java`: entity-target kind mapping
- `classEB.java::method3820`: per-cycle desired-facing resolution
- `classBT.java::method1244`: coordinate-to-2048-angle calculation

Current Rust surfaces compared:

- `crates/protocol/src/openrune_240/info_fields.rs`
- `crates/protocol/src/openrune_240/player_extended.rs`
- `crates/protocol/src/openrune_240/npc_extended.rs`
- `crates/model/src/entity.rs`
- `crates/game-state/src/player_movement.rs`
- `crates/game-state/src/npcs.rs`

## 1. Stock actor path queue

`Actor` owns arrays of length 10 for queued X, queued Y, and traversal mode. The
active queue length is `field1471`.

`Actor.method3122(actor, x, y, traversal)` inserts a path request as follows:

1. if the active length is below 9, increment it;
2. shift existing entries one slot toward the tail;
3. write the new request at index 0.

The scheduler consumes `field1471 - 1`, so the oldest surviving request is the
next target. This is a bounded FIFO implemented by reverse indexing, not a single
packet endpoint.

Immediate placement/reset paths clear both the active queue length and the
sequence/path interaction counter (`field1476`) and place the actor at tile center:

```text
fine_x = tile_x * 128 + actor_size * 64
fine_y = tile_y * 128 + actor_size * 64
```

Rust does not need the same array layout, but it does need the same observable
queue semantics and capacity behavior where server updates can accumulate faster
than actor-cycle movement consumes them.

## 2. Traversal modes

`classGT` has four wire/runtime values:

| Stable symbol | Encoded byte | Established scheduler meaning |
| --- | ---: | --- |
| `classGT.field2676` | `-1` | immediate/reset placement path in player bridge code |
| `classGT.field2677` | `0` | crawl, halves scheduler step size |
| `classGT.field2678` | `1` | walk/default traversal |
| `classGT.field2679` | `2` | run, doubles scheduler step size |

This agrees with RustClient's current `MoveSpeed::{Stationary,Crawl,Walk,Run}`
profile mapping. The missing piece is not the enum value. It is the stock queue and
cycle scheduler that consumes the mode.

## 3. Exact/forced movement phases

`Actor.method3284` has three top-level movement phases based on the current client
cycle and the actor's exact-movement start/end cycles.

### Before or at exact start

When the exact start cycle is greater than or equal to the current client cycle,
the actor approaches the authored start coordinate using the remaining number of
cycles as the divisor. This produces deterministic approach motion rather than an
instant jump.

The desired orientation is set to the exact-movement direction during this phase.

### Between exact start and exact end

When start is in the past but exact end is still greater than or equal to the
current cycle, the actor interpolates between authored start and end coordinates.

For legacy action sequences, interpolation is not unconditional. The source allows
a positional interpolation update when at least one of the following is true:

- this is the exact end cycle;
- there is no active action definition;
- the action has an explicit start delay;
- the legacy action playback is at/past the next frame boundary.

Cached/Maya animation takes the permissive branch in this check.

After this phase the actor's desired and current orientation are both forced to the
exact-movement direction.

This frame-boundary coupling is why forced movement can still look wrong even when
start/end coordinates and cycle arithmetic are correct.

### After exact movement

The scheduler falls through to normal queued path consumption.

## 4. Normal path scheduling

The default locomotion sequence begins as the actor's idle sequence. If the queue
is empty, the movement-delay/catch-up counter is reset and idle is selected.

If an action is active with zero action delay, `SEQUENCE-SCHEDULER.md` documents
the two sequence-policy gates that can stop path progress. A blocked cycle:

- increments `field1452`, the scheduler's catch-up/delay counter;
- selects the current default pose;
- returns without consuming the queued path.

Once path movement is allowed, the scheduler takes the oldest queued tile and
computes its centered fine coordinate.

### Large gap snap

The source calculates the maximum absolute axis distance to the next queued target.
If that distance is greater than 288 fine units, it snaps to the queued target and
consumes the path entry instead of trying to walk across the gap.

This is a client scheduling rule, separate from whether the packet itself was a
short step or teleport-shaped update.

### Directional locomotion selection

When the target is close enough for ordinary movement, the scheduler compares:

```text
desired_orientation - current_orientation  (wrapped to signed 2048-angle space)
```

It selects authored forward/180-degree/left/right walking sequences by angular
quadrant:

- approximately forward: difference `[-256, 256]`;
- one side: `[256, 768)`;
- opposite side: `[-768, -256]`;
- remaining sector uses the 180-degree/backward sequence;
- missing directional sequence falls back to ordinary walk.

This is materially different from choosing the movement animation only from the
packet's step direction.

### Base step speed and backlog acceleration

Normal base step size is 4 fine units per actor cycle.

For actors using the scheduler's normal acceleration mode, queued backlog raises
that base step:

- path length greater than 2: 6;
- path length greater than 3: 8;
- a positive catch-up counter with more than one queued step also forces 8 and
  decrements the catch-up counter.

For the alternate NPC definition mode, the thresholds are one entry lower:

- path length greater than 1: 6;
- path length greater than 2: 8;
- the same catch-up rule applies.

When the actor is turning toward a different desired orientation, has a nonzero
turn rate, and is not in entity-target interaction mode, the normal mode initially
reduces the step to 2 before backlog rules can raise it.

Traversal then modifies the result:

- run doubles it;
- crawl halves it.

The scheduler also substitutes authored run-direction sequences when final step
size is at least 8 and authored crawl-direction sequences when final step size is
at most 2, falling back to the walk-direction sequence when variants are absent.

### Consumption

When the actor reaches the queued fine coordinate, the queue length is decremented.
If the action/path interaction remainder `field1476` is still positive, it is also
decremented.

This couples queue progress to action movement-gating policy and is why preserving
only a final authoritative tile is insufficient for complete stock scheduling.

## 5. Facing update wire model

The 240 extended facing block is richer than `Option<u16>`.

`classBZ.method1384(flag, buffer)` decodes three independent pieces from the flag:

- low 3 bits select a secondary facing behavior enum (`classLV`);
- bits 3..5 select a primary payload mode (`classLU`);
- bit 6 is a boolean control flag used by the actor facing scheduler.

`classLU` proves the encoded selector-to-payload mapping. The selected enum's
internal `field4228` drives the payload switch in `classBZ`.

### Primary selector 0: entity target

Encoded selector `0` maps to `classLU.field4223` and payload-kind `3`.
It decodes:

1. a `classLJ` target kind;
2. a variable-length target/index value;
3. an additional smart value retained with target state.

`classLJ` target kinds are:

| Value | Stable symbol | Established target source |
| ---: | --- | --- |
| 0 | `field4172` | none |
| 1 | `field4175` | NPC collection (`WorldView.field1696`) |
| 2 | `field4174` | player collection (`WorldView.field1692`) |
| 3 | `field4173` | world-entity collection |

`Actor.isInteracting()` returns true for the NPC and player target kinds.

`classEB.method3820` resolves the referenced target each cycle, calculates the
angle from actor to current target position, and uses that as desired orientation.
This means entity-facing cannot be reduced to a one-time angle at packet receipt.

### Primary selector 1: coordinate/area target

Encoded selector `1` maps to `classLU.field4224` and payload-kind `2`.
It decodes two smart coordinates plus a packed smart containing two nibble-sized
extent values.

`classEB.method3820` uses those values to determine the target area's center,
translates it through the relevant world view, and computes desired orientation.

### Primary selector 2: explicit angle

Encoded selector `2` maps to `classLU.field4226` and payload-kind `0`.
It decodes one smart value. This is the only primary mode RustClient currently
retains as `Some(angle)`.

### Primary selector 3: no primary payload

Encoded selector `3` maps to `classLU.field4225` and payload-kind `1`; it carries
no additional primary payload.

The secondary `classLV` behavior and the exact semantics of every auxiliary smart
or control flag are not yet named here. Preserve them in any intermediate semantic
representation rather than discarding them while those names are finalized.

## 6. Desired orientation calculation

`classEB.method3820` is run before `Actor.method3284` in the actor update flow via:

```text
Actor.method3291
  -> classEB.method3820   // resolve desired facing
  -> Actor.method3284     // exact/path scheduling and locomotion selection
  -> Actor.method3280     // bounds reset/safety
```

The facing routine resolves one of:

- explicit facing angle;
- coordinate/area target;
- player target;
- NPC target;
- world-entity target;
- queued-path direction when no stronger facing source applies;
- prior desired orientation as fallback.

`classBT.method1244` computes the target angle with `atan2` and maps it into the
client's 0..2047 angular space.

A control flag can snap current orientation to the newly resolved desired
orientation under the source's turn-state conditions. The remaining ordinary
smooth-turn advancement still needs its direct mutator traced before assigning a
final semantic name to `Actor.field1470`/related counters.

## 7. Bounds/reset behavior

`Actor.method3280` is not the ordinary turn scheduler. It is a safety/reset stage.

If an actor leaves the current scene's fine-coordinate bounds, it:

- clears the active action controller;
- clears exact-movement start/end cycles;
- clears actor spot animations;
- snaps fine coordinates back to queued tile 0 center;
- resets actor path/effect runtime through `method3298`.

The local player has a stricter central safe-area check in the ordinary top-level
world view and receives the same reset when outside that area.

This reset ordering is useful for lifecycle parity and prevents stale sequence or
effect state after invalid projection coordinates.

## 8. Current Rust mismatches

### A. Player movement commits packet endpoints immediately

`crates/game-state/src/player_movement.rs::apply_movement` currently:

- changes the player's authoritative tile immediately;
- derives locomotion directly from the packet traversal speed;
- derives orientation directly from the movement direction;
- has no bounded multi-entry player path queue.

That is sufficient for endpoint correctness, but not for revision-240 cycle
scheduling, directional locomotion, action path blocking, backlog acceleration or
moving-target facing.

### B. NPC path state represents only the latest request

`NpcPath` retains one one/two-step request for projection and immediately advances
the semantic NPC tile to its endpoint. It does not represent the same bounded
actor queue that `Actor.method3122`/`method3284` consume over cycles.

The current design is useful as a packet-to-projection bridge, but full stock
scheduler parity needs a shared semantic actor queue rather than independent
player endpoint logic and NPC latest-request projection logic.

### C. Facing modes are consumed and discarded

`info_fields.rs::face` correctly consumes the byte shapes for the four primary
selectors, but returns a semantic value only for selector 2:

```text
selector 0 -> consume entity-target payload -> None
selector 1 -> consume coordinate/area payload -> None
selector 2 -> return Some(angle)
selector 3 -> None
```

Both player and NPC extended decoders use this helper. Therefore the current model
cannot retain:

- player target identity;
- NPC target identity;
- world-entity target identity;
- coordinate/area facing target;
- secondary facing behavior;
- the facing control flag.

### D. `facing: Option<u16>` is too narrow

`PlayerInfoUpdate`, `RemotePlayerUpdate::Attributes`, and `NpcProperty::Facing`
represent facing as a single angle. That shape makes the discarded modes impossible
to restore later without changing the semantic contract.

### E. Explicit facing is applied as current orientation immediately

Current NPC attribute application writes `NpcProperty::Facing(angle)` directly to
`npc.orientation`. Player movement similarly writes orientation from movement.
The stock client distinguishes desired orientation from current orientation and
uses their difference to select direction-specific locomotion.

### F. Current movement sequence model is incomplete

`MovementSequences` contains idle, turn, four walk directions, and one run
sequence. The source scheduler can select directional run and crawl variants where
an actor/NPC definition supplies them. Those authored variants need to survive
definition/appearance decoding before the stock selection policy can be reproduced.

## 9. Required semantic shape

Do not solve this by moving the 240 Java arrays directly into the renderer.
The authoritative actor domain should express the behavior in revision-neutral
terms while protocol adapters retain 240 encodings.

A suitable semantic actor movement model needs at least:

```text
confirmed/network coordinate state
bounded queued path entries
  - target tile
  - traversal mode
current fine/cycle movement state or enough state to derive it deterministically
exact movement trajectory
movement catch-up/block counter
pre-action queued-path remainder
current orientation
desired orientation
facing source
  - none
  - explicit angle
  - coordinate/area target
  - actor/entity target
authored turn rate/state
locomotion sequence selection state
```

The exact split between tile/fine-coordinate state can be chosen to fit the existing
`GameState` contract, but renderer prediction must not become the only place the
queue progresses if action blocking and plugin-visible actor state depend on that
progress.

## 10. Facing semantic type recommendation

Replace raw `Option<u16>` at the semantic boundary with a typed value that can
preserve all 240 modes without exposing raw mask bits. Conceptually:

```text
FacingUpdate
  None / Clear
  Angle(JauAngle)
  AreaTarget { ...normalized coordinate/extents... }
  EntityTarget { kind, instance/index, auxiliary }
```

Keep secondary behavior/control fields typed but provisionally named until their
remaining consumers are traced. The protocol decoder should preserve them; the
semantic actor scheduler decides their meaning.

For entity targets, use lifecycle-safe identities where possible. A reused NPC or
player index must not accidentally make an old facing target point to a new actor
instance after despawn/reuse.

## 11. Implementation order implied by this trace

1. Expand immutable sequence policy as described by `SEQUENCE-SCHEDULER.md`.
2. Introduce a shared semantic actor path queue/cycle scheduler contract.
3. Change facing decode from `Option<u16>` to a typed all-mode update.
4. Retain desired orientation separately from current orientation.
5. Resolve moving entity/area targets per cycle.
6. Finish the ordinary smooth-turn mutator trace and implement authored turn rate.
7. Move directional walk/run/crawl selection into the shared semantic scheduler.
8. Keep scene interpolation as a projection of committed scheduler state, not a
   competing source of movement truth.
9. Add focused player and NPC fixtures for queue saturation, crawl/walk/run,
   action-blocked paths, explicit angle, area target, player target, NPC target,
   target disappearance and index reuse.

## 12. Verification targets

When implementation begins, focused tests should prove at least:

- path insertion caps at the stock effective depth and preserves FIFO order;
- crawl/walk/run apply the stock half/base/double step behavior;
- backlog and catch-up counters choose the stock step sizes;
- >288 fine-unit gaps snap and consume the queue entry;
- desired/current angular difference picks the correct directional locomotion;
- missing directional variants fall back correctly;
- exact movement holds/interpolates according to action frame-boundary gating;
- all four facing primary selectors round-trip into typed semantics;
- player/NPC/world-entity targets resolve each cycle and clear/fallback correctly;
- local/out-of-scene reset clears action/exact/spot state in the required order;
- replay/fingerprint state includes newly authoritative queue/facing fields.

No broad Cargo or CI gate is required for this research document itself.
