//! Authoritative ground items (stock `WorldView` tile deques of `TileItem`).
//!
//! Each tile holds its items in arrival order, oldest first, exactly like the stock deque
//! (`Deque2.method7946` appends; handlers iterate from the head). Matching rules follow the stock
//! handlers: delete, count, customise and uncustomise touch the first item with the same id and
//! quantity; enabling ops touches the first item with the same id. Nothing here predicts
//! despawns: the server deletes items, and the public/despawn ticks are retained facts only.
use crate::state::SessionLifecycle;
use crate::{GameState, OperationCommit, OperationError, SemanticEvent, StateDelta};
use core::fmt;
use rustclient_model::{
    ActionMask, GameTick, GroundItemCustomisation, GroundItemOwnership, GroundItemPosition,
    GroundItemUpdate, ItemDefinitionId, Plane, TileOffset, WorldTile,
};
use std::collections::BTreeMap;

/// Most items retained across the whole map window; a server that exceeds it is rejected rather
/// than letting memory grow without bound.
pub const GROUND_ITEM_LIMIT: usize = 65_536;

/// One item lying on a tile.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct GroundItem {
    pub item: ItemDefinitionId,
    pub quantity: u32,
    /// Ground options the server leaves enabled.
    pub ops: ActionMask,
    pub ownership: GroundItemOwnership,
    /// Tick at which the item becomes visible to everyone.
    pub public_at: GameTick,
    /// Tick at which the server will despawn the item.
    pub despawn_at: GameTick,
    pub never_public: bool,
    pub customisation: Option<GroundItemCustomisation>,
}

/// Ground item transitions.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum GroundItemOperation {
    /// Apply one decoded update.
    Update(GroundItemUpdate),
    /// Remove every item in a rectangle (`UPDATE_ZONE_FULL_FOLLOWS` clears its 8x8 zone).
    ResetArea {
        offset: TileOffset,
        plane: Plane,
        size: [u32; 2],
    },
}

#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum GroundItemError {
    InactiveSession,
    /// The update needs a map anchor that has not been placed yet.
    NoMap,
    Overflow,
    Limit,
    InvalidArea,
}
impl fmt::Display for GroundItemError {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        f.write_str(match self {
            Self::InactiveSession => "ground item update requires an active session",
            Self::NoMap => "ground item update requires a placed map",
            Self::Overflow => "ground item coordinate or tick overflow",
            Self::Limit => "ground item limit exceeded",
            Self::InvalidArea => "invalid ground item reset area",
        })
    }
}
impl std::error::Error for GroundItemError {}

/// Every ground item in the current map window, by tile.
#[derive(Debug, Clone, Default, PartialEq, Eq)]
pub struct GroundItemsState {
    piles: BTreeMap<WorldTile, Vec<GroundItem>>,
    total: usize,
}
impl GroundItemsState {
    /// The items on `tile`, oldest first.
    #[must_use]
    pub fn pile(&self, tile: WorldTile) -> &[GroundItem] {
        self.piles.get(&tile).map_or(&[], Vec::as_slice)
    }
    /// Every non-empty pile, ordered by tile.
    #[must_use]
    pub fn piles(&self) -> impl ExactSizeIterator<Item = (WorldTile, &[GroundItem])> {
        self.piles
            .iter()
            .map(|(tile, items)| (*tile, items.as_slice()))
    }
    /// Number of items over all piles.
    #[must_use]
    pub const fn len(&self) -> usize {
        self.total
    }
    #[must_use]
    pub const fn is_empty(&self) -> bool {
        self.total == 0
    }
    pub(crate) fn clear(&mut self) -> bool {
        let changed = self.total != 0;
        self.piles.clear();
        self.total = 0;
        changed
    }
    fn first_mut(
        &mut self,
        tile: WorldTile,
        item: ItemDefinitionId,
        quantity: Option<u32>,
    ) -> Option<&mut GroundItem> {
        self.piles.get_mut(&tile)?.iter_mut().find(|entry| {
            entry.item == item && quantity.is_none_or(|quantity| entry.quantity == quantity)
        })
    }
    /// Apply one update at an already-resolved tile; returns whether anything changed.
    fn apply(
        &mut self,
        tile: WorldTile,
        update: GroundItemUpdate,
        now: GameTick,
    ) -> Result<bool, GroundItemError> {
        Ok(match update {
            GroundItemUpdate::Add {
                item,
                quantity,
                ops,
                ownership,
                ticks_until_public,
                ticks_until_despawn,
                never_public,
                ..
            } => {
                if self.total >= GROUND_ITEM_LIMIT {
                    return Err(GroundItemError::Limit);
                }
                let at = |ticks: u16| {
                    now.get()
                        .checked_add(u64::from(ticks))
                        .map(GameTick::new)
                        .ok_or(GroundItemError::Overflow)
                };
                let entry = GroundItem {
                    item,
                    quantity,
                    ops,
                    ownership,
                    public_at: at(ticks_until_public)?,
                    despawn_at: at(ticks_until_despawn)?,
                    never_public,
                    customisation: None,
                };
                self.piles.entry(tile).or_default().push(entry);
                self.total += 1;
                true
            }
            GroundItemUpdate::Delete { item, quantity, .. } => {
                let Some(pile) = self.piles.get_mut(&tile) else {
                    return Ok(false);
                };
                let Some(index) = pile
                    .iter()
                    .position(|entry| entry.item == item && entry.quantity == quantity)
                else {
                    return Ok(false);
                };
                pile.remove(index);
                if pile.is_empty() {
                    self.piles.remove(&tile);
                }
                self.total -= 1;
                true
            }
            GroundItemUpdate::Count { item, old, new, .. } => {
                self.first_mut(tile, item, Some(old)).is_some_and(|entry| {
                    let changed = entry.quantity != new;
                    entry.quantity = new;
                    changed
                })
            }
            GroundItemUpdate::EnabledOps { item, ops, .. } => {
                self.first_mut(tile, item, None).is_some_and(|entry| {
                    let changed = entry.ops != ops;
                    entry.ops = ops;
                    changed
                })
            }
            GroundItemUpdate::Customise {
                item,
                quantity,
                customisation,
                ..
            } => self
                .first_mut(tile, item, Some(quantity))
                .is_some_and(|entry| {
                    let changed = entry.customisation != Some(customisation);
                    entry.customisation = Some(customisation);
                    changed
                }),
            GroundItemUpdate::Uncustomise { item, quantity, .. } => self
                .first_mut(tile, item, Some(quantity))
                .is_some_and(|entry| entry.customisation.take().is_some()),
        })
    }
    fn reset_area(&mut self, origin: WorldTile, size: [u32; 2]) -> bool {
        let [width, height] = size.map(|n| i32::try_from(n).unwrap_or(i32::MAX));
        let before = self.total;
        self.piles.retain(|tile, items| {
            let inside = tile.plane == origin.plane
                && (origin.x..origin.x.saturating_add(width)).contains(&tile.x)
                && (origin.y..origin.y.saturating_add(height)).contains(&tile.y);
            if inside {
                self.total -= items.len();
            }
            !inside
        });
        self.total != before
    }
}

impl GameState {
    #[must_use]
    pub const fn ground_items(&self) -> &GroundItemsState {
        &self.ground_items
    }
    pub(crate) fn apply_ground_items(
        &mut self,
        op: GroundItemOperation,
    ) -> Result<OperationCommit, OperationError> {
        self.change_ground_items(op)
            .map_err(OperationError::GroundItems)
    }
    fn ground_item_tile(
        &self,
        offset: TileOffset,
        plane: Plane,
    ) -> Result<WorldTile, GroundItemError> {
        let anchor = self
            .initial_placement()
            .ok_or(GroundItemError::NoMap)?
            .map_anchor;
        Ok(WorldTile::new(
            anchor
                .x
                .checked_add(offset.x)
                .ok_or(GroundItemError::Overflow)?,
            anchor
                .y
                .checked_add(offset.y)
                .ok_or(GroundItemError::Overflow)?,
            plane,
        ))
    }
    fn change_ground_items(
        &mut self,
        op: GroundItemOperation,
    ) -> Result<OperationCommit, GroundItemError> {
        if self.lifecycle() != SessionLifecycle::Active {
            return Err(GroundItemError::InactiveSession);
        }
        let changed = match op {
            GroundItemOperation::Update(update) => {
                let tile = match update.position() {
                    GroundItemPosition::Zone { offset, plane } => {
                        self.ground_item_tile(offset, plane)?
                    }
                    GroundItemPosition::Absolute(tile) => {
                        // Stock `WorldView.method3747`: an absolute tile outside the map
                        // window is ignored, like a zone body outside it.
                        let anchor = self
                            .initial_placement()
                            .ok_or(GroundItemError::NoMap)?
                            .map_anchor;
                        let inside = |value: i32, origin: i32| {
                            value
                                .checked_sub(origin)
                                .is_some_and(|d| (-48..55).contains(&d))
                        };
                        if !inside(tile.x, anchor.x) || !inside(tile.y, anchor.y) {
                            return Ok(OperationCommit::new(None, StateDelta::default()));
                        }
                        tile
                    }
                };
                let now = self.current_tick().unwrap_or(GameTick::ZERO);
                self.ground_items
                    .apply(tile, update, now)?
                    .then_some(SemanticEvent::GroundItemsChanged { tile })
            }
            GroundItemOperation::ResetArea {
                offset,
                plane,
                size,
            } => {
                if size.contains(&0) || size.iter().any(|s| *s > 192) {
                    return Err(GroundItemError::InvalidArea);
                }
                let origin = self.ground_item_tile(offset, plane)?;
                self.ground_items
                    .reset_area(origin, size)
                    .then_some(SemanticEvent::GroundItemAreaReset { origin, size })
            }
        };
        Ok(match changed {
            Some(event) => OperationCommit::new(Some(event), StateDelta::GROUND_ITEMS),
            None => OperationCommit::new(None, StateDelta::default()),
        })
    }
}
