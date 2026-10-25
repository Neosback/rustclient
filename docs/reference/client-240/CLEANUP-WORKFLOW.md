# Reading and cleaning the revision-240 injected client

Use two views of the same corpus: immutable original bytecode for evidence, and derived Java/readable
views for navigation. A cleaner-looking decompilation is not proof of equivalent behavior.

## Order of work

1. Verify the original/semantic inputs and tool hashes with `toolchain.py --check`.
2. Index the original corpus in `deob-workbench`. Extract injected API bridges **before** filtering
   injection-only wrappers from a reading view. Preserve getter/setter multipliers and exact targets.
3. Use `decoders` to find modular inverse evidence. Require field ownership, opcode width and paired
   read/write evidence; a common numeric constant alone does not identify a semantic field.
4. Use `cfg`, `cfg-structure` and `provenance` for ambiguous branches, loops and stack/local origins.
   Protocol/sync/mask passes tie player/NPC behavior to actual packet readers and controlled writes.
5. Browse the relevant classes through Recaf MCP or the regenerated Vineflower view. Cross-check
   ambiguous methods with bundled CFR. Record disagreement instead of picking the prettiest output.
6. Promote justified names into owned mapping/ledger shards, then run the rename-only verifier and
   hand-review T3/T4 names. Apply behavior to RustClient separately, in its semantic owner, with fixtures.

The workbench has deterministic passes and JSON reports. Recaf supplies convenient local exploration
through its workspace and multiple decompilers. Reusable evidence extraction belongs in the workbench;
interactive experiments do not silently become production mappings.

The optional [Enigma MCP integration](../../../tools/enigma/README.md) adds GUI-backed hierarchy,
reference and method-family queries plus validated draft renaming. Its isolated drafts do not write
production mappings. The [hybrid resource workflow](../../../tools/deob/README.md) combines injected
rl-suite evidence, vanilla-deob analysis ideas and revision-240 agent search patterns without running
their default transformation pipelines. See the [measured setup](verification/enigma-hybrid-20261025.md).
The subsequent [mapping baseline repair](MAPPING-BASELINE.md) clears the 179 mechanical validation
problems while retaining existing names and confidence. It does not resolve the five Java reconstruction
defects or turn naming counts into completed Rust ports.

## Safe readability improvements

Prefer mapping overlays, method-scoped decompilation, collapsible injection methods, exact API anchors,
packet IDs/lengths, typed constant annotations, and CFG navigation. Keep callback/mixin behavior clearly
marked as injection-derived. A method with several field accesses or side effects should remain
unclassified until its behavior is understood.

If a future transform removes opaque branches, rewrites arithmetic, inlines wrappers, strips dead code
or normalizes exceptions, give it its own derived artifact and transformation manifest. Record original
JAR/class hashes, pass version/options, every original-to-derived member/instruction mapping, and any
instructions with no exact correspondence. Compare exception/side-effect ordering as well as return
values. Renaming verification does not validate these transforms.

Do not recompile Java output and use the resulting bytecode as original evidence. Do not remove
injected bridges before extracting their semantic targets. Do not treat the pinned semantic JAR's
three rewritten constant-dynamic concatenation sites as identical to the original initialization behavior.

## Setup evidence, 2026-10-25

The original corpus hash is
`25f42961c400bd9dfff1554402441c0ba6d1cffd011163cb9b0b4c42ae194f85`.
The setup run of `decoders` reported 786 inverse pairs and 32,340 arithmetic sites. After recognizing
both RuneLite API namespaces, `injected-anchors` reported 571 field anchors and 290 method anchors;
485 semantic-looking methods were deliberately left unclassified. These are analysis outputs, not
571/290 approved names or proof that the injected API is stock game behavior.

The workbench build also exposed a stale `UpdateMaskReport.tests()` accessor and an instruction-ID
`TreeSet` without a comparator. Both were fixed; a generated-bytecode regression test covers the current
and legacy API namespaces and rejects an unrelated interface. A successful build alone would not have
caught the previously unused bridge path.

The [portable naming report](verification/portable-setup-20261024.md) records a separate committed
mapping baseline: 735-class normalized roundtrip identity, 27,052 preserved annotation lines, and
735 generated Java sources with zero failed bodies. It does not certify uncommitted/new mapping shards.
See [Recaf setup](../../../tools/recaf/README.md) for MCP build and smoke-test commands.

For measured renderer results and the current readable export, use
[Decompiler quality](DECOMPILER-QUALITY.md). Its syntax checks found defects beyond the older
file-count/failed-body-marker gate. The checked bundle retains complete original bytecode for Java
that cannot yet be reconstructed, and does not certify the current production naming shards.
