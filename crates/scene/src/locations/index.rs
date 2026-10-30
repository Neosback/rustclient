//! Immutable source-location addresses and allocation-free point queries.
use super::{StaticLocation, location_key};
use crate::{SceneAsset, SceneryInputs};
use rustclient_cache::world::WorldAssets;
use rustclient_model::{LocationChange, LocationKey, LocationLayer, ObjectDefinitionId};
use std::collections::BTreeMap;

pub(crate) struct LocationIndex(BTreeMap<LocationKey, Vec<(usize, usize)>>);
impl LocationIndex {
    pub fn new(assets: &WorldAssets) -> Self {
        let mut by_key = BTreeMap::<_, Vec<_>>::new();
        for (square, source) in assets.squares.iter().enumerate() {
            for (index, location) in source.locations.iter().enumerate() {
                by_key
                    .entry(location_key(location))
                    .or_default()
                    .push((square, index));
            }
        }
        Self(by_key)
    }
    pub fn present(
        &self,
        assets: &WorldAssets,
        inputs: &SceneryInputs,
        key: LocationKey,
        object: ObjectDefinitionId,
    ) -> bool {
        let mut present = self.0.get(&key).is_some_and(|indices| {
            indices
                .iter()
                .any(|&(square, index)| assets.squares[square].locations[index].object == object)
        });
        // Fold overrides in their original order. This also preserves unusual raw inputs whose
        // replacement shape has a different layer; GameState rejects those in normal operation.
        for (changed, override_) in &inputs.locations {
            match override_.change {
                LocationChange::Inherit => {}
                LocationChange::Remove => {
                    if *changed == key {
                        present = false;
                    }
                }
                LocationChange::Replace(def) => {
                    if *changed == key {
                        present = false;
                    }
                    if changed.tile == key.tile
                        && LocationLayer::from_shape(def.shape) == key.layer
                        && def.object == object
                    {
                        present = true;
                    }
                }
            }
        }
        present
    }
}
pub(crate) fn effective_locations(
    asset: &SceneAsset,
    inputs: &SceneryInputs,
) -> Vec<StaticLocation> {
    effective_locations_with(asset, &inputs.locations)
}
/// The effective locations for one committed override map.
pub(crate) fn effective_locations_with(
    asset: &SceneAsset,
    overrides: &BTreeMap<LocationKey, rustclient_game_state::LocationOverride>,
) -> Vec<StaticLocation> {
    if overrides.is_empty() {
        return asset
            .assets
            .squares
            .iter()
            .flat_map(|square| square.locations.iter().copied())
            .collect();
    }
    if overrides.iter().any(|(key, value)| matches!(value.change, LocationChange::Replace(def) if LocationLayer::from_shape(def.shape) != key.layer)) {
        return super::legacy_locations(asset, overrides);
    }
    // One source pass rather than one full retain pass for every changed location. Original
    // source order and the ordered appended replacements remain identical to the old path.
    let mut locations: Vec<_> = asset
        .assets
        .squares
        .iter()
        .flat_map(|s| s.locations.iter().copied())
        .filter(|loc| {
            overrides
                .get(&location_key(loc))
                .is_none_or(|value| value.change == LocationChange::Inherit)
        })
        .collect();
    locations.extend(
        overrides
            .iter()
            .filter_map(|(key, value)| match value.change {
                LocationChange::Replace(def) => Some(StaticLocation {
                    object: def.object,
                    tile: key.tile,
                    shape: def.shape,
                    orientation: def.orientation,
                }),
                _ => None,
            }),
    );
    locations
}
