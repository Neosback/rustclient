# Actor runtime: revision 240 source map

<!-- authority-notice -->
> Domain source/ownership research; implementation statements are bounded to the dates
> recorded below. The [current matrix](PORT-MATRIX.md) and [audit](CURRENT-AUDIT.md)
> supersede older gap/status lists; preserve exact source details and historical evidence.

Status: active navigation document.

This document connects the already-audited revision-240 player/NPC wire behavior to the remaining shared actor runtime work. It does not duplicate the exact transform tables in `docs/plan/PLAYER-UPDATING-240.md` or `docs/plan/NPC-ANIMATION-240.md`.

## Why this runtime slice matters

Player and NPC packet framing is substantially understood. The larger parity risk sits downstream: committed movement, facing, sequence and effect state must produce the same cycle-by-cycle actor behavior as the 240 client.

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

The facing-writer audit also confirms that `classDC` propagates retained facing state but does not directly author `Actor.field1470` turn rate or arm `Actor.field1473`.

This class is useful evidence for lifecycle and ordering, but RustClient should not copy its mixed networking/runtime responsibility.

### `Actor.java`: shared movement scheduler

`Actor.method3284` owns the shared movement stage. It covers:

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

`Actor.field1473` remains deliberately unnamed. In `Actor` itself it is initialized/reset to `false`; the exact consumer behavior is known, but the focused actor/player/NPC plus semantic-`client.java` audit did not recover an arming assignment. That provenance gap is deferred rather than kept as an active runtime blocker.

### `classKN.java`: outer actor-cycle and smooth turning

`classKN.method6539` is the exact ordinary turning owner.

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

When desired and current orientation are already equal, `method6539` also consumes `Actor.field1473`: if the latch is set, it clears retained entity-target state and resets the latch. This proves cleanup behavior, not the writer that armed it.

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

If an entity target disappears, no fallback angle exists, and `Actor.field1473` is set, `method3820` clears the retained target and resets the latch. The focused writer audit did not find a direct arming assignment in `Actor`, `Player`, `NPC`, `classDF`, `classDC`, `classAS`, `classGA`, `classEB`, `classKN`, or the semantic top-level `client.java` exact-field results. Keep the stable symbol; do not block later actor-runtime research on this unresolved provenance.

### `classBT.java`: target-to-angle helper

`classBT.method1244` converts actor-to-target coordinates, including cross-world-view transforms, to the 0..2047 orientation domain using `atan2`. Coincident positions return `-1`.

### `classGA.java` / `NPCComposition.java`: NPC turn-rate policy

`classGA.method5325` copies `NPCComposition.field5297` into `Actor.field1470`.

In the exact 240 NPC definition decoder:

- the temporary value defaults to `32`;
- opcode `103` replaces it with an unsigned-short value;
- the final value is stored in `field5297`.

So NPC turn rate is definition-authored cache policy, not a universal hard-coded constant.

The direct actor/synchronization writer audit further establishes:

- `Actor` supplies semantic default `32`;
- `classGA.method5325` is the direct NPC runtime writer in the inspected normal path;
- `classAS.method661` calls that projection and, if the resulting rate is zero, sets current orientation to zero rather than inventing a different rate;
- `Player`, `NPC`, `classDF`, and `classDC` contain no direct turn-rate write in the inspected sources.

This is enough to prevent a duplicate packet-local turn-rate source in RustClient. A wider whole-archive write-site census should be done only if another reachable subsystem exposes a mutation outside this normal actor path.

### `classCU.java`: action assignment policy

`classCU.method2672` covers same-sequence reassignment and action priority behavior.

Exact requirements:

- same ID + restart mode `1`: reset frame/frameCycle/loopCount and reapply action delay;
- same ID + restart mode `2`: reset only loopCount;
- other same-ID restart modes leave playback state unchanged;
- different-ID replacement is rejected when new `Animation.field5119` priority is lower than the current definition's priority;
- successful replacement captures current path remainder/backlog into `Actor.field1476` for later movement/action gating.

These are definition-driven semantic policies and should enter the actor semantic gateway using decoded sequence metadata.

### `classQR.java`: active sequence controller

Cluster 07 now closes the two formerly ambiguous counters:

- `field5599` -> `loopCount`;
- `field5598` -> `unreadyCycles`.

`loopCount` increments when legacy or cached-model playback crosses the definition end and attempts to loop. It is compared against `Animation.field5122`, the opcode-8 maximum-loop value.

`unreadyCycles` is not playback age. `classRD.method9980` resets it to zero while `Animation.method8820` reports the required animation assets ready and increments it only while those assets are not ready. `classKB.method6491` uses it for the bounded idle-pose fallback path.

Legacy state is `(frame, frameCycle, loopCount)`. Cached-model state is `(frame/time, loopCount)`; cached advancement does not mutate legacy frameCycle.

### `classRD.java` / `classEJ.java`: exact playback advancement

`classRD.method9980` is the common advancement helper and implements the full legacy frame-list path. `classEJ.method3922` implements cached-model/Maya advancement.

For legacy animation:

```text
frameCycle += advanceAmount
while frameCycle > frameLengths[frame]:
    frameCycle -= frameLengths[frame]
    frame += 1

    if frame reaches end:
        loopCount += 1
        frame -= frameStep
        terminal if loopCount >= maxLoops
        terminal if loop-back frame is invalid
```

The strict `>` boundary is compatibility-sensitive.

For cached-model animation:

```text
for each effective advance cycle:
    frame/time += 1

    if frame/time reaches cached duration:
        loopCount += 1
        frame/time -= frameStep
        terminal if loopCount >= maxLoops
        terminal if loop-back time is invalid
```

Both paths return common result flags. The important actor-scheduler bits are:

- bit `1`: crossed the end / loop boundary;
- bit `2`: terminal completion;
- bit `4`: advanced at least one frame/time step;
- bit `8`: definition entered with `frameStep == -1`.

### `classKB.java`: action/pose advancement owner

`classKB.method6491` is now traced for the core action/pose lifetime path rather than remaining an open source-location question.

Its important behavior is:

1. evaluate the special action/path delay-one hold;
2. process animation-freeze start transitions and controller snapshots;
3. establish the actor frozen state;
4. when not frozen, advance the active pose controller;
5. maintain an idle fallback pose while active pose animation resources are unready;
6. process actor spot-animation controllers outside the pose/action freeze gate;
7. unless the action was held, decrement positive action delay;
8. do not advance the action on the same cycle that positive delay was consumed;
9. when not frozen and delay was already zero, advance the action controller;
10. clear the action sequence when advancement returns terminal bit `2`.

Terminal completion is slot-specific:

- pose controller: reset playback state and keep sequence assigned;
- idle fallback controller: reset playback state and keep sequence assigned;
- action controller: clear sequence.

Freeze is also not global: pose/action scheduling and ordinary body turning are suppressed, but the spot-animation loop still executes inside `classKB.method6491`.

The canonical exact detail is in `SEQUENCE-SCHEDULER.md` and cluster 07; do not duplicate a second sequence lifetime algorithm in scene/model code.

### `Animation.java`: sequence definition inputs

The semantic `Animation` class supplies immutable timing/policy data.

Scheduler-critical fields now established include:

- opcode `1`: legacy frame lengths/IDs;
- opcode `2` -> `field5132` / `frameStep`;
- opcode `5` -> `field5119` replacement priority;
- opcode `8` -> `field5122` maximum loops, default `99`;
- opcode `9` -> `field5129` preexisting-path movement gate;
- opcode `10` -> `field5117` post-remainder movement gate;
- opcode `11` -> `field5120` / restart mode;
- opcode `13` -> cached-model animation identity;
- opcode `15` -> cached-model range/duration inputs.

Keep separate:

1. immutable sequence-definition policy/timing;
2. authoritative actor playback state;
3. downstream model posing/projection state.

### `classFL.java` / `classAAV.java`: temporary model overrides

Cluster 11 establishes two independent actor/model mechanisms:

- `classFL` → `ModelColorOverride`: hue, saturation, luminance and amount applied only while the actor's half-open color window is active;
- `classAAV` → `TransparencyTransition`: cycle-based transparency sampling with persistent post-end value and the exact future-schedule current-value carry-forward branch.

Player and NPC model construction applies the temporary color payload after animation composition, then samples transparency and writes it into the already API-mapped `Model.transparency` state. These are model-projection consequences of authoritative actor state, not renderer-owned timers.

### overhead text lifetime

The seed API already names `Actor.overheadText` and `Actor.overheadCycle`. Cluster 11 closes the cycle lifetime:

- NPC simple overhead text starts at `100` cycles;
- player simple/public overhead text starts at `150` cycles;
- top-level `client.method2173` decrements positive counters after actor updates and clears the text exactly when the counter reaches zero;
- `Actor.method3279` is RuneLite `OverheadTextChanged` observability, not the state mutation.

### NPC model/chathead override selection

NPC override state is not one slot:

- `NPC.chatheadOverrides` is fed by the mask-`524288` family;
- `NPC.modelOverrides` is fed by the mask-`33554432` / mask-`1048576` families.

World-model assembly can select the local player's composition, a prepared alternate `PlayerComposition`, or the normal `NPCComposition` override path according to `NpcOverrides` flags. The remaining generic override fields stay unnamed until stronger evidence justifies narrower names.

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
- `FacingApplyPolicy` (`MOVEMENT_GATED` versus `BYPASS_MOVEMENT_GATE`);
- proven target-disappearance/alignment cleanup behavior;
- packet/source-driven snap flag;
- idle turn-left/right sequence inputs.

Do not give the unresolved cleanup latch a Rust semantic field solely from `Actor.field1473` consumers. Its arming provenance is deferred unless parity evidence later proves the state must be represented.

### Locomotion

- idle sequence;
- walk forward/back/left/right sequences;
- run and crawl variants where authored;
- selected semantic locomotion sequence/mode;
- movement speed derived from traversal/path scheduling.

### Action/sequence playback

- optional sequence ID;
- action/start delay;
- legacy `{frame, frameCycle}` or cached-model `{time}` playback position;
- loopCount;
- max-loop/restart/replacement policy from immutable definition data;
- movement-blocking/catch-up interaction state;
- unreadyCycles only where resource-fallback compatibility requires it;
- slot-specific terminal behavior: restart pose/fallback, clear action.

Do not replace these with one aggregate animation cycle counter; the source distinguishes them observably.

### Effects and overhead state

The following actor-attached effect families are now source-specified for this naming phase:

- keyed actor spot-animation slots and their add/remove/advance/repeat lifetime;
- hitsplat insertion, definition-driven replacement and absolute expiry;
- health-bar definition arbitration plus bounded timestamped update history and expiry;
- overhead text plus cycle countdown/clear behavior;
- temporary `ModelColorOverride` payload with `[startCycle, endCycle)` activation;
- `TransparencyTransition` sampling and persistent end value;
- animation-freeze window packet feeds into the canonical cluster-07 scheduler;
- separate NPC chathead and world-model override slots/model-selection behavior.

Still open here:

- final semantic names for the packed player public-chat presentation/filter fields;
- full player `ColorTextureOverride[]` / `PlayerComposition` cache invalidation and model-rebuild consequences;
- generic `NpcOverrides` fields whose behavior is known but whose narrow names are not yet justified.

## Exact cycle ordering established so far

For the movement/facing/action path traced here, the order is now:

1. resolve desired facing via `classEB.method3820`;
2. run forced/ordinary movement and locomotion selection via `Actor.method3284`;
3. apply scene-bound safety reset via `Actor.method3280`;
4. if animations are not frozen, advance current orientation toward desired in `classKN.method6539`;
5. perform idle left/right turn-pose selection inside that same turn block;
6. when already aligned, consume pending target cleanup if armed;
7. enter `classKB.method6491`;
8. evaluate action hold/freeze windows;
9. advance pose/fallback controllers when allowed;
10. process spot-animation controllers;
11. decrement or advance action according to delay/freeze state;
12. clear action on terminal completion;
13. perform actor end-of-cycle cleanup.

Do not reorder turn advancement ahead of movement. Direction-sensitive path pose selection in `Actor.method3284` sees the desired/current values as they existed before the ordinary turn step for that cycle.

Do not advance an action on the same cycle that its positive delay is decremented to zero.

For later model projection, player/NPC model construction composes animation state first, then applies the active temporary HSL color payload, then samples/writes actor transparency. Separately, the top-level client cycle decrements Player/NPC overhead counters after actor updates and clears text when the counter reaches zero.

Plugin callback ordering is only claimed where a source trace explicitly separates the injected callback from committed stock-shaped state.

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

Shared orientation math, turn stepping, path traversal, exact trajectory timing, action restart policy, legacy/cached playback advancement, finite-loop completion, hitsplat/health-bar lifetime, and common temporary actor override sampling should not have independent player/NPC implementations unless exact evidence proves a difference.

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
- live facing target/intent and apply policy;
- turn rate and turn-cycle counter;
- ordinary smooth-turn mutation;
- target cleanup behavior only to the extent its arming semantics are proven/required;
- authoritative sequence assignment, delay, playback position, loop count, and terminal lifetime;
- spot/hitsplat/health-bar scheduler state and validated cycle transitions/expiration;
- overhead text/countdown state;
- temporary color-override timing/payload and transparency-transition state;
- animation-freeze windows;
- NPC chathead/world-model override selection state.

### Cache owns

- sequence definitions and authored timing/policy fields;
- NPC turn-rate opcode 103;
- player/NPC definition/model dependencies;
- spot/hitsplat/headbar definitions;
- immutable appearance/color/texture/model inputs used by player/NPC composition.

### Scene owns

- deriving the pose for committed actor state at a cycle;
- applying legacy/cached-model animation data to geometry;
- selecting/composing the NPC model from committed override state;
- applying active actor HSL override and sampled transparency to the constructed model;
- model composition/transforms and geometry preparation;
- render interpolation that does not mutate semantic truth.

### Renderer owns

GPU resources/passes/draw ordering only after scene projection has produced the required geometry/material semantics. It does not own actor effect lifetime counters.

## Next source traces

Facing/turning, core action/pose advancement, actor spot animations, hitsplats/health bars, overhead lifetime, temporary color/transparency state, freeze mask feeds, and NPC override selection are sufficiently closed for this naming phase. `Actor.field1473` remains a deferred stable-symbol provenance gap rather than an active blocker.

Continue in this order:

1. trace `Player` / `PlayerComposition` appearance customization and exact model-cache invalidation/rebuild rules, including per-equipment `ColorTextureOverride[]` consequences;
2. trace world `GraphicsObject` and `Projectile` lifetime/trajectory/animation behavior and their zone-packet feeds;
3. return to region rebuild/base-shift and remaining scene/model P0 dependencies once the actor model invalidation boundary is closed;
4. preserve the now-specified typed facing, sequence, spot, hitsplat/health, overhead and override state during later Rust implementation, reopening generic symbols only when concrete parity evidence requires it.

Only widen the turn-rate writer census if a newly traced reachable subsystem demonstrates a direct mutation outside actor default and NPC definition projection.

Each trace should update the stable-symbol research ledger before implementation work.

## Existing evidence to keep linked

- `docs/plan/PLAYER-UPDATING-240.md`
- `docs/plan/NPC-ANIMATION-240.md`
- `docs/reference/client-240/clusters/09-spot-animation.md`
- `docs/reference/client-240/clusters/10-hitsplats-healthbars.md`
- `docs/reference/client-240/clusters/11-overhead-overrides.md`
- `docs/reference/rs240-port/FACING-TURNING.md`
- `docs/reference/rs240-port/SEQUENCE-SCHEDULER.md`
- `docs/reference/source-manifest.json`
- `crates/protocol/tests/fixtures/PROVENANCE.md`

Do not call actor parity complete until the remaining shared runtime behaviors are traced, implemented and independently verified.

## Verification

No `verify.sh`, tests, Clippy, CI, compilation, mapping regeneration, or workspace gate was run as part of this naming/research wave.
