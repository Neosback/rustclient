# Revision 240 port research corpus

Status: active reverse-engineering index for the OpenRune 240.2 port.

This directory is the navigation layer between the pinned revision-240 evidence and
RustClient implementation. It does not replace the decompiled sources, existing
normative specs, or implementation checkpoint documents. Its job is to answer four
questions for every behavior we need to reproduce:

1. Where is the behavior in the revision-240 client evidence?
2. What does the behavior mean after removing obfuscation and RuneLite injection
   artifacts?
3. Which RustClient subsystem owns that semantic behavior?
4. What is the current port and verification status?

## Do not re-run deobfuscation by default

The useful revision-240 deob/decompile output already exists at
`references/rl-suite-240/`.

The canonical analysis source is:

- `references/rl-suite-240/sources-semantic/`: 735 Vineflower-decompiled Java
  classes from the revision-240 RuneLite injected client, with stable generic
  names and 93 API-derived semantic class names.
- `references/rl-suite-240/semantic-1.12.39.tsv`: checksum-bound map from original
  obfuscated class names to the 93 semantic class names. Do not duplicate this
  complete table here.
- `references/rl-suite-240/injected-client-1.12.39-deob-semantic.audit.json`:
  transformation audit for the semantic deob run.
- `references/gamepacks/osrs-240/injected-client-1.12.39.jar`: pinned original
  injected artifact and provenance evidence. Treat it as read-only.

The transformed deob is analysis-only. It retains original names through
`@ObfuscatedName` / `@ObfuscatedSignature`, marks unused members rather than
removing them, names packet fields from the OSRS-240 profile, and materializes a
small number of constant-dynamic string concatenation sites for decompiler
readability. Do not execute it as the game client and do not infer that injected
RuneLite behavior is stock client behavior merely because it appears in this jar.

A new deob run is justified only when the pinned input changes or a specific
transformation defect is demonstrated. In that case, run it in isolated scratch
space and update provenance before replacing any evidence.

## Evidence hierarchy

Use the smallest combination of sources that can establish the claim.

| Evidence | Use | Limit |
| --- | --- | --- |
| `references/rl-suite-240/sources-semantic/` | Primary client control flow and client-side semantics | Input is RuneLite-injected, so injection-only code must be separated from stock behavior |
| original injected 240 jar + audit reports | Bytecode identity, original symbols, transformation and injection checks | Not an untouched vanilla gamepack |
| selected RSProt `osrs-240` sources used by OpenRune | Exact target wire layouts, transforms, masks and packet fields | Server writer/reader describes wire behavior, not every client runtime consequence |
| actual OpenRune 240.2 cache + pinned FileStore research | Definition/archive/model layouts and target data | A generic decoder's existence does not prove the selected cache uses every variant |
| `references/rustosrs/` | Rust realization, scene/model/GPU research and independent implementation clues | Editor/HD behavior is not automatically stock OSRS behavior |
| open RuneLite 240 API/client sources | Plugin API meaning, GPU/plugin ergonomics and observable client surface | Plugin/injection architecture is evidence for desired observability, not RustClient state ownership |
| rsprox | Framing, packet families, transforms and diagnostic cross-checking | Supplied revision modules do not by themselves establish 240 packet IDs |

For protocol claims, prefer agreement between deob control flow and the exact
selected RSProt 240 source. For cache claims, require actual cache evidence where
format ambiguity matters. For rendering claims, separate stock client rules from
RuneLite GPU behavior and from RustOSRS implementation choices.

## Porting rule

Do not translate Java classes one-for-one.

RustClient owns semantic state in `GameState`; revision-specific bytes belong in
`crates/protocol`; cache layouts belong in `crates/cache`; scene/model preparation
belongs in `crates/scene`; GPU presentation belongs in `crates/renderer`; game
interfaces belong in `crates/game-ui`; scripts belong in `crates/client-script`;
platform I/O belongs behind platform adapters.

A deob class can therefore map to several Rust owners, and several deob classes
can collapse into one typed Rust concept. RuneLite injected accessors, callbacks
and hooks should normally become committed semantic events, immutable snapshots,
or validated plugin commands rather than injected mutable fields.

## Corpus files

- `PORT-MATRIX.md`: dependency-ordered domain inventory and current port state.
- `SYMBOL-LEDGER.tsv`: machine-readable source-symbol to Rust-owner ledger. Keep
  rows narrow and factual so agents can search/sort it without parsing prose.
- `ACTOR-RUNTIME.md`: first method-level navigation map for player/NPC movement,
  actor scheduling and action sequences.
- `AGENTS.md`: local rules for extending this research corpus.

Existing deep audits remain canonical for their exact checkpoints. In particular:

- `docs/plan/PLAYER-UPDATING-240.md`
- `docs/plan/NPC-ANIMATION-240.md`
- `docs/plan/GAMEFRAME-240.md`
- `docs/plan/REVISION-240-delivery.md`

This directory links and normalizes those findings. It must not copy large tables
from them and then drift.

## Research record format

Every method-level finding should eventually have the following information,
either as a ledger row or in a domain document:

- domain
- exact source path
- semantic or stable class name
- method/field symbol and original obfuscated symbol when material
- observed preconditions and ordering constraints
- bytes/bits/transforms involved, if wire-facing
- semantic state read or mutated
- cycle/timing semantics
- side effects and callbacks
- RustClient owner
- current status
- confidence/evidence level
- related fixture/test/checkpoint, if one exists
- unresolved questions

Do not rename a generic `classXX.methodNNNN` merely because a guessed name sounds
plausible. Prefer a descriptive ledger note while retaining the stable source
symbol until behavior is established.

## Status vocabulary

Use these values consistently:

- `untriaged`: source exists but ownership/behavior has not been inspected.
- `evidence-found`: relevant source symbols are identified.
- `specified`: semantic behavior and Rust owner are documented well enough to
  implement without rereading broad source areas.
- `partial`: RustClient implements part of the required semantics.
- `implemented`: intended semantic behavior exists in the owning Rust subsystem.
- `verified`: implementation has focused independent evidence/fixtures for the
  claimed behavior.
- `not-needed`: reference behavior is deliberately outside the RustClient target.
- `injection-only`: RuneLite transformation/API behavior, not stock game semantics.
- `blocked`: a missing source/input prevents a sound conclusion.

`implemented` is not equivalent to visual or end-to-end parity. Use `verified`
only for the exact behavior covered by the evidence named in the row/document.

## Confidence vocabulary

- `A`: exact 240 client evidence plus an independent exact target source or data
  source agrees, such as selected RSProt 240 or actual cache bytes.
- `B`: exact 240 client evidence is clear but independent confirmation is absent
  or unnecessary.
- `C`: nearby revision/reference implementation is being used to form a hypothesis.
- `D`: inferred/unknown. Do not implement compatibility-sensitive behavior from a
  D-confidence row without more research.

## Dependency-ordered research sequence

1. Buffer primitives, packet framing, ISAAC, bit access and transformed reads.
2. Login/bootstrap, region base, local player initialization and session lifecycle.
3. Player and NPC list synchronization, movement, update masks and appearance.
4. Shared actor scheduler: path consumption, facing, exact movement, sequence
   restart/priority, spots, hits/headbars and overhead state.
5. JS5/cache containers and every runtime definition needed by those systems.
6. Region/terrain/collision/scene placement and dynamic location state.
7. Model/model-data transforms, lighting, animation skeletons/frames/cached models,
   projectiles and graphics objects.
8. Camera, projection, visibility, draw ordering, materials, alpha/priority and
   minimap/world presentation.
9. Widgets, inventories, menus, input/picking, drag behavior and CS2 execution.
10. Chat/social/clan, audio, preferences, world map and secondary systems.
11. RuneLite injected API/hook surface translated into RustClient plugin events,
    snapshots, queries and commands.
12. Final cross-domain parity audit for timing/order assumptions that only become
    visible when the full loop is connected.

Do not postpone documenting a discovered dependency until its phase. Add the row
immediately and link it from the affected domain.

## Branch research policy

This research branch is intentionally inspection/documentation first. Do not run
workspace-wide build, test, Clippy, release-gate, parity or long CI tasks merely
for Markdown/TSV research edits. Do not claim those gates passed. If a later
checkpoint changes Rust code, use only the narrow validation necessary for that
change unless the branch purpose is explicitly changed.

## Definition of done for this corpus

The research corpus is complete when every row in `PORT-MATRIX.md` is either:

- backed by enough method-level/source-level entries to implement the required
  Rust behavior,
- explicitly `not-needed` with a reason, or
- explicitly `blocked` with the missing evidence named,

and every implemented Rust behavior can be traced back to its evidence without
requiring a future agent to rediscover the broad deob call graph from scratch.
