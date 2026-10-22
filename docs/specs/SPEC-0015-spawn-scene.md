# SPEC-0015: Cache-backed spawn scene preview

Status: implementation milestone. Owners: model (immutable scene identity/bounds),
cache (240 map/floor/location/model decoding), scene (immutable asset assembly and
disposable mesh extraction), game-state (loaded-scene reference), application
(loading orchestration), renderer (camera, depth and GPU meshes), native (workers,
input and capture files).

## Scope and invariants

After authenticated initial placement, load the normal map window around its
anchor using the selected cache profile. Revision-specific archive layouts,
sentinels and binary formats remain in the cache adapter. In particular, modern
map squares use numeric groups with separate terrain/location files; do not infer
historical mX_Y/lX_Y names or request unrelated encryption keys.

Decode actual tile heights, floor definitions, overlay shapes and static location
models, retaining source provenance and contextual bounded errors. Unsupported
formats/opcodes fail explicitly. No placeholder buildings or procedural substitute
maps may be presented as decoded assets. Static animation/morph limitations and
material approximations must be counted and reported.

The scene asset is immutable CPU data with a content identity. GameState owns the
authoritative reference and world bounds through a validated typed operation;
session end clears it. An application-owned immutable asset catalog resolves that
reference. It is not a second mutable scene authority. Renderer geometry, camera
and GPU resources are disposable projections. Background extraction validates its descriptor against the immutable catalog asset; presentation selects only the
asset referenced by committed state. New state/event/delta encoding uses semantic
fingerprint v4, with replay/reset/failure coverage.

The first visual target is a static spawn-region preview, with real cached terrain
and location geometry, depth testing, directional shading and an orbit/zoom camera.
Use texture-average colors where UV materials are not implemented and report this
explicitly. Omit roofs above the player's plane while retaining bridge surfaces.
Dynamic entities, animation, var-driven morph updates, navigation/collision,
instanced/subsequent rebuilds, widgets, CS2 and full software-renderer parity remain
outside this slice. Camera controls never move the authoritative player.

## Loading and failure behavior

Load/decode/mesh off the event thread. Results carry the connection generation;
cancelled/stale results cannot install a scene. Keep login/ticks responsive during
loading. Scene failure leaves authenticated state intact, reports exact resource
context, and permits disconnect. Preserve CPU assets and GameState through GPU
recreation. Zero-sized surfaces suspend; resize recreates depth attachments, not
pipelines. Native and wasm share decoding/scene/render code.

Bound map opcodes, smart accumulators, definition/model counts, section offsets,
triangle indexes, scene extents and total mesh allocations before use. Missing
required resources and unsupported binary layouts are errors, not guessed defaults.

## Evidence and acceptance

Record exact reference paths/revisions/hashes for materially ported behavior.
Add synthetic provenance-recorded fixtures for map/floor/location/model streams,
malformed inputs, geometry transforms, scene-reference lifecycle and replay.
Probe the actual server LIVE cache, report tile/location/model/triangle counts and
all rendering limitations, then capture actual GPU frames from fixed cameras.
Verify pixel/semantic preservation through GPU recreation and resize/camera input.
Run formatting, all-target workspace check, strict Clippy, workspace tests and
web/shared-application wasm checks. Report precisely which checks actually ran.

## Explicit compatibility and material limits

`osrs-240` remains strict about decoded reference-size metadata. The opt-in `openrune-240` repack profile treats only index 2/group 6's decoded-size hint as advisory, and reports the observed mismatch. CRC, stored version, sector/container lengths and allocation bounds remain mandatory. The running server's object archive reports 2,439,447 decoded bytes but contains 2,452,568. A synthetic fixture proves this exception does not bypass CRC checks.

Reference evidence and SHA-256 records live in `crates/cache/tests/fixtures/world/README.md`. Static model families FF FD and FF FE are implemented; other model discriminators fail. Geometry follows editor placement, including dual walls, decoration full/half offsets, footprint rotation, mirror, definition scale/translation, contouring and shape-11's final 256-JAU rotation.

This preview uses flat directional shading, per-tile unblended underlay HSL and texture-average colors. It defers texture UV sampling/animation, 11-tile HSL blending, reference baked/smooth lighting and object contrast, priority/face-bias ordering, occlusion/culling and backface-cull parity. Morphing locations are omitted and counted; animated locations use static unposed geometry and are counted. Additional modern map files are integrity-checked through their group but not interpreted. The gold triangle marks initial spawn, not a live player model.

Controls: right-drag/arrows orbit, wheel zoom, Home reset, F5 rebuild disposable GPU resources, Escape or HUD Disconnect ends the session. UI uses the same centered logical/DPI transform as hit testing. No flame texture upload or periodic flame redraw is performed while the world is active.
