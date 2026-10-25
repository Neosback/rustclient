# Client Deob Workbench

Standalone Java tooling for making the OSRS client explainable without confusing a cleaned/decompiled representation with the client that actually shipped.

## Core rule

> Original bytecode defines behavior. Other sources identify, explain, correlate, and name it.

The workbench is intentionally separate from the Rust workspace. Its job is to build a traceable evidence graph from exact gamepack instructions to structural identities, control flow, hierarchy, value flow, decoder arithmetic, packet reads, protocol entry points, cross-revision lineage, RuneLite semantic vocabulary, and eventually human-reviewed readable names.

## Implemented foundation

- Immutable exact JAR/class corpus and stable class/member/opcode-instruction identities.
- Exact and rename-insensitive structural method fingerprints.
- Exact CFG, dominators, immediate dominators, and natural-loop evidence.
- Exact class hierarchy and virtual method families.
- Stack/local value provenance and proven modular decoder pairs.
- Structural packet-read discovery and read-to-consumer timelines.
- Explainable cross-revision method lineage with ambiguity reporting.
- Buffer-family, framing/cipher-like, packet-handler, and player-update hypothesis ranking.
- Parser for rl-suite-style rev-240 server/client packet TSV profiles.
- Structural bit-access discovery.
- Rev-240 `PLAYER_INFO` / `NPC_INFO_*` dispatch correlation and player-like/NPC-like role scoring.
- Update-mask test evidence plus mask-controlled Actor-state vocabulary hints.
- JSON CLI surfaces for focused analysis.

Nothing here has been promoted to behavioral truth beyond the original bytecode.

## Evidence layers

1. **original-exact**: untouched gamepack bytecode and stable instruction identities.
2. **identity**: structural aliases and preserved obfuscated metadata.
3. **analysis**: CFG, hierarchy, provenance, decoder proofs, packet timelines, protocol candidates, sync roles, mask evidence, and revision matching.
4. **semantic**: packet-profile names, RuneLite/API/domain vocabulary, and human-reviewed mappings.
5. **presentation**: JSON/TSV evidence, decompiler source, and an offline workbench UI.

## Build

This project uses Java 17 and Maven. The packaged artifact is intended to be a self-contained CLI jar.

```bash
cd deob-workbench
mvn package
java -jar target/client-deob-workbench-0.1.0-SNAPSHOT.jar --help
```

Those commands are documentation only until validation is explicitly run on the development branch.

## Core CLI

```bash
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

java -jar target/client-deob-workbench-0.1.0-SNAPSHOT.jar protocol \
  --jar ../references/gamepacks/osrs-240/injected-client-1.12.39.jar \
  --out .cache/rev240/protocol-evidence.json

java -jar target/client-deob-workbench-0.1.0-SNAPSHOT.jar lineage \
  --source ../references/neighbor.jar \
  --target ../references/gamepacks/osrs-240/injected-client-1.12.39.jar \
  --out .cache/rev240/lineage.json
```

## Rev-240 packet and sync CLI

The packet profile is external evidence. The parser consumes the rl-suite rev-240 TSV shape but the workbench does not vendor those tables or make them behavioral authority.

```bash
java -jar target/client-deob-workbench-0.1.0-SNAPSHOT.jar packet-profile \
  --server-packets /path/to/server-packets.tsv \
  --client-packets /path/to/client-packets.tsv \
  --out .cache/rev240/packet-profile.json

java -jar target/client-deob-workbench-0.1.0-SNAPSHOT.jar bit-access \
  --jar ../references/gamepacks/osrs-240/injected-client-1.12.39.jar \
  --out .cache/rev240/bit-access.json

java -jar target/client-deob-workbench-0.1.0-SNAPSHOT.jar sync-candidates \
  --jar ../references/gamepacks/osrs-240/injected-client-1.12.39.jar \
  --server-packets /path/to/server-packets.tsv \
  --out .cache/rev240/sync-candidates.json

java -jar target/client-deob-workbench-0.1.0-SNAPSHOT.jar update-masks \
  --jar ../references/gamepacks/osrs-240/injected-client-1.12.39.jar \
  --server-packets /path/to/server-packets.tsv \
  --out .cache/rev240/update-masks.json
```

### Packet-table correlation

The rev-240 server table currently identifies `PLAYER_INFO` and multiple `NPC_INFO_*` entries. `Rev240SyncAnalyzer` correlates those external packet IDs with exact `TABLESWITCH`/`LOOKUPSWITCH` cases in the target gamepack, records the target basic block, and ranks its direct calls as `PLAYER_LIKE`, `NPC_LIKE`, `MIXED`, or `UNKNOWN`.

This is stronger than a generic dense-handler score, but still not behavioral authority: packet names come from an external profile and the direct call may be a helper rather than the full handler.

### Bit access and update masks

`BitAccessAnalyzer` requires exact byte-buffer evidence plus shift-by-3 behavior and then scores mutable integer state, masks, 7/8 constants, branches, and loops. It does not automatically rename candidates to `readBits`, `importIndex`, or `exportIndex`.

`UpdateMaskAnalyzer` looks for `IAND constant -> conditional branch` idioms inside sync-role candidates. It records both immediate CFG successors and their exact field writes, rather than assuming which branch corresponds to a set mask bit.

Actor-state semantic hints are intentionally broad vocabulary only. For example, an integer field shared by player-like and NPC-like mask paths may receive candidates such as `animation`, `orientation`, `targetIndex`, or frame/cycle state. That is not enough to rename the field. Historical RuneLite semantics, lineage, read variants, surrounding calls, and human review must narrow it first.

## Source roles for rev 240

- **Original 1.12.39**: behavioral authority.
- **Semantic 1.12.39**: identity/readability overlay only.
- **rl-suite neighboring rev-240 jars**: structural lineage evidence.
- **rl-suite rev-240 packet TSVs**: external protocol-name/profile evidence, not behavior authority.
- **Historical RuneLite near 1.12.39**: semantic vocabulary once an exact commit is pinned.
- **Devious Client deobfuscator**: algorithm reference; useful algorithms are adapted to the ASM-native model rather than introducing a second bytecode truth.

See `docs/ARCHITECTURE.md` for the pass model and next implementation layers.

## Non-goals

- Reimplementing a Java decompiler.
- Running a normalized jar as though it were the original client.
- Deleting unreachable/unused code from the authority corpus.
- Automatically promoting guessed names into the revision-240 mapping ledger.
- Calling structural framing evidence `ISAAC` without corroboration.
- Calling bit-oriented evidence `readBits` without corroboration.
- Calling a dense update path player or NPC sync without packet-profile/dispatch evidence.
- Treating broad Actor vocabulary hints as final field names.
