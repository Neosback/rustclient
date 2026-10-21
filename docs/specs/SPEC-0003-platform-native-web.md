# SPEC-0003: Native and Web Platform Boundary

**Status:** Draft

## Goal

One semantic client implementation, with thin native/browser adapters.

## Platform capabilities

The `platform` crate will define capability-oriented contracts for:

- monotonic and wall clocks,
- persistent key/value/blob storage,
- transport creation and byte streams/messages,
- HTTP/fetch where required,
- clipboard,
- window/canvas lifecycle,
- audio output,
- notifications where supported,
- asset/plugin package loading,
- optional filesystem dialogs on native.

Core/domain crates must not branch on `target_arch = "wasm32"` for semantic behavior. Target cfg belongs primarily in platform/runtime composition layers.

## Transport

Private servers may expose different transports.

- Native: TCP is expected to be common; TLS/WebSocket/other transports may also exist.
- Browser: raw TCP is unavailable; servers must offer a browser-compatible endpoint or relay such as WebSocket/WebTransport.

Transport carries bytes/messages. Protocol framing and cipher/revision semantics belong to `protocol`, not platform.

## Async rule

Shared code uses runtime-neutral futures/async contracts. Do not let Tokio-specific types become public core APIs unless an ADR explicitly changes this policy.

## Threading rule

Shared semantic code may be written to permit parallelism but must not require native threads to function. Browser workers can be used behind platform/runtime adapters.

## Storage rule

No domain crate assumes filesystem paths. Persistent resources are addressed by semantic keys/URIs/handles supplied through platform services.

## Acceptance criteria

- `apps/native` and `apps/web` both compose shared crates.
- Shared domain tests run on native; selected compatibility tests compile/run under WASM tooling later.
- No protocol/cache/game-state crate imports browser/native OS bindings.
