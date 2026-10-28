// RuneLite GPU fragui/vertui + scale/bicubic.glsl + scale/hybrid.glsl.
// Canvas pixels are encoded RGB with premultiplied alpha, matching the software UI texture.
struct CanvasUniform { source:vec2<u32>, mode:u32, color_mode:u32, rect:vec4<f32>, params:vec4<f32>, overlay:vec4<f32> }
@group(0) @binding(0) var<uniform> config:CanvasUniform;
@group(0) @binding(1) var canvas:texture_2d<f32>;
@group(0) @binding(2) var world:texture_2d<f32>;
fn canvas_texel(point:vec2<i32>)->vec4<f32> {
 return textureLoad(canvas,clamp(point,vec2<i32>(0),vec2<i32>(config.source)-vec2<i32>(1)),0);
}
fn nearest_canvas(uv:vec2<f32>)->vec4<f32> {return canvas_texel(vec2<i32>(floor(uv*vec2<f32>(config.source))));}
fn linear_canvas(uv:vec2<f32>)->vec4<f32> {
 let p=uv*vec2<f32>(config.source)-vec2<f32>(0.5);
 let base=vec2<i32>(floor(p));let f=fract(p);
 return mix(mix(canvas_texel(base),canvas_texel(base+vec2<i32>(1,0)),f.x),
            mix(canvas_texel(base+vec2<i32>(0,1)),canvas_texel(base+vec2<i32>(1,1)),f.x),f.y);
}
fn cubic_weight(distance:f32,catrom:bool)->f32 {
 let t=abs(distance);let t2=t*t;let t3=t2*t;
 if catrom {
  if t<1.0 {return 1.5*t3-2.5*t2+1.0;}
  if t<2.0 {return -0.5*t3+2.5*t2-4.0*t+2.0;}
 } else {
  if t<1.0 {return (7.0/6.0)*t3-2.0*t2+8.0/9.0;}
  if t<2.0 {return (-7.0/18.0)*t3+2.0*t2-(10.0/3.0)*t+16.0/9.0;}
 }
 return 0.0;
}
fn cubic_canvas(uv:vec2<f32>,catrom:bool)->vec4<f32> {
 let size=vec2<f32>(config.source);let sub=fract(uv*size-vec2<f32>(0.5));
 let origin=uv-sub/size;
 var sum=vec4<f32>(0.0);var denominator=0.0;
 var low=vec4<f32>(3.402823466e+38);var high=vec4<f32>(1.175494351e-38);
 for(var m:i32=-1;m<=2;m++){for(var n:i32=-1;n<=2;n++){
  let offset=vec2<f32>(f32(m),f32(n));let value=nearest_canvas(origin+offset/size);
  let weight=cubic_weight(length(offset-sub),catrom);
  sum+=value*weight;denominator+=weight;low=min(low,value);high=max(high,value);
 }}
 let filtered=sum/denominator;
 return select(filtered,mix(filtered,clamp(filtered,low,high),0.9),catrom);
}
fn hybrid_canvas(uv:vec2<f32>)->vec4<f32> {
 let size=vec2<f32>(config.source);let p=uv*size;let f=fract(p);
 let scale=config.rect.zw/size;
 let amount=min(f*scale,vec2<f32>(0.5))-min((vec2<f32>(1.0)-f)*scale,vec2<f32>(0.5));
 return linear_canvas((floor(p)+vec2<f32>(0.5)+amount)/size);
}
fn to_linear(rgb:vec3<f32>)->vec3<f32> {
 return select(pow(max((rgb+vec3<f32>(0.055))/1.055,vec3<f32>(0.0)),vec3<f32>(2.4)),rgb/12.92,rgb<=vec3<f32>(0.04045));
}
@vertex fn vs_main(@builtin(vertex_index) i:u32)->@builtin(position) vec4<f32> {
 let p=array<vec2<f32>,3>(vec2<f32>(-1.0,-1.0),vec2<f32>(3.0,-1.0),vec2<f32>(-1.0,3.0));
 return vec4<f32>(p[i],0.0,1.0);
}
@fragment fn fs_main(@builtin(position) p:vec4<f32>)->@location(0) vec4<f32> {
 let uv=(p.xy-config.rect.xy)/config.rect.zw;
 var c=vec4<f32>(0.0);
 if all(uv>=vec2<f32>(0.0)) && all(uv<vec2<f32>(1.0)) {
  switch config.mode {
   case 1u: {c=linear_canvas(uv);}
   case 2u: {c=cubic_canvas(uv,false);}
   case 3u: {c=cubic_canvas(uv,true);}
   case 4u: {c=xbr_canvas(uv,max(ceil(config.rect.z/f32(config.source.x)),1.0));}
   case 5u: {c=hybrid_canvas(uv);}
   default: {c=nearest_canvas(uv);}
  }
  c=c+config.overlay*(1.0-c.a);
  c=vec4<f32>(correct_color(c.rgb,config.color_mode,config.params.x),c.a);
 }
 var base=vec3<f32>(0.0);
 if config.params.y>0.0 {base=textureLoad(world,vec2<i32>(p.xy),0).rgb;}
 let rgb=c.rgb+base*(1.0-c.a);
 return vec4<f32>(select(rgb,to_linear(rgb),config.params.z>0.0),1.0);
}
