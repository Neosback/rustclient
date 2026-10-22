# SPEC-0003: Native and Web Platform Boundary

**Status:** Draft

## Goal

One semantic client implementation, with thin native/browser adapters.

## Platform capabilities

The `platform` crate defines capability-oriented contracts. CP2 implements the first two contracts:

- monotonic and wall clocks,
- persistent binary key/value storage.

Later capabilities include:

- transport creation and byte streams/messages,
- HTTP/fetch where required,
- clipboard,
- audio output,
- notifications where supported,
- asset/plugin package loading,
- optional filesystem dialogs on native.

Window/canvas event-loop ownership stays at the application composition edge. `winit` is the cross-platform window/canvas adapter for CP2, while `renderer` owns the shared `wgpu` surface/device projection. `platform` itself does not expose `winit` or `wgpu` types.

Core/domain crates must not branch on `target_arch = "wasm32"` for semantic behavior. Target cfg belongs primarily in platform/runtime composition layers.

## Clock contract

`Clock` exposes:

- monotonic time from an adapter-owned origin,
- wall-clock Unix milliseconds.

Game ticks are semantic runtime values and remain separate from platform time. Native uses `std::time`; browser uses `Performance.now()` plus `Date.now()` behind `platform-web`.

## Storage contract

`BlobStorage` addresses values by validated semantic `StorageKey` values rather than filesystem paths or browser keys.

- Native CP2 adapter maps keys below one configured directory root.
- Browser CP2 adapter maps small blobs to namespaced `localStorage` entries using base64.
- Large cache/assets are explicitly out of scope for browser `localStorage`; an IndexedDB-style store can implement the same or a later bulk-storage capability.
- Cross-operation transaction/durability guarantees are not implied by CP2.

## Transport sequencing

CP2 deferred transports until concrete consumers existed. SPEC-0014 now defines `ByteStream`: runtime-neutral future-based ordered receive and send-all operations, with stable Closed/Timeout/Cancelled/Unavailable failures. Native TCP is worker-bound, accepts IP literals or localhost, and checks explicit cancellation and deadlines. Sockets and OS entropy remain native capabilities.

Adapter scope:

- Native: TCP commonly, plus TLS/WebSocket/other transports where required.
- Browser: WebSocket/WebTransport or a compatible relay/endpoint because raw TCP is unavailable.

Transport will carry bytes/messages only. Protocol framing, cipher and revision semantics belong to `protocol`, not platform.

## Async rule

Shared code uses runtime-neutral futures/async contracts where asynchronous capabilities are required. Tokio-specific types must not become public core APIs without an ADR.

## Threading rule

Shared semantic code may permit parallelism but must not require native threads to function. Browser workers can be used behind platform/runtime adapters.

## Acceptance criteria

- native and browser composition roots use the same renderer API,
- `platform` has no native/browser implementation dependencies,
- native/web clocks implement the same contract,
- native/web small-blob storage implements the same contract,
- semantic storage keys cannot escape a native root through traversal syntax,
- browser-only APIs are target-gated,
- no protocol/cache/game-state crate imports browser/native OS bindings,
- transport consumers follow the concrete SPEC-0014 byte-stream contract rather than an unrelated placeholder.

## Read-only bulk asset ranges

The offline title milestone adds `ReadOnlyBlobs`: optional blob length and exact
bounded range reads by `StorageKey`. Unlike writable preference storage, opening
an asset source never creates directories/files or repairs missing data.
`DirectoryBlobs` supplies native reads and confines resolved paths to its root;
`MemoryBlobs` supplies already-acquired bytes for portable tests and future web
import. Cache code owns JS5 interpretation, bounds and source context. Native
schedules loading on a worker; a future browser adapter acquires data
asynchronously before invoking the same bounded decoders.
