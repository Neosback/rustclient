
const int MAX_MATERIAL_COUNT = 2048;
const int MAX_MATERIAL_ID = MAX_MATERIAL_COUNT - 1;

const float GAMMA = 0.7;

uniform int bloom_enabled;
uniform vec4 boardColor;
uniform int boardMaterial;

uniform sampler2DArray textures;
#ifdef texture_array_512
uniform sampler2DArray textures2;
uniform sampler2DArray textures3;
#endif
#ifdef texture_array_256
uniform sampler2DArray textures2;
uniform sampler2DArray textures3;
uniform sampler2DArray textures4;
uniform sampler2DArray textures5;
#endif


in vec2 f_uv;

out vec4 color;

struct MaterialConfig {
    vec4 repeat_animation_speed;
    vec4 bloom_emissive_effect_args;
};

layout (std140) uniform MaterialConfigs {
    MaterialConfig material_configs[MAX_MATERIAL_COUNT];
};

vec4 getTexel(MaterialConfig config, vec2 uv) {
    vec4 c;
    #ifdef texture_array_256
    if (boardMaterial < 256) {
        c = texture(textures, vec3(uv, boardMaterial));
    } else if (boardMaterial < 512) {
        c = texture(textures2, vec3(uv, boardMaterial - 256));
    } else if (boardMaterial < 768) {
        c = texture(textures3, vec3(uv, boardMaterial - 512));
    } else if (boardMaterial < 1024) {
        c = texture(textures4, vec3(uv, boardMaterial - 768));
    } else {
        c = texture(textures5, vec3(uv, boardMaterial - 1024));
    }
    #else
    #ifdef texture_array_512
    if(boardMaterial < 512) {
        c = texture(textures, vec3(uv, boardMaterial));
    } else if (boardMaterial < 1024) {
        c = texture(textures2, vec3(uv, boardMaterial - 512));
    } else {
        c = texture(textures3, vec3(uv, boardMaterial - 1024));
    }
    #else
    c = texture(textures, vec3(uv, boardMaterial));
    #endif
    #endif
    if (config.bloom_emissive_effect_args.x > 0.) {
        // undo gamma corrected inputs, normalize colors
        c.rgb = pow(c.rgb, vec3(1./GAMMA));
        if (bloom_enabled == 0) {
            // Texture emission
            c.rgb *= 1. + config.bloom_emissive_effect_args.y;
        } else {
            // Texture Bloom
            c.rgb *= (1. + ((config.bloom_emissive_effect_args.x / 4096.) * 31.));
        }
    }
    return c;
}

void main() {

    vec4 texture_color = vec4(1, 1, 1, 1);

    if(boardMaterial != MAX_MATERIAL_ID) {
        MaterialConfig config = material_configs[boardMaterial];
        texture_color = getTexel(config, f_uv);
    }

    color = texture_color * boardColor;

    #ifdef gamma_correction
    color.rgb = pow(color.rgb, vec3(2.2));
    #endif
}