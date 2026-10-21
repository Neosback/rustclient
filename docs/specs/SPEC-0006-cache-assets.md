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
