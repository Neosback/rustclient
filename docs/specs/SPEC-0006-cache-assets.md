# SPEC-0006: Cache, Assets, and Definitions

**Status:** Draft

## Goal

Decode OSRS-style cache data into revision-neutral semantic assets while preserving enough source identity for debugging and exact rendering behavior.

## Layers

1. storage/archive/index transport,
2. compression/container decoding,
3. revision-specific binary definitions,
4. semantic definitions/assets,
5. runtime asset cache/resources,
6. renderer upload/projection.

The cache crate must not create GPU resources.

## Asset identities

Use typed IDs for model, object, NPC, item, sequence, spot animation, sprite, texture, map/region and other major namespaces. Avoid untyped `i32` IDs in public semantic APIs.

## Source identity

When useful, semantic assets retain revision/profile/source identifiers for diagnostics. Do not confuse source identity with mutable runtime entity identity.

## Caching

Decoded CPU assets and uploaded GPU resources have separate caches and lifecycles. Cache invalidation/eviction is explicit and measurable.

## Custom private-server assets

Profiles may define custom archives/definitions. Extensions are decoded by profile adapters into the same semantic concepts where possible. Truly new concepts require a documented semantic extension rather than hidden flags.

## Validation

Golden fixtures must pin representative assets per supported cache family. Decoder errors include archive/group/file/definition context.

## First implemented slice: title assets

SPEC-0013 defines the bounded read-only JS5 reader and explicit `osrs-240` profile.
The supported binary subset includes sector chains, container compression,
protocol-5–7 reference tables, named lookup, multi-file splitting, indexed sprites,
JPEG backgrounds and simple 257-byte bitmap-font metrics. This describes the original title subset. Later spawn/scenery/entity slices add
map/model/texture/definition/animation decoding under SPEC-0015–0017; it is not a
claim of complete cache coverage. Extended font metrics remain outside the title
contract.
The metadata fingerprint is SHA-256 over a domain prefix and ordered
(index byte, big-endian u64 encoded length, exact reference
container bytes) tuples; it does not claim to hash all dat2 contents.

The LIVE publisher encoded-size policy is defined in
[SPEC-0013](SPEC-0013-offline-title.md#live-encoded-size-metadata-policy).

## Revision-240 decoder research

Use OpenRune FileStore opcode/definition codecs alongside the deob and actual
server cache; [the reference map](../reference/INDEX.md) records their identities.
Do not assume a decoder collection proves every 240 format. Record unsupported
required formats explicitly. Cached-model animation decoding rejects truncated
curves, invalid targets/channels, duplicate or unordered times, non-finite inputs
and cyclic/out-of-range bone parents. Hierarchies are bounded to 1024 bones and
curve expansion to 8,388,608 float samples per animation; exceeding those budgets
is a contextual asset error, never silent partial decoding. Failed preparation
remains a reported advisory and does not satisfy a parity gate.

## Sparse normal-map archives (revision 240, 2026-10-27)

Owners: the revision cache adapter classifies reference-table absence and performs the source map
initialization; scene consumes immutable availability, height and base-shadow metadata. A group
absent from index 5's reference table is an unavailable map square, not a corrupt required payload.
It has no decoded floors or locations, retains blocked terrain traversal, and uses the exact 240
empty-area initialization. A listed group's absent payload, missing required terrain/location file,
invalid container/CRC/version or decode error remains an error. No catch-all fallback is permitted.

The injected 240 loader `rl4.method10135` tests group presence before treating null data as pending.
Its `method10138` initializes missing ground areas for scene-center chunk Y below 800: inclusive
region edges clipped to the core window, ground base shadow 127, west/east/south/north height-copy
order. Upper planes keep their initialized zero heights. This is source-defined empty space, not a
procedural substitute map. Instance templates and expanded-ring assembly remain outside this slice.

Availability and source-defined base shadows are catalog data; their changes affect asset identity,
not live entity authority. Validate bounds, missing-versus-listed error separation, edge/corner copy
order, high-Y behavior and collision treatment with focused fixtures. Re-run selected-cache region
captures separately; passing codec tests does not establish Java/runtime visual acceptance.
