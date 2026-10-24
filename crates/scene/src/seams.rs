//! Scene-local `ModelData` seam lighting before baking. Reference: Scene.method5585/5587
//! and ModelData.method5262 in the pinned deob source. Flat-lit and animated models
//! never join this pass; normals from earlier merges do not feed subsequent merges.
use crate::locations::PreparedModel;
use std::collections::{BTreeMap, BTreeSet};

fn footprint(model: &PreparedModel) -> [u8; 2] {
    if matches!(model.loc.shape, 10 | 11) {
        model.size
    } else {
        [1, 1]
    }
}
pub(crate) fn merge(models: &mut [PreparedModel]) -> crate::Result<()> {
    let mut tiles = BTreeMap::<_, Vec<usize>>::new();
    let mut memberships = 0_usize;
    for (index, model) in models.iter().enumerate().filter(|(_, m)| m.mergeable) {
        let size = footprint(model);
        for dx in 0..i32::from(size[0]) {
            for dz in 0..i32::from(size[1]) {
                memberships += 1;
                if memberships > 3_000_000 {
                    return Err(crate::SceneError(
                        "normal seam adjacency allocation limit".into(),
                    ));
                }
                tiles
                    .entry((
                        i16::from(model.loc.tile.plane.get()),
                        model.loc.tile.x + dx,
                        model.loc.tile.y + dz,
                    ))
                    .or_default()
                    .push(index);
            }
        }
    }
    for a in 0..models.len() {
        if !models[a].mergeable {
            continue;
        }
        let size = footprint(&models[a]);
        let tile = models[a].loc.tile;
        let mut candidates = BTreeSet::new();
        for plane in i16::from(tile.plane.get()) - 1..=i16::from(tile.plane.get()) + 1 {
            for dx in -1..=i32::from(size[0]) {
                for dz in -1..=i32::from(size[1]) {
                    if let Some(indices) = tiles.get(&(plane, tile.x + dx, tile.y + dz)) {
                        candidates.extend(indices.iter().copied().filter(|b| *b > a));
                    }
                }
            }
        }
        for b in candidates {
            let same_boundary = models[a].loc.tile == models[b].loc.tile
                && models[a].loc.shape <= 3
                && models[b].loc.shape <= 3;
            let Some(hide) = (if same_boundary {
                Some(false)
            } else {
                neighbors(&models[a], &models[b]).or_else(|| neighbors(&models[b], &models[a]))
            }) else {
                continue;
            };
            let (left, right) = models.split_at_mut(b);
            pair(&mut left[a], &mut right[0], hide);
        }
    }
    Ok(())
}

fn neighbors(a: &PreparedModel, b: &PreparedModel) -> Option<bool> {
    let dx = b.loc.tile.x - a.loc.tile.x;
    let dz = b.loc.tile.y - a.loc.tile.y;
    let plane = i16::from(b.loc.tile.plane.get()) - i16::from(a.loc.tile.plane.get());
    if a.loc.shape == 22 {
        return (plane == 0 && b.loc.shape == 22 && matches!((dx, dz), (1, -1..=1) | (0, 1)))
            .then_some(true);
    }
    if matches!(a.loc.shape, 4..=8 | 22)
        || matches!(b.loc.shape, 4..=8 | 22)
        || !(0..=1).contains(&plane)
    {
        return None;
    }
    let a_size = footprint(a);
    let b_size = footprint(b);
    for x in dx..dx + i32::from(b_size[0]) {
        for z in dz..dz + i32::from(b_size[1]) {
            let end_x = i32::from(a_size[0]);
            let end_z = i32::from(a_size[1]);
            if x >= -i32::from(plane == 1)
                && x <= end_x
                && z >= -1
                && z <= end_z
                && (plane == 1 || x == end_x || z == end_z || (z < 0 && x != 0))
            {
                return Some(plane == 0);
            }
        }
    }
    None
}

fn pair(a: &mut PreparedModel, b: &mut PreparedModel, hide: bool) {
    let mut positions = BTreeMap::<_, Vec<usize>>::new();
    for (index, p) in b.points.iter().enumerate() {
        if b.normals.vertices[index].count != 0 {
            positions.entry((p.x, p.y, p.z)).or_default().push(index);
        }
    }
    let mut matched_a = vec![false; a.points.len()];
    let mut matched_b = vec![false; b.points.len()];
    let mut matches = 0;
    for (i, p) in a.points.iter().enumerate() {
        if a.normals.vertices[i].count == 0 {
            continue;
        }
        if let Some(indices) = positions.get(&(p.x, p.y, p.z)) {
            for &j in indices {
                a.merged[i]
                    .get_or_insert(a.normals.vertices[i])
                    .add(b.normals.vertices[j]);
                b.merged[j]
                    .get_or_insert(b.normals.vertices[j])
                    .add(a.normals.vertices[i]);
                matched_a[i] = true;
                matched_b[j] = true;
                matches += 1;
            }
        }
    }
    if hide && matches >= 3 {
        for (model, matched) in [(a, matched_a), (b, matched_b)] {
            for face in &mut model.model.faces {
                if face.vertices.iter().all(|v| matched[*v as usize]) {
                    face.render_type = 2;
                }
            }
        }
    }
}
