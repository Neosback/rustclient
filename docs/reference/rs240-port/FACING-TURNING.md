# Revision 240 actor facing and turning

Status: desired/current orientation and the path-scheduler interaction are specified from exact revision-240 client evidence. The ordinary smooth-turn mutator is not yet pinned and must not be guessed.

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

RustClient's current single `orientation: u16` field therefore collapses two independently observable values.

Do not "fix" this by renaming the existing field to current orientation. The current Rust field is populated from packet-facing/path intent and is already closest to revision-240 **desired orientation** semantics. The safe migration is to retain it as desired orientation and add a separate current-orientation value.

## 2. Per-cycle ordering

`Actor.method3291` orders the relevant stock work as:

```text
classEB.method3820(actor)  // resolve desired orientation
Actor.method3284(actor)    // forced/path movement and locomotion selection
Actor.method3280(actor)    // scene-bound safety reset
```

This proves that ordinary path movement chooses direction-sensitive locomotion from the desired/current orientation state **after desired facing has been resolved for that client cycle**.

The smooth mutation that advances current orientation toward desired orientation is not in `method3284` and is not the scene-bound `method3280` safety stage. Its exact owner/call position must be located before RustClient advances current orientation authoritatively.

## 3. Forced movement writes desired/current differently by phase

`Actor.method3284` distinguishes the two forced phases:

### Approach phase

While `forced_start_cycle >= current_cycle`:

- fine position approaches the forced start coordinate;
- catch-up debt (`field1452`) resets;
- `field1493` (desired orientation) is set to the authored forced direction;
- **`field1485` (current orientation) is not assigned in this branch.**

This is a concrete correction to the temporary RustClient Part 11B representation, which currently has only one orientation field and therefore effectively forces both concepts during approach.

### Interpolation phase

While the start is in the past and `forced_end_cycle >= current_cycle`:

- the legacy action-frame gate determines whether position interpolates;
- catch-up debt resets;
- desired orientation is set to the authored forced direction;
- current orientation is then explicitly set equal to desired orientation.

So the eventual Rust transition must be:

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

RustClient must preserve this ordering. Adding a generic `if turning { speed /= 2 }` after backlog/run scaling would be wrong.

## 9. Turn rate default is exactly 32

The `Actor` constructor stores obfuscated `field1470 = -853380256`. Applying the field's read multiplier used by the source (`-909895021`) produces the semantic value `32` in Java 32-bit arithmetic.

That establishes the constructor default only. It does **not** establish all mutation rules for the field, so RustClient should not yet expose a fully named `turn_rate` policy API until the remaining writer/advancement methods are traced.

## 10. Current RustClient mismatch after Part 11B

RustClient currently has one `orientation: u16` on `PlayerEntity` and `NpcEntity`.

That one value is used for multiple incompatible roles:

- packet/path desired direction;
- explicit-facing result;
- forced-movement direction;
- scene/pose orientation assumptions.

Part 11B also writes the forced direction before checking whether the actor is in the approach or interpolation phase. With one field that was the only available representation, but exact source evidence now proves the future split must preserve current orientation through the approach phase and snap it only in the interpolation phase.

The current `Locomotion::{Idle,Walk,Run,Turn}` type is also too coarse to identify forward/back/left/right authored locomotion. Packet traversal speed alone cannot select the correct sequence.

Finally, protocol `face(...)` still discards entity-target, coordinate-target, secondary behavior and control-flag state. That prevents moving-target facing and exact interaction-aware slowdown.

## 11. Required implementation boundary

The next implementation checkpoint should introduce a revision-neutral live orientation state without copying `classBZ` directly.

At minimum, visible actors need:

```text
desired_orientation: JauAngle
current_orientation: JauAngle
facing source/state
  none/path-derived
  explicit angle
  coordinate/area target
  entity target with lifecycle-safe identity
secondary facing behavior (provisional typed enum)
control/snap flag
turn-rate/state value once the mutator trace is complete
```

The existing Rust `orientation` field should be treated as the desired orientation during migration for compatibility with its current packet-facing role; add current orientation separately rather than silently changing the meaning of existing readers.

Protocol owns the revision-240 selector/byte decoding. `GameState` owns the resolved live facing source and both orientation values. Scene/renderer consume current orientation for draw rotation; they do not independently chase targets or rotate actors.

Entity targets should use actor instance identity where possible so an index reused after despawn cannot redirect an old facing target onto a different actor.

## 12. Do not implement yet

Do not implement the following from inference alone:

- per-cycle smooth current-orientation advancement;
- exact semantics of `field1469`;
- all writers/override rules for `field1470`;
- secondary `classLV` behavior names beyond their exact encoded values;
- target-disappearance cleanup details that have not yet been traced;
- directional run/crawl sequence availability for NPCs until the relevant definition fields are preserved by cache projection.

Those are compatibility-sensitive and should be pinned from the exact 240 writers/consumers first.

## 13. Next concrete checkpoint

The next small implementation should be the **desired/current orientation split only**:

1. retain the existing actor `orientation` meaning as desired orientation;
2. add current orientation initialized from desired orientation on spawn/visibility entry;
3. correct forced movement so approach changes desired only and interpolation sets both;
4. include current orientation in semantic fingerprints;
5. keep ordinary smooth turning disabled until its exact mutator is found;
6. keep the legacy scene turn projector only as a disposable visual fallback until GameState current-orientation advancement is authoritative.

After that, trace and implement the smooth-turn mutator, then wire the exact turning slowdown and directional locomotion policy. This keeps each semantic transition evidence-backed and avoids another orientation rewrite.
