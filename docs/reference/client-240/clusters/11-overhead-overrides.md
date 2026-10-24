# Cluster 11 — overhead text and actor overrides

Status: specified for actor overhead-text lifetime, temporary HSL color overrides, transparency transitions, and the packet-to-runtime boundary for animation-freeze windows and NPC model/chathead overrides. Full player appearance/model-cache invalidation remains a separate player-appearance obligation.

## Scope

Owned classes in this shard:

- `classFL` → `ModelColorOverride`
- `classAAV` → `TransparencyTransition`

Read-only evidence from already-owned classes:

- `Actor`
- `Player`
- `NPC`
- `Model`
- `classDC`
- `classDF`
- `classAS`
- `classQH`
- `classKB`
- `NpcOverrides`
- `PlayerComposition`
- `ColorTextureOverride`
- top-level `client`

`Actor`, `Player`, `NPC`, `Model`, `classDC`, `classDF`, `classAS`, `classQH`, `classKB`, `NpcOverrides`, `PlayerComposition`, and `ColorTextureOverride` already belong to other mapping shards or the seed API map. This cluster does not duplicate their names.

Focused exact-240 referrer inspection for the newly owned helpers covered `Actor`, `classDC`, `Player`, `NPC`, and `Model`. GitHub branch code search did not index these branch-only generic symbols reliably, so this is not claimed as a whole-archive referrer census. Re-open only if a later source trace reaches another writer/consumer.

## Rust ownership

| Behavior | RustClient owner |
| --- | --- |
| player/NPC mask bits, transformed reads, sentinels and packed chat fields | `protocol` |
| committed overhead text/cycle, color-override window/payload, transparency transition, freeze controls and NPC override selection | `game-state` |
| immutable appearance/NPC/model definition inputs | `cache` |
| applying committed color/transparency/custom model state while constructing the actor model | `scene` |
| drawing chat bubbles / overhead presentation | `game-ui` / renderer projection |
| RuneLite `OverheadTextChanged` callback | plugin-event adapter after committed mutation; not semantic authority |

These states are independent. Do not collapse color override, transparency transition, animation freeze, and model customization into one generic actor-effect object merely because they arrive through update masks.

## Readable overlay

### `classFL` → `ModelColorOverride`

| Stable symbol | Readable name | Evidence |
| --- | --- | --- |
| `classFL.field2298` | `overrideHue` | `Model.getOverrideHue()` reads this byte from `Model.field2420` |
| `classFL.field2299` | `overrideSaturation` | `Model.getOverrideSaturation()` reads this byte |
| `classFL.field2300` | `overrideLuminance` | `Model.getOverrideLuminance()` reads this byte |
| `classFL.field2301` | `overrideAmount` | `Model.getOverrideAmount()` reads this byte; `Actor.method3190` requires it to be positive |
| `classFL.method4843` | `copyFrom` | copies all four bytes |
| `classFL.method4850` | `set` | writes hue/saturation/luminance/amount |
| `classFL.method4856` | `clear` | resets H/S/L to `-1` and amount to `0` |
| `classFL.method4857` | `isActive` | returns `overrideAmount > 0` |

### `classAAV` → `TransparencyTransition`

| Stable symbol | Readable name | Evidence |
| --- | --- | --- |
| `classAAV.field105` | `startCycle` | second `method271` argument; interpolation begins here |
| `classAAV.field104` | `endCycle` | third `method271` argument; final value is committed at/after this cycle |
| `classAAV.field107` | `startTransparency` | interpolation start byte unless the current value is deliberately carried forward |
| `classAAV.field106` | `endTransparency` | transition target and persistent post-end sample |
| `classAAV.field108` | `currentTransparency` | mutable sampled value retained before start and updated during/after interpolation |
| `classAAV.method271` | `setTransition` | configures cycle bounds and transition endpoints |
| `classAAV.method272` | `sample` | samples the transition for a client cycle |

The boolean passed to `setTransition` is intentionally left unnamed. Its exact branch behavior is known, but this slice does not assign a broader domain meaning that the source does not establish.

## Overhead text is authoritative actor state

The seed API already names:

- `Actor.field1459` → `overheadText`
- `Actor.field1457` → `overheadCycle`

The important compatibility rule is that the text and countdown live on the actor and are advanced by the client-cycle loop. Chat-bubble drawing is a projection of that committed state.

### NPC simple overhead text

In `classAS.method661`, NPC mask bit `16` does:

```text
overheadText = PacketBuffer string
overheadCycle = 100
```

The same mutation invokes `Actor.method3279`, which is RuneLite's injected `OverheadTextChanged` callback surface. That callback is not the state owner.

### Player simple overhead text

In `classDF` extended player data, mask bit `1` reads a string and delegates to `classDC.method2940`.

For each represented player, the clean source path writes:

```text
overheadText = decoded text
overheadCycle = 150
packed color/effect state = defaults
packet/chat flags = defaults
```

If the first character is `~`, the prefix is removed before storage. That form is also emitted into the chatbox. A non-`~` simple overhead message is emitted into the chatbox only for the local player.

Preserve this distinction; the `~` prefix is control syntax, not visible actor text.

### Player public-chat overhead state

Player mask bit `4096` is the richer public-chat path. After packet/account/filter decoding, `classDC.method2954`/the equivalent checked variant writes the represented player's actor overhead state:

```text
overheadText  = decoded text.trim()
overheadCycle = 150
field1458     = packed value high byte
field1479     = packed value low byte
field1436     = decoded optional metadata bytes
field1500     = packet boolean
field1456     = client-side suppression/filter result
```

The high/low bytes clearly feed overhead presentation policy, but those `Actor` members are seed-owned and are deliberately left as stable symbols here rather than renamed from inference. A future chat/UI cluster can name them once every consumer is traced.

The public-chat path also creates a chatbox message with account-type/autochat-dependent message type. Do not make the chatbox history object the source of the actor's overhead bubble.

## Exact overhead expiry

The top-level client cycle (`client.method2173`) updates actors first and then performs a dedicated overhead countdown pass across every active `WorldView`:

```text
for each tracked Player:
    if overheadCycle > 0:
        overheadCycle -= 1
        if overheadCycle == 0:
            overheadText = null

for each NPC:
    same rule
```

Therefore:

- lifetime is client-cycle based;
- player simple/public overhead starts at `150` cycles;
- NPC simple overhead starts at `100` cycles;
- the text is cleared exactly when the decremented counter reaches zero;
- this countdown occurs after the actor movement/turn/animation cycle in the inspected top-level loop.

Do not implement overhead expiry as wall-clock time or render-frame time.

## RuneLite overhead callback separation

`Actor.method3279` reads `overheadText` and, when non-null, posts RuneLite's `OverheadTextChanged` event.

Port ordering should be:

```text
protocol decode
    -> GameState overhead mutation
    -> committed actor snapshot/delta
    -> optional plugin event
```

The event is observability, not the mutation mechanism.

## Temporary model color override

Actor retains:

- a `classFL` payload in `Actor.field1433`;
- a start cycle in `Actor.field1435`;
- an end cycle in `Actor.field1430`.

`Actor.method3196` stores those cycle bounds and copies the color payload.

`Actor.method3190` considers the override active only when:

```text
overrideAmount > 0
&& currentCycle >= startCycle
&& currentCycle < endCycle
```

The cycle interval is therefore exactly half-open:

```text
[startCycle, endCycle)
```

### Player wire feed

Player mask bit `1024` reads:

```text
startCycle = clientCycle + transformed offset
endCycle   = clientCycle + transformed offset
four signed/byte values
```

`classDC.method2971` retains this state for the tracked player slot and fans it out to every represented `Player`, which receives it through `Actor.method3196`.

### NPC wire feed

NPC mask bit `512` reads the same semantic shape with its own exact transformed primitives:

```text
startCycle = clientCycle + offset
endCycle   = clientCycle + offset
hue/saturation/luminance/amount bytes
```

It then calls `Actor.method3192`, which writes the same actor cycle bounds and four-byte `classFL` payload.

Protocol must preserve the player/NPC primitive differences. GameState should receive one normalized semantic color-override operation.

## Color override model application order

Both `Player.vmethod93` and `NPC.vmethod93` first build the actor's animation-composed model.

After that:

```text
if Actor.method3190(currentCycle):
    Model.method5232(actor ModelColorOverride, original face count)
else:
    clear/default model override state
```

`Model.method5232` copies the `classFL` payload into model projection state and records the face-count boundary used by the model override path.

This establishes the ownership split:

```text
GameState: cycle window + semantic H/S/L/amount
Scene:     apply active payload to constructed model
Renderer:  consume prepared model state
```

The renderer must not independently decide when the override starts or expires.

## Transparency transition

Actor's `field1475` is a nullable `TransparencyTransition`.

Player and NPC model construction sample it after the temporary HSL color override step:

```text
Model.method5230(
    transition != null
        ? transition.sample(clientCycle)
        : 0
)
```

The seed API already names `Model.field2423` as `transparency`, and `Model.method5230` writes that field.

This is distinct from `ModelColorOverride`. One changes model HSL override parameters; the other writes the model transparency byte.

### Exact sampling rule

`TransparencyTransition.sample(cycle)` behaves as follows.

Before start:

```text
cycle < startCycle
    -> return currentTransparency unchanged
```

During the interval:

```text
startCycle <= cycle < endCycle
    progress = (cycle - startCycle) / (endCycle - startCycle)
    currentTransparency = lerp(
        startTransparency & 255,
        endTransparency & 255,
        progress
    )
```

At and after the end:

```text
cycle >= endCycle
    currentTransparency = endTransparency
```

The final value is therefore persistent in the transition object; reaching `endCycle` does not by itself clear the object.

### Scheduling quirk

`setTransition(currentCycle, startCycle, endCycle, packetStart, packetEnd, control)` always stores the new start/end cycles and `packetEnd` target.

For the start value:

```text
if !control && startCycle >= currentCycle:
    startTransparency = currentTransparency
else:
    startTransparency = packetStart
```

This allows a future transition to continue from the actor's currently sampled transparency rather than necessarily jumping to the packet's start byte. Preserve the branch exactly; do not guess a domain name for `control` until its protocol meaning is independently established.

### Player wire feed

Player mask bit `65536` reads:

```text
startCycle = clientCycle + transformed offset
endCycle   = clientCycle + transformed offset
start byte
end byte
boolean control
```

`classDC.method2973`/`method2976` retains one transition for the tracked player slot and applies the same transition to every represented `Player`.

### NPC wire feed

NPC mask bit `32768` reads its own transformed form of the same values, lazily creates `Actor.field1475`, then calls `setTransition(clientCycle, startCycle, endCycle, start, end, control)`.

Again, wire transforms remain protocol-owned while transition sampling belongs to committed actor/model semantics.

## Model transparency consequence

`Model.method5214` uses the sampled transparency byte while resolving face alpha. For positive model transparency, a face alpha below `253` is moved toward `253` proportionally to the model transparency value.

This proves `TransparencyTransition` affects model projection rather than animation scheduling. Do not use it as an actor visibility/despawn timer.

## Animation freeze/update-mask boundary

Freeze runtime is already canonically owned and named by cluster 07:

- `classQH` → `AnimationFreezeWindow`
- start/end cycles
- reset-on-start policy
- `classKB.method6491` scheduler behavior
- `Actor.field1466` / `animationsFrozen` from the actor-motion shard

This cluster only closes how player/NPC update masks feed that existing scheduler state.

### Player freeze control

Player mask bit `262144` reads two unsigned-short-like values with `65535 -> -1`, plus a boolean.

`classDC.method2981` resolves the represented player across world views and applies:

```text
if first != -1 && second != -1:
    Actor.method3099(first, second, boolean)
else:
    Actor.method3105()     // clear freeze windows/snapshots
    if boolean:
        Actor.method3111() // clear action sequence
```

`Actor.method3099` converts the two packet values to absolute cycles:

```text
startCycle = clientCycle + first
endCycle   = startCycle + second
```

It updates a matching current freeze window or appends a new `AnimationFreezeWindow`.

### NPC freeze control

NPC mask bit `1` performs the same semantic operation with NPC-specific buffer primitives and the same `65535 -> -1` normalization.

Therefore player and NPC packet forms normalize into the already-documented cluster-07 freeze scheduler contract.

### Freeze is not a rendering override

While a freeze window is active, cluster 07 established that:

- ordinary pose advancement is suppressed;
- ordinary action advancement/delay progression is suppressed by the scheduler gate;
- ordinary smooth turning is suppressed by `animationsFrozen`;
- actor spot animations still run because their loop sits outside that freeze gate.

Color override and transparency sampling are not controlled by this freeze flag. Keep these concepts independent.

## NPC customization: chathead and world-model override are separate

The seed API already distinguishes:

- `NPC.field1580` → `chatheadOverrides`
- `NPC.field1581` → `modelOverrides`

Do not merge them.

### Chathead override family

NPC mask bit `524288` decodes a flags byte.

When its clear bit is set, `NPC.method3501` clears `chatheadOverrides`.

Otherwise it can decode optional:

- model IDs;
- recolor replacements;
- retexture replacements;
- a boolean control;

and installs a new `NpcOverrides` through `NPC.method3496`.

### World-model override family

NPC masks `33554432` and `1048576` are two exact wire forms that install/clear `modelOverrides`.

Both use a flags byte whose bit `1` clears through `NPC.method3503`. Non-clear forms construct `NpcOverrides` and call `NPC.method3500`; the richer form can additionally carry the fields that drive the alternate `PlayerComposition` branch.

`NPC.method3500` stores the override. When `NpcOverrides.field5371` is set, it also prepares `NPC.field1578` as a `PlayerComposition` using override-provided model/color/customization inputs. The exact final semantic names of `field5371`, `field5370`, and `field5377` are deliberately deferred because `NpcOverrides` is seed-owned and this slice does not establish sufficiently narrow domain names for them.

### NPC model selection order

When `modelOverrides` exists, NPC model assembly branches:

```text
if NpcOverrides.field5375 / API useLocalPlayer():
    build from local PlayerComposition
else if NpcOverrides.field5371:
    build from NPC's prepared PlayerComposition
else:
    build NPCComposition model with NpcOverrides
```

Without `modelOverrides`, normal `NPCComposition` model construction is used.

This state belongs in authoritative NPC semantic/model-selection state. Scene consumes it to build the model; renderer does not own the selection.

## Player appearance customization handoff

`Player.method2615` appearance decode also carries per-equipment `ColorTextureOverride[]` selected by a bit mask, then supplies those overrides to `PlayerComposition.method6719`.

That is persistent appearance/model-cache input and is **not** the same mechanism as:

- temporary `ModelColorOverride`;
- `TransparencyTransition`;
- animation freeze windows.

Full player appearance customization and cache invalidation should remain the next dedicated player-appearance/model-invalidation audit rather than being partially renamed here.

## Ordering established by this slice

For the relevant visible actor state:

1. packet decoder reads revision-specific fields;
2. normalized semantic state is committed to the actor / tracked-player slot;
3. actor movement, turning and sequence scheduling run on client cycles;
4. model construction composes active animation state;
5. temporary HSL override is applied if its half-open cycle window is active;
6. transparency transition is sampled and written to the model;
7. NPC-specific model override selection/custom composition participates in NPC model construction;
8. renderer/game UI projects the prepared model and overhead text;
9. top-level client cycle decrements overhead text counters and clears text on zero;
10. injected callbacks are observability surfaces around committed state, not the authority.

Do not infer that step 9 clears tint/transparency/customization. Those have their own explicit state/lifetime rules.

## Client quirks to preserve

- NPC simple overhead text starts at `100` cycles; player simple/public overhead text starts at `150`.
- `~` is stripped from player simple overhead text and forces chatbox publication.
- overhead text clears only when its decremented counter reaches zero.
- temporary HSL color window is `[startCycle, endCycle)`.
- color override requires `overrideAmount > 0` in addition to the cycle window.
- transparency sampling is persistent after `endCycle`; the transition object is not automatically removed there.
- a future transparency transition may start from the actor's current sampled value instead of the packet start byte.
- freeze suppresses specific actor scheduling/turning paths but does not globally freeze spot animations or model override sampling.
- NPC chathead overrides and world-model overrides are distinct slots with distinct packet masks.

## Deliberately unresolved / left generic

- `Actor.field1458`, `field1479`, `field1436`, `field1500`, and `field1456` remain seed-owned stable symbols until the full overhead/chat rendering consumers justify final names.
- the transparency transition control boolean is behaviorally specified but not semantically named.
- `NpcOverrides.field5371`, `field5370`, and `field5377` remain generic; their model-selection behavior is documented without guessing names.
- full `PlayerComposition` invalidation/cache-key consequences of player appearance `ColorTextureOverride[]` remain open.
- NPC's separate render-time `field1584` color/highlight path is not conflated with packet-authored temporary actor color state in this cluster.

## Rust handoff

A sound semantic shape is closer to:

```text
ActorOverheadState
  overhead_text: Option<Text>
  overhead_cycles_remaining
  overhead_presentation metadata (once fully named)

TimedModelColorOverride
  start_cycle
  end_cycle
  hue
  saturation
  luminance
  amount

TransparencyTransition
  start_cycle
  end_cycle
  start_transparency
  end_transparency
  current_transparency
  exact control-branch semantics

AnimationFreezeWindow[]
  // canonical behavior from cluster 07

NpcModelSelection
  normal definition
  optional chathead override
  optional world-model override
```

These may live in smaller Rust structs internally, but GameState remains the single semantic authority. Scene/model code receives committed snapshots and must not maintain independent lifetime counters.

Next actor-runtime priority after this slice: finish player appearance/customization model invalidation, then graphics objects/projectiles or move to the next P0 region/scene dependency according to the port matrix.
