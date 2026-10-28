# OSRS 240 / RuneLite porting roadmap

Status: canonical execution order, reviewed 2026-10-28. Full client parity is open.
Contract: [SPEC-0019](../specs/SPEC-0019-port-parity.md).
Baseline: `bd3aae10d4a37c46401aaf71be2fa415cbe623e8`; review began at
`0a91c023` with dirty work that was committed during inspection.
[The review baseline](../reference/rs240-port/REVIEW-BASELINE.json) binds inspected
files. World-map and GPU-shell integration is inspected, not accepted by this review.

## Read and update these owners

| Question | Canonical document/data |
| --- | --- |
| What must match, and what can Rust improve? | SPEC-0019 and [deviations](PARITY-DEVIATIONS.tsv) |
| What exists and what is missing by domain? | [PORT-MATRIX.tsv](../reference/rs240-port/PORT-MATRIX.tsv), generated Markdown |
| Is the pasted review accurate? | [CURRENT-AUDIT.md](../reference/rs240-port/CURRENT-AUDIT.md) |
| Where are the exact source symbols? | [SYMBOL-LEDGER.tsv](../reference/rs240-port/SYMBOL-LEDGER.tsv), linked domain research |
| What packets have dispatch/encode paths? | [PACKET-COVERAGE.tsv](../reference/rs240-port/PACKET-COVERAGE.tsv); not end-to-end acceptance |
| What was actually tested at a past milestone? | Its historical checkpoint; dates/commits delimit the claim |
| Which documents are active or historical? | [Documentation index](../README.md) |

The existing crate topology remains: protocol/cache adapters; authoritative
game-state/model; application orchestration/CS2 host; scene/game-ui/renderer
projections; platform adapters; plugin host/API/bridge/runtimes. Search these
owners before adding state or a new abstraction. Several source methods may map
to one owner, and one client method may span several Rust owners.

## Dependency-ordered batches

| Batch | Work and existing owners | Exit evidence |
| --- | --- | --- |
| 0. Freeze the compatibility denominator | protocol/cache + research: enumerate selected RSProt packets, all stock mask branches, cache families, host opcodes and RuneLite features/API/hooks; classify injection/platform mechanisms separately; inventory source tests/cases/assets and their dispositions | Each item has a source/version/hash, owner, status and concrete next obligation; unknowns remain visible. Existing partial inventories do not close this batch. |
| 1. Valid traffic and map lifecycle | protocol -> application -> game-state: selected NPC BAS/head/body customization layouts; full stock player masks as a separate scope; normal travel/reconnect, instance rebuild/chunk rotation and nested-world routing | Golden family vectors and malformed tails; retained fields and cache-policy resolution; reset/index reuse/replay; exact profile restrictions documented. No whitelist-only repair. |
| 2. Server world mutations | protocol/model/game-state/cache/scene: ground-item add/remove/count/visibility/customization, dynamic location add/change/delete/merge, zone clear/rebuild ordering, collision and minimap invalidation | Ordered mixed-zone batches reach committed state and rebuild equivalent projections; doors and ground items survive replacement, travel and reset correctly. Static var-driven morphs do not close server mutations. |
| 3. Complete interaction and dialogue | shared application/input/game-ui + protocol; native/web compose adapters: pause/count/name/string/item dialogue, player/item operations, inventory drag/drop, target item/spell modes, stacked menus and attack priority | Validated intent -> exact selected encoder -> server result -> committed state, including stale selection/epoch, cancel and rejection. Follow selected T/U packet routing; do not implement deprecated U packets just to increase counts. |
| 4. Actors and effects | game-state scheduler + cache/scene/game-ui: rich facing, freeze, path/turn slowdown, movement/idle/action lifetimes, spots, hits/headbars, overrides, projectiles/graphics | Source cycle/order boundaries, replacement/removal and bounded lifetime; reset/reuse, events/replay/fingerprints; downstream consumers; representative live trajectories. Decoder alignment and model posing are separate evidence. |
| 5. Interfaces, scripts and navigation | game-ui/application/cache/model: model widgets/chatheads/previews, remaining widget mutations/listeners, F-keys and hotkey arbitration, host opcode inventory, DB long/default branches, minimap icons/dots and world-map lifecycle/input/menus | Source-linked script/state cases and interface flows. Audit world-map presentation state versus replayable/script-visible state ownership; finish hover/menus/target movement and browser composition. Do not rebuild existing loaders/controllers. |
| 6. Remaining normal-client systems | game-state/application/protocol/audio/platform: social/clans, var domains/groups, scripted camera, audio/music/ambient queues, preferences and account/recovery paths | Exact packet -> state -> consumer chains; bounded queues/drain/expiry, platform effects and teardown; source-linked scenarios on both targets. Audio priority does not permit permanent omission. |
| 7. Classic rendering and RuneLite features | scene/renderer + plugin owners: close per-setting GPU/core obligations, full applicable plugin feature/API/hook dispositions and visual integration | Use [GPU-AUDIT](../reference/builtin-plugins/GPU-AUDIT.md) and [first-party ledger](../reference/builtin-plugins/FIRST-PARTY-LEDGER.md); follow SPEC-0016/17 and ADR-0004 for one complete manual reference/actual/difference acceptance report. Compile/probe results remain separate. |
| 8. Browser, packaging and final acceptance | apps/platform/plugin runtimes: browser cache/storage/transport, shared session/UI/input/audio/plugins, distribution and recovery | Same semantic cases on both adapters, real native/browser flows and plugin lifecycle; measured performance, reproducible packages and all scoped acceptance gates. A Wasm check or JS-host sample alone is insufficient. |

Browser adapters and evidence research can progress during earlier batches, but
must use the same semantic owners. A small walk/bank/login milestone is a useful
slice and never narrows the final parity denominator. Current source gaps are
listed in the matrix; historical milestone task lists do not override this order.

## Completion and document maintenance

Freeze each batch before implementation and run its consolidated audit/appropriate
checks after implementation. Record commands, source and Rust baselines, actual
results and skipped checks in the checkpoint. Update the canonical rows and ledger
in the same change. Validate documentation tools after edits; their success proves
schema/paths/provenance consistency only.

An implemented subset can be retained as such without declaring its domain done.
Missing branches, unresolved source behavior, absent consumers, source cases not
accounted for, and platform/visual NOT-RUN gates all remain open. Intentional
differences require the linked decision and evidence, not a claim that Rust is
inherently better. Historical validation is never silently carried forward to a
new worktree.
