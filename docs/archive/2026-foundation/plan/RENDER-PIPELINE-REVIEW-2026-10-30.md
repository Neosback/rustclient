# Rendering pipeline review: frame pacing and memory (2026-10-30)

> Historical checkpoint, archived 2026-11-01. Status, counts, commands and “next” instructions below apply to the recorded snapshot. Use the [current roadmap](../../../plan/PORTING-ROADMAP.md) for new work. Links were relocated; original content hashes are in the [archive manifest](../../MANIFEST.json).


Checkpoint for a user-requested review of dropped frames, especially when a door opens or
closes. It builds on the [update-system latency review](../../../reference/UPDATE-SYSTEM-2026-10-30.md) and
[prepared scene updates](../../../reference/UPDATE-OPTIMIZATION-2026-10-30.md); their open items are not
repeated here. Nothing below was measured on a live client.

## Changed

| Change | Owner | Effect |
| --- | --- | --- |
| Minimap and compass pixels are recomposed only when an input changes: focus, yaw, minimap build, mask sprite, actor dots, destination, local-player visibility. `DynamicImage` gained a `revision`; the renderer skips an upload whose revision and size match, and a recreated texture always uploads. | `apps/native`, `crates/renderer` | An idle or stationary frame no longer allocates and rotates about 90 KB of pixels or re-uploads them. A moving camera or walking player still recomposes every frame. |
| `StaticModel.skeletal_weights` is `VertexWeights`: one contiguous store, nothing per vertex for the common unweighted model. | `crates/cache` (consumers in `crates/scene`) | Removes a 24-byte `Vec` header per vertex (it was the largest per-vertex field). Pixels, poses and decoding are unchanged. Unit test covers equivalence with a vector of vectors. |

## Pasted suggestions that were checked and not applied

- **Remove the CPU opaque sort.** Not applied: the order is face priority and the depth-write
  policy of the 240 client, which a depth buffer cannot reproduce. The sort already reruns only when
  the camera view changes.
- **Move actor lighting to the shader.** Not applied: it would change the 240 integer lighting
  arithmetic. Normals cannot be cached for animated models because posing moves the vertices.
- **Actor scratch-buffer reuse.** Not done: the claimed 4-8 ms `entity build` time is unmeasured and
  `RUSTCLIENT_FRAME_TIMINGS=1` already reports it. Measure first.
- **Retained game-UI projection, transparent draw-call coalescing, raw cache retention trimming.**
  Not done and not verified; the first needs complete script, pointer and drag dependency tracking.

## Open: door smoothness

The synchronous event-thread stall is already reduced by the prepared-update work. A structural
change still rebuilds the whole scene on a worker (about 130-150 ms mesh, about 140 ms preparation)
and publishes a full replacement of a roughly 46 MB static vertex stream. Dependency-correct
per-zone updates (RuneLite `invalidateZone`, TSPS dirty groups) remain the real fix and need a
SPEC-0016 amendment before implementation.

## Validation (what actually ran)

Passed: `cargo fmt --check`; `cargo clippy --workspace --all-targets -- -D warnings`;
`cargo check -p rustclient-web --target wasm32-unknown-unknown`; `cargo test` for
`rustclient-cache`, `-scene`, `-renderer`, `-native`, `-application`, `-game-ui` (326 passed, 0 failed,
6 ignored by default).

NOT RUN: any live frame timing or visual check; GPU upload skip (no test creates a device for it);
`tools/port_audit.py --check`.
