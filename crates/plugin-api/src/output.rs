//! Declarative plugin output (API 0.2).
//!
//! A plugin never calls into the renderer or mutates client state. Each update it returns a
//! [`PluginOutput`] describing what it wants drawn, hidden, restyled or notified; the host
//! validates every item against the plugin's grants and bounds, merges outputs across plugins and
//! hands the result to the owning subsystem. Outputs are level-triggered: omitting an item next
//! update removes it.

use crate::{ActorRef, MenuEdit, WorldPoint};
use std::sync::Arc;

/// Draw ordering of an overlay command.
#[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord, Default)]
pub enum OverlayLayer {
    /// Drawn with the world scene, under interface widgets.
    Scene,
    /// Drawn over the world view but below the gameframe widgets.
    #[default]
    AboveScene,
    /// Drawn above every widget.
    Top,
}

/// Which horizontal edge of a text or sprite its `x` names.
#[derive(Debug, Clone, Copy, PartialEq, Eq, Default)]
pub enum HorizontalAlign {
    /// `x` is the left edge.
    #[default]
    Left,
    /// `x` is the middle of the text.
    Center,
    /// `x` is the right edge.
    Right,
}

/// One drawing primitive. Coordinates are canvas pixels unless anchored to the world.
#[derive(Debug, Clone, PartialEq)]
pub enum OverlayCommand {
    /// Text drawn with the client's interface font.
    Text {
        /// Text, at most 256 bytes.
        text: String,
        /// Edge named by `align`.
        x: i32,
        /// Baseline y.
        y: i32,
        /// Packed `AARRGGBB`.
        argb: u32,
        /// Which edge `x` names; the host measures the text, the plugin cannot.
        align: HorizontalAlign,
    },
    /// An interface sprite from the cache, drawn at its stored pixel size.
    Sprite {
        /// Sprite archive id.
        sprite: u32,
        /// Edge of the sprite named by `align`.
        x: i32,
        /// Top edge of the sprite.
        y: i32,
        /// Which edge `x` names; the host knows the sprite's size, the plugin does not.
        align: HorizontalAlign,
    },
    /// A rectangle.
    Rect {
        /// Left edge.
        x: i32,
        /// Top edge.
        y: i32,
        /// Width.
        width: i32,
        /// Height.
        height: i32,
        /// Packed `AARRGGBB`.
        argb: u32,
        /// Whether the rectangle is filled; otherwise a one pixel outline.
        filled: bool,
    },
    /// A line segment.
    Line {
        /// Start point.
        from: (i32, i32),
        /// End point.
        to: (i32, i32),
        /// Packed `AARRGGBB`.
        argb: u32,
    },
    /// A tile outline projected from the world.
    Tile {
        /// Tile to outline.
        tile: WorldPoint,
        /// Packed `AARRGGBB`.
        argb: u32,
        /// Fill colour (`AARRGGBB`, alpha 0 for none).
        fill_argb: u32,
    },
    /// The projected convex hull of an actor.
    ActorHull {
        /// The actor.
        actor: ActorRef,
        /// Packed `AARRGGBB`.
        argb: u32,
        /// Fill colour (`AARRGGBB`, alpha 0 for none).
        fill_argb: u32,
    },
    /// Text attached above an actor.
    ActorText {
        /// The actor.
        actor: ActorRef,
        /// Text, at most 256 bytes.
        text: String,
        /// Extra height above the actor's model top, in local units.
        z_offset: i32,
        /// Packed `AARRGGBB`.
        argb: u32,
    },
    /// A dot on the minimap at a world tile.
    MinimapDot {
        /// Tile.
        tile: WorldPoint,
        /// Packed `AARRGGBB`.
        argb: u32,
    },
}

/// One overlay command with its layer.
#[derive(Debug, Clone, PartialEq)]
pub struct LayeredOverlay {
    /// Draw ordering.
    pub layer: OverlayLayer,
    /// The primitive.
    pub command: OverlayCommand,
}

/// How a notification should be surfaced.
#[derive(Debug, Clone, Copy, PartialEq, Eq, Default)]
pub enum NotificationKind {
    /// A system notification when the window is unfocused, a game message otherwise.
    #[default]
    Standard,
    /// Also flash the window.
    Flash,
}

/// A user-facing notification request.
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct Notification {
    /// Short title, at most 64 bytes.
    pub title: String,
    /// Body text, at most 256 bytes.
    pub body: String,
    /// Delivery style.
    pub kind: NotificationKind,
}

/// A small status box drawn in the info box column.
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct InfoBox {
    /// Stable id within the plugin, at most 64 bytes.
    pub id: String,
    /// Short text drawn on the box, at most 16 bytes.
    pub text: String,
    /// Packed `AARRGGBB` text colour.
    pub argb: u32,
    /// Hover tooltip, at most 256 bytes.
    pub tooltip: String,
}

/// An RGBA replacement for one interface sprite.
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct SpriteOverride {
    /// Sprite archive id being replaced.
    pub sprite: u32,
    /// Replacement width in pixels, at most 256.
    pub width: u16,
    /// Replacement height in pixels, at most 256.
    pub height: u16,
    /// Row-major RGBA8, `width * height * 4` bytes.
    pub rgba: Arc<[u8]>,
}

/// A request to move the camera.
#[derive(Debug, Clone, Copy, PartialEq)]
pub enum CameraCommand {
    /// Ease the yaw toward a target in 0..2048 angle units.
    YawTarget(i32),
    /// Ease the pitch toward a target in 128..=383 angle units.
    PitchTarget(i32),
    /// Camera easing speed multiplier.
    Speed(f32),
    /// Zoom scale target.
    ZoomTarget(i32),
    /// Permitted zoom scale range.
    ZoomLimits {
        /// Smallest zoom scale.
        min: i32,
        /// Largest zoom scale.
        max: i32,
    },
    /// Invert horizontal mouse rotation.
    InvertYaw(bool),
    /// Invert vertical mouse rotation.
    InvertPitch(bool),
}

/// Actors and effects the renderer must skip while drawing the scene.
#[derive(Debug, Clone, Default, PartialEq, Eq)]
pub struct EntityVisibility {
    /// Actors whose model is not drawn and not clickable.
    pub hidden: Vec<ActorRef>,
    /// Actors whose 2D overlays (overhead text, health bars, hitsplats) are not drawn.
    pub hidden_overlays: Vec<ActorRef>,
    /// Skip projectiles.
    pub hide_projectiles: bool,
    /// Skip spot animations and other graphics objects.
    pub hide_graphics: bool,
}

/// Which roofs the scene builder omits, as `net.runelite.api.Scene.ROOF_FLAG_*` bits.
#[derive(Debug, Clone, Copy, PartialEq, Eq, Default)]
pub struct RoofModes(u8);

impl RoofModes {
    /// The roof above the local player.
    pub const POSITION: u8 = 1;
    /// The roof above the hovered tile.
    pub const HOVERED: u8 = 2;
    /// The roof above the walk destination.
    pub const DESTINATION: u8 = 4;
    /// The roofs between the camera and the local player.
    pub const BETWEEN: u8 = 8;

    /// Roof removal for the modes in `mask`.
    #[must_use]
    pub const fn new(mask: u8) -> Self {
        Self(mask & 0b1111)
    }

    /// The mode bits.
    #[must_use]
    pub const fn bits(self) -> u8 {
        self.0
    }

    /// Whether every mode in `mask` is active.
    #[must_use]
    pub const fn contains(self, mask: u8) -> bool {
        self.0 & mask == mask
    }

    /// Modes active in either value.
    #[must_use]
    pub const fn union(self, other: Self) -> Self {
        Self(self.0 | other.0)
    }
}

/// Tiles forced to count as roofed regardless of the map data (`RoofRemovalPlugin` overrides).
#[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord, Hash)]
pub struct RoofArea {
    /// South-west world tile, inclusive.
    pub min: (i32, i32),
    /// North-east world tile, inclusive.
    pub max: (i32, i32),
    /// Lowest and highest plane, inclusive.
    pub planes: (u8, u8),
}

/// Which animation classes the renderer interpolates between frames.
#[derive(Debug, Clone, Copy, PartialEq, Eq, Default)]
pub struct AnimationSmoothing(u8);

impl AnimationSmoothing {
    /// Player action and pose animations.
    pub const PLAYERS: u8 = 1;
    /// NPC action and pose animations.
    pub const NPCS: u8 = 2;
    /// Scenery object animations.
    pub const OBJECTS: u8 = 4;
    /// Widget model animations.
    pub const WIDGETS: u8 = 8;

    /// Smoothing for the classes in `mask`.
    #[must_use]
    pub const fn new(mask: u8) -> Self {
        Self(mask & 0b1111)
    }

    /// Whether every class in `mask` is smoothed.
    #[must_use]
    pub const fn contains(self, mask: u8) -> bool {
        self.0 & mask == mask
    }

    /// Classes smoothed by either value.
    #[must_use]
    pub const fn union(self, other: Self) -> Self {
        Self(self.0 | other.0)
    }
}

/// Renderer/scene flags a plugin may set.
#[derive(Debug, Clone, Copy, PartialEq, Eq, Default)]
pub struct SceneFlags {
    /// Which roofs are hidden around the player, pointer and camera.
    pub roofs: RoofModes,
    /// Interpolated animation classes.
    pub smoothing: AnimationSmoothing,
}

/// How the canvas is scaled to the window.
#[derive(Debug, Clone, Copy, PartialEq, Eq, Default)]
pub enum StretchScaling {
    /// Nearest neighbour.
    #[default]
    Nearest,
    /// Bilinear.
    Linear,
}

/// Stretched-mode request (`Client.setStretchedEnabled` and its companions).
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct Stretch {
    /// Keep the canvas aspect ratio, letterboxing the rest.
    pub keep_aspect_ratio: bool,
    /// Use whole-number scale factors only.
    pub integer_scaling: bool,
    /// `Client.setScalingFactor`: in resizable modes the game canvas is the window divided by
    /// `1 + scale_percent / 100`, then stretched back up; fixed mode ignores it.
    pub scale_percent: i16,
    /// Resampling the source asks for (`increasedPerformance` selects [`StretchScaling::Nearest`]).
    pub scaling: StretchScaling,
}

impl Stretch {
    /// Lowest `scale_percent` the host accepts (a factor of one half).
    pub const MIN_SCALE_PERCENT: i16 = -50;
    /// Highest `scale_percent` the host accepts.
    pub const MAX_SCALE_PERCENT: i16 = 800;
}

/// Everything one plugin wants this update.
#[derive(Debug, Clone, Default, PartialEq)]
pub struct PluginOutput {
    /// Drawing primitives, back to front within a layer.
    pub overlay: Vec<LayeredOverlay>,
    /// Notifications to raise now.
    pub notifications: Vec<Notification>,
    /// Info boxes to show while present.
    pub info_boxes: Vec<InfoBox>,
    /// Edits to the last sorted menu.
    pub menu_edits: Vec<MenuEdit>,
    /// Actors to hide.
    pub entities: Option<EntityVisibility>,
    /// Interface sprites to replace while present.
    pub sprite_overrides: Vec<SpriteOverride>,
    /// Camera requests to apply now.
    pub camera: Vec<CameraCommand>,
    /// Scene flags, if the plugin controls them.
    pub scene: Option<SceneFlags>,
    /// Areas forced roofed while present; changing them rebuilds the scene's roofs.
    pub roof_areas: Vec<RoofArea>,
    /// Stretched mode, if the plugin controls it.
    pub stretch: Option<Stretch>,
    /// Animation ids that stay on whole frames while [`SceneFlags::smoothing`] is active
    /// (`Client.setAnimationInterpolationFilter` rejecting them).
    pub smoothing_denied: Vec<u32>,
    /// Frames per second the client may present at most; the lowest request among plugins applies.
    pub frame_limit: Option<u32>,
}

impl PluginOutput {
    /// Whether the plugin asked for nothing.
    #[must_use]
    pub fn is_empty(&self) -> bool {
        *self == Self::default()
    }
}

/// Bounds the host enforces on every plugin output.
pub mod limits {
    /// Overlay commands accepted per plugin per update.
    pub const OVERLAY_COMMANDS: usize = 4096;
    /// Notifications accepted per plugin per update.
    pub const NOTIFICATIONS: usize = 4;
    /// Info boxes accepted per plugin.
    pub const INFO_BOXES: usize = 32;
    /// Menu edits accepted per plugin per update.
    pub const MENU_EDITS: usize = 64;
    /// Hidden actors accepted per plugin.
    pub const HIDDEN_ACTORS: usize = 2048;
    /// Sprite overrides accepted per plugin.
    pub const SPRITE_OVERRIDES: usize = 64;
    /// Roof areas accepted per plugin.
    pub const ROOF_AREAS: usize = 4096;
    /// Animation ids a plugin may exclude from smoothing.
    pub const SMOOTHING_DENIED: usize = 4096;
    /// Camera commands accepted per plugin per update.
    pub const CAMERA_COMMANDS: usize = 8;
    /// Lowest accepted frame limit.
    pub const MIN_FRAME_LIMIT: u32 = 1;
    /// Highest accepted frame limit.
    pub const MAX_FRAME_LIMIT: u32 = 360;
    /// Longest overlay/notification text in bytes.
    pub const TEXT_BYTES: usize = 256;
    /// Largest sprite edge in pixels.
    pub const SPRITE_EDGE: u16 = 256;
}
