# Client Deob Workbench

Standalone Java tooling for making the OSRS client explainable without confusing a cleaned/decompiled representation with the client that actually shipped.

## Core rule

> Original bytecode defines behavior. Other sources identify, explain, correlate, and name it.

The workbench is intentionally separate from the Rust workspace. Its job is to build a traceable evidence graph from exact gamepack instructions to structural identities, control flow, value flow, packet reads, decoder arithmetic, cross-revision lineage, decompiler views, RuneLite semantic vocabulary, and eventually human-reviewed readable names.

## Planned evidence layers

1. **original-exact**: untouched gamepack bytecode and stable instruction identities.
2. **identity**: structural aliases and preserved obfuscated metadata.
3. **analysis**: CFG, symbolic provenance, decoder proofs, packet timelines, virtual families, and revision matching.
4. **semantic**: RuneLite/API/domain vocabulary and human-reviewed mappings.
5. **presentation**: JSON/TSV evidence, decompiler source, and an offline workbench UI.

No analysis layer is allowed to become behavioral authority merely because it is easier to read.

## Project layout

```text
deob-workbench/
  pom.xml
  src/main/java/dev/neosback/deob/
    cli/         command surface
    corpus/      immutable jar/class corpus
    identity/    exact symbol and instruction identities
    analysis/    evidence-producing passes
    evidence/    provenance/confidence model
    io/          deterministic serializers
    revision/    revision profiles and source manifests
    semantic/    RuneLite/human semantic evidence
```

## Build

This project uses Java 17 and Maven. The packaged artifact is intended to be a self-contained CLI jar.

```bash
cd deob-workbench
mvn package
java -jar target/client-deob-workbench-0.1.0-SNAPSHOT.jar --help
```

Those commands are documentation only until validation is explicitly run on the development branch.

## Non-goals

- Reimplementing a Java decompiler.
- Running a normalized jar as though it were the original client.
- Deleting unreachable/unused code from the authority corpus.
- Automatically promoting guessed names into the revision-240 mapping ledger.
- Mirroring RuneLite or Devious Client architecture wholesale.
