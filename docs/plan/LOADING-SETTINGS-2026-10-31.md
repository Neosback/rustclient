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

## Open, in order

1. Load latency leftovers: parallel seam merge/emit, chunked vertex packing, minimap off the UI
   thread, cancellation between phases.
2. Settings: find the varp a real click changes and why script 3840 takes its empty branch;
   `Mes`/MES_TYPED 3100/3102, CC_INPUT_*, DEVICEOPTION_GETRANGE 3217, STRINGVECTOR.
3. `interface_audit` over every cache group (mount, 20 cycles, sprites/fonts/atlas area, prompts
   close), with a committed baseline.
4. Minimap: render at the committed zoom, wheel zoom, mapfunction icons, remaining dot frames,
   rebuild on scenery change; Tile Indicators and the Minimap plugin (plugin API additions needed).
5. Memory and baseline FPS (see the plan: `vertex_remap`, `WorldMesh` retention, per-cycle
   plugin/entity work, release profile).
