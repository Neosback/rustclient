uniform int fog_enabled;
uniform vec3 fog_color;
uniform float fog_start;
uniform float fog_end;
uniform float fog_density;
uniform float fog_gradient;


in vec4 f_color;
out vec4 frag_color;

in float f_distance;

float fog() {
    float visibility = 0.;
    // edge/wall fog
    visibility += smoothstep(fog_start, fog_end, f_distance);
    // inner fog
    visibility += (1. - exp(-pow(f_distance * fog_density, fog_gradient)));
    return clamp(visibility, 0., 1.);
}

void main() {
    frag_color = f_color;

    #ifdef gamma_correction
    frag_color.rgb = pow(frag_color.rgb, vec3(2.2));
    #endif

    // Fog
    if (fog_enabled == 1) {
        float fog = fog();
        frag_color.rgb = mix(frag_color.rgb, fog_color, fog);
    }
}
