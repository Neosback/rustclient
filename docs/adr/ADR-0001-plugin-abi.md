# ADR-0001: WebAssembly Component/WIT as External Plugin Boundary

**Status:** Accepted for architecture direction

## Context

Rust native dynamic libraries are fast but expose unstable Rust/compiler/platform ABI coupling and do not provide one browser-compatible extension format. A scripting language alone would lock the ecosystem to one language/runtime.

## Decision

Use versioned WIT interfaces/WebAssembly Components as the intended external plugin contract. Provide a first-class Rust SDK, while keeping the ABI language-neutral. Native and web use different execution adapters behind one host semantic contract.

## Consequences

- Public types must be ABI-friendly and intentionally versioned.
- Cross-boundary calls should be batched.
- Plugins are sandbox/capability oriented.
- Native internal features may remain ordinary Rust and need not cross Wasm boundaries.
- Runtime/toolchain maturity must be validated during CP11 before declaring ABI v1 stable.

## Rejected as universal boundary

- Rust `cdylib`: platform/compiler ABI and browser mismatch.
- Luau only: good scripting ergonomics but language/runtime becomes ecosystem ABI.
- direct internal Rust trait API: excellent in-tree ergonomics, poor external compatibility/isolation.
