# OSRS 240 / RuneLite roadmap

Canonical execution order, reconciled **2026-11-05** against the actor-effects, camera and widget-menu commits after `c890f759`. Full parity is
incomplete and no completion percentage is claimed. The [current audit](../reference/rs240-port/CURRENT-AUDIT.md)
records the inspected baseline and acceptance limits; the [domain matrix](../reference/rs240-port/PORT-MATRIX.md)
owns detailed status. This roadmap replaces the October 29 execution plan and the original
foundation plan; their evidence remains in the [archive](../archive/README.md). The
[2026-10-31 to 2026-11-02 pass record](../reference/CLIENT-PASS-2026-11-02.md) and the
[2026-11-05 pass record](../reference/CLIENT-PASS-2026-11-05.md) hold the commands,
measurements and NOT-RUN gates behind the "Done" rows below.

How to read status here: **Done** means the named scope has code plus cache, probe or unit
evidence; it never means live acceptance. **Partial** has working behavior with named
missing branches. **Open** is absent. **Issue** is a defect or unexplained behavior that
needs a decision or an observation. The live client was not observed during the latest
pass, so every "Done" row also appears in the live backlog below.

## Status at a glance

### Done (to the stated scope)

| Area | Scope delivered | Evidence |
| --- | --- | --- |
| Map rebuild without a black screen | `GameState` retires the installed scene on a window-changing rebuild; the previous scene stays drawn; stock loading box after 250 ms (deviation `LOADING-BOX-DELAY`), never for door or location reprojections; replacement load starts at the end of the packet batch | SPEC-0023; `2769b98f` |
| Chatbox prompts | CS2 3104/3105/3106/3115 queue `RESUME_P_*` packets 62/70/54/3 with the stock integer parse; packet 111 is encoded without a script op | `2ab7b78a` |
| UI atlas | Tallest-first packing and growth 2048 to 4096 within device limits; one oversized widget-asset batch no longer fails every later request | `c8b4db66` |
| Settings list builders | Minimap zoom ops decode from real cache scripts (opcode table was unsorted), `Mes`, `TEXT_SWITCH`, `DEVICEOPTION_GETRANGE`, `STRINGVECTOR_*` and the text-input configuration ops are handled; no unsupported op remains in the builders' closure | `ca1cad46` |
| Interface audit | `interface_audit` mounts all 970 groups of index 3 and checks a committed baseline: 936 ok, 33 script-error, 1 missing-assets, 0 load errors, 0 missing sprites or fonts | `8902c824`; [baseline](../reference/rs240-port/INTERFACE-AUDIT.tsv) |
| Minimap | Zoom ops 7250-7254 change what is drawn and where clicks land; mapfunction icons; NPC and player dots | `6b8cdbc8` |
| Plugins added | Tile Indicators, Minimap (wheel zoom, saved zoom, dot colors, hide), Ground Markers (Mark/Unmark through plugin-offered menu entries); API 0.2 gained, additively, `ui.minimap`, `ClientSnapshot` pointer/destination tiles and `PluginOutput::menu_offers` | `b38258e1`, `2e4563eb`, `eef04e4b`; ADR-0006 |
| Scene load latency | Decoded textures, floors, kits and models shared across loads; startup cache warm-up on a `Prefetch` pool job; single-flight memo and config loads; cheaper texture fingerprint. First `load_world` 218 ms to about 30 ms once warmed; scene asset id and 23 captured frames unchanged | `657ba8d1`, `546f2ef4`, `3b30b99f` |
| Memory | Vertex remap and the uploaded static triangles are released (picking runs on a compact copy); texture array sized to the largest id (3.6 MB saved) | `8233aef3`, `e2ce6f64`, `2f6d3c19` |
| Ground items | Authoritative piles from all six zone bodies and six `*_SPECIFIC` packets, stock matching and resets, world menu options, `OPOBJ1..5`, minimap item dots, plugin snapshot and events; piles not drawn (deviations `GROUND-ITEM-TILE-PICK`, `GROUND-ITEM-PILES-UNDRAWN`) | SPEC-0029; `9ff06076`, `2ed41e23` |
| Actor effects | Hitmarks, health bars and spot animations retained per actor with cycle-stamped lifetimes, reaching plugin snapshots and the existing events; interaction target, drawing, projectiles, tint and freeze open | SPEC-0022 |
| Plugin menu stage | `MenuEntryAdded`/`MenuOpened`/`PostMenuSort` emitted synchronously, `menu_edits` (with additive `Swap`) applied to the left click, mouse-over text and opened menu; every chosen entry reaches `MenuOptionClicked`; `ScriptPostFired` emitted | SPEC-0021; `8bb32a1d` |
| Plugin outputs drawn | Info boxes, `MinimapDot`, new `TileText`; plugins added: Menu Entry Swapper (built-in swaps), Ground Items (text overlay, HA values) | ADR-0006; `8bb32a1d`, `2ed41e23` |
| CS2 server-state ops | `INVOTHER_*`, unloaded friend/clan/world-list/stock-market answers, 3129/3644/4036, `FROMDATE`, rectangle gradient fills, head sources retained | `f26dae55`; audit baseline not regenerated |
| Per-cycle plugin work | Unchanged snapshot domains carried forward and not diffed, keyed on the `GameState` journal; equivalence-tested against full rebuilds | `ad4429f2`; unmeasured |
| Scene load | Seam merge by sorted merge join; emit capacity reserved; cancellation between load phases; redundant opaque binds skipped | `2386fc28`; unmeasured |

### Partial or open

| Area | State | What is missing | Owner row |
| --- | --- | --- | --- |
| Interface host | Partial | The 2026-11-05 ops are answered but `interface_audit` was not re-run, so the 33-group baseline is stale; still open: 2310 (sub-ops), 7601 (post-240 op), model widget painting (heads, translucency), interactive text-input fields (`TEXT-FIELD-WIDGET`); translucent models (`MODEL-TRANSPARENCY`); widget mutation packets; harness fixtures for varps, inventories and friends | scripts, widgets, widget-mutations |
| Minimap | Partial | Item, friend, team, friends-chat and clan dots (need ground items and social state in `GameState`); rebuild on scenery change; HD minimap has no source in `references/` | minimap |
| World map | Partial | Alignment and wrapping, hover and element menus, smoothing, flash, staff shortcut; button-sprite failure cause not established | world-map |
| Load pipeline | Partial | Seam merge is now a serial merge join and phases cancel, both unmeasured; parallel merge/emit only if a profile still shows it; chunked vertex packing; minimap build off the UI thread; map-square decode cache; instances, extended bounds, world views | normal-maps, instances, worldviews |
| Plugins | Partial | 18 partial modules of 131 catalog slots; GE price service; actor interaction target; Ground Markers `Label` and `Color`, import and export, minimap outline; blocked on actor effects, social state and provider services | first-party, plugin-api |
| Frame time | Open | Plugin projection reuse landed unmeasured; entity skip-when-unchanged, a hover pick prefilter over emitted-triangle bounds (clickbox bounds are unsafe), static-run culling outside the admission window; all need a live profile first | update-latency |
| Gameplay facts | Open | Ground item pile models; actor facing and interaction target, drawn effects, projectiles, social and trading, Grand Exchange offers, audio, world list | ground-items, actor-effects, facing, private-clan-chat, audio |
| Browser | Open | Shared sidebar and Wasm probes only; no playable browser client | browser |

### Issues and unexplained behavior

| Issue | Status | Next step |
| --- | --- | --- |
| Settings list returns to the top after a click | **Not reproduced.** In the probe a toggle keeps the position; only varp 2855 (tab selection) resets it, as in stock. The reported behavior is unexplained | Observe live, or capture the server packets that follow a click |
| World map buttons without sprites | **Cause unproven.** All 165 sprites of group 595 load from the cache and the audit shows no diagnostics; the atlas overflow was a hypothesis, and the packer fix removes that failure mode, not the diagnosis | Observe live; if it persists, log uploads against atlas area |
| Typed input into prompts | The probe's prompt opens but typed digits did not reach varcstr 359/335; the probe mounts group 162 at an arbitrary parent, so this is not evidence either way | Observe live |
| In-process RSS comparisons | The probe measured 296 MB peak once and 412 MB in every later run, including A/B builds without the change; absolute RSS is unreliable between sessions | Compare only A/B pairs; use `footprint` on a live session |

### Live-observation backlog (NOT-RUN)

Everything above is unobserved in the running client. In priority order: walk across a region
boundary (no black frame, delayed box); change a setting and scroll; type into a numeric, name
and string prompt; open the world map and look at its buttons; minimap wheel zoom, saved zoom
across a restart, dot colors, hide; Tile Indicators; Ground Markers Shift right-click, toggle
and restart persistence; first scene load after login (warm-up) and a door open. From the
2026-11-05 pass: drop, pick up and stack items (menu options, minimap dots, count changes, despawn,
region entry); Menu Entry Swapper on a banker and a fairy ring; Ground Items labels; any plugin info
box; Grand Exchange and settings interfaces mounting with gradient rectangles. Then re-run
`interface_audit --check` and `scene_probe --performance`. Record each result in the pass record or
its successor, not in this file.

## What is already in place

These are implemented foundations, not completed domains or live acceptance:

- Cache-backed native title/login, normal-map scenery, players/NPCs, ordinary
  movement/turning, public chat, selected widget actions, IF1/CS1 and script execution.
- Bounded cycle-stage inbound admission, indivisible packet groups, queued outbound
  flushes, ping replies, typed logout/transfer handling and shared cycle timing.
- Typed projection invalidations, sparse inventory updates, bounded shared native
  workers, retained scene buffers/identical poses and retained widget layout/painting.
  Ordinary location changes prepare dependent 8×8 owning zones and retain terrain/
  untouched GPU buffers, with coherent publication and bounded uploads. Live dependency/
  lifecycle acceptance and proportionate work across every system remain open.
- Absolute presentation deadlines, retained title geometry/flames, indexed widget
  listeners, asynchronous retained map pixels, optional nonblocking GPU timestamps
  and stock normal map-click dispatch (packet 78). Live teleport acceptance is open.
- One egui sidebar for native/browser harness, owned reference assets, GPU **Always
  on** settings, capability-mediated in-tree API, actor lifetime handles, subscriber
  filtering, lifecycle isolation and bounded retained Notes/XP Tracker panel subsets.
  Executable native guests remain ABI 0.1; API 0.2 is in-tree plus a deferred WIT contract.
- The retained previous scene, prompt answers, grown UI atlas, minimap zoom, shared decoded
  assets and cache warm-up listed under Done.

Do not port these foundations again. Extend their existing owners and finish their
missing branches. See [implementation evidence](../reference/UPDATE-OPTIMIZATION-2026-10-30.md)
and [the plugin ledger](../reference/builtin-plugins/FIRST-PARTY-LEDGER.md).

## Next delivery queue

Re-ordered 2026-11-02. The first row is cheap and gates trust in everything shipped since
the last live session; the rest follow dependencies. Work may overlap only when its
prerequisites exist, and the full mixed combat workload remains blocked on missing
semantic systems. Do not label a reduced workload the full 256-actor gate.

| Order | Deliverable | Exit evidence |
| --- | --- | --- |
| 1 | Observe the shipped pass live (backlog above) and resolve the open issues it raises: settings scroll, world-map buttons, prompt typing. | Written observations with build identity; each issue closed, reproduced with a capture, or re-scoped. |
| 2 | Valid current-machine baseline, then the largest measured tails: seam merge and emit, chunked vertex packing, minimap off the UI thread, cancellation between phases, per-cycle plugin and entity skip-when-unchanged, remaining widget and inventory granularity. | Matched raw traces; `scene_probe --performance` and frame traces before and after; no unrelated world reconstruction or upload for chat or inventory; failed uploads remain pending. |
| 3 | Interface and script host: re-run the audit against the 2026-11-05 ops, then burn down what remains, widget mutation packets, model widget painting, text-input widgets, then listener lifetime. | `interface_audit --check` baseline falls; CS2 ledger rows move; fixtures for varps, inventories and friends added to the harness. |
| 4 | Ground item pile models (top three by value, count variants, customisation) and model-bound picking; then actor and combat facts (rich facing, effects, lifetimes, reset and index reuse). | Exact 240 and selected RSProt cases to GameState transitions, replay and fingerprints, menus and rendering, reachable plugin facts. No guessed interaction or synthetic hooks. |
| 5 | Provider services (definitions, prices); key events for plugins; then port applicable plugin slices by dependency. | Correct stage and order, capabilities, source-case dispositions, actual visual consumers. |
| 6 | Classic GPU gates, map and session systems (instances, world views, extended bounds), then browser gameplay and release acceptance. | Per-gate source evidence and native and browser lifecycle results; ADR-0004 visual comparison. Inventory or build success alone cannot close a gate. |

## Workstreams and dependencies

The phase numbers below retain existing spec/research references. They identify
workstreams, not a requirement to repeat completed foundation checkpoints in order.

### Phase 0: evidence and performance baseline

**Owners:** application diagnostics, native performance, scene/renderer probes and
canonical ledgers. **Contract:** SPEC-0008/0019 and ADR-0004.

**Status: open.** Load-stage timings exist (`scene_probe --performance`; first `load_world` 218 ms to about 30 ms warmed, texture fingerprint 19 ms to 3.5 ms). The scored five-minute, 256-actor and soak gates have not been run.

Freeze source/worktree, executable, cache/profile, effective settings, physical
viewport, DPI/refresh and concurrent workload. Stock 240 determines gameplay;
pinned RuneLite determines plugin effects/chrome. Selected RSProt and actual cache
resolve wire/assets. TSPS snapshot and reviewed upstream through `842fd5d` are
separate supporting inputs, not revision authority. Reference trees stay unchanged.

Measure decode → commit → scripts → extraction → preparation → upload → presentation,
separating CPU, GPU and surface waits. Keep allocations, copied/uploaded bytes,
queue depth/stale work, residency and commit-to-visible latency. Overflow or missing
timing invalidates the affected measurement. Application present calls are not scanout.

Run three matched five-minute warm measurements per scored title, supported
256-actor and open-map workload, plus cold-resource tests and a 30-minute lifecycle/
memory soak. The full mixed workload is 64 players + 192 NPCs with varied poses,
effects, movement, chat, ground/inventory changes and repeated location replacement;
characterize 1,024 actors and uncapped headroom separately. Builds/profilers stay
inactive during scoring; the local OpenRune server is part of the declared workload.

At the effective 60 Hz target: presentation rate ≥99% of measured target, active CPU
p99 ≤10 ms, GPU p99 ≤12 ms, presentation gaps p99 ≤20 ms, gaps >25 ms ≤0.1%, warm
change visibility p99 ≤50 ms. Compare identical content for ≥20% lower peak transient
CPU memory, no steady process-memory regression and bounded post-lifecycle retention;
report CPU/GPU accounting separately on unified memory. These gates are **open**.
Keep draw distance **184**, resolution and effects; no quality cuts or global “fastest” claim.

### Phase 1: client cycle and session runtime

**Owners:** application cycle/session orchestration and platform adapters.
**Contract:** [SPEC-0020](../specs/SPEC-0020-client-cycle.md), accepted for implementation.

**Status: partial.** The queue, budget, groups and flush are implemented; stage orchestration, idle/watchdog, reconnect and browser composition are open.

Keep the implemented S4 packet queue, 100 ordinary-packet budget, indivisible groups,
ten-cycle catch-up bound, end-cycle outbound flush and acknowledged-write keepalive.
Finish stage orchestration, input/script queue order, clock-driven idle/watchdog,
reason panels, actual transfer/reconnect and browser composition. Required engine
services survive login/logout and renderer reconstruction; activation is not a session.

### Phase 2: valid inbound actor traffic

**Owners:** selected protocol adapter → existing GameState player/NPC collections.
**Spec:** [SPEC-0022](../specs/SPEC-0022-actor-effects.md) covers effect facts; rich facing and the interaction target remain open.

**Status: partial.** Hitmark, health bar and spot animation blocks are decoded and retained. Selected rejected NPC blocks remain rejected; freeze, tint, transparency and facing-entity blocks are consumed without retained facts.

Port selected rejected BAS/head/body NPC blocks in exact writer order. Retain
consumed-only appearance, freeze, facing and customization facts with their source
policy. Keep full-stock masks separate from what the selected server writer emits.
Test partial packets, malformed input, reset/removal, index reuse, appearance changes
and replay/fingerprint coverage. A decoder accepting bytes does not close this phase.

### Phase 3: map lifecycle and world views

**Owners:** protocol, model/GameState scene lifecycle, application loading and scene.
**Contract:** [SPEC-0023](../specs/SPEC-0023-map-loading-state.md), accepted retained-scene slice.

**Status: partial.** Retained-scene slice done (previous scene stays drawn, loading box after 250 ms, replacement starts at the end of the packet batch, `GameState` owns the retired scene). Instances, world views, extended bounds, rotated chunks and travel acceptance are open.

Preserve previous-scene display until coherent replacement. Complete instances,
rotated chunks/collision, extended bounds/residency, world-view/entity identities and
transforms, travel cancellation and teardown. Rebuild packets currently rejected or
unintegrated remain blockers. Coordinate types and reset ordering precede view consumers.

### Phase 4: incremental zones and world objects

**Owners:** GameState projection journal → application/scene preparation → renderer.
**Contract:** [SPEC-0024](../specs/SPEC-0024-zone-updates.md), accepted incremental-location slice; ground items and complete zone semantics remain separate obligations.

**Status: partial.** Owning-zone slice done; decoded assets are shared across loads and warmed at startup. Ground item state, menus and actions are done (SPEC-0029); pile models, temporary merges and live acceptance are open.

Server location add/change/delete and animation are present. The accepted slice now
implements 8×8 owning-zone preparation and persistent residency for ordinary replacements.
The bounded cache/resource diagnostic retains 273 zones while replacing two, matches the
full Rust projection, and unchanged publication uploads zero bytes. This is implementation
evidence, not live source visual or whole-client performance acceptance. Continue to verify
Invalidate old/new footprints, adjacent normals, decorations, bridge/storage planes,
roofs, collision, picking and minimap dependencies; preserve stable ordering and
cross-zone identity without seams or duplicate draws. Separate initial bake from live replacement.

Publish related resources coherently, reject stale generations, retry failed uploads
and preserve every semantic commit. Background staging now enforces the initial
2 MiB / 1 ms CPU enqueue admission ceiling per frame; critical actor/overlay work shares the
frame budget. Complete temporary merges and authoritative ground stacks/counts/
visibility/customization, menus and validated actions. Retire GPU resources only
after their submissions complete. Do not add another model or semantic cache.

### Phase 5: shared input, picking, menus and actions

**Owners:** input/application/game-ui, with thin native/browser adapters.
**Contract:** [SPEC-0021](../specs/SPEC-0021-input-menus.md); input arbitration accepted,
full menu/input contract still draft.

**Status: partial.** Arbitration is accepted and implemented; the `shared-input` row is still `incorrect` (winit-bound logic in `NativeApp`). The plugin menu stage is accepted and implemented for world and widget menus (SPEC-0021): row projection lives in `application::menu` and edit application in `plugin-host::menu`, both unit-tested without `NativeApp`.

Move reusable winit-bound picking/menu/key/hover logic out of NativeApp. Finish
player/ground-item actions, targeting/sub-ops, drag/drop packets, dialogue resumes,
attack/hidden-op policy, tall/submenus and source picking order. Add widget
incarnations for stale callbacks. Preserve menu/sort/open/select stage hooks; do not
infer these from frame-end snapshots. Test focus, keyboard/IME, clipboard and clipping.

### Phase 6: actor effects and facing

**Owners:** existing GameState actor/effect owners, scene poses and UI overlays.
**Dependency:** phase 2 facts; [SPEC-0022](../specs/SPEC-0022-actor-effects.md).

**Status: partial.** Hitmark, health bar and spot animation facts are retained and projected (SPEC-0022). Open: drawing them, the interaction target and rich facing, projectiles, tint, freeze, the `limit`-driven splat replacement, definition-driven durations.

Complete source-rich target facing/apply/reset policy, interaction slowdown, animation
readiness/freeze/callbacks, delayed spot animations, hitsplats/health bars, tint and
temporary overrides. Add world projectiles/graphics with lifetimes and retargeting.
Speech/hitsplats change overlays without rebuilding bodies; transforms, poses and
appearances invalidate separately. Extend pose reuse; evaluate GPU acceleration
only after source correctness and CPU/GPU/latency/memory measurements show a benefit.

### Phase 7: interfaces and client-script host

**Owners:** GameState interfaces, client-script, application host and game-ui.
**Contract:** [SPEC-0025](../specs/SPEC-0025-interface-script-host.md), scoped acceptance.

**Status: partial.** Chatbox prompts answer the server; the settings list builders run without unsupported ops; `interface_audit` baselines 936 of 970 groups clean (33 script-error, 1 missing-assets). Open: the 33 groups, text-input widgets, translucent models, widget mutation packets, listener lifetime.

Keep IF1/CS1, indexed item search, deferred operations, listener indexing and retained
paint foundations. Complete widget mutation packets, inventory bindings/model widgets,
content/option effects, missing CS2 operations and exact listener lifetime/order.
Retain slot/icon/text/draw data with explicit asset/script/variable/input dependencies;
unchanged/hidden UI does no repeated layout/tessellation/upload work. Separate dragging
and overlays from base content. Asset arrival is presentation invalidation, not a game event.
Use the CS2 ledger for the live denominator; opcode dispatch is not semantic parity.

### Phase 8: variables, chat, social and trading

**Owners:** GameState variable/chat/social/trading state, protocol/cache and script queries.
**Spec reservation:** SPEC-0026, not yet authored.

**Status: open.**

Complete var groups/clan/varcs and persistence, friend/ignore/clan/private channel
membership/dedupe/ranks, player options/attack policy, stock market/trading state,
reset/replay and consumers. Public chat is an existing subset; relationship-dependent
plugins cannot close until these facts are authoritative.

### Phase 9: audio

**Owners:** audio semantic scheduling/cache and native/browser playback adapters.
**Spec reservation:** SPEC-0027, not yet authored; amend SPEC-0003 as needed.

**Status: open.**

Complete definitions/resources, packet/script/animation triggers, priorities, delays,
attenuation, ambient/area effects, music and bounded queue drain/expiry/playback.
Test actual native/browser output and teardown. An existing bounded queue is not audible parity.

### Phase 10: camera, world map, preferences and worlds

**Owners:** existing camera/map application owners, GameState/preferences and adapters.
**Spec reservation:** SPEC-0028, not yet authored; amend SPEC-0007 as needed.

**Status: partial.** Minimap zoom, mapfunction icons and the Minimap plugin are done; item, friend, team and clan dots need phase 4 and 8 facts. World map: buttons' sprites load from the cache, the live failure is unexplained. Camera, preferences and worlds are as described below.

Keep retained map composition, inverse section mapping and guarded normal click dispatch.
Finish map alignment/wrapping, element hover/menus, smooth targets/zoom, flash lifetime
and staff shortcut; audit state/replay ownership. Verify administrator teleporting live
and ordinary-account denial; the server controls rights and authoritative movement.
Complete scripted/cutscene camera, remaining preference effects, remembered window
bounds and profile-supplied world selection/hopping. Transfer decoding already exists;
world switching remains unintegrated.

### Phase 11: classic GPU completion

**Owners:** scene and renderer; [GPU-AUDIT](../reference/builtin-plugins/GPU-AUDIT.md)
and [gpu-audit.json](../reference/builtin-plugins/gpu-audit.json) own source/owner gates.

**Status: partial.** Nine classic gates remain open. The texture array is sized to the largest id and the uploaded static triangles are released after upload; neither closes a gate.

Finish in dependency order: extended loading with matching fog/culling/picking/
residency; source-correct float vertices/removeVertexSnapping; measured worker/upload
scheduling and meaningful numThreads; tint/alpha metadata/world-view/instance transforms
and ordering/frustum cases; FPS/vsync/reconstruction/native/browser lifecycle; exact
source comparisons and test dispositions. Nine classic gates remain open. Keep
xBR undefined-variable and other documented shader corrections explicit. 117HD is a
separate future scope. GPU remains an always-on service; software rendering is deferred.

### Phase 12: RuneLite hooks, providers and feature ports

**Owners:** existing API/bridge/host/first-party/runtime/shared-shell crates.
**Contract:** SPEC-0004/0011 and ADR-0006.

**Status: partial.** Menu Entry Swapper and Ground Items join the earlier ports (18 partial modules of 131 slots). The menu hooks, `MenuOptionClicked` for every entry, `ScriptPostFired` and the ground item events are emitted; info boxes, minimap dots and tile text are drawn. Camera target commands remain unconsumed.

Keep implemented private call authority, grants, activation/publication identities,
subscriber routing and immutable retained panels. Complete authoritative hook-stage
emission, dynamic widget identity, stable profile storage, monotonic elapsed clock,
definition/image services, overlays/tooltips/infoboxes and menu APIs before dependent
ports. Track declared → emitted → reachable → independently verified separately.
Port local features by dependency/disposition; finish source-test case inventories and
actual visual consumers. Notes/XP subsets are foundations, not full reference ports.
External ABI 0.1 compatibility stays required; executable ABI 0.2 and browser guest
execution are later work, not prerequisites to pretend complete in this pass.

### Phase 13: browser, packaging and final acceptance

**Owners:** shared application semantics and web/platform adapters.
**Contract:** SPEC-0003 plus each subsystem's platform acceptance.

**Status: open.**

Connect cache/transport/session, cycle/input, scene/game UI, audio and guest runtime.
The shared sidebar harness and Wasm/WebGPU probes do not establish browser gameplay.
Complete device loss, DPI/resize/fullscreen/monitor changes, login/reconnect/toggles/
cancellation and bounded memory tests, then packaging and all applicable acceptance.

## Completion and maintenance

For each slice: freeze source inputs, accept/amend its owning contract, implement in
existing owners, add source-backed semantic/reset/replay/lifecycle cases, update the
matrix and relevant packet/method/plugin ledgers, and record exact commands and open
gates. Planned spec numbers above are reservations, not accepted documents.

Source-declared members, file counts, schemas, shader compilation and green Rust
contracts never establish full parity. Keep historical evidence scoped to its snapshot.
A behavior-changing improvement needs an evidenced [deviation](PARITY-DEVIATIONS.tsv).
Update these canonical owners instead of creating another competing current plan.
