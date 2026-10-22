# RustClient

RustClient is a greenfield OSRS-style client platform for private servers. The goal is one maintainable source tree for native desktop and browser targets, with revision-aware protocol/cache support and a sandboxed, versioned plugin ecosystem.

## Status

The desktop client loads a local JS5 cache and renders an animated login screen using real cache sprites and bitmap fonts. The opt-in OpenRune 240.2 profile authenticates against the development server and receives initial player placement. After placement it loads and renders real cached terrain and static location models around spawn. Live entities/movement, full texture materials, widgets and CS2 remain deferred. No production protocol, renderer, or plugin ABI is considered stable yet.

The first implementation compatibility target is revision `240`, while the architecture is designed to support additional validated revisions/profiles later.

## Architectural goals

- Rust owns the client core and domain model.
- Native and web targets share domain, cache, scene, UI, renderer, and plugin-facing semantics.
- `GameState` is authoritative; render state and plugin views are projections.
- Server/revision differences live behind explicit profiles/codecs, not scattered conditionals.
- Plugins depend on a versioned public API, never internal crate types.
- WebAssembly Component Model/WIT is the intended cross-platform plugin ABI.
- Architecture is enforced by crate ownership, tests, and eventually `xtask` dependency checks.
- Specs and ADRs precede large implementation changes.

## Reference sources

Pinned implementation evidence lives under `references/`. Initialize it with:

```text
cargo run -p xtask -- references sync
```

Verify the exact pinned checkout with:

```text
cargo run -p xtask -- references status
```

The reference tree is not part of the RustClient Cargo workspace and is not architectural source of truth.

Start with [`ARCHITECTURE.md`](ARCHITECTURE.md), [`docs/specs/README.md`](docs/specs/README.md), and [`docs/reference/INDEX.md`](docs/reference/INDEX.md).

## Connect to the OpenRune development server

With the server running, use its own LIVE cache and public RSA key:

```sh
scripts/run-openrune.sh "/Users/tylercovalt/Desktop/OpenRune Project/OpenRune-Server"
```

Existing User opens the credential form. Use a new dev-server username of at most
12 characters to create a disposable account. The form retains the reference
320/20 character editing limits; server account policy can reject longer names.
Login verifies the 23 desktop archive CRCs against the actual JS5 service, completes
RSA/XTEA/ISAAC and proof of work, and opens a static preview of the cached spawn region. Disconnect or Escape ends the session. Passwords are erased after
handoff and are excluded from preferences, semantic state, replay and diagnostics.

The script selects the explicit `openrune-240` cache repack adapter (including a reported stale object-archive size hint), and supplies `--server-profile openrune-240.2` and `--rsa-public-key PATH`;
`--server-host IP` and `--server-port PORT` override localhost:43594. This initial
native transport accepts IP literals or `localhost`; a DNS resolver and browser
relay are later adapters. The separate Desktop/LIVE snapshot is **not** the same
cache as this server's modified LIVE cache. Do not substitute its checksums.

The authenticated world preview decodes the normal 104×104 window around the server's map anchor. Right-drag or arrows orbit; wheel zooms; Home resets; F5 recreates GPU resources from retained CPU assets. A gold marker shows initial placement. Objects use static geometry, with average texture colors and flat lighting. Var-driven morphing objects are deferred. Ongoing player/NPC updates, movement, instances, widgets and scripts remain unimplemented. See [the spawn-scene checkpoint](docs/plan/SPAWN-scene.md) for exact evidence and limits.

Capture fixed-camera GPU frames and verify byte-identical reconstruction from the server cache without connecting:

```sh
cargo run -p rustclient-native --bin scene_probe -- "/Users/tylercovalt/Desktop/OpenRune Project/OpenRune-Server/.data/cache/LIVE" target/scene-captures
```

The headless `login_probe` binary accepts cache/public-key paths and exactly two
stdin lines (username and password), never a password command-line argument. It
checks localhost:43594, runs an eight-second session and explicitly disconnects.

## Run the offline title preview

```sh
cargo run -p rustclient-native -- --cache-dir /Users/tylercovalt/Desktop/LIVE --cache-profile osrs-240
```

The cache is opened read-only. Select the profile explicitly; cache table revisions
are not client revision detection. The native window opens at 765×503 logical
pixels and supports resize/DPI changes. Existing User opens the form; Enter,
Tab/Shift-Tab, Backspace, Escape and mouse controls work locally. Login reports
that server authentication is not configured. Passwords are never persisted.
Music is silent and world selection is unavailable.

Display preferences default to `$HOME/.rustclient/title/preferences.json`;
`--preferences-dir PATH` overrides that directory. Remember username defaults off.
Browser cache import remains deferred; shared decoder/UI/renderer code still
compiles for wasm.

Inspect metadata and validate every required title file (CRC, stored version,
decoded size metadata and source hashes):

```sh
cargo run -p xtask -- cache inspect --cache-dir /Users/tylercovalt/Desktop/LIVE --cache-profile osrs-240
```

Generate deterministic **GPU-rendered** welcome, credential, offline, authenticated-fixture and HiDPI PNG
captures and verify byte-identical reconstruction after recreating GPU resources:

```sh
cargo run -p rustclient-native -- --cache-dir /Users/tylercovalt/Desktop/LIVE --cache-profile osrs-240 --capture-dir target/title-captures
```

Capture mode does not load or modify personal preferences. Its sample credentials
are fixed local test strings. See [the title contract](docs/specs/SPEC-0013-offline-title.md)
for ownership, supported formats and deliberate compatibility limits.
