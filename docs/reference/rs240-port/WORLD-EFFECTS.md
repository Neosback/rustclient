# Revision 240 transient world effects: graphics objects and projectiles

<!-- authority-notice -->
> Domain source/ownership research; implementation statements are bounded to the dates
> recorded below. The [current matrix](PORT-MATRIX.md) and [audit](CURRENT-AUDIT.md)
> supersede older gap/status lists; preserve exact source details and historical evidence.

Status: source-specified handoff for standalone graphics-object spawn/lifetime, projectile spawn/trajectory/retargeting/lifetime, animation progression, WorldView routing and region-base rebasing. No Rust implementation or visual parity is claimed by this document.

## Canonical evidence

Readable-name and behavior clusters:

- `docs/reference/client-240/clusters/09-spot-animation.md` - spot-animation definition/model and actor-attached spot lifetime;
- `docs/reference/client-240/clusters/13-coord.md` - packed `Coord` and tile/local conversions;
- `docs/reference/client-240/clusters/14-vector3.md` - pooled `Vector3f` math primitive;
- `docs/reference/client-240/clusters/15-world-position.md` - entity/tile 3D position resolver;
- `docs/reference/client-240/clusters/16-zone-updates.md` - zone update markers and standalone `GraphicsObject` spawn/lifetime;
- `docs/reference/client-240/clusters/17-sequence-advance.md` - common animation-sequence advancement;
- `docs/reference/client-240/clusters/18-world-views.md` - WorldView registry and coordinate routing.

Primary exact revision-240 source:

- `references/rl-suite-240/sources-semantic/GraphicsObject.java`;
- `references/rl-suite-240/sources-semantic/Projectile.java`;
- `references/rl-suite-240/sources-semantic/client.java`;
- `references/rl-suite-240/sources-semantic/classNG.java`;
- `references/rl-suite-240/sources-semantic/classCL.java`;
- `docs/reference/client-240/inputs/server-packets.tsv`.

## Architecture boundary

### Protocol

`crates/protocol` should own revision-240 packet identity, read transforms, sentinels, packed coordinate fields and relative cycle values. Protocol should normalize bytes into typed spawn/update inputs and then stop owning them.

### GameState / semantic world-effect runtime

One authoritative semantic owner should retain active transient world effects:

- standalone graphics objects;
- projectiles;
- their cycle bounds;
- WorldView/plane association;
- authoritative current positions and trajectory state;
- sequence-playback state needed for lifetime and posing.

Whether that is a dedicated `game-state::world_effects` module or another narrow semantic owner is an implementation choice. It must not be duplicated in scene or renderer state.

### Cache

`crates/cache` owns immutable:

- `SpotAnimationDefinition` data;
- sequence definitions/timing policy;
- models and animation resources.

### Scene and renderer

`scene` consumes committed semantic effect state and prepares the current model/placement. Renderer owns GPU resources and draw submission only.

Scene/render code must not become the authority for start cycles, expiry, trajectory integration, retargeting, loop completion or region rebasing.

## Coordinate conventions

The client mixes RuneScape world/local conventions with a conventional 3D vector primitive. Keep that distinction explicit.

For this document, projectile semantic position is written as:

```text
(px, py, pz)
```

where `px` and `py` are the two horizontal RuneScape local axes and `pz` is vertical height.

`resolveWorldPosition` returns `Vector3f` using its model-space axes:

```text
Vector3f.x -> projectile px
Vector3f.z -> projectile py
Vector3f.y -> projectile pz
```

Do not accidentally swap the second horizontal coordinate and height when replacing the Java vector helper with typed Rust coordinates.

One tile is `128` local units and its center is:

```text
64 + tile * 128
```

## Standalone graphics objects

The exact standalone graphics-object contract is documented in cluster 16. The minimum Rust behavior is:

```text
receive MAP_ANIM or MAP_ANIM_SPECIFIC
    -> decode id, delay, position and vertical offset
    -> validate target tile
    -> convert tile to local center
    -> sample terrain height and subtract height offset
    -> startCycle = clientCycle + delay
    -> resolve SpotAnimationDefinition.sequenceId
    -> insert into owning WorldView graphics collection
```

Per client update:

```text
if wrong current WorldView level or already finished:
    remove
else if clientCycle < startCycle:
    retain without advancing or drawing
else:
    advanceAnimationSequence(delta)
    if loop-boundary bit 1 cleared the sequence:
        remove
    else:
        submit current effect to scene
```

The first sequence loop boundary ends a standalone `GraphicsObject`. This differs from actor-attached spots, whose retained control flag can allow them to survive that boundary.

Region shifts subtract the tile-base delta in local units from graphics-object x/y and remove effects outside `[0, 13312)` on either local horizontal axis.

`GraphicsObjectCreated` is RuneLite injection-only observation.

# Projectile contract

## `PROJANIM_SPECIFIC_V4`

Revision-240 identifies `PROJANIM_SPECIFIC_V4` as server packet id `72`, fixed length `27`.

The exact client read order is:

| Order | Stable read | Readable primitive | Semantic value |
| ---: | --- | --- | --- |
| 1 | `PacketBuffer.method13278` | `readMediumAlt2` | source actor selector |
| 2 | `Buffer.method13099` | `readUnsignedShort` | start delay |
| 3 | `Buffer.method13254` | `readUnsignedShortLEAdd` | end/target height offset |
| 4 | `Buffer.method13254` | `readUnsignedShortLEAdd` | projectile/spot-animation id |
| 5 | `Buffer.method13107` | `readMedium` | target actor selector |
| 6 | `Buffer.method13250` | `readUnsignedShortLE` | horizontal start distance (`startPos`) |
| 7 | `Buffer.method13095` | `readUnsignedByte` | slope |
| 8 | `Buffer.method13251` | `readUnsignedShortAdd` | source height offset |
| 9 | `Buffer.method13113` | `readInt` | packed source `Coord` |
| 10 | `Buffer.method13113` | `readInt` | packed target `Coord` |
| 11 | `Buffer.method13254` | `readUnsignedShortLEAdd` | end delay |

Both packed coordinates are decoded with:

```text
plane = Coord.unpackPlane(value)
x     = Coord.unpackX(value)
y     = Coord.unpackY(value)
```

If the decoded projectile id is `65535`, the client does not construct or enqueue a projectile.

Otherwise the packet values become the 15 constructor inputs in this semantic order:

```text
Projectile(
    sourceLevel,
    sourceX,
    sourceY,
    sourceHeightOffset,
    sourceActorSelector,
    targetLevel,
    targetX,
    targetY,
    targetHeightOffset,
    targetActorSelector,
    id,
    clientCycle + startDelay,
    clientCycle + endDelay,
    slope,
    startPos
)
```

The constructor resolves `SpotAnimationDefinition(id).sequenceId` into the embedded `AnimationSequence` controller, but it does not calculate trajectory coefficients yet.

## Actor selector encoding

The root-view API surface exposes the selector convention:

```text
selector > 0   -> one-based NPC-side actor index
selector < 0   -> negative one-based player-side actor index
selector == 0  -> no actor target
```

The world-position resolver additionally routes the encoded actor through the appropriate WorldView when world entities/sub-views are involved.

A stored packet tile coordinate remains available as fallback. If an actor selector cannot currently resolve to an Actor, `resolveWorldPosition` falls back to the stored tile endpoint instead of failing the projectile update.

## Delayed start and lifetime

The outer projectile loop is exact:

```text
if clientCycle > endCycle:
    remove projectile
else if clientCycle >= startCycle:
    advance trajectory/animation
    submit to scene
else:
    retain without moving or drawing
```

Consequences:

- `startCycle` is inclusive;
- `endCycle` is also inclusive;
- removal begins only when `clientCycle > endCycle`;
- a projectile exists in semantic runtime before its first visible cycle;
- animation completion does not itself remove the projectile.

## Endpoint resolution

Every active trajectory update resolves the target endpoint again:

```text
target = resolveWorldPosition(
    targetActorSelector,
    targetTileX,
    targetTileY,
    targetLevel,
    worldViewManager
)
```

The resolved vertical coordinate is reduced by `targetHeightOffset`.

The source endpoint is resolved only while the projectile has not begun moving:

```text
source = resolveWorldPosition(
    sourceActorSelector,
    sourceTileX,
    sourceTileY,
    sourceLevel,
    worldViewManager
)
```

Its vertical coordinate is reduced by `sourceHeightOffset`.

This asymmetric rule matters:

- source actor movement after launch does not drag the projectile's already-established origin;
- target actor movement can change the target every active update;
- disappearance/failure of the target Actor can revert target resolution to the stored tile endpoint.

## Initial horizontal position

On the first active update only, let:

```text
sx, sy = resolved source horizontal coordinates
tx, ty = current resolved target horizontal coordinates
dx = tx - sx
dy = ty - sy
d  = sqrt(dx*dx + dy*dy)
```

If `abs(d) < 0.01`, the projectile starts exactly at `(sx, sy)` horizontally.

Otherwise:

```text
px = sx + dx * startPos / d
py = sy + dy * startPos / d
```

The vertical position begins at the resolved source vertical coordinate after subtracting `sourceHeightOffset`.

`startPos` is therefore a horizontal launch-distance offset along the source-to-target direction, not a cycle count and not a height.

## Per-update trajectory solution

For each active update, including retargeted updates, define:

```text
t = endCycle + 1 - clientCycle
```

The `+1` is part of the stock client formula and must be preserved.

Horizontal velocity is recomputed from the projectile's current position to the latest resolved target so that the projectile reaches that target over the remaining lifetime:

```text
vx = (targetX - px) / t
vy = (targetY - py) / t
horizontalSpeed = sqrt(vx*vx + vy*vy)
```

On the first active update only, initial vertical velocity is derived from slope:

```text
vz = -horizontalSpeed * tan(slope * 0.02454369)
```

`0.02454369` is approximately `pi / 128`.

Vertical acceleration is then solved on every active update from the current vertical state and current target:

```text
az = 2 * (targetZ - pz - t*vz) / (t*t)
```

This means a moving target causes horizontal velocity and vertical acceleration to be recalculated against the latest endpoint while retaining the projectile's already-integrated current state.

## Integration by client delta

Let `dt` be the client advancement amount supplied by the outer loop. The client integrates:

```text
px += dt * vx
py += dt * vy
pz += dt * vz + 0.5 * az * dt * dt
vz += dt * az
```

Do not assume the implementation is permitted to hardcode `dt = 1`. The Java method explicitly accepts an advancement amount and applies it to movement and animation progression.

## Orientation and pitch

After trajectory integration, the client derives 2048-unit angular values:

```text
yaw = (int(atan2(vx, vy) * 325.949) + 1024) & 2047
pitch = int(atan2(vz, horizontalSpeed) * 325.949) & 2047
```

`325.949` is approximately `2048 / (2*pi)`.

The projectile model path applies the stored pitch before scene submission. The yaw/orientation is also supplied to the scene placement call.

These are semantic pose inputs derived from trajectory, not independent authoritative orientation state sent by the server.

## Projectile animation

At the end of each active trajectory update, the embedded `AnimationSequence` advances by the same `dt` through `advanceAnimationSequence`.

Unlike standalone `GraphicsObject`, `Projectile.method1228` does not clear/remove itself when the common advancement result reports a loop boundary or terminal completion. Projectile lifetime remains governed by `endCycle`.

Scene/model projection should therefore tolerate the sequence controller's own loop/completion behavior without converting it into projectile despawn policy.

## Scene submission

After an active projectile update, the client submits the projectile to the scene using:

- `sourceLevel` as the placement plane;
- current integrated `px`, `py`, `pz`;
- fixed draw radius/height parameter `60`;
- current projectile yaw/orientation.

Notably, scene placement continues to use the source level even though the packet stores a target level and target height can be resolved from that target level. Preserve that behavior unless later exact evidence establishes an alternate branch.

## Region/base rebasing

The region/base-shift path calls the projectile rebase helper with negative tile deltas. It adjusts:

```text
sourceTileX += deltaX
sourceTileY += deltaY
targetTileX += deltaX
targetTileY += deltaY
current px += deltaX << 7
current py += deltaY << 7
```

with the actual caller supplying the sign needed for the new base.

Vertical state, cycle bounds, slope, actor selectors and animation playback are not reset by this rebase operation.

This is a semantic coordinate-frame transition. Reprojecting only the rendered model while leaving projectile runtime coordinates unchanged would break subsequent retargeting and integration.

## WorldView routing

`WorldViewManager.getWorldViewAt` supplies the spatial view for tile endpoints, with root fallback when no auxiliary spatial view contains the coordinate.

`resolveWorldPosition` then:

1. resolves an Actor endpoint when the actor selector is currently valid;
2. otherwise chooses the WorldView containing the stored tile endpoint;
3. transforms through a backing `WorldEntity` when required;
4. converts tile position to local center where no entity transform is required;
5. samples the correct terrain height;
6. adds parent/root transform height where applicable.

This is why protocol, GameState/world-view routing, and scene coordinates cannot be collapsed into one renderer-only projectile object.

## RuneLite injection-only behavior

`Projectile.method1228` posts `ProjectileMoved` once the projectile has reached its start cycle. `GraphicsObject` construction posts `GraphicsObjectCreated`.

Those events are observation surfaces inserted by RuneLite. RustClient should expose equivalent plugin observations only after the corresponding semantic state transition if plugin compatibility needs them. They must not drive trajectory or effect lifetime.

## Rust implementation invariants

A later Rust port should preserve at least these rules:

1. Packet id/length/transforms are revision-profile concerns.
2. Projectile id `65535` suppresses spawn.
3. Source and target packed coordinates retain plane/x/y exactly.
4. Start and end delays become absolute cycles relative to receive-time `clientCycle`.
5. Start and end cycles are inclusive in the active lifetime test.
6. Before `startCycle`, projectiles and standalone graphics effects are retained but not scene-submitted.
7. Projectile source endpoint is established only before first movement; target endpoint is re-resolved every active update.
8. Missing actor resolution falls back to stored tile coordinates.
9. `startPos` offsets the initial horizontal position along the source-target vector.
10. Initial vertical velocity comes from slope only on first movement.
11. Horizontal velocity and vertical acceleration are recomputed using `endCycle + 1 - currentCycle`.
12. Integration uses the supplied client delta for both movement and animation.
13. Yaw and pitch use stock 2048-angle formulas.
14. Projectile expiry is cycle-driven, not animation-completion-driven.
15. Standalone graphics-object lifetime ends on its first sequence loop boundary.
16. Region/base changes rebase semantic world-effect coordinates before further simulation.
17. WorldView routing and terrain-height resolution occur in semantic/runtime layers, not renderer ownership.
18. Cache definitions/models remain immutable dependencies, not a second copy of effect state.

## Client quirks and cautions

- Projectile remaining-time math uses `endCycle + 1 - currentCycle`.
- `endCycle` is visible/active; removal uses strict `clientCycle > endCycle`.
- A near-zero horizontal source-to-target distance (`< 0.01`) bypasses the `startPos / distance` normalization.
- Retargeting recomputes target position every active update but does not re-anchor the source after launch.
- Scene submission uses `sourceLevel` even when target level differs.
- Standalone graphics objects use sequence loop-boundary bit `1` as completion rather than waiting for normal terminal bit `2`.
- A standalone graphics object with no active sequence is considered finished by its runtime loop.
- Actor-attached spot effects, standalone graphics objects and projectiles all share spot-animation/sequence assets but have distinct semantic lifetime rules.

## What remains

This closes the source-level world-effect runtime necessary to implement basic standalone graphics and projectiles. Remaining related research should focus on:

- confirming any additional zone/enclosed projectile packet forms that normalize to the same semantic spawn;
- complete normal versus instanced region rebuild/base-shift ordering;
- graphics/projectile scene/model interaction beyond the established spot-definition model path;
- visual acceptance after a Rust implementation exists.

No implementation status is advanced by this research document alone.

## Verification

Not run in this session by explicit user instruction. No `verify.sh`, tests, Cargo checks, Clippy, CI, compilation, mapping regeneration, release gate or visual/parity run was executed.
