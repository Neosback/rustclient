# Scene depth, priority and wall decorations (revision 240)

Owner: `crates/scene` (placement, `decorations.rs`) and `crates/renderer` (depth state, shaders).
Evidence source for the placement rules: RustOSRS (`/Users/tylercovalt/Documents/RustOSRS`,
`docs/verification/RENDER-REVIEW-2026-10-10.md`), which diffed its port against the pinned deob client
(`FriendSystem.addObjects`, `Scene.newWallDecoration`, `Scene.method5576`, `WallDecoration.method6262`) and
`RuneLite`'s `SceneUploader`. Nothing here depends on that tree at build time.

## Ported

| Behavior | Rust owner | Verification |
| --- | --- | --- |
| One boundary, one wall decoration and one floor decoration per tile; a later location replaces the earlier | `decorations::Sequencer` | unit tests; end-to-end mesh test |
| Wall decoration displaced by the boundary already on its tile (type 5 full, types 6 and 8 half, defaults 16 and 8) | `locations::decoration_offset` | unit tests |
| `Scene.method5576`: a later type 0, 2, 9 or 12+ wall with displacement other than 16 rescales the decoration already placed (`d * offset / 16`, compounding) | `decorations::Sequencer::place` | unit tests, including the shape table and compounding |
| One-unit nudge of straight decorations (types 4 and 5) along the wall normal | `locations::placement_requests` | unit test; Lumbridge capture |
| Second plate of a type-8 decoration drawn at the plain tile centre (it previously reused the first plate's offset) | `locations::placement_requests` | unit test |
| Roof ids: 8-neighbour flood fill of under-roof tiles per plane, a roof slot per vertex and a shader hidden-slot mask (consumed by the `roofremoval` plugin; see `docs/reference/builtin-plugins/FIRST-PARTY-LEDGER.md`) | `scene::RoofVolumes`, `world.wgsl` | unit tests, naga shader validation |
| Diagonal (`256`) plates snapped flush against the type-9 slab on their tile, or lifted one unit when there is none | `decorations::flush` | synthetic slab test; Lumbridge capture |

The flush rule is **not client behaviour**. The software client paints decorations over their wall without a
depth test; this renderer tests depth, so plates that land inside the slab z-fight or vanish. It is on by
default (`FLUSH_DIAGONAL_DECORATIONS`) and is the only deviation in the table. RustOSRS measured its effect on
the client's own models: 58 of 64 diagonal decorations in one window already agreed with the client rule, and
the other 6 were corrected.

## Evidence run

Lumbridge spawn capture (`scene_probe`, `target/scene-probe` before and `target/scene-probe-after`): about 1.8%
of the pixels differ, concentrated on the castle wall. Before the change the arrow-slit decorations showed
broken black crosses (plates behind the wall face hidden by the depth buffer); after it they render whole.
Triangle count is unchanged at 619,827, so the Lumbridge window contains no replaced duplicates. No pixel
comparison with a live reference client was made; the claim is limited to the improvement visible in the
captures and the unit tests above.

## Not ported yet

- Draw centre of boundaries and decorations is the definition footprint's centre rather than the 1x1 storage
  centre (`RuneLite` draws them at `tile * 128 + 64`). Identical for 1x1 definitions, which is nearly all walls.
- Per-face alpha depth sort within 2048 units of the camera.
- Game-object capacity limits per tile.
