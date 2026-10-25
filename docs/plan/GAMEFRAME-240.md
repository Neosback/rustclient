# Gameframe/window audit — 2026-10-23

Status: callback/reflow repairs implemented; headless three-layout review passes
with the separately identified server script-2358 argument mismatch. Desktop
pixel parity and manual fullscreen/DPI acceptance remain open.

## Revision-240 behavior

| Evidence in `references/rl-suite-240/sources-semantic/` | Contract |
| --- | --- |
| GameEngine.method11047 | Content size is independent of drawable canvas; horizontal centering, top alignment, black unused margins |
| classUO.method11477 / classXO.method12926 | Script window modes 1=fixed, 2=resizable; canvas caps 765×503 and 7680×2160; WINDOW_STATUS reports mode and canvas |
| client.vmethod250 / classCN.method2541 | Canvas resize reflows the root hierarchy |
| NpcOverrides.method9135 / classFC.method4609 | Align attached components, including hidden components; clamp both scroll axes; recurse through ordinary/dynamic children and mounted roots using effective scroll extents |
| classPF.method8890 | Queue resize listener when calculated dimensions change |
| classEA.method3815 | Validate selection, retain the selected widget through its operation callback, then check permission and send the button |
| classBB opcode 1006 / Widget.getNoScrollThrough | Script-owned wheel barrier is separate from click-through |
| classLO.method7180 family (6200–6223) | Canvas width/height queries and reserved zero viewport-origin queries |

Selected source hashes are pinned in `docs/reference/source-manifest.json`.
OpenRune's `DisplaySettingsScript` selects fixed/classic/modern roots through the
server; the cache's dropdown listener closes and deletes its choice rows. Root
548 is fixed, 161 classic resizable, and 164 modern resizable. The latter two share
window mode 2. Fullscreen belongs to the host window and is independent of game
layout; the native F11 path already toggles borderless fullscreen independently.

## Implemented repairs

The baseline action handler revalidated the selected row after its callback.
The authored dropdown callback deletes that row, so revalidation silently dropped
the outgoing selection. The handler now validates stale topology/item/visibility
before execution, retains the selected action during its callback, and checks
effective operation permissions afterward. A second submission of the old action
is still rejected. The network's later stale-server-epoch guard remains in place.

Reflow now uses the same bounded layout traversal with hidden components included;
paint and hit-testing still exclude hidden ancestors. This prevents stale hidden
sizes and false resize events when a same-sized widget becomes visible. Clamped
scroll values commit through existing interface runtime operations before resize
callbacks. Child positions apply both scroll axes, and mounted roots use their
host's effective child extent rather than an inconsistent visible extent.

Added the no-scroll-through script instruction and authoritative runtime flag,
including wheel routing, reset and fingerprint schema 15. This removes the
`script 113 ... unsupported required instruction IfSetnoscrollthrough` failures
that interrupted gameframe setup. Added missing individual canvas queries 6222/6223
and reserved zero-origin queries 6220/6221. Fixed-mode canvas margins now clear
black, matching the engine; the shared input/draw centering transform is retained.

Removed the temporary F10 mode-only toggle: it changed canvas mode without the
server's authored root/mount transition. Layout selection uses Settings/Display;
F11 remains the independent host-fullscreen control.

No new semantic window-mode owner or hand-built gameframe was introduced. Cache
widgets, server root/mount transitions and GameState remain authoritative.

## Live evidence

Baseline `target/frame-audit-baseline-live.log`: zoom drag worked, but the Fixed
choice did not commit. Merely waiting for server/timer work still failed
(`target/frame-audit-wait-live.log`). Correcting callback ordering transmitted the
selection; supporting the missing wheel-barrier instruction removed the repeated
gameframe script failures.

`target/frame-audit-gameframe-live.log` passed with 503 complete packets through
tick 8 and the same LIVE cache metadata identity recorded in the player audit:

- Started on a simulated 1280×800 host.
- Opened Settings/Display and exercised the actual zoom drag listeners.
- Selected Fixed — Classic: waited for root 548 and mode Fixed, canvas 765×503.
- Selected Resizable — Classic: waited for root 161 and mode Resizable.
- Selected Resizable — Modern: waited for root 164, canvas and viewport 1280×800.
- Typed `hello` and verified backspace through the surviving chat key listener.
- Ended the authenticated session cleanly.

The diagnostic uses `login_probe CACHE PUBLIC_KEY --review-gameframe`, two
credential lines on stdin, bounded waits for the server's queued root transitions,
and 20 ms input polling for its client timers. It does not enter guide prices.

Three script-2358 argument errors remain in that successful transition log. The
server helper sends one `scale` argument, while this cache's script 2358 has zero
arguments/locals. Strict client validation is preserved. A separate
[server patch](../patches/openrune-240-interface-scaling-arguments.patch) passes
`git apply --check`; it is not applied or runtime-verified. Reference/server/cache
sources were kept read-only. Guide-price close retains its previously documented
server-side failure and separate patch.

## Validation and remaining acceptance

Focused contracts cover self-deleting dropdown callbacks and stale resubmission,
hidden reflow, scroll clamping, descendant/mounted layout, wheel-barrier state,
window-mode caps and canvas queries. They are semantic contracts under ADR-0004.
Final validation with Rust 1.99.0 passed:

- `cargo +1.99.0 fmt --all --check` (`target/frame-audit-final-fmt.log`).
- `cargo +1.99.0 check --workspace --all-targets` (`target/frame-audit-final-check.log`).
- `cargo +1.99.0 clippy --workspace --all-targets -- -D warnings` (`target/frame-audit-final-clippy.log`).
- `cargo +1.99.0 test --workspace`: 248 passed, 0 failed, 0 ignored (`target/frame-audit-final-tests.log`).
- `cargo +1.99.0 check -p rustclient-web --target wasm32-unknown-unknown` (`target/frame-audit-final-wasm.log`).
- Native client and login probe rebuilt (`target/frame-audit-final-build.log`).
- Pinned 240 source hashes and `git diff --check` passed.

The native executable is rebuilt; an already-running client needs restarting to
load the changes.

This is source and headless behavior evidence. A desktop review still needs to
check each layout visually, physical resize, DPI changes, fullscreen entry/exit,
menu/drag hit alignment and modal behavior. Complete world-map content, every
widget kind, default-mode persistence, layout scaling preferences and full rendering parity are not accepted
by this checkpoint.


## 2026-10-24 implementation continuation

Reviewed merge `809a941e` before extending this checkpoint. Its actor path,
sequence-policy, forced-movement and desired/current orientation work supersedes
the historical actor gaps in earlier plan documents. Current actor evidence lives
under `docs/reference/rs240-port/`; this UI continuation does not accept complete
actor scheduling or animation parity.

The [interface/font audit](../reference/rs240-port/GAMEFRAME-INTERFACES.md)
records exact source methods, owners and a per-layout acceptance matrix. Changes:

- Decode both simple and contour font metrics, signed pair kerning and glyph ink
  ascent/descent. Synthetic metric-family coverage includes malformed/truncated
  data; actual LIVE fonts were loaded separately.
- Preserve rich-text tags through wrapping; share formatted measurement with CS2;
  respect authored shadow/color, ink-based vertical placement and justification.
- Resolve item quantities to named plain-11 and stock raw/K/M colors. Use bold-12
  for context menus and hover text. Align menu bounds, hit bands, row spacing,
  colors and baselines with the selected Menu methods.
- Queue eligible IF3 wheel listeners before execution, with complete rotation and
  click/wheel barriers. Scroll position and thumb updates stay script authored.
  Horizontal/zero pixel events do not synthesize a vertical rotation.
- Repair merge build/lint issues and update the connection replay fixture's old
  schema-16 anchors to the already-authoritative schema 23. Replay equality and
  disconnect/reset checks remain. The obsolete private scene NPC-pose test was
  removed after movement/scheduler ownership moved to GameState; current actor
  contract coverage remains in that owner.

The existing [symbol ledger](../reference/rs240-port/SYMBOL-LEDGER.tsv) now has
separate verification/evidence/owner-path/remaining fields. Historical entries
retain unrecorded verification until concrete evidence is attached. Run
`python3 tools/port_audit.py --check` to detect missing declarations/owners,
duplicate rows, invalid vocabulary, missing strong-verification evidence and
changed selected source hashes. `--inventory target/rs240-methods.tsv --report
target/rs240-port-report.json` generates the full declaration index, including
unmapped declarations. Class mappings do not accept every method in a class;
declaration counts are not semantic completion percentages.

Fresh live evidence is `target/gameframe2-live.log`: 501 complete packets through
tick 8, authored zoom drag `[512,512]` to `[1448,1448]`, fixed root 548 at 765×503,
classic resizable root 161, modern root 164 at simulated 1280×800, chat typing and
backspace, then clean authenticated-state reset. Three script-2358 argument errors
remain. The separate server patch is still unapplied. This is a headless behavior
result, not physical window/fullscreen/DPI or scrollbar pixel acceptance.

`target/gameframe2-cache-interfaces.log` loads roots 548/161/164 plus inventory
149 and chat 162 without executing scripts. It also loads the named quantity font.
LIVE plain-11 is font 494 (ascent 10, ink extents 10/2); plain-12 and bold-12 are
495/496 (ascent 12, ink extents 12/4). This confirms decode/selection, not appearance.
The cache identity matches the earlier live checkpoint.

Validation with Rust 1.99.0:

- Formatting, workspace/all-target check and strict Clippy pass:
  `target/gameframe2-final-{fmt,check,clippy}.log`.
- Workspace tests: 315 passed, 0 failed, 0 ignored:
  `target/gameframe2-final-tests.log`.
- Web wasm target check passes: `target/gameframe2-final-wasm.log`.
- Native client, login probe and interface probe rebuilt:
  `target/gameframe2-final-build.log`.
- Ledger/source checks and generated declaration inventory pass:
  `target/gameframe2-ledger.log`. Negative checks reject duplicate rows, unknown
  statuses and empty evidence for contract-tested rows.
- All 41 selected source-manifest hashes match; references remain unchanged;
  `git diff --check` passes.

Remaining: desktop font/color/scrollbar appearance and physical resize/DPI/
fullscreen acceptance for each layout; inline images and soft-hyphen text;
submenus/formatted menu targets/tall-menu scrolling; quantity display modes;
IF1/additional widget kinds; world-map content; scaling/default-mode persistence.
An already-running client must restart to load the rebuilt executable.


## 2026-10-24 cache-profile restart correction

The subsequent GUI restart incorrectly selected strict `osrs-240`, despite using
the server's actual `.data/cache/LIVE`. Authentication succeeded, then packet-time
NPC policy loading rejected index 2/group 9: reported decoded size 1,792,969 versus
actual 1,806,666. This was a local profile/data-loading error, not a JS5 checksum
rejection. Restarting the server did not change it. Relaunching with the existing
explicit `openrune-240` profile resolves the stale size hint while preserving CRC,
stored-version and bounded decompression checks. No server or cache files changed.
Use the canonical `scripts/run-openrune.sh SERVER_DIRECTORY` launcher, which already
selects this profile.

The headless login probe previously committed entity packets without the native
worker's SequencePolicyResolver. It now resolves actor policy/timing/path metadata
before committing those operations and stops processing a decoded batch after its
first operation failure. `target/cache-profile-live.log` passes this enriched path:
27 NPCs, authored zoom/layout/chat interactions, 516 framed packets through tick 9,
then clean authenticated-state reset. The same three script-2358 argument errors
remain independently unresolved. This supersedes the earlier probe's limited
actor-cache-loading evidence; it is still not visual acceptance.

The GUI was rebuilt and relaunched with `openrune-240`; title loading is recorded
in `target/native-openrune-profile.log`. Cache-failure feedback now covers local
loading/verification without asserting server rejection or a pre-authentication
failure. Diagnostic errors include the selected cache profile. CLI help lists
both profiles and identifies the OpenRune repack choice.

Follow-up validation: Rust 1.99.0 formatting, workspace/all-target checking, strict
Clippy, all 315 workspace tests, the web wasm check and native/probe rebuild pass
(`target/cache-profile-{fmt,check,clippy,tests,wasm,build}.log`). `git diff --check`
also passes. No cache-integrity check was disabled by this correction.


## 2026-10-24 account-specific empty fullscreen root

A fresh diagnostic GUI rendered gameplay for the working account. The user then
reproduced a persistent black screen with `test22` in a second fresh GUI using the
same binary and OpenRune profile. Its frame trace selected root 165 with normal
HUD mounts, no world-map mount, no world viewport, 25 quads and successful frame
presentation. Scene installation and drawable actors had already completed.

Read-only LIVE cache inspection shows root 165 has one-pixel ordinary HUD hosts
and an opaque full-canvas black rectangle, with no WorldViewport content.
OpenRune identifies it as `interface.toplevel_display`, the transient fullscreen
world-map root. Its login selector accepted this row both as a saved layout and as
an ordinary matching-resizable/stone fallback. We did not infer or edit the
account's historical stored values.

The user approved applying, rebuilding and restarting OpenRune. The selector
correction rejects this root for saved login selection and filters it out of both
fallback searches. Normal fixed/classic/modern selection and normal preference
updates are retained. The existing full-screen-map runtime stays separate.

The patch is applied to OpenRune's GameframeScript.kt and mirrored in
`docs/patches/openrune-240-transient-fullscreen-gameframe.patch`.
`:content:interfaces:gameframe:compileKotlin` and its JAR build succeeded;
`:content:interfaces:gameframe:test` reported NO-SOURCE, not an executed unit
suite. Gradle reported BUILD SUCCESSFUL with 158 tasks (2 executed, 156 up-to-date)
in `target/login-root165-server-build.log`. The running server was stopped
gracefully and restarted with its preserved JVM/classpath configuration; startup
is logged in `target/openrune-login-selector-restart.log`.

The [login/region/interface source map](../reference/rs240-port/LOGIN-REGION-INTERFACES.md)
records exact methods, Rust owners, remaining widget-model/reset obligations and
why RustClient must not silently replace a server-selected root. The symbol ledger
and selected source hashes are extended accordingly. No Rust runtime code changed
in this investigation. The user retried the affected `test22` account after the patched server reached
its ready gate and confirmed that the world and gameframe appear. The post-restart
GUI trace also selects normal root 164 with a world viewport. This accepts this
reported account-specific login failure, not every interface or world-map feature.


Post-restart validation: the first probe reached the listening port before the
plugin ready gate and received response 8; it is retained separately. After the
server logged `Server ready`, `target/login-root165-patched-live-ready.log` passed
authentication, actor-policy loading (26 NPCs), authored zoom drag, all three
normal layout choices, chat typing/backspace and clean disconnect: 558 framed
packets through tick 13. The independently known three script-2358 argument
errors remain. The source ledger/checker and all 44 selected deob hashes pass;
reference trees remain unchanged. The applied server patch passes reverse-apply
checking, and whitespace checks pass in both repositories for the changed files.

## 2026-10-25 reused-interface load callbacks

The [current login/interface lifecycle record](../reference/rs240-port/LOGIN-REGION-INTERFACES.md)
now distinguishes explicit root/subinterface opens from unchanged resync entries. Rust previously
suppressed same-group load listeners because the cache group was already resident. `ClientScripts`
now schedules those explicit opens after GameState validation and retains authored component and
root/mount order. Repeated asset synchronization and mount moves do not manufacture open events.
The coherent lifecycle helpers live under `scripts/lifecycle.rs`; the existing semantic owner and
shared native/web path remain in place.

Ten focused host contracts pass. Final Rust 1.99.0 formatting, workspace/all-target check, strict
Clippy, **325 workspace tests**, web Wasm check and native binary build pass. Logs are
`target/interface-lifecycle-final-{fmt,check,clippy,tests,wasm,native-build}.log`.
The source ledger records these branches as contract-tested and the containing methods as partial.
No fresh live login, pixel review, widget-model animation acceptance or script-2358 correction is
claimed. An already-running native client needs a restart to load the rebuilt executable.

## 2026-10-25 live reference replay

The pinned 240 client was run live under the new read-only probes (see `tools/parity/README.md`) and one session's
inbound payloads were replayed through the Rust connection decoder, `ClientScripts` and `WidgetTree`, then diffed against a
widget dump of the same session in the fixed layout. 9,733 replayed packets produced 0 script diagnostics; 208 of 211
compared widgets have identical bounds. This is runtime evidence about one session (ADR-0005 tier TR), not acceptance of
every layout or mode.

Rust changes made from it, each with focused tests:

- Script array and enum opcodes 8002 and 8020-8029, and 6531 (client build, pushes 240 then 1), which the cache's own
  scripts use during gameframe setup and which previously aborted them. `ScriptEnum` now keeps cache entry order so enum
  key/value arrays match the client. `crates/client-script/tests/arrays.rs` covers growth, bounds, types and read-only views.
- Run energy and run weight updates now notify the misc widget listener (the run orb fill is driven by it). Previously the
  fill kept its first value.

Differences that remain: side-tab selection is a client-local click that no packet carries; the chatbox shows one of two
game messages live (chat tab 1) and both in Rust, and whether Rust's default chat tab matches the client's is unverified
until a dump is taken right after login without clicks; interface 122 component 25 gets a runtime position from a script
not yet identified. Pixel, resize, DPI and
other-layout (classic resizable, modern) acceptance is unchanged: those layouts have not been dumped from the live client yet.
Chat that needs the Huffman table, emotes and combat were not part of the captured session.

