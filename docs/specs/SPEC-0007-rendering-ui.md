# SPEC-0007: Rendering and UI Projections

**Status:** Draft

## Shared sidebar continuation (approved 2026-10-29)

ADR-0007's egui panel replaces both native retained DOM and browser DOM chrome.
The pinned RuneLite measurement defines logical panel/rail dimensions, fonts,
assets and control spacing. One shared metrics owner supplies reserved widths,
painting, clipping and input transforms; it supersedes the historical 36/278
pixel reservations below. DPI conversion rounds the complete chrome extent once.
Preserve game extent, zero-width hidden state, rail/panel navigation and accepted
configuration persistence. No TSPS Retina-only rail zoom is adopted. Required GPU
stays searchable with an Always on badge and configuration control. Pixel/native
and browser acceptance remain separate from compilation; record font rasterization
differences explicitly under ADR-0004.

## Retained shell (approved 2026-10-25; native backend revision 2026-10-28)

The original shell uses Dioxus HTML/CSS components and Blitz CPU painting.
The user-authorized native replacement in ADR-0007 uses demand-driven egui passes
and cached wgpu panel targets, preserving the shared typed configuration model.
Browser DOM panels remain during WebGPU integration; browser runtime acceptance is separate. OSRS game widgets remain in
game-ui. External plugins receive neither toolkit objects nor renderer handles.

Panel generation changes, input, resize, scale and theme changes invalidate retained
output. Continuous world redraws do not themselves invalidate it. Hidden panels
perform no recurring component/layout/paint work. Counters separately report
component updates, layout, rasterization and uploaded bytes. Recovery recreates
the GPU texture from retained CPU pixels without changing semantic state.

Focus and hit-testing share panel bounds with composition. Text/IME/clipboard,
wheel, pointer capture, sliders and keyboard navigation must pass native and web
acceptance before claiming the shell usable. Unsupported controls cannot silently
store ineffective settings or silently switch backends. Immediate-mode evaluation
is permitted by ADR-0007 only when driven by invalidation/deadlines, independently
of world frames; static and hidden panels must retain the no-work contract.

## Renderer role

The renderer is a projection of committed semantic state and decoded assets. Losing/rebuilding GPU state must not corrupt gameplay state.

## Planned rendering stack

The target abstraction is `wgpu`, with WGSL shaders and shared native/browser renderer code. Native and browser may select different backend capabilities while preserving scene semantics.

## Bootstrap ownership contract

The shared renderer owns GPU projection resources required to present a surface, including the `wgpu` instance, surface, logical device, queue, and surface configuration. It does not own native windows, browser canvases, event loops, semantic game state, protocol state, or platform services.

Composition roots provide a surface target and remain responsible for their window-system lifecycle. A pre-present callback may be supplied by the composition root when its windowing backend requires notification before presentation; this must not create a renderer dependency on that windowing library.

A zero-sized surface is suspended rather than configured with invalid dimensions. Recoverable timeout, occlusion, outdated, and suboptimal states are reported explicitly. Surface loss requires the composition root to discard and recreate the renderer from still-authoritative state and assets.

The CP2 diagnostic clear frame is only a boundary proof. It is not a second scene representation and must not accumulate gameplay or scene semantics.

## RenderWorld

`RenderWorld` is optimized projection data such as instance buffers, visibility sets, material references, prepared meshes, camera matrices, picking data, and GPU resource handles. It can be rebuilt.

## Performance principles

The accepted incremental-update plan (2026-10-30) requires precise committed
invalidation through scene, widgets, picking, minimap and GPU publication. Small
chat/inventory changes do not invalidate unrelated world geometry. Location work
targets dependency-correct 8x8 zones, preserving cross-zone footprints, normal
reconciliation and source ordering. Full rasterization is independent of whether
CPU preparation or uploads changed. View distance, resolution and effects are
not reduced to satisfy performance or memory targets.

Target native acceptance on the 8 GiB M2 uses 256 visible actors (64 players,
192 NPCs), with 1024 for overload characterization. Gates are active CPU p99
<=10 ms, GPU p99 <=12 ms, presentation-gap p99 <=20 ms and gaps >25 ms <=0.1%;
warm small-change visibility p99 <=50 ms. Compare three five-minute warm runs,
cold updates and a 30-minute lifecycle soak at identical settings. Target at
least 20% less peak update-transient CPU memory without steady-state RSS
regression. These are acceptance requirements, not current measurements.

Native background work uses a persistent bounded pool (logical CPUs minus two,
clamped to 1..4), typed lifecycle identities, cancellation and visible-work
priority. Background upload admission initially allows 2 MiB or 1 ms CPU enqueue
work per frame; urgent updates remain inside the total frame budget. No source
cycle, committed input or required effect is discarded under overload. Optional
GPU acceleration must pass correctness and CPU/GPU/memory measurement before
selection. Existing cache budgets remain unchanged without measured justification.

Retained game widgets must name their actual presentation dependencies: interface
and definition revisions, dragging, loaded visual assets, and the facts read by
legacy expressions. Retention cannot use a frame-end state fingerprint or treat
all actor movement as a widget change. Asset arrival invalidates presentation
without creating semantic events. Future animated widget content must contribute
its animation deadline before it can participate in retention.

The shared UI command stream separates transient world overlays from the retained
widget prefix. Drawing preserves the world-widget insertion boundary, mounted
interface ordering and later pointer/menu overlays. A retained prefix has an
explicit content identity; callers must invalidate it before mutating that prefix.
The renderer may retain its clipped vertices and batches while this identity,
viewport transform and texture layout remain unchanged. Transient commands use a
separate suffix of the same bounded vertex buffer. Atlas replacement, resizing,
DPI/stretch changes and device reconstruction invalidate prepared geometry.
The existing encoded UI canvas may skip its raster pass only when its texture is
valid, the prepared command content is unchanged, there are no current or previous
transient overlays and no dynamic pixels were uploaded. Removing the last overlay
requires repainting to erase its pixels. Resizing or recreating the canvas requires
a first raster even when command inputs match. The world and final display composite
continue independently. This does not claim partial world rasterization.
Diagnostics distinguish prepared/retained quads, vertex upload bytes, dynamic texture
bytes and actual UI raster passes.

### Stable presentation and world-map preparation (accepted 2026-10-31)

Capped native presentation follows absolute monotonic deadlines. Small wake-up
delays do not accumulate into a lower effective cap; missed periods are skipped
without catch-up bursts. Effective-cap changes and suspension reset the clock.
Effective FIFO pacing is not combined with an unintended software cap. Telemetry
distinguishes wake lateness, active preparation, surface acquisition, submission/
present-call time and observed display cadence. Optional GPU timestamp collection
uses bounded asynchronous readback; unavailable timings remain unknown. Raw trace
overflow invalidates a scored run rather than blocking presentation.

Title geometry depends on visible presentation generations and blink phases, not
every render or cosmetic cycle. Flame pixel revisions advance at the existing
source-defined simulation steps; identical pixels do not upload again. No input,
animation, resolution, effect or draw-distance reduction is a performance fix.

World-map viewport/overview composition uses explicit asset/view/presentation
identities. Unchanged content is neither recomposed nor uploaded. Visible flash
phase changes and asset arrival invalidate pixels without gameplay events. Native
composition uses the existing bounded pool with one outstanding and one coalesced
latest request; obsolete session/resource/request results cannot publish. Valid
pixels survive loading/failure, and rejected uploads remain pending for retry.
GameState remains authoritative; map pixels and job inputs are projections.

- batch uploads/draw work,
- separate CPU asset decode from GPU upload,
- spatially cull before expensive preparation,
- avoid per-frame heap churn in hot paths,
- measure before introducing complex caches,
- use generation/dirty tracking for incremental updates,
- preserve correctness fixtures for classic scene semantics before visual enhancements.

## Game UI vs shell UI

`game-ui` implements game interfaces/widgets/menu semantics and client-script integration. `shell-ui` owns application chrome, settings, plugin manager, diagnostics and developer tooling.

External plugins do not receive direct renderer or shell toolkit objects. They submit stable overlay/UI command descriptions.

## Picking

Picking/hover/click geometry must derive from the same semantic/render transforms used to display objects. Avoid a separate approximate coordinate system that drifts from visuals.

## Acceptance criteria

- identical reference scene semantics on native/web within backend tolerances,
- renderer rebuild from state/assets yields equivalent visible projection,
- surface loss never mutates or invalidates authoritative gameplay state,
- native/browser composition can target the same shared renderer API without platform branching inside `renderer`,
- plugin overlays cannot mutate core render resources,
- performance counters exist before optimization claims are made.

## Offline title projection

`game-ui::UiFrame` is a disposable painter-ordered quad stream using typed CPU
image roles, logical rectangles, UVs, tint and clip rectangles. It contains no
GPU handles or raw passwords. The renderer consumes it using one static title
atlas and a separate dynamic flame texture. Texture sampling is nearest-neighbor;
straight-alpha assets use an sRGB atlas and normal GPU alpha blending. Flames may
therefore differ slightly from the Java software rasterizer's sRGB integer blend.
Shared `TitleViewport` governs both input conversion and draw positioning.

The renderer uploads static assets once per resource lifetime, reuses a bounded
vertex buffer, preserves painter order while batching consecutive texture uses,
and exposes quad/draw/upload counters. The diagnostic `capture_ui` path uses the
same GPU pipeline; native drives device polling through an explicit callback,
while browser readback can use its event loop. It does not acquire platform files.

Device-loss callbacks set a renderer-owned atomic signal; the next frame returns
`RecreateRequired`, using the same composition-root recovery path as surface loss.
This signal contains no semantic state and does not mutate `GameState`.


## Sidebar visibility and viewport correction (approved 2026-10-26)

The sidebar is application chrome adjacent to the game canvas. **Superseded 2026-10-27:**
the earlier requirement that closing a panel also removes the rail is replaced by three states:
hidden (zero width), rail only (36 logical pixels), and panel open (242 + 36 logical pixels).
One shell presentation owner controls these states. The arrow/sidebar hotkey hides or restores
all chrome; the active tool/panel hotkey closes or restores the panel while retaining the rail.
The initial state is hidden. Closing chrome never stops its required services.

Shell-ui owns retained session navigation (selected plugin, query, sections, scroll and draft controls).
Application-owned favorites persist through platform blob storage independently of plugin configuration.
Only real implemented tools receive navigation buttons. The existing RuneLite fallback arrow remains
available without native titlebar integration. External plugins receive no additional capabilities.

An asynchronous shell-requested resize preserves the established game extent through intermediate
platform events, immediate results, rapid toggles and scale changes. Resize completion is bounded;
OS-clamped requests settle once to available space. Maximized/fullscreen windows and the explicit
keep-window-size preference reserve chrome inside the existing window. Game raster/scissor, logical
widget extent, stretching, picking and shell composition derive from the same canvas split. Game quads
must never stretch temporarily across space acquired for shell chrome.

Input delivery remains ordered, with component processing separate from presentation-time painting.
Unchanged hover targets trigger no layout/raster/upload or configuration publication. Dirty components,
layout and paint are tracked separately; scrolling without component/style changes reuses layout.
Hidden chrome performs no recurring component/layout/paint/upload work. At most one panel rasterization
and upload occurs per presented frame; game redraws reuse cached pixels.

Sliders keep a presentation-only draft while dragging, commit once on release, and cancel on Escape
or capture loss. Numeric fields commit valid drafts on Enter/blur. Persistence remains prepare →
atomic write → publication; errors retain accepted configuration and restore the accepted slider value.
Native focus/blur and tooltips must work, rather than relying on unsupported DOM event behavior.

Acceptance: three-state transitions and restoration, favorites persistence/failure, draft/cancel contracts,
unchanged-hover and hidden zero-work counters, resize sequences at integer/fractional scale, and optimized
before/after interaction traces. Target p95 visual response within 50ms at 60 FPS. Live desktop acceptance
covers fixed/classic/modern/stretched modes and minimap/pointer alignment. Compilation, standalone panel
images and CPU traces are separate evidence and do not establish logged-in desktop acceptance.

## GPU baseline audit and HD preparation (2026-10-26)

The classic GPU path must inventory every source setting, active shader resource and source test
against Rust owners. Supplied historical compute/OpenCL files are separate evidence where the
vendored active plugin no longer loads them; loading obsolete shaders is not an acceptance gate.
Each shader's applicable behavior must be implemented or explicitly incomplete. HD effects remain
outside the classic plugin; both will use the engine's renderer services without GPU handles exposed
to external plugins or a second authoritative scene.

Device creation requests only used features/limits. Reports distinguish adapter support, requested
device capability and effective settings. Browser present modes/MSAA/optional shader features are
negotiated; unsupported settings explain effective behavior. Resource creation validates dimensions,
array layers and buffer capacities before submitting descriptors. Offscreen verification uses actual
format sample support, not assumed native sample counts. Texture filtering level zero disables mip
selection; level one enables mip selection without anisotropy.

GPU UI scaling operates on the game canvas, preserving logical hit-testing and leaving shell chrome
independent. Source nearest, linear, Mitchell, Catmull-Rom, xBR and hybrid sampling apply to that
canvas; color correction applies to game UI as well as the world. Canvas-sized intermediate textures
are reused until layout changes. Painter order, premultiplied composition, viewport clipping and
HiDPI/stretched transforms must be verified numerically and on the GPU. Browser build success alone
is not browser gameplay/visual acceptance. Requested settings without an effect remain incomplete.

Native presentation uses one clock for the GPU's effective cap and the optional FPS plugin, selecting
whichever active limit is stricter. Unlocked presentation deadlines are independent of 20 ms semantic
cycles; overdue rendering produces one current projection, never catch-up semantic updates. Occluded
windows do not run the presentation loop. Browser presentation remains driven by its platform event
loop. Rendering more often does not imply subcycle actor/vertex interpolation has been ported.

## Zone admission and related maps (2026-10-27)

The injected revision-240 GPU scene path selects an inclusive square of eight-tile zones around the camera
zone, with radius `drawDistance >> 3` (`Scene.method4474`). It differs from the older tile-loop path.
Each terrain primitive and whole scenery/actor model retains one typed scene-local draw-tile anchor;
visibility never clips a model by independently classifying its vertices. Picking consumes the same
anchor and zone-window predicate as the renderer. Vertex-stage rejection saves rasterization work;
CPU upload/sort and zone/frustum performance parity remain separate obligations.

RuneLite RegionManager/Regions chunk-group semantics are projection policy. A repository-owned copy
of its BSD resource is bundled; runtime never reads references/. World-map asset assembly classifies
scenery at its owning tile against the group at `(baseX / 8 + 6, baseY / 8 + 6)`. The accepted setting
controls a frame visibility flag; changing it does not alter GameState, collision, terrain heights,
lighting inputs or network paths. Actor temporary models remain independent of scenery deletion.
Instances bypass this policy; the current normal-map asset loader is not an instance loader and must
not infer a normal-map classification for future instance assets. Parser/lookup cases and bypass
contracts are distinct from future instance-travel integration and exact source visual acceptance.

## Full-viewport widget alpha overlay (2026-10-27)

The game UI projection owns the injected-240 full-viewport overlay optimization. Eligible visible,
filled solid rectangles with nonzero transparency and bounds equal to the world viewport accumulate
into one disposable premultiplied color, preserving source byte arithmetic and encounter order
(`classGP.method5624`). They are omitted from the ordinary quad stream. Other rectangles retain
normal painter order. Reset this color on every widget projection; it is not authoritative state.

The canvas compositor blends sampled UI over that color before UI color correction, then composites
over the existing world target (`GpuPlugin.drawUi` / `fragui.glsl`). It uses the same canvas scaling
and clipping boundary, leaves shell chrome independent, and requires no new scene or GPU pass.
Only retained solid rectangle semantics are eligible; future gradient modes must stay outside this
optimization. Verify byte accumulation and selection/reset with semantic contracts, then run bounded
native/browser shader diagnostics separately from Java visual acceptance.

## Portable geometry resource budgets (2026-10-28)

Renderer-owned static GPU vertices may be compacted by exact equality of every packed vertex byte,
including face priority, draw-tile admission, roof tags, material, color and UVs. The CPU mesh, model
spans, emitted face order and picking geometry remain unchanged. Both opaque and transparent GPU
index streams use the same explicit source-to-packed remap, including later priority sorting.
Compaction is retained only when saved GPU bytes exceed the resident CPU remap allocation.
Dynamic actor/scenery buffers keep their streaming path; no per-cycle hash compaction is introduced.

Each geometry projection may reuse face ordering only while its geometry and camera eye/direction
are unchanged. Geometry updates invalidate that cache independently per static/dynamic/entity stream.
Texture-animation cycles and unchanged visibility do not require re-sorting static faces. This path
requires no optional GPU features and shares behavior across native and WebGPU. Memory and ordering
counters distinguish expanded source vertex bytes, resident GPU bytes and retained CPU remap bytes.
These counters are resource accounting, not a claim about whole-process RSS or visual parity.

Native timing diagnostics include bounded p95/max frame, cycle and presentation-gap samples,
hover/actor-build/upload stages and discarded wall-clock catch-up debt. Semantic movement, collision
and server-confirmed positions are not changed to mask slow frames.

### Static picking broad phase (2026-10-28)

The immutable loaded scene owns a disposable `ScenePickIndex` alongside its CPU mesh.
It indexes bounds for blocks of 32 source triangles, without copying triangle data, and
is rebuilt when the static projection is replaced. Balanced median subdivision bounds
query stack depth and retained memory. Dynamic geometry continues to use its current
exact picker. Both targets can use the same index; no optional GPU features are needed.

Bounds provide conservative rejection only. Accepted candidates use the existing
triangle intersection, primitive visibility, terrain-prefix and semantic target rules.
Equal hit distances resolve by original source triangle order, including untagged
occluders. A mismatched source allocation falls back to the original query; callers
must not mutate indexed geometry in place. This index is never authoritative game
state and must not alter collision, movement, path queues or packet handling.

### Shell composition initialization

The retained shell's portable composition pipeline is compiled during renderer/device
initialization, including device reconstruction. First opening must reuse that pipeline;
interactive shell staging must not synchronously create render pipelines. Hidden chrome
retains only its placeholder/last image and performs no recurring raster/upload work.

World-map primary presses follow resolved widget paint order and clipping. Buttons
above the map receive their press before map dragging begins. The map image rectangle
alone is not an input target and must not intercept a close button over that rectangle.

### Actor CPU pose reuse (authorized 2026-10-29)

The existing entity catalog may retain a bounded, disposable last model-local pose
per visible actor. Keys compare the complete appearance/resolved morph and exact
legacy/cached animation samples, including smoothing and fallback movement. Actor
position, terrain, lighting, draw policy and pick emission are evaluated anew.
Resource merges, scene-asset changes and actor removal invalidate retained poses.
A 32 MiB retained-model budget bounds this cache; oversized poses still render.
No GPU skinning, float approximation, depth offset or GameState mutation is involved.
Counters report lookups, hits, builds and retained model bytes. Before/after probes
compare identical emitted bytes and timings; synthetic/cache probes do not establish
native/browser visual parity or a whole-client frame-rate improvement.
