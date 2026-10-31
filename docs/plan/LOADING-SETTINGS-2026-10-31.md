# Map loading, chatbox prompts, UI atlas and minimap ops checkpoint, 2026-10-31

Status: partial. Commits on `main`: `2769b98f` (retained scene), `2ab7b78a` (RESUME_P_*), `c8b4db66`
(UI atlas, probes), `9e0e8178` (minimap zoom ops). Nothing here was observed in the live client;
every live-behaviour line below is NOT-RUN.

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
  (`SCENERY-PATCH-2026-10-30.md`, `UPDATE-OPTIMIZATION-2026-10-30.md`) still describe load cost.

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
- `locations::build` selects locations serially (stats and error order unchanged), then prepares
  the models on up to 8 scoped threads (not on wasm32) and folds the results back in location
  order. The seam merge and emit remain serial.

Measured (`scene_probe --performance`, release, 104x104 at 3168,3168, M-series Mac, one run each,
`RUSTCLIENT_PROBE_NEIGHBOUR=1` for the warm figure):

| Step | Before | After |
|---|---|---|
| cold `load_world` | 218 ms | 210-213 ms (first load still decodes everything) |
| neighbouring window on the warm cache | not measured (everything re-decoded) | 17-18 ms |
| `build_mesh` | ~140 ms | ~100-115 ms |
| static location phases | | prepare 18 ms, seam merge 34 ms, emit 29 ms, terrain 13 ms |

The probe asserts that reloading the first window warm gives the same `content_hash`.
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
`docs/reference/rs240-port/INTERFACE-AUDIT.tsv`. It does not know server state (varps, inventories,
friends), so some failures are scripts that need data a live session has; those stay in the baseline
as accepted gaps.

First run (970 groups): 4 failed to load, 195 aborted a script, 1 had missing assets. Now:
936 ok, 33 script-errors, 1 missing-assets, 0 load errors, 0 missing sprites or fonts anywhere.

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
- Still aborting (33 groups, all in the baseline): ops tied to server state or features not
  ported (`INVOTHER_*`, `STOCKMARKET_*`, `WORLDLIST_FETCH`, `CLANPROFILE_FIND`, `CLAN_GETCHATOWNERNAME`,
  `IGNORE_COUNT`, `FROMDATE`, `MOBILE_WIFIAVAILABLE`, `CC_SETFILLCOLOUR`, `*_SETNPCHEAD`,
  `CC_SETPLAYERHEAD_SELF`, opcodes 2310/3129/3644/4036/7601, dynamic widget type 12), plus database
  and child-index errors that depend on the row a live server would pass.
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
  `GameState`); wheel zoom (the stock client has none; the RuneLite Minimap plugin adds it and
  needs plugin API additions); rebuilding the image when scenery changes; the RuneLite Minimap
  plugin and Tile Indicators.

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
  separate update-optimisation program (`docs/reference/UPDATE-OPTIMIZATION-2026-10-30.md`) with
  frozen measurements, and its files (`surface.rs`, `world.rs`, `timing.rs`, `ui_canvas.rs`,
  `performance.rs`) are being edited at the same time. The listener-candidate index that
  replaced the per-cycle scan is already in. Per-cycle plugin/entity skip-when-unchanged needs a
  live profile first.
- **Open, with numbers.** The CPU copy of the static mesh stays resident after upload: 46.3 MB of
  `SceneVertex` (1,929,558 vertices) plus the opaque/transparent index lists, kept for hover
  picking (`pick_surface`), the hidden-scenery set and device-loss recovery. Dropping it needs a
  compact pick-only vertex store plus a rebuild path for recovery. The world texture array is
  allocated at 256 layers (about 22 MB with mips) because the layer is the texture id; shrinking
  it needs an id-to-layer map in the shader. The probe's first run measured 296 MB peak RSS and
  every later run (including the A/B without my change) 412 MB; I did not find why, so treat the
  absolute RSS as unreliable between sessions and compare only A/B pairs.

## Open, in order

1. Load latency leftovers: parallel seam merge/emit, chunked vertex packing, minimap off the UI
   thread, cancellation between phases.
2. Settings: observe the live reload-on-click (see "Not reproduced"); make text-input field
   widgets interactive (TEXT-FIELD-WIDGET); MES_TYPED 3102 if a cache script needs it.
3. Interface audit: burn down the 33 script-error groups in the order of how many groups each op
   touches; extend the harness with varp/inventory fixtures so state-dependent scripts can run;
   fire each Variable/Operation/Click listener once and assert scroll survives and prompts close.
4. Minimap: remaining dot frames (needs ground items and friend lists in `GameState`), rebuild on
   scenery change, wheel zoom; Tile Indicators and the Minimap plugin (plugin API additions needed).
5. Memory and baseline FPS: retained CPU mesh (46 MB), texture array layers, per-cycle
   plugin/entity work after a live profile (see "Memory and baseline FPS").
