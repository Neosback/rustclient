# Current mapping baseline, 2026-10-25

Status: mechanical verification **PASS**. This supersedes the 179 validation problems recorded in
the [earlier quality baseline](verification/decompiler-quality-baseline-20261025.md). It does not
upgrade the semantic confidence of existing names or complete the client deobfuscation.

## Repair

The pinned semantic bytecode index supplied unambiguous full JVM descriptors for 156 entries in
`03-actor-motion.tiny` whose descriptor column incorrectly contained the member kind (`f` or `m`).
Only uniquely identified existing members were repaired; readable names and source symbols were
retained. The [machine record](verification/mapping-repair-20261025.json) lists each descriptor change,
the semantic/index/script hashes, and before/after hashes for every affected mapping/ledger shard.

Actor/Player/NPC now belong to shard 03, including their existing seed API entries. WorldView belongs
to shard 06. Coord/classKY belongs to shard 13, including `setPacked` from shard 04; identical duplicate
entries were consolidated and their evidence retained. These are ownership repairs, not new names.
The merged map and ledger were regenerated. A second repair plan was a byte-preserving no-op.

## Executed verification

[`mapping-repair-20261025.md`](verification/mapping-repair-20261025.md) records:

- 18 shards and the merged map validate against all 735 semantic classes.
- 943 name/ledger entries agree; 1,032 mapping records include identity class blocks.
- Forward/reverse remapping produces identical normalized bytecode in all 735 classes.
- 27,052 obfuscated-name/signature annotation lines remain identical.
- 735 Java files regenerate with zero explicit failed-body markers; three enum-sugar diagnostics
  remain. The separate syntax pass still finds six problematic files in this raw regenerated view.
- Original `references/` content is unchanged.

Generic-symbol coverage is 25/603 classes (4.1%), 638/7,030 fields (9.1%) and 280/14,324 methods (2.0%).
These are naming counts, not Rust port or behavior-parity percentages. The original reading bundle
still has five unresolved Java files after its checked CFR fallback; see [decompiler quality](DECOMPILER-QUALITY.md).

## Reproduce

```bash
python3 tools/client-240/workbench.py index \
  --jar docs/reference/client-240/inputs/injected-client-1.12.39-deob-semantic.jar \
  --out .cache/client-240/mapping-repair/semantic-index.json
python3 tools/deob/repair-mapping-baseline.py
# --apply is only needed on a baseline with these known defects.
bash tools/client-240/verify.sh mapping-baseline-check
```

The repair CLI plans into the ignored cache by default, rejects ambiguous identity/descriptor or
readable-name conflicts, and detects concurrent edits before applying. It does not propose names.
Future naming waves must still pass the baseline, retain class ownership and provide source-backed
ledger evidence; this mechanical repair is not a substitute for that review.
