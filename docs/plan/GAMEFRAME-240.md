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
