# RustClient documentation

Start with [ARCHITECTURE.md](../ARCHITECTURE.md), [the parity contract](specs/SPEC-0019-port-parity.md), then [the porting roadmap](plan/PORTING-ROADMAP.md).

The roadmap is the current execution order. [The matrix](reference/rs240-port/PORT-MATRIX.md) owns domain status; [the audit](reference/rs240-port/CURRENT-AUDIT.md) owns the latest review findings. Update these owners instead of appending another competing current plan.

Specs and ADRs retain their declared decision/status scope. A mismatch between code and a ratified contract stays an open obligation. Source research and historical test logs do not silently amend that contract.

Historical checkpoints remain at stable paths for source citations and exact validation records. Their “current”, “next”, counts and completion statements apply only to their recorded snapshot. They do not direct new work. The HD feature menu is optional research after classic parity.

Generated index: `python3 tools/docs_index.py --write`; check with `--check`. This classifies document authority; it does not certify every factual sentence.

Current machine-readable data: [domain matrix](reference/rs240-port/PORT-MATRIX.tsv), [packet coverage](reference/rs240-port/PACKET-COVERAGE.tsv), [method ledger](reference/rs240-port/SYMBOL-LEDGER.tsv), [review baseline](reference/rs240-port/REVIEW-BASELINE.json), [source pins](reference/source-manifest.json), [deviations](plan/PARITY-DEVIATIONS.tsv), [CS2 host coverage](reference/rs240-port/CS2-HOST-COVERAGE.tsv), [RuneLite plugins](reference/builtin-plugins/RUNELITE-PLUGINS.tsv) and [events](reference/builtin-plugins/RUNELITE-EVENTS.tsv) (`tools/parity_inventory.py`), GPU/first-party JSON/TSV ledgers. Source mappings, dispatch paths and test presence are distinct from acceptance.

## Normative specifications

- [specs/README.md](specs/README.md)
- [specs/SPEC-0001-system-architecture.md](specs/SPEC-0001-system-architecture.md)
- [specs/SPEC-0002-state-event-model.md](specs/SPEC-0002-state-event-model.md)
- [specs/SPEC-0003-platform-native-web.md](specs/SPEC-0003-platform-native-web.md)
- [specs/SPEC-0004-plugin-system.md](specs/SPEC-0004-plugin-system.md)
- [specs/SPEC-0005-protocol-revisions.md](specs/SPEC-0005-protocol-revisions.md)
- [specs/SPEC-0006-cache-assets.md](specs/SPEC-0006-cache-assets.md)
- [specs/SPEC-0007-rendering-ui.md](specs/SPEC-0007-rendering-ui.md)
- [specs/SPEC-0008-testing-observability.md](specs/SPEC-0008-testing-observability.md)
- [specs/SPEC-0009-security-capabilities.md](specs/SPEC-0009-security-capabilities.md)
- [specs/SPEC-0010-roadmap.md](specs/SPEC-0010-roadmap.md)
- [specs/SPEC-0011-plugin-authoring-experience.md](specs/SPEC-0011-plugin-authoring-experience.md)
- [specs/SPEC-0012-semantic-model.md](specs/SPEC-0012-semantic-model.md)
- [specs/SPEC-0013-offline-title.md](specs/SPEC-0013-offline-title.md)
- [specs/SPEC-0014-openrune-login.md](specs/SPEC-0014-openrune-login.md)
- [specs/SPEC-0015-spawn-scene.md](specs/SPEC-0015-spawn-scene.md)
- [specs/SPEC-0016-reference-scenery-parity.md](specs/SPEC-0016-reference-scenery-parity.md)
- [specs/SPEC-0017-gameframe-entities.md](specs/SPEC-0017-gameframe-entities.md)
- [specs/SPEC-0018-playable-desktop.md](specs/SPEC-0018-playable-desktop.md)
- [specs/SPEC-0019-port-parity.md](specs/SPEC-0019-port-parity.md)

## Architecture decisions

- [adr/ADR-0001-plugin-abi.md](adr/ADR-0001-plugin-abi.md)
- [adr/ADR-0002-authoritative-state.md](adr/ADR-0002-authoritative-state.md)
- [adr/ADR-0003-platform-boundary.md](adr/ADR-0003-platform-boundary.md)
- [adr/ADR-0004-verification-policy.md](adr/ADR-0004-verification-policy.md)
- [adr/ADR-0005-readable-client-names.md](adr/ADR-0005-readable-client-names.md)
- [adr/ADR-0006-plugin-api-0-2.md](adr/ADR-0006-plugin-api-0-2.md)
- [adr/ADR-0007-on-demand-gpu-shell.md](adr/ADR-0007-on-demand-gpu-shell.md)
- [adr/README.md](adr/README.md)

## Current execution and audit owners

- [plan/MASTER_PLAN.md](plan/MASTER_PLAN.md)
- [plan/PORTING-ROADMAP.md](plan/PORTING-ROADMAP.md)
- [plan/REVISION-240-delivery.md](plan/REVISION-240-delivery.md)
- [reference/INDEX.md](reference/INDEX.md)
- [reference/REVISION-240.md](reference/REVISION-240.md)
- [reference/builtin-plugins/FIRST-PARTY-LEDGER.md](reference/builtin-plugins/FIRST-PARTY-LEDGER.md)
- [reference/builtin-plugins/GPU-AUDIT.md](reference/builtin-plugins/GPU-AUDIT.md)
- [reference/rs240-port/CURRENT-AUDIT.md](reference/rs240-port/CURRENT-AUDIT.md)
- [reference/rs240-port/PORT-MATRIX.md](reference/rs240-port/PORT-MATRIX.md)
- [reference/rs240-port/README.md](reference/rs240-port/README.md)

## Domain research and source ledgers

- [patches/README.md](patches/README.md)
- [reference/REVISION-235-TO-240.md](reference/REVISION-235-TO-240.md)
- [reference/rs240-port/ACTOR-CATCH-UP.md](reference/rs240-port/ACTOR-CATCH-UP.md)
- [reference/rs240-port/ACTOR-PATH-QUEUE.md](reference/rs240-port/ACTOR-PATH-QUEUE.md)
- [reference/rs240-port/ACTOR-RUNTIME.md](reference/rs240-port/ACTOR-RUNTIME.md)
- [reference/rs240-port/FACING-TURNING.md](reference/rs240-port/FACING-TURNING.md)
- [reference/rs240-port/FORCED-MOVEMENT.md](reference/rs240-port/FORCED-MOVEMENT.md)
- [reference/rs240-port/GAMEFRAME-INTERFACES.md](reference/rs240-port/GAMEFRAME-INTERFACES.md)
- [reference/rs240-port/LOGIN-REGION-INTERFACES.md](reference/rs240-port/LOGIN-REGION-INTERFACES.md)
- [reference/rs240-port/MOVEMENT-FACING.md](reference/rs240-port/MOVEMENT-FACING.md)
- [reference/rs240-port/NPC-PATH-ACCELERATION.md](reference/rs240-port/NPC-PATH-ACCELERATION.md)
- [reference/rs240-port/PLAYER-APPEARANCE.md](reference/rs240-port/PLAYER-APPEARANCE.md)
- [reference/rs240-port/PLAYER-EXTENDED-STATE.md](reference/rs240-port/PLAYER-EXTENDED-STATE.md)
- [reference/rs240-port/SEQUENCE-SCHEDULER.md](reference/rs240-port/SEQUENCE-SCHEDULER.md)
- [reference/rs240-port/WORLD-EFFECTS.md](reference/rs240-port/WORLD-EFFECTS.md)

## Historical implementation checkpoints

- [plan/BUILTIN-PLUGINS.md](plan/BUILTIN-PLUGINS.md)
- [plan/CHAT-ACTORS-240.md](plan/CHAT-ACTORS-240.md)
- [plan/CHECKPOINT_TEMPLATE.md](plan/CHECKPOINT_TEMPLATE.md)
- [plan/CP0-reference-infrastructure.md](plan/CP0-reference-infrastructure.md)
- [plan/CP1-semantic-model.md](plan/CP1-semantic-model.md)
- [plan/CP11-plugin-slice.md](plan/CP11-plugin-slice.md)
- [plan/CP2A-platform-capabilities.md](plan/CP2A-platform-capabilities.md)
- [plan/CP2B-shared-renderer-core.md](plan/CP2B-shared-renderer-core.md)
- [plan/CP2C-native-composition.md](plan/CP2C-native-composition.md)
- [plan/CP2D-web-composition.md](plan/CP2D-web-composition.md)
- [plan/CP3A-game-state-root.md](plan/CP3A-game-state-root.md)
- [plan/CP3B-tick-transition.md](plan/CP3B-tick-transition.md)
- [plan/CP3C-semantic-operation-boundary.md](plan/CP3C-semantic-operation-boundary.md)
- [plan/CP3D-operation-batches.md](plan/CP3D-operation-batches.md)
- [plan/CP3E-committed-events-delta.md](plan/CP3E-committed-events-delta.md)
- [plan/CP3F-semantic-replay-envelope.md](plan/CP3F-semantic-replay-envelope.md)
- [plan/CP3G-session-revision-identity.md](plan/CP3G-session-revision-identity.md)
- [plan/CP3H-cp3-exit-audit.md](plan/CP3H-cp3-exit-audit.md)
- [plan/CP3I-deterministic-randomness.md](plan/CP3I-deterministic-randomness.md)
- [plan/CP3J-semantic-fingerprints.md](plan/CP3J-semantic-fingerprints.md)
- [plan/ENTITY-assets.md](plan/ENTITY-assets.md)
- [plan/GAMEFRAME-240.md](plan/GAMEFRAME-240.md)
- [plan/GRAPHICS-FEATURE-MENU.md](plan/GRAPHICS-FEATURE-MENU.md)
- [plan/NPC-ANIMATION-240.md](plan/NPC-ANIMATION-240.md)
- [plan/OPENRUNE-login.md](plan/OPENRUNE-login.md)
- [plan/PLAYABLE-desktop.md](plan/PLAYABLE-desktop.md)
- [plan/PLAYER-UPDATING-240.md](plan/PLAYER-UPDATING-240.md)
- [plan/RENDER-GAMEFRAME-CONTINUATION-240.md](plan/RENDER-GAMEFRAME-CONTINUATION-240.md)
- [plan/SCENE-DEPTH-240.md](plan/SCENE-DEPTH-240.md)
- [plan/SIDEBAR-PERFORMANCE-2026-10-28.md](plan/SIDEBAR-PERFORMANCE-2026-10-28.md)
- [plan/SIDEBAR-PORT-AUDIT-2026-10-26.md](plan/SIDEBAR-PORT-AUDIT-2026-10-26.md)
- [plan/SPAWN-scene.md](plan/SPAWN-scene.md)
- [plan/TITLE-offline-cache.md](plan/TITLE-offline-cache.md)
- [reference/builtin-plugins/GPU-ACTOR-DEPTH-2026-10-27.md](reference/builtin-plugins/GPU-ACTOR-DEPTH-2026-10-27.md)
- [reference/builtin-plugins/GPU-MODEL-SORT-2026-10-28.md](reference/builtin-plugins/GPU-MODEL-SORT-2026-10-28.md)
- [reference/builtin-plugins/GPU-OVERLAY-2026-10-27.md](reference/builtin-plugins/GPU-OVERLAY-2026-10-27.md)
- [reference/builtin-plugins/GPU-SPARSE-MAPS-2026-10-27.md](reference/builtin-plugins/GPU-SPARSE-MAPS-2026-10-27.md)
- [reference/builtin-plugins/GPU-ZONES-2026-10-27.md](reference/builtin-plugins/GPU-ZONES-2026-10-27.md)

## Reference tooling and verification records

- [reference/client-240/CLEANUP-WORKFLOW.md](reference/client-240/CLEANUP-WORKFLOW.md)
- [reference/client-240/DECOMPILER-QUALITY.md](reference/client-240/DECOMPILER-QUALITY.md)
- [reference/client-240/DEOB-V2.md](reference/client-240/DEOB-V2.md)
- [reference/client-240/MAPPING-BASELINE.md](reference/client-240/MAPPING-BASELINE.md)
- [reference/client-240/OPCODES.md](reference/client-240/OPCODES.md)
- [reference/client-240/README.md](reference/client-240/README.md)
- [reference/client-240/clusters/01-buffer-framing.md](reference/client-240/clusters/01-buffer-framing.md)
- [reference/client-240/clusters/04-player-info.md](reference/client-240/clusters/04-player-info.md)
- [reference/client-240/clusters/05-npc-info.md](reference/client-240/clusters/05-npc-info.md)
- [reference/client-240/clusters/06-world-region.md](reference/client-240/clusters/06-world-region.md)
- [reference/client-240/clusters/07-animation-state.md](reference/client-240/clusters/07-animation-state.md)
- [reference/client-240/clusters/08-facing-state.md](reference/client-240/clusters/08-facing-state.md)
- [reference/client-240/clusters/09-spot-animation.md](reference/client-240/clusters/09-spot-animation.md)
- [reference/client-240/clusters/10-hitsplats-healthbars.md](reference/client-240/clusters/10-hitsplats-healthbars.md)
- [reference/client-240/clusters/11-overhead-overrides.md](reference/client-240/clusters/11-overhead-overrides.md)
- [reference/client-240/clusters/12-player-appearance.md](reference/client-240/clusters/12-player-appearance.md)
- [reference/client-240/clusters/13-coord.md](reference/client-240/clusters/13-coord.md)
- [reference/client-240/clusters/14-vector3.md](reference/client-240/clusters/14-vector3.md)
- [reference/client-240/clusters/15-world-position.md](reference/client-240/clusters/15-world-position.md)
- [reference/client-240/clusters/16-zone-updates.md](reference/client-240/clusters/16-zone-updates.md)
- [reference/client-240/clusters/17-sequence-advance.md](reference/client-240/clusters/17-sequence-advance.md)
- [reference/client-240/clusters/18-world-views.md](reference/client-240/clusters/18-world-views.md)
- [reference/client-240/clusters/EXCLUDED.md](reference/client-240/clusters/EXCLUDED.md)
- [reference/client-240/verification/baseline-20261025b.md](reference/client-240/verification/baseline-20261025b.md)
- [reference/client-240/verification/decompiler-quality-baseline-20261025.md](reference/client-240/verification/decompiler-quality-baseline-20261025.md)
- [reference/client-240/verification/enigma-hybrid-20261025.md](reference/client-240/verification/enigma-hybrid-20261025.md)
- [reference/client-240/verification/hook-events-20261025.md](reference/client-240/verification/hook-events-20261025.md)
- [reference/client-240/verification/mapping-repair-20261025.md](reference/client-240/verification/mapping-repair-20261025.md)
- [reference/client-240/verification/mapping-repair-baseline-20261025.md](reference/client-240/verification/mapping-repair-baseline-20261025.md)
- [reference/client-240/verification/portable-setup-20261024.md](reference/client-240/verification/portable-setup-20261024.md)
- [reference/client-240/verification/seed.md](reference/client-240/verification/seed.md)
- [reference/client-240/verification/tooling-setup-20261025.md](reference/client-240/verification/tooling-setup-20261025.md)

## Documentation and evidence rules

- [AGENTS.md](AGENTS.md)
- [reference/client-240/AGENTS.md](reference/client-240/AGENTS.md)
- [reference/rs240-port/AGENTS.md](reference/rs240-port/AGENTS.md)
