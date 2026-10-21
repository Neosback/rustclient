# RustClient

RustClient is a greenfield OSRS-style client platform for private servers. The goal is one maintainable source tree for native desktop and browser targets, with revision-aware protocol/cache support and a sandboxed, versioned plugin ecosystem.

## Status

Foundation/specification phase. No production protocol, renderer, or plugin ABI is considered stable yet.

The first implementation compatibility target is revision `240`, while the architecture is designed to support additional validated revisions/profiles later.

## Architectural goals

- Rust owns the client core and domain model.
- Native and web targets share domain, cache, scene, UI, renderer, and plugin-facing semantics.
- `GameState` is authoritative; render state and plugin views are projections.
- Server/revision differences live behind explicit profiles/codecs, not scattered conditionals.
- Plugins depend on a versioned public API, never internal crate types.
- WebAssembly Component Model/WIT is the intended cross-platform plugin ABI.
- Architecture is enforced by crate ownership, tests, and eventually `xtask` dependency checks.
- Specs and ADRs precede large implementation changes.

## Reference sources

Pinned implementation evidence lives under `references/`. Initialize it with:

```text
cargo xtask references sync
```

Verify the exact pinned checkout with:

```text
cargo xtask references status
```

The reference tree is not part of the RustClient Cargo workspace and is not architectural source of truth.

Start with [`ARCHITECTURE.md`](ARCHITECTURE.md), [`docs/specs/README.md`](docs/specs/README.md), and [`docs/reference/INDEX.md`](docs/reference/INDEX.md).
