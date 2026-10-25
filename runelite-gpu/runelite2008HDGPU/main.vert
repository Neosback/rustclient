const vec3 DEFAULT_WATER_COLOR = vec3(0.073, 0.169, 0.24);

uniform mat4 transform;
uniform mat4 projection;
uniform float view_distance;
uniform float base_depth_shift;
uniform vec3 camera_position;
uniform vec3 fog_camera_position;

uniform int anim_enabled;
uniform int lights_enabled;
uniform vec4 model_lights;


uniform sampler2D waterColorDepthTexture;
uniform int underworld;

uniform sampler2D heightsTexture;
uniform int alignment;
uniform float alignment_arg;
uniform int alignment_floor;
uniform float alignment_bounds;
uniform vec3 alignment_point;


struct LightParams {
    vec4 tl;
    vec4 wl;
    vec4 wallFlags;
};

layout (std140) uniform BoneTransforms {
    mat4 bone_transforms[1024];
};

layout (std140) uniform SkinTransforms {
    vec4 skin_transforms[512];
};

layout (std140) uniform VertexLights {
    LightParams vertex_lights[1024];
};

layout (std140) uniform MatrixBlock {
    mat4 model_transform;
};

in vec3 position;
in vec4 color;
in vec3 normal;
in vec2 uv;
in int material;
in float depth_shift;

centroid out vec4 f_color; // centroid: MSAA edge samples must not extrapolate vertex colors, negative values NaN in gamma pow
out vec3 f_normal;
out vec2 f_uv;
out vec3 f_position;
flat out int f_material;
flat out vec4 f_lights;
out float f_distance;
out vec3 f_water_color;
out vec2 f_water_height_density;


float heightTile(vec2 tile) {
    return textureLod(heightsTexture, tile / 256., 0.)[alignment_floor];
}
// RuneTek height interpolation behaves differently (can't use linear filering / weighted average)
float height(vec2 pos) {
    vec2 tile = pos / 128.;
    float h00 = heightTile(tile + vec2(0., 0.));
    float h10 = heightTile(tile + vec2(1., 0.));
    float h11 = heightTile(tile + vec2(1., 1.));
    float h01 = heightTile(tile + vec2(0., 1.));
    vec2 dt = fract(tile);
    return mix(mix(h00, h10, dt.x), mix(h01, h11, dt.x), dt.y);
}
vec4 align(vec4 mpos) {
    vec2 wp = (model_transform * mpos).xz;
    if (alignment == 1) {
        mpos.y += height(wp) - alignment_point.y;
    } else if (alignment == 2) {
        float ay = alignment_point.y;
        float minY = alignment_bounds;
        float v = (mpos.y * 65536.) / minY;
        float a = alignment_arg;
        if (v < a) {
            mpos.y = ((height(wp) - ay) * (a - v)) / a;
        }
    }
    return mpos;
}

mat4 project() {
    float depth_scale = (base_depth_shift + depth_shift) * 1.5;
    if (depth_scale == 0.) return projection;

    // 1. some cards do not like mutating the matrix directly:
    //  proj[2][2] = proj[2][2] + b; <<== BAD
    // 2. some macs are just stupid, and this this form is what makes it work..

    vec4 proj2 = projection[2];
    vec4 proj3 = projection[3];

    const float depth_offset = 6144.;
    const float depth_range_end = 1.;
    float scaled_offset = depth_offset / (depth_scale + depth_offset);
    float offset = (-depth_range_end * (1. - scaled_offset) * (1. - scaled_offset) * proj3.z) / depth_scale;

    proj2.z += offset;
    proj3.z *= (scaled_offset * scaled_offset);

    return mat4(
        projection[0],
        projection[1],
        proj2,
        proj3
    );
}

int wallMask(vec3 mpos) {
    //const int[] WALL_MASKS = int[](76, 8, 137, 4, 0, 1, 38, 2, 19);
    //int flags = 0;
    // orgional logic (seems less percise since it rounds to the tile)
    /*int camTileX = int(camera_position.x / 128);
    int camTileY = int(camera_position.z / 128);
    int tileX = int(mpos.x / 128);
    int tileY = int(mpos.z / 128);
    if (camTileX == tileX) {
        flags++;
    } else if (camTileX < tileX) {
        flags += 2;
    }
    if (camTileY == tileY) {
        flags += 3;
    } else if (camTileY > tileY) {
        flags += 6;
    }*/
    /* if (camera_position.x < mpos.x) {
         flags += 2;
     }
     if (camera_position.z > mpos.z) {
         flags += 6;
     }
     int mask = WALL_MASKS[flags];*/

    /*if(a) {
        if(b) {
            mask = 19;
        } else {
            mask = 137;
        }
    } else {
        if(b) {
            mask = 38;
        } else {
            mask = 76;
        }
    }*/

    /*bool a = camera_position.x < mpos.x;
    bool b = camera_position.z > mpos.z;
    int mask = 0;
    if(!a && !b) { mask = 76;  } // 0
    else if( a && !b) { mask = 137; } // 2
    else if(!a &&  b) { mask = 38;  } // 6
    else if( a &&  b) { mask = 19;  } // 8*/

    const float M0 = 76.;  // !a && !b
    const float M2 = 137.; //  a && !b
    const float M6 = 38.;  // !a &&  b
    const float M8 = 19.;  //  a &&  b

    return int(mix(
        mix(M8,M2,step(camera_position.z,mpos.z)),
        mix(M6,M0,step(camera_position.z,mpos.z)),
        step(mpos.x,camera_position.x)
    ));
}

void main(void) {
    // animate the positon if needed
    vec4 mpos = vec4(position, 1);
    vec4 col = color;

    mat4 trans = mat4(1.0);
    if (anim_enabled != 0) {

        if ((anim_enabled & 0x1) != 0) {
            int bone = ((material >> 20) & 1023) - 1;
            if (bone >= 0) {
                trans = bone_transforms[bone];
                mpos *= trans;
            } else {
                // col = 0xff0000ff;
            }
        }

        // col = vec4(mod(label/5., 1.), mod(label/14., 1.), mod(label/63., 1.), 1.);

        if ((anim_enabled & 0x2) != 0) {
            int skin = ((material >> 11) & 511) - 1;
            if (skin >= 0) {
                vec4 delta = skin_transforms[skin];
                // alpha
                col.a = clamp(col.a + delta.a, 0., 1.);
                // rgb // TODO needs testing, most anims dont recolor (only alpha), we can filter/optimize
                /* col = convertRGBtoHSL(col);
                   col.s = fract(col.s + delta.s);
                   col.tp = clamp(col.tp + delta.tp, 0., 1.);
                   col = convertHSLtoRGB(col);*/
            }
        }
    }

    if(alignment != 0) {
        mpos = align(mpos);
    }

    mpos = model_transform * mpos;
    //vec4 pos = projection * transform * mpos;
    //pos.z += (base_depth_shift + depth_shift) / 10;
    vec4 epos = transform * mpos;
    gl_Position = project() * epos;

    f_distance = length(fog_camera_position.xz - mpos.xz);

    f_position = mpos.xyz;

    // transform normals
    // we only want to apply the rotation, we retain the origional non-unit normal scaling
    // TODO in most cases this is not needed (ex: static scenery / chunks)
    // TODO in some animations, it seems its optional to adjust the normals.. (for some reason)
    // - scaling is non-uniform
    mat3 normalMatrix = transpose(inverse(mat3(trans * model_transform)));
    // - normalize the normal when applying the matrix
    // - remove non-uniform scaling from the transform by normalizing the result
    // - re-apply the origional normal scale to the rotated normal
    vec3 transformedNormal = normalize(normalMatrix * normalize(normal)) * length(normal);
    f_normal = isnan(transformedNormal.x) ? vec3(0) : transformedNormal;
    // f_normal = normal;

    f_color = col;
    f_uv = uv;
    f_material = material & 0x7ff;

    f_lights = vec4(0, 0, 0, 0);

    if(underworld == 1) {
        float tileX = clamp(position.x / 128., 2., 244.);
        float tileY = clamp(position.z / 128., 2., 244.);
        vec4 data = textureLod(waterColorDepthTexture, vec2(tileX / 256., tileY / 256.), 0.);
        f_water_color = data.rgb;
        int waterData = int(data.a);
        float surfaceHeight = float((waterData >> 16) & 0xffff);
        float density = float(waterData & 0xffff) * 8.;
        f_water_height_density = vec2(surfaceHeight,density);
    } else {
        f_water_color = DEFAULT_WATER_COLOR;
        f_water_height_density = vec2(0.);
    }

    if (lights_enabled == 1) {
        // dynamic mode
        f_lights = model_lights;
    } else if (lights_enabled == 2) {
        // landscape mode
        int label = ((material >> 11) & 2047) - 1;
        if (label >= 0) {
            LightParams params = vertex_lights[label];
            int wallFlags = int(params.wallFlags.x);
            if (wallFlags == 0) {
                // no wall to check
                f_lights = params.tl;
            } else if ((wallFlags & 768) != 0){
                // check diagonal wall
                float dx = mpos.x - camera_position.x;
                float dz = mpos.z - camera_position.z;
                if ((wallFlags & 256) != 0) {
                    if (dz > -dx) {
                        f_lights = params.tl;
                    } else {
                        f_lights = params.wl;
                    }
                } else /*if((params.wallFlags & 512) != 0) */ {
                    if (dz > dx) {
                        f_lights = params.tl;
                    } else {
                        f_lights = params.wl;
                    }
                }
            } else {
                // check normal wall
                int mask = wallMask(mpos.xyz);
                if ((wallFlags & mask) != 0) {
                    f_lights = params.wl;
                } else {
                    f_lights = params.tl;
                }
            }
        }
    }

}
