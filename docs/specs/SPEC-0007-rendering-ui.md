# SPEC-0007: Rendering and UI Projections

**Status:** Draft

## Renderer role

The renderer is a projection of committed semantic state and decoded assets. Losing/rebuilding GPU state must not corrupt gameplay state.

## Rendering stack

The target abstraction is `wgpu` with WGSL shaders and shared native/browser renderer code. CP2 establishes `SurfaceRenderer` as the common surface/device/queue owner using `wgpu` 30.x. Native and browser composition roots inject a surface target supplied by `winit`.

`winit` window/event-loop ownership does not move into `renderer`; the renderer receives only what it needs to create a `wgpu` surface. Window-system pre-present notification is injected by callback rather than adding a `winit` dependency to renderer.

## CP2 bootstrap behavior

CP2 proves the boundary with one deterministic clear-frame path.

- zero-sized surfaces suspend rendering rather than configuring an invalid surface,
- resize reconfigures the existing surface,
- timeout/occlusion skips a frame,
- outdated/suboptimal surfaces reconfigure,
- lost surfaces request complete renderer recreation,
- validation failures are explicit errors,
- semantic/application state is not stored in renderer.

The clear frame is a bootstrap proof only, not the OSRS scene renderer.

## RenderWorld

CP7 introduces `RenderWorld`: optimized projection data such as instance buffers, visibility sets, material references, prepared meshes, camera matrices, picking data, and GPU resource handles. It remains rebuildable.

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

For CP2:

- the native composition root compiles against `SurfaceRenderer`,
- the WASM composition root compiles against the same `SurfaceRenderer`,
- the deterministic bootstrap frame uses the same renderer implementation,
- renderer recovery never mutates or invents semantic game state.

For later scene checkpoints:

- identical reference scene semantics on native/web within backend tolerances,
- renderer rebuild from state/assets yields equivalent visible projection,
- plugin overlays cannot mutate core render resources,
- performance counters exist before optimization claims are made.
