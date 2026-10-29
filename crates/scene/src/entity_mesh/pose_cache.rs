//! Last model-local pose per actor; all world transforms and picking still run on every emission.
use super::{
    EntityCatalog,
    actor_animation::{Playback, PoseSample},
};
use crate::{Result, SceneAsset};
use rustclient_cache::world::StaticModel;
use rustclient_game_state::WorldEntitiesState;
use rustclient_model::{NpcDefinitionId, NpcIndex, PlayerAppearance, PlayerIndex, SceneAssetId};
use std::{collections::BTreeMap, sync::Arc};
const BUDGET: usize = 32 * 1024 * 1024;
#[derive(Debug, Default, Clone, Copy)]
pub struct PoseCacheStats {
    pub lookups: u64,
    pub hits: u64,
    pub builds: u64,
    pub retained_model_bytes: usize,
}
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub(super) struct PoseKey {
    action: PoseSample,
    movement: PoseSample,
    blend: bool,
}
impl PoseKey {
    pub fn new(
        asset: &SceneAsset,
        catalog: &EntityCatalog,
        action: Playback,
        movement: Playback,
        blend: bool,
    ) -> Result<Self> {
        let action = action.sample(asset, catalog, blend)?;
        let movement = if matches!(
            action,
            PoseSample::Cached(_, None) | PoseSample::Legacy(_, None)
        ) {
            movement.sample(asset, catalog, blend)?
        } else {
            PoseSample::Rest
        };
        Ok(Self {
            action,
            movement,
            blend,
        })
    }
}
#[derive(Debug)]
struct Cached<K> {
    identity: K,
    key: PoseKey,
    model: Arc<StaticModel>,
    action: bool,
    bytes: usize,
}
#[derive(Debug, Default)]
pub(super) struct PoseCache {
    asset: Option<SceneAssetId>,
    players: BTreeMap<PlayerIndex, Cached<PlayerAppearance>>,
    npcs: BTreeMap<NpcIndex, Cached<NpcDefinitionId>>,
    stats: PoseCacheStats,
}
impl PoseCache {
    pub fn stats(&self) -> PoseCacheStats {
        self.stats
    }
    pub fn clear(&mut self) {
        self.players.clear();
        self.npcs.clear();
        self.stats.retained_model_bytes = 0;
    }
    pub fn prepare(&mut self, asset: SceneAssetId, entities: &WorldEntitiesState) {
        if self.asset != Some(asset) {
            self.clear();
            self.asset = Some(asset);
        }
        let bytes = &mut self.stats.retained_model_bytes;
        self.players.retain(|id, cached| {
            let keep = entities.players().contains_key(id);
            if !keep {
                *bytes -= cached.bytes;
            }
            keep
        });
        self.npcs.retain(|id, cached| {
            let keep = entities.npcs().contains_key(id);
            if !keep {
                *bytes -= cached.bytes;
            }
            keep
        });
    }
    pub fn player(
        &mut self,
        id: PlayerIndex,
        appearance: &PlayerAppearance,
        key: PoseKey,
    ) -> Option<(Arc<StaticModel>, bool)> {
        self.stats.lookups += 1;
        let cached = self
            .players
            .get(&id)
            .filter(|c| &c.identity == appearance && c.key == key)?;
        self.stats.hits += 1;
        Some((Arc::clone(&cached.model), cached.action))
    }
    pub fn npc(
        &mut self,
        id: NpcIndex,
        definition: NpcDefinitionId,
        key: PoseKey,
    ) -> Option<(Arc<StaticModel>, bool)> {
        self.stats.lookups += 1;
        let cached = self
            .npcs
            .get(&id)
            .filter(|c| c.identity == definition && c.key == key)?;
        self.stats.hits += 1;
        Some((Arc::clone(&cached.model), cached.action))
    }
    pub fn insert_player(
        &mut self,
        id: PlayerIndex,
        appearance: &PlayerAppearance,
        key: PoseKey,
        model: Arc<StaticModel>,
        action: bool,
    ) {
        if self.admit(&model) {
            let bytes = model_bytes(&model);
            let old = self.players.insert(
                id,
                Cached {
                    identity: appearance.clone(),
                    key,
                    model,
                    action,
                    bytes,
                },
            );
            self.stats.retained_model_bytes += bytes;
            self.stats.retained_model_bytes -= old.map_or(0, |c| c.bytes);
        }
    }
    pub fn insert_npc(
        &mut self,
        id: NpcIndex,
        definition: NpcDefinitionId,
        key: PoseKey,
        model: Arc<StaticModel>,
        action: bool,
    ) {
        if self.admit(&model) {
            let bytes = model_bytes(&model);
            let old = self.npcs.insert(
                id,
                Cached {
                    identity: definition,
                    key,
                    model,
                    action,
                    bytes,
                },
            );
            self.stats.retained_model_bytes += bytes;
            self.stats.retained_model_bytes -= old.map_or(0, |c| c.bytes);
        }
    }
    fn admit(&mut self, model: &StaticModel) -> bool {
        self.stats.builds += 1;
        let bytes = model_bytes(model);
        if bytes > BUDGET {
            return false;
        }
        if self.stats.retained_model_bytes.saturating_add(bytes) > BUDGET {
            self.clear();
        }
        true
    }
}
fn model_bytes(model: &StaticModel) -> usize {
    model.vertices.capacity() * size_of::<rustclient_model::ModelPoint>()
        + model.faces.capacity() * size_of::<rustclient_cache::world::ModelFace>()
        + model.vertex_skins.capacity() * size_of::<Option<u8>>()
        + model.texture_triangles.capacity() * size_of::<[u32; 3]>()
        + model.texture_types.capacity()
        + model.skeletal_weights.capacity() * size_of::<Vec<(u8, u8)>>()
        + model
            .skeletal_weights
            .iter()
            .map(|w| w.capacity() * size_of::<(u8, u8)>())
            .sum::<usize>()
}
