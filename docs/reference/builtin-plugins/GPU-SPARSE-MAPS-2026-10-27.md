# Sparse map loading and GPU boundary evidence — 2026-10-27

<!-- authority-notice -->
> Historical continuation with bounded source/runtime evidence. Use the
> [current audit](../rs240-port/CURRENT-AUDIT.md), [GPU ledger](GPU-AUDIT.md) and
> [roadmap](../../plan/PORTING-ROADMAP.md) for present status and ordering.

The supplied `/Users/tylercovalt/Desktop/LIVE` cache is usable for the region-boundary diagnostic.
The previous checkpoint's missing-archive failure exposed a RustClient loader defect: an undeclared
map group is valid empty space in revision 240. It must be distinguished from a declared archive
whose bytes are missing or corrupt. This corrects the earlier interpretation in
[the zone checkpoint](GPU-ZONES-2026-10-27.md); it does not change the cache.

## Source and ownership

The pinned injected-240 source `rl4.method10135` checks index-5 group presence before deciding that
missing data is pending. Present terrain streams load first. Missing ground areas then pass through
`rl4.method10138` when scene-center chunk Y is below 800. That method visits inclusive region edges
in Y/X order, clipped to the scene window, sets ground shadow 127 and copies boundary heights in
west/east/south/north order. Later writes win at corners and adjacent empty regions. Upper-plane
heights remain zero. `rl4.method10122` clears the collision initialization bit only for decoded
terrain; `CollisionData.method5353` supplies its blocked initial state. This is injection-derived
240 loader evidence, not a claim that all injected behavior is untouched vanilla behavior.

`cache::osrs240::world_maps` owns archive classification and initialization. The immutable catalog
retains `MapSquareSource` and `TerrainTile::base_shadow`. Missing squares have no floor definitions
or locations; no terrain mesh is invented. `SceneAsset::terrain_available` and `scene::collision`
keep their traversal blocked, while `scene::locations::shadows` supplies source base shadows before
location shadows. GameState and the external plugin ABI are unchanged. Expanded rings and instance
templates remain incomplete; the adapter currently initializes the supported core window.

An absent square contributes a domain-tagged coordinate marker to asset hashing. Dense scenes keep
their previous identity. A missing index table, missing payload for a listed group, missing required
file, bad container/CRC/version or malformed terrain/location stream remains a contextual error.
The new behavior is based on reference-table membership, never a catch-all error fallback.

## Verification

- Focused cache contracts cover sparse/present classification, ground/upper heights, inclusive edges,
  corner overwrite order, shadow 127, the high-Y exception, asset identity separation and strict
  declared-group failures. Scene contracts distinguish a decoded floorless tile from an unavailable
  square, reject traversal through the latter and produce no substitute terrain.
- The actual LIVE boundary fixture uses origin `(2832,4352)` and target `(2884,4404)`. It contains two
  absent map squares; 49,038 of 124,740 vertices belong to unrelated maps. Toggling the setting changes
  53,583 color-channel bytes through the actual Metal pipeline. The zero-distance variant also
  changes 53,583 bytes from the unfiltered frame. Fresh GPU reconstruction is byte-identical.
- The boundary PNGs were inspected. The filtered view at this diagnostic camera is black and the
  unfiltered view contains a narrow platform strip. The numerical admission check passes, but these
  captures do not establish a useful gameplay view or Java visual parity. They are unrelated to the
  earlier login-black-screen repair.
- The dense spawn probe still has nine present squares, 1,929,774 vertices and 46,314,576 vertex bytes.
  Its asset identity and fingerprint `e3a928e9c04b50a5` are unchanged. Fresh GPU reconstruction is
  byte-identical; materials, fog, color correction and filtering captures pass.

Reproduce using `tools/gpu/README.md`. Generated logs and PNGs live under ignored `target/`.
Full automated gate results are recorded in [the owning plan](../../plan/BUILTIN-PLUGINS.md).
No new browser run, Java pixel comparison, reference-MCP session, authenticated travel or
fixed/classic/modern visual acceptance is claimed by this sparse-map checkpoint. The later overlay checkpoint reduces the remaining broader
GPU obligations to ten; complete classic GPU and 117HD readiness are not established.
