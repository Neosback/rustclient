# RustClient Master Implementation Plan

This plan expands `SPEC-0010` into execution-sized work packages. Specs remain the normative behavioral source. This file defines sequencing, expected artifacts, and checkpoint evidence.

## Offline title milestone

The user-approved [SPEC-0013](../specs/SPEC-0013-offline-title.md) vertical slice
implements cache-facing CP4 primitives and title-specific CP6/CP9/input work
before live networking. See [the completed checkpoint](TITLE-offline-cache.md).
It does not close the remaining CP4–CP10 work or claim an authenticated session.

## Authenticated bootstrap milestone

The user-approved [SPEC-0014](../specs/SPEC-0014-openrune-login.md) slice adds the
first concrete server profile, transport and login exchange after title loading.
See [the connection checkpoint](OPENRUNE-login.md). It proves authentication and
initial placement; broader entity, map, widget and script checkpoints remain open.

## Planning rules

- Work in checkpoint branches or tightly scoped sub-branches.
- Every task has one semantic owner.
- Prefer vertical proofs only after the lower-level contract exists.
- No checkpoint closes with TODO-only interfaces standing in for required behavior.
- If implementation discovers a bad boundary, amend the spec/ADR before spreading a workaround.
- Record exact commit, exact tests, and skipped validation when closing a checkpoint.

---

# CP0 - Foundation

## CP0.1 Workspace topology

Create and keep compiling:

- `apps/native`, `apps/web`
- domain/core crates
- platform adapters
- plugin API/host/runtime crates
- SDK/WIT directories
- `xtask`

**Evidence:** Cargo metadata resolves every workspace member; native and wasm composition roots compile.

## CP0.2 Documentation hierarchy

Create:

- root architecture map,
- spec governance,
- initial system/state/platform/plugin/protocol/cache/render/test/security specs,
- ADRs for the highest-cost decisions,
- root and nested `AGENTS.md` files.

**Evidence:** each architectural owner has one canonical document; nested agent files add local rules without duplicating the root.

## CP0.3 CI baseline

CI gates:

- formatting,
- workspace check,
- Clippy `-D warnings`,
- workspace tests,
- wasm target compile.

Later add architecture and docs-link checks as their tooling exists.

## CP0.4 Architecture checker design

Define a machine-readable dependency policy. Initial checks should reject obvious edges such as:

- `game-state -> renderer`
- `game-state -> protocol`
- `protocol -> renderer`
- `cache -> renderer`
- `plugin-api -> game-state internals`
- shared domain crates -> native/web adapters.

Implement as `cargo xtask architecture` once real crate dependencies are introduced.

## CP0.5 Reference baseline

Pin `Neosback/RustOSRS` as read-only implementation evidence under `references/rustosrs`.

The pinned snapshot supplies:

- RustOSRS Rust/editor implementation evidence,
- the bundled RuneLite reference tree,
- the bundled OpenRune server reference tree.

Add scoped reference-agent rules, a reference index and `cargo xtask references sync/status` tooling. Amend `SPEC-0005` so revision `240` is the first implementation baseline while preserving explicit multi-revision/profile architecture.

**Evidence:** the reference path is a mode `160000` gitlink, `cargo xtask references status` validates the checkout against the gitlink, and the ordinary RustClient CI suite remains green.

**CP0 exit:** repository has a stable skeleton, pinned reference evidence and green baseline CI.

---

# CP1 - Semantic model

## CP1.1 Coordinate taxonomy

Define separate typed concepts for:

- world tile coordinates,
- region/local scene coordinates,
- fine/subtile coordinates if needed by the target protocol family,
- planes,
- viewport/screen coordinates,
- model/local-space coordinates.

Do not use one generic point type across spaces.

Tests:

- conversions at region boundaries,
- negative/out-of-range behavior where applicable,
- round-trip properties for reversible conversions.

## CP1.2 Typed identities

Introduce newtypes for:

- player/NPC runtime index or handle,
- object/item/NPC definitions,
- model/sprite/texture/sequence IDs,
- widget/interface/component IDs,
- varp/varbit IDs,
- region/map IDs.

Document source identity versus runtime identity.

## CP1.3 Time and orientation

Define game tick, monotonic duration, orientation/rotation, plane and angle semantics. Avoid encoding protocol-specific bit widths in the semantic API.

## CP1.4 Error conventions

Establish subsystem errors with contextual source location/identifier fields. Decide which internal errors are recoverable, connection-fatal, asset-local, or programmer invariants.

**CP1 exit:** higher crates can represent core semantic concepts without protocol or renderer primitives.

---

# CP2 - Platform contracts and shared renderer bootstrap

## CP2.1 Clock capability

Define platform monotonic and wall-clock contracts with native/web implementations. Semantic game ticks remain owned by the model/runtime layer rather than being derived from a platform clock type.

## CP2.2 Storage capability

Define key/value and blob storage contracts with explicit limits/errors. Native/web adapters implement them without leaking filesystem or browser-storage details inward. CP2 only proves small persistent blobs; bulk browser cache storage is deferred.

## CP2.3 Transport capability - deferred

Do not add a placeholder transport abstraction in CP2. Networking is intentionally deferred until revision-240 protocol/replay requirements make the consuming contract concrete. Later native TCP and browser WebSocket/WebTransport adapters will remain below protocol framing and revision semantics.

## CP2.4 Shared `wgpu` bootstrap

Create one renderer surface/device abstraction used by both composition roots.

Proof:

- desktop window compiles against the shared deterministic diagnostic clear-frame path,
- browser canvas compiles against that same renderer path,
- shared renderer code contains no native/browser branching or `winit` dependency,
- actual visual runtime smoke tests are recorded separately when interactive native/browser runners are available.

**CP2 exit:** native and browser can execute the same renderer API and platform-neutral runtime primitives, with transport deliberately absent rather than prematurely specified.

---

# CP3 - Authoritative runtime and replay

## CP3.1 `GameState`

Introduce the single-writer state container and mutation gateway.

## CP3.2 Semantic operations

Define input/domain operations independent of packet opcode, UI toolkit and renderer.

## CP3.3 `StateDelta` / event batches

Committed mutations produce deterministic deltas/events consumed by projections.

## CP3.4 Replay envelope

Define a versioned replay format for semantic operations/events plus fixture metadata.

**CP3 exit:** deterministic replay reconstructs equivalent semantic state without network or GPU involvement.

---

# CP4 - Binary/protocol primitives

## CP4.1 Bounded readers/writers

Implement endian variants, transformed bytes/shorts/ints where required, smart values, strings, bit access and length framing with explicit errors.

## CP4.2 Codec property/fuzz tests

Malformed lengths/bitstreams never panic or allocate unbounded memory.

## CP4.3 Revision/profile interfaces

Define protocol codec ownership selected by `ServerProfile`; still no live login requirement.

**CP4 exit:** revision codecs can be implemented without exposing byte quirks outside `protocol`.

---

# CP5 - First private-server profile

## CP5.1 Choose/pin target

Record the first server/revision/cache assumptions as fixtures and profile metadata.

## CP5.2 Login/framing codec

Implement login response/request framing and connection state machine against offline/golden fixtures first.

## CP5.3 High-value inbound operations

Decode enough region/player/NPC/var/widget operations to drive semantic fixtures.

## CP5.4 Outbound semantic intents

Encode a minimal interaction subset through profile-owned encoders.

**CP5 exit:** a profile can translate between representative server bytes and semantic operations with deterministic fixtures.

---

# CP6 - Cache/assets

## CP6.1 Cache source abstraction

Support local/native and browser-compatible asset sources without leaking platform details.

## CP6.2 Containers/indexes

Decode required archive/index/compression formats with bounds.

## CP6.3 Semantic definitions

Object/NPC/item/sequence/spotanim/varbit/etc. decode into typed CPU-side definitions retaining source identity.

## CP6.4 Media/model assets

Models, textures, sprites and fonts decode into renderer-neutral CPU representations.

**CP6 exit:** fixtures decode deterministically on native and wasm-compatible paths.

---

# CP7 - Scene and classic renderer

## CP7.1 Terrain/tiles

Build semantic scene tiles from map/cache definitions.

## CP7.2 Loc/object placement

Footprints/orientation/planes/collision-visible scene semantics are typed and testable.

## CP7.3 Render projection

Generate disposable `RenderWorld`/GPU data from semantic scene state.

## CP7.4 Classic parity fixtures

Add deterministic camera/render captures for representative tile/object/model cases.

**CP7 exit:** static revision-compatible map scenes render natively and in browser from the same semantic inputs.

---

# CP8 - Entities and animation

Players, NPCs, projectiles, graphics objects, spot animations, sequence blending, orientation and interpolation.

Tests emphasize tick timing and deterministic semantic outputs, not only screenshots.

---

# CP9 - Widgets and client scripts

Decode/represent game interfaces separately from shell UI. Implement widget hierarchy/layout/state, text/sprites/models, vars and the required client-script VM semantics incrementally from fixtures.

---

# CP10 - Input and interaction

Typed pointer/keyboard/menu input becomes semantic intents. Introduce `UserGestureToken` or equivalent provenance token before any plugin API can request player-affecting actions.

---

# CP11 - Plugin ABI/runtime

## CP11.1 WIT package structure
## CP11.2 Manifest/package format
## CP11.3 PluginHost lifecycle state machine
## CP11.4 Event subscriptions
## CP11.5 State query API
## CP11.6 Plugin command buffers
## CP11.7 Native Wasmtime runtime
## CP11.8 Browser worker runtime
## CP11.9 Rust SDK
## CP11.10 Cross-platform sample plugin

**Exit:** the same packaged sample plugin runs under native and web hosts against the same semantic fixture.

---

# CP12 - Plugin tooling and optional scripting

Implement scaffold/build/test/dev/hot-reload tooling, event/state inspectors and deterministic replay-based plugin tests. Prototype optional Rhai/Luau only through `PluginHost` services; select or reject by ADR after measurements and DX evaluation.

---

# CP13 - Second server profile proof

Implement a meaningfully different protocol/revision profile. This is the architecture test that revision isolation is real rather than theoretical.

**Exit:** adding profile two requires changes in profile/codec/fixtures and composition configuration, not renderer/plugin/generic game-state revision branches.

---

# CP14 - Performance program

Establish CPU frame/tick budgets, allocation tracking, GPU timings, scene rebuild costs, plugin budgets and browser memory limits. Optimize from profiles/benchmarks rather than intuition.

---

# CP15 - Distribution and plugin registry

Define signed package metadata, trust/review model, capability declarations, compatibility ranges, update flow and reproducible release packaging.

---

# CP16 - Production hardening

Crash/device-loss recovery, corrupted caches, hostile packets, plugin exhaustion, storage quota failures, browser suspension/resume, telemetry/privacy controls and migration/versioning guarantees.

## Cache-backed spawn preview checkpoint

The focused SPEC-0015 slice follows authenticated initial placement and precedes general world/entity work. Cache adapters now decode actual terrain and static location geometry; GameState selects an immutable scene descriptor through replayable schema-v4 operations. Camera/GPU buffers are disposable. See [SPAWN-scene.md](SPAWN-scene.md) for measured completion and deliberate material/entity limits. This does not complete the general CP6 scene, CP7 entity, widget or script milestones.
