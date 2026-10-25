uniform mat4 projection;
uniform mat4 model;
uniform float depth_shift;

in vec3 position;
in vec2 uv;

out vec2 f_uv;

mat4 project() {
    float depth_scale = (depth_shift) * 1.5;
    if (depth_scale == 0.) return projection;

    // 1. some cards do not like mutating the matrix directly:
    //  proj[2][2] = proj[2][2] + b; <<== BAD
    // 2. some macs are just stupid, and this this form is what makes it work..

    vec4 proj2 = projection[2];
    vec4 proj3 = projection[3];

    const float depth_offset = 6144.;
    const float depth_range_end = 1.;
    float scaled_offset = depth_offset / (depth_scale + depth_offset);
    float offset = (-depth_range_end * (1. - scaled_offset) * (1. - scaled_offset) * proj3.z) / depth_scale;

    proj2.z += offset;
    proj3.z *= (scaled_offset * scaled_offset);

    return mat4(
    projection[0],
    projection[1],
    proj2,
    proj3
    );
}

void main(void) {
    gl_Position = project() * model * vec4(position, 1);
    f_uv = uv;
}
