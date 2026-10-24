//! On-demand revision-240 NPC, worn-item and animation dependencies.
//!
//! The spawn window knows which locations exist before login completes, but the
//! set of NPCs and worn items is only known once the server sends entity
//! updates. Those definitions, their models and their animations are therefore
//! decoded in grow-only batches. A single bad definition is recorded as a
//! failure for that batch instead of aborting unrelated entities.
use super::animation;
use crate::{
    Cache, CacheProfile, FileId, GroupId, IndexId, Source,
    world::{
        CachedAnimation, ItemDefinition, LegacyFrame, NpcDefinition, SequenceAsset, Skeleton,
        StaticModel, VarbitDefinition, WorldAssets,
    },
};
use rustclient_model::{ItemDefinitionId, ModelId, NpcDefinitionId, SequenceId, VarbitId};
use rustclient_platform::ReadOnlyBlobs;
use std::collections::{BTreeMap, BTreeSet};

/// Hard cap on definitions accepted in one batch.
const MAX_BATCH: usize = 4096;

/// Definitions a caller needs; dependent models and frames are resolved by the loader.
#[derive(Debug, Default, Clone, PartialEq, Eq)]
pub struct EntityRequest {
    pub npcs: BTreeSet<NpcDefinitionId>,
    pub items: BTreeSet<ItemDefinitionId>,
    pub sequences: BTreeSet<SequenceId>,
}

impl EntityRequest {
    #[must_use]
    pub fn is_empty(&self) -> bool {
        self.npcs.is_empty() && self.items.is_empty() && self.sequences.is_empty()
    }
}

/// Newly decoded entity dependencies. Merging is additive.
#[derive(Debug, Default)]
pub struct EntityAssets {
    pub npcs: BTreeMap<NpcDefinitionId, NpcDefinition>,
    pub items: BTreeMap<ItemDefinitionId, ItemDefinition>,
    pub models: BTreeMap<ModelId, StaticModel>,
    pub sequences: BTreeMap<SequenceId, SequenceAsset>,
    pub frames: BTreeMap<u32, LegacyFrame>,
    pub skeletons: BTreeMap<u16, Skeleton>,
    /// Cached-model (skeletal) animations, keyed by the id sequences name in opcode 13.
    pub cached_animations: BTreeMap<u32, CachedAnimation>,
    /// Varbit layouts that NPC transformations select by.
    pub varbits: BTreeMap<VarbitId, VarbitDefinition>,
    /// Per-asset decode problems with source context.
    pub failures: Vec<String>,
}

/// Remembers what earlier batches already produced so each asset decodes once.
#[derive(Debug, Default)]
pub struct EntityLoader {
    profile: Option<CacheProfile>,
    models: BTreeSet<ModelId>,
    sequences: BTreeSet<SequenceId>,
    frames: BTreeSet<u32>,
    skeletons: BTreeSet<u16>,
    cached_animations: BTreeSet<u32>,
    varbits: BTreeSet<VarbitId>,
    npcs: BTreeSet<NpcDefinitionId>,
    items: BTreeSet<ItemDefinitionId>,
}

impl EntityLoader {
    /// Start from assets already present in the immutable spawn window.
    #[must_use]
    pub fn new(profile: CacheProfile, world: &WorldAssets) -> Self {
        Self {
            profile: Some(profile),
            models: world.models.keys().copied().collect(),
            sequences: world
                .sequences
                .values()
                .filter(|s| s.skeletal.is_none())
                .map(|s| s.id)
                .collect(),
            frames: world.frames.keys().copied().collect(),
            skeletons: world.skeletons.keys().copied().collect(),
            varbits: world.varbits.keys().copied().collect(),
            ..Self::default()
        }
    }

    /// Decode everything `request` needs that earlier batches did not supply.
    pub fn load<S: ReadOnlyBlobs>(
        &mut self,
        cache: &Cache<S>,
        request: &EntityRequest,
    ) -> EntityAssets {
        let mut out = EntityAssets::default();
        let mut sequences: BTreeSet<SequenceId> = request.sequences.clone();
        let mut models: BTreeSet<ModelId> = BTreeSet::new();
        let mut varbits: BTreeSet<VarbitId> = BTreeSet::new();

        self.load_npcs(
            cache,
            request,
            &mut out,
            &mut sequences,
            &mut models,
            &mut varbits,
        );
        self.load_varbits(cache, &varbits, &mut out);
        self.load_items(cache, request, &mut out, &mut models);
        self.load_sequences(cache, &sequences, &mut out);
        self.load_models(cache, &models, &mut out);
        out
    }

    /// Group read honouring the profile's explicit stale-size-hint policy.
    fn group<S: ReadOnlyBlobs>(
        &self,
        cache: &Cache<S>,
        index: IndexId,
        group: GroupId,
    ) -> crate::error::Result<Vec<(FileId, Vec<u8>)>> {
        let accept_stale = self.profile == Some(CacheProfile::OpenRune240);
        cache.group_files_policy(index, group, accept_stale, &mut Vec::new())
    }

    fn load_npcs<S: ReadOnlyBlobs>(
        &mut self,
        cache: &Cache<S>,
        request: &EntityRequest,
        out: &mut EntityAssets,
        sequences: &mut BTreeSet<SequenceId>,
        models: &mut BTreeSet<ModelId>,
        varbits: &mut BTreeSet<VarbitId>,
    ) {
        let mut pending: BTreeSet<_> = request
            .npcs
            .iter()
            .filter(|id| !self.npcs.contains(id))
            .take(MAX_BATCH)
            .copied()
            .collect();
        if pending.is_empty() {
            return;
        }
        let group = GroupId(9);
        let files: BTreeMap<FileId, Vec<u8>> = match self.group(cache, IndexId(2), group) {
            Ok(files) => files.into_iter().collect(),
            Err(error) => {
                out.failures.push(format!("npc group: {error}"));
                return;
            }
        };
        // Transformation targets join the queue; each id decodes at most once per loader.
        while let Some(id) = pending.pop_first() {
            if !self.npcs.insert(id) {
                continue;
            }
            let file = FileId(id.get());
            let Some(bytes) = files.get(&file) else {
                continue;
            };
            let source = Source {
                index: IndexId(2),
                group: Some(group),
                file: Some(file),
            };
            match super::definitions::npc(bytes, id, source) {
                Ok(def) => {
                    models.extend(def.models.iter().copied());
                    sequences.extend(
                        [
                            def.idle_sequence,
                            def.walk_sequence,
                            def.turn_left_sequence,
                            def.turn_right_sequence,
                            def.walk_back_sequence,
                            def.walk_left_sequence,
                            def.walk_right_sequence,
                        ]
                        .into_iter()
                        .flatten(),
                    );
                    sequences.extend(def.run_sequences.into_iter().flatten());
                    sequences.extend(def.crawl_sequences.into_iter().flatten());
                    if let Some(morphs) = &def.morphs {
                        varbits.extend(morphs.varbit);
                        pending.extend(
                            morphs
                                .transforms
                                .iter()
                                .flatten()
                                .filter(|target| !self.npcs.contains(target))
                                .copied(),
                        );
                    }
                    out.npcs.insert(id, def);
                }
                Err(error) => out.failures.push(error.to_string()),
            }
        }
    }

    fn load_varbits<S: ReadOnlyBlobs>(
        &mut self,
        cache: &Cache<S>,
        wanted: &BTreeSet<VarbitId>,
        out: &mut EntityAssets,
    ) {
        let pending: BTreeSet<_> = wanted
            .iter()
            .filter(|id| !self.varbits.contains(id))
            .copied()
            .collect();
        if pending.is_empty() {
            return;
        }
        let group = GroupId(14);
        let files = match self.group(cache, IndexId(2), group) {
            Ok(files) => files,
            Err(error) => {
                out.failures.push(format!("varbit group: {error}"));
                return;
            }
        };
        for (file, bytes) in files {
            let id = VarbitId::new(file.0);
            if !pending.contains(&id) {
                continue;
            }
            let source = Source {
                index: IndexId(2),
                group: Some(group),
                file: Some(file),
            };
            match animation::varbit(&bytes, source) {
                Ok(def) => {
                    out.varbits.insert(id, def);
                }
                Err(error) => out.failures.push(error.to_string()),
            }
        }
        self.varbits.extend(pending);
    }

    fn load_items<S: ReadOnlyBlobs>(
        &mut self,
        cache: &Cache<S>,
        request: &EntityRequest,
        out: &mut EntityAssets,
        models: &mut BTreeSet<ModelId>,
    ) {
        let wanted: BTreeSet<_> = request
            .items
            .iter()
            .filter(|id| !self.items.contains(id))
            .take(MAX_BATCH)
            .copied()
            .collect();
        if wanted.is_empty() {
            return;
        }
        let group = GroupId(10);
        let files = match self.group(cache, IndexId(2), group) {
            Ok(files) => files,
            Err(error) => {
                out.failures.push(format!("item group: {error}"));
                return;
            }
        };
        for (file, bytes) in files {
            let id = ItemDefinitionId::new(file.0);
            if !wanted.contains(&id) {
                continue;
            }
            let source = Source {
                index: IndexId(2),
                group: Some(group),
                file: Some(file),
            };
            match super::definitions::item(&bytes, id, source) {
                Ok(def) => {
                    for wear in [&def.male, &def.female] {
                        models.extend(wear.models.iter().flatten().copied());
                    }
                    self.items.insert(id);
                    out.items.insert(id, def);
                }
                Err(error) => out.failures.push(error.to_string()),
            }
        }
        for id in wanted {
            if !out.items.contains_key(&id) {
                self.items.insert(id);
            }
        }
    }

    fn load_sequences<S: ReadOnlyBlobs>(
        &mut self,
        cache: &Cache<S>,
        wanted: &BTreeSet<SequenceId>,
        out: &mut EntityAssets,
    ) {
        let wanted: BTreeSet<_> = wanted
            .iter()
            .filter(|id| !self.sequences.contains(id))
            .take(MAX_BATCH)
            .copied()
            .collect();
        if wanted.is_empty() {
            return;
        }
        let group = GroupId(12);
        let files = match self.group(cache, IndexId(2), group) {
            Ok(files) => files,
            Err(error) => {
                out.failures.push(format!("sequence group: {error}"));
                return;
            }
        };
        let mut frame_ids = BTreeSet::new();
        let mut cached_ids = BTreeSet::new();
        for (file, bytes) in files {
            let id = SequenceId::new(file.0);
            if !wanted.contains(&id) {
                continue;
            }
            let source = Source {
                index: IndexId(2),
                group: Some(group),
                file: Some(file),
            };
            match animation::sequence(&bytes, id, source) {
                Ok(sequence) => {
                    cached_ids.extend(sequence.skeletal);
                    frame_ids.extend(sequence.frames.iter().copied());
                    out.sequences.insert(id, sequence);
                }
                Err(error) => out.failures.push(error.to_string()),
            }
        }
        self.sequences.extend(wanted);
        self.load_frames(cache, &frame_ids, out);
        self.load_cached_animations(cache, &cached_ids, out);
    }

    /// Cached-model animations live in index 22: group is the id's high half, file its low half.
    fn load_cached_animations<S: ReadOnlyBlobs>(
        &mut self,
        cache: &Cache<S>,
        wanted: &BTreeSet<u32>,
        out: &mut EntityAssets,
    ) {
        let mut groups: BTreeMap<u32, BTreeMap<FileId, Vec<u8>>> = BTreeMap::new();
        let mut local_skeletons: BTreeMap<u16, Skeleton> = BTreeMap::new();
        let pending: Vec<u32> = wanted
            .iter()
            .copied()
            .filter(|id| !self.cached_animations.contains(id))
            .collect();
        for id in pending {
            self.cached_animations.insert(id);
            let group = id >> 16;
            let file = FileId(id & 65535);
            let source = Source {
                index: IndexId(22),
                group: Some(GroupId(group)),
                file: Some(file),
            };
            if let std::collections::btree_map::Entry::Vacant(slot) = groups.entry(group) {
                match self.group(cache, IndexId(22), GroupId(group)) {
                    Ok(files) => {
                        slot.insert(files.into_iter().collect());
                    }
                    Err(error) => {
                        out.failures.push(error.to_string());
                        continue;
                    }
                }
            }
            let Some(bytes) = groups.get(&group).and_then(|g| g.get(&file)) else {
                out.failures.push(
                    source
                        .error("cached animation lookup", "missing animation")
                        .to_string(),
                );
                continue;
            };
            let skeleton_id = match animation::cached_animation_skeleton(bytes, source) {
                Ok(skeleton_id) => skeleton_id,
                Err(error) => {
                    out.failures.push(error.to_string());
                    continue;
                }
            };
            let skeleton =
                match self.resolve_skeleton(cache, skeleton_id, &mut local_skeletons, out) {
                    Ok(skeleton) => skeleton,
                    Err(message) => {
                        out.failures.push(message);
                        continue;
                    }
                };
            match animation::cached_animation(bytes, skeleton, source) {
                Ok(animation) => {
                    out.cached_animations.insert(id, animation);
                }
                Err(error) => out.failures.push(error.to_string()),
            }
        }
    }

    /// Load a skeleton once per batch, delivering it to the catalog only the first time.
    fn resolve_skeleton<'a, S: ReadOnlyBlobs>(
        &mut self,
        cache: &Cache<S>,
        id: u16,
        local: &'a mut BTreeMap<u16, Skeleton>,
        out: &mut EntityAssets,
    ) -> Result<&'a Skeleton, String> {
        if let std::collections::btree_map::Entry::Vacant(slot) = local.entry(id) {
            let skeleton = Self::skeleton(cache, id)?;
            if self.skeletons.insert(id) {
                out.skeletons.insert(id, skeleton.clone());
            }
            slot.insert(skeleton);
        }
        Ok(&local[&id])
    }

    fn load_frames<S: ReadOnlyBlobs>(
        &mut self,
        cache: &Cache<S>,
        frame_ids: &BTreeSet<u32>,
        out: &mut EntityAssets,
    ) {
        let mut groups: BTreeMap<u32, BTreeMap<FileId, Vec<u8>>> = BTreeMap::new();
        let mut local_skeletons: BTreeMap<u16, Skeleton> = BTreeMap::new();
        let pending: Vec<u32> = frame_ids
            .iter()
            .copied()
            .filter(|id| !self.frames.contains(id))
            .collect();
        for id in pending {
            self.frames.insert(id);
            let group = id >> 16;
            let file = FileId(id & 65535);
            let source = Source {
                index: IndexId(0),
                group: Some(GroupId(group)),
                file: Some(file),
            };
            if let std::collections::btree_map::Entry::Vacant(slot) = groups.entry(group) {
                match self.group(cache, IndexId(0), GroupId(group)) {
                    Ok(files) => {
                        slot.insert(files.into_iter().collect());
                    }
                    Err(error) => {
                        out.failures.push(error.to_string());
                        continue;
                    }
                }
            }
            let Some(bytes) = groups.get(&group).and_then(|g| g.get(&file)) else {
                out.failures
                    .push(source.error("frame lookup", "missing frame").to_string());
                continue;
            };
            let Some(skeleton_id) = bytes.get(..2).map(|b| u16::from_be_bytes([b[0], b[1]])) else {
                out.failures
                    .push(source.error("frame", "truncated header").to_string());
                continue;
            };
            let skeleton =
                match self.resolve_skeleton(cache, skeleton_id, &mut local_skeletons, out) {
                    Ok(skeleton) => skeleton,
                    Err(message) => {
                        out.failures.push(message);
                        continue;
                    }
                };
            match animation::frame(bytes, &skeleton.transforms, source) {
                Ok(frame) => {
                    out.frames.insert(id, frame);
                }
                Err(error) => out.failures.push(error.to_string()),
            }
        }
    }

    fn skeleton<S: ReadOnlyBlobs>(cache: &Cache<S>, id: u16) -> Result<Skeleton, String> {
        let group = GroupId(u32::from(id));
        let source = Source {
            index: IndexId(1),
            group: Some(group),
            file: Some(FileId(0)),
        };
        let files = cache
            .group_files(IndexId(1), group)
            .map_err(|e| e.to_string())?;
        let bytes = files
            .into_iter()
            .find(|(file, _)| file.0 == 0)
            .ok_or_else(|| {
                source
                    .error("skeleton lookup", "missing skeleton")
                    .to_string()
            })?
            .1;
        animation::skeleton(&bytes, source).map_err(|e| e.to_string())
    }

    fn load_models<S: ReadOnlyBlobs>(
        &mut self,
        cache: &Cache<S>,
        wanted: &BTreeSet<ModelId>,
        out: &mut EntityAssets,
    ) {
        let pending: Vec<ModelId> = wanted
            .iter()
            .copied()
            .filter(|id| !self.models.contains(id))
            .collect();
        for id in pending {
            self.models.insert(id);
            let group = GroupId(id.get());
            let source = Source {
                index: IndexId(7),
                group: Some(group),
                file: Some(FileId(0)),
            };
            let files = match self.group(cache, IndexId(7), group) {
                Ok(files) => files,
                Err(error) => {
                    out.failures.push(error.to_string());
                    continue;
                }
            };
            let Some((_, bytes)) = files.into_iter().find(|(file, _)| file.0 == 0) else {
                out.failures.push(
                    source
                        .error("model lookup", "missing model file")
                        .to_string(),
                );
                continue;
            };
            match super::models::decode(&bytes, source) {
                Ok(model) => {
                    out.models.insert(id, model);
                }
                Err(error) => out.failures.push(error.to_string()),
            }
        }
    }
}
