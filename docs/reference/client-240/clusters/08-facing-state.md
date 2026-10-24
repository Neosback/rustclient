# 08 - actor facing state

Status: **specified** for the revision-240 facing-state representation, target classification, and movement-gating policy. The ordinary turn mutator is independently traced in cluster 03 / `classKN.method6539`; this shard still does not claim full facing-target retention or Rust parity. The remaining `Actor.field1473` arming writer is intentionally left unresolved after the focused source audit below and is no longer a naming-wave blocker.

## Scope

This cluster owns `classBZ`, `classLU`, `classLJ`, and `classLV`. It names the state object carried by `Actor.field1461`, the primary facing mode, the entity target kind, and the secondary policy controlling whether facing is gated by active movement. `Actor`, `classDC`, `classDF`, `classAS`, `Player`, `NPC`, `classGA`, `classEB`, `classBT`, `classKN`, and `client` are read-only evidence and remain owned by their existing shards.

Observed external referrers include `Actor`, `classDC`, `classEB`, and `classKN` through the desired/current-orientation lifecycle. The broader player/NPC synchronization corpus and the semantic `client.java` dispatcher were also checked in the cleanup-writer audit.

## Mapping

| Stable symbol | Readable name | Evidence |
| --- | --- | --- |
| `classBZ` | `ActorFacingState` | `Actor.field1461`; `classEB.method3820` resolves it into desired orientation |
| `classBZ.field624` | `mode` | `Actor.method3162`, `method3165`, `method3163` classify angle/area/entity modes |
| `classBZ.field622` | `snap` | `Actor.method3236`; `classEB.method3820` can snap current orientation to desired orientation when allowed |
| `classBZ.field623` | `targetKind` | `classEB.method3820` chooses NPC/player/world-entity storage from it |
| `classBZ.field631` | `targetIndex` | `Actor.method3252`; lookup key in `classEB.method3820` |
| `classBZ.field633` | `fallbackAngle` | used by `classEB.method3820` when the entity target is absent |
| `classBZ.field625` | `applyPolicy` | low-three-bit policy consumed by `Actor.method3216` and `method3222` |
| `classBZ.field627` | `angle` | explicit-angle branch consumed by `classEB.method3820` |
| `classBZ.field628` | `areaX` | area-facing origin X |
| `classBZ.field630` | `areaY` | area-facing origin Y |
| `classBZ.field629` | `areaWidth` | area-facing width |
| `classBZ.field632` | `areaHeight` | area-facing height |
| `classBZ.method1378` | `reset` | restores default mode/policy/target/snap and sentinels |
| `classBZ.method1380` | `copyFrom` | copies shared state plus the payload appropriate to the source mode |
| `classBZ.method1384` | `decode` | packet-facing state decoder called by `Actor.method3231` |
| `classLU` | `FacingMode` | exact-240 Actor predicates distinguish angle, area, entity and default modes |
| `classLU.field4226` | `ANGLE` | required by `Actor.method3162` |
| `classLU.field4224` | `AREA` | required by `Actor.method3165` |
| `classLU.field4225` | `NONE` | constructor/reset default |
| `classLU.field4223` | `ENTITY` | required by `Actor.method3163` |
| `classLJ` | `FacingTargetKind` | exact target-storage selection in `classEB.method3820` |
| `classLJ.field4174` | `PLAYER` | resolves through `WorldView.field1692` |
| `classLJ.field4175` | `NPC` | resolves through `WorldView.field1696` |
| `classLJ.field4173` | `WORLD_ENTITY` | resolves through `WorldView.field1698` |
| `classLJ.field4172` | `NONE` | constructor/reset default and rejected by entity-facing predicate |
| `classLV` | `FacingApplyPolicy` | both values are consumed only as movement-state eligibility policy for angle/area facing |
| `classLV.field4233` | `BYPASS_MOVEMENT_GATE` | `method3216`/`method3222` skip their path-movement gate for this value |
| `classLV.field4232` | `MOVEMENT_GATED` | angle/area facing remains conditioned on movement state for this value |

All rows are T4/B: exact revision-240 behavior is clear from the client, but this cluster does not add an independent RSProt facing-layout cross-check.

## Wire shape

`Actor.method3231(header, PacketBuffer)` delegates to `classBZ.method1384` after the actor-level hook. The stock semantic state decoder interprets the header as:

- bits `0..2`: `FacingApplyPolicy` through the `classLV` enum lookup;
- bits `3..5`: `FacingMode` through the `classLU` enum lookup;
- bit `6`: `snap`.

The payload depends on `FacingMode`:

- `ANGLE`: one `readSmart1or2` value into `angle`.
- `NONE`: no facing payload.
- `AREA`: `areaX`, `areaY`, then one `readSmart1or2` whose low nibble is `areaWidth` and high nibble is `areaHeight`.
- `ENTITY`: target kind via `readSmart1or2`, target index via `readSmart2or4Null`, then `fallbackAngle` via `readSmart1or2`.

Those primitive names are already established by the buffer/framing shard. Enum lookup uses each enum's ordinal contract; do not substitute Java array position.

## Facing apply policy

The two `classLV` values have a safe behavior-level name because their inspected consumers are explicit movement eligibility checks.

For explicit-angle facing, `Actor.method3216` behaves as follows:

```text
if applyPolicy != BYPASS_MOVEMENT_GATE and pathLength != 0:
    if pathCatchUpCycles <= 0:
        reject/defer this angle for the current cycle
accept otherwise
```

For area facing, `Actor.method3222` is stricter:

```text
if applyPolicy != BYPASS_MOVEMENT_GATE and pathLength != 0:
    reject/defer this area facing for the current cycle
accept otherwise
```

Therefore `field4233` does not mean a guessed concept such as "forced", "tracking", or "locked". The exact statement supported by revision 240 is that it **bypasses the movement gate**. The other value remains **movement-gated**. Explicit-angle facing has the additional catch-up exception shown above.

This policy is semantic retained state. RustClient should not collapse it into the primary `FacingMode` because two updates with identical angle/area payloads can resolve differently while the actor has queued movement.

## Runtime semantics

`classEB.method3820` resolves a desired orientation in this order:

1. An eligible explicit `ANGLE` can produce a one-shot desired angle. The angle/snap state is cleared after consumption.
2. An eligible `AREA` computes the center of the supplied world-tile rectangle, converts it to local fine coordinates relative to the current `WorldView`, and derives an angle toward that center.
3. An `ENTITY` resolves the target kind as NPC, player, or world entity. If the target exists, its coordinates drive the angle. If it does not exist and `fallbackAngle` is present, the fallback is used.
4. If none of those yields a facing angle and the actor has a path, the path heading can become the desired orientation.
5. Otherwise the prior desired orientation is retained.

When `snap` is set, `classEB.method3820` may copy desired orientation into current orientation immediately when the actor's turn rate is positive.

After desired-facing resolution and movement/safety-reset work, `classKN.method6539` owns the ordinary current-orientation turn step. It advances current orientation by `turnRate` toward desired orientation with 2048-angle wrap handling and can select the authored idle turn-left/right pose. Cluster 03 owns those symbols and remains the source of their mappings.

## Target cleanup behavior

`Actor.field1473` remains deliberately unnamed. Its **consumer behavior** is exact:

- in `classEB.method3820`, if an entity target can no longer be resolved, no fallback angle is available, and `field1473` is set, the actor clears retained entity-facing state and then clears the flag;
- in `classKN.method6539`, when current orientation has reached desired orientation, the same flag causes retained target state to be cleared and the flag reset;
- `Actor.method3248` performs the target-state reset through `classBZ.method1388`;
- actor construction and broader reset helpers initialize/reset the flag to `false`.

The focused writer audit checked the direct exact-240 actor/synchronization path and the semantic top-level dispatcher rather than inferring from old-client names:

- `Actor` contains initialization/reset-to-false and consumer-side clearing, but no `true` assignment;
- `Player` and `NPC` contain no direct assignment;
- `classDF` and `classDC` contain no direct assignment while managing player synchronization/facing propagation;
- `classAS` contains no direct assignment while decoding NPC synchronization;
- `classGA` contains no direct assignment while projecting NPC composition;
- `classEB` and `classKN` consume the latch and clear it, but do not arm it;
- an exact-field search of the semantic `client.java` blob found `field1473` only in the same consumer/clear-to-false paths and found no `field1473 = true` assignment.

The RuneLite interaction-notification path is separate: `Actor.method3285` marks `Actor.field1481` pending event flags, and `Actor.method3224` invokes that hook before copying `classBZ`. It does not arm `field1473`.

This is enough to stop treating the latch writer as a productive naming-wave search target. It does **not** prove that the writer never exists elsewhere in the archive, nor whether the missing provenance is stock, injected, indirect, or a decompilation artifact. Keep `Actor.field1473` stable and unnamed. Re-open the writer census only if Rust implementation/parity evidence makes the latch behavior observable and necessary.

## Turn-rate source boundary

The same audit tightened the turn-rate ownership boundary:

- `Actor.field1470` initializes to semantic `32`;
- `classGA.method5325` projects `NPCComposition.field5297` into the NPC actor;
- `NPCComposition.field5297` is authored by opcode `103`, with decode default `32`;
- `classAS.method661` calls `classGA.method5325` and only reacts to a resulting zero rate by setting current orientation to zero; it does not author another rate;
- `Player`, `NPC`, `classDF`, and `classDC` contain no direct turn-rate writer in the inspected sources.

For the Rust port, cache-derived NPC policy may override the actor default, including with zero. Packet-local movement/facing decoders should not synthesize a second turn-rate source.

## Lifecycle and ordering

`classBZ` is embedded in actor/player-info state rather than allocated for every facing update. `classDC.method2891` resets it while resetting the durable player-info slot. Packet decoding mutates the stored intent; `classEB.method3820` later resolves that intent into `Actor.field1493` desired orientation during actor-cycle processing.

The relevant runtime order is:

```text
packet decode -> retained facing intent + apply policy
classEB.method3820 -> policy-gated desired orientation
Actor.method3284 / method3280 -> movement + safety reset
classKN.method6539 -> ordinary current-orientation turn step + aligned cleanup
classKB.method6491 -> animation advancement
```

Packet-time facing intent, policy eligibility, desired-orientation resolution, and current-orientation mutation are separate phases.

## RustClient ownership

Do not port `ActorFacingState` one-for-one as a Java-shaped object.

- Header bits, smart reads, enum ordinals, and sentinels belong in `protocol::openrune_240`.
- Normalized facing intent, `FacingApplyPolicy`, target identity, explicit angle/area, fallback state, and proven cleanup behavior belong in `game-state` actor semantics.
- Target lookup and ordinary turn stepping are semantic `GameState` work.
- NPC definition-authored turn rate belongs in cache-derived policy.
- Scene/renderer consumes committed current orientation and must not own facing authority.

A useful semantic shape is a typed facing intent (`None | Angle | Area | Entity`) plus a separate apply policy and snap/control state. Keeping policy separate prevents movement-gating rules from leaking back into protocol bytes or renderer logic. Do not add a guessed Rust boolean corresponding to `Actor.field1473` until the arming semantics become observable or otherwise proven.

## Injection-only separation

RuneLite API methods such as `Actor.getInteracting()` and `Actor.isInteracting()` expose this state to plugins. `Actor.method3285`/`field1481` also implement injected interaction-change notification. Those are observability/event surfaces and are distinct from the unresolved stock-style cleanup latch behavior; they are not evidence that plugin callbacks should own semantic facing state in RustClient.

## Client quirks and cautions

- `classBZ.copyFrom` copies only the payload selected by the source mode. Do not normalize it into an unconditional struct copy without proving stale inactive fields are irrelevant.
- Area width/height are packed as four-bit values. Preserve the encoded values rather than silently forcing a minimum dimension.
- A missing entity target does not automatically mean "face nothing"; `fallbackAngle`, cleanup-pending state, path heading, and retained desired orientation participate in the fallback chain.
- `BYPASS_MOVEMENT_GATE` is intentionally a behavioral name. It does not claim the original Jagex domain term for `classLV.field4233`.
- Do not duplicate `classKN.method6539` or the Actor turn fields into this mapping shard; cluster 03 already owns them.

No ambiguous Vineflower labelled-break or collapsed-switch construct was needed for these conclusions, so a secondary decompiler was not required for this shard.

## Handover

- Full facing-target retention: preserve entity/area/apply-policy/control state through the protocol-to-GameState boundary instead of collapsing everything to an optional angle.
- `Actor.field1473`: keep the stable symbol and proven consumer behavior, but **do not spend another naming wave on its arming writer** unless implementation/parity evidence makes it necessary.
- Turn rate: constructor/default + NPC opcode-103 definition projection are established for the normal actor path; widen the write-site census only if another reachable subsystem exposes a mutation.
- Ordinary smooth turning itself is **closed as a source-location question**: `classKN.method6539` is the exact owner.
- Next shared-runtime priority is action-sequence advancement/completion, followed by actor spot-animation lifecycle.

## Verification

Not run in this session by explicit user instruction. No `verify.sh`, tests, Clippy, CI, compilation, or build gate was executed.