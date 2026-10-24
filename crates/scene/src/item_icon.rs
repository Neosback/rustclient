//! Bounded CPU projection of cache inventory models for widget image assets.
//! This initial untextured icon path does not establish classic rasterizer parity.
use crate::{Result, SceneError};
use rustclient_cache::{
    GroupId, Image, IndexId, Source,
    world::{ItemDefinition, StaticModel},
};
#[expect(
    clippy::cast_sign_loss,
    reason = "bounded 36x32 raster coordinates and byte colors"
)]
/// Project a small untextured icon from immutable model geometry.
/// # Errors
/// Rejects invalid model indexes or inconsistent image storage.
pub fn item_icon(def: &ItemDefinition, model: &StaticModel) -> Result<Image> {
    let mut points = Vec::with_capacity(model.vertices.len());
    let angle = |a: u16| f64::from(a) * std::f64::consts::TAU / 2048.;
    let (sin_y, cos_y) = angle(def.icon_angles[1]).sin_cos();
    let (sin_z, cos_z) = angle(def.icon_angles[2]).sin_cos();
    let (sin_x, cos_x) = angle(def.icon_angles[0]).sin_cos();
    let depth = f64::from(def.icon_zoom).max(1.);
    let min_y = model.vertices.iter().map(|v| v.y).min().unwrap_or(0);
    let max_y = model.vertices.iter().map(|v| v.y).max().unwrap_or(0);
    let center = f64::midpoint(f64::from(min_y), f64::from(max_y));
    for v in &model.vertices {
        let (x, y, z) = (f64::from(v.x), f64::from(v.y) - center, f64::from(v.z));
        let (x, z) = (x * cos_y + z * sin_y, z * cos_y - x * sin_y);
        let (x, y) = (x * cos_z - y * sin_z, y * cos_z + x * sin_z);
        let (y, z) = (y * cos_x - z * sin_x, z * cos_x + y * sin_x + depth);
        points.push([
            18. + (x + f64::from(def.icon_offset[0])) * 512. / z.max(1.),
            16. + (y + f64::from(def.icon_offset[1])) * 512. / z.max(1.),
            z,
        ]);
    }
    let mut pixels = vec![0_u8; 36 * 32 * 4];
    let mut zbuffer = vec![f64::INFINITY; 36 * 32];
    for face in &model.faces {
        let mut triangle = [[0.; 3]; 3];
        for (i, index) in face.vertices.iter().enumerate() {
            triangle[i] = *points
                .get(usize::try_from(*index).map_err(|_| SceneError("icon vertex index".into()))?)
                .ok_or_else(|| SceneError("icon vertex index".into()))?;
        }
        if triangle.iter().any(|v| v[2] <= 1.) {
            continue;
        }
        let color = def
            .recolors
            .iter()
            .find(|(from, _)| *from == face.color)
            .map_or(face.color, |(_, to)| *to);
        let rgba = hsl(color, 255_u8.wrapping_sub(face.alpha.cast_unsigned()));
        let edge = |a: [f64; 3], b: [f64; 3], x: f64, y: f64| {
            (x - a[0]) * (b[1] - a[1]) - (y - a[1]) * (b[0] - a[0])
        };
        let area = edge(triangle[0], triangle[1], triangle[2][0], triangle[2][1]);
        if area.abs() < 0.0001 {
            continue;
        }
        for y in 0..32 {
            for x in 0..36 {
                let a = edge(
                    triangle[1],
                    triangle[2],
                    f64::from(x) + 0.5,
                    f64::from(y) + 0.5,
                ) / area;
                let b = edge(
                    triangle[2],
                    triangle[0],
                    f64::from(x) + 0.5,
                    f64::from(y) + 0.5,
                ) / area;
                let c = 1. - a - b;
                if a < 0. || b < 0. || c < 0. {
                    continue;
                }
                let depth = a * triangle[0][2] + b * triangle[1][2] + c * triangle[2][2];
                let index = (y * 36 + x) as usize;
                if depth < zbuffer[index] {
                    zbuffer[index] = depth;
                    pixels[index * 4..index * 4 + 4].copy_from_slice(&rgba);
                }
            }
        }
    }
    Image::new(
        36,
        32,
        pixels,
        Source::group(
            IndexId(7),
            GroupId(def.icon_model.map_or(0, rustclient_model::ModelId::get)),
        ),
    )
    .map_err(|e| SceneError(e.to_string()))
}
#[expect(
    clippy::cast_possible_truncation,
    clippy::cast_sign_loss,
    clippy::many_single_char_names,
    reason = "normalized HSL to clamped byte channels"
)]
fn hsl(packed: u16, alpha: u8) -> [u8; 4] {
    let h = f64::from(packed >> 10) / 64.;
    let s = f64::from((packed >> 7) & 7) / 8. + 0.0625;
    let l = f64::from(packed & 127) / 128.;
    let channel = |offset: f64| {
        let k = (offset + h * 12.) % 12.;
        let a = s * l.min(1. - l);
        ((l - a * (-1_f64).max((k - 3.).min(9. - k).min(1.))) * 255.).clamp(0., 255.) as u8
    };
    [channel(0.), channel(8.), channel(4.), alpha]
}
