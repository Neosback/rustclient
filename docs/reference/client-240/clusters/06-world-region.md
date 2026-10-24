# 06 world and region state

Status: `specified`

Rust owners: `protocol` for rebuild packet decoding, `game-state` for active world-view/base/entity state, `scene` for scene data and collision consumers, and `application` for the load-scene transition. The injected Java loader is not a Rust architecture template.

## Family

`WorldView` is already a semantic class name and already has seed mappings for id, base coordinates, size, plane, tile heights/settings, instance state and map-region/template data. This shard fills the remaining high-value ownership fields needed by player/NPC/scene synchronization. The normal rebuild path is anchored by `classIV.method6364` and `classKK.method6533`; both enclosing classes contain unrelated behavior and therefore remain generic.

## Old to new

| Stable symbol | `@ObfuscatedName` | Readable name | Tier | Confidence |
| --- | --- | --- | --- | --- |
| `WorldView.field1685` | `ab` | `collisionMaps` | T4 | A |
| `WorldView.field1687` | `ae` | `scene` | T4 | A |
| `WorldView.field1692` | `aq` | `players` | T4 | A |
| `WorldView.field1696` | `ay` | `npcs` | T1 | A |
| `WorldView.field1698` | `ad` | `worldEntities` | T1 | A |
| `classIV.method6364` | `cm` | `decodeNormalRegionRebuild` | T4 | A |
| `classKK.method6533` | `dd` | `beginRegionLoad` | T4 | B |

## Region/base semantics

The normal rebuild path reads transformed coordinate values from the incoming packet and updates the client's active map-coordinate state before calling `beginRegionLoad(..., false, ...)`. `beginRegionLoad` treats the packet values as the region center and derives tile-space base values using `(center - 6) * 8`. The active plane is carried into the new load state.

This establishes three coordinate layers that should remain explicit in Rust:

1. Region-center values from the rebuild packet.
2. `WorldView.baseX/baseY`, the tile origin of that view.
3. Entity/world coordinates, converted to local/view coordinates only at the subsystem boundary.

Cluster 04's player-info family depends directly on this distinction when it materializes a logical player into a `WorldView`.

## WorldView ownership

`collisionMaps` is four-plane collision state. `scene` is the view's scene-data owner. `players` and `npcs` are indexed entity sets, and `worldEntities` is the view-scoped world-entity table. They are cleared together when the view resets, before the scene and collision maps are cleared.

The existing seed names remain the source of truth for `baseX`, `baseY`, `plane`, `sizeX`, `sizeY`, `tileHeights`, `tileSettings`, `mapRegions`, `instance` and `instanceTemplateChunks`; this shard does not duplicate those rows.

## Loading order

The normal region rebuild decoder performs packet prelude handling, reads the rebuild coordinates, applies the active map coordinate values, begins the region load, then runs the packet completion hook. The load transition must not be moved ahead of the coordinate decode.

`beginRegionLoad` also coordinates a client game-state transition when the client is not already in the active game state. RustClient should preserve the externally visible transition ordering but should express it through the existing application/game-state architecture, not by mirroring Java globals.

## Stock behavior versus RuneLite injection

The `rl4` object constructed inside `classKK.method6533`, its asynchronous bookkeeping and the assertions around concurrent map loads are RuneLite-injected loader machinery. They are `injection-only` evidence for when a load begins and what base/center/plane values it carries.

The stock semantics that matter to the port are the rebuild packet's base/region selection, view reset/repopulation ordering, terrain/collision/scene ownership and the point at which the application transitions to a new loaded scene. Do not port `rl4` one-for-one.

## Hot path and ownership guidance

Keep `WorldView` durable state in `game-state` with typed IDs/coordinates. `scene` should consume immutable or transactionally updated view data rather than becoming the canonical owner of players/NPCs. Collision maps are simulation/game-state inputs even when the scene subsystem also needs them. Packet transforms stay in `protocol`.

## External referrers and handovers

Cluster 04 owns the player-info decoder and coordinate family. Cluster 05 owns the NPC synchronization entry point. Cluster 03 owns Actor movement/animation fields. `client` and injected `rl4` are intentionally not wave-owned. The instance/dynamic rebuild sibling path remains a separate follow-up because this cluster only names the normal rebuild entry point proven here.

## Client quirks

`beginRegionLoad` is a no-op when the requested center is unchanged unless the caller forces a rebuild. Preserve that guard at the application/map boundary. The Java method also has explicit concurrent-load assertions/errors from RuneLite injection; record those as injection behavior rather than stock protocol requirements.

## Deliberately generic

`classIV` and `classKK` remain generic due to unrelated clan/login/string/collision helpers in the same classes. `WorldView.field1699` contains per-tile `TileItem` deques and is strongly suggestive of ground-item storage, but it remains generic in this wave to avoid choosing a public semantic name from usage alone. The dynamic/instance rebuild entry point is not named until its full packet path is isolated.
