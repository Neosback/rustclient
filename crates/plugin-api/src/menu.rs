//! Menu records and plugin-requested menu edits (API 0.2).

/// What a menu entry does when chosen, following `RuneLite`'s `MenuAction` groupings.
#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum MenuKind {
    /// Walk to the clicked tile.
    Walk,
    /// Cancel/close the menu.
    Cancel,
    /// An option on a world NPC, `op` is 1..=5.
    NpcOption(u8),
    /// An option on a world player, `op` is 1..=8.
    PlayerOption(u8),
    /// An option on a scenery object, `op` is 1..=5.
    ObjectOption(u8),
    /// An option on a ground item, `op` is 1..=5.
    GroundItemOption(u8),
    /// A widget action, `op` is 1..=10.
    WidgetOption(u8),
    /// Use/examine/other, by raw client type id.
    Other(u16),
}

/// One entry of the right-click menu.
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct MenuEntry {
    /// Option text, e.g. `Attack`.
    pub option: String,
    /// Target text, e.g. `Goblin (level-2)`.
    pub target: String,
    /// What choosing the entry does.
    pub kind: MenuKind,
    /// Primary identifier (NPC/object/item id, or widget child).
    pub identifier: i32,
    /// First parameter (scene x or widget child index).
    pub param0: i32,
    /// Second parameter (scene y or widget id).
    pub param1: i32,
    /// Whether the entry is dimmed and acts as a deprioritized option.
    pub deprioritized: bool,
}

/// `MenuAction.RUNELITE`: the raw client type of an entry a plugin added, as [`MenuKind::Other`].
pub const PLUGIN_MENU_TYPE: u16 = 1500;

/// An entry a plugin asks the host to add to the world menu (`Client.createMenuEntry`).
///
/// The host adds it, below the other entries, only while the menu is opened on `tile` and, when
/// `shift` is set, only while Shift is held. Choosing it delivers a `MenuOptionClicked` event whose
/// entry has this option and target, kind `Other(PLUGIN_MENU_TYPE)`, and the tile in `param0` and
/// `param1` as world x and y. Offers are redeclared every update; a stale one never stays.
#[derive(Debug, Clone, PartialEq, Eq)]
pub struct MenuOffer {
    /// Option text, at most 64 bytes.
    pub option: String,
    /// Target text, at most 64 bytes.
    pub target: String,
    /// The world tile the menu must be opened on. The plane is the local player's.
    pub tile: crate::WorldPoint,
    /// Offer the entry only while Shift is held.
    pub shift: bool,
}

/// An edit a plugin asks the host to apply to the entries of the last `PostMenuSort` event.
///
/// Indexes address the entry list delivered with that event, top first; the host drops
/// directives whose index is out of range. Edits apply in order, each to the order the earlier
/// ones produced, so a later edit of the same entry wins.
#[derive(Debug, Clone, PartialEq, Eq)]
pub enum MenuEdit {
    /// Make the entry the left-click default.
    MoveToTop {
        /// Entry index.
        index: usize,
    },
    /// Remove the entry from the menu.
    Remove {
        /// Entry index.
        index: usize,
    },
    /// Push the entry below the walk option so it is never the default.
    Deprioritize {
        /// Entry index.
        index: usize,
    },
    /// Exchange the positions of two entries (`RuneLite` Menu Entry Swapper's `swap`).
    ///
    /// Added additively to API 0.2 (ADR-0006).
    Swap {
        /// First entry index.
        first: usize,
        /// Second entry index.
        second: usize,
    },
    /// Replace the entry's option text.
    SetOption {
        /// Entry index.
        index: usize,
        /// New option text, at most 64 bytes.
        option: String,
    },
}
