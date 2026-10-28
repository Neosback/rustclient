# CP1: Semantic Model

<!-- authority-notice -->
> Historical checkpoint and evidence record. Implementation status, “next” tasks, counts
> and validation below apply to the recorded snapshot. Current work follows
> [PORTING-ROADMAP.md](PORTING-ROADMAP.md) and the [domain matrix](../reference/rs240-port/PORT-MATRIX.md).

**Branch:** `feat/cp1-semantic-model`

## Goal

Create the dependency-light semantic value layer that every later subsystem can share without importing protocol, cache, renderer, platform, or plugin representations.

## Inputs

Normative design:

- `ARCHITECTURE.md`
- `docs/specs/SPEC-0001-system-architecture.md`
- `docs/specs/SPEC-0005-protocol-revisions.md`
- `docs/specs/SPEC-0012-semantic-model.md`
- `crates/model/AGENTS.md`

Pinned evidence:

- `references/rustosrs/crates/osrs-core/src/coords.rs`
- `references/rustosrs/crates/osrs-core/src/ids.rs`
- `references/rustosrs/crates/osrs-core/src/orientation.rs`
- `references/rustosrs/RuneLite-melxin/runelite-api/src/main/java/net/runelite/api/coords/`

## CP1.1 Coordinate taxonomy

Implement:

- `Plane`
- `WorldTile`
- `RegionCoord`
- `RegionTile`
- `SceneTile`
- `FineCoord`
- `WorldFinePoint`
- `ModelPoint`

Rules:

- 64×64 region semantics are explicit.
- 128 fine units per tile are explicit.
- world/scene conversion is plane-aware.
- scene size is not globally hard-coded.
- negative region math uses Euclidean division/remainder.
- overflow is checked.
- scene-local fine coordinates are not called `WorldFinePoint`; a future type must name that space explicitly.

Tests:

- valid/invalid plane range,
- region boundary round trip including negative coordinates,
- invalid region-local tile rejection,
- scene/world round trip,
- plane mismatch rejection,
- negative fine-coordinate floor/offset,
- coordinate overflow rejection.

## CP1.2 Typed identity

Implement definition/resource IDs separately from runtime indexes.

Definitions/resources:

- object
- NPC
- item
- model
- sprite
- texture
- sequence
- spot animation
- varp
- varbit
- widget
- interface
- client script
- region

Runtime:

- player index
- NPC index

Rules:

- initial primitive is `u32`,
- no packet-width assumptions,
- no sentinel values,
- absence remains `Option<T>` in owning semantic state.

## CP1.3 Revision identity

Implement `Revision` as:

- major `u16`,
- optional subrevision `u16`.

Support textual forms such as `240` and `240.2`.

Define `REVISION_240` as the initial target identity.

Do not implement full `ServerProfile` here. Profile ownership stays in the protocol/profile layer.

## CP1.4 Time

Implement:

- `GameTick`
- `TickSpan`

Rules:

- deterministic ordinal only,
- checked arithmetic,
- no hard-coded 600 ms duration.

## CP1.5 Orientation

Implement:

- `LocOrientation`
- `QuarterTurn`
- `JauAngle`

Keep all operations integer/exact. Renderer transforms remain outside the model crate.

## CP1.6 Error conventions

The model uses validated constructors/typed conversion errors for primitive range boundaries.

Do not create a universal catch-all application error. Each subsystem later owns contextual errors and classifies them at its boundary.

## Exit criteria

CP1 closes only when:

- `crates/model` has no RustClient subsystem dependencies,
- all semantic model tests pass,
- workspace formatting/check/strict Clippy/tests pass,
- WASM target check passes,
- `SPEC-0012` matches implementation,
- no protocol widths, renderer types, scene-size constants, or server-profile configuration leaked into `model`,
- exact final SHA and validation evidence are recorded in the PR/checkpoint notes.
