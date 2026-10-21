# RustClient Master Implementation Plan

This plan expands `SPEC-0010` into execution-sized work packages. Specs remain the normative behavioral source. This file defines sequencing, expected artifacts, and checkpoint evidence.

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

**CP0 exit:** repository has a stable skeleton and green baseline CI.

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

Contracts for monotonic time and wall-clock time. Domain code receives time rather than reading global clocks.

## CP2.2 Storage capability

Async key/blob store with namespaces and atomic-write expectations. Implement:

- native filesystem/database-backed adapter,
- browser IndexedDB-style adapter.

Do not expose paths to shared domain APIs.

## CP2.3 Transport capability

Define transport traits around connection establishment and ordered byte/message I/O. Keep packet framing outside this layer.

Implement initial adapters:

- native TCP,
- browser WebSocket.

Leave room for native WebSocket and future WebTransport.

## CP2.4 Window/canvas and GPU bootstrap

Add `winit`/browser canvas composition and initialize `wgpu` through one renderer-facing API.

Vertical proof: same scene primitive/diagnostic frame from one renderer implementation on native and web.

## CP2.5 Logging/diagnostics bootstrap

Structured tracing sinks for native console/files and browser console.

**CP2 exit:** native and browser are real executable surfaces sharing the renderer/application interfaces.

---

# CP3 - Deterministic runtime and GameState

## CP3.1 GameState root

Define session-scoped root state with explicit reset/connect/disconnect lifecycle.

Initial owned domains:

- session/profile identity,
- vars,
- skills,
- inventories/containers,
- entity registries,
- scene semantic state,
- widget state,
- chat/message state as needed.

## CP3.2 Domain operation model

Define typed inbound/local/timer/script operations. Operations are validated before mutation.

## CP3.3 Transaction/batch ordering

Specify processing order for one logical update cycle. No subsystem may mutate state outside the runtime transition API.

## CP3.4 Event and StateDelta generation

Generate committed fact events and dirty/delta records from transitions. Events should not require consumers to diff entire state.

## CP3.5 Deterministic services

Injected clock/random providers. Stable ordering in any collection that affects output.

## CP3.6 Replay envelope v0

Define an internal unstable replay envelope carrying profile metadata, operation batches, local intents, timing and seeds.

**CP3 exit:** a synthetic replay can produce deterministic state/event hashes without network/GPU.

---

# CP4 - Binary and protocol primitives

## CP4.1 Safe read/write cursors

Implement bounded cursor APIs for primitive integers, endian variants, transformed bytes/shorts/ints as needed by target protocol families.

## CP4.2 Protocol value encodings

Implement smart/compact values, strings and any family-specific variable integer formats behind named primitives.

## CP4.3 Packet framing

Length tables, fixed/byte/short-variable packet lengths, maximum-size policy, partial stream handling and queueing.

## CP4.4 Cipher interfaces

Define inbound/outbound opcode cipher state independently of framing. Add concrete algorithms only when required by the first profile.

## CP4.5 Decoder hardening

Property/fuzz tests for truncation, oversized lengths, invalid strings, malformed masks and cursor underflow/overflow.

**CP4 exit:** protocol foundation can safely parse hostile byte streams without involving game state.

---

# CP5 - First reference private-server profile

The first profile becomes the architecture proving ground. Pin its exact revision/server assumptions in fixtures/docs rather than calling it simply "OSRS".

## CP5.1 ServerProfile metadata

Stable profile ID, display name, protocol family/revision, cache family, transport requirements and extension negotiation information.

## CP5.2 Login/bootstrap codec

Handshake, seed/session exchange, login payload, response parsing and reconnect/logout states required by the selected server.

## CP5.3 Initial packet registry

Implement enough inbound semantic operations for:

- local player/session initialization,
- region/scene load,
- player/NPC basics,
- var/skill/container updates,
- widgets/messages required to reach a usable session.

## CP5.4 Initial outbound registry

Enough semantic actions for movement/menu/widget/chat or other minimum interactions selected for the vertical slice.

## CP5.5 Fixture server or recorded byte fixtures

Prefer a deterministic test fixture server for login/framing integration plus pinned packet fixture vectors.

**CP5 exit:** connection reaches a semantic logged-in state; no opcode leaks outside protocol/profile implementation.

---

# CP6 - Cache and assets

## CP6.1 Cache source abstraction

Read archive/index/group/file data from native local cache, packaged/web source, or server-provided source without changing decoders.

## CP6.2 Container/compression layer

Bounds-limited decompression with explicit archive/group/file context.

## CP6.3 Definition families

Prioritize definitions required for the first playable scene:

1. underlays/overlays,
2. objects,
3. NPCs,
4. items,
5. sequences,
6. spot animations,
7. varbits,
8. widgets/scripts as profile needs dictate.

## CP6.4 Sprite/texture/model decode

Keep decoded CPU representation independent from `wgpu`.

## CP6.5 Asset repository/cache

Typed lookup service, explicit missing/corrupt behavior, CPU memory accounting and eviction design.

**CP6 exit:** a reference region and its required entities/UI assets can be decoded entirely from pinned cache fixtures.

---

# CP7 - Scene and classic rendering vertical slice

## CP7.1 Scene build

Terrain heights, overlays/underlays, tile shapes, collision/occupancy metadata and static object placement from semantic map data.

## CP7.2 Model preparation

Transform cache models into renderer-ready CPU mesh/material data without losing source identity.

## CP7.3 RenderWorld projection

Dirty tracking from state/scene into disposable rendering data.

## CP7.4 GPU resources

Mesh/index buffers, texture arrays/atlases where appropriate, bind group/material strategy, depth and camera resources.

## CP7.5 Classic scene passes

Terrain, static objects, entities as they become available, alpha/transparency rules and ordering required for reference parity.

## CP7.6 Camera and visibility

Viewport projection, camera movement, frustum/scene visibility and clipping semantics.

## CP7.7 Picking

Generate/select clickable geometry from the same transforms used for visual rendering.

**CP7 exit:** pinned reference scene renders on native/web and can be rebuilt from state/cache after renderer reset.

---

# CP8 - Dynamic entities and animation

## CP8.1 Entity lifecycle

Spawn/update/despawn semantic state for players, NPCs, projectiles and graphics objects required by profile.

## CP8.2 Appearance/model composition

Player equipment/colors and NPC transformations/variants.

## CP8.3 Animation system

Sequence timing, skeleton transforms, interpolation policy if supported, movement/stance/action precedence and spot animations.

## CP8.4 Movement

Server path updates, local prediction only if explicitly designed, orientation/turning and teleport semantics.

## CP8.5 Projection performance

Generation counters and incremental renderer uploads for changing entities.

**CP8 exit:** recorded entity sessions replay identically and render without renderer ownership of gameplay truth.

---

# CP9 - Widgets, menus, and client scripts

## CP9.1 Widget model

Parent/child identity, layout/alignment, visibility, scroll, text, sprites, models, inventories and interaction metadata.

## CP9.2 Layout engine

Deterministic layout independent from native/web shell toolkit.

## CP9.3 Menu model

Semantic menu entries, target/action metadata, ordering and plugin decoration hooks.

## CP9.4 Script VM foundation

Stacks/locals/call frames, instruction budget, errors, deterministic execution and profile-specific opcode tables.

## CP9.5 Required script opcode families

Implement from fixtures/use rather than trying to implement every opcode before first UI works.

## CP9.6 Fonts/text

Game-specific font decoding/layout/drawing with renderer integration.

**CP9 exit:** representative gameplay interfaces work in replay mode and have inspectable script/widget state.

---

# CP10 - Input and outbound interaction

## CP10.1 Input normalization

Mouse, keyboard, wheel, touch/pointer mapping where appropriate; platform events become semantic input events.

## CP10.2 Hover/picking/menu construction

Scene/widget hits feed a deterministic menu builder.

## CP10.3 Interaction intent

Menu selection creates a semantic intent, not a packet.

## CP10.4 Policy validation

Validate local state, plugin/user source, server profile capabilities and any interaction restrictions.

## CP10.5 Protocol encode

Profile converts accepted intent to outbound packet(s).

**CP10 exit:** a user action is traceable across every boundary with no packet construction in UI or renderer code.

---

# CP11 - Plugin platform vertical slice

## CP11.1 WIT package structure

Split interfaces by least privilege: lifecycle, events, game queries, overlays, panels, config/storage, diagnostics, optional interaction.

## CP11.2 Manifest/package format

Stable ID/version/API compatibility/capabilities/entry artifact and integrity metadata.

## CP11.3 PluginHost state machine

Discover -> validate -> grant -> instantiate -> start -> running -> stopping/faulted/disabled.

## CP11.4 Event subscriptions

Plugins declare/filter events; host emits batches rather than invoking a crossing for every tiny event.

## CP11.5 Query API

Bounded semantic queries and snapshots. Avoid exposing the entire world as one giant serialized object each frame.

## CP11.6 Command buffers

Overlay/UI/notification/menu-decoration commands validated by host and applied after plugin execution.

## CP11.7 Native runtime

Wasmtime Component Model integration, resource/fuel/epoch limits, trap diagnostics and no ambient WASI permissions unless granted.

## CP11.8 Browser runtime

Component-to-browser toolchain, Web Worker isolation, host bridge, budget/watchdog behavior and graceful worker restart.

## CP11.9 Rust SDK

Ergonomic generated bindings, plugin trait/macros/helpers, typed events/queries/commands, config helpers.

## CP11.10 Sample plugin

A useful but small plugin such as tile/XP/entity overlay. Same source artifact pipeline proves native/web behavior.

**CP11 exit:** external plugin can do meaningful work without internal Rust types or privileged access.

---

# CP12 - Plugin tooling and scripting

## CP12.1 Developer loader

Local unsigned/dev mode with conspicuous trust status and deterministic reload lifecycle.

## CP12.2 Hot reload

Stop old instance, preserve/migrate allowed plugin storage/config, load new instance and report compatibility errors.

## CP12.3 Inspectors

Events, queries, capabilities, command buffers, logs, CPU/fuel and errors.

## CP12.4 Replay tests

SDK harness feeds recorded/synthetic events and asserts emitted plugin commands.

## CP12.5 Lightweight scripts research

Prototype Rhai and optionally Luau against the same host capability model. Choose only after measuring browser/native integration and developer value. Scripts must not bypass PluginHost policy.

---

# CP13 - Second profile architecture proof

Select a profile with meaningful differences: login, packet mappings/update masks, cache schema or custom extensions.

Required evidence:

- no renderer fork,
- no plugin API fork,
- no scattered revision conditionals,
- common semantic tests reused,
- only genuinely different adapters/profile modules added.

If this is painful, fix the abstraction before supporting many profiles.

---

# CP14 - Performance program

## Measurements first

Instrument:

- state update time,
- packet decode queues,
- cache decode latency,
- CPU asset bytes,
- GPU upload bytes,
- draw/dispatch counts,
- frame stages,
- entity/scene update counts,
- plugin execution/crossing cost,
- allocations in hot loops.

## Likely optimization tools

Only after profiling: arenas/slot maps, SoA hot data, command batching, persistent buffers, atlas/array textures, dirty generations, job preparation, cache eviction, event coalescing.

Every optimization requires before/after benchmark evidence and parity tests.

---

# CP15 - Packaging, plugin registry, trust

Native installers/update strategy, web build/deploy, cache bootstrap/update, plugin package hashing/signing, registry metadata, compatibility ranges, revocation/disable mechanism and reproducible plugin build investigation.

---

# CP16 - Production hardening

Reconnect/session failure handling, corrupt asset/cache recovery, settings/config migrations, plugin migration failure behavior, crash diagnostics, safe mode, accessibility/input options, browser storage quota behavior, low-memory behavior, updater rollback, release validation matrix and documentation.

---

# Definition of done for any checkpoint

A checkpoint close note must record:

- branch and exact head SHA,
- specs/ADRs changed,
- implementation artifacts,
- tests added,
- exact validation commands/checks that passed,
- checks intentionally skipped and why,
- known limitations carried forward,
- next checkpoint dependencies.

A passing demo alone is not checkpoint completion.
