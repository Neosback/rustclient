uniform mat4 transform;
uniform mat4 projection;
uniform vec3 fog_camera_position;
uniform float scale;

in vec3 position;
in vec4 color;
in float size;
in int material;
in float depth;

out vec4 f_color;
out float f_distance;
flat out int f_material;
flat out int f_ambient;


mat4 project() {
    float depth_scale = (depth) * 1.5;
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
    vec4 epos = transform * vec4(position, 1);
    f_distance = length(position.xz - fog_camera_position.xz);
    // gl_PointSize size is in pixel units, scale it based on depth
    gl_PointSize = (size * scale) / max(length(epos.xyz), 1.0);
    gl_Position = project() * epos;
    f_color = color;
    f_material = material & 0x7ff;
    f_ambient = (material >> 11) & 0x1;
}
