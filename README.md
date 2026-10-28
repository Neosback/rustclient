# RustClient

RustClient is a Rust client platform for private servers, targeting nearly 1:1
OSRS 240 behavior and applicable pinned RuneLite features. Desktop and browser
share one semantic core, explicit revision/cache profiles and a sandboxed,
versioned plugin ecosystem. Rust-specific improvements must preserve behavior
or have a documented, evidenced departure under [SPEC-0019](docs/specs/SPEC-0019-port-parity.md).

## Status

The native client has cache-backed login/world/gameframe, player/NPC movement and
selected action/chat/widget/CS2 paths. World-map assets/controller, viewport and
overview rendering, native pan/zoom and compass reset exist. Full gameplay parity
remains incomplete: selected NPC masks can terminate sessions, instances are
rejected, ground-item and server scenery updates/actions/dialogue are incomplete,
actor effects/model widgets/audio and many packet/script consumers remain open.
Server pings and reflection checks go unanswered, and a reasoned server logout ends
as a protocol error. Of 606 named CS2 opcodes, 185 still fail in the host. Of 131
pinned RuneLite plugins, 11 have partial ports.

The native WIT component/host/overlay sample and a JS-host transpilation proof
exist; full plugin feature coverage and browser runtime integration remain open.
The browser app is still a renderer/canvas bootstrap. Native GPU-shell integration
at bd3aae10 replaces the earlier CPU-DOM mechanism; this documentation review does
not certify its controls, performance or live behavior.

Follow [the porting roadmap](docs/plan/PORTING-ROADMAP.md),
[the current audit](docs/reference/rs240-port/CURRENT-AUDIT.md) and
[the domain matrix](docs/reference/rs240-port/PORT-MATRIX.md). Historical checkpoint
validation is bounded to its recorded source snapshot. No full-client, visual or
browser gameplay parity is claimed. [The documentation index](docs/README.md)
identifies canonical owners and historical evidence.

## Architectural goals

- Rust owns the client core and domain model.
- Native and web targets share domain, cache, scene, UI, renderer, and plugin-facing semantics.
- `GameState` is authoritative; render state and plugin views are projections.
- Server/revision differences live behind explicit profiles/codecs, not scattered conditionals.
- Plugins depend on a versioned public API, never internal crate types.
- WebAssembly Component Model/WIT is the intended cross-platform plugin ABI.
- Architecture follows crate ownership and a small semantic/codec suite; rendering has one manual acceptance gate when complete (ADR-0004).
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

The reference trees are outside the RustClient Cargo workspace. The exact-240
evidence is the rl-suite deob (`references/rl-suite-240`), the open-runelite 240
tree and the pinned injected 240 jar; the readable RuneLite 235 tree, rsprox and the
RustOSRS submodule supplement them. rsprox supplies both client and server packet
evidence, including an `osrs-240` module; OpenRune and its pinned rsprot sources
resolve revision-240 differences. OpenRune FileStore supplies additional cache
opcode/decoder evidence. See [the integration guide](docs/reference/REVISION-240.md)
and [the current delivery plan](docs/plan/REVISION-240-delivery.md).

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
RSA/XTEA/ISAAC and proof of work, and opens the cached spawn region. Disconnect or Escape ends the session. Passwords are erased after
handoff and are excluded from preferences, semantic state, replay and diagnostics.

The script selects the explicit `openrune-240` cache repack adapter (including a reported stale object-archive size hint), and supplies `--server-profile openrune-240.2` and `--rsa-public-key PATH`;
`--server-host IP` and `--server-port PORT` override localhost:43594. This initial
native transport accepts IP literals or `localhost`; a DNS resolver and browser
relay are later adapters. The separate Desktop/LIVE snapshot is **not** the same
cache as this server's modified LIVE cache. Do not substitute its checksums.

The world projection decodes the normal 104×104 window around the server's map
anchor. Camera input, cached scenery, variable-driven location changes, textures,
entity assets and gameframe work are present; full reference parity is unverified.
F5 recreates GPU resources from retained CPU assets. Instances, complete entity
movement/animation selection and full visual parity remain unfinished. Cache widgets,
bounded scripts and banking now have integration evidence; see the
[playable desktop checkpoint](docs/plan/PLAYABLE-desktop.md) for manual gaps.
The original [spawn-scene checkpoint](docs/plan/SPAWN-scene.md) describes the earlier
static slice; [SPEC-0016](docs/specs/SPEC-0016-reference-scenery-parity.md) and
[SPEC-0017](docs/specs/SPEC-0017-gameframe-entities.md) define later work.

Capture fixed-camera GPU frames and verify byte-identical reconstruction from the server cache without connecting:

```sh
cargo run -p rustclient-native --bin scene_probe -- "/Users/tylercovalt/Desktop/OpenRune Project/OpenRune-Server/.data/cache/LIVE" target/scene-captures
```

The title bar shows FPS and resident memory (MiB RSS). The development launcher
optimizes the gameplay hot paths while retaining debug assertions and symbols.
Set `RUSTCLIENT_FRAME_TIMINGS=1` for timing summaries. The normal gameframe comes
from cache definitions and scripts, including the chatbox.

The headless `login_probe` binary accepts cache/public-key paths and exactly two
stdin lines (username and password), never a password command-line argument. It
checks localhost:43594, runs an eight-second session and explicitly disconnects.
Add `--walk-and-bank` for the disposable 10-coin bank loop, or `--verify-bank`
for a separate-login persistence check; see the checkpoint for fixture setup.

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
