//! Explicit cache/GPU resource diagnostic, outside CI appearance acceptance.
use super::{PreparedLocationUpdate, SceneryGpu};
use crate::RenderError;
use rustclient_scene::SceneGeometry;
use std::{sync::Arc, time::Instant};
#[derive(Debug)]
pub struct ZoneResidencySample {
    pub updated_zones: usize,
    pub unchanged_resource_revisions: usize,
    pub iterations: u32,
    pub max_enqueued_bytes: u64,
    pub max_enqueue_ms: f64,
    pub resident_gpu_bytes: u64,
    pub unchanged_update_uploaded: bool,
}
/// Exercise the same resource owner, staging budget and revision checks as the native renderer.
/// Explicit diagnostic waits are outside measured enqueue work; there is no surface/scanout claim.
/// # Errors
/// Reports invalid geometry, violated resource contracts or unavailable GPU resources.
pub async fn verify_location_residency(
    base: &SceneGeometry,
    frames: &[SceneGeometry],
) -> Result<Vec<ZoneResidencySample>, RenderError> {
    let (device, queue, _) = crate::capture::capture_device().await?;
    let mut gpu = SceneryGpu::new_static(&device, base)?;
    queue.submit([]);
    device
        .poll(wgpu::PollType::wait_indefinitely())
        .map_err(|_| RenderError::World("diagnostic GPU drain failed"))?;
    let mut samples = Vec::new();
    let mut previous = None;
    for (sample, geometry) in frames.iter().enumerate() {
        let old = gpu.revisions();
        let old_buffers = gpu.buffers();
        let prepared = PreparedLocationUpdate::prepare(geometry, previous.as_ref())?;
        let deferred = sample % 2 == 0;
        if deferred {
            gpu.queue_deferred_zones(prepared.clone())?;
        } else {
            gpu.queue_zones(prepared.clone())?;
        }
        let mut iterations = 0;
        let mut maximum_bytes = 0;
        let mut maximum_ms = 0_f64;
        loop {
            iterations += 1;
            let start = Instant::now();
            let mut ready = gpu.advance_zones(&device, &queue)?;
            maximum_ms = maximum_ms.max(start.elapsed().as_secs_f64() * 1000.);
            maximum_bytes = maximum_bytes.max(gpu.last_enqueued_bytes);
            if gpu.last_enqueued_bytes > 2 * 1024 * 1024 {
                return Err(RenderError::World("zone upload exceeded byte budget"));
            }
            if !ready && gpu.publication_is(&geometry.location_zones) {
                return Err(RenderError::World("partial zone group became visible"));
            }
            drain(&device, &queue)?;
            if deferred && gpu.ready_for(&geometry.location_zones) {
                if gpu.buffers() != old_buffers {
                    return Err(RenderError::World(
                        "unacknowledged resources became visible",
                    ));
                }
                let wrong: Arc<[_]> = geometry.location_zones.to_vec().into();
                if gpu.commit_ready(&queue, &wrong) {
                    return Err(RenderError::World(
                        "stale publication acknowledgement accepted",
                    ));
                }
                ready = gpu.commit_ready(&queue, &geometry.location_zones);
            }
            if ready {
                break;
            }
            if iterations > 1000 {
                return Err(RenderError::World("zone publication made no progress"));
            }
        }
        let new = gpu.revisions();
        let retained = old
            .iter()
            .filter(|(id, revision)| new.get(id).is_some_and(|next| Arc::ptr_eq(revision, next)))
            .count();
        let buffers = gpu.buffers();
        let retained_buffers = old_buffers
            .iter()
            .filter(|(id, old)| buffers.get(id).is_some_and(|new| new == *old))
            .count();
        if retained_buffers != retained {
            return Err(RenderError::World("unchanged GPU buffers were replaced"));
        }
        let changed = new.len() - retained;
        let expected = geometry
            .location_zones
            .iter()
            .filter(|z| {
                old.get(&z.zone)
                    .is_none_or(|r| !Arc::ptr_eq(r, &z.revision))
            })
            .count();
        if changed != expected {
            return Err(RenderError::World("unrelated zone allocation was replaced"));
        }
        gpu.queue_zones(prepared.clone())?;
        gpu.advance_zones(&device, &queue)?;
        let unchanged_uploaded = gpu.last_enqueued_bytes != 0;
        if unchanged_uploaded {
            return Err(RenderError::World("unchanged zone group uploaded again"));
        }
        samples.push(ZoneResidencySample {
            updated_zones: changed,
            unchanged_resource_revisions: retained,
            iterations,
            max_enqueued_bytes: maximum_bytes,
            max_enqueue_ms: maximum_ms,
            resident_gpu_bytes: gpu.stats().gpu_buffer_bytes,
            unchanged_update_uploaded: unchanged_uploaded,
        });
        previous = Some(prepared);
    }
    Ok(samples)
}

fn drain(device: &wgpu::Device, queue: &wgpu::Queue) -> Result<(), RenderError> {
    queue.submit([]);
    device
        .poll(wgpu::PollType::wait_indefinitely())
        .map_err(|_| RenderError::World("diagnostic zone drain failed"))?;
    Ok(())
}
