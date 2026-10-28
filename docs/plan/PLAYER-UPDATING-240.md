# Player updating audit — 2026-10-23

<!-- authority-notice -->
> Historical checkpoint and evidence record. Implementation status, “next” tasks, counts
> and validation below apply to the recorded snapshot. Current work follows
> [PORTING-ROADMAP.md](PORTING-ROADMAP.md) and the [domain matrix](../reference/rs240-port/PORT-MATRIX.md).

Historical checkpoint: later actor ownership, path consumption, forced movement and
sequence assignment were superseded by the merged
[actor runtime corpus](../reference/rs240-port/ACTOR-RUNTIME.md),
[sequence scheduler](../reference/rs240-port/SEQUENCE-SCHEDULER.md) and
[facing split](../reference/rs240-port/FACING-TURNING.md). Read those current owner
records before treating the remaining-work table below as a present implementation
inventory. This record retains its original evidence and validation limits.

Status: corrected movement foundation; complete actor parity remains open.
Baseline: `c7d3643a4cb118e38aace209ddd2b31a6f7444d6`.

## Source authority

The actual revision-240 client is `references/rl-suite-240/sources-semantic/`:

| Source | Verified responsibility |
| --- | --- |
| classDF.method3007 | Initial local 30-bit and outside 18-bit tracking |
| classDF.method3015/method3018 | Four independently aligned high/low passes and skips |
| classDF.method3043 / method3050 | Extension bits 7/13 and ordered extended fields |
| classDF.method3046 | Deferred ordinary movement commit |
| classDC.method2961 | Forced offsets anchored to the newly decoded network coordinate; forced end becomes the actor path target |
| classDC.method2924 / classGT | Immediate placement versus crawl/walk/run path entries |
| Actor.method3284 | Approach, forced interpolation, movement blocking and normal path scheduling |
| classCU.method2672 | Same-sequence restart modes and action priority arbitration |

Selected source hashes are in `docs/reference/source-manifest.json`. The exact
OpenRune dependency, RSProt `1.0.0-ALPHA-20260825` desktop-240 writer/encoders and
buffer extensions, resolves wire transforms. Entry hashes and synthetic vector
provenance are in `crates/protocol/tests/fixtures/PROVENANCE.md`.

The older `references/rustosrs/RuneLite-melxin` PlayerUpdateManager is useful
historical evidence, but its extended masks/transforms are incompatible here.
TSPS lifecycle/input structure and RustOSRS rendering do not override 240 wire
evidence. Reference/server/cache source trees were not edited.

## Corrections

- Replaced the older mask extension/layout with the selected 240 writer order.
  Appearance bytes reverse without an added-byte transform. Sequence/exact-move
  shorts use LE-add. Signed traversal transforms and reset sentinel are explicit
  in the profile. Unknown unsupported masks fail with context.
- Removed decoder-owned persistent path speed. GameState retains persistent and
  current traversal; outside-view records retain the default through promotion.
  Remote movement carries its override atomically, avoiding an intermediate
  locomotion restart. Crawl halves speed, run doubles it, immediate placement
  discards the interpolation queue.
- Long teleport offsets wrap on the profile-supplied grid. Short relative deltas
  remain signed checked arithmetic. The model does not encode 240 bit widths as
  universal world-coordinate rules. Their wire movement kind does not itself
  force immediate placement: traversal control and the projection's distance/plane
  checks decide that. Reset-to-position preserves the prior heading unless a
  separate facing update replaces it (classDC.method2924).
- Normalized exact movement in GameState after applying the new network tile.
  Invalid intervals, directions and arithmetic reject the entire batch. Scene
  motion derives its approach/interpolation and forced direction from committed
  tiles/cycles. A completed trajectory holds its end until new network movement;
  rendering never feeds a predicted coordinate back into GameState. Equal start/end
  times follow the deob's inclusive approach phase and hold the start position.
- Fingerprint schema 14 includes traversal and normalized trajectories, with
  updated exact anchors, reset, replay and invalid-batch contracts.

## Evidence

The baseline live login authenticated and then failed with
`player extended info: truncated payload` (`target/player-audit-baseline-live.log`).
The correction passed a fresh login with 397 complete packets and 27 NPCs
(`target/player-audit-fixed-live.log`). Cache metadata identity:
`f12db51150826c019f1ad156b7431a26f62023db736fffff3f989a97a368c3f5`.

A final disposable two-account run observed one remote appearance, one movement
and one removal, with 614 complete packets through tick 34 and NPC peak 30
(`target/player-audit-observer-live.log`). The walking account used authored
Logout tab then logout button actions and ended at tick 9
(`target/player-audit-walker-live.log`). The diagnostic now distinguishes those
two actions and gives the observer a bounded 20-second window. An earlier account
rejection and an earlier run missing removal are not counted as passes.

Focused contracts cover the literal mask/transform family, truncation, core
appearance reversal, persistent/temporary traversal, wrapped teleport, atomic
rejection, replay/reset, interpolation and unchanged-locomotion timing. These
are semantic/codec checks under ADR-0004, not pixel parity.

Final validation used the installed Rust 1.99.0 toolchain, matching the workspace's
1.99-specific Clippy configuration. All gates passed:

| Command (with `cargo +1.99.0`) | Result / local log |
| --- | --- |
| `fmt --all --check` | PASS, `target/player-audit-fmt-final.log` |
| `check --workspace --all-targets` | PASS, `target/player-audit-check.log` |
| `clippy --workspace --all-targets -- -D warnings` | PASS, `target/player-audit-clippy-final.log` |
| `test --workspace` | PASS, 246 passed / 0 failed / 0 ignored, `target/player-audit-workspace-final-tests.log` |
| `check -p rustclient-web --target wasm32-unknown-unknown` | PASS, `target/player-audit-wasm.log` |
| `build -p rustclient-native --bin rustclient-native --bin login_probe` | PASS, `target/player-audit-native-build.log` |

Final focused movement tests also pass in
`target/player-audit-final-movement-tests.log`. Pinned rl-suite-240 manifest hashes
and `git diff --check` passed. The native executable is rebuilt; desktop input,
full actor visual behavior and complete renderer parity have no new manual
acceptance claim in this checkpoint.

## Remaining actor compatibility work

| Area | Current limitation / next owner |
| --- | --- |
| Action assignment | ActorSequenceSlot only changes on a different ID. classCU requires restart mode 1 to restart playback/delay, mode 2 to reset loop count, and priority arbitration. Cache decodes restart but drops priority. Needs explicit asset-derived policies at the semantic gateway. |
| Action completion | Draw projection falls back to locomotion after finite loops; authoritative action slot does not yet implement the full scheduler's completion/loop state. |
| Forced movement during actions | Positional interpolation is implemented; Actor.method3284 gates some legacy-action interpolation on frame boundaries. This coupling is not implemented. |
| Turning/path scheduling | Instant facing remains; entity-target tracking, turn rate, turn/walk directional sequences, action movement blocking and catch-up scheduling are incomplete. |
| Pose composition | One selected action or locomotion pose; interleave/bone-mask blending is incomplete. |
| Appearance | NPC transform and custom appearance details are consumed or omitted rather than fully represented/rendered. Current fixture verifies the modeled core only. |
| Other extended state | Several known masks are bounds-checked/consumed but not retained: hits/headbars, spots, freeze/reset, tint and transparency. Correct framing does not establish their semantic behavior. Public/simple speech is now retained; see CHAT-ACTORS-240.md. |
| NPC paths | Superseded by NPC-ANIMATION-240.md: ordered run steps, crawl speed and forced movement now survive state/projection. Full scheduling/mask semantics and visual acceptance remain open. |
| Rendering/interfaces | This checkpoint does not accept complete z/priority rendering parity or world-map content. Prior input fixes and the server guide-price close patch retain their separately documented acceptance limits. |

Do not mark complete player/NPC updating or animation compatibility from a green
workspace suite or the two-account movement smoke test.
