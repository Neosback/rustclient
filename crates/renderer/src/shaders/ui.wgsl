// Binding map: group 0 = image resources; binding 0 = texture, 1 = nearest sampler.
// No semantic/session data or clock is available to this shader.
@group(0) @binding(0) var image: texture_2d<f32>;
@group(0) @binding(1) var nearest: sampler;

struct VertexInput {
    @location(0) position: vec2<f32>,
    @location(1) uv: vec2<f32>,
    @location(2) tint: vec4<f32>,
}
struct VertexOutput {
    @builtin(position) position: vec4<f32>,
    @location(0) uv: vec2<f32>,
    @location(1) tint: vec4<f32>,
}
@vertex fn vs(input: VertexInput) -> VertexOutput {
    var output: VertexOutput;
    output.position = vec4<f32>(input.position, 0.0, 1.0);
    output.uv = input.uv;
    output.tint = input.tint;
    return output;
}
@fragment fn fs(input: VertexOutput) -> @location(0) vec4<f32> {
    return textureSample(image, nearest, input.uv) * input.tint;
}
