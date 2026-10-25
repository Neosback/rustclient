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

## Live RuneLite 240 reference client

`runelite240.py` builds an isolated Open RuneLite shell (copy of `references/open-runelite-rev-240`, never
modified in place) around the pinned original `injected-client-1.12.39.jar` and launches it against a local server.
Requires JDK 11 and Gradle; output goes under `target/runelite-240-runtime/`.

```sh
python3 tools/parity/runelite240.py \
  --java-home /opt/homebrew/opt/openjdk@11/libexec/openjdk.jdk/Contents/Home \
  --public-key "<server>/.data/client.key" \
  --jav-config target/runelite-240-runtime/jav_local_240.ws
```

`--prepare-only` builds without launching. `Local240Agent.java` is a javaagent that replaces only the RSA modulus
constant in `bq.<clinit>`. `reference/ReferencePlugin.java` is a developer plugin that serves a read-only,
token-protected API on `127.0.0.1` (the token is generated per run into `endpoint.json`, mode 0600).
`reference_mcp.py` exposes it as the `rustclient-reference` MCP server (`reference_status`, `_snapshot`, `_widget`,
`_tile`, `_class`, `_trace_start`, `_trace`, `_trace_stop`); `install-reference-mcp.py` registers it with the Codex
CLI. `verify-reference.py` checks the protocol offline and `--live` exercises every tool against a running client.

Rules: the bridge exposes the public RuneLite API only and has no mutation operations. Log in with a disposable
account; passwords are typed into the client and never stored in this repository. Runtime evidence is a reference
only (see ADR-0005); it never changes the `original-exact` jar and never replaces the pinned inputs.

### Script opcode trace

`--trace-opcodes OUT.tsv` also attaches `Trace240Agent` (read-only). It brackets every interpreter handler
`(I<Script>Z…)I` and aggregates, per (handler, opcode, result, stack-size deltas), which handler really executed an
opcode. A result of 2 means "not handled here" and is dropped. After a session that opens the interfaces you care about:

```sh
python3 tools/client-240/opcode-trace.py OUT.tsv      # writes inputs/script-opcode-trace-240.tsv
python3 tools/client-240/opcode-table.py              # adds traced handler and real stack effect per opcode
```

The agent bytecode was checked offline against the pinned jar (48 classes instrumented, ASM verifier clean); a live
session is still needed to produce data. Unreliable deltas print as `?`.
