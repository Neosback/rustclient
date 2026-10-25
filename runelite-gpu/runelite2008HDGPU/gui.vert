
#ifdef batching
layout (std140) uniform MatrixBlock {
    mat4 positionMatrix;
    mat4 uvMatrix;
    mat4 clipMatrix;
    vec4 color;
    vec4 mode;
};
#else
uniform mat3 positionMatrix;
uniform mat3 uvMatrix;
uniform mat3 clipMatrix;
#endif

in vec2 position;
in vec2 uv;
in vec2 clip;

out vec2 f_uv;
out vec2 f_clip;

void main(void) {
    #ifdef batching
    gl_Position = positionMatrix * vec4(position, 1, 1);
    f_uv = (uvMatrix * vec4(uv, 1, 1)).xy;
    f_clip = (clipMatrix * vec4(clip, 1, 1)).xy;
    #else
    gl_Position = vec4(positionMatrix * vec3(position, 1), 1);
    f_uv = (uvMatrix * vec3(uv, 1)).xy;
    f_clip = (clipMatrix * vec3(clip, 1)).xy;
    #endif
}
