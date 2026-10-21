# CP3A: Authoritative GameState Root

## Goal

Establish the minimum authoritative semantic state root and session lifecycle without introducing operations, events, replay, protocol, rendering, or networking concerns.

## Scope

- `GameState` as the single mutable semantic root.
- Explicit inactive/active session lifecycle.
- Strict begin/end session transitions with stable lifecycle errors.
- Private root fields with immutable lifecycle query.
- `GameState` intentionally does not implement `Clone`.
- Unit tests proving valid and invalid lifecycle transitions.
- State spec/agent rules updated to preserve these ownership invariants.

## Explicit deferrals

- player/NPC/inventory/widget/scene state,
- game-tick advancement,
- semantic operation batches,
- committed events and `StateDelta`,
- replay envelope/capture,
- profile/revision session metadata,
- protocol/network integration,
- renderer/UI projections.

## Architecture audit

Before merge verify:

- `game-state` has no platform, renderer, protocol, cache, UI, or networking dependency,
- the root cannot be cloned wholesale,
- no mutable field reference escapes the root,
- invalid lifecycle transitions do not mutate state,
- no placeholder domain collections are introduced before their mutation contracts exist.

## Validation

Required exact-head gates:

- `cargo fmt --all --check`
- `cargo check --workspace --all-targets`
- `cargo clippy --workspace --all-targets -- -D warnings`
- `cargo test --workspace`
- `cargo check -p rustclient-web --target wasm32-unknown-unknown`

## Exit

Higher runtime work has one explicit semantic authority and one session lifecycle boundary to build on. CP3B may add deterministic transition/tick mechanics, but must not bypass this root.
