# Revision 240 NPC path acceleration

Status: exact source semantics specified and authoritative ordinary NPC stepping implemented for cache-resolved definitions.

## Scope

This note records the revision-240 NPC-definition flag that changes ordinary queued path acceleration. It is deliberately narrow: action movement gates, forced movement, facing/turning, directional locomotion sequences and the blocked-cycle catch-up counter are documented elsewhere.

## Exact client evidence

Primary evidence:

- `references/rl-suite-240/sources-semantic/Actor.java`
- `references/rl-suite-240/sources-semantic/NPCComposition.java`

`Actor.method3284` reads the NPC's current `NPCComposition.field5325` before choosing backlog thresholds.

The normal branch uses:

- base step `4` fine units;
- queue length `> 2` -> step `6`;
- queue length `> 3` -> step `8`.

When `field5325` is false, the NPC-only alternate branch accelerates one entry earlier:

- base step `4` fine units;
- queue length `> 1` -> step `6`;
- queue length `> 2` -> step `8`.

Traversal scaling is applied after that choice: run doubles the step and crawl halves it.

`NPCComposition` constructs `field5325` as `true`. Definition opcode `109` sets it to `false`. Therefore revision-240 opcode 109 is the exact selector for the early backlog thresholds.

This is not opcode 107. Opcode 107 controls the separate interaction/clickability flag already retained by RustClient.

Confidence: **B**. Exact revision-240 client control flow and exact definition decode agree internally. No independent server writer is required because this is cache-definition/runtime behavior rather than a packet-field claim.

## Rust semantic projection

RustClient does not expose opcode 109 to `GameState`.

The cache adapter projects it into the revision-neutral value:

```rust
PathAccelerationPolicy {
    step_six_after,
    step_eight_after,
}
```

Two revision-240 projections are currently named:

- `PathAccelerationPolicy::STANDARD` = strict thresholds `> 2`, `> 3`;
- `PathAccelerationPolicy::EARLY` = strict thresholds `> 1`, `> 2`.

The semantic type intentionally says nothing about opcode numbers, NPCs or revision 240. Those facts remain in `cache::osrs240`.

## Resolution boundary

`cache::osrs240::npc_path_policy::resolve` reads config group 9 and projects the selected NPC definition to `PathAccelerationPolicy`.

Application orchestration memoizes that policy alongside action-sequence policies. For each `NpcInfoUpdate` it resolves definition IDs referenced by:

1. newly spawned NPCs;
2. `NpcProperty::Definition` changes.

The protocol decoder remains cache-free. It emits definition IDs only.

The resolved table is attached to the semantic NPC update before `GameState` mutation. Direct semantic fixtures may leave the table empty; those fixtures remain explicitly unresolved rather than receiving a guessed policy.

## Authoritative lifecycle

`ActorPathQueue` retains the resolved acceleration policy for an NPC.

Lifecycle rules:

- NPC spawn stores the policy for the spawned definition when available;
- ordinary path enqueue does not change the policy;
- queue clear/reset retains the definition policy;
- an NPC definition-change mask replaces both `definition_id` and the retained acceleration policy;
- despawn/reuse destroys the queue and therefore cannot inherit another NPC's policy.

## Per-client-cycle scheduling

`game-state::actor_movement` now advances ordinary NPC paths in the same authoritative cycle phase as ordinary player paths.

Ordering remains:

1. action movement gate sees the action delay at the start of the client cycle;
2. if allowed, path acceleration selects `4`, `6` or `8` fine units from the resolved policy and current backlog;
3. run/crawl scaling applies;
4. fine position approaches the oldest queued target, or snaps when the gap exceeds `288` fine units;
5. reaching/snapping consumes the oldest queue entry and decrements the remaining pre-action backlog when positive;
6. only after movement does the action explicit delay advance by one cycle.

NPCs whose path policy is unresolved do not perform authoritative ordinary fine stepping. This is intentional fail-closed behavior for direct fixtures; connected application traffic resolves the definition policy before mutation.

## Scene projection

The scene `ActorMotion` facade now reads authoritative fine position for ordinary NPCs whose path policy is resolved.

The legacy scene motion implementation is still retained for:

- forced/exact NPC movement;
- unresolved direct fixtures;
- disposable pose/turn projection.

Thus ordinary NPC translation no longer depends on the scene-owned duplicate scheduler in the connected cache-resolved path.

## Focused tests added but not run

Source-level tests now cover:

- default NPC definition -> `STANDARD` policy;
- opcode 109 -> `EARLY` policy;
- standard backlog length 2 still moves `4` fine units;
- early NPC backlog length 2 moves `6` fine units;
- retained policy survives path clear/reset.

No Cargo test/check/Clippy or workspace gate was run for this checkpoint.

## Remaining movement gaps

This checkpoint does not claim full `Actor.method3284` parity. Still outstanding:

- `field1452` blocked-cycle catch-up and its forced `8`-unit recovery step;
- turn-slowing when desired/current orientation differ;
- desired/current orientation and moving target-follow resolution;
- directional walk/run/crawl animation selection;
- forced/exact movement interpolation and its legacy action-frame boundary gate;
- ordinary actor scene-bound safety reset behavior;
- final removal of duplicate ordinary stepping code from the legacy scene projector once pose/forced-movement dependencies are separated.

These are follow-up scheduler checkpoints, not reasons to re-run deobfuscation.
