# SPEC-0002: Authoritative State, Events, Commands, Replay, and Fingerprints

**Status:** Draft

## Purpose

Make runtime behavior deterministic enough to debug, replay, test plugins, and support both native/browser front ends.

## State model

`GameState` owns semantic live state such as players/NPCs, scene occupancy, inventories, varps/varbits, skills, widgets, camera intent where semantic, chat/message state, timers, and connection/session metadata that affects behavior.

State is changed through explicit operations. Systems must not retain mutable aliases into state across a transition boundary.

### Root ownership constraints

The authoritative root is deliberately difficult to duplicate or bypass:

- `GameState` does not implement `Clone`.
- Domain fields are private to `game-state`.
- External crates receive immutable queries/snapshots rather than mutable field references.
- External semantic mutation enters through typed `SemanticOperation` values and `GameState::apply_operation` / `apply_operation_batch`.
- Session lifecycle, revision identity, and game-time validation remain crate-owned transition primitives behind that public operation gateway.
- Starting a session requires an explicit `Revision` and initializes session-owned state.
- Ending a session clears the active revision and all other session-owned state before another session may begin.
- An invalid operation leaves state unchanged.

These constraints remain invariants as richer domains are added.

### Session revision identity

The active semantic session owns an explicit `rustclient_model::Revision`.

- An inactive `GameState` has no session revision.
- `SemanticOperation::BeginSession { revision }` establishes the active revision.
- `GameState::session_revision()` exposes the revision only while that session is active.
- `SemanticOperation::EndSession` clears the revision.
- No process-wide or implicit fallback revision exists inside `game-state`.
- `Revision` is a compatibility identity for protocol/cache semantics; it is not server identity and is not a `ServerProfile`.
- Supported-profile validation remains an application/protocol responsibility. `game-state` records the already-selected semantic revision without owning a revision registry.
- When a canonical server-profile identity type is implemented by its owning subsystem, replay/session metadata may incorporate it explicitly rather than inventing a duplicate profile identifier here.

### Semantic game time

The current semantic game tick is session-owned authoritative state.

- An inactive state has no observable current tick.
- Beginning a session starts at `GameTick::ZERO`.
- Ending a session clears the observable session tick.
- Tick advancement requires an active session.
- Tick advancement uses an explicit `TickSpan`; domain transitions do not read wall time.
- Tick arithmetic is checked. Overflow rejects the transition without mutating state.
- A zero-span tick advance is valid but causes no observable state change.
- A future runtime scheduler decides *when* to request tick advancement; `GameState` only validates and commits the semantic transition.

### Semantic operation application boundary

`SemanticOperation` is the source-neutral request type accepted by the authoritative state mutation gateway.

Current operations are deliberately minimal:

- begin session for an explicit `Revision`,
- end session,
- advance semantic game tick by `TickSpan`.

The operation boundary follows these rules:

- operations contain semantic intent only; packet opcodes, byte encodings, transport state, UI toolkit types, renderer types and platform handles are forbidden,
- `GameState::apply_operation` applies exactly one operation at a time,
- dispatch delegates to existing validated transition primitives rather than reimplementing lifecycle/time rules,
- validation happens before committed mutation,
- failure preserves authoritative observable state for the failing operation,
- operation errors preserve the typed subsystem error category,
- successful operations return commit metadata only after authoritative mutation has completed.

### Commit metadata

`OperationCommit` is the result metadata for one successfully applied semantic operation. It contains:

- an optional `SemanticEvent`, and
- a `StateDelta`.

`SemanticEvent` describes a committed fact, never a request. The currently modeled event facts are:

- `SessionBegan { revision }`,
- `SessionEnded`,
- `GameTickAdvanced { previous, current }`.

The session-began event repeats the exact committed `Revision`. This lets replay and observers verify the compatibility identity that actually became authoritative rather than relying only on an input request.

Events are emitted only when observable semantic state actually changed. For example, `AdvanceTick(TickSpan::ZERO)` succeeds but returns no semantic event because the authoritative tick ordinal is unchanged.

`StateDelta` is projection-oriented metadata describing which modeled state domains changed. It is not authoritative state and must be discardable. CP3E/CP3G track only:

- session lifecycle changed,
- observable semantic game tick changed.

Session revision is lifecycle-owned and changes only at session boundaries, so `lifecycle_changed()` instructs consumers to re-query both lifecycle and `GameState::session_revision()`. A separate revision-delta bit is unnecessary while that invariant holds.

A successful operation that changes no observable state returns an empty delta. Consumers query the authoritative `GameState` for current values rather than treating the delta as a second state store.

### Ordered operation batches

`GameState::apply_operation_batch` applies a borrowed slice of `SemanticOperation` values in exact slice order.

Batch behavior is deliberately simple and deterministic:

1. operations are attempted from index `0` upward,
2. each successful operation commits before the next operation begins,
3. processing stops immediately at the first failed operation,
4. the failed operation itself leaves authoritative state unchanged,
5. operations after the failure are not attempted,
6. successful prefix mutations remain committed,
7. an empty batch is a valid no-op,
8. the returned batch error identifies the first failing zero-based index and preserves its typed `OperationError`.

Operation batches are therefore **not transactional** and are not rolled back on later failure. This avoids full-state cloning or rollback authority.

`GameState::apply_operation_batch_with_commits` preserves the same mutation/failure semantics but additionally invokes a caller-provided callback immediately after each successful commit. The callback receives the operation's zero-based batch index and its `OperationCommit` in exact input order. No callback occurs for the failed operation or skipped suffix. This exposes deterministic commit order without requiring `game-state` to allocate a `Vec` per batch.

## Input batches

A logical frame/tick may eventually combine operations translated from multiple sources such as:

1. decoded inbound server operations,
2. local user intents,
3. client-script results,
4. timers/scheduled work,
5. lifecycle operations.

CP3D through CP3G define ordering, commit, replay, and session-revision behavior *after* a concrete ordered operation sequence has been assembled. Policy for merging multiple source queues into that ordered sequence remains an application/runtime concern to specify when those producers exist. Parallel preparation may never alter the final committed order.

## Events

Events describe committed facts, not requests. Example distinction:

- command/input: `RequestMenuAction`
- operation: validated semantic interaction
- event: `InventorySlotChanged`

Events should carry stable semantic identifiers and the minimum payload consumers require. Event generation must occur only after the corresponding mutation successfully commits.

## StateDelta

A `StateDelta` is an efficient description of what changed. Renderer/UI may use it to incrementally update projections. It is not independently authoritative and can be discarded/rebuilt from state.

## Canonical semantic fingerprints

CP3J defines a versioned canonical fingerprint surface for deterministic regression/replay evidence.

The fingerprint surface is **derived read-only metadata**. It is not authoritative semantic state, a replay log, a security primitive, or a substitute for exact semantic values when those values are already available.

`SEMANTIC_FINGERPRINT_VERSION` is `4` for scene selection (CP3J used v1; offline title v2; authenticated bootstrap v3). Schema v4 uses deterministic `FNV-1a-64` over an explicitly tagged canonical byte stream. Rust's `Hash`, `DefaultHasher`, randomized hasher seeds, pointer addresses, memory layout, platform state, renderer state, and wall-clock values are forbidden inputs.

Canonical integers are encoded little-endian and enum/option/list boundaries use explicit byte tags. State and commit-stream fingerprints have distinct ASCII domain prefixes, so equal byte payloads in different semantic domains cannot intentionally share one namespace.

### State fingerprint

`GameState::semantic_fingerprint()` returns `StateFingerprint` for the currently observable authoritative semantic state.

Schema v4 includes, in exact order:

1. session lifecycle,
2. optional active `Revision`, including explicit subrevision presence,
3. optional observable `GameTick`,
4. non-secret `TitleScreen`,
5. optional authenticated identity (profile u32, player index u32, member byte, staff byte),
6. optional initial placement (position then map anchor: x i32, y i32, plane byte; optional area (presence byte, then u32 when present)),
7. optional scene descriptor (presence byte, then SHA-256 ID bytes, origin x i32/y i32/plane byte, width u32/height u32).

The implementation derives these values through immutable semantic queries rather than hashing `GameState` memory/layout. Two histories that reach the same observable semantic state therefore produce the same state fingerprint.

As future authoritative domains are added, every new observable semantic field requires an explicit fingerprint decision. If adding that field changes the canonical encoding/output, the fingerprint version and golden vectors must change in the same checkpoint.

### Committed-output stream fingerprint

`CommitStreamFingerprintBuilder` incrementally fingerprints successful `OperationCommit` values in exact authoritative commit order and finishes as `CommitStreamFingerprint`.

Schema v4 includes for every commit:

1. an explicit commit-item marker,
2. optional `SemanticEvent`,
3. explicit event variant plus payload when present,
4. lifecycle-changed `StateDelta` flag,
5. game-tick-changed `StateDelta` flag,
6. title-changed `StateDelta` flag,
7. connection-changed `StateDelta` flag,
8. scene-changed `StateDelta` flag.

The finite stream ends with an explicit terminal marker. A different order of the same commits is a different canonical stream.

Operations themselves are not duplicated into this output fingerprint. `ReplayEnvelope` already pairs operation input with the exact resulting commit; the commit-stream fingerprint answers whether committed semantic output was identical.

The fingerprint is deliberately non-cryptographic. It is suitable for deterministic regression checks, replay diagnostics, fixture identities, and CI evidence. Future content authenticity or adversarial integrity requirements require a separately specified cryptographic mechanism.

## Replay

CP3F defines the first executable semantic replay boundary; CP3G makes session revision identity part of that semantic stream; CP3J verifies replay equivalence with canonical state and committed-output fingerprints.

`ReplayEnvelope` pairs:

- one source-neutral `SemanticOperation`, and
- the exact `OperationCommit` that was recorded when that operation originally succeeded.

The envelope is currently an **in-memory semantic contract**, not a stable on-disk or wire format. It deliberately contains no byte serialization, version header, compression, packet capture, wall-clock timestamp, protocol opcode, platform handle, or renderer/plugin data.

Session revision is already replayable without a separate replay-only identity path:

- `BeginSession { revision }` records the requested semantic compatibility identity,
- successful application stores that revision in authoritative `GameState`,
- `SessionBegan { revision }` records the committed fact,
- replay re-applies the operation through the normal gateway and exact-commit comparison verifies the revision again.

A future persistent replay header may surface revision/profile information for indexing or early compatibility checks, but it must be derived from or validated against the canonical semantic session identity rather than becoming a competing source of truth. Concrete server-profile identity remains deferred until the canonical `ServerProfile` abstraction exists.

`GameState::replay_envelopes` and `replay_envelopes_with_commits` replay envelopes in exact slice order. Replay always calls the normal `GameState::apply_operation` mutation gateway; there is no replay-only state mutation path.

For each envelope:

1. replay applies the recorded semantic operation,
2. a rejected operation stops replay at that exact zero-based index with the original typed `OperationError`,
3. if the operation succeeds, replay compares the newly produced `OperationCommit` with the recorded commit using exact equality,
4. a commit mismatch stops replay at that exact index and reports both expected and actual commit metadata,
5. verified-commit callbacks run only after the actual commit matches the recording exactly,
6. later envelopes are never attempted after a rejection or mismatch,
7. an empty replay sequence is a valid no-op.

A replay rejection leaves the rejected operation uncommitted, following normal operation semantics. A commit mismatch is different: the mismatch can only be known after the operation has successfully committed and produced its actual metadata. Therefore replay state after a commit mismatch is invalid for continued use and must be discarded rather than rolled back or resumed.

This design intentionally avoids cloning the authoritative state tree or introducing rollback authority solely for replay verification.

Current replay is sufficient to prove that the modeled semantic operation stream, including active session revision identity, can reconstruct equivalent modeled semantic state without network or GPU access. Persistent replay-file format work remains deferred.

Raw packet capture may be an optional diagnostic stream later, but it is not the canonical semantic replay contract.

## Determinism

- No wall-clock reads inside domain transitions.
- Randomness enters through an injected deterministic source and future random-dependent semantic behavior receives its deterministic random input explicitly.
- HashMap iteration order must not define visible behavior.
- Parallel preparation may not change committed operation order.
- Identical initial state plus an identical valid operation sequence must produce identical observable state, active session revision, and commit metadata.
- Identical initial state plus an identical operation batch must produce the same successful prefix, commit metadata order, first failure index/error, and final observable state.
- Replaying an identical `ReplayEnvelope` sequence from equivalent initial semantic state must reproduce the recorded `OperationCommit` sequence and session revision exactly.
- Replay divergence must stop at the first exact envelope index where an operation is rejected or commit metadata differs.
- Identical modeled semantic state must produce identical `StateFingerprint` under the same `SEMANTIC_FINGERPRINT_VERSION`.
- Identical ordered successful commit streams must produce identical `CommitStreamFingerprint` under the same fingerprint version.
- Canonical fingerprint output must be architecture-independent and must never use Rust randomized hashers.

## Acceptance criteria

- Tests can construct a state and apply typed semantic operations and ordered operation batches.
- An active session exposes exactly the `Revision` supplied by `BeginSession`; inactive state exposes no revision.
- Ending a session clears revision identity before another session begins.
- `SessionBegan` commit metadata carries the exact committed revision.
- Identical initial state + inputs produce identical observable state/events.
- Batch tests prove exact ordering, partial-commit semantics, first-failure reporting, suffix suppression, and successful-prefix commit delivery.
- Successful mutations expose committed event/delta metadata; failed operations expose none.
- A successful semantic no-op produces no event and an empty `StateDelta`.
- Replay tests can record semantic operation/commit envelopes, replay them into equivalent initial state without network/GPU access, and reconstruct equivalent modeled semantic state including session revision.
- Replay tests detect the first rejected operation and first commit mismatch at the exact envelope index.
- Replay consumers receive callbacks only for commits that exactly match their recorded envelope.
- Fingerprint tests freeze exact schema-v4 state and committed-output golden vectors.
- Identical semantic runs produce identical final state and committed-output stream fingerprints.
- Replay of a recorded semantic sequence reconstructs the same canonical state and committed-output stream fingerprints.
- Projection rebuild equals incremental projection in reference tests once projections exist.

## Offline pre-session title state (schema v2)

`GameState::title_screen()` is the single owner of non-secret welcome,
credential-feedback and local information navigation. `SetTitleScreen` is valid
only while inactive, emits `TitleScreenChanged { previous, current }` and sets
`StateDelta::title_changed()` only on change. Repeating a screen is a semantic
no-op. Session begin/end reset title state to Welcome; beginning a session from
another title panel includes the title delta in the existing session commit.
Credentials, hover/focus, preferences and cosmetic flames stay outside semantic
state/replay, as specified by SPEC-0013. No offline title action begins a session.

The title state canonical encoding is: Welcome tag `0`; Credentials tag `1`
followed by Prompt `0`, UsernameRequired `1`, PasswordRequired `2`, Offline `3`;
Information tag `2` followed by NewUser `0` or Help `1`. Event tag `3` encodes
previous then current title state. Existing event tags stay unchanged.
State and commit fingerprints use the same version-2 domain headers, and
exact vectors were independently derived from that byte contract.

## Authenticated bootstrap (schema v3)

See SPEC-0014. `AuthenticateSession` establishes lifecycle/revision and canonical
`ServerProfileId` plus player identity in one validated transition. It emits
`SessionAuthenticated` (fingerprint tag 4, revision then identity) and changes
lifecycle, game time and connection delta domains. Legacy `BeginSession` remains
available for synthetic semantic fixtures. `EstablishInitialPlacement` requires
authentication, consistent planes and no prior placement; its event tag is 5 and
contains position, map anchor and area in the state encoding order. EndSession
clears identity and placement and marks the connection delta domain.

Title feedback appends Connecting tag 4 and Failed tag 5 followed by failure byte:
Connection 0, Authentication 1, CacheMismatch 2, Protocol 3, Disconnected 4.
Passwords and network cipher/challenge state are absent from every semantic type.

## Immutable scene selection (schema v4)

`InstallScene(SceneDescriptor)` requires established placement inside the descriptor's bounds/plane, and rejects a second installation. It emits `SceneInstalled` (tag 6, payload encoded as above), with scene delta bit 16. Session boundaries reset the scene reference and invalidate the scene projection domain. No CPU mesh, GPU handle, camera or credential draft enters semantic state. Immutable CPU assets may be prepared before the operation, but presentation selects only the committed descriptor. Replay failures box their detailed mismatch payload so success-path `Result` values stay small.

## Fingerprint schema: version 7 (SPEC-0018)

The earlier v4 encoding description above is historical. Version 7 retains the
v6 scene/scenery/entity/gameframe domains and additionally encodes full modeled
player appearance (gender, ordered equipment/kit slots, colours, movement
sequences, combat/skill levels and hidden flag), player/NPC animation cycles,
and player locomotion (idle/walk/run/turn). Generation counters remain derived
invalidation metadata and are excluded. Exact regression vectors live in the
fingerprint contract tests.

`MapRebuilt` uses event tag 13 with the current placement encoding. Ordinary
rebuilds replace map selection, invalidate its scene, clear map-local location
overrides and preserve variables and world entities. Scene installation validates
against the current map anchor, since a teleport's confirmed player update can
arrive after its rebuild. Confirmed plane changes invalidate the current scene
and update the existing map selection's plane; entity deltas also mark the map
selection/scene domains. Advancing client cycles advances entity playback through
the same mutation gateway and marks the entity domain when entities exist.

## Current fingerprint schema: version 8 (NPCs and inventories)

Retains schema 7, with these canonical additions (integers little-endian):

- After selected-tab encoding, inventories: u32 container count, ascending
  InventoryId u32, u32 slot count, then slot-order option tags (u8 0/1), and
  present item ID u32 and quantity u32. Empty containers are observable.
- After NPC count, u64 lifetime allocation serial, even when no NPCs remain.
  Each NPC begins with u64 instance ID before its existing index. After sequence
  presence/ID, encode locomotion u8 (idle=0, walk=1, run=2, turn=3), name option
  u8 and UTF-8 length u64/bytes when present, five action visibility u8 values,
  frame option u8/u32, cycle i32 and delay i32. Existing NPC animation_cycle is
  base locomotion playback; ActorSequenceSlot is action playback.
- InventoryChanged uses event tag 14 followed by InventoryId u32. The inventory
  delta boolean follows the gameframe boolean in each commit. StateDelta uses
  bit 512 and session boundaries invalidate it.

NPC lifetime serials are semantic identity allocation state and reset at session
boundaries; projection generations remain excluded. Complete NPC batches and
inventory operations validate before mutation. No-op updates emit no event;
new/removal/reuse, sequence delay, visibility, names and container contents replay
through the same gateway. Exact schema 8 golden vectors reside in the contract
tests alongside replay and malformed-operation checks.

## Current desktop continuation fingerprint

Schema 11 adds bounded server game-message history/filter state, player display
names, reboot countdown/message and script-authored viewport properties. They
mutate through ordinary operations, emit explicit deltas/events, reset at session
boundaries and participate in replay. Earlier schema sections above are historical
checkpoints; exact current regression vectors live in `game-state`'s fingerprint
contract tests. Interface topology epochs remain disposable invalidation tokens.


## Current input/entity continuation: fingerprint schema 13

Schema 13 covers the committed fixed/resizable window metrics and defaults,
numeric device preferences, script-authored widget drag configuration (including
an optional parent address), player action slot frame/cycle/delay, and remote
player tracking. The entity owner retains the player lifetime serial, visible
instance identities, profile-supplied tracking grid dimensions and outside-view
region/appearance/orientation. They reset at session boundaries and replay through
ordinary operations; rejected player batches leave the entire prior state intact.
`ResetActorAnimations` clears player and NPC action slots with an entities delta;
an already clear collection is a no-op. Input capture, layout extents, temporary
drag display origins, interpolation queues, click crosses and GPU sorting remain
disposable projections outside the canonical fingerprint.

Current exact state vectors: inactive `4547667724600293240`, revision 240 at tick 5
`2727580851940192160`, revision 240.2 at tick 5 `3773900769062229405`.
The reference commit stream is `13725348769846276724`; empty stream is
`10220152073364718933`. Authentication/placement state is `5742575929545608436`.
Executable contract tests remain the source for these anchors.
