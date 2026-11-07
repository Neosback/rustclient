//! The synchronous menu stage (SPEC-0004, SPEC-0021): menu hooks and plugin menu edits.
//!
//! `RuneLite` fires `MenuEntryAdded` for each entry, `MenuOpened` when the menu is shown and
//! `PostMenuSort` once the entries are sorted; subscribers then rewrite the entry array in
//! place. Here plugins return [`MenuEdit`] directives instead, which the host collects
//! synchronously from the `PostMenuSort` subscribers and applies to the client-owned list.
use crate::{HostView, LogSink, PluginSet, PluginStorage};
use rustclient_plugin_api::{GameEvent, GameEventKind, MenuEdit, MenuEntry};
use std::collections::BTreeMap;

/// A menu after plugin edits: the original indexes in display order (top first) and replaced
/// option texts by original index.
#[derive(Debug, Clone, PartialEq, Eq, Default)]
pub struct EditedMenu {
    pub order: Vec<usize>,
    pub options: BTreeMap<usize, String>,
}
impl EditedMenu {
    /// The unedited menu of `len` entries.
    #[must_use]
    pub fn identity(len: usize) -> Self {
        Self {
            order: (0..len).collect(),
            options: BTreeMap::new(),
        }
    }
}

/// Apply `edits`, whose indexes address the `len` entries delivered with `PostMenuSort`, in
/// order, each to the order the earlier ones produced. Out-of-range indexes and edits of removed
/// entries are dropped.
///
/// `MoveToTop` puts the entry first, `Deprioritize` last, `Swap` exchanges two entries'
/// positions and `Remove` drops the entry.
#[must_use]
pub fn apply_menu_edits(len: usize, edits: &[MenuEdit]) -> EditedMenu {
    let mut order: Vec<usize> = (0..len).collect();
    let mut options = BTreeMap::new();
    let position = |order: &[usize], index: usize| order.iter().position(|&i| i == index);
    for edit in edits {
        match edit {
            MenuEdit::MoveToTop { index } => {
                if let Some(at) = position(&order, *index) {
                    order.remove(at);
                    order.insert(0, *index);
                }
            }
            MenuEdit::Deprioritize { index } => {
                if let Some(at) = position(&order, *index) {
                    order.remove(at);
                    order.push(*index);
                }
            }
            MenuEdit::Remove { index } => {
                if let Some(at) = position(&order, *index) {
                    order.remove(at);
                }
            }
            MenuEdit::Swap { first, second } => {
                if let (Some(a), Some(b)) = (position(&order, *first), position(&order, *second)) {
                    order.swap(a, b);
                }
            }
            MenuEdit::SetOption { index, option } => {
                if position(&order, *index).is_some() {
                    options.insert(*index, option.clone());
                }
            }
        }
    }
    options.retain(|index, _| order.contains(index));
    EditedMenu { order, options }
}

impl PluginSet {
    /// Whether any running plugin subscribes to a menu hook, so a client can skip the stage.
    #[must_use]
    pub fn wants_menu_stage(&self) -> bool {
        let kinds = self.game_event_kinds();
        [
            GameEventKind::MenuEntryAdded,
            GameEventKind::MenuOpened,
            GameEventKind::PostMenuSort,
        ]
        .into_iter()
        .any(|kind| kinds.contains(kind))
    }

    /// Run the menu stage for `entries` (display order, top first) and return the edited order.
    ///
    /// Delivers `MenuEntryAdded` for each entry, then `MenuOpened` when `opened` (the menu is
    /// being shown, not just consulted for the left-click default or mouse-over text), then
    /// `PostMenuSort`. The edits of every running `PostMenuSort` subscriber that holds
    /// `ui.menu.swap` are collected in subscriber priority order and applied together.
    pub fn menu_stage(
        &mut self,
        entries: &[MenuEntry],
        opened: bool,
        view: &dyn HostView,
        storage: &mut dyn PluginStorage,
        logs: &mut dyn LogSink,
    ) -> EditedMenu {
        if !self.wants_menu_stage() {
            return EditedMenu::identity(entries.len());
        }
        let mut events: Vec<GameEvent> = entries
            .iter()
            .cloned()
            .map(GameEvent::MenuEntryAdded)
            .collect();
        if opened {
            events.push(GameEvent::MenuOpened(entries.to_vec()));
        }
        events.push(GameEvent::PostMenuSort(entries.to_vec()));
        self.dispatch_game(&events, view, storage, logs);
        let subscribers = self.event_routes.classes[GameEventKind::PostMenuSort.index()].clone();
        let mut edits = Vec::new();
        for index in subscribers {
            let fault_limit = self.fault_limit;
            let plugin = &mut self.plugins[index];
            if !plugin.running()
                || !plugin
                    .manifest
                    .capabilities
                    .contains(rustclient_plugin_api::Capability::UiMenuSwap)
            {
                continue;
            }
            let Some(produced) = Self::invoke(
                plugin,
                crate::lifecycle::Phase::Output,
                view,
                storage,
                logs,
                fault_limit,
                |instance, call| instance.output(call),
            ) else {
                continue;
            };
            let checked = crate::v2::validate(&plugin.manifest.capabilities, produced, |_| {});
            edits.extend(checked.menu_edits);
        }
        apply_menu_edits(entries.len(), &edits)
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn edits_reorder_remove_and_rename_with_the_latest_edit_winning() {
        let edits = [
            MenuEdit::MoveToTop { index: 2 },
            MenuEdit::Remove { index: 1 },
            MenuEdit::Deprioritize { index: 0 },
            MenuEdit::SetOption {
                index: 3,
                option: "Pickpocket".into(),
            },
            MenuEdit::MoveToTop { index: 3 },
            MenuEdit::SetOption {
                index: 1,
                option: "gone".into(),
            },
            MenuEdit::Remove { index: 9 },
        ];
        let menu = apply_menu_edits(5, &edits);
        assert_eq!(menu.order, [3, 2, 4, 0]);
        // Swaps exchange positions in the order earlier edits produced.
        let swapped = apply_menu_edits(
            4,
            &[
                MenuEdit::Swap {
                    first: 0,
                    second: 2,
                },
                MenuEdit::Swap {
                    first: 2,
                    second: 3,
                },
                MenuEdit::Swap {
                    first: 1,
                    second: 7,
                },
            ],
        );
        assert_eq!(swapped.order, [3, 1, 0, 2]);
        assert_eq!(menu.options, BTreeMap::from([(3, "Pickpocket".to_owned())]));
        // A later deprioritize undoes an earlier move to the top.
        let menu = apply_menu_edits(
            3,
            &[
                MenuEdit::MoveToTop { index: 1 },
                MenuEdit::Deprioritize { index: 1 },
            ],
        );
        assert_eq!(menu.order, [0, 2, 1]);
        assert_eq!(apply_menu_edits(2, &[]), EditedMenu::identity(2));
    }
}
