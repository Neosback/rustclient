# Reference Index

This index tells humans and coding agents where to look before reinventing OSRS-style behavior. Reference sources are evidence only; RustClient specs and code remain authoritative.

## Revision-240 source map

Read [the revision-240 integration guide](REVISION-240.md) before protocol, cache,
rendering or plugin work. The client target is OpenRune `240.2`; a nearby source
revision is evidence, not a compatibility declaration.

| Source | Location | Use and limits |
| --- | --- | --- |
| RuneLite / deob 235 | `references/RuneLite-melxin/` | Primary executable client behavior: model transforms, normal merging, offsets, skinning, scene order, camera, widgets, scripts, menus. Plugin ergonomics and feature ideas. `buildSrc/.../Dependencies.kt` explicitly declares 235; recheck layouts changed by 240. |
| RuneLite-injected 240 binary | `/Users/tylercovalt/gamepack/gamepack_240_runelite-injected-1.12.39.jar` | Official RuneLite download; publisher SHA-256 and major revision verified. Additional 240 client-behavior evidence with injected transformations. Not an untouched vanilla input. See [provenance](gamepack-240.json). |
| rsprox | `references/rsprox-master/` | **Both client and server packet tables/codecs**, byte transforms, framing and proxy diagnostics. Supplied OSRS modules reach 235, not 240. `incoming` means client-to-server; `outgoing` means server-to-client. |
| OpenRune development server | `/Users/tylercovalt/Desktop/OpenRune Project/OpenRune-Server` | Actual server behavior and cache for the target. Pins `net.rsprot:osrs-240-*` `1.0.0-ALPHA-20260825`; those source JARs provide exact 240 wire evidence. See existing protocol fixture provenance. |
| OpenRune FileStore | `/Users/tylercovalt/Documents/OpenRune-FileStore-main` | Cache definition/opcode, container and model decoder research. Verify individual 240 layouts against actual cache and deob; presence of a codec does not prove every 240 format is supported. External local source, not yet vendored. |
| RustOSRS | `references/rustosrs/` | Pinned Rust cache/scene/rendering research, coordinate conventions, model offsets, transforms, neighboring normal merges and Reference GPU oracle. Port semantic rules into RustClient; do not adopt editor enhancements as OSRS behavior. |

The separate RuneLite and rsprox copies are vendored Git trees, not submodules.
Their exact versions are the trees in the RustClient commit; `cargo run -p xtask
-- references status` reports and checks all three in-repository sources.
`references sync` initializes only the RustOSRS submodule and then checks all
sources; it never downloads or advances the vendored copies.

Selected source SHA-256 values and local source roots are recorded in
[source-manifest.json](source-manifest.json). This is research provenance, not
runtime configuration. External source entries pin only the named files, not an
entire server/FileStore checkout. Work remains independent of those local paths.

The original supplied copies remain available at:

- RuneLite: `/Users/tylercovalt/Documents/ChatGPT/RSPSi-resources/RuneLite-melxin`
- rsprox: `/Users/tylercovalt/Documents/rsprox-master`
- RustOSRS: `/Users/tylercovalt/Documents/RustOSRS`

Older checkpoints cite RuneLite and OpenRune bundled inside `references/rustosrs/`.
Those citations remain valid for their recorded snapshot. New research should use
the source map above and record the specific files/revision used.

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
