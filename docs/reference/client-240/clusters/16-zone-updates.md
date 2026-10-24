# 16 - zone update types and map-animation spawn

Status: **specified** for the named revision-240 zone-update markers and the stock graphics-object spawn/lifecycle path reached by `MAP_ANIM` and `MAP_ANIM_SPECIFIC`. Other `classJC` markers remain generic until their exact packet identities are established.

## Scope and ownership

This cluster owns only `classJC`, a closed family of marker instances passed to `classTI.method10892` to select a zone-local decoder.

Read-only evidence used by this cluster:

- `client.java` for exact server-packet dispatch and the `MAP_ANIM_SPECIFIC` handler;
- `classTI.method10892` for the generic zone-update decoder;
- `GraphicsObject` for effect construction and animation lifetime;
- `classKY` / cluster 13 for local tile-center conversion;
- `classQR` / cluster 07 and `classRD.method9980` for sequence playback;
- `classOR` / cluster 09 for spot-animation definition/model lookup;
- `docs/reference/client-240/inputs/server-packets.tsv` for exact revision-240 packet IDs and lengths.

`GraphicsObject` is already class-owned by seed/API cluster 00, so it is not duplicated in this Tiny shard.

## Old to new

| Stable symbol | `@ObfuscatedName` | Readable name | Evidence |
| --- | --- | --- | --- |
| `classJC` | `jc` | `ZoneUpdateType` | T4: closed marker family passed to the common zone decoder |
| `classJC.field3119` | `az` | `LOC_ADD_CHANGE` | T2: exact client dispatch from `classJJ.LOC_ADD_CHANGE_V2` |
| `classJC.field3130` | `ab` | `LOC_ANIM` | T2: exact client dispatch from `classJJ.LOC_ANIM` |
| `classJC.field3128` | `ao` | `LOC_DEL` | T2: exact client dispatch from `classJJ.LOC_DEL` |
| `classJC.field3122` | `as` | `LOC_MERGE` | T2: exact client dispatch from `classJJ.LOC_MERGE` |
| `classJC.field3133` | `ad` | `MAP_ANIM` | T2: exact client dispatch plus the 6-byte packet-table length |

The five packet marker names are confidence A because the revision-240 client compares a named `classJJ` protocol constant and immediately calls `classTI.method10892` with that exact `classJC` singleton. The family class name is confidence B because its semantic role is established by behavior rather than an API type name.

## Generic `MAP_ANIM`

The revision-240 server table identifies `MAP_ANIM` as packet id `13`, length `6`. The top-level client dispatch does not decode its payload directly. Instead it calls:

```text
classTI.method10892(ZoneUpdateType.MAP_ANIM, ...)
```

Inside the common zone decoder, the `MAP_ANIM` marker reads the semantic values used to create one `GraphicsObject`:

```text
id           = readUnsignedShortLEAdd(...)
delay        = readUnsignedShortLEAdd(...)
packedTile   = readUnsignedByte(...)
heightOffset = readUnsignedByteNeg(...)

tileX = zoneBaseX + ((packedTile >> 4) & 7)
tileY = zoneBaseY + (packedTile & 7)
```

The decoder rejects the spawn when the resulting tile is outside the active WorldView. Valid tile coordinates are converted to stock local centers:

```text
localX = 64 + (tileX << 7)
localY = 64 + (tileY << 7)
```

The graphics object's vertical coordinate is sampled from terrain and offset by the packet height:

```text
z = tileHeight(worldView, localX, localY, level) - heightOffset
```

The selected level is the current zone/world-view level unless the enclosing zone-update context supplies its explicit level override.

Construction is then equivalent to:

```text
GraphicsObject(
    worldView,
    id,
    level,
    localX,
    localY,
    z,
    delay,
    clientCycle
)
```

and the object is inserted into that WorldView's graphics-object deque.

## `MAP_ANIM_SPECIFIC`

`MAP_ANIM_SPECIFIC` is a separate top-level server packet rather than a `ZoneUpdateType` marker. The revision-240 table identifies packet id `90`, length `8`.

The direct client handler establishes this payload shape:

```text
id           = readUnsignedShortLEAdd(...)
delay        = readUnsignedShortLEAdd(...)
heightOffset = readUnsignedByte(...)
packed       = readUnsignedMedium(...)

baseX = packed >> 16
baseY = (packed >> 8) & 255
tileX = baseX + ((packed >> 4) & 7)
tileY = baseY + (packed & 7)
```

After tile validation, it performs the same local-center conversion, terrain-height subtraction, `GraphicsObject` construction, and WorldView insertion as the generic zone form.

These packet forms should remain separate revision-240 protocol decoders in RustClient. They converge only after each wire form has produced the same typed semantic graphics spawn.

## `GraphicsObject` construction

The stock constructor stores:

- `id`;
- `level`;
- local `x` and `y`;
- world/scene `z`;
- owning `WorldView`;
- `startCycle = clientCycle + delay`;
- one `AnimationSequence` controller.

It resolves `SpotAnimationDefinition(id).sequenceId` and assigns that sequence to the controller. The graphics-object identity is therefore distinct from its animation-sequence identity.

The constructor also invokes `GraphicsObject.method1105`, which posts RuneLite's `GraphicsObjectCreated` event. That callback is **injection-only**. It is not part of stock lifetime authority.

## Per-cycle graphics lifetime

The client iterates each WorldView's graphics-object deque. The stock order is significant:

```text
for each graphics object:
    if object.level != worldView.currentLevel OR object is finished:
        unlink object
        continue

    if clientCycle < object.startCycle:
        retain object
        do not advance
        do not submit to scene
        continue

    advance object animation by clientDelta

    if object became finished:
        unlink object
    else:
        submit object to scene at level/x/y/z
```

`GraphicsObject.method1096` defines finished state as the embedded `AnimationSequence` having no active definition.

`GraphicsObject.method1110` advances the controller through `classRD.method9980`. If the returned result contains bit `1`, meaning the sequence crossed its loop boundary, the object immediately clears its sequence. On the same outer update, that makes it finished and therefore eligible for unlinking before scene submission.

This is deliberately different from actor-attached spot effects, where a retained control flag can allow a slot to survive a loop boundary.

## No-sequence effect behavior

If the referenced spot-animation definition supplies no active sequence, construction leaves the embedded controller without an animation definition. Such a graphics object is considered finished by the outer world-effect loop and is culled rather than maintained as an indefinitely static world effect.

Do not infer from `SpotAnimationDefinition.getBaseModel` that every no-sequence definition persists as a `GraphicsObject`; the runtime lifetime check is authoritative here.

## Region/base rebasing

Region base shifts mutate active graphics objects rather than rebuilding them from packets.

For every graphics object in the affected WorldView:

```text
x -= deltaTileX << 7
y -= deltaTileY << 7
```

The shifted object is removed when either local coordinate falls outside:

```text
0 <= coordinate < 13312
```

where `13312 == 104 * 128`, the full stock scene width in local units.

This proves graphics-object position is semantic transient runtime state that participates in world-base changes. It is not renderer-only placement data.

## RustClient ownership

Recommended split:

- `protocol::openrune_240::zone`: exact packet IDs, transformed reads, packed local tile offsets, level context and height offset;
- `game-state` or a dedicated semantic world-effect owner: graphics identity, WorldView association, position, absolute start cycle, playback/lifetime state;
- `cache`: spot-animation definition, animation definition and immutable model inputs;
- `scene`: consume a committed active graphics effect and prepare/model-place it;
- `renderer`: draw prepared geometry only.

Scene or renderer code must not decide whether the effect has started, completed, crossed a loop boundary, or survived a region-base change.

## Other zone markers

The remaining `classJC` singleton fields are deliberately left generic. Their constructor integer strongly resembles a payload length and several are reachable through the common zone decoder, but a number alone is not sufficient naming evidence. They should be named only when exact `classJJ` dispatch or equally strong protocol evidence identifies them.

## Client quirks and cautions

- `startCycle` is absolute and equals the receive-time client cycle plus packet delay.
- A graphics object exists before `startCycle` but is neither advanced nor submitted to the scene.
- Wrong-level objects are unlinked rather than kept dormant for later plane changes.
- Completion is keyed to common sequence advancement **bit 1**, the first loop boundary, not terminal-completion bit 2.
- A sequence-less graphics object is treated as finished by this runtime path.
- Region shifts rebase active local coordinates and cull objects outside the 104-tile scene.
- `GraphicsObjectCreated` is RuneLite injection-only observation.

## Handover

This cluster closes the packet-to-runtime path for standalone map animations well enough for a later Rust implementation:

```text
MAP_ANIM / MAP_ANIM_SPECIFIC
        -> typed graphics spawn
        -> WorldView graphics collection
        -> delayed start
        -> common sequence advancement
        -> first-loop completion
        -> scene projection
        -> region-base rebasing/cull
```

Projectile world effects use a different top-level packet and trajectory scheduler and remain a separate trace.

## Verification

Not run in this session by explicit user instruction. No `verify.sh`, tests, Clippy, CI, compilation, mapping merge or build gate was executed.
