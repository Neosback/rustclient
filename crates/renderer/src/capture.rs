//! Offscreen verification uses the same atlas, pipeline and draw path as the window.
use crate::{RenderError, ui::UiGpu};
use rustclient_cache::TitleAssets;
use rustclient_game_ui::{TitleViewport, UiFrame};

/// Render an actual GPU title frame into row-major RGBA8 pixels.
///
/// This diagnostic API creates a fresh GPU projection each time, allowing tests
/// to verify rebuild equivalence without a window or authoritative-state mutation.
/// Native callers drive readback with `Device::poll`; browser callers let the
/// event loop deliver the callback. No native polling is assumed here.
/// It never writes files; the composition root chooses how to store captures.
///
/// # Errors
/// Returns errors for missing adapters/devices, invalid UI data or failed readback.
pub async fn capture_ui(
    assets: &TitleAssets,
    frame: &UiFrame,
    flames: &[u8],
    viewport: TitleViewport,
    drive_readback: impl FnOnce(&wgpu::Device),
) -> Result<Vec<u8>, RenderError> {
    capture_ui_with_images(
        assets,
        &std::collections::BTreeMap::new(),
        frame,
        flames,
        viewport,
        drive_readback,
    )
    .await
}
/// Render widget assets through the same GPU quad pipeline used by the desktop.
/// # Errors
/// Rejects oversized image sets, invalid projections or failed GPU readback.
pub async fn capture_ui_with_images(
    assets: &TitleAssets,
    images: &std::collections::BTreeMap<rustclient_game_ui::UiImageId, rustclient_cache::Image>,
    frame: &UiFrame,
    flames: &[u8],
    viewport: TitleViewport,
    drive_readback: impl FnOnce(&wgpu::Device),
) -> Result<Vec<u8>, RenderError> {
    let (device, queue) = capture_device().await?;
    let width = viewport.physical_width;
    let height = viewport.physical_height;
    if width == 0 || height == 0 || width > 4096 || height > 4096 {
        return Err(RenderError::Ui("invalid capture dimensions"));
    }
    let format = wgpu::TextureFormat::Rgba8UnormSrgb;
    let mut ui = UiGpu::with_images(&device, &queue, format, assets, images)?;
    ui.prepare(
        &device,
        &queue,
        frame,
        flames,
        crate::ui::DynamicImages::default(),
        viewport,
    )?;
    let texture = target(&device, width, height, format);
    let view = texture.create_view(&wgpu::TextureViewDescriptor::default());
    let stride = (width * 4).div_ceil(256) * 256;
    let buffer = device.create_buffer(&wgpu::BufferDescriptor {
        label: Some("title capture readback"),
        size: u64::from(stride) * u64::from(height),
        usage: wgpu::BufferUsages::COPY_DST | wgpu::BufferUsages::MAP_READ,
        mapped_at_creation: false,
    });
    let mut encoder = device.create_command_encoder(&wgpu::CommandEncoderDescriptor {
        label: Some("title capture encoder"),
    });
    {
        let mut pass = encoder.begin_render_pass(&wgpu::RenderPassDescriptor {
            label: Some("title capture pass"),
            color_attachments: &[Some(wgpu::RenderPassColorAttachment {
                view: &view,
                depth_slice: None,
                resolve_target: None,
                ops: wgpu::Operations {
                    load: wgpu::LoadOp::Clear(wgpu::Color::BLACK),
                    store: wgpu::StoreOp::Store,
                },
            })],
            depth_stencil_attachment: None,
            timestamp_writes: None,
            occlusion_query_set: None,
            multiview_mask: None,
        });
        ui.draw(&mut pass);
    }
    encoder.copy_texture_to_buffer(
        wgpu::TexelCopyTextureInfo {
            texture: &texture,
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
    drive_readback(&device);
    receiver
        .await
        .map_err(|_| RenderError::Ui("capture readback disconnected"))?
        .map_err(|_| RenderError::Ui("capture map failed"))?;
    let mapped = buffer
        .slice(..)
        .get_mapped_range()
        .map_err(|_| RenderError::Ui("capture mapped range failed"))?;
    let mut pixels = Vec::with_capacity((width * height * 4) as usize);
    for row in mapped.chunks_exact(stride as usize) {
        pixels.extend_from_slice(&row[..(width * 4) as usize]);
    }
    drop(mapped);
    buffer.unmap();
    Ok(pixels)
}

fn target(
    device: &wgpu::Device,
    width: u32,
    height: u32,
    format: wgpu::TextureFormat,
) -> wgpu::Texture {
    device.create_texture(&wgpu::TextureDescriptor {
        label: Some("title capture target"),
        size: wgpu::Extent3d {
            width,
            height,
            depth_or_array_layers: 1,
        },
        mip_level_count: 1,
        sample_count: 1,
        dimension: wgpu::TextureDimension::D2,
        format,
        usage: wgpu::TextureUsages::RENDER_ATTACHMENT | wgpu::TextureUsages::COPY_SRC,
        view_formats: &[],
    })
}

pub(crate) async fn capture_device() -> Result<(wgpu::Device, wgpu::Queue), RenderError> {
    let instance = wgpu::Instance::default();
    let adapter = instance
        .request_adapter(&wgpu::RequestAdapterOptions::default())
        .await
        .map_err(|_| RenderError::Ui("capture adapter unavailable"))?;
    let (device, queue) = adapter
        .request_device(&crate::settings::device_descriptor(&adapter))
        .await
        .map_err(|_| RenderError::Ui("capture device unavailable"))?;

    Ok((device, queue))
}
