# CP2: Platform Contracts and Shared Renderer Bootstrap

## Goal

Prove that native desktop and browser/WASM composition roots can use the same platform-neutral capability shapes and the same `wgpu` renderer implementation without networking or gameplay state.

## Branch

`feat/cp2-platform-renderer-bootstrap`

## Scope

### Platform core

- `Clock` with monotonic and wall time values.
- `BlobStorage` with validated semantic storage keys.
- stable storage error categories.

### Native adapter

- `NativeClock` backed by `std::time`.
- `DirectoryStorage` rooted under one caller-supplied directory.
- traversal-resistant semantic key mapping.

### Web adapter

- `BrowserClock` backed by Performance/Date browser APIs.
- small-blob `LocalStorage` adapter with RustClient namespacing/base64.
- browser implementations compiled only for `wasm32`.

### Shared renderer

- `wgpu` surface/device/queue initialization in `renderer`.
- one deterministic clear-frame implementation.
- resize/zero-size handling.
- explicit surface timeout/occlusion/reconfigure/loss outcomes.
- caller-injected pre-present hook so renderer does not depend on `winit`.

### Composition roots

- native `winit` event loop/window -> `SurfaceRenderer`.
- web `winit` canvas/event loop -> the same `SurfaceRenderer`.
- async browser renderer initialization remains in the browser composition root.

## Explicit deferrals

- TCP, WebSocket and WebTransport.
- login/authentication.
- revision-240 packet framing/ciphers.
- OSRS scene/asset rendering.
- IndexedDB bulk cache storage.
- audio/clipboard/notifications/filesystem dialogs.

Transport is deliberately deferred rather than represented by a placeholder interface. Networking work will start only after the protocol/replay requirements that consume it are concrete.

## Architecture audit

Before merge verify:

- `platform` has no `winit`, `wgpu`, browser or filesystem dependency,
- `renderer` has no dependency on native/web adapters or semantic game state,
- browser cfg appears only in web adapter/composition code,
- both apps compile against the same renderer type,
- no platform adapter owns protocol framing or revision behavior,
- storage keys cannot express native path traversal.

## Validation

Required exact-head gates:

- `cargo fmt --all --check`
- `cargo check --workspace --all-targets`
- `cargo clippy --workspace --all-targets -- -D warnings`
- `cargo test --workspace`
- `cargo check -p rustclient-web --target wasm32-unknown-unknown`

Runtime smoke tests for an actual desktop window and browser canvas should be recorded separately when an interactive runner/browser is available; CI compile validation must not be mislabeled as visual runtime validation.
