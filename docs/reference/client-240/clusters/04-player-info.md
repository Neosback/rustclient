# 04 player info

Status: `specified`

Ownership correction, 2026-10-25: `classKY`/Coord now belongs exclusively to
[cluster 13](13-coord.md), including this shard's existing `setPacked` mapping. The coordinate behavior
below remains supporting evidence; mapping/ledger ownership is no longer duplicated.

Rust owners: `protocol` for bitstream and mask decoding; `game-state` for durable player-info slots, coordinates and committed entity state. The Java family must not be ported one class for one Rust type.

## Family

`classDF` is the revision-240 player-info decoder. It owns 2048 logical slots, partitions them into active and inactive index sets, decodes skip-compressed movement under bit access, and dispatches extended state. `classDC` is the durable logical state for one player index and may fan that logical state into concrete `Player` objects in multiple `WorldView`s. `classKY` is the packed plane/x/y coordinate value used by that state.

## Old to new

| Stable symbol | `@ObfuscatedName` | Readable name | Tier | Confidence |
| --- | --- | --- | --- | --- |
| `classDF` | `df` | `PlayerInfoDecoder` | T4 | A |
| `classDF.field1421` | `ae` | `activePlayerIndices` | T4 | A |
| `classDF.field1409` | `ab` | `playerSlots` | T4 | A |
| `classDF.field1414` | `ag` | `deferredMovement` | T4 | B |
| `classDF.field1416` | `aa` | `inactivePlayerIndices` | T4 | A |
| `classDF.field1417` | `aj` | `activePlayerCount` | T4 | A |
| `classDF.field1418` | `ay` | `inactivePlayerCount` | T4 | A |
| `classDF.method3006` | `ak` | `initializePlayerInfo` | T4 | A |
| `classDF.method3015` | `an` | `decodePlayerMovement` | T4 | A |
| `classDF.method3019` | `ag` | `readSkipCount` | T4 | A |
| `classDF.method3050` | `yz` | `decodeExtendedPlayerUpdate` | T4 | A |
| `classDC` | `dc` | `PlayerInfoSlot` | T4 | B |
| `classDC.field1379` | `af` | `packedRegion` | T4 | A |
| `classDC.field1380` | `ae` | `coord` | T4 | A |
| `classDC.field1388` | `ab` | `updateFlags` | T4 | A |
| `classDC.field1393` | `as` | `appearanceBuffer` | T4 | A |
| `classDC.field1382` | `ax` | `actions` | T4 | A |
| `classDC.field1392` | `ag` | `traversal` | T4 | A |
| `classDC.field1381` | `ac` | `playerIndex` | T4 | A |
| `classDC.field1387` | `az` | `representedPlayers` | T4 | B |
| `classDC.method2891` | `af` | `reset` | T4 | A |
| `classDC.method2901` | `ab` | `attachPlayer` | T4 | A |
| `classDC.method2923` | `us` | `applyTraversal` | T4 | A |
| `classDC.method2930` | `ao` | `applyActionSequence` | T4 | A |
| `classKY` | `ky` | `Coord` | T4 | A |
| `classKY.field4053` | `af` | `x` | T4 | A |
| `classKY.field4054` | `ae` | `y` | T4 | A |
| `classKY.field4055` | `az` | `plane` | T4 | A |
| `classKY.method6623` | `az` | `pack` | T4 | A |
| `classKY.method6626` | `af` | `unpackPlane` | T4 | A |
| `classKY.method6638` | `ae` | `unpackX` | T4 | A |
| `classKY.method6640` | `ab` | `unpackY` | T4 | A |
| `classKY.method6660` | `ao` | `setPacked` | T4 | A |
| `classKY.method6674` | `aq` | `isValid` | T4 | A |

## Wire and cycle semantics

`classDF.method3006` enters bit access, reads the local player as a 30-bit packed coordinate and seeds all other slots from 18-bit external coordinates. `classDF.method3015` then performs four passes: active slots with flag bit 0 clear, active slots with it set, inactive slots with it set, then inactive slots with it clear. A zero update bit starts a compressed skip run. `readSkipCount` uses a 2-bit selector and consumes 0, 5, 8 or 11 additional bits. Every pass requires its skip counter to return to zero before the next pass begins.

After the four passes, update flags are shifted right and the active/inactive index arrays are rebuilt for the next cycle. This ordering is protocol behavior, not an optimization opportunity.

The extended-state order is the one documented in `../rs240-port/PLAYER-EXTENDED-STATE.md`. Known revision-240 branches include appearance, action sequence, facing, traversal, forced movement, spot animation, public chat, health bars and hitsplats. The two branches already documented there as unresolved remain unresolved here rather than receiving speculative names.

`Coord` packs `plane << 28 | x << 14 | y`. The invalid sentinel is represented by plane `-1`. Do not collapse this into a renderer coordinate: this is durable game-state/world coordinate semantics.

## Materialization and WorldViews

`PlayerInfoSlot.applyTraversal` subtracts a target `WorldView` base from the durable world coordinate before applying movement to a concrete Player. `classDF.method3038`/`method3039` create a representation when the logical coordinate enters a view and remove it when it leaves. A single logical remote player can therefore have view-specific concrete representations while retaining one shared logical update state.

## Hot path guidance

Keep bit reads allocation-free. The 2048 player slots and active/inactive index arrays should be fixed-capacity state in Rust. Extended masks should decode into typed update data and commit in client order; avoid allocating a trait object or hash lookup for every mask branch.

## External referrers and handovers

`client` owns the local player index, current cycle and top-level packet dispatch and is intentionally not owned by this wave. `WorldView` is owned by cluster 06. Actor and Player movement/animation fields are already owned by cluster 03. This cluster does not rename those symbols even when they are written by player updates.

## Injection-only behavior

RuneLite callback posts such as `PlayerSpawned`/`PlayerDespawned` are useful observation evidence but are not stock wire semantics and do not become protocol responsibilities in RustClient.

## Client quirks

The decoder throws if a compressed skip run is not exhausted at the end of each bit pass. Preserve that fail-fast packet-consistency behavior at the protocol boundary rather than silently accepting a partially consumed pass.

## Deliberately generic

The extended-player mask branches `0x40000` and `0x8` remain semantically unresolved as recorded in `PLAYER-EXTENDED-STATE.md`. Several scratch arrays/counters in `classDF` also remain generic until their complete producer/consumer roles are proven. No names were inferred solely from array shape.
