# ADR-0006: Plugin API 0.2 — Rust-native, RuneLite-readable

**Status:** Accepted for implementation (2026-10-25)

## Context

API 0.1 exposes a tick, one player position, string config and overlay text lines, and enforces three of its ten
capabilities. The always-on GPU and RuneLite core plugins are in-tree Rust, and a first batch of thirteen RuneLite
plugins (roofremoval, animsmoothing, stretchedmode, fps, statusbars, interfacestyles, devtools, entityhider,
npchighlight, playerindicators, idlenotifier, camera, menuentryswapper) needs actor, widget, variable, menu, camera and
scene data that the API cannot express. RuneLite reaches that data by holding mutable client objects inside the same
process; that model is rejected by invariants 1 and 4.

## Decision

1. **Snapshots and events, not live objects.** Plugins observe immutable, versioned records (`PlayerSnapshot`,
   `NpcSnapshot`, `WidgetSnapshot`, `MenuEntry`, `ChatLine`, `ClientSnapshot`, `SkillSnapshot`, variable values) and
   `GameEvent` batches. Names follow RuneLite (`GameTick`, `NpcSpawned`, `AnimationChanged`, `MenuEntryAdded`,
   `VarbitChanged`, `StatChanged`, ...) so RuneLite plugin code reads naturally when ported.
2. **Hooks are data returned by the plugin, not callbacks into the renderer.** A plugin returns a `PluginOutput`
   per frame/tick: overlay primitives, notifications, info boxes, menu swaps, an entity-visibility rule set, a sprite
   override set, camera commands and scene flags. The host validates each item against capabilities and bounds before it
   reaches an owner. This keeps ABI crossings batched and keeps the renderer free of plugin callbacks in its hot path.
3. **`GameState` stays authoritative.** A projection crate, `rustclient-plugin-bridge`, builds snapshots from
   `GameState` (and cache definitions for names/compositions) and derives change events by diffing consecutive
   projections. It owns no semantic state of its own except the previous projection used for diffing, which is
   discarded on session end. Where a semantic owner is missing (hitsplats, menu model) the owner is extended, never
   shadowed by the bridge.
4. **Additive versioning.** `PluginApiVersion` becomes `0.2`. A `0.1` plugin loads unchanged: `PluginInstance`
   gains defaulted methods only; the three `PluginEvent` variants are unchanged and still delivered.
5. **Capabilities are enforced at the host boundary for every query, event class and output kind.** New capabilities
   are least-privilege splits, not shortcuts for first-party plugins. First-party in-tree plugins receive the same
   grants from the same manifest path.
6. **WIT.** `wit/rustclient/plugin-0.2` is the cross-language contract for the same records, events and outputs. It is
   parse- and bindgen-checked in CI. The wasm runtime still instantiates the `0.1.0` world; binding the `0.2.0` world is
   follow-up work and is not claimed here. In-tree plugins implement the Rust trait directly and do not cross a Wasm
   boundary (SPEC-0004 already allows this).

## Consequences

- Public records are separate from internal structs, so renames inside `GameState` do not break plugins.
- Diff-derived events cost one projection per tick/frame; the bridge projects only the categories at least one enabled
  plugin is granted, and reuses buffers.
- Event order is deterministic per tick: `ClientTick` before `GameTick` effects, then actor spawn/despawn, change events,
  variable/stat events, widget events, chat, menu.
- Anything a plugin could do through RuneLite's Java API that this API cannot express must have a ledger row with the
  reason (see `docs/reference/builtin-plugins`).
- Roof removal is a declarative scene output (`SceneFlags::roofs` mode bits plus `PluginOutput::roof_areas`). The scene
  crate owns roof regions and their render tags; plugins never see roof ids. A changed area set makes the host rebuild
  the scene projection, not the plugin mutate tile settings as the injected client allows.
- Animation smoothing is a declarative scene output (`SceneFlags::smoothing` class bits plus
  `PluginOutput::smoothing_denied` animation ids), the replacement for `Client.setAnimationInterpolationFilter`. The scene
  crate owns the blend (`AnimationInterpolation`); plugins never see frames or models.
- `ClientSnapshot::scene_base` is the loaded scene's south-west world tile and `scene_size` its extent, so plugins can
  resolve map regions as `Scene.getBaseX/Y` and `getMapRegions` would.
- First-party plugins live in `crates/first-party-plugins`, are native `PluginInstance`s, take settings from
  schema-driven configuration (`PluginCall::config`) and carry an `enabled` switch (RuneLite `enabledByDefault`).
  Their schemas are generated from the vendored `*Config.java` by `tools/builtin-plugins/first_party_schemas.py`.

## Rejected

- Exposing `&GameState` or a read-only clone: second representation of the same semantic concept and a leak of internal
  shape.
- Per-renderable callbacks for entity hiding: thousands of ABI calls per frame.
- Binding 0.2 only through WIT first: blocks first-party ports behind runtime maturity work that ADR-0001 already
  schedules separately.
