# Revision 240 actor facing and turning

Status: ordinary smooth turns and NPC definition turn-rate projection are implemented in GameState with focused contracts (2026-10-26); desired-facing resolution is traced from exact revision-240 evidence. Rich packet facing intent is still not fully retained in RustClient, so this is a source/ownership record rather than a parity claim. The remaining `Actor.field1473` arming provenance is explicitly deferred after a focused source audit; its consumer behavior remains documented under the stable symbol.

This document narrows the facing/turning work that remains after authoritative ordinary and forced actor translation. It supersedes the older ambiguity in `MOVEMENT-FACING.md` about whether the client keeps one orientation value or two, and supersedes the earlier statement that the ordinary smooth-turn mutator had not yet been located.

## Primary exact-240 evidence

- `references/rl-suite-240/sources-semantic/Actor.java`
  - `Actor.method3291`: movement-cycle ordering wrapper
  - `Actor.method3284`: forced movement, directional locomotion selection and turning slowdown
  - `Actor.method3208` / `method3207`: direction to oldest queued path target
  - `Actor.getOrientation()`: desired orientation surface
  - `Actor.getCurrentOrientation()`: current orientation surface
  - `Actor.method3163` / `method3307`: active entity-target interaction predicate used by the slowdown rule
- `references/rl-suite-240/sources-semantic/classKN.java`
  - `classKN.method6539`: full actor-cycle wrapper and ordinary smooth-turn mutator
- `references/rl-suite-240/sources-semantic/classEB.java`
  - `classEB.method3820`: per-cycle desired-facing resolver
- `references/rl-suite-240/sources-semantic/classBZ.java`
  - `classBZ.method1384`: facing update state decoder
- `references/rl-suite-240/sources-semantic/classLU.java`
  - primary facing payload selector mapping
- `references/rl-suite-240/sources-semantic/classLV.java`
  - secondary facing apply policy
- `references/rl-suite-240/sources-semantic/classLJ.java`
  - facing target-kind mapping
- `references/rl-suite-240/sources-semantic/classBT.java`
  - `classBT.method1244`: target-coordinate to 0..2047 orientation
- `references/rl-suite-240/sources-semantic/classGA.java`
  - `classGA.method5325`: copies NPC definition turn rate into the actor
- `references/rl-suite-240/sources-semantic/NPCComposition.java`
  - opcode 103 supplies the NPC turn-rate value; constructor default is 32
- `references/rl-suite-240/sources-semantic/classDC.java`
  - `classDC.method2933`: player facing block decode/propagation
- `references/rl-suite-240/sources-semantic/classDF.java`, `classAS.java`, `Player.java`, `NPC.java`
  - checked in the writer-audit slice for direct turn-rate and pending-target-cleanup writes
- `references/rl-suite-240/sources-semantic/client.java`
  - exact-field audit of the semantic top-level dispatcher found `field1473` only in consumer/clear-to-false paths and no `field1473 = true` assignment

Confidence is **B**: the stock/injected revision-240 control flow is direct and internally consistent. Independent exact-wire confirmation is still required where packet selector/sentinel details matter.

## 1. The actor has two orientation values

The distinction is exact and API-visible in the revision-240 source:

| Actor field | Injected API surface | Meaning established here |
| --- | --- | --- |
| `field1493` | `getOrientation()` | desired orientation selected by facing/path/forced-movement logic |
| `field1485` | `getCurrentOrientation()` | current body rotation advanced by `classKN.method6539` |
| `field1470` | none | turn rate; constructor default is 32 and NPC definitions override it from opcode 103 |
| `field1469` | none | consecutive turning-cycle counter used by idle turn-pose selection |

RustClient preserves the desired/current distinction for visible actors. Existing `PlayerEntity::orientation` / `NpcEntity::orientation` remains the desired orientation for compatibility with packet/path-facing code, and `current_orientation` stores the independent current body rotation.

The values remain in the `0..2047` Jagex angular domain. A later cleanup may wrap both in a dedicated angle type without changing the semantic ownership established here.

## 2. Exact per-cycle ordering

`classKN.method6539(WorldView, Actor, ...)` is the outer actor-cycle routine for the behavior traced here.

It performs:

```text
Actor.method3291(world, actor)
    Actor.method3297(world, actor)
        classEB.method3820(world, actor)  // resolve desired orientation
    Actor.method3284(actor)               // forced/path movement + locomotion selection
    Actor.method3280(world, actor)        // scene-bound safety reset

if !actor.animationsFrozen:
    smooth-turn current orientation toward desired orientation
    possibly switch idle pose to turn-left / turn-right / walk fallback

classKB.method6491(world, actor)           // advance actor animations
Actor.method3289(world, actor, ...)        // clear/update end-of-cycle actor state
```

This ordering matters. Desired facing is resolved before path locomotion selection. Smooth current-orientation mutation happens after movement/safety-reset work, but before animation advancement.

## 3. Ordinary smooth-turn algorithm

When animations are not frozen, `classKN.method6539` computes:

```text
delta = (desired_orientation - current_orientation) & 2047
```

If `delta == 0`:

- the turning-cycle counter is reset to zero;
- pending target-cleanup state may clear the retained facing target;
- no ordinary turn step is applied.

If `delta != 0`:

1. increment the consecutive turning-cycle counter;
2. choose direction:
   - `delta > 1024` -> rotate negatively;
   - otherwise -> rotate positively;
3. advance current orientation by exactly `turn_rate * direction`;
4. if the remaining wrapped delta is within one turn-rate step of the target, snap current orientation exactly to desired orientation;
5. mask current orientation back into `0..2047`.

The threshold check is wrap-aware. Do not replace it with an unwrapped absolute-difference test.

### Turn-pose selection

When `turn_rate > 0`, the actor can switch pose while turning. If the current pose is the idle pose and either:

- the actor has been turning for more than 25 consecutive cycles, or
- the current turn step did not snap directly to the target,

then the source prefers:

- negative turn direction -> `idleRotateLeft` when present;
- positive turn direction -> `idleRotateRight` when present;
- otherwise -> ordinary walk animation as fallback.

The existing cluster-03 readable names `turnCycles`, `turnRate` and `updateActorTurn` are therefore evidence-backed, not provisional guesses.

## 4. Turn-rate sources

`Actor.field1470` has constructor semantic default `32`.

For NPCs, `classGA.method5325` copies `NPCComposition.field5297` into the actor. In the exact revision-240 NPC definition decoder:

```text
var34 = 32        // definition decode default
opcode 103:
    var34 = readUnsignedShort()
...
field5297 = var34
```

So NPC turn rate is definition-authored and opcode-103-backed. RustClient should retain it in cache/definition data rather than hard-code 32 for every NPC.

### Writer audit in the actor/synchronization corpus

This slice checked the direct actor, player, NPC, player-info and NPC-info paths rather than inferring writers from the field name:

- `Actor` initializes `field1470` to semantic `32` and otherwise consumes it in movement/turning logic.
- `classGA.method5325` is the direct NPC runtime writer found in the selected corpus; it projects `NPCComposition.field5297` into `Actor.field1470`.
- `classAS.method661` calls `classGA.method5325` for NPCs and then has a special zero-turn-rate consequence: if the resulting turn rate is `0`, current orientation is set to `0`. It does not independently author the turn-rate value.
- `Player`, `NPC`, `classDF`, and `classDC` contain no direct `field1470` write in the inspected exact-240 sources.

That closes the normal player/NPC synchronization path without pretending to be a whole-archive bytecode reference census. If another subsystem later exposes a reachable direct writer, add it to this record rather than weakening the established constructor/NPC-definition ownership.

For the Rust port, the safe current model is therefore: actor default policy supplies `32`; NPC definition projection may replace it, including with `0`; no packet-local player/NPC movement decoder should invent a second turn-rate value.

## 5. Forced movement writes desired/current differently by phase

`Actor.method3284` distinguishes the two forced phases.

### Approach phase

While `forced_start_cycle >= current_cycle`:

- fine position approaches the forced start coordinate;
- catch-up debt resets;
- desired orientation is set to the authored forced direction;
- current orientation is not assigned in this branch.

### Interpolation phase

While the start is in the past and `forced_end_cycle >= current_cycle`:

- the legacy action-frame gate determines whether position interpolates;
- catch-up debt resets;
- desired orientation is set to the authored forced direction;
- current orientation is explicitly set equal to desired orientation.

So the authoritative transition is:

```text
approach:
    desired = forced_direction
    current = unchanged

interpolation/held forced phase:
    desired = forced_direction
    current = desired
```

## 6. Desired orientation resolution

`classEB.method3820` runs during each actor cycle and selects a desired angle from the active facing state.

The resolver can obtain desired orientation from:

1. explicit angle state;
2. coordinate/area target state;
3. NPC target;
4. player target;
5. world-entity target;
6. oldest queued path direction when the actor has path work and the facing policy allows path-derived facing;
7. prior desired orientation when no stronger source produces a new angle.

For coordinate/entity targets, `classBT.method1244` converts the actor/target position delta with:

```text
atan2(delta_x, delta_y) * 325.94932345220167
```

and masks the result to the `0..2047` domain. A zero position delta produces `-1` and leaves the prior desired orientation in effect.

RustClient still does not retain enough typed facing-source state to reproduce every moving-target case.

## 7. Facing update wire/runtime state

`classBZ.method1384(flag, buffer)` decodes three independent fields from the facing control byte:

- bits `0..2`: `FacingApplyPolicy` (`classLV`);
- bits `3..5`: primary payload mode (`classLU`);
- bit `6`: boolean control flag.

The primary selector mapping is exact:

| Encoded selector | `classLU` | Payload kind | Meaning |
| ---: | --- | ---: | --- |
| 0 | `field4223` | 3 | entity target |
| 1 | `field4224` | 2 | coordinate/area target |
| 2 | `field4226` | 0 | explicit angle |
| 3 | `field4225` | 1 | no primary payload |

The low-three-bit `classLV` values are behaviorally established as:

- `field4233`: `BYPASS_MOVEMENT_GATE`;
- `field4232`: `MOVEMENT_GATED`.

For explicit-angle and area facing, `field4233` bypasses the path-movement eligibility gate. The gated policy can defer facing while movement is active; explicit-angle facing has the additional catch-up-cycle exception documented in cluster 08.

### Entity target

The payload retains a `classLJ` target kind, target/index value and auxiliary smart value.

`classLJ` maps:

| Value | Meaning |
| ---: | --- |
| 0 | none |
| 1 | NPC collection |
| 2 | player collection |
| 3 | world-entity collection |

`Actor.isInteracting()` is true specifically for player and NPC target kinds while the primary facing mode is entity-target.

That identity is live state, not a one-time angle: `classEB.method3820` resolves the target's current position each client cycle.

### Coordinate/area target

The payload retains two smart coordinates plus packed nibble extents. `classEB.method3820` resolves the target area's center through the relevant `WorldView` before calculating desired orientation.

### Explicit angle

The payload retains one smart angle.

### No primary payload

No primary payload follows. Secondary policy/control state still participates in facing lifecycle and must not be replaced with a guessed angle.

## 8. Control-flag snap behavior

`classEB.method3820` carries the facing-state boolean through the active source. When that control condition is true and `turn_rate > 0`, it can assign:

```text
current_orientation = desired_orientation
```

This packet/source-driven snap occurs before the ordinary `classKN.method6539` turn step because desired-facing resolution is inside `Actor.method3291` at the head of the outer routine.

Do not model every desired-orientation change as an immediate current-orientation change merely because this explicit snap case exists.

## 9. Pending target-cleanup latch

`Actor.field1473` remains intentionally unnamed. The consumer behavior is exact:

- constructor/reset paths initialize or restore it to `false`;
- `classEB.method3820` checks it when an entity target can no longer be resolved and no fallback angle is available; when set, retained entity-target state is cleared and the flag is reset;
- `classKN.method6539` checks it once current orientation equals desired orientation; when set, retained target state is cleared and the flag is reset;
- `Actor.method3248` delegates the target reset to `classBZ.method1388`.

The focused writer audit did not find an arming assignment in the normal actor/player/NPC synchronization path. It also inspected the semantic `client.java` dispatcher by exact field name: every located `field1473` assignment there was `false`, paired with the already-known cleanup consumers; no `field1473 = true` assignment was found.

The injected interaction-change mechanism is a different state path. `Actor.method3285` sets `Actor.field1481` pending event flags, and `Actor.method3224` invokes that event hook before copying facing state. That does not establish an arming source for `field1473`.

Therefore the safe conclusion is deliberately limited: the latch has proven cleanup consumers, but its arming provenance is not recovered from the inspected semantic source path. This does **not** prove the writer does not exist elsewhere, nor classify the missing writer as stock, injected, indirect, or decompiler-lost. Keep the stable symbol and do not add a guessed Rust boolean. Re-open the census only if implementation/parity evidence makes this behavior observable and necessary.

## 10. Path-derived orientation and directional locomotion

For an ordinary queued target, `Actor.method3284` computes the wrapped signed angular difference:

```text
delta = (desired - current) & 2047
if delta > 1024:
    delta -= 2048
```

Then it chooses the authored walk-direction sequence:

| Signed delta | Sequence |
| --- | --- |
| `[-256, 256]` | forward walk (`field1443`) |
| `[256, 768)` | rotate-right walk (`field1446`) |
| `[-768, -256]` | rotate-left walk (`field1448`) |
| remaining sector | 180/back walk (`field1460`) |

If the selected directional walk sequence is absent (`-1`), the source falls back to ordinary forward walk.

This selection happens before traversal-specific run/crawl sequence substitution.

### Run variants

After final movement step size is known, a step of at least 8 can replace the selected walk-direction sequence with the matching authored run variant when present:

- forward: `field1447`;
- back/180: `field1441`;
- left: `field1449`;
- right: `field1450`.

### Crawl variants

A final step of at most 2 can replace the walk-direction sequence with the matching crawl variant when present:

- forward: `field1451`;
- back/180: `field1444`;
- left: `field1453`;
- right: `field1454`.

## 11. Turning slowdown ordering

For the standard acceleration branch (`NPCComposition.field5325 == true`, and players), the initial ordinary movement step is reduced from 4 to 2 when all of the following are true:

```text
desired_orientation != current_orientation
AND actor is not in active entity-target interaction mode
AND turn_rate != 0
```

The slowdown occurs before backlog and catch-up acceleration:

```text
base = 4
turning slowdown may set base = 2
backlog may raise base = 6 or 8
catch-up debt with backlog > 1 may force base = 8
run/crawl traversal scaling applies last
```

The alternate NPC acceleration branch does not run this turning-slowdown test.

## 12. Freeze behavior

`classKN.method6539` skips the ordinary smooth-turn block when `Actor.field1466` (`animationsFrozen` in cluster 03) is active. It still advances the later actor-animation routine and performs end-of-cycle cleanup.

That means a Rust implementation must not advance current orientation from the ordinary turn rate during the frozen state merely because desired orientation changed earlier in the cycle.

## 13. Semantic ownership

Protocol owns revision-240 facing selector/byte decoding.

`GameState` owns:

- desired orientation;
- current orientation;
- turn rate and turn-cycle counter;
- resolved live facing source/target identity;
- per-cycle smooth-turn advancement;
- proven target cleanup behavior when/if the arming state is represented.

Cache owns definition-authored NPC turn rate and movement sequence IDs.

Scene/renderer consumes committed current orientation for projection. It must not independently chase targets or become the authoritative smooth-turn scheduler.

Entity targets should use actor instance identity where possible so an index reused after despawn cannot redirect an old facing target onto a different actor instance.

## 14. Remaining compatibility work

The ordinary turn mutator, facing apply policy, and normal NPC turn-rate source are no longer open research items. Remaining facing work is narrower:

1. retain full typed player/NPC/area/entity facing intent instead of collapsing it to an optional angle;
2. preserve the established `FacingApplyPolicy` separately from `FacingMode`;
3. route directional idle-turn/walk/run/crawl sequence availability through cache-derived policy;
4. complete movement-sequence lifecycle/pacing and live acceptance now that committed body orientation reaches the scene boundary;
5. keep `Actor.field1473` as an unresolved stable-symbol compatibility note rather than blocking broader actor-runtime research; re-open only if observable parity requires it;
6. perform a wider turn-rate write-site census only if another reachable subsystem proves it can mutate actor turn rate outside constructor/default and NPC definition projection.

Do not re-open the desired/current split or invent a second turning algorithm. The exact ordinary step owner is `classKN.method6539`. The next shared actor-runtime research priority is action-sequence advancement/completion, followed by actor spot-animation lifecycle.

## Historical source-research verification

The original research wave recorded source evidence only. No `verify.sh`, tests, Clippy, CI, compilation or workspace gate was run as part of this naming/research wave.

## Rust implementation checkpoint, 2026-10-26

`game-state::ActorTurning` now owns the ordinary body turn, signed wrapping cycle counter,
pre-turn movement orientation and idle turn request. `WorldEntitiesState::advance_cycles` runs
this after movement and before action playback. Existing entity deltas and semantic replay carry
these results; fingerprint schema 27 includes every new field. Application enriches player updates with the profile adapter's 32-unit actor initialization.
NPCs and players retain explicit unresolved rates until enrichment arrives; GameState does not
guess a revision default.

The cache adapter retains opcode 103 (default 32, full unsigned-short range). Application
`SequencePolicyResolver` attaches `NpcMovementPolicy` to the existing NPC update, replacing the
old acceleration-only table. `classAS.method661` spawn handling clears current rotation when rate
is zero; its definition-change branch only calls `classGA.method5325` and does not apply that reset.
Rust preserves that difference and clears stale policy on an unresolved definition replacement.

`scene::authoritative_motion` supplies committed rotation for players and resolved NPCs, including
on projection reconstruction. Its snapshot position history now checks actor instance identity.
The scene's old movement/turn scheduler remains an explicit unresolved-fixture fallback; connected
actors do not run it. Disposable movement-animation pacing/sequence state is still partial.

Contract evidence: `game-state/src/actor_turning.rs`, `game-state/tests/actor_turning.rs`,
`cache/tests/npc_animations.rs`, `scene/src/authoritative_motion.rs`. Boundary coverage includes
1024 tie direction, strict snap equality, wraparound, zero/large rates, signed counter wrap,
forced approach/interpolation ordering, rate replacement, atomic rejection, replay, cycle partition
and actor index reuse. Rich facing targets, freeze flags, target-interaction slowdown, safety resets
and complete movement sequence lifetime remain incomplete. No new logged-in visual acceptance is
claimed by these tests. The source-only Verification section above describes the historical research
wave, not this implementation checkpoint.

Implementation verification (Rust 1.99.0): formatting check, workspace/all-target check,
strict workspace/all-target Clippy, workspace tests (526 passed, zero failed, one ignored),
web Wasm check and native binary build all passed. The ignored packet-capture test requires
`RUSTCLIENT_PACKET_REPLAY` and `RUSTCLIENT_PACKET_LOCAL`; no new live capture was substituted.
Local logs are `target/actor-turn-{fmt,workspace-check,clippy,workspace-tests,wasm,native}.log`.
Port-matrix validation passed for 42 domains and all 13 pinned TypeScript files; the symbol-ledger
check passed for 136 rows. Selected classKN/classGA source hashes were added to the source
manifest; reference trees were unchanged. Fixed/classic/modern logged-in visual acceptance,
end-to-end input latency and comparison against a live reference actor remain pending.
