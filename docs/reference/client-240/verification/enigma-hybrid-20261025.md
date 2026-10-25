# Enigma MCP and hybrid-resource setup, 2026-10-25

Status: **PASS for the bounded setup and read-only comparison below**. This is not a naming-wave,
complete decompilation, behavioral-parity or Rust-port completion claim.

## Enigma integration

The supplied Fabric tree matched official commit
`29993f13872f25bcf7e2a354750a6756c5882ca6` byte-for-byte. The supplied Enigma-MCP fork changed 14 files
against that snapshot. Its complete delta and LGPL license material are repository-owned under
[`tools/enigma`](../../../../tools/enigma/README.md). The fork's original remote was unavailable.
The Quilt source uses a different package/API; Fabric was chosen for compatibility with this existing
MCP implementation. It was not chosen by comparing decompiler quality or version numbers.

Built the GUI integration with host JDK 24, then launched the metadata-clean reading input in
**RustClient — Enigma Mapping Review**. All 16 tools were exercised over the actual loopback HTTP
server, including initialize/notification/ping, default-package and member pagination, Actor hierarchy,
PacketBuffer references, method families, valid/invalid/colliding name checks, constructor rejection,
temporary rename/javadoc/mark/reset edits, source pagination, invalid requests and a completed draft save.
Temporary edits were restored. The server indexed **735 classes, 7,350 fields and 18,967 methods**.

The source changes also fixed empty default-package queries, constructor renamability metadata and
the fork's save response returning before asynchronous writes completed. Hashes and Git status checks
confirmed original references and production mappings/ledgers were unchanged. A subsequent build/hash
check and idempotent live installer check passed. `rustclient-enigma` is registered in local Codex;
new direct tools require a connection reload. The server is backed by the open GUI session.

Upstream Enigma tests were skipped by this integration build. External RuneLite API dependency JARs
were not loaded. Enigma's analysis frames/families and mapped-status flags do not replace original
instruction evidence or ledger approval. Draft saves stay under the isolated cache session.

## Hybrid resources actually inspected

Reviewed the user-supplied `RuneLite-melxin/deobfuscatorRuneLite`, `rl-suite-main`, and
`open-runelite-rev-240/runelite-agent` source trees without modifying them. The
[machine record](enigma-hybrid-20261025.json) records source inventory digests and selected file hashes;
the generated full inventories live in `.cache/client-240/hybrid-review/review.json`.

The original input SHA-256 is
`25f42961c400bd9dfff1554402441c0ba6d1cffd011163cb9b0b4c42ae194f85`.
The metadata-clean Enigma input is
`1968e214aa130284da841aa00ee90cb0983ff45a0388338dfef9a414743cb135`.
The existing metadata-view check retained every Code attribute and all non-Named metadata bytes;
annotation reflection changes, so this remains a reading view.

The optional local rl-suite all-JAR SHA-256 is
`7f0d65bc300a87f45e0c6748cf61e8969deb3caac6c3f998b7b640ac2dff8353`.
Its correspondence to the inspected source tree was **not** rebuilt/proven. The following executable
results belong to that exact binary. No rl-suite transformation was run by the probe.

## Decoder results

The workbench produced **786 inverse pairs across 715 typed fields** and 32,340 arithmetic sites.
rl-suite produced **1,473 candidates**: 575 `PROVEN`, 381 `STATED`, 517 `INFERRED` by its own labels.

| Comparison outcome | Candidates |
| --- | ---: |
| Match a single workbench pair | 493 |
| Match one of several workbench pairs | 62 |
| Different multiplier from available workbench pairs | 61 |
| No workbench pair available | 780 |
| Ambiguous descriptor under rl-suite's owner/name key | 77 |

The 61 differing candidates include 30 labelled `PROVEN`, 19 `STATED` and 12 `INFERRED` by rl-suite.
There are also 99 workbench fields without an unambiguous matching typed rl-suite candidate.
Neither analyzer wins by label or count. Modular arithmetic is checked at the correct width, but
semantic meaning, field identity and site provenance still need review. Multiple pairs and descriptor
collisions are preserved; no disputed candidate is automatically normalized or named.

The same run regenerated 571 exact field anchors and 290 method anchors using the workbench.
These remain evidence candidates, not approved names.

## Agent-pattern results

The read-only probe found all these candidates in original bytes:

- `client.menuAction(IILnet/runelite/api/MenuAction;IILjava/lang/String;Ljava/lang/String;)V`
  invokes `qp.ef(IIIIIILjava/lang/String;Ljava/lang/String;IIB)V` at opcode index 34.
  `openWorldHopper` and `hopToWorld` independently call the same target.
- `dh.getAnimation()I` reads `dh.cn:Lqr;` at index 1 and `qr.af:I` at index 2, then multiplies by
  `1684838611`. Recaf disassembly from the original-hash workspace confirms this getter is already
  a straight field-chain/multiply/return; the agent's restriction-removal comment is not evidence that
  this particular revision needs its runtime patch.
- `client.ru()V` calls `client.initRLICN(Ljava/awt/Component;)V` at index 48.
- `tq.ql()V` has a bootstrap Handle to `tq.ls(Ljava/awt/event/HierarchyEvent;)V` at index 1.

Additional localization candidates include two runtime-argument calls, one platform constructor,
three focus-method name matches, eight error-report literals and six device-command literals.
These are static searches. The agent was not loaded or applied, and its transformed runtime behavior
was not tested. The pattern probe deliberately does not reproduce its load-order/class-name filters.

## Checks and remaining limits

- Enigma build, recipe/JAR hash check, all-16-tool live smoke and idempotent MCP registration passed.
- Hybrid evidence extraction/comparison completed; original, reference status and production mapping/
  ledger checks passed; zero transformations or automatic mapping writes.
- Six focused comparison tests passed: signed/unsigned residues, 64-bit width, descriptor identity,
  multiple-pair ambiguity, preserved confidence on disagreement, invalid inverse rejection.
- The baseline quality bundle still has **five unresolved Java files**, and the separate production
  mapping validation still has **179 issues**. This setup did not repair or certify those mappings.
- No full client Java type-check, upstream Enigma suite, runtime agent test or client behavioral parity
  run was performed. Rust source was unchanged; workspace Cargo checks were not run for this tooling work.

Reproduce with [`tools/deob/hybrid-review.py`](../../../../tools/deob/README.md) and the
[Enigma setup commands](../../../../tools/enigma/README.md). The canonical pass decisions remain in
[`deob-passes.json`](../../../../tools/client-240/deob-passes.json).
