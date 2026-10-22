# RC240-P1: First Interactive World

Status: active feature branch milestone.
Branch: `feat/rc240-p1-first-interactive-world`.
Target: OpenRune `240.2` desktop vertical slice using the existing shared semantic core.

## Goal

Turn the authenticated rendered spawn scene into the first authoritative interactive gameplay loop without bypassing the repository architecture.

The completed branch must demonstrate:

```text
login
  -> rendered world + entities
  -> semantic world picking
  -> context/menu intent
  -> validated outbound revision-240 action
  -> decoded server response
  -> committed GameState update
  -> renderer/minimap projection follows state
  -> region-boundary continuity
  -> one NPC or location interaction round trip
```

`GameState` remains authoritative. Picking is projection-derived evidence only. User input becomes typed semantic intent before protocol encoding. Revision-specific packet details remain inside the selected protocol profile.

## Checkpoints

### P1.1 Interaction intent contract

Status: complete (`03e3a3bf027d5882e1d910b5c4bd6f48b3d76807`).

- Extend `rustclient-input` beyond title editing with typed revision-neutral gameplay intents.
- Represent walking and target/action selection with existing model IDs and world coordinates.
- Keep packet opcodes, renderer handles, raw mouse events, and mutable game state out of the contract.
- Add focused semantic tests.

Exit: downstream code can express `WalkTo` and target interaction requests without knowing OpenRune packet encodings.

### P1.2 Terrain picking and Walk Here menu

Status: in progress.

- Add deterministic world-terrain picking through the scene/render projection boundary.
- Convert a viewport click into a semantic world tile using the active camera/viewport transforms.
- Build the first context-menu entry, `Walk here`, from the picked semantic tile.
- No network transmission yet.

Exit: native input can select a visible terrain tile and produce the exact `GameIntent::WalkTo` requested by the user.

### P1.3 Outbound walk action

- Add the OpenRune 240.2 outbound movement codec using pinned rsprox/rsprot/reference evidence.
- Validate semantic intent against the active scene/session before encoding.
- Route native application intent through the protocol profile and transport rather than constructing bytes in the app.
- Add golden codec tests and malformed/out-of-scene rejection tests.

Exit: clicking `Walk here` emits the validated revision-240 movement request accepted by OpenRune.

### P1.4 Authoritative local movement

- Complete local-player movement/update application needed for server-authoritative walking.
- Select idle/walk/run movement sequences from committed state.
- Ensure camera and minimap track committed movement rather than client-side invented authority.
- Cover movement timing with deterministic semantic tests.

Exit: after the server accepts movement, the local player visibly walks and camera/minimap follow authoritative state.

### P1.5 NPC/location picking and interaction

- Extend picking to semantic NPC and location targets.
- Build definition-driven action slots and right-click menu ordering for the supported target families.
- Encode at least one NPC or location option through the profile-owned outbound codec.
- Decode/apply the representative server response without renderer or UI state becoming authoritative.

Exit: one real NPC or object interaction completes a user-intent -> server -> GameState round trip.

### P1.6 Region-boundary continuity

- Support the ordinary region rebuild/streaming path needed when movement leaves the initial spawn window.
- Preserve/reconcile entities, scene generation, collision-visible semantics, minimap data, and camera state as required.
- Reject stale async scene work by session/semantic generation.

Exit: the player can cross an ordinary region boundary and continue rendering/moving/interacting correctly.

### P1.7 Major-milestone validation and merge gate

Run and record:

- `cargo fmt --all --check`
- `cargo check --workspace --all-targets`
- `cargo clippy --workspace --all-targets -- -D warnings`
- `cargo test --workspace`
- `cargo check -p rustclient-web --target wasm32-unknown-unknown`
- relevant protocol/picking golden tests
- live OpenRune login/walk/interaction/region-boundary smoke test

Publish exact PASS/FAIL/NOT-RUN evidence. Merge only after all required P1 behavior is complete; do not merge checkpoint branches individually because this milestone intentionally uses one feature branch.

## Deferred from this branch

- full widgets/CS2
- inventory/bank/shop interfaces
- ground items/projectiles/graphics objects unless required by the chosen interaction proof
- browser gameplay composition
- plugin ABI/runtime
- exhaustive outbound packet coverage
- instanced/dynamic regions unless required for the ordinary boundary proof
