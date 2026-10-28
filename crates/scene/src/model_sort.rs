//! Immutable inputs for the sorted actor uploader; camera decisions stay disposable.
use crate::{Result, SceneError};
use rustclient_model::ModelPoint;
use std::sync::Arc;

/// Source near boundary in fine units, shared by projection and whole-model admission.
pub const SORT_NEAR: f32 = 50.;
/// Inputs discarded by triangle extraction but required by the reference sorted uploader.
#[derive(Debug, Clone)]
pub struct ModelSortSource {
    pub origin: ModelPoint,
    /// Every placed posed vertex, including unused/hidden-face vertices.
    pub vertices: Arc<[ModelPoint]>,
    pub radius: i32,
    pub diameter: i32,
}
impl ModelSortSource {
    /// Derive cylindrical bounds before orientation, matching Model.method5254.
    /// # Errors
    /// Rejects mismatched model/placed vertex counts.
    #[expect(
        clippy::cast_precision_loss,
        clippy::cast_possible_truncation,
        reason = "reference float bounds; oversized cylinders saturate and are rejected at admission"
    )]
    pub fn new(origin: ModelPoint, local: &[ModelPoint], placed: Vec<ModelPoint>) -> Result<Self> {
        if local.len() != placed.len() {
            return Err(SceneError("sorted-model vertex count mismatch".into()));
        }
        let (mut top, mut bottom, mut square) = (0_f32, 0_f32, 0_f32);
        for point in local {
            let (x, y, z) = (point.x as f32, point.y as f32, point.z as f32);
            top = top.max(-y);
            bottom = bottom.max(y);
            square = square.max(x * x + z * z);
        }
        let horizontal = f64::from(square).sqrt().ceil();
        let top = f64::from(top).ceil();
        let bottom = f64::from(bottom).ceil();
        let radius = (horizontal * horizontal + top * top).sqrt().ceil();
        let diameter = radius + (horizontal * horizontal + bottom * bottom).sqrt().ceil();
        Ok(Self {
            origin,
            vertices: placed.into(),
            radius: radius as i32,
            diameter: diameter as i32,
        })
    }
    #[must_use]
    pub fn admitted(&self, view: ModelView) -> bool {
        self.diameter < 6000
            && self
                .vertices
                .iter()
                .all(|&p| view.project(p)[2] >= SORT_NEAR)
    }
}

/// Revision-client float view coefficients, independent from viewport size and GPU resources.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct ModelView {
    eye: [i32; 3],
    trig: [u32; 4],
}
impl ModelView {
    #[must_use]
    #[expect(
        clippy::cast_possible_truncation,
        reason = "Scene stores Math.sin/cos as float"
    )]
    pub fn new(eye: [i32; 3], yaw: u16, pitch: u16) -> Self {
        // client.method2268 converts 14-bit JAU to float radians. The existing integer
        // camera supplies 11-bit angles; fractional/interpolated camera poses remain separate.
        let angle = |value: u16| f64::from((f32::from(value & 2047) * 8.) * 3.834_952E-4_f32);
        let (ys, yc) = angle(yaw).sin_cos();
        let (ps, pc) = angle(pitch).sin_cos();
        Self {
            eye,
            trig: [ys, yc, ps, pc].map(|v| (v as f32).to_bits()),
        }
    }
    #[must_use]
    pub fn eye(self) -> [i32; 3] {
        self.eye
    }
    #[must_use]
    pub fn coefficients(self) -> [f32; 4] {
        self.trig.map(f32::from_bits)
    }
    /// Same operation order as IntProjection.project, with perspective division left to callers.
    #[must_use]
    #[expect(
        clippy::cast_precision_loss,
        reason = "bounded scene-local fine coordinates"
    )]
    pub fn project(self, point: ModelPoint) -> [f32; 3] {
        let [ys, yc, ps, pc] = self.coefficients();
        let x = point.x as f32 - self.eye[0] as f32;
        let y = point.y as f32 - self.eye[1] as f32;
        let z = point.z as f32 - self.eye[2] as f32;
        let horizontal = x * yc + z * ys;
        let forward = z * yc - x * ys;
        [horizontal, y * pc - forward * ps, forward * pc + y * ps]
    }
    #[must_use]
    pub fn front_face(self, points: [ModelPoint; 3]) -> bool {
        Self::front_projected(points.map(|p| self.project(p)))
    }
    #[must_use]
    pub fn front_projected([a, b, c]: [[f32; 3]; 3]) -> bool {
        let [ax, ay] = [a[0] / a[2], a[1] / a[2]];
        let [bx, by] = [b[0] / b[2], b[1] / b[2]];
        let [cx, cy] = [c[0] / c[2], c[1] / c[2]];
        (ax - bx) * (cy - by) - (cx - bx) * (ay - by) > 0.
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn literal_java_projection_and_bounds_vectors() {
        // tools/gpu/model_sort_oracle.py extracts IntProjection.project and Model.method5254.
        let view = ModelView::new([13, -70, 29], 321, 177);
        let points = [
            ModelPoint::new(-100, 100, 800),
            ModelPoint::new(100, 100, 800),
            ModelPoint::new(0, -100, 800),
        ];
        assert_eq!(
            points.map(|p| view.project(p).map(|v| v.to_bits().cast_signed())),
            [
                [1_141_962_888, -1_024_009_660, 1_141_202_776],
                [1_143_775_013, -1_038_792_472, 1_136_880_072],
                [1_142_868_951, -1_015_309_716, 1_135_831_014],
            ]
        );
        let local = [ModelPoint::new(3, -4, 4), ModelPoint::new(1, 2, 1)];
        let source =
            ModelSortSource::new(ModelPoint::new(0, 0, 0), &local, local.to_vec()).unwrap();
        assert_eq!((source.radius, source.diameter), (7, 13));
        assert!(ModelSortSource::new(source.origin, &local, vec![]).is_err());
    }
    #[test]
    fn whole_model_admission_includes_unused_vertices_and_excludes_oversized_cylinders() {
        let view = ModelView::new([0; 3], 0, 0);
        let point = ModelPoint::new(0, 0, 50);
        let mut source =
            ModelSortSource::new(ModelPoint::new(0, 0, 0), &[point], vec![point]).unwrap();
        assert!(source.admitted(view)); // Source comparison is strictly less than 50.
        source.vertices = vec![point, ModelPoint::new(100, 100, 49)].into();
        assert!(!source.admitted(view)); // Unused/hidden vertices still reject the whole model.
        source.vertices = vec![point].into();
        source.diameter = 5999;
        assert!(source.admitted(view));
        source.diameter = 6000;
        assert!(!source.admitted(view));
    }
    #[test]
    fn front_faces_exclude_reversed_and_degenerate_triangles() {
        let view = ModelView::new([0; 3], 0, 0);
        let [a, b, c] = [
            ModelPoint::new(-10, 10, 100),
            ModelPoint::new(10, 10, 100),
            ModelPoint::new(0, -10, 100),
        ];
        assert!(view.front_face([a, b, c]));
        assert!(!view.front_face([a, c, b]));
        assert!(!view.front_face([a, a, c]));
        assert_eq!(view, ModelView::new([0; 3], 2048, 2048));
    }
}
