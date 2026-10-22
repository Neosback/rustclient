//! Revision-240 sequence, legacy pose and varbit resource grammars.
use crate::{
    Source,
    error::Result,
    reader::Reader,
    world::{FrameTransform, LegacyFrame, SequenceAsset, SkeletonTransform, VarbitDefinition},
};
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
pub(super) fn skeleton(bytes: &[u8], source: Source) -> Result<Vec<SkeletonTransform>> {
    let mut r = Reader::new(bytes, source);
    let n = usize::from(r.u8()?);
    let types = r.take(n)?.to_vec();
    let lengths = r.take(n)?.to_vec();
    let mut out = Vec::with_capacity(n);
    for (kind, len) in types.into_iter().zip(lengths) {
        out.push(SkeletonTransform {
            kind,
            labels: r.take(usize::from(len))?.to_vec(),
        });
    }
    if r.remaining() > 0 && r.u16()? != 0 {
        return Err(source.error(
            "skeleton",
            "required cached-model bone extension is not yet decoded",
        ));
    }
    r.finish()?;
    Ok(out)
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
        let frame = frame(&[0, 7, 2, 0, 1, 63], &skel, source()).unwrap();
        assert_eq!(frame.skeleton, 7);
        assert_eq!(frame.transforms.len(), 2);
        assert_eq!(frame.transforms[0].slot, 0);
        assert_eq!(frame.transforms[0].values, [0; 3]);
        assert_eq!(frame.transforms[1].values, [-1, 128, 128]);
        assert!(super::frame(&[0, 7, 2, 0, 8], &skel, source()).is_err());
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
