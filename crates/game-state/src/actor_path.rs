//! Bounded authoritative actor path queue shared by players and NPCs.

use rustclient_model::{MoveSpeed, WorldTile};

/// Local fine-coordinate units per world tile used by the stock actor scheduler.
pub const ACTOR_FINE_UNITS_PER_TILE: i64 = 128;
/// Maximum number of active path requests retained by revision-240 actor scheduling.
pub const ACTOR_PATH_CAPACITY: usize = 9;

/// Revision-neutral world fine position normalized to a tile's south-west footprint corner.
///
/// The stock client stores footprint-centred fine coordinates. Keeping the constant footprint
/// offset out of authoritative path state preserves exactly the same deltas, reach tests and snap
/// thresholds without making actor-definition size a dependency of the shared scheduler.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct ActorFinePosition {
    pub x: i64,
    pub y: i64,
}

impl ActorFinePosition {
    #[must_use]
    pub fn from_tile(tile: WorldTile) -> Self {
        Self {
            x: i64::from(tile.x) * ACTOR_FINE_UNITS_PER_TILE,
            y: i64::from(tile.y) * ACTOR_FINE_UNITS_PER_TILE,
        }
    }

    #[must_use]
    pub const fn units(self) -> [i64; 2] {
        [self.x, self.y]
    }
}

/// One queued actor path target and its authored traversal mode.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct ActorPathEntry {
    pub target: WorldTile,
    pub speed: MoveSpeed,
}

/// Bounded newest-first queue plus the authoritative fine-position scheduler cursor.
///
/// New entries are inserted at index zero. The scheduler consumes the oldest active entry from
/// the tail. When full, inserting another request drops the previous oldest entry, matching the
/// stock client's length-9 active window over its ten-element backing arrays.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct ActorPathQueue {
    entries: [Option<ActorPathEntry>; ACTOR_PATH_CAPACITY],
    len: u8,
    /// Lazily initialized so actor construction can remain tile-native until sub-tile movement is
    /// first required. Once initialized this value is authoritative and must not be recomputed from
    /// the server-confirmed endpoint.
    fine_position: Option<ActorFinePosition>,
}

impl Default for ActorPathQueue {
    fn default() -> Self {
        Self {
            entries: [None; ACTOR_PATH_CAPACITY],
            len: 0,
            fine_position: None,
        }
    }
}

impl ActorPathQueue {
    #[must_use]
    pub const fn len(self) -> u8 {
        self.len
    }

    #[must_use]
    pub const fn backlog(self) -> u32 {
        u32::from(self.len)
    }

    #[must_use]
    pub const fn is_empty(self) -> bool {
        self.len == 0
    }

    /// Current authoritative fine position, falling back to the actor's confirmed tile only until
    /// the scheduler has initialized its cursor.
    #[must_use]
    pub fn fine_position(self, fallback: WorldTile) -> ActorFinePosition {
        match self.fine_position {
            Some(position) => position,
            None => ActorFinePosition::from_tile(fallback),
        }
    }

    #[must_use]
    pub fn newest(self) -> Option<ActorPathEntry> {
        self.entries[0]
    }

    #[must_use]
    pub fn oldest(self) -> Option<ActorPathEntry> {
        self.len
            .checked_sub(1)
            .and_then(|index| self.entries[usize::from(index)])
    }

    /// Active entries in newest-to-oldest storage order.
    pub fn iter(self) -> impl DoubleEndedIterator<Item = ActorPathEntry> {
        self.entries
            .into_iter()
            .take(usize::from(self.len))
            .flatten()
    }

    pub(crate) fn ensure_position(&mut self, fallback: WorldTile) {
        if self.fine_position.is_none() {
            self.fine_position = Some(ActorFinePosition::from_tile(fallback));
        }
    }

    pub(crate) fn set_fine_position(&mut self, position: ActorFinePosition) {
        self.fine_position = Some(position);
    }

    pub(crate) fn enqueue(&mut self, target: WorldTile, speed: MoveSpeed) {
        let active = usize::from(self.len);
        let next_len = (active + 1).min(ACTOR_PATH_CAPACITY);
        for index in (1..next_len).rev() {
            self.entries[index] = self.entries[index - 1];
        }
        self.entries[0] = Some(ActorPathEntry { target, speed });
        self.len = u8::try_from(next_len).expect("actor path capacity fits u8");
    }

    /// Remove and return the oldest active request, matching the stock scheduler's `len - 1`
    /// consumption order.
    pub(crate) fn pop_oldest(&mut self) -> Option<ActorPathEntry> {
        let index = usize::from(self.len.checked_sub(1)?);
        let entry = self.entries[index].take();
        self.len -= 1;
        entry
    }

    /// Clear path requests while retaining the current fine position. Forced movement uses this
    /// form because its approach phase begins from the actor's current sub-tile position.
    pub(crate) fn clear(&mut self) {
        self.entries = [None; ACTOR_PATH_CAPACITY];
        self.len = 0;
    }

    /// Clear path requests and place the scheduler cursor immediately at the supplied tile.
    pub(crate) fn reset_to(&mut self, tile: WorldTile) {
        self.clear();
        self.fine_position = Some(ActorFinePosition::from_tile(tile));
    }
}

#[cfg(test)]
mod tests {
    use super::{ACTOR_PATH_CAPACITY, ActorFinePosition, ActorPathQueue};
    use rustclient_model::{MoveSpeed, Plane, WorldTile};

    fn tile(x: i32) -> WorldTile {
        WorldTile::new(x, 0, Plane::MIN)
    }

    #[test]
    fn fine_position_is_lazy_and_can_be_reset_independently_of_endpoint_state() {
        let mut queue = ActorPathQueue::default();
        assert_eq!(queue.fine_position(tile(3)), ActorFinePosition::from_tile(tile(3)));
        queue.ensure_position(tile(3));
        queue.reset_to(tile(7));
        assert_eq!(queue.fine_position(tile(99)), ActorFinePosition::from_tile(tile(7)));
    }

    #[test]
    fn queue_consumption_order_is_oldest_first() {
        let mut queue = ActorPathQueue::default();
        queue.enqueue(tile(1), MoveSpeed::Walk);
        queue.enqueue(tile(2), MoveSpeed::Run);
        queue.enqueue(tile(3), MoveSpeed::Crawl);

        assert_eq!(queue.len(), 3);
        assert_eq!(queue.newest().unwrap().target, tile(3));
        assert_eq!(queue.oldest().unwrap().target, tile(1));
        assert_eq!(
            queue.iter().rev().map(|entry| entry.target).collect::<Vec<_>>(),
            vec![tile(1), tile(2), tile(3)]
        );
        assert_eq!(queue.pop_oldest().unwrap().target, tile(1));
        assert_eq!(queue.pop_oldest().unwrap().target, tile(2));
        assert_eq!(queue.pop_oldest().unwrap().target, tile(3));
        assert_eq!(queue.pop_oldest(), None);
    }

    #[test]
    fn full_queue_discards_previous_oldest() {
        let mut queue = ActorPathQueue::default();
        for x in 0..=ACTOR_PATH_CAPACITY {
            queue.enqueue(tile(i32::try_from(x).unwrap()), MoveSpeed::Walk);
        }

        assert_eq!(usize::from(queue.len()), ACTOR_PATH_CAPACITY);
        assert_eq!(queue.newest().unwrap().target, tile(9));
        assert_eq!(queue.oldest().unwrap().target, tile(1));
        assert_eq!(queue.pop_oldest().unwrap().target, tile(1));
    }

    #[test]
    fn clear_removes_entries_but_preserves_sub_tile_cursor() {
        let mut queue = ActorPathQueue::default();
        queue.ensure_position(tile(0));
        queue.enqueue(tile(1), MoveSpeed::Walk);
        queue.clear();
        assert!(queue.is_empty());
        assert_eq!(queue.oldest(), None);
        assert_eq!(queue.pop_oldest(), None);
        assert_eq!(queue.fine_position(tile(9)), ActorFinePosition::from_tile(tile(0)));
    }
}
