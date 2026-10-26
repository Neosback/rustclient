//! Typed gameplay events delivered to plugins in committed order (API 0.2).
//!
//! Names follow the `RuneLite` API (`GameTick`, `NpcSpawned`, `AnimationChanged`, ...). Events that
//! describe a removed actor carry the last snapshot because the current [`GameSnapshot`] no
//! longer contains it.

use crate::{
    ActorRef, Capability, ChatLine, GameStateKind, Hitsplat, MenuEntry, NpcSnapshot,
    PlayerSnapshot, Skill, SkillLevel,
};

/// Which variable class changed.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum VarKind {
    /// A varp changed.
    Varp,
    /// A varbit changed; the backing varp changed too.
    Varbit,
}

/// One committed event.
#[derive(Debug, Clone, PartialEq)]
pub enum GameEvent {
    /// The client lifecycle state changed.
    GameStateChanged(GameStateKind),
    /// One 20 ms client cycle elapsed.
    ClientTick,
    /// One committed gameplay tick.
    GameTick(u64),
    /// A new map region is about to replace the scene.
    PreMapLoad,
    /// A player entered the scene.
    PlayerSpawned(Box<PlayerSnapshot>),
    /// A player left the scene; carries the last snapshot.
    PlayerDespawned(Box<PlayerSnapshot>),
    /// A player's appearance block changed.
    PlayerChanged(u16),
    /// An NPC entered the scene.
    NpcSpawned(Box<NpcSnapshot>),
    /// An NPC left the scene; carries the last snapshot.
    NpcDespawned(Box<NpcSnapshot>),
    /// An NPC changed composition; carries the previous composition id.
    NpcChanged {
        /// NPC scene index.
        index: u16,
        /// Composition id before the change.
        previous: u32,
    },
    /// An actor's action animation changed.
    AnimationChanged {
        /// The actor.
        actor: ActorRef,
        /// New animation, or `None` when cleared.
        animation: Option<u32>,
    },
    /// An actor's spot animation changed.
    GraphicChanged {
        /// The actor.
        actor: ActorRef,
        /// New graphic, or `None` when cleared.
        graphic: Option<u32>,
    },
    /// An actor started or stopped interacting with another actor.
    InteractingChanged {
        /// The actor whose target changed.
        source: ActorRef,
        /// New target, or `None` when cleared.
        target: Option<ActorRef>,
    },
    /// A hitsplat was applied.
    HitsplatApplied {
        /// The actor that received it.
        actor: ActorRef,
        /// The splat.
        hitsplat: Hitsplat,
    },
    /// An actor's overhead text changed.
    OverheadTextChanged {
        /// The actor.
        actor: ActorRef,
        /// New text, or `None` when cleared.
        text: Option<String>,
    },
    /// A varp or varbit changed.
    VarbitChanged {
        /// Which class changed.
        kind: VarKind,
        /// Varp or varbit id.
        id: u32,
        /// New value.
        value: i32,
    },
    /// A skill's level or experience changed.
    StatChanged {
        /// The skill.
        skill: Skill,
        /// New values.
        level: SkillLevel,
    },
    /// An inventory-like container changed.
    ItemContainerChanged {
        /// Container id.
        container: u32,
    },
    /// An interface group was mounted.
    WidgetLoaded {
        /// Interface group id.
        group: u16,
    },
    /// An interface group was removed.
    WidgetClosed {
        /// Interface group id.
        group: u16,
    },
    /// A chat line was added.
    ChatMessage(ChatLine),
    /// The gameframe switched between fixed and resizable layouts.
    ResizeableChanged(bool),
    /// The window gained or lost focus.
    FocusChanged(bool),
    /// A menu entry was added while building the menu.
    MenuEntryAdded(MenuEntry),
    /// The menu is complete and about to be shown.
    MenuOpened(Vec<MenuEntry>),
    /// The menu was sorted; plugins may return reorder directives for these entries.
    PostMenuSort(Vec<MenuEntry>),
    /// A menu entry was chosen.
    MenuOptionClicked(MenuEntry),
    /// A client script finished running.
    ScriptPostFired(u32),
}

impl GameEvent {
    /// The capability a plugin needs to receive this event, if it exposes gated data.
    ///
    /// Lifecycle and timing events carry no gated data and are always delivered.
    #[must_use]
    pub const fn required_capability(&self) -> Option<Capability> {
        match self {
            Self::GameStateChanged(_) | Self::ClientTick | Self::GameTick(_) | Self::PreMapLoad => {
                None
            }
            Self::PlayerSpawned(_) | Self::PlayerDespawned(_) | Self::PlayerChanged(_) => {
                Some(Capability::GameReadPlayers)
            }
            Self::NpcSpawned(_) | Self::NpcDespawned(_) | Self::NpcChanged { .. } => {
                Some(Capability::GameReadNpcs)
            }
            Self::AnimationChanged { actor, .. }
            | Self::GraphicChanged { actor, .. }
            | Self::HitsplatApplied { actor, .. }
            | Self::OverheadTextChanged { actor, .. } => Some(actor_capability(*actor)),
            Self::InteractingChanged { source, .. } => Some(actor_capability(*source)),
            Self::VarbitChanged { .. } => Some(Capability::GameReadVars),
            Self::StatChanged { .. } => Some(Capability::GameReadSkills),
            Self::ItemContainerChanged { .. } => Some(Capability::GameReadInventory),
            Self::WidgetLoaded { .. } | Self::WidgetClosed { .. } => {
                Some(Capability::GameReadWidgets)
            }
            Self::ChatMessage(_) => Some(Capability::GameReadChat),
            Self::ResizeableChanged(_) | Self::FocusChanged(_) | Self::ScriptPostFired(_) => {
                Some(Capability::GameReadClient)
            }
            Self::MenuEntryAdded(_)
            | Self::MenuOpened(_)
            | Self::PostMenuSort(_)
            | Self::MenuOptionClicked(_) => Some(Capability::UiMenuSwap),
        }
    }
}

const fn actor_capability(actor: ActorRef) -> Capability {
    match actor {
        ActorRef::Player(_) => Capability::GameReadPlayers,
        ActorRef::Npc(_) => Capability::GameReadNpcs,
    }
}
