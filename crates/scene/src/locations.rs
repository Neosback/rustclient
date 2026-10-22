use crate::{Result, SceneAsset, SceneError, WorldMesh, mesh::visible};
use rustclient_cache::world::{ObjectDefinition, StaticLocation};
use rustclient_model::{ModelPoint, Plane};
use std::collections::BTreeMap;
use std::hash::{Hash, Hasher};
pub(crate) fn build(
    asset: &SceneAsset,
    mesh: &mut WorldMesh,
    plane: Plane,
    inputs: &crate::SceneryInputs,
    dynamic: bool,
) -> Result<()> {
    let d = asset.descriptor;
    let origin = d.origin();
    let locations = effective_locations(asset, inputs);
    let mut walls = BTreeMap::new();
    {
        for loc in &locations {
            if loc.shape <= 3
                && let Some(def) = asset.assets.objects.get(&loc.object)
            {
                walls.insert(loc.tile, def.decoration_displacement);
            }
        }
    }
    {
        for loc in &locations {
            if loc.tile.x < origin.x
                || loc.tile.y < origin.y
                || i64::from(loc.tile.x) >= i64::from(origin.x) + i64::from(d.width())
                || i64::from(loc.tile.y) >= i64::from(origin.y) + i64::from(d.height())
                || !visible(asset, loc.tile.plane, loc.tile.x, loc.tile.y, plane)
            {
                continue;
            }
            let def = asset
                .assets
                .objects
                .get(&loc.object)
                .ok_or_else(|| SceneError(format!("missing object {}", loc.object.get())))?;
            if (def.animation.is_some() || def.morphs.is_some()) != dynamic {
                continue;
            }
            mesh.stats.locations += 1;
            let Some(def) = resolve(asset, def, inputs)? else {
                continue;
            };
            if def.animation.is_some() {
                mesh.stats.animated_static += 1;
            }
            let rotation = loc.orientation.get();
            let thickness = i32::from(*walls.get(&loc.tile).unwrap_or(&16));
            let requests = placement_requests(loc.shape, rotation, thickness);
            let mut selected = false;
            for (shape, orientation, offset) in requests {
                selected |= place(asset, mesh, loc, def, shape, orientation, offset, inputs)?;
            }
            if !selected {
                mesh.stats.no_model += 1;
            }
        }
    }
    Ok(())
}
fn resolve<'a>(
    asset: &'a SceneAsset,
    mut def: &'a ObjectDefinition,
    inputs: &crate::SceneryInputs,
) -> Result<Option<&'a ObjectDefinition>> {
    let mut visited = std::collections::BTreeSet::new();
    while let Some(morphs) = &def.morphs {
        if !visited.insert(def.id) {
            return Err(SceneError("cyclic object morph".into()));
        }
        let selector = if let Some(id) = morphs.varbit {
            let bit = asset
                .assets
                .varbits
                .get(&id)
                .ok_or_else(|| SceneError("missing varbit dependency".into()))?;
            let value = *inputs.variables.get(&bit.varp).unwrap_or(&0);
            let width = u32::from(bit.high - bit.low) + 1;
            let mask = if width == 32 {
                u32::MAX
            } else {
                (1_u32 << width) - 1
            };
            ((value.cast_unsigned() >> bit.low) & mask).cast_signed()
        } else {
            morphs
                .varp
                .map_or(-1, |id| *inputs.variables.get(&id).unwrap_or(&0))
        };
        let selected = usize::try_from(selector)
            .ok()
            .and_then(|n| morphs.targets.get(n))
            .copied()
            .unwrap_or(morphs.fallback);
        let Some(id) = selected else {
            return Ok(None);
        };
        def = asset
            .assets
            .objects
            .get(&id)
            .ok_or_else(|| SceneError("missing morph dependency".into()))?;
    }
    Ok(Some(def))
}
struct InstanceModel {
    vertices: Vec<ModelPoint>,
    faces: Vec<rustclient_cache::world::ModelFace>,
    textures: Vec<[u32; 3]>,
    skins: Vec<Option<u8>>,
}
fn construct(
    asset: &SceneAsset,
    def: &ObjectDefinition,
    shape: u8,
    orientation: u8,
) -> Result<Option<InstanceModel>> {
    let mut model = InstanceModel {
        vertices: Vec::new(),
        faces: Vec::new(),
        textures: Vec::new(),
        skins: Vec::new(),
    };
    let mut shared = BTreeMap::new();
    let mirror = def.mirrored ^ (orientation > 3);
    for reference in def
        .models
        .iter()
        .filter(|m| m.shape.map_or(shape == 10, |s| s == shape))
    {
        let source = asset
            .assets
            .models
            .get(&reference.id)
            .ok_or_else(|| SceneError("missing selected model".into()))?;
        let mut mapping = Vec::with_capacity(source.vertices.len());
        for (vertex, &point) in source.vertices.iter().enumerate() {
            let point = transform(point, def, shape, orientation, mirror)?;
            let key = (
                source.vertices[vertex].x,
                source.vertices[vertex].y,
                source.vertices[vertex].z,
            );
            let index = *shared.entry(key).or_insert_with(|| {
                let index =
                    u32::try_from(model.vertices.len()).expect("bounded source combination");
                model.vertices.push(point);
                model.skins.push(source.vertex_skins[vertex]);
                index
            });
            mapping.push(index);
        }
        let texture_base = u16::try_from(model.textures.len())
            .map_err(|_| SceneError("texture mapping limit".into()))?;
        model.textures.extend(
            source
                .texture_triangles
                .iter()
                .map(|t| t.map(|v| mapping[v as usize])),
        );
        for &face in &source.faces {
            let mut face = face;
            face.vertices = face.vertices.map(|v| mapping[v as usize]);
            if mirror {
                face.vertices.swap(0, 2);
            }
            face.texture_mapping = face.texture_mapping.map(|v| v + texture_base);
            for &(from, to) in &def.recolors {
                if face.color == from {
                    face.color = to;
                }
            }
            for &(from, to) in &def.retextures {
                if face.texture == from {
                    face.texture = to;
                }
            }
            model.faces.push(face);
        }
    }
    Ok((!model.vertices.is_empty()).then_some(model))
}
#[expect(
    clippy::too_many_arguments,
    reason = "validated reference placement inputs"
)]
#[expect(
    clippy::too_many_lines,
    reason = "single reference construction/pose/contour/upload sequence; split after structural parity"
)]
fn place(
    asset: &SceneAsset,
    mesh: &mut WorldMesh,
    loc: &StaticLocation,
    def: &ObjectDefinition,
    shape: u8,
    orientation: u8,
    offset: [i32; 2],
    inputs: &crate::SceneryInputs,
) -> Result<bool> {
    let Some(mut model) = construct(asset, def, shape, orientation)? else {
        return Ok(false);
    };
    let size = if loc.orientation.get() & 1 != 0 {
        [def.size[1], def.size[0]]
    } else {
        def.size
    };
    let x0 = loc.tile.x + i32::from(size[0] / 2);
    let y0 = loc.tile.y + i32::from(size[1] / 2);
    let x1 = loc.tile.x + i32::from(size[0].div_ceil(2));
    let y1 = loc.tile.y + i32::from(size[1].div_ceil(2));
    let base = (asset.elevation(loc.tile.plane, x0, y0)?
        + asset.elevation(loc.tile.plane, x1, y0)?
        + asset.elevation(loc.tile.plane, x0, y1)?
        + asset.elevation(loc.tile.plane, x1, y1)?)
        >> 2;
    let center = [
        loc.tile.x * 128 + i32::from(size[0]) * 64,
        loc.tile.y * 128 + i32::from(size[1]) * 64,
    ];
    let mut normals = crate::lighting::calculate(&model.vertices, &model.faces);
    let base_normals = &normals;
    let empty_merged = vec![None; model.vertices.len()];
    let mut colors: Vec<_> = model
        .faces
        .iter()
        .enumerate()
        .map(|(i, &f)| {
            crate::lighting::bake(f, i, base_normals, &empty_merged, def.ambient, def.contrast)
        })
        .collect();
    let start = inputs
        .locations
        .get(&location_key(loc))
        .and_then(|o| o.animation);
    let animation = start.map_or(def.animation, |s| s.sequence);
    if let Some(id) = animation {
        let seq = asset
            .assets
            .sequences
            .get(&id)
            .ok_or_else(|| SceneError("missing sequence".into()))?;
        let zone = (loc.tile.x.div_euclid(8), loc.tile.y.div_euclid(8));
        let local = (
            center[0] + offset[0] - zone.0 * 1024,
            -base,
            center[1] + offset[1] - zone.1 * 1024,
        );
        let mut hash = std::collections::hash_map::DefaultHasher::new();
        (zone, local, loc.tile.plane.get(), id.get()).hash(&mut hash);
        let random = def
            .random_start
            .then(|| start.map_or_else(|| hash.finish(), |s| s.random_seed));
        if let Some(index) = crate::animation::frame_index(
            seq,
            inputs
                .cycle
                .get()
                .saturating_sub(start.map_or(0, |s| s.cycle.get())),
            random,
        )? {
            let frame = asset
                .assets
                .frames
                .get(&seq.frames[index])
                .ok_or_else(|| SceneError("missing sequence frame".into()))?;
            let skeleton = asset
                .assets
                .skeletons
                .get(&frame.skeleton)
                .ok_or_else(|| SceneError("missing skeleton".into()))?;
            crate::animation::pose(
                &mut model.vertices,
                &mut model.faces,
                &model.skins,
                frame,
                skeleton,
                orientation,
            )?;
        }
    }

    if let Some(clip) = def.contour {
        let height = model
            .vertices
            .iter()
            .map(|p| -p.y)
            .max()
            .unwrap_or(0)
            .max(0);
        for p in &mut model.vertices {
            let delta = base - sample(asset, loc.tile.plane, center[0] + p.x, center[1] + p.z)?;
            if clip == 0 {
                p.y += delta;
            } else {
                let ratio = if height == 0 {
                    0
                } else {
                    (-i64::from(p.y) << 16) / i64::from(height)
                };
                if ratio < i64::from(clip) {
                    p.y += i32::try_from(
                        i64::from(delta) * (i64::from(clip) - ratio) / i64::from(clip),
                    )
                    .map_err(|_| SceneError("contour overflow".into()))?;
                }
            }
        }
        if def.non_flat_shading && def.animation.is_none() {
            normals = crate::lighting::calculate(&model.vertices, &model.faces);
        }
    }
    let merged = vec![None; model.vertices.len()];
    if def.non_flat_shading && def.animation.is_none() {
        colors = model
            .faces
            .iter()
            .enumerate()
            .map(|(i, &f)| {
                crate::lighting::bake(f, i, &normals, &merged, def.ambient, def.contrast)
            })
            .collect();
    }
    for (index, &face) in model.faces.iter().enumerate() {
        let Some(colors) = colors[index] else {
            mesh.stats.hidden_faces += 1;
            continue;
        };
        let uv = if face.texture.is_some() {
            face.texture_mapping
                .and_then(|s| model.textures.get(usize::from(s)))
                .map_or([[0., 0.], [1., 0.], [0., 1.]], |t| {
                    crate::uv::authored(
                        face.vertices.map(|v| model.vertices[v as usize]),
                        t.map(|v| model.vertices[v as usize]),
                    )
                })
        } else {
            [[0.; 2]; 3]
        };
        let points = face.vertices.map(|v| {
            let p = model.vertices[v as usize];
            let p = if loc.shape == 11 {
                rotate_diagonal(p)
            } else {
                p
            };
            [
                center[0] + p.x + offset[0] - asset.descriptor.origin().x * 128,
                p.y - base,
                center[1] + p.z + offset[1] - asset.descriptor.origin().y * 128,
            ]
        });
        let alpha = if face.alpha == -2 {
            0
        } else {
            face.alpha.cast_unsigned()
        };
        mesh.geometry
            .triangle(points, colors, face.texture, uv, alpha, face.bias)?;
        mesh.stats.model_triangles += 1;
    }
    Ok(true)
}
pub(crate) fn transform(
    mut p: ModelPoint,
    def: &ObjectDefinition,
    shape: u8,
    orientation: u8,
    mirror: bool,
) -> Result<ModelPoint> {
    if mirror {
        p.z = p.z.wrapping_neg();
    }
    if shape == 4 && orientation > 3 {
        let x = p.x;
        let z = p.z;
        p.x = (z.wrapping_mul(46_340).wrapping_add(x.wrapping_mul(46_340)) >> 16) + 45;
        p.z = (z.wrapping_mul(46_340).wrapping_sub(x.wrapping_mul(46_340)) >> 16) - 45;
    }
    (p.x, p.z) = match orientation & 3 {
        0 => (p.x, p.z),
        1 => (p.z, -p.x),
        2 => (-p.x, -p.z),
        _ => (-p.z, p.x),
    };
    p.x = p.x.wrapping_mul(i32::from(def.scale[0])) / 128 + def.translation[0];
    p.y = p.y.wrapping_mul(i32::from(def.scale[1])) / 128 + def.translation[1];
    p.z = p.z.wrapping_mul(i32::from(def.scale[2])) / 128 + def.translation[2];
    if [p.x, p.y, p.z].iter().any(|v| v.unsigned_abs() > 1_000_000) {
        return Err(SceneError("transformed model coordinate limit".into()));
    }
    Ok(p)
}
pub(crate) fn rotate_diagonal(mut p: ModelPoint) -> ModelPoint {
    let x = p.x;
    let z = p.z;
    p.x = (z.wrapping_mul(46_340).wrapping_add(x.wrapping_mul(46_340))) >> 16;
    p.z = (z.wrapping_mul(46_340).wrapping_sub(x.wrapping_mul(46_340))) >> 16;
    p
}
fn sample(asset: &SceneAsset, plane: Plane, x: i32, y: i32) -> Result<i32> {
    let tx = x.div_euclid(128);
    let ty = y.div_euclid(128);
    let fx = x.rem_euclid(128);
    let fy = y.rem_euclid(128);
    let a = (asset.elevation(plane, tx, ty)? * (128 - fx)
        + asset.elevation(plane, tx + 1, ty)? * fx)
        >> 7;
    let b = (asset.elevation(plane, tx, ty + 1)? * (128 - fx)
        + asset.elevation(plane, tx + 1, ty + 1)? * fx)
        >> 7;
    Ok((a * (128 - fy) + b * fy) >> 7)
}

pub(crate) fn placement_requests(
    shape: u8,
    rotation: u8,
    thickness: i32,
) -> Vec<(u8, u8, [i32; 2])> {
    let mut requests = Vec::with_capacity(2);
    match shape {
        2 => {
            requests.push((2, rotation + 4, [0, 0]));
            requests.push((2, (rotation + 1) & 3, [0, 0]));
        }
        4 => requests.push((4, rotation, [0, 0])),
        5 => {
            let offset = [[1, 0], [0, -1], [-1, 0], [0, 1]][usize::from(rotation)];
            requests.push((4, rotation, offset.map(|v| v * thickness)));
        }
        6 | 8 => {
            let offset = [[1, -1], [-1, -1], [-1, 1], [1, 1]][usize::from(rotation)]
                .map(|v| v * thickness / 2);
            requests.push((4, rotation + 4, offset));
            if shape == 8 {
                requests.push((4, ((rotation + 2) & 3) + 4, offset));
            }
        }
        7 => requests.push((4, ((rotation + 2) & 3) + 4, [0, 0])),
        11 => requests.push((10, rotation, [0, 0])),
        shape => requests.push((shape, rotation, [0, 0])),
    }

    requests
}

/// Placement shadow input is assembled before terrain colors.
#[expect(
    clippy::cast_possible_truncation,
    clippy::cast_sign_loss,
    clippy::cast_possible_wrap,
    clippy::cast_precision_loss,
    reason = "bounded local tile indexes and reference radius truncation"
)]
pub(crate) fn shadows(
    asset: &SceneAsset,
    plane: Plane,
    width: usize,
    height: usize,
    inputs: &crate::SceneryInputs,
) -> Result<Vec<i32>> {
    let mut shadows = vec![0; (width + 1) * (height + 1)];
    let stride = height + 1;
    let origin = asset.descriptor.origin();
    {
        for loc in &effective_locations(asset, inputs) {
            let x = loc.tile.x - origin.x;
            let y = loc.tile.y - origin.y;
            if loc.tile.plane != plane
                || x < 1
                || y < 1
                || x >= width as i32 - 1
                || y >= height as i32 - 1
            {
                continue;
            }
            let def = &asset.assets.objects[&loc.object];
            if !def.clipped {
                continue;
            }
            let x = x as usize;
            let y = y as usize;
            let orientation = loc.orientation.get();
            let mut cells = Vec::new();
            match loc.shape {
                0 => cells.extend(match orientation {
                    0 => [(x, y), (x, y + 1)],
                    1 => [(x, y + 1), (x + 1, y + 1)],
                    2 => [(x + 1, y), (x + 1, y + 1)],
                    _ => [(x, y), (x + 1, y)],
                }),
                1 | 3 => cells.push(match orientation {
                    0 => (x, y + 1),
                    1 => (x + 1, y + 1),
                    2 => (x + 1, y),
                    _ => (x, y),
                }),
                10 | 11 => {
                    let Some(model) = construct(asset, def, 10, orientation)? else {
                        continue;
                    };
                    let value = if def.non_flat_shading
                        || def.animation.is_some()
                        || def.morphs.is_some()
                    {
                        15
                    } else {
                        let radius = model
                            .vertices
                            .iter()
                            .map(|v| {
                                i64::from(v.x) * i64::from(v.x) + i64::from(v.z) * i64::from(v.z)
                            })
                            .max()
                            .unwrap_or(0);
                        (((radius as f64).sqrt() + 0.99) as i32 / 4).min(30)
                    };
                    let size = if orientation & 1 == 0 {
                        def.size
                    } else {
                        [def.size[1], def.size[0]]
                    };
                    for dx in 0..=usize::from(size[0]) {
                        for dy in 0..=usize::from(size[1]) {
                            if x + dx <= width && y + dy <= height {
                                let i = (x + dx) * stride + y + dy;
                                shadows[i] = shadows[i].max(value);
                            }
                        }
                    }
                    continue;
                }
                _ => {}
            }
            for (x, y) in cells {
                shadows[x * stride + y] = 50;
            }
        }
    }
    Ok(shadows)
}

pub(crate) fn location_key(loc: &StaticLocation) -> rustclient_model::LocationKey {
    use rustclient_model::LocationLayer;
    let layer = match loc.shape {
        0..=3 => LocationLayer::Boundary,
        4..=8 => LocationLayer::WallDecoration,
        22 => LocationLayer::FloorDecoration,
        _ => LocationLayer::GameObject,
    };
    rustclient_model::LocationKey {
        tile: loc.tile,
        layer,
    }
}
pub(crate) fn effective_locations(
    asset: &SceneAsset,
    inputs: &crate::SceneryInputs,
) -> Vec<StaticLocation> {
    let mut locations: Vec<_> = asset
        .assets
        .squares
        .iter()
        .flat_map(|s| s.locations.iter().copied())
        .collect();
    for (key, override_) in &inputs.locations {
        match override_.change {
            rustclient_model::LocationChange::Inherit => {}
            rustclient_model::LocationChange::Remove => {
                locations.retain(|l| location_key(l) != *key);
            }
            rustclient_model::LocationChange::Replace(def) => {
                locations.retain(|l| location_key(l) != *key);
                locations.push(StaticLocation {
                    object: def.object,
                    tile: key.tile,
                    shape: def.shape,
                    orientation: def.orientation,
                });
            }
        }
    }
    locations
}
