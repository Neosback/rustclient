# 08 - actor facing state

Status: **specified** for the revision-240 facing-state representation and target classification only. This does not claim the full turning scheduler is implemented or verified.

## Scope

This cluster owns `classBZ`, `classLU`, and `classLJ`. It names the state object carried by `Actor.field1461`, the facing mode, and the entity target kind. `Actor`, `classDC`, and `classEB` are read-only evidence and remain owned by their existing shards.

`classLV` is deliberately left generic. Revision 240 proves that it is selected from the low three bits of the facing header and has two enum values, but the inspected consumers do not yet prove what changes between those values. Naming it would encode a guess.

Observed external referrers during this shard include `Actor`, `classDC`, and `classEB`.

## Mapping

| Stable symbol | Readable name | Evidence |
| --- | --- | --- |
| `classBZ` | `ActorFacingState` | `Actor.field1461`; `classEB.method3820` resolves it into desired orientation |
| `classBZ.field624` | `mode` | `Actor.method3162`, `method3165`, `method3163` classify angle/area/entity modes |
| `classBZ.field622` | `snap` | `Actor.method3236`; `classEB.method3820` snaps current orientation to desired orientation when allowed |
| `classBZ.field623` | `targetKind` | `classEB.method3820` chooses NPC/player/world-entity storage from it |
| `classBZ.field631` | `targetIndex` | `Actor.method3252`; lookup key in `classEB.method3820` |
| `classBZ.field633` | `fallbackAngle` | used by `classEB.method3820` when the entity target is absent |
| `classBZ.field627` | `angle` | explicit-angle branch consumed by `classEB.method3820` |
| `classBZ.field628` | `areaX` | area-facing origin X |
| `classBZ.field630` | `areaY` | area-facing origin Y |
| `classBZ.field629` | `areaWidth` | area-facing width |
| `classBZ.field632` | `areaHeight` | area-facing height |
| `classBZ.method1378` | `reset` | restores the default mode, target and sentinels |
| `classBZ.method1380` | `copyFrom` | copies the payload appropriate to the source mode |
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

All rows are T4/B: exact revision-240 behavior is clear from the client, but this cluster does not add an independent RSProt facing-layout cross-check.

## Wire shape

`Actor.method3231(header, PacketBuffer)` delegates to `classBZ.method1384` after the actor-level hook. The stock semantic state decoder interprets the header as:

- bits `0..2`: a `classLV` value. Meaning intentionally unresolved.
- bits `3..5`: `FacingMode` through the enum lookup table.
- bit `6`: `snap`.

The payload depends on `FacingMode`:

- `ANGLE`: one `readSmart1or2` value into `angle`.
- `NONE`: no facing payload.
- `AREA`: `areaX`, `areaY`, then one `readSmart1or2` whose low nibble is `areaWidth` and high nibble is `areaHeight`.
- `ENTITY`: target kind via `readSmart1or2`, target index via `readSmart2or4Null`, then `fallbackAngle` via `readSmart1or2`.

Those primitive names are already established by the buffer/framing shard. The enum lookup uses each enum's ordinal contract; do not replace it with assumptions about Java array position.

## Runtime semantics

`classEB.method3820` resolves a desired orientation in this order:

1. An explicit `ANGLE` can produce a one-shot desired angle. The state is cleared after consumption.
2. An `AREA` computes the center of the supplied world-tile rectangle, converts it to local fine coordinates relative to the current `WorldView`, and derives an angle toward that center.
3. An `ENTITY` resolves the target kind as NPC, player, or world entity. If the target exists, its coordinates drive the angle. If it does not exist and `fallbackAngle` is present, the fallback is used.
4. If none of those yields a facing angle and the actor has a path, the path heading can become the desired orientation.
5. Otherwise the prior desired orientation is retained.

When `snap` is set, `classEB.method3820` may copy desired orientation into current orientation immediately when the actor's turn rate is positive. This flag is therefore semantic actor state, not a rendering-only concern.

The target-kind values are exact from storage selection in `classEB.method3820`:

- `NONE`: no entity target.
- `NPC`: `WorldView.field1696`.
- `PLAYER`: `WorldView.field1692`.
- `WORLD_ENTITY`: `WorldView.field1698`.

## Lifecycle and ordering

`classBZ` is embedded in actor/player-info state rather than allocated for every facing update. `classDC.method2891` resets it while resetting the durable player-info slot. Packet decoding mutates the stored intent; `classEB.method3820` later resolves that intent into `Actor.field1493` desired orientation during actor-cycle processing.

This separation matters for the Rust port: packet-time facing intent and client-cycle orientation mutation are different phases.

## RustClient ownership

Do not port `ActorFacingState` one-for-one as a Java-shaped object.

- Header bits, smart reads, enum ordinals and sentinels belong in `protocol::openrune_240`.
- Normalized facing intent, target identity, explicit angle/area and fallback state belong in `game-state::entities` / actor movement semantics.
- Target lookup is semantic `GameState` work.
- Scene/renderer code consumes committed actor orientation and must not own facing authority.

A useful Rust shape is a typed facing intent enum such as `None | Angle | Area | Entity`, with the low-three-bit behavior retained separately until its meaning is proven.

## Injection-only separation

RuneLite API methods such as `Actor.getInteracting()` and `Actor.isInteracting()` expose this state to plugins. They are observability surfaces, not evidence that plugin callbacks or injected setters should own the semantic state in RustClient.

## Client quirks and cautions

- `classBZ.copyFrom` copies only the payload selected by the source mode. Do not normalize it into an unconditional struct copy without proving stale inactive fields are irrelevant.
- Area width/height are packed as four-bit values. Preserve the encoded values rather than silently forcing a minimum dimension.
- A missing entity target does not automatically mean 'face nothing'; `fallbackAngle`, actor cleanup state, and path heading participate in the fallback chain.
- The `classLV` value is intentionally unresolved. Do not infer names such as tracking/locked/forced from its two values yet.

No ambiguous Vineflower labelled-break or collapsed-switch construct was needed for these conclusions, so a secondary decompiler was not required for this shard.

## Handover

- `classLV`: determine the semantic difference between its two values before naming it or `classBZ.field625`.
- Existing actor-motion ownership: `classEB.method3820`, Actor desired/current orientation, turn rate, directional pose selection and target-disappearance cleanup remain outside this shard.
- Next facing work should trace the ordinary turn mutator after `classEB.method3820`, especially turn-rate stepping and turn-left/right/walk pose selection.

## Verification

Not run in this session by explicit user instruction. No `verify.sh`, tests, Clippy, CI or build gate was executed.
