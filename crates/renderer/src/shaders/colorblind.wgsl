// RuneLite colorblind.glsl, matrices in the same column order (row-vector * matrix in both languages).
const RGB2LMS=mat3x3<f32>(vec3<f32>(17.8824,43.5161,4.11935),vec3<f32>(3.45565,27.1554,3.86714),vec3<f32>(0.0299566,0.184309,1.46709));
const LMS2RGB=mat3x3<f32>(vec3<f32>(0.0809444479,-0.130504409,0.116721066),vec3<f32>(-0.0102485335,0.0540193266,-0.113614708),vec3<f32>(-0.000365296938,-0.00412161469,0.693511405));
const PROTAN=mat3x3<f32>(vec3<f32>(0.0,2.02344,-2.52581),vec3<f32>(0.0,1.0,0.0),vec3<f32>(0.0,0.0,1.0));
const DEUTERAN=mat3x3<f32>(vec3<f32>(1.0,0.0,0.0),vec3<f32>(0.494207,0.0,1.24827),vec3<f32>(0.0,0.0,1.0));
const TRITAN=mat3x3<f32>(vec3<f32>(1.0,0.0,0.0),vec3<f32>(0.0,1.0,0.0),vec3<f32>(-0.395913,0.801109,0.0));
const CORRECTIONS=mat3x3<f32>(vec3<f32>(0.0,0.0,0.0),vec3<f32>(0.7,1.0,0.0),vec3<f32>(0.7,0.0,1.0));
fn correct_color(color:vec3<f32>, mode:u32, intensity:f32)->vec3<f32> {
 if mode==0u {return color;}
 let lms_in=color*RGB2LMS;
 var lms=lms_in*PROTAN;
 if mode==2u {lms=lms_in*DEUTERAN;}
 if mode==3u {lms=lms_in*TRITAN;}
 let error=color-lms*LMS2RGB;
 let correction=(error*CORRECTIONS)*clamp(intensity/100.0,0.0,1.0);
 return color+correction;
}
