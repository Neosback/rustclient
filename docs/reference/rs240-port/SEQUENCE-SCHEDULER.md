# Sequence assignment and actor movement gating

Status: exact revision-240 behavior identified for assignment policy; full playback
scheduler remains incomplete.

This document records the first concrete deob-to-Rust mismatch found by the port
research branch. It is deliberately narrower than a complete animation audit.

## Sources

Exact revision-240 client evidence:

- `references/rl-suite-240/sources-semantic/classCU.java`
  - `classCU.method2672`
- `references/rl-suite-240/sources-semantic/classQR.java`
  - `classQR.method9770`
  - `classQR.method9791`
  - `classQR.method9793`
- `references/rl-suite-240/sources-semantic/Animation.java`
  - constructor opcode decode
  - `Animation.field5119`
  - `Animation.field5120`
  - `Animation.field5129`
  - `Animation.field5117`
- `references/rl-suite-240/sources-semantic/Actor.java`
  - `Actor.field1471`
  - `Actor.field1476`
  - path-movement checks that consume `Animation.field5129/field5117`

Current Rust owners inspected:

- `crates/cache/src/osrs240/animation.rs`
- `crates/cache/src/world.rs::SequenceAsset`
- `crates/model/src/entity.rs::ActorSequenceSlot`
- `crates/game-state/src/entities.rs`
- `crates/game-state/src/player_movement.rs`

## Exact 240 assignment behavior

`classCU.method2672(Player actor, int requested_sequence, int delay, ...)` performs
policy before mutating the action slot.

### Case 1: requested ID equals current ID

If the ID is `-1`, the same-ID restart logic is skipped and normal replacement
logic continues.

If the ID is not `-1`, the current `Animation.field5120` restart mode controls the
result:

- restart mode `1` calls `classQR.method9791` and reapplies the supplied actor
  sequence delay;
- restart mode `2` calls `classQR.method9793`;
- any other restart mode leaves playback state unchanged;
- after the same-ID branch, the method returns. It does not run replacement
  priority arbitration.

`classQR.method9791` resets three playback counters to zero:

- `field5600`
- `field5602`
- `field5599`

`classQR.method9793` resets only `field5599` to zero.

The exact higher-level semantic names of all three controller counters still need
to be established from advancement code. Their differing reset sets are already a
compatibility requirement. Do not collapse both restart modes into one generic
"restart animation" operation.

### Case 2: requested ID differs from current ID

If the requested ID is not `-1` and the current controller has an active sequence,
`method2672` compares:

```text
new Animation.field5119 < current Animation.field5119
```

If true, assignment is rejected and the current sequence continues.

Therefore `Animation.field5119` is the sequence replacement priority used by this
240 client. It comes from sequence-definition opcode `5`, whose constructor default
is `5`.

If replacement is allowed:

1. `classQR.method9770` changes the controller sequence ID;
2. `classQR.method9791` resets the playback counters;
3. the actor sequence delay is set to the supplied delay;
4. `Actor.field1476` is set from `Actor.field1471`.

The last step is important because `field1476` participates in movement gating.
It is not renderer-only bookkeeping.

## Sequence-definition fields relevant to the scheduler

The `Animation` constructor provides a useful exact opcode map. The fields below
are the ones already proven relevant to actor scheduling.

| Opcode | 240 constructor destination | Default | Established use |
| --- | --- | ---: | --- |
| 1 | frame delays + frame IDs | absent | legacy frame playback |
| 2 | `field5132` | `-1` | frame step/loop-back value, API getter `getFrameStep()` |
| 3 | `field5133` | absent | interleave/order table; presence also affects defaults for opcodes 9/10 |
| 5 | `field5119` | `5` | replacement priority in `classCU.method2672` |
| 8 | `field5122` | `99` | max-loop-like authored limit; exact completion use still to trace |
| 9 | `field5129` | derived | movement gate used while pre-assignment queued path remains |
| 10 | `field5117` | derived | movement gate used when that pre-assignment remainder is exhausted |
| 11 | `field5120` | `2` | same-ID restart mode |
| 13 | `field5126` | `-1` | cached-model/skeletal animation identity path |
| 17 | `field5134` | absent | 256-entry boolean mask; presence also affects defaults for opcodes 9/10 |

Opcode 9 and opcode 10 defaulting is exact:

- if neither the opcode-3 interleave table nor opcode-17 boolean mask exists,
  each missing value defaults to `0`;
- otherwise each missing value defaults to `2`.

This means simply storing raw explicit opcode values is insufficient. The cache
adapter must preserve the final authored policy after constructor/default rules.

Other sequence opcodes are still port-relevant for posing, hand overrides, sounds,
blending and cached-model behavior. They are outside this narrow assignment-policy
finding and remain in the broader `PORT-MATRIX.md` audit.

## Exact movement-gating evidence

The actor source uses two different sequence-definition policy fields based on
`Actor.field1476`.

When an action is active and its explicit delay is zero, normal path movement can
be blocked as follows:

```text
if field1476 > 0 and Animation.field5129 == 0:
    block normal path progress

if field1476 <= 0 and Animation.field5117 == 0:
    block normal path progress
```

`field1476` is reset with path resets, is initialized from `field1471` when a new
action is assigned, and is decremented when queued path entries are consumed.
This establishes that it tracks action/path interaction state tied to the path that
existed when the action began. The precise semantic name should remain provisional
until the complete path scheduler trace is finished.

Do not encode this behavior in scene posing. It changes authoritative movement
progress and therefore belongs in the actor semantic scheduler.

## Current Rust mismatch

### 1. Sequence priority is discarded

`crates/cache/src/osrs240/animation.rs` currently handles opcode `5` together with
other one-byte fields by reading and discarding it:

```text
5 | 9 | 10 | 16 => read u8 and drop
```

`SequenceAsset` has `restart`, `max_loops`, frame/cached-animation fields and range,
but it has no replacement-priority field.

Consequence: `GameState` cannot reproduce the exact lower-priority rejection in
`classCU.method2672`, even if assignment logic is otherwise corrected.

### 2. Both movement-gating policy fields are discarded

The same decoder drops opcodes `9` and `10`, and therefore also loses the derived
constructor defaults based on opcode 3/17 mask presence.

Consequence: a future actor scheduler would not have enough immutable sequence
metadata to reproduce path blocking/catch-up behavior.

### 3. Same-ID restart modes cannot work

`ActorSequenceSlot::set_sequence` currently mutates only when:

```text
self.sequence != requested_sequence
```

For the same ID it performs no action, so revision-240 restart mode 1 and mode 2
are both impossible.

### 4. Playback state is too small for the proven restart distinction

The current `ActorSequenceSlot` contains:

- sequence ID
- optional frame
- aggregate cycle
- delay

It has no explicit loop/iteration counter equivalent to the controller state that
`classQR.method9793` resets independently. Its `advance_cycles` only consumes start
delay and increments aggregate cycle. It does not implement authored finite-loop
completion.

### 5. Assignment has no definition-policy input

Player/NPC attribute application currently calls `set_sequence` with ID/delay only.
No sequence definition is available at the semantic mutation boundary, so the
mutation cannot evaluate:

- same-ID restart mode;
- replacement priority;
- movement-gating policy;
- max loops/completion policy.

This is an ownership/API design issue, not a missing `if` statement.

### 6. Player movement currently has no stock-like queued path state

The current player semantic movement helper directly advances the confirmed tile
for step/run updates. It retains traversal speed and exact movement, but not the
stock actor's multi-entry path queue/action-path remainder represented by the
`field1471`/`field1476` interaction.

This does not automatically mean the Rust design must copy a ten-element Java
array. It does mean the semantic owner needs enough queued-path state to reproduce
cycle-by-cycle movement and sequence blocking rather than asking the renderer to
fake it from packet endpoints.

NPCs already retain an ordered path request. Player and NPC scheduling should
converge on shared actor semantics where the 240 behavior is actually shared.

## Required Rust contract before implementation

Do not patch `ActorSequenceSlot::set_sequence` in isolation. First make the semantic
contract able to express the source behavior.

At minimum, immutable sequence policy must expose:

```text
restart_mode
replacement_priority
movement_gate_with_preexisting_path
movement_gate_after_preexisting_path
max_loops
```

Names can be improved once the complete source trace is finished. The first four
must preserve the 240 values/default rules, not generic assumptions.

Authoritative action playback needs distinct state for at least:

```text
sequence id
start delay
legacy frame or cached-animation time
within-frame time where applicable
loop/iteration count
completion state
path remainder captured/derived for movement gating
```

The exact representation should support both legacy frames and cached-model
animations without making cache objects or renderer resources part of `GameState`.

## Recommended semantic gateway

The clean boundary is a definition-informed action assignment operation, not a raw
packet callback and not a renderer decision.

Conceptually:

```text
protocol mask decode
    -> SequenceChange { id, delay }
    -> application/domain gateway resolves immutable SequencePolicy
    -> GameState validates assignment against current action/path state
    -> committed action state/delta
    -> scene selects/advances pose from committed state + immutable assets
```

Whether `SequencePolicy` lives in `model`, a dependency-light semantic asset view,
or an application-mediated command should be decided against existing dependency
rules before code changes. Do not make `game-state` depend directly on the cache
crate merely to obtain a priority byte.

## Focused verification when implementation begins

No broad gate is required for this research document. The eventual implementation
should have small semantic contracts for:

1. same ID + restart 1 resets full playback counters and reapplies delay;
2. same ID + restart 2 resets only iteration/loop state;
3. same ID + other restart mode is a no-op;
4. lower-priority different ID is rejected;
5. equal/higher-priority different ID replaces and captures path interaction state;
6. clearing with `-1`/`None` behaves independently of priority rejection;
7. opcode 5, 9, 10 and 11 values/defaults survive cache decode;
8. preexisting-path versus post-remainder movement gates block only under the exact
   policy values established by the deob;
9. replay/fingerprint state changes when newly authoritative scheduler fields do.

These should be focused unit/fixture tests, not a workspace-wide validation pass
for the research branch.

## Immediate conclusion

The current animation problem is not primarily a renderer problem. The revision-240
client makes action assignment and movement decisions from sequence metadata that
RustClient currently discards, and it maintains playback/path interaction state
that `ActorSequenceSlot` does not yet represent.

Fixing this semantic scheduler layer should happen before trying to tune animation
visuals around the existing state, otherwise the renderer will be compensating for
incorrect authoritative behavior.
