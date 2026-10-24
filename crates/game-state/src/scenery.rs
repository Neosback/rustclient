//! Authoritative scenery inputs; meshes and animation poses remain projections.
use crate::{
    GameState, OperationCommit, OperationError, SemanticEvent, SessionLifecycle, StateDelta,
};
use core::fmt;
use rustclient_model::{AnimationStart, ClientCycle, LocationChange, LocationKey, VarpId};
use std::collections::BTreeMap;
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum SceneryOperation {
    /// Trigger at the current cycle, relative to the active map anchor and local plane.
    ResetLocationArea {
        offset: rustclient_model::TileOffset,
        plane: rustclient_model::Plane,
        size: [u32; 2],
    },
    TriggerLocationAnimation {
        plane: Option<rustclient_model::Plane>,
        offset: rustclient_model::TileOffset,
        placement: rustclient_model::LocationAnimationPlacement,
        sequence: Option<rustclient_model::SequenceId>,
    },
    AdvanceCycles(u32),
    SetVariable {
        id: VarpId,
        value: i32,
    },
    SetClientVariable {
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
    server_variables: BTreeMap<VarpId, i32>,
    locations: BTreeMap<LocationKey, LocationOverride>,
    /// Invalidation token derived from committed structural updates. Not semantic
    /// identity: fingerprints encode the values themselves, not mutation history.
    generation: u64,
}
impl SceneryState {
    pub(crate) fn clear_map_locations(&mut self) {
        self.locations.clear();
        self.generation = self.generation.wrapping_add(1);
    }
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
    pub const fn server_variables(&self) -> &BTreeMap<VarpId, i32> {
        &self.server_variables
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
        if let SceneryOperation::TriggerLocationAnimation {
            offset,
            placement,
            sequence,
            plane: requested_plane,
        } = op
        {
            return self.trigger_location_animation(offset, requested_plane, placement, sequence);
        }
        if let SceneryOperation::ResetLocationArea {
            offset,
            plane,
            size,
        } = op
        {
            return self.reset_location_area(offset, plane, size);
        }
        let scene = &mut self.scenery;
        let event = match op {
            SceneryOperation::TriggerLocationAnimation { .. }
            | SceneryOperation::ResetLocationArea { .. } => unreachable!("normalized above"),
            SceneryOperation::AdvanceCycles(cycles) => {
                return self.advance_scenery_cycles(cycles);
            }
            SceneryOperation::SetVariable { id, value } => {
                change_variable(scene, id, value, false)?
            }
            SceneryOperation::SetClientVariable { id, value } => {
                change_variable(scene, id, value, true)?
            }
            SceneryOperation::ResetVariables => {
                if scene.variables.is_empty() && scene.server_variables.is_empty() {
                    None
                } else {
                    let generation = scene
                        .generation
                        .checked_add(1)
                        .ok_or(SceneryError::Overflow)?;
                    scene.variables.clear();
                    scene.server_variables.clear();
                    scene.generation = generation;
                    Some(SemanticEvent::VariablesReset)
                }
            }
            SceneryOperation::SynchronizeVariables => {
                if scene.variables == scene.server_variables {
                    None
                } else {
                    let generation = scene
                        .generation
                        .checked_add(1)
                        .ok_or(SceneryError::Overflow)?;
                    scene.variables.clone_from(&scene.server_variables);
                    scene.generation = generation;
                    Some(SemanticEvent::VariablesSynchronized)
                }
            }
            SceneryOperation::ChangeLocation { key, change } => {
                validate_key(key)?;
                if let LocationChange::Replace(def) = change
                    && (def.shape > 22
                        || rustclient_model::LocationLayer::from_shape(def.shape) != key.layer)
                {
                    return Err(SceneryError::InvalidLocation);
                }
                let previous = scene.locations.get(&key).copied().unwrap_or_default();
                let current = LocationOverride { change, ..previous };
                change_location(scene, key, previous, current)?
            }
            SceneryOperation::AnimateLocation { key, start } => {
                validate_key(key)?;
                if start.and_then(|s| s.placement).is_some_and(|p| {
                    p.shape > 22
                        || rustclient_model::LocationLayer::from_shape(p.shape) != key.layer)
                }) {
                    return Err(SceneryError::InvalidLocation);
                }
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
    fn advance_scenery_cycles(&mut self, cycles: u32) -> Result<OperationCommit, SceneryError> {
        let previous = self.scenery.cycle;
        let current = previous
            .checked_advance(cycles)
            .ok_or(SceneryError::Overflow)?;
        if previous == current {
            return Ok(OperationCommit::new(None, StateDelta::default()));
        }
        self.scenery.cycle = current;
        let reboot_active = self.entities.reboot_cycles() != 0;
        self.entities.advance_cycles(previous, cycles);
        Ok(OperationCommit::new(
            Some(SemanticEvent::ClientCyclesAdvanced { previous, current }),
            StateDelta::client_cycle(
                reboot_active
                    || !self.entities.players().is_empty()
                    || !self.entities.npcs().is_empty(),
            ),
        ))
    }

    fn trigger_location_animation(
        &mut self,
        offset: rustclient_model::TileOffset,
        requested_plane: Option<rustclient_model::Plane>,
        placement: rustclient_model::LocationAnimationPlacement,
        sequence: Option<rustclient_model::SequenceId>,
    ) -> Result<OperationCommit, SceneryError> {
        let anchor = self
            .initial_placement()
            .ok_or(SceneryError::InvalidLocation)?
            .map_anchor;
        let plane = requested_plane.unwrap_or(
            self.entities
                .local_player()
                .ok_or(SceneryError::InvalidLocation)?
                .tile
                .plane,
        );
        let tile = rustclient_model::WorldTile::new(
            anchor
                .x
                .checked_add(offset.x)
                .ok_or(SceneryError::Overflow)?,
            anchor
                .y
                .checked_add(offset.y)
                .ok_or(SceneryError::Overflow)?,
            plane,
        );
        self.change_scenery(SceneryOperation::AnimateLocation {
            key: LocationKey::new(
                tile,
                rustclient_model::LocationLayer::from_shape(placement.shape),
            ),
            start: Some(AnimationStart {
                placement: Some(placement),
                sequence,
                cycle: self.scenery.cycle(),
                random_seed: 0,
            }),
        })
    }
    fn reset_location_area(
        &mut self,
        offset: rustclient_model::TileOffset,
        plane: rustclient_model::Plane,
        size: [u32; 2],
    ) -> Result<OperationCommit, SceneryError> {
        let anchor = self
            .initial_placement()
            .ok_or(SceneryError::InvalidLocation)?
            .map_anchor;
        if size.contains(&0) || size.iter().any(|s| *s > 192) {
            return Err(SceneryError::InvalidLocation);
        }
        let origin = rustclient_model::WorldTile::new(
            anchor
                .x
                .checked_add(offset.x)
                .ok_or(SceneryError::Overflow)?,
            anchor
                .y
                .checked_add(offset.y)
                .ok_or(SceneryError::Overflow)?,
            plane,
        );
        let inside = |key: &LocationKey| {
            key.tile.plane == plane
                && i64::from(key.tile.x) >= i64::from(origin.x)
                && i64::from(key.tile.x) < i64::from(origin.x) + i64::from(size[0])
                && i64::from(key.tile.y) >= i64::from(origin.y)
                && i64::from(key.tile.y) < i64::from(origin.y) + i64::from(size[1])
        };
        if !self.scenery.locations.keys().any(inside) {
            return Ok(OperationCommit::new(None, StateDelta::default()));
        }
        let generation = self
            .scenery
            .generation
            .checked_add(1)
            .ok_or(SceneryError::Overflow)?;
        self.scenery.locations.retain(|key, _| !inside(key));
        self.scenery.generation = generation;
        Ok(OperationCommit::new(
            Some(SemanticEvent::LocationAreaReset { origin, size }),
            StateDelta::SCENERY,
        ))
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
fn change_variable(
    scene: &mut SceneryState,
    id: VarpId,
    value: i32,
    local: bool,
) -> Result<Option<SemanticEvent>, SceneryError> {
    let previous = scene.variable(id);
    let server_previous = *scene.server_variables.get(&id).unwrap_or(&0);
    let effective_changed = previous != value;
    let server_changed = !local && server_previous != value;
    if !effective_changed && !server_changed {
        return Ok(None);
    }
    if value != 0
        && ((scene.variables.len() >= 16_384 && !scene.variables.contains_key(&id))
            || (!local
                && scene.server_variables.len() >= 16_384
                && !scene.server_variables.contains_key(&id)))
    {
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
    if !local {
        if value == 0 {
            scene.server_variables.remove(&id);
        } else {
            scene.server_variables.insert(id, value);
        }
    }
    scene.generation = generation;
    Ok(Some(if effective_changed {
        SemanticEvent::VariableChanged {
            id,
            previous,
            current: value,
        }
    } else {
        SemanticEvent::ServerVariableChanged {
            id,
            previous: server_previous,
            current: value,
        }
    }))
}
