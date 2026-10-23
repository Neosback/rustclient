use crate::{
    Result, SceneAsset, SceneError, WorldMesh, color,
    mesh::visible,
    shapes::{FACE_CODES, VERTEX_CODES},
};
use rustclient_model::Plane;
#[expect(
    clippy::cast_possible_truncation,
    clippy::cast_possible_wrap,
    reason = "reference slope square root truncates to integer"
)]
fn lighting(
    asset: &SceneAsset,
    plane: Plane,
    shadows: &[i32],
    width: usize,
    height: usize,
) -> Result<Vec<i32>> {
    let mut out = vec![0; (width + 1) * (height + 1)];
    let stride = height + 1;
    let origin = asset.descriptor.origin();
    for x in 1..width - 1 {
        for y in 1..height - 1 {
            let wx = origin.x + x as i32;
            let wy = origin.y + y as i32;
            let dx = asset.elevation(plane, wx - 1, wy)? - asset.elevation(plane, wx + 1, wy)?;
            let dz = asset.elevation(plane, wx, wy - 1)? - asset.elevation(plane, wx, wy + 1)?;
            let len = f64::from(dx * dx + dz * dz + 65536).sqrt() as i32;
            let sun = (((dx << 8) / len) * -50 + ((dz << 8) / len) * -50 + (65536 / len) * -10)
                / 213
                + 96;
            let i = x * stride + y;
            out[i] = sun
                - (shadows[i + 1] >> 3)
                - (shadows[i - stride] >> 2)
                - (shadows[i - 1] >> 2)
                - (shadows[i + stride] >> 3)
                - (shadows[i] >> 1);
        }
    }
    Ok(out)
}
#[expect(
    clippy::too_many_lines,
    clippy::cast_possible_wrap,
    clippy::cast_possible_truncation,
    reason = "bounded 192-tile reference terrain construction arithmetic"
)]
pub(crate) fn build(
    asset: &SceneAsset,
    mesh: &mut WorldMesh,
    selected: Plane,
    inputs: &crate::SceneryInputs,
) -> Result<()> {
    let d = asset.descriptor;
    let origin = d.origin();
    let width = d.width() as usize;
    let height = d.height() as usize;
    for p in 0..4 {
        let plane = Plane::new(p).expect("bounded plane");
        let shadows = super::locations::shadows(asset, plane, width, height, inputs)?;
        let light = lighting(asset, plane, &shadows, width, height)?;
        let floors = underlay_floors(asset, plane, width, height);
        for x in 1..width.saturating_sub(1) {
            for y in 1..height.saturating_sub(1) {
                let wx = origin.x + x as i32;
                let wy = origin.y + y as i32;
                if !visible(asset, plane, wx, wy, selected) {
                    continue;
                }
                let tile = asset
                    .tile(plane, wx, wy)
                    .ok_or_else(|| SceneError("missing terrain tile".into()))?;
                if tile.underlay.is_none() && tile.overlay.is_none() {
                    continue;
                }
                let underlay =
                    blended_underlay(&floors, width, height, x, y, tile.underlay.is_some());
                let overlay = tile
                    .overlay
                    .map(|o| {
                        asset
                            .assets
                            .overlays
                            .get(&o.id)
                            .ok_or_else(|| SceneError("missing overlay".into()))
                    })
                    .transpose()?;
                let texture = overlay.and_then(|o| o.texture);
                let overlay_hsl = overlay.map_or(-2, |o| {
                    if texture.is_some() {
                        -1
                    } else if !o.visible {
                        -2
                    } else {
                        color::overlay(o.rgb)
                    }
                });
                let heights = [
                    asset.elevation(plane, wx, wy)?,
                    asset.elevation(plane, wx + 1, wy)?,
                    asset.elevation(plane, wx + 1, wy + 1)?,
                    asset.elevation(plane, wx, wy + 1)?,
                ]
                .map(|v| -v);
                let stride = height + 1;
                let i = x * stride + y;
                let lights = [
                    light[i],
                    light[i + stride],
                    light[i + stride + 1],
                    light[i + 1],
                ];
                let uc = lights.map(|l| color::light(underlay, l));
                let oc = lights.map(|l| color::light(overlay_hsl, l));
                let (shape, rotation) = tile
                    .overlay
                    .map_or((0, 0), |o| (usize::from(o.shape) + 1, o.rotation));
                let codes = VERTEX_CODES
                    .get(shape)
                    .ok_or_else(|| SceneError("unsupported floor topology".into()))?;
                let mut points = [[0; 3]; 6];
                let mut under = [0; 6];
                let mut over = [0; 6];
                let mut uvs = [[0.; 2]; 6];
                for (index, &raw) in codes.iter().enumerate() {
                    let code = rotate_code(raw, rotation);
                    let (px, py, h) = vertex(code, heights)?;
                    points[index] = [
                        x as i32 * 128 + i32::from(px),
                        h,
                        y as i32 * 128 + i32::from(py),
                    ];
                    under[index] = vertex(code, uc)?.2;
                    over[index] = vertex(code, oc)?.2;
                    uvs[index] = [f32::from(px) / 128., f32::from(py) / 128.];
                }
                let rows: &[u8] = if shape == 0 {
                    &[0, 2, 3, 1, 0, 0, 1, 3]
                } else if shape == 1 {
                    &[1, 2, 3, 1, 1, 0, 1, 3]
                } else {
                    FACE_CODES[shape]
                };
                for face in rows.chunks_exact(4) {
                    let indices = std::array::from_fn::<_, 3, _>(|i| {
                        let n = face[i + 1];
                        usize::from(if n < 4 && shape > 1 {
                            n.wrapping_sub(rotation) & 3
                        } else {
                            n
                        })
                    });
                    let colors = indices.map(|i| if face[0] == 0 { under[i] } else { over[i] });
                    if colors[0] == color::SKIP {
                        continue;
                    }
                    let points = indices.map(|i| points[i]);
                    let uv = indices.map(|i| uvs[i]);
                    mesh.geometry.triangle(
                        points,
                        colors,
                        if face[0] == 0 { None } else { texture },
                        uv,
                        0,
                        0,
                    )?;
                    mesh.stats.terrain_triangles += 1;
                }
            }
        }
    }
    Ok(())
}
/// Per-tile underlay HSL terms for one plane, indexed `x * height + y`.
#[expect(
    clippy::cast_possible_truncation,
    clippy::cast_possible_wrap,
    reason = "bounded 192-tile reference terrain coordinates"
)]
pub(crate) fn underlay_floors(
    asset: &SceneAsset,
    plane: Plane,
    width: usize,
    height: usize,
) -> Vec<Option<color::UnderlayHsl>> {
    let origin = asset.descriptor.origin();
    let mut floors = vec![None; width * height];
    for x in 0..width {
        for y in 0..height {
            floors[x * height + y] = asset
                .tile(plane, origin.x + x as i32, origin.y + y as i32)
                .and_then(|t| t.underlay)
                .and_then(|id| asset.assets.underlays.get(&id))
                .map(|m| color::underlay(m.rgb));
        }
    }
    floors
}
/// The reference floor blend over the window `[x-4, x+5]` by `[y-4, y+5]`; `-2` when the tile
/// has no underlay to paint.
pub(crate) fn blended_underlay(
    floors: &[Option<color::UnderlayHsl>],
    width: usize,
    height: usize,
    x: usize,
    y: usize,
    has_underlay: bool,
) -> i32 {
    let mut sums = [0; 5];
    // Sliding-window add(x+5), remove(x-5): exactly [x-4,x+5].
    for bx in x.saturating_sub(4)..=(x + 5).min(width - 1) {
        for by in y.saturating_sub(4)..=(y + 5).min(height - 1) {
            if let Some(f) = floors[bx * height + by] {
                for (sum, v) in
                    sums.iter_mut()
                        .zip([f.hue, f.saturation, f.lightness, f.multiplier, 1])
                {
                    *sum += v;
                }
            }
        }
    }
    if has_underlay && sums[3] != 0 && sums[4] != 0 {
        color::pack(
            sums[0] * 256 / sums[3],
            sums[1] / sums[4],
            sums[2] / sums[4],
        )
    } else {
        -2
    }
}
fn vertex(code: u8, h: [i32; 4]) -> Result<(u8, u8, i32)> {
    Ok(match code {
        1 => (0, 0, h[0]),
        2 => (64, 0, (h[0] + h[1]) >> 1),
        3 => (128, 0, h[1]),
        4 => (128, 64, (h[1] + h[2]) >> 1),
        5 => (128, 128, h[2]),
        6 => (64, 128, (h[2] + h[3]) >> 1),
        7 => (0, 128, h[3]),
        8 => (0, 64, (h[3] + h[0]) >> 1),
        9 => (64, 32, (h[0] + h[1]) >> 1),
        10 => (96, 64, (h[1] + h[2]) >> 1),
        11 => (64, 96, (h[2] + h[3]) >> 1),
        12 => (32, 64, (h[3] + h[0]) >> 1),
        13 => (32, 32, h[0]),
        14 => (96, 32, h[1]),
        15 => (96, 96, h[2]),
        16 => (32, 96, h[3]),
        _ => return Err(SceneError("invalid floor vertex".into())),
    })
}

fn rotate_code(raw: u8, rotation: u8) -> u8 {
    if raw <= 8 && raw & 1 == 0 {
        ((raw - 1).wrapping_sub(rotation * 2) & 7) + 1
    } else if raw > 8 && raw <= 12 {
        ((raw - 9).wrapping_sub(rotation) & 3) + 9
    } else if raw > 12 {
        ((raw - 13).wrapping_sub(rotation) & 3) + 13
    } else {
        raw
    }
}
