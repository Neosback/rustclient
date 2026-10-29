# TSPS shortlist implementation checkpoint — 2026-10-29

Historical implementation evidence for the user-authorized shortlist. The current
[roadmap](PORTING-ROADMAP.md), [domain matrix](../reference/rs240-port/PORT-MATRIX.md)
and [plugin ledger](../reference/builtin-plugins/FIRST-PARTY-LEDGER.md) remain the
status owners. This checkpoint does not complete those larger phases.

The source review froze TSPS at `cf20aea7d18e451f49ea4532b26367b8c35f6b28`;
[the shortlist](../reference/TSPS-GIT-SHORTLIST-2026-10-29.md) retains the Git links.
Stock behavior below comes from the pinned revision-240 client; the XP subset
comes from pinned RuneLite 240. No runtime reads or modifications of references/.
Work began with substantial existing dirty interface/world-map/native changes;
those are preserved. This checkpoint describes the additional scoped work.

| Slice | Implemented | Verification and remaining boundary |
| --- | --- | --- |
| Item search | Profile-owned immutable name index and OC_FIND/NEXT/RESET host cursor; effective member/bought names, tradeability, note/placeholder exclusion, empty query, 250/251 overflow, stock quicksort ties | Independent fixtures and host reuse/reset contract. Caller currently shares the existing authenticated-member context used by MAP_MEMBERS; full stock world-membership selection remains a session-profile obligation. Cold index construction is synchronous once per context. |
| Cache/script/interface tooling | `cargo xtask cache audit`: every declared file in config/index 2, interfaces/index 3 and scripts/index 12; known config grammars; full interface graph checks; conservative CFG fixed-effect stack checks | Frozen metadata identity, separate decoded/unsupported/failed/unchecked rows, advisory size warnings. Calls, variable effects and unvisited branches are explicitly unchecked. No neutral opcode stubs or claim of whole-script execution parity. Existing script_probe runs selected interface load scripts separately. |
| IF1 | Exact 240 header/types 0–8, 32-bit model selectors, parent graph, alternate presentations, buttons, comparisons and raw CS1 expressions retained in immutable interface assets | Synthetic byte fixtures, every truncation boundary, limits, trailing bytes and invalid parent graphs. CS1 execution, tooltip/button integration and all legacy painting remain open; decoding is not complete IF1 UI. |
| Trigger operation ordering | Queue CC/IF_TRIGGEROP until the invoking script finishes; capture item context, validate operations 1–10, run onOp before the permission gate, retain script diagnostics | Mutating-script ordering, captured-item/reset, invalid operation and callback-error contracts. Menu selections retain their own validation. Content-type actions and complete drag packet/runtime behavior remain open. Trigger capture is not a reason to snapshot drag IDs before onDragComplete. |
| CPU pose reuse | Existing entity catalog owns bounded last model-local poses keyed by appearance/resolved morph and exact animation sample; 32 MiB model budget; merges, asset changes and removal invalidate | Emitted vertices/indices and base depth policy preserved by fixture. Actual-cache optimized probe below. World placement, terrain, lighting and picking still run each emission. Dynamic GPU staging already existed; cancellation and incremental replacement uploads were not added speculatively. |
| Local XP Tracker | Registered optional first-party overlay; login sync gate, gains/loss reset, ten-action ring, rates, idle/logout pause and rate reset. Pure core also accepts goals, manual pause and account-bound save/restore | Core arithmetic/lifecycle/storage-envelope and host-overlay contracts. Only implemented source settings are shown. Full panel/menu integration, stable account persistence, real-time clock and external sync remain open. The live adapter uses client cycles; this is not full RuneLite feature parity. |

## Actual-cache and CPU evidence

Cache selection: explicit `openrune-240`, LIVE under the configured OpenRune server.
Reference-metadata SHA-256:
`f12db51150826c019f1ad156b7431a26f62023db736fffff3f989a97a368c3f5`.
The machine-readable [evidence](../reference/TSPS-ADOPTION-EVIDENCE-2026-10-29.json)
retains final counts, diagnostic summaries, hashes and validation outcomes.
Reports are generated under `target/tsps-adoption/`; they are not source dependencies.

The inventory contains 234,706 declared files: 196,660 decoded, 629 unsupported,
38 failed and 37,379 unchecked. These are decoder classifications. Stack results
and whole-interface graph warnings are reported separately. Unknown config
families remain unchecked. Failures are not suppressed to make the sweep green.
The four trailing-byte IF3 model-widget failures are retained for revision/data investigation.
2,096 IF1 and 24,311 IF3 component files decode. Array opcodes 45/46 are
context-dependent in 240 (integer/object/long), so their audit paths remain
unchecked rather than using a revision-235 integer-only signature.

Optimized CPU probe: 16 actual-cache NPC definitions, 200 identical redraws,
three repeats. Rebuilding every pose took 89.459, 87.744 and 85.010 ms; reuse took
61.341, 60.496 and 67.588 ms (20.5–31.4% less CPU time in this workload).
Every repeat reported identical final vertex/index streams, zero asset/pose
failures, 3,200 versus 16 model builds and 440,792 retained model bytes. Cache keys
and container overhead are additional memory. This does not measure changing-pose
performance, peak RSS, GPU upload latency, whole-client FPS or live visual parity.
The forced-rebuild comparison clears the new cache and includes its miss/bookkeeping
cost; it is not a retained binary benchmark of the pre-change client.

```sh
cargo xtask cache audit --cache-dir "$RUSTCLIENT_CACHE_DIR" --cache-profile openrune-240
cargo run -p rustclient-native --bin script_probe -- "$RUSTCLIENT_CACHE_DIR" --execute 12 149 548 161 116 134
cargo run -p rustclient-native --release --bin entity_probe -- "$RUSTCLIENT_CACHE_DIR" 1-16 --pose-benchmark
```

## Validation

Passed: rustfmt, workspace/all-target check, strict Clippy, web/application/first-party
Wasm checks, relevant contracts (284 passed, one ignored in the broad run; final
changed contracts rerun), selected interface-script probe, generated inventories
and source/owner/documentation checks. The read-only cache inventory completed
with the unsupported/failed/unchecked counts above; exit zero is not a clean-cache
or whole-script parity result. Desktop/browser logged-in visual acceptance,
live reference-client differential execution and full RuneLite XP acceptance are
not run. No TSPS timing is presented as a Rust speedup.
