# Revision 240 player extended actor state

<!-- authority-notice -->
> Domain source/ownership research; implementation statements are bounded to the dates
> recorded below. The [current matrix](PORT-MATRIX.md) and [audit](CURRENT-AUDIT.md)
> supersede older gap/status lists; preserve exact source details and historical evidence.

Status: exact stock-client behavior identified for the high-value actor-state blocks
currently consumed but not retained by RustClient. Selected writer identities and remaining consumer gaps are distinguished below.

This document complements `PLAYER-UPDATING-240.md`. That checkpoint is canonical
for bit passes, skip runs, mask extension, transforms and movement framing. This
file asks a different question: **after the bytes are decoded, what client state
must survive so the actor behaves and renders like revision 240?**

## Sources

Exact revision-240 client evidence:

- `classDF.java::method3050`: ordered player extended-mask dispatch.
- `classDC.java`: applies one logical remote-player update to the concrete `Player`
  actor instance(s) that represent it in world views.
- `Actor.java`: spot animations, hitsplats, health bars, overhead state, timed
  model overrides and action/path state.
- `Player.java`: applies timed model overrides during model assembly.
- `Model.java`: receives hue/saturation/luminance/amount and alpha-like overrides.
- `classFL.java`: four-byte model color override state.
- `classAAV.java`: cycle-ranged interpolation of a model-wide byte override.

Current Rust comparison:

- `crates/protocol/src/openrune_240/player_extended.rs`
- `crates/protocol/src/openrune_240/info_fields.rs`
- `crates/game-state/src/entities.rs`
- `crates/game-state/src/player_movement.rs`

## 2026-10-28 corrections

Speech/public chat now cross decode into history/overhead state. Selected RSProt
identifies `0x40000` as FREEZE and `0x8` as PLAYER_RESET; consumer/reset semantics
still need completion. It does not emit legacy `0x2`/`0x200`/`0x80000`. Preserve the
source analysis below without presenting old unresolved names or discarded-chat
claims as current facts. See [CURRENT-AUDIT.md](CURRENT-AUDIT.md).

## Important distinction

A decoder is not complete merely because it consumes the correct number of bytes.
For revision-240 actor parity, fields that alter model composition, overlays,
combat feedback, speech, targeting or cycle state must cross the protocol boundary
as typed semantics and be owned by `GameState` or the appropriate immutable asset
layer.

The current decoder deliberately consumes several known blocks without retaining
them. That was useful for packet alignment, but those blocks now represent a real
port-completeness gap.

## Mask/state matrix

The masks below are the currently selected revision-240 player family handled by
RustClient. Extension-marker bits are omitted from the semantic rows.

| Mask | 240 client flow | Semantic state | RustClient now | Required owner |
| ---: | --- | --- | --- | --- |
| `0x4` | appearance payload -> `Player.method2615` | equipment, body colors, identity/NPC transform, locomotion sequence IDs, name/combat/team and appearance overrides | core retained; transform ID/icons/customization incomplete | protocol -> `game-state::players`; cache/scene consume definitions |
| `0x10` | sequence ID + delay -> action assignment | action sequence request | retained, but assignment policy incomplete | protocol -> shared actor scheduler; see `SEQUENCE-SCHEDULER.md` |
| `0x40` | `classDC.method2933` -> `classBZ.method1384` | angle, area/coordinate target, entity target, secondary facing behavior/control | only direct angle retained | protocol -> typed facing update -> shared actor scheduler; see `MOVEMENT-FACING.md` |
| `0x4000` | traversal enum update | persistent/default traversal mode | retained | protocol -> player movement state |
| `0x800` | temporary traversal byte/sentinel | movement traversal override/reset | retained | protocol -> player movement state |
| `0x8000` | `classDC.method2961` | exact/forced movement trajectory | retained | protocol -> shared actor scheduler |
| `0x20` | records -> `classDC.method2979` -> `Actor.method3148` | actor spot-animation slots | **consumed, discarded** | protocol -> actor effect state; cache spot definition; scene pose/model composition |
| `0x1000` | chat metadata/text -> `classDC.method2955` | public-chat text plus authored message/color/effect/filter metadata | retained in speech/history/overhead path; complete filtering/channels remain partial | protocol chat/Huffman -> game-state chat + actor overhead state |
| `0x20000` | `classDC.method2947` / `method2950` -> `Actor.method3144` / `method3145` | health-bar add/update/remove state | **consumed, discarded** | protocol -> actor health-bar state; cache definition; UI/projection |
| `0x100000` | records -> `classDC.method2942` -> `Actor.method3138` | hitsplats | **consumed, discarded** | protocol -> actor hitsplat slots; cache definition; UI/projection |
| `0x400` | `classDC.method2971` -> `Actor.method3196` -> `Player` model assembly | timed model override: hue, saturation, luminance, amount | **consumed, discarded** | protocol -> timed actor visual state; scene/model projection |
| `0x10000` | `classDC.method2973` -> `classAAV.method271` -> `Player` model assembly -> `Model.method5230` | timed interpolated model-wide byte override affecting face alpha/transparency handling | **consumed, discarded** | protocol -> timed actor visual state; scene/model projection |
| `0x1` | text -> `classDC.method2940` | overhead actor text and its reset/default metadata | retained as SpeechUpdate::Say and actor overhead state; complete acceptance remains open | protocol -> actor overhead state + chat/UI event projection |
| `0x40000` | delay + duration + cancelSequence -> `classDC.method2981` -> `Actor.method3099` / `method3105` | animation-freeze window; sentinel resets it, boolean controls action cancellation | **consumed, discarded** | shared GameState actor freeze scheduler; see SEQUENCE-SCHEDULER.md and cluster 11 |
| `0x8` | `classDC.method2899` plus one trailing byte | PLAYER_RESET clears auxiliary state; selected model documents trailing value as discarded | byte consumed; semantic reset incomplete | typed reset in GameState; preserve source clear order |

The final two writer identities are established by selected RSProt and the actor
consumer trace. They are not provisional linked-player IDs. Their semantic
retention, ordering and downstream reset/freeze behavior remain implementation gaps.

## Spot animations: `0x20`

`classDF.method3050` reads a count, then each entry carries:

- slot/index;
- spot-animation ID;
- one packed value split into two 16-bit values;
- a boolean.

It calls `classDC.method2979`, which applies the record to each represented actor
through `Actor.method3148`.

The actor owns spot animations in a keyed table. The spot helper removes/replaces
an existing entry for the same slot and, when the supplied animation ID is not the
null sentinel, inserts a new `ActorSpotAnim` with:

- animation/graphic ID;
- height/offset-like value;
- start cycle derived from the current client cycle plus delay;
- owning actor;
- boolean mode flag.

The actor separately tracks how many spot-animation entries are present, and its
reset/bounds path clears all spot animations.

### Port consequence

This cannot remain a byte-skip. The semantic actor needs keyed spot/effect slots so
replacement and removal happen by slot rather than by graphic ID alone. Scene
projection then resolves the spot definition/sequence and composes the effect with
the actor model.

## Health bars: `0x20000`

The player update reads a count. Each record begins with a smart health-bar
identifier and a second smart value.

- when the second value is `32767`, `classDC.method2950` calls
  `Actor.method3145`, which finds the matching `HealthBarConfig` and removes that
  bar;
- otherwise additional smart/byte values are read and `classDC.method2947` calls
  `Actor.method3144` to add/update the bar.

`Actor.method3144` resolves `HealthBarConfig`, updates an existing bar with the
same config identity when present, otherwise inserts according to config ordering.
The actor maintains at most four bars and evicts according to definition priority
when required.

### Port consequence

The actor state needs ordered health-bar instances with cycle/value transition
state. The renderer/UI should not reconstruct these from the most recent packet.
Cache ownership remains with health-bar definitions, while active bars and their
cycle interpolation belong to semantic actor state.

## Hitsplats: `0x100000`

The update reads a count of records, four smart values per record, and calls
`classDC.method2942`. That forwards to `Actor.method3138` with the current client
cycle.

`Actor.method3138` is hitsplat scheduling. It uses the hitsplat definition and the
actor's bounded hitsplat collection to choose/reuse a slot and calculate expiry.
RuneLite's injected event surface posts a `HitsplatApplied` event from this path,
but the underlying active hitsplat collection and expiry logic are stock client
semantics.

### Port consequence

Retain hitsplat ID/value/secondary values and expiry timing in `GameState`; expose
plugin events only after the semantic state transition commits. Do not make the
injected RuneLite callback the state owner.

## Timed color override: `0x400`

`classDF.method3050` reads:

- start cycle offset;
- end cycle offset;
- four bytes.

`classDC.method2971` converts the cycle offsets to absolute client cycles and copies
the four-byte `classFL` value into every represented player through
`Actor.method3196`.

The exact API names are visible on `Model`:

1. override hue;
2. override saturation;
3. override luminance;
4. override amount.

`Actor.method3190` makes the override active only when the current client cycle is
within the authored start/end window. `Player` model assembly then calls
`Model.method5232(classFL, ...)`; outside the window the model override is cleared.

### Port consequence

This is time-ranged semantic actor visual state, not a protocol-only decoration.
Store the authored start/end cycles and four values. Scene/model projection applies
it for the current cycle. The renderer only receives the resulting material/model
parameters.

## Timed model-wide alpha-like override: `0x10000`

This block reads:

- start cycle offset;
- end cycle offset;
- starting byte;
- ending byte;
- boolean control.

`classDC.method2973` stores the transition in `classAAV`. `classAAV.method271`
keeps absolute start/end cycles and chooses whether the transition begins from the
current interpolated value or from the supplied starting byte. Its cycle getter
linearly interpolates between start and end values.

`Player` copies the current interpolated byte into its assembled model through
`Model.method5230`. `Model.method5214` uses that byte when calculating per-face
alpha/transparency, moving ordinary alpha values toward the client's opaque-edge
value as the override increases.

### Port consequence

Retain this as an explicit timed actor-model alpha override. Do not flatten it to a
single byte at packet receipt because the client interpolates it every cycle.

## Overhead text: `0x1`

`classDC.method2940` strips a leading `~` marker when present, then for each player:

- clears the filtered/public-chat flag;
- resets overhead color/effect metadata to defaults;
- stores the text on the actor;
- resets the overhead-text lifetime through the actor helper.

For the local player, or when the leading `~` form is used, it also emits a normal
chat-line entry.

### Port consequence

The actor needs its own overhead-text lifetime/state even if the chat log also
records the message. Chat history and the floating actor text are related outputs,
not one shared mutable string.

## Public chat: `0x1000`

The public-chat block carries more state than text alone. `classDF.method3050`
reads packed metadata, a player/chat type enum, autochat/filter state, compressed
text bytes and optional extra metadata before calling `classDC.method2955`.

`classDC.method2955` performs ignore/filter checks, writes actor overhead text and
its color/effect/lifetime state, and posts the message into the chat-line system
when accepted.

### Port consequence

This should eventually reuse one decoded chat message object to update both:

- semantic chat history/event state; and
- semantic actor overhead presentation state.

Huffman/text decompression remains protocol/profile work. Ignore/filter rules and
committed message state should not live in the renderer.

## Animation freeze: `0x40000`

The selected `PlayerFreezeEncoder` writes delay with p2Alt2, duration with p2Alt1,
and cancelSequence with p1Alt2. These are cycle offsets/control, not definition
IDs. The internal Freeze model defines delay and duration in client cycles; either
65535 sentinel clears the existing freeze. cancelSequence affects the action
sequence rather than base animation.

`classDC.method2981` applies the update to each represented player across world
views. `Actor.method3099` creates/updates a freeze window:

```text
startCycle = clientCycle + delay
endCycle = startCycle + duration
```

The reset branch calls `Actor.method3105` and optionally clears the action via
`Actor.method3111`. NPC mask `0x1` feeds the same semantics through different wire
transforms. The existing [freeze research](../client-240/clusters/11-overhead-overrides.md)
and [scheduler](SEQUENCE-SCHEDULER.md) own exact half-open windows, pose/action
snapshot rules and the fact that spot controllers keep running while ordinary
pose/action/turning are frozen. Reuse this one GameState scheduler contract.

The Rust decoder currently skips five bytes. Retain typed delay/duration/control,
then implement windows, cancellation, reset/reuse and replay/fingerprints before
claiming freeze parity. Archive entries are pinned in REVIEW-BASELINE.json.

## The `0x8` reset block

`classDC.method2899` clears a wide set of auxiliary state held by the remote-player
wrapper and every represented `Player`, including:

- the interpolated model-wide override;
- timed color override cycle state and values;
- stored appearance/customization buffer;
- persistent traversal default back to walk;
- facing helper state;
- three player action strings;
- current action sequence via `classCU.method2672(..., -1, 0)`.

The packet branch also consumes one byte later in `method3050`. The selected PlayerReset model identifies this as PLAYER_RESET and documents
the transmitted trailing value as discarded by the client. Port the proven clear
order and world-view propagation through the existing actor owner; the byte does
not justify inventing another authoritative property.

## Current Rust gap summary

The current player decoder is strong at framing and byte alignment, but it has a
large **semantic-retention gap**. In particular, successful decode currently does
not mean player parity for:

- spot animations;
- complete public-chat filtering/channels and overhead acceptance (the decode/state path now exists);
- health bars;
- hitsplats;
- timed hue/saturation/luminance/amount override;
- timed alpha-like model override;
- non-angle facing modes;
- animation-freeze delay/duration/cancellation windows;
- auxiliary reset behavior.

These gaps explain why it would be a mistake to debug every visible difference in
the renderer. Several required render inputs never survive protocol decode today.

## Recommended semantic actor additions

The exact Rust type names should follow crate conventions, but the shared actor
state eventually needs revision-neutral concepts equivalent to:

```text
spot_effects: keyed active slots
hitsplats: bounded active slots with expiry
health_bars: ordered bounded active bars with transition timing
overhead_text: text + color/effect/filter/lifetime
timed_color_override: start/end + hue/saturation/luminance/amount
timed_alpha_override: start/end + source/target/control
facing_source: all modes from MOVEMENT-FACING.md
action_sequence: policy-aware scheduler from SEQUENCE-SCHEDULER.md
```

Player-only or still-unresolved payloads should remain separate until evidence
proves they are shared actor behavior.

## Implementation order

1. Preserve every currently-known dropped mask as a typed protocol value without
   changing existing transform/order logic.
2. Add shared actor containers for spots, hitsplats, bars and overhead state.
3. Add timed color/alpha model override state and scene projection.
4. Extend the existing public-chat/say path feeding chat history and actor
   overhead state; do not reintroduce the historical claim that all chat is discarded.
5. Implement the specified `0x40000` freeze and `0x8` reset lifecycles; do not
   restore the superseded linked-player/two-ID interpretation.
6. Extend deterministic replay/fingerprints to all newly authoritative state.
7. Only then tune visual parity for these effects.

## Focused verification targets

When implementation begins, use small deterministic tests for:

- replacing/removing a spot animation by slot;
- four-health-bar cap, ordering and removal;
- hitsplat slot selection and expiry;
- overhead text reset/lifetime and leading `~` behavior;
- public-chat acceptance/filter paths;
- timed color override activation at start, during range and at end;
- alpha override interpolation, including interrupted/rebased transitions;
- actor reset clearing the proven auxiliary fields;
- packet truncation for every retained block;
- replay/fingerprint differences for every newly authoritative field.

No workspace-wide gates, deob reruns, builds or CI tasks are required for this
research document.