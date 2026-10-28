# SPEC-0007: Rendering and UI Projections

**Status:** Draft

## Retained shell (approved 2026-10-25)

Shell panels use Dioxus HTML/CSS components. Native embeds the compatible Blitz
DOM/CPU painting stack inside the existing event loop; the renderer composites a
cached panel texture. Browser panels use Dioxus Web. OSRS game widgets remain in
game-ui. External plugins receive neither toolkit objects nor renderer handles.

Panel generation changes, input, resize, scale and theme changes invalidate retained
output. Continuous world redraws do not themselves invalidate it. Hidden panels
perform no recurring component/layout/paint work. Counters separately report
component updates, layout, rasterization and uploaded bytes. Recovery recreates
the GPU texture from retained CPU pixels without changing semantic state.

Focus and hit-testing share panel bounds with composition. Text/IME/clipboard,
wheel, pointer capture, sliders and keyboard navigation must pass native and web
acceptance before claiming the shell usable. Unsupported controls cannot silently
store ineffective settings or fall back to an immediate-mode UI.

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

The sidebar is application chrome adjacent to the game canvas. By the user's selected behavior,
closing its panel also removes the tool rail: closed chrome reserves zero horizontal width, takes
no game input and does no recurring component/layout/painting/upload work. One visibility owner
projects both rail and panel state. The reopen affordance may use the existing RuneLite-style
fallback arrow when native titlebar integration is unavailable; it is not a replacement game HUD.

Tool navigation belongs to real tool/panel services. A plugin's configuration entry does not by
itself create a toolbar navigation button. Required services remain active while their UI is hidden.

The game UI's raster viewport/scissor must equal the physical extent used to normalize its vertices.
A wider native surface containing shell chrome must not stretch game quads into that chrome.
Picking, widget input, minimap input, overlay placement and stretched-mode rendering share the same
canvas transform. Shell-driven asynchronous resizes must not be fed back as user changes to game size.

Acceptance: closed/open DOM and idle-work contracts at 1x/2x, preserved game width, repeated open/close,
DPI/resize/stretched tests and desktop fixed/classic/modern minimap/pointer checks. Compilation or a
standalone panel PNG does not establish the desktop checks.

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
