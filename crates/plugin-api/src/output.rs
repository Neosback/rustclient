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

/// One drawing primitive. Coordinates are canvas pixels unless anchored to the world.
#[derive(Debug, Clone, PartialEq)]
pub enum OverlayCommand {
    /// Text drawn with the client's interface font.
    Text {
        /// Text, at most 256 bytes.
        text: String,
        /// Left edge.
        x: i32,
        /// Baseline y.
        y: i32,
        /// Packed `AARRGGBB`.
        argb: u32,
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
    /// Actors that are not drawn and not clickable.
    pub hidden: Vec<ActorRef>,
    /// Skip projectiles.
    pub hide_projectiles: bool,
    /// Skip spot animations and other graphics objects.
    pub hide_graphics: bool,
}

/// Which roofs the scene builder omits.
#[derive(Debug, Clone, Copy, PartialEq, Eq, Default)]
pub enum RoofRemoval {
    /// Draw roofs as the client does.
    #[default]
    Off,
    /// Omit every roof.
    All,
    /// Omit roofs on planes above the local player's plane.
    AbovePlayer,
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
    /// Roof handling.
    pub roofs: RoofRemoval,
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

/// Stretched-mode request.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct Stretch {
    /// Keep the canvas aspect ratio, letterboxing the rest.
    pub keep_aspect_ratio: bool,
    /// Scale factor in percent, 100 for exact fit.
    pub scale_percent: u16,
    /// Resampling.
    pub scaling: StretchScaling,
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
    /// Stretched mode, if the plugin controls it.
    pub stretch: Option<Stretch>,
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
    /// Camera commands accepted per plugin per update.
    pub const CAMERA_COMMANDS: usize = 8;
    /// Longest overlay/notification text in bytes.
    pub const TEXT_BYTES: usize = 256;
    /// Largest sprite edge in pixels.
    pub const SPRITE_EDGE: u16 = 256;
}
