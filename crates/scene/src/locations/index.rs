//! Immutable source-location addresses and allocation-free point queries.
use super::{StaticLocation, location_key};
use crate::{SceneAsset, SceneryInputs};
use rustclient_cache::world::WorldAssets;
use rustclient_model::{LocationChange, LocationKey, LocationLayer, ObjectDefinitionId};
use std::collections::BTreeMap;

pub(crate) struct LocationIndex {
    keys: BTreeMap<LocationKey, Vec<(usize, usize)>>,
    addresses: Vec<(usize, usize)>,
    anchors: BTreeMap<crate::SceneZone, Vec<u32>>,
    touching: BTreeMap<crate::SceneZone, Vec<u32>>,
}
impl LocationIndex {
    pub fn new(assets: &WorldAssets) -> Self {
        let mut by_key = BTreeMap::<_, Vec<_>>::new();
        let mut addresses = Vec::new();
        let mut anchors = BTreeMap::<_, Vec<_>>::new();
        let mut touching = BTreeMap::<_, Vec<_>>::new();
        for (square, source) in assets.squares.iter().enumerate() {
            for (index, location) in source.locations.iter().enumerate() {
                let ordinal = u32::try_from(addresses.len()).unwrap_or(u32::MAX);
                addresses.push((square, index));
                anchors
                    .entry(crate::SceneZone::of(location_key(location)))
                    .or_default()
                    .push(ordinal);
                let rect = super::patch::footprint_from(assets, location);
                for zone in covered(rect) {
                    touching.entry(zone).or_default().push(ordinal);
                }
                by_key
                    .entry(location_key(location))
                    .or_default()
                    .push((square, index));
            }
        }
        Self {
            keys: by_key,
            addresses,
            anchors,
            touching,
        }
    }
    pub fn present(
        &self,
        assets: &WorldAssets,
        inputs: &SceneryInputs,
        key: LocationKey,
        object: ObjectDefinitionId,
    ) -> bool {
        let mut present = self.keys.get(&key).is_some_and(|indices| {
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
impl LocationIndex {
    pub(crate) fn at_key(
        &self,
        asset: &SceneAsset,
        inputs: &SceneryInputs,
        key: LocationKey,
    ) -> Vec<StaticLocation> {
        match inputs.locations.get(&key).map(|v| v.change) {
            Some(LocationChange::Remove) => Vec::new(),
            Some(LocationChange::Replace(def)) => vec![StaticLocation {
                object: def.object,
                tile: key.tile,
                shape: def.shape,
                orientation: def.orientation,
            }],
            _ => self
                .keys
                .get(&key)
                .into_iter()
                .flatten()
                .map(|&(s, i)| asset.assets.squares[s].locations[i])
                .collect(),
        }
    }
    fn collect(
        &self,
        asset: &SceneAsset,
        inputs: &SceneryInputs,
        ids: std::collections::BTreeSet<u32>,
    ) -> BTreeMap<crate::LocationOrder, StaticLocation> {
        ids.into_iter()
            .filter_map(|id| {
                let (square, index) = self.addresses[id as usize];
                let loc = asset.assets.squares[square].locations[index];
                inputs
                    .locations
                    .get(&location_key(&loc))
                    .is_none_or(|v| v.change == LocationChange::Inherit)
                    .then_some((crate::LocationOrder::Source(id), loc))
            })
            .collect()
    }
    pub(crate) fn owning(
        &self,
        asset: &SceneAsset,
        inputs: &SceneryInputs,
        zones: &std::collections::BTreeSet<crate::SceneZone>,
    ) -> BTreeMap<crate::LocationOrder, StaticLocation> {
        let ids = zones
            .iter()
            .filter_map(|z| self.anchors.get(z))
            .flatten()
            .copied()
            .collect();
        let mut result = self.collect(asset, inputs, ids);
        for (key, value) in &inputs.locations {
            if zones.contains(&crate::SceneZone::of(*key))
                && let LocationChange::Replace(def) = value.change
            {
                result.insert(
                    crate::LocationOrder::Override(*key),
                    StaticLocation {
                        object: def.object,
                        tile: key.tile,
                        shape: def.shape,
                        orientation: def.orientation,
                    },
                );
            }
        }
        result
    }
    pub(crate) fn neighbors(
        &self,
        asset: &SceneAsset,
        inputs: &SceneryInputs,
        around: &[super::patch::Rect],
    ) -> BTreeMap<crate::LocationOrder, StaticLocation> {
        let mut cells = std::collections::BTreeSet::new();
        let grown: Vec<_> = around.iter().map(|r| r.grown()).collect();
        for rect in &grown {
            for plane in
                rect.plane.get().saturating_sub(1)..=rect.plane.get().saturating_add(1).min(3)
            {
                if let Some(plane) = rustclient_model::Plane::new(plane) {
                    cells.extend(covered(super::patch::Rect { plane, ..*rect }));
                }
            }
        }
        let ids = cells
            .iter()
            .filter_map(|z| self.touching.get(z))
            .flatten()
            .copied()
            .collect();
        let mut result = self.collect(asset, inputs, ids);
        for (key, value) in &inputs.locations {
            if let LocationChange::Replace(def) = value.change {
                let loc = StaticLocation {
                    object: def.object,
                    tile: key.tile,
                    shape: def.shape,
                    orientation: def.orientation,
                };
                if grown
                    .iter()
                    .any(|r| r.touches(super::patch::footprint(asset, &loc)))
                {
                    result.insert(crate::LocationOrder::Override(*key), loc);
                }
            }
        }
        result.retain(|_, loc| {
            grown
                .iter()
                .any(|r| r.touches(super::patch::footprint(asset, loc)))
        });
        result
    }
}
fn covered(rect: super::patch::Rect) -> impl Iterator<Item = crate::SceneZone> {
    (rect.x0.div_euclid(8)..=(rect.x1 - 1).div_euclid(8)).flat_map(move |x| {
        (rect.y0.div_euclid(8)..=(rect.y1 - 1).div_euclid(8)).map(move |y| crate::SceneZone {
            plane: rect.plane,
            x,
            y,
        })
    })
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

/// Source ordinals survive removals; appended overrides retain `BTreeMap` ordering.
pub(super) fn effective_ordered(
    asset: &SceneAsset,
    inputs: &SceneryInputs,
) -> (Vec<StaticLocation>, Vec<crate::LocationOrder>) {
    if inputs.locations.iter().any(|(key,value)| matches!(value.change, LocationChange::Replace(def) if LocationLayer::from_shape(def.shape) != key.layer)) {
        let locations = super::legacy_locations(asset, &inputs.locations);
        let orders = (0..locations.len()).map(|i|crate::LocationOrder::Source(u32::try_from(i).unwrap_or(u32::MAX))).collect();
        return (locations, orders);
    }
    let mut locations = Vec::new();
    let mut orders = Vec::new();
    for (ordinal, loc) in asset
        .assets
        .squares
        .iter()
        .flat_map(|s| &s.locations)
        .enumerate()
    {
        if inputs
            .locations
            .get(&location_key(loc))
            .is_none_or(|v| v.change == rustclient_model::LocationChange::Inherit)
        {
            locations.push(*loc);
            orders.push(crate::LocationOrder::Source(
                u32::try_from(ordinal).unwrap_or(u32::MAX),
            ));
        }
    }
    for (key, value) in &inputs.locations {
        if let rustclient_model::LocationChange::Replace(def) = value.change {
            locations.push(StaticLocation {
                object: def.object,
                tile: key.tile,
                shape: def.shape,
                orientation: def.orientation,
            });
            orders.push(crate::LocationOrder::Override(*key));
        }
    }
    (locations, orders)
}
