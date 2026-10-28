//! Revision-240 world map interface data: map elements, areas, composite maps and the per-region
//! geography/ground archives. Binary layouts follow the pinned reference client
//! (`WorldMapElement`, `WorldMapArea`, `WorldMapAreaData`, `WorldMapData_0/1`,
//! `AbstractWorldMapData`); rendering lives with the scene crate.
//!
//! This is a different concept from [`super::world_maps`], which loads the normal 3D scene's
//! map squares.
use super::definitions;
use crate::{
    BitmapFont, Cache, CacheProfile, FileId, GroupId, IndexId, Source, Sprite, decode_sprites,
    error::Result, reader::Reader,
};
use rustclient_model::ObjectDefinitionId;
use rustclient_platform::ReadOnlyBlobs;
use std::collections::{BTreeMap, BTreeSet};

// WorldMapRenderer -> classIT.method6356 uses ImageIO, not the indexed-sprite codec.
fn decode_overview(bytes: &[u8], source: Source) -> Result<Sprite> {
    let image = crate::Image::jpeg(bytes, source)?;
    let canvas = [image.width(), image.height()].map(u16::try_from);
    Ok(Sprite {
        id: rustclient_model::SpriteId::new(source.group.map_or(0, |group| group.0)),
        frame: 0,
        canvas: [
            canvas[0].map_err(|e| source.error("world map JPEG", e.to_string()))?,
            canvas[1].map_err(|e| source.error("world map JPEG", e.to_string()))?,
        ],
        offset: [0, 0],
        image,
    })
}

const WORLD_MAP_INDEX: IndexId = IndexId(18);
const GEOGRAPHY_INDEX: IndexId = IndexId(19);
const GROUND_INDEX: IndexId = IndexId(20);
const ELEMENT_GROUP: u32 = 35;
const OBJECT_GROUP: u32 = 6;
/// Tiles along one side of a region.
pub const REGION_TILES: usize = 64;
type GroupFileList = Vec<(FileId, Vec<u8>)>;

/// A packed `(plane, x, y)` coordinate as scripts pass it: `plane << 28 | x << 14 | y`.
#[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord, Hash)]
pub struct MapCoord {
    pub plane: i32,
    pub x: i32,
    pub y: i32,
}

impl MapCoord {
    #[must_use]
    pub const fn new(plane: i32, x: i32, y: i32) -> Self {
        Self { plane, x, y }
    }
    /// Unpack a script coordinate.
    #[must_use]
    pub const fn from_packed(packed: i32) -> Self {
        Self {
            plane: (packed.cast_unsigned() >> 28).cast_signed(),
            x: (packed >> 14) & 16383,
            y: packed & 16383,
        }
    }
    /// The script representation.
    #[must_use]
    pub const fn packed(self) -> i32 {
        (self.plane << 28) | (self.x << 14) | self.y
    }
}

/// One entry of the world map element definitions (config group 35).
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct WorldMapElement {
    pub id: u32,
    /// Icon sprite group, absent when the element is label-only.
    pub sprite: Option<u32>,
    pub hover_sprite: Option<u32>,
    pub name: Option<String>,
    pub label_color: u32,
    /// `0` small, `1` medium, `2` large label font.
    pub text_size: u8,
    pub world_map_visible: bool,
    pub minimap_visible: bool,
    pub menu_actions: [Option<String>; 5],
    pub menu_target: Option<String>,
    pub category: Option<u32>,
    /// Raw reference ordinals: `0` left, `1` centred, `2` right.
    pub horizontal_alignment: u8,
    /// Raw reference ordinals: `0` top, `1` centred, `2` bottom.
    pub vertical_alignment: u8,
}

/// Inclusive tile rectangle.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct TileRect {
    pub x0: i32,
    pub y0: i32,
    pub x1: i32,
    pub y1: i32,
}

impl TileRect {
    #[must_use]
    pub const fn contains(self, x: i32, y: i32) -> bool {
        x >= self.x0 && x <= self.x1 && y >= self.y0 && y <= self.y1
    }
}

/// One mapping between real world tiles and display tiles of an area. The four reference
/// section encodings (whole regions, one region, chunk ranges, one chunk) all reduce to a pair
/// of equally sized inclusive tile rectangles.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct WorldMapSection {
    min_plane: i32,
    planes: i32,
    source: TileRect,
    display: TileRect,
}

impl WorldMapSection {
    #[must_use]
    pub const fn new(min_plane: i32, planes: i32, source: TileRect, display: TileRect) -> Self {
        Self {
            min_plane,
            planes,
            source,
            display,
        }
    }
    fn contains_coord(&self, plane: i32, x: i32, y: i32) -> bool {
        plane >= self.min_plane
            && plane < self.min_plane + self.planes
            && self.source.contains(x, y)
    }
    const fn contains_position(&self, x: i32, y: i32) -> bool {
        self.display.contains(x, y)
    }
    /// Display position of a real-world coordinate in this section.
    fn position(&self, plane: i32, x: i32, y: i32) -> Option<[i32; 2]> {
        self.contains_coord(plane, x, y).then_some([
            x + self.display.x0 - self.source.x0,
            y + self.display.y0 - self.source.y0,
        ])
    }
    /// Real-world coordinate of a display position.
    fn coord(&self, x: i32, y: i32) -> Option<MapCoord> {
        self.contains_position(x, y).then_some(MapCoord::new(
            self.min_plane,
            x + self.source.x0 - self.display.x0,
            y + self.source.y0 - self.display.y0,
        ))
    }
}

/// A named world map (main map, Underground, Prifddinas, ...).
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct WorldMapArea {
    /// File id inside the `details` group; this is the id scripts use.
    pub id: u32,
    pub internal_name: String,
    pub external_name: String,
    pub origin: MapCoord,
    /// Region fill for tiles without data (ARGB).
    pub region_background: u32,
    /// Fill behind the whole map viewport (ARGB).
    pub viewport_background: u32,
    /// Default zoom percentage (`25`, `37`, `50`, `75`, `100` or `200`).
    pub default_zoom: u8,
    pub is_main: bool,
    pub sections: Vec<WorldMapSection>,
    /// Display region bounds, inclusive.
    pub region_low: [i32; 2],
    pub region_high: [i32; 2],
}

impl WorldMapArea {
    /// Whether a real-world coordinate is drawn by this area.
    #[must_use]
    pub fn contains_coord(&self, plane: i32, x: i32, y: i32) -> bool {
        self.sections.iter().any(|s| s.contains_coord(plane, x, y))
    }
    /// Whether a display tile has map data.
    #[must_use]
    pub fn contains_position(&self, x: i32, y: i32) -> bool {
        let (rx, ry) = (x / 64, y / 64);
        rx >= self.region_low[0]
            && rx <= self.region_high[0]
            && ry >= self.region_low[1]
            && ry <= self.region_high[1]
            && self.sections.iter().any(|s| s.contains_position(x, y))
    }
    /// Display position of a real-world coordinate.
    #[must_use]
    pub fn position(&self, plane: i32, x: i32, y: i32) -> Option<[i32; 2]> {
        self.sections.iter().find_map(|s| s.position(plane, x, y))
    }
    /// Real-world coordinate under a display position.
    #[must_use]
    pub fn coord(&self, x: i32, y: i32) -> Option<MapCoord> {
        self.sections
            .iter()
            .find(|s| s.contains_position(x, y))
            .and_then(|s| s.coord(x, y))
    }
}

/// One whole display region backed by one geography file.
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct WorldMapWholeRegion {
    pub min_plane: i32,
    pub planes: usize,
    /// Real-world region this data was cut from.
    pub source_region: [i32; 2],
    pub display_region: [i32; 2],
    pub group: Option<u32>,
    pub file: Option<u32>,
}

/// One 8x8 chunk of a display region.
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct WorldMapChunk {
    pub min_plane: i32,
    pub planes: usize,
    pub source_region: [i32; 2],
    pub source_chunk: [i32; 2],
    pub display_region: [i32; 2],
    pub display_chunk: [i32; 2],
    pub group: Option<u32>,
    pub file: Option<u32>,
}

/// An icon placed by the composite file rather than by a scenery object.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct WorldMapStaticIcon {
    pub element: Option<u32>,
    /// Display coordinate.
    pub coord: MapCoord,
    pub members_only: bool,
}

/// An area plus everything its `compositemap` file lists.
#[derive(Debug, Clone)]
pub struct WorldMapComposite {
    pub area: WorldMapArea,
    pub whole: Vec<WorldMapWholeRegion>,
    pub chunks: Vec<WorldMapChunk>,
    pub icons: Vec<WorldMapStaticIcon>,
    /// Pre-rendered overview picture (`compositetexture`).
    pub overview: Option<Sprite>,
}

/// A scenery object drawn on the map (wall or map scene) or the source of an icon.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct WorldMapDecoration {
    pub object: Option<u32>,
    pub shape: u8,
    pub rotation: u8,
}

/// One floor overlay of a tile: id `0` means none, otherwise `definition id + 1`.
#[derive(Debug, Clone, Copy, PartialEq, Eq, Default)]
pub struct WorldMapOverlay {
    pub id: u16,
    pub shape: u8,
    pub rotation: u8,
}

/// One decoded tile of the geography archive.
#[derive(Debug, Clone, Default, PartialEq, Eq)]
pub struct WorldMapTile {
    /// Underlay id: `0` none, otherwise `definition id + 1`.
    pub underlay: u16,
    /// Overlays by plane; empty when the tile has none.
    pub overlays: Vec<WorldMapOverlay>,
    /// Decorations by plane.
    pub decorations: Vec<Vec<WorldMapDecoration>>,
}

/// Everything needed to paint one 64x64 display region.
#[derive(Debug, Clone)]
pub struct WorldMapRegion {
    pub region: [i32; 2],
    pub planes: usize,
    /// Tiles indexed `x * 64 + y`.
    pub tiles: Vec<WorldMapTile>,
    /// Pre-blended ground colours (`0xAARRGGBB`) indexed `y * 64 + x`, from the ground archive.
    pub ground: Vec<u32>,
    /// Whether any geography was found for the region.
    pub has_data: bool,
}

impl WorldMapRegion {
    fn empty(region: [i32; 2]) -> Self {
        Self {
            region,
            planes: 0,
            tiles: vec![WorldMapTile::default(); REGION_TILES * REGION_TILES],
            ground: vec![0; REGION_TILES * REGION_TILES],
            has_data: false,
        }
    }
    /// Tile at `(x, y)` inside the region.
    #[must_use]
    pub fn tile(&self, x: usize, y: usize) -> &WorldMapTile {
        &self.tiles[x * REGION_TILES + y]
    }
    /// Ground colour at `(x, y)` inside the region; `0` where the archive had none.
    #[must_use]
    pub fn ground_color(&self, x: usize, y: usize) -> u32 {
        self.ground[y * REGION_TILES + x]
    }
}

/// The slice of an object definition the world map needs.
#[derive(Debug, Clone, PartialEq, Eq, Default)]
pub struct WorldMapObject {
    /// Doors (non-zero) are drawn red, other walls grey.
    pub interactive: bool,
    pub map_scene: Option<u32>,
    pub size: [u8; 2],
    /// Map element shown for this object, resolved through its first morph target when needed.
    pub map_element: Option<u32>,
}

/// Label font roles, in the reference's `text_size` order.
#[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord)]
pub enum WorldMapLabelSize {
    Small,
    Medium,
    Large,
}

impl WorldMapLabelSize {
    /// Element `text_size` field.
    #[must_use]
    pub const fn from_text_size(value: u8) -> Option<Self> {
        match value {
            0 => Some(Self::Small),
            1 => Some(Self::Medium),
            2 => Some(Self::Large),
            _ => None,
        }
    }
    /// Smallest zoom (pixels per tile) at which labels of this size are shown.
    #[must_use]
    pub const fn min_zoom(self) -> f32 {
        match self {
            Self::Small => 3.,
            Self::Medium => 2.,
            Self::Large => 0.,
        }
    }
    const fn font_name(self) -> &'static str {
        match self {
            Self::Small => "verdana_11pt_regular",
            Self::Medium => "verdana_13pt_regular",
            Self::Large => "verdana_15pt_regular",
        }
    }
}

fn element_source(id: u32) -> Source {
    Source {
        index: IndexId(2),
        group: Some(GroupId(ELEMENT_GROUP)),
        file: Some(FileId(id)),
    }
}

/// Decode one world map element definition.
/// # Errors
/// Rejects truncated data and unknown opcodes.
pub fn decode_element(bytes: &[u8], id: u32, source: Source) -> Result<WorldMapElement> {
    let mut r = Reader::new(bytes, source);
    let mut out = WorldMapElement {
        id,
        sprite: None,
        hover_sprite: None,
        name: None,
        label_color: 0,
        text_size: 0,
        world_map_visible: true,
        minimap_visible: false,
        menu_actions: Default::default(),
        menu_target: None,
        category: None,
        horizontal_alignment: 1,
        vertical_alignment: 1,
    };
    loop {
        match r.u8()? {
            0 => break,
            1 => out.sprite = r.nullable_big_smart()?,
            2 => out.hover_sprite = r.nullable_big_smart()?,
            3 => out.name = Some(r.string_value()?),
            4 => out.label_color = r.u24()?,
            5 => {
                r.u24()?;
            }
            18 | 25 => {
                r.nullable_big_smart()?;
            }
            6 => out.text_size = r.u8()?,
            7 => {
                let flags = r.u8()?;
                out.world_map_visible = flags & 1 != 0;
                out.minimap_visible = flags & 2 != 0;
            }
            8 | 28 => {
                r.u8()?;
            }
            tag @ 10..=14 => out.menu_actions[usize::from(tag - 10)] = Some(r.string_value()?),
            15 => {
                let count = usize::from(r.u8()?);
                r.take(count * 4)?;
                r.u32()?;
                let extra = usize::from(r.u8()?);
                r.take(extra * 4)?;
                r.take(count)?;
            }
            16 => {}
            17 => out.menu_target = Some(r.string_value()?),
            19 => out.category = Some(u32::from(r.u16()?)),
            21 | 22 => {
                r.u32()?;
            }
            23 => {
                r.take(3)?;
            }
            24 => {
                r.take(4)?;
            }
            29 => out.horizontal_alignment = r.u8()?,
            30 => out.vertical_alignment = r.u8()?,
            tag => {
                return Err(source.error(
                    "world map element",
                    format!("element {id} unsupported opcode {tag}"),
                ));
            }
        }
    }
    Ok(out)
}

#[allow(clippy::similar_names)]
fn read_section(r: &mut Reader<'_>, source: Source) -> Result<WorldMapSection> {
    let kind = r.u8()?;
    let region_rect = |sx: u16, sy: u16, ex: u16, ey: u16| TileRect {
        x0: i32::from(sx) * 64,
        y0: i32::from(sy) * 64,
        x1: i32::from(ex) * 64 + 63,
        y1: i32::from(ey) * 64 + 63,
    };
    let chunk_rect =
        |region_x: u16, chunk_x: u8, region_y: u16, chunk_y: u8, chunks: [u8; 2]| TileRect {
            x0: i32::from(region_x) * 64 + i32::from(chunk_x) * 8,
            y0: i32::from(region_y) * 64 + i32::from(chunk_y) * 8,
            x1: i32::from(region_x) * 64 + i32::from(chunks[0]) * 8 + 7,
            y1: i32::from(region_y) * 64 + i32::from(chunks[1]) * 8 + 7,
        };
    Ok(match kind {
        // WORLDMAPSECTIONTYPE0: a rectangle of whole regions moved to another rectangle.
        0 => {
            let min_plane = i32::from(r.u8()?);
            let planes = i32::from(r.u8()?);
            let [sx, sy, ex, ey, nsx, nsy, nex, ney] = [(); 8].map(|()| r.u16());
            WorldMapSection {
                min_plane,
                planes,
                source: region_rect(sx?, sy?, ex?, ey?),
                display: region_rect(nsx?, nsy?, nex?, ney?),
            }
        }
        // WORLDMAPSECTIONTYPE1: one region to one display region.
        1 => {
            let min_plane = i32::from(r.u8()?);
            let planes = i32::from(r.u8()?);
            let [sx, sy, dx, dy] = [(); 4].map(|()| r.u16());
            let (sx, sy, dx, dy) = (sx?, sy?, dx?, dy?);
            WorldMapSection {
                min_plane,
                planes,
                source: region_rect(sx, sy, sx, sy),
                display: region_rect(dx, dy, dx, dy),
            }
        }
        // WORLDMAPSECTIONTYPE2: chunk ranges.
        2 => {
            let old_z = i32::from(r.u8()?);
            let new_z = i32::from(r.u8()?);
            let old_x = r.u16()?;
            let (old_cx_low, old_cx_high) = (r.u8()?, r.u8()?);
            let old_y = r.u16()?;
            let (old_cy_low, old_cy_high) = (r.u8()?, r.u8()?);
            let new_x = r.u16()?;
            let (new_cx_low, new_cx_high) = (r.u8()?, r.u8()?);
            let new_y = r.u16()?;
            let (new_cy_low, new_cy_high) = (r.u8()?, r.u8()?);
            WorldMapSection {
                min_plane: old_z,
                planes: new_z,
                source: chunk_rect(
                    old_x,
                    old_cx_low,
                    old_y,
                    old_cy_low,
                    [old_cx_high, old_cy_high],
                ),
                display: chunk_rect(
                    new_x,
                    new_cx_low,
                    new_y,
                    new_cy_low,
                    [new_cx_high, new_cy_high],
                ),
            }
        }
        // WORLDMAPSECTIONTYPE3: one chunk.
        3 => {
            let min_plane = i32::from(r.u8()?);
            let planes = i32::from(r.u8()?);
            let old_x = r.u16()?;
            let old_cx = r.u8()?;
            let old_y = r.u16()?;
            let old_cy = r.u8()?;
            let new_x = r.u16()?;
            let new_cx = r.u8()?;
            let new_y = r.u16()?;
            let new_cy = r.u8()?;
            WorldMapSection {
                min_plane,
                planes,
                source: chunk_rect(old_x, old_cx, old_y, old_cy, [old_cx, old_cy]),
                display: chunk_rect(new_x, new_cx, new_y, new_cy, [new_cx, new_cy]),
            }
        }
        other => {
            return Err(source.error(
                "world map area",
                format!("unsupported section type {other}"),
            ));
        }
    })
}

/// Decode one `details` file.
/// # Errors
/// Rejects truncated data and unknown section types.
pub fn decode_area(bytes: &[u8], id: u32, source: Source) -> Result<WorldMapArea> {
    let mut r = Reader::new(bytes, source);
    let internal_name = r.string_value()?;
    let external_name = r.string_value()?;
    let origin = MapCoord::from_packed(r.u32()?.cast_signed());
    let region_background = r.u32()?;
    let viewport_background = r.u32()?;
    r.u8()?;
    let is_main = r.u8()? == 1;
    let default_zoom = r.u8()?;
    let count = r.u8()?;
    let mut sections = Vec::with_capacity(usize::from(count));
    for _ in 0..count {
        sections.push(read_section(&mut r, source)?);
    }
    let (mut low, mut high) = ([i32::MAX; 2], [0; 2]);
    for section in &sections {
        low[0] = low[0].min(section.display.x0 / 64);
        low[1] = low[1].min(section.display.y0 / 64);
        high[0] = high[0].max(section.display.x1 / 64);
        high[1] = high[1].max(section.display.y1 / 64);
    }
    Ok(WorldMapArea {
        id,
        internal_name,
        external_name,
        origin,
        region_background,
        viewport_background,
        default_zoom,
        is_main,
        sections,
        region_low: low,
        region_high: high,
    })
}

fn plane_count(value: u8) -> usize {
    usize::from(value).min(4)
}

fn decode_composite(area: WorldMapArea, bytes: &[u8], source: Source) -> Result<WorldMapComposite> {
    let mut r = Reader::new(bytes, source);
    let whole_count = r.u16()?;
    let mut whole = Vec::with_capacity(usize::from(whole_count));
    for _ in 0..whole_count {
        if r.u8()? != 0 {
            return Err(source.error("world map composite", "unexpected whole-region type"));
        }
        let min_plane = i32::from(r.u8()?);
        let planes = plane_count(r.u8()?);
        let source_region = [i32::from(r.u16()?), i32::from(r.u16()?)];
        let display_region = [i32::from(r.u16()?), i32::from(r.u16()?)];
        whole.push(WorldMapWholeRegion {
            min_plane,
            planes,
            source_region,
            display_region,
            group: r.nullable_big_smart()?,
            file: r.nullable_big_smart()?,
        });
    }
    let chunk_count = r.u16()?;
    let mut chunks = Vec::with_capacity(usize::from(chunk_count));
    for _ in 0..chunk_count {
        if r.u8()? != 1 {
            return Err(source.error("world map composite", "unexpected chunk type"));
        }
        let min_plane = i32::from(r.u8()?);
        let planes = plane_count(r.u8()?);
        let source_region = [i32::from(r.u16()?), i32::from(r.u16()?)];
        let source_chunk = [i32::from(r.u8()?), i32::from(r.u8()?)];
        let display_region = [i32::from(r.u16()?), i32::from(r.u16()?)];
        let display_chunk = [i32::from(r.u8()?), i32::from(r.u8()?)];
        chunks.push(WorldMapChunk {
            min_plane,
            planes,
            source_region,
            source_chunk,
            display_region,
            display_chunk,
            group: r.nullable_big_smart()?,
            file: r.nullable_big_smart()?,
        });
    }
    let icon_count = r.u16()?;
    let mut icons = Vec::with_capacity(usize::from(icon_count));
    for _ in 0..icon_count {
        let element = r.nullable_big_smart()?;
        let coord = MapCoord::from_packed(r.u32()?.cast_signed());
        let members_only = r.u8()? == 1;
        icons.push(WorldMapStaticIcon {
            element,
            coord,
            members_only,
        });
    }
    Ok(WorldMapComposite {
        area,
        whole,
        chunks,
        icons,
        overview: None,
    })
}

/// Which 64x64 block of a region file a decoder fills.
#[derive(Clone, Copy)]
enum TileWindow {
    Region { region: [i32; 2] },
    Chunk { region: [i32; 2], chunk: [i32; 2] },
}

/// Decode one geography file into `region`, which must be the display region it belongs to.
fn decode_geography(
    bytes: &[u8],
    window: TileWindow,
    planes: usize,
    region: &mut WorldMapRegion,
    source: Source,
) -> Result<()> {
    let mut r = Reader::new(bytes, source);
    let (kind, expected, bounds) = match window {
        TileWindow::Region { region } => (0, region.to_vec(), (0, 64, 0, 64)),
        TileWindow::Chunk { region, chunk } => (
            1,
            vec![region[0], region[1], chunk[0], chunk[1]],
            (
                usize::try_from(chunk[0] * 8).unwrap_or(0),
                usize::try_from(chunk[0] * 8 + 8).unwrap_or(0),
                usize::try_from(chunk[1] * 8).unwrap_or(0),
                usize::try_from(chunk[1] * 8 + 8).unwrap_or(0),
            ),
        ),
    };
    if r.u8()? != kind {
        return Err(source.error("world map geography", "unexpected region type"));
    }
    for value in expected {
        if i32::from(r.u8()?) != value {
            return Err(source.error("world map geography", "region header mismatch"));
        }
    }
    let planes = planes.min(4);
    region.planes = region.planes.max(planes);
    let (x0, x1, y0, y1) = bounds;
    for x in x0..x1 {
        for y in y0..y1 {
            read_tile(&mut r, planes, &mut region.tiles[x * REGION_TILES + y])?;
        }
    }
    Ok(())
}

fn read_tile(r: &mut Reader<'_>, planes: usize, tile: &mut WorldMapTile) -> Result<()> {
    let flags = r.u8()?;
    if flags == 0 {
        return Ok(());
    }
    if flags & 1 != 0 {
        if flags & 2 != 0 {
            tile.overlays = vec![WorldMapOverlay {
                id: r.u16()?,
                ..WorldMapOverlay::default()
            }];
        }
        tile.underlay = r.u16()?;
        return Ok(());
    }
    let decoration_planes = usize::from((flags & 0x18) >> 3) + 1;
    tile.underlay = r.u16()?;
    if flags & 0x02 != 0 {
        let count = usize::from(r.u8()?);
        let mut overlays = vec![WorldMapOverlay::default(); count.min(planes.max(1))];
        for plane in 0..count {
            let id = r.u16()?;
            if id != 0 {
                let packed = r.u8()?;
                if let Some(slot) = overlays.get_mut(plane) {
                    *slot = WorldMapOverlay {
                        id,
                        shape: packed >> 2,
                        rotation: packed & 3,
                    };
                }
            }
        }
        tile.overlays = overlays;
    }
    if flags & 4 != 0 {
        let mut by_plane = vec![Vec::new(); decoration_planes.min(planes.max(1))];
        for plane in 0..decoration_planes {
            let count = r.u8()?;
            let mut list = Vec::with_capacity(usize::from(count));
            for _ in 0..count {
                let object = r.nullable_big_smart()?;
                let packed = r.u8()?;
                list.push(WorldMapDecoration {
                    object,
                    shape: packed >> 2,
                    rotation: packed & 3,
                });
            }
            if let Some(slot) = by_plane.get_mut(plane) {
                *slot = list;
            }
        }
        tile.decorations = by_plane;
    }
    Ok(())
}

/// Decode the ground archive's 64x64 colour sprite into `y * 64 + x` order.
fn decode_ground(bytes: &[u8], source: Source) -> Result<Vec<u32>> {
    let sprites = decode_sprites(bytes, source)?;
    let sprite = sprites
        .first()
        .ok_or_else(|| source.error("world map ground", "no sprite frame"))?;
    let mut out = vec![0; REGION_TILES * REGION_TILES];
    let width = usize::try_from(sprite.image.width()).unwrap_or(0);
    let height = usize::try_from(sprite.image.height()).unwrap_or(0);
    let (ox, oy) = (usize::from(sprite.offset[0]), usize::from(sprite.offset[1]));
    for row in 0..height {
        for column in 0..width {
            let (x, y) = (column + ox, row + oy);
            if x >= REGION_TILES || y >= REGION_TILES {
                continue;
            }
            let at = (row * width + column) * 4;
            let px = &sprite.image.pixels()[at..at + 4];
            out[y * REGION_TILES + x] = (u32::from(px[3]) << 24)
                | (u32::from(px[0]) << 16)
                | (u32::from(px[1]) << 8)
                | u32::from(px[2]);
        }
    }
    Ok(out)
}

impl CacheProfile {
    /// Every world map element definition, by element id. Elements that fail to decode are
    /// skipped and reported in `warnings` so one bad definition cannot hide the whole map.
    /// # Errors
    /// Fails when the config group itself cannot be read.
    pub fn load_world_map_elements<S: ReadOnlyBlobs>(
        self,
        cache: &Cache<S>,
        warnings: &mut Vec<String>,
    ) -> Result<BTreeMap<u32, WorldMapElement>> {
        let mut out = BTreeMap::new();
        let mut group_warnings = Vec::new();
        cache.config_group_with(
            ELEMENT_GROUP,
            self == CacheProfile::OpenRune240,
            &mut group_warnings,
            |files| {
                for (file, bytes) in files {
                    match decode_element(bytes, file.0, element_source(file.0)) {
                        Ok(element) => {
                            out.insert(file.0, element);
                        }
                        Err(error) => warnings.push(error.to_string()),
                    }
                }
                Ok(())
            },
        )?;
        Ok(out)
    }

    /// Every world map area in the `details` group.
    /// # Errors
    /// Fails when the group is missing or a definition is malformed.
    pub fn load_world_map_areas<S: ReadOnlyBlobs>(
        self,
        cache: &Cache<S>,
    ) -> Result<Vec<WorldMapArea>> {
        let context = Source::group(WORLD_MAP_INDEX, GroupId(0));
        let group = cache
            .tables()
            .get(&WORLD_MAP_INDEX)
            .and_then(|table| table.named_group("details"))
            .map(|row| row.id)
            .ok_or_else(|| context.error("world map", "missing details group"))?;
        cache
            .group_files(WORLD_MAP_INDEX, group)?
            .into_iter()
            .map(|(file, bytes)| {
                decode_area(
                    &bytes,
                    file.0,
                    Source {
                        index: WORLD_MAP_INDEX,
                        group: Some(group),
                        file: Some(file),
                    },
                )
            })
            .collect()
    }

    /// The composite map and overview of one area.
    /// # Errors
    /// Fails when the area has no composite file or it is malformed. A missing overview is not
    /// an error.
    pub fn load_world_map_composite<S: ReadOnlyBlobs>(
        self,
        cache: &Cache<S>,
        area: &WorldMapArea,
    ) -> Result<WorldMapComposite> {
        let (source, bytes) =
            cache.named_file(WORLD_MAP_INDEX, "compositemap", &area.internal_name)?;
        let mut composite = decode_composite(area.clone(), &bytes, source)?;
        if let Ok((source, bytes)) =
            cache.named_file(WORLD_MAP_INDEX, "compositetexture", &area.internal_name)
        {
            composite.overview = Some(decode_overview(&bytes, source)?);
        }
        Ok(composite)
    }

    /// Decode one display region of a composite, merging every chunk that lands in it.
    /// # Errors
    /// Fails on malformed geography or ground data. A region with no data at all is returned
    /// empty rather than failing.
    pub fn load_world_map_region<S: ReadOnlyBlobs>(
        self,
        cache: &Cache<S>,
        composite: &WorldMapComposite,
        region: [i32; 2],
    ) -> Result<WorldMapRegion> {
        let mut out = WorldMapRegion::empty(region);
        let mut groups: BTreeMap<(IndexId, u32), GroupFileList> = BTreeMap::new();
        let mut file = |index: IndexId, group: u32, file: u32| -> Result<Option<Vec<u8>>> {
            if let std::collections::btree_map::Entry::Vacant(entry) = groups.entry((index, group))
            {
                let known = cache
                    .tables()
                    .get(&index)
                    .is_some_and(|table| table.groups.contains_key(&GroupId(group)));
                entry.insert(if known {
                    cache.group_files(index, GroupId(group))?
                } else {
                    Vec::new()
                });
            }
            Ok(groups[&(index, group)]
                .iter()
                .find(|(id, _)| id.0 == file)
                .map(|(_, bytes)| bytes.clone()))
        };
        let mut ground_group = None;
        if let Some(whole) = composite
            .whole
            .iter()
            .find(|data| data.display_region == region)
        {
            if let (Some(group), Some(file_id)) = (whole.group, whole.file) {
                ground_group = Some(group);
                if let Some(bytes) = file(GEOGRAPHY_INDEX, group, file_id)? {
                    decode_geography(
                        &bytes,
                        TileWindow::Region { region },
                        whole.planes,
                        &mut out,
                        Source {
                            index: GEOGRAPHY_INDEX,
                            group: Some(GroupId(group)),
                            file: Some(FileId(file_id)),
                        },
                    )?;
                    out.has_data = true;
                }
            }
        } else {
            for chunk in composite
                .chunks
                .iter()
                .filter(|data| data.display_region == region)
            {
                let (Some(group), Some(file_id)) = (chunk.group, chunk.file) else {
                    continue;
                };
                ground_group.get_or_insert(group);
                if let Some(bytes) = file(GEOGRAPHY_INDEX, group, file_id)? {
                    decode_geography(
                        &bytes,
                        TileWindow::Chunk {
                            region,
                            chunk: chunk.display_chunk,
                        },
                        chunk.planes,
                        &mut out,
                        Source {
                            index: GEOGRAPHY_INDEX,
                            group: Some(GroupId(group)),
                            file: Some(FileId(file_id)),
                        },
                    )?;
                    out.has_data = true;
                }
            }
        }
        if let Some(group) = ground_group
            && let Some(bytes) = file(GROUND_INDEX, group, 0)?
        {
            out.ground = decode_ground(
                &bytes,
                Source {
                    index: GROUND_INDEX,
                    group: Some(GroupId(group)),
                    file: Some(FileId(0)),
                },
            )?;
        }
        Ok(out)
    }

    /// The map-relevant parts of the requested object definitions. Objects that already appear
    /// in `known` are not decoded again; missing or malformed definitions are skipped.
    /// # Errors
    /// Fails when the object config group cannot be read.
    pub fn load_world_map_objects<S: ReadOnlyBlobs>(
        self,
        cache: &Cache<S>,
        wanted: &BTreeSet<u32>,
        known: &mut BTreeMap<u32, WorldMapObject>,
    ) -> Result<()> {
        let missing: Vec<u32> = wanted
            .iter()
            .copied()
            .filter(|id| !known.contains_key(id))
            .collect();
        if missing.is_empty() {
            return Ok(());
        }
        let mut warnings = Vec::new();
        cache.config_group_with(
            OBJECT_GROUP,
            self == CacheProfile::OpenRune240,
            &mut warnings,
            |files| {
                let source = |id: u32| Source {
                    index: IndexId(2),
                    group: Some(GroupId(OBJECT_GROUP)),
                    file: Some(FileId(id)),
                };
                let decode = |id: u32| {
                    files.get(&FileId(id)).and_then(|bytes| {
                        definitions::object(bytes, ObjectDefinitionId::new(id), source(id)).ok()
                    })
                };
                for id in missing {
                    let mut object = WorldMapObject::default();
                    if let Some(definition) = decode(id) {
                        object.interactive = definition.int1 != 0;
                        object.map_scene = definition.map_scene;
                        object.size = definition.size;
                        object.map_element = definition.map_icon;
                        if object.map_element.is_none()
                            && let Some(morphs) = &definition.morphs
                        {
                            object.map_element = morphs
                                .targets
                                .iter()
                                .chain([&morphs.fallback])
                                .flatten()
                                .filter_map(|target| decode(target.get()))
                                .find_map(|target| target.map_icon);
                        }
                    }
                    known.insert(id, object);
                }
                Ok(())
            },
        )
    }

    /// Average HSL of each texture, used to colour textured floor overlays.
    /// # Errors
    /// Fails when the texture definition group cannot be read.
    pub fn load_world_map_texture_colors<S: ReadOnlyBlobs>(
        self,
        cache: &Cache<S>,
    ) -> Result<BTreeMap<u32, u16>> {
        let mut out = BTreeMap::new();
        for (id, bytes) in cache.group_files(IndexId(9), GroupId(0))? {
            let source = Source {
                index: IndexId(9),
                group: Some(GroupId(0)),
                file: Some(id),
            };
            let mut r = Reader::new(&bytes, source);
            r.u16()?;
            out.insert(id.0, r.u16()?);
        }
        Ok(out)
    }

    /// The Verdana label fonts the world map draws with.
    /// # Errors
    /// Fails when the font or its metrics are missing or malformed.
    pub fn load_world_map_font<S: ReadOnlyBlobs>(
        self,
        cache: &Cache<S>,
        size: WorldMapLabelSize,
    ) -> Result<BitmapFont> {
        let name = size.font_name();
        let group_of = |index: IndexId| -> Result<GroupId> {
            cache
                .tables()
                .get(&index)
                .and_then(|table| table.named_group(name))
                .map(|row| row.id)
                .ok_or_else(|| {
                    Source::group(index, GroupId(0))
                        .error("world map font", format!("missing font {name}"))
                })
        };
        let first = |index: IndexId| -> Result<(Source, Vec<u8>)> {
            let group = group_of(index)?;
            let source = Source::group(index, group);
            let bytes = cache
                .group_files(index, group)?
                .into_iter()
                .find(|(file, _)| file.0 == 0)
                .map(|(_, bytes)| bytes)
                .ok_or_else(|| source.error("world map font", "missing file"))?;
            Ok((source, bytes))
        };
        let (source, masks) = first(IndexId(8))?;
        let (_, metrics) = first(IndexId(13))?;
        BitmapFont::decode(&metrics, decode_sprites(&masks, source)?, source)
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    fn source() -> Source {
        Source::group(WORLD_MAP_INDEX, GroupId(0))
    }

    #[test]
    fn composite_texture_uses_bounded_jpeg_instead_of_indexed_sprite_decoding() {
        // Generated 8x8 constant RGB(12,180,40), JPEG quality 100, no subsampling.
        let bytes = include_bytes!("../../tests/fixtures/worldmap-overview.jpg");
        let sprite = decode_overview(bytes, source()).unwrap();
        assert_eq!(sprite.canvas, [8, 8]);
        assert_eq!(sprite.offset, [0, 0]);
        assert_eq!(sprite.image.pixels().len(), 8 * 8 * 4);
        let pixel = &sprite.image.pixels()[..4];
        assert!(
            pixel[0].abs_diff(12) <= 2 && pixel[1].abs_diff(180) <= 2 && pixel[2].abs_diff(40) <= 2
        );
        assert_eq!(pixel[3], 255);
        assert!(decode_sprites(bytes, source()).is_err());
        assert!(decode_overview(&bytes[..20], source()).is_err());
    }
    #[test]
    fn packed_coordinates_round_trip_the_script_layout() {
        let coord = MapCoord::new(2, 3200, 3210);
        assert_eq!(coord.packed(), (2 << 28) | (3200 << 14) | 0x0c8a);
        assert_eq!(MapCoord::from_packed(coord.packed()), coord);
    }

    #[test]
    fn element_decodes_sprite_name_flags_actions_and_polygon() {
        let mut bytes = vec![1, 0x00, 0x2a]; // sprite 42
        bytes.extend_from_slice(&[3]);
        bytes.extend_from_slice(b"Bank\0");
        bytes.extend_from_slice(&[4, 0xff, 0x00, 0x10, 6, 2, 7, 0b11, 10]);
        bytes.extend_from_slice(b"Open\0");
        bytes.extend_from_slice(&[
            15, 2, 0, 1, 0, 2, 0, 3, 0, 4, 0, 0, 0, 9, 1, 0, 0, 0, 7, 5, 6,
        ]);
        bytes.extend_from_slice(&[19, 0, 12, 29, 2, 30, 0, 0]);
        let element = decode_element(&bytes, 5, source()).unwrap();
        assert_eq!(element.sprite, Some(42));
        assert_eq!(element.name.as_deref(), Some("Bank"));
        assert_eq!(element.label_color, 0x00ff_0010);
        assert_eq!(element.text_size, 2);
        assert!(element.world_map_visible && element.minimap_visible);
        assert_eq!(element.menu_actions[0].as_deref(), Some("Open"));
        assert_eq!(element.category, Some(12));
        assert_eq!(
            (element.horizontal_alignment, element.vertical_alignment),
            (2, 0)
        );
        for n in 0..bytes.len() {
            assert!(decode_element(&bytes[..n], 5, source()).is_err());
        }
        assert!(decode_element(&[99, 0], 5, source()).is_err());
    }

    #[test]
    fn sections_map_source_tiles_to_display_tiles_and_back() {
        // Type 1: region (50, 50) shown at display region (10, 11), plane 0 only.
        let mut bytes = b"main\0Main Map\0".to_vec();
        bytes.extend_from_slice(&MapCoord::new(0, 3200, 3200).packed().to_be_bytes());
        bytes.extend_from_slice(&0x0000_0000_u32.to_be_bytes());
        bytes.extend_from_slice(&0xff12_3456_u32.to_be_bytes());
        bytes.extend_from_slice(&[0, 1, 50, 1]);
        bytes.extend_from_slice(&[1, 0, 1, 0, 50, 0, 50, 0, 10, 0, 11]);
        let area = decode_area(&bytes, 7, source()).unwrap();
        assert!(area.is_main);
        assert_eq!(area.default_zoom, 50);
        assert_eq!((area.region_low, area.region_high), ([10, 11], [10, 11]));
        assert!(area.contains_coord(0, 3200, 3200));
        assert!(!area.contains_coord(1, 3200, 3200));
        assert_eq!(area.position(0, 3200 + 5, 3200 + 6), Some([645, 710]));
        assert_eq!(area.coord(645, 710), Some(MapCoord::new(0, 3205, 3206)));
        assert!(area.contains_position(645, 710));
        assert!(!area.contains_position(0, 0));
    }

    #[test]
    fn composite_lists_regions_chunks_and_icons() {
        let area = WorldMapArea {
            id: 0,
            internal_name: String::new(),
            external_name: String::new(),
            origin: MapCoord::new(0, 0, 0),
            region_background: 0,
            viewport_background: 0,
            default_zoom: 25,
            is_main: false,
            sections: Vec::new(),
            region_low: [0; 2],
            region_high: [0; 2],
        };
        let mut bytes = vec![0, 1];
        bytes.extend_from_slice(&[0, 0, 4, 0, 50, 0, 51, 0, 10, 0, 11, 0x00, 0x03, 0x00, 0x09]);
        bytes.extend_from_slice(&[0, 1]);
        bytes.extend_from_slice(&[
            1, 0, 1, 0, 50, 0, 51, 2, 3, 0, 10, 0, 11, 4, 5, 0x00, 0x03, 0x00, 0x09,
        ]);
        bytes.extend_from_slice(&[0, 1, 0x00, 0x08]);
        bytes.extend_from_slice(&MapCoord::new(1, 7, 9).packed().to_be_bytes());
        bytes.push(1);
        let composite = decode_composite(area, &bytes, source()).unwrap();
        assert_eq!(composite.whole[0].display_region, [10, 11]);
        assert_eq!(composite.whole[0].planes, 4);
        assert_eq!(composite.whole[0].group, Some(3));
        assert_eq!(composite.chunks[0].display_chunk, [4, 5]);
        assert_eq!(composite.chunks[0].source_chunk, [2, 3]);
        assert_eq!(composite.icons[0].element, Some(8));
        assert_eq!(composite.icons[0].coord, MapCoord::new(1, 7, 9));
        assert!(composite.icons[0].members_only);
    }

    #[test]
    fn geography_tiles_decode_overlays_and_decorations() {
        let mut region = WorldMapRegion::empty([10, 11]);
        // Header: region type 0 at (10, 11); every tile but the first is empty.
        let mut bytes = vec![0, 10, 11];
        // Tile (0, 0): flags 0b110 (overlays + decorations, 1 decoration plane), underlay 5.
        bytes.extend_from_slice(&[0b110, 0, 5]);
        bytes.extend_from_slice(&[1, 0, 9, (3 << 2) | 1]); // one overlay plane: id 9, shape 3 rot 1
        bytes.extend_from_slice(&[1, 0x00, 0x20, (10 << 2) | 2]); // one decoration: object 32 shape 10 rot 2
        bytes.extend(std::iter::repeat_n(0, 4095));
        decode_geography(
            &bytes,
            TileWindow::Region { region: [10, 11] },
            1,
            &mut region,
            source(),
        )
        .unwrap();
        let tile = region.tile(0, 0);
        assert_eq!(tile.underlay, 5);
        assert_eq!(
            tile.overlays,
            [WorldMapOverlay {
                id: 9,
                shape: 3,
                rotation: 1
            }]
        );
        assert_eq!(
            tile.decorations,
            [vec![WorldMapDecoration {
                object: Some(32),
                shape: 10,
                rotation: 2
            }]]
        );
        assert_eq!(region.tile(1, 0), &WorldMapTile::default());
        assert!(
            decode_geography(
                &bytes,
                TileWindow::Region { region: [9, 11] },
                1,
                &mut WorldMapRegion::empty([9, 11]),
                source()
            )
            .is_err()
        );
    }
}
