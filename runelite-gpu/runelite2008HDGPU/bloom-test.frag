
uniform vec3 params;
uniform sampler2DRect sceneTex;

const vec3 lumCoef = vec3(0.2126, 0.7152, 0.0722);

in vec2 f_uv_0;
out vec4 f_color;

void main() {
    vec3 col = texture(sceneTex, f_uv_0).rgb;
    f_color = vec4(col*step(params.x, dot(lumCoef, col)), 1.0);
}
