# Independent reference evidence

Production Cargo crates never link these tools or reference modules.
`oracle.py --cache PATH` verifies the pinned commit/source hashes, copies the
reference workspace and cache to a scratch directory, executes the original
classic GPU renderer, and records every input blob and output hash. Reference
cache sidecars are written only in the scratch directory. The added texture
oracle runs against upstream decoding and emits raw RGBA for exact comparison.

`camera_oracle.py DEOB_PATH` extracts and executes the literal pinned Java
camera input/pose blocks in an isolated harness. The committed trace and source
hashes live under `crates/renderer/tests/fixtures/camera`; tests need no Java or
reference checkout. The trace does not yet establish wheel-script or extreme
aspect-ratio letterbox parity.

`fingerprint_v5.py` independently calculates the documented canonical byte
encoding and FNV regression vectors. This is a diagnostic, not a security hash.

The parity specification is SPEC-0016. A successful capture is evidence of oracle
execution, not proof that RustClient matches it. Required differences remain
failures until resolved and measured.

## Live RuneLite 240 reference client

`runelite240.py` builds an isolated Open RuneLite shell (copy of `references/open-runelite-rev-240`, never
modified in place) around the pinned original `injected-client-1.12.39.jar` and launches it against a local server.
Requires JDK 11 and Gradle; output goes under `target/runelite-240-runtime/`.

```sh
python3 tools/parity/runelite240.py \
  --java-home /opt/homebrew/opt/openjdk@11/libexec/openjdk.jdk/Contents/Home \
  --public-key "<server>/.data/client.key" \
  --jav-config target/runelite-240-runtime/jav_local_240.ws
```

`--prepare-only` builds without launching. `Local240Agent.java` is a javaagent that replaces only the RSA modulus
constant in `bq.<clinit>`. `reference/ReferencePlugin.java` is a developer plugin that serves a read-only,
token-protected API on `127.0.0.1` (the token is generated per run into `endpoint.json`, mode 0600).
`reference_mcp.py` exposes it as the `rustclient-reference` MCP server (`reference_status`, `_snapshot`, `_widget`,
`_tile`, `_class`, `_trace_start`, `_trace`, `_trace_stop`); `install-reference-mcp.py` registers it with the Codex
CLI. `verify-reference.py` checks the protocol offline and `--live` exercises every tool against a running client.

Rules: the bridge exposes the public RuneLite API only and has no mutation operations. Log in with a disposable
account; passwords are typed into the client and never stored in this repository. Runtime evidence is a reference
only (see ADR-0005); it never changes the `original-exact` jar and never replaces the pinned inputs.

### Script opcode trace

`--trace-opcodes OUT.tsv` also attaches `Trace240Agent` (read-only). It brackets every interpreter handler
`(I<Script>Z…)I` and aggregates, per (handler, opcode, result, stack-size deltas), which handler really executed an
opcode. A result of 2 means "not handled here" and is dropped. After a session that opens the interfaces you care about:

```sh
python3 tools/client-240/opcode-trace.py OUT.tsv      # writes inputs/script-opcode-trace-240.tsv
python3 tools/client-240/opcode-table.py              # adds traced handler and real stack effect per opcode
```

The agent bytecode was checked offline against the pinned jar (48 classes instrumented, ASM verifier clean); a live
session is still needed to produce data. Unreliable deltas print as `?`.

### Packet trace

`--trace-packets OUT.tsv` attaches `Packet240Agent` (read-only, original obfuscated names, argument separator `|`).
Outbound: one call at the entry of `GameConnection.queuePacket` (`dj.ae`), recording the prot opcode, declared length,
encoded size, the first bytes and, in newer builds, the calling method. Inbound: one call just before each store of
`null` to `GameConnection.currentServerProt` in the packet loop (`client.ia` and its two duplicates), after the handler
ran, recording the prot, declared and actual payload length and the bytes the handler consumed (`consumed_mismatches`
counts packets where it read fewer or more than the payload). Stored multipliers are decoded in
`runelite240.py` (`PACKET_ARGUMENT`).

```sh
python3 tools/client-240/packet-trace.py OUT.tsv     # writes inputs/packet-trace-240.tsv
```

Checked in the live pinned client on 2026-10-25 (two sessions: login, interface tabs, several Walk here clicks):
53 opcodes (37 inbound, 16 outbound) agree with `server-packets.tsv` / `client-packets.tsv` on declared length, fixed
sizes and, inbound, consumed bytes (`short-read` would flag a handler that read fewer or more than the payload).
Outbound packets written without a `ClientProt` (the login handshake, from `client.ie`) are listed as `RAW_NO_PROT`.
Walk here sends `MOVE_GAMECLICK` (102) with only the destination tile (y, a one-byte modifier that was 0 in every
click, x): no client-side path. Eight outbound opcodes (walk, mouse move/click/scroll, no-timeout, applet focus, and
others) are all written inline by one method, `client.ih`, so a caller frame does not separate them; the other writers
are `client.aez` (mouse click), `bd.au` (window status), `client.il`, `client.ng` (map build), `ea.fo`, `tb.gp`.
This does not validate update-mask order; it shows the client consumed exactly the payload.

#### Replaying a session through the Rust decoders

`--packet-stream OUT.log` (with `--trace-packets`) appends every inbound payload in arrival order, whole, as
`sequence opcode length hex`. The log is live session data (other players' names): keep it under `.cache`, never commit it.
The opt-in test `crates/protocol/src/openrune_240/replay_tests.rs` feeds it through the player-info (91), NPC-info
(150/141), NPC-origin (111) and rebuild (39) decoders, which reject trailing bytes:

```sh
RUSTCLIENT_PACKET_REPLAY=OUT.log RUSTCLIENT_PACKET_LOCAL=<local player index> \
  cargo test -p rustclient-protocol replay -- --ignored --nocapture
```

First run, 2026-10-25, Lumbridge, about ten minutes of walking: 195 player-info, 195 NPC-info and 195 NPC-origin
packets, 0 failures. Coverage is thin for players (193 of 195 packets are 3 bytes: nothing changed; one 87-byte packet
is the local appearance) and moderate for NPCs (10 packets of 40+ bytes, 2 of 100+). It did not exercise public chat
(needs the cache Huffman table), emotes, combat or hits, the large NPC variant (141), or map rebuilds after login.
It shows the decoders consume exactly what the pinned client sent; it does not prove every mask's meaning.

### Automation driver and UI recorder

The bridge also offers an input driver and unattended recorders (`reference_input`, `reference_record_menu`,
`reference_record_status`, `reference_record_stop`, `reference_dump_widgets`). The driver only delivers AWT
mouse/key/focus events to the game canvas, the same events a player produces; there is no call that writes game state.
It stays on 127.0.0.1 behind the per-run token. `tools/parity/record_ui.py` wraps it:

```sh
python3 tools/parity/record_ui.py dump fixed-baseline          # every loaded widget: geometry, text, sprites, actions
python3 tools/parity/record_ui.py sweep fixed-sweep --step 8   # hover a grid, record the client's full menu at each point
python3 tools/parity/record_ui.py regions fixed-sweep --step 8 --out menu-regions.tsv
python3 tools/parity/record_ui.py tabs fixed-sweep             # click every tab found, dump its widgets
```

The window can stay minimised or behind other windows: input is delivered to the canvas directly, not through the OS
pointer. A fully headless JVM is not possible because the client creates an AWT canvas. Recordings go to
`target/runelite-240-runtime/recordings/` (not committed); curated summaries are copied into
`docs/reference/client-240/inputs/`. `tools/client-240/menu-actions.py` extracts the matching static table (menu action
id to outbound packet) from the decompiled dispatcher.

#### Replaying a session through the widget and script path

`apps/native/src/bin/reference_replay.rs` feeds the interface, variable, inventory, status and chat packets of the same
stream through the unmodified connection decoder, `ClientScripts` and `WidgetTree`, then prints the resulting layout.
`compare-layout.py` diffs that against a widget dump (`record_ui.py dump NAME`) taken from the same live session
(take the dump at the end of the capture; the stream keeps growing while the client runs):

```sh
cargo run -p rustclient-native --bin reference_replay -- CACHE_DIR STREAM.log MAX_PACKETS fixed LOCAL_INDEX > rust.tsv
python3 tools/parity/compare-layout.py rust.tsv target/runelite-240-runtime/recordings/NAME.json
```

`REPLAY_CYCLES=N` also advances N client cycles for timer listeners (the stream carries none). The comparer drops
reference widgets the client never laid out (`bounds` of `-1, -1`) and children of zero-area containers, and Rust nodes
whose clip is empty, because neither is drawn.

Result, 2026-10-25, fixed layout, one 9,733-packet session, dump taken at the end: 0 script diagnostics; 211 widgets
compared, 208 with identical bounds. Rust now also draws the run orb fill like the client: the fill widgets follow run
energy through the misc listener, which Rust did not notify before (found by this diff, fixed in `scripts/mod.rs`).
What still differs, and why:

- Side-tab slot (live interface 320, Rust 149): the live tab was changed by a click; the selection is client-local state
  that no inbound packet carries, so a replay cannot reproduce it. Not a Rust defect.
- Chatbox rows: the live snapshot reported chat tab 1 and shows only the first of two game messages (types 108 and 117);
  Rust shows both. The tab choice is client-local too, but it is not verified that Rust's default tab matches the
  client's default, so this stays open until a dump is taken right after login with no clicks.
- Interface 122 component 25 (one 85x19 widget at 399,34): live positions it, Rust leaves the cache default of 1x1.
  The script that does it is not identified; still open.

