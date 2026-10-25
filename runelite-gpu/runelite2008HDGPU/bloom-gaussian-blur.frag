uniform vec3 step;
uniform sampler2D baseTex;

in vec2 f_uv_0;
out vec4 f_color;

void main() {
    vec4 fragCol = texture(baseTex, f_uv_0)*0.091396265;
    fragCol += texture(baseTex, f_uv_0+(-1.0*step.xy))*0.088584304;
    fragCol += texture(baseTex, f_uv_0+(1.0*step.xy))*0.088584304;
    fragCol += texture(baseTex, f_uv_0+(-2.0*step.xy))*0.08065692;
    fragCol += texture(baseTex, f_uv_0+(2.0*step.xy))*0.08065692;
    fragCol += texture(baseTex, f_uv_0+(-3.0*step.xy))*0.068989515;
    fragCol += texture(baseTex, f_uv_0+(3.0*step.xy))*0.068989515;
    fragCol += texture(baseTex, f_uv_0+(-4.0*step.xy))*0.055434637;
    fragCol += texture(baseTex, f_uv_0+(4.0*step.xy))*0.055434637;
    fragCol += texture(baseTex, f_uv_0+(-5.0*step.xy))*0.04184426;
    fragCol += texture(baseTex, f_uv_0+(5.0*step.xy))*0.04184426;
    fragCol += texture(baseTex, f_uv_0+(-6.0*step.xy))*0.029672023;
    fragCol += texture(baseTex, f_uv_0+(6.0*step.xy))*0.029672023;
    fragCol += texture(baseTex, f_uv_0+(-7.0*step.xy))*0.019765828;
    fragCol += texture(baseTex, f_uv_0+(7.0*step.xy))*0.019765828;
    fragCol += texture(baseTex, f_uv_0+(-8.0*step.xy))*0.012369139;
    fragCol += texture(baseTex, f_uv_0+(8.0*step.xy))*0.012369139;
    f_color = fragCol;
}
