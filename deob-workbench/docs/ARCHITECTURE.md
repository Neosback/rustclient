# Deob Workbench Architecture

## Objective

Produce a readable, navigable reconstruction of a target client while preserving a provable path back to exact shipped bytecode.

```text
original exact jar
      |
      +--> immutable corpus + opcode instruction ids
      |          |
      |          +--> exact CFG
      |          |          |
      |          |          +--> dominators + natural loops
      |          |
      |          +--> exact class hierarchy
      |          |          |
      |          |          +--> virtual method families
      |          |
      |          +--> stack/local value provenance
      |                     |
      |                     +--> decoder/multiplier evidence
      |                     +--> structural packet-read discovery
      |                                |
      |                                +--> packet value timelines
      |
      +--> structural neighbor revisions
      |          |
      |          +--> explainable method lineage matcher
      |                     |
      |                     +--> richer CFG/value-flow/family evidence (next)
      |
      +--> identity overlay
      |
      +--> historical RuneLite semantic vocabulary
                 |
                 +--> evidence-backed name candidates
                              |
                              v
                      human-reviewed mappings
```

## One corpus, many passes

A major failure mode in deob tooling is letting each analyzer build its own partially different model of the same classfile. This project instead owns exact classfile bytes in `JarCorpus`. Every pass requests a fresh ASM tree from those bytes and reports evidence using shared `ClassId`, `FieldId`, `MethodId`, and `InstructionId` identities.

The ASM tree is a working view. It is not the identity system and is never the source artifact.

## Instruction identity

`InstructionId` counts only opcode-bearing JVM instructions. Labels, stack-map frames, line numbers, and other debug/pseudo nodes do not consume an index. Branch/switch/exception labels resolve to the first executable instruction at or after the label.

This gives the UI and later evidence records a stable coordinate such as:

```text
aa.bb(I)V#137
```

Decompiler line numbers are presentation coordinates and must not replace this identity.

## Fingerprints

The bytecode index emits two SHA-256 fingerprints per method:

- `exactSha256`: includes owners/names/descriptors and instruction operands.
- `structuralSha256`: removes class/member names from references and normalizes object types while retaining control-flow targets, descriptors, constants, locals, switches, and bootstrap shape.

Neither hash alone proves semantic identity. Lineage uses fingerprints as one weighted feature and reports ambiguity rather than auto-promoting a match.

## CFG

`CfgAnalyzer` partitions exact opcode indices into basic blocks and emits typed edges for:

- fallthrough
- conditional branch
- goto
- legacy subroutine jump
- switch case/default
- exception handlers

The `backward` flag on a raw edge is only lexical. `CfgStructureAnalyzer` separately computes dominator sets and immediate dominators. A natural loop is emitted only when a non-exception/non-subroutine back-edge target dominates its source. This keeps "backward jump" and "proven loop" as separate claims.

Post-dominators and richer region/loop nesting remain future passes.

## Hierarchy and virtual families

`ClassHierarchy` records exact in-corpus superclass/interface relationships and deterministic ancestor/descendant traversal. `VirtualMethodFamilies` links non-static, non-private declarations only when the same JVM name and descriptor exist in an ancestor/interface chain.

This is dispatch evidence, not semantic naming. A family key is stable only for the analyzed corpus and exists so later lineage/semantic passes can reason about override groups as one unit.

## Value provenance

`ValueProvenanceAnalyzer` uses ASM source analysis over the exact method body. Each executable instruction reports the producer instructions or argument/local origins feeding the values it consumes.

The analysis intentionally keeps incomplete input shapes explicit for stack-manipulation instructions whose logical consumption depends on category-1/category-2 values. Missing precision is reported as incomplete evidence rather than guessed.

This provenance graph is the substrate for packet reads, decoder arithmetic, field-flow explanations, and later semantic proposals.

## Decoder proof

`DecoderEvidenceAnalyzer` follows provenance through local copies and arithmetic to find integer/long multiplication associated with exact field reads and writes.

A site is only a candidate until the analyzer finds a same-field read multiplier and write multiplier that satisfy:

```text
readMultiplier * writeMultiplier == 1 (mod 2^N)
```

where `N` is 32 or 64 from the field descriptor. Proven pairs are evidence records only. The original bytecode is not normalized or rewritten.

## Packet-read discovery

`PacketReadDiscovery` searches exact methods for structural byte-buffer behavior rather than semantic method names. Evidence includes byte-array loads, integer fields both read and written in the same method, `& 255` masks, shifts, branches, return type, and fixed/variable byte-consumption shape.

A candidate can therefore be described as `FIXED_TWO_BYTE` with specific byte-array and offset-field evidence without claiming it is a particular RuneScape protocol primitive.

## Packet value timelines

`PacketTimelineAnalyzer` treats calls to discovered read candidates as exact roots. It reverses the provenance relation into producer-to-consumer edges and walks reachable consumers. Events are classified as read calls, transforms, local flow, field writes, branches, calls, returns, or other dependent instructions.

This is the first direct bridge from "candidate buffer read" to "what client state or control flow did that value affect?". Analyzer failures are retained in `skippedMethods` so missing coverage remains visible.

## Cross-revision lineage

`MethodLineageMatcher` is the first explainable method matcher. It normalizes object identities in method descriptor shape, enforces static/constructor compatibility, and ranks candidates using:

- rename-insensitive structural bytecode hash
- instruction count
- branch and switch counts
- try/catch count
- invoke count
- field-access count
- simple owning-class shape

Every score contribution is emitted. The best score is compared with the runner-up, and close candidates become `AMBIGUOUS`. No mapping is written automatically.

This is intentionally only phase one. Future lineage enrichment should add exact CFG topology, dominator/loop signatures, hierarchy/virtual-family context, packet-read roles, decoder evidence, and value-flow fingerprints before high-confidence semantic carry-forward is attempted.

## Evidence source policy

`config/rev240-sources.json` records the role and readiness of each source. The original 1.12.39 gamepack is the behavioral authority. The semantic jar is an identity overlay. `rl-suite` rev-240 jars are neighboring structural evidence. A historically pinned RuneLite snapshot will provide semantic vocabulary. Devious Client is an algorithm reference, not a second bytecode model.

## Next passes

1. Enrich lineage with CFG topology, dominator/loop signatures, virtual families, decoder sites, and packet/value-flow evidence.
2. Identify packet-buffer class families and packet handler call paths, including ISAAC/framing relationships.
3. Build focused player/NPC update dossiers from packet reads through update masks into Actor/Player/NPC state.
4. Pin the historical RuneLite snapshot nearest the target rev-240 window and extract semantic vocabulary.
5. Add field/method semantic candidate scoring with explicit source weights and provenance.
6. Add static workbench pages linking every proposal back to exact instructions and revision lineage.
