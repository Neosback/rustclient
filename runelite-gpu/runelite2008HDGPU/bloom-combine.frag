
uniform vec3 params;
uniform vec3 dimScale;
uniform sampler2D bloomTex;
uniform sampler2DRect sceneTex;

const vec3 lumCoef = vec3(0.2126, 0.7152, 0.0722);

in vec2 f_uv_0;
in vec2 f_uv_1;

out vec4 f_color;

void main() {
    vec3 bloomCol = texture(bloomTex, f_uv_1).rgb;
    vec3 sceneCol = texture(sceneTex, f_uv_0).rgb;
    float preLum = 0.99*dot(lumCoef, sceneCol)+0.01;
    float postLum = preLum*(1.0+(preLum/params.y))/(preLum+1.0);
    f_color = vec4(sceneCol*(postLum/preLum)+bloomCol*params.x, 1.0);
}
