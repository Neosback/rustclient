# Full-viewport widget alpha overlay — 2026-10-27

The classic GPU's `alphaOverlay` now has a game-UI producer and shared native/browser consumer.
This implements the scoped overlay obligation; source/runtime visual acceptance remains open.
See [the GPU ledger](gpu-audit.json) for the ten remaining obligations and
[the sparse-map checkpoint](GPU-SPARSE-MAPS-2026-10-27.md) for the accompanying loader repair.

## Exact source

Injected-240 `classGP.method5624` scans visible siblings for filled solid type-3 rectangles whose
bounds equal the world viewport and whose transparency is nonzero. It accumulates premultiplied
encoded RGB with weights `256 - transparency` and `transparency`, using shifts by eight. Alpha
uses the source's integer product/unsigned shift; two 128-transparent red/blue rectangles yield
RGBA `(63,0,127,191)`, not an ordinary rounded straight-alpha blend. It temporarily hides the
extracted widgets for painting, while its cleanup restores them. `Scene.method4508` resets the
accumulator when drawing the scene. `GameEngine.method11153` forwards it to the draw callback.

`GpuPlugin.drawUi` unpacks that premultiplied value into `alphaOverlay`. `fragui.glsl` composites
sampled UI over it, applies UI color correction, then uses premultiplied blending over the world.
It is not a screen tint painted above menus. These are RuneLite injection/GPU semantics, not a
claim about vanilla software rendering.

## Rust adaptation

`game_ui::ViewportOverlay` owns bounded byte accumulation in disposable `UiFrame` metadata.
`widget_visuals` selects eligible rectangles against the current shaped viewport, including
letterboxing, and omits their quads. Input/layout nodes and GameState are untouched. Hidden widgets
are already excluded by the shared layout. Every projection resets the accumulator; title/loading/
diagnostic projections clear it as well. No global state or temporary semantic-widget mutation
reproduces the source's implementation mechanics. Unsupported gradient/fill modes are outside
the retained solid-rectangle subset and still belong in the widget port matrix.

`renderer::ui_canvas` uploads normalized premultiplied bytes in its existing uniform. The block is
now 64 bytes, with the overlay at offset 48; portable Naga/host size checks pass. The shared WGSL
blends it beneath the sampled canvas before color correction and world composition. The color
is not multiplied by alpha again. Shell chrome is independent. This adds no target, render pass,
shader variant or hot-path allocation. Unchanged uniform data does not upload again.

The source uses an interface-group prepass and viewport globals; Rust resolves visible widgets
in the current projection and extracts matching rectangles encountered after its world widget.
Unusual source group ordering and authored fade interfaces still need live comparison. No claim
of complete widget paint-order parity follows from this bounded port.

## Executed evidence

- Semantic contracts: exact source bytes, eligible/ineligible rectangles, shaped viewport rejection,
  projection reset/removal, preserved input nodes and unchanged GameState fingerprint pass.
- Actual Metal canvas: opaque UI covers the overlay; translucent UI combines once with premultiplied
  overlay; transparent UI reveals it. All six scaling modes preserve a constant overlay. Color
  correction changes it. Existing cache title 1x/2x and real sparse-region GPU diagnostics still pass.
- Actual browser WebGPU: **31 checks pass**, including the eight new overlay checks, shared world/UI
  shader compilation, native/browser scaling comparisons (maximum channel difference one), source
  zone admission and no uncaptured GPU errors. Only core device features were enabled.
- Browser report: ignored `target/gpu-overlay-browser.json`; visible pass screenshot:
  `target/gpu-overlay-browser.png`. Native diagnostic: `target/gpu-overlay-probe.log`.
- `tools/gpu/overlay_oracle.py` hash-checks `classGP.java`, extracts its literal arithmetic block
  and executes it in an isolated Java harness. All four named vectors match: single red, red/blue
  accumulation, white with transparency 1 and white with transparency 255. The first three agree
  with the Rust contract assertions. The generated Java/result JSON live under ignored
  `target/gpu-overlay-oracle/`. This is arithmetic evidence, not gamepack or shader execution.

Reproduce with `tools/gpu/README.md`. Full workspace gate results are in the owning plan.
Full Java shader/source-case execution/pixel MATCH, live account fades, three gameframe modes and playable
browser lifecycle remain NOT-RUN. These checks do not establish complete classic GPU or 117HD readiness.
The reference-MCP status request returned connection refused; the offline bridge protocol check passes.
