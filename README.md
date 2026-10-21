# RustClient

RustClient is a greenfield OSRS-style client platform for private servers. The goal is one maintainable source tree for native desktop and browser targets, with revision-aware protocol/cache support and a sandboxed, versioned plugin ecosystem.

## Status

Foundation/specification phase. No production protocol, renderer, or plugin ABI is considered stable yet.

## Architectural goals

- Rust owns the client core and domain model.
- Native and web targets share domain, cache, scene, UI, renderer, and plugin-facing semantics.
- `GameState` is authoritative; render state and plugin views are projections.
- Server/revision differences live behind explicit profiles/codecs, not scattered conditionals.
- Plugins depend on a versioned public API, never internal crate types.
- WebAssembly Component Model/WIT is the intended cross-platform plugin ABI.
- Architecture is enforced by crate ownership, tests, and eventually `xtask` dependency checks.
- Specs and ADRs precede large implementation changes.

Start with [`ARCHITECTURE.md`](ARCHITECTURE.md) and [`docs/specs/README.md`](docs/specs/README.md).
