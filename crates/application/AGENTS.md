# Application Runtime Scope

`crates/application` owns target-neutral runtime orchestration shared by native and web composition roots.

- It may coordinate inward-facing domain crates, but it must not become a second authoritative semantic state store.
- Platform-specific handles/APIs stay in adapters and composition roots.
- Deterministic runtime services whose lifecycle spans semantic producers may live here when they are not themselves domain state.
- `RandomSeed`, `RandomSample`, `DeterministicRandomSource`, and the reference `SplitMix64Random` provider are owned here.
- Deterministic random providers require explicit seed/state input and must never read OS/browser entropy, wall time, or hidden global state while producing samples.
- Native/browser entropy acquisition, if later needed to choose a live seed, stays outside deterministic providers and is passed in explicitly.
- Future semantic consumers must receive deterministic random input explicitly; do not let lower-layer transitions reach back into application or platform randomness.
- Do not create duplicate RNG abstractions in other crates merely because a consumer needs a different distribution. The consumer owns sample-to-domain mapping; this crate owns deterministic raw sample production.
