# 07 animation state

Status: `specified` for revision-240 assignment, controller playback counters, legacy frame advancement, cached-model advancement, finite loop completion, action delay, and freeze-window interaction. Spot-animation lifetime is only partially described here and remains the next separate effect trace.

Rust owners: `game-state` for sequence-controller and actor scheduling state; `cache` for immutable `Animation` definition metadata; `scene` only for posing committed semantic playback state. Script/UI code can request sequence changes, but it should not own sequence timing.

## Family

`classQR` is the runtime controller for one active sequence. Revision 240 gives it a sequence id, cached `Animation` definition, legacy frame/frame-cycle state, a completed-loop counter, an animation-resource wait counter, Actor linkage, and set/clear/copy/reset helpers.

`classQH` is the time window used by Actor's already-mapped `animationFreezes` list. `classKB.method6491` is the per-cycle actor animation scheduler. `classCU.method2672` is the Player-specific action-sequence assignment/restart helper. `classQA.method9477` is the `Animation` definition cache lookup.

The actual controller advancement math is delegated from `classKB.method6491` to two mixed-purpose helpers owned outside this mapping shard:

- `classRD.method9980`: common controller advancement plus the full legacy-frame path;
- `classEJ.method3922`: cached-model animation advancement selected by `classRD.method9980`.

Those stable symbols remain citations rather than new mappings in this shard.

Observed external referrers relevant to the newly named `classQR` members include `classKB`, `classCU`, `classRD`, and `classEJ`.

## Old to new

| Stable symbol | `@ObfuscatedName` | Readable name | Tier | Confidence |
| --- | --- | --- | --- | --- |
| `classQR` | `qr` | `AnimationSequence` | T3 | B |
| `classQR.field5604` | `to` | `actor` | T4 | A |
| `classQR.field5596` | `af` | `sequenceId` | T4 | A |
| `classQR.field5597` | `ae` | `definition` | T4 | A |
| `classQR.field5600` | `ag` | `frame` | T4 | A |
| `classQR.field5602` | `ab` | `frameCycle` | T4 | A |
| `classQR.field5599` | `as` | `loopCount` | T4 | A |
| `classQR.field5598` | `ax` | `unreadyCycles` | T4 | A |
| `classQR.method9770` | `au` | `setSequence` | T4 | A |
| `classQR.method9765` | `dc` | `clearSequence` | T4 | A |
| `classQR.method9775` | `ag` | `getDefinition` | T4 | A |
| `classQR.method9772` | `bz` | `hasDefinition` | T4 | A |
| `classQR.method9799` | `aj` | `getUnreadyCycles` | T4 | A |
| `classQR.method9784` | `aa` | `getLoopCount` | T4 | A |
| `classQR.method9791` | `ao` | `resetPlaybackState` | T4 | A |
| `classQR.method9793` | `al` | `resetLoopCount` | T4 | A |
| `classQR.method9811` | `ap` | `setLegacyPlaybackState` | T4 | A |
| `classQR.method9812` | `au` | `setCachedPlaybackState` | T4 | A |
| `classQH` | `qh` | `AnimationFreezeWindow` | T4 | A |
| `classQH.field5538` | `az` | `startCycle` | T4 | A |
| `classQH.field5536` | `af` | `endCycle` | T4 | A |
| `classQH.field5537` | `ae` | `resetActorOnStart` | T4 | A |
| `classQH.method9582` | `ep` | `isActiveAt` | T4 | A |
| `classQH.method9573` | `af` | `shouldResetActorOnStart` | T4 | A |
| `classKB.method6491` | `bd` | `advanceActorAnimations` | T4 | A |
| `classCU.method2672` | `bl` | `applyPlayerActionSequence` | T4 | A |
| `classQA.method9477` | `az` | `getAnimationDefinition` | T4 | A |

## Controller state semantics

### `frame`

For legacy frame-list definitions, `frame` is the current index into `Animation.field5125` / `frameLengths`.

For cached-model animations, the same integer slot is used as the current cached animation time/frame position. The controller is intentionally shared across both definition modes; Rust does not need to copy that storage choice if a typed representation is clearer.

### `frameCycle`

`frameCycle` is used by the legacy path only. `classRD.method9980` adds the requested advancement amount and advances while:

```text
frameCycle > frameLengths[frame]
```

The comparison is strictly `>` rather than `>=`. Preserve that timing quirk.

The cached-model path does not update `frameCycle`.

### `loopCount`

`loopCount` is now exact rather than provisional. Both advancement modes increment it once when playback crosses the definition end and attempts to loop back.

The sequence becomes terminal when either:

- `loopCount >= Animation.field5122`; or
- subtracting `Animation.frameStep` leaves the next frame/time outside the valid definition range.

`Animation.field5122` is therefore the authored maximum-loop count for this scheduler path. It comes from definition opcode `8` and defaults to `99`.

This also closes the same-id restart distinction from `classCU.method2672`:

- restart mode `1` -> `resetPlaybackState`: frame, frameCycle, and loopCount become zero and action delay is reapplied;
- restart mode `2` -> `resetLoopCount`: only loopCount becomes zero;
- other restart modes -> playback state is unchanged.

### `unreadyCycles`

`field5598` was previously left generic because it looked superficially like elapsed playback time. Exact advancement proves otherwise.

`classRD.method9980` does:

```text
if no definition:
    unreadyCycles = 0
else if Animation.method8820(definition) reports required animation assets ready:
    unreadyCycles = 0
else:
    unreadyCycles += advanceAmount
```

`Animation.method8820` checks readiness of the cached-model animation resource or the first legacy frame archive. Therefore this counter measures consecutive advancement time while the definition's required animation assets are not ready.

`classKB.method6491` uses that state for fallback posing: when the active pose controller has positive `unreadyCycles`, the idle fallback controller may be kept synchronized/advanced while its own `unreadyCycles` remains below `30`.

Do not use this field as an action age, assignment age, loop timer, or generic sequence elapsed counter.

## Common advancement result bits

`classRD.method9980` returns a bitset consumed by the actor scheduler. The exact observed meanings are:

| Bit | Meaning established by source |
| ---: | --- |
| `1` | playback crossed the definition end / loop boundary |
| `2` | controller reached terminal completion for this advancement |
| `4` | at least one frame/time step advanced |
| `8` | `Animation.frameStep == -1` on entry |

`classKB.method6491` primarily consumes bit `2` for pose/action controllers. Spot-animation scheduling also observes the loop-boundary bit; its removal/repeat policy belongs to the separate spot-animation trace.

## Legacy frame advancement

For a non-cached definition, `classRD.method9980` reads:

```text
frame
frameCycle
loopCount
```

If `frameStep > 0` and `Animation.field5131 > 0`, a large requested advancement is reduced modulo the definition's total legacy frame-duration sum before stepping.

Then:

```text
frameCycle += advanceAmount
while frameCycle > frameLengths[frame]:
    frameCycle -= frameLengths[frame]
    frame += 1
    emit frame callback/event when applicable

    if frame >= frameCount:
        loopCount += 1
        mark loop boundary
        frame -= frameStep

        if loopCount >= maxLoops:
            mark terminal

        if frame is outside [0, frameCount):
            mark terminal
            frame = 0
```

The final triple is written through `setLegacyPlaybackState`.

Important quirks:

- frame transition is `frameCycle > frameLength`, not `>=`;
- loop-back subtracts authored `frameStep`; it is not automatically zero;
- max-loop completion and invalid loop-back are independent terminal causes;
- a terminal controller can still leave its stored frame normalized to zero; the caller decides whether to clear or restart the controller.

## Cached-model advancement

`classEJ.method3922` handles definitions for which `Animation.method8805` selects the cached-model/Maya path.

There is no per-frame duration array on this path. The controller's `frame` acts as animation time/frame position and advances once per requested cycle:

```text
while advanceAmount > 0:
    advanceAmount -= 1
    frame += 1
    mark advanced
    emit frame callback/event when applicable

    if frame >= cachedDuration:
        loopCount += 1
        mark loop boundary
        frame -= frameStep

        if loopCount >= maxLoops:
            mark terminal

        if frame is outside [0, cachedDuration):
            mark terminal
            frame = 0
```

For positive `frameStep`, large advances are reduced to the equivalent positive remainder inside one loop-step span before iteration.

The final pair is written through `setCachedPlaybackState`; `frameCycle` is deliberately untouched.

Legacy and cached playback therefore share loop-count/completion policy while retaining different intra-loop timing state.

## Actor scheduling order

`classKB.method6491` is the scheduler that ties together the names already established in cluster 03: `poseController`, `idlePoseController`, `actionController`, `frozenActionController`, `animationFreezes`, and `actionDelay`.

The relevant order is:

1. evaluate the action/path delay-hold rule;
2. process freeze-window start transitions and snapshot controller state as needed;
3. compute whether the actor is currently animation-frozen;
4. when not frozen, advance the active pose controller;
5. maintain/advance the idle fallback controller when the active pose is waiting on animation assets;
6. advance actor spot-animation controllers regardless of the pose/action freeze gate;
7. unless the action was held at delay `1`, decrement positive action delay;
8. only when not frozen and no positive delay was consumed this cycle, advance the action controller;
9. if action advancement returns terminal bit `2`, clear the action sequence;
10. update the actor boolean sourced from the active action definition's `field5135`.

This means a positive action delay is consumed on a cycle **instead of** advancing the action controller. Advancement starts on a later cycle after the delay has reached zero.

### Action hold at delay one

Before freeze processing, an active action can force `actionDelay = 1` and suppress the later decrement for that cycle when the exact movement/path gate involving `Animation.field5129`, the captured path remainder, and forced-movement cycle bounds is active.

This is a semantic scheduler hold, not render interpolation.

### Freeze behavior

`AnimationFreezeWindow` remains half-open:

```text
startCycle <= cycle < endCycle
```

On the first start transition, the scheduler can snapshot current action/pose controller state and optionally invoke the actor reset helper.

While frozen:

- ordinary pose advancement is suppressed;
- ordinary action advancement/delay progression is suppressed by the surrounding scheduler gate;
- the actor's outer ordinary smooth-turn block is also suppressed via `Actor.field1466`;
- spot-animation controllers are still processed by `classKB.method6491` because that loop sits outside the `!frozen` pose/action block.

Do not turn the freeze flag into a blanket "nothing animated advances" rule.

## Pose completion versus action completion

Callers interpret terminal bit `2` differently.

For the active pose and idle fallback controllers, terminal playback causes `resetPlaybackState`; the sequence remains assigned and restarts from its beginning.

For the action controller, terminal playback causes `clearSequence`; the action becomes inactive.

Therefore finite-loop completion belongs to controller/scheduler semantics, but **completion policy is slot-specific**. A generic sequence player that always clears or always loops would be wrong.

## Definition ownership

The exact scheduling fields now established are:

| `Animation` field | Definition source | Scheduler meaning |
| --- | --- | --- |
| `field5123` / API `frameLengths` | opcode `1` | legacy per-frame duration |
| `field5132` / API `frameStep` | opcode `2`, default `-1` | loop-back distance |
| `field5119` | opcode `5`, default `5` | replacement priority |
| `field5122` | opcode `8`, default `99` | maximum completed loops before terminal playback |
| `field5129` | opcode `9`, derived default | preexisting-path action movement gate |
| `field5117` | opcode `10`, derived default | post-remainder action movement gate |
| `field5120` / API `restartMode` | opcode `11`, default `2` | same-id restart policy |
| `field5126` | opcode `13`, default `-1` | cached-model animation identity |
| `field5127` / `field5128` | opcode `15` | cached-model start/end range; their difference is cached duration |

`Animation.field5131` is the sum of decoded legacy frame lengths and is used as an advancement-modulo optimization when looping is possible.

These fields are immutable cache-definition policy. `GameState` should consume a dependency-light sequence-policy/timing view rather than own decoded cache objects.

## Stock behavior versus injection

`AnimationSequence` contains RuneLite callback hooks for sequence changes, and frame advancement can invoke callback/event surfaces through the supplied callback sink. Those posts are injection/observation behavior.

The following are stock-compatible semantic requirements for the Rust port:

- sequence assignment/restart/priority;
- frame/frameCycle progression;
- cached-model time progression;
- loopCount and maximum-loop completion;
- action delay and movement hold;
- freeze windows;
- slot-specific clear/restart behavior;
- definition readiness fallback state where visible posing depends on it.

Plugin callback dispatch should not become the owner of any of those transitions.

## RustClient ownership

A useful Rust semantic split is:

```text
SequencePolicy / SequenceTiming  (immutable cache-derived view)
    legacy frame lengths
    cached duration/range
    frame step
    max loops
    restart mode
    replacement priority
    movement-gate policies

SequencePlayback               (authoritative GameState)
    sequence id
    mode-specific frame/time
    legacy frameCycle when applicable
    loopCount
    start/action delay
    unreadyCycles where fallback compatibility requires it
    active/completed state
```

The actor scheduler decides when each slot advances and what terminal completion means. Scene/model code consumes the committed frame/time and immutable sequence definition to pose geometry; it must not increment counters or decide whether an action has ended.

## Client quirks and cautions

- The legacy frame boundary uses strict `>`.
- Cached-model animation has no legacy `frameCycle` progression.
- Loop-back subtracts `frameStep`; it does not blindly restart at frame zero.
- `maxLoops` is checked after incrementing `loopCount` at an end crossing.
- Invalid loop-back marks terminal even before a valid max-loop limit would otherwise be reached.
- Pose slots restart after terminal completion; the action slot clears.
- `unreadyCycles` tracks animation-resource readiness, not semantic playback age.
- Freeze windows suppress pose/action scheduling but not the spot-animation loop in this method.
- Same-id restart mode `2` resets only loopCount, preserving the current frame and frameCycle.

No ambiguous labelled-break result affects the legacy/cached advancement conclusions: the terminal conditions, loop-back assignments, and final controller writes are direct in the revision-240 semantic source. A secondary decompiler was not required for this slice.

## Handover

Closed by this slice:

- `classQR.field5598` -> `unreadyCycles`;
- `classQR.field5599` -> `loopCount`;
- legacy frame advancement and strict boundary rule;
- cached-model advancement;
- `Animation.field5122` maximum-loop completion use;
- common loop/terminal result semantics;
- action delay consumption and terminal clear behavior;
- pose restart versus action clear distinction;
- freeze interaction with pose/action versus spot effects.

Next trace: actor spot-animation slot add/remove/repeat/advance semantics and corresponding player/NPC update-mask fields. Hitsplats/headbars should follow after spot effects.

## Verification

Not run in this session by explicit user instruction. No `verify.sh`, tests, Clippy, CI, compilation, mapping regeneration, or build gate was executed.
