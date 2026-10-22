# CP11 — Native plugin vertical slice

## Goal

Prove the plugin architecture end-to-end before broadening the feature surface: one canonical
WIT contract, one package format, one mediated host, one real WebAssembly component loaded by
the desktop client, and one overlay drawn from plugin commands.

This closes the executable part of CP11.1–CP11.7 and proves CP11.10 at the component level: the
same packaged component runs under Wasmtime and under `jco`'s JS bindings against the same
fixture. Browser worker integration (CP11.8) and SDK ergonomics (CP11.9) remain open and are
listed under [Deliberately skipped](#deliberately-skipped).

## ABI v0.1.0

Canonical contract: [`wit/rustclient/plugin/world.wit`](../../wit/rustclient/plugin/world.wit),
package `rustclient:plugin@0.1.0`.

- `host` imports: `log`, `player-position` (query), `tick`, `config-get`, `config-set`.
- `guest` exports: `init`, `on-event(list<event>)`, `overlay() -> list<overlay-line>`.
- `event` variants: `started`, `tick(u64)`, `stopped`.
- `overlay-line { text, x, y, argb }` in the `765x503` logical interface space.
- Capabilities are deny-by-default. Host imports check the operator grant on every call and
  record denials; a missing grant returns `none`/no-op rather than leaking state.
- The sample guest is `no_std` + `alloc` and imports **only** `rustclient:plugin/host@0.1.0`.
  No WASI, filesystem, sockets, environment, renderer or platform imports exist in the ABI.

## Package format

`plugin.json` beside the component:

```json
{
  "id": "dev.rustclient.hello-overlay",
  "name": "Hello Overlay",
  "version": "0.1.0",
  "plugin-api": "0.1",
  "capabilities": ["game.read.players", "ui.overlay", "storage.plugin"],
  "entry": "hello_overlay.wasm"
}
```

`plugin-api` is `major.minor`; a host accepts the same major with an equal or higher minor.
`entry` must be a plain file name, so a manifest cannot escape its package directory.
Unknown fields, capabilities and malformed IDs are rejected.

## Host mediation

`crates/plugin-api` owns the versioned public vocabulary: `PluginId`, `PluginApiVersion`,
`Capability`/`CapabilitySet`, `PlayerPosition`, `OverlayLine`, `PluginEvent`, `LogLevel` and the
manifest types.

`crates/plugin-host` owns lifecycle and mediation:

- `HostView` is the only approved game-state projection (position + tick today).
- `PluginStorage` is plugin-namespaced key/value storage, loaded as a snapshot before a call and
  saved afterwards; `MemoryStorage` serves tests and `DirectoryStorage` persists JSON per plugin.
- `PluginSet` initializes, dispatches event batches, collects overlays and isolates faults.
- Three consecutive faults disable a plugin; faults are logged and never abort the client.
- Runtime adapters implement `PluginInstance`; they never receive `GameState`.

## Native runtime

`crates/plugin-runtime-native` executes components with Wasmtime 49 (pinned: Wasmtime 50 requires
rustc 1.97, this workspace builds on 1.96). Each plugin gets:

- its own `Store<HostState>` and component instance,
- a 20,000,000-unit fuel refill per entry point; fuel exhaustion maps to a budget fault,
- owned per-call state: config snapshot, position, tick, buffered logs and denial records,
- flushes to the host call context only after the entry point returns.

There is no shared mutable state between plugins and the client.

## Sample plugin

`plugins/hello-overlay` (detached workspace, builds to `wasm32-wasip2`):

- logs a ready line and restores `last_tick` from plugin-local config on `init`,
- counts `tick` events and every 50 commits queries `player-position`,
- persists `last_tick` through `config-set`,
- emits one yellow overlay line with the tick and committed position.

The guest runtime section (dlmalloc global allocator, panic handler, canonical-ABI
`cabi_realloc`) is intentionally visible in the sample and is the template content that will move
into `rustclient-plugin-sdk` during CP11.9.

## Native client wiring

- `--plugins-dir PATH` (default `<preferences-dir>/plugins`) is scanned one package per child
  directory, sorted, capped at 32.
- Loading happens once at startup; a bad package logs and is skipped, never failing the client.
- Sessions dispatch `Started` on authentication, batched `Tick` events when the client step
  advances, and `Stopped` on disconnect.
- Overlay lines project through the shared bitmap-font path (`game_ui::draw_text`) after the
  gameframe/widget/context-menu projections, topmost, clipped to `765x503`.

Reproduce:

```text
rustup target add wasm32-wasip2
scripts/build-sample-plugin.sh
cargo run -p rustclient-native --bin plugin_probe -- \
  --package plugins/hello-overlay --storage /tmp/rustclient-plugin-storage --ticks 120
# run the probe a second time to observe `last persisted tick 100`
scripts/run-openrune.sh /path/to/OpenRune-Server --plugins-dir plugins
```

## Web host proof

The packaged component is not tied to Wasmtime. `scripts/check-plugin-web.sh` builds the same
`hello_overlay.wasm`, transpiles it with `jco` (Node 26, jco 1.39), maps the host interface to a
reference JS host, and drives `init` → `started` → 120 ticks → `stopped` → `overlay` twice:

```text
packaged plugin package: .../plugins/hello-overlay (plugin.json + hello_overlay.wasm)
web plugin fresh run passed: hello-overlay · tick 100 · 3224,3218 plane 0
web plugin restored run passed: hello-overlay · tick 100 · 3224,3218 plane 0
```

Both runs assert the exact overlay text, position, style, `last_tick = 100` and the three expected
log lines. The second run restores `last persisted tick 100` from the JSON config, matching the
native probe. The generated module imports only `rustclient:plugin/host`; no WASI or preview2
shims are involved. The reference host keeps config in Node's filesystem; the browser worker will
replace that with IndexedDB and a message boundary (CP11.8).

## Validation evidence

- `cargo fmt --all --check` — pass.
- `cargo clippy --workspace --all-targets -- -D warnings` — pass. This also cleared
  pre-existing pedantic drift exposed by the rustc 1.96 clippy (unrelated to plugin logic).
- `cargo check --workspace --all-targets` — pass.
- `cargo check -p rustclient-web --target wasm32-unknown-unknown` — pass.
- `cargo test --workspace` — 67 test binaries pass. Two stale fingerprint anchors from
  commit `953f7ead` were refreshed in a separate commit; all structural fingerprint
  invariants (equivalence, revision identity, replay, commit order) were already passing.
- `cargo check -p rustclient-plugin-api -p rustclient-plugin-host --all-targets` — pass.
- `cargo check -p rustclient-plugin-runtime-native` — pass (Wasmtime 49.0.4 resolved).
- `cargo check -p rustclient-native --all-targets` — pass.
- `cargo test -p rustclient-plugin-api -p rustclient-plugin-host` — manifest, version,
  capability, mediation, overlay-grant and fault-isolation tests pass.
- Probe run 1 (fresh storage): overlay `hello-overlay · tick 100 · 3224,3218 plane 0`; config
  written `{"last_tick": "100"}`.
- Probe run 2 (same storage): init log `hello-overlay ready (last persisted tick 100)`,
  proving plugin-local persistence across instantiations.
- Guest artifact: 19,754 bytes, imports only `rustclient:plugin/host@0.1.0`.
- `scripts/check-plugin-web.sh` — jco transpilation and fresh/restored JS-host runs pass with
  the same overlay text and persisted tick as the native probe.

The GUI overlay acceptance pass (login → walk → overlay visible) is manual and not claimed here.
The protocol login probe against OpenRune 240.2 is already verified separately.

## Deliberately skipped

- **CP11.8 browser runtime.** Component-level parity is proven, but the browser worker, message
  boundary and IndexedDB-backed storage are not implemented yet. The guest has no WASI imports
  specifically so browser transpilation does not require preview2 shims.
- **CP11.9 SDK.** `plugin-sdk/rust` is still a facade stub; the sample carries the guest runtime
  plumbing. Extracting it needs a documented exception for the canonical-ABI realloc shim.
- **In-browser acceptance.** The JS proof runs in Node; loading the overlay from the web client is
  still open.
- **Visual acceptance.** No screenshots or manual in-world overlay check in this checkpoint.
- Plugin memory quotas, hot reload, menus/panels, interactive commands, signed packages,
  config schemas and cross-plugin dependencies are future checkpoints.

## Known limitations

- Overlay is text-only; coordinates are logical interface pixels, not widget-relative anchors.
- `Tick` dispatch is batched once per client frame, not once per committed game tick.
- Capability grants are manifest-declared and operator-authorized by directory placement; there
  is no interactive grant UI or signature verification yet.
- The Wasmtime 49 pin is driven by the current rustc 1.96 toolchain (Wasmtime 50 needs 1.97).
- The JS host harness persists config with Node's filesystem; only the ABI and guest behavior are
  browser-equivalent so far.
