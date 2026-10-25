# Deob Workbench Architecture

## Objective

Produce a readable, navigable reconstruction of a target client while preserving a provable path back to exact shipped bytecode.

```text
original exact 1.12.39 jar
      |
      +--> immutable corpus + opcode instruction ids
      |          |
      |          +--> exact CFG -> dominators -> natural loops
      |          +--> exact hierarchy -> virtual method families
      |          +--> provenance -> decoder proofs
      |          +--> packet reads -> packet timelines
      |          +--> buffer family -> bit-access candidates
      |          +--> generic protocol entry candidates
      |
      +--> rev-240 packet profile (external semantic evidence)
      |          |
      |          +--> PLAYER_INFO / NPC_INFO_* ids
      |                        |
      |                        +--> exact switch cases
      |                        +--> target basic blocks
      |                        +--> player-like / NPC-like calls
      |                                      |
      |                                      +--> update-mask tests
      |                                      +--> mask-controlled field writes
      |                                      +--> Actor vocabulary hints
      |
      +--> neighboring rev-240 structural lineage
      |
      +--> historical RuneLite semantic vocabulary
                              |
                              v
                       human-reviewed mappings
```

## Authority model

`JarCorpus` owns exact target classfile bytes. Every analyzer opens a fresh ASM tree and reports through shared `ClassId`, `FieldId`, `MethodId`, and `InstructionId` coordinates. No transformed/decompiled representation becomes behavioral authority.

External packet tables and RuneLite names are semantic evidence. They may label or rank exact-bytecode evidence, but they cannot redefine what the target bytecode does.

## CFG, hierarchy, provenance, and decoders

`CfgAnalyzer` emits exact basic blocks and typed normal/exception edges. `CfgStructureAnalyzer` computes dominators and only reports natural loops when a normal back-edge target dominates its source.

`ClassHierarchy` and `VirtualMethodFamilies` model exact in-corpus type/dispatch structure.

`ValueProvenanceAnalyzer` uses ASM source analysis for exact producer evidence. `DecoderEvidenceAnalyzer` proves same-field integer/long read/write multipliers only when they are modular inverses. Missing precision remains explicit.

## Packet reads and value timelines

`PacketReadDiscovery` ranks methods that consume object-owned byte arrays and mutate offset-like integer state. Byte count, masks, shifts, branches, and loops are structural facts, not semantic method names.

`PacketTimelineAnalyzer` treats discovered read calls as roots and follows exact provenance through transforms, locals, field writes, branches, calls, and returns.

## Generic protocol entry analysis

`BufferFamilyAnalyzer` groups likely buffer classes from packet-read ownership, byte-array/offset fields, and inheritance.

`ProtocolEntryAnalyzer` ranks framing/cipher-like methods, packet-handler methods, and high-density player-update hypotheses. These generic hypotheses are useful for narrowing search space but are deliberately weaker than rev-specific packet-table correlation.

## External rev-240 packet profile

`PacketTableParser` consumes the five-column rl-suite rev-240 TSV shape:

```text
id    name    length    read/write_count    fields
```

It preserves the raw read/write operation strings. Client write descriptors are split only on top-level commas so byte-encoding lists inside brackets remain intact.

The workbench does not vendor the upstream TSVs. Their names/ids are external protocol evidence with independent provenance.

## Bit-oriented buffer access

`BitAccessAnalyzer` searches exact methods for the intersection of:

- object-owned byte-array access;
- byte loads;
- shift-by-3 operations associated with bit-index/byte-index conversion;
- mutable integer state;
- mask operations;
- 7/8 constants;
- branch/loop structure.

A high score means bit-oriented buffer behavior is structurally plausible. It does not prove a particular RuneLite/Jagex method name such as `readBits`, `importIndex`, or `exportIndex`.

## Rev-240 player/NPC sync correlation

`Rev240SyncAnalyzer` reads `PLAYER_INFO` and `NPC_INFO_*` ids from the supplied server packet profile. It then finds exact `TABLESWITCH`/`LOOKUPSWITCH` cases containing those ids.

For each case it records:

1. exact dispatcher method and switch instruction;
2. external packet id/name/length;
3. exact target instruction/basic block;
4. direct calls made by that target block.

Those direct calls become role candidates and are strengthened with exact bit-access calls, structural packet-read calls, field writes, branches, and downstream calls. Roles are `PLAYER_LIKE`, `NPC_LIKE`, `MIXED`, or `UNKNOWN`.

The role remains evidence, not truth: a target-block call can be a helper, and packet-table names come from an external profile.

## Update-mask evidence

`UpdateMaskAnalyzer` runs only over sync-role candidates. It looks for exact:

```text
IAND <constant>
conditional branch
```

idioms. For every match it records the exact `IAND`, exact branch, and field writes in both immediate CFG successor blocks. It does not assume `IFEQ`/`IFNE` orientation or claim that every such test is a canonical update-mask bit.

Fields observed under these tests receive low-confidence semantic vocabulary based on descriptor and shared player/NPC role. Integer fields can carry candidates such as animation, pose animation, frame, orientation, target, or movement-cycle state; string fields can carry overhead-text vocabulary. These candidate strings are prompts for later evidence fusion, never automatic mappings.

## Cross-revision lineage

`MethodLineageMatcher` currently combines rename-insensitive structural fingerprints, normalized descriptor shape, instruction/control-flow counts, call/field-access shape, and owner structure with explicit score contributions and ambiguity.

Future lineage scoring should add CFG topology, loop signatures, virtual-family position, decoder patterns, bit-access roles, rev-240 dispatch roles, mask signatures, and value-flow fingerprints.

## Evidence fusion target

The intended final semantic decision should look like:

```text
exact field/method
    + rev-240 packet case
    + bit/update-mask role
    + packet read/value flow
    + decoder proof
    + neighboring revision lineage
    + historical RuneLite vocabulary
    + human review
        -> readable mapping
```

No single source gets to shortcut this chain.

## Next passes

1. Pin the historical RuneLite commit nearest the 1.12.39/rev-240 period and ingest only its semantic/API vocabulary.
2. Expand sync dispatch evidence through interprocedural call paths instead of only direct target-block calls.
3. Fingerprint update-mask blocks by read variants, constants, calls, and written fields so neighboring revisions can align individual mask semantics.
4. Separate movement, animation, facing/target, overhead text, hitsplat/health, and spot-animation clusters with evidence-backed scoring.
5. Produce focused player and NPC dossiers with exact packet case -> mask bit -> read -> transform -> field/call -> cycle dependency chains.
6. Add static workbench views for packet dispatch, masks, lineage, and semantic proposals.

## Safety invariant

No analysis view replaces the original bytecode. No packet-table label, scored role, bit-access hypothesis, Actor vocabulary hint, or readable name becomes authoritative merely because an analyzer produced it. Every conclusion must remain traceable to exact shipped instructions and its external evidence sources.
