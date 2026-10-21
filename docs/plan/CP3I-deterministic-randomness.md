# CP3I — Deterministic Randomness/Input Contract

## Goal

Close the deterministic-randomness gap identified by CP3H without introducing hidden entropy into semantic transitions or inventing a fake random game operation before a real consumer exists.

## Ownership decision

The deterministic random source lives in `crates/application`.

That ownership is deliberate:

- `model` explicitly owns stable semantic primitives and forbids becoming a random-helper crate,
- `platform` owns capability contracts and target adapters, not target-neutral pseudo-random algorithms,
- `game-state` must receive randomness explicitly rather than hiding generator state or platform entropy inside authoritative transitions,
- `application` is the shared native/web runtime orchestrator and can inject deterministic inputs into future semantic producers without creating target forks.

## Scope

- add explicit `RandomSeed(u64)` and raw `RandomSample(u64)` values,
- add an injectable `DeterministicRandomSource` contract,
- add a dependency-free `SplitMix64Random` reference implementation,
- require all generator state to originate from an explicit seed,
- prohibit wall-clock, OS entropy, browser randomness, or hidden global state reads inside deterministic providers,
- freeze the reference SplitMix64 sequence with canonical regression vectors,
- prove identical seed + identical call order yields identical samples,
- document application ownership and CP3 roadmap status.

## Why raw samples are intentionally distribution-free

`RandomSample` represents exactly one raw deterministic 64-bit input. It does not define ranges, percentages, floating-point conversion, modulo behavior, or game-specific probability semantics.

Those mappings belong to the semantic consumer that knows the domain rule. Centralizing them prematurely in the RNG layer would risk silently choosing biased or revision-specific behavior before a real consumer exists.

## Replay implication

No current `SemanticOperation` consumes randomness, so CP3I does not add RNG state to `GameState`, `ReplayEnvelope`, `OperationCommit`, events, or deltas.

When a future semantic operation genuinely requires randomness, its deterministic random input must cross that semantic boundary explicitly. The preferred replay shape is to record the exact semantic random input/sample that affected the operation, so replay does not depend on rerunning an implementation-specific PRNG sequence.

If a future subsystem instead proves that generator cursor/state is itself semantic, that change requires an explicit state/replay design checkpoint rather than hidden mutable RNG state.

## Explicitly out of scope

- OS/native/browser entropy acquisition,
- automatically selecting a live seed,
- cryptographic randomness,
- protocol/login ciphers or ISAAC,
- game-specific probability/range helpers,
- fake random `SemanticOperation` variants,
- storing RNG state in `GameState`,
- persistent replay serialization,
- canonical state/event fingerprinting.

## Invariants

1. Equivalent seed + equivalent draw order produces an equivalent raw sample sequence.
2. A deterministic provider never reaches out to nondeterministic platform state while generating samples.
3. Seed choice is explicit at the provider boundary.
4. `RandomSample` carries no hidden distribution semantics.
5. `GameState` remains free of hidden RNG state until a real semantic requirement proves otherwise.
6. Future random-dependent semantic behavior receives random input explicitly.
7. The reference provider is allocation-free and uses fixed-width integer arithmetic only.

## Validation

The exact final branch head must pass:

- `cargo fmt --all --check`
- `cargo check --workspace --all-targets`
- `cargo clippy --workspace --all-targets -- -D warnings`
- `cargo test --workspace`
- `cargo check -p rustclient-web --target wasm32-unknown-unknown`

## Exit criteria

CP3I is complete when:

1. the deterministic randomness contract has a documented architectural owner,
2. explicit seed/sample value types and an injectable source contract exist,
3. the reference deterministic provider has exact output-vector tests,
4. no platform entropy or hidden RNG state enters `GameState`,
5. CP3 roadmap documentation records deterministic randomness as complete,
6. the exact checkpoint head passes all standard CI gates.

## Next checkpoint

CP3J should define the canonical semantic state/event fingerprint surface and final CP3 repeatability exit test. It must not start until CP3I is merged and explicitly approved.
