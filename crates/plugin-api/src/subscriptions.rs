//! Static event subscriptions for native and future component plugins.
use crate::{Capability, CapabilitySet, GameEvent};

/// An event class, independent of an individual event's payload.
#[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord, Hash)]
#[repr(u8)]
pub enum GameEventKind {
    /// The `GameStateChanged` event class.
    GameStateChanged,
    /// The `ClientTick` event class.
    ClientTick,
    /// The `GameTick` event class.
    GameTick,
    /// The `PreMapLoad` event class.
    PreMapLoad,
    /// The `PlayerSpawned` event class.
    PlayerSpawned,
    /// The `PlayerDespawned` event class.
    PlayerDespawned,
    /// The `PlayerChanged` event class.
    PlayerChanged,
    /// The `NpcSpawned` event class.
    NpcSpawned,
    /// The `NpcDespawned` event class.
    NpcDespawned,
    /// The `NpcChanged` event class.
    NpcChanged,
    /// The `AnimationChanged` event class.
    AnimationChanged,
    /// The `GraphicChanged` event class.
    GraphicChanged,
    /// The `InteractingChanged` event class.
    InteractingChanged,
    /// The `HitsplatApplied` event class.
    HitsplatApplied,
    /// The `OverheadTextChanged` event class.
    OverheadTextChanged,
    /// The `VarbitChanged` event class.
    VarbitChanged,
    /// The `StatChanged` event class.
    StatChanged,
    /// The `ItemContainerChanged` event class.
    ItemContainerChanged,
    /// The `WidgetLoaded` event class.
    WidgetLoaded,
    /// The `WidgetClosed` event class.
    WidgetClosed,
    /// The `ChatMessage` event class.
    ChatMessage,
    /// The `ResizeableChanged` event class.
    ResizeableChanged,
    /// The `FocusChanged` event class.
    FocusChanged,
    /// The `MenuEntryAdded` event class.
    MenuEntryAdded,
    /// The `MenuOpened` event class.
    MenuOpened,
    /// The `PostMenuSort` event class.
    PostMenuSort,
    /// The `MenuOptionClicked` event class.
    MenuOptionClicked,
    /// The `ScriptPostFired` event class.
    ScriptPostFired,
}
impl GameEventKind {
    /// All currently supported declarations, in stable declaration order.
    pub const ALL: [Self; 28] = [
        Self::GameStateChanged,
        Self::ClientTick,
        Self::GameTick,
        Self::PreMapLoad,
        Self::PlayerSpawned,
        Self::PlayerDespawned,
        Self::PlayerChanged,
        Self::NpcSpawned,
        Self::NpcDespawned,
        Self::NpcChanged,
        Self::AnimationChanged,
        Self::GraphicChanged,
        Self::InteractingChanged,
        Self::HitsplatApplied,
        Self::OverheadTextChanged,
        Self::VarbitChanged,
        Self::StatChanged,
        Self::ItemContainerChanged,
        Self::WidgetLoaded,
        Self::WidgetClosed,
        Self::ChatMessage,
        Self::ResizeableChanged,
        Self::FocusChanged,
        Self::MenuEntryAdded,
        Self::MenuOpened,
        Self::PostMenuSort,
        Self::MenuOptionClicked,
        Self::ScriptPostFired,
    ];
    /// Stable host routing index, unrelated to revision-specific packet ids.
    #[must_use]
    pub const fn index(self) -> usize {
        self as usize
    }
    /// Whether grants can authorize this class; payload checks still apply at dispatch.
    #[must_use]
    pub fn permitted_by(self, grants: &CapabilitySet) -> bool {
        let required = match self {
            Self::GameStateChanged | Self::ClientTick | Self::GameTick | Self::PreMapLoad => {
                return true;
            }
            Self::PlayerSpawned | Self::PlayerDespawned | Self::PlayerChanged => {
                Capability::GameReadPlayers
            }
            Self::NpcSpawned | Self::NpcDespawned | Self::NpcChanged => Capability::GameReadNpcs,
            Self::AnimationChanged
            | Self::GraphicChanged
            | Self::InteractingChanged
            | Self::HitsplatApplied
            | Self::OverheadTextChanged => {
                return grants.contains(Capability::GameReadPlayers)
                    || grants.contains(Capability::GameReadNpcs);
            }
            Self::VarbitChanged => Capability::GameReadVars,
            Self::StatChanged => Capability::GameReadSkills,
            Self::ItemContainerChanged => Capability::GameReadInventory,
            Self::WidgetLoaded | Self::WidgetClosed => Capability::GameReadWidgets,
            Self::ChatMessage => Capability::GameReadChat,
            Self::ResizeableChanged | Self::FocusChanged | Self::ScriptPostFired => {
                Capability::GameReadClient
            }
            Self::MenuEntryAdded
            | Self::MenuOpened
            | Self::PostMenuSort
            | Self::MenuOptionClicked => Capability::UiMenuSwap,
        };
        grants.contains(required)
    }
}

/// Allocation-free set of event classes. Interests never grant access to data.
#[derive(Debug, Clone, Copy, PartialEq, Eq, Default)]
pub struct EventInterest(u64);
impl EventInterest {
    /// No gameplay callbacks.
    pub const NONE: Self = Self(0);
    /// Every declared gameplay event; the host still checks grants.
    pub const ALL: Self = Self((1_u64 << GameEventKind::ALL.len()) - 1);
    /// One event class.
    #[must_use]
    pub const fn one(kind: GameEventKind) -> Self {
        Self(1_u64 << kind.index())
    }
    /// Include another event class.
    #[must_use]
    pub const fn with(self, kind: GameEventKind) -> Self {
        self.union(Self::one(kind))
    }
    /// Union of two sets.
    #[must_use]
    pub const fn union(self, other: Self) -> Self {
        Self(self.0 | other.0)
    }
    /// Whether this class is requested.
    #[must_use]
    pub const fn contains(self, kind: GameEventKind) -> bool {
        self.0 & Self::one(kind).0 != 0
    }
    /// Classes shared by both sets.
    #[must_use]
    pub const fn intersection(self, other: Self) -> Self {
        Self(self.0 & other.0)
    }
    /// Whether the sets share any class.
    #[must_use]
    pub const fn intersects(self, other: Self) -> bool {
        self.0 & other.0 != 0
    }
    /// Intersect declarations with the data grants that can authorize each class.
    #[must_use]
    pub fn permitted_by(self, grants: &CapabilitySet) -> Self {
        GameEventKind::ALL
            .into_iter()
            .filter(|&kind| self.contains(kind) && kind.permitted_by(grants))
            .fold(Self::NONE, Self::with)
    }
    /// Whether no classes are requested.
    #[must_use]
    pub const fn is_empty(self) -> bool {
        self.0 == 0
    }
}
impl GameEvent {
    /// Class used for subscription routing without inspecting or copying payloads.
    #[must_use]
    pub const fn kind(&self) -> GameEventKind {
        match self {
            Self::GameStateChanged(_) => GameEventKind::GameStateChanged,
            Self::ClientTick => GameEventKind::ClientTick,
            Self::GameTick(_) => GameEventKind::GameTick,
            Self::PreMapLoad => GameEventKind::PreMapLoad,
            Self::PlayerSpawned(_) => GameEventKind::PlayerSpawned,
            Self::PlayerDespawned(_) => GameEventKind::PlayerDespawned,
            Self::PlayerChanged(_) => GameEventKind::PlayerChanged,
            Self::NpcSpawned(_) => GameEventKind::NpcSpawned,
            Self::NpcDespawned(_) => GameEventKind::NpcDespawned,
            Self::NpcChanged { .. } => GameEventKind::NpcChanged,
            Self::AnimationChanged { .. } => GameEventKind::AnimationChanged,
            Self::GraphicChanged { .. } => GameEventKind::GraphicChanged,
            Self::InteractingChanged { .. } => GameEventKind::InteractingChanged,
            Self::HitsplatApplied { .. } => GameEventKind::HitsplatApplied,
            Self::OverheadTextChanged { .. } => GameEventKind::OverheadTextChanged,
            Self::VarbitChanged { .. } => GameEventKind::VarbitChanged,
            Self::StatChanged { .. } => GameEventKind::StatChanged,
            Self::ItemContainerChanged { .. } => GameEventKind::ItemContainerChanged,
            Self::WidgetLoaded { .. } => GameEventKind::WidgetLoaded,
            Self::WidgetClosed { .. } => GameEventKind::WidgetClosed,
            Self::ChatMessage(_) => GameEventKind::ChatMessage,
            Self::ResizeableChanged(_) => GameEventKind::ResizeableChanged,
            Self::FocusChanged(_) => GameEventKind::FocusChanged,
            Self::MenuEntryAdded(_) => GameEventKind::MenuEntryAdded,
            Self::MenuOpened(_) => GameEventKind::MenuOpened,
            Self::PostMenuSort(_) => GameEventKind::PostMenuSort,
            Self::MenuOptionClicked(_) => GameEventKind::MenuOptionClicked,
            Self::ScriptPostFired(_) => GameEventKind::ScriptPostFired,
        }
    }
}
