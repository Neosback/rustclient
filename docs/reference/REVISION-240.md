# Revision-240 integration guide

Target: an OSRS/RuneLite-style Rust client for OpenRune 240.2, with one shared
semantic implementation for downloadable desktop and browser Wasm. A simpler,
more capable plugin authoring experience is a product goal; plugin runtimes and
browser gameplay are not implemented merely because their crates exist.

## Evidence and revision changes

Use RuneLite's deobfuscated `runescape-client/src/main/java` as the client-behavior
reference. Use RustOSRS to understand its Rust realization and GPU conventions.
For a disagreement, identify the client rule and the actual 240 input before
changing RustClient. RustOSRS HD/editor behavior is not the classic parity target.

Use **both** rsprox directions:

- `protocol/osrs-235/src/main/kotlin/net/rsprox/protocol/v235/game/incoming/decoder/`
  contains the client packet IDs, lengths and decoder field reads (client sends).
- The adjacent `game/outgoing/decoder/` contains server packet IDs, lengths and
  payload interpretation (client receives).
- Shared models and codecs under `protocol/src/` explain the semantic message.
  Revision-specific imports may reuse older modules; follow them to the actual
  implementation instead of assuming every codec lives in the latest folder.

For each 240 packet family, compare the rsprox/deob evidence with OpenRune and
**the server-selected rsprot sources**, including ID, fixed/variable length,
ISAAC/framing, field order, endian/add/neg/sub transforms, masks and bit widths.
Translate verified input into existing typed operations and outbound intents in
`crates/protocol/src/openrune_240/`; keep opcode tables out of other subsystems.
Never copy a 235 opcode to the 240 registry solely because its name matches.

OpenRune's `gradle/libs.versions.toml` selects rsprot
`1.0.0-ALPHA-20260825`. Another local version (`20260912`) is also cached; its
presence must not silently change our profile. Source JARs are under
`~/.gradle/caches/modules-2/files-2.1/net.rsprot/osrs-240-*/<version>/`.
The source manifest records hashes for the selected api/model/internal/shared
artifacts. Inspect archives read-only or extract to `target/reference-research/`.
These are development evidence, never RustClient runtime dependencies.

OpenRune FileStore's relevant areas are `definition/osrs/.../codec`,
`definition/.../opcode`, `definition/.../codec/ModelCodec.kt` and
`filestore/.../definition/`. Check definition widths/opcodes against actual
240 cache bytes. Keep private-server repack accommodations in `CacheProfile`.

## Deobfuscation and decompilation

The supplied RuneLite tree already contains deobfuscated **235** Java source.
`buildSrc/src/main/kotlin/Dependencies.kt` declares `rsversion = 235`.
`deobfuscator/deobfuscator.gradle.kts` defines the exact tools:

1. `Deob$main()` runs `net.runelite.deob.Deob` over a gamepack and emits a
   deobfuscated JAR.
2. `UpdateMappings$main()` maps the old annotated client onto that new JAR.
3. `buildSrc/src/main/kotlin/FernflowerTask.kt` decompiles the mapped result.

Deobfuscation, mapping and decompilation are separate steps; changing a Gradle
revision number does not supply a new gamepack or validate transferred mappings.
A version-verified **240 gamepack** and its SHA-256 are required for a 240 deob.
The local `~/gamepack/gamepack_235.jar` is 235 evidence; the unversioned
`gamepack.jar` has not been established as 240. No 240 deob run is claimed.

Run this toolchain only in an isolated scratch copy outside `references/` and
outside the supplied source directories. Configure the explicit input/output
paths and exact revision there. Record input hash, tool/tree identity, mapping
warnings, source hashes, and scratch adjustments. Keep original reference trees
read-only. Do not use the downloader's current live gamepack as an implicit 240
input, overwrite reference Java with generated output, or commit binary cache and
account material. Until verified 240 deob output exists, use 235 behavior with
explicit 240 cross-checks and mark uncertain rules unresolved.

## Ownership and rendering

- `cache`: bounded revision-specific decoding to typed immutable assets.
- `game-state`: authoritative entities, appearance, vars, locations and cycles.
- `scene`: disposable assembled models, transforms, normal reconciliation,
  lighting, animation poses, terrain and visibility.
- `renderer`: shared wgpu/WGSL uploads, passes and presentation.
- `platform-*`: transport, storage, timing, windows/canvas and platform I/O.

Preserve model merge/dedup rules, placement offsets, mirror/rotation/scale/translate
order, normal merge eligibility, contouring, material/alpha/priority and bone
coordinate conventions. These are coupled client rules; a plausible-looking
mesh is not parity evidence. GPU reconstruction must use retained CPU assets and
committed state. Reference handles and Java classes never cross public APIs.

## One source for desktop and web

Share domain, profiles/codecs, cache decoders, scene, widgets/scripts, renderer and
plugin contract. Desktop TCP/files and browser WebSocket/cache import are adapters.
A browser needs a server-supported WebSocket endpoint or explicit relay; it cannot
use raw TCP. Preserve game framing/cipher order across transport chunk boundaries.
Select the same server/cache profile and semantic behavior on both targets.

Current browser composition only creates a canvas and clear frame. It still needs
cache import/storage, title/session integration, transport/relay, input mapping,
world presentation and plugin execution. A wasm compile pass proves build
compatibility, not these user flows. See the execution plan's current priorities.

## Plugin experience target

Keep the accepted WIT/Component ABI and separate public semantic types. Translate
RuneLite feature ideas into batch event subscriptions, approved queries and
validated overlay/panel/menu commands. Plugins get no internal mutable client,
raw sockets/files or GPU handles. Both runtimes enforce the same capabilities,
ordering, storage namespace, budgets and fault behavior.

The first useful slice is one sample plugin that subscribes to a committed event,
queries one value, persists its settings and draws an overlay on desktop **and**
web. Build it before promising compatibility for an entire RuneLite plugin catalog.
Then deliver create/build/load/reload tooling, generated bindings, typed config,
logs/event inspector and replay debugging without editing client internals.
Keep SDK boilerplate low and distinguish required capabilities from optional
ones in install diagnostics. RuneLite Java plugins require ports, not direct loading.

## Verification volume

[ADR-0004](../adr/ADR-0004-verification-policy.md) governs a small semantic/codec
suite plus **one manual rendering acceptance pass when the renderer feature set
is complete**. No per-object pixel tests, screenshot farm, or repeated automatic
render checks. Subsequent full parity runs are explicit acceptance work.
`tools/parity/oracle.py` captures independent reference evidence; it does not itself
compare every acceptance gate, and `cargo xtask parity` is not implemented yet.
No pass is claimed until reference/actual/difference artifacts and every gate's
PASS/FAIL/NOT-RUN result are recorded. Continue ordinary compile/lint/semantic
checks without treating them as visual proof.
