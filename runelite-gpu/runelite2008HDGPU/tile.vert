uniform mat4 positionMatrix;
uniform mat4 projection;
uniform mat4 model;
uniform vec3 fog_camera_position;

in vec3 position;
in vec4 color;
in float depth_shift;

out vec4 f_color;
out float f_distance;

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
    vec4 mpos = model * vec4(position, 1);
    f_distance = length(fog_camera_position.xz - mpos.xz);
    gl_Position = project() * positionMatrix * mpos;
    f_color = color;
}
