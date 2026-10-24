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
- revision-neutral sequence scheduling policy shared between definition adapters and live actor schedulers,
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
11. Sequence definition adapters resolve revision-specific defaults before constructing `SequencePolicy`; live actor scheduling does not interpret cache opcodes or unresolved sentinels.

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

### MapAnchor

`MapAnchor` is a plane-independent centre of the selected map window in signed
world tile coordinates. It carries no region size, wire width or scene extent.
`on_plane(Plane)` explicitly projects it to a `WorldTile`; the session's confirmed
player plane supplies that input. This avoids manufacturing a plane in rebuild
packets that carry no plane. SPEC-0018 owns its map-selection lifecycle.

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

## Sequence scheduling policy

`SequencePolicy` is the revision-neutral set of definition-derived inputs needed by the live actor action scheduler. It contains:

- `restart_mode`, controlling what a same-sequence assignment does to existing playback state,
- `replacement_priority`, used when deciding whether a different action sequence may replace the active one,
- `movement_gate_with_backlog`, used while movement queued before the action assignment remains outstanding,
- `movement_gate_without_backlog`, used after that assignment-time movement backlog has been exhausted,
- `max_loops`, carrying the finite loop limit supplied by the definition.

The model deliberately does not define cache opcode numbers or unresolved cache defaults. A revision-specific definition adapter must resolve those details before projecting a `SequencePolicy`.

The numeric restart and movement-gate modes remain compact semantic values until cross-revision evidence justifies stable named enums. Consumers must not reinterpret those values as revision-240 cache opcodes.

`SequencePolicy` does not own a `SequenceId`, frame data, playback counters, or actor state. Those belong to the definition/playback/runtime objects that use the policy.

### Action sequence assignment

`ActorSequenceSlot` retains the policy for its active action sequence together with playback state, the iteration counter, and the count of movement entries that predated the accepted assignment.

The assignment primitive consumes an already-resolved `(SequenceId, SequencePolicy)` and follows these revision-neutral scheduling rules established from the revision-240 evidence:

1. Clearing the action removes the sequence, policy, playback counters, delay, and assignment-time movement backlog.
2. Reassigning the same active sequence consults the active sequence's restart policy and returns before replacement-priority arbitration.
3. Restart mode `1` resets frame/cycle/iteration playback state and reapplies the newly supplied delay, but does not recapture movement backlog.
4. Restart mode `2` resets only the iteration counter. Frame, cycle, delay, and the captured movement backlog remain unchanged.
5. Other restart modes leave playback unchanged.
6. A different sequence is rejected when its replacement priority is lower than the active sequence's priority.
7. An accepted different sequence fully resets playback, stores its resolved policy and delay, and captures the current movement backlog for later movement-gate decisions.
8. If a legacy active slot has no retained policy, a different-id assignment must report `MissingCurrentPolicy` without mutation rather than guessing the priority result.

`SequenceAssignmentOutcome` makes each result explicit so orchestration and tests can distinguish replacement, same-id restart, priority rejection, and unresolved legacy state.

The transitional unpolicy `set_sequence` path is not the target runtime scheduler. It exists only while packet application is being connected to a policy resolver and deliberately clears any retained policy on a changed sequence so stale metadata cannot be used for later arbitration.

Policy resolution must occur before authoritative action scheduling. Rendering-oriented asynchronous animation loading is not sufficient for this decision because replacement priority and restart semantics are observable at packet-application time.

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
- `SequencePolicy` contains only resolved semantic scheduling inputs and no cache/parser types,
- revision adapters can project decoded definition metadata into `SequencePolicy` without adding a dependency from `crates/model` back to cache,
- action assignment tests cover same-id restart modes, replacement priority, assignment-time backlog capture, clear behavior, and missing active policy,
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

## Playable desktop entity updates

Player movement includes typed step/run changes and teleport deltas with an
explicit plane change. `SequenceChange` distinguishes an omitted update from an
assigned sequence and an explicit clear; profile sentinel values never become
semantic sequence IDs. Wire lists, skip counts and mask layouts stay in protocol.

NPC batches include a typed map-anchor TileOffset origin, ordered properties,
action visibility, movement and sequence delay. Runtime NpcInstanceId identifies
a spawned lifetime independently of a reusable NpcIndex. WorldTarget captures
that lifetime plus the base and resolved definition; location targets capture
the typed LocationKey and definitions. WorldAction adds a SceneDescriptor and a
checked ActionSlot in 0..5. ActionMask governs those five independent slots.
These values carry no wire opcodes or renderer handles. ActorSequenceSlot consumes
delay before advancing its cycle with explicit wrapping playback arithmetic.

InventoryId and InventorySlot distinguish container identity from slot position
and ItemDefinitionId. ItemStack preserves a quantity of zero (a placeholder)
separately from absence. InventoryUpdate expresses replacement, ordered patches
and removal; limits and session ownership belong to game-state, encodings to the
profile. These internal semantic types do not extend the versioned plugin API.


### Input and visible-player lifetime continuation

`PlayerInstanceId` identifies a visible incarnation independently of reusable
`PlayerIndex`. `PlayerTrackingGrid` validates positive dimensions and a checked
world extent; the selected protocol profile supplies those dimensions.
`RemotePlayerUpdate` expresses outside-view tracking/region shifts, promotion,
demotion, movement and attributes in order. Appearance has one owner, transferring
between visible and outside records. Grid/offset/facing validation and lifetime
serial allocation are atomic across a player batch. Packet bits and sentinel
coordinates do not enter these types.

`WidgetDrag` is a committed override for optional parent address, dead zone,
client-cycle threshold and scrollbar behavior. `WidgetKey` names semantic keys;
revision script codes remain in the cache profile. A held pointer's original grab
offset and temporary drag origin are presentation metadata, not another widget
position owner. Action animation delay is independent of movement animation time.

### Traversal and forced movement

MoveSpeed expresses path behavior: Crawl, Walk, Run, or immediate placement
(Stationary). Protocol enum IDs and reset sentinels remain in the profile.
PlayerInfoUpdate distinguishes a persistent path-speed change from a one-entry
speed override. Remote movement carries its effective override with the movement;
attributes change the persistent default separately. This avoids observable
intermediate locomotion changes while applying a single player batch.

WrappedTeleport carries offsets, plane delta and a positive checked grid extent.
The profile supplies its wrapping dimensions; GameState performs checked Euclidean
wrapping. It is distinct from a signed relative teleport that does not wrap.
ExactMove requests signed tile offsets from the newly updated network coordinate,
client-cycle start/end delays and a direction. GameState validates and normalizes
it to an immutable ExactMovement with absolute world tiles and client cycles.
Invalid intervals, directions, coordinate or cycle overflow reject the entire
player batch. Neither the packet decoder nor a draw projection advances semantic
coordinates or stores a second persistent traversal default.

## NPC and location animation continuation (2026-10-23)

NpcMovement preserves one adjacent TileOffset and an optional second adjacent
step in source order, with typed MoveSpeed. Crawl/Walk carry one step; Run
carries two. Collapsing two directions into an endpoint loses bent and closed
paths. NpcProperty::ExactMove uses the shared relative trajectory request.
LocationAnimationPlacement preserves shape/orientation independently of the
cached object identity. AnimationStart optionally carries that placement;
sequence absence explicitly stops animation. Shape 22 is FloorDecoration;
shapes 9 through 21 are GameObject. Wire sentinels/coordinate layouts remain
inside the revision adapter.