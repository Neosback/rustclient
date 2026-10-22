# Reference Sources

This directory contains pinned external source trees used as implementation evidence for RustClient. Reference sources are not part of the RustClient Cargo workspace and are never authoritative for RustClient architecture.

## RustOSRS

`references/rustosrs` is a Git submodule pinned to an exact commit of `Neosback/RustOSRS`. That repository currently gives us three useful bodies of evidence in one snapshot:

- RustOSRS Rust crates and editor code for cache, scene, model, texture, animation, rendering, coordinate and map behavior.
- `RuneLite-melxin/` for client behavior, widgets, client scripts, vars, menus, entities, animations and plugin concepts.
- `OpenRune-Server-main/` for private-server behavior, revision-specific semantics, update masks, cache expectations and later protocol/login work.

The submodule gitlink is the single source of truth for the pinned reference commit.

## Direct RuneLite and rsprox snapshots

`references/RuneLite-melxin` and `references/rsprox-master` are vendored trees
recorded by the parent RustClient commit, not submodules. RuneLite declares OSRS
235; the supplied rsprox OSRS modules reach 235 and include **both client and
server** packet information. Cross-check revision changes against OpenRune's
selected rsprot 240 sources. Neither copy is a declaration of 240 compatibility.

OpenRune FileStore and the actual development server are additional local,
read-only evidence. Their locations, purposes and selected file hashes are in
[`docs/reference/INDEX.md`](../docs/reference/INDEX.md) and its source manifest.

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
