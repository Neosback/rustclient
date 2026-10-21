# SPEC-0011: Plugin Authoring Experience

**Status:** Draft

## Goal

Make plugin development simple enough for small private-server features while preserving the same safe cross-platform architecture used by large plugins.

## Developer workflow target

```text
rustclient plugin new my-plugin
rustclient dev --plugin ./my-plugin
rustclient test-plugin ./my-plugin --replay fixtures/session.rcreplay
```

The CLI may ultimately be implemented through `xtask` first and split into a distributable tool later.

## Rust SDK

Rust is the first-class authoring language. The SDK should hide generated component bindings behind ergonomic traits/macros/helpers without hiding capability boundaries.

Conceptual API:

```rust
#[rustclient::plugin]
struct XpTracker;

impl Plugin for XpTracker {
    fn on_event(&mut self, ctx: &mut Context<'_>, event: Event) {
        // Query approved game views and submit overlay/UI commands.
    }
}
```

This syntax is illustrative until the WIT contract and generated bindings are proven during CP11.

## Lightweight scripting

A future embedded scripting layer such as Rhai may be added for local scripts and rapid prototyping. It must consume the same capability/service model rather than bypassing the plugin host. Luau may also be evaluated, but neither scripting language is the external ABI.

## API design priorities

1. discoverable semantic types and services,
2. minimal boilerplate,
3. strong typed IDs/coordinates,
4. event subscription filtering,
5. bulk queries and command buffers,
6. configuration schema generation,
7. stable diagnostics and version errors,
8. replay-based tests without a live server.

## Documentation requirements

Every public plugin service should include examples, capability requirements, cost/performance notes where relevant, ordering/lifecycle guarantees, and target support.

## Acceptance criteria

The first SDK vertical slice is not complete until a new developer can create, build, load, test, and debug a sample plugin without editing client internals or reading generated WIT glue.
