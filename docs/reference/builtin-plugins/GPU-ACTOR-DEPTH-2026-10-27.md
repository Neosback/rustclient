# Sorted actor color and deferred depth — 2026-10-27

Implemented the revision-240 opaque actor path that had been absent from the GPU ledger.
This is a bounded port of render mode 2, not complete GPU, actor customization or visual parity.
The ten broader GPU obligations remain open.

## Pinned source

- `Player.vmethod260` returns `classFZ.field2515`, whose value is 2. This applies even when player
  appearance uses an NPC model.
- `NPC.vmethod260` selects the same mode when its base definition's `field5332` is false, or when
  its live model override's `field5371` is true. `NPCComposition` initializes the definition flag
  true and opcode 147 changes it to false. It reads the base definition, not its resolved morph.
- `GpuPlugin.drawTemp` forces sorted model upload for this mode even when all faces are opaque.
  `ModelUploader.uploadSortedModel` orders faces before splitting opaque and alpha output.
- `VAO.draw` paints each consecutive mode-2 opaque range with depth tests and color writes but
  without depth writes. It redraws that range with color writes disabled and depth writes enabled.
  It does not disable the depth test. Alpha faces still use the ordinary later blended stream.

These are pinned injected-240 and RuneLite GPU semantics; no untouched-vanilla claim is made.

## Rust owners

`model::ModelDepthPolicy` is a source-neutral immutable value. The 240 cache adapter decodes opcode
147 into `NpcDefinition::depth_policy`. `scene::entity_mesh` selects deferred depth for players and
reads an NPC's base catalog definition independently of its morph appearance. `SceneModel` extends
the existing vertex span with that policy; scenery retains ordinary depth writing. Input/picking,
posed coordinates, model heights, GameState, fingerprints and external plugin ABI are unchanged.
The renderer's staged CPU copy retains the span/policy for recovery.

`renderer::world_geometry` retains opaque faces in sorted actor metadata, including models without
alpha. It uses the existing twelve-priority face sorter and keeps painter ordering within each
model. Consecutive equal-policy ranges merge, matching the source's range batching. Alpha faces
retain the existing shared stream merge. Deferred opaque index storage is reused; ordinary-only
streams allocate none of that extra scratch. It adds an index-buffer write when their face order
is prepared. No performance or memory-budget acceptance is inferred from that design.

`renderer::world_pipeline` owns four persistent pipelines: ordinary opaque, alpha, deferred color
and deferred depth. The last has an empty color-write mask; both deferred passes keep strict
reverse-Z depth testing. MSAA changes and GPU reconstruction recreate all required pipelines.
Vertex/uniform/shader layouts stay unchanged. No second scene, actor simulation or GPU readback
drives gameplay. Span/triangle boundaries and deferred index contiguity/order are validated before
allocation or queued writes.

## Verification

- Cache grammar contracts retain the default and opcode-147 policy and reject truncation.
- A scene contract resolves both ordinary and deferred morph models while retaining the base
  NPC policy; projection leaves the authoritative fingerprint unchanged.
- Geometry validation covers nonaligned/overlapping spans, triangles crossing spans and
  interrupted deferred model index ranges before uploads.
- Actual Metal synthetic fixture: ordinary nearest red is `(249,32,16,255)`; sorted priority-9
  blue painting after priority-0 red is `(16,22,249,255)`. An intermediate later surface remains
  occluded by the nearest depth written in the second pass. An earlier wall still blocks the
  actor. Fresh GPU reconstruction is byte-identical. These are synthetic priority/depth cases,
  not cache actor screenshots or source visual acceptance.
- Browser WebGPU: **35 checks pass**. Four new actual world-WGSL/indexed-draw checks cover the
  same color/depth recipe and repeated execution on a core-feature device. The JavaScript fixture
  supplies the ordered indices directly; it does not execute the Rust CPU sorter or playable Wasm
  application. Its gamma-1 colors intentionally differ from the native default-gamma fixture.

Artifacts: `target/gpu-depth-probe/`, `target/gpu-depth-probe.log`,
`target/gpu-depth-browser.json`, `target/gpu-depth-browser.png`.
Reproduction: [GPU tools](../../../tools/gpu/README.md). Full gate results belong in
[the owning plan](../../plan/BUILTIN-PLUGINS.md).

The source uploader also rejects a whole model when any vertex is nearer than 50 fine units and
excludes back faces before its priority thresholds. Rust still relies on GPU clipping/backface
culling and the existing centroid-based sorter. Exact source face selection, bucket arithmetic and
near-plane behavior are not complete; the synthetic fixture covers front-facing distant geometry.

Live NPC override flags still lack complete semantic retention; tint/transparency, world-view/boat
transforms, other render modes, exact zone alpha ordering and Java/gameplay visual acceptance
remain incomplete. The existing server-testing client was left running on its launch build;
automated/offscreen results do not establish that this actor policy was accepted in that session.

The symbol checker now recognizes the pinned RuneLite helpers' one-tab method indentation as well
as the decompiler's three-space indentation. A focused declaration check finds VAO.draw/reset/addRange,
rejects nested glDrawArrays calls as declarations and retains NPC.vmethod260. Source hashes and the
150-row ledger validate. This parser support is not a complete Java AST or a migration percentage.
