# Renderer/gameframe continuation — 2026-10-25

<!-- authority-notice -->
> Historical checkpoint and evidence record. Implementation status, “next” tasks, counts
> and validation below apply to the recorded snapshot. Current work follows
> [PORTING-ROADMAP.md](PORTING-ROADMAP.md) and the [domain matrix](../reference/rs240-port/PORT-MATRIX.md).

Active user request after the chat checkpoint: use `/Users/tylercovalt/Documents/RustOSRS`
for roofs, wall decoration, depth/GPU and world-map evidence; port applicable behavior from
`runelite-gpu/` (classic RuneLite GPU and `runelite2008HDGPU/`). Use the readable
`/Users/tylercovalt/Documents/tsps-main/client` 237 client for non-rendering behavior,
while retaining exact target packet/cache adapters. External source trees remain read-only.

## Current checkpoint

- `54e6f9d` completes chat/history/menu-camera corrections on main and moves overhead quads
  into the world widget's paint layer. It also fixes literal rich-text tags in context menus.
  The user confirmed multi-message history and camera controls in the live review window.
- Brightness correction is implemented; live acceptance is pending: actual LIVE scripts 3961/3966 treat option 6 as
  numeric 0..100, not SoundEffectQuality. Exact 240 `classBY.method1359` establishes gamma
  `0.5 + (100 - percent)/200`. This supersedes the previous mapping; TSPS 237's custom
  0.9..0.6 range is not target-240 evidence. One typed preference lives in InterfaceState,
  schema 26, and is projected into the WGSL camera uniform for both materials.
- The supplied classic RuneLite shader applies HSL gamma inside its vertex HSL converter
  and texture gamma in the fragment shader. The WGSL port preserves that distinction and
  adopts RustOSRS's uniform derivative placement before material branches/alpha discard.

## Remaining authorized work

1. Verify brightness slider dragging and scene response in the live review client.
   Automated gates passed: fmt, workspace/all-target check, strict Clippy, 340 workspace tests
   across 75 test targets, Wasm and native build. Logs: target/brightness-*.log.
2. Verify chat-tab switching/current-tab hide behavior against TSPS 237 and actual cache
   scripts; retain authored script ownership. Recheck overhead occlusion after the new build.
3. Port RustOSRS connected roof grouping/overhang behavior into RustClient's scene/GPU
   projection. Separate stock top-level culling from RuneLite individual roof removal.
   Relevant source: osrs-scene/src/roofs.rs, osrs-render/src/gpu/roof.rs and scene.wgsl.
4. Compare wall-decoration offsets/rotation, face priorities, depth bias and depth/alpha
   ordering with RustOSRS and supplied RuneLite shaders; port concrete discrepancies.
5. Integrate applicable classic GPU and 2008HD shader behavior in WGSL with required host
   layouts/resources. Merely checking in GLSL files does not make those shaders active.
   HD materials/normals/shadows/postprocessing must not be claimed complete without support.
6. Implement functional world-map content/interaction. Current WidgetContent leaves map
   content as Other and the script host handles none of the named world-map instructions.
   RustOSRS has details/compositemap/compositetexture loading, geography, ground, labels,
   icons and templates in osrs-world/src/worldmap/. Its source documents build 241: verify
   target-240 layouts. TSPS 237 WorldMapController/Input and the readable 235 deob corroborate
   behavior. Preserve GameState authority for selection/centre/zoom; cache owns decoded assets.

## Parallel work already present (do not overwrite)

Claude worktrees outside main contain uncommitted actor-effects, walking/traversal and
worldmap/gameframe work. The worldmap worktree currently adds the 8000 typed-list family,
including 8020 enum keys, used by settings script 9751. Review/reuse compatible patches
without modifying those worktrees or assuming their current contents have been verified.

## Verification limits

The chat checkpoint passed 339 workspace tests, fmt, workspace/all-target check, strict
Clippy and Wasm. Brightness has separate target/brightness-*.log outputs and changes its
fingerprint vectors. Main's live review app is target/RustClient Chat Review.app; currently
it predates the latest overhead occlusion/menu-formatting/brightness changes and needs a
rebuild/relaunch for that visual acceptance. Fixed/classic visual checks are still pending.

## Executable Java reference

The supplied open-runelite-rev-240 checkout compiles on JDK 11. Its ClientLoader.loadClient
loads the injected gamepack from the runtime classpath, while --jav_config supplies applet
parameters and the game host. It defaults to official servers and requests an unavailable
local injected-client 1.12.40-SNAPSHOT dependency; it does not embed our server connection.
The repo-owned tools/parity/runelite240.py builds a source snapshot in target, substitutes
the checksum-pinned original 1.12.39 injected artifact and applies only OpenRune's public
RSA modulus at class load through Local240Agent. This is an instrumented reference, not an
untouched vanilla client or evidence of Rust parity. Runtime/login acceptance is pending.
