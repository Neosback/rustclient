//! Resident terrain and owning-zone location buffers; one global source draw order.
use super::{GeometryGpu, PreparedGeometry};
use crate::{RenderError, WorldGeometryStats};
use glam::Vec3;
use rustclient_model::ModelDepthPolicy;
use rustclient_scene::{LocationOrder, LocationZone, ModelView, SceneGeometry, SceneZone};
use std::{
    collections::{BTreeMap, BinaryHeap},
    ops::Range,
    sync::Arc,
};

pub(crate) struct PreparedScenery {
    base: (SceneGeometry, PreparedGeometry),
    zones: Vec<(SceneZone, SceneGeometry, PreparedGeometry)>,
}
impl PreparedScenery {
    pub fn new(source: &SceneGeometry) -> Result<Self, RenderError> {
        super::validate(source, u64::MAX)?;
        // Older diagnostics can supply geometry without location ownership metadata.
        let (base, zones) =
            if !source.models.is_empty() && source.model_locations.len() == source.models.len() {
                let (base, zones) = rustclient_scene::location_partitions(source)
                    .map_err(|_| RenderError::World("invalid owning-zone partition"))?;
                let base = Self::prepare_part(base)?;
                let zones = zones
                    .map(|part| {
                        let (id, geometry) =
                            part.map_err(|_| RenderError::World("invalid owning-zone partition"))?;
                        let (geometry, prepared) = Self::prepare_part(geometry)?;
                        Ok((id, geometry, prepared))
                    })
                    .collect::<Result<_, RenderError>>()?;
                (base, zones)
            } else {
                (Self::prepare_part(source.clone())?, Vec::new())
            };
        Ok(Self { base, zones })
    }
    fn prepare_part(
        mut geometry: SceneGeometry,
    ) -> Result<(SceneGeometry, PreparedGeometry), RenderError> {
        let prepared = PreparedGeometry::new(&geometry, true)?;
        prepared.release_packed_source(&mut geometry);
        Ok((geometry, prepared))
    }
    pub fn validate_for(&self, _source: &SceneGeometry, maximum: u64) -> Result<(), RenderError> {
        self.base.1.validate_for(&self.base.0, maximum)?;
        for (_, geometry, prepared) in &self.zones {
            prepared.validate_for(geometry, maximum)?;
        }
        Ok(())
    }
    pub fn upload(
        self,
        device: &wgpu::Device,
        source: &SceneGeometry,
    ) -> Result<SceneryGpu, RenderError> {
        self.validate_for(source, device.limits().max_buffer_size)?;
        let base = self.base.1.upload(device, &self.base.0)?;
        let mut zones = BTreeMap::new();
        for (id, geometry, prepared) in self.zones {
            zones.insert(
                id,
                ZoneGpu {
                    geometry: prepared.upload(device, &geometry)?,
                    revision: Arc::new(()),
                },
            );
        }
        Ok(SceneryGpu {
            base,
            zones,
            alpha: Vec::new(),
            opaque: Vec::new(),
            last_publication: Arc::default(),
            changed: true,
            pending: None,
            last_enqueued_bytes: 0,
            publication: 0,
            retired_bytes: Arc::new(std::sync::atomic::AtomicU64::new(0)),
            #[cfg(target_arch = "wasm32")]
            web_retired: Vec::new(),
        })
    }
    pub fn replace(
        self,
        current: &mut SceneryGpu,
        device: &wgpu::Device,
        queue: &wgpu::Queue,
        source: &SceneGeometry,
    ) -> Result<(), RenderError> {
        let next = self.upload(device, source)?;
        let old = std::mem::replace(current, next);
        // No rendering callback waits. wgpu also retains submitted handles internally.
        #[cfg(not(target_arch = "wasm32"))]
        queue.on_submitted_work_done(move || drop(old));
        #[cfg(target_arch = "wasm32")]
        current
            .web_retired
            .push(super::retirement::WebRetirement::new(queue, Box::new(old)));
        Ok(())
    }
}
struct ZoneGpu {
    geometry: GeometryGpu,
    revision: Arc<()>,
}
#[derive(Clone, Copy)]
struct AlphaRef {
    zone: Option<SceneZone>,
    face: u32,
    depth: f32,
}
struct OpaqueRef {
    zone: Option<SceneZone>,
    indices: Range<u32>,
    policy: ModelDepthPolicy,
    order: Option<LocationOrder>,
}
/// Separate allocations can be replaced without copying unrelated resident vertices.
pub(crate) struct SceneryGpu {
    base: GeometryGpu,
    zones: BTreeMap<SceneZone, ZoneGpu>,
    alpha: Vec<AlphaRef>,
    opaque: Vec<OpaqueRef>,
    last_publication: Arc<[LocationZone]>,
    changed: bool,
    pending: Option<PendingZones>,
    pub(crate) last_enqueued_bytes: u64,
    pub publication: u64,
    retired_bytes: Arc<std::sync::atomic::AtomicU64>,
    #[cfg(target_arch = "wasm32")]
    web_retired: Vec<super::retirement::WebRetirement>,
}
impl SceneryGpu {
    pub fn new_static(device: &wgpu::Device, source: &SceneGeometry) -> Result<Self, RenderError> {
        PreparedScenery::new(source)?.upload(device, source)
    }
    /// Latest derived target only; superseded partial groups never publish.
    pub fn queue_zones(
        &mut self,
        update: super::PreparedLocationUpdate,
    ) -> Result<(), RenderError> {
        self.queue(update, false)
    }
    pub fn queue_deferred_zones(
        &mut self,
        update: super::PreparedLocationUpdate,
    ) -> Result<(), RenderError> {
        self.queue(update, true)
    }
    fn queue(
        &mut self,
        update: super::PreparedLocationUpdate,
        deferred: bool,
    ) -> Result<(), RenderError> {
        if self.publication_is(&update.0.publication)
            || self.pending.as_ref().is_some_and(|p| {
                rustclient_scene::same_location_zones(
                    &p.update.0.publication,
                    &update.0.publication,
                )
            })
        {
            return Ok(());
        }
        let replacing: std::collections::BTreeSet<_> =
            update.0.entries.iter().map(|(z, _)| z.zone).collect();
        let retained = self
            .zones
            .iter()
            .filter(|(id, _)| !replacing.contains(id))
            .map(|(_, z)| z.geometry.data.expanded_vertex_bytes)
            .sum::<u64>();
        let pending = update
            .0
            .entries
            .iter()
            .map(|(z, _)| (z.geometry.vertices.len() * size_of::<super::SceneVertex>()) as u64)
            .sum::<u64>();
        let limit = (rustclient_scene::SCENE_VERTEX_LIMIT * size_of::<super::SceneVertex>()) as u64;
        if retained
            .saturating_add(pending)
            .saturating_add(self.base.data.expanded_vertex_bytes)
            > limit
        {
            return Err(RenderError::World(
                "resident scenery exceeds existing scene vertex budget",
            ));
        }
        self.pending = Some(PendingZones {
            update,
            ready: Vec::new(),
            upload: None,
            cursor: 0,
            validated: false,
            deferred,
        });
        Ok(())
    }
    pub fn ready_for(&self, zones: &Arc<[LocationZone]>) -> bool {
        self.publication_is(zones)
            || self.pending.as_ref().is_some_and(|p| {
                p.cursor == p.update.0.entries.len()
                    && p.upload.is_none()
                    && rustclient_scene::same_location_zones(&p.update.0.publication, zones)
            })
    }
    pub fn commit_ready(&mut self, queue: &wgpu::Queue, zones: &Arc<[LocationZone]>) -> bool {
        if self.publication_is(zones) {
            return true;
        }
        if !self.ready_for(zones) {
            return false;
        }
        let Some(pending) = self.pending.take() else {
            return false;
        };
        let mut retired = Vec::new();
        for (zone, next) in pending.ready {
            if let Some(old) = self.zones.insert(zone, next) {
                retired.push(old);
            }
        }
        if !retired.is_empty() {
            let bytes = retired
                .iter()
                .map(|z| z.geometry.stats().gpu_buffer_bytes)
                .sum();
            self.retired_bytes
                .fetch_add(bytes, std::sync::atomic::Ordering::Relaxed);
            let tracker = Arc::clone(&self.retired_bytes);
            #[cfg(target_arch = "wasm32")]
            self.web_retired.push(super::retirement::WebRetirement::new(
                queue,
                Box::new(retired),
            ));
            queue.on_submitted_work_done(move || {
                #[cfg(not(target_arch = "wasm32"))]
                drop(retired);
                tracker.fetch_sub(bytes, std::sync::atomic::Ordering::Relaxed);
            });
        }
        self.last_publication = Arc::clone(&pending.update.0.publication);
        self.publication = self.publication.wrapping_add(1).max(1);
        self.changed = true;
        true
    }
    pub fn has_pending(&self) -> bool {
        self.pending.is_some()
    }
    pub fn publication_is(&self, zones: &Arc<[LocationZone]>) -> bool {
        rustclient_scene::same_location_zones(&self.last_publication, zones)
    }
    /// A shared 2 MiB / 1 ms enqueue budget, after frame-critical actor work.
    pub fn advance_zones(
        &mut self,
        device: &wgpu::Device,
        queue: &wgpu::Queue,
    ) -> Result<bool, RenderError> {
        #[cfg(target_arch = "wasm32")]
        self.web_retired
            .retain(super::retirement::WebRetirement::pending);
        self.last_enqueued_bytes = 0;
        let Some(pending) = &mut self.pending else {
            return Ok(true);
        };
        if self
            .retired_bytes
            .load(std::sync::atomic::Ordering::Relaxed)
            > 4 * 1024 * 1024
        {
            return Ok(false);
        }
        let started = web_time::Instant::now();
        if !pending.validated {
            for (zone, prepared) in &pending.update.0.entries {
                prepared.validate_for(&zone.geometry, device.limits().max_buffer_size)?;
            }
            pending.validated = true;
        }
        let mut budget = 2 * 1024 * 1024;
        while pending.cursor < pending.update.0.entries.len() {
            if budget < 4 || started.elapsed() >= std::time::Duration::from_millis(1) {
                break;
            }
            let (zone, prepared) = &pending.update.0.entries[pending.cursor];
            if pending.upload.is_none() {
                if self
                    .zones
                    .get(&zone.zone)
                    .is_some_and(|old| Arc::ptr_eq(&old.revision, &zone.revision))
                {
                    pending.cursor += 1;
                    continue;
                }
                pending.upload = Some(super::zone_upload::ZoneUpload::new(
                    device,
                    zone,
                    Arc::clone(prepared),
                )?);
            }
            let upload = pending
                .upload
                .as_mut()
                .ok_or(RenderError::World("missing active zone upload"))?;
            if !upload.advance(queue, &mut budget, started) {
                break;
            }
            let upload = pending
                .upload
                .take()
                .ok_or(RenderError::World("missing finished zone upload"))?;
            let id = upload.zone;
            let revision = Arc::clone(&upload.revision);
            pending.ready.push((
                id,
                ZoneGpu {
                    geometry: upload.finish(),
                    revision,
                },
            ));
            pending.cursor += 1;
        }
        self.last_enqueued_bytes = (2 * 1024 * 1024 - budget) as u64;
        if pending.cursor < pending.update.0.entries.len() || pending.upload.is_some() {
            return Ok(false);
        }
        if pending.deferred {
            return Ok(false);
        }
        let publication = Arc::clone(&pending.update.0.publication);
        Ok(self.commit_ready(queue, &publication))
    }
    pub fn set_hidden(&mut self, sources: &[u32]) -> bool {
        let mut changed = self.base.set_hidden(sources);
        for zone in self.zones.values_mut() {
            changed |= zone.geometry.set_hidden(sources);
        }
        self.changed |= changed;
        changed
    }
    fn resource(&self, zone: Option<SceneZone>) -> &GeometryGpu {
        zone.and_then(|z| self.zones.get(&z))
            .map_or(&self.base, |z| &z.geometry)
    }
    #[cfg(not(target_arch = "wasm32"))]
    pub(super) fn buffers(&self) -> BTreeMap<SceneZone, [wgpu::Buffer; 3]> {
        self.zones
            .iter()
            .map(|(&id, z)| {
                (
                    id,
                    [
                        z.geometry.vertices.clone(),
                        z.geometry.opaque.clone(),
                        z.geometry.transparent.clone(),
                    ],
                )
            })
            .collect()
    }
    #[cfg(not(target_arch = "wasm32"))]
    pub(super) fn revisions(&self) -> BTreeMap<SceneZone, Arc<()>> {
        self.zones
            .iter()
            .map(|(&id, z)| (id, Arc::clone(&z.revision)))
            .collect()
    }
    pub fn stats(&self) -> WorldGeometryStats {
        let mut sum = WorldGeometryStats::default();
        for geometry in std::iter::once(&self.base).chain(self.zones.values().map(|z| &z.geometry))
        {
            let x = geometry.stats();
            sum.gpu_buffer_bytes += x.gpu_buffer_bytes;
            sum.expanded_vertex_bytes += x.expanded_vertex_bytes;
            sum.resident_vertex_bytes += x.resident_vertex_bytes;
            sum.cpu_remap_bytes += x.cpu_remap_bytes;
            sum.ordering_runs += x.ordering_runs;
            sum.ordering_reuses += x.ordering_reuses;
        }
        sum.resident_location_zones = self.zones.len() as u64;
        sum.retired_gpu_buffer_bytes = self
            .retired_bytes
            .load(std::sync::atomic::Ordering::Relaxed);
        if let Some(pending) = &self.pending {
            sum.pending_gpu_buffer_bytes = pending
                .ready
                .iter()
                .map(|(_, z)| z.geometry.stats().gpu_buffer_bytes)
                .sum::<u64>()
                + pending
                    .upload
                    .as_ref()
                    .map_or(0, super::zone_upload::ZoneUpload::resident_bytes);
        }
        sum
    }
    #[expect(
        clippy::cast_precision_loss,
        reason = "source camera fine units use the same bounded float conversion as the existing model sorter"
    )]
    pub fn sort(&mut self, queue: &wgpu::Queue, view: ModelView) -> bool {
        let mut changed = self.changed | self.base.sort(queue, view);
        for zone in self.zones.values_mut() {
            changed |= zone.geometry.sort(queue, view);
        }
        if !changed {
            return false;
        }
        self.changed = false;
        self.opaque.clear();
        let eye = Vec3::from_array(view.eye().map(|x| x as f32));
        let mut heap = BinaryHeap::new();
        let resources = std::iter::once((None, &self.base))
            .chain(self.zones.iter().map(|(id, z)| (Some(*id), &z.geometry)));
        for (zone, geometry) in resources {
            for run in &geometry.data.opaque_runs {
                if run.group.is_some_and(|g| geometry.data.group_hidden(g)) {
                    continue;
                }
                let order = run
                    .group
                    .and_then(|g| geometry.data.location_orders.get(g).copied().flatten());
                self.opaque.push(OpaqueRef {
                    zone,
                    indices: run.indices.clone(),
                    policy: run.policy,
                    order,
                });
            }
            if let Some(face) = geometry.data.sorted.first() {
                heap.push(Head {
                    zone,
                    face: 0,
                    depth: face.center.distance_squared(eye),
                    order: face.location_order,
                });
            }
        }
        // Stable ties within one owning model preserve its original part/face order.
        self.opaque.sort_by_key(|run| run.order);
        let mut coalesced: Vec<OpaqueRef> = Vec::with_capacity(self.opaque.len());
        for run in self.opaque.drain(..) {
            if let Some(last) = coalesced.last_mut()
                && last.zone == run.zone
                && last.policy == run.policy
                && last.indices.end == run.indices.start
            {
                last.indices.end = run.indices.end;
            } else {
                coalesced.push(run);
            }
        }
        self.opaque = coalesced;
        self.alpha.clear();
        while let Some(head) = heap.pop() {
            self.alpha.push(AlphaRef {
                zone: head.zone,
                face: head.face,
                depth: head.depth,
            });
            let next = head.face + 1;
            if let Some(face) = self.resource(head.zone).data.sorted.get(next as usize) {
                heap.push(Head {
                    zone: head.zone,
                    face: next,
                    depth: face.center.distance_squared(eye),
                    order: face.location_order,
                });
            }
        }
        true
    }
    pub fn alpha_depth(&self, index: u32, _eye: Vec3) -> Option<f32> {
        self.alpha.get(index as usize).map(|f| f.depth)
    }
    pub fn draw_alpha(&self, pass: &mut wgpu::RenderPass<'_>, indices: Range<u32>) {
        let mut cursor = indices.start / 3;
        while cursor < indices.end / 3 {
            let first = self.alpha[cursor as usize];
            let mut end = cursor + 1;
            while end < indices.end / 3 {
                let next = self.alpha[end as usize];
                if next.zone != first.zone || next.face != first.face + end - cursor {
                    break;
                }
                end += 1;
            }
            self.resource(first.zone)
                .draw_alpha(pass, first.face * 3..(first.face + end - cursor) * 3);
            cursor = end;
        }
    }
    pub fn draw_opaque(
        &self,
        pass: &mut wgpu::RenderPass<'_>,
        pipelines: &crate::world_pipeline::WorldPipelines,
    ) {
        let mut bound = super::OpaqueBindings::default();
        for run in &self.opaque {
            self.resource(run.zone).draw_opaque_range(
                pass,
                pipelines,
                run.indices.clone(),
                run.policy,
                &mut bound,
            );
        }
    }
}
#[derive(Clone, Copy)]
struct Head {
    zone: Option<SceneZone>,
    face: u32,
    depth: f32,
    order: Option<LocationOrder>,
}
impl PartialEq for Head {
    fn eq(&self, other: &Self) -> bool {
        self.cmp(other).is_eq()
    }
}
impl Eq for Head {}
impl PartialOrd for Head {
    fn partial_cmp(&self, other: &Self) -> Option<std::cmp::Ordering> {
        Some(self.cmp(other))
    }
}
impl Ord for Head {
    fn cmp(&self, other: &Self) -> std::cmp::Ordering {
        self.depth
            .total_cmp(&other.depth)
            .then_with(|| other.order.cmp(&self.order))
            .then_with(|| other.zone.cmp(&self.zone))
            .then_with(|| other.face.cmp(&self.face))
    }
}

struct PendingZones {
    update: super::PreparedLocationUpdate,
    ready: Vec<(SceneZone, ZoneGpu)>,
    upload: Option<super::zone_upload::ZoneUpload>,
    cursor: usize,
    validated: bool,
    deferred: bool,
}
