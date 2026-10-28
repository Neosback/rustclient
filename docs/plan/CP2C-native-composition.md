# CP2C: Native Composition Root

<!-- authority-notice -->
> Historical checkpoint and evidence record. Implementation status, “next” tasks, counts
> and validation below apply to the recorded snapshot. Current work follows
> [PORTING-ROADMAP.md](PORTING-ROADMAP.md) and the [domain matrix](../reference/rs240-port/PORT-MATRIX.md).

## Goal

Wire the native desktop composition root to the already-shared renderer without changing browser composition or adding gameplay/runtime behavior.

## Branch

`feat/cp2c-native-composition`

## Scope

- add native-only `winit` and `pollster` dependencies,
- create the native window through `winit`'s application lifecycle,
- initialize the shared `SurfaceRenderer` from that window,
- forward physical resize events to the renderer,
- call `pre_present_notify` through the renderer's injected callback,
- recreate the disposable renderer when surface loss requires it,
- use `ControlFlow::Wait` and event-driven redraws so the diagnostic shell does not busy-render.

## Explicit deferrals

- browser/WASM composition,
- continuous game-loop/frame scheduling,
- game state or replay integration,
- input semantics beyond window lifecycle,
- scene/UI rendering,
- networking and protocol work.

## Architecture audit

Before merge verify:

- no browser files change,
- `winit` remains in the composition root and does not enter `crates/renderer`,
- renderer recreation does not create or own gameplay state,
- successful diagnostic presentation does not request another frame automatically,
- resize/reconfigure/loss behavior stays explicit.

## Validation

Required exact-head gates:

- `cargo fmt --all --check`
- `cargo check --workspace --all-targets`
- `cargo clippy --workspace --all-targets -- -D warnings`
- `cargo test --workspace`
- `cargo check -p rustclient-web --target wasm32-unknown-unknown`

Interactive native-window smoke testing should be recorded separately when an interactive runner is available; compile/test CI must not be described as a visual runtime test.

## Next checkpoint

After CP2C merges, create a fresh branch for **CP2D - Web composition root**.
