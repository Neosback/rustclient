# CP3J — Canonical Semantic Fingerprints and CP3 Exit

## Goal

Close the final CP3 gap identified by CP3H/CP3I by defining a canonical deterministic fingerprint surface for authoritative semantic state and committed semantic outputs, then prove the CP3 repeatability exit criterion.

## Scope

- add a versioned canonical semantic fingerprint schema,
- add `StateFingerprint` for observable authoritative `GameState`,
- add `CommitStreamFingerprint` plus an incremental `CommitStreamFingerprintBuilder`,
- use an explicitly specified deterministic `FNV-1a-64` implementation rather than Rust's randomized hashers,
- encode integers little-endian with explicit presence/variant/order tags,
- include lifecycle, active revision identity, and observable game tick in the state fingerprint,
- include committed `SemanticEvent` plus `StateDelta` in exact commit order,
- freeze schema-v1 outputs with exact golden vectors,
- prove identical semantic runs produce identical final state and commit-stream fingerprints,
- prove semantic replay reconstructs the same state and commit-stream fingerprints,
- mark CP3 complete only if the exact checkpoint head passes the full standard CI gate.

## Ownership

Fingerprint code belongs in `crates/game-state` because that crate owns both the authoritative modeled semantic state and the committed event/delta representation being fingerprinted.

The fingerprint surface is read-only derived metadata. It must never become:

- a second state store,
- a mutation authority,
- a persistent replay format,
- a cryptographic integrity/security primitive,
- a replacement for exact semantic equality in APIs that already have the values available.

## Canonical schema v1

`SEMANTIC_FINGERPRINT_VERSION` is `1`.

The hash algorithm is fixed `FNV-1a-64` with the standard offset basis and prime. The canonical byte stream uses:

- a distinct ASCII domain prefix for state vs committed-output streams,
- the fingerprint schema version as little-endian `u16`,
- explicit one-byte enum/option/item/end tags,
- little-endian fixed-width semantic integers,
- exact authoritative/commit order only.

Any future change that alters canonical output must bump `SEMANTIC_FINGERPRINT_VERSION` and update specs and golden vectors together.

### State fingerprint v1

The state fingerprint contains, in order:

1. session lifecycle tag,
2. optional active `Revision` including explicit subrevision presence,
3. optional observable `GameTick`.

It intentionally uses `GameState`'s immutable semantic queries rather than internal memory layout.

### Commit-stream fingerprint v1

Each successful commit is appended in authoritative commit order and contains:

1. explicit commit-item marker,
2. optional `SemanticEvent`,
3. explicit event variant and payload when present,
4. lifecycle-changed delta bit,
5. game-tick-changed delta bit.

The finite stream ends with an explicit terminal marker.

Operations themselves are not hashed into this output fingerprint. `ReplayEnvelope` already owns operation/commit pairing; this surface answers whether committed outputs were identical.

## Deterministic randomness relationship

CP3I established the deterministic random-input source contract. No currently modeled `SemanticOperation` consumes randomness, so CP3J does not fabricate a random state field or fake random operation solely for the exit test.

When a real semantic domain later consumes deterministic random input, that input affects normal semantic operations/state/events and therefore the canonical output fingerprints through those real semantic effects. Any new observable state/event field must update the fingerprint schema contract explicitly.

## Exact regression evidence

Schema-v1 tests freeze at least:

- inactive initial state fingerprint,
- active revision-240 tick-5 state fingerprint,
- a revision-with-subrevision fingerprint,
- empty committed-output stream fingerprint,
- a representative committed-output stream containing begin-session, tick advance, semantic no-op, and end-session commits.

These vectors make accidental algorithm/tag/order changes fail loudly.

## CP3 final repeatability test

The exit test proves:

```text
same initial semantic state
+ same ordered deterministic semantic operation inputs
= same canonical final state fingerprint
+ same canonical committed-output stream fingerprint
```

A second test records operation/commit replay envelopes, replays them through the normal authoritative gateway, and proves the replayed state/commit-stream fingerprints exactly equal the recording run.

## Explicitly out of scope

- cryptographic hashes or signatures,
- persistent replay serialization,
- cache/profile/content fingerprints,
- protocol/network byte fingerprints,
- renderer/UI/plugin state,
- OS/browser entropy acquisition,
- richer game domains that do not yet exist,
- projection rebuild-vs-incremental tests until projections exist.

## Validation

The exact final branch head must pass:

- `cargo fmt --all --check`
- `cargo check --workspace --all-targets`
- `cargo clippy --workspace --all-targets -- -D warnings`
- `cargo test --workspace`
- `cargo check -p rustclient-web --target wasm32-unknown-unknown`

## Exit criteria

CP3J is complete when:

1. state and committed-output fingerprint types are public read-only derived metadata,
2. schema v1 is explicit, versioned, architecture-independent, and independent of randomized Rust hashers,
3. exact golden vectors protect the canonical representation,
4. identical semantic operation sequences produce identical final state and commit-stream fingerprints,
5. replay reconstructs the same fingerprints,
6. SPEC-0002 documents fingerprint semantics and future-extension obligations,
7. SPEC-0010 marks CP3 complete and does not pull CP4 work backward,
8. the exact checkpoint head passes all standard CI gates.

## Next checkpoint

After CP3J is merged and explicitly approved, the roadmap may proceed to CP4 binary/protocol primitives. CP4 must not start as part of this checkpoint.
