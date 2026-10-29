//! Per-tile placement order for boundaries, wall decorations and floor decorations.
//!
//! The reference client files at most one of each per tile: a later location replaces the earlier one.
//! A wall decoration is displaced by the boundary already on its tile, and a later wall of the right
//! kind retroactively sets its offset (revision 240 `classDI.method3340`, `Scene.method4502`).
//! Porting the client's order instead of looking up the final wall is what keeps decorations flush
//! when a decoration is authored before its wall.
//!
//! The 240 rule is absolute and idempotent: a later type 0 or 2 wall with displacement `dd != 16`
//! sets every nonzero axis of the decoration's first plate to `sign * (dd + 1)`, a later type 9 wall
//! to `sign * (dd / 2 + 1)`. It runs only while the scene loads, never for a server-added location
//! (`classDI.method3336`), and never touches the second plate. See
//! `docs/plan/WALL-DECOR-240-2026-10-29.md` for the stock tables and citations.
//!
//! The optional diagonal snap (`FLUSH_DIAGONAL_DECORATIONS`, off) is not client behaviour: it
//! snaps `256`-flag plates against the type-9 slab they sit on. It was ported from `RustOSRS` to
//! compensate for 235-era offsets that the 240 placement above no longer needs.

use crate::{
    Result, SceneAsset,
    locations::{construct, decoration_offset, has_model, placement_requests, resolve},
};
use rustclient_cache::world::StaticLocation;
use rustclient_model::WorldTile;
use std::collections::{BTreeSet, HashMap};

/// Non-reference diagonal-decoration fix; see the module documentation. Off: stock 240 offsets are
/// used as they are.
const FLUSH_DIAGONAL_DECORATIONS: bool = false;

/// Decoration state for each location of one scene build, indexed like the location list.
pub(crate) struct Plan {
    dropped: Vec<bool>,
    /// First-plate displacement per wall-decoration location, after client rescaling.
    offsets: HashMap<usize, [i32; 2]>,
    /// Absolute plate offsets of snapped diagonal decorations, replacing the client's.
    plates: HashMap<usize, [[i32; 2]; 2]>,
}
impl Plan {
    /// A later location on the same tile and layer replaced this one.
    pub(crate) fn dropped(&self, index: usize) -> bool {
        self.dropped.get(index).copied().unwrap_or(false)
    }
    pub(crate) fn requests(
        &self,
        index: usize,
        shape: u8,
        rotation: u8,
    ) -> Vec<(u8, u8, [i32; 2])> {
        let offset = self
            .offsets
            .get(&index)
            .copied()
            .unwrap_or_else(|| decoration_offset(shape, rotation, None));
        let mut requests = placement_requests(shape, rotation, offset);
        if let Some(plates) = self.plates.get(&index) {
            for (request, plate) in requests.iter_mut().zip(plates) {
                request.2 = *plate;
            }
        }
        requests
    }
}

/// How the client treats one location when it files it on its tile.
#[derive(Clone, Copy)]
struct Placed {
    shape: u8,
    rotation: u8,
    /// Whether the client would create its scene object, which it does only when the primary model exists.
    inserted: bool,
    /// Whether this location runs the load-path rescale; a server-added location never does.
    rescales: bool,
}

/// Which scene tile and slot a location occupies; the client keeps one object per layer slot.
#[derive(Default)]
struct Sequencer {
    boundary_displacement: HashMap<WorldTile, i32>,
    boundary_owner: HashMap<WorldTile, usize>,
    decoration_owner: HashMap<WorldTile, usize>,
    floor_owner: HashMap<WorldTile, usize>,
    offsets: HashMap<usize, [i32; 2]>,
    dropped: Vec<bool>,
}
impl Sequencer {
    fn replace(
        owners: &mut HashMap<WorldTile, usize>,
        dropped: &mut [bool],
        tile: WorldTile,
        index: usize,
    ) {
        if let Some(previous) = owners.insert(tile, index) {
            dropped[previous] = true;
        }
    }
    /// One location, in file order (`Scene.method4502` runs only on the load path).
    fn place(&mut self, index: usize, loc: &StaticLocation, placed: Placed, displacement: i32) {
        let Placed {
            shape,
            rotation,
            inserted,
            rescales,
        } = placed;
        let tile = loc.tile;
        match shape {
            0..=3 if inserted => {
                Self::replace(&mut self.boundary_owner, &mut self.dropped, tile, index);
                self.boundary_displacement.insert(tile, displacement);
            }
            4..=8 => {
                let existing = self.boundary_displacement.get(&tile).copied();
                self.offsets
                    .insert(index, decoration_offset(shape, rotation, existing));
                if inserted {
                    Self::replace(&mut self.decoration_owner, &mut self.dropped, tile, index);
                }
            }
            22 if inserted => Self::replace(&mut self.floor_owner, &mut self.dropped, tile, index),
            _ => {}
        }
        // `Scene.method4502`: walls of kinds 0, 2 and 9 set the offset of a decoration already on
        // the tile. Kinds 1, 3 and 12+ never call it.
        let argument = match loc.shape {
            0 | 2 => Some(1 + displacement),
            9 => Some(1 + displacement / 2),
            _ => None,
        };
        if rescales
            && displacement != 16
            && let Some(argument) = argument
            && let Some(owner) = self.decoration_owner.get(&tile)
            && let Some(offset) = self.offsets.get_mut(owner)
        {
            *offset = offset.map(|v| v.signum() * argument);
        }
    }
}

/// Plan every location. `tiles` restricts the work to those tiles (a pose-only rebuild); state is
/// per tile, so a restricted plan agrees with the full one on the tiles it covers.
pub(crate) fn plan(
    asset: &SceneAsset,
    inputs: &crate::SceneryInputs,
    locations: &[StaticLocation],
    tiles: Option<&BTreeSet<WorldTile>>,
) -> Result<Plan> {
    plan_with(asset, inputs, locations, tiles, FLUSH_DIAGONAL_DECORATIONS)
}

/// `plan` with the optional diagonal snap chosen by the caller.
pub(crate) fn plan_with(
    asset: &SceneAsset,
    inputs: &crate::SceneryInputs,
    locations: &[StaticLocation],
    tiles: Option<&BTreeSet<WorldTile>>,
    flush_diagonals: bool,
) -> Result<Plan> {
    let d = asset.descriptor;
    let origin = d.origin();
    let mut sequencer = Sequencer {
        dropped: vec![false; locations.len()],
        ..Sequencer::default()
    };
    let mut hosts: HashMap<WorldTile, usize> = HashMap::new();
    for (index, loc) in locations.iter().enumerate() {
        if tiles.is_some_and(|tiles| !tiles.contains(&loc.tile))
            || loc.tile.x < origin.x
            || loc.tile.y < origin.y
            || i64::from(loc.tile.x) >= i64::from(origin.x) + i64::from(d.width())
            || i64::from(loc.tile.y) >= i64::from(origin.y) + i64::from(d.height())
        {
            continue;
        }
        let Some(def) = asset.assets.objects.get(&loc.object) else {
            continue;
        };
        let animated = inputs
            .locations
            .get(&crate::locations::location_key(loc))
            .and_then(|o| o.animation)
            .and_then(|a| a.placement);
        let rotation = animated.map_or(loc.orientation.get(), |p| p.orientation.get());
        let shape = animated.map_or(loc.shape, |p| p.shape);
        let resolved = resolve(asset, def, inputs).ok().flatten();
        let inserted = resolved.is_some_and(|def| {
            placement_requests(shape, rotation, [0, 0])
                .iter()
                .any(|request| has_model(asset, def, request.0))
        });
        if loc.shape == 9 && inserted {
            hosts.insert(loc.tile, index);
        }
        let server_added = matches!(
            inputs
                .locations
                .get(&crate::locations::location_key(loc))
                .map(|o| o.change),
            Some(rustclient_model::LocationChange::Replace(_))
        );
        sequencer.place(
            index,
            loc,
            Placed {
                shape,
                rotation,
                inserted,
                rescales: !server_added,
            },
            i32::from(def.decoration_displacement),
        );
    }
    let mut plates = HashMap::new();
    if flush_diagonals {
        for (&tile, &owner) in &sequencer.decoration_owner {
            let loc = &locations[owner];
            if !(6..=8).contains(&loc.shape) || sequencer.dropped[owner] {
                continue;
            }
            let host = hosts.get(&tile).map(|&host| &locations[host]);
            let offset = sequencer.offsets.get(&owner).copied().unwrap_or([0, 0]);
            if let Some(snapped) = flush(asset, inputs, loc, offset, host)? {
                plates.insert(owner, snapped);
            }
        }
    }
    Ok(Plan {
        dropped: sequencer.dropped,
        offsets: sequencer.offsets,
        plates,
    })
}

const K: f64 = std::f64::consts::FRAC_1_SQRT_2;
/// The four diagonal wall normals in (x, z).
const NORMALS: [(f64, f64); 4] = [(K, K), (K, -K), (-K, -K), (-K, K)];
/// Plates clear their face by a hair: the integer 45-degree rotation leaves some plates up to 0.7
/// units behind it, where the depth buffer would hide them.
const LIFT: f64 = 1.0;

/// (x, z) of every vertex a drawn face references; render types 2 and 3 are never drawn.
fn used_points(model: &crate::locations::InstanceModel) -> Vec<(i32, i32)> {
    let mut used = vec![false; model.vertices.len()];
    for face in &model.faces {
        if matches!(face.render_type, 2 | 3) {
            continue;
        }
        for &vertex in &face.vertices {
            if let Some(flag) = used.get_mut(vertex as usize) {
                *flag = true;
            }
        }
    }
    model
        .vertices
        .iter()
        .zip(used)
        .filter(|(_, used)| *used)
        .map(|(vertex, _)| (vertex.x, vertex.z))
        .collect()
}
fn range(points: &[(i32, i32)], normal: (f64, f64)) -> (f64, f64) {
    points.iter().fold((f64::MAX, f64::MIN), |(lo, hi), point| {
        let projection = f64::from(point.0) * normal.0 + f64::from(point.1) * normal.1;
        (lo.min(projection), hi.max(projection))
    })
}
#[expect(
    clippy::cast_possible_truncation,
    reason = "offsets are a few local units"
)]
fn rounded(delta: f64, normal: (f64, f64)) -> [i32; 2] {
    [
        (delta * normal.0).round() as i32,
        (delta * normal.1).round() as i32,
    ]
}

/// Absolute plate offsets for one diagonal decoration, or `None` to keep the client's.
fn flush(
    asset: &SceneAsset,
    inputs: &crate::SceneryInputs,
    loc: &StaticLocation,
    offset: [i32; 2],
    host: Option<&StaticLocation>,
) -> Result<Option<[[i32; 2]; 2]>> {
    let Some(decor_def) = asset.assets.objects.get(&loc.object) else {
        return Ok(None);
    };
    let Some(decor_def) = resolve(asset, decor_def, inputs)? else {
        return Ok(None);
    };
    let requests = placement_requests(loc.shape, loc.orientation.get(), offset);
    let plate = |request: Option<&(u8, u8, [i32; 2])>| -> Result<Option<Vec<(i32, i32)>>> {
        let Some(&(shape, orientation, _)) = request else {
            return Ok(None);
        };
        Ok(construct(asset, decor_def, shape, orientation)?.map(|model| used_points(&model)))
    };
    let plates = [plate(requests.first())?, plate(requests.get(1))?];

    let host_slab = match host {
        Some(host) => host_slab(asset, inputs, host)?,
        None => None,
    };
    // Only a type-9 wall on this tile is a host; without one the client's offsets stand, lifted.
    if host.is_none() {
        let mut offsets = [offset, [0, 0]];
        for (slot, points) in plates.iter().enumerate() {
            let Some(points) = points else { continue };
            for normal in NORMALS {
                let (lo, hi) = range(points, normal);
                if lo.abs() <= 1.5 && hi > 1.5 {
                    let lift = rounded(1.0, normal);
                    offsets[slot] = [offsets[slot][0] + lift[0], offsets[slot][1] + lift[1]];
                    break;
                }
            }
        }
        return Ok(Some(offsets));
    }
    let Some((normal, slab_low)) = host_slab else {
        return Ok(None);
    };
    // A thin slab (a 16-unit wall) has a second, inner face; a wedge's inside is solid.
    let thin = -slab_low < 24.0;
    let mut offsets = [[0, 0]; 2];
    for (slot, points) in plates.iter().enumerate() {
        let Some(points) = points else { continue };
        let (plate_low, plate_high) = range(points, normal);
        let delta = if plate_low.abs() <= 1.5 && plate_high > 1.5 {
            LIFT
        } else if plate_high.abs() <= 1.5 && plate_low < -1.5 {
            if thin {
                slab_low - LIFT
            } else {
                // Buried in a solid wedge: keep the client's placement.
                offsets[slot] = if slot == 0 { offset } else { [0, 0] };
                continue;
            }
        } else {
            continue;
        };
        offsets[slot] = rounded(delta, normal);
    }
    Ok(Some(offsets))
}

/// The host wall's outward normal and the low end of its slab along that normal. The host's flat
/// face passes through the tile centre, so along one diagonal axis its vertices end at 0.
fn host_slab(
    asset: &SceneAsset,
    inputs: &crate::SceneryInputs,
    host: &StaticLocation,
) -> Result<Option<((f64, f64), f64)>> {
    let Some(def) = asset.assets.objects.get(&host.object) else {
        return Ok(None);
    };
    let Some(def) = resolve(asset, def, inputs)? else {
        return Ok(None);
    };
    let Some(model) = construct(asset, def, 9, host.orientation.get())? else {
        return Ok(None);
    };
    let points = used_points(&model);
    Ok(NORMALS.into_iter().find_map(|normal| {
        let (lo, hi) = range(&points, normal);
        (hi.abs() < 1.0 && lo < -2.0).then_some((normal, lo))
    }))
}

#[cfg(test)]
mod tests {
    use super::*;
    use rustclient_model::{LocOrientation, ObjectDefinitionId, Plane};

    fn loc(object: u16, shape: u8, tile: (i32, i32)) -> StaticLocation {
        StaticLocation {
            object: ObjectDefinitionId::new(u32::from(object)),
            tile: WorldTile::new(tile.0, tile.1, Plane::MIN),
            shape,
            orientation: LocOrientation::new(0).unwrap(),
        }
    }
    fn sequence(steps: &[(StaticLocation, u8, i32)]) -> Sequencer {
        sequence_with(steps, true)
    }
    fn sequence_with(steps: &[(StaticLocation, u8, i32)], rescales: bool) -> Sequencer {
        let mut sequencer = Sequencer {
            dropped: vec![false; steps.len()],
            ..Sequencer::default()
        };
        for (index, (location, rotation, displacement)) in steps.iter().enumerate() {
            let placed = Placed {
                shape: location.shape,
                rotation: *rotation,
                inserted: true,
                rescales,
            };
            sequencer.place(index, location, placed, *displacement);
        }
        sequencer
    }

    #[test]
    fn decoration_after_a_wall_is_displaced_by_that_wall_plus_one() {
        // 240 `classDI.method3340`: type 5 takes `dd + 1`, types 6 and 8 take `dd / 2 + 1`.
        let s = sequence(&[(loc(1, 0, (3, 3)), 0, 32), (loc(2, 5, (3, 3)), 0, 16)]);
        assert_eq!(s.offsets[&1], [33, 0]);
        let s = sequence(&[(loc(1, 0, (3, 3)), 0, 32), (loc(2, 6, (3, 3)), 0, 16)]);
        assert_eq!(s.offsets[&1], [17, -17]);
        let s = sequence(&[(loc(2, 6, (3, 3)), 0, 16)]);
        assert_eq!(
            s.offsets[&0],
            [9, -9],
            "no wall: defaults 16 and 8, plus one"
        );
        let s = sequence(&[(loc(2, 5, (3, 3)), 0, 16)]);
        assert_eq!(s.offsets[&0], [17, 0]);
    }

    #[test]
    fn decoration_before_a_thick_wall_is_set_to_the_same_offset() {
        let s = sequence(&[(loc(2, 5, (3, 3)), 0, 16), (loc(1, 0, (3, 3)), 0, 32)]);
        assert_eq!(s.offsets[&0], [33, 0]);
        // Type 2 uses `dd + 1`; a diagonal first plate keeps its sign on both axes.
        let s = sequence(&[(loc(2, 6, (3, 3)), 0, 16), (loc(1, 2, (3, 3)), 0, 32)]);
        assert_eq!(s.offsets[&0], [33, -33]);
        // Type 9 uses `dd / 2 + 1`, which is the diagonal rule.
        let s = sequence(&[(loc(2, 6, (3, 3)), 0, 16), (loc(1, 9, (3, 3)), 0, 32)]);
        assert_eq!(s.offsets[&0], [17, -17]);
    }

    #[test]
    fn straight_and_inside_unit_offsets_are_rescaled_too() {
        // Type 4 and type 7 start at a unit step, which is nonzero, so the wall rescales them.
        let s = sequence(&[(loc(2, 4, (3, 3)), 1, 16), (loc(1, 0, (3, 3)), 0, 32)]);
        assert_eq!(s.offsets[&0], [0, -33]);
        let s = sequence(&[(loc(2, 7, (3, 3)), 0, 16), (loc(1, 9, (3, 3)), 0, 32)]);
        assert_eq!(s.offsets[&0], [-17, 17]);
    }

    #[test]
    fn only_wall_kinds_zero_two_and_nine_rescale() {
        for (shape, rescaled) in [
            (0, Some(41)),
            (1, None),
            (2, Some(41)),
            (3, None),
            (9, Some(21)),
            (10, None),
            (11, None),
            (12, None),
            (21, None),
        ] {
            let s = sequence(&[(loc(2, 5, (3, 3)), 0, 16), (loc(1, shape, (3, 3)), 0, 40)]);
            assert_eq!(s.offsets[&0], [rescaled.unwrap_or(17), 0], "shape {shape}");
        }
    }

    #[test]
    fn a_default_displacement_wall_never_rescales() {
        let s = sequence(&[(loc(2, 5, (3, 3)), 0, 16), (loc(1, 0, (3, 3)), 0, 16)]);
        assert_eq!(s.offsets[&0], [17, 0]);
    }

    #[test]
    fn rescaling_is_absolute_not_compounding() {
        // The last wall wins: 8 then sets `1 + 8`, whatever 32 did before it.
        let s = sequence(&[
            (loc(2, 5, (3, 3)), 0, 16),
            (loc(1, 0, (3, 3)), 0, 32),
            (loc(3, 0, (3, 3)), 0, 8),
        ]);
        assert_eq!(s.offsets[&0], [9, 0]);
        let s = sequence(&[
            (loc(2, 5, (3, 3)), 0, 16),
            (loc(1, 0, (3, 3)), 0, 32),
            (loc(3, 0, (3, 3)), 0, 32),
        ]);
        assert_eq!(s.offsets[&0], [33, 0], "idempotent");
    }

    #[test]
    fn a_server_added_wall_does_not_rescale() {
        let s = sequence_with(
            &[(loc(2, 5, (3, 3)), 0, 16), (loc(1, 0, (3, 3)), 0, 32)],
            false,
        );
        assert_eq!(s.offsets[&0], [17, 0]);
    }

    #[test]
    fn later_locations_replace_earlier_ones_per_layer_and_tile() {
        let s = sequence(&[
            (loc(1, 4, (3, 3)), 0, 16),
            (loc(2, 5, (3, 3)), 0, 16),
            (loc(3, 0, (3, 3)), 0, 16),
            (loc(4, 2, (3, 3)), 0, 16),
            (loc(5, 22, (3, 3)), 0, 16),
            (loc(6, 22, (3, 3)), 0, 16),
            (loc(7, 4, (4, 3)), 0, 16),
        ]);
        // Wall decorations 4 and 5 share one slot, boundaries 0 and 2 another, floor decorations a third.
        assert_eq!(s.dropped, [true, false, true, false, true, false, false]);
    }

    #[test]
    fn placement_requests_carry_the_240_offsets_and_the_second_plate() {
        use crate::locations::decoration_offset as offset;
        assert_eq!(
            placement_requests(4, 0, offset(4, 0, None)),
            [(4, 0, [1, 0])]
        );
        assert_eq!(
            placement_requests(4, 3, offset(4, 3, None)),
            [(4, 3, [0, 1])]
        );
        assert_eq!(
            placement_requests(5, 1, offset(5, 1, Some(34))),
            [(4, 1, [0, -35])]
        );
        // Shape 8: first plate `dd / 2 + 1`, second plate its own unit step (not the centre).
        assert_eq!(
            placement_requests(8, 0, offset(8, 0, Some(34))),
            [(4, 4, [18, -18]), (4, 6, [-1, 1])]
        );
        // Shape 7 is drawn with the opposite orientation and its unit offset.
        assert_eq!(
            placement_requests(7, 1, offset(7, 1, None)),
            [(4, 7, [1, 1])]
        );
        assert_eq!(
            placement_requests(6, 2, offset(6, 2, Some(35))),
            [(4, 6, [-18, 18])]
        );
    }
}
