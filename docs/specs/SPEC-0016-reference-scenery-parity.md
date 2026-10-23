# SPEC-0016: Reference GPU scenery and gameplay camera

Status: implementing; not a parity/completion claim. Supersedes SPEC-0015's visual
approximations. Owners: cache (240 binary assets), scene (resolved scenery and CPU
projection), game-state (variables, location overrides, explicit client cycle),
application (orchestration), renderer (GPU and gameplay camera), platform adapters
(input/time/transport), native/web (composition), tools/parity (independent evidence).

## Contract

Use RustOSRS commit 854a3962164ad401d21064a5f207f86869ea69b4's executable Reference
GPU path, not HD/editor enhancements; use RuneLite deob commit
1ad572d7dcdbc0fb67a4a00f0c2f959d5ab25abc for gameplay camera and missing semantics.
Source hashes, notices, scratch-only oracle adjustments and input manifests must be
recorded. Reference trees and server/cache remain read-only; no RustClient Cargo
path or runtime dependency may point into references. Fixture tests run standalone.

Settings: brightness 0.8; remove color banding on; bright textures off; fog off;
black clear; draw distance 50 tiles; 4x MSAA; animation smoothing off; terrain
smoothing and decoration snapping off. Default roof behavior is reference cutaway.
World projection uses the reference non-sRGB target, reverse-Z/strict Greater,
CCW/backface culling, authored bias, exact cutouts, alpha ordering and executable
blend/depth-write settings. UI display conversion must not change world RGB.

Match terrain window boundary arithmetic, weighted HSL blending, slope/shadow
corner colors, flat/shaped floor sentinels; location admission/order/shadows;
model assembly, transformations, lighting, neighboring normal reconciliation,
contouring; then minimum-plane/bridge relinking. Preserve raw material metadata,
UV mappings, texture pixels/mips/sampling and animation inputs through decode.
No generic relighting, invented sky/fog, average-texture fallback, silently omitted
morphs or silent animation rest-pose fallback may satisfy a parity gate.

GameState owns semantic variables, location replacement/removal/animation overrides
and a typed 50 Hz client cycle distinct from server GameTick. Typed operations,
reset, deltas/events, replay and fingerprint v5 cover every new semantic field.
Immutable decoded assets are catalog data, and meshes/poses/visibility/camera are
rebuildable projections. Preserve CPU assets, animation phase and camera on GPU
recreation. Asset/projection work carries session and semantic generations.

Camera follows authoritative spawn fine-coordinate center and bridge-aware terrain
height. Port deob angle units, 128..383 pitch, wrapped yaw, held-key/middle-mouse
acceleration/damping, focal height, terrain distance and viewport/zoom rules. Step
at 50 Hz independently of redraw and OS repeats; share DPI/viewport transforms for
render/input/picking. Hide preview marker/panels by default; keep optional diagnostics
and renderer rebuild. No invented player model or local movement authority.

## Bounds and failures

Static scenery, animated scenery and actors share one transparency order. Opaque
draws complete first; transparent models are merged far to near across resident
geometry streams with stable source/emission ties. Faces within a model retain
their authored priority and original ordinal. The twelve-queue priority preparation
uses the `(1,2)`, `(3,4)`, `(6,8)` average-depth thresholds and exhausts priority 10
before 11. Opaque faces participate in those thresholds; only transparent faces
enter the blend pass. Alpha draws retain strict depth testing with writes disabled.
Authored depth bias and face priority remain distinct: priority is an ordering
rule, never an invented global depth offset. Face ordering reuses its scratch
storage between frames. RustOSRS `osrs-render/src/gpu/mod.rs` and `priority.rs`
are the evidence, with the independent threshold-crossing fixture recorded in
source-manifest.json. Structural ordering contracts do not satisfy ADR-0004's
visual acceptance. Exact zone/level ordering and coplanar material acceptance
remain part of that gate.

Validate binary section lengths/counts, selectors/indexes, recursive dependency
closure, morph cycles, pose layouts and projection budgets. Decode errors identify
source and stage; unsupported required formats fail the relevant gate explicitly.
Cache and projection work stay off the event thread. Apply source-neutral decoded
updates through the existing GameState gateway; failed operations remain atomic.
Discard stale async results and keep disconnect available on scene failures.

## Acceptance

Independent structural goldens for textures, terrain colors, models/normal merges,
lighting, UV/alpha/bias, morphs, animation, roofs and draw order. Camera traces must
match deob integer outputs. Fixed same-backend/state/viewport captures must match
pixel-for-pixel; report cross-backend differences separately. Cover spawn terrain,
castle interiors/exteriors, water, textured walls, foliage, transparency, bridges,
roofs and mirrored/diagonal objects. Exercise every required animation format,
variable reset/sync and location changes; no unexplained omissions pass.

Run live authentication/scenery/camera/reconnect/resize/GPU reconstruction; formatting,
workspace all-target checks, strict Clippy, workspace tests, native/web shared wasm
checks and reference cleanliness. Publish reference/actual/difference artifacts and
individual PASS/FAIL/NOT-RUN gates. Green unit tests alone do not establish parity.

Non-goals: walking; player/NPC models; ground items, projectiles and spot effects;
instanced travel/subsequent region streaming; full widgets or general CS2.
