# Robustness, diagnostics and session hygiene pass, 2026-11-06

Evidence record for the pass after the [2026-11-05 pass](CLIENT-PASS-2026-11-05.md). It is not an
execution plan: the [roadmap](../plan/PORTING-ROADMAP.md) owns order and what remains, and the
[domain matrix](rs240-port/PORT-MATRIX.md) owns status.

Every line about the live client is **NOT-RUN**. This pass ran in a container with no OSRS cache,
no GPU and no game server, so `interface_audit`, `scene_probe`, the native client and a real login
were not run. Nothing below is accepted beyond the unit, integration and source evidence named with it.

## Wave 1: robustness and diagnostics

| Change | Evidence |
| --- | --- |
| Server opcodes the client does not decode are counted per opcode (`SessionCodec::unhandled_opcodes`) and logged once when first seen | `packet_groups` test: counted, announced once, decoded opcodes excluded |
| Rejected NPC and player extended-info masks decoded with exact widths: NPC base animation set, head customisation, body customisation V2 and V3, old spot animations; player name extras and old spot animations. The V2 recolour/retexture lists are sized by the NPC definition through a cache-backed lookup (`NpcShapeLookup`, `npc_path_policy::customisation_shape`) | golden bytes with every-prefix truncation checks in `npc_extended` and `player_extended`; contents are not retained, so BAS overrides and customised models stay open |
| A failed scene load retries once, then returns to the title with `LoginFailure::SceneLoad` | `record_load_failure` test; the title text is unit-checked only |
| Login refusals carry the stock response code as `LoginFailure::Rejected(LoginRejection)` with the stock three-line message | handshake test for codes 6, 7, 56 and an unknown code; every code has text. The code table follows the stock login response table from memory, cross-checked against the strings in `classKK`; codes 11 and 12 are the least certain |
| Per-session scratch state (`SessionScratch`) is cleared on disconnect, fixing a stale plugin wheel-zoom flag, script error dedupe and widget request set across relogin | reset test |
| Plugin `config.json` is saved by staging, `sync_all` and rename; a corrupt file is kept as `config.json.corrupt`; failures are queued (`take_errors`) and logged | `directory_storage` tests |
| The window position, size and maximized state are restored when still reachable on a monitor | `client_preferences` tests; the winit wiring is compiled only |
| Every `eprintln!` also feeds a 200-line ring, and a panic hook writes `crash/crash-<time>.txt` (message, location, backtrace, version, recent log), keeping the newest ten | ring and report tests; the hook itself has not been triggered |
| The post-login network loop (`session_loop`) is separate from the socket and driven in memory | close, silence watchdog, bootstrap timeout, malformed group, forwarding, policy failure, outbound ordering and stale-map dropping |

Decisions:

- Brightness, volume and chat filters are server variables in the stock client, so they are not kept in the
  machine-local client preferences.
- Plugins are still stopped after `EndSession`: the `Stopped` event and final projection then show the
  ended session, which is what a logout looks like to RuneLite plugins. Moving it before would hide that state.

Not done, with reasons:

- An in-client error toast: it needs UI work and a place in the presentation layer.
- A loopback login server (RSA and ISAAC): the loop is tested through `session_loop`; the login handshake keeps
  its own protocol tests.
- The unhandled opcode counts are in the codec only; nothing surfaces them in a panel yet.

## Session lifecycle (Wave 2, partly)

- [ADR-0008](../adr/ADR-0008-login-reconnect-and-world-transfer.md) is **Proposed**: the `Reconnecting`
  state, the retry policy, credential rules and world transfer. No reconnect code exists; it needs a
  server to verify the reconnect login against.
- Closed: `NativeTcp` resolves host names (bounded to four addresses, malformed names refused before any
  lookup); the preferences folder follows the platform (an existing `~/.rustclient` is kept); a missing cache
  names the folder it expected. Evidence: unit tests for address candidates and the folder choice. The
  developer-machine cache paths in `options.rs` are still tried first, deliberately.

## Examine (Wave 3, partly)

- `ActionSlot::EXAMINE` and an Examine row after every NPC, object and ground item action; `OPNPC6` (101),
  `OPLOC6` (105) and `OPOBJ6` (9) are encoded; plugins see `MenuKind::Other(1002..=1004)`.
  Evidence: golden bytes for the three packets, application tests that rows exist and validate (including
  stale-target rejection through the existing revalidation). Deviation `EXAMINE-BASE-IDS` records that the
  definition ids sent are the base ones and unverified against a live server.

## Interaction target and player options (deob closeout, 2026-11-06)

- Facing blocks are retained per actor and projected as `ActorState::interacting` (SPEC-0022; schema 33).
  Open: the facing-target orientation resolver.
- `SET_PLAYER_OP` (85) is decoded into GameState (schema 34) and `OPPLAYER1..8` are encoded; the world menu
  lists every server-set option for other players standing on the hovered tile (deviation `PLAYER-TILE-PICK`).
  Evidence: golden bytes for the packet and all eight encoders, state replay test, application test through
  validation and stale-incarnation rejection. The relative-level colour of the name suffix is not implemented.
  Hide-ops (110/55/112), hint arrows and logout reason panels are still open.

## Not started in this pass

Player options (`OPPLAYER1..8`, `SET_PLAYER_OP`, a player pick target), `IF_BUTTOND`/`IF_SUBOP`, F-keys and
clipboard paste, item/spell targeting, combat visuals, instances, piles, model widgets, social, audio,
sandbox limits, fuzzing and CI. They remain as written in the roadmap; nothing above should be read as
having moved them.

Validation of the final commit: `cargo fmt --all --check`, `cargo clippy --workspace --all-targets -- -D
warnings`, `cargo test --workspace --no-fail-fast` (838 passed, 0 failed), both wasm checks and the five
documentation tools pass. `interface_audit`, `scene_probe`, GPU and live checks remain NOT-RUN.
