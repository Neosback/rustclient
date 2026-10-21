# SPEC-0007: Rendering and UI Projections

**Status:** Draft

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
