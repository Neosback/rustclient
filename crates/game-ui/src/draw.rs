//! Ordered, platform-neutral UI projection commands.

/// Image role within one title asset set; these are not GPU handles.
#[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord)]
pub enum UiImageId {
    /// Title background.
    Background,
    /// Standard logo.
    Logo,
    /// Panel background.
    TitleBox,
    /// Standard button.
    Button,
    /// Disabled music indicator.
    Muted,
    /// One checkbox state.
    Checkbox(u8),
    /// Font index and CP1252 glyph.
    Glyph(u8, u8),
    /// Dynamic rune flame texture.
    Flames,
    /// Solid-color rectangle pixel.
    White,
    /// Static gameframe / interface sprite by ID.
    Sprite(u32),
    /// Dynamic rotating minimap circle texture.
    Minimap,
    /// Dynamic rotating compass texture.
    Compass,
}

/// Logical rectangle with exclusive right/bottom bounds.
#[derive(Debug, Clone, Copy, PartialEq)]
pub struct Rect {
    /// Left coordinate.
    pub x: f32,
    /// Top coordinate.
    pub y: f32,
    /// Width.
    pub width: f32,
    /// Height.
    pub height: f32,
}
impl Rect {
    /// Construct a logical rectangle.
    #[must_use]
    pub const fn new(x: f32, y: f32, width: f32, height: f32) -> Self {
        Self {
            x,
            y,
            width,
            height,
        }
    }
    /// Logical containment using the same geometry as drawing.
    #[must_use]
    pub fn contains(self, x: f32, y: f32) -> bool {
        x >= self.x && y >= self.y && x < self.x + self.width && y < self.y + self.height
    }
}

/// One painter-ordered textured/tinted quad.
#[derive(Debug, Clone)]
pub struct Quad {
    /// CPU image identity.
    pub image: UiImageId,
    /// Logical destination.
    pub rect: Rect,
    /// Normalized source range; a negative width mirrors horizontally.
    pub uv: Rect,
    /// Straight-alpha RGBA tint.
    pub tint: [u8; 4],
    /// Logical clipping rectangle.
    pub clip: Rect,
}

/// Reusable CPU projection buffer. Contains no credential secrets.
#[derive(Debug, Default)]
pub struct UiFrame {
    /// Painter-ordered commands.
    pub quads: Vec<Quad>,
}
impl UiFrame {
    pub fn image(&mut self, image: UiImageId, rect: Rect, clip: Rect) {
        self.quads.push(Quad {
            image,
            rect,
            uv: Rect::new(0., 0., 1., 1.),
            tint: [255; 4],
            clip,
        });
    }

    pub fn image_tinted(&mut self, image: UiImageId, rect: Rect, clip: Rect, tint: [u8; 4]) {
        self.quads.push(Quad {
            image,
            rect,
            uv: Rect::new(0., 0., 1., 1.),
            tint,
            clip,
        });
    }

    pub fn fill_rect(&mut self, rect: Rect, color: [u8; 4], clip: Rect) {
        self.image_tinted(UiImageId::White, rect, clip, color);
    }
}

/// Native and browser must apply this same logical/DPI transform to input/output.
#[derive(Debug, Clone, Copy)]
pub struct TitleViewport {
    /// Physical framebuffer width.
    pub physical_width: u32,
    /// Physical framebuffer height.
    pub physical_height: u32,
    /// Physical pixels per logical pixel.
    pub scale: f64,
}
impl TitleViewport {
    /// Centered title origin in logical pixels, top aligned.
    #[must_use]
    #[expect(
        clippy::cast_possible_truncation,
        reason = "window coordinates are intentionally projected to f32"
    )]
    pub fn origin_x(self) -> f32 {
        ((f64::from(self.physical_width) / self.scale - 765.) / 2.).floor() as f32
    }
    /// Convert a physical pointer coordinate into title-local logical coordinates.
    #[must_use]
    #[expect(
        clippy::cast_possible_truncation,
        reason = "window coordinates are intentionally projected to f32"
    )]
    pub fn pointer(self, x: f64, y: f64) -> (f32, f32) {
        (
            (x / self.scale) as f32 - self.origin_x(),
            (y / self.scale) as f32,
        )
    }
}
