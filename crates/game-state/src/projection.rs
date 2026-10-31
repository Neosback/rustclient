//! Bounded invalidation history. Keys identify what to re-query, never semantic values.
use crate::{OperationCommit, SemanticEvent, StateDelta};
use rustclient_model::{InventoryId, InventorySlot, LocationKey, VarpId, WidgetAddress};
use std::collections::VecDeque;

const HISTORY_LIMIT: usize = 2048;

/// Actor identity for disposable projections; session invalidation is still mandatory.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum ActorProjectionKey {
    Player {
        index: rustclient_model::PlayerIndex,
        instance: rustclient_model::PlayerInstanceId,
    },
    Npc {
        index: rustclient_model::NpcIndex,
        instance: rustclient_model::NpcInstanceId,
    },
}

/// Position in one state's projection history; independent of other consumers.
#[derive(Debug, Clone, Copy, Default, PartialEq, Eq)]
pub struct ProjectionCursor(u64);

/// Internal projection invalidation; deliberately outside the external plugin ABI.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum ProjectionChange {
    /// Broad fallback for domains whose precise changes are not modeled yet.
    Domains(StateDelta),
    Location(LocationKey),
    Variable(VarpId),
    Inventory(InventoryId),
    InventorySlot {
        id: InventoryId,
        slot: InventorySlot,
    },
    Widget(WidgetAddress),
    Chat(u32),
    ActorOverhead(ActorProjectionKey),
}
impl ProjectionChange {
    const fn domains(self) -> StateDelta {
        match self {
            Self::Domains(delta) => delta,
            Self::Location(_) | Self::Variable(_) => StateDelta::SCENERY,
            Self::Inventory(_) | Self::InventorySlot { .. } => StateDelta::INVENTORY,
            Self::Widget(_) => StateDelta::INTERFACE,
            Self::Chat(_) => StateDelta::CHAT,
            Self::ActorOverhead(_) => StateDelta::ENTITIES,
        }
    }
}

/// Immutable observation; reading does not acknowledge or discard shared history.
pub struct ProjectionChanges<'a> {
    cursor: ProjectionCursor,
    missed: bool,
    missed_domains: StateDelta,
    start: usize,
    history: &'a VecDeque<ProjectionChange>,
}
impl ProjectionChanges<'_> {
    #[must_use]
    pub const fn cursor(&self) -> ProjectionCursor {
        self.cursor
    }
    /// A full current snapshot is required before accepting this cursor.
    #[must_use]
    pub const fn missed(&self) -> bool {
        self.missed
    }
    /// Only these current domains need full refresh when records expired.
    #[must_use]
    pub const fn missed_domains(&self) -> StateDelta {
        self.missed_domains
    }
    pub fn iter(&self) -> impl Iterator<Item = ProjectionChange> + '_ {
        self.history.range(self.start..).copied()
    }
}

#[derive(Debug, Default)]
pub(crate) struct ProjectionJournal {
    sequence: u64,
    exhausted: bool,
    latest: [u64; 12],
    history: VecDeque<ProjectionChange>,
}
impl ProjectionJournal {
    pub(crate) const fn cursor(&self) -> ProjectionCursor {
        ProjectionCursor(self.sequence)
    }
    pub(crate) fn read(&self, cursor: ProjectionCursor) -> ProjectionChanges<'_> {
        let count = self
            .sequence
            .checked_sub(cursor.0)
            .and_then(|n| usize::try_from(n).ok());
        let missed = self.exhausted || count.is_none_or(|n| n > self.history.len());
        let missed_domains = if !missed {
            StateDelta::default()
        } else if self.exhausted || cursor.0 > self.sequence {
            StateDelta::ALL
        } else {
            StateDelta::ALL
                .domains()
                .filter(|(index, _)| self.latest[*index] > cursor.0)
                .fold(StateDelta::default(), |delta, (_, domain)| {
                    delta.union(domain)
                })
        };
        ProjectionChanges {
            cursor: self.cursor(),
            missed,
            missed_domains,
            start: if missed {
                self.history.len()
            } else {
                self.history.len() - count.unwrap_or(0)
            },
            history: &self.history,
        }
    }
    pub(crate) fn push(&mut self, change: ProjectionChange) {
        let Some(sequence) = self.sequence.checked_add(1) else {
            // Never alias an old cursor after wraparound. Permanent full refresh is safe.
            self.exhausted = true;
            return;
        };
        self.sequence = sequence;
        for (index, _) in change.domains().domains() {
            self.latest[index] = sequence;
        }
        if self.history.len() == HISTORY_LIMIT {
            self.history.pop_front();
        }
        self.history.push_back(change);
    }
    pub(crate) fn committed(&mut self, commit: OperationCommit, widget: Option<WidgetAddress>) {
        if commit.delta().is_empty() {
            return;
        }
        if matches!(commit.event(), Some(SemanticEvent::InventoryChanged(_))) {
            // Inventory transitions already emitted the final slots/container identity.
            return;
        }
        let precise = widget
            .map(ProjectionChange::Widget)
            .or_else(|| match commit.event() {
                Some(
                    SemanticEvent::LocationChanged { key, .. }
                    | SemanticEvent::LocationOptionsChanged { key },
                ) => Some(ProjectionChange::Location(key)),
                Some(
                    SemanticEvent::VariableChanged { id, .. }
                    | SemanticEvent::ServerVariableChanged { id, .. },
                ) => Some(ProjectionChange::Variable(id)),
                Some(SemanticEvent::ChatMessageAdded(uid)) => Some(ProjectionChange::Chat(uid)),
                _ => None,
            });
        if precise.is_none_or(|change| change.domains() != commit.delta()) {
            self.push(ProjectionChange::Domains(commit.delta()));
        }
        if let Some(change) = precise {
            self.push(change);
        }
    }
}
