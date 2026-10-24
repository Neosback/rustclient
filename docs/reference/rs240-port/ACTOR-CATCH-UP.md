# Revision 240 actor movement catch-up (`field1452`)

Status: ordinary-path catch-up is implemented authoritatively for players and resolved NPCs; forced/exact-movement reset timing remains deliberately deferred to the forced-movement scheduler.

This document records the exact revision-240 meaning of `Actor.field1452` and the RustClient ownership boundary used by the ordinary actor path scheduler.

## Exact revision-240 evidence

Primary evidence is `references/rl-suite-240/sources-semantic/Actor.java`, especially `Actor.method3284`.

`field1452` is initialized to zero by the actor constructor. It is not a sequence-definition field and it is not a queue length. It is a signed live actor counter representing ordinary movement cycles that were prevented by the active action-sequence movement gate.

In the normal-path branch of `method3284`:

1. an empty path resets `field1452` to zero;
2. if an active action sequence with zero explicit delay blocks movement through the opcode-9 gate (`field1476 > 0`) or opcode-10 gate (`field1476 <= 0`), `field1452` increments by one and the actor does not move that cycle;
3. once movement is allowed and the target gap is at most 288 fine units, ordinary backlog/turning step selection runs first;
4. after those ordinary step choices, if `field1452 > 0` and path backlog is greater than one, the base step is forced to 8 and `field1452` decrements by exactly one;
5. traversal scaling then applies: run doubles that base step and crawl halves it;
6. a gap greater than 288 takes the snap branch and consumes the target without decrementing `field1452`;
7. therefore a snap can leave positive catch-up debt until a later ordinary eligible cycle or until a reset condition clears it.

The check is signed `field1452 > 0`. RustClient therefore stores a signed counter and uses wrapping increment semantics rather than inventing saturation behavior absent from the client evidence.

## Reset behavior

Revision 240 does not reset the counter on every queue clear.

Hard/immediate placement helpers such as `Actor.method3123` / `method3124` reset the path length, `field1476`, and `field1452` together before placing the actor at the supplied tile.

By contrast, several queue-only helpers clear path length and `field1476` without touching `field1452`. Therefore the counter belongs to actor movement state, not to a generic queue-clear operation.

Both forced/exact movement phases inside `Actor.method3284` reset `field1452` to zero on each cycle in which those phases are active. RustClient does not apply that reset merely because `ExactMovement` is present: the current trajectory marker can remain stored beyond its active interval, and the authoritative movement loop does not yet carry the exact per-inner-cycle forced phase. The forced-movement checkpoint must own that reset when it ports the exact timing and action-frame boundary gate.

## `method3292` is not the mutation authority

The RuneLite-injected/auxiliary float movement helper `Actor.method3292` also observes positive `field1452` when choosing base step 8, but it does not perform the authoritative decrement. The state mutation used for port parity is in `Actor.method3284`.

RustClient therefore keeps `GameState` as the sole authority and does not model a second render-owned catch-up counter.

## RustClient ownership

`game-state::ActorPathQueue` owns:

- the bounded ordinary target queue,
- the authoritative fine-position cursor,
- the resolved NPC path-acceleration policy where required,
- `catch_up_cycles`, the revision-240 `field1452` equivalent.

This is appropriate because the counter changes future ordinary movement speed and is shared by player and NPC scheduling. It is not immutable cache policy and is not action-sequence playback state.

`game-state::actor_movement` applies the counter in this order for ordinary movement:

```text
action gate blocks
    -> catch_up_cycles += 1
    -> no movement

later ordinary movement allowed
    -> gap > 288 ? snap without spending debt
    -> otherwise choose normal/definition backlog step
    -> if debt > 0 and backlog > 1:
           base step = 8
           debt -= 1
    -> run/crawl traversal scaling
    -> advance fine position
    -> consume target if reached
```

An ordinary empty queue resets the debt. `ActorPathQueue::reset_to` also resets it because that method represents immediate/hard placement. `ActorPathQueue::clear` deliberately preserves it because a generic queue clear is not sufficient evidence for a reset.

## Interaction with pending turning work

The stock standard-acceleration branch can reduce base step to 2 for turning before applying backlog acceleration and catch-up. Catch-up is later in that ordering and therefore overrides a turning slowdown to base step 8 when its condition is true.

RustClient has not yet ported turning slowdown. When it is added, the catch-up test must remain after the turning/backlog calculations and before run/crawl scaling.

## Fingerprint/replay consequence

Positive catch-up debt changes future authoritative position, so it participates in semantic fingerprint schema v21 as a signed `i32` in the actor-path encoding.

Two otherwise-identical actors with different catch-up debt are intentionally different semantic states.

## Implemented scope

Implemented in the ordinary authoritative scheduler:

- increment on opcode-9/opcode-10 action-gated path cycles;
- signed positive-debt test;
- backlog-greater-than-one requirement;
- base-step override to 8;
- one-debt decrement per eligible ordinary cycle;
- decrement before run/crawl scaling;
- no decrement on the >288 snap branch;
- empty-queue reset;
- hard-placement reset through `reset_to`;
- preservation across generic queue `clear`;
- semantic fingerprint participation.

Still deferred:

- forced/exact phase reset timing and interpolation;
- the legacy action-frame boundary condition during forced interpolation;
- turning slowdown and desired/current orientation scheduling;
- scene-bound safety-reset parity where it indirectly invokes hard actor reset behavior.

The next movement dependency should be authoritative forced/exact movement because it closes both the remaining positional projection exception and the remaining exact reset site for this counter.
