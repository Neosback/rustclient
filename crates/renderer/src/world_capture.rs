//! Offscreen verification uses the same atlas, pipeline and draw path as the window.
use crate::{GameplayCameraController, RenderError, SurfaceSize, world::WorldGpu};

/// Capture a world mesh with a fresh GPU projection using the desktop pipelines.
/// # Errors
/// Returns adapter, resource, dimension or readback failures.
pub async fn capture_world(
    mesh: &rustclient_scene::WorldMesh,
    camera: GameplayCameraController,
    size: SurfaceSize,
    drive_readback: impl FnOnce(&wgpu::Device),
) -> Result<Vec<u8>, RenderError> {
    capture_world_at(
        mesh,
        camera,
        size,
        rustclient_model::ClientCycle::ZERO,
        drive_readback,
    )
    .await
}
/// Capture an explicitly frozen semantic animation/texture cycle.
/// # Errors
/// Returns adapter, resource, dimension or readback failures.
pub async fn capture_world_at(
    mesh: &rustclient_scene::WorldMesh,
    camera: GameplayCameraController,
    size: SurfaceSize,
    cycle: rustclient_model::ClientCycle,
    drive_readback: impl FnOnce(&wgpu::Device),
) -> Result<Vec<u8>, RenderError> {
    capture_world_with(
        mesh,
        camera,
        size,
        cycle,
        crate::GpuSettings::default(),
        drive_readback,
    )
    .await
}
/// Capture with explicit GPU preferences (fog, color-blind correction, materials, anisotropy, MSAA).
/// # Errors
/// Returns adapter, resource, dimension or readback failures.
pub async fn capture_world_with(
    mesh: &rustclient_scene::WorldMesh,
    camera: GameplayCameraController,
    size: SurfaceSize,
    cycle: rustclient_model::ClientCycle,
    settings: crate::GpuSettings,
    drive_readback: impl FnOnce(&wgpu::Device),
) -> Result<Vec<u8>, RenderError> {
    let (device, queue, capabilities) = crate::capture::capture_device().await?;
    let width = size.width;
    let height = size.height;
    if width == 0 || height == 0 || width > 4096 || height > 4096 {
        return Err(RenderError::World("invalid capture dimensions"));
    }
    let format = super::world_target::FORMAT;
    let effective = settings.resolve(&capabilities);
    let samples = effective.samples;
    let mut world = WorldGpu::new(&device, &queue, format, size, mesh, samples)?;
    world.set_filtering(&device, effective.anisotropy, effective.mipmaps);
    world.prepare(&queue, camera, 1., (cycle.get() & 127) as u32, settings);
    let stride = (width * 4).div_ceil(256) * 256;
    let buffer = device.create_buffer(&wgpu::BufferDescriptor {
        label: Some("world capture readback"),
        size: u64::from(stride) * u64::from(height),
        usage: wgpu::BufferUsages::COPY_DST | wgpu::BufferUsages::MAP_READ,
        mapped_at_creation: false,
    });
    let mut encoder = device.create_command_encoder(&wgpu::CommandEncoderDescriptor {
        label: Some("world capture encoder"),
    });
    world.render_scene(&mut encoder);
    encoder.copy_texture_to_buffer(
        wgpu::TexelCopyTextureInfo {
            texture: &world.target.texture,
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
        .map_err(|_| RenderError::World("capture readback disconnected"))?
        .map_err(|_| RenderError::World("capture map failed"))?;
    let mapped = buffer
        .slice(..)
        .get_mapped_range()
        .map_err(|_| RenderError::World("capture mapped range failed"))?;
    let mut pixels = Vec::with_capacity((width * height * 4) as usize);
    for row in mapped.chunks_exact(stride as usize) {
        pixels.extend_from_slice(&row[..(width * 4) as usize]);
    }
    drop(mapped);
    buffer.unmap();
    Ok(pixels)
}

/// Create the real portable world resources and report their resident geometry costs.
/// This diagnostic does not read pixels or retain the temporary device/resources.
/// # Errors
/// Returns adapter, texture, or geometry validation failures.
pub async fn capture_world_resources(
    mesh: &rustclient_scene::WorldMesh,
) -> Result<crate::WorldGeometryStats, RenderError> {
    let (device, queue, _) = crate::capture::capture_device().await?;
    let world = WorldGpu::new(
        &device,
        &queue,
        wgpu::TextureFormat::Rgba8UnormSrgb,
        SurfaceSize::new(1, 1),
        mesh,
        1,
    )?;
    Ok(world.geometry_stats())
}
