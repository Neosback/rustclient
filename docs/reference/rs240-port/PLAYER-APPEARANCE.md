# Revision 240 player appearance and model-cache contract

Status: source-specified handoff for later Rust implementation.

Canonical source evidence:

- `docs/reference/client-240/clusters/12-player-appearance.md`
- `docs/reference/client-240/mappings/clusters/12-player-appearance.tiny`
- `docs/reference/client-240/ledger/clusters/12-player-appearance.tsv`
- `docs/reference/rs240-port/SYMBOL-LEDGER.tsv`

This document is the port-facing contract. Stable obfuscated symbols remain evidence anchors; the Rust design should preserve behavior without recreating the Java ownership graph.

## Architecture boundary

### Protocol

Owns the revision-240 player appearance wire representation:

- base equipment values;
- body colors;
- gender;
- transformed-NPC sentinel/value;
- the 16-bit per-equipment customization mask;
- the mask high-bit force-invalidation control;
- slot-specific `ColorTextureOverride` payload decoding.

Protocol should normalize those bytes into one semantic appearance update and should not own a second persistent player appearance.

### GameState

Owns authoritative player appearance:

```text
PlayerAppearance
  equipment[12]
  colors[5]
  gender
  transformed_npc_id
  per_slot_item_overrides[12]
```

A generation/revision counter may be useful for downstream invalidation, but the scene cache itself is not semantic authority.

### Cache

Owns immutable definition/model dependencies:

- `KitDefinition` (`classOF`), loaded from definition archive group `3`;
- item wearable body/head model inputs;
- NPC definitions used by transformed players;
- immutable model assets.

### Scene/model projection

Owns:

- deterministic appearance signature/key generation;
- reusable unanimated base-player-model cache;
- last-successful-model fallback key;
- animation-time equipment substitutions;
- body and head/chathead ModelData composition;
- animation application after reusable base-model retrieval/build.

### Renderer

Consumes prepared model state and owns GPU resources/draw submission only. It does not own player appearance, cache invalidation, asset-readiness fallback, or animation equipment substitution policy.

## Equipment value domains

Revision 240 uses one integer array with three semantic ranges:

```text
0                 empty slot
256 .. 2047       kit id = value - 256
>= 2048           item id = value - 2048
```

Do not erase these distinctions before definition lookup.

## KitDefinition

Cluster 12 names `classOF` as `KitDefinition`.

Definition decode relevant to player appearance:

```text
opcode 1       body-part selector
opcode 3       non-selectable flag
opcode 5       body model ids
opcode 40      recolor source/destination pairs
opcode 41      retexture source/destination pairs
opcodes 70..79 head model ids
```

Gender-aware matching is exact:

```text
if gender == 1:
    definition.body_part_id == requested_part + 7
else:
    definition.body_part_id == requested_part
```

Default/cycled kit selection skips definitions marked non-selectable and chooses a definition matching the requested body part.

Body and head model arrays are independent and must stay independent in the Rust cache model.

## Per-equipment customization

`Player.method2615` decodes the persistent per-slot customization array supplied to `PlayerComposition`.

From the 16-bit appearance customization mask:

```text
force_invalidate = ((mask >> 15) & 1) == 1
```

When the value is positive and not exactly `32768`, the stock client allocates twelve possible `ColorTextureOverride` entries and decodes an entry for each indicated equipment slot.

This mechanism is persistent appearance input. It is unrelated to cluster-11 temporary actor tint/transparency effects.

## ColorTextureOverride behavior

The API map already establishes the recolor/retexture destination arrays.

For an item's authored recolors, the effective destination array is replaced by the per-slot override only when:

```text
override destinations != null
&& override destination length == authored destination length
```

Otherwise the item-authored destinations remain intact.

The same length-equality rule applies to retextures.

There is no partial zip/truncation rule for mismatched lengths.

`ColorTextureOverride` can also replace gender-specific item model IDs. Body-model and head-model replacement channels are separate.

## Base appearance signature

`PlayerComposition.method6742` recomputes the revision-240 appearance hash in this conceptual order:

```text
for each of 12 equipment values:
    hash all four bytes

for each non-null per-slot ColorTextureOverride:
    for each replacement color short:
        hash high byte
        hash low byte

    for each replacement texture short:
        hash high byte
        hash low byte

for each of 5 body-color indices:
    hash the color index

hash gender
```

The source uses the client's CRC-style lookup table.

The inspected base signature does not directly mix:

- transformed NPC id;
- `PlayerComposition.field4091`;
- the extra override body/head model replacement IDs.

A Rust key does not need to copy the exact Java hash implementation internally, but its lookup/invalidation behavior must be observationally equivalent. Do not silently change cache reuse semantics by adding unrelated inputs unless the corresponding lifecycle behavior is preserved deliberately.

## Cache invalidation

Let `old` be the previously stored base appearance hash and `new` the newly computed hash.

The old cached base-player-model entry is evicted exactly when:

```text
(old != 0 && old != new)
|| force_invalidate
```

The force flag therefore matters even when the semantic hash does not change.

In the source, the reusable player base-model cache is a `NodeCache` with capacity `260`.

This cache is derived state. Dropping it must never mutate GameState player appearance.

## Full player model selection

`PlayerComposition.method6744` is the main full-player model path.

### Transformed player

First:

```text
if transformed_npc_id != -1:
    use NPCComposition model path
```

The ordinary player appearance cache is bypassed.

### Animation-time equipment replacement

The requested model normally uses the base appearance key and authoritative equipment array.

The primary animation may temporarily replace two equipment slots. Revision-240 source indices are:

```text
classOV.field5158 index = 5
classOV.field5162 index = 3
```

The source:

1. copies the 12-entry equipment array;
2. changes only the temporary copy;
3. derives a temporary cache key from the base key and replacement differences;
4. leaves `PlayerComposition.equipmentIds` unchanged.

The corresponding source key shifts are `40` and `48` bits.

Rust should model this as scene/model projection, not as a GameState equipment mutation.

## Resource-readiness gate

On a desired cache miss, every effective slot is checked before a new base model is assembled.

Kit slot:

```text
KitDefinition.bodyModelsReady()
```

Item slot:

```text
ItemComposition.method8706(gender, per_slot_override)
```

If any required model resource is unavailable, the stock client does not necessarily return no model.

## Last-successful-model fallback

`PlayerComposition.field4105` retains the key of the last successfully constructed player base model.

When a newly requested appearance cannot be built because resources are not ready:

```text
if last_successful_key != -1:
    try base_model_cache[last_successful_key]

if no fallback model exists:
    return null
```

Therefore an actor can keep displaying its previous successfully built appearance while new assets are loading.

This is a parity requirement, not merely a loading optimization.

## Base model construction

When resources are ready and the desired key is absent, the client assembles ModelData from the effective twelve equipment slots.

Kit slot:

```text
KitDefinition.getBodyModelData()
```

Item slot:

```text
ItemComposition.method8709(gender, per_slot_override)
```

The item path can apply:

- gender-specific body model selection;
- per-slot replacement model ID;
- authored vertical offset;
- recolor overrides;
- retexture overrides.

After combining the slot ModelData, the player composition applies its five body-color selections through the two authored player recolor palettes.

The resulting ModelData is lit into a reusable base Model and inserted into the appearance cache under the desired key. Only then is the last-successful key updated.

## Animation happens after base-model caching

The cached model represents reusable unanimated appearance geometry.

After lookup/build/fallback:

```text
no active animation:
    copy base model

primary + secondary:
    apply combined animation path

primary only:
    apply primary animation

secondary only:
    apply secondary animation
```

Do not cache a frame-specific posed player model as if it were the base appearance model.

## Head/chathead construction is separate

Head/chathead ModelData does not use the full-body cached Model as its authority.

For kit slots:

```text
KitDefinition.headModelsReady()
KitDefinition.getHeadModelData()
```

For item slots:

```text
ItemComposition.method8714(gender, override)
ItemComposition.method8716(gender, override)
```

Then the same five player body-color selections are applied to the combined head ModelData.

A transformed player delegates this path to the transformed NPC definition.

## Mutation path

Stock-shaped player appearance mutation routes back through the PlayerComposition setup/refresh family and eventually recomputes the appearance signature and invalidation state.

Examples include:

- network appearance updates;
- equipment changes;
- kit cycling;
- body-color changes;
- gender changes.

RuneLite-injected convenience methods such as `createColorTextureOverride`, `removeColorTextureOverride`, and API `setHash()` are not semantic authority. In particular, the create/remove convenience helpers directly mutate the override array; the injected `setHash()` surface explicitly invokes the stock-shaped hash refresh helper afterward.

## Rust implementation invariants

The later port must preserve at least these observable rules:

1. GameState player appearance is authoritative; model caches are derived.
2. Empty, kit and item equipment ranges remain distinguishable.
3. Kit body/head models are separate.
4. Kit body-part matching is gender-sensitive with the revision-240 `+7` rule.
5. Per-slot item overrides stay attached to their slot.
6. Override recolor/retexture destination arrays apply only on exact destination-length match.
7. Base appearance invalidation includes equipment, override recolor/retexture destinations, five colors, gender, and the explicit force-invalidate path.
8. Transformed NPC players bypass ordinary player base-model assembly.
9. Animation equipment replacements are temporary projection inputs and never mutate authoritative equipment.
10. Unready desired assets reuse the last successfully built cached model when available.
11. The cache stores unanimated base appearance geometry; posing happens afterward.
12. Head/chathead composition remains independent from the full-body base-model cache.

## Next research wave

The player appearance/model-cache boundary is sufficiently specified for this naming phase.

Next P0 work should trace the world-effect runtime in one bounded cluster:

1. `GraphicsObject` spawn parameters, delayed start, sequence advancement and removal;
2. `Projectile` spawn parameters, source/end cycles, trajectory setup, retargeting, orientation and sequence advancement;
3. exact zone/server packet feeds for both;
4. GameState versus scene-runtime ownership;
5. stock lifecycle versus RuneLite event/API observation.

After that, return to region rebuild/base-shift and remaining scene/model dependencies.
