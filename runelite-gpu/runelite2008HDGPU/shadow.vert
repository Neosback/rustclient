uniform mat4 transform;
uniform mat4 projection;
uniform vec3 fog_camera_position;

in vec3 position;

out vec2 f_uv;
out float f_distance;

void main(void) {
    f_uv = (position.xz / 8.);
    vec4 epos = transform * vec4(position, 1);
    f_distance = length(position.xz - fog_camera_position.xz);
    gl_Position = projection * epos;
}
