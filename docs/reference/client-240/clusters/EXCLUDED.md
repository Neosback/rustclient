# Triaged out of scope (`not-needed`)

Classes recorded here are deliberately not named. A wave should not re-triage them. Add a row with the
stable symbol, a one-line reason and the evidence (the method or import that shows the role).

## Categories that qualify

- AWT, applet, Swing or other desktop-window glue.
- The software rasterizer and its pixel buffers (RustClient renders through wgpu).
- Jagex Mobile / platform-specific input backends.
- Sound-platform backends (the semantic audio model, if ever needed, is a separate cluster).
- RuneLite-only injection shims: `@Export` hooks, callbacks, mixin glue.
- The launcher and applet parameter plumbing.

Renderer-agnostic code (protocol, cache definitions, model and scene data, UI, scripts, pathfinding,
movement) is never excluded on these grounds.

## Rows

| Stable symbol | Reason | Evidence |
| --- | --- | --- |
