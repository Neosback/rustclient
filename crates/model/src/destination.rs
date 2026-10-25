//! Requested minimap flag target, independent of confirmed actor position.

/// A world-tile destination has no plane; the active map supplies its projection.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct MapDestination {
    pub x: i32,
    pub y: i32,
}
