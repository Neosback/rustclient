# CP3B: Deterministic Tick Transition

## Goal

Add the first session-owned deterministic time field and one minimal validated mutation path to the authoritative `GameState`.

## Scope

- depend on `rustclient-model` for `GameTick` and `TickSpan`
- store the current semantic tick privately in `GameState`
- expose the tick only while a session is active
- reset session tick state on begin/end lifecycle boundaries
- add checked `advance_tick(TickSpan)` mutation
- reject advancement while inactive
- reject overflow without mutation
- add focused unit tests
- update game-state invariants and SPEC-0002

## Explicitly out of scope

- generic semantic operation enums
- operation batching or ordering
- committed event types
- `StateDelta`
- replay capture/envelopes
- wall-clock scheduling
- protocol/networking
- player/NPC/inventory/widget/scene domain state
- renderer/UI integration

## Invariants

1. Semantic game time is not wall time.
2. Only an active session has an observable current tick.
3. A new session always begins at tick zero.
4. Failed tick transitions leave state unchanged.
5. Tick arithmetic never wraps.
6. No mutable tick reference escapes `GameState`.

## Validation

The exact final branch head must pass:

- `cargo fmt --all --check`
- `cargo check --workspace --all-targets`
- `cargo clippy --workspace --all-targets -- -D warnings`
- `cargo test --workspace`
- `cargo check -p rustclient-web --target wasm32-unknown-unknown`

## Next checkpoint

CP3C should introduce the smallest typed semantic operation application boundary. It must not start until CP3B is merged and explicitly approved to continue.
