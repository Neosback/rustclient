# SPEC-0022: Actor effects

**Status:** Accepted for the hitmark / health bar / spot animation milestone, 2026-11-05. Drawing
the effects, projectiles, tint, freeze and resolving the facing target into an orientation remain open.
**Owners:** protocol (`openrune_240::actor_effects`), model (`ActorEffects`), GameState
(`ActorEffectsState` on `PlayerEntity` and `NpcEntity`), plugin bridge (snapshot projection).

## Scope and invariants

Server-authored effect blocks of the player and NPC info packets are semantic facts of the actor
that received them. They are stored on the actor entity in `GameState`, stamped with the client
cycle of arrival, and expire with the same cycle clock. No renderer, plugin or timer owns a copy.
Revision-240 bytes stay in the protocol adapter; everything above sees `ActorEffects`.

Source anchors: rsprox 240 `PlayerInfoClient`/`NpcInfoClient` (`decodeHitmark(s)`,
`decodeHeadbar(s)`, `decodeSpotanims`); stock `classDF.method3050` (player block order),
`Actor.method3138` (add hitsplat), `Actor.addHealthBar`, `HealthBarConfig.get` and the
`GrandExchangeOfferWorldComparator` draw code (health bar fill), `classPW` (hitsplat definition
default duration 70).

## Behaviour

- **Hitmarks** (`player 0x100000`, `npc 0x20000`): `type`, `value`, `delay`, `limit` smarts. A
  splat is shown from `arrival + delay` and gone at `arrival + delay + 70`. At most 16 are kept
  per actor, oldest dropped first.
- **Health bars** (`player 0x20000`, `npc 0x1000000`): per bar type, `end_time == 32767` removes
  the type; otherwise an update starts at `arrival + start_time`, moves the fill from `start_fill`
  to `end_fill` over `end_time` cycles (no second byte when `end_time == 0`), and stays for
  `70 + end_time` cycles after it starts. A later started update supersedes earlier ones
  (`HealthBarConfig.get`). At most 16 types and 8 pending updates per type.
- **Spot animations** (`player 0x20`, `npc 0x2`): per slot (0-15), id 65535 clears the slot,
  otherwise it replaces it. Height, delay and loop are retained.
- Blocks for a player not in the scene are dropped. Removing an actor discards its effects, so a
  reused index starts clean. Replay reproduces effects (fingerprint schema 32, facing added in 33).
- Plugins: `ActorState::{hitsplats, healthbars, graphic}` are projected at the current cycle:
  only shown splats, the interpolated fill (or no amount before a bar starts), and the lowest
  occupied spot animation slot. `HitsplatApplied` and `GraphicChanged` follow from the existing
  snapshot diff.

## Accepted deviations

`HITSPLAT-DEFAULT-DURATION`, `HEADBAR-DEFAULT-DURATION` (cache definitions are not consulted for
display time), `HEADBAR-FILL-UNITS` (raw fill, linear, no definition step), recorded in
[PARITY-DEVIATIONS.tsv](../plan/PARITY-DEVIATIONS.tsv).

## Non-goals of this milestone

Drawing hitsplats and health bars, the `limit`-driven replacement of full splat lists, projectiles,
tint, freeze, transparency. The interaction target is retained (see below); resolving the facing
target into a desired orientation is open. BAS, body and head customisation, old
spot animations and player name extras are consumed with exact widths (SPEC-0018) but not retained.

## Facing block and the interaction target (2026-11-06)

Each player and NPC keeps the last facing block the server sent (`FacingBlock`: entity, area, angle or
cleared, plus the walk policy and control flag; deob `classBZ.method1384`, rsprox `decodeFacing`). A later
block of any kind replaces it, so an angle block ends an interaction. `ActorState::interacting` is the
player or NPC an entity block names, and only while that actor is still in the scene (RuneLite's
`Actor.getInteracting` is null otherwise). The fingerprint carries the block (schema 33).

Open: the per-cycle desired-orientation resolver (`classEB.method3820`: moving entity and area targets,
the fallback angle, the control-flag snap and the pending-cleanup latch whose arming is unproven) and the
walk policy's effect on movement. Until then an entity target does not turn the actor; deviation
`INTERACTING-CHANGED-ON-DIFF` records that the plugin event fires when the interaction changes, not on
every decoded facing block as in stock.

## Acceptance

- Protocol: golden bytes per block and byte-transform class, truncation rejection.
- GameState: lifetimes, supersession, bounds, index reuse, replay fingerprint equality.
- Bridge: snapshot fields and events for an NPC with all three blocks.
- Live (NOT-RUN): hitsplat / health bar / graphic traffic from a real server.
