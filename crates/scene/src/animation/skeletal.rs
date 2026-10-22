//! Cached-model ("skeletal") pose: per-bone curves, bone hierarchy and weighted vertex skinning.
use super::{
    curve::BakedCurve,
    matrix::{self, IDENTITY, Matrix, Quaternion},
};
use crate::{Result, SceneError};
use rustclient_cache::world::{BONE_CHANNELS, CachedAnimation, ModelFace, Skeleton};
use rustclient_model::ModelPoint;

/// Skeleton transform kind that drives face transparency.
const TRANSFORM_ALPHA: u8 = 5;

/// Bind pose a bone falls back to for any channel without a curve.
#[derive(Debug, Clone, Copy)]
struct BoneDefaults {
    rotation: [f32; 3],
    translation: [f32; 3],
    scale: [f32; 3],
}

#[derive(Debug, Clone)]
struct AlphaChannel {
    labels: Vec<u8>,
    curve: BakedCurve,
}

/// An animation prepared for playback: curves baked per frame and bind-pose matrices resolved.
///
/// Built once from decoded cache data; posing reads it immutably. Derived data only, so it can be
/// dropped and rebuilt from the cache at any time.
#[derive(Debug, Clone)]
pub struct BakedAnimation {
    parents: Vec<Option<usize>>,
    /// Bone indices ordered so every parent precedes its children.
    order: Vec<usize>,
    defaults: Vec<BoneDefaults>,
    inverse_bind: Vec<Matrix>,
    curves: Vec<[Option<BakedCurve>; BONE_CHANNELS]>,
    alpha: Vec<AlphaChannel>,
}

impl BakedAnimation {
    /// Resolve `animation` against the skeleton it was authored for.
    /// # Errors
    /// Returns an error when the skeleton has no bone hierarchy or does not match the animation.
    pub fn new(animation: &CachedAnimation, skeleton: &Skeleton) -> Result<Self> {
        let hierarchy = skeleton
            .bones
            .as_ref()
            .ok_or_else(|| SceneError("cached animation skeleton has no bones".into()))?;
        let count = hierarchy.bones.len();
        if animation.bone_curves.len() != count {
            return Err(SceneError("cached animation bone count mismatch".into()));
        }
        let variant = usize::from(animation.variant);
        let parents: Vec<Option<usize>> = hierarchy.bones.iter().map(|b| b.parent).collect();
        if parents.iter().flatten().any(|parent| *parent >= count) {
            return Err(SceneError("bone parent outside hierarchy".into()));
        }
        // Parents precede children: sort by chain depth. Cycles were rejected by the decoder.
        let depth = |mut bone: usize| {
            let mut depth = 0_usize;
            while let Some(parent) = parents[bone] {
                bone = parent;
                depth += 1;
                if depth > count {
                    return Err(SceneError("cyclic bone hierarchy".into()));
                }
            }
            Ok(depth)
        };
        let mut order: Vec<(usize, usize)> = (0..count)
            .map(|bone| depth(bone).map(|depth| (depth, bone)))
            .collect::<Result<_>>()?;
        order.sort_unstable();
        let order: Vec<usize> = order.into_iter().map(|(_, bone)| bone).collect();

        let mut bind_world: Vec<Matrix> = vec![IDENTITY; count];
        let mut defaults = Vec::with_capacity(count);
        for &bone in &order {
            let bind = hierarchy.bones[bone]
                .bind
                .get(variant)
                .ok_or_else(|| SceneError("pose variant outside bone variants".into()))?;
            bind_world[bone] =
                parents[bone].map_or(*bind, |p| matrix::multiply(bind, &bind_world[p]));
        }
        for bone in &hierarchy.bones {
            let bind = bone
                .bind
                .get(variant)
                .ok_or_else(|| SceneError("pose variant outside bone variants".into()))?;
            let determinant = matrix::determinant(bind);
            if !determinant.is_finite() || determinant == 0. {
                return Err(SceneError("singular or non-finite bind matrix".into()));
            }
            defaults.push(BoneDefaults {
                rotation: matrix::euler(&matrix::invert(bind)),
                translation: [bind[12], bind[13], bind[14]],
                scale: matrix::row_lengths(bind),
            });
        }
        let inverse_bind = bind_world.iter().map(matrix::invert).collect();

        let curves = animation
            .bone_curves
            .iter()
            .map(|channels| std::array::from_fn(|i| channels[i].as_ref().map(BakedCurve::bake)))
            .collect();
        let alpha = animation
            .transform_curves
            .iter()
            .filter_map(|(&index, channels)| {
                let transform = skeleton.transforms.get(index)?;
                let curve = channels.first()?.as_ref()?;
                (transform.kind == TRANSFORM_ALPHA).then(|| AlphaChannel {
                    labels: transform.labels.clone(),
                    curve: BakedCurve::bake(curve),
                })
            })
            .collect();
        Ok(Self {
            parents,
            order,
            defaults,
            inverse_bind,
            curves,
            alpha,
        })
    }

    /// Local bone matrix at `frame`: rotation then scale, with translation written last.
    fn local(&self, bone: usize, frame: i32) -> Matrix {
        let defaults = &self.defaults[bone];
        let channels = &self.curves[bone];
        let value = |channel: usize, default: f32| {
            channels[channel]
                .as_ref()
                .map_or(default, |curve| curve.value(frame))
        };
        let rotation = [
            value(0, defaults.rotation[0]),
            value(1, defaults.rotation[1]),
            value(2, defaults.rotation[2]),
        ];
        let translation = [
            value(3, defaults.translation[0]),
            value(4, defaults.translation[1]),
            value(5, defaults.translation[2]),
        ];
        let scale = [
            value(6, defaults.scale[0]),
            value(7, defaults.scale[1]),
            value(8, defaults.scale[2]),
        ];
        let about_x = Quaternion::axis_angle(1., 0., 0., rotation[0]);
        let about_y = Quaternion::axis_angle(0., 1., 0., rotation[1]);
        let about_z = Quaternion::axis_angle(0., 0., 1., rotation[2]);
        let combined = Quaternion::IDENTITY
            .multiply(about_z)
            .multiply(about_x)
            .multiply(about_y);
        let rotated = matrix::multiply(&IDENTITY, &combined.matrix());
        let mut local = matrix::multiply(&rotated, &matrix::scale(scale[0], scale[1], scale[2]));
        local[12] = translation[0];
        local[13] = translation[1];
        local[14] = translation[2];
        local
    }

    /// Matrix taking a bind-pose point to its posed position, for every bone.
    fn skin_matrices(&self, frame: i32) -> Vec<Matrix> {
        let mut world: Vec<Matrix> = vec![IDENTITY; self.order.len()];
        let mut skin = vec![IDENTITY; self.order.len()];
        for &bone in &self.order {
            let local = self.local(bone, frame);
            world[bone] = self.parents[bone].map_or(local, |p| matrix::multiply(&local, &world[p]));
            skin[bone] = matrix::multiply(&self.inverse_bind[bone], &world[bone]);
        }
        skin
    }
}

/// Pose `vertices` and face transparency at animation `frame`.
///
/// Each weighted vertex becomes the weight-blended sum of its bones' skin matrices applied to
/// the vertex in the reference's negated-Y/Z space, truncated to integers. Vertices without
/// weights are untouched.
/// # Errors
/// Returns an error when `weights` does not describe every vertex.
pub(crate) fn pose(
    animation: &BakedAnimation,
    frame: i32,
    vertices: &mut [ModelPoint],
    faces: &mut [ModelFace],
    weights: &[Vec<(u8, u8)>],
) -> Result<()> {
    if weights.len() != vertices.len() {
        return Err(SceneError("vertex weight count mismatch".into()));
    }
    let skin = animation.skin_matrices(frame);
    for (vertex, influences) in vertices.iter_mut().zip(weights) {
        if influences.is_empty() {
            continue;
        }
        // Matrix columns 0..=2 are the only ones that reach the vertex.
        let mut sum = [0.0_f32; 16];
        for &(bone, weight) in influences {
            let Some(matrix) = skin.get(usize::from(bone)) else {
                continue;
            };
            let weight = f32::from(weight) / 255.0;
            for (slot, entry) in sum.iter_mut().zip(matrix) {
                *slot += *entry * weight;
            }
        }
        #[expect(
            clippy::cast_precision_loss,
            reason = "model coordinates are far below f32's exact integer range"
        )]
        let (x, y, z) = (vertex.x as f32, -vertex.y as f32, -vertex.z as f32);
        #[expect(
            clippy::cast_possible_truncation,
            reason = "the reference truncates the transformed coordinate toward zero"
        )]
        let transformed =
            |a: f32, b: f32, c: f32, d: f32| (a * x + b * y + c * z + d * 1.0_f32) as i32;
        *vertex = ModelPoint::new(
            transformed(sum[0], sum[4], sum[8], sum[12]),
            transformed(sum[1], sum[5], sum[9], sum[13]).wrapping_neg(),
            transformed(sum[2], sum[6], sum[10], sum[14]).wrapping_neg(),
        );
    }
    apply_alpha(animation, frame, faces);
    Ok(())
}

/// Offset face transparency by the alpha channels of the skeleton's alpha transforms.
fn apply_alpha(animation: &BakedAnimation, frame: i32, faces: &mut [ModelFace]) {
    for channel in &animation.alpha {
        let delta = channel.curve.value(frame);
        for &label in &channel.labels {
            for face in faces.iter_mut().filter(|f| f.skin == Some(label)) {
                // The -2 sentinel means "no transparency declared", which the client stores as 0.
                let base = if face.alpha == -2 {
                    0
                } else {
                    face.alpha.cast_unsigned()
                };
                #[expect(
                    clippy::cast_possible_truncation,
                    clippy::cast_sign_loss,
                    reason = "the reference adds in float, truncates, then clamps to a byte"
                )]
                let alpha = ((f32::from(base) + delta * 255.0) as i32).clamp(0, 255) as u8;
                // 254 is the sentinel's bit pattern; the nearest distinct value is fully hidden.
                face.alpha = if alpha == 254 {
                    -1
                } else {
                    alpha.cast_signed()
                };
            }
        }
    }
}
