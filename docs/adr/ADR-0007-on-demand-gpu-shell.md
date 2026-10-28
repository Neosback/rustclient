# ADR-0007: On-demand GPU shell panels

Status: implementing, 2026-10-28. Supersedes the native CPU-paint requirement in
SPEC-0007, following the user's request to replace the sidebar's costly hover path.

The retained Dioxus/Blitz panel avoids unchanged-frame work, but the current release
trace still measures 14.87 ms p95 per cross-row hover transition at 2x DPI. It performs
120 layouts and CPU rasterizations for 120 transitions. This competes with the world
frame budget. An unchanged target costs 0.0002 ms and produces no repeated paint;
retention alone does not solve the changed-hover cost.

Use egui 0.37 and egui-wgpu 0.37 with the existing wgpu 30 device. Egui passes run on
input/model/appearance changes and requested deadlines, independently of world frames.
Render changed panels into one retained GPU texture, then reuse that texture during
world rendering. Hidden panels do no UI passes. Keep the same typed catalog, accepted
configuration owner, validation/persistence path, capabilities and versioned plugin API.
No renderer handles or toolkit contexts reach external plugins.

The component and render adapter compile for native/WebGPU. Existing browser DOM
mounting remains available during integration; a compile check is not playable browser
acceptance. Preserve native text/IME/clipboard, scroll/drag, keyboard and focus behavior,
compact RuneLite-style rows, search, sections, favorites, tool rail and reset controls.

Retain bounded texture snapshots and draw data to reconstruct GPU resources. Unchanged
panels must perform no layout/tessellation, GPU rendering or uploads. Measure the same
hover workload before promoting this backend. Report GPU draws/uploads separately from
CPU pass/tessellation counts. No performance result is inferred from framework choice.
