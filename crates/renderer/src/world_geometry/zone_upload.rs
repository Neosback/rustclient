//! CPU-prepared zone groups and bounded, nonblocking GPU staging.
use super::{GeometryGpu, PreparedGeometry};
use crate::RenderError;
use rustclient_scene::{LocationZone, SceneGeometry, SceneZone};
use std::sync::Arc;

/// Immutable worker-prepared publication, reusable until the renderer acknowledges it.
#[derive(Clone)]
pub struct PreparedLocationUpdate(pub(crate) Arc<PreparedZones>);
pub(crate) struct PreparedZones {
    pub publication: Arc<[LocationZone]>,
    pub entries: Vec<(LocationZone, Arc<PreparedGeometry>)>,
}
impl PreparedLocationUpdate {
    /// Reuse unchanged resources, preparing only revisions absent from the previous group.
    /// # Errors
    /// Rejects invalid geometry before the owning thread queues GPU work.
    pub fn prepare(geometry: &SceneGeometry, previous: Option<&Self>) -> Result<Self, RenderError> {
        if let Some(old) = previous
            && rustclient_scene::same_location_zones(&old.0.publication, &geometry.location_zones)
        {
            return Ok(old.clone());
        }
        let mut seen = std::collections::BTreeSet::new();
        let mut entries = Vec::with_capacity(geometry.location_zones.len());
        for zone in geometry.location_zones.iter() {
            if !seen.insert(zone.zone) {
                return Err(RenderError::World("duplicate location zone in publication"));
            }
            let retained = previous
                .and_then(|old| {
                    old.0.entries.iter().find(|(z, _)| {
                        z.zone == zone.zone && Arc::ptr_eq(&z.revision, &zone.revision)
                    })
                })
                .map(|(_, p)| Arc::clone(p));
            let prepared = match retained {
                Some(p) => p,
                None => Arc::new(PreparedGeometry::new(&zone.geometry, true)?),
            };
            entries.push((zone.clone(), prepared));
        }
        Ok(Self(Arc::new(PreparedZones {
            publication: Arc::clone(&geometry.location_zones),
            entries,
        })))
    }
}
pub(super) struct ZoneUpload {
    pub zone: SceneZone,
    pub revision: Arc<()>,
    geometry: Arc<SceneGeometry>,
    prepared: Arc<PreparedGeometry>,
    buffers: [wgpu::Buffer; 3],
    capacities: [u64; 3],
    part: usize,
    offset: usize,
}
impl ZoneUpload {
    pub fn new(
        device: &wgpu::Device,
        zone: &LocationZone,
        prepared: Arc<PreparedGeometry>,
    ) -> Result<Self, RenderError> {
        prepared.validate_for(&zone.geometry, device.limits().max_buffer_size)?;
        let bytes = prepared.buffers(&zone.geometry);
        let capacities = bytes.map(|b| b.len().max(4) as u64);
        let buffers = std::array::from_fn(|i| {
            device.create_buffer(&wgpu::BufferDescriptor {
                label: Some("pending location zone"),
                size: capacities[i],
                usage: wgpu::BufferUsages::COPY_DST
                    | if i == 0 {
                        wgpu::BufferUsages::VERTEX
                    } else {
                        wgpu::BufferUsages::INDEX
                    },
                mapped_at_creation: false,
            })
        });
        Ok(Self {
            zone: zone.zone,
            revision: Arc::clone(&zone.revision),
            geometry: Arc::clone(&zone.geometry),
            prepared,
            buffers,
            capacities,
            part: 0,
            offset: 0,
        })
    }
    pub fn advance(
        &mut self,
        queue: &wgpu::Queue,
        budget: &mut usize,
        started: web_time::Instant,
    ) -> bool {
        let sources = self.prepared.buffers(&self.geometry);
        while self.part < 3 {
            let source = sources[self.part];
            if self.offset == source.len() {
                self.part += 1;
                self.offset = 0;
                continue;
            }
            if *budget < 4 || started.elapsed() >= std::time::Duration::from_millis(1) {
                return false;
            }
            // All three source streams are multiples of COPY_BUFFER_ALIGNMENT.
            let count = (source.len() - self.offset).min(*budget).min(256 * 1024) & !3;
            if count == 0 {
                return false;
            }
            queue.write_buffer(
                &self.buffers[self.part],
                self.offset as u64,
                &source[self.offset..self.offset + count],
            );
            self.offset += count;
            *budget -= count;
        }
        true
    }
    pub fn resident_bytes(&self) -> u64 {
        self.capacities.iter().sum()
    }
    pub fn finish(self) -> GeometryGpu {
        let [vertices, opaque, transparent] = self.buffers;
        GeometryGpu {
            vertices,
            opaque,
            transparent,
            capacities: self.capacities,
            data: self.prepared.data.clone(),
        }
    }
}
