# Cluster 15: world-position resolution

Status: evidence-found

## Scope

Owned class: `classNG` (`@ObfuscatedName("ng")`).

Only one member is renamed: `classNG.method7883` → `resolveWorldPosition`.

The class itself deliberately remains generic. Its instance role is an unrelated `Callable` worker over a priority queue, while `method7883` is a static world/entity position helper. Naming the whole class after the static helper would misrepresent the source.

## Old to new

| Stable symbol | Original | Readable name | Evidence |
| --- | --- | --- | --- |
| `classNG.method7883` | `aa` | `resolveWorldPosition` | T4: resolves entity or tile inputs into a `classUZ`/`Vector3f` containing scene-space horizontal coordinates and sampled height |

## Behavior

`resolveWorldPosition` accepts an encoded target selector plus fallback coordinates, plane/height context and a world-view resolver.

### Entity target path

When the encoded selector identifies an entity target, the method:

1. resolves the corresponding `WorldView`;
2. resolves the target actor/entity;
3. obtains the actor coordinate through `Actor.method3175`;
4. creates a temporary vector;
5. writes the coordinate x/y pair into the vector's horizontal components.

### Tile target path

When no entity target resolves, the method:

1. resolves the `WorldView` covering the supplied tile coordinates;
2. subtracts that view's base tile offsets;
3. converts relative tiles to 128-unit local tile centers with `Coord.tileToLocalCenter`;
4. applies world-entity transforms when present;
5. otherwise constructs a temporary vector at the centered local tile position.

### Height resolution

After horizontal resolution, the method samples the applicable terrain/world-view height with `classKV.method6613`. If the resolved view is nested under a `WorldEntity`, it adds that entity/view contribution as well. The sampled value becomes the vector's vertical component.

The returned vector is therefore a temporary resolved scene-space position, not authoritative entity state.

## Projectile dependency

`Projectile.method1228` calls this method for both its current target and initial/source position. It then uses:

```text
resolved.x  -> first horizontal coordinate
resolved.z  -> second horizontal coordinate
resolved.y  -> terrain/entity height
```

and returns the temporary vector to the `Vector3f` pool after extracting the values.

This helper is a key bridge between encoded projectile targeting and the actual trajectory math, but it is not itself the projectile scheduler.

## Rust owner

Do not reproduce `classNG` as one Rust type. The useful semantic operation should be decomposed across existing owners:

- target identity/index interpretation: revision-specific protocol or typed semantic target reference;
- authoritative entity/world-view lookup: `game-state`;
- coordinate/world-view transform and terrain-height query: `scene` or a dependency-light world-coordinate service;
- projectile state and trajectory scheduling: semantic world-effect owner in `game-state` or a dedicated runtime subsystem.

The returned temporary Java vector is an implementation detail. Rust code can return a typed resolved-position value without recreating the pool.

## Known external referrers inspected

- `Projectile.java`, which calls `method7883` in its trajectory update path;
- `Actor.java` via the coordinate extraction helper used by the entity branch;
- `WorldView`/`WorldEntity` helpers reached while selecting the applicable view and transform.

The exhaustive reference set was not regenerated in this checkpoint because repository verification/gate commands were explicitly excluded.

## Client quirks

The containing `classNG` mixes an asynchronous `Callable` worker role with unrelated static gameplay helpers. That mixed ownership is preserved as a source quirk rather than normalized into a misleading class rename.

The resolver may fall back from an entity-target-shaped selector to tile/world-view resolution when no entity-derived vector is produced. The port should preserve the observed fallback ordering rather than assuming every encoded target always resolves to a live actor.

## Deliberately left generic

- the `classNG` class name itself;
- queue/worker methods `call`, `method7884`, and `method7885`;
- `method7886`, which iterates active player indexes and belongs to a different runtime concern;
- downstream world-view/height helpers whose broader semantics require their own clusters.

## Verification state

No `verify.sh`, tests, Cargo checks, Clippy, CI, decompiler regeneration, or generated merge update was run for this checkpoint, per user instruction.
