# Reference Sources

This directory contains pinned external source trees used as implementation evidence for RustClient. Reference sources are not part of the RustClient Cargo workspace and are never authoritative for RustClient architecture.

## RustOSRS

`references/rustosrs` is a Git submodule pinned to an exact commit of `Neosback/RustOSRS`. That repository currently gives us three useful bodies of evidence in one snapshot:

- RustOSRS Rust crates and editor code for cache, scene, model, texture, animation, rendering, coordinate and map behavior.
- `RuneLite-melxin/` for client behavior, widgets, client scripts, vars, menus, entities, animations and plugin concepts.
- `OpenRune-Server-main/` for private-server behavior, revision-specific semantics, update masks, cache expectations and later protocol/login work.

The submodule gitlink is the single source of truth for the pinned reference commit.

## Direct RuneLite 240 and rsprox snapshots

`references/open-runelite-rev-240`, `references/rl-suite-240` and
`references/rsprox-master` are vendored trees recorded by the parent RustClient
commit, not submodules.

- `open-runelite-rev-240` is a RuneLite fork targeting revision 240 (`runelite-api`,
  `runelite-client` including the GPU plugin and shaders, `cache`, and a
  `runelite-agent`). The supplied checkout was not a Git repository, so no upstream
  commit is recorded; the vendored tree is the pin. It expects an external
  `injected-client` jar and its login RSA modulus is Jagex's, not OpenRune's.
- `rl-suite-240` is the rl-suite 1.3.1 deobfuscation of the tracked gamepack
  (input SHA-256 `25f42961...4f85`, identical to `gamepacks/osrs-240`), keeping only
  the semantic-name Vineflower sources, the semantic map and the audit reports.
  Class names are stable `classXX` names, not the old 235 names; every original
  name is kept in `@ObfuscatedName`.
- `rsprox-master` carries client and server packet information through OSRS 235.
  Cross-check revision changes against OpenRune's selected rsprot 240 sources.

The former RuneLite 235 tree (`references/RuneLite-melxin`) was removed; it remains
in Git history. A copy also remains inside the pinned `references/rustosrs`
submodule.

## Revision-240 client binary

`references/gamepacks/osrs-240/injected-client-1.12.39.jar` is a verified
revision-240 RuneLite-injected client, tracked directly in Git. Its bytes match
RuneLite's historical publisher manifest. Source URL, SHA-256, revision evidence
and compatibility limits are recorded in
[`docs/reference/gamepack-240.json`](../docs/reference/gamepack-240.json).
The adjacent `revision-verification.txt` records the static bytecode evidence.
Use it as read-only research input; no RustClient runtime depends on it.

## Commands

```text
cargo run -p xtask -- references sync
cargo run -p xtask -- references status
```

`sync` initializes/updates the submodule to the exact commit recorded by RustClient. `status` compares the RustOSRS checkout with its gitlink and verifies that the two vendored trees match the current RustClient commit.

Do not use `git submodule update --remote` during normal implementation work. Updating the reference snapshot must be an explicit reviewed change to the RustClient gitlink.

## Authority

Reference code is evidence. RustClient specs, ADRs, semantic owners and tests are authoritative. Port behavior intentionally, place it in the correct RustClient owner, and add tests that describe the behavior we rely on.
