# Combining injected-client research tools

Use the injected JAR as the permanent input. Combine analyzers and evidence; do not chain every tool's
default transforms and call the result vanilla. Injection adds members and modifies existing bodies.
Removing API interfaces/hooks cannot reconstruct an exact pre-injection gamepack.

The canonical pass policy is [deob-passes.json](../client-240/deob-passes.json). This command composes
the existing ASM workbench with optional local rl-suite comparison and read-only agent-inspired searches:

```bash
python3 tools/client-240/workbench.py --build
python3 tools/deob/hybrid-review.py
python3 tools/deob/test_hybrid_review.py
```

Requirements: Python 3.9+ and JDK 17+. The first workbench build uses its pinned Maven bootstrap.
The default command needs no personal source checkout. Outputs stay under
`.cache/client-240/hybrid-review/`. `--out` must be a child of `CLIENT240_CACHE`.

To reproduce the optional comparison, supply a local rl-suite all-JAR and source roots:

```bash
python3 tools/deob/hybrid-review.py \
  --suite-jar /path/to/rl-suite/transform-jar/build/libs/rl-suite-1.3.1-all.jar \
  --suite-source /path/to/rl-suite \
  --vanilla-source /path/to/RuneLite-melxin/deobfuscatorRuneLite \
  --agent-source /path/to/open-runelite-rev-240/runelite-agent
```

The optional rl-suite probe calls `JarArchive.read` and `DecoderTable.extract`; it applies no passes.
The original JAR is parsed, not executed. Source inventories and the supplied binary receive separate
hashes: this command does not prove that an existing all-JAR was built from the inventoried source.
It does not rebuild or modify those external trees. No rl-suite implementation is copied into this repo.

## Division of work

| Resource | Use | Boundary |
| --- | --- | --- |
| rl-suite | Injected API anchors, confidence vocabulary, hierarchy-aware naming and comparison | Preserve bridges, descriptors, overrides and ambiguity; do not run all default normalizers as authority |
| Legacy RuneLite/Devious deob | CFG, value-flow, arithmetic and mapping ideas | Adapt to the existing ASM owner; default exception/deletion passes are unsuitable for exact evidence |
| Revision-240 runelite-agent | Locate API accessors, menu call targets, lambda handles and diagnostic/platform methods | Search patterns only; runtime patches change behavior |
| `deob-workbench` | Exact corpus, instruction IDs, CFG, provenance, inverse pairs, update-mask research | Owns bytecode evidence; no second bytecode IR |
| [Recaf](../recaf/README.md) | Browse bytecode and compare decompilers | Read-only exploration; decompiled source is presentation |
| [Enigma MCP](../enigma/README.md) | Hierarchy/reference exploration and draft naming validation | Saved drafts require evidence review and rename verification before promotion |

The supplied vanilla deob starts by removing `RuntimeException` handlers. Its `InvokeDynamic`
implementation returns an empty method target list and rejects some target rewrites. Its constant
type model does not cover `ConstantDynamic`. These are concrete reasons to avoid its unmodified
pipeline on this input. Its unused-method pass *does* preserve many long non-obfuscated names, and
its unused-parameter pass has obfuscated-signature checks; it is inaccurate to claim it blindly
deletes all injected API members. Those protections do not make reachability complete for injection,
reflection, bootstrap handles or external callers.

rl-suite is closer to this corpus: it extracts decoder facts before mutation, preserves API/bridge
families, and marks unused members instead of deleting them. Its default pipeline also includes
packet-handler and constant-dynamic normalization. Those remain separate analysis views. The local
source did not provide an identified root license; it remains a design reference under the existing
policy. The optional executable probe uses public APIs of the user-supplied binary.

## What the agent contributes

`AgentPatternProbe.java` reuses `JarCorpus` and `InstructionIndex`; it searches original bytes without
loading the agent. Each match retains owner, name, descriptor and real-opcode index (`-1` denotes a
method-level signature match). It inventories candidates beyond the agent's class-name/load-order
filters, and does not promise to emulate which classes a live transformer would change.

Useful supplied patterns include `getAnimation`, calls from `client.menuAction`/world-hopper wrappers,
`initRLICN`, `HierarchyEvent` bootstrap method handles, platform-constructor signatures and error-report
literals. Full descriptors are retained, and multiple fields/calls remain visible instead of selecting
the first match. Pattern matches do not establish stock game behavior or justify an automatic name.

The supplied agent is a live behavioral patcher. Its ten registered transformers include animation
accessor changes, a menu wrapper, diagnostics, platform/device/random-data changes and early returns
in selected methods. None are applied by this workflow. Its manifest advertises retransform support,
but `Agent.instrument` calls the one-argument `addTransformer` overload; the manifest alone does not
prove already-loaded classes are retransformed. Source comments also mention older revisions, so the
directory name alone does not establish compatibility with the pinned 240 artifact.

## Decoder comparison

`decoder-comparison.json` keeps each rl-suite confidence label and compares typed field identities
modulo 2^32 or 2^64 against workbench inverse pairs. Every reported pair is checked arithmetically.
An inverse product of one proves that arithmetic relation; it does not prove a globally unique or
semantically correct decoder. Matching multiple pairs stays ambiguous. Disagreements are retained.

rl-suite's decoder key is `owner.name`, without descriptor. When that identity has several descriptors
in the original JAR, the probe emits `AMBIGUOUS` and does not guess which field was intended. A missing
workbench pair is an evidence gap, not proof the rl-suite candidate is false. Matching a workbench
pair does not upgrade an inferred candidate into an approved mapping.

The [measured setup report](../../docs/reference/client-240/verification/enigma-hybrid-20261025.md)
records the current comparison, exact source hashes and limitations. `review.json` records tool/source/
input/output hashes, source inventories and protected-input checks. The command never writes mapping
shards or ledgers. It checks those and the original input for changes before completing.

This workflow improves inspectability and surfaces uncertainty. It does not type-check a complete
recompiled client or recover all missing Java. The existing reading bundle still has five unresolved
source files and a separate production mapping validation failure; see
[Decompiler quality](../../docs/reference/client-240/DECOMPILER-QUALITY.md).
