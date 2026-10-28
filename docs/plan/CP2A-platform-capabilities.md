# CP2A: Platform Capabilities

<!-- authority-notice -->
> Historical checkpoint and evidence record. Implementation status, “next” tasks, counts
> and validation below apply to the recorded snapshot. Current work follows
> [PORTING-ROADMAP.md](PORTING-ROADMAP.md) and the [domain matrix](../reference/rs240-port/PORT-MATRIX.md).

## Goal

Establish the first platform-neutral capabilities used by both native and browser targets without introducing renderer, windowing, networking, or gameplay dependencies.

## Branch

`feat/cp2-platform-renderer-bootstrap`

This branch predates the smaller-checkpoint workflow. Its final diff is intentionally reduced to CP2A only.

## Scope

### Shared platform contracts

- `Clock` with explicit monotonic and wall-clock values.
- `BlobStorage` with validated semantic storage keys.
- stable cross-platform storage error categories.
- no filesystem paths, browser objects, renderer handles, or protocol concepts in the shared contract.

### Native adapters

- `NativeClock` backed by `std::time`.
- `DirectoryStorage` rooted under one caller-provided directory.
- traversal-resistant mapping from validated semantic keys to native paths.

### Browser adapters

- `BrowserClock` backed by browser timing APIs.
- small-blob `LocalStorage` adapter with RustClient namespacing and base64 encoding.
- browser implementation code compiled only for `wasm32`.

## Explicit deferrals

The following are deliberately not part of CP2A:

- `wgpu` renderer bootstrap,
- native `winit` composition root,
- browser canvas/window composition root,
- TCP, WebSocket, or WebTransport,
- login/authentication,
- revision-specific protocol framing,
- bulk IndexedDB cache storage,
- audio, clipboard, notifications, or file dialogs.

Transport remains deferred until protocol/replay requirements make the consuming contract concrete.

## Follow-up checkpoints

Each follow-up should use one focused branch and merge before the next branch starts:

1. **CP2B - Shared renderer core:** `wgpu` surface/device/queue ownership and deterministic diagnostic frame path only.
2. **CP2C - Native composition root:** native `winit` window/event-loop integration against the shared renderer.
3. **CP2D - Web composition root:** browser/WASM canvas/event-loop integration against the same renderer.

Do not keep multiple CP2 implementation branches open unless a hard dependency requires it.

## Architecture audit

Before merge verify:

- `crates/platform` has no native, browser, renderer, protocol, or gameplay dependency,
- native filesystem details remain in `platform-native`,
- browser API details remain in `platform-web`,
- storage keys cannot express native path traversal,
- semantic game ticks remain separate from platform clock values,
- no transport placeholder is introduced.

## Validation

Required exact-head gates:

- `cargo fmt --all --check`
- `cargo check --workspace --all-targets`
- `cargo clippy --workspace --all-targets -- -D warnings`
- `cargo test --workspace`
- `cargo check -p rustclient-web --target wasm32-unknown-unknown`

CP2A closes only when the exact branch head passes all gates.
