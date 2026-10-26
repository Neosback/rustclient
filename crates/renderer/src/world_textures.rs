//! Static reference texture layers and integer box mips, uploaded once per GPU build.
use rustclient_scene::WorldMesh;
use wgpu::util::DeviceExt;
pub(crate) fn upload(
    device: &wgpu::Device,
    queue: &wgpu::Queue,
    mesh: &WorldMesh,
) -> (wgpu::TextureView, wgpu::Buffer) {
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
    (view, animations)
}
/// Magnification stays nearest like `RuneLite`'s texture config; wgpu only permits anisotropy
/// above 1 with every filter linear, so a filtered level also smooths magnification.
pub(crate) fn sampler(device: &wgpu::Device, anisotropy: u16) -> wgpu::Sampler {
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
