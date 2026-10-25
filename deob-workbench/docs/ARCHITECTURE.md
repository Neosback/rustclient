# Deob Workbench Architecture

## Objective

Produce a readable, navigable reconstruction of a target client while preserving a provable path back to exact shipped bytecode.

```text
original exact jar
      |
      +--> immutable corpus + opcode instruction ids
      |          |
      |          +--> exact CFG
      |          +--> symbolic value provenance       (next)
      |          +--> decoder/multiplier proof        (next)
      |          +--> packet read timelines           (later)
      |
      +--> structural neighbor revisions
      |          |
      |          +--> explainable lineage matcher     (later)
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

The current `backward` flag means only that an edge targets an earlier lexical instruction range. Dominator/post-dominator and natural-loop analysis will be a separate pass so the workbench does not overstate what was proven.

## Evidence source policy

`config/rev240-sources.json` records the role and readiness of each source. The original 1.12.39 gamepack is the behavioral authority. The semantic jar is an identity overlay. `rl-suite` rev-240 jars are neighboring structural evidence. A historically pinned RuneLite snapshot will provide semantic vocabulary. Devious Client is an algorithm reference, not a second bytecode model.

## Next passes

1. Virtual method family index from the exact hierarchy.
2. Symbolic stack/local value provenance over ASM frames.
3. Modular arithmetic/encoder-decoder proof with stated/proven/inferred confidence.
4. Packet-buffer read discovery and packet timeline evidence.
5. Multi-feature 1.12.34 -> 1.12.39 lineage matching.
6. Historical RuneLite semantic-oracle extraction.
7. Static workbench pages linking every semantic proposal back to exact instructions.
