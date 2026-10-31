//! Retained widget vertices and transient overlays in one bounded GPU buffer.
use super::{
    DrawSource, Flames, MAX_QUADS, Region, RenderError, TitleViewport, UiFrame, UiImageId, Vertex,
    geometry::add_quad,
};
use rustclient_game_ui::{Quad, RetainedUi};
use std::{collections::BTreeMap, ops::Range};

pub(super) struct Draw {
    pub first: u32,
    pub count: u32,
    pub source: DrawSource,
}

#[derive(Clone, Copy)]
pub(super) struct ImageLayout<'a> {
    pub regions: &'a BTreeMap<UiImageId, Region>,
    pub atlas: [u32; 2],
    pub dynamic: [[u32; 2]; 4],
}
impl ImageLayout<'_> {
    fn resolve(self, id: UiImageId) -> Result<(DrawSource, Region, [u32; 2]), RenderError> {
        let (source, size) = match id {
            UiImageId::Flames => (DrawSource::Flames, [Flames::WIDTH, Flames::HEIGHT]),
            UiImageId::Minimap => (DrawSource::Minimap, self.dynamic[0]),
            UiImageId::Compass => (DrawSource::Compass, self.dynamic[1]),
            UiImageId::WorldMap => (DrawSource::WorldMap, self.dynamic[2]),
            UiImageId::WorldMapOverview => (DrawSource::WorldMapOverview, self.dynamic[3]),
            _ => {
                let region = self
                    .regions
                    .get(&id)
                    .copied()
                    .ok_or(RenderError::Ui("unknown UI image"))?;
                return Ok((DrawSource::Atlas, region, self.atlas));
            }
        };
        Ok((
            source,
            Region {
                x: 0,
                y: 0,
                width: size[0],
                height: size[1],
            },
            size,
        ))
    }
}

struct PreparedKey {
    content: RetainedUi,
    viewport: TitleViewport,
    dynamic: [[u32; 2]; 4],
    boundary: usize,
}

/// Physical vertices: widgets before/after the world, then transient world and UI.
/// Draw order: widgets before, world overlays, widgets after, transient UI. Keeping
/// these batches separate permits a suffix-only upload without copying base geometry.
pub(super) struct PreparedUi {
    pub vertices: Vec<Vertex>,
    draws: [Vec<Draw>; 4],
    key: Option<PreparedKey>,
    base_vertices: usize,
    base_quads: u32,
    quads: u32,
    prepared_quads: u32,
    retained_quads: u32,
    unchanged: bool,
    had_transients: bool,
}
impl Default for PreparedUi {
    fn default() -> Self {
        Self {
            // Preserve the existing scratch budget. Clipped rotated quads can emit
            // more than six vertices; lazy Vec growth could overshoot this capacity.
            vertices: Vec::with_capacity(MAX_QUADS * 6),
            draws: std::array::from_fn(|_| Vec::new()),
            key: None,
            base_vertices: 0,
            base_quads: 0,
            quads: 0,
            prepared_quads: 0,
            retained_quads: 0,
            unchanged: false,
            had_transients: false,
        }
    }
}
impl PreparedUi {
    pub fn invalidate(&mut self) {
        self.key = None;
    }

    pub fn draws(&self) -> impl Iterator<Item = &Draw> {
        self.draws.iter().flatten()
    }

    pub const fn quads(&self) -> u32 {
        self.quads
    }

    pub const fn prepared_quads(&self) -> u32 {
        self.prepared_quads
    }

    pub const fn retained_quads(&self) -> u32 {
        self.retained_quads
    }

    pub const fn unchanged(&self) -> bool {
        self.unchanged
    }

    /// Return the physical vertex range requiring upload. A failed attempt always
    /// invalidates the key, so a retry cannot retain partially prepared geometry.
    pub fn prepare(
        &mut self,
        frame: &UiFrame,
        layout: ImageLayout<'_>,
        viewport: TitleViewport,
    ) -> Result<Range<usize>, RenderError> {
        if frame.quads.len().saturating_add(frame.world_quads.len()) > MAX_QUADS {
            self.invalidate();
            return Err(RenderError::Ui("UI vertex budget exceeded"));
        }
        let content = frame
            .retained
            .as_ref()
            .filter(|content| content.quad_count() <= frame.quads.len());
        let base = content.map_or(frame.quads.len(), RetainedUi::quad_count);
        let boundary = frame.world_boundary().min(base);
        let previous_key = self.key.take();
        let reuse = previous_key.as_ref().is_some_and(|key| {
            Some(&key.content) == content
                && key.viewport == viewport
                && key.dynamic == layout.dynamic
                && key.boundary == boundary
        });
        self.prepared_quads = 0;
        self.retained_quads = if reuse { self.base_quads } else { 0 };
        self.quads = self.retained_quads;
        let transients = !frame.world_quads.is_empty() || frame.quads.len() > base;
        self.unchanged = reuse && !transients && !self.had_transients;
        self.had_transients = transients;
        let result = self.prepare_layers(frame, layout, viewport, base, boundary, reuse);
        if result.is_ok() {
            self.key = if reuse {
                previous_key
            } else {
                content.map(|content| PreparedKey {
                    content: content.clone(),
                    viewport,
                    dynamic: layout.dynamic,
                    boundary,
                })
            };
        }
        result
    }

    fn prepare_layers(
        &mut self,
        frame: &UiFrame,
        layout: ImageLayout<'_>,
        viewport: TitleViewport,
        base: usize,
        boundary: usize,
        reuse: bool,
    ) -> Result<Range<usize>, RenderError> {
        self.draws[1].clear();
        self.draws[3].clear();
        let upload_start = if reuse {
            self.vertices.truncate(self.base_vertices);
            self.base_vertices
        } else {
            self.vertices.clear();
            self.draws[0].clear();
            self.draws[2].clear();
            self.append(&frame.quads[..boundary], 0, layout, viewport)?;
            self.append(&frame.quads[boundary..base], 2, layout, viewport)?;
            self.base_vertices = self.vertices.len();
            self.base_quads = self.quads();
            0
        };
        self.append(&frame.world_quads, 1, layout, viewport)?;
        self.append(&frame.quads[base..], 3, layout, viewport)?;
        Ok(upload_start..self.vertices.len())
    }

    fn append(
        &mut self,
        quads: &[Quad],
        layer: usize,
        layout: ImageLayout<'_>,
        viewport: TitleViewport,
    ) -> Result<(), RenderError> {
        for quad in quads {
            let (source, region, [width, height]) = layout.resolve(quad.image)?;
            let before = self.vertices.len();
            add_quad(&mut self.vertices, quad, region, width, height, viewport);
            self.prepared_quads += 1;
            if self.vertices.len() == before {
                continue;
            }
            self.quads += 1;
            if self.vertices.len() > MAX_QUADS * 6 {
                return Err(RenderError::Ui("UI vertex budget exceeded"));
            }
            let count = u32::try_from(self.vertices.len() - before)
                .map_err(|_| RenderError::Ui("vertex overflow"))?;
            let first = u32::try_from(before).map_err(|_| RenderError::Ui("vertex overflow"))?;
            if let Some(draw) = self.draws[layer]
                .last_mut()
                .filter(|draw| draw.source == source)
            {
                draw.count += count;
            } else {
                self.draws[layer].push(Draw {
                    first,
                    count,
                    source,
                });
            }
        }
        Ok(())
    }
}
