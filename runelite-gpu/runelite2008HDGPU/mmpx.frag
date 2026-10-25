/*
 * Copyright (c) 2025 Brainfree <https://gitlab.com/brainfree1337>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/*
 MMPX Style-Preserving Pixel Art Magnification Algorithmsince

 Implementation based on:
 "MMPX Style-Preserving Pixel Art Magnification" by Morgan McGuire and Mara Gagiu
 Journal of Computer Graphics Techniques, Vol. 10, No. 2, 2021
 https://jcgt.org/published/0010/02/04/

 This algorithm magnifies pixel art by exactly 2x in each dimension while preserving
 artistic style, sharp corners, palette, transparency, and single-pixel features.
*/

#version 140

uniform sampler2D srcTexture;
uniform ivec2 srcSize;

in vec2 vTexCoord;

out vec4 fragColor;

// Convert normalized float to uint [0-255]
uint floatToUint(float f) {
    return uint(clamp(f * 255.0 + 0.5, 0.0, 255.0));
}

// Convert uint [0-255] to normalized float
float uintToFloat(uint u) {
    return float(u) / 255.0;
}

// Sample texture as uint values with clamping
uvec4 sampleClamped(ivec2 coord) {
    coord = clamp(coord, ivec2(0), srcSize - 1);
    vec2 texCoordFloat = (vec2(coord) + 0.5) / vec2(srcSize);
    vec4 pixel = texture(srcTexture, texCoordFloat);
    return uvec4(
        floatToUint(pixel.r),
        floatToUint(pixel.g),
        floatToUint(pixel.b),
        floatToUint(pixel.a)
    );
}

// Fast luminance approximation with transparency, assuming bright background
uint luma(uvec4 color) {
    uint alpha = color.a;
    return (color.r + color.g + color.b + 1u) * (256u - alpha);
}

// Utility functions for pattern matching
bool colorEq(uvec4 a, uvec4 b) {
    return a.r == b.r && a.g == b.g && a.b == b.b && a.a == b.a;
}

bool all_eq2(uvec4 b, uvec4 a0, uvec4 a1) {
    return colorEq(b, a0) && colorEq(b, a1);
}

bool all_eq3(uvec4 b, uvec4 a0, uvec4 a1, uvec4 a2) {
    return colorEq(b, a0) && colorEq(b, a1) && colorEq(b, a2);
}

bool all_eq4(uvec4 b, uvec4 a0, uvec4 a1, uvec4 a2, uvec4 a3) {
    return colorEq(b, a0) && colorEq(b, a1) && colorEq(b, a2) && colorEq(b, a3);
}

bool any_eq3(uvec4 b, uvec4 a0, uvec4 a1, uvec4 a2) {
    return colorEq(b, a0) || colorEq(b, a1) || colorEq(b, a2);
}

bool none_eq2(uvec4 b, uvec4 a0, uvec4 a1) {
    return !colorEq(b, a0) && !colorEq(b, a1);
}

bool none_eq4(uvec4 b, uvec4 a0, uvec4 a1, uvec4 a2, uvec4 a3) {
    return !colorEq(b, a0) && !colorEq(b, a1) && !colorEq(b, a2) && !colorEq(b, a3);
}

bool none_eq8(uvec4 b, uvec4 a0, uvec4 a1, uvec4 a2, uvec4 a3, uvec4 a4, uvec4 a5, uvec4 a6, uvec4 a7) {
    return !colorEq(b, a0) && !colorEq(b, a1) && !colorEq(b, a2) && !colorEq(b, a3) &&
           !colorEq(b, a4) && !colorEq(b, a5) && !colorEq(b, a6) && !colorEq(b, a7);
}

void main() {
    // Map fragment coordinate to source pixel coordinate
    ivec2 dstCoord = ivec2(gl_FragCoord.xy);
    ivec2 srcCoord = dstCoord / 2;

    // Determine which of the 4 output pixels this fragment represents
    ivec2 subPixel = dstCoord % 2;

    // Sample 3x3 neighborhood around source pixel
    uvec4 A = sampleClamped(srcCoord + ivec2(-1, -1));
    uvec4 B = sampleClamped(srcCoord + ivec2( 0, -1));
    uvec4 C = sampleClamped(srcCoord + ivec2( 1, -1));
    uvec4 D = sampleClamped(srcCoord + ivec2(-1,  0));
    uvec4 E = sampleClamped(srcCoord + ivec2( 0,  0));
    uvec4 F = sampleClamped(srcCoord + ivec2( 1,  0));
    uvec4 G = sampleClamped(srcCoord + ivec2(-1,  1));
    uvec4 H = sampleClamped(srcCoord + ivec2( 0,  1));
    uvec4 I = sampleClamped(srcCoord + ivec2( 1,  1));

    // Default to nearest magnification
    uvec4 J = E, K = E, L = E, M = E;

    // If no adjacent pixels match E, use nearest neighbor (output already set)
    if (!none_eq8(E, A, B, C, D, F, G, H, I)) {
        // At least one adjacent pixel matches E, apply MMPX rules

        // Sample extended diamond pattern
        uvec4 P = sampleClamped(srcCoord + ivec2( 0, -2));
        uvec4 Q = sampleClamped(srcCoord + ivec2(-2,  0));
        uvec4 R = sampleClamped(srcCoord + ivec2( 2,  0));
        uvec4 S = sampleClamped(srcCoord + ivec2( 0,  2));

        // Precompute luminances
        uint Bl = luma(B), Dl = luma(D), El = luma(E), Fl = luma(F), Hl = luma(H);

        // 1:1 slope rules (extended from EPX)
        if ((colorEq(D, B) && !colorEq(D, H) && !colorEq(D, F)) &&
            (El >= Dl || colorEq(E, A)) && any_eq3(E, A, C, G) &&
            (El < Dl || !colorEq(A, D) || !colorEq(E, P) || !colorEq(E, Q)))
            J = D;

        if ((colorEq(B, F) && !colorEq(B, D) && !colorEq(B, H)) &&
            (El >= Bl || colorEq(E, C)) && any_eq3(E, A, C, I) &&
            (El < Bl || !colorEq(C, B) || !colorEq(E, P) || !colorEq(E, R)))
            K = B;

        if ((colorEq(H, D) && !colorEq(H, F) && !colorEq(H, B)) &&
            (El >= Hl || colorEq(E, G)) && any_eq3(E, A, G, I) &&
            (El < Hl || !colorEq(G, H) || !colorEq(E, S) || !colorEq(E, Q)))
            L = H;

        if ((colorEq(F, H) && !colorEq(F, B) && !colorEq(F, D)) &&
            (El >= Fl || colorEq(E, I)) && any_eq3(E, C, G, I) &&
            (El < Fl || !colorEq(I, H) || !colorEq(E, R) || !colorEq(E, S)))
            M = F;

        // Intersection rules
        uvec4 farEast = sampleClamped(srcCoord + ivec2(3, 0));
        uvec4 farWest = sampleClamped(srcCoord + ivec2(-3, 0));
        uvec4 farNorth = sampleClamped(srcCoord + ivec2(0, -3));
        uvec4 farSouth = sampleClamped(srcCoord + ivec2(0, 3));

        if ((!colorEq(E, F) && all_eq4(E, C, I, D, Q) && all_eq2(F, B, H)) &&
            !colorEq(F, farEast)) { K = F; M = F; }

        if ((!colorEq(E, D) && all_eq4(E, A, G, F, R) && all_eq2(D, B, H)) &&
            !colorEq(D, farWest)) { J = D; L = D; }

        if ((!colorEq(E, H) && all_eq4(E, G, I, B, P) && all_eq2(H, D, F)) &&
            !colorEq(H, farSouth)) { L = H; M = H; }

        if ((!colorEq(E, B) && all_eq4(E, A, C, H, S) && all_eq2(B, D, F)) &&
            !colorEq(B, farNorth)) { J = B; K = B; }

        // Triangle tip rules
        if (Bl < El && all_eq4(E, G, H, I, S) && none_eq4(E, A, D, C, F)) { J = B; K = B; }
        if (Hl < El && all_eq4(E, A, B, C, P) && none_eq4(E, D, G, I, F)) { L = H; M = H; }
        if (Fl < El && all_eq4(E, A, D, G, Q) && none_eq4(E, B, C, I, H)) { K = F; M = F; }
        if (Dl < El && all_eq4(E, C, F, I, R) && none_eq4(E, B, A, G, H)) { J = D; L = D; }

        // 2:1 edge rules
        if (!colorEq(H, B)) {
            if (!colorEq(H, A) && !colorEq(H, E) && !colorEq(H, C)) {
                uvec4 ne = sampleClamped(srcCoord + ivec2(2, -1));
                uvec4 nw = sampleClamped(srcCoord + ivec2(-2, -1));
                if (all_eq3(H, G, F, R) && none_eq2(H, D, ne)) L = M;
                if (all_eq3(H, I, D, Q) && none_eq2(H, F, nw)) M = L;
            }

            if (!colorEq(B, I) && !colorEq(B, G) && !colorEq(B, E)) {
                uvec4 se = sampleClamped(srcCoord + ivec2(2, 1));
                uvec4 sw = sampleClamped(srcCoord + ivec2(-2, 1));
                if (all_eq3(B, A, F, R) && none_eq2(B, D, se)) J = K;
                if (all_eq3(B, C, D, Q) && none_eq2(B, F, sw)) K = J;
            }
        }

        if (!colorEq(F, D)) {
            if (!colorEq(D, I) && !colorEq(D, E) && !colorEq(D, C)) {
                uvec4 en = sampleClamped(srcCoord + ivec2(1, -2));
                uvec4 es = sampleClamped(srcCoord + ivec2(1, 2));
                if (all_eq3(D, A, H, S) && none_eq2(D, B, es)) J = L;
                if (all_eq3(D, G, B, P) && none_eq2(D, H, en)) L = J;
            }

            if (!colorEq(F, E) && !colorEq(F, A) && !colorEq(F, G)) {
                uvec4 wn = sampleClamped(srcCoord + ivec2(-1, -2));
                uvec4 ws = sampleClamped(srcCoord + ivec2(-1, 2));
                if (all_eq3(F, C, H, S) && none_eq2(F, B, ws)) K = M;
                if (all_eq3(F, I, B, P) && none_eq2(F, H, wn)) M = K;
            }
        }
    }

    // Select which output pixel to write based on sub-pixel position
    uvec4 outputPixel = E; // Default fallback
    if (subPixel.x == 0 && subPixel.y == 0) outputPixel = J;      // Top-left
    else if (subPixel.x == 1 && subPixel.y == 0) outputPixel = K; // Top-right
    else if (subPixel.x == 0 && subPixel.y == 1) outputPixel = L; // Bottom-left
    else if (subPixel.x == 1 && subPixel.y == 1) outputPixel = M; // Bottom-right

    // Convert back to float and output
    fragColor = vec4(
        uintToFloat(outputPixel.r),
        uintToFloat(outputPixel.g),
        uintToFloat(outputPixel.b),
        uintToFloat(outputPixel.a)
    );
}