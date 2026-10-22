# Playable desktop implementation checkpoint

Status: **incomplete milestone**. Contract: [SPEC-0018](../specs/SPEC-0018-playable-desktop.md).
This checkpoint implements movement, stock NPC updates, world interactions and
inventory synchronization. The full walk-and-bank loop is not implemented.

## Implemented

- Native world/minimap movement input uses the rendering camera's inverse matrix,
  retained terrain triangles, displayed minimap rotation/aperture and HUD occlusion.
- Cache object definitions retain names, actions and movement blocking semantics.
  A disposable collision projection includes terrain flags, bridges, walls,
  footprints, morphs and committed location overrides. Bounded reachability
  rejects inaccessible destinations; the server remains responsible for routing.
- The selected profile encodes MOVE_GAMECLICK and MAP_BUILD_COMPLETE. Ctrl-click
  requests the server's inversion of walk/run preference, not an unconditional run.
  Invalid coordinates are rejected before ISAAC advances.
- Native outbound queues are bounded; map epochs discard requests queued before a
  rebuild/plane change. Scene worker epochs prevent obsolete map/asset results
  from replacing a later projection. Camera orientation survives map changes.
- Ordinary six-byte rebuilds replace the existing map selection, clear obsolete
  location overrides and invalidate scene projections. Variables/entities persist.
- The local-player GPI path now tracks all four aligned list/skip passes, consumes
  remote update blocks, and decodes local step/run/teleport/sequence-clear changes.
  Walk/run/turn base sequence selection derives from committed state.
- Entity animation cycles now advance through semantic operations, including in
  the entity diagnostic. Mutable entity access no longer escapes GameState.
- NPC decoding tracks ordered retained indexes, update origin, variable-width
  spawns, removals, movement and verified stock extended blocks. GameState validates
  whole batches before mutation and owns names, action visibility, sequence delay
  and locomotion. Session-local instance IDs reject menus targeting a reused index.
- NPC and location geometry captures base/effective semantic targets over the
  same posed triangles submitted to the GPU. Untagged surfaces occlude picking;
  failed dynamic uploads retain the prior displayed picking snapshot.
- Native left-click defaults and a right-click action menu use cache names/actions
  and server visibility. Arrow keys, Enter and Escape operate the menu. Application
  revalidates targets, maps and actions before enqueue; all five NPC/location V2
  action slots have selected-profile codecs and synthetic golden coverage.
- Inventory full/partial/stop-transmit packets now produce typed operations.
  GameState owns bounded container slots, including zero-count placeholders and
  mirror IDs. Full updates replace, ordered partials preserve other slots, removals
  forget containers, and session shutdown clears them. No inventory UI yet.
- Fingerprint schema 8 includes complete appearance/playback plus NPC instance
  allocation, names, visibility, delayed sequence state and containers. New events
  and deltas replay through the normal mutation gateway; exact vectors are updated.

## Still required before walk-and-bank acceptance

1. Implement interface state, widget cache decoding, layout/item icons/scrolling
   and bounded CS2 execution for login/inventory/bank.
2. Wire bank open, deposit/withdraw one/all, close/reopen and fresh-login persistence.
3. Finish remaining NPC customization/BAS updates and advanced actor effects.
   Unsupported masks currently fail explicitly. Cosmetic blocks consumed for stream
   alignment do not yet render overhead text, hitbars or effects. Remote player
   semantic updates, movement interpolation and advanced appearance remain open.
4. Run the actual desktop loop against the server. Port 43594 had no listener at
   the continuation checkpoint; login/travel/banking and menu/posed-picking visuals
   have not been manually verified. Passing fixtures do not establish playability.

Instancing, combat, advanced banking, browser gameplay and plugins remain outside
this milestone. Reference sources and the pre-existing untracked deob tooling
were preserved.

## Validation

Passed continuation checks (2026-10-22):

- `cargo test --workspace`: codec families, semantic atomicity/replay and existing
  workspace contracts; output `target/continuation-workspace-tests.log`.
- `cargo fmt --all --check`
- `cargo check --workspace --all-targets`
- `cargo clippy --workspace --all-targets -- -D warnings`
- `cargo check -p rustclient-web --target wasm32-unknown-unknown`
- `cargo check -p rustclient-application --target wasm32-unknown-unknown`
- `git diff --check`

Live gameplay, bank persistence,
GPU/manual appearance acceptance and a broad entity-asset diagnostic were not run.
