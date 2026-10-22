# Independent reference evidence

Production Cargo crates never link these tools or reference modules.
`oracle.py --cache PATH` verifies the pinned commit/source hashes, copies the
reference workspace and cache to a scratch directory, executes the original
classic GPU renderer, and records every input blob and output hash. Reference
cache sidecars are written only in the scratch directory. The added texture
oracle runs against upstream decoding and emits raw RGBA for exact comparison.

`camera_oracle.py DEOB_PATH` extracts and executes the literal pinned Java
camera input/pose blocks in an isolated harness. The committed trace and source
hashes live under `crates/renderer/tests/fixtures/camera`; tests need no Java or
reference checkout. The trace does not yet establish wheel-script or extreme
aspect-ratio letterbox parity.

`fingerprint_v5.py` independently calculates the documented canonical byte
encoding and FNV regression vectors. This is a diagnostic, not a security hash.

The parity specification is SPEC-0016. A successful capture is evidence of oracle
execution, not proof that RustClient matches it. Required differences remain
failures until resolved and measured.
