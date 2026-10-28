# Entity assets checkpoint (Phase 1a/1b)

<!-- authority-notice -->
> Historical checkpoint and evidence record. Implementation status, “next” tasks, counts
> and validation below apply to the recorded snapshot. Current work follows
> [PORTING-ROADMAP.md](PORTING-ROADMAP.md) and the [domain matrix](../reference/rs240-port/PORT-MATRIX.md).

Delivered: NPCs and equipped players are meshed from real cache definitions.

## Ownership

- `cache::osrs240::entity_assets` owns on-demand decoding of NPC and worn-item definitions,
  their models, sequences, legacy frames and skeletons, and the varbit layouts used by NPC
  transformations. `EntityLoader` remembers what earlier batches produced so each asset decodes
  once; a bad asset becomes an advisory string, never a batch failure.
- `scene::entity_mesh::EntityCatalog` is grow-only catalog data layered over the immutable spawn
  window. It is not semantic state and can be rebuilt from the cache. `request_missing` derives
  needs from `WorldEntitiesState`; `resolve_npc` applies transformations against committed
  `GameState` variables.
- `apps/native` runs one `entity-assets` worker and merges `LoadEvent::EntityAssets` results.

## Behavior notes

- Entity lighting uses the reference rig: sun (-30,-50,-30), ambient+64, contrast+850
  (players) or `contrast*5+850` (NPCs), distinct from scenery's (-50,-10,-50) rig.
- NPC width/height scale and multi-tile footprint centring are applied.
- NPC and item decoders now cover the full 240 opcode set and reject unknown opcodes. Before this,
  about 5% of item definitions silently desynchronised.
- Scenery (object) morphs were already implemented against committed variables.

## Verification actually run

Correction during branch consolidation: the original `entity_probe` requested NPC
IDs 0–16000 and item IDs 0–32000 in a single request, but the loader capped each
requested family at 4096. The old result therefore did **not** establish full
range coverage. The corrected batched diagnostic and current evidence are in
[the delivery record](REVISION-240-delivery.md). Workspace fmt, check,
strict Clippy, tests and the wasm checks pass. Not run: live login observing NPCs, and the
parity gate.

## Known gaps

- Superseded by the integrated cached-model decode/pose implementation; visual
  skeletal parity remains unverified. Decode/preparation failures still report
  advisories and must not count as successful parity.
- NPC/player animation state (walk/run/turn selection) is not driven yet; entities use their
  idle or server-provided sequence.
- Entity geometry is rebuilt on the main thread every client cycle.
