# ADR-0004: Verification Policy - Few Semantic Tests, One Render Parity Gate

**Status:** Accepted

## Decision

Tests prove semantic contracts, not rendering appearance. The suite stays small.

Allowed in CI and `cargo test --workspace`:

- bounded-reader, framing and codec goldens, one per packet or definition family rather than per opcode,
- `GameState` operation, replay and fingerprint contract tests,
- small cache fixture decodes with recorded provenance,
- the baseline gates: `cargo fmt --check`, `cargo check --workspace --all-targets`, strict Clippy, and the wasm target check.

Not added:

- per-feature, per-model or per-object render or pixel tests,
- tests that restate an implementation detail of a renderer or scene builder.

Rendering correctness is established once, by a manual `cargo xtask parity` run against the deobfuscated-client oracle in `tools/parity`, when the planned renderer feature set is complete (SPEC-0016 acceptance list). The run records reference, actual and difference artifacts plus individual PASS/FAIL/NOT-RUN gates in a results document. It is not part of CI. It is re-run only after a deliberate renderer change, never to guard routine work.

## Consequences

- Existing renderer and scene tests that are really parity checks are folded into the gate instead of extended.
- A passing unit suite never counts as a rendering-parity claim (SPEC-0016 already requires this).
- Validation claims in checkpoint documents list exactly which commands ran.
