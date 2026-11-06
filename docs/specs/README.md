# Specification registry

Specs define required behavior; ADRs record decisions. The [current roadmap](../plan/PORTING-ROADMAP.md)
owns execution order, the [domain matrix](../reference/rs240-port/PORT-MATRIX.md)
owns implementation status and the [current audit](../reference/rs240-port/CURRENT-AUDIT.md)
owns reconciliation findings. A spec's accepted status does not mean its implementation
or acceptance is complete. This registry summarizes declared scope, reviewed 2026-11-01;
the linked spec remains authoritative.

## Status and change rules

- **Draft:** design is open; explicitly approved subsections keep their stated scope.
- **Accepted:** required implementation target; incompatible changes need deliberate amendment/ADR.
- **Implemented:** evidence establishes the named acceptance scope, not all later extensions.
- **Superseded:** replaced within the stated scope; retain historical criteria and evidence.

Every substantive spec must state purpose/scope, owners, invariants, behavior,
failure handling, non-goals and acceptance criteria; include performance constraints
when applicable. Identify the semantic owner and source anchors, amend the contract
before large changes, implement focused semantic/reset/replay/lifecycle cases, then
record exact validation and open gates. Do not weaken accepted requirements to match code.

## Existing specifications

| Spec | Declared status / scope | Responsibility and limit |
| --- | --- | --- |
| [SPEC-0001-system-architecture](SPEC-0001-system-architecture.md) | Accepted for foundation | System architecture and crate ownership; remaining shared-input ownership violations stay open. |
| [SPEC-0002-state-event-model](SPEC-0002-state-event-model.md) | Draft | Authoritative state, operations, deltas and replay. |
| [SPEC-0003-platform-native-web](SPEC-0003-platform-native-web.md) | Draft | Native/web adapters, bounded jobs and platform capabilities. |
| [SPEC-0004-plugin-system](SPEC-0004-plugin-system.md) | Draft with approved continuations | Plugin platform, capability/lifecycle mediation and retained panels; executable ABI 0.2 deferred. |
| [SPEC-0005-protocol-revisions](SPEC-0005-protocol-revisions.md) | Draft | Protocol/revision profile boundaries. |
| [SPEC-0006-cache-assets](SPEC-0006-cache-assets.md) | Draft | Cache/assets/definitions. |
| [SPEC-0007-rendering-ui](SPEC-0007-rendering-ui.md) | Draft with approved continuations | Rendering projections and shared retained sidebar; visuals remain open. |
| [SPEC-0008-testing-observability](SPEC-0008-testing-observability.md) | Draft | Testing, observability and determinism; ADR-0004 governs rendering acceptance. |
| [SPEC-0009-security-capabilities](SPEC-0009-security-capabilities.md) | Draft | Security/capabilities and reflection policy. |
| [SPEC-0010-roadmap](SPEC-0010-roadmap.md) | Accepted historical decomposition | Original foundation exit criteria remain scoped; execution order superseded by the current roadmap. |
| [SPEC-0011-plugin-authoring-experience](SPEC-0011-plugin-authoring-experience.md) | Draft | Plugin authoring/SDK and scripting direction. |
| [SPEC-0012-semantic-model](SPEC-0012-semantic-model.md) | Draft | Dependency-light semantic IDs, units, coordinates and time. |
| [SPEC-0013-offline-title](SPEC-0013-offline-title.md) | Implemented focused milestone | First offline cache-backed desktop title, 2026-10-21; not full title/visual parity. |
| [SPEC-0014-openrune-login](SPEC-0014-openrune-login.md) | Implemented focused milestone | OpenRune login bootstrap; not complete login/reconnect/session parity. |
| [SPEC-0015-spawn-scene](SPEC-0015-spawn-scene.md) | Implementation milestone | Initial static spawn preview; visual approximations superseded by SPEC-0016. |
| [SPEC-0016-reference-scenery-parity](SPEC-0016-reference-scenery-parity.md) | Implementing | Reference scenery/GPU/camera parity; acceptance incomplete. |
| [SPEC-0017-gameframe-entities](SPEC-0017-gameframe-entities.md) | Approved implementation milestone | Gameframe/entities/location animation; full actor/effects parity incomplete. |
| [SPEC-0018-playable-desktop](SPEC-0018-playable-desktop.md) | Accepted milestone, in progress | Playable desktop walk/bank; remaining actions/gameplay acceptance open. |
| [SPEC-0019-port-parity](SPEC-0019-port-parity.md) | Accepted contract, incomplete | Observable stock 240 and applicable RuneLite parity, deviations and evidence gates. |
| [SPEC-0020-client-cycle](SPEC-0020-client-cycle.md) | Accepted for implementation | Shared cycle/session order; queue/flush subset implemented, full orchestration open. |
| [SPEC-0021-input-menus](SPEC-0021-input-menus.md) | Draft; arbitration section accepted | Input/picking/menus; accepted 2026-10-29 arbitration subset does not accept the whole draft. |
| [SPEC-0023-map-loading-state](SPEC-0023-map-loading-state.md) | Accepted retained-scene slice | Keep prior scene until replacement; instances/world views/teardown remain open. |
| [SPEC-0024-zone-updates](SPEC-0024-zone-updates.md) | Accepted incremental-location milestone | Typed zone resources, bounded preparation/upload and coherent publication; live/scored acceptance remains open. |
| [SPEC-0025-interface-script-host](SPEC-0025-interface-script-host.md) | Accepted scoped slice | Item search, IF1, deferred operations and interface/script audits; full host acceptance open. |
| [SPEC-0030-render-profiles](SPEC-0030-render-profiles.md) | Proposed | Render profiles (GPU, 117HD, future 2008HD) and capability negotiation; HD work waits for the 117HD source. |

## Reserved, not yet authored

The roadmap retains these reservations; they are neither accepted contracts nor
completed implementation milestones. Write and accept the relevant spec when its
substantial work begins, extending existing owners and contracts as appropriate.

| Number | Intended scope |
| --- | --- |
| SPEC-0022 | Actor synchronization, rich facing and effects |
| SPEC-0026 | Variables, chat, social and trading |
| SPEC-0027 | Audio |
| SPEC-0028 | Preferences, world map and world list |

[Historical checkpoints](../archive/README.md) retain exact past commands and
foundation criteria. They do not provide current execution instructions. Architecture
changes still require an accepted spec/ADR under the root instructions.
