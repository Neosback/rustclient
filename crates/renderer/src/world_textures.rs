//! Static reference texture layers and integer box mips, uploaded once per GPU build.
use rustclient_scene::WorldMesh;
use wgpu::util::DeviceExt;
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
            depth_or_array_layers: 256,
        },
        mip_level_count: 8,
        sample_count: 1,
        dimension: wgpu::TextureDimension::D2,
        format: wgpu::TextureFormat::Rgba8Unorm,
        usage: wgpu::TextureUsages::TEXTURE_BINDING | wgpu::TextureUsages::COPY_DST,
        view_formats: &[],
    });
    let mut animations = [[0_f32; 4]; 256];
    for (id, asset) in &mesh.textures {
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
    if limits.max_texture_array_layers < 256 || limits.max_texture_dimension_2d < 128 {
        return Err(crate::RenderError::World(
            "classic texture array exceeds device limits",
        ));
    }
    for (id, asset) in &mesh.textures {
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

pub(crate) fn fingerprint(
    textures: &std::collections::BTreeMap<
        rustclient_model::TextureId,
        rustclient_cache::world::TextureAsset,
    >,
) -> u64 {
    let mut hash = 0xCBF2_9CE4_8422_2325_u64;
    for (id, asset) in textures {
        for byte in id
            .get()
            .to_le_bytes()
            .into_iter()
            .chain([
                asset.animation_direction,
                asset.animation_speed,
                u8::from(asset.opaque),
            ])
            .chain(asset.image.pixels().iter().copied())
        {
            hash ^= u64::from(byte);
            hash = hash.wrapping_mul(0x0000_0100_0000_01B3);
        }
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
            textures: std::collections::BTreeMap::default(),
            roofs: std::sync::Arc::default(),
        };
        let limits = wgpu::Limits::default();
        assert!(validate(&mesh, &limits).is_ok());
        let mut small = limits.clone();
        small.max_texture_array_layers = 128;
        assert!(validate(&mesh, &small).is_err());
        small = limits.clone();
        small.max_texture_dimension_2d = 64;
        assert!(validate(&mesh, &small).is_err());
        let source = Source::group(IndexId(0), GroupId(0));
        let asset = TextureAsset {
            image: Image::new(1, 1, vec![255; 4], source).unwrap(),
            animation_direction: 0,
            animation_speed: 0,
            opaque: true,
        };
        mesh.textures.insert(TextureId::new(0), asset.clone());
        assert!(validate(&mesh, &limits).is_err());
        mesh.textures.clear();
        let asset = TextureAsset {
            image: Image::new(128, 128, vec![255; 128 * 128 * 4], source).unwrap(),
            ..asset
        };
        mesh.textures.insert(TextureId::new(256), asset.clone());
        assert!(validate(&mesh, &limits).is_err());
        mesh.textures.clear();
        mesh.textures.insert(TextureId::new(255), asset);
        assert!(validate(&mesh, &limits).is_ok());
    }
}
