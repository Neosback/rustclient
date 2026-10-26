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
colors and hotkeys. A validated candidate is applied through its owning service,
then atomically persisted and published as a configuration change. Any failure
retains the previous accepted configuration. Application presentation preferences
have one configuration owner; gameplay-affecting changes use GameState operations.

Acceptance includes required-service enforcement, source-setting inventory,
persistence/recovery, renderer reconstruction, and native/browser control behavior.
Source inventory or stored settings alone do not establish implemented effects.

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
