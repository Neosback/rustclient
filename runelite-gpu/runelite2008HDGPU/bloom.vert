
in vec2 position;
in vec2 uv_0;
in vec2 uv_1;

out vec2 f_uv_0;
out vec2 f_uv_1;

void main(void) {
    gl_Position = vec4(position, 0, 1);
    f_uv_0 = uv_0;
    f_uv_1 = uv_1;
}
