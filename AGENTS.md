# RustClient Agent Instructions

## Read first

Before significant work, read `ARCHITECTURE.md`, then the applicable `docs/specs/` files and the nearest nested `AGENTS.md`.

## Non-negotiable invariants

1. `GameState` is authoritative semantic state. Renderer/UI/plugin projections are not truth.
2. Do not create a second representation of the same semantic concept merely because the existing API is inconvenient. Extend or refactor the owner.
3. Revision-specific protocol/cache knowledge stays in revision/profile adapters.
4. Plugins do not receive internal mutable state, raw sockets, filesystem access, renderer/GPU handles, or platform APIs by default.
5. Native and web share semantics. Platform differences are adapters, not forks of domain behavior.
6. Public plugin API types are intentionally versioned and separate from internal structs.
7. Prefer typed IDs/coordinates/units over primitive soup.
8. No hidden global mutable state.
9. New architecture boundaries require a spec or ADR update.
10. Keep commits/checkpoints coherent and validation claims exact.
11. `references/` is pinned evidence, not RustClient source ownership. Never depend on or silently modify reference trees to solve implementation work.

## Implementation discipline

- Search for an owner/implementation before adding a new one.
- Prefer small crates with clear ownership, not arbitrary fragmentation.
- Keep hot-path allocations explicit and measurable.
- Avoid `unsafe`; workspace lint denies it unless an ADR later creates a narrowly scoped audited exception.
- Errors crossing subsystem boundaries must carry context without leaking secrets.
- Tests should exercise semantic contracts, not private implementation details unless necessary.
- When reference code materially informs behavior, port the semantics into the owning RustClient subsystem and add a focused test/fixture; do not mirror foreign architecture mechanically.

## Validation baseline

For Rust changes, target: `cargo fmt --check`, `cargo check --workspace --all-targets`, strict Clippy, relevant tests, and `cargo check -p rustclient-web --target wasm32-unknown-unknown` when web-compatible code changes.

Do not claim full validation if only a subset ran.
