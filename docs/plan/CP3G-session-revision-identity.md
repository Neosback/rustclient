# CP3G — Session Revision Identity

## Goal

Make semantic session compatibility identity explicit and replayable without introducing a duplicate server-profile abstraction or a persistent replay-file format.

## Scope

- make `SemanticOperation::BeginSession` carry an explicit canonical `Revision`
- store the active session revision in authoritative `GameState`
- expose `GameState::session_revision()` as an immutable query
- clear session revision on `EndSession`
- include the exact committed revision in `SemanticEvent::SessionBegan`
- preserve the existing lifecycle `StateDelta` bit as the signal to re-query session revision
- make CP3F replay reconstruct and verify session revision through the existing operation/commit envelope
- document that `Revision` and `ServerProfile` remain separate concepts
- keep supported-revision/profile validation outside `game-state`

## Invariants

1. An inactive `GameState` has no session revision.
2. A successful begin-session transition establishes exactly the supplied `Revision`.
3. A failed begin-session transition leaves the existing session revision unchanged.
4. Ending a session clears the revision before another session can begin.
5. No implicit/global default revision is selected inside `game-state`.
6. `SessionBegan { revision }` reports the exact revision that became authoritative.
7. Replay uses the normal `BeginSession { revision }` operation and exact commit verification; there is no replay-only identity mutation path.
8. `Revision` is compatibility identity only and must not be treated as server-profile identity.

## Why profile identity is deferred

SPEC-0005 defines `ServerProfile` conceptually but the repository does not yet contain a canonical concrete profile identity type. CP3G does not create a duplicate placeholder in `game-state` or replay. A later checkpoint can add canonical profile identity once its owning subsystem exists, then thread that identity through the same session/replay boundary.

## Explicitly deferred

- concrete `ServerProfile` / profile-ID implementation
- supported-revision/profile registry implementation
- persistent replay file/header encoding
- replay schema versions and migration
- compression
- cache/profile fingerprints
- deterministic RNG seeds/inputs
- source/provenance taxonomy
- replay seeking/checkpoints/snapshots
- networking/login selection
- renderer/UI/plugin replay integration
- additional semantic domains

## Validation

Required CI remains:

- `cargo fmt --all --check`
- `cargo check --workspace --all-targets`
- `cargo clippy --workspace --all-targets -- -D warnings`
- `cargo test --workspace`
- `cargo check -p rustclient-web --target wasm32-unknown-unknown`

## Exit criteria

A semantic session cannot begin without an explicit canonical `Revision`; that revision is authoritative only while the session is active, appears in committed session-began metadata, is cleared at session end, and deterministic replay reconstructs the same revision through the existing operation/commit envelope.
