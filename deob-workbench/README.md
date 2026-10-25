# Client Deob Workbench

Standalone Java tooling for making the OSRS client explainable without confusing a cleaned/decompiled representation with the client that actually shipped.

## Core rule

> Original bytecode defines behavior. Other sources identify, explain, correlate, and name it.

The workbench is intentionally separate from the Rust workspace. Its job is to build a traceable evidence graph from exact gamepack instructions to structural identities, control flow, hierarchy, value flow, decoder arithmetic, packet reads, cross-revision lineage, decompiler views, RuneLite semantic vocabulary, and eventually human-reviewed readable names.

## Implemented foundation

- Immutable exact JAR/class corpus.
- Shared class, field, method, and opcode-instruction identities.
- Exact and rename-insensitive structural method fingerprints.
- Bytecode class/member metric index.
- Exact basic-block CFG with branch, switch, fallthrough, and exception edges.
- Dominator sets, immediate dominators, and dominator-proven natural loops.
- Exact class hierarchy and override-equivalent virtual method families.
- Stack/local value provenance using ASM source analysis.
- Integer/long multiplier evidence with same-field inverse-pair proof.
- Conservative structural packet-read discovery.
- Packet-read value timelines from exact read calls to dependent transforms, field writes, branches, calls, and returns.
- First-pass explainable cross-revision method lineage scoring with explicit ambiguity.
- Revision-240 evidence-source manifest.
- JSON CLI surfaces for indexing and focused analysis.

Nothing here has been promoted to behavioral truth beyond the original bytecode.

## Evidence layers

1. **original-exact**: untouched gamepack bytecode and stable instruction identities.
2. **identity**: structural aliases and preserved obfuscated metadata.
3. **analysis**: CFG, hierarchy, symbolic provenance, decoder proofs, packet timelines, virtual families, and revision matching.
4. **semantic**: RuneLite/API/domain vocabulary and human-reviewed mappings.
5. **presentation**: JSON/TSV evidence, decompiler source, and an offline workbench UI.

## Project layout

```text
deob-workbench/
  config/       revision evidence-source manifests
  docs/         architecture and pass contracts
  src/main/java/dev/neosback/deob/
    cli/         command surface
    corpus/      immutable jar/class corpus
    identity/    exact symbol and instruction identities
    bytecode/    shared exact-bytecode coordinates
    analysis/    evidence-producing passes
    io/          deterministic serializers
    revision/    revision profiles and source manifests
```

## Build

This project uses Java 17 and Maven. The packaged artifact is intended to be a self-contained CLI jar.

```bash
cd deob-workbench
mvn package
java -jar target/client-deob-workbench-0.1.0-SNAPSHOT.jar --help
```

Those commands are documentation only until validation is explicitly run on the development branch.

## CLI

From `deob-workbench/`:

```bash
# Inspect configured evidence sources
java -jar target/client-deob-workbench-0.1.0-SNAPSHOT.jar sources

# Index exact bytecode
java -jar target/client-deob-workbench-0.1.0-SNAPSHOT.jar index \
  --jar ../references/gamepacks/osrs-240/injected-client-1.12.39.jar \
  --out .cache/rev240/original-index.json

# Build an exact method CFG
java -jar target/client-deob-workbench-0.1.0-SNAPSHOT.jar cfg \
  --jar ../references/gamepacks/osrs-240/injected-client-1.12.39.jar \
  --class aa --method bb --desc '(I)V' \
  --out .cache/rev240/cfg-aa-bb.json

# Prove dominators and natural loops over that exact CFG
java -jar target/client-deob-workbench-0.1.0-SNAPSHOT.jar cfg-structure \
  --jar ../references/gamepacks/osrs-240/injected-client-1.12.39.jar \
  --class aa --method bb --desc '(I)V' \
  --out .cache/rev240/cfg-structure-aa-bb.json

# Index class hierarchy and virtual dispatch families
java -jar target/client-deob-workbench-0.1.0-SNAPSHOT.jar hierarchy \
  --jar ../references/gamepacks/osrs-240/injected-client-1.12.39.jar \
  --out .cache/rev240/hierarchy.json

# Trace stack/local producers for one method
java -jar target/client-deob-workbench-0.1.0-SNAPSHOT.jar provenance \
  --jar ../references/gamepacks/osrs-240/injected-client-1.12.39.jar \
  --class aa --method bb --desc '(I)V' \
  --out .cache/rev240/provenance-aa-bb.json

# Discover multiplier sites and prove inverse read/write pairs
java -jar target/client-deob-workbench-0.1.0-SNAPSHOT.jar decoders \
  --jar ../references/gamepacks/osrs-240/injected-client-1.12.39.jar \
  --out .cache/rev240/decoders.json

# Discover byte-buffer read candidates without assigning semantic read names
java -jar target/client-deob-workbench-0.1.0-SNAPSHOT.jar packet-reads \
  --jar ../references/gamepacks/osrs-240/injected-client-1.12.39.jar \
  --out .cache/rev240/packet-reads.json

# Follow each discovered read call through exact value provenance to its consumers
java -jar target/client-deob-workbench-0.1.0-SNAPSHOT.jar packet-timelines \
  --jar ../references/gamepacks/osrs-240/injected-client-1.12.39.jar \
  --out .cache/rev240/packet-timelines.json

# Rank method lineage between two revisions; this never writes mappings
java -jar target/client-deob-workbench-0.1.0-SNAPSHOT.jar lineage \
  --source path/to/neighbor.jar \
  --target ../references/gamepacks/osrs-240/injected-client-1.12.39.jar \
  --out .cache/rev240/lineage.json
```

## Packet evidence policy

Packet read discovery is structural. It records byte-array loads, candidate offset fields, masks, shifts, branches, return shape, and a conservative byte-consumption shape such as `FIXED_ONE_BYTE` or `VARIABLE_OR_LOOPING`. It does **not** automatically rename a method to `readUnsignedByte`, `readUnsignedShort`, or any transformed variant.

`PacketTimelineAnalyzer` then treats calls to those candidates as roots and follows exact producer/consumer provenance into dependent transforms, locals, field writes, branches, calls, and returns. Methods that ASM cannot analyze are listed explicitly as skipped instead of disappearing from the report.

## CFG proof policy

A lexical backward edge is not automatically a loop. `CfgStructureAnalyzer` computes dominators first. A natural loop is emitted only when the candidate loop header dominates the back-edge source. Exception and legacy subroutine edges are excluded from natural-loop construction.

## Decoder confidence

A multiplication site is reported as `CANDIDATE` when exact value provenance links it to a field read or write and a literal multiplier. A field becomes `PROVEN_INVERSE_PAIR` only when a read multiplier and write multiplier for that same field multiply to one modulo `2^32` or `2^64`.

The analyzer does not rewrite multipliers or normalize bytecode. It reports evidence only.

## Lineage confidence

The first lineage matcher filters on normalized JVM descriptor shape and static/constructor compatibility, then scores candidates from rename-insensitive bytecode fingerprints, instruction/control-flow metrics, call/field-access shape, and simple owning-class structure. The report includes the score breakdown and runner-up score.

Close candidates are reported as `AMBIGUOUS`. No lineage result is promoted into tiny-v2 mappings automatically. Exact CFG structure, virtual-family context, packet roles, and deeper value-flow fingerprints are planned enrichments to this first-pass scorer.

## Source roles for rev 240

- **Original 1.12.39**: behavioral authority.
- **Semantic 1.12.39**: identity/readability overlay only.
- **rl-suite 1.12.34 through 1.12.36**: neighboring rev-240 structural lineage evidence.
- **Historical RuneLite near 1.12.39**: semantic vocabulary once an exact commit is pinned.
- **Devious Client deobfuscator**: BSD-licensed algorithm reference; port useful algorithms onto the ASM-native model rather than importing a second bytecode IR.

See `docs/ARCHITECTURE.md` for the pass model and next implementation layers.

## Non-goals

- Reimplementing a Java decompiler.
- Running a normalized jar as though it were the original client.
- Deleting unreachable/unused code from the authority corpus.
- Automatically promoting guessed names into the revision-240 mapping ledger.
- Mirroring RuneLite or Devious Client architecture wholesale.
