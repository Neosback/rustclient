# Actor runtime: revision 240 source map

Status: active navigation document.

This document connects the already-audited revision-240 player/NPC wire behavior to the remaining shared actor runtime work. It does not duplicate the exact transform tables in `docs/plan/PLAYER-UPDATING-240.md` or `docs/plan/NPC-ANIMATION-240.md`.

## Why this runtime slice matters

Player and NPC packet framing is substantially understood. The larger parity risk now sits downstream: committed movement, facing, sequence and effect state must produce the same cycle-by-cycle actor behavior as the 240 client.

A decoder can be byte-perfect while visible movement is still wrong if the actor scheduler chooses the wrong locomotion sequence, restarts an action incorrectly, blocks path progress on the wrong frame, turns at the wrong rate, or completes exact movement at a different cycle. Treat packet decode and actor scheduling as separate contracts.

## Source roles

### `classDF.java`: player synchronization manager

High-value symbols already established by the player audit:

- `method3006`: initializes the local player from a 30-bit value and all other tracked players from 18-bit outside-view state.
- `method3015`: performs the four independently aligned active/inactive update passes, including skip runs and tracking-flag rotation.
- `method3019`: decodes skip counts using a 2-bit selector followed by 0, 5, 8 or 11 bits.
- `method3043`: extended-mask extension logic.
- `method3050`: ordered extended-information decode.
- `method3046`: deferred ordinary movement commit after extended data is known.

Rust ownership:

- bit/mask/transformed byte interpretation: `crates/protocol/src/openrune_240/player_info.rs`
- validated semantic movement/state: `crates/game-state`
- visual interpolation/pose from committed state: `crates/scene`

Do not retain a decoder-private second copy of persistent player path semantics.

### `classDC.java`: tracked player network/runtime bridge

The existing audit identifies two especially important behaviors:

- `method2961`: exact/forced movement offsets are anchored after the new network coordinate is decoded; the forced-movement end becomes the actor path target.
- `method2924`: immediate placement versus queued path movement/reset behavior. A position reset does not itself invent a new facing direction.

This class is useful evidence for lifecycle and ordering, but RustClient should not copy its mixed networking/runtime responsibility.

### `Actor.java`: shared movement scheduler

`Actor.method3284` owns the shared movement stage identified by the current audit. It covers:

- approach toward a future exact-movement start;
- interpolation during forced/exact movement;
- ordinary path consumption;
- movement blocking/catch-up interactions with action sequences;
- turning slowdown inputs;
- directional locomotion sequence selection;
- traversal-dependent run/crawl scaling.

`Actor.method3291` is the inner movement-cycle wrapper:

```text
Actor.method3297(world, actor)
    classEB.method3820(world, actor)  // desired-facing resolution
Actor.method3284(actor)               // movement + locomotion selection
Actor.method3280(world, actor)        // scene-bound safety reset
```

### `classKN.java`: outer actor-cycle and smooth turning

`classKN.method6539` is the exact ordinary turning owner that older notes had left unresolved.

Its order is:

```text
Actor.method3291(world, actor)
if !actor.animationsFrozen:
    advance current orientation toward desired orientation
    select idle left/right turn pose when required
classKB.method6491(world, actor)   // animation advancement
Actor.method3289(...)              // end-of-cycle cleanup
```

The ordinary turn algorithm uses:

- `Actor.field1493`: desired orientation;
- `Actor.field1485`: current orientation;
- `Actor.field1470`: turn rate;
- `Actor.field1469`: consecutive turn-cycle counter;
- `Actor.field1442`: idle rotate-left sequence;
- `Actor.field1455`: idle rotate-right sequence;
- `Actor.field1443`: walk fallback.

The wrapped delta is `(desired - current) & 2047`. Delta above 1024 turns negatively; otherwise positively. Current orientation advances by exactly `turnRate` in that direction and snaps to desired when the remaining wrapped angle is within one step. The result is masked back into `0..2047`.

When the current pose is idle, turn-rate is positive, and either the turn lasts more than 25 cycles or the current step does not snap directly to target, the source prefers the matching idle rotate-left/right sequence and falls back to ordinary walk if that authored turn sequence is unavailable.

### `classEB.java`: desired-facing resolver

`classEB.method3820` resolves the actor's desired orientation before movement each cycle.

Sources include:

- explicit angle;
- coordinate/area target;
- NPC target;
- player target;
- world-entity target;
- oldest queued path heading;
- prior desired orientation as fallback.

The packet-facing state itself lives in `classBZ`; packet-time intent and client-cycle orientation mutation are separate phases.

### `classBT.java`: target-to-angle helper

`classBT.method1244` converts actor-to-target coordinates, including cross-world-view transforms, to the 0..2047 orientation domain using `atan2`. Coincident positions return `-1`.

### `classGA.java` / `NPCComposition.java`: NPC turn-rate policy

`classGA.method5325` copies `NPCComposition.field5297` into `Actor.field1470`.

In the exact 240 NPC definition decoder:

- the temporary value defaults to `32`;
- opcode `103` replaces it with an unsigned-short value;
- the final value is stored in `field5297`.

So NPC turn rate is definition-authored cache policy, not a universal hard-coded constant.

### `classCU.java`: action assignment policy

`classCU.method2672` covers same-sequence reassignment and action priority behavior.

Required concepts already established:

- assigning the same action ID is not always a no-op;
- restart mode 1 restarts playback/delay state;
- restart mode 2 resets loop-count-related state without the same full restart;
- action replacement is subject to authored priority arbitration.

These are definition-driven semantic policies and should enter the actor semantic gateway using decoded sequence metadata.

### `Animation.java`: sequence definition inputs

The semantic `Animation` class is the API-derived sequence definition class in this 240 artifact. The actor scheduler needs authored policy/timing data; geometry posing belongs in scene/cache preparation.

Keep separate:

1. sequence-definition fields;
2. authoritative actor playback state;
3. projection/pose state.

## Required actor semantic state

### Position/path

- authoritative tile/plane;
- queued path steps and traversal mode per step;
- persistent/default traversal mode where required;
- immediate placement/reset serial or equivalent lifecycle signal;
- exact/forced trajectory with start/end coordinates and cycle bounds;
- path delay/catch-up counters.

### Facing/turning

- desired orientation;
- current orientation;
- turn rate;
- consecutive turning-cycle counter;
- entity target identity and kind;
- explicit face-coordinate/area/angle intent;
- target-disappearance cleanup state;
- packet/source-driven snap flag;
- idle turn-left/right sequence inputs.

### Locomotion

- idle sequence;
- walk forward/back/left/right sequences;
- run and crawl variants where authored;
- selected semantic locomotion sequence/mode;
- movement speed derived from traversal/path scheduling.

### Action sequence

- optional sequence ID;
- assignment/start cycle and delay;
- frame/cached-animation time state;
- iteration/loop count;
- restart mode policy;
- replacement priority policy;
- movement-blocking/catch-up state;
- completion/clear state.

### Effects and overhead state

Important remaining gaps:

- actor spot-animation slots;
- hitsplats;
- health bars/headbars;
- overhead text and color/effect/expiry state;
- temporary tint/color/texture/transparency overrides;
- freeze/reset/customization fields present in 240 masks.

## Exact cycle ordering established so far

For the movement/facing/animation path traced here, the exact order is now:

1. resolve desired facing via `classEB.method3820`;
2. run forced/ordinary movement and locomotion selection via `Actor.method3284`;
3. apply scene-bound safety reset via `Actor.method3280`;
4. if animations are not frozen, advance current orientation toward desired in `classKN.method6539`;
5. perform idle left/right turn-pose selection inside that same turn block;
6. advance actor animations through `classKB.method6491`;
7. perform actor end-of-cycle cleanup.

Do not reorder turn advancement ahead of movement. Direction-sensitive path pose selection in `Actor.method3284` sees the desired/current values as they existed before the ordinary turn step for that cycle.

Effect expiration and every plugin-visible callback ordering point are not yet fully closed by this document.

## Exact-movement invariants already established

Do not regress these while completing the scheduler:

- exact offsets are normalized after applying the newly decoded network tile;
- invalid intervals/directions/arithmetic reject the semantic update rather than partially mutating actor state;
- the trajectory end becomes the path target;
- the completed trajectory holds its end until new network movement;
- equal start/end times follow the deob's inclusive approach behavior;
- forced approach writes desired orientation only;
- forced interpolation writes desired and current orientation together;
- scene interpolation is a projection of committed semantic coordinates/cycles.

## Player versus NPC differences

Prefer one shared actor scheduler with definition/profile inputs, while preserving real differences.

Known examples:

- player appearance supplies locomotion/default sequence identity differently from NPC definitions;
- NPC definitions author directional run/crawl variants and turn rate;
- update masks and entity target encodings can differ;
- player outside-view tracking/lifecycle differs structurally from NPC synchronization;
- NPC morphs can change definition-derived animation/size/model data at runtime.

Shared orientation math, turn stepping, path traversal, exact trajectory timing and action restart policy should not have independent player/NPC implementations unless exact evidence proves a difference.

## Port boundary

### Protocol owns

- update-mask bits and extension scheme;
- transformed/endian reads;
- signed/sentinel interpretation that is revision-specific;
- player/NPC index bit widths and list synchronization encoding;
- facing selector/header payload decoding;
- exact packet field ordering.

### GameState owns

- entity lifecycle after decode;
- current tile/path/trajectory;
- desired/current orientation;
- live facing target/intent;
- turn rate and turn-cycle counter;
- ordinary smooth-turn mutation;
- action/locomotion/effect scheduler state;
- validated cycle transitions and expiration.

### Cache owns

- sequence definitions and authored policy fields;
- NPC turn-rate opcode 103;
- player/NPC definition/model dependencies;
- spot/hitsplat/headbar definitions.

### Scene owns

- deriving the pose for committed actor state at a cycle;
- legacy/cached-model animation application and blending;
- model composition/transforms and geometry preparation;
- render interpolation that does not mutate semantic truth.

### Renderer owns

GPU resources/passes/draw ordering only after scene projection has produced the required geometry/material semantics.

## Next source traces

The ordinary turn mutator is no longer an open item. Continue in this order:

1. full typed facing-target retention and target-disappearance cleanup, including the unresolved `classLV` secondary behavior;
2. remaining reachable turn-rate writers beyond constructor/default and NPC opcode 103;
3. complete cached-model action advancement/completion and remaining legacy completion/event semantics;
4. actor spot animation add/remove/advance helpers and corresponding player/NPC mask fields;
5. hitsplat/headbar add/remove/expiry helpers and replacement priority rules;
6. overhead/tint/freeze/customization mask consumers and expiry/application sites;
7. `Player`/`PlayerComposition` model assembly invalidation rules.

Each trace should update the stable-symbol research ledger before implementation work.

## Existing evidence to keep linked

- `docs/plan/PLAYER-UPDATING-240.md`
- `docs/plan/NPC-ANIMATION-240.md`
- `docs/reference/rs240-port/FACING-TURNING.md`
- `docs/reference/source-manifest.json`
- `crates/protocol/tests/fixtures/PROVENANCE.md`

Do not call actor parity complete until the remaining shared runtime behaviors are traced, implemented and independently verified.

## Verification

No `verify.sh`, tests, Clippy, CI, compilation or workspace gate was run as part of this naming/research wave.
