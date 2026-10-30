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
    /// The overrides the static scene was baked with. Present only for dynamic builds that patch
    /// the baseline (see `docs/specs/SPEC-0016`, scenery patch publication); a full build ignores it.
    pub base_locations: Option<std::sync::Arc<BTreeMap<LocationKey, LocationOverride>>>,
    /// Areas forced roofed by a plugin; presentation input, not game state.
    pub roof_areas: std::sync::Arc<[crate::RoofArea]>,
    /// Which animations are blended between frames; presentation input, not game state.
    pub interpolation: crate::AnimationInterpolation,
}
impl From<&SceneryState> for SceneryInputs {
    fn from(state: &SceneryState) -> Self {
        Self {
            animations: std::sync::Arc::default(),
            cycle: state.cycle(),
            generation: state.generation(),
            variables: state.variables().clone(),
            locations: state.locations().clone(),
            base_locations: None,
            roof_areas: std::sync::Arc::default(),
            interpolation: crate::AnimationInterpolation::default(),
        }
    }
}

impl SceneryInputs {
    /// Restrict variable dependencies to this immutable scene's morph selectors.
    /// Unrelated server timers must not invalidate a background terrain build.
    pub fn retain_scene_dependencies(&mut self, asset: &crate::SceneAsset) {
        self.variables
            .retain(|id, _| asset.scenery_varps.contains(id));
    }
    /// Check committed geometry dependencies without allocating a second state snapshot.
    /// Cycle and unrelated variables do not change geometry; absent and explicit zero values
    /// remain distinct, matching [`Self::same_geometry`] after dependency filtering.
    #[must_use]
    pub fn matches_state(&self, state: &SceneryState, asset: &crate::SceneAsset) -> bool {
        self.locations == *state.locations()
            && asset
                .scenery_varps
                .iter()
                .all(|id| self.variables.get(id) == state.variables().get(id))
    }
    /// Whether both inputs place the same scenery; roof areas retag geometry and are compared
    /// separately.
    #[must_use]
    pub fn same_geometry(&self, other: &Self) -> bool {
        self.variables == other.variables && self.locations == other.locations
    }
}
