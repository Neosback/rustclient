# ADR-0009: One renderer, selectable render profiles

Status: Proposed, 2026-11-06. Nothing here is implemented beyond the capability-negotiation core named
under "Current state". The 117HD source is not yet in the repository, so no HD behavior is specified here.

## Context

RuneLite's GPU plugin and 117HD (RLHD) both implement `DrawCallbacks` and install themselves with
`client.setDrawCallbacks(this)` (`references/open-runelite-rev-240/.../gpu/GpuPlugin.java`, lines ~372,
420-449). The client has one such slot, so only one can own scene drawing; 117HD declares a plugin
conflict with GPU. The conflict is a symptom of injection: the renderer is a plugin that replaces the
client's drawing. The 117HD side of this is unverified until its source is available.

RustClient has no injection. One wgpu renderer (`crates/renderer`) draws from `GameState`-derived
projections, native and WebGPU. A planned 2008HD plugin and the existing GPU settings need the same
things: shaders, passes, settings, resource budgets, and honest reporting of what the device can do.

## Decision

1. There is one renderer and it is never replaced by a plugin. A **render profile** is a value the
   renderer owns: an id, required and optional device capabilities, a pass-graph builder, shader modules,
   a typed settings type, and resource budgets. Profiles draw the same scene and actor representation; no
   profile keeps a second copy of semantic or scene data.
2. "GPU" (the existing classic path, behavior unchanged) and "117HD" are profiles. A future 2008HD is
   another. Exactly one profile is active. Selecting one is an operation of the renderer's owner, not a
   plugin conflict flag: the shell shows the profiles as mutually exclusive and says why.
3. First-party "plugins" for profiles (GPU, 117HD, 2008HD) are configuration surfaces: they own the settings
   schema and map accepted configuration to the profile's settings type, through the existing configuration
   and shell machinery. They hold no GPU handles. External plugins get typed render-policy services (select a
   profile, read and write accepted settings, supply area and environment data) and never receive GPU
   handles or shader source.
4. Capabilities are negotiated, never assumed. Each profile declares what it needs. Negotiation against the
   created device yields supported, degraded (with a reason per lost option) or unsupported (with a reason).
   Option resolution continues the `GpuSettings::resolve` pattern: requested versus effective, shown to the
   player. The WebGPU baseline (core limits only) must run a reduced profile; native may request more
   (larger limits, compute, f16, timestamps) behind the same checks.
5. Classic behavior is regression-guarded: the classic profile is the current renderer path, and the existing
   renderer tests and captures must pass unchanged.

## Consequences

- Adding an HD profile is data (tables), shaders, a settings schema and a pass-graph builder, not renderer surgery.
- Switching profiles rebuilds pipelines from the same scene; it does not reload the map.
- HD material paging and budgets stay explicit (a full-mip 256-layer 128 px array is about 21.3 MiB; at 1024 px
  about 1.33 GiB before normals and shadows, see GPU-AUDIT).
- Ported RLHD files keep their BSD-2-Clause notice; deviations are recorded in PARITY-DEVIATIONS.
- Visual parity and performance cannot be established without a GPU session; they are recorded NOT-RUN.

## Alternatives

- Each HD plugin ships its own renderer: duplicates scene upload, settings and tests, and reintroduces the conflict.
- Keep RuneLite's hard conflict between plugins: no sharing, and the reason is a limitation we do not have.

## Current state

`crates/renderer` has the classic path, `GpuSettings` with `resolve`, and `GpuCapabilities`. The profile
descriptor and the negotiation core (SPEC-0030) are the first code under this decision.
