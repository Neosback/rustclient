//! Authoritative bounded inventory containers.
use rustclient_model::{InventoryId, InventoryUpdate, ItemStack};
use std::collections::BTreeMap;

const MAX_CONTAINERS: usize = 256;
const MAX_SLOTS: usize = 16_384;

#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum InventoryError {
    LimitExceeded { id: InventoryId },
}
impl core::fmt::Display for InventoryError {
    fn fmt(&self, f: &mut core::fmt::Formatter<'_>) -> core::fmt::Result {
        match self {
            Self::LimitExceeded { id } => {
                write!(f, "inventory {id}: container or slot limit exceeded")
            }
        }
    }
}
impl std::error::Error for InventoryError {}

#[derive(Debug, Default)]
pub struct InventoryState {
    containers: BTreeMap<InventoryId, Vec<Option<ItemStack>>>,
}
impl InventoryState {
    /// Current transmitted slots; an absent container differs from an empty one.
    #[must_use]
    pub fn get(&self, id: InventoryId) -> Option<&[Option<ItemStack>]> {
        self.containers.get(&id).map(Vec::as_slice)
    }
    pub fn iter(&self) -> impl ExactSizeIterator<Item = (InventoryId, &[Option<ItemStack>])> {
        self.containers
            .iter()
            .map(|(id, slots)| (*id, slots.as_slice()))
    }
    pub(crate) fn apply(&mut self, update: InventoryUpdate) -> Result<bool, InventoryError> {
        let id = update.id();
        if matches!(update, InventoryUpdate::Remove(_)) {
            return Ok(self.containers.remove(&id).is_some());
        }
        if !self.containers.contains_key(&id) && self.containers.len() >= MAX_CONTAINERS {
            return Err(InventoryError::LimitExceeded { id });
        }
        // Build the bounded final container before mutating the authority. This
        // also suppresses events for patches whose intermediate edits cancel out.
        let candidate = match update {
            InventoryUpdate::Replace { slots, .. } => {
                if slots.len() > MAX_SLOTS {
                    return Err(InventoryError::LimitExceeded { id });
                }
                slots
            }
            InventoryUpdate::Patch { slots, .. } => {
                if slots.len() > MAX_SLOTS || slots.iter().any(|s| s.slot.get() >= 16_384) {
                    return Err(InventoryError::LimitExceeded { id });
                }
                let mut candidate = self.containers.get(&id).cloned().unwrap_or_default();
                for change in slots {
                    let index = usize::try_from(change.slot.get())
                        .map_err(|_| InventoryError::LimitExceeded { id })?;
                    if candidate.len() <= index {
                        candidate.resize(index + 1, None);
                    }
                    candidate[index] = change.stack;
                }
                candidate
            }
            InventoryUpdate::Remove(_) => return Ok(self.containers.remove(&id).is_some()),
        };
        if self.containers.get(&id) == Some(&candidate) {
            return Ok(false);
        }
        self.containers.insert(id, candidate);
        Ok(true)
    }
}
