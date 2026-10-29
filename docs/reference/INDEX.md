# Reference Index

This index maps pinned behavioral evidence to RustClient owners.
[SPEC-0019](../specs/SPEC-0019-port-parity.md) defines the nearly 1:1 contract;
[the roadmap](../plan/PORTING-ROADMAP.md) orders implementation. Reference source
is read-only evidence. A conflict between code and a ratified target remains an
open gap until deliberately reconciled; current code does not redefine parity.

## Current implementation status

[The domain port matrix](rs240-port/PORT-MATRIX.md) and [current correctness audit](rs240-port/CURRENT-AUDIT.md)
separate implemented subsets, missing/incorrect behavior, source evidence and verification limits.
The matrix is generated from checked TSV; it is not a completion percentage.

## Revision-240 source map

Read [the revision-240 integration guide](REVISION-240.md) before protocol, cache,
rendering or plugin work. The client target is OpenRune `240.2`; a nearby source
revision is evidence, not a compatibility declaration.

| Source | Location | Use and limits |
| --- | --- | --- |
| RuneLite / deob 240 (rl-suite) | `references/rl-suite-240/` | Primary executable client behavior at revision 240: deobfuscated client sources with stable `classXX` names plus 93 API-derived names (model transforms, scene order, camera, widgets, scripts, menus, actor movement). Original obfuscated names are in `@ObfuscatedName`. Same input jar as `gamepacks/osrs-240`. |
| Readable 240 client names | `docs/reference/client-240/` | Evidence-backed readable-name overlay (tiny-v2 shards keyed by the stable rl-suite symbols), per-cluster behavior write-ups and a name ledger with tier and confidence, regenerated and verified by `tools/client-240`. Names are only as good as their recorded evidence; see [ADR-0005](../adr/ADR-0005-readable-client-names.md). |
| open-runelite 240 | `references/open-runelite-rev-240/` | RuneLite API and client at 240: GPU plugin and shaders (depth, culling, reverse-Z), `runelite-api` types, plugin ergonomics. The pinned original injected jar is under `references/gamepacks/osrs-240`; `tools/parity/runelite240.py` supplies the local reference launcher/bridge. Not a declaration of OpenRune 240.2 compatibility. |
| RuneLite-injected 240 binary | `references/gamepacks/osrs-240/injected-client-1.12.39.jar` | Official RuneLite download; publisher SHA-256 and major revision verified. Additional 240 client-behavior evidence with injected transformations. Not an untouched vanilla input. See [provenance](gamepack-240.json). |
| rsprox | `references/rsprox-master/` | **Both client and server packet tables/codecs**, byte transforms, framing and proxy diagnostics. The current vendored tree includes OSRS-240 codecs; selected OpenRune RSProt remains exact target authority. `incoming` means client-to-server; `outgoing` means server-to-client. |
| OpenRune development server | `/Users/tylercovalt/Desktop/OpenRune Project/OpenRune-Server` | Actual server behavior and cache for the target. Pins `net.rsprot:osrs-240-*` `1.0.0-ALPHA-20260825`; those source JARs provide exact 240 wire evidence. See existing protocol fixture provenance. |
| OpenRune FileStore | `/Users/tylercovalt/Documents/OpenRune-FileStore-main` | Cache definition/opcode, container and model decoder research. Verify individual 240 layouts against actual cache and deob; presence of a codec does not prove every 240 format is supported. External local source, not yet vendored. |
| TSPS client | `/Users/tylercovalt/Documents/tsps-main/client` | Supporting implementation research; the supplied snapshot targets cache 241 (`2026-10-20`), not verified stock 241 wire compatibility. See the [2026-10-29 review](TSPS-241-REVIEW-2026-10-29.md) and its separate source pins for useful rendering/UI/audit mechanisms and explicit exclusions (decoration corrections, priority depth bias, neutral opcode stubs and mutable engine plugin access). Older source-manifest and TSPS-237 pins retain their historical scope. Verify semantics against exact 240 and wire layouts against the selected RSProt adapter; preserve RustClient owners. |
| 235 → 240 change inventory | [`REVISION-235-TO-240.md`](REVISION-235-TO-240.md) | What carries over from the readable 235 tree and what changed (packets, cache decoders, XTEA, world map, CS2 long type, camera, GPU plugin, sailing), with per-packet and per-commit data. Start here before porting from 235. |
| RustOSRS | `references/rustosrs/` | Pinned Rust cache/scene/rendering research, coordinate conventions, model offsets, transforms, neighboring normal merges and Reference GPU oracle. Port semantic rules into RustClient; do not adopt editor enhancements as OSRS behavior. |

The separate RuneLite 240, rl-suite and rsprox copies are vendored Git trees, not
submodules. Their exact versions are the trees in the RustClient commit; `cargo run
-p xtask -- references status` reports and checks all four in-repository sources.
`references sync` initializes only the RustOSRS submodule and then checks all
sources; it never downloads or advances the vendored copies.

Selected source SHA-256 values and local source roots are recorded in
[source-manifest.json](source-manifest.json). This is research provenance, not
runtime configuration. External source entries pin only the named files, not an
entire server/FileStore checkout. Work remains independent of those local paths.

The [TSPS Git shortlist](TSPS-GIT-SHORTLIST-2026-10-29.md) ranks practical transfers
from pinned client history against current Rust owners and exact-240 behavior.
It separates missing features, existing coverage, conditional optimizations and
rejected shortcuts; its [Git evidence](TSPS-GIT-EVIDENCE-2026-10-29.json) does not
advance the older source pins or declare any behavior implemented.

The original supplied copies remain available at:

- open-runelite 240: `/Users/tylercovalt/Documents/open-runelite-rev-240`
- rl-suite output: `/Users/tylercovalt/Documents/Java Deob/decompiled/rl-suite-240`; tool: `/Users/tylercovalt/Documents/rl-suite-main` (also holds revision 235-240 packet tables under `data/`)
- rsprox: `/Users/tylercovalt/Documents/rsprox-master`
- RustOSRS: `/Users/tylercovalt/Documents/RustOSRS`

Older checkpoints cite RuneLite 235 and OpenRune bundled inside `references/rustosrs/`.
Those citations remain valid for their recorded snapshot. New research should use
the source map above and record the specific files/revision used.

## The 235 tree

The readable (`@Export`-named) client source is revision **235**, not 240. Use it
to navigate concepts, then confirm affected behavior in exact 240 evidence. The
[235→240 inventory](REVISION-235-TO-240.md) is a partial change guide; an unlisted
change never proves equality. New 240 compatibility claims need 240 anchors.

| What | Where |
| --- | --- |
| In this repository | `references/runelite-melxin-235/` (vendored tree; RuneLite 1.12.14 base, OpenOSRS 4.31.2, `rsversion = 235`; local edits and omissions in its `SNAPSHOT-NOTES.md`) |
| Original working copy | `/Users/tylercovalt/Documents/ChatGPT/RSPSi-resources/RuneLite-melxin` (the source of the vendored tree; also holds untracked build output) |
| Older copy | `references/rustosrs/RuneLite-melxin` (inside the RustOSRS submodule; lacks the local edits, has extra resources) |
| What changed up to 240 | [REVISION-235-TO-240.md](REVISION-235-TO-240.md), data in [`rev-235-240/`](rev-235-240/), generators in `tools/rev-diff/` |

The earlier `references/RuneLite-melxin` was removed and remains in Git history; cite the tree above instead.

The [gameframe audit](../plan/GAMEFRAME-240.md) maps 240 canvas sizing, reflow and
operation-callback ordering to the shared widget/runtime owners.

The [2026-10-23 player audit](../plan/PLAYER-UPDATING-240.md) identifies the actual
240 update manager and separates verified movement from remaining actor gaps.

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

The current NPC/object animation correction audit is [NPC-ANIMATION-240.md](../plan/NPC-ANIMATION-240.md); source pins distinguish stock framing from unimplemented actor scheduling/effects.

Scoped implementation results: [TSPS shortlist checkpoint, 2026-10-29](../plan/TSPS-ADOPTION-2026-10-29.md).

IF1 continuations: [CS1 evaluation and source-backed audit fixes](../plan/CS1-240-2026-10-29.md), [authored button dispatch](../plan/IF1-BUTTONS-240-2026-10-29.md).
