// Classic reference materials: baked HSL/lightness, no directional relighting.
struct Camera { view_projection: mat4x4<f32>, cycle: u32, _pad0:u32,_pad1:u32,_pad2:u32 }
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
 @location(4) color:vec3<f32>
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
 return pow(vec3<f32>(hue_channel(p,q,hue+0.3333333333333333),hue_channel(p,q,hue),hue_channel(p,q,hue-0.3333333333333333)),vec3<f32>(0.8));
}
@vertex fn vs_main(input:Input)->Vertex {
 var out:Vertex;
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
    out.color=vec3<f32>(0.0);
 } else {out.color=hsl_rgb(input.abhsl);}
 return out;
}
@fragment fn fs_main(input:Vertex)->@location(0) vec4<f32> {
 if input.texture>0u {
    let layer=i32(input.texture-1u);
    let dx=dpdx(input.uv);let dy=dpdy(input.uv);
    if textureSampleLevel(textures,tex_sampler,input.uv,layer,0.0).a<1.0 {discard;}
    let rgb=pow(textureSampleGrad(textures,tex_sampler,input.uv,layer,dx,dy).rgb,vec3<f32>(0.8));
    return vec4<f32>(rgb*(input.hsl/127.0),input.alpha);
 }
 return vec4<f32>(input.color,input.alpha);
}
