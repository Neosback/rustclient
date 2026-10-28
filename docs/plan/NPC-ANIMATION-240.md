# NPC and object animation audit — 2026-10-23

<!-- authority-notice -->
> Historical checkpoint and evidence record. Implementation status, “next” tasks, counts
> and validation below apply to the recorded snapshot. Current work follows
> [PORTING-ROADMAP.md](PORTING-ROADMAP.md) and the [domain matrix](../reference/rs240-port/PORT-MATRIX.md).

Historical checkpoint: later actor ownership, path consumption, forced movement and
sequence assignment were superseded by the merged
[actor runtime corpus](../reference/rs240-port/ACTOR-RUNTIME.md),
[sequence scheduler](../reference/rs240-port/SEQUENCE-SCHEDULER.md) and
[facing split](../reference/rs240-port/FACING-TURNING.md). Read those current owner
records before treating the remaining-work table below as a present implementation
inventory. This record retains its original evidence and validation limits.

Status: implemented corrections; complete actor/render parity remains open.

## Evidence and corrections

The selected source is RSProt 240 desktop/model 1.0.0-ALPHA-20260825 and the
actual rl-suite-240 deob, particularly classAS, NPC, NPCComposition, DynamicObject,
ObjectComposition, classJR and classQR. Their hashes are pinned in the source
manifest; encoder entry hashes and synthetic codec vectors are documented in
protocol/tests/fixtures/PROVENANCE.md. Reference/server trees are read-only.

- NPC list/mask ordering, sequence transforms and stock transformation decoding
  were checked against the selected writer. The previous framing remains valid;
  known cosmetic blocks are still consumed without full semantic/effect support.
- Crawl speed and both ordered run steps survive decoding, semantic validation,
  fingerprints/replay and motion consumption. Closed runs still move. Facing uses
  the final authored direction. A path serial distinguishes equal-endpoint requests.
- NPC forced movement is no longer discarded. Offset transforms, post-movement
  anchoring, absolute client-cycle timing, interval validation and trajectory
  projection now use the existing actor trajectory owner/helpers.
- NPC definitions retain forward/back/left/right run and crawl sequences, including
  nullable IDs. The dependency loader loads these sequences; NPC projection uses
  the authored forward speed sequence with walk/idle fallback. Directional turning
  and side/back selection still depend on unfinished actor rotation scheduling.
- NPC morph resolution follows nested transforms, including absent/default targets,
  and bounds cyclic chains without allocating a visited set on each actor draw.
- Specific location packets now decode their zone/local coordinates, subtract the
  profile-owned build-area anchor offset, apply the correct sub byte transform,
  preserve placement and treat 65535 as a stop. Off-map specifics are ignored as
  in the deob, instead of disconnecting the session.
- Global LOC_ANIM and enclosed zone animations are decoded with zone-plane context.
  Batched payloads validate before emission; full-zone resets clear matching
  location overrides. Non-animation enclosed payloads are bounds-checked/consumed;
  their object/item/projectile semantics are not claimed here.
- Server triggers start at the current authoritative cycle instead of cycle zero.
  Shape 22 maps to FloorDecoration; 12–21 remain GameObject.
- Server overrides on static definitions now enter the dynamic stream only. Shared
  immutable animation snapshots supply dependencies outside the original map
  closure, including cached-model skeletal animations and their vertex weights.
  Server starts use random word zero, selecting frame zero. Pending asset arrivals
  retain base geometry; asset failures are diagnostic, never parity acceptance.

## Validation

Pending final workspace gates. Focused families cover packet transforms and every
truncated NPC exact-mask prefix; crawl/bent/closed paths; trajectory normalization,
atomic rejection, reset/replay/index reuse; cache speed sequences; location start,
placement, stop and plane-bounded reset; zone context and malformed enclosed tails.

Fingerprint schema 16 exact anchors: inactive `13106773751953311645`, revision
240 tick 5 `10190475705488636671`, revision 240.2 tick 5 `6392671347810390634`,
reference commit stream `10395866910311550467`, empty stream
`10566103509052713974`, authentication/placement `12191201622066736735`,
authentication/placement stream `10866463569101878984`.

## Remaining compatibility work

ActorSequenceSlot still ignores same-ID reassignments. The deob uses authored
restart mode and priority arbitration; those policies need to enter the semantic
gateway and scheduler, not be guessed by the mesh builder. Authoritative finite
completion/loop state, action/locomotion blending, target following, turn rates,
movement blocking/catch-up and legacy-action frame gating during exact movement
remain incomplete. Hits/headbars, overhead text, spot effects, tint/transparency,
freeze and customization masks still lack full semantics/rendering. Full visual
acceptance against reference captures, including animated object normals/UVs,
alpha and depth/priority interactions, has not been performed by this audit.
