# ADR-0008: Login, reconnect and world transfer

Status: Proposed, 2026-11-06. Nothing here is implemented beyond what "Current behaviour" states;
the reconnect and transfer paths need a server to verify against, which no session so far had.

## Context

A session today has one shape: log in, play, and end at the title screen on any failure. The client
tears everything down (scene, widgets, plugins' session view) when the socket fails, the server logs
the player out, or the server asks for a world transfer (`LOGOUT_TRANSFER`, decoded into
`SessionControl::Transfer` and then reported as "world transfer unavailable"). The stock client keeps
the loaded world on screen, shows "Connection lost" and retries with a reconnect login before it gives
up; it also hops worlds without a title screen visit. Players on a flaky link, and every world hop,
currently lose their place and must retype a password the client has already discarded.

Facts that constrain the design:

- `GameState` is the single writer; the lifecycle is `Inactive` or `Active` (`game-state/src/state.rs`).
  A lifecycle value reaches fingerprints and replays, so a new one is a schema change.
- The ISAAC ciphers are consumed by the network worker (`ConnectionDecoder`), and the password is
  zeroized after the login block is written (`ui.clear_password()` on disconnect).
- `SessionCounters::reconnect_due` already flags 750 silent cycles (15 s); the network loop's own
  watchdog fails the session after 15 s of silence. They are two clocks for one fact.
- The login body and the reconnect login body are revision details and belong in `openrune_240/login.rs`.

## Decision

1. **States.** Add `Reconnecting` beside `Active` in `SessionLifecycle`. In it, `GameState` keeps
   every domain (scene, interfaces, inventories, actors) but accepts no server operations and
   advances no semantic time. Entering it is an operation (`ConnectionLost`), leaving it is either
   `ConnectionRestored` (back to `Active`, followed by the server's full state refresh) or `EndSession`.
   Fingerprint and replay carry the new lifecycle value; this is a schema bump.
2. **What survives.** Only the lifecycle and the visible world. Outbound actions queued while
   reconnecting are dropped: they were chosen against a world the server may have moved on.
   Interface and map epochs advance on restore so stale actions cannot cross.
3. **Retry policy.** One owner for "the connection is dead": the network loop. It reports
   `ConnectionLost` once after the existing 15 s silence or a socket error, then retries on a fixed
   backoff (for example 1, 2, 4, 8, 16 s), at most five attempts, each cancellable. A refused
   reconnect (`LoginRejection`) other than a transient one ends the session at once with the
   rejection shown. `SessionCounters::reconnect_due` is removed in favour of that one clock.
4. **Credentials.** The password is never kept. A reconnect uses the session's retained login token
   and the ISAAC seeds the server issued for the session (the reconnect login type of the 240
   protocol), held by the network worker only, zeroized when the session ends. If the server does not
   support that login type, the client does not retry and ends the session as it does now.
5. **World transfer.** `SessionControl::Transfer` carries the target host and world. It becomes a
   reconnect to that endpoint with the same session secret, reusing the `Reconnecting` state so the
   world stays on screen until the new server answers. The world list stays a separate feature.
6. **Plugins.** They see `GameState` projected as `CONNECTION_LOST` while `Reconnecting` and
   `LOGGED_IN` again after restore (RuneLite's `GameState` values), with no `Stopped`. Ending the
   session still stops them.
7. **UI.** The stock "Connection lost. Please wait - attempting to reestablish." box over the frozen
   world, plus the attempt count in diagnostics.

## Consequences

- One new lifecycle value, three operations and one projection mapping; the fingerprint version moves.
- The reconnect login body, the retained session secret and the server's response must be verified
  against OpenRune (or a recorded reference session) before it is switched on; until then the
  `Reconnecting` state can be exercised only with the in-memory network loop (`session_loop` tests).
- Transfer behaves like reconnect, so both share the same tests.

## Alternatives

- Reconnect from the title screen with stored credentials: simpler, but stores a password and drops the world.
- Keep the 15 s kill and add only a retry button: no new state, but the player still loses the session on every blip.
- Make `Reconnecting` UI-only, outside `GameState`: plugins and replay would not see it, and the rule that
  lifecycle is semantic state would have an exception.

## Current behaviour

Socket failures and server logouts end the session at the title screen with a message
(`LoginFailure::Disconnected` or the logout reason). A transfer request ends the session with
"world transfer unavailable". Login refusals show the server's reason (`LoginFailure::Rejected`).
