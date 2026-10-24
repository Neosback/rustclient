# Revision 240 forced/exact actor movement

Status: exact source behavior is pinned and sequence timing now reaches semantic entity updates; authoritative interpolation is the next checkpoint.

## Exact revision-240 control flow

Primary evidence is `references/rl-suite-240/sources-semantic/Actor.java`, especially `Actor.method3284`.

The method has three mutually ordered position phases around the current client cycle:

1. **Approach phase** while `forced_start_cycle >= current_cycle`.
   - Compute the forced start fine coordinate.
   - Divide the remaining delta by `max(1, forced_start_cycle - current_cycle)` and approach that start coordinate.
   - Reset `field1452` catch-up debt to zero.
   - Force the authored desired orientation.

2. **Interpolation phase** while `forced_end_cycle >= current_cycle` after the approach condition is false.
   - Interpolation is always allowed on the exact end cycle.
   - It is also allowed when there is no active action sequence or the action has non-zero explicit delay.
   - Cached-model/skeletal actions allow interpolation without the legacy frame-boundary test.
   - A legacy action with zero explicit delay allows interpolation only when `current_frame_cycle + 1 > current_frame_delay`.
   - When allowed, linearly interpolate the forced start/end coordinates using `(current - start) / (end - start)`.
   - Reset `field1452` to zero and force both desired/current orientation to the authored direction.

3. **Ordinary path phase** once `current_cycle > forced_end_cycle`.
   - Forced interpolation no longer owns position.
   - The ordinary path scheduler resumes.

The approach condition is inclusive, so a zero-duration forced interval is handled by the approach branch at its start/end cycle rather than by division through a zero interpolation duration.

## Why action timing is semantic input

The legacy interpolation gate reads the active action's current frame and current frame-cycle against that frame's definition delay. This means `GameState` cannot reproduce forced movement from `ExactMovement` plus `SequencePolicy` alone.

Revision 240 additionally distinguishes cached-model actions from legacy frame sequences: cached-model actions bypass the legacy frame-delay gate. That classification is immutable definition data, not renderer state.

Part 11A therefore adds revision-neutral `SequenceTiming`:

- `Legacy { frame_delays, frame_step }`
- `CachedModel { range }`

The cache adapter derives it from the already-decoded revision-240 sequence definition. Application orchestration memoizes it and attaches a sorted `(SequenceId, SequenceTiming)` table to player/NPC semantic updates alongside `sequence_policies`.

Protocol remains cache-free and direct fixtures remain allowed to omit timing. The authoritative forced scheduler must treat missing required legacy timing as unresolved rather than inventing a frame duration.

## Current RustClient boundary

Implemented in Part 11A:

- dependency-light `SequenceTiming` semantic vocabulary;
- revision-240 projection from `SequenceAsset`;
- cached-model versus legacy classification;
- retention of legacy frame delays and frame rewind count;
- retention of cached-model range;
- application-level timing memoization;
- player/NPC update enrichment using the same referenced sequence IDs as policy resolution;
- SPEC-0012 ownership documentation.

Not implemented until the next checkpoint:

- authoritative legacy action frame/cycle advancement from resolved timing;
- exact per-inner-client-cycle forced phase selection;
- approach/interpolation mutation of `ActorPathQueue` fine position;
- active forced-phase `field1452` reset;
- forced orientation mutation;
- clearing/resuming ordinary scheduling after the end cycle;
- removal of the scene forced-position fallback.

Those pieces should land together so there is one forced-movement authority and no guessed legacy frame gate.
