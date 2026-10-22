# RustClient Architecture

This document is the compact architectural map. Detailed normative requirements live in `docs/specs/`; decisions live in `docs/adr/`.

## Product boundary

RustClient is an OSRS-style client platform for private servers. It is not designed around a single server revision. A server is described by explicit protocol/cache/capability profiles.

## Initial delivery target

OpenRune 240.2 is the first concrete server target. The product aims for
RuneLite-class client features with simpler plugin authoring and the same core
on desktop and browser Wasm. RuneLite/deob 235 and both directions of rsprox
packet codecs are behavioral evidence; OpenRune's selected rsprot 240 sources
and cache resolve revision differences. OpenRune FileStore supplies cache decoder
research; RustOSRS supplies additional scene/GPU evidence. See
[the source map](docs/reference/INDEX.md) and
[delivery status](docs/plan/REVISION-240-delivery.md).

## Core rule

`GameState` is the single authoritative semantic state of the running client.

Inputs such as decoded packets, user intents, cache definitions, scripts, and timers produce validated domain operations. Those operations transition `GameState`. Rendering, UI presentation, plugins, telemetry, and replays observe committed state/deltas.

```text
platform input / network bytes / cache / clock
                    |
                    v
       revision-specific adapters
                    |
                    v
          semantic domain operations
                    |
                    v
              GameState
           SINGLE WRITER
          /     |      \
         v      v       v
  RenderWorld  UI   Plugin snapshots/events
```

## Architectural layers

1. **Model**: dependency-light IDs, coordinates, value objects, shared semantic enums.
2. **Protocol/cache adapters**: transform revision-specific bytes into semantic values and back where appropriate.
3. **Game state/domain**: authoritative entities, var state, inventories, widgets, scene semantics, clocks, transitions.
4. **Application runtime**: lifecycle, scheduling, batching, orchestration.
5. **Projection systems**: renderer, shell UI, plugin views, telemetry, replay capture.
6. **Platform adapters**: native/browser transport, storage, clipboard, window/canvas, audio backend, clocks.
7. **Applications**: `apps/native` and `apps/web` compose the same core with different platform implementations.

## Dependency direction

Dependencies point inward toward semantics.

- `model` depends on no client subsystem.
- `protocol` and `cache` may depend on `model`; they do not depend on renderer or UI.
- `game-state` may depend on `model`; it does not depend on platform, renderer, plugin runtime, or apps.
- `renderer` consumes semantic scene/render projections; it does not own game truth.
- `plugin-api` contains deliberately public/versioned types and cannot expose internal state objects.
- `plugin-host` mediates capabilities, snapshots, events, commands, budgets, lifecycle, and compatibility.
- `platform-*` implement contracts from `platform`; core crates do not call OS/browser APIs directly.
- `apps/*` are composition roots and may depend outward as needed.

## Plugin boundary

Plugins are untrusted extensions. The target ABI is WIT/WebAssembly Components. Native uses a Wasm runtime; web uses browser-compatible component transpilation/instantiation. Plugins receive capabilities and semantic snapshots/events and return commands. They never receive mutable pointers/references to `GameState`, renderer internals, sockets, files, or GPU resources.

First-party features may be compiled in while still using the same semantic plugin-facing services when practical. Internal core features are not required to pay a Wasm crossing cost.

## Revision model

Protocol revisions are data/code profiles selected during connection bootstrap. Revision-specific IDs/opcodes/encodings stay inside protocol/cache profile modules. Higher layers consume stable semantic concepts. Avoid `if revision == ...` outside adapter/profile crates.

## Native + web

Shared code must not assume threads, filesystem paths, raw TCP, blocking I/O, or native window handles. Those are capabilities supplied by platform adapters.

Native private-server connections may use TCP/UDP/WebSocket as supported by the server profile. Browser connections require browser-compatible transports, normally secure WebSocket/WebTransport through a server or relay that explicitly supports them.

## UI split

- `game-ui`: game widgets/interfaces, menus, fonts, inventories, client scripts, and OSRS-style presentation semantics.
- `shell-ui`: RustClient chrome such as settings, plugin management, diagnostics, developer tools.

Plugins use RustClient UI/overlay commands, not direct `egui` or renderer access.

## Specifications

Any behavior that affects public APIs, state ownership, protocol correctness, persistence, replay format, rendering semantics, plugin capabilities, or platform parity must have a spec or ADR before broad implementation.
