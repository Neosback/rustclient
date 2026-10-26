# Required GPU and RuneLite core services

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
