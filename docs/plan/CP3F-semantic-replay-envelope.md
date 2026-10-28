# CP3F — Semantic Replay Envelope

<!-- authority-notice -->
> Historical checkpoint and evidence record. Implementation status, “next” tasks, counts
> and validation below apply to the recorded snapshot. Current work follows
> [PORTING-ROADMAP.md](PORTING-ROADMAP.md) and the [domain matrix](../reference/rs240-port/PORT-MATRIX.md).

## Goal

Add the smallest executable deterministic replay boundary on top of CP3E commit metadata without introducing a persistent replay format or a second state-mutation implementation.

## Scope

- add `ReplayEnvelope` pairing one `SemanticOperation` with the `OperationCommit` originally recorded for it
- add typed `ReplayFailure` / `ReplayError` reporting the exact zero-based failing envelope index
- replay ordered envelopes through the existing `GameState::apply_operation` gateway
- reject replay immediately when an operation is invalid for the replay state
- compare actual replayed `OperationCommit` metadata with the recorded commit using exact equality
- stop at the first commit mismatch and report expected/actual commit metadata
- provide an allocation-free verified-commit callback path
- keep empty replay sequences valid no-ops
- prove equivalent modeled semantic state reconstructs from a recorded envelope sequence without network/GPU access

## Important failure semantics

Replay does not clone or checkpoint `GameState`.

- rejected replay operations do not mutate state, following normal operation semantics
- commit mismatches are detected only after the operation has committed and produced actual commit metadata
- after a commit mismatch, the replay state is invalid/disposable and must not be resumed
- verified-commit callbacks run only after actual commit metadata exactly matches the recording

This preserves one authoritative mutation implementation and avoids adding replay-only rollback authority.

## Explicitly deferred

- persistent replay file/byte encoding
- schema/version headers
- compression
- protocol packet capture
- source/provenance taxonomy
- profile/revision session identity in replay headers
- deterministic RNG seeds/inputs beyond currently modeled deterministic operations
- replay seeking/checkpoints/snapshots
- renderer/UI/plugin replay integration
- networking/runtime replay orchestration
- additional player/NPC/inventory/widget/scene semantic operations

## Validation

Required CI remains:

- `cargo fmt --all --check`
- `cargo check --workspace --all-targets`
- `cargo clippy --workspace --all-targets -- -D warnings`
- `cargo test --workspace`
- `cargo check -p rustclient-web --target wasm32-unknown-unknown`

## Exit criteria

A recorded valid semantic envelope sequence can be replayed against equivalent initial `GameState`, reconstruct equivalent modeled semantic state, reproduce every recorded `OperationCommit`, and deterministically stop at the exact first rejection or commit mismatch.
