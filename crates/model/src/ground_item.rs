//! Server-owned item stacks lying on world tiles ("ground items", stock `TileItem`).
use crate::{ActionMask, ItemDefinitionId, ModelId, Plane, TileOffset, WorldTile};

/// Who may see and pick up a ground item (stock `TileItem.ownership`, `RuneLite`
/// `TileItem.OWNERSHIP_*`). Values outside the known four are retained unchanged.
#[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord, Hash)]
pub struct GroundItemOwnership(pub u8);
impl GroundItemOwnership {
    /// Visible to everyone (or ownership unknown).
    pub const NONE: Self = Self(0);
    /// Dropped by or for the local player.
    pub const SELF: Self = Self(1);
    /// Owned by another player.
    pub const OTHER: Self = Self(2);
    /// Owned by the local player's group (group ironman).
    pub const GROUP: Self = Self(3);
}

/// A model, recolour and retexture override the server applied to one ground item.
#[derive(Debug, Clone, Copy, PartialEq, Eq, Default)]
pub struct GroundItemCustomisation {
    /// Replacement model, if any.
    pub model: Option<ModelId>,
    /// `(index, colour)` replacing one recolour target, if any.
    pub recolour: Option<(u16, u16)>,
    /// `(index, texture)` replacing one retexture target, if any.
    pub retexture: Option<(u16, u16)>,
}

/// Where an update applies. Zone payloads are relative to the current map anchor like location
/// updates; `*_SPECIFIC` packets name an absolute tile.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum GroundItemPosition {
    /// Map-relative offset from a zone payload.
    Zone {
        /// Offset from the map anchor.
        offset: TileOffset,
        /// Plane of the zone.
        plane: Plane,
    },
    /// Absolute world tile.
    Absolute(WorldTile),
}

/// One semantic ground item update, after revision-specific decoding.
///
/// Matching follows the reference: deletion, count, customisation and uncustomisation act on the
/// first item of the tile's pile (oldest first) with the same id and quantity; enabling ops acts
/// on the first item with the same id.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum GroundItemUpdate {
    /// Append an item to the tile's pile.
    Add {
        position: GroundItemPosition,
        item: ItemDefinitionId,
        quantity: u32,
        /// Ground options the server leaves enabled.
        ops: ActionMask,
        ownership: GroundItemOwnership,
        /// Ticks until the item becomes visible to everyone.
        ticks_until_public: u16,
        /// Ticks until the item despawns.
        ticks_until_despawn: u16,
        /// The item never becomes public.
        never_public: bool,
    },
    /// Remove the first matching item.
    Delete {
        position: GroundItemPosition,
        item: ItemDefinitionId,
        quantity: u32,
    },
    /// Change the quantity of the first item with `old` quantity.
    Count {
        position: GroundItemPosition,
        item: ItemDefinitionId,
        old: u32,
        new: u32,
    },
    /// Replace which ground options are enabled.
    EnabledOps {
        position: GroundItemPosition,
        item: ItemDefinitionId,
        ops: ActionMask,
    },
    /// Apply a model/recolour/retexture override.
    Customise {
        position: GroundItemPosition,
        item: ItemDefinitionId,
        quantity: u32,
        customisation: GroundItemCustomisation,
    },
    /// Remove a customisation.
    Uncustomise {
        position: GroundItemPosition,
        item: ItemDefinitionId,
        quantity: u32,
    },
}
impl GroundItemUpdate {
    /// Where the update applies.
    #[must_use]
    pub const fn position(&self) -> GroundItemPosition {
        match *self {
            Self::Add { position, .. }
            | Self::Delete { position, .. }
            | Self::Count { position, .. }
            | Self::EnabledOps { position, .. }
            | Self::Customise { position, .. }
            | Self::Uncustomise { position, .. } => position,
        }
    }
}
