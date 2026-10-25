# Chat and actor presentation checkpoint — 2026-10-25

Status: chat/movement corrections implemented; full visual parity remains open.

## Changes and evidence

- Main includes rendering commits `f9e293b0` and `a65debda`: footprint height,
  floor-decoration lift, animation height offsets, render levels/roofs and minimap plane refresh.
- The selected RSProt 240 desktop `PlayerChatEncoder`, `PlayerSayEncoder` and
  buffer `pdataAlt3` establish public/simple speech transforms. The buffer reverses
  compressed bytes and adds 128; optional pattern entries use negated bytes.
  Decoder construction receives the actual cache Huffman table before authenticated updates.
- `classDC.method2954` and `method2940` establish public/say storage/history policy.
  GameState owns actor speech and 150-player/100-NPC cycle expiry; bounded history
  remains independent. There is no outbound optimistic echo. Schema 25 includes speech.
- Player-carried history updates notify cache chat-transmit listeners after commitment.
  The live follow-up exposed reversed UID traversal. `classVX.method12332` / `classIW.method6366`,
  corroborated by the readable deob's `Messages_getLastChatID` / `class194.method4452`,
  establish NEXT=newer and PREV=older. ChatState owns these queries; scripts now walk every row.
- NPC spawn cursors initialize before endpoint changes, including receive bursts before
  the first client cycle. Ordered targets remain authoritative; no collision clamp or server
  path rewrite was introduced. Model/camera snapshots follow the committed client cycle.
- Camera arrow presses/releases precede world/widget menu dispatch. Mouse/Enter/Escape
  retain menu actions and focus loss clears held input.
- Overhead uses posed bounds, the world camera, cache bold font, clipping/overlap and style
  metadata. Quads occupy the world widget's painter boundary, beneath mounted interfaces.
  Menu labels use the widget rich-text parser/measurement instead of drawing literal tags.

Reference trees and the other Claude worktrees were not modified. Only the relevant bounded
Huffman decoder from the uncommitted actor-effects work was reused, then changed to a validated
prefix trie. Hitsplats, health bars and spot animations were not imported with it.

## Validation

The checkpoint passed `cargo +1.99.0 fmt --check`, workspace/all-target check,
strict Clippy (`--workspace --all-targets -- -D warnings`), 339 workspace tests,
and `cargo +1.99.0 check -p rustclient-web --target wasm32-unknown-unknown`.
Tests cover codec transforms/CP1252/truncation, speech/history/expiry/reset/replay/index reuse,
cache chat callbacks, history neighbor traversal, NPC corner-path packet bursts and menu key release.
Logs from the final checkpoint run are in `target/chat-*.log` (untracked build artifacts).

Live test22 in RustClient Chat Review: user confirmed overhead plus chatbox messages, then
confirmed multiple history rows and arrow-key camera movement with the context menu open.
The modern layout/world and retained multi-line history were also inspected through the native UI.
Fixed/classic chat acceptance, interface occlusion after the painter fix, detailed feet contact,
NPC wall trajectories and full reference/actual/difference visual parity are not yet accepted.
No full parity run or pixel-perfect claim is made.

## Continuation

The user subsequently requested RustOSRS roof/wall/GPU/world-map evidence, supplied RuneLite GPU
and 2008HD shaders, the readable TSPS 237 client for non-rendering behavior, brightness control
and chat-tab semantics. Those are follow-on implementation work. The initial live cache review
already proves option 6 is numeric screen brightness (scripts 3961/3966), contradicting its
previous boolean sound-quality mapping; that correction requires its own semantic checkpoint.
Full facing/turn-speed coupling, social-list filters and unrelated unsupported scripts remain open.
