# Sidebar and walking performance checkpoint — 2026-10-28

Implementation spans application composition, shell UI, platform file reads, scene picking
and disposable GPU resources. `GameState`, movement/collision semantics, plugin ABI and
capabilities are unchanged by this work. Other concurrent working-tree changes are not
attributed to this checkpoint.

## Sidebar behavior

- Hidden startup; distinct hidden / 36px rail / 278px panel+rail states. The arrow toggles
  the whole sidebar; the configuration tool and panel hotkey toggle panel / rail.
- Session selection, filter, section expansion and scroll restoration; Back returns to
  the filtered list. Clear search, accessible persisted favorites and native descriptions.
- Compact charcoal/orange presentation, aligned controls, visible focus and hover.
- Slider drafts update locally and commit once on release; Escape or lost capture cancels.
  Numeric fields commit on Enter/blur; configuration failures restore accepted values.
- Accepted configuration stays application-owned, retaining prepare → persist → publish.
  Presentation-only events do not rebuild the catalog or synchronize configurations.
- Window resize transactions preserve established game width across intermediate events,
  rapid toggles, DPI changes and clamps, with bounded settling. Fixed-window preference
  and maximized/fullscreen reserve space internally. Physical game/chrome rounding is
  separate to avoid fractional-DPI width drift.
- Input staging performs no CPU paint. Presentation performs at most one raster/upload;
  unchanged hover targets perform no update/layout/paint. Scrolling skips layout.
- Repository-owned Blitz and Dioxus compatibility fixes address child reordering, stale
  node identities, overflow hit testing and removed focus/hover references. See
  [vendor provenance](../../vendor/README.md).

## Measured resource and CPU improvements

All numbers below are local diagnostic observations, not portable performance promises.
The host also ran another client and concurrent builds. Timing tails are particularly
sensitive to that contention. Optimized package profiles use Rust 1.99.0 opt-level 2;
shell before/after fixtures used release builds on the same host.

| Workload | Before | After | Scope |
| --- | ---: | ---: | --- |
| 600 pointer moves within one hover target | 600 layouts/paints | 1 initial transition, then zero | retained panel trace; semantic no-work contract also tested |
| 2× search p95 | 42.83 ms | 8.50 ms | complete panel event/render fixture |
| 2× slider draft p95 | 81.54 ms | 18.14 ms | excludes persistence; commits reduced from 122 to 1 |
| 2× scroll p95 | 98.62 ms | 9.90 ms | after performs zero layouts |
| 2× cross-row hover p95 | 176.59 ms | 27.44 ms | hover transitions still paint |
| 20,000 positioned 520-byte reads | 443.16 ms | 15.70 ms | temporary local fixture; 32 retained file handles, no file-content cache |
| Read p95 | 41.13 µs | 1.13 µs | exact/concurrent read contracts tested |
| 600 unchanged-camera ordering calls, p95 | 2.0027 ms | 0.0001 ms | retained index order checked; geometry updates invalidate reuse |
| 300,000-vertex repeated static fixture | 7.20 MB GPU vertices | 1.44 MB GPU vertices + 1.20 MB CPU remap | exact packed bytes, index order and primitive admission retained |
| Live courtyard surface query p95 | 4.8377 ms | 0.0450 ms | 600 identical hits over 613,849 static triangles |
| Live courtyard GPU vertex bytes | 46.34 MB | 17.48 MB | actual portable world allocations; +7.37 MB retained CPU remap |
| 202,500-triangle surface query p95 | 1.2037 ms | 0.0018 ms | exact original picker compared with conservative block index |

Static GPU packing is enabled only when vertex savings exceed its retained CPU remap.
Packing uses a temporary vertex table during projection creation; these figures
describe retained resources, not peak startup/reprojection memory. It uses portable
vertex/index buffers. No multi-draw, indirect-count, compute or optional
GPU feature requirement was added. Index-order reuse depends on unchanged eye/direction
and geometry, preserving ordering while the camera moves.

Static picking adds one balanced bounds leaf per 32 source triangles, without copying
triangle data. The query fixture builds in 2.09 ms and retains 303,768 bytes. Exact source
triangles, target order, untagged occluders, roof admission and terrain prefix remain the
final authority for hit results. The loaded immutable projection owns the index and
rebuilds it when the static mesh is replaced. Dynamic/entity picking remains exact.

## Live observations

A disposable local test client used the existing OpenRune server/cache. The baseline
courtyard scene contained 23,431 terrain and 620,233 model triangles (1,930,992 vertices).
Ordinary walking reproduced visible frame drops (roughly 26–38 FPS during observed
movements; stationary observations varied up to 50 FPS). The 5-second CPU sample's
strongest hot path was exact scene picking: 570 intersection and 413 surface-picker
samples, versus 51 geometry-sort samples. These are sample counts, not independent
percentages; nested frames overlap.

At 2× DPI, authenticated sidebar opening expanded the physical window from 1602 to
2086 pixels while game extent remained 1530×1006 (765×503 logical). Hidden and rail
states measured 1530 and 1602 physical pixels. No displaced HUD was observed in these
snapshots. This does not establish every intermediate presented frame or all display
modes. Automated geometry sequences cover intermediate/clamped events and fractional DPI.

Runtime diagnostics now include shell staging in frame timing, bounded p95/max histories,
presentation gaps, hover/entity-build/upload stages, discarded catch-up wall time, GPU
geometry resource counts and picking-index bytes. Persistence latency is separate from
slider feedback. A faster picker does not establish that collision or server movement
is correct; no verified noclip reproduction or collision correction is claimed.

The real cache probe (`scene_probe CACHE --performance`) rebuilt the same courtyard
mesh without authentication (643,664 total triangles, 613,849 static). Its 19,183 picking leaves retain 920,760 bytes and build
in 5.74 ms. Actual geometry buffers occupy 25,205,172 GPU bytes after packing;
expanded/resident vertex bytes are 46,343,808 / 17,481,196, with 7,366,188 CPU remap
bytes. The net geometry-resource saving after both CPU additions is 20,575,664 bytes
(about 19.6 MiB); this is not a whole-process RSS measurement. Exact picks matched
for all 600 live-cache rays; p50 was 4.3028 → 0.0118 ms, p95 4.8377 → 0.0450 ms.
No account, server, optional GPU feature or pixel acceptance assertion is needed for
this resource diagnostic. The live-cache source was an APFS temporary clone because
OS opens against Desktop files stalled. The public login key was likewise cloned;
original server/cache files were never changed.

The rebuilt interactive client reached authentication, but the local server timed out.
A read-only JVM thread dump records its `game` thread parked on PostgreSQL's
`ResourceLock` inside `CharacterAccountRepository.setOnlineSession`, via
`touchOnlineSessionHeartbeats` / `GameCycle.postTick`. The `central-scheduler`
thread is waiting for a database response to `WorldRepository.findAllForList`.
This is independent evidence of a server game-loop stall, and can delay movement
updates independently of client rendering. Server/database code or runtime services
were not modified. Live after-change FPS, walking/collision behavior and whole-process
RAM comparisons remain unverified until authentication/game ticks recover.

Remaining live acceptance: fixed, resizable classic/modern, stretched, maximized and
fullscreen gameplay across sidebar transitions; intermediate-frame visual feedback
and pointer alignment while walking. Geometry sequences and panel contracts cover
these mathematical/input obligations, but cannot replace that live acceptance.

## Validation record

Passed with Rust 1.99.0:

- `cargo fmt --all --check`.
- `cargo check --workspace --all-targets`.
- `cargo clippy --workspace --all-targets -- -D warnings`.
- `cargo check -p rustclient-web -p rustclient-shell-ui --target wasm32-unknown-unknown`.
- Explicit final retained sidebar scenario at 1×/2×, exact picker equivalence contracts,
  favorites failure/publication, geometry packing and ordering resource diagnostics.
- Real-cache `scene_probe --performance`: all 600 exact hits match; actual GPU resources
  created successfully.
- Existing offscreen world probe: fresh GPU reconstruction byte-identical; second
  camera/HiDPI, draw-distance, fog, color correction, MSAA/anisotropy captures succeeded.
  Spawn image visually inspected. This is separate from live gameplay acceptance.

The first workspace run caught a race-sensitive cache-counter assertion: four initial
cold readers may race their opens before one handle is retained. Exact/concurrent bytes
were correct. The corrected contract bounds cold opens and retained descriptors; the
full suite is being rerun. Ignored timing/resource probes are diagnostics, not new
source-parity or screenshot acceptance gates (ADR-0004).

Raw ignored local artifacts: `target/sidebar-before-full-trace.log`,
`target/sidebar-after-full-trace.log`, `target/cache-reads-{before,after}.log`,
`target/geometry-ordering-trace.log`, `target/geometry-memory-trace.log`,
`target/static-picking-direct-trace.log`, `target/live-cache-performance.log`, and
`target/sidebar-review/` (including the server thread dump).
