//! Scene-local `ModelData` seam lighting before baking. Reference: Scene.method5585/5587
//! and ModelData.method5262 in the pinned deob source. Flat-lit and animated models
//! never join this pass; normals from earlier merges do not feed subsequent merges.
use crate::locations::PreparedModel;
use std::collections::BTreeMap;

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
    let mut lit_a = Vec::new();
    let mut lit_b = Vec::new();
    let mut matched_a = Vec::new();
    let mut matched_b = Vec::new();
    let mut candidates = Vec::new();
    for a in 0..models.len() {
        if !models[a].mergeable {
            continue;
        }
        let size = footprint(&models[a]);
        let tile = models[a].loc.tile;
        candidates.clear();
        for plane in i16::from(tile.plane.get()) - 1..=i16::from(tile.plane.get()) + 1 {
            for dx in -1..=i32::from(size[0]) {
                for dz in -1..=i32::from(size[1]) {
                    if let Some(indices) = tiles.get(&(plane, tile.x + dx, tile.y + dz)) {
                        candidates.extend(indices.iter().copied().filter(|b| *b > a));
                    }
                }
            }
        }
        // Ascending and unique, as the ordered set this replaced visited them.
        candidates.sort_unstable();
        candidates.dedup();
        let mut a_sorted = false;
        for &b in &candidates {
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
            if !a_sorted {
                lit_positions(&models[a], &mut lit_a);
                a_sorted = true;
            }
            lit_positions(&models[b], &mut lit_b);
            let (left, right) = models.split_at_mut(b);
            pair(
                &mut left[a],
                &mut right[0],
                hide,
                [&lit_a, &lit_b],
                &mut matched_a,
                &mut matched_b,
            );
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

/// A model's vertices that carry a normal, as `(x, y, z, vertex)` sorted by position then vertex.
///
/// Normals from earlier merges never feed later ones (only `merged` changes), so the list depends
/// on the model alone; `out` is a reused buffer.
fn lit_positions(model: &PreparedModel, out: &mut Vec<(i32, i32, i32, u32)>) {
    out.clear();
    out.extend(
        model
            .points
            .iter()
            .enumerate()
            .filter(|(index, _)| model.normals.vertices[*index].count != 0)
            .map(|(index, p)| (p.x, p.y, p.z, u32::try_from(index).unwrap_or(u32::MAX))),
    );
    out.sort_unstable();
}

/// Call `each(i, j)` for every vertex `i` of `a` and `j` of `b` at the same position: a merge join
/// of two position-sorted lists. Within one position, `i` and `j` ascend.
fn coincident(
    a: &[(i32, i32, i32, u32)],
    b: &[(i32, i32, i32, u32)],
    mut each: impl FnMut(usize, usize),
) {
    let key = |p: &(i32, i32, i32, u32)| (p.0, p.1, p.2);
    let (mut i, mut j) = (0, 0);
    while i < a.len() && j < b.len() {
        match key(&a[i]).cmp(&key(&b[j])) {
            std::cmp::Ordering::Less => i += 1,
            std::cmp::Ordering::Greater => j += 1,
            std::cmp::Ordering::Equal => {
                let position = key(&a[i]);
                let a_end = i + a[i..].iter().take_while(|p| key(p) == position).count();
                let b_end = j + b[j..].iter().take_while(|p| key(p) == position).count();
                for left in &a[i..a_end] {
                    for right in &b[j..b_end] {
                        each(left.3 as usize, right.3 as usize);
                    }
                }
                (i, j) = (a_end, b_end);
            }
        }
    }
}

/// Merge the normals of every coincident lit vertex pair (`ModelData.method5262`), then hide the
/// faces whose vertices all matched when the pair hides (at least three matches).
///
/// `Normal::add` is wrapping integer addition, so the result does not depend on the order in
/// which coincident pairs are visited.
fn pair(
    a: &mut PreparedModel,
    b: &mut PreparedModel,
    hide: bool,
    [lit_a, lit_b]: [&[(i32, i32, i32, u32)]; 2],
    matched_a: &mut Vec<bool>,
    matched_b: &mut Vec<bool>,
) {
    matched_a.clear();
    matched_a.resize(a.points.len(), false);
    matched_b.clear();
    matched_b.resize(b.points.len(), false);
    let mut matches = 0;
    coincident(lit_a, lit_b, |i, j| {
        a.merged[i]
            .get_or_insert(a.normals.vertices[i])
            .add(b.normals.vertices[j]);
        b.merged[j]
            .get_or_insert(b.normals.vertices[j])
            .add(a.normals.vertices[i]);
        matched_a[i] = true;
        matched_b[j] = true;
        matches += 1;
    });
    if hide && matches >= 3 {
        for (model, matched) in [(a, &*matched_a), (b, &*matched_b)] {
            for face in &mut model.model.faces {
                if face.vertices.iter().all(|v| matched[*v as usize]) {
                    face.render_type = 2;
                }
            }
        }
    }
}

#[cfg(test)]
mod tests {
    use super::coincident;

    /// The nested-loop definition `coincident` must equal: every same-position pair.
    fn brute(a: &[(i32, i32, i32, u32)], b: &[(i32, i32, i32, u32)]) -> Vec<(usize, usize)> {
        let mut out = Vec::new();
        for p in a {
            for q in b {
                if (p.0, p.1, p.2) == (q.0, q.1, q.2) {
                    out.push((p.3 as usize, q.3 as usize));
                }
            }
        }
        out.sort_unstable();
        out
    }

    #[test]
    fn merge_join_finds_exactly_the_coincident_pairs_including_duplicates() {
        // A deterministic spread with repeated positions on both sides.
        let mut seed = 0x2400_0001_u32;
        let mut next = || {
            seed ^= seed << 13;
            seed ^= seed >> 17;
            seed ^= seed << 5;
            i32::try_from(seed % 4).unwrap()
        };
        for size in [0, 1, 7, 40] {
            let mut a: Vec<_> = (0..size).map(|i| (next(), next(), next(), i)).collect();
            let mut b: Vec<_> = (0..size + 3).map(|i| (next(), next(), next(), i)).collect();
            a.sort_unstable();
            b.sort_unstable();
            let mut got = Vec::new();
            coincident(&a, &b, |i, j| got.push((i, j)));
            got.sort_unstable();
            assert_eq!(got, brute(&a, &b), "size {size}");
        }
    }
}
