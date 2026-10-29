//! Source-neutral scenery identities and explicit animation-start inputs.
use crate::{ClientCycle, LocOrientation, ObjectDefinitionId, SequenceId, WorldTile};
#[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord, Hash)]
pub enum LocationLayer {
    Boundary,
    WallDecoration,
    GameObject,
    FloorDecoration,
}

impl LocationLayer {
    #[must_use]
    pub const fn from_shape(shape: u8) -> Self {
        match shape {
            0..=3 => Self::Boundary,
            4..=8 => Self::WallDecoration,
            22 => Self::FloorDecoration,
            _ => Self::GameObject,
        }
    }
}

#[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord, Hash)]
pub struct LocationKey {
    pub tile: WorldTile,
    pub layer: LocationLayer,
}

impl LocationKey {
    #[must_use]
    pub const fn new(tile: WorldTile, layer: LocationLayer) -> Self {
        Self { tile, layer }
    }
}
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct LocationDefinition {
    pub object: ObjectDefinitionId,
    pub shape: u8,
    pub orientation: LocOrientation,
}
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum LocationChange {
    Inherit,
    Remove,
    Replace(LocationDefinition),
}
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct AnimationStart {
    pub placement: Option<LocationAnimationPlacement>,
    /// None explicitly stops the inherited animation.
    pub sequence: Option<SequenceId>,
    pub cycle: ClientCycle,
    pub random_seed: u64,
}

/// Shape and orientation requested when replacing a location's animated renderable.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct LocationAnimationPlacement {
    pub shape: u8,
    pub orientation: LocOrientation,
}

/// Per-placement server overrides of the five definition-authored location actions.
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct LocationOptions {
    pub enabled: crate::ActionMask,
    pub labels: [Option<String>; 5],
}
impl Default for LocationOptions {
    fn default() -> Self {
        Self {
            enabled: crate::ActionMask::ALL,
            labels: Default::default(),
        }
    }
}
