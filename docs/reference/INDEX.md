# Reference Index

This index tells humans and coding agents where to look before reinventing OSRS-style behavior. Reference sources are evidence only; RustClient specs and code remain authoritative.

## RustOSRS snapshot

Path: `references/rustosrs/`

Use the pinned snapshot for behavior/provenance research. Never assume its architecture should be copied into RustClient.

### Rust editor / Rust implementation

Path: `references/rustosrs/crates/`

Useful for:

- cache/archive decoding,
- terrain and tile semantics,
- scene construction,
- object placement,
- models and model identity,
- textures/materials,
- animations,
- coordinates and region behavior,
- classic/HD rendering research,
- deterministic fixture ideas.

When porting behavior, find the owning RustClient crate first. The editor's types are not RustClient API types.

### RuneLite reference

Path: `references/rustosrs/RuneLite-melxin/`

Useful for:

- client-visible behavior,
- widgets/interfaces,
- client scripts,
- varps/varbits,
- menu/action semantics,
- player/NPC state presentation,
- animation behavior,
- plugin lifecycle and developer-experience ideas,
- API naming/behavior comparisons.

Do not reproduce Java-specific architecture, classloader assumptions, Guice wiring or mutable-client internals merely for parity.

### OpenRune server reference

Path: `references/rustosrs/OpenRune-Server-main/`

Useful for:

- revision-240-era private-server behavior,
- entity synchronization semantics,
- update masks,
- region/state expectations,
- cache assumptions,
- vars/widgets where server behavior matters,
- packet/login/protocol research when networking work begins.

Networking is intentionally deferred. Until then, use OpenRune primarily to validate semantic expectations and create fixtures/replays.

## CP1 semantic-model evidence

For coordinate/identity/orientation work, inspect these pinned files first:

- `references/rustosrs/crates/osrs-core/src/coords.rs`
- `references/rustosrs/crates/osrs-core/src/ids.rs`
- `references/rustosrs/crates/osrs-core/src/orientation.rs`
- `references/rustosrs/RuneLite-melxin/runelite-api/src/main/java/net/runelite/api/coords/WorldPoint.java`
- `references/rustosrs/RuneLite-melxin/runelite-api/src/main/java/net/runelite/api/coords/LocalPoint.java`

Important evidence used by RustClient CP1:

- map regions are 64×64 tiles,
- fine/local game coordinates use 128 units per tile,
- world location is plane-aware,
- scene-relative coordinates depend on a scene/world-view origin,
- model-local coordinates and game-world coordinates are different spaces,
- runtime entity indexes must not be conflated with definition/resource IDs.

RustClient deliberately does not copy the reference types verbatim. See `SPEC-0012` for the owned contract.

## Search discipline

Before implementing a nontrivial OSRS-style behavior:

1. read the owning RustClient spec and nearest `AGENTS.md`,
2. search RustClient for an existing owner/implementation,
3. inspect only the relevant reference area,
4. write down the semantic behavior being reproduced,
5. implement it in the RustClient owner,
6. add a test/fixture proving the behavior,
7. document deliberate incompatibilities or extensions.

## Revision baseline

RustClient's first implemented compatibility target is revision `240`. Multi-revision selection is part of the architecture from the start, but only supported revisions appear in the runtime registry. See `docs/specs/SPEC-0005-protocol-revisions.md`.
