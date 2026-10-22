# SPEC-0012: Semantic Model

**Status:** Draft

## Purpose

Define the dependency-light value model shared by protocol, cache, game state, scene, UI, plugin-facing adapters, tests, and tooling without leaking any one subsystem's representation into the others.

## Scope

This specification owns:

- world/region/scene/fine/model coordinate taxonomy,
- ordinary world planes,
- semantic IDs and runtime entity indexes,
- revision identity,
- deterministic game-tick ordinals,
- integer game-space orientation/angles,
- conversion and range behavior for those values.

It does not own server networking, packet layouts, cache binary formats, renderer transforms, plugin ABI types, or the full `ServerProfile` configuration.

## Reference evidence

The initial implementation was checked against the pinned RustOSRS snapshot at `references/rustosrs/`, especially:

- `crates/osrs-core/src/coords.rs`,
- `crates/osrs-core/src/ids.rs`,
- `crates/osrs-core/src/orientation.rs`,
- RuneLite `runelite-api/.../coords/WorldPoint.java` and related coordinate classes.

Reference code is evidence only. This specification and RustClient tests define RustClient behavior.

## Invariants

1. Coordinate spaces that are not semantically interchangeable use different Rust types.
2. Protocol/cache numeric widths do not determine semantic type widths unless that width is itself part of the stable semantic contract.
3. Runtime entity indexes are not definition IDs.
4. Absence is represented by the owning semantic field with `Option<T>`, not by sentinel IDs such as `-1` or `65535`.
5. Checked conversions fail rather than silently overflowing or wrapping.
6. No universal scene width/height is encoded into `SceneTile`.
7. `Revision` is distinct from `ServerProfile`.
8. Game ticks are ordinal semantic steps; real-time tick duration belongs to runtime/profile policy.
9. Renderer coordinates, matrices, quaternions, and GPU types do not enter this crate.
10. Revision-specific packet/cache behavior stays outside the semantic model.

## Coordinate taxonomy

### Plane

`Plane` represents the ordinary OSRS world planes `0..=3`.

Values outside that range are invalid semantic world planes and must be rejected explicitly.

### WorldTile

`WorldTile` is a plane-aware global tile coordinate:

- signed `x`,
- signed `y`,
- validated `Plane`.

Signed coordinates are retained so arithmetic and synthetic fixtures can represent values around zero without unsigned underflow.

### RegionCoord and RegionTile

A map region is 64×64 tiles.

`RegionCoord` is the signed global region coordinate. `RegionTile` is validated to `0..=63` on each axis.

World-to-region splitting uses Euclidean division/remainder so negative coordinates behave mathematically rather than inheriting language-specific truncation semantics.

Round-trip requirement:

`WorldTile -> (RegionCoord, RegionTile) -> WorldTile`

must reproduce the original tile when intermediate checked arithmetic fits.

### SceneTile

`SceneTile` is relative to a constructed scene origin and includes a plane.

The type does not encode a fixed scene size. The owning scene/profile validates actual dimensions. This avoids hard-coding one historical client scene size into the universal semantic layer.

World/scene conversion is plane-aware and checked.

### Fine coordinates

One tile contains exactly 128 fine/local units.

`FineCoord` represents one signed coordinate in that exact unit system. Tile conversion uses checked integer multiplication. Floor/offset operations use Euclidean semantics.

A plane-aware fine point must document whether it is world-relative or scene-relative. Ambiguous "local point" naming is prohibited in new APIs.

### ModelPoint

`ModelPoint` is asset/model-local three-dimensional integer space before scene placement.

It must not be passed where world/scene/fine coordinates are required without an explicit conversion owned by the subsystem performing placement.

## IDs

Definition/resource IDs use dedicated types, including at minimum:

- `ObjectDefinitionId`,
- `NpcDefinitionId`,
- `ItemDefinitionId`,
- `ModelId`,
- `SpriteId`,
- `TextureId`,
- `SequenceId`,
- `SpotAnimationId`,
- `VarpId`,
- `VarbitId`,
- `WidgetId`,
- `InterfaceId`,
- `ScriptId`,
- `RegionId`.

Runtime identities use different types, initially:

- `PlayerIndex`,
- `NpcIndex`.

The initial wrappers use `u32` to avoid prematurely encoding protocol bit widths into semantic APIs.

## Revision

`Revision` contains:

- a major `u16`,
- an optional `u16` subrevision.

Examples:

- `240`,
- `240.2`.

`Revision::new(240)` and `Revision::with_subrevision(240, 0)` are intentionally distinct values.

The initial compatibility target is `REVISION_240`.

Support policy is not encoded by `Revision` itself. A later registry/profile layer determines which revisions are actually implemented. Unsupported revisions must fail explicitly rather than falling back to revision 240.

## ServerProfile boundary

The full `ServerProfile` does not belong in `crates/model`.

A server profile will combine a `Revision` with protocol/cache/login/extension/runtime configuration in the owning profile/protocol layer. Multiple server profiles may share the same revision.

## Time

`GameTick` is a monotonic semantic ordinal represented independently from wall-clock time.

`TickSpan` is a count of semantic ticks.

Tick arithmetic is checked. This layer does not assume that one tick is exactly 600 ms or any other duration; private servers may intentionally configure different timing.

## Orientation

`LocOrientation` validates ordinary object/location orientation `0..=3`.

`QuarterTurn` models exact integer quarter-turn operations.

`JauAngle` models wrapped Jagex angular units in a 2048-unit turn.

These values are game semantics, not renderer orientation types.

## Error conventions

Primitive semantic constructors may use validated constructors or typed conversion errors.

Subsystem failures do not collapse into one global model error. The owning crate defines contextual errors and classifies them at boundaries as appropriate, for example:

- recoverable/local input error,
- asset-local decode failure,
- connection/profile fatal error,
- programmer invariant violation.

Errors crossing subsystem boundaries must identify the semantic operation/resource involved without leaking secrets.

## Performance constraints

These value types should remain small, copyable where practical, allocation-free, deterministic, and hash/order friendly.

Do not micro-optimize field widths by leaking revision-specific limits into semantic APIs. Measure hot paths before introducing packed representations.

## Acceptance criteria

- coordinate spaces have separate types and checked conversions,
- negative region boundary behavior is tested,
- region round trips are tested,
- scene conversion rejects plane mismatches,
- fine-coordinate floor/offset behavior is tested for negative values,
- overflow behavior is tested,
- runtime entity indexes and definition IDs are separate types,
- revision parse/display behavior is tested,
- game-tick arithmetic is checked,
- integer orientation behavior has deterministic tests,
- the model crate depends on no RustClient subsystem.

## Non-goals

- networking/login,
- revision-240 packet codecs,
- cache decoders,
- scene storage implementation,
- renderer coordinate transforms,
- widget/client-script semantics,
- plugin ABI serialization,
- choosing every future supported revision.

## Bootstrap identity primitives

`ServerProfileId(u32)` is the semantic identity allocated by the canonical protocol
profile registry. It contains no address or wire behavior; `Revision` remains a
separate compatibility identity. `WorldAreaId(u32)` names the server's semantic map
area; its geometry is interpreted by the profile. Neither wrapper infers defaults
or reserves absence sentinels. GameState owns optional session presence. The map-area field is optional: a profile
translates the main-world sentinel to absence; sentinel bytes never become IDs.

## Immutable scene references

`SceneAssetId([u8;32])` is the SHA-256 identity supplied by the immutable asset catalog. `SceneDescriptor` pairs it with checked nonempty world bounds and a selected plane. It supports exclusive containment; it contains no GPU/camera information. `FloorUnderlayId` and `FloorOverlayId` are separate definition identities. Scene selection is optional session-owned `GameState` data; its content resides in the immutable application asset catalog (SPEC-0015).
