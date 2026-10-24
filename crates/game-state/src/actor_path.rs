//! Bounded authoritative actor path queue shared by players and NPCs.

use rustclient_model::{MoveSpeed, WorldTile};

/// Maximum number of active path requests retained by revision-240 actor scheduling.
pub const ACTOR_PATH_CAPACITY: usize = 9;

/// One queued actor path target and its authored traversal mode.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct ActorPathEntry {
    pub target: WorldTile,
    pub speed: MoveSpeed,
}

/// Bounded newest-first queue matching the observable `Actor.method3120` semantics.
///
/// New entries are inserted at index zero. The scheduler consumes the oldest active entry from
/// the tail. When full, inserting another request drops the previous oldest entry, matching the
/// stock client's length-9 active window over its ten-element backing arrays.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct ActorPathQueue {
    entries: [Option<ActorPathEntry>; ACTOR_PATH_CAPACITY],
    len: u8,
}

impl Default for ActorPathQueue {
    fn default() -> Self {
        Self {
            entries: [None; ACTOR_PATH_CAPACITY],
            len: 0,
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

    pub(crate) fn clear(&mut self) {
        self.entries = [None; ACTOR_PATH_CAPACITY];
        self.len = 0;
    }
}

#[cfg(test)]
mod tests {
    use super::{ACTOR_PATH_CAPACITY, ActorPathQueue};
    use rustclient_model::{MoveSpeed, Plane, WorldTile};

    fn tile(x: i32) -> WorldTile {
        WorldTile::new(x, 0, Plane::MIN)
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
    fn clear_removes_all_active_entries() {
        let mut queue = ActorPathQueue::default();
        queue.enqueue(tile(1), MoveSpeed::Walk);
        queue.clear();
        assert!(queue.is_empty());
        assert_eq!(queue.oldest(), None);
        assert_eq!(queue.pop_oldest(), None);
    }
}
