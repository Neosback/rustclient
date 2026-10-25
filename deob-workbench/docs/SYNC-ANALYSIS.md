# Rev-240 Sync Analysis

The sync analysis path narrows exact revision-240 bytecode from packet dispatch to candidate actor-state semantics without turning external packet names or heuristics into behavioral authority.

## Pipeline

```text
server packet profile
    + exact target bytecode
        -> PLAYER_INFO / NPC_INFO_* switch cases
        -> direct sync roots
        -> bounded interprocedural call paths
        -> bit/packet reads
        -> update-mask tests
        -> rename-independent mask fingerprints
        -> cross-build mask lineage
        -> broad actor behavior clusters
        -> pinned RuneLite semantic vocabulary
        -> ranked semantic field proposals
        -> human-reviewed mappings
```

## Interprocedural paths

`sync-paths` starts from rev-specific dispatch-supported sync roots and follows only methods present in the exact target corpus. Each reachable method retains the original sync root and role, supporting packet names, shortest discovered call depth, exact call-site `InstructionId` path, and structural packet/field/branch metrics.

Traversal is bounded with `--max-depth` and cycles are suppressed per root. Reachability narrows the search space; it does not prove that every reachable helper is entity synchronization.

## Mask fingerprints

`mask-fingerprints` canonicalizes the two immediate CFG successors of each detected `IAND constant -> conditional branch` mask test. The structural hash is rename-independent and uses opcode shape, normalized descriptors, and integer constants.

The two successor hashes are sorted before the combined hash is formed, so branch orientation does not have to be guessed. The mask constant is recorded separately and is not part of the combined structural hash.

## Cross-build mask lineage

`mask-lineage` aligns source and target mask blocks using visible score contributions. Exact combined structural hashes are strongest evidence. Supporting signals include sync role, successor count, shared successor hashes, field descriptor shape, packet-read-count shape, downstream-call descriptor shape, and the raw mask constant.

The raw mask constant is intentionally weak evidence. A semantic block can still align strongly when Jagex moves the behavior to another mask bit. Close runner-up scores are reported as `AMBIGUOUS`; weak matches remain `LOW` or `UNMATCHED`.

Use exact source and target gamepacks when behavioral comparison matters. A transformed/deobfuscated neighbor may still be useful as structural evidence, but must not silently become behavioral authority.

## Actor behavior clusters

`actor-clusters` scores deliberately coarse labels including `ANIMATION_FRAME_CYCLE`, `MOVEMENT_PATH`, `ORIENTATION_OR_TARGET`, `OVERHEAD_TEXT`, `SPOT_ANIMATION`, and `HEALTH_OR_HITSPLAT`.

A high animation score means the block has evidence such as multiple packet reads, multiple integer state writes, and frame/cycle-like zero resets. It does not identify which field is animation id versus frame versus cycle.

## Semantic dossier

`actor-semantic-dossier` fuses target behavior clusters, cross-build mask lineage, and the pinned RuneLite 1.12.36 Actor vocabulary. A field proposal is only emitted when the vocabulary category is compatible with the behavior cluster and the vocabulary entry has an exact JVM descriptor hint matching the target field.

Lineage adds confidence but is not mandatory. Every proposal records the semantic source id/commit and the evidence contributions that produced its score. No proposal is written to the mapping ledger automatically.

## CLI examples

```bash
java -jar target/client-deob-workbench-0.1.0-SNAPSHOT.jar sync-paths \
  --jar ../references/gamepacks/osrs-240/injected-client-1.12.39.jar \
  --server-packets /path/to/server-packets.tsv \
  --max-depth 3 \
  --out .cache/rev240/sync-paths.json

java -jar target/client-deob-workbench-0.1.0-SNAPSHOT.jar mask-fingerprints \
  --jar ../references/gamepacks/osrs-240/injected-client-1.12.39.jar \
  --server-packets /path/to/server-packets.tsv \
  --out .cache/rev240/mask-fingerprints.json

java -jar target/client-deob-workbench-0.1.0-SNAPSHOT.jar mask-lineage \
  --source-jar /path/to/exact-source-client.jar \
  --source-server-packets /path/to/source-server-packets.tsv \
  --target-jar ../references/gamepacks/osrs-240/injected-client-1.12.39.jar \
  --target-server-packets /path/to/rev240-server-packets.tsv \
  --out .cache/rev240/mask-lineage.json

java -jar target/client-deob-workbench-0.1.0-SNAPSHOT.jar actor-clusters \
  --jar ../references/gamepacks/osrs-240/injected-client-1.12.39.jar \
  --server-packets /path/to/server-packets.tsv \
  --out .cache/rev240/actor-clusters.json

java -jar target/client-deob-workbench-0.1.0-SNAPSHOT.jar actor-semantic-dossier \
  --source-jar /path/to/exact-source-client.jar \
  --source-server-packets /path/to/source-server-packets.tsv \
  --target-jar ../references/gamepacks/osrs-240/injected-client-1.12.39.jar \
  --target-server-packets /path/to/rev240-server-packets.tsv \
  --vocabulary config/runelite-1.12.36-actor-vocabulary.json \
  --out .cache/rev240/actor-semantic-dossier.json
```

## Promotion threshold

A semantic proposal is still too weak to rename when its behavior cluster is broad, its cross-build lineage is ambiguous/unmatched, multiple same-type fields receive the same vocabulary term, or the value-flow/read/call evidence does not distinguish those fields. Those cases should remain explicit candidates for the next evidence pass rather than being forced into a mapping.
