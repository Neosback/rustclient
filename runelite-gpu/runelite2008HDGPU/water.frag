in vec4 f_color;
in float f_distance;

out vec4 color;

void main(void) {
    color = f_color;

    #ifdef gamma_correction
    color.rgb = pow(color.rgb, vec3(2.2));
    #endif
}
