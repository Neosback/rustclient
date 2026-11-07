# Current implementation and acceptance audit

Reviewed for the TSPS/build cleanup on **2026-11-07**, starting from clean main
`d4ea8072`. This is a targeted adapter/build/documentation review, not whole-client
behavioral acceptance. The [domain matrix](PORT-MATRIX.md) owns detailed scope and
remaining branches; [the roadmap](../../plan/PORTING-ROADMAP.md) owns execution order.
The [earlier audit](../../archive/2026-foundation/reviews/CURRENT-AUDIT-2026-11-02.md)
retains its original measurements and limitations without directing new work.

## Current findings

| Area | Established scope | Acceptance still missing |
| --- | --- | --- |
| Existing OpenRune 240 | Retained profile and regression contracts; shared GameState/cycle, scene, widgets and plugin owners remain in place | Full classic behavior, complete interfaces/actors/providers, live session and visual acceptance |
| TSPS wire | Explicit experimental profile, separate framing/password login, shared intent/update facade, WebSocket composition, source-produced byte fixtures | Required NPC/skill/run-energy/ground-item/social/widget-mutation and scoped-script packets, full TSPS credential limits and several intents; no playable TSPS claim |
| TSPS cache | Independent code/cache pins; OpenRS2 cache 2746 title assets pass CRC/version/decoded-size checks; explicit metadata identity; separate command map | Decode inventory has unsupported/uninterpreted resources; script execution, map/XTEA and live interfaces remain unverified |
| Input/lifecycles | Existing shared arbitration and menu owners retained; added floating-menu/scaled-coordinate contract | All live input/IME/touch modes, asynchronous retirement, actors/index reuse and complete script/menu stage acceptance |
| Classic rendering | No TSPS placement/depth/pose heuristics imported; source diagonal snap is off | Matched stock visual acceptance for placement, depth/order, transparency, posing and picking; the renderer is not signed off |
| Plugins/graphics options | Normal builds retain external Wasm and first-party plugins; existing versioned/capability-mediated API and render-profile negotiation remain | Complete external ABI 0.2/browser execution and full RuneLite effects; HD implementations remain later work |
| Build/test workflow | Opt-in diagnostics; optimized play versus fast edit/check; optional external executor excluded from fast developer builds; integration families consolidated without removing their cases | Comparable clean/incremental timing evidence and all final validation results; no overall speedup percentage claimed |
| Platforms | Native/shared code compiles to the inspected scope; Linux plus macOS/Windows CI jobs defined | Cross-platform jobs not run locally; no playable browser, phone, device-loss or live DPI acceptance claim |

## Corrected contradictions and unsafe transfer traps

- The authority register's enabled-decoration-snap claim was stale: current Rust source disables it
  and documents stock 240 placement/rescaling. That correction is source evidence, not visual parity.
- TSPS force-run and invert-run flags are distinct. The adapter maps the shared inversion intent
  to the modifier bit instead of forcing running; the pinned client encoder supplies the fixture.
- The current TS appearance decoder documents simplified/guessed customisation skipping. Rust's
  adapter validates its represented subset and rejects unowned extension effects. Positive spot
  effects and entity-target fallback likewise remain explicit gaps until their semantic owners
  can preserve duration/current-angle behavior.
- Menu hit regions must remain in canvas coordinates, even outside the world viewport. Their
  regression contract exercises fractional/Retina scale conversion and foreground ownership.
- Inactive root Dioxus/Blitz sources are removed. [Recovery provenance](../../archive/retired-shell/README.md)
  preserves hashes, upstream notices and the exact Git baseline. Reference source trees remain
  evidence; ordinary Cargo/test does not scan or execute them.

## Pinned cache evidence

The [source/cache manifest](../../../crates/protocol/tests/fixtures/tsps-241/sources.json)
pins TSPS `ac7b52a21df137cbd6c011c73fd2f173cbc274d6` separately from the configured
`osrs-241_2026-10-20` cache (OpenRS2 2746). Reference metadata is
`eed13fcb9fd98865c24a7dfe50fbe41bae6f791df7dcbcd17bc6e04b8d967223`.
The supplied XTEA set is empty. The existing `xtask cache inspect-world` workflow loads CPU assets for the 104×104 window at (3168,3168,0): 763 object definitions, 951 models and 215 textures. This does not establish other regions, complete world geometry or visual acceptance. The pinned TSPS selector chooses this exact revision/date artifact; the inspected TSPS checkout itself has no installed cache.

The declared config/interface/script inventory reports **240,949 files: 200,724 decoded,
877 unsupported, one failed, 39,347 unchecked**. The single failed resource, script group 0,
is two bytes (`000a`), not a complete script footer. It remains reported rather than replaced
with a neutral program. Decode/stack inventories do not establish invocation outcomes, pixels,
complete source-test parity or gameplay. Raw local results are under ignored `target/`.

## Validation boundary

Validation is still being completed for this worktree. The initial five TSPS wire contracts,
real-cache title inspection, documentation links/navigation/domain inventories and optional
source-research checks passed in their recorded scopes. The consolidated workspace suite and enabled-diagnostic workspace/all-target/all-feature check passed before the latest script-precondition/varbit continuation. Strict Clippy caught documentation/formatting issues that were corrected; the current pass is still pending. Final focused semantic tests, fresh enabled-diagnostic checks, strict Clippy and Wasm checks must be reported with exact outcomes after the implementation freeze. Live TSPS, matched visuals, browser gameplay and
Windows/Linux hardware runs remain **NOT-RUN**.

Local build observations are not comparable A/B claims: the initial native build against cached
optimized dependencies took 171.92 s; switching profiles populated a different dependency graph.
Fast native mode removes the external compiler/runtime from its normal dependency tree (292 to
200 unique entries). Consolidation changes 53 integration executables into nine while preserving
module files; six libraries with no unit cases skip empty harnesses and retain doctests/target
checks. macOS startup sampling observed waiting in `_dyld_start`, outside Rust test execution.
The stock gameplay quality/draw distance and normal-play plugin capabilities were not reduced.

## File cohesion review

Touched authored Rust files above 500 lines remain below the 1,000-line ceiling:
`native/network.rs` owns one cancellable session worker/lifecycle (TSPS connection composition
is extracted); `native/plugins.rs` composes the existing plugin registry and capabilities;
`openrune_240/game.rs` owns the retained adapter dispatch (shared messages and profile dispatch
are extracted); `openrune_240/player_info.rs` owns the coupled four-pass tracking decoder.
Keeping each state machine together avoids splitting its invariants across unrelated helpers.
The new packet-length and script-opcode tables exceed 500 lines as cohesive profile data,
with behavior in separate modules. The previously 1,670-line script query file is split into a query dispatcher (under 1,000 lines), the variable-instruction owner and its unchanged unit cases. The scenery variable owner remains one cohesive state machine, including separate live/server mirror mutation. No touched file needs a ceiling exception.

## Script/variable continuation

The 29 source-produced fixtures now include the distinct 16-/32-bit script IDs, ordered
variable/inventory preconditions, subinterface opens, full operation masks and explicit rejection
of unrepresented widget-scoped scripts. Cache-resolved varbit ranges carry no live variable
values. GameState merges them into its live/server mirrors independently; the regression covers
client prediction, server-only changes, no-op generation, replay and session reset. Varbit schema
and mirror behavior are pinned separately from the source wire encoders. Focused protocol/GameState cases and the cancellable WebSocket upgrade test pass. Final broad validation remains pending until the commands finish.

The scene-base review also found a required effect the initial adapter discarded after bootstrap.
Every TSPS player frame now carries its authoritative window header into GameState as
`SetMapAnchor`. Unchanged windows after movement are strict no-ops; changed windows use the
existing retained-scene rebuild. Source idle/shift/repeat fixtures and a semantic replay case
pass. No map-anchor mirror was added to the adapter. The operation dispatcher and session/scene
owner stay below 1,000 lines; their 500-line review keeps existing transition/error cases and
scene lifecycle together.
