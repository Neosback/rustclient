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
      |          |          |
      |          |          +--> decoder/multiplier evidence
      |          |          +--> packet read timelines
      |          |
      |          +--> buffer-family evidence
      |                     |
      |                     +--> framing/cipher-like candidates
      |                     +--> packet-handler candidates
      |                     +--> player-update dossier hypotheses
      |
      +--> structural neighbor revisions
      |          |
      |          +--> explainable lineage matcher
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

`JarCorpus` owns exact classfile bytes. Every pass requests a fresh ASM tree and reports evidence using shared `ClassId`, `FieldId`, `MethodId`, and `InstructionId` identities. The ASM tree is a working view, not the source artifact or the identity system.

## Exact instruction identity

`InstructionId` counts opcode-bearing JVM instructions only. Labels, frames, line numbers, and pseudo nodes do not consume indices. This keeps packet, CFG, decoder, and semantic evidence tied to exact shipped instructions rather than decompiler line numbers.

## CFG structure

`CfgAnalyzer` emits exact basic blocks and typed normal/exception edges. `CfgStructureAnalyzer` computes dominators and immediate dominators and only reports a natural loop when the target of a normal back edge dominates its source. Lexical backward jumps therefore remain distinct from proven natural loops.

## Hierarchy and virtual dispatch

`ClassHierarchy` records exact in-corpus superclass/interface relationships. `VirtualMethodFamilies` groups override-equivalent methods by JVM name/descriptor only when hierarchy evidence supports the relationship. These families are structural evidence, not semantic names.

## Value provenance and decoders

`ValueProvenanceAnalyzer` uses ASM source analysis to identify exact producer instructions and argument/local origins for consumed values. Incomplete stack-manipulation cases are marked incomplete instead of guessed.

`DecoderEvidenceAnalyzer` uses that provenance to locate integer/long multiplier sites. A field only receives `PROVEN_INVERSE_PAIR` evidence when a read and write multiplier are multiplicative inverses modulo 2^32 or 2^64. No bytecode normalization occurs.

## Packet reads and timelines

`PacketReadDiscovery` ranks methods that structurally consume object-owned byte arrays and mutate offset-like integer fields. It classifies fixed/variable byte-consumption shapes without assigning semantic names such as `readUnsignedShort`.

`PacketTimelineAnalyzer` treats calls to discovered read methods as roots and follows exact provenance forward into transforms, locals, field writes, branches, calls, and returns. Unanalyzable methods are reported explicitly.

## Protocol entry evidence

`BufferFamilyAnalyzer` groups packet-read methods by class and exact inheritance relationships. The goal is to identify likely buffer families without assuming class names.

`ProtocolEntryAnalyzer` then correlates the existing evidence into four separate hypothesis layers:

1. **Buffer-family candidates**: packet-read ownership, byte-array fields, offset fields, subtype structure.
2. **Framing/cipher-like candidates**: packet-read calls, byte-array access, integer-return calls, object state, arithmetic. These are not called ISAAC unless later evidence proves that role.
3. **Packet-handler candidates**: multiple packet-read timelines plus dependent transforms, field writes, branches, calls, and natural loops.
4. **Player-update dossier candidates**: high-density update handlers with exact read, field-write, branch, call, and loop evidence. The word `player` is a hypothesis label only until historical RuneLite/neighbor revision/packet evidence corroborates it.

This separation is intentional. A method can be an excellent structural packet-handler candidate while still being NPC, widget, region, chat, or another protocol path.

## Cross-revision lineage

`MethodLineageMatcher` ranks source->target method candidates with visible score contributions from rename-insensitive structural fingerprints, normalized descriptor shape, instruction/control-flow counts, call/field-access shape, and owning-class structure. Close candidates are reported as `AMBIGUOUS`; no match is promoted into mappings automatically.

The next lineage revision should add CFG topology, natural-loop signatures, virtual-family role, decoder patterns, packet-read roles, and value-flow fingerprints to the scoring model.

## Evidence source policy

`config/rev240-sources.json` records source role/readiness. Exact 1.12.39 is behavioral authority. The semantic 1.12.39 jar is an identity/readability overlay. `rl-suite` rev-240 jars are neighboring structural evidence. Historical RuneLite will be a semantic vocabulary source once an exact time-appropriate commit is pinned. Devious Client is an algorithm reference, not a second bytecode model.

## Next passes

1. Correlate protocol candidates with rl-suite server/client packet tables and neighboring rev-240 lineage.
2. Discover bit-access/start/end-bit patterns and update-mask dispatch structure.
3. Pin the historical RuneLite snapshot nearest the 1.12.39/rev-240 target.
4. Score Actor/Player/NPC semantic candidates using packet flow + lineage + RuneLite vocabulary.
5. Produce focused player/NPC update dossiers containing mask bits, read variants, field writes, movement/animation/facing/cycle dependencies, and exact instruction links.
6. Add static workbench views for protocol paths, lineage confidence, and semantic proposals.

## Safety invariant

No analysis view replaces the original bytecode. No candidate role or readable name becomes authoritative merely because an analyzer scored it highly. The target output is traceable evidence that a human can audit back to the exact shipped instruction stream.
