# CP2B: Shared Renderer Core

<!-- authority-notice -->
> Historical checkpoint and evidence record. Implementation status, “next” tasks, counts
> and validation below apply to the recorded snapshot. Current work follows
> [PORTING-ROADMAP.md](PORTING-ROADMAP.md) and the [domain matrix](../reference/rs240-port/PORT-MATRIX.md).

## Goal

Add the platform-neutral `wgpu` renderer core without wiring any native window or browser canvas yet.

## Branch

`feat/cp2b-shared-renderer-core`

## Scope

- add `wgpu` as the renderer dependency,
- own `Instance`, `Surface`, `Device`, `Queue`, and surface configuration inside `crates/renderer`,
- expose typed physical surface size and diagnostic clear color,
- suspend cleanly for zero-sized surfaces,
- report timeout/occlusion, reconfigure, surface-loss, and validation outcomes explicitly,
- keep pre-present notification injected by the caller so `renderer` has no `winit` dependency,
- include focused dependency-free unit tests for surface-size/bootstrap semantics.

## Explicit deferrals

- native `winit` window/event-loop integration,
- browser canvas/event-loop integration,
- game scene rendering,
- `RenderWorld`, meshes, textures, picking, UI, or shaders beyond the clear pass,
- networking and protocol work.

## Architecture audit

Before merge verify:

- only `crates/renderer` depends on `wgpu` for this checkpoint,
- `renderer` has no dependency on `winit`, platform adapters, protocol, or game state,
- the diagnostic clear path is not treated as authoritative state,
- surface loss is represented as a rebuild request rather than hidden mutation,
- no app composition root changes are present.

## Validation

Required exact-head gates:

- `cargo fmt --all --check`
- `cargo check --workspace --all-targets`
- `cargo clippy --workspace --all-targets -- -D warnings`
- `cargo test --workspace`
- `cargo check -p rustclient-web --target wasm32-unknown-unknown`

## Next checkpoint

After CP2B merges, create one fresh branch for **CP2C - Native composition root**. Do not begin CP2D until CP2C is merged.
