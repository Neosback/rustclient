# SPEC-0014: OpenRune desktop connection bootstrap

Status: implemented focused milestone; validation recorded in docs/plan/OPENRUNE-login.md. Owners: protocol (wire/profile/crypto), platform
(byte-stream contract), platform-native (TCP and entropy), application (connection
orchestration), game-state (authenticated session and initial placement), game-ui
(secret drafts and presentation), native (composition and worker lifecycle).

## Contract

The opt-in `openrune-240.2` server profile targets OpenRune commit
`abc80a8d76d68912f96819680f8aae279a586514` using RSProt
`1.0.0-ALPHA-20260825` desktop 240. Revision and profile identity are distinct.
The profile owns opcode lengths, transformed fields, RSA/XTEA/ISAAC, proof of
work, login responses, JS5 compatibility checks and initial rebuild decoding.
Reference sources are evidence only; they are never runtime dependencies.

Read reference-table CRCs through the cache owner, compare all 23 login archives
with the server's JS5 master table before submitting credentials, and reject
incompatibility explicitly. A cache directory name is not compatibility proof.
Only the supplied public exponent/modulus enters the client. Passwords and
session keys must never enter semantic operations, events, replay, fingerprints,
preferences, diagnostics or captures. Secret buffers are erased on release.

An injected asynchronous byte stream supplies exact reads/writes. Native TCP
runs on a dedicated worker with deadlines and cancellation. Shared orchestration
has no sockets, threads or filesystem paths. Browser bootstrap requires a future
compatible transport adapter; shared codecs remain wasm-compatible.

Successful authentication commits explicit profile/revision and player identity
to GameState. Initial placement and map area are committed through validated
operations; session end clears them. UI/worker projections are not authoritative.
New fields/events/deltas participate in fingerprint schema v3 and replay.
Cancellation invalidates stale worker results and closes the connection.

The login form supports connecting/cancel, bounded non-secret error categories,
and an authenticated bootstrap preview with Disconnect. Offline startup remains
available. Authentication does not imply world rendering or general packet
semantics: frame all known desktop packets, decode only initial placement/rebuild,
tick-end and disconnect, and count other packets without applying invented state.
Subsequent player movement, instanced maps, widgets and CS2 are outside this slice.

## Failure behavior

Reject truncated/oversized data, unknown opcodes, incompatible cache/revision,
unsupported authentication/challenges, invalid player coordinates and instance
rebuilds with stage-specific errors free of credential data. Proof work is bounded
and cancellable. No authenticated state is committed for failed login attempts.
Transport loss ends the active semantic session and returns to credential feedback.

## Evidence and acceptance

Record exact cached RSProt source-jar hashes and relevant source entry names in
the protocol fixture provenance. Test crypto against independent vectors, framing
across partial reads, bounded malformed inputs, cache mismatch, cancellation,
state lifecycle/replay/fingerprint, and credential handoff. Run the Rust validation
baseline including wasm. Against the user-started development server, create a
disposable account, verify authentication and initial placement, heartbeat, and
disconnect; report actual results precisely. Do not modify server keys/config or
reference trees. Do not start another server against the existing database.

## Shared runtime and native composition

`application::ConnectionDecoder` translates profile updates into the existing
SemanticOperation gateway. It owns wire phase/cipher state, never a copy of player
placement or lifecycle. Native composition supplies OS entropy, public-key reads,
cache reads and TCP workers. A generation token invalidates cancelled worker
results before they reach GameState. Transport DNS is deliberately deferred: the
first TCP adapter accepts IP literals and localhost with a two-second connect
bound, 100 ms cancellation polling, a 20-second login read/write deadline,
15-second initial-state/inactivity deadlines and five-second outbound heartbeat.
Synthetic authenticated GPU captures are explicitly labeled fixtures; live desktop
screenshots and the headless server probe provide separate authentication evidence.

The signed map-area sentinel -1 denotes the primary world in this profile and
decodes to `None`; nonnegative area identities use `WorldAreaId(u32)`. Negative
values other than the explicit sentinel fail. Wire widths do not constrain the
shared semantic ID wrappers.
