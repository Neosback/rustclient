# Revision 240 sequence assignment and playback scheduler

<!-- authority-notice -->
> Domain source/ownership research; implementation statements are bounded to the dates
> recorded below. The [current matrix](PORT-MATRIX.md) and [audit](CURRENT-AUDIT.md)
> supersede older gap/status lists; preserve exact source details and historical evidence.

Status: exact revision-240 behavior identified for action assignment, restart/priority policy, legacy frame advancement, cached-model advancement, finite-loop completion, action delay, and actor freeze interaction. Spot-animation lifetime/repeat semantics remain a separate open effect trace.

This document is the canonical port record for authoritative actor sequence scheduling. Rendering/model posing consumes committed playback state; it is not allowed to decide sequence lifetime, loop count, movement blocking, or restart policy.

## Exact revision-240 sources

- `references/rl-suite-240/sources-semantic/classCU.java`
  - `classCU.method2672`: player action assignment/restart/priority
- `references/rl-suite-240/sources-semantic/classQR.java`
  - active sequence controller state and reset/copy helpers
- `references/rl-suite-240/sources-semantic/classRD.java`
  - `classRD.method9980`: common advancement and full legacy-frame path
- `references/rl-suite-240/sources-semantic/classEJ.java`
  - `classEJ.method3922`: cached-model animation advancement
- `references/rl-suite-240/sources-semantic/classKB.java`
  - `classKB.method6491`: actor-cycle scheduling, action delay, freeze windows, slot-specific completion behavior
- `references/rl-suite-240/sources-semantic/Animation.java`
  - sequence-definition opcode decode and timing/policy fields
- `references/rl-suite-240/sources-semantic/Actor.java`
  - action/path movement gates and authoritative sequence slots

Readable controller names are maintained by client-240 cluster 07. Stable foreign symbols remain cited where their classes are owned elsewhere.

## 1. Action assignment policy

`classCU.method2672(Player actor, requestedSequence, delay, ...)` applies authored definition policy before mutating the action controller.

### Same ID

When the requested ID equals the currently active ID and is not `-1`, `Animation.field5120` controls the result:

- restart mode `1`: reset frame, frameCycle, and loopCount to zero, then reapply the supplied action delay;
- restart mode `2`: reset only loopCount;
- other values: preserve playback state unchanged;
- return immediately after the same-ID handling; do not run replacement-priority arbitration.

Cluster 07 now names the exact controller helpers:

- `classQR.method9791` -> `resetPlaybackState`;
- `classQR.method9793` -> `resetLoopCount`.

This is why restart mode 2 must not be implemented as a generic animation restart.

### Different ID

If the requested ID is not `-1` and the current controller has a definition, assignment is rejected when:

```text
new Animation.field5119 < current Animation.field5119
```

`Animation.field5119` comes from sequence-definition opcode `5`, defaults to `5`, and is the replacement priority for this path.

If replacement is allowed:

1. change/clear the action sequence ID;
2. reset playback state;
3. set the supplied action delay;
4. copy current path backlog/remainder state into `Actor.field1476`.

Clearing with `-1` is not blocked by the replacement-priority test.

## 2. Exact controller state

The revision-240 controller has two counters that were previously unresolved.

### `classQR.field5599` -> `loopCount`

This is exact.

Both `classRD.method9980` and `classEJ.method3922` increment it when playback crosses the definition end and attempts to loop. Completion checks it against `Animation.field5122`.

Same-ID restart mode 2 resets only this field, further confirming that it is the independent iteration/loop counter.

### `classQR.field5598` -> `unreadyCycles`

This is **not** playback elapsed time.

At the beginning of `classRD.method9980`:

```text
if controller has no definition:
    unreadyCycles = 0
else if Animation.method8820(definition) reports animation assets ready:
    unreadyCycles = 0
else:
    unreadyCycles += advanceAmount
```

`Animation.method8820` checks the cached-model animation resource on the Maya/cached path and the first required legacy frame archive on the legacy path.

`classKB.method6491` uses positive `unreadyCycles` to keep an idle fallback pose controller synchronized/advancing for a bounded window (`< 30` on the fallback controller).

Do not use this field as sequence age or completion time in Rust.

### Mode-specific playback state

Legacy definitions use:

```text
frame
frameCycle
loopCount
```

Cached-model definitions use:

```text
frame/time position
loopCount
```

The cached path deliberately leaves `frameCycle` untouched.

A Rust representation may use a typed enum for these mode-specific fields rather than mirroring the Java storage layout.

## 3. Definition fields now closed for scheduling

| Opcode | Source field | Default | Exact scheduler role |
| ---: | --- | ---: | --- |
| 1 | `field5123` + `field5125` | absent | legacy frame lengths and frame IDs |
| 2 | `field5132` / API `getFrameStep()` | `-1` | loop-back distance |
| 5 | `field5119` | `5` | replacement priority |
| 8 | `field5122` | `99` | maximum loop count before terminal completion |
| 9 | `field5129` | derived | action/path movement gate while pre-assignment path remainder exists |
| 10 | `field5117` | derived | action/path movement gate after that remainder is exhausted |
| 11 | `field5120` / API `getRestartMode()` | `2` | same-ID restart behavior |
| 13 | `field5126` | `-1` | cached-model animation identity |
| 15 | `field5127`, `field5128` | `0`, `0` | cached-model start/end range; difference is cached duration |
| 17 | `field5134` | absent | boolean mask affecting opcode 9/10 defaults and blending behavior |

Opcode 9 and 10 defaulting remains exact:

- if neither the opcode-3 interleave table nor opcode-17 mask exists, a missing value defaults to `0`;
- otherwise a missing value defaults to `2`.

`Animation.field5131` is the sum of decoded legacy frame lengths and is used to reduce large legacy advancement amounts modulo total duration when loop-back is possible.

## 4. Common advancement flags

`classRD.method9980(controller, advanceAmount, callbackSink, ...)` returns a bitset.

Observed meanings:

| Bit | Meaning |
| ---: | --- |
| `1` | playback crossed the end and attempted a loop |
| `2` | terminal completion occurred |
| `4` | at least one frame/time step advanced |
| `8` | definition entered advancement with `frameStep == -1` |

The actor scheduler uses terminal bit `2` for pose/action lifetime decisions. Spot-effect scheduling also observes the loop-boundary bit; that effect-specific behavior remains for the next trace.

## 5. Legacy frame advancement

For non-cached definitions, `classRD.method9980` loads current `frame`, `frameCycle`, and `loopCount`.

If the stored frame is already beyond the frame array, it normalizes frame/frameCycle to zero before continuing.

When `frameStep > 0` and total legacy frame duration is positive, a large requested advancement is reduced modulo that duration.

The core loop is:

```text
frameCycle += advanceAmount

while frameCycle > frameLengths[frame]:
    frameCycle -= frameLengths[frame]
    frame += 1
    mark ADVANCED
    emit frame callback when configured

    if frame >= frameCount:
        loopCount += 1
        mark LOOP_BOUNDARY
        frame -= frameStep

        if loopCount >= maxLoops:
            mark TERMINAL

        if frame < 0 or frame >= frameCount:
            mark TERMINAL
            frame = 0

        if not terminal:
            emit callback for looped-to frame when configured
```

Then the controller stores the final `(frame, frameCycle, loopCount)`.

### Timing quirk

The legacy boundary is strictly:

```text
frameCycle > frameLengths[frame]
```

not `>=`.

Do not replace it with a conventional zero-based `>= duration` loop without reconciling the resulting one-cycle difference.

### Loop-back quirk

Looping subtracts authored `frameStep` from the frame index. It does **not** automatically restart at frame `0`.

If the subtraction produces an invalid frame, playback is terminal even if `maxLoops` has not yet been reached.

## 6. Cached-model advancement

`classEJ.method3922` handles cached-model/Maya definitions.

It reads the controller's frame/time position and loopCount. There is no legacy `frameCycle` accumulation.

For positive `frameStep`, a large positive advancement is reduced to the equivalent positive remainder within one loop-step span.

Then each remaining cycle does:

```text
frame += 1
mark ADVANCED
emit frame callback when configured

if frame >= cachedDuration:
    loopCount += 1
    mark LOOP_BOUNDARY
    frame -= frameStep

    if loopCount >= maxLoops:
        mark TERMINAL

    if frame < 0 or frame >= cachedDuration:
        mark TERMINAL
        frame = 0

    if not terminal:
        emit callback for looped-to frame when configured
```

The controller stores `(frame, loopCount)` and leaves legacy frameCycle alone.

Legacy and cached definitions therefore share the same loop-count and terminal policy but not the same intra-loop timing representation.

## 7. Slot-specific completion policy

Terminal bit `2` does not have one universal consequence.

### Pose controller

When active pose advancement becomes terminal, `classKB.method6491` calls `resetPlaybackState`.

The pose sequence remains assigned and restarts from its beginning.

### Idle fallback controller

The same terminal behavior applies: reset playback state, keep the sequence assigned.

### Action controller

When action advancement becomes terminal, `classKB.method6491` calls `clearSequence`.

The action becomes inactive.

Therefore a generic scheduler that always loops or always clears on terminal completion would be incompatible. Lifetime policy belongs to the actor slot using the shared playback result.

## 8. Exact action-delay ordering

`classKB.method6491` separates delay consumption from action advancement.

When the special action/path hold is not active:

```text
if actionDelay > 0:
    actionDelay -= 1
    consumedDelayThisCycle = true

if not frozen and not consumedDelayThisCycle:
    advance action controller by one cycle
```

So a controller does **not** advance on the same cycle that a positive delay is decremented to zero.

### Hold at delay one

Before freeze-window processing, an active action can force:

```text
actionDelay = 1
```

and mark the cycle so that the later delay decrement/action advancement block is skipped.

The gate involves:

- action definition `field5129 == 1`;
- captured pre-assignment path remainder `Actor.field1476 > 0`;
- the exact/forced-movement cycle bounds relative to the current client cycle.

This reproduces the old-client action/path synchronization hold. It changes authoritative action timing and cannot live in scene interpolation.

## 9. Movement-gating policy

Separate from the delay-one hold, Actor path movement uses two authored definition policies while an action is active with zero explicit delay:

```text
if Actor.field1476 > 0 and Animation.field5129 == 0:
    block ordinary path progress

if Actor.field1476 <= 0 and Animation.field5117 == 0:
    block ordinary path progress
```

`Actor.field1476` is initialized from current path backlog/remainder when a new action is assigned and changes as path work is consumed.

This policy belongs in the shared actor semantic scheduler, not the renderer.

## 10. Freeze-window interaction

`AnimationFreezeWindow` is half-open:

```text
startCycle <= cycle < endCycle
```

At a start transition, `classKB.method6491` can snapshot action/pose controllers and optionally invoke the actor reset helper.

While the actor is frozen:

- active pose advancement is suppressed;
- action delay/action advancement is suppressed by the scheduler's frozen gate;
- ordinary smooth body turning is skipped by the outer actor cycle via the same actor frozen state;
- spot-animation controllers are still processed because their loop in `classKB.method6491` is outside the `!frozen` pose/action section.

This is not a global animation stop.

## 11. Resource-readiness fallback

When not frozen, the active pose controller advances first.

The scheduler then sets the fallback controller to the authored idle sequence. If the active pose has positive `unreadyCycles` and the idle fallback has been unready for fewer than 30 cycles:

- if both controller IDs match, copy active pose controller state into the fallback;
- otherwise advance the fallback controller independently and restart it on terminal completion.

This is compatibility behavior for animation-resource readiness. RustClient may express the fallback differently architecturally, but it must not reinterpret `unreadyCycles` as gameplay sequence age.

## 12. Historical RustClient mismatches

The original audit found the following gaps. Use the dated implementation checkpoint below for current ownership/status.

### Cache policy loss

The revision-240 cache adapter historically discarded opcode `5`, `9`, and `10`, preventing exact replacement priority and movement-gate policy from reaching GameState.

The cache-derived semantic policy must preserve final values after constructor defaulting, not only explicit wire opcodes.

### Same-ID reassignment

`ActorSequenceSlot::set_sequence` historically ignored same-ID requests, so restart modes 1 and 2 could not be reproduced.

### Playback state too small

An aggregate `cycle` field is insufficient for the exact contract. The source distinguishes:

- legacy frame;
- legacy within-frame cycle;
- cached-model frame/time;
- loopCount;
- action delay;
- asset-readiness wait state where fallback posing needs it;
- terminal lifetime interpreted by slot.

### Assignment lacks policy input

Raw `{id, delay}` mutation cannot evaluate restart mode, replacement priority, max loops, or movement gates. The application/domain boundary must resolve an immutable sequence-policy view before authoritative mutation.

### Shared actor path state

Player/NPC sequence scheduling needs enough shared queued-path semantics to reproduce the `field1476` movement/action interaction. Rust does not need the exact Java array layout, but it does need the behavior.

## 13. Required Rust semantic contract

A dependency-light immutable view should expose at least:

```text
SequencePolicy {
    restart_mode
    replacement_priority
    movement_gate_with_preexisting_path
    movement_gate_after_preexisting_path
    frame_step
    max_loops
    timing: Legacy { frame_lengths } | Cached { duration }
}
```

Authoritative playback needs mode-specific state rather than one ambiguous cycle counter, for example:

```text
SequencePlayback {
    sequence_id
    delay
    loop_count
    unready_cycles
    position:
        Legacy { frame, frame_cycle }
        Cached { time }
}
```

Exact type placement should respect crate dependency rules; `game-state` should not depend directly on heavyweight cache objects merely to evaluate policy.

A clean flow remains:

```text
protocol update
    -> SequenceChange { id, delay }
    -> application resolves immutable SequencePolicy
    -> GameState applies assignment/restart/priority/path policy
    -> actor scheduler advances committed playback per cycle
    -> scene/model poses committed frame/time
```

## 14. Implementation acceptance cases

When implementation begins, focused tests should cover at least:

1. same ID + restart mode 1 resets frame/frameCycle/loopCount and reapplies delay;
2. same ID + restart mode 2 resets only loopCount;
3. same ID + other restart mode is a no-op;
4. lower-priority different ID is rejected;
5. equal/higher priority replacement succeeds and captures path interaction state;
6. clearing ignores replacement-priority rejection;
7. legacy transition uses strict `>` frame-length boundary;
8. legacy loop-back subtracts frameStep;
9. cached playback advances one time unit per cycle without mutating frameCycle;
10. loopCount increments at end crossing and maxLoops produces terminal completion;
11. invalid loop-back is terminal;
12. terminal pose restarts while terminal action clears;
13. positive delay decrements without advancing action on the same cycle;
14. delay-one path hold preserves delay and suppresses action advancement;
15. freeze suppresses pose/action but does not suppress the spot-animation loop;
16. asset-unready pose fallback uses readiness state rather than gameplay elapsed time;
17. authoritative replay/fingerprint state includes newly semantic playback fields.

## 15. Remaining research

The core action/pose sequence lifetime question is now closed as a source-control-flow problem.

Next research should move to:

1. actor spot-animation add/remove/repeat/advance semantics and mask decode;
2. hitsplat/headbar replacement and expiry rules;
3. overhead/tint/freeze/customization state;
4. player composition/model invalidation and final pose assembly.

Do not reopen `classQR.field5598` as an elapsed-time candidate or `field5599` as an ambiguous counter unless new contradictory exact-240 evidence appears.

## Historical research verification

Source/manual evidence only for the original research wave. No `verify.sh`, tests, Clippy, CI, compilation, mapping regeneration, or workspace gate was run.


## Action implementation checkpoint, 2026-10-26

`game-state::sequence_playback` now advances resolved actor action lifetimes, after movement and
body turning. Legacy playback uses strict `>` boundaries, carries the remainder instead of
resetting every new frame to one, normalizes stale frame indices, rewinds the authored count and
terminates at invalid rewinds/max loops. For a positive rewind, the source total-duration modulo
also applies to a one-cycle request: total duration one yields an advance of zero. Rust checks
only the first two nonzero durations to establish that special case, avoiding an ordinary full
frame-table sum per actor/cycle.

Cached playback increments `ActorSequenceSlot::frame` as a zero-based time cursor, compares it
with end minus start, rewinds by the authored count, increments iterations and clears the action
on terminal completion. It leaves the legacy `cycle` counter unchanged. The cache timing adapter
now retains the cached rewind; schema 28 fingerprints it independently from range and actor state.
The global timing registry remains immutable evidence, and new actors still require their own
resolved bit rather than inheriting readiness from an old actor at the same index.

The `classKB.method6491` delay-one path hold runs after movement, using remaining pre-assignment
path backlog and forced-cycle bounds. A consumed positive delay never advances the action in
that same cycle. Semantic cycle deltas/replay expose completion; per-frame/audio callbacks are
not implemented by this checkpoint.

`scene::entity_mesh::actor_animation` samples committed frames/time for resolved actions, including
legacy within-frame smoothing input. It does not compute their loop lifetime from elapsed time.
Elapsed sampling remains for movement poses and deliberately unresolved direct fixtures. The
existing animation height-offset selection uses the same selected playback. Missing animation
resources retain existing rendering fallback; no asset-readiness parity is claimed.

Rust robustness difference: resolved empty legacy timing or a zero-duration repeating tail clears
the action in bounded work. The source can fault or spin on those degenerate definitions. This is
not an assertion that their invalid-data runtime outcomes match. Missing timing remains explicitly
unresolved, with the prior aggregate compatibility path; it is not classified or given a guessed
cached duration. Action absence keeps RustClient's canonical cleared-slot representation.

Focused evidence: `game-state/src/sequence_playback.rs`, `game-state/tests/action_playback.rs`,
`cache/src/osrs240/sequence_policy.rs`, `scene/src/entity_mesh/actor_animation.rs`, and cached-rewind
fingerprint regression assertions. Cases cover local/remote/NPC completion, positive delays,
preexisting walking, forced hold bounds, range offsets, zero/missing/invalid rewinds, loop limits,
replay, client-cycle partition, reset/index reuse, and committed pose sampling. These are manual
source-derived contracts, not a complete live differential run.

Still incomplete: movement/idle/fallback controller lifetime and synchronization, model readiness,
freeze-window retention/ordering, slot-specific frame/audio callbacks, spot effects, full sequence
blending and logged-in visual acceptance. Do not label the entire actor scheduler complete.

Implementation verification (Rust 1.99.0): formatting check, workspace/all-target check,
strict workspace/all-target Clippy, workspace tests (537 passed, zero failed, one ignored),
web Wasm check and native binary build all passed. Logs are under
`target/action-playback-{fmt,workspace-check,clippy,workspace-tests,wasm,native}.log`.
The ignored packet replay test requires a live capture and local-player index. Matrix validation
passed for 42 domains and all 13 TypeScript evidence hashes; symbol-ledger validation passed
for 138 rows. Added source-manifest pins for classRD/classEJ/classKB; reference sources were not
edited. The reference MCP status query returned connection refused, so no new runtime trace or
fixed/classic/modern visual comparison is claimed for this checkpoint.
