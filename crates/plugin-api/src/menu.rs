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

/// An edit a plugin asks the host to apply to the entries of the last `PostMenuSort` event.
///
/// `index` addresses the entry list delivered with that event; the host drops directives whose
/// index is out of range or whose menu has since changed.
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
    /// Replace the entry's option text.
    SetOption {
        /// Entry index.
        index: usize,
        /// New option text, at most 64 bytes.
        option: String,
    },
}
