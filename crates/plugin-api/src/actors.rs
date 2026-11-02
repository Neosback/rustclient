//! Actor lifetime references. A protocol slot is reusable; an incarnation is not.
use crate::{ActorRef, ActorState, GameSnapshot};

/// An actor in one client session. Unknown/legacy zero identities cannot form a handle.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct ActorHandle {
    session: u64,
    actor: ActorRef,
    incarnation: u64,
}
impl ActorHandle {
    /// Construct a reference from observed identities, returning `None` for unknown facts.
    #[must_use]
    pub const fn new(session: u64, actor: ActorRef, incarnation: u64) -> Option<Self> {
        if session == 0 || incarnation == 0 {
            None
        } else {
            Some(Self {
                session,
                actor,
                incarnation,
            })
        }
    }
    /// Application-owned session identity, unrelated to account identity.
    #[must_use]
    pub const fn session(self) -> u64 {
        self.session
    }
    /// Reusable scene slot; use the whole handle for retained references.
    #[must_use]
    pub const fn actor(self) -> ActorRef {
        self.actor
    }
    /// Identity from the authoritative actor owner.
    #[must_use]
    pub const fn incarnation(self) -> u64 {
        self.incarnation
    }
}
impl GameSnapshot {
    /// Capture the current occupant of a slot without guessing an identity.
    #[must_use]
    pub fn actor_handle(&self, actor: ActorRef) -> Option<ActorHandle> {
        ActorHandle::new(self.client.session, actor, self.actor(actor)?.instance)
    }
    /// Resolve a lifetime reference, rejecting another session or a replaced occupant.
    #[must_use]
    pub fn actor_by_handle(&self, handle: ActorHandle) -> Option<&ActorState> {
        if handle.session != self.client.session {
            return None;
        }
        self.actor(handle.actor)
            .filter(|actor| actor.instance == handle.incarnation)
    }
    fn actor(&self, actor: ActorRef) -> Option<&ActorState> {
        match actor {
            ActorRef::Player(index) => self.player(index).map(|player| &player.actor),
            ActorRef::Npc(index) => self.npc(index).map(|npc| &npc.actor),
        }
    }
}
