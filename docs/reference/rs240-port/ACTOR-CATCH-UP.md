# Revision 240 actor movement catch-up (`field1452`)

Status: catch-up is authoritative for ordinary and forced/exact player/NPC movement. Turning-dependent speed remains separate pending work.

This document records the exact revision-240 meaning of `Actor.field1452` and the RustClient ownership boundary used by the actor movement scheduler.

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

The check is signed `field1452 > 0`. RustClient stores a signed counter and uses wrapping increment semantics rather than inventing saturation behavior absent from the client evidence.

## Reset behavior

Revision 240 does not reset the counter on every queue clear.

Hard/immediate placement helpers such as `Actor.method3123` / `method3124` reset the path length, `field1476`, and `field1452` together before placing the actor at the supplied tile.

By contrast, several queue-only helpers clear path length and `field1476` without touching `field1452`. Therefore the counter belongs to actor movement state, not to a generic queue-clear operation.

Both active forced/exact movement phases inside `Actor.method3284` reset `field1452` to zero every client cycle. RustClient now applies that reset from authoritative per-inner-cycle forced phase selection, not merely from `ExactMovement` marker presence. Once `current_cycle > forced_end_cycle`, the marker is cleared and ordinary path scheduling resumes in that same cycle without an extra reset.

## `method3292` is not the mutation authority

The RuneLite-injected/auxiliary float movement helper `Actor.method3292` also observes positive `field1452` when choosing base step 8, but it does not perform the authoritative decrement. The state mutation used for port parity is in `Actor.method3284`.

RustClient therefore keeps `GameState` as the sole authority and does not model a second render-owned catch-up counter.

## RustClient ownership

`game-state::ActorPathQueue` owns:

- the bounded ordinary target queue,
- the authoritative fine-position cursor used by both ordinary and forced movement,
- the resolved NPC path-acceleration policy where required,
- `catch_up_cycles`, the revision-240 `field1452` equivalent.

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

During either active forced phase:

```text
forced approach/interpolation owns this client cycle
    -> catch_up_cycles = 0
    -> forced fine-position transition
    -> forced orientation
```

An ordinary empty queue resets the debt. `ActorPathQueue::reset_to` also resets it because that method represents immediate/hard placement. `ActorPathQueue::clear` deliberately preserves it because a generic queue clear is not sufficient evidence for a reset.

## Interaction with pending turning work

The stock standard-acceleration branch can reduce base step to 2 for turning before applying backlog acceleration and catch-up. Catch-up is later in that ordering and therefore overrides a turning slowdown to base step 8 when its condition is true.

RustClient has not yet ported authoritative desired/current orientation and turning slowdown. When it is added, the catch-up test must remain after turning/backlog calculations and before run/crawl scaling.

## Fingerprint/replay consequence

Positive catch-up debt changes future authoritative position. It participates in the actor-path portion of semantic fingerprint schema v22; v22 also fingerprints active sequence timing because that timing can hold or advance forced interpolation.

Two otherwise-identical actors with different catch-up debt remain intentionally different semantic states.

## Implemented scope

Implemented authoritatively:

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
- reset on every actually active forced approach/interpolation cycle;
- no over-reset after the forced interval expires;
- semantic fingerprint participation.

Still deferred:

- turning slowdown and desired/current orientation scheduling;
- scene-bound safety-reset parity where it indirectly invokes hard actor reset behavior.

The next movement dependency is authoritative facing/turning because forced/exact positional ownership and the remaining exact `field1452` reset site are now closed.
