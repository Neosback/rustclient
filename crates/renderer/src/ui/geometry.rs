//! Clipped UI vertices, preserving reference UV and DPI transforms.
#![expect(
    clippy::cast_precision_loss,
    reason = "bounded texture and window coordinates"
)]
use super::{Quad, Region, TitleViewport, Vertex};
pub(super) fn add_quad(
    vertices: &mut Vec<Vertex>,
    quad: &Quad,
    region: Region,
    tw: u32,
    th: u32,
    viewport: TitleViewport,
) {
    let rect = quad.rect;
    let clip = quad.clip;
    if rect.width <= 0. || rect.height <= 0. {
        return;
    }
    if quad.rotation != 0 {
        add_rotated_quad(vertices, quad, region, tw, th, viewport);
        return;
    }
    let x0 = rect.x.max(clip.x);
    let y0 = rect.y.max(clip.y);
    let x1 = (rect.x + rect.width).min(clip.x + clip.width);
    let y1 = (rect.y + rect.height).min(clip.y + clip.height);
    if x1 <= x0 || y1 <= y0 {
        return;
    }
    let u = |x: f32| {
        (region.x as f32
            + (quad.uv.x + (x - rect.x) / rect.width * quad.uv.width) * region.width as f32)
            / tw as f32
    };
    let v = |y: f32| {
        (region.y as f32
            + (quad.uv.y + (y - rect.y) / rect.height * quad.uv.height) * region.height as f32)
            / th as f32
    };
    let position = |x: f32, y: f32| viewport.ndc(x, y);
    let tint = [
        normalized(quad.tint[0]),
        normalized(quad.tint[1]),
        normalized(quad.tint[2]),
        f32::from(quad.tint[3]) / 255.,
    ];
    let top_left = Vertex {
        position: position(x0, y0),
        uv: [u(x0), v(y0)],
        tint,
    };
    let top_right = Vertex {
        position: position(x1, y0),
        uv: [u(x1), v(y0)],
        tint,
    };
    let bottom_right = Vertex {
        position: position(x1, y1),
        uv: [u(x1), v(y1)],
        tint,
    };
    let bottom_left = Vertex {
        position: position(x0, y1),
        uv: [u(x0), v(y1)],
        tint,
    };
    vertices.extend_from_slice(&[
        top_left,
        top_right,
        bottom_right,
        top_left,
        bottom_right,
        bottom_left,
    ]);
}
/// Rotated sprites use a bounded eight-corner clip polygon; ordinary quads keep the fast path.
fn add_rotated_quad(
    vertices: &mut Vec<Vertex>,
    quad: &Quad,
    region: Region,
    tw: u32,
    th: u32,
    viewport: TitleViewport,
) {
    let rect = quad.rect;
    let center = quad
        .rotation_center
        .unwrap_or([rect.x + rect.width / 2., rect.y + rect.height / 2.]);
    let radians = f32::from(quad.rotation) * std::f32::consts::TAU / 65_536.;
    let (sin, cos) = radians.sin_cos();
    let tint = [
        normalized(quad.tint[0]),
        normalized(quad.tint[1]),
        normalized(quad.tint[2]),
        f32::from(quad.tint[3]) / 255.,
    ];
    let mut polygon = Vec::with_capacity(8);
    for (x, y, u, v) in [
        (rect.x, rect.y, 0., 0.),
        (rect.x + rect.width, rect.y, 1., 0.),
        (rect.x + rect.width, rect.y + rect.height, 1., 1.),
        (rect.x, rect.y + rect.height, 0., 1.),
    ] {
        let delta = [x - center[0], y - center[1]];
        polygon.push(Vertex {
            position: [
                center[0] + delta[0] * cos - delta[1] * sin,
                center[1] + delta[0] * sin + delta[1] * cos,
            ],
            uv: [
                (region.x as f32 + (quad.uv.x + u * quad.uv.width) * region.width as f32)
                    / tw as f32,
                (region.y as f32 + (quad.uv.y + v * quad.uv.height) * region.height as f32)
                    / th as f32,
            ],
            tint,
        });
    }
    for (axis, boundary, positive) in [
        (0, quad.clip.x, true),
        (0, quad.clip.x + quad.clip.width, false),
        (1, quad.clip.y, true),
        (1, quad.clip.y + quad.clip.height, false),
    ] {
        let mut clipped = Vec::with_capacity(8);
        if let Some(mut previous) = polygon.last().copied() {
            let inside = |point: Vertex| {
                if positive {
                    point.position[axis] >= boundary
                } else {
                    point.position[axis] <= boundary
                }
            };
            for current in polygon {
                if inside(previous) != inside(current) {
                    let t = (boundary - previous.position[axis])
                        / (current.position[axis] - previous.position[axis]);
                    clipped.push(Vertex {
                        position: std::array::from_fn(|i| {
                            previous.position[i] + t * (current.position[i] - previous.position[i])
                        }),
                        uv: std::array::from_fn(|i| {
                            previous.uv[i] + t * (current.uv[i] - previous.uv[i])
                        }),
                        tint,
                    });
                }
                if inside(current) {
                    clipped.push(current);
                }
                previous = current;
            }
        }
        polygon = clipped;
    }
    for point in &mut polygon {
        point.position = viewport.ndc(point.position[0], point.position[1]);
    }
    for index in 1..polygon.len().saturating_sub(1) {
        vertices.extend_from_slice(&[polygon[0], polygon[index], polygon[index + 1]]);
    }
}

fn normalized(channel: u8) -> f32 {
    f32::from(channel) / 255.
}

#[cfg(test)]
mod tests {
    use super::*;
    use rustclient_game_ui::Rect;
    use rustclient_game_ui::UiImageId;

    #[test]
    fn clipping_preserves_mirrored_uvs_and_hidpi_positions() {
        let quad = Quad {
            image: UiImageId::Background,
            rect: Rect::new(-10., 0., 20., 20.),
            uv: Rect::new(1., 0., -1., 1.),
            tint: [255; 4],
            clip: Rect::new(0., 0., 765., 503.),
            rotation: 0,
            rotation_center: None,
        };
        let region = Region {
            x: 0,
            y: 0,
            width: 20,
            height: 20,
        };
        let viewport = TitleViewport {
            canvas_width: 765,
            physical_width: 1800,
            physical_height: 1100,
            scale: 2.,
            stretch: None,
        };
        let mut vertices = Vec::new();
        add_quad(&mut vertices, &quad, region, 20, 20, viewport);
        assert_eq!(vertices.len(), 6);
        assert!((vertices[0].uv[0] - 0.5).abs() < f32::EPSILON);
        assert!(vertices[0].uv[1].abs() < f32::EPSILON);
        assert!(vertices[1].uv.iter().all(|v| v.abs() < f32::EPSILON));
        let expected = (viewport.origin_x() * 2.) / 1800. * 2. - 1.;
        assert!((vertices[0].position[0] - expected).abs() < 0.00001);
        let mut outside = quad;
        outside.rect = Rect::new(-30., 0., 20., 20.);
        vertices.clear();
        add_quad(&mut vertices, &outside, region, 20, 20, viewport);
        assert!(vertices.is_empty());
    }
}
