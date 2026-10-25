
uniform mat4 projection;
uniform int anim_enabled;

layout (std140) uniform BoneTransforms {
// https://opengl.gpuinfo.org/displaycapability.php?name=GL_MAX_VERTEX_UNIFORM_COMPONENTS
// TODO allow for smaller uniforms, this requires >= 2048
// TODO support update_normals
    mat4 boneTransforms[1024];
};

in vec3 position;
in int material;

void main(void) {

    vec4 mpos = vec4(position, 1);

    if (anim_enabled == 1) {
        int label = ((material >> 20) & 1023) - 1;
        if (label >= 0) {
            mpos *= boneTransforms[label];
        }
    }

    gl_Position = projection * mpos;
}
