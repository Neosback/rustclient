//! Revision-240 sequence, legacy pose and varbit resource grammars.
use crate::{
    Source,
    error::Result,
    reader::Reader,
    world::{
        BONE_CHANNELS, Bone, BoneHierarchy, CachedAnimation, Curve, Extrapolation, FrameTransform,
        Keyframe, LegacyFrame, Matrix4, SequenceAsset, Skeleton, SkeletonTransform,
        VarbitDefinition,
    },
};
use std::collections::BTreeMap;
use rustclient_model::{SequenceId, VarpId};
pub(super) fn sequence(bytes: &[u8], id: SequenceId, source: Source) -> Result<SequenceAsset> {
    let mut r = Reader::new(bytes, source);
    let mut out = SequenceAsset {
        id,
        frames: Vec::new(),
        delays: Vec::new(),
        frame_step: None,
        max_loops: 99,
        restart: 2,
        skeletal: None,
        range: None,
    };
    loop {
        match r.u8()? {
            0 => break,
            1 => {
                let n = usize::from(r.u16()?);
                out.delays = (0..n).map(|_| r.u16()).collect::<Result<_>>()?;
                let lows = (0..n).map(|_| r.u16()).collect::<Result<Vec<_>>>()?;
                out.frames.clear();
                for low in lows {
                    out.frames
                        .push(u32::from(low) | (u32::from(r.u16()?) << 16));
                }
            }
            2 => out.frame_step = Some(r.u16()?),
            3 | 17 => {
                let n = usize::from(r.u8()?);
                r.take(n)?;
            }
            4 | 19 => {}
            5 | 9 | 10 | 16 => {
                r.u8()?;
            }
            6 | 7 => {
                r.u16()?;
            }
            8 => out.max_loops = u16::from(r.u8()?),
            11 => out.restart = r.u8()?,
            12 => {
                let n = usize::from(r.u8()?);
                r.take(n * 4)?;
            }
            13 => {
                let id = r.u32()?;
                out.skeletal = (i32::try_from(id).is_ok()).then_some(id);
            }
            14 => {
                let n = usize::from(r.u16()?);
                r.take(n * 8)?;
            }
            15 => out.range = Some((r.u16()?, r.u16()?)),
            op => return Err(source.error("sequence", format!("unsupported opcode {op}"))),
        }
    }
    r.finish()?;
    Ok(out)
}
pub(super) fn skeleton(bytes: &[u8], source: Source) -> Result<Skeleton> {
    let mut r = Reader::new(bytes, source);
    let n = usize::from(r.u8()?);
    let types = r.take(n)?.to_vec();
    let lengths = r.take(n)?.to_vec();
    let mut transforms = Vec::with_capacity(n);
    for (kind, len) in types.into_iter().zip(lengths) {
        transforms.push(SkeletonTransform {
            kind,
            labels: r.take(usize::from(len))?.to_vec(),
        });
    }
    // The legacy label grammar may be followed by a bone hierarchy for cached-model animations.
    let bones = if r.remaining() > 0 {
        match usize::from(r.u16()?) {
            0 => None,
            count => Some(bone_hierarchy(&mut r, count, source)?),
        }
    } else {
        None
    };
    r.finish()?;
    Ok(Skeleton { transforms, bones })
}
/// Bytes one bone variant occupies: a 4x4 bind matrix and three trailing floats the client
/// reads and never uses.
const BONE_VARIANT_BYTES: usize = (16 + 3) * 4;
fn bone_hierarchy(r: &mut Reader<'_>, count: usize, source: Source) -> Result<BoneHierarchy> {
    let variants = r.u8()?;
    let per_bone = 2 + usize::from(variants) * BONE_VARIANT_BYTES;
    if r.remaining() < count * per_bone {
        return Err(source.error("skeleton", "bone table exceeds resource"));
    }
    let mut bones = Vec::with_capacity(count);
    for _ in 0..count {
        let parent = usize::try_from(r.i16()?).ok();
        if parent.is_some_and(|p| p >= count) {
            return Err(source.error("skeleton", "bone parent outside hierarchy"));
        }
        let mut bind = Vec::with_capacity(usize::from(variants));
        for _ in 0..variants {
            let mut matrix: Matrix4 = [0.; 16];
            for value in &mut matrix {
                *value = r.f32()?;
            }
            for _ in 0..3 {
                r.f32()?;
            }
            bind.push(matrix);
        }
        bones.push(Bone { parent, bind });
    }
    // Parent chains must terminate; the client would recurse without bound on a cycle.
    for start in 0..count {
        let mut at = Some(start);
        for _ in 0..=count {
            at = at.and_then(|i| bones[i].parent);
        }
        if at.is_some() {
            return Err(source.error("skeleton", "bone parent cycle"));
        }
    }
    Ok(BoneHierarchy { variants, bones })
}
/// Channel slots per curve target kind. Kind 1 drives bones; the rest attach to label transforms.
const KIND_SLOTS: [usize; 6] = [0, 9, 3, 6, 1, 3];
const KIND_BONE: u8 = 1;
const KIND_ALPHA: u8 = 4;
/// Slot a channel ordinal selects inside its target's curve array.
const fn channel_slot(ordinal: u8) -> Option<usize> {
    match ordinal {
        1..=9 => Some(ordinal as usize - 1),
        10..=15 => Some(ordinal as usize - 10),
        16 => Some(0),
        _ => None,
    }
}
fn extrapolation(value: u8, source: Source) -> Result<Extrapolation> {
    Ok(match value {
        0 => Extrapolation::Constant,
        1 => Extrapolation::Tangent,
        2 => Extrapolation::Repeat,
        3 => Extrapolation::Offset,
        4 => Extrapolation::Mirror,
        other => {
            return Err(source.error("cached animation", format!("unknown extrapolation {other}")));
        }
    })
}
fn curve(r: &mut Reader<'_>, source: Source) -> Result<Curve> {
    let count = usize::from(r.u16()?);
    // Interpolation-kind byte; the client reads it and never consults it.
    r.u8()?;
    let before = extrapolation(r.u8()?, source)?;
    let after = extrapolation(r.u8()?, source)?;
    let bezier = r.u8()? != 0;
    if count == 0 {
        return Err(source.error("cached animation", "curve without keyframes"));
    }
    if r.remaining() < count * 22 {
        return Err(source.error("cached animation", "keyframes exceed resource"));
    }
    let mut keys = Vec::with_capacity(count);
    for _ in 0..count {
        keys.push(Keyframe {
            time: i32::from(r.i16()?),
            value: r.f32()?,
            in_x: r.f32()?,
            in_y: r.f32()?,
            out_x: r.f32()?,
            out_y: r.f32()?,
        });
    }
    if keys.windows(2).any(|pair| pair[0].time > pair[1].time) {
        return Err(source.error("cached animation", "keyframes out of order"));
    }
    Ok(Curve {
        before,
        after,
        bezier,
        keys,
    })
}
/// Skeleton id a cached-model animation names in its header (the client checks the same bytes).
pub(super) fn cached_animation_skeleton(bytes: &[u8], source: Source) -> Result<u16> {
    let mut r = Reader::new(bytes, source);
    // Revision byte; every revision present in the cache shares this grammar.
    r.u8()?;
    r.u16()
}
/// Decode a cached-model animation against the skeleton its header names.
pub(super) fn cached_animation(
    bytes: &[u8],
    skeleton: &Skeleton,
    source: Source,
) -> Result<CachedAnimation> {
    let mut r = Reader::new(bytes, source);
    r.u8()?;
    let skeleton_id = r.u16()?;
    let hierarchy = skeleton.bones.as_ref().ok_or_else(|| {
        source.error(
            "cached animation",
            format!("skeleton {skeleton_id} has no bone hierarchy"),
        )
    })?;
    // Two 16-bit fields precede the pose variant; the client skips both.
    r.u16()?;
    r.u16()?;
    let variant = r.u8()?;
    if variant >= hierarchy.variants {
        return Err(source.error("cached animation", "pose variant outside bone variants"));
    }
    let curves = usize::from(r.u16()?);
    let mut bone_curves: Vec<[Option<Curve>; BONE_CHANNELS]> =
        vec![std::array::from_fn(|_| None); hierarchy.bones.len()];
    let mut transform_curves: BTreeMap<usize, Vec<Option<Curve>>> = BTreeMap::new();
    let mut has_alpha = false;
    for _ in 0..curves {
        let kind = r.u8()?;
        let target = r.s_smart()?;
        let ordinal = r.u8()?;
        let value = curve(&mut r, source)?;
        let slots = *KIND_SLOTS.get(usize::from(kind)).ok_or_else(|| {
            source.error("cached animation", format!("unknown curve kind {kind}"))
        })?;
        let slot = channel_slot(ordinal)
            .ok_or_else(|| source.error("cached animation", format!("unknown channel {ordinal}")))?;
        let target = usize::try_from(target)
            .map_err(|_| source.error("cached animation", "negative curve target"))?;
        if kind == KIND_BONE {
            let channels = bone_curves
                .get_mut(target)
                .ok_or_else(|| source.error("cached animation", "bone target outside hierarchy"))?;
            channels[slot] = Some(value);
        } else {
            if target >= skeleton.transforms.len() {
                return Err(source.error("cached animation", "transform target outside skeleton"));
            }
            // The first curve to reach a target fixes how many channels it holds.
            let channels = transform_curves
                .entry(target)
                .or_insert_with(|| vec![None; slots]);
            *channels.get_mut(slot).ok_or_else(|| {
                source.error("cached animation", "transform channel out of range")
            })? = Some(value);
            has_alpha |= kind == KIND_ALPHA;
        }
    }
    r.finish()?;
    Ok(CachedAnimation {
        skeleton: skeleton_id,
        variant,
        bone_curves,
        transform_curves,
        has_alpha,
    })
}
pub(super) fn frame(
    bytes: &[u8],
    skel: &[SkeletonTransform],
    source: Source,
) -> Result<LegacyFrame> {
    let mut flags = Reader::new(bytes, source);
    let skeleton = flags.u16()?;
    let n = usize::from(flags.u8()?);
    if n > skel.len() {
        return Err(source.error("frame", "slot exceeds skeleton"));
    }
    let masks = flags.take(n)?;
    let mut values = Reader::new(flags.take(flags.remaining())?, source);
    let mut transforms = Vec::new();
    let mut last = 0;
    for (slot, &mask) in masks.iter().enumerate() {
        if mask == 0 {
            continue;
        }
        if mask & !7 != 0 {
            return Err(source.error("frame", "invalid transform mask"));
        }
        if skel[slot].kind != 0
            && let Some(pivot) = (last..slot).rev().find(|&i| skel[i].kind == 0)
        {
            transforms.push(FrameTransform {
                slot: pivot,
                values: [0; 3],
            });
        }
        let default = if skel[slot].kind == 3 { 128 } else { 0 };
        let mut xyz = [default; 3];
        for (axis, v) in xyz.iter_mut().enumerate() {
            if mask & (1 << axis) != 0 {
                *v = values.s_smart()?;
            }
        }
        transforms.push(FrameTransform { slot, values: xyz });
        last = slot + 1;
    }
    values.finish()?;
    Ok(LegacyFrame {
        skeleton,
        transforms,
    })
}
pub(super) fn varbit(bytes: &[u8], source: Source) -> Result<VarbitDefinition> {
    let mut r = Reader::new(bytes, source);
    let mut out = None;
    loop {
        match r.u8()? {
            0 => break,
            1 => {
                let varp = VarpId::new(u32::from(r.u16()?));
                let low = r.u8()?;
                let high = r.u8()?;
                if low > high || high > 31 {
                    return Err(source.error("varbit", "invalid bit bounds"));
                }
                out = Some(VarbitDefinition { varp, low, high });
            }
            op => return Err(source.error("varbit", format!("unsupported opcode {op}"))),
        }
    }
    r.finish()?;
    out.ok_or_else(|| source.error("varbit", "missing definition"))
}

#[cfg(test)]
mod tests {
    use super::*;
    fn source() -> Source {
        Source::group(crate::IndexId(2), crate::GroupId(12))
    }
    #[test]
    fn legacy_sequence_and_modern_metadata_keep_exact_frame_identifiers() {
        // Hand-authored from revision-240 opcode widths, not copied cache data.
        let bytes = [
            1, 0, 2, 0, 3, 0, 5, 0, 7, 0, 9, 0, 1, 0, 2, 2, 0, 2, 8, 4, 11, 1, 0,
        ];
        let seq = sequence(&bytes, SequenceId::new(1), source()).unwrap();
        assert_eq!(seq.frames, [0x10007, 0x20009]);
        assert_eq!(seq.delays, [3, 5]);
        assert_eq!(seq.frame_step, Some(2));
        assert_eq!((seq.max_loops, seq.restart), (4, 1));
        for n in 0..bytes.len() {
            assert!(sequence(&bytes[..n], seq.id, source()).is_err());
        }
        let seq = sequence(
            &[13, 0, 1, 0, 2, 15, 0, 3, 0, 9, 0],
            SequenceId::new(2),
            source(),
        )
        .unwrap();
        assert_eq!(seq.skeletal, Some(65538));
        assert_eq!(seq.range, Some((3, 9)));
    }
    #[test]
    fn frame_inserts_omitted_pivot_and_preserves_signed_smart_scale_defaults() {
        let skel = skeleton(&[2, 0, 3, 1, 1, 0, 1], source()).unwrap();
        let frame = frame(&[0, 7, 2, 0, 1, 63], &skel.transforms, source()).unwrap();
        assert_eq!(frame.skeleton, 7);
        assert_eq!(frame.transforms.len(), 2);
        assert_eq!(frame.transforms[0].slot, 0);
        assert_eq!(frame.transforms[0].values, [0; 3]);
        assert_eq!(frame.transforms[1].values, [-1, 128, 128]);
        assert!(super::frame(&[0, 7, 2, 0, 8], &skel.transforms, source()).is_err());
        assert!(skeleton(&[0, 0, 1], source()).is_err());
    }
    #[test]
    fn morph_bit_bounds_and_unknown_opcodes_fail_with_context() {
        let bit = varbit(&[1, 0, 31, 2, 5, 0], source()).unwrap();
        assert_eq!((bit.varp.get(), bit.low, bit.high), (31, 2, 5));
        assert!(varbit(&[1, 0, 31, 5, 2, 0], source()).is_err());
        assert!(
            sequence(&[255, 0], SequenceId::new(0), source())
                .unwrap_err()
                .to_string()
                .contains("index 2 / group 12")
        );
    }
}
