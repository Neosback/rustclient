//! Discrete legacy location poses and cached-model (skeletal) entity poses. Inputs are immutable
//! and time is explicit.
mod curve;
mod matrix;
mod skeletal;

use crate::{Result, SceneError};
use rustclient_cache::world::{LegacyFrame, ModelFace, SequenceAsset, SkeletonTransform};
use rustclient_model::ModelPoint;

pub use skeletal::BakedAnimation;
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
/// Resolve a frame from an explicit initial random word and elapsed cycles.
pub(crate) fn frame_index(
    sequence: &SequenceAsset,
    elapsed: u64,
    random: Option<u64>,
) -> Result<Option<usize>> {
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
    Ok(Some(frame))
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
    #[test]
    fn delayed_boundaries_loopback_and_random_start_are_explicit() {
        let seq = SequenceAsset {
            id: SequenceId::new(1),
            frames: vec![1, 2, 3],
            delays: vec![2, 3, 4],
            frame_step: Some(2),
            max_loops: 1,
            restart: 2,
            skeletal: None,
            range: None,
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
}
