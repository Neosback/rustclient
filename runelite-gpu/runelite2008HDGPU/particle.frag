
const int MAX_MATERIAL_COUNT = 2048;
const int MAX_MATERIAL_ID = MAX_MATERIAL_COUNT - 1;

const int EFFECT_NONE = 0;
const int EFFECT_SPECULAR = 1;
const int EFFECT_FLUID = 2;
const int EFFECT_WATER_WAVES = 4;
const int EFFECT_WATER_FALL = 5;
const int EFFECT_EMISSIVE = 6;

const float GAMMA = 0.7;

uniform vec3 diffuse_color;
uniform float ambient_intensity;
uniform int bloom_enabled;

uniform int fog_enabled;
uniform vec3 fog_color;
uniform float fog_start;
uniform float fog_end;
uniform float fog_density;
uniform float fog_gradient;

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

out vec4 color;

in vec4 f_color;
in float f_distance;
flat in int f_material;
flat in int f_ambient;



struct MaterialConfig {
    // Integraded macs (2015?) do not play nice...
    // Keeping the uniform structure simple for them is needed.
    vec4 repeat_animation_speed;
    vec4 bloom_emissive_effect_args;
};

layout (std140) uniform MaterialConfigs {
    MaterialConfig material_configs[MAX_MATERIAL_COUNT];
};

vec4 getTexel(MaterialConfig config, vec2 uv) {
    vec4 c;
    #ifdef texture_array_256
    if (f_material < 256) {
        c = texture(textures, vec3(uv, f_material));
    } else if (f_material < 512) {
        c = texture(textures2, vec3(uv, f_material - 256));
    } else if (f_material < 768) {
        c = texture(textures3, vec3(uv, f_material - 512));
    } else if (f_material < 1024) {
        c = texture(textures4, vec3(uv, f_material - 768));
    } else {
        c = texture(textures5, vec3(uv, f_material - 1024));
    }
    #else
    #ifdef texture_array_512
    if(f_material < 512) {
        c = texture(textures, vec3(uv, f_material));
    } else if (f_material < 1024) {
        c = texture(textures2, vec3(uv, f_material - 512));
    } else {
        c = texture(textures3, vec3(uv, f_material - 1024));
    }
    #else
    c = texture(textures, vec3(uv, f_material));
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

float fog() {
    float visibility = 0.;
    // edge/wall fog
    visibility += smoothstep(fog_start, fog_end, f_distance);
    // inner fog
    visibility += (1. - exp(-pow(f_distance * fog_density, fog_gradient)));
    return clamp(visibility, 0., 1.);
}

void main() {

    if (f_material > MAX_MATERIAL_ID) {
        color = vec4(1, 0, 0, 1);
        return;
    }

    MaterialConfig config = material_configs[f_material];

    vec2 uv = gl_PointCoord;

    vec4 texture_color = vec4(1, 1, 1, 1);

    if(f_material != MAX_MATERIAL_ID) {
        texture_color = getTexel(config, uv);
    }

    // Ambient lighting
    vec3 brightness = mix(vec3(1, 1, 1), diffuse_color * ambient_intensity, f_ambient);

    // Emissive lighting
    if (int(config.bloom_emissive_effect_args.z) == EFFECT_EMISSIVE) {
        brightness = vec3(1, 1, 1);
    }

    color = texture_color * f_color;
    color.rgb *= max(brightness, 0.0);

    #ifdef gamma_correction
    color.rgb = pow(color.rgb, vec3(2.2));
    #endif

    // Fog
    if (fog_enabled == 1) {
        color.rgb = mix(color.rgb, fog_color, fog());
    }
}