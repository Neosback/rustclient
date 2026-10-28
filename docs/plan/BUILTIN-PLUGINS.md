# Required GPU and RuneLite core services

<!-- authority-notice -->
> Historical checkpoint and evidence record. Implementation status, “next” tasks, counts
> and validation below apply to the recorded snapshot. Current work follows
> [PORTING-ROADMAP.md](PORTING-ROADMAP.md) and the [domain matrix](../reference/rs240-port/PORT-MATRIX.md).

Status: implementation in progress; not a parity/completion claim. User-approved
scope is the full applicable GPU and RuneLite configuration/core behavior, native
retained HTML/CSS UI plus browser panels. HD and other gameplay plugins are excluded.

## Source baseline and ownership

The vendored Open RuneLite tree is the source baseline; the original injected
1.12.39 jar and live 240 cache provide executable evidence. `tools/builtin-plugins/inventory.py`
records source hashes, settings, active resources and source tests in
`docs/reference/builtin-plugins/source-inventory.json`. Inventory presence is not
effect implementation. References remain unchanged.

- plugin-host owns required-service registration, lifecycle, typed descriptors,
  validation and accepted application configuration.
- shell-ui owns Dioxus presentation snapshots, input and retained native pixels.
- renderer owns GPU resources and the eventual panel composition/settings effects.
- application orchestrates typed services; platform adapters own persistence,
  notifications, window and clipboard operations. GameState retains gameplay truth.

## Current implementation

Required IDs `dev.rustclient.gpu` / `dev.rustclient.runelite` initialize before
authentication in the native composition root. Host disable/unregister requests
reject required services. Their faults keep their registration enabled. External
component manifests/ABI remain 0.1; external manifests cannot request required policy.

The host catalog describes 16 GPU and 34 RuneLite configuration options. It retains
source keys/defaults and validates candidate values before an owner transaction;
accepted state/generation/events publish only after transaction success. The owner
transaction must implement effect and persistence rollback. Declaring a descriptor
does not implement its renderer/window/notification effect.

The native shell embeds Dioxus 0.7.10/native-dom 0.7.10, Blitz DOM 0.2.4/paint 0.2.1,
AnyRender 0.6.2 and Vello CPU 0.8.0. It reuses physical RGBA pixels between changes;
hidden and unchanged panels return no work. This does not create another GPU device.
The explicit vendor compatibility patch is documented under vendor/README.md.

## Acceptance gates and remaining work

1. Native DOM control gate: search/text editing, checkbox, numeric entry and slider
   dragging/capture, enum choices, scrolling, keyboard, clipboard and IME. The actual
   DOM/CPU raster tests are in shell-ui/tests; compilation alone does not pass them.
2. Complete color/font/hotkey/size controls, tooltips, focus routing and browser mounting.
3. Native atomic persistence/rollback and application effect handlers. Unsupported
   effects remain explicitly unavailable; do not render effective-looking inert controls.
4. Composite retained panels into the client, with one shared game/shell viewport
   transform for resize, game picking and pointer coordinates in all game modes.
5. Port full GPU configuration/material/culling/loading behavior into existing owners.
   No duplicate scene, actor or authoritative preference state.
6. Port core window/notification/overlay/infobox/input services and platform support.
7. Use executable reference/MCP evidence, source test cases, full workspace gates,
   Wasm, native/live layout checks and separate visual acceptance reports.

Keep this checkpoint and the source ledger current. Do not claim the full delivery
from green host tests, a stored setting, source counts or a standalone panel demo.


## 2026-10-26 reconciliation

The current native implementation includes all descriptor control types, atomic application-level
configuration transactions/recovery, adjacent cached shell composition, and plugin API 0.2 for native
Rust plugins while preserving the external 0.1 component ABI. The GPU has additional settings effects
and explicitly reports pending/limited effects; consult current code and per-setting ledgers rather
than the earlier acceptance list as a claim that all those items are still absent.

The latest requested collapse policy hides both panel and rail, with zero reserved width, and uses a
single shell visibility value. The native retained-DOM contract covers close/reopen and 1x/2x geometry.
Game UI raster viewport was corrected to match its normalized game extent. The native loop separately
tracks shell-requested window widths to avoid feedback from intermediate platform sizes.

Broader completion and verification remain open: [the domain matrix](../reference/rs240-port/PORT-MATRIX.md)
and [current audit](../reference/rs240-port/CURRENT-AUDIT.md) are the current gap index. First-party
plugins added in intervening commits have their own [scope ledger](../reference/builtin-plugins/FIRST-PARTY-LEDGER.md);
these additions do not complete GPU/core behavior or browser gameplay.

Validation of this reconciliation is recorded in [the checkpoint](SIDEBAR-PORT-AUDIT-2026-10-26.md); automated checks
and desktop observations are separate.

## GPU audit continuation — 2026-10-26

The [GPU audit](../reference/builtin-plugins/GPU-AUDIT.md) and
[frozen per-file/setting ledger](../reference/builtin-plugins/gpu-audit.json) supersede stale GPU
status rows. All 16 settings and all active/historical/supplied resources are accounted for.
Six canvas scaling modes, shared UI/world correction, encoded composition, source filtering policy,
actual format sample negotiation, capability/resource guards and native presentation scheduling now
have implementations and focused/backend checks. Existing actor action-lifetime changes remain in
this shared checkout and are validated by the workspace gates below; this GPU work does not replace them.

The source xBR uninitialized f4 has an explicit deterministic correction. Active Java resources are
translated by applicable semantics; obsolete compute/OpenCL and separately scoped 2008HD are not
activated simply to claim every supplied file runs. There are 11 explicit open implementation/
acceptance/HD gates. This is not a completed GPU plugin or 117HD readiness claim.

Validation at this GPU checkpoint: formatting, workspace/all-target checks, strict workspace/all-target
Clippy, all native binaries and the Wasm check pass. Full workspace tests: **545 passed, 0 failed,
1 ignored** (live packet replay requires explicit fixture environment). The source/setting/resource
checker and 42-domain matrix checker pass; the completion checker intentionally returns incomplete
with 11 open gates. Focused native/backend evidence: six canvas modes, exact 1x/2x title pixels,
byte-identical world reconstruction and 20 core-feature browser checks. Isolated native title-window
pacing smoke passes for 30/locked-50/60 targets. These do not establish exact Java runtime parity,
authenticated fixed/classic/modern interaction, device-loss acceptance, browser gameplay or HD readiness.
The GPU tool scripts, shader translations and notices live in the repository; target artifacts are
regenerated locally. No reference source or supplied shader evidence was edited.


## GPU zone and related-map continuation — 2026-10-27

[The checkpoint](../reference/builtin-plugins/GPU-ZONES-2026-10-27.md) records implemented exact-240
inclusive eight-tile zone admission and normal-map related-group filtering. Picking, overhead speech,
hovered-roof queries and world plugin overlays share frame visibility with the renderer. Typed anchor
metadata preserves whole models and enables setting changes without cache reload, scene duplication
or GameState/collision mutation. The owned region resource keeps its notice and is byte-checked.
The source ledger and matrix distinguish these effects from remaining instance/minimap/frustum/
multi-zone-alpha/runtime acceptance. Real underground boundary captures could not run with absent
required archives; no empty terrain was fabricated. Full GPU/117HD completion remains open.

Validation of this continuation: formatting, workspace/all-target checks, strict Clippy, all native
binaries and Wasm pass; full workspace tests **547 passed, 0 failed, 1 ignored**. Actual native GPU
quad admission/title/world reconstruction and 23 browser checks pass. Source, matrix and symbol
checkers pass; the symbol checker now validates all manifest roots. Sparse-cache region-boundary
visuals, live/reference acceptance and complete GPU/HD readiness remain open.

## Sparse maps and viewport overlay — 2026-10-27

[The sparse-map repair](../reference/builtin-plugins/GPU-SPARSE-MAPS-2026-10-27.md) corrects the earlier
cache-blocker interpretation. Undeclared index-5 map groups are valid void squares in the injected
240 loader; source height/shadow initialization and blocked traversal are now retained. Listed missing/
corrupt archives still fail. The supplied Desktop/LIVE cache executes the real mixed-group Metal
probe: two absent squares, 53,583 changed channel bytes, byte-identical reconstruction. The diagnostic
camera's limited framing is recorded separately from Java/gameplay visual acceptance. The dense spawn
scene retains its existing asset identity and fingerprint.

[The viewport-overlay port](../reference/builtin-plugins/GPU-OVERLAY-2026-10-27.md) implements the
240 full-viewport solid-widget producer and shared canvas `alphaOverlay` consumer, preserving source
byte arithmetic, shaped viewport selection, reset, premultiplication and correction order. GameState,
input targets and shell chrome remain independent. A literal extracted Java arithmetic harness
confirms its four named vectors. This closes the overlay implementation obligation; ten broader GPU
implementation/acceptance/HD obligations remain open in the frozen ledger.

Final validation after both changes: formatting, workspace/all-target check, strict workspace/all-target
Clippy, full native binary build and Wasm check pass. Full workspace tests: **551 passed, 0 failed,
1 ignored** (live packet fixture environment required). GPU inventory, 146-row symbol ledger and
42-domain matrix checks pass. The completion gate intentionally returns 2 with ten open obligations.
Actual native canvas/title/region diagnostics pass; browser core WebGPU passes **31 checks**, with
maximum native/browser channel difference one. The reference-MCP endpoint is still unavailable
(connection refused); its offline protocol checks pass. No new live account travel, three-layout
visual acceptance, complete Java runtime parity, playable browser or 117HD readiness is claimed.

Tools, source pins, WGSL, notices, specs and ledger updates are repository-owned. Generated reports,
harnesses and captures stay under ignored target/. Reference trees, supplied shaders and cache were
not modified. Existing actor/GPU work in this shared checkout was preserved and included in validation.

## Sorted actor depth continuation — 2026-10-27

[The actor-depth checkpoint](../reference/builtin-plugins/GPU-ACTOR-DEPTH-2026-10-27.md) ports the
source opaque color-then-depth machinery for players and opcode-147 base NPC definitions. Typed
policy travels with the existing model span, independently of appearance morph, alpha, priority and
bias. Persistent color-only/depth-only pipelines retain depth tests and rebuild with MSAA/device
resources. The source uploader's front-face selection, model near-plane rejection and exact bucket
arithmetic still need independent acceptance; the existing sorter remains the baseline.

Focused cache/scene/renderer contracts and strict Clippy pass. Actual Metal synthetic priority/depth
and reconstruction checks pass; browser core WebGPU passes 35 checks. These are not Java actor pixels
or live acceptance. The server-testing launch uses the server's own cache and public key, keeping
the separate Desktop/LIVE cache for offline probes. No server or reference files were changed.

The initial workspace validation attempt encountered in-progress sidebar edits in this shared
checkout: formatting/Clippy errors and a transient missing ShellAction::TogglePanel variant. Those
changes are preserved. Do not promote the previous 551-test clean baseline into validation of this
continuation. Current full-gate status is recorded below after the build attempts finish.
