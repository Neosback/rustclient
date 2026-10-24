# Actor runtime: revision 240 source map

Status: active navigation document.

This document connects the already-audited revision-240 player/NPC wire behavior
to the remaining shared actor runtime work. It intentionally does not duplicate
the exact transform tables in `docs/plan/PLAYER-UPDATING-240.md` or
`docs/plan/NPC-ANIMATION-240.md`.

## Why this is the first runtime slice

Player and NPC packet framing is now substantially understood. The largest parity
risk has moved one layer downstream: the client must turn committed movement,
facing, sequence and effect state into the same cycle-by-cycle actor behavior as
the 240 client.

A decoder can be byte-perfect while visible movement is still wrong if the actor
scheduler chooses the wrong sequence, restarts an action incorrectly, blocks path
progress on the wrong frame, turns instantly, or completes an exact movement at a
different cycle. Treat packet decode and actor scheduling as separate contracts.

## Source roles

### `classDF.java`: player synchronization manager

High-value symbols already established by the player audit:

- `method3006`: initializes the local player from a 30-bit value and all other
  tracked players from 18-bit outside-view state.
- `method3015`: performs the four independently aligned active/inactive update
  passes, including skip runs and tracking-flag rotation.
- `method3019`: decodes skip counts using a 2-bit selector followed by 0, 5, 8 or
  11 bits.
- `method3043`: extended-mask extension logic.
- `method3050`: ordered extended-information decode.
- `method3046`: deferred ordinary movement commit after extended data is known.

Rust ownership:

- bit/mask/transformed byte interpretation: `crates/protocol/src/openrune_240/player_info.rs`
- validated semantic movement/state: `crates/game-state`
- visual interpolation/pose from committed state: `crates/scene`

Do not retain a decoder-private second copy of persistent player path semantics.
The decoder should emit enough typed information for the semantic owner to apply
the update atomically.

### `classDC.java`: tracked player network/runtime bridge

The existing audit identifies two especially important behaviors:

- `method2961`: exact/forced movement offsets are anchored after the new network
  coordinate is decoded; the forced-movement end becomes the actor path target.
- `method2924`: immediate placement versus queued path movement/reset behavior.
  A position reset does not itself invent a new facing direction.

This class is useful evidence for lifecycle and ordering, but RustClient should not
copy its mixed networking/runtime responsibility. Wire concerns stay in protocol;
movement/path ownership stays in `GameState`.

### `Actor.java`: shared per-cycle actor scheduler

`Actor.method3284` is the key shared scheduling entry identified by the current
audit. It contains or coordinates:

- approach toward a future exact-movement start;
- interpolation during forced/exact movement;
- normal path consumption;
- movement blocking/catch-up interactions with action sequences;
- orientation/facing updates;
- locomotion sequence selection inputs;
- cycle-sensitive actor state expiration/update.

RustClient already models exact trajectories and ordinary movement, but this source
must be traced far enough to separate authoritative scheduler state from render-only
interpolation.

### `classCU.java`: action assignment policy

`classCU.method2672` is the current highest-value missing policy source. It covers
same-sequence reassignment and action priority behavior.

The existing audits establish these required concepts:

- assigning the same action ID is not always a no-op;
- restart mode 1 restarts playback/delay state;
- restart mode 2 resets loop-count-related state without the same full restart;
- action replacement is subject to authored priority arbitration.

These are definition-driven semantic policies. They should enter the actor semantic
gateway using decoded sequence metadata, not be guessed in the scene mesh builder.

### `Animation.java`: sequence definition inputs

The semantic `Animation` class is the API-derived sequence definition class in this
240 artifact. The actor scheduler needs only the authored policy/timing data;
geometry posing belongs in scene/cache preparation.

The audit must explicitly separate:

1. sequence-definition fields: frame/cached-animation identity, lengths, loop
   metadata, restart mode, priority, movement-blocking policy, interleave/bone mask,
   sound/effect references;
2. authoritative actor playback state: current sequence, delay, frame/time,
   iterations/loops and completion;
3. projection state: selected prepared pose/blend for the current cycle.

Do not merge these three layers into a renderer-owned animation object.

## Required actor semantic state

The final exact fields may differ, but the semantic model must be able to express
the following without consulting raw packets or renderer caches.

### Position/path

- current authoritative tile/plane;
- queued path steps and traversal mode per step;
- persistent/default traversal mode where revision behavior requires it;
- immediate placement/reset serial or equivalent lifecycle signal;
- exact/forced trajectory with start/end coordinates and inclusive cycle bounds;
- path delay/catch-up counters if confirmed by `Actor.method3284`.

### Facing

- current orientation;
- desired/target orientation;
- authored turn rate;
- entity target identity, with player/NPC distinction if wire encoding requires it;
- explicit face-coordinate/face-angle request;
- whether target disappearance clears or preserves a pending facing request;
- turn-left/turn-right/turn-in-place sequence selection inputs.

### Locomotion

- idle sequence;
- walk forward/back/left/right sequences where authored;
- run and crawl variants where authored;
- currently selected semantic locomotion mode/sequence if selection is stateful;
- movement speed derived from traversal/path scheduling rather than renderer guesswork.

### Action sequence

- optional sequence ID;
- assignment/start cycle and delay;
- frame/cached-animation time state;
- iteration/loop count;
- restart mode policy source;
- replacement priority policy source;
- movement-blocking/catch-up state;
- completion/clear state.

### Effects and overhead state

These are currently important gaps and should be modeled only after their exact
mask/scheduler behavior is traced:

- actor spot-animation slots;
- hitsplats;
- health bars/headbars;
- overhead text and its color/effect/expiry state;
- temporary tint/color/texture/transparency overrides;
- freeze/reset/customization fields present in 240 masks.

## Cycle ordering to establish

The actor audit is not complete until the exact per-cycle ordering below is known.
The list is a research checklist, not a claimed order:

1. apply newly committed network changes;
2. choose approach/exact/normal path position update;
3. process movement blocking or catch-up counters;
4. update target/facing and turn orientation;
5. select locomotion sequence from direction/speed/turn state;
6. advance action sequence and decide blocking/completion;
7. advance spot/effect sequences;
8. expire overhead/hits/headbars/tints;
9. emit immutable projection inputs for scene rendering.

Trace `Actor.method3284` and direct helpers/callers to replace this checklist with
an exact order. A visually plausible order is not sufficient because plugins,
projectiles, model orientation and action frame timing can all observe differences.

## Exact-movement invariants already established

Do not regress these while completing the scheduler:

- exact offsets are normalized after applying the newly decoded network tile;
- invalid intervals/directions/arithmetic reject the semantic update rather than
  partially mutating actor state;
- the trajectory end becomes the path target;
- the completed trajectory holds its end until new network movement;
- equal start/end times follow the deob's inclusive approach behavior;
- scene interpolation is a projection of committed semantic coordinates/cycles;
  predicted render coordinates never feed back into `GameState`.

## Player versus NPC differences

Prefer one shared actor scheduler with definition/profile inputs, but preserve real
differences rather than forcing false unification.

Known examples to keep explicit:

- player appearance supplies locomotion/default sequence identity differently from
  NPC definitions;
- NPC definitions author forward/back/left/right run/crawl variants;
- update masks and entity target encodings can differ;
- player outside-view tracking/lifecycle is structurally different from NPC list
  synchronization;
- NPC morphs can change definition-derived animation/size/model data at runtime.

Shared rules such as orientation math, path traversal, exact trajectory timing and
action restart policy should not have independent player and NPC implementations
unless the deob proves a behavioral difference.

## Port boundary

### Protocol must own

- update-mask bits and extension scheme;
- transformed/endian reads;
- signed/sentinel interpretation that is revision-specific;
- player/NPC index bit widths and list synchronization encoding;
- exact packet field ordering.

### GameState must own

- entity lifecycle after decode;
- current tile/path/trajectory;
- facing target/orientation state;
- action/locomotion/effect scheduler state;
- validated cycle transitions and expiration;
- deterministic replay/fingerprint representation for observable semantic fields.

### Cache must own

- sequence definitions and authored policy fields;
- player/NPC definition/model dependencies;
- spot/hitsplat/headbar definitions.

### Scene must own

- deriving the pose for a committed actor state at a cycle;
- legacy/cached-model animation application and blending;
- model composition/transforms and geometry preparation;
- render interpolation that does not mutate semantic truth.

### Renderer must own

- GPU resources/passes/draw ordering only after scene projection has produced the
  required geometry/material semantics.

## Next source traces

Work these in order because each one removes ambiguity for the next:

1. `classCU.method2672` plus all fields read from `Animation`: write the exact
   action-assignment state machine, including same-ID modes and priority.
2. `Actor.method3284` call tree: isolate normal path scheduling, action movement
   blocking, catch-up counters and exact-movement frame gating.
3. Actor facing helpers: target identity resolution, turn rate and directional
   sequence choice.
4. Action advancement/completion helper(s): frame/cached-model timing, loops and
   clear conditions.
5. Actor spot animation add/remove/advance helpers and corresponding player/NPC
   mask fields.
6. Hitsplat/headbar add/remove/expiry helpers and replacement priority rules.
7. Overhead/tint/freeze/customization mask consumers and expiry/application sites.
8. `Player`/`PlayerComposition` model assembly invalidation rules so actor state
   changes rebuild only what stock behavior requires.

Each trace should add/update `SYMBOL-LEDGER.tsv` rows before implementation work.

## Existing evidence to keep linked

- `docs/plan/PLAYER-UPDATING-240.md`: exact player wire/movement checkpoint and
  remaining actor gaps.
- `docs/plan/NPC-ANIMATION-240.md`: exact NPC movement/location animation checkpoint
  and remaining shared scheduler/effect gaps.
- `docs/reference/source-manifest.json`: selected evidence hashes.
- `crates/protocol/tests/fixtures/PROVENANCE.md`: selected RSProt source/vector
  provenance for implemented packet families.

Do not call actor parity complete until these shared runtime behaviors are traced,
implemented and verified independently of packet framing success.
