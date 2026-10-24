# Revision 240 forced/exact actor movement

Status: forced/exact player and NPC translation is authoritative in `GameState`, including the legacy action-frame boundary gate, active-cycle `field1452` reset, desired/current forced-orientation behavior, and scene projection migration.

## Exact revision-240 control flow

Primary evidence is `references/rl-suite-240/sources-semantic/Actor.java`, especially `Actor.method3284`.

The method has three mutually ordered position phases around the current client cycle:

1. **Approach phase** while `forced_start_cycle >= current_cycle`.
   - Compute the forced start fine coordinate.
   - Divide the remaining delta by `max(1, forced_start_cycle - current_cycle)` and approach that start coordinate.
   - Reset `field1452` catch-up debt to zero.
   - Set desired orientation to the authored forced direction.
   - Leave current body orientation unchanged.

2. **Interpolation phase** while `forced_end_cycle >= current_cycle` after the approach condition is false.
   - Interpolation is always allowed on the exact end cycle.
   - It is also allowed when there is no active action sequence or the action has non-zero explicit delay.
   - Cached-model/skeletal actions allow interpolation without the legacy frame-boundary test.
   - A legacy action with zero explicit delay allows interpolation only when `current_frame_cycle + 1 > current_frame_delay`.
   - When allowed, linearly interpolate the forced start/end coordinates using `(current - start) / (end - start)`.
   - Reset `field1452` to zero.
   - Set desired orientation to the authored forced direction and current orientation equal to desired orientation.
   - The current-orientation snap occurs for the interpolation phase even when the legacy action-frame gate holds position for that cycle.

3. **Ordinary path phase** once `current_cycle > forced_end_cycle`.
   - Clear the expired forced trajectory marker.
   - Resume ordinary scheduling in that same client cycle.

The approach condition is inclusive, so a zero-duration forced interval is handled by the approach branch at its start/end cycle rather than by division through a zero interpolation duration.

## Sequence timing and cycle ordering

The legacy interpolation gate reads the active action's current frame and within-frame cycle before action playback advances for that client cycle. Cached-model actions bypass the legacy frame-delay gate.

RustClient carries revision-neutral `SequenceTiming` from cache through application orchestration:

- `Legacy { frame_delays, frame_step }`
- `CachedModel { range }`

`WorldEntitiesState` retains immutable timing evidence once, while each actor retains whether timing was actually supplied for its currently accepted action. Registry history therefore cannot silently resolve a later direct fixture that omitted timing.

The authoritative client-cycle order is:

```text
SceneryState previous ClientCycle
    -> derive exact inner current cycle
    -> actor movement / forced gate using pre-advance action state
    -> advance action delay/frame state
```

This preserves the revision-240 legacy test against the frame boundary that will be crossed later in the same cycle.

For resolved legacy timing, the semantic playback helper advances frame/frame-cycle state and applies the retained rewind/max-loop inputs. Cached-model family classification is sufficient for the forced gate, but complete cached-model playback/completion remains separate work.

## Authoritative position and orientation transition

`game-state::actor_movement` now owns forced movement for players and NPCs:

- pre-start approach writes `ActorPathQueue` fine position;
- active interpolation writes the same authoritative fine cursor;
- an unresolved zero-delay action holds mid-flight rather than inventing a legacy frame duration;
- the exact end cycle always reaches the authored interpolation result even if timing is unresolved;
- active approach/interpolation cycles reset `catch_up_cycles`;
- approach updates desired orientation only;
- interpolation/held forced cycles set current orientation equal to the forced desired orientation;
- expired trajectories are cleared before ordinary path scheduling resumes.

Visible players and NPCs now retain both values explicitly: existing `orientation` remains desired orientation for compatibility, while `current_orientation` is the current body rotation. Ordinary smooth-turn advancement remains deliberately disabled until its exact revision-240 mutator is pinned.

## Projection boundary

`crates/scene/src/authoritative_motion.rs` snapshots player and forced-NPC translation from `ActorPathQueue::fine_position(...)` just like ordinary authoritative movement.

The old scene motion implementation may still maintain disposable pose/turn state, and it remains a fallback for deliberately unresolved **ordinary** NPC fixtures. It is no longer the positional authority for connected forced movement. Draw-rotation migration to authoritative `current_orientation` belongs with the smooth-turn checkpoint so scene rotation does not freeze while GameState turning is intentionally not advancing yet.

## Fingerprint/replay consequence

Active sequence timing resolution can change whether a forced actor moves or holds on the next client cycle, and current orientation is now independent live actor state. Semantic fingerprint schema v23 therefore fingerprints:

- desired orientation;
- current orientation for visible actors;
- unresolved/legacy/cached-model timing bound to the active action.

The historical timing registry itself is not fingerprinted when unused; only timing bound to the active action participates in live semantic identity.

## Remaining gaps

Forced/exact positional ownership and the desired/current forced-orientation distinction are no longer movement gaps. Remaining adjacent work includes:

- the exact ordinary smooth-turn mutator and turn-rate/state writers;
- player/NPC/world-entity and coordinate-target following;
- turning-dependent ordinary speed reduction and directional locomotion selection;
- complete cached-model action playback/completion semantics;
- scene-bound safety-reset parity.
