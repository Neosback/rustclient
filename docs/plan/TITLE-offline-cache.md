# Offline cache-backed title checkpoint

<!-- authority-notice -->
> Historical checkpoint and evidence record. Implementation status, “next” tasks, counts
> and validation below apply to the recorded snapshot. Current work follows
> [PORTING-ROADMAP.md](PORTING-ROADMAP.md) and the [domain matrix](../reference/rs240-port/PORT-MATRIX.md).

**Status:** Complete for SPEC-0013's desktop/offline scope, 2026-10-21.

Implementation is an uncommitted working-tree change based on RustClient commit
`2a164db5a09aa3d406916f0e67bedfc012209041`. This is not a release or a completion
claim for general cache definitions, networking, widgets or browser delivery.

## Delivered behavior

- Explicit `--cache-dir` / `--cache-profile osrs-240` selection and read-only
  bounded range access; native worker loads the CPU asset set.
- JS5 storage/container/table/file pipeline, immutable sprites/JPEG/fonts,
  named title assets and local inspection with source hashes.
- Shared GPU quad pipeline, atlas, clipped glyphs/sprites and dynamic rune flames.
- Welcome/credential/local-information panels; editing and validation, masking,
  remember/hide preferences, no semantic game session or network authentication.
- Non-secret title state in `GameState`, typed transitions, event/delta/replay
  support and schema-v2 canonical fingerprints. Password drafts never leave UI.
- Surface/device-loss recovery signals reuse composition-root reconstruction.

## Validation actually run

All commands completed successfully on the local macOS host:

```sh
cargo fmt --all --check
cargo check --workspace --all-targets
cargo clippy --workspace --all-targets -- -D warnings
cargo test --workspace
cargo check -p rustclient-web --target wasm32-unknown-unknown
cargo run -p xtask -- cache inspect --cache-dir /Users/tylercovalt/Desktop/LIVE --cache-profile osrs-240
cargo run -p rustclient-native -- --cache-dir /Users/tylercovalt/Desktop/LIVE --cache-profile osrs-240 --capture-dir target/title-captures
```

The workspace suite passed **83 tests**, zero failures/ignored tests. Coverage
includes malformed/truncated containers and tables, allocation bounds, trailing
compressed bytes, large group headers, CRC/version errors, cyclic chains,
multi-file chunks, sprite flags/offsets/alpha, bitmap metrics, LIVE graphics
opcodes, advisory encoded sizes versus enforced decoded sizes, preference
persistence/corruption, input bounds, masking, resize/HiDPI hit testing, title
replay/fingerprints and clipping/mirrored UVs.

The flame regression uses explicit SplitMix64 seed 240, 300 steps and authored
synthetic rune masks, including a noise reseed. RGBA SHA-256:
`1a566d543bfb159c085df93b97aef792e900d459e10c357d4db26943f7911c0c`.
This freezes the Rust port's cosmetic output, not a Java RNG parity claim.

LIVE inspection validated the 17 required title source files and all present
reference tables. Metadata snapshot SHA-256:
`c87f4c26b48a0bbf58b1c924a4f429bf03b0124fd6fc77837877601005f2bc79`.
See SPEC-0013 for the encoded-size hint policy and revision identity limitations.
No other asset payload family was validated by that inspection.

`target/title-captures/` contains welcome, credentials, populated offline and
HiDPI PNGs plus `evidence.txt`. Captures use the actual shared GPU pipeline with
seed 240/step 150. Recreating every GPU resource yielded byte-identical welcome
pixels while preserving the authoritative state fingerprint. Welcome and offline
captures were visually reviewed against the reference's layout/asset semantics;
the overlapping password label found during review was corrected. These are not
pixel-diff comparisons against a running Java client.

A live native window was launched with the real cache. Computer-use inspection
observed the welcome screen and an interactive credential form reaching the
masked-password offline status. A disposable macOS app wrapper under `target/`
was used only to expose the running development binary to UI inspection; it is
not distribution packaging. Test preferences are under
`target/title-smoke-preferences/`, separate from personal preferences.

## Evidence and remaining limits

RustOSRS remains clean at gitlink `854a3962164ad401d21064a5f207f86869ea69b4`.
RuneLite 235 behavior references are recorded with fixture provenance; the
inspected Java files matched checkout `1ad572d7dcdbc0fb67a4a00f0c2f959d5ab25abc`.
Neither reference source nor the LIVE cache was modified.

Browser runtime/import, Linux/Windows runtime, actual driver/device fault
injection, and an authenticated server connection were **not tested**. GPU
resource reconstruction was tested; physical driver-loss recovery was not forced.
Extended font metrics, XTEA maps, music, world selection, general widgets, CS2,
models/terrain and protocol/profile expansion remain deferred. CI was not run by
this local checkpoint. Local check output is retained in `target/title-*.txt`;
those build artifacts are intentionally untracked.
