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
