//! Authoritative bounded inventory containers.
use crate::{ProjectionChange, projection::ProjectionJournal};
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
    #[must_use]
    pub fn iter(&self) -> impl ExactSizeIterator<Item = (InventoryId, &[Option<ItemStack>])> {
        self.containers
            .iter()
            .map(|(id, slots)| (*id, slots.as_slice()))
    }
    pub(crate) fn apply(
        &mut self,
        update: InventoryUpdate,
        journal: &mut ProjectionJournal,
    ) -> Result<bool, InventoryError> {
        let id = update.id();
        if matches!(update, InventoryUpdate::Remove(_)) {
            let changed = self.containers.remove(&id).is_some();
            if changed {
                journal.push(ProjectionChange::Inventory(id));
            }
            return Ok(changed);
        }
        if self.containers.len() >= MAX_CONTAINERS && !self.containers.contains_key(&id) {
            return Err(InventoryError::LimitExceeded { id });
        }
        match update {
            InventoryUpdate::Replace { slots, .. } => {
                if slots.len() > MAX_SLOTS {
                    return Err(InventoryError::LimitExceeded { id });
                }
                if self.containers.get(&id) == Some(&slots) {
                    return Ok(false);
                }
                self.containers.insert(id, slots);
                journal.push(ProjectionChange::Inventory(id));
                Ok(true)
            }
            InventoryUpdate::Patch { slots, .. } => {
                if slots.len() > MAX_SLOTS || slots.iter().any(|s| s.slot.get() >= 16_384) {
                    return Err(InventoryError::LimitExceeded { id });
                }
                // All fallible checks precede mutation. Reverse traversal applies each slot's
                // final assignment once, so cancelling intermediate edits remain a no-op.
                let length = slots
                    .iter()
                    .map(|s| s.slot.get() as usize + 1)
                    .max()
                    .unwrap_or(0);
                let (container, existed) = match self.containers.entry(id) {
                    std::collections::btree_map::Entry::Occupied(entry) => (entry.into_mut(), true),
                    std::collections::btree_map::Entry::Vacant(entry) => {
                        (entry.insert(Vec::new()), false)
                    }
                };
                let shape_changed = !existed || length > container.len();
                if length > container.len() {
                    container.resize(length, None);
                }
                let mut changed = shape_changed;
                if let [change] = slots.as_slice() {
                    let index = change.slot.get() as usize;
                    if container[index] != change.stack {
                        container[index] = change.stack;
                        journal.push(ProjectionChange::InventorySlot {
                            id,
                            slot: change.slot,
                        });
                        changed = true;
                    }
                    if shape_changed {
                        journal.push(ProjectionChange::Inventory(id));
                    }
                    return Ok(changed);
                }
                let mut seen = [0_u64; MAX_SLOTS / 64];
                for change in slots.iter().rev() {
                    let index = change.slot.get() as usize;
                    let bit = 1_u64 << (index % 64);
                    if seen[index / 64] & bit != 0 {
                        continue;
                    }
                    seen[index / 64] |= bit;
                    if container[index] != change.stack {
                        container[index] = change.stack;
                        journal.push(ProjectionChange::InventorySlot {
                            id,
                            slot: change.slot,
                        });
                        changed = true;
                    }
                }
                if shape_changed {
                    journal.push(ProjectionChange::Inventory(id));
                }
                Ok(changed)
            }
            InventoryUpdate::Remove(_) => unreachable!("removal handled above"),
        }
    }
}
