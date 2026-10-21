# Reference Sources

This directory contains pinned external source trees used as implementation evidence for RustClient. Reference sources are not part of the RustClient Cargo workspace and are never authoritative for RustClient architecture.

## RustOSRS

`references/rustosrs` is a Git submodule pinned to an exact commit of `Neosback/RustOSRS`. That repository currently gives us three useful bodies of evidence in one snapshot:

- RustOSRS Rust crates and editor code for cache, scene, model, texture, animation, rendering, coordinate and map behavior.
- `RuneLite-melxin/` for client behavior, widgets, client scripts, vars, menus, entities, animations and plugin concepts.
- `OpenRune-Server-main/` for private-server behavior, revision-specific semantics, update masks, cache expectations and later protocol/login work.

The submodule gitlink is the single source of truth for the pinned reference commit.

## Commands

```text
cargo xtask references sync
cargo xtask references status
```

`sync` initializes/updates the submodule to the exact commit recorded by RustClient. `status` compares the checked-out reference commit with the gitlink recorded by the current RustClient commit.

Do not use `git submodule update --remote` during normal implementation work. Updating the reference snapshot must be an explicit reviewed change to the RustClient gitlink.

## Authority

Reference code is evidence. RustClient specs, ADRs, semantic owners and tests are authoritative. Port behavior intentionally, place it in the correct RustClient owner, and add tests that describe the behavior we rely on.
