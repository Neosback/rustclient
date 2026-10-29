# TSPS Git history: best transfers into RustClient

Reviewed 2026-10-29. Supporting research; no Rust implementation or compatibility
decision is made here. This narrows the [snapshot review](TSPS-241-REVIEW-2026-10-29.md)
using the actual [client history](https://github.com/RSPSApp/tsps/commits/main/client).
The [existing roadmap](../plan/PORTING-ROADMAP.md) still orders implementation.

## Recommendation

Use TSPS primarily for **small, separable algorithms, lifecycle failure cases and
tooling**, with the exact 240 deob determining game behavior and the pinned RuneLite
tree determining plugin effects. The best immediate gap-filling candidates are
**stock item search, cache/script sweeps and IF1 decoding**. The best rendering
mechanisms are **owned pose reuse, coherent cancellable builds and bounded upload
publication**. Local XP tracking is a promising later RuneLite feature.

An implementation mechanism can improve without changing OSRS results. A different
result/order/pose needs evidence that the departure is far superior under the user's
instruction, plus SPEC-0019's recorded deviation and the owning spec when applicable.
**This review establishes no such behavior-changing exception.** TSPS commit claims,
attractive output and benchmarks of another client do not establish one for us.

## What was reviewed

The GitHub connector enumerated **945 distinct commits touching `client/`** at
`cf20aea7d18e451f49ea4532b26367b8c35f6b28`: 735 nonmerge records and 210 merges,
across ten API pages; the last page contains 45 records. The returned dates span
2026-07-31 through 2026-10-29 UTC. This is the complete returned path-filtered
history at that tip, not the entire repository's history.

Messages were screened by topic; **40 selected commit detail responses** were
retrieved, with selected hunks inspected in **35 files across 31 commits**.
Broad feature commits were not audited in full. Inspection also checked the
current Rust working tree and specific exact-240/RuneLite source anchors below.
[Git evidence](TSPS-GIT-EVIDENCE-2026-10-29.json) records pagination, commit/parent/tree
IDs, all changed-file blob IDs for the selected commits, inspected hunk paths,
and local source hashes. Enumeration is not a method-by-method parity audit.

The supplied TSPS folder remains an unversioned snapshot; the previous report's
9-unchanged/4-changed hash comparison is still valid for that snapshot. Git history
now supplies introduction/follow-up evidence separately. New live Git fixes are
not assumed to exist in the local copy.

## Ranked shortlist

Size describes the proposed Rust slice, not a time estimate. Existing coverage
comes from working-tree inspection, including concurrent uncommitted work.

| Rank | Transfer | Rust slice / size | Current situation and gate |
| --- | --- | --- | --- |
| 1 | Indexed `OC_FIND` search with stock results | Cache assets + application script host / medium | 4210–4212 are named but unhandled. Preserve deob matching, filters, ordering, limit and cursor behavior. |
| 2 | Cache-wide decode, script-stack and interface sweeps | Existing cache/probe/inventory tools / small–medium | Adds evidence around our partial decoders/host; neutral stubs must remain unsupported. |
| 3 | Legacy IF1 decoding, with exact boundaries | `cache::osrs240`, existing widget/model owners / medium | `widgets::decode` explicitly rejects IF1. Parsing is only the first slice; CS1/layout/actions remain separate. |
| 4 | Close/reopen, stale addresses and reset regressions | GameState interfaces + application scripts / small tests/audit | Runtime cleanup and trigger-op handling already exist. Check lifecycle/order; do not port another widget manager. |
| 5 | Owned reusable CPU bases and poses | Existing scene entity catalog/projection / medium | Entity assembly/posing is a profiling target; Rust ownership avoids TSPS's scratch-alias bug. |
| 6 | Cancellable builds from one immutable input snapshot | Application/scene, scheduling adapters / medium | Native workers/generation checks already exist. Stop obsolete work without dropping semantic operations. |
| 7 | Bounded uploads with complete publication | Existing renderer geometry resources / medium | Persistent buffers already exist; investigate large replacement spikes and peak memory. |
| 8 | Listener indexes and subscriber interest | Application/bridge/host / small–medium after profiling | Preserve exact cycle/event order, hidden/mount semantics and committed events. |
| 9 | Bounded icon/image residency and model-frame invalidation | Existing game-ui visual assets + renderer / medium | Our icons use an atlas and currently item-ID keys, not TSPS's per-count texture architecture. |
| 10 | Local XP Tracker state/rates | First-party plugin + existing skill events / small state core, larger complete feature | Skills/StatChanged exist; panel, persistence and complete feature acceptance are separate. |
| 11 | Inventory Tags pure rules/pixel masks | First-party plugin + widget/overlay services / medium | Item/bounds snapshots exist; menu decoration, color picking and safe image effects need services. |
| 12 | Sound/music regression scenarios | Future audio spec and owning crate / large complete subsystem | Audio is still a crate shell. Translate tests/algorithms, not browser synth architecture or guessed timing. |

These are choices within roadmap phases, not a new execution order. Source-equivalent
optimizations may proceed inside existing boundaries once measured; phase specs
and their acceptance requirements still apply.

## 1. Item search: a missing feature and a simple optimization

[Commit 1db4984f](https://github.com/RSPSApp/tsps/commit/1db4984fb8b771d0609da12138d5e0832e5fa8dd)
replaces repeated item-definition scans with an index of normalized names and
tradeability, invalidated by loader/membership context. Its useful Rust shape is
an immutable indexed catalog shared by the script host, with search cursor/results
kept in the existing session runtime. No global cache or alternate item owner.

**Do not port its result semantics blindly.** The pinned 240 stock portion of
`classOG.method8468` scans the actual item archive count, excludes note/placeholder
templates, applies tradeability only when requested, returns the overflow sentinel
once matches exceed 250, and sorts accepted results by item name.
`classKI.method6526` / `classBB.method877` then provide find/next/reset behavior.
The RuneLite `GrandExchangeSearched` wrapper around the stock function is a separate
plugin hook. TSPS's index hunk uses a fixed 65535 scan range and does not itself
establish these stock guarantees.

Acceptance: empty query, mixed-case/cache character behavior, duplicate names/ties,
noted and placeholder variants, tradeable filter, exactly 250 versus 251 matches,
next exhausted/reset, profile/membership replacement and session reset. Repeated
queries must not re-decode the catalog. This supports GE/bank/search scripts without
claiming complete GE state/UI. Roadmap phases 7/8.

## 2. Sweep tools: reuse the method, generate for revision 240

[Migration e53ef39a](https://github.com/RSPSApp/tsps/commit/e53ef39a709d50de938bf7b1c4a8a7852f4cb4cd)
adds command signatures, isolated handler checks, whole-script runs, interface
onLoad/transmit/timer sweeps, config byte-consumption checks and cache-ID comparisons.
The prior snapshot review inspected these tools and documents their blind spots.

Bring the **diagnostic design** into existing Rust probes and inventories. Generate
240 signatures from pinned evidence; report unknown/unchecked handlers, required
unsupported semantics, script errors and coverage separately. Require exact decoder
consumption and contextual errors, but distinguish absent optional archives from
corrupt declared resources. Run interface cases with explicit semantic state.

TSPS's 241 numbers and dummy outputs are not our implementation. A handler that
pops/pushes correctly can still be wrong, and default arguments cannot exercise
all script paths. This is the strongest tooling transfer because it helps expose
real missing behavior instead of hiding it behind dispatch counts. Phases 0/7.

## 3. IF1: close a real decoder gap without importing 317 assumptions

[Commit f2e9c8fd](https://github.com/RSPSApp/tsps/commit/f2e9c8fd46a09a5e804497a7feffc08c350c269f)
removes an incorrect legacy-container child list. Children identify their parent;
the container's type-specific block reads scroll height and hidden state.

This agrees with the inspected 240 `Widget.method7215`, including its type-0
branch. Our [widgets decoder](../../crates/cache/src/osrs240/widgets.rs) currently
rejects a non-255 header explicitly. Extend that adapter with exact stock decoding,
retaining CS1 instructions/comparisons and widget-type data in existing owned
types as needed. Do not insert custom child coordinates to compensate for a
misread format. New retained semantics need the owning spec before implementation.

Acceptance must separate **bytes decoded** from **interface works**: actual-cache
vectors, all required IF1 types, parent links, CS1 evaluation, visibility/actions,
malformed/trailing data and the rendered selected interfaces. One container test
cannot certify every legacy interface. Phase 7.

## 4. Interface regression cases: valuable, but inspect what we already own

[Close/unload cb4ea7c6](https://github.com/RSPSApp/tsps/commit/cb4ea7c6ba5d5e03c239a107632578e2c6028ee3)
and [stale lookup 90503373](https://github.com/RSPSApp/tsps/commit/905033737757bf2102eb6dd899dec78dd2774bb4)
show why mutable widget objects must not survive close/reopen through a lookup
cache. In Rust, immutable definitions may remain cached while GameState runtime
overrides and script-created children reset; recreating Java/TS mutable definitions
is unnecessary. Our `InterfaceState::topology` already removes runtime/events for
unmounted groups, while script lifecycle tracks residency separately.

Adopt cases for shared mounts, same-group reopen, repeated child creation/deletion,
closed-group mutation, pending listener cancellation and address reuse. Read-only
queries must not mount a group as a side effect. Retained asset metadata must not
retain mutable gameplay overrides. Match exact-240 close order, including queued
events, before calling the lifecycle complete.

[Trigger-op 4f31590d](https://github.com/RSPSApp/tsps/commit/4f31590ddb471afdc8e6b54ab3e2a89e366bc991)
is a useful test lead, not a missing handler to add again. Current Rust already
handles active/explicit trigger ops. The 240 `classJG.method6408` queues operations
with captured item context; the script executor drains them through
`classEA.method3815`, which runs onOp and checks permissions/content behavior.
Our inspected host calls `perform_action` directly. Audit that deferred ordering
and error/item handling. Do not use TSPS's nonempty action-label check as server
permission. Named dispatch alone does not establish parity. Phases 5/7.

## 5–7. Rendering work that translates cleanly to Rust ownership

**Own cached poses.** [d971d3a6](https://github.com/RSPSApp/tsps/commit/d971d3a6ecab8f7b6b89db7c625559f106db3241)
copies cached player geometry out of reusable scratch storage. In Rust, scratch
`Vec`s and retained `Arc`/owned geometry should have distinct lifetimes. Cache keys
must include appearance/morph, animation slots/cursors, asset generation and any
deformation inputs. Keep placements separate only where the source permits sharing.
Compare geometry, picking, bounds and sorted-depth inputs before claiming reuse
correct. Start with CPU reuse; GPU label matrices need the precision gate in the
snapshot review. Phases 6/11.

**Cancel without mixing inputs.** Take
[eb89da97](https://github.com/RSPSApp/tsps/commit/eb89da97886dbe5aed5959f8a265ce6df97ab6a1)
together with its [snapshot-consistency fix ce46cfb7](https://github.com/RSPSApp/tsps/commit/ce46cfb7a3735f33da88a5b91931534cc8cdf6b4).
Cooperative cancellation alone is incomplete: every chunk must see the same
committed input snapshot. Rust can pass immutable inputs instead of temporarily
freezing mutable worker globals. Check cancellation between bounded units;
generation-reject late results. Coalesce derived rebuild requests only, preserving
all semantic operations and event order. Our worker/channel foundations already
exist; extend them. Phase 3.

**Publish complete resources.**
[b8e39e47](https://github.com/RSPSApp/tsps/commit/b8e39e471f0d522d193d6d1d20fd7fc86d0e2fc8)
stages large uploads, while
[3fe05e65](https://github.com/RSPSApp/tsps/commit/3fe05e651a4aec870088f8caf2b0de04a7c4c411)
ties scene readiness to drawable resources. Our renderer already retains static
and grown dynamic buffers. Measure replacement submission spikes and old+new peak
memory before adding staging. Publish matching mesh, picking, textures and metadata
together; dispose cancelled allocations. TSPS's byte/time budgets are examples,
not portable constants or measured Rust improvements. Phases 3/11.

## 8–9. Smaller performance candidates after measurement

- [Loader reuse 1e98bd61](https://github.com/RSPSApp/tsps/commit/1e98bd615cdb857d40144968f68226dafcb02c63):
  our shared cache and per-host resource maps already provide ownership. Check for
  actual redundant decode/construction before adding another cache abstraction.
- [Timer traversal f120e717](https://github.com/RSPSApp/tsps/commit/f120e7171d24081d869c86ed1dbe1b756cc23df0):
  derive a listener index from current mounts/topology/listener generations, while
  reading live arguments/visibility and preserving stock traversal order. Hide/show,
  alternate mounts and deletion invalidate eligibility; do not cache event results.
- [Subscriber guards b2805d13](https://github.com/RSPSApp/tsps/commit/b2805d131e69592cc7df46c64f209e9b9f0f3ae1)
  and [dispatch indexes 7d8fa6fb](https://github.com/RSPSApp/tsps/commit/7d8fa6fb9b1b92aab5d393b5608af162bbc2fa36):
  avoid unused plugin projection/allocation, never suppress GameState commits,
  replay events or another consumer's observation. Recheck interest on plugin lifecycle.
- [Icon bounds 3e333f2f](https://github.com/RSPSApp/tsps/commit/3e333f2f9f4dbecb139fcd0735655dd114615b96)
  and [widget-model invalidation 919fba2d](https://github.com/RSPSApp/tsps/commit/919fba2dc16b844359395fb2c3ddd8470e649e76):
  use explicit byte budgets and complete generation/frame keys. Our atlas does not
  share TSPS's per-quantity GL texture leak, and full type-6 rendering is still a gap.
  Eviction must preserve active command/resource lifetimes and allow regeneration.
- [Animated bounds 63f08683](https://github.com/RSPSApp/tsps/commit/63f08683a32e4855369abae9f9c721977ecbec73):
  conservative bounds must cover every applicable pose. Prefer valid cached bounds
  or retain unknown models; do not bake all animations solely to enable culling.

These support phases 7/11/12. Count actual work and retained bytes, not just cache hits.

## 10–12. Missing feature leads with explicit prerequisites

**Local XP Tracker is the best contained RuneLite feature candidate.**
[3df1df13](https://github.com/RSPSApp/tsps/commit/3df1df135526a701f79c57fdaf6a7dd361c0539f)
separates tracking state/rates from React panels. Its fixed action-history ring,
gain/reset state and rate arithmetic translate naturally to typed Rust structs.
The pinned RuneLite `XpStateSingle` independently confirms the ten-entry history
and minimum 60-second rate denominator. `XpTrackerPlugin::onStatChanged` also
suppresses initial login synchronization; that gate must not be lost in translation.

Use existing skill snapshots/StatChanged with explicit elapsed time. Verify login
baseline, XP loss/reset, idle/logout/manual pause, goals and per-account restore.
The current plugin ledger flags the complete XP Tracker for external-service
decision; a local-only slice can be evaluated separately without treating the
network portion as implemented or accepted. Full panel, persistence and menu effects
still require services and acceptance. Phase 12; no mutable access to skill truth.

**Inventory Tags has small pure algorithms but more UI dependencies.**
[e471842d](https://github.com/RSPSApp/tsps/commit/e471842d97f279b9b9dff5ac8896410b8b766e7b)
provides item canonicalization, tag serialization and outline/fill pixel operations.
Existing widget snapshots expose item IDs and clipped bounds. Keep pixels/resources
engine-owned; add no GPU handle to plugins. Menu decoration/color picking and
complete overlay effects need versioned services before calling the plugin done.
Use the pinned RuneLite feature as authority; TSPS uses newer RuneLite 1.13.3
interfaces whose IDs/feature scope may differ. Ground Markers follows later because
instance-aware coordinates, picking and menu services are still incomplete. Phase 12.

**Audio has useful regression leads, not a quick complete port.**
[c1a2e320](https://github.com/RSPSApp/tsps/commit/c1a2e3208e5becf1cdf30f9befab8d485a74333a)
removes earlier filtering/gain assumptions;
[594319e5](https://github.com/RSPSApp/tsps/commit/594319e5b62cb645b42df4769cce7a190716a468)
corrects a blanket channel-9 Note-Off rule; and
[cfd4b31d](https://github.com/RSPSApp/tsps/commit/cfd4b31d514e1385f85c16c0405154f803acc0ab)
guards late sound loads after disposal. Those are test scenarios to check against
the exact 240 mixer, sample/patch data and cycle queues. Do not assume a comment
calling a percussion rule vanilla makes it correct. Preserve individual scheduled
sound events even if asset-load requests deduplicate by sound ID. Browser audio
activation is a platform concern. Our audio crate remains a shell; phase 9 owns the
complete decoder/mixer/lifecycle work.

## Changes to exclude or investigate before reuse

| Evidence | Decision |
| --- | --- |
| [Drag snapshot 0ab599b1](https://github.com/RSPSApp/tsps/commit/0ab599b1809886f72dc5272d0f15d5ea7a06231d) reads item IDs before onDragComplete | Do not adopt this order unconditionally. In 240 `classPQ`'s release path, `classDE.vmethod347` runs the script synchronously via `classSZ.method10771`; only afterward does `classTM.method10984` read widget fields for IF_BUTTOND. Whether our retained fields behave identically needs a mutating-script fixture. Treat TSPS's bank failure as a scenario, not overriding wire authority. |
| [Church placement 31ab0cad](https://github.com/RSPSApp/tsps/commit/31ab0cad83a3ec0ff6e21205622f4c687c7aa7c3) | It removes some prior corrective offsets; it does not establish all remaining nudges/diagonal shifts as stock. Keep our existing decoration/depth exclusion and reconcile our own snapping separately. |
| [Animated picking 5e0773c0](https://github.com/RSPSApp/tsps/commit/5e0773c0395b49a7988c04cf86b8caae94db25ca), followed by [9091eaa0](https://github.com/RSPSApp/tsps/commit/9091eaa050e20cacae0e9a2c4448387f4b082872) | Keep the draw/pick geometry agreement invariant. Exclude the intermediate door-name/action heuristic; the follow-up removes that workaround for authored animations. Our current picker already uses scene geometry. |
| [Item fade revert 0e5ce2cb](https://github.com/RSPSApp/tsps/commit/0e5ce2cb949380ac7cd6234bcaa0c62d9eee27f8) | Do not copy the reverted wait-for-server fade. Use current stock pointer-held behavior, with exact-240 input/visual acceptance. |
| [Bundles 28f2788c](https://github.com/RSPSApp/tsps/commit/28f2788c90bb34cb6e2251f025daab321cb085cc), [actor instancing f5b2915d](https://github.com/RSPSApp/tsps/commit/f5b2915df4ecbaa1f205aa12707871523a435bcb) | Later experiments after CPU reuse. Preserve our reverse-Z, priority ordering and deferred opaque writes; prevent animation churn from invalidating large bundles. A smaller draw count alone is insufficient. |
| GPU label posing, fixed grace/rest poses, arbitrary graphic lifts and face-priority depth offsets | Keep the snapshot review's precision and behavior gates. Faster approximate poses/depth are not established as superior substitutes for stock. |
| Custom protocol, neutral CS2 handlers, server quests/combat, JVM Quest Helper bridge | Supporting scenarios only. No custom packet IDs, dummy semantic success, server authority or JVM dependency enters the Rust port. |

Some seemingly useful fixes are already covered: wrapped text retains a shared
style across soft breaks in `game-ui::widget_text`; interface topology cleanup,
cached widget snapshots, retained shell output and static/dynamic GPU resource
separation also exist. Use relevant TSPS tests to strengthen acceptance, not to
replace those owners.

## Concrete next slices within the canonical roadmap

1. Phase 0: select the exact-240 cache/script sweep scope and report unsupported
   paths separately. Freeze inputs and add genuine cases to existing inventories.
2. Phase 7: specify and port stock OC_FIND/NEXT/RESET with an immutable name index;
   cover source results and repeated-search decode counts. Then address IF1 in
   bounded decoder/CS1/layout slices.
3. Phases 5/7: audit deferred trigger operations and drag/script/packet order with
   mutating-script fixtures; strengthen close/reopen and address-reuse contracts.
4. Phases 3/6/11: measure EntityBuild/EntityUpload and replacement-load latency;
   choose CPU reuse, cancellation or upload staging from the measured bottleneck.
5. Phase 12: evaluate the local XP Tracker core first, then required presentation/
   storage services; Inventory Tags after menu and item-image services are ready.

No item above is marked implemented by this report. Domain/method/packet/plugin
status continues to live in the canonical ledgers. Any adopted slice updates those
owners with implementation evidence and exact validation, rather than using this
shortlist as a completion ledger.

## Verification boundary

Performed: GitHub API history enumeration and selected commit/file reads, local
Rust/deob/RuneLite inspection, evidence consistency/source-hash checks, Markdown
link checks, documentation-index check and whitespace validation. GitHub source
was pinned; neither reference trees nor Rust code were changed by this review.

NOT-RUN: Rust/TSPS tests or builds, live gameplay, visual acceptance, benchmark
reproduction and complete parity inventories. No TSPS timing is promoted to a Rust
speedup. For a superior-mechanism claim, retain equal semantic/visual/packet output
and report repeatable CPU latency, allocations, upload/draw work and peak memory;
for a behavior departure, also document the exact lost/changed stock contract and
the demonstrated user benefit before accepting it. Preserve applicable source
licenses if a later port derives code.
