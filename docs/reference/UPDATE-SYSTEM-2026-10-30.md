# Update-system latency review — 2026-10-30

Status: source review and bounded diagnostics, with scheduling/lifetime corrections. **Door
smoothness and general gameplay frame pacing are not accepted.** Runtime behavior targets 240;
241 supplies implementation ideas. The reference endpoint refused the connection during this
review; no live Java observations or source/runtime MATCH results are claimed.

## Findings

| Priority | Path and cause | Disposition |
| --- | --- | --- |
| P0 | Every structural scenery change calls `application::reproject_scene`, rebuilding terrain, all static locations, dynamic locations and the full picking index. Publication performs full static compaction/metadata creation and geometry allocation on the event thread. | Open: dependency-correct incremental projections and bounded uploads. |
| P0 | Native started projections from inbound operations before the rest of the packet group/cycle committed. Obsolete completions were installed before another build started. | Changed: schedule after committed cycle work; reject obsolete completions before GPU/picking/minimap publication. Packet/listener order is preserved. |
| P1 | Same-asset scenery uploads erased resident and pending actor geometry. | Changed: retain both streams only for an explicit refresh inside the same live scene. New scene/session installation clears actors even if cache IDs repeat. Manual GPU resource diagnostic covers refresh, rejection, repeated-ID reinstall and asset change. |
| P1 | Every scenery rebuild regenerated the minimap from the same immutable asset. Its builder does not consume live overrides. | Changed: rebuild on asset/plane change. **Live location/morph/collision/minimap invalidation remains missing**; caching identical output does not implement it. |
| P1 | Animation results were rejected on every scenery generation change and while any projection loaded. Old workers could release a newer in-flight slot; static completion could rewind compatible newer poses. | Changed: validate session, worker activation, cycle, geometry dependencies, roofs and interpolation; preserve compatible newer poses. Animation across genuinely changed structural geometry still depends on incremental publication. |
| P1 | Each inbound operation cloned scenery maps and rediscovered morph selectors across all object definitions. | Changed: index immutable selector dependencies in `SceneAsset`; compare borrowed state when projection generation changes. Owned snapshots remain worker inputs. |
| P1 | Native walked widget visual dependencies after every unrelated inbound operation. | Changed: collect once after the cycle batch. Asset-completion and script-specific paths remain. |
| P1 | Animated scenery re-enumerates every catalog location, resolves overrides/morphs, plans decorations and allocates pose output per build. Overrides repeatedly scan the list. | Open: indexed dependencies, bounded invalidation and reusable pose storage, measured under realistic override/animation counts. |
| P1 | Native poses all visible actors each advanced presentation batch; missing resources/cache arrivals change the cost. | Existing motion authority and reused buffers/staging remain. Open: crowded-scene measurements and bounded pose/metadata/upload work. |
| P1 | Widgets are projected every frame. Layout already has an interface-revision guard; visual invalidation needs pointer, timer, drag, text/model and script dependencies. | Open: retain visual projections without suppressing CS2 listeners, dynamic-widget replacement, IME or pointer behavior. |
| P1 | Ten catch-up cycles may each execute timer scripts and plugins before presentation. Ingress is bounded and groups indivisible; some local UI/asset operations still bypass the full shared phase gateway. | SPEC-0020 remains incomplete. Reduce derived work without dropping commits, timers, replay records or plugin event ordering. |
| P2 | New location definitions clone/extend world assets; each structural job spawns a thread. Renderer fingerprints the texture catalog and recreates static/dynamic buffers. | Open: measured resource reuse, persistent bounded/coalescing worker and upload budgets. Pipelines/targets/unchanged textures were already reused. |

## Reference evidence

Selected-file hashes and raw measurements are in
[UPDATE-SYSTEM-EVIDENCE-2026-10-30.json](UPDATE-SYSTEM-EVIDENCE-2026-10-30.json).
Reference trees are unchanged and have no RustClient runtime dependency.

- **Stock 240:** `classDS.method3605` processes pending location lifetimes and model readiness;
  `classMI.method7635` removes/replaces a location and updates collision;
  `classFK.method4839` constructs replacement renderables. These are semantic authority. Do not
  turn an instant door ID/orientation replacement into a fabricated swing or fade.
- **Dynamic objects:** `DynamicObject.method3726` advances animation from client-cycle elapsed
  time; `method3732` resolves/poses the model. Worker/cache arrival cannot restart the sequence
  or become its clock. Random starts, transition/reuse and pending-spawn lifetimes remain open.
- **RuneLite GPU:** `Scene` mutation paths call `invalidateZone(x >> 3, z >> 3)`;
  `GpuPlugin.invalidateZone` marks once. `onPostClientTick`/`rebuild` rebuild dirty 8×8 zones,
  preserving unrelated resident streams. This supports post-commit coalescing and local
  rendering resources, not a second semantic scene or external plugin GPU handles.
- **Supporting TSPS 241:** `webgpu/locUpdates.ts` versions requests, accumulates dirty groups,
  batches reloads and rejects old versions. `WebGPURenderer.queueLocReload` and partial apply
  separate door/location geometry from terrain. Adopt dependencies and stale rejection; do not
  copy door-name classification, decoration/depth adjustments, smoothing defaults, timers,
  fades or its browser reload delay as OSRS behavior.

## Measurements and diagnostics

`scene_probe CACHE --updates` uses OpenRune240, a 104×104 scene at (3168,3168), nine catalog map
squares, 21,610 catalog locations and 8,455 admitted locations. After initial mesh warm-up it
removes/restores real openable boundary object 12986 at (3186,3268), plane 0, nine times. This
is an equivalent small structural change, **not a recorded server door packet/animation**.

The first dev run (actual client packages use `opt-level=2`, with assertions/symbols) had
1,930,719/1,930,992 expanded vertices; restored vertex data was 46.34 MB. Mesh CPU time was
152.54–183.05 ms (median 154.20), picking 6.21–10.91 ms (median 8.00), minimap 3.60–4.02 ms
(median 3.85). Mesh/picking run on a worker in the application. Sampling points mainly to
location/model construction, seams and emission. Sampled peak process footprint was about
387.5 MB; this is not a whole-client memory budget.

Release CPU samples and actual GPU replacement enqueue timings are recorded in the evidence
JSON. `--updates-gpu` uses a separate temporary device for eight real same-asset replacements;
initial device/pipeline creation and completion waits are excluded from timing. These do not
measure presented frame times. Live `RUSTCLIENT_FRAME_TIMINGS=1` now exposes scene upload,
minimap building, widget projection and widget dependency collection alongside existing
frame/tick/present-gap and actor stages.
GPU sample 0 can include deferred initial queue writes; subsequent samples drain prior work
outside the timed interval. All eight raw samples are retained rather than treated as a stable
benchmark or a live frame distribution.

Release mesh rebuilding had a median of 131.46 ms. Same-asset GPU replacement took
151.43–217.60 ms of CPU enqueue time (median 166.62 ms), including static compaction, metadata
and resource creation. This directly reproduces a long synchronous installation stall in the
same renderer method used by native publication. It does not prove the live distribution or
attribute that entire interval to GPU execution. Moving only the mesh builder onto a worker
does not remove this stall.

The host was not isolated (other clients active). Retain raw samples and compare the same
profile/workload. The snapshot micro-diagnostic uses a small input map and cannot establish
a whole-client speedup. No before/after door-FPS improvement is claimed; full mesh rebuilding
is unchanged.

## Next work in dependency order

1. Preserve the source distinction between initial scene bake and live replacement. Trace
   normals, terrain shadows, floor-decoration lift, bridge planes, old/new bounds, decoration
   displacement and collision lifetime. A patch cannot re-bake unrelated lighting or leave
   obsolete targets. Complete temporary merge/pending-spawn timers.
2. Index effective locations and reverse dependencies in the existing scene owner: location key,
   morph selectors, resource closure, source ordinal and coverage. Dirty source-defined dependent
   old/new zones, including crossing models/decorations. Preserve source order and authoritative
   coordinates; 8×8 rendering partitions must not redefine game semantics.
3. Separate immutable terrain/catalog lifetime from changed locations/poses, local picking and
   live minimap output. Prepare/validate CPU geometry and metadata off the event thread; retain
   unchanged GPU resources and buffer capacity. Publish a coherent generation. Bound/coalesce
   jobs and upload bytes; measure `numThreads` before claiming meaningful parallelism. This is
   still the classic GPU upload-scheduling dependency.
4. Measure animation/actor and widget/script/plugin work using representative populations,
   overrides, listeners and event interests. Retain 50 Hz semantic/event order; interpolation
   remains an explicit plugin policy. Retained UI needs complete visual dependency tracking.
5. Bounded live acceptance: repeated single/double doors, adjacent decorations, simultaneous
   vars/morphs/animations, bursts, A→B→A while busy, region transition/reconnect, roof/interpolation
   changes, asset arrival, hide/resume and device rebuild. Record event-to-commit, commit-to-visible,
   frame/present-gap p50/p95/p99/max, CPU, allocations and upload bytes. Compare under ADR-0004.
   Browser gameplay and native/browser pacing remain open until observed.

Validation results are in the evidence JSON. Source/resource contracts do not close classic
GPU parity, general update parity or live visual acceptance.

Final validation passed: formatting; workspace/all-target checking; strict workspace/all-target
Clippy; Wasm checking and strict Clippy; both port/GPU ledger checks; 220 tests in scene, renderer,
application and native. Five tests were ignored by default; the selected actual-GPU resource
lifetime test passed separately, leaving four ignored tests unrun. This is not a full workspace
test run or a Java/visual parity result.
