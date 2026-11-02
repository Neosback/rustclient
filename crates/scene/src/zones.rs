//! Stable ownership and dependency closure for disposable location resources.
use crate::{SceneAsset, SceneGeometry, SceneryInputs, locations};
use rustclient_model::{LocationKey, Plane};
use std::{collections::BTreeSet, sync::Arc};

/// World-space owning zone. A crossing model belongs to its anchor exactly once.
#[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord)]
pub struct SceneZone {
    pub plane: Plane,
    pub x: i32,
    pub y: i32,
}
impl SceneZone {
    #[must_use]
    pub fn of(key: LocationKey) -> Self {
        Self {
            plane: key.tile.plane,
            x: key.tile.x.div_euclid(8),
            y: key.tile.y.div_euclid(8),
        }
    }
}
/// Stable source ordering, independent of zone allocation and removed-list indices.
#[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord)]
pub enum LocationOrder {
    Source(u32),
    Override(LocationKey),
}
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct LocationModel {
    pub key: LocationKey,
    pub order: LocationOrder,
}
/// Immutable zone geometry shared by worker, publication and picking.
#[derive(Clone)]
pub struct LocationZone {
    pub zone: SceneZone,
    pub geometry: Arc<SceneGeometry>,
    /// Identity survives queued publication and cannot alias a still-resident resource.
    pub revision: Arc<()>,
}

/// Dirty owning zones, including direct output dependencies of old and new footprints.
/// A second ring is prepared as input by `build_location_zones`, never re-emitted.
#[must_use]
pub fn changed_location_zones(
    asset: &SceneAsset,
    before: &SceneryInputs,
    after: &SceneryInputs,
) -> BTreeSet<SceneZone> {
    if before.same_geometry(after) {
        return BTreeSet::new();
    }
    let mut zones = BTreeSet::new();
    if before.variables != after.variables || before.roof_areas != after.roof_areas {
        for loc in locations::effective_locations(asset, before)
            .into_iter()
            .chain(locations::effective_locations(asset, after))
        {
            zones.insert(SceneZone::of(locations::location_key(&loc)));
        }
        return zones;
    }
    let changed: BTreeSet<_> = before
        .locations
        .keys()
        .chain(after.locations.keys())
        .filter(|key| before.locations.get(key) != after.locations.get(key))
        .copied()
        .collect();
    let mut around = Vec::new();
    for key in changed {
        for inputs in [before, after] {
            around.extend(
                asset
                    .location_index
                    .at_key(asset, inputs, key)
                    .iter()
                    .map(|loc| locations::patch::footprint(asset, loc)),
            );
        }
    }
    for inputs in [before, after] {
        for loc in asset
            .location_index
            .neighbors(asset, inputs, &around)
            .values()
        {
            if asset.descriptor().contains(loc.tile) {
                zones.insert(SceneZone::of(locations::location_key(loc)));
            }
        }
    }
    zones
}

/// Prepare only the requested static owning zones plus their normal/decoration inputs.
/// Empty output explicitly clears a removed zone. Posed models are a separate stream.
/// # Errors
/// Returns source asset/model/dependency failures without changing any published resource.
pub fn build_location_zones(
    asset: &SceneAsset,
    inputs: &SceneryInputs,
    zones: &BTreeSet<SceneZone>,
) -> crate::Result<Vec<LocationZone>> {
    if zones.is_empty() {
        return Ok(Vec::new());
    }
    let (patch, selected, orders) = locations::patch::for_zones(asset, inputs, zones);
    let mut mesh = crate::mesh::empty_mesh(asset, [0.; 3], asset.roofs_with(&inputs.roof_areas));
    locations::build_selected(
        asset,
        &mut mesh,
        inputs,
        locations::Scope::Patch(&patch),
        &selected,
        &orders,
    )?;
    let (_, mut split) = partition_locations(&mesh.geometry)?;
    Ok(zones
        .iter()
        .map(|&zone| LocationZone {
            zone,
            geometry: Arc::new(split.remove(&zone).unwrap_or_default()),
            revision: Arc::new(()),
        })
        .collect())
}

/// Partition a full static projection without changing model/face emission order.
/// Terrain stays separate and never participates in live location uploads.
/// # Errors
/// Rejects invalid model spans and indices outside the supported resource range.
pub fn partition_locations(
    source: &SceneGeometry,
) -> crate::Result<(
    SceneGeometry,
    std::collections::BTreeMap<SceneZone, SceneGeometry>,
)> {
    let (terrain, zones) = location_partitions(source)?;
    Ok((terrain, zones.collect::<crate::Result<_>>()?))
}

/// Stream one owning zone at a time so CPU packing does not retain a second expanded scene.
/// # Errors
/// Rejects invalid ownership/model spans before exposing the partition iterator.
pub fn location_partitions(
    source: &SceneGeometry,
) -> crate::Result<(SceneGeometry, LocationPartitions<'_>)> {
    if source.model_locations.len() != source.models.len()
        || source
            .models
            .iter()
            .any(|m| m.vertices.start > m.vertices.end || m.vertices.end > source.vertices.len())
    {
        return Err(crate::SceneError("invalid location model partition".into()));
    }
    let mut terrain = SceneGeometry::default();
    let first_model = source
        .models
        .first()
        .map_or(source.vertices.len(), |m| m.vertices.start);
    terrain
        .vertices
        .extend_from_slice(&source.vertices[..first_model]);
    terrain.opaque.extend(
        source
            .opaque
            .iter()
            .copied()
            .take_while(|&i| (i as usize) < first_model),
    );
    terrain.transparent.extend(
        source
            .transparent
            .iter()
            .copied()
            .take_while(|&i| (i as usize) < first_model),
    );
    let mut groups: std::collections::BTreeMap<SceneZone, Vec<usize>> =
        std::collections::BTreeMap::new();
    for (index, location) in source.model_locations.iter().enumerate() {
        groups
            .entry(SceneZone::of(location.key))
            .or_default()
            .push(index);
    }
    Ok((
        terrain,
        LocationPartitions {
            source,
            groups: groups.into_iter(),
        },
    ))
}

/// Disposable partition traversal over immutable scene geometry; no semantic state is copied.
pub struct LocationPartitions<'a> {
    source: &'a SceneGeometry,
    groups: std::collections::btree_map::IntoIter<SceneZone, Vec<usize>>,
}
impl Iterator for LocationPartitions<'_> {
    type Item = crate::Result<(SceneZone, SceneGeometry)>;
    fn next(&mut self) -> Option<Self::Item> {
        let (zone, models) = self.groups.next()?;
        let mut geometry = SceneGeometry::default();
        for index in models {
            if let Err(error) = append_model(self.source, index, &mut geometry) {
                return Some(Err(error));
            }
        }
        Some(Ok((zone, geometry)))
    }
}

fn append_model(
    source: &SceneGeometry,
    index: usize,
    dest: &mut SceneGeometry,
) -> crate::Result<()> {
    let model = &source.models[index];
    let start = dest.vertices.len();
    dest.vertices
        .extend_from_slice(&source.vertices[model.vertices.clone()]);
    for (indices, output) in [
        (&source.opaque, &mut dest.opaque),
        (&source.transparent, &mut dest.transparent),
    ] {
        let first = indices.partition_point(|&i| (i as usize) < model.vertices.start);
        let end = indices.partition_point(|&i| (i as usize) < model.vertices.end);
        for &i in &indices[first..end] {
            output.push(
                u32::try_from(i as usize - model.vertices.start + start)
                    .map_err(|_| crate::SceneError("zone vertex index exceeds u32".into()))?,
            );
        }
    }
    let mut copy = model.clone();
    copy.vertices = start..dest.vertices.len();
    dest.models.push(copy);
    dest.model_locations.push(source.model_locations[index]);
    if let Some(&source_id) = source.model_sources.get(index) {
        dest.model_sources.push(source_id);
    }
    for pick in source.picks.iter().filter(|p| {
        p.vertices.start >= model.vertices.start && p.vertices.end <= model.vertices.end
    }) {
        let mut copy = pick.clone();
        copy.vertices = (pick.vertices.start - model.vertices.start + start)
            ..(pick.vertices.end - model.vertices.start + start);
        dest.picks.push(copy);
    }
    Ok(())
}

/// Worker-local retained structural projection. Its inputs are invalidation dependencies,
/// never an alternative semantic state; all values originate in committed scenery.
pub struct LocationProjection {
    inputs: SceneryInputs,
    baseline: Vec<rustclient_cache::world::StaticLocation>,
    zones: std::collections::BTreeMap<SceneZone, LocationZone>,
    published: Arc<[LocationZone]>,
    hidden: Vec<u32>,
    builds: u64,
    invalidated: bool,
}
impl LocationProjection {
    #[must_use]
    pub fn new(asset: &SceneAsset, inputs: SceneryInputs) -> Self {
        let baseline = locations::effective_locations(asset, &inputs);
        Self {
            inputs,
            baseline,
            zones: std::collections::BTreeMap::default(),
            published: Arc::default(),
            hidden: Vec::new(),
            builds: 0,
            invalidated: false,
        }
    }
    pub fn invalidate(&mut self) {
        self.invalidated = true;
    }
    #[must_use]
    pub const fn zone_builds(&self) -> u64 {
        self.builds
    }
    /// Prepare current poses while reusing every unchanged structural resource.
    /// Failed builds do not advance dependencies or clear the dirty target.
    /// # Errors
    /// Preserves source model/dependency errors for retry by the owning application.
    pub fn extract(
        &mut self,
        asset: &SceneAsset,
        inputs: &SceneryInputs,
    ) -> crate::Result<SceneGeometry> {
        let dirty = if self.invalidated {
            locations::effective_locations(asset, &self.inputs)
                .into_iter()
                .chain(locations::effective_locations(asset, inputs))
                .filter(|loc| asset.descriptor().contains(loc.tile))
                .map(|loc| SceneZone::of(locations::location_key(&loc)))
                .collect()
        } else {
            changed_location_zones(asset, &self.inputs, inputs)
        };
        let updates = build_location_zones(asset, inputs, &dirty)?;
        let mut animated = crate::mesh::build_animated(asset, inputs)?;
        let updating: BTreeSet<_> = updates.iter().map(|z| z.zone).collect();
        let retained = self
            .zones
            .iter()
            .filter(|(id, _)| !updating.contains(id))
            .map(|(_, z)| z.geometry.vertices.len())
            .fold(0_usize, usize::saturating_add);
        let next = updates
            .iter()
            .map(|z| z.geometry.vertices.len())
            .fold(retained, usize::saturating_add)
            .saturating_add(animated.vertices.len());
        if next > crate::SCENE_VERTEX_LIMIT {
            return Err(crate::SceneError(
                "retained location projection exceeds scene vertex budget".into(),
            ));
        }
        if !dirty.is_empty() {
            self.builds += updates.len() as u64;
            for update in updates {
                self.zones.insert(update.zone, update);
            }
            self.published = self.zones.values().cloned().collect::<Vec<_>>().into();
            self.hidden = self
                .baseline
                .iter()
                .enumerate()
                .filter(|(_, loc)| {
                    self.zones
                        .contains_key(&SceneZone::of(locations::location_key(loc)))
                })
                .filter_map(|(i, _)| u32::try_from(i).ok())
                .collect();
        }
        if !self.inputs.same_geometry(inputs) || self.inputs.roof_areas != inputs.roof_areas {
            self.inputs = inputs.clone();
        }
        self.invalidated = false;
        animated.location_zones = Arc::clone(&self.published);
        animated.hidden_static.clone_from(&self.hidden);
        Ok(animated)
    }
}

/// Empty publications are equivalent without allocating a shared global empty value.
#[must_use]
pub fn same_location_zones(a: &Arc<[LocationZone]>, b: &Arc<[LocationZone]>) -> bool {
    (a.is_empty() && b.is_empty()) || Arc::ptr_eq(a, b)
}
