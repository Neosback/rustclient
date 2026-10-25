# Client Deob Workbench

Standalone Java tooling for making the OSRS client explainable without confusing a cleaned/decompiled representation with the client that actually shipped.

## Core rule

> Original bytecode defines behavior. Other sources identify, explain, correlate, and name it.

The workbench is intentionally separate from the Rust workspace. Its job is to build a traceable evidence graph from exact gamepack instructions to structural identities, control flow, value flow, packet reads, decoder arithmetic, cross-revision lineage, decompiler views, RuneLite semantic vocabulary, and eventually human-reviewed readable names.

## Implemented foundation

- Immutable exact JAR/class corpus.
- Shared class, field, method, and opcode-instruction identities.
- Exact and rename-insensitive structural method fingerprints.
- Bytecode class/member metric index.
- Exact basic-block CFG with branch, switch, fallthrough, and exception edges.
- Revision-240 evidence-source manifest.
- JSON CLI surfaces for indexing and CFG inspection.

Nothing here has been promoted to behavioral truth beyond the original bytecode.

## Evidence layers

1. **original-exact**: untouched gamepack bytecode and stable instruction identities.
2. **identity**: structural aliases and preserved obfuscated metadata.
3. **analysis**: CFG, symbolic provenance, decoder proofs, packet timelines, virtual families, and revision matching.
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

## Initial CLI

From `deob-workbench/`:

```bash
# Inspect configured evidence sources
java -jar target/client-deob-workbench-0.1.0-SNAPSHOT.jar sources

# Index exact bytecode
java -jar target/client-deob-workbench-0.1.0-SNAPSHOT.jar index \
  --jar ../references/gamepacks/osrs-240/injected-client-1.12.39.jar \
  --out .cache/rev240/original-index.json

# Build an exact method CFG. Descriptor must be the exact JVM descriptor.
java -jar target/client-deob-workbench-0.1.0-SNAPSHOT.jar cfg \
  --jar ../references/gamepacks/osrs-240/injected-client-1.12.39.jar \
  --class aa --method bb --desc '(I)V' \
  --out .cache/rev240/cfg-aa-bb.json
```

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
