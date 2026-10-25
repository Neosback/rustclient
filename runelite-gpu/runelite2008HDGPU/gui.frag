
uniform sampler2D tex;
uniform sampler2D clipTex;

#ifdef batching
layout (std140) uniform MatrixBlock {
    mat4 positionMatrix;
    mat4 uvMatrix;
    mat4 clipMatrix;
    vec4 color;
    vec4 mode;
};
#else
uniform vec4 color;
uniform int mode;
uniform float arc_startAngle;
uniform float arc_endAngle;
uniform float arc_innerRadius;
#endif

in vec2 f_uv;
in vec2 f_clip;

out vec4 f_color;

int getMode() {
    #ifdef batching
    return int(mode.x);
    #else
    return mode;
    #endif
}

vec3 arcParams() {
    #ifdef batching
    return vec3(mode.y, mode.z, mode.w);
    #else
    return vec3(arc_startAngle, arc_endAngle, arc_innerRadius);
    #endif
}

// fill: innerRadius = 0.0
// draw/outline: innerRadius = 1.0 - thickness (normalized to the radius of the arc)
float arc(float u_startAngle, float u_endAngle, float u_innerRadius) {

    const float M_PI = 3.141592653589793;
    const float M_TWO_PI = 6.283185307179586;

    float startAngle = radians(u_startAngle);
    float endAngle = radians(u_endAngle);

    float innerRadius = u_innerRadius;
    float outerRadius = 1.0;

    startAngle = clamp(startAngle, 0.0, endAngle);
    endAngle = clamp(endAngle, startAngle, M_TWO_PI);

    vec2 centerPos = f_uv;

    float dist = length(centerPos);

    float delta = fwidth(dist);

    float inner = smoothstep(innerRadius, innerRadius + delta, dist);
    float outer = smoothstep(outerRadius, outerRadius - delta, dist);
    float bandAlpha = inner * outer;

    float angle = atan(centerPos.y, centerPos.x) + M_PI;

    float startEdge = smoothstep(angle, angle - delta, startAngle);
    float endEdge = smoothstep(angle, angle + delta, endAngle);
    float angleAlpha = startEdge * endEdge;

    return bandAlpha * angleAlpha;
}

void main() {

    #ifndef batching
    // if not batching, force fragment to near plane to be always on top of scenery
    gl_FragDepth = 0;
    #endif

    f_color = color;

    if (getMode() == 1) {
        f_color *= texture(tex, f_uv);
    } else if (getMode() == 2) {
        f_color *= texture(tex, f_uv);
        f_color.a *= texture(clipTex, f_clip).a;
    } else if (getMode() == 3) {
        vec3 params = arcParams();
        f_color.a *= arc(params.x, params.y, params.z);
    }

    #ifdef gamma_correction
    f_color.rgb = pow(f_color.rgb, vec3(2.2));
    #endif
}
