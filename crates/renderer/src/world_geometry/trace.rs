//! Manual GPU-resource/CPU-ordering trace, independent of cache files and login.
use super::*;
use rustclient_scene::{DrawTile, SceneModel};
use std::time::Instant;

#[test]
#[ignore = "manual optimized GPU ordering trace"]
fn retained_ordering_trace() {
    let (device, queue, _) = pollster::block_on(crate::capture::capture_device()).unwrap();
    let mut geometry = SceneGeometry::default();
    for model in 0..2048_u16 {
        let first = geometry.vertices.len();
        let x = i16::try_from(model % 32 * 128).unwrap();
        let z = i16::try_from(model / 32 * 128).unwrap();
        for face in 0..12_i16 {
            for (dx, dz) in [(0, 0), (64, 0), (0, 64)] {
                geometry
                    .transparent
                    .push(u32::try_from(geometry.vertices.len()).unwrap());
                geometry.vertices.push(SceneVertex {
                    position: [x + dx, face, z + dz, face],
                    abhsl: 128 << 24,
                    texture_uv: [0; 4],
                    draw_tile: DrawTile::scenery(
                        [i32::from(model % 32), i32::from(model / 32)],
                        true,
                    ),
                });
            }
        }
        geometry.models.push(SceneModel {
            vertices: first..geometry.vertices.len(),
            depth_policy: rustclient_model::ModelDepthPolicy::DepthWrite,
            sort_source: None,
        });
    }
    let mut gpu = GeometryGpu::new(&device, &geometry).unwrap();
    let view = ModelView::new([4096, 2048, 4096], 0, 128);
    let mut expected = None;
    for force in [true, false] {
        let mut samples = Vec::new();
        let before = gpu.stats();
        for index in 0..600 {
            if force {
                gpu.last_sort = None;
            }
            let started = Instant::now();
            gpu.sort(&queue, view);
            samples.push(started.elapsed().as_secs_f64() * 1000.);
            queue.submit([]);
            if index % 64 == 0 {
                device.poll(wgpu::PollType::wait_indefinitely()).unwrap();
            }
        }
        samples.sort_by(f64::total_cmp);
        println!(
            "force_reorder={force}: p50={:.4}ms p95={:.4}ms sort_runs={} reused={}",
            samples[300],
            samples[570],
            gpu.stats().ordering_runs - before.ordering_runs,
            gpu.stats().ordering_reuses - before.ordering_reuses
        );
        if let Some(expected) = &expected {
            assert_eq!(&gpu.order, expected);
        } else {
            expected = Some(gpu.order.clone());
        }
    }
    // Geometry updates with the same eye invalidate cached ordering.
    gpu.update(&device, &queue, &geometry).unwrap();
    assert!(gpu.sort(&queue, view));
    device.poll(wgpu::PollType::wait_indefinitely()).unwrap();
}
