# Current implementation and acceptance audit

Reviewed **2026-11-01** against HEAD `e2ce6f64c9cbfcd957c219fe27f4c94e7883e8fe`
plus the uncommitted performance/plugin/API work and the incremental-location continuation
below; the [2026-11-02 reconciliation](#reconciliation--2026-11-02) lists what changed since. The original documentation sweep was a source reconciliation; its evidence is
distinct from the subsequent cache/GPU diagnostics and title-window measurements.
[REVIEW-BASELINE.json](REVIEW-BASELINE.json) records the sweep scope and inspected
identities. The earlier pasted-assessment/deob review is preserved in the
[archive](../../archive/2026-foundation/reviews/CURRENT-AUDIT-2026-10-28.md).

Use the [roadmap](../../plan/PORTING-ROADMAP.md) for execution order and the generated
[domain matrix](PORT-MATRIX.md) for status/owners/remaining branches. This audit explains
what changed in the assessment and what still prevents acceptance; it does not replace
the per-domain ledgers or weaken the [parity contract](../../specs/SPEC-0019-port-parity.md).

## Implemented foundations and their limits

| Area | Present in the current source | Still open |
| --- | --- | --- |
| Runtime/session | Native bounded S4 inbound queue/group admission, cycle outbound flush, ping replies, typed logout/transfer, absolute frame deadlines | Full stage/input/script ordering, idle/watchdog/reconnect/transfer behavior and browser composition |
| Updates | Independent projection cursors, sparse inventory patches, bounded persistent workers, retained zone buffers/identical poses and UI; 2 MiB / 1 ms zone staging | Live dependency/lifecycle acceptance, precise actor body aspects, granular widget edits and complete small-change isolation |
| Scenery | Location add/change/delete/animations, spatial dependency queries, retained 8×8 owning-zone preparation and coherent geometry/pick/minimap publication | Live source comparisons, temporary merges, cold catalog-copy reduction, dirty minimap rectangles and doors/travel acceptance |
| Title/map | Retained title/flame revisions, listener membership index, async retained viewport/overview pixels and guarded normal packet-78 clicks | Scored cadence, stale queued listener identity, full map menu/alignment/smoothing/flash and live teleport/rights acceptance |
| Shared shell | One egui native/browser harness, resolved 31px rail/273px open width, owned fonts/icons, Always on GPU | Exact reference control/font pixels, all DPI/window/input/IME/clipboard and hidden/unchanged-work acceptance |
| Plugins | Private capability-mediated calls, filtered interests, activation/actor handles, phase-aware lifecycle isolation, retained panels and local Notes/XP subsets | Source-stage hooks, missing gameplay facts, provider services, full feature/source-case parity and live visuals |
| Guest execution | Actual native ABI 0.1 component compatibility contracts; API/WIT 0.2 declarations/bindgen | Executable external ABI 0.2 and browser guest execution are deferred |

These mechanisms are not claims of sustained 60 FPS or complete plugin/stock behavior.
Source owners and focused evidence are linked from [PORT-MATRIX.tsv](PORT-MATRIX.tsv).

## Remaining blockers by dependency

1. **Proportionate updates and measured frame time.** Ordinary locations now retain
   terrain/untouched zones and publish affected resources coherently; cache-backed
   resource contracts pass. Live tails, transitions and global invalidation still need
   measurement. Profile changing actor
   poses and per-slot/widget changes. No chat/inventory edit should reconstruct/upload
   unrelated world geometry. Preserve draw distance 184 and all current quality.
2. **Authoritative gameplay facts.** Selected NPC BAS/head/body blocks are rejected;
   other actor blocks are partly consumed without retained effects. Rich facing,
   interaction, spot animations, hitsplats, health bars, tint/overrides, ground stacks,
   projectiles and graphics need source policy, lifetimes/reset/index reuse and
   GameState/replay/fingerprint ownership before downstream hooks can work.
3. **Map/session completeness.** Normal maps work; instances and world views are
   rejected or unintegrated. Extended bounds, loading/residency, collision and
   cancellation/reconnect/transfer acceptance remain open.
4. **Shared input, menus, scripts and widgets.** Too much reusable input/picking/menu
   behavior still lives in winit-bound NativeApp. Player/ground-item/target/drag and
   dialogue actions, widget mutation/model effects and named/unnamed script operations
   remain incomplete. Listener indexing does not solve every queued callback's lifetime.
5. **Provider and client systems.** Social/ignore/clan/trading/var domains, audio output,
   definition/image/elapsed-clock/profile services and full world selection/preferences
   block their consumers. Avoid fabricating absent facts in plugins or projections.
6. **Classic rendering and platform acceptance.** Nine GPU gates remain open; settings
   or successful shader probes do not close them. Browser gameplay is absent despite
   shared UI and Wasm builds. Exact 240/RuneLite visual/source comparisons are unrun.

## Inventory reconciliation

The [plugin catalog](../builtin-plugins/RUNELITE-PLUGINS.tsv) contains **131 slots**:
16 partial modules, one required GPU service, two schema-only entries and 112
not-started entries. The consumer scanner therefore finds **114 without executable
registration** (the two schema-only entries are included). No count means complete
plugins. Remote-service/fork dispositions remain explicit; they are not implementation.

The [CS2 inventory](CS2-HOST-COVERAGE.tsv) currently records 487 host-dispatched,
140 named-unhandled, 300 unnamed-cache-used and 440 unnamed-unused opcode rows.
Names from different sources are not a stable completion denominator. Dispatch
requires downstream semantics and source cases before it can count as verified behavior.

The [event ledger](../builtin-plugins/RUNELITE-EVENTS.tsv) distinguishes API declarations
from emissions and reachability. Actor facts are missing for several declared effects;
menu/script callbacks need actual stock build/sort/select/interpreter stages. Frame-end
differences cannot manufacture those hooks. Consumer regex inventories identify
candidates, not AST/member coverage, Java case enumeration or differential MATCH results.

## Evidence available versus acceptance still missing

The [API continuation record](../builtin-plugins/API-CONTINUATION-EVIDENCE.json) freezes
source digest `0445583a2fe1daf85a7e1990b6b485e8a37d92140d080ca5fb1b23ef53500a2d`.
On that batch, formatting, workspace/all-target checks, strict all-feature Clippy,
Wasm checking and **173 scoped Rust contracts** passed, including actual ABI 0.1
component mediation and three compile-fail authority cases. Those are historical
results on their recorded source, not commands rerun for this documentation sweep
or lossless Java/source-test parity.

[Update evidence](../UPDATE-OPTIMIZATION-2026-10-30.md) retains bounded CPU/GPU diagnostics,
raw samples/outliers and earlier workload comparisons. Later mechanisms supersede
older “still missing” notes within those dated sections; their original measurements
remain valid only for their original inputs. An isolated CPU improvement or occluded
window smoke does not prove whole-client cadence, current RSS or scanout behavior.

The roadmap's matched gameplay/map runs, full 256-actor mixed
workload, 1,024-actor characterization, cold-resource runs, 30-minute soak, warm
commit-to-visible latency and transient/steady memory gates remain **NOT-RUN or not
established**. Administrator teleporting and ordinary-account denial remain unobserved.
Exact visuals stay under ADR-0004; the reference was offline at the last recorded check.
No new live reference availability check was performed in this sweep.

## Documentation disposition

Old foundation/delivery/checkpoint plans are now [historical records](../../archive/README.md),
with original hashes and relocated links. The roadmap, this audit and domain TSV are
current; specs/ADRs retain their declared authority and scoped acceptance. Accepted
requirements were not downgraded to match missing code. The old master plan and delivery
page no longer direct implementation. Frozen JSON provenance retains its original
paths, resolvable through the [archive manifest](../../archive/MANIFEST.json).

The original documentation sweep checked links, inventories and ledger consistency.
Subsequent implementation checks and measurements are scoped separately below.


## Incremental-location continuation — 2026-11-01

[SPEC-0024](../../specs/SPEC-0024-zone-updates.md) now has an implemented native slice:
old/new footprint and adjacent-plane dependency queries, persistent owning-zone GPU
resources, poses separate from structural geometry, independent journal progress with
explicit missed-history refresh, worker CPU preparation, bounded chunk uploads,
stale-result rejection, retryable pending state and coherent geometry/pick/minimap
publication. Missing location assets extend the existing catalog without rebuilding
terrain or restarting session/plugin activation. The existing vertex budget remains.

The bounded actual-cache diagnostic removes/restores one openable object through
GameState nine times. Two zones change and 273 actual GPU buffer groups remain identical;
unchanged groups upload zero bytes. All visible triangle contents match full Rust
projection. The bounded before/after capture comparison is byte-identical across 22 PNGs;
it compares Rust implementations, not stock/RuneLite runtime appearance. Initial indexed
extraction was 8.18–11.20 ms; preparation included a 16.95 ms outlier during concurrent
checks. Raw observations are retained and cannot establish representative p99 or FPS.
See the canonical [update evidence](../UPDATE-OPTIMIZATION-2026-10-30.md) for final runs.

Three baseline five-minute title runs at physical 1530×1006, DPI 2, distance 184,
MSAA 2 and 60 FPS cap produced about 59.99 application presentations/s, but all failed
the 12 ms GPU gate and the 0.1% >25 ms gap gate. Two also missed the 20 ms p99 gap gate.
The local server was absent, other desktop apps were uncontrolled and physical scanout
was not measured. These observations do not close the declared environment's 60 FPS gate.

Minimap pixels are retained but a structural update still composes the whole minimap
once on a worker. Cold asset extension still clones catalog data. Live door/collision/
map coherence, missing-asset and rapid-replacement stress, 256/1,024 actors, the 30-minute
soak and transient/steady CPU/GPU memory gates remain open. Browser retirement preserves
GPU-handle thread ownership; Wasm checking alone is not browser runtime acceptance.

The reference endpoint was rechecked during this continuation and refused the connection;
no new live reference image or gameplay observation is claimed.

The final resource diagnostic preserved a 12.69 ms enqueue/automatic-publication outlier
alongside eight 0.103–0.161 ms samples. The byte/residency contracts pass; a hard 1 ms
enclosing-call or live frame-time guarantee is not established. Streaming initial
preparation removed the redundant expanded copy: peak RSS in this one initial-process
probe fell to 315 MB, while macOS peak footprint remained about 343 MB versus 340 MB
baseline. These different counters do not close the update-memory or steady-state gate.

## Reconciliation — 2026-11-02

Reviewed against HEAD `3b30b99f`. This is a roadmap and ledger reconciliation of the
loading, prompt, interface, minimap and memory pass; it is not a new source sweep and
[REVIEW-BASELINE.json](REVIEW-BASELINE.json) still records the 2026-11-01 inspection.
Evidence, commands and NOT-RUN gates are in the [pass record](../CLIENT-PASS-2026-11-02.md).

- **Closed to their named scope, live NOT-RUN:** previous scene kept during a map rebuild,
  chatbox prompt answers, UI atlas growth, minimap zoom and icons, settings list builder
  opcodes, the interface audit and its baseline, startup cache warm-up and shared decodes,
  released CPU geometry, and the Tile Indicators, Minimap and Ground Markers plugins.
- **Corrected here:** plugin catalog counts (16 partial modules, 112 not-started, 114
  without executable registration); `MenuOptionClicked` is now `emitted` in the event
  inventory (plugin menu entries only); `tools/parity_inventory.py` accepts the dispositions
  and blocking ids the catalog had gained, and its generated inventories were rewritten.
- **Still open from the blocker list above:** every item in it. Ground items, actor effects,
  social state, instances and world views are unchanged and now also block the minimap's
  item and friend dots and several plugin ports.
- **Unexplained, needs a live observation:** the settings list returning to the top after a
  click, and the world map buttons without sprites. Neither reproduces from the cache or the
  probes.
