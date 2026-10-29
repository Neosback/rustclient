# Shared cycle infrastructure checkpoint

Status: partial delivery of roadmap phase 1. Based on `ef76ce75`; this is evidence
for this implementation slice, not another execution backlog. Full phase 1 and
the dependable-desktop gameplay milestone remain open.

## Delivered

- Corrected the clickbox array type, roof camera bindings and GPU-shell probe schema
  iteration. Retired a stale model-picking test that called a removed API and
  asserted obsolete projection behavior; ADR-0004 owns visual acceptance.
- Restored formatting, addressed strict Clippy diagnostics without weakening the
  warning gate, and added the four existing documentation/inventory checks to CI.
  Added a direct application Wasm gate. Diagnostic executables contain no test
  cases: they remain in all-target check/Clippy and explicit `cargo run --bin ...`,
  while their empty test/benchmark harnesses are disabled.
- Accepted/corrected SPEC-0020 under the approved implementation plan. Exact 240
  evidence establishes ten-cycle catch-up, strict thresholds, three script queues,
  pending-location versus actor-effect timers, and draw-path elapsed-cycle effects.
- Added application-owned CycleClock, bounded CycleIngress and ordered CycleEgress.
  Native packet application runs at a cycle boundary with the 100-packet budget;
  complete groups remain indivisible. Groups count the marker as a packet.
- Protocol buffers a group's complete wire payload before decoding/publishing it;
  malformed boundaries and nested groups fail explicitly. Empty/deferred packets
  still count against the ordinary budget. Existing decode callers retain their
  ordered flattened API, and cycle callers use explicit batch metadata.
- Native transport events use a bounded channel and coalesced wakeups. Application
  queue overflow fails contextually; cancellation drops the receiver and releases
  a blocked worker. Outbound intent batches wait for the cycle flush, preserve
  order on a full handoff, and validate before cipher advancement. Window status,
  ping replies and keepalive are session-wide and survive map-epoch changes. World
  actions retain their captured identity and are revalidated after S4 at flush,
  including NPC removal/reuse; walks and widgets use their existing validators again.
- NO_TIMEOUT uses the strict >50 cycle counter with transport write acknowledgement,
  replacing the fixed five-second timer. Plugins observe each executed cycle
  instead of only the final snapshot of a catch-up batch.
- Logout/reason/transfer/ping decode to typed controls. Native plain logout returns
  to title without a transport error. Reason and transfer metadata are no longer
  discarded or reported as malformed protocol. Ping replies echo both challenges,
  use measured FPS and zero JVM-GC time, and wait for the cycle flush.

## Evidence

- `classMG.method7611` and GameEngine clock dispatch: bounded catch-up/debt policy.
- `client.method2198/2346`: packet budget, group byte accounting and session controls.
- `classPQ.method9191` / `classQN`: queue order 5565, 5564, 5566.
- `classDS.method3605`: pending location lifetime; `client.method2181`: sound queue.
- `TileItem.method3420` / `classCC.method1407`: elapsed-cycle draw integration.
- Selected RSProt `1.0.0-ALPHA-20260825` `SendPingReplyDecoder`: transformed reply fields.
  Source hashes are in source-manifest.json and protocol fixture provenance.
- Contracts: `application/tests/client_cycle.rs`, `protocol/tests/packet_groups.rs`.
  The cycle replay fixture applies 150 decoded ticks over two boundaries and
  verifies the same recorded operations/commits reconstruct the same fingerprint.

## Remaining acceptance boundary

This does not implement the entire cycle specification. Native UI/asset mutations
still need the shared gateway, ClientScripts still drains its current queue during
packet commits, and the complete stage orchestration is not yet application-owned.
Local UI actions still originate outside the normative stages; creation-order
preservation is not proof of stock stage ordering. The browser is still a renderer
bootstrap, despite successful shared-code compilation.
The tested >750 watchdog counter does not yet replace the native 15-second receive
timeout. Reconnect authentication, transfer transport/policy, stock logout-reason
panels, IDLE/input telemetry and reflection policy remain open. Later actor/world,
interaction/dialogue and platform phases remain in PORTING-ROADMAP, not closed here.

## Validation

Passed:

- `cargo fmt --all --check`.
- `cargo check --workspace --all-targets --locked`.
- `cargo clippy --workspace --all-targets --locked -- -D warnings`.
- `cargo test --workspace --locked --quiet`: 588 passed, 0 failed, 6 ignored
  in the completed full run (`target/workspace-tests.log`, local artifact).
- After final native action revalidation and the additional mixed-framing fixture:
  `cargo test -p rustclient-protocol --test packet_groups --locked`: 4 passed;
  `cargo test -p rustclient-native --bin rustclient-native --locked --quiet`: 20 passed.
  A second full workspace repetition was interrupted; it is not counted as a pass.
- `cargo check -p rustclient-web -p rustclient-application --target wasm32-unknown-unknown --locked`.
- `python3 tools/port_matrix.py --check`, `tools/port_audit.py --check`,
  `tools/parity_inventory.py --check`, `tools/docs_index.py --check`, and `git diff --check`.

The inventory checks cover 57 domains, 269 packet identities and 170 symbol rows,
with no unpinned ledger sources. They prove consistency, not semantic parity.
The reference bridge status request returned connection refused; live reference
acceptance remains NOT-RUN.
No live server login, reconnect, browser gameplay, GPU/performance or visual
acceptance has been performed for this slice.
