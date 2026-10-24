# Cluster 12 — player appearance and model-cache invalidation

Status: specified for revision-240 player appearance inputs, kit-definition selection, per-equipment `ColorTextureOverride[]`, base appearance hashing/invalidation, full-body model cache lookup/build/fallback, animation-time equipment substitutions, transformed-NPC bypass, and the separate head/chathead ModelData path. Rendering/GPU implementation remains out of scope for this naming wave.

## Scope

Owned class in this shard:

- `classOF` → `KitDefinition`

Read-only evidence from already-owned/API-seeded classes:

- `Player`
- `PlayerComposition`
- `ItemComposition`
- `ColorTextureOverride`
- `ModelData`
- `Model`
- `Animation`
- `NPCComposition`
- `NodeCache`
- `classHW`
- `classGB`
- `classON`
- `classOV`
- `classHQ`

`PlayerComposition`, `ItemComposition`, `ColorTextureOverride`, `Player`, `ModelData`, `Model`, `Animation`, `NPCComposition`, and `NodeCache` are seed/API-owned and are not duplicated into this mapping shard. Their stable symbols remain the source citations below.

Focused external referrers for the newly owned `classOF` include `PlayerComposition` for appearance/model assembly, `classGB.method5333` for default kit selection, and `classHW.method6163` for archive/cache lookup. Branch code search does not reliably index the pinned branch-only reference tree, so this is a focused exact-source referrer audit rather than a claimed whole-archive census.

## Rust ownership

| Behavior | RustClient owner |
| --- | --- |
| player appearance packet bytes, override-mask layout and revision-specific sentinels | `protocol` |
| committed equipment IDs, colors, gender, transformed-NPC ID and per-slot item overrides | `game-state` |
| immutable `KitDefinition` / item / NPC definition data and model-resource availability | `cache` |
| deterministic appearance signature and constructed base-model cache | `scene` model/appearance projection |
| animation pose application to a cached/base player model | `scene` |
| GPU resources and drawing | renderer only |
| RuneLite API-only appearance mutation helpers | plugin command/event adapter if supported; never semantic authority |

The base model cache is derived projection state. It must never become a second authoritative copy of player appearance.

## Readable overlay

### `classOF` → `KitDefinition`

| Stable symbol | Readable name | Evidence |
| --- | --- | --- |
| `classOF.field4842` | `bodyPartId` | opcode `1`; `method8444` compares it with appearance body-part index, adding `7` for gender `1` |
| `classOF.field4843` | `bodyModelIds` | opcode `5`; `method8452` loads/combines these models and `PlayerComposition.method6744` uses the result for the full player model |
| `classOF.field4848` | `headModelIds` | opcodes `70..79`; `method8456` loads/combines this separate model set for head/chathead ModelData |
| `classOF.field4849` | `nonSelectable` | opcode `3`; player kit-selection/cycling paths skip definitions when true |
| `classOF.field4844` | `recolorFrom` | opcode `40` source array |
| `classOF.field4845` | `recolorTo` | opcode `40` destination array |
| `classOF.field4846` | `retextureFrom` | opcode `41` source array |
| `classOF.field4847` | `retextureTo` | opcode `41` destination array |
| `classOF.method8444` | `matchesBodyPart` | gender-sensitive body-part match used by default/cycled kit selection |
| `classOF.method8449` | `bodyModelsReady` | checks archive readiness for every `bodyModelIds` entry |
| `classOF.method8452` | `getBodyModelData` | combines body models and applies kit recolor/retexture pairs |
| `classOF.method8453` | `headModelsReady` | checks each non-`-1` `headModelIds` entry |
| `classOF.method8456` | `getHeadModelData` | combines head models and applies kit recolor/retexture pairs |
| `classOF.method8438` | `getKitDefinition` | archive-group-`3` load and 64-entry definition-cache lookup |

## Kit definition decode

`classOF` is the player body-kit definition used for equipment values in the kit range.

Relevant decode opcodes are:

```text
1       -> bodyPartId
3       -> nonSelectable = true
5       -> bodyModelIds[]
40      -> recolorFrom[] / recolorTo[]
41      -> retextureFrom[] / retextureTo[]
70..79  -> headModelIds[opcode - 70]
```

`classHW.method6163` and `classOF.method8438` load definition bytes from archive group `3` and cache decoded definitions.

### Gender/body-part matching

`KitDefinition.matchesBodyPart(part, gender, ...)` is exact:

```text
if gender == 1:
    return bodyPartId == part + 7
else:
    return bodyPartId == part
```

`classGB.method5333` scans kit definitions from low ID upward and selects the first definition that:

```text
definition != null
&& !definition.nonSelectable
&& definition.matchesBodyPart(part, gender)
```

The chosen equipment value is stored as:

```text
256 + kitDefinitionId
```

This establishes the player-equipment ID domains used throughout `PlayerComposition`:

```text
0                 empty
256 .. 2047       kit definition: value - 256
>= 2048           item definition: value - 2048
```

Keep those encoding details out of generic rendering code.

## Player appearance packet handoff

`Player.method2615` is the important appearance decoder feeding `PlayerComposition`.

The already API-mapped persistent appearance fields are:

- `PlayerComposition.field4092` → `gender`
- `field4093` → `equipmentIds`
- `field4095` → `colorTextureOverrides`
- `field4096` → `colors`
- `field4097` → `transformedNpcId`

The source also retains a companion equipment array (`field4090`), a force-invalidation boolean (`field4094`), the current appearance hash (`field4104`) and the last successfully built model key (`field4105`). Those members remain stable symbols because `PlayerComposition` is seed-owned.

### Per-equipment override mask

After base equipment and color values are decoded, `Player.method2615` reads a 16-bit value used for player item customization.

Its high bit is extracted independently:

```text
forceInvalidate = ((mask >> 15) & 1) == 1
```

When the value is positive and is not exactly `32768`, the client allocates a 12-entry `ColorTextureOverride[]`. For each equipment slot whose corresponding mask bit is set, it decodes one `ColorTextureOverride` against the currently equipped item.

The array is then passed into `PlayerComposition.method6719` together with the force-invalidation flag and the remaining appearance state.

Do not confuse this persistent per-equipment customization with cluster-11's temporary actor `ModelColorOverride` or `TransparencyTransition`.

## `ColorTextureOverride` as persistent appearance input

The seed API already names:

- `ColorTextureOverride.field5259` → `colorToReplaceWith`
- `ColorTextureOverride.field5258` → `textureToReplaceWith`

The object can also carry gender-indexed replacement model IDs used by `ItemComposition` body/head model selection. Those additional fields remain generic in this cluster because the class is seed-owned and their exact API/domain names are not required to close the player cache behavior.

### Recolor/retexture behavior

`ItemComposition.method8720(ModelData, ColorTextureOverride, ...)` begins with the item's authored recolor/retexture sources and destinations.

For recoloring:

```text
from = ItemComposition.colorToReplace
into = ItemComposition.colorToReplaceWith

if override.colorToReplaceWith != null
   && override length == authored destination length:
    into = override.colorToReplaceWith

for each authored source index:
    ModelData.recolor(from[i], into[i])
```

Retexturing follows the same rule with the item's authored texture arrays and `override.textureToReplaceWith`.

The length equality is compatibility-sensitive. A non-null override array with the wrong length does not partially replace the item's authored destination array.

### Body/head model substitutions

For full-body item model construction, `ItemComposition.method8709(gender, override, ...)` selects gender-specific authored model IDs and allows the `ColorTextureOverride` to replace the primary body model ID when its corresponding replacement is present. It then combines the available item model parts, applies the gender-specific vertical offset, and applies recolor/retexture customization through `method8720`.

`ItemComposition.method8706(...)` is the corresponding body-resource readiness check used before `PlayerComposition` attempts a new cached full-body model.

The separate head/chathead path uses:

- `ItemComposition.method8714(...)` for model-resource readiness;
- `ItemComposition.method8716(...)` for head ModelData construction;
- a separate pair of gender-specific replacement model IDs from the same `ColorTextureOverride`.

The item body and head model paths therefore must not be represented as one interchangeable model ID.

## Base appearance hash

`PlayerComposition.method6742` recomputes the base appearance hash stored in `field4104`.

The source uses the client's CRC-style lookup table and incorporates data in this exact conceptual order:

```text
for equipment slot 0..11:
    hash all four bytes of equipmentIds[slot]

for each non-null ColorTextureOverride slot:
    for each colorToReplaceWith short:
        hash high byte
        hash low byte

    for each textureToReplaceWith short:
        hash high byte
        hash low byte

for body color index 0..4:
    hash colors[index]

hash gender
```

### What the base hash does not contain

The inspected `method6742` does **not** directly mix:

- `transformedNpcId`;
- the auxiliary `PlayerComposition.field4091` value;
- the additional body/head model-ID replacement fields carried by `ColorTextureOverride`.

That omission is important: do not silently design a "better" Rust cache key and then assume its lifecycle matches revision 240. A Rust implementation may use a typed deterministic key internally, but it must preserve the same invalidation/reuse semantics.

## Exact invalidation rule

Before recomputing, `method6742` retains the old appearance hash.

After computing the new one, the old base-model cache entry is removed when:

```text
(oldHash != 0 && oldHash != newHash)
|| forceInvalidate
```

The force flag is the boolean retained in `PlayerComposition.field4094` from the appearance update path.

Therefore a packet can force cache eviction even when the newly computed hash is unchanged.

The base model cache is `PlayerComposition.field4098`, a `NodeCache` constructed with capacity `260`.

## Full player model lookup/build

`PlayerComposition.method6744(Animation primary, frame, Animation secondary, frame, ...)` is the main exact full-player model path used by `Player.vmethod93`.

### Transformed-NPC bypass

First:

```text
if transformedNpcId != -1:
    return NPCComposition model path
```

The ordinary player appearance-model cache is bypassed for transformed players.

This is why `transformedNpcId` does not need to participate in the ordinary player appearance hash to select the correct branch.

### Animation-time equipment substitutions

The initial desired key is the base appearance hash and the initial equipment array is `equipmentIds`.

If the primary `Animation` carries either of its two equipment replacement values, the client:

1. copies the 12-entry equipment array;
2. changes the affected equipment slot(s) only in that temporary copy;
3. derives a temporary cache key from the base key using the difference between replacement and stored equipment values.

The two exact affected equipment indices are:

```text
classOV.field5158.field5152 == 5
classOV.field5162.field5152 == 3
```

The first difference is mixed at bit shift `40`; the second at bit shift `48`.

This is an animation/model-projection override. It does not mutate the authoritative `PlayerComposition.equipmentIds`.

## Resource-readiness gate and last-model fallback

After cache lookup, a missing desired model does not immediately trigger construction.

The client first checks every effective equipment slot.

For kit values (`256 <= value < 2048`):

```text
KitDefinition.bodyModelsReady()
```

For item values (`value >= 2048`):

```text
ItemComposition.method8706(gender, slotColorTextureOverride, ...)
```

If any required resource is not ready, revision 240 attempts this fallback:

```text
if field4105 != -1:
    model = baseModelCache[field4105]

if model == null:
    return null
```

`field4105` is updated only after a new desired base model has been successfully built and inserted into the cache.

This means an actor can continue projecting its last successfully built player model while newly requested appearance assets are still unavailable. RustClient must not replace this behavior with an unconditional "missing asset = invisible this cycle" rule.

## Base model construction

When resources are ready and the requested model is still absent from the cache, `method6744` assembles up to 12 ModelData contributions.

For each effective slot:

```text
kit:
    KitDefinition.getBodyModelData()

item:
    ItemComposition.method8709(
        gender,
        colorTextureOverrides[slot]
    )
```

The parts are combined into one `ModelData`.

The client then applies five body-color selections through two authored recolor palettes when each selected color index is in range.

The combined ModelData is lit into a base `Model`, stored under the requested appearance key, and that key becomes `field4105` / the last successfully built model key.

Only after that does the method copy/apply the requested animation state:

```text
no animations:
    copy base model

primary + secondary:
    apply combined animation path

primary only:
    apply primary

secondary only:
    apply secondary
```

The cache therefore stores the reusable unanimated appearance model, not a frame-specific posed model.

## Kit full-body ModelData

`KitDefinition.getBodyModelData()`:

1. loads each `bodyModelIds` entry;
2. combines them (or uses the single model directly);
3. applies every authored recolor pair;
4. applies every authored retexture pair.

`KitDefinition.bodyModelsReady()` checks the same model IDs against the model archive before full player-model construction.

This definition data belongs to `cache`; the assembled player appearance model remains derived scene/model state.

## Separate head/chathead path

Player head/chathead ModelData does not simply reuse the cached full-body `Model`.

The PlayerComposition head path independently checks resources and assembles ModelData:

```text
kit slot:
    KitDefinition.headModelsReady()
    KitDefinition.getHeadModelData()

item slot:
    ItemComposition.method8714(gender, override)
    ItemComposition.method8716(gender, override)
```

It then applies the same five player body-color selections to the combined ModelData.

If `transformedNpcId != -1`, the head path delegates to the transformed NPC definition instead.

Do not make the full-body `NodeCache(260)` entry the semantic source for chathead construction.

## Appearance mutation and hash refresh

Stock-shaped appearance mutation paths funnel back through `PlayerComposition.method6722` / `method6754` and ultimately `method6742` so the appearance hash and cache eviction policy stay synchronized.

Examples include:

- equipment/item changes;
- kit cycling;
- body-color changes;
- gender changes;
- full appearance decode through `method6719`.

### RuneLite API-only helpers

The source also contains injected API helpers without an `@ObfuscatedName`, including:

- `createColorTextureOverride(...)`;
- `removeColorTextureOverride(...)`;
- `setHash()`.

The create/remove helpers directly mutate the override array and do not themselves run the hash-refresh routine. The API `setHash()` explicitly calls the stock-shaped hash recomputation helper.

Do not use these injected convenience methods as evidence that stock packet mutation skips invalidation.

## Copy behavior

The `PlayerComposition(PlayerComposition)` copy constructor copies:

- equipment arrays;
- color array;
- the 12-entry `ColorTextureOverride[]` container.

The override array is copied with `Arrays.copyOf`, so its element objects are not deep-cloned by this constructor.

This is a source quirk worth preserving only if an equivalent copy surface becomes observable in RustClient; it is not a reason to expose mutable shared override objects to plugins.

## Exact ownership model for RustClient

A suitable semantic split is:

```text
GameState::PlayerAppearance
    equipment slots
    body colors
    gender
    transformed NPC id
    per-slot semantic color/texture/model overrides
    appearance revision/change signal if needed

Cache
    KitDefinition
    ItemDefinition appearance inputs
    NPC definition/model inputs
    immutable model assets

Scene::PlayerModelProjection
    deterministic appearance key
    reusable unanimated base-model cache
    last-successful-model fallback key
    animation-time slot substitutions
    body/head ModelData composition
    pose application

Renderer
    GPU upload/resources
    draw submission
```

`GameState` remains the authority. The appearance cache may be dropped and rebuilt without changing semantic player appearance.

## Compatibility requirements

Preserve these exact behaviors:

- equipment values use empty / kit / item ranges rather than one undifferentiated ID namespace;
- selectable kit matching depends on both body part and gender, with `+7` for gender `1`;
- kit body and head model arrays are separate;
- per-equipment item overrides are slot-specific;
- override destination recolor/retexture arrays replace authored destinations only when lengths match;
- the base appearance hash includes equipment, override recolor/retexture values, five colors and gender;
- the base hash does not directly include transformed NPC ID or override replacement model IDs;
- old cached model is evicted on a changed nonzero hash or explicit force-invalidate flag;
- animation equipment substitutions derive a temporary model key and do not mutate stored equipment;
- unavailable requested assets fall back to the last successfully built cached appearance when possible;
- cached base models are unanimated and animation posing happens after cache retrieval/build;
- transformed-NPC model generation bypasses ordinary player model-cache assembly;
- head/chathead ModelData uses its own readiness/build path.

## Deliberately unresolved / left generic

- `PlayerComposition.field4090` is behaviorally a retained companion/baseline equipment array, but this seed-owned member is left as a stable symbol rather than renamed here.
- `PlayerComposition.field4091` is not required to close the full-body cache contract and remains generic.
- `PlayerComposition.field4094` is behaviorally the force-invalidation flag; it remains seed-owned and is documented rather than mapped.
- `PlayerComposition.field4104` and `field4105` are behaviorally the current appearance hash and last successful model key; they remain seed-owned and are documented rather than duplicated into this shard.
- the additional `ColorTextureOverride` replacement-model fields remain generic; their body/head selection behavior is specified without guessing public names.
- `classOV` slot constants are cited by exact numeric index where needed instead of assigning a broader equipment-slot enum mapping in this slice.

## Injection separation

Stock/revision behavior:

- player appearance packet decode;
- `PlayerComposition` semantic state assignment;
- hash recomputation and cache eviction;
- kit/item model readiness;
- base model construction and fallback;
- NPC-transform and animation-equipment model selection.

RuneLite/injected convenience behavior:

- `PlayerComposition.createColorTextureOverride`;
- `removeColorTextureOverride`;
- API `setHash` surface and other direct plugin mutation helpers.

RustClient plugin APIs, if added later, should issue validated appearance/debug commands or observe committed snapshots. They should not receive the mutable model cache or internal appearance arrays.

## Handoff

The core actor/player model invalidation boundary is now sufficiently specified for this research phase.

Next high-value P0 source trace:

1. `GraphicsObject` lifecycle, sequence assignment/advancement and removal;
2. `Projectile` spawn parameters, target retargeting, trajectory math, orientation and sequence lifetime;
3. exact zone-packet feeds for both world effects;
4. then region rebuild/base-shift and remaining scene/model dependencies.

No Rust implementation should begin from class names alone. Port the behavior into the owning semantic/cache/scene boundaries above.
