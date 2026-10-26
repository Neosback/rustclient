// RuneLite GPU material transfer: runelite-gpu/vert.glsl + frag.glsl.
// HSL gamma is applied in the vertex conversion; texture gamma in the fragment shader.
// fog: x depth in tiles (0 = off), y draw distance in local units, z/w eye x/z. color: x color-blind mode, y intensity.
struct Camera { view_projection: mat4x4<f32>, cycle: u32, top_level: u32, brightness:f32, material_flags:u32, fog:vec4<f32>, fog_color:vec4<f32>, color:vec4<f32> }
struct Animations { values:array<vec4<f32>,256> }
@group(0) @binding(0) var<uniform> camera:Camera;
@group(0) @binding(1) var textures:texture_2d_array<f32>;
@group(0) @binding(2) var tex_sampler:sampler;
@group(0) @binding(3) var<uniform> animations:Animations;
struct Input { @location(0) position:vec4<i32>, @location(1) abhsl:u32, @location(2) texture_uv:vec4<u32> }
struct Vertex {
 @builtin(position) clip:vec4<f32>,
 @location(0) alpha:f32,
 @location(1) @interpolate(linear,centroid) hsl:f32,
 @location(2) @interpolate(flat) texture:u32,
 @location(3) uv:vec2<f32>,
 @location(4) color:vec3<f32>,
 @location(5) fog:f32
}
fn hue_channel(p:f32,q:f32,raw:f32)->f32 {
 var h=raw;
 if h<0.0 {h+=1.0;} if h>1.0 {h-=1.0;}
 if 6.0*h<1.0 {return p+(q-p)*6.0*h;}
 if 2.0*h<1.0 {return q;}
 if 3.0*h<2.0 {return p+(q-p)*(0.6666666666666666-h)*6.0;}
 return p;
}
fn hsl_rgb(packed:u32)->vec3<f32> {
 let hue=f32((packed>>10u)&63u)/64.0+0.0078125;
 let sat=f32((packed>>7u)&7u)/8.0+0.0625;
 let lum=f32(packed&127u)/128.0;
 var q=lum+sat-lum*sat;
 if lum<0.5 {q=lum*(1.0+sat);}
 let p=2.0*lum-q;
 return pow(vec3<f32>(hue_channel(p,q,hue+0.3333333333333333),hue_channel(p,q,hue),hue_channel(p,q,hue-0.3333333333333333)),vec3<f32>(camera.brightness));
}
// RuneLite vert.glsl: distance to the draw-distance square, clamped to the scene (including expanded
// loading, which this client does not use yet), with rounded corners; fogFactorLinear(d, 0, depth).
const TILE:f32=128.0;
const SCENE_MIN:f32=128.0;
const SCENE_MAX:f32=13184.0;
const CORNER:f32=1.5;
fn fog_amount(world:vec3<f32>)->f32 {
 if camera.fog.x<=0.0 {return 0.0;}
 let west=max(SCENE_MIN,camera.fog.z-camera.fog.y);
 let east=min(SCENE_MAX,camera.fog.z+camera.fog.y);
 let south=max(SCENE_MIN,camera.fog.w-camera.fog.y);
 let north=min(SCENE_MAX,camera.fog.w+camera.fog.y);
 let x_dist=min(world.x-west,east-world.x);
 let z_dist=min(world.z-south,north-world.z);
 let nearest=min(x_dist,z_dist);
 let second=max(x_dist,z_dist);
 let distance=nearest-CORNER*TILE*max(0.0,(nearest+CORNER*CORNER)/(second+CORNER*CORNER));
 return 1.0-clamp(distance/(camera.fog.x*TILE),0.0,1.0);
}
// RuneLite colorblind.glsl, matrices in the same column order (row-vector * matrix in both languages).
const RGB2LMS=mat3x3<f32>(vec3<f32>(17.8824,43.5161,4.11935),vec3<f32>(3.45565,27.1554,3.86714),vec3<f32>(0.0299566,0.184309,1.46709));
const LMS2RGB=mat3x3<f32>(vec3<f32>(0.0809444479,-0.130504409,0.116721066),vec3<f32>(-0.0102485335,0.0540193266,-0.113614708),vec3<f32>(-0.000365296938,-0.00412161469,0.693511405));
const PROTAN=mat3x3<f32>(vec3<f32>(0.0,2.02344,-2.52581),vec3<f32>(0.0,1.0,0.0),vec3<f32>(0.0,0.0,1.0));
const DEUTERAN=mat3x3<f32>(vec3<f32>(1.0,0.0,0.0),vec3<f32>(0.494207,0.0,1.24827),vec3<f32>(0.0,0.0,1.0));
const TRITAN=mat3x3<f32>(vec3<f32>(1.0,0.0,0.0),vec3<f32>(0.0,1.0,0.0),vec3<f32>(-0.395913,0.801109,0.0));
const CORRECTIONS=mat3x3<f32>(vec3<f32>(0.0,0.0,0.0),vec3<f32>(0.7,1.0,0.0),vec3<f32>(0.7,0.0,1.0));
fn colorblind(color:vec3<f32>)->vec3<f32> {
 let lms_in=color*RGB2LMS;
 var lms=lms_in*PROTAN;
 if camera.color.x==2.0 {lms=lms_in*DEUTERAN;}
 if camera.color.x==3.0 {lms=lms_in*TRITAN;}
 let error=color-lms*LMS2RGB;
 let correction=(error*CORRECTIONS)*clamp(camera.color.y/100.0,0.0,1.0);
 return color+correction;
}
fn finish(rgb:vec3<f32>,alpha:f32,fog:f32)->vec4<f32> {
 var c=rgb;
 if camera.color.x>0.0 {c=colorblind(c);}
 return vec4<f32>(mix(c,camera.fog_color.rgb,fog),alpha);
}
@vertex fn vs_main(input:Input)->Vertex {
 var out:Vertex;
 if input.texture_uv.w>camera.top_level {
    // Tile render level above this frame's top level (roofs, upper floors): all three
    // vertices collapse to one point, so the triangle is dropped before rasterization.
    out.clip=vec4<f32>(2.0,2.0,2.0,1.0);
    out.alpha=0.0;out.hsl=0.0;out.texture=0u;out.uv=vec2<f32>(0.0);out.color=vec3<f32>(0.0);out.fog=0.0;
    return out;
 }
 out.clip=camera.view_projection*vec4<f32>(vec3<f32>(input.position.xyz),1.0);
 out.clip.z+=f32((input.abhsl>>16u)&255u)/128.0;
 out.alpha=1.0-f32(input.abhsl>>24u)/255.0;
 out.hsl=f32(input.abhsl&65535u);
 out.texture=input.texture_uv.x;
 let u=i32(input.texture_uv.y)-select(0,65536,input.texture_uv.y>=32768u);
 let v=i32(input.texture_uv.z)-select(0,65536,input.texture_uv.z>=32768u);
 out.uv=vec2<f32>(f32(u),f32(v))/256.0;
 if out.texture>0u {
    out.uv+=f32(camera.cycle&127u)*animations.values[out.texture-1u].xy/128.0;
 }
 out.color=hsl_rgb(input.abhsl);
 out.fog=fog_amount(vec3<f32>(input.position.xyz));
 return out;
}
@fragment fn fs_main(input:Vertex)->@location(0) vec4<f32> {
 // RustOSRS: derivatives precede material branching and alpha discard.
 let dx=dpdx(input.uv);let dy=dpdy(input.uv);
 if input.texture>0u {
    let layer=i32(input.texture-1u);
    if textureSampleLevel(textures,tex_sampler,input.uv,layer,0.0).a<1.0 {discard;}
    let rgb=pow(textureSampleGrad(textures,tex_sampler,input.uv,layer,dx,dy).rgb,vec3<f32>(camera.brightness));
    // RuneLite textureLightMode selects HSL RGB for the old bright-texture path.
    let light=select(vec3<f32>(input.hsl/127.0),input.color,(camera.material_flags&2u)!=0u);
    return finish(rgb*light,input.alpha,input.fog);
 }
 // RuneLite smoothBanding false interpolates packed HSL and quantizes at the fragment.
 let color=select(input.color,hsl_rgb(u32(input.hsl)),(camera.material_flags&1u)!=0u);
 return finish(color,input.alpha,input.fog);
}
