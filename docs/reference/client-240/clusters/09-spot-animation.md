# 09 - actor spot-animation lifecycle

Status: **specified** for revision-240 actor-attached spot-animation slot replacement, delayed start, sequence advancement, loop-boundary removal and the definition/model dependency used by `ActorSpotAnim`. Rendering parity and every packet primitive name are not claimed by this shard.

## Scope and ownership

This cluster owns only `classOR`, the previously-unmapped spot-animation definition family.

The following classes are read-only evidence because they are already owned elsewhere or are mixed-purpose containers:

- `ActorSpotAnim` is already seed/API-owned by cluster 00 (`id`, `height`, `startCycle`). This cluster does **not** duplicate that class in its Tiny shard.
- `Actor` is owned by the actor-motion/runtime work. Its spot table/count and add/clear helpers are handover evidence only.
- `classQR`, `classRD` and `classKB.method6491` belong to the sequence-scheduler work.
- `classDF` / `classDC` and `classAS` are player/NPC synchronization owners.
- `classAI` is a mixed-purpose class. Only `classAI.method367` is cited here as the runtime spot-definition lookup used by `ActorSpotAnim`.

This ownership split matters because the Rust port needs one semantic actor effect store, not another copy introduced by the renderer or packet decoder.

## Mapping

| Stable symbol | Readable name | Evidence |
| --- | --- | --- |
| `classOR` | `SpotAnimationDefinition` | archive-group-13 definition whose sequence id is loaded by `ActorSpotAnim` and whose model is projected by `method8757` |
| `classOR.field5086` | `id` | constructor definition id; `method8760` uses it as the model-cache key |
| `classOR.field5082` | `modelId` | opcode-3 destination; `method8760` passes it to `ModelData` loading |
| `classOR.field5081` | `sequenceId` | opcode-2 destination, default `-1`; `ActorSpotAnim` constructor feeds it into `classQR` |
| `classOR.method8753` | `getSpotAnimationDefinition` | definition cache lookup and archive-group-13 decode |
| `classOR.method8757` | `getModel` | base model + optional sequence frame + authored scale/rotation |
| `classOR.method8760` | `getBaseModel` | cached unanimated model build with recolor/retexture/light transforms |

All rows are T4 behavior-derived names from the exact revision-240 source. `sequenceId`, `modelId`, `getModel` and `getBaseModel` are direct enough for confidence A; the class/id naming remains confidence B because no independent API getter names the definition class itself.

## Actor slot representation

`Actor` constructs `field1467` as an `IterableHashTable(4)` and initializes `field1445` to zero. Exact runtime behavior shows:

- the hash-table key is the **spot slot**, not the spot-animation definition id;
- multiple actor spot effects can therefore coexist in distinct slots;
- `field1445` tracks the number of inserted active slot nodes and is incremented/decremented on insert/removal;
- broad actor spot clears unlink every `ActorSpotAnim` and reset the count to zero.

Do not reduce this to one `optional_graphic_id` in RustClient. The semantic shape is a keyed collection of actor-attached effects.

## Stock add/replace semantics

`Actor.method3148(slot, id, height, delay, control, ...)` is the exact stock mutation point used by both player and NPC synchronization.

Its order is significant:

```text
startCycle = clientCycle + delay
existing = spotSlots[slot]

if existing exists:
    unlink existing
    activeSpotCount -= 1

if id != 65535 and id != -1:
    spotSlots[slot] = ActorSpotAnim(
        id,
        height,
        startCycle,
        actor,
        control
    )
    activeSpotCount += 1

post injected GraphicChanged observation
```

Consequences:

1. Replacement is atomic at the slot level: the old effect is unlinked before the new sentinel/id is interpreted.
2. Both `65535` and `-1` leave the slot empty after any existing entry has been removed.
3. The delay is relative to the authoritative client cycle at application time.
4. Height and delay are independent semantic values even though the wire packs them together in several mask families.
5. The boolean/control bit is retained on `ActorSpotAnim`; it is not a packet-only flag.

`Actor.method3146` and `method3147` are structurally equivalent stock helpers without the injected callback wrapper seen in `method3148`; they confirm the same slot/id/height/start-cycle/control representation.

## Player update path

The exact player extended-info path in `classDF.method3050` has a spot-animation block under mask bit `32`.

It decodes:

```text
count = PacketBuffer.method13218(...)
repeat count times:
    slot   = PacketBuffer.method13224(...)
    id     = PacketBuffer.method13250(...)
    packed = classXY.method13113(...)
    control = PacketBuffer.method13225(...) == 1

    height = packed >> 16
    delay  = packed & 65535

    classDC.method2979(slot, id, height, delay, control, ...)
```

`classDC.method2979` fans the semantic update to each represented `Player` and calls `Actor.method3148`.

The stable primitive names are intentionally retained here instead of guessing byte-transform names from call shape. The protocol adapter should use the canonical revision-240 buffer/framing table when these reads are implemented.

## NPC update paths

`classAS.method661` contains two exact mask families that feed the same `Actor.method3148` semantic operation.

### NPC mask bit `2`

```text
count = PacketBuffer.method13225(...)
repeat count times:
    slot   = PacketBuffer.method13095(...)
    id     = PacketBuffer.method13099(...)
    packed = PacketBuffer.method13295(...)
    control = PacketBuffer.method13218(...) == 1

    height = packed >> 16
    delay  = packed & 65535

    npc.method3148(slot, id, height, delay, control, ...)
```

### NPC mask bit `4194304`

```text
count = PacketBuffer.method13218(...)
repeat count times:
    slot   = PacketBuffer.method13095(...)
    id     = PacketBuffer.method13254(...)
    packed = PacketBuffer.method13303(...)

    height = packed >> 16
    delay  = packed & 65535

    npc.method3148(slot, id, height, delay, false, ...)
```

These two blocks reach the same semantic mutation but use different wire primitives and differ in whether the control flag is transmitted. Do not normalize their raw decoding into one guessed transform sequence. Normalize only **after** each revision-240 wire form has produced the same typed spot update.

## `ActorSpotAnim` construction

The exact constructor stores:

- spot-animation definition `id`;
- vertical `height` offset;
- absolute `startCycle`;
- owning `Actor`;
- `field5960`, the retained control boolean;
- an embedded `classQR` playback controller.

When `id >= 0`, the constructor resolves the `classOR` definition through `classAI.method367(id, ...)`, reads `classOR.field5081`, and assigns that sequence id to the embedded controller.

Therefore spot-effect identity and animation-sequence identity are separate cache concepts:

```text
SpotAnimationDefinition id
        │
        ├─ modelId
        └─ sequenceId -> Animation definition -> classQR playback state
```

RustClient should preserve that separation. The actor slot should not store a sequence id as if it were the effect id.

## Per-cycle lifetime and advancement

The spot loop lives inside `classKB.method6491` after ordinary pose-controller advancement setup and outside the `!animationsFrozen` pose/action block.

For every stored `ActorSpotAnim`:

```text
if id == -1:
    no stock advancement branch
else if clientCycle < startCycle:
    wait; do not advance
else:
    controller = spot.controller

    if controller has no Animation definition:
        unlink spot
        activeSpotCount -= 1
    else:
        result = classRD.method9980(controller, 1, frameEventSink, ...)

        if result has loop-boundary bit 1:
            if !spot.field5960:
                unlink spot
                activeSpotCount -= 1
```

`classRD.method9980` is already specified by cluster 07. Bit `1` marks reaching the sequence end / loop boundary; bit `2` is terminal-completion state for normal sequence slots.

The spot scheduler intentionally keys removal to **bit 1**, not bit 2. That is a compatibility detail: a normal spot effect with `field5960 == false` is removed at its first loop boundary regardless of the sequence definition's broader max-loop completion policy.

When `field5960 == true`, the outer spot scheduler does not remove the node at that boundary. The exact evidence-safe behavioral description is therefore **retain across loop boundaries**. Because `ActorSpotAnim` is seed-owned, this wave leaves the stable field name `field5960` unchanged rather than creating a second class owner merely to name it.

## Freeze interaction

Actor animation freeze windows do **not** freeze actor spot effects.

`classKB.method6491` suppresses ordinary pose/action advancement while a freeze window is active, but the spot-animation iteration occurs outside that conditional block and still advances eligible started spots.

This should be modeled deliberately in RustClient. A single blanket `actor.animations_frozen => return` would be incorrect.

## Definition/model boundary

`SpotAnimationDefinition` is cache-authored immutable data:

- group 13 provides the definition;
- `modelId` selects the base model data;
- `sequenceId` selects the optional animation definition;
- `getBaseModel` builds/caches the definition model with recolor/retexture/light transforms;
- `getModel(frame, ...)` applies the selected sequence frame and authored scale/rotation.

The Rust ownership boundary should be:

- `protocol::openrune_240`: mask bits, transformed reads, packed height/delay, sentinels;
- `game-state`: actor spot slots, id/height/start cycle/control policy, controller lifetime, replacement/removal;
- `cache`: spot-animation definitions and immutable sequence/model dependencies;
- `scene`: apply the committed effect frame/time and actor-relative transform to model geometry;
- renderer: draw already-prepared geometry only.

Do not let scene/render code decide whether a spot slot still exists.

## Injection-only separation

Several visually related methods are RuneLite observability/convenience surfaces rather than stock semantic authority:

- `Actor.createSpotAnim(...)`, `setGraphic(...)`, `removeSpotAnim(...)`, `clearSpotAnims()` and first-graphic convenience accessors;
- `Actor.method3283(...)`, which posts `GraphicChanged` after the stock slot mutation;
- `ActorSpotAnim` API getters/setters;
- RuneLite callback/event plumbing.

These are useful for understanding exposed state but are not evidence for packet transforms or Rust plugin mutation authority.

## Client quirks and cautions

- Slot replacement unlinks the old node **before** testing `65535`/`-1`.
- Player/NPC wire blocks pack `height` in the high 16 bits and relative `delay` in the low 16 bits.
- The effect does not advance before its absolute `startCycle`.
- The spot loop removes missing-sequence-controller entries once their start cycle is reached.
- Normal removal is keyed to the sequence **loop-boundary** flag, not normal action-sequence terminal bit 2.
- `field5960` changes lifetime semantics and must survive protocol -> GameState normalization where that wire family supplies it.
- Freeze windows suppress pose/action advancement but not spot advancement.
- The effect model applies height relative to the owning actor; height is not a world-space Z coordinate.

## Handover

The actor-attached spot-animation source question is now sufficiently closed for the naming phase:

- keyed multi-slot storage is proven;
- player mask bit `32` and NPC mask bits `2` / `4194304` are tied to the same semantic update;
- slot replacement, sentinels, height/delay split and start-cycle conversion are proven;
- sequence-definition lookup and per-cycle playback are proven;
- first-loop removal versus retain-across-loop-boundary behavior is proven;
- freeze interaction is proven.

Implementation work should use these semantics to replace the current consume/drop behavior before visual tuning. The next actor-runtime research slice should move to **hitsplats and health bars/headbars**, including capacity, replacement priority, expiry and update-mask ordering.

## Verification

Not run in this session by explicit user instruction. No `verify.sh`, tests, Clippy, CI, compilation, mapping merge or build gate was executed.
