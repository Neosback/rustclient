# Client Deob Workbench

Standalone Java tooling for making the OSRS client explainable without confusing a cleaned/decompiled representation with the client that actually shipped.

## Core rule

> Original bytecode defines behavior. Other sources identify, explain, correlate, and name it.

The workbench is intentionally separate from the Rust workspace. Its job is to build a traceable evidence graph from exact gamepack instructions to structural identities, control flow, hierarchy, value flow, decoder arithmetic, packet reads, protocol entry points, cross-revision lineage, RuneLite semantic vocabulary, and eventually human-reviewed readable names.

## Implemented foundation

- Immutable exact JAR/class corpus.
- Shared class, field, method, and opcode-instruction identities.
- Exact and rename-insensitive structural method fingerprints.
- Bytecode class/member metric index.
- Exact basic-block CFG with branch, switch, fallthrough, and exception edges.
- Dominators, immediate dominators, and natural-loop evidence.
- Exact class hierarchy and override-equivalent virtual method families.
- Stack/local value provenance using ASM source analysis.
- Integer/long multiplier evidence with same-field inverse-pair proof.
- Structural packet-read discovery and read-to-consumer timelines.
- Explainable cross-revision method lineage scoring with ambiguity reporting.
- Buffer-family, framing/cipher-like, packet-handler, and player-update hypothesis ranking.
- Revision-240 evidence-source manifest.
- JSON CLI surfaces for indexing and focused analysis.

Nothing here has been promoted to behavioral truth beyond the original bytecode.

## Evidence layers

1. **original-exact**: untouched gamepack bytecode and stable instruction identities.
2. **identity**: structural aliases and preserved obfuscated metadata.
3. **analysis**: CFG, hierarchy, symbolic provenance, decoder proofs, packet timelines, virtual families, protocol candidates, and revision matching.
4. **semantic**: RuneLite/API/domain vocabulary and human-reviewed mappings.
5. **presentation**: JSON/TSV evidence, decompiler source, and an offline workbench UI.

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
java -jar target/client-deob-workbench-0.1.0-SNAPSHOT.jar sources

java -jar target/client-deob-workbench-0.1.0-SNAPSHOT.jar index \
  --jar ../references/gamepacks/osrs-240/injected-client-1.12.39.jar \
  --out .cache/rev240/original-index.json

java -jar target/client-deob-workbench-0.1.0-SNAPSHOT.jar cfg \
  --jar ../references/gamepacks/osrs-240/injected-client-1.12.39.jar \
  --class aa --method bb --desc '(I)V' \
  --out .cache/rev240/cfg-aa-bb.json

java -jar target/client-deob-workbench-0.1.0-SNAPSHOT.jar cfg-structure \
  --jar ../references/gamepacks/osrs-240/injected-client-1.12.39.jar \
  --class aa --method bb --desc '(I)V' \
  --out .cache/rev240/cfg-structure-aa-bb.json

java -jar target/client-deob-workbench-0.1.0-SNAPSHOT.jar hierarchy \
  --jar ../references/gamepacks/osrs-240/injected-client-1.12.39.jar \
  --out .cache/rev240/hierarchy.json

java -jar target/client-deob-workbench-0.1.0-SNAPSHOT.jar provenance \
  --jar ../references/gamepacks/osrs-240/injected-client-1.12.39.jar \
  --class aa --method bb --desc '(I)V' \
  --out .cache/rev240/provenance-aa-bb.json

java -jar target/client-deob-workbench-0.1.0-SNAPSHOT.jar decoders \
  --jar ../references/gamepacks/osrs-240/injected-client-1.12.39.jar \
  --out .cache/rev240/decoders.json

java -jar target/client-deob-workbench-0.1.0-SNAPSHOT.jar packet-reads \
  --jar ../references/gamepacks/osrs-240/injected-client-1.12.39.jar \
  --out .cache/rev240/packet-reads.json

java -jar target/client-deob-workbench-0.1.0-SNAPSHOT.jar packet-timelines \
  --jar ../references/gamepacks/osrs-240/injected-client-1.12.39.jar \
  --out .cache/rev240/packet-timelines.json

java -jar target/client-deob-workbench-0.1.0-SNAPSHOT.jar lineage \
  --source ../references/neighbor.jar \
  --target ../references/gamepacks/osrs-240/injected-client-1.12.39.jar \
  --out .cache/rev240/lineage.json

# Rank protocol entry points. "player update" remains a hypothesis until semantic evidence confirms it.
java -jar target/client-deob-workbench-0.1.0-SNAPSHOT.jar protocol \
  --jar ../references/gamepacks/osrs-240/injected-client-1.12.39.jar \
  --out .cache/rev240/protocol-evidence.json
```

## Protocol evidence

`protocol` combines existing exact-bytecode analyses instead of inventing protocol names from decompiler text. It emits:

- ranked buffer-family candidates from packet-read ownership, byte-array/offset fields, and inheritance;
- framing/cipher-like method candidates from packet-read calls, byte-array access, integer-return calls, state fields, and arithmetic;
- packet-handler candidates from multiple packet reads plus dependent transforms, branches, field writes, calls, and natural loops;
- player-update dossier candidates containing exact field-write, branch, and call instruction sites.

`playerUpdateCandidates` are explicitly **hypotheses**. Dense packet/update behavior alone does not prove that a method is player synchronization, NPC synchronization, or another state-update handler. Historical RuneLite semantics, neighboring revisions, packet tables, and human review are expected to confirm the role.

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
- Calling structural framing evidence "ISAAC" without corroborating evidence.
- Calling a dense update handler "player update" without corroborating evidence.
- Mirroring RuneLite or Devious Client architecture wholesale.
