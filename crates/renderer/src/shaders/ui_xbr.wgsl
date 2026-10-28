// Port of Hyllian xBR-lv2 (MIT), source scale/xbr_lv2_{vert,frag,common}.glsl.
// CORNER_C and small_details=1 are the source's active defines. Neighborhood coordinates are
// calculated in the fragment stage to keep the portable inter-stage variable budget small.
fn xbr_diff(a:vec4<f32>,b:vec4<f32>)->vec4<f32> {return select(vec4<f32>(0.0),vec4<f32>(1.0),a!=b);}
fn xbr_eq(a:vec4<f32>,b:vec4<f32>)->vec4<f32> {return step(abs(a-b),vec4<f32>(9.0));}
fn xbr_neq(a:vec4<f32>,b:vec4<f32>)->vec4<f32> {return vec4<f32>(1.0)-xbr_eq(a,b);}
fn xbr_wd(a:vec4<f32>,b:vec4<f32>,c:vec4<f32>,d:vec4<f32>,e:vec4<f32>,f:vec4<f32>,g:vec4<f32>,h:vec4<f32>,i:vec4<f32>,j:vec4<f32>,k:vec4<f32>,l:vec4<f32>)->vec4<f32> {
 return abs(a-b)+abs(a-c)+abs(d-e)+abs(d-f)+abs(i-j)+abs(k-l)+2.0*abs(g-h);
}
fn xbr_rgb_diff(a:vec3<f32>,b:vec3<f32>)->f32 {let d=abs(a-b);return d.x+d.y+d.z;}
fn xbr_read(uv:vec2<f32>,x:f32,y:f32)->vec4<f32> {return nearest_canvas(uv+vec2<f32>(x,y)/vec2<f32>(config.source));}
fn xbr_canvas(uv:vec2<f32>,scale:f32)->vec4<f32> {
 let a1=xbr_read(uv,-1.0,-2.0);let b1=xbr_read(uv,0.0,-2.0);let c1=xbr_read(uv,1.0,-2.0);
 let a=xbr_read(uv,-1.0,-1.0);let b0=xbr_read(uv,0.0,-1.0);let c0=xbr_read(uv,1.0,-1.0);
 let d0=xbr_read(uv,-1.0,0.0);let e0=xbr_read(uv,0.0,0.0);let f0=xbr_read(uv,1.0,0.0);
 let g0=xbr_read(uv,-1.0,1.0);let h0=xbr_read(uv,0.0,1.0);let i0=xbr_read(uv,1.0,1.0);
 let g5=xbr_read(uv,-1.0,2.0);let h5p=xbr_read(uv,0.0,2.0);let i5p=xbr_read(uv,1.0,2.0);
 let a0=xbr_read(uv,-2.0,-1.0);let d0p=xbr_read(uv,-2.0,0.0);let g0p=xbr_read(uv,-2.0,1.0);
 let c4=xbr_read(uv,2.0,-1.0);let f4p=xbr_read(uv,2.0,0.0);let i4p=xbr_read(uv,2.0,1.0);
 let weights=vec3<f32>(14.352,28.176,5.472);
 let b=vec4<f32>(dot(b0.rgb,weights),dot(d0.rgb,weights),dot(h0.rgb,weights),dot(f0.rgb,weights));
 let c=vec4<f32>(dot(c0.rgb,weights),dot(a.rgb,weights),dot(g0.rgb,weights),dot(i0.rgb,weights));
 let d=b.yzwx;let e=vec4<f32>(dot(e0.rgb,weights));let f=b.wxyz;let g=c.zwxy;let h=b.zwxy;let i=c.wxyz;
 let y=50.0*vec3<f32>(0.2126,0.7152,0.0722);
 let i4=vec4<f32>(dot(i4p.rgb,y),dot(c1.rgb,y),dot(a0.rgb,y),dot(g5.rgb,y));
 let i5=vec4<f32>(dot(i5p.rgb,y),dot(c4.rgb,y),dot(a1.rgb,y),dot(g0p.rgb,y));
 let h5=vec4<f32>(dot(h5p.rgb,y),dot(f4p.rgb,y),dot(b1.rgb,y),dot(d0p.rgb,y));
 // The supplied GLSL declares f4 but never initializes it. Use its rotationally corresponding
 // neighborhood instead of reproducing undefined driver-dependent values.
 let f4=h5.yzwx;
 let fp=fract(uv*vec2<f32>(config.source));
 let axes=vec4<f32>(1.0,-1.0,-1.0,1.0);
 let fx=axes*fp.y+vec4<f32>(1.0,1.0,-1.0,-1.0)*fp.x;
 let fx_l=axes*fp.y+vec4<f32>(0.5,2.0,-0.5,-2.0)*fp.x;
 let fx_u=axes*fp.y+vec4<f32>(2.0,0.5,-2.0,-0.5)*fp.x;
 let irlv0=xbr_diff(e,f)*xbr_diff(e,h);
 let irlv1=irlv0*(xbr_neq(f,b)*xbr_neq(f,c)+xbr_neq(h,d)*xbr_neq(h,g)+xbr_eq(e,i)*(xbr_neq(f,f4)*xbr_neq(f,i4)+xbr_neq(h,h5)*xbr_neq(h,i5))+xbr_eq(e,g)+xbr_eq(e,c));
 let irlv2l=xbr_diff(e,g)*xbr_diff(d,g);let irlv2u=xbr_diff(e,c)*xbr_diff(b,c);
 let delta=vec4<f32>(1.0/scale);let dl=vec4<f32>(0.5,1.0,0.5,1.0)/scale;let du=dl.yxwz;
 let co=vec4<f32>(1.5,0.5,-0.5,0.5);
 let wd1=xbr_wd(e,c,g,i,f4,h5,h,f,b,d,i4,i5);let wd2=xbr_wd(h,d,i5,f,b,i4,e,i,g,h5,c,f4);
 let edri=step(wd1,wd2)*irlv0;let edr=step(wd1+vec4<f32>(0.1),wd2)*step(vec4<f32>(0.5),irlv1);
 let edrl=step(2.0*abs(f-g),abs(h-c))*irlv2l*edr;let edru=step(2.0*abs(h-c),abs(f-g))*irlv2u*edr;
 let f45i=edri*clamp((fx+delta-co-vec4<f32>(0.25))/(2.0*delta),vec4<f32>(0.0),vec4<f32>(1.0));
 let f45=edr*clamp((fx+delta-co)/(2.0*delta),vec4<f32>(0.0),vec4<f32>(1.0));
 let f30=edrl*clamp((fx_l+dl-vec4<f32>(1.0,1.0,-0.5,0.0))/(2.0*dl),vec4<f32>(0.0),vec4<f32>(1.0));
 let f60=edru*clamp((fx_u+du-vec4<f32>(2.0,0.0,-1.0,0.5))/(2.0*du),vec4<f32>(0.0),vec4<f32>(1.0));
 let amount=max(max(f30,f60),max(f45,f45i));let px=step(abs(e-f),abs(e-h));
 let res1=mix(mix(e0,mix(h0,f0,px.x),amount.x),mix(b0,d0,px.z),amount.z);
 let res2=mix(mix(e0,mix(f0,b0,px.y),amount.y),mix(d0,h0,px.w),amount.w);
 return mix(res1,res2,step(xbr_rgb_diff(e0.rgb,res1.rgb),xbr_rgb_diff(e0.rgb,res2.rgb)));
}
