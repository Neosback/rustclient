# Sorted-model admission and bucket arithmetic — 2026-10-28

<!-- authority-notice -->
> Historical continuation with bounded source/runtime evidence. Use the
> [current audit](../rs240-port/CURRENT-AUDIT.md), [GPU ledger](GPU-AUDIT.md) and
> [roadmap](../../plan/PORTING-ROADMAP.md) for present status and ordering.

This checkpoint extends the prior [actor depth port](GPU-ACTOR-DEPTH-2026-10-27.md).
It implements source face selection and depth arithmetic for actors that enter the sorted uploader.
It does not establish complete GPU parity or readiness for 117HD.

## Evidence and ownership

Pinned `ModelUploader.uploadSortedModel` rejects the entire model when any projected vertex is
nearer than 50, rejects cylinders with diameter at least 6000, caps the original face count at
8192, and excludes back faces before priority averages. Depth is the cylindrical radius plus the
signed integer average of three vertex distances; each vertex and the origin are truncated before
subtraction. Ordinary alpha actors use depth order; mode-2 actors use twelve priority queues.
`IntProjection.project` supplies the float view transform; `Model.method5254` supplies bounds.

`scene::model_sort` retains immutable placed posed vertices, including unused/hidden vertices,
origin and derived bounds alongside the existing `SceneModel`. This is disposable projection data;
it adds no actor state or simulation. `entity_mesh::emission` owns triangle extraction and the
8192-original-face cap for known sorted actors. The cache model does not yet retain authored
zero-only alpha-array presence; those otherwise opaque NPCs remain a separate incomplete case.

`renderer::world_geometry::sort` filters whole models and front faces before depth/priority ordering,
reuses existing ordering storage and preserves resident static geometry compaction. Rejected opaque
faces become degenerate indices in retained spans; alpha output includes only admitted faces.
`world_geometry::validation` checks guard budgets, bounds metadata and contiguous model index spans
before allocations/writes. No additional GPU pass or shader feature is required.

The renderer view matrix, source bucket projection and CPU picking use the same float view
coefficients. The reverse-Z near boundary is 50 for rendering, point projection and pointer rays.
Picking checks whole-model admission once per model per query and front faces per candidate triangle;
it leaves geometry and authoritative `GameState` untouched.

## Verification scope

`tools/gpu/model_sort_oracle.py` verifies source hashes and extracts the literal Java projection and
bounds methods, plus uploader face/depth expressions, into a temporary harness under `target/`.
Its bit-exact projection vectors, bounds (7,13), reversed-face rejection and signed bucket vectors
are compared with Rust contracts. A fixture yielding bucket 172 instead of the centroid approximation
171 protects the per-vertex truncation order. This executes literal arithmetic, not the gamepack,
complete uploader or Java GPU frames.

Additional contracts cover the 49/50 boundary, 5999/6000 diameter boundary, unused guard vertices,
original-face cap, ordinary/alpha actor emission and picking consistency. The explicit Metal probe
adds reversed-face and unreferenced-near-vertex captures to the prior priority/deferred-depth cases.
Automated and actual-GPU run results are recorded in the owning delivery plan after execution.

Float animation coordinates/orientation are still quantized into the existing integer mesh. Full
float-vertex fidelity, authored zero-only alpha-array presence, NPC overrides/tint, world-view/boat
transforms, static-scene source upload/frustum/alpha order and exact Java/gameplay acceptance remain
open. The previous browser 35-check fixture exercises shared WGSL and the pass recipe, with supplied
indices; it does not execute this Rust sorter or playable Wasm game. Desktop three-mode acceptance
is pending: the user has not tested all three modes yet.
