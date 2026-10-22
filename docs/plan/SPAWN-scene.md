# Cache-backed spawn scene checkpoint

> **Historical.** This records the SPEC-0015 slice as delivered. The later working tree has since added reference lighting with normal merging, UV/texture arrays, scrolling textures, a gameplay camera, entities and a minimap (SPEC-0016/0017); the flat-shading and average-colour limits below no longer describe the code. Test counts below are as of this slice.

Delivered: authenticated OpenRune 240.2 login opens a static cached Lumbridge scene on desktop. This is the SPEC-0015 slice; general entities, movement, material parity and subsequent/instanced rebuilds remain separate work.

## Ownership and implementation

- `cache::osrs240` owns revision-specific normal-scene extents, index 5 numeric map groups/files, underlays/overlays, object definitions, texture averages and FF FD/FF FE static model decoding. Bounded readers validate stream sections, smart deltas, counts and triangle indexes, with source context. No filesystem access or full data-file load occurs in shared decoding.
- `scene` retains immutable CPU assets and extracts disposable mesh geometry. Model IDs, terrain heights, overlay topology/rotation, footprints, mirrors, scale, translation, wall pairs/decoration offsets, ground contouring, authored recolor/retexture order and shape-11 final 45-degree placement follow pinned reference behavior.
- `GameState` selects the immutable content ID and footprint with `InstallScene`. Placement containment, duplicate rejection, reset, events/deltas, canonical fingerprint v4 and replay are covered. The worker can prepare geometry before selection; rendering requires the committed reference to match uploaded geometry. Camera and credentials stay outside semantic state.
- `application` orchestrates cache/scene loading; native dispatches workers with connection generations. Stale completions are discarded. The window stays interactive during cache work, and the scene stays intact across GPU recreation.
- `renderer` owns static vertex/index buffers, depth attachments and camera uniforms. Opaque and sorted transparent geometry precede a separate alpha-blended UI pass. Resize replaces depth attachments; camera updates reuse buffers. The world path performs no flame uploads/50 Hz flame redraws. CPU assets and camera survive F5 recreation.

## Exact observed cache

Server tree: `/Users/tylercovalt/Desktop/OpenRune Project/OpenRune-Server`, commit `abc80a8d76d68912f96819680f8aae279a586514`.

Cache directory: `.data/cache/LIVE`; profile: `openrune-240`.

- Reference metadata SHA-256: `f12db51150826c019f1ad156b7431a26f62023db736fffff3f989a97a368c3f5`.
- Scene asset SHA-256: `1cd67cebb9f877ac371f63bdb99cd8dd703d8a922f8162a5049eb48cf3c413ca`. Its domain-separated digest includes the profile, metadata identity, footprint, resource addresses/lengths and actual decoded source bytes used by the scene.
- Initial position: `(3221,3218,0)`; server anchor `(3216,3216,0)`; normal window origin `(3168,3168,0)`, 104×104 tiles.
- 9 decoded map squares, 147,456 terrain records across four planes, 21,610 location records across those squares; 577 required object definitions and 509 models.
- 6,665 visible location records considered; 18 var-driven morphs deferred; 110 animated locations use unposed static geometry; no missing model selections.
- 22,999 terrain triangles, 523,137 nondegenerate model triangles and one gold spawn-marker triangle. Total: 546,137 triangles, 1,638,411 vertices, 65,536,440 vertex bytes plus index buffers. This initial unbatched CPU extraction duplicates three vertices per triangle; no per-frame mesh rebuild occurs.
- 18,271 processed texture-average faces; 132 hidden model faces skipped. 27 auxiliary modern map files were group-integrity checked but not interpreted.

The server repack's index 2/group 6 reports decoded size 2,439,447 but decodes to 2,452,568 bytes. Only the explicit `openrune-240` profile accepts this stale hint and reports it. The strict `osrs-240` profile rejects it. CRC, stored version, sector/container sizes and all allocation bounds remain enforced. A synthetic repack fixture checks strict rejection, explicit acceptance and continued CRC rejection.

The original Desktop/LIVE title cache has a different metadata identity; selecting this server's own cache remains necessary for pre-login CRC compatibility. Cache metadata revisions alone do not identify client revision.

## Reference provenance

Exact paths, commits and SHA-256 hashes are recorded in `crates/cache/tests/fixtures/world/README.md`. RustOSRS is read-only evidence and is not a Cargo or runtime dependency. Its GPU geometry path identified shape-11's extra instance rotation; placement tests also freeze full/half/opposite wall-decoration offsets. RuneLite 235 supplies the floor topology rows. No reference or server source/config/private-key files were modified.

## Verification actually run

All commands exited successfully:

```sh
cargo fmt --check
cargo check --workspace --all-targets
cargo clippy --workspace --all-targets -- -D warnings
cargo test --workspace
cargo check -p rustclient-web --target wasm32-unknown-unknown
cargo check -p rustclient-application --target wasm32-unknown-unknown
```

Workspace tests: **118 passed, 0 failed, 0 ignored**. Added coverage includes both model families and malformed/truncated sections/indexes, modern terrain/floor/location/object grammar, repack integrity policy, all floor shape/rotation areas, transform/contour/recolor/morph/bridge rules, scene identity/lifecycle/replay and camera/depth conventions.

Actual GPU diagnostic:

```sh
cargo run -p rustclient-native --bin scene_probe -- "/Users/tylercovalt/Desktop/OpenRune Project/OpenRune-Server/.data/cache/LIVE" target/scene-captures
```

Two fresh GPU devices/pipelines produced byte-identical 1024×768 pixels at the fixed default camera. A second orbit/zoom view was captured at 1530×1006. The diagnostic builds a synthetic authenticated state from actual cache assets, installs the scene, and verifies its semantic fingerprint remains `f1316a0ae748ae32` across GPU projection recreation. This is reconstruction evidence, not an injected hardware device-loss test.

Live UI smoke against the user's already-running localhost server used one disposable account: login → cached spawn view; arrow orbit; wheel zoom; Home reset; F5 renderer recreation (diagnostic confirmed semantic preservation); macOS window zoom/restore; resized HUD Disconnect; reconnect. Password cleared on disconnect and was not persisted or logged. The server remained running. The test client is left displaying the scene. Right-drag is implemented; the automated native smoke exercised arrow orbit instead.

Local evidence (ignored build outputs): `target/scene-validation/results.json`, `evidence.json`, individual command logs and `gpu-probe.log`; fixed-camera PNGs under `target/scene-captures/`; live PNGs and sanitized client log under `target/scene-smoke/`. `git diff --check` also passed.

## Deliberate rendering/gameplay limits

This is a static world preview. Textures use cached average colors; underlay colors are per-tile rather than the reference 11-tile HSL blend. Flat directional shading differs from reference baked/smooth lighting and object contrast. UV textures/animation, face bias/priority ordering, reference occlusion/culling/backface behavior and material parity remain deferred. Roof surfaces above the player's plane are omitted; bridge surfaces remain. Morphing objects are counted and omitted; animated locations use static rest geometry. The gold triangle is a spawn marker, not a player model.

The network still applies initial placement, tick-end and logout only. It does not yet apply ongoing player/NPC movement/appearance, subsequent rebuilds, world interactions, navigation, widgets or CS2. Next useful slices are live local-player/entity updates and textured/reference-lit geometry, using the pinned editor as evidence.
