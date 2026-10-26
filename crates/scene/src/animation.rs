//! Discrete legacy location poses and cached-model (skeletal) entity poses. Inputs are immutable
//! and time is explicit.
mod curve;
mod matrix;
mod skeletal;

use crate::{Result, SceneError};
use rustclient_cache::world::{
    FrameTransform, LegacyFrame, ModelFace, SequenceAsset, SkeletonTransform,
};
use rustclient_model::ModelPoint;

pub use skeletal::BakedAnimation;

/// Which animations are drawn blended between frames (`Client.setAnimationInterpolationFilter`).
///
/// The reference filter is asked about an actor's or object's current animation id; an animation
/// it rejects is posed on whole frames, and for an actor that includes its movement pose.
#[derive(Debug, Clone, Default, PartialEq, Eq)]
pub struct AnimationInterpolation {
    /// Blend player animations.
    pub players: bool,
    /// Blend NPC animations.
    pub npcs: bool,
    /// Blend scenery animations.
    pub objects: bool,
    /// Animation ids that are never blended, sorted.
    denied: std::sync::Arc<[u32]>,
}

impl AnimationInterpolation {
    /// Blend the given classes except for `denied` animation ids.
    #[must_use]
    pub fn new(players: bool, npcs: bool, objects: bool, mut denied: Vec<u32>) -> Self {
        denied.sort_unstable();
        denied.dedup();
        Self {
            players,
            npcs,
            objects,
            denied: denied.into(),
        }
    }

    /// Whether `animation` (the action animation of an actor, or an object's animation) is
    /// drawn blended, given its class switch.
    #[must_use]
    pub(crate) fn blends(
        &self,
        class_enabled: bool,
        animation: Option<rustclient_model::SequenceId>,
    ) -> bool {
        class_enabled && animation.is_none_or(|id| self.denied.binary_search(&id.get()).is_err())
    }
}
pub(crate) use skeletal::pose as pose_skeletal;

/// Animation frame of a cached-model sequence after `elapsed` client cycles.
///
/// Skeletal sequences advance one frame per cycle through `range.1 - range.0` frames. Reaching
/// the end jumps back `frame_step` frames; without a valid step the sequence has finished and
/// `None` is returned. A sequence with no frame range holds frame zero.
#[must_use]
pub(crate) fn skeletal_frame(
    sequence: &SequenceAsset,
    elapsed: u64,
    random: Option<u64>,
) -> Option<i32> {
    let (start, end) = sequence.range.unwrap_or((0, 0));
    let length = u64::from(end.saturating_sub(start));
    if length == 0 {
        return Some(0);
    }
    let first = random
        .filter(|_| sequence.frame_step.is_some())
        .map_or(0, |bits| bits % length);
    let first_pass = length - first;
    if elapsed < first_pass {
        return i32::try_from(first + elapsed).ok();
    }
    let step = u64::from(sequence.frame_step?);
    if step == 0 || step > length {
        return None;
    }
    i32::try_from(length - step + (elapsed - first_pass) % step).ok()
}
/// The frame and how many client cycles into it the sequence is (`frameCycle`: 1 right after the
/// frame changes, up to the frame's delay; 0 only before the first cycle has elapsed).
pub(crate) fn frame_position(
    sequence: &SequenceAsset,
    elapsed: u64,
    random: Option<u64>,
) -> Result<Option<(usize, u64)>> {
    if sequence.skeletal.is_some() {
        return Err(SceneError(format!(
            "sequence {} is a cached-model sequence; pose it with skeletal_frame",
            sequence.id
        )));
    }
    if sequence.frames.is_empty()
        || sequence.frames.len() != sequence.delays.len()
        || sequence.delays.contains(&0)
    {
        return Err(SceneError("invalid legacy sequence frame delays".into()));
    }
    let (mut frame, phase) = if let Some(bits) = random.filter(|_| sequence.frame_step.is_some()) {
        let frame = usize::try_from(bits % sequence.frames.len() as u64)
            .expect("modulo bounded frame count");
        (frame, (bits >> 32) % u64::from(sequence.delays[frame]))
    } else {
        (0, 0)
    };
    let mut cycle = elapsed.saturating_add(phase);
    // Skip complete loops only after reaching the first sequence boundary.
    while cycle > u64::from(sequence.delays[frame]) {
        cycle -= u64::from(sequence.delays[frame]);
        frame += 1;
        if frame == sequence.frames.len() {
            let Some(step) = sequence
                .frame_step
                .map(usize::from)
                .filter(|&n| n > 0 && n <= sequence.frames.len())
            else {
                return Ok(None);
            };
            frame -= step;
            let duration: u64 = sequence.delays[frame..].iter().map(|&d| u64::from(d)).sum();
            if cycle > duration {
                cycle = (cycle - 1) % duration + 1;
            }
        }
    }
    Ok(Some((frame, cycle)))
}
/// Blend two legacy frames of one skeleton, as `Model.interpolateFrames` does when animation
/// smoothing is active: `interval` cycles into a frame that lasts `count` cycles.
///
/// Returns `None` when the frame is posed as authored: no next frame, no elapsed interval, or a
/// next frame on a different skeleton. Rotation components blend along the shorter way round the
/// byte circle, scale blends from 128, alpha is never blended, and a label one frame moves and the
/// other leaves alone blends from the identity value (0, or 128 for scale).
#[must_use]
pub(crate) fn blend_frames(
    skeleton: &[SkeletonTransform],
    frame: &LegacyFrame,
    next: Option<&LegacyFrame>,
    interval: i32,
    count: i32,
) -> Option<LegacyFrame> {
    let next = next.filter(|next| next.skeleton == frame.skeleton)?;
    if interval == 0 || count <= 0 {
        return None;
    }
    let (mut current_index, mut next_index) = (0, 0);
    let mut transforms = Vec::new();
    for (slot, definition) in skeleton.iter().enumerate() {
        let current = frame
            .transforms
            .get(current_index)
            .filter(|transform| transform.slot == slot);
        let following = next
            .transforms
            .get(next_index)
            .filter(|transform| transform.slot == slot);
        if current.is_none() && following.is_none() {
            continue;
        }
        let identity = if matches!(definition.kind, 3 | 10) {
            128
        } else {
            0
        };
        let from = current.map_or([identity; 3], |transform| transform.values);
        let to = following.map_or([identity; 3], |transform| transform.values);
        current_index += usize::from(current.is_some());
        next_index += usize::from(following.is_some());
        let values = match definition.kind {
            2 => std::array::from_fn(|axis| {
                let mut delta = (to[axis] - from[axis]) & 0xFF;
                if delta >= 128 {
                    delta -= 256;
                }
                (from[axis] + delta * interval / count) & 0xFF
            }),
            5 => [from[0], 0, 0],
            _ => {
                std::array::from_fn(|axis| from[axis] + (to[axis] - from[axis]) * interval / count)
            }
        };
        transforms.push(FrameTransform { slot, values });
    }
    Some(LegacyFrame {
        skeleton: frame.skeleton,
        transforms,
    })
}
#[expect(
    clippy::cast_possible_truncation,
    reason = "reference 16.16 sine/cosine tables truncate floating values"
)]
fn trig(value: i32) -> (i32, i32) {
    let radians = f64::from((value & 255) * 8) * 0.003_067_961_5;
    (
        (radians.sin() * 65536.) as i32,
        (radians.cos() * 65536.) as i32,
    )
}
fn quarter(p: &mut ModelPoint, turn: u8) {
    (p.x, p.z) = match turn & 3 {
        1 => (p.z, -p.x),
        2 => (-p.x, -p.z),
        3 => (-p.z, p.x),
        _ => (p.x, p.z),
    };
}
/// Object sequences animate in the unrotated orientation, then restore placement.
pub(crate) fn pose_skeletal_oriented(
    animation: &BakedAnimation,
    frame: i32,
    vertices: &mut [ModelPoint],
    faces: &mut [ModelFace],
    weights: &[Vec<(u8, u8)>],
    orientation: u8,
) -> Result<()> {
    for p in &mut *vertices {
        quarter(p, (4 - (orientation & 3)) & 3);
    }
    pose_skeletal(animation, frame, vertices, faces, weights)?;
    for p in vertices {
        quarter(p, orientation & 3);
    }
    Ok(())
}
/// Actor movement loops indefinitely. Action playback honors the authored maximum
/// loop count and then falls back to the actor's movement sequence.
/// Frame and in-frame cycle of an actor sequence (see [`frame_position`]).
pub(crate) fn actor_frame_position(
    sequence: &SequenceAsset,
    elapsed: u64,
    action: bool,
) -> Result<Option<(usize, u64)>> {
    if sequence.frames.is_empty()
        || sequence.frames.len() != sequence.delays.len()
        || sequence.delays.contains(&0)
    {
        return Err(SceneError("invalid actor sequence frame delays".into()));
    }
    let duration: u64 = sequence.delays.iter().map(|&d| u64::from(d)).sum();
    let elapsed = if action {
        if elapsed > duration {
            let Some(step) = sequence
                .frame_step
                .map(usize::from)
                .filter(|&n| n > 0 && n <= sequence.frames.len())
            else {
                return Ok(None);
            };
            let repeat: u64 = sequence.delays[sequence.frames.len() - step..]
                .iter()
                .map(|&d| u64::from(d))
                .sum();
            if (elapsed - duration - 1) / repeat + 1 >= u64::from(sequence.max_loops) {
                return Ok(None);
            }
        }
        elapsed
    } else {
        elapsed % (duration + 1)
    };
    frame_position(sequence, elapsed, None)
}
pub(crate) fn actor_skeletal_frame(
    sequence: &SequenceAsset,
    elapsed: u64,
    action: bool,
) -> Option<i32> {
    let (start, end) = sequence.range.unwrap_or((0, 0));
    let length = u64::from(end.saturating_sub(start));
    if length == 0 {
        return Some(0);
    }
    if !action {
        return i32::try_from(elapsed % length).ok();
    }
    if elapsed >= length {
        let step = u64::from(sequence.frame_step?).min(length);
        if step == 0 || (elapsed - length) / step + 1 >= u64::from(sequence.max_loops) {
            return None;
        }
    }
    skeletal_frame(sequence, elapsed, None)
}
#[expect(
    clippy::many_single_char_names,
    reason = "reference XYZ rotations and sine/cosine arithmetic"
)]
pub(crate) fn pose(
    vertices: &mut [ModelPoint],
    faces: &mut [ModelFace],
    skins: &[Option<u8>],
    frame: &LegacyFrame,
    skeleton: &[SkeletonTransform],
    orientation: u8,
) -> Result<()> {
    if skins.len() != vertices.len() {
        return Err(SceneError("vertex skin count mismatch".into()));
    }
    for p in &mut *vertices {
        quarter(p, (4 - (orientation & 3)) & 3);
    }
    let mut groups = vec![Vec::new(); 256];
    for (i, &skin) in skins.iter().enumerate() {
        if let Some(skin) = skin {
            groups[usize::from(skin)].push(i);
        }
    }
    let mut pivot = ModelPoint::new(0, 0, 0);
    for transform in &frame.transforms {
        let definition = skeleton
            .get(transform.slot)
            .ok_or_else(|| SceneError("frame slot outside skeleton".into()))?;
        let [x, y, z] = transform.values;
        if definition.kind == 5 {
            for &label in &definition.labels {
                for f in &mut *faces {
                    if f.skin == Some(label) {
                        let alpha = (i32::from(f.alpha.cast_unsigned()) + x * 8).clamp(0, 255);
                        f.alpha = u8::try_from(alpha).expect("clamped alpha").cast_signed();
                    }
                }
            }
            continue;
        }
        if definition.kind == 0 {
            let mut sum = ModelPoint::new(0, 0, 0);
            let mut count = 0;
            for &label in &definition.labels {
                for &i in &groups[usize::from(label)] {
                    sum.x = sum.x.wrapping_add(vertices[i].x);
                    sum.y = sum.y.wrapping_add(vertices[i].y);
                    sum.z = sum.z.wrapping_add(vertices[i].z);
                    count += 1;
                }
            }
            pivot = if count == 0 {
                ModelPoint::new(x, y, z)
            } else {
                ModelPoint::new(sum.x / count + x, sum.y / count + y, sum.z / count + z)
            };
            continue;
        }
        for &label in &definition.labels {
            for &i in &groups[usize::from(label)] {
                let p = &mut vertices[i];
                match definition.kind {
                    1 => {
                        p.x = p.x.wrapping_add(x);
                        p.y = p.y.wrapping_add(y);
                        p.z = p.z.wrapping_add(z);
                    }
                    2 => {
                        p.x -= pivot.x;
                        p.y -= pivot.y;
                        p.z -= pivot.z;
                        if z & 255 != 0 {
                            let (s, c) = trig(z);
                            let next = p.y.wrapping_mul(s).wrapping_add(p.x.wrapping_mul(c)) >> 16;
                            p.y = p.y.wrapping_mul(c).wrapping_sub(p.x.wrapping_mul(s)) >> 16;
                            p.x = next;
                        }
                        if x & 255 != 0 {
                            let (s, c) = trig(x);
                            let next = p.y.wrapping_mul(c).wrapping_sub(p.z.wrapping_mul(s)) >> 16;
                            p.z = p.y.wrapping_mul(s).wrapping_add(p.z.wrapping_mul(c)) >> 16;
                            p.y = next;
                        }
                        if y & 255 != 0 {
                            let (s, c) = trig(y);
                            let next = p.z.wrapping_mul(s).wrapping_add(p.x.wrapping_mul(c)) >> 16;
                            p.z = p.z.wrapping_mul(c).wrapping_sub(p.x.wrapping_mul(s)) >> 16;
                            p.x = next;
                        }
                        p.x += pivot.x;
                        p.y += pivot.y;
                        p.z += pivot.z;
                    }
                    3 => {
                        p.x = (p.x - pivot.x).wrapping_mul(x) / 128 + pivot.x;
                        p.y = (p.y - pivot.y).wrapping_mul(y) / 128 + pivot.y;
                        p.z = (p.z - pivot.z).wrapping_mul(z) / 128 + pivot.z;
                    }
                    _ => {}
                }
            }
        }
    }
    for p in &mut *vertices {
        quarter(p, orientation & 3);
    }
    Ok(())
}
#[cfg(test)]
mod tests {
    use super::*;
    use rustclient_model::SequenceId;
    fn frame_index(
        sequence: &SequenceAsset,
        elapsed: u64,
        random: Option<u64>,
    ) -> Result<Option<usize>> {
        Ok(frame_position(sequence, elapsed, random)?.map(|(frame, _)| frame))
    }
    fn actor_frame(sequence: &SequenceAsset, elapsed: u64, action: bool) -> Result<Option<usize>> {
        Ok(actor_frame_position(sequence, elapsed, action)?.map(|(frame, _)| frame))
    }
    #[test]
    fn actor_idle_loops_and_finite_actions_resume_movement_after_their_last_loop() {
        let mut sequence = SequenceAsset {
            id: rustclient_model::SequenceId::new(1),
            frames: vec![1, 2],
            delays: vec![2, 3],
            frame_step: None,
            max_loops: 1,
            restart: 2,
            priority: 5,
            movement_gate_with_backlog: 0,
            movement_gate_without_backlog: 0,
            skeletal: None,
            range: None,
            height_offset: 0,
        };
        assert_eq!(actor_frame(&sequence, 6, false).unwrap(), Some(0));
        assert_eq!(actor_frame(&sequence, 11, false).unwrap(), Some(1));
        assert_eq!(actor_frame(&sequence, 5, true).unwrap(), Some(1));
        assert_eq!(actor_frame(&sequence, 6, true).unwrap(), None);
        sequence.frame_step = Some(1);
        sequence.max_loops = 2;
        assert_eq!(actor_frame(&sequence, 8, true).unwrap(), Some(1));
        assert_eq!(actor_frame(&sequence, 9, true).unwrap(), None);
        sequence.range = Some((10, 15));
        assert_eq!(actor_skeletal_frame(&sequence, 5, false), Some(0));
        assert_eq!(actor_skeletal_frame(&sequence, 5, true), Some(4));
        assert_eq!(actor_skeletal_frame(&sequence, 6, true), None);
    }
    #[test]
    fn delayed_boundaries_loopback_and_random_start_are_explicit() {
        let seq = SequenceAsset {
            id: SequenceId::new(1),
            frames: vec![1, 2, 3],
            delays: vec![2, 3, 4],
            frame_step: Some(2),
            max_loops: 1,
            restart: 2,
            priority: 5,
            movement_gate_with_backlog: 0,
            movement_gate_without_backlog: 0,
            skeletal: None,
            range: None,
            height_offset: 0,
        };
        for (cycle, frame) in [
            (0, 0),
            (2, 0),
            (3, 1),
            (5, 1),
            (6, 2),
            (9, 2),
            (10, 1),
            (17, 1),
        ] {
            assert_eq!(frame_index(&seq, cycle, None).unwrap(), Some(frame));
        }
        assert_eq!(frame_index(&seq, 0, Some(2)).unwrap(), Some(2));
        let seq = SequenceAsset {
            frame_step: None,
            ..seq
        };
        assert_eq!(frame_index(&seq, 10, None).unwrap(), None);
    }

    fn skeleton() -> Vec<SkeletonTransform> {
        // origin, translate, rotate, scale, alpha
        [0, 1, 2, 3, 5]
            .into_iter()
            .map(|kind| SkeletonTransform {
                kind,
                labels: vec![0],
            })
            .collect()
    }
    fn legacy(transforms: &[(usize, [i32; 3])]) -> LegacyFrame {
        LegacyFrame {
            skeleton: 7,
            transforms: transforms
                .iter()
                .map(|&(slot, values)| FrameTransform { slot, values })
                .collect(),
        }
    }
    #[test]
    fn frame_position_counts_cycles_into_the_current_frame() {
        let seq = SequenceAsset {
            id: SequenceId::new(1),
            frames: vec![1, 2],
            delays: vec![4, 3],
            frame_step: None,
            max_loops: 1,
            restart: 2,
            priority: 5,
            movement_gate_with_backlog: 0,
            movement_gate_without_backlog: 0,
            skeletal: None,
            range: None,
            height_offset: 0,
        };
        let at = |elapsed| frame_position(&seq, elapsed, None).unwrap();
        assert_eq!(at(0), Some((0, 0)));
        assert_eq!(at(1), Some((0, 1)));
        assert_eq!(at(4), Some((0, 4)));
        assert_eq!(at(5), Some((1, 1)));
        assert_eq!(at(7), Some((1, 3)));
    }
    #[test]
    fn blending_mixes_linearly_and_takes_the_short_way_round_rotations() {
        let skeleton = skeleton();
        let frame = legacy(&[
            (1, [0, 100, -40]),
            (2, [250, 10, 0]),
            (3, [128, 128, 128]),
            (4, [8, 0, 0]),
        ]);
        let next = legacy(&[
            (1, [40, 0, 40]),
            (2, [10, 10, 0]),
            (3, [256, 0, 128]),
            (4, [64, 0, 0]),
        ]);
        let blended = blend_frames(&skeleton, &frame, Some(&next), 1, 4).unwrap();
        let by_slot = |slot| {
            blended
                .transforms
                .iter()
                .find(|t| t.slot == slot)
                .unwrap()
                .values
        };
        // translation: a quarter of the way
        assert_eq!(by_slot(1), [10, 75, -20]);
        // rotation 250 -> 10 is +16 across the wrap, a quarter of that is +4
        assert_eq!(by_slot(2), [254, 10, 0]);
        // scale
        assert_eq!(by_slot(3), [160, 96, 128]);
        // alpha is not blended and carries no y/z
        assert_eq!(by_slot(4), [8, 0, 0]);
    }
    #[test]
    fn a_label_one_frame_leaves_alone_blends_from_the_identity() {
        let skeleton = skeleton();
        let frame = legacy(&[(1, [40, 40, 40])]);
        let next = legacy(&[(3, [256, 256, 256])]);
        let blended = blend_frames(&skeleton, &frame, Some(&next), 1, 2).unwrap();
        let by_slot = |slot| {
            blended
                .transforms
                .iter()
                .find(|t| t.slot == slot)
                .unwrap()
                .values
        };
        assert_eq!(by_slot(1), [20, 20, 20]);
        assert_eq!(by_slot(3), [192, 192, 192]);
    }
    #[test]
    fn frames_are_posed_as_authored_without_something_to_blend() {
        let skeleton = skeleton();
        let frame = legacy(&[(1, [1, 2, 3])]);
        let next = legacy(&[(1, [9, 9, 9])]);
        assert!(blend_frames(&skeleton, &frame, None, 1, 4).is_none());
        assert!(blend_frames(&skeleton, &frame, Some(&next), 0, 4).is_none());
        let other = LegacyFrame {
            skeleton: 8,
            ..next
        };
        assert!(blend_frames(&skeleton, &frame, Some(&other), 1, 4).is_none());
    }
    #[test]
    fn denied_animations_and_disabled_classes_are_not_blended() {
        let filter = AnimationInterpolation::new(true, false, true, vec![8270, 244, 8270]);
        assert!(filter.blends(filter.players, None));
        assert!(filter.blends(filter.players, Some(SequenceId::new(1))));
        assert!(!filter.blends(filter.players, Some(SequenceId::new(244))));
        assert!(!filter.blends(filter.npcs, Some(SequenceId::new(1))));
        assert!(!AnimationInterpolation::default().blends(false, None));
    }
}
