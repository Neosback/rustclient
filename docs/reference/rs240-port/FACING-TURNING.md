# Revision 240 actor facing and turning

Status: desired/current orientation is now split authoritatively in `GameState`, including the exact forced-approach versus interpolation behavior. The ordinary smooth-turn mutator, full facing-target state, turning slowdown and directional locomotion are still not implemented and must not be guessed.

This document narrows the facing/turning work that remains after authoritative ordinary and forced actor translation. It supersedes the older ambiguity in `MOVEMENT-FACING.md` about whether the client keeps one orientation value or two.

## Primary exact-240 evidence

- `references/rl-suite-240/sources-semantic/Actor.java`
  - `Actor.method3291`: per-cycle actor ordering
  - `Actor.method3284`: forced movement, directional locomotion selection and turning slowdown
  - `Actor.method3208` / `method3207`: direction to oldest queued path target
  - `Actor.getOrientation()`: desired orientation surface
  - `Actor.getCurrentOrientation()`: current orientation surface
  - `Actor.method3163` / `method3307`: active entity-target interaction predicate used by the slowdown rule
- `references/rl-suite-240/sources-semantic/classEB.java`
  - `classEB.method3820`: per-cycle desired-facing resolver
- `references/rl-suite-240/sources-semantic/classBZ.java`
  - `classBZ.method1384`: facing update state decoder
- `references/rl-suite-240/sources-semantic/classLU.java`
  - primary facing payload selector mapping
- `references/rl-suite-240/sources-semantic/classLV.java`
  - secondary facing behavior selector
- `references/rl-suite-240/sources-semantic/classLJ.java`
  - facing target-kind mapping
- `references/rl-suite-240/sources-semantic/classBT.java`
  - `classBT.method1244`: target-coordinate to 0..2047 orientation
- `references/rl-suite-240/sources-semantic/classDC.java`
  - `classDC.method2933`: player facing block decode/propagation

Confidence is **B**: the stock/injected revision-240 control flow is direct and internally consistent, but the ordinary smooth-turn mutator has not yet been located and independently named.

## 1. The actor has two orientation values

The distinction is exact and API-visible in the revision-240 source:

| Actor field | Injected API surface | Meaning established here |
| --- | --- | --- |
| `field1493` | `getOrientation()` | desired orientation selected by facing/path/forced-movement logic |
| `field1485` | `getCurrentOrientation()` | current body rotation used by locomotion direction selection |
| `field1470` | no stable semantic name yet | non-zero turn-rate/state value; constructor default decodes to `32` |
| `field1469` | no stable semantic name yet | unresolved turn-related runtime field; do not name from proximity alone |

RustClient now preserves this distinction for visible actors. Existing `PlayerEntity::orientation` / `NpcEntity::orientation` remains the desired orientation for compatibility with packet/path-facing code, and `current_orientation` stores the independent current body rotation.

The fields intentionally remain `u16` during this migration because all existing protocol validation already enforces the `0..2047` domain. A later cleanup can wrap both in `JauAngle` without changing the semantic ownership established here.

## 2. Per-cycle ordering

`Actor.method3291` orders the relevant stock work as:

```text
classEB.method3820(actor)  // resolve desired orientation
Actor.method3284(actor)    // forced/path movement and locomotion selection
Actor.method3280(actor)    // scene-bound safety reset
```

This proves that ordinary path movement chooses direction-sensitive locomotion from the desired/current orientation state **after desired facing has been resolved for that client cycle**.

The smooth mutation that advances current orientation toward desired orientation is not in `method3284` and is not the scene-bound `method3280` safety stage. Its exact owner/call position must be located before RustClient advances current orientation authoritatively.

Part 12B therefore stores current orientation but deliberately does not advance it during ordinary cycles.

## 3. Forced movement writes desired/current differently by phase

`Actor.method3284` distinguishes the two forced phases:

### Approach phase

While `forced_start_cycle >= current_cycle`:

- fine position approaches the forced start coordinate;
- catch-up debt (`field1452`) resets;
- `field1493` (desired orientation) is set to the authored forced direction;
- **`field1485` (current orientation) is not assigned in this branch.**

RustClient now matches that behavior: forced approach changes `orientation` and leaves `current_orientation` unchanged.

### Interpolation phase

While the start is in the past and `forced_end_cycle >= current_cycle`:

- the legacy action-frame gate determines whether position interpolates;
- catch-up debt resets;
- desired orientation is set to the authored forced direction;
- current orientation is then explicitly set equal to desired orientation.

The current-orientation assignment belongs to the interpolation phase itself, not only to cycles where positional interpolation is allowed. RustClient therefore snaps `current_orientation` even when the legacy action-frame gate produces a `ForcedHeld` position cycle.

So the authoritative transition is now:

```text
approach:
    desired = forced_direction
    current = unchanged

interpolation/held forced phase:
    desired = forced_direction
    current = desired
```

The exact end cycle belongs to the interpolation branch unless the zero-duration interval was already captured by the inclusive approach condition.

## 4. Desired orientation resolution

`classEB.method3820` runs every actor cycle and selects a desired angle from the active facing state.

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

and masks the result to the `0..2047` Jagex angular domain. A zero position delta produces `-1` and leaves the prior desired orientation in effect.

RustClient does not yet retain enough typed facing-source state to reproduce this resolver for moving targets. Existing packet/path updates continue writing desired orientation only; they no longer overwrite `current_orientation` as a side effect.

## 5. Facing update wire/runtime state

`classBZ.method1384(flag, buffer)` decodes three independent fields from the facing control byte:

- bits `0..2`: secondary behavior (`classLV`);
- bits `3..5`: primary payload mode (`classLU`);
- bit `6`: boolean control flag.

The primary selector mapping is exact:

| Encoded selector | `classLU` | Payload kind | Meaning |
| ---: | --- | ---: | --- |
| 0 | `field4223` | 3 | entity target |
| 1 | `field4224` | 2 | coordinate/area target |
| 2 | `field4226` | 0 | explicit angle |
| 3 | `field4225` | 1 | no primary payload |

### Entity target

The payload retains a `classLJ` target kind, target/index value and an auxiliary smart value.

`classLJ` maps:

| Value | Meaning |
| ---: | --- |
| 0 | none |
| 1 | NPC collection |
| 2 | player collection |
| 3 | world-entity collection |

`Actor.isInteracting()` is true specifically for the player and NPC target kinds while the primary facing mode is entity-target.

That target identity is live state, not a one-time angle: `classEB.method3820` resolves the target's current position every client cycle.

### Coordinate/area target

The payload retains two smart coordinates plus packed nibble extents. `classEB.method3820` resolves the target area's center through the relevant `WorldView` before calculating desired orientation.

### Explicit angle

The payload retains one smart angle. This is the only primary mode RustClient currently preserves through its narrow `Option<u16>` facing representation.

### No primary payload

No primary payload follows. Secondary behavior/control state still participates in facing lifecycle and must not be silently replaced with a guessed angle.

## 6. Control-flag snap behavior

`classEB.method3820` also carries a boolean result from the active facing source. When that control condition is true and the actor's turn-rate/state value (`field1470`) is positive, the source assigns:

```text
current_orientation = desired_orientation
```

This is a separate snap path from ordinary smooth turning.

RustClient now has the state shape required to express this precisely, but the facing control/source model itself is not yet retained, so this packet-driven snap path remains deferred.

It is important not to model every desired-orientation change as an immediate current-orientation change merely because this explicit snap case exists.

## 7. Path-derived orientation and directional locomotion

For an ordinary queued target, `Actor.method3284` computes the wrapped signed angular difference:

```text
delta = (desired - current) & 2047
if delta > 1024:
    delta -= 2048
```

Then it chooses the authored walk-direction sequence:

| Signed delta | Sequence |
| --- | --- |
| `[-256, 256]` | forward walk (`field1443`, `getWalkAnimation`) |
| `[256, 768)` | rotate-right walk (`field1446`, `getWalkRotateRight`) |
| `[-768, -256]` | rotate-left walk (`field1448`, `getWalkRotateLeft`) |
| remaining sector | 180/back walk (`field1460`, `getWalkRotate180`) |

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

Missing run/crawl variants leave the already-selected walk-direction sequence in place.

## 8. Turning slowdown ordering

For the standard acceleration branch (`NPCComposition.field5325 == true`, and players), the initial ordinary movement step is reduced from 4 to 2 when all of the following are true:

```text
desired_orientation != current_orientation
AND actor is not in active entity-target interaction mode
AND field1470 != 0
```

`Actor.method3307()` delegates to `method3163()`. The latter identifies active entity-target facing state: primary entity-target mode, non-none target kind, and a valid retained target/index value.

The slowdown occurs **before** backlog and catch-up acceleration:

```text
base = 4
turning slowdown may set base = 2
backlog may raise base = 6 or 8
catch-up debt with backlog > 1 may force base = 8
run/crawl traversal scaling applies last
```

The alternate NPC acceleration branch does not run this turning-slowdown test; it immediately applies its earlier backlog thresholds.

RustClient now has independent desired/current orientation values, so this comparison no longer requires another state-model rewrite. The slowdown is still deferred until the exact turn-rate/state mutator and entity-target interaction state are retained.

## 9. Turn rate default is exactly 32

The `Actor` constructor stores obfuscated `field1470 = -853380256`. Applying the field's read multiplier used by the source (`-909895021`) produces the semantic value `32` in Java 32-bit arithmetic.

That establishes the constructor default only. It does **not** establish all mutation rules for the field, so RustClient should not yet expose a fully named `turn_rate` policy API until the remaining writer/advancement methods are traced.

## 10. RustClient state after Part 12B

Visible `PlayerEntity` and `NpcEntity` now retain:

```text
orientation          // desired orientation
current_orientation  // current body rotation
```

Lifecycle rules in this checkpoint are deliberately small and exact:

- local-player placement initializes both to the same value;
- a remote player entering visibility initializes current from the tracked desired orientation;
- a newly spawned NPC initializes both from the spawn orientation;
- ordinary movement/path direction changes desired orientation only;
- explicit facing changes desired orientation only;
- forced approach changes desired only;
- forced interpolation/held cycles set current equal to desired;
- visibility/despawn lifecycle destroys current orientation with the actor instance rather than retaining it in out-of-view tracking.

Ordinary current-orientation advancement remains disabled. This is preferable to inventing a turn-rate rule and gives later turning work a clean state boundary.

The current `Locomotion::{Idle,Walk,Run,Turn}` type remains too coarse to identify forward/back/left/right authored locomotion. Packet traversal speed alone cannot select the correct sequence.

Protocol `face(...)` also still discards entity-target, coordinate-target, secondary behavior and control-flag state. That prevents moving-target facing and exact interaction-aware slowdown.

## 11. Semantic ownership

The orientation split is authoritative `GameState` state, not scene state.

Protocol owns revision-240 facing selector/byte decoding. `GameState` owns desired/current orientation and will own resolved live facing source once that richer semantic type lands. Scene/renderer must eventually consume current orientation for draw rotation; it must not independently chase targets or become the smooth-turn authority.

Part 12B intentionally leaves the existing legacy scene turn projector in place as a visual fallback because authoritative ordinary current-orientation advancement is not implemented yet. Migrating draw rotation now would freeze actors at their last committed current angle during ordinary turning.

Entity targets should use actor instance identity where possible so an index reused after despawn cannot redirect an old facing target onto a different actor instance.

## 12. Fingerprint/replay consequence

Desired and current orientation can now diverge and produce different future movement/locomotion behavior. Semantic fingerprint schema **v23** therefore encodes both values for every visible player/NPC.

Outside/tracked players retain only desired orientation because they do not have live actor-body rotation while outside the visible actor lifecycle. Re-entry starts a new visible body state with current initialized from retained desired orientation.

A focused regression fixture changes only `current_orientation` and must produce a different semantic state fingerprint.

## 13. Do not implement yet

Do not implement the following from inference alone:

- per-cycle smooth current-orientation advancement;
- exact semantics of `field1469`;
- all writers/override rules for `field1470`;
- secondary `classLV` behavior names beyond their exact encoded values;
- target-disappearance cleanup details that have not yet been traced;
- directional run/crawl sequence availability for NPCs until the relevant definition fields are preserved by cache projection.

Those are compatibility-sensitive and should be pinned from the exact 240 writers/consumers first.

## 14. Next concrete checkpoint

The next small checkpoint should trace the **ordinary smooth-turn mutator** and every writer that materially changes `field1470` / the associated turn runtime state.

Only after that trace should RustClient:

1. advance `current_orientation` toward desired orientation per client cycle;
2. implement the exact control-flag snap path;
3. add turning slowdown in its proven pre-backlog position;
4. migrate scene draw rotation from the disposable turn projector to authoritative current orientation;
5. then expand the facing semantic type so player/NPC/coordinate targets can update desired orientation every cycle.

This preserves the desired/current split already implemented while keeping the remaining timing behavior evidence-backed.
