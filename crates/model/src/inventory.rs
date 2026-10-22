//! Source-neutral inventory updates; quantities may be zero for placeholders.
use crate::{InventoryId, InventorySlot, ItemDefinitionId};

#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct ItemStack {
    pub item: ItemDefinitionId,
    pub amount: u32,
}

#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct InventorySlotUpdate {
    pub slot: InventorySlot,
    pub stack: Option<ItemStack>,
}

/// Full synchronization, ordered slot changes, or removal of a cached container.
#[derive(Debug, Clone, PartialEq, Eq)]
pub enum InventoryUpdate {
    Replace {
        id: InventoryId,
        slots: Vec<Option<ItemStack>>,
    },
    Patch {
        id: InventoryId,
        slots: Vec<InventorySlotUpdate>,
    },
    Remove(InventoryId),
}
impl InventoryUpdate {
    #[must_use]
    pub const fn id(&self) -> InventoryId {
        match self {
            Self::Replace { id, .. } | Self::Patch { id, .. } | Self::Remove(id) => *id,
        }
    }
}
