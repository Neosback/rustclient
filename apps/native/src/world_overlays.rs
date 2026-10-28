//! World-anchored plugin overlay commands: tile outlines, actor hulls and text above actors.
//!
//! Plugins name a tile or an actor; this module projects it through the same gameplay camera and
//! posed actor vertices the scene renderer draws, so nothing here is read back from the GPU. A tile is
//! outlined through the terrain height at its four corners (`Perspective.getCanvasTilePoly`), an actor
//! through the convex hull of its projected model vertices (`Actor.getConvexHull`), and text sits
//! centred at the top of the actor's model plus the requested offset (`Actor.getCanvasTextLocation`).

use crate::actor_overlays::{WorldView, world_view};
use crate::plugins::{color, draw_line};
use crate::scene::SceneView;
use rustclient_cache::TitleAssets;
use rustclient_game_ui::{Rect, UiFrame, draw_text, text_width};
use rustclient_model::Plane;
use rustclient_plugin_api::{
    ActorAnchor, ActorRef, HorizontalAlign, LayeredOverlay, OverlayCommand,
};
use rustclient_scene::{ActorLabelAnchor, ActorLabelId};

const BLACK: [u8; 4] = [0, 0, 0, 255];
/// Models project to a few thousand vertices; a hull of more points than this is not worth drawing.
const MAX_HULL_POINTS: usize = 16_384;

pub fn project(
    scene: &SceneView,
    assets: &TitleAssets,
    commands: &[LayeredOverlay],
    frame: &mut UiFrame,
    visibility: &rustclient_scene::SceneVisibility,
) {
    if !commands.iter().any(|item| {
        matches!(
            item.command,
            OverlayCommand::Tile { .. }
                | OverlayCommand::ActorHull { .. }
                | OverlayCommand::ActorText { .. }
        )
    }) {
        return;
    }
    let (Some(view), Some(loaded)) = (world_view(scene, frame), &scene.loaded) else {
        return;
    };
    for item in commands {
        match &item.command {
            OverlayCommand::Tile {
                tile,
                size,
                argb,
                fill_argb,
                width,
            } => {
                if let Some(polygon) = tile_polygon(&view, loaded, tile, *size, visibility) {
                    paint(frame, view.clip, &polygon, [*argb, *fill_argb], *width);
                }
            }
            OverlayCommand::ActorHull {
                actor,
                argb,
                fill_argb,
                width,
            } => {
                let Some(anchor) = anchor(scene, *actor, visibility) else {
                    continue;
                };
                let hull = actor_hull(&view, scene, anchor);
                if hull.len() >= 3 {
                    paint(frame, view.clip, &hull, [*argb, *fill_argb], *width);
                }
            }
            OverlayCommand::ActorText {
                actor,
                text,
                z_offset,
                argb,
                anchor: place,
                align,
                x_offset,
            } => {
                let Some(anchor) = anchor(scene, *actor, visibility) else {
                    continue;
                };
                let [x, top, z] = anchor.position;
                let base = match place {
                    ActorAnchor::Top => top,
                    ActorAnchor::Middle => {
                        let feet = model_feet(scene, anchor).unwrap_or(top);
                        top + (feet - top) / 2
                    }
                };
                let Some(point) = screen(&view, [x, base - z_offset, z]) else {
                    continue;
                };
                let edge = point.0 + x_offset;
                #[expect(
                    clippy::cast_precision_loss,
                    reason = "screen coordinates are bounded by the viewport"
                )]
                let (width, edge, baseline) =
                    (text_width(assets, text), edge as f32, point.1 as f32);
                let left = match align {
                    HorizontalAlign::Left => edge,
                    HorizontalAlign::Center => edge - (width / 2.).floor(),
                    HorizontalAlign::Right => edge - width,
                };
                draw_text(
                    frame,
                    assets,
                    text,
                    left + 1.,
                    baseline + 1.,
                    BLACK,
                    view.clip,
                );
                draw_text(frame, assets, text, left, baseline, color(*argb), view.clip);
            }
            _ => {}
        }
    }
}

/// The posed model anchor of `actor`, if it is in the scene and admitted by the current scene visibility.
fn anchor<'a>(
    scene: &'a SceneView,
    actor: ActorRef,
    visibility: &rustclient_scene::SceneVisibility,
) -> Option<&'a ActorLabelAnchor> {
    scene
        .entity_geometry
        .actor_labels
        .iter()
        .find(|anchor| match (anchor.actor, actor) {
            (ActorLabelId::Player(index, _), ActorRef::Player(wanted)) => {
                index.get() == u32::from(wanted)
            }
            (ActorLabelId::Npc(index, _), ActorRef::Npc(wanted)) => {
                index.get() == u32::from(wanted)
            }
            _ => false,
        })
        .filter(|anchor| {
            scene
                .entity_geometry
                .vertices
                .get(anchor.vertices.start)
                .is_some_and(|vertex| visibility.shows_vertex(vertex))
        })
}

/// Project a scene-local point to frame coordinates; points behind the camera are absent.
fn screen(view: &WorldView, point: [i32; 3]) -> Option<(i32, i32)> {
    let projected = view.camera.project_point_unclipped(view.viewport, point)?;
    #[expect(
        clippy::cast_possible_truncation,
        reason = "projected pixels are clamped to a range an i32 holds"
    )]
    let rounded = |value: f32| value.clamp(-1.0e6, 1.0e6) as i32;
    Some((
        rounded(projected[0] + view.origin[0]),
        rounded(projected[1] + view.origin[1]),
    ))
}

fn tile_polygon(
    view: &WorldView,
    loaded: &rustclient_application::LoadedScene,
    tile: &rustclient_plugin_api::WorldPoint,
    size: u8,
    visibility: &rustclient_scene::SceneVisibility,
) -> Option<Vec<(i32, i32)>> {
    if tile.plane > visibility.top_level
        || !visibility.shows_tile(loaded.asset.draw_tile([tile.x, tile.y]))
    {
        return None;
    }
    let plane = Plane::new(tile.plane)?;
    let origin = loaded.asset.descriptor().origin();
    let base = [(tile.x - origin.x) * 128, (tile.y - origin.y) * 128];
    let edge = i32::from(size.max(1));
    // Walk the perimeter one tile corner at a time so the outline follows the terrain.
    let corners = (0..edge)
        .map(|step| [step, 0])
        .chain((0..edge).map(|step| [edge, step]))
        .chain((0..edge).map(|step| [edge - step, edge]))
        .chain((0..edge).map(|step| [0, edge - step]));
    corners
        .map(|[dx, dz]| {
            let fine = [base[0] + dx * 128, base[1] + dz * 128];
            let height = loaded.asset.ground_height(plane, fine).ok()?;
            screen(view, [fine[0], height, fine[1]])
        })
        .collect()
}

/// The lowest point of the actor's posed model (largest scene y, since y points up negatively).
fn model_feet(scene: &SceneView, anchor: &ActorLabelAnchor) -> Option<i32> {
    scene
        .entity_geometry
        .vertices
        .get(anchor.vertices.clone())?
        .iter()
        .map(|vertex| i32::from(vertex.position[1]))
        .max()
}

fn actor_hull(view: &WorldView, scene: &SceneView, anchor: &ActorLabelAnchor) -> Vec<(i32, i32)> {
    let vertices = &scene.entity_geometry.vertices;
    let Some(range) = vertices.get(anchor.vertices.clone()) else {
        return Vec::new();
    };
    let mut points: Vec<(i32, i32)> = range
        .iter()
        .take(MAX_HULL_POINTS)
        .filter_map(|vertex| {
            screen(
                view,
                [
                    i32::from(vertex.position[0]),
                    i32::from(vertex.position[1]),
                    i32::from(vertex.position[2]),
                ],
            )
        })
        .collect();
    convex_hull(&mut points)
}

fn paint(
    frame: &mut UiFrame,
    clip: Rect,
    polygon: &[(i32, i32)],
    [outline, fill]: [u32; 2],
    width: u8,
) {
    // The source draws the stroke first and the fill over it.
    for (index, from) in polygon.iter().enumerate() {
        let to = polygon[(index + 1) % polygon.len()];
        thick_line(frame, clip, *from, to, outline, i32::from(width));
    }
    if fill >> 24 != 0 {
        for (y, x0, x1) in fill_spans(polygon) {
            #[expect(
                clippy::cast_precision_loss,
                reason = "spans are clamped to screen-sized values"
            )]
            let rect = Rect::new(x0 as f32, y as f32, (x1 - x0 + 1) as f32, 1.);
            frame.fill_rect(rect, color(fill), clip);
        }
    }
}

/// A Bresenham line drawn with `width` by `width` squares centred on each step.
fn thick_line(
    frame: &mut UiFrame,
    clip: Rect,
    from: (i32, i32),
    to: (i32, i32),
    argb: u32,
    width: i32,
) {
    if width <= 1 {
        draw_line(frame, clip, from, to, argb);
        return;
    }
    let (mut x, mut y) = from;
    let (dx, dy) = ((to.0 - x).abs(), -(to.1 - y).abs());
    let (sx, sy) = (if x < to.0 { 1 } else { -1 }, if y < to.1 { 1 } else { -1 });
    let mut error = dx + dy;
    let half = width / 2;
    for _ in 0..4096 {
        #[expect(
            clippy::cast_precision_loss,
            reason = "clamped screen coordinates and a width of at most eight"
        )]
        let square = Rect::new(
            (x - half) as f32,
            (y - half) as f32,
            width as f32,
            width as f32,
        );
        frame.fill_rect(square, color(argb), clip);
        if (x, y) == to {
            break;
        }
        let doubled = 2 * error;
        if doubled >= dy {
            error += dy;
            x += sx;
        }
        if doubled <= dx {
            error += dx;
            y += sy;
        }
    }
}

/// Andrew's monotone chain: the hull in counter-clockwise order, without repeated points.
pub(crate) fn convex_hull(points: &mut Vec<(i32, i32)>) -> Vec<(i32, i32)> {
    points.sort_unstable();
    points.dedup();
    if points.len() < 3 {
        return points.clone();
    }
    let cross = |o: (i32, i32), a: (i32, i32), b: (i32, i32)| {
        i64::from(a.0 - o.0) * i64::from(b.1 - o.1) - i64::from(a.1 - o.1) * i64::from(b.0 - o.0)
    };
    let mut hull: Vec<(i32, i32)> = Vec::with_capacity(points.len());
    for &point in points.iter() {
        while hull.len() >= 2 && cross(hull[hull.len() - 2], hull[hull.len() - 1], point) <= 0 {
            hull.pop();
        }
        hull.push(point);
    }
    let lower = hull.len() + 1;
    for &point in points.iter().rev().skip(1) {
        while hull.len() >= lower && cross(hull[hull.len() - 2], hull[hull.len() - 1], point) <= 0 {
            hull.pop();
        }
        hull.push(point);
    }
    hull.pop();
    hull
}

/// Horizontal spans `(y, x0, x1)` covering a convex polygon, one per scanline.
pub(crate) fn fill_spans(polygon: &[(i32, i32)]) -> Vec<(i32, i32, i32)> {
    let (Some(top), Some(bottom)) = (
        polygon.iter().map(|point| point.1).min(),
        polygon.iter().map(|point| point.1).max(),
    ) else {
        return Vec::new();
    };
    let mut spans = Vec::new();
    for y in top..=bottom {
        let mut left = i32::MAX;
        let mut right = i32::MIN;
        for (index, a) in polygon.iter().enumerate() {
            let b = polygon[(index + 1) % polygon.len()];
            if (a.1 > y) == (b.1 > y) && a.1 != y && b.1 != y {
                continue;
            }
            if a.1 == b.1 {
                left = left.min(a.0.min(b.0));
                right = right.max(a.0.max(b.0));
                continue;
            }
            if y < a.1.min(b.1) || y > a.1.max(b.1) {
                continue;
            }
            let x = a.0 + (b.0 - a.0) * (y - a.1) / (b.1 - a.1);
            left = left.min(x);
            right = right.max(x);
        }
        if left <= right {
            spans.push((y, left, right));
        }
    }
    spans
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn the_hull_drops_interior_and_collinear_points() {
        let mut points = vec![
            (0, 0),
            (10, 0),
            (10, 10),
            (0, 10),
            (5, 5),
            (5, 0),
            (3, 7),
            (10, 0),
        ];
        let hull = convex_hull(&mut points);
        let mut sorted = hull.clone();
        sorted.sort_unstable();
        assert_eq!(sorted, [(0, 0), (0, 10), (10, 0), (10, 10)]);
    }

    #[test]
    fn degenerate_input_is_returned_as_is() {
        assert!(convex_hull(&mut Vec::new()).is_empty());
        assert_eq!(convex_hull(&mut vec![(1, 1), (1, 1)]), [(1, 1)]);
    }

    #[test]
    fn a_square_fills_every_row_edge_to_edge() {
        let spans = fill_spans(&[(0, 0), (4, 0), (4, 4), (0, 4)]);
        assert_eq!(spans.len(), 5);
        assert!(spans.iter().all(|&(_, x0, x1)| (x0, x1) == (0, 4)));
    }

    #[test]
    fn a_triangle_narrows_toward_its_apex() {
        let spans = fill_spans(&[(0, 0), (10, 0), (5, 10)]);
        assert_eq!(spans.first(), Some(&(0, 0, 10)));
        let widths: Vec<i32> = spans.iter().map(|&(_, x0, x1)| x1 - x0).collect();
        assert!(widths.windows(2).all(|pair| pair[1] <= pair[0]));
        assert_eq!(spans.last().map(|&(y, ..)| y), Some(10));
    }
}
