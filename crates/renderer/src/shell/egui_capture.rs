//! Explicit offscreen diagnostic of the same cached panel path used by the native window.
use super::{
    ShellGpu, ShellStaging,
    egui::{EguiShellLayer, EguiShellStats, EguiStaging},
};
use crate::RenderError;
/// # Errors
/// Rejects invalid UI plans, unavailable GPUs and readback failures.
pub async fn capture_gpu_shell(
    layer: EguiShellLayer<'_>,
    drive: impl FnOnce(&wgpu::Device),
) -> Result<(Vec<u8>, EguiShellStats), RenderError> {
    let (device, queue, _) = crate::capture::capture_device().await?;
    let mut staging = EguiStaging::default();
    let mut placement = ShellStaging::default();
    staging.set(layer, &mut placement)?;
    let mut gpu = ShellGpu::new(&device, wgpu::TextureFormat::Rgba8UnormSrgb);
    let [width, height] = [layer.size.width, layer.size.height];
    let stride = (width * 4).div_ceil(256) * 256;
    let buffer = device.create_buffer(&wgpu::BufferDescriptor {
        label: Some("GPU shell capture"),
        size: u64::from(stride) * u64::from(height),
        usage: wgpu::BufferUsages::COPY_DST | wgpu::BufferUsages::MAP_READ,
        mapped_at_creation: false,
    });
    let mut encoder = device.create_command_encoder(&Default::default());
    staging.prepare(&device, &queue, &mut encoder, &mut gpu);
    // A continuous world frame or unchanged input cannot cause another paint/upload.
    staging.set(layer, &mut placement)?;
    staging.prepare(&device, &queue, &mut encoder, &mut gpu);
    encoder.copy_texture_to_buffer(
        wgpu::TexelCopyTextureInfo {
            texture: &gpu.texture,
            mip_level: 0,
            origin: wgpu::Origin3d::ZERO,
            aspect: wgpu::TextureAspect::All,
        },
        wgpu::TexelCopyBufferInfo {
            buffer: &buffer,
            layout: wgpu::TexelCopyBufferLayout {
                offset: 0,
                bytes_per_row: Some(stride),
                rows_per_image: Some(height),
            },
        },
        wgpu::Extent3d {
            width,
            height,
            depth_or_array_layers: 1,
        },
    );
    queue.submit([encoder.finish()]);
    let (sender, receiver) = futures_channel::oneshot::channel();
    buffer
        .slice(..)
        .map_async(wgpu::MapMode::Read, move |result| {
            let _ = sender.send(result);
        });
    drive(&device);
    receiver
        .await
        .map_err(|_| RenderError::Ui("GPU shell capture disconnected"))?
        .map_err(|_| RenderError::Ui("GPU shell capture map failed"))?;
    let mapped = buffer
        .slice(..)
        .get_mapped_range()
        .map_err(|_| RenderError::Ui("GPU shell capture range failed"))?;
    let mut pixels = Vec::with_capacity((width * height * 4) as usize);
    for row in mapped.chunks_exact(stride as usize) {
        pixels.extend_from_slice(&row[..(width * 4) as usize]);
    }
    drop(mapped);
    buffer.unmap();
    Ok((pixels, staging.stats))
}
