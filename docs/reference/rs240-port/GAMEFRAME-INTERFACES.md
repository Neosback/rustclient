# Gameframe, interface and font audit

<!-- authority-notice -->
> Domain source/ownership research; implementation statements are bounded to the dates
> recorded below. The [current matrix](PORT-MATRIX.md) and [audit](CURRENT-AUDIT.md)
> supersede older gap/status lists; preserve exact source details and historical evidence.

Status: active implementation audit, 2026-10-24. This extends the existing
[gameframe checkpoint](../../plan/GAMEFRAME-240.md); it does not supersede that
checkpoint's exact live evidence or establish visual parity.

## Current source-to-owner map

The method-level entries are canonical in [SYMBOL-LEDGER.tsv](SYMBOL-LEDGER.tsv),
under gameframe, widget-layout, widget-input, widget-cache and font. Source files
are hash-pinned in the reference manifest. The ledger records exact source
symbols, Rust files, implementation status, separate verification, named evidence
and remaining branches. A method with only some branches ported remains partial.

The engine's canvas/centering behavior maps to profile window metrics and the
shared draw/input transform. Script window modes remain 1 fixed / 2 resizable;
classic and modern resizable use different server-selected root interfaces, not
a third/fourth platform window mode. Root/mount changes remain authoritative
GameState changes. Host fullscreen and DPI remain platform adapter inputs.

The server's root 165 is a separate transient fullscreen **world-map interface**,
not host fullscreen or an ordinary resizable layout. Its empty-login selection
and the approved server correction are documented in
[LOGIN-REGION-INTERFACES.md](LOGIN-REGION-INTERFACES.md).

AbstractFont.method26/method4 establish both simple and contour/kerning metrics.
Constructor ink extents differ from nominal ascent. method33 and method38 define
formatted width/wrapping; method63 defines widget line placement; method104 and
method110 define formatting lifetime and ordered glyph/shadow drawing. Java's
static style fields become local disposable text state in Rust. There is no
shared mutable global font style or second widget-property owner.

classPQ.method9164 enqueues IF3 scroll callbacks under the clipped pointer and
cancels callbacks behind click/wheel barriers. Its scrollbar behavior is not a
permission to mutate container scroll independently of the authored CS2. Rust
queues the selected visible listeners before executing them, preserving their
rotation placeholders and painter order. The host reports wheel input, and script
operations change GameState. Current IF3 scrollbar sprites, dimensions, drag,
arrow/track callbacks and scroll positions remain cache/script authored. IF1 and
its native scrollbar drawing are a separate unsupported branch.

## Corrections in this continuation

Widget wrapping formerly removed formatting tags, and painting ignored authored
shadows. It now keeps formatting across automatic wraps, resets style on explicit
breaks and handles documented color/shadow/underline/strike tags and escaped angle
brackets. Measurement and drawing use kerning, the selected cache font and shared
formatted text width. Top/center/bottom/distributed line placement uses glyph ink
extents, and justified lines distribute space advances.

Item quantities now request the cache plain-11 font explicitly and use stock
yellow/raw, white/K and green/M thresholds, rather than picking the first loaded
font and always drawing yellow. Full quantity display modes remain open.

Context menus and hover text now select bold-12, established by classDG's
classBB.field341 initialization through classXJ.field6874 (`b12_full`). Menu's
method12717/method12760/method12749/method12763 establish centered/clamped popup
placement, measured width plus eight, fifteen-pixel rows, header/body borders,
baselines and hit bands. The disposable menu now follows those values. Submenus,
formatted target labels, tall-menu scrolling and visual acceptance remain open.
Literal title/menu glyph painting also applies the same pair advances as width.

The cache font loader formerly rejected every non-257-byte metric file. It now
bounds-checks both layouts, preserves signed wrapping delta contours, derives
pair kerning and computes baseline ink extents from glyph masks.

Wheel routing formerly changed scroll position directly before a scrollbar
listener could run, and reduced all input to a single step. It now sends full
rotation to eligible listeners, respecting clipping/visibility/wheel barriers,
and leaves scroll/knob updates to their scripts. Zero/horizontal events are not
turned into a downward scroll.

The latest merge also contained a stray closing parenthesis in scenery validation
and a const-incompatible From conversion in actor path backlog; these small build
repairs preserve their existing behavior. Ongoing actor scheduler changes are
tracked in their own domain documents.

## Layout acceptance matrix

| Obligation | Fixed (548) | Resizable classic (161) | Resizable modern (164) |
| --- | --- | --- | --- |
| Actual Settings/Display root + mode transition | Prior headless live pass | Prior headless live pass | Prior headless live pass |
| Canvas cap, reflow and scroll clamping | Shared contract tests | Shared contract tests | Shared contract tests |
| Font IDs/colors/shadows/markup | Shared text path; visual review open | Shared text path; visual review open | Shared text path; visual review open |
| Scrollbar wheel/drag/track/arrow and listener state | Shared wheel contract; real scrollbar review open | Shared wheel contract; real scrollbar review open | Shared wheel contract; real scrollbar review open |
| Physical resize, DPI, fullscreen return and hit alignment | Desktop acceptance open | Desktop acceptance open | Desktop acceptance open |
| All interfaces, models, world-map content, scaling/persistence | Incomplete | Incomplete | Incomplete |

Review at minimum Settings/Display, chat, inventory, a long scrollable interface,
a modal, tooltips and nested mounted interfaces. Check actual font IDs and RGB,
line baselines/widths, text shadows, scroll thumb bounds, wheel/drag/arrow/track,
click barriers, canvas margins, menus and world picking in each layout. Record
logical canvas and physical host/DPI separately. A green root/mode transition is
not a pixel result. The previously documented server script-2358 mismatch and
unapplied server patch retain their own acceptance status.

Inline images/mod icons, soft-hyphen wrapping, RuneLite injected substitutions,
all legacy IF1 branches and complete widget kinds are still explicit gaps. Tests
for metrics, formatted-text semantics and listener routing do not accept their
appearance or every interface. See the checkpoint for exact validation commands.
