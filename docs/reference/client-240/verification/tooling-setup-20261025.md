# Research tooling setup: 2026-10-25

Result: **PASS for the checks listed below**. No naming wave or Rust runtime changes are included.

| Check | Observed result |
| --- | --- |
| Canonical skill validation | Valid; installed link points to the repo-owned skill |
| Pinned naming dependencies/inputs | 9 runtime JARs, 1 source archive, 4 inputs verified; associated licenses tracked |
| Naming verifier | [Committed baseline report](portable-setup-20261024.md): 735-class normalized roundtrip identity, 27,052 preserved annotation lines, 735 Java sources, zero failed bodies |
| Setup failure cases | Corrupt/missing input, reference/cache-root output, label traversal, occupied skill install, stale mapping rejection; dependent remap/decompile did not run after stale-symbol failure |
| Secondary decompiler | Bundled CFR produced nonempty `Actor.java` |
| Workbench | Maven package succeeded; 1 test, 0 failures/errors/skips |
| Workbench exact analysis | 786 inverse multiplier pairs; 571 injected field anchors, 290 method anchors, 485 methods unclassified |
| Packet evidence | Bundled client/server TSVs parsed through `packet-profile` |
| Recaf | Pinned source build and MCP adapter compilation succeeded |
| Registered MCP launch | Initialize/notification/catalog and all 6 tools succeeded against 735 classes; CFR decompilation, bytecode, strings, errors and EOF shutdown checked |
| Corpus protection | Original SHA-256 preserved; `git status -- references` empty |
| Syntax/format | All client-240 shell scripts parsed, Python research scripts parsed, skill metadata valid, `git diff --check` clean |

Workbench source snapshot SHA-256: `926ad69e9d4877737575a4e58a7637bd79fa07eb7f0f06b6214acbce31c87cd4`. The wrapper records each source file hash and its
built CLI hash under `.cache/client-240/workbench/<snapshot>/build.json`. This snapshot includes
local workbench fixes; it is distinct from a claim about only the base Git commit.

Recaf source commit: `d5888cdc10ba301659146e67a823d7d124851650`.
MCP server registration: `rustclient-recaf`, stdio; the registered command and its selected JDK were
used for the smoke run. The protocol was tested directly; this does not claim that the current chat
has hot-loaded the newly registered tools.

Original client SHA-256: `25f42961c400bd9dfff1554402441c0ba6d1cffd011163cb9b0b4c42ae194f85`.
Analysis outputs and build logs stay under ignored `.cache/` and `target/`; repeat commands are in
[the corpus README](../README.md) and [Recaf setup](../../../../tools/recaf/README.md).

The naming bundle is repo-contained. Optional workbench/Recaf builds additionally need the documented
host JDK and first-build network access; transitive Maven/Gradle dependencies are not claimed to be
fully vendored or hermetically locked. No Cargo checks were run because no Rust code changed.
These checks verify setup and execution, not semantic correctness of proposed names or transformed bytecode.
