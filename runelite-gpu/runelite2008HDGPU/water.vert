const vec4 DEFAULT_WATER_COLOR = vec4(0.073, 0.169, 0.24, 1.0);

const int MAX_MATERIAL_COUNT = 2048;
const int MAX_MATERIAL_ID = MAX_MATERIAL_COUNT - 1;

uniform mat4 transform;
uniform mat4 projection;
uniform float base_depth_shift;
uniform vec3 diffuse_color;
uniform float ambient_intensity;
uniform float diffuse_intensity;
uniform int underworld;

uniform sampler2D waterColorDepthTexture;

struct MaterialConfig {
    vec4 repeat_animation_speed;
    vec4 bloom_emissive_effect_args;
};

layout (std140) uniform MaterialConfigs {
    MaterialConfig material_configs[MAX_MATERIAL_COUNT];
};

in vec3 position;
in int material;
in float depth_shift;

out vec4 f_color;
out float f_distance;

void main(void) {

    // only render water verticies
    if(int(material_configs[material & 0x7ff].bloom_emissive_effect_args.z) == 4) {
        vec4 epos = transform * vec4(position, 1);
        gl_Position = projection * epos;
        gl_Position.z += (base_depth_shift + (depth_shift)) / 10.;

        // fetch water color
        vec4 waterColor = DEFAULT_WATER_COLOR;
        if(underworld == 1) {
            float tileX = clamp(position.x / 128., 2., 244.);
            float tileY = clamp(position.z / 128., 2., 244.);
            vec4 data = textureLod(waterColorDepthTexture, vec2(tileX / 256., tileY / 256.), 0.);
            waterColor = data.a != 0.0 ? vec4(data.rgb, 1) : DEFAULT_WATER_COLOR;
        }

        waterColor.rgb *= ambient_intensity + diffuse_intensity;
        waterColor.rgb *= 0.58823526;
        waterColor.rgb *= diffuse_color;

        f_color = waterColor;
    } else {
        // discard vertex
        gl_Position = vec4(0);
    }

}
