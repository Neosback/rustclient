# SPEC-0019: OSRS 240 and RuneLite behavioral parity

**Status:** Accepted as the porting contract, 2026-10-28; implementation incomplete.  
**Owners:** all semantic, adapter, projection, platform and plugin subsystems.

## Purpose and scope

RustClient targets nearly 1:1 observable OSRS revision-240 client behavior and
the applicable features of the pinned RuneLite 240 client. OpenRune 240.2 is the
first server/cache profile. Desktop and browser share these semantics. Additional
validated profiles remain possible; this contract does not declare them supported.

Parity covers packet behavior, state, ordering, cycles, scripts, interfaces,
input/actions, assets, rendering, audio, social systems and plugin feature effects.
It includes reset, reconnect, removal/index reuse, malformed input and lifecycle
behavior. A working login, attractive scene or registered plugin is insufficient.

## Evidence and ownership invariants

- Use exact 240 stock control flow, separating RuneLite injection from stock rules.
  Resolve target wire details with the selected RSProt version and cache layouts
  with the selected cache. Follow [the source map](../reference/INDEX.md).
- Older revisions and other clients are supporting research. An unlisted revision
  difference is not proof of equality. Ambiguous decompilation needs bytecode or
  a second independent source before it becomes a compatibility requirement.
- GameState owns authoritative semantic game state. Cache assets are immutable;
  application orchestrates; scene/UI/renderer/plugin views project committed state.
- Keep RustClient's existing crate boundaries, typed IDs, explicit lifetimes and
  adapters. Java class/package shape and mutable injection mechanisms are not the
  compatibility target. No source-layout rewrite is required to claim parity.
- Reference trees are read-only evidence, never runtime dependencies.

## Rust improvements and deliberate differences

Prefer Rust ownership, safe APIs, bounded readers/queues, explicit errors, shared
native/web logic and measured allocation improvements when they preserve observable
contracts. Record source-to-owner mappings instead of mirroring Java architecture.

A change to a valid input's game result, packet bytes, cycle/order behavior,
interaction, visual/audio effect or plugin feature is a compatibility difference,
even if an implementation appears cleaner or faster. Such a change requires an
entry in [PARITY-DEVIATIONS.tsv](../plan/PARITY-DEVIATIONS.tsv), with source rule,
benefit, affected behavior, evidence, decision and regression/acceptance obligation.
Architectural changes also require the owning spec/ADR. Proposed differences stay
open; missing behavior and implementation difficulty are not accepted deviations.
Preserve source quirks unless an evidenced improvement or prior user decision
justifies departing from them. Do not enlarge a mask whitelist without decoding
its payload in source order and preserving the required semantic effects.

## Failure behavior

Malformed input fails with contextual errors without exposing secrets. Unsupported
valid behavior remains an explicit port gap. Consumption without retention,
Deferred dispatch and settings without effects cannot count as semantic support.
Rejecting an unsupported layout can protect stream alignment, but does not make
that layout compatible. Distinguish session termination, script failure and process
panic in reports; do not call all three a crash.

## Evidence and acceptance

Maintain one current [roadmap](../plan/PORTING-ROADMAP.md), one generated domain
[matrix](../reference/rs240-port/PORT-MATRIX.md), a method ledger and explicit
verification boundaries. Historical checkpoints describe their own snapshots.
Every claimed behavior needs source anchor, Rust owner, remaining branches and
named evidence; unknowns stay unknown. Source declaration counts and packet counts
are inventories, not feature weights or completion percentages.

For each implementation batch, freeze the affected source/profile/cache inputs,
then cover wire fields/order, semantic transitions, reset/reuse, deltas/replay/
fingerprints and downstream effects as applicable. Inventory source tests/cases
and fixtures before claiming lossless migration; record disposition and per-case
results rather than inferring equivalence from independently green suites.

Use the small semantic/codec suite and baseline checks in SPEC-0008 and root
AGENTS.md. [ADR-0004](../adr/ADR-0004-verification-policy.md) governs the manual
render acceptance pass; do not create a screenshot farm. Native/browser build
success, headless plugin execution and GPU probes have separate evidence levels
from gameplay, audio or visual acceptance. NOT-RUN, blocked and unverified cases
remain open. No full-client parity claim until the scoped behavioral inventory,
source-case dispositions and platform acceptance are complete, with every
remaining difference explicitly accepted and evidenced.

## Non-goals and open questions

Direct execution of Java RuneLite plugins, JVM/AWT emulation and copying the Java
software rasterizer are not required implementation mechanisms. Their applicable
user-visible effects still need Rust/native/browser equivalents. HD enhancements
are optional extensions after the classic baseline, not substitutes for parity.

The exhaustive behavior/test denominator and full RuneLite API/plugin disposition
are not complete today. They must be expanded from pinned sources; the current
domain matrix alone cannot certify exhaustive coverage.
