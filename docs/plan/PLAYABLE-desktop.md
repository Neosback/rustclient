# Playable desktop implementation checkpoint

Status: walk/bank **headless integration proof passes**; desktop gameframe/manual
acceptance is still being reviewed. Contract: [SPEC-0018](../specs/SPEC-0018-playable-desktop.md).
Full visual parity remains the separate ADR-0004 gate.

The [2026-10-23 gameframe audit](GAMEFRAME-240.md) records fixed/classic/modern
layout transitions and remaining server-script mismatch.

The [2026-10-23 player audit](PLAYER-UPDATING-240.md) records the corrected 240
decoder, traversal/forced movement, live evidence and remaining animation gaps.

## Implemented

- Server-confirmed movement, ordinary rebuilds, collision, cache-authored world
  actions, NPC updates and bounded outgoing requests from the existing checkpoint.
- IF3 definitions, mounted interface lifecycle, dynamic/nested children, runtime
  properties, permissions, variables, inventories and deterministic replay.
- Bounded revision-240 script decoding/execution, transitive calls, load/timer,
  variable/container/stat/chat notifications and mounted-interface SubChange.
- Shared widget layout, text/fonts, sprites, quantities, item model icons,
  scrolling/clipping and validated widget menus/actions. The normal native UI
  draws the cache gameframe; the handwritten chat/tabs/minimap HUD was removed.
- Incoming game-message history and filters, player display names, reboot status,
  and script-authored viewport settings. Camera projection/picking use the same
  cache-authored viewport and aspect/zoom settings. Current fingerprint schema is 15 (SPEC-0002).
- Login retains the form and Login button while connecting. Repeat submission is
  blocked; only the submitted password's mask length remains in presentation.
- Native title bar reports presented FPS and process RSS in MiB once per second.
  No GPU readback is involved. `RUSTCLIENT_FRAME_TIMINGS=1` adds wall-time summaries.
- Script geometry queries follow bounded ancestry instead of rebuilding the whole
  tree; layout indexes dynamic children once, and notifications borrow listener
  data. Development builds optimize the measured gameplay crates while retaining
  assertions and debug symbols.
- Eligible neighboring static scenery merges integer vertex normals before color
  baking and suppresses shared faces. Animated/flat-lit models keep their existing
  lighting path; normal adjacency and prepared geometry have allocation limits.

## Live evidence (2026-10-22)

The server's LIVE cache metadata fingerprint was
`f12db51150826c019f1ad156b7431a26f62023db736fffff3f989a97a368c3f5`.
Use its matching `.data/client.key`; reference trees and server code were preserved.

- `target/live-bank-journey.log`: Lumbridge to Draynor through ordinary map rebuilds,
  reaching a real cache-authored bank interaction. This is diagnostic movement
  evidence, not a claim that every desktop input has been manually accepted.
- `target/review-bank.log`: disposable `bankprobe22b`; validated authored actions
  and server updates proved Deposit-1 (backpack 9/bank 1), Withdraw-1 (10/0),
  Deposit-All (0/10), Withdraw-All (10/0), another Deposit-All, close and reopen
  (0/10). 773 framed packets, tick 39, explicit disconnect.
- `target/review-bank-relogin.log`: a separate connection reopened the bank and
  confirmed backpack 0/bank 10; 408 framed packets, tick 12, explicit disconnect.
- Fixture setup seeded exactly 10 coins once into the offline disposable account's
  empty backpack. The probe normalized the existing 9/1 state through a real
  withdrawal, then used only server updates for subsequent counts. No privileges
  or server behavior were changed to perform transactions.
- Native interaction confirmed the Login button remains while connecting and the
  actual chatbox background/channel controls/player-name line appear. Before the
  hot-path fixes the sampled tick took about 270 ms (~3 FPS). The optimized dev
  build was observed around 58–60 FPS with tick work around 2–3 ms in the spawn
  scene (`target/review-window-optimized.log`, `target/review-window-latest.log`).
  These are local observations, not release benchmarks or render-parity results.

## Remaining manual/compatibility work

- Finish reviewing sidebar/minimap/tab initialization and desktop widget input;
  do not replace missing cache behavior with another HUD. Some social-list,
  database and settings instructions still report contextual unsupported errors.
- Complete the full desktop walk/bank interaction acceptance and record its
  evidence separately from the headless transactions above.
- Validate wall appearance against the reference under the separate parity gate;
  merged normals alone do not establish complete scenery parity.
- Full model-widget presentation, item icon texture parity, rich text, public/
  private chat, social lists, advanced banking, instancing and combat remain later
  coverage. An automated green suite does not make these implemented.

## Launch and verify

```sh
scripts/run-openrune.sh "/Users/tylercovalt/Desktop/OpenRune Project/OpenRune-Server"
```

Use Existing User and a disposable local account. The normal development launcher
uses the optimized gameplay crates automatically. Add `RUSTCLIENT_FRAME_TIMINGS=1`
before the command for diagnostics. FPS counts presented frames, so an occluded
window can report zero. RSS is resident process memory, not a GPU-memory counter.

`login_probe CACHE PUBLIC_KEY --walk-and-bank` exercises the authored bank loop;
`--verify-bank` checks the 0/10 fixture state on a fresh connection. Both read
exactly two credential lines from stdin; never put a password in command arguments.
The transaction probe expects the named disposable fixture's total to remain 10.

Current continuation validation is recorded in the [player audit](PLAYER-UPDATING-240.md).
