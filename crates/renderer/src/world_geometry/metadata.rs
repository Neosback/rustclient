//! CPU-only model/priority metadata, shared by worker preparation and live streams.
use super::{
    AlphaModel, GeometryData, ModelDepthPolicy, OpaqueBatch, OpaqueRun, RenderError, SceneGeometry,
    TransparentTriangle, Vec3, point,
};
impl GeometryData {
    #[expect(
        clippy::cast_precision_loss,
        reason = "bounded model face count used for centroid averaging"
    )]
    #[expect(
        clippy::too_many_lines,
        reason = "one ordered derivation of model spans, face groups and opaque pass ranges"
    )]
    pub(super) fn metadata(&mut self, geometry: &SceneGeometry) -> Result<(), RenderError> {
        self.last_sort = None;
        self.opaque_count = u32::try_from(geometry.opaque.len())
            .map_err(|_| RenderError::World("opaque index count"))?;
        self.transparent_count = u32::try_from(geometry.transparent.len())
            .map_err(|_| RenderError::World("alpha index count"))?;
        for model in &mut self.models {
            model.faces.clear();
            model.opaque_span = None;
            model.hidden = false;
        }
        self.hidden.clear();
        self.model_sources.clone_from(&geometry.model_sources);
        self.location_orders = geometry
            .models
            .iter()
            .enumerate()
            .map(|(i, _)| geometry.model_locations.get(i).map(|m| m.order))
            .collect();
        self.model_groups.clear();
        self.model_groups.resize(geometry.models.len(), None);
        let mut used = 0;

        for (alpha, indices) in [(true, &geometry.transparent), (false, &geometry.opaque)] {
            for (offset, triangle) in indices.as_chunks::<3>().0.iter().enumerate() {
                let first = triangle[0] as usize;
                let group = geometry
                    .models
                    .partition_point(|model| model.vertices.end <= first);
                let group = geometry
                    .models
                    .get(group)
                    .filter(|model| model.vertices.contains(&first))
                    .map(|_| group);
                let policy = group.map_or(ModelDepthPolicy::DepthWrite, |group| {
                    geometry.models[group].depth_policy
                });
                if let Some(group) = group
                    && triangle
                        .iter()
                        .any(|&index| !geometry.models[group].vertices.contains(&(index as usize)))
                {
                    return Err(RenderError::World("triangle crosses model spans"));
                }
                let model = if let Some(group) = group {
                    if let Some(model) = self.model_groups[group] {
                        model
                    } else if alpha || policy == ModelDepthPolicy::SortedDeferredWrite {
                        let model = self.reserve_model(used, policy);
                        self.models[model]
                            .source
                            .clone_from(&geometry.models[group].sort_source);
                        used += 1;
                        self.model_groups[group] = Some(model);
                        model
                    } else {
                        continue;
                    }
                } else if alpha {
                    let model = self.reserve_model(used, policy);
                    used += 1;
                    model
                } else {
                    continue;
                };
                if !alpha
                    && (policy == ModelDepthPolicy::SortedDeferredWrite
                        || self.models[model].source.is_some())
                {
                    let span = &mut self.models[model].opaque_span;
                    let start = offset * 3;
                    if let Some(span) = span {
                        if span.end != start {
                            return Err(RenderError::World(
                                "deferred model indices are not contiguous",
                            ));
                        }
                        span.end += 3;
                    } else {
                        *span = Some(start..start + 3);
                    }
                }
                let mut center = Vec3::ZERO;
                for &index in triangle {
                    let vertex = geometry
                        .vertices
                        .get(index as usize)
                        .ok_or(RenderError::World("alpha vertex index"))?;
                    center += point(vertex);
                }
                let priority = u8::try_from(geometry.vertices[first].position[3])
                    .ok()
                    .filter(|p| *p <= 11)
                    .ok_or(RenderError::World("face priority"))?;
                self.models[model].faces.push(TransparentTriangle {
                    center: center / 3.,
                    indices: [triangle[0], triangle[1], triangle[2]].map(|index| {
                        self.vertex_remap
                            .get(index as usize)
                            .copied()
                            .unwrap_or(index)
                    }),
                    ordinal: first / 3,
                    location_order: group
                        .and_then(|g| geometry.model_locations.get(g).map(|m| m.order)),
                    priority,
                    alpha,
                    points: triangle.map(|index| {
                        let point = geometry.vertices[index as usize].position;
                        [point[0], point[1], point[2]]
                    }),
                });
            }
        }
        for model in self.models.iter_mut().take(used) {
            // Emission order, not opacity-pass order, resolves equal face depths.
            model.faces.sort_unstable_by_key(|face| face.ordinal);
            model.center =
                model.faces.iter().map(|face| face.center).sum::<Vec3>() / model.faces.len() as f32;
        }
        self.opaque_order.clear();
        if self
            .models
            .iter()
            .take(used)
            .any(|model| model.opaque_span.is_some())
        {
            self.opaque_order
                .extend(geometry.opaque.iter().map(|&index| {
                    self.vertex_remap
                        .get(index as usize)
                        .copied()
                        .unwrap_or(index)
                }));
        }
        self.opaque_runs.clear();
        for (index, triangle) in geometry.opaque.as_chunks::<3>().0.iter().enumerate() {
            let first = triangle[0] as usize;
            let group = geometry
                .models
                .partition_point(|model| model.vertices.end <= first);
            let group = geometry
                .models
                .get(group)
                .filter(|model| model.vertices.contains(&first))
                .map(|_| group);
            let policy = group.map_or(ModelDepthPolicy::DepthWrite, |group| {
                geometry.models[group].depth_policy
            });
            let start =
                u32::try_from(index * 3).map_err(|_| RenderError::World("opaque batch index"))?;
            if let Some(run) = self.opaque_runs.last_mut()
                && run.group == group
                && run.indices.end == start
            {
                run.indices.end += 3;
            } else {
                self.opaque_runs.push(OpaqueRun {
                    indices: start..start + 3,
                    policy,
                    group,
                });
            }
        }
        self.rebuild_batches();
        Ok(())
    }
    /// Draw ranges: runs coalesced by depth policy, without the hidden models' runs.
    fn rebuild_batches(&mut self) {
        self.opaque_batches.clear();
        for run in &self.opaque_runs {
            if run.group.is_some_and(|group| self.group_hidden(group)) {
                continue;
            }
            if let Some(batch) = self.opaque_batches.last_mut()
                && batch.policy == run.policy
                && batch.indices.end == run.indices.start
            {
                batch.indices.end = run.indices.end;
            } else {
                self.opaque_batches.push(OpaqueBatch {
                    indices: run.indices.clone(),
                    policy: run.policy,
                });
            }
        }
    }
    pub(super) fn group_hidden(&self, group: usize) -> bool {
        self.model_sources
            .get(group)
            .is_some_and(|source| self.hidden.binary_search(source).is_ok())
    }
    pub(super) fn set_hidden(&mut self, sources: &[u32]) -> bool {
        if self.hidden == sources {
            return false;
        }
        self.hidden.clear();
        self.hidden.extend_from_slice(sources);
        for group in 0..self.model_groups.len() {
            if let Some(model) = self.model_groups[group] {
                self.models[model].hidden = self.group_hidden(group);
            }
        }
        self.rebuild_batches();
        // Alpha and deferred models re-sort so their hidden faces leave both index buffers.
        self.last_sort = None;
        true
    }
    fn reserve_model(&mut self, index: usize, policy: ModelDepthPolicy) -> usize {
        if index == self.models.len() {
            self.models.push(AlphaModel {
                center: Vec3::ZERO,
                faces: Vec::new(),
                depth_policy: policy,
                opaque_span: None,
                source: None,
                hidden: false,
            });
        }
        self.models[index].depth_policy = policy;
        self.models[index].source = None;
        self.models[index].hidden = false;
        index
    }
}
