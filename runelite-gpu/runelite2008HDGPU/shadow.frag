uniform sampler2DArray shadowTexture;

uniform int fog_enabled;
uniform vec3 fog_color;
uniform float fog_start;
uniform float fog_end;
uniform float fog_density;
uniform float fog_gradient;

in vec2 f_uv;
in vec2 f_position;
in float f_distance;

out vec4 color;

float fog() {
    float visibility = 0.;
    // edge/wall fog
    visibility += smoothstep(fog_start, fog_end, f_distance);
    // inner fog
    visibility += (1. - exp(-pow(f_distance * fog_density, fog_gradient)));
    return clamp(visibility, 0., 1.);
}

void main(void) {
    float cx = f_uv.x / 256.;
    float cy = f_uv.y / 256.;
    #ifdef region_256
    int layer = (int(cy) * 16) + int(cx);
    #else
    int layer = (int(cy) * 8) + int(cx);
    #endif
    vec3 cuv = vec3(fract(cx), fract(cy), layer);
    float a = texture(shadowTexture, cuv).r;
    color = vec4(0, 0, 0, a);

    #ifdef gamma_correction
    color.a = pow(color.a, 1.0/2.2);
    #endif

    // Fog
    if (fog_enabled == 1) {
        color = mix(color, vec4(fog_color, 0), fog());
    }
}
