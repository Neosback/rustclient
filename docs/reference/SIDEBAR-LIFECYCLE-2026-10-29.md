# Shared sidebar and plugin lifecycle checkpoint — 2026-10-29

Implementation continuation from clean RustClient `37ef536cfb917cd4a469aa62838afbb46a66f55`.
The source and validation record is [the evidence JSON](SIDEBAR-LIFECYCLE-EVIDENCE-2026-10-29.json).
This checkpoint implements the approved sidebar/lifecycle slice; it does not close RuneLite plugin,
classic GPU, browser gameplay or visual parity. The canonical GPU work order remains
[GPU-AUDIT](builtin-plugins/GPU-AUDIT.md). Architecture decisions are in
[SPEC-0004](../specs/SPEC-0004-plugin-system.md),
[SPEC-0007](../specs/SPEC-0007-rendering-ui.md) and [ADR-0007](../adr/ADR-0007-on-demand-gpu-shell.md).

## Source authority and measurement

Stock revision 240 governs gameplay. The pinned Open RuneLite 240 checkout governs plugin effects
and shell presentation. TSPS commit `268bf5d1f7e2092b8e6579a0394e06c84660c474` supplies seven
individually hashed supporting files: EventBus, PluginManager, Plugin, RuneLite, narrow Client API,
Sidebar.css and widget-hover-lifecycle.md. This is a selected-source freeze, not an assertion that
all of TSPS was audited or is compatible with revision 240. Earlier TSPS pins remain historical.
No reference tree was changed; runtime assets are owned copies with notices.

`tools/sidebar/RuneLiteSidebarMetrics.java` loads the pinned compiled shell classes and resolved
RuneLiteLAF on JDK 11.0.31, headless, `flatlaf.uiScale=1x`. It does not invoke a gamepack.
The compiled source-manifest hashes were checked against the pinned source files. Results:

| Resolved logical metric | Value |
| --- | ---: |
| PluginPanel content / scrollbar allowance | 225 / 17 |
| Full content / closed rail / open sidebar | 242 / 31 / 273 |
| Tab rectangle within open sidebar | x243, y0, width30, height26 |
| Scrollbar width | 7 |
| Navigation icon / plain font | 16 / RuneScape 16 |
| Plain font line height / “Configuration” advance | 16 / 78 |

The 31-pixel rail includes a 30-pixel FlatLaf tab and one separator. Icon width plus declared tab
insets alone would incorrectly suggest 26. The old native 36 reservation and egui 32 rail are
replaced by `SidebarMetrics`; native canvas resizing and browser clipping/input use its physical
rounding. TSPS's additional Retina-only 1.5 rail zoom is excluded. List row source preference is
20 high with a 5 gap; star control is 21 wide, edit/toggle slots 25 wide. The panel uses owned
RuneScape plain/bold/small fonts and source-derived favorite/toggle images, source colors, square
frames, tooltips and egui focus. Text shaping/rasterization and complex settings controls still
require visual acceptance; using the same TTF does not establish identical text pixels.

## Implemented ownership

There is one egui component in `crates/shell-ui`, used by native and the browser harness. The parallel
Dioxus/Blitz sidebar, feature, dependencies and its feature-only tests are retired. Vendor archives
remain historical. Search includes descriptions/tags, favorites retain stable plugin IDs, settings
scroll areas retain per-plugin identity, and hiding clears focus/drafts without clearing navigation.
The existing prepare → persist → commit configuration path remains authoritative. GPU's unchanged
`dev.rustclient.gpu` ID has an Always on badge in both list and settings header and a configuration
button; unsupported settings show reasons, and negotiated hardware differences show effective values.

Accepted renderer preference interpretation now lives in `GpuSettings::from_preferences`, shared
by native and browser; availability lives in the application configuration owner. Browser harness
F9/F10 shortcuts are diagnostic controls. Other first-party effect controls are explicitly unavailable
there; they do not imply browser game integration. `apps/web/index.html` is only a harness legend and
canvas loader, not a second sidebar. Clipboard and IME adapters feed the same panel. Browser guest
execution and playable cache/transport/session composition remain deferred.

## Lifecycle and event ordering

`PluginSet` owns configured intent independently of Stopped/Starting/Running/Stopping/Faulted status.
Startup obtains a new activation identity, initializes before subscription, and rolls back with
shutdown on failure. Failure leaves configured intent intact but prevents scheduling. Disable and
fault isolation revoke cached output and subscriptions before shutdown; shutdown failure remains
visible and cannot reactivate an instance. Unregister requires a stopped optional instance. Required
services cannot be disabled/unregistered and remain alive across login/logout and renderer rebuilding.
Session-end clears session output without changing activation. Stateful first-party shutdown clears
transient tracker/notification/status/style state. Synchronous output is validated and tagged with
its producing activation; retained output excludes edge notifications, menu edits and camera targets.
No asynchronous guest callback entry point is added; a future one must check the activation identity.

The three-fault default is tracked separately for session, game-event, overlay and output callbacks.
Successful output collection cannot reset an event-handler failure streak. The sidebar exposes actual
status and errors; optional faults recover through disable/enable, startup failures retry on a new
accepted configuration attempt. Required service fault status is diagnostic, never a permission to
replace the engine renderer or silently restart indefinitely.

Native enablement is staged until a current capability-filtered snapshot can be built. A bootstrap
read reuses projection caches but does not consume previous snapshot/timing history, reset peers,
mutate GameState or emit session events. Normal event order stays lifecycle/map, timing, players,
NPCs, variables, skills, containers, widgets, chat. This is a committed projection contract, not proof
that every RuneLite hook has the source's injection timing. Authoritative commits/replay are never
skipped. Query-only first-party plugins declare no event subscription; when no running subscriber
exists the bridge skips event derivation and per-plugin event allocation while advancing history.
Needed snapshots still build for output queries. Per-event grants filter delivery. ABI 0.1 components
keep conservative subscription defaults; executable external ABI 0.2 remains deferred.

## Hooks and API disposition

[The curated ownership table](builtin-plugins/HOOK-API-OWNERS.tsv) records required facts, ordering,
capabilities, consumers, platform reachability and independent evidence. It complements the canonical
[83-event construction inventory](builtin-plugins/RUNELITE-EVENTS.tsv); it does not replace that
inventory or turn a declared enum variant into a working hook.

MenuEntryAdded/MenuOpened/PostMenuSort/MenuOptionClicked and ScriptPostFired remain declared but not
constructed. Emit these only at the owning menu construction/sort/selection and script completion
boundaries, preserving cancellation and nested-script order. Do not infer them from frame-end lists.
GraphicChanged/InteractingChanged/HitsplatApplied remain unreachable from projected empty actor
fields. Retain authoritative packet/client facts before claiming these effects; no fabricated hooks.

TSPS's rebuilt dynamic-child hover fix is useful regression evidence. Stock 240
`classPQ.method9191` rejects an event whose child object no longer matches the parent's current child.
Rust's address-only queued listener resolution can accept a replacement at the same address.
A widget-incarnation/owner-liveness dependency remains open in application listener/pointer routing;
plugin activation identities do not solve widget replacement. Interpreter stacks, mutable injected
client references, renderer callbacks and GPU/platform handles stay outside the external API.

## Acceptance and limits

The evidence JSON records exact commands/results for this work. Five-scale geometry/hit-region
contracts cover 1, 1.25, 1.5, 1.75 and 2. Native canvas contracts cover rounding, rapid toggles,
clamped resizes and monitor scale changes. These are semantic tests, not screenshot goldens.
Search/favorites/navigation, typed actions, slider release/cancel, focus, IME and retained idle/hidden
work have shared component contracts. The GPU probe renders list/settings and reconstructs textures
on a fresh device; its idle workload checks no repeated panel passes/tessellation/uploads.

A bounded CUA inspection observed a RuneLite login-screen sidebar at 2× and compared owned GPU panel
captures. It exposed row alignment/spacing differences corrected in this pass. The live bridge's
status request returned a Java NullPointerException, and accessibility search input did not change
the reference UI. Therefore the live window is not promoted to a fully attributed exact-reference
comparison. Exact pixels, all-scale text rasterization, settings-control shape, monitor/fullscreen/
maximized-window interaction, real OS IME/clipboard and logged-in lifecycle acceptance remain OPEN.
No login or credentials were used. The resolved pinned shell oracle is independent metric evidence.

No speedup is claimed: the current cross-row workload is measured, but a comparable old-panel run at
the same source/font/workload was not executed. Historic DOM timings are not comparable to this pass.
A Rust software renderer is not planned; existing backend boundaries remain intact.
