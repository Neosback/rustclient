# SPEC-0004: Plugin Platform and ABI

**Status:** Draft

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
owning subsystem. Plugins never call the renderer or mutate `GameState`.

**Capabilities.** Each query, event class and output kind maps to one capability and is checked at the host boundary:
`game.read.{players,npcs,inventory,widgets,vars,chat,skills,client}`, `ui.{overlay,menu.swap,entities.hide,
sprite.override,infobox,viewport}`, `scene.control`, `camera.control`, `client.notify`, `client.frame_limit`. `ui.panel`, `ui.menu.decorate`,
`network.http`, `interaction.request` are reserved: no 0.2 operation exists for them, so granting them changes nothing.
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
