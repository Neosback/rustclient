//! Dependency set of a scenery patch: which static baseline locations a committed override change
//! invalidates, and which neighbours must be prepared to re-emit them exactly.
//!
//! A location's emitted geometry depends on its own prepared model and on the prepared models it
//! exchanges normals and hidden faces with (`seams::merge`: footprint plus one tile, planes +/-1).
//! Merging reads pre-merge normals only, so a changed location alters its neighbours' output but
//! never a second ring. The patch therefore re-emits `emit` (changed locations and their direct
//! neighbours) and prepares `prepare` (those plus their neighbours, as inputs only).
use super::{StaticLocation, effective_locations_with, location_key};
use crate::{SceneAsset, SceneryInputs};
use rustclient_game_state::LocationOverride;
use rustclient_model::{LocationKey, Plane, WorldTile};
use std::collections::{BTreeMap, BTreeSet};

/// Historical fallback threshold, retained for compatibility and regression workloads.
/// Current zone preparation does not impose this override-count limit.
pub const PATCH_KEY_BUDGET: usize = 48;

/// Indices are positions in the *current* effective location list, except `hidden`, which indexes
/// the baseline's list (the one its static models were emitted for).
#[derive(Debug, Default, Clone)]
pub(crate) struct Patch {
    pub prepare: BTreeSet<usize>,
    pub emit: BTreeSet<usize>,
    /// Tiles of single-tile locations in `prepare`; per-tile decoration state is complete there.
    pub tiles: BTreeSet<WorldTile>,
    /// Sorted baseline location indices whose static models the patch replaces.
    pub hidden: Vec<u32>,
}

/// A footprint on one plane: tiles `x0..x1` by `y0..y1`.
#[derive(Clone, Copy)]
pub(crate) struct Rect {
    pub(crate) plane: Plane,
    pub(crate) x0: i32,
    pub(crate) y0: i32,
    pub(crate) x1: i32,
    pub(crate) y1: i32,
}
impl Rect {
    pub(crate) fn grown(self) -> Self {
        Self {
            x0: self.x0 - 1,
            y0: self.y0 - 1,
            x1: self.x1 + 1,
            y1: self.y1 + 1,
            ..self
        }
    }
    pub(crate) fn touches(self, other: Self) -> bool {
        self.plane.get().abs_diff(other.plane.get()) <= 1
            && self.x0 < other.x1
            && other.x0 < self.x1
            && self.y0 < other.y1
            && other.y0 < self.y1
    }
}
pub(crate) fn footprint(asset: &SceneAsset, loc: &StaticLocation) -> Rect {
    footprint_from(&asset.assets, loc)
}
pub(crate) fn footprint_from(
    assets: &rustclient_cache::world::WorldAssets,
    loc: &StaticLocation,
) -> Rect {
    // Only models of shapes 10 and 11 span more than their tile (`seams::footprint`).
    let [w, h] = if matches!(loc.shape, 10 | 11) {
        assets.objects.get(&loc.object).map_or([1, 1], |def| {
            if loc.orientation.get() & 1 != 0 {
                [def.size[1], def.size[0]]
            } else {
                def.size
            }
        })
    } else {
        [1, 1]
    };
    Rect {
        plane: loc.tile.plane,
        x0: loc.tile.x,
        y0: loc.tile.y,
        x1: loc.tile.x + i32::from(w.max(1)),
        y1: loc.tile.y + i32::from(h.max(1)),
    }
}
/// Indices of the locations whose footprint touches any `around` rectangle grown by one tile.
fn near(asset: &SceneAsset, list: &[StaticLocation], around: &[Rect]) -> BTreeSet<usize> {
    let grown: Vec<_> = around.iter().map(|rect| rect.grown()).collect();
    list.iter()
        .enumerate()
        .filter(|(_, loc)| {
            let rect = footprint(asset, loc);
            grown.iter().any(|around| around.touches(rect))
        })
        .map(|(index, _)| index)
        .collect()
}
type Signature = (WorldTile, u8, u32, u8);
fn signature(loc: &StaticLocation) -> Signature {
    (loc.tile, loc.shape, loc.object.get(), loc.orientation.get())
}
fn changed_keys(
    base: &BTreeMap<LocationKey, LocationOverride>,
    current: &BTreeMap<LocationKey, LocationOverride>,
) -> BTreeSet<LocationKey> {
    base.keys()
        .chain(current.keys())
        .filter(|key| base.get(key) != current.get(key))
        .copied()
        .collect()
}

/// Whether a static scene baked with `base` can show `current` as baseline plus patch: few enough
/// changed keys, and every replacement object already decoded in the asset. Identical override
/// sets are trivially supported.
pub fn patch_supported(
    asset: &SceneAsset,
    base: &BTreeMap<LocationKey, LocationOverride>,
    current: &BTreeMap<LocationKey, LocationOverride>,
) -> bool {
    let changed = changed_keys(base, current);
    changed
        .iter()
        .all(|key| match current.get(key).map(|value| value.change) {
            Some(rustclient_model::LocationChange::Replace(def)) => {
                asset.assets.objects.contains_key(&def.object)
            }
            _ => true,
        })
}

/// The patch from the baseline overrides to `inputs`.
/// `None` when nothing differs or a required definition has not arrived yet.
pub(crate) fn plan(asset: &SceneAsset, inputs: &SceneryInputs) -> Option<Patch> {
    let base = inputs.base_locations.as_deref()?;
    let changed = changed_keys(base, &inputs.locations);
    if changed.is_empty() {
        return None;
    }
    let before = effective_locations_with(asset, base);
    let after = effective_locations_with(asset, &inputs.locations);

    // Seeds: locations that exist in only one list, and every location of a changed key (its
    // class may change between static and animated without the list changing).
    let mut remaining: BTreeMap<Signature, i32> = BTreeMap::new();
    for loc in &before {
        *remaining.entry(signature(loc)).or_default() += 1;
    }
    let mut seeds = Vec::new();
    for loc in &after {
        match remaining.get_mut(&signature(loc)) {
            Some(count) if *count > 0 => *count -= 1,
            _ => seeds.push(footprint(asset, loc)),
        }
    }
    let after_signatures: BTreeSet<_> = after.iter().map(signature).collect();
    for loc in &before {
        if !after_signatures.contains(&signature(loc)) {
            seeds.push(footprint(asset, loc));
        }
    }
    for loc in before.iter().chain(&after) {
        if changed.contains(&location_key(loc)) {
            seeds.push(footprint(asset, loc));
        }
    }
    if seeds.is_empty() {
        return None;
    }

    let hidden = near(asset, &before, &seeds)
        .into_iter()
        .filter_map(|index| u32::try_from(index).ok())
        .collect();
    let emit = near(asset, &after, &seeds);
    let mut around = seeds;
    around.extend(emit.iter().map(|&index| footprint(asset, &after[index])));
    let mut prepare = near(asset, &after, &around);
    prepare.extend(&emit);
    let tiles = prepare
        .iter()
        .map(|&index| &after[index])
        .filter(|loc| matches!(loc.shape, 0..=9 | 22))
        .map(|loc| loc.tile)
        .collect();
    Some(Patch {
        prepare,
        emit,
        tiles,
        hidden,
    })
}

/// Whole owning zones to emit, plus one input ring for seam and decoration dependencies.
pub(crate) fn for_zones(
    asset: &SceneAsset,
    inputs: &SceneryInputs,
    zones: &BTreeSet<crate::SceneZone>,
) -> (Patch, Vec<StaticLocation>, Vec<crate::LocationOrder>) {
    let own = asset.location_index.owning(asset, inputs, zones);
    let around: Vec<_> = own.values().map(|loc| footprint(asset, loc)).collect();
    let mut selected = asset.location_index.neighbors(asset, inputs, &around);
    selected.extend(own);
    let (orders, list): (Vec<_>, Vec<_>) = selected.into_iter().unzip();
    let emit = list
        .iter()
        .enumerate()
        .filter(|(_, loc)| zones.contains(&crate::SceneZone::of(location_key(loc))))
        .map(|(i, _)| i)
        .collect();
    let prepare = (0..list.len()).collect();
    let tiles = list
        .iter()
        .filter(|loc| matches!(loc.shape, 0..=9 | 22))
        .map(|loc| loc.tile)
        .collect();
    (
        Patch {
            prepare,
            emit,
            tiles,
            hidden: Vec::new(),
        },
        list,
        orders,
    )
}
