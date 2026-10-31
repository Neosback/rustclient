# SPEC-0023: Map loading state

Status: accepted for the retained-scene slice, 2026-10-31; instances, world views and
teardown stay open under the roadmap Phase 3. Owners: model, game-state, application
scene projection, game-ui and the native/web scene views.
Contract: [SPEC-0019](SPEC-0019-port-parity.md); client states:
[SPEC-0020](SPEC-0020-client-cycle.md); overlay rule: [SPEC-0018](SPEC-0018-playable-desktop.md).

## Problem

A map rebuild to a different window used to drop the committed scene before the replacement
existed, so the viewport had nothing to draw and went black until the whole new scene was built.
Stock 240 keeps drawing the previous scene while the replacement loads.

## Stock evidence

- REBUILD_NORMAL is `classIV.method6364` into `classKK.method6533`: when the client is already
  logged in (state 30) it keeps that state and starts the asynchronous region load; the previous
  scene stays drawn until `classFK.method4840` swaps in the finished one.
- The "Loading - please wait." box is `classLU.method7199` (235: `HttpMethod.drawLoadingMessage`):
  black fill and white border at (6,6) with 4 px padding, plain12 text at (10,10) inside, width
  from `lineWidth(text, 250)`, height `lineCount * 13`. It is gated by the loading-message flag.
  State 25 (235 `class167.method4165`) draws only that box over the frozen frame, never a cleared
  screen. The injected `isLoading` black viewport fill belongs to login, not to rebuilds.
- RuneLite's GPU plugin and TSPS (`MapManager.commitTargetGridAsActive`) likewise keep the last
  complete scene until the new one is renderable.

## Contract

1. `GameState` owns which scene is displayed. `loaded_scene()` is the installed scene and stays
   `None` from a window-changing rebuild until `InstallScene`. `displayed_scene()` is the
   installed scene, else the scene retired by the rebuild, else `None`.
2. A window-changing `RebuildMap` retires the installed scene (it does not drop it). A second
   rebuild before installation keeps the oldest retired scene. `InstallScene`, logout and any
   other loss of the scene (leaving its footprint, plane change outside it) clear the retired
   scene, so only a rebuild can leave a stale scene on screen. The retired scene is part of the
   semantic fingerprint.
3. Presentation draws the world from `displayed_scene()`. The camera keeps following the player
   against the retired asset. Actions that need the new window (walk, world actions, map
   acknowledgement) stay gated on the installed scene; the acknowledgement is sent once the
   replacement is installed.
4. Scenery animation and location patches do not run against a retired scene: its locations were
   cleared by the rebuild, and publishing that diff would visibly change a scene about to be
   replaced.
5. The loading box is drawn only when there is no displayed scene (login, first load) or when a
   replacement has been loading longer than 250 ms. Door and location reprojections never show it.
   Deviation from stock 240, which draws no box on a state-30 rebuild: the delayed box follows the
   235 state-25 behaviour and is recorded in `PARITY-DEVIATIONS.tsv`.
6. The retired scene is dropped as soon as the replacement is installed; no two scenes are ever
   uploaded to the same frame.

## Failure behaviour

A failed replacement load keeps the retired scene and logs the error; the next rebuild or
scenery generation retries. Disconnect and logout clear the retired scene with the rest of the
session.

## Acceptance

- Game-state tests: retire on a changed window, keep on the same window, second rebuild, install
  clears, plane change, fingerprint covers it, replay equality.
- Native: the redraw gate uses `displayed_scene()`; `scene_probe` and `RUSTCLIENT_FRAME_TIMINGS`
  report rebuild-to-published latency and frames drawn without a world.
- Live: walking across a region boundary shows the old scene, then the new one, with no black
  frame. Not claimed until observed.
