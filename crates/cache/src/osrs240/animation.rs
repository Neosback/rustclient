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
use rustclient_model::{SequenceId, VarpId};
use std::collections::BTreeMap;
pub(super) fn sequence(bytes: &[u8], id: SequenceId, source: Source) -> Result<SequenceAsset> {
    let mut r = Reader::new(bytes, source);
    let mut movement_gate_with_backlog = None;
    let mut movement_gate_without_backlog = None;
    let mut has_blend_mask = false;
    let mut out = SequenceAsset {
        id,
        frames: Vec::new(),
        delays: Vec::new(),
        frame_step: None,
        max_loops: 99,
        priority: 5,
        movement_gate_with_backlog: 0,
        movement_gate_without_backlog: 0,
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
                has_blend_mask = true;
                let n = usize::from(r.u8()?);
                r.take(n)?;
            }
            4 | 19 => {}
            5 => out.priority = r.u8()?,
            9 => movement_gate_with_backlog = Some(r.u8()?),
            10 => movement_gate_without_backlog = Some(r.u8()?),
            16 => {
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
    let default_movement_gate = if has_blend_mask { 2 } else { 0 };
    out.movement_gate_with_backlog = movement_gate_with_backlog.unwrap_or(default_movement_gate);
    out.movement_gate_without_backlog =
        movement_gate_without_backlog.unwrap_or(default_movement_gate);
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
    if count > 1024 || variants == 0 {
        return Err(source.error("skeleton", "invalid or excessive bone hierarchy"));
    }
    let per_bone = 2 + usize::from(variants) * BONE_VARIANT_BYTES;
    if r.remaining() < count * per_bone {
        return Err(source.error("skeleton", "bone table exceeds resource"));
    }
    let mut bones = Vec::with_capacity(count);
    for _ in 0..count {
        let encoded_parent = r.i16()?;
        if encoded_parent < -1 {
            return Err(source.error("skeleton", "invalid negative bone parent"));
        }
        let parent = usize::try_from(encoded_parent).ok();
        if parent.is_some_and(|p| p >= count) {
            return Err(source.error("skeleton", "bone parent outside hierarchy"));
        }
        let mut bind = Vec::with_capacity(usize::from(variants));
        for _ in 0..variants {
            let mut matrix: Matrix4 = [0.; 16];
            for value in &mut matrix {
                *value = r.f32()?;
                if !value.is_finite() {
                    return Err(source.error("skeleton", "non-finite bind matrix"));
                }
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
    if keys.iter().any(|key| {
        [key.value, key.in_x, key.in_y, key.out_x, key.out_y]
            .iter()
            .any(|value| !value.is_finite())
    }) {
        return Err(source.error("cached animation", "non-finite keyframe"));
    }
    if keys.windows(2).any(|pair| pair[0].time >= pair[1].time) {
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
    // Curves expand to one f32 per integer frame during projection preparation.
    let mut sample_count = 0_usize;
    for _ in 0..curves {
        let kind = r.u8()?;
        let target = r.s_smart()?;
        let ordinal = r.u8()?;
        let value = curve(&mut r, source)?;
        sample_count += usize::try_from(value.keys[value.keys.len() - 1].time - value.keys[0].time)
            .map_err(|_| source.error("cached animation", "invalid curve range"))?
            + 1;
        if sample_count > 8 * 1024 * 1024 {
            return Err(source.error("cached animation", "curve sample budget exceeded"));
        }
        let slots = *KIND_SLOTS.get(usize::from(kind)).ok_or_else(|| {
            source.error("cached animation", format!("unknown curve kind {kind}"))
        })?;
        let slot = channel_slot(ordinal).ok_or_else(|| {
            source.error("cached animation", format!("unknown channel {ordinal}"))
        })?;
        if slot >= slots {
            return Err(source.error("cached animation", "channel outside curve kind"));
        }
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
            1, 0, 2, 0, 3, 0, 5, 0, 7, 0, 9, 0, 1, 0, 2, 2, 0, 2, 5, 7, 8, 4, 9, 1,
            10, 2, 11, 1, 0,
        ];
        let seq = sequence(&bytes, SequenceId::new(1), source()).unwrap();
        assert_eq!(seq.frames, [0x10007, 0x20009]);
        assert_eq!(seq.delays, [3, 5]);
        assert_eq!(seq.frame_step, Some(2));
        assert_eq!((seq.max_loops, seq.restart), (4, 1));
        assert_eq!(seq.priority, 7);
        assert_eq!(seq.movement_gate_with_backlog, 1);
        assert_eq!(seq.movement_gate_without_backlog, 2);
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
        assert_eq!(seq.priority, 5);
        assert_eq!(seq.movement_gate_with_backlog, 0);
        assert_eq!(seq.movement_gate_without_backlog, 0);
        let blended = sequence(&[3, 1, 7, 0], SequenceId::new(3), source()).unwrap();
        assert_eq!(blended.movement_gate_with_backlog, 2);
        assert_eq!(blended.movement_gate_without_backlog, 2);
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
    fn cached_animation_family_preserves_channels_and_rejects_malformed_resources() {
        // Synthetic one-bone hierarchy and translation curve. Grammar evidence:
        // RuneLite 235 Skeleton, class136, class146, class139 and class134.
        let mut bones = vec![0, 0, 1, 1, 255, 255];
        for index in 0..19 {
            let value = if index < 16 && index % 5 == 0 {
                1_f32
            } else {
                0_f32
            };
            bones.extend_from_slice(&value.to_be_bytes());
        }
        let skel = skeleton(&bones, source()).unwrap();
        let mut bytes = vec![
            1, 0, 7, 0, 0, 0, 0, 0, 0, 1, 1, 64, 4, 0, 1, 0, 0, 0, 0, 0, 0,
        ];
        for value in [12_f32, 0., 0., 0., 0.] {
            bytes.extend_from_slice(&value.to_be_bytes());
        }
        let decoded = cached_animation(&bytes, &skel, source()).unwrap();
        assert_eq!(decoded.skeleton, 7);
        assert_eq!(
            decoded.bone_curves[0][3].as_ref().unwrap().keys[0]
                .value
                .to_bits(),
            12_f32.to_bits()
        );
        for end in 0..bytes.len() {
            assert!(cached_animation(&bytes[..end], &skel, source()).is_err());
        }
        for (index, value) in [(7, 1), (10, 0), (11, 63), (12, 255)] {
            let mut invalid = bytes.clone();
            invalid[index] = value;
            assert!(cached_animation(&invalid, &skel, source()).is_err());
        }
        let mut invalid = bytes;
        invalid[21..25].copy_from_slice(&f32::NAN.to_be_bytes());
        assert!(cached_animation(&invalid, &skel, source()).is_err());
        // Parent zero makes the only bone its own ancestor.
        bones[4..6].copy_from_slice(&0_i16.to_be_bytes());
        assert!(skeleton(&bones, source()).is_err());
        bones[4..6].copy_from_slice(&(-2_i16).to_be_bytes());
        assert!(skeleton(&bones, source()).is_err());
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