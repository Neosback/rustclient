# 07 animation state

Status: `specified`

Rust owners: `game-state` for sequence-controller and actor scheduling state; `cache` for Animation definition lookup/decoding. Script/UI code can request sequence changes, but it should not own sequence timing.

## Family

`classQR` is the runtime controller for one active sequence. The revision-235 carry proposal calls it `AnimationSequence`, and revision 240 preserves the same structural role: sequence id, cached `Animation` definition, frame/frame-cycle state, Actor linkage and set/clear/copy/advance behavior.

`classQH` is the time window used by Actor's already-mapped `animationFreezes` list. `classKB.method6491` is the per-cycle actor animation scheduler. `classCU.method2672` is the Player-specific action-sequence application helper. `classQA.method9477` is the Animation-definition cache lookup used when a controller changes sequence.

## Old to new

| Stable symbol | `@ObfuscatedName` | Readable name | Tier | Confidence |
| --- | --- | --- | --- | --- |
| `classQR` | `qr` | `AnimationSequence` | T3 | B |
| `classQR.field5604` | `to` | `actor` | T4 | A |
| `classQR.field5596` | `af` | `sequenceId` | T4 | A |
| `classQR.field5597` | `ae` | `definition` | T4 | A |
| `classQR.field5600` | `ag` | `frame` | T4 | A |
| `classQR.field5602` | `ab` | `frameCycle` | T4 | A |
| `classQR.method9770` | `au` | `setSequence` | T4 | A |
| `classQR.method9765` | `dc` | `clearSequence` | T4 | A |
| `classQR.method9775` | `ag` | `getDefinition` | T4 | A |
| `classQR.method9772` | `bz` | `hasDefinition` | T4 | A |
| `classQH` | `qh` | `AnimationFreezeWindow` | T4 | A |
| `classQH.field5538` | `az` | `startCycle` | T4 | A |
| `classQH.field5536` | `af` | `endCycle` | T4 | A |
| `classQH.field5537` | `ae` | `resetActorOnStart` | T4 | A |
| `classQH.method9582` | `ep` | `isActiveAt` | T4 | A |
| `classQH.method9573` | `af` | `shouldResetActorOnStart` | T4 | A |
| `classKB.method6491` | `bd` | `advanceActorAnimations` | T4 | A |
| `classCU.method2672` | `bl` | `applyPlayerActionSequence` | T4 | A |
| `classQA.method9477` | `az` | `getAnimationDefinition` | T4 | A |

## Sequence controller semantics

`AnimationSequence.setSequence` changes the sequence id, resolves the corresponding `Animation` definition through `getAnimationDefinition`, resets elapsed sequence state and normalizes an uninitialized frame to zero. A negative id clears the definition and returns the frame state to its inactive sentinel. `clearSequence` is exactly the `-1` form of this operation.

`frame` is the current definition frame. `frameCycle` is the time spent in that frame for frame-length based animations. The controller also contains loop/elapsed counters that are intentionally left generic in this wave because their exact distinction depends on all advancement modes, including cached-model sequences.

## Freeze windows

`AnimationFreezeWindow` is half-open: `startCycle <= cycle < endCycle`. `advanceActorAnimations` tests all of an Actor's freeze windows each client cycle and suppresses normal advancement while any window is active. When a window begins, the scheduler can snapshot/copy the current controller state; if `resetActorOnStart` is true, it also invokes the Actor reset helper before continuing.

The half-open interval and start transition are observable client behavior. Do not convert this to an inclusive end or remove the one-time start handling when porting.

## Actor scheduling order

`classKB.method6491` is the scheduler that ties together the names already established in cluster 03: `poseController`, `idlePoseController`, `actionController`, `frozenActionController`, `animationFreezes` and `actionDelay`. It checks the current client cycle, evaluates freezes, advances the non-frozen controllers and applies action-delay/definition gating before progressing the action sequence.

`classCU.method2672` handles Player action-sequence replacement/restart. Reapplying the same sequence consults the definition's restart mode. Replacing a different active sequence is priority-sensitive. The helper also captures the current path backlog used by the movement/action gate.

This means the Rust owner should be a deterministic game-state sequence scheduler. Rendering consumes the selected frame/model state; rendering must not advance it.

## Definition ownership

`classQA.method9477` looks up `Animation` definitions from the definition cache, reads archive group 12 on a miss, constructs/caches the definition and returns it. RustClient should expose that through the `cache` definition layer and hand an immutable definition/reference to `game-state` scheduling.

## Stock behavior versus injection

`AnimationSequence` contains RuneLite callback hooks for sequence changes, and `getAnimationDefinition` posts `PostAnimation` after decoding/caching a definition. Those callback posts are `injection-only`. Sequence id/frame timing, restart/priority rules, freeze windows and cache lookup are the stock behavior relevant to the port.

## External referrers and handovers

Actor sequence fields are already owned by cluster 03 and are not duplicated here. `Animation` definition fields such as `restartMode`, frame lengths and frame step already have seed/API names where available. `client.field856` is the client cycle and remains client-owned. The mixed-purpose `classKB`, `classCU` and `classQA` containers stay generic; only the proven animation methods are named.

## Client quirks

The freeze interval is half-open and the scheduler records a distinct start transition before testing active-state suppression. Sequence replacement is not a simple assignment: same-id restart behavior and cross-id priority checks must be preserved. Cached-model animation definitions and frame-length animations take different advancement paths inside the controller.

## Deliberately generic

`classQR.field5598` and `classQR.field5599` remain generic until their elapsed/loop roles are reconciled across both definition modes. The enclosing `classKB`, `classCU` and `classQA` classes remain generic because animation behavior is only one responsibility in each. No renderer-side names are introduced.
