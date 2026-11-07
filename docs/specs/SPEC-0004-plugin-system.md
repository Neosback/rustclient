# SPEC-0004: Plugin Platform and ABI

**Status:** Draft

## Lifecycle continuation (approved 2026-10-29)

Configured enablement and operational status are separate. The host owns stopped,
starting, running, stopping and faulted states, activation identities and diagnostics.
Only running instances receive events or contribute output. Startup receives a
current, capability-mediated snapshot without advancing shared projection history.
Failure rolls back subscriptions/output and invokes cleanup once. Disable/unload
revokes scheduling before cleanup; cleanup failure cannot restore stale output.
Session Started/Stopped remain distinct from instance activation/shutdown.

The default runtime fault threshold remains three failures per callback phase;
success in another phase does not reset that streak. Startup failure immediately
prevents scheduling. Required registrations are retained on faults and expose a
diagnostic rather than an effective-looking optional switch. Event interests are
host-side scheduling hints intersected with grants, never authorization. Default
interest is all supported events for compatibility. Core commits/replay are not
suppressed by plugin interest. The external 0.1 ABI remains supported; executable
0.2 binding remains deferred. Internal lifecycle extensions do not add guest imports.

Acceptance includes startup while logged in, rollback, cleanup failure, per-phase
fault isolation, reactivation, stale activation output, current snapshot isolation,
capability filtering and required-service/session independence.

## Applicable RuneLite plugin completion (approved 2026-10-31)

The target is every applicable feature in the pinned bundled-plugin catalog, with
local portions of mixed local/service plugins tracked separately. Remote accounts,
cloud synchronization, public price/hiscore providers, telemetry and Discord/Twitch
remain deferred. External ABI 0.1 remains compatible; executable 0.2 remains deferred.
Stock 240 owns gameplay and timing, RuneLite owns plugin effects/visuals, and TSPS
supplies independently verified implementation ideas. Existing canonical inventories
and per-plugin/source-case ledgers own completion evidence.

The Rust call context keeps aggregate views, storage adapters and log sinks private.
Public accessors filter reads by grants, bind persistence and diagnostics to the
executing plugin, and provide an owned filtered 0.1 view to its runtime adapter.

Gameplay subscriptions declare event kinds, source subscriber class and finite
source priority. Dispatch is event-major in committed order: subscribers for each
event run in descending priority, then source class name, then registration order.
Declarations are immutable for a registration. Scheduling intersects declarations,
running status and grants. Dispatch borrows events; it must not clone a payload
vector for each plugin. Query-only plugins require no event dispatch. This extends
the host-side contract only and adds no ABI 0.1 guest imports.

Client lifecycle queries use authoritative session/displayed-scene state. Renderer
reconstruction and replacement preparation do not manufacture semantic loading/login
transitions while a committed scene remains displayed (SPEC-0023).

Actor observations expose the existing semantic owner's incarnation, distinct from
a reusable slot. Public lifetime handles bind session, actor kind/slot and incarnation;
unknown identity is explicit. Replacing an actor in one slot yields despawn then
spawn, even if its appearance is identical. Projection metadata cannot create
missing combat facts. Later source-stage hooks supplement snapshot differences;
the menu hooks are emitted by the synchronous menu stage (SPEC-0021) and
`ScriptPostFired` by the script host once per finished invocation, delivered at the next
update.

Acceptance includes event-major ordering, source priority/ties, kind/grant filtering,
route invalidation on unload/re-register, per-callback fault isolation, actor index
reuse and bootstrap/history isolation. Plugin panels, resources, named services and
stage-bound commands require their documented capability and owning consumer before
being reported as working. No raw mutable client, interpreter or GPU access is added.

## Required built-ins (approved 2026-10-25)

The host registers `dev.rustclient.gpu` and `dev.rustclient.runelite` as required
application-lifetime services, active before login. Disable/uninstall requests are
rejected at the host boundary. The GPU service configures the engine renderer;
GPU resources remain renderer-owned. Repeated service faults retain the required
registration and last working renderer configuration, with actionable diagnostics.
Optional external plugins retain their existing fault-isolation policy and 0.1 ABI.

Configuration descriptors carry source keys, sections, defaults, ranges, choices,
colors and hotkeys. A validated candidate prepares its owning service effect without publishing,
is atomically persisted, then commits the prepared effect and configuration-change publication. Any failure
retains the previous accepted configuration. Application presentation preferences
have one configuration owner; gameplay-affecting changes use GameState operations.

Acceptance includes required-service enforcement, source-setting inventory,
persistence/recovery, renderer reconstruction, and native/browser control behavior.
Source inventory or stored settings alone do not establish implemented effects.

## Plugin API 0.2 (approved 2026-10-25, ADR-0006)

API 0.2 is Rust-native and RuneLite-readable: names, events and record shapes follow the RuneLite API so ported plugin
code reads naturally, while plugins observe immutable projections and return declarative output instead of holding
mutable client objects. Compatibility: `PluginApiVersion::current()` is `0.2`; 0.1 packages load unchanged.

**Observation.** The host builds one `GameSnapshot` per update from `GameState` and immutable cache definitions
(players, NPCs, skills, varps/varbits, containers, widgets with resolved bounds, chat, client facts). `GameEvent`s are
derived by diffing consecutive projections in a fixed order: lifecycle/map, timing, players, NPCs, variables, stats,
containers, widgets, chat. The projector (`rustclient-plugin-bridge`) retains only the previous projection.

**Output.** `PluginOutput` is level-triggered per update: overlay primitives (layered), notifications, info boxes, menu
edits, entity visibility, sprite overrides, camera commands, scene flags, stretch. The host validates every item
against grants and bounds (`plugin_api::limits`), merges across plugins in load order and passes the result to the
owning subsystem. The native shell draws screen, world (`Tile`, `ActorHull`, `ActorText`, `TileText`) and minimap
(`MinimapDot`) overlay commands and info boxes; camera target and zoom-limit commands ease the camera (`CAMERA-TARGET-EASING`).
Ground items are readable with `game.read.ground_items` (SPEC-0029). Plugins never call the renderer or mutate `GameState`.

**Capabilities.** Each query, event class and output kind maps to one capability and is checked at the host boundary:
`game.read.{players,npcs,inventory,widgets,vars,chat,skills,client}`, `ui.{overlay,menu.swap,entities.hide,
sprite.override,infobox,viewport,menu.decorate}`, `scene.control`, `camera.control`, `client.notify`, `client.frame_limit`, `ui.minimap`, `ui.panel`. `network.http` and `interaction.request` are reserved: no 0.2 operation exists for
them, so granting them changes nothing.
The first denial of each capability per plugin is logged; repeats are silent.

**Scene output.** `scene` carries RuneLite-style roof removal mode bits (`ROOF_FLAG_POSITION=1`, `HOVERED=2`,
`DESTINATION=4`, `BETWEEN=8`) and animation-smoothing classes, with `smoothing_denied` listing animation ids that stay on whole frames
(the source plugin's interpolation filter); `roof_areas` lists world-tile blocks forced roofed (the
source plugin's area overrides). The scene crate owns roof regions (`RoofVolumes`): the host never exposes roof ids.
`ClientSnapshot::scene_base/scene_size` are the loaded scene's south-west tile and extent (`Scene.getBaseX/Y`).

**Overlay and pacing output.** Overlay `Text` and `Sprite` commands carry a `HorizontalAlign` (left, center, right) resolved by the
shell with the font metrics, so a plugin never measures text. `Sprite` names a cache sprite; the shell requests it from the
same worker that decodes widget sprites and skips it until it is loaded. `frame_limit` (capability `client.frame_limit`) asks the
shell to present at most that many frames per second; the host clamps it and the lowest limit among plugins applies.
`minimap` (capability `ui.minimap`) asks for mouse-wheel zoom on the minimap, a zoom level to apply once (clamped to 2..=8 pixels per tile),
hiding the minimap and orbs, and one replacement color per map dot kind; the shell owns the zoom value, the sprite replacement and the
widget visibility, and `ClientSnapshot::minimap_zoom` reports the current level.
`menu_offers` (capability `ui.menu.decorate`, at most 8 per update, texts at most 64 bytes) asks the shell to add `Client.createMenuEntry`-style
entries to the world menu opened on a given tile, optionally only while Shift is held. They are redeclared every update, are appended
below "Walk here" (never the left-click default), and choosing one delivers `MenuOptionClicked` with kind
`Other(PLUGIN_MENU_TYPE = 1500)`, the offer's option and target, and the tile's world x and y in `param0`/`param1`, to plugins that hold
`ui.menu.swap`. Stock world menu entries are projected too, and choosing one delivers `MenuOptionClicked` the same way.
`menu_edits` are collected synchronously from `PostMenuSort` subscribers during the menu stage (SPEC-0021); the
level-triggered `menu_edits` of an ordinary update are not applied.

**First-party plugins and configuration.** In-tree ports live in `crates/first-party-plugins` (one module each) and
implement `NativePlugin`, which adapts to `PluginInstance`. Their settings are schema-driven like the required services:
`first-party-schemas.json` (generated from the vendored `*Config.java`) is loaded by the application's configuration
owner, persisted per plugin, and delivered read-only to the instance as `PluginCall::config` (defaults applied, mistyped
reads are zero values). Every optional plugin's schema starts with an `enabled` setting (`enabledByDefault`); the
sidebar renders it as the card switch and `PluginSet::configure` applies it, re-initializing a plugin that is switched on.
Schema controls: boolean, integer, choice, color, hotkey, size, font, text and decimal (thousandths).

**Facts the client does not retain yet** (spot animations, interact target, hitsplats, health bars, menu model) appear
as absent fields/events, never guessed; each is a semantic-owner extension tracked in the plugin ledger.

**Failure behavior.** A faulting plugin follows the existing isolation policy; required services are never disabled.
Sprite overrides are bounded to 1024 pixels per edge, 256 sprites and 4 MiB per plugin. Malformed output items (oversized sprites, non-finite camera speed, inverted zoom limits) are dropped individually.

**WIT.** `wit/rustclient/plugin-0.2` mirrors these records for cross-language plugins. The wasm runtime still
instantiates the `0.1.0` world; binding `0.2.0` is separate work. In-tree plugins implement `PluginInstance` directly.

**Acceptance.** Denial tests for every gated query and output kind; event filtering by grant; deterministic event order;
0.1 manifests load; `GameState` operations project to the documented snapshot fields; faults never disable required
services.

## Goal

Provide a powerful RuneLite-like developer experience while keeping plugins cross-platform, versionable, observable, sandboxable, and decoupled from Rust compiler ABI details.

## ABI

The intended external ABI is WebAssembly Component Model interfaces defined in `wit/`. WIT is canonical for cross-language contracts. Rust SDK types/wrappers are generated/adapted from that contract rather than becoming the contract themselves.

## Runtime model

Native and browser plugin runtimes implement a common host contract:

```text
manifest -> compatibility check -> capability grant -> instantiate
         -> start -> event batches / queries / commands -> stop
```

A plugin cannot directly mutate `GameState`. It observes approved snapshots/events and submits commands such as overlay primitives, panel/UI descriptions, notifications, menu decorations, and explicitly permitted interaction requests.

## Manifest

A plugin package eventually declares at least:

- stable plugin ID,
- name/version/author,
- plugin API version range,
- required and optional capabilities,
- target support,
- entry component,
- configuration schema/version,
- dependencies/conflicts only if we deliberately support them.

## Capabilities

Examples:

- `game.read.players`
- `game.read.npcs`
- `game.read.inventory`
- `game.read.widgets`
- `ui.overlay`
- `ui.panel`
- `ui.menu.decorate`
- `storage.plugin`
- `network.http` (high-trust/explicit)
- `interaction.request` (server/client policy controlled)

Capabilities are deny-by-default for external plugins.

## Performance

Do not emit thousands of ABI calls per frame when a batch works. Prefer event batches, snapshot/query batches, and overlay command buffers. Plugin work receives time/fuel budgets where the runtime supports them. Slow/faulting plugins are isolated and diagnosable.

## First-party plugins

Built-in features may use native Rust implementations for efficiency but should target the same semantic service boundaries where practical. The public ABI is not required inside every hot internal loop.

## Developer experience

Target tooling:

- project template,
- local hot reload,
- event inspector,
- API explorer,
- overlay/UI preview,
- per-plugin logs/metrics,
- deterministic replay-based tests,
- manifest/config validation.

## Compatibility

Public plugin interfaces use explicit semantic versioning. Additive evolution is preferred. Removal/change requires deprecation windows or a major interface version.

## Acceptance criteria for first vertical slice

A sample plugin must run from the same source on native and browser targets, receive one event type, query one state value, persist plugin-local config/storage, and render an overlay command without accessing internal client structs.

## Local XP Tracker core and overlay (authorized 2026-10-29)

The first-party XP tracker observes existing immutable skill snapshots/events. Its
local state is derived plugin state, never authoritative XP. The core accepts
explicit elapsed milliseconds, pause/logout policy, goals and an explicit account
key for saved-state envelopes. It preserves the RuneLite ten-action history,
60-second minimum rate denominator, XP-loss reinitialization and rate-only reset.
The live adapter uses snapshot client cycles and suppresses the first two game
ticks of login synchronization. An account change resets its local baseline.

Only effective settings appear for this initial canvas overlay: hide-maxed,
idle pause, idle rate reset and recent-skill ordering. Logout uses the source
default (paused); its toggle awaits a real elapsed-time service. The overlay
shows session XP, XP/hour and action count. Core goal/save/manual-pause contracts
are independently testable; no panel/menu/persistence control is exposed until
stable account identity, elapsed real time and those services reach the plugin
boundary. Display names are not silently promoted to persistent account IDs.
The complete RuneLite tracker, external sync and native/browser visual acceptance
remain separate obligations in the canonical plugin ledger.

### Retained in-tree panels

In-tree API 0.2 panels are immutable, revisioned documents of bounded flat blocks,
rendered by the shared egui shell. They carry supported owned navigation icons,
source priority, text/metric rows, buttons and focus-committed text editors. `ui.panel`
is required. The host validates ids, duplicate interactive ids and total text bytes;
it rejects invalid documents as a whole. A returned action identifies the plugin,
activation, panel revision and element, and is accepted only against that plugin's
last validated running output and the element's declared value kind/size. It is
never an arbitrary game command. Shutdown immediately revokes panels and actions.
The shell retains drafts and focus independently of configuration controls; ordinary
content publication must not reset IME, undo or search. In-tree presentation does not
make external component ABI 0.2 executable. Notes initially uses existing plugin-local
storage: RuneLite profile switching and exact Swing undo grouping remain open parity
items until their shared owners exist. Text budgets are explicit host limits, not
claims that the source imposes the same limits.

Projection history shares the immutable snapshot root; ordinary history retention must not clone its actor/chat/container payloads. Newly queried domains establish a baseline without reporting their existing contents as fresh shared gameplay events. Private bootstrap reads do not advance that history.

Each mounted panel also has a host-owned publication lease. Removal, callback failure or changed content revokes it, even if a plugin reuses its document id and revision within one activation. Unchanged documents retain the lease. UI actions must match this lease as well as the activation, panel revision and element.

Session stop expires cached game/world effects while activation-owned local panels remain mounted. Native title/login cycles continue plugin query/output and panel-action processing without inventing gameplay ticks. Actual disable, unload or fault still revokes panels, publication leases and owned editor/undo state.

Call identity, grant and accepted-configuration references are private as well as the aggregate view/storage/log sinks. Plugins receive read-only accessors and cannot rebind authority references on a mutable call context; compile-fail API contracts enforce that boundary.
