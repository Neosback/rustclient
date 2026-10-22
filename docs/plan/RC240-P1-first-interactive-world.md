# RC240-P1: First Interactive World

Status: active feature branch milestone. This branch now ends at the first complete interactive walking slice; NPC/location interaction and region-boundary continuity continue from `main` on a follow-up branch.
Branch: `feat/rc240-p1-first-interactive-world`.
Target: OpenRune `240.2` desktop vertical slice using the existing shared semantic core.

## Goal

Turn the authenticated rendered spawn scene into the first authoritative interactive gameplay loop without bypassing the repository architecture.

The mergeable slice for this branch must demonstrate:

```text
login
  -> rendered world + entities
  -> semantic world picking
  -> context/menu intent
  -> validated outbound revision-240 walk action
  -> decoded server movement response
  -> committed GameState player update
  -> renderer/minimap/camera projection follows state
```

`GameState` remains authoritative. Picking is projection-derived evidence only. User input becomes typed semantic intent before protocol encoding. Revision-specific packet details remain inside the selected protocol profile.

The broader first-interactive-world work still includes NPC/location interaction and ordinary region-boundary continuity, but those are no longer blockers for merging this walking slice. They begin from the resulting `main` head on a new feature branch.

## Checkpoints

### P1.1 Interaction intent contract

Status: complete (`03e3a3bf027d5882e1d910b5c4bd6f48b3d76807`).

- Extend `rustclient-input` beyond title editing with typed revision-neutral gameplay intents.
- Represent walking and target/action selection with existing model IDs and world coordinates.
- Keep packet opcodes, renderer handles, raw mouse events, and mutable game state out of the contract.
- Add focused semantic tests.

Exit: downstream code can express `WalkTo` and target interaction requests without knowing OpenRune packet encodings.

### P1.2 Terrain picking and Walk Here menu

Status: complete. Implementation validated at `9219b9f7eed3bc90a0b35baf4268d932a728e495` by CI run 144.

- Add deterministic world-terrain picking through the scene/render projection boundary.
- Convert a viewport click into a semantic world tile using the active camera/viewport transforms.
- Build the first context-menu entry, `Walk here`, from the picked semantic tile.
- No network transmission yet.

Automated validation at the implementation head passed `cargo fmt --all --check`, `cargo check --workspace --all-targets`, strict workspace Clippy, `cargo test --workspace`, and the WebAssembly web-target check. A synthetic flat-scene regression proves a viewport pointer resolves to the expected visible `WorldTile`; native wiring produces the corresponding typed `GameIntent::WalkTo` while deliberately stopping before protocol/network handling.

Exit: native input can select a visible terrain tile and produce the exact `GameIntent::WalkTo` requested by the user.

### P1.3 Outbound walk action

Status: implementation complete. Validated at `fab1fef01739078fcc7375c201707fc6827b693e` by CI run 156. Live OpenRune acceptance smoke: **NOT RUN** at this checkpoint and retained for the branch merge gate.

- Added the OpenRune 240.2 `MOVE_GAMECLICK` codec from rsprot revision-240 evidence: opcode `102`, variable-byte framing, and body order/transforms `z(g2Alt3)`, `keyCombination(g1Alt2)`, `x(g2Alt3)`.
- Added target-neutral validation against active lifecycle, authentication, and the committed `SceneDescriptor` before profile conversion.
- Kept byte construction in `rustclient-protocol`; native composition only queues validated `ClientIntent` values to the connection worker.
- Kept the existing network worker as the sole socket and ISAAC owner with a bounded outbound queue.
- Added a deterministic encrypted packet golden plus invalid-coordinate and out-of-scene/session rejection tests.

Automated validation passed `cargo fmt --all --check`, `cargo check --workspace --all-targets`, `cargo clippy --workspace --all-targets -- -D warnings`, `cargo test --workspace`, and `cargo check -p rustclient-web --target wasm32-unknown-unknown` on CI run 156.

Exit for the implementation checkpoint: clicking `Walk here` now reaches a validated, profile-owned revision-240 movement packet and the native transport queue. Live server acceptance is deliberately not claimed until the merge-gate smoke test.

### P1.4 Authoritative local movement and branch merge boundary

Status: next checkpoint. This is the stopping point selected for merging this branch to `main` once its merge gate passes.

- Complete local-player movement/update application needed for server-authoritative walking.
- Select idle/walk/run movement sequences from committed state as supported by decoded update evidence.
- Ensure camera and minimap track committed movement rather than client-side invented authority.
- Cover movement timing with deterministic semantic tests.
- Run the branch merge gate after implementation rather than continuing into interaction/region work on this branch.

Exit: after the server accepts movement, the local player visibly walks and camera/minimap follow authoritative state. The branch is then eligible for the merge gate below.

### P1.5 NPC/location picking and interaction

Status: moved to a follow-up branch after the P1.4 walking slice is merged to `main`.

- Extend picking to semantic NPC and location targets.
- Build definition-driven action slots and right-click menu ordering for the supported target families.
- Encode at least one NPC or location option through the profile-owned outbound codec.
- Decode/apply the representative server response without renderer or UI state becoming authoritative.

Exit: one real NPC or object interaction completes a user-intent -> server -> GameState round trip.

### P1.6 Region-boundary continuity

Status: moved to a follow-up branch after the P1.4 walking slice is merged to `main`.

- Support the ordinary region rebuild/streaming path needed when movement leaves the initial spawn window.
- Preserve/reconcile entities, scene generation, collision-visible semantics, minimap data, and camera state as required.
- Reject stale async scene work by session/semantic generation.

Exit: the player can cross an ordinary region boundary and continue rendering/moving/interacting correctly.

### P1.7 Walking-slice validation and merge gate

Run and record after P1.4:

- `cargo fmt --all --check`
- `cargo check --workspace --all-targets`
- `cargo clippy --workspace --all-targets -- -D warnings`
- `cargo test --workspace`
- `cargo check -p rustclient-web --target wasm32-unknown-unknown`
- relevant protocol/picking/movement golden tests
- live OpenRune login/walk smoke test when the development server/runtime credentials are available

Publish exact PASS/FAIL/NOT-RUN evidence. A live test that cannot be executed must be recorded as `NOT RUN`, never inferred from protocol evidence. Merge this branch only after P1.4 is complete, automated gates are green, and any remaining `NOT RUN` live evidence has been explicitly considered before merge. P1.5/P1.6 are follow-up work and are not blockers for this walking-slice merge.

## Deferred from this branch

- NPC/location picking and interaction beyond the typed contract already introduced in P1.1
- ordinary region-boundary continuity/streaming
- full widgets/CS2
- inventory/bank/shop interfaces
- ground items/projectiles/graphics objects
- browser gameplay composition
- plugin ABI/runtime
- exhaustive outbound packet coverage
- instanced/dynamic regions
