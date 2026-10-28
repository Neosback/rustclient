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
    capture_ui_with_settings(
        assets,
        images,
        frame,
        flames,
        viewport,
        crate::GpuSettings::default(),
        drive_readback,
    )
    .await
}

/// Capture the game canvas with actual GPU scaling/color settings and the shared display path.
/// # Errors
/// Rejects unavailable adapters, invalid uploads/dimensions and readback failures.
pub async fn capture_ui_with_settings(
    assets: &TitleAssets,
    images: &std::collections::BTreeMap<rustclient_game_ui::UiImageId, rustclient_cache::Image>,
    frame: &UiFrame,
    flames: &[u8],
    viewport: TitleViewport,
    settings: crate::GpuSettings,
    drive_readback: impl FnOnce(&wgpu::Device),
) -> Result<Vec<u8>, RenderError> {
    let (device, queue, _) = capture_device().await?;
    let width = viewport.physical_width;
    let height = viewport.physical_height;
    if width == 0 || height == 0 || width > 4096 || height > 4096 {
        return Err(RenderError::Ui("invalid capture dimensions"));
    }
    let format = wgpu::TextureFormat::Rgba8UnormSrgb;
    let mut ui = UiGpu::with_images(&device, &queue, crate::ui_canvas::FORMAT, assets, images)?;
    let mut canvas = crate::ui_canvas::UiCanvas::new(&device, format);
    let source_viewport = canvas.prepare(&device, &queue, frame, viewport, settings, None)?;
    ui.prepare(
        &device,
        &queue,
        frame,
        flames,
        crate::ui::DynamicImages::default(),
        source_viewport,
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
    canvas.render(&mut encoder, &ui);
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
        canvas.draw(&mut pass);
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

pub(crate) async fn capture_device()
-> Result<(wgpu::Device, wgpu::Queue, crate::GpuCapabilities), RenderError> {
    let instance = wgpu::Instance::default();
    let adapter = instance
        .request_adapter(&wgpu::RequestAdapterOptions::default())
        .await
        .map_err(|_| RenderError::Ui("capture adapter unavailable"))?;
    let (device, queue) = adapter
        .request_device(&crate::settings::device_descriptor(&adapter))
        .await
        .map_err(|_| RenderError::Ui("capture device unavailable"))?;

    let capabilities = crate::settings::capabilities(&adapter, &device, Vec::new());
    Ok((device, queue, capabilities))
}

/// Sample an immutable straight-alpha image through the same game-canvas scaling shader.
/// Used for numeric native/browser shader comparisons without a cache or login session.
/// # Errors
/// Returns adapter, dimension, pipeline or readback errors.
pub async fn capture_canvas(
    image: &rustclient_cache::Image,
    size: crate::SurfaceSize,
    settings: crate::GpuSettings,
    drive_readback: impl FnOnce(&wgpu::Device),
) -> Result<Vec<u8>, RenderError> {
    capture_canvas_with_overlay(
        image,
        size,
        settings,
        rustclient_game_ui::ViewportOverlay::default(),
        drive_readback,
    )
    .await
}

/// Canvas diagnostic with a projected full-viewport widget overlay beneath the UI texture.
/// # Errors
/// Returns the same device, resource and readback errors as [`capture_canvas`].
#[expect(
    clippy::too_many_lines,
    clippy::cast_precision_loss,
    reason = "bounded 4096px GPU fixture target and readback lifecycle"
)]
pub async fn capture_canvas_with_overlay(
    image: &rustclient_cache::Image,
    size: crate::SurfaceSize,
    settings: crate::GpuSettings,
    overlay: rustclient_game_ui::ViewportOverlay,
    drive_readback: impl FnOnce(&wgpu::Device),
) -> Result<Vec<u8>, RenderError> {
    let (device, queue, _) = capture_device().await?;
    if size.is_zero() || size.width > 4096 || size.height > 4096 {
        return Err(RenderError::Ui("invalid canvas capture dimensions"));
    }
    let frame = UiFrame {
        viewport_overlay: overlay,
        canvas: Some(rustclient_model::CanvasSize {
            width: image.width(),
            height: image.height(),
        }),
        ..Default::default()
    };
    let format = wgpu::TextureFormat::Rgba8UnormSrgb;
    let mut canvas = crate::ui_canvas::UiCanvas::new(&device, format);
    let viewport = TitleViewport {
        canvas_width: image.width(),
        physical_width: size.width,
        physical_height: size.height,
        scale: 1.,
        stretch: Some(rustclient_game_ui::StretchedCanvas {
            canvas_height: image.height(),
            x: 0.,
            width: size.width as f32,
            height: size.height as f32,
        }),
    };
    canvas.prepare(&device, &queue, &frame, viewport, settings, None)?;
    canvas.upload_fixture(&queue, image);
    let texture = target(&device, size.width, size.height, format);
    let view = texture.create_view(&wgpu::TextureViewDescriptor::default());
    let stride = (size.width * 4).div_ceil(256) * 256;
    let buffer = device.create_buffer(&wgpu::BufferDescriptor {
        label: Some("canvas shader fixture readback"),
        size: u64::from(stride) * u64::from(size.height),
        usage: wgpu::BufferUsages::COPY_DST | wgpu::BufferUsages::MAP_READ,
        mapped_at_creation: false,
    });
    let mut encoder = device.create_command_encoder(&wgpu::CommandEncoderDescriptor {
        label: Some("canvas fixture encoder"),
    });
    {
        let mut pass = encoder.begin_render_pass(&wgpu::RenderPassDescriptor {
            label: Some("canvas shader fixture"),
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
        canvas.draw(&mut pass);
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
                rows_per_image: Some(size.height),
            },
        },
        wgpu::Extent3d {
            width: size.width,
            height: size.height,
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
        .map_err(|_| RenderError::Ui("canvas readback disconnected"))?
        .map_err(|_| RenderError::Ui("canvas readback map failed"))?;
    let mapped = buffer
        .slice(..)
        .get_mapped_range()
        .map_err(|_| RenderError::Ui("canvas mapped range failed"))?;
    let mut pixels = Vec::with_capacity((size.width * size.height * 4) as usize);
    for row in mapped.chunks_exact(stride as usize) {
        pixels.extend_from_slice(&row[..(size.width * 4) as usize]);
    }
    drop(mapped);
    buffer.unmap();
    Ok(pixels)
}

/// Actual offscreen adapter/device budget, without exposing device handles.
/// # Errors
/// Returns a diagnostic if no usable adapter/device is available.
pub async fn capture_capabilities() -> Result<crate::GpuCapabilities, RenderError> {
    let (_, _, capabilities) = capture_device().await?;
    Ok(capabilities)
}
