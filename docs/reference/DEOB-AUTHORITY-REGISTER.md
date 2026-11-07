# Deob and RuneLite authority register

Seeded 2026-11-06 for the later study of the revision-241 TypeScript client (TSPS, an external snapshot
that is not in this repository). It lists behavior where the stock 240 client (`references/rl-suite-240`)
or RuneLite (`references/open-runelite-rev-240`) is the **only** authority, so the 241 study compares
against these entries instead of absorbing from the TS client. It decides nothing by itself: a behavior
change still needs a [PARITY-DEVIATIONS](../plan/PARITY-DEVIATIONS.tsv) row under
[SPEC-0019](../specs/SPEC-0019-port-parity.md), and difficulty or missing behavior is not a deviation.

Sources: [TSPS-241-REVIEW](TSPS-241-REVIEW-2026-10-29.md) and
[TSPS-GIT-SHORTLIST](TSPS-GIT-SHORTLIST-2026-10-29.md). The TS source was read there, not here; nothing
below was re-checked against it, and its tests were not run.

## Where the TS client differs from stock (do not adopt)

| Area | Stock (authority) | TS client | Rust owner and state |
| --- | --- | --- | --- |
| Face priority and depth | Authored face order and depth bias, strict reverse-Z with deferred opaque writes (SPEC-0016) | Priority masked to 3 bits and added to view depth; `less-equal` with depth writes | renderer / scene; unchanged |
| Wall decoration offsets | Stock placement | `wallDecorationNudge`, `embeddedWallDecorationShift` corrections (some removed upstream) | scene `decorations.rs`, `locations.rs`. **Our own `FLUSH_DIAGONAL_DECORATIONS = true` and straight nudges disagree with SPEC-0016's baseline: unreconciled, check against the deob first** |
| Effect heights and lifetimes | Cycle-driven, held in GameState | `TILE_GFX_LIFT_UNITS = 8`, wall-clock grace for chained effects | game-state `ActorEffectsState`; definition durations still defaulted (`*-DEFAULT-DURATION`) |
| NPC movement step | Per-axis stepping | Normalised distance with an epsilon | game-state path queue |
| Animation continuity | Stock sequence/pose rules | 150 ms cross-sequence grace, previous-pose holding, neutral-geometry fallback; looping reset in `ActorAnimation` | scene `entity_mesh`; unchanged |
| GPU label posing | Integer CPU path truncates after each step | Floating-point matrices; the TS test tracks the float/integer gap | CPU pose stays the oracle; precision gate first |
| Drag completion | Runs the script, then reads item ids | Reads ids before `onDragComplete` | widget drag, not yet implemented |
| Root world-view id | 0 | -1 | world views, not yet implemented |
| Unsupported CS2 ops | Real handler or explicit failure | Neutral stubs (`UnsupportedOps.ts`) | `CS2-HOST-COVERAGE.tsv`; no neutral stubs allowed |
| World map click | Packet 78 | Packet 105, release-click policy, different coordinate byte order | worldmap; stock dispatch implemented |
| Login | RSProt login contract | Custom username/password/revision packet | protocol `login.rs` |
| Permissions | Server decides | An action label used as permission | application `interaction.rs` |
| Item search | Stock search over the cache | Fixed 65535 scan range | item search |
| Audio | Stock Note-Off handling | Blanket channel-9 rule | audio, not started |
| Plugins | Versioned immutable snapshots (SPEC-0004) | Mutable engine access through an injector | plugin API |
| Interface text | Stock text | A "Continue" action inferred from English | scripts |

## Accepted deviations that took TS or 241 values (re-verify against the deob)

`MINIMAP-ZOOM-OPS`, `MINIMAP-ICON-ZOOM-LIMIT` (direction inferred from the op name) and
`DEVICE-OPTION-RANGE` (option 20 is a guess; TS numbers it 27). These cite the TS source, not the deob.

## Stock behavior with no safe substitute

Settle from the deob before any TS comparison: the facing-target orientation resolver
(`classEB.method3820`, [FACING-TURNING](rs240-port/FACING-TURNING.md)), instance chunk rotation and
collision, projectile and map-animation lifetimes, widget mutation packets, `GROUP_*` var domains, the
CS2 ops with no 240 handler evidence (7000 and 6750 ranges), friends/clan ordering, and the RuneLite
event timing noted in `INTERACTING-CHANGED-ON-DIFF`.

## Safe to learn from the TS client

Small algorithms, lifecycle failure cases and tooling (see the shortlist): bounded pose queues and
cancellation, atomic staged uploads, conservative actor bounds, damage-region texture updates, map-job
epochs, interaction-cache invalidation scenarios, cache-wide config and interface sweeps, cache-ID diffs,
the guide-price readiness lifecycle. Areas the deob cannot teach and the 241 study should cover: cache
update and live download, world hopping, a playable browser/WebGPU client, worker pools, and the
237 to 241 migration notes.
