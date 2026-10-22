//! Immutable dependency closure loading, retaining source hashes in canonical order.
use super::{animation, hash_file};
use crate::{
    Cache, FileId, GroupId, IndexId, Source,
    error::Result,
    reader::Reader,
    world::{LegacyFrame, SequenceAsset, Skeleton, TextureAsset, VarbitDefinition},
};
use rustclient_model::{SequenceId, TextureId, VarbitId};
use rustclient_platform::ReadOnlyBlobs;
use sha2::Sha256;
use std::collections::{BTreeMap, BTreeSet};
pub(super) fn textures<S: ReadOnlyBlobs>(
    cache: &Cache<S>,
    digest: &mut Sha256,
) -> Result<(BTreeMap<TextureId, u16>, BTreeMap<TextureId, TextureAsset>)> {
    let mut colors = BTreeMap::new();
    let mut textures = BTreeMap::new();
    let mut sprite_bytes = BTreeMap::new();
    for (id, bytes) in cache.group_files(IndexId(9), GroupId(0))? {
        let source = Source {
            index: IndexId(9),
            group: Some(GroupId(0)),
            file: Some(id),
        };
        hash_file(digest, source, &bytes);
        let mut r = Reader::new(&bytes, source);
        let sprite = r.u16()?;
        let average = r.u16()?;
        let opaque = r.u8()? == 0;
        let direction = r.u8()?;
        let speed = r.u8()?;
        r.finish()?;
        if id.0 >= 256 || direction > 4 {
            return Err(source.error("texture", "unsupported layer or animation direction"));
        }
        let source = Source {
            index: IndexId(8),
            group: Some(GroupId(u32::from(sprite))),
            file: Some(FileId(0)),
        };
        if let std::collections::btree_map::Entry::Vacant(e) = sprite_bytes.entry(sprite) {
            let files = cache.group_files(IndexId(8), GroupId(u32::from(sprite)))?;
            let bytes = files
                .into_iter()
                .find(|(id, _)| id.0 == 0)
                .ok_or_else(|| source.error("texture sprite", "missing file"))?
                .1;
            hash_file(digest, source, &bytes);
            e.insert(bytes);
        }
        let frames = crate::decode_sprites(&sprite_bytes[&sprite], source)?;
        let frame = frames
            .first()
            .ok_or_else(|| source.error("texture sprite", "no frames"))?;
        if frame.canvas[0] != frame.canvas[1] || ![64, 128].contains(&frame.canvas[0]) {
            return Err(source.error("texture sprite", "expected square 64/128 canvas"));
        }
        let size = usize::from(frame.canvas[0]);
        let mut rgba = vec![0; 128 * 128 * 4];
        let pixels = frame.image.pixels();
        for y in 0..128 {
            for x in 0..128 {
                let sx = x * size / 128;
                let sy = y * size / 128;
                let ox = usize::from(frame.offset[0]);
                let oy = usize::from(frame.offset[1]);
                if sx < ox
                    || sy < oy
                    || sx - ox >= frame.image.width() as usize
                    || sy - oy >= frame.image.height() as usize
                {
                    continue;
                }
                let src = ((sy - oy) * frame.image.width() as usize + sx - ox) * 4;
                let pixel = &pixels[src..src + 4];
                if pixel[..3] != [0, 0, 0] {
                    let dest = (y * 128 + x) * 4;
                    rgba[dest..dest + 3].copy_from_slice(&pixel[..3]);
                    rgba[dest + 3] = 255;
                }
            }
        }
        let typed = TextureId::new(id.0);
        colors.insert(typed, average);
        textures.insert(
            typed,
            TextureAsset {
                image: crate::Image::new(128, 128, rgba, source)?,
                animation_direction: direction,
                animation_speed: speed,
                opaque,
            },
        );
    }
    Ok((colors, textures))
}
pub(super) type Animations = (
    BTreeMap<SequenceId, SequenceAsset>,
    BTreeMap<u32, LegacyFrame>,
    BTreeMap<u16, Skeleton>,
);
pub(super) fn animations<S: ReadOnlyBlobs>(
    cache: &Cache<S>,
    needed: &BTreeSet<SequenceId>,
    digest: &mut Sha256,
) -> Result<Animations> {
    let mut sequences = BTreeMap::new();
    let mut wanted = BTreeSet::new();
    for (id, bytes) in cache.group_files(IndexId(2), GroupId(12))? {
        let typed = SequenceId::new(id.0);
        if !needed.contains(&typed) {
            continue;
        }
        let source = Source {
            index: IndexId(2),
            group: Some(GroupId(12)),
            file: Some(id),
        };
        hash_file(digest, source, &bytes);
        let sequence = animation::sequence(&bytes, typed, source)?;
        wanted.extend(sequence.frames.iter().copied());
        sequences.insert(typed, sequence);
    }
    if needed.len() != sequences.len() {
        return Err(Source::group(IndexId(2), GroupId(12))
            .error("sequence lookup", "missing required sequence"));
    }
    let mut frames = BTreeMap::new();
    let mut skeletons = BTreeMap::new();
    let mut groups = BTreeMap::new();
    for id in wanted {
        let group = id >> 16;
        let file = id & 65535;
        let source = Source {
            index: IndexId(0),
            group: Some(GroupId(group)),
            file: Some(FileId(file)),
        };
        if let std::collections::btree_map::Entry::Vacant(e) = groups.entry(group) {
            e.insert(
                cache
                    .group_files(IndexId(0), GroupId(group))?
                    .into_iter()
                    .collect::<BTreeMap<_, _>>(),
            );
        }
        let bytes = groups[&group]
            .get(&FileId(file))
            .ok_or_else(|| source.error("frame lookup", "missing required frame"))?;
        hash_file(digest, source, bytes);
        let mut r = Reader::new(bytes, source);
        let skeleton = r.u16()?;
        if let std::collections::btree_map::Entry::Vacant(e) = skeletons.entry(skeleton) {
            let source = Source {
                index: IndexId(1),
                group: Some(GroupId(u32::from(skeleton))),
                file: Some(FileId(0)),
            };
            let files = cache.group_files(source.index, source.group.unwrap_or(GroupId(0)))?;
            let bytes = files
                .into_iter()
                .find(|(id, _)| id.0 == 0)
                .ok_or_else(|| source.error("skeleton lookup", "missing required skeleton"))?
                .1;
            hash_file(digest, source, &bytes);
            e.insert(animation::skeleton(&bytes, source)?);
        }
        frames.insert(
            id,
            animation::frame(bytes, &skeletons[&skeleton].transforms, source)?,
        );
    }
    Ok((sequences, frames, skeletons))
}
pub(super) fn varbits<S: ReadOnlyBlobs>(
    cache: &Cache<S>,
    needed: &BTreeSet<VarbitId>,
    digest: &mut Sha256,
    profile: crate::CacheProfile,
    warnings: &mut Vec<String>,
) -> Result<BTreeMap<VarbitId, VarbitDefinition>> {
    let mut out = BTreeMap::new();
    if needed.is_empty() {
        return Ok(out);
    }
    for (id, bytes) in cache.group_files_policy(
        IndexId(2),
        GroupId(14),
        profile == crate::CacheProfile::OpenRune240,
        warnings,
    )? {
        let typed = VarbitId::new(id.0);
        if needed.contains(&typed) {
            let source = Source {
                index: IndexId(2),
                group: Some(GroupId(14)),
                file: Some(id),
            };
            hash_file(digest, source, &bytes);
            out.insert(typed, animation::varbit(&bytes, source)?);
        }
    }
    if out.len() != needed.len() {
        return Err(Source::group(IndexId(2), GroupId(14))
            .error("varbit lookup", "missing required definition"));
    }
    Ok(out)
}
