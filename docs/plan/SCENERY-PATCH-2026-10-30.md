# Scenery patches for door and location changes (2026-10-30)

Checkpoint for the "per-zone scene updates for smooth doors" task. The contract is the
"Scenery patch publication" section of [SPEC-0016](../specs/SPEC-0016-reference-scenery-parity.md),
written before the implementation. Nothing here was observed on a live client.

## What changed

A committed location change (door open/close, wall removed, object replaced) used to rebuild the
whole scene on a worker and replace the roughly 44 MB static vertex stream. It is now published as
the baked **baseline plus a patch**:

| Piece | Owner | Behaviour |
| --- | --- | --- |
| Baseline | `crates/application` (`projection_inputs.base_locations`) | The override set the static scene was baked with, shared by `Arc`. A full rebuild is the only thing that replaces it. |
| Planner | `crates/scene/src/locations/patch.rs` | Diffs the baseline and current override sets into the changed locations, their seam neighbours (footprint plus one tile, planes +/-1) and the neighbours' neighbours as inputs only. Falls back when more than 48 keys differ. |
| Patch geometry | `build_dynamic` | Emits the changed locations and their neighbours into the existing per-cycle dynamic stream, and lists the baseline locations they replace in `hidden_static`. |
| Static identity | `SceneGeometry::model_sources` | Records which effective location each static model came from. |
| Rendering | `crates/renderer` (`GeometryGpu::set_hidden`) | Hidden models leave the opaque draw ranges and the alpha/deferred ordering. No static buffer is written. |
| Picking | `ScenePickIndex::set_hidden` | Skips hidden static triangles and clickboxes. |
| Native | `apps/native` | A change the baseline can express as a patch no longer reprojects. Variable-only changes no longer reproject either. |

Equivalence is covered by `crates/scene/src/tests/patch.rs`: baseline minus hidden models plus the
patch equals a full rebuild, compared as a multiset of raw vertex bytes, for an opened door between
mergeable walls, a wall removed beside a wall decoration, two doors changed at once, and a far
location that must stay in the baseline.

## Deliberate behaviour change

Terrain shadows now come from the map-file locations only. Stock 240 writes them in the load path
(`classDI.method3340`) and never in the LOC_ADD path (`method3336`), so an opened door no longer
changes the terrain under it. The previous code fed server overrides into the shadow pass, which
made terrain depend on overrides and forced a terrain rebuild.

## Measurements (CPU only)

`scene_probe <cache> --updates`, release build, one machine, nine alternating remove/restore
samples of openable object 12986 at (3186, 3268). Raw samples, milliseconds:

| Sample | Full mesh | Patch build | Hidden locations | Patch vertices |
| --- | --- | --- | --- | --- |
| 0 remove | 124.58 | 13.30 | 11 | 90936 |
| 1 restore | 134.62 | 4.85 | 0 | 89445 |
| 2 remove | 129.12 | 12.74 | 11 | 90936 |
| 3 restore | 121.99 | 4.33 | 0 | 89445 |
| 4 remove | 121.64 | 12.53 | 11 | 90936 |
| 5 restore | 121.89 | 4.37 | 0 | 89445 |
| 6 remove | 123.01 | 12.63 | 11 | 90936 |
| 7 restore | 122.35 | 4.40 | 0 | 89445 |
| 8 remove | 120.78 | 13.97 | 11 | 90936 |

- The patch's vertices are mostly the dynamic stream that was already rebuilt every cycle (89445
  vertices when nothing is patched). The change itself is about 1.5k vertices (1764 hidden).
- Replaced bytes per change: about 44.2 MB static before; about 2.2 MB dynamic now.
- Index hide: 0.0004 to 0.0188 ms. The vertex total matched the full rebuild in all nine samples.
- Patch build adds about 8 ms to a worker pass that previously cost about 4.4 ms. It runs on the
  animation thread, once per cycle while a change is outstanding, never on the event loop.

## Not done / NOT RUN

- **Live frame pacing: NOT RUN.** No client was driven and `RUSTCLIENT_FRAME_TIMINGS=1` was not
  observed. The user declined computer-use access to the live client. A door check on a real
  session is the remaining acceptance step.
- **First appearance of an undefined object** (for example an open-door object not in the map
  files) still takes the old full rebuild, because the asset must be extended on the cache. Later
  changes with that object patch. Extending the asset without a static rebuild is a follow-up.
- **Patch recomputed every cycle.** The overlay is rebuilt each animation pass while it differs from
  the baseline. Caching it (baseline pointer, overrides, roof areas) is a follow-up.
- **Full re-sort after a hide change.** `last_sort` is cleared so alpha and deferred models
  re-order once; a partial write is a follow-up if that shows up in frame timings.
- Minimap and collision data were already derived from the asset and committed state, not from the
  rebuilt mesh, so they are not part of this change. No live check was made.
- A GPU-level test of `set_hidden` does not exist; the CPU draw-range and ordering state is tested.

## Validation (what actually ran)

Passed: `cargo fmt --check`; `cargo clippy -p rustclient-scene -p rustclient-renderer
-p rustclient-application --all-targets -- -D warnings`; `cargo test` for `rustclient-scene`,
`-renderer`, `-application`, `-cache`, `-native`; `cargo check -p rustclient-web --target
wasm32-unknown-unknown`; `cargo run --release --bin scene_probe -- <cache> --updates`.

Blocked outside this change: `cargo clippy --workspace --all-targets -- -D warnings` fails in
`crates/game-state/src/operation.rs` (identical match arms) in another uncommitted working-tree
change, so the strict workspace run did not pass. `cargo clippy -p rustclient-native` without
`-D warnings` reports only that diagnostic.
