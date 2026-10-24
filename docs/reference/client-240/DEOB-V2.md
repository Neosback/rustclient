# Client-240 deob v2: bytecode-first research workbench

Status: implementation branch. This design extends the existing mapping and verification pipeline. It does not replace the tiny-v2 shards, symbol ledger, or rename-only proof.

## Problem statement

The existing client-240 workflow is strong at proving that a rename is mechanically safe, but weaker at proving that the evidence used to choose a name or describe behavior is faithful to the shipped gamepack.

The current flow is effectively:

```text
original injected jar
        |
        | rl-suite semantic transforms
        v
semantic jar
        |
        +--> Vineflower source ----------------------> human interpretation
        |
        +--> tiny-v2 readable overlay --> Vineflower -> readable source
        |
        `--> CFR only when a researcher notices suspicious source
```

That has four failure modes.

### 1. The semantic jar is not the original control flow

The pinned semantic audit records deliberate transformations before the naming wave ever starts. For revision 240 it reports, among other changes:

- 329 packet-handler instructions removed;
- 165 packet return jumps flattened;
- packet-handler try/catch regions reduced from 1,640 to 10;
- three ConstantDynamic string-concatenation sites materialized;
- decoder normalization and unused-member marking;
- structural symbol renaming.

These transforms may be valid analysis transforms, but they mean the semantic jar cannot be the sole behavioral authority for protocol, player/NPC update, cycle, animation, or exception-sensitive behavior. A decompiler can only reconstruct the bytecode it is given.

**v2 rule:** original gamepack bytecode is behavioral ground truth. The semantic jar is an identity/readability overlay. Any transformed method is explicitly surfaced before a researcher treats its decompiled Java as behavioral evidence.

### 2. Decompiled Java is currently too close to being the data model

Class ownership is commonly discovered with source `grep`; carry-forward is based on a compact structure dump; ambiguous control flow is manually sent to CFR. This makes source text do jobs that bytecode metadata can do deterministically.

Source `grep` can miss or distort:

- descriptor-only dependencies;
- inheritance/interface dependencies;
- bootstrap handles and invokedynamic dependencies;
- annotations and signatures;
- subclass families whose source names do not match a word-boundary grep;
- references hidden by a decompiler reconstruction;
- references that a decompiler inlines or rewrites into higher-level syntax.

**v2 rule:** reference sets, ownership boundaries, member identity, call edges, field edges, control-flow metrics, and fingerprints come from ASM over the jar. Decompiled source is presentation evidence only.

### 3. The second decompiler is reactive rather than systematic

Vineflower is the primary renderer and CFR is invoked only after a researcher notices a suspicious labelled break, collapsed switch, `$VF` marker, or other anomaly. That catches known-looking failures, not silent disagreements.

**v2 rule:** a requested class can be rendered through both pinned decompilers and compared automatically. The workbench emits a dossier containing original-bytecode facts beside both source renderings and marks disagreement instead of silently selecting one.

### 4. Cross-revision carry-forward has too little structural entropy

`carry235.py` correctly refuses many ambiguous matches and already learned that member order is unreliable. Its 240-side dump, however, describes methods primarily with descriptor, access, instruction count, and invocation count. Distinct obfuscated methods can share those features.

**v2 rule:** candidate matching can additionally consume a rename-invariant bytecode fingerprint built from canonical obfuscated identities, opcode classes, constants, member-reference identities, branch shape, switch shape, and exception-table shape. A mismatch is negative evidence. A fingerprint is never itself permission to assign a human-readable name.

## Architecture

```text
                     +---------------------------+
                     | original revision-240 jar |
                     +-------------+-------------+
                                   |
                                   | ASM, no decompilation
                                   v
+----------------+      +----------+-----------+      +------------------+
| semantic jar   |----->| evidence index /     |<-----| tiny-v2 mappings |
| identity layer |      | original-vs-semantic |      | and name ledger  |
+-------+--------+      | drift audit          |      +------------------+
        |               +----------+-----------+
        |                          |
        |                          +--> exact bytecode reference graph
        |                          +--> canonical symbol identities
        |                          +--> method metrics/fingerprints
        |                          +--> transformed-method warnings
        |                          `--> cluster candidate sets
        |
        +-----------------------+
                                v
                   +------------+-------------+
                   | decompiler ensemble       |
                   | Vineflower + CFR          |
                   +------------+-------------+
                                |
                                v
                   human-readable research dossier
```

The architecture deliberately separates four concepts:

1. **Identity:** stable semantic names plus `@ObfuscatedName` / `@ObfuscatedSignature` linkage.
2. **Behavioral facts:** original bytecode, descriptors, constants, edges, branches, switches, exception regions.
3. **Presentation:** Vineflower and CFR renderings.
4. **Names:** evidence-backed tiny-v2 overlays and ledger rows.

A name never changes the behavioral index. A decompiler disagreement never changes bytecode facts.

## New tooling

### `Client240Evidence.java`

A repository-local ASM program that emits deterministic TSV evidence. It is intentionally separate from `Client240Tool.java` so the existing verified rename path is not destabilized while v2 is evaluated.

Planned commands:

```text
index JAR OUT.tsv
    Build a bytecode-first class/member/reference index.

audit ORIGINAL.jar SEMANTIC.jar OUT.tsv
    Match symbols through obfuscated identity and report methods whose bytecode
    shape or control-flow metrics differ between the shipped and semantic jars.

refs JAR CLASS [CLASS...]
    Print the exact incoming/outgoing class reference closure from bytecode,
    descriptors, hierarchy and annotations rather than source grep.
```

The index records both a strict instruction fingerprint and a canonical structural fingerprint. Canonical identities use preserved obfuscation annotations where available so a pure rename does not manufacture a structural change.

### `deob-v2.py`

The orchestration CLI. It resolves only repository-pinned inputs and tools and writes generated evidence below `.cache/client-240/deob-v2/` unless explicitly redirected through the existing safe-output policy.

It will provide:

```text
python3 tools/client-240/deob-v2.py index
python3 tools/client-240/deob-v2.py audit
python3 tools/client-240/deob-v2.py refs classDF classDG
python3 tools/client-240/deob-v2.py dossier classDF
```

`index`, `audit`, and `refs` do not decompile anything. `dossier` is the explicit opt-in source-rendering path.

### `decompile-ensemble.py`

A focused source renderer that keeps generated source disposable. It renders requested evidence through the pinned decompilers, records command/tool identity, preserves failures independently, and writes a comparison manifest. It does not bless either renderer as correct.

## Evidence contract

Every future naming or behavior claim should be able to answer these questions:

| Question | Authority |
| --- | --- |
| What symbol is this? | semantic stable name plus preserved obfuscated identity |
| Who references it? | ASM bytecode reference graph |
| What does the shipped client execute? | original gamepack bytecode |
| Was this method transformed for analysis? | original-vs-semantic drift audit |
| What readable Java do the tools reconstruct? | Vineflower and CFR, independently |
| What readable name do we assign? | tiny-v2 shard plus evidence ledger |
| Does the rename alter bytecode? | existing round-trip rename-only proof |

## Method fingerprints

Fingerprints are research accelerators, not naming evidence tiers by themselves.

A method gets two hashes:

- `strict_hash`: opcode and operand stream as represented in that jar. Useful for cache invalidation and exact same-jar checks.
- `canonical_hash`: an identity-normalized stream. In-repo class/member references are represented by preserved obfuscated identities when available; constant-pool indices and decompiler syntax are absent.

The canonical stream includes:

- opcode;
- primitive immediates and stable literal values;
- field and method reference kind, canonical owner, canonical member identity, normalized descriptor;
- type instructions with canonical type identity;
- invokedynamic name/descriptor and bootstrap-handle identity;
- jump target expressed as a real-instruction index;
- lookup/table switch keys and destinations;
- multi-array dimensions;
- exception table start/end/handler indices and caught type.

Line numbers, local-variable names, stack-map frames, source file names, and constant-pool slot numbers are excluded.

## Original-versus-semantic drift classification

The audit should not merely say `different`. Each matched method receives metrics for both sides and zero or more flags:

- `instruction-count-changed`
- `branch-shape-changed`
- `switch-shape-changed`
- `try-catch-changed`
- `invoke-set-changed`
- `field-set-changed`
- `constant-set-changed`
- `canonical-bytecode-changed`
- `missing-original`
- `missing-semantic`

For protocol and entity-sync work, any control-flow flag means the original method must be consulted before specifying behavior.

## Renaming policy changes

The existing evidence tiers remain. v2 changes how candidates are *screened* before a human promotes them:

1. T1/T2 evidence remains strongest.
2. T3 carry-forward requires descriptor compatibility plus canonical structural compatibility where both revisions can provide it.
3. T4 behavior names must cite the original-bytecode identity when the drift audit marks the semantic method transformed.
4. A decompiler disagreement lowers confidence until reconciled against bytecode.
5. Reference-set ownership comes from the bytecode graph, not `grep -rlw`.
6. `client` and `rl5` remain unowned unless explicitly assigned by the wave.
7. No automated score is allowed to write a mapping shard directly.

## Why not write a new decompiler?

A JVM decompiler is a large compiler-reconstruction project: exception-region recovery, expression structuring, type inference, synthetic pattern recognition, switch recovery, monitor handling and malformed/hostile bytecode all matter. The repository already pins two mature and independent decompilers. Reimplementing that machinery would add risk while leaving the more important source-of-truth problem unsolved.

The better investment is an independent bytecode evidence layer plus a deterministic ensemble around the decompilers already shipped.

## Compatibility with the existing pipeline

v2 is additive:

- existing mapping shards remain tiny-v2;
- existing ledger rows remain the naming authority;
- `remap.sh` remains the rename mechanism;
- `verify.sh` remains the rename-only gate when the user chooses to run it;
- generated v2 indexes and decompiler outputs stay under cache paths;
- `references/` remains read-only;
- no Rust implementation is generated from this tooling.

During evaluation, `Client240Tool.java`, the current remapper, and current verification flow remain untouched. Once v2 proves useful, selected v2 checks can be promoted into the normal wave procedure in a separate checkpoint.