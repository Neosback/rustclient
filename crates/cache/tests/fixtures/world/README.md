# Spawn scene provenance and fixed fixtures

The files here are synthetic grammar vectors authored by `generate.py`; they contain no game assets. Tests require neither the cache nor reference repositories.

RustOSRS commit `854a3962164ad401d21064a5f207f86869ea69b4` supplies cache semantics, placement offsets, transform order and GPU geometry conventions. RuneLite-melxin 235 commit `1ad572d7dcdbc0fb67a4a00f0c2f959d5ab25abc` supplies the thirteen floor topology rows. The owned RustClient port uses +Y elevation and tile-relative GPU units. It does not claim shader/material parity with the editor: UV textures, baked smooth lighting, HSL blending, priority ordering and face bias are deferred.

Reference file SHA-256 (read-only evidence):

- `references/rustosrs/crates/osrs-cache/src/decode/map.rs`: `1e87e3ff6b8309e2e6736d4cb72ef5693c21527307c702f7c0a92cfc13e41bbe`
- `references/rustosrs/crates/osrs-cache/src/decode/floor.rs`: `424da92f38a08cb74c4783ddb428e54ec30755da9cb148fefd5ff8b9566f9c6e`
- `references/rustosrs/crates/osrs-cache/src/decode/object.rs`: `61034938c4e94b99ef208e830fb51ede0535da77268bfecd8427e413e6f29bb1`
- `references/rustosrs/crates/osrs-cache/src/decode/model.rs`: `e4f134f606fa160876b8a796f6e6fc80cd919ebe09a0f603d00f7b4c92693493`
- `references/rustosrs/crates/osrs-cache/src/decode/texture.rs`: `0568d7328c904563f19010cfa78d9cba5f06da5013f8f29be8a7445f2322249d`
- `references/rustosrs/crates/osrs-core/src/model_construction/instance_transform.rs`: `4a778a7c1db4e5db3bf6eb787008e10513ecbd0a7dc8a0df882776c0272483a6`
- `references/rustosrs/crates/osrs-scene/src/placement.rs`: `1da54670ca2b1dfd866da31be84e8805b5fc9914d39243dc851e0458e9950ce8`
- `references/rustosrs/crates/osrs-scene/src/terrain_load.rs`: `dd81a9c3a4fe61a2819c07a386245e175e9c03de844bf044c0c1f3d739793bd7`
- `references/rustosrs/crates/osrs-render/src/geometry.rs`: `379d962fe26d84c3ea27bf7956f5d7bf4ebed654712a226532372c47b01ed797`
- `references/rustosrs/crates/osrs-render/src/gpu/scene.wgsl`: `a92d221c0e60e44be4d17ad9da7d3da91e00f4bbd04377fa3c0457d59ab34a81`
- `/Users/tylercovalt/Documents/ChatGPT/RSPSi-resources/RuneLite-melxin/runescape-client/src/main/java/SceneTileModel.java`: `dc68f213ba1925b12d08417dfe96143e69330b078a2366a4d7411b03000c3edd`

Synthetic fixture SHA-256:

- `locations.bin` (6 bytes): `11bddc09d303c27e9f03d578454852cc5221b646636184cd31e00bf231cb4e39`
- `model-fd.bin` (44 bytes): `efda38f1fb2f01f4a9bce4459f9d91dd321fd2e73f5f185efc1a5c2f0d2dd2d1`
- `model-fe.bin` (40 bytes): `6f46d24d02c81bf916dee9ba2792865cbf3d3d5c556a36df9fc33bf0c87d23ef`
- `object.bin` (31 bytes): `1d3ccff1a63ad83e64787bc97831fba25a5f0e2f387b6a90d7ae525ffad7cdfd`
- `terrain.bin` (32777 bytes): `610eed2143dddcfc5c32abb3b0c5d43c5222a5ddcd3806b303471ea5c123d9a3`
