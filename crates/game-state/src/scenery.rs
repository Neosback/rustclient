//! Authoritative scenery inputs; meshes and animation poses remain projections.
use crate::{
    GameState, OperationCommit, OperationError, SemanticEvent, SessionLifecycle, StateDelta,
};
use core::fmt;
use rustclient_model::{AnimationStart, ClientCycle, LocationChange, LocationKey, VarpId};
use std::collections::BTreeMap;
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum SceneryOperation {
    AdvanceCycles(u32),
    SetVariable {
        id: VarpId,
        value: i32,
    },
    ResetVariables,
    /// Reapply the server values. Currently no client scripts modify local vars,
    /// so committed and server values are always synchronized.
    SynchronizeVariables,
    ChangeLocation {
        key: LocationKey,
        change: LocationChange,
    },
    AnimateLocation {
        key: LocationKey,
        start: Option<AnimationStart>,
    },
}
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum SceneryError {
    InactiveSession,
    Overflow,
    Limit,
    InvalidLocation,
    FutureAnimation,
}
impl fmt::Display for SceneryError {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        f.write_str(match self {
            Self::InactiveSession => "scenery update requires an active session",
            Self::Overflow => "scenery counter overflow",
            Self::Limit => "scenery allocation limit",
            Self::InvalidLocation => "invalid scenery location update",
            Self::FutureAnimation => "animation starts after current client cycle",
        })
    }
}
impl std::error::Error for SceneryError {}
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct LocationOverride {
    pub change: LocationChange,
    pub animation: Option<AnimationStart>,
}
impl Default for LocationOverride {
    fn default() -> Self {
        Self {
            change: LocationChange::Inherit,
            animation: None,
        }
    }
}
#[derive(Debug, Default)]
pub struct SceneryState {
    cycle: ClientCycle,
    variables: BTreeMap<VarpId, i32>,
    locations: BTreeMap<LocationKey, LocationOverride>,
    /// Invalidation token derived from committed structural updates. Not semantic
    /// identity: fingerprints encode the values themselves, not mutation history.
    generation: u64,
}
impl SceneryState {
    #[must_use]
    pub const fn cycle(&self) -> ClientCycle {
        self.cycle
    }
    #[must_use]
    pub const fn generation(&self) -> u64 {
        self.generation
    }
    #[must_use]
    pub fn variable(&self, id: VarpId) -> i32 {
        *self.variables.get(&id).unwrap_or(&0)
    }
    #[must_use]
    pub const fn variables(&self) -> &BTreeMap<VarpId, i32> {
        &self.variables
    }
    #[must_use]
    pub const fn locations(&self) -> &BTreeMap<LocationKey, LocationOverride> {
        &self.locations
    }
}
impl GameState {
    #[must_use]
    pub const fn scenery(&self) -> &SceneryState {
        &self.scenery
    }
    pub(crate) fn apply_scenery(
        &mut self,
        op: SceneryOperation,
    ) -> Result<OperationCommit, OperationError> {
        self.change_scenery(op).map_err(OperationError::Scenery)
    }
    fn change_scenery(&mut self, op: SceneryOperation) -> Result<OperationCommit, SceneryError> {
        if self.lifecycle() != SessionLifecycle::Active {
            return Err(SceneryError::InactiveSession);
        }
        let scene = &mut self.scenery;
        let event = match op {
            SceneryOperation::AdvanceCycles(cycles) => {
                let previous = scene.cycle;
                let current = previous
                    .checked_advance(cycles)
                    .ok_or(SceneryError::Overflow)?;
                if previous == current {
                    return Ok(OperationCommit::new(None, StateDelta::default()));
                }
                scene.cycle = current;
                return Ok(OperationCommit::new(
                    Some(SemanticEvent::ClientCyclesAdvanced { previous, current }),
                    StateDelta::CLIENT_CYCLE,
                ));
            }
            SceneryOperation::SetVariable { id, value } => {
                let previous = scene.variable(id);
                if value == previous {
                    None
                } else {
                    if scene.variables.len() >= 16384 && !scene.variables.contains_key(&id) {
                        return Err(SceneryError::Limit);
                    }
                    let generation = scene
                        .generation
                        .checked_add(1)
                        .ok_or(SceneryError::Overflow)?;
                    if value == 0 {
                        scene.variables.remove(&id);
                    } else {
                        scene.variables.insert(id, value);
                    }
                    scene.generation = generation;
                    Some(SemanticEvent::VariableChanged {
                        id,
                        previous,
                        current: value,
                    })
                }
            }
            SceneryOperation::ResetVariables => {
                if scene.variables.is_empty() {
                    None
                } else {
                    let generation = scene
                        .generation
                        .checked_add(1)
                        .ok_or(SceneryError::Overflow)?;
                    scene.variables.clear();
                    scene.generation = generation;
                    Some(SemanticEvent::VariablesReset)
                }
            }
            SceneryOperation::SynchronizeVariables => None,
            SceneryOperation::ChangeLocation { key, change } => {
                validate_key(key)?;
                if let LocationChange::Replace(def) = change
                    && (def.shape > 22 || layer(def.shape) != key.layer)
                {
                    return Err(SceneryError::InvalidLocation);
                }
                let previous = scene.locations.get(&key).copied().unwrap_or_default();
                let current = LocationOverride { change, ..previous };
                change_location(scene, key, previous, current)?
            }
            SceneryOperation::AnimateLocation { key, start } => {
                validate_key(key)?;
                if start.is_some_and(|s| s.cycle > scene.cycle) {
                    return Err(SceneryError::FutureAnimation);
                }
                let previous = scene.locations.get(&key).copied().unwrap_or_default();
                let current = LocationOverride {
                    animation: start,
                    ..previous
                };
                change_location(scene, key, previous, current)?
            }
        };
        let delta = if event.is_none() {
            StateDelta::default()
        } else {
            StateDelta::SCENERY
        };
        Ok(OperationCommit::new(event, delta))
    }
}
fn change_location(
    scene: &mut SceneryState,
    key: LocationKey,
    previous: LocationOverride,
    current: LocationOverride,
) -> Result<Option<SemanticEvent>, SceneryError> {
    if previous == current {
        return Ok(None);
    }
    if scene.locations.len() >= 16384 && !scene.locations.contains_key(&key) {
        return Err(SceneryError::Limit);
    }
    let generation = scene
        .generation
        .checked_add(1)
        .ok_or(SceneryError::Overflow)?;
    if current == LocationOverride::default() {
        scene.locations.remove(&key);
    } else {
        scene.locations.insert(key, current);
    }
    scene.generation = generation;
    Ok(Some(SemanticEvent::LocationChanged {
        key,
        previous,
        current,
    }))
}
fn validate_key(key: LocationKey) -> Result<(), SceneryError> {
    if key.tile.x < 0 || key.tile.y < 0 {
        Err(SceneryError::InvalidLocation)
    } else {
        Ok(())
    }
}
fn layer(shape: u8) -> rustclient_model::LocationLayer {
    use rustclient_model::LocationLayer;
    match shape {
        0..=3 => LocationLayer::Boundary,
        4..=8 => LocationLayer::WallDecoration,
        22 => LocationLayer::FloorDecoration,
        _ => LocationLayer::GameObject,
    }
}
