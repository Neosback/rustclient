# Deob Workbench Architecture

## Objective

Produce a readable, navigable reconstruction of a target client while preserving a provable path back to exact shipped bytecode.

```text
original exact jar
      |
      +--> immutable corpus + opcode instruction ids
      |          |
      |          +--> exact CFG
      |          +--> exact class hierarchy
      |          |          |
      |          |          +--> virtual method families
      |          |
      |          +--> stack/local value provenance
      |          |          |
      |          |          +--> decoder/multiplier evidence
      |          |          +--> packet read timelines       (next)
      |          |
      |          +--> dominators/natural loops              (later)
      |
      +--> structural neighbor revisions
      |          |
      |          +--> explainable lineage matcher           (later)
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

The initial index emits two SHA-256 fingerprints per method:

- `exactSha256`: includes owners/names/descriptors and instruction operands.
- `structuralSha256`: removes class/member names from references and normalizes object types while retaining control-flow targets, descriptors, constants, locals, switches, and bootstrap shape.

Neither hash alone proves semantic identity. The future lineage matcher will combine multiple independent features and report ambiguity rather than auto-promote matches.

## CFG

`CfgAnalyzer` partitions exact opcode indices into basic blocks and emits typed edges for:

- fallthrough
- conditional branch
- goto
- legacy subroutine jump
- switch case/default
- exception handlers

The current `backward` flag means only that an edge targets an earlier lexical instruction range. Dominator/post-dominator and natural-loop analysis remain separate so the workbench does not overstate what was proven.

## Hierarchy and virtual families

`ClassHierarchy` records exact in-corpus superclass/interface relationships and deterministic ancestor/descendant traversal. `VirtualMethodFamilies` links non-static, non-private declarations only when the same JVM name and descriptor exist in an ancestor/interface chain.

This is dispatch evidence, not semantic naming. A family key is stable only for the analyzed corpus and exists so later lineage/semantic passes can reason about override groups as one unit.

## Value provenance

`ValueProvenanceAnalyzer` uses ASM source analysis over the exact method body. Each executable instruction reports the producer instructions or argument/local origins feeding the values it consumes.

The analysis intentionally keeps incomplete input shapes explicit for stack-manipulation instructions whose logical consumption depends on category-1/category-2 values. Missing precision is reported as incomplete evidence rather than guessed.

This provenance graph is the substrate for packet reads, decoder arithmetic, field-flow explanations, and future semantic proposals.

## Decoder proof

`DecoderEvidenceAnalyzer` follows provenance through local copies and arithmetic to find integer/long multiplication associated with exact field reads and writes.

A site is only a candidate until the analyzer finds a same-field read multiplier and write multiplier that satisfy:

```text
readMultiplier * writeMultiplier == 1 (mod 2^N)
```

where `N` is 32 or 64 from the field descriptor. Proven pairs are evidence records only. The original bytecode is not normalized or rewritten.

## Evidence source policy

`config/rev240-sources.json` records the role and readiness of each source. The original 1.12.39 gamepack is the behavioral authority. The semantic jar is an identity overlay. `rl-suite` rev-240 jars are neighboring structural evidence. A historically pinned RuneLite snapshot will provide semantic vocabulary. Devious Client is an algorithm reference, not a second bytecode model.

## Next passes

1. Packet-buffer read discovery and packet timeline evidence.
2. Dominators, post-dominators, and natural-loop evidence.
3. Multi-feature 1.12.34 -> 1.12.39 lineage matching using hierarchy, CFG, fingerprints, and value flow.
4. Historical RuneLite semantic-oracle extraction.
5. Field/method semantic candidate scoring with explicit evidence weights.
6. Static workbench pages linking every semantic proposal back to exact instructions.
