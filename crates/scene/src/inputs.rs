//! Immutable extraction input, copied from committed state for a worker.
use rustclient_game_state::{LocationOverride, SceneryState};
use rustclient_model::{ClientCycle, LocationKey, VarpId};
use std::collections::BTreeMap;
#[derive(Debug, Clone, Default)]
pub struct SceneryInputs {
    /// Catalog resources are disposable projection inputs, never semantic state.
    pub animations: std::sync::Arc<crate::AnimationAssets>,
    pub cycle: ClientCycle,
    pub generation: u64,
    pub variables: BTreeMap<VarpId, i32>,
    pub locations: BTreeMap<LocationKey, LocationOverride>,
    /// Areas forced roofed by a plugin; presentation input, not game state.
    pub roof_areas: std::sync::Arc<[crate::RoofArea]>,
}
impl From<&SceneryState> for SceneryInputs {
    fn from(state: &SceneryState) -> Self {
        Self {
            animations: std::sync::Arc::default(),
            cycle: state.cycle(),
            generation: state.generation(),
            variables: state.variables().clone(),
            locations: state.locations().clone(),
            roof_areas: std::sync::Arc::default(),
        }
    }
}

impl SceneryInputs {
    /// Restrict variable dependencies to this immutable scene's morph selectors.
    /// Unrelated server timers must not invalidate a background terrain build.
    pub fn retain_scene_dependencies(&mut self, asset: &crate::SceneAsset) {
        let mut wanted = std::collections::BTreeSet::new();
        for def in asset.sources().objects.values() {
            if let Some(morph) = &def.morphs {
                if let Some(varp) = morph.varp {
                    wanted.insert(varp);
                }
                if let Some(bit) = morph.varbit.and_then(|id| asset.sources().varbits.get(&id)) {
                    wanted.insert(bit.varp);
                }
            }
        }
        self.variables.retain(|id, _| wanted.contains(id));
    }
    /// Whether both inputs place the same scenery; roof areas retag geometry and are compared
    /// separately.
    #[must_use]
    pub fn same_geometry(&self, other: &Self) -> bool {
        self.variables == other.variables && self.locations == other.locations
    }
}
