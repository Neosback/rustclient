# Loading, prompts, settings, interface audit, minimap and memory pass, 2026-10-31 to 2026-11-02

Evidence record for the pass that followed the 2026-11-01 documentation sweep. It continues the
archived [map-loading checkpoint](../archive/2026-foundation/plan/LOADING-SETTINGS-2026-10-31.md)
(frozen at its first four commits) and is not an execution plan: the
[roadmap](../plan/PORTING-ROADMAP.md) owns order and what remains, the
[domain matrix](rs240-port/PORT-MATRIX.md) owns status. Every line about the live client is
**NOT-RUN**: the client was never observed running during this pass, so no behavior below is
accepted beyond the cache, probe and unit evidence named with it.

Commits on `main`: `2769b98f` retained scene, `2ab7b78a` RESUME_P_*, `c8b4db66` UI atlas,
`9e0e8178` minimap zoom ops, `657ba8d1` shared decodes, `ca1cad46` opcode table and settings
builders, `8902c824` interface_audit, `6b8cdbc8` minimap zoom and icons, `8233aef3` vertex remap,
`b38258e1` Tile Indicators, `2e4563eb` Minimap plugin, `e2ce6f64` retained pick mesh,
`2f6d3c19` texture array sizing, `eef04e4b` Ground Markers, `546f2ef4` cache warm-up,
`3b30b99f` texture fingerprint.

## What changed

1. **No blackout on a map rebuild** (SPEC-0023). `GameState` retires the installed scene on a
   window-changing `RebuildMap` instead of dropping it; `displayed_scene()` keeps it drawable until
   `InstallScene`. The native app keeps rendering it (`SceneView::retire_map`, `replacing`), stops the
   old scene's animator/entity workers, starts the replacement load at the end of the cycle's packet
   batch (`start_pending_scene`), and never lets the A-B-A shortcut in `discard_obsolete_scene`
   mistake the retired scene for the replacement. The stock loading box (`classLU.method7199`:
   black fill, white border, plain12 at (10,10)) replaces the bare text: always with no displayed
   scene, over a retired scene only after 250 ms (`LOADING-BOX-DELAY`, a recorded deviation), and
   never for door/location reprojections.
2. **Chatbox prompts answer the server.** CS2 3104/3105/3106/3115 (`classKU`) queue a `ResumeIntent`
   encoded as `RESUME_P_COUNTDIALOG`(62)/`NAMEDIALOG`(70)/`STRINGDIALOG`(54)/`OBJDIALOG`(3); the stock
   integer parse (non-integers send 0) is in `ResumeIntent::count_from_text`. `RESUME_P_COUNTDIALOG_LONG`
   (111) is encoded but has no script op yet. Before, scripts 299/681 aborted at these ops before
   clearing varc 5, leaving the chatbox in its prompt layout.
3. **UI atlas.** Images are packed tallest-first and the atlas grows 2048 -> 4096 within the device
   limit. Before, one overflowing widget-asset batch failed the upload and every later request
   failed with it.
4. **Minimap zoom ops 7250/7252/7253/7254** run against `GameState`-owned `MinimapSettings`
   (`MINIMAP-ZOOM-OPS` deviation: the ops are in the cache's scripts but not in the pinned 240 jar;
   semantics from the 241 client).
5. **Probes.** `script_probe --variable-sim` (server-style variable changes against a mounted
   interface, tracking widget counts and scroll) and `--prompt-sim` (open a prompt, type, press
   Enter).

## Findings that change the plan

- **Key scope does not gate `onKey`.** `classPQ` reads the scope only to decide op-key (`IfSetopkey`)
  eligibility, so the earlier "keys leak between chat and prompt listeners" fix is not needed and was
  not made. `key_scope` stays stored but unread.
- **Settings scroll reset: reproduced for one variable only.** With group 134 mounted and its list
  scrolled, changing varp 2855 (bit 0, the only variable the mounted interface listens to besides
  three row variables) runs script 3840's reset branch: `IfSetscrollpos 0 0` then `IfSetscrollsize 0 0`,
  widgets 316 -> 152, no script error. All other varps in 0..8000 changed nothing. This is a rebuild
  that produces an empty list, not a missing op, so the cause is in what the rebuild reads (database
  or enum result for that mode). A real click flows through the server (IF_BUTTON, then a varp), so the
  user-visible reset is not yet explained; it needs a live capture of the varp the click causes.
- **Typed input.** In `--prompt-sim` the prompt opens (varc 5 = mode) but typed digits never reach
  varcstr 359/335 and Enter sends nothing, with no script error. Script 73 (the chat key handler) first
  returns when chat layers 162:55/162:56 are hidden; the probe mounts group 162 at an arbitrary
  parent, so this run does not show whether the live chatbox types. Not a conclusion either way.
- **Minimap ops origin.** The cache is newer than the pinned 240 jar: its scripts call ops the jar
  lacks (7250-7254), which is why the 241 client is the evidence for them.

## Validation that ran

- `cargo test -p rustclient-game-state` (incl. retired-scene replay and fingerprint vectors),
  `-p rustclient-protocol --lib resume`, `-p rustclient-application`, `-p rustclient-model`,
  `-p rustclient-cache`, `-p rustclient-renderer --lib` (35 passed, 5 ignored), `-p rustclient-native`
  and `-p rustclient-game-ui`: all passed in the runs that included them.
- `cargo clippy ... --all-targets -- -D warnings` on game-state, game-ui, protocol, application,
  model, cache, renderer and native: clean when each change was committed.
- `cargo check -p rustclient-web --target wasm32-unknown-unknown`: passed after the retained-scene,
  atlas and minimap-op commits.
- `python3 tools/parity_inventory.py --check`, `tools/docs_index.py --check`, `tools/port_matrix.py
  --check`, `tools/port_audit.py --check`: pass.
- `script_probe` against a copy of the b240 cache: `--execute 134` 0 errors; the variable and prompt
  simulations above.

## NOT-RUN

- Live walk across a region boundary (no black frame, delayed box), settings clicks, typing into a
  prompt, world map buttons, minimap: the live client is off limits to me in this session.
- Whether the atlas overflow was the cause of the blank world-map buttons (unmeasured; the packer
  fix removes the failure mode, not the diagnosis).
- Workspace-wide `cargo clippy`/`cargo test`: only the crates listed above ran; another agent's
  uncommitted renderer and tool edits are in the tree and were not validated here.
- No release-profile load timing was taken for the retained-scene change; the earlier measurements
  ([SCENERY-PATCH-2026-10-30.md](../archive/2026-foundation/plan/SCENERY-PATCH-2026-10-30.md), [UPDATE-OPTIMIZATION-2026-10-30.md](UPDATE-OPTIMIZATION-2026-10-30.md)) still describe load cost.

## Load latency (stage 1b)

What changed, all on the worker side of a window load:

- `Cache` keeps a `Memo` (`crates/cache/src/memo.rs`): the decoded texture set, floor set and kit
  set once per open cache, and decoded models (bounded at 4096; reaching it drops the set).
  Entries carry the SHA-256 of the source bytes they came from and `load_world` composes those into
  the window's content hash, so a warm load reproduces the cold hash. The hash composition itself
  changed (per-entry digests instead of one stream of file bytes), so `SceneAssetId` values differ
  from earlier builds; nothing persists or pins them (checked: only in-memory identity).
- `WorldAssets.textures` and `WorldMesh.textures` are `Arc<BTreeMap<..>>`: the cache's set, every
  scene asset and every mesh share one copy instead of deep-cloning it per load and per mesh.
- `locations::build` selects locations serially (stats and error order unchanged), then prepares the
  models and folds the results back in location order. This commit prepared them on up to 8 scoped
  threads; the 2026-11-01 sweep (`99f1111a`) removed that to meet SPEC-0024 (no nested worker
  threads), so `prepare_jobs` is serial again and the `build_mesh` row below was measured with the
  threads. The seam merge and emit are serial.

Measured (`scene_probe --performance`, release, 104x104 at 3168,3168, M-series Mac, one run each,
`RUSTCLIENT_PROBE_NEIGHBOUR=1` for the warm figure):

| Step | Before | After |
|---|---|---|
| cold `load_world` | 218 ms | 210-213 ms (first load still decodes everything) |
| neighbouring window on the warm cache | not measured (everything re-decoded) | 17-18 ms |
| `build_mesh` (with the since-removed scoped threads) | ~140 ms | ~100-115 ms |
| static location phases | | prepare 18 ms, seam merge 34 ms, emit 29 ms, terrain 13 ms |

The probe asserts that reloading the first window warm gives the same `content_hash`.

### Cache warm-up (stage 1c)

- **Done: the window-independent definitions are decoded before the first scene load.**
  `CacheProfile::warm_up` fills the memo's floors, kits and textures and loads config groups 6
  (objects), 12 (sequences) and 14 (varbits), with the same advisory flag the loads use. The native
  app submits it once from `resumed()` as a `JobPriority::Prefetch` job on the existing bounded
  pool (`SceneView::prewarm`), which also opens the shared cache; login normally takes longer than
  the warm-up, so the first `load_world` finds everything decoded. It spawns no thread and waits on
  no child job (SPEC-0024).
- `Slot::get_or_load` and the config group load are now single flight (one decode; later callers
  wait and take the result), so a scene load that starts mid-warm-up shares the warm-up's work
  rather than repeating it. A failed warm-up stores nothing and the load reports the error.
- Measured (`scene_probe`, release, same 104x104 window, one run each; `RUSTCLIENT_PROBE_WARMUP=1`
  warms first, `=concurrent` races the warm-up against the load on two threads):

| Case | Total | `load_world` itself |
|---|---|---|
| cold, no warm-up | 218 ms | 218 ms |
| warm-up first | 146 ms warm-up | 30 ms |
| warm-up racing the load | 106 ms | 106 ms |

- A/B: the scene asset id (`186,74,52,...`), state fingerprint and all 23 captured frames equal the
  pre-change `cap-before` run (`diff -rq`), so the content hash is unchanged.
- **Done: the texture fingerprint mixes eight bytes per step.** `world_textures::fingerprint`
  decides only whether the GPU array must be rewritten between uploads in one process, so it
  needs no fixed algorithm. On 215 synthetic 128x128 textures (release, one run) it fell from
  19 ms to 3.5 ms; a test covers pixels, animation, ids and a tail byte past the last whole word.
- Kept on purpose: the whole-scene `validate` at the start of `PreparedScenery::new` (13 ms). The
  owning-zone partition indexes the source on the strength of it, so removing it would trade a
  safety check for time on a worker thread; the per-part validation does not replace it.
- Not done: a scoped-thread version of the model/texture decode ran in 77-146 ms but was dropped
  for contradicting SPEC-0024. Remaining cold cost is models (window dependent).

Not done: map-square decode cache, sharing `effective_locations`/`decorations::plan` between the
static and dynamic passes (both are ~1 ms), chunked vertex packing, moving `build_scene_minimap` off
the UI thread, cancellation checks between phases, prefetching, parallel seam merge/emit (the
largest remaining serial cost, ~63 ms).

## Settings pass (stage 2 follow-up)

Found and fixed:

- **The four minimap zoom opcodes never decoded from the cache.** The opcode table is binary
  searched and the earlier commit put 7250-7254 between 3119 and 3125, so real cache scripts still
  read them as `Unsupported`. Only hand-built test scripts exercised them. The entries now sit in
  order, and a test asserts the table is strictly sorted and that every entry decodes to itself.
  Rows built by scripts 3885/3888/3961 (zoom and dropdown rows) no longer abort.
- `Mes` (3100), `TEXT_SWITCH` (4110), `DEVICEOPTION_GETRANGE` (3217), the `STRINGVECTOR_*` family
  (7400-7409) and the eight text-input field configuration ops used by script 3850 (1133, 1134,
  1137-1139, 1144-1146) are handled. After this the transitive closure of the all-settings list
  builders (3840, 3841, 3846-3869, 3961, 3885, 3888, 4182, 7092, 7106, 7904) contains no
  unsupported op. Deviations: `TEXT-FIELD-WIDGET` (input fields are not interactive) and
  `DEVICE-OPTION-RANGE` (option 20 follows TSPS 241).

New probe modes (`script_probe CACHE --execute 134 ...`): `--click-sim` fires the first operation of
every offered widget with a long list scrolled (authenticates the session so operations are
offered), and `--tab-sim` selects each tab of the strip. Result on the b240 cache: all seven tabs
build with 0 script errors; toggles and dropdown selects keep the list position (clamped to the new
content height); selecting a tab or Search resets it to the top, as in stock.

**Not reproduced.** I could not make the all-settings list jump to the top on a toggle click in the
probe: script 3847 re-runs the list builder with the live scroll position (`IfGetscrolly`) and the
builder restores it. Of all varps 0..8000 only 2855 (tab selection, listened to by 3837) resets the
list. The user's report is therefore not explained by what the probe can drive; it needs a live
observation or a capture of the server packets that follow a click. The opcode-table bug above is the
only defect found on that path. Row toggles are client-side (`SetVarbit` in script 3965), and the
rows' own operation bit is clear, so they send no packet, as in stock.

## Interface audit (stage 3 follow-up)

`interface_audit CACHE_DIR [--write FILE | --check FILE] [--verbose] [GROUP ...]`
(`apps/native/src/bin/interface_audit.rs`) opens every group of index 3 as the root of a fresh
authenticated session, runs 20 game cycles with resize listeners, then asks for the sprites, fonts
and item icons the group draws and projects it once. It prints one TSV row per group (script errors,
missing sprites/fonts/items, summed sprite area, quads, projection diagnostics) and `--check`
fails when a group's counts rise or a clean group stops being clean. The whole cache takes about two
seconds in release. Group 595 also runs the world-map open script (1749) the server would send.
`RUSTCLIENT_SCAN_MODELS=1` and `--hex GROUP FILE` are decoder diagnostics. The baseline is
[INTERFACE-AUDIT.tsv](rs240-port/INTERFACE-AUDIT.tsv). It does not know server state (varps, inventories,
friends), so some failures are scripts that need data a live session has; those stay in the baseline
as accepted gaps.

First run (970 groups): 4 failed to load, 195 aborted a script, 1 had missing assets. Now:
936 ok, 33 script-errors, 1 missing-assets, 0 load errors, 0 missing sprites or fonts anywhere.
After the services slice below: 951 ok, 18 script-errors, 1 missing-assets. After the fill and head
slice: 954 ok, 15 script-errors, 1 missing-assets. After the naming slice: 960 ok, 9 script-errors,
1 missing-assets.

Fixed from the first run:

- **Four interfaces did not load at all** (19, 272, 415, 428): model components with width mode 1
  and height mode 0 carry two size fields where the reference client reads one, leaving two zero
  bytes. The client never checks where a component ends; the decoder now tolerates zero padding
  (non-zero trailing bytes still fail).
- **`ENUM` with an enum id of -1** aborted the script in 122 groups. The reference client loads an
  absent enum as an empty one whose types never match, so the lookup answers 0 or "null". Same for
  `ENUM_STRING` and `ENUM_GETOUTPUTCOUNT`.
- **`STRUCT_PARAM` with a struct id of -1** aborted likewise; an absent struct answers each
  parameter's default.
- Handled: `OC_CERT`, `OC_UNCERT`, `OC_STACKABLE` (classBB 4204-4206), `RANDOM`/`RANDOMINC` (seeded
  `SplitMix64Random`, fixed default seed, `seed_random` for the host), `CC_GETTRANS`,
  `CC_GETMODELZOOM`/`ANGLE{X,Y,Z}`, `CC_GETMODELTRANSPARENT`, `CC_SETMODELTRANSPARENT` (accepted,
  not stored, so models are never drawn translucent: `MODEL-TRANSPARENCY`), and the listener
  signature characters `W` and `X`, which the reference pops from the object stack like `s`.

Findings that need other work:

- **World map (595):** 165 sprites requested, none missing, 717 runtime widgets, no asset or
  projection diagnostics. The button sprites therefore load from the cache; if they still fail
  live, the cause is at upload (atlas) or in a state the audit cannot see. Largest sprite area of
  any group is 768 195 px (group 100), well under the 2048x2048 atlas, so one group alone does not
  overflow it.
- Services slice (2026-11-03): `INVOTHER_*` (the `INV_*` family at container + 32768),
  `FROMDATE` (UTC), `MOBILE_WIFIAVAILABLE`/`BATTERYCHARGING`/`BATTERYLEVEL`, and, in
  `scripts/services.rs`, `STOCKMARKET_*` (eight empty offers), `IGNORE_COUNT` (-1, not loaded),
  `CLAN_GETCHATOWNERNAME` (empty), `CLANPROFILE_FIND` (0) and `WORLDLIST_FETCH` (0, never ready)
  now answer with the reference's own initial state. 33 groups fell to 18. These are not
  behaviour for a live server: when a packet fills one of these tables the arm moves to its
  `GameState` owner.
- Fill and head slice (2026-11-03): `CC_SETFILLCOLOUR`, and the two unnamed ops beside it that the
  cache uses (1124 and 1125, named `CC_SETFILLTRANSPARENCY` and `CC_SETGRADIENT` from their
  handlers; the `IF_` forms 2124 and 2125 likewise), are script-only overrides in
  `WidgetRuntime::fill` (the cache widget format carries none of them). `CC_GETFILLCOLOUR` reads
  it back. The widget painter draws a gradient rectangle row by row with the reference weights
  (`classYW.method13738`/`method13804`, `game-ui/src/fill_gradient.rs`); only the row maths is
  tested, not a pixel capture. `CC_SETNPCHEAD` and `CC_SETPLAYERHEAD_SELF` record the head as the
  widget's model source in `WidgetRuntime::head` (exclusive with an item or a model id); **no
  head model is drawn yet**, because model widgets are not painted at all. Model angles and zoom
  widened from `u16` to `i32` (the reference stores plain ints and a script passes -1).
- Naming slice (2026-11-03): the seven unnamed ops that aborted groups were read from the deob
  handlers and named. `CC_CLEAROP`/`IF_CLEAROP` (1310, 2310: blank operation 1..10 of a widget that
  has operations, `classBB.method812`), `CAM_SETKEYPANSPEEDS` (3129: stores the arrow-key camera
  pan speeds `client.field842`/`field952`, read by `classZJ`; the pair is taken and dropped
  because no camera pan consumes it), `FRIENDSCHAT_CLEARSORT`/`FRIENDSCHAT_APPLYSORT` (3644, 3655:
  clear and run the member sort comparator, a no-op without a channel), `PARSEINT_OR_MINUSONE`
  (4036: the reference's decimal reader, -1 for anything that is not a whole `i32`),
  `NC_NAME` (6754: NPC definition name, `"null"` when absent, through
  `CacheProfile::script_npc_name`) and `ARRAY_FILL_SEQUENCE` (8011: `first, first+1, ...` over a
  clamped range of an int array, `Stacks::fill_array_sequence`). Names are ours, taken from what
  each handler does, not from a reference table. The friends chat channel queries (`CLAN_GETCHAT*`,
  `CLAN_ISSELF`, `CLAN_ISFRIEND`) now answer the no-channel state; `CLAN_KICKUSER`,
  `CLAN_JOINCHAT` and `CLAN_LEAVECHAT` still need their outbound packets. Op 7601 stays
  unhandled on purpose: the reference's handler for the whole 7600 range (`classNF.method7882`)
  returns 2, its "not handled" result, so script 7166 cannot run there either. Unnamed and still
  unhandled in the same handler: the friends chat sort ops 3645-3654 and 3657 and the friends and
  ignore list ones (3628-3643, 3656), whose comparator keys are not yet named.
- Still aborting (9 groups, all in the baseline): op 7601 (reference-unhandled), dynamic widget
  type 12 (two groups), and database, child-index, stack and string-stack errors in scripts 8943,
  4445, 7962, 8690, 3100 and 1451 that depend on rows, widgets or a serialised array string
  a live server supplies (script 1451 writes past an array sized from an unset string).
- Item 2360 (group 426) has no inventory model in the cache.

## Minimap (stage 4, first part)

- **Zoom renders.** `compose_minimap`, `click_destination` and the marker/dot placement take the
  committed `zoom_quarters` (16 is the stored 4 pixels per tile, 8 to 32 are 2x to 8x), so the
  zoom scripts (7250-7254) now change what is drawn and where a click lands. The composed key
  includes the zoom, so a change repaints. `scene_probe CACHE DIR` writes `minimap-*-z8/z16/z32`.
- **`mapfunction` icons.** Locations whose object names a world map element that the minimap shows
  (`minimap_visible`, with a sprite) are listed per plane in `MinimapImage::icons` and drawn over
  the map with the same rotation and scale as dots. `WorldAssets::map_icons` holds only the sprites
  the loaded objects reference (39 sprites, 43 icons in the Lumbridge window). Icons hide below
  `icon_zoom_limit` whole steps (`MINIMAP-ICON-ZOOM-LIMIT`).
- Not done: item, friend, team, friends-chat and clan dots (no ground items or friend list in
  `GameState`); rebuilding the image when scenery changes.

## RuneLite Minimap plugin (stage 4, third pass)

`first-party-plugins::minimap` ports `MinimapPlugin`; it is `enabledByDefault` in the source, so the
wheel zoom is on out of the box. API 0.2 gained, additively, the capability `ui.minimap`,
`PluginOutput::minimap` (`MinimapRequest`: wheel zoom, a zoom to apply once, hide, seven dot
colors) and `ClientSnapshot::minimap_zoom` (ADR-0006, SPEC-0004).

- The shell owns every effect: wheel over the minimap changes `MinimapSettings::zoom_quarters` by one
  quarter per notch (the 241 step, 2x to 8x); a request change flips `MinimapUpdate::Zoomable`; the
  saved level (`zoomLevel` in plugin storage, written every 11 s when it moved) is applied once with
  `ZoomQuarters`; dot colors replace the seven `mapdots` frames with the source's 4x5 sprite
  (`recolored_dots`; alpha zero is the source's null color, which keeps the stock dot); `hideMinimap`
  sets the runtime `hidden` of components 22 (minimap) and 33 (orbs) on top-levels 161/164 every
  cycle and unhides them when lifted.
- Differences are listed under `## minimap` in `FIRST-PARTY-LEDGER.md`.
- NOT-RUN: nothing was seen in the live client, so the wheel feel, the saved-zoom round trip across a
  restart, the dot look and the hidden minimap/orbs on a real resizable layout are unobserved.

## Memory and baseline FPS (stage 5, first pass)

Measured with `scene_probe CACHE_DIR --performance` (release, 104x104 Lumbridge window, M2).
That probe loads, builds and uploads one scene; it does not run the 50 Hz cycle, so it says
nothing about steady-state frame time.

- **Done: `vertex_remap` is released after the draw metadata is built.** The source-to-packed
  vertex remap was only read by `GeometryData::metadata`; afterwards a boolean (`immutable`)
  answers "this geometry is packed and cannot take dynamic updates". The probe's
  `WorldGeometryStats.cpu_remap_bytes` went from 7,360,452 to 0. The probe's peak RSS did not move
  (about 412 MB in both an A/B build with and without the change): the peak is the transient mesh
  build, before the remap is dropped. The saving is steady-state only and has not been measured in
  the live client. Test: `packed_geometry_stays_immutable_without_keeping_the_remap`.
- **Measured, not adopted: release `lto = "thin"` + `codegen-units = 1`.** Load stages were
  indistinguishable (`mesh built` 258-263 ms with and 260-268 ms without) and the full release
  build went from seconds to 2 min 44 s. No `[profile.release]` change.
- **Measured, not adopted: bounding `Cache.config_files`.** The config groups are 2.8 MB
  compressed; evicting them would re-decode objects/NPCs/locations on the next load, which is the
  latency stage 1b removed. Left cached.
- **Not touched: frame time.** The window limiter, GPU timing and retained UI work are under the
  separate update-optimisation program ([UPDATE-OPTIMIZATION-2026-10-30.md](UPDATE-OPTIMIZATION-2026-10-30.md)) with
  frozen measurements, and its files (`surface.rs`, `world.rs`, `timing.rs`, `ui_canvas.rs`,
  `performance.rs`) are being edited at the same time. The listener-candidate index that
  replaced the per-cycle scan is already in. Per-cycle plugin/entity skip-when-unchanged needs a
  live profile first.
- **Done: the static mesh's CPU triangles are released after upload.** `SceneGeometry::
  release_triangles` drops `vertices`, `opaque` and `transparent` once the renderer has the mesh
  and `ScenePickIndex` has built; the index keeps a compact copy (`picking/triangles.rs`:
  `[i16; 3]` per vertex and an 8-byte mark per triangle with the first corner's render tag, draw
  tile and the all-alpha-255 flag) and runs the same traversal, ties and clickbox query on it
  through a small `Triangles` trait that the reference geometry also implements. A renderer
  created after the release (`F5`, surface loss, window resume) cannot re-upload, so
  `create_renderer` sets `SceneView::awaiting_world` and `schedule_scene_projection` re-projects
  the scene from the cached asset and the current scenery inputs until it publishes (no world is
  drawn meanwhile; the per-cycle entity and animator uploads are skipped). Spec: SPEC-0016
  "Released static triangles". Tests: `a_released_geometry_keeps_picking_from_the_compact_copy`
  (surface, far-ray fallback and clickbox answers equal the unreleased reference) and the
  existing indexed-versus-linear tests. `scene_probe --performance` prints the resident figures
  and checks 600 rays against the released geometry.
- **Done: the world texture array is sized to the largest texture id.** `world_textures::
  layer_count` replaces the fixed 256 layers: 215 layers, 18,786,700 B instead of 22,369,280 B
  (3.6 MB; `scene_probe --performance` prints both). Packing textures by count with an id-to-layer
  map in the shader would not save more: the 240 cache has 214 of the 215 ids, and the entity
  streams may name any of them later. The array is therefore about 19 MB for the whole cache;
  the next reduction would be compression or on-demand layers, which change the texture path and
  are not planned. A/B check: `scene_probe <cache> <dir>` captured 22 frames (spawn, orbit,
  fog, color-blind, MSAA/aniso variants) with 256 forced and with 215 layers; all 22 PNGs are
  byte-identical. The first try, a dense map in the animation buffer read by the fragment
  shader, was dropped when the cache showed no gain.
- **Done: Ground Markers.** `crates/first-party-plugins/src/groundmarkers.rs` marks tiles with Shift and
  the world menu: the plugin offers `Mark`/`Unmark` `Tile` for the hovered tile (`PluginOutput::menu_offers`,
  capability `ui.menu.decorate`, SPEC-0004/ADR-0006), `NativeApp::add_plugin_entries` appends it below
  "Walk here" on a Shift right-click when the menu opened on that tile, and the choice returns as
  `MenuOptionClicked` (kind `Other(1500)`, tile in `param0`/`param1`, needs `ui.menu.swap`). Markers are
  stored per region in the RuneLite JSON and drawn with `OverlayCommand::Tile` (`markerColor`,
  `borderWidth`, `fillOpacity`, 32 tile limit, player's plane). Not ported: `Label` and `Color` entries
  (they need a text prompt, a color picker and submenus), the minimap outline, the world map orb
  import/export, instances; the schema omits `drawOnMinimap` and `showImportExport`. Tests: five plugin
  tests (offer, toggle and persisted text, event filtering, source-shaped input, distance and region
  edges), a host test (grant, count and text bounds) and a native test of offer selection. Nothing was
  seen in the live client, and the menu path from right-click to click in `NativeApp` is not unit tested.
- **Open, with numbers.** The probe's first run measured 296 MB peak RSS and every later run
  (including the A/B without my change) 412 MB; I did not find why, so treat the absolute RSS as
  unreliable between sessions and compare only A/B pairs.

## Frame-time tails (roadmap row 2, first slice)

Offline, release build, `scene_probe` on the 240 cache for the 104x104 window at (3168, 3168).
The machine was not quiet (load average 4.8-5.9 from unrelated desktop processes), so these are
stage attributions on a busy host, not a valid baseline, and the live frame trace is NOT-RUN.

- **Worker stage split of `build_mesh` (temporary timing, removed again):** terrain 12-13 ms,
  static locations 107-119 ms, dynamic locations 4.5-4.8 ms. Inside the static pass: effective
  locations 0.1-0.2 ms, decoration plan 0.9-1.0 ms, per-location preparation 55-58 ms (8,285
  jobs, pure), **seam merge 30.5-31.1 ms**, emit 21-28 ms.
- **Done: seam merge now builds each model's vertex position table once.** `seams::merge` rebuilt a
  `BTreeMap` of the right-hand model's positions for every neighbouring pair; it now keeps one
  sorted `(position, vertex)` table per model, built on first use, and binary searches it. The
  match order (ascending vertex index per position) is unchanged. Seam merge fell from
  30.5-31.1 ms to 10.1-12.4 ms (four runs). The mesh vertex bytes (static and dynamic) hash
  identically before and after (`eae2a92db161c5a8`, a temporary hash over `GeometryData.vertices`,
  not committed). New contract test `smooth_models_on_neighbouring_tiles_light_their_shared_corner_
  identically` shows a merged seam lights both models' shared corner alike and an unmerged one does
  not.
- **Tried and dropped: reserving the vertex buffer before emit.** Emit measured 26-36 ms against
  21-28 ms without it, so the doubling copies are not the cost.
- **Measured, not changed: minimap build on the event loop.** `build_scene_minimap` takes
  p50 2.68 ms, p95 2.88 ms (20 builds, `scene_probe --performance` now prints it). It runs on the
  event loop at scene arrival and on a plane change. That is a few milliseconds at rare events, so
  moving it to the pool is smaller than the other open items and was not done.
- **Decided against: parallel per-location preparation (55 ms).** A throwaway scoped-thread
  experiment (not committed) measured 53-62 ms with 1 worker, 35-36 ms with 2, 25-26 ms with 3
  and 21-30 ms with 4, so the ceiling is about 30 ms of a roughly 330 ms worker-side load, and
  only worker latency, not main-thread frame time. SPEC-0024 forbids nested per-rebuild threads
  and pool workers waiting on child jobs, and a pool version needs `'static` ownership of the
  asset, inputs and jobs (`unsafe` is denied) plus a spec amendment. Skipped on 2026-11-04; revisit
  only if worker latency becomes the measured bottleneck. Emit (21-28 ms) and terrain (12-13 ms)
  are the other serial stages of similar size.
- **Still NOT-RUN:** a frame trace of the main thread (per-cycle plugin and entity work, widget
  and inventory granularity), cancellation between phases, chunked vertex packing, the quiet-host
  baseline, and every live observation.
