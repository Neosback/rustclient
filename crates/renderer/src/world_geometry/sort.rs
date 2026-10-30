//! Camera-dependent sorting: source actor admission/front faces, retained legacy scenery ordering.
use super::{GeometryGpu, TransparentTriangle};
use crate::face_priority::{self, FaceDepth};
use glam::Vec3;
use rustclient_model::{ModelDepthPolicy, ModelPoint};
use rustclient_scene::{ModelSortSource, ModelView};
impl GeometryGpu {
    #[expect(
        clippy::cast_possible_truncation,
        clippy::cast_precision_loss,
        reason = "bounded fine-coordinate projection and reference truncation toward zero"
    )]
    pub fn sort(&mut self, queue: &wgpu::Queue, view: ModelView) -> bool {
        let data = &mut self.data;
        if data.last_sort == Some(view) {
            data.ordering_reuses += 1;
            return false;
        }
        data.ordering_runs += 1;
        let eye = Vec3::from_array(view.eye().map(|v| v as f32));
        let [ys, yc, ps, pc] = view.coefficients();
        let direction = Vec3::new(-ys * pc, ps, yc * pc);
        data.model_order.clear();
        data.model_order.extend(
            data.models
                .iter()
                .enumerate()
                .filter(|(_, m)| !m.faces.is_empty())
                .map(|(i, _)| i),
        );
        data.model_order.sort_unstable_by(|&a, &b| {
            data.models[b]
                .center
                .distance_squared(eye)
                .total_cmp(&data.models[a].center.distance_squared(eye))
                .then(a.cmp(&b))
        });
        data.sorted.clear();
        for &model in &data.model_order {
            let model = &data.models[model];
            if let Some(span) = &model.opaque_span {
                data.opaque_order[span.clone()].fill(0);
            }
            if model
                .source
                .as_ref()
                .is_some_and(|source| !source.admitted(view))
            {
                continue;
            }
            data.face_depths.clear();
            for (index, face) in model.faces.iter().enumerate() {
                let depth = if let Some(source) = &model.source {
                    let points = face
                        .points
                        .map(|[x, y, z]| ModelPoint::new(i32::from(x), i32::from(y), i32::from(z)));
                    let Some(depth) = source_depth(source, view, points) else {
                        continue;
                    };
                    depth
                } else {
                    (face.center - eye).dot(direction) as i32
                };
                data.face_depths.push(FaceDepth {
                    index,
                    priority: face.priority,
                    depth,
                });
            }
            if model.source.is_some() && model.depth_policy == ModelDepthPolicy::DepthWrite {
                data.face_depths
                    .sort_unstable_by(|a, b| b.depth.cmp(&a.depth).then(a.index.cmp(&b.index)));
                data.face_order.clear();
                data.face_order
                    .extend(data.face_depths.iter().map(|face| face.index));
            } else {
                face_priority::order(&mut data.face_depths, &mut data.face_order);
            }
            let mut opaque_cursor = model.opaque_span.as_ref().map_or(0, |span| span.start);
            for &index in &data.face_order {
                let face = model.faces[index];
                if face.alpha {
                    data.sorted.push(TransparentTriangle {
                        center: model.center,
                        ..face
                    });
                } else if model.opaque_span.is_some() {
                    data.opaque_order[opaque_cursor..opaque_cursor + 3]
                        .copy_from_slice(&face.indices);
                    opaque_cursor += 3;
                }
            }
        }
        data.order.clear();
        for face in &data.sorted {
            data.order.extend_from_slice(&face.indices);
        }
        if !data.order.is_empty() {
            queue.write_buffer(&self.transparent, 0, bytemuck::cast_slice(&data.order));
        }
        if !data.opaque_order.is_empty() {
            queue.write_buffer(&self.opaque, 0, bytemuck::cast_slice(&data.opaque_order));
        }
        data.last_sort = Some(view);
        true
    }
}
/// The source truncates each vertex before subtracting the truncated origin and taking the face
/// average. Front faces alone contribute to priority averages. Keep this arithmetic independent
/// of a renderer buffer so the literal Java uploader can supply differential vectors.
#[expect(
    clippy::cast_possible_truncation,
    reason = "source float-to-int depth casts"
)]
pub(super) fn source_depth(
    source: &ModelSortSource,
    view: ModelView,
    points: [ModelPoint; 3],
) -> Option<i32> {
    let projected = points.map(|point| view.project(point));
    if !ModelView::front_projected(projected) {
        return None;
    }
    let zero = i64::from(view.project(source.origin)[2] as i32);
    let sum = projected
        .iter()
        .map(|p| i64::from(p[2] as i32) - zero)
        .sum::<i64>();
    i32::try_from(i64::from(source.radius) + sum / 3).ok()
}

#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn literal_java_face_depth_and_front_face_vectors() {
        // Independently executed source expressions in tools/gpu/model_sort_oracle.py.
        let view = ModelView::new([13, -70, 29], 321, 177);
        let [a, b, c] = [
            ModelPoint::new(-100, 100, 800),
            ModelPoint::new(100, 100, 800),
            ModelPoint::new(0, -100, 800),
        ];
        let mut source = ModelSortSource {
            origin: ModelPoint::new(0, 0, 800),
            vertices: [a, b, c].into(),
            radius: 200,
            diameter: 400,
        };
        assert_eq!(source_depth(&source, view, [a, b, c]), Some(217));
        assert_eq!(source_depth(&source, view, [a, c, b]), None);
        source.origin.z = 900;
        assert_eq!(
            source_depth(&source, view, [a, b, ModelPoint::new(0, -100, 807)]),
            Some(172)
        );
        // Truncating the projected centroid instead gives 171; negative integer division matters.
    }
}
