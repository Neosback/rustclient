# Graphics feature menu: beyond OSRS and 117HD

**Status:** Proposal  
**Owner:** renderer  
**Target:** RustClient shared `wgpu` renderer  
**Reference implementation:** pinned RustOSRS HD profile (`references/rustosrs/crates/osrs-render/src/gpu/hd/`)  
**External evidence:** HDOS 751 shaders, Matrix 876 GLSL

## 1. Purpose

Catalog the graphics techniques available from HDOS and Matrix 876, plus
techniques neither client ships, that can become **opt-in** RustClient graphics
settings. The target is not OSRS parity or 117HD parity. It is a tiered set of
presentation features that a player can enable on capable hardware:

- **Classic** keeps the current reference renderer byte-for-byte.
- **Enhanced** adds cheap, high-impact post and atmosphere.
- **HD / Ultra** adds lighting, water, shadows and screen-space effects.
- **Cinematic** adds temporal and volumetric effects for screenshots/cutscenes.

The renderer remains a projection of committed semantic state. Every feature
below is presentation-only: it may read `RenderWorld`, camera and environment
data, but it may not mutate `GameState` or own gameplay resources.

## 2. What already exists

### RustClient today

`crates/renderer/src/` is a classic reference renderer:

- `world_target.rs`: `Rgba8Unorm`, 4x MSAA, single-sample resolve, display
  conversion in a composite triangle.
- `world.wgsl`: baked HSL/lightness vertex colour, texture array, no directional
  relighting, no post.
- `world.rs`: one opaque and one alpha-blended pipeline, reverse-Z
  (`Depth32Float`, compare `Greater`), no offscreen HDR path.
- `ui.wgsl` / `ui.rs`: straight-alpha sRGB UI quads with nearest sampling.

This is the correct baseline. Do not turn it into an HD renderer in place; add a
separate presentation profile that can be selected at runtime and rebuilt from
the same `RenderWorld`.

### RustOSRS HD (pinned reference)

The pinned reference already contains most of the 117HD foundation:

| Area | State in reference |
|---|---|
| HDR offscreen target | `Rgba16Float` (`hd/mod.rs`) |
| Frame graph | pass declarations, transient pooling (`gpu/frame_graph.rs`) |
| Materials | texture array, diffuse/normal/height/roughness/specular/AO, ground blending, parallax |
| Terrain/model extraction | HD vertex stream, procedural normals, water depth |
| Environments | per-area ambient/directional light, fog, ground fog, lightning |
| Shadows | directional shadow maps, PCF/PCSS-style filtering, edge fade |
| Water | normal/flow/foam/Fresnel/specular, underwater tint, caustics, draw-distance clamp |
| Sky | gradient + sun/moon disks stub; stars/aurora/time-of-day not done |
| Post | exposure + vignette placeholder; no tone mapping, bloom, LUT or AA |
| Point lights | placed lights only, not object/NPC/projectile lights |
| Settings | `HdSettings` exists but is not user-settable |

So the menu should spend effort on **what 117HD does not do**, and finish the
parts of 117HD that the reference has not yet ported.

## 3. Source inventory

### HDOS 751 (`hdos-client/shaders/hdos/shader/`)

| File | What it contains | Reusable idea |
|---|---|---|
| `main.vert` (305 lines) | bone matrices (1024), skin colour/alpha transforms (512), vertex lights (1024), heightmap alignment, depth-shift projection, texture arrays | GPU skinning, height snapping, depth bias, per-vertex lights |
| `main.frag` (523 lines) | 2048 material configs, 1023 lights, specular cube maps, fluid jiggle, high-detail water waves, waterfalls, emissive, fog, region-locker fog, underwater | Emissive/bloom tags, light loops, region fog, water/underwater model |
| `water.vert` / `water.frag` | surface-only water pass, colour from water-depth texture, ambient/diffuse tint | Cheap separate water pass |
| `bloom.vert` / `bloom-test.frag` | fullscreen bloom threshold (luminance `step`) | Bloom threshold |
| `bloom-gaussian-blur.frag` | 17-tap separable Gaussian, exact weights | Bloom blur |
| `bloom-combine.frag` | luminance tone curve + additive bloom | Bloom composite |
| `shadow.vert` / `shadow.frag` | baked shadow map as a texture array (8x8 or 16x16 tiles), alpha-black, fog | Baked static shadows |
| `particle.vert` / `particle.frag` | point sprites, material array, ambient flag, emissive, fog | Particle batching, soft particles |
| `tile.vert` / `tile.frag` | coloured tile geometry, depth shift, fog | Ground decals/overlays |
| `gui.frag` / `gui-cubic.frag` | UI quads; Catmull-Rom bicubic 4x4 sampling with anti-ringing (BSD, logarrhythmic) | Crisp non-integer UI scaling |
| `mmpx.comp`, `mmpx.frag`/`mmpx.vert` | 2x pixel-art magnification, compute and fragment variants (BSD, Brainfree) | Retro/UI upscaling |
| `mc.vert` / `mc.frag` | minimal rigid bone-skinned model pass | GPU skinning reference |
| `board.vert` / `board.frag` | textured board with material/bloom config | Emissive UI/board example |

### Matrix 876 (`Matrix 876/extracted-shaders/`)

| File | What it contains | Reusable idea |
|---|---|---|
| `bloom__aString11606.frag` | 17-tap separable Gaussian | Same kernel as HDOS |
| `bloom__aString11611/11614.frag` | luminance threshold (`step`) | Bloom threshold |
| `bloom__aString11601.frag` | luminance tone curve + bloom add | Bloom composite |
| `hsl_remap__linked_1..6.frag` | 2D-strip and 3D LUT colour remap; 2/3/4-channel variants | 3D LUT colour grading |
| `gamma_range__aString11404.frag` | black/white point, gamma, output black/white | Display calibration |
| `water_clean__aString8963.frag` | 3D normal map, cube-map reflection, Fresnel, sun specular, shore factor | Cube-map water/armour reflections |
| `water_waves__aString8897.frag` | wave-intensity blend on the same water | Stylised wave bands |
| `heightmap_normal__aString1737.frag` | heightmap to normal map | Runtime terrain normals |

### Local evidence locations

- HDOS GLSL: `/Users/tylercovalt/Documents/Default Project/hdos-client/shaders/hdos/shader/`
- Matrix 876 GLSL: `/Users/tylercovalt/Documents/Matrix 876/extracted-shaders/`
  (extracted from Java string literals by
  `Default Project/extract_matrix_shaders.py`)
- RustOSRS reference: pinned at `references/rustosrs/`; the live working copy is
  `/Users/tylercovalt/Documents/RustOSRS/`

### License reality

- **HDOS** is proprietary/closed-source. The shaders are behavioural reference;
  reimplement the technique, do not copy the source text into a distributed
  build without permission.
- **Matrix 876** is a deobfuscated Jagex client from a private server. Its shader
  strings are Jagex-derived; same caution.
- **117HD** is BSD-2-Clause. The reference port already carries attribution in
  `THIRD_PARTY.md`; reuse it with the notice intact.
- **`gui-cubic.frag`** and **`mmpx.comp`** carry BSD notices in the source.
  Retain them if ported.

Practical rule: write the WGSL yourself from the mathematics, not by translating
the GLSL line-for-line.

## 4. Feature menu

Cost is for a 1440p-class frame on a discrete GPU; "cheap" is under ~0.3 ms,
"medium" ~0.3–1.5 ms, "heavy" 1.5–5 ms, "very heavy" >5 ms. These are planning
numbers, not measurements.

### Tier 0 - foundation (required by most tiers)

| ID | Feature | Source | Why it matters | wgpu work | Cost |
|---|---|---|---|---|---|
| F0 | HDR scene target + linear pipeline | 117HD reference | Bloom, tonemap, LUT and AA all need values above 1.0 | `Rgba16Float` MSAA target, resolve, final pass; linearize scene output | cheap |
| F1 | Post chain / frame graph | RustOSRS reference | Ping-pong passes, resize, pass ordering, transient reuse | port `frame_graph.rs` patterns | cheap |
| F2 | Graphics settings model | new | Toggles, presets, capability fallbacks, persistence | `GraphicsSettings` in shell UI + renderer | cheap |
| F3 | Depth/normal prepass | new | SSAO, SSR, TAA, DoF, contact shadows need view-space data | extra depth (+ normal) target, or reconstruct | medium |
| F4 | Material metadata | HDOS | Emissive/bloom strength, specular mode, water flags, light flags | extend material storage buffer | cheap |

### Tier 1 - cheap, high-impact ("Enhanced")

| ID | Feature | Source | Why it matters | wgpu work | Cost |
|---|---|---|---|---|---|
| E1 | Emissive materials + bloom | HDOS `main.frag`, Matrix bloom | Lava, spells, crystals, torches glow | material `bloom_strength`, threshold + blur + add | cheap |
| E2 | 3D LUT colour grading | Matrix `hsl_remap` | Cinematic/seasonal/colorblind looks in one texture | `texture_3d`, 16³ or 32³ LUT, tetrahedral sample | cheap |
| E3 | Gamma / black-white point | Matrix `gamma_range` | Proper display calibration and mood | fullscreen math | negligible |
| E4 | Region-locker fog | HDOS `main.frag` | Cave/region atmosphere, fog walls, interior haze | 16 rects + params in uniform, per-fragment loop | cheap |
| E5 | Height/ground fog | 117HD + HDOS | Depth in valleys, mist over water | analytic exponential fog | cheap |
| E6 | Cube-map specular | HDOS `spec1..3` | Metallic armour and water highlights without SSR | cube textures, transformed normal | cheap |
| E7 | Bicubic UI upscaling | HDOS `gui-cubic` | Crisp UI at 125/150/175% scale | 4x4 Catmull-Rom in UI shader | cheap |
| E8 | FXAA / CAS | neither | Cheap AA and sharpening for non-TAA presets | fullscreen LDR pass | cheap |
| E9 | Vignette, grain, chromatic aberration | neither | Film look, screenshot mode | fullscreen LDR math | negligible |
| E10 | Emissive texture fallback | HDOS | Glow without HDR/bloom | multiply texture by strength | negligible |
| E11 | Outline / hover highlight | HDOS `main.frag` | Clean selection and interaction feedback | per-draw colour uniform, stencil or mask pass | cheap |

### Tier 2 - medium ("HD / Ultra")

| ID | Feature | Source | Why it matters | wgpu work | Cost |
|---|---|---|---|---|---|
| M1 | Baked terrain lightmap + dynamic lights | HDOS `lightTexture`, 1023 lights | Torches, caves, spells light the world | light index texture/storage, light loop, clustering | medium–heavy |
| M2 | Light culling (clustered/tiled) | new | Makes many lights affordable | compute pass, light grid, storage buffers | medium |
| M3 | Planar water reflection | new | Real reflections of trees/skies | mirrored camera pass + clip plane + reflection texture | heavy |
| M4 | Water refraction + depth foam | 117HD + Matrix | Shorelines and underwater distortion | grab scene colour, sample with normal offset, depth compare | medium |
| M5 | HDOS high-detail water | HDOS `main.frag` | World-space 3D water pattern, waterfalls | `texture_3d` projected by world position, animated | cheap |
| M6 | SSAO / GTAO | neither | Contact darkening, grounded objects | depth+normal, hemisphere kernel, bilateral blur | medium |
| M7 | Contact shadows | neither | Small-scale shadow under characters/props | screen-space ray march | medium |
| M8 | PCSS / cascaded shadows | 117HD reference | Soft, stable shadows at distance | cascade selection, blocker search, PCF | medium |
| M9 | GPU skinning | HDOS `main.vert` | Smoother animation, less CPU work | bone storage buffer, rigid vertex bone index | cheap |
| M10 | Heightmap alignment | HDOS `main.vert` | Stops scenery floating/sinking on HD terrain | vertex texture fetch + per-object flag | cheap |
| M11 | Soft/lit particles | HDOS `particle.*` | Rain, smoke, sparks blend with the scene | instanced billboards, depth fade, material array | medium |
| M12 | Weather (rain/snow/splash) | neither | Atmosphere and world reactivity | GPU particles + wetness mask | medium |
| M13 | Sky: stars, aurora, time of day | 117HD phase 7 | Night/day identity | sky shader + environment timeline | medium |

### Tier 3 - heavy / cinematic

| ID | Feature | Source | Why it matters | wgpu work | Cost |
|---|---|---|---|---|---|
| C1 | SSR for water/wet ground/metal | neither | Reflections without a full extra scene pass | depth ray march, fallback to cube map | heavy |
| C2 | Volumetric god rays | neither | Sun shafts through trees/caves | shadow-map ray march or radial blur | heavy |
| C3 | Volumetric clouds / cloud shadows | neither | Living sky | 3D noise, sun transmittance, projected shadow | very heavy |
| C4 | TAA | neither | Stable image at low render scale | jitter, velocity buffer, history, clamp | medium–heavy |
| C5 | FSR 1/2 or CAS upscaling | neither | 1440p/4K from a lower internal resolution | EASU/RCAS or FSR2 integration | medium |
| C6 | Depth of field | neither | Screenshot/cutscene look | CoC from depth, bokeh gather | heavy |
| C7 | Motion blur | neither | Speed and camera motion | camera reprojection or velocity buffer | medium |
| C8 | Lens flare / sun glare | neither | Bright sun character | occlusion query + sprites | cheap |
| C9 | Screenshot SSAA (2x/4x) | neither | Clean marketing screenshots | large offscreen target + downsample | very heavy |
| C10 | MMPX retro mode | HDOS `mmpx.comp` | Pixel-art UI and a deliberate retro look | compute, two storage textures | cheap |

## 5. Deep dives

### 5.1 HDR, bloom and tone mapping

**Pass order**

```
shadow maps
  -> depth/normal prepass (optional)
  -> opaque scene          Rgba16Float MSAA -> resolve
  -> sky
  -> planar reflection (optional, before water)
  -> water / transparent
  -> SSAO (compute/fullscreen) -> blur -> apply
  -> SSR (optional)
  -> TAA resolve (optional)
  -> bloom: threshold -> downsample mips -> upsample
  -> tonemap (exposure, ACES/AgX/filmic)
  -> LUT colour grade
  -> gamma/range, vignette, grain, chromatic aberration
  -> UI
```

Bloom must run on HDR values before tone mapping. LUT grading normally runs
after tone mapping in display-referred space, because LUTs are authored against
a display transform. If a LUT is authored in linear space, move it before the
tonemap; make that an explicit setting rather than guessing.

**Colour-space contract (important).** The classic `world.wgsl` writes
gamma-encoded OSRS colours (`pow(..., 0.8)`) into `Rgba8Unorm`, and the display
pass converts them back for the sRGB surface. That is intentionally an identity
round trip. Do not point the classic shader at `Rgba16Float` and call the result
HDR: `0.5` would suddenly mean half a linear unit instead of mid-grey. Add an
explicit working-space decision:

- **Classic profile**: keep `Rgba8Unorm` + gamma-encoded colours exactly as now.
- **HD profile**: decode material/vertex colours to linear at the point they
  enter the scene shader, light and composite in linear, store linear in
  `Rgba16Float`, then encode sRGB once in the final pass.

The reference HD scene currently writes display-referred colour into
`Rgba16Float` (see the `post.wgsl` comment), so it is not yet a true HDR bloom
source. Fix that before enabling bloom or a filmic tonemap.

**Emissive tagging.** HDOS stores per-material flags in
`bloom_emissive_effect_args`: `x` = bloom texture strength, `y` = non-bloom
emission strength, `z` = effect enum, `w` = effect args. Reuse that shape:

```wgsl
struct MaterialConfig {
    repeat_animation_speed: vec4<f32>,
    // x = bloom strength, y = emissive strength, z = effect, w = effect args
    bloom_emissive_effect_args: vec4<f32>,
};
```

For HDR, an emissive texel should be able to exceed 1.0:

```wgsl
var c = textureSample(tex, samp, uv);
if (m.bloom_emissive_effect_args.x > 0.0) {
    // HDR path: push above 1 so the threshold picks it up.
    c = vec4(c.rgb * (1.0 + m.bloom_emissive_effect_args.x), c.a);
} else if (m.bloom_emissive_effect_args.y > 0.0) {
    // Classic path: visible glow without HDR.
    c = vec4(c.rgb * (1.0 + m.bloom_emissive_effect_args.y), c.a);
}
```

**Threshold with soft knee** (better than the hard `step` in HDOS/Matrix):

```wgsl
struct BloomParams {
    threshold: f32,
    knee: f32,
    intensity: f32,
    _pad: f32,
};
@group(0) @binding(0) var src: texture_2d<f32>;
@group(0) @binding(1) var samp: sampler;
@group(0) @binding(2) var<uniform> p: BloomParams;

@fragment
fn fs_threshold(@location(0) uv: vec2<f32>) -> @location(0) vec4<f32> {
    let c = textureSampleLevel(src, samp, uv, 0.0).rgb;
    let l = dot(c, vec3<f32>(0.2126, 0.7152, 0.0722));
    let w = smoothstep(p.threshold, p.threshold + p.knee, l);
    return vec4<f32>(c * w, 1.0);
}
```

**17-tap separable blur** (HDOS / Matrix weights). Use one pass per axis and
ping-pong; a mip chain gives a wider radius for less bandwidth than a single
huge kernel.

```wgsl
const W: array<f32, 9> = array<f32, 9>(
    0.091396265, 0.088584304, 0.08065692, 0.068989515, 0.055434637,
    0.04184426, 0.029672023, 0.019765828, 0.012369139,
);

@group(0) @binding(0) var src: texture_2d<f32>;
@group(0) @binding(1) var samp: sampler;
@group(0) @binding(2) var<uniform> step: vec2<f32>; // direction * texel

@fragment
fn fs_blur(@location(0) uv: vec2<f32>) -> @location(0) vec4<f32> {
    var acc = textureSampleLevel(src, samp, uv, 0.0) * W[0];
    for (var i = 1; i < 9; i++) {
        let o = step * f32(i);
        acc += textureSampleLevel(src, samp, uv + o, 0.0) * W[i];
        acc += textureSampleLevel(src, samp, uv - o, 0.0) * W[i];
    }
    return vec4<f32>(acc.rgb, 1.0);
}
```

**Composite with a luminance curve** (HDOS `bloom-combine`, Matrix
`bloom__aString11601`). This is a display-referred curve; for an HDR pipeline,
apply it after tone mapping or replace it with a proper tonemap:

```wgsl
let scene = textureSampleLevel(scene_tex, samp, uv, 0.0).rgb;
let bloom = textureSampleLevel(bloom_tex, samp, uv, 0.0).rgb;
let pre = 0.99 * dot(vec3<f32>(0.2126, 0.7152, 0.0722), scene) + 0.01;
let post = pre * (1.0 + pre / params.y) / (pre + 1.0);
let out = scene * (post / pre) + bloom * params.x;
```

Recommended modern chain: threshold -> 5–7 mip downsample (13-tap or dual
filter) -> upsample with additive blend -> composite before tonemap. The
17-tap kernel is a good single-level option and matches the source look.

### 5.2 3D LUT colour grading

Matrix supports both a 3D LUT and a 2D strip. Use a 3D LUT on desktop WebGPU and
keep the strip as a fallback for limited devices.

- 3D LUT: 16³ or 32³, `Rgba8Unorm`, authored in sRGB. Sample with trilinear
  filtering; tetrahedral interpolation is nicer but only needed if 16³ shows
  banding.
- 2D strip: 16 slices of 16×16 in a 256×16 texture; sample the two nearest
  slices and lerp by blue. Matrix uses a half-texel inset to avoid bleeding
  (`0.5/256`, `0.5/16`); keep that.

```wgsl
@group(0) @binding(0) var lut: texture_3d<f32>;
@group(0) @binding(1) var lut_samp: sampler;

fn sample_lut(c: vec3<f32>) -> vec3<f32> {
    let n = 16.0;
    let scaled = clamp(c, vec3<f32>(0.0), vec3<f32>(1.0)) * (n - 1.0);
    let uv = (scaled + vec3<f32>(0.5)) / n;
    return textureSampleLevel(lut, lut_samp, uv, 0.0).rgb;
}

// strength 0 = neutral, 1 = full LUT
color = mix(color, sample_lut(color), strength);
```

Ship a small set of LUTs: Neutral, Warm, Cold, Autumn, Winter, High contrast,
Filmic, plus colorblind-safe variants. LUTs are cheap and are one of the
highest "look" wins per byte.

### 5.3 Gamma and range (Matrix)

```wgsl
// params: x = black point, y = white point, z = output black, w = output white
fn apply_range_gamma(c: vec3<f32>, gamma: f32, r: vec4<f32>) -> vec3<f32> {
    let x = clamp((c - vec3<f32>(r.x)) / (r.y - r.x), vec3<f32>(0.0), vec3<f32>(1.0));
    let g = pow(x, vec3<f32>(gamma));
    return mix(vec3<f32>(r.z), vec3<f32>(r.w), g);
}
```

Expose black point, white point, gamma and output range as one "Display" group.
This is a better calibration story than a single brightness slider.

### 5.4 Region-locker fog (HDOS)

HDOS allows up to 16 axis-aligned rectangles, each with a fog wall and an inner
falloff. Per fragment:

```wgsl
struct RegionFog {
    rect: vec4<f32>, // min.x, min.z, max.x, max.z
};
struct RegionParams {
    enabled: u32,
    exclude: u32,
    count: u32,
    strength: f32,
    color: vec4<f32>,
    fade: f32,
    _pad: vec3<f32>,
};
```

```wgsl
fn region_fog(p: vec3<f32>, params: RegionParams) -> f32 {
    if (params.enabled == 0u || params.count == 0u || params.strength <= 0.0) {
        return 0.0;
    }
    var visibility = 0.0;
    for (var i = 0u; i < 16u; i++) {
        if (i >= params.count) { break; }
        let r = region_rects[i].rect;
        if (p.x < r.x || p.x > r.z || p.z < r.y || p.z > r.w) { continue; }
        let inward = min(min(p.x - r.x, r.z - p.x), min(p.z - r.y, r.w - p.z));
        let fade = max(params.fade, 1.0);
        let wall = smoothstep(-128.0, fade * 0.5, inward);
        let inner = 1.0 - exp(-pow(max(inward, 0.0) / fade, 1.35));
        visibility = max(visibility, clamp(max(wall, inner), 0.0, 1.0));
    }
    return visibility * params.strength;
}
```

Driving data: reuse 117HD `areas.json` rectangles, or author RustClient area
volumes. The fog should be evaluated after normal distance fog and before the
tonemap. A region fog is probably the single cheapest way to make caves,
interiors and the Wilderness feel authored rather than globally hazed.

### 5.5 Terrain lightmaps and dynamic lights (HDOS)

HDOS stores four light indices per vertex/tile and samples a lightmap texture
for terrain edges; lights themselves are a 1023-entry uniform array with
position, attenuation, colour and radius. Two attenuation modes exist: a smooth
inverse-square model and a "terrain" model with a bounded radius.

For wgpu:

- Put lights in a **storage buffer** (`array<Light>`), not a 64 KB uniform:
  1023 lights × 32 bytes ≈ 32 KB, plus material and bone data can exceed the
  default uniform binding size.
- Terrain lightmap: `texture_2d_array<f32>` of light indices, one layer per
  plane, sampled by world tile and edge. This gives smooth baked lighting for
  static torches/braziers without per-fragment loops.
- Dynamic lights: start with the nearest 32–64 lights per frame (CPU or compute
  culling). Move to a clustered/tiled grid when the count grows.
- Keep a `max_lights` setting and a "baked only" mode for low-end GPUs.

Baked lightmaps plus a handful of dynamic lights will look better than 117HD's
current placed-light partial implementation and cost far less than 1023 lights
per fragment.

### 5.6 Cube-map specular (HDOS)

HDOS samples one of three cube maps with a transformed normal and adds the
result weighted by texture alpha:

```wgsl
let dir = normalize(spec_transform * n);
let spec = textureSampleLevel(spec_cube, cube_samp, dir, 0.0).r;
color.rgb += base.rgb * spec * base.a;
```

Three cube maps are just three prefiltered environment probes (e.g. day, night,
underwater). This is much cheaper than SSR and gives armour, water and metal a
believable response. Pair it with the 117HD roughness map: blend cube specular
by `1 - roughness`.

### 5.7 Water

**What 117HD already does:** normal/flow maps, foam, Fresnel, directional
specular, underwater tint, flow distortion, caustics. Do not rebuild that.

**What HDOS adds:**

- High-detail mode projects a 3D water texture by world position:
  `uv3 = (dot(pos, sPlane), dot(pos, tPlane), time_speed)`, so the pattern is
  world-anchored and does not swim with the camera.
- Fluid surfaces use a 64-entry `vec2` noise table indexed by world position for
  a cheap "surface tension" jiggle; this is a good lava/water detail without a
  full simulation.
- Waterfall mode uses the same projection with a faster vertical speed.
- Underwater colour/depth comes from a per-tile water texture; surface geometry
  is hidden behind fog when beyond the fog end.
- A separate surface-only water pass uses the material effect flag.

**What Matrix adds:**

- 3D noise normal map with a `w` channel used as a foam/shore mask.
- Cube-map reflection from the reflected view vector.
- Fresnel `pow(1 - |dot(view, normal)|, 2)`.
- Sun specular `pow(clamp(-dot(sun_dir, reflection)), exponent)`.
- Shore factor from water depth and normal `w`.
- Wave band blend from a wave-intensity pair.

**Recommended water stack** (quality order):

1. 117HD baseline (normal/flow/foam/Fresnel/caustics).
2. Cube-map reflection + Fresnel for the sky (cheap).
3. Depth-based refraction: sample the resolved scene colour with a normal-based
   offset, reject samples behind the water surface.
4. Planar reflection: mirror the camera across the water plane, render a
   reflection pass at half resolution, blur by roughness, sample in the water
   shader. This is the big visual win and the big cost.
5. Screen-space SSR for wet ground/metal (Tier 3).

WGSL note: water is transparent, so it normally renders after opaque. To
refract, the opaque scene must be resolved first; with MSAA, resolve before the
water pass and sample the resolved single-sample colour.

### 5.8 Shadows, AO and contact shadows

The reference already has directional shadow maps. Add:

- **Contact shadows**: screen-space ray march from the depth buffer toward the
  light, 8–16 steps, fade by distance. Cheap and fixes the "floating" look under
  characters and props.
- **SSAO / GTAO**: depth + normal, hemisphere kernel, bilateral blur, multiply
  the ambient term. On a forward renderer, apply AO in a fullscreen composite
  before water. For better quality, use a compute GTAO pass and a temporal
  accumulator.
- **Baked static shadows** (HDOS): a texture array of pre-baked shadow tiles is
  an excellent fallback for low-end devices and for static scenery where
  real-time shadow maps are too expensive. Keep it as a quality option, not the
  default.
- **PCSS**: blocker search + variable PCF radius, exposed as "Soft shadows".

### 5.9 Anti-aliasing and upscaling

| Mode | Where | Notes |
|---|---|---|
| MSAA 4x | scene | already present; keep for Classic/Enhanced |
| FXAA | after tonemap, LDR | cheap, blurry; good for low-end |
| SMAA | after tonemap, LDR | better edges, 2–3 passes |
| TAA | HDR, before bloom | needs jitter + velocity + history clamp |
| CAS | final upscale | sharpening; combine with render scale |
| FSR 1 | final upscale | EASU + RCAS; portable |
| FSR 2 | HDR, before bloom | needs motion vectors and reactive masks |

TAA is the biggest quality jump for a low internal resolution, but it is also
the easiest to make look bad. Requirements:

- jitter the projection each frame (Halton 2,3 or 8,8);
- write camera velocity (and object velocity for dynamic geometry);
- reproject the history with the previous view-projection;
- clamp the history to the current neighbourhood (YCoCg or min/max);
- reject on disocclusion using depth/normal;
- keep UI out of the temporal buffer.

If TAA is too much work, ship FXAA + CAS + supersampling for screenshots. Users
with headroom will use 1.5x/2x render scale anyway.

### 5.10 UI

**Bicubic upscaling** (HDOS `gui-cubic`, Catmull-Rom with anti-ringing):

- Use when the UI scale is non-integer (125%, 150%, 175%).
- 4x4 taps, 16 samples per pixel; negligible for UI.
- Anti-ringing clamps the result to the local min/max; without it, text and
  icons get halos.
- Disable for pixel-art assets; nearest is correct there.

**MMPX** (HDOS `mmpx.comp`):

- 2x magnification preserving single-pixel features and corners.
- Use for a "Retro" UI mode, the minimap, or a deliberately low-res world mode.
- wgpu: `rgba8unorm` storage textures, workgroup 8x8, two textures (read/write).
  Check `TextureFormat::Rgba8Unorm` storage support and the
  `max_compute_workgroup_size` limits. This is a straight compute port, not a
  render pass.
- Do not run MMPX on the whole 3D frame unless the retro look is the goal; it
  will destroy anti-aliased edges.

### 5.11 GPU skinning and height alignment (HDOS)

- **Bone transforms**: 1024 `mat4` entries, one rigid bone per vertex (OSRS
  models are rigid-skinned). Put the palette in a storage buffer; index with the
  vertex's packed bone id. This removes the CPU vertex rewrite and makes
  animation updates cheap.
- **Skin transforms**: 512 `vec4` entries for colour/alpha deltas. Small; keep
  in a uniform.
- **Heightmap alignment**: sample a height texture in the vertex shader and
  offset the model. Two modes in HDOS: full snap and a blended "only near the
  ground" mode. Needs a per-object flag and a height texture; vertex texture
  fetch is supported in WGSL but costs a sample per vertex, so only enable it
  for objects that need it.
- **Depth shift**: HDOS rewrites the projection matrix to bias depth. In wgpu,
  use `DepthBiasState` (`constant`/`slope_scale`) or a reversed-Z `Depth32Float`
  target. Do not rewrite the projection.

### 5.12 SSAO, SSR, volumetrics, DoF, motion blur

These are not in either source, but they are the natural next step with GPU
headroom.

- **SSAO/GTAO**: 16–32 samples in a hemisphere, depth+normal, 4x4 or bilateral
  blur. Apply to ambient only; do not darken direct light. Cost ~0.5–1.5 ms at
  1440p.
- **SSR**: ray march the depth buffer in screen space, 32–64 steps, binary
  refinement, fade by distance and screen edge. Fall back to the cube probe.
  Use only for water/wet/metal, not every surface.
- **God rays**: either ray-march the sun shadow map (volumetric, better) or do a
  radial blur from the sun screen position (cheap, fake). Mask by sky/occlusion.
- **Cloud shadows**: sample a tiling noise texture with a sun-projected UV; one
  texture fetch, huge atmosphere win.
- **DoF**: CoC from depth, bokeh gather (e.g. 16–32 taps) at half resolution;
  keep UI sharp.
- **Motion blur**: camera-only reprojection is cheap and safe; per-object blur
  needs a velocity buffer and is easy to ghost.
- **Lens flare**: query the sun's screen visibility (depth test a few taps),
  then draw a small sprite chain. Cheap.
- **Weather**: GPU particles for rain/snow, a wetness mask that darkens and
  increases specular on upward faces, splash decals near the camera.

### 5.13 Sky, time of day and atmosphere

The reference has a gradient + sun/moon disk stub. 117HD phase 7 adds stars,
nebulae, aurora and time-of-day. If the goal is "beyond 117HD", sky is a strong
differentiator:

- night sky with stars and a milky-way band (procedural or cubemap);
- aurora ribbons (noise + vertical fade);
- moon phases;
- sunrise/sunset colour grading driven by the environment;
- cloud layer with parallax and sun scattering;
- cloud shadows on the terrain;
- environment transitions (rain, fog, lightning) tied to area/weather state.

Keep the sky in the HDR target so the sun disk can drive bloom and god rays.

## 6. Settings model

### Presets

| Preset | HDR | Bloom | LUT | Fog | Lights | Water | Shadows | AO | AA | UI |
|---|---|---|---|---|---|---|---|---|---|---|
| Classic | no | no | no | classic | no | classic | classic | no | MSAA 4x | nearest |
| Enhanced | yes | yes | yes | height + region | baked | 117HD | 117HD | no | FXAA/CAS | bicubic |
| HD | yes | yes | yes | full | dynamic 32 | 117HD + cube | CSM + PCSS | SSAO | FXAA/TAA | bicubic |
| Ultra | yes | high | yes | volumetric | 64 + baked | planar + refraction | CSM + PCSS + contact | GTAO | TAA + CAS | bicubic |
| Cinematic | yes | high | yes | volumetric | 64 | planar + SSR | high | GTAO | TAA + FSR2 | bicubic |
| Retro | no | no | optional | classic | no | classic | classic | no | none | MMPX |
| Competitive | optional | no | no | minimal | no | low | low | no | FXAA + CAS | nearest |

### Proposed settings tree

```text
graphics
  preset
  display
    render_scale        0.5 .. 2.0
    upscale             nearest | bilinear | cas | fsr1 | fsr2
    hdr                 bool
    exposure            f32
    tone_map            none | reinhard | aces | agx | filmic
    gamma
    black_point
    white_point
    output_black
    output_white
  color
    lut                 path/id
    lut_strength        0 .. 1
    saturation
    contrast
  bloom
    enabled
    threshold
    knee
    intensity
    radius              low | medium | high
  lighting
    dynamic_lights      bool
    max_lights          0 | 16 | 32 | 64
    baked_lightmaps     bool
    cube_specular       bool
  fog
    height_fog          bool
    region_fog          bool
    volumetric          bool
  water
    reflections         off | cube | planar
    refraction          bool
    foam                bool
    caustics            bool
    high_detail         bool
  shadows
    cascades            1 | 2 | 4
    filter              pcf | pcss
    contact_shadows     bool
    resolution          1024 | 2048 | 4096
  ao
    mode                off | ssao | gtao
    radius
    intensity
  post
    fxaa                bool
    taa                 bool
    ssr                 bool
    dof                 bool
    motion_blur         bool
    vignette
    grain
    chromatic_aberration
    sharpening
  ui
    filter              nearest | bilinear | bicubic | mmpx
    integer_scale       bool
  particles
    density             0 .. 2
    soft                bool
  screenshot
    supersample         1 | 2 | 4
```

### Capability gating

- Default WebGPU limits: `max_texture_array_layers = 256`. The HD material set
  needs more; request `max_texture_array_layers` up to the adapter maximum and
  fall back to a smaller material set or atlas.
- `Rgba16Float` is filterable by default; `Rgba32Float` is not. Use 16-bit HDR.
- Storage buffers are required for lights/bones; check
  `max_storage_buffer_binding_size`.
- Compute storage textures for MMPX: `Rgba8Unorm` is the safe format.
- Timestamp queries are optional; gate profiling UI on the feature.
- Everything must still run on the wasm target with reduced defaults.

### Persistence and safety

- Persist settings as versioned JSON next to the client config.
- Validate on load; unknown fields are ignored, out-of-range values clamp.
- Changing a setting that affects pipeline state (MSAA, HDR, TAA, shadow
  resolution) rebuilds only the renderer projection, never `GameState`.
- Provide "Reset to Classic" and per-section reset.
- Show measured GPU frame time next to the preset; recommend a preset instead
  of silently lowering quality.

## 7. Implementation order

### Phase G0 - foundation (1–2 weeks)

1. Add `GraphicsSettings` and persistence (Classic default).
2. Add an `Rgba16Float` HDR target and a post chain with a final display pass.
3. Port the pair of fullscreen-triangle helpers and transient pool patterns from
   the reference frame graph.
4. Add a GPU timing counter and a debug view (albedo/normal/depth/bloom).

### Phase G1 - quick wins (1–2 weeks)

1. Emissive material metadata + threshold/blur/composite bloom.
2. 3D LUT grading, gamma/range, saturation/contrast.
3. Region fog + height fog.
4. Cube-map specular.
5. Bicubic UI upscaling.
6. FXAA/CAS and vignette/grain.

This phase alone delivers a visibly different "Enhanced" preset.

### Phase G2 - lighting and water (3–5 weeks)

1. Baked terrain lightmap + dynamic light buffer + simple culling.
2. GPU skinning + height alignment.
3. Water: cube reflection, refraction, depth foam, high-detail 3D pattern.
4. Planar reflection at half resolution.
5. SSAO/GTAO + contact shadows.
6. Soft/lit particles + weather.

### Phase G3 - cinematic (4–8 weeks)

1. TAA with jitter/velocity/history.
2. FSR1/FSR2 or CAS upscaling + render scale.
3. SSR, volumetric god rays, cloud shadows.
4. DoF, motion blur, lens flare.
5. Screenshot SSAA.
6. Sky: stars, aurora, time of day, clouds.

### Phase G4 - polish

- Presets and auto-quality.
- Regression images per preset.
- GPU/CPU budget tests.
- Settings UI copy and tooltips.
- License/attribution review.

## 8. wgpu notes and pitfalls

- **Format**: `Rgba16Float` for HDR; `Rgba8UnormSrgb`/`Bgra8UnormSrgb` for the
  surface. Do not use `Rgba32Float` as a filterable scene target.
- **Colour space**: decide per profile whether scene values are linear
  (recommended for HD) or display-referred (current classic round trip).
  Mixing the two is the most common cause of "washed out" or "too dark" HDR
  ports. Encode sRGB exactly once, in the final pass.
- **MSAA**: resolve before sampling. A multisampled texture cannot be bound as a
  normal sampled texture. The reference uses 4x; keep 4x for Enhanced/HD and
  offer 2x/off for low-end.
- **Reverse-Z**: the client clears depth to 0 and compares `Greater`. Depth
  reconstruction, SSAO and SSR must use the same convention.
- **Centroid interpolation**: HDOS uses `centroid` for vertex colour to avoid
  extrapolating at MSAA edges. In WGSL:
  `@location(0) @interpolate(perspective, centroid) color: vec4<f32>`.
- **Flat material ids**: `@interpolate(flat)` for material/effect integers.
- **Uniform limits**: keep material configs, lights and bones in storage
  buffers. Uniform buffers max out at 64 KB by default and have 16-byte
  alignment rules.
- **Texture arrays**: `max_texture_array_layers` is 256 by default. HDOS-style
  2048 materials need multiple arrays or an atlas; the reference already raises
  the limit to 1024 on desktop.
- **3D textures**: `max_texture_dimension_3d` is 2048 by default; a 16³/32³ LUT
  is tiny. Set `TextureViewDimension::D3`.
- **Cube maps**: create with `TextureViewDimension::Cube`, sample with
  `texture_cube<f32>`; generate mips for roughness.
- **Point particles**: WebGPU has no `gl_PointSize`. Expand particles to
  instanced quads in the vertex shader.
- **Storage textures**: `rgba8unorm` is the portable choice for MMPX. If the
  format is not storage-capable, fall back to a render-pass-based upscale.
- **Bind groups**: cache per resource, not per frame. Creating bind groups every
  frame is a measurable CPU cost in a 144 Hz client.
- **Fullscreen passes**: use a 3-vertex triangle and
  `textureSampleLevel`/`textureLoad`; avoid derivative-dependent sampling in
  passes with undefined UV derivatives.
- **Frame graph**: keep pass declarations explicit. A pass that is not connected
  to the final output should be dropped, not silently recorded.
- **Wasm**: no `Features::FLOAT32_FILTERABLE` assumption, no huge texture
  arrays, and test the default limits path.

## 9. What to build first

If the goal is maximum visible change per unit of work:

1. **HDR + bloom + emissive tags** — makes spells, lava, torches and crystals
   glow; the most obvious "this is not OSRS" change.
2. **3D LUT + gamma/range** — one texture and one fullscreen pass for a
   cinematic look, seasonal grades and colorblind modes.
3. **Region fog + height fog** — cheap, authored atmosphere in caves and
   interiors.
4. **Cube-map specular** — armour and water stop looking flat.
5. **Bicubic UI** — immediately fixes non-integer scaling on high-DPI displays.
6. **SSAO + contact shadows** — grounds objects and adds depth.
7. **Planar water reflection** — the expensive showpiece; gate it behind Ultra.

## 10. Appendix: shader-to-technique map

| Technique | HDOS | Matrix 876 | RustOSRS reference | New WGSL |
|---|---|---|---|---|
| Emissive/bloom material tags | `main.frag` | — | material storage | `hd/materials.wgsl` + post |
| Bloom threshold | `bloom-test.frag` | `bloom__aString11611/11614` | — | new post pass |
| Bloom blur | `bloom-gaussian-blur.frag` | `bloom__aString11606` | — | new post pass |
| Bloom composite | `bloom-combine.frag` | `bloom__aString11601` | `hd/post.wgsl` | extend post |
| Tone mapping | `bloom-combine` curve | `bloom__aString11601` | placeholder | `hd/post.wgsl` |
| 3D LUT grading | — | `hsl_remap__linked_1..6` | colour filters only | new post pass |
| Gamma/range | — | `gamma_range__aString11404` | — | new post pass |
| Region fog | `main.frag` | — | area fog only | `hd/common.wgsl` |
| Height/ground fog | `main.frag` | — | `hd/common.wgsl` | extend |
| Dynamic lights | `main.frag` / `main.vert` | — | placed lights partial | `hd/layout.rs` + scene |
| Terrain lightmap | `main.frag` / `main.vert` | — | — | new bind group |
| Cube specular | `main.frag` | `water_clean` | roughness/specular maps | `hd/scene.wgsl` |
| Water surface | `water.*`, `main.frag` | `water_clean/waves` | `hd/scene.wgsl` | extend |
| Water reflection | — | cube map | sky colours | new pass |
| Water refraction | — | — | — | new pass |
| Baked shadows | `shadow.*` | — | CSM | optional fallback |
| GPU skinning | `main.vert` | — | CPU animation | new vertex path |
| Height alignment | `main.vert` | — | — | new vertex path |
| Bicubic UI | `gui-cubic.frag` | — | nearest | `ui.wgsl` option |
| MMPX | `mmpx.comp` | — | — | new compute pass |
| SSAO/GTAO | — | — | — | new post pass |
| SSR | — | — | — | new post pass |
| TAA | — | — | — | new post pass |
| Volumetrics | — | — | — | new post pass |
| Weather | — | — | — | new particle system |

## 11. Open questions

1. Does the client ship as a single GPU profile, or separate Classic/HD
   profiles selected at startup? A separate HD pipeline is easier to validate
   and keeps the reference path honest.
2. Is WebGPU the only browser target, or is a WebGL2 fallback required? That
   decides whether 3D LUTs, storage buffers and compute MMPX can be mandatory.
3. Do we want a physical "HDR display" output (scRGB/EDR) or is HDR only an
   internal precision/tone-mapping choice?
4. Should color grading LUTs be content (user-installable) or curated presets?
   User LUTs are a cheap modding win but need validation.
5. How much of the HDOS data model (2048 materials, 1023 lights, 16 region
   rects) should be reproduced versus simplified to RustClient's scene
   semantics?

## 12. External shader resources

There is no single permissively licensed WGSL catalog that covers OSRS-style
rendering. The practical strategy is to use WGSL-native passes where they
already exist, and port GLSL/HLSL techniques where the license allows. HDOS and
Matrix 876 remain reference-only; do not copy their source (section 3).

### 12.1 WGSL-native sources

| Source | What is there | License | Use for | Port cost |
|---|---|---|---|---|
| [`zbanks/radiance`](https://github.com/zbanks/radiance) `library/` | Rust/wgpu VJ app with ~170 standalone WGSL effects: blur, brighten/blowout, CRT, colour, feedback, distortion, pixelate, kaleidoscope | MIT | post, colour, CRT/feedback; good WGSL style reference | Low: fullscreen passes, simple uniforms |
| [`webgpu/webgpu-samples`](https://github.com/webgpu/webgpu-samples) | Official samples: particles, imageBlur, deferredRendering, shadowMapping, reversedZ, skinnedMesh, volumeRenderingTexture3D | BSD-3 | pass/bind-group patterns, particles, reversed-Z, volume | Low-medium: TypeScript host |
| [`gfx-rs/wgpu` examples](https://github.com/gfx-rs/wgpu/tree/trunk/examples/features/src) | Rust+WGSL `water`, `shadow`, `skybox`, `mipmap`, `cube`, compute | MIT/Apache-2.0 | water surface, shadow map, skybox, mip chain | Low: closest to our stack |
| [Bevy](https://github.com/bevyengine/bevy) + [`bevy-examples`](https://github.com/rust-adventure/bevy-examples) | Production shaders in WESL (WGSL + imports): PBR, bloom, SSAO, TAA/FXAA/SMAA, fog, parallax | MIT/Apache-2.0 | SSAO, TAA, bloom, PBR/IBL patterns | Medium: WESL→WGSL flattening + ECS bindings |
| [`dmnsgn/shaders-smaa`](https://github.com/dmnsgn/shaders-smaa) | SMAA in GLSL and WGSL: luma/colour/depth edges, temporal resolve | MIT | anti-aliasing, edge-aware upscale | Low |
| [`pmndrs/upscaler`](https://github.com/pmndrs/upscaler) | WGSL FSR EASU/RCAS and temporal FSR2/3 architecture | MIT | upscaling + sharpen for HD tier | Medium: history/reprojection |
| [`evroon/bevy-volumetric-clouds`](https://github.com/evroon/bevy-volumetric-clouds) | WGSL compute volumetric clouds (Horizon Zero Dawn) | MIT | volumetric clouds/fog | Medium |
| [`joshbrew/webgpu_realtime_clouds`](https://github.com/joshbrew/webgpu_realtime_clouds) | Pure WebGPU compute clouds | MIT | cloud/fog raymarch | Medium |
| [`jeantimex/webgpu-water`](https://github.com/jeantimex/webgpu-water) | WebGPU heightfield water | MIT | interactive water surface | Medium: simulation pass |
| [`Yuu6883/WebGPUDemo`](https://github.com/Yuu6883/WebGPUDemo) | Deferred rendering, cloth, compute particles | MIT | deferred/particles | Medium |
| [`mikbry/awesome-webgpu`](https://github.com/mikbry/awesome-webgpu) | Curated WebGPU ecosystem list | CC0 | discovery | n/a |
| [WGSL Shader Depot](https://wgsl-shader-depot.vercel.app/) | Searchable WGSL samples, fork of webgpu-samples | BSD-3 | discovery/snippets | n/a |

Raw discovery: the [`wgsl`](https://github.com/topics/wgsl) and
[`webgpu`](https://github.com/topics/webgpu) GitHub topics have hundreds of
repos, but signal is low; prefer the curated entries above.

### 12.2 GLSL/HLSL catalogs to port

| Source | What is there | License | Use for |
|---|---|---|---|
| [Shadertoy](https://www.shadertoy.com/) | Enormous GLSL catalog, including full ray-marched scenes | Per shader; many CC BY-NC-SA | technique study and clean-room reimplementation |
| [Godot Shaders](https://godotshaders.com/) / [`gdquest-demos/godot-shaders`](https://github.com/gdquest-demos/godot-shaders) | Thousands of water, sky, fog, particle, post shaders | Per shader; GDQuest repo code MIT, art CC BY-NC-SA | water, sky, fog, particles |
| [LYGIA](https://github.com/patriciogonzalezvivo/lygia) | Granular GLSL/HLSL/Metal/WGSL functions: noise, SDF, colour, lighting | Prosperity/Patron (free noncommercial, 30-day commercial trial) | function reference; license before shipping |
| [`SaschaWillems/Vulkan`](https://github.com/SaschaWillems/Vulkan) | Many small Vulkan samples: bloom, SSAO, PBR, shadows, particles, subsurface | MIT | effect recipes with minimal host code |
| [AMD FidelityFX](https://github.com/GPUOpen-Effects/FidelityFX) | CACAO (SSAO), SSSR, CAS, SPD, denoisers | MIT | SSAO, SSR, sharpen, downsampling |
| [FidelityFX FSR2](https://github.com/GPUOpen-Effects/FidelityFX-FSR2) | FSR 2.2 upscaler | MIT | temporal upscaling |
| [Filament](https://github.com/google/filament) | Production PBR/IBL/tonemap/colour pipeline | Apache-2.0 | PBR, IBL, colour reference |
| [three.js](https://github.com/mrdoob/three.js) | UnrealBloomPass, SSAO, SSR, water, sky, postprocessing | MIT | post/water/sky recipes |
| [Babylon.js](https://github.com/BabylonJS/Babylon.js) | PBR, bloom, SSAO2, SSR, TAA; WebGPU/WGSL paths | Apache-2.0 | post/SSAO/SSR/TAA reference |
| [PlayCanvas](https://github.com/playcanvas/engine) | WebGPU/WGSL engine with post effects | MIT | WebGPU pass structure |
| [LearnOpenGL](https://learnopengl.com/) | SSAO, bloom, deferred, water, shadows | CC BY-NC 4.0 | reference only for commercial builds |
| [3D Game Shaders for Beginners](https://github.com/lettier/3d-game-shaders-for-beginners) | SSAO, SSR, LUT, bloom, DoF, motion blur | Unclear (no SPDX license) | technique reference only |
| [libplacebo](https://github.com/haasn/libplacebo) | High-quality scaling, debanding, tone mapping, LUTs | LGPL-2.1 | upscaling/tonemap reference; LGPL caution |

### 12.3 Tools

- **naga** (ships with wgpu): parse/validate WGSL and translate GLSL,
  SPIR-V, HLSL and MSL. Use it to get a first-pass conversion, then fix
  bindings and coordinate conventions by hand.
- **SPIRV-Cross** (Apache-2.0): GLSL/HLSL/MSL/SPIR-V cross-compilation.
- **WESL** ([`wesl-rs`](https://github.com/webgpu-tools/wesl-rs),
  Apache-2.0): WGSL plus imports/exports; flattens to plain WGSL. Bevy now
  ships its shaders in WESL, so this is needed to reuse them.
- **shader-playground.timjones.io**: quick compile/compare across compilers.
- **RenderDoc** / **Xcode Metal debugger**: inspect passes, bindings and
  resource state.
- **wgsl-analyzer**: editor diagnostics for WGSL.

### 12.4 Assets and colour data

- [Poly Haven](https://polyhaven.com/) and [ambientCG](https://ambientcg.com/):
  CC0 HDRIs and PBR textures for sky/IBL and terrain tests.
- [OpenColorIO ACES configs](https://github.com/AcademySoftwareFoundation/OpenColorIO-Config-ACES)
  (BSD-3): reference colour transforms and tone mapping.
- LUT packs (FreshLUTs, IWLTBAP, etc.): per-LUT licenses; ship only cleared
  files, and keep LUTs as content rather than code.
- The HDOS `gui-cubic.frag` and `mmpx.comp` are already BSD-licensed and remain
  the best starting points for UI scaling; no external replacement needed.

### 12.5 Porting checklist

Before porting any shader, check:

1. **License** — MIT/BSD/Apache/CC0 only for shipped code; NC/ND and
   "all rights reserved" sources are reference-only.
2. **Clip space and depth** — GL uses `-1..1` NDC and often a different Y
   direction; wgpu uses `0..1` depth. Our renderer is reverse-Z, so rederive
   depth comparisons rather than copying them.
3. **Bindings** — GLSL `std140` blocks, combined samplers, and loose uniforms
   do not map directly to WGSL bind groups; rebuild the layout explicitly.
4. **Coordinate assumptions** — screen UV origin, texture V flip, and
   world-to-view handedness differ between engines.
5. **Feature gaps** — no geometry shaders in WebGPU, limited subgroup ops,
   no framebuffer fetch; replace those paths with compute or extra passes.
6. **Precision and loops** — dynamic loop bounds and `mediump` assumptions can
   compile but be slow or wrong on some backends.
7. **Temporal state** — TAA/FSR need jitter, history rejection and a reset path
   on resize/teleport; do not bolt them onto a pass that has no motion vectors.

### 12.6 Shortlist for RustClient

If we only pull a few things, pull these:

| Feature | First source |
|---|---|
| HDR + bloom | Bevy bloom + `webgpu-samples/imageBlur`; Radiance blur/brighten |
| 3D LUT / gamma / range | 3D Game Shaders LUT+gamma recipes, Filament colour, ACES configs |
| Water | `wgpu` water example, `jeantimex/webgpu-water`, Godot water shaders |
| SSAO | Bevy SSAO, FidelityFX CACAO |
| SSR | FidelityFX SSSR, three.js SSR |
| TAA / upscale | Bevy TAA, `pmndrs/upscaler`, `dmnsgn/shaders-smaa` |
| Volumetrics | `bevy-volumetric-clouds`, `webgpu_realtime_clouds` |
| Particles | `webgpu-samples/particles`, `Yuu6883/WebGPUDemo` |
| UI upscale | existing HDOS `gui-cubic`/`mmpx`; `pmndrs/upscaler` for FSR |

Port effects one at a time behind an `Enhanced`/`HD` flag, and keep the
classic composite untouched so the reference path stays byte-comparable.
