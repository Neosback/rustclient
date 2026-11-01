//! What picking reads of a triangle soup, and the compact copy the pick index keeps of it.
//!
//! Picking needs three corner positions per triangle, whether the triangle is fully transparent,
//! and the render tag and draw tile of its first corner (the only corner admission ever tests).
//! That is 6 bytes per vertex plus 8 per triangle instead of the 24-byte [`SceneVertex`], so the
//! uploaded static mesh can drop its triangles and keep picking.
use crate::{DrawTile, SceneGeometry, SceneVisibility};

/// Triangle soup as picking reads it. A vertex argument is always the first corner of a triangle.
pub(crate) trait Triangles {
    /// Vertices, including a trailing partial triangle.
    fn vertex_count(&self) -> usize;
    fn position(&self, vertex: usize) -> [i16; 3];
    /// Admission of the triangle that starts at `vertex`.
    fn shows(&self, vertex: usize, visibility: &SceneVisibility) -> bool;
    /// Every corner of the triangle that starts at `vertex` has alpha 255: it occludes nothing.
    fn invisible(&self, vertex: usize) -> bool;
}

impl Triangles for SceneGeometry {
    fn vertex_count(&self) -> usize {
        self.vertices.len()
    }
    fn position(&self, vertex: usize) -> [i16; 3] {
        let [x, y, z, _] = self.vertices[vertex].position;
        [x, y, z]
    }
    fn shows(&self, vertex: usize, visibility: &SceneVisibility) -> bool {
        visibility.shows_vertex(&self.vertices[vertex])
    }
    fn invisible(&self, vertex: usize) -> bool {
        self.vertices[vertex..vertex + 3]
            .iter()
            .all(|v| v.abhsl >> 24 == 255)
    }
}

#[derive(Clone, Copy)]
struct Mark {
    tile: DrawTile,
    tag: u16,
    invisible: bool,
}

/// The picking copy of a static geometry, owned by [`crate::ScenePickIndex`].
pub(crate) struct PickTriangles {
    positions: Vec<[i16; 3]>,
    /// One per triangle, from its first corner.
    marks: Vec<Mark>,
}

impl PickTriangles {
    pub fn new(geometry: &SceneGeometry) -> Self {
        let positions = geometry
            .vertices
            .iter()
            .map(|v| [v.position[0], v.position[1], v.position[2]])
            .collect();
        let marks = geometry
            .vertices
            .chunks(3)
            .map(|triangle| Mark {
                tile: triangle[0].draw_tile,
                tag: triangle[0].render_tag(),
                invisible: triangle.len() == 3 && triangle.iter().all(|v| v.abhsl >> 24 == 255),
            })
            .collect();
        Self { positions, marks }
    }
    pub fn bytes(&self) -> usize {
        self.positions.capacity() * size_of::<[i16; 3]>()
            + self.marks.capacity() * size_of::<Mark>()
    }
}

impl Triangles for PickTriangles {
    fn vertex_count(&self) -> usize {
        self.positions.len()
    }
    fn position(&self, vertex: usize) -> [i16; 3] {
        self.positions[vertex]
    }
    fn shows(&self, vertex: usize, visibility: &SceneVisibility) -> bool {
        let mark = self.marks[vertex / 3];
        visibility.shows_mark(mark.tag, mark.tile)
    }
    fn invisible(&self, vertex: usize) -> bool {
        self.marks[vertex / 3].invisible
    }
}
