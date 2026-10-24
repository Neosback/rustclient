# 17 - common animation-sequence advancement

Status: **specified** for the common revision-240 playback entry point used by actors and transient world effects.

## Scope and ownership

This cluster owns `classRD` only for mapping purposes. The Java class is mixed-purpose: it is an abstract comparator chain and also hosts a static sequence-advancement helper. Naming the entire class after animation playback would therefore be misleading, so the class deliberately remains `classRD` while the behaviorally proven method is named.

Read-only dependencies:

- `classQR` / cluster 07: `AnimationSequence` controller state;
- `Animation`: immutable sequence definition data;
- `classEJ.method3922`: cached-model playback branch;
- `classKB.method6491`: actor sequence scheduler;
- `GraphicsObject`: standalone graphics-effect advancement;
- `Projectile`: projectile model-sequence advancement.

## Mapping

| Stable symbol | `@ObfuscatedName` | Readable name | Evidence |
| --- | --- | --- | --- |
| `classRD.method9980` | `az` | `advanceAnimationSequence` | T4: exact common controller advancement path, including legacy/cached dispatch and returned state flags |

The method name is confidence B because the exact revision-240 behavior is direct but the mixed-purpose class has no independent API semantic name.

## Entry contract

Conceptually:

```text
result = advanceAnimationSequence(
    controller,
    advanceAmount,
    frameEventSink,
    ...
)
```

The authoritative mutable state is the supplied `AnimationSequence`. The immutable timing and loop policy comes from its current `Animation` definition.

If the controller has no definition, the helper resets the controller's animation-resource wait counter to zero and returns no advancement flags.

## Resource readiness

When a definition exists, the helper updates the `unreadyCycles` state already named in cluster 07:

```text
if definition-required animation assets are ready:
    unreadyCycles = 0
else:
    unreadyCycles += advanceAmount
```

This counter is resource-readiness state, not generic elapsed animation time.

## Legacy-frame path

For a non-cached-model definition, the helper reads the controller's:

```text
frame
frameCycle
loopCount
```

It adds the requested advancement and crosses a frame boundary only while:

```text
frameCycle > frameLength[frame]
```

The strict `>` is observable timing behavior. It is not `>=`.

At each crossed frame it advances the frame index and can invoke the supplied frame event sink. When the frame index reaches the definition end, it:

1. increments `loopCount`;
2. marks loop-boundary result bit `1`;
3. subtracts the authored `frameStep` from the frame index;
4. evaluates max-loop and invalid-loopback terminal conditions;
5. writes the resulting frame/frameCycle/loopCount state back to the controller.

The exact legacy semantics remain canonical in cluster 07; this cluster provides the readable stable method name used by other subsystem write-ups.

## Cached-model path

When the `Animation` definition selects cached-model playback, `advanceAnimationSequence` delegates the mode-specific progression to `classEJ.method3922`.

That path advances the controller's cached animation time/frame state without legacy `frameCycle` progression, while sharing loop-count and completion policy with the common controller.

`classEJ` remains a handover symbol here rather than being pulled into this mixed-purpose class shard.

## Result bits

The revision-240 callers establish these result flags:

| Bit | Meaning |
| ---: | --- |
| `1` | crossed the sequence end / loop boundary |
| `2` | reached terminal completion |
| `4` | at least one frame/time step advanced |
| `8` | definition `frameStep == -1` at entry |

The bitset is intentionally interpreted differently by each semantic owner.

## Caller-specific completion policy

### Actor pose/action slots

`classKB.method6491` uses terminal bit `2` as part of slot-specific scheduling. Pose-like controllers can restart while action controllers can clear. The common helper does not own that slot policy.

### Actor-attached spot effects

Cluster 09 establishes that the actor spot scheduler observes loop-boundary bit `1`, not only terminal bit `2`. A retained control flag can allow an actor spot effect to survive that boundary.

### Standalone `GraphicsObject`

Standalone map graphics also observe bit `1`, but they have no corresponding retain-across-loop flag. `GraphicsObject.method1110` clears its controller as soon as bit `1` appears. The world-effect loop then treats the object as finished and unlinks it.

### `Projectile`

`Projectile.method1228` calls this helper every active update to advance its embedded animation controller, but it does not use the returned bitset to end projectile lifetime. Projectile removal is governed by its explicit `endCycle` instead.

That distinction is important for the Rust port: animation completion and semantic effect lifetime are related but not universally identical.

## Frame-event sink and RuneLite injection

The helper can call the supplied `classQV` sink when a frame boundary is crossed. In the injected client this participates in callback/event observation.

The Rust semantic requirements are controller timing, frame transitions, loops and completion. Plugin callback delivery must be layered after committed state and must not become the owner of playback progression.

## RustClient owner

Do not reproduce the Java comparator class in Rust merely to host this function. The behavior belongs with dependency-light sequence playback state, for example:

```text
game-state::sequence_playback
    advance(playback, timing_policy, amount) -> AdvanceFlags
```

Immutable frame lengths, cached-model duration, frame step and maximum loops remain cache-derived policy. Actor and world-effect schedulers consume the returned flags according to their own lifetime rules.

## External referrers inspected

- `classKB.method6491` for actor pose/action/spot scheduling;
- `GraphicsObject.method1110` for standalone graphics-object first-loop completion;
- `Projectile.method1228` for projectile animation progression;
- world-entity animation call sites in `client.java` that also consume the result flags.

The exhaustive reference set was not regenerated because verification tooling is intentionally disabled for this checkpoint.

## Deliberately left generic

- `classRD` remains generic because its instance behavior is comparator chaining unrelated to animation playback.
- `classEJ.method3922` remains a handover for cached-model mode-specific advancement.
- `classQV` remains generic because this cluster only needs its callback/event-sink role and does not own that family.

## Client quirks and cautions

- Legacy frame boundaries use strict `frameCycle > frameLength`.
- Loop boundary and terminal completion are separate flags.
- A caller may intentionally end an effect on bit `1` before terminal bit `2` would occur.
- Projectile lifetime ignores the playback result flags and is cycle-bounded independently.
- Resource-readiness waiting is tracked in the controller and must not be conflated with frame age.

## Verification

Not run in this session by explicit user instruction. No `verify.sh`, tests, Clippy, CI, compilation, mapping merge or build gate was executed.
