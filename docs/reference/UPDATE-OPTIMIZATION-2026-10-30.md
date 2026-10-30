# Prepared scene updates and retained pose uploads — 2026-10-30

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
