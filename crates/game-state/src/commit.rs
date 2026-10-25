use crate::TitleScreen;
use crate::{AuthenticatedPlayer, InitialPlacement};
use rustclient_model::{GameTick, Revision, SceneDescriptor};

/// A semantic fact that became true because authoritative state committed.
///
/// Events are outputs, never mutation requests. A successful operation that
/// does not change observable state may commit without producing an event.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum SemanticEvent {
    LocationAreaReset {
        origin: rustclient_model::WorldTile,
        size: [u32; 2],
    },
    ChatMessageAdded(u32),
    ChatSettingsChanged,
    InterfacesChanged,
    InventoryChanged(rustclient_model::InventoryId),
    ClientCyclesAdvanced {
        previous: rustclient_model::ClientCycle,
        current: rustclient_model::ClientCycle,
    },
    VariableChanged {
        id: rustclient_model::VarpId,
        previous: i32,
        current: i32,
    },
    VariablesReset,
    VariablesSynchronized,
    ServerVariableChanged {
        id: rustclient_model::VarpId,
        previous: i32,
        current: i32,
    },
    LocationChanged {
        key: rustclient_model::LocationKey,
        previous: crate::LocationOverride,
        current: crate::LocationOverride,
    },
    /// Authoritative entity state (players/NPCs) updated.
    EntitiesUpdated,
    /// Active gameframe tab selection changed.
    GameFrameTabSelected(Option<usize>),
    DestinationChanged(Option<rustclient_model::MapDestination>),
    /// A server authenticated this session.
    SessionAuthenticated {
        revision: Revision,
        player: AuthenticatedPlayer,
    },
    /// The server supplied initial placement.
    InitialPlacementEstablished(InitialPlacement),
    /// The current map selection changed; previous scene projections are invalid.
    MapRebuilt(InitialPlacement),
    /// A validated immutable asset became the active scene.
    SceneInstalled(SceneDescriptor),
    /// Non-secret pre-session navigation committed.
    TitleScreenChanged {
        /// Previously displayed panel.
        previous: TitleScreen,
        /// Newly committed panel.
        current: TitleScreen,
    },
    /// A semantic game session became active for an explicit compatibility revision.
    SessionBegan {
        /// Protocol/cache compatibility revision selected for the session.
        revision: Revision,
    },
    /// The active semantic game session ended.
    SessionEnded,
    /// Deterministic semantic game time changed from one tick to another.
    GameTickAdvanced {
        /// Tick ordinal before the committed transition.
        previous: GameTick,
        /// Tick ordinal after the committed transition.
        current: GameTick,
    },
}

/// Efficient description of which currently modeled state domains changed.
///
/// A delta is projection metadata, not authoritative state. Consumers query
/// [`crate::GameState`] for current values after observing changed domains.
#[derive(Debug, Clone, Copy, PartialEq, Eq, Default)]
pub struct StateDelta {
    changed: u16,
}
impl StateDelta {
    pub(crate) const PLAYER_PLANE: Self = Self {
        changed: 8 | 16 | 128,
    };
    pub(crate) const MAP_REBUILD: Self = Self {
        changed: 8 | 16 | 32,
    };
    pub(crate) const fn session_boundary(title_changed: bool) -> Self {
        Self {
            changed: 1
                | 2
                | 8
                | 16
                | 32
                | 64
                | 128
                | 256
                | 512
                | 2048
                | 1024
                | if title_changed { 4 } else { 0 },
        }
    }
    pub(crate) const CHAT: Self = Self { changed: 2048 };
    #[must_use]
    pub const fn chat_changed(self) -> bool {
        self.changed & 2048 != 0
    }
    pub(crate) const INTERFACE: Self = Self { changed: 1024 };
    #[must_use]
    pub const fn interfaces_changed(self) -> bool {
        self.changed & 1024 != 0
    }
    pub(crate) const INVENTORY: Self = Self { changed: 512 };
    #[must_use]
    pub const fn inventory_changed(self) -> bool {
        self.changed & 512 != 0
    }
    pub(crate) const ENTITIES: Self = Self { changed: 128 };
    pub(crate) const GAMEFRAME: Self = Self { changed: 256 };
    pub(crate) const SCENERY: Self = Self { changed: 32 };
    pub(crate) const fn client_cycle(entities_changed: bool) -> Self {
        Self {
            changed: 64 | if entities_changed { 128 } else { 0 },
        }
    }
    pub(crate) const fn with_gameframe(self, changed: bool) -> Self {
        Self {
            changed: self.changed | if changed { 256 } else { 0 },
        }
    }
    #[must_use]
    pub const fn entities_changed(self) -> bool {
        self.changed & 128 != 0
    }
    #[must_use]
    pub const fn gameframe_changed(self) -> bool {
        self.changed & 256 != 0
    }
    #[must_use]
    pub const fn scenery_changed(self) -> bool {
        self.changed & 32 != 0
    }
    #[must_use]
    pub const fn client_cycle_changed(self) -> bool {
        self.changed & 64 != 0
    }
    pub(crate) const SCENE: Self = Self { changed: 16 };
    /// Whether the session's immutable scene reference changed.
    #[must_use]
    pub const fn scene_changed(self) -> bool {
        self.changed & 16 != 0
    }
    pub(crate) const TITLE: Self = Self { changed: 4 };
    pub(crate) const GAME_TICK: Self = Self { changed: 2 };
    pub(crate) const CONNECTION: Self = Self { changed: 8 };
    /// Whether authenticated identity or initial placement changed.
    #[must_use]
    pub const fn connection_changed(self) -> bool {
        self.changed & 8 != 0
    }
    /// Whether non-secret title navigation or feedback changed.
    #[must_use]
    pub const fn title_changed(self) -> bool {
        self.changed & 4 != 0
    }
    /// Whether lifecycle or session revision changed.
    #[must_use]
    pub const fn lifecycle_changed(self) -> bool {
        self.changed & 1 != 0
    }
    /// Whether observable semantic game time changed.
    #[must_use]
    pub const fn game_tick_changed(self) -> bool {
        self.changed & 2 != 0
    }
    /// Whether no modeled domain changed.
    #[must_use]
    pub const fn is_empty(self) -> bool {
        self.changed == 0
    }
}

/// Result metadata for one successfully applied semantic operation.
///
/// The authoritative mutation has already committed when this value exists.
/// `event` is absent when the successful operation caused no observable state
/// change; `delta` is correspondingly empty in that case.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub struct OperationCommit {
    event: Option<SemanticEvent>,
    delta: StateDelta,
}

impl OperationCommit {
    pub(crate) const fn new(event: Option<SemanticEvent>, delta: StateDelta) -> Self {
        Self { event, delta }
    }

    /// Return the committed semantic fact, if observable state changed.
    #[must_use]
    pub const fn event(self) -> Option<SemanticEvent> {
        self.event
    }

    /// Return the projection-oriented description of changed state domains.
    #[must_use]
    pub const fn delta(self) -> StateDelta {
        self.delta
    }
}
