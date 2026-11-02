# SPEC-0024: Incremental location resources

**Status:** Accepted for the user-approved incremental-location milestone, 2026-11-01;
implementation and acceptance remain separate.
**Owners:** GameState (commits/journal), scene (dependency extraction), application
(publication), renderer (resident resources), native/web scheduling adapters.

## Scope and invariants

Ordinary normal-map location add/remove/replace and existing animations must cause
work proportional to their dependencies. GameState remains authoritative. Internal
zone/resource metadata is disposable and does not amend the external plugin ABI,
packet formats or semantic replay/fingerprints. Preserve every ordered commit and
source-required callback; coalesce only derived preparation after cycle commits.

Stock 240 classDS.method3605, classMI.method7635 and classFK.method4839 define
location lifetime/readiness, replacement/collision and renderable construction.
Pinned RuneLite GpuPlugin.invalidateZone/onPostClientTick/rebuild supply the 8×8
resource mechanism. Source anchors are indexed in the existing update audit.
Initial-bake shadows and other load-only effects must not be repeated for live updates.

## Resource and publication contract

- Use typed owning-zone and resource-generation identities, current immutable inputs
  and an independent projection-journal cursor. Missing journal history explicitly
  invalidates the affected domain; it never silently advances stale presentation.
- Account for old/new footprints, normals/hidden faces, decorations, bridge/storage
  planes, roofs, picking, collision and minimap dependencies. Cross-zone models draw
  once; global source model/face ordering and alpha/priority ties remain unchanged.
- Structural geometry is retained separately from changing animation poses. Unchanged
  structural dependencies cause no recurring preparation or upload.
- Ordinary changes cannot fall back to full reconstruction because a historical
  override count exceeds 48 or an asset has not arrived. Load missing assets through
  the existing immutable catalog owner, retain valid presentation and retry affected
  resources. Full reconstruction remains for scene replacement/explicit recovery or
  explicitly identified global inputs outside this slice.
- Native scheduling uses the existing bounded pool, with no nested per-rebuild thread
  creation or pool workers waiting on their own child jobs. Browser scheduling is an
  adapter to the same CPU preparation contracts.
- Validate before publication. Reject obsolete session/scene/resource/request output;
  failures retain dirty/pending state. Matching dependent visual resources publish
  coherently. Interaction queries use committed state, not obsolete worker inputs.
- Background uploads admit at most 2 MiB or 1 ms CPU enqueue work per presentation
  iteration, whichever comes first. Large resources stage in chunks and remain
  invisible until their publication group completes. Actor/overlay work is included
  in total frame timing. No synchronous GPU readback or completion waits.
- Keep immutable assets shared, scratch/output retention bounded, and retired GPU
  allocations alive until their submissions complete. Device reconstruction must not
  change session or plugin activation. Preserve draw distance 184 and effective quality.

## Failure behavior

Malformed inputs remain semantic/adapter errors. Missing assets, upload failures,
queue saturation and cancellation cannot discard commits or clear a newer request.
Keep the last coherent presentation while retryable resources are pending; surface
contextual errors and distinguish unsupported behavior from successful publication.

## Verification and acceptance

Freeze before/after sources, executable, cache/profile, settings and environment.
Use normal GameState/application commits for repeatable location, chat and inventory
controls. Cover boundary footprints, neighboring normals/decorations, bridge tags,
morphs/animation, >48 accumulated keys, missing assets, rapid replacement, stale
results, upload failure, reset/reconnect and reconstruction. Existing source-backed
semantic contracts stay small; bounded GPU/cache diagnostics are separate from CI
appearance tests under ADR-0004.

Record rebuilt resources, copies/uploads, CPU stages/worker time, GPU work, queue
pressure/residency, presentation gaps and commit-to-first-presentation correlation.
Unsupported timing remains unavailable; dropped/incomplete traces invalidate their
measurements. Presentation calls/GPU intervals do not establish physical scanout.
The roadmap owns scored 60 Hz and memory gates; implementation alone closes none.

## Non-goals and open acceptance

Ground-item/combat systems, temporary merge/lifetime completion, instances/world
views, extended scene loading, software rendering, speculative GPU skinning and
external executable ABI 0.2 are separate work. Live reference visuals, full mixed
256-actor acceptance and memory/soak results remain open until observed.
