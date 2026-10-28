# SPEC-0010: Implementation Roadmap

**Status:** Accepted as the initial foundation decomposition; execution order is superseded by [PORTING-ROADMAP.md](../plan/PORTING-ROADMAP.md) under [SPEC-0019](SPEC-0019-port-parity.md). CP exit criteria below remain the record for their checkpoints.

The roadmap is dependency-ordered. A checkpoint is complete only when its acceptance criteria and validation evidence are recorded. Do not skip semantics to build a visually impressive shell that cannot be tested.

SPEC-0014 permits authenticated OpenRune bootstrap immediately after the offline
title slice. Authentication and initial placement do not close world rendering,
general entities, widgets or CS2 checkpoints.

## CP0 - Repository and architecture foundation

Deliver:
- Cargo workspace and composition roots,
- crate ownership skeleton,
- root and scoped `AGENTS.md`,
- specs/ADRs,
- native + web CI compile gates,
- `xtask` home for architecture/tooling commands.

Exit:
- workspace compiles,
- `cargo fmt --check`, workspace check, Clippy and tests pass,
- wasm target checks,
- documentation links are valid.

## CP1 - Typed model foundation

Deliver typed coordinates, entity/asset IDs, units, colors, orientation/plane/region concepts, time/tick types and error conventions.

Exit: no protocol opcodes or renderer types in `model`; property tests for coordinate conversions.

## CP2 - Platform contracts + two shells

Deliver clock/storage/transport/window/canvas abstractions and minimal native/browser composition. Render a diagnostic clear-frame/triangle through the same renderer crate.

Exit: native window and browser canvas use shared renderer API; no domain target forks.

## CP3 - Deterministic state runtime

**Status after CP3J: complete.**

Deliver `GameState`, typed operations, ordered batches, events, deltas, explicit deterministic semantic time/random inputs, replay skeleton, and canonical repeatability evidence.

Implemented through CP3J:

- authoritative non-`Clone` `GameState`,
- session-owned deterministic game tick,
- source-neutral typed semantic operations,
- exact ordered batch semantics,
- post-commit semantic events and `StateDelta`,
- in-memory deterministic replay envelopes with exact commit verification,
- explicit session `Revision` identity that participates in replay,
- explicit deterministic randomness contract owned by `application`, including `RandomSeed`, raw `RandomSample`, injectable `DeterministicRandomSource`, and a regression-tested `SplitMix64Random` reference provider,
- versioned canonical `StateFingerprint` and ordered `CommitStreamFingerprint` surfaces with exact schema-v1 golden vectors,
- repeatability tests proving identical semantic inputs produce identical canonical final-state and committed-output fingerprints,
- replay tests proving recorded semantic operations reconstruct the same canonical state and committed-output fingerprints.

The deterministic-clock intent is satisfied by explicit `AdvanceTick(TickSpan)` semantic input. `GameState` does not read wall time or a clock provider internally; adding such a read would create a second time authority and weaken deterministic replay.

The deterministic-randomness intent is satisfied without putting hidden RNG state into `GameState`: application orchestration owns seeded deterministic raw-sample production, providers cannot consult platform entropy while drawing samples, and future random-dependent semantic behavior must receive deterministic random input explicitly at its semantic boundary. Native/browser entropy acquisition for choosing a live seed remains outside deterministic providers and is not required until a real live consumer exists.

The CP3 fingerprint exit evidence is intentionally non-cryptographic and versioned. It uses an explicit canonical semantic encoding rather than Rust randomized hashers or in-memory layout. Future modeled state/event fields must update the fingerprint contract explicitly; canonical output changes require a fingerprint-version bump and new golden vectors.

Exit: identical initial semantic state plus identical deterministic semantic inputs produce identical canonical state and committed-event/commit fingerprints in tests. **Satisfied by CP3J.**

Persistent replay serialization, concrete server profiles, protocol/networking, renderer/UI/plugin integration, richer game domains, projection rebuild tests before projections exist, and platform entropy acquisition remain later-checkpoint work and do not reopen CP3.

## CP4 - Binary/protocol primitives

The accepted [offline title milestone](SPEC-0013-offline-title.md) implements the
cache-facing binary subset and title-specific CP6/CP9 work before CP5. This does
not mark the remaining protocol, general assets, widgets or scripts complete.

Deliver buffers, endian/transforms, smart values, strings, framing, cipher interfaces, limits and fuzz/property tests.

Exit: malformed input cannot panic in covered decoders; primitive golden suite passes.

## CP5 - First server profile and login/session

Choose one concrete private-server revision/profile as the reference vertical slice. Implement handshake/login/framing and a minimal inbound/outbound packet set.

Exit: fixture server connection reaches an authenticated/logged-in semantic session; raw opcodes remain in protocol crate.

## CP6 - Cache container + first definitions

Deliver archive/container/compression pipeline and first semantic definitions/sprites/models needed by the reference server.

Exit: pinned fixtures decode deterministically with source context errors.

## CP7 - Scene and classic renderer vertical slice

Deliver terrain tile geometry, object/model placement, camera, texture/sprite basics, entity transforms, visibility and picking foundation.

Exit: a reference region renders equivalently from state+cache on native and browser.

## CP8 - Animation/entities

Deliver skeleton/sequence/model animation semantics, player/NPC appearance, spot animations/projectiles as required by the reference profile.

Exit: replayed entity motion/animation is deterministic and renderer remains projection-only.

## CP9 - Game widgets + client script VM

Deliver widget tree, layout, fonts/sprites/models in widgets, var state bindings, menus, core script VM/opcodes required by fixtures.

Exit: representative interfaces can be replayed and inspected without live network.

## CP10 - Input + interaction pipeline

Deliver mouse/keyboard/touch normalization, picking/menu intents, semantic outbound actions and revision encoding.

Exit: an input action flows `platform -> intent -> domain validation -> profile encoder` with tests.

## CP11 - Plugin ABI vertical slice

Deliver initial WIT package, manifest, capability grants, plugin host, native Wasm runtime, browser runtime/worker bridge, Rust SDK and sample overlay plugin.

Exit: same sample plugin source runs native and web, receives events, queries approved state, persists config and draws overlay commands.

## CP12 - Plugin developer tooling

Deliver local plugin loader, hot reload, event/API inspector, logs/metrics, replay-driven plugin tests and template CLI/xtask commands.

## CP13 - Revision/profile expansion

Prove architecture by adding a materially different private-server profile without introducing cross-layer revision conditionals.

Exit: architecture tests confirm separation; shared semantic tests remain unchanged where behavior is equivalent.

## CP14 - Performance hardening

Profile CPU, allocations, GPU work, cache residency, transport queues and plugin overhead. Optimize measured bottlenecks with regression benchmarks.

Targets must be hardware/profile documented, not arbitrary universal FPS claims.

## CP15 - Distribution and plugin trust

Deliver native packaging/updater strategy, web deployment packaging, plugin package signing/hash verification, registry metadata and compatibility reporting.

## CP16 - Production readiness

Crash recovery, corrupt-cache recovery, reconnect behavior, migration/versioning, accessibility/input settings, telemetry opt-in policy, diagnostics bundles and release checklist.

## Cross-checkpoint rule

Do not implement a second subsystem-specific version of a concept already owned elsewhere. If the existing owner is insufficient, change its spec/API rather than duplicating state or logic.
