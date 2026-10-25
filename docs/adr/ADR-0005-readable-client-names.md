# ADR-0005: Readable Client Names Are an Evidence-Backed Mapping Overlay

**Status:** Proposed

## Context

The revision-240 client evidence under `references/rl-suite-240/` has stable but unreadable names
(`classDF.method3015`, `field1269`). Research and ports cite those symbols, and
`docs/reference/rs240-port/` forbids guessed renames. At the same time, accurate ports need readable,
original-mapped descriptions of client behavior.

`references/` is read-only evidence (root `AGENTS.md` rule 11), so readable names cannot be produced by
editing the reference sources, and hand-edited decompiler output cannot prove that only names changed.

## Decision

1. Readable names live in **tiny-v2 mapping shards** under `docs/reference/client-240/mappings/clusters/`,
   one shard per cluster. The left column is the stable rl-suite name, so existing citations keep working
   and remain the primary key. No Java source is hand-edited.
2. Each renamed symbol has a **ledger row** with an evidence tier (T1 API getter or injector event call site, T2 packet/protocol
   table, T3 235 carry-forward, T4 behavior-derived, TR runtime-observed in the live pinned client through the
   read-only probes in `tools/parity`), a confidence and cited evidence. Names without evidence are not added.
   Runtime probes never alter the `original-exact` jar and their traces are evidence about one session only. The reference
   bridge may deliver canvas mouse/key events (an input driver) but never writes game state directly.
3. Readable sources are **regenerated** from the semantic jar by tiny-remapper and Vineflower into an
   untracked cache. `tools/client-240/verify.sh` proves rename-only by remapping forward and back and
   comparing normalized bytecode, and proves the `@ObfuscatedName`/`@ObfuscatedSignature` evidence is
   byte-identical.
4. Clusters own disjoint classes (and therefore disjoint shards), so parallel research agents cannot
   conflict. Cluster write-ups record behavior, wire/cycle semantics, client quirks and the proposed
   RustClient owner. Rust porting is a separate spec-gated step; this tree is evidence, not authority.
5. Naming tools, licenses, corresponding LGPL source and pinned analysis inputs ship in the repository.
   Checksum manifests are checked before use. Generated readable JARs and sources stay in an ignored cache.
   Optional workbench/Recaf builds use repository-owned launchers and pinned bootstraps, with explicit
   host JDK/network prerequisites. They do not depend on a researcher's external checkout.

## Consequences

- A readable name is only as good as its evidence. The tooling cannot prove a name is correct, so T3/T4
  entries need hand review before a cluster is committed.
- The semantic jar is analysis-only (three constant-dynamic concatenation sites are rewritten); behavior
  involving those constants must be checked against the original gamepack.
- The tooling adds no Rust dependency and does not change runtime architecture, so no spec amendment is
  required beyond this ADR and the index updates.

## Alternatives considered

- **Hand-edit a copy of the decompiled Java.** Allows restructuring, but renames and behavior changes can
  not be proven separate by tooling, and concurrent agents collide on source files.
- **Rename in place under `references/`.** Violates the read-only reference rule and the provenance model.
- **Carry names forward from RuneLite 235 automatically.** A positional alignment agreed with the
  deterministic API-derived names only 23% of the time because the obfuscator reorders members; unique
  signature matching is retained only as unconfirmed proposals.
