# Plugin hooks, ground items, interface ops and efficiency pass, 2026-11-05

Evidence record for the pass after the [2026-11-02 pass](CLIENT-PASS-2026-11-02.md). It is not an
execution plan: the [roadmap](../plan/PORTING-ROADMAP.md) owns order and what remains, and the
[domain matrix](rs240-port/PORT-MATRIX.md) owns status.

Every line about the live client is **NOT-RUN**. This pass ran in a container with no OSRS cache
and no GPU, so `interface_audit`, `scene_probe` and the native client were not run. Nothing below is
accepted beyond the unit, integration and source evidence named with it.

Commits on `claude/gracious-johnson-m90ttn`:

| Commit | Change |
| --- | --- |
| `ccf8a074` | Rust 1.99 Clippy fixes |
| `f26dae55` | interface-audit CS2 ops |
| `9ff06076` | ground items |
| `8bb32a1d` | menu stage and Menu Entry Swapper |
| `2ed41e23` | ground items for plugins, info boxes, minimap dots, Ground Items |
| `ad4429f2` | plugin snapshot domain reuse |
| `2386fc28` | seams, emit, cancellation and binds |

## What changed

1. **Toolchain.** The pinned `stable` channel resolved to Rust 1.99, which adds
   `assert_is_empty` and `chunks_exact_to_as_chunks` to `clippy::all`. `assert_is_empty` is
   allowed workspace-wide, and the other findings are fixed.
2. **Interface audit ops** (SPEC-0025, `scripts::unmodelled`):
   - `INVOTHER_*` read inventory id + 32768.
   - Unloaded-system answers: `IGNORE_COUNT`, `CLAN_GETCHATOWNERNAME`, `CLANPROFILE_FIND`,
     `WORLDLIST_FETCH`, `MOBILE_WIFIAVAILABLE` and 3644.
   - The eight empty `STOCKMARKET_*` offers.
   - 3129 consumes its two operands.
   - VM ops: 4036 integer parse; `FROMDATE` formats the UTC date.
   - Rectangle gradient fills (1123-1125 and the `IF_` forms), painted row by row with the
     `Rasterizer2D` fixed-point blend.
   - Head sources (`CC_SETNPCHEAD`, `CC_SETPLAYERHEAD_SELF`) are retained but not painted.

   Fingerprint schema 30. New deviations: `FROMDATE-UTC`, `OCULUS-ORB-SPEED`,
   `WIDGET-HEAD-MODELS`, `STOCKMARKET-EMPTY-OFFERS`.
3. **Ground items** (new SPEC-0029):
   - Wire: six enclosed zone bodies and six `*_SPECIFIC` packets. Every `g4Alt` variant is
     resolved against the stock `classXY` read methods.
   - State: `GameState` piles with stock matching, cleared by zone reset, window-changing rebuild
     and session boundary. `GROUND_ITEMS` delta, per-tile projection change, fingerprint schema 31.
   - Cache: item cost, ground actions and count variants.
   - Interaction: world menu entries for the hovered tile, revalidated `OPOBJ1..5` encoders, red
     minimap dots.
   - Deviations: `GROUND-ITEM-TILE-PICK`, `GROUND-ITEM-PILES-UNDRAWN`.
4. **Plugin menu stage** (SPEC-0021, ADR-0006):
   - `MenuEntryAdded`, `MenuOpened` and `PostMenuSort` come from a synchronous host stage.
     `PostMenuSort` subscribers' `menu_edits` are applied in order. The edited list drives the left
     click, the mouse-over text and the opened menu.
   - Additive `MenuEdit::Swap`.
   - Stock entries reach plugins as `MenuOptionClicked`.
   - `ScriptPostFired` fires per finished script invocation.
   - Menu Entry Swapper ports the built-in swap table and the exact bottom-to-top walk.
5. **Plugin services** (ADR-0006):
   - Ground items in the API: `game.read.ground_items`, snapshot records, and
     `ItemSpawned`/`ItemDespawned`/`ItemQuantityChanged`.
   - `OverlayCommand::TileText` and `InfoBox::sprite`.
   - The shell now draws info boxes (RuneLite layout and look) and `MinimapDot`.
   - Ground Items text overlay subset, using high alchemy values only.
   - The schema generator now finds config-subpackage enums.
6. **Plugin projection reuse.** The bridge carries an unchanged domain forward from the previous
   snapshot and skips its diff. Domains are keyed on the `GameState` journal cursor, grants, shell
   session, definition revision and varbit table identity. An equivalence test compares the result
   with an always-rebuilding projector after every step. The redraw computes the scene visibility
   predicate once.
7. **Scene load and GPU:**
   - The seam merge sorts each model's lit vertices once into reused buffers and merge-joins them.
     It had rebuilt a `BTreeMap` for every pair. The merge-join test also covers duplicate
     positions.
   - The static emit reserves capacity up front.
   - `build_mesh_cancellable` and `load_scene_cancellable` stop between phases; the native load job
     uses them.
   - Static opaque runs skip redundant buffer and pipeline binds.

## Validation

Run on the final commit of the pass:

```text
cargo fmt --all --check                                         pass
cargo clippy --workspace --all-targets -- -D warnings           pass
cargo test --workspace --no-fail-fast                           801 passed, 0 failed
cargo check -p rustclient-web --target wasm32-unknown-unknown   pass
cargo check -p rustclient-application --target wasm32-unknown-unknown   pass
python3 tools/port_matrix.py --check                            pass
python3 tools/port_audit.py --check                             pass
python3 tools/parity_inventory.py --check                       pass
python3 tools/docs_index.py --check                             pass
python3 tools/docs_check.py                                     pass
```

`cargo check --workspace --all-targets` passed as part of Clippy.

## NOT-RUN gates

- `interface_audit CACHE --check --verbose`: the committed baseline (33 script-error groups) was not
  regenerated. Which groups now mount is unknown until it runs.
- `scene_probe CACHE --performance` and frame traces: the seam, emit, reuse and bind changes have no
  measured before/after numbers.
- GPU: no adapter, so the bind-skipping draw path ran only through compilation.
- Live client: ground item drop/pickup/count/despawn, OPOBJ actions against OpenRune, menu swaps,
  Ground Items labels, info boxes and minimap dots, and gradient rectangles in the settings and
  Grand Exchange interfaces.

## Deferred, with reasons

- **Parallel seam merge and emit:** wait for a cache profile of the serial merge join.
- **Hover-pick prefilter:** `PickBounds` are clickboxes, not guaranteed to enclose posed geometry
  in scene space, so testing against them could drop real hits. A safe prefilter needs bounds
  computed from the emitted triangles.
- **Static-run culling outside the admission window:** needs the zone window at sort time and a
  depth-tie argument. It is unmeasured.
- **Camera target and zoom-limit commands:** these need the reference easing in the camera step,
  and no plugin emits them yet.
- **Batched plugin event dispatch:** the routing tests make one callback per event an explicit
  contract (`event_routing.rs`).

## Continuation (same day): actor effects, camera targets, Shift/clock, widget menus

Commits on `claude/gracious-johnson-m90ttn` after `c890f759`:

| Change | Evidence |
| --- | --- |
| Hitmarks, health bars and spot animations retained as actor state (SPEC-0022): `openrune_240::actor_effects` decoders replace the skip code, `ActorEffectsState` on players and NPCs with cycle-stamped lifetimes, plugin snapshot fields and the existing `HitsplatApplied`/`GraphicChanged` diff; fingerprint schema 32; deviations `HITSPLAT-DEFAULT-DURATION`, `HEADBAR-DEFAULT-DURATION`, `HEADBAR-FILL-UNITS` | protocol golden bytes for every block class, GameState lifetime/expiry/index-reuse/replay test, bridge snapshot-and-event test |
| Plugin camera targets applied (`YawTarget`, `PitchTarget`, `ZoomTarget`, `ZoomLimits`); additive `ClientSnapshot::shift_held` and `wall_time_ms`; Menu Entry Swapper Shift-dependent swaps; deviation `CAMERA-TARGET-EASING` | camera easing test, Shift swap test |
| Widget menus run the plugin menu stage with `CC_OP` row conventions; shared `apply_edited_menu` | row projection and reordering unit test; no live inventory |

Validation of the final commit: `cargo fmt --all --check`, `cargo clippy --workspace --all-targets
-- -D warnings`, `cargo test --workspace --no-fail-fast` (812 passed, 0 failed), both wasm checks and the
five documentation tools all pass.

Still NOT-RUN: `interface_audit`, `scene_probe`, GPU, and every live check (real hitsplat traffic, camera
feel, widget menu swaps on a real inventory).

Deferred, with reasons:

- **Ground item piles in the scene** (and pick-by-model): needs the item resize opcodes (110-112,
  skipped by the definition decoder), the stock three-item stack ordering and a GPU/visual comparison;
  none of it can be verified without a cache and an adapter. The semantic state, menu, minimap dots and
  plugin API from the previous pass stand; deviations `GROUND-ITEM-PILES-UNDRAWN` and
  `GROUND-ITEM-TILE-PICK` remain open.
- **The interaction target** (`FACE_PATHING_ENTITY`): the player/NPC facing blocks are consumed as
  angles; the entity-target mask and writer order are not established, so `ActorState::interacting`
  stays empty and Idle Notifier's interaction branch stays dormant.
- **Plugin snapshot reuse for actors:** every client cycle changes the entity domain while any actor
  exists, so the carry-forward in `ad4429f2` saves players and NPCs only when no actor is present; it
  saves vars, containers, chat and ground items as designed.
