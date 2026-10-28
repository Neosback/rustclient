# OpenRune 240.2 authenticated bootstrap checkpoint

<!-- authority-notice -->
> Historical checkpoint and evidence record. Implementation status, “next” tasks, counts
> and validation below apply to the recorded snapshot. Current work follows
> [PORTING-ROADMAP.md](PORTING-ROADMAP.md) and the [domain matrix](../reference/rs240-port/PORT-MATRIX.md).

Validated 2026-10-21 on macOS against the user-started development server at
127.0.0.1:43594. Working branch `Neosback/openrune-240-login`, base commit
`2a164db5a09aa3d406916f0e67bedfc012209041`. Implementation remains in the working tree alongside the prior title
slice; this is not a committed release or a claim of complete revision support.
Contract: [SPEC-0014](../specs/SPEC-0014-openrune-login.md).

## Delivered

- Canonical ServerProfile registry/identity and explicit OpenRune 240.2 adapter.
- Cache-owned reference CRCs compared with the actual server JS5 master index
  before credential submission; deliberately absent archive 16 maps to CRC zero.
- Bounded ordered byte-stream contract and worker-bound native TCP, cancellation,
  deadlines, OS entropy, public RSA key parsing, raw RSA/XTEA/ISAAC login and the
  server's SHA-256 proof of work. No client dependency on a private key.
- Exact desktop framing, including smart encrypted opcodes and the RSProt success
  marker 37 / actual payload 34 distinction. Profile updates become typed semantic
  operations through application::ConnectionDecoder.
- Authoritative authenticated identity, initial placement, primary-world area
  absence, events/deltas, checked session lifecycle, replay and fingerprint v3.
- Single-use secret handoff from the title form, connecting/cancel, rejection,
  authenticated preview and disconnect. Passwords do not enter semantic state,
  events, replay, fingerprints, preferences or diagnostics.
- [Launcher](../../scripts/run-openrune.sh) using the server's cache/public key;
  stdin-only headless diagnostic. Offline title mode remains available.

## Reference evidence and cache identity

Server source commit: `abc80a8d76d68912f96819680f8aae279a586514`.
Wire source: Gradle-cached RSProt `1.0.0-ALPHA-20260825`; exact source-jar SHA-256,
entry names, independently generated fixture provenance and signed area evidence
are recorded in `crates/protocol/tests/fixtures/PROVENANCE.md`.
Reference trees and server source/configuration/keys were not modified.

Server LIVE reference-metadata SHA-256:
`f12db51150826c019f1ad156b7431a26f62023db736fffff3f989a97a368c3f5`.
Original Desktop LIVE reference-metadata SHA-256:
`c87f4c26b48a0bbf58b1c924a4f429bf03b0124fd6fc77837877601005f2bc79`.
These identify exact reference metadata, not a proof of client revision or a hash
of every asset payload. The original snapshot fails the actual server comparison
at archive 0, before any game-login credential packet is sent.

## Validation actually run

All passed on the final Rust sources:

- `cargo fmt --all --check`
- `cargo check --workspace --all-targets`
- `cargo clippy --workspace --all-targets -- -D warnings`
- `cargo test --workspace`: 101 passed, zero failed
- `cargo check -p rustclient-web --target wasm32-unknown-unknown`
- `cargo check -p rustclient-application --target wasm32-unknown-unknown`
- `bash -n scripts/run-openrune.sh` and `git diff --check`

Live headless probe: all 23 desktop CRC slots matched, proof solved, disposable
account authenticated, placement (3221,3218,plane 0), map anchor (3216,3216), main
world area None. Final eight-second run framed 395 packets and committed 14 server
ticks, sent a heartbeat, then disconnected and cleared authenticated state.
Earlier runs also passed; packet count/player slot vary with server activity.
Mismatched-cache run exited with a precise archive-0 rejection.

Actual desktop UI: welcome -> credentials -> live authentication -> starting
position preview -> Disconnect -> welcome. Also exercised wrong-password response
3 with cleared password, immediate Escape cancellation and a successful subsequent
fresh login retry. An independent verification window kept the user's existing client
window available. Isolated preference directories were used for UI tests.

Real GPU capture mode passed using the server LIVE cache. A seeded flame frame
(seed 240, step 150) was fixed. Fresh GPU resource creation produced byte-identical
welcome and synthetic authenticated-preview pixels and preserved the authoritative
fingerprint. The authenticated GPU case is explicitly synthetic, independent of
the real live UI authentication evidence.

Local reproducible artifacts (ignored target output):

- `target/openrune-validation/*.log`: all six Cargo gate logs.
- `target/openrune-smoke/live-login.txt`, `mismatched-cache.txt`: live probes.
- `target/openrune-smoke/{welcome,credentials,connected,rejected}.png`: actual UI.
- `target/openrune-gpu-captures/`: welcome, credentials, offline, HiDPI and
  connected-fixture PNGs plus GPU reconstruction evidence.

## Explicit remaining scope

The connected display is an authenticated bootstrap preview. It frames known
profile packets but deliberately defers general widgets/scripts, varps, inventory,
social messages and ongoing player/NPC updates rather than inventing semantic
state for them. Initial low-resolution remote slots are bounds-validated as part
of the fixed GPI prefix but not materialized as entities. Subsequent/instanced map
rebuilds fail explicitly. Reconnect authentication, 2FA/token auth, world transfer,
DNS resolution, TLS/browser relay and world rendering are not implemented.

The next vertical slice should load the spawn region's terrain/location assets,
establish scene semantics and render that region from committed initial placement;
then add player/avatar updates and movement. Broad CP4-CP10 work remains open.
