# Prepared scene updates and retained pose uploads — 2026-10-30

Current status: [audit](rs240-port/CURRENT-AUDIT.md) and [roadmap](../plan/PORTING-ROADMAP.md).
This document is an ordered collection of dated evidence, newest first. Older
remaining-work statements describe their checkpoint; later implementations can
supersede them without extending the original measurements or validation.


## Stable presentation, listener discovery and world-map clicks (2026-10-31)

This checkpoint follows the retained UI work below; it does not replace its frozen
results. The pre-change executable and Rust sources were preserved under
`target/stable-60-20261031/`, with source digest
`d543e9086e9b6b48902030d21fbb7e321235cadffe3ebdc604378d822c9b0a54`
and executable digest `b11b8075ac0624c8ad5cb8521dd40d307ce8b3c07f4145d18c7ae37c3f15c261`.
The working tree was committed externally as `ef9b2af9` during this work; that is
not a new independent acceptance result. Current frozen hashes and commands are in
`stablePresentation` in the [evidence JSON](UPDATE-OPTIMIZATION-EVIDENCE-2026-10-30.json).

The native frame limiter now retains absolute deadline phase after ordinary late
wakes. Missed periods skip without catch-up bursts; cap/policy changes, occlusion
and suspension reset scheduling. GPU and FPS-plugin policy remain the same.
Optional diagnostics distinguish CPU stage wall time, acquire/submit/present-call
spans and asynchronous GPU intervals. The GPU reader has three slots and never
waits for completion on the rendering path. Its portable path timestamps the first
executed world/UI render pass through the final display pass using base
TIMESTAMP_QUERY support, including Metal stage-boundary counters; it requires no
extra pass or optional encoder-timestamp feature. Preceding queue uploads and actual
scanout are outside this interval. Unsupported GPU timing remains unknown.
Raw timing uses an 8,192-record nonblocking writer queue; overflow, incomplete output
or lost GPU samples invalidates the corresponding scored gate. `window_probe.py`
now enforces the declared 60 Hz gates instead of accepting 60% of the requested cap.
A short smoke remains explicitly separate from three five-minute measurements.

Title geometry is retained by screen/input/asset identity, visible blink state and
actual hover controls. Flame content revisions change at existing 20 ms simulation
steps, so duplicate presentations need no flame upload. The game clock, presentation
quality and draw distance 184 are unchanged. Exact visual comparison remains manual.

`ClientScripts` builds listener candidates only when interface topology, runtime
listener membership/triggers or the loaded definition catalog changes. It resolves
current listener arguments/overrides before enqueueing, preserves sorted address
order and trigger filters, and still runs required timer callbacks. Text/property
updates do not invalidate membership. The metadata revision is internal and excluded
from semantic fingerprints and the external ABI. This does not certify every already
queued callback against arbitrary replacement during a different callback; complete
source callback-identity/lifecycle coverage remains a separate audit dependency.

Map viewport/overview composition now uses the existing bounded native worker pool.
One request runs and the latest desired view coalesces; session/resource/request
checks and full dependency comparison reject obsolete output. Immutable region,
sprite/font and composite data are shared by `Arc`; one owned spare output pair is
moved between worker and presentation without cloning pixels. Closing the map drops
retained output/scratch and cancels work. The existing 32 MiB region-raster budget
and 2,048-side dynamic-image limit remain. Valid pixels survive loading/failure;
nonzero image revisions suppress unchanged uploads, with reconstruction/upload retry
remaining renderer-owned. Flash clocks invalidate only when a potentially visible
flashing element changes its visible phase. The existing visual flash period is
preserved; full source flash lifetimes and target/zoom smoothing remain open.

Normal map input belongs to shared `scripts::worldmap`: stock240
`RenderOverview.method12459` calls coordinate resolution before drag, and
`method12464` applies the previous-press **500 ms / 25 pixel** guard. Display
coordinates pass through the active area's inverse section mapping. Native only
supplies game-canvas coordinates, monotonic time and modifiers; focus loss, closure,
resize and session replacement revoke gestures. There is no single-click bypass.
The separate stock staff modifier shortcut remains unavailable and is not substituted
with a normal click. The typed intent does not mutate the local player.

The selected RSProt `ClickWorldMapDecoder` calls `g4Alt1`; stock240
`classXY.method13284` agrees. **Opcode 78 is four-byte packed source coordinate,
little-endian**, unlike the supplied TSPS packet/release-click conventions. For
plane1/x3200/y3201 the payload is `81 0c 20 13`. The adapter validates both 14-bit
coordinates before advancing ISAAC. The source jar/hash and extracted decoder are
preserved in `target/stable-60-20261031/map-codec-source.json`. OpenRune's
`WorldMapScript.kt` registers `onWorldMapClick(Rights.ADMINISTRATOR)` and its handler
uses the current player level. The server owns authorization and teleporting.

The [TSPS shortlist](TSPS-GIT-SHORTLIST-2026-10-29.md#current-reconciliation--2026-10-31)
now ranks remaining actor preparation, widget granularity, ground/combat effects,
map/menu/script and audio work against current Rust owners. Existing item search,
IF1/CS1, worker admission and retained UI are removed from the current missing-work
list. Stock240 and pinned RuneLite keep their existing authority; TSPS remains
supporting evidence, and reference trees/shader correction dispositions are unchanged.

Current executed results and performance/visual/live gates are recorded below when
available. Full 256/1,024-actor workloads, cold resources, a 30-minute lifecycle soak,
commit-to-visible latency, 20% transient-memory reduction, ordinary-account denial,
scanout cadence, browser gameplay and exact offline-reference visuals are **not
established by these implementation changes**. No global “fastest engine” claim.

## Retained UI canvas (2026-10-31, subsequent checkpoint)

The renderer now reuses the existing encoded UI canvas texture when its commands
and dynamic pixels are unchanged and neither the current nor previous frame has
transient overlays. This adds no full-screen texture or extra widget command copy.
Removing the final overlay forces a raster pass even with zero vertex upload bytes,
so its pixels cannot remain on screen. Dynamic pixel uploads, a new command identity,
atlas replacement, canvas resizing/reconstruction and renderer reconstruction force
repainting. The world and final display composite continue on every required frame.
`UiStats.raster_passes` records actual UI raster decisions in the shared production path.

The explicit `interface_probe CACHE_DIR 12 149 548 161 --retained-ui-gpu` diagnostic
submitted 15 lifecycle steps per cache-authored root on the actual GPU (60 total).
The unchanged/recovered/reconstructed cases submitted zero UI raster passes and
zero vertex/texture upload bytes. Adding/removing overlays, recovering from unknown
assets, replacing the atlas, resizing and reconstructing resources forced repainting.
GPU validation reported no errors. No pixels were read back and no GPU execution
time was measured; this is a lifecycle/submission contract, not the ADR-0004 visual
comparison or a live performance gate. The later `uiRasterRetention` evidence section
records this checkpoint separately from the earlier frozen widget-only validation.

The CPU workload additionally compares a static frame and the same frame with two
transient overlays. Static retained frames prepare/upload no vertices and become
eligible for raster reuse; the overlay case prepares only the two transient quads.
For cache root 548 (43 widget quads), medians across three rounds' 27 raw measured
batches of 2,000 iterations were:

| CPU preparation workload | Fresh batch | Retained batch | Requested vertex bytes/iteration |
| --- | ---: | ---: | ---: |
| Static widgets | 3.2740 ms | 0.0261 ms | 8,256 → 0 |
| Same widgets plus two overlays | 3.4303 ms | 0.1475 ms | 8,640 → 384 |

These measure CPU preparation, not GPU copy/execution time or live frame latency.
The host was not isolated; all samples/outliers remain in the JSON. Root 149 has no
visible quads without CS2 and remains the no-geometry-benefit control. Native
unchanged-frame painting also remains zero in the retained paint workload. The
existing 6 MiB vertex scratch capacity is preserved: lazy geometric growth for
clipped rotated quads was rejected during review before final validation. No RSS
or whole-update memory improvement is inferred from these counts.

Unchanged transient commands still repaint conservatively. Fine-grained widget/slot
changes, world-map pixel retention, ground items/combat effects and live 256/1024-actor,
memory and visual acceptance remain open.

Final source `d543e9086e9b6b48902030d21fbb7e321235cadffe3ebdc604378d822c9b0a54`
passed formatting, workspace/all-target checks, strict workspace/all-target Clippy,
380 scoped contracts (seven ignored), the Wasm check and native binary build.
Source hashes match before/after every gate. Exact commands/logs and the interrupted
pre-capacity-review attempt remain separate in the evidence JSON. Browser gameplay,
full native device loss and the live/visual/stress/memory gates remain NOT-RUN.

## Retained widget preparation (2026-10-31, continuation of the foundation)

The native painter now retains its widget command prefix. Its application-owned
presentation dependencies name the interface revision, definition-catalog revision,
drag geometry and the facts actually read by CS1. Successful varbit reads retain
the owning varp dependency, avoiding cache decoding on unchanged-frame checks.
An unobserved variable does not invalidate the paint. Missing facts remain explicit
errors and are rechecked; no legacy inventory binding is invented. Asset merges and
plugin sprite replacement invalidate painting independently of gameplay events.
Timers, listeners and semantic commits continue through their existing owners.

Speech uses a separate transient command layer at the world widget's painter
boundary. Mounted interfaces, pointer feedback and menus retain their order.
`UiFrame::seal_widgets` supplies a fresh owned content identity; renderer preparation
retains widget vertices and batches and uploads transient vertices at the buffer
suffix. Atlas replacement, viewport/DPI/stretch changes, a new seal and renderer
reconstruction invalidate prepared vertices. Preparation failure invalidates its
key before a retry. `UiStats` reports prepared/retained quads and vertex bytes
separately from dynamic texture bytes, available in native timing notes.

The explicit `interface_probe --retained-paint-benchmark` workload compares fresh
and retained painting/preparation in the same optimized binary with the same cache
and frame, retaining two warm-up and nine measured batches. It exercises painter
order, five DPI transforms, stretching/resizing, texture layout, content identity,
atlas invalidation and missing-asset recovery against fresh preparation. These are
CPU command/vertex checks, not screenshots or the ADR-0004 visual parity gate.
The cache-authored roots are measured without CS2: 12/548/161 contain 34/43/50
quads; root 149 is empty and is retained as a no-benefit geometry case. A pair of
diagnostic transient rectangles exercises the two overlay layers without creating
gameplay events. Raw logs, binary/source identities and final validation are recorded
in the evidence JSON's `retainedWidgets` section.

This slice skips unchanged-frame painting and widget vertex uploads; a changed
widget can still repaint the interface prefix. Per-widget/per-slot preparation,
GPU raster retention (completed for static UI in the subsequent checkpoint above), world-map image retention and full inventory/chat gameplay
acceptance remain open. It does not complete ground items/combat effects or establish
live frame pacing, GPU timing or process-memory improvements.

## Incremental update foundation (2026-10-30, baseline `56e822b5`)

The accepted incremental-update plan is broader than this foundation. Its
requirements are recorded in SPEC-0002, SPEC-0003 and SPEC-0007. Historical
measurements below retain their original scope. The evidence JSON's
`incrementalFoundation` section records this separate implementation and validation.

- The single successful-operation gateway records bounded projection history
  with independent consumer cursors. Typed location, variable, widget, inventory
  slot, received-chat and actor-overhead keys supplement broad domain fallback.
  Expired readers receive the affected domains to refresh. Failed/no-op
  operations do not manufacture changes. Cache-authored nested script commits
  use the same gateway. This metadata stays outside semantic/replay fingerprints
  and the external plugin ABI; schema version 29 remains unchanged.
- Sparse inventory patches validate first, retain last-write/no-op semantics and
  apply final slot values in place. They no longer clone the entire container.
  Empty versus absent containers and extension by empty slots remain observable.
- Native widget layout survives unrelated semantic operations. Its key includes
  interface revision, controlled definition-catalog revision and drag geometry.
  Asset dependency collection also skips unchanged interface/catalog inputs,
  while newly requested plugin sprites still trigger loading. Painting live
  values, timer/listener execution and overlays continue; full retained painting,
  per-slot icon updates and unchanged-quad upload suppression remain open.
- Scene loading, structural preparation, animated scenery, actor assets, widgets
  and world-map batches share one persistent 1..4-worker pool with at most 64
  queued jobs. Cancellation, fault notification and session/resource/request
  identities prevent abandoned work from clearing newer pending state. Actor
  assets have at most one outstanding request; revoked/rejected requests retry.
  Queue count is not a byte budget. Large builder/decoder calls still need finer
  cancellation slices. This does not implement the GPU plugin's `numThreads` option.
- Timing logs retain every sample in bounded histograms, with conservative
  0.1 ms quantiles, lifetime p99, maximum and >25 ms presentation-gap counts.
  Skipped/reconfigured frames remain in presentation gaps; occlusion and explicit
  surface suspension reset the origin. Commit/script and worker-queue
  diagnostics are available. Frame wall time includes presentation; active CPU,
  GPU timestamps and full decode-to-visible correlation remain open.

The optimized inventory probe runs the same source against the frozen baseline
crate and current crate. Three alternating baseline/current rounds, each with
two warm-up and nine measured batches, preserve all raw samples:

| Workload | Baseline median | Current median |
| --- | ---: | ---: |
| 200,000 single-slot updates; 28-slot inventory | 19.1883 ms | 8.2742 ms |
| 20,000 single-slot updates; 16,384-slot container | 83.7974 ms | 0.8191 ms |

Both final semantic fingerprints match. Earlier very short probes were noisy,
including a small-container regression; those samples are retained separately.
The larger repeated workloads are CPU diagnostics on a non-isolated host, not
native FPS, allocation/RSS measurements or the 20% whole-update memory gate.
Run the current workload with `cargo run --release -p rustclient-game-state
--example inventory_updates -- 28` (or `16384`).

Ground items, complete combat effects, precise actor-body/pose invalidation,
complete dirty-zone residency, hard upload-byte/time admission, inactive catalog
eviction and GPU acceleration remain acceptance dependencies. Concurrent scenery
patch work in the shared checkout is separate from these foundation measurements.
The 256/1024-actor runs, three five-minute frame-pacing runs, 30-minute memory soak,
live Java comparison and browser gameplay acceptance remain NOT-RUN.

Status: implementation and bounded optimized diagnostics; live frame-pacing/visual acceptance
remains open. Owners: application (owned projection transfer), scene (immutable catalog lookup),
renderer (CPU preparation, resident buffers and staging), native (worker/publication composition).
Contract: [SPEC-0016](../specs/SPEC-0016-reference-scenery-parity.md). This continues the
[latency review](UPDATE-SYSTEM-2026-10-30.md); its original measurements remain historical evidence.

## Changes

- `PreparedWorldMesh` owns its immutable source mesh together with lossless vertex packing,
  camera-independent draw metadata and texture identity. Preparation has no GPU/device/platform
  handles. `LoadedScene::try_map_mesh` transfers this payload without copying the asset or picking
  index or introducing another semantic state owner. Native load/reprojection workers prepare it;
  obsolete results are rejected before publication. Consuming publication returns the source mesh
  for picking/device recovery. Device limits are checked before queued writes.
- Static and dynamic GPU buffers retain sufficient capacity. Replacement writes only the current
  streams; growth happens when required. Pipelines, targets and unchanged textures remain resident.
  Actor retention requires an explicit refresh inside the same live scene; scene/session installs
  clear actors even if cache identity repeats. A newer compatible dynamic pose is staged before
  the first presentation of a completed static job.
  Extending the decoded catalog inside a live scene restarts its asset workers without treating
  the extension as a new scene/session and clearing its actors.
- The immutable scene now indexes source location addresses by typed location key. A single
  definition query folds relevant committed overrides without allocating a whole effective-location
  list. Batch extraction makes one source pass instead of retaining the full list once per override.
  Duplicate keys, original source order, appended replacement order, morph resolution and unusual
  raw cross-layer inputs retain their previous behavior. The index is derived catalog data.
- Actor and animated-scenery staging compares all GPU inputs: vertices, indices, model spans,
  depth policy and full sorted-model admission metadata, including unused posed vertices. Identical
  poses skip copying, metadata rebuilding and GPU writes. Unknown GPU lifetimes still receive the
  first input, including empty geometry. Failed uploads stay pending; an identical later update
  cannot erase work awaiting retry. Camera sorting, shader cycles, semantic commits, labels and
  picking remain independent and continue updating.

No animation timing, gameplay authority, packet order, lighting, geometry placement, shader
arithmetic, decoration offsets, smoothing defaults or plugin capabilities are changed. No reference
tree is modified. This is an ownership/scheduling optimization, not a new rendering effect.

## Evidence and reproduction

[UPDATE-OPTIMIZATION-EVIDENCE-2026-10-30.json](UPDATE-OPTIMIZATION-EVIDENCE-2026-10-30.json)
records raw timings, environment, commands, checks, implementation hashes and reference anchors.
The actual OpenRune240 cache workload is the same 104×104 window used by the preceding review:
21,610 catalog locations and about 1.93 million expanded vertices.

```sh
cargo run --release -p rustclient-native --bin scene_probe -- "$RUSTCLIENT_CACHE_DIR" --updates-prepared
cargo run --release -p rustclient-native --bin scene_probe -- "$RUSTCLIENT_CACHE_DIR" --updates
cargo run --release -p rustclient-native --bin scene_probe -- "$RUSTCLIENT_CACHE_DIR" --updates-staging
```

The prepared diagnostic alternates sixteen inline/prepared replacements on the same temporary
device. It drains initial/prior queue work outside each timed interval and reports CPU preparation,
enqueue and resident geometry bytes separately. It does not present frames or measure GPU execution.
The location workload performs 64 queries with zero/32 overrides. The staging workload repeats the
actual animated-scenery payload 256 times, comparing copy-every-input with retention; its counter
is **requested** uploads, not observed GPU submissions or a real movement distribution.

Final optimized observations (same host, not isolated):

| Metric | Inline/previous policy | Prepared/retained policy |
| --- | --- | --- |
| Publication CPU enqueue, median of 16 pairs | 148.60 ms | 6.28 ms |
| Publication CPU enqueue, worst observed pair | 203.91 ms | 7.70 ms |
| 64 location queries, 32 overrides | 44.9351 ms | 0.0053 ms |
| 256 identical 89,445-vertex staging inputs: upload requests | 256 | 1 |
| CPU staging for those 256 inputs | 13.49 ms | 15.61 ms |

Staging therefore trades about 0.0083 ms extra comparison time per input for avoiding repeated
downstream metadata work and upload requests in this identical-payload workload. The initial
field-wise comparison was slower (29.40 ms); integer POD byte comparison reduced that overhead.
All exploratory and final samples are retained. Changed poses still use the normal upload path.

Worker preparation itself still took a median 139.28 ms, in addition to full scene extraction.
This removes a large event-thread stall; it does **not** make complete door reconstruction instant.
Dependency-correct per-zone work is still needed to reduce end-to-end publication latency. Sixteen
pairs do not establish a stable p95/p99 budget or live frame pacing.

Earlier exploratory runs included substantial outliers while background work was active; those
samples are retained. The host is not isolated. Report median, tail and maximum rather than
discarding slow samples or claiming a whole-client FPS gain. Sampled preparation time is dominated
by lossless packing, with texture hashing and model metadata also visible. Work moved off the event
thread remains real CPU work and retains a bounded additional prepared payload until publication.

## Remaining boundaries

Full scene mesh/picking reconstruction still runs for structural changes. GPU publication still
writes a full replacement; a hard per-frame upload-byte budget and dependency-correct dirty-zone
resources remain open. This pass does not complete `numThreads`, classic GPU parity, live minimap/
collision invalidation, temporary location merge lifetimes or browser gameplay. Initial pipeline/
texture creation and device reconstruction can still be expensive. Other actor/script/widget
projection work needs representative live profiling before additional caching.

The Java reference endpoint refused connection during this run. Live door/actor/roof/region/reset
comparison and frame/present-gap p50/p95/p99 acceptance remain NOT-RUN. Tests cover ownership,
ordered override queries, staging/retry and resource lifetimes; they do not establish appearance.
Final command outcomes and numeric comparisons are in the evidence JSON.

Final checks passed: formatting; workspace/all-target checking and strict Clippy; Wasm checking
and strict Clippy; 223 unique relevant automated tests and two manual GPU resource contracts;
domain/port/GPU audit checks; and a fresh native executable build. Four default-ignored tests
remain unrun. Renderer/native tests were repeated after their final changes without double-counting
them. The running old client instances were not restarted; restart the client to use the new build.


## Incremental-location continuation — 2026-11-01

This section supersedes the preceding full-reconstruction/upload limitations for
ordinary normal-map locations only. Historical measurements above retain their
original source and workload. [SPEC-0024](../specs/SPEC-0024-zone-updates.md) owns the
new resource/publication contract; the roadmap's full acceptance gates remain open.

The existing GameState journal now has an independent native scenery cursor, with
explicit domain refresh after history expires. Spatial queries find old/new footprints,
adjacent-plane normal dependencies and decoration inputs. A model belongs to its
anchor's 8×8 zone once, including crossing models. Structural zone resources persist;
terrain stays resident and animated/morphing poses use their separate existing stream.
Stable source identities preserve opaque and alpha ordering across resources.

CPU preparation runs on the existing bounded pool, without nested per-build thread
creation. Missing object/model definitions extend the existing immutable catalog on
a worker instead of reconstructing terrain, picking or the whole mesh. The former
48-override fallback is removed. The existing three-million-vertex budget also bounds
retained structural/pose output; no cache budget or quality setting was lowered.

GPU groups stage in 256 KiB chunks under 2 MiB / 1 ms admission per frame after actor
updates. They publish only after all matching resources are ready and the application
acknowledges the group. CPU minimap/picking, hidden baseline IDs and GPU geometry switch
for the same next frame. Superseded work does not publish; failures remain retryable.
Replaced allocations retire after queue completion. Browser handles remain on their
owning thread behind completion flags; no rendering path waits for the GPU.

### Bounded mechanism evidence

Frozen sources/binaries, initial dirty patch, complete raw logs and captures are under
`target/incremental-locations-20261101/` (ignored local artifacts). The canonical evidence
JSON records their hashes and exact commands; a fresh checkout does not contain them.
The cache diagnostic changes an actual openable object at `(3186,3268,0)` nine times
through GameState, alternating remove/inherit. It compares visible emitted triangles
with the full Rust projection outside CI and checks actual GPU buffer identities,
deferred acknowledgement and unchanged-publication upload suppression.

The indexed diagnostic changed two of 275 resident zones, retaining 273 buffer groups.
Structural upload was 412,980–416,856 bytes; unchanged publication uploaded zero bytes.
All nine visible triangle comparisons passed. Worker extraction was 8.18–11.20 ms;
preparation was usually 1.67–1.92 ms, with 4.09 and 16.95 ms outliers retained during
concurrent checks. GPU enqueue was 0.104–0.191 ms. These nine observations are not a
representative latency percentile, live door measurement or whole-client FPS result.
Twenty-two bounded before/after PNG captures are byte-identical, including fresh GPU
reconstruction and alternate camera/size/settings. They compare Rust versions, not
stock runtime appearance, and remain separate from ADR-0004 reference acceptance.

### Remaining work

Structural changes still compose a full minimap once on the worker; dirty rectangles
remain open. Cold resource extension still clones the immutable catalog's owned data.
Initial scene baking, device reconstruction, actor preparation, per-slot/widget updates,
queue/retirement cleanup and changing-camera draw counts need representative profiling.
Do not infer process-memory savings from requested GPU buffer byte counts or sum those
counts with RSS on unified memory. No 20% transient-memory result is established.

Live source door/roof/bridge/collision/minimap comparison, rapid replacements and
missing assets, 256/1,024-actor gameplay, open-map interaction, device loss and the
30-minute lifecycle/memory soak remain open. The complete combat/ground-item workload
also depends on unfinished authoritative systems. numThreads remains disabled; no
classic GPU gate, source-case inventory or external ABI status is promoted by this slice.


### Measured initial-preparation memory correction

The first resource/query probe exposed an extra expanded-scene copy during initial
zone partitioning. Its maximum RSS was 478,773,248 bytes versus 431,079,424 at the frozen
baseline. Initial preparation now streams one owning zone and releases expanded input
once packed buffers own the upload bytes. A focused ownership contract verifies those
bytes remain unchanged; all 22 bounded captures still match the frozen Rust baseline.

After correction, the same probe reported maximum RSS 315,310,080 bytes, while macOS
peak footprint was 342,901,576 versus 340,034,400 baseline bytes. Requested GPU geometry
buffers were 25,206,180 versus 25,205,172 bytes; resident vertex bytes were identical.
These are different accounting measures: one initial-process pair is not the update
transient-memory workload, a steady gameplay result or proof of the 20% target. The
higher footprint observation is retained. Driver/textures/targets and shared-memory
accounting prevent summing logical GPU bytes with process RSS.

The preliminary title run was stopped for this correction, marked INCOMPLETE and kept
with all partial samples. It is excluded from scored acceptance. Final matched title
runs use the corrected binary, source/executable hashes and the same declared settings.

The final no-Cargo zone diagnostic again retained 273 GPU groups and changed two.
Extraction was 7.85–9.19 ms, preparation 1.67–1.83 ms, and enqueue 0.103–12.690 ms.
All nine full-Rust triangle checks and unchanged-upload checks passed. These samples
remain a bounded CPU/resource diagnostic, not live presentation or source acceptance.


The final resource sample includes an enclosing enqueue/automatic-publication outlier
of **12.690375 ms**; the other eight were 0.103–0.161 ms. It is retained. Byte admission
passed, but the 1 ms check admits work between calls and cannot preempt an allocation,
write, callback/publication call or OS interruption. No profiler was attached and the
cause is unassigned. This does **not** establish a hard 1 ms wall-time guarantee. Tail
attribution, publication/cleanup timing and larger/cold/live workloads remain required.
