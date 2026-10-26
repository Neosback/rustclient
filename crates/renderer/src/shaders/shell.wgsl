// Rasterized by the retained native DOM only when dirty; sampled without per-frame uploads.
@group(0) @binding(0) var panel: texture_2d<f32>;
@group(0) @binding(1) var panel_sampler: sampler;
struct Vertex { @builtin(position) position:vec4<f32>, @location(0) uv:vec2<f32> }
@vertex fn vs_main(@builtin(vertex_index) index:u32)->Vertex {
    let positions=array<vec2<f32>,3>(vec2<f32>(-1.,-1.),vec2<f32>(3.,-1.),vec2<f32>(-1.,3.));
    var result:Vertex;
    result.position=vec4<f32>(positions[index],0.,1.);
    result.uv=vec2<f32>((positions[index].x+1.)/2.,(1.-positions[index].y)/2.);
    return result;
}
@fragment fn fs_main(input:Vertex)->@location(0) vec4<f32> {
    return textureSample(panel,panel_sampler,input.uv);
}
