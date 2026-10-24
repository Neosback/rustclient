//! Explicit OSRS/OpenRune 240 binary and archive adapter.
mod animation;
pub mod db_tables;
pub mod definitions;
mod dependencies;
pub mod entity_assets;
pub mod models;
pub mod npc_model;
pub mod npc_path_policy;
pub mod player_model;
mod script_opcodes;
pub mod script_tables;
pub mod scripts;
pub mod sequence_policy;
mod terrain;
pub mod widgets;
use crate::{
    Cache, CacheProfile, FileId, GroupId, IndexId, Source,
    error::Result,
    world::{FloorMaterial, KitDefinition, MapSquare, ObjectDefinition, StaticModel, WorldAssets},
};
use rustclient_model::{
    FloorOverlayId, FloorUnderlayId, KitId, ModelId, ObjectDefinitionId, RegionCoord, SequenceId,
    WorldTile,
};
use rustclient_platform::ReadOnlyBlobs;
use sha2::{Digest, Sha256};
use std::collections::{BTreeMap, BTreeSet};
impl CacheProfile {
    /// Revision-owned normal scene window around a server map anchor.
    /// # Errors
    /// Rejects negative or overflowing world coordinates.
    pub fn scene_window(self, anchor: WorldTile) -> Result<(WorldTile, u32, u32)> {
        let source = Source::group(IndexId(5), GroupId(0));
        let x = anchor
            .x
            .checked_sub(48)
            .filter(|x| *x >= 0)
            .ok_or_else(|| source.error("scene window", "invalid anchor x"))?;
        let y = anchor
            .y
            .checked_sub(48)
            .filter(|y| *y >= 0)
            .ok_or_else(|| source.error("scene window", "invalid anchor y"))?;
        Ok((WorldTile::new(x, y, anchor.plane), 104, 104))
    }

    /// Load immutable CPU assets for a normal bounded window.
    /// # Errors
    /// Rejects missing/corrupt assets, unsupported encodings and invalid extents.
    pub fn load_world<S: ReadOnlyBlobs>(
        self,
        cache: &Cache<S>,
        origin: WorldTile,
        width: u32,
        height: u32,
        mut progress: impl FnMut(&str),
    ) -> Result<WorldAssets> {
        let context = Source::group(IndexId(5), GroupId(0));
        if width == 0 || height == 0 || width > 192 || height > 192 || origin.x < 0 || origin.y < 0
        {
            return Err(context.error("world extent", "unsupported scene bounds"));
        }
        let end_x = origin
            .x
            .checked_add(
                i32::try_from(width)
                    .map_err(|_| context.error("world extent", "width overflow"))?,
            )
            .ok_or_else(|| context.error("world extent", "x overflow"))?;
        let end_y = origin
            .y
            .checked_add(
                i32::try_from(height)
                    .map_err(|_| context.error("world extent", "height overflow"))?,
            )
            .ok_or_else(|| context.error("world extent", "y overflow"))?;
        let mut digest = Sha256::new();
        digest.update(b"rustclient:spawn-assets:v2");
        digest.update(self.name().as_bytes());
        digest.update(cache.metadata_fingerprint().as_bytes());
        digest.update(origin.x.to_le_bytes());
        digest.update(origin.y.to_le_bytes());
        digest.update([origin.plane.get()]);
        digest.update(width.to_le_bytes());
        digest.update(height.to_le_bytes());
        let (squares, needed, auxiliary_map_files) =
            load_maps(cache, origin, (end_x, end_y), &mut digest, &mut progress)?;
        progress("floor definitions");
        let (underlays, overlays) = load_floors(cache, &mut digest)?;
        let mut warnings = Vec::new();
        progress("object definitions");
        let (objects, mut model_ids) =
            load_objects(cache, self, &needed, &mut digest, &mut warnings)?;
        progress("identity kit definitions");
        let (kits, kit_model_ids) = load_kits(cache, &mut digest);
        model_ids.extend(kit_model_ids);
        progress("location models");
        let models = load_models(cache, model_ids, &mut digest)?;
        progress("texture sprites");
        let (texture_colors, textures) = dependencies::textures(cache, &mut digest)?;
        let map_scenes = cache.named_sprites("mapscene")?;
        let map_dots = cache.named_sprites("mapdots")?;
        progress("sequence and frame dependencies");
        let mut wanted: BTreeSet<SequenceId> =
            objects.values().filter_map(|d| d.animation).collect();
        for seq in [808, 819, 820, 821, 822, 823, 824] {
            wanted.insert(SequenceId::new(seq));
        }
        let (sequences, frames, skeletons) =
            dependencies::animations(cache, &wanted, &mut digest, self, &mut warnings)?;
        let wanted = objects
            .values()
            .filter_map(|d| d.morphs.as_ref().and_then(|m| m.varbit))
            .collect();
        let varbits = dependencies::varbits(cache, &wanted, &mut digest, self, &mut warnings)?;
        Ok(WorldAssets {
            squares,
            underlays,
            overlays,
            objects,
            models,
            texture_colors,
            map_scenes,
            map_dots,
            textures,
            sequences,
            frames,
            skeletons,
            varbits,
            kits,
            metadata_fingerprint: cache.metadata_fingerprint().to_owned(),
            content_hash: digest.finalize().into(),
            auxiliary_map_files,
            size_hint_warnings: warnings,
        })
    }
}

type Kits = (BTreeMap<KitId, KitDefinition>, BTreeSet<ModelId>);

fn load_kits<S: ReadOnlyBlobs>(cache: &Cache<S>, digest: &mut Sha256) -> Kits {
    let mut kits = BTreeMap::new();
    let mut model_ids = BTreeSet::new();
    if let Ok(files) = cache.group_files(IndexId(2), GroupId(3)) {
        for (id, bytes) in files {
            let source = Source {
                index: IndexId(2),
                group: Some(GroupId(3)),
                file: Some(id),
            };
            hash_file(digest, source, &bytes);
            if let Ok(kit) = definitions::kit(&bytes, KitId::new(id.0), source) {
                for model in &kit.models {
                    model_ids.insert(*model);
                }
                kits.insert(kit.id, kit);
            }
        }
    }
    (kits, model_ids)
}

fn load_maps<S: ReadOnlyBlobs>(
    cache: &Cache<S>,
    origin: WorldTile,
    end: (i32, i32),
    digest: &mut Sha256,
    progress: &mut impl FnMut(&str),
) -> Result<(Vec<MapSquare>, BTreeSet<ObjectDefinitionId>, usize)> {
    let (end_x, end_y) = end;
    let context = Source::group(IndexId(5), GroupId(0));
    let mut auxiliary_map_files = 0;
    let mut squares = Vec::new();
    let mut needed = BTreeSet::new();
    // Include the far border, whose tile anchors supply shared height corners.
    for x in origin.x / 64..=end_x / 64 {
        for y in origin.y / 64..=end_y / 64 {
            if x > 255 || y > 255 {
                return Err(context.error("world extent", "region outside profile range"));
            }
            progress("map squares");
            let group = GroupId(
                u32::try_from(x * 256 + y)
                    .map_err(|_| context.error("map lookup", "group overflow"))?,
            );
            let source = Source::group(IndexId(5), group);
            let files = cache.group_files(IndexId(5), group)?;
            let get = |id: u32| {
                files
                    .iter()
                    .find(|(f, _)| f.0 == id)
                    .map(|(_, b)| b.as_slice())
                    .ok_or_else(|| source.error("map lookup", format!("missing file {id}")))
            };
            auxiliary_map_files += files.iter().filter(|(id, _)| id.0 > 1).count();
            for (id, bytes) in files.iter().filter(|(id, _)| id.0 <= 1) {
                hash_file(
                    digest,
                    Source {
                        file: Some(*id),
                        ..source
                    },
                    bytes,
                );
            }
            let coord = RegionCoord::new(x, y);
            let tiles = terrain::terrain(
                get(0)?,
                coord,
                Source {
                    file: Some(FileId(0)),
                    ..source
                },
            )?;
            let locations = terrain::locations(
                get(1)?,
                coord,
                Source {
                    file: Some(FileId(1)),
                    ..source
                },
            )?;
            needed.extend(
                locations
                    .iter()
                    .filter(|l| {
                        l.tile.x >= origin.x
                            && l.tile.x < end_x
                            && l.tile.y >= origin.y
                            && l.tile.y < end_y
                    })
                    .map(|l| l.object),
            );
            squares.push(MapSquare {
                coord,
                tiles,
                locations,
            });
        }
    }
    Ok((squares, needed, auxiliary_map_files))
}
type Floors = (
    BTreeMap<FloorUnderlayId, FloorMaterial>,
    BTreeMap<FloorOverlayId, FloorMaterial>,
);
fn load_floors<S: ReadOnlyBlobs>(cache: &Cache<S>, digest: &mut Sha256) -> Result<Floors> {
    let mut underlays = BTreeMap::new();
    let mut overlays = BTreeMap::new();
    for (group, overlay) in [(1, false), (4, true)] {
        for (id, bytes) in cache.group_files(IndexId(2), GroupId(group))? {
            hash_file(
                digest,
                Source {
                    index: IndexId(2),
                    group: Some(GroupId(group)),
                    file: Some(id),
                },
                &bytes,
            );
            let material = definitions::floor(
                &bytes,
                overlay,
                Source {
                    index: IndexId(2),
                    group: Some(GroupId(group)),
                    file: Some(id),
                },
            )?;
            if overlay {
                overlays.insert(FloorOverlayId::new(id.0), material);
            } else {
                underlays.insert(FloorUnderlayId::new(id.0), material);
            }
        }
    }
    Ok((underlays, overlays))
}
type Objects = (
    BTreeMap<ObjectDefinitionId, ObjectDefinition>,
    BTreeSet<ModelId>,
);
fn load_objects<S: ReadOnlyBlobs>(
    cache: &Cache<S>,
    profile: CacheProfile,
    needed: &BTreeSet<ObjectDefinitionId>,
    digest: &mut Sha256,
    warnings: &mut Vec<String>,
) -> Result<Objects> {
    let context = Source::group(IndexId(2), GroupId(6));
    let mut objects = BTreeMap::new();
    let mut model_ids = BTreeSet::new();
    let files: BTreeMap<_, _> = cache
        .group_files_policy(
            IndexId(2),
            GroupId(6),
            profile == CacheProfile::OpenRune240,
            warnings,
        )?
        .into_iter()
        .collect();
    let mut pending = needed.clone();
    while let Some(typed) = pending.pop_first() {
        if objects.contains_key(&typed) {
            continue;
        }
        if objects.len() >= 16_384 {
            return Err(context.error("object dependencies", "closure allocation limit"));
        }
        let source = Source {
            file: Some(FileId(typed.get())),
            ..context
        };
        let bytes = files
            .get(&FileId(typed.get()))
            .ok_or_else(|| source.error("object lookup", "missing required object definition"))?;
        hash_file(digest, source, bytes);
        let object = definitions::object(bytes, typed, source)?;
        if let Some(morphs) = &object.morphs {
            pending.extend(
                morphs
                    .targets
                    .iter()
                    .chain([&morphs.fallback])
                    .flatten()
                    .filter(|id| !objects.contains_key(*id))
                    .copied(),
            );
        }
        model_ids.extend(object.models.iter().map(|m| m.id));
        objects.insert(typed, object);
    }
    Ok((objects, model_ids))
}
fn load_models<S: ReadOnlyBlobs>(
    cache: &Cache<S>,
    model_ids: BTreeSet<ModelId>,
    digest: &mut Sha256,
) -> Result<BTreeMap<ModelId, StaticModel>> {
    let mut models = BTreeMap::new();
    for id in model_ids {
        let group = GroupId(id.get());
        let source = Source {
            index: IndexId(7),
            group: Some(group),
            file: Some(FileId(0)),
        };
        let files = cache.group_files(IndexId(7), group)?;
        let bytes = files
            .iter()
            .find(|(f, _)| f.0 == 0)
            .ok_or_else(|| source.error("model lookup", "missing model file"))?;
        hash_file(digest, source, &bytes.1);
        models.insert(id, models::decode(&bytes.1, source)?);
    }
    Ok(models)
}
fn hash_file(digest: &mut Sha256, source: Source, bytes: &[u8]) {
    digest.update([source.index.0]);
    digest.update(source.group.map_or(0, |id| id.0).to_le_bytes());
    digest.update(source.file.map_or(0, |id| id.0).to_le_bytes());
    digest.update((bytes.len() as u64).to_le_bytes());
    digest.update(bytes);
}

#[cfg(test)]
mod tests;

mod script_widgets;
