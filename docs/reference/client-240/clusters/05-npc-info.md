# 05 NPC info

Status: `specified`

Rust owner: `protocol` for the synchronization decoder and typed update blocks; `game-state` for committing NPC lifecycle and actor state.

## Family

The NPC synchronization entry point is `classAS.method661`. The enclosing `classAS` is a mixed-purpose ordinal-like class, so the class itself remains generic rather than being mislabeled as an NPC decoder. `classKA` is also a mixed static utility holder; only the bit-width table whose role is proven by the NPC bitstream is named.

## Old to new

| Stable symbol | `@ObfuscatedName` | Readable name | Tier | Confidence |
| --- | --- | --- | --- | --- |
| `classAS.method661` | `en` | `decodeNpcInfo` | T4 | A |
| `classKA.field3506` | `az` | `npcDefinitionBitWidths` | T4 | B |

## Wire and ordering semantics

`decodeNpcInfo(WorldView, boolean, PacketBuffer, packetLength, ...)` first delegates the prelude to the client packet helper, clears the per-packet removal/update counters and reconciles the previously active NPC list. Existing NPCs use one update bit followed, when set, by a two-bit movement/update form. The method then remains in bit access to discover new NPCs.

For a newly observed NPC the stream includes variable-width fields. The NPC definition id is not fixed width: the decoder reads a two-bit selector and then consumes `npcDefinitionBitWidths[selector]`, where the revision-240 widths are `12, 14, 17, 24` bits. This table is protocol data and should be represented explicitly in the Rust decoder, not hidden in a generic bit helper.

After the new-list bit section, the decoder exits bit access and consumes the extended update masks for every NPC queued for state updates. The mask itself is extended in stages: the initial byte can request an additional byte, then additional high bytes through extension flags. The method's branch order is client behavior and should be preserved when decoding into typed updates.

Observed branches include forced movement, sequence/animation state, temporary model overrides, spot effects, hitsplats, health bars, definition replacement, facing/target state, text and actor visual state. Actor fields already owned by cluster 03 are not renamed here.

## Lifecycle semantics

The decoder distinguishes three operations that should stay separate in Rust:

1. Retain or move an existing NPC from the previous active list.
2. Materialize a newly transmitted NPC with an `NPCComposition` and relative position.
3. Remove stale NPCs after the packet reconciliation finishes.

Do not fold all three into a single mutable hash-map pass if that changes packet ordering or when composition defaults become visible.

## Hot path guidance

NPC synchronization is a per-tick hot path. Decode directly from `PacketBuffer`/Rust `Reader` bit state, use compact index arrays or equivalent fixed-capacity storage for active/update/removal lists, and avoid heap allocation for update-mask dispatch. Definition lookup can cross into `cache`, but the wire decoder itself belongs to `protocol`.

## External referrers and handovers

`client` owns the active/update/removal arrays and counters used by this method and is intentionally not assigned to a wave cluster. `WorldView` is cluster 06. `NPC`, `Actor`, `NPCComposition`, and sequence fields already have seed/cluster-03 ownership. This cluster records those writes as behavior evidence only.

`classKA.field3507` is also consumed through a two-bit width selector in the new-NPC bitstream, but the semantic meaning of that variable-width value is not proven strongly enough by the observed use. It remains a handover/unresolved symbol rather than receiving a guess.

## Injection-only behavior

Any RuneLite callback or injected observation reached from downstream NPC/Actor setters is not part of the stock wire format. The synchronization algorithm and state transitions are stock behavior; callback posting is `injection-only` evidence.

## Client quirks

The update-mask extension layout is cumulative and order-sensitive. Preserve the exact extension checks before interpreting high mask bits. Likewise, new-NPC definition widths are selected by the transmitted two-bit index rather than by a single revision-wide constant.

## Deliberately generic

`classAS` and `classKA` remain generic because both contain substantial unrelated behavior. `classKA.field3507` remains generic because its width-table role is visible but the meaning of the field read with those widths is not sufficiently established. Client-owned NPC scratch arrays/counters are not named in this wave.
