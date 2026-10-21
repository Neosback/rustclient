# Game State Scope

This crate owns authoritative live semantic state and validated transitions.

- No socket, filesystem, browser, window, GPU, `egui`, Wasm runtime, or packet opcode dependencies.
- No revision-specific byte encodings.
- Time/randomness enter explicitly.
- Events describe committed facts; commands/operations request changes.
- Avoid mutable references escaping transition boundaries.
- Every new state field needs a clear owner, mutation path, reset/session lifecycle, and replay implication.
- Prefer deterministic containers/order where ordering is observable.
