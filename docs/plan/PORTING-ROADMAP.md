# OSRS 240 / RuneLite roadmap

Canonical execution order, reviewed **2026-11-01** against `e2ce6f64` plus the
existing uncommitted performance and plugin/API work. Full parity is incomplete.
The [current audit](../reference/rs240-port/CURRENT-AUDIT.md) records the inspected
baseline and acceptance limits; the [domain matrix](../reference/rs240-port/PORT-MATRIX.md)
owns detailed status. This roadmap replaces the October 29 execution plan and the
original foundation plan; their evidence remains in the [archive](../archive/README.md).

## What is already in place

These are implemented foundations, not completed domains or live acceptance:

- Cache-backed native title/login, normal-map scenery, players/NPCs, ordinary
  movement/turning, public chat, selected widget actions, IF1/CS1 and script execution.
- Bounded cycle-stage inbound admission, indivisible packet groups, queued outbound
  flushes, ping replies, typed logout/transfer handling and shared cycle timing.
- Typed projection invalidations, sparse inventory updates, bounded shared native
  workers, retained scene buffers/identical poses and retained widget layout/painting.
  Ordinary location changes now prepare dependent 8×8 owning zones and retain terrain/
  untouched GPU buffers, with coherent publication and bounded uploads. Live dependency/
  lifecycle acceptance and proportionate work across every system remain open.
- Absolute presentation deadlines, retained title geometry/flames, indexed widget
  listeners, asynchronous retained map pixels, optional nonblocking GPU timestamps
  and stock normal map-click dispatch (packet 78). Live teleport acceptance is open.
- One egui sidebar for native/browser harness, owned reference assets, GPU **Always
  on** settings, capability-mediated in-tree API, actor lifetime handles, subscriber
  filtering, lifecycle isolation and bounded retained Notes/XP Tracker panel subsets.
  Executable native guests remain ABI 0.1; API 0.2 is in-tree plus a deferred WIT contract.

Do not port these foundations again. Extend their existing owners and finish their
missing branches. See [implementation evidence](../reference/UPDATE-OPTIMIZATION-2026-10-30.md)
and [the plugin ledger](../reference/builtin-plugins/FIRST-PARTY-LEDGER.md).

## Next delivery queue

| Order | Deliverable | Exit evidence |
| --- | --- | --- |
| 1 | Establish a valid current-machine baseline, then remove the largest measured update tails. Start with ordinary location replacement, actor preparation and remaining widget/inventory granularity. | Matched raw traces; no unrelated world reconstruction/upload for chat or inventory; ordinary doors avoid whole-scene reconstruction; failed uploads remain pending. |
| 2 | Complete authoritative ground items and actor/combat facts, including selected rejected NPC masks, rich facing, effects, lifetimes and reset/index reuse. | Exact 240/selected RSProt/cache source cases → GameState transitions → replay/fingerprints → menus/rendering → reachable plugin facts. No guessed interaction or synthetic hooks. |
| 3 | Finish shared input/menu/script stage ownership and provider services, then port applicable plugin slices by their dependencies. | Correct stage/order and capabilities; source-case dispositions; actual visual consumers. Menu/script events originate at their owners. |
| 4 | Close classic GPU, map/session and remaining client systems, then browser gameplay integration and release acceptance. | Per-gate source evidence and native/browser lifecycle results; ADR-0004 visual comparison. Inventory/build success alone cannot close a gate. |

Work may overlap only when its prerequisites exist. Measuring supported gameplay
must proceed now; the full mixed combat workload remains blocked on missing
semantic systems. Do not label a reduced workload the full 256-actor gate.

## Workstreams and dependencies

The phase numbers below retain existing spec/research references. They identify
workstreams, not a requirement to repeat completed foundation checkpoints in order.

### Phase 0: evidence and performance baseline

**Owners:** application diagnostics, native performance, scene/renderer probes and
canonical ledgers. **Contract:** SPEC-0008/0019 and ADR-0004.

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

Keep the implemented S4 packet queue, 100 ordinary-packet budget, indivisible groups,
ten-cycle catch-up bound, end-cycle outbound flush and acknowledged-write keepalive.
Finish stage orchestration, input/script queue order, clock-driven idle/watchdog,
reason panels, actual transfer/reconnect and browser composition. Required engine
services survive login/logout and renderer reconstruction; activation is not a session.

### Phase 2: valid inbound actor traffic

**Owners:** selected protocol adapter → existing GameState player/NPC collections.
**Spec reservation:** SPEC-0022, actor synchronization/effects, not yet authored.

Port selected rejected BAS/head/body NPC blocks in exact writer order. Retain
consumed-only appearance, freeze, facing and customization facts with their source
policy. Keep full-stock masks separate from what the selected server writer emits.
Test partial packets, malformed input, reset/removal, index reuse, appearance changes
and replay/fingerprint coverage. A decoder accepting bytes does not close this phase.

### Phase 3: map lifecycle and world views

**Owners:** protocol, model/GameState scene lifecycle, application loading and scene.
**Contract:** [SPEC-0023](../specs/SPEC-0023-map-loading-state.md), accepted retained-scene slice.

Preserve previous-scene display until coherent replacement. Complete instances,
rotated chunks/collision, extended bounds/residency, world-view/entity identities and
transforms, travel cancellation and teardown. Rebuild packets currently rejected or
unintegrated remain blockers. Coordinate types and reset ordering precede view consumers.

### Phase 4: incremental zones and world objects

**Owners:** GameState projection journal → application/scene preparation → renderer.
**Contract:** [SPEC-0024](../specs/SPEC-0024-zone-updates.md), accepted incremental-location slice; ground items and complete zone semantics remain separate obligations.

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

Move reusable winit-bound picking/menu/key/hover logic out of NativeApp. Finish
player/ground-item actions, targeting/sub-ops, drag/drop packets, dialogue resumes,
attack/hidden-op policy, tall/submenus and source picking order. Add widget
incarnations for stale callbacks. Preserve menu/sort/open/select stage hooks; do not
infer these from frame-end snapshots. Test focus, keyboard/IME, clipboard and clipping.

### Phase 6: actor effects and facing

**Owners:** existing GameState actor/effect owners, scene poses and UI overlays.
**Dependency:** phase 2 facts; planned SPEC-0022.

Complete source-rich target facing/apply/reset policy, interaction slowdown, animation
readiness/freeze/callbacks, delayed spot animations, hitsplats/health bars, tint and
temporary overrides. Add world projectiles/graphics with lifetimes and retargeting.
Speech/hitsplats change overlays without rebuilding bodies; transforms, poses and
appearances invalidate separately. Extend pose reuse; evaluate GPU acceleration
only after source correctness and CPU/GPU/latency/memory measurements show a benefit.

### Phase 7: interfaces and client-script host

**Owners:** GameState interfaces, client-script, application host and game-ui.
**Contract:** [SPEC-0025](../specs/SPEC-0025-interface-script-host.md), scoped acceptance.

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

Complete var groups/clan/varcs and persistence, friend/ignore/clan/private channel
membership/dedupe/ranks, player options/attack policy, stock market/trading state,
reset/replay and consumers. Public chat is an existing subset; relationship-dependent
plugins cannot close until these facts are authoritative.

### Phase 9: audio

**Owners:** audio semantic scheduling/cache and native/browser playback adapters.
**Spec reservation:** SPEC-0027, not yet authored; amend SPEC-0003 as needed.

Complete definitions/resources, packet/script/animation triggers, priorities, delays,
attenuation, ambient/area effects, music and bounded queue drain/expiry/playback.
Test actual native/browser output and teardown. An existing bounded queue is not audible parity.

### Phase 10: camera, world map, preferences and worlds

**Owners:** existing camera/map application owners, GameState/preferences and adapters.
**Spec reservation:** SPEC-0028, not yet authored; amend SPEC-0007 as needed.

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
