//! Immutable animation dependencies shared by actor and scenery projections.
use crate::{SceneAsset, SceneError, animation::BakedAnimation};
use rustclient_cache::{
    osrs240::entity_assets::EntityAssets,
    world::{LegacyFrame, SequenceAsset, Skeleton},
};
use rustclient_model::SequenceId;
use std::{collections::BTreeMap, sync::Arc};

/// Cheap snapshots share decoded resources; catalog growth copies only map entries.
#[derive(Debug, Clone, Default)]
pub struct AnimationAssets {
    sequences: BTreeMap<SequenceId, Arc<SequenceAsset>>,
    frames: BTreeMap<u32, Arc<LegacyFrame>>,
    skeletons: BTreeMap<u16, Arc<Skeleton>>,
    cached: BTreeMap<u32, Arc<BakedAnimation>>,
}
impl AnimationAssets {
    pub(crate) fn merge(&mut self, asset: &SceneAsset, assets: &mut EntityAssets) -> Vec<String> {
        self.sequences.extend(
            std::mem::take(&mut assets.sequences)
                .into_iter()
                .map(|(id, value)| (id, Arc::new(value))),
        );
        self.frames.extend(
            std::mem::take(&mut assets.frames)
                .into_iter()
                .map(|(id, value)| (id, Arc::new(value))),
        );
        self.skeletons.extend(
            std::mem::take(&mut assets.skeletons)
                .into_iter()
                .map(|(id, value)| (id, Arc::new(value))),
        );
        let mut failures = Vec::new();
        for (id, animation) in std::mem::take(&mut assets.cached_animations) {
            match self
                .skeleton(asset, animation.skeleton)
                .ok_or_else(|| SceneError(format!("skeleton {} missing", animation.skeleton)))
                .and_then(|s| BakedAnimation::new(&animation, s))
            {
                Ok(baked) => {
                    self.cached.insert(id, Arc::new(baked));
                }
                Err(error) => failures.push(format!("cached animation {id}: {error}")),
            }
        }
        failures
    }
    pub(crate) fn sequence<'a>(
        &'a self,
        asset: &'a SceneAsset,
        id: SequenceId,
    ) -> Option<&'a SequenceAsset> {
        self.sequences
            .get(&id)
            .map(AsRef::as_ref)
            .or_else(|| asset.sources().sequences.get(&id))
    }
    pub(crate) fn frame<'a>(&'a self, asset: &'a SceneAsset, id: u32) -> Option<&'a LegacyFrame> {
        self.frames
            .get(&id)
            .map(AsRef::as_ref)
            .or_else(|| asset.sources().frames.get(&id))
    }
    pub(crate) fn skeleton<'a>(&'a self, asset: &'a SceneAsset, id: u16) -> Option<&'a Skeleton> {
        self.skeletons
            .get(&id)
            .map(AsRef::as_ref)
            .or_else(|| asset.sources().skeletons.get(&id))
    }
    pub(crate) fn cached(&self, id: u32) -> Option<&BakedAnimation> {
        self.cached.get(&id).map(AsRef::as_ref)
    }
}
