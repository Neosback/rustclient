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
        -> broad actor behavior clusters
        -> human-reviewed semantic mappings
```

## Interprocedural paths

`sync-paths` starts from rev-specific dispatch-supported sync roots and follows only methods present in the exact target corpus. Each reachable method retains:

- the original sync root and role;
- packet names supporting that root;
- shortest discovered call depth;
- exact call-site `InstructionId` path;
- bit-access calls, packet-read calls, field writes, branches, and downstream in-corpus calls.

Traversal is bounded with `--max-depth` and cycles are suppressed per root. This is search-space expansion, not proof that every reachable method is entity synchronization.

## Mask fingerprints

`mask-fingerprints` canonicalizes the two immediate CFG successors of each detected `IAND constant -> conditional branch` mask test. Exact member names remain in evidence lists, while the structural hash is rename-independent and is based on opcode shape, normalized descriptors, and integer constants.

The two successor hashes are sorted before the combined hash is formed, so branch orientation does not have to be guessed. The mask constant is recorded separately and is not part of the combined structural hash, allowing cross-revision alignment even when mask-bit assignments move.

## Actor behavior clusters

`actor-clusters` scores broad hypotheses from mask-block evidence. Current labels include:

- `ANIMATION_FRAME_CYCLE`
- `MOVEMENT_PATH`
- `ORIENTATION_OR_TARGET`
- `OVERHEAD_TEXT`
- `SPOT_ANIMATION`
- `HEALTH_OR_HITSPLAT`

These labels are deliberately coarse. A high animation score means the block has evidence such as multiple packet reads, multiple integer state writes, and frame/cycle-like zero resets. It does not rename any field to `animation`, `animationFrame`, or `animationFrameCycle` by itself.

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

java -jar target/client-deob-workbench-0.1.0-SNAPSHOT.jar actor-clusters \
  --jar ../references/gamepacks/osrs-240/injected-client-1.12.39.jar \
  --server-packets /path/to/server-packets.tsv \
  --out .cache/rev240/actor-clusters.json
```

## Next semantic step

The next useful evidence fusion is to combine each mask fingerprint with neighboring-revision lineage and a time-appropriate RuneLite semantic snapshot. That should let individual blocks move from broad clusters toward specific reviewed claims such as animation sequence/delay handling, target index, orientation, movement queue state, overhead text, spot animation, or hitsplat/health behavior.
