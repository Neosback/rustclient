# 18 - WorldView registry and coordinate routing

Status: **specified** for revision-240 WorldView registry identity, root fallback, coordinate lookup, creation, removal and reset lifecycle used by world-effect position resolution.

## Scope and ownership

This cluster owns `classCL`.

The class is the client-side registry/lifecycle manager for `WorldView` instances. It is not a renderer scene graph and should not become one in RustClient.

Read-only evidence used here:

- `WorldView` for per-view base, dimensions and runtime collections;
- `classNG.method7883` / cluster 15 for projectile source/target position resolution;
- `WorldEntity` for transformed sub-world positions;
- `client.java` for active manager use and region/base-shift behavior.

## Old to new

| Stable symbol | `@ObfuscatedName` | Readable name | Evidence |
| --- | --- | --- | --- |
| `classCL` | `cl` | `WorldViewManager` | T4: id-keyed WorldView registry plus root/lookup/lifecycle operations |
| `classCL.field779` | `ax` | `worldViewCount` | T4: create increments, remove decrements, reset clears |
| `classCL.field782` | `az` | `worldViews` | T4: IterableHashTable keyed by WorldView id |
| `classCL.field785` | `as` | `rootWorldView` | T4: id-0 root creation, direct getter and coordinate fallback |
| `classCL.method1575` | `af` | `createWorldView` | T4: construct, register by id, increment count |
| `classCL.method1584` | `ag` | `getWorldViewById` | T4: direct registry lookup |
| `classCL.method1590` | `ap` | `getWorldViewAt` | T4: coordinate containment scan with root fallback |
| `classCL.method1588` | `as` | `getRootWorldView` | T4: direct root getter |
| `classCL.method1572` | `ax` | `createRootWorldView` | T4: create/register id-0 root view |
| `classCL.method1580` | `ar` | `removeWorldView` | T4: unregister, clean up/unlink, decrement count |
| `classCL.method1615` | `bw` | `resetWorldViews` | T4: unload/clear/reset all registry state and reinitialize retained root |

All names are confidence B because the exact revision-240 semantics are direct but there is no independent public API type naming this registry family.

## Registry authority

`worldViews` is an `IterableHashTable` keyed by the integer WorldView id.

`createWorldView` performs the authoritative registry mutation:

```text
view = WorldView(id, ...)
worldViews[id] = view
worldViewCount += 1
return view
```

`getWorldViewById(id)` reads the same table directly.

This registry is semantic application/game state. Renderer scene handles may reference a WorldView, but they are not the authority for which views exist.

## Root WorldView

`createRootWorldView(...)` creates id `0` through the same registration path and stores the result in `rootWorldView`.

`getRootWorldView()` returns that exact stored reference.

The root view is also the fallback for spatial routing when no non-special registered WorldView contains a requested coordinate.

That fallback is relevant to projectiles and other cross-view position resolution. A caller is not required to fail merely because no auxiliary WorldView rectangle matches.

## Coordinate routing

`getWorldViewAt(tileX, tileY)` iterates registered views and skips views whose `WorldView.method3734(...)` marks them as the special/non-spatial kind excluded from this lookup.

For each spatial candidate it reads the view's global tile rectangle:

```text
baseX = view.field1706
baseY = view.field1707
endX  = baseX + view.field1694
endY  = baseY + view.field1690
```

The containment test is half-open:

```text
baseX <= tileX < endX
baseY <= tileY < endY
```

The first matching WorldView is returned. If none match, `rootWorldView` is returned.

RustClient should preserve the containment and fallback semantics while using typed tile coordinates rather than reproducing primitive integer soup.

## Projectile dependency

`resolveWorldPosition` (`classNG.method7883`, cluster 15) receives this manager and uses it when a projectile endpoint is not resolved directly from an Actor.

For a tile endpoint it does, conceptually:

```text
view = worldViewManager.getWorldViewAt(tileX, tileY)
relativeX = tileX - view.baseX
relativeY = tileY - view.baseY
```

It then either:

- transforms the local tile center through the corresponding `WorldEntity`, when the view is entity-backed; or
- uses the ordinary `64 + tile*128` local center.

Finally it samples the appropriate height and accounts for parent/root view transforms before returning the 3D position.

This means WorldView selection is part of authoritative projectile targeting semantics, not a renderer convenience.

## Removal

`removeWorldView(view)` performs more than unlinking a scene object:

1. remove per-view entries from the manager's side maps;
2. invoke WorldView cleanup/despawn state;
3. unlink the WorldView from the registry structure;
4. decrement `worldViewCount`.

A Rust implementation should similarly make view removal an application/game-state lifecycle operation and let downstream scene/render resources follow that transition.

## Reset lifecycle

`resetWorldViews` iterates all registered WorldViews through the manager's unload helper, clears the registry, resets the count and manager side maps/default configuration, then reinitializes and re-registers the retained root view when one exists.

The important semantic distinction is:

```text
reset manager runtime
!=
destroy the concept of the root view permanently
```

This is useful for reconnect/rebuild lifecycle design: dependent projections must be disposable while root semantic view identity can be deliberately re-established.

## Region/base shifts

The client region-shift path demonstrates that WorldView-owned and global transient entities are rebased consistently:

- actors have queued tile positions and local/fine positions shifted;
- WorldView graphics objects shift local x/y by `deltaTile << 7` and are culled if they leave the 104-tile local scene;
- projectiles shift source tile coordinates, target tile coordinates and current local x/y;
- WorldEntity transforms are shifted through their own view-aware helper.

This is evidence that view/base changes are semantic coordinate-frame changes. Reconstructing only renderer transforms would leave authoritative runtime objects in the old coordinate frame.

## Injection-only separation

`classCL.method1620` posts RuneLite `WorldViewUnloaded` and invokes renderer/plugin-facing despawn observation. That notification surface is useful evidence that view lifetime is externally observable, but it is not the authority that performs semantic removal.

RustClient should publish an event/snapshot after the committed view lifecycle transition rather than make plugin callbacks own the transition.

## RustClient ownership

Recommended split:

- `game-state::world_views`: view ids, root view, spatial bounds, registry membership and coordinate routing;
- application/session lifecycle: create/reset/remove orchestration;
- protocol profile: revision-specific world-view/rebuild packet decode;
- scene: projection/resource state derived from existing semantic views;
- renderer: GPU/display resources only.

A Java `WorldViewManager` does not require one monolithic Rust manager type. What matters is a single authoritative semantic registry and one typed coordinate-routing path.

## Deliberately left generic

Several methods are duplicate wrappers around already named behavior, including alternate coordinate lookup and root-creation shapes. They remain stable generic symbols rather than receiving redundant names.

The manager also contains side maps/configuration types whose semantics are not required to establish projectile routing or lifecycle. Those fields remain generic pending their owning subsystem audit.

## Client quirks and cautions

- Spatial containment is half-open at the maximum x/y bounds.
- Non-spatial/special WorldViews are skipped by the coordinate lookup.
- No match falls back to root instead of returning null.
- Reset clears registry state but can deliberately reinitialize the retained root view.
- WorldView lifecycle callbacks visible to RuneLite are observation, not semantic ownership.
- Region rebasing must reach semantic transient objects, not just visual scene transforms.

## Handover

This cluster supplies the missing owner beneath projectile endpoint resolution:

```text
packed/tile endpoint
       -> WorldViewManager.getWorldViewAt
       -> view-relative/local transform
       -> optional WorldEntity transform
       -> height resolution
       -> projectile trajectory update
```

It also establishes the lifecycle boundary needed by later normal/instanced rebuild research without claiming that complete rebuild packet ordering is closed here.

## Verification

Not run in this session by explicit user instruction. No `verify.sh`, tests, Clippy, CI, compilation, mapping merge or build gate was executed.
