# SPEC-0025: Interface and client-script host

Status: accepted for the user-authorized TSPS shortlist implementation, 2026-10-29;
implementation and live acceptance tracked separately. Owners: model, cache,
GameState interfaces, application scripts, game-ui and protocol adapters.
Contract: [SPEC-0019](SPEC-0019-port-parity.md). This consolidates the interface
continuations without declaring the whole client-script host complete.

## Scope and invariants

Immutable cache definitions remain cache assets; live widget overrides, children,
variables and containers remain GameState-owned. Scripts use the existing host and
semantic-operation gateway. Native and web share the interpreter and query rules.
No mutable reference-client widgets, global caches or neutral-success opcode stubs.

The initial authorized slices are stock item search, exact IF1 decoding and
cache/script/interface audit tools. Additional interface handlers keep the same
source-first, bounded-error and lifecycle requirements.

## Item search

Use the stock portion of revision-240 `classOG.method8468`, `classKI.method6526`
and `classQA.method9458`. A rebuildable immutable name index may replace repeated
definition decoding, but not change names, membership policy, note/placeholder
exclusions, requested tradeability, empty-query behavior, the 250-result limit,
name ordering (including duplicate-name ties), or find/next/reset results.
The index is tied to the explicit cache/profile and membership context. Cursor
and results use the existing session script runtime and reset with it. Unsupported
or corrupt source data fails contextually; a failed index build is not cached.

## Deferred widget operations

`classJG.method6408` validates trigger operation numbers 1 through 10 and queues
address/item context. The invocation completes before `classBB` flushes those
operations through `classEA.method3815`; nested onOp scripts retain that ordering.
The queue resets with the session and has explicit count/depth limits. Script
triggers do not borrow menu labels or pointer hit tests as authorization. The
retained widget supplies onOp and the post-callback permissions. Actual drag
packets still need their own end-to-end implementation/acceptance: item capture
for trigger-op does not justify moving drag IDs ahead of onDragComplete.

## Legacy interfaces

Decode the selected 240 `Widget.method7215` format exactly; type-0 children link
through parent IDs, not an invented 317 child list. Retain authored comparisons,
CS1 expressions, alternate presentations, button and inventory metadata in the
existing definition owner. Parsing, CS1 evaluation, actions and painting are
separate support levels. A successful decode cannot be counted as complete UI.
Bound component dimensions, arrays and expression lengths before allocation.
Truncation, invalid selectors, malformed parent graphs and trailing bytes remain
contextual errors. Static metadata needs no GameState fingerprint change; any
new live field requires reset, transition, replay and fingerprint coverage.

## Audit tools

Read-only sweeps inventory declared cache files and report decoded, unsupported,
failed and unchecked cases separately. Instruction stack effects are compared
against revision-owned signatures, with context-dependent/unexecuted branches
explicit. Interface loading uses explicit bounded semantic state and reports
script failures; dummy inputs or passing stack checks never establish gameplay
parity. Reports include profile/cache identity and are deterministic apart from
explicit timing measurements. Tools do not modify caches or reference sources.

## Acceptance and failure behavior

Item-search fixtures cover filters, membership, duplicate names, empty input,
250/251 boundaries, cursor reset/exhaustion and reuse without repeated decoding.
IF1 fixtures cover supported type fields, alternate data, parent linkage, CS1
retention, limits, every truncation boundary and trailing bytes. Actual-cache
sweeps enumerate every declared entry in their selected scope, retaining failures.
Whole-script execution/visual/native/browser acceptance are reported separately.
Relevant Rust contracts, formatting, all-target checks, strict Clippy and shared
Wasm checks must pass; unavailable/live gates remain explicitly open.

Non-goals: revision-241 support, custom TSPS wire layouts, completing unrelated
social/audio/GE state by stubbing it, or replacing the client architecture.

## CS1 evaluation and legacy presentation (continuation 2026-10-29)

The revision adapter interprets `classEL.method3945` and `classGP.method5615` /
`method5609`. It issues typed semantic queries to an immutable live-state host.
Arithmetic wraps as Java int arithmetic, division truncates toward zero (including
MIN/-1), zero divisors leave the accumulator unchanged, and operators apply in
stream order without precedence. Missing expressions produce -2; evaluation
failures produce -1 and explicit diagnostics. Unrecognized CS1 words follow the
source's zero-valued operand behavior; this is not permission to stub CS2 handlers.

The 240 enabled-skill mask contains 24 enabled entries. Skill thresholds and the
real-level cap of 99 belong to the revision adapter, shared by CS1, CS2 and plugin
projection. CS1 reads raw run-energy units; CS2's visible-energy conversion remains
separate. World coordinates use committed world fine position plus the player's
footprint-centre offset, never renderer motion.

Conditional rectangles/text/sprites/model selectors and %1–%5 substitution are
disposable widget presentation. They do not write live widget overrides or clone
GameState. Empty alternate text retains the live primary text; sprite alternate
absence stays absent. Asset requests include authored alternates. Native uses the
same shared projection service available to Wasm. Hover-specific colors, tooltip
activation, legacy buttons, model painting and unresolved legacy inventory arrays
remain separate obligations. Do not infer a widget-to-container binding from its
numeric ID. Unavailable facts are diagnosed; their -1 compatibility result cannot
be reported as supported semantics. Evaluation and substitution are bounded.

## Legacy button dispatch (continuation 2026-10-29)

The existing widget action owner distinguishes ordinary operations, continuation,
and authored click/toggle/select buttons. `classDE.vmethod323` supplies IF1 button
labels; `classQP.method9746` dispatches them without an onOp callback or ordinary
operation-bit authorization. Visibility, authentication, base-component identity
and interface epoch are still validated. An ordinary operation cannot impersonate
an authored legacy button. Click content handlers are unsupported until their
semantics are implemented, and reject with a diagnostic before emitting a packet.

The 240 adapter encodes click/toggle/select as IF_BUTTON (combined component only),
independently of IF_BUTTONX. Toggle/select send that intent before interpreting
the first CS1 expression's direct varp reference. Toggle wraps `1 - current`;
select assigns comparison zero only when different. Local assignments use the
existing GameState client-variable transition, preserve the server shadow, and
notify variable listeners through the existing script queue. Malformed metadata
is diagnosed after retaining the button intent, matching the source dispatch
order. Option-type side effects remain explicitly unverified; no synthetic
preference/audio mutations are inferred. Target selection, close-dialog lifecycle
and the pending-continue latch remain open and are not counted as complete here.
