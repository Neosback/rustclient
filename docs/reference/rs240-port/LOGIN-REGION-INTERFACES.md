# Login, initial region and interface lifecycle

Status: source review and account-specific black-screen investigation, 2026-10-24.
The current GUI failure is a server-selected fullscreen world-map root without
its content, rather than an unfinished first-region load. This document extends
[GAMEFRAME-INTERFACES.md](GAMEFRAME-INTERFACES.md) and the
[checkpoint](../../plan/GAMEFRAME-240.md).

## Source order and Rust ownership

| Exact 240 source | Behavior | Rust owner / limit |
| --- | --- | --- |
| client IF_OPENTOP / IF_RESYNC_V2 branches | Select server root; reconcile mounted groups and permission ranges | protocol interface adapter; GameState interface owner. No account-name or first-login root guesses |
| classPQ.method9137 | Assign root, align to canvas, reset widget model playback, invoke load listeners | application ClientScripts + shared WidgetTree. Widget model playback reset/advancement is incomplete |
| classPQ.method9148 | Install mount, reset its model playback, align mounted children, invoke load and root callbacks | same authoritative interface owner + script scheduler; full callback/model branches remain open |
| classPQ.method9188 | Reset component model frame and frame-cycle counters | explicit remaining widget-animation obligation, not an actor-animation reset substitute |
| classPQ.method9181 | Invoke loaded group's authored on-load scripts | ClientScripts::synchronize; complete reopen/same-group lifecycle still needs auditing |
| client.vmethod237 / client.method2187 / classGP.method5585 | Dispatch loading/game frames; draw the selected root and its children | native composition + game-ui projection + renderer. A presented frame does not prove visible gameplay |
| classIV.method6364 / classKK.method6533 | Parse normal rebuild/base selection and coordinate initial/loading versus active map work | protocol map adapter; GameState scene selection; application load_scene; generation-tagged native worker. The injected async rl4 map-loader architecture is evidence, not Rust ownership |

The selected injected deob includes RuneLite map-loader changes; distinguish those
from stock packet/root/widget semantics. Do not copy its globals or numeric game
states into a second Rust state machine. GameState remains authoritative for the
session, initial placement, scene descriptor, root, mounts, vars and properties.
Workers prepare immutable assets; stale generations cannot install another region.

Authentication, gameframe selection and scene readiness are separate observations.
A new-account welcome, appearance or setup modal must arrive through the server's
interface/script operations. Account freshness is not permission for the renderer
to manufacture root 548, hide a modal, or bypass its listeners. The OpenRune
GameframeScript runs its layout selection on player initialization; LoginScript
sends vars, inventories, client scripts and other account state.

## Reproduced account difference

The working diagnostic GUI rendered root 161, its world viewport, chat and side
interfaces. The user's failing `test22` login instead received root 165 and mounted
normal overlays there. Its diagnostic log reports a loaded scene, drawable actors,
3247 layout nodes, 25 quads, no world viewport and successfully presented frames.
No widget/actor decode error explained the black screen.

The actual LIVE root-165 definition has no WorldViewport content. Most ordinary
HUD hosts are one-pixel placeholders; component 39 is an opaque black rectangle
covering the canvas beneath the display/modal subtree. The failing mount list has
no world-map content. Thus loading more terrain or suppressing login state cannot
supply the missing ordinary gameframe.

OpenRune's GameframeTable identifies this root as `interface.toplevel_display`,
the transient fullscreen world-map frame. GameframeScript.openLoginGameframe
accepts any saved frame whose resizable flag matches, while selectFallback searches
all rows with matching resizable/stone flags. Both paths include the fullscreen
row alongside normal layouts. This permits an empty fullscreen root on login;
we have not assumed which historical action produced this account's saved vars.

The approved server correction excludes `toplevel_display` from saved login
selection and from both fallback searches, preserving the ordinary fixed/classic/
modern choices. It uses the server's existing fallback and normal var update path,
without editing account storage or forcing a different root inside RustClient.
Patch: [OpenRune selector correction](../../patches/openrune-240-transient-fullscreen-gameframe.patch).
Build/restart and same-account verification are recorded in the checkpoint.

## Evidence and remaining work

- `target/login-black-screen-baseline.log`: working GUI and frame/resource counters.
- `target/login-black-screen-repeat.log`: failing account/root/mount/viewport trace.
- `target/login-root165-cache.log`: read-only LIVE cache layout/font diagnostic.
- `target/login-root165-server-build.log`: server module compilation/JAR; test task has no sources.
- `target/login-root165-patched-live-ready.log`: passing post-ready login, actor-policy and three-layout probe; 558 packets through tick 13.
- User confirmation: the affected `test22` account now displays world/gameframe.

This diagnosis does not complete world-map rendering, widget model animation,
all initial-load/reconnect transitions or every account-setup modal. The user retried `test22` after the patched server finished startup and confirmed
that the world and gameframe appear. The post-restart GUI trace selects normal
root 164 with a world viewport. Preserve
the logs and source mapping instead of treating 60 FPS or a loaded map as acceptance.
