# CP2D: Web Composition Root

<!-- authority-notice -->
> Historical checkpoint and evidence record. Implementation status, “next” tasks, counts
> and validation below apply to the recorded snapshot. Current work follows
> [PORTING-ROADMAP.md](PORTING-ROADMAP.md) and the [domain matrix](../reference/rs240-port/PORT-MATRIX.md).

## Goal

Wire the browser/WASM composition root to the same shared renderer used by native without changing native behavior or adding gameplay/runtime systems.

## Branch

`feat/cp2d-web-composition`

## Scope

- add browser async bootstrap support with `wasm-bindgen-futures`,
- create the browser canvas/window through `winit`'s web application lifecycle,
- initialize the shared `SurfaceRenderer` asynchronously,
- forward physical resize events to the renderer,
- call `pre_present_notify` through the renderer's injected callback,
- recreate disposable GPU state after surface loss,
- report browser bootstrap/render errors through the browser console,
- use `ControlFlow::Wait` and event-driven redraws so the bootstrap does not become a busy animation loop.

## Explicit deferrals

- gameplay/runtime frame scheduling,
- game state or replay integration,
- input semantics beyond window lifecycle,
- scene/UI rendering,
- browser transport/networking,
- packaging/bundling and deployment tooling.

## Architecture audit

Before merge verify:

- no native app files change,
- browser-specific APIs remain under the WASM composition root,
- `crates/renderer` remains platform-neutral and has no `winit` or browser dependency,
- renderer initialization is asynchronous only at the browser composition edge,
- successful diagnostic presentation does not request another frame automatically,
- surface loss recreates GPU projection without owning semantic state.

## Validation

Required exact-head gates:

- `cargo fmt --all --check`
- `cargo check --workspace --all-targets`
- `cargo clippy --workspace --all-targets -- -D warnings`
- `cargo test --workspace`
- `cargo check -p rustclient-web --target wasm32-unknown-unknown`

Interactive browser rendering should be smoke-tested separately in a browser-capable environment; compile/test CI must not be described as visual validation.

## CP2 exit

After CP2D merges, CP2 is complete: shared platform primitives plus one shared renderer API are compiled through both native and browser composition roots, while transport remains deliberately deferred.
