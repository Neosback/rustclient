# SPEC-0030: Render profiles and capability negotiation

**Status:** Proposed, 2026-11-06 (see [ADR-0009](../adr/ADR-0009-render-profiles.md)). Only the negotiation
core and the profile selection rule are specified; HD passes, materials and settings wait for the 117HD audit.
**Owner:** `crates/renderer` (profile descriptors and negotiation); configuration and shell stay with
`crates/application` and `crates/shell-ui`.

## Scope and invariants

- A profile is identified by a stable id (`classic` for the "GPU" plugin's path, `hd117` for 117HD, reserved
  `hd2008`). Ids are data, not an enum of code paths, so profiles can be added without touching selection.
- Exactly one profile is active. `ProfileSelection::select` replaces the active profile and reports the one it
  replaced so the shell can say "117HD replaced GPU". Selecting the active profile is a no-op.
- A profile states `ProfileRequirements`: minimum limits (texture dimension, array layers, storage buffers per
  stage, compute), required features, and optional features that improve quality. Requirements are checked
  against `GpuCapabilities`, the capabilities of the device actually created, never the adapter's.
- `negotiate` returns `Supported`, `Degraded` (with one reason per optional capability missing) or `Unsupported`
  (with the first unmet requirement). It never panics and never guesses a missing limit.
- Nothing in a profile reaches plugins as a handle. Render-policy services are typed values.

## Non-goals of this milestone

HD pass graphs, materials, lighting, shadows, water, sky and post-processing; the 117HD settings schema; the
render-policy plugin API. Those need the 117HD source and have their own plans.

## Acceptance

- Unit tests with synthetic capabilities: the WebGPU baseline accepts the classic profile; a profile that needs
  more array layers than the device grants is `Unsupported` with the layer count in the reason; a profile whose
  optional compute or f16 support is absent is `Degraded` and names each; selection reports the replaced profile.
- The classic renderer tests and captures are unchanged.
- Live and visual acceptance: NOT-RUN.
