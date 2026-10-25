
const int MAX_MATERIAL_COUNT = 2048;
const int MAX_MATERIAL_ID = MAX_MATERIAL_COUNT - 1;
const int MAX_LIGHTS = 1023;

const int EFFECT_NONE = 0;
const int EFFECT_SPECULAR = 1;
const int EFFECT_FLUID = 2;
const int EFFECT_WATER_WAVES = 4;
const int EFFECT_WATER_FALL = 5;
const int EFFECT_EMISSIVE = 6;

const float W = 1.0 / 1024.0; // 0.0009765625
const float GAMMA = 0.7;
uniform float time;
uniform vec3 diffuse_color;
uniform float ambient_intensity;
uniform float diffuse_intensity;
uniform float diffuse_negate_intensity;
uniform vec3 sun_position;
uniform int lights_enabled;
uniform int bloom_enabled;
uniform int floor;
uniform int underworld;
uniform int high_water_detail;
uniform mat3 specularTransform;

uniform int fog_enabled;
uniform vec3 fog_color;
uniform float fog_start;
uniform float fog_end;
uniform float fog_density;
uniform float fog_gradient;
uniform int regionlocker_fog_enabled;
uniform int regionlocker_fog_exclude;
uniform int regionlocker_rect_count;
uniform vec3 regionlocker_fog_color;
uniform float regionlocker_fog_strength;
uniform float regionlocker_fog_fade;
uniform vec4 regionlocker_rect_0;
uniform vec4 regionlocker_rect_1;
uniform vec4 regionlocker_rect_2;
uniform vec4 regionlocker_rect_3;
uniform vec4 regionlocker_rect_4;
uniform vec4 regionlocker_rect_5;
uniform vec4 regionlocker_rect_6;
uniform vec4 regionlocker_rect_7;
uniform vec4 regionlocker_rect_8;
uniform vec4 regionlocker_rect_9;
uniform vec4 regionlocker_rect_10;
uniform vec4 regionlocker_rect_11;
uniform vec4 regionlocker_rect_12;
uniform vec4 regionlocker_rect_13;
uniform vec4 regionlocker_rect_14;
uniform vec4 regionlocker_rect_15;

uniform vec4 highlight_color;
uniform float highlight_weight;
uniform vec4 outline_color;

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
uniform sampler2DArray lightTexture;
uniform sampler3D waterTexture;
uniform sampler3D waterFallTexture;
uniform samplerCube spec1;
uniform samplerCube spec2;
uniform samplerCube spec3;


struct MaterialConfig {
    // Integraded macs (2015?) do not play nice...
    // Keeping the uniform structure simple for them is needed.
    vec4 repeat_animation_speed;
    vec4 bloom_emissive_effect_args;
};

struct Light {
    vec4 pos_attenuation;
    vec4 diffuse_radius;
};

layout (std140) uniform MaterialConfigs {
    MaterialConfig material_configs[MAX_MATERIAL_COUNT];
};

layout (std140) uniform Lights {
    Light lights[MAX_LIGHTS];
};

layout (std140) uniform FluidNoise {
    vec4 fluid_noise[64];
};

centroid in vec4 f_color; // centroid: MSAA edge samples must not extrapolate vertex colors, negative values NaN in gamma pow
in vec3 f_normal;
in vec2 f_uv;
in vec3 f_position;
in float f_distance;
// TODO filter out if not in use
in vec3 f_water_color;
in vec2 f_water_height_density;

flat in int f_material;
flat in vec4 f_lights;

out vec4 color;


vec2 fluidNoise() {
    vec2 noiseCoords = vec2(f_position.x + f_position.z, f_position.z - f_position.x);
    noiseCoords *= 0.0001220703125;
    noiseCoords = fract(noiseCoords);
    noiseCoords *= 256.0;
    float nx = fluid_noise[int(noiseCoords.x)].x;
    float ny = fluid_noise[int(noiseCoords.y)].y;
    return vec2(nx, ny);
}

vec4 getLights(float tileX, float tileY, int edge) {
    return texture(lightTexture, vec3((float(edge) * 0.25) + (tileX / 1024.), tileY / 256., floor));
}

vec3 light(vec3 normal, Light light) {
    // Light position relative to point hit
    vec3 lp = light.pos_attenuation.xyz - f_position;

    if(lights_enabled == 3) {
        float distance = length(lp);
        lp /= distance;
        float att = distance / light.diffuse_radius.a;
        float a = max(0.0, 1.0 - (att * att));
        float b = max(0.0, dot(lp, normal));
        float c = min(1.0, a * b * 2.0);
        return min(vec3(1.0), light.diffuse_radius.rgb * c);
    } else {
        float distance = length(lp);

        // Attenuation: factor by which light is dimmed due to being further away from surface
        float attenuation = 1.0 / (light.pos_attenuation.w * distance * distance);

        // Lambertian: factor by which light is dimmed due to hitting surface at an angle
        float lambertian = max(dot(normalize(f_normal), normalize(lp)), 0.0);

        // Final color TODO: hack, we shouldn't need to clamp to 1
        vec3 final = light.diffuse_radius.rgb * attenuation * lambertian;
        float brightness = length(final) / length(vec3(1, 1, 1));

        if (brightness > 1.0) {
            final *= 1.0 / brightness;
        }

        return final;
    }
}


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

vec4 regionlockerRect(int index) {
    if (index == 0) return regionlocker_rect_0;
    if (index == 1) return regionlocker_rect_1;
    if (index == 2) return regionlocker_rect_2;
    if (index == 3) return regionlocker_rect_3;
    if (index == 4) return regionlocker_rect_4;
    if (index == 5) return regionlocker_rect_5;
    if (index == 6) return regionlocker_rect_6;
    if (index == 7) return regionlocker_rect_7;
    if (index == 8) return regionlocker_rect_8;
    if (index == 9) return regionlocker_rect_9;
    if (index == 10) return regionlocker_rect_10;
    if (index == 11) return regionlocker_rect_11;
    if (index == 12) return regionlocker_rect_12;
    if (index == 13) return regionlocker_rect_13;
    if (index == 14) return regionlocker_rect_14;
    return regionlocker_rect_15;
}

float regionlockerFog() {
    if (
        regionlocker_fog_enabled == 0 ||
        regionlocker_fog_exclude == 1 ||
        regionlocker_rect_count == 0 ||
        regionlocker_fog_strength <= 0.
    ) {
        return 0.;
    }

    float visibility = 0.;
    for (int i = 0; i < 16; i++) {
        if (i >= regionlocker_rect_count) {
            break;
        }

        vec4 rect = regionlockerRect(i);
        if (
            f_position.x < rect.x || f_position.x > rect.z ||
            f_position.z < rect.y || f_position.z > rect.w
        ) {
            continue;
        }

        float inwardDistance = min(
            min(f_position.x - rect.x, rect.z - f_position.x),
            min(f_position.z - rect.y, rect.w - f_position.z)
        );
        float fade = max(regionlocker_fog_fade, 1.);
        float wall = smoothstep(-128., fade * 0.5, inwardDistance);
        float inner = 1. - exp(-pow(max(inwardDistance, 0.) / fade, 1.35));
        visibility = max(visibility, clamp(max(wall, inner), 0., 1.));
    }

    return visibility * regionlocker_fog_strength;
}

void main(void) {
    if (f_material > MAX_MATERIAL_ID) {
        color = vec4(1, 0, 0, 1);
        return;
    }
    if (outline_color.a > 0.) {
        color = outline_color;
        // apply fog to outline
        if (fog_enabled == 1) {
            color.rgb = mix(color.rgb, fog_color, fog());
        }
        float regionlocker = regionlockerFog();
        if (regionlocker > 0.) {
            color.rgb = mix(color.rgb, regionlocker_fog_color, regionlocker);
        }
        return;
    }

    MaterialConfig config = material_configs[f_material];

    int effect = int(config.bloom_emissive_effect_args.z);
    int effect_args = int(config.bloom_emissive_effect_args.w);

    //////////////////////////////////////
    //             Texture              //
    //////////////////////////////////////

    vec2 uv = f_uv;

    // Animation
    // (water waves disables animations)
    if ((high_water_detail == 0 || effect != EFFECT_WATER_WAVES)) {
        uv += time * config.repeat_animation_speed.zw / 128.0;
    }

    // Repeat
    uv.x = mix(clamp(uv.x, 0., 1.), uv.x, config.repeat_animation_speed.x);
    uv.y = mix(clamp(uv.y, 0., 1.), uv.y, config.repeat_animation_speed.y);

    vec4 texture_color = vec4(1, 1, 1, 1);
    if(f_material != MAX_MATERIAL_ID) {
        if(effect == EFFECT_NONE || effect == EFFECT_SPECULAR || effect == EFFECT_EMISSIVE) {

            texture_color = getTexel(config, uv);

        } else if(effect == EFFECT_FLUID) { // fluid surface tention "jiggle"

            // bool waterMovement = (effect_args & 128) == 0;
            // bool ambient = (effect_args & 64) == 0;
            float turbulence = 0.025 * pow(2.0, float(effect_args & 3));
            vec2 noise = turbulence * fluidNoise();
            vec2 oTexCoord0 = vec2(f_position.xz * 0.0078125 + noise);
            texture_color = getTexel(config, oTexCoord0);

        } else if(effect == EFFECT_WATER_WAVES) { // water movement
            // https://www.khronos.org/opengl/wiki/Mathematics_of_glTexGen

            if(high_water_detail == 1) {
                bool animate = (effect_args & 1) == 1;

                const vec4 sPlane = vec4(W, 0., 0., 0.);
                const vec4 tPlane = vec4(0., 0., W, 0.);
                vec4 rPlane = vec4(0., 0., 0., animate ? time / 200. : 0.);
                vec4 pos = vec4(f_position, 1.);
                vec3 uv3 = vec3(dot(pos, sPlane), dot(pos, tPlane), dot(pos, rPlane));

                texture_color = texture(waterTexture, uv3).rrrg;
            } else {
                texture_color = getTexel(config, uv);
            }

        } else if(effect == EFFECT_WATER_FALL) { // waterfall

            float speed0 = (float(effect_args & 3) + 1.) * -0.01;
            float speed1 = (float(effect_args >> 3 & 3) + 1.) * 0.01;
            float spacing = (effect_args & 64) == 0 ? (W / 2.) : W;
            bool vertical = (effect_args & 128) != 0;

            vec4 sPlane = vertical ? vec4(spacing, 0., 0., 0.) : vec4(0., 0., spacing, 0.);
            vec4 tPlane = vec4(0., spacing, 0., time * speed0);
            vec4 rPlane = vec4(0., 0., 0., time * speed1);
            vec4 pos = vec4(f_position, 1);
            vec3 uv3 = vec3(dot(pos, sPlane), dot(pos, tPlane), dot(pos, rPlane));

            texture_color = texture(waterFallTexture, uv3).rrrg;
        }
    }

    // Lighting
    vec3 brightness = vec3(0, 0, 0);

    // Ambient lighting
    brightness += diffuse_color * ambient_intensity;

    // Diffuse lighting
    vec3 n = f_normal;
    vec3 l = normalize(sun_position);
    float lambertian = clamp(dot(n, l), 0.0, 1.0);

    brightness += diffuse_intensity * lambertian * diffuse_color;

    // Negate diffuse
    float lambertianNegate = clamp(dot(n, -l), 0.0, 1.0);
    brightness -= diffuse_negate_intensity * lambertianNegate * diffuse_color;

    // Emissive lighting
    if (effect == EFFECT_EMISSIVE) {
        brightness = vec3(1, 1, 1);
    }

    // Lights
    if (lights_enabled > 0) {
        vec4 pLights;

        if (lights_enabled == 3) {
            // terrain
            float tileX = f_position.x / 128.0;
            float tileY = f_position.z / 128.0;

            float fx = fract(tileX);
            float fy = fract(tileY);

            // 0 == north, 1 == west, 2 == east, 3 == south
            int edge = int(step(fy, fx) * 2.0 + step(fy, 1.0 - fx));

            pLights = getLights(tileX, tileY, edge);
        } else {
            // landscape or dynamic
            pLights = f_lights;
        }

        if (dot(pLights, pLights) > 0.0) {
            pLights *= float(MAX_LIGHTS);

            if (pLights.x > 0.0) {
                brightness += light(n, lights[int(pLights.x) - 1]);
            }

            if (pLights.y > 0.0) {
                brightness += light(n, lights[int(pLights.y) - 1]);
            }

            if (pLights.z > 0.0) {
                brightness += light(n, lights[int(pLights.z) - 1]);
            }

            if (pLights.w > 0.0) {
                brightness += light(n, lights[int(pLights.w) - 1]);
            }
        }
    }

    // Final color calculation
    if(effect == EFFECT_WATER_WAVES && high_water_detail == 1) {
        const vec4 waveColor = vec4(0.1F, 0.1F, 0.15F, 0.1F);
        color = waveColor + texture_color;
        color.rgb *= 2.;
        // wave color is not effected by the environment
    } else if(effect == EFFECT_SPECULAR) {
        color = texture_color * f_color;
        color.rgb *= max(brightness, 0.0);

        // specular effect adds to the final color
        vec3 dir = specularTransform * n;
        float spec = 0.0;
        if(effect_args == 1) {
            spec = texture(spec1, dir).r;
        } else if(effect_args == 2) {
            spec = texture(spec2, dir).r;
        } else if(effect_args == 3) {
            spec = texture(spec3, dir).r;
        }

        // the alpha channel of the texture indicates where to apply the effect
        color.rgb += texture_color.rgb * spec * texture_color.a;
        color.a = f_color.a;
    } else if(effect == EFFECT_FLUID) {
        color = f_color * texture_color;
        // enable/disable ambient lighting
        if((effect_args & 64) == 0) {
            // note: it only applies the ambient light source, no diffuse or point lights.
            color.rgb *= diffuse_color * ambient_intensity;
        }
        // enable/disable waves
        if((effect_args & 128) == 0) {
            vec3 oTexCoord1 = vec3(f_position.xz * W, time / 200.);
            vec4 waterColor = texture(waterTexture, oTexCoord1).rrrg;
            color += waterColor;
        }
    } else {
        color = texture_color * f_color;
        color.rgb *= max(brightness, 0.0);
    }

    // Water depth effect
    if(underworld != 0) {
        vec3 waterColor = f_water_color;
        float surfaceHeight = f_water_height_density.x;
        float density = f_water_height_density.y;
        // note: surfaceHeight is always >= 0
        float depth = surfaceHeight + f_position.y;
        float waterDepth = clamp(depth / density, 0., 1.);

        waterColor *= ambient_intensity + diffuse_intensity;
        waterColor *= 0.58823526;
        waterColor *= diffuse_color;

        const vec4 sPlane = vec4(0.001, 9.0E-4, 0., 0.);
        const vec4 tPlane = vec4(0., 9.0E-4, 0.001, 0.);
        vec4 rPlane = vec4(0., 0., 0., time / 200.);
        vec4 pos = vec4(f_position, 1);
        vec3 uv3 = vec3(dot(pos, sPlane), dot(pos, tPlane), dot(pos, rPlane));
        vec4 wt = texture(waterTexture, uv3).rrrg;

        color.rgb += wt.rgb;
        color.rgb = mix(color.rgb, waterColor, 0.5);
        color.rgb = mix(color.rgb, waterColor, waterDepth);
        // hide surface water that is behind the fog, when enabled
        color.rgb = mix(color.rgb, fog_color, fog_enabled * step(fog_end, f_distance) * (1. - waterDepth));
    }

    // clamp emissive effect to avoid over saturation
    if (bloom_enabled == 0) {
        float brightness = length(color.rgb) / length(vec3(1, 1, 1));
        // if brightness >= 1 && brightness > 0
        color.rgb /= mix(1., brightness, step(1., brightness) * (1. - step(1., 1. - brightness)));
    }

    // Highlighting
    color.rgb = mix(color.rgb, highlight_color.rgb, highlight_weight);
    color.a *= (1. - highlight_color.a);

    #ifdef gamma_correction
    color.rgb = pow(max(color.rgb, 0.), vec3(2.2)); // negative base NaNs, poisons bloom
    #endif

    // Fog
    if (fog_enabled == 1 && underworld == 0) {
        float fog = fog();
        color.rgb = mix(color.rgb, fog_color, fog);
        // fix underworld wall along region edge from showing
        if (effect == EFFECT_WATER_WAVES) {
            color.a = min(1., color.a + fog);
        }
    }

    float regionlocker = regionlockerFog();
    if (regionlocker > 0.) {
        color.rgb = mix(color.rgb, regionlocker_fog_color, regionlocker);
    }
}
