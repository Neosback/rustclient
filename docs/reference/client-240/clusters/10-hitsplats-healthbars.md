# Cluster 10 — hitsplats and health bars

Status: specified for actor-attached hitsplat insertion/replacement, health-bar definition arbitration, per-bar update retention/expiry, and exact player/NPC mask feeds. Rendering/layout remains downstream work.

## Scope

Owned classes in this shard:

- `classPW` → `HitsplatDefinition`
- `classCO` → `ActorHealthBar`
- `classCV` → `HealthBarUpdate`

Read-only evidence from already-owned classes:

- `Actor`
- `Hitsplat`
- `HealthBarConfig`
- `classDC`
- `classDF`
- `classAS`

`Actor`, `Hitsplat`, and `HealthBarConfig` already belong to other mapping shards, so this cluster does not duplicate their names. Stable symbols remain the citation keys.

## Rust ownership

| Behavior | RustClient owner |
| --- | --- |
| player/NPC mask bits, counts, sentinels and transformed reads | `protocol` |
| committed actor hitsplat slots and health-bar/update lifetime | `game-state` |
| hitsplat/health-bar immutable definition metadata | `cache` |
| choosing screen positions, sprites, text and interpolation visuals | `scene` / `game-ui` |
| RuneLite `HitsplatApplied` and similar callbacks | plugin-event adapter only; not semantic authority |

Do not store authoritative hit or health state in the renderer. The renderer consumes committed actor overhead state plus immutable definitions.

## Readable overlay

### `classPW` → `HitsplatDefinition`

| Stable symbol | Readable name | Evidence |
| --- | --- | --- |
| `classPW.field5446` | `duration` | opcode 9, default 70; added to current cycle + packet delay for `Hitsplat.disappearsOnGameCycle` |
| `classPW.field5458` | `replacementMode` | opcode 12, default -1; drives full-capacity replacement policy |
| `classPW.field5453` | `transforms` | opcodes 17/18 transform target array |
| `classPW.field5460` | `transformVarbit` | `method9378` resolves it through `classEM.method3946` |
| `classPW.field5461` | `transformVarp` | `method9378` indexes `classLB.field4088` when no varbit selector is present |
| `classPW.method9378` | `transform` | chooses an alternate hitsplat definition or returns null |

### `classCO` → `ActorHealthBar`

| Stable symbol | Readable name | Evidence |
| --- | --- | --- |
| `classCO.field1174` | `updates` | bounded ordered deque of `classCV` records |
| `classCO.field1175` | `definition` | `HealthBarConfig` used by actor arbitration and update expiry |
| `classCO.method2549` | `putUpdate` | same-cycle upsert + ordered insertion + four-update cap |
| `classCO.method2552` | `getActiveUpdate` | selects newest eligible update and expires it by definition/update cycle policy |
| `classCO.method2560` | `getDefinition` | direct definition accessor used by injected API health-scale lookup |

### `classCV` → `HealthBarUpdate`

| Stable symbol | Readable name | Evidence |
| --- | --- | --- |
| `classCV.field1298` | `cycle` | ordering/eligibility key in `ActorHealthBar` |
| `classCV.field1295` | `cycleOffset` | participates in exact expiry expression |
| `classCV.field1297` | `healthRatio` | returned by `method2685`; `Actor.getHealthRatio()` returns this value from the active update |
| `classCV.method2676` | `set` | overwrites the four update values |
| `classCV.method2685` | `getHealthRatio` | returns `field1297` |

The remaining `classCV.field1296` is intentionally left generic in the mapping. It is part of the four-value update payload, but this slice does not need to guess a more specific display/interpolation name.

## Hitsplat insertion and replacement

The clearest stock-shaped routine is `Actor.method3135`; `Actor.method3136`/`method3137` duplicate the same policy. The network-facing `Actor.method3138` contains the same state mutation plus injected callback handoff.

Inputs are semantically:

```text
hitsplat definition id
amount
current cycle
packet delay
slot limit
```

The slot limit is supplied by the update payload in this revision. It is not safe to hard-code four merely because `Actor.field1432` is constructed with initial `ArrayList(4)` capacity.

### Definition resolution

For non-negative definition id and positive slot limit:

1. load `HitsplatDefinition`;
2. if `transforms` exists, call `transform()`;
3. if the transformed definition is null, stop without installing a hitsplat;
4. read `replacementMode` and `duration` from the resolved definition.

### Active/full determination

The actor's `field1432` list contains reusable `Hitsplat` records.

A record is still active when:

```text
hitsplat.disappearsOnGameCycle > currentCycle
```

The routine considers the collection full only when the list has at least `slotLimit` entries and every considered slot is still active.

While scanning active entries, it carries a rotating candidate cursor. When the list is not full, it scans up to `slotLimit` positions for either:

- a not-yet-created position, in which case a new `Hitsplat` record is appended; or
- an expired record whose disappearance cycle is `<= currentCycle`.

For slot limits greater than four, the source resets this scan cursor to zero before the free/expired search. Preserve that source behavior rather than imposing a four-slot model.

### Full-list replacement modes

When every available slot is active:

#### `replacementMode == -1`

Reject the incoming hitsplat.

#### `replacementMode == 0`

Choose the active hitsplat with the smallest `disappearsOnGameCycle` and overwrite it.

This is earliest-expiring replacement.

#### `replacementMode == 1`

Choose the active hitsplat with the smallest amount.

Then apply an additional guard:

```text
if smallest_existing_amount >= incoming_amount:
    reject incoming hitsplat
```

Only a strictly larger incoming amount can replace the smallest existing amount under mode 1.

Other replacement-mode values do not establish another replacement branch in the inspected routine; do not invent one.

### Installed lifetime

When a target slot is selected:

```text
disappearsOnGameCycle =
    currentCycle
    + packetDelay
    + resolvedDefinition.duration
```

The stored visible state in `Hitsplat` is:

- definition/type id;
- amount;
- absolute disappearance cycle.

This means expiry is authoritative cycle state, not a renderer-local timer.

## RuneLite hitsplat injection separation

`Actor.method3138` starts by invoking `Actor.method3287`, which clears the injected static `Hitsplat.field1638` handoff. The stock-shaped insertion/replacement policy then runs. On each terminal path it invokes `Actor.method3290`, which constructs/posts RuneLite's `HitsplatApplied` event using the committed or synthesized hitsplat.

Port requirement:

```text
protocol update
  -> GameState hitsplat mutation
  -> committed event snapshot
  -> optional plugin event
```

Do not make event construction part of the mutation algorithm and do not use a callback object as the authoritative hitsplat record.

## Player hitsplat wire path

`classDF.method3048` mask bit `1048576` carries a hitsplat batch.

It reads:

```text
count = PacketBuffer.method13095()
repeat count:
    definition id = method13150()
    amount        = method13150()
    delay         = method13150()
    slot limit    = method13150()
```

`classDC.method2942` fans the values to every represented player and inserts the current client cycle before calling `Actor.method3138`.

The four `method13150` values are all the same smart-width primitive in this source path. Protocol adapters own the primitive implementation; GameState should receive normalized integers.

## NPC hitsplat wire path

`classAS.method661` mask bit `131072` carries the equivalent NPC hitsplat batch.

It reads the batch count with `PacketBuffer.method13218`, then four `method13150` values per entry, and directly calls:

```text
Actor.method3138(
    definition_id,
    amount,
    clientCycle,
    delay,
    slot_limit,
    ...
)
```

Player and NPC framing therefore differ at the mask/count layer but converge on the same actor replacement/lifetime policy.

## Health-bar definition arbitration

The actor stores `classCO` health bars in `Actor.field1440`.

`Actor.method3143` is the clean stock-shaped add/update routine. `Actor.method3144` performs the same health-bar mutation but first invokes injected `Actor.method3277` death/event bookkeeping.

A health-bar definition is loaded from `HealthBarConfig` and participates in three separate policies.

### Group identity — `HealthBarConfig.field5482`

If an existing `ActorHealthBar.definition.field5482` equals the incoming definition's `field5482`, the actor does **not** create a second bar. It updates the existing bar's timeline via `putUpdate` and returns.

This is a definition-authored grouping key, not simply Java object identity.

### Ordering — `HealthBarConfig.field5479`

While scanning the actor's existing bars, the source retains an insertion anchor according to the relative `field5479` values. A new bar is inserted at that definition-authored ordered position.

The mapping shard deliberately leaves this seed-owned `HealthBarConfig` field under its stable symbol rather than inventing an original-domain name.

### Capacity/eviction — `HealthBarConfig.field5480`

An actor keeps at most **four distinct health bars**.

During the scan, the source finds an eviction candidate only among existing bars whose `field5480` value is greater than the incoming definition's value. As it scans it retains the greatest qualifying existing value.

At four bars:

- if no such candidate exists, reject the incoming new health bar;
- otherwise insert the new bar and remove that selected existing bar.

Thus health-bar capacity is definition-prioritized; it is not simple FIFO replacement.

## Per-bar update timeline

Each `ActorHealthBar` owns `updates`, a bounded ordered deque of `HealthBarUpdate` records.

`ActorHealthBar.putUpdate(cycle, value1, healthRatio, cycleOffset, ...)` implements the exact retention policy.

### Same cycle

If an existing update has exactly the same `cycle`, overwrite its four fields in place and return.

### Ordered insertion

Otherwise find the last traversed update with:

```text
existing.cycle <= incoming.cycle
```

and insert the incoming update at the corresponding ordered position.

### Four-update cap

At most four updates are retained per health bar.

When a newer/in-order update is inserted while four updates already exist, the oldest deque entry is removed. If the incoming update belongs before every retained update and the deque already contains four entries, the incoming older update is dropped instead of displacing current history.

This is separate from the actor-level four-health-bar cap:

```text
actor
  up to 4 health-bar definitions
      each bar
        up to 4 timestamped updates
```

Do not flatten these two limits into one collection.

## Active health-bar update and expiry

`ActorHealthBar.getActiveUpdate(currentCycle)` starts from the earliest retained update. If its `cycle` is already eligible, it walks forward through subsequent updates while their cycle is also `<= currentCycle`, removing superseded older entries as it goes.

The newest eligible update remains active only while:

```text
HealthBarConfig.field5478
+ update.cycle
+ update.cycleOffset
> currentCycle
```

At equality or after the boundary, the update is removed and the method returns null.

This strict `>` is part of cycle parity.

`Actor.getHealthRatio()` takes the first actor health bar, resolves its active update at the current game cycle, and returns `HealthBarUpdate.healthRatio`. `Actor.getHealthScale()` returns `HealthBarConfig.field5483` through `ActorHealthBar.getDefinition().method9420()`.

Those injected getters confirm observable data but do not own health-bar lifetime.

## Health-bar removal

`Actor.method3145(definitionId, ...)` loads the `HealthBarConfig` and removes the actor health-bar instance whose definition object is that resolved cached definition.

Wire removal is represented by the sentinel `32767` in the second per-entry smart field in the inspected player/NPC health-bar masks.

## Player health-bar wire path

`classDF.method3048` mask bit `131072` carries player health-bar updates.

Per entry:

```text
id = method13150()
value = method13150()

if value == 32767:
    classDC.method2950(id)       // remove bar
else:
    value2 = method13150()
    byteA  = method13095()
    byteB  = value > 0 ? method13224() : byteA
    classDC.method2947(id, value, value2, byteA, byteB)
```

`classDC.method2947` inserts the current client cycle and forwards to `Actor.method3144`.

The exact higher-level display meaning of every payload integer is deliberately not overnamed here. What is established is how they feed `HealthBarUpdate.cycle`, `cycleOffset`, `healthRatio`, and the remaining unnamed update value through `ActorHealthBar.putUpdate`.

## NPC health-bar wire path

`classAS.method661` mask bit `16777216` carries NPC health-bar updates.

Its normalized control flow is the same:

```text
count = method13218()
repeat count:
    id = method13150()
    value = method13150()
    if value == 32767:
        Actor.method3145(id)
    else:
        value2 = method13150()
        byteA = method13225()
        byteB = value > 0 ? method13224() : byteA
        Actor.method3144(id, clientCycle, value, value2, byteA, byteB)
```

The player and NPC transformed byte methods are not interchangeable. Revision-specific decoding stays in protocol adapters; the shared actor semantic operation begins only after normalization.

## Cache-definition notes

### Hitsplat definition

`HitsplatDefinition` constructor/default behavior relevant to this cluster:

- opcode 9 → `duration`, default `70`;
- opcode 12 → `replacementMode`, default `-1`;
- opcodes 17/18 → varbit/varp-driven transform targets with `65535 -> -1` sentinels.

Presentation sprites/fonts/text/color remain cache/UI projection concerns and are not renamed merely to expand this cluster.

### Health-bar definition

Exact `HealthBarConfig` fields consumed by actor runtime:

- `field5482`: grouping key / constructor definition id;
- `field5479`: actor-bar insertion ordering policy;
- `field5480`: actor-bar eviction policy;
- `field5478`: update lifetime component;
- `field5483`: health scale exposed by `Actor.getHealthScale()`; constructor default 30.

`HealthBarConfig` is seed/API-owned, so this cluster records their behavior without duplicating mapping ownership.

## Compatibility requirements for RustClient

The semantic owner needs enough state to reproduce at least:

```text
ActorOverheadState
  hitsplats:
    variable slot-limit insertion/replacement
    definition id
    amount
    absolute disappearance cycle

  health_bars:
    <= 4 definition-backed bars
    definition ordering/eviction policy
    each bar <= 4 ordered updates
    cycle eligibility
    strict expiry boundary
```

Immutable policy should be resolved from cache definitions before mutation. Do not put definition-cache objects themselves into authoritative GameState.

Recommended boundary:

```text
protocol player/NPC mask
  -> normalized HitsplatUpdate / HealthBarUpdate command
  -> application resolves compact immutable definition policy
  -> GameState applies capacity/replacement/timeline rules
  -> scene/game-ui projects visible overheads
  -> plugin adapter emits committed snapshots/events
```

## Quirks to preserve

1. Hitsplat slot count is packet-supplied in this source path; the Java list's initial capacity of four is not the semantic cap.
2. Hitsplat replacement mode 1 requires the incoming amount to be **strictly greater** than the smallest existing amount.
3. Hitsplat lifetime is `currentCycle + packetDelay + definition.duration`.
4. Health-bar definition capacity is four, independent from the four-update history cap inside each bar.
5. A full actor health-bar set can reject a new definition entirely depending on `HealthBarConfig.field5480`.
6. A full per-bar update deque can reject an older incoming update rather than evict newer history.
7. Health-bar expiry uses strict `>`; equality is expired.
8. Player/NPC mask/transformed-read details differ even though their normalized actor mutations converge.

## Injection-only / not semantic authority

- `Actor.method3287`: clears RuneLite's static hitsplat event handoff.
- `Actor.method3290`: creates/posts `HitsplatApplied` after the stock-shaped mutation path.
- `Actor.method3277`: RuneLite actor-death/event bookkeeping invoked by the network-facing health-bar wrapper.
- API getters such as `Actor.getHealthRatio()` / `getHealthScale()` are useful evidence but not the storage owner.

RustClient may expose equivalent plugin events later, but only after GameState mutation has committed.

## Remaining work after this cluster

The next actor-runtime slice should move to overhead text plus temporary tint/freeze/customization masks. Hitsplat/health-bar screen layout, sprite composition and interpolation rendering can be revisited under scene/game-ui parity without reopening the authoritative capacity/lifetime rules documented here.

## Verification

Source/manual evidence review only. No `verify.sh`, mapping merge, remap/decompile pass, tests, Clippy, build, compilation or CI was run for this cluster.
