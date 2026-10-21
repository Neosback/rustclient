# Model Scope

`crates/model` owns dependency-light semantic value types shared across client subsystems.

## Rules

- Keep this crate independent of protocol, cache, game-state, renderer, UI, plugins, and platform adapters.
- Do not encode packet bit widths, opcodes, cache binary layouts, scene dimensions, server addresses, or renderer conventions here.
- Distinguish coordinate spaces with different Rust types. Never use one generic point for world, scene, fine/local, model, screen, or GPU space.
- Distinguish runtime entity indexes from definition/resource IDs.
- Represent semantic absence with `Option<T>` at the owning field; do not bake revision-specific sentinel values into ID wrappers.
- `Revision` identifies compatibility era/version. Full `ServerProfile` configuration belongs to the profile/protocol layer, not this crate.
- Game ticks are semantic ordinals. Do not hard-code wall-clock tick duration in `GameTick`.
- Prefer checked conversions at coordinate/range boundaries. Do not silently wrap overflow.
- Renderer vectors, matrices, quaternions, colors, device handles, and `wgpu` types are forbidden here.
- New shared primitives need focused boundary/property tests and a corresponding update to `SPEC-0012` when semantics change.

## Reference evidence

Useful pinned reference paths include:

- `references/rustosrs/crates/osrs-core/src/coords.rs`
- `references/rustosrs/crates/osrs-core/src/ids.rs`
- `references/rustosrs/crates/osrs-core/src/orientation.rs`
- `references/rustosrs/RuneLite-melxin/runelite-api/src/main/java/net/runelite/api/coords/`

Reference code is evidence only. RustClient's model contract is defined by its own specs and tests.
