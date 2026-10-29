# SPEC-0020: Client cycle and session runtime

**Status:** Accepted for implementation by the dependable-desktop plan, 2026-10-28.
Implementation and full phase acceptance remain open; delivery may land in contract-tested slices.
**Owners:** `application` (cycle driver and session runtime), `protocol` (packet framing and
codecs), `game-state` (committed transitions), `platform` (clock, transport, input event
contracts). `apps/*` only compose these.
**Evidence:** [client logic map §1](../reference/rs240-port/CLIENT-LOGIC-MAP.md#1-engine-loop-and-client-cycle),
[DEOB-PACKET-SITES.tsv](../reference/rs240-port/DEOB-PACKET-SITES.tsv). Contract:
[SPEC-0019](SPEC-0019-port-parity.md).

## Purpose

The stock client changes game state only inside a fixed 20 ms logic cycle, in a fixed order.
That cycle is `client.vmethod231`, with `client.method2172` for the logged-in state (235
`Client.doCycle`/`doCycleLoggedIn`). Packets, actor updates, script events, menus, idle
detection and outbound packets each have a defined place in it. RustClient currently drives
the cycle from the native event loop, applies packets whenever a window event delivers them
and sends outbound packets straight from the network worker. This spec makes the stock order
a shared, testable contract for native and browser.

## Scope

Logic cycle timing and stage order, inbound packet application, outbound packet creation and
flush, keepalive/idle/watchdog, game states, reconnect and session termination. Rendering cadence
is out of scope except where it reads cycle output. Input and menu semantics are in
[SPEC-0021](SPEC-0021-input-menus.md).

## Terminology

- **Cycle**: one 20 ms `ClientCycle`. Cycles are counted; wall time only decides how many are due.
- **Stage**: a named step of the logged-in cycle below.
- **Inbound queue**: framed packets whose bytes have arrived and passed ISAAC/framing, but are not
  yet applied to `GameState`.
- **Packet group**: the packets covered by a PACKET_GROUP_START byte count.

## Invariants

1. Only the application cycle driver commits gameplay operations. Network, input and render
   adapters enqueue; they never call `GameState::apply_operation` for gameplay.
2. A framed packet is invisible to `GameState`, scripts, plugins and projections until stage S4
   of some cycle applies it.
3. Packets are applied in arrival order. Outbound packets are sent in creation order.
4. Native and browser use the same driver. The only platform differences are clocks, transports
   and input event sources (SPEC-0003).
5. The cycle driver is deterministic for recorded inputs: the same inbound packets per cycle, the
   same input events per cycle and the same seeds produce the same commits and outbound bytes.

## Normative behavior

### Cycle timing

- Due cycles are computed from a monotonic platform clock at 20 ms per cycle.
- Use the nanosecond-clock rule in `classMG.method7611` (74-89): run at most ten
  due cycles per wake-up, then move an overdue deadline to the current monotonic time.
  `GameEngine.method11071` invokes the clock and runs its returned cycle count. Preserve
  strict deadline comparison and check duration arithmetic. RuneLite unlocked rendering
  (`classMG.method7610`) does not define a different semantic clock for RustClient.
- The `ClientTick` plugin event fires once per cycle. `GameTick` fires when SERVER_TICK_END
  (stock `client.method2383` serverTick) is applied.

### Game states

The runtime models the stock `client.field829` states: 0 initial, 5 loading archives, 10 title,
11 title variant, 20 logging in, 25 map loading, 30 logged in, 40 connection lost (reconnecting),
45 reconnect variant, 50 alternate login. A semantic enum carries these; numeric values stay in
the 240 profile. `updateGameState` side effects (classJB.method6386) are part of each
transition. Before acceptance, each transition records the source branch it implements.

### Logged-in cycle stages

The order is normative. Stage contents may be implemented by any owner, but observable effects
must occur in this order within one cycle.

| Stage | Behavior | Stock anchor (client.method2172 relative line) |
| --- | --- | --- |
| S1 | Reboot timer and logout timer countdown | 3-8 |
| S2 | Pending network error: run the error handler (S8 path), skip the rest of the cycle | 15-17, `classCN.method2546` |
| S3 | If no menu is open, reset menu entries to the Cancel entry | 24, `classEY.method4149` |
| S4 | Apply inbound packets: at most 100, but always the whole of a packet group (below) | 27, `client.method2198` |
| S5 | Reflection-check replies (policy: SPEC-0009), connection telemetry | 33-51 |
| S6 | Input telemetry packets: mouse move/click, scroll, keyboard, camera position, focus | 149-413 |
| S7 | World-map update, pending location-spawn timers, queued sound processing | `client.method2172` 3666-3715; `classDS.method3605` advances pending locations, `client.method2181` processes queued sounds |
| S8 | Connection watchdog: more than 750 cycles without inbound data enters state 40 | 486-487 |
| S9 | Per world view: players, then NPCs, then world entities run the per-cycle actor update (`classKN.method6539`, `classCZ.method2849`) | 490-560 (516, 521) |
| S10 | Player/NPC overhead-text countdown, then accumulate cycles since the last draw | `client.method2172` 3821-3893; projectile/graphics draw integration is described below |
| S11 | Key queue, root interface layout, script queues, widget drag, menu input, menu sort | `classFW.method5060/5073`, `classPQ.method9204/9191`; drain `classQN.field5565`, then `field5564`, then `field5566`, using `method9719`, `method9723`, `method9717` |
| S12 | Walk click (MOVE_GAMECLICK), hover invalidation, camera follow/scripted camera, world map icons | 829-913 |
| S13 | Idle: mouse and keyboard both idle for more than 15000 cycles sends IDLE (235 sets logoutTimer 250). More than 50 cycles since the last outbound packet sends NO_TIMEOUT | 925-985 |
| S14 | Flush outbound packets created this cycle | 990 |

Projectile advancement is reached from the draw path `TileItem.method3420` through
`classCC.method1407`; graphics-object integration follows in the same draw path. Both
consume `client.field950`, the elapsed-cycle accumulator; the logged-in draw resets it.
Do not invent a projectile step between actor and script updates from the 235 outline.
The later world-effects contract must preserve accumulated elapsed time and observable
results while keeping semantic ownership in GameState. Health-bar/hitsplat lifecycle is
also an actor-effects obligation, not an implementation of `classDS.method3605`.

### Inbound packets

- The transport adapter delivers bytes. Framing and decoding may run off the cycle thread, but
  their output goes only to the inbound queue.
- S4 applies queued packets in order until 100 have been applied and no group is open, or the
  queue is empty.
- PACKET_GROUP_START sets a byte count. Stock `client.method2346` refuses to apply any packet
  until all group bytes are available, then applies the group in the same cycle, beyond the
  100-packet limit. RustClient must keep a group atomic within one cycle. The selected
  RSProt PacketGroupStart model limits member wire bytes to 32767, matching the signed
  short read in 240; nested groups are unsupported and rejected explicitly. A group that never
  completes is a session failure after the S8 watchdog, not partial application.
- Decoding stays in `protocol`. The queue holds decoded profile values and their original order.
  Opcode numbers never leave the protocol crate.

### Outbound packets

- Intents produced by stages (input, scripts, menus, telemetry, keepalive) create packets in
  stage order. The transport sends them after S14 in creation order.
- Validation before cipher advancement remains as today (SPEC-0005).
- NO_TIMEOUT timing follows S13, replacing the fixed 5 s heartbeat.

### Session termination and reconnect

Each outcome is distinct and reported distinctly (SPEC-0019 failure behavior):

| Cause | Stock behavior | Required RustClient outcome |
| --- | --- | --- |
| LOGOUT | Return to title | Logged-out lifecycle, no error |
| LOGOUT_WITHREASON | Title with a reason message | Logged-out lifecycle with a typed reason; message text from the profile |
| LOGOUT_TRANSFER | Reconnect to the given world | World-transfer lifecycle (requires a world-list profile, matrix `world-list`) |
| Socket error or 750-cycle watchdog | State 40, reconnect attempt using a reconnect login | Reconnect state; on failure, title with the stock message |
| Malformed packet | Client error | Protocol failure with context (no secrets) |

## Failure behavior

- Inbound queue and packet groups are bounded by byte and packet counts. Exceeding a bound is a
  protocol failure with context.
- A stage error from a script is a script failure (SPEC-0018), not a session failure, unless the
  stock client would disconnect.
- Unsupported valid packets keep their current explicit behavior (PACKET-COVERAGE status) until
  ported. This spec does not make them acceptable.

## Performance

The driver must not allocate per packet beyond the decoded value. It must not block the cycle on
transport I/O. Per-stage timing counters reuse the existing performance stages.

## Acceptance criteria

1. Shared driver crate API used by `apps/native`. `apps/web` compiles against the same driver.
2. Contract tests with recorded byte streams:
   - 150 ordinary packets received in one read are applied over two cycles (100 + 50).
   - A 150-packet group is applied in one cycle and never split.
   - A group whose bytes arrive across two reads is applied only when complete.
   - SERVER_TICK_END emits GameTick in the cycle that applies it.
3. Outbound tests: telemetry, menu and walk packets created in one cycle are flushed in stage
   order. NO_TIMEOUT is absent at counter 50 and sent when the pre-flush counter exceeds 50;
   a successful nonempty flush resets the counter.
4. Idle test: no IDLE at 15000; both counters greater than 15000 produce IDLE and
   set the logout timer to 250. The stock mouse counter is then set to 14500.
5. Watchdog test: 750 silent cycles do not reconnect; exceeding 750 enters reconnect. Reconnect success
   and failure are both covered with a fixture server.
6. Replay: a recorded session replays to identical fingerprints, including the per-cycle packet
   split.
7. PORT-MATRIX `client-cycle` and `session-control` rows and PACKET-COVERAGE are updated. Every
   S-stage cites its 240 anchor in the symbol ledger.

## Non-goals

Emulating Java threads, AWT event timing or the software rasterizer. The draw rate and frame
pacing are not specified here. Reflection-check content is a SPEC-0009 policy decision.

## Delivery obligations still open

- Reconnect login/transfer must use the selected server's exact handshake and retention rules;
  a typed disconnect result alone is not a reconnect implementation.
- Input telemetry and reflection policy remain separately tracked obligations. Reflection must
  never expose arbitrary Rust host memory, filesystem or platform APIs. Do not report success
  for checks the client cannot perform.
- The complete stage order remains the acceptance target as its domain owners are delivered.
  A cycle queue/clock slice does not close menus, audio, actor effects or script scheduling.
- Source/live session-control cases must be identified explicitly; absent captures remain NOT-RUN.
