//! Explicit device-lifecycle diagnostic; no source-parity pixels or account required.
use super::*;
use std::time::Instant;

#[test]
#[ignore = "manual GPU initialization/upload resource trace"]
fn precompiled_shell_uploads_once_and_hidden_frames_do_no_work() {
    let (device, queue, _) = pollster::block_on(crate::capture::capture_device()).unwrap();
    let started = Instant::now();
    let mut gpu = ShellGpu::new(&device, wgpu::TextureFormat::Rgba8UnormSrgb);
    eprintln!("shell pipeline initialization {:?}", started.elapsed());
    let mut staging = ShellStaging::default();
    let pixels = [80_u8; 16];
    staging
        .set(Some(ShellLayer {
            pixels: &pixels,
            size: SurfaceSize::new(2, 2),
            generation: 1,
            origin: [0, 0],
        }))
        .unwrap();
    let started = Instant::now();
    staging.prepare(&mut gpu, &device, &queue);
    eprintln!("first prepared shell upload {:?}", started.elapsed());
    for _ in 0..600 {
        staging.prepare(&mut gpu, &device, &queue);
    }
    assert_eq!(staging.stats.uploads, 1);
    assert_eq!(staging.stats.uploaded_bytes, 16);
    staging.set(None).unwrap();
    for _ in 0..600 {
        staging.prepare(&mut gpu, &device, &queue);
    }
    assert_eq!(staging.stats.uploads, 1);
    // Restoring the same image reuses the retained texture, without reuploading.
    staging
        .set(Some(ShellLayer {
            pixels: &pixels,
            size: SurfaceSize::new(2, 2),
            generation: 1,
            origin: [0, 0],
        }))
        .unwrap();
    staging.prepare(&mut gpu, &device, &queue);
    assert_eq!(staging.stats.uploads, 1);
    queue.submit([]);
    device.poll(wgpu::PollType::wait_indefinitely()).unwrap();
}
