//! Static reference texture layers and integer box mips, uploaded once per GPU build.
use rustclient_scene::WorldMesh;
use wgpu::util::DeviceExt;

/// Ids index the array directly (vertices carry `id + 1`), so it needs one layer per id up to the
/// largest the mesh supplies, not the 256 the classic shader allows. A vertex naming a layer past
/// the end samples nothing: out-of-range `textureLoad` reads zero, alpha 0, and the fragment is
/// discarded, which is what an unwritten layer did before. The revision-240 cache numbers its
/// textures densely (214 of the 215 ids up to the largest exist), so packing them by count with an
/// id-to-layer map in the shader would save one layer, not the rest of the 256.
#[must_use]
pub fn layer_count(mesh: &WorldMesh) -> u32 {
    mesh.textures
        .keys()
        .next_back()
        .map_or(1, |id| id.get() + 1)
}

/// Resident bytes of the reference texture array: every layer with its full mip chain.
#[must_use]
pub fn array_bytes(mesh: &WorldMesh) -> u64 {
    let texels: u64 = (0..8).map(|level| 1_u64 << (14 - 2 * level)).sum();
    texels * 4 * u64::from(layer_count(mesh))
}

pub(crate) fn upload(
    device: &wgpu::Device,
    queue: &wgpu::Queue,
    mesh: &WorldMesh,
) -> Result<(wgpu::TextureView, wgpu::Buffer), crate::RenderError> {
    validate(mesh, &device.limits())?;
    let texture = device.create_texture(&wgpu::TextureDescriptor {
        label: Some("reference texture array"),
        size: wgpu::Extent3d {
            width: 128,
            height: 128,
            depth_or_array_layers: layer_count(mesh),
        },
        mip_level_count: 8,
        sample_count: 1,
        dimension: wgpu::TextureDimension::D2,
        format: wgpu::TextureFormat::Rgba8Unorm,
        usage: wgpu::TextureUsages::TEXTURE_BINDING | wgpu::TextureUsages::COPY_DST,
        view_formats: &[],
    });
    let mut animations = [[0_f32; 4]; 256];
    for (id, asset) in mesh.textures.iter() {
        let mut pixels = asset.image.pixels().to_vec();
        let mut size = 128;
        for level in 0..8 {
            queue.write_texture(
                wgpu::TexelCopyTextureInfo {
                    texture: &texture,
                    mip_level: level,
                    origin: wgpu::Origin3d {
                        x: 0,
                        y: 0,
                        z: id.get(),
                    },
                    aspect: wgpu::TextureAspect::All,
                },
                &pixels,
                wgpu::TexelCopyBufferLayout {
                    offset: 0,
                    bytes_per_row: Some(size * 4),
                    rows_per_image: Some(size),
                },
                wgpu::Extent3d {
                    width: size,
                    height: size,
                    depth_or_array_layers: 1,
                },
            );
            if size > 1 {
                pixels = mip(&pixels, size as usize);
                size /= 2;
            }
        }
        let speed = f32::from(asset.animation_speed);
        animations[id.get() as usize] = match asset.animation_direction {
            1 => [0., -speed, 0., 0.],
            2 => [-speed, 0., 0., 0.],
            3 => [0., speed, 0., 0.],
            4 => [speed, 0., 0., 0.],
            _ => [0.; 4],
        };
    }
    let view = texture.create_view(&wgpu::TextureViewDescriptor {
        dimension: Some(wgpu::TextureViewDimension::D2Array),
        ..Default::default()
    });
    let animations = device.create_buffer_init(&wgpu::util::BufferInitDescriptor {
        label: Some("texture scroll vectors"),
        contents: bytemuck::cast_slice(&animations),
        usage: wgpu::BufferUsages::UNIFORM,
    });
    Ok((view, animations))
}
pub(crate) fn validate(mesh: &WorldMesh, limits: &wgpu::Limits) -> Result<(), crate::RenderError> {
    if limits.max_texture_array_layers < layer_count(mesh) || limits.max_texture_dimension_2d < 128
    {
        return Err(crate::RenderError::World(
            "classic texture array exceeds device limits",
        ));
    }
    for (id, asset) in mesh.textures.iter() {
        if id.get() >= 256 || asset.image.width() != 128 || asset.image.height() != 128 {
            return Err(crate::RenderError::World(
                "invalid classic texture layer or image dimensions",
            ));
        }
    }
    for vertex in &mesh.geometry.vertices {
        if vertex.texture_uv[0] > 256 {
            return Err(crate::RenderError::World(
                "mesh texture exceeds classic shader capacity",
            ));
        }
    }
    Ok(())
}

/// Identity of the texture set's content, compared only between uploads in one process to decide
/// whether the GPU array must be rewritten. It mixes eight bytes per step instead of one: the
/// pixels are most of the 14 MB and a bytewise hash took 17 ms on every scene.
pub(crate) fn fingerprint(
    textures: &std::collections::BTreeMap<
        rustclient_model::TextureId,
        rustclient_cache::world::TextureAsset,
    >,
) -> u64 {
    const PRIME: u64 = 0x9E37_79B9_7F4A_7C15;
    fn mix(hash: u64, word: u64) -> u64 {
        let hash = (hash ^ word).wrapping_mul(PRIME);
        hash ^ (hash >> 32)
    }
    let mut hash = 0xCBF2_9CE4_8422_2325_u64;
    for (id, asset) in textures {
        hash = mix(
            hash,
            u64::from(id.get())
                | u64::from(asset.animation_direction) << 32
                | u64::from(asset.animation_speed) << 40
                | u64::from(asset.opaque) << 48,
        );
        let pixels = asset.image.pixels();
        hash = mix(hash, pixels.len() as u64);
        let (words, rest) = pixels.as_chunks::<8>();
        for word in words {
            hash = mix(hash, u64::from_le_bytes(*word));
        }
        let mut tail = [0_u8; 8];
        tail[..rest.len()].copy_from_slice(rest);
        hash = mix(hash, u64::from_le_bytes(tail));
    }
    hash
}

/// wgpu requires all-linear filters for anisotropy. The world shader explicitly samples nearest
/// level zero for magnification and cutout alpha; only minification uses this filtered sampler.
pub(crate) fn sampler(device: &wgpu::Device, anisotropy: u16, mipmaps: bool) -> wgpu::Sampler {
    let filter = if anisotropy > 1 {
        wgpu::FilterMode::Linear
    } else {
        wgpu::FilterMode::Nearest
    };
    device.create_sampler(&wgpu::SamplerDescriptor {
        label: Some("reference wrapping and filtering"),
        address_mode_u: wgpu::AddressMode::ClampToEdge,
        address_mode_v: wgpu::AddressMode::Repeat,
        mag_filter: filter,
        min_filter: filter,
        mipmap_filter: wgpu::MipmapFilterMode::Linear,
        anisotropy_clamp: anisotropy.max(1),
        lod_max_clamp: if mipmaps { 32. } else { 0. },
        ..Default::default()
    })
}
fn mip(pixels: &[u8], width: usize) -> Vec<u8> {
    let next = width / 2;
    let mut out = vec![0; next * next * 4];
    for y in 0..next {
        for x in 0..next {
            for c in 0..4 {
                let i = ((y * 2) * width + x * 2) * 4 + c;
                let sum = u16::from(pixels[i])
                    + u16::from(pixels[i + 4])
                    + u16::from(pixels[i + width * 4])
                    + u16::from(pixels[i + width * 4 + 4]);
                out[(y * next + x) * 4 + c] = u8::try_from(sum / 4).expect("averaged channel");
            }
        }
    }
    out
}
#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn box_mips_average_raw_rgba_without_gamma_conversion() {
        assert_eq!(
            mip(
                &[
                    0, 20, 40, 0, 100, 40, 80, 255, 200, 60, 120, 255, 255, 80, 160, 255
                ],
                2
            ),
            [138, 50, 100, 191]
        );
    }
}

#[cfg(test)]
mod validation_tests {
    use super::*;
    use rustclient_cache::{GroupId, Image, IndexId, Source, world::TextureAsset};
    use rustclient_model::{SceneAssetId, TextureId};
    #[test]
    fn invalid_texture_resources_fail_before_any_gpu_upload() {
        let mut mesh = WorldMesh {
            asset: SceneAssetId::new([1; 32]),
            geometry: rustclient_scene::SceneGeometry::default(),
            dynamic: rustclient_scene::SceneGeometry::default(),
            focus: [0.; 3],
            stats: rustclient_scene::SceneStats::default(),
            textures: std::sync::Arc::default(),
            roofs: std::sync::Arc::default(),
        };
        let limits = wgpu::Limits::default();
        assert!(validate(&mesh, &limits).is_ok());
        assert_eq!(layer_count(&mesh), 1);
        let mut small = limits.clone();
        small.max_texture_dimension_2d = 64;
        assert!(validate(&mesh, &small).is_err());
        let source = Source::group(IndexId(0), GroupId(0));
        let asset = TextureAsset {
            image: Image::new(1, 1, vec![255; 4], source).unwrap(),
            animation_direction: 0,
            animation_speed: 0,
            opaque: true,
        };
        std::sync::Arc::make_mut(&mut mesh.textures).insert(TextureId::new(0), asset.clone());
        assert!(validate(&mesh, &limits).is_err());
        std::sync::Arc::make_mut(&mut mesh.textures).clear();
        let asset = TextureAsset {
            image: Image::new(128, 128, vec![255; 128 * 128 * 4], source).unwrap(),
            ..asset
        };
        std::sync::Arc::make_mut(&mut mesh.textures).insert(TextureId::new(256), asset.clone());
        assert!(validate(&mesh, &limits).is_err());
        std::sync::Arc::make_mut(&mut mesh.textures).clear();
        std::sync::Arc::make_mut(&mut mesh.textures).insert(TextureId::new(255), asset);
        assert!(validate(&mesh, &limits).is_ok());
        assert_eq!(layer_count(&mesh), 256);
        let mut small = limits.clone();
        small.max_texture_array_layers = 128;
        assert!(validate(&mesh, &small).is_err());
        // The layer is the id: a lone texture 40 needs 41 layers, not one.
        std::sync::Arc::make_mut(&mut mesh.textures).remove(&TextureId::new(255));
        let asset = TextureAsset {
            image: Image::new(128, 128, vec![255; 128 * 128 * 4], source).unwrap(),
            animation_direction: 0,
            animation_speed: 0,
            opaque: true,
        };
        std::sync::Arc::make_mut(&mut mesh.textures).insert(TextureId::new(40), asset);
        assert_eq!(layer_count(&mesh), 41);
        assert!(validate(&mesh, &small).is_ok());
        // 128 x 128 texels per layer, eight mips each a quarter the size of the last.
        assert_eq!(
            array_bytes(&mesh),
            41 * 4 * (16384 + 4096 + 1024 + 256 + 64 + 16 + 4 + 1)
        );
    }
}

#[cfg(test)]
mod fingerprint_tests {
    use super::*;
    use rustclient_cache::{GroupId, Image, IndexId, Source, world::TextureAsset};
    use rustclient_model::TextureId;
    use std::collections::BTreeMap;

    fn set(pixel: u8, speed: u8, id: u32) -> BTreeMap<TextureId, TextureAsset> {
        let source = Source::group(IndexId(0), GroupId(0));
        BTreeMap::from([(
            TextureId::new(id),
            TextureAsset {
                image: Image::new(3, 3, vec![pixel; 36], source).unwrap(),
                animation_direction: 1,
                animation_speed: speed,
                opaque: true,
            },
        )])
    }

    #[test]
    fn fingerprint_follows_pixels_animation_and_ids() {
        let base = fingerprint(&set(7, 2, 5));
        assert_eq!(base, fingerprint(&set(7, 2, 5)));
        assert_ne!(base, fingerprint(&set(8, 2, 5)));
        assert_ne!(base, fingerprint(&set(7, 3, 5)));
        assert_ne!(base, fingerprint(&set(7, 2, 6)));
        assert_ne!(base, fingerprint(&BTreeMap::new()));
        // A change in the last byte, past the final whole word, still shows.
        let mut last = set(7, 2, 5);
        let source = Source::group(IndexId(0), GroupId(0));
        let mut pixels = vec![7; 36];
        pixels[35] = 9;
        last.get_mut(&TextureId::new(5)).unwrap().image = Image::new(3, 3, pixels, source).unwrap();
        assert_ne!(base, fingerprint(&last));
    }
}
