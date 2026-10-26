# Sidebar visibility and port audit checkpoint

Scope: latest requested closed-sidebar behavior and a current source/implementation audit. The full
GPU/core delivery remains in progress; this checkpoint does not complete all behavior in those plugins.

## Changes

- One native shell visibility value controls the panel and far-right tool rail. Both close together;
  a closed shell reserves zero width, paints no panel texture and cannot consume game pointer input.
  Required plugins stay active. Existing RuneLite-style fallback arrow and configured hotkeys reopen it.
- The retained component removes both rail and panel when closed. The 1x/2x native DOM contract covers
  full-height 242+36 layout, close/reopen and hidden/unchanged zero-work behavior. The instrumentation
  now counts both layout passes when embedded resources require a second resolve.
- The new 42-domain port matrix has canonical TSV input, generated Markdown, checked owner/evidence
  paths, explicit remaining obligations, and no inferred completion percentage. The current audit
  records actor/animation/menu/chat/world-map/world-view/browser gaps and corrects stale completion wording.
- Reviewed 13 supplied TypeScript files are hash-bound. The reference-root check is optional and read-only;
  the checker and all audit data are in the repository. References remain unchanged.
- The 235-to-240 guide no longer treats an unlisted upstream change as proof a method stayed identical.
  Selected RSProt 240 independently confirms outgoing public-chat style fields; that source-entry hash
  is recorded in protocol fixture provenance. Font/menu/heading symbol records now describe their tested
  subset and retain remaining obligations instead of claiming whole-method parity.

## Verification

Passed with Rust 1.99.0:

- `cargo fmt --all --check`.
- `cargo check --workspace --all-targets`.
- `cargo clippy --workspace --all-targets -- -D warnings`.
- `cargo test --workspace`: 516 passed, zero failed, one ignored live-capture replay test requiring
  `RUSTCLIENT_PACKET_REPLAY` and `RUSTCLIENT_PACKET_LOCAL` input.
- `cargo check -p rustclient-web --target wasm32-unknown-unknown`.
- `cargo build -p rustclient-native --bin rustclient-native`.
- `python3 tools/port_matrix.py --check --tsps-root /path/to/tsps-main/client`: 42 domain rows,
  owner/evidence paths and 13 supporting source hashes verified without source changes.
- `python3 tools/port_audit.py --check`: 133 method/field/class ledger rows validated; unrecorded
  verification and the partial source pin list remain explicit audit gaps.
- Audit document links, generated matrix freshness, Python syntax and `git diff --check` passed.

Visual observation: inspected the native Dioxus/Blitz raster at `target/sidebar-configuration.png`.
It shows the compact flat configuration list left of the far-right rail. The real native DOM tests
verify full-height layout at 1x/2x, hidden rail/panel removal and idle pixel-cache reuse.
This is not a logged-in gameframe comparison. Local build/test logs are under `target/sidebar-audit-*`;
these are diagnostic outputs, not committed golden fixtures.

## Remaining desktop acceptance

No new logged-in fixed/resizable classic/resizable modern acceptance has been recorded for this
checkpoint. The user's earlier report of a hidden minimap/shifted mouse coordinates must be retested in
an executable containing the corrected game raster viewport and asynchronous shell resize handling.
The standalone retained-panel PNG is useful UI review, not proof of live HUD/picking or pathfinding.

Broader source-port obligations remain in [CURRENT-AUDIT.md](../reference/rs240-port/CURRENT-AUDIT.md)
and [PORT-MATRIX.md](../reference/rs240-port/PORT-MATRIX.md). No speculative NPC collision clamp, second
scene/actor authority, or revision-235 packet/cache table was introduced by this checkpoint.
